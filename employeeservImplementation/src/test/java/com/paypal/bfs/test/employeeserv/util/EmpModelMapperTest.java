package com.paypal.bfs.test.employeeserv.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.dao.AddressEntity;
import com.paypal.bfs.test.employeeserv.dao.EmployeeEntity;
import com.paypal.bfs.test.employeeserv.exception.InputParseException;

class EmpModelMapperTest {
	
	private EmpModelMapper modelMapper;
	
	@BeforeEach
	void setUp() {
		modelMapper = new EmpModelMapper();
	}

	@Test
	void testToEntityEmployee() {
		//Given
		Address address = getSampleAddress();
		
		Employee emp = new Employee();
		emp.setFirstName("First");
		emp.setLastName("Last");
		emp.setDateOfBirth("1981-04-05");
		emp.setAddress(address);
		
		//when
		EmployeeEntity empEntity = modelMapper.toEntity(emp);
		
		//then
		assertThat(empEntity.getFirstName()).isEqualTo(emp.getFirstName());
		assertThat(empEntity.getLastName()).isEqualTo(emp.getLastName());
		assertThat(empEntity.getDateOfBirth().equals(LocalDate.of(1981, 4, 5)));
		assertThat(empEntity.getAddress().getCity()).isEqualTo("Pune");
		
	}
	
	@Test
	void testToEntityEmployeeWithInvalidDateOfBirth() {
		Employee emp = new Employee();
		emp.setFirstName("First");
		emp.setLastName("Last");
		emp.setDateOfBirth("invalid_date_of_birth");
		
		assertThatThrownBy(() -> modelMapper.toEntity(emp))
		.isInstanceOf(InputParseException.class)
		.hasMessageContaining("Invalid date of birth");
	}

	@Test
	void testToEntityAddress() {
		Address address = getSampleAddress();
		
		AddressEntity addressEntity = modelMapper.toEntity(address);
		
		assertThat(addressEntity).isNotNull();
		assertThat(addressEntity.getLine1()).isEqualTo(address.getLine1());
		assertThat(addressEntity.getLine2()).isEqualTo(address.getLine2());
		assertThat(addressEntity.getCity()).isEqualTo(address.getCity());
		assertThat(addressEntity.getState()).isEqualTo(address.getState());
		assertThat(addressEntity.getCountry()).isEqualTo(address.getCountry());
		assertThat(addressEntity.getZipCode()).isEqualTo(address.getZipCode());
	}

	@Test
	void testToEmployeeBean() {
		EmployeeEntity empEntity = new EmployeeEntity();
		empEntity.setFirstName("FirstName");empEntity.setLastName("LastName");
		empEntity.setDateOfBirth(LocalDate.of(2000, 10, 10));
		empEntity.setId(1);empEntity.setAddress(getSampleAddressEntity());
		
		Employee empBean = modelMapper.toBean(empEntity);
		assertThat(empBean.getFirstName()).isEqualTo(empEntity.getFirstName());
		assertThat(empBean.getLastName()).isEqualTo(empEntity.getLastName());
		assertThat(empBean.getDateOfBirth()).isEqualTo("2000-10-10");
		assertThat(empBean.getAddress().getCity()).isEqualTo(empEntity.getAddress().getCity());
	}

	@Test
	void testToAddressBean() {
		AddressEntity addressEntity = getSampleAddressEntity();
		
		Address address = modelMapper.toBean(addressEntity);
		
		assertThat(address).isNotNull();
		assertThat(address.getLine1()).isEqualTo(addressEntity.getLine1());
		assertThat(address.getLine2()).isEqualTo(addressEntity.getLine2());
		assertThat(address.getCity()).isEqualTo(addressEntity.getCity());
		assertThat(address.getState()).isEqualTo(addressEntity.getState());
		assertThat(address.getCountry()).isEqualTo(addressEntity.getCountry());
		assertThat(address.getZipCode()).isEqualTo(addressEntity.getZipCode());
	}
	
	private Address getSampleAddress() {
		Address address = new Address();
		address.setLine1("line1");address.setLine2("line2");
		address.setCity("Pune");address.setState("Maharashtra");
		address.setCountry("India");address.setZipCode("12345678");
		return address;
	}
	
	private AddressEntity getSampleAddressEntity() {
		AddressEntity addrEntity = new AddressEntity();
		addrEntity.setLine1("line1");addrEntity.setLine2("line2");
		addrEntity.setCity("Pune");addrEntity.setState("Maharashtra");
		addrEntity.setCountry("India");addrEntity.setZipCode("12345678");
		return addrEntity;
	}

}
