package com.example.spring.jpa;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.hamcrest.core.IsEqual.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import com.example.spring.jpa.controller.ArticlesApi;
import com.example.spring.jpa.service.ArticleService;
import com.example.spring.jpa.testdata.ArticleTestData;
import com.example.spring.jpa.testdata.NewArticleRequestTestData;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@WebMvcTest({ArticlesApi.class})
class ArticlesApiTest {

	@MockBean
	ArticleService articleService;

	@Autowired
	private MockMvc mvc;

	@BeforeEach
	public void setUp() {
		RestAssuredMockMvc.mockMvc(mvc);
	}

	@Test
	void should_save_article(){
		var newArticleRequest = NewArticleRequestTestData.aNewArticleRequest().build();
		var article = ArticleTestData.anArticle().build();

		when(articleService.createArticle(newArticleRequest)).thenReturn(article);
		
		given().
			contentType("application/json").
			body(newArticleRequest).
		when().
			post("/articles").			
		then().
		    status(HttpStatus.OK).
		    body("article.title", equalTo( article.getTitle()));
	}
}
