package com.example.spring.jpa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@NoArgsConstructor @RequiredArgsConstructor
public class Tag {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	@NonNull
	private String name;
	
	@ManyToOne
	private Article article;
	
	
}
