package com.employee.exception;

public class EmployeeSystemException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String message;
	private final ProblemDetails problemDetails;

	public EmployeeSystemException(String message) {
		super(message);
		this.message = message;
		this.problemDetails = new ProblemDetails();
	}

	public EmployeeSystemException(ProblemDetails problemDetails) {
		super(problemDetails.getDetail());
		this.message = "";
		this.problemDetails = problemDetails;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public ProblemDetails getProblemDetails() {
		return problemDetails;
	}

	public static class EmployeeNotFoundException extends EmployeeSystemException {

		private static final long serialVersionUID = 2L;

		public EmployeeNotFoundException(String message) {
			super(message);
		}

		public EmployeeNotFoundException(ProblemDetails problemDetails) {
			super(problemDetails);
		}
	}

	public static class DuplicateDepartmentException extends EmployeeSystemException {

		private static final long serialVersionUID = 3L;

		public DuplicateDepartmentException(String message) {
			super(message);
		}

		public DuplicateDepartmentException(ProblemDetails problemDetails) {
			super(problemDetails);
		}
	}

	public static class DepartmentNotFoundException extends EmployeeSystemException {

		private static final long serialVersionUID = 4L;

		public DepartmentNotFoundException(String message) {
			super(message);
		}

		public DepartmentNotFoundException(ProblemDetails problemDetails) {
			super(problemDetails);
		}
	}

	public static class InvalidRequestException extends EmployeeSystemException {

		private static final long serialVersionUID = 5L;

		public InvalidRequestException(String message) {
			super(message);
		}

		public InvalidRequestException(ProblemDetails problemDetails) {
			super(problemDetails);
		}
	}

	public static class InternalServerErrorException extends EmployeeSystemException {

		private static final long serialVersionUID = 6L;

		public InternalServerErrorException(String message) {
			super(message);
		}

		public InternalServerErrorException(ProblemDetails problemDetails) {
			super(problemDetails);
		}
	}

	public static class DuplicateEmployeeException extends EmployeeSystemException {

		private static final long serialVersionUID = 7L;

		public DuplicateEmployeeException(String message) {
			super(message);
		}

		public DuplicateEmployeeException(ProblemDetails problemDetails) {
			super(problemDetails);
		}
	}

}
