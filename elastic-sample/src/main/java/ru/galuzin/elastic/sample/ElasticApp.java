package ru.galuzin.elastic.sample;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import ru.galuzin.elastic.sample.model.journal.Journal;
import ru.galuzin.elastic.sample.model.journal.JournalExportStatus;
import ru.galuzin.elastic.sample.repository.ArticleRepository;
import ru.galuzin.elastic.sample.repository.JournalRepository;
import ru.galuzin.elastic.sample.service.ArticleService;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@SpringBootApplication
public class ElasticApp {

    @Autowired
    RestHighLevelClient highLevelClient;

    @Autowired
    ArticleService articleService;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    JournalRepository journalRepository;

    public static void main(String[] args) {
        final ConfigurableApplicationContext context = SpringApplication.run(ElasticApp.class);

    }

    @EventListener(ApplicationReadyEvent.class)
    public void applicationReady() throws IOException, InterruptedException {

        //work fine
//        IndexRequest request = new IndexRequest("spring-data", "elasticsearch",
//                UUID.randomUUID().toString())
//                .source(singletonMap("feature", "high-level-rest-client"))
//                .setRefreshPolicy(IMMEDIATE);
//        IndexResponse response = highLevelClient.index(request, RequestOptions.DEFAULT);
        System.out.println("response = " + response);

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
//        final Page<Article> articles = articleRepository.findByTitle("history", Pageable.unpaged());
//        articles.forEach(a -> System.out.println("a.getId() = " + a.getId()));
//        final Page<Article> articles = articleRepository.findByFilteredTagQuery("parphum", Pageable.unpaged());
//        articles.forEach(a -> System.out.println("a.getId() = " + a.getTitle()));
        //todo test update
//        for (int i = 0; i < 10 ; i++) {
//            journalRepository.save(generateJournal());
//            Thread.sleep(1_000);
//        }
//        final SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        final Page<Journal> byEvD = journalRepository.getAllOrderByEventDate(Pageable.unpaged());
//        byEvD.forEach(a -> System.out.println("q = "+sd.format(a.getEventDate())));
    }

    public static Journal generateJournal() {
        final Journal j = new Journal();
        j.setPartitionDate(removeTime(new Date()));
        j.setServiceId(UUID.randomUUID().toString());
        j.setServiceIdType("TYPE");
        j.setEventDate(new Date());
        final JournalExportStatus jes = new JournalExportStatus();
        jes.setErrorCount(0);
        jes.setZoneId("ucp");
        jes.setPluginId("func_si");
        j.setJes(Arrays.asList(jes));
        return j;
    }


    public static Date removeTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
