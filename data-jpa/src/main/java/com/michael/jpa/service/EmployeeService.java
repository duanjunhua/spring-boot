package com.michael.jpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.michael.jpa.dao.EmployeeDAO;
import com.michael.jpa.entity.Employee;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeDAO employeeDAO;
	
	public List<Employee> listEmpByJpa(){
		return employeeDAO.findAll();
	}
	
	public Employee getEmpByQuery(String name){
		return employeeDAO.getEmpByName(name);
	}
	
	public Employee getEmpByNativeQuery(int id){
		return employeeDAO.getEmpById(id);
	}
	
	public List<Employee> getEmpByPrototype(){
		return employeeDAO.getAllEmp();
	}
}
