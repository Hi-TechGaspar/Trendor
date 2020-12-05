package link.hitech.gaspar.Trendor.entity;

import javax.persistence.*;
import lombok.NoArgsConstructor;

/**
 * Video entity.
 * 
 * @author Hi-Tech Gaspar
 */
@Entity
@NoArgsConstructor
public class Video extends BaseEntity {

  public Video(String slug, DateEntry date) {
    super(slug, date);
  }
}
