package com.bis.sourcelead.service.impl;

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
	CandidateDao CandidateDao;

	@Override
	public Candidate storeCandidate(Candidate candidate) throws Exception {
		return null;
	}
	

	

	
	
	
}
