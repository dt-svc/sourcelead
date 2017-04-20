package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
public class CandidateOffBoardRestController {
	
private static final Logger logger = LoggerFactory.getLogger(TimesheetRestController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
    @Autowired
	CommonUtil commonUtil;
    
    @Autowired
    UserService userService;
    
    @Autowired
    EmployeesService employeesService;
    
    @Autowired
    UserRoleService userRoleService;
    
    protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
    
    
    
    
	 @RequestMapping(value = "/getUsersForOffboard")
	 public String getUsersForOffboard(HttpServletRequest request) throws Exception {

		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		
		List<User> usersList = userService.getUsersByOrgLocationIdExceptLoggedInUser(loginOrgLocation.getId(),loginUser.getId());
		List<User> orgUsersList = new ArrayList<User>();
		
		if(null != usersList && usersList.size() > 0){
			for(User user : usersList){

				    StringBuilder userName = new StringBuilder();
					if(null != user.getLastName()){
						user.setUserName(userName.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
					}else{
						user.setUserName(userName.append(user.getFirstName()).toString());
					}
				
				orgUsersList.add(user);
			}
		}
		
		return commonUtil.toJSON(usersList);
	 
	 }
	 
	 
	 
	 
	 @RequestMapping(value = "/getOffboardUserDetailsById")
	 public String getOffboardUserDetailsById(@RequestParam(value="offboardUserId", required=false) Long offboardUserId,
			 HttpServletRequest request) throws Exception {

		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		User user = userService.getUserById(offboardUserId);
		Employees employees = employeesService.getEmployeeByUserIdAndOrgLocationsId(user.getId(), loginOrgLocation.getId());
		if(null != employees && null != employees.getHireDate()){
			user.setDateOfJoin(Constants.DMY.format(employees.getHireDate()));
		}
		UserRole userRole = userRoleService.getUserDetailsByUserIdAndLocationId(user.getId(), loginOrgLocation.getId());
		user.setRole(userRole.getRoles().getDisplayName());
		
		return commonUtil.toJSON(user);
	 }

}
