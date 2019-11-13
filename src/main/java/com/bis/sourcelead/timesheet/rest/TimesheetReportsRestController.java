package com.bis.sourcelead.timesheet.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ReportsTimeDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.TimeReportsSubmissionDetailsDTO;
import com.bis.sourcelead.dto.TimesheetReportsDTO;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.service.AppliedLeaveDateService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.timesheet.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.timesheet.service.TimesheetReportService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.util.TimesheetApprovalFrequencyGeneratorUtil;

@RestController 
@RequestMapping("/app")
public class TimesheetReportsRestController {

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private TimesheetApprovalFrequencyGeneratorUtil timesheetApprovalFrequencyGeneratorUtil;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private ProjectMembersService projectMembersService;
	
	@Autowired
	private SubmissionApproverHierarchyService submissionApproverHierarchyService;
	
	@Autowired
	private ProjectMembersHistoryService projectMembersHistoryService;
	
	@Autowired
	private TimesheetReportService timesheetReportService;
	
	@Autowired
	private AppliedLeaveDateService appliedLeaveDateService;
	
	@Autowired
	private EmployeesService employeesService;
	
	@RequestMapping(value = "/timesheetReportsDiv", method = RequestMethod.POST ) 
	public ModelAndView prapareTimesheetReports(TimesheetReportsDTO timesheetReportsDTO,HttpServletRequest request) throws Exception {
		ModelAndView model = new  ModelAndView();
		try {
		timesheetReportsDTO = timesheetReportsDiv(timesheetReportsDTO,request);
		model.addObject("timesheetReportsDTO",timesheetReportsDTO);
		model.setViewName("timesheet/timeReportsDropDown");
		}catch(Exception e) {
			model.addObject("AWKWARD", true);
			model.setViewName("timesheet/timeReportsDropDown");
		}
		return model;
		
	}

	private TimesheetReportsDTO timesheetReportsDiv(TimesheetReportsDTO timesheetReportsDTO,HttpServletRequest request) throws Exception {
		   LinkedHashSet<String>  tsrYearsList = CalendarUtil.getYearsListFromCurrentDate(1);
			 LinkedList<String>  allMonthsList = CalendarUtil.getAllMonths();
			 LinkedList<String>  currentMonthsList = new LinkedList<>();
			 LinkedList<String>  nextyrMonthsList = new LinkedList<>();
			 LinkedList<String>  tsrMonthsList = null;
			 
			 Calendar currentCal = Calendar.getInstance(TimeZone.getDefault());
			 int cMonth = Integer.parseInt(Constants.DF_MM.format(currentCal.getTime()));
			 String currentYear = CalendarUtil.getCurrentYear();
			 String currentMonth = CalendarUtil.getCurrentMonth();
			 
			 /**
			  * for to show months for current year
			  */
			 if(cMonth> 9){
				 currentMonthsList.addAll(allMonthsList);
			 }
			 else{
			     for(int i = 0;i< cMonth+2;i++){
			    	 currentMonthsList.add(allMonthsList.get(i));
			     }
			 }
			 /**
			  * for to show months for next year
			  */
			 if(cMonth == 11){
				 nextyrMonthsList.add(allMonthsList.get(0));
			 }
			 else if(cMonth == 12){
				 nextyrMonthsList.add(allMonthsList.get(0));
				 nextyrMonthsList.add(allMonthsList.get(1));
			 }
			 
			 HashMap<String, LinkedList<String>> monthYear = new HashMap<>();
			 
			 for (String yrStr : tsrYearsList) {
			 tsrMonthsList = new LinkedList<>();	 
			 if(Integer.parseInt(currentYear)>Integer.parseInt(yrStr)){
				 tsrMonthsList.addAll(allMonthsList);
			    		
			    }
			    else if(Integer.parseInt(currentYear)==Integer.parseInt(yrStr)){
			    		tsrMonthsList.addAll(currentMonthsList);
			    }
			    else if(Integer.parseInt(currentYear)<Integer.parseInt(yrStr)){
			    		tsrMonthsList.addAll(nextyrMonthsList);
			    }
			 monthYear.put(yrStr, tsrMonthsList);
			 
			 }
			 
			 if(StringUtils.isNotEmpty(timesheetReportsDTO.getFrequencyMonth()) && StringUtils.isNotBlank(timesheetReportsDTO.getFrequencyMonth())) {
				 timesheetReportsDTO.setFrequencyMonth(timesheetReportsDTO.getFrequencyMonth());
			 }else {
				 timesheetReportsDTO.setFrequencyMonth(currentMonth);	 
			 }
			 
			 if(StringUtils.isNotEmpty(timesheetReportsDTO.getFrequencyYear()) && StringUtils.isNotBlank(timesheetReportsDTO.getFrequencyYear())) {
				 timesheetReportsDTO.setFrequencyYear(timesheetReportsDTO.getFrequencyYear());
			 }else {
				 timesheetReportsDTO.setFrequencyYear(currentYear);	 
			 }
			 
			 timesheetReportsDTO.setAllMonthsList(tsrMonthsList);
			 timesheetReportsDTO.setFrequencyYearList(tsrYearsList);
			 timesheetReportsDTO.setMonthYearList(monthYear);
			 
			 timesheetReportsDTO = prepareDateFrequencyForTimeSheetReports(timesheetReportsDTO,request);
			 timesheetReportsDTO = prepareSubmissionDetails(timesheetReportsDTO,request);
		return timesheetReportsDTO;
	}
	
