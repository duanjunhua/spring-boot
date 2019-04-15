package com.michael.jpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.michael.jpa.entity.Employee;


public interface EmployeeDAO extends JpaRepository<Employee, Integer>, EmpDAO {

	@Query("select emp from Employee emp where emp.name = ?1")
	Employee getEmpByName(String name);
	
	@Query(value = "select * from EMP emp where emp.id = ?1", nativeQuery = true)
	Employee getEmpById(int id);
	
}
