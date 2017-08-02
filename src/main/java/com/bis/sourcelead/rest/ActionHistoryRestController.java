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
import com.bis.sourcelead.service.UserLoginLogoutSessionService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.ConversionUtil;

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
		List<ActionHistory> actionHistoryList = actionHistoryService.getAllActionPerformedByUser(userLoginLogoutSession.getId());
		if(actionHistoryList != null){
			for(ActionHistory actionHistory:actionHistoryList){
				actionHistory.setActionTimeStr(conversionUtil.getDateAsddMMMYYYYHHMMSS(actionHistory.getActionTime()));
			}
		}
		return actionHistoryList;
	}
	
}
