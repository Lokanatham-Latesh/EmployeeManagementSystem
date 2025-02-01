package com.employee.request;

import java.time.LocalDate;

import org.springframework.validation.annotation.Validated;

import com.employee.validator.ValidatorConstant;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Validated
public class EmployeeRequest {

	
	@NotBlank(message = ValidatorConstant.NAME_NULL_MESSAGE)
	@Pattern(regexp = ValidatorConstant.NAME_PATTERN, message = ValidatorConstant.NAME_PATTERN_MESSAGE)
	@Size(min = 3, max = 50 , message = ValidatorConstant.EMPLOYEE_NAME_SIZE_MESSAGE)
	private String name;

	@NotBlank(message = ValidatorConstant.EMAIL_NULL_MESSAGE)
	@Pattern(regexp = ValidatorConstant.EMAIL_PATTERN, message = ValidatorConstant.EMAIL_FORMAT_ERROR_MESSAGE)
	private String email;

	@NotNull(message = ValidatorConstant.SALARY_NULL_MESSAGE)
	@Positive(message = ValidatorConstant.SALARY_POSITIVE_MESSAGE)
	private Double salary;

	@NotNull(message = ValidatorConstant.DATE_OF_JOINING_NULL_MESSAGE) 
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate dateOfJoining;

}
