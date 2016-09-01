package com.bis.sourcelead.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
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
	OrgSpocService orgSpocService;
	
	@Autowired
	CommonUtil commonUtil;

	@Autowired
	CountriesService countriesService;

	
	
	/**
	 * Handles to display client jsp
	 * @return ModelAndView
	 * @throws Exception
	 */
	 @RequestMapping(value = "/client")
		public ModelAndView client(HttpServletRequest request) throws Exception {
		 	logger.debug("Display the client page");
			ModelAndView model = new ModelAndView();
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			
			List<User> userList = userService.getOwnersByOrgLocationId(orgLocations.getId());
			model.addObject("userList", userList);
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
		 
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    ModelAndView model = new ModelAndView();
			model.setViewName("client/clientDashboard");
			List<OrgRelations> clientsList = orgRelationsService.getAllClientsByOrgLocationId(orgLocations.getId());
			
			
			for (OrgRelations orgRelation : clientsList) {
				 //to fetch user details by usercode 
				User user = userService.getUserDetailsByUserCode(orgRelation.getCreatedBy());

				if(user != null){
					orgRelation.setVendorCreateduserName(user.getFirstName()+" "+user.getLastName());
				}
				List<OrgSpoc> orgSpocs = orgSpocService.getAllSpocsByOrgRelationId(orgRelation.getId(), loginUser.getId(),,orgLocations.getId());

				orgRelation.setNoOfContatcs(orgSpocs.size());

			}
		
			
			model.addObject("loginOrgLocId",orgLocations.getId());
			model.addObject("clientsList", clientsList);
			
			//for get the owners list for spocs
			List<User> userList = userService.getOwnersByOrgLocationId(orgLocations.getId());
			model.addObject("userList", userList);
		
			commonUtil.highLightMenu(Constants.Clients, request);
			
			String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "clientCreateMsg");
			if(msg != null && !"".equals(msg)){
				model.addObject("msg", msg);
				SourceLeadSessionManager.removeSessionAttribute(request, "clientCreateMsg");
			}
			
			model.addObject("sourceleadUrl", sourceleadUrl);
			return model;
	    }
	    
	 
	    /**
		 * Handles to display client jsp
		 * @return ModelAndView
		 * @throws Exception
		 */
		 @RequestMapping(value = "/addClientContact")
		 public ModelAndView addClientContact(HttpServletRequest request) throws Exception {
			 ModelAndView model = new ModelAndView();
				try{
					
					OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
					List<User> userList = userService.getOwnersByOrgLocationId(orgLocations.getId());
					model.addObject("userList", userList);
					model.setViewName("client/addClientContact");
					model.addObject("sourceleadUrl", sourceleadUrl);
					
					List<OrgRelations> vandorsListByOrgLocationId = orgRelationsService.getAllClientsByOrgLocationId(orgLocations.getId());
					model.addObject("loginOrgLocId", orgLocations.getId());
					model.addObject("clientContactList", vandorsListByOrgLocationId);
					
					SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
					if(sessionData == null)
					{
						sessionData = new SessionMaintainanceData();
					}
					sessionData.setNav(Constants.Clients);
					request.getSession().setAttribute("sessionData",sessionData);
					
				}catch(Exception e){
					logger.error(ExceptionUtils.getFullStackTrace(e));
				}
				return model;
				/*logger.debug("Display the client page");
				ModelAndView model = new ModelAndView();
				OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				
				List<User> userList = userService.getOwnersByOrgLocationId(orgLocations.getId());
				model.addObject("userList", userList);
				model.setViewName("client/client");
				model.addObject("sourceleadUrl", sourceleadUrl);
				Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
				model.addObject("loginCountryName", countries.getName());
				return model;*/
		    }
		
		
}