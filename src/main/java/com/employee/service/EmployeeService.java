package com.employee.service;

import java.util.List;

import com.employee.request.EmployeeDepartmentRequest;
import com.employee.request.EmployeeRequest;
import com.employee.response.EmployeeResponse;
import com.employee.response.EmployeeWithDepartmentResponse;

public interface EmployeeService {
	EmployeeResponse addEmployee(EmployeeRequest employeeRequest);

	EmployeeResponse getEmployeeById(Long id);

	Boolean deleteEmployee(Long id);

	EmployeeResponse updateEmployeeSalary(Long id, Double salary);

	String AddEmployeeToDepartments(EmployeeDepartmentRequest employeeDepartmentRequest);

	List<EmployeeResponse> getAllEmployees();

	List<EmployeeResponse> getEmployeesWithSalaryGreaterThan(Double salary);

	List<EmployeeResponse> getRecentJoiners();

	List<EmployeeResponse> getTop3HighestPaidEmployees();

	List<EmployeeResponse> getPaginatedEmployees(int page);

	List<EmployeeWithDepartmentResponse> getEmployeesWithDepartments();

	String transferEmployeesBetweenDepartments(Long fromDepartmentId, Long toDepartmentId, Long employeeId);

	String bulkSalaryUpdate(double percentage);
}
