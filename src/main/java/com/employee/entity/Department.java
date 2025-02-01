package com.employee.entity;

import jakarta.persistence.*;

import lombok.Data;

import java.util.List;

@Entity
@Table(name = "DEPARTMENT")
@Data
public class Department {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DEPARTMENT_ID")
	private Long id;

	@Column(unique = true, name = "NAME" , length = 50)
	private String name;

	@Column(name = "LOCATION" , length = 50)
	private String location;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "DEPARTMENT_EMPLOYEE", joinColumns = @JoinColumn(name = "DEPARTMENT_ID"), inverseJoinColumns = @JoinColumn(name = "EMPLOYEE_ID"))
	private List<Employee> employees;
}
