package com.bis.sourcelead.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.LeaveReportView;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
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
			HttpServletResponse response) throws Exception {

		ModelAndView model = new ModelAndView();
		Collection<Roles> rolesList = null;
		 List<Department> departmentList;
		 Response<String> respons = new Response<>();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		Long roleHierarchyId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "roleHierarchyId");
		try {
			rolesList = rolesService.getUserRolesListByModuleAndLoginUserRoleHierarchy(org,orgLocations.getId(),roleHierarchyId);
			departmentList = departmentService.getDepartmentByOrgLocationId(orgLocations.getId());
			List<LeaveType> leaveTypeNamesList = leaveTypeService.getLeaveTypeListByLocationId(orgLocations.getId().toString());
			String date=commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date());
			model.addObject("rolesList", rolesList);
			model.addObject("departmentList", departmentList);
		    model.addObject("departmentListSize", departmentList.size());
		    model.addObject("leaveTypeNamesList", leaveTypeNamesList);
			model.addObject("fromDate", date);
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.setViewName("reports/daywiseLeaveReport");
		}catch (Exception e) {
			respons.setResponse(Result.AWKWARD.name());
			respons.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
		
//		Date date=commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), new Date());
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
		LeaveReportView leaveReportView = new LeaveReportView();
		List<Long> departmentsList = new ArrayList<>();
		List<Long> leaveTypeNamesList = new ArrayList<>();
		List<String> employmentTypeList = new ArrayList<>();
		ResponseList<LeaveReportView> leaveReportsList=null;
		try{
		String[] tableColumns = {"name", "userDeptName", "employmentType", "leaveTypeName", "type", "duration", "fromDate", "toDate","reason","approver","status"};

		   int sortColumnIndex = 1; //by default sorting using updated date
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
				if(orgDepartments != null && !orgDepartments.isEmpty()) {
					String[] orgDepts = orgDepartments.split(",");
					for(int i=0; i < orgDepts.length; i++) {
						departmentsList.add(Long.parseLong(orgDepts[i]));
					}
				}
				if(leaveTypeNames != null && !leaveTypeNames.isEmpty()) {
					String[] leaveTypeName = leaveTypeNames.split(",");
					for(int i=0; i < leaveTypeName.length; i++) {
						leaveTypeNamesList.add(Long.parseLong(leaveTypeName[i]));
					}
				}
				if(employmentType !=null && !employmentType.isEmpty()){
					String[] empType = employmentType.split(",");
					for(int i=0; i < empType.length; i++) {
						employmentTypeList.add(empType[i]);
					employmentTypeList.add(employmentType);
				}
					}
		leaveReportsList = leaveService.getLeaveReportsOrglocationIdAndFromToDate( leaveReportView,orgLocation.getOrg().getId(),orgLocation.getId(),fromDate,searchValue,userRoleList,departmentsList,employmentTypeList,leaveTypeNamesList);
		leaveReportsList.getData().stream().forEach(u -> {
    		   u.setFromDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), u.getFromDate()));
    		   u.setToDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), u.getToDate()));
    	   });
		
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return leaveReportsList;
//    	   leaveReportsList.setData(leaveList);
		
		
}
	
	@RequestMapping(value = "/getExportDailyWiseLeaveReport", method = RequestMethod.GET)
	@ResponseBody
	public void getExportDailyWiseLeaveReport(@RequestParam(value="fromDate", required=false) String fromDate,
			@RequestParam(value="exportType", required=false) String exportType,
			@RequestParam(value="searchValue", required=false) String searchValue,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LeaveReportView leaveReportView = new LeaveReportView();
		List<Long> departmentsList = new ArrayList<>();
		List<Long> leaveTypeNamesList = new ArrayList<>();
		List<String> employmentTypeList = new ArrayList<>();
		List<Long> userRoleList = new ArrayList<>();
		 Workbook createExcelworkbook =  new XSSFWorkbook();
		 StringBuilder excelSB = new StringBuilder();
         
        OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
 		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
 		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		 Sheet createExcelsheet = createExcelworkbook.createSheet(excelSB.append("dailyWiseLeaveReport ").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString());
			ResponseList<LeaveReportView> leaveReportsList = leaveService.getLeaveReportsOrglocationIdAndFromToDate( leaveReportView,orgLocations.getOrg().getId(),orgLocations.getId(),fromDate,searchValue,userRoleList,departmentsList,employmentTypeList,leaveTypeNamesList);
			leaveReportsList.getData().stream().forEach(u -> {
	    		   u.setFromDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), u.getFromDate()));
	    		   u.setToDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), u.getToDate()));
	    		   u.setAppliedOnDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), u.getAppliedOn()));
	    		   u.setApprovedOnDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), u.getAppliedOn()));
	    		   
	    	   });
			
			File file = null;
	 		if(!"pdf".equalsIgnoreCase(exportType)){
	 			createExcelsheet = leaveService.getDailyWiseLeaveExcelFile(createExcelsheet,createExcelworkbook,leaveReportsList,orgLocations);
	 			  StringBuilder templateSB = new StringBuilder();
	 	 		  file = File.createTempFile(templateSB.append("Day_Wise_Leave_Report").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".xls");
	 	 		  file.setReadable(true);
	 	 		  
	 			 FileOutputStream fos = new FileOutputStream(file);
	 			 try {
	 				createExcelworkbook.write(fos);
	 				fos.close();
	 			} catch (IOException e) {
	 				e.printStackTrace();
	 			}	 	
	 		}else{
	 			file = leaveService.getDailyWiseLeavePdfFile(leaveReportsList,orgLocations);
	 		
	 		}
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
				if(file!= null){
					file.delete();
				}
	}
}
