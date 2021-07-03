package com.paypal.bfs.test.employeeserv.util;

import java.util.UUID;

public class EmpUtil {
	
	public static String toUpper(String text) {
		return text!=null ? text.toUpperCase() : "";
	}

	public static String getUuid(Object obj) {
		return UUID.nameUUIDFromBytes(obj.toString().getBytes()).toString();
	}
}
