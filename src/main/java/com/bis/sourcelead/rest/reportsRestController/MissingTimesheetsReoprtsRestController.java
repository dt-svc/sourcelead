package com.bis.sourcelead.rest.reportsRestController;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.reportsDTO.MissingTimesheetRepotDTO;
import com.bis.sourcelead.reportsDTO.MissingTimesheetResultRepotDTO;
import com.bis.sourcelead.reportsDTO.PendingForExpenseApprovalReportDTO;
import com.bis.sourcelead.service.ExpenseProjectApproverHierarchyService;
import com.bis.sourcelead.service.ExpensesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.timesheet.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.timesheet.service.TimeSheetDayEntriesService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.PdfUtil;
import com.bis.sourcelead.util.ReportsUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.util.TimesheetFrequencyGeneratorUtil;
import com.bis.sourcelead.util.dynamicReport.DynamicReport;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;

@RestController
@RequestMapping("/app")
public class MissingTimesheetsReoprtsRestController {
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	ProjectsService projectsService;
	
	@Autowired
	UserRoleService userRoleService;
	@Autowired
	ExpensesService expensesService;
	
	@Autowired
	TimesheetFrequencyGeneratorUtil timesheetFrequencyGeneratorUtil;
	
	@Autowired
	SubmissionApproverHierarchyService submissionApproverHierarchyService;
	
	@Autowired
	ProjectApproverHierarchyService projectApproverHierarchyService;
	
	@Autowired
	TimeSheetDayEntriesService timeSheetDayEntriesService;
	
	@Autowired
	TimeZoneService timeZoneService;
	
	@Autowired
	ReportsUtil reportsUtil;
	
	@Autowired
	PdfUtil pdfUtil;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	@Autowired
	ExpenseProjectApproverHierarchyService expenseProjectApproverHierarchyService;
	
	@Autowired
	private DynamicReport dynamicReport;

	

