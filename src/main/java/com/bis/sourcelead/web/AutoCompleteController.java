package com.bis.sourcelead.web;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.service.CountriesService;

/**
 * Prasad Salina
 * @author bis113
 *
 */

@Controller
public class AutoCompleteController {

	private static final Logger logger = LoggerFactory.getLogger(AutoCompleteController.class);
	@Autowired
	private ApplicationContext applicationContext;
	
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	CountriesService countriesService;
	
	
	@PostConstruct
	public void init() {
		logger.debug(" *** AutoCompleteController init with: " + applicationContext);
	}

    @RequestMapping(value = "/testAutoComplete")
  	public ModelAndView testAutoComplete() throws Exception {
  		ModelAndView model = new ModelAndView();
  		model.setViewName("testAutoComplete");
  		model.addObject("sourceleadUrl", sourceleadUrl);
  		
  		List<Countries> countryList = countriesService.getAllCountries();
  		model.addObject("countryList", countryList);
  		return model;

  	}
}
