package com.employee.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.employee.validator.ValidatorConstant;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Handles general EmployeeSystemException and provides an internal server error
	 * response.
	 *
	 * @param ex the EmployeeSystemException
	 * @return ResponseEntity containing ProblemDetails with HTTP status 500
	 *         (INTERNAL_SERVER_ERROR)
	 */

	@ExceptionHandler(EmployeeSystemException.class)
	public ResponseEntity<ProblemDetails> handleEmployeeSystemException(EmployeeSystemException ex) {
		ProblemDetails problemDetails = ex.getProblemDetails();
		if (problemDetails == null) {
			problemDetails = new ProblemDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unknown error");
		}
		return new ResponseEntity<>(problemDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Handles EmployeeNotFoundException and returns a NOT_FOUND (404) response.
	 *
	 * @param ex the EmployeeNotFoundException
	 * @return ResponseEntity containing ProblemDetails with HTTP status 404
	 *         (NOT_FOUND)
	 */
	@ExceptionHandler(EmployeeSystemException.EmployeeNotFoundException.class)
	public ResponseEntity<ProblemDetails> handleEmployeeNotFoundException(
			EmployeeSystemException.EmployeeNotFoundException ex) {
		ProblemDetails problemDetails = ex.getProblemDetails();
		if (problemDetails == null) {
			problemDetails = new ProblemDetails(HttpStatus.NOT_FOUND.value(), ValidatorConstant.EMPLOYEE_NOT_FOUND);
		}
		return new ResponseEntity<>(problemDetails, HttpStatus.NOT_FOUND);
	}

	/**
	 * Handles DuplicateDepartmentException and returns a BAD_REQUEST (400)
	 * response.
	 *
	 * @param ex the DuplicateDepartmentException
	 * @return ResponseEntity containing ProblemDetails with HTTP status 400
	 *         (BAD_REQUEST)
	 */

	@ExceptionHandler(EmployeeSystemException.DuplicateDepartmentException.class)
	public ResponseEntity<ProblemDetails> handleDuplicateDepartmentException(
			EmployeeSystemException.DuplicateDepartmentException ex) {
		ProblemDetails problemDetails = ex.getProblemDetails();
		if (problemDetails == null) {
			problemDetails = new ProblemDetails(HttpStatus.BAD_REQUEST.value(), ValidatorConstant.DUPLICATE_DEPARTMENT);
		}
		return new ResponseEntity<>(problemDetails, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handles DepartmentNotFoundException and returns a NOT_FOUND (404) response.
	 *
	 * @param ex the DepartmentNotFoundException
	 * @return ResponseEntity containing ProblemDetails with HTTP status 404
	 *         (NOT_FOUND)
	 */

	@ExceptionHandler(EmployeeSystemException.DepartmentNotFoundException.class)
	public ResponseEntity<ProblemDetails> handleDepartmentNotFoundException(
			EmployeeSystemException.DepartmentNotFoundException ex) {
		ProblemDetails problemDetails = ex.getProblemDetails();
		if (problemDetails == null) {
			problemDetails = new ProblemDetails(HttpStatus.NOT_FOUND.value(), ValidatorConstant.DEPARTMENT_NOT_FOUND);
		}
		return new ResponseEntity<>(problemDetails, HttpStatus.NOT_FOUND);
	}

	/**
	 * Handles InvalidRequestException and returns a BAD_REQUEST (400) response.
	 *
	 * @param ex the InvalidRequestException
	 * @return ResponseEntity containing ProblemDetails with HTTP status 400
	 *         (BAD_REQUEST)
	 */

	@ExceptionHandler(EmployeeSystemException.InvalidRequestException.class)
	public ResponseEntity<ProblemDetails> handleInvalidRequestException(
			EmployeeSystemException.InvalidRequestException ex) {
		ProblemDetails problemDetails = ex.getProblemDetails();
		if (problemDetails == null) {
			problemDetails = new ProblemDetails(HttpStatus.BAD_REQUEST.value(), "Invalid request");
		}
		return new ResponseEntity<>(problemDetails, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handles InternalServerErrorException and returns a 500
	 * (INTERNAL_SERVER_ERROR) response.
	 *
	 * @param ex the InternalServerErrorException
	 * @return ResponseEntity containing ProblemDetails with HTTP status 500
	 *         (INTERNAL_SERVER_ERROR)
	 */

	@ExceptionHandler(EmployeeSystemException.InternalServerErrorException.class)
	public ResponseEntity<ProblemDetails> handleInternalServerErrorException(
			EmployeeSystemException.InternalServerErrorException ex) {
		ProblemDetails problemDetails = ex.getProblemDetails();
		if (problemDetails == null) {
			problemDetails = new ProblemDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error \t");
		}
		return new ResponseEntity<>(problemDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Handles validation exceptions for invalid method arguments.
	 *
	 * @param ex the MethodArgumentNotValidException
	 * @return ResponseEntity containing a map of validation errors with HTTP status
	 *         400 (BAD_REQUEST)
	 */

	@ExceptionHandler({ MethodArgumentNotValidException.class })
	public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, Object> response = new HashMap<>();
		response.put("title", "Validation Error");
		response.put("status", HttpStatus.BAD_REQUEST.value());
		Map<String, String> errors = new HashMap<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.put(error.getField(), error.getDefaultMessage());
		}
		response.put("errors", errors);
		response.put("timeStamp", new Date());

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handles all other general exceptions and returns an INTERNAL_SERVER_ERROR
	 * (500) response.
	 *
	 * @param ex the generic Exception
	 * @return ResponseEntity containing ProblemDetails with HTTP status 500
	 *         (INTERNAL_SERVER_ERROR)
	 */
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Map<String, Object>> handleInvalidFormatException(HttpMessageNotReadableException ex) {
	    Map<String, Object> response = new HashMap<>();
	    response.put("title", "Invalid Input Format");
	    response.put("status", HttpStatus.BAD_REQUEST.value());
	    response.put("message", "Invalid date format. Expected format: yyyy-MM-dd");
	    response.put("timeStamp", new Date());

	    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ProblemDetails> handleGenericException(Exception ex) {
		ProblemDetails problemDetails = new ProblemDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				"Unexpected error");
		return new ResponseEntity<>(problemDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
