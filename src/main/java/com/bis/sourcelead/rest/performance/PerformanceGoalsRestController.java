package com.bis.sourcelead.rest.performance;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.TeamDTO;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.dto.performance.PerformanceGoalsAssignToDTO;
import com.bis.sourcelead.dto.performance.PerformanceGoalsAttachmentsDTO;
import com.bis.sourcelead.dto.performance.PerformanceGoalsCommentsDTO;
import com.bis.sourcelead.dto.performance.PerformanceGoalsDTO;
import com.bis.sourcelead.dto.performance.PerformanceGoalsDoNutChartCountsDTO;
import com.bis.sourcelead.dto.performance.PerformanceTeamMembersDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.entity.performance.PerformanceGoals;
import com.bis.sourcelead.entity.performance.PerformanceGoalsAssignTo;
import com.bis.sourcelead.entity.performance.PerformanceGoalsAttachments;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.performance.PerformanceGoalsAssignToService;
import com.bis.sourcelead.service.performance.PerformanceGoalsAttachmentsService;
import com.bis.sourcelead.service.performance.PerformanceGoalsCommentsService;
import com.bis.sourcelead.service.performance.PerformanceGoalsService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
public class PerformanceGoalsRestController {
	
	@Value("${static.path}")
	private String staticPath;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	PerformanceGoalsService performanceGoalsService;
	
	@Autowired
	PerformanceGoalsAttachmentsService performanceGoalsAttachmentsService;
	
	@Autowired
	PerformanceGoalsCommentsService performanceGoalsCommentsService;
	
	@Autowired
	PerformanceGoalsAssignToService performanceGoalsAssignToService;
	
	  @RequestMapping(value = { "/getReportingManagersUsersForGoalsAssignment", "/rest/getReportingManagersUsersForGoalsAssignment" }, method = RequestMethod.GET)
	   public ResponseList<PerformanceGoalsAssignToDTO> getReportingManagersUsersForGoalsAssignment(HttpServletRequest request) throws Exception {
		   Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		   Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
		   Long userRoleId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userRoleId");
		   List<PerformanceGoalsAssignToDTO> userRolesList = null;
		   ResponseList<PerformanceGoalsAssignToDTO> resObj = new ResponseList<>();
		   try {
			  userRolesList = performanceGoalsService.getReportingManagersUsersForAssigningGoals(userId,orgLocationId);
			  UserRole userRole = userRoleService.getUserRoleById(userRoleId);
			  PerformanceGoalsAssignToDTO managerDTO = new PerformanceGoalsAssignToDTO();
			  managerDTO.setAssignedUserId(BigInteger.valueOf(userRole.getUser().getId()));
			  managerDTO.setAssignedUser(userRole.getUser().getFullName());
			  userRolesList.add(managerDTO);
			  resObj.setData(userRolesList);
			  resObj.setResponse(Result.SUCCESS.name());
			  resObj.setResponseMessage(Result.SUCCESS.getValue());
		   }catch (Exception e) {
			   e.printStackTrace();
			   resObj.setResponse(Result.AWKWARD.name());
			   resObj.setResponseMessage(Result.AWKWARD.getValue());
			     
			}
		   return resObj;
	   }
	  
	  
	  @RequestMapping(value = { "/savePerformanceGoals", "/rest/savePerformanceGoals" }, method = RequestMethod.POST)
		public @ResponseBody Response<PerformanceGoalsDTO> savePerformanceGoals(PerformanceGoalsDTO performanceGoalsDTO,HttpServletRequest request) throws Exception {
			  Response<PerformanceGoalsDTO> response = new  Response<>();
			  Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
			  Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
			  String dateFormat = (String) SourceLeadSessionManager.getSessionAttribute(request, "dateFormat");
			try {
			 if(Validator.validate(performanceGoalsDTO)) {
				 performanceGoalsDTO.setStaticPath(staticPath);
				 performanceGoalsService.storePerformanceGoals(performanceGoalsDTO, userId, orgLocationId, dateFormat);
				 response.setResponse(Result.SUCCESS.name());
				 response.setResponseMessage(Result.SUCCESS.getValue());
			 }else {
	    		 response.setResponse(Result.INVALID_DATA.name());
				 response.setResponseMessage(Result.INVALID_DATA.getValue());
	    	 }
			}catch (Exception e) {
				e.printStackTrace();
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
			}
			return response;
		}
	  
	  
	  
