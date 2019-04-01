package com.bis.sourcelead.rest.performance;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.PerformanceBadgesDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.performance.PerformanceCompetenciesDTO;
import com.bis.sourcelead.dto.performance.PerformanceGoalsAttachmentsDTO;
import com.bis.sourcelead.dto.performance.PerformanceGoalsDTO;
import com.bis.sourcelead.dto.performance.PerformanceGoalsDoNutChartCountsDTO;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.entity.performance.PerformanceGoals;
import com.bis.sourcelead.entity.performance.PerformanceGoalsAssignTo;
import com.bis.sourcelead.entity.performance.PerformanceGoalsAttachments;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.performance.PerformanceGoalsAttachmentsService;
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
	
	  @RequestMapping(value = "/getReportingManagersUsersForGoalsAssignment" , method = RequestMethod.GET)
	   public List<UserRole> getReportingManagersUsersForGoalsAssignment(HttpServletRequest request) throws Exception {
		   Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		   Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
		   Long userRoleId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userRoleId");
		   List<UserRole> userRolesList = null;
		   try {
			  userRolesList = userRoleService.getAllAssignedUsersByReportingManagerId(userId,orgLocationId);
			  UserRole userRole = userRoleService.getUserRoleById(userRoleId);
			  userRolesList.add(userRole);
			
		   }catch (Exception e) {
			     e.printStackTrace();
			}
		   return userRolesList;
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
		public Response<PerformanceGoalsDoNutChartCountsDTO> getMyTotalGoalsDoNutCharts(HttpServletRequest request){
		  
		    Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		    Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
		  
			 Response<PerformanceGoalsDoNutChartCountsDTO> resObj = new Response<>();
			 try {
				 PerformanceGoalsDoNutChartCountsDTO totalCountsDTO =  performanceGoalsService.getTotalGoalsDoNutChartCounts(userId, orgLocationId);
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
		public Response<PerformanceGoalsDoNutChartCountsDTO> getMyInprogressGoalsDoNutCharts(HttpServletRequest request){
		  
		    Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		    Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
		  
			 Response<PerformanceGoalsDoNutChartCountsDTO> resObj = new Response<>();
			 try {
				 PerformanceGoalsDoNutChartCountsDTO totalCountsDTO =  performanceGoalsService.getInprogressGoalsDoNutChartCounts(userId, orgLocationId);
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
		public Response<PerformanceGoalsDoNutChartCountsDTO> getMyCompletedGoalsDoNutCharts(HttpServletRequest request){
		  
		    Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		    Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
		  
			 Response<PerformanceGoalsDoNutChartCountsDTO> resObj = new Response<>();
			 try {
				 PerformanceGoalsDoNutChartCountsDTO totalCountsDTO =  performanceGoalsService.getCompletedGoalsDoNutChartCounts(userId, orgLocationId);
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
		public Response<PerformanceGoalsDoNutChartCountsDTO> getMyOverDueGoalsDoNutCharts(HttpServletRequest request){
		  
		    Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		    Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
		  
			 Response<PerformanceGoalsDoNutChartCountsDTO> resObj = new Response<>();
			 try {
				 PerformanceGoalsDoNutChartCountsDTO totalCountsDTO =  performanceGoalsService.getOverDueGoalsDoNutChartCounts(userId, orgLocationId);
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
		public Response<PerformanceGoalsDoNutChartCountsDTO> getMyNotStartedGoalsDoNutCharts(HttpServletRequest request){
		  
		    Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		    Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
		  
			 Response<PerformanceGoalsDoNutChartCountsDTO> resObj = new Response<>();
			 try {
				 PerformanceGoalsDoNutChartCountsDTO totalCountsDTO =  performanceGoalsService.getNotStartedGoalsDoNutChartCounts(userId, orgLocationId);
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
			public ResponseList<PerformanceGoalsDTO> getPerformanceCompetenceList(PerformanceGoalsDTO performanceGoalsDTO,HttpServletRequest request) throws Exception {
				
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
				ResponseList<PerformanceGoalsDTO> performanceGoalsList = performanceGoalsService.getPerformanceGoalsList(performanceGoalsDTO, userId, orgLocationId,dateFormat);
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
					 resObj.setResponse(Result.AWKWARD.name());
					 resObj.setResponseMessage(Result.AWKWARD.getValue());
				}
				 return resObj;
		    }
			 
}
