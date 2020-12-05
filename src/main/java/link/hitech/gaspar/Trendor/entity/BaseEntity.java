package link.hitech.gaspar.Trendor.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class BaseEntity {
  
  @Id
  @EqualsAndHashCode.Include
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  /**
   * Url slug.
   */
  String slug;
  
  /**
   * Amount of views for this entity, on a given day.
   */
  int views;
  
  /**
   * Amount of votes up for this entity, on a given day.
   */
  int votesUp;
  
  /**
   * Amount of votes down for this entity, on a given day.
   */
  int votesDown;
  
  /**
   * Amount of times a user favorites this entity, on a given day.
   */
  int favorites;
  
  /**
   * Amount of comments for this entity, on a given day.
   */
  int comments;

  /**
   * Amount of times an external link was added to this page profile.
   */
  int addExternalLink;

  @ManyToOne
  @JoinColumn(nullable=false)
  @ToString.Exclude        
  DateEntry date;
  
  public BaseEntity(String slug, DateEntry date) {
    this.slug = slug;
    this.date = date;
  }
  
  public BaseEntity incViews() {
    this.views++;
    return this;
  }
  
  public BaseEntity incVotesUp() {
    this.votesUp++;
    return this;
  }
  
  public BaseEntity incVotesDown() {
    this.votesDown++;
    return this;
  }
  
  public BaseEntity incFavorites() {
    this.favorites++;
    return this;
  }
  
  public BaseEntity incComments() {
    this.comments++;
    return this;
  }
  
  public BaseEntity incAddExternalLink() {
    this.addExternalLink++;
    return this;
  }
  
  
}
