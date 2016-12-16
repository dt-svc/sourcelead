package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.UserMailBox;

public interface UserMailBoxService {

	public UserMailBox saveUserMailBox(UserMailBox userMailBox) throws Exception;
	
	List<UserMailBox> getUserMailBoxByUserIdAndOrgLocationIdAndMailStatus(Long userId,Long orgLocationId,String mailStatus) throws Exception;
	
	UserMailBox getUserMailBoxByUUID(String UUID);
}
