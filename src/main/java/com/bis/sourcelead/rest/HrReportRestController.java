package com.bis.sourcelead.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.DayWiseTimesheetReoprtsDTO;
import com.bis.sourcelead.dto.DayWiseTimesheetReoprtsListDTO;
import com.bis.sourcelead.dto.ProjectsDTO;
import com.bis.sourcelead.dto.ReportsTimeDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.TimeReportsSubmissionDetailsDTO;
import com.bis.sourcelead.dto.TimesheetReportsDTO;
import com.bis.sourcelead.entity.Asset;
import com.bis.sourcelead.entity.AssetCategory;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Shift;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.reportsDTO.AssetsReportDTO;
import com.bis.sourcelead.service.AppliedLeaveDateService;
import com.bis.sourcelead.service.AssetCategoryService;
import com.bis.sourcelead.service.AssetService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.ExpensesReportService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.ShiftService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.timesheet.service.DayEntryReportsService;
import com.bis.sourcelead.timesheet.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.timesheet.service.TimesheetReportService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.PdfUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.util.TimesheetApprovalFrequencyGeneratorUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/app")
public class HrReportRestController {
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	AssetCategoryService assetCategoryService;
	
	@Autowired
	EmployeesService employeesService;
	
	@Autowired
	ShiftService shiftService;
	
	@Autowired
	AssetService assetService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	PdfUtil pdfUtil;
	
	@Autowired
	private ProjectsService projectService;
	
	@Autowired
	private DayEntryReportsService dayEntryReportsService;
	
	@Autowired
	ProjectMembersService projectMembersService;
	
	
	@Autowired
	private ProjectMembersService projectMemberService;
	
	
	@Autowired
	private TimesheetApprovalFrequencyGeneratorUtil timesheetApprovalFrequencyGeneratorUtil;
	
	
	@Autowired
	private SubmissionApproverHierarchyService submissionApproverHierarchyService;
	
	@Autowired
	private ProjectMembersHistoryService projectMembersHistoryService;
	
	
	@Autowired
	private AppliedLeaveDateService appliedLeaveDateService;
	
	@Autowired
	TimesheetReportService timesheetReportService;
	
