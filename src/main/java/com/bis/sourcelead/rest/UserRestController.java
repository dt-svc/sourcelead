package com.bis.sourcelead.rest;

import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectMembersHistory;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Primary;
import com.bis.sourcelead.enums.Project_Status;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;


@RestController
public class UserRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	 
	@Autowired
	UserService userService;
	
	@Autowired
	private ProjectsService projectsService;
	
	@Autowired
	private ProjectMembersService projectMembersService;
	
	@Autowired
	private ProjectMembersHistoryService projectMembersHistoryService;
	
	@Autowired
	public SubmissionApproverHierarchyService submissionApproverHierarchyService;
	
	@Autowired
	public TimesheetSubmissionsService timesheetSubmissionsService;
	
	@Autowired
	private ProjectApproverHierarchyService projectApproverHierarchyService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	EmployeesService employeesService;
	
	@Autowired
	private SourceleadPasswordEncoder sourceleadPasswordEncoder;
	
	@Autowired
	RolesService rolesService;
	
	
	/**
	 * To get All Approvers
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getUnassignedProjects")
	public @ResponseBody String getUnassignedProjects(@RequestParam(value="userId", required=false) Long userId,HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 List<Projects> projectsList = projectsService.getProjectByOrgLocationsIdAndUserId(orgLocations.getId(),userId);
		String resultJson = toJSON(projectsList);
		return resultJson;

	}	
	
	/**
	 * To create new project
	 * @param json
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addProject" , method = RequestMethod.POST)
	 public @ResponseBody  String saveProject(@RequestParam(value="json", required=false) String json, HttpServletRequest request) throws Exception {
		
	     OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 String resultJson = null;
			
		 List<Projects> projectsList = projectsService.getProjectByOrgLocationsId(orgLocations.getId());
		
		 resultJson = toJSON(projectsList);
		 return resultJson;
	   }

	/**
	 * To Create user
	 * @param json
	 * @param request
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value = "/createUser" , method = RequestMethod.POST)
	 public  String createUser(@RequestParam(value="json", required=false) String json, HttpServletRequest request) throws Exception {
		 	
		 json = "{"+json+"}";
		 
		 JSONObject jsonObj = new JSONObject(json);
		 	
	 	 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	 	 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 	
	 	 //For Saving of User Details
	 	 userService.storeUser(jsonObj,user,orgLocations,"CREATEUSER","",new Org());
	 	
	 	
	 	 //For Saving of User Details
		// userService.createUser(jsonObj,user,orgLocations.getId());
		 SourceLeadSessionManager.setSessionAttribute(request, "userCreateMsg", "Successfully you have created a user");
		 return "usersList";
	   }
	 
	 /**
	  * To get the Project Manager Name by using projectId
	  * @param projectId
	  * @param request
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/getProjectManager" , method = RequestMethod.POST)
	 public @ResponseBody  String getProjectManager(@RequestParam(value="projectId", required=false) String projectId, HttpServletRequest request) throws Exception {
		 	
		 	String resultJson = null;
		 	ProjectApproverHierarchy ph = null;
		 	if(projectId != null && !"".equals(projectId)){
		 		ph = projectApproverHierarchyService.getLevelOneProjectApproverByProjectId(Long.parseLong(projectId));
		 	}
		 	ph.setFullName(ph.getApproverUserId().getFirstName() +" "+ph.getApproverUserId().getLastName());
		 	resultJson = toJSON(ph);
			return resultJson;
	   }
	 
	 /**
	  * To  validate user email
	  * @param EmailId
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/validateUserEmail" , method = RequestMethod.POST)
	 public @ResponseBody  String validateUserEmail(@RequestParam(value="EmailId", required=false) String EmailId) throws Exception {
		 	
		 	String result = null;
		 	User user = userService.findByEmail(EmailId);
		 	
		 	if (user != null) {
				result = "true";
			}else{
				result = "false";
			}
			return result;
	   }
	 
	 
	 /**
	  * To validate employee Id
	  * @param EmployeeId
	  * @param request
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/validateEmployeeId" , method = RequestMethod.POST)
	 public @ResponseBody  Boolean validateEmployeeId(@RequestParam(value="EmployeeId", required=false) String EmployeeId, HttpServletRequest request) throws Exception {
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 	Boolean flag = employeesService.validateEmployeeId(EmployeeId,orgLocations.getId());
		 	
			return flag;
	   }
	 
	 
	 /**
	  * To validate existing employee Id
	  * @param id
	  * @param userId
	  * @param EmployeeId
	  * @param request
	  * @return
	  * @throws Exception
	  */
		 @RequestMapping(value = "/validateExistingEmployeeId" , method = RequestMethod.POST)
		 public @ResponseBody  Boolean validateExistingEmployeeId(@RequestParam(value="id", required=false) Long id,@RequestParam(value="userId", required=false) Long userId,@RequestParam(value="EmployeeId", required=false) String EmployeeId, HttpServletRequest request) throws Exception {
			    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			   Employees employees = employeesService.getEmployeeByEmployeeIdAndUserIdAndOrgLocationsId(EmployeeId, userId, orgLocations.getId());
			    Boolean flag = false;
			    if(employees == null){
			    	flag = employeesService.validateEmployeeId(EmployeeId,orgLocations.getId());
			    }
			 	
				return flag;
		   }
	 
		 
		 @RequestMapping(value = "/validateExistingEmployeeEmailId" , method = RequestMethod.POST)
		 @ResponseBody  Boolean validateExistingEmployeeEmailId(@RequestParam(value="id", required=false) Long id,@RequestParam(value="userId", required=false) Long userId,@RequestParam(value="EmailId", required=false) String emailId, HttpServletRequest request) throws Exception {
			    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			    Boolean flag = false;
 
			    if(emailId != null){
			    	flag = employeesService.validateEmployeeEmailId(userId,emailId);
			    }
			 	
				return flag;
		   }
		 
		 /**
		  * To get the Project Manager Name by using projectId
		  * @param currentApproverId
		  * @param request
		  * @return
		  * @throws Exception
		  */
		@RequestMapping(value = "/getApproversByUserId", method = RequestMethod.POST)
		public @ResponseBody String getApproversByUserId(
				@RequestParam(value = "currentApproverId", required = false) Long currentApproverId,
				HttpServletRequest request) throws Exception {
			String resultJson = null;
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
					"orgLocations");
			List<User> userList = userService.getUsersByUserRolesAndOrgLocationIdAndNoCurrentUser(currentApproverId,
					orgLocations.getId());
			resultJson = toJSON(userList);
			return resultJson;
		}

		/**
		 * To get All Approvers
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/getAllApprovers")
		public @ResponseBody String getAllApprovers(HttpServletRequest request) throws Exception {
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
					"orgLocations");
			String resultJson = null;
			List<User> userList = userService.getUsersByUserRolesAndOrgLocationId(orgLocations.getId());
			resultJson = toJSON(userList);
			return resultJson;

		}	 
		
	/**
	 * To validate Project SequeneNumber 
	 * @param ProjectSequeneNumber
	 * @param request
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value = "/validateProjectSequeneNumber" , method = RequestMethod.POST)
	 public @ResponseBody  Boolean validateProjectSequeneNumber(@RequestParam(value="ProjectSequeneNumber", required=false) String ProjectSequeneNumber, HttpServletRequest request) throws Exception {
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 	Boolean flag = projectsService.validateProjectSequeneNumber(ProjectSequeneNumber,orgLocations.getId());
		 	
			return flag;
	   }
	 
	 
	 /**
	  * To re send email to user
	  * @param userId
	  * @param request
	  * @return
	  * @throws Exception
	  */
	  @RequestMapping(value = "/resendInvitationEmail" , method = RequestMethod.POST)
	  public @ResponseBody  String resendInvitationEmail(@RequestParam(value="userId", required=false) String userId, HttpServletRequest request) throws Exception {
		    
		    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    //get user by userId
		    User user = userService.getUserById(Long.parseLong(userId));
		    
		    //generate random password
		    String password = SourceLeadRandomCodeHelper.generateRandomPassword();
	 		if(user != null){
	 			user.setPassword(sourceleadPasswordEncoder.encode(password));
	 			user.setIsMailSent(Status.Y);
	 			userService.addUser(user);
	 			userService.sendEmail(user,loginUser,password);
	 		}
		
	 	  return "success";
	    }
		 
		 
	 	/**
	 	 * get the list of all projects of the particular user
	 	 * @param request
	 	 * @param userId
	 	 * @return
	 	 * @throws Exception

	 	 */
		 @RequestMapping(value = "/getProjectDetailsById", method = RequestMethod.POST)
		 @ResponseBody String getProjectDetailsById(HttpServletRequest request,
					@RequestParam(value = "userId", required = false) Long userId,
					@RequestParam(value = "filterValue", required = false) String filterValue) throws Exception {

				List<ProjectMembers> allProjectsList = projectMembersService.getAllProjectsByUserIdAndStatus(userId, filterValue);
				return toJSON(allProjectsList);

			}
		 
		 /**
			 * to assign the project to the particular user
			 * @param json
			 * @param userId
			 * @param request
			 * @return
			 * @throws Exception
			 */
			@RequestMapping(value = "/saveAssignProject", method = RequestMethod.POST)
			@ResponseBody String saveAssignProject(@RequestParam(value = "json", required = false) String json,
					@RequestParam(value = "userId", required = false) Long userId, HttpServletRequest request)
							throws Exception {
				json = "{" + json + "}";
				JSONObject jsonObj = new JSONObject(json);
				Employees employee = employeesService.getEmployeeByUserId(userId);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Projects projects = projectsService.getProjectById(jsonObj.getLong("projectId"));
				ProjectMembers projectMembers = new ProjectMembers();
				projectMembers.setEmployee(employee);
				projectMembers.setProjects(projects);
				projectMembers.setTimesheetFrequency("weekly");
				
				User user = userService.getUserById(userId);
				
				HashSet<ProjectMembersHistory> pmhList = new HashSet<ProjectMembersHistory>();
				ProjectMembersHistory pmh = new ProjectMembersHistory();
				pmh.setProjectMemberId(projectMembers);
				pmh.setStatus(Constants.PROJECT_MEMBER_HISTORY_OPEN);
				if (jsonObj.has("StartDate") && !isNullOrEmpty(jsonObj.getString("StartDate"))) {
					try {
						pmh.setProjectAssignedDate(sdf.parse(jsonObj.getString("StartDate")));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				if (jsonObj.has("EndDate") && !isNullOrEmpty(jsonObj.getString("EndDate"))) {
					try {
						pmh.setProjectTerminationDate(sdf.parse(jsonObj.getString("EndDate")));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

	            pmh.setCreatedDate(new Date());
				pmh.setUpdatedDate(new Date());
				pmhList.add(pmh);
				
				if (employee.getRate() != null && !"".equalsIgnoreCase(employee.getRate())) {
					projectMembers.setCurrentBillRate(Float.parseFloat(employee.getRate()));
				}
				// need change role
				projectMembers.setRole(user.getUserType());
				if (employee.getRatePer() != null && !"".equalsIgnoreCase(employee.getRatePer())) {
					projectMembers.setRateType(employee.getRatePer());
				}
				projectMembers.setStatus(Constants.MEMBER_ACTIVE);
				projectMembers.setCreatedDate(new Date());
				projectMembers.setUpdatedDate(new Date());
				projectMembers.setMemberHistoryList(pmhList);
				
				projectMembersService.saveProjectMember(projectMembers);
				commonUtil.storeTimesheetSubmissionRecords(user);
				return "success";
			}
			/**
			 * to save the reason when we terminate the user from project 
			 * @param projectMemberId
			 * @param reason
			 * @return
			 */
			@RequestMapping(value = "/saveReason", method = RequestMethod.POST)
			@ResponseBody String saveReason(
					@RequestParam(value = "projectMemberId", required = false) Long projectMemberId,
					@RequestParam(value = "reason", required = false) String reason,HttpServletRequest request) {
				   User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				   Integer updatedCount =  0;
				   try {
				  	  ProjectMembers projectMembers =	projectMembersService.getProjectMemberById(projectMemberId);
				  	  if(projectMembers != null){
				  		  String status = Constants.MEMBER_IN_ACTIVE;
					  	  projectMembers.setTerminateReason(reason);
						  projectMembers.setUpdatedDate(new Date());
						  projectMembers.setUpdatedBy(user.getUserCode());
					  	  HashSet<ProjectMembersHistory> memberHistoryList = new HashSet<ProjectMembersHistory>();
					  	  ProjectMembersHistory pmh = projectMembersHistoryService.getProjectMembersOpenHistoryByProjectMemberId(projectMembers.getId());
						  if(pmh != null){
							  pmh.setProjectTerminationDate(new Date());
							  pmh.setUpdatedDate(new Date());
							  pmh.setUpdatedBy(user.getUserCode());
							  memberHistoryList.add(pmh);
							  projectMembers.setMemberHistoryList(memberHistoryList);
						  }
						  projectMembers.setStatus(status);
						  projectMembersService.saveProjectMember(projectMembers);
						  commonUtil.terminateProjectMemberByProjectMemberId(projectMemberId,Primary.Yes);
					      updatedCount = 1;
				  	   }
					
				    } catch (Exception e) {
				   }
				
				logger.info("updated records" + updatedCount);
				return "success";
			}
		 
		 /**
		  * to update the end date of ProjectMembers in assign project
		  * @param endDate
		  * @param projectMemberId
		  * @return

		 -	 * @throws Exception

		 -	 */

		@RequestMapping(value = "/updateEndDateByProjectMemberId", method = RequestMethod.POST)
		public String updateEndDateByProjectMemberId(@RequestParam(value = "endDate", required = false) String endDate,

				@RequestParam(value = "projectMemberId", required = false) Long projectMemberId) throws Exception {

					ProjectMembers projectMembers =	projectMembersService.getProjectMemberById(projectMemberId);
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					if (endDate != null && !"".equals(endDate) && !endDate.equalsIgnoreCase("--")) {
						ProjectMembersHistory pmh = projectMembersHistoryService.getProjectMembersOpenHistoryByProjectMemberId(projectMembers.getId());
						if(pmh == null){
							pmh = projectMembersHistoryService.getProjectMembersRecentClosedByProjectMemberId(projectMembers.getId());
						}
						Date endDate1=sdf.parse(endDate);
						if(pmh.getProjectTerminationDate()== null){
							/*projectMembersService.updateEndDateOfProjectMemberId(endDate1, projectMemberId);*/
							projectMembersHistoryService.updateEndDateOfProjectMemberId(endDate1, projectMemberId);
						}

						if (pmh.getProjectTerminationDate() != null && pmh.getProjectTerminationDate().compareTo(sdf.parse(endDate)) != 0) {
							projectMembersHistoryService.updateEndDateOfProjectMemberId(endDate1, projectMemberId);
						}
						
						Date terminationDate = sdf.parse(endDate);
						Date currentDate = new  Date();
						if(currentDate.after(terminationDate) && !currentDate.equals(terminationDate)){
							commonUtil.terminateProjectMemberByProjectMemberId(projectMemberId,Primary.Yes);
						}
						else{
							commonUtil.inactivateTimesheetSubmissionsBasedOnMemberIdAndMemberTerminationDate(projectMemberId, terminationDate);
						}
					}
					return "updated";
		       }
		
		
		
		
		/**
		 * to assign the project to the particular user
		 * @param json
		 * @param userId
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/saveReAssignProject", method = RequestMethod.POST)
		@ResponseBody String saveReAssignProject(@RequestParam(value = "json", required = false) String json,
				@RequestParam(value = "projectMemberId", required = false) Long projectMemberId, HttpServletRequest request)
						throws Exception {
			json = "{" + json + "}";
			JSONObject jsonObj = new JSONObject(json);
			
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			 try {
			  	  ProjectMembers projectMembers =	projectMembersService.getProjectMemberById(projectMemberId);
			  	  if(projectMembers != null){
			  		  String status = Constants.MEMBER_ACTIVE;
					  projectMembers.setUpdatedDate(new Date());
					  projectMembers.setUpdatedBy(user.getUserCode());
					  
					  HashSet<ProjectMembersHistory> memberHistoryList = new HashSet<ProjectMembersHistory>(); 
					  ProjectMembersHistory pmh = new  ProjectMembersHistory(); 
					  
					  pmh.setProjectMemberId(projectMembers);
			  		  pmh.setStatus(Constants.PROJECT_MEMBER_HISTORY_OPEN);
			  		  String pattern = "yyyy-MM-dd";
		    	      SimpleDateFormat format = new SimpleDateFormat(pattern);
		    	      if(jsonObj.has("Re-StartDate")){
		    	    	  Date startDate = format.parse(jsonObj.getString("Re-StartDate"));
						  pmh.setProjectAssignedDate(startDate);
		    	      }
		    	      if(jsonObj.has("Re-EndDate")){
		    	    	  Date endDate = format.parse(jsonObj.getString("Re-EndDate"));
						  pmh.setProjectTerminationDate(endDate);
		    	      }
		    	      pmh.setCreatedDate(commonUtil.currentDate());
		    	    	if(user.getUserCode() != null){
		    	    		pmh.setCreatedBy(user.getUserCode());	
		    	    	}
		    	    	
					  pmh.setUpdatedDate(commonUtil.currentDate());
					  pmh.setUpdatedBy(user.getUserCode());
					  memberHistoryList.add(pmh);
					  projectMembers.setMemberHistoryList(memberHistoryList);
					  
					  projectMembers.setStatus(status);
					  projectMembers =  projectMembersService.saveProjectMember(projectMembers);
					  projectMembers = projectMembersService.getProjectMemberById(projectMembers.getId());
					  commonUtil.storeTimesheetSubmissionRecords(projectMembers.getEmployee().getUser());
			  	   }
				
			    } catch (Exception e) {
			   }
			
			return "success";
		}
		
		
		
		/**
		 * 
		 * @param userID
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/reActiveUser", method = RequestMethod.POST)
        public String reActiveUser(@RequestParam(value = "userId", required = false) String userID) throws Exception {
                    User user = userService.getUserById(Long.parseLong(userID));
                    logger.info("The user state is "+user.getUserState());
                    user.setUserState(user.getPreviousUserState());
                    user.setPreviousUserState(user.getUserState());
                    userService.addUser(user);
                    return "updated";
               }
		
		
		/**
		 * To get the reporting managers list based on role type
		 * @author Prasad salina
		 * @param role
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/getReportingManager", method = RequestMethod.POST)
		public @ResponseBody String getReportingManager(
				@RequestParam(value = "role", required = false) String role, HttpServletRequest request) throws Exception {
			String resultJson = null;
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			List<User> userList = userService.getManagersByUserRolesAndOrgLocationId(role, orgLocations.getId());
			resultJson = toJSON(userList);
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
	 
	 protected static boolean isNullOrEmpty(String str) {
			return (str == null || "".equals(str.trim()));
		}
}
