package com.example.spring.jpa.testdata;


import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.spring.jpa.config.UserPrincipal;


public class UserPrincipalTestData {
	
	public static UserPrincipal.UserPrincipalBuilder aUserProncipal(){
		return UserPrincipal.builder()
				.id("111-222")
				.username("afattahi")
				.email("afattahi@yahoo.com")
				.password("123456")
				.authority(  new SimpleGrantedAuthority("ROLE_USER"));
	}
	
}
