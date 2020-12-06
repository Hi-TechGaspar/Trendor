package link.hitech.gaspar.Trendor.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import link.hitech.gaspar.Trendor.entity.DateEntry;
import link.hitech.gaspar.Trendor.entity.Title;
import link.hitech.gaspar.Trendor.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Mutation implements GraphQLMutationResolver {
  
  @Autowired
  EventService eventService;
  
  public Title titleEvent(String slug, String eventType) {
    DateEntry dateEntry = this.eventService.newEvent("title", slug, eventType);
    return dateEntry.getTitles().stream()
                          .filter(t -> t.getSlug().equals(slug))
                          .findFirst().get();
  }
  
}
