package com.example.spring.jpa.testdata;


import com.example.spring.jpa.model.Comment;

public class CommentTestData {
	public static Comment.CommentBuilder aComment(){
		return Comment.builder()
				.name("comment name");
	}
	
}
