package com.bis.sourcelead.dao;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateEmployers;

	/**
	 * 
	 * @author Shivayogi
	 * @date 25th August 2016
	 *
	 */
public interface CandidateEducationDao {

	
	
	public CandidateEducation storeCandidateEducation(CandidateEducation candidateEducation) throws Exception;

	public List<CandidateEducation>   getCandidateEducationListByCandidateIdAndOrgLocationIDImmigration(Long candidateId, Long orgLocationId, String searchVal) throws Exception;
	
	public List<CandidateEducation>   getCandidateEducationListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception;

	public CandidateEducation getCandidateEducationBycandidateIdandEducationId(Long candidateId,Long candidateCertificateId)  throws Exception;
	
	/**
	 * @author Prasad Salina
	 * @param candidateId
	 * @return
	 */
	public CandidateEducation getCandidateEducationByCandidateId(Long candidateId) throws Exception;

	public Boolean validateEducationName(Long candidateId, String candidateEducation) throws Exception;
	
	public List<String> getCandidateEducationNamesByCandidateId(Long candidateId) throws Exception;
	
	CandidateEducation getCandidateEducationById(Long candidateEduId) throws Exception;
	
	Integer getCandidateActiveEducationsCountByCandidateId(Long candidateId) throws Exception;

	Integer getCandidateGlobalActiveEducationsCountByCandidateId(Long candidateId) throws Exception;

}
