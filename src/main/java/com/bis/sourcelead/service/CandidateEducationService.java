package com.bis.sourcelead.service;

import java.util.HashSet;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.CandidateEducationDTO;
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
	public CandidateEducation storeCandidateEducation(CandidateEducationDTO candidateEducationDTO,Long candidateId, Long orgLocationId,User user) throws Exception;
	

	/**
	 * @param candidateId
	 * @param orgLocationId
	 * @param searchVal 
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
	HashSet<CandidateEducation> getCandidateEducationDetails(CandidateDTO candidateDTO,OrgLocations orgLocations,Candidate candidate,String userCode) throws Exception;

	/**
	 * @param candidateId
	 * @param candidateEducationId
	 * @return CandidateEducation
	 * @throws Exception
	 */
	public CandidateEducation removeCandidateEducationBycandidateIdandcandidateEducationId(Long candidateId, Long candidateEducationId, User user, OrgLocations orgLocations) throws Exception;

	/**
	 * @author Prasad Salina
	 * @param candidateId
	 * @return
	 */
	public CandidateEducation getCandidateEducationByCandidateId(Long candidateId) throws Exception;
	
	/**
	 * @param candidateId
	 * @param candidateCertificateId
	 * @return CandidateEducation
	 * @throws Exception
	 */
	public CandidateEducation getCandidateEducationBycandidateIdandEducationId(Long candidateId,Long candidateCertificateId)  throws Exception;

	public Boolean validateEducationName(Long candidateId, String candidateEducation) throws Exception;
	
	public void saveOrUpdateCandidateElkDocumentWithEducationDetails(Candidate candidate,Long locationId) throws Exception;
	
	public List<String> getCandidateEducationNamesByCandidateId(Long candidateId) throws Exception;


	List<CandidateEducation> getCandidateEducationListByCandidateIdAndOrgLocationIDImmigration(Long candidateId,
			Long orgLocationId, String searchVal) throws Exception;
	
	String removeCandidateEucationrDocById(Long candidateEduId) throws Exception;
	
	Integer getCandidateActiveEducationsCountByCandidateId(Long candidateId) throws Exception;
	
	HashSet<CandidateEducation> getCandidateEducationDetailsList(CandidateDTO candidateDTO,OrgLocations orgLocations,Candidate candidate,User user) throws Exception;
	
	Integer getCandidateGlobalActiveEducationsCountByCandidateId(Long candidateId) throws Exception;
	
	public List<CandidateEducation>   getCandidateGlobalEducationListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception;
	
	public CandidateEducation getCandidateGlobalEducationBycandidateIdandEducationId(Long candidateId,Long candidateEducationId)  throws Exception;
	
	public Boolean validateCandidateGlobalEducationName(Long candidateId, String candidateEducation) throws Exception;
	
	public CandidateEducation removeCandidateGlobalEducationBycandidateIdandcandidateEducationId(Long candidateId, Long candidateEducationId, User user, OrgLocations orgLocations) throws Exception;
	
	

}
