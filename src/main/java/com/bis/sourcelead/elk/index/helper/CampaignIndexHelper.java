package com.bis.sourcelead.elk.index.helper;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.bis.sourcelead.elk.index.CampaignIndex;
import com.bis.sourcelead.entity.UserMailBox;

@Component
public class CampaignIndexHelper {
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	public CampaignIndex getCampaignIndexFromUserMailBox(UserMailBox userMailBox) throws Exception{
		
		CampaignIndex campaignIndex = null;
		
		if(null != userMailBox && null != userMailBox.getId()){
			campaignIndex = new CampaignIndex();
			campaignIndex.setMailBoxId(userMailBox.getId());
			campaignIndex.setUserId(userMailBox.getUserId().getId());
			campaignIndex.setOrgLocationId(userMailBox.getOrgLocationId().getId());
			campaignIndex.setToMail(userMailBox.getToMail());
			campaignIndex.setFromMail(userMailBox.getFromMail());
			campaignIndex.setMailStatus(userMailBox.getMailStatus());
			campaignIndex.setSubject(userMailBox.getSubject());
			if(!isNullOrEmpty(userMailBox.getCampaignTableData())){
				campaignIndex.setCampaignTableData(userMailBox.getCampaignTableData());
			}
			
			if(!isNullOrEmpty(userMailBox.getBody())){
				campaignIndex.setBody(userMailBox.getBody());
			}
			
			if(!isNullOrEmpty(userMailBox.getCandidateId())){
				campaignIndex.setCandidateId(userMailBox.getCandidateId());
			}
			campaignIndex.setUuid(userMailBox.getUuid());
			campaignIndex.setIsFavourite(userMailBox.getIsFavourite());
			
			if(!isNullOrEmpty(userMailBox.getNameFields())){
				campaignIndex.setNameFields(userMailBox.getNameFields());
			}
			
			if(!isNullOrEmpty(userMailBox.getPhno())){
				campaignIndex.setPhno(userMailBox.getPhno());
			}
			
			if(!isNullOrEmpty(userMailBox.getComments())){
				campaignIndex.setComments(userMailBox.getComments());
			}
			
			campaignIndex.setStatus(userMailBox.getStatus());
			
			if(!isNullOrEmpty(userMailBox.getSignature())){
				campaignIndex.setSignature(userMailBox.getSignature());
			}
			
			if(!isNullOrEmpty(userMailBox.getFileContentType())){
				campaignIndex.setFileContentType(userMailBox.getFileContentType());
			}
			
			if(!isNullOrEmpty(userMailBox.getFileName())){
				campaignIndex.setFileName(userMailBox.getFileName());
			}
			
			campaignIndex.setIsMailRead(userMailBox.getIsMailRead());
			
			if(!isNullOrEmpty(userMailBox.getCreatedBy())){
				campaignIndex.setCreatedBy(userMailBox.getCreatedBy());
			}
			
			if(!isNullOrEmpty(userMailBox.getUpdatedBy())){
				campaignIndex.setUpdatedBy(userMailBox.getUpdatedBy());
			}
			
			if(userMailBox.getCreatedDate() != null){
				campaignIndex.setCreatedDate(new Date(userMailBox.getCreatedDate().getTime()));
			}
			
			if(userMailBox.getUpdatedDate() != null){
				campaignIndex.setUpdatedDate(new Date(userMailBox.getUpdatedDate().getTime()));
			}
			
			if(userMailBox.getMailType() != null){
			campaignIndex.setMailType(userMailBox.getMailType().toString());
			}
			
			
		}
		
		return campaignIndex;
		
	}

}
