package com.bis.sourcelead.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectClients;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.service.ClientVendorExport;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectClientService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;



@Controller
public class ProjectsDashboardController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectsDashboardController.class);
	 @Value("${sourceleadUrl}")
	 private String sourceleadUrl;
	
	 @Autowired
	 public ProjectApproverHierarchyService projectApproverHierarchyService;
	 
	 @Autowired
	 private OrgLocationService orgLocationService;
	 
	 @Autowired
	 private OrgRelationsService orgRelationsService;
	 
	 @Autowired
	 private ProjectsService projectsService;
	 
	 @Autowired
	 private ProjectMembersService projectMembersService;
	 
	 @Autowired
	 private ClientVendorExport clientVendorExport;
	 
	 @Autowired
	 private ProjectClientService projectClientService;
	 
	 /**
	 * This method for redirect projectDashboard
	 * @param request
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/projectDashboard")
	public ModelAndView projectDashboard(@RequestParam(value="searchResult", required=false) String searchResult,
			@RequestParam(value="searchValue", required=false) String searchValue,
			@RequestParam(value="projectId",required=false) Long projectId,
			@RequestParam(value="projectName",required=false) String projectName,
			@RequestParam(value="startDate",required=false) String startDate,
			@RequestParam(value="endDate",required=false) String endDate,
			@RequestParam(value="status",required=false) String status,
			@RequestParam(value="projectKeyword",required=false) String projectKeyword,
			HttpServletRequest request) throws Exception {
           logger.debug("projectDashboard");
           
		ModelAndView model = new ModelAndView();
		model.setViewName("project/projectDashboard");
		String loginOrgLocation = (String) SourceLeadSessionManager.getSessionAttribute(request, "locationCode");
	    OrgLocations loginOrgLocations = orgLocationService.getOrgLocationsByLocationCode(loginOrgLocation);
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
        if(sessionData == null)
        {
            sessionData = new SessionMaintainanceData();
        }
        sessionData.setNav(Constants.Projects);
        request.getSession().setAttribute("sessionData",sessionData);
        
        List<OrgRelations> orgClientList = orgRelationsService.getAllClientsByOrgLocationId(loginOrgLocations.getId()); 
        for(OrgRelations orgRelations:orgClientList){
        	//orgRelations.setClientName(orgRelations.getResponderLocationId().getOrgLocationName());
        	if(null != orgRelations.getLocationId() && orgRelations.getLocationId().getId() != null && orgRelations.getLocationId().getId().longValue() == loginOrgLocations.getId().longValue()){
				if(orgRelations.getResponderLocationId() != null){
					orgRelations.setClientName(orgRelations.getResponderLocationId().getOrgLocationName());
				}
			}
			if(null != orgRelations.getResponderLocationId() && null != orgRelations.getResponderLocationId().getId() && orgRelations.getResponderLocationId().getId().longValue() == loginOrgLocations.getId().longValue()){
				if(orgRelations.getLocationId() != null){
					orgRelations.setClientName(orgRelations.getLocationId().getOrgLocationName());
				}
			}
        }
        model.addObject("add_client", orgClientList);
        model.addObject("clientListSize", orgClientList.size());
        model.addObject(orgClientList);
        
        if(null != searchResult && null != searchValue){
        	model.addObject("searchValue", searchValue);
        	model.addObject("searchResult", searchResult);
        	model.addObject("searchType", "Projects");
        }
        
        if(null != projectKeyword && !"".equalsIgnoreCase(projectKeyword) &&  projectKeyword.equalsIgnoreCase("projectSearch")){
			model.addObject("projectKeyword",projectKeyword);
			model.addObject("projectId",projectId);
			model.addObject("projectName",projectName);
			model.addObject("startDate",startDate);
			model.addObject("endDate",endDate);
			model.addObject("status",status);
		}

        
       /* List<Long> projectIds = projectsService.getProjectIdsByOrgLocationsId(loginOrgLocations.getId());
        

        if(null != projectIds && !projectIds.isEmpty()){

        	List<ProjectApproverHierarchy> projectManagersList = projectApproverHierarchyService.getAllApproverUsersByProjectIdList(projectIds);
            for(ProjectApproverHierarchy projectManager:projectManagersList){
            	if(null != projectManager.getApproverUserId() && null != projectManager.getApproverUserId().getLastName()){
            		projectManager.setFullName(projectManager.getApproverUserId().getFirstName()+" "+projectManager.getApproverUserId().getLastName());
            	}else{
            		projectManager.setFullName(projectManager.getApproverUserId().getFirstName());
            	}
            }
            model.addObject("add_projectManagersList", projectManagersList);
            
            if(null != projectIds  && !projectIds.isEmpty()){
            	  List<ProjectMembers> projectMembersList = projectMembersService.getAllProjectMembersByProjectIdsList(projectIds);
                  for(ProjectMembers projectMembers:projectMembersList){
                  	if(null != projectMembers.getEmployee() && null != projectMembers.getEmployee().getUser() && null != projectMembers.getEmployee().getUser().getLastName()){
                  		projectMembers.setEmpName(projectMembers.getEmployee().getUser().getFirstName()+" "+projectMembers.getEmployee().getUser().getLastName());
                  	}else{
                  		projectMembers.setEmpName(projectMembers.getEmployee().getUser().getFirstName());
                  	}
                  	projectMembers.setEmpEmail(projectMembers.getEmployee().getUser().getEmail());
                  }
                  model.addObject("add_projectMembersList", projectMembersList);
                  
            }
          
        }*/
        
        
        /*List<Long> projectIds = projectApproverHierarchyService.getProjectIdsByLoginUserId(user.getId());
        
        if(!projectIds.isEmpty()){
        List<Projects> projectsList = projectsService.getAllLoginUserProjectsList(projectIds, loginOrgLocations.getId());
        for(Projects projects: projectsList){
            if(projects.getOrgRelationId()!=null) {
                projects.setClient(projects.getOrgRelationId().getResponderLocationId().getOrgLocationName());
                
            }
        }
		model.addObject(projectsList);
        }*/
		
		String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "projectCreateMsg");
		if(!isNullOrEmpty(msg)){
			model.addObject("msg", msg);
			SourceLeadSessionManager.removeSessionAttribute(request, "projectCreateMsg");
		}
		
		model.addObject("sourceleadUrl", sourceleadUrl);

		return model;
		
    }
	/**	 
	 * checks if string is null or empty
	 * @param str
	 * @return boolean
	 */	
	protected static boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str.trim());
	}
	
	 /**
	  * @author Rajashekar
	  * 
	  * Writes Projects Assgined employee data to excel and pdf based on exportType
	  * @param projectId
	  * @return
	  * @throws Exception
	  */
	 @PreAuthorize("hasAnyRole('ROLE_EXPORT_EMPLOYEE')")
	 @RequestMapping(value = "/exportProjectEmployeeList")
	 public void  exportProjectEmployeeList(@RequestParam(value="projectId", required=false) Long projectId,
			 @RequestParam(value="status", required=false) String status,@RequestParam(value="exportType", required=false) String exportType,HttpServletRequest request,HttpServletResponse response) throws Exception{ 
		
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 ProjectClients projectClients = projectClientService.getProjectClientsByProjectIdAndOrgLocationId(projectId, orgLocations.getId());
		 List<ProjectMembers> projectMembersList = projectMembersService.getAllProjectMembersByProjectClientIdAndStatus(projectClients.getId(),status);
		 Projects project = projectsService.getProjectById(projectId);
		 
		 File file = clientVendorExport.getexportProjectEmployeeFile(projectMembersList,exportType,project,projectClients); 
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
			if(file!=null){
				file.delete();
			}
		
	  }
}
