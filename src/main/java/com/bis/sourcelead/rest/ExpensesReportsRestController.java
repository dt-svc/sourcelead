package com.bis.sourcelead.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLConnection;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/app")
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
	public LinkedHashSet<ProjectMembers> getExpensesApprovalList(HttpServletRequest request,@RequestParam(value="datequarterly", required=false) String datequarterly) throws Exception {
		LinkedHashSet<ProjectMembers> finalProjectMemberList = new LinkedHashSet<>();
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<ProjectMembers> projectMembers = null;
	   if(null != datequarterly && !"".equalsIgnoreCase(datequarterly) && !datequarterly.equals("undefined")){
		String[] dates = datequarterly.split("TO");
		String startDate = dates[0];
		String  endtDate = dates[1];
		Date convertedStartDate = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(),startDate);
		Date convertedEndDate = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(),endtDate);
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
			 //Float claimedExpenseAmount = 0f;
			 if(count == 0)
				 projectMember.setMonthsStringFormat(dateList);
			 List<Expenses> expenses  = expensesReportService.getExpensesByProjectMemberIdAndExpenseStartAndEndDate(projectMember.getId(),convertedStartDate,convertedEndDate);
			 List<Expenses> expensesClaimendAmount  = expensesReportService.getExpensesByProjectMemberIdAndExpenseStartAndEndDateForClaimedExpenses(projectMember.getId(),projectMember.getProjects().getStartDate(),new Date());
			 if(expenses != null && !expenses.isEmpty()){
				 BigDecimal janApprovedTotal = new BigDecimal(0);
				 BigDecimal janUnApprovedTotal = new BigDecimal(0);
				 BigDecimal febApprovedTotal = new BigDecimal(0);
				 BigDecimal febUnApprovedTotal = new BigDecimal(0);
				 BigDecimal marApprovedTotal = new BigDecimal(0);
				 BigDecimal marUnApprovedTotal = new BigDecimal(0);
				 BigDecimal aprApprovedTotal = new BigDecimal(0);
				 BigDecimal aprUnApprovedTotal = new BigDecimal(0);
				 BigDecimal mayApprovedTotal = new BigDecimal(0);
				 BigDecimal mayUnApprovedTotal = new BigDecimal(0);
				 BigDecimal junApprovedTotal = new BigDecimal(0);
				 BigDecimal jununApprovedTotal = new BigDecimal(0);
				 BigDecimal julApprovedTotal = new BigDecimal(0);
				 BigDecimal julUnApprovedTotal = new BigDecimal(0); 
				 BigDecimal augApprovedTotal = new BigDecimal(0);
				 BigDecimal augUnApprovedTotal = new BigDecimal(0);
				 BigDecimal sepApprovedTotal = new BigDecimal(0);
				 BigDecimal sepUnApprovedTotal = new BigDecimal(0);
				 BigDecimal octApprovedTotal = new BigDecimal(0);
				 BigDecimal octUnApprovedTotal = new BigDecimal(0);
				 BigDecimal novApprovedTotal = new BigDecimal(0);
				 BigDecimal novUnApprovedTotal = new BigDecimal(0);
				 BigDecimal decApprovedTotal = new BigDecimal(0);
				 BigDecimal decUnApprovedTotal = new BigDecimal(0);
				 
			 for(Expenses exp:expenses){
				 if(null != projectMember.getProjectMemberCandidteId()){
						if(null != projectMember.getProjectMemberCandidteId().getDesignationType()){
							projectMember.setEmployementType(projectMember.getProjectMemberCandidteId().getDesignationType());
						}
						if(projectMember.getEmployee() != null && projectMember.getEmployee().getEmployeeId() != null && StringUtils.isNotEmpty(projectMember.getEmployee().getEmployeeId())){
							projectMember.setEmployeeIdForExpenses(projectMember.getEmployee().getEmployeeId());
						}
						if(null != projectMember.getProjectMemberCandidteId()){
							if(null !=projectMember.getProjectMemberCandidteId().getLastName()){
								StringBuilder nameBuilder = new StringBuilder();
								projectMember.setUserName(nameBuilder.append(projectMember.getProjectMemberCandidteId().getFirstName()).append(projectMember.getProjectMemberCandidteId().getLastName()).toString());
							}else{
								projectMember.setUserName(projectMember.getProjectMemberCandidteId().getFirstName());
							}
							
							if(null != projectMember.getProjectMemberCandidteId().getEmail()){
								projectMember.setUserEmail(projectMember.getProjectMemberCandidteId().getEmail());
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
				 
				 if(projectMember.getProjectClientsId() != null && projectMember.getProjectClientsId().getOrgRelationId() !=null && projectMember.getProjectClientsId().getOrgRelationId().getClientName() != null){
					 projectMember.setClientNameExpenses(projectMember.getProjectClientsId().getOrgRelationId().getClientName());
				 }
				 
				 /*if(null != projectMember.getEmployee().getOrgLocations() && null != projectMember.getEmployee().getOrgRelationId() && (projectMember.getEmployee().getUserEmploymentType().equalsIgnoreCase("C2C-1099")||projectMember.getEmployee().getUserEmploymentType().equalsIgnoreCase("C2C"))){
						OrgRelations orgRelation = orgRelationsService.getClientAndVendorOrgDeatilsById(projectMember.getEmployee().getOrgLocations().getId(), projectMember.getEmployee().getOrgRelationId().getId());
						projectMember.setEmploymentTypeVendors(orgRelation.getResponderLocationId().getOrg().getOrgName());
				 
				 }*/
				 
			    StringBuilder vendor=new StringBuilder();
			 	if(null != projectMember.getProjectMemberCandidteId().getDesignationType() && !"".equalsIgnoreCase(projectMember.getProjectMemberCandidteId().getDesignationType()) && (projectMember.getProjectMemberCandidteId().getDesignationType().equalsIgnoreCase("C2C-1099")||projectMember.getProjectMemberCandidteId().getDesignationType().equalsIgnoreCase("C2C"))){
     			  OrgRelations orgRelation = projectMember.getProjectMemberCandidteId().getOrgRelationId();
     			  if(orgRelation != null) {
     				  vendor.append(projectMember.getProjectMemberCandidteId().getDesignationType()).append(",").append(orgRelation.getResponderLocationId().getOrgLocationName()).toString();
     			  }else {
     				 vendor.append(projectMember.getProjectMemberCandidteId().getDesignationType());
     			  }
           	     }else{
           	    	vendor.append("--").toString(); 
           	     }
				 
				 
				 
				 Date d = new Date(exp.getEndDate().getTime());
				 if(CalendarUtil.isDateBetweenTwoDates(janStartDate, janEndDate, d)){
					 
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 if(projectMember.getJanExpenseApprovedTotal() != null){
							 janApprovedTotal = new BigDecimal(projectMember.getJanExpenseApprovedTotal());
						 }
						  janApprovedTotal = janApprovedTotal.add(exp.getTotalAmount());
						  projectMember.setJanExpenseApprovedTotal(janApprovedTotal.floatValue());
						  expenseApprovedAmount += janApprovedTotal.floatValue();
					 }
					 else{
						 if(projectMember.getJanExpenseUnApprovedTotal() != null){
							 janUnApprovedTotal =  new BigDecimal(projectMember.getJanExpenseUnApprovedTotal());
						 }
						 janUnApprovedTotal = janUnApprovedTotal.add(exp.getTotalAmount());
						 projectMember.setJanExpenseUnApprovedTotal(janUnApprovedTotal.floatValue());
						 expenseUnApprovedAmount += janUnApprovedTotal.floatValue();
					 }
					 projectMember.setJanExpenseTotal(janApprovedTotal.floatValue()+janUnApprovedTotal.floatValue());
					
				 }
				 
				 else if(CalendarUtil.isDateBetweenTwoDates(febStartDate, febEndDate, d)){
					 
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 if(projectMember.getFebExpenseApprovedTotal() != null){
							 febApprovedTotal =  new BigDecimal(projectMember.getFebExpenseApprovedTotal());
						 }
						  febApprovedTotal = febApprovedTotal.add(exp.getTotalAmount());
						  projectMember.setFebExpenseApprovedTotal(febApprovedTotal.floatValue());
						  expenseApprovedAmount += febApprovedTotal.floatValue();
					 }
					 else{
						 if(projectMember.getFebExpenseUnApprovedTotal() != null){
							 febUnApprovedTotal =  new BigDecimal(projectMember.getFebExpenseUnApprovedTotal());
						 }
						 febUnApprovedTotal = febUnApprovedTotal.add(exp.getTotalAmount());
						 projectMember.setFebExpenseUnApprovedTotal(febUnApprovedTotal.floatValue());
						  expenseUnApprovedAmount += febUnApprovedTotal.floatValue();
					 }
					 projectMember.setFebExpenseTotal(febApprovedTotal.floatValue()+febUnApprovedTotal.floatValue());
					
				 }
				 
				 else if(CalendarUtil.isDateBetweenTwoDates(marStartDate, marEndDate, d)){
					 
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 if(projectMember.getMarExpenseApprovedTotal() != null){
							 marApprovedTotal =  new BigDecimal(projectMember.getMarExpenseApprovedTotal());
						 }
						  marApprovedTotal = marApprovedTotal.add(exp.getTotalAmount());
						  projectMember.setMarExpenseApprovedTotal(marApprovedTotal.floatValue());
						  expenseApprovedAmount += marApprovedTotal.floatValue();
					 }
					 else{
						 if(projectMember.getMarExpenseUnApprovedTotal() != null){
							 marUnApprovedTotal =  new BigDecimal( projectMember.getMarExpenseUnApprovedTotal());
						 }
						 marUnApprovedTotal = marUnApprovedTotal.add(exp.getTotalAmount());
						 projectMember.setMarExpenseUnApprovedTotal(marUnApprovedTotal.floatValue());
						  expenseUnApprovedAmount += marUnApprovedTotal.floatValue();
					 }
					 projectMember.setMarExpenseTotal(marApprovedTotal.floatValue()+marUnApprovedTotal.floatValue());
					
				 }
				 else if(CalendarUtil.isDateBetweenTwoDates(aprStartDate, aprEndDate, d)){
					 
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 if(projectMember.getAprExpenseApprovedTotal() != null){
							 aprApprovedTotal = new BigDecimal( projectMember.getAprExpenseApprovedTotal());
						 }
						  aprApprovedTotal = aprApprovedTotal.add(exp.getTotalAmount());
						  projectMember.setAprExpenseApprovedTotal(aprApprovedTotal.floatValue());
						  expenseApprovedAmount += aprApprovedTotal.floatValue();
					 }
					 else{
						 if(projectMember.getAprExpenseUnApprovedTotal() != null){
							 aprUnApprovedTotal =  new BigDecimal(projectMember.getAprExpenseUnApprovedTotal());
						 }
						 aprUnApprovedTotal = aprUnApprovedTotal.add(exp.getTotalAmount());
						 projectMember.setAprExpenseUnApprovedTotal(aprUnApprovedTotal.floatValue());
						  expenseUnApprovedAmount += aprUnApprovedTotal.floatValue();
					 }
					 projectMember.setAprExpenseTotal(aprApprovedTotal.floatValue()+aprUnApprovedTotal.floatValue());
					
				 }
				 
				 else if(CalendarUtil.isDateBetweenTwoDates(mayStartDate, mayEndDate, d)){
					 
					 
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 if(projectMember.getMayExpenseApprovedTotal() != null){
							 mayApprovedTotal =  new BigDecimal(projectMember.getMayExpenseApprovedTotal());
						 }
						  mayApprovedTotal = mayApprovedTotal.add(exp.getTotalAmount());
						  projectMember.setMayExpenseApprovedTotal(mayApprovedTotal.floatValue());
						  expenseApprovedAmount += mayApprovedTotal.floatValue();
					 }
					 else{
						 if(projectMember.getMayExpenseUnApprovedTotal() != null){
							 mayUnApprovedTotal = new BigDecimal(projectMember.getMayExpenseUnApprovedTotal());
						 }
						 mayUnApprovedTotal = mayUnApprovedTotal.add(exp.getTotalAmount());
						 projectMember.setMayExpenseUnApprovedTotal(mayUnApprovedTotal.floatValue());
						  expenseUnApprovedAmount += mayUnApprovedTotal.floatValue();
					 }
					 projectMember.setMayExpenseTotal(mayApprovedTotal.floatValue()+mayUnApprovedTotal.floatValue());
					
				 }
				 
				 else if(CalendarUtil.isDateBetweenTwoDates(junStartDate, junEndDate, d)){
					 
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 if(projectMember.getJunExpenseApprovedTotal() != null){
							 junApprovedTotal =  new BigDecimal(projectMember.getJunExpenseApprovedTotal());
						 }
						  junApprovedTotal = junApprovedTotal.add(exp.getTotalAmount());
						  projectMember.setJunExpenseApprovedTotal(junApprovedTotal.floatValue());
						  expenseApprovedAmount += junApprovedTotal.floatValue();
					 }
					 else{
						 if(projectMember.getJunExpenseUnApprovedTotal() != null){
							 jununApprovedTotal =  new BigDecimal(projectMember.getJunExpenseUnApprovedTotal());
						 }
						 jununApprovedTotal = jununApprovedTotal.add(exp.getTotalAmount());
						 projectMember.setJunExpenseUnApprovedTotal(jununApprovedTotal.floatValue());
						  expenseUnApprovedAmount += jununApprovedTotal.floatValue();
					 }
					 projectMember.setJunExpenseTotal(junApprovedTotal.floatValue()+jununApprovedTotal.floatValue());
					
				 }
				 
				 else if(CalendarUtil.isDateBetweenTwoDates(julStartDate, julEndDate, d)){
					 
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 if(projectMember.getJulExpenseApprovedTotal() != null){
							 julApprovedTotal =  new BigDecimal(projectMember.getJulExpenseApprovedTotal());
						 }
						  julApprovedTotal = julApprovedTotal.add(exp.getTotalAmount());
						  projectMember.setJulExpenseApprovedTotal(julApprovedTotal.floatValue());
						  expenseApprovedAmount += julApprovedTotal.floatValue();
					 }
					 else{
						 
						 if(projectMember.getJulExpenseUnApprovedTotal() != null){
							 julUnApprovedTotal =  new BigDecimal(projectMember.getJulExpenseUnApprovedTotal());
						 }
						 julUnApprovedTotal = julUnApprovedTotal.add(exp.getTotalAmount());
						 projectMember.setJulExpenseUnApprovedTotal(julUnApprovedTotal.floatValue());
						  expenseUnApprovedAmount += julUnApprovedTotal.floatValue();
					 }
					 projectMember.setJulExpenseTotal(julApprovedTotal.floatValue()+julUnApprovedTotal.floatValue());
					
				 }
				 
				 else if(CalendarUtil.isDateBetweenTwoDates(augStartDate, augEndDate, d)){
					 
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 if(projectMember.getAugExpenseApprovedTotal() != null){
							 augApprovedTotal =  new BigDecimal(projectMember.getAugExpenseApprovedTotal());
						 }
						  augApprovedTotal = augApprovedTotal.add(exp.getTotalAmount());
						  projectMember.setAugExpenseApprovedTotal(augApprovedTotal.floatValue());
						  expenseApprovedAmount += augApprovedTotal.floatValue();
					 }
					 else{
						 if(projectMember.getAugExpenseUnApprovedTotal() != null){
							 augUnApprovedTotal =  new BigDecimal(projectMember.getAugExpenseUnApprovedTotal());
						 }
						 augUnApprovedTotal = augUnApprovedTotal.add(exp.getTotalAmount());
						 projectMember.setAugExpenseUnApprovedTotal(augUnApprovedTotal.floatValue());
						  expenseUnApprovedAmount += augUnApprovedTotal.floatValue();
					 }
					 projectMember.setAugExpenseTotal(augApprovedTotal.floatValue()+augUnApprovedTotal.floatValue());
					
				 }
				 
				 else if(CalendarUtil.isDateBetweenTwoDates(sepStartDate, sepEndDate, d)){
					 
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 if(projectMember.getSepExpenseApprovedTotal() != null){
							 sepApprovedTotal =  new BigDecimal(projectMember.getSepExpenseApprovedTotal());
						 }
						  sepApprovedTotal = sepApprovedTotal.add(exp.getTotalAmount());
						  projectMember.setSepExpenseApprovedTotal(sepApprovedTotal.floatValue());
						  expenseApprovedAmount += sepApprovedTotal.floatValue();
					 }
					 else{
						 if(projectMember.getSepExpenseUnApprovedTotal() != null){
							 sepUnApprovedTotal =  new BigDecimal(projectMember.getSepExpenseUnApprovedTotal());
						 }
						 sepUnApprovedTotal = sepUnApprovedTotal.add(exp.getTotalAmount());
						 projectMember.setSepExpenseUnApprovedTotal(sepUnApprovedTotal.floatValue());
						  expenseUnApprovedAmount += sepUnApprovedTotal.floatValue();
					 }
					 projectMember.setSepExpenseTotal(sepApprovedTotal.floatValue()+sepUnApprovedTotal.floatValue());
					
				 }
				 
				 
				 else if(CalendarUtil.isDateBetweenTwoDates(octStartDate, octEndDate, d)){
					 
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 if(projectMember.getOctExpenseApprovedTotal() != null){
							 octApprovedTotal =  new BigDecimal(projectMember.getOctExpenseApprovedTotal());
						 }
						  octApprovedTotal = octApprovedTotal.add(exp.getTotalAmount());
						  projectMember.setOctExpenseApprovedTotal(octApprovedTotal.floatValue());
						  expenseApprovedAmount += octApprovedTotal.floatValue();
					 }
					 else{
						 if(projectMember.getOctExpenseUnApprovedTotal() != null){
							 octUnApprovedTotal =  new BigDecimal(projectMember.getOctExpenseUnApprovedTotal());
						 }
						 octUnApprovedTotal = octUnApprovedTotal.add(exp.getTotalAmount());
						 projectMember.setOctExpenseUnApprovedTotal(octUnApprovedTotal.floatValue());
						  expenseUnApprovedAmount += octUnApprovedTotal.floatValue();
					 }
					 projectMember.setOctExpenseTotal(octApprovedTotal.floatValue()+octUnApprovedTotal.floatValue());
					 
				 }
				 
				 else if(CalendarUtil.isDateBetweenTwoDates(novStartDate, novEndDate, d)){
					 
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 if(projectMember.getNovExpenseApprovedTotal() != null){
							 novApprovedTotal =  new BigDecimal(projectMember.getNovExpenseApprovedTotal());
						 }
						  novApprovedTotal = novApprovedTotal.add(exp.getTotalAmount());
						  projectMember.setNovExpenseApprovedTotal(novApprovedTotal.floatValue());
						  expenseApprovedAmount += novApprovedTotal.floatValue();
					 }
					 else{
						 if(projectMember.getNovExpenseUnApprovedTotal() != null){
							 novUnApprovedTotal =  new BigDecimal(projectMember.getNovExpenseUnApprovedTotal());
						 }
						 novUnApprovedTotal = novUnApprovedTotal.add(exp.getTotalAmount());
						 projectMember.setNovExpenseUnApprovedTotal(novUnApprovedTotal.floatValue());
						  expenseUnApprovedAmount += novUnApprovedTotal.floatValue();
					 }
					 projectMember.setNovExpenseTotal(novApprovedTotal.floatValue()+novUnApprovedTotal.floatValue());
					 
				 }
				 
				 else if(CalendarUtil.isDateBetweenTwoDates(decStartDate, decEndDate, d)){
					 
					 if(exp.getExpenseStatus() != null && exp.getExpenseStatus().equals(Submission_Status.APPROVED)){
						 if(projectMember.getDecExpenseApprovedTotal() != null){
							 decApprovedTotal =  new BigDecimal(projectMember.getDecExpenseApprovedTotal());
						 }
						  decApprovedTotal = decApprovedTotal.add(exp.getTotalAmount());
						  projectMember.setDecExpenseApprovedTotal(decApprovedTotal.floatValue());
						  expenseApprovedAmount += decApprovedTotal.floatValue();
					 }
					 else{
						 if(projectMember.getDecExpenseUnApprovedTotal() != null){
							 decUnApprovedTotal =  new BigDecimal(projectMember.getDecExpenseUnApprovedTotal());
						 }
						 decUnApprovedTotal = decUnApprovedTotal.add(exp.getTotalAmount());
						 projectMember.setDecExpenseUnApprovedTotal(decUnApprovedTotal.floatValue());
						  expenseUnApprovedAmount += decUnApprovedTotal.floatValue();
					 }
					 projectMember.setDecExpenseTotal(decApprovedTotal.floatValue()+decUnApprovedTotal.floatValue());
					 
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
							 BigDecimal approvedClaimedTotal = new BigDecimal(0);
							 if(projectMember.getClaimedExpenseApprovedTotal() != null){
								 approvedClaimedTotal = new BigDecimal(projectMember.getClaimedExpenseApprovedTotal());
							 }
							 approvedClaimedTotal = approvedClaimedTotal.add(exp.getTotalAmount());
							  projectMember.setClaimedExpenseApprovedTotal(approvedClaimedTotal.floatValue());
							  claimedExpenseApprovedAmount += approvedClaimedTotal.floatValue();
						 }
						 else{
							 BigDecimal unApprovedClaimedTotal = new BigDecimal(0);
							 if(projectMember.getClaimedExpenseUnApprovedTotal() != null){
								 unApprovedClaimedTotal = new BigDecimal(projectMember.getClaimedExpenseUnApprovedTotal());
							 }
							 unApprovedClaimedTotal = unApprovedClaimedTotal.add(exp.getTotalAmount());
							 projectMember.setClaimedExpenseUnApprovedTotal(unApprovedClaimedTotal.floatValue());
							 claimedExpenseUnApprovedAmount += unApprovedClaimedTotal.floatValue();
						 }
						 
						 BigDecimal expenseClaimedTotal = new BigDecimal(0);
						 if(projectMember.getClaimedExpenseTotal() != null){
							 expenseClaimedTotal = new BigDecimal(projectMember.getClaimedExpenseTotal());
						 }
						 if(exp.getExpenseStatus().equals(Submission_Status.APPROVED)) {
							 expenseClaimedTotal = expenseClaimedTotal.add(exp.getTotalAmount());
							 projectMember.setClaimedExpenseTotal(expenseClaimedTotal.floatValue());
						 }
				   }
			      finalProjectMemberList.add(projectMember);
			      count++;
			   }
			 }
		    }
		}
	   return finalProjectMemberList;
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
	public String[] getYearsAndQuartersComboList(HttpServletRequest request,@RequestParam(value="dateFormat", required=false) String dateFormat) throws Exception {
		Map<String, Map<String,String>> quarterlyDateList = expensesReportService.getYearsAndQuartersComboList(dateFormat);
		String resultJson = commonUtil.toJSON(getCurrentYearAndCurrentQuater());
		String resultJson1 = commonUtil.toJSON(quarterlyDateList);
		String[] output = new String[2];
		output[0] = resultJson;
		output[1] = resultJson1;
		return output;
	}

}
