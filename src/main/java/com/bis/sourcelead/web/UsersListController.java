package com.bis.sourcelead.web;

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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.OrgRelationDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.dto.UserRoleDTO;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.TagEntites;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.SourceleadTagsService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@RequestMapping("/app")
public class UsersListController {
	
private static final Logger logger = LoggerFactory.getLogger(UsersListController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	RolesService rolesService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	 private CommonUtil commonUtil;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	DepartmentService departmentService;
	
	@Autowired
	 private SourceleadTagsService sourceleadTagsService;
	
	/**
	 * To forward users List jsp page
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	/* This method is used for landing page of user tab. So, who has user tab access they can see users list. No preAuthorize for this method */
	@RequestMapping(value = "/usersList")
	public ModelAndView usersList(@RequestParam(value="searchResult",required = false) String searchResult,@RequestParam(value="searchValue",required = false) String searchValue,HttpServletRequest request) throws Exception { 
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		Long loginUserRoleId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRoleId");
		Collection<Roles> rolesList = null;
		ModelAndView model = new ModelAndView();
		model.setViewName("users/usersList");
		model.addObject("sourceleadUrl", sourceleadUrl);
		if(searchResult !=null && searchValue !=null){
			model.addObject("searchResult", searchResult);
			model.addObject("searchValue", searchValue);
			model.addObject("searchType", "Users");
		}
		
		Long roleHierarchyId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "roleHierarchyId");
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Users);
		
		request.getSession().setAttribute("sessionData",sessionData);
		
