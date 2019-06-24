package com.bis.sourcelead.timesheet.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.TimeSheetDayEntries;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.timesheet.dao.TimeSheetDayEntriesDao;
import com.bis.sourcelead.timesheet.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.timesheet.service.TimeSheetDayEntriesService;
import com.bis.sourcelead.timesheet.service.TimesheetSubmissionsService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.TimesheetFrequencyGeneratorUtil;

@Service
public class TimeSheetDayEntriesServiceImpl implements TimeSheetDayEntriesService {

	@Autowired
	private TimeSheetDayEntriesDao timeSheetDayEntriesDao;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private TimesheetFrequencyGeneratorUtil timesheetFrequencyGeneratorUtil;
	
	
	@Autowired
	private TimesheetSubmissionsService timesheetSubmissionsService;
	
	
	@Autowired
	private ProjectApproverHierarchyService projectApproverHierarchyService;
	
	@Autowired
	private SubmissionApproverHierarchyService submissionApproverHierarchyService;
	
	@Autowired
	private ProjectMembersService projectMembersService;
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private ProjectTasksService projectTasksService;
	
	@Autowired
	private UserService userService;
	
	@Override
	public TimeSheetDayEntries saveTimesheetDayEntries(TimeSheetDayEntries dayentries) throws Exception {
		return timeSheetDayEntriesDao.saveTimesheetDayEntries(dayentries);
	}

	@Override
	public List<Long> getAllTimeSheetDayEntryIdsBySubmissionId(Long submissionId) throws Exception {
		return timeSheetDayEntriesDao.getAllTimeSheetDayEntryIdsBySubmissionId(submissionId);
	}

	@Override
	public Boolean deleteTimeSheetDayEntries(Long id) throws Exception {
		return timeSheetDayEntriesDao.deleteTimeSheetDayEntries(id);
	}

	@Override
	public TimeSheetDayEntries getTimesheetDayEntriesById(Long id) throws Exception {
		return timeSheetDayEntriesDao.getTimesheetDayEntriesById(id);
	}

	@Override
	public TimeSheetDayEntries getTimesheetDayEntriesBySubmissionIdTaskIdAndTimesheetDate(Long submissionId,
			Long taskId, Date timesheetDate,String hourlyType) throws Exception {
		return timeSheetDayEntriesDao.getTimesheetDayEntriesBySubmissionIdTaskIdAndTimesheetDate(submissionId, taskId, timesheetDate,hourlyType);
	}

	@Override
	public List<TimeSheetDayEntries> getAllTimeSheetDayEntriesBySubmissionId(Long submissionId) throws Exception {
		return timeSheetDayEntriesDao.getAllTimeSheetDayEntriesBySubmissionId(submissionId);
	}

	@Override
	public List<TimeSheetDayEntries> getAllTimeSheetDayEntriesBySubmissionIdWithStartDateAndEndDate(Long submissionId,
			Date startDate, Date endDate) throws Exception {
		return timeSheetDayEntriesDao.getAllTimeSheetDayEntriesBySubmissionIdWithStartDateAndEndDate(submissionId, startDate, endDate);
	}

	@Override
	public ResponseList<TimeSheetDayEntries> getTimesheetDayEntriesByTaskUUIDAndUserId(String taskUUID, Long userId,
			Long locationId, int start, int length, String searchValue) throws Exception {
		return timeSheetDayEntriesDao.getTimesheetDayEntriesByTaskUUIDAndUserId(taskUUID, userId, locationId, start, length, searchValue);
	}

	@Override
	public Float getTotalHoursByProjectIdMasteCandidateIdandTimesheetDate(Long projectId, Long masterCandidateId,Date timesheetDate) {
		return timeSheetDayEntriesDao.getTotalHoursByProjectIdMasteCandidateIdandTimesheetDate(projectId,masterCandidateId,timesheetDate);
	}
	