	  @RequestMapping(value = { "/savePerformanceSubGoals", "/rest/savePerformanceSubGoals" }, method = RequestMethod.POST)
		public @ResponseBody Response<PerformanceGoalsDTO> savePerformanceSubGoals(PerformanceGoalsDTO performanceGoalsDTO,HttpServletRequest request) throws Exception {
			  Response<PerformanceGoalsDTO> response = new  Response<>();
			  Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
			  Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
			  String dateFormat = (String) SourceLeadSessionManager.getSessionAttribute(request, "dateFormat");
			try {
			 if(Validator.validate(performanceGoalsDTO)) {
				 performanceGoalsDTO.setGoalType("Sub_Goal");
				 performanceGoalsDTO.setStaticPath(staticPath);
				 performanceGoalsService.storePerformanceGoals(performanceGoalsDTO, userId, orgLocationId, dateFormat);
				 response.setResponse(Result.SUCCESS.name());
				 response.setResponseMessage(Result.SUCCESS.getValue());
			 }else {
	    		 response.setResponse(Result.INVALID_DATA.name());
				 response.setResponseMessage(Result.INVALID_DATA.getValue());
	    	 }
			}catch (Exception e) {
				e.printStackTrace();
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
			}
			return response;
		}
	  
	  
	  
	  @RequestMapping(value = { "/deletePerformanceGoalsAttachment", "/rest/deletePerformanceGoalsAttachment" }, method = RequestMethod.POST)
		public @ResponseBody Response<PerformanceGoalsDTO> deletePerformanceGoalsAttachment(@RequestParam(value="attachmentUUID", required=true) String attachmentUUID) throws Exception {
			  Response<PerformanceGoalsDTO> response = new  Response<>();
			try {
				
				 PerformanceGoalsAttachments attachments = performanceGoalsAttachmentsService.getPerformanceGoalsAttachmentsByUUID(attachmentUUID);
				 attachments.setStatus(Constants.RECORD_INACTIVE);
				 performanceGoalsAttachmentsService.savePerformanceGoalsAttachments(attachments);
				 response.setResponse(Result.SUCCESS.name());
				 response.setResponseMessage(Result.SUCCESS.getValue());
			}catch (Exception e) {
				e.printStackTrace();
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
			}
			return response;
		}
	  
	  
	  @RequestMapping(value = { "/getMyTotalGoalsDoNutCharts", "/rest/getMyTotalGoalsDoNutCharts" }, method = RequestMethod.GET)
		public Response<PerformanceGoalsDoNutChartCountsDTO> getMyTotalGoalsDoNutCharts(@RequestParam(value="performanceGoalType", required = true) String performanceGoalType,
				HttpServletRequest request){
		  
		    Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		    Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
		  
			 Response<PerformanceGoalsDoNutChartCountsDTO> resObj = new Response<>();
			 try {
				 PerformanceGoalsDoNutChartCountsDTO totalCountsDTO =  performanceGoalsService.getTotalGoalsDoNutChartCounts(userId, orgLocationId, performanceGoalType);
				 resObj.setObject(totalCountsDTO);
				 resObj.setResponse(Result.SUCCESS.name());
				 resObj.setResponseMessage(Result.SUCCESS.getValue());
			    
			 }catch (Exception e) {
				 resObj.setResponse(Result.AWKWARD.name());
				 resObj.setResponseMessage(Result.AWKWARD.getValue());
			}
			 return resObj;
	    }
	  
	  
	  
