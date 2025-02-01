package com.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employee.request.DepartmentRequest;
import com.employee.response.DepartmentResponse;
import com.employee.response.DepartmentStatsResponse;
import com.employee.service.DepartmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "[2] Department Management API", description = "Handles operations related to department management, including CRUD operations, updates, and listing departments")
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;

	@Operation(summary = "[1] This API is used to Add a New Department")
	@PostMapping(value = "departments")
	public ResponseEntity<DepartmentResponse> addDepartment(@Valid @RequestBody DepartmentRequest departmentRequest) {
		return ResponseEntity.ok(departmentService.addDepartment(departmentRequest));
	}

	@Operation(summary = "[3] This API is used to Delete a Department by ID")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Boolean> deleteDepartment(
			@PathVariable @NotNull(message = "Department ID cannot be null") Long id) {
		return ResponseEntity.ok(departmentService.deleteDepartment(id));
	}

	@Operation(summary = "[4] This API is used to Retrieve a Paginated List of All Departments")
	@GetMapping(value = "departments")
	public ResponseEntity<Page<DepartmentResponse>> getAllDepartments(
			@RequestParam(defaultValue = "0") @Min(value = 0, message = "Page index cannot be negative") int page,
			@RequestParam(defaultValue = "10") @Min(value = 1, message = "Page size must be at least 1") int size) {
		return ResponseEntity.ok(departmentService.getAllDepartments(page, size));
	}

	@Operation(summary = "[5] This API is used to Retrieve a Department by ID")
	@GetMapping(value = "departments/{id}")
	public ResponseEntity<DepartmentResponse> getDepartmentById(
			@PathVariable @NotNull(message = "Department ID cannot be null") Long id) {
		return ResponseEntity.ok(departmentService.getDepartmentById(id));
	}

	@Operation(summary = "[6] This API is used to Retrieve the Number of Employees in Each Department")
	@GetMapping(value = "departments/employee-count")
	public ResponseEntity<List<DepartmentStatsResponse>> getEmployeeCountInDepartment() {
		return ResponseEntity.ok(departmentService.getEmployeeCountByDepartment());
	}
}