	@Override
	public TimeSheetDayEntries getTimesheetDayEntryByProjectIdMasteCandidateIdandTimesheetDate(Long projectId,Long masterCandidateId, Date timesheetDate,Long projectTaskId) {
		return timeSheetDayEntriesDao.getTimesheetDayEntryByProjectIdMasteCandidateIdandTimesheetDate(projectId,masterCandidateId,timesheetDate,projectTaskId);
	}

	@Override
	public List<TimeSheetDayEntries> getAllTimeSheetDayEntriesByProjectIdAndTimesheetDate(Long projectId,Date timesheetDate,Long projectMemberId) throws Exception {
		return timeSheetDayEntriesDao.getAllTimeSheetDayEntriesByProjectIdAndTimesheetDate(projectId, timesheetDate,projectMemberId);
	}

	@Override
	public List<TimeSheetDayEntries> getAllTimeSheetDayEntriesByProjectIdAndTimesheetDateTest(Long projectId,Date startDate, Date endDate, Long projectMembers) throws Exception {
		return timeSheetDayEntriesDao.getAllTimeSheetDayEntriesByProjectIdAndTimesheetDateTest(projectId, startDate,endDate,projectMembers);
	}

	@Override
	public Double getOverTimeHoursByMasterCandidateIdAndOrgLocationWithStartEndDates(Long masterCandidateId,
			Date startDate, Date endDate, OrgLocations orgLocations) throws Exception {
		return timeSheetDayEntriesDao.getOverTimeHoursByMasterCandidateIdAndOrgLocationWithStartEndDates(masterCandidateId, startDate, endDate, orgLocations);
	}

	@Override
	public Boolean isMissingTimesheetByMasterCandidateIdAndOrgLocationWithStartEndDates(Long masterCandidateId,
			Date startDate, Date endDate, OrgLocations orgLocations) throws Exception {
		return timeSheetDayEntriesDao.isMissingTimesheetByMasterCandidateIdAndOrgLocationWithStartEndDates(masterCandidateId, startDate, endDate, orgLocations);
	}

	
	