		try {
			rolesList = rolesService.getUserRolesListByModuleAndLoginUserRoleHierarchy(org,orgLocations.getId(),roleHierarchyId);
		} catch (Exception e) {
			logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
		}
		
	
		String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "userCreateMsg");
		if(!isNullOrEmpty(msg)){
			model.addObject("msg", msg);
			SourceLeadSessionManager.removeSessionAttribute(request, "userCreateMsg");
		}
		model.addObject("rolesList", rolesList);
		
		Integer orgLocationsCount = orgLocationService.getRegisteredOrgLoctionsCountByOrgId(org.getId());
		model.addObject("orgLocationsCount", orgLocationsCount);
		
		/*List<OrgRelationDTO> orgClientList = orgRelationsService.getAllClientsListOrgLocationId(orgLocations.getId()); 
        model.addObject("clientsList", orgClientList);
        model.addObject("clientListSize", orgClientList.size());*/
        
        List<OrgRelationDTO> orgVendorList = orgRelationsService.getAllVendorsListOrgLocationId(orgLocations.getId()); 
        model.addObject("orgVendorList", orgVendorList);
        model.addObject("clientListSize", orgVendorList.size());
        
        
        List<UserDTO> reportingManagersList = userRoleService.getAllReportingManagersListAssignedToAssociates(orgLocations.getId());
        model.addObject("reportingManagersList", reportingManagersList);
        model.addObject("reportingManagersListSize", reportingManagersList.size());
        
        List<Team> teamsList = teamService.getTeamListByOrgLocationID(orgLocations.getId());
        model.addObject("teamsList", teamsList);
        model.addObject("teamsListSize", teamsList.size());
        
        List<Department> departmentList = departmentService.getDepartmentByOrgLocationId(orgLocations.getId());
        model.addObject("departmentList", departmentList);
        model.addObject("departmentListSize", departmentList.size());
		
        List<String> associateTagsList = sourceleadTagsService.sourceleadTagsListByEntitypeAndOrgloactionId(TagEntites.ASSOCIATES.toString(), orgLocations.getId());
        model.addObject("associateTagsList", associateTagsList);
        
		return model;

	}
	
	protected boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str.trim());
	}

	/**
	 * @author Rajashekar
	 * To get All the Users to export to Excel and pdf
	 * @param request
	 * @param userRole
	 * @param searchByName
	 * @param userState
	 * @param loginUserRole
	 * @return
	 * @throws Exception 
	 */
	@PreAuthorize("hasAnyRole('ROLE_EXPORT_ASSOCIATE')")
	@RequestMapping(value = "/getExportUsers")
	public void getExportUsersData(UserDTO userDTO,UserRoleDTO userRoleDTO,HttpServletRequest request,
			@RequestParam(value="exportType", required=false) String exportType, HttpServletResponse response) throws Exception {
		
		 List<UserRole> activeList = null;
		 List<UserRole> inactiveList = null;
		 List<UserRole> notLoggedInList = null;
		 List<Long> managerUserIds = new ArrayList<>();
		 List<Long> teamsList = new ArrayList<>();
		 List<Long> departmentsList = new ArrayList<>();
		 List<Long> orgRelationsList = new ArrayList<>();
		
		 Workbook createExcelworkbook =  new XSSFWorkbook();
		 StringBuilder excelSB = new StringBuilder();
         
        OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
 		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
 		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
 		ArrayList<UserRole> userRolesList = new ArrayList<UserRole>();
 		Long userRoleType ;
		if(!"".equalsIgnoreCase(userDTO.getUserRole())){
			userRoleType = Long.parseLong(userDTO.getUserRole());
		}else{
			userRoleType = null;
		}
 		 Sheet createExcelsheet = createExcelworkbook.createSheet(excelSB.append("users ").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString());
 		try {
 			
 			if(userRoleDTO.getOrgClients() != null && !userRoleDTO.getOrgClients().isEmpty()) {
 				String[] orgClients = userRoleDTO.getOrgClients().split(",");
 				for(int i=0; i < orgClients.length; i++) {
 					orgRelationsList.add(Long.parseLong(orgClients[i]));
 				}
 			}
 			
 			if(userRoleDTO.getOrgManagers() != null && !userRoleDTO.getOrgManagers().isEmpty()) {
 				String[] orgManagers = userRoleDTO.getOrgManagers().split(",");
 				for(int i=0; i < orgManagers.length; i++) {
 					managerUserIds.add(Long.parseLong(orgManagers[i]));
 				}
 			}
 			
 			if(userRoleDTO.getOrgTeams() != null && !userRoleDTO.getOrgTeams().isEmpty()) {
 				String[] orgTeams = userRoleDTO.getOrgTeams().split(",");
 				for(int i=0; i < orgTeams.length; i++) {
 					teamsList.add(Long.parseLong(orgTeams[i]));
 				}
 			}
 			
 			if(userRoleDTO.getOrgDepartments() != null && !userRoleDTO.getOrgDepartments().isEmpty()) {
 				String[] orgDepts = userRoleDTO.getOrgDepartments().split(",");
 				for(int i=0; i < orgDepts.length; i++) {
 					departmentsList.add(Long.parseLong(orgDepts[i]));
 				}
 			}
 				
 			
			ResponseList<UserRole>  rolesList = userRoleService.getDynamicUsersListByRoleAndNameAndLocationId(userDTO, userRoleDTO, orgLocations.getId(), user.getId(), userDTO.getLoginUserRole(), user.getUserCode(),orgRelationsList,managerUserIds,teamsList,departmentsList);
 			if(null != rolesList){
 				for(UserRole role : rolesList.getData()){
 					role.setUserRoleName(role.getRoles().getDisplayName());
 					userRolesList.add(role);
 				}
 			}
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		File file = null;
 		if(!"pdf".equalsIgnoreCase(exportType)){
 			createExcelsheet=  userRoleService.getExcelFile(createExcelsheet,createExcelworkbook,activeList,inactiveList,notLoggedInList,userRolesList,userDTO.getUserState(),orgLocations);
 			  StringBuilder templateSB = new StringBuilder();
 	 		  file = File.createTempFile(templateSB.append("associates_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".xls");
 	 		  file.setReadable(true);
 	 		  
 			 FileOutputStream fos = new FileOutputStream(file);
 			 try {
 				createExcelworkbook.write(fos);
 				fos.close();
 			} catch (IOException e) {
 				e.printStackTrace();
 			}	 	
 		}else{
 			file = userRoleService.getPdfFile(activeList,inactiveList,notLoggedInList,userRolesList,userDTO.getUserState(),orgLocations);
 		
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
