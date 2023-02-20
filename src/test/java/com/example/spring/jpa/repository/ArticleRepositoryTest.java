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

import com.example.spring.jpa.model.Article;
import com.example.spring.jpa.testdata.ArticleTestData;
import com.example.spring.jpa.testdata.TagTestData;

import jakarta.persistence.PersistenceException;

@DataJpaTest(properties = { "spring.datasource.url=jdbc:h2:mem:testdb" })
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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
		var newArticle = ArticleTestData.anArticle().build();
		
		var savedArticle = articleRepo.save(newArticle);	
		assertThat(savedArticle).extracting(Article::getTitle, Article::getDescription )
								.containsExactly(newArticle.getTitle(),newArticle.getDescription() );
	}

	@Test
	void should_find_all_articles() {
		var artilce1 = ArticleTestData.anArticle().build();
		articleRepo.save(artilce1);

		var article2 = ArticleTestData.anArticle().build();
		articleRepo.save(article2);

		var article3 = ArticleTestData.anArticle().build();
		articleRepo.save(article3);

		var articles = articleRepo.findAll();
		assertThat(articles).hasSize(3).contains(artilce1, article2, article3);
	}

	@Test
	void save_all_articles() {
		var artiles = Arrays.asList(
				ArticleTestData.anArticle().build() ,
				ArticleTestData.anArticle().build() , 
				ArticleTestData.anArticle().build() 
			);

		var savedArticles = articleRepo.saveAll(artiles);
		var validIdFound = new AtomicInteger();
		savedArticles.forEach(a -> {
			if (!a.getId().isEmpty()) {
				validIdFound.getAndIncrement();
			}
		});
		assertThat(validIdFound.intValue()).isEqualByComparingTo(3);

	}

	@Test
	void should_find_articles_by_title_containing_string() {
				

		var article1 = ArticleTestData.anArticle().title("How to train Spring?").build();
		var article2 = ArticleTestData.anArticle().title("Will  java remain forever?").build();
		var article3 = ArticleTestData.anArticle().title("Spring data can save your life").build();
		var article4 = ArticleTestData.anArticle().title("playING with caps lock").build();
		
		articleRepo.saveAll( Arrays.asList( article1 , article2, article3, article4 ));
		var articles = articleRepo.findByTitleContaining("ing");

		assertThat(articles).hasSize(2).contains(article1, article3);
	}

	@Test
	void should_not_save_articles_with_long_name() {
		
		var article = ArticleTestData.anArticle().title("I am a veryyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy long title").build();
		articleRepo.save(article);
		// The save will not actually execute insert command. You may think of
		// annotating this method with @Commit
		// but the annotating will execute the flush in afterTestMethod method and the
		// below assert will not trigger
		assertThrows(PersistenceException.class, () -> testEntityManager.flush());
	}


	@Test
	void should_find_article_by_id() {
		var artilce1 = ArticleTestData.anArticle().build();
		articleRepo.save(artilce1);

		var article2 = ArticleTestData.anArticle().build();;
		articleRepo.save(article2);

		Article foundTutorial = articleRepo.findById(article2.getId()).get();

		assertThat(foundTutorial).isEqualTo(article2);
	}
	
	@Test
	void should_find_articles_by_tag() {
				
		var article1 = ArticleTestData.anArticle()
				.clearTags()
				.tag(TagTestData.aTag("Spring").build() )
				.build();
		
		var article2 = ArticleTestData.anArticle()
				.clearTags()
				.tag(TagTestData.aTag("Struts").build())
				.build();

		var article3 = ArticleTestData.anArticle()
				.clearTags()
				.tag(TagTestData.aTag("Java").build())
				.build();

		var article4 = ArticleTestData.anArticle()
				.clearTags()
				.tag(TagTestData.aTag("Java").build())
				.tag(TagTestData.aTag("Spring").build())
				.build();
		articleRepo.saveAll( Arrays.asList( article1 , article2, article3, article4 ));
		
		var articles = articleRepo.findByTagsName("Java");

		assertThat(articles).hasSize(2).contains(article3, article4);		
	}

}
