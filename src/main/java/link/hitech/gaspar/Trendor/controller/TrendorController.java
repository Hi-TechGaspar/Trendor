package link.hitech.gaspar.Trendor.controller;

import javax.servlet.http.HttpServletRequest;
import link.hitech.gaspar.Trendor.repository.DateEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for Trendor.
 * 
 * @author Hi-Tech Gaspar
 */
@Controller
public class TrendorController {
  
  @Autowired
  DateEntryRepository dr;
  
  @RequestMapping(value = "/events", method = RequestMethod.GET)
  public String titles(HttpServletRequest request, Model model) {  
    model.addAttribute("events", this.dr.findAll());
    return "events";
  }
  
}
