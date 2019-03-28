package com.bis.sourcelead.rest.performance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.dto.PerformanceBadgesDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.performance.PerformanceCompetenciesDTO;
import com.bis.sourcelead.dto.performance.PerformanceGoalsDTO;
import com.bis.sourcelead.dto.performance.PerformanceGoalsDoNutChartCountsDTO;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.UserRoleService;
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
	
	  @RequestMapping(value = "/getResourceManagersUsersForGoalsAssignment" , method = RequestMethod.GET)
	   public List<UserRole> getResourceManagersUsersForGoalsAssignment(HttpServletRequest request) throws Exception {
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
			 
}
