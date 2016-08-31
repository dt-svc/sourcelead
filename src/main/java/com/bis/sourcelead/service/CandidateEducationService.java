package com.bis.sourcelead.service;

import java.util.HashSet;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateCertifications;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;


public interface CandidateEducationService {

	
	public CandidateEducation storeCandidateEducation(JSONObject jsonObject,Long candidateId, Long orgLocationId,User user) throws Exception;
	

	public List<CandidateEducation>   getCandidateEducationListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception;

	HashSet<CandidateEducation> getCandidateEducationDetails(JSONObject jsonObj,OrgLocations orgLocations,Candidate candidate,String userCode) throws JSONException;

	public CandidateEducation removeCandidateEducationBycandidateIdandcandidateEducationId(Long candidateId, Long candidateEducationId) throws Exception;

	
	public CandidateEducation getCandidateEducationBycandidateIdandEducationId(Long candidateId,Long candidateCertificateId)  throws Exception;


}
