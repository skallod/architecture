package ru.galuzin.elastic.sample.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

@Configuration
//@EnableElasticsearchRepositories(basePackages = "ru.galuzin.elastic.sample.repository")
//@ComponentScan(basePackages = { "com.baeldung.spring.data.es.service" })
public class Config  extends AbstractElasticsearchConfiguration {

    @Override
    public RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .build();

        return RestClients.create(clientConfiguration).rest();
    }

//    @Value("${elasticsearch.home:/usr/local/Cellar/elasticsearch/5.6.0}")
//    private String elasticsearchHome;
//
//    @Value("${elasticsearch.cluster.name:elasticsearch}")
//    private String clusterName;

//    @Bean
//    public Client client() throws UnknownHostException {
//        Settings elasticsearchSettings = Settings.builder()
////                .put("client.transport.sniff", true)
////                .put("path.home", elasticsearchHome)
//                .put("cluster.name", "es-docker-cluster").build();
//        TransportClient client = new PreBuiltTransportClient(elasticsearchSettings);
//        client.addTransportAddress(
//                new TransportAddress(InetAddress.getByName("127.0.0.1"), 9200));
//        return client;
//    }
//
//    @Bean(name = { "elasticsearchOperations", "elasticsearchTemplate" })
//    public ElasticsearchOperations elasticsearchTemplate() throws UnknownHostException {
//        return new ElasticsearchTemplate(elasticsearchClient());
//    }
}
