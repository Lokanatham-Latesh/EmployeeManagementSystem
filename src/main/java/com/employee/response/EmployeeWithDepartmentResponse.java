package com.employee.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeWithDepartmentResponse {

    private Long id;
    private String name;
    private String email;
    private Double salary;
    private LocalDate dateOfJoining;
    private List<DepartmentResponse> departments;  
}
