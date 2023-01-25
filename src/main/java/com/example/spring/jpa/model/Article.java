package com.example.spring.jpa.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @RequiredArgsConstructor @NoArgsConstructor
@Entity
@ToString
@SequenceGenerator(name = "id_gen", sequenceName = "id_gen",  initialValue = 100)
public class Article {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "id_gen")	
	private long id;
	
    @Column(length = 40)
	@NonNull
	private String title;
    private String userId;
	private String slug;
	@NonNull
	private String description;
	@NonNull
	private String body;
	
	
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	@OneToMany
	@JoinColumn
	private List<Comment> comments;
	@NonNull
	private List<Tag> tags;
}
