package com.bis.sourcelead.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ExpensesService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@PropertySource("${propertyLocation:app.properties}")
public class ExpensesRestController {

	private static final Logger logger = LoggerFactory.getLogger(ExpensesRestController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Autowired
	private ExpensesService expensesService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	
	@RequestMapping(value = "/getExpensesList")
	 public String getExpensesList(HttpServletRequest request) throws Exception {

		String resultJson;
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<Expenses> expensesList = expensesService.getAllExpenses(orgLoc,loginUser);
		for(Expenses ex:expensesList){
			if(null != ex.getProjectMembers())
			{
				ex.setProjectMemberId(ex.getProjectMembers().getId().toString());	
			}
			if(null != ex.getStartDate())
			{
			ex.setStartDateStr(Constants.DF_DMY.format(ex.getStartDate()));
			}
			if(null != ex.getEndDate())
			{
				ex.setEndDateStr(Constants.DF_DMY.format(ex.getEndDate()));
			}
		}
		resultJson = commonUtil.toJSON(expensesList);
		return resultJson;
	 }
	
	
	
	

}