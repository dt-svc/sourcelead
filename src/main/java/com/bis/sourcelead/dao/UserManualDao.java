package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserManualsDTO;
import com.bis.sourcelead.entity.UserManualAttachment;
import com.bis.sourcelead.entity.UserManualLevelOne;
import com.bis.sourcelead.entity.UserManualLevelThree;
import com.bis.sourcelead.entity.UserManualLevelTwo;

public interface UserManualDao {
	
	public UserManualLevelOne storeUserManuals(UserManualLevelOne userManualLevelOne) throws Exception;
	
	public UserManualLevelTwo storeUserManualstwo(UserManualLevelTwo userManualLevelTwo) throws Exception;
	
	public UserManualLevelThree storeUserManualsThree(UserManualLevelThree userManualLevelThree) throws Exception;
	
	public UserManualAttachment storeUserManualAttachment(UserManualAttachment userManualAttachment)throws Exception;
	
	UserManualLevelOne getUserManualByTitle( String UserManTitle) throws Exception;
	
	UserManualLevelTwo getUserManualTwoByTitle(String UserManTitle) throws Exception;
	
	UserManualLevelThree getUserManualThreeByTitle(String UserManTitle) throws Exception;
	
	UserManualLevelOne getUserManualById(Long userManId) throws Exception;
	
	UserManualLevelTwo getUserManualTwoById(Long userManId) throws Exception;
	
	UserManualLevelThree getUserManualThreeById(Long userManId) throws Exception;
	
	UserManualLevelOne getLevelOneByUUId(String id) throws Exception;
	
	UserManualLevelTwo getLevelTwoByUUId(String id) throws Exception;
	
	UserManualLevelThree getLevelThreeByUUId(String id) throws Exception;
	
	UserManualLevelTwo getUserManual2ByIds(Long userMan1Id,Long userMan2Id) throws Exception;
	
	List<UserManualLevelOne> getAllUserManualls() throws Exception;
	
	ResponseList<UserManualLevelOne> getAllUserManuallsForDynamicList(UserManualsDTO userManualsDTO) throws Exception;
	
	ResponseList<UserManualLevelTwo> getAllLevelTwoDynamicList(UserManualsDTO userManualsDTO,Long id) throws Exception;
	
	ResponseList<UserManualLevelThree> getAllLevelThreeDynamicList(UserManualsDTO userManualsDTO,Long id) throws Exception;
	
	List<UserManualLevelTwo> getAllUserManuallsTwoById(Long id) throws Exception;
	
	public UserManualLevelThree deleteUserManualThree(UserManualLevelThree userManualLevelThree)throws Exception;
	
	public UserManualLevelTwo deleteUserManualTwo(UserManualLevelTwo userManualLevelTwo)throws Exception;
	
	public UserManualLevelOne deleteUserManualOne(UserManualLevelOne UserManualLevelOne)throws Exception;
	
	//attachments
	
	List<UserManualAttachment> getUsermanualAttachmentList(Long levelThreeId);
	
	UserManualAttachment getUserManualAttchListById(Long id);
	
	public UserManualAttachment deleteUserManualAttachments(UserManualAttachment UserManualAttachments) throws Exception;
	
	//for publishing the data in website
	
	List<Object[]> getUserManualHelperOneTitles() throws Exception;
	
	List<Object[]> getUserManualHelperTwoTitles(Long id) throws Exception;
	
	List<UserManualLevelThree> getUserManualHelperThreeTitles(Long id)throws Exception;


}
