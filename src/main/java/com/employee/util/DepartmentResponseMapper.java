package com.employee.util;

import com.employee.entity.Department;
import com.employee.response.DepartmentResponse;

public class DepartmentResponseMapper {
	  public static DepartmentResponse mapToDepartmentResponse(Department department) {
	        if (department == null) {
	            return null;  
	        }
	        
	      
	        return new DepartmentResponse(
	            department.getId(),
	            department.getName(),
	            department.getLocation()
	        );
	    }
}
