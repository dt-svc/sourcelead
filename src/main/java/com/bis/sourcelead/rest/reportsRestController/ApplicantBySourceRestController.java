package com.bis.sourcelead.rest.reportsRestController;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.dto.RequirementDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.reportsDTO.ApplicantsBySourceDTO;
import com.bis.sourcelead.util.ReportsUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/app")
public class ApplicantBySourceRestController {
	
	@Autowired
	ReportsUtil reportsUtil;
	
	@RequestMapping(value = "/getApplicantBySourceDetails", method = RequestMethod.GET)
	public List<ApplicantsBySourceDTO> getApplicantBySourceDetails(HttpServletRequest request) throws Exception {
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		
		List<ApplicantsBySourceDTO> applicantBySourceList = new ArrayList<>();
		
		return applicantBySourceList;
	}

}
