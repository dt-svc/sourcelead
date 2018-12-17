package com.bis.sourcelead.rest.reportsRestController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
import com.bis.sourcelead.reportsDTO.MissingTimesheetRepotDTO;
import com.bis.sourcelead.reportsDTO.MissingTimesheetResultRepotDTO;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.service.TimeSheetDayEntriesService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ReportsUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.util.TimesheetFrequencyGeneratorUtil;
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
								
					             if(submissionApproverHierarchyService.getPendingForApprovalTimesheetReportByProjectMemberId(frequencyStartDate, frequencyEndDate, pm.getId()) && (flag) && frequencyEndDate.before(currentCal.getTime())){
					            	String unfilledFrequency =  key.replaceAll("/", "-")+" To "+value.replaceAll("/", "-");
					            	MissingTimesheetResultRepotDTO mtr = new MissingTimesheetResultRepotDTO();
					            	mtr.setProjectName(pm.getProjects().getProjectName());
					            	mtr.setEmployeName(pm.getProjectMemberCandidteId().getFullName());
					            	mtr.setEmployeType(pm.getProjectMemberCandidteId().getDesignation());
					            	mtr.setFrequency(unfilledFrequency);
					            	mtr.setDueDays(CalendarUtil.getNumberOfDays(frequencyEndDate,currentCal.getTime()));
					                SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyForEmployeeByProjectMemberId(frequencyStartDate,frequencyEndDate ,pm.getId());
					                mtr.setSubmittedOn(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), sah.getUpdatedDate()));
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

}
