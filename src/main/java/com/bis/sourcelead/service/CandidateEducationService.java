package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.entity.CandidateEducation;


public interface CandidateEducationService {

	
	public CandidateEducation storeCandidateEducation(JSONObject jsonObject) throws Exception;
	

	public List<CandidateEducation>   getCandidateEducationListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception;

	
}
