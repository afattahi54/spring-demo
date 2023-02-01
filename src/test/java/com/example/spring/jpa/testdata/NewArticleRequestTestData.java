package com.example.spring.jpa.testdata;

import com.example.spring.jpa.controller.payload.NewArticleRequest;

public class NewArticleRequestTestData {
	public static NewArticleRequest.NewArticleRequestBuilder aNewArticleRequest(){
		return NewArticleRequest.builder()
				.body("new body")
				.title("new title")
				.description("new description");
	}
}