	  @RequestMapping(value = { "/getMyInprogressGoalsDoNutCharts", "/rest/getMyInprogressGoalsDoNutCharts" }, method = RequestMethod.GET)
		public Response<PerformanceGoalsDoNutChartCountsDTO> getMyInprogressGoalsDoNutCharts(@RequestParam(value="performanceGoalType", required = true) String performanceGoalType,
				HttpServletRequest request){
		  
		    Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		    Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
		  
			 Response<PerformanceGoalsDoNutChartCountsDTO> resObj = new Response<>();
			 try {
				 PerformanceGoalsDoNutChartCountsDTO totalCountsDTO =  performanceGoalsService.getInprogressGoalsDoNutChartCounts(userId, orgLocationId, performanceGoalType);
				 resObj.setObject(totalCountsDTO);
				 resObj.setResponse(Result.SUCCESS.name());
				 resObj.setResponseMessage(Result.SUCCESS.getValue());
			    
			 }catch (Exception e) {
				 resObj.setResponse(Result.AWKWARD.name());
				 resObj.setResponseMessage(Result.AWKWARD.getValue());
			}
			 return resObj;
	    }
	  
	  
	  @RequestMapping(value = { "/getMyCompletedGoalsDoNutCharts", "/rest/getMyCompletedGoalsDoNutCharts" }, method = RequestMethod.GET)
		public Response<PerformanceGoalsDoNutChartCountsDTO> getMyCompletedGoalsDoNutCharts(@RequestParam(value="performanceGoalType", required = true) String performanceGoalType,
				HttpServletRequest request){
		  
		    Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		    Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
		  
			 Response<PerformanceGoalsDoNutChartCountsDTO> resObj = new Response<>();
			 try {
				 PerformanceGoalsDoNutChartCountsDTO totalCountsDTO =  performanceGoalsService.getCompletedGoalsDoNutChartCounts(userId, orgLocationId, performanceGoalType);
				 resObj.setObject(totalCountsDTO);
				 resObj.setResponse(Result.SUCCESS.name());
				 resObj.setResponseMessage(Result.SUCCESS.getValue());
			    
			 }catch (Exception e) {
				 resObj.setResponse(Result.AWKWARD.name());
				 resObj.setResponseMessage(Result.AWKWARD.getValue());
			}
			 return resObj;
	    }
	  
	  
	  @RequestMapping(value = { "/getMyOverDueGoalsDoNutCharts", "/rest/getMyOverDueGoalsDoNutCharts" }, method = RequestMethod.GET)
		public Response<PerformanceGoalsDoNutChartCountsDTO> getMyOverDueGoalsDoNutCharts(@RequestParam(value="performanceGoalType", required = true) String performanceGoalType,
				HttpServletRequest request){
		  
		    Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		    Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
		  
			 Response<PerformanceGoalsDoNutChartCountsDTO> resObj = new Response<>();
			 try {
				 PerformanceGoalsDoNutChartCountsDTO totalCountsDTO =  performanceGoalsService.getOverDueGoalsDoNutChartCounts(userId, orgLocationId, performanceGoalType);
				 resObj.setObject(totalCountsDTO);
				 resObj.setResponse(Result.SUCCESS.name());
				 resObj.setResponseMessage(Result.SUCCESS.getValue());
			    
			 }catch (Exception e) {
				 resObj.setResponse(Result.AWKWARD.name());
				 resObj.setResponseMessage(Result.AWKWARD.getValue());
			}
			 return resObj;
	    }
	  
