package com.bis.sourcelead.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.NotificationDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Notifications;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.service.NotificationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

/**
 * 
 * @author Prasanna vadla
 *
 */

@Controller
@RequestMapping("/app")
public class NotificationController {

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	TimeZoneService timeZoneService;
	
	@Autowired
	OrgSpocService orgSpocService;
	
	/*
	 * To change the notification mode as read
	 */
	private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);
	@Autowired
	private NotificationsService notificationsService;

	

	/**
	 * to get a list of notification of particular user by user id
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/notificationsList")
	public ModelAndView notificationsList(HttpServletRequest request) throws Exception {
		logger.debug("notificationsList");
		ModelAndView model = new ModelAndView();
		Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request,"userId");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
		String locationCode =  (String) SourceLeadSessionManager.getSessionAttribute(request,"locationCode");
		String orgCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "orgCode");
		List<Notifications> notificationsList = notificationsService.getAllNotificationsListByUserId(userId,orgLocations.getOrg().getOrgCode(), locationCode);
		if(notificationsList!=null){
			calculateDays(notificationsList, timezone);
		}
		model.setViewName("notifications/notificationsList");
		model.addObject("notificationsList", notificationsList);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("orgCode", orgLocations.getOrg().getOrgCode());
		model.addObject("locationCode", locationCode);
		
		SourceLeadSessionManager.removeSessionAttribute(request, "notificationList");
		SourceLeadSessionManager.setSessionAttribute(request, "notificationList",notificationsList);
		
		Integer notificationCount = notificationsService.getNotificationsCountByUserId(userId,orgCode,locationCode);
		SourceLeadSessionManager.removeSessionAttribute(request, "notificationCount");
		SourceLeadSessionManager.setSessionAttribute(request, "notificationCount",notificationCount);
		
		return model;

	}
	
	@RequestMapping(value = "/getNotificationtLists")
	public @ResponseBody List<Notifications> getNotificationtLists(HttpServletRequest request) throws Exception {

	    Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request,"userId");
	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
		String locationCode =  (String) SourceLeadSessionManager.getSessionAttribute(request,"locationCode");
		List<Notifications> notificationsList = notificationsService.getAllNotificationsListByUserId(userId,orgLocations.getOrg().getOrgCode(), locationCode);
		if(notificationsList!=null){
			calculateDays(notificationsList,timezone);
			for(Notifications nl : notificationsList){
				nl.setNotificationFirstName(nl.getNotificationFrom().getFirstName());
				nl.setNotificationLastName(nl.getNotificationFrom().getLastName());
				nl.setNotificationFormId(nl.getNotificationFrom().getId().toString());
				nl.setNotificationImage(nl.getNotificationFrom().getFileUrl());
				nl.setUuId(nl.getUuId());
				if(StringUtils.isNotBlank(nl.getSpocUUID())){
					OrgSpoc orgSpoc = orgSpocService.getOrgSpocByUUID(nl.getSpocUUID());
					nl.setSpocInvitationStatus(orgSpoc.getInvitationStatus());
					nl.setOrgRelationUUID(orgSpoc.getOrgRelations().getUUID());
					String responderType = "";
					if(userId.longValue() == orgSpoc.getClientUser().getId().longValue())
						responderType = "vendor";
					else
						responderType = "client";
					nl.setResponderType(responderType);
				}
			}
		}
		return notificationsList;
	}
	
	
	/**
	 * calculating the days between the project created date and current date 
	 * @param notificationList
	 * @throws ParseException
	 */
	
	public void calculateDays(List<Notifications> notificationList,Timezone timezone) throws ParseException {
		for (Notifications notifications : notificationList) {
			
			long diff = commonUtil.currentDate().getTime() - notifications.getCreatedDate().getTime();
		    long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			StringBuilder  time = new StringBuilder();
			if(diffDays == 0 && diffHours == 0 && diffMinutes == 0){
				notifications.setCalDays(time.append(diffSeconds).append(" sec ago ").toString());
			}else if(diffDays == 0 && diffHours == 0){
				notifications.setCalDays(time.append(diffMinutes).append(" min ").append(diffSeconds).append(" sec ago ").toString());
			}else if(diffDays == 0){
				notifications.setCalDays(time.append(diffHours).append(" hours ").append(diffMinutes).append(" min ago ").toString());
			}else if(diffDays < 7){
				notifications.setCalDays(time.append(diffDays).append(" day(s) ago").toString());
			}else{
				DateFormat standardFormat = new SimpleDateFormat( "MMM dd, yyyy hh:mm:ss aa");
				String currentTimeStr = Constants.TZ_DF.format(notifications.getCreatedDate());
				String currentDateInTimezone= CommonUtil.convertTimeZones("UTC" ,timezone.getTimeZoneId(), currentTimeStr);
				DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
				String commonDateFormat = standardFormat.format(converteDate);
				notifications.setCalDays(commonDateFormat.toString());
			} 	
			
		}

	}
	
	/**
	 * @author Laxman
	 * DyanmicNotificationList
	 * @param notificationDTO
	 * @param request
	 * @return notificationsList
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDynamicNotificationtLists", method = RequestMethod.POST)
	public @ResponseBody ResponseList<Notifications> getDynamicNotificationtLists(NotificationDTO notificationDTO,HttpServletRequest request) throws Exception {
		ResponseList<Notifications> notificationsList =  new  ResponseList<Notifications>();
		Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request,"userId");
	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
		String locationCode =  (String) SourceLeadSessionManager.getSessionAttribute(request,"locationCode");
		 notificationsList = notificationsService.getDynamicAllNotificationsListByUserId(notificationDTO,userId,orgLocations.getOrg().getOrgCode(), locationCode,timezone);
		 if(notificationsList != null && notificationsList.getData() != null) {
			 calculateDays(notificationsList.getData(),timezone);
			 for(Notifications nl : notificationsList.getData()){
					nl.setNotificationFirstName(nl.getNotificationFrom().getFirstName());
					nl.setNotificationLastName(nl.getNotificationFrom().getLastName());
					nl.setNotificationFormId(nl.getNotificationFrom().getId().toString());
					nl.setNotificationImage(nl.getNotificationFrom().getFileUrl());
					nl.setUuId(nl.getUuId());
					if(StringUtils.isNotBlank(nl.getSpocUUID())){
						OrgSpoc orgSpoc = orgSpocService.getOrgSpocByUUID(nl.getSpocUUID());
						if(orgSpoc != null){
							nl.setSpocInvitationStatus(orgSpoc.getInvitationStatus());
							nl.setOrgRelationUUID(orgSpoc.getOrgRelations().getUUID());
							String responderType = "";
							if(userId.longValue() == orgSpoc.getClientUser().getId().longValue())
								responderType = "vendor";
							else
								responderType = "client";
							nl.setResponderType(responderType);
						}
					}
				}
		 }
		 return notificationsList;
	}
	
	
	
}
