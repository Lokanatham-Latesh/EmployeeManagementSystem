package com.employee.util;

import com.employee.entity.Employee;
import com.employee.request.EmployeeRequest;

public class EmployeeRequestMapper {

	public static Employee mapToEmployee(EmployeeRequest employeeRequest) {
		if (employeeRequest == null) {
			return null;
		}

		Employee employee = new Employee();
		employee.setName(employeeRequest.getName());
		employee.setEmail(employeeRequest.getEmail());
		employee.setSalary(employeeRequest.getSalary());
		employee.setDateOfJoining(employeeRequest.getDateOfJoining());

		return employee;
	}
}
