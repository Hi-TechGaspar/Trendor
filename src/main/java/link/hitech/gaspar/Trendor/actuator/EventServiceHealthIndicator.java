package link.hitech.gaspar.Trendor.actuator;

import link.hitech.gaspar.Trendor.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class EventServiceHealthIndicator implements HealthIndicator {

  @Autowired
  EventService es;
  
  @Override
  public Health health() {
    return isStatusUp() 
      ? Health.up().withDetail("EventService", "Available").build()
      : Health.down().withDetail("EventService", "Not Available").build();
  }
  
  private boolean isStatusUp() {
    // Custom logic to check if all is good would go here
    
    return false;
  }
  
}
