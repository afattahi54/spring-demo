package com.example.spring.jpa.controller.payload;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonRootName("article")
@NoArgsConstructor
@AllArgsConstructor
public class NewArticleRequest {
	// @NotBlank(message = "can't be empty")
	// @DuplicatedArticleConstraint
	private String title;

	// @NotBlank(message = "can't be empty")
	private String description;

	// @NotBlank(message = "can't be empty")
	private String body;

	private List<String> tagList;
}
