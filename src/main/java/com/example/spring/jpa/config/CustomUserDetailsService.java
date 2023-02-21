package com.example.spring.jpa.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.example.spring.jpa.model.Privilege;
import com.example.spring.jpa.model.Role;
import com.example.spring.jpa.model.User;
import com.example.spring.jpa.repository.RoleRepository;
import com.example.spring.jpa.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String usernameOrEmail) {
		// Let people login with either username or email
		// User user = userRepository.findByUsername(usernameOrEmail);

		return null;
	}

	@Transactional
    public UserDetails loadUserById(String id) {
		
		
        User user = userRepository.findById(id).orElseThrow();

        return new org.springframework.security.core.userdetails.User(
        		user.getEmail(), user.getPassword(), true, true, true, 
        			true, getAuthorities(user.getRoles()));
    }

	private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {

		return getGrantedAuthorities(getPrivileges(roles));
	}

	private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (String privilege : privileges) {
			authorities.add(new SimpleGrantedAuthority(privilege));
		}
		return authorities;
	}

	private List<String> getPrivileges(Collection<Role> roles) {

		List<String> privileges = new ArrayList<>();
		List<Privilege> collection = new ArrayList<>();
		for (Role role : roles) {
			privileges.add(role.getName());
			collection.addAll(role.getPrivileges());
		}
		for (Privilege item : collection) {
			privileges.add(item.getName());
		}
		return privileges;
	}
}