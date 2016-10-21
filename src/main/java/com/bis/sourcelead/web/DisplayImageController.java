package com.bis.sourcelead.web;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * This class is used for...
 * 
 * @author: Srinivas Vemula
 * @date: 12-May-2016
 */

@Controller
public class DisplayImageController {

	private static final Logger logger = LoggerFactory.getLogger(DisplayImageController.class);

	@Autowired
	private ApplicationContext applicationContext;
	
	
	@PostConstruct
	public void init() {
		logger.debug(" *** HomeController init with: " + applicationContext);
	}

    @RequestMapping(value = "/userAutoComplete")
  	public ModelAndView userAutoComplete() {
  		ModelAndView model = new ModelAndView();
  		model.setViewName("userAutoComplete");
  		return model;

  	}
}
