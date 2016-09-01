package com.bis.sourcelead.service;

import java.util.HashSet;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

/**
 * @author 
 *
 */
@Service
public interface CandidateEmployersService {

	/**
	 * @param jsonObject
	 * @param user
	 * @param orgLocationId
	 * @param candidateId
	 * @return CandidateEmployers
	 * @throws Exception
	 */
	public CandidateEmployers storeCandidateEmployers(JSONObject jsonObject,User user,OrgLocations orgLocationId,Long candidateId) throws Exception; ; 
	
	/**
	 * @param candidateId
	 * @param orgLocationId
	 * @return List<CandidateEmployers>
	 * @throws Exception
	 */
	public List<CandidateEmployers>   getCandidatesEmployersListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception;
   
	/**
	 * @param jsonObj
	 * @param orgLocations
	 * @param candidate
	 * @param userCode
	 * @return
	 * @throws JSONException
	 */
	public  HashSet<CandidateEmployers> getCandidateEmployersDetails(JSONObject jsonObj,OrgLocations orgLocations,Candidate candidate,String userCode) throws JSONException;

	/**
	 * @param candidateId
	 * @param candidateEmployerId
	 * @return CandidateEmployers
	 * @throws Exception
	 */
	public CandidateEmployers removeCandidateEmployerBycandidateIdandCandidateEmployerID(Long candidateId,Long candidateEmployerId)  throws Exception;

	/**
	 * @param candidateId
	 * @param candidateEmployerId
	 * @return CandidateEmployers
	 * @throws Exception
	 */
	public CandidateEmployers getCandidateEmployerBycandidateIdandCandidateEmployerID(Long candidateId,Long candidateEmployerId)  throws Exception;

	
}
