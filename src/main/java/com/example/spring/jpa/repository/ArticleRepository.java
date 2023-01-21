package com.example.spring.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring.jpa.model.Article;


public interface ArticleRepository extends JpaRepository<Article, Long>{
	List<Article> findByTitleContaining(String title);
}
