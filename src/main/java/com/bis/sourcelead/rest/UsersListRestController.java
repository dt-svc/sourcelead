package com.bis.sourcelead.rest;

import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

@RestController
public class UsersListRestController {
	
private static final Logger logger = LoggerFactory.getLogger(UsersListRestController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	EmployeesService employeesService;
	
	@Autowired
	UserService userService;

	@Autowired
	ProjectsService projectsService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	TimesheetSubmissionsService timesheetSubmissionsService;
	
	@Autowired
	private SourceleadPasswordEncoder sourceleadPasswordEncoder;
		
	
	/**
	 * To get All the Users by filtering 
	 * @param request
	 * @param userRole
	 * @param searchByName
	 * @param userState
	 * @param loginUserRole
	 * @return
	 */
	@RequestMapping(value = "/getUsersList")
	@ResponseBody
	public String getUsersList(HttpServletRequest request,
			@RequestParam(value="userRole", required=false) String userRole,
			@RequestParam(value="searchByName", required=false) String searchByName,
			@RequestParam(value="userState", required=false) String userState,
			@RequestParam(value="loginUserRole", required=false) String loginUserRole) {
		
	 	String resultJson = null;
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		ArrayList<UserRole> userRolesList = new ArrayList<UserRole>();
		List<UserRole> rolesList = new ArrayList<UserRole>();
		
		
		try {
			
			if(!isNullOrEmpty(userState) && isNullOrEmpty(userRole) && isNullOrEmpty(searchByName)){
				rolesList = userRoleService.getUsersListByUserStateAndLocationId(userState, orgLocations.getId(),user.getId(),loginUserRole,user.getUserCode());
			}
			else if((isNullOrEmpty(userRole) && isNullOrEmpty(searchByName)) || (userRole.equalsIgnoreCase("All") && isNullOrEmpty(searchByName))){
				rolesList = userRoleService.getUsersListByLocationId(orgLocations.getId(),userState,user.getId(),loginUserRole,user.getUserCode());
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
	
	/*else if(!isNullOrEmpty(loginUserRole) && loginUserRole.equalsIgnoreCase("ROLE_APPROVER")){
		List<Long> projectIdsList  = null;
		try {
			 projectIdsList = projectsService.getApprovarAssignedProjects(user.getId(), orgLocations.getId());
			 rolesList = userRoleService.getApproverAssignedUsers(projectIdsList, userState, searchByName);
			 for(UserRole role : rolesList){
					role.setUserRoleName(role.getRoles().getDisplayName());
					userRolesList.add(role);
				}
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}*/
		 resultJson = toJSON(userRolesList);
		 return resultJson;

	}
	

	/**
	 * Forward to user controller by using userId as request param
	 * @param userId
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	 @RequestMapping(value = "/userSummary/{userId}" , method = RequestMethod.GET)
	 public @ResponseBody void userSummary(@PathVariable Long userId,HttpServletRequest request,HttpServletResponse response) throws Exception {
			response.sendRedirect("/sourcelead/userSummaryDetails?userId="+userId);
			
	   }
	 
	 
	 /**
	  * Get Employee details by User ID
	  * @param request
	  * @param userId
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/getUserDetailsById", method = RequestMethod.POST)
	 public @ResponseBody String getUserDetailsById(HttpServletRequest request, @RequestParam(value="userId", required=false) Long userId) throws Exception{ 
		 String resultJson = null;   
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 
		 UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(userId, orgLocations.getId());
		 
		 Employees employee = employeesService.getEmployeeByUserIdAndOrgLocationsId(userId, orgLocations.getId());
		 
		 userRole.setUserType(userRole.getUserType());
		 
		 if(employee !=null){
				userRole.setEmployeeId(employee.getEmployeeId());
				userRole.setRate(employee.getRate());
				userRole.setRatePer(employee.getRatePer());
				userRole.setInvoiceFrequency(employee.getInvoiceFrequency());
				if(employee.getHireDate() != null){
					userRole.setHireDateStr(com.bis.sourcelead.constants.Constants.DF_YMD.format(employee.getHireDate())); 
				 }else{
					 userRole.setHireDateStr("");
				 }
				userRole.setDailyHours(employee.getDailyHours());
				
		 }
		
		 
		 List<User> userList = userService.getManagersByUserIdAndUserRolesAndOrgLocationId(userRole.getUser().getId(), userRole.getUserType(), orgLocations.getId());
		 for (User user : userList) {
			    UserRole userRole1 = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocations.getId());
				if(userRole1 != null){
					user.setUserType(userRole1.getUserType());
				}
		  }
		 
		 userRole.setAllReportingManagers(userList);
		 
		 resultJson = toJSON(userRole);
		 return resultJson;
	  }

	 /**
	  * To terminate user by using User ID.
	  * @param userId
	  * @param reason
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/terminateUser", method = RequestMethod.POST)
		public @ResponseBody String terminateUser(
				@RequestParam(value = "userId", required = false) Long userId,@RequestParam(value="reason", required=false) String reason, HttpServletRequest request) throws Exception{
				OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(userId, orgLocations.getId());
				if(userRole != null){
					userRole.setIsPrimary(0);
					userRole.setPreviousUserState(userRole.getUserState());
					userRole.setUserState(Status.INACTIVE);
					userRole.setTerminateReason(reason);
					userRoleService.addUserRole(userRole);
					
					//to assign primary role to another user
					UserRole existinguserRole = userRoleService.getNotPrimaryUser(userId);
					if(existinguserRole != null){
						existinguserRole.setIsPrimary(1);
						userRoleService.addUserRole(existinguserRole);
					}
					
				}
			
				return "success";
		}
	 
	 /**
	  * To update user and employee By User ID.
	  * @param json
	  * @param userId
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	 @ResponseBody String updateUser(@RequestParam(value="json", required=false) String json,@RequestParam(value = "userId", required = false) Long userId,@RequestParam(value="emailId", required=false) String emailId,HttpServletRequest request) throws Exception{

				
				 User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				
				User user = userService.getUserById(userId);
				UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocations.getId());
				

				json = "{"+json+"}";
			    JSONObject jsonObj = new JSONObject(json);
			    String password = "";
			    boolean isUserActive = false;
			    
				if(user != null){
					user.setFirstName(jsonObj.getString("FirstName"));
					user.setLastName(jsonObj.getString("LastName"));
					user.setMobilePhone(jsonObj.getString("PhoneNumber"));
					if(jsonObj.has("reportingManager") && !isNullOrEmpty(jsonObj.getString("reportingManager"))){
						User reportingManager = new User();
						reportingManager.setId(Long.parseLong(jsonObj.getString("reportingManager")));
						userRole.setReportingManager(reportingManager);
					}
					
					isUserActive = userRoleService.isUserActive(user.getId());
					if(isUserActive == false && !emailId.equalsIgnoreCase(jsonObj.getString("EmailId"))){
						//generate random password
						user.setEmail(jsonObj.getString("EmailId"));
						user.setUserName(jsonObj.getString("EmailId"));
						password = SourceLeadRandomCodeHelper.generateRandomPassword();
			 			user.setPassword(sourceleadPasswordEncoder.encode(password));
			 			user.setIsMailSent(Status.Y);
					}
					user.setUpdatedBy(loginUser.getUserCode());
					user.setUpdatedDate(commonUtil.currentDate());
					
					userService.addUser(user);
					userRoleService.addUserRole(userRole);
					
					if(isUserActive == false && !emailId.equalsIgnoreCase(jsonObj.getString("EmailId"))){
						userService.sendEmail(user,loginUser,password,orgLocations.getId());
                     }
				}
			    Employees employees = employeesService.getEmployeeByUserId(userId);
			    if(employees !=  null){
			    	employees.setEmployeeId(jsonObj.getString("EmployeeId"));
					employees.setRate(jsonObj.getString("Rate"));
					employees.setRatePer(jsonObj.getString("RatePer"));
					employees.setDailyHours(jsonObj.getString("DayHours"));
					employees.setInvoiceFrequency(jsonObj.getString("InvoiceFrequency"));
					if(jsonObj.has("DateofJoin") && !isNullOrEmpty(jsonObj.getString("DateofJoin"))){
						try {
							   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							   employees.setHireDate(sdf.parse(jsonObj.getString("DateofJoin")));
							} catch (Exception e) {
							  e.printStackTrace();
							}
					}
					employees.setUpdatedBy(loginUser.getUserCode());
					employees.setUpdatedDate(commonUtil.currentDate());
					employeesService.updateEmployee(employees);
			    }
				return "success";
		}
	
	 /**
	 * to get the start date and end date of the particular project by projectId
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
		//to get a start date and end date of the project
	 @RequestMapping(value = "/getStartAndEndOfProject" , method = RequestMethod.POST)
	 public @ResponseBody String getStartAndEndOfProject(@RequestParam(value="projectId", required=false) Long projectId) throws Exception {
		 	String resultJson = null;
		 	
		 	Projects projects = projectsService.getProjectById(projectId);
			if(projects.getStartDate()!=null){
				projects.setStartDateStr(com.bis.sourcelead.constants.Constants.DF_YMD.format(projects.getStartDate()));
			}
			if(projects.getEndDate()!=null){
				projects.setEndDateStr(com.bis.sourcelead.constants.Constants.DF_YMD.format(projects.getEndDate()));
			}

			resultJson = toJSON(projects);
			return resultJson;
	   }
	 
	private String toJSON(Object obj){
        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
            public HierarchicalStreamWriter createWriter(Writer writer) {
                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
            }
        });
        return xstream.toXML(obj);
    }
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

}
