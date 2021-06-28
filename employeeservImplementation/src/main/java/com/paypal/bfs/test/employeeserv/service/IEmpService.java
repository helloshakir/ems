package com.paypal.bfs.test.employeeserv.service;

import com.paypal.bfs.test.employeeserv.api.model.Employee;

public interface IEmpService {
	Employee getEmpById(String empId);
	Employee addEmployee(Employee emp);
}
