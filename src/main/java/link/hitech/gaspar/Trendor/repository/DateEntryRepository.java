package link.hitech.gaspar.Trendor.repository;

import link.hitech.gaspar.Trendor.entity.DateEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for DateEntry entities.
 * 
 * @author Hi-Tech Gaspar
 */
public interface DateEntryRepository extends CrudRepository<DateEntry, String> {
  
  Page<DateEntry> findAll(Pageable pageable);
  
}
