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

import com.bis.sourcelead.dto.PerformanceFeedbackTemplateDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.performance.PerformanceAppraisalDetailsDTO;
import com.bis.sourcelead.dto.performance.PerformanceAppraisalTemplateDTO;
import com.bis.sourcelead.dto.performance.PerformanceFeedbackCategoryDTO;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.performance.PerformanceAppraisalService;
import com.bis.sourcelead.service.performance.PerformanceAppraisalUserFeedbackService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
public class PerformanceAppraisalRestController {

	@Autowired
	PerformanceAppraisalService performanceAppraisalService;
	
	@Autowired
	PerformanceAppraisalUserFeedbackService performanceAppraisalUserFeedbackService;
	
	@Value("${static.path}")
	private String staticPath;
	
	@RequestMapping(value = "/getAssignedMyAppraisalList", method = RequestMethod.GET)
	public ResponseList<PerformanceAppraisalTemplateDTO> getAssignedMyAppraisalList(PerformanceAppraisalTemplateDTO performanceAppraisalTemplateDTO,HttpServletRequest request) throws Exception {
		
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
 		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
 		 
		String[] tableColumns = { "name", "appraisalStartDate", "appraisalProcessStartDate", "selfAppraisalStartDate", "status" };
		int sortColumnIndex = 1; // by default sorting using updated date
		String sortColumnOrder = "desc"; // by default descending order
		if (performanceAppraisalTemplateDTO.getOrder() != null && !performanceAppraisalTemplateDTO.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(performanceAppraisalTemplateDTO.getOrder().get(0).get("column"));
			sortColumnOrder = performanceAppraisalTemplateDTO.getOrder().get(0).get("dir");
		}
		performanceAppraisalTemplateDTO.setSortColumn(tableColumns[sortColumnIndex]);
		performanceAppraisalTemplateDTO.setSortOrder(sortColumnOrder);
		ResponseList<PerformanceAppraisalTemplateDTO> myAppraisalAssignedList = performanceAppraisalService.getAssignedMyAppraisalList(performanceAppraisalTemplateDTO, user.getId(), orgLocation.getId(), orgLocation.getDateFormat());
		return myAppraisalAssignedList;
	}
	
	
	@RequestMapping(value = "/getAssignedPeersAppraisalList", method = RequestMethod.GET)
	public ResponseList<PerformanceAppraisalTemplateDTO> getAssignedPeersAppraisalList(PerformanceAppraisalTemplateDTO performanceAppraisalTemplateDTO,HttpServletRequest request) throws Exception {
		
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
 		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
 		 
		String[] tableColumns = { "name", "appraisalStartDate", "appraisalProcessStartDate", "peerReviewStartDate", "status" };
		int sortColumnIndex = 1; // by default sorting using updated date
		String sortColumnOrder = "desc"; // by default descending order
		if (performanceAppraisalTemplateDTO.getOrder() != null && !performanceAppraisalTemplateDTO.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(performanceAppraisalTemplateDTO.getOrder().get(0).get("column"));
			sortColumnOrder = performanceAppraisalTemplateDTO.getOrder().get(0).get("dir");
		}
		performanceAppraisalTemplateDTO.setSortColumn(tableColumns[sortColumnIndex]);
		performanceAppraisalTemplateDTO.setSortOrder(sortColumnOrder);
		ResponseList<PerformanceAppraisalTemplateDTO> myAppraisalAssignedList = performanceAppraisalService.getAssignedPeersAppraisalList(performanceAppraisalTemplateDTO, user.getId(), orgLocation.getId(), orgLocation.getDateFormat());
		return myAppraisalAssignedList;
	}
	
	
        @RequestMapping(value = "/getSelfAppraisalTemplateDetails", method = RequestMethod.GET)
		public Response<PerformanceAppraisalDetailsDTO> getSelfAppraisalTemplateDetails(HttpServletRequest request, 
				@RequestParam(value = "assignedUUID", required = true) String assignedUUID){
        	
        	 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
     		 
			 Response<PerformanceAppraisalDetailsDTO> resObj = new Response<>();
			 try {
				 PerformanceAppraisalDetailsDTO appraisalDetailsDTO =  performanceAppraisalService.getSelfAppraisalTemplateDetails(assignedUUID, orgLocation.getDateFormat());
				 resObj.setObject(appraisalDetailsDTO);
				 resObj.setResponse(Result.SUCCESS.name());
				 resObj.setResponseMessage(Result.SUCCESS.getValue());
			    
			 }catch (Exception e) {
				 resObj.setResponse(Result.AWKWARD.name());
				 resObj.setResponseMessage(Result.AWKWARD.getValue());
			}
			 return resObj;
	    }
        
        
        

	    @RequestMapping(value = "/getAssignedFeedbackTemplateList", method = RequestMethod.GET)
		public ResponseList<PerformanceFeedbackTemplateDTO> getAssignedFeedbackTemplateList(HttpServletRequest request, 
				@RequestParam(value = "assignedUUID", required = true) String assignedUUID){
	    	 ResponseList<PerformanceFeedbackTemplateDTO> resObj = new ResponseList<>();
			 try {
				 List<PerformanceFeedbackTemplateDTO> feedbackTemplateDTOList =  performanceAppraisalService.getPerformanceAssignedFeedbackTemplateDTOList(assignedUUID);
				 resObj.setData(feedbackTemplateDTOList);
				 resObj.setResponse(Result.SUCCESS.name());
				 resObj.setResponseMessage(Result.SUCCESS.getValue());
			    
			 }catch (Exception e) {
				 resObj.setResponse(Result.AWKWARD.name());
				 resObj.setResponseMessage(Result.AWKWARD.getValue());
			}
			 return resObj;
	    }
	    
		
	    @RequestMapping(value = "/loadPerformanceFeedbackTemplate" , method = RequestMethod.GET)
	    public PerformanceFeedbackTemplateDTO loadPerformanceFeedbackTemplate(@RequestParam(value="templateUUID", required=false) String templateUUID) throws Exception {
	 		
	 	   PerformanceFeedbackTemplateDTO templateDTO  = performanceAppraisalService.getPerformanceFeedbackTemplateDTOByUUID(templateUUID);
	 	   return templateDTO;
	     
	    }
	    
	    
	    @RequestMapping(value = { "/saveSubmittedFeedbackTemplate", "/rest/saveSubmittedFeedbackTemplate" }, method = RequestMethod.POST)
		public @ResponseBody Response<PerformanceFeedbackCategoryDTO> storeProject(PerformanceFeedbackCategoryDTO performanceFeedbackCategoryDTO,HttpServletRequest request) throws Exception {
			Response<PerformanceFeedbackCategoryDTO> response =new  Response<>();
			try {
				
				 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				 Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		 		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 		 
		 		 performanceFeedbackCategoryDTO.setOrgId(org.getId());
		 		 performanceFeedbackCategoryDTO.setUserId(user.getId());
		 		 performanceFeedbackCategoryDTO.setOrgLocationId(orgLocation.getId());
				 performanceFeedbackCategoryDTO.setStaticPath(staticPath);
				 performanceAppraisalUserFeedbackService.storePerformanceAppraisalUserFeedback(performanceFeedbackCategoryDTO);
				 response.setResponse(Result.SUCCESS.name());
				 response.setResponseMessage(Result.SUCCESS.getValue());
			
			}catch (Exception e) {
				e.printStackTrace();
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
			}
			return response;
		}
	    
	
}
