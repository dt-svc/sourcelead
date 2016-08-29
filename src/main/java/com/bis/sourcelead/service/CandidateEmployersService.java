package com.bis.sourcelead.service;

import java.util.HashSet;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

@Service
public interface CandidateEmployersService {

	public CandidateEmployers storeCandidateEmployers(JSONObject jsonObject,User user,OrgLocations orgLocationId,Long candidateId) throws Exception; ; 
	
	public List<CandidateEmployers>   getCandidatesEmployersListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception;
   
	public  HashSet<CandidateEmployers> getCandidateEmployersDetails(JSONObject jsonObj,Long orgLocationId,String userCode)throws JSONException;
}
