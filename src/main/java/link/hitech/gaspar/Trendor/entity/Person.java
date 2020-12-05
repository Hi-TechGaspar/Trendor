package link.hitech.gaspar.Trendor.entity;

import javax.persistence.*;
import lombok.NoArgsConstructor;

/**
 * Person entity.
 * 
 * @author Hi-Tech Gaspar
 */
@Entity
@NoArgsConstructor
public class Person extends BaseEntity {
    
  public Person(String slug, DateEntry date) {
    super(slug, date);
  }
  
}
