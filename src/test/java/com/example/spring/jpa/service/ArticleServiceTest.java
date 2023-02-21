package com.example.spring.jpa.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.spring.jpa.model.Article;
import com.example.spring.jpa.model.Tag;
import com.example.spring.jpa.model.User;
import com.example.spring.jpa.repository.ArticleRepository;
import com.example.spring.jpa.repository.TagRepository;
import com.example.spring.jpa.repository.UserRepository;
import com.example.spring.jpa.testdata.NewArticleRequestTestData;
import com.example.spring.jpa.testdata.RoleTestData;
import com.example.spring.jpa.testdata.UserTestData;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
	
	@Mock
	ArticleRepository articleRepo;
	
	@Mock
	TagRepository tagRepo;
	
	@Mock
	UserRepository userRepo;
	
	@InjectMocks
	ArticleService articleService;
		
	@Test
	void should_not_save_when_tag_exist(){
		// Arrange
		User user = UserTestData.aSavedUser().build();
		when(articleRepo.save(any (Article.class) )).thenReturn(new Article());
		when(tagRepo.findByName("Java")).thenReturn(new Tag());
		when(userRepo.findById(any(String.class) )).thenReturn( Optional.of( user ));
		
		// Act		
		final var newArticleRequest = NewArticleRequestTestData.aNewArticleRequest()
										.tag("Java")
										.build();
		articleService.createArticle(newArticleRequest, user );
		
		// Assert
		 verify(tagRepo, times(0)).save(any(Tag.class));
		 verifyNoMoreInteractions(tagRepo);
	}
	
	@Test
	void should_save_only_new_tags(){
		// Arrange
		User user = UserTestData.aSavedUser().build();
		when(articleRepo.save(any (Article.class) )).thenReturn(new Article());
		when(tagRepo.findByName("Java")).thenReturn(new Tag());
		when(tagRepo.findByName("Struts")).thenReturn(null);
		when(userRepo.findByUsername("alireza")).thenReturn(new User());
		
		// Act
		final var newArticleRequest = NewArticleRequestTestData.aNewArticleRequest()
										.tag("Java")
										.tag("Struts")
										.build();
		
		articleService.createArticle(newArticleRequest,user);
		
		// Assert
		 verify(tagRepo, times(1)).save(any(Tag.class));
		 verifyNoMoreInteractions(tagRepo);
	}

}
