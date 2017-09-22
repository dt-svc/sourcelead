package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OptionsDao;
import com.bis.sourcelead.dto.AddUserDTO;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.ClientDTO;
import com.bis.sourcelead.dto.OptionsDTO;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.LeaveRole;
import com.bis.sourcelead.entity.Option;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.entity.AccountDeductionsDetails;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.OptionsService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
public class LeaveManagementRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(LeaveManagementRestController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	DepartmentService departmentService;
	
	@Autowired
	RolesService rolesService;
	
	@Autowired
	OptionsService optionsService;
	
	@Autowired
	OptionsDao optionsDao;
	
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
	 */
	@RequestMapping(value = "/applyLeave")
	public ModelAndView applyLeaveJsp(HttpServletRequest request,HttpServletResponse response) {
		   ModelAndView model = new ModelAndView();
		   model.addObject("sourceleadUrl", sourceleadUrl);
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
	 */
	@RequestMapping(value = "/addLeave")
	public ModelAndView addLeaveJsp(HttpServletRequest request,HttpServletResponse response) {
		   ModelAndView model = new ModelAndView();
		   model.addObject("sourceleadUrl", sourceleadUrl);
		   model.setViewName("leavemanagement/addLeave");
	       return model;
	}
	
	/**
	 * returns view leave  jsp
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/viewLeaves")
	public ModelAndView viewLeavesJsp(HttpServletRequest request,HttpServletResponse response) {
		   ModelAndView model = new ModelAndView();
		   model.addObject("sourceleadUrl", sourceleadUrl);
		   model.setViewName("leavemanagement/viewLeave");
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
//	/
	
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
	
}
