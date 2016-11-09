package com.bis.sourcelead.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class UsersListController {
	
private static final Logger logger = LoggerFactory.getLogger(UsersListController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	RolesService rolesService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	
	/**
	 * To forward users List jsp page
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/usersList")
	public ModelAndView usersList(HttpServletRequest request) {
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		Long loginUserRoleId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRoleId");
		Collection<Roles> rolesList = null;
		ModelAndView model = new ModelAndView();
		model.setViewName("users/usersList");
		model.addObject("sourceleadUrl", sourceleadUrl);
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Users);
		
		request.getSession().setAttribute("sessionData",sessionData);
		
		try {
			rolesList = rolesService.getMoudleRoles(org.getId(), loginUserRoleId);
		} catch (Exception e) {
			logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
		}
		
	
		String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "userCreateMsg");
		if(!isNullOrEmpty(msg)){
			model.addObject("msg", msg);
			SourceLeadSessionManager.removeSessionAttribute(request, "userCreateMsg");
		}
		
		model.addObject("rolesList", rolesList);
		
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
	@RequestMapping(value = "/getExportUsers")
	public void getExportUsersData(HttpServletRequest request,
			@RequestParam(value="userRole", required=false) String userRole,
			@RequestParam(value="searchByName", required=false) String searchByName,
			@RequestParam(value="userState", required=false) String userState,
			@RequestParam(value="loginUserRole", required=false) String loginUserRole,
			@RequestParam(value="exportType", required=false) String exportType,HttpServletResponse response) throws Exception {
		
		 List<UserRole> activeList = null;
		 List<UserRole> inactiveList = null;
		 List<UserRole> notLoggedInList = null;
		 DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		 Workbook createExcelworkbook =  new XSSFWorkbook();
 		 Sheet createExcelsheet = createExcelworkbook.createSheet("users "+dateFormat.format(new Date()));
         
        OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
 		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
 		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
 		ArrayList<UserRole> userRolesList = new ArrayList<UserRole>();
 		List<UserRole> rolesList = new ArrayList<UserRole>();
 		
 		try {
 			
 			if(!isNullOrEmpty(userState) && isNullOrEmpty(userRole) && isNullOrEmpty(searchByName)){
 				rolesList = userRoleService.getUsersListByUserStateAndLocationId(userState, orgLocations.getId(),user.getId(),loginUserRole,user.getUserCode());
 			}
 			else if((isNullOrEmpty(userRole) && isNullOrEmpty(searchByName)) || (userRole.equalsIgnoreCase("All") && isNullOrEmpty(searchByName))){
 				rolesList = userRoleService.getUsersListByLocationId(orgLocations.getId(),userState,user.getId(),loginUserRole,user.getUserCode());
 				 activeList = userRoleService.getUsersListByUserStateAndLocationId("ACTIVE", orgLocations.getId(),user.getId(),loginUserRole,user.getUserCode());
				 inactiveList = userRoleService.getUsersListByUserStateAndLocationId("INACTIVE", orgLocations.getId(),user.getId(),loginUserRole,user.getUserCode());
				 notLoggedInList = userRoleService.getUsersListByUserStateAndLocationId("NOT_LOGGEDIN", orgLocations.getId(),user.getId(),loginUserRole,user.getUserCode());
 			}
 			else if(!isNullOrEmpty(userRole) && !"All".equalsIgnoreCase(userRole) && isNullOrEmpty(searchByName)){
 				rolesList = userRoleService.getUsersListByRoleAndLocationId(Long.parseLong(userRole), orgLocations.getId(),userState,user.getId(),loginUserRole,user.getUserCode());
 			}
 			else if((!isNullOrEmpty(searchByName) && isNullOrEmpty(userRole)) || ("All".equalsIgnoreCase(userRole) && !isNullOrEmpty(searchByName))){
 				rolesList = userRoleService.getUsersListByUserNameAndLocationId(searchByName, orgLocations.getId(),userState,user.getId(),loginUserRole,user.getUserCode());
 			}
 			else if(!isNullOrEmpty(userRole) && !"All".equalsIgnoreCase(userRole) && !isNullOrEmpty(searchByName)){
 				rolesList = userRoleService.getUsersListByRoleAndNameAndLocationId(Long.parseLong(userRole), searchByName, orgLocations.getId(),userState,user.getId(),loginUserRole,user.getUserCode());
 			}
 		
 			if(null != rolesList){
 				for(UserRole role : rolesList){
 					role.setUserRoleName(role.getRoles().getDisplayName());
 					userRolesList.add(role);
 				}
 			}
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		File file = null;
 		if(!"pdf".equalsIgnoreCase(exportType)){
 			createExcelsheet = userRoleService.getExcelFile(createExcelsheet,createExcelworkbook,activeList,inactiveList,notLoggedInList,userRolesList,userState);
 	 		  file = new File("users_"+dateFormat.format(new Date())+".xls");
 			 FileOutputStream fos = new FileOutputStream(file);
 			 try {
 				createExcelworkbook.write(fos);
 				fos.close();
 			} catch (IOException e) {
 				e.printStackTrace();
 			}	 	
 		}else{
 			file = userRoleService.getPdfFile(activeList,inactiveList,notLoggedInList,userRolesList,userState,org.getOrgName());
 		
 		}
		 String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
			inputStream.close();
			response.getOutputStream().close();
			response.getOutputStream().flush();	
			

	}

}
