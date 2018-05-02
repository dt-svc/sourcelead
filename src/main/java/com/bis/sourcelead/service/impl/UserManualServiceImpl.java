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
import com.bis.sourcelead.entity.OrgLocations;
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
	public String storeUserManuals(UserManualsDTO userManualsDTO) throws Exception {
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
			if(userManualsDTO.getDescription()!=null){
				userManualLevelOne.setDescription(userManualsDTO.getDescription());
			}
			
			UserManualLevelOne levelOne=userManualDao.storeUserManuals(userManualLevelOne);
			if(levelOne != null){
				return "success";
			}
		}else if((userManualsDTO.getLevelName()).equalsIgnoreCase("Level-2")){
			
			if(userManualsDTO.getId()!=null && StringUtils.isNotBlank(userManualsDTO.getId())){
				userManualLevelTwo=userManualDao.getUserManualTwoById(Long.valueOf(userManualsDTO.getId()));
				if(user!=null){
					userManualLevelTwo.setUpdatedBy(user);
				}
				userManualLevelTwo.setUpdatedDate(new Date());
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
			if(userManualsDTO.getDescription()!=null){
				userManualLevelTwo.setDescription(userManualsDTO.getDescription());
			}
			UserManualLevelTwo levelTwo=userManualDao.storeUserManualstwo(userManualLevelTwo);
			if(levelTwo != null){
				return "success";
			}
			
		}else{
			
			if(userManualsDTO.getId()!=null && StringUtils.isNotBlank(userManualsDTO.getId())){
				userManualLevelThree=userManualDao.getUserManualThreeById(Long.valueOf(userManualsDTO.getId()));
				if(user!=null){
					userManualLevelThree.setUpdatedBy(user);
				}
				userManualLevelThree.setUpdatedDate(new Date());
				if(userManualsDTO.getSave().equalsIgnoreCase("publish")){
					userManualLevelThree.setStatus("published");
				}
				
			}else{
				userManualLevelThree=new UserManualLevelThree();
				if(user!=null){
					userManualLevelThree.setCreatedBy(user);
					}
				String levelThreeUUID=SourceLeadRandomCodeHelper.generateRandomOrgCode();
				userManualLevelThree.setLevelThreeUUID(levelThreeUUID);
				userManualLevelThree.setCreatedDate(commonUtil.currentDate());
				
				if(userManualsDTO.getSave().equalsIgnoreCase("publish")){
					userManualLevelThree.setStatus("published");
				}else
					userManualLevelThree.setStatus("publish");
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
				}else {
					userManualLevelThree.setUploadUrl(null);
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
			UserManualLevelThree levelThree=userManualDao.storeUserManualsThree(userManualLevelThree);
			if(levelThree != null){
				return "success";
			}
		}
			
		return "fail";
		
	}

	@Override
	public UserManualLevelOne getUserManualByTitle(String title) throws Exception {
		
		return userManualDao.getUserManualByTitle( title);
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
			if(uml.getId() !=null){
				umd.setId(uml.getId().toString());
			}
			if(uml.getTitle() !=null){
				umd.setTitle(uml.getTitle());
			}
			if(uml.getLevelOneUUID() !=null){
				umd.setUserManualUUID(uml.getLevelOneUUID());
			}
			if(uml.getDescription() !=null){
				umd.setDescription(uml.getDescription().replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " "));
			}
				
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
			
			if(uml.getId() !=null){
				umd.setId(uml.getId().toString());
			}
			if(uml.getTitle() !=null){
				umd.setTitle(uml.getTitle());
			}
			if(uml.getLevelTwoUUID() !=null){
				umd.setUserManualUUID(uml.getLevelTwoUUID());
			}
			if(uml.getDescription() !=null){
				umd.setDescription(uml.getDescription().replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " "));
			}
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
			if(uml.getId() !=null){
				umd.setId(uml.getId().toString());
			}
			if(uml.getTitle() !=null){
				umd.setTitle(uml.getTitle());
			}
			if(uml.getLevelThreeUUID() !=null){
				umd.setUserManualUUID(uml.getLevelThreeUUID());
			}
			if(uml.getDescription() !=null){
				umd.setDescription(uml.getDescription().replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " "));
			}
			if(uml.getStatus() !=null){
				umd.setStatus(uml.getStatus());
			}
			
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
	public UserManualLevelThree deleteUserManualThree(Long id) throws Exception{
		UserManualLevelThree userManualLevelThree=userManualDao.getUserManualThreeById(id);
		UserManualLevelThree levelThree= userManualDao.deleteUserManualThree(userManualLevelThree);
		return levelThree;
	}

	@Override
	public UserManualLevelTwo deleteUserManualTwo(Long id) throws Exception {
		UserManualLevelTwo userManualLevelTwo=userManualDao.getUserManualTwoById(id);
		UserManualLevelTwo levelTwo=userManualDao.deleteUserManualTwo(userManualLevelTwo);
		return levelTwo;
	}

	@Override
	public UserManualLevelOne deleteUserManualOne(Long id) throws Exception {
		UserManualLevelOne UserManualLevelOne=userManualDao.getUserManualById(id);
		UserManualLevelOne levelOne=userManualDao.deleteUserManualOne(UserManualLevelOne);
		return levelOne;
	}

	@Override
	public List<UserManualAttachment> getUsermanualAttachmentList(Long levelThreeId) {
		return userManualDao.getUsermanualAttachmentList(levelThreeId);
	}

	@Override
	public UserManualAttachment deleteUserManualAttachments(Long id) throws Exception {
		UserManualAttachment userManualAttList=userManualDao.getUserManualAttchListById(id);
		UserManualAttachment attchment=userManualDao.deleteUserManualAttachments(userManualAttList);
		return attchment;
	}

	@Override
	public UserManualsDTO getUserManualOneById(Long id) throws Exception{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		UserManualsDTO userManualsDTO=new UserManualsDTO();
		UserManualLevelOne userManualLevelOne= userManualDao.getUserManualById(id);
		if(userManualLevelOne.getTitle()!=null ){
			userManualsDTO.setTitle(userManualLevelOne.getTitle());
		}
		if(userManualLevelOne.getDescription()!=null ){
			userManualsDTO.setDescription(userManualLevelOne.getDescription().replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " "));
		}
		if(userManualLevelOne.getCreatedBy()!=null ){
			userManualsDTO.setCreatedBy(userManualLevelOne.getCreatedBy().getFullName());
		}
		if(userManualLevelOne.getUpdatedBy()!=null ){
			userManualsDTO.setUpdatedBy(userManualLevelOne.getUpdatedBy().getFullName());
		}
		if(userManualLevelOne.getCreatedDate()!=null ){
			String createddateStr = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), userManualLevelOne.getCreatedDate());
			userManualsDTO.setCreatedDate(createddateStr);
		}
		if(userManualLevelOne.getUpdatedDate()!=null ){
			String updateddateStr = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), userManualLevelOne.getUpdatedDate());
			userManualsDTO.setUpdatedDate(updateddateStr);
		}
		return userManualsDTO;
	}

	@Override
	public UserManualsDTO getUserManualTwoById(Long id) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		UserManualsDTO userManualsDTO=new UserManualsDTO();
		UserManualLevelTwo userManualLevelTwo= userManualDao.getUserManualTwoById(id);
		if(userManualLevelTwo.getTitle()!=null ){
			userManualsDTO.setTitle(userManualLevelTwo.getTitle());
		}
		//String strippedText = htmlText.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ");
		if(userManualLevelTwo.getDescription()!=null ){
			userManualsDTO.setDescription(userManualLevelTwo.getDescription().replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " "));
		}
		if(userManualLevelTwo.getCreatedBy()!=null ){
			userManualsDTO.setCreatedBy(userManualLevelTwo.getCreatedBy().getFullName());
		}
		if(userManualLevelTwo.getUpdatedBy()!=null ){
			userManualsDTO.setUpdatedBy(userManualLevelTwo.getUpdatedBy().getFullName());
		}
		if(userManualLevelTwo.getCreatedDate()!=null ){
			String createddateStr = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), userManualLevelTwo.getCreatedDate());
			userManualsDTO.setCreatedDate(createddateStr);
		}
		if(userManualLevelTwo.getUpdatedDate()!=null ){
			String updateddateStr = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), userManualLevelTwo.getUpdatedDate());
			userManualsDTO.setUpdatedDate(updateddateStr);
		}
		return userManualsDTO;
	}

	@Override
	public UserManualsDTO getUserManualThreeById(Long id) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

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
			String createddateStr = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), userManualLevelThree.getCreatedDate());
			userManualsDTO.setCreatedDate(createddateStr);
		}
		if(userManualLevelThree.getUpdatedDate()!=null ){
			String updateddateStr = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), userManualLevelThree.getUpdatedDate());
			userManualsDTO.setUpdatedDate(updateddateStr);
		}
		if(userManualLevelThree.getPublishOn()!=null ){
			userManualsDTO.setPublishOn(userManualLevelThree.getPublishOn().toString());
		}
		if(userManualLevelThree.getUploadUrl()!=null ){
			userManualsDTO.setUrlName(userManualLevelThree.getUploadUrl());
		}
		
		if(id!=null){

			List<UserManualAttachment> attchachmentList=new ArrayList<>();
			attchachmentList=userManualDao.getUsermanualAttachmentList(id);
			if(attchachmentList!=null){
				userManualsDTO.setUserManualAttachment(attchachmentList);
			}
			
		}
		return userManualsDTO;
	}
	
	
	@Override
	public UserManualLevelTwo getUserManualTwoByTitle(String UserManTitle) throws Exception {
		return userManualDao.getUserManualTwoByTitle(UserManTitle);
	}

	@Override
	public UserManualLevelThree getUserManualThreeByTitle(String UserManTitle) throws Exception {
		return userManualDao.getUserManualThreeByTitle(UserManTitle);
	}
	
	//for publishing the data in website

	@Override
	public List<UserManualsDTO> getUserManualHelperOneTitles() throws Exception {
		
		List<UserManualsDTO> manualList=new ArrayList<>();
		
		List<Object[]> userManualLevelOneList = userManualDao.getUserManualHelperOneTitles();
		
		for(Object[] object:userManualLevelOneList){
			UserManualsDTO userManualsDTO=new UserManualsDTO();
			userManualsDTO.setTitle((String)object[0]);
			userManualsDTO.setId(object[1].toString());
			manualList.add(userManualsDTO);
		}
		// TODO Auto-generated method stub
		return manualList;
	}
	
	@Override
	public List<UserManualsDTO> getUserManualHelperTwoTitles(Long userManualId) throws Exception {
		List<UserManualsDTO> manualList=new ArrayList<>();
		List<Object[]> userManualLevelOneList = userManualDao.getUserManualHelperTwoTitles(userManualId);
		
		for(Object[] object:userManualLevelOneList){
			UserManualsDTO userManualsDTO=new UserManualsDTO();
			userManualsDTO.setId(object[1].toString());
			userManualsDTO.setTitle(object[0].toString());
			manualList.add(userManualsDTO);
		}
		
		return manualList;
	}

	@Override
	public List<UserManualsDTO> getUserManualHelperThreeTitles(Long userManualId) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<UserManualsDTO> manualList=new ArrayList<>();
		List<UserManualLevelThree> userManualLevelThreeList = userManualDao.getUserManualHelperThreeTitles(userManualId);
		
		for(UserManualLevelThree userManualLevelThree:userManualLevelThreeList){
			UserManualsDTO userManualsDTO=new UserManualsDTO();
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
			
			/*if(userManualLevelThree.getCreatedDate()!=null ){
				String createddateStr = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), userManualLevelThree.getCreatedDate());
				userManualsDTO.setCreatedDate(createddateStr);
			}
			if(userManualLevelThree.getUpdatedDate()!=null ){
				String updateddateStr = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), userManualLevelThree.getUpdatedDate());
				userManualsDTO.setUpdatedDate(updateddateStr);
			}*/
			if(userManualLevelThree.getPublishOn()!=null ){
				userManualsDTO.setPublishOn(userManualLevelThree.getPublishOn().toString());
			}
			if(userManualLevelThree.getUploadUrl()!=null ){
				userManualsDTO.setUrlName(userManualLevelThree.getUploadUrl());
			}
			
			if(userManualId!=null){

				List<UserManualAttachment> attchachmentList=new ArrayList<>();
				attchachmentList=userManualDao.getUsermanualAttachmentList(userManualLevelThree.getId());
				if(attchachmentList!=null){
					userManualsDTO.setUserManualAttachment(attchachmentList);
				}
				
			}
			manualList.add(userManualsDTO);
		}
		return manualList;
	}

	@Override
	public boolean publishUserManualThree(Long id) throws Exception {
		
		UserManualLevelThree userManualLevelThree=userManualDao.getUserManualThreeById(id);
		if(userManualLevelThree!=null){
			userManualLevelThree.setStatus("published");
			userManualDao.storeUserManualsThree(userManualLevelThree);
			return true;
		}
		
		return false;
	}

}
