package com.employee.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Transactional;

import com.employee.repository.CustomEmployeeRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public class CustomEmployeeRepositoryImpl implements CustomEmployeeRepository {

	@Autowired
	private EntityManager entityManager;

	@Transactional
	@Override
	public void updateSalariesByPercentage(double percentage) {

		double multiplier = 1 + (percentage / 100);

		String jpql = "UPDATE Employee e SET e.salary = ROUND(e.salary * :multiplier, 2)";
		Query query = entityManager.createQuery(jpql);
		query.setParameter("multiplier", multiplier);

		query.executeUpdate();
	}

}
