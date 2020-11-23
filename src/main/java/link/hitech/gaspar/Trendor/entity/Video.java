package link.hitech.gaspar.Trendor.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

/**
 * Video entity.
 * 
 * @author Hi-Tech Gaspar
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Video {
  
  @Id
  @EqualsAndHashCode.Include
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;
  
  /**
   * Url slug.
   */
  private String slug;

  /**
   * Amount of views for this entity, on a given day.
   */
  private int views;
  
  /**
   * Amount of votes up for this entity, on a given day.
   */
  private int votesUp;
  
  /**
   * Amount of votes down for this entity, on a given day.
   */
  private int votesDown;
  
  /**
   * Amount of times a user favorites this entity, on a given day.
   */
  private int favorites;
  
  /**
   * Amount of comments for this entity, on a given day.
   */
  private int comments;

  /**
   * Amount of times an external link was added to this page profile.
   */
  private int addExternalLink;
  
  @ManyToOne
  @JoinColumn(nullable=false)
  @ToString.Exclude
  DateEntry date;

  public Video(String slug, DateEntry date) {
    this.slug = slug;
    this.date = date;
  }
  
}
