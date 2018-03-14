package com.bis.sourcelead.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.UserManualDao;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserManualsDTO;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserManualAttachment;
import com.bis.sourcelead.entity.UserManualLevelOne;
import com.bis.sourcelead.entity.UserManualLevelThree;
import com.bis.sourcelead.entity.UserManualLevelTwo;
import com.bis.sourcelead.service.UserManualService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Service
public class UserManualServiceImpl implements UserManualService{
	
	private static final Logger logger = LoggerFactory.getLogger(UserManualServiceImpl.class);
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	UserManualDao userManualDao;

	@Override
	public void storeUserManuals(UserManualsDTO userManualsDTO) throws Exception {
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		UserManualLevelOne userManualLevelOne=null;
		UserManualLevelTwo userManualLevelTwo=null;
		
		UserManualLevelThree userManualLevelThree=null;
		if((userManualsDTO.getLevelName()).equalsIgnoreCase("Level-1")){
			
			if(userManualsDTO.getId()!=null && StringUtils.isNotBlank(userManualsDTO.getId())){
				userManualLevelOne=userManualDao.getUserManualById(Long.valueOf(userManualsDTO.getId()));
				userManualLevelOne.setUpdatedBy(user);
				userManualLevelOne.setUpdatedDate(new Date());
			}else{
				userManualLevelOne = new UserManualLevelOne();
				userManualLevelOne.setCreatedBy(user);
				userManualLevelOne.setCreatedDate(commonUtil.currentDate());
				String levelOneUUID=SourceLeadRandomCodeHelper.generateRandomOrgCode();
				userManualLevelOne.setLevelOneUUID(levelOneUUID);
			}
			if(userManualsDTO.getTitle()!=null && StringUtils.isNotBlank(userManualsDTO.getTitle())){
				userManualLevelOne.setTitle(userManualsDTO.getTitle());
			}
			if(userManualsDTO.getDescription()!=null && StringUtils.isNotBlank(userManualsDTO.getDescription())){
				userManualLevelOne.setDescription(userManualsDTO.getDescription());
			}
			
			userManualDao.storeUserManuals(userManualLevelOne);
		}else if((userManualsDTO.getLevelName()).equalsIgnoreCase("Level-2")){
			
			if(userManualsDTO.getId()!=null && StringUtils.isNotBlank(userManualsDTO.getId())){
				userManualLevelTwo=userManualDao.getUserManualTwoById(Long.valueOf(userManualsDTO.getId()));
				if(user!=null){
					userManualLevelOne.setUpdatedBy(user);
				}
				userManualLevelOne.setUpdatedDate(new Date());
			}else{
				userManualLevelTwo = new UserManualLevelTwo();
				if(user!=null){
					userManualLevelTwo.setCreatedBy(user);
				}
				userManualLevelTwo.setCreatedDate(commonUtil.currentDate());
				String levelTwoUUID=SourceLeadRandomCodeHelper.generateRandomOrgCode();
				userManualLevelTwo.setLevelTwoUUID(levelTwoUUID);
			}
			if(userManualsDTO.getLevel1Title()!=null && StringUtils.isNotBlank(userManualsDTO.getLevel1Title())){
				userManualLevelOne=userManualDao.getUserManualById(Long.valueOf(userManualsDTO.getLevel1Title()));
				userManualLevelTwo.setUserManualLevelOneId(userManualLevelOne);
			}
			if(userManualsDTO.getTitle()!=null && StringUtils.isNotBlank(userManualsDTO.getTitle())){
				userManualLevelTwo.setTitle(userManualsDTO.getTitle());
			}
			if(userManualsDTO.getDescription()!=null && StringUtils.isNotBlank(userManualsDTO.getDescription())){
				userManualLevelTwo.setDescription(userManualsDTO.getDescription());
			}
			userManualDao.storeUserManualstwo(userManualLevelTwo);
			
		}else{
			
			if(userManualsDTO.getId()!=null && StringUtils.isNotBlank(userManualsDTO.getId())){
				userManualLevelThree=userManualDao.getUserManualThreeById(Long.valueOf(userManualsDTO.getId()));
				if(user!=null){
					userManualLevelThree.setUpdatedBy(user);
				}
				userManualLevelThree.setUpdatedDate(new Date());
			}else{
				userManualLevelThree=new UserManualLevelThree();
				if(user!=null){
					userManualLevelThree.setCreatedBy(user);
					}
				String levelThreeUUID=SourceLeadRandomCodeHelper.generateRandomOrgCode();
				userManualLevelThree.setLevelThreeUUID(levelThreeUUID);
				userManualLevelThree.setCreatedDate(commonUtil.currentDate());
				}
				if(userManualsDTO.getTitle()!=null && StringUtils.isNotBlank(userManualsDTO.getTitle())){
					userManualLevelThree.setTitle(userManualsDTO.getTitle());
				}
				if(userManualsDTO.getDescription()!=null && StringUtils.isNotBlank(userManualsDTO.getDescription())){
					userManualLevelThree.setDescription(userManualsDTO.getDescription());
				}
				if(userManualsDTO.getLevel1Title()!=null && StringUtils.isNotBlank(userManualsDTO.getLevel1Title()) &&
						userManualsDTO.getLevel2Title()!=null && StringUtils.isNotBlank(userManualsDTO.getLevel2Title())){
					userManualLevelTwo=userManualDao.getUserManual2ByIds(Long.valueOf(userManualsDTO.getLevel1Title()),Long.valueOf(userManualsDTO.getLevel2Title()));
				}
				if(userManualsDTO.getPublishOn() != null && !userManualsDTO.getPublishOn().isEmpty()) {
				DateFormat dateFormatResult = new SimpleDateFormat ("MM/dd/yyyy HH:mm a");
				userManualLevelThree.setPublishOn(dateFormatResult.parse(userManualsDTO.getPublishOn().toString().trim()));
				}
				if(userManualsDTO.getUrlName()!=null && StringUtils.isNotBlank(userManualsDTO.getUrlName())){
					userManualLevelThree.setUploadUrl(userManualsDTO.getUrlName());
				}
				if(userManualLevelTwo !=null){
					userManualLevelThree.setUserManualLevelTwoId(userManualLevelTwo);
				}
				
			if(user!=null){
				userManualLevelThree.setCreatedBy(user);
			}
			userManualLevelThree.setCreatedDate(new Date());
			if(userManualsDTO.getFileinfo() != null && !userManualsDTO.getFileinfo().isEmpty()){
				Set<UserManualAttachment> userManuAttchList=getUserManualsDocuments(userManualsDTO,user,userManualLevelThree);
				userManualLevelThree.setUserManualAttachment(userManuAttchList);
			}
			userManualDao.storeUserManualsThree(userManualLevelThree);
		}
			
		
		
	}

