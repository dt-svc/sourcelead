package com.bis.sourcelead.rest.performance;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.performance.PerformanceAppraisalTemplateDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
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
 		 
		String[] tableColumns = { "name", "appraisalStartDate", "appraisalProcessStartDate", "selfAppraisalEndDate", "status" };
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
	
}
