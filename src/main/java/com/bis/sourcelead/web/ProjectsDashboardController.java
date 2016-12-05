package com.bis.sourcelead.web;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;



@Controller
public class ProjectsDashboardController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectsDashboardController.class);
	 @Value("${sourceleadUrl}")
	 private String sourceleadUrl;
	
	 @Autowired
	 private ProjectsService projectsService; 
	 
	 @Autowired
	 public ProjectApproverHierarchyService projectApproverHierarchyService;
	 
	 @Autowired
	 private OrgLocationService orgLocationService;
	 
	 @Autowired
	 private OrgRelationsService orgRelationsService;
	 

	 /**
	 * This method for redirect projectDashboard
	 * @param request
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/projectDashboard")
	public ModelAndView projectDashboard(HttpServletRequest request) throws Exception {
           logger.debug("projectDashboard");
		User user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
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
        
        List<Long> projectIds = projectApproverHierarchyService.getProjectIdsByLoginUserId(user.getId());
        
        if(!projectIds.isEmpty()){
        List<Projects> projectsList = projectsService.getAllLoginUserProjectsList(projectIds, loginOrgLocations.getId());
        for(Projects projects: projectsList){
            if(projects.getOrgRelationId()!=null) {
                projects.setClient(projects.getOrgRelationId().getResponderLocationId().getOrg().getOrgName());
                
            }
        }
		model.addObject(projectsList);
        }
		
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
