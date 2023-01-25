package com.example.spring.jpa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.h2.tools.Server;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import com.example.spring.jpa.model.Article;
import com.example.spring.jpa.repository.ArticleRepository;
import com.example.spring.jpa.repository.TagRepository;

import jakarta.persistence.PersistenceException;

@DataJpaTest(properties = { "spring.datasource.url=jdbc:h2:mem:testdb" })
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ArticleRepositoryTest {

	@Autowired
	ArticleRepository repository;

	@Autowired
	TagRepository tagRepo;
	
	@Autowired
	private TestEntityManager testEntityManager;

	@BeforeAll
	public static void initTest() throws Exception  {
		Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8091").start();
	}

	@Test
	void should_find_no_articles_if_repository_is_empty() {
		List<Article> allArticles = repository.findAll();
		assertThat(allArticles).isEmpty();
	}

	@Test
	void should_store_a_article() {		
		Article  newArticle = new Article("New Title","New Desc","New Body", Arrays.asList(  tagRepo.findByName("Java") ) );
		
		Article savedArticle = repository.save(newArticle);
		assertThat(savedArticle).extracting(Article::getTitle, Article::getDescription, Article::getBody, Article::getTags )
								.containsExactly("New Title","New Desc","New Body", new String[] { "Java" } );
	}

	@Test
	void should_find_all_articles() {
		Article artilce1 = new Article("New Title 1","New Desc 1","New Body 1", Arrays.asList(  tagRepo.findByName("Java") ) );
		repository.save(artilce1);

		Article article2 = new Article("New Title 1","New Desc 1","New Body 1", Arrays.asList(  tagRepo.findByName("Spring") ) );
		repository.save(article2);

		Article article3 = new Article("New Title 1","New Desc 1","New Body 1", Arrays.asList(  tagRepo.findByName("Struts") ) );
		repository.save(article3);

		List<Article> articles = repository.findAll();
		assertThat(articles).hasSize(3).contains(artilce1, article2, article3);
	}

	@Test
	void save_all_articles() {
		List<Article> artiles = Arrays.asList(
				new Article("New Title 1","New Desc 1","New Body 1", Arrays.asList(  tagRepo.findByName("Java") ) ),
				new Article("New Title 1","New Desc 1","New Body 1", Arrays.asList(  tagRepo.findByName("Spring") ) ), 
				new Article("New Title 1","New Desc 1","New Body 1", Arrays.asList(  tagRepo.findByName("Struts") ) )
			);

		List<Article> savedArticles = repository.saveAll(artiles);
		AtomicInteger validIdFound = new AtomicInteger();
		savedArticles.forEach(a -> {
			if (a.getId() > 0) {
				validIdFound.getAndIncrement();
			}
		});
		assertThat(validIdFound.intValue()).isEqualByComparingTo(3);

	}

	@Test
	void should_find_articles_by_name_containing_string() {
		Article article1 = new Article("How to train Spring?", "Ever wonder how?" ,"Try spring.io first", Arrays.asList(  tagRepo.findByName("Spring") ) );
		repository.save(article1);

		Article article2 = new Article("Is Java what you are looking for?", "May be other tech?" ,"Ofcourse it is none dying tech", Arrays.asList(  tagRepo.findByName("Java") ) );
		repository.save(article2);

		Article article3 = new Article("Spring data can save your life", "how?" ,"No more boilerplate", Arrays.asList(tagRepo.findByName("Spring") ) );
		repository.save(article3);

		Article article4 = new Article("playING with caps lock", "test case" ,"CAPS LOCK cOULD bEE fUNN", Arrays.asList(tagRepo.findByName("Spring") ) );;
		repository.save(article4);

		List<Article> articles = repository.findByNameContaining("ing");

		assertThat(articles).hasSize(2).contains(article1, article3);
	}

	@Test
	void should_not_save_articles_with_long_name() {
		Article article1 = new Article("I am a veryyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy long title", "name","body",Arrays.asList(tagRepo.findByName("Spring")) );
		repository.save(article1);
		// The save will not actually execute insert command. You may think of
		// annotating this method with @Commit
		// but the annotating will execute the flush in afterTestMethod method and the
		// below assert will not trigger
		assertThrows(PersistenceException.class, () -> testEntityManager.flush());
	}

	// @Commit
	@Test
	void should_find_article_by_id() {
		Article artilce1 = new Article("New Title 1","New Desc 1","New Body 1", Arrays.asList(  tagRepo.findByName("Java") ));
		repository.save(artilce1);

		Article article2 = new Article("New Title 2","New Desc 2","New Body 2", Arrays.asList(  tagRepo.findByName("Java") ));
		repository.save(article2);

		Article foundTutorial = repository.findById(article2.getId()).get();

		assertThat(foundTutorial).isEqualTo(article2);
	}

}
