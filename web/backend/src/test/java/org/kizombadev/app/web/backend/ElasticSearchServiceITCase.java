package org.kizombadev.app.web.backend;

import org.elasticsearch.client.transport.TransportClient;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestConfiguration
public class ElasticSearchServiceITCase {

    @Autowired
    private TransportClient transportClient;

    @Autowired
    private ElasticSearchService elasticSearchService;

    @Before
    public void init() {
        transportClient.admin().indices().prepareCreate("test").get();
    }

    @Test
    @Ignore
    public void test() {
        //List<Map<String, Object>> result = elasticSearchService.getElements("ping_google", 0, 1);
        //assertThat(result).hasSize(1);
    }
}
