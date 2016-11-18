package com.bis.sourcelead.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Modules;
import com.bis.sourcelead.entity.Notifications;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.TimeSheetSubmissionCount;
import com.bis.sourcelead.entity.User;
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
	 private ModulesService moduleService;
	 
	 
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
	 
	 @PreAuthorize("hasRole('ROLE_ENROLL_ORG')")
	 @RequestMapping(value = "/enrollOrg")
		public ModelAndView registration() {
			ModelAndView model = new ModelAndView();
			List<Modules> modulesList = null;
			List<Modules> requirementSubModulesList = null;
			try {
				 modulesList = modulesService.getSourceLeadModules();
				 model.addObject("modulesList", modulesList);
				 
				 requirementSubModulesList = moduleService.getRequirementSubModules();
				 model.addObject("requirementSubModulesList", requirementSubModulesList);
				 
			} catch (Exception e) {
				e.printStackTrace();
			}
			
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
		    boolean flag;
			
		    try {
		    	orgLocations = orgLocationService.getOrgLocations(respondingPartyOrgLocationId);
		    	org = orgLocations.getOrg();
		    	String modules = org.getModulesId();
		    	if(null != org){
			    	org.setOrgType("Company");
			    	
			    	List<Integer> moduleIds = new ArrayList<Integer>();
					 for (String s : org.getModulesId().split(",")){
						 moduleIds.add(Integer.parseInt(s));
					 }
					 
			    	if("VMS".equalsIgnoreCase(respondingOrgType)){
			    		flag = moduleIds.contains(Constants.MODULE_ATS);
			    		if(flag == false){
			    			modules = modules+","+Constants.MODULE_ATS;
			    		}
			    	}else{
			    		flag = moduleIds.contains(Constants.MODULE_VMS);
			    		if(flag == false){
			    			modules = modules+","+Constants.MODULE_VMS;
			    		}
			    	}
			    	org.setModulesId(modules);
			    	org.setStatus(Status.ACTIVE);
					orgService.updateOrg(org);
					
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
	 
	 
	 
	 @PreAuthorize("hasRole('ROLE_ENROLL_ORG')")
	 @RequestMapping(value = "/enrolledOrgSummary")
		public ModelAndView enrolledOrgSummary(HttpServletRequest request,
				@RequestParam(value = "orgId", required = false) Long orgId) throws Exception {
			    ModelAndView model = new ModelAndView();
			
				OrgLocations orgLocations = orgLocationService.getOrgLocationByOrgId(orgId);

				List<Modules> modulesList = null;
				List<Modules> requirementSubModulesList = null;
				
				if(null != orgLocations.getOrg().getModulesId()){
					ArrayList<Modules> registeredModulesList = new ArrayList<Modules>();
					ArrayList<Modules> registeredSubModulesList = new ArrayList<Modules>();
					modulesList = modulesService.getSourceLeadModules();
					requirementSubModulesList = moduleService.getRequirementSubModules();
					
					List<Long> orgModulesList = new ArrayList<Long>();
					 for (String s : orgLocations.getOrg().getModulesId().split(",")){
						 orgModulesList.add(Long.parseLong(s));
					 }
					 
					 for(Modules modules : modulesList){
						 if((orgModulesList.contains(modules.getId()))){
							 modules.setIsModuleConfigured(Constants.YES);
						 }else{
							 modules.setIsModuleConfigured(Constants.NO);
						 }
						 registeredModulesList.add(modules);
					 }
					 
					 for(Modules modules : requirementSubModulesList){
						 if(orgModulesList.contains(modules.getId())){
							 modules.setIsModuleConfigured(Constants.YES);
						 }else{
							 modules.setIsModuleConfigured(Constants.NO);
						 }
						 registeredSubModulesList.add(modules);
					 }
					 
					 model.addObject("registeredModulesList", registeredModulesList);
					 model.addObject("registeredSubModulesList", registeredSubModulesList);
				}
				
				model.addObject("orgloc",orgLocations);
				model.addObject("orgId",orgId);
				model.setViewName("registration/enrolledOrgSummary");
				model.addObject("sourceleadUrl", sourceleadUrl);
				
			
			return model;

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