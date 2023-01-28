package com.example.spring.jpa.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @RequiredArgsConstructor @NoArgsConstructor
@Entity
@ToString
public class Article {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
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
	
	@ManyToMany //https://stackoverflow.com/questions/8804572/hibernate-onetomany-and-unique-constraint/75266290#75266290
	@NonNull
	private List<Tag> tags;
}
