package com.bis.sourcelead.timesheet.rest;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.HolidayDAO;
import com.bis.sourcelead.dto.EmployeeLeaveDTO;
import com.bis.sourcelead.dto.EmployeeTimeSheetSubmissionStatusDTO;
import com.bis.sourcelead.dto.EmployeeTimesheetEntryDetailsDTO;
import com.bis.sourcelead.dto.EmployeeTimesheetFileInfoDTO;
import com.bis.sourcelead.dto.EmployeeTimesheetSubmissionDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.ProjectMembersDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.TimeSheetSubmissionDTO;
import com.bis.sourcelead.dto.TimeSheetSubmissionDayEntryDTO;
import com.bis.sourcelead.dto.TimesheetEntriesDTO;
import com.bis.sourcelead.dto.TimesheetEntryDetailsDTO;
import com.bis.sourcelead.dto.TimesheetExpenseEntryDetailsUtilDTO;
import com.bis.sourcelead.dto.TimesheetProjectTaskDTO;
import com.bis.sourcelead.dto.TimesheetTaskDTO;
import com.bis.sourcelead.dto.UpdateTimesheetEntryDetailsDTO;
import com.bis.sourcelead.dto.WeekDaysDTO;
import com.bis.sourcelead.dto.helper.ProjectMemberDTOConverter;
import com.bis.sourcelead.entity.AppliedLeaveDate;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Holiday;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectMembersHistory;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Shift;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.TimeSheetDayEntries;
import com.bis.sourcelead.entity.TimesheetActivities;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.TimesheetSubmissionsDocuments;
import com.bis.sourcelead.entity.TimesheetSubmissionsDocumentsHistory;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserDroppedHolidays;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.service.AppliedLeaveDateService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.HolidayService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.ShiftService;
import com.bis.sourcelead.service.UserDroppedHolidaysService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.timesheet.dao.TimeSheetDayEntriesDao;
import com.bis.sourcelead.timesheet.service.ExportTimesheetEntries;
import com.bis.sourcelead.timesheet.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.timesheet.service.TimeSheetDayEntriesService;
import com.bis.sourcelead.timesheet.service.TimesheetActivityService;
import com.bis.sourcelead.timesheet.service.TimesheetSubmissionsDocumentsService;
import com.bis.sourcelead.timesheet.service.TimesheetSubmissionsService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.util.TimeSheetPdfUtil;
import com.bis.sourcelead.util.TimesheetApprovalFrequencyGeneratorUtil;
import com.bis.sourcelead.util.TimesheetEmailUtil;
import com.bis.sourcelead.util.TimesheetFrequencyGeneratorUtil;

@RestController
public class TimesheetRestController {

	private static final Logger logger = LoggerFactory.getLogger(TimesheetRestController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;

	@Autowired
	private ProjectsService projectsService;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private TimesheetApprovalFrequencyGeneratorUtil timesheetApprovalFrequencyGeneratorUtil;

	@Autowired
	private TimeSheetPdfUtil timeSheetPdfUtil;

	@Autowired
	private ProjectTasksService projectTasksService;

	@Autowired
	private TimesheetSubmissionsService timesheetSubmissionsService;

	@Autowired
	private TimeSheetDayEntriesDao timesheetDayEntriesDao;

	@Autowired
	private SubmissionApproverHierarchyService submissionApproverHierarchyService;

	@Autowired
	private TimesheetSubmissionsDocumentsService timesheetSubmissionsDocumentsService;

	@Autowired
	private ProjectApproverHierarchyService projectApproverHierarchyService;

	@Autowired
	private ProjectMembersService projectMembersService;
	
	@Autowired
	private UserDroppedHolidaysService userDroppedHolidaysService;

	@Autowired
	private UserService userService;

	@Autowired
	private TimesheetFrequencyGeneratorUtil timesheetFrequencyGeneratorUtil;

	@Autowired
	private ProjectMembersHistoryService projectMembersHistoryService;

	@Autowired
	private OrgLocationService orgLocationService;

	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private HolidayService holidayService;

	@Autowired
	private ExportTimesheetEntries exportTimesheetEntries;

	@Value("${static.path}")
	private String staticPath;

	@Autowired
	private TimesheetEmailUtil timesheetEmailUtil;

	@Autowired
	private OrgRelationsService orgRelationsService;
	
	@Autowired
	private TimesheetActivityService timesheetActivityService;
	
	@Autowired
	private TimeSheetDayEntriesService timeSheetDayEntriesService;
	
	@Autowired
	private AppliedLeaveDateService appliedLeaveDateService;
	
	
	@Autowired
	private HolidayDAO holidayDAO;
	
	@Autowired
	private EmployeesService employeesService;
	
	@Autowired
	ShiftService shiftService;
	

	/***
	 * @purpose : Getting ProjectList For Login User (Step - 1)
	 * @param eedDTO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/rest/getTimesheetEntryDetails")
	public TimesheetEntryDetailsDTO getTimesheetEntryDetailsMobile(
			@RequestBody TimesheetExpenseEntryDetailsUtilDTO eedDTO, HttpServletRequest request) throws Exception {
		TimesheetEntryDetailsDTO tedDTO = getTimesheetEntryDetails(eedDTO, request);
		TimeSheetSubmissionDTO tsDTO = tedDTO.getTimesheetSubmission();
		if (tsDTO != null) {
			Map<String, List<TimesheetEntriesDTO>> entriesMap = getEntriesMapForDisplayMobile(tsDTO, request);
			tsDTO.setEntriesMap(entriesMap);
			tedDTO = getWeekDaysMapForDisplayMobile(tedDTO, tsDTO, request);
		}
		return tedDTO;
	}

	
	
	@RequestMapping(value = "/getTimesheetEntryDetails")
	public ModelAndView getTimesheetEntryDetailsWeb(TimesheetExpenseEntryDetailsUtilDTO eedDTO,
			HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		try {
			TimesheetEntryDetailsDTO tedDTO = getTimesheetEntryDetails(eedDTO, request);
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			Long shiftId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "shiftId");
			if (orgLocations == null && StringUtils.isNotEmpty(eedDTO.getLocationCode())) {
				orgLocations = orgLocationService.getOrgLocationsByLocationCode(eedDTO.getLocationCode());
			}else {
				orgLocations = orgLocationService.getOrgLocations(orgLocations.getId());
			}
			Long projectMemberId = eedDTO.getProjectMembersId();
			if (projectMemberId != null) {
				TimeSheetSubmissionDTO tsDTO =tedDTO.getTimesheetSubmission();
				ProjectMembers conPM =  projectMembersService.getConsultantUserProjectMemberByProjectMemberId(projectMemberId);
				if(conPM != null) {
					User consultantUser = conPM.getProjectMemberCandidteId().getConsultantUser();
	                Map<String,List<EmployeeLeaveDTO>> empLeaveMap = getEmpLeaveList(tsDTO.getWeekDays(),tedDTO.getCurrentTimeSubFrequency(), orgLocations,consultantUser,conPM.getProjectMemberCandidteId(),shiftId);
	                tsDTO.setEmpLeaveMap(empLeaveMap);
				}
				
				model.addObject("timesheetEntryDetailsDTO", tedDTO);
				model.addObject("timesheetSubmissionDTO", tedDTO.getTimesheetSubmission());
				LinkedHashMap<String, LinkedList<TimesheetEntriesDTO>> entriesMap = getEntriesMapForDisplayWeb(
						tedDTO.getTimesheetSubmission(), request);
				model.addObject("entriesMap", entriesMap);
			}
			model.setViewName("timesheet/timesheetProjects");
		} catch (Exception e) {
			
			model.addObject("AWKWARD", true);
			model.setViewName("timesheet/timesheetProjects");
			e.printStackTrace();
		}
		return model;

	}

	@RequestMapping(value = "/getEmployeeTimesheetEntryDetails")
	public ModelAndView getEmployeeTimesheetEntryDetailsWeb(TimesheetExpenseEntryDetailsUtilDTO eedDTO,
			HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Candidate candidate = (Candidate) SourceLeadSessionManager.getSessionAttribute(request, "candidate");

		if (loginUser != null && orgLocations != null) {
			candidate = candidateService.getCandidateByConsultantUserId(loginUser.getId(), orgLocations.getId());
		}

		Long shiftId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "shiftId");

		if (orgLocations == null && StringUtils.isNotEmpty(eedDTO.getLocationCode())) {
			orgLocations = orgLocationService.getOrgLocationsByLocationCode(eedDTO.getLocationCode());
		}else {
			orgLocations = orgLocationService.getOrgLocations(orgLocations.getId());
		}
		try {
			EmployeeTimesheetEntryDetailsDTO tedDTO = getEmployeeTimesheetEntryDetails(eedDTO, request);
			if(tedDTO.getProjectMembersList() == null || tedDTO.getProjectMembersList().isEmpty()) {
				model.addObject("NORECORDSFOUND", true);
				model.addObject("employeeTimesheetEntryDetailsDTO", tedDTO);
				model.setViewName("timesheet/timesheetInvoiceList");
			}else {
				List<ProjectMembersDTO> freshProjectMembersList = new LinkedList<>();
				List<ProjectMembersDTO>  allProjectMembersList = new LinkedList<>();
				List<ProjectMembersDTO> submittedProjectMembersList = new LinkedList<>();
				List<ProjectMembersDTO>  attProjectMemberIdsList = new LinkedList<>();
				List<Long>  oldSubmittedPmIdsList = new LinkedList<>();
				Boolean isRejected = false;
				Boolean isSUBMITTED = false;
				Boolean isAPPROVED = false;
				String combinedStatus = "";
				boolean combinedMonEditable =  false;
				boolean combinedTueEditable =  false;
				boolean combinedWedEditable =  false;
				boolean combinedThuEditable =  false;
				boolean combinedFriEditable =  false;
				boolean combinedSatEditable =  false;
				boolean combinedSunEditable =  false;
				boolean submitButtonVisible = false;
				for(ProjectMembersDTO pmDTO :tedDTO.getProjectMembersList()) {
					boolean monEditable = tedDTO.getStatusMap().get(pmDTO.getId()).getMonEditable();
					boolean tueEditable = tedDTO.getStatusMap().get(pmDTO.getId()).getTueEditable();
					boolean wedEditable = tedDTO.getStatusMap().get(pmDTO.getId()).getWedEditable();
					boolean thuEditable = tedDTO.getStatusMap().get(pmDTO.getId()).getThuEditable();
					boolean friEditable = tedDTO.getStatusMap().get(pmDTO.getId()).getFriEditable();
					boolean satEditable = tedDTO.getStatusMap().get(pmDTO.getId()).getSatEditable();
					boolean sunEditable = tedDTO.getStatusMap().get(pmDTO.getId()).getSunEditable();
					if(monEditable && !combinedMonEditable) {
						combinedMonEditable = monEditable;
					}
					if(tueEditable && !combinedTueEditable) {
						combinedTueEditable = tueEditable;
					}
					if(wedEditable && !combinedWedEditable) {
						combinedWedEditable = wedEditable;
					}
					if(thuEditable && !combinedThuEditable) {
						combinedThuEditable = thuEditable;
					}
					if(friEditable && !combinedFriEditable) {
						combinedFriEditable = friEditable;
					}
					if(satEditable && !combinedSatEditable) {
						combinedSatEditable = satEditable;
					}
					if(sunEditable && !combinedSunEditable) {
						combinedSunEditable = sunEditable;
					}
					
					if(!submitButtonVisible) {
						submitButtonVisible = tedDTO.getStatusMap().get(pmDTO.getId()).getSubmitButtonVisible();
					}
					if (tedDTO.getStatusMap().get(pmDTO.getId()) == null || tedDTO.getStatusMap().get(pmDTO.getId()).getTimesheetStatus().equalsIgnoreCase("REJECTED") 
							|| tedDTO.getStatusMap().get(pmDTO.getId()).getTimesheetStatus().equalsIgnoreCase("DRAFT")) {
						isRejected = true;
						combinedStatus = "REJECTED";
					}
					else if (!isRejected 
							&& tedDTO.getStatusMap().get(pmDTO.getId()).getTimesheetStatus().equalsIgnoreCase("SUBMITTED")) {
						isSUBMITTED = true;
						combinedStatus = "SUBMITTED";
						
					}
					else if (!isRejected && !isSUBMITTED
							&& tedDTO.getStatusMap().get(pmDTO.getId()).getTimesheetStatus().equalsIgnoreCase("APPROVED")) {
						isAPPROVED = true;
						combinedStatus = "APPROVED";
					} else if(!isRejected && !isSUBMITTED && !isAPPROVED && tedDTO.getStatusMap().get(pmDTO.getId()).getTimesheetStatus().equalsIgnoreCase("NEW")) {
						combinedStatus = "NEW";
					}
					
					
					if(tedDTO.getStatusMap().get(pmDTO.getId()) == null || tedDTO.getStatusMap().get(pmDTO.getId()).getTimesheetStatus().equalsIgnoreCase("REJECTED") 
							|| tedDTO.getStatusMap().get(pmDTO.getId()).getTimesheetStatus().equalsIgnoreCase("DRAFT") || tedDTO.getStatusMap().get(pmDTO.getId()).getTimesheetStatus().equalsIgnoreCase("NEW") ) {
						
						if(tedDTO.getStatusMap().get(pmDTO.getId()).getTimesheetStatus().equalsIgnoreCase("NEW") && (
								StringUtils.isNotEmpty(pmDTO.getIsProjectForTimeSheets()) 
								                          && pmDTO.getIsProjectForTimeSheets().equalsIgnoreCase("Y"))
						  ) {
							freshProjectMembersList.add(pmDTO);
							allProjectMembersList.add(pmDTO);
						}
						if(!tedDTO.getStatusMap().get(pmDTO.getId()).getTimesheetStatus().equalsIgnoreCase("NEW")) {
							freshProjectMembersList.add(pmDTO);
							allProjectMembersList.add(pmDTO);
							attProjectMemberIdsList.add(pmDTO);
							oldSubmittedPmIdsList.add(pmDTO.getId());
						}
						
					}else{
						submittedProjectMembersList.add(pmDTO);
						oldSubmittedPmIdsList.add(pmDTO.getId());
						allProjectMembersList.add(pmDTO);
						attProjectMemberIdsList.add(pmDTO);
					}
					
				}
				tedDTO.getProjectMembersList().clear();
				tedDTO.setOldSubmittedProjectMembersList(oldSubmittedPmIdsList);
				tedDTO.setProjectMembersList(freshProjectMembersList);
				tedDTO.setSubmittedProjectMembersList(submittedProjectMembersList);
				tedDTO.setAllProjectMembersList(allProjectMembersList);
				tedDTO.setAttProjectMembersList(attProjectMemberIdsList);
				if(StringUtils.isNotEmpty(combinedStatus) && (combinedStatus.equalsIgnoreCase("SUBMITTED") || combinedStatus.equalsIgnoreCase("APPROVED"))){
					tedDTO.setSaveButtonVisible(false);
				}else {
					tedDTO.setSaveButtonVisible(true);
				}
				tedDTO.setSubmitButtonVisible(submitButtonVisible);
				 
				int workingDaysPerWeek = 0;
				if(StringUtils.isNotEmpty(orgLocations.getWeekOffDays())) {
					 String[] strArray = orgLocations.getWeekOffDays().split(",");
				     List<String> weekOffs = new ArrayList<String>(Arrays.asList(strArray));
					if(combinedMonEditable && !weekOffs.contains("Mon")) {
						workingDaysPerWeek++;
					}
	                if(combinedTueEditable && !weekOffs.contains("Tue")) {
	                	workingDaysPerWeek++;
					}
	                if(combinedWedEditable && !weekOffs.contains("Wed")) {
	                	workingDaysPerWeek++;
					}
	                if(combinedThuEditable && !weekOffs.contains("Thu")) {
	                	workingDaysPerWeek++;
					}
	                if(combinedFriEditable && !weekOffs.contains("Fri")) {
	                	workingDaysPerWeek++;
					}
	                if(combinedSatEditable && !weekOffs.contains("Sat")) {
	                	workingDaysPerWeek++;
					}
	                if(combinedSunEditable && !weekOffs.contains("Sun")) {
	                	workingDaysPerWeek++;
					}
				}else {
					workingDaysPerWeek = 5;
				}
				
				Integer avgWorkingHoursPerDay = 0;
				Employees employee = employeesService.getEmployeeByUserAndOrgLocationId(loginUser.getId(),orgLocations.getId());
				
				if(employee != null && StringUtils.isNotEmpty(employee.getDailyHours())){
					String [] hrsArray = employee.getDailyHours().split(":");
					avgWorkingHoursPerDay = Integer.parseInt(hrsArray[0]);
				}else {
					if(orgLocations.getAverageWorkingHours() != null) {
						avgWorkingHoursPerDay = orgLocations.getAverageWorkingHours();
					}else {
						avgWorkingHoursPerDay = 8;
					}
				}
                
                Map<String,List<EmployeeLeaveDTO>> empLeaveMap = getEmpLeaveList(tedDTO.getWeekDays(),tedDTO.getCurrentTimeSubFrequency(), orgLocations,loginUser,candidate,shiftId);
				tedDTO.setEmpLeaveMap(empLeaveMap);
				Integer minimumWorkingHoursPerWeek = workingDaysPerWeek * avgWorkingHoursPerDay;
				model.addObject("minimumWorkingHoursPerWeek", minimumWorkingHoursPerWeek);
				
				String isTimesheetAvgHoursRequired = orgLocations.getIsTimesheetAvgHoursRequired();
                String timesheetAvgHoursValidation = "N";
                if(StringUtils.isNotEmpty(isTimesheetAvgHoursRequired) && isTimesheetAvgHoursRequired.equalsIgnoreCase("Y")) {
                	timesheetAvgHoursValidation = "Y";
                }
				if(allProjectMembersList == null || allProjectMembersList.isEmpty()) {
					model.addObject("NORECORDSFOUND", true);
					model.addObject("employeeTimesheetEntryDetailsDTO", tedDTO);
					model.setViewName("timesheet/timesheetInvoiceList");
				}else {
					model.addObject("timesheetAvgHoursValidation", timesheetAvgHoursValidation);
					model.addObject("employeeTimesheetEntryDetailsDTO", tedDTO);
					model.setViewName("timesheet/timesheetInvoiceList");
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addObject("AWKWARD", true);
			model.setViewName("timesheet/timesheetInvoiceList");
		}
		return model;

	}
	
	public Map<String,List<EmployeeLeaveDTO>> getEmpLeaveList(List<WeekDaysDTO> weekDays,String currentTimeSubFrequency,OrgLocations loc,User user,Candidate candidate, Long shiftId) throws Exception {
		Map<String,List<EmployeeLeaveDTO>> empLeaveMap = new ConcurrentHashMap<>();
		Date startDate = null;
		Date endDate = null;
		if (StringUtils.isNotEmpty(currentTimeSubFrequency)) {
			String[] subfrequency = currentTimeSubFrequency.split("To");
			String subfrstartDate = subfrequency[0].trim();
			String subfrEndDate = subfrequency[1].trim();
			startDate = commonUtil.orgLocationDateFormat(loc.getDateFormat(), subfrstartDate);
			endDate = commonUtil.orgLocationDateFormat(loc.getDateFormat(), subfrEndDate);
			List<AppliedLeaveDate> leaveDateList = appliedLeaveDateService.getAppliedLeaveDateByUserId(user.getId(), startDate, endDate);
			List<Holiday>  holidays = null;
			if(user != null && candidate != null && candidate.getWorkLocation() != null) {
				if(!loc.getId().equals(candidate.getWorkLocation().getId())) {
					Shift shift = shiftService.getShiftListByShiftNameAndOrglocationId(Constants.GENERAL_SHIFT, candidate.getWorkLocation().getId());
					shiftId = shift.getId();
				}
				 holidays = holidayDAO.getHolidaybyLocationIdAndFromdateForTimesheets(startDate, endDate,candidate.getWorkLocation().getId(),user.getId(), candidate.getWorkLocation().getId(),shiftId);
			}
			
			if((leaveDateList != null && !leaveDateList.isEmpty()) || (holidays != null && !holidays.isEmpty())) {
			    LinkedHashMap<String, EmployeeLeaveDTO> valuesMap = new LinkedHashMap<>();
			  if(leaveDateList != null && !leaveDateList.isEmpty()) {
				for(AppliedLeaveDate leaveDate :leaveDateList) {
					String leaveType = leaveDate.getLeave().getLeaveType().getName();
					String billableType = leaveDate.getLeave().getLeaveType().getLeaveType();
					String leaveTypeArr = leaveType+"##"+billableType;
					String avgHours =leaveDate.getAppliedHours();
					EmployeeLeaveDTO leaveDTO = new EmployeeLeaveDTO();
					leaveDTO.setHours(avgHours);
					SimpleDateFormat sf = new SimpleDateFormat("E");
					leaveDTO.setLeaveDate(commonUtil.dateStrFromOrgDateFormatter(loc.getDateFormat(),leaveDate.getApplyLeaveDate()));
					leaveDTO.setDayOfTheDate(sf.format(leaveDate.getApplyLeaveDate()));
					leaveDTO.setHoliday(false);
					leaveDTO.setLeaveReason(leaveDate.getLeave().getReason());
					List<EmployeeLeaveDTO>  leaveDTOList = empLeaveMap.get(leaveTypeArr);
					if(leaveDTOList != null && !leaveDTOList.isEmpty()) {
						leaveDTOList.add(leaveDTO);
						valuesMap.put(leaveTypeArr+leaveDTO.getLeaveDate(),leaveDTO);
						empLeaveMap.put(leaveTypeArr, leaveDTOList);
						
					}else {
						leaveDTOList = new ArrayList<>();
						leaveDTOList.add(leaveDTO);
						valuesMap.put(leaveTypeArr+leaveDTO.getLeaveDate(),leaveDTO);
						empLeaveMap.put(leaveTypeArr, leaveDTOList);
					}
				}
			  }
			  if(holidays != null && !holidays.isEmpty()) {
				for(Holiday holiday :holidays) {
					List<Date> duration =  CalendarUtil.getListOfDaysBetweenTwoDates(holiday.getFromDate(),holiday.getToDate());
					if(duration != null && !duration.isEmpty()) {
						for(Date  holidayDate :duration) {
							String leaveType = holiday.getHolidayName();
							String billableType = holiday.getBillableType();
							String leaveTypeArr = leaveType+"##"+billableType;
							String avgHours =holiday.getOrgAvgHrs();
							EmployeeLeaveDTO leaveDTO = new EmployeeLeaveDTO();
							leaveDTO.setHours(avgHours);
							SimpleDateFormat sf = new SimpleDateFormat("E");
							leaveDTO.setLeaveDate(commonUtil.dateStrFromOrgDateFormatter(loc.getDateFormat(),holidayDate));
							leaveDTO.setDayOfTheDate(sf.format(holidayDate));
							leaveDTO.setLeaveReason(holiday.getDescription());
							leaveDTO.setHoliday(true);
							leaveDTO.setHolidayId(holiday.getId());
							leaveDTO.setHolidayUserId(user.getId());
							if(holiday.getOrgLocations() != null){
								leaveDTO.setHolidayUserLocationId(holiday.getOrgLocations().getId());
							}else {
								leaveDTO.setHolidayUserLocationId(loc.getId());
							}
							List<EmployeeLeaveDTO>  leaveDTOList = empLeaveMap.get(leaveTypeArr);
							if(leaveDTOList != null && !leaveDTOList.isEmpty()) {
								leaveDTOList.add(leaveDTO);
								valuesMap.put(leaveTypeArr+leaveDTO.getLeaveDate(),leaveDTO);
								empLeaveMap.put(leaveTypeArr, leaveDTOList);
								
							}else {
								leaveDTOList = new ArrayList<>();
								leaveDTOList.add(leaveDTO);
								valuesMap.put(leaveTypeArr+leaveDTO.getLeaveDate(),leaveDTO);
								empLeaveMap.put(leaveTypeArr, leaveDTOList);
							}
						}
					}
					
				 }
				}
				Boolean isLeaveOnFullWeek = true;
				LinkedList<String> weekDaysList = new LinkedList<>();
				weekDaysList.add("Mon");
				weekDaysList.add("Tue");
				weekDaysList.add("Wed");
				weekDaysList.add("Thu");
				weekDaysList.add("Fri");
				weekDaysList.add("Sat");
				weekDaysList.add("Sun");
				
				for (Map.Entry<String, List<EmployeeLeaveDTO>> entry : empLeaveMap.entrySet())
				{
					List<EmployeeLeaveDTO> freshLeaveDTOList = new LinkedList<>();
					String leaveTypeArr = entry.getKey();
						for (WeekDaysDTO weekDay : weekDays) {
							if (valuesMap.get(leaveTypeArr+weekDay.getDate()) == null) {
								EmployeeLeaveDTO leaveDTO = new EmployeeLeaveDTO();
								leaveDTO.setHours("HH:MM");
								leaveDTO.setLeaveDate(weekDay.getDate());
								leaveDTO.setDayOfTheDate(weekDay.getDayOfTheDate());
								leaveDTO.setLeaveReason("");
								freshLeaveDTOList.add(leaveDTO);
							}else {
								freshLeaveDTOList.add(valuesMap.get(leaveTypeArr+weekDay.getDate()));
								weekDaysList.remove(weekDay.getDayOfTheDate());
							}
						}
					entry.getValue().clear();
					entry.getValue().addAll(freshLeaveDTOList);
				}
				
				if(weekDaysList != null && !weekDaysList.isEmpty()) {
					isLeaveOnFullWeek = false;
				}
				//tedDTO.setLeaveOnFullWeek(isLeaveOnFullWeek);
				//tedDTO.setEmpLeaveMap(empLeaveMap);
			}
		}
		return empLeaveMap;
	}
	
	private EmployeeTimesheetEntryDetailsDTO getEmployeeTimesheetEntryDetails(TimesheetExpenseEntryDetailsUtilDTO eedDTO,
			HttpServletRequest request) throws Exception {
		EmployeeTimesheetEntryDetailsDTO employeeTimesheetEntryDetailsDTO = new EmployeeTimesheetEntryDetailsDTO();
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if (loginUser == null && eedDTO.getUserId() != null) {
			loginUser = userService.getUserById(eedDTO.getUserId());
		}else {
			loginUser = userService.getUserById(loginUser.getId());
		}
		
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		if (orgLocations == null && StringUtils.isNotEmpty(eedDTO.getLocationCode())) {
			orgLocations = orgLocationService.getOrgLocationsByLocationCode(eedDTO.getLocationCode());
		}else {
			orgLocations = orgLocationService.getOrgLocations(orgLocations.getId());
		}

		Candidate candidate = (Candidate) SourceLeadSessionManager.getSessionAttribute(request, "candidate");
		if (loginUser != null && orgLocations != null) {
			candidate = candidateService.getCandidateByConsultantUserId(loginUser.getId(), orgLocations.getId());
		}

		/**
		 * Getting List Of Projects For LoginUser
		 */
		List<String> timesheetFrequencyList = projectMembersService.getProjectMembersTimesheetFrequenciesByOrgLocationsIdAndMasterCandidateId(orgLocations.getId(),orgLocations.getOrg().getId(), candidate.getMasterCandidateId().getId());
		if(timesheetFrequencyList != null && !timesheetFrequencyList.isEmpty()) {
			employeeTimesheetEntryDetailsDTO.setInvoiceFrequencyList(new LinkedHashSet<String>(timesheetFrequencyList));
			if(StringUtils.isEmpty(eedDTO.getInvoiceFrequency())){
				employeeTimesheetEntryDetailsDTO.setInvoiceFrequency(timesheetFrequencyList.get(0));
	       }else {
	       	employeeTimesheetEntryDetailsDTO.setInvoiceFrequency(eedDTO.getInvoiceFrequency());
	       }
			List<ProjectMembers> projectMembersList = projectMembersService.getProjectMembersByOrgLocationsIdAndCandidateIdTimesheetFrequency(orgLocations.getId(),orgLocations.getOrg().getId(), candidate.getId(),employeeTimesheetEntryDetailsDTO.getInvoiceFrequency());
			employeeTimesheetEntryDetailsDTO =  getEmployeeWeekFrequncyDropDown(projectMembersList,employeeTimesheetEntryDetailsDTO,eedDTO,request);
		}
		return employeeTimesheetEntryDetailsDTO;

	}
	
	private EmployeeTimesheetEntryDetailsDTO  getEmployeeWeekFrequncyDropDown(List<ProjectMembers> projectMembersList,EmployeeTimesheetEntryDetailsDTO etd,TimesheetExpenseEntryDetailsUtilDTO eedDTO,HttpServletRequest request) throws Exception{
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (loginUser == null && eedDTO.getUserId() != null) {
				loginUser = userService.getUserById(eedDTO.getUserId());
			}else {
				loginUser = userService.getUserById(loginUser.getId());
			}
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
					"orgLocations");
			if (orgLocations == null && StringUtils.isNotEmpty(eedDTO.getLocationCode())) {
				orgLocations = orgLocationService.getOrgLocationsByLocationCode(eedDTO.getLocationCode());
			}else {
				orgLocations = orgLocationService.getOrgLocations(orgLocations.getId());
			}
	
			Candidate candidate = (Candidate) SourceLeadSessionManager.getSessionAttribute(request, "candidate");
			if (loginUser != null && orgLocations != null) {
				candidate = candidateService.getCandidateByConsultantUserId(loginUser.getId(), orgLocations.getId());
			}
  	         Map<String,String> frequencyDropDown = timesheetFrequencyGeneratorUtil.getEmployeeTimesheetFrequencyListFromInvoiceFrequency(etd.getInvoiceFrequency(), orgLocations);
	   	     Date sDate = null;
		     Date eDate = null;
		     String fStartDate = null;
          	 String fEndDate =  null;
          	 if(StringUtils.isNotEmpty(eedDTO.getWeekFrequency())){
          		 String[] frequency = eedDTO.getWeekFrequency().split("To");
          		 fStartDate = frequency[0].trim();
          		 fEndDate = frequency[1].trim();
          	 }
	         if(StringUtils.isNotEmpty(fEndDate) && StringUtils.isNotEmpty(fEndDate))
			 {
				 sDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),fStartDate.replaceAll("/", "-"));
				 eDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),fEndDate.replaceAll("/", "-"));
			 }	
	   	   
	         Map<String,Date> dateList =  new HashMap<>();
			 String currentTimesheetFrequency = eedDTO.getCurrentTimeFrequency();
	        if(frequencyDropDown != null && !frequencyDropDown.isEmpty()){
	    	int  count = 1;
	    	LinkedHashMap<String,String> dateFrequencyList = new LinkedHashMap<>();
	    	for (Map.Entry<String,String> entry : frequencyDropDown.entrySet()) {
	    		 String  k = entry.getKey();
	    		 String  v = entry.getValue();
	    		 Date frequencyStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),k.replaceAll("/", "-"));
	    		 Date frequencyEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),v.replaceAll("/", "-"));
	    		try {
	    			if(sDate == null && eDate == null&& count == 1
	    				&& !CalendarUtil.isCurrentDateBetweenDateRange(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),sDate), commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),eDate),orgLocations.getDateFormat())){
	    				 dateList.clear();
                 	     dateList.put("STARTDATE", frequencyStartDate);
					     dateList.put("ENDDATE",frequencyEndDate);
					     if(StringUtils.isEmpty(eedDTO.getCurrentTimeFrequency()))
					     currentTimesheetFrequency = k.replaceAll("/", "-")+"To"+v.replaceAll("/", "-");
	    			}
	    			else if(sDate == null && eDate == null
	    					 && CalendarUtil.isCurrentDateBetweenDateRange(k, v,orgLocations.getDateFormat())){
                  	     dateList.clear();
                  	     dateList.put("STARTDATE",frequencyStartDate);
					         dateList.put("ENDDATE", frequencyEndDate);
					         if(StringUtils.isEmpty(eedDTO.getCurrentTimeFrequency()))
					         currentTimesheetFrequency = k.replaceAll("/", "-")+"To"+v.replaceAll("/", "-");
                   }
	    			else if(sDate != null && eDate != null 
	    					&& sDate.equals(frequencyStartDate) && eDate.equals(frequencyEndDate))
	    			{
	    				 dateList.clear();
                 	     dateList.put("STARTDATE",frequencyStartDate);
					     dateList.put("ENDDATE", frequencyEndDate);
					     if(StringUtils.isEmpty(eedDTO.getCurrentTimeFrequency()))
					     currentTimesheetFrequency = k.replaceAll("/", "-")+"To"+v.replaceAll("/", "-");
	    			}
			      } catch (Exception e) {
			    	  e.printStackTrace();
			      }
	    	     count++;
	    	    if(k.equalsIgnoreCase(v)){
	    	    	dateFrequencyList.put(k.replaceAll("/", "-")+"To"+v.replaceAll("/", "-"), k.replaceAll("/", "-"));
	    	     }else{
	    	    	 dateFrequencyList.put(k.replaceAll("/", "-")+"To"+v.replaceAll("/", "-"),  k.replaceAll("/", "-")+" To "+v.replaceAll("/", "-"));
	    	     }
	    	    etd.setWeekFrequencyList(dateFrequencyList);
	    	}
	        } 
	        etd.setCurrentTimeFrequency(currentTimesheetFrequency);
	        eedDTO.setCurrentTimeFrequency(currentTimesheetFrequency);
	        etd =  getEmployeeTimesheetSubFrequencyListByCurrentFrequency(eedDTO, etd, orgLocations,candidate, request);
	        etd = getWeekDaysByWeekStartDateAndEndDate(etd, etd.getCurrentTimeSubFrequency(), orgLocations);
	        etd = getProjectMembersList(projectMembersList,request,etd, candidate, etd.getCurrentTimeFrequency(),etd.getCurrentTimeSubFrequency(), orgLocations,loginUser);
		  return etd;
	}
	
	private EmployeeTimesheetEntryDetailsDTO getEmployeeTimesheetSubFrequencyListByCurrentFrequency(
			TimesheetExpenseEntryDetailsUtilDTO eedDTO, EmployeeTimesheetEntryDetailsDTO employeeTimesheetEntryDetailsDTO,
			OrgLocations orgLocations,Candidate candidate, HttpServletRequest request) throws Exception {
		LinkedHashMap<String, String> timeSubFrequencies = new LinkedHashMap<>();

		String currentTimeFrequency = eedDTO.getCurrentTimeFrequency();
		String[] frequency = currentTimeFrequency.split("To");
		String currentFrequencyStartDate = frequency[0].trim();
		String currentFrequencyEndDate = frequency[1].trim();

		String timesheetFor = "user";
		if (StringUtils.isNotEmpty(eedDTO.getTimeSheetsFor())) {
			timesheetFor = eedDTO.getTimeSheetsFor();
		}

		if (!isNullOrEmpty(currentFrequencyStartDate) && !isNullOrEmpty(currentFrequencyEndDate)) {
			Map<String, String> timeSubFrequencyMap = timesheetFrequencyGeneratorUtil
					.getTabFrequencyListFromProjectInvoiceFrequency(currentFrequencyStartDate, currentFrequencyEndDate,
							orgLocations.getDateFormat());
			String currentTimeSubFrequency = eedDTO.getCurrentTimeSubFrequency();

			StringBuilder currentStartDate = new StringBuilder("");
			StringBuilder currentEndDate = new StringBuilder("");
			int count = 1;
			if (timeSubFrequencyMap != null && !timeSubFrequencyMap.isEmpty()) {
				for (Map.Entry<String, String> entry : timeSubFrequencyMap.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
					Date startDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
							key.replaceAll("/", "-"));
					Date endDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
							value.replaceAll("/", "-"));
					DateFormat shortormat = Constants.SHORT_DF_STD_DM;
					if (CalendarUtil.isCurrentDateBetweenDateRange(currentFrequencyStartDate, currentFrequencyEndDate,
							orgLocations.getDateFormat())) {
						if (CalendarUtil.isCurrentDateBetweenDateRange(key, value, orgLocations.getDateFormat())) {
							currentStartDate.append(key);
							currentEndDate.append(value);
						}
						if (startDate.equals(endDate)) {
							timeSubFrequencies.put(key + "To" + value, shortormat.format(startDate));
						} else {
							timeSubFrequencies.put(key + "To" + value,
									shortormat.format(startDate) + " - " + shortormat.format(endDate));
						}
					} else {
						if (count == 1) {
							currentStartDate.append(key);
							currentEndDate.append(value);
						}
						if (startDate.equals(endDate)) {
							timeSubFrequencies.put(key + "To" + value, shortormat.format(startDate));
						} else {
							timeSubFrequencies.put(key + "To" + value,
									shortormat.format(startDate) + " - " + shortormat.format(endDate));
						}
					}
					count++;
				}

				if (StringUtils.isEmpty(eedDTO.getCurrentTimeSubFrequency())) {
					currentTimeSubFrequency = currentStartDate.toString() + "To" + currentEndDate.toString();
				}
				employeeTimesheetEntryDetailsDTO.setCurrentTimeSubFrequency(currentTimeSubFrequency);
				employeeTimesheetEntryDetailsDTO.setTimeSubFrequencies(timeSubFrequencies);
				employeeTimesheetEntryDetailsDTO.setTimesheetFor(timesheetFor);
				employeeTimesheetEntryDetailsDTO.setLocDateFomat(orgLocations.getDateFormat());
			}
		}
		return employeeTimesheetEntryDetailsDTO;
	}
	
	
	private EmployeeTimesheetEntryDetailsDTO getWeekDaysByWeekStartDateAndEndDate(EmployeeTimesheetEntryDetailsDTO employeeTimesheetEntryDetailsDTO,String currentTimeSubFrequency,OrgLocations orgLocations) throws Exception {
		
		String[] currentSubFrequency = currentTimeSubFrequency.split("To");
		String currentSubFrequencyStartDate = currentSubFrequency[0].trim();
		String currentSubFrequencyEndDate = currentSubFrequency[1].trim();
		Date actualStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),currentSubFrequencyStartDate.replaceAll("/", "-"));
				

		String weekStartDate = currentSubFrequencyStartDate;
		SimpleDateFormat sf = new SimpleDateFormat("E");
		if (!sf.format(actualStartDate).equalsIgnoreCase("Mon")) {
			Calendar cal = Calendar.getInstance(TimeZone.getDefault());
			cal.setLenient(false);
			cal.setTime(actualStartDate);
			Calendar startMonday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON",orgLocations.getDateFormat());
			if(startMonday.getTime().before(actualStartDate)) {
				weekStartDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), startMonday.getTime());
			}else {
				startMonday = CalendarUtil.getPreviousWeekDayByCalendar(cal, "MON",orgLocations.getDateFormat());
				weekStartDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), startMonday.getTime());
			}
		}
		String tuesDayDate = CalendarUtil.getNextDate(weekStartDate, orgLocations.getDateFormat());
		String WednesDayDate = CalendarUtil.getNextDate(tuesDayDate, orgLocations.getDateFormat());
		String ThursDayDate = CalendarUtil.getNextDate(WednesDayDate, orgLocations.getDateFormat());
		String FriDayDate = CalendarUtil.getNextDate(ThursDayDate, orgLocations.getDateFormat());
		String SaturdayDate = CalendarUtil.getNextDate(FriDayDate, orgLocations.getDateFormat());
		String SundayDate = CalendarUtil.getNextDate(SaturdayDate, orgLocations.getDateFormat());
		
		/**
		 * Current Week Days
		 */
		ArrayList<WeekDaysDTO> weekDays = new ArrayList<>();
		WeekDaysDTO monDayDTO = new WeekDaysDTO();
		monDayDTO.setDate(weekStartDate);
		monDayDTO.setDayOfTheDate("Mon");
		weekDays.add(monDayDTO);

		WeekDaysDTO tuesDayDTO = new WeekDaysDTO();
		tuesDayDTO.setDate(tuesDayDate);
		tuesDayDTO.setDayOfTheDate("Tue");
		weekDays.add(tuesDayDTO);

		WeekDaysDTO wedDayDTO = new WeekDaysDTO();
		wedDayDTO.setDate(WednesDayDate);
		wedDayDTO.setDayOfTheDate("Wed");
		weekDays.add(wedDayDTO);

		WeekDaysDTO thursDayDTO = new WeekDaysDTO();
		thursDayDTO.setDate(ThursDayDate);
		thursDayDTO.setDayOfTheDate("Thu");
		weekDays.add(thursDayDTO);

		WeekDaysDTO friDayDTO = new WeekDaysDTO();
		friDayDTO.setDate(FriDayDate);
		friDayDTO.setDayOfTheDate("Fri");
		weekDays.add(friDayDTO);

		WeekDaysDTO satDayDTO = new WeekDaysDTO();
		satDayDTO.setDate(SaturdayDate);
		satDayDTO.setDayOfTheDate("Sat");
		weekDays.add(satDayDTO);

		WeekDaysDTO sunDayDTO = new WeekDaysDTO();
		sunDayDTO.setDate(SundayDate);
		sunDayDTO.setDayOfTheDate("Sun");
		weekDays.add(sunDayDTO);
		employeeTimesheetEntryDetailsDTO.setWeekDays(weekDays);
		return employeeTimesheetEntryDetailsDTO;
	}
	
	private EmployeeTimesheetEntryDetailsDTO getProjectMembersList(List<ProjectMembers> projectMembersList,HttpServletRequest request,EmployeeTimesheetEntryDetailsDTO etd,Candidate candidate,String currentTimeFrequency,String currentTimeSubFrequency,OrgLocations orgLocations,User loginUser) throws Exception {
		ArrayList<ProjectMembersDTO> pmDTOList = new ArrayList<>();
		
		
		
		String[] currentFrequency = currentTimeFrequency.split("To");
		String currentFrequencyStartDate = currentFrequency[0].trim();
		String currentFrequencyEndDate   = currentFrequency[1].trim();
		Date actualTimeStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
				currentFrequencyStartDate.replaceAll("/", "-"));
		Date actualTimeEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
				currentFrequencyEndDate.replaceAll("/", "-"));
		
		
		String[] currentSubFrequency = currentTimeSubFrequency.split("To");
		String currentSubFrequencyStartDate = currentSubFrequency[0].trim();
		String currentSubFrequencyEndDate = currentSubFrequency[1].trim();
		Date actualStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
				currentSubFrequencyStartDate.replaceAll("/", "-"));
		Date actualEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
				currentSubFrequencyEndDate.replaceAll("/", "-"));
		LinkedList<EmployeeTimesheetSubmissionDTO> etsDTOList = new LinkedList<>();
		Map<Long,EmployeeTimeSheetSubmissionStatusDTO> ettsDTOMap = new LinkedHashMap<>();
		List<EmployeeTimesheetFileInfoDTO> timesheetAttachments = new ArrayList<>();
		int editableCount = 1;
		if(projectMembersList != null && !projectMembersList.isEmpty()) {
			if (projectMembersList != null && !projectMembersList.isEmpty()) {
				for (ProjectMembers pm : projectMembersList) {
					if(StringUtils.isEmpty(pm.getProjects().getInvoiceFrequency())  
							|| (StringUtils.isNotEmpty(pm.getProjects().getInvoiceFrequency()) && !pm.getProjects().getInvoiceFrequency().equalsIgnoreCase(etd.getInvoiceFrequency()))
							|| (StringUtils.isNotEmpty(etd.getInvoiceFrequency()) && etd.getInvoiceFrequency().equalsIgnoreCase("Bi-Weekly") && (CalendarUtil.getNumberOfDays(pm.getProjects().getWeekStartDate(), actualTimeStartDate) %14 != 0)))
						continue;
					
					
					HashSet<Date> pmhDateList = projectMembersHistoryService
							.getAllProjectMemberHistoryDatesByProjectMemberIdAndWeekStartDateEndDate(
									pm.getId(), actualStartDate, actualEndDate);
					if (pmhDateList.isEmpty() || pmhDateList == null)
						continue;	
					pmDTOList.add(ProjectMemberDTOConverter.getProjectMembersDTOFromProjectMembers(pm));
					TimesheetSubmissions ts = timesheetSubmissionsService.getAllCandidateTimesheetSubmissionsByProjectIdAndMasterCandidateId(actualStartDate, actualEndDate,pm.getProjects().getId(), pm.getMasterCandidateId().getId());
					Map<String,LinkedList<TimesheetEntriesDTO>> entryMap = getEmployeeTimesheetSubmissionDetailsByProjectMemberId(ts,editableCount,new LinkedList(etd.getWeekDays()),pm,request, orgLocations, actualStartDate, actualEndDate, etd);
					String entryAvailableCheck = "";
					for(String key : entryMap.keySet()) {
						entryAvailableCheck = key;
					}
					EmployeeTimeSheetSubmissionStatusDTO etssDTO = getEmployeeTimesheetSubmissionStatus(pm.getId(), currentTimeFrequency, currentTimeSubFrequency, orgLocations, loginUser,editableCount);
					if(etssDTO != null && StringUtils.isNotEmpty(etssDTO.getTimesheetStatus()) && ts != null &&  !etssDTO.getTimesheetStatus().equalsIgnoreCase("NEW")) {
						entryAvailableCheck = "available";
					}
					if(editableCount == 1) {
						etd.setMonEditable(etssDTO.getEditableMap().get("Mon"));
						etd.setTueEditable(etssDTO.getEditableMap().get("Tue"));
						etd.setWedEditable(etssDTO.getEditableMap().get("Wed"));
						etd.setThuEditable(etssDTO.getEditableMap().get("Thu"));
						etd.setFriEditable(etssDTO.getEditableMap().get("Fri"));
						etd.setSatEditable(etssDTO.getEditableMap().get("Sat"));
						etd.setSunEditable(etssDTO.getEditableMap().get("Sun"));
					}
					
					editableCount++;
					ettsDTOMap.put(pm.getId(), etssDTO);
					if(StringUtils.isEmpty(entryAvailableCheck))
						continue;
					if (StringUtils.isNotEmpty(currentFrequencyEndDate)) {
						Date frequencyEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
								currentFrequencyEndDate.replaceAll("/", "-"));
						if (frequencyEndDate.equals(actualEndDate)) {
							if (ts != null) {
								List<TimesheetSubmissionsDocuments> timesheetSubmissionsDocumentsList = timesheetSubmissionsDocumentsService.getAllTimesheetSubmissionsDocumentsByProjectIdMasterCandidateIdAndFrequencyStartDateEndDate(ts.getMasterCandidateId().getId(), ts.getProjects().getId(), actualTimeStartDate, actualTimeEndDate);
								 List<FileInfo> fileInfoList = new ArrayList<>();
								if (timesheetSubmissionsDocumentsList != null && !timesheetSubmissionsDocumentsList.isEmpty()) {
									 for (TimesheetSubmissionsDocuments tsd : timesheetSubmissionsDocumentsList) {
										FileInfo fileInfo = new FileInfo();
										fileInfo.setFileDbId(tsd.getId().toString());
										fileInfo.setFileName(tsd.getFileName());
										fileInfo.setFileURL(tsd.getFileUrl());
										fileInfo.setStatus(tsd.getStatus());
										fileInfo.setContentType(tsd.getFileContentType());
										fileInfoList.add(fileInfo);
									}
								 }
								 if((fileInfoList != null && !fileInfoList.isEmpty()) || StringUtils.isNotEmpty(ts.getWeekcomments())) {
									 EmployeeTimesheetFileInfoDTO etfinfo = new EmployeeTimesheetFileInfoDTO();
									 etfinfo.setProjectMemberId(pm.getId());
									 etfinfo.setFileInfo(fileInfoList);
									 etfinfo.setWeekComment(ts.getWeekcomments());
									 timesheetAttachments.add(etfinfo);
								 }
							}
						}
					}
					
					List<ProjectTasks> projectsTasks = projectTasksService.getAllTasksByProjectId(pm.getProjects().getId());
					if(entryMap != null && !entryMap.isEmpty()) {
					    for(Map.Entry<String,LinkedList<TimesheetEntriesDTO>> entry : entryMap.entrySet()) {
							EmployeeTimesheetSubmissionDTO etsDTO = new EmployeeTimesheetSubmissionDTO();
							Long pmId = null;
							String taskType = "";
							String ticketNumber = "";
							String hourlyType = "";
							for(TimesheetEntriesDTO teDTO :entry.getValue()) {
								if(teDTO.getProjectMemberId() != null) {
									pmId = teDTO.getProjectMemberId();
								}else {
									pmId = pm.getId();
								}
								if(teDTO != null && teDTO.getTimesheetTaskDTO() != null && StringUtils.isNotEmpty(teDTO.getTimesheetTaskDTO().getTaskType())) {
									taskType = teDTO.getTimesheetTaskDTO().getTaskType();
								}	
								
								if(teDTO != null && StringUtils.isNotEmpty(teDTO.getTicketNumber())) {
									ticketNumber = teDTO.getTicketNumber();
								}
								
								if(teDTO != null && StringUtils.isNotEmpty(teDTO.getHourlyType())) {
									hourlyType = teDTO.getHourlyType();
								}
							}
							etsDTO.setTimesheetEntries(entry.getValue());
							etsDTO.setProjectMemberId(pmId);
							String[] taskSplitter = entry.getKey().split("##");
							Long taskId = 0l;
							if(taskSplitter != null && taskSplitter.length >= 0 && StringUtils.isNotEmpty(taskSplitter[0])) {
								taskId = Long.parseLong(taskSplitter[0]);
							}
							if(taskId == 0)
								continue;
							etsDTO.setProjectTaskId(taskId);
							etsDTO.setTicketNumber(ticketNumber);
							etsDTO.setHourlyType(hourlyType);
							etsDTO.setProjectTaskType(taskType);
							etsDTO.setProjectTaskList(projectsTasks);
							etsDTOList.add(etsDTO);
						}
					}
				}
				etd.setStatusMap(ettsDTOMap);
				etd.setTimsheetAttachments(timesheetAttachments);
			}
		}
		
		if(etsDTOList != null && !etsDTOList.isEmpty()) {
			etd.setSubmissionList(etsDTOList);
			etd.setMaxRowCount(etsDTOList.size());
		}else {
			EmployeeTimesheetSubmissionDTO etsDTO = new EmployeeTimesheetSubmissionDTO();
			List<TimesheetEntriesDTO> edtoList = getTimesheetEntriesForEmptySubmissions(orgLocations, actualStartDate, actualEndDate, etd);
			etsDTO.setTimesheetEntries(edtoList);
			etsDTOList.add(etsDTO);
			etd.setSubmissionList(etsDTOList);
			etd.setMaxRowCount(1);
		}
		etd.setProjectMembersList(pmDTOList);
		
		return etd;
	}
	
	
	
	private EmployeeTimeSheetSubmissionStatusDTO getEmployeeTimesheetSubmissionStatus(Long projectMemberId,
			String currentTimeFrequency, String currentTimeSubFrequency, OrgLocations orgLocations,
			User loginUser,int editableCheck) throws Exception {
		EmployeeTimeSheetSubmissionStatusDTO statusDTO = new EmployeeTimeSheetSubmissionStatusDTO();
		String[] frequency = currentTimeFrequency.split("To");
		String currentFrequencyEndDate = frequency[1].trim();
		
		Boolean isFillMyTimeSheet = commonUtil.hasRole("ROLE_FILL_MY_TIMESHEETS");
		Boolean isFillMemberTimesheet = commonUtil.hasRole("ROLE_FILL_MEMBER_TIMESHEETS");
		if (isFillMyTimeSheet || isFillMemberTimesheet) {
			if (!isFillMyTimeSheet && isFillMemberTimesheet) {
				isFillMyTimeSheet = isFillMemberTimesheet;
			}
		}
		String timesheetFor = "user";
		ProjectMembers projectMember = projectMembersService.getProjectMemberById(projectMemberId);
		HashMap<String, String> statusMap = getEmployeeTimesheetStatusByTimeSubFrequency(projectMember, currentTimeFrequency, orgLocations, loginUser);
		StringBuilder disbleCondition1 = new StringBuilder("");
		StringBuilder disbleCondition2 = new StringBuilder("");
		StringBuilder disbleCondition3 = new StringBuilder("");
		StringBuilder disbleCondition4 = new StringBuilder("");
		StringBuilder disbleCondition5 = new StringBuilder("");
		StringBuilder disbleCondition6 = new StringBuilder("");
		StringBuilder disbleCondition7 = new StringBuilder("");

		String[] currentSubFrequency = currentTimeSubFrequency.split("To");
		String currentSubFrequencyStartDate = currentSubFrequency[0].trim();
		String currentSubFrequencyEndDate = currentSubFrequency[1].trim();

		Boolean reopenButtonIsVisible = new Boolean(statusMap.get("IS_Client_PrimeVendor"));

		Date actualStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),currentSubFrequencyStartDate.replaceAll("/", "-"));
				
		Date actualEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),currentSubFrequencyEndDate.replaceAll("/", "-"));
				
		int dayOfTheDateWithStartDate = CalendarUtil.getDayOfTheDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), currentSubFrequencyStartDate));
		String weekStartDate = currentSubFrequencyStartDate;
		switch (dayOfTheDateWithStartDate) {
		case 1:
			weekStartDate = CalendarUtil.getPreviousDateByDecrementor(currentSubFrequencyStartDate, 6,
					orgLocations.getDateFormat());
			disbleCondition1.setLength(0);
			disbleCondition2.setLength(0);
			disbleCondition3.setLength(0);
			disbleCondition4.setLength(0);
			disbleCondition5.setLength(0);
			disbleCondition6.setLength(0);
			disbleCondition1.append("disabled");
			disbleCondition2.append("disabled");
			disbleCondition3.append("disabled");
			disbleCondition4.append("disabled");
			disbleCondition5.append("disabled");
			disbleCondition6.append("disabled");
			break;
		case 2:
			weekStartDate = CalendarUtil.getPreviousDateByDecrementor(currentSubFrequencyStartDate, 0,
					orgLocations.getDateFormat());
			break;
		case 3:
			weekStartDate = CalendarUtil.getPreviousDateByDecrementor(currentSubFrequencyStartDate, 1,
					orgLocations.getDateFormat());
			disbleCondition1.setLength(0);
			disbleCondition1.append("disabled");
			break;
		case 4:
			weekStartDate = CalendarUtil.getPreviousDateByDecrementor(currentSubFrequencyStartDate, 2,
					orgLocations.getDateFormat());
			disbleCondition1.setLength(0);
			disbleCondition2.setLength(0);
			disbleCondition1.append("disabled");
			disbleCondition2.append("disabled");
			break;
		case 5:
			weekStartDate = CalendarUtil.getPreviousDateByDecrementor(currentSubFrequencyStartDate, 3,
					orgLocations.getDateFormat());
			disbleCondition1.setLength(0);
			disbleCondition2.setLength(0);
			disbleCondition3.setLength(0);
			disbleCondition1.append("disabled");
			disbleCondition2.append("disabled");
			disbleCondition3.append("disabled");
			break;
		case 6:
			weekStartDate = CalendarUtil.getPreviousDateByDecrementor(currentSubFrequencyStartDate, 4,
					orgLocations.getDateFormat());
			disbleCondition1.setLength(0);
			disbleCondition2.setLength(0);
			disbleCondition3.setLength(0);
			disbleCondition4.setLength(0);
			disbleCondition1.append("disabled");
			disbleCondition2.append("disabled");
			disbleCondition3.append("disabled");
			disbleCondition4.append("disabled");
			break;
		case 7:
			weekStartDate = CalendarUtil.getPreviousDateByDecrementor(currentSubFrequencyStartDate, 5,
					orgLocations.getDateFormat());
			disbleCondition1.setLength(0);
			disbleCondition2.setLength(0);
			disbleCondition3.setLength(0);
			disbleCondition4.setLength(0);
			disbleCondition5.setLength(0);
			disbleCondition1.append("disabled");
			disbleCondition2.append("disabled");
			disbleCondition3.append("disabled");
			disbleCondition4.append("disabled");
			disbleCondition5.append("disabled");
			break;
		default:
			weekStartDate = CalendarUtil.getPreviousDateByDecrementor(currentSubFrequencyStartDate, 0,
					orgLocations.getDateFormat());
			break;
		}
		int dayOfTheDateWithEndDate = CalendarUtil.getDayOfTheDate(
				commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), currentSubFrequencyEndDate));
		switch (dayOfTheDateWithEndDate) {
		case 2:
			disbleCondition2.setLength(0);
			disbleCondition3.setLength(0);
			disbleCondition4.setLength(0);
			disbleCondition5.setLength(0);
			disbleCondition6.setLength(0);
			disbleCondition7.setLength(0);
			disbleCondition2.append("disabled");
			disbleCondition3.append("disabled");
			disbleCondition4.append("disabled");
			disbleCondition5.append("disabled");
			disbleCondition6.append("disabled");
			disbleCondition7.append("disabled");
			break;

		case 3:
			disbleCondition3.setLength(0);
			disbleCondition4.setLength(0);
			disbleCondition5.setLength(0);
			disbleCondition6.setLength(0);
			disbleCondition7.setLength(0);
			disbleCondition3.append("disabled");
			disbleCondition4.append("disabled");
			disbleCondition5.append("disabled");
			disbleCondition6.append("disabled");
			disbleCondition7.append("disabled");
			break;
		case 4:
			disbleCondition4.setLength(0);
			disbleCondition5.setLength(0);
			disbleCondition6.setLength(0);
			disbleCondition7.setLength(0);
			disbleCondition4.append("disabled");
			disbleCondition5.append("disabled");
			disbleCondition6.append("disabled");
			disbleCondition7.append("disabled");
			break;
		case 5:
			disbleCondition5.setLength(0);
			disbleCondition6.setLength(0);
			disbleCondition7.setLength(0);
			disbleCondition5.append("disabled");
			disbleCondition6.append("disabled");
			disbleCondition7.append("disabled");
			break;
		case 6:
			disbleCondition6.setLength(0);
			disbleCondition7.setLength(0);
			disbleCondition6.append("disabled");
			disbleCondition7.append("disabled");
			break;
		case 7:
			disbleCondition7.setLength(0);
			disbleCondition7.append("disabled");
			break;
		}

		
		if(editableCheck == 1) {
			Map<String,Boolean> editableMap = new LinkedHashMap<>();
			if (disbleCondition1.toString().equalsIgnoreCase("disabled")) {
				editableMap.put("Mon", false);
			} else {
				editableMap.put("Mon", true);
			}
			
			if (disbleCondition2.toString().equalsIgnoreCase("disabled")) {
				editableMap.put("Tue", false);
			} else {
				editableMap.put("Tue", true);
			}
			
			
			if (disbleCondition3.toString().equalsIgnoreCase("disabled")) {
				editableMap.put("Wed", false);
			} else {
				editableMap.put("Wed", true);
			}
			
			if (disbleCondition4.toString().equalsIgnoreCase("disabled")) {
				editableMap.put("Thu", false);
			} else {
				editableMap.put("Thu", true);
			}
			
			if (disbleCondition5.toString().equalsIgnoreCase("disabled")) {
				editableMap.put("Fri", false);
			} else {
				editableMap.put("Fri", true);
			}
			
			if (disbleCondition6.toString().equalsIgnoreCase("disabled")) {
				editableMap.put("Sat", false);
			} else {
				editableMap.put("Sat", true);
			}
			
			if (disbleCondition7.toString().equalsIgnoreCase("disabled")) {
				editableMap.put("Sun", false);
			} else {
				editableMap.put("Sun", true);
			}
			statusDTO.setEditableMap(editableMap);
	    }
		
		Date actualWeekStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
				weekStartDate.replaceAll("/", "-"));
		String tuesDayDate = CalendarUtil.getNextDate(weekStartDate, orgLocations.getDateFormat());
		String WednesDayDate = CalendarUtil.getNextDate(tuesDayDate, orgLocations.getDateFormat());
		String ThursDayDate = CalendarUtil.getNextDate(WednesDayDate, orgLocations.getDateFormat());
		String FriDayDate = CalendarUtil.getNextDate(ThursDayDate, orgLocations.getDateFormat());
		String SaturdayDate = CalendarUtil.getNextDate(FriDayDate, orgLocations.getDateFormat());
		String SundayDate = CalendarUtil.getNextDate(SaturdayDate, orgLocations.getDateFormat());
		
		/**
		 * Here getting dates which are enabled in the current submission (Hint:In
		 * particular project,the member may assigned in the mid of the week)
		 */
		HashSet<Date> pmhDateList = projectMembersHistoryService
				.getAllProjectMemberHistoryDatesByProjectMemberIdAndWeekStartDateEndDate(projectMemberId,
						actualWeekStartDate, actualEndDate);

		Calendar currentCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
		currentCal.setLenient(false);
		currentCal.setTime(actualWeekStartDate);

		String approvalStatus = statusMap.get("STATUS");
		if ((!isFillMyTimeSheet) || (!pmhDateList.contains(actualWeekStartDate)) || !isNullOrEmpty(approvalStatus)
				&& (((approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()))
						&& (timesheetFor.equalsIgnoreCase("user") || !reopenButtonIsVisible))
						|| approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())
						|| (approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue())
								&& (!reopenButtonIsVisible && !timesheetFor.equalsIgnoreCase("user"))))) {
			disbleCondition1.setLength(0);
			disbleCondition1.append("disabled");
		}
		if ((!isFillMyTimeSheet)
				|| (!pmhDateList.contains(CalendarUtil
						.getCurrentWeekDayByCalendar(currentCal, "TUE", orgLocations.getDateFormat()).getTime()))
				|| !isNullOrEmpty(approvalStatus)
						&& (((approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()))
								&& (timesheetFor.equalsIgnoreCase("user") || !reopenButtonIsVisible))
								|| approvalStatus
										.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())
								|| (approvalStatus
										.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue())
										&& (!reopenButtonIsVisible && !timesheetFor.equalsIgnoreCase("user"))))) {
			disbleCondition2.setLength(0);
			disbleCondition2.append("disabled");
		}
		if ((!isFillMyTimeSheet)
				|| (!pmhDateList.contains(CalendarUtil
						.getCurrentWeekDayByCalendar(currentCal, "WED", orgLocations.getDateFormat()).getTime()))
				|| !isNullOrEmpty(approvalStatus)
						&& (((approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()))
								&& (timesheetFor.equalsIgnoreCase("user") || !reopenButtonIsVisible))
								|| approvalStatus
										.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())
								|| (approvalStatus
										.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue())
										&& (!reopenButtonIsVisible && !timesheetFor.equalsIgnoreCase("user"))))) {
			disbleCondition3.setLength(0);
			disbleCondition3.append("disabled");
		}
		if ((!isFillMyTimeSheet)
				|| (!pmhDateList.contains(CalendarUtil
						.getCurrentWeekDayByCalendar(currentCal, "THU", orgLocations.getDateFormat()).getTime()))
				|| !isNullOrEmpty(approvalStatus)
						&& (((approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()))
								&& (timesheetFor.equalsIgnoreCase("user") || !reopenButtonIsVisible))
								|| approvalStatus
										.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())
								|| (approvalStatus
										.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue())
										&& (!reopenButtonIsVisible && !timesheetFor.equalsIgnoreCase("user"))))) {
			disbleCondition4.setLength(0);
			disbleCondition4.append("disabled");
		}
		if ((!isFillMyTimeSheet)
				|| (!pmhDateList.contains(CalendarUtil
						.getCurrentWeekDayByCalendar(currentCal, "FRI", orgLocations.getDateFormat()).getTime()))
				|| !isNullOrEmpty(approvalStatus)
						&& (((approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()))
								&& (timesheetFor.equalsIgnoreCase("user") || !reopenButtonIsVisible))
								|| approvalStatus
										.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())
								|| (approvalStatus
										.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue())
										&& (!reopenButtonIsVisible && !timesheetFor.equalsIgnoreCase("user"))))) {
			disbleCondition5.setLength(0);
			disbleCondition5.append("disabled");
		}
		if ((!isFillMyTimeSheet)
				|| (!pmhDateList.contains(CalendarUtil
						.getCurrentWeekDayByCalendar(currentCal, "SAT", orgLocations.getDateFormat()).getTime()))
				|| !isNullOrEmpty(approvalStatus)
						&& (((approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()))
								&& (timesheetFor.equalsIgnoreCase("user") || !reopenButtonIsVisible))
								|| approvalStatus
										.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())
								|| (approvalStatus
										.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue())
										&& (!reopenButtonIsVisible && !timesheetFor.equalsIgnoreCase("user"))))) {
			disbleCondition6.setLength(0);
			disbleCondition6.append("disabled");
		}
		if ((!isFillMyTimeSheet)
				|| (!pmhDateList.contains(CalendarUtil
						.getCurrentWeekDayByCalendar(currentCal, "SUN", orgLocations.getDateFormat()).getTime()))
				|| !isNullOrEmpty(approvalStatus)
						&& (((approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()))
								&& (timesheetFor.equalsIgnoreCase("user") || !reopenButtonIsVisible))
								|| approvalStatus
										.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())
								|| (approvalStatus
										.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue())
										&& (!reopenButtonIsVisible && !timesheetFor.equalsIgnoreCase("user"))))) {
			disbleCondition7.setLength(0);
			disbleCondition7.append("disabled");
		}
		
		

		if (!pmhDateList.isEmpty() && !isNullOrEmpty(approvalStatus)&& !(approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue())
				&& (timesheetFor.equalsIgnoreCase("user") || !reopenButtonIsVisible))
				&& !approvalStatus.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("approved").getValue())) {
						

			if (approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("new").getValue())
					|| approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("draft").getValue())
					|| !((approvalStatus
							.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue())
							|| approvalStatus
									.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue()))
							&& (timesheetFor.equalsIgnoreCase("user") || !reopenButtonIsVisible))
					|| approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue())
							&& (timesheetFor.equalsIgnoreCase("user"))) {
				statusDTO.setSaveButtonVisible(true);
				if (StringUtils.isNotEmpty(currentFrequencyEndDate)) {
					Date frequencyEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
							currentFrequencyEndDate.replaceAll("/", "-"));
					Date terminationDate = null;
					if(projectMemberId != null) {
						ProjectMembersHistory pmh = projectMembersHistoryService.getProjectMembersOpenHistoryByProjectMemberId(projectMemberId);
						if(pmh == null) {
							pmh = projectMembersHistoryService.getProjectMembersRecentClosedByProjectMemberId(projectMemberId);
						}
						 terminationDate = pmh.getProjectTerminationDate();
					}
					
					if ( frequencyEndDate.equals(actualEndDate) || 
							(terminationDate != null &&(terminationDate.equals(actualStartDate) || terminationDate.equals(actualEndDate) || (terminationDate.before(actualEndDate) && terminationDate.after(actualStartDate))))
					   )
						statusDTO.setSubmitButtonVisible(true);
				}

			}
		} else {
			statusDTO.setSaveButtonVisible(false);
			statusDTO.setSubmitButtonVisible(false);
			statusDTO.setCancelButtonVisible(false);
		}
	
		
		
		boolean isMonEditable = false;
		boolean isTueEditable = false;
		boolean isWedEditable = false;
		boolean isThuEditable = false;
		boolean isFriEditable = false;
		boolean isSatEditable = false;
		boolean isSunEditable = false;
		
		if (disbleCondition1.toString().equalsIgnoreCase("disabled")) {
			isMonEditable = false;
		} else {
			isMonEditable = true;
		}
		
		if (disbleCondition2.toString().equalsIgnoreCase("disabled")) {
			isTueEditable = false;
		} else {
			isTueEditable = true;
		}
		
		if (disbleCondition3.toString().equalsIgnoreCase("disabled")) {
			isWedEditable = false;
		} else {
			isWedEditable = true;
		}
		
		if (disbleCondition4.toString().equalsIgnoreCase("disabled")) {
			isThuEditable = false;
		} else {
			isThuEditable = true;
		}
		
		if (disbleCondition5.toString().equalsIgnoreCase("disabled")) {
			isFriEditable = false;
		} else {
			isFriEditable = true;
		}
		
		if (disbleCondition6.toString().equalsIgnoreCase("disabled")) {
			isSatEditable = false;
		} else {
			isSatEditable = true;
		}
		
		if (disbleCondition7.toString().equalsIgnoreCase("disabled")) {
			isSunEditable = false;
		} else {
			isSunEditable = true;
		}
		
		statusDTO.setMonEditable(isMonEditable);
		statusDTO.setTueEditable(isTueEditable);
		statusDTO.setWedEditable(isWedEditable);
		statusDTO.setThuEditable(isThuEditable);
		statusDTO.setFriEditable(isFriEditable);
		statusDTO.setSatEditable(isSatEditable);
		statusDTO.setSunEditable(isSunEditable);
		
		if(projectMember != null && projectMember.getProjects() != null && StringUtils.isNotEmpty(projectMember.getProjects().getIsTicketNumberRequired()) && projectMember.getProjects().getIsTicketNumberRequired().equalsIgnoreCase("Y")) {
			statusDTO.setTicketNumberRequired(true);
		}else {
			statusDTO.setTicketNumberRequired(false);
		}
		
		if(projectMember != null && projectMember.getProjects() != null && StringUtils.isNotEmpty(projectMember.getProjects().getIsTaskCommentsRequired()) && projectMember.getProjects().getIsTaskCommentsRequired().equalsIgnoreCase("Y")) {
			statusDTO.setTaskCommentsRequired(true);
		}else {
			statusDTO.setTaskCommentsRequired(false);
		}
		
		if(projectMember != null && projectMember.getProjects() != null && projectMember.getProjects().getIsTimesheetAttachmentRequired() != null && projectMember.getProjects().getIsTimesheetAttachmentRequired() ==1) {
			statusDTO.setTimesheetDocumentRequired(true);
		}else {
			statusDTO.setTimesheetDocumentRequired(false);
		}
		
		statusDTO.setTimesheetStatus(statusMap.get("STATUS"));
		statusDTO.setModifyButtonVisible(new Boolean(statusMap.get("IS_MODIFY_BUTTON_VISIBLE")));
		statusDTO.setReopenButtonVisible(new Boolean(statusMap.get("IS_REOPEN_BUTTON_VISIBLE")));
		statusDTO.setReopenRequestSent(new Boolean(statusMap.get("REOPEN_REQUEST_SENT")));
		statusDTO.setRejectedReason(statusMap.get("REJECTED_REASON"));
		return statusDTO;
	}
     private HashMap<String, String> getEmployeeTimesheetStatusByTimeSubFrequency(ProjectMembers pm,
				String currentTimeFrequency, OrgLocations orgLocations, User loginUser) throws Exception {
			String[] frequency = currentTimeFrequency.split("To");
			String currentFrequencyStartDate = frequency[0].trim();
			String currentFrequencyEndDate = frequency[1].trim();
			HashMap<String, String> statusMap = new HashMap<>();

			if (pm != null) {
				Long projectId = pm.getProjects().getId();
				Long masterCandidateId = pm.getMasterCandidateId().getId();
				Date frequencyStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
						currentFrequencyStartDate.replaceAll("/", "-"));
				Date frequencyEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
						currentFrequencyEndDate.replaceAll("/", "-"));

				Boolean isReopenButtonVisible = false;
				SubmissionApproverHierarchy sah  = submissionApproverHierarchyService.getSubmissionApproverHierarchyForEmployeeByProjectIdAndMasterCandidateId(frequencyStartDate,frequencyEndDate, projectId, masterCandidateId);
				Submission_Status submissionStatus = sah.getSubmissionStatus();
				String submissionStatusStr = submissionStatus.getValue();
				SubmissionApproverHierarchy isSah = submissionApproverHierarchyService
						.isThereAnyApproverChangesStatusForProjectAndMasterCandidateId(projectId, masterCandidateId,
								frequencyStartDate, frequencyEndDate, "APPROVED");
				boolean isSahApproved = false;
				if ((isSah == null
						&& !isNullOrEmpty(submissionStatusStr)
						&& (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue())))) {
					if (isSah == null)
						isSahApproved = true;
				}
				statusMap.put("STATUS", submissionStatusStr.toUpperCase());
				statusMap.put("REOPEN_REASON", sah.getReopenReason());
				if (!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr
						.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()))) {
					if (isSahApproved) {
						statusMap.put("IS_MODIFY_BUTTON_VISIBLE", "true");
					}
				}
				if (!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr
						.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue()))) {
					statusMap.put("REJECTED_REASON", sah.getRejectedReason());
				}
				statusMap.put("IS_Client_PrimeVendor", isReopenButtonVisible.toString());
			}
			return statusMap;
		}
	private List<TimesheetEntriesDTO> getTimesheetEntriesForEmptySubmissions(OrgLocations orgLocations, Date actualStartDate,Date actualEndDate,EmployeeTimesheetEntryDetailsDTO etd) throws Exception {
		LinkedList<TimesheetEntriesDTO> tsDTOList = new LinkedList<>();
		for (WeekDaysDTO weekDay : etd.getWeekDays()) {
			TimesheetEntriesDTO teDTO = new TimesheetEntriesDTO();
			teDTO.setDayOfTheDate(weekDay.getDayOfTheDate());
			teDTO.setDate(weekDay.getDate());
			teDTO.setHours("HH:MM");
			tsDTOList.add(teDTO);
		}
		
		return tsDTOList;
	}
	private Map<String,LinkedList<TimesheetEntriesDTO>> getEmployeeTimesheetSubmissionDetailsByProjectMemberId(TimesheetSubmissions ts,Integer editableCount,LinkedList<WeekDaysDTO> weekDaysDTOs, ProjectMembers projectMember,HttpServletRequest request, OrgLocations orgLocations, Date actualStartDate,Date actualEndDate,EmployeeTimesheetEntryDetailsDTO etd) throws Exception {
		Map<String,LinkedList<TimesheetEntriesDTO>> entriesMap = new LinkedHashMap<>();
		LinkedList<TimesheetEntriesDTO> entriesList = new LinkedList<>();
		if(editableCount >1 && ts == null) {
			 return new HashMap<String,LinkedList<TimesheetEntriesDTO>>();
		}else {
			if (ts != null) {
				String dateFormat = orgLocations.getDateFormat();
				
				SimpleDateFormat sf = new SimpleDateFormat("E");
				if (!sf.format(actualStartDate).equalsIgnoreCase("Mon")) {
					Calendar cal = Calendar.getInstance(TimeZone.getDefault());
					cal.setLenient(false);
					cal.setTime(actualStartDate);
					Calendar startMonday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON",orgLocations.getDateFormat());
					if(startMonday.getTime().before(actualStartDate)) {
						actualStartDate = startMonday.getTime();
					}else {
						startMonday = CalendarUtil.getPreviousWeekDayByCalendar(cal, "MON",orgLocations.getDateFormat());
						actualStartDate = startMonday.getTime();
					}
				}
				
				String monDayDate = commonUtil.dateStrFromOrgDateFormatter(dateFormat, actualStartDate);
				String tuesDayDate = CalendarUtil.getNextDate(monDayDate, dateFormat);
				String WednesDayDate = CalendarUtil.getNextDate(tuesDayDate, dateFormat);
				String ThursDayDate = CalendarUtil.getNextDate(WednesDayDate,dateFormat);
				String FriDayDate = CalendarUtil.getNextDate(ThursDayDate, dateFormat);
				String SaturdayDate = CalendarUtil.getNextDate(FriDayDate, dateFormat);
				String SundayDate = CalendarUtil.getNextDate(SaturdayDate, dateFormat);
				
				String monDayTotalHours = "HH:MM";
				String monDayBillableHours = "HH:MM";
				String monDayNonBillableHours = "HH:MM";

				String tuesDayTotalHours = "HH:MM";
				String tuesDayBillableHours = "HH:MM";
				String tuesDayNonBillableHours = "HH:MM";

				String wedDayTotalHours = "HH:MM";
				String wedDayBillableHours = "HH:MM";
				String wedDayNonBillableHours = "HH:MM";

				String thursDayTotalHours = "HH:MM";
				String thursDayBillableHours = "HH:MM";
				String thursDayNonBillableHours = "HH:MM";

				String friDayTotalHours = "HH:MM";
				String friDayBillableHours = "HH:MM";
				String friDayNonBillableHours = "HH:MM";

				String satDayTotalHours = "HH:MM";
				String satDayBillableHours = "HH:MM";
				String satDayNonBillableHours = "HH:MM";

				String sunDayTotalHours = "HH:MM";
				String sunDayBillableHours = "HH:MM";
				String sunDayNonBillableHours = "HH:MM";
				List<TimeSheetDayEntries> timesheetEntriesList = timesheetDayEntriesDao.getAllTimeSheetDayEntriesBySubmissionId(ts.getId());
				if (timesheetEntriesList != null && !timesheetEntriesList.isEmpty()) {
					for (TimeSheetDayEntries te : timesheetEntriesList) {
						
						String taskIdWithRef = "";
						String billableType = "Billable";
						TimesheetTaskDTO timesheetTaskDTO = new TimesheetTaskDTO();
						if (te.getProjectTask() != null) {
							Long taskId = te.getProjectTask().getId();
							billableType = te.getTaskType();
							timesheetTaskDTO.setId(te.getProjectTask().getId());
							timesheetTaskDTO.setTaskName(te.getProjectTask().getTaskName());
							timesheetTaskDTO.setTicketNumber(te.getTicketNumber());
							String ticketNumber = "";
							String hourlyType = "";
							if(StringUtils.isNotEmpty(te.getTicketNumber())) {
								ticketNumber = te.getTicketNumber();
							}
							if(StringUtils.isNotEmpty(te.getHourlyType())) {
								hourlyType = te.getHourlyType();
							}
							taskIdWithRef = taskId+"##"+ticketNumber+"##"+hourlyType;
							
						}

						timesheetTaskDTO.setTaskType(billableType);

						if (monDayDate
								.equalsIgnoreCase(commonUtil.dateStrFromOrgDateFormatter(dateFormat, te.getTimesheetDate()))) {
							String mondayHours = te.getTotalHours();
							if (StringUtils.isEmpty(mondayHours)) {
								mondayHours = "HH:MM";
							}
							TimesheetEntriesDTO teMonDTO = new TimesheetEntriesDTO();
							teMonDTO.setId(te.getId());
							teMonDTO.setSubmissionId(ts.getId());
							if(te != null && te.getTimesheetSubmissions() != null && te.getTimesheetSubmissions().getProjectMembers() != null && te.getTimesheetSubmissions().getProjectMembers().getId() != null)
							teMonDTO.setProjectMemberId(te.getTimesheetSubmissions().getProjectMembers().getId());
							teMonDTO.setTimesheetTaskDTO(timesheetTaskDTO);
							teMonDTO.setDayOfTheDate("Mon");
							teMonDTO.setDate(monDayDate);
							teMonDTO.setHours(mondayHours);
							teMonDTO.setComments(te.getComments());
							teMonDTO.setTicketNumber(te.getTicketNumber());
							teMonDTO.setHourlyType(te.getHourlyType());
							LinkedList<TimesheetEntriesDTO> tsDTOList = entriesMap.get(taskIdWithRef);
							if (tsDTOList == null) {
								tsDTOList = new LinkedList<>();
								tsDTOList.add(teMonDTO);
								entriesMap.put(taskIdWithRef, tsDTOList);
							} else {
								tsDTOList.add(teMonDTO);
								entriesMap.put(taskIdWithRef, tsDTOList);
							}
							if (StringUtils.isNotEmpty(mondayHours) && !mondayHours.equalsIgnoreCase("HH:MM")) {
								if (StringUtils.isNotEmpty(monDayTotalHours) && monDayTotalHours.equalsIgnoreCase("HH:MM")) {
									monDayTotalHours = "0.0";
									monDayBillableHours = "0.0";
									monDayNonBillableHours = "0.0";
								}
								monDayTotalHours = CommonUtil.getCombinedHours(Float.parseFloat(mondayHours.replace(":", ".")),
										Float.parseFloat(monDayTotalHours)).toString();
								if (StringUtils.isNotBlank(billableType) && billableType.equalsIgnoreCase("Non-Billable")) {
									monDayNonBillableHours = CommonUtil
											.getCombinedHours(Float.parseFloat(mondayHours.replace(":", ".")),
													Float.parseFloat(monDayNonBillableHours))
											.toString();
								} else {
									monDayBillableHours = CommonUtil
											.getCombinedHours(Float.parseFloat(mondayHours.replace(":", ".")),
													Float.parseFloat(monDayBillableHours))
											.toString();
								}
							}

						} else if (tuesDayDate
								.equalsIgnoreCase(commonUtil.dateStrFromOrgDateFormatter(dateFormat, te.getTimesheetDate()))) {
							String tuesdayHours = te.getTotalHours();
							if (StringUtils.isEmpty(tuesdayHours)) {
								tuesdayHours = "HH:MM";
							}
							TimesheetEntriesDTO teTueDTO = new TimesheetEntriesDTO();
							teTueDTO.setId(te.getId());
							teTueDTO.setTimesheetTaskDTO(timesheetTaskDTO);
							teTueDTO.setDayOfTheDate("Tue");
							teTueDTO.setDate(tuesDayDate);
							teTueDTO.setSubmissionId(ts.getId());
							teTueDTO.setHours(tuesdayHours);
							teTueDTO.setTicketNumber(te.getTicketNumber());
							teTueDTO.setHourlyType(te.getHourlyType());
							if(te != null && te.getTimesheetSubmissions() != null && te.getTimesheetSubmissions().getProjectMembers() != null && te.getTimesheetSubmissions().getProjectMembers().getId() != null)
							teTueDTO.setProjectMemberId(te.getTimesheetSubmissions().getProjectMembers().getId());
							teTueDTO.setComments(te.getComments());
							LinkedList<TimesheetEntriesDTO> tsDTOList = entriesMap.get(taskIdWithRef);
							if (tsDTOList == null) {
								tsDTOList = new LinkedList<>();
								tsDTOList.add(teTueDTO);
								entriesMap.put(taskIdWithRef, tsDTOList);
							} else {
								tsDTOList.add(teTueDTO);
								entriesMap.put(taskIdWithRef, tsDTOList);
							}
							if (StringUtils.isNotEmpty(tuesdayHours) && !tuesdayHours.equalsIgnoreCase("HH:MM")) {
								if (StringUtils.isNotEmpty(tuesDayTotalHours) && tuesDayTotalHours.equalsIgnoreCase("HH:MM")) {
									tuesDayTotalHours = "0.0";
									tuesDayBillableHours = "0.0";
									tuesDayNonBillableHours = "0.0";
								}
								tuesDayTotalHours = CommonUtil
										.getCombinedHours(Float.parseFloat(tuesdayHours.replace(":", ".")),
												Float.parseFloat(tuesDayTotalHours))
										.toString();
								if (StringUtils.isNotBlank(billableType) && billableType.equalsIgnoreCase("Non-Billable")) {
									tuesDayNonBillableHours = CommonUtil
											.getCombinedHours(Float.parseFloat(tuesdayHours.replace(":", ".")),
													Float.parseFloat(tuesDayNonBillableHours))
											.toString();
								} else {
									tuesDayBillableHours = CommonUtil
											.getCombinedHours(Float.parseFloat(tuesdayHours.replace(":", ".")),
													Float.parseFloat(tuesDayBillableHours))
											.toString();
								}
							}

						} else if (WednesDayDate
								.equalsIgnoreCase(commonUtil.dateStrFromOrgDateFormatter(dateFormat, te.getTimesheetDate()))) {
							String weddayHours = te.getTotalHours();
							if (StringUtils.isEmpty(weddayHours)) {
								weddayHours = "HH:MM";
							}
							TimesheetEntriesDTO teWedDTO = new TimesheetEntriesDTO();
							teWedDTO.setId(te.getId());
							teWedDTO.setTimesheetTaskDTO(timesheetTaskDTO);
							if(te != null && te.getTimesheetSubmissions() != null && te.getTimesheetSubmissions().getProjectMembers() != null && te.getTimesheetSubmissions().getProjectMembers().getId() != null)
							teWedDTO.setProjectMemberId(te.getTimesheetSubmissions().getProjectMembers().getId());
							teWedDTO.setDayOfTheDate("Wed");
							teWedDTO.setSubmissionId(ts.getId());
							teWedDTO.setTicketNumber(te.getTicketNumber());
							teWedDTO.setHourlyType(te.getHourlyType());
							teWedDTO.setDate(WednesDayDate);
							teWedDTO.setHours(weddayHours);
							teWedDTO.setComments(te.getComments());
							LinkedList<TimesheetEntriesDTO> tsDTOList = entriesMap.get(taskIdWithRef);
							if (tsDTOList == null) {
								tsDTOList = new LinkedList<>();
								tsDTOList.add(teWedDTO);
								entriesMap.put(taskIdWithRef, tsDTOList);
							} else {
								tsDTOList.add(teWedDTO);
								entriesMap.put(taskIdWithRef, tsDTOList);
							}
							if (StringUtils.isNotEmpty(weddayHours) && !weddayHours.equalsIgnoreCase("HH:MM")) {
								if (StringUtils.isNotEmpty(wedDayTotalHours) && wedDayTotalHours.equalsIgnoreCase("HH:MM")) {
									wedDayTotalHours = "0.0";
									wedDayBillableHours = "0.0";
									wedDayNonBillableHours = "0.0";
								}
								wedDayTotalHours = CommonUtil.getCombinedHours(Float.parseFloat(weddayHours.replace(":", ".")),
										Float.parseFloat(wedDayTotalHours)).toString();
								if (StringUtils.isNotBlank(billableType) && billableType.equalsIgnoreCase("Non-Billable")) {
									wedDayNonBillableHours = CommonUtil
											.getCombinedHours(Float.parseFloat(weddayHours.replace(":", ".")),
													Float.parseFloat(wedDayNonBillableHours))
											.toString();
								} else {
									wedDayBillableHours = CommonUtil
											.getCombinedHours(Float.parseFloat(weddayHours.replace(":", ".")),
													Float.parseFloat(wedDayBillableHours))
											.toString();
								}
							}

						} else if (ThursDayDate
								.equalsIgnoreCase(commonUtil.dateStrFromOrgDateFormatter(dateFormat, te.getTimesheetDate()))) {
							String thursdayHours = te.getTotalHours();
							if (StringUtils.isEmpty(thursdayHours)) {
								thursdayHours = "HH:MM";
							}
							TimesheetEntriesDTO teThuDTO = new TimesheetEntriesDTO();
							teThuDTO.setId(te.getId());
							teThuDTO.setTicketNumber(te.getTicketNumber());
							teThuDTO.setHourlyType(te.getHourlyType());
							teThuDTO.setTimesheetTaskDTO(timesheetTaskDTO);
							if(te != null && te.getTimesheetSubmissions() != null && te.getTimesheetSubmissions().getProjectMembers() != null && te.getTimesheetSubmissions().getProjectMembers().getId() != null)
							teThuDTO.setProjectMemberId(te.getTimesheetSubmissions().getProjectMembers().getId());
							teThuDTO.setDayOfTheDate("Thu");
							teThuDTO.setSubmissionId(ts.getId());
							teThuDTO.setDate(ThursDayDate);
							teThuDTO.setHours(thursdayHours);
							teThuDTO.setComments(te.getComments());
							LinkedList<TimesheetEntriesDTO> tsDTOList = entriesMap.get(taskIdWithRef);
							if (tsDTOList == null) {
								tsDTOList = new LinkedList<>();
								tsDTOList.add(teThuDTO);
								entriesMap.put(taskIdWithRef, tsDTOList);
							} else {
								tsDTOList.add(teThuDTO);
								entriesMap.put(taskIdWithRef, tsDTOList);
							}
							if (StringUtils.isNotEmpty(thursdayHours) && !thursdayHours.equalsIgnoreCase("HH:MM")) {
								if (StringUtils.isNotEmpty(thursDayTotalHours)
										&& thursDayTotalHours.equalsIgnoreCase("HH:MM")) {
									thursDayTotalHours = "0.0";
									thursDayBillableHours = "0.0";
									thursDayNonBillableHours = "0.0";
								}
								thursDayTotalHours = CommonUtil
										.getCombinedHours(Float.parseFloat(thursdayHours.replace(":", ".")),
												Float.parseFloat(thursDayTotalHours))
										.toString();
								if (StringUtils.isNotBlank(billableType) && billableType.equalsIgnoreCase("Non-Billable")) {
									thursDayNonBillableHours = CommonUtil
											.getCombinedHours(Float.parseFloat(thursdayHours.replace(":", ".")),
													Float.parseFloat(thursDayNonBillableHours))
											.toString();
								} else {
									thursDayBillableHours = CommonUtil
											.getCombinedHours(Float.parseFloat(thursdayHours.replace(":", ".")),
													Float.parseFloat(thursDayBillableHours))
											.toString();
								}
							}

						} else if (FriDayDate
								.equalsIgnoreCase(commonUtil.dateStrFromOrgDateFormatter(dateFormat, te.getTimesheetDate()))) {
							String fridayHours = te.getTotalHours();
							if (StringUtils.isEmpty(fridayHours)) {
								fridayHours = "HH:MM";
							}
							TimesheetEntriesDTO teFriDTO = new TimesheetEntriesDTO();
							if(te != null && te.getTimesheetSubmissions() != null && te.getTimesheetSubmissions().getProjectMembers() != null && te.getTimesheetSubmissions().getProjectMembers().getId() != null)
							teFriDTO.setProjectMemberId(te.getTimesheetSubmissions().getProjectMembers().getId());
							teFriDTO.setId(te.getId());
							teFriDTO.setTicketNumber(te.getTicketNumber());
							teFriDTO.setHourlyType(te.getHourlyType());
							teFriDTO.setTimesheetTaskDTO(timesheetTaskDTO);
							teFriDTO.setDayOfTheDate("Fri");
							teFriDTO.setSubmissionId(ts.getId());
							teFriDTO.setDate(FriDayDate);
							teFriDTO.setHours(fridayHours);
							teFriDTO.setComments(te.getComments());
							LinkedList<TimesheetEntriesDTO> tsDTOList = entriesMap.get(taskIdWithRef);
							if (tsDTOList == null) {
								tsDTOList = new LinkedList<>();
								tsDTOList.add(teFriDTO);
								entriesMap.put(taskIdWithRef, tsDTOList);
							} else {
								tsDTOList.add(teFriDTO);
								entriesMap.put(taskIdWithRef, tsDTOList);
							}
							if (StringUtils.isNotEmpty(fridayHours) && !fridayHours.equalsIgnoreCase("HH:MM")) {
								if (StringUtils.isNotEmpty(friDayTotalHours) && friDayTotalHours.equalsIgnoreCase("HH:MM")) {
									friDayTotalHours = "0.0";
									friDayBillableHours = "0.0";
									friDayNonBillableHours = "0.0";
								}
								friDayTotalHours = CommonUtil.getCombinedHours(Float.parseFloat(fridayHours.replace(":", ".")),
										Float.parseFloat(friDayTotalHours)).toString();
								if (StringUtils.isNotBlank(billableType) && billableType.equalsIgnoreCase("Non-Billable")) {
									friDayNonBillableHours = CommonUtil
											.getCombinedHours(Float.parseFloat(fridayHours.replace(":", ".")),
													Float.parseFloat(friDayNonBillableHours))
											.toString();
								} else {
									friDayBillableHours = CommonUtil
											.getCombinedHours(Float.parseFloat(fridayHours.replace(":", ".")),
													Float.parseFloat(friDayBillableHours))
											.toString();
								}
							}

						} else if (SaturdayDate
								.equalsIgnoreCase(commonUtil.dateStrFromOrgDateFormatter(dateFormat, te.getTimesheetDate()))) {
							String satdayHours = te.getTotalHours();
							if (StringUtils.isEmpty(satdayHours)) {
								satdayHours = "HH:MM";
							}
							TimesheetEntriesDTO teSatDTO = new TimesheetEntriesDTO();
							teSatDTO.setId(te.getId());
							teSatDTO.setTicketNumber(te.getTicketNumber());
							teSatDTO.setHourlyType(te.getHourlyType());
							teSatDTO.setTimesheetTaskDTO(timesheetTaskDTO);
							if(te != null && te.getTimesheetSubmissions() != null && te.getTimesheetSubmissions().getProjectMembers() != null && te.getTimesheetSubmissions().getProjectMembers().getId() != null)
							teSatDTO.setProjectMemberId(te.getTimesheetSubmissions().getProjectMembers().getId());
							teSatDTO.setDayOfTheDate("Sat");
							teSatDTO.setSubmissionId(ts.getId());
							teSatDTO.setDate(SaturdayDate);
							teSatDTO.setHours(satdayHours);
							teSatDTO.setComments(te.getComments());
							LinkedList<TimesheetEntriesDTO> tsDTOList = entriesMap.get(taskIdWithRef);
							if (tsDTOList == null) {
								tsDTOList = new LinkedList<>();
								tsDTOList.add(teSatDTO);
								entriesMap.put(taskIdWithRef, tsDTOList);
							} else {
								tsDTOList.add(teSatDTO);
								entriesMap.put(taskIdWithRef, tsDTOList);
							}
							if (StringUtils.isNotEmpty(satdayHours) && !satdayHours.equalsIgnoreCase("HH:MM")) {
								if (StringUtils.isNotEmpty(satDayTotalHours) && satDayTotalHours.equalsIgnoreCase("HH:MM")) {
									satDayTotalHours = "0.0";
									satDayBillableHours = "0.0";
									satDayNonBillableHours = "0.0";
								}
								satDayTotalHours = CommonUtil.getCombinedHours(Float.parseFloat(satdayHours.replace(":", ".")),
										Float.parseFloat(satDayTotalHours)).toString();
								if (StringUtils.isNotBlank(billableType) && billableType.equalsIgnoreCase("Non-Billable")) {
									satDayNonBillableHours = CommonUtil
											.getCombinedHours(Float.parseFloat(satdayHours.replace(":", ".")),
													Float.parseFloat(satDayNonBillableHours))
											.toString();
								} else {
									satDayBillableHours = CommonUtil
											.getCombinedHours(Float.parseFloat(satdayHours.replace(":", ".")),
													Float.parseFloat(satDayBillableHours))
											.toString();
								}
							}

						} else if (SundayDate
								.equalsIgnoreCase(commonUtil.dateStrFromOrgDateFormatter(dateFormat, te.getTimesheetDate()))) {
							String sundayHours = te.getTotalHours();
							if (StringUtils.isEmpty(sundayHours)) {
								sundayHours = "HH:MM";
							}
							TimesheetEntriesDTO teSunDTO = new TimesheetEntriesDTO();
							teSunDTO.setId(te.getId());
							teSunDTO.setTicketNumber(te.getTicketNumber());
							teSunDTO.setHourlyType(te.getHourlyType());
							teSunDTO.setTimesheetTaskDTO(timesheetTaskDTO);
							teSunDTO.setDayOfTheDate("Sun");
							teSunDTO.setSubmissionId(ts.getId());
							teSunDTO.setDate(SundayDate);
							teSunDTO.setHours(sundayHours);
							if(te != null && te.getTimesheetSubmissions() != null && te.getTimesheetSubmissions().getProjectMembers() != null && te.getTimesheetSubmissions().getProjectMembers().getId() != null)
							teSunDTO.setProjectMemberId(te.getTimesheetSubmissions().getProjectMembers().getId());
							teSunDTO.setComments(te.getComments());
							LinkedList<TimesheetEntriesDTO> tsDTOList = entriesMap.get(taskIdWithRef);
							if (tsDTOList == null) {
								tsDTOList = new LinkedList<>();
								tsDTOList.add(teSunDTO);
								entriesMap.put(taskIdWithRef, tsDTOList);
							} else {
								tsDTOList.add(teSunDTO);
								entriesMap.put(taskIdWithRef, tsDTOList);
							}
							if (StringUtils.isNotEmpty(sundayHours) && !sundayHours.equalsIgnoreCase("HH:MM")) {
								if (StringUtils.isNotEmpty(sunDayTotalHours) && sunDayTotalHours.equalsIgnoreCase("HH:MM")) {
									sunDayTotalHours = "0.0";
									sunDayNonBillableHours = "0.0";
									sunDayBillableHours = "0.0";
								}
								sunDayTotalHours = CommonUtil.getCombinedHours(Float.parseFloat(sundayHours.replace(":", ".")),
										Float.parseFloat(sunDayTotalHours)).toString();
								if (StringUtils.isNotBlank(billableType) && billableType.equalsIgnoreCase("Non-Billable")) {
									sunDayNonBillableHours = CommonUtil
											.getCombinedHours(Float.parseFloat(sundayHours.replace(":", ".")),
													Float.parseFloat(sunDayNonBillableHours))
											.toString();
								} else {
									sunDayBillableHours = CommonUtil
											.getCombinedHours(Float.parseFloat(sundayHours.replace(":", ".")),
													Float.parseFloat(sunDayBillableHours))
											.toString();
								}
							}

						}
					}
				}
			}		
			//return entriesMap;
			if(entriesMap !=null && !entriesMap.isEmpty()) {
				for(String key :entriesMap.keySet()) {
					entriesList.addAll(entriesMap.get(key));
				}
			}
			
			return getEmployeeEntriesMapForDisplayWeb(entriesList, weekDaysDTOs, request);
		}
		
	}
	
	private LinkedHashMap<String, LinkedList<TimesheetEntriesDTO>> getEmployeeEntriesMapForDisplayWeb(
			LinkedList<TimesheetEntriesDTO> entriesList,LinkedList<WeekDaysDTO> weekDaysList, HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		if(orgLocations != null){
			orgLocations = orgLocationService.getOrgLocations(orgLocations.getId());
		}
		LinkedHashMap<String, LinkedList<TimesheetEntriesDTO>> entriesMap = new LinkedHashMap<>();
		LinkedHashMap<String, String> valuesMap = new LinkedHashMap<>();
		LinkedHashMap<String, String> taskTypeMap = new LinkedHashMap<>();
		LinkedHashMap<String, String> taskNameMap = new LinkedHashMap<>();
		LinkedHashMap<String, String> ticketNumberMap = new LinkedHashMap<>();
		LinkedHashMap<String, Integer> weekCountMap = new LinkedHashMap<>();
		if (entriesList != null && entriesList != null && !entriesList.isEmpty()) {
			for (TimesheetEntriesDTO teDTO : entriesList) {
				String taskIdWithRef = "";
				String taskName="";
				String taskType = "";
				String ticketNumber="NA";
				String hourlyType="NA";
				if (teDTO.getTimesheetTaskDTO() != null && teDTO.getTimesheetTaskDTO().getId() != null) {
					Long taskId = teDTO.getTimesheetTaskDTO().getId();
					taskName = teDTO.getTimesheetTaskDTO().getTaskName();
					taskType = teDTO.getTimesheetTaskDTO().getTaskType();
					if(StringUtils.isNotEmpty(teDTO.getTimesheetTaskDTO().getTicketNumber())) {
						ticketNumber = teDTO.getTimesheetTaskDTO().getTicketNumber();
					}
					if(StringUtils.isNotEmpty(teDTO.getHourlyType())) {
						hourlyType = teDTO.getHourlyType();
					}
					int count = 0;
					if(weekCountMap.get(taskId+"##"+ticketNumber+"##"+hourlyType+"##"+teDTO.getDate()) != null){
						count = weekCountMap.get(taskId+"##"+ticketNumber+"##"+hourlyType+"##"+teDTO.getDate())+1;
					}
					weekCountMap.put(taskId+"##"+ticketNumber+"##"+hourlyType+"##"+teDTO.getDate(),count);
					taskIdWithRef = taskId+"##"+ticketNumber+"##"+hourlyType+"##"+count;
				}
				LinkedList<TimesheetEntriesDTO> tsDTOList = entriesMap.get(taskIdWithRef);
				if (tsDTOList == null) {
					tsDTOList = new LinkedList<>();
					tsDTOList.add(teDTO);
				} else {
					tsDTOList.add(teDTO);
				}
				valuesMap.put(taskIdWithRef + "" + teDTO.getDate(), taskIdWithRef);
				entriesMap.put(taskIdWithRef, tsDTOList);
				taskNameMap.put(taskIdWithRef, taskName);
				taskTypeMap.put(taskIdWithRef, taskType);
				ticketNumberMap.put(taskIdWithRef, ticketNumber);
			}
		} else {
			String taskIdWithRef = "";
			for (WeekDaysDTO weekDay : weekDaysList) {
				TimesheetEntriesDTO teDTO = new TimesheetEntriesDTO();
				teDTO.setDayOfTheDate(weekDay.getDayOfTheDate());
				teDTO.setDate(weekDay.getDate());
				teDTO.setEditable(weekDay.isEditable());
				teDTO.setHours("HH:MM");
				LinkedList<TimesheetEntriesDTO> tsDTOList = entriesMap.get(taskIdWithRef);
				if (tsDTOList == null) {
					tsDTOList = new LinkedList<>();
					tsDTOList.add(teDTO);
				} else {
					tsDTOList.add(teDTO);
				}
				entriesMap.put(taskIdWithRef, tsDTOList);
			}
		}

		if (valuesMap != null && !valuesMap.isEmpty()) {
			for (String taskIdWithRef : entriesMap.keySet()) {
				for (WeekDaysDTO weekDay : weekDaysList) {
					if (valuesMap.get(taskIdWithRef + "" + weekDay.getDate()) == null) {
						//ProjectTasks pt = projectTasksService.getTasksId(taskId.toString());
						String[] taskSplitter = taskIdWithRef.split("##");
						if((taskSplitter != null && taskSplitter.length < 0) || (taskSplitter != null && taskSplitter.length >= 0 && StringUtils.isEmpty(taskSplitter[0])))
							continue;
						TimesheetEntriesDTO teDTO = new TimesheetEntriesDTO();
						teDTO.setDayOfTheDate(weekDay.getDayOfTheDate());
						teDTO.setDate(weekDay.getDate());
						teDTO.setEditable(weekDay.isEditable());
						teDTO.setHours("HH:MM");
						TimesheetTaskDTO timesheetTaskDTO = new TimesheetTaskDTO();
						timesheetTaskDTO.setId(Long.parseLong(taskSplitter[0]));
						if (StringUtils.isNotEmpty(taskTypeMap.get(taskIdWithRef))) {
							timesheetTaskDTO.setTaskType(taskTypeMap.get(taskIdWithRef));
						}
						if (StringUtils.isNotEmpty(ticketNumberMap.get(taskIdWithRef))) {
							timesheetTaskDTO.setTicketNumber(ticketNumberMap.get(taskIdWithRef));
						}
						if (StringUtils.isNotEmpty(taskNameMap.get(taskIdWithRef))) {
							timesheetTaskDTO.setTaskName(taskNameMap.get(taskIdWithRef));
						}
						teDTO.setTimesheetTaskDTO(timesheetTaskDTO);
						LinkedList<TimesheetEntriesDTO> tsDTOList = entriesMap.get(taskIdWithRef);
						if (tsDTOList == null) {
							tsDTOList = new LinkedList<>();
							tsDTOList.add(teDTO);
						} else {
							tsDTOList.add(teDTO);
						}
						entriesMap.put(taskIdWithRef, tsDTOList);
					}
				}

			}
		}

		LinkedHashMap<String, LinkedList<TimesheetEntriesDTO>> freshEntryMap = new LinkedHashMap<>();
		if (entriesMap != null && !entriesMap.isEmpty()) {
			for (String taskIdWithRef : entriesMap.keySet()) {
				LinkedList<TimesheetEntriesDTO> tedList = entriesMap.get(taskIdWithRef);
				TreeMap<Date, TimesheetEntriesDTO> freshTsDTOMap = new TreeMap<>();
				for (TimesheetEntriesDTO ted : tedList) {
					freshTsDTOMap.put(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
							ted.getDate().replaceAll("/", "-")), ted);
				}
				freshEntryMap.put(taskIdWithRef, new LinkedList<TimesheetEntriesDTO>(freshTsDTOMap.values()));
			}
		}
		return freshEntryMap;
	}
	
	@PreAuthorize("hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_FILL_MEMBER_TIMESHEETS')")
	@RequestMapping(value = "/saveEmployeeTimesheetEntries", method = RequestMethod.POST)
	public ModelAndView saveEmployeeTimesheetEntriesWeb(EmployeeTimesheetEntryDetailsDTO tedDTO, HttpServletRequest request)
			throws Exception {
		ModelAndView model = new ModelAndView();
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Candidate candidate = (Candidate) SourceLeadSessionManager.getSessionAttribute(request, "candidate");
		Long shiftId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "shiftId");
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		if (loginUser != null && orgLocations != null) {
			orgLocations = orgLocationService.getOrgLocations(orgLocations.getId());
			loginUser = userService.getUserById(loginUser.getId());
			candidate = candidateService.getCandidateByConsultantUserId(loginUser.getId(), orgLocations.getId());
		}
		
		try {
				tedDTO = saveEmployeeTimesheetEntries(tedDTO, request);
				if(tedDTO.getProjectMembersList() == null || tedDTO.getProjectMembersList().isEmpty()) {
					model.addObject("NORECORDSFOUND", true);
					model.addObject("employeeTimesheetEntryDetailsDTO", tedDTO);
					model.setViewName("timesheet/timesheetInvoiceList");
				}else {
					List<ProjectMembersDTO> freshProjectMembersList = new LinkedList<>();
					List<ProjectMembersDTO>  allProjectMembersList = new LinkedList<>();
					List<ProjectMembersDTO> submittedProjectMembersList = new LinkedList<>();
					List<ProjectMembersDTO>  attProjectMemberIdsList = new LinkedList<>();
					List<Long>  oldSubmittedPmIdsList = new LinkedList<>();
					Boolean isRejected = false;
					Boolean isSUBMITTED = false;
					Boolean isAPPROVED = false;
					String combinedStatus = "";
					boolean combinedMonEditable =  false;
					boolean combinedTueEditable =  false;
					boolean combinedWedEditable =  false;
					boolean combinedThuEditable =  false;
					boolean combinedFriEditable =  false;
					boolean combinedSatEditable =  false;
					boolean combinedSunEditable =  false;
					boolean submitButtonVisible = false;
					for(ProjectMembersDTO pmDTO :tedDTO.getProjectMembersList()) {
						boolean monEditable = tedDTO.getStatusMap().get(pmDTO.getId()).getMonEditable();
						boolean tueEditable = tedDTO.getStatusMap().get(pmDTO.getId()).getTueEditable();
						boolean wedEditable = tedDTO.getStatusMap().get(pmDTO.getId()).getWedEditable();
						boolean thuEditable = tedDTO.getStatusMap().get(pmDTO.getId()).getThuEditable();
						boolean friEditable = tedDTO.getStatusMap().get(pmDTO.getId()).getFriEditable();
						boolean satEditable = tedDTO.getStatusMap().get(pmDTO.getId()).getSatEditable();
						boolean sunEditable = tedDTO.getStatusMap().get(pmDTO.getId()).getSunEditable();
						if(monEditable && !combinedMonEditable) {
							combinedMonEditable = monEditable;
						}
						if(tueEditable && !combinedTueEditable) {
							combinedTueEditable = tueEditable;
						}
						if(wedEditable && !combinedWedEditable) {
							combinedWedEditable = wedEditable;
						}
						if(thuEditable && !combinedThuEditable) {
							combinedThuEditable = thuEditable;
						}
						if(friEditable && !combinedFriEditable) {
							combinedFriEditable = friEditable;
						}
						if(satEditable && !combinedSatEditable) {
							combinedSatEditable = satEditable;
						}
						if(sunEditable && !combinedSunEditable) {
							combinedSunEditable = sunEditable;
						}
						
						if(!submitButtonVisible) {
							submitButtonVisible = tedDTO.getStatusMap().get(pmDTO.getId()).getSubmitButtonVisible();
						}
						if (tedDTO.getStatusMap().get(pmDTO.getId()) == null || tedDTO.getStatusMap().get(pmDTO.getId()).getTimesheetStatus().equalsIgnoreCase("REJECTED") 
								|| tedDTO.getStatusMap().get(pmDTO.getId()).getTimesheetStatus().equalsIgnoreCase("DRAFT")) {
							isRejected = true;
							combinedStatus = "REJECTED";
						}
						else if (!isRejected 
								&& tedDTO.getStatusMap().get(pmDTO.getId()).getTimesheetStatus().equalsIgnoreCase("SUBMITTED")) {
							isSUBMITTED = true;
							combinedStatus = "SUBMITTED";
							
						}
						else if (!isRejected && !isSUBMITTED
								&& tedDTO.getStatusMap().get(pmDTO.getId()).getTimesheetStatus().equalsIgnoreCase("APPROVED")) {
							isAPPROVED = true;
							combinedStatus = "APPROVED";
						} else if(!isRejected && !isSUBMITTED && !isAPPROVED && tedDTO.getStatusMap().get(pmDTO.getId()).getTimesheetStatus().equalsIgnoreCase("NEW")) {
							combinedStatus = "NEW";
						}
						
						
						if(tedDTO.getStatusMap().get(pmDTO.getId()) == null || tedDTO.getStatusMap().get(pmDTO.getId()).getTimesheetStatus().equalsIgnoreCase("REJECTED") 
								|| tedDTO.getStatusMap().get(pmDTO.getId()).getTimesheetStatus().equalsIgnoreCase("DRAFT") || tedDTO.getStatusMap().get(pmDTO.getId()).getTimesheetStatus().equalsIgnoreCase("NEW") ) {
							
							if(tedDTO.getStatusMap().get(pmDTO.getId()).getTimesheetStatus().equalsIgnoreCase("NEW") && (
									StringUtils.isNotEmpty(pmDTO.getIsProjectForTimeSheets()) 
									                          && pmDTO.getIsProjectForTimeSheets().equalsIgnoreCase("Y"))
							  ) {
								freshProjectMembersList.add(pmDTO);
								allProjectMembersList.add(pmDTO);
							}
							if(!tedDTO.getStatusMap().get(pmDTO.getId()).getTimesheetStatus().equalsIgnoreCase("NEW")) {
								freshProjectMembersList.add(pmDTO);
								allProjectMembersList.add(pmDTO);
								attProjectMemberIdsList.add(pmDTO);
								oldSubmittedPmIdsList.add(pmDTO.getId());
							}
							
						}else{
							submittedProjectMembersList.add(pmDTO);
							oldSubmittedPmIdsList.add(pmDTO.getId());
							allProjectMembersList.add(pmDTO);
							attProjectMemberIdsList.add(pmDTO);
						}
						
					}
					tedDTO.getProjectMembersList().clear();
					tedDTO.setOldSubmittedProjectMembersList(oldSubmittedPmIdsList);
					tedDTO.setProjectMembersList(freshProjectMembersList);
					tedDTO.setSubmittedProjectMembersList(submittedProjectMembersList);
					tedDTO.setAllProjectMembersList(allProjectMembersList);
					tedDTO.setAttProjectMembersList(attProjectMemberIdsList);
					if(StringUtils.isNotEmpty(combinedStatus) && (combinedStatus.equalsIgnoreCase("SUBMITTED") || combinedStatus.equalsIgnoreCase("APPROVED"))){
						tedDTO.setSaveButtonVisible(false);
					}else {
						tedDTO.setSaveButtonVisible(true);
					}
					tedDTO.setSubmitButtonVisible(submitButtonVisible);
					int workingDaysPerWeek = 0;
					if(StringUtils.isNotEmpty(orgLocations.getWeekOffDays())) {
						 String[] strArray = orgLocations.getWeekOffDays().split(",");
					     List<String> weekOffs = new ArrayList<String>(Arrays.asList(strArray));
						if(combinedMonEditable && !weekOffs.contains("Mon")) {
							workingDaysPerWeek++;
						}
		                if(combinedTueEditable && !weekOffs.contains("Tue")) {
		                	workingDaysPerWeek++;
						}
		                if(combinedWedEditable && !weekOffs.contains("Wed")) {
		                	workingDaysPerWeek++;
						}
		                if(combinedThuEditable && !weekOffs.contains("Thu")) {
		                	workingDaysPerWeek++;
						}
		                if(combinedFriEditable && !weekOffs.contains("Fri")) {
		                	workingDaysPerWeek++;
						}
		                if(combinedSatEditable && !weekOffs.contains("Sat")) {
		                	workingDaysPerWeek++;
						}
		                if(combinedSunEditable && !weekOffs.contains("Sun")) {
		                	workingDaysPerWeek++;
						}
					}else {
						workingDaysPerWeek = 5;
					}
					
					Integer avgWorkingHoursPerDay = 8;
					Employees employee = employeesService.getEmployeeByUserAndOrgLocationId(loginUser.getId(),orgLocations.getId());
					if(employee == null) {
						avgWorkingHoursPerDay = orgLocations.getAverageWorkingHours();
					}else if(StringUtils.isNotEmpty(employee.getDailyHours())){
						String [] hrsArray = employee.getDailyHours().split(":");
						avgWorkingHoursPerDay = Integer.parseInt(hrsArray[0]);
					}
					Map<String,List<EmployeeLeaveDTO>> empLeaveMap = getEmpLeaveList(tedDTO.getWeekDays(),tedDTO.getCurrentTimeSubFrequency(), orgLocations,loginUser,candidate,shiftId);
					tedDTO.setEmpLeaveMap(empLeaveMap);	
							
					Integer minimumWorkingHoursPerWeek = workingDaysPerWeek * avgWorkingHoursPerDay;
					model.addObject("minimumWorkingHoursPerWeek", minimumWorkingHoursPerWeek);
					
					String isTimesheetAvgHoursRequired = orgLocations.getIsTimesheetAvgHoursRequired();
	                String timesheetAvgHoursValidation = "N";
	                if(StringUtils.isNotEmpty(isTimesheetAvgHoursRequired) && isTimesheetAvgHoursRequired.equalsIgnoreCase("Y")) {
	                	timesheetAvgHoursValidation = "Y";
	                }
					model.addObject("timesheetAvgHoursValidation", timesheetAvgHoursValidation);
					if(allProjectMembersList == null || allProjectMembersList.isEmpty()) {
						model.addObject("NORECORDSFOUND", true);
						model.addObject("employeeTimesheetEntryDetailsDTO", tedDTO);
						model.setViewName("timesheet/timesheetInvoiceList");
					}else {
						model.addObject("employeeTimesheetEntryDetailsDTO", tedDTO);
						model.setViewName("timesheet/timesheetInvoiceList");
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
			model.addObject("AWKWARD", true);
			model.setViewName("timesheet/timesheetInvoiceList");
		}
		return model;
	}
	
	private void saveEmployeeTimesheetEntriesPerProjectMemberId(Long projectMemberId,String recordStatus,String weekComment,List<FileInfo> fileInfoList,LinkedList<TimesheetEntriesDTO> entriesDTOList,Map<String,String> timeSubFrequencies, String currentTimeFrequency,String currentTimeSubFrequency,String status,OrgLocations loginOrgLocation,User loginUser,Candidate candidate) throws Exception{
		ProjectMembers projectMember = null;
		String savingType = "user";
		if (projectMemberId != null) {
			projectMember = projectMembersService.getProjectMemberById(projectMemberId);
		
		Date frequencyStartDate = null;
		Date frequencyEndDate = null;
		if (status != null && (status.equalsIgnoreCase("Draft") || status.equalsIgnoreCase("Submitted"))) {
			Date startDate = null;
			Date endDate = null;
			if (StringUtils.isNotEmpty(currentTimeFrequency)) {
				String[] subfrequency = currentTimeFrequency.split("To");
				String subfrstartDate = subfrequency[0].trim();
				String subfrEndDate = subfrequency[1].trim();
				frequencyStartDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(), subfrstartDate);
				frequencyEndDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(), subfrEndDate);
			}

			if (StringUtils.isNotEmpty(currentTimeSubFrequency)) {
				String[] frequency = currentTimeSubFrequency.split("To");
				String frstartDate = frequency[0].trim();
				String frEndDate = frequency[1].trim();
				startDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(), frstartDate);
				endDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(), frEndDate);
			}
			Long total = 0l;
			Long totalBillableHours = 0l;
			Long totalNonBillableHours = 0l;
			TimesheetSubmissions timesheetSubmissions = timesheetSubmissionsService
					.getAllCandidateTimesheetSubmissionsByProjectIdAndMasterCandidateId(startDate, endDate,
							projectMember.getProjects().getId(), projectMember.getMasterCandidateId().getId());
			HashSet<SubmissionApproverHierarchy> freshSaHierarchyList = new HashSet<SubmissionApproverHierarchy>();
		    if (timesheetSubmissions != null) {
					List<SubmissionApproverHierarchy> saHierarchyList = submissionApproverHierarchyService
							.getSubmissionApproverHierarchyBySubmissionId(timesheetSubmissions.getId());
					List<ProjectApproverHierarchy> hierarchyList = projectApproverHierarchyService.getApproverUsersByProjectMemberMasterCandidateIdAndProjectId(
							                                                              projectMember.getMasterCandidateId().getId(),projectMember.getProjects().getId());
					if (saHierarchyList == null || saHierarchyList.isEmpty()) {
						ArrayList<TimesheetSubmissions> timesheetSubmissionsList = new ArrayList<TimesheetSubmissions>();
						if (hierarchyList != null && !hierarchyList.isEmpty()) {
							for (ProjectApproverHierarchy hierarchy : hierarchyList) {
								SubmissionApproverHierarchy saHierarchy = new SubmissionApproverHierarchy();
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
								
								saHierarchy.setSubmissionStatus(Submission_Status.getNameByValue(status));
								saHierarchy
										.setSubmissionApprovalStatus(Submission_ApprovalStatus.getNameByValue("new"));
								saHierarchy.setCreatedDate(timesheetSubmissions.getCreatedDate());
								if (frequencyStartDate != null)
									saHierarchy.setFrequencyStartDate(frequencyStartDate);
								if (frequencyEndDate != null)
									saHierarchy.setFrequencyEndDate(frequencyEndDate);
								saHierarchy.setCreatedBy(timesheetSubmissions.getCreatedBy());
								saHierarchy.setUpdatedDate(timesheetSubmissions.getUpdatedDate());
								saHierarchy.setUpdatedBy(timesheetSubmissions.getUpdatedBy());
								if(StringUtils.isNotEmpty(recordStatus) && recordStatus.equalsIgnoreCase("updated")) {
									saHierarchy.setStatus(Constants.RECORD_ACTIVE + "");
								}else {
									saHierarchy.setStatus(Constants.RECORD_INACTIVE + "");
								}
								
								freshSaHierarchyList.add(saHierarchy);
							}
							if(StringUtils.isNotEmpty(recordStatus) && recordStatus.equalsIgnoreCase("updated")) {
								timesheetSubmissions.setStatus(Constants.RECORD_ACTIVE + "");
							}else {
								timesheetSubmissions.setStatus(Constants.RECORD_INACTIVE + "");
							}
							timesheetSubmissions.setApproverHierarchyList(freshSaHierarchyList);
						}
						timesheetSubmissionsList.add(timesheetSubmissions);
						timesheetSubmissionsService.saveAllTimesheetSubmissions(timesheetSubmissionsList);
					}else {
						Set<Long> existedIds = saHierarchyList.stream().map(sah -> sah.getHierarchyId().getId()).collect(Collectors.toSet());
                        List<ProjectApproverHierarchy> freshProjectApproverHierarchyList = hierarchyList.stream().filter(pah->!existedIds.contains(pah.getId())).collect(Collectors.toList());
                        if(freshProjectApproverHierarchyList != null && !freshProjectApproverHierarchyList.isEmpty()) {
                        	for (ProjectApproverHierarchy hierarchy : freshProjectApproverHierarchyList) {
    							SubmissionApproverHierarchy saHierarchy = new SubmissionApproverHierarchy();
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
								
    							saHierarchy.setSubmissionStatus(Submission_Status.getNameByValue(status));
    							saHierarchy
    									.setSubmissionApprovalStatus(Submission_ApprovalStatus.getNameByValue("new"));
    							saHierarchy.setCreatedDate(timesheetSubmissions.getCreatedDate());
    							if (frequencyStartDate != null)
    								saHierarchy.setFrequencyStartDate(frequencyStartDate);
    							if (frequencyEndDate != null)
    								saHierarchy.setFrequencyEndDate(frequencyEndDate);
    							saHierarchy.setCreatedBy(timesheetSubmissions.getCreatedBy());
    							saHierarchy.setUpdatedDate(timesheetSubmissions.getUpdatedDate());
    							saHierarchy.setUpdatedBy(timesheetSubmissions.getUpdatedBy());
    							if(StringUtils.isNotEmpty(recordStatus) && recordStatus.equalsIgnoreCase("updated")) {
    								saHierarchy.setStatus(Constants.RECORD_ACTIVE + "");
    							}else {
    								saHierarchy.setStatus(Constants.RECORD_INACTIVE + "");
    							}
    							saHierarchyList.add(saHierarchy);
    						}
                        }
					}
					
					SubmissionApproverHierarchy sahActivity = null;
					for (SubmissionApproverHierarchy saHierarchy : saHierarchyList) {
						Submission_Status currentSahStatus = saHierarchy.getSubmissionStatus();
						if (StringUtils.isNotEmpty(recordStatus) && recordStatus.equalsIgnoreCase("updated") 
								&&currentSahStatus != null && currentSahStatus.equals(Submission_Status.SUBMITTED)
								&& StringUtils.isNotEmpty(savingType) && !savingType.equalsIgnoreCase("user")
								&& StringUtils.isNotEmpty(status) && status.equalsIgnoreCase("Draft")) {
							status = "submitted";
						}

						if (StringUtils.isNotEmpty(recordStatus) && recordStatus.equalsIgnoreCase("updated") 
								&& StringUtils.isNotEmpty(saHierarchy.getSubmissionStatus().getValue())
								&& saHierarchy.getSubmissionStatus().getValue().equalsIgnoreCase("approved")) {
							status = "approved";
							saHierarchy.setSubmissionStatus(Submission_Status.getNameByValue("approved"));
						} else {
							saHierarchy.setSubmissionStatus(Submission_Status.getNameByValue(status));
						}

						if (StringUtils.isNotEmpty(recordStatus) && recordStatus.equalsIgnoreCase("updated") 
								&& StringUtils.isNotEmpty(status) && status.equalsIgnoreCase("Draft"))
							saHierarchy.setSubmissionApprovalStatus(Submission_ApprovalStatus.getNameByValue("new"));

						if (StringUtils.isNotEmpty(recordStatus) && recordStatus.equalsIgnoreCase("updated") 
								&& StringUtils.isNotEmpty(status) && status.equalsIgnoreCase("Submitted"))
							saHierarchy.setSubmissionApprovalStatus(
									Submission_ApprovalStatus.getNameByValue("submitted for approval"));
                        if(StringUtils.isNotEmpty(recordStatus) && recordStatus.equalsIgnoreCase("removed") ) {
                        	saHierarchy.setSubmissionStatus(Submission_Status.NEW);
                        	saHierarchy.setSubmissionApprovalStatus(Submission_ApprovalStatus.NEW);
                        }
						saHierarchy.setUpdatedDate(CommonUtil.currentDate());
						saHierarchy.setUpdatedBy(loginUser.getUserCode());

						if (frequencyStartDate != null)
							saHierarchy.setFrequencyStartDate(frequencyStartDate);
						if (frequencyEndDate != null)
							saHierarchy.setFrequencyEndDate(frequencyEndDate);
						sahActivity = saHierarchy;
						if(StringUtils.isNotEmpty(recordStatus) && recordStatus.equalsIgnoreCase("removed")) {
							sahActivity.setStatus(Constants.RECORD_INACTIVE + "");
						}
						freshSaHierarchyList.add(saHierarchy);
					}
					submissionApproverHierarchyService.saveAllSubmissionApproverHierarchy(new ArrayList<>(freshSaHierarchyList));
					
					String activityStatus = null;
                   if (StringUtils.isNotEmpty(status) && status.equalsIgnoreCase("Draft")){
                   	activityStatus ="Draft"; 
					}else if (StringUtils.isNotEmpty(status) && status.equalsIgnoreCase("Submitted")){
						activityStatus ="Submitted"; 
					}
                   TimesheetActivities ta = new TimesheetActivities();
                   ta.setActivityStatus(activityStatus);
                   if(StringUtils.isNotEmpty(recordStatus) && recordStatus.equalsIgnoreCase("removed")) {
                	   ta.setStatus(Constants.RECORD_INACTIVE + "");
					}
                   timesheetActivityService.saveTimesheetActivity(ta);
                   
                   if(StringUtils.isEmpty(weekComment) && frequencyStartDate != null && frequencyEndDate != null) {
						String comment = timesheetSubmissionsService.getWeekCommentByCurrentFrequency(frequencyStartDate, frequencyEndDate, timesheetSubmissions.getProjects().getId(), timesheetSubmissions.getMasterCandidateId().getId());
						if(StringUtils.isNotEmpty(comment));
						timesheetSubmissions.setWeekcomments(comment);
					}else {
						timesheetSubmissions.setWeekcomments(weekComment);
					}
					timesheetSubmissions.setUpdatedDate(CommonUtil.currentDate());
					timesheetSubmissions.setUpdatedBy(loginUser.getUserCode());
					if(StringUtils.isNotEmpty(recordStatus) && recordStatus.equalsIgnoreCase("removed")) {
						timesheetSubmissions.setStatus(Constants.RECORD_INACTIVE + "");
					}
					timesheetSubmissionsService.updateTimesheetSubmissions(timesheetSubmissions);
					if(StringUtils.isNotEmpty(weekComment)  && frequencyStartDate != null && frequencyEndDate != null) {
						timesheetSubmissionsService.updateWeekCommentForAllSubmissions(weekComment,frequencyStartDate, frequencyEndDate, timesheetSubmissions.getProjects().getId(), timesheetSubmissions.getMasterCandidateId().getId());
					}

			} else {
				timesheetSubmissions = new TimesheetSubmissions();
				timesheetSubmissions.setStartDate(startDate);
				timesheetSubmissions.setEndDate(endDate);
				timesheetSubmissions.setProjectMembers(projectMember);
				timesheetSubmissions.setStatus(Constants.RECORD_ACTIVE + "");
				timesheetSubmissions.setCreatedDate(CommonUtil.currentDate());
				timesheetSubmissions.setCreatedBy(loginUser.getUserCode());
				timesheetSubmissions.setProjects(projectMember.getProjects());
				timesheetSubmissions.setMasterCandidateId(projectMember.getMasterCandidateId());
				timesheetSubmissions.setUpdatedDate(CommonUtil.currentDate());
				timesheetSubmissions.setUpdatedBy(loginUser.getUserCode());
				if(StringUtils.isEmpty(weekComment)  && frequencyStartDate != null && frequencyEndDate != null) {
					String comment = timesheetSubmissionsService.getWeekCommentByCurrentFrequency(frequencyStartDate, frequencyEndDate, timesheetSubmissions.getProjects().getId(), timesheetSubmissions.getMasterCandidateId().getId());
					if(StringUtils.isNotEmpty(comment));
					timesheetSubmissions.setWeekcomments(comment);
		        }else {
		        	timesheetSubmissions.setWeekcomments(weekComment);
		        }
				

				ArrayList<TimesheetSubmissions> timesheetSubmissionsList = new ArrayList<TimesheetSubmissions>();
				List<ProjectApproverHierarchy> hierarchyList = projectApproverHierarchyService
						.getApproverUsersByProjectMemberMasterCandidateIdAndProjectId(
								projectMember.getMasterCandidateId().getId(), projectMember.getProjects().getId());
				if (hierarchyList != null && !hierarchyList.isEmpty()) {
					for (ProjectApproverHierarchy hierarchy : hierarchyList) {
						SubmissionApproverHierarchy saHierarchy = new SubmissionApproverHierarchy();
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
						
						saHierarchy.setSubmissionStatus(Submission_Status.getNameByValue(status));
						saHierarchy.setSubmissionApprovalStatus(Submission_ApprovalStatus.getNameByValue("new"));
						saHierarchy.setCreatedDate(timesheetSubmissions.getCreatedDate());
						if (frequencyStartDate != null)
							saHierarchy.setFrequencyStartDate(frequencyStartDate);
						if (frequencyEndDate != null)
							saHierarchy.setFrequencyEndDate(frequencyEndDate);
						saHierarchy.setCreatedBy(timesheetSubmissions.getCreatedBy());
						saHierarchy.setUpdatedDate(timesheetSubmissions.getUpdatedDate());
						saHierarchy.setUpdatedBy(timesheetSubmissions.getUpdatedBy());
						saHierarchy.setStatus(Constants.RECORD_ACTIVE + "");
						freshSaHierarchyList.add(saHierarchy);
					}
					timesheetSubmissions.setApproverHierarchyList(freshSaHierarchyList);
				}
				timesheetSubmissionsList.add(timesheetSubmissions);
				timesheetSubmissionsService.saveAllTimesheetSubmissions(timesheetSubmissionsList);
			}
			// save mutliple attachment in timesheet submissison attachment.
		    
		    List<Long> oldFileDbIdList = timesheetSubmissionsDocumentsService.getAllTimesheetSubmissionsDocumentIdsByProjectIdMasterCandidateIdAndFrequencyStartDateEndDate(timesheetSubmissions.getMasterCandidateId().getId(), timesheetSubmissions.getProjects().getId(), frequencyStartDate, frequencyEndDate);
			List<Long> freshFileIdList = new ArrayList<>();
			if (fileInfoList != null && !fileInfoList.isEmpty()) {
				for (FileInfo file : fileInfoList) {
					if (StringUtils.isEmpty(file.getFileDbId()) && StringUtils.isNotEmpty(file.getFileURL())) {
						FileUploadUtil.moveFile(staticPath, file, "timesheetDocuments");
						TimesheetSubmissionsDocuments tsd = new TimesheetSubmissionsDocuments();
						tsd.setTimesheetSubmissions(timesheetSubmissions);
						tsd.setFileUrl(file.getFileURL());
						tsd.setFileName(file.getFileName());
						tsd.setFileContentType(file.getContentType());
						tsd.setStatus(Constants.CANDIDATE_ACTIVE);
						tsd.setCreatedDate(CommonUtil.currentDate());
						tsd.setCreatedBy(loginUser.getUserCode());
						tsd.setUpdatedDate(CommonUtil.currentDate());
						tsd.setUpdatedBy(loginUser.getUserCode());
						tsd = timesheetSubmissionsDocumentsService.addTimesheetSubmissionsDocuments(tsd);
					    freshFileIdList.add(tsd.getId());
					}else if(StringUtils.isNotEmpty(file.getFileDbId())) {
						  freshFileIdList.add(Long.parseLong(file.getFileDbId()));
					}
				}
			}
			oldFileDbIdList.removeAll(freshFileIdList);
			if (oldFileDbIdList != null && !oldFileDbIdList.isEmpty()) {
				for (Long id : oldFileDbIdList) {
					TimesheetSubmissionsDocuments timesheetSubmissionsDocuments  = timesheetSubmissionsDocumentsService.getTimesheetSubmissionsDocuments(id);
					timesheetSubmissionsDocuments.setDocFile(null);
					timesheetSubmissionsDocuments.setFileContentType(null);
					timesheetSubmissionsDocuments.setFileName(null);
					timesheetSubmissionsDocuments.setStatus(Constants.CANDIDATE_IN_ACTIVE);
				    timesheetSubmissionsDocumentsService.addTimesheetSubmissionsDocuments(timesheetSubmissionsDocuments);
				}
			}
			List<Long> oldDbIdList = timesheetDayEntriesDao
					.getAllTimeSheetDayEntryIdsBySubmissionId(timesheetSubmissions.getId());
			List<Long> freshIdList = new ArrayList<>();
			LinkedList<TimeSheetDayEntries> entriesDayList = new LinkedList<>();
			if (entriesDTOList != null && !entriesDTOList.isEmpty()) {
				for (TimesheetEntriesDTO teDTO : entriesDTOList) {
					if (StringUtils.isEmpty(teDTO.getHours()) || ((teDTO.getTimesheetTaskDTO() == null)
							|| (teDTO.getTimesheetTaskDTO() == null && teDTO.getTimesheetTaskDTO().getId() == null)))
						continue;
					ProjectTasks projectTasks = projectTasksService
							.getTasksId(teDTO.getTimesheetTaskDTO().getId().toString());
					String taskType = teDTO.getTimesheetTaskDTO().getTaskType();
					String billableHours = "00:00";
					String nonBillableHours = "00:00";
					TimeSheetDayEntries timesheetDayEntries = new TimeSheetDayEntries();
					if (teDTO.getId() != null) {
						timesheetDayEntries.setId(teDTO.getId());
					}
					timesheetDayEntries.setProjectTask(projectTasks);
					timesheetDayEntries.setTimesheetSubmissions(timesheetSubmissions);
					total = getTotalHoursFormDayHours(teDTO.getHours(), total);
					if (taskType.equalsIgnoreCase("Billable")) {
						if (StringUtils.isNotEmpty(teDTO.getHours())) {
							billableHours = teDTO.getHours();
							if (!teDTO.getHours().equalsIgnoreCase("00:00")) {
								timesheetDayEntries.setIsManuallyAdded(Constants.YES);
							} else {
								timesheetDayEntries.setIsManuallyAdded(Constants.NO);
							}

						} else {
							timesheetDayEntries.setIsManuallyAdded(Constants.NO);
						}
						totalBillableHours = getTotalHoursFormDayHours(billableHours, totalBillableHours);
						timesheetDayEntries.setBillableHours(billableHours);
						timesheetDayEntries.setTotalHours(billableHours);
						timesheetDayEntries.setTaskType("Billable");
					} else if (taskType.equalsIgnoreCase("Non-Billable")) {
						if (StringUtils.isNotEmpty(teDTO.getHours())) {
							nonBillableHours = teDTO.getHours();
							if (!teDTO.getHours().equalsIgnoreCase("00:00")) {
								timesheetDayEntries.setIsManuallyAdded(Constants.YES);
							} else {
								timesheetDayEntries.setIsManuallyAdded(Constants.NO);
							}
						} else {
							timesheetDayEntries.setIsManuallyAdded(Constants.NO);
						}
						totalNonBillableHours = getTotalHoursFormDayHours(nonBillableHours, totalNonBillableHours);
						timesheetDayEntries.setNonBillableHours(nonBillableHours);
						timesheetDayEntries.setTotalHours(nonBillableHours);
						timesheetDayEntries.setTaskType("Non-Billable");
					}
					timesheetDayEntries.setTicketNumber(teDTO.getTicketNumber());
					
					if (teDTO.getId() == null && StringUtils.isEmpty(teDTO.getHourlyType())) {
						timesheetDayEntries.setHourlyType("Regular");
					}else if(StringUtils.isNotEmpty(teDTO.getHourlyType())) {
						timesheetDayEntries.setHourlyType(teDTO.getHourlyType());
					}
					timesheetDayEntries.setComments(teDTO.getComments());
					timesheetDayEntries.setTimesheetDate(
							commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(), teDTO.getDate()));
					timesheetDayEntries.setStatus(1);
					timesheetDayEntries.setCreatedDate(CommonUtil.currentDate());
					timesheetDayEntries.setUpdatedBy(loginUser.getCreatedBy());
					timesheetDayEntries.setUpdatedDate(CommonUtil.currentDate());
					timesheetDayEntries.setCreatedBy(loginUser.getCreatedBy());
					entriesDayList.add(timesheetDayEntries);
				}

				if (entriesDayList != null && !entriesDayList.isEmpty()) {
					for (TimeSheetDayEntries timesheetdayEntries : entriesDayList) {
						timesheetdayEntries = timesheetDayEntriesDao.saveTimesheetDayEntries(timesheetdayEntries);
						freshIdList.add(timesheetdayEntries.getId());
					}
				}

			}
			oldDbIdList.removeAll(freshIdList);

			if (oldDbIdList != null && !oldDbIdList.isEmpty()) {
				for (Long removedEntryId : oldDbIdList) {
					timesheetDayEntriesDao.deleteTimeSheetDayEntries(removedEntryId);
				}
			}
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
				if (StringUtils.isNotEmpty(actualNonBillableMinutesStr) && actualNonBillableMinutesStr.length() == 1)
					nonBillableStartWithZero = "0";
				String actualNonBillableTimeString = actualNonBillableHours + "." + nonBillableStartWithZero
						+ actualNonBillableMinutes;
				timesheetSubmissions.setNonBillableHours(actualNonBillableTimeString);
				timesheetSubmissionsService.updateTimesheetSubmissions(timesheetSubmissions);

				// send mail to assigned manager at time sheet submission
				if (!actualTimeString.equalsIgnoreCase("0.0")) {
					if (!"Draft".equalsIgnoreCase(status) && projectMember != null
							&& projectMember.getProjects() != null && frequencyStartDate != null
							&& frequencyEndDate != null) {
						    timesheetEmailUtil.sendEmailToManagerAtTimesheetSubmissionsNew(projectMember, projectMember.getMasterCandidateId().getId(),
								projectMember.getProjects().getId(), frequencyStartDate, frequencyEndDate, loginUser,
								loginOrgLocation, loginOrgLocation.getOrgCode());
					}
				}
			}
			if ((StringUtils.isNotEmpty(recordStatus) && !recordStatus.equalsIgnoreCase("removed")  || StringUtils.isEmpty(recordStatus))&& status != null && status.equalsIgnoreCase("Submitted")) {
				if (timeSubFrequencies != null && !timeSubFrequencies.isEmpty()) {
					LinkedList<String> tabDates = new LinkedList<String>(timeSubFrequencies.keySet());
					if (tabDates != null && !tabDates.isEmpty()) {
						for (String tabDate : tabDates) {
							tabDate = tabDate.replace("\"", "");
							String[] words = tabDate.split("To");
							Date actualStartDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(),
									words[0].replaceAll("/", "-"));
							Date actualEndDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(),
									words[1].replaceAll("/", "-"));
							List<TimesheetSubmissions> timesheetSubmissionsList = timesheetSubmissionsService
									.getAllCandidateTimesheetSubmissionsByProjectIdAndMasterCandidateIdForUpdation(
											actualStartDate, actualEndDate, projectMember.getProjects().getId(),
											projectMember.getMasterCandidateId().getId());
							if (timesheetSubmissionsList != null && !timesheetSubmissionsList.isEmpty()) {
								for (TimesheetSubmissions ts : timesheetSubmissionsList) {
									List<SubmissionApproverHierarchy> sahList = submissionApproverHierarchyService
											.getSubmissionApproverHierarchyBySubmissionId(ts.getId());
									if (sahList != null) {
										for (SubmissionApproverHierarchy sah : sahList) {
											if (sah != null && sah.getId() != null) {
												if (frequencyStartDate != null)
													sah.setFrequencyStartDate(frequencyStartDate);
												if (frequencyEndDate != null)
													sah.setFrequencyEndDate(frequencyEndDate);
												sah.setSubmissionStatus(Submission_Status.SUBMITTED);
												sah.setSubmissionApprovalStatus(
														Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL);
												submissionApproverHierarchyService.addSubmissionApproverHierarchy(sah);
											}
										}
									}
								}

							}/* else {
								insertNewTimesheetRecordByProjectMemberAndDates(projectMember, actualStartDate,
										actualEndDate, frequencyStartDate, frequencyEndDate, loginUser, candidate);
							}*/

						}
					}

				}
			}
	      }
		}
	}
	private EmployeeTimesheetEntryDetailsDTO saveEmployeeTimesheetEntries(EmployeeTimesheetEntryDetailsDTO tedDTO, HttpServletRequest request)throws Exception {

			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
					"orgLocations");
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			Candidate candidate = (Candidate) SourceLeadSessionManager.getSessionAttribute(request, "candidate");
			if (loginUser != null && loginOrgLocation != null) {
				loginOrgLocation = orgLocationService.getOrgLocations(loginOrgLocation.getId());
				loginUser = userService.getUserById(loginUser.getId());
				candidate = candidateService.getCandidateByConsultantUserId(loginUser.getId(), loginOrgLocation.getId());
			}
	
			TimesheetExpenseEntryDetailsUtilDTO eedDTO = new TimesheetExpenseEntryDetailsUtilDTO();
			eedDTO.setUserId(loginUser.getId());
			eedDTO.setLocationCode(loginOrgLocation.getLocationCode());
			eedDTO.setTimeSheetsFor(tedDTO.getTimesheetFor());
			eedDTO.setMasterCandidatesId(tedDTO.getMasterCandidateId());
			eedDTO.setCurrentTimeFrequency(tedDTO.getCurrentTimeFrequency());
			eedDTO.setCurrentTimeSubFrequency(tedDTO.getCurrentTimeSubFrequency());
			eedDTO.setInvoiceFrequency(tedDTO.getInvoiceFrequency());
			tedDTO  =  getEmployeeTimesheetSubFrequencyListByCurrentFrequency(eedDTO, tedDTO, loginOrgLocation,candidate, request);
			String status = "";
			EmployeeTimesheetSubmissionDTO tsDTO = null;
			if (tedDTO.getTimesheetSubmission() != null) {
				tsDTO = tedDTO.getTimesheetSubmission();
			}
			if (tsDTO != null && StringUtils.isNotEmpty(tsDTO.getTimesheetStatus())) {
				status = tsDTO.getTimesheetStatus();
			} else {
				status = "Draft";
			}
			LinkedHashSet<Long> freshPmIdList = new LinkedHashSet<>();
			List<EmployeeTimesheetFileInfoDTO> timesheetAttachments = tedDTO.getTimsheetAttachments();
			Map<Long,LinkedList<TimesheetEntriesDTO>> timesheetEntriesMap = new LinkedHashMap<>();
			if(tsDTO.getTimesheetEntries() != null && !tsDTO.getTimesheetEntries().isEmpty()) {
				for(TimesheetEntriesDTO teDTO :tsDTO.getTimesheetEntries()) {
					if(StringUtils.isNotEmpty(teDTO.getTimesheetStatus()) && (teDTO.getTimesheetStatus().equalsIgnoreCase("APPROVED") || teDTO.getTimesheetStatus().equalsIgnoreCase("SUBMITTED")))
					{
						freshPmIdList.add(teDTO.getProjectMemberId());
						continue;
					}
						
					Long projectMemberId = teDTO.getProjectMemberId();
					LinkedList<TimesheetEntriesDTO> tsDTOList = timesheetEntriesMap.get(projectMemberId);
					if (tsDTOList == null) {
						tsDTOList = new LinkedList<>();
						tsDTOList.add(teDTO);
					} else {
						tsDTOList.add(teDTO);
					}
					timesheetEntriesMap.put(projectMemberId, tsDTOList);
				}
			}
		     List<Long> oldPmDbIdList = tedDTO.getOldSubmittedProjectMembersList();
		   
			if(timesheetEntriesMap != null && !timesheetEntriesMap.isEmpty()) {
				 
				  for (Map.Entry<Long,LinkedList<TimesheetEntriesDTO>> entry : timesheetEntriesMap.entrySet()) {
					     Long  projectMemberId= entry.getKey();
				   		 LinkedList<TimesheetEntriesDTO> entriesList = entry.getValue();
				   		 List<FileInfo> fileInfoList = new ArrayList<>();
				   		 String weekComment = "";
				   		 if(timesheetAttachments != null && !timesheetAttachments.isEmpty()) {
				   			 for(EmployeeTimesheetFileInfoDTO etf :timesheetAttachments) {
				   				 if(etf.getProjectMemberId() != null && etf.getProjectMemberId().equals(projectMemberId)) {
				   					fileInfoList.clear();
				   					if(etf.getFileInfo() != null)
				   					fileInfoList.addAll(etf.getFileInfo());
				   					weekComment = etf.getWeekComment();
				   				 }
				   			 }
				   		 }
				   		freshPmIdList.add(projectMemberId);
				   		saveEmployeeTimesheetEntriesPerProjectMemberId(projectMemberId,"updated",weekComment,fileInfoList,entriesList, tedDTO.getTimeSubFrequencies(), tedDTO.getCurrentTimeFrequency(), tedDTO.getCurrentTimeSubFrequency(), status, loginOrgLocation, loginUser, candidate);
					}
			}

			if (oldPmDbIdList != null && !oldPmDbIdList.isEmpty()) {
				oldPmDbIdList.removeAll(freshPmIdList);
				if (oldPmDbIdList != null && !oldPmDbIdList.isEmpty()) {
					for (Long projectMemberId : oldPmDbIdList) {
						saveEmployeeTimesheetEntriesPerProjectMemberId(projectMemberId,"removed","",new ArrayList<FileInfo>(),new LinkedList<TimesheetEntriesDTO>(), tedDTO.getTimeSubFrequencies(), tedDTO.getCurrentTimeFrequency(), tedDTO.getCurrentTimeSubFrequency(), status, loginOrgLocation, loginUser, candidate);
					}
				}
				
			}
			
			return getEmployeeTimesheetEntryDetails(eedDTO, request);
	}

	
	private LinkedHashMap<String, LinkedList<TimesheetEntriesDTO>> getEntriesMapForDisplayWeb(
			TimeSheetSubmissionDTO tsDTO, HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		if (orgLocations != null) {
			orgLocations = orgLocationService.getOrgLocations(orgLocations.getId());
		}
		LinkedHashMap<String, LinkedList<TimesheetEntriesDTO>> entriesMap = new LinkedHashMap<>();
		LinkedHashMap<String, String> valuesMap = new LinkedHashMap<>();
		LinkedHashMap<String, Integer> weekCountMap = new LinkedHashMap<>();
		if (tsDTO != null && tsDTO.getTimesheetEntries() != null && !tsDTO.getTimesheetEntries().isEmpty()) {
			for (TimesheetEntriesDTO teDTO : tsDTO.getTimesheetEntries()) {
				String taskIdWithRef = "";
				if (teDTO.getTimesheetTaskDTO() != null && teDTO.getTimesheetTaskDTO().getId() != null) {
					Long taskId = teDTO.getTimesheetTaskDTO().getId();
					String ticketNumber = "NA";
					String hourlyType = "NA";
					if(StringUtils.isNotEmpty(teDTO.getTimesheetTaskDTO().getTicketNumber())) {
						ticketNumber = teDTO.getTimesheetTaskDTO().getTicketNumber();
					}
					if(StringUtils.isNotEmpty(teDTO.getHourlyType())) {
						  hourlyType = teDTO.getHourlyType();
					}
					int count = 0;
					if(weekCountMap.get(teDTO.getDate()) != null){
						count = weekCountMap.get(teDTO.getDate())+1;
					}
					weekCountMap.put(teDTO.getDate(),count);
					taskIdWithRef = taskId+"##"+ticketNumber+"##"+hourlyType+"##"+count;
				}
				LinkedList<TimesheetEntriesDTO> tsDTOList = entriesMap.get(taskIdWithRef);
				if (tsDTOList == null) {
					tsDTOList = new LinkedList<>();
					tsDTOList.add(teDTO);
				} else {
					tsDTOList.add(teDTO);
				}
				valuesMap.put(taskIdWithRef + "" + teDTO.getDate(), taskIdWithRef);
				entriesMap.put(taskIdWithRef, tsDTOList);
			}
		} else {
			String taskIdWithRef = "";
			for (WeekDaysDTO weekDay : tsDTO.getWeekDays()) {
				TimesheetEntriesDTO teDTO = new TimesheetEntriesDTO();
				teDTO.setDayOfTheDate(weekDay.getDayOfTheDate());
				teDTO.setDate(weekDay.getDate());
				teDTO.setEditable(weekDay.isEditable());
				teDTO.setHours("HH:MM");
				LinkedList<TimesheetEntriesDTO> tsDTOList = entriesMap.get(taskIdWithRef);
				if (tsDTOList == null) {
					tsDTOList = new LinkedList<>();
					tsDTOList.add(teDTO);
				} else {
					tsDTOList.add(teDTO);
				}
				entriesMap.put(taskIdWithRef, tsDTOList);
			}
		}
		if (valuesMap != null && !valuesMap.isEmpty()) {
			for (String taskIdWithRef : entriesMap.keySet()) {
				for (WeekDaysDTO weekDay : tsDTO.getWeekDays()) {
					if (valuesMap.get(taskIdWithRef + "" + weekDay.getDate()) == null) {
						
						String[] taskSplitter = taskIdWithRef.split("##");
						if((taskSplitter != null && taskSplitter.length < 0) || (taskSplitter != null && taskSplitter.length >= 0 && StringUtils.isEmpty(taskSplitter[0])))
							continue;
						
						TimesheetEntriesDTO tempteDTO =  entriesMap.get(taskIdWithRef).getFirst();
						//ProjectTasks pt = projectTasksService.getTasksId(taskId.toString());
						TimesheetEntriesDTO teDTO = new TimesheetEntriesDTO();
						teDTO.setDayOfTheDate(weekDay.getDayOfTheDate());
						teDTO.setDate(weekDay.getDate());
						teDTO.setEditable(weekDay.isEditable());
						teDTO.setHours("HH:MM");
						TimesheetTaskDTO timesheetTaskDTO = new TimesheetTaskDTO();
						timesheetTaskDTO.setId(tempteDTO.getTimesheetTaskDTO().getId());
						timesheetTaskDTO.setTaskType(tempteDTO.getTimesheetTaskDTO().getTaskType());
						timesheetTaskDTO.setTaskName(tempteDTO.getTimesheetTaskDTO().getTaskName());
						timesheetTaskDTO.setTicketNumber(tempteDTO.getTimesheetTaskDTO().getTicketNumber());
						teDTO.setTimesheetTaskDTO(timesheetTaskDTO);
						LinkedList<TimesheetEntriesDTO> tsDTOList = entriesMap.get(taskIdWithRef);
						if (tsDTOList == null) {
							tsDTOList = new LinkedList<>();
							tsDTOList.add(teDTO);
						} else {
							tsDTOList.add(teDTO);
						}
						entriesMap.put(taskIdWithRef, tsDTOList);
					}
				}

			}
		}

		LinkedHashMap<String, LinkedList<TimesheetEntriesDTO>> freshEntryMap = new LinkedHashMap<>();
		if (entriesMap != null && !entriesMap.isEmpty()) {
			for (String taskIdWithRef : entriesMap.keySet()) {
				LinkedList<TimesheetEntriesDTO> tedList = entriesMap.get(taskIdWithRef);
				TreeMap<Date, TimesheetEntriesDTO> freshTsDTOMap = new TreeMap<>();
				for (TimesheetEntriesDTO ted : tedList) {
					freshTsDTOMap.put(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
							ted.getDate().replaceAll("/", "-")), ted);
				}
				freshEntryMap.put(taskIdWithRef, new LinkedList<TimesheetEntriesDTO>(freshTsDTOMap.values()));
			}
		}
		return freshEntryMap;
	}

	private Map<String, List<TimesheetEntriesDTO>> getEntriesMapForDisplayMobile(TimeSheetSubmissionDTO tsDTO,
			HttpServletRequest request) throws Exception {
		Map<String, List<TimesheetEntriesDTO>> entriesMap = new HashMap<>();
		if (tsDTO != null && tsDTO.getTimesheetEntries() != null && !tsDTO.getTimesheetEntries().isEmpty()) {
			for (TimesheetEntriesDTO teDTO : tsDTO.getTimesheetEntries()) {
				String taskDate = null;
				if (teDTO.getDate() != null) {
					taskDate = teDTO.getDate();
				}
				List<TimesheetEntriesDTO> tsDTOList = entriesMap.get(taskDate);
				if (tsDTOList == null) {
					tsDTOList = new LinkedList<>();
					tsDTOList.add(teDTO);
				} else {
					tsDTOList.add(teDTO);
				}
				entriesMap.put(taskDate, tsDTOList);
			}
		}
		for (WeekDaysDTO weekDay : tsDTO.getWeekDays()) {
			if (entriesMap.get(weekDay.getDate()) == null || entriesMap.get(weekDay.getDate()).isEmpty()) {
				entriesMap.put(weekDay.getDate(), new ArrayList<TimesheetEntriesDTO>());
			}
		}

		return entriesMap;
	}

	private TimesheetEntryDetailsDTO getWeekDaysMapForDisplayMobile(TimesheetEntryDetailsDTO tedDTO,
			TimeSheetSubmissionDTO tsDTO, HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if (orgLocations != null) {
			orgLocations = orgLocationService.getOrgLocations(orgLocations.getId());
		}
		if (loginUser != null) {
			loginUser = userService.getUserById(loginUser.getId());
		}
		String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		Map<String, List<WeekDaysDTO>> entriesMap = new LinkedHashMap<>();
		Float totalHours = 0f;
		Float billableHours = 0f;
		Float nonBillableHours = 0f;
		if (orgLocations != null) {
			if (tedDTO != null && tedDTO.getTimeSubFrequencies() != null && !tedDTO.getTimeSubFrequencies().isEmpty()) {
				for (String key : tedDTO.getTimeSubFrequencies().keySet()) {
					String[] frequency = key.split("To");
					String fStartDate = frequency[0].trim();
					String fEndDate = frequency[1].trim();
					Date actualStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
							fStartDate.replaceAll("/", "-"));
					Date actualEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
							fEndDate.replaceAll("/", "-"));
					if (key != null && key.equalsIgnoreCase(tedDTO.getCurrentTimeSubFrequency())) {
						TimeSheetSubmissionDTO tssDTO = tedDTO.getTimesheetSubmission();
						List<WeekDaysDTO> weekDayDTOList = new LinkedList<>();
						while (actualStartDate.before(actualEndDate) || actualStartDate.equals(actualEndDate)) {
							String weekDayDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
									actualStartDate);
							for (WeekDaysDTO weekDaysDTO : tssDTO.getWeekDays()) {
								if (weekDayDate.equalsIgnoreCase(weekDaysDTO.getDate())) {
									if (StringUtils.isNotEmpty(weekDaysDTO.getTotalHours())
											&& !weekDaysDTO.getTotalHours().equalsIgnoreCase("HH:MM")) {
										totalHours = CommonUtil.getCombinedHours(
												Float.parseFloat(weekDaysDTO.getTotalHours().replace(":", ".")),
												totalHours);
									}
									if (StringUtils.isNotEmpty(weekDaysDTO.getBillableHours())
											&& !weekDaysDTO.getBillableHours().equalsIgnoreCase("HH:MM")) {
										billableHours = CommonUtil.getCombinedHours(
												Float.parseFloat(weekDaysDTO.getBillableHours().replace(":", ".")),
												billableHours);
									}
									if (StringUtils.isNotEmpty(weekDaysDTO.getNonBillableHours())
											&& !weekDaysDTO.getNonBillableHours().equalsIgnoreCase("HH:MM")) {
										nonBillableHours = CommonUtil.getCombinedHours(
												Float.parseFloat(weekDaysDTO.getNonBillableHours().replace(":", ".")),
												nonBillableHours);
									}
									weekDayDTOList.add(weekDaysDTO);
								}
							}
							actualStartDate = CalendarUtil.getNextDate(actualStartDate);
						}
						entriesMap.put(key, weekDayDTOList);
					} else {
						TimeSheetSubmissionDTO tssDTO = getWeekDaysDTOListBySubFrrequencyList(
								tedDTO.getProjectMemberId(), tedDTO.getCurrentTimeFrequency(), key, "user",
								orgLocations, loginUser,isGlobalAccess);
						if (tssDTO != null && tssDTO.getWeekDays() != null && !tssDTO.getWeekDays().isEmpty()) {
							List<WeekDaysDTO> weekDayDTOList = new LinkedList<>();
							while (actualStartDate.before(actualEndDate) || actualStartDate.equals(actualEndDate)) {
								String weekDayDate = commonUtil
										.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), actualStartDate);
								for (WeekDaysDTO weekDaysDTO : tssDTO.getWeekDays()) {
									if (weekDayDate.equalsIgnoreCase(weekDaysDTO.getDate())) {
										if (StringUtils.isNotEmpty(weekDaysDTO.getTotalHours())
												&& !weekDaysDTO.getTotalHours().equalsIgnoreCase("HH:MM")) {
											totalHours = CommonUtil.getCombinedHours(
													Float.parseFloat(weekDaysDTO.getTotalHours().replace(":", ".")),
													totalHours);
										}
										if (StringUtils.isNotEmpty(weekDaysDTO.getBillableHours())
												&& !weekDaysDTO.getBillableHours().equalsIgnoreCase("HH:MM")) {
											billableHours = CommonUtil.getCombinedHours(
													Float.parseFloat(weekDaysDTO.getBillableHours().replace(":", ".")),
													billableHours);
										}
										if (StringUtils.isNotEmpty(weekDaysDTO.getNonBillableHours())
												&& !weekDaysDTO.getNonBillableHours().equalsIgnoreCase("HH:MM")) {
											nonBillableHours = CommonUtil.getCombinedHours(
													Float.parseFloat(
															weekDaysDTO.getNonBillableHours().replace(":", ".")),
													nonBillableHours);
										}
										weekDayDTOList.add(weekDaysDTO);
									}
								}
								actualStartDate = CalendarUtil.getNextDate(actualStartDate);
							}
							entriesMap.put(key, weekDayDTOList);
						} else {
							List<WeekDaysDTO> weekDayDTOList = new LinkedList<>();
							while (actualStartDate.before(actualEndDate) || actualStartDate.equals(actualEndDate)) {
								WeekDaysDTO weekDaysDTO = new WeekDaysDTO();
								weekDaysDTO.setDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
										actualStartDate));
								SimpleDateFormat sf = new SimpleDateFormat("E");
								if (sf.format(actualStartDate).equalsIgnoreCase("Mon")) {
									weekDaysDTO.setDayOfTheDate("Mon");
								} else if (sf.format(actualStartDate).equalsIgnoreCase("Tue")) {
									weekDaysDTO.setDayOfTheDate("Tue");
								} else if (sf.format(actualStartDate).equalsIgnoreCase("Wed")) {
									weekDaysDTO.setDayOfTheDate("Wed");
								} else if (sf.format(actualStartDate).equalsIgnoreCase("Thu")) {
									weekDaysDTO.setDayOfTheDate("Thu");
								} else if (sf.format(actualStartDate).equalsIgnoreCase("Fri")) {
									weekDaysDTO.setDayOfTheDate("Fri");
								} else if (sf.format(actualStartDate).equalsIgnoreCase("Sat")) {
									weekDaysDTO.setDayOfTheDate("Sat");
								} else if (sf.format(actualStartDate).equalsIgnoreCase("Sun")) {
									weekDaysDTO.setDayOfTheDate("Sun");
								}
								weekDayDTOList.add(weekDaysDTO);
								actualStartDate = CalendarUtil.getNextDate(actualStartDate);
							}
							entriesMap.put(key, weekDayDTOList);
						}

					}
				}
			}
		}
		tsDTO.setTotalHours(CommonUtil.getHoursDisplayFormat(totalHours,null));
		tsDTO.setBillableHours(CommonUtil.getHoursDisplayFormat(billableHours,null));
		tsDTO.setNonBillableHours(CommonUtil.getHoursDisplayFormat(nonBillableHours,null));
		tedDTO.setWeekDaysMap(entriesMap);
		tedDTO.setTimesheetSubmission(tsDTO);
		return tedDTO;
	}

	private TimesheetEntryDetailsDTO getTimesheetEntryDetails(TimesheetExpenseEntryDetailsUtilDTO eedDTO,
			HttpServletRequest request) throws Exception {
		TimesheetEntryDetailsDTO timesheetEntryDetailsDTO = new TimesheetEntryDetailsDTO();
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if (loginUser == null && eedDTO.getUserId() != null) {
			loginUser = userService.getUserById(eedDTO.getUserId());
		}
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		if (orgLocations == null && StringUtils.isNotEmpty(eedDTO.getLocationCode())) {
			orgLocations = orgLocationService.getOrgLocationsByLocationCode(eedDTO.getLocationCode());
		}

		Candidate candidate = (Candidate) SourceLeadSessionManager.getSessionAttribute(request, "candidate");
		if (candidate == null && loginUser != null && orgLocations != null) {
			candidate = candidateService.getCandidateByConsultantUserId(loginUser.getId(), orgLocations.getId());
		}

		/**
		 * Getting List Of Projects For LoginUser
		 */
		timesheetEntryDetailsDTO = getProjectListForLoginUser(eedDTO, timesheetEntryDetailsDTO, orgLocations, candidate,
				request);
		return timesheetEntryDetailsDTO;

	}

	private TimesheetEntryDetailsDTO getProjectListForLoginUser(TimesheetExpenseEntryDetailsUtilDTO eedDTO,
			TimesheetEntryDetailsDTO timesheetEntryDetailsDTO, OrgLocations orgLocations, Candidate candidate,
			HttpServletRequest request) throws Exception {
		Map<String, String> timesheetFrequencyMap = null;
		Long projectMemberId = eedDTO.getProjectMembersId();
		Long projectId = eedDTO.getProjectsId();
		Long masterCandidateId = eedDTO.getMasterCandidatesId();
		List<ProjectMembers> projectMembersList = projectMembersService
				.getProjectMembersListByOrgLocationsIdAndCandidateId(orgLocations.getId(),orgLocations.getOrg().getId(), candidate.getId());
		ArrayList<ProjectMembersDTO> pmDTOList = new ArrayList<>();
		if (projectMembersList != null && !projectMembersList.isEmpty()) {
			long count = 1;
			for (ProjectMembers pm : projectMembersList) {
				if (count == 1 && projectMemberId == null) {
					timesheetFrequencyMap = timesheetFrequencyGeneratorUtil
							.getTimesheetFrequencyListFromProjectInvoiceFrequency(pm, orgLocations, "view");
					if (timesheetFrequencyMap == null || timesheetFrequencyMap.isEmpty())
						continue;

					projectMemberId = pm.getId();
					projectId = pm.getProjects().getId();
					masterCandidateId = pm.getMasterCandidateId().getId();
					count++;
				} else if (pm.getId().equals(projectMemberId)) {
					timesheetFrequencyMap = timesheetFrequencyGeneratorUtil
							.getTimesheetFrequencyListFromProjectInvoiceFrequency(pm, orgLocations, "view");
					if (timesheetFrequencyMap == null || timesheetFrequencyMap.isEmpty())
						continue;
					if (projectId == null || masterCandidateId == null) {
						projectId = pm.getProjects().getId();
						masterCandidateId = pm.getMasterCandidateId().getId();
					}
					count++;

				}
				ProjectMembersHistory projectmemberHistory = projectMembersHistoryService
						.getProjectMembersHistoryByProjectMemberIdAndOrgLoctionId(pm.getId(), orgLocations.getId());
				if (projectmemberHistory.getProjectAssignedDate() != null) {
					pm.setProjectAssignedDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
							projectmemberHistory.getProjectAssignedDate()));
				}
				if (projectmemberHistory.getProjectTerminationDate() != null) {
					pm.setProjectEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
							projectmemberHistory.getProjectTerminationDate()));
				}
				pmDTOList.add(ProjectMemberDTOConverter.getProjectMembersDTOFromProjectMembers(pm));
			}
		}
		timesheetEntryDetailsDTO.setTimeFrequencies(timesheetFrequencyMap);
		timesheetEntryDetailsDTO.setProjectMembersList(pmDTOList);
		timesheetEntryDetailsDTO.setProjectMemberId(projectMemberId);
		timesheetEntryDetailsDTO.setProjectId(projectId);
		timesheetEntryDetailsDTO.setMasterCandidateId(masterCandidateId);

		eedDTO.setProjectMembersId(projectMemberId);
		eedDTO.setProjectsId(projectId);
		eedDTO.setMasterCandidatesId(masterCandidateId);
		/**
		 * Getting TimesheetFrequencyList For Selected Project
		 */
		timesheetEntryDetailsDTO = getTimesheetFrequencyList(eedDTO, timesheetEntryDetailsDTO, request);

		return timesheetEntryDetailsDTO;
	}

	/**
	 * @purpose : Getting TimesheetFrequencyList For Selected Project(Step - 2)
	 * @param eedDTO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/rest/getTimesheetfrequencyList")
	public TimesheetEntryDetailsDTO getTimesheetfrequencyListMobile(TimesheetExpenseEntryDetailsUtilDTO eedDTO,
			HttpServletRequest request) throws Exception {
		TimesheetEntryDetailsDTO timesheetEntryDetailsDTO = new TimesheetEntryDetailsDTO();
		return getTimesheetFrequencyList(eedDTO, timesheetEntryDetailsDTO, request);
	}

	@RequestMapping(value = "/getTimesheetfrequencyList")
	public ModelAndView getTimesheetfrequencyListWeb(TimesheetExpenseEntryDetailsUtilDTO eedDTO,
			HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		try {
			TimesheetEntryDetailsDTO timesheetEntryDetailsDTO = new TimesheetEntryDetailsDTO();
			timesheetEntryDetailsDTO = getTimesheetFrequencyList(eedDTO, timesheetEntryDetailsDTO, request);
			model.addObject("timesheetSubmissionDTO", timesheetEntryDetailsDTO.getTimesheetSubmission());
			model.addObject("timesheetEntryDetailsDTO", timesheetEntryDetailsDTO);
			LinkedHashMap<String, LinkedList<TimesheetEntriesDTO>> entriesMap = getEntriesMapForDisplayWeb(
					timesheetEntryDetailsDTO.getTimesheetSubmission(), request);
			model.addObject("entriesMap", entriesMap);
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.setViewName("timesheet/timesheetfrequencyList");
		} catch (Exception e) {
			e.printStackTrace();
			model.addObject("AWKWARD", true);
			model.setViewName("timesheet/timesheetfrequencyList");
			e.printStackTrace();
		}
		return model;
	}

	private TimesheetEntryDetailsDTO getTimesheetFrequencyList(TimesheetExpenseEntryDetailsUtilDTO eedDTO,
			TimesheetEntryDetailsDTO timesheetEntryDetailsDTO, HttpServletRequest request) throws Exception {
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if (loginUser == null && eedDTO.getUserId() != null) {
			loginUser = userService.getUserById(eedDTO.getUserId());
		}else {
			loginUser = userService.getUserById(loginUser.getId());
		}
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		if (orgLocations == null && StringUtils.isNotEmpty(eedDTO.getLocationCode())) {
			orgLocations = orgLocationService.getOrgLocationsByLocationCode(eedDTO.getLocationCode());
		}else
		{
	      orgLocations = orgLocationService.getOrgLocations(orgLocations.getId());
	    }
		Candidate candidate = (Candidate) SourceLeadSessionManager.getSessionAttribute(request, "candidate");
		if (loginUser != null && orgLocations != null) {
			candidate = candidateService.getCandidateByConsultantUserId(loginUser.getId(), orgLocations.getId());
		}

		Long projectMemberId = eedDTO.getProjectMembersId();
		if (projectMemberId != null) {
			ProjectMembers projectMember = projectMembersService.getProjectMemberById(projectMemberId);
			Map<String, String> timesheetFrequencyMap = timesheetEntryDetailsDTO.getTimesheetFrequencyMap();
			if (timesheetFrequencyMap == null || timesheetFrequencyMap.isEmpty()) {
				timesheetFrequencyMap = timesheetFrequencyGeneratorUtil
						.getTimesheetFrequencyListFromProjectInvoiceFrequency(projectMember, orgLocations, "view");
			}
			LinkedHashMap<String, String> timeFrequencies = new LinkedHashMap<>();
			String currentTimeFrequency = eedDTO.getCurrentTimeFrequency();
			// For List Of Frequencies
			if (StringUtils.isEmpty(currentTimeFrequency)) {
				StringBuilder selectedStartDate = new StringBuilder("");
				StringBuilder selectedEndDate = new StringBuilder("");
				if (timesheetFrequencyMap != null && !timesheetFrequencyMap.isEmpty()) {
					long flag = 1;
					for (Map.Entry<String, String> entry : timesheetFrequencyMap.entrySet()) {
						String key = entry.getKey();
						String value = entry.getValue();
						Date currentStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
								key.replaceAll("/", "-"));
						Date currentEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
								value.replaceAll("/", "-"));
						HashSet<Date> pmhDateList = projectMembersHistoryService
								.getAllProjectMemberHistoryDatesByProjectMemberIdAndWeekStartDateEndDate(
										projectMemberId, currentStartDate, currentEndDate);
						if (pmhDateList.isEmpty() || pmhDateList == null)
							continue;
						if (flag == 1) {
							selectedStartDate.append(key.replaceAll("/", "-"));
							selectedEndDate.append(value.replaceAll("/", "-"));
						}
						if (CalendarUtil.isCurrentDateBetweenDateRange(key, value, orgLocations.getDateFormat())) {
							selectedStartDate.setLength(0);
							selectedEndDate.setLength(0);
							selectedStartDate.append(key.replaceAll("/", "-"));
							selectedEndDate.append(value.replaceAll("/", "-"));
						}
						timeFrequencies.put(key.replaceAll("/", "-") + "To" + value.replaceAll("/", "-"),
								key.replaceAll("/", "-") + " To " + value.replaceAll("/", "-"));
						flag++;
					}
					currentTimeFrequency = selectedStartDate.toString().replaceAll("/", "-") + "To"
							+ selectedEndDate.toString().replaceAll("/", "-");
				}
			} else {
				if (timesheetFrequencyMap != null && !timesheetFrequencyMap.isEmpty()) {
					for (Map.Entry<String, String> entry : timesheetFrequencyMap.entrySet()) {
						String key = entry.getKey();
						String value = entry.getValue();
						Date currentStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
								key.replaceAll("/", "-"));
						Date currentEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
								value.replaceAll("/", "-"));
						HashSet<Date> pmhDateList = projectMembersHistoryService
								.getAllProjectMemberHistoryDatesByProjectMemberIdAndWeekStartDateEndDate(
										projectMemberId, currentStartDate, currentEndDate);
						if (pmhDateList.isEmpty() || pmhDateList == null)
							continue;
						timeFrequencies.put(key.replaceAll("/", "-") + "To" + value.replaceAll("/", "-"),
								key.replaceAll("/", "-") + " To " + value.replaceAll("/", "-"));
					}
				}
			}
			timesheetEntryDetailsDTO.setTimeFrequencies(timeFrequencies);
			timesheetEntryDetailsDTO.setCurrentTimeFrequency(currentTimeFrequency);
			timesheetEntryDetailsDTO.setProjectMemberId(projectMemberId);
			timesheetEntryDetailsDTO.setProjectId(projectMember.getProjects().getId());
			timesheetEntryDetailsDTO.setMasterCandidateId(projectMember.getMasterCandidateId().getId());
			eedDTO.setProjectsId(projectMember.getProjects().getId());
			eedDTO.setMasterCandidatesId(projectMember.getMasterCandidateId().getId());
			eedDTO.setCurrentTimeFrequency(currentTimeFrequency);
			eedDTO.setUserId(loginUser.getId());
			eedDTO.setLocationCode(orgLocations.getLocationCode());

			/**
			 * Getting Timehsheet Sub-FrequencyList For Selected Timesheet Frequency
			 */
			timesheetEntryDetailsDTO = getTimesheetSubFrequencyListByCurrentFrequency(eedDTO, timesheetEntryDetailsDTO,
					orgLocations, request);

			TimeSheetSubmissionDTO ts = getTimesheetSubmissionDetails(eedDTO, request);
			timesheetEntryDetailsDTO.setTimesheetSubmission(ts);
		}
		return timesheetEntryDetailsDTO;
	}

	private TimesheetEntryDetailsDTO getTimesheetSubFrequencyListByCurrentFrequency(
			TimesheetExpenseEntryDetailsUtilDTO eedDTO, TimesheetEntryDetailsDTO timesheetEntryDetailsDTO,
			OrgLocations orgLocations, HttpServletRequest request) throws Exception {
		LinkedHashMap<String, String> timeSubFrequencies = new LinkedHashMap<>();

		String currentTimeFrequency = eedDTO.getCurrentTimeFrequency();
		String[] frequency = currentTimeFrequency.split("To");
		String currentFrequencyStartDate = frequency[0].trim();
		String currentFrequencyEndDate = frequency[1].trim();

		String timesheetFor = "user";
		if (StringUtils.isNotEmpty(eedDTO.getTimeSheetsFor())) {
			timesheetFor = eedDTO.getTimeSheetsFor();
		}

		if (!isNullOrEmpty(currentFrequencyStartDate) && !isNullOrEmpty(currentFrequencyEndDate)) {
			Map<String, String> timeSubFrequencyMap = timesheetFrequencyGeneratorUtil
					.getTabFrequencyListFromProjectInvoiceFrequency(currentFrequencyStartDate, currentFrequencyEndDate,
							orgLocations.getDateFormat());
			String currentTimeSubFrequency = eedDTO.getCurrentTimeSubFrequency();

			StringBuilder currentStartDate = new StringBuilder("");
			StringBuilder currentEndDate = new StringBuilder("");
			int count = 1;
			LinkedList<String> filledTimeSubFrequencies = new LinkedList<>();
			if (timeSubFrequencyMap != null && !timeSubFrequencyMap.isEmpty()) {
				for (Map.Entry<String, String> entry : timeSubFrequencyMap.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
					Date startDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
							key.replaceAll("/", "-"));
					Date endDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
							value.replaceAll("/", "-"));
					DateFormat shortormat = Constants.SHORT_DF_STD_DM;
					TimesheetSubmissions ts = timesheetSubmissionsService
							.getAllCandidateTimesheetSubmissionsByProjectIdAndMasterCandidateId(startDate, endDate,
									eedDTO.getProjectsId(), eedDTO.getMasterCandidatesId());

					if (ts != null && eedDTO != null && eedDTO.getUserId() != null) {
						SubmissionApproverHierarchy sah = null;
						if (timesheetFor.equalsIgnoreCase("user")) {
							sah = submissionApproverHierarchyService
									.getSubmissionLowLevelApproverHierarchyForEmployee(ts.getId());
						} else {
							sah = submissionApproverHierarchyService
									.getSubmissionApproverHierarchyForApprover(ts.getId(), eedDTO.getUserId());
						}
						if (sah != null) {
							Submission_Status submissionStatus = sah.getSubmissionStatus();
							String submissionStatusStr = submissionStatus.getValue();
							if (!isNullOrEmpty(submissionStatusStr) && !submissionStatusStr
									.equalsIgnoreCase(Submission_Status.getNameByValue("new").getValue())) {
								filledTimeSubFrequencies.add(key + "To" + value);
							}
						}

					}

					if (CalendarUtil.isCurrentDateBetweenDateRange(currentFrequencyStartDate, currentFrequencyEndDate,
							orgLocations.getDateFormat())) {
						if (CalendarUtil.isCurrentDateBetweenDateRange(key, value, orgLocations.getDateFormat())) {
							currentStartDate.append(key);
							currentEndDate.append(value);
						}
						if (startDate.equals(endDate)) {
							timeSubFrequencies.put(key + "To" + value, shortormat.format(startDate));
						} else {
							timeSubFrequencies.put(key + "To" + value,
									shortormat.format(startDate) + " - " + shortormat.format(endDate));
						}
					} else {
						if (count == 1) {
							currentStartDate.append(key);
							currentEndDate.append(value);
						}
						if (startDate.equals(endDate)) {
							timeSubFrequencies.put(key + "To" + value, shortormat.format(startDate));
						} else {
							timeSubFrequencies.put(key + "To" + value,
									shortormat.format(startDate) + " - " + shortormat.format(endDate));
						}
					}
					count++;
				}

				if (StringUtils.isEmpty(eedDTO.getCurrentTimeSubFrequency())) {
					currentTimeSubFrequency = currentStartDate.toString() + "To" + currentEndDate.toString();
				}
				timesheetEntryDetailsDTO.setFilledTimeSubFrequencies(filledTimeSubFrequencies);
				timesheetEntryDetailsDTO.setCurrentTimeSubFrequency(currentTimeSubFrequency);
				timesheetEntryDetailsDTO.setTimeSubFrequencies(timeSubFrequencies);
				timesheetEntryDetailsDTO.setTimesheetFor(timesheetFor);
				timesheetEntryDetailsDTO.setLocDateFomat(orgLocations.getDateFormat());
				timesheetEntryDetailsDTO.setLocDateFomat(orgLocations.getDateFormat());
				eedDTO.setCurrentTimeSubFrequency(currentTimeSubFrequency);
			}
		}
		return timesheetEntryDetailsDTO;
	}

	private HashMap<String, String> getTimesheetStatusByTimeSubFrequency(ProjectMembers pm, String timesheetFor,
			String currentTimeFrequency, OrgLocations orgLoc, User loginUser, Boolean isModifyMyTimeSheet,
			Boolean isFillMyTimeSheet,String isGlobalAccess) throws Exception {
		String[] frequency = currentTimeFrequency.split("To");
		String currentFrequencyStartDate = frequency[0].trim();
		String currentFrequencyEndDate = frequency[1].trim();
		
		OrgLocations orgLocations = orgLocationService.getOrgLocationById(orgLoc.getId());

		if (StringUtils.isEmpty(timesheetFor)) {
			timesheetFor = "user";
		}
		HashMap<String, String> statusMap = new HashMap<>();

		if (pm != null) {
			Long projectId = pm.getProjects().getId();
			Long masterCandidateId = pm.getMasterCandidateId().getId();
			Date frequencyStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
					currentFrequencyStartDate.replaceAll("/", "-"));
			Date frequencyEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
					currentFrequencyEndDate.replaceAll("/", "-"));

			Boolean isReopenButtonVisible = false;
			SubmissionApproverHierarchy sah = null;
			if (StringUtils.isNotEmpty(timesheetFor) && timesheetFor.equalsIgnoreCase("user")) {
				sah = submissionApproverHierarchyService
						.getSubmissionApproverHierarchyForEmployeeByProjectIdAndMasterCandidateId(frequencyStartDate,
								frequencyEndDate, projectId, masterCandidateId);
			} else {
				isReopenButtonVisible = submissionApproverHierarchyService.isReopenButtonVisible(frequencyStartDate,
						frequencyEndDate, projectId, masterCandidateId, orgLocations.getId(),orgLocations.getOrg().getId(),isGlobalAccess);
				sah = submissionApproverHierarchyService
						.getSubmissionApproverHierarchyByMasterCandidateIdStartDateAndEndDateForApproverLogin(
								loginUser.getId(), projectId, masterCandidateId, orgLocations.getId(),
								frequencyStartDate, frequencyEndDate,isGlobalAccess);
			}
			Submission_Status submissionStatus = sah.getSubmissionStatus();
			Boolean isSuperUser = commonUtil.isSuperAdminForProjectMemberToReopenTimesheets(pm.getId(), loginUser,
					orgLocations,isGlobalAccess);
			String submissionStatusStr = submissionStatus.getValue();
			SubmissionApproverHierarchy isSah = submissionApproverHierarchyService
					.isThereAnyApproverChangesStatusForProjectAndMasterCandidateId(projectId, masterCandidateId,
							frequencyStartDate, frequencyEndDate, "APPROVED");
			boolean isSahApproved = false;
			if ((isSah == null && timesheetFor.equalsIgnoreCase("user") && isModifyMyTimeSheet
					&& !isNullOrEmpty(submissionStatusStr)
					&& (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue())))
					|| (!isNullOrEmpty(submissionStatusStr)
							&& submissionStatusStr
									.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())
							&& timesheetFor.equalsIgnoreCase("approver")
							&& StringUtils.isEmpty(sah.getReopenReason()))) {
				if (isSah == null)
					isSahApproved = true;
			}

			boolean isSahReopened = false;
			if (null != isSah && timesheetFor.equalsIgnoreCase("approver") && null != isSah.getReopenReason()
					&& !"".equalsIgnoreCase(isSah.getReopenReason())) {
				isSahReopened = true;
			}

			String acSubmissionStatusStr = null;
			if (!isNullOrEmpty(submissionStatusStr)
					&& submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())) {
				SubmissionApproverHierarchy acsah = submissionApproverHierarchyService
						.getSubmissionApproverHierarchyForEmployeeByProjectIdAndMasterCandidateId(frequencyStartDate,
								frequencyEndDate, projectId, masterCandidateId);
				Submission_Status acSubmissionStatus = acsah.getSubmissionStatus();
				acSubmissionStatusStr = acSubmissionStatus.getValue();
			}
			statusMap.put("STATUS", submissionStatusStr.toUpperCase());
			statusMap.put("REOPEN_REASON", sah.getReopenReason());
			if (!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr
					.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()))) {
				if (isSahApproved && isModifyMyTimeSheet && timesheetFor.equalsIgnoreCase("user") && isModifyMyTimeSheet
						&& isFillMyTimeSheet) {
					statusMap.put("IS_MODIFY_BUTTON_VISIBLE", "true");
				}
			}

			if (!isNullOrEmpty(submissionStatusStr)
					&& submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())) {
				if (timesheetFor.equalsIgnoreCase("approver") && StringUtils.isEmpty(sah.getReopenReason())) {

					// to show re-open rejected reason
					if (StringUtils.isNotEmpty(sah.getReopenRejectedReason()) && isReopenButtonVisible) {
						statusMap.put("REOPEN_REJECTED_REASON", sah.getReopenRejectedReason());
					}
					if (!isNullOrEmpty(acSubmissionStatusStr) && isReopenButtonVisible && acSubmissionStatusStr
							.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())) {
						statusMap.put("IS_REOPEN_BUTTON_VISIBLE", "true");
					}
				} else if (timesheetFor.equalsIgnoreCase("approver") && !isSuperUser
						&& StringUtils.isNotEmpty(sah.getReopenReason()) && isSahReopened) {
					// to show re-open reason
					if (StringUtils.isNotEmpty(sah.getReopenReason()) && isReopenButtonVisible) {
						statusMap.put("REOPEN_REASON", sah.getReopenReason());
					}
					statusMap.put("REOPEN_REQUEST_SENT", "true");
				} else if (timesheetFor.equalsIgnoreCase("approver") && isSahReopened && isSuperUser
						&& null != sah.getReopenedUser()) {
					// to show re-open reason
					if (StringUtils.isNotEmpty(sah.getReopenReason())) {
						statusMap.put("REOPEN_REASON", sah.getReopenReason());
					}
					statusMap.put("REOPEN_USER_ID", "" + sah.getReopenedUser().getId());
					statusMap.put("REOPEN_REQUEST_SENT", "true");
					statusMap.put("IS_REOPEN_ACCEPT_REJECT_BUTTON_VISIBLE", "true");
				}
			}
			if (!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr
					.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue()))) {
				statusMap.put("REJECTED_REASON", sah.getRejectedReason());
			}

			statusMap.put("IS_Client_PrimeVendor", isReopenButtonVisible.toString());

		}
		return statusMap;
	}

	/**
	 * @purpose : Getting TimesheetSubmission Details For Current-TimesheetFrequency
	 *          and Current-TimesheetSubFrequency(Step - 3)
	 * @param eedDTO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/rest/getTimesheetSubmissionDetails")
	public TimeSheetSubmissionDTO getTimesheetSubmissionDetailsMobile(
			@RequestBody TimesheetExpenseEntryDetailsUtilDTO eedDTO, HttpServletRequest request) throws Exception {
		return getTimesheetSubmissionDetails(eedDTO, request);
	}

	@RequestMapping(value = "/getTimesheetSubmissionDetails")
	public ModelAndView getTimesheetSubmissionDetailsWeb(TimesheetExpenseEntryDetailsUtilDTO eedDTO,
			HttpServletRequest request) throws Exception {

		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Long shiftId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "shiftId");
		
		if (loginUser == null && eedDTO.getUserId() != null) {
			loginUser = userService.getUserById(eedDTO.getUserId());
		}else {
			loginUser = userService.getUserById(loginUser.getId());
		}
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		if (orgLocations == null && StringUtils.isNotEmpty(eedDTO.getLocationCode())) {
			orgLocations = orgLocationService.getOrgLocationsByLocationCode(eedDTO.getLocationCode());
		}else {
			 orgLocations = orgLocationService.getOrgLocations(orgLocations.getId());
		}
		ModelAndView model = new ModelAndView();
		try {
			Long projectMemberId = eedDTO.getProjectMembersId();
			if (projectMemberId != null) {
				ProjectMembers pm = projectMembersService.getProjectMemberById(projectMemberId);
				eedDTO.setUserId(loginUser.getId());
				eedDTO.setLocationCode(orgLocations.getLocationCode());
				eedDTO.setProjectsId(pm.getProjects().getId());
				eedDTO.setMasterCandidatesId(pm.getMasterCandidateId().getId());
				TimesheetEntryDetailsDTO timesheetEntryDetailsDTO = getTimesheetSubFrequencyListByCurrentFrequency(
						eedDTO, new TimesheetEntryDetailsDTO(), orgLocations, request);
				timesheetEntryDetailsDTO.setProjectMemberId(projectMemberId);
				timesheetEntryDetailsDTO.setProjectId(pm.getProjects().getId());
				timesheetEntryDetailsDTO.setMasterCandidateId(pm.getMasterCandidateId().getId());

				String fileUrl = "";
				if (pm != null && pm.getProjectMemberCandidteId() != null
						&& pm.getProjectMemberCandidteId().getConsultantUser() != null)
					fileUrl = pm.getProjectMemberCandidteId().getConsultantUser().getFileUrl();

				timesheetEntryDetailsDTO.setProfileImageFileUrl(fileUrl);
				timesheetEntryDetailsDTO.setEmployeeName(pm.getProjectMemberCandidteId().getFullName());
				timesheetEntryDetailsDTO.setEmployeeEmail(pm.getProjectMemberCandidteId().getEmail());
				timesheetEntryDetailsDTO.setEmployeePhoneNo(pm.getProjectMemberCandidteId().getMobilePhone());
				timesheetEntryDetailsDTO.setProjectNameForSubmissionPageDisplay(pm.getProjects().getProjectName());
				timesheetEntryDetailsDTO.setEmploymentTypeForSubmissionPageDisplay(pm.getProjectMemberCandidteId().getDesignationType());
						

				if (pm.getProjectClientsId().getOrgRelationId() != null) {
					OrgRelations orgRelations = orgRelationsService
							.getOrgRelationById(pm.getProjectClientsId().getOrgRelationId().getId());
					if (null != pm.getProjectClientsId().getOrgRelationId().getLocationId()
							&& pm.getProjectClientsId().getOrgRelationId().getLocationId().getId() != null
							&& pm.getProjectClientsId().getOrgRelationId().getLocationId().getId()
									.longValue() == orgLocations.getId().longValue()) {
						if (orgRelations.getResponderLocationId() != null) {
							timesheetEntryDetailsDTO.setClientNameForDisplay(
									orgRelations.getResponderLocationId().getOrgLocationName());
						}
					}
					if (null != pm.getProjectClientsId().getOrgRelationId().getResponderLocationId()
							&& null != pm.getProjectClientsId().getOrgRelationId().getResponderLocationId().getId()
							&& pm.getProjectClientsId().getOrgRelationId().getResponderLocationId().getId()
									.longValue() == orgLocations.getId().longValue()) {
						if (orgRelations.getLocationId() != null) {
							timesheetEntryDetailsDTO
									.setClientNameForDisplay(orgRelations.getLocationId().getOrgLocationName());
						}
					}
				}

				List<ProjectTasks> projectsTasks = projectTasksService.getAllTasksByProjectId(eedDTO.getProjectsId());
				TimeSheetSubmissionDTO tsDTO = getTimesheetSubmissionDetails(eedDTO, request);
				//time sheet activity from time sheet activity
				tsDTO  = timesheetActitvityList(pm.getProjects().getId(),pm.getMasterCandidateId().getId(),tsDTO.getTimeFrequency(),orgLocations,tsDTO);
				
				ProjectMembers conPM =  projectMembersService.getConsultantUserProjectMemberByProjectMemberId(projectMemberId);
				if(conPM != null) {
					User consultantUser = conPM.getProjectMemberCandidteId().getConsultantUser();
	                Map<String,List<EmployeeLeaveDTO>> empLeaveMap = getEmpLeaveList(tsDTO.getWeekDays(),eedDTO.getCurrentTimeSubFrequency(), orgLocations,consultantUser,conPM.getProjectMemberCandidteId(),shiftId);
	                tsDTO.setEmpLeaveMap(empLeaveMap);
				}
				model.addObject("timesheetSubmissionDTO", tsDTO);
				LinkedHashMap<String, LinkedList<TimesheetEntriesDTO>> entriesMap = getEntriesMapForDisplayWeb(tsDTO,request);
				model.addObject("entriesMap", entriesMap);
				model.addObject("projectsTasks", projectsTasks);
				model.addObject("timesheetEntryDetailsDTO", timesheetEntryDetailsDTO);
				
				/*
				 * if(eedDTO.getRedirectionRequestFrom()!= null &&
				 * StringUtils.isNotBlank(eedDTO.getRedirectionRequestFrom()) &&
				 * eedDTO.getRedirectionRequestFrom().equalsIgnoreCase("notifyLink")){
				 * model.addObject("notificationCurrentFrequency",eedDTO.getCurrentTimeFrequency
				 * ()); model.addObject("notificationProMemberId",eedDTO.getProjectMembersId());
				 * model.addObject("notificationCurrentSubTimeFrequency",eedDTO.
				 * getCurrentTimeSubFrequency()); }
				 */
			}
			model.setViewName("timesheet/timesheetApprovalSubmission");
		} catch (Exception e) {
			model.addObject("AWKWARD", true);
			model.setViewName("timesheet/timesheetApprovalSubmission");
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value = "/timesheetActivity" , method = RequestMethod.POST)
	public ModelAndView timesheetActitvityListDisplay(
				@RequestParam(value="projectMemberId",required = true)Long projectMemberId,
				@RequestParam(value="timesheetFrequency",required = true)String timesheetFrequency,
				HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		if(orgLocations != null) {
			 orgLocations = orgLocationService.getOrgLocations(orgLocations.getId());
		}
		ModelAndView model = new ModelAndView();
		ProjectMembers projectMember = projectMembersService.getProjectMemberById(projectMemberId);
		TimeSheetSubmissionDTO tsDTO = new TimeSheetSubmissionDTO();
		tsDTO = timesheetActitvityList(projectMember.getProjects().getId(),projectMember.getMasterCandidateId().getId(),timesheetFrequency,orgLocations,tsDTO);
		model.addObject("timesheetSubmissionDTO",tsDTO);
		model.setViewName("timesheet/timesheetActivity");
		return model;
	}
	
	private TimeSheetSubmissionDTO timesheetActitvityList(Long projectId, Long masterCandidateId, String timeFrequency, OrgLocations orgLocations, TimeSheetSubmissionDTO tsDTO) throws Exception {
		List<TimesheetActivities> activityList = timesheetActivityService.getTimesheetActivityByProjectIdMasterCandidateIdFrequencyStartAndEndDate(projectId,masterCandidateId,timeFrequency,orgLocations);
		tsDTO.setActivityList(activityList);
		return tsDTO;
	}

	/**
	 * @purpose : Getting TimesheetSubmission For Given TimesheetSubFrequency (For
	 *          to Show Filled Hours in Mobile)
	 * @return TimeSheetSubmissionDTO
	 * @throws Exception
	 */
	private TimeSheetSubmissionDTO getWeekDaysDTOListBySubFrrequencyList(Long projectMemberId,
			String currentTimeFrequency, String currentTimeSubFrequency, String timesheetFor, OrgLocations orgLocations,
			User loginUser,String isGlobalAccess) throws Exception {
		Boolean isFillMyTimeSheet = commonUtil.hasRole("ROLE_FILL_MY_TIMESHEETS");
		Boolean isModifyMyTimeSheet = commonUtil.hasRole("ROLE_MODIFY_MY_TIMESHEETS");
		Boolean isFillMemberTimesheet = commonUtil.hasRole("ROLE_FILL_MEMBER_TIMESHEETS");
		if (isFillMyTimeSheet || isFillMemberTimesheet) {
			if (!isFillMyTimeSheet && isFillMemberTimesheet) {
				isFillMyTimeSheet = isFillMemberTimesheet;
			}
		}
		ProjectMembers projectMember = projectMembersService.getProjectMemberById(projectMemberId);
		HashMap<String, String> statusMap = getTimesheetStatusByTimeSubFrequency(projectMember, timesheetFor,
				currentTimeFrequency, orgLocations, loginUser, isModifyMyTimeSheet, isFillMyTimeSheet,isGlobalAccess);
		StringBuilder disbleCondition1 = new StringBuilder("");
		StringBuilder disbleCondition2 = new StringBuilder("");
		StringBuilder disbleCondition3 = new StringBuilder("");
		StringBuilder disbleCondition4 = new StringBuilder("");
		StringBuilder disbleCondition5 = new StringBuilder("");
		StringBuilder disbleCondition6 = new StringBuilder("");
		StringBuilder disbleCondition7 = new StringBuilder("");

		String[] currentSubFrequency = currentTimeSubFrequency.split("To");
		String currentSubFrequencyStartDate = currentSubFrequency[0].trim();
		String currentSubFrequencyEndDate = currentSubFrequency[1].trim();

		Boolean reopenButtonIsVisible = new Boolean(statusMap.get("IS_Client_PrimeVendor"));

		Date actualStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
				currentSubFrequencyStartDate.replaceAll("/", "-"));
		Date actualEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
				currentSubFrequencyEndDate.replaceAll("/", "-"));
		int dayOfTheDateWithStartDate = CalendarUtil.getDayOfTheDate(
				commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), currentSubFrequencyStartDate));
		String weekStartDate = currentSubFrequencyStartDate;
		switch (dayOfTheDateWithStartDate) {
		case 1:
			weekStartDate = CalendarUtil.getPreviousDateByDecrementor(currentSubFrequencyStartDate, 6,
					orgLocations.getDateFormat());
			disbleCondition1.setLength(0);
			disbleCondition2.setLength(0);
			disbleCondition3.setLength(0);
			disbleCondition4.setLength(0);
			disbleCondition5.setLength(0);
			disbleCondition6.setLength(0);
			disbleCondition1.append("disabled");
			disbleCondition2.append("disabled");
			disbleCondition3.append("disabled");
			disbleCondition4.append("disabled");
			disbleCondition5.append("disabled");
			disbleCondition6.append("disabled");
			break;
		case 2:
			weekStartDate = CalendarUtil.getPreviousDateByDecrementor(currentSubFrequencyStartDate, 0,
					orgLocations.getDateFormat());
			break;
		case 3:
			weekStartDate = CalendarUtil.getPreviousDateByDecrementor(currentSubFrequencyStartDate, 1,
					orgLocations.getDateFormat());
			disbleCondition1.setLength(0);
			disbleCondition1.append("disabled");
			break;
		case 4:
			weekStartDate = CalendarUtil.getPreviousDateByDecrementor(currentSubFrequencyStartDate, 2,
					orgLocations.getDateFormat());
			disbleCondition1.setLength(0);
			disbleCondition2.setLength(0);
			disbleCondition1.append("disabled");
			disbleCondition2.append("disabled");
			break;
		case 5:
			weekStartDate = CalendarUtil.getPreviousDateByDecrementor(currentSubFrequencyStartDate, 3,
					orgLocations.getDateFormat());
			disbleCondition1.setLength(0);
			disbleCondition2.setLength(0);
			disbleCondition3.setLength(0);
			disbleCondition1.append("disabled");
			disbleCondition2.append("disabled");
			disbleCondition3.append("disabled");
			break;
		case 6:
			weekStartDate = CalendarUtil.getPreviousDateByDecrementor(currentSubFrequencyStartDate, 4,
					orgLocations.getDateFormat());
			disbleCondition1.setLength(0);
			disbleCondition2.setLength(0);
			disbleCondition3.setLength(0);
			disbleCondition4.setLength(0);
			disbleCondition1.append("disabled");
			disbleCondition2.append("disabled");
			disbleCondition3.append("disabled");
			disbleCondition4.append("disabled");
			break;
		case 7:
			weekStartDate = CalendarUtil.getPreviousDateByDecrementor(currentSubFrequencyStartDate, 5,
					orgLocations.getDateFormat());
			disbleCondition1.setLength(0);
			disbleCondition2.setLength(0);
			disbleCondition3.setLength(0);
			disbleCondition4.setLength(0);
			disbleCondition5.setLength(0);
			disbleCondition1.append("disabled");
			disbleCondition2.append("disabled");
			disbleCondition3.append("disabled");
			disbleCondition4.append("disabled");
			disbleCondition5.append("disabled");
			break;
		default:
			weekStartDate = CalendarUtil.getPreviousDateByDecrementor(currentSubFrequencyStartDate, 0,
					orgLocations.getDateFormat());
			break;
		}
		int dayOfTheDateWithEndDate = CalendarUtil.getDayOfTheDate(
				commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), currentSubFrequencyEndDate));
		switch (dayOfTheDateWithEndDate) {
		case 2:
			disbleCondition2.setLength(0);
			disbleCondition3.setLength(0);
			disbleCondition4.setLength(0);
			disbleCondition5.setLength(0);
			disbleCondition6.setLength(0);
			disbleCondition7.setLength(0);
			disbleCondition2.append("disabled");
			disbleCondition3.append("disabled");
			disbleCondition4.append("disabled");
			disbleCondition5.append("disabled");
			disbleCondition6.append("disabled");
			disbleCondition7.append("disabled");
			break;

		case 3:
			disbleCondition3.setLength(0);
			disbleCondition4.setLength(0);
			disbleCondition5.setLength(0);
			disbleCondition6.setLength(0);
			disbleCondition7.setLength(0);
			disbleCondition3.append("disabled");
			disbleCondition4.append("disabled");
			disbleCondition5.append("disabled");
			disbleCondition6.append("disabled");
			disbleCondition7.append("disabled");
			break;
		case 4:
			disbleCondition4.setLength(0);
			disbleCondition5.setLength(0);
			disbleCondition6.setLength(0);
			disbleCondition7.setLength(0);
			disbleCondition4.append("disabled");
			disbleCondition5.append("disabled");
			disbleCondition6.append("disabled");
			disbleCondition7.append("disabled");
			break;
		case 5:
			disbleCondition5.setLength(0);
			disbleCondition6.setLength(0);
			disbleCondition7.setLength(0);
			disbleCondition5.append("disabled");
			disbleCondition6.append("disabled");
			disbleCondition7.append("disabled");
			break;
		case 6:
			disbleCondition6.setLength(0);
			disbleCondition7.setLength(0);
			disbleCondition6.append("disabled");
			disbleCondition7.append("disabled");
			break;
		case 7:
			disbleCondition7.setLength(0);
			disbleCondition7.append("disabled");
			break;
		}

		Date actualWeekStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
				weekStartDate.replaceAll("/", "-"));
		String tuesDayDate = CalendarUtil.getNextDate(weekStartDate, orgLocations.getDateFormat());
		String WednesDayDate = CalendarUtil.getNextDate(tuesDayDate, orgLocations.getDateFormat());
		String ThursDayDate = CalendarUtil.getNextDate(WednesDayDate, orgLocations.getDateFormat());
		String FriDayDate = CalendarUtil.getNextDate(ThursDayDate, orgLocations.getDateFormat());
		String SaturdayDate = CalendarUtil.getNextDate(FriDayDate, orgLocations.getDateFormat());
		String SundayDate = CalendarUtil.getNextDate(SaturdayDate, orgLocations.getDateFormat());

		/**
		 * Here getting dates which are enabled in the current submission (Hint:In
		 * particular project,the member may assigned in the mid of the week)
		 */
		HashSet<Date> pmhDateList = projectMembersHistoryService
				.getAllProjectMemberHistoryDatesByProjectMemberIdAndWeekStartDateEndDate(projectMemberId,
						actualWeekStartDate, actualEndDate);

		Calendar currentCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
		currentCal.setLenient(false);
		currentCal.setTime(actualWeekStartDate);

		String approvalStatus = statusMap.get("STATUS");
		if ((!isFillMyTimeSheet) || (!pmhDateList.contains(actualWeekStartDate)) || !isNullOrEmpty(approvalStatus)
				&& (((approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()))
						&& (timesheetFor.equalsIgnoreCase("user") || !reopenButtonIsVisible))
						|| approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())
						|| (approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue())
								&& (!reopenButtonIsVisible && !timesheetFor.equalsIgnoreCase("user"))))) {
			disbleCondition1.setLength(0);
			disbleCondition1.append("disabled");
		}
		if ((!isFillMyTimeSheet)
				|| (!pmhDateList.contains(CalendarUtil
						.getCurrentWeekDayByCalendar(currentCal, "TUE", orgLocations.getDateFormat()).getTime()))
				|| !isNullOrEmpty(approvalStatus)
						&& (((approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()))
								&& (timesheetFor.equalsIgnoreCase("user") || !reopenButtonIsVisible))
								|| approvalStatus
										.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())
								|| (approvalStatus
										.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue())
										&& (!reopenButtonIsVisible && !timesheetFor.equalsIgnoreCase("user"))))) {
			disbleCondition2.setLength(0);
			disbleCondition2.append("disabled");
		}
		if ((!isFillMyTimeSheet)
				|| (!pmhDateList.contains(CalendarUtil
						.getCurrentWeekDayByCalendar(currentCal, "WED", orgLocations.getDateFormat()).getTime()))
				|| !isNullOrEmpty(approvalStatus)
						&& (((approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()))
								&& (timesheetFor.equalsIgnoreCase("user") || !reopenButtonIsVisible))
								|| approvalStatus
										.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())
								|| (approvalStatus
										.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue())
										&& (!reopenButtonIsVisible && !timesheetFor.equalsIgnoreCase("user"))))) {
			disbleCondition3.setLength(0);
			disbleCondition3.append("disabled");
		}
		if ((!isFillMyTimeSheet)
				|| (!pmhDateList.contains(CalendarUtil
						.getCurrentWeekDayByCalendar(currentCal, "THU", orgLocations.getDateFormat()).getTime()))
				|| !isNullOrEmpty(approvalStatus)
						&& (((approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()))
								&& (timesheetFor.equalsIgnoreCase("user") || !reopenButtonIsVisible))
								|| approvalStatus
										.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())
								|| (approvalStatus
										.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue())
										&& (!reopenButtonIsVisible && !timesheetFor.equalsIgnoreCase("user"))))) {
			disbleCondition4.setLength(0);
			disbleCondition4.append("disabled");
		}
		if ((!isFillMyTimeSheet)
				|| (!pmhDateList.contains(CalendarUtil
						.getCurrentWeekDayByCalendar(currentCal, "FRI", orgLocations.getDateFormat()).getTime()))
				|| !isNullOrEmpty(approvalStatus)
						&& (((approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()))
								&& (timesheetFor.equalsIgnoreCase("user") || !reopenButtonIsVisible))
								|| approvalStatus
										.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())
								|| (approvalStatus
										.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue())
										&& (!reopenButtonIsVisible && !timesheetFor.equalsIgnoreCase("user"))))) {
			disbleCondition5.setLength(0);
			disbleCondition5.append("disabled");
		}
		if ((!isFillMyTimeSheet)
				|| (!pmhDateList.contains(CalendarUtil
						.getCurrentWeekDayByCalendar(currentCal, "SAT", orgLocations.getDateFormat()).getTime()))
				|| !isNullOrEmpty(approvalStatus)
						&& (((approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()))
								&& (timesheetFor.equalsIgnoreCase("user") || !reopenButtonIsVisible))
								|| approvalStatus
										.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())
								|| (approvalStatus
										.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue())
										&& (!reopenButtonIsVisible && !timesheetFor.equalsIgnoreCase("user"))))) {
			disbleCondition6.setLength(0);
			disbleCondition6.append("disabled");
		}
		if ((!isFillMyTimeSheet)
				|| (!pmhDateList.contains(CalendarUtil
						.getCurrentWeekDayByCalendar(currentCal, "SUN", orgLocations.getDateFormat()).getTime()))
				|| !isNullOrEmpty(approvalStatus)
						&& (((approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()))
								&& (timesheetFor.equalsIgnoreCase("user") || !reopenButtonIsVisible))
								|| approvalStatus
										.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())
								|| (approvalStatus
										.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue())
										&& (!reopenButtonIsVisible && !timesheetFor.equalsIgnoreCase("user"))))) {
			disbleCondition7.setLength(0);
			disbleCondition7.append("disabled");
		}

		TimesheetSubmissions ts = timesheetSubmissionsService.getAllCandidateTimesheetSubmissionsByProjectIdAndMasterCandidateId(actualStartDate, actualEndDate,
						projectMember.getProjects().getId(), projectMember.getMasterCandidateId().getId());
		Collection<TimeSheetDayEntries> timesheetEntriesList = new ArrayList<>();
		TimeSheetSubmissionDTO tsDTO = new TimeSheetSubmissionDTO();
		if (ts != null) {
			tsDTO.setId(ts.getId());
			timesheetEntriesList = timesheetDayEntriesDao.getAllTimeSheetDayEntriesBySubmissionId(ts.getId());
		}

		/**
		 * Current Week Days
		 */

		LinkedHashMap<String, WeekDaysDTO> weekDaysMap = new LinkedHashMap<>();
		WeekDaysDTO monDayDTO = new WeekDaysDTO();
		monDayDTO.setDate(weekStartDate);
		monDayDTO.setDayOfTheDate("Mon");
		if (disbleCondition1.toString().equalsIgnoreCase("disabled")) {
			monDayDTO.setEditable(false);
		} else {
			monDayDTO.setEditable(true);
		}
		weekDaysMap.put("Mon", monDayDTO);

		WeekDaysDTO tuesDayDTO = new WeekDaysDTO();
		tuesDayDTO.setDate(tuesDayDate);
		tuesDayDTO.setDayOfTheDate("Tue");
		if (disbleCondition2.toString().equalsIgnoreCase("disabled")) {
			tuesDayDTO.setEditable(false);
		} else {
			tuesDayDTO.setEditable(true);
		}
		weekDaysMap.put("Tue", tuesDayDTO);

		WeekDaysDTO wedDayDTO = new WeekDaysDTO();
		wedDayDTO.setDate(WednesDayDate);
		wedDayDTO.setDayOfTheDate("Wed");
		if (disbleCondition3.toString().equalsIgnoreCase("disabled")) {
			wedDayDTO.setEditable(false);
		} else {
			wedDayDTO.setEditable(true);
		}
		weekDaysMap.put("Wed", wedDayDTO);

		WeekDaysDTO thursDayDTO = new WeekDaysDTO();
		thursDayDTO.setDate(ThursDayDate);
		thursDayDTO.setDayOfTheDate("Thu");
		if (disbleCondition4.toString().equalsIgnoreCase("disabled")) {
			thursDayDTO.setEditable(false);
		} else {
			thursDayDTO.setEditable(true);
		}
		weekDaysMap.put("Thu", thursDayDTO);

		WeekDaysDTO friDayDTO = new WeekDaysDTO();
		friDayDTO.setDate(FriDayDate);
		friDayDTO.setDayOfTheDate("Fri");
		if (disbleCondition5.toString().equalsIgnoreCase("disabled")) {
			friDayDTO.setEditable(false);
		} else {
			friDayDTO.setEditable(true);
		}
		weekDaysMap.put("Fri", friDayDTO);

		WeekDaysDTO satDayDTO = new WeekDaysDTO();
		satDayDTO.setDate(SaturdayDate);
		satDayDTO.setDayOfTheDate("Sat");
		if (disbleCondition6.toString().equalsIgnoreCase("disabled")) {
			satDayDTO.setEditable(false);
		} else {
			satDayDTO.setEditable(true);
		}

		weekDaysMap.put("Sat", satDayDTO);

		WeekDaysDTO sunDayDTO = new WeekDaysDTO();
		sunDayDTO.setDate(SundayDate);
		sunDayDTO.setDayOfTheDate("Sun");
		if (disbleCondition7.toString().equalsIgnoreCase("disabled")) {
			sunDayDTO.setEditable(false);
		} else {
			sunDayDTO.setEditable(true);
		}

		weekDaysMap.put("Sun", sunDayDTO);
		tsDTO = getTimesheetSubmissionDTOWithEntriesList(ts, tsDTO, weekDaysMap, timesheetEntriesList,
				orgLocations.getDateFormat());
		return tsDTO;
	}

	private TimeSheetSubmissionDTO getTimesheetSubmissionDetails(TimesheetExpenseEntryDetailsUtilDTO eedDTO,
			HttpServletRequest request) throws Exception {
		Boolean isFillMyTimeSheet = commonUtil.hasRole("ROLE_FILL_MY_TIMESHEETS");
		Boolean isModifyMyTimeSheet = commonUtil.hasRole("ROLE_MODIFY_MY_TIMESHEETS");
		Boolean isFillMemberTimesheet = commonUtil.hasRole("ROLE_FILL_MEMBER_TIMESHEETS");
		if (isFillMyTimeSheet || isFillMemberTimesheet) {
			if (!isFillMyTimeSheet && isFillMemberTimesheet) {
				isFillMyTimeSheet = isFillMemberTimesheet;
			}
		}
		/**
		 * Getting TimesheetStatus
		 */

		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if (loginUser == null && eedDTO.getUserId() != null) {
			loginUser = userService.getUserById(eedDTO.getUserId());
		}
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		if (orgLocations == null && StringUtils.isNotEmpty(eedDTO.getLocationCode())) {
			orgLocations = orgLocationService.getOrgLocationsByLocationCode(eedDTO.getLocationCode());
		}
		String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		Candidate candidate = (Candidate) SourceLeadSessionManager.getSessionAttribute(request, "candidate");
		if (candidate == null && loginUser != null && orgLocations != null) {
			candidate = candidateService.getCandidateByConsultantUserId(loginUser.getId(), orgLocations.getId());
		}

		Long projectMemberId = eedDTO.getProjectMembersId();
		String currentTimeFrequency = eedDTO.getCurrentTimeFrequency();
		String currentTimeSubFrequency = eedDTO.getCurrentTimeSubFrequency();
		String timesheetFor = eedDTO.getTimeSheetsFor();
		if (StringUtils.isEmpty(timesheetFor)) {
			timesheetFor = "user";
		}
		TimeSheetSubmissionDTO tsDTO = new TimeSheetSubmissionDTO();
		if (projectMemberId != null && StringUtils.isNotEmpty(currentTimeFrequency)
				&& StringUtils.isNotEmpty(currentTimeSubFrequency)) {
			ProjectMembers projectMember = projectMembersService.getProjectMemberById(projectMemberId);
			HashMap<String, String> statusMap = getTimesheetStatusByTimeSubFrequency(projectMember, timesheetFor,
					currentTimeFrequency, orgLocations, loginUser, isModifyMyTimeSheet, isFillMyTimeSheet,isGlobalAccess);
			tsDTO.setTimeSubFrequency(currentTimeSubFrequency);
			tsDTO.setTimeFrequency(currentTimeFrequency);
			tsDTO.setTimesheetFor(timesheetFor);
			tsDTO.setProjectMemberId(projectMemberId);
			tsDTO.setTimesheetStatus(statusMap.get("STATUS"));
			tsDTO.setReopenReason(statusMap.get("REOPEN_REASON"));
			tsDTO.setModifyButtonVisible(new Boolean(statusMap.get("IS_MODIFY_BUTTON_VISIBLE")));
			tsDTO.setReopenRejectedReason(statusMap.get("REOPEN_REJECTED_REASON"));
			tsDTO.setReopenButtonVisible(new Boolean(statusMap.get("IS_REOPEN_BUTTON_VISIBLE")));
			tsDTO.setReopenRequestSent(new Boolean(statusMap.get("REOPEN_REQUEST_SENT")));
			tsDTO.setReopenAcceptRejectButtonVisible(
					new Boolean(statusMap.get("IS_REOPEN_ACCEPT_REJECT_BUTTON_VISIBLE")));
			tsDTO.setReopenUserId(statusMap.get("REOPEN_USER_ID"));
			tsDTO.setRejectedReason(statusMap.get("REJECTED_REASON"));

			String approvalStatus = tsDTO.getTimesheetStatus();

			LinkedList<TimesheetTaskDTO> taskDTOList = new LinkedList<>();
			Collection<ProjectTasks> projectTasksList = projectTasksService
					.getAllTasksByProjectId(projectMember.getProjects().getId());
			if (projectTasksList != null && !projectTasksList.isEmpty()) {
				for (ProjectTasks pt : projectTasksList) {
					TimesheetTaskDTO taskDTO = new TimesheetTaskDTO();
					taskDTO.setId(pt.getId());
					taskDTO.setTaskName(pt.getTaskName());
					taskDTO.setTaskType(pt.getBillableType());
					if(pt.getGeneratedId() != null && StringUtils.isNotEmpty(pt.getGeneratedId())) {
						taskDTO.setGeneratedId(pt.getGeneratedId());
					}
					taskDTOList.add(taskDTO);
				}

			}
			StringBuilder disbleCondition1 = new StringBuilder("");
			StringBuilder disbleCondition2 = new StringBuilder("");
			StringBuilder disbleCondition3 = new StringBuilder("");
			StringBuilder disbleCondition4 = new StringBuilder("");
			StringBuilder disbleCondition5 = new StringBuilder("");
			StringBuilder disbleCondition6 = new StringBuilder("");
			StringBuilder disbleCondition7 = new StringBuilder("");

			String[] currentFrequency = currentTimeFrequency.split("To");
			String currentFrequencyStartDate = currentFrequency[0].trim();
			String currentFrequencyEndDate = currentFrequency[1].trim();
			
			Date freqStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
					currentFrequencyStartDate.replaceAll("/", "-"));
			Date freqEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
					currentFrequencyEndDate.replaceAll("/", "-"));

			String[] currentSubFrequency = currentTimeSubFrequency.split("To");
			String currentSubFrequencyStartDate = currentSubFrequency[0].trim();
			String currentSubFrequencyEndDate = currentSubFrequency[1].trim();

			Boolean reopenButtonIsVisible = new Boolean(statusMap.get("IS_Client_PrimeVendor"));

			Date actualStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
					currentSubFrequencyStartDate.replaceAll("/", "-"));
			Date actualEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
					currentSubFrequencyEndDate.replaceAll("/", "-"));
			int dayOfTheDateWithStartDate = CalendarUtil.getDayOfTheDate(
					commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), currentSubFrequencyStartDate));
			String weekStartDate = currentSubFrequencyStartDate;
			switch (dayOfTheDateWithStartDate) {
			case 1:
				weekStartDate = CalendarUtil.getPreviousDateByDecrementor(currentSubFrequencyStartDate, 6,
						orgLocations.getDateFormat());
				disbleCondition1.setLength(0);
				disbleCondition2.setLength(0);
				disbleCondition3.setLength(0);
				disbleCondition4.setLength(0);
				disbleCondition5.setLength(0);
				disbleCondition6.setLength(0);
				disbleCondition1.append("disabled");
				disbleCondition2.append("disabled");
				disbleCondition3.append("disabled");
				disbleCondition4.append("disabled");
				disbleCondition5.append("disabled");
				disbleCondition6.append("disabled");
				break;
			case 2:
				weekStartDate = CalendarUtil.getPreviousDateByDecrementor(currentSubFrequencyStartDate, 0,
						orgLocations.getDateFormat());
				break;
			case 3:
				weekStartDate = CalendarUtil.getPreviousDateByDecrementor(currentSubFrequencyStartDate, 1,
						orgLocations.getDateFormat());
				disbleCondition1.setLength(0);
				disbleCondition1.append("disabled");
				break;
			case 4:
				weekStartDate = CalendarUtil.getPreviousDateByDecrementor(currentSubFrequencyStartDate, 2,
						orgLocations.getDateFormat());
				disbleCondition1.setLength(0);
				disbleCondition2.setLength(0);
				disbleCondition1.append("disabled");
				disbleCondition2.append("disabled");
				break;
			case 5:
				weekStartDate = CalendarUtil.getPreviousDateByDecrementor(currentSubFrequencyStartDate, 3,
						orgLocations.getDateFormat());
				disbleCondition1.setLength(0);
				disbleCondition2.setLength(0);
				disbleCondition3.setLength(0);
				disbleCondition1.append("disabled");
				disbleCondition2.append("disabled");
				disbleCondition3.append("disabled");
				break;
			case 6:
				weekStartDate = CalendarUtil.getPreviousDateByDecrementor(currentSubFrequencyStartDate, 4,
						orgLocations.getDateFormat());
				disbleCondition1.setLength(0);
				disbleCondition2.setLength(0);
				disbleCondition3.setLength(0);
				disbleCondition4.setLength(0);
				disbleCondition1.append("disabled");
				disbleCondition2.append("disabled");
				disbleCondition3.append("disabled");
				disbleCondition4.append("disabled");
				break;
			case 7:
				weekStartDate = CalendarUtil.getPreviousDateByDecrementor(currentSubFrequencyStartDate, 5,
						orgLocations.getDateFormat());
				disbleCondition1.setLength(0);
				disbleCondition2.setLength(0);
				disbleCondition3.setLength(0);
				disbleCondition4.setLength(0);
				disbleCondition5.setLength(0);
				disbleCondition1.append("disabled");
				disbleCondition2.append("disabled");
				disbleCondition3.append("disabled");
				disbleCondition4.append("disabled");
				disbleCondition5.append("disabled");
				break;
			default:
				weekStartDate = CalendarUtil.getPreviousDateByDecrementor(currentSubFrequencyStartDate, 0,
						orgLocations.getDateFormat());
				break;
			}
			int dayOfTheDateWithEndDate = CalendarUtil.getDayOfTheDate(
					commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), currentSubFrequencyEndDate));
			switch (dayOfTheDateWithEndDate) {
			case 2:
				disbleCondition2.setLength(0);
				disbleCondition3.setLength(0);
				disbleCondition4.setLength(0);
				disbleCondition5.setLength(0);
				disbleCondition6.setLength(0);
				disbleCondition7.setLength(0);
				disbleCondition2.append("disabled");
				disbleCondition3.append("disabled");
				disbleCondition4.append("disabled");
				disbleCondition5.append("disabled");
				disbleCondition6.append("disabled");
				disbleCondition7.append("disabled");
				break;

			case 3:
				disbleCondition3.setLength(0);
				disbleCondition4.setLength(0);
				disbleCondition5.setLength(0);
				disbleCondition6.setLength(0);
				disbleCondition7.setLength(0);
				disbleCondition3.append("disabled");
				disbleCondition4.append("disabled");
				disbleCondition5.append("disabled");
				disbleCondition6.append("disabled");
				disbleCondition7.append("disabled");
				break;
			case 4:
				disbleCondition4.setLength(0);
				disbleCondition5.setLength(0);
				disbleCondition6.setLength(0);
				disbleCondition7.setLength(0);
				disbleCondition4.append("disabled");
				disbleCondition5.append("disabled");
				disbleCondition6.append("disabled");
				disbleCondition7.append("disabled");
				break;
			case 5:
				disbleCondition5.setLength(0);
				disbleCondition6.setLength(0);
				disbleCondition7.setLength(0);
				disbleCondition5.append("disabled");
				disbleCondition6.append("disabled");
				disbleCondition7.append("disabled");
				break;
			case 6:
				disbleCondition6.setLength(0);
				disbleCondition7.setLength(0);
				disbleCondition6.append("disabled");
				disbleCondition7.append("disabled");
				break;
			case 7:
				disbleCondition7.setLength(0);
				disbleCondition7.append("disabled");
				break;
			}

			Date actualWeekStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
					weekStartDate.replaceAll("/", "-"));
			String tuesDayDate = CalendarUtil.getNextDate(weekStartDate, orgLocations.getDateFormat());
			String WednesDayDate = CalendarUtil.getNextDate(tuesDayDate, orgLocations.getDateFormat());
			String ThursDayDate = CalendarUtil.getNextDate(WednesDayDate, orgLocations.getDateFormat());
			String FriDayDate = CalendarUtil.getNextDate(ThursDayDate, orgLocations.getDateFormat());
			String SaturdayDate = CalendarUtil.getNextDate(FriDayDate, orgLocations.getDateFormat());
			String SundayDate = CalendarUtil.getNextDate(SaturdayDate, orgLocations.getDateFormat());

			/**
			 * Here getting dates which are enabled in the current submission (Hint:In
			 * particular project,the member may assigned in the mid of the week)
			 */
			HashSet<Date> pmhDateList = projectMembersHistoryService
					.getAllProjectMemberHistoryDatesByProjectMemberIdAndWeekStartDateEndDate(projectMemberId,
							actualWeekStartDate, actualEndDate);

			Calendar currentCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
			currentCal.setLenient(false);
			currentCal.setTime(actualWeekStartDate);

			if ((!isFillMyTimeSheet) || (!pmhDateList.contains(actualWeekStartDate)) || !isNullOrEmpty(approvalStatus)
					&& (((approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()))
							&& (timesheetFor.equalsIgnoreCase("user") || !reopenButtonIsVisible))
							|| approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())
							|| (approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue())
									&& (!reopenButtonIsVisible && !timesheetFor.equalsIgnoreCase("user"))))) {
				disbleCondition1.setLength(0);
				disbleCondition1.append("disabled");
			}
			if ((!isFillMyTimeSheet)
					|| (!pmhDateList.contains(CalendarUtil
							.getCurrentWeekDayByCalendar(currentCal, "TUE", orgLocations.getDateFormat()).getTime()))
					|| !isNullOrEmpty(approvalStatus) && (((approvalStatus
							.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()))
							&& (timesheetFor.equalsIgnoreCase("user") || !reopenButtonIsVisible))
							|| approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())
							|| (approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue())
									&& (!reopenButtonIsVisible && !timesheetFor.equalsIgnoreCase("user"))))) {
				disbleCondition2.setLength(0);
				disbleCondition2.append("disabled");
			}
			if ((!isFillMyTimeSheet)
					|| (!pmhDateList.contains(CalendarUtil
							.getCurrentWeekDayByCalendar(currentCal, "WED", orgLocations.getDateFormat()).getTime()))
					|| !isNullOrEmpty(approvalStatus) && (((approvalStatus
							.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()))
							&& (timesheetFor.equalsIgnoreCase("user") || !reopenButtonIsVisible))
							|| approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())
							|| (approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue())
									&& (!reopenButtonIsVisible && !timesheetFor.equalsIgnoreCase("user"))))) {
				disbleCondition3.setLength(0);
				disbleCondition3.append("disabled");
			}
			if ((!isFillMyTimeSheet)
					|| (!pmhDateList.contains(CalendarUtil
							.getCurrentWeekDayByCalendar(currentCal, "THU", orgLocations.getDateFormat()).getTime()))
					|| !isNullOrEmpty(approvalStatus) && (((approvalStatus
							.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()))
							&& (timesheetFor.equalsIgnoreCase("user") || !reopenButtonIsVisible))
							|| approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())
							|| (approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue())
									&& (!reopenButtonIsVisible && !timesheetFor.equalsIgnoreCase("user"))))) {
				disbleCondition4.setLength(0);
				disbleCondition4.append("disabled");
			}
			if ((!isFillMyTimeSheet)
					|| (!pmhDateList.contains(CalendarUtil
							.getCurrentWeekDayByCalendar(currentCal, "FRI", orgLocations.getDateFormat()).getTime()))
					|| !isNullOrEmpty(approvalStatus) && (((approvalStatus
							.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()))
							&& (timesheetFor.equalsIgnoreCase("user") || !reopenButtonIsVisible))
							|| approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())
							|| (approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue())
									&& (!reopenButtonIsVisible && !timesheetFor.equalsIgnoreCase("user"))))) {
				disbleCondition5.setLength(0);
				disbleCondition5.append("disabled");
			}
			if ((!isFillMyTimeSheet)
					|| (!pmhDateList.contains(CalendarUtil
							.getCurrentWeekDayByCalendar(currentCal, "SAT", orgLocations.getDateFormat()).getTime()))
					|| !isNullOrEmpty(approvalStatus) && (((approvalStatus
							.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()))
							&& (timesheetFor.equalsIgnoreCase("user") || !reopenButtonIsVisible))
							|| approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())
							|| (approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue())
									&& (!reopenButtonIsVisible && !timesheetFor.equalsIgnoreCase("user"))))) {
				disbleCondition6.setLength(0);
				disbleCondition6.append("disabled");
			}
			if ((!isFillMyTimeSheet)
					|| (!pmhDateList.contains(CalendarUtil
							.getCurrentWeekDayByCalendar(currentCal, "SUN", orgLocations.getDateFormat()).getTime()))
					|| !isNullOrEmpty(approvalStatus) && (((approvalStatus
							.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()))
							&& (timesheetFor.equalsIgnoreCase("user") || !reopenButtonIsVisible))
							|| approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())
							|| (approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue())
									&& (!reopenButtonIsVisible && !timesheetFor.equalsIgnoreCase("user"))))) {
				disbleCondition7.setLength(0);
				disbleCondition7.append("disabled");
			}

			TimesheetSubmissions ts = timesheetSubmissionsService
					.getAllCandidateTimesheetSubmissionsByProjectIdAndMasterCandidateId(actualStartDate, actualEndDate,
							projectMember.getProjects().getId(), projectMember.getMasterCandidateId().getId());
			Collection<TimeSheetDayEntries> timesheetEntriesList = new ArrayList<>();
			if (ts != null) {
				tsDTO.setId(ts.getId());
				timesheetEntriesList = timesheetDayEntriesDao.getAllTimeSheetDayEntriesBySubmissionId(ts.getId());
			}

			/**
			 * Current Week Days
			 */

			LinkedHashMap<String, WeekDaysDTO> weekDaysMap = new LinkedHashMap<>();
			WeekDaysDTO monDayDTO = new WeekDaysDTO();
			monDayDTO.setDate(weekStartDate);
			monDayDTO.setDayOfTheDate("Mon");
			if (disbleCondition1.toString().equalsIgnoreCase("disabled")) {
				monDayDTO.setEditable(false);
			} else {
				monDayDTO.setEditable(true);
			}
			weekDaysMap.put("Mon", monDayDTO);

			WeekDaysDTO tuesDayDTO = new WeekDaysDTO();
			tuesDayDTO.setDate(tuesDayDate);
			tuesDayDTO.setDayOfTheDate("Tue");
			if (disbleCondition2.toString().equalsIgnoreCase("disabled")) {
				tuesDayDTO.setEditable(false);
			} else {
				tuesDayDTO.setEditable(true);
			}
			weekDaysMap.put("Tue", tuesDayDTO);

			WeekDaysDTO wedDayDTO = new WeekDaysDTO();
			wedDayDTO.setDate(WednesDayDate);
			wedDayDTO.setDayOfTheDate("Wed");
			if (disbleCondition3.toString().equalsIgnoreCase("disabled")) {
				wedDayDTO.setEditable(false);
			} else {
				wedDayDTO.setEditable(true);
			}
			weekDaysMap.put("Wed", wedDayDTO);

			WeekDaysDTO thursDayDTO = new WeekDaysDTO();
			thursDayDTO.setDate(ThursDayDate);
			thursDayDTO.setDayOfTheDate("Thu");
			if (disbleCondition4.toString().equalsIgnoreCase("disabled")) {
				thursDayDTO.setEditable(false);
			} else {
				thursDayDTO.setEditable(true);
			}
			weekDaysMap.put("Thu", thursDayDTO);

			WeekDaysDTO friDayDTO = new WeekDaysDTO();
			friDayDTO.setDate(FriDayDate);
			friDayDTO.setDayOfTheDate("Fri");
			if (disbleCondition5.toString().equalsIgnoreCase("disabled")) {
				friDayDTO.setEditable(false);
			} else {
				friDayDTO.setEditable(true);
			}
			weekDaysMap.put("Fri", friDayDTO);

			WeekDaysDTO satDayDTO = new WeekDaysDTO();
			satDayDTO.setDate(SaturdayDate);
			satDayDTO.setDayOfTheDate("Sat");
			if (disbleCondition6.toString().equalsIgnoreCase("disabled")) {
				satDayDTO.setEditable(false);
			} else {
				satDayDTO.setEditable(true);
			}

			weekDaysMap.put("Sat", satDayDTO);

			WeekDaysDTO sunDayDTO = new WeekDaysDTO();
			sunDayDTO.setDate(SundayDate);
			sunDayDTO.setDayOfTheDate("Sun");
			if (disbleCondition7.toString().equalsIgnoreCase("disabled")) {
				sunDayDTO.setEditable(false);
			} else {
				sunDayDTO.setEditable(true);
			}

			weekDaysMap.put("Sun", sunDayDTO);

			/**
			 * TimesheetEntries List
			 */
			tsDTO = getTimesheetSubmissionDTOWithEntriesList(ts, tsDTO, weekDaysMap, timesheetEntriesList,
					orgLocations.getDateFormat());

			if (ts != null) {
				tsDTO.setId(ts.getId());
			}
			
			if (!timesheetFor.equalsIgnoreCase("user") && actualStartDate != null && actualEndDate != null) {
				Date monthStartDate = CalendarUtil.getMonthStartDateByGivenDate(actualEndDate, Constants.CURRENT_MONTH)
						.get("STARTDATE");
				Date monthEndDate = CalendarUtil.getMonthEndDateByGivenDate(actualEndDate, Constants.CURRENT_MONTH)
						.get("ENDDATE");
				if (projectMember != null && projectMember.getId() != null) {
					
					SubmissionApproverHierarchy sah = submissionApproverHierarchyService
							.getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLoginForTsReports(
									loginUser.getId(), orgLocations.getId(), projectMember.getId(), monthStartDate,
									monthEndDate,isGlobalAccess);
					if (sah != null) {
						tsDTO.setProjectToDateTotalHours(sah.getProjectToDateTotalHours());
						tsDTO.setProjectToDateBillableHours(sah.getProjectToDateBillableHours());
						tsDTO.setProjectToDateNonBillableHours(sah.getProjectToDateNonBillableHours());
						tsDTO.setPreviousMonthTotalHours(sah.getPreviousMonthTotalHours());
						tsDTO.setPreviousMonthBillableHours(sah.getPreviousMonthBillableHours());
						tsDTO.setPreviousMonthNonBillableHours(sah.getPreviousMonthNonBillableHours());
						float monthTotalHours = 0f;
						float monthBillableTotalHours = 0f;
						float monthNonBillableTotalHours = 0f;
						Map<String, String> frequencyDropDown = timesheetApprovalFrequencyGeneratorUtil
								.getTabFrequencyListFromProjectInvoiceFrequency(
										commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
												monthStartDate),
										commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
												monthEndDate),
										orgLocations.getDateFormat());
						if (frequencyDropDown != null && !frequencyDropDown.isEmpty()) {
							for (Map.Entry<String, String> entry : frequencyDropDown.entrySet()) {
								Date k = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
										entry.getKey().replaceAll("/", "-"));
								if (actualStartDate.equals(k))
									continue;
								try {
									Float weekBillableHours = 0f;
									if (sah.getWeekBillableHoursMap().get(k) != null) {
										weekBillableHours = sah.getWeekBillableHoursMap().get(k);
									}
									Float weekNonBillableHours = 0f;
									if (sah.getWeekNonBillableHoursMap().get(k) != null) {
										weekNonBillableHours = sah.getWeekNonBillableHoursMap().get(k);
									}
									Float weekTotalHours = 0f;
									if (sah.getWeekTotalHoursMap().get(k) != null) {
										weekTotalHours = sah.getWeekTotalHoursMap().get(k);
									}
									monthTotalHours = CommonUtil.getCombinedHours(monthTotalHours, weekTotalHours);
									monthBillableTotalHours = CommonUtil.getCombinedHours(monthBillableTotalHours,
											weekBillableHours);
									monthNonBillableTotalHours = CommonUtil.getCombinedHours(monthNonBillableTotalHours,
											weekNonBillableHours);
								} catch (Exception e) {
									e.printStackTrace();
								}

							}
							tsDTO.setCurrentMonthBillableHours(
									Float.parseFloat(CommonUtil.getHoursDisplayFormat(monthBillableTotalHours,null)));
							tsDTO.setCurrentMonthNonBillableHours(
									Float.parseFloat(CommonUtil.getHoursDisplayFormat(monthNonBillableTotalHours,null)));
							tsDTO.setCurrentMonthTotalHours(
									Float.parseFloat(CommonUtil.getHoursDisplayFormat(monthTotalHours,null)));

							if (CalendarUtil.isGivenDateBetweenDateRange(actualStartDate, actualEndDate,
									monthStartDate)) {
								if (CalendarUtil.getDayOfTheDate(monthStartDate) != 1) {
									tsDTO.setIncrStartsFrom(CalendarUtil.getDayOfTheDate(monthStartDate) - 1);
								} else {
									tsDTO.setIncrStartsFrom(7);
								}

							} else {
								tsDTO.setIncrStartsFrom(1);
							}
							if (monthStartDate != null && monthEndDate != null) {
								LinkedHashSet<String> currentMonth = CalendarUtil
										.getMonthStringsFromStartDateAndEndDate(monthStartDate, monthEndDate);
								String currentYear = CalendarUtil.getYearFromGivenDate(monthStartDate);
								if (currentMonth != null && !currentMonth.isEmpty()) {
									StringBuilder monthYear = new StringBuilder();
									tsDTO.setCurrentMonthName(monthYear.append(currentMonth.iterator().next())
											.append("-").append(currentYear).toString());
								}
							}
						}
					}
				}
			}

			
					
			/**
			 * Project Task List
			 */
			tsDTO.setTimesheetTaskList(taskDTOList);
			if (StringUtils.isNotEmpty(currentFrequencyEndDate)) {
				List<FileInfo> fileInfoList = new ArrayList<>();
				Date frequencyEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
						currentFrequencyEndDate.replaceAll("/", "-"));
				if (frequencyEndDate.equals(actualEndDate) || (StringUtils.isNotEmpty(timesheetFor) && !timesheetFor.equalsIgnoreCase("approver"))) {
					List<TimesheetSubmissionsDocuments> timesheetSubmissionsDocumentsList = null;
					if (ts != null) {
						 timesheetSubmissionsDocumentsList = timesheetSubmissionsDocumentsService.getAllTimesheetSubmissionsDocumentsByProjectIdMasterCandidateIdAndFrequencyStartDateEndDate(ts.getMasterCandidateId().getId(), ts.getProjects().getId(), freqStartDate, freqEndDate);
						if (timesheetSubmissionsDocumentsList != null && !timesheetSubmissionsDocumentsList.isEmpty()) {
							for (TimesheetSubmissionsDocuments tsd : timesheetSubmissionsDocumentsList) {
								FileInfo fileInfo = new FileInfo();
								fileInfo.setFileDbId(tsd.getId().toString());
								fileInfo.setFileName(tsd.getFileName());
								fileInfo.setFileURL(tsd.getFileUrl());
								fileInfo.setStatus(tsd.getStatus());
								fileInfo.setContentType(tsd.getFileContentType());
								fileInfoList.add(fileInfo);
							}
						}
					}

					if (projectMember.getProjects().getIsTimesheetAttachmentRequired() != null
							&& projectMember.getProjects().getIsTimesheetAttachmentRequired() != 0) {
						tsDTO.setAttachmentRequired(true);
					} else {
						tsDTO.setAttachmentRequired(false);
					}

					if (frequencyEndDate.equals(actualEndDate)) {
						tsDTO.setWeekCommentVisible(true);
					}

					if (ts != null && ts.getWeekcomments() != null && !ts.getWeekcomments().isEmpty()) {
						tsDTO.setWeekComment(ts.getWeekcomments());
					}
				}
				tsDTO.setFileInfo(fileInfoList);
			}
			if (isFillMyTimeSheet) {
				if (!pmhDateList.isEmpty() && !isNullOrEmpty(approvalStatus)
						&& !(approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue())
								&& (timesheetFor.equalsIgnoreCase("user") || !reopenButtonIsVisible))
						&& !approvalStatus
								.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("approved").getValue())) {

					if (approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("new").getValue())
							|| approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("draft").getValue())
							|| !((approvalStatus
									.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue())
									|| approvalStatus
											.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue()))
									&& (timesheetFor.equalsIgnoreCase("user") || !reopenButtonIsVisible))
							|| approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue())
									&& (timesheetFor.equalsIgnoreCase("user"))) {
						tsDTO.setSaveButtonVisible(true);
						if (StringUtils.isNotEmpty(currentFrequencyEndDate)) {
							Date frequencyEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
									currentFrequencyEndDate.replaceAll("/", "-"));
							Date terminationDate = null;
							if(projectMemberId != null) {
								ProjectMembersHistory pmh = projectMembersHistoryService.getProjectMembersOpenHistoryByProjectMemberId(projectMemberId);
								if(pmh == null) {
									pmh = projectMembersHistoryService.getProjectMembersRecentClosedByProjectMemberId(projectMemberId);
								}
								 terminationDate = pmh.getProjectTerminationDate();
							}
							
							if ( frequencyEndDate.equals(actualEndDate) || 
									(terminationDate != null &&(terminationDate.equals(actualStartDate) || terminationDate.equals(actualEndDate) || (terminationDate.before(actualEndDate) && terminationDate.after(actualStartDate))))
							   )
								tsDTO.setSubmitButtonVisible(true);
						}

						if (!isNullOrEmpty(approvalStatus)
								&& approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("New").getValue()))
							tsDTO.setCancelButtonVisible(true);
					}
				} else {
					tsDTO.setSaveButtonVisible(false);
					tsDTO.setSubmitButtonVisible(false);
					tsDTO.setCancelButtonVisible(false);
				}
				if (ts != null && ts.getWeekcomments() != null && !ts.getWeekcomments().isEmpty()) {
					tsDTO.setWeekComment(ts.getWeekcomments());
				}else if(projectMember != null  && freqStartDate != null && freqEndDate != null){
					String comment = timesheetSubmissionsService.getWeekCommentByCurrentFrequency(freqStartDate, freqEndDate, projectMember.getProjects().getId(),projectMember.getMasterCandidateId().getId());
					tsDTO.setWeekComment(comment);
				}
			}
			tsDTO.setResponseStatus("success");
		} else {
			tsDTO.setResponseStatus("failure");
		}
		return tsDTO;
	}

	private TimeSheetSubmissionDTO getTimesheetSubmissionDTOWithEntriesList(TimesheetSubmissions ts,
			TimeSheetSubmissionDTO tsDTO, LinkedHashMap<String, WeekDaysDTO> weekDaysMap,
			Collection<TimeSheetDayEntries> timesheetEntriesList, String dateFormat) {

		LinkedList<TimesheetEntriesDTO> entriesDTOList = new LinkedList<>();
		String monDayTotalHours = "HH:MM";
		String monDayBillableHours = "HH:MM";
		String monDayNonBillableHours = "HH:MM";

		String tuesDayTotalHours = "HH:MM";
		String tuesDayBillableHours = "HH:MM";
		String tuesDayNonBillableHours = "HH:MM";

		String wedDayTotalHours = "HH:MM";
		String wedDayBillableHours = "HH:MM";
		String wedDayNonBillableHours = "HH:MM";

		String thursDayTotalHours = "HH:MM";
		String thursDayBillableHours = "HH:MM";
		String thursDayNonBillableHours = "HH:MM";

		String friDayTotalHours = "HH:MM";
		String friDayBillableHours = "HH:MM";
		String friDayNonBillableHours = "HH:MM";

		String satDayTotalHours = "HH:MM";
		String satDayBillableHours = "HH:MM";
		String satDayNonBillableHours = "HH:MM";

		String sunDayTotalHours = "HH:MM";
		String sunDayBillableHours = "HH:MM";
		String sunDayNonBillableHours = "HH:MM";

		Float totalHours = 0f;
		Float billableHours = 0f;
		Float nonBillableHours = 0f;
		ArrayList<WeekDaysDTO> weekDays = new ArrayList<>();
		WeekDaysDTO monDayDTO = weekDaysMap.get("Mon");
		WeekDaysDTO tuesDayDTO = weekDaysMap.get("Tue");
		WeekDaysDTO wedDayDTO = weekDaysMap.get("Wed");
		WeekDaysDTO thursDayDTO = weekDaysMap.get("Thu");
		WeekDaysDTO friDayDTO = weekDaysMap.get("Fri");
		WeekDaysDTO satDayDTO = weekDaysMap.get("Sat");
		WeekDaysDTO sunDayDTO = weekDaysMap.get("Sun");
		if (timesheetEntriesList != null && !timesheetEntriesList.isEmpty()) {
			HashSet<String> taskSize = new HashSet<>();
			for (TimeSheetDayEntries te : timesheetEntriesList) {
				String billableType = "Billable";
				TimesheetTaskDTO timesheetTaskDTO = new TimesheetTaskDTO();
				if (te.getProjectTask() != null) {
					String ticketNumber = "";
					String  hourlyType = "";
					if(StringUtils.isNotEmpty(te.getTicketNumber())) {
						ticketNumber = te.getTicketNumber();
					}
					
					if(StringUtils.isNotEmpty(te.getHourlyType())) {
						hourlyType = te.getHourlyType();
					}
					String taskIdWithRef = te.getProjectTask().getId()+"##"+ticketNumber+"##"+hourlyType;
					taskSize.add(taskIdWithRef);
					billableType = te.getTaskType();
					timesheetTaskDTO.setId(te.getProjectTask().getId());
					timesheetTaskDTO.setTaskName(te.getProjectTask().getTaskName());
					timesheetTaskDTO.setTicketNumber(te.getTicketNumber());
				}

				timesheetTaskDTO.setTaskType(billableType);

				if (monDayDTO.getDate()
						.equalsIgnoreCase(commonUtil.dateStrFromOrgDateFormatter(dateFormat, te.getTimesheetDate()))) {
					String mondayHours = te.getTotalHours();
					if (StringUtils.isEmpty(mondayHours)) {
						mondayHours = "HH:MM";
					}
					TimesheetEntriesDTO teMonDTO = new TimesheetEntriesDTO();
					teMonDTO.setId(te.getId());
					teMonDTO.setTimesheetTaskDTO(timesheetTaskDTO);
					teMonDTO.setDayOfTheDate("Mon");
					teMonDTO.setTicketNumber(te.getTicketNumber());
					teMonDTO.setHourlyType(te.getHourlyType());
					teMonDTO.setDate(monDayDTO.getDate());
					teMonDTO.setEditable(monDayDTO.isEditable());
					teMonDTO.setHours(mondayHours);
					teMonDTO.setComments(te.getComments());
					entriesDTOList.add(teMonDTO);
					if (StringUtils.isNotEmpty(mondayHours) && !mondayHours.equalsIgnoreCase("HH:MM")) {
						if (StringUtils.isNotEmpty(monDayTotalHours) && monDayTotalHours.equalsIgnoreCase("HH:MM")) {
							monDayTotalHours = "0.0";
							monDayBillableHours = "0.0";
							monDayNonBillableHours = "0.0";
						}
						monDayTotalHours = CommonUtil.getCombinedHours(Float.parseFloat(mondayHours.replace(":", ".")),
								Float.parseFloat(monDayTotalHours)).toString();
						if (StringUtils.isNotBlank(billableType) && billableType.equalsIgnoreCase("Non-Billable")) {
							monDayNonBillableHours = CommonUtil
									.getCombinedHours(Float.parseFloat(mondayHours.replace(":", ".")),
											Float.parseFloat(monDayNonBillableHours))
									.toString();
						} else {
							monDayBillableHours = CommonUtil
									.getCombinedHours(Float.parseFloat(mondayHours.replace(":", ".")),
											Float.parseFloat(monDayBillableHours))
									.toString();
						}
					}

				} else if (tuesDayDTO.getDate()
						.equalsIgnoreCase(commonUtil.dateStrFromOrgDateFormatter(dateFormat, te.getTimesheetDate()))) {
					String tuesdayHours = te.getTotalHours();
					if (StringUtils.isEmpty(tuesdayHours)) {
						tuesdayHours = "HH:MM";
					}
					TimesheetEntriesDTO teTueDTO = new TimesheetEntriesDTO();
					teTueDTO.setId(te.getId());
					teTueDTO.setTimesheetTaskDTO(timesheetTaskDTO);
					teTueDTO.setDayOfTheDate("Tue");
					teTueDTO.setTicketNumber(te.getTicketNumber());
					teTueDTO.setHourlyType(te.getHourlyType());
					teTueDTO.setDate(tuesDayDTO.getDate());
					teTueDTO.setEditable(tuesDayDTO.isEditable());
					teTueDTO.setHours(tuesdayHours);
					teTueDTO.setComments(te.getComments());
					entriesDTOList.add(teTueDTO);
					if (StringUtils.isNotEmpty(tuesdayHours) && !tuesdayHours.equalsIgnoreCase("HH:MM")) {
						if (StringUtils.isNotEmpty(tuesDayTotalHours) && tuesDayTotalHours.equalsIgnoreCase("HH:MM")) {
							tuesDayTotalHours = "0.0";
							tuesDayBillableHours = "0.0";
							tuesDayNonBillableHours = "0.0";
						}
						tuesDayTotalHours = CommonUtil
								.getCombinedHours(Float.parseFloat(tuesdayHours.replace(":", ".")),
										Float.parseFloat(tuesDayTotalHours))
								.toString();
						if (StringUtils.isNotBlank(billableType) && billableType.equalsIgnoreCase("Non-Billable")) {
							tuesDayNonBillableHours = CommonUtil
									.getCombinedHours(Float.parseFloat(tuesdayHours.replace(":", ".")),
											Float.parseFloat(tuesDayNonBillableHours))
									.toString();
						} else {
							tuesDayBillableHours = CommonUtil
									.getCombinedHours(Float.parseFloat(tuesdayHours.replace(":", ".")),
											Float.parseFloat(tuesDayBillableHours))
									.toString();
						}
					}

				} else if (wedDayDTO.getDate()
						.equalsIgnoreCase(commonUtil.dateStrFromOrgDateFormatter(dateFormat, te.getTimesheetDate()))) {
					String weddayHours = te.getTotalHours();
					if (StringUtils.isEmpty(weddayHours)) {
						weddayHours = "HH:MM";
					}
					TimesheetEntriesDTO teWedDTO = new TimesheetEntriesDTO();
					teWedDTO.setId(te.getId());
					teWedDTO.setTimesheetTaskDTO(timesheetTaskDTO);
					teWedDTO.setDayOfTheDate("Wed");
					teWedDTO.setTicketNumber(te.getTicketNumber());
					teWedDTO.setHourlyType(te.getHourlyType());
					teWedDTO.setDate(wedDayDTO.getDate());
					teWedDTO.setEditable(wedDayDTO.isEditable());
					teWedDTO.setHours(weddayHours);
					teWedDTO.setComments(te.getComments());
					entriesDTOList.add(teWedDTO);
					if (StringUtils.isNotEmpty(weddayHours) && !weddayHours.equalsIgnoreCase("HH:MM")) {
						if (StringUtils.isNotEmpty(wedDayTotalHours) && wedDayTotalHours.equalsIgnoreCase("HH:MM")) {
							wedDayTotalHours = "0.0";
							wedDayBillableHours = "0.0";
							wedDayNonBillableHours = "0.0";
						}
						wedDayTotalHours = CommonUtil.getCombinedHours(Float.parseFloat(weddayHours.replace(":", ".")),
								Float.parseFloat(wedDayTotalHours)).toString();
						if (StringUtils.isNotBlank(billableType) && billableType.equalsIgnoreCase("Non-Billable")) {
							wedDayNonBillableHours = CommonUtil
									.getCombinedHours(Float.parseFloat(weddayHours.replace(":", ".")),
											Float.parseFloat(wedDayNonBillableHours))
									.toString();
						} else {
							wedDayBillableHours = CommonUtil
									.getCombinedHours(Float.parseFloat(weddayHours.replace(":", ".")),
											Float.parseFloat(wedDayBillableHours))
									.toString();
						}
					}

				} else if (thursDayDTO.getDate()
						.equalsIgnoreCase(commonUtil.dateStrFromOrgDateFormatter(dateFormat, te.getTimesheetDate()))) {
					String thursdayHours = te.getTotalHours();
					if (StringUtils.isEmpty(thursdayHours)) {
						thursdayHours = "HH:MM";
					}
					TimesheetEntriesDTO teThuDTO = new TimesheetEntriesDTO();
					teThuDTO.setId(te.getId());
					teThuDTO.setTimesheetTaskDTO(timesheetTaskDTO);
					teThuDTO.setDayOfTheDate("Thu");
					teThuDTO.setTicketNumber(te.getTicketNumber());
					teThuDTO.setHourlyType(te.getHourlyType());
					teThuDTO.setDate(thursDayDTO.getDate());
					teThuDTO.setEditable(thursDayDTO.isEditable());
					teThuDTO.setHours(thursdayHours);
					teThuDTO.setComments(te.getComments());
					entriesDTOList.add(teThuDTO);
					if (StringUtils.isNotEmpty(thursdayHours) && !thursdayHours.equalsIgnoreCase("HH:MM")) {
						if (StringUtils.isNotEmpty(thursDayTotalHours)
								&& thursDayTotalHours.equalsIgnoreCase("HH:MM")) {
							thursDayTotalHours = "0.0";
							thursDayBillableHours = "0.0";
							thursDayNonBillableHours = "0.0";
						}
						thursDayTotalHours = CommonUtil
								.getCombinedHours(Float.parseFloat(thursdayHours.replace(":", ".")),
										Float.parseFloat(thursDayTotalHours))
								.toString();
						if (StringUtils.isNotBlank(billableType) && billableType.equalsIgnoreCase("Non-Billable")) {
							thursDayNonBillableHours = CommonUtil
									.getCombinedHours(Float.parseFloat(thursdayHours.replace(":", ".")),
											Float.parseFloat(thursDayNonBillableHours))
									.toString();
						} else {
							thursDayBillableHours = CommonUtil
									.getCombinedHours(Float.parseFloat(thursdayHours.replace(":", ".")),
											Float.parseFloat(thursDayBillableHours))
									.toString();
						}
					}

				} else if (friDayDTO.getDate()
						.equalsIgnoreCase(commonUtil.dateStrFromOrgDateFormatter(dateFormat, te.getTimesheetDate()))) {
					String fridayHours = te.getTotalHours();
					if (StringUtils.isEmpty(fridayHours)) {
						fridayHours = "HH:MM";
					}
					TimesheetEntriesDTO teFriDTO = new TimesheetEntriesDTO();
					teFriDTO.setId(te.getId());
					teFriDTO.setTimesheetTaskDTO(timesheetTaskDTO);
					teFriDTO.setDayOfTheDate("Fri");
					teFriDTO.setTicketNumber(te.getTicketNumber());
					teFriDTO.setHourlyType(te.getHourlyType());
					teFriDTO.setDate(friDayDTO.getDate());
					teFriDTO.setEditable(friDayDTO.isEditable());
					teFriDTO.setHours(fridayHours);
					teFriDTO.setComments(te.getComments());
					entriesDTOList.add(teFriDTO);
					if (StringUtils.isNotEmpty(fridayHours) && !fridayHours.equalsIgnoreCase("HH:MM")) {
						if (StringUtils.isNotEmpty(friDayTotalHours) && friDayTotalHours.equalsIgnoreCase("HH:MM")) {
							friDayTotalHours = "0.0";
							friDayBillableHours = "0.0";
							friDayNonBillableHours = "0.0";
						}
						friDayTotalHours = CommonUtil.getCombinedHours(Float.parseFloat(fridayHours.replace(":", ".")),
								Float.parseFloat(friDayTotalHours)).toString();
						if (StringUtils.isNotBlank(billableType) && billableType.equalsIgnoreCase("Non-Billable")) {
							friDayNonBillableHours = CommonUtil
									.getCombinedHours(Float.parseFloat(fridayHours.replace(":", ".")),
											Float.parseFloat(friDayNonBillableHours))
									.toString();
						} else {
							friDayBillableHours = CommonUtil
									.getCombinedHours(Float.parseFloat(fridayHours.replace(":", ".")),
											Float.parseFloat(friDayBillableHours))
									.toString();
						}
					}

				} else if (satDayDTO.getDate()
						.equalsIgnoreCase(commonUtil.dateStrFromOrgDateFormatter(dateFormat, te.getTimesheetDate()))) {
					String satdayHours = te.getTotalHours();
					if (StringUtils.isEmpty(satdayHours)) {
						satdayHours = "HH:MM";
					}
					TimesheetEntriesDTO teSatDTO = new TimesheetEntriesDTO();
					teSatDTO.setId(te.getId());
					teSatDTO.setTimesheetTaskDTO(timesheetTaskDTO);
					teSatDTO.setDayOfTheDate("Sat");
					teSatDTO.setTicketNumber(te.getTicketNumber());
					teSatDTO.setHourlyType(te.getHourlyType());
					teSatDTO.setDate(satDayDTO.getDate());
					teSatDTO.setEditable(satDayDTO.isEditable());
					teSatDTO.setHours(satdayHours);
					teSatDTO.setComments(te.getComments());
					entriesDTOList.add(teSatDTO);
					if (StringUtils.isNotEmpty(satdayHours) && !satdayHours.equalsIgnoreCase("HH:MM")) {
						if (StringUtils.isNotEmpty(satDayTotalHours) && satDayTotalHours.equalsIgnoreCase("HH:MM")) {
							satDayTotalHours = "0.0";
							satDayBillableHours = "0.0";
							satDayNonBillableHours = "0.0";
						}
						satDayTotalHours = CommonUtil.getCombinedHours(Float.parseFloat(satdayHours.replace(":", ".")),
								Float.parseFloat(satDayTotalHours)).toString();
						if (StringUtils.isNotBlank(billableType) && billableType.equalsIgnoreCase("Non-Billable")) {
							satDayNonBillableHours = CommonUtil
									.getCombinedHours(Float.parseFloat(satdayHours.replace(":", ".")),
											Float.parseFloat(satDayNonBillableHours))
									.toString();
						} else {
							satDayBillableHours = CommonUtil
									.getCombinedHours(Float.parseFloat(satdayHours.replace(":", ".")),
											Float.parseFloat(satDayBillableHours))
									.toString();
						}
					}

				} else if (sunDayDTO.getDate()
						.equalsIgnoreCase(commonUtil.dateStrFromOrgDateFormatter(dateFormat, te.getTimesheetDate()))) {
					String sundayHours = te.getTotalHours();
					if (StringUtils.isEmpty(sundayHours)) {
						sundayHours = "HH:MM";
					}
					TimesheetEntriesDTO teSunDTO = new TimesheetEntriesDTO();
					teSunDTO.setId(te.getId());
					teSunDTO.setTimesheetTaskDTO(timesheetTaskDTO);
					teSunDTO.setDayOfTheDate("Sun");
					teSunDTO.setTicketNumber(te.getTicketNumber());
					teSunDTO.setHourlyType(te.getHourlyType());
					teSunDTO.setDate(sunDayDTO.getDate());
					teSunDTO.setEditable(sunDayDTO.isEditable());
					teSunDTO.setHours(sundayHours);
					teSunDTO.setComments(te.getComments());
					entriesDTOList.add(teSunDTO);
					if (StringUtils.isNotEmpty(sundayHours) && !sundayHours.equalsIgnoreCase("HH:MM")) {
						if (StringUtils.isNotEmpty(sunDayTotalHours) && sunDayTotalHours.equalsIgnoreCase("HH:MM")) {
							sunDayTotalHours = "0.0";
							sunDayNonBillableHours = "0.0";
							sunDayBillableHours = "0.0";
						}
						sunDayTotalHours = CommonUtil.getCombinedHours(Float.parseFloat(sundayHours.replace(":", ".")),
								Float.parseFloat(sunDayTotalHours)).toString();
						if (StringUtils.isNotBlank(billableType) && billableType.equalsIgnoreCase("Non-Billable")) {
							sunDayNonBillableHours = CommonUtil
									.getCombinedHours(Float.parseFloat(sundayHours.replace(":", ".")),
											Float.parseFloat(sunDayNonBillableHours))
									.toString();
						} else {
							sunDayBillableHours = CommonUtil
									.getCombinedHours(Float.parseFloat(sundayHours.replace(":", ".")),
											Float.parseFloat(sunDayBillableHours))
									.toString();
						}
					}

				}
			}
			tsDTO.setMaxRowCount(taskSize.size());
		}

		tsDTO.setTimesheetEntries(entriesDTOList);

		if (ts != null && StringUtils.isNotBlank(ts.getTotalHours()))
			totalHours = Float.parseFloat(ts.getTotalHours());
		tsDTO.setTotalHours(CommonUtil.getHoursDisplayFormat(totalHours,null));

		if (ts != null && StringUtils.isNotBlank(ts.getBillableHours()))
			billableHours = Float.parseFloat(ts.getBillableHours());
		tsDTO.setBillableHours(CommonUtil.getHoursDisplayFormat(billableHours,null));

		if (ts != null && StringUtils.isNotBlank(ts.getNonBillableHours()))
			nonBillableHours = Float.parseFloat(ts.getNonBillableHours());
		tsDTO.setNonBillableHours(CommonUtil.getHoursDisplayFormat(nonBillableHours,null));
		if (StringUtils.isNotEmpty(monDayTotalHours) && monDayTotalHours.equalsIgnoreCase("HH:MM")) {
			monDayDTO.setTotalHours(monDayTotalHours);
			monDayDTO.setBillableHours(monDayBillableHours);
			monDayDTO.setNonBillableHours(monDayNonBillableHours);
		} else {
			monDayDTO.setTotalHours(CommonUtil.getHoursDisplayFormat(Float.parseFloat(monDayTotalHours),null));
			monDayDTO.setBillableHours(CommonUtil.getHoursDisplayFormat(Float.parseFloat(monDayBillableHours),null));
			monDayDTO.setNonBillableHours(CommonUtil.getHoursDisplayFormat(Float.parseFloat(monDayNonBillableHours),null));
		}
		weekDays.add(monDayDTO);
		if (StringUtils.isNotEmpty(tuesDayTotalHours) && tuesDayTotalHours.equalsIgnoreCase("HH:MM")) {
			tuesDayDTO.setTotalHours(tuesDayTotalHours);
			tuesDayDTO.setBillableHours(tuesDayBillableHours);
			tuesDayDTO.setNonBillableHours(tuesDayNonBillableHours);
		} else {
			tuesDayDTO.setTotalHours(CommonUtil.getHoursDisplayFormat(Float.parseFloat(tuesDayTotalHours),null));
			tuesDayDTO.setBillableHours(CommonUtil.getHoursDisplayFormat(Float.parseFloat(tuesDayBillableHours),null));
			tuesDayDTO.setNonBillableHours(CommonUtil.getHoursDisplayFormat(Float.parseFloat(tuesDayNonBillableHours),null));
		}

		weekDays.add(tuesDayDTO);
		if (StringUtils.isNotEmpty(wedDayTotalHours) && wedDayTotalHours.equalsIgnoreCase("HH:MM")) {
			wedDayDTO.setTotalHours(wedDayTotalHours);
			wedDayDTO.setBillableHours(wedDayBillableHours);
			wedDayDTO.setNonBillableHours(wedDayNonBillableHours);
		} else {
			wedDayDTO.setTotalHours(CommonUtil.getHoursDisplayFormat(Float.parseFloat(wedDayTotalHours),null));
			wedDayDTO.setBillableHours(CommonUtil.getHoursDisplayFormat(Float.parseFloat(wedDayBillableHours),null));
			wedDayDTO.setNonBillableHours(CommonUtil.getHoursDisplayFormat(Float.parseFloat(wedDayNonBillableHours),null));
		}

		weekDays.add(wedDayDTO);

		if (StringUtils.isNotEmpty(thursDayTotalHours) && thursDayTotalHours.equalsIgnoreCase("HH:MM")) {
			thursDayDTO.setTotalHours(thursDayTotalHours);
			thursDayDTO.setBillableHours(thursDayBillableHours);
			thursDayDTO.setNonBillableHours(thursDayNonBillableHours);
		} else {
			thursDayDTO.setTotalHours(CommonUtil.getHoursDisplayFormat(Float.parseFloat(thursDayTotalHours),null));
			thursDayDTO.setBillableHours(CommonUtil.getHoursDisplayFormat(Float.parseFloat(thursDayBillableHours),null));
			thursDayDTO
					.setNonBillableHours(CommonUtil.getHoursDisplayFormat(Float.parseFloat(thursDayNonBillableHours),null));
		}

		weekDays.add(thursDayDTO);

		if (StringUtils.isNotEmpty(friDayTotalHours) && friDayTotalHours.equalsIgnoreCase("HH:MM")) {
			friDayDTO.setTotalHours(friDayTotalHours);
			friDayDTO.setBillableHours(friDayBillableHours);
			friDayDTO.setNonBillableHours(friDayNonBillableHours);
		} else {
			friDayDTO.setTotalHours(CommonUtil.getHoursDisplayFormat(Float.parseFloat(friDayTotalHours),null));
			friDayDTO.setBillableHours(CommonUtil.getHoursDisplayFormat(Float.parseFloat(friDayBillableHours),null));
			friDayDTO.setNonBillableHours(CommonUtil.getHoursDisplayFormat(Float.parseFloat(friDayNonBillableHours),null));
		}

		weekDays.add(friDayDTO);
		if (StringUtils.isNotEmpty(satDayTotalHours) && satDayTotalHours.equalsIgnoreCase("HH:MM")) {
			satDayDTO.setTotalHours(satDayTotalHours);
			satDayDTO.setBillableHours(satDayBillableHours);
			satDayDTO.setNonBillableHours(satDayNonBillableHours);
		} else {
			satDayDTO.setTotalHours(CommonUtil.getHoursDisplayFormat(Float.parseFloat(satDayTotalHours),null));
			satDayDTO.setBillableHours(CommonUtil.getHoursDisplayFormat(Float.parseFloat(satDayBillableHours),null));
			satDayDTO.setNonBillableHours(CommonUtil.getHoursDisplayFormat(Float.parseFloat(satDayNonBillableHours),null));
		}

		weekDays.add(satDayDTO);
		if (StringUtils.isNotEmpty(sunDayTotalHours) && sunDayTotalHours.equalsIgnoreCase("HH:MM")) {
			sunDayDTO.setTotalHours(sunDayTotalHours);
			sunDayDTO.setBillableHours(sunDayBillableHours);
			sunDayDTO.setNonBillableHours(sunDayNonBillableHours);
		} else {
			sunDayDTO.setTotalHours(CommonUtil.getHoursDisplayFormat(Float.parseFloat(sunDayTotalHours),null));
			sunDayDTO.setBillableHours(CommonUtil.getHoursDisplayFormat(Float.parseFloat(sunDayBillableHours),null));
			sunDayDTO.setNonBillableHours(CommonUtil.getHoursDisplayFormat(Float.parseFloat(sunDayNonBillableHours),null));
		}
		weekDays.add(sunDayDTO);
		tsDTO.setWeekDays(weekDays);

		return tsDTO;
	}
	

	@PreAuthorize("hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_FILL_MEMBER_TIMESHEETS')")
	@RequestMapping(value = "/saveTimesheetEntries", method = RequestMethod.POST)
	public ModelAndView saveTimesheetEntriesWeb(TimesheetEntryDetailsDTO tedDTO, HttpServletRequest request)
			throws Exception {
		/**
		 * saving Code
		 */
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		Long shiftId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "shiftId");
		if(orgLocations != null) {
			 orgLocations = orgLocationService.getOrgLocations(orgLocations.getId());
		}
		ModelAndView model = new ModelAndView();
		try {
			outerLoop:{
				if (Validator.validate(tedDTO)) {
					if (Validator.validate(tedDTO.getTimesheetSubmission())) {
						for (TimesheetEntriesDTO entries : tedDTO.getTimesheetSubmission().getTimesheetEntries()) {
							if(StringUtils.isNotEmpty(entries.getDayOfTheDate()) && StringUtils.isNotEmpty(entries.getDate())) {
								if (!Validator.validate(entries)) {
									model.addObject("INVALID_DATA", entries.getDayOfTheDate());
									break outerLoop;
								}
							}
							
						}
						/*if valid Data*/
						tedDTO = saveTimesheetEntries(tedDTO, request);
						
					}else {
						model.addObject("INVALID_DATA_EMPTY", "Atleat One Date Should Be Filled");
						break outerLoop;
					}
				}
		     }
		
		
			/*if invalid Data*/
			TimesheetExpenseEntryDetailsUtilDTO eedDTO = new TimesheetExpenseEntryDetailsUtilDTO();
			eedDTO.setCurrentTimeFrequency(tedDTO.getCurrentTimeFrequency());
			eedDTO.setCurrentTimeSubFrequency(tedDTO.getCurrentTimeSubFrequency());
			eedDTO.setTimeSheetsFor(tedDTO.getTimesheetFor());
			eedDTO.setProjectMembersId(tedDTO.getProjectMemberId());
			TimeSheetSubmissionDTO tsDTO = getTimesheetSubmissionDetails(eedDTO, request);
			
			/*Employee Details*/
			ProjectMembers pm = projectMembersService.getProjectMemberById(tedDTO.getProjectMemberId());
			String fileUrl = "";
			if (pm != null && pm.getProjectMemberCandidteId() != null
					&& pm.getProjectMemberCandidteId().getConsultantUser() != null)
				fileUrl = pm.getProjectMemberCandidteId().getConsultantUser().getFileUrl();
			
			tedDTO.setProfileImageFileUrl(fileUrl);
			tedDTO.setEmployeeName(pm.getProjectMemberCandidteId().getFullName());
			tedDTO.setEmployeeEmail(pm.getProjectMemberCandidteId().getEmail());
			tedDTO.setEmployeePhoneNo(pm.getProjectMemberCandidteId().getMobilePhone());
			tedDTO.setProjectNameForSubmissionPageDisplay(pm.getProjects().getProjectName());
			tedDTO.setEmploymentTypeForSubmissionPageDisplay(
			pm.getProjectMemberCandidteId().getDesignationType());
	
			ProjectMembers conPM =  projectMembersService.getConsultantUserProjectMemberByProjectMemberId(pm.getId());
			if(conPM != null) {
				User consultantUser = conPM.getProjectMemberCandidteId().getConsultantUser();
                Map<String,List<EmployeeLeaveDTO>> empLeaveMap = getEmpLeaveList(tsDTO.getWeekDays(),eedDTO.getCurrentTimeSubFrequency(), orgLocations,consultantUser,conPM.getProjectMemberCandidteId(),shiftId);
                tsDTO.setEmpLeaveMap(empLeaveMap);
			 }
			if (pm.getProjectClientsId().getOrgRelationId() != null) {
					OrgRelations orgRelations = orgRelationsService
							.getOrgRelationById(pm.getProjectClientsId().getOrgRelationId().getId());
					if (null != pm.getProjectClientsId().getOrgRelationId().getLocationId()
							&& pm.getProjectClientsId().getOrgRelationId().getLocationId().getId() != null
							&& pm.getProjectClientsId().getOrgRelationId().getLocationId().getId()
									.longValue() == orgLocations.getId().longValue()) {
						if (orgRelations.getResponderLocationId() != null) {
							tedDTO.setClientNameForDisplay(orgRelations.getResponderLocationId().getOrgLocationName());
						}
					}
					if (null != pm.getProjectClientsId().getOrgRelationId().getResponderLocationId()
							&& null != pm.getProjectClientsId().getOrgRelationId().getResponderLocationId().getId()
							&& pm.getProjectClientsId().getOrgRelationId().getResponderLocationId().getId()
									.longValue() == orgLocations.getId().longValue()) {
						if (orgRelations.getLocationId() != null) {
							tedDTO.setClientNameForDisplay(orgRelations.getLocationId().getOrgLocationName());
						}
					}
			}
			tedDTO.setTimesheetSubmission(tsDTO);
		model.addObject("timesheetSubmissionDTO", tedDTO.getTimesheetSubmission());
		LinkedHashMap<String, LinkedList<TimesheetEntriesDTO>> entriesMap = getEntriesMapForDisplayWeb(
				tedDTO.getTimesheetSubmission(), request);
		model.addObject("entriesMap", entriesMap);
		model.addObject("timesheetEntryDetailsDTO", tedDTO);
		} catch (Exception e) {
			model.addObject("AWKWARD", true);
			e.printStackTrace();
		}
		model.setViewName("timesheet/timesheetApprovalSubmission");
		return model;
	}

	@PreAuthorize("hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_FILL_MEMBER_TIMESHEETS')")
	@RequestMapping(value = "/rest/saveTimesheetEntries", method = RequestMethod.POST)
	public @ResponseBody TimesheetEntryDetailsDTO saveTimesheetEntriesMobile(
			@RequestBody TimesheetEntryDetailsDTO tedDTO, HttpServletRequest request) throws Exception {
		tedDTO = saveTimesheetEntries(tedDTO, request);
		TimeSheetSubmissionDTO tsDTO = tedDTO.getTimesheetSubmission();
		if (tsDTO != null) {
			Map<String, List<TimesheetEntriesDTO>> entriesMap = getEntriesMapForDisplayMobile(tsDTO, request);
			tsDTO.setEntriesMap(entriesMap);
			tedDTO = getWeekDaysMapForDisplayMobile(tedDTO, tsDTO, request);
		}
		return tedDTO;
	}
	
	
	
	

	private TimesheetEntryDetailsDTO saveTimesheetEntries(TimesheetEntryDetailsDTO tedDTO, HttpServletRequest request)
			throws Exception {
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Candidate candidate = (Candidate) SourceLeadSessionManager.getSessionAttribute(request, "candidate");
		Long shiftId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "shiftId");
		if(loginUser != null && loginOrgLocation != null ) {
			loginUser = userService.getUserById(loginUser.getId());
			loginOrgLocation = orgLocationService.getOrgLocations(loginOrgLocation.getId());
			 candidate = candidateService.getCandidateByConsultantUserId(loginUser.getId(), loginOrgLocation.getId());
		}
		List<Long> timesheetEntryId = new ArrayList<Long>();
		TimesheetExpenseEntryDetailsUtilDTO eedDTO = new TimesheetExpenseEntryDetailsUtilDTO();
		eedDTO.setUserId(loginUser.getId());
		eedDTO.setLocationCode(loginOrgLocation.getLocationCode());
		eedDTO.setProjectMembersId(tedDTO.getProjectMemberId());
		eedDTO.setTimeSheetsFor(tedDTO.getTimesheetFor());
		eedDTO.setProjectsId(tedDTO.getProjectId());
		eedDTO.setMasterCandidatesId(tedDTO.getMasterCandidateId());
		eedDTO.setCurrentTimeFrequency(tedDTO.getCurrentTimeFrequency());
		eedDTO.setCurrentTimeSubFrequency(tedDTO.getCurrentTimeSubFrequency());
		tedDTO = getTimesheetSubFrequencyListByCurrentFrequency(eedDTO, tedDTO, loginOrgLocation, request);
		String status = "";
		String savingType = "";
		TimeSheetSubmissionDTO tsDTO = null;
		if (tedDTO.getTimesheetSubmission() != null) {
			tsDTO = tedDTO.getTimesheetSubmission();
		}
		if (tsDTO != null && StringUtils.isNotEmpty(tsDTO.getTimesheetStatus())) {
			status = tsDTO.getTimesheetStatus();
		} else {
			status = "Draft";
		}

		if (StringUtils.isEmpty(tedDTO.getTimesheetFor())) {
			savingType = "user";
		} else {
			savingType = tedDTO.getTimesheetFor();
		}

		ProjectMembers projectMember = null;
		if (tedDTO != null && tedDTO.getProjectMemberId() != null) {
			projectMember = projectMembersService.getProjectMemberById(tedDTO.getProjectMemberId());
			tedDTO.setEmployeeEmail(projectMember.getProjectMemberCandidteId().getEmail());
			tedDTO.setEmployeeName(projectMember.getProjectMemberCandidteId().getFullName());
			tedDTO.setEmployeePhoneNo(projectMember.getProjectMemberCandidteId().getMobilePhone());
		}
		Date frequencyStartDate = null;
		Date frequencyEndDate = null;
		if (status != null && (status.equalsIgnoreCase("Draft") || status.equalsIgnoreCase("Submitted"))) {
			Long submissionId = null;
			if (tsDTO != null && tsDTO.getId() != null) {
				submissionId = tsDTO.getId();
			}

			Date startDate = null;
			Date endDate = null;
			if (StringUtils.isNotEmpty(tedDTO.getCurrentTimeSubFrequency())) {
				String[] subfrequency = tedDTO.getCurrentTimeSubFrequency().split("To");
				String subfrstartDate = subfrequency[0].trim();
				String subfrEndDate = subfrequency[1].trim();
				startDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(), subfrstartDate);
				endDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(), subfrEndDate);
			}

			if (StringUtils.isNotEmpty(tedDTO.getCurrentTimeFrequency())) {
				String[] frequency = tedDTO.getCurrentTimeFrequency().split("To");
				String frstartDate = frequency[0].trim();
				String frEndDate = frequency[1].trim();
				frequencyStartDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(), frstartDate);
				frequencyEndDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(), frEndDate);
			}
			Long total = 0l;
			Long totalBillableHours = 0l;
			Long totalNonBillableHours = 0l;
			TimesheetSubmissions timesheetSubmissions;
			HashSet<SubmissionApproverHierarchy> freshSaHierarchyList = new HashSet<SubmissionApproverHierarchy>();
			SubmissionApproverHierarchy sahActivity = null;
			if (submissionId != null) {
				timesheetSubmissions = timesheetSubmissionsService.getTimesheetSubmissions(submissionId.toString());
				if (timesheetSubmissions != null) {
					List<SubmissionApproverHierarchy> saHierarchyList = submissionApproverHierarchyService
							.getSubmissionApproverHierarchyBySubmissionId(timesheetSubmissions.getId());
					if (saHierarchyList == null || saHierarchyList.isEmpty()) {
						ArrayList<TimesheetSubmissions> timesheetSubmissionsList = new ArrayList<TimesheetSubmissions>();
						List<ProjectApproverHierarchy> hierarchyList = projectApproverHierarchyService
								.getApproverUsersByProjectMemberMasterCandidateIdAndProjectId(
										projectMember.getMasterCandidateId().getId(),
										projectMember.getProjects().getId());
						if (hierarchyList != null && !hierarchyList.isEmpty()) {
							for (ProjectApproverHierarchy hierarchy : hierarchyList) {
								SubmissionApproverHierarchy saHierarchy = new SubmissionApproverHierarchy();
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
								
								saHierarchy.setSubmissionStatus(Submission_Status.getNameByValue(status));
								saHierarchy
										.setSubmissionApprovalStatus(Submission_ApprovalStatus.getNameByValue("new"));
								saHierarchy.setCreatedDate(timesheetSubmissions.getCreatedDate());
								if (frequencyStartDate != null)
									saHierarchy.setFrequencyStartDate(frequencyStartDate);
								if (frequencyEndDate != null)
									saHierarchy.setFrequencyEndDate(frequencyEndDate);
								saHierarchy.setCreatedBy(timesheetSubmissions.getCreatedBy());
								saHierarchy.setUpdatedDate(timesheetSubmissions.getUpdatedDate());
								saHierarchy.setUpdatedBy(timesheetSubmissions.getUpdatedBy());
								saHierarchy.setStatus(Constants.RECORD_ACTIVE + "");
								freshSaHierarchyList.add(saHierarchy);
							}
							timesheetSubmissions.setApproverHierarchyList(freshSaHierarchyList);
						}
						timesheetSubmissionsList.add(timesheetSubmissions);
						timesheetSubmissionsService.saveAllTimesheetSubmissions(timesheetSubmissionsList);
					}
					
					for (SubmissionApproverHierarchy saHierarchy : saHierarchyList) {
						Submission_Status currentSahStatus = saHierarchy.getSubmissionStatus();
						if (currentSahStatus != null && currentSahStatus.equals(Submission_Status.SUBMITTED)
								&& StringUtils.isNotEmpty(savingType) && !savingType.equalsIgnoreCase("user")
								&& StringUtils.isNotEmpty(status) && status.equalsIgnoreCase("Draft")) {
							status = "submitted";
						}

						if (StringUtils.isNotEmpty(saHierarchy.getSubmissionStatus().getValue())
								&& saHierarchy.getSubmissionStatus().getValue().equalsIgnoreCase("approved")) {
							status = "approved";
							saHierarchy.setSubmissionStatus(Submission_Status.getNameByValue("approved"));
						} else {
							saHierarchy.setSubmissionStatus(Submission_Status.getNameByValue(status));
						}

						if (StringUtils.isNotEmpty(status) && status.equalsIgnoreCase("Draft"))
							saHierarchy.setSubmissionApprovalStatus(Submission_ApprovalStatus.getNameByValue("new"));

						if (StringUtils.isNotEmpty(status) && status.equalsIgnoreCase("Submitted"))
							saHierarchy.setSubmissionApprovalStatus(
									Submission_ApprovalStatus.getNameByValue("submitted for approval"));

						saHierarchy.setUpdatedDate(CommonUtil.currentDate());
						saHierarchy.setUpdatedBy(loginUser.getUserCode());

						if (frequencyStartDate != null)
							saHierarchy.setFrequencyStartDate(frequencyStartDate);
						if (frequencyEndDate != null)
							saHierarchy.setFrequencyEndDate(frequencyEndDate);
						saHierarchy.setTimesheetActivityLoginUser(loginUser.getId());
						sahActivity = saHierarchy;
						freshSaHierarchyList.add(saHierarchy);
					}
					if(StringUtils.isEmpty(tsDTO.getWeekComment())) {
						//String comment = timesheetSubmissionsService.getWeekCommentByCurrentFrequency(frequencyStartDate, frequencyEndDate, timesheetSubmissions.getProjects().getId(), timesheetSubmissions.getMasterCandidateId().getId());
						//if(StringUtils.isNotEmpty(comment));
						timesheetSubmissions.setWeekcomments(tsDTO.getWeekComment());
					}else {
						timesheetSubmissions.setWeekcomments(tsDTO.getWeekComment());
					}
					submissionApproverHierarchyService.saveAllSubmissionApproverHierarchy(new ArrayList<>(freshSaHierarchyList));
					timesheetSubmissions.setUpdatedDate(CommonUtil.currentDate());
					timesheetSubmissions.setUpdatedBy(loginUser.getUserCode());
					timesheetSubmissionsService.updateTimesheetSubmissions(timesheetSubmissions);
					if(StringUtils.isNotEmpty(tsDTO.getWeekComment()) && frequencyStartDate != null && frequencyEndDate != null) {
						timesheetSubmissionsService.updateWeekCommentForAllSubmissions(tsDTO.getWeekComment(),frequencyStartDate, frequencyEndDate, timesheetSubmissions.getProjects().getId(), timesheetSubmissions.getMasterCandidateId().getId());
					}
				}

			} else {
				timesheetSubmissions = timesheetSubmissionsService
						.getAllCandidateTimesheetSubmissionsByProjectIdAndMasterCandidateId(startDate, endDate,
								projectMember.getProjects().getId(), projectMember.getMasterCandidateId().getId());
				if (timesheetSubmissions == null) {
					timesheetSubmissions = new TimesheetSubmissions();
					timesheetSubmissions.setStartDate(startDate);
					timesheetSubmissions.setEndDate(endDate);
					timesheetSubmissions.setProjectMembers(projectMember);
					timesheetSubmissions.setStatus(Constants.RECORD_ACTIVE + "");
					timesheetSubmissions.setCreatedDate(CommonUtil.currentDate());
					timesheetSubmissions.setCreatedBy(loginUser.getUserCode());
					timesheetSubmissions.setProjects(projectMember.getProjects());
					timesheetSubmissions.setMasterCandidateId(projectMember.getMasterCandidateId());
				}

				timesheetSubmissions.setUpdatedDate(CommonUtil.currentDate());
				timesheetSubmissions.setUpdatedBy(loginUser.getUserCode());
				if(StringUtils.isEmpty(tsDTO.getWeekComment())  && frequencyStartDate != null && frequencyEndDate != null) {
					String comment = timesheetSubmissionsService.getWeekCommentByCurrentFrequency(frequencyStartDate, frequencyEndDate, timesheetSubmissions.getProjects().getId(), timesheetSubmissions.getMasterCandidateId().getId());
					if(StringUtils.isNotEmpty(comment));
					timesheetSubmissions.setWeekcomments(comment);
				}else {
					timesheetSubmissions.setWeekcomments(tsDTO.getWeekComment());
				}

				ArrayList<TimesheetSubmissions> timesheetSubmissionsList = new ArrayList<TimesheetSubmissions>();
				List<ProjectApproverHierarchy> hierarchyList = projectApproverHierarchyService
						.getApproverUsersByProjectMemberMasterCandidateIdAndProjectId(
								projectMember.getMasterCandidateId().getId(), projectMember.getProjects().getId());
				if (hierarchyList != null && !hierarchyList.isEmpty()) {
					for (ProjectApproverHierarchy hierarchy : hierarchyList) {
						SubmissionApproverHierarchy saHierarchy = new SubmissionApproverHierarchy();
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
						saHierarchy.setSubmissionStatus(Submission_Status.getNameByValue(status));
						saHierarchy.setSubmissionApprovalStatus(Submission_ApprovalStatus.getNameByValue("new"));
						saHierarchy.setCreatedDate(timesheetSubmissions.getCreatedDate());
						if (frequencyStartDate != null)
							saHierarchy.setFrequencyStartDate(frequencyStartDate);
						if (frequencyEndDate != null)
							saHierarchy.setFrequencyEndDate(frequencyEndDate);
						saHierarchy.setCreatedBy(timesheetSubmissions.getCreatedBy());
						saHierarchy.setUpdatedDate(timesheetSubmissions.getUpdatedDate());
						saHierarchy.setUpdatedBy(timesheetSubmissions.getUpdatedBy());
						saHierarchy.setStatus(Constants.RECORD_ACTIVE + "");
						saHierarchy.setTimesheetActivityLoginUser(loginUser.getId());
						sahActivity = saHierarchy;
						freshSaHierarchyList.add(saHierarchy);
					}
					timesheetSubmissions.setApproverHierarchyList(freshSaHierarchyList);
				}
				timesheetSubmissionsList.add(timesheetSubmissions);
				timesheetSubmissionsService.saveAllTimesheetSubmissions(timesheetSubmissionsList);
				if(StringUtils.isNotEmpty(tsDTO.getWeekComment()) && frequencyStartDate != null && frequencyEndDate != null) {
					timesheetSubmissionsService.updateWeekCommentForAllSubmissions(tsDTO.getWeekComment(),frequencyStartDate, frequencyEndDate, timesheetSubmissions.getProjects().getId(), timesheetSubmissions.getMasterCandidateId().getId());
				}
			}
			// save mutliple attachment in timesheet submissison attachment.
			LinkedList<FileInfo> fileInfoList = new LinkedList<>();
			if (tsDTO.getFileInfo() != null) {
				for (FileInfo file : tsDTO.getFileInfo()) {
					FileInfo freshFile = file;
					if (StringUtils.isEmpty(file.getFileDbId())) {
						FileUploadUtil.moveFile(staticPath, file, "timesheetDocuments");
						TimesheetSubmissionsDocuments tsd = new TimesheetSubmissionsDocuments();
						tsd.setTimesheetSubmissions(timesheetSubmissions);
						tsd.setFileUrl(file.getFileURL());
						tsd.setFileName(file.getFileName());
						tsd.setFileContentType(file.getContentType());
						tsd.setStatus(Constants.CANDIDATE_ACTIVE);
						tsd.setCreatedDate(CommonUtil.currentDate());
						tsd.setCreatedBy(loginUser.getUserCode());
						tsd.setUpdatedDate(CommonUtil.currentDate());
						tsd.setUpdatedBy(loginUser.getUserCode());
						tsd = timesheetSubmissionsDocumentsService.addTimesheetSubmissionsDocuments(tsd);
						freshFile.setFileDbId(tsd.getId().toString());
					}
					fileInfoList.add(freshFile);
				}
			}
			tsDTO.setFileInfo(fileInfoList);

			if (tsDTO.getRemovedDocumentIds() != null && !tsDTO.getRemovedDocumentIds().isEmpty()) {
				for (Long removedId : tsDTO.getRemovedDocumentIds()) {
					deleteSubmissionDocument(removedId);
				}
			}
			String activityStatus = null;
            if (StringUtils.isNotEmpty(status) && status.equalsIgnoreCase("Draft")){
            	activityStatus ="Draft"; 
			}else if (StringUtils.isNotEmpty(status) && status.equalsIgnoreCase("Submitted")){
				activityStatus ="Submitted"; 
			}
            
			List<Long> oldDbIdList = timesheetDayEntriesDao
					.getAllTimeSheetDayEntryIdsBySubmissionId(timesheetSubmissions.getId());
			List<Long> freshIdList = new ArrayList<>();
			LinkedList<TimeSheetDayEntries> entriesDayList = new LinkedList<>();
			if (tsDTO.getTimesheetEntries() != null && !tsDTO.getTimesheetEntries().isEmpty()) {
				for (TimesheetEntriesDTO teDTO : tsDTO.getTimesheetEntries()) {
					if (StringUtils.isEmpty(teDTO.getHours()) || ((teDTO.getTimesheetTaskDTO() == null)
							|| (teDTO.getTimesheetTaskDTO() == null && teDTO.getTimesheetTaskDTO().getId() == null)))
						continue;
					ProjectTasks projectTasks = projectTasksService
							.getTasksId(teDTO.getTimesheetTaskDTO().getId().toString());
					String taskType = teDTO.getTimesheetTaskDTO().getTaskType();
					String billableHours = "00:00";
					String nonBillableHours = "00:00";
					TimeSheetDayEntries timesheetDayEntries = new TimeSheetDayEntries();
					if (teDTO.getId() != null) {
						timesheetDayEntries.setId(teDTO.getId());
					}
					timesheetDayEntries.setProjectTask(projectTasks);
					timesheetDayEntries.setTimesheetSubmissions(timesheetSubmissions);
					total = getTotalHoursFormDayHours(teDTO.getHours(), total);
					if (taskType.equalsIgnoreCase("Billable")) {
						if (StringUtils.isNotEmpty(teDTO.getHours())) {
							billableHours = teDTO.getHours();
							if (!teDTO.getHours().equalsIgnoreCase("00:00")) {
								timesheetDayEntries.setIsManuallyAdded(Constants.YES);
							} else {
								timesheetDayEntries.setIsManuallyAdded(Constants.NO);
							}

						} else {
							timesheetDayEntries.setIsManuallyAdded(Constants.NO);
						}
						totalBillableHours = getTotalHoursFormDayHours(billableHours, totalBillableHours);
						timesheetDayEntries.setBillableHours(billableHours);
						timesheetDayEntries.setTotalHours(billableHours);
						timesheetDayEntries.setTaskType("Billable");
					} else if (taskType.equalsIgnoreCase("Non-Billable")) {
						if (StringUtils.isNotEmpty(teDTO.getHours())) {
							nonBillableHours = teDTO.getHours();
							if (!teDTO.getHours().equalsIgnoreCase("00:00")) {
								timesheetDayEntries.setIsManuallyAdded(Constants.YES);
							} else {
								timesheetDayEntries.setIsManuallyAdded(Constants.NO);
							}
						} else {
							timesheetDayEntries.setIsManuallyAdded(Constants.NO);
						}
						totalNonBillableHours = getTotalHoursFormDayHours(nonBillableHours, totalNonBillableHours);
						timesheetDayEntries.setNonBillableHours(nonBillableHours);
						timesheetDayEntries.setTotalHours(nonBillableHours);
						timesheetDayEntries.setTaskType("Non-Billable");
					}
					timesheetDayEntries.setTicketNumber(teDTO.getTicketNumber());
					timesheetDayEntries.setHourlyType(teDTO.getHourlyType());
					timesheetDayEntries.setComments(teDTO.getComments());
					timesheetDayEntries.setTimesheetDate(
							commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(), teDTO.getDate()));
					timesheetDayEntries.setStatus(1);
					timesheetDayEntries.setCreatedDate(CommonUtil.currentDate());
					timesheetDayEntries.setUpdatedBy(loginUser.getCreatedBy());
					timesheetDayEntries.setUpdatedDate(CommonUtil.currentDate());
					timesheetDayEntries.setCreatedBy(loginUser.getCreatedBy());
					entriesDayList.add(timesheetDayEntries);
				}

				if (entriesDayList != null && !entriesDayList.isEmpty()) {
					for (TimeSheetDayEntries timesheetdayEntries : entriesDayList) {
						timesheetdayEntries = timesheetDayEntriesDao.saveTimesheetDayEntries(timesheetdayEntries);
						timesheetEntryId.add(timesheetdayEntries.getId());
						freshIdList.add(timesheetdayEntries.getId());
					}
				}

			}
			oldDbIdList.removeAll(freshIdList);

			if (oldDbIdList != null && !oldDbIdList.isEmpty()) {
				for (Long removedEntryId : oldDbIdList) {
					timesheetDayEntriesDao.deleteTimeSheetDayEntries(removedEntryId);
				}
			}
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
				if (StringUtils.isNotEmpty(actualNonBillableMinutesStr) && actualNonBillableMinutesStr.length() == 1)
					nonBillableStartWithZero = "0";
				String actualNonBillableTimeString = actualNonBillableHours + "." + nonBillableStartWithZero
						+ actualNonBillableMinutes;
				timesheetSubmissions.setNonBillableHours(actualNonBillableTimeString);
				timesheetSubmissionsService.updateTimesheetSubmissions(timesheetSubmissions);

				// send mail to assigned manager at time sheet submission
				if (!actualTimeString.equalsIgnoreCase("0.0")) {
					if (!"Draft".equalsIgnoreCase(status) && projectMember != null
							&& projectMember.getProjects() != null && frequencyStartDate != null
							&& frequencyEndDate != null) {
						    timesheetEmailUtil.sendEmailToManagerAtTimesheetSubmissionsNew(projectMember, projectMember.getMasterCandidateId().getId(),
								projectMember.getProjects().getId(), frequencyStartDate, frequencyEndDate, loginUser,
								loginOrgLocation, loginOrgLocation.getOrgCode());
					}
				}
			}
			if (status != null && status.equalsIgnoreCase("Submitted")) {
				if (tedDTO.getTimeSubFrequencies() != null && !tedDTO.getTimeSubFrequencies().isEmpty()) {
					LinkedList<String> tabDates = new LinkedList<String>(tedDTO.getTimeSubFrequencies().keySet());
					if (tabDates != null && !tabDates.isEmpty()) {
						for (String tabDate : tabDates) {
							tabDate = tabDate.replace("\"", "");
							String[] words = tabDate.split("To");
							Date actualStartDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(),
									words[0].replaceAll("/", "-"));
							Date actualEndDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(),
									words[1].replaceAll("/", "-"));
							List<TimesheetSubmissions> timesheetSubmissionsList = timesheetSubmissionsService
									.getAllCandidateTimesheetSubmissionsByProjectIdAndMasterCandidateIdForUpdation(
											actualStartDate, actualEndDate, projectMember.getProjects().getId(),
											projectMember.getMasterCandidateId().getId());
							if (timesheetSubmissionsList != null && !timesheetSubmissionsList.isEmpty()) {
								for (TimesheetSubmissions ts : timesheetSubmissionsList) {
									List<SubmissionApproverHierarchy> sahList = submissionApproverHierarchyService
											.getSubmissionApproverHierarchyBySubmissionId(ts.getId());
									if (sahList != null) {
										for (SubmissionApproverHierarchy sah : sahList) {
											if (sah != null && sah.getId() != null) {
												if (frequencyStartDate != null)
													sah.setFrequencyStartDate(frequencyStartDate);
												if (frequencyEndDate != null)
													sah.setFrequencyEndDate(frequencyEndDate);
												sah.setSubmissionStatus(Submission_Status.SUBMITTED);
												sah.setSubmissionApprovalStatus(
														Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL);
												sah = submissionApproverHierarchyService.addSubmissionApproverHierarchy(sah);
												sahActivity = sah;
											}
										}
									}
								}

							} else {
								insertNewTimesheetRecordByProjectMemberAndDates(projectMember, actualStartDate,
										actualEndDate, frequencyStartDate, frequencyEndDate, loginUser, candidate);
							}

						}
					}

				}
			}
	          TimesheetActivities ta = new TimesheetActivities();
              ta.setActivityStatus(activityStatus);
            ta = timesheetActivityService.saveTimesheetActivity(ta);
            if(submissionId !=  null && timesheetSubmissions != null) {
            	List<TimesheetSubmissionsDocuments> documents = timesheetSubmissionsDocumentsService.getAllTimesheetSubmissionsDocumentsByProjectIdMasterCandidateIdAndFrequencyStartDateEndDate(timesheetSubmissions.getMasterCandidateId().getId(), timesheetSubmissions.getProjects().getId(), frequencyStartDate, frequencyEndDate);
	            for(TimesheetSubmissionsDocuments document:documents) {
	            	TimesheetSubmissionsDocumentsHistory tdhistory = new TimesheetSubmissionsDocumentsHistory();
	            	tdhistory.setTimesheetActivity(ta.getId());
	            	saveTimesheetDocumentHistory(tdhistory, document);
	            }
            }
            if(timesheetEntryId !=  null && !timesheetEntryId.isEmpty()) {
            	for(Long entry:timesheetEntryId) {
            		 TimeSheetDayEntries timesheetDayEntry = timeSheetDayEntriesService.getTimesheetDayEntriesById(entry);
            		 timesheetDayEntry.setTimesheetActivityId(ta.getId());
            		 timeSheetDayEntriesService.saveTimesheetDayEntries(timesheetDayEntry);
            	}
            }
			tedDTO = getTimesheetSubFrequencyListByCurrentFrequency(eedDTO, tedDTO, loginOrgLocation, request);
			tsDTO = getTimesheetSubmissionDetails(eedDTO, request);
			ProjectMembers conPM =  projectMembersService.getConsultantUserProjectMemberByProjectMemberId(projectMember.getId());
			if(conPM != null) {
				User consultantUser = conPM.getProjectMemberCandidteId().getConsultantUser();
                Map<String,List<EmployeeLeaveDTO>> empLeaveMap = getEmpLeaveList(tsDTO.getWeekDays(),eedDTO.getCurrentTimeSubFrequency(), loginOrgLocation,consultantUser,conPM.getProjectMemberCandidteId(),shiftId);
                tsDTO.setEmpLeaveMap(empLeaveMap);
			 }
			tedDTO.setTimesheetSubmission(tsDTO);
		}
		return tedDTO;
	}

	private void saveTimesheetDocumentHistory(TimesheetSubmissionsDocumentsHistory tsdHistory,TimesheetSubmissionsDocuments tsd) throws Exception {
		tsdHistory.setTimesheetSubmissions(tsd.getTimesheetSubmissions());
		tsdHistory.setFileUrl(tsd.getFileUrl());
		tsdHistory.setFileName(tsd.getFileName());
		tsdHistory.setFileContentType(tsd.getFileContentType());
		tsdHistory.setCreatedDate(CommonUtil.currentDate());
		tsdHistory.setCreatedBy(tsd.getCreatedBy());
		tsdHistory.setUpdatedDate(CommonUtil.currentDate());
		tsdHistory.setUpdatedBy(tsd.getUpdatedBy());
		timesheetSubmissionsDocumentsService.addTimesheetSubmissionsDocumentsHistory(tsdHistory);
	}

	@PreAuthorize("hasRole('ROLE_MODIFY_MY_TIMESHEETS')")
	@RequestMapping(value = "/withDrawTimesheet", method = RequestMethod.POST)
	public @ResponseBody void withDrawTimesheet(
			@RequestParam(value = "projectMemberId", required = false) Long projectMemberId,
			@RequestParam(value = "timesheetFrequency", required = false) String timesheetFrequency,
			@RequestParam(value = "dateFormat", required = false) String dateFormat, HttpServletRequest request)
			throws Exception {
		// DateFormat format = Constants.DF_DMY;
		// DateFormat format = LoginSessionController.DATEFORMAT;
		try {
			if (StringUtils.isNotEmpty(timesheetFrequency)) {
				String[] frequency = timesheetFrequency.split("To");
				String weekStartDate = frequency[0].trim();
				String weekEndDate = frequency[1].trim();
				Date startDate = commonUtil.orgLocationDateFormat(dateFormat, weekStartDate.replaceAll("/", "-"));
				Date endDate = commonUtil.orgLocationDateFormat(dateFormat, weekEndDate.replaceAll("/", "-"));
				ProjectMembers pm = projectMembersService.getProjectMemberById(projectMemberId);
				submissionApproverHierarchyService
						.updateSubmissionStatusByProjectIdMasterCandidateIdStartDateAndEndDate(pm.getProjects().getId(),
								pm.getMasterCandidateId().getId(), startDate, endDate,
								Submission_Status.DRAFT.getValue());
				;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@PreAuthorize("hasRole('ROLE_MODIFY_MY_TIMESHEETS')")
	@RequestMapping(value = "/dropHoliday", method = RequestMethod.POST)
	public @ResponseBody String dropHoliday(
			@RequestParam(value = "holidayId", required = true) Long holidayId,
			@RequestParam(value = "holidayUserId", required = true) Long holidayUserId,
			@RequestParam(value = "holidayUserLocationId", required = true) Long holidayUserLocationId, HttpServletRequest request)
			throws Exception {
		   String output = "";
		   User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		   if(loginUser != null) {
			   loginUser = userService.getUserById(loginUser.getId());
		   }
		try {
			UserDroppedHolidays userDroppedHolidays = userDroppedHolidaysService.getUserDroppedHolidaysFromUserIdlocationIdAndHolidayId(holidayUserId, holidayUserLocationId, holidayId); 
			
			if(userDroppedHolidays == null) {
				userDroppedHolidays = new UserDroppedHolidays();
			}
			
			Holiday holiday = holidayService.getHolidaysById(holidayId.toString());
			holiday.setId(holidayId);
			userDroppedHolidays.setHoliday(holiday);
			userDroppedHolidays.setToDate(holiday.getToDate());
			userDroppedHolidays.setFromDate(holiday.getFromDate());
			User user = new User();
			user.setId(holidayUserId);
			userDroppedHolidays.setUser(user);
			
			OrgLocations orgLocations = new OrgLocations();
			orgLocations.setId(holidayUserLocationId);
			userDroppedHolidays.setOrgLocations(orgLocations);
			userDroppedHolidays.setStatus(Constants.RECORD_ACTIVE);
			
			userDroppedHolidays.setCreatedBy(loginUser.getId());
			userDroppedHolidays.setCreatedDate(CommonUtil.currentDate());
			userDroppedHolidays.setUpdatedDate(CommonUtil.currentDate());
			userDroppedHolidays.setUpdatedBy(loginUser.getId());
			userDroppedHolidaysService.addUserDroppedHolidays(userDroppedHolidays);
			output ="success";
		} catch (Exception e) {
			output ="failure";
		}
		return output;
	}
	
	
	/*@RequestMapping(value = "/rest/withDrawTimesheet", method = RequestMethod.POST)
	public @ResponseBody TimesheetEntryDetailsDTO withDrawTimesheetMobile(UpdateTimesheetEntryDetailsDTO tsDTO, HttpServletRequest request)throws Exception {
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if (loginUser == null && tsDTO.getUserId() != null) {
			loginUser = userService.getUserById(tsDTO.getUserId());
		}
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		if (orgLoc == null && StringUtils.isNotEmpty(tsDTO.getLocationCode())) {
			orgLoc = orgLocationService.getOrgLocationsByLocationCode(tsDTO.getLocationCode());
		}
		
		try {
			if (StringUtils.isNotEmpty(tsDTO.getTimeFrequency())) {
				String[] frequency = tsDTO.getTimeFrequency().split("To");
				String weekStartDate = frequency[0].trim();
				String weekEndDate = frequency[1].trim();
				Date startDate = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(), weekStartDate.replaceAll("/", "-"));
				Date endDate = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(), weekEndDate.replaceAll("/", "-"));
				ProjectMembers pm = projectMembersService.getProjectMemberById(tsDTO.getProjectMemberId());
				submissionApproverHierarchyService.updateSubmissionStatusByProjectIdMasterCandidateIdStartDateAndEndDate(pm.getProjects().getId(),pm.getMasterCandidateId().getId(), startDate, endDate,
								Submission_Status.DRAFT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TimesheetExpenseEntryDetailsUtilDTO eedDTO = new TimesheetExpenseEntryDetailsUtilDTO();
		eedDTO.setCurrentTimeFrequency(tsDTO.getTimeFrequency());
		eedDTO.setCurrentTimeSubFrequency(tsDTO.getTimeSubFrequency());
		eedDTO.setTimeSheetsFor(tsDTO.getTimesheetFor());
		eedDTO.setProjectMembersId(tsDTO.getProjectMemberId());
		eedDTO.setUserId(loginUser.getId());
		eedDTO.setLocationCode(orgLoc.getLocationCode());
		return getTimesheetEntryDetailsMobile(eedDTO, request);

	}*/
	

	@Async
	public void insertNewTimesheetRecordByProjectMemberAndDates(ProjectMembers projectMember, Date actualStartDate,
			Date actualEndDate, Date frequencyStartDate, Date frequencyEndDate, User loginUser, Candidate cnadidate)
			throws Exception {

		TimesheetSubmissions timesheetSubmissions = timesheetSubmissionsService
				.getAllCandidateTimesheetSubmissionsByProjectIdAndMasterCandidateId(actualStartDate, actualEndDate,
						projectMember.getProjects().getId(), projectMember.getMasterCandidateId().getId());
		if (timesheetSubmissions == null) {
			timesheetSubmissions = new TimesheetSubmissions();
			timesheetSubmissions.setStartDate(actualStartDate);
			timesheetSubmissions.setEndDate(actualEndDate);
			timesheetSubmissions.setProjectMembers(projectMember);
			timesheetSubmissions.setProjects(projectMember.getProjects());
			timesheetSubmissions.setMasterCandidateId(projectMember.getMasterCandidateId());
			timesheetSubmissions.setStatus(Constants.RECORD_ACTIVE + "");
			timesheetSubmissions.setCreatedDate(CommonUtil.currentDate());
			timesheetSubmissions.setCreatedBy(loginUser.getUserCode());
		}
		timesheetSubmissions.setUpdatedDate(CommonUtil.currentDate());
		timesheetSubmissions.setUpdatedBy(loginUser.getUserCode());
		if(frequencyStartDate != null && frequencyEndDate != null) {
			String comment = timesheetSubmissionsService.getWeekCommentByCurrentFrequency(frequencyStartDate, frequencyEndDate, timesheetSubmissions.getProjects().getId(), timesheetSubmissions.getMasterCandidateId().getId());
			if(StringUtils.isNotEmpty(comment));
			timesheetSubmissions.setWeekcomments(comment);
		}
		ArrayList<TimesheetSubmissions> timesheetSubmissionsList = new ArrayList<TimesheetSubmissions>();
		List<ProjectApproverHierarchy> hierarchyList = projectApproverHierarchyService
				.getApproverUsersByProjectMemberMasterCandidateIdAndProjectId(
						projectMember.getMasterCandidateId().getId(), projectMember.getProjects().getId());
		HashSet<SubmissionApproverHierarchy> saHierarchyList = new HashSet<SubmissionApproverHierarchy>();
		if (hierarchyList != null && !hierarchyList.isEmpty()) {
			for (ProjectApproverHierarchy hierarchy : hierarchyList) {
				SubmissionApproverHierarchy saHierarchy = new SubmissionApproverHierarchy();
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
				saHierarchy.setSubmissionStatus(Submission_Status.SUBMITTED);
				saHierarchy.setSubmissionApprovalStatus(Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL);
				saHierarchy.setCreatedDate(timesheetSubmissions.getCreatedDate());
				saHierarchy.setCreatedBy(timesheetSubmissions.getCreatedBy());
				saHierarchy.setUpdatedDate(timesheetSubmissions.getUpdatedDate());
				saHierarchy.setUpdatedBy(timesheetSubmissions.getUpdatedBy());
				saHierarchy.setStatus(Constants.RECORD_ACTIVE + "");
				saHierarchyList.add(saHierarchy);
			}
			timesheetSubmissions.setApproverHierarchyList(saHierarchyList);
		}
		timesheetSubmissionsList.add(timesheetSubmissions);
		timesheetSubmissionsService.saveAllTimesheetSubmissions(timesheetSubmissionsList);

		/*
		 * while(actualStartDate.before(actualEndDate) ||
		 * actualStartDate.equals(actualEndDate)){ TimeSheetDayEntries
		 * timesheetDayEntries = new TimeSheetDayEntries();
		 * timesheetDayEntries.setBillableHours("00:00");
		 * timesheetDayEntries.setNonBillableHours("00:00");
		 * timesheetDayEntries.setTotalHours("00:00");
		 * timesheetDayEntries.setTimesheetSubmissions(timesheetSubmissions);
		 * timesheetDayEntries.setTimesheetDate(actualStartDate);
		 * timesheetDayEntries.setIsManuallyAdded(Constants.NO);
		 * timesheetDayEntries.setCreatedDate(CommonUtil.currentDate());
		 * timesheetDayEntries.setUpdatedBy(loginUser.getCreatedBy());
		 * timesheetDayEntries.setUpdatedDate(CommonUtil.currentDate());
		 * timesheetDayEntries.setCreatedBy(loginUser.getCreatedBy());
		 * timesheetDayEntriesDao.saveTimesheetDayEntries(timesheetDayEntries);
		 * actualStartDate = CalendarUtil.getNextDate(actualStartDate); }
		 */
	}

	@RequestMapping(value = "/addTimeSheetDayEntries", method = RequestMethod.POST)
	public ResponseList<TimeSheetDayEntries> addTimeSheetDayEntries(TimesheetProjectTaskDTO timesheetProjectTaskDTO,
			HttpServletRequest request) throws Exception {
		ResponseList<TimeSheetDayEntries> timeSheetDayEntriesListResponseList = new ResponseList<>();
		try {
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
					"orgLocations");
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if(loginUser != null && loginOrgLocation != null) {
				 loginUser = userService.getUserById(loginUser.getId());
				 loginOrgLocation = orgLocationService.getOrgLocations(loginOrgLocation.getId());
			}
			LinkedList<TimeSheetDayEntries> timeSheetDayEntriesList = new LinkedList<>();
			addTimesheetDayEntriesByProjectIdAndProjectMemberId(timesheetProjectTaskDTO.getProjectId(),
					timesheetProjectTaskDTO.getProjectTaskMemberId(), timesheetProjectTaskDTO.getTaskUUID(),
					timesheetProjectTaskDTO.getTimesheetDate(), timesheetProjectTaskDTO.getHours(),
					timesheetProjectTaskDTO.getComments(), loginUser, loginOrgLocation, null,timesheetProjectTaskDTO.getHourlyType());
			timeSheetDayEntriesListResponseList.setData(timeSheetDayEntriesList);
			timeSheetDayEntriesListResponseList.setResponse(Result.SUCCESS.name());
			timeSheetDayEntriesListResponseList.setResponseMessage(Result.SUCCESS.getValue());
		} catch (Exception e) {
			timeSheetDayEntriesListResponseList.setResponse(Result.AWKWARD.name());
			timeSheetDayEntriesListResponseList.setResponseMessage(Result.AWKWARD.getValue());
			e.printStackTrace();
		}
		return timeSheetDayEntriesListResponseList;
	}

	private void addTimesheetDayEntriesByProjectIdAndProjectMemberId(Long projectId, Long projectMemberId,
			String taskUUID, String timesheetDateStr, String hours, String comments, User loginUser,
			OrgLocations loginOrgLocation, String leaveStatusType,String hourlyType) throws Exception {
		Projects projects = projectsService.getProjectById(projectId);
		ProjectMembers projectMember = projectMembersService.getProjectMemberById(projectMemberId);
		Date frequencyStartDate = null;
		Date frequencyEndDate = null;
		Date actualStartDate = null;
		Date actualEndDate = null;
		Date timesheetDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(), timesheetDateStr);
		if (projects != null && projects.getInvoiceFrequency().equalsIgnoreCase("Monthly")) {
			frequencyStartDate = CalendarUtil.getMonthStartDateByGivenDate(timesheetDate, Constants.CURRENT_MONTH)
					.get("STARTDATE");
			frequencyEndDate = CalendarUtil.getMonthEndDateByGivenDate(timesheetDate, Constants.CURRENT_MONTH)
					.get("ENDDATE");
		} else if (projects != null && projects.getInvoiceFrequency().equalsIgnoreCase("Semi-Monthly")) {
			Date empMonth15thDate = CalendarUtil.getMonth15thDateByGivenDate(timesheetDate, Constants.CURRENT_MONTH)
					.get("STARTDATE");
			if (timesheetDate.after(empMonth15thDate)) {
				frequencyStartDate = CalendarUtil.getMonth16thDateByGivenDate(timesheetDate, Constants.CURRENT_MONTH)
						.get("STARTDATE");
				frequencyEndDate = CalendarUtil.getMonthEndDateByGivenDate(frequencyStartDate, 0).get("ENDDATE");
			} else {
				frequencyStartDate = CalendarUtil.getMonthStartDateByGivenDate(timesheetDate, Constants.CURRENT_MONTH)
						.get("STARTDATE");
				frequencyEndDate = CalendarUtil.getMonth15thDateByGivenDate(frequencyStartDate, 0).get("STARTDATE");
			}
		} else if (projects != null && projects.getInvoiceFrequency().equalsIgnoreCase("Bi-Weekly")) {
			Date biWeeklyStartMondayDate = timesheetFrequencyGeneratorUtil
					.getBiweeklyStartMonDayDateByUsingProjectStartDateAndCurrentDateForTsImports(projects,
							timesheetDate);
			Calendar cal = Calendar.getInstance(TimeZone.getDefault());
			cal.setLenient(false);
			cal.setTime(biWeeklyStartMondayDate);
			Calendar startMonday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON",
					loginOrgLocation.getDateFormat());
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
				startMonday = CalendarUtil.getPreviousWeekDayByCalendar(cal, "MON", loginOrgLocation.getDateFormat());
				endSunday = CalendarUtil.getPreviousWeekDayByCalendar(cal, "SUN", loginOrgLocation.getDateFormat());
			} else {
				startMonday = CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(cal, "MON",
						loginOrgLocation.getDateFormat());
				endSunday = CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(cal, "SUN",
						loginOrgLocation.getDateFormat());
			}
			frequencyStartDate = startMonday.getTime();
			frequencyEndDate = endSunday.getTime();

		}
		Map<String, String> timeSubFrequencyMap = timesheetFrequencyGeneratorUtil
				.getTabFrequencyListFromProjectInvoiceFrequency(
						commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(), frequencyStartDate),
						commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(), frequencyEndDate),
						loginOrgLocation.getDateFormat());

		for (Map.Entry<String, String> entry : timeSubFrequencyMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			String date = commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(), timesheetDate);
			if (CalendarUtil.isGivenDateBetweenDateRange(key, value, date, loginOrgLocation.getDateFormat())) {
				actualStartDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(),
						key.replaceAll("/", "-"));
				actualEndDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(),
						value.replaceAll("/", "-"));
			}

		}
		timeSheetDayEntriesService.addTimesheetDayEntry(projectMember.getId(), actualStartDate, actualEndDate, frequencyStartDate, frequencyEndDate,
				loginUser.getId(), timesheetDate, hours, comments, taskUUID,null, loginOrgLocation.getDateFormat(),hourlyType);
	}

	@PreAuthorize("hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_FILL_MEMBER_TIMESHEETS')")
	@RequestMapping(value = "/rest/saveTimesheetDayEntries", method = RequestMethod.POST)
	public TimesheetEntryDetailsDTO saveTimesheetDayEntryMobile(@RequestBody TimeSheetSubmissionDayEntryDTO tsDTO, HttpServletRequest request) throws Exception {
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if (loginUser == null && tsDTO.getUserId() != null) {
			loginUser = userService.getUserById(tsDTO.getUserId());
		}else {
			loginUser = userService.getUserById(loginUser.getId());
		}
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		if (orgLoc == null && StringUtils.isNotEmpty(tsDTO.getLocationCode())) {
			orgLoc = orgLocationService.getOrgLocationsByLocationCode(tsDTO.getLocationCode());
		}else {
			orgLoc = orgLocationService.getOrgLocations(orgLoc.getId());
		}
		String currentTimeFrequency = null;	
		String currentTimeSubFrequency = null;
		String timesheetFor = null;
		Long projectMemberId = null;
		if(tsDTO != null && tsDTO.getProjectMemberId() != null && tsDTO.getTimesheetEntries() != null && !tsDTO.getTimesheetEntries().isEmpty()  && StringUtils.isNotEmpty(tsDTO.getTimeFrequency()) && StringUtils.isNotEmpty(tsDTO.getTimeSubFrequency())) {
			projectMemberId = tsDTO.getProjectMemberId();
			timesheetFor = tsDTO.getTimesheetFor();
			List<Long> oldDbIdList = new ArrayList<>();
			List<Long> freshIdList = new ArrayList<>();
			
			Date frequencyStartDate = null;
			Date frequencyEndDate   = null;
			Date actualStartDate = null;
			Date actualEndDate   = null;
			if (StringUtils.isNotEmpty(tsDTO.getTimeFrequency())) {
				currentTimeFrequency = tsDTO.getTimeFrequency();
				String[] frequency = currentTimeFrequency.split("To");
				String startDate = frequency[0].trim();
				String endDate = frequency[1].trim();
				frequencyStartDate = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(),startDate.replaceAll("/", "-"));
				frequencyEndDate =  commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(),endDate.replaceAll("/", "-"));
						
			}	
			if (StringUtils.isNotEmpty(tsDTO.getTimeSubFrequency())) {
				currentTimeSubFrequency = tsDTO.getTimeSubFrequency();
				String[] frequency = currentTimeSubFrequency.split("To");
				String startDate = frequency[0].trim();
				String endDate = frequency[1].trim();
				actualStartDate = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(),startDate.replaceAll("/", "-"));
				actualEndDate =  commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(),endDate.replaceAll("/", "-"));
						
			}	
			ProjectMembers projectMember = projectMembersService.getProjectMemberById(tsDTO.getProjectMemberId());
			if(projectMember != null) {
				int count = 0;
				for(TimesheetEntriesDTO te :tsDTO.getTimesheetEntries()) {
					if(te.getTimesheetTaskDTO() != null && te.getTimesheetTaskDTO().getId() != null) {
						Date timesheetDate = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(),te.getDate().replaceAll("/", "-"));
						if(count == 0 && tsDTO.getId() !=null) {
							oldDbIdList = timesheetDayEntriesDao.getAllTimeSheetDayEntryIdsBySubmissionIdAndEntryDate(tsDTO.getId(),timesheetDate);
						}
						
						TimeSheetDayEntries tde = timeSheetDayEntriesService.addTimesheetDayEntry(projectMember.getId(),actualStartDate,actualEndDate,frequencyStartDate,frequencyEndDate,loginUser.getId(),timesheetDate,te.getHours(),te.getComments(),null,te.getTimesheetTaskDTO().getId(),orgLoc.getDateFormat(),te.getTimesheetTaskDTO().getHourlyType());
						freshIdList.add(tde.getId());
						count++;
					}
				}
			}
			oldDbIdList.removeAll(freshIdList);
			if (oldDbIdList != null && !oldDbIdList.isEmpty()) {
				for (Long removedEntryId : oldDbIdList) {
					timesheetDayEntriesDao.deleteTimeSheetDayEntries(removedEntryId);
				}
		}
	}
		TimesheetExpenseEntryDetailsUtilDTO eedDTO = new TimesheetExpenseEntryDetailsUtilDTO();
		eedDTO.setCurrentTimeFrequency(currentTimeFrequency);
		eedDTO.setCurrentTimeSubFrequency(currentTimeSubFrequency);
		eedDTO.setTimeSheetsFor(timesheetFor);
		eedDTO.setProjectMembersId(projectMemberId);
		eedDTO.setUserId(loginUser.getId());
		eedDTO.setLocationCode(orgLoc.getLocationCode());
		return getTimesheetEntryDetailsMobile(eedDTO, request);
	}
	
	
	
	
	@PreAuthorize("hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_FILL_MEMBER_TIMESHEETS')")
	@RequestMapping(value = "/rest/updateTimesheetDayEntries", method = RequestMethod.POST)
	public TimesheetEntryDetailsDTO updateTimesheetDayEntryMobile(@RequestBody UpdateTimesheetEntryDetailsDTO tsDTO, HttpServletRequest request) throws Exception {
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if (loginUser == null && tsDTO.getUserId() != null) {
			loginUser = userService.getUserById(tsDTO.getUserId());
		}else {
			loginUser = userService.getUserById(loginUser.getId());
		}
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		if (orgLoc == null && StringUtils.isNotEmpty(tsDTO.getLocationCode())) {
			orgLoc = orgLocationService.getOrgLocationsByLocationCode(tsDTO.getLocationCode());
		}else {
			orgLoc = orgLocationService.getOrgLocations(orgLoc.getId());
		}
		if(tsDTO.getSubmissionId() != null) {
		 TimesheetSubmissions timesheetSubmissions = timesheetSubmissionsService.getTimesheetSubmissions(tsDTO.getSubmissionId().toString());
		 if(timesheetSubmissions != null) {
			    HashSet<SubmissionApproverHierarchy> freshSaHierarchyList = new HashSet<SubmissionApproverHierarchy>();
				SubmissionApproverHierarchy sahActivity = null;
			    String status = "";
				String savingType = "";
				Date frequencyStartDate = null;
				Date frequencyEndDate = null;
				if (tsDTO != null && StringUtils.isNotEmpty(tsDTO.getTimesheetStatus())) {
					status = tsDTO.getTimesheetStatus();
				} else {
					status = "Draft";
				}

				if (StringUtils.isEmpty(tsDTO.getTimesheetFor())) {
					savingType = "user";
				} else {
					savingType = tsDTO.getTimesheetFor();
				}
				String activityStatus = null;
	            if (StringUtils.isNotEmpty(status) && status.equalsIgnoreCase("Draft")){
	            	activityStatus ="Draft"; 
				}else if (StringUtils.isNotEmpty(status) && status.equalsIgnoreCase("Submitted")){
					activityStatus ="Submitted"; 
				}
				if (StringUtils.isNotEmpty(tsDTO.getTimeFrequency())) {
					String[] frequency = tsDTO.getTimeFrequency().split("To");
					String frstartDate = frequency[0].trim();
					String frEndDate = frequency[1].trim();
					frequencyStartDate = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(), frstartDate);
					frequencyEndDate = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(), frEndDate);
				}else if(StringUtils.isNotEmpty(tsDTO.getCurrentTimeFrequency())) {
					String[] frequency = tsDTO.getCurrentTimeFrequency().split("To");
					String frstartDate = frequency[0].trim();
					String frEndDate = frequency[1].trim();
					frequencyStartDate = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(), frstartDate);
					frequencyEndDate = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(), frEndDate);
				}
			     List<SubmissionApproverHierarchy> saHierarchyList = submissionApproverHierarchyService.getSubmissionApproverHierarchyBySubmissionId(timesheetSubmissions.getId());
			     for (SubmissionApproverHierarchy saHierarchy : saHierarchyList) {
						Submission_Status currentSahStatus = saHierarchy.getSubmissionStatus();
						if (currentSahStatus != null && currentSahStatus.equals(Submission_Status.SUBMITTED)
								&& StringUtils.isNotEmpty(savingType) && !savingType.equalsIgnoreCase("user")
								&& StringUtils.isNotEmpty(status) && status.equalsIgnoreCase("Draft")) {
							status = "submitted";
						}

						if (StringUtils.isNotEmpty(saHierarchy.getSubmissionStatus().getValue())
								&& saHierarchy.getSubmissionStatus().getValue().equalsIgnoreCase("approved")) {
							status = "approved";
							saHierarchy.setSubmissionStatus(Submission_Status.getNameByValue("approved"));
						} else {
							saHierarchy.setSubmissionStatus(Submission_Status.getNameByValue(status));
						}

						if (StringUtils.isNotEmpty(status) && status.equalsIgnoreCase("Draft"))
							saHierarchy.setSubmissionApprovalStatus(Submission_ApprovalStatus.getNameByValue("new"));

						if (StringUtils.isNotEmpty(status) && status.equalsIgnoreCase("Submitted"))
							saHierarchy.setSubmissionApprovalStatus(
									Submission_ApprovalStatus.getNameByValue("submitted for approval"));

						saHierarchy.setUpdatedDate(CommonUtil.currentDate());
						saHierarchy.setUpdatedBy(loginUser.getUserCode());

						if (frequencyStartDate != null)
							saHierarchy.setFrequencyStartDate(frequencyStartDate);
						if (frequencyEndDate != null)
							saHierarchy.setFrequencyEndDate(frequencyEndDate);
						saHierarchy.setTimesheetActivityLoginUser(loginUser.getId());
						sahActivity = saHierarchy;
						freshSaHierarchyList.add(saHierarchy);
					}
					submissionApproverHierarchyService.saveAllSubmissionApproverHierarchy(new ArrayList<>(freshSaHierarchyList));
					if(StringUtils.isEmpty(tsDTO.getWeekComment()) && frequencyStartDate != null && frequencyEndDate != null) {
						String comment = timesheetSubmissionsService.getWeekCommentByCurrentFrequency(frequencyStartDate, frequencyEndDate, timesheetSubmissions.getProjects().getId(), timesheetSubmissions.getMasterCandidateId().getId());
						if(StringUtils.isNotEmpty(comment));
						timesheetSubmissions.setWeekcomments(comment);
					}
					timesheetSubmissions.setUpdatedDate(CommonUtil.currentDate());
					timesheetSubmissions.setUpdatedBy(loginUser.getUserCode());
					timesheetSubmissionsService.updateTimesheetSubmissions(timesheetSubmissions);	
					if(StringUtils.isNotEmpty(tsDTO.getWeekComment()) && frequencyStartDate != null && frequencyEndDate != null) {
						timesheetSubmissionsService.updateWeekCommentForAllSubmissions(tsDTO.getWeekComment(),frequencyStartDate, frequencyEndDate, timesheetSubmissions.getProjects().getId(), timesheetSubmissions.getMasterCandidateId().getId());
					}
					// save mutliple attachment in timesheet submissison attachment.
					LinkedList<FileInfo> fileInfoList = new LinkedList<>();
					if (tsDTO.getFileInfo() != null) {
						for (FileInfo file : tsDTO.getFileInfo()) {
							FileInfo freshFile = file;
							if (StringUtils.isEmpty(file.getFileDbId())) {
								FileUploadUtil.moveFile(staticPath, file, "timesheetDocuments");
								TimesheetSubmissionsDocuments tsd = new TimesheetSubmissionsDocuments();
								tsd.setTimesheetSubmissions(timesheetSubmissions);
								tsd.setFileUrl(file.getFileURL());
								tsd.setFileName(file.getFileName());
								tsd.setFileContentType(file.getContentType());
								tsd.setStatus(Constants.CANDIDATE_ACTIVE);
								tsd.setCreatedDate(CommonUtil.currentDate());
								tsd.setCreatedBy(loginUser.getUserCode());
								tsd.setUpdatedDate(CommonUtil.currentDate());
								tsd.setUpdatedBy(loginUser.getUserCode());
								tsd = timesheetSubmissionsDocumentsService.addTimesheetSubmissionsDocuments(tsd);
								freshFile.setFileDbId(tsd.getId().toString());
							}
							fileInfoList.add(freshFile);
						}
					}
					tsDTO.setFileInfo(fileInfoList);

					if (tsDTO.getRemovedDocumentIds() != null && !tsDTO.getRemovedDocumentIds().isEmpty()) {
						for (Long removedId : tsDTO.getRemovedDocumentIds()) {
							deleteSubmissionDocument(removedId);
						}
					}
			          TimesheetActivities ta = new TimesheetActivities();
	                   ta.setActivityStatus(activityStatus);
			            ta = timesheetActivityService.saveTimesheetActivity(ta);
			            if(timesheetSubmissions.getId() !=  null) {
			            	List<TimesheetSubmissionsDocuments> documents = timesheetSubmissionsDocumentsService.getAllTimesheetSubmissionsDocumentsBySubmissionId(timesheetSubmissions.getId());
				            for(TimesheetSubmissionsDocuments document:documents) {
				            	TimesheetSubmissionsDocumentsHistory tdhistory = new TimesheetSubmissionsDocumentsHistory();
				            	tdhistory.setTimesheetActivity(ta.getId());
				            	saveTimesheetDocumentHistory(tdhistory, document);
				            }
			            }
						
		      }
	    }
		TimesheetExpenseEntryDetailsUtilDTO eedDTO = new TimesheetExpenseEntryDetailsUtilDTO();
		eedDTO.setCurrentTimeFrequency(tsDTO.getTimeFrequency());
		eedDTO.setCurrentTimeSubFrequency(tsDTO.getTimeSubFrequency());
		eedDTO.setTimeSheetsFor(tsDTO.getTimesheetFor());
		eedDTO.setProjectMembersId(tsDTO.getProjectMemberId());
		eedDTO.setUserId(loginUser.getId());
		eedDTO.setLocationCode(orgLoc.getLocationCode());
		return getTimesheetEntryDetailsMobile(eedDTO, request);
    }
	
	
	
	
	

	

	public static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
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

	@PreAuthorize("hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS')")
	@RequestMapping(value = "/downloadDocument", method = RequestMethod.GET)
	public void downloadDocument(
			@RequestParam(value = "submissionDocumentId", required = false) Long submissionDocumentId,
			HttpServletResponse response) throws Exception {

		TimesheetSubmissionsDocuments timesheetSubmissionsDocuments = timesheetSubmissionsDocumentsService
				.getTimesheetSubmissionsDocuments(submissionDocumentId);
		// TimesheetSubmissions timesheetSubmissions =
		// timesheetSubmissionsService.getTimesheetSubmissions(submissionId.toString());

		response.setContentType("application/octet-stream");
		StringBuilder fileName = new StringBuilder();
		response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"")
				.append(timesheetSubmissionsDocuments.getFileName()).append("\"").toString());
		InputStream inputStream = new ByteArrayInputStream(timesheetSubmissionsDocuments.getDocFile());
		try {
			OutputStream outStream = response.getOutputStream();

			byte[] buffer = new byte[4096];
			int bytesRead = -1;

			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}

			inputStream.close();
			outStream.close();

		} catch (FileNotFoundException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}

	}

	@RequestMapping(value = "/notifyReopenTimeSheet", method = RequestMethod.POST)
	public @ResponseBody ResponseList<String> notifyReopenTimeSheet(
			@RequestParam(value = "projectMemberId", required = false) Long projectMemberId,
			@RequestParam(value = "timesheetFrequency", required = false) String timesheetFrequency,
			@RequestParam(value = "reason", required = false) String reason,
			@RequestParam(value = "totalHours", required = false) Float totalHours, HttpServletRequest request)
			throws Exception {
		ResponseList<String> response = new ResponseList<>();
		try {
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if(loginUser != null && orgLoc != null) {
				 loginUser = userService.getUserById(loginUser.getId());
				 orgLoc = orgLocationService.getOrgLocations(orgLoc.getId());
			}
			String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
			String isSuperUser = Constants.NO;
			if (commonUtil.isSuperAdminForProjectMemberToReopenTimesheets(projectMemberId, loginUser, orgLoc,isGlobalAccess)) {
				isSuperUser = Constants.YES;
			}

			// DateFormat format = Constants.DF_DMY;
			// DateFormat format = LoginSessionController.DATEFORMAT;
			if (StringUtils.isNotEmpty(timesheetFrequency)) {
				String[] frequency = timesheetFrequency.split("To");
				String weekStartDate = frequency[0].trim();
				String weekEndDate = frequency[1].trim();
				Date startDate = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(),
						weekStartDate.replaceAll("/", "-"));
				Date endDate = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(),
						weekEndDate.replaceAll("/", "-"));
				submissionApproverHierarchyService
						.updateSubmissionStatusByProjectMemberIdStartDateAndEndDateWithReOpenReason(projectMemberId,
								startDate, endDate, reason, loginUser, "notify", isSuperUser);

				// send mail & notification logic
				if (isSuperUser.equalsIgnoreCase(Constants.NO)) {
					timesheetEmailUtil.sendReopenTimeSheetMail(projectMemberId, reason, loginUser,
							orgLoc, timesheetFrequency, totalHours);
				}
			}
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			e.printStackTrace();
		}
		return response;

	}

	@RequestMapping(value = "/ws/acceptOrRejectReopenTimeSheetBySuperUser", method = RequestMethod.POST)
	public @ResponseBody ResponseList<String> acceptOrRejectReopenTimeSheetBySuperUser(
			@RequestParam(value = "projectMemberId", required = false) Long projectMemberId,
			@RequestParam(value = "reOpenedUserId", required = false) Long reopenedUserId,
			@RequestParam(value = "timesheetFrequency", required = false) String timesheetFrequency,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "reason", required = false) String reason, HttpServletRequest request)
			throws Exception {
		ResponseList<String> response = new ResponseList<>();
		try {
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if(loginUser != null && orgLoc != null) {
				loginUser = userService.getUserById(loginUser.getId());
				orgLoc = orgLocationService.getOrgLocations(orgLoc.getId());
			}
			User reopenedUser = userService.getUserById(reopenedUserId);
			// DateFormat format = Constants.DF_DMY;
			// DateFormat format = LoginSessionController.DATEFORMAT;
			if (StringUtils.isNotEmpty(timesheetFrequency) && null != type && "accept".equalsIgnoreCase(type)) {
				String[] frequency = timesheetFrequency.split("To");
				String weekStartDate = frequency[0].trim();
				String weekEndDate = frequency[1].trim();
				Date startDate = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(),
						weekStartDate.replaceAll("/", "-"));
				Date endDate = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(),
						weekEndDate.replaceAll("/", "-"));
				submissionApproverHierarchyService
						.updateSubmissionStatusByProjectMemberIdStartDateAndEndDateWithReOpenReason(projectMemberId,
								startDate, endDate, reason, loginUser, type, Constants.YES);

				// send mail & notification logic
				timesheetEmailUtil.sendReopenTimeSheetAcceptOrRejectMail(projectMemberId, reason,
						loginUser, orgLoc, timesheetFrequency, type, reopenedUser);

			} else if (StringUtils.isNotEmpty(timesheetFrequency) && null != type && "reject".equalsIgnoreCase(type)
					&& null != reason && !"".equalsIgnoreCase(reason)) {
				String[] frequency = timesheetFrequency.split("To");
				String weekStartDate = frequency[0].trim();
				String weekEndDate = frequency[1].trim();
				Date startDate = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(),
						weekStartDate.replaceAll("/", "-"));
				Date endDate = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(),
						weekEndDate.replaceAll("/", "-"));
				submissionApproverHierarchyService
						.updateSubmissionStatusByProjectMemberIdStartDateAndEndDateWithReOpenReason(projectMemberId,
								startDate, endDate, reason, loginUser, type, Constants.YES);

				// send mail & notification logic
				timesheetEmailUtil.sendReopenTimeSheetAcceptOrRejectMail(projectMemberId, reason,
						loginUser, orgLoc, timesheetFrequency, type, reopenedUser);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			e.printStackTrace();
		}
		return response;

	}

	@PreAuthorize("hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS')")
	@RequestMapping(value = "/rest/deleteSubmissionDocument", method = RequestMethod.POST)
	public String deleteSubmissionDocumentForMobile(
			@RequestParam(value = "submissionDocumentId") Long submissionDocumentId) throws Exception {
		Response<String> resp = deleteSubmissionDocument(submissionDocumentId);
		String output = "";
		if (resp.getResponse() == Result.SUCCESS.name()) {
			output = "success";
		} else if (resp.getResponse() == Result.FAILED.name()) {
			output = "failure";
		}
		return output;
	}

	@PreAuthorize("hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS')")
	@RequestMapping(value = "/deleteSubmissionDocument", method = RequestMethod.POST)
	public Response<String> deleteSubmissionDocument(
			@RequestParam(value = "submissionDocumentId", required = false) Long submissionDocumentId) {
		Response<String> resp = new Response<>();
		try {
			TimesheetSubmissionsDocuments timesheetSubmissionsDocuments = timesheetSubmissionsDocumentsService
					.getTimesheetSubmissionsDocuments(submissionDocumentId);
			timesheetSubmissionsDocuments.setDocFile(null);
			timesheetSubmissionsDocuments.setFileContentType(null);
			timesheetSubmissionsDocuments.setFileName(null);
			timesheetSubmissionsDocuments.setStatus(Constants.CANDIDATE_IN_ACTIVE);
			TimesheetSubmissionsDocuments tsd = timesheetSubmissionsDocumentsService.addTimesheetSubmissionsDocuments(timesheetSubmissionsDocuments);
			if (tsd != null) {
				resp.setResponse(Result.SUCCESS.name());
				resp.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				resp.setResponse(Result.FAILED.name());
				resp.setResponseMessage(Result.FAILED.getValue());
			}
		} catch (Exception e) {
			resp.setResponse(Result.AWKWARD.name());
			resp.setResponseMessage(Result.AWKWARD.getValue());
			e.printStackTrace();
		}

		return resp;
	}

	/*public void linkLeaveTypeWithTimeSheet(User leaveApproverUserId, OrgLocations orgLocations, Long leaveTypeId,
			Leave leave, String leaveStatusType) throws Exception {
		List<ProjectTasks> projectTaskList = leaveTypeService
				.getProjectTaskByorgLocationIdAndLeaveTypeId(orgLocations.getId(), leaveTypeId);
		if (projectTaskList != null && !projectTaskList.isEmpty()) {
			for (ProjectTasks projectTask : projectTaskList) {
				ProjectMembers projectMember = projectMembersService.getProjectMemberByUserIdAndProjectId(leave.getUser().getId(), projectTask.getProjectId().getId());
				if (projectMember != null) {
					HashSet<Date> pmhDateList = projectMembersHistoryService
							.getAllProjectMemberHistoryDatesByProjectMemberIdAndWeekStartDateEndDate(
									projectMember.getId(), leave.getFromDate(), leave.getToDate());
					if (pmhDateList.isEmpty() || pmhDateList == null)
						continue;
					
					String dateList = commonUtil.getDaysBetweenDates(leave.getFromDate(), leave.getToDate(),orgLocations.getDateFormat());
					String leaveDate[] = dateList.split(",");
					for (int i = 0; i < leaveDate.length; i++) {
					if (projectTask != null && projectMember != null && leave != null) {
					    for (Date pmhDate :pmhDateList) {
					    	String leaveDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), pmhDate);
						if(!pmhDateList.contains(leaveDate[i]))
							continue;
						
						if(leave.getFromHalfDay() !=null && leave.getFromHalfDay().equalsIgnoreCase("YES") && pmhDate.equals(leave.getFromDate())) {
							addTimesheetDayEntriesByProjectIdAndProjectMemberId(projectTask.getProjectId().getId(),	projectMember.getId(), projectTask.getProjectTasksUUID(), leaveDate,CalendarUtil.getHalfTimesheetHoursDisplayFormat(orgLocations.getAverageWorkingHours().floatValue()).replace(".", ":"),
									leave.getReason(), leaveApproverUserId, orgLocations, leaveStatusType);
						}
						else if(leave.getToHalfDay() !=null && leave.getToHalfDay().equalsIgnoreCase("YES") && pmhDate.equals(leave.getToDate())) {
							addTimesheetDayEntriesByProjectIdAndProjectMemberId(projectTask.getProjectId().getId(),	projectMember.getId(), projectTask.getProjectTasksUUID(), leaveDate,CalendarUtil.getHalfTimesheetHoursDisplayFormat(orgLocations.getAverageWorkingHours().floatValue()).replace(".", ":"),
									leave.getReason(), leaveApproverUserId, orgLocations, leaveStatusType);
						}
						else {
								addTimesheetDayEntriesByProjectIdAndProjectMemberId(projectTask.getProjectId().getId(),projectMember.getId(), projectTask.getProjectTasksUUID(),leaveDate,
										CalendarUtil.getTimesheetHoursDisplayFormat(orgLocations.getAverageWorkingHours().floatValue()).replace(".", ":"),
										leave.getReason(), leaveApproverUserId, orgLocations, leaveStatusType);
							}
						}
					}
				}
			}
		}
	}

	public void linkHolidaysWithTimeSheet(User user, OrgLocations orgLocations, Holiday holiday) throws Exception {
		List<ProjectTasks> projectTaskList = holidayService
				.getHolidayProjectTaskByorgLocationIdAndHolidayId(orgLocations.getId(), holiday.getId());
		if (projectTaskList != null && !projectTaskList.isEmpty()) {
			for (ProjectTasks projectTask : projectTaskList) {
				List<ProjectMembers> projectMemberList = projectMembersService
						.getAllProjectMembersByProjectId(projectTask.getProjectId().getId());
				if (projectMemberList != null && !projectMemberList.isEmpty() && holiday != null) {
					for (ProjectMembers projectMember : projectMemberList) {
						if (projectMember != null) {
							String dateList = commonUtil.getDaysBetweenDates(holiday.getFromDate(), holiday.getToDate(),
									orgLocations.getDateFormat());
							String holidayDate[] = dateList.split(",");
							for (int i = 0; i < holidayDate.length; i++) {
								addTimesheetDayEntriesByProjectIdAndProjectMemberId(projectTask.getProjectId().getId(),
										projectMember.getId(), projectTask.getProjectTasksUUID(), holidayDate[i],
										CalendarUtil
												.getTimesheetHoursDisplayFormat(
														orgLocations.getAverageWorkingHours().floatValue())
												.replace(".", ":"),
										holiday.getHolidayName(), user, orgLocations, null);
							}
						}
					}
				}
			}
		}
	}
*/
	/**
	 * @author Rajashekar
	 * @param request
	 * @param navigation
	 * @param startDate
	 * @return JSON
	 * @throws Exception
	 */

	@RequestMapping(value = "/exportTimeSheet")
	@PreAuthorize("hasAnyRole('ROLE_FILL_MY_TIMESHEETS')")
	public void ExportTimeShhet(HttpServletRequest request,
			@RequestParam(value = "projectMemberId", required = false) Long projectMemberId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestParam(value = "exportType", required = false) String exportType,
			@RequestParam(value = "requestFrom", required = false) String requestFrom, HttpServletResponse response)
			throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(loginUser != null && orgLocations != null) {
			 loginUser = userService.getUserById(loginUser.getId());
			 orgLocations = orgLocationService.getOrgLocations(orgLocations.getId());
		}
		String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		ProjectMembers projectMember = null;
		if (null != projectMemberId) {
			projectMember = projectMembersService.getProjectMemberById(projectMemberId);
		}

		File file = null;
		if (!"pdf".equalsIgnoreCase(exportType)) {
			file = exportTimesheetEntries.exportTimesheets(projectMember, startDate, endDate, orgLocations, requestFrom,
					loginUser,isGlobalAccess);
		} else {
			file = timeSheetPdfUtil.exportTimesheetPdf(projectMember, startDate, endDate, orgLocations, requestFrom,
					loginUser,isGlobalAccess);
		}

		if (file != null) {
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);
			StringBuilder fileName = new StringBuilder();
			response.setHeader("Content-Disposition",
					fileName.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
			inputStream.close();
			response.getOutputStream().close();
			response.getOutputStream().flush();
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS')")
	@RequestMapping(value = "/saveSubmissionApprovalStatusByProjectMemberId", method = RequestMethod.POST)
	public Response<String> saveSubmissionApprovalStatusByProjectMemberId(HttpServletRequest request,
			@RequestParam(value = "projectMemberId", required = false) Long projectMemberId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestParam(value = "approvalStatus", required = false) String approvalStatus,
			@RequestParam(value = "rejectedReason", required = false) String rejectedReason) throws Exception {
		Response<String> resp = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
					"orgLocations");
			if(user !=null && loginOrgLocation != null) {
				 user = userService.getUserById(user.getId());
				 loginOrgLocation = orgLocationService.getOrgLocations(loginOrgLocation.getId());
			}
			String loginOrgCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "orgCode");
			// SimpleDateFormat simpleDateFormat =
			// loginOrgLocation.getLocFullFormDateFormatter();
			// DateFormat sdf = LoginSessionController.DATEFORMAT;
			Date sDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(), startDate);
			Date eDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(), endDate);
			String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
			submissionApproverHierarchyService.updateApprovalStatusByApproverForProjectMemberId(projectMemberId,user.getId(), sDate, eDate, approvalStatus, rejectedReason, loginOrgLocation,isGlobalAccess);
			sendEmailToEmployeeTimesheetApprovalStatus(projectMemberId, user, startDate, endDate, approvalStatus,
					rejectedReason, loginOrgLocation, loginOrgCode);
			resp.setResponse(Result.SUCCESS.name());
			resp.setResponseMessage(Result.SUCCESS.getValue());
		} catch (Exception e) {
			resp.setResponse(Result.AWKWARD.name());
			resp.setResponseMessage(Result.AWKWARD.getValue());
			e.printStackTrace();
		}
		return resp;
	}

	private void sendEmailToEmployeeTimesheetApprovalStatus(Long pmId, User loginUser, String startDate, String endDate,
			String status, String rejectedReason, OrgLocations loginOrgLocation, String loginOrgCode) throws Exception {

		ProjectMembers projectMember = projectMembersService.getProjectMemberById(pmId);
		Date frequencyStartDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(),
				startDate.replaceAll("/", "-"));
		Date frequencyEndDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(),
				endDate.replaceAll("/", "-"));

		SubmissionApproverHierarchy sah = submissionApproverHierarchyService
				.getSubmissionApproverHierarchyForEmployeeByProjectIdAndMasterCandidateId(frequencyStartDate,
						frequencyEndDate, projectMember.getProjects().getId(),
						projectMember.getMasterCandidateId().getId());
		Submission_Status submissionStatus = sah.getSubmissionStatus();
		String submissionStatusStr = submissionStatus.getValue();
		if (!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr
				.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())
				|| submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue()))) {
			// For Employee
			timesheetEmailUtil.sendEmailNotificationToEmployeeAndApproversOfEndClient(
					projectMember.getProjectMemberCandidteId().getFirstName(),
					projectMember.getProjectMemberCandidteId().getLastName(),
					projectMember.getProjectMemberCandidteId().getEmail(), loginUser, loginOrgLocation, projectMember,
					startDate, endDate, submissionStatusStr, rejectedReason);

			// For Approvers of EndClient
			List<ProjectApproverHierarchy> approverList = projectApproverHierarchyService
					.getProjectsHierarchyListByProjectIdAndOrgLocationId(projectMember.getProjects().getId(),
							loginOrgLocation.getId());
			if (approverList != null && approverList.isEmpty()) {
				for (ProjectApproverHierarchy approver : approverList) {
					if (!loginUser.getUserCode().equalsIgnoreCase(approver.getApproverUserId().getUserCode())) {
						timesheetEmailUtil.sendEmailNotificationToEmployeeAndApproversOfEndClient(
								approver.getApproverUserId().getFirstName(), approver.getApproverUserId().getLastName(),
								approver.getApproverUserId().getEmail(), loginUser, loginOrgLocation, projectMember,
								startDate, endDate, submissionStatusStr, rejectedReason);
					}
				}
			}

			//

		}
		// Send Emails & Notifications to vendors
		timesheetEmailUtil.sendEmailToVendorAtTimesheetApprovalStatus(projectMember, loginUser, loginOrgLocation, startDate,
				endDate, status, rejectedReason);

	}

	
	

	@RequestMapping(value = "/getExportTimeSheetForApprovalMechanizm")
	public void getExportTimeSheetForApprovalMechanizm(HttpServletRequest request,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestParam(value = "exportType", required = false) String exportType,
			@RequestParam(value = "client", required = false) String client,
			@RequestParam(value = "project", required = false) String project,
			@RequestParam(value = "stage", required = false) String stage,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "timesheetFrequency", required = false) String timesheetFrequency,
			@RequestParam(value = "frequencyMonth", required = false) String frequencyMonth,
			@RequestParam(value = "weekFrequency", required = false) String weekFrequency, HttpServletResponse response)
			throws Exception {

		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		if(user != null && orgLocations != null) {
			user = userService.getUserById(user.getId());
			orgLocations = orgLocationService.getOrgLocations(orgLocations.getId());
		}
		String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		// DateFormat df = Constants.DMY;
		// DateFormat df = LoginSessionController.DATEFORMAT;
		Date stDate = null;
		Date edDate = null;
		if (null != startDate && !("undefined").equalsIgnoreCase(startDate) && null != endDate
				&& !("undefined").equalsIgnoreCase(endDate)) {
			stDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), startDate);
			edDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), endDate);
		}
		File file = null;

		if (timesheetFrequency.equalsIgnoreCase("Monthly")) {
			Map<String, Date> dateList = CalendarUtil
					.getMonthStartDateAndEndDateFromGivenMonthString(frequencyMonth.toString());

			stDate = dateList.get("STARTDATE");
			edDate = dateList.get("ENDDATE");
		}
		if ("pdf".equalsIgnoreCase(exportType)) {
			file = exportTimesheetEntries.timeSheetApprovelExport(user, orgLocations, stDate, edDate,
					timesheetFrequency, client,project, stage, frequencyMonth, weekFrequency,isGlobalAccess,searchValue);
		} else {
			file = exportTimesheetEntries.timeSheetApprovelExportExcel(user, orgLocations, stDate, edDate,
					timesheetFrequency, client,project, stage, frequencyMonth, weekFrequency,isGlobalAccess,searchValue);
		}

		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		response.setContentType(mimeType);
		StringBuilder fileName = new StringBuilder();
		response.setHeader("Content-Disposition",
				fileName.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
		response.setContentLength((int) file.length());
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
		inputStream.close();
		response.getOutputStream().close();
		response.getOutputStream().flush();
	}

	@PreAuthorize("hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_FILL_MEMBER_TIMESHEETS')")
	@RequestMapping(value = "/projectTasksById", method = RequestMethod.POST)
	public @ResponseBody String getProjectTasks(@RequestParam(value = "projectId", required = false) Long projectId,
			@RequestParam(value = "avilableProjectTaskIds", required = false) String avilableProjectTaskIds)
			throws Exception {

		List<Long> ids = new ArrayList<Long>();
		String[] avilableProjectTaskId = avilableProjectTaskIds.split(",");
		for (String id : avilableProjectTaskId) {
			if (id != null && !id.isEmpty()) {
				ids.add(Long.parseLong(id));
			}
		}
		String resultJson;
		Collection<ProjectTasks> taskList = projectTasksService
				.getAllTasksByProjectIdAndExistedProjectTaskIds(projectId, ids);
		resultJson = commonUtil.toJSON(taskList);
		return resultJson;
	}
	@PreAuthorize("hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_FILL_MEMBER_TIMESHEETS')")
	@RequestMapping(value = "/projectTasksByMemberId", method = RequestMethod.POST)
	public @ResponseBody String getProjectTasksByMemberId(@RequestParam(value = "projectMemeberId", required = true) Long projectMemeberId,
			@RequestParam(value = "avilableProjectTaskIds", required = false) String avilableProjectTaskIds)
			throws Exception {

		List<Long> ids = new ArrayList<Long>();
		String[] avilableProjectTaskId = avilableProjectTaskIds.split(",");
		for (String id : avilableProjectTaskId) {
			if (id != null && !id.isEmpty()) {
				ids.add(Long.parseLong(id));
			}
		}
		String resultJson="";
		if (projectMemeberId != null) {
			ProjectMembers projectMember = projectMembersService.getProjectMemberById(projectMemeberId);
			Collection<ProjectTasks> taskList = projectTasksService
					.getAllTasksByProjectIdAndExistedProjectTaskIds(projectMember.getProjects().getId(), ids);
			resultJson = commonUtil.toJSON(taskList);
			
		}
		return resultJson;

	}
	private String getHoursStringFormHours(Integer hours) {
		String hrs = "";
		if(hours != null) {
			if(hours.toString().length() == 1) {
				hrs = "0"+hours+":00";
			}else if(hours.toString().length() == 2) {
				hrs = hours+":00";
			}
		}
		return hrs;
	}
}