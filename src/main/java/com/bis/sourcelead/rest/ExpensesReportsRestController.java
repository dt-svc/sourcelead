package com.bis.sourcelead.rest;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<ProjectMembers> projectMembers = null;
	   if(null != datequarterly && !"".equalsIgnoreCase(datequarterly) && !datequarterly.equals("undefined")){
		String[] dates = datequarterly.split("TO");
		String startDate = dates[0];
		String  endtDate = dates[1];
		 
		Date convertedStartDate = Constants.DMY.parse(startDate);
		Date convertedEndDate = Constants.DMY.parse(endtDate);
		//Map<String,String> dateList = CalendarUtil.getStartAndEndDateForGivenFrequency(convertedStartDate, convertedEndDate);
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
		 for(ProjectMembers projectMember :projectMembers){
			 List<Expenses> expenses  = expensesReportService.getExpensesByProjectMemberIdAndExpenseStartAndEndDate(projectMember.getId(),convertedStartDate,convertedEndDate);
			 for(Expenses exp:expenses){
				 Date d = new Date(exp.getEndDate().getTime());
				 System.out.println(d.getTime());
				 if(CalendarUtil.isDateBetweenTwoDates(janStartDate, janEndDate, d)){
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 Float approvedTotal = 0f;
						 if(projectMember.getJanExpenseApprovedTotal() != null){
							 approvedTotal = projectMember.getJanExpenseApprovedTotal();
						 }
						  approvedTotal = approvedTotal+exp.getTotalAmount();
						  projectMember.setJanExpenseApprovedTotal(approvedTotal);
					 }
					 else{
						 Float unApprovedTotal = 0f;
						 if(projectMember.getJanExpenseUnApprovedTotal() != null){
							 unApprovedTotal = projectMember.getJanExpenseUnApprovedTotal();
						 }
						 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
						 projectMember.setJanExpenseUnApprovedTotal(unApprovedTotal);
					 }
					 
					 Float expenseTotal = 0f;
					 if(projectMember.getJanExpenseTotal() != null){
						 expenseTotal = projectMember.getJanExpenseTotal();
					 }
					 expenseTotal = expenseTotal+exp.getTotalAmount();
					 projectMember.setJanExpenseTotal(expenseTotal);
				 }
				 
				 else if(CalendarUtil.isDateBetweenTwoDates(febStartDate, febEndDate, d)){
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 Float approvedTotal = 0f;
						 if(projectMember.getFebExpenseApprovedTotal() != null){
							 approvedTotal = projectMember.getFebExpenseApprovedTotal();
						 }
						  approvedTotal = approvedTotal+exp.getTotalAmount();
						  projectMember.setFebExpenseApprovedTotal(approvedTotal);
					 }
					 else{
						 Float unApprovedTotal = 0f;
						 if(projectMember.getFebExpenseUnApprovedTotal() != null){
							 unApprovedTotal = projectMember.getFebExpenseUnApprovedTotal();
						 }
						 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
						 projectMember.setFebExpenseUnApprovedTotal(unApprovedTotal);
					 }
					 
					 Float expenseTotal = 0f;
					 if(projectMember.getFebExpenseTotal() != null){
						 expenseTotal = projectMember.getFebExpenseTotal();
					 }
					 expenseTotal = expenseTotal+exp.getTotalAmount();
					 projectMember.setFebExpenseTotal(expenseTotal);
				 }
				 
				 else if(CalendarUtil.isDateBetweenTwoDates(marStartDate, marEndDate, d)){
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 Float approvedTotal = 0f;
						 if(projectMember.getMarExpenseApprovedTotal() != null){
							 approvedTotal = projectMember.getMarExpenseApprovedTotal();
						 }
						  approvedTotal = approvedTotal+exp.getTotalAmount();
						  projectMember.setMarExpenseApprovedTotal(approvedTotal);
					 }
					 else{
						 Float unApprovedTotal = 0f;
						 if(projectMember.getFebExpenseUnApprovedTotal() != null){
							 unApprovedTotal = projectMember.getMarExpenseUnApprovedTotal();
						 }
						 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
						 projectMember.setMarExpenseUnApprovedTotal(unApprovedTotal);
					 }
					 
					 Float expenseTotal = 0f;
					 if(projectMember.getMarExpenseTotal() != null){
						 expenseTotal = projectMember.getMarExpenseTotal();
					 }
					 expenseTotal = expenseTotal+exp.getTotalAmount();
					 projectMember.setMarExpenseTotal(expenseTotal);
				 }
				 else if(CalendarUtil.isDateBetweenTwoDates(aprStartDate, aprEndDate, d)){
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 Float approvedTotal = 0f;
						 if(projectMember.getAprExpenseApprovedTotal() != null){
							 approvedTotal = projectMember.getAprExpenseApprovedTotal();
						 }
						  approvedTotal = approvedTotal+exp.getTotalAmount();
						  projectMember.setAprExpenseApprovedTotal(approvedTotal);
					 }
					 else{
						 Float unApprovedTotal = 0f;
						 if(projectMember.getAprExpenseUnApprovedTotal() != null){
							 unApprovedTotal = projectMember.getAprExpenseUnApprovedTotal();
						 }
						 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
						 projectMember.setAprExpenseUnApprovedTotal(unApprovedTotal);
					 }
					 
					 Float expenseTotal = 0f;
					 if(projectMember.getAprExpenseTotal() != null){
						 expenseTotal = projectMember.getFebExpenseTotal();
					 }
					 expenseTotal = expenseTotal+exp.getTotalAmount();
					 projectMember.setAprExpenseTotal(expenseTotal);
				 }
				 
				 else if(CalendarUtil.isDateBetweenTwoDates(mayStartDate, mayEndDate, d)){
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 Float approvedTotal = 0f;
						 if(projectMember.getMayExpenseApprovedTotal() != null){
							 approvedTotal = projectMember.getMayExpenseApprovedTotal();
						 }
						  approvedTotal = approvedTotal+exp.getTotalAmount();
						  projectMember.setMayExpenseApprovedTotal(approvedTotal);
					 }
					 else{
						 Float unApprovedTotal = 0f;
						 if(projectMember.getMayExpenseUnApprovedTotal() != null){
							 unApprovedTotal = projectMember.getMayExpenseUnApprovedTotal();
						 }
						 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
						 projectMember.setMayExpenseUnApprovedTotal(unApprovedTotal);
					 }
					 
					 Float expenseTotal = 0f;
					 if(projectMember.getMayExpenseTotal() != null){
						 expenseTotal = projectMember.getFebExpenseTotal();
					 }
					 expenseTotal = expenseTotal+exp.getTotalAmount();
					 projectMember.setMayExpenseTotal(expenseTotal);
				 }
				 
				 else if(CalendarUtil.isDateBetweenTwoDates(junStartDate, junEndDate, d)){
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 Float approvedTotal = 0f;
						 if(projectMember.getJunExpenseApprovedTotal() != null){
							 approvedTotal = projectMember.getJunExpenseApprovedTotal();
						 }
						  approvedTotal = approvedTotal+exp.getTotalAmount();
						  projectMember.setJunExpenseApprovedTotal(approvedTotal);
					 }
					 else{
						 Float unApprovedTotal = 0f;
						 if(projectMember.getJunExpenseUnApprovedTotal() != null){
							 unApprovedTotal = projectMember.getJunExpenseUnApprovedTotal();
						 }
						 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
						 projectMember.setJunExpenseUnApprovedTotal(unApprovedTotal);
					 }
					 
					 Float expenseTotal = 0f;
					 if(projectMember.getJunExpenseTotal() != null){
						 expenseTotal = projectMember.getFebExpenseTotal();
					 }
					 expenseTotal = expenseTotal+exp.getTotalAmount();
					 projectMember.setJunExpenseTotal(expenseTotal);
				 }
				 
				 else if(CalendarUtil.isDateBetweenTwoDates(julStartDate, julEndDate, d)){
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 Float approvedTotal = 0f;
						 if(projectMember.getJulExpenseApprovedTotal() != null){
							 approvedTotal = projectMember.getJulExpenseApprovedTotal();
						 }
						  approvedTotal = approvedTotal+exp.getTotalAmount();
						  projectMember.setJulExpenseApprovedTotal(approvedTotal);
					 }
					 else{
						 Float unApprovedTotal = 0f;
						 if(projectMember.getJulExpenseUnApprovedTotal() != null){
							 unApprovedTotal = projectMember.getJulExpenseUnApprovedTotal();
						 }
						 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
						 projectMember.setJulExpenseUnApprovedTotal(unApprovedTotal);
					 }
					 
					 Float expenseTotal = 0f;
					 if(projectMember.getJulExpenseTotal() != null){
						 expenseTotal = projectMember.getFebExpenseTotal();
					 }
					 expenseTotal = expenseTotal+exp.getTotalAmount();
					 projectMember.setJulExpenseTotal(expenseTotal);
				 }
				 
				 else if(CalendarUtil.isDateBetweenTwoDates(augStartDate, augEndDate, d)){
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 Float approvedTotal = 0f;
						 if(projectMember.getAugExpenseApprovedTotal() != null){
							 approvedTotal = projectMember.getAugExpenseApprovedTotal();
						 }
						  approvedTotal = approvedTotal+exp.getTotalAmount();
						  projectMember.setAugExpenseApprovedTotal(approvedTotal);
					 }
					 else{
						 Float unApprovedTotal = 0f;
						 if(projectMember.getAugExpenseUnApprovedTotal() != null){
							 unApprovedTotal = projectMember.getAugExpenseUnApprovedTotal();
						 }
						 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
						 projectMember.setAugExpenseUnApprovedTotal(unApprovedTotal);
					 }
					 
					 Float expenseTotal = 0f;
					 if(projectMember.getAugExpenseTotal() != null){
						 expenseTotal = projectMember.getFebExpenseTotal();
					 }
					 expenseTotal = expenseTotal+exp.getTotalAmount();
					 projectMember.setAugExpenseTotal(expenseTotal);
				 }
				 
				 else if(CalendarUtil.isDateBetweenTwoDates(sepStartDate, sepEndDate, d)){
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 Float approvedTotal = 0f;
						 if(projectMember.getSepExpenseApprovedTotal() != null){
							 approvedTotal = projectMember.getSepExpenseApprovedTotal();
						 }
						  approvedTotal = approvedTotal+exp.getTotalAmount();
						  projectMember.setSepExpenseApprovedTotal(approvedTotal);
					 }
					 else{
						 Float unApprovedTotal = 0f;
						 if(projectMember.getSepExpenseUnApprovedTotal() != null){
							 unApprovedTotal = projectMember.getSepExpenseUnApprovedTotal();
						 }
						 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
						 projectMember.setSepExpenseUnApprovedTotal(unApprovedTotal);
					 }
					 
					 Float expenseTotal = 0f;
					 if(projectMember.getSepExpenseTotal() != null){
						 expenseTotal = projectMember.getFebExpenseTotal();
					 }
					 expenseTotal = expenseTotal+exp.getTotalAmount();
					 projectMember.setSepExpenseTotal(expenseTotal);
				 }
				 
				 
				 else if(CalendarUtil.isDateBetweenTwoDates(octStartDate, octEndDate, d)){
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 Float approvedTotal = 0f;
						 if(projectMember.getOctExpenseApprovedTotal() != null){
							 approvedTotal = projectMember.getOctExpenseApprovedTotal();
						 }
						  approvedTotal = approvedTotal+exp.getTotalAmount();
						  projectMember.setOctExpenseApprovedTotal(approvedTotal);
					 }
					 else{
						 Float unApprovedTotal = 0f;
						 if(projectMember.getOctExpenseUnApprovedTotal() != null){
							 unApprovedTotal = projectMember.getOctExpenseUnApprovedTotal();
						 }
						 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
						 projectMember.setOctExpenseUnApprovedTotal(unApprovedTotal);
					 }
					 
					 Float expenseTotal = 0f;
					 if(projectMember.getOctExpenseTotal() != null){
						 expenseTotal = projectMember.getFebExpenseTotal();
					 }
					 expenseTotal = expenseTotal+exp.getTotalAmount();
					 projectMember.setOctExpenseTotal(expenseTotal);
				 }
				 
				 else if(CalendarUtil.isDateBetweenTwoDates(novStartDate, novEndDate, d)){
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 Float approvedTotal = 0f;
						 if(projectMember.getNovExpenseApprovedTotal() != null){
							 approvedTotal = projectMember.getNovExpenseApprovedTotal();
						 }
						  approvedTotal = approvedTotal+exp.getTotalAmount();
						  projectMember.setNovExpenseApprovedTotal(approvedTotal);
					 }
					 else{
						 Float unApprovedTotal = 0f;
						 if(projectMember.getNovExpenseUnApprovedTotal() != null){
							 unApprovedTotal = projectMember.getNovExpenseUnApprovedTotal();
						 }
						 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
						 projectMember.setNovExpenseUnApprovedTotal(unApprovedTotal);
					 }
					 
					 Float expenseTotal = 0f;
					 if(projectMember.getNovExpenseTotal() != null){
						 expenseTotal = projectMember.getFebExpenseTotal();
					 }
					 expenseTotal = expenseTotal+exp.getTotalAmount();
					 projectMember.setNovExpenseTotal(expenseTotal);
				 }
				 
				 else if(CalendarUtil.isDateBetweenTwoDates(decStartDate, decEndDate, d)){
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 Float approvedTotal = 0f;
						 if(projectMember.getDecExpenseApprovedTotal() != null){
							 approvedTotal = projectMember.getDecExpenseApprovedTotal();
						 }
						  approvedTotal = approvedTotal+exp.getTotalAmount();
						  projectMember.setDecExpenseApprovedTotal(approvedTotal);
					 }
					 else{
						 Float unApprovedTotal = 0f;
						 if(projectMember.getDecExpenseUnApprovedTotal() != null){
							 unApprovedTotal = projectMember.getDecExpenseUnApprovedTotal();
						 }
						 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
						 projectMember.setDecExpenseUnApprovedTotal(unApprovedTotal);
					 }
					 
					 Float expenseTotal = 0f;
					 if(projectMember.getDecExpenseTotal() != null){
						 expenseTotal = projectMember.getFebExpenseTotal();
					 }
					 expenseTotal = expenseTotal+exp.getTotalAmount();
					 projectMember.setDecExpenseTotal(expenseTotal);
				 } 
				 
			 }
			 }
				 
			 }
	return commonUtil.toJSON(projectMembers);
			 
			 
		// Map<String, String> date = expensesReportService.getmonthSetringNamefromStartAndEndDate(convertedStartDate,convertedEndDate,projectMembers);
		
		/*for(Expenses exp:expenses){
			exp.setStartDateStr(Constants.DMY.format(exp.getStartDate()));
			exp.setEndDateStr(Constants.DMY.format(d));
			if(null != exp.getProjectMembers()){
				if(null != exp.getProjectMembers().getEmployee()){
					if(null != exp.getProjectMembers().getEmployee().getUserEmploymentType()){
						exp.setEmployementType(exp.getProjectMembers().getEmployee().getUserEmploymentType());
					}
					if(null != exp.getProjectMembers().getEmployee().getUser()){
						if(null != exp.getProjectMembers().getEmployee().getUser().getLastName()){
							StringBuilder nameBuilder = new StringBuilder();
							exp.setUserName(nameBuilder.append(exp.getProjectMembers().getEmployee().getUser().getFirstName()).append(exp.getProjectMembers().getEmployee().getUser().getLastName()).toString());
						}else{
							exp.setUserName(exp.getProjectMembers().getEmployee().getUser().getFirstName());
						}
						
						if(null != exp.getProjectMembers().getEmployee().getUser().getEmail()){
							exp.setUserEmail(exp.getProjectMembers().getEmployee().getUser().getEmail());
						}else{
							exp.setUserEmail("--");
						}
					}
					if(null != exp.getProjectMembers().getProjects()){
						exp.setProjectName(exp.getProjectMembers().getProjects().getProjectName());
					}else{
						exp.setProjectName("--");
					}
				}
			
			}
			
			exp.setMonthsStringFormat(date);
		}
		if(expenses.isEmpty()){
			Expenses exp = new Expenses();			
			exp.setMonthsStringFormat(date);
			expenses.add(exp);
		}*/
	   //commonUtil.toJSON(expenses)
		
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
