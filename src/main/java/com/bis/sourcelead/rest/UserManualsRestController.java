package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.bis.sourcelead.entity.UserManualLevelThree;
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
	public @ResponseBody boolean storeUserManuals(@RequestParam(value = "saveType", required = false) String saveType,UserManualsDTO userManualsDTO,HttpServletRequest request) throws Exception {
    	//userManualsDTO.setLevel(userManualLevel);
    	userManualsDTO.setStaticPath(staticPath);
    	userManualService.storeUserManuals(userManualsDTO);
		
		 return true;
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
			    String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
				String[] tableColumns = {"title", "description", "level"};
			    int sortColumnIndex = 1; //by default sorting using updated date
				String sortColumnOrder = "asc"; //by default descending order
				if(userManualsDTO.getOrder() != null && !userManualsDTO.getOrder().isEmpty()) {
					sortColumnIndex = Integer.parseInt(userManualsDTO.getOrder().get(0).get("column"));
					sortColumnOrder = userManualsDTO.getOrder().get(0).get("dir");
				}
				userManualsDTO.setSortColumn(tableColumns[sortColumnIndex]);
				userManualsDTO.setSortOrder(sortColumnOrder);
				ResponseList<UserManualsDTO>  UserManualList=userManualService.getAllUserManuallsForDynamicList(userManualsDTO);
				return UserManualList;
		 }
 		@RequestMapping(value = "/getAllUseManualLevelTwoList", method = RequestMethod.POST)
		 public @ResponseBody   ResponseList<UserManualsDTO> getAllUseManualLevelTwoList(@RequestParam(value = "id", required = false) Long id,UserManualsDTO userManualsDTO,HttpServletRequest request) throws Exception {
			  
			 ResponseList<UserManualsDTO> responseList=new  ResponseList<>();
			    String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
				String[] tableColumns = {"title", "description", "level"};
				int sortColumnIndex = 5; //by default sorting using updated date
				String sortColumnOrder = "asc"; //by default descending order
				
				if(userManualsDTO.getOrder() != null && !userManualsDTO.getOrder().isEmpty()) {
					sortColumnIndex = Integer.parseInt(userManualsDTO.getOrder().get(0).get("column"));
					sortColumnOrder = userManualsDTO.getOrder().get(0).get("dir");
				}
				userManualsDTO.setSortColumn(tableColumns[sortColumnIndex]);
				userManualsDTO.setSortOrder(sortColumnOrder);
				
				ResponseList<UserManualsDTO>  UserManualList=userManualService.getAllLevelTwoDynamicList(userManualsDTO,id);
				return UserManualList;
		 }
 		
 		@RequestMapping(value = "/getAllUseManualLevelThreeList", method = RequestMethod.POST)
		 public @ResponseBody   ResponseList<UserManualsDTO> getAllUseManualLevelThreeList(@RequestParam(value = "id", required = false) Long id,UserManualsDTO userManualsDTO,HttpServletRequest request) throws Exception {
			  
			 ResponseList<UserManualsDTO> responseList=new  ResponseList<>();
			    String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
				String[] tableColumns = {"title", "description", "level"};
			    int sortColumnIndex = 1; //by default sorting using updated date
				String sortColumnOrder = "asc"; //by default descending order
				if(userManualsDTO.getOrder() != null && !userManualsDTO.getOrder().isEmpty()) {
					sortColumnIndex = Integer.parseInt(userManualsDTO.getOrder().get(0).get("column"));
					sortColumnOrder = userManualsDTO.getOrder().get(0).get("dir");
				}
				userManualsDTO.setSortColumn(tableColumns[sortColumnIndex]);
				userManualsDTO.setSortOrder(sortColumnOrder);
				ResponseList<UserManualsDTO>  UserManualList=userManualService.getAllLevelThreeDynamicList(userManualsDTO,id);
				return UserManualList;
		 }
 		
 		@RequestMapping(value = "/deleteUserManualThree", method = RequestMethod.GET)
		 public @ResponseBody String deleteUserManualThree(@RequestParam(value = "userManualId", required = true) Long userManualId,HttpServletRequest request) throws Exception {
 			userManualService.deleteUserManualThree(userManualId);
			 
				return "success";
		 }
 		@RequestMapping(value = "/deleteUserManualTwo", method = RequestMethod.GET)
		 public @ResponseBody   String deleteUserManualTwo(@RequestParam(value = "userManualId", required = false) Long userManualId,HttpServletRequest request) throws Exception {
			  
 			userManualService.deleteUserManualTwo(userManualId);
				return "success";
		 }
 		@RequestMapping(value = "/deleteUserManualOne", method = RequestMethod.GET)
		 public @ResponseBody   String deleteUserManualOne(@RequestParam(value = "userManualId", required = false) Long userManualId,HttpServletRequest request) throws Exception {
			  
 			userManualService.deleteUserManualOne(userManualId);
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
			  
			UserManualsDTO userManualsDTO=userManualService.getUserManualTwoById(userManualId);
			
			return userManualsDTO;
		 }
 		@RequestMapping(value = "/userManualThreeSummary", method = RequestMethod.POST)
		 public @ResponseBody   UserManualsDTO userManualThreeSummary(@RequestParam(value = "userManualId", required = false) Long userManualId,HttpServletRequest request) throws Exception {
			  
			UserManualsDTO userManualsDTO=userManualService.getUserManualThreeById(userManualId);
			
			return userManualsDTO;
		 }
 		
 		@RequestMapping(value = "/publishUserManualThree", method = RequestMethod.POST)
		 public @ResponseBody   boolean publishUserManualThree(@RequestParam(value = "userManualId", required = false) Long userManualId,HttpServletRequest request) throws Exception {
			  
			return userManualService.publishUserManualThree(userManualId);
			
		 }
 		
 		
 		// for publishing the data in the website
 		
 		@RequestMapping(value = "/ws/getUserManualHelperOneTitles", method = RequestMethod.GET)
 		public @ResponseBody List<UserManualsDTO> getUserManualHelperOneTitles(HttpServletRequest request) throws Exception {
 			
 			List<UserManualsDTO> UserManualList=userManualService.getUserManualHelperOneTitles();
 			
 			return UserManualList;
 		}
 		
 		@RequestMapping(value = "/ws/getUserManualHelperTwoTitles", method = RequestMethod.GET)
 		public @ResponseBody List<UserManualsDTO> getUserManualHelperTwoTitles(@RequestParam(value = "userManualId", required = false) Long userManualId,HttpServletRequest request) throws Exception {
 			
 			List<UserManualsDTO> UserManualList=userManualService.getUserManualHelperTwoTitles(userManualId);
 			
 			return UserManualList;
 		}
 		
 		
 		@RequestMapping(value = "/ws/getUserManualHelperThree", method = RequestMethod.GET)
 		public @ResponseBody List<UserManualsDTO> getUserManualHelperThree(@RequestParam(value = "userManualId", required = false) Long userManualId,HttpServletRequest request) throws Exception {
 			
 			List<UserManualsDTO> UserManualList=userManualService.getUserManualHelperThreeTitles(userManualId);
 			
 			return UserManualList;
 		}
	
}