	  @RequestMapping(value = { "/getMyNotStartedGoalsDoNutCharts", "/rest/getMyNotStartedGoalsDoNutCharts" }, method = RequestMethod.GET)
		public Response<PerformanceGoalsDoNutChartCountsDTO> getMyNotStartedGoalsDoNutCharts(@RequestParam(value="performanceGoalType", required = true) String performanceGoalType,
				HttpServletRequest request){
		  
		    Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		    Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
		  
			 Response<PerformanceGoalsDoNutChartCountsDTO> resObj = new Response<>();
			 try {
				 PerformanceGoalsDoNutChartCountsDTO totalCountsDTO =  performanceGoalsService.getNotStartedGoalsDoNutChartCounts(userId, orgLocationId, performanceGoalType);
				 resObj.setObject(totalCountsDTO);
				 resObj.setResponse(Result.SUCCESS.name());
				 resObj.setResponseMessage(Result.SUCCESS.getValue());
			    
			 }catch (Exception e) {
				 resObj.setResponse(Result.AWKWARD.name());
				 resObj.setResponseMessage(Result.AWKWARD.getValue());
			}
			 return resObj;
	    }
	  
	  
		 @RequestMapping(value = { "/getPerformanceGoalsList", "/rest/getPerformanceGoalsList" }, method = RequestMethod.GET)
			public ResponseList<PerformanceGoalsDTO> getPerformanceCompetenceList(PerformanceGoalsDTO performanceGoalsDTO,
					@RequestParam(value="performanceGoalType", required = true) String performanceGoalType,HttpServletRequest request) throws Exception {
				
				Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
				Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
				String dateFormat = (String) SourceLeadSessionManager.getSessionAttribute(request, "dateFormat");
				
				String[] tableColumns = { "goalName", "percentageDone", "goalEndDate", "goalActions" };
				int sortColumnIndex = 1; // by default sorting using updated date
				String sortColumnOrder = "desc"; // by default descending order
				if (performanceGoalsDTO.getOrder() != null && !performanceGoalsDTO.getOrder().isEmpty()) {
					sortColumnIndex = Integer.parseInt(performanceGoalsDTO.getOrder().get(0).get("column"));
					sortColumnOrder = performanceGoalsDTO.getOrder().get(0).get("dir");
				}
				performanceGoalsDTO.setSortColumn(tableColumns[sortColumnIndex]);
				performanceGoalsDTO.setSortOrder(sortColumnOrder);
				ResponseList<PerformanceGoalsDTO> performanceGoalsList = performanceGoalsService.getPerformanceGoalsList(performanceGoalsDTO, userId, orgLocationId,dateFormat,performanceGoalType);
				return performanceGoalsList;
			}
		 
		 
		 
