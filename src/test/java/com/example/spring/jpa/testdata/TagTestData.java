package com.example.spring.jpa.testdata;

import com.example.spring.jpa.model.Tag;

public class TagTestData {
	public static Tag.TagBuilder aTag(){
		return Tag.builder()
				.name("Java");				
	}
	
	public static Tag.TagBuilder aTag(String name){
		return Tag.builder()
				.name(name);				
	}
}
