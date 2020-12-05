package link.hitech.gaspar.Trendor.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import link.hitech.gaspar.Trendor.exception.TrendorException;
import link.hitech.gaspar.Trendor.service.EventService;
import link.hitech.gaspar.Trendor.service.TrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * REST Controller for Trendor.
 * 
 * @author Hi-Tech Gaspar
 */
@RestController
public class TrendorRestController {
  
  @Autowired
  EventService eventService;
  
  @Autowired
  TrendService trendService;
  
  @RequestMapping(value = {"/event/{entityType}/{slug}"}, method = RequestMethod.POST)
  public ResponseEntity<?> postNewEvent(@PathVariable Map<String, String> pathVariables,
                                        @RequestParam(name = "t") String eventType) {
    try {
      String entityType = pathVariables.get("entityType");
      String slug = pathVariables.get("slug");
      this.eventService.newEvent(entityType, slug, eventType);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (TrendorException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }    
  }
  
  
  @RequestMapping(value = {"/trending"}, method = RequestMethod.POST)
  public ResponseEntity<?> getTrends(@RequestParam(name = "max") Optional<Integer> maxElements) {
    Map<String, Map> trending  = new HashMap<String, Map>() {{
        put("trending-people", trendService.getTrendingPeople(maxElements));
        put("trending-titles", trendService.getTrendingTitles(maxElements));
        put("trending-videos", trendService.getTrendingVideos(maxElements));
    }};

    return new ResponseEntity<>(trending, HttpStatus.OK);
  }
  
}
