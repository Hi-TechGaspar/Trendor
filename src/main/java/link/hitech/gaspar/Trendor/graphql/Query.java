package link.hitech.gaspar.Trendor.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import link.hitech.gaspar.Trendor.entity.DateEntry;
import link.hitech.gaspar.Trendor.repository.DateEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * GraphQL queries.
 * 
 * @author Hi-Tech Gaspar
 */
@Component
public class Query implements GraphQLQueryResolver {
  
  @Autowired
  DateEntryRepository dr;
  
  public Iterable<DateEntry> findAllDateEntries() {
    return this.dr.findAll();
  }
  
}
