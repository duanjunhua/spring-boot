package com.michael.jpa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.michael.jpa.entity.Employee;
import com.michael.jpa.service.EmployeeService;

@RestController
@RequestMapping("/jpa-emp")
public class JpaEmpController {

	@Autowired
	private EmployeeService empService;
	
	@GetMapping("/listEmpByJpa")
	public List<Employee> listEmpByJpa(){
		/*[ {
			  "id" : 1,
			  "name" : "Michael",
			  "age" : 26
			}, {
			  "id" : 2,
			  "name" : "Kay",
			  "age" : 29
			}, {
			  "id" : 3,
			  "name" : "Nono",
			  "age" : 35
			} ]
		 */
		return empService.listEmpByJpa();
	}
	
	@GetMapping("/getEmpByQuery/{name}")
	public Employee getEmpByQuery(@PathVariable String name){
		return empService.getEmpByQuery(name);
	}
	
	@GetMapping("/getEmpByNativeQuery/{id}")
	public Employee getEmpByNativeQuery(@PathVariable int id){
		return empService.getEmpByNativeQuery(id);
	}
	
	@GetMapping("/getEmpByPrototype")
	public List<Employee> getEmpByPrototype(){
		return empService.getEmpByPrototype();
	}
}
