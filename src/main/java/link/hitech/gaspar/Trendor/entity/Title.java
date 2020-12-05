package link.hitech.gaspar.Trendor.entity;

import javax.persistence.*;
import lombok.NoArgsConstructor;

/**
 * Title entity.
 * 
 * @author Hi-Tech Gaspar
 */
@Entity
@NoArgsConstructor
public class Title extends BaseEntity {

  public Title(String slug, DateEntry date) {
    super(slug, date);
  }
  
}
