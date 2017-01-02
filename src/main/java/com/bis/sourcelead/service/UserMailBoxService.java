package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.UserMailBox;

public interface UserMailBoxService {

	public UserMailBox saveUserMailBox(UserMailBox userMailBox) throws Exception;
	
	List<UserMailBox> getUserMailBoxByUserIdAndOrgLocationIdAndMailStatus(Long userId,Long orgLocationId,String mailStatus) throws Exception;
	
	UserMailBox getUserMailBoxByUUID(String UUID);
	
	public void setRequestForResumeDetails(JSONObject jsonObj,String candidatesIds,String UUID) throws Exception;
	
	UserMailBox getUserMailBoxById(String Id) throws Exception;
	
	List<UserMailBox> getUserFavouriteMailsByUserIdAndOrgLocationId(Long userId,Long orgLocationId) throws Exception;
	
	public Integer getUnReadMessagesByUserIdAndLocationIdAndType(Long userId,Long orgLocationId,String type) throws Exception;
	
	UserMailBox getUserPreviousMail(Long Id,Long userId,Long orgLocationId,String type) throws Exception;
	
	UserMailBox getUserNextMail(Long Id,Long userId,Long orgLocationId,String type) throws Exception;
	
}
