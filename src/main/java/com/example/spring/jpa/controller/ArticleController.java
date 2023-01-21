package com.example.spring.jpa.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.jpa.model.Article;
import com.example.spring.jpa.repository.ArticleRepository;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {
	
	@Autowired
	ArticleRepository articleRepository;
	
	@PostMapping("/create")
	public ResponseEntity<Article> createArticle(@RequestBody Article article) {
		try {
			Article newArticle = articleRepository
					.save(new Article(article.getTitle(), article.getName()));
			return new ResponseEntity<>(newArticle, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
