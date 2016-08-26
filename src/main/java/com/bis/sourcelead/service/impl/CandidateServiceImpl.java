package com.bis.sourcelead.service.impl;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.service.CandidateService;

/**
 * 
 * @author prasad K
 * @date 25th Aug 2016
 *
 */

@Service
public class CandidateServiceImpl implements CandidateService {

	@Autowired
	CandidateDao candidateDao;

	@Override
	public Candidate storeCandidate(JSONObject jsonObj) throws Exception {
		Candidate candidate = new Candidate();
		candidate.setFileName(jsonObj.getString("firstName"));
		candidate.setLastName(jsonObj.getString("lastName"));
		
		return candidate;
	}
	

	

	
	
	
}
