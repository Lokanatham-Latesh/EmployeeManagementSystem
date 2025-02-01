package com.employee.validator;

public class ValidatorConstant {
	public static final String NAME_NULL_MESSAGE = "Name cannot be empty.";
	public static final String NAME_PATTERN_MESSAGE = "Invalid name format.";
	public static final String EMAIL_NULL_MESSAGE = "Email cannot be empty.";
	public static final String EMAIL_FORMAT_ERROR_MESSAGE = "Invalid email format.";
	public static final String SALARY_NULL_MESSAGE = "Salary cannot be empty.";
	public static final String SALARY_POSITIVE_MESSAGE = "Salary must be a positive number.";
	public static final String DATE_OF_JOINING_NULL_MESSAGE = "Date of joining cannot be empty.";
	public static final String DATE_FORMAT_ERROR_MESSAGE = "Invalid email format.";

	// Regex Patterns
	public static final String EMAIL_PATTERN = "^[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
	public static final String NAME_PATTERN = "^[A-Za-z\\s]+$";
	public static final String DATE_PATTERN = "^\\d{4}-\\d{2}-\\d{2}$";

	// Employee Validation Messages
	public static final String EMPLOYEE_ID_NULL_MESSAGE = "Employee ID cannot be null.";
	public static final String EMPLOYEE_ID_POSITIVE_MESSAGE = "Employee ID must be a positive value.";
	public static final String EMPLOYEE_ALREADY_EXISTS = "Employee with this email already exists.";
	public static final String EMPLOYEE_NAME_SIZE_MESSAGE = "Employee name must be between 3 and 50 characters.";
	public static final String EMPLOYEE_EMAIL_SIZE_MESSAGE = "Employee name must be between 3 and 50 characters.";

	public static final String EMPLOYEE_NOT_FOUND = "Employee with ID '%d' not found.";
	public static final String EMPLOYEE_PAGE_NOT_FOUND = "No employees found for the given page and size.";
	public static final String PERCENTAGE_ERROR_MESSAGE = "Percentage must be at least 1.";

	public static final String EMPLOYEE_TRANSFER_ERROR = "Error occurred while transferring the employee.";
	public static final String EMPLOYEE_ASSIGN_ERROR = "Failed to assign employee to department.";

	// Department Validation Messages
	public static final String DEPARTMENT_ID_NULL_MESSAGE = "Department ID cannot be null.";
	public static final String DEPARTMENT_ID_POSITIVE_MESSAGE = "Department ID must be a positive value.";
	public static final String DEPARTMENT_NAME_NULL_MESSAGE = "Department name cannot be empty.";
	public static final String DEPARTMENT_NAME_SIZE_MESSAGE = "Department name must be between 3 and 50 characters.";
	public static final String DEPARTMENT_LOCATION_NULL_MESSAGE = "Location cannot be empty.";
	public static final String DEPARTMENT_LOCATION_SIZE_MESSAGE = "Location must be between 3 and 50 characters.";
	public static final String DUPLICATE_DEPARTMENT = "Department name is already in use.";
	public static final String DEPARTMENT_NOT_FOUND = "Department with ID '%d' not found.";
	public static final String NO_DEPARTMENTS_FOUND = "No departments found for the given page and size.";
	public static final String NO_EMPLOYEE_COUNT_FOUND = "No departments found with employee count.";
	public static final String EMPLOYEE_ALREADY_IN_DESTINATION_DEPARTMENT = "Employee is already in the destination department.";
	public static final String EMPLOYEE_NOT_IN_SOURCE_DEPARTMENT = "Employee is not in the source department.";

	// Error Messages
	public static final String INTERNAL_ERROR_SAVE = "Failed to save : ";
	public static final String INTERNAL_ERROR_DELETE = "Failed to delete : ";
	public static final String INTERNAL_ERROR_UPDATE = "Failed to update Salary: ";
	public static final String INTERNAL_ERROR_FETCH = "Error occurred while fetching data.";
	public static final String INTERNAL_ERROR_TRANSFER = "An error occurred while transferring employee.";

	// Pagination & Input Validation
	public static final String PAGE_MUST_BE_POSITIVE = "Page number must be greater than or equal to 0.";
	public static final String SIZE_MUST_BE_POSITIVE = "Page size must be greater than 0.";

	public static final String NO_EMPLOYEES_JOINED_LAST_6_MONTHS = "No employees found who joined in the last 6 months.";
	public static final String NO_EMPLOYEES_FOUND_SALARY_GREATER_THAN = "No employees found with salary greater than ";
	public static final String AT_LEAST_ONE_DEPARTMENT_ASSIGNED = "At least one department must be assigned.";

}
