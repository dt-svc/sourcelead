package com.bis.sourcelead.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.LeaveDao;
import com.bis.sourcelead.reportsDTO.LeaveBalanceReportDTO;
import com.bis.sourcelead.dto.OrgRelationDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.dto.UserRoleDTO;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.LeaveReportView;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.AssociateBalanceLeaveService;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.LeaveService;
import com.bis.sourcelead.service.LeaveTypeService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.web.UsersListController;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@RestController
@RequestMapping("/app")
public class LeaveManagementReportsRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(UsersListController.class);
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
	LeaveTypeService leaveTypeService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	AssociateBalanceLeaveService associateBalanceLeaveService;
	
	@Autowired
	RolesService rolesService;
	
	@Autowired
	DepartmentService departmentService;
	
	@RequestMapping(value = "/daywiseLeaveReport")
	public ModelAndView approvedLeavesJsp(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("reports/daywiseLeaveReport");
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
	
	 
	
	/*@RequestMapping(value = "/getDaywiseLeaveReportList", method = RequestMethod.GET)
	@ResponseBody
	public ResponseList<LeaveReportView> getDaywiseLeaveReportList(@RequestParam(value="fromDate", required=false) String fromDate,@RequestParam(value="searchValue", required=false) String searchValue,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
//		ResponseList<LeaveReportView> leaveReportsList =  new ResponseList<>();
		ResponseList<LeaveReportView> leaveReportsList = leaveService.getLeaveReportsOrglocationIdAndFromToDate(orgLocation.getOrg().getId(),orgLocation.getId(),fromDate,searchValue);
		leaveReportsList.getData().stream().forEach(u -> {
    		   u.setFromDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), u.getFromDate()));
    		   u.setToDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), u.getToDate()));
    	   });
//    	   leaveReportsList.setData(leaveList);
		
		
		return leaveReportsList;
}*/
	
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
	 			if (file != null) {
	 				file.delete();
	 			}
	 		}else{
	 			file = leaveService.getLeaveBalancePdfFile(LeaveBalanceReport,orgLocation);
	 		
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



