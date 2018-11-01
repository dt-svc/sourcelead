package com.bis.sourcelead.web.reportsController;

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
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.dto.OrgRelationDTO;
import com.bis.sourcelead.dto.ProjectsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.reportsDTO.ProjectReportDTO;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.util.PdfUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@RequestMapping ("/app")

public class ProjectReportController {	

	@Value("${sourceleadUrl}") 
	private String sourceleadUrl;
	 
	@Autowired
	ProjectsService projectsService;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	private PdfUtil pdfUtil;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	@RequestMapping(value = "/projectReport")
	
	public ModelAndView projectReport(HttpServletRequest request)throws Exception{
		ModelAndView model = new ModelAndView();
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 List<Projects> projectList = projectsService.getAllProjectByOrgLocationsId(orgLocation.getId());
		 model.addObject("projectList", projectList);
		 List<User> projectManagersList = userRoleService.getAllProjectManagersAssignedToProjects(orgLocation.getId());
	      model.addObject("projectManagersList", projectManagersList);
	      model.addObject("projectManagersListSize", projectManagersList.size());
		   model.addObject("sourceleadUrl", sourceleadUrl);
		    List<OrgRelationDTO> orgClientList = orgRelationsService.getAllClientsListOrgLocationId(orgLocation.getId()); 
	        model.addObject("add_client", orgClientList);
	        model.addObject("clientListSize", orgClientList.size());
	        model.addObject(orgClientList);
		  model.setViewName("reports/projectReport");
		 return model;
	}

	
	@RequestMapping(value = "/getProjectReport" ,method = RequestMethod.POST)
	public @ResponseBody ResponseList<ProjectReportDTO> getProjectReportList(ProjectReportDTO projectReportDTO,HttpServletRequest request) throws Exception{
		 //User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ResponseList<ProjectReportDTO> projectReportList = projectsService.getProjectReportList(projectReportDTO,orgLocation.getId());
		
		return projectReportList;
	}

	@ResponseBody
	@RequestMapping(value = "/getProjectReportExport")
	public File projectReportListForExport(HttpServletRequest request, HttpServletResponse response, ProjectReportDTO projectReportDTO) throws Exception {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

		String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		File file = null;
		ResponseList<ProjectReportDTO> projectReportList = projectsService.getProjectReportList(projectReportDTO,orgLocation.getId());
		if (projectReportList.getData() != null && !projectReportList.getData().isEmpty()) {

			if (projectReportDTO.getType().equalsIgnoreCase("pdf")) {
				file = pdfUtil.getProjectReportListOfPdf(projectReportList.getData(), user, orgLocation,
						isGlobalAccess);
			} else {
				file = projectsService.getProjectReportListOfExcel(projectReportList.getData(), user, orgLocation, isGlobalAccess);
			}
		}
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		response.setContentType(mimeType);
		StringBuilder fileName = new StringBuilder();
		response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
		response.setContentLength((int) file.length());
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
		inputStream.close();
		response.getOutputStream().close();
		response.getOutputStream().flush();
		if (file != null) {
			file.delete();
		}
		return file;

	}
	
}
