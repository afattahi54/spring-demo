package com.example.spring.jpa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @RequiredArgsConstructor
@Entity
@ToString
@SequenceGenerator(name = "id_gen", sequenceName = "id_gen",  initialValue = 100)
public class Article {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "id_gen")
	private long id;
	@NonNull
	private String name;
	@NonNull
	private String title;
}