	@Override
	public TimeSheetDayEntries addTimesheetDayEntry(Long projectMemberId, Date subFrequencyStartDate, Date subFrequencyEndDate,
			Date frequencyStartDate, Date frequencyEndDate, Long loginUserId, Date timesheetDate, String hours,
			String comments, String taskUUID, Long taskId, String dateformat,String hourlyType) throws Exception {
		TimeSheetDayEntries te = null;
		ProjectMembers projectMember = projectMembersService.getProjectMemberById(projectMemberId);
		User loginUser = userService.getUserById(loginUserId);
		
		if (projectMember != null && projectMember.getProjects() != null
				&& projectMember.getMasterCandidateId() != null) {
			TimesheetSubmissions timesheetSubmissions = timesheetSubmissionsService
					.getAllCandidateTimesheetSubmissionsByProjectIdAndMasterCandidateId(subFrequencyStartDate, subFrequencyEndDate,
							projectMember.getProjects().getId(), projectMember.getMasterCandidateId().getId());
			if (timesheetSubmissions == null) {
				timesheetSubmissions = new TimesheetSubmissions();
				timesheetSubmissions.setStartDate(subFrequencyStartDate);
				timesheetSubmissions.setEndDate(subFrequencyEndDate);
				timesheetSubmissions.setProjectMembers(projectMember);
				timesheetSubmissions.setProjects(projectMember.getProjects());
				timesheetSubmissions.setMasterCandidateId(projectMember.getMasterCandidateId());
				timesheetSubmissions.setStatus(Constants.RECORD_ACTIVE + "");
				timesheetSubmissions.setCreatedDate(CommonUtil.currentDate());
				timesheetSubmissions.setCreatedBy(loginUser.getUserCode());
				if(frequencyStartDate != null && frequencyEndDate != null) {
					String comment = timesheetSubmissionsService.getWeekCommentByCurrentFrequency(frequencyStartDate, frequencyEndDate, timesheetSubmissions.getProjects().getId(), timesheetSubmissions.getMasterCandidateId().getId());
					if(StringUtils.isNotEmpty(comment));
					timesheetSubmissions.setWeekcomments(comment);
				}
			}
			timesheetSubmissions.setUpdatedDate(CommonUtil.currentDate());
			timesheetSubmissions.setUpdatedBy(loginUser.getUserCode());
			ArrayList<TimesheetSubmissions> timesheetSubmissionsList = new ArrayList<TimesheetSubmissions>();
			List<ProjectApproverHierarchy> hierarchyList = projectApproverHierarchyService
					.getApproverUsersByProjectMemberMasterCandidateIdAndProjectId(
							projectMember.getMasterCandidateId().getId(), projectMember.getProjects().getId());
			HashSet<SubmissionApproverHierarchy> saHierarchyList = new HashSet<SubmissionApproverHierarchy>();
			outer: {
				if (hierarchyList != null && !hierarchyList.isEmpty()) {
					for (ProjectApproverHierarchy hierarchy : hierarchyList) {
						SubmissionApproverHierarchy saHierarchy = null;
						if (timesheetSubmissions != null && timesheetSubmissions.getId() != null) {
							saHierarchy = submissionApproverHierarchyService.getSubmissionApproverHierarchyForApprover(
									timesheetSubmissions.getId(), hierarchy.getApproverUserId().getId());
							if (saHierarchy.getSubmissionApprovalStatus() == Submission_ApprovalStatus.APPROVED) {
								break outer;
							}
						}
						if (saHierarchy == null) {
							saHierarchy = new SubmissionApproverHierarchy();
							saHierarchy.setHeirarchyId(hierarchy);
							saHierarchy.setSubmissionId(timesheetSubmissions);
							//Additional Fields are added for de-normalization
							saHierarchy.setProjectId(timesheetSubmissions.getProjects());
							saHierarchy.setMasterCandidateId(timesheetSubmissions.getMasterCandidateId().getId());
							saHierarchy.setProjectClientId(hierarchy.getProjectClientsId());
							saHierarchy.setOrgLocationId(hierarchy.getProjectClientsId().getOrgLocationsId().getId());
							saHierarchy.setOrgId(hierarchy.getProjectClientsId().getOrgLocationsId().getOrg().getId());
							ProjectMembers pm = projectMembersService.getProjectMembersByMasterCandidateIdAndProjectClientId(timesheetSubmissions.getMasterCandidateId().getId(), hierarchy.getProjectClientsId().getId());
							if(pm != null) {
								saHierarchy.setProjectMemberId(pm);
								saHierarchy.setCandidateId(pm.getCandidateId());
								User consultantUser = candidateService.getConsultantUserByCandidateIdOrMasterCandidateId(pm.getProjectMemberCandidteId().getId(),pm.getProjectMemberCandidteId().getMasterCandidateId().getId());
								saHierarchy.setConsultantUser(consultantUser);
							}
							if (frequencyStartDate != null)
								saHierarchy.setFrequencyStartDate(frequencyStartDate);
							if (frequencyEndDate != null)
								saHierarchy.setFrequencyEndDate(frequencyEndDate);
							saHierarchy.setSubmissionStatus(Submission_Status.DRAFT);
							saHierarchy.setSubmissionApprovalStatus(Submission_ApprovalStatus.NEW);
							saHierarchy.setCreatedDate(timesheetSubmissions.getCreatedDate());
							saHierarchy.setCreatedBy(timesheetSubmissions.getCreatedBy());
							saHierarchy.setUpdatedDate(timesheetSubmissions.getUpdatedDate());
							saHierarchy.setUpdatedBy(timesheetSubmissions.getUpdatedBy());
							saHierarchy.setStatus(Constants.RECORD_ACTIVE + "");
						}
						saHierarchyList.add(saHierarchy);
					}
					timesheetSubmissions.setApproverHierarchyList(saHierarchyList);
				}
				timesheetSubmissionsList.add(timesheetSubmissions);
				timesheetSubmissionsService.saveAllTimesheetSubmissions(timesheetSubmissionsList);

				HashMap<TimeSheetDayEntries,HashMap<String, Long>> resultMap = addTimesheetDateDayEntries(subFrequencyStartDate, subFrequencyEndDate,
						timesheetDate, dateformat, taskUUID,taskId, hours, comments, timesheetSubmissions, loginUser,hourlyType);
			    te = resultMap.keySet().iterator().next();
				HashMap<String, Long> totalMap = resultMap.values().iterator().next();
				Long total = totalMap.get("TOTAL");
				Long totalNonBillableHours = totalMap.get("BILLABLE");
				Long totalBillableHours = totalMap.get("NONBILLABLE");
				if (total != null) {
					/**
					 * Total Hours Calculation
					 */
					int actualHours = (int) (total / 60);
					int actualMinutes = (int) (total % 60);
					String actualMinutesStr = "" + actualMinutes;
					String startWithZero = "";
					if (StringUtils.isNotEmpty(actualMinutesStr) && actualMinutesStr.length() == 1)
						startWithZero = "0";
					String actualTimeString = actualHours + "." + startWithZero + actualMinutes;
					timesheetSubmissions.setTotalHours(actualTimeString);

					/**
					 * Billable Hours Calculation
					 */
					int actualBillableHours = (int) (totalBillableHours / 60);
					int actualBillableMinutes = (int) (totalBillableHours % 60);
					String actualBillableMinutesStr = "" + actualBillableMinutes;
					String billableStartWithZero = "";
					if (StringUtils.isNotEmpty(actualBillableMinutesStr) && actualBillableMinutesStr.length() == 1)
						billableStartWithZero = "0";
					String actualBillableTimeString = actualBillableHours + "." + billableStartWithZero
							+ actualBillableMinutes;
					timesheetSubmissions.setBillableHours(actualBillableTimeString);

					/**
					 * Non-Billable Hours Calculation
					 */
					int actualNonBillableHours = (int) (totalNonBillableHours / 60);
					int actualNonBillableMinutes = (int) (totalNonBillableHours % 60);
					String actualNonBillableMinutesStr = "" + actualNonBillableMinutes;
					String nonBillableStartWithZero = "";
					if (StringUtils.isNotEmpty(actualNonBillableMinutesStr)
							&& actualNonBillableMinutesStr.length() == 1)
						nonBillableStartWithZero = "0";
					String actualNonBillableTimeString = actualNonBillableHours + "." + nonBillableStartWithZero
							+ actualNonBillableMinutes;
					timesheetSubmissions.setNonBillableHours(actualNonBillableTimeString);
					timesheetSubmissionsService.updateTimesheetSubmissions(timesheetSubmissions);
				}
			}
		}
		return te;
	}
	
	
	@SuppressWarnings("unused")
	private HashMap<TimeSheetDayEntries,HashMap<String, Long>> addTimesheetDateDayEntries(Date actualStartDate, Date actualEndDate,
			Date timesheetDate, String dateFormat, String taskUUID,Long taskId, String hours, String comments,
			TimesheetSubmissions timesheetSubmissions, User loginUser,String hourlyType) throws Exception {
		Long total = 0l;
		Long totalBillableHours = 0l;
		Long totalNonBillableHours = 0l;
		HashMap<String, Long> totalMap = new HashMap<>();
		HashMap<TimeSheetDayEntries, HashMap<String, Long>> resultMap = new HashMap<>();
		/*
		 * while(actualStartDate.before(actualEndDate) ||
		 * actualStartDate.equals(actualEndDate)){
		 */
		ProjectTasks projectTasks =  null;
		if(taskId != null) {
			projectTasks = projectTasksService.getTasksId(taskId.toString());
		}else {
			projectTasks = projectTasksService.getProjectTasksByUUID(taskUUID);
		}
		TimeSheetDayEntries timesheetDayEntries = 
				getTimesheetDayEntriesBySubmissionIdTaskIdAndTimesheetDate(timesheetSubmissions.getId(),
						projectTasks.getId(), timesheetDate,hourlyType);
		if (timesheetDayEntries == null) {
			timesheetDayEntries = new TimeSheetDayEntries();
			timesheetDayEntries.setBillableHours("00:00");
			timesheetDayEntries.setNonBillableHours("00:00");
			timesheetDayEntries.setTotalHours("00:00");
			timesheetDayEntries.setIsManuallyAdded(Constants.NO);
		}
		String taskType = projectTasks.getBillableType();
		timesheetDayEntries.setProjectTask(projectTasks);
		total = getTotalHoursFormDayHours(hours, total);
		timesheetDayEntries.setIsManuallyAdded(Constants.YES);
		if (taskType.equalsIgnoreCase("Billable")) {
			timesheetDayEntries.setComments(comments);
			timesheetDayEntries.setBillableHours(hours);
			timesheetDayEntries.setNonBillableHours("00:00");
			timesheetDayEntries.setTotalHours(hours);
			totalBillableHours = getTotalHoursFormDayHours(hours, totalBillableHours);
		} else {
			timesheetDayEntries.setComments(comments);
			timesheetDayEntries.setBillableHours("00:00");
			timesheetDayEntries.setNonBillableHours(hours);
			timesheetDayEntries.setTotalHours(hours);
			totalNonBillableHours = getTotalHoursFormDayHours(hours, totalNonBillableHours);
		}
		if(StringUtils.isEmpty(hourlyType)) {
			timesheetDayEntries.setHourlyType("Regular");
		}else {
			timesheetDayEntries.setHourlyType(hourlyType);
		}
		timesheetDayEntries.setTaskType(taskType);
		timesheetDayEntries.setTimesheetSubmissions(timesheetSubmissions);
		timesheetDayEntries.setTimesheetDate(timesheetDate);
		timesheetDayEntries.setCreatedDate(CommonUtil.currentDate());
		timesheetDayEntries.setCreatedBy(loginUser.getCreatedBy());
		timesheetDayEntries.setStatus(1);
		timesheetDayEntries.setHourlyType(hourlyType);
		timesheetDayEntries.setUpdatedBy(loginUser.getCreatedBy());
		timesheetDayEntries.setUpdatedDate(CommonUtil.currentDate());
		timesheetDayEntries = saveTimesheetDayEntries(timesheetDayEntries);
		totalMap.put(commonUtil.dateStrFromOrgDateFormatter(dateFormat, actualStartDate), timesheetDayEntries.getId());
		/*
		 * actualStartDate = CalendarUtil.getNextDate(actualStartDate); }
		 */
		totalMap.put("TOTAL", total);
		totalMap.put("BILLABLE", totalBillableHours);
		totalMap.put("NONBILLABLE", totalNonBillableHours);
		resultMap.put(timesheetDayEntries, totalMap);
		return resultMap;
	}
	
