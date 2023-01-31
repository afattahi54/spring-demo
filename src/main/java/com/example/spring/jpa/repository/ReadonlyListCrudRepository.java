package com.example.spring.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;


@NoRepositoryBean
public interface ReadonlyListCrudRepository<T, ID> extends Repository<T, ID> {
	<S extends T> S save(S entity);	
	Optional<T> findById(ID id);
	boolean existsById(ID id);
	long count();
	//Iterable<T> findAll();
	//Iterable<T> findAllById(Iterable<ID> ids);
	//<S extends T> Iterable<S> saveAll(Iterable<S> entities);
	
	List<T> findAllById(Iterable<ID> ids);
	List<T> findAll();
	<S extends T> List<S> saveAll(Iterable<S> entities);
}