	private TimesheetReportsDTO prepareDateFrequencyForTimeSheetReports(TimesheetReportsDTO timesheetReportsDTO,HttpServletRequest request) throws Exception {
		int tdSpanLength = 6;
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Map<String,Date> dateList =  CalendarUtil.getMonthStartDateAndEndDateFromGivenMonthStringForTsReports(timesheetReportsDTO.getFrequencyMonth(),timesheetReportsDTO.getFrequencyYear());
		Date startDate = dateList.get("STARTDATE");
		Date endDate = dateList.get("ENDDATE");
		DateFormat shortormat = Constants.SHORT_DF_STD_DM;
		LinkedHashMap<Date, Date> displayWeeksList = new LinkedHashMap<>();
		LinkedHashSet<String> dataTableDate = new LinkedHashSet<>();
		Map<String,String> frequencyDropDown =  timesheetApprovalFrequencyGeneratorUtil.getTabFrequencyListFromProjectInvoiceFrequency(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),startDate), commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),endDate),orgLocations.getDateFormat());
		if(frequencyDropDown != null && !frequencyDropDown.isEmpty()){
			for (Map.Entry<String,String> entry : frequencyDropDown.entrySet()) {
	    		 String  k = entry.getKey();
	    		 String  v = entry.getValue();
				 tdSpanLength =tdSpanLength+1;
			        Date weekStartDate;
			        Date weekEndDate;
				try {
					 weekStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),k.replaceAll("/", "-"));
					 weekEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),v.replaceAll("/", "-"));
					 displayWeeksList.put(weekStartDate, weekEndDate);
					 if(weekStartDate.equals(weekEndDate)){
						 dataTableDate.add(shortormat.format(weekStartDate));
					 }else{
						 StringBuilder  dateBuilder = new StringBuilder();
						 dataTableDate.add(dateBuilder.append(shortormat.format(weekStartDate)).append("-").append(shortormat.format(weekEndDate)).toString());
					 }
   					
				} catch (Exception e) {}
				timesheetReportsDTO.setDataTableDateList(dataTableDate);
			}
			timesheetReportsDTO.setDisplayWeeksList(displayWeeksList);
		}
		return timesheetReportsDTO;
	}
	
	
	
	@RequestMapping(value = "/getMemberReports", method = RequestMethod.POST ) 
	public ModelAndView prepareSubmissionDetailsWeb(TimesheetReportsDTO timesheetReportsDTO,HttpServletRequest request) throws Exception {
		ModelAndView model = new  ModelAndView();
		try {
			timesheetReportsDTO = prepareDateFrequencyForTimeSheetReports(timesheetReportsDTO,request);
			timesheetReportsDTO = prepareSubmissionDetails(timesheetReportsDTO,request);
			model.addObject("timesheetReportsDTO",timesheetReportsDTO);
			model.setViewName("timesheet/timeReportsSubmissionData");
		}catch(Exception e) {
			model.addObject("AWKWARD", true);
			model.setViewName("timesheet/timeReportsSubmissionData");
		}
		return model;
	}
	private TimesheetReportsDTO prepareSubmissionDetails(TimesheetReportsDTO timesheetReportsDTO,HttpServletRequest request) throws Exception {
		int start = timesheetReportsDTO.getStart();
		int length = 0;
		if(timesheetReportsDTO.getPageNo()<1) {
			timesheetReportsDTO.setPageNo(1);
		}
		if(timesheetReportsDTO.getPageSize()<1) {
			length = 10;
			timesheetReportsDTO.setPageSize(10);
		}else {
			length = timesheetReportsDTO.getPageSize();
		}
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		List<TimeReportsSubmissionDetailsDTO> detailsList = new ArrayList<TimeReportsSubmissionDetailsDTO>();
		
		  Map<String,Date> dateList =  CalendarUtil.getMonthStartDateAndEndDateFromGivenMonthStringForTsReports(timesheetReportsDTO.getFrequencyMonth(),timesheetReportsDTO.getFrequencyYear());
		  Date startDate = dateList.get("STARTDATE");
		  Date endDate = dateList.get("ENDDATE");
			String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");

		ResponseList<ProjectMembers> projRespList = projectMembersService.getProjectMembersListByTimesheetFrequencyOrgLocationsIdAndApproverUserId(null,startDate, endDate,null,null,null, orgLocations.getId(), loginUser.getId(),timesheetReportsDTO.getSearchValue(),null,start,length,"reports",isGlobalAccess);
		if(projRespList.getData() != null){
			
		List<ProjectMembers> projectMembers = projRespList.getData();
		
		
		timesheetReportsDTO.setTotalRows(projRespList.getRecordsTotal());
		
		
		
		int totalRows = projRespList.getRecordsTotal();
		if(totalRows > 0) {
			 int remainder = totalRows % length;
			 int pageCount = totalRows / length;
			 if(remainder > 0) {
				 timesheetReportsDTO.setPageCount(pageCount+1);
			 }
			 else {
				 timesheetReportsDTO.setPageCount(pageCount);
			 }
		}
        for (ProjectMembers projectMember : projectMembers)	{	
        	if(projectMember == null)
        		continue;
        TimeReportsSubmissionDetailsDTO details = new TimeReportsSubmissionDetailsDTO();
		/* ProjectMembers pmWithUser = projectMembersService.getConsultantUserProjectMemberByProjectMemberId(projectMember.getId());*/
		  User consultantUser = null;
			 if(projectMember != null && projectMember.getProjectMemberCandidteId() != null) {
				 if(projectMember.getProjectMemberCandidteId().getConsultantUser() != null) {
					 consultantUser = projectMember.getProjectMemberCandidteId().getConsultantUser();
					 details.setFileUrl(consultantUser.getFileUrl());
				 }
				details.setFullName(projectMember.getProjectMemberCandidteId().getFullName());
			 	details.setEmail(projectMember.getProjectMemberCandidteId().getEmail());
			 }
			 	details.setProjectMemberId(projectMember.getId());
			 
             if(null != projectMember.getProjectMemberCandidteId().getDesignationType() && !"".equalsIgnoreCase(projectMember.getProjectMemberCandidteId().getDesignationType())){
            	 details.setDesignationType(projectMember.getProjectMemberCandidteId().getDesignationType());
             }
             if(consultantUser != null){
            	   Employees employee = employeesService.getEmployeeByUserAndOrgLocationId(consultantUser.getId(),orgLocations.getId());
            	   if(employee != null)
            	   details.setEmployeeId(employee.getEmployeeId());
             }
		     if(null != projectMember && null != projectMember.getProjectMemberCandidteId() && null != projectMember.getProjectMemberCandidteId().getOrgRelationId()){
		    	 OrgRelations orgRelation = projectMember.getProjectMemberCandidteId().getOrgRelationId();
		    	 details.setProjectName(projectMember.getProjects().getProjectName());
		    	 details.setVendorName(orgRelation.getResponderLocationId().getOrgLocationName());
		     }else{
		    	 details.setProjectName(projectMember.getProjects().getProjectName());
		     }
		     if(null != projectMember.getProjects().getInvoiceFrequency()){
		    	 details.setFrequrncy(projectMember.getProjects().getInvoiceFrequency());
		     }else{
		    	 details.setFrequrncy("--"); 
		     }
		     
		     
	    	 StringBuilder rate = new StringBuilder();
	    	 if( projectMember.getPayRate() != null && StringUtils.isNotEmpty(projectMember.getPayRateType())) {
	    		 if(StringUtils.isNotEmpty(projectMember.getPayRateCurrency())){
		    		 details.setRate(rate.append(projectMember.getPayRateCurrency()).append("-").append(projectMember.getPayRate()).append("/").append(projectMember.getPayRateType()).toString()); 
		    	 }else{
		    		 details.setRate(rate.append(projectMember.getPayRate()).append("/").append(projectMember.getPayRateType()).toString());
		    	 } 
	    	 }else{
		    	 details.setRate("--");	 
		     }
		     User pmUser = null;
		     ProjectMembers conPM =  projectMembersService.getConsultantUserProjectMemberByProjectMemberId(projectMember.getId());
			 if(conPM != null) {
				 pmUser = conPM.getProjectMemberCandidteId().getConsultantUser();
			 }
			 
			 Long workLocationId = 0l;
			 if(conPM.getProjectMemberCandidteId() != null && conPM.getProjectMemberCandidteId().getWorkLocation() != null && conPM.getProjectMemberCandidteId().getWorkLocation().getId() != null) {
				workLocationId = conPM.getProjectMemberCandidteId().getWorkLocation().getId();
			 }
		     SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLoginForTsReports(loginUser.getId(),orgLocations.getId(),projectMember.getId(),startDate,endDate,isGlobalAccess);
	    	 Submission_ApprovalStatus combinedApprovalStatus = null; 
		     float monthTotalHours            = 0f;
			 float monthBillableTotalHours    = 0f;
			 float monthNonBillableTotalHours = 0f;
			 DateFormat shortormat = Constants.SHORT_DF_STD_DM;
		     if(timesheetReportsDTO.getDisplayWeeksList() != null && !timesheetReportsDTO.getDisplayWeeksList().isEmpty()){
		    	 HashMap<String, ReportsTimeDTO> entryList = new HashMap<>();
		    	 for (Map.Entry<Date,Date> entry : timesheetReportsDTO.getDisplayWeeksList().entrySet()) { 
		    		    ReportsTimeDTO reportsTimeDTO = new ReportsTimeDTO();
		    	    	HashSet<Date> pmhDateList = projectMembersHistoryService.getAllProjectMemberHistoryDatesByProjectMemberIdAndWeekStartDateEndDate(projectMember.getId(), entry.getKey(), entry.getValue());
		    	    	Submission_ApprovalStatus approvalStatus = Submission_ApprovalStatus.NEW;
		    	    	if(sah.getWeekApprovalStatusMap().get(entry.getKey()) != null){
		    	    		approvalStatus = sah.getWeekApprovalStatusMap().get(entry.getKey());
		    	    	}
		    	    	if(pmhDateList == null  ||  pmhDateList.isEmpty()){
		    	    		approvalStatus = Submission_ApprovalStatus.NOTAPPLICABLE;
		    	    	}
		    	    	
		    	    	Float leaveTotal = 0f;
		    	    	Float leaveBillableTotal = 0f;
		    	    	Float leaveNonBillableTotal = 0f;
			    	    if(consultantUser != null) {
			    	    	 Map<String,Float> leaveTotalMap = appliedLeaveDateService.getAppliedLeaveDateAndHolidayHoursByUserId(pmUser.getId(), entry.getKey(), entry.getValue(),workLocationId);
				    	     if(leaveTotalMap != null && !leaveTotalMap.isEmpty()) {
				    	    	 leaveTotal = leaveTotalMap.get("TOTAL");
				    	    	 leaveBillableTotal = leaveTotalMap.get("BILLABLE");
				    	    	 leaveNonBillableTotal = leaveTotalMap.get("NONBILLABLE");
				    	     }
				    	     
			    	    }
				    	 
		    	    	Float  weekBillableHours = 0f;
		    	    	if(sah.getWeekBillableHoursMap().get(entry.getKey()) != null){
		    	    		weekBillableHours = sah.getWeekBillableHoursMap().get(entry.getKey());
		    	    		if(leaveBillableTotal != null) {
		    	    			weekBillableHours = CommonUtil.getCombinedHours(weekBillableHours, leaveBillableTotal);
		    	    		}
		    	    	}
		    	    	Float  weekNonBillableHours = 0f;
		    	    	if(sah.getWeekNonBillableHoursMap().get(entry.getKey()) != null){
		    	    		weekNonBillableHours = sah.getWeekNonBillableHoursMap().get(entry.getKey());
		    	    		if(leaveNonBillableTotal != null) {
		    	    			weekNonBillableHours = CommonUtil.getCombinedHours(weekNonBillableHours, leaveNonBillableTotal);
		    	    		}
		    	    	}
		    	    	Float  weekTotalHours = 0f;
		    	    	if(sah.getWeekTotalHoursMap().get(entry.getKey()) != null){
		    	    		 weekTotalHours = sah.getWeekTotalHoursMap().get(entry.getKey());
		    	    		 if(leaveTotal != null) {
		    	    			 weekTotalHours = CommonUtil.getCombinedHours(weekTotalHours, leaveTotal);
			    	    	 }
		    	    	}
		    	    	
		    	    	String approvalClass = "draft";
		    	         if(approvalStatus.equals(Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL)){
		    	        	  approvalClass = "subm";
		    	    	 }
		    	    	 else  if(approvalStatus.equals(Submission_ApprovalStatus.APPROVED)){
		    	    		 approvalClass = "appr";
		    	    	 }
		    	    	 else  if(approvalStatus.equals(Submission_ApprovalStatus.REJECTED)){
		    	    		 approvalClass = "reje";
		    	    	 }
		    	    	
		    	         reportsTimeDTO.setApprovalClass(approvalClass);
		    	         
		    	         
		    	        if(approvalStatus == Submission_ApprovalStatus.REJECTED){
		    	        	if(combinedApprovalStatus == null || combinedApprovalStatus == Submission_ApprovalStatus.REJECTED){
		    	        		combinedApprovalStatus = Submission_ApprovalStatus.REJECTED;
		    	        	}
		    	        	else{
		    	        		combinedApprovalStatus = Submission_ApprovalStatus.ACTIONREQUIRED;
		    	        	}
		 					
		 				}
		 				else if(approvalStatus == Submission_ApprovalStatus.NEW){
		 					if(combinedApprovalStatus == null || combinedApprovalStatus == Submission_ApprovalStatus.NEW){
		 						combinedApprovalStatus = Submission_ApprovalStatus.NEW;
		 					}
		 					else{
		    	        		combinedApprovalStatus = Submission_ApprovalStatus.ACTIONREQUIRED;
		    	        	}
		 					
		 				}
		 				else if(approvalStatus == Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL){
		 					if(combinedApprovalStatus == null || combinedApprovalStatus == Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL){
		 						combinedApprovalStatus = Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL;
		 					}
		 					else{
		    	        		combinedApprovalStatus = Submission_ApprovalStatus.ACTIONREQUIRED;
		    	        	}
		 					
		 				}
		 				else if(approvalStatus == Submission_ApprovalStatus.APPROVED){
		 					if(combinedApprovalStatus == null || combinedApprovalStatus == Submission_ApprovalStatus.APPROVED){
		 						combinedApprovalStatus = Submission_ApprovalStatus.APPROVED;
		 					}
		 					else{
		    	        		combinedApprovalStatus = Submission_ApprovalStatus.ACTIONREQUIRED;
		    	        	}
		 					
		 				}
		    	        monthTotalHours = CommonUtil.getCombinedHours(monthTotalHours, weekTotalHours);
		    	        monthBillableTotalHours = CommonUtil.getCombinedHours(monthBillableTotalHours, weekBillableHours);
		    	        monthNonBillableTotalHours =  CommonUtil.getCombinedHours(monthNonBillableTotalHours, weekNonBillableHours);
		    	    	 
		    	        
		    	        reportsTimeDTO.setWeekTotalHours(CommonUtil.getHoursDisplayFormat(weekTotalHours,null));
		    	        reportsTimeDTO.setWeekBillabeHours(CommonUtil.getHoursDisplayFormat(weekBillableHours,null));
		    	        reportsTimeDTO.setWeekNonBillableHours(CommonUtil.getHoursDisplayFormat(weekNonBillableHours,null));
		    	        
		    	        String date = null;
		    	        StringBuilder  dateBuilder = new StringBuilder();
		    	        if(entry.getKey().equals(entry.getValue())) {
		    	        	date = dateBuilder.append(shortormat.format(entry.getKey())).toString();
		    	        }else {
		    	        	date = (dateBuilder.append(shortormat.format(entry.getKey())).append("-").append(shortormat.format(entry.getValue())).toString());
		    	        }
						
		    	        entryList.put(date,reportsTimeDTO);
		    	     }
		    	 details.setReportsTimeMap(entryList);
		       }
		     details.setMonthTotalHours(CommonUtil.getHoursDisplayFormat(monthTotalHours,null));
		     details.setMonthBillabeHours(CommonUtil.getHoursDisplayFormat(monthBillableTotalHours,null));
		     details.setMonthNonBillableHours(CommonUtil.getHoursDisplayFormat(monthNonBillableTotalHours,null));
		     	
		     
		     
		     String combinedApprStatus = "Action Required";
		     String combinedApprClass = "draft";
	         if(combinedApprovalStatus != null && combinedApprovalStatus.equals(Submission_ApprovalStatus.APPROVED)){
	    		  combinedApprStatus = "Approved";
	    		  combinedApprClass = "appr";
	    	 }
	    	 else  if(combinedApprovalStatus != null && combinedApprovalStatus.equals(Submission_ApprovalStatus.REJECTED)){
	    		  combinedApprStatus = "Rejected";
	    		  combinedApprClass = "reje";
	    	 }
	    	 else  if(combinedApprovalStatus != null && combinedApprovalStatus.equals(Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL)){
	    		  combinedApprStatus = "Submitted";
	    		  combinedApprClass = "subm";
	    	 }
	    	 else  if(combinedApprovalStatus != null &&  combinedApprovalStatus.equals(Submission_ApprovalStatus.NEW)){
	    		  combinedApprStatus = "NEW";
	    		  combinedApprClass = "draft";
	    	 }
	         details.setApprovalStatus(combinedApprStatus);
	         details.setCombinedApprovalClass(combinedApprClass);
	         
	         details.setProjectToDateTotal(CommonUtil.getHoursDisplayFormat(sah.getProjectToDateTotalHours(),null));
	         details.setProjectToDateBillableTotal(CommonUtil.getHoursDisplayFormat(sah.getProjectToDateBillableHours(),null));
	         details.setProjectToDateNonBillableTotal(CommonUtil.getHoursDisplayFormat(sah.getProjectToDateNonBillableHours(),null));
		     
	         
	         details.setLastMonthTotal(CommonUtil.getHoursDisplayFormat(sah.getPreviousMonthTotalHours(),null));
	         details.setLastMonthBillableTotal(CommonUtil.getHoursDisplayFormat(sah.getPreviousMonthBillableHours(),null));
	         details.setLastMonthNonBillableTotal(CommonUtil.getHoursDisplayFormat(sah.getPreviousMonthNonBillableHours(),null));
	         
	         
	         detailsList.add(details);
	         timesheetReportsDTO.setTimeReportsSubmissionDetails(detailsList);
        	}	
        }
		return timesheetReportsDTO;
	}
	
	@RequestMapping(value = "/timeSheetReportExportList")   
	public @ResponseBody void timeSheetReportExportList(HttpServletRequest request,HttpServletResponse response,TimesheetReportsDTO timesheetReportsDTO) throws Exception {
		   File timesheetReportFile = null;
		   try {
		   User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		   OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		   String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		   if(timesheetReportsDTO.getExportType().equalsIgnoreCase("pdf")){
		    timesheetReportFile = timesheetReportService.prepareTimeSheetExport(timesheetReportsDTO,user,orgLocations,isGlobalAccess);
		   }else{
			timesheetReportFile = timesheetReportService.prepareTimeSheetExportExcel(timesheetReportsDTO,user,orgLocations,isGlobalAccess);   
		   }
		   String mimeType = URLConnection.guessContentTypeFromName(timesheetReportFile.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);
			StringBuilder fileName = new StringBuilder();
			StringBuilder timesheetfileName = new StringBuilder();
			int pos = timesheetReportFile.getName().lastIndexOf("-");
			String result = timesheetReportFile.getName().substring(fileName.length(), pos);
			int index = timesheetReportFile.getName().lastIndexOf(".");
			String fileType = timesheetReportFile.getName().substring(index, timesheetReportFile.getName().length());
			String tsFileName = timesheetfileName.append(result).append(fileType).toString();
			
			response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(tsFileName).append("\"").toString());
			response.setContentLength((int) timesheetReportFile.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(timesheetReportFile));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
			inputStream.close();
			response.getOutputStream().close();
			response.getOutputStream().flush();
		   }catch(Exception e) {
			  e.printStackTrace();
		   }
	}
}
