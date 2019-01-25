package com.bis.sourcelead.rest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.NotificationDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Notifications;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.NotificationsService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

/**
 * 
 * @author Prasanna vadla
 *
 */

@RestController
@RequestMapping("/app")
public class NotificationRestController {

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	/*
	 * To change the notification mode as read
	 */

	@Autowired
	private NotificationsService notificationsService;
	
	@Autowired
    private UserService userService;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	@Autowired
	private TimeZoneService timeZoneService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private OrgSpocService orgSpocService;
	/**
	 * when user click on the notifications, we are updating those notifications mode as read 
	 * @param userId
	 * @param orgCode
	 * @param locationCode
	 * @return
	 */
	@RequestMapping(value = "/notificationStatusAsRead", method = RequestMethod.POST)
	public @ResponseBody String notificationStatusAsRead(@RequestParam(value = "orgCode", required = false) String orgCode,
						@RequestParam(value = "locationCode", required = false) String locationCode, HttpServletRequest request) {
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		
		List<Notifications> notificationList = notificationsService.getNotificationsListByUserId(loginUser.getId(),orgCode,locationCode);
		
		for(Notifications notification : notificationList){
			notification.setMode(Constants.NOTIFICATION_READ);
			notificationsService.updateNotificationStatusAsRead(loginUser.getId(), orgCode, locationCode);
		}
		return "success";
	}
	
	
	/**
	 * when user delete the notification, we are updating that notification status as deleted of that particular user
	 * 
	 * @param notificationId
	 * @param orgCode
	 * @param locationCode
	 * @return
	 */
	@RequestMapping(value = "/deleteNotification", method = RequestMethod.POST)
	public @ResponseBody String deleteNotification(
			@RequestParam(value = "notificationId", required = false) Long notificationId,
			@RequestParam(value = "orgCode", required = false) String orgCode,
			@RequestParam(value = "locationCode", required = false) String locationCode) {
			notificationsService.updateNotificationDeleteStatus(notificationId,orgCode,locationCode);
		return "success";
	}

	/**
	 * when user delete all the notification, we are updating all notification status as deleted of that particular user
	 * @param notificationIds
	 * @param orgCode
	 * @param locationCode
	 * @return
	 */
	@RequestMapping(value = "/deleteAllNotification", method = RequestMethod.POST)
	public @ResponseBody String deleteAllNotification(
			@RequestParam(value = "notificationIds", required = false) String notificationIds,
			@RequestParam(value = "orgCode", required = false) String orgCode,
			@RequestParam(value = "locationCode", required = false) String locationCode) {
		String[] notificationId  = notificationIds.split(",");
		for (int i = 0; i < notificationId.length; i++) {
			notificationsService.updateNotificationDeleteStatus(Long.parseLong(notificationId[i]),orgCode,locationCode);
		}
		
		return "success";
	}
	
	
	@RequestMapping(value = {"/getNotificationsByuserId","/rest/getNotificationsByuserId"}, method = RequestMethod.GET)
	@ResponseBody
	public  ResponseList<NotificationDTO> getNotificationsByuserId(@RequestParam(value = "userId", required = false) Long userId,
						@RequestParam(value = "locationCode", required = false) String locationCode, 
						@RequestParam(value = "orgCode", required = false) String orgCode,
						@RequestParam(value = "orgLocationId", required = false) Long orgLocationId,HttpServletRequest request) throws Exception {
		
		ResponseList<NotificationDTO> responseList = new ResponseList<>();
		User loginUser = userService.getUserById(userId);
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
		List<Notifications> notificationList = notificationsService.getTopTenNotificationsListByUserId(loginUser.getId(),orgCode,locationCode);
		List<NotificationDTO> notificationDTOList = new ArrayList<>();
		for (Notifications notifications : notificationList) {
			StringBuilder sb = new StringBuilder();
			NotificationDTO notificationDTO = new NotificationDTO();
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
			if(notifications.getNotificationFrom().getLastName() != null){
				sb.append(notifications.getNotificationFrom().getFirstName()).append(notifications.getNotificationFrom().getLastName());
			}else{
				sb.append(notifications.getNotificationFrom().getFirstName());
			}
			notificationDTO.setFirstName(sb.toString());
			notificationDTO.setUserType(notifications.getNotificationFrom().getUserType());
			notificationDTO.setFileUrl(notifications.getNotificationFrom().getFileUrl());
			notificationDTO.setBody(notifications.getBody());
			notificationDTO.setCalDays(notifications.getCalDays());
			notificationDTO.setActionLink(notifications.getActionLink());
			notificationDTO.setUuId(notifications.getUuId());
			notificationDTO.setMode(notifications.getMode());
			if(StringUtils.isNotBlank(notifications.getSpocUUID())){
				notificationDTO.setSpocUUID(notifications.getSpocUUID());
				OrgSpoc orgSpoc = orgSpocService.getOrgSpocByUUID(notifications.getSpocUUID());
				notificationDTO.setSpocInvitationStatus(orgSpoc.getInvitationStatus());
				notificationDTO.setOrgRelationUUID(orgSpoc.getOrgRelations().getUUID());
				String responderType = "";
				if(userId.longValue() == orgSpoc.getClientUser().getId().longValue())
					responderType = "vendor";
				else
					responderType = "client";
				notificationDTO.setResponderType(responderType);
			}
			
			notificationDTOList.add(notificationDTO);
				}
		responseList.setData(notificationDTOList);
		return responseList;
		
	}
	
	@RequestMapping(value = "/notificationStatusRead", method = RequestMethod.POST)
	public @ResponseBody String notificationStatusRead(@RequestParam(value = "uuId", required = false) String uuId,HttpServletRequest request) throws Exception {
		Notifications notification = notificationsService.getNotificationDetailsByUUID(uuId);
			notification.setMode(Constants.NOTIFICATION_READ);
			notificationsService.saveNotification(notification);
		return "success";
	}
	
	
}
