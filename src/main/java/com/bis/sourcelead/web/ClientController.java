package com.bis.sourcelead.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.SourceleadSectionDTO;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.States;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.SourceleadFormSectionsService;
import com.bis.sourcelead.service.StatesService;
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

	@Autowired
	StatesService statesService;
	
	@Autowired
	private SourceleadActionsService sourceleadActionsService;
	
	@Autowired
	private SourceleadFormSectionsService sourceleadFormSectionsService;
	
	/**
	 * Handles to display client jsp
	 * @return ModelAndView
	 * @throws Exception
	 */
	@PreAuthorize("hasAnyRole('ROLE_CREATE_CLIENT')")
	 @RequestMapping(value = "/client")
		public ModelAndView client(HttpServletRequest request) throws Exception {
		 	logger.debug("Display the client page");
			ModelAndView model = new ModelAndView();
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			
			SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			if(sessionData == null)
			{
				sessionData = new SessionMaintainanceData();
			}
			sessionData.setNav(Constants.Clients);
			request.getSession().setAttribute("sessionData",sessionData);
			
			 SourceleadActions action = sourceleadActionsService.getSourceleadActionsByActionRole(Constants.ROLE_CREATE_CLIENT);
		     model.addObject("actionId",action.getId());
		     List<SourceleadSectionDTO> sections  = sourceleadFormSectionsService.getSourceleadFormSectionsByActionId(action.getId(),orgLocations.getId());
			 model.addObject("sourceleadSectionList",sections);
		     
			List<User> userList = userService.getTabAccessOwnersByOrgLocationIdAndTabId(orgLocations.getId(),Long.valueOf(Constants.TAB_CLIENT));
			model.addObject("userList", userList);
			model.setViewName("client/client");
			model.addObject("sourceleadUrl", sourceleadUrl);
			Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
			if(countries != null){
				model.addObject("loginCountryName", countries.getName());
			}
			return model;
	    }
	 /**
	  * Handles to display clientdash board
	  * @param request
	  * @return ModelAndView
	  * @throws Exception
	  */
	
	/* This method is used for landing page of clients tab. So, who has client tab access they can see clients list. No preAuthorize for this method */
	 @RequestMapping(value = "/clientDashboard")
		public ModelAndView clientDashboard(HttpServletRequest request,@RequestParam(value = "searchResult", required = false) String searchResult,@RequestParam(value = "searchValue", required = false) String searchValue,
				@RequestParam(value = "responderLocationId", required = false) String responderLocationId,
				@RequestParam(value = "orgRelationId", required = false) String orgRelationId,
				@RequestParam(value = "orgRelationUUID", required = false) String orgRelationUUID,
				@RequestParam(value = "orgSpocUUID", required = false) String orgSpocUUID,
				@RequestParam(value = "orgName", required = false) String orgName,
				@RequestParam(value = "clientKeyword", required = false) String clientKeyword,
				@RequestParam(value="contactsTab", required=false)String contactsTab) throws Exception {
		 
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    ModelAndView model = new ModelAndView();
			
		    SourceleadActions action = sourceleadActionsService.getSourceleadActionsByActionRole(Constants.ROLE_CREATE_CLIENT);
		     model.addObject("actionId",action.getId());
		     List<SourceleadSectionDTO> sections  = sourceleadFormSectionsService.getSourceleadFormSectionsByActionId(action.getId(),orgLocations.getId());
			 model.addObject("sourceleadSectionList",sections);
		    
			 model.addObject("contactsTab",contactsTab);
		    model.setViewName("client/clientDashboard");
			
			if(null != searchResult && !searchResult.equalsIgnoreCase("") && searchResult.equals("clientSearchResult")){
				model.addObject("clientSearchResult","clientSearchResult");
				model.addObject("searchValue",searchValue);
				model.addObject("searchType","Client");
			}
			
			if(null != clientKeyword && !clientKeyword.equalsIgnoreCase("") && clientKeyword.equals("clientSearch")){
				model.addObject("responderLocationId",responderLocationId);
				model.addObject("clientKeyword",clientKeyword);
				model.addObject("orgRelationId",orgRelationId);
				model.addObject("orgName",orgName);
			}
			
			List<OrgRelations> clientsList = orgRelationsService.getAllClientsByOrgLocationId(orgLocations.getId());
			for (OrgRelations orgRelation : clientsList) {
				if(null != orgRelation.getLocationId() && orgRelation.getLocationId().getId() != null && orgRelation.getLocationId().getId().longValue() == orgLocations.getId().longValue()){
					if(orgRelation.getResponderLocationId() != null){
						orgRelation.setClientName(orgRelation.getResponderLocationId().getOrgLocationName());
					}
				}
				if(null != orgRelation.getResponderLocationId() && null != orgRelation.getResponderLocationId().getId() && orgRelation.getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
					if(orgRelation.getLocationId() != null){
						orgRelation.setClientName(orgRelation.getLocationId().getOrgLocationName());
					}
				}
				 //to fetch user details by usercode 
				User user = userService.getUserDetailsByUserCode(orgRelation.getCreatedBy());

				if(user != null){
					StringBuilder userNameSB = new StringBuilder();
					orgRelation.setVendorCreateduserName(userNameSB.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
				}
				List<OrgSpoc> orgSpocs = orgSpocService.getAllSpocsByOrgRelationId(orgRelation.getId(), loginUser.getId(),orgLocations.getId(),"");

				orgRelation.setNoOfContatcs(orgSpocs.size());

			}
		
			
			model.addObject("loginOrgLocId",orgLocations.getId());
			model.addObject("clientsList", clientsList);
			
			//for get the owners list for spocs
			//List<User> userList = userService.getOwnersByOrgLocationId(orgLocations.getId());
			List<User> userList = userService.getTabAccessOwnersByOrgLocationIdAndTabId(orgLocations.getId(),Long.valueOf(Constants.TAB_CLIENT));
			model.addObject("userList", userList);
			
			List<Countries> countryList = countriesService.getAllCountries();
			model.addObject("countryList", countryList);
			
			if(null != orgLocations && StringUtils.isNotEmpty(orgLocations.getCountry())){
				
				Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
				if(null != countries){
				   model.addObject("loginCountryName", countries.getName());
				}
				if(countries!= null && countries.getName()!= null) {
		        List<States> statesList = statesService.getAllStatesByCountry(countries.getName());
		        model.addObject("statesList", statesList);
			}
			}
			
			commonUtil.highLightMenu(Constants.Clients, request);
			String msg = "";
			msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "clientCreateMsg");
			
			if(msg != null && !"".equals(msg)){
				model.addObject("msg", msg);
				SourceLeadSessionManager.removeSessionAttribute(request, "clientCreateMsg");
			}else{
				msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "clientContactCreateMsg");
				if(msg != null){
					model.addObject("msg", msg);
					SourceLeadSessionManager.removeSessionAttribute(request, "clientContactCreateMsg");
				}
			}
			if(msg == null){
				msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "clientImport");
				model.addObject("msg", msg);
				SourceLeadSessionManager.removeSessionAttribute(request, "clientImport");
			}
			
			if(orgRelationUUID != null){
				OrgRelations orgRelations = orgRelationsService.getOrgRelationsByUUID(orgRelationUUID);
				if(orgRelations.getLocationId().getId().longValue() == orgLocations.getId().longValue()){
					model.addObject("clientOrgLocationId", orgRelations.getResponderLocationId().getId());
					model.addObject("clientName", orgRelations.getResponderLocationId().getOrgLocationName());
				}else{
					model.addObject("clientOrgLocationId", orgRelations.getLocationId().getId());
					model.addObject("clientName", orgRelations.getLocationId().getOrgLocationName());
				}
				
				model.addObject("orgRelationId", orgRelations.getId());
				model.addObject("orgRelationUUID", orgRelationUUID);
			}
			
			if(orgSpocUUID != null){
			
			OrgSpoc orgSpoc = orgSpocService.getOrgSpocByUUID(orgSpocUUID);
				
			if(orgSpoc != null){
				model.addObject("spocId", orgSpoc.getId());
				model.addObject("ownerId", orgSpoc.getVendorUser().getId());
				model.addObject("spocUserId", orgSpoc.getClientUser().getId());
				model.addObject("tempOwnerId", orgSpoc.getVendorUser().getId());
				model.addObject("spocName", orgSpoc.getClientUser().getFullName());
				model.addObject("spocLocationId", orgSpoc.getClientLocationId().getId());
				model.addObject("orgRelationId", orgSpoc.getOrgRelations().getId());
				model.addObject("userId", orgSpoc.getVendorUser().getId());
			 }
			}
			
			model.addObject("sourceleadUrl", sourceleadUrl);
			return model;
	    }
	    
	 
	    /**
		 * Handles to display client jsp
		 * @return ModelAndView
		 * @throws Exception
		 */
	 @PreAuthorize("hasAnyRole('ROLE_CREATE_CLIENT_CONTACT')")
		 @RequestMapping(value = "/addClientContact")
		 public ModelAndView addClientContact(HttpServletRequest request,@RequestParam(value = "cid", required = false) String cid) throws Exception {
			 ModelAndView model = new ModelAndView();
				try{
					
					OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
					List<User> userList = userService.getTabAccessOwnersByOrgLocationIdAndTabId(orgLocations.getId(),Long.valueOf(Constants.TAB_CLIENT));
					model.addObject("cid",cid);
					model.addObject("userList", userList);
					model.setViewName("client/addClientContact");
					model.addObject("sourceleadUrl", sourceleadUrl);
					
					List<OrgRelations> vandorsListByOrgLocationId = orgRelationsService.getAllClientsByOrgLocationId(orgLocations.getId());
					model.addObject("loginOrgLocId", orgLocations.getId());
					model.addObject("clientContactList", vandorsListByOrgLocationId);
					
					 SourceleadActions action = sourceleadActionsService.getSourceleadActionsByActionRole(Constants.ROLE_CREATE_CLIENT);
				     model.addObject("actionId",action.getId());
				     List<SourceleadSectionDTO> sections  = sourceleadFormSectionsService.getSourceleadFormSectionsByActionId(action.getId(),orgLocations.getId());
					 model.addObject("sourceleadSectionList",sections);
					 
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
		    }
		
//		 /uploadClientForm
	 @PreAuthorize("hasAnyRole('ROLE_CLIENT_BULK_IMPORTS','ROLE_CLIENT_CONTACT_BULK_IMPORTS')")
		 @RequestMapping(value = "/uploadClientForm")
			public ModelAndView uploadClientForm(HttpServletRequest request) {
			 SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
				if(sessionData == null)
				{
					sessionData = new SessionMaintainanceData();
				}
				sessionData.setNav(Constants.Users);
				
				request.getSession().setAttribute("sessionData",sessionData);
				ModelAndView model = new ModelAndView();
				model.setViewName("upload/uploadClientForm");
				model.addObject("sourceleadUrl", sourceleadUrl);
				sessionData.setNav(Constants.Clients);
				return model;

			}

}