package com.paypal.bfs.test.employeeserv.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.service.IEmpService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation class for employee resource.
 */

@AllArgsConstructor
@Slf4j
@RestController
public class EmployeeResourceImpl implements EmployeeResource {
	
	private final IEmpService empService;

	@Validated
    @Override
    public ResponseEntity<Employee> employeeGetById(String empId) {
    	Employee emp = empService.getEmpById(empId);
    	log.info("Employee found for {} is {}" , empId, emp);
        return new ResponseEntity<>(emp , HttpStatus.OK);
    }

	@Override
	public ResponseEntity<Employee> addEmployee(Employee emp) {
		Employee addedEmp = empService.addEmployee(emp);
		log.info("New employee added {}" , addedEmp);
		return new ResponseEntity<>(addedEmp, HttpStatus.CREATED);
	}
}
