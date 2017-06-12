package com.bis.sourcelead.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesEntries;
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
				ex.setProjectMemberId(ex.getProjectMembers().getProjects().getProjectName());	
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
	
	@RequestMapping(value = "/getIndividualExpense")
	 public String getIndividualExpense(HttpServletRequest request,String expenseId) throws Exception {

		String resultJson;
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<ExpensesEntries> expensesEntriesList = expensesService.getExpensesEntries(expenseId);
		
		
		for(ExpensesEntries exEnt:expensesEntriesList){

			if(null != exEnt.getExpenseDate())
			{
				exEnt.setExpenseDateStr(Constants.DF_DMY.format(exEnt.getExpenseDate()));
			}

			if(null == exEnt.getExpenseIndList())
			{
				List<Expenses> expensesList = expensesService.getIndividualExpense(orgLoc,loginUser,expenseId);
				for(Expenses ex:expensesList){
					if(null != ex.getProjectMembers())
					{
						ex.setProjectMemberId(ex.getProjectMembers().getProjects().getProjectName());	
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
				exEnt.setExpenseIndList(expensesList);
			}
		}
		resultJson = commonUtil.toJSON(expensesEntriesList);
		return resultJson;
	 }
	
	@RequestMapping(value = "/exportMyExpensesList")
	 public void exportMyExpensesList(HttpServletRequest request,HttpServletResponse response,
			 @RequestParam(value = "type", required = false) String type,
			 @RequestParam(value = "Expid", required = false) String Expid) throws Exception {
		
		File file = null;
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		
		Expenses ExpenseDetails = expensesService.getExpenseByExpenseId(Long.parseLong(Expid));

		if(type.equalsIgnoreCase("PDF")){
			file = expensesService.exportMyExpensesToPdf(ExpenseDetails,orgLoc,loginUser);
		}else{
			file = expensesService.exportMyExpensesToExcel(ExpenseDetails,orgLoc,loginUser);	
		}
		
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		response.setContentType(mimeType);
		StringBuilder fileName = new StringBuilder();
		response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
		response.setContentLength((int) file.length());
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
		inputStream.close();
		response.getOutputStream().close();
		response.getOutputStream().flush();	
		if(file != null){
			file.delete();
		}
	 }
	
	

}