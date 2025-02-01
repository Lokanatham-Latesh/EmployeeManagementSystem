package com.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employee.request.EmployeeDepartmentRequest;
import com.employee.request.EmployeeRequest;
import com.employee.response.EmployeeResponse;
import com.employee.response.EmployeeWithDepartmentResponse;
import com.employee.service.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "[1] Employee Management API", description = "Handles operations related to employee management, including CRUD operations, salary updates, and listing employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Operation(summary = "[1] This API is used to Add a New Employee")
	@PostMapping(value = "employees")
	public ResponseEntity<EmployeeResponse> addEmployee(@Valid @RequestBody EmployeeRequest employee) {

		return ResponseEntity.ok(employeeService.addEmployee(employee));
	}

	@Operation(summary = "[2] This API is used to Update an Existing Employee's Salary")
	@PutMapping(value = "employees/{id}/salary")
	public ResponseEntity<EmployeeResponse> updateEmployeeSalary(
			@PathVariable @NotNull(message = "Employee ID cannot be null") Long id,
			@RequestParam @Min(value = 0, message = "Salary must be a positive value") Double salary) {

		return ResponseEntity.ok(employeeService.updateEmployeeSalary(id, salary));
	}

	@Operation(summary = "[3] This API is used to Delete an Employee by ID")
	@DeleteMapping(value = "employees/{id}")
	public ResponseEntity<Boolean> deleteEmployee(
			@PathVariable @NotNull(message = "Employee ID cannot be null") Long id) {
		return ResponseEntity.ok(employeeService.deleteEmployee(id));
	}

	@Operation(summary = "[4] This API is used to Retrieve a List of All Employees")
	@GetMapping(value = "employees")
	public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {

		return ResponseEntity.ok(employeeService.getAllEmployees());

	}

	@Operation(summary = "[5] This API is used to Retrieve an Employee by ID")
	@GetMapping(value = "employees/{id}")
	public ResponseEntity<EmployeeResponse> getEmployeeById(
			@PathVariable @NotNull(message = "Employee ID cannot be null") Long id) {

		return ResponseEntity.ok(employeeService.getEmployeeById(id));

	}

	@Operation(summary = "[6] This API is used to Retrieve Employees with Salary Greater Than a Certain Value")
	@GetMapping(value = "employees/salary/{salary}")
	public ResponseEntity<List<EmployeeResponse>> getEmployeesWithSalaryGreaterThan(@PathVariable Double salary) {
		return ResponseEntity.ok(employeeService.getEmployeesWithSalaryGreaterThan(salary));

	}

	@Operation(summary = "[7] This API is used to Retrieve the Top 3 Highest Paid Employees")
	@GetMapping(value = "employees/top-salaries")
	public ResponseEntity<List<EmployeeResponse>> getTop3HighestPaidEmployees() {

		return ResponseEntity.ok(employeeService.getTop3HighestPaidEmployees());
	}

	@Operation(summary = "[8] This API is used to Add Employee to Departments")
	@PostMapping(value = "/employees/assign-departments")
	public ResponseEntity<String> AddEmployeeToDepartments(
			@RequestBody EmployeeDepartmentRequest employeeDepartmentRequest) {
		return ResponseEntity.ok(employeeService.AddEmployeeToDepartments(employeeDepartmentRequest));
	}

	@Operation(summary = "[9] This API is used to Retrieve the List of Employees who joined in last 6 months")
	@GetMapping(value = "employees/recent-joiners")
	public ResponseEntity<List<EmployeeResponse>> getRecentJoiners() {
		return ResponseEntity.ok(employeeService.getRecentJoiners());
	}

	@Operation(summary = "[10] This API is used to Retrieve the List of Employees with Pagination")
	@GetMapping(value = "employees/paginated")
	public ResponseEntity<List<EmployeeResponse>> getPaginatedEmployees(@RequestParam(defaultValue = "0") int page) {
		return ResponseEntity.ok(employeeService.getPaginatedEmployees(page));
	}

	@Operation(summary = "[11] This API is used to Retrieve Employees along with their Department Details")
	@GetMapping(value = "employees/with-departments")
	public ResponseEntity<List<EmployeeWithDepartmentResponse>> getEmployeesWithDepartments() {
		return ResponseEntity.ok(employeeService.getEmployeesWithDepartments());
	}

	@Operation(summary = "[12] This API is used to Transfer Employees from One Department to Another")
	@PutMapping(value = "employees/transfer/{employeeId}")
	public ResponseEntity<String> transferEmployee(
			@PathVariable @NotNull(message = "Employee ID cannot be null") Long employeeId,
			@RequestParam @NotNull(message = "From Department ID cannot be null") Long fromDepartmentId,
			@RequestParam @NotNull(message = "To Department ID cannot be null") Long toDepartmentId) {
		return ResponseEntity
				.ok(employeeService.transferEmployeesBetweenDepartments(fromDepartmentId, toDepartmentId, employeeId));

	}

	@Operation(summary = "[13] This API is used to update salaries for all employees by a given percentage")
	@PutMapping(value = "employees/bulk-update-salaries")
	public ResponseEntity<String> bulkSalaryUpdate(@RequestParam double percentage) {
		return ResponseEntity.ok(employeeService.bulkSalaryUpdate(percentage));
	}

}
