package com.bis.sourcelead.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URLConnection;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.AssociateBalanceLeaveDao;
import com.bis.sourcelead.dao.HolidayDAO;
import com.bis.sourcelead.dao.LeaveDao;
import com.bis.sourcelead.dao.LeaveManagementHistoryDao;
import com.bis.sourcelead.dao.LeaveTypeDao;
import com.bis.sourcelead.dao.OptionsDao;
import com.bis.sourcelead.dao.RestrictDayDao;
import com.bis.sourcelead.dao.ShiftDao;
import com.bis.sourcelead.dto.HolidayDTO;
import com.bis.sourcelead.dto.LeaveDTO;
import com.bis.sourcelead.dto.LeaveReportsDTO;
import com.bis.sourcelead.dto.LeaveTypeDTO;
import com.bis.sourcelead.dto.OptionsDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.RestrictDayDTO;
import com.bis.sourcelead.dto.RoleDTO;
import com.bis.sourcelead.dto.ShiftDTO;
import com.bis.sourcelead.dto.ValidateDateDTO;
import com.bis.sourcelead.entity.AppliedLeaveDateView;
import com.bis.sourcelead.entity.AssetAssign;
import com.bis.sourcelead.entity.AssociateBalanceLeave;
import com.bis.sourcelead.entity.AssociateBalanceLeaveView;
import com.bis.sourcelead.entity.AssociateGrades;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Holiday;
import com.bis.sourcelead.entity.HolidayAndRestrictday;
import com.bis.sourcelead.entity.HolidayShift;
import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.entity.LeaveApproveHierarchy;
import com.bis.sourcelead.entity.LeaveManagementHistory;
import com.bis.sourcelead.entity.LeaveRole;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.Option;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.RestrictDay;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.Shift;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.AppliedLeaveDateService;
import com.bis.sourcelead.service.AssetService;
import com.bis.sourcelead.service.AssociateBalanceLeaveService;
import com.bis.sourcelead.service.AssociateGradesService;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.HolidayService;
import com.bis.sourcelead.service.LeaveService;
import com.bis.sourcelead.service.LeaveTypeService;
import com.bis.sourcelead.service.OptionsService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.RestrictDayService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.ShiftService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.LeaveManagementUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.ibm.icu.text.DecimalFormat;

@RestController
public class LeaveManagementRestController {

	private static final Logger logger = LoggerFactory.getLogger(LeaveManagementRestController.class);
	
	DecimalFormat dec = new DecimalFormat("#0.00");

	@Autowired
	ShiftService shiftService;

	@Autowired
	LeaveDao leaveDao;

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Value("${static.path}")
	private String staticPath;

	@Autowired
	OrgLocationService orgLocationService;

	@Autowired
	LeaveTypeService leaveTypeService;

	@Autowired
	private HolidayService holidayService;

	@Autowired
	private RestrictDayService restrictDayService;

	@Autowired
	private RolesService rolesService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private OptionsService optionsService;

	@Autowired
	private OptionsDao optionsDao;

	@Autowired
	private RestrictDayDao restrictDayDao;

	@Autowired
	LeaveTypeDao leaveTypeDao;

	@Autowired
	UserService userService;

	@Autowired
	UserRoleService userRoleService;

	@Autowired
	LeaveService leaveService;

	@Autowired
	HolidayDAO holidayDAO;

	@Autowired
	CommonUtil commonUtil;

	@Autowired
	EmployeesService employeesService;

	@Autowired
	ShiftDao shiftDao;

	@Autowired
	LeaveManagementHistoryDao leaveManagementHistoryDao;

	@Autowired
	private OrgService orgService;

	@Autowired
	LeaveManagementUtil leaveManagementUtil;

	@Autowired
	private ProjectTasksService projectTasksService;
	
	@Autowired
	TimeZoneService timeZoneService;
	
	@Autowired
	private AssetService assetService;
	
	@Autowired
	AssociateGradesService associateGradesService;
	
	
	@Autowired
	AssociateBalanceLeaveService associateBalanceLeaveService;
	
	@Autowired
	AssociateBalanceLeaveDao associateBalanceLeaveDao;
	

	@Autowired
	AppliedLeaveDateService appliedLeaveDateService;
	
	@Autowired
	ConversionUtil conversionUtil;
	

	/**
	 * returns Pending leaves jsp
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/pendingLeaves")
	public ModelAndView pendingLeavesJsp(@RequestParam(value = "notifyLink", required = false) String notifyLink,
			@RequestParam(value = "leaveUUID", required = false) String leaveUUID,
			@RequestParam(value = "orgLocationId", required = false) String orgLocationId,
			@RequestParam(value = "status", required = false) String status, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("notifyLink", notifyLink);
		model.addObject("leaveUUID", leaveUUID);
		model.addObject("orgLocationId", orgLocationId);
		model.addObject("status", status);
		model.setViewName("leavemanagement/pendingLeaves");
		return model;
	}

	/**
	 * returns approved leaves jsp
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/approvedLeaves")
	public ModelAndView approvedLeavesJsp(@RequestParam(value = "notifyLink", required = false) String notifyLink,
			@RequestParam(value = "leaveUUID", required = false) String leaveUUID,
			@RequestParam(value = "orgLocationId", required = false) String orgLocationId, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("notifyLink", notifyLink);
		model.addObject("leaveUUID", leaveUUID);
		model.addObject("orgLocationId", orgLocationId);
		model.setViewName("leavemanagement/approvedLeaves");
		return model;
	}

	/**
	 * returns Rejected leaves jsp
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/rejectedLeaves")
	public ModelAndView rejectedLeavesJsp(@RequestParam(value = "notifyLink", required = false) String notifyLink,
			@RequestParam(value = "leaveUUID", required = false) String leaveUUID,
			@RequestParam(value = "orgLocationId", required = false) String orgLocationId, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("notifyLink", notifyLink);
		model.addObject("leaveUUID", leaveUUID);
		model.addObject("orgLocationId", orgLocationId);
		model.setViewName("leavemanagement/rejectedLeaves");
		return model;
	}

	/**
	 * retuens cancel leaves jsp
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/cancelLeaves")
	public ModelAndView cancelLeavesJsp(@RequestParam(value = "notifyLink", required = false) String notifyLink,
			@RequestParam(value = "leaveUUID", required = false) String leaveUUID,
			@RequestParam(value = "orgLocationId", required = false) String orgLocationId, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("notifyLink", notifyLink);
		model.addObject("leaveUUID", leaveUUID);
		model.addObject("orgLocationId", orgLocationId);
		model.setViewName("leavemanagement/cancelLeaves");
		return model;
	}

	/**
	 * retuens leave Management Reports leaves jsp
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/leaveManagementReports")
	public ModelAndView leaveManagementReportsJsp(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView model = new ModelAndView();
		SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession().getAttribute("sessionData");
		if (null == sessionData) {
			sessionData = new SessionMaintainanceData();
		}
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		sessionData.setNav(Constants.LEAVE_MANAGENENT);
		List<Department> departmentList = departmentService.getDepartmentByOrgLocationIdAndStatus(orgLocations.getId(),
				Constants.RECORD_ACTIVE.toString());

		LinkedHashSet<String> yearsList = CalendarUtil.getYearsListFromCurrentDate(1, 10);
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		model.addObject("currentYear", currentYear);
		model.addObject("yearsList", yearsList);
		model.addObject("departmentList", departmentList);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("leavemanagement/leaveManagementReports");
		return model;
	}
	
	/**
	 * retuens leave Management Reports leaves jsp
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception
	 */
	@PreAuthorize("hasAnyRole('ROLE_APPLY_LEAVE')")
	@RequestMapping(value = "/applyLeave")
	public ModelAndView applyLeaveJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		
		
