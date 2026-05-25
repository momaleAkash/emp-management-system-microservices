package com.em.employee_service.dto;

import com.em.employee_service.validators.CreateEmployeeValidationGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class EmployeeRequestDTO {
    @NotBlank(message="Name is mandatory")
    @Size(max=70,message="Name should be less than 70 characters")
    private String name;

    @NotBlank(message="Email is mandatory")
    @Email(message="Email should be valid")
    private String email;

    @NotBlank(message="Address is mandatory")
    private String address;

    @NotNull(message="Date of birth is mandatory")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotNull(groups = CreateEmployeeValidationGroup.class, message="Register date is mandatory")
    private LocalDate registerDate;

    @NotBlank(message="Department is mandatory")
    private String department;

    @NotBlank(message="Designation is mandatory")
    private String designation;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
