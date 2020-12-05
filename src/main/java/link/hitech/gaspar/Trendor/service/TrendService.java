package link.hitech.gaspar.Trendor.service;


import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import link.hitech.gaspar.Trendor.entity.BaseEntity;
import link.hitech.gaspar.Trendor.entity.DateEntry;
import link.hitech.gaspar.Trendor.repository.DateEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

/**
 * Service layer for getting trending entities.
 * 
 * @author Hi-Tech Gaspar
 */
@Component
public class TrendService {
  
  @Autowired
  DateEntryRepository dr;
  
  @Value("${trendor.weight.pageview}")
  private int weightPageView;
    
  @Value("${trendor.weight.votedown}")
  private int weightVoteDown;  
    
  @Value("${trendor.weight.voteup}")
  private int weightVoteUp;
    
  @Value("${trendor.weight.favorite}")
  private int weightFavorite;
  
  @Value("${trendor.weight.comment}")
  private int weightComment;
 
  @Value("${trendor.weight.add.external.link}")
  private int weightAddExternalLink;
  
  /**
   * Basically the amount of days in the past to consider for trend analysis.
   */
  @Value("${trendor.sliding.window.size}")
  private int slidingWindowSize;
  
  @Value("${trendor.max.entities.to.reply}")
  private int maxEntitiesToReply;
  
  public Map<String, Integer> getTrendingVideos(Optional<Integer> max) {
    int n = max.isPresent() ? max.get() : maxEntitiesToReply;
    Pageable limit = PageRequest.of(0, slidingWindowSize, Sort.Direction.DESC, "date");
    Page<DateEntry> entries = dr.findAll(limit);   
    return getTrending(entries.getContent(), n, "video");
  }

  public Map<String, Integer> getTrendingTitles(Optional<Integer> max) {
    int n = max.isPresent() ? max.get() : maxEntitiesToReply;
    Pageable limit = PageRequest.of(0, slidingWindowSize, Sort.Direction.DESC, "date");
    Page<DateEntry> entries = dr.findAll(limit);   
    return getTrending(entries.getContent(), n, "title");
  }

  public Map<String, Integer> getTrendingPeople(Optional<Integer> max) {
    int n = max.isPresent() ? max.get() : maxEntitiesToReply;
    Pageable limit = PageRequest.of(0, slidingWindowSize, Sort.Direction.DESC, "date");
    Page<DateEntry> entries = dr.findAll(limit);   
    return getTrending(entries.getContent(), n, "person");
  }
  
  private Map<String, Integer> getTrending(List<DateEntry> entries, int n, String entityType) {
    Map<String, Integer> ranking = new LinkedHashMap();
    
    for (DateEntry entry : entries) {
      Set<? extends BaseEntity> specificEntities = entry.getTrendingEntities(entityType);
      for (BaseEntity entity : specificEntities) {
        if (!ranking.containsKey(entity.getSlug())) {
          ranking.put(entity.getSlug(), 0);
        }
        int dailyRank = entity.getViews() * weightPageView 
                      + entity.getVotesDown() * weightVoteDown 
                      + entity.getVotesUp() * weightVoteUp 
                      + entity.getFavorites() * weightFavorite 
                      + entity.getComments() * weightComment
                      + entity.getAddExternalLink() * weightAddExternalLink;
        ranking.put(entity.getSlug(), ranking.get(entity.getSlug()) + dailyRank);
      }
    }
    
    /**
     * After above loop ranking contains a Map between slug and ranking value.
     * Let us now order it by ranking value and limit the amount.
     */
    return 
    ranking.entrySet().stream()
       .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
       .limit(n)
       .collect(Collectors.toMap(
          Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
  }
  
  
}
