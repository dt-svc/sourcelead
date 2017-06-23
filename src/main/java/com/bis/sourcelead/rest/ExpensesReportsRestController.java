package com.bis.sourcelead.rest;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.service.ExpensesReportService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
public class ExpensesReportsRestController {

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	public CommonUtil commonUtil;
	
	@Autowired
	private ExpensesReportService expensesReportService;
	
	@Autowired
	private ProjectMembersService projectMembersService;

	@RequestMapping(value = "/getExpensesReportList")
	public String getExpensesApprovalList(HttpServletRequest request,@RequestParam(value="datequarterly", required=false) String datequarterly) throws Exception {
		LinkedHashSet<ProjectMembers> finalProjectMemberList = new LinkedHashSet<>();
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<ProjectMembers> projectMembers = null;
	   if(null != datequarterly && !"".equalsIgnoreCase(datequarterly) && !datequarterly.equals("undefined")){
		String[] dates = datequarterly.split("TO");
		String startDate = dates[0];
		String  endtDate = dates[1];
		 
		Date convertedStartDate = Constants.DMY.parse(startDate);
		Date convertedEndDate = Constants.DMY.parse(endtDate);
		LinkedHashSet<String> dateList = CalendarUtil.getMonthStringsFromStartDateAndEndDate(convertedStartDate, convertedEndDate);
		 DateFormat format =Constants.Y;
		 String year = format.format(convertedEndDate);
		 Map<String,Date> janStartDateAndEndDate = CalendarUtil.getStartAndEndDateFromMonthAndYear(0, year);
		 Map<String,Date> febStartDateAndEndDate = CalendarUtil.getStartAndEndDateFromMonthAndYear(1, year);
		 Map<String,Date> marStartDateAndEndDate = CalendarUtil.getStartAndEndDateFromMonthAndYear(2, year);
		 Map<String,Date> aprStartDateAndEndDate = CalendarUtil.getStartAndEndDateFromMonthAndYear(3, year);
		 Map<String,Date> mayStartDateAndEndDate = CalendarUtil.getStartAndEndDateFromMonthAndYear(4, year);
		 Map<String,Date> junStartDateAndEndDate = CalendarUtil.getStartAndEndDateFromMonthAndYear(5, year);
		 Map<String,Date> julStartDateAndEndDate = CalendarUtil.getStartAndEndDateFromMonthAndYear(6, year);
		 Map<String,Date> augStartDateAndEndDate = CalendarUtil.getStartAndEndDateFromMonthAndYear(7, year);
		 Map<String,Date> sepStartDateAndEndDate = CalendarUtil.getStartAndEndDateFromMonthAndYear(8, year);
		 Map<String,Date> octStartDateAndEndDate = CalendarUtil.getStartAndEndDateFromMonthAndYear(9, year);
		 Map<String,Date> novStartDateAndEndDate = CalendarUtil.getStartAndEndDateFromMonthAndYear(10,year);
		 Map<String,Date> decStartDateAndEndDate = CalendarUtil.getStartAndEndDateFromMonthAndYear(11,year);
		 
		 Date janStartDate = janStartDateAndEndDate.get("STARTDATE");
		 Date janEndDate =   janStartDateAndEndDate.get("ENDDATE");
		 
		 Date febStartDate = febStartDateAndEndDate.get("STARTDATE");
		 Date febEndDate =   febStartDateAndEndDate.get("ENDDATE");
		 
		 Date marStartDate = marStartDateAndEndDate.get("STARTDATE");
		 Date marEndDate =   marStartDateAndEndDate.get("ENDDATE");
		 
		 Date aprStartDate = aprStartDateAndEndDate.get("STARTDATE");
		 Date aprEndDate =   aprStartDateAndEndDate.get("ENDDATE");
		 
		 Date mayStartDate = mayStartDateAndEndDate.get("STARTDATE");
		 Date mayEndDate =   mayStartDateAndEndDate.get("ENDDATE");
		 
		 Date junStartDate = junStartDateAndEndDate.get("STARTDATE");
		 Date junEndDate =   junStartDateAndEndDate.get("ENDDATE");
		 
		 Date julStartDate = julStartDateAndEndDate.get("STARTDATE");
		 Date julEndDate =   julStartDateAndEndDate.get("ENDDATE");
		 
		 Date augStartDate = augStartDateAndEndDate.get("STARTDATE");
		 Date augEndDate =   augStartDateAndEndDate.get("ENDDATE");
		 
		 Date sepStartDate = sepStartDateAndEndDate.get("STARTDATE");
		 Date sepEndDate =   sepStartDateAndEndDate.get("ENDDATE");
		 
		 Date octStartDate = octStartDateAndEndDate.get("STARTDATE");
		 Date octEndDate =   octStartDateAndEndDate.get("ENDDATE");
		 
		 Date novStartDate = novStartDateAndEndDate.get("STARTDATE");
		 Date novEndDate =   novStartDateAndEndDate.get("ENDDATE");
		 
		 Date decStartDate = decStartDateAndEndDate.get("STARTDATE");
		 Date decEndDate =   decStartDateAndEndDate.get("ENDDATE");
		 
		 
		 projectMembers = projectMembersService.getProjectMembersListByOrgLocationsIdAndApproverUserId(convertedStartDate, convertedEndDate, orgLoc.getId(), loginUser.getId());
		 int count = 0;
		 if(projectMembers != null && !projectMembers.isEmpty()){
		 for(ProjectMembers projectMember :projectMembers){
			 Float Q1ExpenseTotal = 0f;
			 Float Q2ExpenseTotal = 0f;
			 Float Q3ExpenseTotal = 0f;
			 Float Q4ExpenseTotal = 0f;
			 Float expenseAmount = 0f;
			 Float expenseApprovedAmount = 0f;
			 Float expenseUnApprovedAmount = 0f;
			 if(count == 0)
				 projectMember.setMonthsStringFormat(dateList);
			 List<Expenses> expenses  = expensesReportService.getExpensesByProjectMemberIdAndExpenseStartAndEndDate(projectMember.getId(),convertedStartDate,convertedEndDate);
			 if(expenses != null && !expenses.isEmpty()){
			 for(Expenses exp:expenses){
				 if(null != projectMember.getEmployee()){
						if(null != projectMember.getEmployee().getUserEmploymentType()){
							projectMember.setEmployementType(exp.getProjectMembers().getEmployee().getUserEmploymentType());
						}
						if(null != projectMember.getEmployee().getUser()){
							if(null != projectMember.getEmployee().getUser().getLastName()){
								StringBuilder nameBuilder = new StringBuilder();
								projectMember.setUserName(nameBuilder.append(projectMember.getEmployee().getUser().getFirstName()).append(projectMember.getEmployee().getUser().getLastName()).toString());
							}else{
								projectMember.setUserName(projectMember.getEmployee().getUser().getFirstName());
							}
							
							if(null != projectMember.getEmployee().getUser().getEmail()){
								projectMember.setUserEmail(projectMember.getEmployee().getUser().getEmail());
							}else{
								projectMember.setUserEmail("--");
							}
						}
						if(null != projectMember.getProjects()){
							projectMember.setProjectName(projectMember.getProjects().getProjectName());
						}else{
							projectMember.setProjectName("--");
						}
					}
				 
				 Date d = new Date(exp.getEndDate().getTime());
				 if(CalendarUtil.isDateBetweenTwoDates(janStartDate, janEndDate, d)){
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 Float approvedTotal = 0f;
						 if(projectMember.getJanExpenseApprovedTotal() != null){
							 approvedTotal = projectMember.getJanExpenseApprovedTotal();
						 }
						  approvedTotal = approvedTotal+exp.getTotalAmount();
						  projectMember.setJanExpenseApprovedTotal(approvedTotal);
						  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
					 }
					 else{
						 Float unApprovedTotal = 0f;
						 if(projectMember.getJanExpenseUnApprovedTotal() != null){
							 unApprovedTotal = projectMember.getJanExpenseUnApprovedTotal();
						 }
						 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
						 projectMember.setJanExpenseUnApprovedTotal(unApprovedTotal);
						 expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
					 }
					 
					 Float expenseTotal = 0f;
					 if(projectMember.getJanExpenseTotal() != null){
						 expenseTotal = projectMember.getJanExpenseTotal();
					 }
					 expenseTotal = expenseTotal+exp.getTotalAmount();
					 projectMember.setJanExpenseTotal(expenseTotal);
					 Q1ExpenseTotal = Q1ExpenseTotal+expenseTotal;
				 }
				 
				 else if(CalendarUtil.isDateBetweenTwoDates(febStartDate, febEndDate, d)){
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 Float approvedTotal = 0f;
						 if(projectMember.getFebExpenseApprovedTotal() != null){
							 approvedTotal = projectMember.getFebExpenseApprovedTotal();
						 }
						  approvedTotal = approvedTotal+exp.getTotalAmount();
						  projectMember.setFebExpenseApprovedTotal(approvedTotal);
						  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
					 }
					 else{
						 Float unApprovedTotal = 0f;
						 if(projectMember.getFebExpenseUnApprovedTotal() != null){
							 unApprovedTotal = projectMember.getFebExpenseUnApprovedTotal();
						 }
						 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
						 projectMember.setFebExpenseUnApprovedTotal(unApprovedTotal);
						  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
					 }
					 
					 Float expenseTotal = 0f;
					 if(projectMember.getFebExpenseTotal() != null){
						 expenseTotal = projectMember.getFebExpenseTotal();
					 }
					 expenseTotal = expenseTotal+exp.getTotalAmount();
					 projectMember.setFebExpenseTotal(expenseTotal);
					 Q1ExpenseTotal = Q1ExpenseTotal+expenseTotal;
				 }
				 
				 else if(CalendarUtil.isDateBetweenTwoDates(marStartDate, marEndDate, d)){
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 Float approvedTotal = 0f;
						 if(projectMember.getMarExpenseApprovedTotal() != null){
							 approvedTotal = projectMember.getMarExpenseApprovedTotal();
						 }
						  approvedTotal = approvedTotal+exp.getTotalAmount();
						  projectMember.setMarExpenseApprovedTotal(approvedTotal);
						  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
					 }
					 else{
						 Float unApprovedTotal = 0f;
						 if(projectMember.getFebExpenseUnApprovedTotal() != null){
							 unApprovedTotal = projectMember.getMarExpenseUnApprovedTotal();
						 }
						 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
						 projectMember.setMarExpenseUnApprovedTotal(unApprovedTotal);
						  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
					 }
					 
					 Float expenseTotal = 0f;
					 if(projectMember.getMarExpenseTotal() != null){
						 expenseTotal = projectMember.getMarExpenseTotal();
					 }
					 expenseTotal = expenseTotal+exp.getTotalAmount();
					 projectMember.setMarExpenseTotal(expenseTotal);
					 Q1ExpenseTotal = Q1ExpenseTotal+expenseTotal;
				 }
				 else if(CalendarUtil.isDateBetweenTwoDates(aprStartDate, aprEndDate, d)){
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 Float approvedTotal = 0f;
						 if(projectMember.getAprExpenseApprovedTotal() != null){
							 approvedTotal = projectMember.getAprExpenseApprovedTotal();
						 }
						  approvedTotal = approvedTotal+exp.getTotalAmount();
						  projectMember.setAprExpenseApprovedTotal(approvedTotal);
						  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
					 }
					 else{
						 Float unApprovedTotal = 0f;
						 if(projectMember.getAprExpenseUnApprovedTotal() != null){
							 unApprovedTotal = projectMember.getAprExpenseUnApprovedTotal();
						 }
						 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
						 projectMember.setAprExpenseUnApprovedTotal(unApprovedTotal);
						  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
					 }
					 
					 Float expenseTotal = 0f;
					 if(projectMember.getAprExpenseTotal() != null){
						 expenseTotal = projectMember.getAprExpenseTotal();
					 }
					 expenseTotal = expenseTotal+exp.getTotalAmount();
					 projectMember.setAprExpenseTotal(expenseTotal);
					 Q2ExpenseTotal = Q2ExpenseTotal+expenseTotal;
				 }
				 
				 else if(CalendarUtil.isDateBetweenTwoDates(mayStartDate, mayEndDate, d)){
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 Float approvedTotal = 0f;
						 if(projectMember.getMayExpenseApprovedTotal() != null){
							 approvedTotal = projectMember.getMayExpenseApprovedTotal();
						 }
						  approvedTotal = approvedTotal+exp.getTotalAmount();
						  projectMember.setMayExpenseApprovedTotal(approvedTotal);
						  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
					 }
					 else{
						 Float unApprovedTotal = 0f;
						 if(projectMember.getMayExpenseUnApprovedTotal() != null){
							 unApprovedTotal = projectMember.getMayExpenseUnApprovedTotal();
						 }
						 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
						 projectMember.setMayExpenseUnApprovedTotal(unApprovedTotal);
						  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
					 }
					 
					 Float expenseTotal = 0f;
					 if(projectMember.getMayExpenseTotal() != null){
						 expenseTotal = projectMember.getMayExpenseTotal();
					 }
					 expenseTotal = expenseTotal+exp.getTotalAmount();
					 projectMember.setMayExpenseTotal(expenseTotal);
					 Q2ExpenseTotal = Q2ExpenseTotal+expenseTotal;
				 }
				 
				 else if(CalendarUtil.isDateBetweenTwoDates(junStartDate, junEndDate, d)){
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 Float approvedTotal = 0f;
						 if(projectMember.getJunExpenseApprovedTotal() != null){
							 approvedTotal = projectMember.getJunExpenseApprovedTotal();
						 }
						  approvedTotal = approvedTotal+exp.getTotalAmount();
						  projectMember.setJunExpenseApprovedTotal(approvedTotal);
						  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
					 }
					 else{
						 Float unApprovedTotal = 0f;
						 if(projectMember.getJunExpenseUnApprovedTotal() != null){
							 unApprovedTotal = projectMember.getJunExpenseUnApprovedTotal();
						 }
						 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
						 projectMember.setJunExpenseUnApprovedTotal(unApprovedTotal);
						  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
					 }
					 
					 Float expenseTotal = 0f;
					 if(projectMember.getJunExpenseTotal() != null){
						 expenseTotal = projectMember.getJunExpenseTotal();
					 }
					 expenseTotal = expenseTotal+exp.getTotalAmount();
					 projectMember.setJunExpenseTotal(expenseTotal);
					 Q2ExpenseTotal = Q2ExpenseTotal+expenseTotal;
				 }
				 
