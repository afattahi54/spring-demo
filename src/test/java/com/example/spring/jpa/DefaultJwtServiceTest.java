package com.example.spring.jpa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.spring.jpa.config.DefaultJwtService;
import com.example.spring.jpa.testdata.UserTestData;

class DefaultJwtServiceTest {

	private DefaultJwtService jwtService;

	@BeforeEach
	public void setUp() {
		jwtService = new DefaultJwtService("234545454545454544121234543211234543211234543211234543211234543211234543211234543213454321", 3600);
	}

	@Test
	void should_generate_valid_token() {
		var user = UserTestData.aUser().build();
		user.setId("111-222");
		String generatedToken = jwtService.toToken(user);
		assertThat(generatedToken).isNotBlank();
		
		var userId = jwtService.getSubFromToken(generatedToken);		
		
		assertThat(userId.get()).isEqualTo(user.getId());

	}
	
	  @Test
	  void should_get_null_with_wrong_jwt() {
	    Optional<String> optional = jwtService.getSubFromToken("aaaaa");
	    assertFalse(optional.isPresent());
	  }
}
