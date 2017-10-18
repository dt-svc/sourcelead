package com.bis.sourcelead.rest;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
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
import com.bis.sourcelead.dto.NotificationDTO;
import com.bis.sourcelead.dto.OptionsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.RestrictDayDTO;
import com.bis.sourcelead.dto.ShiftDTO;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Holiday;
import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.entity.LeaveApproveHierarchy;
import com.bis.sourcelead.entity.LeaveManagementHistory;
import com.bis.sourcelead.entity.LeaveRole;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.Option;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RestrictDay;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.Shift;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.HolidayService;
import com.bis.sourcelead.service.LeaveService;
import com.bis.sourcelead.service.LeaveTypeService;
import com.bis.sourcelead.service.OptionsService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.RestrictDayService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.ShiftService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;


@RestController
public class LeaveManagementRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(LeaveManagementRestController.class);
	
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

	
	/**
	 * returns Pending leaves jsp
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/pendingLeaves")
	public ModelAndView pendingLeavesJsp(HttpServletRequest request,HttpServletResponse response) {
		   ModelAndView model = new ModelAndView();
		   model.addObject("sourceleadUrl", sourceleadUrl);
		   model.setViewName("leavemanagement/pendingLeaves");
	       return model;
	}
	
	/**
	 * returns approved leaves jsp
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/approvedLeaves")
	public ModelAndView approvedLeavesJsp(HttpServletRequest request,HttpServletResponse response) {
		   ModelAndView model = new ModelAndView();
		   model.addObject("sourceleadUrl", sourceleadUrl);
		   model.setViewName("leavemanagement/approvedLeaves");
	       return model;
	}
	
	/**
	 * returns Rejected leaves jsp
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/rejectedLeaves")
	public ModelAndView rejectedLeavesJsp(HttpServletRequest request,HttpServletResponse response) {
		   ModelAndView model = new ModelAndView();
		   model.addObject("sourceleadUrl", sourceleadUrl);
		   model.setViewName("leavemanagement/rejectedLeaves");
	       return model;
	}
	
	/**
	 * retuens cancel leaves jsp
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/cancelLeaves")
	public ModelAndView cancelLeavesJsp(HttpServletRequest request,HttpServletResponse response) {
		   ModelAndView model = new ModelAndView();
		   model.addObject("sourceleadUrl", sourceleadUrl);
		   model.setViewName("leavemanagement/cancelLeaves");
	       return model;
	}
	
	
	/**
	 * retuens leave Management Reports leaves jsp
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception 
	 */
	@RequestMapping(value = "/leaveManagementReports")
	public ModelAndView leaveManagementReportsJsp(HttpServletRequest request,HttpServletResponse response) throws Exception {
		   ModelAndView model = new ModelAndView();
		   OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		   List<Department> departmentList = departmentService.getDepartmentByOrgLocationIdAndStatus(orgLocations.getId(),Constants.RECORD_ACTIVE.toString());
		   model.addObject("departmentList",departmentList);
		   model.addObject("sourceleadUrl", sourceleadUrl);
		   model.setViewName("leavemanagement/leaveManagementReports");
	       return model;
	}
	
	
	/**
	 * retuens leave Management Reports leaves jsp
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception 
	 */
	@PreAuthorize("hasAnyRole('ROLE_APPLY_LEAVE')")
	@RequestMapping(value = "/applyLeave")
	public ModelAndView applyLeaveJsp(HttpServletRequest request,HttpServletResponse response) throws Exception {
		   ModelAndView model = new ModelAndView();
		   OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		   Long roleId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRoleId");
		   List<User> usersLis = userService.getAllUsersByOrgLocationCode(orgLocations.getId());
		   model.addObject("sourceleadUrl", sourceleadUrl);
		   model.addObject("usersLis", usersLis);
			List<User> approverList = userService.getManagersByUserRolesAndOrgLocationId(roleId, orgLocations.getId(),Long.valueOf(Constants.TAB_USER));
			if(approverList !=null) {
				for (User user : approverList) {
					UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocations.getId());
					if(userRole != null){
						user.setUserType(userRole.getUserType());
					}
				}
				 model.addObject("approverList", approverList);
			}
		   model.setViewName("leavemanagement/applyLeave");
	       return model;
	}
	
	
	@RequestMapping(value = {"/getPieChartForLeaveStatus", "/rest/getPieChartForLeaveStatus"}, method = RequestMethod.GET)
	public List<LeaveType> getPieChartForLeaveStatus(@RequestParam(value = "orgLocationId", required = false) Long orgLocationId,
			                                         @RequestParam(value = "userId", required = false) Long userId,
			                                         HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		   OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		   User loginUser = userService.getUserById(userId);
		   UserRole roleId = userRoleService.getUserRoleByUserId(userId);

		   int balanceLeaveCounts = 0;
		   int pendingLeaveCounts = 0;
		   int takenLeaveCounts = 0;
		   List<LeaveType> leaveTypeList = leaveTypeService.getLeaveTypeListByLocationIdAndStatus(String.valueOf(orgLocations.getId()));

		   List<LeaveType> roleLeaveType = new ArrayList<>();
		   if(leaveTypeList !=null && !leaveTypeList.isEmpty()) {
			   for(LeaveType leaveType : leaveTypeList) {
				   Set<Long> roleIds = new HashSet<>();
				   for(LeaveRole leaveRole : leaveType.getLeaveTypeRolesSet()) {
					   roleIds.add(leaveRole.getRoleId().getId());
				   }
				   if(roleIds.contains(roleId) || "Y".equalsIgnoreCase(leaveType.getApplicableToUsers())) {
					   roleLeaveType.add(leaveType);
				   }
			   }
			   
				for(LeaveType type : roleLeaveType) {
				int totalLeaves = Integer.valueOf(type.getNumbefrOfLeavesPeryear());
				List<Leave> totalleavesTaken = leaveService.getLeavesListByTypeIdAndLocationIdAndUserId(type.getId(),orgLocations.getId(), loginUser.getId());
				for (Leave leave : totalleavesTaken) {
					if(Constants.PENDING.equalsIgnoreCase(leave.getStatus())) {
//						totalLeaves = totalLeaves - (Integer.valueOf(leave.getNumberOfDays()));
						pendingLeaveCounts = pendingLeaveCounts + (Integer.valueOf(leave.getNumberOfDays()));
					}
					if(Constants.APPROVED.equalsIgnoreCase(leave.getStatus())) {
						totalLeaves = totalLeaves - (Integer.valueOf(leave.getNumberOfDays()));
						takenLeaveCounts = takenLeaveCounts + (Integer.valueOf(leave.getNumberOfDays()));
					}
				}
				balanceLeaveCounts += totalLeaves;
				type.setBalanceLeaveCounts(balanceLeaveCounts);
				type.setPendingLeaveCounts(pendingLeaveCounts);
				type.setTakenLeaveCounts(takenLeaveCounts);
				type.setRemainingTotalLeaveCounts(totalLeaves);
				}
		   }
		return roleLeaveType;
	}
	
	/**
	 * returns leave configuration jsp
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = "/leaveConfiguration")
	public ModelAndView leaveConfigurationJsp(HttpServletRequest request,HttpServletResponse response) {
		   ModelAndView model = new ModelAndView();
		   model.addObject("sourceleadUrl", sourceleadUrl);
		   model.setViewName("leavemanagement/leaveConfigure");
	       return model;
	}
	

	
	/**
	 * returns add leave  jsp
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception 
	 */
	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = "/addLeaveType")
	public ModelAndView addLeaveJsp(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		Long roleHierarchyId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "roleHierarchyId");
		List<Department> departmentList = departmentService.getDepartmentByOrgLocationIdAndStatus(orgLocations.getId(),Constants.RECORD_ACTIVE.toString());
		List<Roles> rolesList = rolesService.getUserRolesListByModuleAndLoginUserRoleHierarchy(org, orgLocations.getId(), roleHierarchyId);
		model.addObject("departmentList", departmentList);
		model.addObject("rolesList", rolesList);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("leavemanagement/addLeaveType");
		return model;
	}
	
	/**
	 * returns view leave  jsp
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = "/viewLeaveTypes")
	public ModelAndView viewLeavesJsp(HttpServletRequest request,HttpServletResponse response) {
		   ModelAndView model = new ModelAndView();
		   model.addObject("sourceleadUrl", sourceleadUrl);
		   model.setViewName("leavemanagement/viewLeaveType");
	       return model;
	}
	
	/**
	 * returns add Holiday  jsp
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = "/addHoliday")
	public ModelAndView addHolidayJsp(HttpServletRequest request,HttpServletResponse response) {
		   ModelAndView model = new ModelAndView();
		   model.addObject("sourceleadUrl", sourceleadUrl);
		   model.setViewName("leavemanagement/addHoliday");
	       return model;
	}

	/**
	 * returns view Holiday jsp
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = "/viewHoliday")
	public ModelAndView viewHolidayJsp(HttpServletRequest request,HttpServletResponse response) {
		   ModelAndView model = new ModelAndView();
		   model.addObject("sourceleadUrl", sourceleadUrl);
		   model.setViewName("leavemanagement/viewHoliday");
	       return model;
	}
	
	/**
	 * returns add shift  jsp
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = "/addShift")
	public ModelAndView addShiftJsp(HttpServletRequest request,HttpServletResponse response) {
		   ModelAndView model = new ModelAndView();
		   model.addObject("sourceleadUrl", sourceleadUrl);
		   model.setViewName("leavemanagement/addShift");
	       return model;
	}
	/**
	 * returns view shift  jsp
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = "/viewShift")
	public ModelAndView viewShiftJsp(HttpServletRequest request,HttpServletResponse response) {
		   ModelAndView model = new ModelAndView();
		   model.addObject("sourceleadUrl", sourceleadUrl);
		   model.setViewName("leavemanagement/viewShift");
	       return model;
	}
	
	/**
	 * returns add RestrictDay  jsp
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = "/addRestrictDay")
	public ModelAndView addRestrictDayJsp(HttpServletRequest request,HttpServletResponse response,@RequestParam(value = "restrictDayID", required = false) String restrictDayID) {
		   ModelAndView model = new ModelAndView();
		   OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		   Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		   Long roleHierarchyId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "roleHierarchyId");
		   List<Roles> rolesList = rolesService.getUserRolesListByModuleAndLoginUserRoleHierarchy(org,orgLocations.getId(),roleHierarchyId);
		   model.addObject("rolesList", rolesList);
		   List<Roles> existedRolesList = new ArrayList<Roles>();
		   if(StringUtils.isNotBlank(restrictDayID)) {
			   RestrictDay restrictDay = restrictDayService.getRestrictDayById(restrictDayID);
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
	 * returns add RestrictDay  jsp
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = "/viewRestrictDay")
	public ModelAndView viewRestrictDayJsp(HttpServletRequest request,HttpServletResponse response) {
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
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		Long roleHierarchyId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "roleHierarchyId");
		List<Department> departmentList = departmentService.getDepartmentByOrgLocationId(orgLocations.getId());
		List<Roles> rolesList = rolesService.getUserRolesListByModuleAndLoginUserRoleHierarchy(org,orgLocations.getId(),roleHierarchyId);
		List<String> calendarList = new ArrayList<String>(Arrays.asList(Constants.MONTHS));
		List<String> weekends = new ArrayList<String>(Arrays.asList(Constants.WEEKENDS));
		model.addObject("calendarList", calendarList);
		model.addObject("weekends", weekends);
		model.addObject("departmentList", departmentList);
		model.addObject("rolesList", rolesList);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("leavemanagement/addOptions");
		return model;
	}
	
	/**
	 * returns add RestrictDay  jsp
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = "/viewOptions")
	public ModelAndView viewOptionsJsp(HttpServletRequest request,HttpServletResponse response) {
		   ModelAndView model = new ModelAndView();
		   model.addObject("sourceleadUrl", sourceleadUrl);
		   model.setViewName("leavemanagement/viewOptions");
	       return model;
	}
	

	@RequestMapping(value = "/storeRestrictedDay")
	public String storeRestrictedDay(RestrictDayDTO restrictDayDTO) throws Exception {
	      
		restrictDayService.storeRestrictedDay(restrictDayDTO);
		return "success";
	}
	
	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = {"/getRestrictDayList", "/rest/getRestrictDayList"}, method = RequestMethod.GET)
	public ResponseList<RestrictDayDTO> getRestrictDayList(@RequestParam(value = "orgLocationId", required = true) Long orgLocationId) throws Exception {
		OrgLocations orgLocations = orgLocationService.getOrgLocations(Long.valueOf(orgLocationId));
		ResponseList<RestrictDayDTO> responseList = new ResponseList<>();
		
		List<RestrictDay> restrictDayList = restrictDayService.getRestrictDayListByOrgLocationId(orgLocationId);
		List<RestrictDayDTO> restrictDayDTOList = new ArrayList<>();
		for(RestrictDay restrictDay : restrictDayList){
			RestrictDayDTO restrictDayDTO = new RestrictDayDTO();
			
			restrictDayDTO.setRestrictName(restrictDay.getRestrictName());
			restrictDayDTO.setStartDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), restrictDay.getStartDate()));
			restrictDayDTO.setEndDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), restrictDay.getEndDate()));
			restrictDayDTO.setReason(restrictDay.getReason());
			restrictDayDTO.setIsApllicable(restrictDay.getApplicableToAll());
			restrictDayDTO.setStatus(restrictDay.getStatus());
			restrictDayDTOList.add(restrictDayDTO);
		}
		responseList.setData(restrictDayDTOList);
		return responseList;
	}

	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = {"/saveShift", "/rest/saveShift"}, method = RequestMethod.POST)
	public String saveShift(ShiftDTO shiftDTO) throws Exception {
		shiftService.saveShift(shiftDTO);
	       return "success";
	}
	
	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = {"/getShiftList", "/rest/getShiftList"}, method = RequestMethod.GET)
	public ResponseList<ShiftDTO> getShiftList(@RequestParam (value = "orgLocationId", required = true )Long orgLocationId) throws Exception{
		ResponseList<ShiftDTO> responseList = new ResponseList<>();
		List<Shift> shiftList = shiftService.getShiftList(orgLocationId);
	    List<ShiftDTO> shiftDTOList = new ArrayList<>();
	    
	      for(Shift shift : shiftList){
		     ShiftDTO shiftDTO = new ShiftDTO();
				shiftDTO.setShiftName(shift.getShiftName());
				shiftDTO.setTimeIn(shift.getTimeIn());
				shiftDTO.setTimeOut(shift.getTimeOut());
				shiftDTO.setShiftDescription(shift.getShiftDescription());
				shiftDTO.setStatus(shift.getStatus());
				shiftDTOList.add(shiftDTO);
	
	            }
				responseList.setData(shiftDTOList);
				return responseList;
	         }
	
	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = "/editleaveshift")
	public ModelAndView editShift(@RequestParam (value = "shiftId", required = false )String shiftId,HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView model = new ModelAndView();
	    Shift shift = shiftService.getShiftById(shiftId);
		model.addObject("shift", shift);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("leavemanagement/addShift");
		return model;
	}
	
	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = "/deleteShift")
	public boolean deleteShift(@RequestParam(value = "shiftId", required = false) String shiftId,@RequestParam(value = "orgLocationId", required = false) String orgLocationId) throws Exception
	{
		boolean isDeleted = false;
		 Shift deleteShift = shiftService.getShiftById(shiftId);
		 if(deleteShift !=null) {
			 deleteShift.setStatus(Constants.MEMBER_IN_ACTIVE);
			 shiftDao.deleteShift(deleteShift);
			 isDeleted = true;
		 }
		return isDeleted;
	}

	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = "/saveHoliday", method = RequestMethod.POST)
	public @ResponseBody String saveHoliday(HolidayDTO holidayDTO,HttpServletRequest request)
			throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		if(orgLocations.getId() != null)
			holidayDTO.setOrgLocationId(orgLocations.getId().toString());
		if(holidayDTO != null)
		holidayService.storeHoliday(holidayDTO);
		return "success";
	}
	
	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = {"/getHolidaysList","/rest/getHolidaysList"}, method = RequestMethod.GET)
	public @ResponseBody List<HolidayDTO> getHolidaysList(@RequestParam(value = "orgLocationId", required = false) Long orgLocationId, HttpServletRequest request) throws Exception
	{
		List<HolidayDTO> holiodaysDTOList = null;
		holiodaysDTOList = holidayService.getAllHolidaysList(orgLocationId);
		return holiodaysDTOList;
	}
	
	/**
	 * saving new leave type
	 * 
	 * @throws Exception
	 * @throws Exception
	 * 
	 */
	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = "/saveNewLeaveType")
	public @ResponseBody String saveNewLeaveType(LeaveTypeDTO leaveTypeDTO, HttpServletRequest request) throws Exception
	{
		LeaveType leaveType = leaveTypeService.addLeaveType(leaveTypeDTO);
		if(leaveType !=null) {
			return "success";
		}
		return "failed";
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
	@RequestMapping(value = {"/getLeaveTypesByLocationid","/rest/getLeaveTypesByLocationid"}, method = RequestMethod.GET)
	public @ResponseBody List<LeaveType> getLeaveTypesByLocationid(LeaveTypeDTO leaveTypeDTO, HttpServletRequest request) throws Exception
	{
		List<LeaveType> leaveTypeList = leaveTypeService.getLeaveTypeListByLocationId(leaveTypeDTO.getOrgLocationId());
		return leaveTypeList;
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
	@ResponseBody
	public boolean saveLeaveOption(OptionsDTO optionsDTO, HttpServletRequest request) throws Exception {

		boolean isInserted = false;
		Option options = optionsService.saveLeaveOptions(optionsDTO);
		if (options != null) {
			isInserted = true;
		}
		return isInserted;
	}
	
	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = {"/viewLeaveOption", "/rest/viewLeaveOption"}, method = RequestMethod.GET)
	@ResponseBody
	public  List<OptionsDTO> getDeductionsDetailsbyId(@RequestParam(value = "orgLocationId", required = false) Long orgLocationId, HttpServletRequest request) throws Exception
	{
		List<OptionsDTO> optionsDTOList = null;
		 optionsDTOList = optionsService.getOptionsListByLocationId(orgLocationId);
		 if(optionsDTOList !=null) {
			 return optionsDTOList;
		 }
		return optionsDTOList;
	}
	
	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = {"/deleteLeaveOption", "/rest/deleteLeaveOption"}, method = RequestMethod.POST)
	@ResponseBody
	public  boolean deleteLeaveOption(@RequestParam(value = "optionId", required = false) String optionId,@RequestParam(value = "reason", required = false) String reason,HttpServletRequest request) throws Exception
	{
		boolean isDeleted = false;
		 Option options = optionsService.getOptionsById(optionId);
		 if(options !=null) {
			 options.setStatus("0");
			 options.setDeleteReason(reason);
			 optionsDao.saveLeaveOption(options);
			 isDeleted = true;
		 }
		return isDeleted;
	}
	
	/**
	 * returns add RestrictDay  jsp
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception 
	 */
	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = "/editOption")
	public ModelAndView editOption(@RequestParam(value="optionId", required=false) String optionId,HttpServletRequest request,HttpServletResponse response) throws Exception {
		   ModelAndView model = new ModelAndView();
		   OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
			Long roleHierarchyId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "roleHierarchyId");
			List<Roles> rolesList = rolesService.getUserRolesListByModuleAndLoginUserRoleHierarchy(org,orgLocations.getId(),roleHierarchyId);
			List<Department> departmentList = departmentService.getDepartmentByOrgLocationId(orgLocations.getId());
		    Option options = optionsService.getOptionsById(optionId);
		   if(options.getDepartmentId() !=null) {
			   Long departmentId = options.getDepartmentId().getId();
			   model.addObject("departmentId", departmentId);
		   }
		   if(options.getWeekends() !=null) {
			   String weekends = options.getWeekends();
			   model.addObject("weekends", weekends);
		   }
		   Set<LeaveRole> leaveRoles = options.getLeaverolesSet();
		  List<Roles> existedRolesList = new ArrayList<Roles>();
		   leaveRoles.forEach(role->{
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
			
		   if(options.getWeekends() != null) {
			   model.addObject("existWeekends", options.getWeekends());
		   }
			if(options.getCalendarMonth() != null) {
				model.addObject("existCalendarList", options.getCalendarMonth());
			}
		   model.addObject("existedRoles", existedRolesList);
		   model.addObject("rolesList", rolesList);
		   model.addObject("departmentList", departmentList);
		   model.addObject("options", options);
		   model.addObject("sourceleadUrl", sourceleadUrl);
		   model.setViewName("leavemanagement/addOptions");
	       return model;
	}
	

	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = {"/deleteRestrictedDay"})
	@ResponseBody
	public  boolean deleteRestrictedDay(@RequestParam(value = "id", required = false) String restrictId,@RequestParam(value = "reason", required = false) String reason) throws Exception
	{
		boolean isDeleted = false;
		 RestrictDay restrictDay = restrictDayService.getRestrictDayById(restrictId);
		 if(restrictDay !=null) {
			 restrictDay.setStatus("0");
			 restrictDay.setDeleteReason(reason);
			 restrictDayDao.storeRestrictedDay(restrictDay);
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
		@RequestMapping(value = {"/applyLeave","/rest/applyLeave"}, method = RequestMethod.POST)
		@ResponseBody
		public boolean applyLeave(LeaveDTO leaveDTO, HttpServletRequest request) throws Exception {
	
			boolean isInserted = false;
			OrgLocations orgLocations = orgLocationService.getOrgLocations(Long.parseLong(leaveDTO.getOrgLocationId()));
			User user = userService.getUserById(Long.parseLong(leaveDTO.getUserId()));
			leaveDTO.setStaticPath(staticPath);
			Leave leave = leaveService.saveLeave(leaveDTO,orgLocations,user);
			if (leave != null) {
				isInserted = true;
			}
			return isInserted;
		}
	
	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = "/editLeaveType")
	public ModelAndView editLeaveType(LeaveTypeDTO leaveTypeDTO, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		Long roleHierarchyId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "roleHierarchyId");
		List<Roles> rolesList = rolesService.getUserRolesListByModuleAndLoginUserRoleHierarchy(org, orgLocations.getId(), roleHierarchyId);
		List<Department> departmentList = departmentService.getDepartmentByOrgLocationId(orgLocations.getId());
		LeaveType leaveType = leaveTypeService.getLeaveTypeById(leaveTypeDTO.getLeaveTypeId());
		if (leaveType.getDepartmentId() != null) {
			Long departmentId = leaveType.getDepartmentId().getId();
			model.addObject("departmentId", departmentId);
		}
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
		model.addObject("leaveType", leaveType);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("leavemanagement/addLeaveType");
		return model;
	}
	

	@RequestMapping(value = {"/getLeavePolicy", "/rest/getLeavePolicy"}, method = RequestMethod.GET)
	@ResponseBody
	public  String getleavePolicy(@RequestParam(value = "leaveTypeId", required = false) String leaveTypeId,HttpServletRequest request) throws Exception
	{
		String leavePolicy = null;
		 LeaveType leaveType = leaveTypeService.getLeaveTypeById(leaveTypeId);
		 if(leaveType !=null && "Y".equalsIgnoreCase(leaveType.getVisibilityToAllUsers())) {
			 leavePolicy = leaveType.getPolicyDescription();
		 }
		return leavePolicy;
	}
	

	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = {"/deleteLeaveType", "/rest/deleteLeaveType"}, method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteLeaveType(LeaveTypeDTO leaveTypeDTO, HttpServletRequest request) throws Exception
	{
		boolean isDeleted = false;
		LeaveType leaveType = leaveTypeService.getLeaveTypeById(leaveTypeDTO.getLeaveTypeId());
		 if(leaveType !=null) {
			 
			 if("reactive".equalsIgnoreCase(leaveTypeDTO.getStatus())){
				 leaveType.setStatus(Constants.RECORD_ACTIVE.toString());
			 }else{
			 leaveType.setStatus(Constants.RECORD_INACTIVE.toString());
			 leaveType.setDeleteReason(leaveTypeDTO.getDeleteReason());
			 }
			 if(leaveTypeDTO.getLoginUserId() != null){
				 User user = userService.getUserById(Long.parseLong(leaveTypeDTO.getLoginUserId()));
				 leaveType.setUpdatedBy(user.getUserCode());
			 }
			 leaveType.setUpdatedDate(new Date());
			 leaveTypeDao.addLeaveType(leaveType);
			 isDeleted = true;
		}
		return isDeleted;
	}

	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = "/editHoliday")
	public ModelAndView editHoliday(@RequestParam(value="holidayId", required=false) String holidayId,HttpServletRequest request,HttpServletResponse response) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ModelAndView model = new ModelAndView();
		   Holiday holidays = holidayService.getHolidaysById(holidayId);
		   HolidayDTO holidayDTO = new HolidayDTO();
		   holidayDTO.setId(holidays.getId());
		   holidayDTO.setHolidayName(holidays.getHolidayName());
		   holidayDTO.setDescription(holidays.getDescription());
		   holidayDTO.setFromDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), holidays.getFromDate()));
		   holidayDTO.setFromHalfDayCheckBox(holidays.getFromHalfDayCheckBox());
		   holidayDTO.setFromHalfDayAmPm(holidays.getFromHalfDayAmPm());
		   holidayDTO.setToDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), holidays.getToDate()));
		   holidayDTO.setToHalfDayCheckBox(holidays.getToHalfDayCheckBox());
		   holidayDTO.setToHalfDayAmPm(holidays.getToHalfDayAmPm());
		   holidayDTO.setHolidayStatus(holidays.getHolidayStatus());
		   model.addObject("holidayDTO", holidayDTO);
		   model.addObject("sourceleadUrl", sourceleadUrl);
		   model.setViewName("leavemanagement/addHoliday");
	       return model;
	}
	
	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = {"/deleteHoliday","/rest/deleteHoliday"}, method = RequestMethod.GET)
	public  @ResponseBody boolean deleteHoliday(@RequestParam(value = "holidayId", required = false) String holidayId,HttpServletRequest request) throws Exception
	{
		boolean isDeleted = false;
		 Holiday deleteHoliday = holidayService.getHolidaysById(holidayId);
		 if(deleteHoliday !=null) {
			 deleteHoliday.setStatus("0");
			 holidayDAO.saveDeleteHoliday(deleteHoliday);
			 isDeleted = true;
		 }
		return isDeleted;
	}
	
	@RequestMapping(value = {"/validateHolidayName","/rest/validateHolidayName"}, method = RequestMethod.GET)
	public  @ResponseBody boolean validateHolidayName(@RequestParam(value = "holidayName", required = false) String holidayName,@RequestParam(value = "orgLocationId", required = false) Long orgLocationId,HttpServletRequest request) throws Exception
	{
		boolean isDeleted = false;
		 Holiday deleteHoliday = holidayService.getHolidaysById(holidayName);
		 if(deleteHoliday !=null) {
			 deleteHoliday.setStatus("0");
			 holidayDAO.saveDeleteHoliday(deleteHoliday);
			 isDeleted = true;
		 }
		return isDeleted;
		
	}
	
	
	/**
	 * returns add Employee Requested Leaves  jsp
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@PreAuthorize("hasAnyRole('ROLE_APPROVE_LEAVE')")
	@RequestMapping(value = "/employeeRequestedLeaves")
	public ModelAndView employeeRequestedLeavesJsp(HttpServletRequest request,HttpServletResponse response) {
		   ModelAndView model = new ModelAndView();
		   model.addObject("sourceleadUrl", sourceleadUrl);
		   model.setViewName("leavemanagement/employeeRequestedLeaves");
	       return model;
	}
	
	/**
	 * returns add Employee Approved Leaves  jsp
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@PreAuthorize("hasAnyRole('ROLE_APPROVE_LEAVE')")
	@RequestMapping(value = "/employeeApprovedLeaves")
	public ModelAndView employeeApprovedLeavesJsp(HttpServletRequest request,HttpServletResponse response) {
		   ModelAndView model = new ModelAndView();
		   model.addObject("sourceleadUrl", sourceleadUrl);
		   model.setViewName("leavemanagement/employeeApprovedLeaves");
	       return model;
	}
	
	/**
	 * returns add Employee Rejected Leaves  jsp
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@PreAuthorize("hasAnyRole('ROLE_APPROVE_LEAVE')")
	@RequestMapping(value = "/employeeRejectedLeaves")
	public ModelAndView employeeRejectedLeaves(HttpServletRequest request,HttpServletResponse response) {
		   ModelAndView model = new ModelAndView();
		   model.addObject("sourceleadUrl", sourceleadUrl);
		   model.setViewName("leavemanagement/employeeRejectedLeaves");
	       return model;
	}
	
	/**
	 * returns add Employee Cancel Leaves  jsp
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@PreAuthorize("hasAnyRole('ROLE_APPROVE_LEAVE')")
	@RequestMapping(value = "/employeeCancelLeaves")
	public ModelAndView employeeCancelLeavesLeaves(HttpServletRequest request,HttpServletResponse response) {
		   ModelAndView model = new ModelAndView();
		   model.addObject("sourceleadUrl", sourceleadUrl);
		   model.setViewName("leavemanagement/employeeCancelLeaves");
	       return model;
	}

	@PreAuthorize("hasAnyRole('ROLE_LEAVE_CONFIGURE')")
	@RequestMapping(value = {"/leaveTypeSummary", "/rest/leaveTypeSummary"}, method = RequestMethod.POST)
	@ResponseBody
	public LeaveType leaveTypeSummary(LeaveTypeDTO leaveTypeDTO, HttpServletRequest request) throws Exception
	{
		LeaveType leaveType = leaveTypeService.getLeaveTypeById(leaveTypeDTO.getLeaveTypeId());
		return leaveType;
	}
	
	/**
	 * Get Employees those who are requested for the aprrove leave
	 * @param orgLocationId
	 * @param userId
	 * @param request
	 * @return List<LeaveDTO>
	 * @throws Exception
	 */
	@PreAuthorize("hasAnyRole('ROLE_APPROVE_LEAVE')")
	@RequestMapping(value = {"/getEmployeeRequestedLeaves","/rest/getEmployeeRequestedLeaves"}, method = RequestMethod.POST)
	public  @ResponseBody ResponseList<LeaveDTO> getEmployeeRequestedLeaves(LeaveDTO leaveDTO,HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocations = orgLocationService.getOrgLocations(Long.valueOf(leaveDTO.getOrgLocationId()));
		ResponseList<LeaveDTO> requestedLeavesList = leaveService.getEmployeeRequestedLeavesBylocationId(leaveDTO,orgLocations);
		return requestedLeavesList;
	}
	

	@RequestMapping(value = {"/getLeavesListByLocationIdAndStatus","/rest/getLeavesListByLocationIdAndStatus"}, method = RequestMethod.GET)
	public @ResponseBody ResponseList<LeaveDTO> getLeavesListByLocationIdAndStatus(@RequestParam(value = "orgLocationId", required = false) Long orgLocationId, @RequestParam(value = "status", required = false) String status,@RequestParam(value = "userId", required = false) Long userId, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResponseList<LeaveDTO> leaveDtoList = leaveService.getLeavesListByLocationIdAndStatus(orgLocationId,status,userId);
		return leaveDtoList;
	}

	/**
	 * Approving the requested leave
	 * @param orgLocationId
	 * @param leaveId
	 * @param leavTypeId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@PreAuthorize("hasAnyRole('ROLE_APPROVE_LEAVE')")
	@RequestMapping(value = {"/approveRequestedLeave", "/rest/approveRequestedLeave"}, method = RequestMethod.GET)
	@ResponseBody
	public  boolean approveRequestedLeave(
			@RequestParam(value = "orgLocationId", required = false) Long orgLocationId,
			@RequestParam(value = "leaveId", required = false) String leaveId,
			@RequestParam(value = "leaveTypeId", required = false) String leaveTypeId, 
			@RequestParam(value = "approveStatus", required = false) String approveStatus,
			@RequestParam(value = "userId", required = false) Long userId,
			@RequestParam(value = "numberOfDays", required = false) String numberOfDays,
			@RequestParam(value = "reason", required = false) String reason,HttpServletRequest request)
			throws Exception {
		
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		User user = userService.getUserById(userId);
		boolean isApproved = leaveService.approveRequestLeave(orgLocations,user,leaveId,leaveTypeId,approveStatus,numberOfDays,reason);
		return isApproved;
		
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
	@PreAuthorize("hasAnyRole('ROLE_APPROVE_LEAVE')")
	@RequestMapping(value = { "/getRequestedAproveSummary","/rest/getRequestedAproveSummary" }, method = RequestMethod.GET)
	@ResponseBody
	public LeaveDTO getRequestedAproveSummary(@RequestParam(value = "leaveId", required = false) String leaveId,
			@RequestParam(value = "orgLocationId", required = false) Long orgLocationId, HttpServletRequest request)
			throws Exception {
		Leave leave = leaveService.getLeaveByLeavId(leaveId);
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		LeaveDTO leaveDto = new LeaveDTO();
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
				Employees employees = employeesService.getEmployeeByUserIdAndOrgLocationsId(leave.getUser().getId(), orgLocations.getId());
				if(employees !=null) {
					leaveDto.setEmpid(employees.getEmployeeId());	
				}
					 UserRole userRole = userRoleService.getUserRoleByUserId(leave.getUser().getId());
						leaveDto.setEmpType(userRole.getUserType());
			}
			durationSb.append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), leave.getFromDate()))
					.append(System.getProperty("line.separator"))
					.append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), leave.getToDate()));

			leaveDto.setDuration(durationSb.toString());
			leaveDto.setLeaveType(leave.getLeaveType().getName());
			leaveDto.setLeaveTypeId(leave.getLeaveType().getId());
			leaveDto.setStatus(leave.getStatus());
			try {
				int numberOfdays = commonUtil.getNumberOfDaysBetweenTwoDates(leave.getFromDate(), leave.getToDate());
				leaveDto.setNoOfDays(String.valueOf(numberOfdays));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			leaveDto.setBalanceleaves(leave.getLeaveType().getNumbefrOfLeavesPeryear());
			leaveDto.setFileUrl(leave.getFileUrl());
			leaveDto.setReason(leave.getReason());
			leaveDto.setCreatedDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),leave.getCreatedDate()));
			leaveDto.setFileName(leave.getFileName());
            if(leave.getRejectedReason() !=null) {
            	leaveDto.setRejectedReason(leave.getRejectedReason());
            }
           
			StringBuilder sbu = new StringBuilder();
			if(leave.getUser() != null){
			if(StringUtils.isNotBlank(leave.getUser().getLastName())){
				sbu.append(leave.getUser().getFirstName()).append(" ").append(leave.getUser().getLastName());
				leaveDto.setApprovedBy(sbu.toString());
			}else{
				sbu.append(leave.getUser().getFirstName());
				leaveDto.setApprovedBy(sbu.toString());
			}
		 }
			
			leaveDto.setUpdatedDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),leave.getUpdatedDate()));
			
			StringBuilder sbr = new StringBuilder();
			if(leave.getUser() != null){
				if(StringUtils.isNotBlank(leave.getUser().getLastName())){
					sbr.append(leave.getUser().getFirstName()).append(" ").append(leave.getUser().getLastName());
					leaveDto.setRejectedBy(sbr.toString());
				}else{
					sbr.append(leave.getUser().getFirstName());
					leaveDto.setRejectedBy(sbr.toString());
				}
			}
			StringBuilder sbc = new StringBuilder();
			if(leave.getUser() != null){
				if(StringUtils.isNotBlank(leave.getUser().getLastName())){
					sbc.append(leave.getUser().getFirstName()).append(" ").append(leave.getUser().getLastName());
					leaveDto.setCancelledBy(sbc.toString());
				}else{
					sbc.append(leave.getUser().getFirstName());
					leaveDto.setCancelledBy(sbc.toString());
				}
			 }


			List<String> aphlist = new ArrayList<>();
			for(LeaveApproveHierarchy laph1 : leave.getApproverHierarchyList()){
				StringBuilder sb1 = new StringBuilder();
				try {
					UserRole role = userRoleService.getUserRoleByUserIdAndLocationId(laph1.getApproverUserId().getId(), orgLocations.getId());
					if(role != null){
						if(StringUtils.isNotBlank(laph1.getApproverUserId().getLastName())){
							aphlist.add(sb1.append(role.getUserType()).append(" ( ").append(laph1.getApproverUserId().getFirstName()).append(" ").append(laph1.getApproverUserId().getLastName()).append(" )").toString());					
						}else{
							aphlist.add(sb1.append(role.getUserType()).append(" ( ").append(laph1.getApproverUserId().getFirstName()).append(" )").toString());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			leaveDto.setApproverUsers(String.join(",", aphlist)); 
			int nuberOfLeavestaken = leaveDao.getNumberOfLeavesTakenByUser(leave.getUser().getId(),leave.getLeaveType().getId());
			leaveDto.setLeavesTaken(String.valueOf(nuberOfLeavestaken));
			int thisMonthLeaves = leaveDao.getThisMonthLeavesTakenByUser(leave.getUser().getId(),leave.getLeaveType().getId());
			leaveDto.setThisMonthLeaves(String.valueOf(thisMonthLeaves));

		}
		return leaveDto;
	}
	
	@PreAuthorize("hasAnyRole('ROLE_CANCEL_LEAVE')")
	@RequestMapping(value = {"/storeCancelReason"})
	@ResponseBody
	public  boolean storeCancelReason(@RequestParam(value = "id", required = false) String id,@RequestParam(value = "reason", required = false) String reason,@RequestParam(value = "userId", required = false) Long userId) throws Exception
	{
		boolean isCancelled = false;
		LeaveManagementHistory leaveManagementHistory = new LeaveManagementHistory();
		Leave leave = leaveService.getLeaveByLeavId(id);
		leaveManagementHistory.setFromStage(leave.getStatus());
		User user = userService.getUserById(userId);
		 if(leave !=null) {
			 leave.setDeleteReason(reason);
			 leave.setCancelledBy(user);
			 leave.setStatus(Constants.LEAVE_CANCELLED);
			 leaveDao.saveLeave(leave);
			 isCancelled = true;
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
			leaveService.LeaveCancelEmailToUser(user,leaveManagementHistory,reason);
		return isCancelled;
	}	
	
	@PreAuthorize("hasAnyRole('ROLE_APPROVE_LEAVE')")
	@RequestMapping(value = { "/getEmployeeLeavesList", "/rest/getEmployeeLeavesList" }, method = RequestMethod.GET)
	@ResponseBody
	public List<LeaveReportsDTO> getEmployeeLeavesList(@RequestParam(value = "orgLocationId", required = true) Long orgLocationId, @RequestParam(value = "userId", required = true) String userId, @RequestParam(value = "department", required = false) String department, @RequestParam(value = "leaveDateStr", required = false) String leaveDate) throws Exception
	{
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		List<LeaveReportsDTO> leavesList = leaveService.getEmployeeLeavesByApproverIdAndStatus(orgLocations, userId, Constants.APPROVED, department, leaveDate);
		return leavesList;
	}
	
	/**
	 * Get top leave takers by location
	 * @param leaveDTO
	 * @param req
	 * @param res
	 * @return ResponseList<LeaveDTO>
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getTopLeavetakers", "/rest/getTopLeavetakers" }, method = RequestMethod.POST)
	public @ResponseBody ResponseList<LeaveDTO> getTopLeavetakers(LeaveDTO leaveDTO, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		OrgLocations orgLocation = orgLocationService.getOrgLocations(Long.parseLong(leaveDTO.getOrgLocationId()));
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
			}
			StringBuilder sb = new StringBuilder();
			if (StringUtils.isNotBlank(leave.getUser().getLastName())) {
				sb.append(leave.getUser().getFirstName()).append(" ").append(leave.getUser().getLastName());
			} else {
				sb.append(leave.getUser().getFirstName());
			}
			leaveDto.setEmpName(sb.toString());
			leaveDto.setStatus(leave.getStatus());
			totalLeaves = totalLeaves + Integer.parseInt(leave.getNumberOfDays());
			leaveDto.setNoOfDays(String.valueOf(totalLeaves));
			reportMap.put(leave.getUser().getId(), leaveDto);
		}

		for (Map.Entry<Long, LeaveDTO> entry : reportMap.entrySet()) {
			leaveDtoList.add(entry.getValue());
		}
		leaveDtoResponse.setData(leaveDtoList);
		leaveDtoResponse.setRecordsFiltered(response.getRecordsFiltered());
		leaveDtoResponse.setRecordsTotal(response.getRecordsTotal());
		leaveDtoResponse.setError(response.getError());
		return leaveDtoResponse;
	}

	@RequestMapping(value = "/validateRestrictDate")
	@ResponseBody
	public boolean validateRestrictDate(@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate,
			@RequestParam(value = "orgLocationId", required = false) Long orgLocationId,
			@RequestParam(value = "roleId", required = false) Long roleId,
			@RequestParam(value = "leaveId", required = false) String leaveId) throws Exception {

		boolean isValid = false;
		List<RestrictDay> restrictDayList = new ArrayList<>();
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		if(StringUtils.isNotBlank(leaveId)){
			Leave leave = leaveService.getLeaveByLeavId(leaveId);
			fromDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), leave.getFromDate());
			toDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), leave.getToDate());
		}
		List<RestrictDay> restDay = restrictDayService.validateRestrictDayDates(fromDate,toDate,orgLocations);

		 Set<Long> roleIds = new HashSet<>();
		for (RestrictDay resDay : restDay) {
			if (!"Y".equalsIgnoreCase(resDay.getApplicableToAll())) {
				for (LeaveRole leaveRole : resDay.getRestrictDayRolesList()) {
					roleIds.add(leaveRole.getRoleId().getId());
				}
			} else if (roleIds.contains(roleId)) {
				restrictDayList.add(resDay);
			} else {
				restrictDayList.add(resDay);
			}
		}
		if(restrictDayList != null && restrictDayList.size()>0) {
			isValid = true;
		}
		if(!isValid) {
			List<Holiday> holiDayList = holidayService.validateHoliDayDates(fromDate,toDate,orgLocations);
			if(holiDayList != null && holiDayList.size()>0) {
				isValid = true;
			}
		}
		
		return isValid;
	}
	

	@RequestMapping(value = {"/totalCountsForEmployeeLeaves","/rest/totalCountsForEmployeeLeaves"}, method=RequestMethod.GET)
	@ResponseBody
	public LeaveDTO totalCountsForEmployeeLeaves(@RequestParam(value = "orgLocationId", required = false) Long orgLocationId,@RequestParam(value = "userId", required = false) Long userId) throws Exception {
		return leaveService.getAllEmployeesLeavesCounts(orgLocationId,userId);
	}
	
	@RequestMapping(value = {"/totalCountsForSelfLeaves","/rest/totalCountsForSelfLeaves"}, method=RequestMethod.GET)
	@ResponseBody
	public LeaveDTO totalCountsForSelfLeaves(@RequestParam(value = "orgLocationId", required = false) Long orgLocationId,@RequestParam(value = "userId", required = false) Long userId) throws Exception {
		return leaveService.getAllSelfLeavesCounts(orgLocationId,userId);

	}
	/**
	 * Get leave takers list for barchart
	 * @param leaveDTO
	 * @param request
	 * @param response
	 * @return List<LeaveDTO> 
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getLeaveTakersBarChat", "/rest/getLeaveTakersBarChat" }, method=RequestMethod.GET)
	@ResponseBody
	public List<LeaveDTO>  getLeaveTakersBarChat(LeaveDTO leaveDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {

		OrgLocations orgLocation = orgLocationService.getOrgLocations(Long.parseLong(leaveDTO.getOrgLocationId()));
		List<LeaveDTO> leavDtoList = leaveService.getLeaveTakersBarChat(orgLocation,leaveDTO);
		return leavDtoList;
	}
	
	/**
	 * Withdraw leave by employee
	 * @param orgLocationId
	 * @param leaveId
	 * @param reason
	 * @return boolean
	 * @throws Exception
	 */
	@PreAuthorize("hasAnyRole('ROLE_APPLY_LEAVE')")
	@RequestMapping(value = "/withdrawLeave")
	@ResponseBody
	public boolean withdrawLeave(@RequestParam(value = "orgLocationId", required = false) Long orgLocationId,
			@RequestParam(value = "leaveId", required = false) String leaveId,
			@RequestParam(value = "userId", required = false) Long userId,
			@RequestParam(value = "reason", required = false) String reason) throws Exception {
		
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		User user = userService.getUserById(userId);
		boolean isValid = false;
		isValid = leaveService.getWithdrawLeave(orgLocations,leaveId,user,reason);
		
		return isValid;

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
	@RequestMapping(value = {"/getLeaveTypeReport","/rest/getLeaveTypeReport"}, method=RequestMethod.GET)
	public  List<LeaveTypeDTO> getLeaveTypeReport(@RequestParam(value = "orgLocationId", required = false) Long orgLocationId,
			@RequestParam(value = "userId", required = false) Long userId,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);   
		User user = userService.getUserById(userId);
		List<LeaveType> leaveTypeList = leaveTypeService.getLeaveTypeListByLocationId(String.valueOf(orgLocations.getId()));
		
		   List<LeaveTypeDTO> leaveTypeDTOList = new ArrayList<>();
		   if(leaveTypeList !=null && !leaveTypeList.isEmpty()) {
				for(LeaveType type : leaveTypeList) {
					int pendingCounts = 0;
				    int approvedCounts = 0;
					int rejectedCounts = 0;
					int cancelCounts = 0;
					int noOfDays = 0;
					LeaveTypeDTO leaveTypeDto = new LeaveTypeDTO();
					List<Leave> totalleavesTaken = leaveService.getLeavesListByTypeIdAndLocationIdAndUserId(type.getId(),orgLocations.getId(), user.getId());
					if(totalleavesTaken != null && totalleavesTaken.size() > 0) {
						for (Leave leave : totalleavesTaken) {
							if(Constants.PENDING.equalsIgnoreCase(leave.getStatus())) {
								pendingCounts = pendingCounts + 1;
							}
							if(Constants.APPROVED.equalsIgnoreCase(leave.getStatus())) {
								approvedCounts = approvedCounts + 1;
								
								noOfDays = noOfDays + Integer.valueOf(leave.getNumberOfDays());
								if(noOfDays > Integer.valueOf(type.getNumbefrOfLeavesPeryear())) {
									int totalLeaves =  Integer.valueOf(type.getNumbefrOfLeavesPeryear()) - noOfDays;
									leaveTypeDto.setNumberOfDaysCount(String.valueOf(totalLeaves));
								}else {
									leaveTypeDto.setNumberOfDaysCount(String.valueOf(noOfDays));
								}
								
							}
							if(Constants.REJECTED.equalsIgnoreCase(leave.getStatus())) {
								rejectedCounts = rejectedCounts + 1;
							}
							if(Constants.LEAVE_CANCELLED.equalsIgnoreCase(leave.getStatus())) {
								cancelCounts = cancelCounts + 1;
							}
							
					 }
						
						leaveTypeDto.setName(type.getName());
						leaveTypeDto.setLeaveTypeId(type.getId());
						leaveTypeDto.setNoOfLeavesPerYear(type.getNumbefrOfLeavesPeryear());
						leaveTypeDto.setApprovedCount(approvedCounts);
					    leaveTypeDto.setRejectedCount(rejectedCounts);
					    leaveTypeDto.setPendingCount(pendingCounts);
					    leaveTypeDto.setCancelCount(cancelCounts);
					    leaveTypeDTOList.add(leaveTypeDto);					
					}
					}
		   }
		   
		    
		return leaveTypeDTOList;
	}
	
	@RequestMapping(value = {"/validateMaxLeave","/rest/validateMaxLeave"}, method = RequestMethod.GET)
	public  @ResponseBody boolean validateMaxLeave(@RequestParam(value = "leaveTypeId", required = false) String leaveTypeId,@RequestParam(value = "orgLocationId", required = false) Long orgLocationId,@RequestParam(value = "userId", required = false) Long userId) throws Exception
	{
		boolean isvalidate = false;
		int totalLeavesApplied = 0;
		int totalLeaves = 0;
		List<Leave> leaveList = leaveService.getLeavesListByTypeIdAndLocationIdAndUserId(leaveTypeId, orgLocationId,userId);
		for (Leave leave : leaveList) {
			if(Constants.APPROVED.equalsIgnoreCase(leave.getStatus())) {
				totalLeavesApplied = totalLeavesApplied + Integer.valueOf(leave.getNumberOfDays());
			}
			totalLeaves = Integer.valueOf(leave.getLeaveType().getNumbefrOfLeavesPeryear());
		}
		
		if(totalLeavesApplied > totalLeaves) {
			isvalidate = true;
		}
		
		return isvalidate;
		
	}
	
	
}
