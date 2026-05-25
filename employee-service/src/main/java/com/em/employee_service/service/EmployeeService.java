package com.em.employee_service.service;

import com.em.employee_service.dto.EmployeeRequestDTO;
import com.em.employee_service.dto.EmployeeResponseDTO;
import com.em.employee_service.exception.EmailAlreadyExistsException;
import com.em.employee_service.exception.EmployeeNotFoundException;
import com.em.employee_service.exception.EmployeeWorkflowException;
import com.em.employee_service.grpc.PerformanceServiceGrpcClient;
import com.em.employee_service.kafka.KafkaProducer;
import com.em.employee_service.mapper.EmployeeMapper;
import com.em.employee_service.entity.Employee;
import com.em.employee_service.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);
    private final EmployeeRepository employeeRepository;
    private final PerformanceServiceGrpcClient performanceServiceGrpcClient;
    private final KafkaProducer kafkaProducer;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, PerformanceServiceGrpcClient performanceServiceGrpcClient, KafkaProducer kafkaProducer) {
        this.employeeRepository = employeeRepository;
        this.performanceServiceGrpcClient = performanceServiceGrpcClient;
        this.kafkaProducer = kafkaProducer;
    }

    public List<EmployeeResponseDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(EmployeeMapper::toEmployeeResponseDTO).toList();
    }

    @Transactional
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeRequestDTO){
        if(employeeRepository.existsByEmail(employeeRequestDTO.getEmail())){
            throw new EmailAlreadyExistsException("Employee with email "+employeeRequestDTO.getEmail()+" already exists");
        }

        Employee employee = EmployeeMapper.toEmployee(employeeRequestDTO);
        try {
            employee = employeeRepository.saveAndFlush(employee);
        } catch (DataIntegrityViolationException e) {
            throw new EmailAlreadyExistsException("Employee with email " + employeeRequestDTO.getEmail() + " already exists");
        }

        boolean performanceAccountCreated = false;
        try {
            performanceServiceGrpcClient.createPerformanceAccount(employee.getId().toString(),employee.getName(),employee.getEmail(),employee.getDesignation());
            performanceAccountCreated = true;
            kafkaProducer.sendEvent(employee);
        } catch (RuntimeException e) {
            if (performanceAccountCreated) {
                try {
                    performanceServiceGrpcClient.deletePerformanceAccount(employee.getId().toString());
                } catch (RuntimeException rollbackException) {
                    log.error("Failed to rollback performance account for employee {}", employee.getId(), rollbackException);
                    e.addSuppressed(rollbackException);
                }
            }
            throw new EmployeeWorkflowException("Unable to complete employee creation workflow", e);
        }

        return EmployeeMapper.toEmployeeResponseDTO(employee);
    }

    @Transactional
    public EmployeeResponseDTO updateEmployee(UUID id,EmployeeRequestDTO employeeRequestDTO){
        Employee employee=employeeRepository.findById(id).orElseThrow(()->new EmployeeNotFoundException("Employee with id "+id+" not found"));

        if(!employeeRequestDTO.getEmail().equals(employee.getEmail()) && employeeRepository.existsByEmail(employeeRequestDTO.getEmail())){
            throw new EmailAlreadyExistsException("Employee with email "+employeeRequestDTO.getEmail()+" already exists");
        }

        employee.setName(employeeRequestDTO.getName());
        employee.setEmail(employeeRequestDTO.getEmail());
        employee.setDepartment(employeeRequestDTO.getDepartment());
        employee.setAddress(employeeRequestDTO.getAddress());
        employee.setDateOfBirth(employeeRequestDTO.getDateOfBirth());
        employee.setDesignation(employeeRequestDTO.getDesignation());

        Employee updatedEmployee;
        try {
            updatedEmployee = employeeRepository.saveAndFlush(employee);
        } catch (DataIntegrityViolationException e) {
            throw new EmailAlreadyExistsException("Employee with email " + employeeRequestDTO.getEmail() + " already exists");
        }
        return EmployeeMapper.toEmployeeResponseDTO(updatedEmployee);
    }

    @Transactional
    public void deleteEmployee(UUID id){
        log.info("Deleting employee with id {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with id " + id + " not found"));
        employeeRepository.delete(employee);
        employeeRepository.flush();
        performanceServiceGrpcClient.deletePerformanceAccount(id.toString());
    }

}
