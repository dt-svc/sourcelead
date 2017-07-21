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
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ActionHistoryService;
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
	
	@RequestMapping(value = "/getRestActionHistoryList", method = RequestMethod.GET)
	public @ResponseBody List<ActionHistory> getExpensesCategoryList(@RequestParam(value="userLoginLogoutSessionId", required=false) Long userLoginLogoutSessionId) throws Exception {
		List<ActionHistory> actionHistoryList = actionHistoryService.getAllActionPerformedByUser(userLoginLogoutSessionId);
		if(actionHistoryList != null){
			for(ActionHistory actionHistory:actionHistoryList){
				actionHistory.setActionTimeStr(conversionUtil.getDateAsString(actionHistory.getActionTime()));
			}
		}
		return actionHistoryList;
	}
	
}
