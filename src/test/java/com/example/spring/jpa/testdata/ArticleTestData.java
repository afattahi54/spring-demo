package com.example.spring.jpa.testdata;

import com.example.spring.jpa.model.Article;

/**
 * refer to https://www.arhohuttunen.com/test-data-builders/
 * @author A_Fattahi
 *
 */
public class ArticleTestData {
	public static Article.ArticleBuilder anArticle(){
		return Article.builder()
				.title("Article Title")
				.body("Article Body")
				.description("Article Descroption")
				.tag( TagTestData.aTag().build())
				.user(UserTestData.aUser().build());				
	}
	public static Article.ArticleBuilder anArticle(String tagname){
		return anArticle()
				.clearTags()
				.tag( TagTestData.aTag(tagname).build());
	}
}
