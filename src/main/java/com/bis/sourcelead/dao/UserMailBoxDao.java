package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.UserMailBox;

public interface UserMailBoxDao {

	public UserMailBox saveUserMailBox(UserMailBox userMailBox) throws Exception;
	
	List<UserMailBox> getUserMailBoxByUserIdAndOrgLocationIdAndMailStatus(Long userId,Long orgLocationId,String mailStatus) throws Exception;
	
	UserMailBox getUserMailBoxByUUID(String UUID);
	
	UserMailBox getUserMailBoxById(String Id) throws Exception;
	
	List<UserMailBox> getUserFavouriteMailsByUserIdAndOrgLocationId(Long userId,Long orgLocationId) throws Exception;
	
	public Integer getUnReadMessagesByUserIdAndLocationIdAndType(Long userId,Long orgLocationId,String type) throws Exception;
	
	UserMailBox getUserPreviousMail(Long Id,Long userId,Long orgLocationId,String type,String mailType) throws Exception;
	
	UserMailBox getUserNextMail(Long Id,Long userId,Long orgLocationId,String type,String mailType) throws Exception;
	
	public Long getMailBoxCountByUserIdAndOrgLocationid(Long userId,Long orgLocationId,String type) throws Exception;
}
