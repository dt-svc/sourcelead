package com.bis.sourcelead.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.entity.Candidate;

/**
 * 
 * @author Prasad K
 * @date 29th Mar 2016
 *
 */
@Repository
public class CandidateDaoImpl implements CandidateDao {
	private static final Logger logger = LoggerFactory.getLogger(CandidateDaoImpl.class);
	
	
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

    /**
     * This method for to store candidate into Database
     * @return candidate
     * @exception
     */
	@Override
	public Candidate storeCandidate(Candidate candidate) throws Exception {
		return null;
	}
	
	
	
	
}
