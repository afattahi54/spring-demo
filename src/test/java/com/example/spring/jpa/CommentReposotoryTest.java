package com.example.spring.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.spring.jpa.model.Article;
import com.example.spring.jpa.model.Comment;
import com.example.spring.jpa.repository.ArticleRepository;
import com.example.spring.jpa.repository.CommentRepository;
import com.example.spring.jpa.repository.TagRepository;

@DataJpaTest
class CommentRepositoryTest {

	@Autowired
	CommentRepository commentRepo;

	@Autowired
	ArticleRepository articleRepo;
	
	@Autowired
	TagRepository tagRepo;
	

	@Test
	void should_find_no_comment_if_repository_is_empty() {
		List<Comment> allComments = commentRepo.findAll();
		assertThat(allComments).isEmpty();
	}

	@Test
	void should_add_comment_for_article() {
		Article artilce = new Article("New Title 1","New Desc 1","New Body 1", Arrays.asList(  tagRepo.findByName("Java") ));
		Article savedArticle = articleRepo.save(artilce);
		Comment comment = new Comment("Intersting article", savedArticle);
		Comment savedComment = commentRepo.save(comment);

		assertThat(savedComment)
					.extracting( Comment::getName, c -> c.getArticle().getTitle() , c -> c.getArticle().getBody())
					.containsExactly("Intersting article","New Title 1","New Body 1");		
	}

}
