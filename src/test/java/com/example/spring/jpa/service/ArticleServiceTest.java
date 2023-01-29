package com.example.spring.jpa.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.spring.jpa.controller.payload.NewArticleRequest;
import com.example.spring.jpa.model.Article;
import com.example.spring.jpa.model.Tag;
import com.example.spring.jpa.repository.ArticleRepository;
import com.example.spring.jpa.repository.TagRepository;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
	
	@Mock
	ArticleRepository articleRepo;
	
	@Mock
	TagRepository tagRepo;
	
	@InjectMocks
	ArticleService articleService;
		
	@Test
	void should_save_article_when_tag_exist(){
		final var tag = new Tag("Java");
		final var article = new Article("Spring data can save your life", "how?" ,"No more boilerplate", 
				Arrays.asList( tag ) );
		
		when(articleRepo.save(article) ).thenReturn(article );
		when(tagRepo.findByName("Java")).thenReturn(tag);
		
		final var newArticleRequest = new NewArticleRequest("Spring data can save your life", "how?" ,"No more boilerplate", 
				Arrays.asList( "Java") ) ;
		final var actulArtilce = articleService.createArticle(newArticleRequest);
		 assertThat(actulArtilce).usingRecursiveComparison().isEqualTo(article);
	}

}
