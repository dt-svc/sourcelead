package com.bis.sourcelead.web;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.ActionRoles;
import com.bis.sourcelead.entity.Notifications;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.ActionRolesService;
import com.bis.sourcelead.service.ModulesService;
import com.bis.sourcelead.service.NotificationsService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@PropertySource("${propertyLocation:app.properties}")
public class RegistrationController {
	
	 
	 @Value("${sourceLeadBlockedSocialDomains}")
	 private String sourceLeadBlockedSocialDomains;
	 
	 @Value("${sourceleadUrl}")
	 private String sourceleadUrl;
	 
	 @Autowired
	 private OrgService orgService;
	 
	 @Autowired
	 private OrgLocationService orgLocationService;
	 
	 @Autowired
	 private ModulesService modulesService;
	 
	 @Autowired
	 private ActionRolesService actionRolesService;
	 
	 @Autowired
	 private NotificationsService notificationsService;
	 
	 
	 @Autowired
	 CommonUtil commonUtil;
	 
	 @PreAuthorize("hasRole('ROLE_ENROLL_ORG')")
	 @RequestMapping(value = "/enrolledOrgsList")
		public ModelAndView enrolledOrgsList(HttpServletRequest request) {
		 
		    commonUtil.highLightMenu(Constants.OrgSettings, request);
		    
			ModelAndView model = new ModelAndView();
			String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "orgCreateMsg");
			if(!isNullOrEmpty(msg)){
				model.addObject("msg", msg);
				SourceLeadSessionManager.removeSessionAttribute(request, "orgCreateMsg");
			}
			model.setViewName("registration/enrolledOrgsList");
			model.addObject("sourceleadUrl", sourceleadUrl);
			
			
			return model;

	 }
	 
	 @PreAuthorize("hasRole('ROLE_SOURCELEAD_ADMIN')")
	 @RequestMapping(value = "/enrollOrg")
		public ModelAndView registration() {
			ModelAndView model = new ModelAndView();
			
			model.setViewName("registration/enrollOrg");

			model.addObject("sourceleadUrl", sourceleadUrl);
			model.addObject("sourceLeadBlockedSocialDomains", sourceLeadBlockedSocialDomains);
			
			return model;

		}
	 
	 
	 /**
	  * @author Venkat I
	  * This method calls when client or vendor accept the invitation or accept the notification.
	  * @param respondingOrgType
	  * @param actionLink
	  * @param respondingPartyOrgLocationId
	  * @return
	  */
	 
	 @RequestMapping(value = "/acceptClientOrVendorInvitation")
		public String acceptClientOrVendorInvitation(@RequestParam(value = "respondingOrgType", required = false) String respondingOrgType,
				@RequestParam(value = "actionLink", required = false) String actionLink,
				@RequestParam(value = "UUID", required = false) String UUID,
				@RequestParam(value = "respondingPartyOrgLocationId", required = false) Long respondingPartyOrgLocationId) {
			
		    Org org = null;
		    OrgLocations orgLocations = null;
			
		    try {
		    	orgLocations = orgLocationService.getOrgLocations(respondingPartyOrgLocationId);
		    	org = orgLocations.getOrg();
		    	
		    	if(null != org){
			    	org.setOrgType("Company");
			    	if("VMS".equalsIgnoreCase(respondingOrgType)){
			    		org.setVms(Status.Y);
			    	}else{
			    		org.setAts(Status.Y);
			    	}
					orgService.updateOrg(org);
					
					 List<Long> modulesId = modulesService.getModulesIdByModuleName("Recruiting,");
					 Collection<ActionRoles> actionRolesList = actionRolesService.getSourceLeadActionRolesListByOrgId(1L,modulesId,org);
					 if(actionRolesList != null){
						 for(ActionRoles actionRoles : actionRolesList){
							 ActionRoles userActionroles = actionRolesService.getActionRolesByActionIdAndRoleIdAndLocationId(actionRoles.getTabActions().getId(), actionRoles.getRoles().getId(), org.getId());
							 if(null == userActionroles){
								 userActionroles = new ActionRoles();
								 userActionroles.setTabActions(actionRoles.getTabActions());
								 userActionroles.setRoles(actionRoles.getRoles());
								 userActionroles.setIsNotify(actionRoles.getIsNotify());
								 userActionroles.setOrg(org);
								 userActionroles.setOrgLocations(orgLocations);
								 userActionroles.setCreatedDate(new Date());
								 actionRolesService.addActionRoles(userActionroles);
							 }
							 
						 }
					 }
					 
					 Notifications notifications = notificationsService.getNotificationsByUUID(UUID);
					 if(null != notifications){
						 notifications.setUserNotificationStatus("Accepted");
						 notificationsService.saveNotification(notifications);
					 }
		    	}
				 
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		    
		    
			if(null == actionLink){
				return "redirect:/login";
			}else{
				return "redirect:/dashboard/"+respondingPartyOrgLocationId+"";
			}
			
		}
		
	 
	 
	 /**	 
	  * checks if string is null or empty
	  * @param str
	  * @return boolean
	  */
	   protected boolean isNullOrEmpty(String str) {
			return str == null || "".equals(str.trim());
		}
	
	
	}