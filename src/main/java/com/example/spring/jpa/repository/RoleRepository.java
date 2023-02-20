package com.example.spring.jpa.repository;

import com.example.spring.jpa.model.Role;

public interface RoleRepository extends ReadonlyListCrudRepository<Role, String> {
	Role findByName(String name);
}
