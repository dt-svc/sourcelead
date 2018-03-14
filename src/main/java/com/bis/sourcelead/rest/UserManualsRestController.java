package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.dto.ProjectTasksDTO;
import com.bis.sourcelead.dto.ProjectsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserManualsDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.UserManualLevelOne;
import com.bis.sourcelead.entity.UserManualLevelTwo;
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
	public @ResponseBody boolean storeUserManuals(UserManualsDTO userManualsDTO,HttpServletRequest request) throws Exception {
    	//userManualsDTO.setLevel(userManualLevel);
    	userManualsDTO.setStaticPath(staticPath);
    	userManualService.storeUserManuals(userManualsDTO);
		
		 return true;
    }
    
 // to validate user Manual Titles
 		@RequestMapping(value = "/validateOrgLocationUserManTitle", method = RequestMethod.GET)
 		public @ResponseBody Boolean validateOrgLocationUserManTitle(@RequestParam(value = "title", required = false) String title,HttpServletRequest request) throws Exception {
 			
 			boolean flag = false;
 			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
 			if(StringUtils.isNotBlank(title)) {
 				UserManualLevelOne userManualLevelOne = userManualService.getUserManualByTitle(orgLocations.getId(), title.trim());
 				if(userManualLevelOne != null) {
						if(userManualLevelOne.getTitle().equalsIgnoreCase(title.trim())) {
							flag = true;
						}
				}
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
			    String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
				String[] tableColumns = {"Title", "Description", "Level","status"};
			    int sortColumnIndex = 1; //by default sorting using updated date
				String sortColumnOrder = "asc"; //by default descending order
				ResponseList<UserManualsDTO>  UserManualList=userManualService.getAllUserManuallsForDynamicList(userManualsDTO);
				return UserManualList;
		 }
 		@RequestMapping(value = "/getAllUseManualLevelTwoList", method = RequestMethod.POST)
		 public @ResponseBody   ResponseList<UserManualsDTO> getAllUseManualLevelTwoList(@RequestParam(value = "id", required = false) Long id,UserManualsDTO userManualsDTO,HttpServletRequest request) throws Exception {
			  
			 ResponseList<UserManualsDTO> responseList=new  ResponseList<>();
			    String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
				String[] tableColumns = {"Title", "Description", "Level","status"};
			    int sortColumnIndex = 1; //by default sorting using updated date
				String sortColumnOrder = "asc"; //by default descending order
				ResponseList<UserManualsDTO>  UserManualList=userManualService.getAllLevelTwoDynamicList(userManualsDTO,id);
				return UserManualList;
		 }
 		
 		@RequestMapping(value = "/getAllUseManualLevelThreeList", method = RequestMethod.POST)
		 public @ResponseBody   ResponseList<UserManualsDTO> getAllUseManualLevelThreeList(@RequestParam(value = "id", required = false) Long id,UserManualsDTO userManualsDTO,HttpServletRequest request) throws Exception {
			  
			 ResponseList<UserManualsDTO> responseList=new  ResponseList<>();
			    String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
				String[] tableColumns = {"Title", "Description", "Level","status"};
			    int sortColumnIndex = 1; //by default sorting using updated date
				String sortColumnOrder = "asc"; //by default descending order
				ResponseList<UserManualsDTO>  UserManualList=userManualService.getAllLevelThreeDynamicList(userManualsDTO,id);
				return UserManualList;
		 }
 		
 		@RequestMapping(value = "/deleteUserManualThree", method = RequestMethod.GET)
		 public @ResponseBody String deleteUserManualThree(@RequestParam(value = "userManualId", required = true) Long userManualId,HttpServletRequest request) throws Exception {
 			userManualService.deleteUserManualThree(userManualId);
			 
				return "success";
		 }
 		@RequestMapping(value = "/deleteUserManualTwo", method = RequestMethod.POST)
		 public @ResponseBody   String deleteUserManualTwo(@RequestParam(value = "UserManualId", required = false) Long UserManualId,HttpServletRequest request) throws Exception {
			  
 			userManualService.deleteUserManualTwo(UserManualId);
				return "success";
		 }
 		@RequestMapping(value = "/deleteUserManualOne", method = RequestMethod.POST)
		 public @ResponseBody   String deleteUserManualOne(@RequestParam(value = "UserManualId", required = false) Long UserManualId,HttpServletRequest request) throws Exception {
			  
 			userManualService.deleteUserManualOne(UserManualId);
				return "success";
		 }
 		@RequestMapping(value = "/deleteManualAttachment", method = RequestMethod.POST)
		 public @ResponseBody   String deleteManualAttachment(@RequestParam(value = "attachmentId", required = false) Long attachmentId,HttpServletRequest request) throws Exception {
			  
			userManualService.deleteUserManualAttachments(attachmentId);
				return "success";
		 }
 		
 		@RequestMapping(value = "/userManualOneSummary", method = RequestMethod.POST)
		 public @ResponseBody   UserManualsDTO userManualOneSummary(@RequestParam(value = "userManualId", required = false) Long userManualId,HttpServletRequest request) throws Exception {
			  
 			UserManualsDTO userManualsDTO=userManualService.getUserManualOneById(userManualId);
 			
 			return userManualsDTO;
		 }
 		
 		@RequestMapping(value = "/userManualTwoSummary", method = RequestMethod.POST)
		 public @ResponseBody   UserManualsDTO userManualTwoSummary(@RequestParam(value = "userManualId", required = false) Long userManualId,HttpServletRequest request) throws Exception {
			  
			UserManualsDTO userManualsDTO=userManualService.getUserManualOneById(userManualId);
			
			return userManualsDTO;
		 }
 		@RequestMapping(value = "/userManualThreeSummary", method = RequestMethod.POST)
		 public @ResponseBody   UserManualsDTO userManualThreeSummary(@RequestParam(value = "userManualId", required = false) Long userManualId,HttpServletRequest request) throws Exception {
			  
			UserManualsDTO userManualsDTO=userManualService.getUserManualOneById(userManualId);
			
			return userManualsDTO;
		 }
	
}
