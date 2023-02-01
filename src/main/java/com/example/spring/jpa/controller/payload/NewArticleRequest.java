package com.example.spring.jpa.controller.payload;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonRootName;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;

@Getter
@JsonRootName("article")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode //The equal needs for test see: https://stackoverflow.com/questions/26680678/mockito-thenreturn-always-returns-null-object
public class NewArticleRequest {
	@NotBlank(message = "can't be empty")
	// @DuplicatedArticleConstraint
	private String title;

	@NotBlank(message = "can't be empty")
	private String description;

	@NotBlank(message = "can't be empty")
	private String body;

	@Singular
	private List<String> tags;
}
