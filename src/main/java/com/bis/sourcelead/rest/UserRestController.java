package com.bis.sourcelead.rest;

import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.elk.index.ProjectsIndex;
import com.bis.sourcelead.elk.index.helper.ProjectsIndexHelper;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectMembersHistory;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Primary;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
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
	private UserRoleService userRoleService;
	
	@Autowired
	RolesService rolesService;
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	 ProjectsIndexHelper projectsIndexHelper;
	 
	@Autowired
	SourceleadELKBaseIndexer<ProjectsIndex> sourceleadELKBaseIndexer;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	
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
	
	@RequestMapping(value = "/getActiveAssociateList", method = RequestMethod.POST)
	public @ResponseBody String getCandidatesList(
		   @RequestParam(value = "employeementType", required = false) String employeementType, HttpServletRequest request) throws Exception {
		String resultJson = null;
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		List<Candidate> candidateIdsList = candidateService.getAllActiveAssociatesByOrglocationId(orgLocations.getId(),employeementType);
		resultJson = toJSON(candidateIdsList);
		return resultJson;
	}
	
	@RequestMapping(value = "/getActiveAssociateDetails",  method = RequestMethod.POST)
    public @ResponseBody String getAssociateDetailsById(@RequestParam(value="candidateId", required=false) Long candidateId) throws Exception {
		
		Candidate candidate   = candidateService.getCandidateById(candidateId);
			
	   /*ArrayList<String> associateList = new ArrayList<String>();
	   Candidate candidate = null;
		try {
			candidate = candidateService.getCandidateById(candidateId);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		
		if(null != candidate)
		{
			associateList.add(candidate.getFirstName());
			associateList.add(candidate.getLastName());
			associateList.add(candidate.getEmail());
			associateList.add(candidate.getMobilePhone());
			
		}*/
		
		return commonUtil.toJSON(candidate);
		
	}

	/**
	 * To Create user
	 * @param json
	 * @param request
	 * @return
	 * @throws Exception
	 */
	 @PreAuthorize("hasRole('ROLE_CREATE_USER')")
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
		 	if(ph.getApproverUserId().getId()!=null){
		 		StringBuilder nameSB = new StringBuilder();
		 		ph.setFullName(nameSB.append(ph.getApproverUserId().getFirstName()).append(" ").append(ph.getApproverUserId().getLastName()).toString());
		 	}
		 	
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
	 public @ResponseBody  String validateUserEmail(@RequestParam(value="EmailId", required=false) String EmailId, HttpServletRequest request) throws Exception {
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 	String result = null;
		 	User user = userService.findByEmailAndOrgLocationsId(EmailId,orgLocations.getId());
		 	
		 	if (user != null) {
				result = "true";
			}else{
				result = "false";
			}
			return result;
	   }
	 
	 
	 @RequestMapping(value = "/getUserByUserEmail" , method = RequestMethod.POST)
	 public @ResponseBody  String getUserByUserEmail(@RequestParam(value="EmailId", required=false) String EmailId, HttpServletRequest request) throws Exception {
		 	String resultJson = null;
		 	User user = userService.findByEmail(EmailId);
		 	resultJson = toJSON(user);
			return resultJson;
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
			/*List<User> userList = userService.getUsersByUserRolesAndOrgLocationIdAndNoCurrentUser(currentApproverId,
					orgLocations.getId());*/
			
			List<User> userList = userService.getTabAccessOwnersByOrgLocationIdAndTabIdAndNoCurrentUser(currentApproverId,orgLocations.getId(),Long.valueOf(Constants.TAB_TIMESHEET_APPROVAL));
			
			
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
	  public @ResponseBody  String resendInvitationEmail(@RequestParam(value="userId", required=false) String userId,
			  @RequestParam(value="orgId", required=false) Long orgId,HttpServletRequest request) throws Exception {
		    
		    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    //get user by userId
		    User user = userService.getUserById(Long.parseLong(userId));
		    
		    //generate random password
		    String password = SourceLeadRandomCodeHelper.generateRandomPassword();
		    boolean isUserActive = false;
	 		if(user != null){
	 			isUserActive = userRoleService.isUserActive(user.getId());
	 			if(isUserActive == false){
	 				user.setPassword(sourceleadPasswordEncoder.encode(password));
	 			}
	 			user.setIsMailSent(Status.Y);
	 			userService.addUser(user);

	 			userService.sendEmailFromSourcelead(user,loginUser,password,orgLocations.getId(),orgId);

	 			/*
				 * Update USER DB data as ElasticSearch Documents
				 */
	 			  UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocations.getId());
	 			  if(null != userRole){
	 				 Employees employee = employeesService.getEmployeeByUserIdAndOrgLocationsId(userRole.getUser().getId(), orgLocations.getId());
	    			  if(null != employee){
	    				  userService.saveOrUpdateUserElkDocument(userRole,employee,user.getUserCode());
	    			  }
	 			  }
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
				//projectMembers.setTimesheetFrequency("weekly");
				
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
				//commonUtil.storeTimesheetSubmissionRecords(user);
				return "success";
			}
			/**
			 * to save the reason when we terminate the user from project 
			 * @param projectMemberId
			 * @param reason
			 * @return
			 */
			@PreAuthorize("hasAnyRole('ROLE_VIEW_DELETE_PROJECT_EMPLOYEES')")
			@RequestMapping(value = "/saveReason", method = RequestMethod.POST)
			@ResponseBody String saveReason(
					@RequestParam(value = "projectMemberId", required = false) Long projectMemberId,
					@RequestParam(value = "projectId", required = false) Long projectId,
					@RequestParam(value = "reason", required = false) String reason,HttpServletRequest request) {
				   User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				   OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
							"orgLocations");
				   Integer updatedCount =  0;
				   try {
				  	  ProjectMembers projectMembers =	projectMembersService.getProjectMemberById(projectMemberId);
				  	  if(projectMembers != null){
				  		  String status = Constants.MEMBER_IN_ACTIVE;
					  	  projectMembers.setTerminateReason(reason);
						  projectMembers.setUpdatedDate(new Date());
						  projectMembers.setUpdatedBy(user.getUserCode());
						  projectMembers.setStatus(status);
						  projectMembersService.saveProjectMember(projectMembers);
					  	  ProjectMembersHistory pmh = projectMembersHistoryService.getProjectMembersOpenHistoryByProjectMemberId(projectMembers.getId());
						  if(pmh != null){
							  pmh.setProjectTerminationDate(new Date());
							  pmh.setUpdatedDate(new Date());
							  pmh.setUpdatedBy(user.getUserCode());
							  pmh.setStatus(Constants.PROJECT_MEMBER_HISTORY_CLOSED);
							  pmh =  projectMembersHistoryService.updateProjectMembersHistory(pmh);
						  }
						  
						  commonUtil.terminateProjectMemberByProjectMemberId(projectMemberId,Primary.Yes);
					      updatedCount = 1;
					      terminatedMailToEmployee(user,pmh,orgLocations);
					      
					      List<ProjectApproverHierarchy>  mangerList = projectApproverHierarchyService.getApproverUsersByProjectId(pmh.getProjectMemberId().getProjects().getId());
					      for(ProjectApproverHierarchy pah : mangerList){
					    	  if(user.getId() != pah.getApproverUserId().getId()){
					    		  employeeTerminationMailToProjectManager(user,pmh,orgLocations);
					    	  }
					      }
					      
					      
				  	   }
					   if(null != projectId){
							Projects projects = projectsService.getProjectById(projectId);
							if(null != projects){
								Collection<ProjectMembers> projectMembersList = projectMembersService.getAllProjectMembersByProjectId(projectId);
								ProjectsIndex projectsIndex = projectsIndexHelper.getProjectsIndexFromProjects(projects, null, projectMembersList);
								//Elastic Search Document Saving
								sourceleadELKBaseIndexer.saveOrUpdateElkDocument(projectsIndex, Constants.ELK_PROJECT_INDEX, Constants.ELK_PROJECT_TYPE,projects.getId().toString());
							}
					   }
				    } catch (Exception e) {
				   }
				return "success";
			}
			
		 
		 private void employeeTerminationMailToProjectManager(User loginUser, ProjectMembersHistory pmh,
					OrgLocations orgLocations) throws Exception {
			 
			 User user=null;
			 List<ProjectApproverHierarchy>  mangerList = projectApproverHierarchyService.getApproverUsersByProjectId(pmh.getProjectMemberId().getProjects().getId());
			 for (ProjectApproverHierarchy projectApproverHierarchy : mangerList) {
				 if(null != pmh.getProjectMemberId()){
					 user = userService.getUserById(projectApproverHierarchy.getApproverUserId().getId());
			}
			 
			 EmailMessage emailMessage = new EmailMessage();
			 emailMessage.setFirstName(user.getFirstName());
			 emailMessage.setLastName(user.getLastName());
			 emailMessage.setToEmail(user.getEmail());
			 emailMessage.setFromEmail(loginUser.getEmail());
			 emailMessage.setCreatedBy(loginUser.getUserCode());
			// StringBuilder msgBuild = new StringBuilder();
			// emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
			// emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
			//setting the class filed values
			 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 
			 if(null != pmh.getProjectMemberId().getEmployee().getUser()){
				 if(null != pmh.getProjectMemberId().getEmployee().getUser().getLastName() && !"".equalsIgnoreCase(pmh.getProjectMemberId().getEmployee().getUser().getLastName())){
					 StringBuilder empName = new StringBuilder();
					 globalVarsMap.put("Employee Name", empName.append(pmh.getProjectMemberId().getEmployee().getUser().getFirstName()).append(pmh.getProjectMemberId().getEmployee().getUser().getLastName()).toString());
				 }
			 else{
				 globalVarsMap.put("Employee Name", pmh.getProjectMemberId().getEmployee().getUser().getFirstName());
				}
			 }
			 
			 if (null != user.getLastName() && !"".equalsIgnoreCase(user.getLastName())) {
				 StringBuilder mangerName = new StringBuilder();
				 globalVarsMap.put("Manager", mangerName.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
				 } else {
					 globalVarsMap.put("Manager", user.getFirstName());
				}
			
			 if (null != loginUser.getLastName() && !"".equalsIgnoreCase(loginUser.getLastName())) {
				 StringBuilder loginName = new StringBuilder();
				 globalVarsMap.put("LoginUser", loginName.append(loginUser.getFirstName()).append(loginUser.getLastName()).toString());
				 } else {
					 globalVarsMap.put("LoginUser", loginUser.getFirstName());
				}
			
			 if (null != pmh.getProjectMemberId().getTerminateReason() && !"".equalsIgnoreCase(pmh.getProjectMemberId().getTerminateReason())) {
				 globalVarsMap.put("Reason", pmh.getProjectMemberId().getTerminateReason());
				 } else {
					 globalVarsMap.put("Reason", "--");
				}
			 
			
			 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
			
			 //getting the current EmailScenario by EmailScenario unique name and entity type
			 //using same migration which has been already used in updatedRequirementMailToManager method
			 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.EMPLOYEE_TERMINATION_MAIL_TO_MANAGER,Constants.ENTITY_TYPE_EMAIL);
			 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.EMPLOYEE_TERMINATION_MAIL_TO_MANAGER,Constants.ENTITY_TYPE_NOTIFICATION);

			 
			 //covert subject,body and signature
			 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
			 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
			 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
			 String url = "";
			 
			 String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getBody(),globalVarsMap);
			 notificationScenario.setBody(notificationbody);

			 //construct HTML template using required data
			 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLocations.getOrg());
			 emailMessage.setSubject(subject);
			 emailMessage.setMessageBody(emailTemplate);
			 
			 //calling the sendEmail method of emailScenerioUtil
			 emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(),iAdressArray,new ArrayList<EmailAttachment>());
			 String actionLink="";
			 emailScenerioUtil.sendNotification(actionLink, loginUser,user, projectApproverHierarchy.getProjectId().getOrgLocationsId(), notificationScenario);
			 }
			}

		private void terminatedMailToEmployee(User loginUser, ProjectMembersHistory pmh, OrgLocations orgLocations) throws Exception {
			 
			 User user=null;
			 if(null != pmh.getProjectMemberId()){
			 user = userService.getUserByUserCode(pmh.getProjectMemberId().getCreatedBy());
			 }
			 
			 EmailMessage emailMessage = new EmailMessage();
			 emailMessage.setFirstName(pmh.getProjectMemberId().getEmployee().getUser().getFirstName());
			 emailMessage.setLastName(pmh.getProjectMemberId().getEmployee().getUser().getLastName());
			 emailMessage.setToEmail(pmh.getProjectMemberId().getEmployee().getUser().getEmail());
			 emailMessage.setFromEmail(loginUser.getEmail());
			 emailMessage.setCreatedBy(loginUser.getUserCode());
			// StringBuilder msgBuild = new StringBuilder();
			// emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
			// emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
			//setting the class filed values
			 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 if(null!= pmh.getProjectMemberId().getEmployee()){
				 if(null != pmh.getProjectMemberId().getEmployee().getUser()){
					 if(null != pmh.getProjectMemberId().getEmployee().getUser().getLastName() && !"".equalsIgnoreCase(pmh.getProjectMemberId().getEmployee().getUser().getLastName())){
						 StringBuilder empName = new StringBuilder();
						 globalVarsMap.put("Employee Name", empName.append(pmh.getProjectMemberId().getEmployee().getUser().getFirstName()).append(pmh.getProjectMemberId().getEmployee().getUser().getLastName()).toString());
					 }	 
			 else{
				 globalVarsMap.put("Employee Name", pmh.getProjectMemberId().getEmployee().getUser().getFirstName());
				}
			 }
			 }
			 
			 if(null != pmh.getProjectMemberId().getProjects()){
				 if(null != pmh.getProjectMemberId().getProjects().getProjectName() && !"".equalsIgnoreCase(pmh.getProjectMemberId().getProjects().getProjectName())){
					 globalVarsMap.put("Project Name", pmh.getProjectMemberId().getProjects().getProjectName());
				}else{
					 globalVarsMap.put("Project Name", "--");
				 }
			 }
			 
			 if(null != pmh.getProjectAssignedDate()){
				 globalVarsMap.put("Project StartDate",pmh.getProjectAssignedDate().toString());
			 }else{
				 globalVarsMap.put("Project StartDate", "--");
			 }
			 
			 if(null != pmh.getProjectTerminationDate()){
				 globalVarsMap.put("Project EndDate",pmh.getProjectTerminationDate().toString());
			 }else{
				 globalVarsMap.put("Project EndDate", "--");
			 }
			 
			 if (null != user.getLastName() && !"".equalsIgnoreCase(user.getLastName())) {
				 StringBuilder mangerName = new StringBuilder();
				 globalVarsMap.put("Manager", mangerName.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
				 } else {
					 globalVarsMap.put("Manager", user.getFirstName());
				}
			
			 if (null != loginUser.getLastName() && !"".equalsIgnoreCase(loginUser.getLastName())) {
				 StringBuilder loginName = new StringBuilder();
				 globalVarsMap.put("LoginUser", loginName.append(loginUser.getFirstName()).append(loginUser.getLastName()).toString());
				 } else {
					 globalVarsMap.put("LoginUser", loginUser.getFirstName());
				}
			
			 if (null != orgLocations.getOrg()) {
				 if(null != orgLocations.getOrg().getOrgName() && !"".equalsIgnoreCase(orgLocations.getOrg().getOrgName())){
					 globalVarsMap.put("LoginOrg", orgLocations.getOrg().getOrgName());
				 } else {
					 globalVarsMap.put("LoginOrg", "--");
				}
			 }
			 
			
			 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
			
			 //getting the current EmailScenario by EmailScenario unique name and entity type
			 //using same migration which has been already used in updatedRequirementMailToManager method
			 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.EMPLOYEE_TERMINATION,Constants.ENTITY_TYPE_EMAIL);
			 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.EMPLOYEE_TERMINATION,Constants.ENTITY_TYPE_NOTIFICATION);

			 
			 //covert subject,body and signature
			 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
			 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
			 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
			 String url = "";
			 
			 String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getBody(),globalVarsMap);
			 notificationScenario.setBody(notificationbody);

			 //construct HTML template using required data
			 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLocations.getOrg());
			 emailMessage.setSubject(subject);
			 emailMessage.setMessageBody(emailTemplate);
			 
			 User toUser = pmh.getProjectMemberId().getEmployee().getUser();
			 
			 //calling the sendEmail method of emailScenerioUtil
			 emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(),iAdressArray,new ArrayList<EmailAttachment>());
			 String actionLink="";
			emailScenerioUtil.sendNotification(actionLink, loginUser,toUser, pmh.getProjectMemberId().getEmployee().getOrgLocations(), notificationScenario);

				
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
					
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
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
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
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
			  		  String pattern = "dd-MM-yyyy";
		    	      SimpleDateFormat format = new SimpleDateFormat(pattern);
		    	      if(jsonObj.has("Re-StartDate") && !isNullOrEmpty(jsonObj.getString("Re-StartDate"))){
		    	    	  Date startDate = format.parse(jsonObj.getString("Re-StartDate"));
						  pmh.setProjectAssignedDate(startDate);
		    	      }
		    	      if(jsonObj.has("Re-EndDate") && !isNullOrEmpty(jsonObj.getString("Re-EndDate"))){
		    	    	  Date endDate = format.parse(jsonObj.getString("Re-EndDate"));
						  pmh.setProjectTerminationDate(endDate);
		    	      }
		    	      pmh.setCreatedDate(commonUtil.currentDate());
		    	    	if(user.getUserCode() != null){
		    	    		pmh.setCreatedBy(user.getUserCode());	
		    	    	}
		    	    	
					  pmh.setUpdatedDate(commonUtil.currentDate());
					  pmh.setUpdatedBy(user.getUserCode());
					/*  memberHistoryList.add(pmh);
					  projectMembers.setMemberHistoryList(memberHistoryList);*/
					  pmh.setStatus(Constants.PROJECT_MEMBER_HISTORY_OPEN);
					  pmh =  projectMembersHistoryService.updateProjectMembersHistory(pmh);
					  
					  projectMembers.setStatus(status);
					  projectMembers =  projectMembersService.saveProjectMember(projectMembers);
					  projectMembers = projectMembersService.getProjectMemberById(projectMembers.getId());
					  //commonUtil.storeTimesheetSubmissionRecords(projectMembers.getEmployee().getUser());
					  reAssignMailToEmployee(user,pmh,orgLocations);
			  	   }
				
			    } catch (Exception e) {
			   }
			
			return "success";
		}
		
		
		
		private void reAssignMailToEmployee(User loginUser, ProjectMembersHistory pmh, OrgLocations orgLocations) throws Exception {

			 User user=null;
			 if(null != pmh.getProjectMemberId()){
			 user = userService.getUserByUserCode(pmh.getProjectMemberId().getCreatedBy());
			 }
			 
			 EmailMessage emailMessage = new EmailMessage();
			 emailMessage.setFirstName(pmh.getProjectMemberId().getEmployee().getUser().getFirstName());
			 emailMessage.setLastName(pmh.getProjectMemberId().getEmployee().getUser().getLastName());
			 emailMessage.setToEmail(pmh.getProjectMemberId().getEmployee().getUser().getEmail());
			 emailMessage.setFromEmail(loginUser.getEmail());
			 emailMessage.setCreatedBy(loginUser.getUserCode());
			// StringBuilder msgBuild = new StringBuilder();
			// emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
			// emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
			//setting the class filed values
			 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 
			 if(null!= pmh.getProjectMemberId().getEmployee()){
				 if(null != pmh.getProjectMemberId().getEmployee().getUser()){
					 if(null != pmh.getProjectMemberId().getEmployee().getUser().getLastName() && !"".equalsIgnoreCase(pmh.getProjectMemberId().getEmployee().getUser().getLastName())){
						 StringBuilder empName = new StringBuilder();
						 globalVarsMap.put("Employee Name", empName.append(pmh.getProjectMemberId().getEmployee().getUser().getFirstName()).append(pmh.getProjectMemberId().getEmployee().getUser().getLastName()).toString());
					 }else{
						 globalVarsMap.put("Employee Name", pmh.getProjectMemberId().getEmployee().getUser().getFirstName());
						}
				 }
			 }
			 
			 
			 if(null != pmh.getProjectMemberId().getProjects()){
				 if(null != pmh.getProjectMemberId().getProjects().getProjectName() && !"".equalsIgnoreCase(pmh.getProjectMemberId().getProjects().getProjectName())){
				 globalVarsMap.put("Project Name", pmh.getProjectMemberId().getProjects().getProjectName());
				}else{
					 globalVarsMap.put("Project Name", "--");
				 }
			 }
			 
			 if(null != pmh.getProjectAssignedDate()){
				 globalVarsMap.put("Project StartDate",pmh.getProjectAssignedDate().toString());
			 }else{
				 globalVarsMap.put("Project StartDate", "--");
			 }
			 
			 if(null != pmh.getProjectTerminationDate()){
				 globalVarsMap.put("Project EndDate",pmh.getProjectTerminationDate().toString());
			 }else{
				 globalVarsMap.put("Project EndDate", "--");
			 }
			 
			 if (null != user.getLastName() && !"".equalsIgnoreCase(user.getLastName())) {
				 StringBuilder mangerName = new StringBuilder();
				 globalVarsMap.put("Manager", mangerName.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
				 } else {
					 globalVarsMap.put("Manager", user.getFirstName());
				}
			
			 if (null != loginUser.getLastName() && !"".equalsIgnoreCase(loginUser.getLastName())) {
				 StringBuilder loginName = new StringBuilder();
				 globalVarsMap.put("LoginUser", loginName.append(loginUser.getFirstName()).append(loginUser.getLastName()).toString());
				 } else {
					 globalVarsMap.put("LoginUser", loginUser.getFirstName());
				}
			
			 /*if (null != orgLocations.getOrg()) {
				 globalVarsMap.put("LoginOrg", orgLocations.getOrg().getOrgName());
				 } else {
					 globalVarsMap.put("LoginOrg", "--");
				}*/
			 
			
			 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
			
			 //getting the current EmailScenario by EmailScenario unique name and entity type
			 //using same migration which has been already used in updatedRequirementMailToManager method
			 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.EMPLOYEE_REASSIGN,Constants.ENTITY_TYPE_EMAIL);
			 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.EMPLOYEE_REASSIGN,Constants.ENTITY_TYPE_NOTIFICATION);

			 
			 //covert subject,body and signature
			 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
			 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
			 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
			 String url = "";
			 
			 String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getBody(),globalVarsMap);
			 notificationScenario.setBody(notificationbody);

			 //construct HTML template using required data
			 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLocations.getOrg());
			 emailMessage.setSubject(subject);
			 emailMessage.setMessageBody(emailTemplate);
			 
			 User toUser = pmh.getProjectMemberId().getEmployee().getUser();
			 
			 //calling the sendEmail method of emailScenerioUtil
			 emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(),iAdressArray,new ArrayList<EmailAttachment>());
			 String actionLink="";
			emailScenerioUtil.sendNotification(actionLink, loginUser,toUser, pmh.getProjectMemberId().getEmployee().getOrgLocations(), notificationScenario);

		}

		/**
		 * 
		 * @param userID
		 * @return
		 * @throws Exception
		 */
		@PreAuthorize("hasAnyRole('ROLE_VIEW_DELETE_USER','ROLE_CREATE_USER')")
		@RequestMapping(value = "/reActiveUser", method = RequestMethod.POST)
        public String reActiveUser(@RequestParam(value = "userId", required = false) String userID, HttpServletRequest request) throws Exception {
				OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(Long.parseLong(userID), orgLocations.getId());
				User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				if(userRole != null){
					userRole.setUserState(userRole.getPreviousUserState());
					userRole.setPreviousUserState(userRole.getUserState());
					userRole.setUpdatedBy(user.getUserCode());
					userRole.setUpdatedDate(commonUtil.currentDate());
					userRoleService.addUserRole(userRole);
					
					//to assign primary role to another user
					UserRole existinguserRole = userRoleService.getLoginUserPrimaryRole(Long.parseLong(userID));
					if(existinguserRole == null){
						userRole.setIsPrimary(1);
						userRoleService.addUserRole(userRole);
					}

					/*
    				 * Saving USER DB data as ElasticSearch Documents
    				 */
        			  Employees employee = employeesService.getEmployeeByUserIdAndOrgLocationsId(userRole.getUser().getId(), orgLocations.getId());
        			  if(null != employee){
        				  userService.saveOrUpdateUserElkDocument(userRole,employee,user.getUserCode());
        			  }
				}
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
				@RequestParam(value = "role", required = false) Long roleId, HttpServletRequest request) throws Exception {
			String resultJson = null;
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			List<User> userList = userService.getManagersByUserRolesAndOrgLocationId(roleId, orgLocations.getId(),Long.valueOf(Constants.TAB_USER));
			for (User user : userList) {
				UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocations.getId());
				if(userRole != null){
					user.setUserType(userRole.getUserType());
				}
			}
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
