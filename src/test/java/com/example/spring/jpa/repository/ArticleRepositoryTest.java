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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import com.example.spring.jpa.model.Article;

import jakarta.persistence.PersistenceException;

@DataJpaTest(properties = { "spring.datasource.url=jdbc:h2:mem:testdb" })
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = { "/create-tag-data.sql", "/create-user-data.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
class ArticleRepositoryTest {

	@Autowired
	ArticleRepository articleRepo;

	@Autowired
	TagRepository tagRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	private TestEntityManager testEntityManager;

	@BeforeAll
	public static void initTest() throws Exception  {
		Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8092").start();
	}
	
	@AfterAll
	public static void finalizeTest() throws Exception  {
		System.out.println("Just put a break point here");
	}

	@Test
	void should_find_no_articles_if_repository_is_empty() {
		var allArticles = articleRepo.findAll();
		assertThat(allArticles).isEmpty();
	}

	@Test
	void should_store_an_article() {		
		var newArticle = Article.builder()
				.title("New Title")
				.description("New Desc")
				.body("New Body")
				.tag( tagRepo.findByName("Java"))
				.user( userRepo.findByUsername("roham"))
				.build();
		
		var savedArticle = articleRepo.save(newArticle);	
		assertThat(savedArticle).extracting(Article::getTitle, Article::getDescription, Article::getBody )
								.containsExactly("New Title","New Desc","New Body" );
	}

	@Test
	void should_find_all_articles() {
		var artilce1 = Article.builder()
				.title("New Title 1")
				.description("New Desc 1")
				.body("New Body 1")
				.tag( tagRepo.findByName("Java"))
				.user(userRepo.findByUsername("alireza"))
				.build();
		articleRepo.save(artilce1);

		var article2 = Article.builder()
				.title("New Title 2")
				.description("New Desc 2")
				.body("New Body 2")
				.tag( tagRepo.findByName("Spring"))
				.tag( tagRepo.findByName("Java"))
				.user(userRepo.findByUsername("alireza"))
				.build();
		articleRepo.save(article2);

		var article3 = Article.builder()
				.title("New Title 2")
				.description("New Desc 2")
				.body("New Body 2")
				.tag( tagRepo.findByName("Struts"))
				.user(userRepo.findByUsername("alireza"))
				.build();
		articleRepo.save(article3);

		var articles = articleRepo.findAll();
		assertThat(articles).hasSize(3).contains(artilce1, article2, article3);
	}

	@Test
	void save_all_articles() {
		var artiles = Arrays.asList(
				new Article(userRepo.findByUsername("alireza"),"New Title 1","New Desc 1","New Body 1", Arrays.asList(  tagRepo.findByName("Java") ) ),
				new Article(userRepo.findByUsername("alireza"), "New Title 1","New Desc 1","New Body 1", Arrays.asList(  tagRepo.findByName("Spring") ) ), 
				new Article(userRepo.findByUsername("alireza"), "New Title 1","New Desc 1","New Body 1", Arrays.asList(  tagRepo.findByName("Struts") ) )
			);

		var savedArticles = articleRepo.saveAll(artiles);
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
				
		var article1 = Article.builder()
				.title("How to train Spring?")
				.description( "Ever wonder how?")
				.body("Try spring.io first")
				.tag( tagRepo.findByName("Spring"))
				.user(userRepo.findByUsername("alireza"))
				.build();
		articleRepo.save(article1);
		
		var article2 = Article.builder()
				.title("Will  java remain forever?")
				.description( "May be other tech?")
				.body("Ofcourse it is none dying tech")
				.tag( tagRepo.findByName("Java"))
				.user(userRepo.findByUsername("arefeh"))
				.build();
		articleRepo.save(article2);

		var article3 = Article.builder()
				.title("Spring data can save your life")
				.description( "how?")
				.body("No more boilerplate")
				.tag( tagRepo.findByName("Spring"))
				.user(userRepo.findByUsername("arefeh"))
				.build();
		articleRepo.save(article3);

		var article4 = Article.builder()
				.title("playING with caps lock")
				.description( "test case")
				.body("CAPS LOCK cOULD bEE fUNN")
				.tag( tagRepo.findByName("Spring"))
				.user(userRepo.findByUsername("roham"))
				.build();
		articleRepo.save(article4);

		var articles = articleRepo.findByTitleContaining("ing");

		assertThat(articles).hasSize(2).contains(article1, article3);
	}

	@Test
	void should_not_save_articles_with_long_name() {
		
		var article1 = Article.builder()
				.title("I am a veryyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy long title")
				.description( "name")
				.tag( tagRepo.findByName("Spring"))
				.user(userRepo.findByUsername("alireza"))
				.body("greate framework")
				.build();
		articleRepo.save(article1);
		// The save will not actually execute insert command. You may think of
		// annotating this method with @Commit
		// but the annotating will execute the flush in afterTestMethod method and the
		// below assert will not trigger
		assertThrows(PersistenceException.class, () -> testEntityManager.flush());
	}


	@Test
	void should_find_article_by_id() {
		var artilce1 = Article.builder()
				.title("New Title 1")
				.description("New Desc 1")
				.body("New Body 1")
				.tag( tagRepo.findByName("Java"))
				.user(userRepo.findByUsername("arefeh"))
				.build();
		articleRepo.save(artilce1);

		var article2 = Article.builder()
				.title("New Title 2")
				.description("New Desc 2")
				.body("New Body 2")
				.tag( tagRepo.findByName("Spring"))
				.user(userRepo.findByUsername("alireza"))
				.build();
		articleRepo.save(article2);

		Article foundTutorial = articleRepo.findById(article2.getId()).get();

		assertThat(foundTutorial).isEqualTo(article2);
	}
	
	@Test
	void should_find_articles_by_tag() {
				
		var article1 = Article.builder()
				.title("How to train Spring?")
				.description( "Ever wonder how?")
				.body("Try spring.io first")
				.tag(  tagRepo.findByName("Java"))
				.user(userRepo.findByUsername("arefeh"))
				.build();
		articleRepo.save(article1);
		
		var article2 = Article.builder()
				.title("Will  java remain forever?")
				.description( "May be other tech?")
				.body("Ofcourse it is none dying tech")
				.tag(  tagRepo.findByName("Java"))
				.user(userRepo.findByUsername("alireza"))
				.build();
		articleRepo.save(article2);

		var article3 = Article.builder()
				.title("Spring data can save your life")
				.description( "how?")
				.body("No more boilerplate")
				.tag(  tagRepo.findByName("Spring"))
				.user(userRepo.findByUsername("alireza"))
				.build();
		articleRepo.save(article3);

		var article4 = Article.builder()
				.title("playING with caps lock")
				.description( "test case")
				.body("CAPS LOCK cOULD bEE fUNN")
				.tag(  tagRepo.findByName("Spring"))
				.user(userRepo.findByUsername("alireza"))
				.build();
		articleRepo.save(article4);

		var articles = articleRepo.findByTagsName("Java");

		assertThat(articles).hasSize(2).contains(article1, article2);
	}

}
