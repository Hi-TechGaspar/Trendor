package link.hitech.gaspar.Trendor.repository;

import link.hitech.gaspar.Trendor.entity.BaseEntity;

/**
 * Repository for Title entities.
 * 
 * @author Hi-Tech Gaspar
 * @param <T> Type of base entity
 */
public interface AbstractBaseEntityRepository<T extends BaseEntity> {  

  T findBySlug(String slug);
  
}
