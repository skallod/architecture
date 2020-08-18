package ru.galuzin.elastic.sample.service;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

@Service
public class JournalService {

//    @Autowired
//    @Qualifier("elasticsearchTemplate")
//    ElasticsearchOperations operations;

    @Autowired
    RestHighLevelClient client;

    public void getAllSorted() {

//        IndexRequest request = new IndexRequest("spring-data", "elasticsearch",
//                UUID.randomUUID().toString())
//                .source(singletonMap("feature", "high-level-rest-client"))
//                .setRefreshPolicy(IMMEDIATE);
//        IndexResponse response = highLevelClient.index(request, RequestOptions.DEFAULT);

        client.searchTemplate();
//
//        SearchResponse response = elasticsearchTemplate.prepareSearch("index_db").setTypes("departments")
//                .addSort(SortBuilders.fieldSort("totalEmployee.keyword")
//                        .order(SortOrder.ASC)).setQuery(qb)
//                .setSize(100).execute().actionGet();
//
//        for(SearchHit hits : response.getHits())
//        {
//            System.out.print("id = " + hits.getId());
//            System.out.println(hits.getSourceAsString());
//        }
    }
}