		 @RequestMapping(value = { "/getPerformanceGoalsDetails", "/rest/getPerformanceGoalsDetails"}, method = RequestMethod.GET)
			public Response<PerformanceGoalsDTO> getPerformanceGoalsDetails(HttpServletRequest request,
					@RequestParam(value="goalUUID", required = true) String goalUUID){
			  
				 Response<PerformanceGoalsDTO> resObj = new Response<>();
				 PerformanceGoalsDTO performanceGoalsDTO = new PerformanceGoalsDTO();
				 try {
					 PerformanceGoals performanceGoals =  performanceGoalsService.getPerformanceGoalsByUUID(goalUUID);
					 if(performanceGoals != null) {
						 performanceGoalsDTO.setGoalName(performanceGoals.getGoalName()); 
						 performanceGoalsDTO.setGoalStartDate(performanceGoals.getGoalStartDateStr());
						 performanceGoalsDTO.setGoalEndDate(performanceGoals.getGoalEndDateStr());
						 performanceGoalsDTO.setDescription(performanceGoals.getDescription());
						 performanceGoalsDTO.setPercentageDone(performanceGoals.getPercentageDone());
						 performanceGoalsDTO.setGoalId(performanceGoals.getId());
						 performanceGoalsDTO.setCreatedDate(performanceGoals.getCreatedDateStr());
						 
						 if(performanceGoals.getTeamId() != null) {
							 performanceGoalsDTO.setTeamId(String.valueOf(performanceGoals.getTeamId().getId()));
							 performanceGoalsDTO.setTeamName(performanceGoals.getTeamId().getName());
						 }
						 
						 Set<PerformanceGoalsAssignTo> performanceGoalsAssignToSet = performanceGoals.getPerformanceGoalsAssignTo();
						 if(performanceGoalsAssignToSet != null && !performanceGoalsAssignToSet.isEmpty()) {
							 StringBuilder assignToIds = new StringBuilder();
							 for(PerformanceGoalsAssignTo assignTo : performanceGoalsAssignToSet) {
								 if(assignTo.getStatus() == Constants.RECORD_ACTIVE) {
									 assignToIds.append(assignTo.getAssignedUserId().getId()).append(",").toString();
								 }
							 }
							 String assignTo = assignToIds.deleteCharAt(assignToIds.length()-1).toString(); 
							 performanceGoalsDTO.setGoalsAssignTo(assignTo);
						 }
						 
						 Set<PerformanceGoalsAttachments> performanceGoalsAttachementsSet = performanceGoals.getPerformanceGoalsAttachments();
						 if(performanceGoalsAttachementsSet != null && !performanceGoalsAttachementsSet.isEmpty()) {
							 List<PerformanceGoalsAttachmentsDTO> attachmentsDTOList = new ArrayList<>();
							 for(PerformanceGoalsAttachments attachments : performanceGoalsAttachementsSet) {
								 if(attachments.getStatus() == Constants.RECORD_ACTIVE) {
									 PerformanceGoalsAttachmentsDTO attachmentsDTO = new PerformanceGoalsAttachmentsDTO();
									 attachmentsDTO.setFileName(attachments.getFileName());
									 attachmentsDTO.setFileUrl(attachments.getFileUrl());
									 attachmentsDTO.setAttachmentId(attachments.getId());
									 attachmentsDTO.setUuid(attachments.getUuid());
									 attachmentsDTOList.add(attachmentsDTO);
								 }
								
							 }
							 
							 performanceGoalsDTO.setAttachmentsList(attachmentsDTOList);
						 }
					 }
					 resObj.setObject(performanceGoalsDTO);
					 resObj.setResponse(Result.SUCCESS.name());
					 resObj.setResponseMessage(Result.SUCCESS.getValue());
				    
				 }catch (Exception e) {
					 e.printStackTrace();
					 resObj.setResponse(Result.AWKWARD.name());
					 resObj.setResponseMessage(Result.AWKWARD.getValue());
				}
				 return resObj;
		    }
			 
		 
	 	@RequestMapping(value = { "/getPerformanceSubGoalsList", "/rest/getPerformanceSubGoalsList" }, method = RequestMethod.GET)
		public ResponseList<PerformanceGoalsDTO> getPerformanceSubGoalsList(@RequestParam(value="goalUUID", required=true) String goalUUID,HttpServletRequest request) throws Exception {
	 		
	 		ResponseList<PerformanceGoalsDTO> resObj = new ResponseList<>();
			String dateFormat = (String) SourceLeadSessionManager.getSessionAttribute(request, "dateFormat");
			 try {
					 PerformanceGoals performanceGoals = performanceGoalsService.getPerformanceGoalsByUUID(goalUUID);
					 List<PerformanceGoalsDTO> performanceGoalsList = performanceGoalsService.getPerformanceSubGoalsList(performanceGoals.getId(),dateFormat);
					 resObj.setData(performanceGoalsList);
					
					 resObj.setResponse(Result.SUCCESS.name());
					 resObj.setResponseMessage(Result.SUCCESS.getValue());
					 
			 }catch (Exception e) {
				 	resObj.setResponse(Result.AWKWARD.name());
				 	resObj.setResponseMessage(Result.AWKWARD.getValue());
			 }
			
			return resObj;
		}
	 	
	 	
	 	@RequestMapping(value = { "/getPerformanceGoalsAssignToList", "/rest/getPerformanceGoalsAssignToList" }, method = RequestMethod.GET)
		public ResponseList<PerformanceGoalsAssignToDTO> getPerformanceGoalsAssignToList(@RequestParam(value="goalUUID", required=true) String goalUUID,HttpServletRequest request) throws Exception {
	 		
	 		ResponseList<PerformanceGoalsAssignToDTO> resObj = new ResponseList<>();
			String dateFormat = (String) SourceLeadSessionManager.getSessionAttribute(request, "dateFormat");
			 try {
					 List<PerformanceGoalsAssignToDTO> performanceGoalsAssignToList = performanceGoalsService.getPerformanceGoalsAssignedUsersList(goalUUID,dateFormat);
					 resObj.setData(performanceGoalsAssignToList);
					
					 resObj.setResponse(Result.SUCCESS.name());
					 resObj.setResponseMessage(Result.SUCCESS.getValue());
					 
			 }catch (Exception e) {
				 	resObj.setResponse(Result.AWKWARD.name());
				 	resObj.setResponseMessage(Result.AWKWARD.getValue());
			 }
			
			return resObj;
		}
	 	
