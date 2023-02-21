package com.example.spring.jpa.api;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.spring.jpa.config.CustomUserDetailsService;
import com.example.spring.jpa.config.DefaultJwtService;
import com.example.spring.jpa.model.User;
import com.example.spring.jpa.repository.UserRepository;
import com.example.spring.jpa.testdata.UserTestData;

abstract class TestWithCurrentUser {

	@MockBean
	protected UserRepository userRepository;

	@MockBean
	CustomUserDetailsService customUserDetailsService;

	@MockBean
	protected DefaultJwtService jwtService;

	protected User user;
	protected String token;

	void fixuser() {
		User user = UserTestData.aSavedUser().build();

		when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
		token = "aToken";
		when(jwtService.getSubFromToken(token)).thenReturn(Optional.of( String.valueOf( user.getId()) ));

		//when(customUserDetailsService.loadUserById("111-222")).thenReturn(user);

	}

	@BeforeEach
	public void setUp() {
		fixuser();
	}
}
