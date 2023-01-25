package com.example.spring.jpa.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

import com.example.spring.jpa.model.Tag;

public interface TagRepository extends ListCrudRepository<Tag, Long>, ListPagingAndSortingRepository<Tag, Long> {
	Tag findByName(String name);
}
