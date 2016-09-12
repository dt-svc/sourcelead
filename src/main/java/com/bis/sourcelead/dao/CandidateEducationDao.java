package com.bis.sourcelead.dao;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.CandidateEducation;

	/**
	 * 
	 * @author Shivayogi
	 * @date 25th August 2016
	 *
	 */
public interface CandidateEducationDao {

	
	
	public CandidateEducation storeCandidateEducation(CandidateEducation candidateEducation) throws Exception;

	public List<CandidateEducation>   getCandidateEducationListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception;

	public CandidateEducation getCandidateEducationBycandidateIdandEducationId(Long candidateId,Long candidateCertificateId)  throws Exception;

	

}
