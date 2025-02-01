package com.employee.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.employee.request.DepartmentRequest;
import com.employee.response.DepartmentResponse;
import com.employee.response.DepartmentStatsResponse;

public interface DepartmentService {

	DepartmentResponse addDepartment(DepartmentRequest departmentRequest);

	DepartmentResponse getDepartmentById(Long id);

	Boolean deleteDepartment(Long id);

	Page<DepartmentResponse> getAllDepartments(int page, int size);

	List<DepartmentStatsResponse> getEmployeeCountByDepartment();

}
