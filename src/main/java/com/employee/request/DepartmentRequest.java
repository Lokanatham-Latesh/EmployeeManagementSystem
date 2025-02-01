package com.employee.request;

import org.springframework.validation.annotation.Validated;

import com.employee.validator.ValidatorConstant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Validated

public class DepartmentRequest {
	@NotBlank(message = ValidatorConstant.DEPARTMENT_NAME_NULL_MESSAGE)
	@Size(min = 2, max = 100, message = ValidatorConstant.DEPARTMENT_NAME_SIZE_MESSAGE)
	private String name;

	@NotBlank(message = ValidatorConstant.DEPARTMENT_LOCATION_NULL_MESSAGE)
	@Size(min = 2, max = 100, message = ValidatorConstant.DEPARTMENT_LOCATION_SIZE_MESSAGE)
	private String location;

}
