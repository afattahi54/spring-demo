package com.example.spring.jpa.repository;

import org.springframework.data.repository.ListPagingAndSortingRepository;

import com.example.spring.jpa.model.Comment;

public interface CommentRepository extends NoDeleteListCrudRepository<Comment, Long> , ListPagingAndSortingRepository<Comment, Long>{
	
}
