package com.paypal.bfs.test.employeeserv.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.exception.InvalidResourceIdentifierException;
import com.paypal.bfs.test.employeeserv.exception.ResourceNotFoundException;
import com.paypal.bfs.test.employeeserv.service.IEmpService;

@WebMvcTest(EmployeeResource.class)
class EmployeeResourceImplTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private IEmpService empService;

	@Test
	void testEmployeeGetById() throws Exception {
		Employee emp = new Employee();
		emp.setFirstName("First");emp.setLastName("Last");
		emp.setDateOfBirth("2000-05-19");emp.setAddress(new Address());
		
		BDDMockito.given(empService.getEmpById("1")).willReturn(emp);
		
		RequestBuilder request = MockMvcRequestBuilders.get("/v1/bfs/employees/1");
		MvcResult mvcResult = mvc.perform(request).andReturn();
		
		MockHttpServletResponse response = mvcResult.getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		String respText = response.getContentAsString();
		System.out.println(respText);
		assertThat(respText).contains("2000-05-19");
	}
	
	@Test
	void testGetEmpIdDoesNotExist() throws Exception {
		BDDMockito.given(empService.getEmpById("1")).willThrow(ResourceNotFoundException.class);
		
		RequestBuilder request = MockMvcRequestBuilders.get("/v1/bfs/employees/1");
		MockHttpServletResponse response = mvc.perform(request).andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	void testGetEmpIdNotInteger() throws Exception {
		BDDMockito.given(empService.getEmpById("1")).willThrow(InvalidResourceIdentifierException.class);
		
		RequestBuilder request = MockMvcRequestBuilders.get("/v1/bfs/employees/1");
		MockHttpServletResponse response = mvc.perform(request).andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	void testAddEmployee() {
		
	}

}