	@Override
	public UserManualLevelOne getUserManualByTitle(Long orgLocationsId, String title) throws Exception {
		
		return userManualDao.getUserManualByTitle(orgLocationsId, title);
	}

	@Override
	public List<UserManualLevelOne> getAllUserManualls() throws Exception {
		return userManualDao.getAllUserManualls();
	}

	@Override
	public List<UserManualLevelTwo> getAllUserManuallsTwoById(Long id) throws Exception {
		return userManualDao.getAllUserManuallsTwoById(id);
	}
	
	private Set<UserManualAttachment> getUserManualsDocuments(UserManualsDTO userManualsDTO, User user,UserManualLevelThree userManualLevelThree)throws Exception {
		
		Set<UserManualAttachment> userManualSet = new HashSet<>();
		if (userManualsDTO.getFileinfo() != null && userManualsDTO.getFileinfo().size() > 0) {
		List<FileInfo> fileInfoList = userManualsDTO.getFileinfo();
		for (FileInfo fileInfo : fileInfoList) {
			UserManualAttachment sta = new UserManualAttachment();
			sta.setUserManualId(userManualLevelThree);
		sta.setAttachmentUUid(SourceLeadRandomCodeHelper.generateRandomUserCode());
		sta.setFileType(fileInfo.getContentType());
		sta.setFileName(fileInfo.getFileName());
		sta.setFileUrl(fileInfo.getFileURL());
		//sta.setStatus(Constants.RECORD_ACTIVE);
		sta.setCreatedBy(user);
		sta.setCreatedDate(new Date());
		if(fileInfo != null){
		FileUploadUtil.moveFile(userManualsDTO.getStaticPath(), fileInfo, "userManuals");
		}
		userManualSet.add(sta);
		}
		}
		return userManualSet;
		}

