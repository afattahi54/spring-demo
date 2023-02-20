package com.example.spring.jpa.repository;

import com.example.spring.jpa.model.Privilege;

public interface PrivilegeRepository extends ReadonlyListCrudRepository<Privilege, String> {
	Privilege findByName(String name);
}
