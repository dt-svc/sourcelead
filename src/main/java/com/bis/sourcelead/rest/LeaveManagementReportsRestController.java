package com.bis.sourcelead.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.bis.sourcelead.dto.DayWiseLeaveReportDTO;
import com.bis.sourcelead.dto.HrLeaveReportDTO;
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
import com.bis.sourcelead.reportsDTO.LeaveBalanceReportDTO;
import com.bis.sourcelead.service.AssociateBalanceLeaveService;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.LeaveService;
import com.bis.sourcelead.service.LeaveTypeService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/app")
public class LeaveManagementReportsRestController {

	
//	private static final Logger logger = LoggerFactory.getLogger(UsersListController.class);

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
	UserService userService;
	
	@Autowired
	AssociateBalanceLeaveService associateBalanceLeaveService;
	
	
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
	
	@RequestMapping(value = "/leaveReport")
	public ModelAndView leaveReport(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("reports/leaveReport");
		return model;
	}
	
/*
	@RequestMapping(value = "/getDaywiseLeaveReportList", method = RequestMethod.GET)
=======
	@RequestMapping(value="/leaveReportsList",method= RequestMethod.POST)
	@ResponseBody
	public ResponseList<LeaveReportView> getDayEntriesList(HrLeaveReportDTO hrLeaveReportDTO,HttpServletRequest request) throws Exception {
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		LeaveReportView leaveReportView = new LeaveReportView();
		ResponseList<LeaveReportView> leaveReportsList=null;
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
				
					
			leaveReportsList = leaveService.getLeaveReportsOrglocationIdAndFromDateToDate(hrLeaveReportDTO,orgLocation.getOrg().getId(),orgLocation.getId());
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
*/
	
	@RequestMapping(value="/getLeaveBalanceReportList",method = RequestMethod.GET)
	@ResponseBody
	public Response<LeaveBalanceReportDTO> getLeaveBalanceReportList(@RequestParam(value="userRole", required=false) String userRole,@RequestParam(value="orgDepartments", required=false) String orgDepartments,@RequestParam(value="userleaveType", required=false) String userleaveType,@RequestParam(value="leaveType", required=false) String leaveType,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Response<LeaveBalanceReportDTO> res = new Response<>();
		LeaveBalanceReportDTO leaveBalanceReportDto = new LeaveBalanceReportDTO();
		try {
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		if(orgLocation != null) {
		//List<LeaveType> leaveTypeList = leaveTypeService.getLeaveTypeListByLocationIdAndStatus(String.valueOf(orgLocation.getId()));
		List<Long>userRolesList = new ArrayList<>();
		List<Long>userleaveTypeList = new ArrayList<>();
		List<Long>orgDepartmentsList = new ArrayList<>();
		List<String>leaveTypesList = new ArrayList<>();
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
		List<LeaveBalanceReportDTO> users =  userService.getUserNameAndTypeByOrgLocationId(orgLocation.getId(),userRolesList,orgDepartmentsList);
		
		
		List<String> leaveTypeNames = leaveService.getLeaveTypeNamesByOrgLocationId(orgLocation.getId());
		
		
		Map<String,List<LeaveBalanceReportDTO>>resultMap = leaveService.getLeaveNamesByOrgLocationId(orgLocation.getId(),userleaveTypeList,leaveTypesList);
		
				
		List<LeaveBalanceReportDTO> leaveTypeList = resultMap.get("LEAVETYPES");
		List<LeaveBalanceReportDTO> departmentList = resultMap.get("DEPARMENTS"); 
		Map<String,Float> map = new HashMap<>();
		Map<Long,String> depart = new HashMap<>(); 
		for(LeaveBalanceReportDTO ltDto : users) {
			for(LeaveBalanceReportDTO lt : leaveTypeList) {
				float leaveTypeBalance = associateBalanceLeaveService.getTotalLeavesCountByUserIdAndLeaveTypeId(lt.getId().longValue(),ltDto.getUserId().longValue(),orgLocation.getId(),orgLocation.getOrg().getId());
				StringBuilder sb = new StringBuilder();
				sb.append(lt.getName()).append("-").append(String.valueOf(ltDto.getUserId().longValue()));
				map.put(sb.toString(), leaveTypeBalance);
			}
			for(LeaveBalanceReportDTO d:departmentList) {

				String Departments = leaveService.getDepartmentTypeNamesByOrgLocationIdAndUserId(ltDto.getUserId().longValue());
				depart.put(ltDto.getUserId().longValue(),Departments );
			}
		}
		leaveBalanceReportDto.setLeaveTypeNames(leaveTypeNames);
		leaveBalanceReportDto.setMap(map);
		leaveBalanceReportDto.setDepart(depart);
		if(users != null) 
		leaveBalanceReportDto.setUsers(users);
		//leaveBalanceReportDto.setUsers(departmentList);
		//leaveBalanceReportDto.setUsers(leaveTypeList);
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
		}	
		return res;
	}

	@RequestMapping(value = "/getExportReports", method = RequestMethod.GET)
	@ResponseBody
	public void getExportReports(@RequestParam(value="userRole", required=false) String userRole,@RequestParam(value="orgDepartments", required=false) String orgDepartments,@RequestParam(value="userleaveType", required=false) String userleaveType,@RequestParam(value="leaveType", required=false) String leaveType,@RequestParam(value="exportType", required=false) String exportType,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		 Workbook createExcelworkbook =  new XSSFWorkbook();
		// StringBuilder excelSB = new StringBuilder();
         
        OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
 		//User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
 		//Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		 Sheet createExcelsheet = createExcelworkbook.createSheet("LeaveBalanceReport");
		 Response<LeaveBalanceReportDTO> LeaveBalanceReport=  getLeaveBalanceReportList(  userRole,  orgDepartments,  userleaveType, leaveType, request,  response);
			
			File file = null;
	 		if(!"pdf".equalsIgnoreCase(exportType)){
	 			createExcelsheet = leaveService.getLeaveBalanceExcelFile(createExcelsheet,createExcelworkbook,LeaveBalanceReport,orgLocation);
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
                 file = leaveService.getLeaveBalancePdfFile(LeaveBalanceReport, orgLocation);
             
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
	 	         
	 	        OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 	         User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	 	         Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
	 	         Sheet createExcelsheet = createExcelworkbook.createSheet(excelSB.append("dailyWiseLeaveReport ").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString());
	 	            ResponseList<LeaveReportView> leaveReportsList = leaveService.getLeaveReportsOrglocationIdAndFromToDate(dayWiseLeaveReportDTO,orgLocations.getOrg().getId(),orgLocations.getId());

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
         
        OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
 		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
 		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		 Sheet createExcelsheet = createExcelworkbook.createSheet(excelSB.append("dailyWiseLeaveReport ").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString());
			ResponseList<LeaveReportView> leaveReportsList = leaveService.getLeaveReportsOrglocationIdAndFromDateToDate(hrLeaveReportDTO,orgLocations.getOrg().getId(),orgLocations.getId());


		leaveReportsList.getData().stream().forEach(u -> {
	    		   u.setFromDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), u.getFromDate()));
	    		   u.setToDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), u.getToDate()));
	    		   u.setAppliedOnDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), u.getAppliedOn()));
	    		   u.setApprovedOnDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), u.getAppliedOn()));
	    		   
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
}

