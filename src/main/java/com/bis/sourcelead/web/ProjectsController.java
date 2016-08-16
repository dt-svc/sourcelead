package com.bis.sourcelead.web;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;


@Controller
@PropertySource("${propertyLocation:app.properties}")
public class ProjectsController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectsController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	 @Autowired
	 private OrgService orgService;
	 
	 @Autowired
	 private OrgRelationsService orgRelationsService;
	 
	 @Autowired
	 private OrgLocationService orgLocationService;
	 
	 @Autowired
	 private UserService userService;
	
	 
	 /**
	  * This method for get project by id
	  * @param id
	  * @return
	  * @throws Exception
	  */
	 
	 @RequestMapping(value = "/project")
		public ModelAndView projects(HttpServletRequest request) throws Exception {
		    String loginOrgLocation = (String) SourceLeadSessionManager.getSessionAttribute(request, "locationCode");
		   
		    OrgLocations loginOrgLocations = orgLocationService.getOrgLocationsByLocationCode(loginOrgLocation);
		   
				ModelAndView model = new ModelAndView();
				
				 SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
					if(sessionData == null)
					{
						sessionData = new SessionMaintainanceData();
					}
					sessionData.setNav(Constants.Projects);
					
					request.getSession().setAttribute("sessionData",sessionData);
				
				Collection<OrgRelations> orgClientList =orgRelationsService.getAllClientsByOrgLocationId(loginOrgLocations.getId()); 
				for(OrgRelations ocl :orgClientList){
					ocl.setOrgName(ocl.getClientLocationId().getOrg().getOrgName());
					ocl.setOrgid(ocl.getClientLocationId().getOrg().getId());
				 }
			    model.addObject("add_clientList", orgClientList);
			    
				Collection<Org> orgsList = orgService.getOrgs();
				model.addObject("add_client", orgsList);
				
                Collection<User> userList = userService.getUsersByUserRolesAndOrgLocationId(loginOrgLocations.getId());
				model.addObject("user_List", userList);
				
				model.setViewName("project/project");
				model.addObject("sourceleadUrl", sourceleadUrl);
				return model;
	}
/**	 
 * checks if string is null or empty
 * @param str
 * @return boolean
 */
   protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
}