	private Long getTotalHoursFormDayHours(String dayHours, Long total) {
		String hours = "00";
		String min = "00";
		if (StringUtils.isNotEmpty(dayHours)) {
			String[] parts = dayHours.split(":");
			hours = parts[0];
			min = parts[1];
		}
		Long localHours = Long.parseLong(hours) * 60;
		Long localHoursMinute = Long.parseLong(min);
		Long finalmin = localHours + localHoursMinute;
		total = total + finalmin;
		return total;
	}
	
	/**
	 * @author rajasekhar
	 * 
	 * Getting Time sheet frequency and its sub frequency
	 * 
	 * @return Map<String,String>
	 */
	@Override
	public Map<String,String> getTimesheetFrequency(Date timesheetDate,OrgLocations orgLocation, Projects projects)
	{
		Map<String,String> map = new  HashMap<String, String>();
		try {
			Date frequencyStartDate = null;
			Date frequencyEndDate = null;
			Date subFrequencyStartDate = null;
			Date subFrequencyEndDate = null;
			
			//Date timesheetDate = commonUtil.orgLocationCommonDate(clockOutDate, orgLocation);
			if (projects != null && "Monthly".equalsIgnoreCase(projects.getInvoiceFrequency())) {
				frequencyStartDate = CalendarUtil.getMonthStartDateByGivenDate(timesheetDate, Constants.CURRENT_MONTH).get("STARTDATE");
				frequencyEndDate = CalendarUtil.getMonthEndDateByGivenDate(timesheetDate, Constants.CURRENT_MONTH).get("ENDDATE");
			} else if (projects != null && "Semi-Monthly".equalsIgnoreCase(projects.getInvoiceFrequency())) {
				Date empMonth15thDate = CalendarUtil.getMonth15thDateByGivenDate(timesheetDate, Constants.CURRENT_MONTH).get("STARTDATE");
				if (timesheetDate.after(empMonth15thDate)) {
					frequencyStartDate = CalendarUtil.getMonth16thDateByGivenDate(timesheetDate, Constants.CURRENT_MONTH).get("STARTDATE");
					frequencyEndDate = CalendarUtil.getMonthEndDateByGivenDate(frequencyStartDate, 0).get("ENDDATE");
				} else {
					frequencyStartDate = CalendarUtil.getMonthStartDateByGivenDate(timesheetDate, Constants.CURRENT_MONTH).get("STARTDATE");
					frequencyEndDate = CalendarUtil.getMonth15thDateByGivenDate(frequencyStartDate, 0).get("STARTDATE");
				}
			} else if (projects != null && "Bi-Weekly".equalsIgnoreCase(projects.getInvoiceFrequency())) {
				Date biWeeklyStartMondayDate = timesheetFrequencyGeneratorUtil.getBiweeklyStartMonDayDateByUsingProjectStartDateAndCurrentDateForTsImports(projects,
								timesheetDate);
				Calendar cal = Calendar.getInstance(TimeZone.getDefault());
				cal.setLenient(false);
				cal.setTime(biWeeklyStartMondayDate);
				Calendar startMonday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON",orgLocation.getDateFormat());
				frequencyStartDate = startMonday.getTime();
				Calendar endSunday = CalendarUtil.getDateIncrementByincrement(cal, 13);
				frequencyEndDate = endSunday.getTime();
			} else {
				Calendar cal = Calendar.getInstance(TimeZone.getDefault());
				cal.setLenient(false);
				cal.setTime(timesheetDate);
				Calendar startMonday = null;
				Calendar endSunday = null;
				SimpleDateFormat sf = new SimpleDateFormat("E");
				if (sf.format(timesheetDate).equalsIgnoreCase("SUN")) {
					startMonday = CalendarUtil.getPreviousWeekDayByCalendar(cal, "MON", orgLocation.getDateFormat());
					endSunday = CalendarUtil.getPreviousWeekDayByCalendar(cal, "SUN", orgLocation.getDateFormat());
				} else {
					startMonday = CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(cal, "MON",orgLocation.getDateFormat());
					endSunday = CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(cal, "SUN",orgLocation.getDateFormat());
				}
				frequencyStartDate = startMonday.getTime();
				frequencyEndDate = endSunday.getTime();

			}
			String freqSDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), frequencyStartDate);
			String freqEDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), frequencyEndDate);
			map.put("timesheetFreq", freqSDate+"To"+freqEDate);
			Map<String, String> timeSubFrequencyMap = timesheetFrequencyGeneratorUtil.getTabFrequencyListFromProjectInvoiceFrequency(
							commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), frequencyStartDate),
							commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), frequencyEndDate),orgLocation.getDateFormat());

			for (Map.Entry<String, String> entry : timeSubFrequencyMap.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				String date = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), timesheetDate);
				if (CalendarUtil.isGivenDateBetweenDateRange(key, value, date, orgLocation.getDateFormat())) {
					subFrequencyStartDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),key.replaceAll("/", "-"));
					subFrequencyEndDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),value.replaceAll("/", "-"));
				}
			}
			String subFreqSDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), subFrequencyStartDate);
			String subFreqEDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), subFrequencyEndDate);
			map.put("timesheetSubFreq", subFreqSDate+"To"+subFreqEDate);
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

}
