package com.example.spring.jpa.repository;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;

import com.example.spring.jpa.model.User;

@DataJpaTest(properties = { "spring.datasource.url=jdbc:h2:mem:testdb" })
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

	@Autowired
	ArticleRepository articleRepo;

	@Autowired
	TagRepository tagRepo;

	@Autowired
	UserRepository userRepo;

	
	@AfterAll
	public static void finalizeTest() throws Exception  {
		System.out.println("Just put a break point here");
	}


	@Test
	@Commit
	void should_store_a_user() {		
		var newUser = User.builder()
				.email("afattahi@yahoo.com")
				.username("afattahi")
				.password("123456")
				.build();
		
		var savedUser = userRepo.save(newUser);	
		assertThat(savedUser).extracting(User::getUsername , User::getEmail, User::getPassword )
								.containsExactly("afattahi@yahoo.com","afattahi","123456" );
	}

		

}
