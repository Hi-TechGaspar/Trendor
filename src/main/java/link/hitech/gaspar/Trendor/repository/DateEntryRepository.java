package link.hitech.gaspar.Trendor.repository;

import link.hitech.gaspar.Trendor.entity.DateEntry;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for DateEntry entities.
 * 
 * @author Hi-Tech Gaspar
 */
public interface DateEntryRepository extends CrudRepository<DateEntry, String> {
  
}