	@Override
	public ResponseList<UserManualsDTO> getAllUserManuallsForDynamicList(UserManualsDTO userManualsDTO) throws Exception {
		ResponseList<UserManualsDTO> responseList = new ResponseList<>();
		ResponseList<UserManualLevelOne> usermanualList= userManualDao.getAllUserManuallsForDynamicList(userManualsDTO);
		List<UserManualsDTO> userManualsDTOList = new ArrayList<>();
		for(UserManualLevelOne uml:usermanualList.getData()){
			UserManualsDTO umd = new UserManualsDTO();
			umd.setId(uml.getId().toString());
				umd.setTitle(uml.getTitle());
				umd.setId(uml.getId().toString());
				umd.setUserManualUUID(uml.getLevelOneUUID());
				umd.setDescription(uml.getDescription());
				//userManualsDTO.setStatus(userManual.getStatus());
			
			userManualsDTOList.add(umd);
		}
		responseList.setData(userManualsDTOList);
		responseList.setRecordsFiltered(usermanualList.getRecordsFiltered());
		responseList.setRecordsTotal(usermanualList.getRecordsTotal());
		 return responseList;
	}

	@Override
	public ResponseList<UserManualsDTO> getAllLevelTwoDynamicList( UserManualsDTO userManualsDTO ,Long id) throws Exception {
		ResponseList<UserManualsDTO> responseList = new ResponseList<>();
		ResponseList<UserManualLevelTwo> usermanualList= userManualDao.getAllLevelTwoDynamicList(userManualsDTO,id);
		
		List<UserManualsDTO> userManualsDTOList = new ArrayList<>();
		for(UserManualLevelTwo uml:usermanualList.getData()){
			UserManualsDTO umd = new UserManualsDTO();
			umd.setId(uml.getId().toString());
				umd.setTitle(uml.getTitle());
				umd.setId(uml.getId().toString());
				umd.setUserManualUUID(uml.getLevelTwoUUID());
				umd.setDescription(uml.getDescription());
				//userManualsDTO.setStatus(userManual.getStatus());
			
			userManualsDTOList.add(umd);
		}
		responseList.setData(userManualsDTOList);
		responseList.setRecordsFiltered(usermanualList.getRecordsFiltered());
		responseList.setRecordsTotal(usermanualList.getRecordsTotal());
		 return responseList;
	}

	@Override
	public ResponseList<UserManualsDTO> getAllLevelThreeDynamicList(UserManualsDTO userManualsDTO, Long id)
			throws Exception {
		

		ResponseList<UserManualsDTO> responseList = new ResponseList<>();
		ResponseList<UserManualLevelThree> usermanualList= userManualDao.getAllLevelThreeDynamicList(userManualsDTO,id);
		
		List<UserManualsDTO> userManualsDTOList = new ArrayList<>();
		for(UserManualLevelThree uml:usermanualList.getData()){
			UserManualsDTO umd = new UserManualsDTO();
			umd.setId(uml.getId().toString());
				umd.setTitle(uml.getTitle());
				umd.setUserManualUUID(uml.getLevelThreeUUID());
				umd.setId(uml.getId().toString());
				umd.setDescription(uml.getDescription());
				//userManualsDTO.setStatus(userManual.getStatus());
			
			userManualsDTOList.add(umd);
		}
		responseList.setData(userManualsDTOList);
		responseList.setRecordsFiltered(usermanualList.getRecordsFiltered());
		responseList.setRecordsTotal(usermanualList.getRecordsTotal());
		 return responseList;
	
		
	}

	@Override
	public UserManualLevelOne getLevelOneByUUId(String id) throws Exception {
		return userManualDao.getLevelOneByUUId(id);
	}

	@Override
	public UserManualLevelTwo getLevelTwoByUUId(String id) throws Exception {
		return userManualDao.getLevelTwoByUUId(id);
	}

	@Override
	public UserManualLevelThree getLevelThreeByUUId(String id) throws Exception {
		return userManualDao.getLevelThreeByUUId(id);
	}

	@Override
	public void deleteUserManualThree(Long id) throws Exception{
		UserManualLevelThree userManualLevelThree=userManualDao.getUserManualThreeById(id);
		 userManualDao.deleteUserManualThree(userManualLevelThree);
		
	}

	@Override
	public void deleteUserManualTwo(Long id) throws Exception {
		UserManualLevelTwo userManualLevelTwo=userManualDao.getUserManualTwoById(id);
		userManualDao.deleteUserManualTwo(userManualLevelTwo);
	}

	@Override
	public void deleteUserManualOne(Long id) throws Exception {
		UserManualLevelOne UserManualLevelOne=userManualDao.getUserManualById(id);
		userManualDao.deleteUserManualOne(UserManualLevelOne);
	}

