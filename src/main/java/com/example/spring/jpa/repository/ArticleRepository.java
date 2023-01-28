package com.example.spring.jpa.repository;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import com.example.spring.jpa.model.Article;

/**
 * The spring 3 has changed the repository interfaces, the new interfaces are added to make them return list. 
 * The sorting repository was also splitted into different interface
 * 
 * @see https://spring.io/blog/2022/02/22/announcing-listcrudrepository-friends-for-spring-data-3-0
 *  
 * @author A_Fattahi
 *
 */
public interface ArticleRepository extends ListCrudRepository<Article, Long> , ListPagingAndSortingRepository<Article, Long>{
	List<Article> findByTitleContaining(String name);
	
//	@Query("select a from Article u where u.name = :name")
//	UserEntity findByNameCustomQuery(@Param("name") String name);
}
