package com.employee.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employee.entity.Department;
import com.employee.exception.EmployeeSystemException;
import com.employee.exception.ProblemDetails;
import com.employee.repository.DepartmentRepository;
import com.employee.request.DepartmentRequest;
import com.employee.response.DepartmentResponse;
import com.employee.response.DepartmentStatsResponse;
import com.employee.service.DepartmentService;

import com.employee.validator.ValidatorConstant;

@Service
public class DepartmentServiceImp implements DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private ModelMapper mapper;

	/**
	 * Description: Adds a new department to the system.
	 * 
	 * @param departmentRequest The details of the department to be added.
	 * @return ResponseEntity containing the added department details.
	 */
	@Override
	@Transactional
	public DepartmentResponse addDepartment(DepartmentRequest departmentRequest) {
		Department newDepartment = new Department();
		newDepartment.setName(departmentRequest.getName());
		newDepartment.setLocation(departmentRequest.getLocation());

		try {

			Department department = departmentRepository.save(newDepartment);

			  return mapper.map(department, DepartmentResponse.class);
		} catch (DataIntegrityViolationException ex) {

			ProblemDetails problemDetails = new ProblemDetails(HttpStatus.CONFLICT.value(), ValidatorConstant.DUPLICATE_DEPARTMENT);
			throw new EmployeeSystemException.DuplicateDepartmentException(problemDetails);
		} catch (Exception ex) {
			ProblemDetails problemDetails = ProblemDetails
					.forInternalError(ValidatorConstant.INTERNAL_ERROR_SAVE+ ex.getMessage());
			throw new EmployeeSystemException.InternalServerErrorException(problemDetails);
		}
	}

	/**
	 * Description : Retrieves a department by its ID.
	 * 
	 * @param id The ID of the department to retrieve.
	 * @return ResponseEntity containing the department details.
	 */
	@Override
	public DepartmentResponse getDepartmentById(Long id) {

		Department department = findDepartmentById(id);
		  return mapper.map(department, DepartmentResponse.class);

	}

	/**
	 * Description: Deletes a department by its ID.
	 * 
	 * @param id The ID of the department to delete.
	 * @return ResponseEntity indicating whether the department was successfully
	 *         deleted.
	 */

	@Override
	public Boolean deleteDepartment(Long id) {

		Department department = findDepartmentById(id);

		try {
			departmentRepository.delete(department);
			return true;
		} catch (Exception ex) {
			ProblemDetails problemDetails = ProblemDetails
					.forInternalError(ValidatorConstant.INTERNAL_ERROR_DELETE + ex.getMessage());
			throw new EmployeeSystemException.InternalServerErrorException(problemDetails);
		}

	}

	/**
	 * Description: Retrieves all departments with pagination support.
	 * 
	 * @param page The page number.
	 * @param size The size of each page.
	 * @return ResponseEntity containing a paginated list of department details.
	 */

	@Override
	public Page<DepartmentResponse> getAllDepartments(int page, int size) {
		PageRequest pageable = PageRequest.of(page, size);

		Page<Department> departments = departmentRepository.findAll(pageable);

		if (departments.isEmpty()) {
			ProblemDetails problemDetails = ProblemDetails
					.forNotFound(ValidatorConstant.NO_DEPARTMENTS_FOUND);
			throw new EmployeeSystemException.DepartmentNotFoundException(problemDetails);
		}

		return (departments.map(department -> {
			  return mapper.map(department, DepartmentResponse.class);
		}));
	}

	/**
	 * Description of Code: Retrieves the employee count for each department.
	 * 
	 * @return ResponseEntity containing a list of department stats with employee
	 *         counts.
	 */

	@Override
	public List<DepartmentStatsResponse> getEmployeeCountByDepartment() {
		List<Object[]> result = departmentRepository.getEmployeeCountByDepartment();
		List<DepartmentStatsResponse> response = new ArrayList<>();

		if (result == null || result.isEmpty()) {

			ProblemDetails problemDetails = ProblemDetails.forNotFound(ValidatorConstant.NO_EMPLOYEE_COUNT_FOUND);
			throw new EmployeeSystemException.DepartmentNotFoundException(problemDetails);
		} else {
			for (Object[] row : result) {
				String departmentName = (row[0] != null) ? (String) row[0] : "Unknown Department";
				Long employeeCount = (row[1] != null) ? (Long) row[1] : 0L;

				response.add(new DepartmentStatsResponse(departmentName, employeeCount.intValue()));
			}
		}

		return response;
	}
	
	
	/**
	 * Description : Retrieves a department by ID and acts as helper method.
	 * 
	 * @param id The department ID.
	 * @return The Department entity if found.
	 */
	public Department findDepartmentById(Long id) {
		return departmentRepository.findById(id)
				.orElseThrow(() -> new EmployeeSystemException.DepartmentNotFoundException(new ProblemDetails(
						HttpStatus.NOT_FOUND.value(),String.format(ValidatorConstant.DEPARTMENT_NOT_FOUND, id))));
	}

}
