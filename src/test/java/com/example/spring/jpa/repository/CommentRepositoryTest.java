package com.example.spring.jpa.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.example.spring.jpa.model.Comment;
import com.example.spring.jpa.testdata.ArticleTestData;
import com.example.spring.jpa.testdata.CommentTestData;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentRepositoryTest {

	@Autowired
	CommentRepository commentRepo;

	@Autowired
	ArticleRepository articleRepo;
		
	@Test
	void should_find_no_comment_if_repository_is_empty() {
		var allComments = commentRepo.findAll();
		assertThat(allComments).isEmpty();
	}

	@Test
	void should_add_comment_for_article() {		
		var savedArticle = articleRepo.save( ArticleTestData.anArticle().build() );
		var comment = CommentTestData.aComment()
				.article(savedArticle)
				.build();
		var savedComment = commentRepo.save(comment);

		assertThat(savedComment)
					.extracting( Comment::getName, c -> c.getArticle().getTitle())
					.containsExactly(comment.getName(),savedArticle.getTitle());		
	}

}
