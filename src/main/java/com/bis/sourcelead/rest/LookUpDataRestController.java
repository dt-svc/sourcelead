package com.bis.sourcelead.rest;

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
@RequestMapping(value = "/app/lookupdata")
public class LookUpDataRestController {

    
	@Autowired
	LookUpDataService lookUpDataService;
	
}
