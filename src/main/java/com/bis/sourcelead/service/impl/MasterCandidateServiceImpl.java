package com.bis.sourcelead.service.impl;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.MasterCandidateDao;
import com.bis.sourcelead.entity.MasterCandidate;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.MasterCandidateService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class MasterCandidateServiceImpl implements MasterCandidateService{

	@Autowired
	MasterCandidateDao masterCandidateDao;
	
	@Autowired
	CommonUtil commonUtil;
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	@Override
	public MasterCandidate saveMasterCandidate(MasterCandidate masterCandidate) throws Exception
	{
		return masterCandidateDao.saveMasterCandidate(masterCandidate);
	}

	@Override
	public MasterCandidate getMasterCandidateByInteligence(JSONObject jsonObj) throws Exception
	{
		String email = null;
		String phoneNumber = null;
		
		if(jsonObj.has("email") && !isNullOrEmpty(jsonObj.getString("email"))){
			email = jsonObj.getString("email");
		}
		
		if(jsonObj.has("mobilePhone") && !isNullOrEmpty(jsonObj.getString("mobilePhone"))){
			phoneNumber = jsonObj.getString("mobilePhone");
		}
		
		if(jsonObj.has("phoneNumber") && !isNullOrEmpty(jsonObj.getString("phoneNumber"))){
			phoneNumber = jsonObj.getString("phoneNumber");
		}
		
		MasterCandidate masterCandidate = masterCandidateDao.getMasterCandidateByEmailOrPhoneNumber(email, phoneNumber);
		
		return masterCandidate;
	}

	@Override
	public MasterCandidate storeMasterCandidateByJsonObject(JSONObject jsonObj,User user) throws Exception
	{
		MasterCandidate masterCandidate = new MasterCandidate();
		
		if(jsonObj.has("firstName")){
			masterCandidate.setFirstName(jsonObj.getString("firstName"));
		}
		
		if(jsonObj.has("lastName")){
			masterCandidate.setLastName(jsonObj.getString("lastName"));
		}
		
		if(jsonObj.has("email")){
			masterCandidate.setEmail(jsonObj.getString("email"));
		}
		
		if(jsonObj.has("phoneNumber")){
			masterCandidate.setPhoneNumber(jsonObj.getString("phoneNumber"));
		}
		
		masterCandidate.setStatus(Constants.RECORD_ACTIVE);
		masterCandidate.setCreatedBy(user.getUserCode());
		masterCandidate.setCreatedDate(commonUtil.currentDate());
		masterCandidate.setUpdatedBy(user.getUserCode());
		masterCandidate.setUpdatedDate(commonUtil.currentDate());
		masterCandidateDao.saveMasterCandidate(masterCandidate);
		
		return masterCandidate;
	}

	
}
