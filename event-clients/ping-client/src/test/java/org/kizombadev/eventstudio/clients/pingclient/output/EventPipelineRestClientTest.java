package org.kizombadev.eventstudio.clients.pingclient.output;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.Map;

import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@RestClientTest(EventPipelineRestClient.class)
public class EventPipelineRestClientTest {

    @Autowired
    private EventPipelineRestClient eventPipelineRestClient;

    @Autowired
    private MockRestServiceServer server;

    @Test
    public void test() {
        //Arrange
        server.expect(once(), requestTo("http://localhost:8081/api/v1/events/single")).andRespond(withSuccess("", MediaType.APPLICATION_JSON));
        Map<String, Object> map = ImmutableMap.of("source_id", "google", "type", "ping");

        //Act
        eventPipelineRestClient.send(map);

        //Assert
        server.verify();
    }
}