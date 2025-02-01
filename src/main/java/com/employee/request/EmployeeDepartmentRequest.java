package com.employee.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;

import com.employee.validator.ValidatorConstant;

import lombok.Data;

@Data
public class EmployeeDepartmentRequest {

	@NotNull(message = ValidatorConstant.EMPLOYEE_ID_NULL_MESSAGE)
	@Positive(message = ValidatorConstant.EMPLOYEE_ID_POSITIVE_MESSAGE)
	private Long employeeId;

	@NotEmpty(message = ValidatorConstant.DEPARTMENT_ID_NULL_MESSAGE)
	private List<@Positive(message = ValidatorConstant.DEPARTMENT_ID_POSITIVE_MESSAGE) Long> departmentIds;
}