				 else if(CalendarUtil.isDateBetweenTwoDates(julStartDate, julEndDate, d)){
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 Float approvedTotal = 0f;
						 if(projectMember.getJulExpenseApprovedTotal() != null){
							 approvedTotal = projectMember.getJulExpenseApprovedTotal();
						 }
						  approvedTotal = approvedTotal+exp.getTotalAmount();
						  projectMember.setJulExpenseApprovedTotal(approvedTotal);
						  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
					 }
					 else{
						 Float unApprovedTotal = 0f;
						 if(projectMember.getJulExpenseUnApprovedTotal() != null){
							 unApprovedTotal = projectMember.getJulExpenseUnApprovedTotal();
						 }
						 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
						 projectMember.setJulExpenseUnApprovedTotal(unApprovedTotal);
						  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
					 }
					 
					 Float expenseTotal = 0f;
					 if(projectMember.getJulExpenseTotal() != null){
						 expenseTotal = projectMember.getJulExpenseTotal();
					 }
					 expenseTotal = expenseTotal+exp.getTotalAmount();
					 projectMember.setJulExpenseTotal(expenseTotal);
					 Q3ExpenseTotal = Q3ExpenseTotal+expenseTotal;
				 }
				 
				 else if(CalendarUtil.isDateBetweenTwoDates(augStartDate, augEndDate, d)){
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 Float approvedTotal = 0f;
						 if(projectMember.getAugExpenseApprovedTotal() != null){
							 approvedTotal = projectMember.getAugExpenseApprovedTotal();
						 }
						  approvedTotal = approvedTotal+exp.getTotalAmount();
						  projectMember.setAugExpenseApprovedTotal(approvedTotal);
						  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
					 }
					 else{
						 Float unApprovedTotal = 0f;
						 if(projectMember.getAugExpenseUnApprovedTotal() != null){
							 unApprovedTotal = projectMember.getAugExpenseUnApprovedTotal();
						 }
						 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
						 projectMember.setAugExpenseUnApprovedTotal(unApprovedTotal);
						  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
					 }
					 
					 Float expenseTotal = 0f;
					 if(projectMember.getAugExpenseTotal() != null){
						 expenseTotal = projectMember.getAugExpenseTotal();
					 }
					 expenseTotal = expenseTotal+exp.getTotalAmount();
					 projectMember.setAugExpenseTotal(expenseTotal);
					 Q3ExpenseTotal = Q3ExpenseTotal+expenseTotal;
				 }
				 
				 else if(CalendarUtil.isDateBetweenTwoDates(sepStartDate, sepEndDate, d)){
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 Float approvedTotal = 0f;
						 if(projectMember.getSepExpenseApprovedTotal() != null){
							 approvedTotal = projectMember.getSepExpenseApprovedTotal();
						 }
						  approvedTotal = approvedTotal+exp.getTotalAmount();
						  projectMember.setSepExpenseApprovedTotal(approvedTotal);
						  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
					 }
					 else{
						 Float unApprovedTotal = 0f;
						 if(projectMember.getSepExpenseUnApprovedTotal() != null){
							 unApprovedTotal = projectMember.getSepExpenseUnApprovedTotal();
						 }
						 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
						 projectMember.setSepExpenseUnApprovedTotal(unApprovedTotal);
						  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
					 }
					 
					 Float expenseTotal = 0f;
					 if(projectMember.getSepExpenseTotal() != null){
						 expenseTotal = projectMember.getSepExpenseTotal();
					 }
					 expenseTotal = expenseTotal+exp.getTotalAmount();
					 projectMember.setSepExpenseTotal(expenseTotal);
					 Q3ExpenseTotal = Q3ExpenseTotal+expenseTotal;
				 }
				 
				 
				 else if(CalendarUtil.isDateBetweenTwoDates(octStartDate, octEndDate, d)){
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 Float approvedTotal = 0f;
						 if(projectMember.getOctExpenseApprovedTotal() != null){
							 approvedTotal = projectMember.getOctExpenseApprovedTotal();
						 }
						  approvedTotal = approvedTotal+exp.getTotalAmount();
						  projectMember.setOctExpenseApprovedTotal(approvedTotal);
						  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
					 }
					 else{
						 Float unApprovedTotal = 0f;
						 if(projectMember.getOctExpenseUnApprovedTotal() != null){
							 unApprovedTotal = projectMember.getOctExpenseUnApprovedTotal();
						 }
						 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
						 projectMember.setOctExpenseUnApprovedTotal(unApprovedTotal);
						  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
					 }
					 
					 Float expenseTotal = 0f;
					 if(projectMember.getOctExpenseTotal() != null){
						 expenseTotal = projectMember.getOctExpenseTotal();
					 }
					 expenseTotal = expenseTotal+exp.getTotalAmount();
					 projectMember.setOctExpenseTotal(expenseTotal);
					 Q4ExpenseTotal = Q4ExpenseTotal+expenseTotal;
				 }
				 
				 else if(CalendarUtil.isDateBetweenTwoDates(novStartDate, novEndDate, d)){
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 Float approvedTotal = 0f;
						 if(projectMember.getNovExpenseApprovedTotal() != null){
							 approvedTotal = projectMember.getNovExpenseApprovedTotal();
						 }
						  approvedTotal = approvedTotal+exp.getTotalAmount();
						  projectMember.setNovExpenseApprovedTotal(approvedTotal);
						  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
					 }
					 else{
						 Float unApprovedTotal = 0f;
						 if(projectMember.getNovExpenseUnApprovedTotal() != null){
							 unApprovedTotal = projectMember.getNovExpenseUnApprovedTotal();
						 }
						 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
						 projectMember.setNovExpenseUnApprovedTotal(unApprovedTotal);
						  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
					 }
					 
					 Float expenseTotal = 0f;
					 if(projectMember.getNovExpenseTotal() != null){
						 expenseTotal = projectMember.getNovExpenseTotal();
					 }
					 expenseTotal = expenseTotal+exp.getTotalAmount();
					 projectMember.setNovExpenseTotal(expenseTotal);
					 Q4ExpenseTotal = Q4ExpenseTotal+expenseTotal;
				 }
				 
				 else if(CalendarUtil.isDateBetweenTwoDates(decStartDate, decEndDate, d)){
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 Float approvedTotal = 0f;
						 if(projectMember.getDecExpenseApprovedTotal() != null){
							 approvedTotal = projectMember.getDecExpenseApprovedTotal();
						 }
						  approvedTotal = approvedTotal+exp.getTotalAmount();
						  projectMember.setDecExpenseApprovedTotal(approvedTotal);
						  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
					 }
					 else{
						 Float unApprovedTotal = 0f;
						 if(projectMember.getDecExpenseUnApprovedTotal() != null){
							 unApprovedTotal = projectMember.getDecExpenseUnApprovedTotal();
						 }
						 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
						 projectMember.setDecExpenseUnApprovedTotal(unApprovedTotal);
						  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
					 }
					 
					 Float expenseTotal = 0f;
					 if(projectMember.getDecExpenseTotal() != null){
						 expenseTotal = projectMember.getDecExpenseTotal();
					 }
					 expenseTotal = expenseTotal+exp.getTotalAmount();
					 projectMember.setDecExpenseTotal(expenseTotal);
					 Q4ExpenseTotal = Q4ExpenseTotal+expenseTotal;
				  } 
			     }
			      expenseAmount = Q1ExpenseTotal+Q2ExpenseTotal+Q3ExpenseTotal+Q4ExpenseTotal;
			      projectMember.setExpenseTotal(expenseAmount);
			      projectMember.setExpenseApprovedTotal(expenseApprovedAmount);
			      projectMember.setExpenseUnApprovedTotal(expenseUnApprovedAmount);
			      projectMember.setProjectStatus("ACTION REQUIRED");
			      finalProjectMemberList.add(projectMember);
			      count++;
			   }
			 }
		    }
		}
	   return commonUtil.toJSON(finalProjectMemberList);
	}
	
	
	private Map<String, String> getCurrentYearAndCurrentQuater() {
	      Map<String, String> currentYearAndQuater = new HashMap<>();
	      String currentYear = CalendarUtil.getYearPatternByYearPreference(new Date(), 0);
	      String currentQuater = CalendarUtil.getQuarterByDate(new Date());
	      currentYearAndQuater.put(currentYear, currentQuater);
		return currentYearAndQuater;
	
}
	
	

	@RequestMapping(value = "/getYearsAndQuartersComboList")
	public String[] getYearsAndQuartersComboList(HttpServletRequest request) throws Exception {
		Map<String, Map<String,String>> quarterlyDateList = expensesReportService.getYearsAndQuartersComboList();
		String resultJson = commonUtil.toJSON(getCurrentYearAndCurrentQuater());
		String resultJson1 = commonUtil.toJSON(quarterlyDateList);
		String[] output = new String[2];
		output[0] = resultJson;
		output[1] = resultJson1;
		return output;
	}

}
