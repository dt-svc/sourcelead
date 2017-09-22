package com.bis.sourcelead.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.LeaveTypeDTO;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.LeaveTypeService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
public class LeaveManagenebtRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(LeaveManagenebtRestController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	LeaveTypeService leaveTypeService;
	
	@Autowired
	DepartmentService departmentService;
	
	@Autowired
	RolesService rolesService;
	
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
	 * @throws Exception 
	 */
	@RequestMapping(value = "/addLeaveType")
	public ModelAndView addLeaveJsp(LeaveTypeDTO leaveTypeDTO, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		Long roleHierarchyId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "roleHierarchyId");
		List<Department> departmentList = departmentService.getDepartmentByOrgLocationIdAndStatus(orgLocations.getId(),Constants.RECORD_ACTIVE.toString());
		List<Roles> rolesList = rolesService.getUserRolesListByModuleAndLoginUserRoleHierarchy(org, orgLocations.getId(), roleHierarchyId);
		
		if(leaveTypeDTO != null && leaveTypeDTO.getLeaveTypeId() != null){
			LeaveType leaveType = leaveTypeService.getLeaveTypeById(leaveTypeDTO.getLeaveTypeId());
			if(leaveType != null){
				model.addObject("leaveType", leaveType);
			}
		}
		
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
	 * returns add RestrictDay  jsp
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/addOptions")
	public ModelAndView addOptionsJsp(HttpServletRequest request,HttpServletResponse response) {
		   ModelAndView model = new ModelAndView();
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
}