	@Autowired
	private ExpensesReportService expensesReportService;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	@Autowired
	private UserService userService;
	
	
	/**
	 * Get Asset Report Jsp
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/assetsReport")
	public ModelAndView assetsReport(HttpServletRequest request) throws Exception
	{
		ModelAndView model = new ModelAndView();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<AssetCategory> assetCategoriesList = assetCategoryService.getAssetCategoryListByLocationId(orgLocation.getId());
		model.addObject("assetCategoriesList", assetCategoriesList);
		List<Employees> employeesList = employeesService.getEmployeesByLocationId(orgLocation.getId());
		model.addObject("employeesList", employeesList);
		List<Asset> assetList = assetService.getAllAssetByOrgId(orgLocation.getId());
		model.addObject("assetList", assetList);
		List<Shift> shiftList = shiftService.getShiftList(orgLocation.getId());
		model.addObject("shiftList", shiftList);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("reports/assetsReport");
		return model;
	}
	
	
	
	/**
	 * To Get Day Wise Timesheet Report JSP
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/daywiseTimeSheetReports")
	public ModelAndView daywiseTimeSheetReports(HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 model.setViewName("reports/dayWiseTimesheetReports");
		 model.addObject("SourceLeadWeb_orgLocationId",orgLocations.getId());
		 model.addObject("sourceleadUrl", sourceleadUrl);
		return model;

	}
	
	
	@RequestMapping(value = "/projectTimeEntriesReport")
	public ModelAndView projectTimeEntriesReport(HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 model.setViewName("reports/projectTimeEntryReportList");
		 model.addObject("SourceLeadWeb_orgLocationId",orgLocations.getId());
		 model.addObject("sourceleadUrl", sourceleadUrl);
		return model;

	}
	
	
	@RequestMapping(value = "/resourceTimeEntriesReport")
	public ModelAndView resourceTimeEntriesReport(HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 model.setViewName("reports/resourceTimeEntryReportList");
		 model.addObject("SourceLeadWeb_orgLocationId",orgLocations.getId());
		 model.addObject("sourceleadUrl", sourceleadUrl);
		return model;

	}
	
	
	/**
	 * Getting Asset Reports 
	 * @param assetsReportDTO
	 * @param request
	 * @return ResponseList<AssetsReportDTO>
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAssetReport", method = RequestMethod.POST)
	public @ResponseBody ResponseList<AssetsReportDTO> getAssetReportList(AssetsReportDTO assetsReportDTO, HttpServletRequest request) throws Exception
	{
		// User user = (User)
		// SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startTime = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), assetsReportDTO.getFromDate());
		Date endTime = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), assetsReportDTO.getToDate());
		String customStartTimeStr = TZ_DF_CSD.format(startTime);
		String customEndTimeStr = TZ_DF_CED.format(endTime);
		Date startDateUTC = TZ_DF_TS.parse(customStartTimeStr);
		Date endDateUTC = TZ_DF_TS.parse(customEndTimeStr);
		assetsReportDTO.setConvertedFromDate(startDateUTC);
		assetsReportDTO.setConvertedToDate(endDateUTC);
		ResponseList<AssetsReportDTO> assetReportList = assetService.getAssetReportList(assetsReportDTO, orgLocation.getId());
		return assetReportList;
	}
	
	@RequestMapping(value="/getAllUserListBasedOnOrgLocationId", method= RequestMethod.POST)
	public List<ProjectMembers> getAllUserListBasedOnOrgLocationId(HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		List<ProjectMembers> projectMemberList = projectMembersService.getAllProjectsByOrgLocationId(orgLocations.getId());
		projectMemberList.forEach(pm -> pm.setUserId(String.valueOf(pm.getProjectMemberCandidteId().getConsultantUser().getId())));
		return projectMemberList;
	}
	
	@RequestMapping(value = "/timeSheetReports")
	public ModelAndView timeSheetReports(HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		 model.setViewName("reports/timesheetReports");
		 model.addObject("sourceleadUrl", sourceleadUrl);
		return model;

	}
	
	@RequestMapping(value = "/expensesReportsList")
	public ModelAndView expensesApprovalList(HttpServletRequest request) throws Exception
	{
		
		ModelAndView model = new ModelAndView();
		SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession().getAttribute("sessionData");
		if (null == sessionData) {
			sessionData = new SessionMaintainanceData();
		}
		request.getSession().setAttribute("sessionData", sessionData);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("reports/expensesReports");
		return model;
	}

	@RequestMapping(value = "/dailyTimeSheetReports")
	public ModelAndView dailyTimeSheetReports(HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		 model.setViewName("reports/dailyTimesheetReports");
		 return model;
	}
	
	@RequestMapping(value="/dayEntriesReportList",method= RequestMethod.POST)
	public ModelAndView getDayEntriesList(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		model.addObject("exportDto",dayWiseTimesheetReoprtsDto);
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		if(orgLocations != null) {
			orgLocations = orgLocationService.getOrgLocationById(orgLocations.getId());
		}
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(loginUser != null) {
			loginUser = userService.getUserById(loginUser.getId());
		}
		String isGlobalUser = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		
		int start = dayWiseTimesheetReoprtsDto.getStart();
		int length = 0;
		if(dayWiseTimesheetReoprtsDto.getPageNo()<1) {
			dayWiseTimesheetReoprtsDto.setPageNo(1);
		}
		if(dayWiseTimesheetReoprtsDto.getPageSize()<1) {
			length = 10;
			dayWiseTimesheetReoprtsDto.setPageSize(10);
		}else {
			length = dayWiseTimesheetReoprtsDto.getPageSize();
		}
		dayWiseTimesheetReoprtsDto.setStart(start);
		dayWiseTimesheetReoprtsDto.setLength(length);
		
		ResponseList<DayWiseTimesheetReoprtsListDTO>  listDto = dayEntryReportsService.getDayEntriesList(dayWiseTimesheetReoprtsDto,orgLocations, loginUser,isGlobalUser) ;
		model.addObject("dayWiseTimesheetReoprtsList",listDto.getData());
	    if(listDto != null && listDto.getData() != null && !listDto.getData().isEmpty()) {
	    	for (DayWiseTimesheetReoprtsListDTO totalHours : listDto.getData()) {
				model.addObject("totalBillable",totalHours.getBillableTotalCount());
				model.addObject("totalNonBillable",totalHours.getNonBillableTotalCount());
			}
	    }
		model.addObject("startDate",dayWiseTimesheetReoprtsDto.getStartDate());
		model.addObject("endDate",dayWiseTimesheetReoprtsDto.getEndDate());
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("reports/dayEntriesReportList");
		model.addObject("totalRows",listDto.getRecordsFiltered());
		model.addObject("pageCount",dayWiseTimesheetReoprtsDto.getPageNo());
		model.addObject("pageSize",dayWiseTimesheetReoprtsDto.getLength());
		model.addObject("searchValue",dayWiseTimesheetReoprtsDto.getSearchValue());
		return model;
	}
	
	@RequestMapping(value="/getProjectListBasedOnStartAndEndDate")
	public List<ProjectsDTO> getProjectListBasedOnStartAndEndDate(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,HttpServletRequest request) throws JsonProcessingException {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request,"org");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String isGlobalUser = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		dayWiseTimesheetReoprtsDto.setLocationsId(orgLocations.getId());
		dayWiseTimesheetReoprtsDto.setLoginUserId(loginUser.getId());
		List<ProjectsDTO> projectList  = null;
		try {
			projectList = projectService.getProjectListForDaywiseReports(orgLocations.getId(), org.getId(), isGlobalUser, loginUser.getId(), dayWiseTimesheetReoprtsDto.getTypeOfProject(),userRole);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		return projectList;
	}

	@RequestMapping(value="/getProjectMembersBasedOnProjectId")
	public List<ProjectMembers> getProjectMembersBasedOnProjectId(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,HttpServletRequest request) throws Exception {
		List<ProjectMembers> projectMemberList = null;
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request,"org");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		String isGlobalUser = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		dayWiseTimesheetReoprtsDto.setLocationsId(orgLocations.getId());
		dayWiseTimesheetReoprtsDto.setLoginUserId(loginUser.getId());
		dayWiseTimesheetReoprtsDto.setLoginUserRoleType(loginUserRole);
		dayWiseTimesheetReoprtsDto.setIsGlobalUser(isGlobalUser);
		dayWiseTimesheetReoprtsDto.setOrgId(org.getId());
		projectMemberList = projectMemberService.getAllProjectMembersBasedOnProjectIdStartDateEndDateAndEmploymentType(dayWiseTimesheetReoprtsDto);
		return projectMemberList;
	}

	@RequestMapping(value="/exportDaywiseTimesheetReport")
	public void exportDaywiseTimesheetReport(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,HttpServletRequest request,HttpServletResponse response) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		if(orgLocations != null) {
				orgLocations = orgLocationService.getOrgLocationById(orgLocations.getId());
		}
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(loginUser != null) {
			loginUser = userService.getUserById(loginUser.getId());
		}
		String isGlobalUser = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		File file = null;
		if ("excel".equalsIgnoreCase(dayWiseTimesheetReoprtsDto.getExportType())) {
				file = dayEntryReportsService.exportDayEntryReportsWithoutProjectExcel(dayWiseTimesheetReoprtsDto,orgLocations,loginUser,isGlobalUser);
		} else if("pdf".equalsIgnoreCase(dayWiseTimesheetReoprtsDto.getExportType())){
				file = dayEntryReportsService.exportDayEntryReportsWithProjectPdf(dayWiseTimesheetReoprtsDto,orgLocations,loginUser,isGlobalUser);
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
	
	@RequestMapping(value = "/timesheetReportsDiv", method = RequestMethod.POST ) 
	public ModelAndView prapareTimesheetReports(TimesheetReportsDTO timesheetReportsDTO,HttpServletRequest request) throws Exception {
		ModelAndView model = new  ModelAndView();
		try {
		timesheetReportsDTO = timesheetReportsDiv(timesheetReportsDTO,request);
		model.addObject("timesheetReportsDTO",timesheetReportsDTO);
		model.setViewName("reports/timeReportsDropDown");
		}catch(Exception e) {
			model.addObject("AWKWARD", true);
			model.setViewName("reports/timeReportsDropDown");
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
		if(orgLocations != null) {
			orgLocations = orgLocationService.getOrgLocationById(orgLocations.getId());
		}
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
		if(orgLocations != null) {
			orgLocations = orgLocationService.getOrgLocationById(orgLocations.getId());
		}
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(loginUser != null) {
			loginUser = userService.getUserById(loginUser.getId());
		}
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
		   if(user != null) {
				user = userService.getUserById(user.getId());
			}
		   OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		   if(orgLocations != null) {
				orgLocations = orgLocationService.getOrgLocationById(orgLocations.getId());
			}
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
	
	@RequestMapping(value = "/getMemberReports", method = RequestMethod.POST ) 
	public ModelAndView prepareSubmissionDetailsWeb(TimesheetReportsDTO timesheetReportsDTO,HttpServletRequest request) throws Exception {
		ModelAndView model = new  ModelAndView();
		try {
			timesheetReportsDTO = prepareDateFrequencyForTimeSheetReports(timesheetReportsDTO,request);
			timesheetReportsDTO = prepareSubmissionDetails(timesheetReportsDTO,request);
			model.addObject("timesheetReportsDTO",timesheetReportsDTO);
			model.setViewName("reports/timeReportsSubmissionData");
		}catch(Exception e) {
			model.addObject("AWKWARD", true);
			model.setViewName("reports/timeReportsSubmissionData");
		}
		return model;
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
	
	private Map<String, String> getCurrentYearAndCurrentQuater() {
	      Map<String, String> currentYearAndQuater = new HashMap<>();
	      String currentYear = CalendarUtil.getYearPatternByYearPreference(new Date(), 0);
	      String currentQuater = CalendarUtil.getQuarterByDate(new Date());
	      currentYearAndQuater.put(currentYear, currentQuater);
		return currentYearAndQuater;
	
}

	@RequestMapping(value = "/getExpensesReportList")
	public LinkedHashSet<ProjectMembers> getExpensesApprovalList(HttpServletRequest request,@RequestParam(value="datequarterly", required=false) String datequarterly) throws Exception {
		LinkedHashSet<ProjectMembers> finalProjectMemberList = new LinkedHashSet<>();
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(loginUser != null) {
			loginUser = userService.getUserById(loginUser.getId());
		}
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		if(orgLoc != null) {
			orgLoc = orgLocationService.getOrgLocationById(orgLoc.getId());
		}
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
						projectMember.setProfileImage(projectMember.getProjectMemberCandidteId().getConsultantUser().getFileUrl());
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
		if(loginUser != null) {
			loginUser = userService.getUserById(loginUser.getId());
		}
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		if(orgLoc != null) {
			orgLoc = orgLocationService.getOrgLocationById(orgLoc.getId());
		}
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
	
	
	
	@RequestMapping(value="/projectTimeEntriesReportList",method= RequestMethod.POST)
	public ModelAndView projectTimeEntriesReportList(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		model.addObject("exportDto",dayWiseTimesheetReoprtsDto);
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String isGlobalUser = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		
		int start = dayWiseTimesheetReoprtsDto.getStart();
		int length = 0;
		if(dayWiseTimesheetReoprtsDto.getPageNo()<1) {
			dayWiseTimesheetReoprtsDto.setPageNo(1);
		}
		if(dayWiseTimesheetReoprtsDto.getPageSize()<1) {
			length = 10;
			dayWiseTimesheetReoprtsDto.setPageSize(10);
		}else {
			length = dayWiseTimesheetReoprtsDto.getPageSize();
		}
		dayWiseTimesheetReoprtsDto.setStart(start);
		dayWiseTimesheetReoprtsDto.setLength(length);
		
		ResponseList<DayWiseTimesheetReoprtsListDTO>  listDto = dayEntryReportsService.getProjectTimeEntriesList(dayWiseTimesheetReoprtsDto,orgLocations, loginUser,isGlobalUser) ;
		model.addObject("dayWiseTimesheetReoprtsList",listDto.getData());
	    if(listDto != null && listDto.getData() != null && !listDto.getData().isEmpty()) {
	    	for (DayWiseTimesheetReoprtsListDTO totalHours : listDto.getData()) {
				model.addObject("totalBillable",totalHours.getBillableTotalCount());
				model.addObject("totalNonBillable",totalHours.getNonBillableTotalCount());
			}
	    }
		model.addObject("startDate",dayWiseTimesheetReoprtsDto.getStartDate());
		model.addObject("endDate",dayWiseTimesheetReoprtsDto.getEndDate());
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("reports/projectEntriesReportList");
		model.addObject("totalRows",listDto.getRecordsFiltered());
		model.addObject("pageCount",dayWiseTimesheetReoprtsDto.getPageNo());
		model.addObject("pageSize",dayWiseTimesheetReoprtsDto.getLength());
		model.addObject("searchValue",dayWiseTimesheetReoprtsDto.getSearchValue());
		return model;
	}
	
	
	
	@RequestMapping(value="/resourceTimeEntriesReportList",method= RequestMethod.POST)
	public ModelAndView resourceTimeEntriesReportList(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		model.addObject("exportDto",dayWiseTimesheetReoprtsDto);
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String isGlobalUser = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		
		int start = dayWiseTimesheetReoprtsDto.getStart();
		int length = 0;
		if(dayWiseTimesheetReoprtsDto.getPageNo()<1) {
			dayWiseTimesheetReoprtsDto.setPageNo(1);
		}
		if(dayWiseTimesheetReoprtsDto.getPageSize()<1) {
			length = 10;
			dayWiseTimesheetReoprtsDto.setPageSize(10);
		}else {
			length = dayWiseTimesheetReoprtsDto.getPageSize();
		}
		dayWiseTimesheetReoprtsDto.setStart(start);
		dayWiseTimesheetReoprtsDto.setLength(length);
		
		ResponseList<DayWiseTimesheetReoprtsListDTO>  listDto = dayEntryReportsService.getResourceTimeEntriesList(dayWiseTimesheetReoprtsDto,orgLocations, loginUser,isGlobalUser) ;
		model.addObject("dayWiseTimesheetReoprtsList",listDto.getData());
	    if(listDto != null && listDto.getData() != null && !listDto.getData().isEmpty()) {
	    	for (DayWiseTimesheetReoprtsListDTO totalHours : listDto.getData()) {
				model.addObject("totalBillable",totalHours.getBillableTotalCount());
				model.addObject("totalNonBillable",totalHours.getNonBillableTotalCount());
			}
	    }
		model.addObject("startDate",dayWiseTimesheetReoprtsDto.getStartDate());
		model.addObject("endDate",dayWiseTimesheetReoprtsDto.getEndDate());
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("reports/resourceEntriesReportList");
		model.addObject("totalRows",listDto.getRecordsFiltered());
		model.addObject("pageCount",dayWiseTimesheetReoprtsDto.getPageNo());
		model.addObject("pageSize",dayWiseTimesheetReoprtsDto.getLength());
		model.addObject("searchValue",dayWiseTimesheetReoprtsDto.getSearchValue());
		return model;
	}
	
	
	@RequestMapping(value="/getProjectListForProjectTimeEntriesReportBasedOnStartAndEndDate")
	public List<ProjectsDTO> getProjectListForProjectTimeEntriesReportBasedOnStartAndEndDate(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,HttpServletRequest request) throws JsonProcessingException {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request,"org");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String isGlobalUser = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		dayWiseTimesheetReoprtsDto.setLocationsId(orgLocations.getId());
		dayWiseTimesheetReoprtsDto.setLoginUserId(loginUser.getId());
		List<ProjectsDTO> projectList  = null;
		try {
			projectList = projectService.getProjectListForProjectTimeEntriesReports(orgLocations.getId(), org.getId(), isGlobalUser, loginUser.getId(), dayWiseTimesheetReoprtsDto.getTypeOfProject(),userRole);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		return projectList;
	}

	@RequestMapping(value="/getProjectListForResourceTimeEntriesReportBasedOnStartAndEndDate")
	public List<ProjectsDTO> getProjectListForResourceTimeEntriesReportBasedOnStartAndEndDate(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,HttpServletRequest request) throws JsonProcessingException {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request,"org");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String isGlobalUser = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		dayWiseTimesheetReoprtsDto.setLocationsId(orgLocations.getId());
		dayWiseTimesheetReoprtsDto.setLoginUserId(loginUser.getId());
		List<ProjectsDTO> projectList  = null;
		try {
			projectList = projectService.getProjectListForResourceTimeEntriesReports(orgLocations.getId(), org.getId(), isGlobalUser, loginUser.getId(), dayWiseTimesheetReoprtsDto.getTypeOfProject(),userRole);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		return projectList;
	}
	
	@RequestMapping(value="/getProjectMembersForResourceTimeEntriesBasedOnProjectId")
	public List<ProjectMembers> getProjectMembersForResourceTimeEntriesBasedOnProjectId(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,HttpServletRequest request) throws Exception {
		
		List<ProjectMembers> projectMemberList = null;
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request,"org");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		String isGlobalUser = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		dayWiseTimesheetReoprtsDto.setLocationsId(orgLocations.getId());
		dayWiseTimesheetReoprtsDto.setLoginUserId(loginUser.getId());
		dayWiseTimesheetReoprtsDto.setLoginUserRoleType(loginUserRole);
		dayWiseTimesheetReoprtsDto.setIsGlobalUser(isGlobalUser);
		dayWiseTimesheetReoprtsDto.setOrgId(org.getId());
	    projectMemberList = projectMemberService.getAllResourceTimeEntryProjectMembersBasedOnProjectIdStartDateEndDateAndEmploymentType(dayWiseTimesheetReoprtsDto);
		return projectMemberList;
	}

	@RequestMapping(value="/exportProjectTimeEntriresTimesheetReport")
	public void exportProjectTimeEntriresTimesheetReport(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,HttpServletRequest request,HttpServletResponse response) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String isGlobalUser = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		File file = null;
		if ("excel".equalsIgnoreCase(dayWiseTimesheetReoprtsDto.getExportType())) {
				file = dayEntryReportsService.exportProjectTimeEntryReportsWithoutProjectExcel(dayWiseTimesheetReoprtsDto,orgLocations,loginUser,isGlobalUser);
		} else if("pdf".equalsIgnoreCase(dayWiseTimesheetReoprtsDto.getExportType())){
				file = dayEntryReportsService.exportProjectTimeEntryReportsWithProjectPdf(dayWiseTimesheetReoprtsDto,orgLocations,loginUser,isGlobalUser);
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


	@RequestMapping(value="/exportResourceTimeEntriresTimesheetReport")
	public void exportResourceTimeEntriresTimesheetReport(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,HttpServletRequest request,HttpServletResponse response) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String isGlobalUser = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		File file = null;
		if ("excel".equalsIgnoreCase(dayWiseTimesheetReoprtsDto.getExportType())) {
				file = dayEntryReportsService.exportResourceTimeEntryReportsWithoutProjectExcel(dayWiseTimesheetReoprtsDto,orgLocations,loginUser,isGlobalUser);
		} else if("pdf".equalsIgnoreCase(dayWiseTimesheetReoprtsDto.getExportType())){
				file = dayEntryReportsService.exportResourceTimeEntryReportsWithProjectPdf(dayWiseTimesheetReoprtsDto,orgLocations,loginUser,isGlobalUser);
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
}