	 	@RequestMapping(value = { "/getAllUserListForGoals", "/rest/getAllUserListForGoals" }, method = RequestMethod.GET)
			public ResponseList<UserDTO> getAllUserListForGoals(HttpServletRequest request) throws Exception {
		 		
	 		   Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		 		ResponseList<UserDTO> resObj = new ResponseList<>();
				 try {
						 List<UserDTO> usersList = userRoleService.getAllActiveUsersList(orgLocationId);
						 resObj.setData(usersList);
						
						 resObj.setResponse(Result.SUCCESS.name());
						 resObj.setResponseMessage(Result.SUCCESS.getValue());
						 
				 }catch (Exception e) {
					 	resObj.setResponse(Result.AWKWARD.name());
					 	resObj.setResponseMessage(Result.AWKWARD.getValue());
				 }
				
				return resObj;
			}
	 	
	 	
	 	@RequestMapping(value = { "/savePerformanceGoalsComments", "/rest/savePerformanceGoalsComments" }, method = RequestMethod.POST)
		public @ResponseBody Response<PerformanceGoalsCommentsDTO> savePerformanceGoals(PerformanceGoalsCommentsDTO performanceGoalsCommentsDTO,HttpServletRequest request) throws Exception {
			  Response<PerformanceGoalsCommentsDTO> response = new  Response<>();
			  User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			  OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			try {
			 if(Validator.validate(performanceGoalsCommentsDTO)) {
				 performanceGoalsCommentsDTO.setCreatedUserId(user.getId());
				 performanceGoalsCommentsDTO.setCreatedUserName(user.getFullName());
				 performanceGoalsCommentsService.storePerformanceGoalsComments(performanceGoalsCommentsDTO,orgLocations.getId());
				 response.setResponse(Result.SUCCESS.name());
				 response.setResponseMessage(Result.SUCCESS.getValue());
			 }else {
	    		 response.setResponse(Result.INVALID_DATA.name());
				 response.setResponseMessage(Result.INVALID_DATA.getValue());
	    	 }
			}catch (Exception e) {
				e.printStackTrace();
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
			}
			return response;
		}
	 
	 	
	 	@RequestMapping(value = { "/getPerformanceGoalsCommentsList", "/rest/getPerformanceGoalsCommentsList" }, method = RequestMethod.GET)
		public ResponseList<PerformanceGoalsCommentsDTO> getPerformanceGoalsCommentsList(@RequestParam(value="goalId", required=true) Long goalId,HttpServletRequest request) throws Exception {
	 		
	 		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
	 		ResponseList<PerformanceGoalsCommentsDTO> resObj = new ResponseList<>();
			 try {
					 List<PerformanceGoalsCommentsDTO> commentsList = performanceGoalsCommentsService.getPerformanceGoalsCommentsList(goalId, orgLocations.getDateFormat());
					 resObj.setData(commentsList);
					
					 resObj.setResponse(Result.SUCCESS.name());
					 resObj.setResponseMessage(Result.SUCCESS.getValue());
					 
			 }catch (Exception e) {
				 	resObj.setResponse(Result.AWKWARD.name());
				 	resObj.setResponseMessage(Result.AWKWARD.getValue());
			 }
			
			return resObj;
		}
	 	
	 	
	 	@RequestMapping(value = { "/makeGoalAsCompleted", "/rest/makeGoalAsCompleted" }, method = RequestMethod.POST)
		public @ResponseBody Response<PerformanceGoalsDTO> makeGoalAsCompleted(@RequestParam(value="goalUUID", required=true) String goalUUID,HttpServletRequest request) throws Exception {
			  Response<PerformanceGoalsDTO> response = new  Response<>();
			try {
			 if(StringUtils.isNoneBlank(goalUUID)) {
				 PerformanceGoals goals =  performanceGoalsService.getPerformanceGoalsByUUID(goalUUID);
				 goals.setPercentageDone(100d);
				 performanceGoalsService.savePerformanceGoals(goals);
				 
				 if(goals.getGoalId() != null) {
					    double percentage = performanceGoalsService.getPerformanceParentGoalsPercentageDoneCountBySubGoalsPercentageDoneCount(goals.getGoalId().getId());
						String parentGoalPercentageDone =  String.format("%.2f", percentage);
						PerformanceGoals parentGoal = performanceGoalsService.getPerformanceGoalsById(goals.getGoalId().getId());
						parentGoal.setPercentageDone(Double.valueOf(parentGoalPercentageDone));
						performanceGoalsService.savePerformanceGoals(parentGoal);
				 }
				 response.setResponse(Result.SUCCESS.name());
				 response.setResponseMessage(Result.SUCCESS.getValue());
			 }else {
	    		 response.setResponse(Result.INVALID_DATA.name());
				 response.setResponseMessage(Result.INVALID_DATA.getValue());
	    	 }
			}catch (Exception e) {
				e.printStackTrace();
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
			}
			return response;
		}
	 	
