package com.paypal.bfs.test.employeeserv.dao;

import static com.paypal.bfs.test.employeeserv.util.EmpUtil.toUpper;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "EMPLOYEE")
public class EmployeeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;

	// alternate id used to handle idempotent post request
	private String uuid;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id", referencedColumnName = "id")
	private AddressEntity address;

	@Override
	public String toString() {
		return "EmployeeEntity [firstName=" + toUpper(firstName) + ", lastName=" + toUpper(lastName) + ", dateOfBirth="
				+ dateOfBirth + ", address=" + address + "]";
	}

}
