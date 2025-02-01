package com.employee.service.imp;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employee.entity.Department;
import com.employee.entity.Employee;
import com.employee.exception.EmployeeSystemException;
import com.employee.exception.ProblemDetails;
import com.employee.exception.EmployeeSystemException.EmployeeNotFoundException;
import com.employee.repository.DepartmentRepository;
import com.employee.repository.EmployeeRepository;
import com.employee.request.EmployeeDepartmentRequest;
import com.employee.request.EmployeeRequest;
import com.employee.response.DepartmentResponse;
import com.employee.response.EmployeeResponse;
import com.employee.response.EmployeeWithDepartmentResponse;
import com.employee.service.EmployeeService;
import com.employee.util.DepartmentResponseMapper;
import com.employee.util.EmployeeRequestMapper;
import com.employee.util.EmployeeResponseMapper;
import com.employee.validator.ValidatorConstant;

@Service
public class EmployeeServiceImp implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private DepartmentServiceImp departmentService;

	/**
	 * Description: Saves a new employee with the provided details.
	 * 
	 * @param employeeRequest The employee details.
	 * @return saved employee details.
	 */
	@Override
	public EmployeeResponse addEmployee(EmployeeRequest employeeRequest) {

		Employee employee = EmployeeRequestMapper.mapToEmployee(employeeRequest);

		try {
			Employee savedEmployee = employeeRepository.save(employee);
			EmployeeResponse employeeResponse = EmployeeResponseMapper.mapToEmployeeResponse(savedEmployee);
			return employeeResponse;

		} catch (DataIntegrityViolationException ex) {

			ProblemDetails problemDetails = new ProblemDetails(HttpStatus.CONFLICT.value(),
					ValidatorConstant.EMPLOYEE_ALREADY_EXISTS);
			throw new EmployeeSystemException.DuplicateEmployeeException(problemDetails);
		} catch (Exception ex) {
			ProblemDetails problemDetails = new ProblemDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					ValidatorConstant.INTERNAL_ERROR_SAVE + ex.getMessage());
			throw new EmployeeSystemException.InternalServerErrorException(problemDetails);
		}
	}

	/**
	 * Description: Retrieves an employee by ID.
	 * 
	 * @param id The employee ID.
	 * @return ResponseEntity containing the employee details.
	 */
	@Override
	public EmployeeResponse getEmployeeById(Long id) {

		if (id <= 0) {
			ProblemDetails problemDetails = ProblemDetails
					.forBadRequest(ValidatorConstant.EMPLOYEE_ID_POSITIVE_MESSAGE);
			throw new EmployeeSystemException.InvalidRequestException(problemDetails);
		}
		return (EmployeeResponseMapper.mapToEmployeeResponse(findEmployeeById(id)));
	}

	/**
	 * Description: Deletes an employee by ID.
	 * 
	 * @param id The employee ID.
	 * @return ResponseEntity containing a Boolean value indicating success.
	 */
	@Override
	public Boolean deleteEmployee(Long id) {

		Employee employee = findEmployeeById(id);
		employee.getDepartments().forEach(department -> {
			department.getEmployees().remove(employee);
		});

		employeeRepository.delete(employee);

		return true;
	}

	/**
	 * Description : Updates the salary of an employee by ID.
	 * 
	 * @param id     The employee ID.
	 * @param salary The new salary value.
	 * @return ResponseEntity containing the updated employee details.
	 */

	@Override
	public EmployeeResponse updateEmployeeSalary(Long id, Double salary) {

		if (salary <= 0) {
			ProblemDetails problemDetails = new ProblemDetails(HttpStatus.BAD_REQUEST.value(),
					ValidatorConstant.SALARY_POSITIVE_MESSAGE);
			throw new EmployeeSystemException.InvalidRequestException(problemDetails);
		}

		Employee employee = findEmployeeById(id);
		employee.setSalary(salary);

		try {

			Employee updatedEmployee = employeeRepository.save(employee);
			EmployeeResponse employeeResponse = EmployeeResponseMapper.mapToEmployeeResponse(updatedEmployee);
			return employeeResponse;
		} catch (Exception ex) {
			ProblemDetails problemDetails = new ProblemDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					ValidatorConstant.INTERNAL_ERROR_UPDATE + ex.getMessage());
			throw new EmployeeSystemException.InternalServerErrorException(problemDetails);
		}
	}

	/**
	 * Description of Code: Adds an employee to multiple departments.
	 * 
	 * @param employeeDepartmentRequest Contains employee ID and list of department
	 *                                  IDs to assign the employee to.
	 * @return ResponseEntity with a success message or error details.
	 */
	@Override
	@Transactional
	public String AddEmployeeToDepartments(EmployeeDepartmentRequest employeeDepartmentRequest) {

		Employee employee = findEmployeeById(employeeDepartmentRequest.getEmployeeId());
		List<Long> departmentIds = employeeDepartmentRequest.getDepartmentIds();
		if (departmentIds.isEmpty()) {
			ProblemDetails problemDetails = new ProblemDetails(HttpStatus.BAD_REQUEST.value(),
					ValidatorConstant.AT_LEAST_ONE_DEPARTMENT_ASSIGNED);
			throw new EmployeeSystemException.InvalidRequestException(problemDetails);
		}

		for (Long departmentId : departmentIds) {
			Department department = departmentService.findDepartmentById(departmentId);
			if (department.getEmployees().contains(employee)) {
				throw new EmployeeSystemException.DuplicateDepartmentException(new ProblemDetails(
						HttpStatus.BAD_REQUEST.value(), "Employee already exists in this department"));
			}

			else {
				department.getEmployees().add(employee);
				employee.getDepartments().add(department);
			}
		}

		try {
			employeeRepository.save(employee);
			return "Employee added to departments successfully.";
		} catch (Exception ex) {
			ProblemDetails problemDetails = new ProblemDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					ValidatorConstant.EMPLOYEE_ASSIGN_ERROR + ex.getMessage());
			throw new EmployeeSystemException.InternalServerErrorException(problemDetails);
		}
	}

	/**
	 * Description of Code: Retrieves a list of all employees.
	 * 
	 * @return ResponseEntity containing the list of employee details or an error
	 *         message if no employees are found.
	 */
	@Override
	public List<EmployeeResponse> getAllEmployees() {
		List<Employee> employees = employeeRepository.findAll();

		if (employees.isEmpty()) {
			ProblemDetails problemDetails = new ProblemDetails(HttpStatus.NOT_FOUND.value(),
					ValidatorConstant.EMPLOYEE_NOT_FOUND);
			throw new EmployeeSystemException.EmployeeNotFoundException(problemDetails);
		}

		List<EmployeeResponse> employeeResponses = employees.stream().map(employee -> {
			EmployeeResponse employeeResponse = EmployeeResponseMapper.mapToEmployeeResponse(employee);
			return employeeResponse;
		}).collect(Collectors.toList());

		return employeeResponses;
	}

	/**
	 * Description of Code: Retrieves a list of employees with a salary greater than
	 * the specified value.
	 * 
	 * @param salary The salary threshold.
	 * @return ResponseEntity containing the list of employee details or an error
	 *         message if no employees match the criteria.
	 */

	@Override
	public List<EmployeeResponse> getEmployeesWithSalaryGreaterThan(Double salary) {
		if (salary < 0) {
			throw new EmployeeSystemException.InvalidRequestException(
					new ProblemDetails(HttpStatus.BAD_REQUEST.value(), ValidatorConstant.SALARY_POSITIVE_MESSAGE));
		}

		List<Employee> employees = employeeRepository.findBySalaryGreaterThan(salary);

		if (employees.isEmpty()) {
			throw new EmployeeSystemException.EmployeeNotFoundException(new ProblemDetails(HttpStatus.NOT_FOUND.value(),
					ValidatorConstant.NO_EMPLOYEES_FOUND_SALARY_GREATER_THAN + salary));
		}

		return (employees.stream().map(emp -> EmployeeResponseMapper.mapToEmployeeResponse(emp))
				.collect(Collectors.toList()));
	}

	/**
	 * Description of Code: Retrieves a list of employees who joined in the last 6
	 * months.
	 * 
	 * @return ResponseEntity containing the list of recent joiners' details or an
	 *         error message if no employees match the criteria.
	 */
	@Override
	public List<EmployeeResponse> getRecentJoiners() {
		LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6);

		List<Employee> employees = employeeRepository.findByDateOfJoiningAfter(sixMonthsAgo);

		if (employees.isEmpty()) {
			throw new EmployeeSystemException.EmployeeNotFoundException(new ProblemDetails(HttpStatus.NOT_FOUND.value(),
					ValidatorConstant.NO_EMPLOYEES_JOINED_LAST_6_MONTHS));
		}

		return employees.stream().map(emp -> EmployeeResponseMapper.mapToEmployeeResponse(emp))
				.collect(Collectors.toList());
	}

	/**
	 * Description of Code: Retrieves the top 3 highest-paid employees.
	 * 
	 * @return ResponseEntity containing the list of the top 3 highest-paid
	 *         employees' details or an error message if no employees are found.
	 */
	@Override
	public List<EmployeeResponse> getTop3HighestPaidEmployees() {
		List<Employee> topEmployees = employeeRepository.getTop3HighestPaidEmployees();

		if (topEmployees.isEmpty()) {
			ProblemDetails problemDetails = ProblemDetails.forNotFound("No employees found with salary details.");
			throw new EmployeeSystemException.EmployeeNotFoundException(problemDetails);
		}

		List<EmployeeResponse> employeeResponses = topEmployees.stream().map(employee -> {
			EmployeeResponse employeeResponse = EmployeeResponseMapper.mapToEmployeeResponse(employee);
			return employeeResponse;
		}).collect(Collectors.toList());

		return employeeResponses;
	}

	/**
	 * Description: Retrieves paginated list of employees sorted by salary
	 * in descending order.
	 * 
	 * @param page The page number to fetch, starting from 0.
	 * @return ResponseEntity containing the list of employees for the given page or
	 *         an error message if no employees are found.
	 */

	@Override
	public List<EmployeeResponse> getPaginatedEmployees(int page) {

		if (page < 0) {
			ProblemDetails problemDetails = ProblemDetails.forBadRequest(ValidatorConstant.PAGE_MUST_BE_POSITIVE);
			throw new EmployeeSystemException.InvalidRequestException(problemDetails);
		}

		PageRequest pageRequest = PageRequest.of(page, 5, Sort.by(Sort.Order.desc("salary")));

		Page<Employee> employeePage = employeeRepository.findAll(pageRequest);

		if (employeePage.isEmpty()) {
			ProblemDetails problemDetails = ProblemDetails.forNotFound(ValidatorConstant.EMPLOYEE_PAGE_NOT_FOUND);
			throw new EmployeeSystemException.EmployeeNotFoundException(problemDetails);
		}

		List<EmployeeResponse> employeeResponses = employeePage
				.getContent().stream().map(employee -> new EmployeeResponse(employee.getId(), employee.getName(),
						employee.getEmail(), employee.getSalary(), employee.getDateOfJoining()))
				.collect(Collectors.toList());

		return employeeResponses;
	}

	/**
	 * Description: Retrieves a list of employees along with their
	 * associated departments.
	 * 
	 * @return ResponseEntity containing a list of employees with department
	 *         details.
	 */
	@Override
	public List<EmployeeWithDepartmentResponse> getEmployeesWithDepartments() {
		List<Employee> employees = employeeRepository.findAll();

		if (employees.isEmpty()) {
			throw new EmployeeSystemException.EmployeeNotFoundException(
					new ProblemDetails(HttpStatus.NOT_FOUND.value(), ValidatorConstant.EMPLOYEE_NOT_FOUND));

		}

		return (employees.stream().map(employee -> {
			List<DepartmentResponse> departmentResponses = employee.getDepartments().stream()
					.map(department -> DepartmentResponseMapper.mapToDepartmentResponse(department))
					.collect(Collectors.toList());

			return new EmployeeWithDepartmentResponse(employee.getId(), employee.getName(), employee.getEmail(),
					employee.getSalary(), employee.getDateOfJoining(), departmentResponses);
		}).collect(Collectors.toList()));
	}

	/**
	 * Description: Transfers an employee from one department to another.
	 * 
	 * @param fromDepartmentId The ID of the source department.
	 * @param toDepartmentId   The ID of the destination department.
	 * @param employeeId       The ID of the employee being transferred.
	 * @return ResponseEntity containing the result of the transfer operation.
	 */

	@Transactional
	@Override
	public String transferEmployeesBetweenDepartments(Long fromDepartmentId, Long toDepartmentId, Long employeeId) {

		Employee employee = findEmployeeById(employeeId);
		Department fromDepartment = departmentService.findDepartmentById(fromDepartmentId);

		Department toDepartment = departmentService.findDepartmentById(toDepartmentId);

		if (toDepartment.getEmployees().contains(employee)) {
			return ValidatorConstant.EMPLOYEE_ALREADY_IN_DESTINATION_DEPARTMENT;
		}

		if (!fromDepartment.getEmployees().contains(employee)) {
			ProblemDetails problemDetails = new ProblemDetails(HttpStatus.BAD_REQUEST.value(),
					ValidatorConstant.EMPLOYEE_NOT_IN_SOURCE_DEPARTMENT);
			throw new EmployeeNotFoundException(problemDetails);
		}

		fromDepartment.getEmployees().remove(employee);
		employee.getDepartments().remove(fromDepartment);

		toDepartment.getEmployees().add(employee);
		employee.getDepartments().add(toDepartment);

		try {

			departmentRepository.save(fromDepartment);
			departmentRepository.save(toDepartment);
			employeeRepository.save(employee);
		} catch (DataAccessException e) {
			ProblemDetails problemDetails = new ProblemDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					ValidatorConstant.EMPLOYEE_TRANSFER_ERROR + e.getMessage());
			throw new EmployeeSystemException.InternalServerErrorException(problemDetails);
		}

		return "Employee transfer successful.";
	}

	/**
	 * Description: Updates the salary of all employees by a specified
	 * percentage.
	 * 
	 * @param percentage The percentage by which to increase the salaries.
	 * @return ResponseEntity containing the result of the bulk salary update
	 *         operation.
	 */

	@Override
	public String bulkSalaryUpdate(double percentage) {

		if (percentage < 1) {
			ProblemDetails problemDetails = new ProblemDetails(HttpStatus.BAD_REQUEST.value(),
					ValidatorConstant.PERCENTAGE_ERROR_MESSAGE);
			throw new EmployeeSystemException.InvalidRequestException(problemDetails);
		}

		try {

			employeeRepository.updateSalariesByPercentage(percentage);
		} catch (DataAccessException e) {
			ProblemDetails problemDetails = new ProblemDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					ValidatorConstant.INTERNAL_ERROR_UPDATE);
			throw new EmployeeSystemException.InternalServerErrorException(problemDetails);
		}

		return "Salary update successful for all employees.";
	}

	/**
	 * Description: Retrieves an employee by ID and acts as helper method.
	 * 
	 * @param id The employee ID.
	 * @return The Employee entity if found.
	 */
	private Employee findEmployeeById(Long id) {
		return employeeRepository.findById(id)
				.orElseThrow(() -> new EmployeeSystemException.EmployeeNotFoundException(new ProblemDetails(
						HttpStatus.NOT_FOUND.value(), String.format(ValidatorConstant.EMPLOYEE_NOT_FOUND, id))));
	}

}