		Long roleId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRoleId");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		List<User> usersList = userService.getUsersHierarchyListByOrgLocationIdAndRoleIdAndNoCurrentUser(
				loginUser.getId(), roleId, orgLocations.getId());
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("usersLis", usersList);
		model.setViewName("leavemanagement/applyLeave");
		return model;
	}

	@RequestMapping(value = { "/getPieChartForLeaveStatus","/rest/getPieChartForLeaveStatus" }, method = RequestMethod.GET)
	public List<LeaveType> getPieChartForLeaveStatus(
			@RequestParam(value = "orgLocationId", required = false) Long orgLocationId,
			@RequestParam(value = "userId", required = false) Long userId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		User loginUser = userService.getUserById(userId);
		UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(userId, orgLocationId);
		
		return leaveTypeService.getLeaveTypeCountForPieChart(orgLocations,loginUser,userRole);

	}

	

	/**
	 * returns leave configuration jsp
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = "/leaveConfiguration")
	public ModelAndView leaveConfigurationJsp(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("leavemanagement/leaveConfigure");
		return model;
	}

	/**
	 * returns add leave jsp
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception
	 */
	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = "/addLeaveType")
	public ModelAndView addLeaveJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		Long roleHierarchyId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "roleHierarchyId");
		List<Department> departmentList = departmentService.getDepartmentByOrgLocationIdAndStatus(orgLocations.getId(),
				Constants.RECORD_ACTIVE.toString());
		List<Roles> rolesList = rolesService.getUserRolesListByModuleAndLoginUserRoleHierarchy(org,
				orgLocations.getId(), roleHierarchyId);
		List<AssociateGrades> gradelist =  associateGradesService.getAssociateGradesListByOrgLocationId(orgLocations.getId());
		model.addObject("gradelist", gradelist);
		model.addObject("employmentTypeList", Constants.EMPLOYMENT_TYPES);
		model.addObject("departmentList", departmentList);
		model.addObject("rolesList", rolesList);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("leavemanagement/addLeaveType");
		return model;
	}

	/**
	 * returns view leave jsp
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = "/viewLeaveTypes")
	public ModelAndView viewLeavesJsp(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("leavemanagement/viewLeaveType");
		return model;
	}

	/**
	 * returns add Holiday jsp
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = "/addHoliday")
	public ModelAndView addHolidayJsp(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<Shift> shiftList = shiftService.getShiftList(orgLocations.getId());
		model.addObject("shiftList", shiftList);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("leavemanagement/addHoliday");
		return model;
	}

	/**
	 * returns view Holiday jsp
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = "/viewHoliday")
	public ModelAndView viewHolidayJsp(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<Shift> shiftList = shiftService.getShiftList(orgLocations.getId());
		LinkedHashSet<String> yearsList = CalendarUtil.getYearsListFromCurrentDate(1, 10);
		model.addObject("shiftList", shiftList);

		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("yearsList", yearsList);
		model.setViewName("leavemanagement/viewHoliday");
		return model;
	}

	/**
	 * returns add shift jsp
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception 
	 */
	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = "/addShift")
	public ModelAndView addShiftJsp(HttpServletRequest request,HttpServletResponse response) throws Exception {
		  OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		  Timezone timezone = timeZoneService.getTimezone(Long.parseLong(orgLocations.getTimeZoneId()));
		   ModelAndView model = new ModelAndView();
		   model.addObject("timezone", timezone.getTimeZoneId());
		   model.addObject("sourceleadUrl", sourceleadUrl);
		   model.setViewName("leavemanagement/addShift");
	       return model;
	}

	/**
	 * returns view shift jsp
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = "/viewShift")
	public ModelAndView viewShiftJsp(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("leavemanagement/viewShift");
		return model;
	}

	/**
	 * returns add RestrictDay jsp
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = "/addRestrictDay")
	public ModelAndView addRestrictDayJsp(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "restrictUUID", required = false) String restrictUUID) {
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		Long roleHierarchyId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "roleHierarchyId");

		List<Roles> rolesList = rolesService.getUserRolesListByModuleAndLoginUserRoleHierarchy(org,
				orgLocations.getId(), roleHierarchyId);
		model.addObject("rolesList", rolesList);
		List<Roles> existedRolesList = new ArrayList<Roles>();
		if (!"".equalsIgnoreCase(restrictUUID) && restrictUUID != null) {
			RestrictDay restrictDay = restrictDayService.getRestrictDayByUUID(restrictUUID);
			model.addObject("restrictDay", restrictDay);

			Set<LeaveRole> leaveRoles = restrictDay.getRestrictDayRolesList();
			leaveRoles.forEach(role -> {
				try {
					Roles existedRole;
					existedRole = rolesService.getRolesById(role.getRoleId().getId());
					existedRolesList.add(existedRole);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
		model.addObject("existedRoles", existedRolesList);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("leavemanagement/addRestrictDay");
		return model;
	}

	/**
	 * returns add RestrictDay jsp
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = "/viewRestrictDay")
	public ModelAndView viewRestrictDayJsp(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("leavemanagement/viewRestrictDay");
		return model;
	}

	/**
	 * returns add RestrictDay jsp
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception
	 */
	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = "/addOptions")
	public ModelAndView addOptionsJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		Long roleHierarchyId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "roleHierarchyId");
		List<Department> departmentList = departmentService.getDepartmentByOrgLocationId(orgLocations.getId());
		List<Roles> rolesList = rolesService.getUserRolesListByModuleAndLoginUserRoleHierarchy(org,
				orgLocations.getId(), roleHierarchyId);

		List<Long> optionRoleIds = optionsService.getOptionRoleIds(orgLocations.getId());
		List<Roles> rolesList1 = new ArrayList<>();
		for (Roles role : rolesList) {
			if (!optionRoleIds.contains(role.getId())) {
				rolesList1.add(role);
			}
		}
		List<String> calendarList = new ArrayList<String>(Arrays.asList(Constants.MONTHS));
		List<String> weekends = new ArrayList<String>(Arrays.asList(Constants.WEEKENDS));
		model.addObject("calendarList", calendarList);
		model.addObject("weekends", weekends);
		model.addObject("departmentList", departmentList);
		model.addObject("rolesList", rolesList1);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("leavemanagement/addOptions");
		return model;
	}

	/**
	 * returns add RestrictDay jsp
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = "/viewOptions")
	public ModelAndView viewOptionsJsp(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("leavemanagement/viewOptions");
		return model;
	}

	@RequestMapping(value = {"/storeRestrictedDay", "/rest/storeRestrictedDay"}, method = RequestMethod.POST)
	public @ResponseBody Response<String> storeRestrictedDay(RestrictDayDTO restrictDayDTO, HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
	try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if (user != null && Validator.validate(restrictDayDTO)) { 
			RestrictDay restrictDay= restrictDayService.storeRestrictedDay(restrictDayDTO);
			if (restrictDay != null) {
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.FAILED.name());
				response.setResponseMessage(Result.FAILED.getValue());
			}
		} else if (user == null) {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		} else {
			response.setResponse(Result.INVALID_DATA.name());
			response.setResponseMessage(Result.INVALID_DATA.getValue());
		}
	} catch (Exception e) {
		response.setResponse(Result.AWKWARD.name());
		response.setResponseMessage(Result.AWKWARD.getValue());
		logger.error(ExceptionUtils.getFullStackTrace(e));
	}
	return response;
		
	}

	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = {"/getRestrictDayList", "/rest/getRestrictDayList"}, method = RequestMethod.GET)
	public ResponseList<RestrictDayDTO> getRestrictDayList(@RequestParam(value = "orgLocationId", required = true) Long orgLocationId, HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = orgLocationService.getOrgLocations(Long.valueOf(orgLocationId));
		ResponseList<RestrictDayDTO> responseList = new ResponseList<>();
		try {
	
		List<RestrictDayDTO> restrictDayDTOList = new ArrayList<>();
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		if (loginOrgLocation != null) {
		List<RestrictDay> restrictDayList = restrictDayService.getRestrictDayListByOrgLocationId(orgLocationId);
		for(RestrictDay restrictDay : restrictDayList){
			RestrictDayDTO restrictDayDTO = new RestrictDayDTO();

			restrictDayDTO.setRestrictName(restrictDay.getRestrictName());
			restrictDayDTO.setStartDate(
					commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), restrictDay.getStartDate()));
			restrictDayDTO.setEndDate(
					commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), restrictDay.getEndDate()));
			restrictDayDTO.setReason(restrictDay.getReason());
			restrictDayDTO.setIsApllicable(restrictDay.getApplicableToAll());
			restrictDayDTO.setStatus(restrictDay.getStatus());
			restrictDayDTO.setRestrictDayId(restrictDay.getId());
			restrictDayDTO.setRestrictUUID(restrictDay.getRestrictUUID());
			restrictDayDTOList.add(restrictDayDTO);
		}
		responseList.setData(restrictDayDTOList);
		responseList.setResponse(Result.SUCCESS.name());
		responseList.setResponseMessage(Result.SUCCESS.getValue());
		} else {
			responseList.setResponse(Result.SESSION_TIMEDOUT.name());
			responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		
	} catch (Exception e) {
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return responseList;
	}

	/**
	 * Adding shift
	 * 
	 * @param shiftDTO
	 * @return String
	 * @throws Exception
	 */
	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = { "/saveShift", "/rest/saveShift" }, method = RequestMethod.POST)
	public  @ResponseBody Response<String> saveShift(ShiftDTO shiftDTO, HttpServletRequest request) throws Exception {
		String isInserted = "failure";
		Response<String> response = new Response<>();
		try {
			
	 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	if (user != null && Validator.validate(shiftDTO)) {
		Shift shift = shiftService.saveShift(shiftDTO);
		if (shift != null) {
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
		}else {
			response.setResponse(Result.FAILED.name());
			response.setResponseMessage(Result.FAILED.getValue());
		}
	 } else if (user == null) {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		} else {
			response.setResponse(Result.INVALID_DATA.name());
			response.setResponseMessage(Result.INVALID_DATA.getValue());
		}
	 }catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
			
	}

	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = {"/getShiftList", "/rest/getShiftList"}, method = RequestMethod.GET)
	public ResponseList<ShiftDTO> getShiftList(@RequestParam (value = "orgLocationId", required = true )Long orgLocationId, HttpServletRequest request) throws Exception{
		ResponseList<ShiftDTO> responseList = new ResponseList<>();
		try
		{
		
	    List<ShiftDTO> shiftDTOList = new ArrayList<>();
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		if (loginOrgLocation != null) {
			List<Shift> shiftList = shiftService.getShiftList(orgLocationId);
	      for(Shift shift : shiftList){
		     ShiftDTO shiftDTO = new ShiftDTO();
		        shiftDTO.setId(shift.getId());
				shiftDTO.setShiftName(shift.getShiftName());
				shiftDTO.setTimeIn(shift.getTimeIn());
				shiftDTO.setTimeOut(shift.getTimeOut());
				shiftDTO.setShiftDescription(shift.getShiftDescription());
				shiftDTO.setStatus(shift.getStatus());
				shiftDTO.setShiftUUID(shift.getShiftUUID());
				if(shift.getDefaultStatus() != null) {
					shiftDTO.setDefaultStatus(shift.getDefaultStatus());
				}else {
					shiftDTO.setDefaultStatus("N");
				}
				StringBuilder sb = new StringBuilder();
				if(shift.getCreatedBy() != null) {
					User user = userService.getUserByUserCode(shift.getCreatedBy());
					if(StringUtils.isNotBlank(user.getLastName())) {
						sb.append(user.getFirstName()).append("").append(user.getLastName());
					}else {
						sb.append(user.getFirstName());
					}
					shiftDTO.setUpdatedBy(sb.toString());
					shiftDTO.setUserId(sb.toString());
				}
				shiftDTOList.add(shiftDTO);
	
	            }
				responseList.setData(shiftDTOList);
				responseList.setResponse(Result.SUCCESS.name());
				responseList.setResponseMessage(Result.SUCCESS.getValue());
		}else {
			responseList.setResponse(Result.SESSION_TIMEDOUT.name());
			responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
	
      } catch (Exception e) {
	responseList.setResponse(Result.AWKWARD.name());
	responseList.setResponseMessage(Result.AWKWARD.getValue());
	logger.error(ExceptionUtils.getFullStackTrace(e));
      }
				return responseList;
	         }
	
	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = "/editleaveshift")
	public ModelAndView editShift(@RequestParam (value = "shiftUUID", required = false )String shiftUUID,HttpServletRequest request,HttpServletResponse response) throws Exception{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		Timezone timezone = timeZoneService.getTimezone(Long.parseLong(orgLocations.getTimeZoneId()));
		ModelAndView model = new ModelAndView();
		
	    Shift shift = shiftService.getShiftById(shiftUUID);
	    model.addObject("timezone", timezone.getTimeZoneId());
		model.addObject("shift", shift);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("leavemanagement/addShift");
		return model;
	}

	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = "/deleteShift")
	public boolean deleteShift(@RequestParam(value = "shiftUUID", required = false) String shiftUUID,
			@RequestParam(value = "orgLocationId", required = false) String orgLocationId) throws Exception {
		boolean isDeleted = false;
		Shift deleteShift = shiftService.getShiftById(shiftUUID);
		if (deleteShift != null) {
			deleteShift.setStatus(Constants.MEMBER_IN_ACTIVE);
			shiftDao.deleteShift(deleteShift);
			isDeleted = true;
			List<AssetAssign> assetAssignLis = assetService.getAssignAssetListByShiftId(deleteShift.getId());
			for(AssetAssign aa : assetAssignLis) {
				aa.setStatus(Constants.RECORD_INACTIVE);
				assetService.saveAssetAssign(aa);
			}
		}
		return isDeleted;
	}

	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = "/saveHoliday", method = RequestMethod.POST)
	public @ResponseBody Response<String> saveHoliday(HolidayDTO holidayDTO, HttpServletRequest request)
			throws Exception {
		Response<String> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null && Validator.validate(holidayDTO)) {
				OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
						"orgLocations");

				if (orgLocations.getId() != null)
					holidayDTO.setOrgLocationId(orgLocations.getId().toString());
				Holiday savedHoliday = null;
				if (holidayDTO != null)
					savedHoliday = holidayService.storeHoliday(holidayDTO);

				/*List<Projects> projectsList = projectsService.getAllProjectByOrgLocationsId(orgLocations.getId());
				if (projectsList != null && projectsList.size() > 0) {
					for (Projects projects : projectsList) {
						ProjectTasks tasks = new ProjectTasks();
						LookUpData taskType = lookUpDataService.getTasksTypeByUniqueKey(Constants.GENERAL);
						if(projects.getProjectShortCode() != null) {
							tasks.setGeneratedId(commonUtil.getProjectTaskGenaratedId(projects));
						}
						tasks.setProjectTasksUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
						tasks.setTaskName(holidayDTO.getHolidayName());
						tasks.setDescription(holidayDTO.getHolidayName());
						tasks.setBillableType("Billable");
						tasks.setProjectId(projects);
						tasks.setTaskType(taskType);
						tasks.setHoliday(savedHoliday);
						LookUpData taskStatus = lookUpDataService.getTasksById(Constants.TaskStatusID);
						tasks.setTaskStatus(taskStatus);
						tasks.setStatus(String.valueOf(Constants.CANDIDATE_ACTIVE));
						tasks.setCreatedBy(user.getUserCode());
						tasks.setCreatedDate(new Date());
						tasks.setUpdatedBy(user.getUserCode());
						tasks.setUpdatedDate(new Date());
						projectTasksDao.addTasks(tasks);
					}

				}

				}*/

				//timesheetRestController.linkHolidaysWithTimeSheet(user, orgLocations, savedHoliday);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else if(user==null){
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}else{
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = { "/getHolidaysList", "/rest/getHolidaysList" }, method = RequestMethod.GET)
	public @ResponseBody ResponseList<HolidayDTO> getHolidaysList(
			@RequestParam(value = "orgLocationId", required = false) Long orgLocationId,
			@RequestParam(value = "reportYear", required = false) String reportYear,
			@RequestParam(value = "shiftIds", required = false) String shiftIds, 
			@RequestParam(value = "fromType", required = false) String fromType, HttpServletRequest request)
			throws Exception {
		ResponseList<HolidayDTO> responseList = new ResponseList<>();
		try {
			List<HolidayDTO> holiodaysDTOList = new ArrayList<>();
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
					"orgLocations");
			
			Long userRoleId = (Long) SourceLeadSessionManager.getSessionAttribute(request,
					"userRoleId");
			UserRole userRole = userRoleService.getUserRoleById(userRoleId);
			
			if(Constants.Dashboard.equalsIgnoreCase(fromType)) {
				if(userRole.getShift() != null) {
					shiftIds = String.valueOf(userRole.getShift().getId());	
				}
				
			}
			
			if (loginOrgLocation != null) {

				
				if (StringUtils.isNotBlank(reportYear)) {
					holiodaysDTOList = holidayService.getAllHolidaysListBasedOnYear(orgLocationId,Long.parseLong(reportYear),shiftIds);
				} else {
					int currentYear = Calendar.getInstance().get(Calendar.YEAR);
					if(StringUtils.isNotBlank(reportYear) ) {
						holiodaysDTOList = holidayService.getAllHolidaysListByOrgloationIdAndShiftId(orgLocationId,shiftIds);
					}else {
						reportYear = String.valueOf(currentYear);
						holiodaysDTOList = holidayService.getAllHolidaysListBasedOnYear(orgLocationId, Long.parseLong(reportYear),shiftIds);
					}
				}
				
				responseList.setData(holiodaysDTOList);
				responseList.setResponse(Result.SUCCESS.name());
				responseList.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				responseList.setResponse(Result.SESSION_TIMEDOUT.name());
				responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}

		} catch (Exception e) {
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}

		return responseList;
	}

	/**
	 * saving new leave type
	 * 
	 * @throws Exception
	 * @throws Exception
	 * 
	 */
	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = "/saveNewLeaveType", method = RequestMethod.POST)
	public @ResponseBody Response<String> saveNewLeaveType(LeaveTypeDTO leaveTypeDTO, HttpServletRequest request)
			throws Exception {
 		Response<String> response = new Response<>();
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request,"org");
		orgLocation.setOrg(org);
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");

			if (user != null && Validator.validate(leaveTypeDTO)) {
				LeaveType leaveType = leaveTypeService.addLeaveType(leaveTypeDTO);
				if (leaveType != null) {
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
						List<User> userList = userService.getUsersListByOrglocationId(orgLocation.getId());
						List<User> updatedUserList = new ArrayList<>();
						List<User> insertUserList = new ArrayList<>();
						for(User usr : userList) {
							AssociateBalanceLeave associa = associateBalanceLeaveDao.getTotalLeavesByUserIdAndLeaveTypeId(leaveType.getId(), usr.getId(), orgLocation.getId(),orgLocation.getOrg().getId());
						    if(associa != null) {
						    	updatedUserList.add(usr);
						    }else {
						    	insertUserList.add(usr);
						    }
						}
						if(!insertUserList.isEmpty() && insertUserList.size() >0) {
							int result = associateBalanceLeaveService.addLeaveTypeToAssociate(insertUserList,orgLocation,leaveType,user,0);
						}
						if(!updatedUserList.isEmpty() && updatedUserList.size() >0) {
							float previousNoOfDays = 0;
							float currentNofDays = 0;
							
							float previousFresherNoOfDays = 0;
							float currentFresherNofDays = 0;
							
							float previousExpNoOfDays = 0;
							float currentExpNofDays = 0;
							
							if(!Constants.ANNUALLY.equalsIgnoreCase(leaveType.getPeriod()) && !Constants.NO.equalsIgnoreCase(leaveType.getAccuralPolicy())) {
								if("All Employees".equalsIgnoreCase(leaveType.getLeaveEffective())) {
									currentNofDays = leaveType.getNoOfDaysPerPeriod();
									previousNoOfDays = leaveTypeDTO.getPreviousNoOfDaysPerPeriod();
								}else{
									 previousFresherNoOfDays = leaveTypeDTO.getPreviousFresherPeriod();
									 currentFresherNofDays = leaveType.getNewJoinPerPeriod();
									
									 previousExpNoOfDays = leaveTypeDTO.getPreviousExpPeriodDays();
									 currentExpNofDays = leaveType.getExperiencedPerPeriod();
								}
							}else {
								if("All Employees".equalsIgnoreCase(leaveType.getLeaveEffective())) {
									currentNofDays   = leaveType.getNoOfDays();
									previousNoOfDays = Float.parseFloat(leaveTypeDTO.getPreviousNoOfdays());
								}else{
									 previousFresherNoOfDays = leaveTypeDTO.getPreviousNewJoinee();
									 currentFresherNofDays = leaveType.getNewJoinee();
									
									 previousExpNoOfDays = leaveTypeDTO.getPreviousExpApplicableDays();
									 currentExpNofDays = leaveType.getExperiencedApplicableDays();
								}
							}
							
							float diffLeaves = 0;
							float diffFresherLeaves = 0;
							float diffExpLeaves = 0;
							String actionType = "";
							String actionFresherType = "";
							String actionExpType = "";
							if(previousNoOfDays < currentNofDays) {
								diffLeaves = currentNofDays - previousNoOfDays;
								actionType = "ADD";
							}if(previousNoOfDays > currentNofDays) {
								 diffLeaves = previousNoOfDays - currentNofDays;
								 actionType = "SUB";
							}if(previousNoOfDays == currentNofDays) {
								 actionType = "NONE";
							}
							//Fresher calculation
							if(previousFresherNoOfDays < currentFresherNofDays) {
								diffFresherLeaves = currentFresherNofDays - previousFresherNoOfDays;
								actionFresherType = "ADD";
							}if(previousFresherNoOfDays > currentFresherNofDays) {
								diffFresherLeaves = currentFresherNofDays - previousFresherNoOfDays;
								 actionFresherType = "SUB";
							}if(previousFresherNoOfDays == currentFresherNofDays) {
								actionFresherType = "NONE";
							}
							// Experience Calculation 
							if(previousExpNoOfDays < currentExpNofDays) {
								diffExpLeaves = currentExpNofDays - previousExpNoOfDays;
								actionExpType = "ADD";
							}if(previousExpNoOfDays > currentExpNofDays) {
								diffExpLeaves = currentExpNofDays - previousExpNoOfDays;
								 actionExpType = "SUB";
							}if(previousExpNoOfDays == currentExpNofDays) {
								actionExpType = "NONE";
							}
							leaveType.setDiffLeaves(diffLeaves);
							leaveType.setActionType(actionType);
							leaveType.setActionFresherType(actionFresherType);
							leaveType.setActionExpType(actionExpType);
							leaveType.setDiffFresherLeaves(diffFresherLeaves);
							leaveType.setDiffExpLeaves(diffExpLeaves);
//							int result = associateBalanceLeaveService.addOrSubAssociateBalanceLeaveType(updatedUserList, orgLocation.getId(), leaveType, Integer.parseInt(leaveType.getStatus()),diffLeaves,actionType,Constants.UPDATE);
							associateBalanceLeaveService.updateLeaveTypeAssociateBalances(updatedUserList, leaveType, leaveTypeDTO);
						}	
//					}
				}
			} else if (user == null) {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			} else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}

		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * @author Pavan Jampana
	 * 
	 * @param leaveTypeDTO
	 * @param request
	 * @return leave type list
	 * @throws Exception
	 */
	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = { "/getLeaveTypesByLocationid",
			"/rest/getLeaveTypesByLocationid" }, method = RequestMethod.GET)
	public @ResponseBody ResponseList<LeaveType> getLeaveTypesByLocationid(LeaveTypeDTO leaveTypeDTO,
			HttpServletRequest request) throws Exception {

				ResponseList<LeaveType> responseList = new ResponseList<>();
		try {
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
					"orgLocations");
			if (loginOrgLocation != null) {
				List<LeaveType> leaveTypeList = leaveTypeService
						.getLeaveTypeListByLocationId(leaveTypeDTO.getOrgLocationId());
				responseList.setData(leaveTypeList);
				responseList.setResponse(Result.SUCCESS.name());
				responseList.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				responseList.setResponse(Result.SESSION_TIMEDOUT.name());
				responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}

		} catch (Exception e) {
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}

		return responseList;
	}

	/**
	 * saving leave optipons
	 * 
	 * @param optionsDTO
	 * @param request
	 * @return boolean
	 * @throws Exception
	 */
	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = {"/saveLeaveOption","/rest/saveLeaveOption"}, method = RequestMethod.POST)
	
	public @ResponseBody Response<String> saveLeaveOption(OptionsDTO optionsDTO, HttpServletRequest request) throws Exception {
 
		boolean isInserted = false;
		Response<String> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null && Validator.validate(optionsDTO)) {
		Option options = optionsService.saveLeaveOptions(optionsDTO);
		if (options != null) {
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
		} else {
			response.setResponse(Result.FAILED.name());
			response.setResponseMessage(Result.FAILED.getValue());
		}
	} else if (user == null) {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			} else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		
		return response;
	}

	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = { "/viewLeaveOption", "/rest/viewLeaveOption" }, method = RequestMethod.GET)
	@ResponseBody
	public  ResponseList<OptionsDTO> getDeductionsDetailsbyId(@RequestParam(value = "orgLocationId", required = false) Long orgLocationId, HttpServletRequest request) throws Exception
	{
		ResponseList<OptionsDTO> responseList = new ResponseList<>();
		try {
			
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (loginOrgLocation != null) {
			List<OptionsDTO> optionsDTOList = new ArrayList<>();
			 optionsDTOList = optionsService.getOptionsListByLocationId(orgLocationId);
			 if(optionsDTOList !=null) {
				 responseList.setData(optionsDTOList);
				 responseList.setResponse(Result.SUCCESS.name());
				 responseList.setResponseMessage(Result.SUCCESS.getValue());
			 }
			} else {
				responseList.setResponse(Result.SESSION_TIMEDOUT.name());
				responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		
		return responseList;
	}

	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = { "/deleteLeaveOption", "/rest/deleteLeaveOption" }, method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteLeaveOption(@RequestParam(value = "optionUUID", required = false) String optionUUID,
			@RequestParam(value = "reason", required = false) String reason, HttpServletRequest request)
			throws Exception {
		boolean isDeleted = false;
		Option options = optionsService.getOptionsById(optionUUID);
		if (options != null) {
			options.setStatus("0");
			options.setDeleteReason(reason);
			optionsDao.saveLeaveOption(options);
			isDeleted = true;
		}
		return isDeleted;
	}

	/**
	 * returns add RestrictDay jsp
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception
	 */
	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = "/editOption")
	public ModelAndView editOption(@RequestParam(value = "optionUUID", required = false) String optionUUID,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		Long roleHierarchyId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "roleHierarchyId");
		List<Roles> rolesList = rolesService.getUserRolesListByModuleAndLoginUserRoleHierarchy(org,
				orgLocations.getId(), roleHierarchyId);
		List<Department> departmentList = departmentService.getDepartmentByOrgLocationId(orgLocations.getId());
		Option options = optionsService.getOptionsById(optionUUID);
		if (options.getDepartmentId() != null) {
			Long departmentId = options.getDepartmentId().getId();
			model.addObject("departmentId", departmentId);
		}
		if (options.getWeekends() != null) {
			String weekends = options.getWeekends();
			model.addObject("weekends", weekends);
		}
		Set<LeaveRole> leaveRoles = options.getLeaverolesSet();
		List<Roles> existedRolesList = new ArrayList<Roles>();
		leaveRoles.forEach(role -> {
			try {
				Roles existedRole;
				existedRole = rolesService.getRolesById(role.getRoleId().getId());
				existedRolesList.add(existedRole);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		List<String> calendarList = new ArrayList<String>(Arrays.asList(Constants.MONTHS));
		List<String> weekends = new ArrayList<String>(Arrays.asList(Constants.WEEKENDS));
		model.addObject("calendarList", calendarList);
		model.addObject("weekends", weekends);

		if (options.getWeekends() != null) {
			model.addObject("existWeekends", options.getWeekends());
		}
		if (options.getCalendarMonth() != null) {
			model.addObject("existCalendarList", options.getCalendarMonth());
		}
		model.addObject("existedRoles", existedRolesList);
		model.addObject("rolesList", rolesList);
		model.addObject("departmentList", departmentList);
		model.addObject("options", options);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("optionId", options.getId());
		model.setViewName("leavemanagement/addOptions");
		return model;
	}

	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = { "/deleteRestrictedDay" })
	@ResponseBody
	public boolean deleteRestrictedDay(@RequestParam(value = "restrictUUID", required = false) String restrictUUID,
			@RequestParam(value = "reason", required = false) String reason) throws Exception {
		boolean isDeleted = false;
		RestrictDay restrictDay = restrictDayService.getRestrictDayByUUID(restrictUUID);
		if (restrictDay != null) {
			restrictDay.setStatus("0");
			restrictDay.setDeleteReason(reason);
			restrictDayDao.storeRestrictedDay(restrictDay);

			List<HolidayAndRestrictday> list = leaveService.getHolidayOrRestrictDaysListById(restrictDay.getId(),
					"RestrictDay");
			for (HolidayAndRestrictday day : list) {
				day.setStatus("0");
				leaveDao.storeHolidayAndRestrictDays(day);
			}
			isDeleted = true;
		}
		return isDeleted;
	}

	/**
	 * saving leave optipons
	 * 
	 * @param optionsDTO
	 * @param request
	 * @return boolean
	 * @throws Exception
	 */
	@PreAuthorize("hasAnyRole('ROLE_APPLY_LEAVE')")
	@RequestMapping(value = { "/saveApplyLeave"}, method = RequestMethod.POST)
	public @ResponseBody Response<String> applyLeave(LeaveDTO leaveDTO, HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			OrgLocations orgLocations = orgLocationService.getOrgLocations(Long.parseLong(leaveDTO.getOrgLocationId()));
			Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
			orgLocations.setOrg(org);
			boolean isSuperUser = false;
			if(StringUtils.isNotBlank(leaveDTO.getUser())){
				isSuperUser = orgLocationService.isLoginUserIsSuperUser(Long.parseLong(leaveDTO.getUser()),orgLocations.getId());
			}else{
				isSuperUser = orgLocationService.isLoginUserIsSuperUser(Long.parseLong(leaveDTO.getUserId()),orgLocations.getId());
			}
			User user = userService.getUserById(Long.parseLong(leaveDTO.getUserId()));
			if (user != null && Validator.validate(leaveDTO)) {
				leaveDTO.setStaticPath(staticPath);
				leaveDTO.setIsSuperUser(isSuperUser);
				Leave leave = leaveService.saveLeave(leaveDTO, orgLocations, user);
				if (leave != null) {
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
//					boolean  isApproved = leaveService.reduceLeaveCount(orgLocations,leave.getUser(),leave.getLeaveUUID(),leave.getLeaveType().getId(),leave.getStatus(),leave.getNumberOfDays());
					
//					leaveService.updateBalanceLeaves(leave,leave.getUser(),orgLocations);
					
				} else {
					response.setResponse(Result.FAILED.name());
					response.setResponseMessage(Result.FAILED.getValue());
				}
			} else if (user == null) {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			} else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}
	
	@RequestMapping(value = { "/rest/saveApplyLeave" }, method = RequestMethod.POST)
	public @ResponseBody Response<String> restApplyLeave(@RequestBody LeaveDTO leaveDTO, HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			OrgLocations orgLocations = orgLocationService.getOrgLocations(Long.parseLong(leaveDTO.getOrgLocationId()));
			Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
			orgLocations.setOrg(org);
			boolean isSuperUser = false;
			if(StringUtils.isNotBlank(leaveDTO.getUser())){
				isSuperUser = orgLocationService.isLoginUserIsSuperUser(Long.parseLong(leaveDTO.getUser()),orgLocations.getId());
			}else{
				isSuperUser = orgLocationService.isLoginUserIsSuperUser(Long.parseLong(leaveDTO.getUserId()),orgLocations.getId());
			}
			User user = userService.getUserById(Long.parseLong(leaveDTO.getUserId()));
			if (user != null && Validator.validate(leaveDTO)) {
				leaveDTO.setStaticPath(staticPath);
				leaveDTO.setIsSuperUser(isSuperUser);
				Leave leave = leaveService.saveLeave(leaveDTO, orgLocations, user);
				if (leave != null) {
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
//					boolean  isApproved = leaveService.reduceLeaveCount(orgLocations,leave.getUser(),leave.getLeaveUUID(),leave.getLeaveType().getId(),leave.getStatus(),leave.getNumberOfDays());
					
//					leaveService.updateBalanceLeaves(leave,leave.getUser(),orgLocations);
					
				} else {
					response.setResponse(Result.FAILED.name());
					response.setResponseMessage(Result.FAILED.getValue());
				}
			} else if (user == null) {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			} else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = "/editLeaveType")
	public ModelAndView editLeaveType(LeaveTypeDTO leaveTypeDTO, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		Long roleHierarchyId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "roleHierarchyId");
		List<Roles> rolesList = rolesService.getUserRolesListByModuleAndLoginUserRoleHierarchy(org,
				orgLocations.getId(), roleHierarchyId);
		List<Department> departmentList = departmentService.getDepartmentByOrgLocationId(orgLocations.getId());
		LeaveType leaveType = leaveTypeService.getLeaveTypeByUUID(leaveTypeDTO.getLeaveTypeUUID());
		if (leaveType.getDepartmentId() != null) {
			Long departmentId = leaveType.getDepartmentId().getId();
			model.addObject("departmentId", departmentId);
		}
		List<AssociateGrades> gradelist =  associateGradesService.getAssociateGradesListByOrgLocationId(orgLocations.getId());
		model.addObject("gradelist", gradelist);
		List<AssociateGrades> existGrade = new ArrayList<>();
		for(AssociateGrades grade : gradelist){
			if("Grade".equalsIgnoreCase(leaveType.getAutoApproveType())){
				String[] gradeArr = leaveType.getAutoApproveTypeList().split(",");
				List<Long> idList = new ArrayList<>();
				for(int i=0;i<gradeArr.length;i++)
					idList.add(Long.parseLong(gradeArr[i]));
//				List<Long> gradeIds = Stream.of(leaveType.getAutoApproveTypeList()).map(Long::parseLong).collect(Collectors.toList());
				if(idList.contains(grade.getId())){
					existGrade.add(grade);
				}
			}
		}
		model.addObject("existGrade", existGrade);
		
		List<Roles> autoApproveRole = new ArrayList<>();
		for(Roles role : rolesList){
			if("Role".equalsIgnoreCase(leaveType.getAutoApproveType())){
				String[] gradeArr = leaveType.getAutoApproveTypeList().split(",");
				List<Long> idList = new ArrayList<>();
				for(int i=0;i<gradeArr.length;i++)
					idList.add(Long.parseLong(gradeArr[i]));
//				List<Long> roleIds = Stream.of(leaveType.getAutoApproveTypeList()).map(Long::parseLong).collect(Collectors.toList());
				if(idList.contains(role.getId())){
					autoApproveRole.add(role);
				}
			}
		}
		model.addObject("autoApproveRole", autoApproveRole);
		
		Set<LeaveRole> leaveRoles = leaveType.getLeaveTypeRolesSet();
		List<Roles> existedRolesList = new ArrayList<Roles>();
		leaveRoles.forEach(role -> {
			try {
				Roles existedRole;
				existedRole = rolesService.getRolesById(role.getRoleId().getId());
				existedRolesList.add(existedRole);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		model.addObject("existedRoles", existedRolesList);
		model.addObject("rolesList", rolesList);
		model.addObject("departmentList", departmentList);
		model.addObject("employmentTypeList", Constants.EMPLOYMENT_TYPES);
		model.addObject("leaveType", leaveType);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("leavemanagement/addLeaveType");
		return model;
	}

	@RequestMapping(value = { "/getLeavePolicy", "/rest/getLeavePolicy" }, method = RequestMethod.GET)
	@ResponseBody
	public String getleavePolicy(@RequestParam(value = "leaveTypeId", required = false) Long leaveTypeId,
			HttpServletRequest request) throws Exception {
		String leavePolicy = null;
		LeaveType leaveType = leaveTypeService.getLeaveTypeById(leaveTypeId);
		if (leaveType != null && "Y".equalsIgnoreCase(leaveType.getVisibilityToAllUsers())
				&& "Y".equalsIgnoreCase(leaveType.getLeavePolicy())) {
			leavePolicy = leaveType.getPolicyDescription();
		}
		return leavePolicy;
	}

	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = { "/deleteLeaveType", "/rest/deleteLeaveType" }, method = RequestMethod.POST)
	@ResponseBody
	public Response<String> deleteLeaveType(LeaveTypeDTO leaveTypeDTO, HttpServletRequest request) throws Exception {
		
	        OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	        Response<String> response = new Response<>();
	        if(orgLocations != null){
	            LeaveType leaveType = leaveTypeService.getLeaveTypeByUUID(leaveTypeDTO.getLeaveTypeUUID());
	            List<ProjectTasks> projectTaskList = projectTasksService.getTasksListByLeaveType(leaveType.getId());
	            int status = 0;
	             if(leaveType !=null) {
	                 if("reactive".equalsIgnoreCase(leaveTypeDTO.getStatus())){
	                     leaveType.setStatus(Constants.RECORD_ACTIVE.toString());
	                     for(ProjectTasks pt:projectTaskList) {
	                         pt.setStatus(Constants.RECORD_ACTIVE.toString());
	                         projectTasksService.updateTasks(pt);
	                     }
	                     status = 1;
	                 }else{
	                 leaveType.setStatus(Constants.RECORD_INACTIVE.toString());
	                 leaveType.setDeleteReason(leaveTypeDTO.getDeleteReason());
	                 for(ProjectTasks pt:projectTaskList) {
	                     pt.setStatus(Constants.RECORD_INACTIVE.toString());
	                     projectTasksService.updateTasks(pt);
	                 }
	                  status = 0;
	                 }
	                 if(leaveTypeDTO.getLoginUserId() != null){
	                     User user = userService.getUserById(Long.parseLong(leaveTypeDTO.getLoginUserId()));
	                     leaveType.setUpdatedBy(user.getUserCode());
	                 }
	                 leaveType.setUpdatedDate(new Date());
	                 LeaveType saveleaveType = leaveTypeDao.addLeaveType(leaveType);
	                 if(saveleaveType != null) {
	                	 response.setResponse(Result.SUCCESS.name());
		                 response.setResponseMessage(Result.SUCCESS.getValue());
		                 List<User> userList = userService.getUsersListByOrglocationId(orgLocations.getId());
		                 int result = associateBalanceLeaveService.updateAssociateBalanceLeaveType(userList,orgLocations.getId(),saveleaveType,status);
	                 }
	                 
	                 
	            }
	        }else {
	            response.setResponse(Result.SESSION_TIMEDOUT.name());
	            response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
	        }
           return response;		
	}

	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = "/editHoliday")
	public ModelAndView editHoliday(@RequestParam(value = "holidayUUID", required = false) String holidayUUID,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		ModelAndView model = new ModelAndView();
		Holiday holidays = holidayService.getHolidaysByUUID(holidayUUID);
		HolidayDTO holidayDTO = new HolidayDTO();
		holidayDTO.setId(holidays.getId());
		holidayDTO.setHolidayName(holidays.getHolidayName());
		holidayDTO.setDescription(holidays.getDescription());
		holidayDTO.setFromDateStr(
				commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), holidays.getFromDate()));
		holidayDTO.setFromHalfDayCheckBox(holidays.getFromHalfDayCheckBox());
		holidayDTO.setFromHalfDayAmPm(holidays.getFromHalfDayAmPm());
		holidayDTO.setToDateStr(
				commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), holidays.getToDate()));
		holidayDTO.setToHalfDayCheckBox(holidays.getToHalfDayCheckBox());
		holidayDTO.setToHalfDayAmPm(holidays.getToHalfDayAmPm());
		holidayDTO.setHolidayStatus(holidays.getHolidayStatus());
		holidayDTO.setHolidayBillableType(holidays.getBillableType());
		
		List<Shift> shiftList = shiftService.getShiftList(orgLocations.getId());
		model.addObject("shiftList", shiftList);
		
		List<HolidayShift> holidayShiftList = holidayService.getHolidayShiftListByHolidayId(holidays.getId());
		model.addObject("holidayShiftList", holidayShiftList);
		
		model.addObject("holidayDTO", holidayDTO);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("leavemanagement/addHoliday");
		return model;
	}

	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = { "/deleteHoliday", "/rest/deleteHoliday" }, method = RequestMethod.GET)
	public @ResponseBody boolean deleteHoliday(
			@RequestParam(value = "holidayUUID", required = false) String holidayUUID, HttpServletRequest request)
			throws Exception {
		boolean isDeleted = false;
		Holiday deleteHoliday = holidayService.getHolidaysByUUID(holidayUUID);
		if (deleteHoliday != null) {
			//delted HolidayShift related query
			holidayDAO.deleteHolidayByHolidayId(deleteHoliday.getId());
			
			deleteHoliday.setStatus("0");
			holidayDAO.saveDeleteHoliday(deleteHoliday);

			List<HolidayAndRestrictday> list = leaveService.getHolidayOrRestrictDaysListById(deleteHoliday.getId(),
					"holiday");
			for (HolidayAndRestrictday day : list) {
				day.setStatus("0");
				leaveDao.storeHolidayAndRestrictDays(day);
			}

			isDeleted = true;
		}
		return isDeleted;
	}

	/**
	 * returns add Employee Requested Leaves jsp
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@PreAuthorize("hasAnyRole('ROLE_APPROVE_LEAVE')")
	@RequestMapping(value = "/employeeRequestedLeaves")
	public ModelAndView employeeRequestedLeavesJsp(
			@RequestParam(value = "notifyLink", required = false) String notifyLink,
			@RequestParam(value = "leaveUUID", required = false) String leaveUUID,
			@RequestParam(value = "orgLocationId", required = false) String orgLocationId, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		model.addObject("notifyLink", notifyLink);

		if (StringUtils.isNotBlank(leaveUUID)) {
			Leave leave = leaveService.getLeaveByLeaveUUId(leaveUUID);
			model.addObject("leaveUUID", leaveUUID);
			if ("APPROVED".equalsIgnoreCase(leave.getStatus())) {
				model.setViewName("leavemanagement/employeeApprovedLeaves");
			} else if ("REJECTED".equalsIgnoreCase(leave.getStatus())) {
				model.setViewName("leavemanagement/employeeRejectedLeaves");
			} else {
				model.setViewName("leavemanagement/employeeRequestedLeaves");
			}
		} else {
			model.setViewName("leavemanagement/employeeRequestedLeaves");
		}

		model.addObject("orgLocationId", orgLocationId);
		model.addObject("sourceleadUrl", sourceleadUrl);

		return model;
	}

	/**
	 * returns add Employee Approved Leaves jsp
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@PreAuthorize("hasAnyRole('ROLE_APPROVE_LEAVE')")
	@RequestMapping(value = "/employeeApprovedLeaves")
	public ModelAndView employeeApprovedLeavesJsp(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("leavemanagement/employeeApprovedLeaves");
		return model;
	}

	/**
	 * returns add Employee Rejected Leaves jsp
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@PreAuthorize("hasAnyRole('ROLE_APPROVE_LEAVE')")
	@RequestMapping(value = "/employeeRejectedLeaves")
	public ModelAndView employeeRejectedLeaves(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("leavemanagement/employeeRejectedLeaves");
		return model;
	}

	/**
	 * returns add Employee Cancel Leaves jsp
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@PreAuthorize("hasAnyRole('ROLE_APPROVE_LEAVE')")
	@RequestMapping(value = "/employeeCancelLeaves")
	public ModelAndView employeeCancelLeavesLeaves(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("leavemanagement/employeeCancelLeaves");
		return model;
	}

	// @PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/leaveTypeSummary", "/rest/leaveTypeSummary" }, method = RequestMethod.POST)
	@ResponseBody
	public Response<LeaveType> leaveTypeSummary(LeaveTypeDTO leaveTypeDTO, HttpServletRequest request)
			throws Exception {

		Response<LeaveType> response = new Response<>();
		try {

			Long orgLocationId = (Long)SourceLeadSessionManager.getSessionAttribute(request,"orgLocationId");
			OrgLocations loginOrgLocation = orgLocationService.getOrgLocationById(orgLocationId);
			if (loginOrgLocation != null) {
				LeaveType leaveType = new LeaveType();
				leaveType =  leaveTypeService.getLeaveTypeByUUID(leaveTypeDTO.getLeaveTypeUUID());
				if (leaveType != null) {
					if(StringUtils.isNotBlank(leaveTypeDTO.getLoginUserId())){
						UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(Long.parseLong(leaveTypeDTO.getLoginUserId()), loginOrgLocation.getId());
						if(StringUtils.isNotBlank(userRole.getUser().getGender())){
							if("All".equalsIgnoreCase(leaveType.getEffectiveGender()) || leaveType.getEffectiveGender() == null){
								leaveType.setUserGender("Allow");
							}else{
								if(userRole.getUser().getGender().equalsIgnoreCase(leaveType.getEffectiveGender())){
									leaveType.setUserGender("Allow");
								}else{
									leaveType.setUserGender("Not Allow");
								}
							}
						}
						
						if(Constants.YES.equalsIgnoreCase(leaveType.getAutoApprove())){
							if("Grade".equalsIgnoreCase(leaveType.getAutoApproveType())){
								if(userRole.getGradeId() != null){
									if(leaveType.getAutoApproveTypeListNames().contains(userRole.getGradeId().getGradeName())){
										leaveType.setAutoApprove(Constants.YES);
									}else{
										leaveType.setAutoApprove(Constants.NO);
									}
								}else{
									leaveType.setAutoApprove(Constants.NO);
								}
							}else if("Role".equalsIgnoreCase(leaveType.getAutoApproveType())){
								if(leaveType.getAutoApproveTypeListNames().contains(userRole.getRoles().getRoleName())){
									leaveType.setAutoApprove(Constants.YES);
								}else{
									leaveType.setAutoApprove(Constants.NO);
								}
							}
						}else {
							leaveType.setAutoApprove(Constants.NO);
						}
					}
					response.setObject(leaveType);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				}
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}

		return response;
					
		/*LeaveType leaveType = leaveTypeService.getLeaveTypeByUUID(leaveTypeDTO.getLeaveTypeUUID());
		return leaveType;*/
	}

	/**
	 * Get Employees those who are requested for the aprrove leave
	 * 
	 * @param orgLocationId
	 * @param userId
	 * @param request
	 * @return List<LeaveDTO>
	 * @throws Exception
	 */
	@PreAuthorize("hasAnyRole('ROLE_APPROVE_LEAVE')")
	@RequestMapping(value = { "/getEmployeeRequestedLeaves","/rest/getEmployeeRequestedLeaves" }, method = RequestMethod.POST)
	public @ResponseBody ResponseList<LeaveDTO> getEmployeeRequestedLeaves(LeaveDTO leaveDTO, HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocations = orgLocationService.getOrgLocations(Long.valueOf(leaveDTO.getOrgLocationId()));
		ResponseList<LeaveDTO> responseList = new ResponseList<>();
		try {
			String[] tableColumns1 = null;
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if(Constants.LEAVE_APPROVED.equalsIgnoreCase(leaveDTO.getLeaveStatus())) {
				String[] tableColumns = {"firstName",  "name","", "","approvedBy","updatedDate",""};
				tableColumns1 = tableColumns;
			}else if(Constants.LEAVE_PENDING.equalsIgnoreCase(leaveDTO.getLeaveStatus())) {
				String[] tableColumns = {"firstName",  "name","", "","","",""};
				tableColumns1 = tableColumns;
			}else if(Constants.LEAVE_REJECTED.equalsIgnoreCase(leaveDTO.getLeaveStatus())) {
				String[] tableColumns = {"firstName",  "name","", "","rejectedBy","",""};
				tableColumns1 = tableColumns;
			}else if(Constants.LEAVE_CANCELLED.equalsIgnoreCase(leaveDTO.getLeaveStatus())) {
				String[] tableColumns = {"firstName",  "name","", "","approvedBy","cancelledBy",""};
				tableColumns1 = tableColumns;
			}
			
		    int sortColumnIndex = 1;		 //by default sorting using updated date
			String sortColumnOrder = "asc";  //by default descending order
			if(leaveDTO.getOrder() != null && !leaveDTO.getOrder().isEmpty()) {
				sortColumnIndex = Integer.parseInt(leaveDTO.getOrder().get(0).get("column"));
				sortColumnOrder = leaveDTO.getOrder().get(0).get("dir");
			}

				leaveDTO.setSortColumn(tableColumns1[sortColumnIndex]);
				leaveDTO.setSortOrder(sortColumnOrder);

			
			
			String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
			leaveDTO.setLoginUserRole(loginUserRole);
			if (loginOrgLocation != null) {
				responseList = leaveService.getEmployeeRequestedLeavesBylocationId(leaveDTO, orgLocations);
				responseList.setResponse(Result.SUCCESS.name());
				responseList.setResponseMessage(Result.SUCCESS.getValue());
			}
			else {
				responseList.setResponse(Result.SESSION_TIMEDOUT.name());
				responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return responseList;
	}
	
	@RequestMapping(value = {"/getLeavesListByLocationIdAndStatus","/rest/getLeavesListByLocationIdAndStatus"}, method = RequestMethod.GET)
	public @ResponseBody ResponseList<LeaveDTO> getLeavesListByLocationIdAndStatus(@RequestParam(value = "orgLocationId", required = false) Long orgLocationId, @RequestParam(value = "status", required = false) String status,@RequestParam(value = "userId", required = false) Long userId, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		ResponseList<LeaveDTO> responseList= null;
		try {
			
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (loginOrgLocation != null) {
				responseList = leaveService.getLeavesListByLocationIdAndStatus(orgLocationId,status,userId);
				responseList.setResponse(Result.SUCCESS.name());
				responseList.setResponseMessage(Result.SUCCESS.getValue());
			}else {
				responseList.setResponse(Result.SESSION_TIMEDOUT.name());
				responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				}
			
		} catch (Exception e) {
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
				
		return responseList;

	}

	/**
	 * Approving the requested leave
	 * 
	 * @param orgLocationId
	 * @param leaveId
	 * @param leavTypeId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@PreAuthorize("hasAnyRole('ROLE_APPROVE_LEAVE')")
	@RequestMapping(value = { "/approveRequestedLeave", "/rest/approveRequestedLeave" }, method = RequestMethod.GET)
	@ResponseBody

	public Response<String> approveRequestedLeave(
			@RequestParam(value = "orgLocationId", required = false) Long orgLocationId,

			@RequestParam(value = "leaveUUID", required = false) String leaveUUID,
			@RequestParam(value = "leaveTypeId", required = false) Long leaveTypeId,
			@RequestParam(value = "approveStatus", required = false) String approveStatus,
			@RequestParam(value = "userId", required = false) Long userId,
			@RequestParam(value = "numberOfDays", required = false) int numberOfDays,
			@RequestParam(value = "reason", required = false) String reason, HttpServletRequest request)
			throws Exception {
		Response<String> response = new Response<>();
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		User user = userService.getUserById(userId);
		 boolean isApproved = false;
	 try {
		User user1 = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if (user1 != null){
		  isApproved = leaveService.approveRequestLeave(orgLocations,user,leaveUUID,leaveTypeId,approveStatus,numberOfDays,reason);
		if(isApproved == true){
		    response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
		}
		else{
			response.setResponse(Result.INVALID_DATA.name());
			response.setResponseMessage(Result.INVALID_DATA.getValue());
		}
		}  else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}	
	 	
	} catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
		return response;
	
	}

	/**
	 * Get Requested leaves Summary
	 * 
	 * @param orgLocationId
	 * @param leaveId
	 * @param leavTypeId
	 * @param request
	 * @return LeaveDTO
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getRequestedAproveSummary" }, method = RequestMethod.GET)
	@ResponseBody
	public Response<LeaveDTO> getRequestedAproveSummary(@RequestParam(value = "leaveUUID", required = false) String leaveUUID,
			@RequestParam(value = "orgLocationId", required = false) Long orgLocationId, HttpServletRequest request)
			throws Exception {
		
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		LeaveDTO leaveDto = new LeaveDTO();
		Response<LeaveDTO> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
			 Leave leave = leaveService.getLeaveByLeaveUUId(leaveUUID);
		     if (leave != null) {
			StringBuilder sb = new StringBuilder();
			StringBuilder durationSb = new StringBuilder();
			leaveDto.setId(leave.getId());
			if (leave.getUser() != null) {
				if (StringUtils.isNotBlank(leave.getUser().getLastName())) {
					sb.append(leave.getUser().getFirstName()).append(" ").append(leave.getUser().getLastName());
				} else {
					sb.append(leave.getUser().getFirstName());
				}
				leaveDto.setEmpName(sb.toString());
				leaveDto.setEmpEmail(leave.getUser().getEmail());
				Employees employees = employeesService.getEmployeeByUserIdAndOrgLocationsId(leave.getUser().getId(),orgLocations.getId());
				if (employees != null) {
					leaveDto.setEmpid(employees.getEmployeeId());
				}
				UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(leave.getUser().getId(),orgLocations.getId());
				leaveDto.setEmpType(userRole.getUserType());
			}
			durationSb.append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), leave.getFromDate()))
					.append(System.getProperty("line.separator")).append("to")
					.append(System.getProperty("line.separator"))
					.append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), leave.getToDate()));

			leaveDto.setDuration(durationSb.toString());
			leaveDto.setLeaveType(leave.getLeaveType().getName());
			leaveDto.setType(leave.getLeaveType().getLeaveType());
			leaveDto.setLeaveTypeId(leave.getLeaveType().getId());
			leaveDto.setStatus(leave.getStatus());
			leaveDto.setLeaveName(leave.getLeaveType().getName());
			try {
				int numberOfdays = commonUtil.getNumberOfDaysBetweenTwoDates(leave.getFromDate(), leave.getToDate());
				leaveDto.setNoOfDays(numberOfdays);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			leaveDto.setLeaveUUID(leave.getLeaveUUID());
			leaveDto.setFileUrl(leave.getFileUrl());
			leaveDto.setReason(leave.getReason());
			leaveDto.setCreatedDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), leave.getCreatedDate()));
			leaveDto.setFileName(leave.getFileName());
			leaveDto.setFileContentType(leave.getFileContentType());
			if (leave.getRejectedReason() != null) {
				leaveDto.setRejectedReason(leave.getRejectedReason());
			}
			if (leave.getDeleteReason() != null) {
				leaveDto.setDeleteReason(leave.getDeleteReason());
			}
			if (leave.getWithdrawReason() != null) {
				leaveDto.setWithdrawReason(leave.getWithdrawReason());
			}

			StringBuilder sbu = new StringBuilder();
			if (leave.getApprovedBy() != null) {
				User approvedUser = userService.getUserById(leave.getApprovedBy().getId());
				if (StringUtils.isNotBlank(approvedUser.getLastName())) {
					sbu.append(approvedUser.getFirstName()).append(" ").append(approvedUser.getLastName());
					leaveDto.setApprovedBy(sbu.toString());
				} else {
					sbu.append(approvedUser.getFirstName());
					leaveDto.setApprovedBy(sbu.toString());
				}
			}

			leaveDto.setUpdatedDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), leave.getUpdatedDate()));

			StringBuilder sbr = new StringBuilder();
			if (leave.getRejectedBy() != null) {
				User rejectedUser = userService.getUserById(leave.getRejectedBy().getId());
				if (StringUtils.isNotBlank(rejectedUser.getLastName())) {
					sbr.append(rejectedUser.getFirstName()).append(" ").append(rejectedUser.getLastName());
					leaveDto.setRejectedBy(sbr.toString());
				} else {
					sbr.append(rejectedUser.getFirstName());
					leaveDto.setRejectedBy(sbr.toString());
				}
			}
			StringBuilder sbc = new StringBuilder();
			if (leave.getCancelledBy() != null) {
				User rejectedUser = userService.getUserById(leave.getCancelledBy().getId());
				if (StringUtils.isNotBlank(rejectedUser.getLastName())) {
					sbc.append(rejectedUser.getFirstName()).append(" ").append(rejectedUser.getLastName());
					leaveDto.setCancelledBy(sbc.toString());
				} else {
					sbc.append(rejectedUser.getFirstName());
					leaveDto.setCancelledBy(sbc.toString());
				}
			}

			List<String> aphlist = new ArrayList<>();
			for (LeaveApproveHierarchy laph1 : leave.getApproverHierarchyList()) {
				StringBuilder sb1 = new StringBuilder();
				try {
					UserRole role = userRoleService.getUserRoleByUserIdAndLocationId(laph1.getApproverUserId().getId(),
							orgLocations.getId());
//					if (role != null) {
						if (StringUtils.isNotBlank(laph1.getApproverUserId().getLastName())) {
							aphlist.add(sb1.append(laph1.getApproverUserId().getFirstName()).append(" ")
									.append(laph1.getApproverUserId().getLastName()).toString());
						} else {
							aphlist.add(sb1.append(laph1.getApproverUserId().getFirstName()).toString());
						}
//					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			Employees emp = employeesService.getEmployeeByUserIdAndOrgLocationsId(leave.getUser().getId(),orgLocations.getId());
			
//			int totalLeavesperYear = leaveManagementUtil.getBalanceCountForLeaveType(leave.getLeaveType(), emp,orgLocations, leave.getUser());
			float totalLeavesperYear = associateBalanceLeaveService.getTotalLeavesCountByUserIdAndLeaveTypeId(leave.getLeaveType().getId(),leave.getUser().getId(),orgLocations.getId(),orgLocations.getOrg().getId());
			
			leaveDto.setApproverUsers(String.join(",", aphlist));
			Map<String, Float> nuberOfLeavestaken = leaveDao.getNumberOfLeavesTakenByUser(leave.getUser().getId(),
					leave.getLeaveType().getId());

			float leavesTaken = (float) (nuberOfLeavestaken.get("numberOfLeavsTaken") - (nuberOfLeavestaken.get("halfdayLeavesCount") * 0.5));
			leaveDto.setActualLeavesTaken(leavesTaken);

			float thisMonthLeaves = leaveDao.getThisMonthLeavesTakenByUser(leave.getUser().getId(),leave.getLeaveType(),orgLocations);
			leaveDto.setThisMonthLeavesTaken(thisMonthLeaves);

			leaveDto.setIsBillable(leave.getLeaveType().getLeaveType());
			float balLeaves = 0f;
//			 float balLeaves1 = totalLeavesperYear - (nuberOfLeavestaken.get("numberOfLeavsTaken") - nuberOfLeavestaken.get("numberOfExtraLeaves"));
			 
			 float tot = 0f;
			 float totalLeaveCount = leaveManagementUtil.getTotalAppliedLeaveCount(orgLocations, leave.getLeaveType(),leave.getUser().getId());
			 if(!(Constants.LEAVE_CANCELLED.equalsIgnoreCase(leave.getStatus()) || Constants.LEAVE_REJECTED.equalsIgnoreCase(leave.getStatus()))){
				 if ("Monthly".equalsIgnoreCase(leave.getLeaveType().getPeriod()) && Constants.NO.equalsIgnoreCase(leave.getLeaveType().getLeavesCarryforward())) {
						try {
							tot = leaveManagementUtil.getTotalBalanceLeavesForNonCarryforwardLeaveType(leave.getLeaveType(), totalLeavesperYear, null);
						} catch (Exception e) {
							e.printStackTrace();
						}
						balLeaves = totalLeavesperYear - totalLeaveCount;
					}else if(Constants.YES.equalsIgnoreCase(leave.getLeaveType().getLeavesCarryforward())){
						balLeaves = totalLeavesperYear - totalLeaveCount;
					}
			 }else{
				 if ("Monthly".equalsIgnoreCase(leave.getLeaveType().getPeriod()) && Constants.NO.equalsIgnoreCase(leave.getLeaveType().getLeavesCarryforward())) {
						try {
							tot = leaveManagementUtil.getTotalBalanceLeavesForNonCarryforwardLeaveType(leave.getLeaveType(), totalLeavesperYear, null);
						} catch (Exception e) {
							e.printStackTrace();
						}
						balLeaves = totalLeavesperYear - totalLeaveCount;
					}else if(Constants.YES.equalsIgnoreCase(leave.getLeaveType().getLeavesCarryforward())){
						balLeaves = totalLeavesperYear - totalLeaveCount;
					}
			 }
			
//			float balLeaves = totalLeavesperYear - leavesTaken;
			leaveDto.setTotalBalLeaves(balLeaves);
			response.setObject(leaveDto);
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
		} else {
			response.setResponse(Result.INVALID_DATA.name());
			response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
	} else {
		response.setResponse(Result.SESSION_TIMEDOUT.name());
		response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		
	}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}	
			
		return response;
	}

	@PreAuthorize("hasAnyRole('ROLE_CANCEL_LEAVE')")
	@RequestMapping(value = { "/storeCancelReason" })
	@ResponseBody
	public Response<String> storeCancelReason(@RequestParam(value = "leaveUUID", required = false) String leaveUUID,@RequestParam(value = "reason", required = false) String reason,@RequestParam(value = "userId", required = false) Long userId,  HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		boolean isCancelled = false;
		Response<String> response = new Response<>();
		LeaveManagementHistory leaveManagementHistory = new LeaveManagementHistory();
		
		User user = userService.getUserById(userId);
	  try {
			User user1 = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if (user1 != null){
			Leave leave = leaveService.getLeaveByLeaveUUId(leaveUUID);
		 if(leave !=null) {
			 isCancelled = true;
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
			if("Monthly".equalsIgnoreCase(leave.getLeaveType().getPeriod()) && Constants.NO.equalsIgnoreCase(leave.getLeaveType().getLeavesCarryforward())){
				 Calendar c = Calendar.getInstance();
				 int year = c.get(Calendar.YEAR);
				 int month = c.get(Calendar.MONTH);
				 int day = 1;
				 c.set(year, month, day);
				 int numOfDaysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
				 Date startDate = c.getTime();
				 java.sql.Date currentMonthStartDate = new java.sql.Date(startDate.getTime());
				 c.add(Calendar.DAY_OF_MONTH, numOfDaysInMonth - 1);
				 Date endDate = c.getTime();
				 java.sql.Date currentMonthEndDate = new java.sql.Date(endDate.getTime());
				 List<Leave> leaveList = leaveService.getLeavesListByTypeIdAndDuration(leave.getLeaveType().getId(), leave.getOrgLocations().getId(),leave.getUser().getId(),currentMonthStartDate,currentMonthEndDate);
				 float extraLeaves = leave.getLeaveType().getNoOfDaysPerPeriod();
				 Leave updatingLeave = leaveList.get(leaveList.size()-1);
				 
				 float cancelledLeave = leave.getNumberOfDays() - leave.getExtraLeavesTaken();
				 float updateExtraLeave = updatingLeave.getExtraLeavesTaken() - cancelledLeave;
				 if(updateExtraLeave > 0){
					 updatingLeave.setExtraLeavesTaken(updateExtraLeave);
				 }else{
					 updatingLeave.setExtraLeavesTaken(0);
				 }
				 leaveDao.saveLeave(updatingLeave);
			 }
			
			 leaveManagementHistory.setFromStage(leave.getStatus());
			 leave.setDeleteReason(reason);
			 leave.setCancelledBy(user);
			 leave.setStatus(Constants.LEAVE_CANCELLED);
			 leaveDao.saveLeave(leave);
		 }
		 else{
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		 	leaveManagementHistory.setLeaveid(leave);
			leaveManagementHistory.setType(Constants.LEAVE);
//			leaveManagementHistory.setStatus(leave.getStatus());
			leaveManagementHistory.setCreatedBy(user.getUserCode());
			leaveManagementHistory.setCreatedDate(new Date());
			leaveManagementHistory.setUpdatedBy(user.getUserCode());
			leaveManagementHistory.setUpdatedDate(new Date());
			leaveManagementHistory.setToStage(Constants.LEAVE_CANCELLED);
			leaveManagementHistoryDao.saveLeaveManagementHistory(leaveManagementHistory);
			
			
			// for URL redirections from mail
			Long redirectOrgLocationId = orgLocations.getId();
			String  redirectURL = "/cancelLeaves-params-notifyLink=notifyLink-and-leaveUUID="+leaveManagementHistory.getLeaveid().getLeaveUUID()+"-and-orgLocationId="+orgLocations.getId();
			leaveService.LeaveCancelEmailToUser(user,leaveManagementHistory,reason,redirectOrgLocationId,redirectURL);
			/*if(leave != null && leave.getLeaveType() != null && leave.getOrgLocations() != null) {
				timesheetRestController.linkLeaveTypeWithTimeSheet(user, leave.getOrgLocations(), leave.getLeaveType().getId(), leave, Constants.LEAVE_CANCELLED);
			}*/
		}  else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}	
	  } catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
	return response;
	}	
	
	@PreAuthorize("hasAnyRole('ROLE_APPROVE_LEAVE')")
	@RequestMapping(value = { "/getEmployeeLeavesList", "/rest/getEmployeeLeavesList" }, method = RequestMethod.GET)
	@ResponseBody
	public List<LeaveReportsDTO> getEmployeeLeavesList(
			@RequestParam(value = "orgLocationId", required = true) Long orgLocationId,
			@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "department", required = false) String department,
			@RequestParam(value = "leaveDateStr", required = false) String leaveDate) throws Exception {
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		List<LeaveReportsDTO> leavesList = leaveService.getEmployeeLeavesByApproverIdAndStatus(orgLocations, userId,
				Constants.APPROVED, department, leaveDate);
		return leavesList;
	}

	/**
	 * Get top leave takers by location
	 * 
	 * @param leaveDTO
	 * @param req
	 * @param res
	 * @return ResponseList<LeaveDTO>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/getTopLeavetakers", "/rest/getTopLeavetakers" }, method = RequestMethod.POST)
	public @ResponseBody ResponseList<LeaveDTO> getTopLeavetakers(LeaveDTO leaveDTO, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		OrgLocations orgLocation = orgLocationService.getOrgLocations(Long.parseLong(leaveDTO.getOrgLocationId()));
		boolean isSuperUser = (boolean) SourceLeadSessionManager.getSessionAttribute(req, "isSuperUser");
		ResponseList<LeaveDTO> leaveDtoResponse = new ResponseList<>();
		String[] tableColumns = { "empName", "noOfDays", "status" };
		int sortColumnIndex = 0; // by default sorting using updated date
		String sortColumnOrder = "desc"; // by default descending order
		if (leaveDTO.getOrder() != null && !leaveDTO.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(leaveDTO.getOrder().get(0).get("column"));
			sortColumnOrder = leaveDTO.getOrder().get(0).get("dir");
		}
		leaveDTO.setSortColumn(tableColumns[sortColumnIndex]);
		leaveDTO.setSortOrder(sortColumnOrder);

		String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(req, "loginUserRole");
		leaveDTO.setLoginUserRole(loginUserRole);
		leaveDTO.setIsSuperUser(isSuperUser);
		ResponseList<Leave> response = leaveService.getTopLeavetakers(orgLocation, leaveDTO);
		List<Leave> leaveList = response.getData();
		List<LeaveDTO> leaveDtoList = new ArrayList<>();
		HashMap<Long, LeaveDTO> reportMap = new HashMap<>();
		int totalLeaves = 0;
		for (Leave leave : leaveList) {
			LeaveDTO leaveDto = reportMap.get(leave.getUser().getId());
			if (leaveDto == null) {
				leaveDto = new LeaveDTO();
				totalLeaves = 0;
			} else {
				totalLeaves = leaveDto.getNoOfDays();
			}
			StringBuilder sb = new StringBuilder();
			if (StringUtils.isNotBlank(leave.getUser().getLastName())) {
				sb.append(leave.getUser().getFirstName()).append(" ").append(leave.getUser().getLastName());
			} else {
				sb.append(leave.getUser().getFirstName());
			}
			leaveDto.setEmpName(sb.toString());
			leaveDto.setStatus(leave.getStatus());
			totalLeaves = totalLeaves + leave.getNumberOfDays();
			leaveDto.setNoOfDays(totalLeaves);
			reportMap.put(leave.getUser().getId(), leaveDto);
		}

		for (Map.Entry<Long, LeaveDTO> entry : reportMap.entrySet()) {
			leaveDtoList.add(entry.getValue());
		}

		Collections.sort(leaveDtoList, new Comparator() {
			@Override
			public int compare(Object obj1, Object obj2) {
				LeaveDTO leaveDTO1 = (LeaveDTO) obj1;
				LeaveDTO leaveDTO2 = (LeaveDTO) obj2;
				if (leaveDTO2.getNoOfDays() < leaveDTO1.getNoOfDays())
					return -1;
				if (leaveDTO2.getNoOfDays() > leaveDTO1.getNoOfDays())
					return 1;
				return 0;
			}
		});

		leaveDtoResponse.setData(leaveDtoList);
		leaveDtoResponse.setRecordsFiltered(leaveDtoList.size());
		leaveDtoResponse.setRecordsTotal(leaveDtoList.size());
		leaveDtoResponse.setError(response.getError());
		return leaveDtoResponse;
	}

	/**
	 * Validate Leave
	 * 
	 * @param fromDate
	 * @param toDate
	 * @param orgLocationId
	 * @param userId
	 * @param leaveUUID
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = {"/validateRestrictDate", "/rest/validateRestrictDate" })
	@ResponseBody
	public ValidateDateDTO validateRestrictDate(@RequestParam(value = "fromDate", required = false) String fromDate, @RequestParam(value = "toDate", required = false) String toDate, @RequestParam(value = "orgLocationId", required = false) Long orgLocationId, @RequestParam(value = "userId", required = false) Long userId, @RequestParam(value = "leaveUUID", required = false) String leaveUUID,@RequestParam(value = "errorMessage", required = false) String errorMessage, HttpServletRequest request) throws Exception
	{
		String isTrue = null;
//		StringBuilder sb = null;
		ValidateDateDTO validateDateDTO = new ValidateDateDTO();
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		User user = null;
		if (userId == null) {
			user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			userId = user.getId();
		}
		UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(userId, orgLocations.getId());
		if (leaveUUID != null && !"".equalsIgnoreCase(leaveUUID)) {
			Leave leave = leaveService.getLeaveByLeaveUUId(leaveUUID);
			fromDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), leave.getFromDate());
			toDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), leave.getToDate());
		}
		StringBuilder sb = new StringBuilder();
		if(StringUtils.isNotBlank(errorMessage)){
			sb.append(errorMessage);
			validateDateDTO.setValidateType("MESSAGE");
		}
		if(StringUtils.isNotBlank(fromDate) && StringUtils.isNotBlank(toDate)){
			List<AppliedLeaveDateView> leaveList = leaveService.getLeaveByUserIdAndFromToDate(userId,orgLocations,fromDate,toDate);
			if(leaveList != null && !leaveList.isEmpty()) {
//				validateDateDTO = new ValidateDateDTO();
				validateDateDTO.setValidateType("SAMEDAY");	
				sb.append(" You cannot apply two leaves on same day.!");
				validateDateDTO.setErrorMessage(sb.toString());
//				sb = new StringBuilder();
//				sb.append("you cannot apply two leaves on same day.!");
				return validateDateDTO;
			}
		}
		
		if(StringUtils.isNotBlank(fromDate) && StringUtils.isNotBlank(toDate)){
			List<HolidayAndRestrictday> list = leaveService.getAllHolidayAndRestrictDaysInDuration(fromDate, toDate, orgLocations);
			List<Date> daysList = new ArrayList<>();
			Set<Long> roleIds = new HashSet<>();
			for (HolidayAndRestrictday day : list) {
				if (day.getRestrictDay() != null) {
					RestrictDay res = restrictDayService.getRestrictDayById(day.getRestrictDay().getId());
					if (!"Y".equalsIgnoreCase(res.getApplicableToAll())) {
						List<LeaveRole> leaveRoleList = leaveService.getLeaveRolesByRestrictDayId(res.getId());
						for (LeaveRole leaveRole : leaveRoleList) {
							roleIds.add(leaveRole.getRoleId().getId());
						}
						if (roleIds.contains(userRole.getRoles().getId())) {
							daysList.add(day.getDay());
						}
					}
					else {
						daysList.add(day.getDay());
					}
				}
				else {
					daysList.add(day.getDay());
				}
			}
			daysList = new ArrayList<Date>(new HashSet<Date>(daysList));
			int i = 0;
			Collections.sort(daysList);
			if (daysList.size() > 0) {
//				validateDateDTO = new ValidateDateDTO();
				sb.append(" You cannot apply for leave on restricted/holiday day ");
				for (Date d : daysList) {
					sb.append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), d));
					if (i != daysList.size() - 1) {
						sb.append(", ");
					}
					i++;
				}
				validateDateDTO.setValidateType("HOLIDAY");
//				return validateDateDTO;
			}
		}
		if(StringUtils.isNotBlank(sb.toString()))
			validateDateDTO.setErrorMessage(sb.toString());
		if(validateDateDTO.getValidateType() == null){
			validateDateDTO.setValidateType("N/A");
			validateDateDTO.setErrorMessage("N/A");
		}
		
		return validateDateDTO;
	}

	@RequestMapping(value = { "/totalCountsForEmployeeLeaves",
			"/rest/totalCountsForEmployeeLeaves" }, method = RequestMethod.GET)
	@ResponseBody
	public LeaveDTO totalCountsForEmployeeLeaves(
			@RequestParam(value = "orgLocationId", required = false) Long orgLocationId,
			@RequestParam(value = "userId", required = false) Long userId, HttpServletRequest request) throws Exception {
		String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		return leaveService.getAllEmployeesLeavesCounts(orgLocationId, userId, loginUserRole,org.getId());
	}

	@RequestMapping(value = { "/totalCountsForSelfLeaves","/rest/totalCountsForSelfLeaves" }, method = RequestMethod.GET)
	@ResponseBody
	public LeaveDTO totalCountsForSelfLeaves(
			@RequestParam(value = "orgLocationId", required = false) Long orgLocationId,
			@RequestParam(value = "userId", required = false) Long userId) throws Exception {
		return leaveService.getAllSelfLeavesCounts(orgLocationId, userId);

	}

	/**
	 * Get leave takers list for barchart
	 * 
	 * @param leaveDTO
	 * @param request
	 * @param response
	 * @return List<LeaveDTO>
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getLeaveTakersBarChat", "/rest/getLeaveTakersBarChat" }, method = RequestMethod.GET)
	@ResponseBody
	public List<LeaveDTO> getLeaveTakersBarChat(LeaveDTO leaveDTO, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		OrgLocations orgLocation = orgLocationService.getOrgLocations(Long.parseLong(leaveDTO.getOrgLocationId()));
		List<LeaveDTO> leavDtoList = leaveService.getLeaveTakersBarChat(orgLocation, leaveDTO);
		return leavDtoList;
	}

	/**
	 * Withdraw leave by employee
	 * 
	 * @param orgLocationId
	 * @param leaveId
	 * @param reason
	 * @return boolean
	 * @throws Exception
	 */
	@PreAuthorize("hasAnyRole('ROLE_APPLY_LEAVE')")
	@RequestMapping(value = {"/withdrawLeave", "/rest/withdrawLeave"})
	@ResponseBody
	public Response<String> withdrawLeave(@RequestParam(value = "orgLocationId", required = false) Long orgLocationId,
			@RequestParam(value = "leaveUUID", required = false) String leaveUUID,
			@RequestParam(value = "userId", required = false) Long userId,
			@RequestParam(value = "reason", required = false) String reason, HttpServletRequest request) throws Exception {
		
		Response<String> response = new Response<>();
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		User user = userService.getUserById(userId);
	try {
		User user1 = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if (user1 != null){
		boolean isValid = false;
		isValid = leaveService.getWithdrawLeave(orgLocations,leaveUUID,user,reason);
		if(isValid == true){
		    response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
		}
		else{
			response.setResponse(Result.INVALID_DATA.name());
			response.setResponseMessage(Result.INVALID_DATA.getValue());
		}
		}  else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}	
	 	
	} catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
		return response;
	}

	/**
	 * 
	 * @param orgLocationId
	 * @param userId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getLeaveTypeReport", "/rest/getLeaveTypeReport" }, method = RequestMethod.GET)
	public List<LeaveTypeDTO> getLeaveTypeReport(
			@RequestParam(value = "orgLocationId", required = false) Long orgLocationId,
			@RequestParam(value = "userId", required = false) Long userId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		User user = userService.getUserById(userId);
		Employees emp = employeesService.getEmployeeByUserIdAndOrgLocationsId(userId, orgLocations.getId());
		List<LeaveType> leaveTypeList = leaveTypeService.getLeaveTypeListByLocationId(String.valueOf(orgLocations.getId()));

		List<LeaveTypeDTO> leaveTypeDTOList = new ArrayList<>();
		
		try {
			if (leaveTypeList != null && !leaveTypeList.isEmpty()) {
				for (LeaveType type : leaveTypeList) {
					if(Constants.YES.equalsIgnoreCase(type.getVisibleInBalanceLeaves())){
						float pendingCounts = 0;
						float approvedCounts = 0;
						float rejectedCounts = 0;
						float cancelCounts = 0;
						LeaveTypeDTO leaveTypeDto = new LeaveTypeDTO();
						List<Leave> takenLeaveList = leaveService.getLeavesListByTypeIdAndLocationIdAndUserId(type.getLeaveTypeUUID(),orgLocations.getId(), user.getId());
						float empToatalLeaves = 0;	
						if(Constants.YES.equalsIgnoreCase(type.getVisibleInBalanceLeaves())){
							 empToatalLeaves  = associateBalanceLeaveService.getTotalLeavesCountByUserIdAndLeaveTypeId(type.getId(),user.getId(),orgLocations.getId(),orgLocations.getOrg().getId());	
						}

						float empAppliedLeaveCount = 0;
						float balLeaves = 0;
						//int totalLeavesperYear = leaveManagementUtil.getBalanceCountForLeaveType(type, emp, orgLocations, user);
						if (takenLeaveList != null && takenLeaveList.size() > 0) {
							 empAppliedLeaveCount = leaveManagementUtil.getTotalAppliedLeaveCount(orgLocations,type,user.getId());
							 empToatalLeaves = empToatalLeaves - empAppliedLeaveCount;
							 leaveTypeDto.setTotalNoOfDays(empAppliedLeaveCount);
							 leaveTypeDto.setName(type.getName());
								leaveTypeDto.setLeaveTypeId(type.getId());
								leaveTypeDto.setNoOfLeavesPerYear(String.format("%.2f", empToatalLeaves));
								leaveTypeDto.setTotalApprovedCount(approvedCounts);
								leaveTypeDto.setTotalRejectedCount(rejectedCounts);
								leaveTypeDto.setTotalPendingCount(pendingCounts);
								leaveTypeDto.setTotalCancelCount(cancelCounts);
								leaveTypeDTOList.add(leaveTypeDto);
							}
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return leaveTypeDTOList;
		}

		
	
	


	/**
	 * 
	 * @param orgLocationId
	 * @param userId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getRoles", "/rest/getRoles" }, method = RequestMethod.GET)
	public ResponseList<RoleDTO> getRoles(@RequestParam(value = "orgLocationId", required = false) Long orgLocationId,
			@RequestParam(value = "orgCode", required = false) String orgCode,
			@RequestParam(value = "roleHierarchyId", required = false) Long roleHierarchyId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		List<RoleDTO> roleDTOList = new ArrayList<>();
		ResponseList<RoleDTO> responseList = new ResponseList<>();
		Org org = orgService.getOrgByOrgCode(orgCode);
		List<Roles> rolesList = rolesService.getUserRolesListByModuleAndLoginUserRoleHierarchy(org, orgLocationId,
				roleHierarchyId);
		List<Long> optionRoleIds = optionsService.getOptionRoleIds(orgLocationId);
		List<Roles> rolesList1 = new ArrayList<>();
		for (Roles role : rolesList) {
			if (!optionRoleIds.contains(role.getId())) {
				rolesList1.add(role);
			}
		}
		rolesList1.forEach(role -> {
			RoleDTO roleDTO = new RoleDTO();
			roleDTO.setId(role.getId());
			roleDTO.setDisplayName(role.getDisplayName());
			roleDTOList.add(roleDTO);
		});
		responseList.setData(roleDTOList);
		return responseList;
	}

	@RequestMapping(value = { "/validateHolidayName", "/rest/validateHolidayName" }, method = RequestMethod.GET)
	public boolean validateHolidayName(@RequestParam(value = "orgLocationId", required = true) Long orgLocationId,
			@RequestParam(value = "from", required = true) String fromTable,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "fromDate", required = true) String fromDate,
			@RequestParam(value = "toDate", required = true) String toDate) throws Exception {
		boolean flag = false;
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		List<HolidayAndRestrictday> list = leaveService.getAllHolidayAndRestrictDaysInDuration(fromDate, toDate,
				orgLocations);
		List<HolidayAndRestrictday> listFilter = new ArrayList<HolidayAndRestrictday>();
		if (list != null && !list.isEmpty()) {
			for (HolidayAndRestrictday holidayAndRestrictday : list) {
				if (holidayAndRestrictday.getHoliday() != null
						&& StringUtils.isNotEmpty(holidayAndRestrictday.getHoliday().getStatus()))
					if (holidayAndRestrictday.getHoliday().getStatus().equalsIgnoreCase("1"))
						listFilter.add(holidayAndRestrictday);
			}
		}
		for (HolidayAndRestrictday holidayAndRestrictday : listFilter) {
			if (holidayAndRestrictday.getHoliday() != null) {
				if (id != null) {
					if (holidayAndRestrictday.getHoliday().getHolidayName().equalsIgnoreCase(name.trim())
							&& holidayAndRestrictday.getHoliday().getId().longValue() == id.longValue()) {
						flag = false;
						break;
					}
				} else {
					if (holidayAndRestrictday.getHoliday().getHolidayName().equalsIgnoreCase(name.trim())) {
						flag = true;
						break;
					}
				}
			}
		}
		return flag;
	}

	@RequestMapping(value = { "/validateName", "/rest/validateName" }, method = RequestMethod.GET)
	public boolean validateName(@RequestParam(value = "orgLocationId", required = true) Long orgLocationId,
			@RequestParam(value = "from", required = true) String fromTable,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "id", required = true) String id) {
		boolean flag = false;
		flag = leaveService.validateNameByOrgLocationIdandFromTable(name, orgLocationId, fromTable, id);
		return flag;
	}

	@RequestMapping(value = { "/validateMaxLeave", "/rest/validateMaxLeave" }, method = RequestMethod.GET)
	public @ResponseBody boolean validateMaxLeave(
			@RequestParam(value = "leaveTypeId", required = false) String leaveTypeId,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate,
			@RequestParam(value = "orgLocationId", required = false) Long orgLocationId,
			@RequestParam(value = "userId", required = false) Long userId) throws Exception {
		User user = userService.getUserById(userId);
		Employees emp = employeesService.getEmployeeByUserIdAndOrgLocationsId(userId, orgLocationId);
		boolean isvalidate = false;
		int totalLeavesApplied = 0;
		int totalLeaves = 0;

		List<Leave> leaveList = leaveService.getLeavesListByTypeIdAndLocationIdAndUserId(leaveTypeId, orgLocationId,
				userId);

		if (leaveList.size() > 0) {
			for (Leave leave : leaveList) {
				if (Constants.APPROVED.equalsIgnoreCase(leave.getStatus())) {
					totalLeavesApplied = totalLeavesApplied + Integer.valueOf(leave.getNumberOfDays());
				}
				totalLeaves = leaveManagementUtil.getBalanceCountForLeaveType(leaveList.get(0).getLeaveType(), emp,
						leaveList.get(0).getOrgLocations(), user);
			}

			if (totalLeavesApplied >= totalLeaves) {
				isvalidate = true;
			}
		}
		return isvalidate;

	}

	/**
	 * @author Pavan Jampana
	 * @param restrictDayDTO
	 * @return boolean
	 * @throws Exception
	 */
	@RequestMapping(value = { "/checkDuplicateRestrictDay",
			"/rest/checkDuplicateRestrictDay" }, method = RequestMethod.GET)
	public @ResponseBody boolean checkDuplicateRestrictDay(RestrictDayDTO restrictDayDTO) throws Exception {
		boolean flag = false;
		flag = restrictDayService.checkDuplicateRestrictDay(restrictDayDTO);
		return flag;
	}

	@RequestMapping(value = { "/getApproversForLeave", "/rest/getApproversForLeave" }, method = RequestMethod.GET)
	public @ResponseBody List<User> getApproversForLeave(@RequestParam(value = "userId", required = true) Long userId,@RequestParam(value = "notifyToOthers", required = false) String notifyToOthers,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
//		Long roleId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRoleId");

		UserRole selectedUserRole = userRoleService.getUserDetailsByUserIdAndLocationId(userId, orgLocations.getId());
//		List<User> approversLnotifyOthersist = new ArrayList<>();
//		boolean flag = false;
			List<User> appUser = new ArrayList<>();
			
			if(!StringUtils.isNotBlank(notifyToOthers) && selectedUserRole.getReportingManager() != null){
				UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(selectedUserRole.getReportingManager().getId(),orgLocations.getId());
				if (userRole == null) {
					userRole = userRoleService.getGlobalUserReportingManagerByUserIdAndOrgId(selectedUserRole.getReportingManager().getId(),orgLocations.getOrgId());
				}
				if (userRole != null) {
//					String roles = userRole.getRoles().getActionId();
//						if (roles.contains("238")) {
//							flag = true;
//						}
						User user = userRole.getUser();
						StringBuilder sb = new StringBuilder();
						if (StringUtils.isNotBlank(user.getLastName())) {
						sb.append(user.getFirstName()).append(" ").append(user.getLastName());
						} else {
						sb.append(user.getFirstName());
						}
						user.setFullName(user.getFullName().toString());
						user.setUserType(userRole.getUserType());
						appUser.add(userRole.getUser());
					}
			}
			
			List<User> approversList = leaveService.getApproversListBasedonHierarchyExceptLoginUser(userId,selectedUserRole.getRoles().getId(), orgLocations.getId());
			
			for (User user : approversList) {
				if(selectedUserRole.getReportingManager() != null) {
					if(selectedUserRole.getReportingManager().getId().longValue() != user.getId().longValue())
						appUser.add(user);
				}
			}
			return appUser;
	}

	@RequestMapping(value = { "/validateCarryForwardLeaves", "/rest/validateCarryForwardLeaves" }, method = RequestMethod.GET)
	public @ResponseBody boolean validateCarryForwardLeaves(
			@RequestParam(value = "leaveTypeId", required = false) Long leaveTypeId,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate,
			@RequestParam(value = "userId", required = false) Long userId, HttpServletRequest request)
			throws Exception {
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = userService.getUserById(userId);
		Employees emp = employeesService.getEmployeeByUserIdAndOrgLocationsId(userId, orgLocation.getId());
		int totalLeaves = 0;
		boolean isvalidate = false;
		Date appliedFromDate = commonUtil.getDateFormat(fromDate);
		Date endDate = commonUtil.getDateFormat(toDate);

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_YEAR, 1);
		Date yearStartDate = commonUtil.getDateFormat(Constants.DMY.format(cal.getTime()));

		Calendar sDate = Calendar.getInstance();
		Calendar eDate = Calendar.getInstance();
		sDate.setTime(appliedFromDate);
		eDate.setTime(yearStartDate);
		int difInMonths = sDate.get(Calendar.MONTH) - eDate.get(Calendar.MONTH);

		List<Leave> leaveList = leaveService.getLeavesListByTypeIdAndDuration(leaveTypeId, orgLocation.getId(), userId,
				yearStartDate, appliedFromDate);

		if (leaveList.size() > 0) {
			int totalAppliedLeaves = 0;
			totalLeaves = leaveManagementUtil.getBalanceCountForLeaveType(leaveList.get(0).getLeaveType(), emp,
					leaveList.get(0).getOrgLocations(), user);
			for (Leave leave : leaveList) {
				if ("Mothly".equalsIgnoreCase(leave.getLeaveType().getPeriod())
						&& Constants.YES.equalsIgnoreCase(leave.getLeaveType().getLeavesCarryforward())) {
					difInMonths = difInMonths * leave.getLeaveType().getNoOfDays();

				}
			}

		}

		return isvalidate;

	}

	@RequestMapping(value = "/getLeaveExportList")
	public void getLeaveExportList(LeaveDTO leaveDTO, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");

		File file = leaveService.getLeaveExport(orgLocations, leaveDTO);

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
		if (file != null) {
			file.delete();
		}
	}

	 @RequestMapping(value = "/shiftList", method = RequestMethod.POST)
		public @ResponseBody List<Shift> refreshRoleList(HttpServletRequest request, Long roleHierarchyId) throws Exception {
	    	OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    	List<Shift> shiftList = shiftService.getShiftList(orgLocations.getId());
           return shiftList;	   
		} 
		
	@RequestMapping(value = "/downloadLeaveTemplate")
	public void downloadLeaveTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		File file = leaveService.getLeaveTypeImportTemplate(orgLocation.getId());
		
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
	}
	
	/**
	 * Import Associate Balance Leave Type Count
	 * @param request
	 * @return LeaveTypeDTO
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadLeaveType", method = RequestMethod.POST)
	@ResponseBody
	public LeaveTypeDTO leaveUpload(MultipartHttpServletRequest request) throws Exception
	{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		MultipartFile multipartFile = null;
		Iterator<String> itrator = request.getFileNames();
		if (itrator.hasNext()) {
			multipartFile = request.getFile(itrator.next());
		}
		LeaveTypeDTO response = new LeaveTypeDTO();
		if (multipartFile != null) {
			response = leaveService.saveBulkLeaveTypes(multipartFile, orgLocation, user, org);
			return response;
		}
		return response;
	}
	

	
	/**
	 * returns Withdraw leaves jsp
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/withdrawLeaves")
	public ModelAndView withdrawLeavesJsp(@RequestParam(value = "orgLocationId", required = false) String orgLocationId,
			@RequestParam(value = "status", required = false) String status, HttpServletRequest request,
			HttpServletResponse response) {
		
			ModelAndView model = new ModelAndView();
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.addObject("orgLocationId", orgLocationId);
			model.addObject("status", status);
			model.setViewName("leavemanagement/withdrawLeaves");
			return model;
	}
	
	/**
	 * returns apply leave jsp
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception 
	 */
	@RequestMapping(value = "/reApplyLeaveByUUID")
	public ModelAndView reApllyLeaveByUUID(@RequestParam(value = "leaveUUID", required = false) String leaveUUID,@RequestParam(value = "currentStage", required = false) String currentStage,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			Leave leave = leaveService.getLeaveByLeaveUUId(leaveUUID);
			UserRole role = userRoleService.getUserDetailsByUserIdAndLocationId(leave.getUser().getId(), orgLocation.getId());
			List<LeaveApproveHierarchy> lahList = new ArrayList<>();
			for(LeaveApproveHierarchy lah : leave.getApproverHierarchyList()) {
				if(role.getReportingManager().getId().longValue() != lah.getApproverUserId().getId().longValue())
					lahList.add(lah);
			}
			
		
			String fromDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), leave.getFromDate());
			String toDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), leave.getToDate());
			
			ModelAndView model = new ModelAndView();
			model.addObject("leave", leave);
			model.addObject("approversList", lahList);
			model.addObject("leaveUUID", leaveUUID);
			model.addObject("fromDate", fromDate);
			model.addObject("toDate", toDate);
			model.addObject("currentStage", currentStage);
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.setViewName("leavemanagement/applyLeave");
			return model;
	}
	
	@RequestMapping(value = "/checkBalance")
	public void checkBalance(HttpServletRequest request,HttpServletResponse response) throws Exception {
//		List<OrgLocations> orglocationsList = orgLocationService.getAllOrgLocations();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<OrgLocations> orglocationsList = new ArrayList<>();
		orglocationsList.add(orgLocations);
		Format formatter = new SimpleDateFormat("MMM"); 
	    String monthName = formatter.format(new Date()).toUpperCase();
		for(OrgLocations orgLocation : orglocationsList){
			List<LeaveType> leaveTypeList = leaveTypeService.getLeaveTypeListByLocationIdAndStatus(String.valueOf(orgLocation.getId()));
			for(LeaveType leaveType : leaveTypeList){
				if(Constants.MONTHLY.equalsIgnoreCase(leaveType.getPeriod())){
					Calendar c = Calendar.getInstance();
					c.set(Calendar.DATE, 1);
					Date startDate = c.getTime();
					c.set(Calendar.DATE,     c.getActualMaximum(Calendar.DAY_OF_MONTH));
					Date endDate = c.getTime();
					int status = associateBalanceLeaveService.calculateAndUpdateAssociateBalanceLeaves(orgLocation,leaveType,startDate,endDate);
				}
			}
		}
	}
	
	
	@RequestMapping(value = "/calculateQuarterlyBalance")
	public void calculateQuarterlyBalance(HttpServletRequest request,HttpServletResponse response) throws Exception {
//		List<OrgLocations> orglocationsList = orgLocationService.getAllOrgLocations();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<OrgLocations> orglocationsList = new ArrayList<>();
		orglocationsList.add(orgLocations);
		Format formatter = new SimpleDateFormat("MMM"); 
	    String monthName = formatter.format(new Date()).toUpperCase();
		for(OrgLocations orgLocation : orglocationsList){
			List<LeaveType> leaveTypeList = leaveTypeService.getLeaveTypeListByLocationIdAndStatus(String.valueOf(orgLocation.getId()));
			for(LeaveType leaveType : leaveTypeList){
				if(Constants.QUARTERLY.equalsIgnoreCase(leaveType.getPeriod())){
					LocalDate today = LocalDate.now();
					LocalDate[] dates = conversionUtil.getThisMonthQuarterDates(today);
					int status = associateBalanceLeaveService.calculateAndUpdateAssociateBalanceLeaves(orgLocation,leaveType,dates[0].toDate(),dates[1].toDate());
				}
			}
		}
	}
	
	@RequestMapping(value = "/calculateHalfyearlyBalance")
	public void calculateHalfyearlyBalance(HttpServletRequest request,HttpServletResponse response) throws Exception {
//		List<OrgLocations> orglocationsList = orgLocationService.getAllOrgLocations();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<OrgLocations> orglocationsList = new ArrayList<>();
		orglocationsList.add(orgLocations);
		Format formatter = new SimpleDateFormat("MMM"); 
	    String monthName = formatter.format(new Date()).toUpperCase();
		for(OrgLocations orgLocation : orglocationsList){
			List<LeaveType> leaveTypeList = leaveTypeService.getLeaveTypeListByLocationIdAndStatus(String.valueOf(orgLocation.getId()));
			for(LeaveType leaveType : leaveTypeList){
				if(Constants.HALFYEARLY.equalsIgnoreCase(leaveType.getPeriod())){
					LocalDate today = LocalDate.now();
					LocalDate[] dates = conversionUtil.getThisMonthHalfyearyDates(today);
					int status = associateBalanceLeaveService.calculateAndUpdateAssociateBalanceLeaves(orgLocation,leaveType,dates[0].toDate(),dates[1].toDate());
				}
			}
		}
	}
	
	@RequestMapping(value = "/calculateYearlyBalance")
	public void calculateYearlyBalance(HttpServletRequest request,HttpServletResponse response) throws Exception {
//		List<OrgLocations> orglocationsList = orgLocationService.getAllOrgLocations();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<OrgLocations> orglocationsList = new ArrayList<>();
		orglocationsList.add(orgLocations);
		Format formatter = new SimpleDateFormat("MMM"); 
	    String monthName = formatter.format(new Date()).toUpperCase();
		for(OrgLocations orgLocation : orglocationsList){
			List<LeaveType> leaveTypeList = leaveTypeService.getLeaveTypeListByLocationIdAndStatus(String.valueOf(orgLocation.getId()));
			for(LeaveType leaveType : leaveTypeList){
				if(Constants.YEARLY.equalsIgnoreCase(leaveType.getPeriod())){
					Date[] dates = conversionUtil.getCurrentYearStartAndEndDate();
					int status = associateBalanceLeaveService.calculateAndUpdateAssociateBalanceLeaves(orgLocation,leaveType,dates[0],dates[1]);
				}
			}
		}
	}

	@RequestMapping(value = "/ws/dumpAllGeneralShifts", method = RequestMethod.GET)
    public void dumpAllGeneralShifts(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        List<OrgLocations> locationsList = orgLocationService.getAllOrgLocations();
        System.out.println("Start Time"+java.time.LocalDateTime.now());
        for (OrgLocations orgLocation : locationsList) {
             commonUtil.saveDefaultGenaeralShift(orgLocation, orgLocation.getUser(), Constants.GENERAL_SHIFT);
        }
        System.out.println("End Time"+java.time.LocalDateTime.now());

    }
	
	
	@RequestMapping(value = "/ws/dumpAllGeneralShiftsWithActiveUsers", method = RequestMethod.GET)
    public void dumpAllGeneralShiftsWithActiveUsers(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
		List<BigInteger> userRoleList =  userRoleService.getAllUsersRoleList();
		for(BigInteger roleId:userRoleList) {
			UserRole userRole = userRoleService.getUserRoleById(roleId.longValue());	
			Shift shift = shiftService.getShiftListByShiftNameAndOrglocationId(Constants.GENERAL_SHIFT,userRole.getOrgLocations().getId());
			   if(userRole.getShift() == null) {
					userRole.setShift(shift);
					userRoleService.addUserRole(userRole);
				}
		}
    }

	@RequestMapping(value = { "/leavepolicyDescription"}, method = RequestMethod.POST)
	public LeaveType leavepolicyDescription(@RequestParam(value = "leaveTypeId", required = true) Long leaveTypeId) throws Exception {
	      LeaveType leaveType =  leaveTypeService.getLeaveTypeById(leaveTypeId);
		 return leaveType;
	}
	
	@RequestMapping(value = { "/calculateLeaveDuration", "/rest/calculateLeaveDuration"}, method = RequestMethod.GET)
	public Response<Float> calculateLeaveDuration(LeaveDTO leaveDTO,HttpServletRequest request) throws Exception {
		Response<Float> response = new Response<>();
	    float noOfDays = 0f; 
	    Long orgLocationId = (Long)SourceLeadSessionManager.getSessionAttribute(request,"orgLocationId");
		OrgLocations orgLocations = orgLocationService.getOrgLocationById(orgLocationId);
	    Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		orgLocations.setOrg(org);
		LeaveType leaveType =  leaveTypeService.getLeaveTypeByUUID(leaveDTO.getLeaveType());
		Leave leave = new Leave();
		leave.setFromDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), leaveDTO.getFromDate()));
		User usr = userService.getUserById(Long.parseLong(leaveDTO.getUserId()));
		leave.setUser(usr);
		if(StringUtils.isNotBlank(leaveDTO.getToDate()))
			leave.setToDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), leaveDTO.getToDate()));
		if(StringUtils.isNotBlank(leaveDTO.getFromHalfDay())) {
			leave.setFromHalfDay(leaveDTO.getFromHalfDay());
		}
		if(StringUtils.isNotBlank(leaveDTO.getToHalfDay())) {
			leave.setToHalfDay(leaveDTO.getToHalfDay());
		}
		if(StringUtils.isNotBlank(leaveDTO.getFromDateFromHalfTime())) {
			leave.setFromDateFromHalfTime(leaveDTO.getFromDateFromHalfTime());
		}
		if(StringUtils.isNotBlank(leaveDTO.getFromDateToHalfTime())) {
			leave.setFromDateToHalfTime(leaveDTO.getFromDateToHalfTime());
		}
		if(StringUtils.isNotBlank(leaveDTO.getToDateFromHalfTime())) {
			leave.setToDateFromHalfTime(leaveDTO.getToDateFromHalfTime());
		}
		if(StringUtils.isNotBlank(leaveDTO.getToDateToHalfTime())) {
			leave.setToDateToHalfTime(leaveDTO.getToDateToHalfTime());
		}
		//float days = leaveManagementUtil.calculateAppliedLeavesCountWithHalfDaysAndWorkingHours(leave,leaveType.getNoOfDaysPerPeriod(),orgLocations.getAverageWorkingHours());
		
		noOfDays = leaveManagementUtil.getAppliedLeaveCount(orgLocations,leaveType,leave,"Duration");
		response.setObject(noOfDays);
		return response;
	}

}
