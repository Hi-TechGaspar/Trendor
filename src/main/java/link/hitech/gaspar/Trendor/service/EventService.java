package link.hitech.gaspar.Trendor.service;

import java.util.Optional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import link.hitech.gaspar.Trendor.entity.*;
import link.hitech.gaspar.Trendor.repository.DateEntryRepository;
import link.hitech.gaspar.Trendor.repository.PersonRepository;
import link.hitech.gaspar.Trendor.repository.TitleRepository;
import link.hitech.gaspar.Trendor.repository.VideoRepository;

/**
 * Service layer for events.
 * 
 * @author Hi-Tech Gaspar
 */
@Component
public class EventService {
  
  @Autowired
  TitleRepository tr;
  
  @Autowired
  PersonRepository pr;
  
  @Autowired
  VideoRepository vr;
  
  @Autowired
  DateEntryRepository dr;
  
  /**
   * Stores in db a new event (page view, vote up, ..) for the entity identified
   * by the given slug in today's DateEntry.
   * 
   * @param entityType Type of the entity to fetch
   * @param slug Slug of the entity
   * @param eventType Type of the event (vote up, vote down, ...)
   * @return Returns updated today's DateEntry
   */
  public DateEntry newEvent(String entityType, String slug, String eventType) {
    DateEntry today = this.getToday();
    BaseEntity entity = this.getEntity(today, entityType, slug);
    this.addEvent(entity, eventType);
    saveBaseEntity(entity);
    return this.dr.save(today);
  }
  
  /**
   * Retrieves the right entity (Person, Video or Title) based on the given type
   * and slug, for today's DateEntry.
   * 
   * If entity does not exist (meaning this is its first event of the day), it
   * is created.
   * 
   * @param entityType Type of the entity to fetch
   * @param slug Slug of the entity
   * @return Video, Title or Person instance
   */
  private BaseEntity getEntity(DateEntry today, String entityType, String slug) {
    if (entityType.equals("title")) {
      Optional<Title> title = today.getTitles()
                                        .stream()
                                        .filter(t -> t.getSlug().equals(slug))
                                        .findFirst();
      if (title.isPresent()) {
        return title.get();
      } else {
        Title t = new Title(slug, today);
        today.addTitle(t);
        return t;
      }
    }
    
    if (entityType.equals("person")) {
      Optional<Person> person = today.getPeople()
                                        .stream()
                                        .filter(t -> t.getSlug().equals(slug))
                                        .findFirst();
      if (person.isPresent()) {
        return person.get();
      } else {
        Person p = new Person(slug, today);
        today.addPerson(p);
        return p;
      }
    }
    
    // Assume its video :)
    Optional<Video> video = today.getVideos()
                                        .stream()
                                        .filter(t -> t.getSlug().equals(slug))
                                        .findFirst();
    if (video.isPresent()) {
      return video.get();
    } else {
      Video v = new Video(slug, today);
      today.addVideo(v);
      return v;
    }    
  }
  
  
  public BaseEntity addEvent(BaseEntity entity, String eventType) {
    if (eventType.equals("pageView")) {
      entity.incViews();
    }
    
    if (eventType.equals("votesUp")) {
      entity.incVotesUp();
    }
    
    if (eventType.equals("votesDown")) {
      entity.incVotesDown();
    }
    
    if (eventType.equals("favorites")) {
      entity.incFavorites();
    }
    
    if (eventType.equals("comments")) {
      entity.incComments();
    }
    
    if (eventType.equals("addExternalLink")) {
      entity.incAddExternalLink();
    }
    
    return entity;
  }
  
  /**
   * Retrieves today's DateEntry from H2 database. If not created yet, creates
   * it and stores in database.
   * 
   * @return Today's DateEntry.
   */
  private DateEntry getToday() {
    DateFormat dateFormat = new SimpleDateFormat("d-MM-yyyy");
    String today = dateFormat.format(new Date());
    Optional<DateEntry> opt = this.dr.findById(today);
    return opt.isPresent() ? opt.get() : this.dr.save(new DateEntry(today));
  }

  private void saveBaseEntity(BaseEntity entity) {
    if (entity instanceof Title) {
      this.tr.save((Title)entity);
    } else if (entity instanceof Person) {
      this.pr.save((Person)entity);
    } else if (entity instanceof Video) {
      this.vr.save((Video)entity);
    }
  }
  
  
  
}
