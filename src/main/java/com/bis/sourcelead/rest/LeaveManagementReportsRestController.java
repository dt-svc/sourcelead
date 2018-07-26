package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
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

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.LeaveReportView;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.LeaveService;
import com.bis.sourcelead.service.LeaveTypeService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/app")
public class LeaveManagementReportsRestController {
	private static final Logger logger = LoggerFactory.getLogger(LeaveManagementReportsRestController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Value("${static.path}")
	private String staticPath;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	TimeZoneService timeZoneService;
	@Autowired
	LeaveService leaveService;
	@Autowired
	RolesService rolesService;
	
	@Autowired
	DepartmentService departmentService;
	@Autowired
	LeaveTypeService leaveTypeService;
	
	@RequestMapping(value = "/daywiseLeaveReport")
	public ModelAndView approvedLeavesJsp(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("reports/daywiseLeaveReport");
		Collection<Roles> rolesList = null;
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		Long roleHierarchyId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "roleHierarchyId");
		try {
			rolesList = rolesService.getUserRolesListByModuleAndLoginUserRoleHierarchy(org,orgLocations.getId(),roleHierarchyId);
		} catch (Exception e) {
			logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
		}
		model.addObject("rolesList", rolesList);
		 List<Department> departmentList;
		try {
			departmentList = departmentService.getDepartmentByOrgLocationId(orgLocations.getId());
			 model.addObject("departmentList", departmentList);
		     model.addObject("departmentListSize", departmentList.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			List<LeaveType> leaveTypeNamesList = leaveTypeService.getLeaveTypeListByLocationId(orgLocations.getId().toString());
			 model.addObject("leaveTypeNamesList", leaveTypeNamesList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value = "/leaveBalancesReport")
	public ModelAndView leaveBalances(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("reports/leaveBalancesReports");
		return model;
	}
	
	 
	
	@RequestMapping(value = "/getDaywiseLeaveReportList", method = RequestMethod.GET)
	@ResponseBody
	public ResponseList<LeaveReportView> getDaywiseLeaveReportList(@RequestParam(value="fromDate", required=false) String fromDate,
			@RequestParam(value="searchValue", required=false) String searchValue,
			@RequestParam(value="userRole", required=false) String userRole,
			@RequestParam(value="orgDepartments", required=false) String orgDepartments,
			@RequestParam(value="employmentType", required=false) String employmentType,
			@RequestParam(value="leaveTypeNames", required=false) String leaveTypeNames,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
//		ResponseList<LeaveReportView> leaveReportsList =  new ResponseList<>();
		LeaveReportView leaveReportView = new LeaveReportView();
		List<Long> departmentsList = new ArrayList<>();
		String[] tableColumns = {"name", "userDeptName", "employmentType", "leaveTypeName", "type", "duration", "fromDate", "toDate","reason","approver","status"};

		  int sortColumnIndex = 0; //by default sorting using updated date
				String sortColumnOrder = "desc"; //by default descending order
				if(leaveReportView.getOrder() != null && !leaveReportView.getOrder().isEmpty()) {
					sortColumnIndex = Integer.parseInt(leaveReportView.getOrder().get(0).get("column"));
					sortColumnOrder = leaveReportView.getOrder().get(0).get("dir");
				}
				leaveReportView.setSortColumn(tableColumns[sortColumnIndex]);
				leaveReportView.setSortOrder(sortColumnOrder);
				List<Long> userRoleList = new ArrayList<>();
				if(userRole != null && !userRole.isEmpty()) {
					String[] userRoles = userRole.split(",");
					for(int i=0; i < userRoles.length; i++) {
						userRoleList.add(Long.parseLong(userRoles[i]));
					}
				}
				
		ResponseList<LeaveReportView> leaveReportsList = leaveService.getLeaveReportsOrglocationIdAndFromToDate( leaveReportView,orgLocation.getOrg().getId(),orgLocation.getId(),fromDate,searchValue,userRoleList,departmentsList);
		leaveReportsList.getData().stream().forEach(u -> {
    		   u.setFromDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), u.getFromDate()));
    		   u.setToDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), u.getToDate()));
    	   });
		
		
		if(orgDepartments != null && !orgDepartments.isEmpty()) {
			String[] orgDepts = orgDepartments.split(",");
			for(int i=0; i < orgDepts.length; i++) {
				departmentsList.add(Long.parseLong(orgDepts[i]));
			}
		}
	
//    	   leaveReportsList.setData(leaveList);
		
		return leaveReportsList;
}
}
