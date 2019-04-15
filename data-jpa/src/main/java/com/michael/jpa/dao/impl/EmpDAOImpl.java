package com.michael.jpa.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.michael.jpa.dao.EmpDAO;
import com.michael.jpa.entity.Employee;

public class EmpDAOImpl implements EmpDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> getAllEmp() {
		Query query = entityManager.createQuery("from Employee");
		return query.getResultList();
	}
}
