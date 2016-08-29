package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.bis.sourcelead.dao.CandidateEmployersDao;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

/**
 * @author Ajith Matta
 * @create date 25 Aug 2016
 */

@Repository
public class CandidateEmployersDaoImpl  implements CandidateEmployersDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${SourceLead.100}")
	private String notFound;
	@Value("${SourceLead.101}")
	private String noRecords;
	@Value("${SourceLead.102}")
	private String statusUpdate;
	@Value("${SourceLead.103}")
	private String statusDelete;
	@Value("${SourceLead.106}")
	private String duplicateValue;
	
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	private static final Logger logger = LoggerFactory.getLogger(CandidateEmployersDaoImpl.class);
	
	/**
	 * @author Ajith Matta
	 * 
	 * This Method is for save the candidate employers by json 
	 * 
	 * @return Candidateemployers
	 * 
	 * @exception NullPointerException
	 */

		

	@Override
	public CandidateEmployers storeCandidateEmployers(CandidateEmployers candidateEmployers) throws Exception {
		
		return null;
		
		
	}
	
	/**
	 * @author Ajith Matta
	 * 
	 * This Method is for save the candidate employers list by candidate id 
	 * 
	 * @return Candidate employers list
	 * 
	 * @exception NullPointerException
	 */

	@Override
	public List<CandidateEmployers> getCandidatesEmployersListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId)
			throws Exception {
		return null;
	}


}
