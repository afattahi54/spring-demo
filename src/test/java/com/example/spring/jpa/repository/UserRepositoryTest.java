package com.example.spring.jpa.repository;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;

import com.example.spring.jpa.model.User;
import com.example.spring.jpa.testdata.UserTestData;

@DataJpaTest(properties = { "spring.datasource.url=jdbc:h2:mem:testdb" })
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

	@Autowired
	UserRepository userRepo;

	
	@Test
	@Commit
	void should_store_a_user() {		
		var newUser = UserTestData.aUser().build();		
		var savedUser = userRepo.save(newUser);	
		assertThat(savedUser).extracting(User::getUsername , User::getEmail, User::getPassword )
								.containsExactly(newUser.getUsername(),newUser.getEmail(), newUser.getPassword() );
	}

	@Test
	@Commit
	void should_store_a_user_with_role() {		
		var newUser = UserTestData.aUserWithUserRole().build();		
		var savedUser = userRepo.save(newUser);	
		assertThat(savedUser).extracting(User::getUsername , User::getEmail, User::getPassword , User::getRoles)
								.containsExactly(newUser.getUsername(),newUser.getEmail(), newUser.getPassword(), newUser.getRoles() );
	}


}
