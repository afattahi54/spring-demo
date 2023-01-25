package com.example.spring.jpa.service;

import org.springframework.stereotype.Service;

import com.example.spring.jpa.controller.payload.NewArticleRequest;
import com.example.spring.jpa.model.Article;
import com.example.spring.jpa.repository.ArticleRepository;
import com.example.spring.jpa.repository.TagRepository;

@Service
public class ArticleService {

	private ArticleRepository articleRepo;
	private TagRepository tagRepo;

	public Article createArticle(NewArticleRequest newArticleRequest) {
	
		Article article = new Article(
				newArticleRequest.getTitle(), 
				newArticleRequest.getDescription(),
				newArticleRequest.getBody(), 
				newArticleRequest.getTagList());
		articleRepo.save(article);
		return article;
	}
}
