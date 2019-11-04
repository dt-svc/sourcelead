package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.dto.CandidateCommentsDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateComments;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.User;

/**
 * @author Venkat Kesav
 *
 */
public interface CandidateCommentsService {
 
	/**
	 * @param jsonObj
	 * @param user
	 * @param candidate
	 * @param orgLocation
	 * @return
	 * @throws JSONException
	 * @throws Exception
	 */
	public CandidateComments storeCandidateComments(CandidateCommentsDTO candidateCommentsDTO, User user,Long candidateId, OrgLocations orgLocation, Long submissionId) throws JSONException,Exception;
	
	public List<CandidateComments> getAllCommentsOnCandidateByCandidateIdAndOrgLocation(Long candidateId, OrgLocations orgLocation) throws Exception;
	
	public List<CandidateComments> getAllCommentsOnCandidateByCandidateIdAndOrgLocationAndSubmissionId(Long candidate, Long submissionId, OrgLocations orgLocation) throws Exception;
	
	public List<CandidateComments> getAllCommentsOnCandidateGlobalByCandidateIdAndOrgLocation(Long candidateId, Long orgLocationId) throws Exception;
	
}
