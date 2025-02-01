package com.employee.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProblemDetails {

	private String title;

	private Integer status;

	private String detail;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date timeStamp = new Date();

	public ProblemDetails(Integer statusCode, String detail) {
		this.status = statusCode;
		this.title = HttpStatus.valueOf(statusCode.intValue()).getReasonPhrase();
		this.detail = detail;

	}

	public static ProblemDetails forBadRequest(String detail) {
		return getProblemForStatusCode(HttpStatus.BAD_REQUEST, detail);
	}

	public static ProblemDetails forInternalError(String detail) {
		return getProblemForStatusCode(HttpStatus.INTERNAL_SERVER_ERROR, detail);
	}

	public static ProblemDetails forConflict(String detail) {
		return getProblemForStatusCode(HttpStatus.CONFLICT, detail);
	}

	public static ProblemDetails forNotFound(String detail) {
		return getProblemForStatusCode(HttpStatus.NOT_FOUND, detail);
	}

	private static ProblemDetails getProblemForStatusCode(HttpStatus httpStatus, String detail) {
		ProblemDetails prob = new ProblemDetails();
		prob.setStatus(Integer.valueOf(httpStatus.value()));
		prob.setTitle(httpStatus.getReasonPhrase());
		prob.setDetail(detail != null ? detail : httpStatus.getReasonPhrase());
		return prob;
	}

}
