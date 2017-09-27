package com.bis.sourcelead.rest;




import javax.measure.unit.Dimension.Model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.dto.ShiftDTO;
import com.bis.sourcelead.entity.Shift;
import com.bis.sourcelead.service.ShiftService;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


import com.bis.sourcelead.dao.HolidayDAO;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.LeaveTypeDao;
import com.bis.sourcelead.dao.OptionsDao;
import com.bis.sourcelead.dto.LeaveTypeDTO;
import com.bis.sourcelead.dto.LeaveTypeDTO;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.LeaveService;
import com.bis.sourcelead.service.LeaveTypeService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.dao.OptionsDao;
import com.bis.sourcelead.dto.AddUserDTO;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.ClientDTO;
import com.bis.sourcelead.dto.LeaveDTO;
import com.bis.sourcelead.dto.HolidayDTO;
import com.bis.sourcelead.dto.OptionsDTO;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.Holiday;
import com.bis.sourcelead.entity.LeaveRole;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.Option;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.payroll.entity.AccountDeductionsDetails;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.LeaveTypeService;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.HolidayService;
import com.bis.sourcelead.service.OptionsService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SourceLeadSessionManager;


@RestController
public class LeaveManagementRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(LeaveManagementRestController.class);
	
	@Autowired
	ShiftService shiftService;
	
	
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
	DepartmentService departmentService;
	
	@Autowired
	RolesService rolesService;

	@Autowired
	OptionsService optionsService;
	
	@Autowired
	OptionsDao optionsDao;
	
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
	 */
	@RequestMapping(value = "/leaveManagementReports")
	public ModelAndView leaveManagementReportsJsp(HttpServletRequest request,HttpServletResponse response) {
		   ModelAndView model = new ModelAndView();
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
	@RequestMapping(value = "/applyLeave")
	public ModelAndView applyLeaveJsp(HttpServletRequest request,HttpServletResponse response) throws Exception {
		   ModelAndView model = new ModelAndView();
		   OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		   Long roleId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRoleId");
		   List<User> usersLis = userService.getAllUsersByOrgLocationCode(orgLocations.getId());
		   model.addObject("sourceleadUrl", sourceleadUrl);
		   model.addObject("usersLis", usersLis);
		   List<LeaveType> leaveTypeList = leaveTypeService.getLeaveTypeListByLocationId(String.valueOf(orgLocations.getId()));
		   if(leaveTypeList !=null && !leaveTypeList.isEmpty()) {
			   model.addObject("leaveTypeList", leaveTypeList);
		   }
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
	
	/**
	 * returns leave configuration jsp
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
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
	@RequestMapping(value = "/addRestrictDay")
	public ModelAndView addRestrictDayJsp(HttpServletRequest request,HttpServletResponse response) {
		   ModelAndView model = new ModelAndView();
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
	@RequestMapping(value = "/viewOptions")
	public ModelAndView viewOptionsJsp(HttpServletRequest request,HttpServletResponse response) {
		   ModelAndView model = new ModelAndView();
		   model.addObject("sourceleadUrl", sourceleadUrl);
		   model.setViewName("leavemanagement/viewOptions");
	       return model;
	}
	


	@RequestMapping(value = "/saveShift")
	public String saveShift(ShiftDTO shiftDTO) throws Exception {
		shiftService.saveShift(shiftDTO);
	       return "success";
	}
	
	@RequestMapping(value = "/getShiftList")
	public List<Shift> getShiftList(@RequestParam (value = "orgLocationId", required = true )Long orgLocationId) throws Exception{
	List<Shift> shift = shiftService.getShiftList(orgLocationId);
	return shift;
	}
	
	@RequestMapping(value = "/editShift" ,method = RequestMethod.POST)
	public ModelAndView editShift(@RequestParam (value = "shiftId", required = true )String shiftId,HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView model = new ModelAndView();
		List<Shift> shift = shiftService.getShiftById(shiftId);
		model.addObject("shifts", shift);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("leavemanagement/addShift");
		return model;
	}

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
	
	@RequestMapping(value = "/getHolidaysList", method = RequestMethod.GET)
	public @ResponseBody List<HolidayDTO> getHolidaysList(@RequestParam(value = "orgLocationId", required = false) Long orgLocationId, @RequestParam(value = "userId", required = false) Long userId,HttpServletRequest request) throws Exception
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
	@RequestMapping(value = "/saveNewLeaveType")
	public @ResponseBody String saveNewLeaveType(LeaveTypeDTO leaveTypeDTO, HttpServletRequest request) throws Exception
	{
		LeaveType leaveType = leaveTypeService.addLeaveType(leaveTypeDTO);
		return "success";
	}
	

	/**
	 * @author Pavan Jampana
	 * 
	 * @param leaveTypeDTO
	 * @param request
	 * @return leave type list
	 * @throws Exception
	 */
	@RequestMapping(value = "/getLeaveTypesByLocationid")
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
	@RequestMapping(value = "/saveLeaveOption", method = RequestMethod.POST)
	@ResponseBody
	public boolean saveLeaveOption(OptionsDTO optionsDTO, HttpServletRequest request) throws Exception {

		boolean isInserted = false;
		Option options = optionsService.saveLeaveOptions(optionsDTO);
		if (options != null) {
			isInserted = true;
		}
		return isInserted;
	}
	

	@RequestMapping(value = {"/viewLeaveOption", "/rest/viewLeaveOption"}, method = RequestMethod.GET)
	@ResponseBody
	public  List<OptionsDTO> getDeductionsDetailsbyId(@RequestParam(value = "orgLocationId", required = false) Long orgLocationId, @RequestParam(value = "userId", required = false) Long userId,HttpServletRequest request) throws Exception
	{
		List<OptionsDTO> optionsDTOList = null;
		 optionsDTOList = optionsService.getOptionsListByLocationId(orgLocationId);
		 if(optionsDTOList !=null) {
			 return optionsDTOList;
		 }
		return optionsDTOList;
	}
	
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
	

	
	
		/**
		 * saving leave optipons
		 * 
		 * @param optionsDTO
		 * @param request
		 * @return boolean
		 * @throws Exception
		 */
		@RequestMapping(value = "/applyLeave", method = RequestMethod.POST)
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
		 LeaveType options = leaveTypeService.getLeaveTypeById(leaveTypeId);
		 if(options !=null) {
			 leavePolicy = options.getPolicyDescription();
		 }
		return leavePolicy;
	}
	

	@RequestMapping(value = {"/deleteLeaveType", "/rest/deleteLeaveType"}, method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteLeaveType(LeaveTypeDTO leaveTypeDTO, HttpServletRequest request) throws Exception
	{
		boolean isDeleted = false;
		LeaveType leaveType = leaveTypeService.getLeaveTypeById(leaveTypeDTO.getLeaveTypeId());
		 if(leaveType !=null) {
			 leaveType.setStatus(Constants.RECORD_INACTIVE.toString());
			 leaveType.setDeleteReason(leaveTypeDTO.getDeleteReason());
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

	@RequestMapping(value = "/editHoliday")
	public ModelAndView editHoliday(@RequestParam(value="holidayId", required=false) String holidayId,HttpServletRequest request,HttpServletResponse response) throws Exception {
		   ModelAndView model = new ModelAndView();
		   Holiday holidays = holidayService.getHolidaysById(holidayId);
		   model.addObject("holidays", holidays);
		   model.addObject("sourceleadUrl", sourceleadUrl);
		   model.setViewName("leavemanagement/addHoliday");
	       return model;
	}
	
	@RequestMapping(value = "/deleteHoliday", method = RequestMethod.GET)
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
	
	
	/**
	 * returns add Employee Requested Leaves  jsp
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
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
	@RequestMapping(value = "/employeeCancelLeaves")
	public ModelAndView employeeCancelLeavesLeaves(HttpServletRequest request,HttpServletResponse response) {
		   ModelAndView model = new ModelAndView();
		   model.addObject("sourceleadUrl", sourceleadUrl);
		   model.setViewName("leavemanagement/employeeCancelLeaves");
	       return model;
	}

	@RequestMapping(value = {"/leaveTypeSummary", "/rest/leaveTypeSummary"}, method = RequestMethod.POST)
	@ResponseBody
	public LeaveType leaveTypeSummary(LeaveTypeDTO leaveTypeDTO, HttpServletRequest request) throws Exception
	{
		LeaveType leaveType = leaveTypeService.getLeaveTypeById(leaveTypeDTO.getLeaveTypeId());
		return leaveType;
	}
	
	@RequestMapping(value = "/getEmployeeRequestedLeaves", method = RequestMethod.GET)
	public  @ResponseBody List<LeaveDTO> getEmployeeRequestedLeaves(@RequestParam(value = "orgLocationId", required = false) Long orgLocationId,@RequestParam(value = "userId", required = false) String userId,HttpServletRequest request) throws Exception
	{
		List<LeaveDTO> requestedLeavesList = leaveService.getEmployeeRequestedLeavesBylocationId(orgLocationId,userId);
		return new ArrayList<LeaveDTO>();
	}
	


}
