package com.employee.entity;

import java.time.LocalDate;

import java.util.List;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.ManyToMany;

import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "EMPLOYEE")
@Data
public class Employee {
	@Id
	@Column(name = "EMPLOYEE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NAME" , length = 50)
	private String name;

	@Column(name = "EMAIL", unique = true, length = 50)
	private String email;

	@Column(name = "SALARY")
	private Double salary;

	@Column(name = "DATE_OF_JOINING")
	private LocalDate dateOfJoining;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "employees")
	private List<Department> departments;
}
