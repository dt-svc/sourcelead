package com.bis.sourcelead.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.ActionHistory;
import com.bis.sourcelead.entity.UserLoginLogoutSession;
import com.bis.sourcelead.service.ActionHistoryService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.UserLoginLogoutSessionService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.ConversionUtil;

@RestController
@RequestMapping("/app")
public class ActionHistoryRestController {
	
	@Autowired
	private ActionHistoryService actionHistoryService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	@Autowired
	private ConversionUtil conversionUtil;
	
	@Autowired
	private UserLoginLogoutSessionService userLoginLogoutSessionService;
	
	
	@RequestMapping(value = "/ws/getRestActionHistoryList", method = RequestMethod.GET)
	public @ResponseBody List<ActionHistory> getRestActionHistoryList(@RequestParam(value = "actionUUID", required = true) String actionUUID,@RequestParam(value = "orgLocation", required = true) Long orgLocation,HttpServletRequest request) throws Exception {
		UserLoginLogoutSession userLoginLogoutSession = userLoginLogoutSessionService.getUserLoginLogoutSessionByActionUUID(actionUUID);
		List<ActionHistory> actionHistoryList = actionHistoryService.getAllActionPerformedByUserActionUUID(userLoginLogoutSession.getActionUUID(), orgLocation);
		if(actionHistoryList != null){
			for(ActionHistory actionHistory:actionHistoryList){
				actionHistory.setActionTimeStr(conversionUtil.getActionDateddMMMYYYYHHMMSS(actionHistory.getActionTime()));
			}
		}
		return actionHistoryList;
	}
	
	
	@RequestMapping(value = "/ws/getRestLocationActionHistoryList", method = RequestMethod.GET)
	public @ResponseBody List<ActionHistory> getRestLocationActionHistoryList(@RequestParam(value = "actionUUID", required = true) String actionUUID,HttpServletRequest request) throws Exception {
		UserLoginLogoutSession userLoginLogoutSession = userLoginLogoutSessionService.getUserLoginLogoutSessionByActionUUID(actionUUID);
		
		List<ActionHistory> actionHistoryListOrgLocations = actionHistoryService.getAllOrglocationsActionHistoryListActionUUID(userLoginLogoutSession.getActionUUID());
		return actionHistoryListOrgLocations;
	}
	
}
