package com.bis.sourcelead.service.impl;

import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.UserMailBoxDao;
import com.bis.sourcelead.entity.UserMailBox;
import com.bis.sourcelead.service.UserMailBoxService;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class UserMailBoxServiceImpl implements UserMailBoxService{
	
	@Autowired
	UserMailBoxDao userMailBoxDao;
	
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	@Override
	public UserMailBox saveUserMailBox(UserMailBox userMailBox) throws Exception {
		return userMailBoxDao.saveUserMailBox(userMailBox);
	}

	@Override
	public List<UserMailBox> getUserMailBoxByUserIdAndOrgLocationIdAndMailStatus(Long userId, Long orgLocationId,
			String mailStatus) throws Exception {
		return userMailBoxDao.getUserMailBoxByUserIdAndOrgLocationIdAndMailStatus(userId, orgLocationId, mailStatus);
	}

	@Override
	public UserMailBox getUserMailBoxByUUID(String UUID) {
		return userMailBoxDao.getUserMailBoxByUUID(UUID);
	}

	@Override
	public void setRequestForResumeDetails(JSONObject jsonObj,String candidatesIds,String UUID) throws Exception {
		
		UserMailBox mailBox = userMailBoxDao.getUserMailBoxByUUID(UUID);
		
		if(null != mailBox){
			
			UserMailBox userMailBox = new UserMailBox();
			userMailBox.setToMail(mailBox.getFromMail());
			userMailBox.setFromMail(jsonObj.getString("email"));
			userMailBox.setMailStatus("Requested");
			userMailBox.setCandidateId(candidatesIds);
			userMailBox.setUserId(mailBox.getUserId());
			userMailBox.setOrgLocationId(mailBox.getOrgLocationId());
			userMailBox.setUuid(SourceLeadRandomCodeHelper.generateUUID());
			userMailBox.setSubject("Request For Candidates / Consultants Profiles");
			
			if(jsonObj.has("phone") && !isNullOrEmpty(jsonObj.getString("phone"))){
				userMailBox.setPhno(jsonObj.getString("phone"));
			}
			
			if(jsonObj.has("comment") && !isNullOrEmpty(jsonObj.getString("comment"))){
				userMailBox.setComments(jsonObj.getString("comment"));
			}
			
			userMailBox.setStatus(1);
			userMailBox.setCreatedDate(new Date());
			userMailBox.setUpdatedDate(new Date());
			userMailBoxDao.saveUserMailBox(userMailBox);
		}
		
	}

	
}
