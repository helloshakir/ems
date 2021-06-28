package com.paypal.bfs.test.employeeserv.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.dao.EmpRepository;
import com.paypal.bfs.test.employeeserv.dao.EmployeeEntity;
import com.paypal.bfs.test.employeeserv.exception.ResourceNotFoundException;
import com.paypal.bfs.test.employeeserv.util.EmpModelMapper;

class EmpServiceTest {

	@InjectMocks
	private EmpService empService;

	@Mock
	private EmpRepository empRepository;

	@Mock
	private EmpModelMapper empModelMapper;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testGetEmpByIdDoesNotExist() {
		String empId = "1";
		assertThatThrownBy(() -> empService.getEmpById(empId)).isInstanceOf(ResourceNotFoundException.class)
				.hasMessageContaining("Employee with id " + empId + " does not exist.");
	}

	@Test
	void testGetEmpByIdDoesExist() {
		// given
		Employee empBean = mock(Employee.class);
		EmployeeEntity empEntity = mock(EmployeeEntity.class);
		when(empRepository.findById(1)).thenReturn(Optional.ofNullable(empEntity));
		when(empModelMapper.toBean(empEntity)).thenReturn(empBean);

		// when
		Employee emp = empService.getEmpById("1");
		//then
		assertThat(emp).isEqualTo(empBean);
	}

	@Test
	void testAddEmployee() {
		Employee empBean = mock(Employee.class);
		EmployeeEntity empEntity = mock(EmployeeEntity.class);
		when(empModelMapper.toEntity(empBean)).thenReturn(empEntity);
		when(empModelMapper.toBean(empEntity)).thenReturn(empBean);
		when(empEntity.getId()).thenReturn(1);
		
		ArgumentCaptor<EmployeeEntity> empArgCaptor = ArgumentCaptor.forClass(EmployeeEntity.class);
		
		empService.addEmployee(empBean);
		
		verify(empRepository).save(empArgCaptor.capture());
		EmployeeEntity capturedValue = empArgCaptor.getValue();
		
		assertThat(capturedValue).isEqualTo(empEntity);
		
	}

}
