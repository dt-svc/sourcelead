package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.UserMailBox;

public interface UserMailBoxDao {

	public UserMailBox saveUserMailBox(UserMailBox userMailBox) throws Exception;
	
	List<UserMailBox> getUserMailBoxByUserIdAndOrgLocationIdAndMailStatus(Long userId,Long orgLocationId,String mailStatus) throws Exception;
	
	UserMailBox getUserMailBoxByUUID(String UUID);
}
