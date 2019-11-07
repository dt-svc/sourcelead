package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.dto.CandidateCertificationDTO;
import com.bis.sourcelead.dto.ProjectTasksDTO;
import com.bis.sourcelead.dto.ProjectsDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserManualsDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserManualAttachment;
import com.bis.sourcelead.entity.UserManualLevelOne;
import com.bis.sourcelead.entity.UserManualLevelThree;
import com.bis.sourcelead.entity.UserManualLevelTwo;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.UserManualService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
/**
 * Handles requests for the Candidate service.
 * 
 * @author Prasad K
 * @date 30th June 2016
 *
 */

@RestController
@RequestMapping("/app")
public class UserManualsRestController {

	private static final Logger logger = LoggerFactory.getLogger(UserManualsRestController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	UserManualService userManualService;
	
	@Value("${static.path}")
	private String staticPath;
	
	
    @RequestMapping(value = "/storeUserManuals", method = RequestMethod.POST)
	public @ResponseBody Response<String> storeUserManuals(@RequestParam(value = "saveType", required = false) String saveType,UserManualsDTO userManualsDTO,HttpServletRequest request) throws Exception {
    	
    	Response<String> response = new Response<>();
    	try{
    		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
    		if (user != null && Validator.validate(userManualsDTO)) {
    			
    			userManualsDTO.setStaticPath(staticPath);
    	    String value=userManualService.storeUserManuals(userManualsDTO);
    			
    			if (value.equalsIgnoreCase("success")) {
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					response.setResponse(Result.FAILED.name());
					response.setResponseMessage(Result.FAILED.getValue());
				}
    			
    		}else if (user == null) {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			} else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
    		
    	}catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		
		 return response;
    }
    
 // to validate user Manual Titles
 		@RequestMapping(value = "/validateOrgLocationUserManTitle", method = RequestMethod.GET)
 		public @ResponseBody Boolean validateOrgLocationUserManTitle(@RequestParam(value = "title", required = false) String title,@RequestParam(value = "levelValue", required = false) String levelValue,HttpServletRequest request) throws Exception {
 			
 			boolean flag = false;
 			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
 			if(StringUtils.isNotBlank(title)) {
 				if(StringUtils.isNotBlank(levelValue) && levelValue.equalsIgnoreCase("level-1")){
 					UserManualLevelOne userManualLevelOne = userManualService.getUserManualByTitle(title.trim());
 	 				if(userManualLevelOne != null) {
 							if(userManualLevelOne.getTitle().equalsIgnoreCase(title.trim())) {
 								flag = true;
 							}
 	 				}
 				}else{
 					if(StringUtils.isNotBlank(levelValue) && levelValue.equalsIgnoreCase("level-2")){
 						UserManualLevelTwo userManualLevelTwo = userManualService.getUserManualTwoByTitle(title.trim());
 	 					if(userManualLevelTwo != null) {
 								if(userManualLevelTwo.getTitle().equalsIgnoreCase(title.trim())) {
 									flag = true;
 								}
 	 					}
 					}
 					
 				}
				/*{
					if(StringUtils.isNotBlank(levelValue) && levelValue.equalsIgnoreCase("level-3")){
	 					UserManualLevelThree userManualLevelThree = userManualService.getUserManualThreeByTitle(title.trim());
	 					if(userManualLevelThree != null) {
								if(userManualLevelThree.getTitle().equalsIgnoreCase(title.trim())) {
									flag = true;
								}
	 				}
	 				
					}
				}*/
 			}
 			
 			return flag;
 		}
 		
 		///getLevelOneUserManTitles
 		
 		@RequestMapping(value = "/getLevelOneUserManTitles", method = RequestMethod.GET)
 		public @ResponseBody List<UserManualsDTO> getLevelOneUserManTitles(HttpServletRequest request) throws Exception {
 			List<UserManualsDTO> userManualDTOList=new ArrayList<>();
 			
 			List<UserManualLevelOne> UserManualList=userManualService.getAllUserManualls();
 			
 			for(UserManualLevelOne userManual:UserManualList){
 				UserManualsDTO userManualsDTO=new UserManualsDTO();
 				userManualsDTO.setTitle(userManual.getTitle());
 				userManualsDTO.setId(userManual.getId().toString());
 				userManualsDTO.setDescription(userManual.getDescription());
 				userManualsDTO.setUserManualUUID(userManual.getLevelOneUUID());
 				//userManualsDTO.setStatus(userManual.getStatus());
 				userManualDTOList.add(userManualsDTO);
 			}
 			return userManualDTOList;
 		}
 		
 		@RequestMapping(value = "/getLevelTwoUserManTitles", method = RequestMethod.GET)
 		public @ResponseBody List<UserManualsDTO> getLevelTwoUserManTitles(@RequestParam(value = "level1Id", required = false) Long level1Id,HttpServletRequest request) throws Exception {
 			List<UserManualsDTO> userManualDTOList=new ArrayList<>();
 			
 			List<UserManualLevelTwo> UserManualList=userManualService.getAllUserManuallsTwoById(level1Id);
 			
 			for(UserManualLevelTwo userManual:UserManualList){
 				UserManualsDTO userManualsDTO=new UserManualsDTO();
 				userManualsDTO.setTitle(userManual.getTitle());
 				userManualsDTO.setId(userManual.getId().toString());
 				userManualsDTO.setUserManualUUID(userManual.getLevelTwoUUID());
 				userManualDTOList.add(userManualsDTO);
 			}
 			return userManualDTOList;
 		}
 		
 		@RequestMapping(value = "/getAllUseManualLevel1List", method = RequestMethod.POST)
		 public @ResponseBody   ResponseList<UserManualsDTO> getAllUseManualLevel1List(UserManualsDTO userManualsDTO,HttpServletRequest request) throws Exception {
			  
 			 ResponseList<UserManualsDTO> responseList=new  ResponseList<>();
 			 
 			 try{
 				User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
 				if (user != null) {
 					String[] tableColumns = {"title", "description", "level"};
 	 			    int sortColumnIndex = 1; //by default sorting using updated date
 	 				String sortColumnOrder = "asc"; //by default descending order
 	 				if(userManualsDTO.getOrder() != null && !userManualsDTO.getOrder().isEmpty()) {
 	 					sortColumnIndex = Integer.parseInt(userManualsDTO.getOrder().get(0).get("column"));
 	 					sortColumnOrder = userManualsDTO.getOrder().get(0).get("dir");
 	 				}
 	 				userManualsDTO.setSortColumn(tableColumns[sortColumnIndex]);
 	 				userManualsDTO.setSortOrder(sortColumnOrder);
 	 				responseList=userManualService.getAllUserManuallsForDynamicList(userManualsDTO);
 	 					responseList.setResponse(Result.SUCCESS.name());
 	 	 				responseList.setResponseMessage(Result.SUCCESS.getValue());
 	 				
 				}else if(user == null){
 					responseList.setResponse(Result.SESSION_TIMEDOUT.name());
 					responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
 				}else{
 					responseList.setResponse(Result.INVALID_DATA.name());
 					responseList.setResponseMessage(Result.INVALID_DATA.getValue());
 				}
 				
 				
 			 }catch (Exception e) {
 				responseList.setResponse(Result.AWKWARD.name());
 				responseList.setResponseMessage(Result.AWKWARD.getValue());
 				logger.error(ExceptionUtils.getFullStackTrace(e));
 			}
 			return responseList; 
		 }
 		@RequestMapping(value = "/getAllUseManualLevelTwoList", method = RequestMethod.POST)
		 public @ResponseBody   ResponseList<UserManualsDTO> getAllUseManualLevelTwoList(@RequestParam(value = "id", required = false) Long id,UserManualsDTO userManualsDTO,HttpServletRequest request) throws Exception {
			  	
 				ResponseList<UserManualsDTO> responseList=new  ResponseList<>();
 			 
 			 try{
 				User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
 				if(user != null){
 					String[] tableColumns = {"title", "description", "level"};
 					int sortColumnIndex = 5; //by default sorting using updated date
 					String sortColumnOrder = "asc"; //by default descending order
 					
 					if(userManualsDTO.getOrder() != null && !userManualsDTO.getOrder().isEmpty()) {
 						sortColumnIndex = Integer.parseInt(userManualsDTO.getOrder().get(0).get("column"));
 						sortColumnOrder = userManualsDTO.getOrder().get(0).get("dir");
 					}
 					userManualsDTO.setSortColumn(tableColumns[sortColumnIndex]);
 					userManualsDTO.setSortOrder(sortColumnOrder);
 					
 					responseList=userManualService.getAllLevelTwoDynamicList(userManualsDTO,id);
 					responseList.setResponse(Result.SUCCESS.name());
	 	 			responseList.setResponseMessage(Result.SUCCESS.getValue());
 				}else if(user == null){
 					responseList.setResponse(Result.SESSION_TIMEDOUT.name());
 					responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
 				}else{
 					responseList.setResponse(Result.INVALID_DATA.name());
 					responseList.setResponseMessage(Result.INVALID_DATA.getValue());
 				}
 				 
 			 		}catch (Exception e) {
  				responseList.setResponse(Result.AWKWARD.name());
  				responseList.setResponseMessage(Result.AWKWARD.getValue());
  				logger.error(ExceptionUtils.getFullStackTrace(e));
 			 		}
 				
				
				return responseList;
		 }
 		
 		@RequestMapping(value = "/getAllUseManualLevelThreeList", method = RequestMethod.POST)
		 public @ResponseBody   ResponseList<UserManualsDTO> getAllUseManualLevelThreeList(@RequestParam(value = "id", required = false) Long id,UserManualsDTO userManualsDTO,HttpServletRequest request) throws Exception {
			  	
 				ResponseList<UserManualsDTO> responseList=new  ResponseList<>();
			 
 				try{
				User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				if(user != null){
					String[] tableColumns = {"title", "description", "level"};
				    int sortColumnIndex = 1; //by default sorting using updated date
					String sortColumnOrder = "asc"; //by default descending order
					if(userManualsDTO.getOrder() != null && !userManualsDTO.getOrder().isEmpty()) {
						sortColumnIndex = Integer.parseInt(userManualsDTO.getOrder().get(0).get("column"));
						sortColumnOrder = userManualsDTO.getOrder().get(0).get("dir");
					}
					userManualsDTO.setSortColumn(tableColumns[sortColumnIndex]);
					userManualsDTO.setSortOrder(sortColumnOrder);
					responseList=userManualService.getAllLevelThreeDynamicList(userManualsDTO,id);
					responseList.setResponse(Result.SUCCESS.name());
	 	 			responseList.setResponseMessage(Result.SUCCESS.getValue());
					}else if(user == null){
	 					responseList.setResponse(Result.SESSION_TIMEDOUT.name());
	 					responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
	 				}else{
	 					responseList.setResponse(Result.INVALID_DATA.name());
	 					responseList.setResponseMessage(Result.INVALID_DATA.getValue());
	 				}
 				}catch (Exception e) {
 	  				responseList.setResponse(Result.AWKWARD.name());
 	  				responseList.setResponseMessage(Result.AWKWARD.getValue());
 	  				logger.error(ExceptionUtils.getFullStackTrace(e));
 	 			 }
				
				return responseList;
		 }
 		
 		@RequestMapping(value = "/deleteUserManualThree", method = RequestMethod.GET)
		 public @ResponseBody Response<String> deleteUserManualThree(@RequestParam(value = "userManualId", required = true) Long userManualId,HttpServletRequest request) throws Exception {
 			
 			Response<String> response = new Response<>();
 			
			try{
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if(user != null){
				UserManualLevelThree levelThree=userManualService.deleteUserManualThree(userManualId);
				if(levelThree != null){
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					response.setResponse(Result.FAILED.name());
					response.setResponseMessage(Result.FAILED.getValue());
				}
				
				}else if(user == null){
					response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
 				}else{
 					response.setResponse(Result.INVALID_DATA.name());
 					response.setResponseMessage(Result.INVALID_DATA.getValue());
 				}
			}catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
	  			logger.error(ExceptionUtils.getFullStackTrace(e));
	 			 }
			
 			
				return response;
		 }
 		@RequestMapping(value = "/deleteUserManualTwo", method = RequestMethod.GET)
		 public @ResponseBody   Response<String> deleteUserManualTwo(@RequestParam(value = "userManualId", required = false) Long userManualId,HttpServletRequest request) throws Exception {
 			Response<String> response = new Response<>();
 			
			try{
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if(user != null){
				UserManualLevelTwo levelTwo=userManualService.deleteUserManualTwo(userManualId);
				if(levelTwo != null){
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					response.setResponse(Result.FAILED.name());
					response.setResponseMessage(Result.FAILED.getValue());
				}
				
				}else if(user == null){
					response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
 				}else{
 					response.setResponse(Result.INVALID_DATA.name());
 					response.setResponseMessage(Result.INVALID_DATA.getValue());
 				}
			}catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
	  			logger.error(ExceptionUtils.getFullStackTrace(e));
	 			 }
				return response; 
		 }
 		@RequestMapping(value = "/deleteUserManualOne", method = RequestMethod.GET)
		 public @ResponseBody   Response<String> deleteUserManualOne(@RequestParam(value = "userManualId", required = false) Long userManualId,HttpServletRequest request) throws Exception {
 			Response<String> response = new Response<>();
 			
			try{
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if(user != null){
				UserManualLevelOne levelOne=userManualService.deleteUserManualOne(userManualId);
				if(levelOne != null){
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					response.setResponse(Result.FAILED.name());
					response.setResponseMessage(Result.FAILED.getValue());
				}
				
				}else if(user == null){
					response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
 				}else{
 					response.setResponse(Result.INVALID_DATA.name());
 					response.setResponseMessage(Result.INVALID_DATA.getValue());
 				}
			}catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
	  			logger.error(ExceptionUtils.getFullStackTrace(e));
	 			 }
				return response;
		 }
 		@RequestMapping(value = "/deleteManualAttachment", method = RequestMethod.POST)
		 public @ResponseBody   Response<String> deleteManualAttachment(@RequestParam(value = "attachmentId", required = false) Long attachmentId,HttpServletRequest request) throws Exception {
 			Response<String> response = new Response<>();
 			
			try{
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if(user != null){
				UserManualAttachment attchment=userManualService.deleteUserManualAttachments(attachmentId);
				if(attchment != null){
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					response.setResponse(Result.FAILED.name());
					response.setResponseMessage(Result.FAILED.getValue());
				}
				
				}else if(user == null){
					response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
 				}else{
 					response.setResponse(Result.INVALID_DATA.name());
 					response.setResponseMessage(Result.INVALID_DATA.getValue());
 				}
			}catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
	  			logger.error(ExceptionUtils.getFullStackTrace(e));
	 			 }
				return response;
		 }
 		
 		@RequestMapping(value = "/userManualOneSummary", method = RequestMethod.POST)
		 public @ResponseBody   Response<UserManualsDTO> userManualOneSummary(@RequestParam(value = "userManualId", required = false) Long userManualId,HttpServletRequest request) throws Exception {
 			Response<UserManualsDTO> reponse = new Response<>(); 
 			
 			try{
 				User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
 				if (user != null) {
 					UserManualsDTO userManualsDTO=userManualService.getUserManualOneById(userManualId);
 					if(userManualsDTO != null){
 						reponse.setObject(userManualsDTO);
 	 					reponse.setResponse(Result.SUCCESS.name());
 	 					reponse.setResponseMessage(Result.SUCCESS.getValue());
 					} else {
 						reponse.setResponse(Result.FAILED.name());
 						reponse.setResponseMessage(Result.FAILED.getValue());
 					}
 					
				}  else if (user == null) {
					reponse.setResponse(Result.SESSION_TIMEDOUT.name());
					reponse.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				} else {
					reponse.setResponse(Result.INVALID_DATA.name());
					reponse.setResponseMessage(Result.INVALID_DATA.getValue());
				}
 				
 			}catch (Exception e) {
 				reponse.setResponse(Result.AWKWARD.name());
 				reponse.setResponseMessage(Result.AWKWARD.getValue());
 				logger.error(ExceptionUtils.getFullStackTrace(e));
 			}
 			return reponse;
		 }
 		
 		@RequestMapping(value = "/userManualTwoSummary", method = RequestMethod.POST)
		 public @ResponseBody   Response<UserManualsDTO> userManualTwoSummary(@RequestParam(value = "userManualId", required = false) Long userManualId,HttpServletRequest request) throws Exception {
 			Response<UserManualsDTO> reponse = new Response<>(); 
 			
 			try{
 				
 				User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
 				if (user != null) {
 					UserManualsDTO userManualsDTO=userManualService.getUserManualTwoById(userManualId);
 					if(userManualsDTO != null){
 						reponse.setObject(userManualsDTO);
 	 					reponse.setResponse(Result.SUCCESS.name());
 	 					reponse.setResponseMessage(Result.SUCCESS.getValue());
 					} else {
 						reponse.setResponse(Result.FAILED.name());
 						reponse.setResponseMessage(Result.FAILED.getValue());
 					}
 					
 				} else if (user == null) {
					reponse.setResponse(Result.SESSION_TIMEDOUT.name());
					reponse.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				} else {
					reponse.setResponse(Result.INVALID_DATA.name());
					reponse.setResponseMessage(Result.INVALID_DATA.getValue());
				}
 				
 			}catch (Exception e) {
 				reponse.setResponse(Result.AWKWARD.name());
 				reponse.setResponseMessage(Result.AWKWARD.getValue());
 				logger.error(ExceptionUtils.getFullStackTrace(e));
 			}
			return reponse;
		 }
 		@RequestMapping(value = "/userManualThreeSummary", method = RequestMethod.POST)
		 public @ResponseBody   Response<UserManualsDTO> userManualThreeSummary(@RequestParam(value = "userManualId", required = false) Long userManualId,HttpServletRequest request) throws Exception {
			  
 			Response<UserManualsDTO> reponse = new Response<>(); 
 			
 			try{
 				User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
 				if (user != null) {
 					UserManualsDTO userManualsDTO=userManualService.getUserManualThreeById(userManualId);
 					if(userManualsDTO != null){
 						reponse.setObject(userManualsDTO);
 	 					reponse.setResponse(Result.SUCCESS.name());
 	 					reponse.setResponseMessage(Result.SUCCESS.getValue());
 					} else {
 						reponse.setResponse(Result.FAILED.name());
 						reponse.setResponseMessage(Result.FAILED.getValue());
 					}
 					
 				}else if (user == null) {
					reponse.setResponse(Result.SESSION_TIMEDOUT.name());
					reponse.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				} else {
					reponse.setResponse(Result.INVALID_DATA.name());
					reponse.setResponseMessage(Result.INVALID_DATA.getValue());
				}
 				
 			}catch (Exception e) {
 				reponse.setResponse(Result.AWKWARD.name());
 				reponse.setResponseMessage(Result.AWKWARD.getValue());
 				logger.error(ExceptionUtils.getFullStackTrace(e));
 			}
			UserManualsDTO userManualsDTO=userManualService.getUserManualThreeById(userManualId);
			
			return reponse;
		 }
 		
 		@RequestMapping(value = "/publishUserManualThree", method = RequestMethod.POST)
		 public @ResponseBody   boolean publishUserManualThree(@RequestParam(value = "userManualId", required = false) Long userManualId,HttpServletRequest request) throws Exception {
			  
			return userManualService.publishUserManualThree(userManualId);
			
		 }
 		
 		
 		// for publishing the data in the website
 		
 		@RequestMapping(value = "/ws/getUserManualHelperOneTitles", method = RequestMethod.GET)
 		public @ResponseBody ResponseList<UserManualsDTO> getUserManualHelperOneTitles(HttpServletRequest request) throws Exception {
 			ResponseList<UserManualsDTO> reponseList = new ResponseList<>();
 			try{
 				
 					List<UserManualsDTO> UserManualList=userManualService.getUserManualHelperOneTitles();
 					if (UserManualList != null) {
 						reponseList.setData(UserManualList);
 						reponseList.setResponse(Result.SUCCESS.name());
 						reponseList.setResponseMessage(Result.SUCCESS.getValue());
 					} else {
 						reponseList.setResponse(Result.FAILED.name());
 						reponseList.setResponseMessage(Result.FAILED.getValue());
 					}
 				
 			}catch (Exception e) {
 				reponseList.setResponse(Result.AWKWARD.name());
 				reponseList.setResponseMessage(Result.AWKWARD.getValue());
 				logger.error(ExceptionUtils.getFullStackTrace(e));
 			}
 			
 			
 			return reponseList;
 		}
 		
 		@RequestMapping(value = "/ws/getUserManualHelperTwoTitles", method = RequestMethod.GET)
 		public @ResponseBody ResponseList<UserManualsDTO> getUserManualHelperTwoTitles(@RequestParam(value = "userManualId", required = false) Long userManualId,HttpServletRequest request) throws Exception {
 			
 			ResponseList<UserManualsDTO> reponseList = new ResponseList<>();
 			
 			try{
 				List<UserManualsDTO> UserManualList=userManualService.getUserManualHelperTwoTitles(userManualId);
 	 			if(UserManualList != null){
 	 				reponseList.setData(UserManualList);
						reponseList.setResponse(Result.SUCCESS.name());
						reponseList.setResponseMessage(Result.SUCCESS.getValue());
					} else {
						reponseList.setResponse(Result.FAILED.name());
						reponseList.setResponseMessage(Result.FAILED.getValue());
					}
 				
 			}catch (Exception e) {
 				reponseList.setResponse(Result.AWKWARD.name());
 				reponseList.setResponseMessage(Result.AWKWARD.getValue());
 				logger.error(ExceptionUtils.getFullStackTrace(e));
 			}
 			
 			return reponseList;
 		}
 		
 		
 		@RequestMapping(value = "/ws/getUserManualHelperThree", method = RequestMethod.GET)
 		public @ResponseBody ResponseList<UserManualsDTO> getUserManualHelperThree(@RequestParam(value = "userManualId", required = false) Long userManualId,HttpServletRequest request) throws Exception {
 			
 			ResponseList<UserManualsDTO> reponseList = new ResponseList<>();
 			
 			try{
 				List<UserManualsDTO> UserManualList=userManualService.getUserManualHelperThreeTitles(userManualId);
 				if(UserManualList != null){
 	 				reponseList.setData(UserManualList);
						reponseList.setResponse(Result.SUCCESS.name());
						reponseList.setResponseMessage(Result.SUCCESS.getValue());
					} else {
						reponseList.setResponse(Result.FAILED.name());
						reponseList.setResponseMessage(Result.FAILED.getValue());
					}
 				
 			}catch (Exception e) {
 				reponseList.setResponse(Result.AWKWARD.name());
 				reponseList.setResponseMessage(Result.AWKWARD.getValue());
 				logger.error(ExceptionUtils.getFullStackTrace(e));
 			}
 			
 			return reponseList;
 		}
	
}
