package com.example.spring.jpa.testdata;

import com.example.spring.jpa.model.Role;


public class RoleTestData {
	
	public static Role.RoleBuilder aRole(){
		return Role.builder()
				.id("111-222")
				.name("ROLE_USER");				
	}
	
}
