package com.example.spring.jpa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.spring.jpa.controller.payload.NewArticleRequest;
import com.example.spring.jpa.model.Article;
import com.example.spring.jpa.model.Tag;
import com.example.spring.jpa.repository.ArticleRepository;
import com.example.spring.jpa.repository.TagRepository;

@Service
public class ArticleService {

	private ArticleRepository articleRepo;
	private TagRepository tagRepo;

	public Article createArticle(NewArticleRequest newArticleRequest) {
	
		List<Tag> articleTags = new ArrayList<Tag>();
		
		for ( String tag : newArticleRequest.getTagList() ) {
			Tag artcileTag =Optional.ofNullable( tagRepo.findByName(tag))
					.orElseGet( () -> {
						Tag newTag = new Tag(tag); 
						return tagRepo.save(newTag);
					});
			articleTags.add(artcileTag);
		};
			
		Article article = new Article(
				newArticleRequest.getTitle(), 
				newArticleRequest.getDescription(),
				newArticleRequest.getBody(), 
				articleTags);
		articleRepo.save(article);
		return article;
	}
}
