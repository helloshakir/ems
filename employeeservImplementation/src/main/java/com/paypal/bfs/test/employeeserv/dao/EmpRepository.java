package com.paypal.bfs.test.employeeserv.dao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpRepository extends JpaRepository<EmployeeEntity, Integer>{
	
	EmployeeEntity findByUuid(String uuid);
}
