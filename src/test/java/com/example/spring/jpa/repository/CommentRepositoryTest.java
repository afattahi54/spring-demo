package com.example.spring.jpa.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;

import com.example.spring.jpa.model.Article;
import com.example.spring.jpa.model.Comment;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentRepositoryTest {

	@Autowired
	CommentRepository commentRepo;

	@Autowired
	ArticleRepository articleRepo;
	
	@Autowired
	TagRepository tagRepo;
	

	@Test
	void should_find_no_comment_if_repository_is_empty() {
		var allComments = commentRepo.findAll();
		assertThat(allComments).isEmpty();
	}

	@Commit
	@Test
	void should_add_comment_for_article() {
		var artilce = new Article("New Title 1","New Desc 1","New Body 1", Arrays.asList(  tagRepo.findByName("Java") ));
		var savedArticle = articleRepo.save(artilce);
		var comment = new Comment("Intersting article", savedArticle);
		var savedComment = commentRepo.save(comment);

		assertThat(savedComment)
					.extracting( Comment::getName, c -> c.getArticle().getTitle() , c -> c.getArticle().getBody())
					.containsExactly("Intersting article","New Title 1","New Body 1");		
	}

}
