package link.hitech.gaspar.Trendor.repository;

import link.hitech.gaspar.Trendor.entity.Title;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for Title entities.
 * 
 * @author Hi-Tech Gaspar
 */
public interface TitleRepository extends CrudRepository<Title, String>,
                                          AbstractBaseEntityRepository<Title> {  

}
