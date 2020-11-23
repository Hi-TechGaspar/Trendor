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
      
      Person henry = pr.save(new Person("henry-cavill", entry));
      Person scarlett = pr.save(new Person("scarlett-johansson", entry));
      
      Title ghost = tr.save(new Title("ghost-in-the-shell-2017", entry));
      Title wolf = tr.save(new Title("the-wolf-of-wall-street-2013", entry));
      Title matrix = tr.save(new Title("the-matrix-1999", entry));
      
      Video topFights = vr.save(new Video("top-10-fight-scenes-from-movies", entry));
      
      
      entry.addPerson(henry);
      entry.addPerson(scarlett);
      
      entry.addTitle(ghost);
      entry.addTitle(wolf);
      entry.addTitle(matrix);
      
      entry.addVideo(topFights);
      
      dr.save(entry);
      
      // Now, let's try to fetch them
      log.info("Printing stored entities:");
      dr.findAll().forEach(d -> log.info(d.toString()));
    };
  }
  

}
