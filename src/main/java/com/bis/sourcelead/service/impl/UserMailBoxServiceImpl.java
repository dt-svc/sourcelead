package com.bis.sourcelead.service.impl;

import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.UserMailBoxDao;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.UserMailBox;
import com.bis.sourcelead.service.UserMailBoxService;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class UserMailBoxServiceImpl implements UserMailBoxService{
	
	@Autowired
	UserMailBoxDao userMailBoxDao;
	
	@Autowired
	CampaignIndexHelper campaignIndexHelper;
	
	@Autowired
    private SourceleadELKBaseIndexer<CampaignIndex> sourceleadELKBaseIndexer;
	
	
	
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
	public void setRequestForResumeDetails(JSONObject jsonObj,String candidatesIds,String UUID,MultipartFile multiFile) throws Exception {
		
		UserMailBox mailBox = userMailBoxDao.getUserMailBoxByUUID(UUID);
		
		if(null != mailBox){
			
			UserMailBox userMailBox = new UserMailBox();
			userMailBox.setToMail(mailBox.getFromMail());
			userMailBox.setFromMail(jsonObj.getString("email"));
			userMailBox.setMailStatus(Constants.MAIL_REQUESTED);
			userMailBox.setCandidateId(candidatesIds);
			userMailBox.setUserId(mailBox.getUserId());
			userMailBox.setOrgLocationId(mailBox.getOrgLocationId());
			userMailBox.setIsFavourite(0);
			userMailBox.setUuid(SourceLeadRandomCodeHelper.generateUUID());
			StringBuilder sb = new StringBuilder();
			userMailBox.setSubject(sb.append("Rep: ").append(mailBox.getSubject()).toString());
			
			if(jsonObj.has("phone") && !isNullOrEmpty(jsonObj.getString("phone"))){
				userMailBox.setPhno(jsonObj.getString("phone"));
			}
			
			if(jsonObj.has("comment") && !isNullOrEmpty(jsonObj.getString("comment"))){
				userMailBox.setComments(jsonObj.getString("comment"));
			}
			
			userMailBox.setIsMailRead(0);
			userMailBox.setStatus(1);
			userMailBox.setCreatedDate(new Date());
			userMailBox.setUpdatedDate(new Date());
			
			 if(null != multiFile.getContentType() && !multiFile.getContentType().equalsIgnoreCase("application/octet-stream")){
			   	  try {
			   		userMailBox.setFileContentType(multiFile.getContentType());
			   		userMailBox.setDocFile(multiFile.getBytes());
			   		userMailBox.setFileName(multiFile.getOriginalFilename());
			   	   
				       }catch (Exception e) {
			   	      	 e.printStackTrace();
						    }
			       }
			
			userMailBoxDao.saveUserMailBox(userMailBox);
			
			mailBox.setIsMailRead(1);
			userMailBoxDao.saveUserMailBox(mailBox);
			saveOrUpdateUserMailBoxELK(userMailBox);
		}
		
	}

	@Override
	public UserMailBox getUserMailBoxById(String Id) throws Exception{
		return userMailBoxDao.getUserMailBoxById(Id);
	}

	@Override
	public List<UserMailBox> getUserFavouriteMailsByUserIdAndOrgLocationId(Long userId, Long orgLocationId)
			throws Exception {
		return userMailBoxDao.getUserFavouriteMailsByUserIdAndOrgLocationId(userId, orgLocationId);
	}

	@Override
	public Integer getUnReadMessagesByUserIdAndLocationIdAndType(Long userId, Long orgLocationId, String type)
			throws Exception {
		return userMailBoxDao.getUnReadMessagesByUserIdAndLocationIdAndType(userId, orgLocationId, type);
	}

	@Override
	public UserMailBox getUserPreviousMail(Long Id, Long userId, Long orgLocationId,String type) throws Exception {
		return userMailBoxDao.getUserPreviousMail(Id, userId, orgLocationId,type);
	}
	
	@Override
	public UserMailBox getUserNextMail(Long Id, Long userId, Long orgLocationId,String type) throws Exception {
		return userMailBoxDao.getUserNextMail(Id, userId, orgLocationId,type);
	}

	@Override
	public void saveOrUpdateUserMailBoxELK(UserMailBox userMailBox) throws Exception {
		
		 //Elastic Search Document Saving
		CampaignIndex campaignIndex = campaignIndexHelper.getCampaignIndexFromUserMailBox(userMailBox);
		sourceleadELKBaseIndexer.saveOrUpdateElkDocument(campaignIndex, Constants.ELK_CAMPAIGN_INDEX, Constants.ELK_CAMPAIGN_TYPE,userMailBox.getId().toString());
	}

	@Override
	public Long getMailBoxCountByUserIdAndOrgLocationid(Long userId, Long orgLocationId, String type) throws Exception {
		return userMailBoxDao.getMailBoxCountByUserIdAndOrgLocationid(userId, orgLocationId, type);
	}
	
}
