package link.hitech.gaspar.Trendor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

/**
 * Integration tests for Trendor.
 * 
 * @author Hi-Tech Gaspar
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {HiTechGasparTrendorIntegrationTest.ContextConfiguration.class})
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class HiTechGasparTrendorIntegrationTest {
  
  @TestConfiguration
  static class ContextConfiguration {
    
    /**
     * We override this bean in order to let the H2 database empty for the 
     * integration tests.
     * 
     * @return 
     */
    @Bean
    public CommandLineRunner h2AccessDemo() {
      return new CommandLineRunner() {
        @Override
        public void run(String[] args) throws Exception {
          // do nothing, that way H2 Database is left empty
        }
      };
    }
  }
  
  @LocalServerPort
  int port;
  
  @Autowired
  TestRestTemplate restTemplate;  
  
  @Test
  public void getNotAllowed() {
    ResponseEntity<Map> response = this.restTemplate.getForEntity("http://localhost:" + port + "/trending", Map.class);
    
    assertThat(response.getStatusCode(), equalTo(HttpStatus.METHOD_NOT_ALLOWED));
  }
  
  @Test
  public void emptyTrends() {
    ResponseEntity<Map> response = this.restTemplate.postForEntity("http://localhost:" + port + "/trending", null, Map.class);
        
    assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    assertThat(response.getBody().get("trending-people"), equalTo(new HashMap()));
    assertThat(response.getBody().get("trending-videos"), equalTo(new HashMap()));
    assertThat(response.getBody().get("trending-titles"), equalTo(new HashMap()));
  }
  
  @Test
  public void trendsReturned() {
    // First, lets post some events: 
    // 2 page views on the matrix, and 1 comment and 1 page view for Ana de Armas
    String pathPageViewMatrix = "/event/title/the-matrix-1998?t=pageView";
    this.restTemplate.postForEntity("http://localhost:" + port + pathPageViewMatrix, null, Map.class);
    this.restTemplate.postForEntity("http://localhost:" + port + pathPageViewMatrix, null, Map.class);
    
    String pathComment = "/event/person/ana-de-armas?t=comments";
    this.restTemplate.postForEntity("http://localhost:" + port + pathComment, null, Map.class);
    String pathPageViewAna = "/event/person/ana-de-armas?t=pageView";
    this.restTemplate.postForEntity("http://localhost:" + port + pathPageViewAna, null, Map.class);
    
    // Get Trends
    ResponseEntity<Map> response = this.restTemplate.postForEntity("http://localhost:" + port + "/trending", null, Map.class);
     
    // Check
    assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    assertThat(response.getBody().get("trending-people"), equalTo(Collections.singletonMap("ana-de-armas", 11)));
    assertThat(response.getBody().get("trending-videos"), equalTo(Collections.emptyMap()));
    assertThat(response.getBody().get("trending-titles"), equalTo(Collections.singletonMap("the-matrix-1998", 2)));    
  }
  
  
}
