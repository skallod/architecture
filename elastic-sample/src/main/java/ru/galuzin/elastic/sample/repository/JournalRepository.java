package ru.galuzin.elastic.sample.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import ru.galuzin.elastic.sample.model.journal.Journal;

@Repository
public interface JournalRepository extends ElasticsearchRepository<Journal, String> {

    //todo order by
//    @Query("{\"bool\": {" +
//                "\"must\": {\"match_all\": {}}, " + //may be comment, score=1 , look at constant_score
//                //"\"filter\": " +
//                //    "{\"term\": {\"tags\": \"?0\" }}" +
//                "\"sort\":[\"eventDate\":\"desc\"]" +
//            "}}")
//    Page<Journal> getAllOrderByEventDate(Pageable pageable);
}
