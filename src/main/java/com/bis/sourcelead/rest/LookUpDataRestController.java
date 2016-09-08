package com.bis.sourcelead.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * Handles requests for the LookUpData service.
 * 
 * @author Prasad K
 * @date 30th Aug 2016
 *
 */

import com.bis.sourcelead.service.LookUpDataService;

@RestController
@RequestMapping(value = "/lookupdata")
public class LookUpDataRestController {

	private static final Logger logger = LoggerFactory.getLogger(LookUpDataRestController.class);
    
	@Autowired
	LookUpDataService lookUpDataService;
	
}
