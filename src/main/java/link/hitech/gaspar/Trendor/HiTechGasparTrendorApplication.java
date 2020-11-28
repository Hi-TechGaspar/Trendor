package link.hitech.gaspar.Trendor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import link.hitech.gaspar.Trendor.entity.Person;
import link.hitech.gaspar.Trendor.entity.Title;
import link.hitech.gaspar.Trendor.entity.Video;
import link.hitech.gaspar.Trendor.entity.DateEntry;
import link.hitech.gaspar.Trendor.repository.DateEntryRepository;
import link.hitech.gaspar.Trendor.repository.PersonRepository;
import link.hitech.gaspar.Trendor.repository.TitleRepository;
import link.hitech.gaspar.Trendor.repository.VideoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HiTechGasparTrendorApplication {

  static final Logger log = LoggerFactory.getLogger(HiTechGasparTrendorApplication.class);
  
  public static void main(String[] args) {
    SpringApplication.run(HiTechGasparTrendorApplication.class, args);
  }
  
  @Bean
  public CommandLineRunner h2AccessDemo(DateEntryRepository dr, PersonRepository pr, 
                                        TitleRepository tr, VideoRepository vr) {
    return (args) -> {
      // First, let's create and save some entities
      DateEntry entry = dr.save(new DateEntry("1-12-2020"));
      
      Person henry = new Person("henry-cavill", entry);
      henry.setViews(135);
      henry.setVotesUp(1);
      henry = pr.save(henry);
      
      Person scarlett = new Person("scarlett-johansson", entry);
      scarlett.setViews(178);
      scarlett.setVotesUp(5);
      scarlett = pr.save(scarlett);      
      
      
      Title ghost = new Title("ghost-in-the-shell-2017", entry);
      ghost.setViews(34);
      ghost = tr.save(ghost);
      
      Title wolf = new Title("the-wolf-of-wall-street-2013", entry);
      wolf.setViews(76);
      wolf = tr.save(wolf);
      
      Title matrix = new Title("the-matrix-1999", entry);
      matrix.setViews(23);
      matrix = tr.save(matrix);
      
      Video topFights = new Video("top-10-fight-scenes-from-movies", entry);
      topFights.setViews(81);
      topFights = vr.save(topFights);      
      
      entry.addPerson(henry);
      entry.addPerson(scarlett);
      
      entry.addTitle(ghost);
      entry.addTitle(wolf);
      entry.addTitle(matrix);
      
      entry.addVideo(topFights);
      
      dr.save(entry);
      
      // Create more entities, for a different day
      DateEntry entry2 = dr.save(new DateEntry("2-12-2020"));
      
      Person will = new Person("will-smith", entry2);
      will.setViews(11);
      will = pr.save(will);
      
      Person scarlett2 = new Person("scarlett-johansson", entry2);
      scarlett2.setViews(202);
      scarlett2.setVotesUp(11);      
      scarlett2 = pr.save(scarlett2);
      
      Title resident = new Title("resident-evil-2002", entry2);
      resident.setViews(45);
      resident.setVotesUp(2);
      resident = tr.save(resident);      
      
      Video topFights2 = new Video("top-10-fight-scenes-from-movies", entry2);
      topFights2.setViews(491);
      topFights2 = vr.save(topFights2);
      
      Video cats = new Video("best-cat-videos-ever", entry2);
      cats.setViews(964);
      cats = vr.save(cats);      
      
      entry2.addPerson(will);
      entry2.addPerson(scarlett2);
      
      entry2.addTitle(resident);
      
      entry2.addVideo(topFights2);
      entry2.addVideo(cats);
      
      dr.save(entry2);
      
      // Now, let's try to fetch them
      log.info("Printing stored entities:");
      dr.findAll().forEach(d -> log.info(d.toString()));
    };
  }
  

}
