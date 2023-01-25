package com.example.spring.jpa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@ToString
@SequenceGenerator(name = "id_generator", sequenceName = "id_generator", initialValue = 1)
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "id_generator")
	private long id;

	@NonNull
	private String name;

	@OneToOne
	@NonNull
	private Article article;
}
