package com.bis.sourcelead.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.Notifications;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.VendorOnboarding;
import com.bis.sourcelead.service.ClientService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.NotificationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.VendorOnboardService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class VendorOnboardController {
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	VendorOnboardService vendorOnboardService;
	
	@Autowired
	LookUpDataService lookUpDataService;
	
	@Autowired
	NotificationsService notificationsService;
	
	@Autowired
	OrgSpocService orgSpocService;
	
	@Autowired
	ClientService clientService;
	
	
	@RequestMapping(value = "/acceptVendorOnboardInvitation")
 	public ModelAndView vendorOnboard(@RequestParam(value="onboardUUID", required = true)String onboardUUID,
 			@RequestParam(value="notificationUUID", required = true)String notificationUUID,HttpServletRequest request) throws Exception {
		    
		    ModelAndView model = new ModelAndView();
 			
 			List<LookUpData> industryList = lookUpDataService.getLookUpDataByCategory(Constants.INDUSTIRES);
 			model.addObject("industryList", industryList);
 			
 			model.addObject("onboardUUID",onboardUUID);
 			model.addObject("notificationUUID",notificationUUID);
 			
 			VendorOnboarding vendorOnboarding = vendorOnboardService.getVendorOnboardingByUUID(onboardUUID);
 			model.addObject("onboardSubmittedStatus",vendorOnboarding.getOnboardSubmitted());
 			OrgLocations vendorOrgLocations = vendorOnboarding.getVendorLocationId();
 			model.addObject("vendorOnboardId",vendorOnboarding.getId());
 			if(vendorOnboarding.getOnboardSubmitted().equalsIgnoreCase(Constants.NO)){
 	 			commonUtil.highLightMenu(Constants.Vendors, request);
 				model.setViewName("vendors/vendorOnboard");
 			}else{
 				
 				 Notifications notifications = notificationsService.getNotificationsByUUID(notificationUUID);
				 if(null != notifications){
					 notifications.setUserNotificationStatus(Constants.MAIL_ACCEPTED);
					 notificationsService.saveNotification(notifications);
					 
					 if(StringUtils.isNotBlank(notifications.getSpocUUID())){
						 OrgSpoc orgSpoc = orgSpocService.getSpocBySpocUUID(notifications.getSpocUUID());
						 if(orgSpoc != null){
							 orgSpoc.setInvitationStatus(Constants.MAIL_ACCEPTED);
							 orgSpocService.addOrgSpoc(orgSpoc);
						 }
						 
						 clientService.sendAcceptEmailToClientOrVendor(orgSpoc,orgSpoc.getClientUser(),vendorOrgLocations);
					 }
					
				 }
				 
 				commonUtil.highLightMenu(Constants.Clients, request);
 				model.setViewName("vendors/vendorOnboard");
 			}
 			
 			return model;
 	}
	
	
	
	@RequestMapping(value = "/rejectVendorOnboardInvitation")
 	public void rejectVendorOnboardInvitation(@RequestParam(value="onboardUUID", required = true)String onboardUUID,
 			@RequestParam(value="notificationUUID", required = true)String notificationUUID,HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		VendorOnboarding vendorOnboard = vendorOnboardService.getVendorOnboardingByUUID(onboardUUID);
		OrgLocations vendorOrgLocations = vendorOnboard.getVendorLocationId();
		vendorOnboardService.finishVendorOnboardProcess(vendorOnboard.getId(), notificationUUID, user,Constants.MAIL_REJECTED);
		StringBuilder redirectUrl = new StringBuilder();
		response.sendRedirect(redirectUrl.append("/sourcelead/dashboard/").append(vendorOrgLocations.getId()).toString());
		
 	}

}
