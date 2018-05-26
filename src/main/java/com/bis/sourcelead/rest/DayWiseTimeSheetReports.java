package com.bis.sourcelead.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.dto.DayWiseTimesheetReoprtsDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.DayEntryReportsService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/app")
public class DayWiseTimeSheetReports {

@Autowired
private ProjectsService projectService;

@Autowired
private DayEntryReportsService dayEntryReportsService;

@Autowired
private ProjectMembersService projectMemberService;

@Autowired
private UserService userService;

@Autowired
private CommonUtil commonUtil;
	
@RequestMapping(value="/getProjectListBasedOnStartAndEndDate")
public List<Projects> getProjectListBasedOnStartAndEndDate(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,HttpServletRequest request) throws JsonProcessingException {
	OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
	User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	
	dayWiseTimesheetReoprtsDto.setLocationsId(orgLocations.getId());
	dayWiseTimesheetReoprtsDto.setLoginUserId(loginUser.getId());
	List<Projects> projectList  = null;
	if(dayWiseTimesheetReoprtsDto.getTypeOfProject().equalsIgnoreCase("internal")) {
		try {
			projectList = projectService.getProjectByOrgLocationsId(orgLocations.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}else if(dayWiseTimesheetReoprtsDto.getTypeOfProject().equalsIgnoreCase("client")){
		try {
			projectList = projectService.getAllClientProjectsByOrgLocationId(orgLocations.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}else {
		try {
			List<Projects> internalprojectList = projectService.getProjectByOrgLocationsId(orgLocations.getId());
			List<Projects> clientprojectList  = projectService.getAllClientProjectsByOrgLocationId(orgLocations.getId());
			projectList = new ArrayList<>();
			projectList.addAll(clientprojectList);
			projectList.addAll(internalprojectList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 
	return projectList;
}


@RequestMapping(value="/getProjectMembersBasedOnProjectId")
public String getProjectMembersBasedOnProjectId(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,HttpServletRequest request) throws Exception {
	OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
	User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	dayWiseTimesheetReoprtsDto.setLocationsId(orgLocations.getId());
	dayWiseTimesheetReoprtsDto.setLoginUserId(loginUser.getId());
	List<ProjectMembers> projectMemberList = projectMemberService.getAllProjectMembersBasedOnProjectIdStartDateEndDateAndEmploymentType(dayWiseTimesheetReoprtsDto);
	ObjectMapper mapper = new ObjectMapper();
	String projectMemberListJson = mapper.writeValueAsString(projectMemberList);
	return projectMemberListJson;
}


@RequestMapping(value="/exportDaywiseTimesheetReport")
public void exportDaywiseTimesheetReport(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,HttpServletRequest request,HttpServletResponse response) throws Exception {
	OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
	User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");

	File file = null;
	if ("excel".equalsIgnoreCase(dayWiseTimesheetReoprtsDto.getExportType())) {
		/*if(dayWiseTimesheetReoprtsDto.getTemplateType()!= null && dayWiseTimesheetReoprtsDto.getTemplateType().equalsIgnoreCase("proJectMembersExport")) {
			file = dayEntryReportsService.exportDayEntryReportsWithoutProject(dayWiseTimesheetReoprtsDto,orgLocations,loginUser);
		}else {*/
			/*file = dayEntryReportsService.exportDayEntryReports(dayWiseTimesheetReoprtsDto,orgLocations,loginUser);*/
			file = dayEntryReportsService.exportDayEntryReportsTemplateOne(dayWiseTimesheetReoprtsDto,orgLocations,loginUser);
		/*}*/
		
	} else {
		file = null;
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
public String getAllUserListBasedOnOrgLocationId(HttpServletRequest request) throws Exception {
	OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
	List<User> userList = userService.getAllUsersByOrgLocationCode(orgLocations.getId());
	return commonUtil.toJSON(userList);
}


	
}