	@Override
	public List<UserManualAttachment> getUsermanualAttachmentList(Long levelThreeId) {
		return userManualDao.getUsermanualAttachmentList(levelThreeId);
	}

	@Override
	public void deleteUserManualAttachments(Long id) throws Exception {
		UserManualAttachment userManualAttList=userManualDao.getUserManualAttchListById(id);
		userManualDao.deleteUserManualAttachments(userManualAttList);
	}

	@Override
	public UserManualsDTO getUserManualOneById(Long id) throws Exception{
		UserManualsDTO userManualsDTO=new UserManualsDTO();
		UserManualLevelOne userManualLevelOne= userManualDao.getUserManualById(id);
		if(userManualLevelOne.getTitle()!=null ){
			userManualsDTO.setTitle(userManualLevelOne.getTitle());
		}
		if(userManualLevelOne.getDescription()!=null ){
			userManualsDTO.setDescription(userManualLevelOne.getDescription());
		}
		if(userManualLevelOne.getCreatedBy()!=null ){
			userManualsDTO.setCreatedBy(userManualLevelOne.getCreatedBy().getFullName());
		}
		if(userManualLevelOne.getUpdatedBy()!=null ){
			userManualsDTO.setUpdatedBy(userManualLevelOne.getUpdatedBy().getFullName());
		}
		if(userManualLevelOne.getCreatedDate()!=null ){
			userManualsDTO.setCreatedDate(userManualLevelOne.getCreatedDate().toString());
		}
		if(userManualLevelOne.getUpdatedDate()!=null ){
			userManualsDTO.setUpdatedDate(userManualLevelOne.getUpdatedDate().toString());
		}
		return userManualsDTO;
	}

	@Override
	public UserManualsDTO getUserManualTwoById(Long id) throws Exception {
		UserManualsDTO userManualsDTO=new UserManualsDTO();
		UserManualLevelTwo userManualLevelTwo= userManualDao.getUserManualTwoById(id);
		if(userManualLevelTwo.getTitle()!=null ){
			userManualsDTO.setTitle(userManualLevelTwo.getTitle());
		}
		if(userManualLevelTwo.getDescription()!=null ){
			userManualsDTO.setDescription(userManualLevelTwo.getDescription());
		}
		if(userManualLevelTwo.getCreatedBy()!=null ){
			userManualsDTO.setCreatedBy(userManualLevelTwo.getCreatedBy().getFullName());
		}
		if(userManualLevelTwo.getUpdatedBy()!=null ){
			userManualsDTO.setUpdatedBy(userManualLevelTwo.getUpdatedBy().getFullName());
		}
		if(userManualLevelTwo.getCreatedDate()!=null ){
			userManualsDTO.setCreatedDate(userManualLevelTwo.getCreatedDate().toString());
		}
		if(userManualLevelTwo.getUpdatedDate()!=null ){
			userManualsDTO.setUpdatedDate(userManualLevelTwo.getUpdatedDate().toString());
		}
		return userManualsDTO;
	}

	@Override
	public UserManualsDTO getUserManualThreeById(Long id) throws Exception {
		UserManualsDTO userManualsDTO=new UserManualsDTO();
		UserManualLevelThree userManualLevelThree= userManualDao.getUserManualThreeById(id);
		if(userManualLevelThree.getTitle()!=null ){
			userManualsDTO.setTitle(userManualLevelThree.getTitle());
		}
		if(userManualLevelThree.getDescription()!=null ){
			userManualsDTO.setDescription(userManualLevelThree.getDescription());
		}
		if(userManualLevelThree.getCreatedBy()!=null ){
			userManualsDTO.setCreatedBy(userManualLevelThree.getCreatedBy().getFullName());
		}
		if(userManualLevelThree.getUpdatedBy()!=null ){
			userManualsDTO.setUpdatedBy(userManualLevelThree.getUpdatedBy().getFullName());
		}
		if(userManualLevelThree.getCreatedDate()!=null ){
			userManualsDTO.setCreatedDate(userManualLevelThree.getCreatedDate().toString());
		}
		if(userManualLevelThree.getUpdatedDate()!=null ){
			userManualsDTO.setUpdatedDate(userManualLevelThree.getUpdatedDate().toString());
		}
		userManualsDTO.setPublishOn(userManualLevelThree.getPublishOn().toString());
		userManualsDTO.setUrlName(userManualLevelThree.getUploadUrl());
		userManualsDTO.setUserManualAttachment(userManualLevelThree.getUserManualAttachment());
		return userManualsDTO;
	}

	
	
	
}
