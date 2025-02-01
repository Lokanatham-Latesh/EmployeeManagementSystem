package com.employee.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {
	private Long id;
	private String name;
	private String email;
	private Double salary;
	private LocalDate dateOfJoining;
}
