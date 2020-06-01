package ru.galuzin.elastic.sample;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.galuzin.elastic.sample.model.Article;
import ru.galuzin.elastic.sample.repository.ArticleRepository;
import ru.galuzin.elastic.sample.service.ArticleService;

import java.io.IOException;

@SpringBootApplication
public class ElasticApp {

    @Autowired
    RestHighLevelClient highLevelClient;

    @Autowired
    ArticleService articleService;

    @Autowired
    ArticleRepository articleRepository;

    public static void main(String[] args) {
        final ConfigurableApplicationContext context = SpringApplication.run(ElasticApp.class);

    }

    @EventListener(ApplicationReadyEvent.class)
    public void applicationReady() throws IOException {

        //work fine
//        IndexRequest request = new IndexRequest("spring-data", "elasticsearch",
//                UUID.randomUUID().toString())
//                .source(singletonMap("feature", "high-level-rest-client"))
//                .setRefreshPolicy(IMMEDIATE);
//        IndexResponse response = highLevelClient.index(request, RequestOptions.DEFAULT);
//        System.out.println("response = " + response);

        //        final ArticleService articleService = context.getBean(ArticleService.class);
        System.out.println("GAL START");
//        Article article = new Article("Parphumer");
//        final String id = UUID.randomUUID().toString();
//        article.setId(id);
//        article.setTitle("Parphumer history of one killer");
//        article.setAuthors(asList(new Author("Patric Zuskend")));
//        article.setTags("parphum", "hudozh");
//        articleService.save(article);
////        final Page<Article> response = articleService.findByAuthorName("John"
////                , Pageable.unpaged());
////        response.forEach(a -> System.out.println("a title = " + a.getTitle()));
//        articleService.save(article);
//        final Optional<Article> article1 = articleService.findOne(id);
//        article1.ifPresent(a -> System.out.println("a.getTitle() = " + a.getTitle()));
        final Page<Article> articles = articleRepository.findByTitle("history", Pageable.unpaged());
        articles.forEach(a -> System.out.println("a.getId() = " + a.getId()));
        //todo test update
    }
}
