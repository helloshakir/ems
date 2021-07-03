package com.paypal.bfs.test.employeeserv.dao;

import static com.paypal.bfs.test.employeeserv.util.EmpUtil.toUpper;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "ADDRESS")
public class AddressEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String line1;
	private String line2;
	private String city;
	private String state;
	private String country;
	private String zipCode;

	@Override
	public String toString() {
		return "AddressEntity [line1=" + toUpper(line1) + ", city=" + toUpper(city) + ", state=" + toUpper(state)
				+ ", country=" + toUpper(country) + ", zipCode=" + toUpper(zipCode) + "]";
	}

}
