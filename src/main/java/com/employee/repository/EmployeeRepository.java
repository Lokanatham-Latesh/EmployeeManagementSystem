package com.employee.repository;

import java.time.LocalDate;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.employee.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> ,CustomEmployeeRepository{


	 List<Employee> findBySalaryGreaterThan(Double salary);

	List<Employee> findByDateOfJoiningAfter(LocalDate sixMonthsAgo);
	
	 @Query(nativeQuery =  true,
			 value = "SELECT * FROM employee where salary >= (SELECT DISTINCT salary FROM  employee ORDER BY salary DESC LIMIT 1 OFFSET 2)")
	    List<Employee> getTop3HighestPaidEmployees();
}
