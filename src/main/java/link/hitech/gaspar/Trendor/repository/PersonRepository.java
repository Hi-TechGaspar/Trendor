package link.hitech.gaspar.Trendor.repository;

import link.hitech.gaspar.Trendor.entity.Person;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for Person entities.
 * 
 * @author Hi-Tech Gaspar
 */
public interface PersonRepository extends CrudRepository<Person, String> {  
}
