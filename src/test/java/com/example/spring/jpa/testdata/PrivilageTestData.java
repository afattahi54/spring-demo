package com.example.spring.jpa.testdata;

import com.example.spring.jpa.model.Privilege;

public class PrivilageTestData {
	
	public static Privilege.PrivilegeBuilder aPrivilege(){
		return Privilege.builder()
				.id("111-222")
				.name("READ");			
	}
	
}
