package com.example.spring.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.jpa.controller.payload.NewArticleRequest;
import com.example.spring.jpa.model.Article;


@RestController
@RequestMapping("/api/articles")
public class ArticleController {
	
	//@Autowired
	//ArticleServiceTest articleService;
	
//	@PostMapping("/create")
//	public ResponseEntity<Article> createArticle(@RequestBody NewArticleRequest newArticleRequest) {
//		try {
//			Article newArticle = articleService
//					.createArticle(newArticleRequest);
//			return new ResponseEntity<>(newArticle, HttpStatus.CREATED);
//		} catch (Exception e) {
//			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

//	@GetMapping("/find/{id}")
//	public Article findArticle(@PathVariable("id") long id){
//		return articleRepository.findById(id).orElseThrow();
//		
//	}
	
//	@DeleteMapping("/delete/{id}")
//	public ResponseEntity<Object> deleteArticle(@PathVariable("id") long id){
//		articleRepository.deleteById(id);
//		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//		
//	}
}
