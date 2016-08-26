package com.bis.sourcelead.service.impl;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.CandidateEmployersDao;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.service.CandidateEmployersService;

/**
 * @author Ajith Matta
 *
 */

@Service
public class CandidateEmployersServiceImpl implements CandidateEmployersService {
	
	@Autowired
	CandidateEmployersDao candidateEmployersDao;
	
	
	/**
	 * This method is used for save the candidate employers
	 * @author Ajith Matta
	 * @return CandidateEmployers
	 * @throws Exception
    */
	
	@Override
	public CandidateEmployers storeCandidateEmployers(JSONObject jsonObject) throws Exception {
		
		
		return null;
	}
	
	/**
	 * This method is used for display the CandidateEmployers list
	 * @author Ajith Matta
	 * @return String
	 * @throws Exception
    */
	@Override
	public List<CandidateEmployers>   getCandidatesEmployersListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception {
		
		 return candidateEmployersDao.getCandidatesEmployersListByCandidateIdAndOrgLocationID(candidateId, orgLocationId);
	}
	

}
