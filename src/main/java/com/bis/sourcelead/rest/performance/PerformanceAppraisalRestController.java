package com.bis.sourcelead.rest.performance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.dto.PerformanceFeedbackTemplateDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.performance.PerformanceAppraisalDetailsDTO;
import com.bis.sourcelead.dto.performance.PerformanceAppraisalTemplateDTO;
import com.bis.sourcelead.dto.performance.PerformanceRatingScaleDTO;
import com.bis.sourcelead.dto.performance.PerformanceTagCompetenciesDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.performance.PerformanceAppraisalService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
public class PerformanceAppraisalRestController {

	@Autowired
	PerformanceAppraisalService performanceAppraisalService;
	
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
	
}
