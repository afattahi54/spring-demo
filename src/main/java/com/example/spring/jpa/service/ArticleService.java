package com.example.spring.jpa.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring.jpa.controller.payload.NewArticleRequest;
import com.example.spring.jpa.model.Article;
import com.example.spring.jpa.model.Tag;
import com.example.spring.jpa.repository.ArticleRepository;
import com.example.spring.jpa.repository.TagRepository;
import com.example.spring.jpa.repository.UserRepository;

@Service
public class ArticleService {

	@Autowired
	ArticleRepository articleRepo;

	@Autowired
	TagRepository tagRepo;
	
	@Autowired
	UserRepository userRepo;

	public Article createArticle(NewArticleRequest newArticleRequest) {
	
		var articleTags = new ArrayList<Tag>();
		
		for ( String tag : newArticleRequest.getTagList() ) {
			var artcileTag = Optional.ofNullable( tagRepo.findByName(tag))
					.orElseGet( () -> {
						var newTag = new Tag(tag); 
						return tagRepo.save(newTag);
					});
			articleTags.add(artcileTag);
		}
		
		var article = new Article(
				userRepo.findByUsername("alireza"),
				newArticleRequest.getTitle(), 
				newArticleRequest.getDescription(),
				newArticleRequest.getBody(), 
				articleTags);
		return articleRepo.save(article);
	}
}
