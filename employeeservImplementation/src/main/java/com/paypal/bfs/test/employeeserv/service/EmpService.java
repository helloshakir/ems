package com.paypal.bfs.test.employeeserv.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.dao.EmpRepository;
import com.paypal.bfs.test.employeeserv.dao.EmployeeEntity;
import com.paypal.bfs.test.employeeserv.exception.InvalidResourceIdentifierException;
import com.paypal.bfs.test.employeeserv.exception.ResourceCreationException;
import com.paypal.bfs.test.employeeserv.exception.ResourceNotFoundException;
import com.paypal.bfs.test.employeeserv.util.EmpModelMapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
@Service
public class EmpService implements IEmpService {

	private final EmpRepository empRepository;

	private final EmpModelMapper empModelMapper;

	@Override
	public Employee getEmpById(String empId) {
		Integer employeeId = praseEmpId(empId);

		Optional<EmployeeEntity> empEntity = empRepository.findById(employeeId);
		log.info("Data retrieved from database - {}", empEntity);

		if (empEntity.isPresent()) {
			return empModelMapper.toBean(empEntity.get());
		}

		throw new ResourceNotFoundException("Employee with id " + empId + " does not exist.");
	}

	private Integer praseEmpId(String empId) {
		try {
			return Integer.parseInt(empId);
		} catch (NumberFormatException e) {
			throw new InvalidResourceIdentifierException("Employee Id must be integer.");
		}
	}

	@Override
	public Employee addEmployee(Employee emp) {
		EmployeeEntity empEntity = empModelMapper.toEntity(emp);
		try {
			EmployeeEntity existingEmpEntity = empRepository.findByHashId(empEntity.getHashId());
			if (existingEmpEntity == null) {
				existingEmpEntity = empRepository.save(empEntity);
			}

			return empModelMapper.toBean(existingEmpEntity);
		} catch (Exception e) {
			throw new ResourceCreationException("Unable to add employee. Error:" + e.getMessage());
		}
	}

}
