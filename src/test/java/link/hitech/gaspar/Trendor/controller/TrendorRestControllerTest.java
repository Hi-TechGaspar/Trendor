package link.hitech.gaspar.Trendor.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import link.hitech.gaspar.Trendor.service.EventService;
import link.hitech.gaspar.Trendor.service.TrendService;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * Unit tests for Trendor's REST Controller.
 * 
 * @author Hi-Tech Gaspar
 */
@SpringBootTest
@AutoConfigureMockMvc
public class TrendorRestControllerTest {

  // Needed due to GraphQL, otherwise fails to boot
  @MockBean
  ServerEndpointExporter serverEndpointExporter;
 
  @Autowired
  MockMvc mockMvc;
  
  @MockBean
  TrendService trendService;
  
  @MockBean
  EventService eventService;  
  
  @Test
  public void getNotAllowed() throws Exception {
    // GET is actually not supported, so it will yield a 405.
    this.mockMvc.perform(get("/trending"))
                .andExpect(status().isMethodNotAllowed());
  }
  
  @Test
  public void emptyTrends() throws Exception {    
    // by using POST we can expect empty trends being returned
    this.mockMvc.perform(post("/trending"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json("{\"trending-people\":{},\"trending-titles\":{},\"trending-videos\":{}}"));
    
    // Also, we can make sure our service layer was indeed called
    verify(this.trendService, times(1)).getTrendingVideos(Optional.empty());
    verify(this.trendService, times(1)).getTrendingPeople(Optional.empty());
    verify(this.trendService, times(1)).getTrendingTitles(Optional.empty());
  }

  @Test
  public void trendsReturned() throws Exception {
    // First, let's mock our service layer
    Map<String, Integer> people = Collections.singletonMap("ana-de-armas", 11);
    when(this.trendService.getTrendingPeople(any())).thenReturn(people);
    
    Map<String, Integer> videos = Collections.emptyMap();
    when(this.trendService.getTrendingVideos(any())).thenReturn(videos);
    
    Map<String, Integer> titles = Collections.singletonMap("the-matrix-1998", 2);
    when(this.trendService.getTrendingTitles(any())).thenReturn(titles);
    
    // Response from Controller should therefore be:
    // {"trending-people":{"ana-de-armas":11},"trending-titles":{"the-matrix-1998":2},"trending-videos":{}}
    // Let's test it is indeed the case.
        
    this.mockMvc.perform(post("/trending"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.trending-people.ana-de-armas").value(11))
                .andExpect(jsonPath("$.trending-videos").value(new HashMap()))
                .andExpect(jsonPath("$.trending-titles.the-matrix-1998").value(2));
  }
  
}
