package com.example.spring.jpa.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.jpa.controller.payload.NewArticleRequest;
import com.example.spring.jpa.model.Article;
import com.example.spring.jpa.model.User;
import com.example.spring.jpa.service.ArticleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/articles")
public class ArticlesApi {

	@Autowired
	ArticleService articleService;

	@PostMapping
	public ResponseEntity<?> createArticle(@Valid @RequestBody NewArticleRequest newArticleRequest,
			@AuthenticationPrincipal User user) {
		Article newArticle = articleService.createArticle(newArticleRequest, user);
		return ResponseEntity.ok(new HashMap<String, Object>() {
			private static final long serialVersionUID = -8763806509755934907L;

			{
				put("article",newArticle ) ;
			}
		});
	}

//	@GetMapping("/find/{id}")
//	public Article findArticle(@PathVariable("id") long id){
//		return articleRepository.findById(id).orElseThrow();
//		
//	}
//	
//	@DeleteMapping("/delete/{id}")
//	public ResponseEntity<Object> deleteArticle(@PathVariable("id") long id){
//		articleRepository.deleteById(id);
//		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//		
//	}
}
