package dev.nathan.Employee.EmployeeRegistration;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record Employee(
        @Id
        Integer id,
        String title,
        @NotEmpty
        String full_name,
        String gender,
        @Positive
        Integer age,
        @Email
        String email,
        Integer phone_number,
        @DateTimeFormat
        LocalDateTime hire_date,
        String department



) {


  }
