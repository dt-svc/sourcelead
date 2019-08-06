package com.bis.sourcelead.rest.reportsRestController;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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

import com.bis.sourcelead.dto.DayWiseLeaveReportDTO;
import com.bis.sourcelead.dto.HrLeaveReportDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.entity.LeaveReportView;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.reportsDTO.LeaveBalanceReportDTO;
import com.bis.sourcelead.service.AssociateBalanceLeaveService;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.LeaveService;
import com.bis.sourcelead.service.LeaveTypeService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.LeaveManagementUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.mysql.fabric.xmlrpc.base.Array;

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
	
	@Autowired
	UserService userService;
	
	@Autowired
	AssociateBalanceLeaveService associateBalanceLeaveService;
	
	@Autowired
	LeaveManagementUtil leaveManagementUtil;
	
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
	
	@RequestMapping(value = "/leaveReport")
	public ModelAndView leaveReport(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("reports/leaveReport");
		return model;
	}

	@RequestMapping(value = "/topLeaveTakenReports")
	public ModelAndView topLeaveTakenReports(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);	
		model.setViewName("reports/topLeaveTakenReports");
		 List<Department> departmentList = departmentService.getDepartmentByOrgLocationId(orgLocation.getId());
		    model.addObject("departmentList", departmentList);
		    model.addObject("departmentListSize", departmentList.size());
			Long roleHierarchyId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "roleHierarchyId");
			Collection<Roles> rolesList = null;
			try {
				rolesList = rolesService.getUserRolesListByModuleAndLoginUserRoleHierarchy(org,orgLocation.getId(),roleHierarchyId);
			} catch (Exception e) {
				logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
			}
			
			model.addObject("rolesList", rolesList);
			List<LeaveType> leaveTypeList = leaveTypeService.getLeaveTypeListByLocationIdAndStatus(String.valueOf(orgLocation.getId()));
			model.addObject("leaveTypeList", leaveTypeList);
		return model;
	}


	
	@RequestMapping(value="/leaveReportsList",method= RequestMethod.POST)
	@ResponseBody
	public ResponseList<LeaveReportView> getDayEntriesList(HrLeaveReportDTO hrLeaveReportDTO,HttpServletRequest request) throws Exception {
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		orgLocation.setOrg(org);
		LeaveReportView leaveReportView = new LeaveReportView();
		ResponseList<LeaveReportView> leaveReportsList=null;

	    String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
	    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");


		try{
			String[] tableColumns = {"id", "name", "userDeptName", "leaveTypeName","leaveTypeBillable", "fromDate", "toDate", "appliedOn", "status","approver","approvedOn"};

			   int sortColumnIndex = 1; //by default sorting using updated date
			   String sortColumnOrder = "desc"; //by default descending order
					if(hrLeaveReportDTO.getOrder() != null && !hrLeaveReportDTO.getOrder().isEmpty()) {
						sortColumnIndex = Integer.parseInt(hrLeaveReportDTO.getOrder().get(0).get("column"));
						sortColumnOrder = hrLeaveReportDTO.getOrder().get(0).get("dir");
					}
					hrLeaveReportDTO.setSortColumn(tableColumns[sortColumnIndex]);
					hrLeaveReportDTO.setSortOrder(sortColumnOrder);
					hrLeaveReportDTO.setLoginUserId(loginUser.getId());
				
					
			leaveReportsList = leaveService.getLeaveReportsOrglocationIdAndFromDateToDate(hrLeaveReportDTO,orgLocation.getOrg().getId(),orgLocation.getId(),loginUserRole);
			List<LeaveReportView> list = leaveReportsList.getData();
	        for(LeaveReportView lrv : list){
	        	 commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), lrv.getFromDate());
	    		 commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), lrv.getToDate());
	        }
	    	leaveReportsList.getData().stream().forEach(u -> {
	 		   u.setFromDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), u.getFromDate()));
	 		   u.setToDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), u.getToDate()));
	 		   u.setAppliedOnDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), u.getAppliedOn()));
	 		   u.setApprovedOnDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), u.getAppliedOn()));
	 		   
	 		   
	 		  LeaveType leaveType = new LeaveType() ;
	 		  leaveType.setId(u.getLeaveTypeId());
	 		  leaveType.setDuration(u.getDuration());
	 		  leaveType.setIncludeWeekoff(u.getIncludeWeekoff());
	 		  leaveType.setIncludeHolidays(u.getIncludeHolidays());
				Leave leave = new Leave();
				leave.setFromDate(u.getFromDate());
				if(StringUtils.isNotBlank(hrLeaveReportDTO.getEndDate()))
					leave.setToDate( u.getToDate());
				
				if(StringUtils.isNotBlank(u.getFromHalfDay())) {
					leave.setFromHalfDay(u.getFromHalfDay());
				}
				if(StringUtils.isNotBlank(u.getToHalfDay())) {
					leave.setToHalfDay(u.getToHalfDay());
				}
				if(StringUtils.isNotBlank(u.getFromDateFromHalfTime())) {
					leave.setFromDateFromHalfTime(u.getFromDateFromHalfTime());
				}
				if(StringUtils.isNotBlank(u.getFromDateToHalfTime())) {
					leave.setFromDateToHalfTime(u.getFromDateToHalfTime());
				}
				if(StringUtils.isNotBlank(u.getToDateFromHalfTime())) {
					leave.setToDateFromHalfTime(u.getToDateFromHalfTime());
				}
				if(StringUtils.isNotBlank(u.getToDateToHalfTime())) {
					leave.setToDateToHalfTime(u.getToDateToHalfTime());
				}
				if(u.getUserId() !=null) {
					User user=new User();
					user.setId(u.getUserId());
					leave.setUser(user);
				}
				float noOfDays = 0f;
				noOfDays = leaveManagementUtil.getAppliedLeaveCount(orgLocation,leaveType,leave,"Duration");
				String noOfDays1 = String.valueOf(noOfDays);
				u.setNoOfDays(noOfDays1);
	 		   
	 	   });
	    	

			}catch (Exception e) {
				e.printStackTrace();
			}
			
			return leaveReportsList;	
	}
	
	@RequestMapping(value = "/getDaywiseLeaveReportList", method = RequestMethod.GET)
	@ResponseBody
	public ResponseList<LeaveReportView> getDaywiseLeaveReportList(DayWiseLeaveReportDTO dayWiseLeaveReportDTO,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		orgLocation.setOrg(org);
		LeaveReportView leaveReportView = new LeaveReportView();
		String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
	    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");

		ResponseList<LeaveReportView> leaveReportsList=null;
		try{
		String[] tableColumns = {"name", "userDeptName", "employmentType", "userRoleName","leaveTypeName", "leaveTypeBillable", "duration", "fromDate", "toDate","reason","approver","status"};

		   int sortColumnIndex = 1; //by default sorting using updated date
		   String sortColumnOrder = "desc"; //by default descending order
				if(dayWiseLeaveReportDTO.getOrder() != null && !dayWiseLeaveReportDTO.getOrder().isEmpty()) {
					sortColumnIndex = Integer.parseInt(dayWiseLeaveReportDTO.getOrder().get(0).get("column"));
					sortColumnOrder = dayWiseLeaveReportDTO.getOrder().get(0).get("dir");
				}
				dayWiseLeaveReportDTO.setSortColumn(tableColumns[sortColumnIndex]);
				dayWiseLeaveReportDTO.setSortOrder(sortColumnOrder);
				dayWiseLeaveReportDTO.setLoginUserId(loginUser.getId());
				
				leaveReportsList = leaveService.getLeaveReportsOrglocationIdAndFromToDate(dayWiseLeaveReportDTO,orgLocation.getOrgId(),orgLocation.getId(),loginUserRole);
		List<LeaveReportView> list = leaveReportsList.getData();
        for(LeaveReportView lrv : list){
        	 commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), lrv.getFromDate());
    		 commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), lrv.getToDate());
        }
    	leaveReportsList.getData().stream().forEach(u -> {
 		   u.setFromDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), u.getFromDate()));
 		   u.setToDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), u.getToDate()));
 		   u.setAppliedOnDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), u.getAppliedOn()));
 		   u.setApprovedOnDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), u.getAppliedOn()));
 		   
 	   });
		
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return leaveReportsList;
		
		
}
	
	@RequestMapping(value = "/getExportDailyWiseLeaveReport", method = RequestMethod.GET)
	    @ResponseBody
	    public void getExportDailyWiseLeaveReport(DayWiseLeaveReportDTO dayWiseLeaveReportDTO,
	            HttpServletRequest request, HttpServletResponse response) throws Exception
	    {
	        List<Long> departmentsList = new ArrayList<>();
	        List<Long> leaveTypeNamesList = new ArrayList<>();
	        List<String> employmentTypeList = new ArrayList<>();
	        List<Long> userRoleList = new ArrayList<>();
	         Workbook createExcelworkbook =  new XSSFWorkbook();
	         StringBuilder excelSB = new StringBuilder();
		    String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    dayWiseLeaveReportDTO.setLoginUserId(loginUser.getId());
	        OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	        Long orgId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgId");
	        
	         User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	         Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
	         Sheet createExcelsheet = createExcelworkbook.createSheet(excelSB.append("dailyWiseLeaveReport ").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString());
	            ResponseList<LeaveReportView> leaveReportsList = leaveService.getLeaveReportsOrglocationIdAndFromToDate(dayWiseLeaveReportDTO,orgId,orgLocations.getId(),loginUserRole);

	        leaveReportsList.getData().stream().forEach(u -> {
	                   u.setFromDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), u.getFromDate()));
	                   u.setToDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), u.getToDate()));
	                   u.setAppliedOnDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), u.getAppliedOn()));
	                   u.setApprovedOnDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), u.getAppliedOn()));
	                   
	               });
	            
	            File file = null;
	             if(!"pdf".equalsIgnoreCase(dayWiseLeaveReportDTO.getExportType())){
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
	
	@RequestMapping(value = "/getExportLeaveReport", method = RequestMethod.GET)
	@ResponseBody
	public void getExportLeaveReport(HrLeaveReportDTO hrLeaveReportDTO,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		List<Long> departmentsList = new ArrayList<>();
		List<Long> leaveTypeNamesList = new ArrayList<>();
		List<String> employmentTypeList = new ArrayList<>();
		List<Long> userRoleList = new ArrayList<>();
		 Workbook createExcelworkbook =  new XSSFWorkbook();
		 StringBuilder excelSB = new StringBuilder();
		    String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
        OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
 		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
 		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		orgLocations.setOrg(org);

		hrLeaveReportDTO.setSortColumn("name");
		hrLeaveReportDTO.setSortOrder("desc");
 		hrLeaveReportDTO.setLoginUserId(loginUser.getId());

		 Sheet createExcelsheet = createExcelworkbook.createSheet(excelSB.append("LeaveReport ").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString());

			ResponseList<LeaveReportView> leaveReportsList = leaveService.getLeaveReportsOrglocationIdAndFromDateToDate(hrLeaveReportDTO,orgLocations.getOrg().getId(),orgLocations.getId(),loginUserRole);


		leaveReportsList.getData().stream().forEach(u -> {
	    		   u.setFromDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), u.getFromDate()));
	    		   u.setToDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), u.getToDate()));
	    		   u.setAppliedOnDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), u.getAppliedOn()));
	    		   u.setApprovedOnDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), u.getAppliedOn()));
	    		   
	    	       LeaveType leaveType = new LeaveType() ;
	 	 		  leaveType.setId(u.getLeaveTypeId());
	 	 		  leaveType.setDuration(u.getDuration());
	 	 		  leaveType.setIncludeWeekoff(u.getIncludeWeekoff());
	 	 		  leaveType.setIncludeHolidays(u.getIncludeHolidays());
	 				Leave leave = new Leave();
	 				leave.setFromDate(u.getFromDate());
	 				if(StringUtils.isNotBlank(hrLeaveReportDTO.getEndDate()))
	 					leave.setToDate( u.getToDate());
	 				
	 				if(StringUtils.isNotBlank(u.getFromHalfDay())) {
	 					leave.setFromHalfDay(u.getFromHalfDay());
	 				}
	 				if(StringUtils.isNotBlank(u.getToHalfDay())) {
	 					leave.setToHalfDay(u.getToHalfDay());
	 				}
	 				if(StringUtils.isNotBlank(u.getFromDateFromHalfTime())) {
	 					leave.setFromDateFromHalfTime(u.getFromDateFromHalfTime());
	 				}
	 				if(StringUtils.isNotBlank(u.getFromDateToHalfTime())) {
	 					leave.setFromDateToHalfTime(u.getFromDateToHalfTime());
	 				}
	 				if(StringUtils.isNotBlank(u.getToDateFromHalfTime())) {
	 					leave.setToDateFromHalfTime(u.getToDateFromHalfTime());
	 				}
	 				if(StringUtils.isNotBlank(u.getToDateToHalfTime())) {
	 					leave.setToDateToHalfTime(u.getToDateToHalfTime());
	 				}
	 				if(u.getUserId() !=null) {
	 					User users=new User();
	 					users.setId(u.getUserId());
	 					leave.setUser(users);
	 				}
	 				float noOfDays = 0f;
	 				noOfDays = leaveManagementUtil.getAppliedLeaveCount(orgLocations,leaveType,leave,"Duration");
	 				String noOfDays1 = String.valueOf(noOfDays);
	 				u.setNoOfDays(noOfDays1);
		
		});
			
			File file = null;
	 		if(!"pdf".equalsIgnoreCase(hrLeaveReportDTO.getExportType())){
	 			createExcelsheet = leaveService.getLeaveReportExcelFile(createExcelsheet,createExcelworkbook,leaveReportsList,orgLocations);
	 			  StringBuilder templateSB = new StringBuilder();
	 	 		  file = File.createTempFile(templateSB.append("Leave_Report").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".xls");
	 	 		  file.setReadable(true);
	 	 		  
	 			 FileOutputStream fos = new FileOutputStream(file);
	 			 try {
	 				createExcelworkbook.write(fos);
	 				fos.close();
	 			} catch (IOException e) {
	 				e.printStackTrace();
	 			}	 	
	 		}else{
	 			file = leaveService.getLeaveReportPdfFile(leaveReportsList,orgLocations);
	 		
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
	
	@RequestMapping(value = "/leaveBalancesReport")
	public ModelAndView leaveBalances(HttpServletRequest request,HttpServletResponse response) throws Exception {
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);	
		model.setViewName("reports/leaveBalancesReports");
		 List<Department> departmentList = departmentService.getDepartmentByOrgLocationId(orgLocation.getId());
		    model.addObject("departmentList", departmentList);
		    model.addObject("departmentListSize", departmentList.size());
			Long roleHierarchyId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "roleHierarchyId");
			Collection<Roles> rolesList = null;
			try {
				rolesList = rolesService.getUserRolesListByModuleAndLoginUserRoleHierarchy(org,orgLocation.getId(),roleHierarchyId);
			} catch (Exception e) {
				logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
			}
			
			model.addObject("rolesList", rolesList);
			List<LeaveType> leaveTypeList = leaveTypeService.getLeaveTypeListByLocationIdAndStatus(String.valueOf(orgLocation.getId()));
			model.addObject("leaveTypeList", leaveTypeList);
		return model;
	}
	
	@RequestMapping(value="/getLeaveBalanceReportList",method = RequestMethod.GET)
	@ResponseBody
	public Response<LeaveBalanceReportDTO> getLeaveBalanceReportList(@RequestParam(value="userRole", required=false) String userRole,@RequestParam(value="searchValue", required=false) String searchValue,@RequestParam(value="orgDepartments", required=false) String orgDepartments,@RequestParam(value="userleaveType", required=false) String userleaveType,@RequestParam(value="leaveType", required=false) String leaveType,@RequestParam(value="topleave", required=false) String topleave,@RequestParam(value="MonthStartDate", required=false) String MonthStartDate,@RequestParam(value="MonthEndDate", required=false) String MonthEndDate,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Response<LeaveBalanceReportDTO> res = new Response<>();
		LeaveBalanceReportDTO leaveBalanceReportDto = new LeaveBalanceReportDTO();
		try {
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		if(orgLocation != null) {
		//List<LeaveType> leaveTypeList = leaveTypeService.getLeaveTypeListByLocationIdAndStatus(String.valueOf(orgLocation.getId()));
		List<Long> userRolesList = new ArrayList<>();
		List<Long> userleaveTypeList = new ArrayList<>();
		List<Long> orgDepartmentsList = new ArrayList<>();
		List<String> leaveTypesList = new ArrayList<>();

		if(userRole != null && !userRole.isEmpty()) {
			String[] userRoles = userRole.split(",");
			for(int i=0; i < userRoles.length; i++) {
				userRolesList.add(Long.parseLong(userRoles[i]));
			}
		}
		if(userleaveType != null && !userleaveType.isEmpty()) {
			String[] userleaveTyp = userleaveType.split(",");
			for(int i=0; i < userleaveTyp.length; i++) {
				userleaveTypeList.add(Long.parseLong(userleaveTyp[i]));
			}
		}
		if(orgDepartments != null && !orgDepartments.isEmpty()) {
			String[] orgDepartment = orgDepartments.split(",");
			for(int i=0; i < orgDepartment.length; i++) {
				orgDepartmentsList.add(Long.parseLong(orgDepartment[i]));
			}
		}
		if(leaveType != null && !leaveType.isEmpty()) {
			String[] leaveTyp = leaveType.split(",");
			for(int i=0; i < leaveTyp.length; i++) {
				leaveTypesList.add(leaveTyp[i]);
			}
		}
		SimpleDateFormat format = new SimpleDateFormat(orgLocation.getDateFormat());
		Date MonthStart = null;
		Date MonthEnd = null;
		if((MonthStartDate!=null&&MonthStartDate!="")||(MonthEndDate!=null&&MonthEndDate!="")) {
		MonthStart=format.parse(MonthStartDate);
		MonthEnd=format.parse(MonthEndDate);
		MonthStart = commonUtil.getOrgLocationCurrentDate(orgLocation.getDateFormat(), MonthStart);
		MonthEnd = commonUtil.getOrgLocationCurrentDate(orgLocation.getDateFormat(), MonthEnd);
		}
		List<LeaveBalanceReportDTO> users =  userService.getUserNameAndTypeByOrgLocationId(searchValue,orgLocation.getId(),userRolesList,orgDepartmentsList,topleave,MonthStart,MonthEnd);
		
//		List<Users> usersList = associateBalanceLeaveService.getLeaveUsersByLocatonDepartmentAndRoles()
		
		List<String> leaveTypeNames = leaveService.getLeaveTypeNamesByOrgLocationId(orgLocation.getId(),topleave);
		
		
		Map<String,List<LeaveBalanceReportDTO>>resultMap = leaveService.getLeaveNamesByOrgLocationId(orgLocation.getId(),userleaveTypeList,leaveTypesList,topleave);
				
		List<LeaveBalanceReportDTO> leaveTypeList = resultMap.get("LEAVETYPES");
		
		List<LeaveBalanceReportDTO> departmentList = resultMap.get("DEPARMENTS");
		Map<String,Float> map = new HashMap<>();
		Map<Long,String> depart = new HashMap<>();
		if ("yes".equalsIgnoreCase(topleave)) {
			leaveTypeNames = leaveTypeList.stream().map(LeaveBalanceReportDTO::getName).collect(Collectors.toList());
			for(LeaveBalanceReportDTO ltDto : users) {
				float total =0;
				List<LeaveType> leaveTypes = leaveTypeService.getLeaveTypeListByLocationId(String.valueOf(orgLocation.getId()));
				Object[] deptArray =  orgDepartmentsList.toArray();
				for(LeaveType lt : leaveTypes) {
					if(leaveTypeNames != null && leaveTypeNames.contains(lt.getName())) {
						boolean flag = false;
						if(lt.getDepartmentIds() != null) {
							List<String> ltDepts = Arrays.asList(lt.getDepartmentIds().split(","));
							List<Long> dList = ltDepts.stream().map(s -> Long.parseLong(s)).collect(Collectors.toList());
								if(ltDto.getDepartmentId() != null &&  dList.contains(ltDto.getDepartmentId().longValue())) {
									flag = true;
								}else if(ltDto.getDepartmentId() == null) {
									flag = true;
								}
						}else {
							flag = true;
						}
						
//						if(flag) {
							lt.setActionType("CUSTOM");
							lt.setFromDate(MonthStartDate);
							lt.setToDate(MonthEndDate);
							float Leavestaken = leaveManagementUtil.getTotalAppliedLeaveCount(orgLocation, lt, ltDto.getUserId().longValue());
							StringBuilder sb = new StringBuilder();
							sb.append(lt.getName()).append("-").append(String.valueOf(ltDto.getUserId().longValue()));
							if(flag) {
								map.put(sb.toString(), Leavestaken);
								total = total +Leavestaken;
							}else {
								map.put(sb.toString(), 0.01f);
							}
							
//						}
					}
				}
//				for(LeaveBalanceReportDTO d:departmentList) {
					String Departments = leaveService.getDepartmentTypeNamesByOrgLocationIdAndUserId(ltDto.getUserId().longValue(),orgLocation.getId());
					depart.put(ltDto.getUserId().longValue(),Departments );
//				}
				}
		}else {
			Object[] deptArray =  orgDepartmentsList.toArray();
			for(LeaveBalanceReportDTO ltDto : users) {
				for(LeaveBalanceReportDTO lt : leaveTypeList) {
					boolean flag = false;
					if(lt.getDepartment() != null) {
						List<String> ltDepts = Arrays.asList(lt.getDepartment().split(","));
						List<Long> dList = ltDepts.stream().map(s -> Long.parseLong(s)).collect(Collectors.toList());
//						for(Object deptId : deptArray) {
							if(ltDto.getDepartmentId() != null &&  dList.contains(ltDto.getDepartmentId().longValue())) {
								flag = true;
							}else if(ltDto.getDepartmentId() == null) {
								flag = true;
							}
//						}
					}else {
						flag = true;
					}
					
						float leaveTypeBalance = associateBalanceLeaveService.getTotalLeavesCountByUserIdAndLeaveTypeId(lt.getId().longValue(),ltDto.getUserId().longValue(),orgLocation.getId(),orgLocation.getOrgId());
						StringBuilder sb = new StringBuilder();
						sb.append(lt.getName()).append("-").append(String.valueOf(ltDto.getUserId().longValue()));
						if(flag) {
							map.put(sb.toString(), leaveTypeBalance);
						}else {
							map.put(sb.toString(), 0.01f);
						}
				}
	//			for(LeaveBalanceReportDTO d:departmentList) {
	
					String Departments = leaveService.getDepartmentTypeNamesByOrgLocationIdAndUserId(ltDto.getUserId().longValue(),orgLocation.getId());
					depart.put(ltDto.getUserId().longValue(),Departments );
	//			}
			}
		}
		
	
		leaveBalanceReportDto.setLeaveTypeNames(leaveTypeNames);
		leaveBalanceReportDto.setMap(map);
		leaveBalanceReportDto.setDepart(depart);
		if(users != null) 
		leaveBalanceReportDto.setUsers(users);
		res.setObject(leaveBalanceReportDto);
		res.setResponse(Result.SUCCESS.name());
		res.setResponseMessage(Result.SUCCESS.getValue());
		} else {
		res.setResponse(Result.SESSION_TIMEDOUT.name());
		res.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		} catch (Exception e) {
		res.setResponse(Result.AWKWARD.name());
		res.setResponseMessage(Result.AWKWARD.getValue());
		e.printStackTrace();
		}	
		return res;
	}
	

	@RequestMapping(value = "/gettopleavetakenReportList", method = RequestMethod.GET)
	@ResponseBody
	public Response<LeaveBalanceReportDTO> gettopleavetakenReportList(@RequestParam(value="userRole", required=false) String userRole,@RequestParam(value="searchValue", required=false) String searchValue,@RequestParam(value="orgDepartments", required=false) String orgDepartments,@RequestParam(value="userleaveType", required=false) String userleaveType,@RequestParam(value="leaveType", required=false) String leaveType,@RequestParam(value="topleave", required=false) String topleave,@RequestParam(value="MonthStartDate", required=false) String MonthStartDate,@RequestParam(value="MonthEndDate", required=false) String MonthEndDate,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		Response<LeaveBalanceReportDTO> topLeave = getLeaveBalanceReportList(userRole, searchValue, orgDepartments, userleaveType, leaveType,topleave,MonthStartDate,MonthEndDate, request, response);
		
		return topLeave;
	}

	@RequestMapping(value = "/getExportReports", method = RequestMethod.GET)
	@ResponseBody
	public void getExportReports(@RequestParam(value="userRole", required=false) String userRole,@RequestParam(value="searchValue", required=false) String searchValue,@RequestParam(value="orgDepartments", required=false) String orgDepartments,@RequestParam(value="userleaveType", required=false) String userleaveType,@RequestParam(value="leaveType", required=false) String leaveType,@RequestParam(value="exportType", required=false) String exportType,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		 Workbook createExcelworkbook =  new XSSFWorkbook();
         
        OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 Sheet createExcelsheet = createExcelworkbook.createSheet("LeaveBalanceReport"); 
		 String topleave = null;
		 String MonthStartDate= null;
		 String MonthEndDate= null;
		Response<LeaveBalanceReportDTO> LeaveBalanceReport=  getLeaveBalanceReportList(userRole,searchValue,  orgDepartments,  userleaveType, leaveType,topleave,MonthStartDate,MonthEndDate,request,  response);
			File file = null;
	 		if(!"pdf".equalsIgnoreCase(exportType)){
	 			createExcelsheet = leaveService.getLeaveBalanceExcelFile(createExcelsheet,createExcelworkbook,LeaveBalanceReport,orgLocation,topleave);
	 			  StringBuilder templateSB = new StringBuilder();
	 	 		  file = File.createTempFile(templateSB.append("Balance_Leave_Report").append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),new Date())).toString(),".xls");
	 	 		 file.setReadable(true);
                  
                  FileOutputStream fos = new FileOutputStream(file);
                  try {
                     createExcelworkbook.write(fos);
                     fos.close();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }         
             }else{
                 file = leaveService.getLeaveBalancePdfFile(LeaveBalanceReport, orgLocation,topleave);
             
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

	@RequestMapping(value = "/getExportTopReports", method = RequestMethod.GET)
	@ResponseBody
	public void getExportTopReports(@RequestParam(value="userRole", required=false) String userRole,@RequestParam(value="searchValue", required=false) String searchValue,@RequestParam(value="orgDepartments", required=false) String orgDepartments,@RequestParam(value="userleaveType", required=false) String userleaveType,@RequestParam(value="leaveType", required=false) String leaveType,@RequestParam(value="exportType", required=false) String exportType,@RequestParam(value="topleave", required=false) String topleave,@RequestParam(value="MonthStartDate", required=false) String MonthStartDate,@RequestParam(value="MonthEndDate", required=false) String MonthEndDate,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		 Workbook createExcelworkbook =  new XSSFWorkbook();
        OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 Sheet createExcelsheet = createExcelworkbook.createSheet("TopLeaveTakenReport");
		 Response<LeaveBalanceReportDTO> LeaveBalanceReport=  getLeaveBalanceReportList(   userRole, searchValue, orgDepartments,  userleaveType, leaveType,topleave,MonthStartDate,MonthEndDate ,request,  response);
			File file = null;
	 		if(!"pdf".equalsIgnoreCase(exportType)){
	 			createExcelsheet = leaveService.getLeaveBalanceExcelFile(createExcelsheet,createExcelworkbook,LeaveBalanceReport,orgLocation,topleave);
	 			  StringBuilder templateSB = new StringBuilder();
	 	 		  file = File.createTempFile(templateSB.append("Top_Leave_Taken_Report").append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),new Date())).toString(),".xls");
	 	 		 file.setReadable(true);
                  
                  FileOutputStream fos = new FileOutputStream(file);
                  try {
                     createExcelworkbook.write(fos);
                     fos.close();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }         
             }else{
            	 file = leaveService.getLeaveBalancePdfFile(LeaveBalanceReport, orgLocation,topleave);
             
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
