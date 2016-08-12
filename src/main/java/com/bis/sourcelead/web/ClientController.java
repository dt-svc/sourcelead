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

import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.Constants;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;
/**
 * Handles requests for the Client service.
 * 
 * @author PrasadK
 * @date 13th June 2016
 *
 */

@Controller
@PropertySource("${propertyLocation:app.properties}")
public class ClientController {

	private static final Logger logger = LoggerFactory.getLogger(ClientController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private OrgRelationsService orgRelationsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	ProjectApproverHierarchyService projectApproverHierarchyService;
	
	@Autowired
	ProjectMembersService projectMembersService;
	
	@Autowired
	CountriesService countriesService;
	
	
	/**
	 * Handles to display client jsp
	 * @return ModelAndView
	 * @throws Exception
	 */
	 @RequestMapping(value = "/client")
		public ModelAndView client(HttpServletRequest request) throws Exception {
			ModelAndView model = new ModelAndView();
			SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			if(sessionData == null)
			{
				sessionData = new SessionMaintainanceData();
			}
			sessionData.setNav(Constants.Clients);
			
			request.getSession().setAttribute("sessionData",sessionData);
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			 Collection<User> userList = userService.getUsersByUserRolesAndOrgLocationId(orgLocations.getId());
				model.addObject("user_List", userList);
			model.setViewName("client/client");
			model.addObject("sourceleadUrl", sourceleadUrl);
			
			Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
			model.addObject("loginCountryName", countries.getName());
			return model;
	    }
	 
	 /**
	  * Handles to display clientdash board
	  * @param request
	  * @return ModelAndView
	  * @throws Exception
	  */
	 @RequestMapping(value = "/clientDashboard")
		public ModelAndView clientDashboard(HttpServletRequest request) throws Exception {
		    User user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    ModelAndView model = new ModelAndView();
			model.setViewName("client/clientDashboard");
			
	        
			
			Collection<OrgRelations> clientsList = orgRelationsService.getAllClientsByOrgLocationId(orgLocations.getId());
			for(OrgRelations orgRelation : clientsList){
				Long count =orgRelationsService.getProjectsCountByOrgRelationIdAndOrgLocationId(orgRelation.getId(), orgLocations.getId());
				orgRelation.setProjectsCont(count);
			}
			model.addObject("clientsList", clientsList);
		
			SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			if(sessionData == null)
			{
				sessionData = new SessionMaintainanceData();
			}
			sessionData.setNav(Constants.Clients);
			request.getSession().setAttribute("sessionData",sessionData);
			
			String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "clientCreateMsg");
			if(!isNullOrEmpty(msg)){
				model.addObject("msg", msg);
				SourceLeadSessionManager.removeSessionAttribute(request, "clientCreateMsg");
			}
			
			model.addObject("sourceleadUrl", sourceleadUrl);
			return model;
	    }
	 
		
		protected static boolean isNullOrEmpty(String str) {
			return (str == null || "".equals(str.trim()));
		}
}