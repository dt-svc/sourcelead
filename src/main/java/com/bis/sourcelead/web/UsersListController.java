package com.bis.sourcelead.web;

import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.Constants;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

@Controller
@RestController
public class UsersListController {
	
private static final Logger logger = LoggerFactory.getLogger(UsersListController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	RolesService rolesService;
	
	@Autowired
	EmployeesService employeesService;
	
	@Autowired
	UserService userService;

	@Autowired
	ProjectsService projectsService;
	
	@Autowired
	TimesheetSubmissionsService timesheetSubmissionsService;
	
	@Autowired
	private SourceleadPasswordEncoder sourceleadPasswordEncoder;
		
	/**
	 * To forward users List jsp page
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/usersList")
	public ModelAndView usersList(HttpServletRequest request) {
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
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
			rolesList = rolesService.getRoles(orgLocations.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
		String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "userCreateMsg");
		if(!isNullOrEmpty(msg)){
			model.addObject("msg", msg);
			SourceLeadSessionManager.removeSessionAttribute(request, "userCreateMsg");
		}
		
		model.addObject("rolesList", rolesList);
		
		return model;

	}
	
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
		
		
	if(!isNullOrEmpty(loginUserRole) && loginUserRole.equalsIgnoreCase("ROLE_ADMIN")){
		try {
			
			if(!isNullOrEmpty(userState) && isNullOrEmpty(userRole) && isNullOrEmpty(searchByName)){
				rolesList = userRoleService.getUsersListByUserStateAndLocationId(userState, orgLocations.getId(),user.getId());
			}
			else if((isNullOrEmpty(userRole) && isNullOrEmpty(searchByName)) || (userRole.equalsIgnoreCase("All") && isNullOrEmpty(searchByName))){
				rolesList = userRoleService.getUsersListByLocationId(orgLocations.getId(),userState,user.getId());
			}
			else if(!isNullOrEmpty(userRole) && !"All".equalsIgnoreCase(userRole) && isNullOrEmpty(searchByName)){
				rolesList = userRoleService.getUsersListByRoleAndLocationId(Long.parseLong(userRole), orgLocations.getId(),userState,user.getId());
			}
			else if((!isNullOrEmpty(searchByName) && isNullOrEmpty(userRole)) || ("All".equalsIgnoreCase(userRole) && !isNullOrEmpty(searchByName))){
				rolesList = userRoleService.getUsersListByUserNameAndLocationId(searchByName, orgLocations.getId(),userState,user.getId());
			}
			else if(!isNullOrEmpty(userRole) && !"All".equalsIgnoreCase(userRole) && !isNullOrEmpty(searchByName)){
				rolesList = userRoleService.getUsersListByRoleAndNameAndLocationId(Long.parseLong(userRole), searchByName, orgLocations.getId(),userState,user.getId());
			}
			
			for(UserRole role : rolesList){
				role.setUserRoleName(role.getRoles().getDisplayName());
				userRolesList.add(role);
			}
			 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }
	
	else if(!isNullOrEmpty(loginUserRole) && loginUserRole.equalsIgnoreCase("ROLE_APPROVER")){
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
		
	}
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
		 Employees employee = employeesService.getEmployeeByUserId(userId);
		 if(employee.getHireDate() != null){
			 employee.setHireDateStr(com.bis.sourcelead.constants.Constants.DF_YMD.format(employee.getHireDate())); 
		 }else{
			 employee.setHireDateStr("");
		 }
		 resultJson = toJSON(employee);
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
				@RequestParam(value = "userId", required = false) Long userId,@RequestParam(value="reason", required=false) String reason) throws Exception{
				User user = userService.getUserById(userId);
				if(user != null){
					user.setTerminateReason(reason);
					user.setUserState(Status.INACTIVE);
					userService.addUser(user);
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
				User user = userService.getUserById(userId);
				 User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				json = "{"+json+"}";
			    JSONObject jsonObj = new JSONObject(json);
			    String password = "";
				if(user != null){
					user.setFirstName(jsonObj.getString("FirstName"));
					user.setLastName(jsonObj.getString("LastName"));
					user.setMobilePhone(jsonObj.getString("PhoneNumber"));
					if(user.getUserState() != null && !"".equals(user.getUserState()) && Status.NOT_LOGGEDIN.equals(user.getUserState())&& !emailId.equalsIgnoreCase(jsonObj.getString("EmailId"))){
						//generate random password
						user.setEmail(jsonObj.getString("EmailId"));
						user.setUserName(jsonObj.getString("EmailId"));
						password = SourceLeadRandomCodeHelper.generateRandomPassword();
			 			user.setPassword(sourceleadPasswordEncoder.encode(password));
			 			user.setIsMailSent(Status.Y);
					}
					userService.addUser(user);
					if(user.getUserState() != null && !"".equals(user.getUserState()) && Status.NOT_LOGGEDIN.equals(user.getUserState())&& !emailId.equalsIgnoreCase(jsonObj.getString("EmailId"))){
						userService.sendEmail(user,loginUser,password);
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
					employeesService.updateEmployee(employees);
			    }
				return "success";
		}
	 //test
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
