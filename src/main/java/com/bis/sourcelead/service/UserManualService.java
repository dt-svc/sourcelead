package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserManualsDTO;
import com.bis.sourcelead.entity.UserManualAttachment;
import com.bis.sourcelead.entity.UserManualLevelOne;
import com.bis.sourcelead.entity.UserManualLevelThree;
import com.bis.sourcelead.entity.UserManualLevelTwo;

public interface UserManualService {
	
	//level one
	public void storeUserManuals(UserManualsDTO userManualsDTO) throws Exception;
	
	UserManualLevelOne getUserManualByTitle(Long orgLocationsId,String UserManTitle) throws Exception;
	
	List<UserManualLevelOne> getAllUserManualls() throws Exception;
	
	UserManualLevelOne getLevelOneByUUId(String id) throws Exception;
	
	UserManualLevelTwo getLevelTwoByUUId(String id) throws Exception;
	
	UserManualLevelThree getLevelThreeByUUId(String id) throws Exception;
	
	ResponseList<UserManualsDTO> getAllUserManuallsForDynamicList(UserManualsDTO userManualsDTO) throws Exception;
	
	ResponseList<UserManualsDTO> getAllLevelTwoDynamicList(UserManualsDTO userManualsDTO,Long id) throws Exception;
	
	
	
	List<UserManualLevelTwo> getAllUserManuallsTwoById(Long id) throws Exception;
	
	public void deleteUserManualThree(Long id)throws Exception;
	
	public void deleteUserManualTwo(Long id)throws Exception;
	
	public void deleteUserManualOne(Long id)throws Exception;
	
	//level Three 
	
	UserManualsDTO getUserManualOneById(Long id)throws Exception;
	
	UserManualsDTO getUserManualTwoById(Long id)throws Exception;
	
	UserManualsDTO getUserManualThreeById(Long id)throws Exception;
	
	ResponseList<UserManualsDTO> getAllLevelThreeDynamicList(UserManualsDTO userManualsDTO,Long id) throws Exception;
	
	//UserManaual attachment
	List<UserManualAttachment> getUsermanualAttachmentList(Long levelThreeId);
	
	
	public void deleteUserManualAttachments(Long id)throws Exception;
	

}