	@RequestMapping(value = "/getMissingTimesheetReoportsList", method = RequestMethod.POST)
	public  @ResponseBody List<MissingTimesheetResultRepotDTO> getMissingTimesheetReoportsList(MissingTimesheetRepotDTO missingTimesheetRepotDTO,HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");

         Date[] searchDates = null;
         if(StringUtils.isNotBlank(missingTimesheetRepotDTO.getDateSelection())){
        	 if(!missingTimesheetRepotDTO.getDateSelection().equalsIgnoreCase("custom")) {
     			searchDates = reportsUtil.getJobFilterDate(missingTimesheetRepotDTO.getDateSelection(),orgLocations);
     		}else {
     			Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
     			DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     			DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
     			DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd'T'23:59:59");
     			Date fromDate =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), missingTimesheetRepotDTO.getFromDate());
         	    Date toDate =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), missingTimesheetRepotDTO.getToDate());
     			String customStartTimeStr = TZ_DF_CSD.format(fromDate);
     			String customEndTimeStr = TZ_DF_CED.format(toDate);
     			String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customStartTimeStr);
     			String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customEndTimeStr);
     			fromDate = TZ_DF_TS.parse(startDateString);
     			toDate = TZ_DF_TS.parse(endDateString);
     			
         	    searchDates = new Date[2];
         	    searchDates[0] = fromDate;
         	    searchDates[1] = toDate;
     		}
         }
		 
		List<Projects> projectList = projectsService.getProjectByOrgLocationsId(orgLocations.getId());
		ArrayList<Long> projectIds = new ArrayList<>();
		if(StringUtils.isNotBlank(missingTimesheetRepotDTO.getProjectIds())) {
			String[] projectIdsStr = missingTimesheetRepotDTO.getProjectIds().split(",");
			for(int i=0; i<projectIdsStr.length; i++) {
				projectIds.add(Long.parseLong(projectIdsStr[i]));
			}
		}
		else {
			if(projectList != null && !projectList.isEmpty()) {
				for(Projects pr :projectList) {
					projectIds.add(pr.getId());
				}
			}
		} 
		List<MissingTimesheetResultRepotDTO> missingTimesheetResultRepotList = new ArrayList<>(); 
		if(projectIds != null && !projectIds.isEmpty()) {
			for(Long projectId :projectIds) {
				HashSet<ProjectMembers> prjectMembers =  userRoleService.getAllProjectMembersByProjectIdForMissingTSReports(projectId, orgLocations.getId(),user.getId());
				if(prjectMembers != null && !prjectMembers.isEmpty()) {
					for(ProjectMembers pm :prjectMembers) {
						String frequency = pm.getProjects().getInvoiceFrequency();
						Map<String,String>  timesheetFrequencyMap = timesheetFrequencyGeneratorUtil.getTimesheetFrequencyListFromProjectInvoiceFrequencyWithStartAndEndDate(pm,orgLocations,searchDates[0],searchDates[1]);
						LinkedHashMap<String,String> finalTFMap = new LinkedHashMap<>();
						if(null != timesheetFrequencyMap && !timesheetFrequencyMap.isEmpty()){
							for (Map.Entry<String, String> entry : timesheetFrequencyMap.entrySet()) {
							    String key = entry.getKey();
							    String value =entry.getValue();
							    Date frequencyStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),key.replaceAll("/", "-"));
								Date frequencyEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),value.replaceAll("/", "-"));
								
								Calendar currentCal = Calendar.getInstance(TimeZone.getDefault());
								currentCal.setLenient(false);
								currentCal.setTime(CommonUtil.currentDate());
								
					             Boolean flag = true;
					             if(CalendarUtil.getNumberOfDays(frequencyEndDate,currentCal.getTime()) <-2 && frequency.equalsIgnoreCase(Constants.BI_WEEKLY)){
					            	 flag = false;
					             }
					             Boolean isMTSPerMasterCandidate = timeSheetDayEntriesService.isMissingTimesheetByMasterCandidateIdAndOrgLocationWithStartEndDates(pm.getMasterCandidateId().getId(), frequencyStartDate, frequencyEndDate, orgLocations);
					             Boolean showEntry = false;
					             if(isMTSPerMasterCandidate) {
					            	 Boolean isMTSPerProject = submissionApproverHierarchyService.isMissingTimesheetPerProjectByMasterCandidateIdAndProjectId(frequencyStartDate, frequencyEndDate, pm.getMasterCandidateId().getId(),pm.getProjects().getId());
					                 if(isMTSPerProject) {
					                	 showEntry = true;
					                 }
					             } 
					             
					             if( showEntry && flag && frequencyEndDate.before(currentCal.getTime())){
					            	String unfilledFrequency =  key.replaceAll("/", "-")+" To "+value.replaceAll("/", "-");
					            	MissingTimesheetResultRepotDTO mtr = new MissingTimesheetResultRepotDTO();
					            	mtr.setProjectName(pm.getProjects().getProjectName());
					            	mtr.setEmployeName(pm.getProjectMemberCandidteId().getFullName());
					            	//mtr.setEmployeType(pm.getProjectMemberCandidteId().getDesignation());
					            	mtr.setEmployeType(pm.getProjectMemberCandidteId().getDesignationType());
					            	mtr.setFrequency(unfilledFrequency);
					            	mtr.setDueDays(CalendarUtil.getNumberOfDays(frequencyEndDate,currentCal.getTime()));
					            	List<ProjectApproverHierarchy> projectApproverHierarchyList = projectApproverHierarchyService.getProjectsHierarchyListByProjectIdAndOrgLocationId(pm.getProjects().getId(),orgLocations.getId()) ;
							    	String approverName = "";
							    	String seperator = "";
					            	for(ProjectApproverHierarchy pah:projectApproverHierarchyList) {
							    		if(StringUtils.isNotEmpty(approverName)) {
							    			seperator =  ",";
							    		}
							    		approverName  = approverName +seperator+ pah.getApproverUserId().getFullName();
							    	}
					            	mtr.setManagerName(approverName);
					            	missingTimesheetResultRepotList.add(mtr);
					            	finalTFMap.put(key, value);
					            
								}
							}
						}
					}
				}
			}
		}
		 return missingTimesheetResultRepotList;

	}
	
	
	@RequestMapping(value = "/getpendingForApprovalTimesheetReportList", method = RequestMethod.POST)
	public  @ResponseBody List<MissingTimesheetResultRepotDTO> getpendingForApprovalTimesheetReportList(MissingTimesheetRepotDTO missingTimesheetRepotDTO,HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");

		return getCommonListForPendingApprovalTimesheetReport(missingTimesheetRepotDTO, orgLocations, user);
		 
	}
	
	
	
	public List<MissingTimesheetResultRepotDTO> getCommonListForPendingApprovalTimesheetReport(MissingTimesheetRepotDTO missingTimesheetRepotDTO, OrgLocations orgLocations, User user) {
		
		List<MissingTimesheetResultRepotDTO> missingTimesheetResultRepotList = new ArrayList<>();
		try {
			Date[] searchDates = null;
	        if(StringUtils.isNotBlank(missingTimesheetRepotDTO.getDateSelection())){
	        	if(!missingTimesheetRepotDTO.getDateSelection().equalsIgnoreCase("custom")) {
	    			searchDates = reportsUtil.getJobFilterDate(missingTimesheetRepotDTO.getDateSelection(),orgLocations);
	    		}else {
	    			Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
	    			DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    			DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
	    			DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd'T'23:59:59");
	    			Date fromDate =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), missingTimesheetRepotDTO.getFromDate());
	       	        Date toDate =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), missingTimesheetRepotDTO.getToDate());
	    			String customStartTimeStr = TZ_DF_CSD.format(fromDate);
	    			String customEndTimeStr = TZ_DF_CED.format(toDate);
	    			String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customStartTimeStr);
	    			String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customEndTimeStr);
	    			fromDate = TZ_DF_TS.parse(startDateString);
	    			toDate = TZ_DF_TS.parse(endDateString);
	    			
	       	    searchDates = new Date[2];
	       	    searchDates[0] = fromDate;
	       	    searchDates[1] = toDate;
	    		}
	        }
			
			List<Projects> projectList = projectsService.getProjectByOrgLocationsId(orgLocations.getId());
			ArrayList<Long> projectIds = new ArrayList<>();
			if(StringUtils.isNotBlank(missingTimesheetRepotDTO.getProjectIds())) {
				String[] projectIdsStr = missingTimesheetRepotDTO.getProjectIds().split(",");
				for(int i=0; i<projectIdsStr.length; i++) {
					projectIds.add(Long.parseLong(projectIdsStr[i]));
				}
			}
			else {
				if(projectList != null && !projectList.isEmpty()) {
					for(Projects pr :projectList) {
						projectIds.add(pr.getId());
					}
				}
			}
			
			if(projectIds != null && !projectIds.isEmpty()) {
				for(Long projectId :projectIds) {
					HashSet<ProjectMembers> prjectMembers =  userRoleService.getAllProjectMembersByProjectIdForMissingTSReports(projectId, orgLocations.getId(),user.getId());
					
					if(prjectMembers != null && !prjectMembers.isEmpty()) {
						for(ProjectMembers pm :prjectMembers) {
							String frequency = pm.getProjects().getInvoiceFrequency();
							Map<String,String>  timesheetFrequencyMap = timesheetFrequencyGeneratorUtil.getTimesheetFrequencyListFromProjectInvoiceFrequencyWithStartAndEndDate(pm,orgLocations,searchDates[0],searchDates[1]);
							LinkedHashMap<String,String> finalTFMap = new LinkedHashMap<>();
							if(null != timesheetFrequencyMap && !timesheetFrequencyMap.isEmpty()){
								for (Map.Entry<String, String> entry : timesheetFrequencyMap.entrySet()) {
								    String key = entry.getKey();
								    String value =entry.getValue();
								    Date frequencyStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),key.replaceAll("/", "-"));
									Date frequencyEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),value.replaceAll("/", "-"));
									
									Calendar currentCal = Calendar.getInstance(TimeZone.getDefault());
									currentCal.setLenient(false);
									currentCal.setTime(CommonUtil.currentDate());
									
						             Boolean flag = true;
						             if(CalendarUtil.getNumberOfDays(frequencyEndDate,currentCal.getTime()) <-2 && frequency.equalsIgnoreCase(Constants.BI_WEEKLY)){
						            	 flag = false;
						             }
									
						             if(submissionApproverHierarchyService.getPendingForApprovalTimesheetReportByProjectMemberId(frequencyStartDate, frequencyEndDate, pm.getId()) && (flag) && frequencyEndDate.before(currentCal.getTime())){
						            	String unfilledFrequency =  key.replaceAll("/", "-")+" To "+value.replaceAll("/", "-");
						            	MissingTimesheetResultRepotDTO mtr = new MissingTimesheetResultRepotDTO();
						            	mtr.setProjectName(pm.getProjects().getProjectName());
						            	mtr.setEmployeName(pm.getProjectMemberCandidteId().getFullName());
						            	if(null != pm.getProjectMemberCandidteId() && null != pm.getProjectMemberCandidteId().getDesignationType() && !"".equalsIgnoreCase(pm.getProjectMemberCandidteId().getDesignationType())) {
						            		mtr.setEmployeType(pm.getEmployee().getUserEmploymentType());
						            	}
						            	mtr.setFrequency(unfilledFrequency);
						            	mtr.setDueDays(CalendarUtil.getNumberOfDays(frequencyEndDate,currentCal.getTime()));
						               // SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyForEmployeeByProjectMemberId(frequencyStartDate,frequencyEndDate ,pm.getId());
						                List<SubmissionApproverHierarchy> sahList = submissionApproverHierarchyService.getSubmissionApproverHierarchyForPendinApproveTimesheetsByProjectMemberId(frequencyStartDate,frequencyEndDate ,pm.getId());
						            	List<ProjectApproverHierarchy> projectApproverHierarchyList = projectApproverHierarchyService.getProjectsHierarchyListByProjectIdAndOrgLocationId(pm.getProjects().getId(),orgLocations.getId()) ;
								    	String approverName1 = "";
								    	String approverName2 = "";
						            	for(ProjectApproverHierarchy pah:projectApproverHierarchyList) {
									    		if(pah.getLevel() == 1) {
									    			approverName1 = pah.getApproverUserId().getFullName();
									    		}
									    		else if(pah.getLevel() == 2) {
									    			approverName2 = pah.getApproverUserId().getFullName();
									    		}
									    		else {
									    			
									    		}
						            	}
						            	for(SubmissionApproverHierarchy sah : sahList) {
							            	if(sah.getSubmissionStatus() == Submission_Status.APPROVED && approverName2 != "") {
								            	mtr.setApproved_By(approverName1);
							            	}
							            	mtr.setNoOfHours(sah.getSubmissionId().getTotalHours());
							            	if(sah.getSubmissionStatus() == Submission_Status.APPROVED){
							            		mtr.setApprovedDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),sah.getUpdatedDate()));
							            	}
							            	mtr.setSubmittedOn(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), sah.getUpdatedDate()));
						            	 }	
						            	mtr.setManager1(approverName1);
						            	mtr.setManager2(approverName2);
						            	mtr.setApprovalMechanism(pm.getProjects().getProjectApprovalType());
						            	mtr.setProjectFrequency(pm.getProjects().getTimesheetFrequency());
						            	if(pm.getEmployee() != null) {
						            		mtr.setEmpId(pm.getEmployee().getEmployeeId());
						            	}
						            	missingTimesheetResultRepotList.add(mtr);
						            	finalTFMap.put(key, value);
						            	
									}
								}
							}
						}
					}
				}
			}
		} 
			
			
	 catch (Exception e) {
			e.printStackTrace();
		}
		return missingTimesheetResultRepotList;
		
	}
	
	@RequestMapping(value="/getTimesheetsPendingExportListByOrgLocationId")
	public void getTimesheetsPendingExportListByOrgLocationId(MissingTimesheetRepotDTO missingTimesheetRepotDTO, HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="exportType", required= false) String exportType) {
		try {
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	List<MissingTimesheetResultRepotDTO> missingTimesheetResultRepotList = getCommonListForPendingApprovalTimesheetReport(missingTimesheetRepotDTO, orgLocations, user);
					File file = null;
					if(exportType.equalsIgnoreCase("pdf")) {
						file = dynamicReport.timesheetsPendingPdfExport(missingTimesheetResultRepotList, orgLocations);
					}else {
						file = dynamicReport.timesheetsPendingExcelExport(missingTimesheetResultRepotList, orgLocations);
					}
					String mimeType = URLConnection.guessContentTypeFromName(file.getName());
					if (mimeType == null) {
						mimeType = "application/octet-stream";
					}
					response.setContentType(mimeType);
					StringBuilder fileNameSB = new StringBuilder();
					response.setHeader("Content-Disposition", fileNameSB.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
					response.setContentLength((int) file.length());
					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
					FileCopyUtils.copy(inputStream, response.getOutputStream());
					inputStream.close();
					response.getOutputStream().close();
					response.getOutputStream().flush();	
					if(file!= null){
						file.delete();
					}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	
	@RequestMapping(value ="/getpendingForExpenseReportList", method = RequestMethod.POST)
	public  @ResponseBody List<PendingForExpenseApprovalReportDTO> getpendingForApprovalExpenseReportList(MissingTimesheetRepotDTO missingTimesheetRepotDTO,HttpServletRequest request) throws Exception {
		Long locationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		OrgLocations orgLocations = orgLocationService.getOrgLocationById(locationId);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");

        Date[] searchDates = null;
        if(StringUtils.isNotBlank(missingTimesheetRepotDTO.getDateSelection())){
        	if(!missingTimesheetRepotDTO.getDateSelection().equalsIgnoreCase("custom")) {
    			searchDates = reportsUtil.getJobFilterDate(missingTimesheetRepotDTO.getDateSelection(),orgLocations);
    		}else {
    			Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
    			DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    			DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
    			DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd'T'23:59:59");
    			Date fromDate =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), missingTimesheetRepotDTO.getFromDate());
       	        Date toDate =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), missingTimesheetRepotDTO.getToDate());
    			String customStartTimeStr = TZ_DF_CSD.format(fromDate);
    			String customEndTimeStr = TZ_DF_CED.format(toDate);
    			String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customStartTimeStr);
    			String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customEndTimeStr);
    			fromDate = TZ_DF_TS.parse(startDateString);
    			toDate = TZ_DF_TS.parse(endDateString);
    			
       	    searchDates = new Date[2];
       	    searchDates[0] = fromDate;
       	    searchDates[1] = toDate;
    		}
        }
		
		List<Projects> projectList = projectsService.getProjectByOrgLocationsId(orgLocations.getId());
		ArrayList<Long> projectIds = new ArrayList<>();
		String projectIdString = "";
		if(StringUtils.isNotBlank(missingTimesheetRepotDTO.getProjectIds())) {
			String[] projectIdsStr = missingTimesheetRepotDTO.getProjectIds().split(",");
			for(int i=0; i<projectIdsStr.length; i++) {
				projectIds.add(Long.parseLong(projectIdsStr[i]));
				projectIdString += (projectIdString.isEmpty() ? "" : ",") + projectIdsStr[i];
			}
		}
		else {
			if(projectList != null && !projectList.isEmpty()) {
				for(Projects pr :projectList) {
					projectIds.add(pr.getId());
					projectIdString += (projectIdString.isEmpty() ? "" : ",") + pr.getId();
				}
			}
		}
		List<PendingForExpenseApprovalReportDTO> pendingForExpenseApprovalReportDTO = new ArrayList<>();
		Date frequencyStartDate = searchDates[0];
	        Date frequencyEndDate = searchDates[1];
		List <PendingForExpenseApprovalReportDTO> exp = expensesService.getPendingForApprovalExpenseReportByProjectIds(projectIdString, frequencyStartDate, frequencyEndDate,orgLocations.getDateFormat());
		return exp;

	}
	
	
	@ResponseBody
	@RequestMapping(value = "/reportExport")
	public File epensePendingapprovalreportForExport(MissingTimesheetRepotDTO missingTimesheetRepotDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");

        Date[] searchDates = null;
        if(StringUtils.isNotBlank(missingTimesheetRepotDTO.getDateSelection())){
        	if(!missingTimesheetRepotDTO.getDateSelection().equalsIgnoreCase("custom")) {
    			searchDates = reportsUtil.getJobFilterDate(missingTimesheetRepotDTO.getDateSelection(),orgLocations);
    		}else {
    			Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
    			DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    			DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
    			DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd'T'23:59:59");
    			Date fromDate =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), missingTimesheetRepotDTO.getFromDate());
       	        Date toDate =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), missingTimesheetRepotDTO.getToDate());
    			String customStartTimeStr = TZ_DF_CSD.format(fromDate);
    			String customEndTimeStr = TZ_DF_CED.format(toDate);
    			String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customStartTimeStr);
    			String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customEndTimeStr);
    			fromDate = TZ_DF_TS.parse(startDateString);
    			toDate = TZ_DF_TS.parse(endDateString);
    			
       	    searchDates = new Date[2];
       	    searchDates[0] = fromDate;
       	    searchDates[1] = toDate;
    		}
        }
		
		List<Projects> projectList = projectsService.getProjectByOrgLocationsId(orgLocations.getId());
		ArrayList<Long> projectIds = new ArrayList<>();
		String projectIdString = "";
		if(StringUtils.isNotBlank(missingTimesheetRepotDTO.getProjectIds())) {
			String[] projectIdsStr = missingTimesheetRepotDTO.getProjectIds().split(",");
			for(int i=0; i<projectIdsStr.length; i++) {
				projectIds.add(Long.parseLong(projectIdsStr[i]));
				projectIdString += (projectIdString.isEmpty() ? "" : ",") + projectIdsStr[i];
			}
		}
		else {
			if(projectList != null && !projectList.isEmpty()) {
				for(Projects pr :projectList) {
					projectIds.add(pr.getId());
					projectIdString += (projectIdString.isEmpty() ? "" : ",") + pr.getId();
				}
			}
		}
		List<PendingForExpenseApprovalReportDTO> pendingForExpenseApprovalReportDTO = new ArrayList<>();
		Date frequencyStartDate = searchDates[0];
	        Date frequencyEndDate = searchDates[1];
		List <PendingForExpenseApprovalReportDTO> exp = expensesService.getPendingForApprovalExpenseReportByProjectIds(projectIdString, frequencyStartDate, frequencyEndDate,orgLocations.getDateFormat());
		File file = null;
		if (exp != null ) {

			if (missingTimesheetRepotDTO.getType().equalsIgnoreCase("pdf")) {
				file = pdfUtil.expensependingApprovalReportListOfPdf(exp, user, orgLocations);
			} else {
				file = pdfUtil.expensependingApprovalReportListOfExcel(exp, user, orgLocations);
			}
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
		if (file != null) {
			file.delete();
		}
		return file;

	}	

}
 