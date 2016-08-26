package com.bis.sourcelead.service.impl;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.util.CommonUtil;

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

	/**
	 * @author David Raju Sammeta
	 * 
	 * This Method is for getting Candidate by Candidate Id OrgId and OrgLocationID
	 * 
	 * @return Candidate
	 * 
	 * @exception NullPointerException
	 */
	@Override
	public Candidate getCandidateByCandidateIdAndOrgLocationIdAndOrgId(Long candidateId,Long orgId,Long orgLocationId) throws Exception {
	
		   Candidate  candidate = candidateDao.getCandidateByCandidateIdAndOrgLocationIdAndOrgId(candidateId, orgId, orgLocationId);
	
		 return candidate;
	}

	/**
	 * to Candidates List by OrgCode OrgLocationCode
	 * @author David Sammeta
	 * @param request
	 * @return String
	 * @throws Exception
    */
	@Override
	public List<Candidate> getCandidatesListByOrgIdAndOrgLocationID(Long orgId, Long orgLocationId) throws Exception {
		
		 return candidateDao.getCandidatesListByOrgIdAndOrgLocationID(orgId, orgLocationId);
	}
	

	

	
	
	
}
