package com.bis.sourcelead.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.dto.DayWiseTimesheetReoprtsDTO;
import com.bis.sourcelead.dto.ProjectsDTO;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.DayEntryReportsService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/app")
public class DayWiseTimeSheetReports {

@Autowired
private ProjectsService projectService;

@Autowired
private DayEntryReportsService dayEntryReportsService;

@Autowired
private ProjectMembersService projectMemberService;

@Value("${sourceleadUrl}")
private String sourceleadUrl;

	
@RequestMapping(value="/getProjectListBasedOnStartAndEndDate")
public List<ProjectsDTO> getProjectListBasedOnStartAndEndDate(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,HttpServletRequest request) throws JsonProcessingException {
	OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
	Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request,"org");
	User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	String isGlobalUser = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
	String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
	dayWiseTimesheetReoprtsDto.setLocationsId(orgLocations.getId());
	dayWiseTimesheetReoprtsDto.setLoginUserId(loginUser.getId());
	List<ProjectsDTO> projectList  = null;
	try {
		projectList = projectService.getProjectListForDaywiseReports(orgLocations.getId(), org.getId(), isGlobalUser, loginUser.getId(), dayWiseTimesheetReoprtsDto.getTypeOfProject(),userRole);
	} catch (Exception e) {
		e.printStackTrace();
	}
	 
	return projectList;
}


@RequestMapping(value="/getProjectMembersBasedOnProjectId")
public List<ProjectMembers> getProjectMembersBasedOnProjectId(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,HttpServletRequest request) throws Exception {
	
	List<ProjectMembers> projectMemberList = null;
	OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
	User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
	dayWiseTimesheetReoprtsDto.setLocationsId(orgLocations.getId());
	dayWiseTimesheetReoprtsDto.setLoginUserId(loginUser.getId());
	dayWiseTimesheetReoprtsDto.setLoginUserRoleType(loginUserRole);
    projectMemberList = projectMemberService.getAllProjectMembersBasedOnProjectIdStartDateEndDateAndEmploymentType(dayWiseTimesheetReoprtsDto);
	return projectMemberList;
}



@RequestMapping(value="/exportDaywiseTimesheetReport")
public void exportDaywiseTimesheetReport(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,HttpServletRequest request,HttpServletResponse response) throws Exception {
	OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
	User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	String isGlobalUser = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
	File file = null;
	if ("excel".equalsIgnoreCase(dayWiseTimesheetReoprtsDto.getExportType())) {
			file = dayEntryReportsService.exportDayEntryReportsWithoutProjectExcel(dayWiseTimesheetReoprtsDto,orgLocations,loginUser,isGlobalUser);
	} else if("pdf".equalsIgnoreCase(dayWiseTimesheetReoprtsDto.getExportType())){
			file = dayEntryReportsService.exportDayEntryReportsWithProjectPdf(dayWiseTimesheetReoprtsDto,orgLocations,loginUser,isGlobalUser);
	}
	if (file != null) {
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		response.setContentType(mimeType);
		StringBuilder fileName = new StringBuilder();
		response.setHeader("Content-Disposition",
				fileName.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
		response.setContentLength((int) file.length());
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
		inputStream.close();
		response.getOutputStream().close();
		response.getOutputStream().flush();
	}
}


@RequestMapping(value="/getAllUserListBasedOnOrgLocationId", method= RequestMethod.POST)
public List<ProjectMembers> getAllUserListBasedOnOrgLocationId(HttpServletRequest request) throws Exception {
	OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
	List<ProjectMembers> projectMemberList = projectMemberService.getAllProjectsByOrgLocationId(orgLocations.getId());
	projectMemberList.forEach(pm -> pm.setUserId(String.valueOf(pm.getProjectMemberCandidteId().getConsultantUser().getId())));
	return projectMemberList;
}


	
}
