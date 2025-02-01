package com.employee.util;

import com.employee.entity.Employee;
import com.employee.response.EmployeeResponse;

public class EmployeeResponseMapper {

	public static EmployeeResponse mapToEmployeeResponse(Employee employee) {
		if (employee == null) {
			return null;
		}

		EmployeeResponse employeeResponse = new EmployeeResponse();
		employeeResponse.setId(employee.getId());
		employeeResponse.setName(employee.getName());
		employeeResponse.setEmail(employee.getEmail());
		employeeResponse.setSalary(employee.getSalary());
		employeeResponse.setDateOfJoining(employee.getDateOfJoining());

		return employeeResponse;
	}
}
