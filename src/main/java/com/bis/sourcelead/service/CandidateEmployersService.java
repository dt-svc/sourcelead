package com.bis.sourcelead.service;

import java.util.HashSet;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.CandidateEmployerDTO;
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
	 * @return
	 * @throws Exception
	 */
	public CandidateEmployers storeCandidateEmployers(CandidateEmployerDTO candidateEmployerDTO,User user,OrgLocations orgLocationId,Long candidateId) throws Exception; ; 
	
	/**
	 * @param candidateId
	 * @param orgLocationId
	 * @param searchVal 
	 * @return
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
	public  HashSet<CandidateEmployers> getCandidateEmployersDetails(CandidateDTO candidateDTO,OrgLocations orgLocations,Candidate candidate,String userCode) throws JSONException;
	 
	/**
	 * @param candidateId
	 * @param candidateEmployerId
	 * @return
	 * @throws Exception
	 */
	public CandidateEmployers removeCandidateEmployerBycandidateIdandCandidateEmployerID(Long candidateId,Long candidateEmployerId)  throws Exception;

	/**
	 * @param candidateId
	 * @param candidateEmployerId
	 * @return
	 * @throws Exception
	 */
	public CandidateEmployers getCandidateEmployerBycandidateIdandCandidateEmployerID(Long candidateId,Long candidateEmployerId)  throws Exception;

	
	public void saveOrUpdateCandidateElkDocumentWithEmployerDetails(Long candidateId,Long locationId) throws Exception;
	
	CandidateEmployers getCandidateCurrentEmployer(Long candidateId, Long orgLocationId) throws Exception;
	
	String removeCandidateEmployerDocById(Long candidateEmployerId) throws Exception;
	
	CandidateEmployers getCandidateLastCompanyEmployer(Long candidateId, Long orgLocationId) throws Exception;
	
	public List<CandidateEmployers>   getCandidatesEmployersListByCandidateIdAndOrgLocationIDImmigration(Long candidateId, String searchVal) throws Exception;
	
	public Integer getCandidateActiveEmployersCountByCandidateId(Long candidateId);
	
	public  HashSet<CandidateEmployers> getCandidateEmployersDetailsList(CandidateDTO candidateDTO,OrgLocations orgLocations,Candidate candidate,User user) throws Exception;
	
	public List<CandidateEmployers>   getCandidatesGlobalEmployersListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception;
	
	public CandidateEmployers removeCandidateGlobalEmployerBycandidateIdandCandidateEmployerID(Long candidateId,Long candidateEmployerId)  throws Exception;

	public CandidateEmployers getCandidateGlobalEmployerBycandidateIdandCandidateEmployerID(Long candidateId,Long candidateEmployerId)  throws Exception;

	public CandidateEmployers saveCandidateEmployers(CandidateEmployers candidateEmployers) throws Exception;
}
