package com.em.employee_service.service;

import com.em.employee_service.dto.EmployeeRequestDTO;
import com.em.employee_service.entity.Employee;
import com.em.employee_service.exception.EmployeeNotFoundException;
import com.em.employee_service.exception.EmployeeWorkflowException;
import com.em.employee_service.grpc.PerformanceServiceGrpcClient;
import com.em.employee_service.kafka.KafkaProducer;
import com.em.employee_service.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private PerformanceServiceGrpcClient performanceServiceGrpcClient;

    @Mock
    private KafkaProducer kafkaProducer;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void createEmployeeRollsBackWorkflowWhenKafkaPublishFails() {
        EmployeeRequestDTO requestDTO = buildRequest();
        UUID employeeId = UUID.randomUUID();

        when(employeeRepository.existsByEmail(requestDTO.getEmail())).thenReturn(false);
        when(employeeRepository.saveAndFlush(any(Employee.class))).thenAnswer(invocation -> {
            Employee savedEmployee = invocation.getArgument(0);
            savedEmployee.setId(employeeId);
            return savedEmployee;
        });
        doThrow(new IllegalStateException("Kafka unavailable")).when(kafkaProducer).sendEvent(any(Employee.class));

        EmployeeWorkflowException exception = assertThrows(EmployeeWorkflowException.class, () -> employeeService.createEmployee(requestDTO));

        assertEquals("Unable to complete employee creation workflow", exception.getMessage());
        verify(performanceServiceGrpcClient).createPerformanceAccount(eq(employeeId.toString()), eq("Alice"), eq("alice@example.com"), eq("Engineer"));
        verify(performanceServiceGrpcClient).deletePerformanceAccount(employeeId.toString());
    }

    @Test
    void deleteEmployeeRejectsMissingEmployeeBeforeGrpcCall() {
        UUID employeeId = UUID.randomUUID();
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.deleteEmployee(employeeId));

        verify(performanceServiceGrpcClient, never()).deletePerformanceAccount(any());
    }

    @Test
    void deleteEmployeeDeletesExistingEmployeeAndPerformanceAccount() {
        UUID employeeId = UUID.randomUUID();
        Employee employee = new Employee();
        employee.setId(employeeId);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepository).delete(employee);

        employeeService.deleteEmployee(employeeId);

        ArgumentCaptor<Employee> employeeCaptor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository).delete(employeeCaptor.capture());
        verify(employeeRepository).flush();
        verify(performanceServiceGrpcClient).deletePerformanceAccount(employeeId.toString());
        assertEquals(employeeId, employeeCaptor.getValue().getId());
    }

    private EmployeeRequestDTO buildRequest() {
        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO();
        requestDTO.setName("Alice");
        requestDTO.setEmail("alice@example.com");
        requestDTO.setAddress("Main Street");
        requestDTO.setDateOfBirth(LocalDate.of(1995, 1, 15));
        requestDTO.setRegisterDate(LocalDate.of(2024, 1, 10));
        requestDTO.setDepartment("Engineering");
        requestDTO.setDesignation("Engineer");
        return requestDTO;
    }
}
