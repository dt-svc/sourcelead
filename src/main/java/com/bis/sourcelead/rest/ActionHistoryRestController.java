package com.bis.sourcelead.rest;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.ActionHistory;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.UserLoginLogoutSession;
import com.bis.sourcelead.service.ActionHistoryService;
import com.bis.sourcelead.service.UserLoginLogoutSessionService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
public class ActionHistoryRestController {
	
	@Autowired
	private ActionHistoryService actionHistoryService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	private ConversionUtil conversionUtil;
	
	@Autowired
	private UserLoginLogoutSessionService userLoginLogoutSessionService;
	
	@RequestMapping(value = "/getRestActionHistoryList", method = RequestMethod.GET)
	public @ResponseBody List<ActionHistory> getActionHistoryList(@RequestParam(value = "userSessionId", required = true) Long userSessionId,HttpServletRequest request) throws Exception {
		UserLoginLogoutSession userLoginLogoutSession = userLoginLogoutSessionService.getUserLoginLogoutSessionById(userSessionId);
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		List<ActionHistory> actionHistoryList = actionHistoryService.getAllActionPerformedByUser(userLoginLogoutSession.getId());
		if(actionHistoryList != null){
			 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for(ActionHistory actionHistory:actionHistoryList){
				Date newDate = dateFormat.parse(actionHistory.getActionTime().toString());
				DateTime zoned = new DateTime(newDate,DateTimeZone.forID(loginOrgLocation.getTimezone().getTimeZoneId()));
				// based on time Zone show time and date
				ZoneId zone1 = ZoneId.of(loginOrgLocation.getTimezone().getTimeZoneId());
				actionHistory.setActionTimeStr(conversionUtil.getDateAsddMMMYYYYHHMMSS(zoned.toDate(),zone1));

				//actionHistory.setActionTimeStr(zoned.toString());
			}
		}
		return actionHistoryList;
	}
	
}