	 	@RequestMapping(value = { "/getPerformanceGoalsTeamsList", "/rest/getPerformanceGoalsTeamsList" }, method = RequestMethod.GET)
		public ResponseList<TeamDTO> getPerformanceGoalsTeamsList(HttpServletRequest request) throws Exception {
	 		
	 		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
	 		ResponseList<TeamDTO> resObj = new ResponseList<>();
	 		
			 try {
					 List<TeamDTO> teamList = performanceGoalsService.getPerformanceGoalsTeamList(orgLocations.getId());
					 resObj.setData(teamList);
					
					 resObj.setResponse(Result.SUCCESS.name());
					 resObj.setResponseMessage(Result.SUCCESS.getValue());
					 
			 }catch (Exception e) {
				 	resObj.setResponse(Result.AWKWARD.name());
				 	resObj.setResponseMessage(Result.AWKWARD.getValue());
			 }
			
			return resObj;
		}
	 	
	 	
	 	@RequestMapping(value = { "/getPerformanceGoalsTeamsMembersList", "/rest/getPerformanceGoalsTeamsMembersList" }, method = RequestMethod.GET)
		public ResponseList<PerformanceTeamMembersDTO> getPerformanceGoalsTeamsMembersList(@RequestParam(value="goalUUID", required=true) String goalUUID,HttpServletRequest request) throws Exception {
	 		
	 		ResponseList<PerformanceTeamMembersDTO> resObj = new ResponseList<>();
	 		
			 try {
				 if(StringUtils.isNoneBlank(goalUUID)) {
					 PerformanceGoals goals =  performanceGoalsService.getPerformanceGoalsByUUID(goalUUID);
					 if(goals.getTeamId() != null) {
						 List<PerformanceTeamMembersDTO> teamMembersList = performanceGoalsService.getPerformanceTeamMembersByTeamId(goals.getTeamId().getId());
						 resObj.setData(teamMembersList);
					 }
				 }
					
					 resObj.setResponse(Result.SUCCESS.name());
					 resObj.setResponseMessage(Result.SUCCESS.getValue());
					 
			 }catch (Exception e) {
				 	resObj.setResponse(Result.AWKWARD.name());
				 	resObj.setResponseMessage(Result.AWKWARD.getValue());
			 }
			
			return resObj;
		}
	 	
	 	
	 	  @RequestMapping(value = { "/savePerformanceTeamSubGoals", "/rest/savePerformanceTeamSubGoals" }, method = RequestMethod.POST)
			public @ResponseBody Response<PerformanceGoalsDTO> savePerformanceTeamSubGoals(PerformanceGoalsDTO performanceGoalsDTO,HttpServletRequest request) throws Exception {
				  Response<PerformanceGoalsDTO> response = new  Response<>();
				  Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
				  Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
				  String dateFormat = (String) SourceLeadSessionManager.getSessionAttribute(request, "dateFormat");
				try {
				 if(Validator.validate(performanceGoalsDTO)) {
					 performanceGoalsDTO.setGoalType("Sub_Goal");
					 performanceGoalsDTO.setStaticPath(staticPath);
					 performanceGoalsService.storePerformanceGoals(performanceGoalsDTO, userId, orgLocationId, dateFormat);
					 response.setResponse(Result.SUCCESS.name());
					 response.setResponseMessage(Result.SUCCESS.getValue());
				 }else {
		    		 response.setResponse(Result.INVALID_DATA.name());
					 response.setResponseMessage(Result.INVALID_DATA.getValue());
		    	 }
				}catch (Exception e) {
					e.printStackTrace();
					response.setResponse(Result.AWKWARD.name());
					response.setResponseMessage(Result.AWKWARD.getValue());
				}
				return response;
			}
	 	  
