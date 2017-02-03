package com.bis.sourcelead.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
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
	 

	 /**
	 * This method for redirect projectDashboard
	 * @param request
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/projectDashboard")
	public ModelAndView projectDashboard(@RequestParam(value="searchResult", required=false) String searchResult,
			@RequestParam(value="searchValue", required=false) String searchValue,HttpServletRequest request) throws Exception {
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
        
        List<OrgRelations> orgClientList =orgRelationsService.getAllClientsByOrgLocationId(loginOrgLocations.getId()); 
        for(OrgRelations orgRelations:orgClientList){
        	orgRelations.setClientName(orgRelations.getResponderLocationId().getOrg().getOrgName());
        }
        model.addObject("add_client", orgClientList);
        model.addObject(orgClientList);
        
        if(null != searchResult && null != searchValue){
        	model.addObject("searchValue", searchValue);
        	model.addObject("searchResult", searchResult);
        }
        
        List<Long> projectIds = projectsService.getProjectIdsByOrgLocationsId(loginOrgLocations.getId());
        
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
          
        }
        
        
        /*List<Long> projectIds = projectApproverHierarchyService.getProjectIdsByLoginUserId(user.getId());
        
        if(!projectIds.isEmpty()){
        List<Projects> projectsList = projectsService.getAllLoginUserProjectsList(projectIds, loginOrgLocations.getId());
        for(Projects projects: projectsList){
            if(projects.getOrgRelationId()!=null) {
                projects.setClient(projects.getOrgRelationId().getResponderLocationId().getOrg().getOrgName());
                
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
}
