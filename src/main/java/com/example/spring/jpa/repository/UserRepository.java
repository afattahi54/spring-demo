package com.example.spring.jpa.repository;

import org.springframework.data.repository.ListPagingAndSortingRepository;

import com.example.spring.jpa.model.User;

public interface UserRepository extends 
									ReadonlyListCrudRepository<User, String> , 
									ListPagingAndSortingRepository<User, String>{
	
	//Not List<User> sure there is only one user
	User findByUsername(String username); 
}