	 	 @RequestMapping(value = { "/deletePerformanceGoalByGoalUUID", "/rest/deletePerformanceGoalByGoalUUID" }, method = RequestMethod.POST)
			public @ResponseBody Response<PerformanceGoalsDTO> deletePerformanceGoalByGoalUUID(@RequestParam(value="goalUUID", required=true) String goalUUID,HttpServletRequest request) throws Exception {
				  Response<PerformanceGoalsDTO> response = new  Response<>();
				try {
				 if(StringUtils.isNoneBlank(goalUUID)) {
					 PerformanceGoals goals =  performanceGoalsService.getPerformanceGoalsByUUID(goalUUID);
					 goals.setStatus(Constants.RECORD_INACTIVE);
					 performanceGoalsService.savePerformanceGoals(goals);
					 
					 List<PerformanceGoalsAssignTo> goalsAssignedList = performanceGoalsAssignToService.getPerformanceGoalsAssignToListByGoalId(goals.getId());
					 if(goalsAssignedList != null && !goalsAssignedList.isEmpty()) {
						 for(PerformanceGoalsAssignTo assignTo : goalsAssignedList) {
							 assignTo.setStatus(Constants.RECORD_INACTIVE);
							 performanceGoalsAssignToService.savePerformanceGoalsAssignTo(assignTo);
						 }
					 }
					 
					 String dateFormat = (String) SourceLeadSessionManager.getSessionAttribute(request, "dateFormat");
					 List<PerformanceGoalsDTO> subGoalsList = performanceGoalsService.getPerformanceSubGoalsList(goals.getId(), dateFormat);
					 if(subGoalsList != null && !subGoalsList.isEmpty()) {
						 for(PerformanceGoalsDTO goalsDTO : subGoalsList) {
							 PerformanceGoals subGoal = performanceGoalsService.getPerformanceGoalsById(goalsDTO.getId().longValue());
							 subGoal.setStatus(Constants.RECORD_INACTIVE);
							 performanceGoalsService.savePerformanceGoals(subGoal);
							 
							 List<PerformanceGoalsAssignTo> subGoalsAssignedList = performanceGoalsAssignToService.getPerformanceGoalsAssignToListByGoalId(subGoal.getId());
							 if(subGoalsAssignedList != null && !subGoalsAssignedList.isEmpty()) {
								 for(PerformanceGoalsAssignTo assignTo : subGoalsAssignedList) {
									 assignTo.setStatus(Constants.RECORD_INACTIVE);
									 performanceGoalsAssignToService.savePerformanceGoalsAssignTo(assignTo);
								 }
							 }
						 }
					 }
					 
					 if(goals.getGoalId() != null) {
						    double percentage = performanceGoalsService.getPerformanceParentGoalsPercentageDoneCountBySubGoalsPercentageDoneCount(goals.getGoalId().getId());
							String parentGoalPercentageDone =  String.format("%.2f", percentage);
							PerformanceGoals parentGoal = performanceGoalsService.getPerformanceGoalsById(goals.getGoalId().getId());
							parentGoal.setPercentageDone(Double.valueOf(parentGoalPercentageDone));
							performanceGoalsService.savePerformanceGoals(parentGoal);
					 }
					 
					 response.setResponse(Result.SUCCESS.name());
					 response.setResponseMessage(Result.SUCCESS.getValue());
				 }else {
		    		 response.setResponse(Result.INVALID_DATA.name());
					 response.setResponseMessage(Result.INVALID_DATA.getValue());
		    	 }
				}catch (Exception e) {
					e.printStackTrace();
					response.setResponse(Result.AWKWARD.name());
					response.setResponseMessage(Result.AWKWARD.getValue());
				}
				return response;
			}
	 	
}
