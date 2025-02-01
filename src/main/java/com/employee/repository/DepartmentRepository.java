package com.employee.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.employee.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

	
	
	  @Query("SELECT d.name, COUNT(e) FROM Department d LEFT JOIN d.employees e GROUP BY d.name")
	    List<Object[]> getEmployeeCountByDepartment();
}
