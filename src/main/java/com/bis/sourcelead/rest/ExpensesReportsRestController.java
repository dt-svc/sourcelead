package com.bis.sourcelead.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.service.ExpensesReportService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.web.LoginSessionController;

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
	
	@Autowired
	private OrgRelationsService orgRelationsService;

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
		Date convertedStartDate = LoginSessionController.DATEFORMAT.parse(startDate);
		Date convertedEndDate = LoginSessionController.DATEFORMAT.parse(endtDate);
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
		 
		 
		 projectMembers = projectMembersService.getProjectMembersListByOrgLocationsIdAndApproverUserIdForExpenseReports(convertedStartDate, convertedEndDate, orgLoc.getId(), loginUser.getId());
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
			 Float claimedExpenseApprovedAmount = 0f;
			 Float claimedExpenseUnApprovedAmount = 0f;
			 Float claimedExpenseAmount = 0f;
			 if(count == 0)
				 projectMember.setMonthsStringFormat(dateList);
			 List<Expenses> expenses  = expensesReportService.getExpensesByProjectMemberIdAndExpenseStartAndEndDate(projectMember.getId(),convertedStartDate,convertedEndDate);
			 List<Expenses> expensesClaimendAmount  = expensesReportService.getExpensesByProjectMemberIdAndExpenseStartAndEndDate(projectMember.getId(),projectMember.getProjects().getStartDate(),new Date());
			 if(expenses != null && !expenses.isEmpty()){
			 for(Expenses exp:expenses){
				 if(null != projectMember.getEmployee()){
						if(null != projectMember.getEmployee().getUserEmploymentType()){
							projectMember.setEmployementType(exp.getProjectMembers().getEmployee().getUserEmploymentType());
						}
						if(null != projectMember.getEmployee().getEmployeeId()){
							projectMember.setEmployeeIdForExpenses(exp.getProjectMembers().getEmployee().getEmployeeId());
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
				 
				 if(exp.getClientName() != null){
					 projectMember.setClientNameExpenses(exp.getClientName());
				 }
				 
				 if(null != projectMember.getEmployee().getOrgLocations() && null != projectMember.getEmployee().getOrgRelationId() && (projectMember.getEmployee().getUserEmploymentType().equalsIgnoreCase("C2C-1099")||projectMember.getEmployee().getUserEmploymentType().equalsIgnoreCase("C2C"))){
						OrgRelations orgRelation = orgRelationsService.getClientAndVendorOrgDeatilsById(projectMember.getEmployee().getOrgLocations().getId(), projectMember.getEmployee().getOrgRelationId().getId());
						projectMember.setEmploymentTypeVendors(orgRelation.getResponderLocationId().getOrg().getOrgName());
				 
				 }				 
				 Date d = new Date(exp.getEndDate().getTime());
				 if(CalendarUtil.isDateBetweenTwoDates(janStartDate, janEndDate, d)){
					 Float approvedTotal = 0f;
					 Float unApprovedTotal = 0f;
					 
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 if(projectMember.getJanExpenseApprovedTotal() != null){
							 approvedTotal = projectMember.getJanExpenseApprovedTotal();
						 }
						  approvedTotal = approvedTotal+exp.getTotalAmount();
						  projectMember.setJanExpenseApprovedTotal(approvedTotal);
						  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
					 }
					 else{
						 if(projectMember.getJanExpenseUnApprovedTotal() != null){
							 unApprovedTotal = projectMember.getJanExpenseUnApprovedTotal();
						 }
						 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
						 projectMember.setJanExpenseUnApprovedTotal(unApprovedTotal);
						 expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
					 }
					 projectMember.setJanExpenseTotal(approvedTotal+unApprovedTotal);
					
				 }
				 
				 else if(CalendarUtil.isDateBetweenTwoDates(febStartDate, febEndDate, d)){
					 Float approvedTotal = 0f;
					 Float unApprovedTotal = 0f;
					 
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 if(projectMember.getFebExpenseApprovedTotal() != null){
							 approvedTotal = projectMember.getFebExpenseApprovedTotal();
						 }
						  approvedTotal = approvedTotal+exp.getTotalAmount();
						  projectMember.setFebExpenseApprovedTotal(approvedTotal);
						  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
					 }
					 else{
						 if(projectMember.getFebExpenseUnApprovedTotal() != null){
							 unApprovedTotal = projectMember.getFebExpenseUnApprovedTotal();
						 }
						 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
						 projectMember.setFebExpenseUnApprovedTotal(unApprovedTotal);
						  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
					 }
					 projectMember.setFebExpenseTotal(approvedTotal+unApprovedTotal);
					
				 }
				 
				 else if(CalendarUtil.isDateBetweenTwoDates(marStartDate, marEndDate, d)){
					 Float approvedTotal = 0f;
					 Float unApprovedTotal = 0f;
					 
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 if(projectMember.getMarExpenseApprovedTotal() != null){
							 approvedTotal = projectMember.getMarExpenseApprovedTotal();
						 }
						  approvedTotal = approvedTotal+exp.getTotalAmount();
						  projectMember.setMarExpenseApprovedTotal(approvedTotal);
						  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
					 }
					 else{
						 if(projectMember.getMarExpenseUnApprovedTotal() != null){
							 unApprovedTotal = projectMember.getMarExpenseUnApprovedTotal();
						 }
						 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
						 projectMember.setMarExpenseUnApprovedTotal(unApprovedTotal);
						  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
					 }
					 projectMember.setMarExpenseTotal(approvedTotal+unApprovedTotal);
					
				 }
				 else if(CalendarUtil.isDateBetweenTwoDates(aprStartDate, aprEndDate, d)){
					 Float approvedTotal = 0f;
					 Float unApprovedTotal = 0f;
					 
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 if(projectMember.getAprExpenseApprovedTotal() != null){
							 approvedTotal = projectMember.getAprExpenseApprovedTotal();
						 }
						  approvedTotal = approvedTotal+exp.getTotalAmount();
						  projectMember.setAprExpenseApprovedTotal(approvedTotal);
						  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
					 }
					 else{
						 if(projectMember.getAprExpenseUnApprovedTotal() != null){
							 unApprovedTotal = projectMember.getAprExpenseUnApprovedTotal();
						 }
						 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
						 projectMember.setAprExpenseUnApprovedTotal(unApprovedTotal);
						  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
					 }
					 projectMember.setAprExpenseTotal(approvedTotal+unApprovedTotal);
					
				 }
				 
				 else if(CalendarUtil.isDateBetweenTwoDates(mayStartDate, mayEndDate, d)){
					 Float approvedTotal = 0f;
					 Float unApprovedTotal = 0f;
					 
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 if(projectMember.getMayExpenseApprovedTotal() != null){
							 approvedTotal = projectMember.getMayExpenseApprovedTotal();
						 }
						  approvedTotal = approvedTotal+exp.getTotalAmount();
						  projectMember.setMayExpenseApprovedTotal(approvedTotal);
						  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
					 }
					 else{
						 if(projectMember.getMayExpenseUnApprovedTotal() != null){
							 unApprovedTotal = projectMember.getMayExpenseUnApprovedTotal();
						 }
						 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
						 projectMember.setMayExpenseUnApprovedTotal(unApprovedTotal);
						  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
					 }
					 projectMember.setMayExpenseTotal(approvedTotal+unApprovedTotal);
					
				 }
				 
				 else if(CalendarUtil.isDateBetweenTwoDates(junStartDate, junEndDate, d)){
					 Float approvedTotal = 0f;
					 Float unApprovedTotal = 0f;
					 
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 if(projectMember.getJunExpenseApprovedTotal() != null){
							 approvedTotal = projectMember.getJunExpenseApprovedTotal();
						 }
						  approvedTotal = approvedTotal+exp.getTotalAmount();
						  projectMember.setJunExpenseApprovedTotal(approvedTotal);
						  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
					 }
					 else{
						 if(projectMember.getJunExpenseUnApprovedTotal() != null){
							 unApprovedTotal = projectMember.getJunExpenseUnApprovedTotal();
						 }
						 unApprovedTotal = unApprovedTotal + exp.getTotalAmount();
						 projectMember.setJunExpenseUnApprovedTotal(unApprovedTotal);
						  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
					 }
					 projectMember.setJunExpenseTotal(approvedTotal+unApprovedTotal);
					
				 }
				 
				 else if(CalendarUtil.isDateBetweenTwoDates(julStartDate, julEndDate, d)){
					 Float approvedTotal = 0f;
					 Float unApprovedTotal = 0f;
					 
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 if(projectMember.getJulExpenseApprovedTotal() != null){
							 approvedTotal = projectMember.getJulExpenseApprovedTotal();
						 }
						  approvedTotal = approvedTotal+exp.getTotalAmount();
						  projectMember.setJulExpenseApprovedTotal(approvedTotal);
						  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
					 }
					 else{
						 
						 if(projectMember.getJulExpenseUnApprovedTotal() != null){
							 unApprovedTotal = projectMember.getJulExpenseUnApprovedTotal();
						 }
						 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
						 projectMember.setJulExpenseUnApprovedTotal(unApprovedTotal);
						  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
					 }
					 projectMember.setJulExpenseTotal(approvedTotal+unApprovedTotal);
					
				 }
				 
				 else if(CalendarUtil.isDateBetweenTwoDates(augStartDate, augEndDate, d)){
					 Float approvedTotal = 0f;
					 Float unApprovedTotal = 0f;
					 
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 if(projectMember.getAugExpenseApprovedTotal() != null){
							 approvedTotal = projectMember.getAugExpenseApprovedTotal();
						 }
						  approvedTotal = approvedTotal+exp.getTotalAmount();
						  projectMember.setAugExpenseApprovedTotal(approvedTotal);
						  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
					 }
					 else{
						 if(projectMember.getAugExpenseUnApprovedTotal() != null){
							 unApprovedTotal = projectMember.getAugExpenseUnApprovedTotal();
						 }
						 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
						 projectMember.setAugExpenseUnApprovedTotal(unApprovedTotal);
						  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
					 }
					 projectMember.setAugExpenseTotal(approvedTotal+unApprovedTotal);
					
				 }
				 
				 else if(CalendarUtil.isDateBetweenTwoDates(sepStartDate, sepEndDate, d)){
					 Float approvedTotal = 0f;
					 Float unApprovedTotal = 0f;
					 
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 if(projectMember.getSepExpenseApprovedTotal() != null){
							 approvedTotal = projectMember.getSepExpenseApprovedTotal();
						 }
						  approvedTotal = approvedTotal+exp.getTotalAmount();
						  projectMember.setSepExpenseApprovedTotal(approvedTotal);
						  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
					 }
					 else{
						 if(projectMember.getSepExpenseUnApprovedTotal() != null){
							 unApprovedTotal = projectMember.getSepExpenseUnApprovedTotal();
						 }
						 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
						 projectMember.setSepExpenseUnApprovedTotal(unApprovedTotal);
						  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
					 }
					 projectMember.setSepExpenseTotal(approvedTotal+unApprovedTotal);
					
				 }
				 
				 
				 else if(CalendarUtil.isDateBetweenTwoDates(octStartDate, octEndDate, d)){
					 Float approvedTotal = 0f;
					 Float unApprovedTotal = 0f;
					 
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 if(projectMember.getOctExpenseApprovedTotal() != null){
							 approvedTotal = projectMember.getOctExpenseApprovedTotal();
						 }
						  approvedTotal = approvedTotal+exp.getTotalAmount();
						  projectMember.setOctExpenseApprovedTotal(approvedTotal);
						  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
					 }
					 else{
						 if(projectMember.getOctExpenseUnApprovedTotal() != null){
							 unApprovedTotal = projectMember.getOctExpenseUnApprovedTotal();
						 }
						 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
						 projectMember.setOctExpenseUnApprovedTotal(unApprovedTotal);
						  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
					 }
					 projectMember.setOctExpenseTotal(approvedTotal+unApprovedTotal);
					 
				 }
				 
				 else if(CalendarUtil.isDateBetweenTwoDates(novStartDate, novEndDate, d)){
					 Float approvedTotal = 0f;
					 Float unApprovedTotal = 0f;
					 
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 if(projectMember.getNovExpenseApprovedTotal() != null){
							 approvedTotal = projectMember.getNovExpenseApprovedTotal();
						 }
						  approvedTotal = approvedTotal+exp.getTotalAmount();
						  projectMember.setNovExpenseApprovedTotal(approvedTotal);
						  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
					 }
					 else{
						 if(projectMember.getNovExpenseUnApprovedTotal() != null){
							 unApprovedTotal = projectMember.getNovExpenseUnApprovedTotal();
						 }
						 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
						 projectMember.setNovExpenseUnApprovedTotal(unApprovedTotal);
						  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
					 }
					 projectMember.setNovExpenseTotal(approvedTotal+unApprovedTotal);
					 
				 }
				 
				 else if(CalendarUtil.isDateBetweenTwoDates(decStartDate, decEndDate, d)){
					 Float approvedTotal = 0f;
					 Float unApprovedTotal = 0f;
					 
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 if(projectMember.getDecExpenseApprovedTotal() != null){
							 approvedTotal = projectMember.getDecExpenseApprovedTotal();
						 }
						  approvedTotal = approvedTotal+exp.getTotalAmount();
						  projectMember.setDecExpenseApprovedTotal(approvedTotal);
						  expenseApprovedAmount = expenseApprovedAmount+approvedTotal;
					 }
					 else{
						 if(projectMember.getDecExpenseUnApprovedTotal() != null){
							 unApprovedTotal = projectMember.getDecExpenseUnApprovedTotal();
						 }
						 unApprovedTotal = unApprovedTotal+exp.getTotalAmount();
						 projectMember.setDecExpenseUnApprovedTotal(unApprovedTotal);
						  expenseUnApprovedAmount = expenseUnApprovedAmount+unApprovedTotal;
					 }
					 projectMember.setDecExpenseTotal(approvedTotal+unApprovedTotal);
					 
				  } 
			     }
			 
			      Q1ExpenseTotal = projectMember.getJanExpenseTotal()+projectMember.getFebExpenseTotal()+projectMember.getMarExpenseTotal();
			      Q2ExpenseTotal = projectMember.getAprExpenseTotal()+projectMember.getMayExpenseTotal()+projectMember.getJunExpenseTotal();
			      Q3ExpenseTotal = projectMember.getJulExpenseTotal()+projectMember.getAugExpenseTotal()+projectMember.getSepExpenseTotal();
			      Q4ExpenseTotal = projectMember.getOctExpenseTotal()+projectMember.getNovExpenseTotal()+projectMember.getDecExpenseTotal();
				  expenseAmount = Q1ExpenseTotal+Q2ExpenseTotal+Q3ExpenseTotal+Q4ExpenseTotal;
			      projectMember.setQ1ExpenseTotal(Q1ExpenseTotal);
			      projectMember.setQ2ExpenseTotal(Q2ExpenseTotal);
			      projectMember.setQ3ExpenseTotal(Q3ExpenseTotal);
			      projectMember.setQ4ExpenseTotal(Q4ExpenseTotal);
			      projectMember.setExpenseTotal(expenseAmount);
			      projectMember.setExpenseApprovedTotal(expenseApprovedAmount);
			      projectMember.setExpenseUnApprovedTotal(expenseUnApprovedAmount);
			      projectMember.setProjectStatus("ACTION REQUIRED");
			     
			      
			      for(Expenses exp:expensesClaimendAmount){
						 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
							 Float approvedClaimedTotal = 0f;
							 if(projectMember.getClaimedExpenseApprovedTotal() != null){
								 approvedClaimedTotal = projectMember.getClaimedExpenseApprovedTotal();
							 }
							 approvedClaimedTotal = approvedClaimedTotal+exp.getTotalAmount();
							  projectMember.setClaimedExpenseApprovedTotal(approvedClaimedTotal);
							  claimedExpenseApprovedAmount = claimedExpenseApprovedAmount+approvedClaimedTotal;
						 }
						 else{
							 Float unApprovedClaimedTotal = 0f;
							 if(projectMember.getClaimedExpenseUnApprovedTotal() != null){
								 unApprovedClaimedTotal = projectMember.getClaimedExpenseUnApprovedTotal();
							 }
							 unApprovedClaimedTotal = unApprovedClaimedTotal+exp.getTotalAmount();
							 projectMember.setClaimedExpenseUnApprovedTotal(unApprovedClaimedTotal);
							 claimedExpenseUnApprovedAmount = claimedExpenseUnApprovedAmount+unApprovedClaimedTotal;
						 }
						 
						 Float expenseClaimedTotal = 0f;
						 if(projectMember.getClaimedExpenseTotal() != null){
							 expenseClaimedTotal = projectMember.getClaimedExpenseTotal();
						 }
						 expenseClaimedTotal = expenseClaimedTotal+exp.getTotalAmount();
						 projectMember.setClaimedExpenseTotal(expenseClaimedTotal);
				   }
			      finalProjectMemberList.add(projectMember);
			      count++;
			   }
			 }
		    }
		}
	   return commonUtil.toJSON(finalProjectMemberList);
	}
	
	
	
	
	
	
	@RequestMapping(value = "/expensesReportExportList")
	public void expensesReportExportList(HttpServletRequest request,HttpServletResponse response,
			   @RequestParam(value="frequencyMonth", required=false) String frequencyMonth,
			   @RequestParam(value="exportType", required=false) String exportType) throws Exception {
		File file = null;
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		if(exportType.equalsIgnoreCase("pdf")){
			file = expensesReportService.expensesReportExportListPdf(frequencyMonth,orgLoc,loginUser);
		}else{
			file = expensesReportService.expensesReportExportListExcel(frequencyMonth,orgLoc,loginUser);
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
