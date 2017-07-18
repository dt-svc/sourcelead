package com.bis.sourcelead.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.ActionHistory;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ActionHistoryService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
public class ActionHistoryRestController {
	
	@Autowired
	private ActionHistoryService actionHistoryService;
	

	
	@RequestMapping(value = "/getActionHistoryList", method = RequestMethod.GET)
	public @ResponseBody List<ActionHistory> getExpensesCategoryList(HttpServletRequest request) throws Exception {
		/*String resultJson = null;*/
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		List<ActionHistory> actionHistoryList = actionHistoryService.getAllActionPerformedByUser(user.getId());
		/*resultJson = toJSON(expensesCategoryList);*/
		return actionHistoryList;
	}
	
}
