package com.example.spring.jpa.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import org.h2.tools.Server;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Commit;

import com.example.spring.jpa.model.Article;

import jakarta.persistence.PersistenceException;

@DataJpaTest(properties = { "spring.datasource.url=jdbc:h2:mem:testdb" })
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ArticleRepositoryTest {

	@Autowired
	ArticleRepository acticleRepo;

	@Autowired
	TagRepository tagRepo;
	
	@Autowired
	private TestEntityManager testEntityManager;

	@BeforeAll
	public static void initTest() throws Exception  {
		Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8092").start();
	}
	
	@AfterAll
	public static void finalizeTest() throws Exception  {
		System.out.print("Just put a break point here");
	}

	@Test
	void should_find_no_articles_if_repository_is_empty() {
		var allArticles = acticleRepo.findAll();
		assertThat(allArticles).isEmpty();
	}

	@Test
	@Commit
	void should_store_an_article() {		
		var newArticle = new Article("New Title","New Desc","New Body", Arrays.asList(  tagRepo.findByName("Java") ) );
		
		var savedArticle = acticleRepo.save(newArticle);	
		assertThat(savedArticle).extracting(Article::getTitle, Article::getDescription, Article::getBody )
								.containsExactly("New Title","New Desc","New Body" );
	}

	@Test
	void should_find_all_articles() {
		var artilce1 = new Article("New Title 1","New Desc 1","New Body 1", Arrays.asList(  tagRepo.findByName("Java") ) );
		acticleRepo.save(artilce1);

		var article2 = new Article("New Title 1","New Desc 1","New Body 1", Arrays.asList(  tagRepo.findByName("Spring") ) );
		acticleRepo.save(article2);

		var article3 = new Article("New Title 1","New Desc 1","New Body 1", Arrays.asList(  tagRepo.findByName("Struts") ) );
		acticleRepo.save(article3);

		var articles = acticleRepo.findAll();
		assertThat(articles).hasSize(3).contains(artilce1, article2, article3);
	}

	@Test
	void save_all_articles() {
		var artiles = Arrays.asList(
				new Article("New Title 1","New Desc 1","New Body 1", Arrays.asList(  tagRepo.findByName("Java") ) ),
				new Article("New Title 1","New Desc 1","New Body 1", Arrays.asList(  tagRepo.findByName("Spring") ) ), 
				new Article("New Title 1","New Desc 1","New Body 1", Arrays.asList(  tagRepo.findByName("Struts") ) )
			);

		var savedArticles = acticleRepo.saveAll(artiles);
		var validIdFound = new AtomicInteger();
		savedArticles.forEach(a -> {
			if (a.getId() > 0) {
				validIdFound.getAndIncrement();
			}
		});
		assertThat(validIdFound.intValue()).isEqualByComparingTo(3);

	}

	@Test
	void should_find_articles_by_title_containing_string() {
		var article1 = new Article("How to train Spring?", "Ever wonder how?" ,"Try spring.io first", Arrays.asList(  tagRepo.findByName("Spring") ) );
		acticleRepo.save(article1);

		var article2 = new Article("Will  java remain forever?", "May be other tech?" ,"Ofcourse it is none dying tech", Arrays.asList(  tagRepo.findByName("Java") ) );
		acticleRepo.save(article2);

		var article3 = new Article("Spring data can save your life", "how?" ,"No more boilerplate", Arrays.asList(tagRepo.findByName("Spring") ) );
		acticleRepo.save(article3);

		var article4 = new Article("playING with caps lock", "test case" ,"CAPS LOCK cOULD bEE fUNN", Arrays.asList(tagRepo.findByName("Spring") ) );
		acticleRepo.save(article4);

		var articles = acticleRepo.findByTitleContaining("ing");

		assertThat(articles).hasSize(2).contains(article1, article3);
	}

	@Test
	void should_not_save_articles_with_long_name() {
		var article1 = new Article("I am a veryyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy long title", "name","body",Arrays.asList(tagRepo.findByName("Spring")) );
		acticleRepo.save(article1);
		// The save will not actually execute insert command. You may think of
		// annotating this method with @Commit
		// but the annotating will execute the flush in afterTestMethod method and the
		// below assert will not trigger
		assertThrows(PersistenceException.class, () -> testEntityManager.flush());
	}


	@Test
	void should_find_article_by_id() {
		var artilce1 = new Article("New Title 1","New Desc 1","New Body 1", Arrays.asList(  tagRepo.findByName("Java") ));
		acticleRepo.save(artilce1);

		var article2 = new Article("New Title 2","New Desc 2","New Body 2", Arrays.asList(  tagRepo.findByName("Java") ));
		acticleRepo.save(article2);

		Article foundTutorial = acticleRepo.findById(article2.getId()).get();

		assertThat(foundTutorial).isEqualTo(article2);
	}

}
