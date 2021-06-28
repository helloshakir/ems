package com.paypal.bfs.test.employeeserv.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class EmpRepositoryTest {
	
	@Autowired
	private EmpRepository empRepository;

	@Test
	void shouldCheckEmpCreation() {
		//given
		AddressEntity address = AddressEntity.builder().line1("Line1").line2("Line2").city("Pune")
		.state("Maharashtra").country("India").zipCode("411048").build();
		
		EmployeeEntity emp = EmployeeEntity.builder().firstName("First").lastName("Second")
		.dateOfBirth(LocalDate.of(1981, 04, 05))
		.address(address).build();
		
		//when
		EmployeeEntity savedEmp = empRepository.save(emp);
		
		//then
		assertThat(savedEmp.getId()).isNotNull();
		
		//when
		Optional<EmployeeEntity> empById = empRepository.findById(savedEmp.getId());
		//then
		assertTrue(empById.isPresent());
		assertThat(empById.get()).isEqualTo(savedEmp);
		
	}

}
