package com.example.spring.jpa.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.jpa.controller.payload.NewUserRequest;
import com.example.spring.jpa.model.User;
import com.example.spring.jpa.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UsersApi {

	@Autowired
	UserService userService;

	@PostMapping
	public ResponseEntity<?> createUser(@Valid @RequestBody NewUserRequest newUserRequest) {
		User newUser = userService.createUser(newUserRequest);
		return ResponseEntity.ok(new HashMap<String, Object>() {
			private static final long serialVersionUID = -8763806509755934907L;

			{
				put("user",newUser ) ;
			}
		});
	}
	

}
