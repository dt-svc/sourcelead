package com.bis.sourcelead.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.service.CandidateService;
/**
 * Handles requests for the Candidate service.
 * 
 * @author Prasad K
 * @date 30th June 2016
 *
 */

@RestController
@RequestMapping(value = "/candidate")
public class CandidateRestController {

	private static final Logger logger = LoggerFactory.getLogger(CandidateRestController.class);

	@Autowired
	private CandidateService candiadteService;

	

	/*------------Create a Candidate--------------------------------*/
	/**
	 * Handles request for storing candidate into Database
	 * @return candidate
	 * @throws NullPointerException
	 */
	@RequestMapping(value = "/storeCandidate", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Candidate storeCandidate() throws Exception {
		Candidate candidate = new Candidate();
		return  candiadteService.storeCandidate(candidate);
	}

	

	
}
