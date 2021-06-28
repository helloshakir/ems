package com.paypal.bfs.test.employeeserv.util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.dao.AddressEntity;
import com.paypal.bfs.test.employeeserv.dao.EmployeeEntity;
import com.paypal.bfs.test.employeeserv.exception.InputParseException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EmpModelMapper {
	
	private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	public EmployeeEntity toEntity(Employee emp) {
		LocalDate dob = parseDateOfBirth(emp);
		EmployeeEntity empEntity = new EmployeeEntity();
		BeanUtils.copyProperties(emp, empEntity, "dateOfBirth","address");
		empEntity.setDateOfBirth(dob);
		if(emp.getAddress() != null) {
			empEntity.setAddress(toEntity(emp.getAddress()));
		}
		
		return empEntity;
	}
	
	public AddressEntity toEntity(Address address) {
		AddressEntity addressEntity = new AddressEntity();
		BeanUtils.copyProperties(address, addressEntity);
		
		return addressEntity;
	}
	
	public Employee toBean(EmployeeEntity empEntity) {
		Employee emp = new Employee();
		BeanUtils.copyProperties(empEntity, emp, "dateOfBirth","address");
		emp.setDateOfBirth(formatDateOfBirth(empEntity));
		
		if(empEntity.getAddress() != null) {
			emp.setAddress(toBean(empEntity.getAddress()));
		}
		
		return emp;
	}
	
	public Address toBean(AddressEntity addressEntity) {
		Address address = new Address();
		BeanUtils.copyProperties(addressEntity, address);
		
		return address;
	}

	protected String formatDateOfBirth(EmployeeEntity emp) {
		try {
			return dateTimeFormatter.format(emp.getDateOfBirth());
		} catch (DateTimeException e) {
			log.error("Unable to format date of birth for employee:"+ emp, e);
		}
		
		return "";
	}

	protected LocalDate parseDateOfBirth(Employee emp) {
		try {
			return LocalDate.parse(emp.getDateOfBirth(), dateTimeFormatter);
		} catch (DateTimeParseException e) {
			log.error("Unable to parse date of birth for employee:"+ emp, e);
			throw new InputParseException("Invalid date of birth."+e.getMessage()+
			"Enter valid date in yyyy-MM-dd format");
		}
	}
}
