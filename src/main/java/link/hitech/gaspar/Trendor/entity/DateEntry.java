package link.hitech.gaspar.Trendor.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Entity keeping track of events occurring on a given day.
 * 
 * @author Hi-Tech Gaspar
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateEntry {

  @Id
  private String date;
  
  @OneToMany(mappedBy="date", fetch = FetchType.EAGER)
  private Set<Video> videos;
  
  @OneToMany(mappedBy="date", fetch = FetchType.EAGER)
  private Set<Title> titles;
  
  @OneToMany(mappedBy="date", fetch = FetchType.EAGER)
  private Set<Person> people;

  public DateEntry(String date) {
    this.date = date;
  }
  
  public void addVideo(Video v) {
    if (this.videos == null) {
      this.videos = new HashSet();
    }
    this.videos.add(v);
    v.setDate(this);
  }
  
  public void addTitle(Title t) {
    if (this.titles == null) {
      this.titles = new HashSet();
    }
    this.titles.add(t);
    t.setDate(this);
  }
  
  public void addPerson(Person p) {
    if (this.people == null) {
      this.people = new HashSet();
    }
    this.people.add(p);
    p.setDate(this);
  }
  
}
