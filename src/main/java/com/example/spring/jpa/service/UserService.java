package com.example.spring.jpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.spring.jpa.controller.payload.NewUserRequest;
import com.example.spring.jpa.model.User;
import com.example.spring.jpa.repository.UserRepository;

@Service
public class UserService {


	@Autowired
	UserRepository userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public User createUser(NewUserRequest newUserRequest) {
	
		var user =	User.builder()
					.email( newUserRequest.getEmail())
					.password( passwordEncoder.encode(newUserRequest.getPassword()) )
					.username(  newUserRequest.getUsername()).build();
		
		return userRepo.save(user);
	}
}
