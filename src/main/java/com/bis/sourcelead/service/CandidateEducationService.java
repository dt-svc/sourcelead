package com.bis.sourcelead.service;

import java.util.HashSet;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;


public interface CandidateEducationService {

	
	/**
	 * @param jsonObject
	 * @param candidateId
	 * @param orgLocationId
	 * @param user
	 * @return CandidateEducation
	 * @throws Exception
	 */
	public CandidateEducation storeCandidateEducation(JSONObject jsonObject,Long candidateId, Long orgLocationId,User user) throws Exception;
	

	/**
	 * @param candidateId
	 * @param orgLocationId
	 * @return List<CandidateEducation>
	 * @throws Exception
	 */
	public List<CandidateEducation>   getCandidateEducationListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception;

	/**
	 * @param jsonObj
	 * @param orgLocations
	 * @param candidate
	 * @param userCode
	 * @return HashSet<CandidateEducation>
	 * @throws JSONException
	 */
	HashSet<CandidateEducation> getCandidateEducationDetails(JSONObject jsonObj,OrgLocations orgLocations,Candidate candidate,String userCode) throws JSONException;

	/**
	 * @param candidateId
	 * @param candidateEducationId
	 * @return CandidateEducation
	 * @throws Exception
	 */
	public CandidateEducation removeCandidateEducationBycandidateIdandcandidateEducationId(Long candidateId, Long candidateEducationId) throws Exception;

	
	/**
	 * @param candidateId
	 * @param candidateCertificateId
	 * @return CandidateEducation
	 * @throws Exception
	 */
	public CandidateEducation getCandidateEducationBycandidateIdandEducationId(Long candidateId,Long candidateCertificateId)  throws Exception;

	public Boolean validateEducationName(Long candidateId, String candidateEducation) throws Exception;

}
