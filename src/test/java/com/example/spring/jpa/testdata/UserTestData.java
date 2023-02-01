package com.example.spring.jpa.testdata;


import com.example.spring.jpa.model.User;

public class UserTestData {
	public static User.UserBuilder aUser(){
		return User.builder()
				.username("afattahi")
				.email("afattahi@yahoo.com")
				.password("123456");		
	}
	
	public static User.UserBuilder aCompleteUser(){
		return aUser()
				.bio("Developer")
				.image("mypic.jpg");
	}
}
