package com.bis.sourcelead.service;

import java.util.HashSet;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.bis.sourcelead.entity.CandidateEducation;


public interface CandidateEducationService {

	
	public CandidateEducation storeCandidateEducation(JSONObject jsonObject,Long candidateId, Long orgLocationId) throws Exception;
	

	public List<CandidateEducation>   getCandidateEducationListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception;

	HashSet<CandidateEducation> getCandidateEducationDetails(JSONObject jsonObj,Long orgLocationId,String userCode) throws JSONException;
}
