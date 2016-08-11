package com.bis.sourcelead.rest;

import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailRecipient;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Project_Status;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.Constants;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;


@RestController
@PropertySource("${propertyLocation:app.properties}")
public class ProjectsRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectsRestController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	 @Autowired
	 EmailScenarioService emailScenarioService;
	
	 @Autowired
	 EmailScenerioUtil emailScenerioUtil;
	
	 @Autowired
	 private ProjectsService projectsService; 
	 
	 @Autowired
	 private OrgService orgService;
	 
	 @Autowired
	 private OrgRelationsService orgRelationsService;
	 
	 @Autowired
	 private OrgLocationService orgLocationService;
	 
	 @Autowired
	 private ProjectTasksService tasksService;
	 
	 @Autowired
	 public UserService userService;
	
	 @Autowired
	 public EmployeesService employeesService;
	 
	 @Autowired
	 public UserRoleService userRoleService;

	 @Autowired
	 public ProjectApproverHierarchyService projectApproverHierarchyService;
	 
	 @Autowired
	 public ProjectMembersService projectMembersService;
	
	 @Autowired
	 public RolesService rolesService;
	 
	 @Autowired
	 CommonUtil commonUtil;
	 
	 @Autowired
	 private SourceleadPasswordEncoder sourceleadPasswordEncoder;
	 
	 
	 /**
	  * This method for save the  project by json
	  * @param json
	  * @param request
	  * @return value is Project pojo
	  * @throws Exception
	  */
	 @RequestMapping(value = "/saveProject" , method = RequestMethod.POST)
	 public @ResponseBody  String saveProject(@RequestParam(value="json", required=false) String json,HttpServletRequest request) throws Exception {
		   
		    json = "{"+json+"}";
		    JSONObject jsonObj = new JSONObject(json);
		 	
		 	String resultJson = null;
		 	OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 	User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 	String loginOrgCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "orgCode");
		 	String pattern = "yyyy-MM-dd";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
		 	
	    	Projects projects = new Projects();
	    	projects.setProjectSequeneNumber(jsonObj.getString("ProjectSequeneNumber"));
	    	projects.setProjectName(jsonObj.getString("ProjectName"));
	    	projects.setDescription(jsonObj.getString("ProjectDescription"));
	    	if(jsonObj.getString("StartDate") != null && jsonObj.getString("StartDate") != "" && jsonObj.getString("StartDate") != "undefined"){
	    		Date startDate = format.parse(jsonObj.getString("StartDate"));
		    	projects.setStartDate(startDate);
	    	}
	    	if(!jsonObj.getString("EndDate").isEmpty()){
	    		Date endDate = format.parse(jsonObj.getString("EndDate"));
		    	projects.setEndDate(endDate);
	    	}
	    	projects.setProjectStatus(Project_Status.getNameByValue((jsonObj.getString("Satatus"))));
	    	projects.setCreatedDate(new Date());
	    	projects.setUpdatedDate(new Date());
	    	if(user.getUserCode() != null){
	    		projects.setCreatedBy(user.getUserCode());	
	    	}
	    	projects.setOrgLocationsId(loginOrgLocation);
	    	
	    	// Saving of Client by bringing OrgRelation id based on Client OrgId
		    String clientOrgCode = jsonObj.getString("selectClient");	
		    if(!clientOrgCode.isEmpty() && clientOrgCode!=null){
		    	  OrgLocations clientOrgLocations = orgLocationService.getOrgLocationsByOrgCode(clientOrgCode);
		    	  if(clientOrgLocations!=null){
		    		  OrgRelations  orgRelations = orgRelationsService.getOrgRelationIdByOrgLocationIdAndClientLocationId(loginOrgLocation.getId(), clientOrgLocations.getId());
			    		  if(orgRelations!=null){
			    			  projects.setOrgRelationId(orgRelations);
			    		  }
		    	  }
		    	  
		    }
		    
		    if((jsonObj.has("isMailToTeamMembers") && !isNullOrEmpty(jsonObj.getString("isMailToTeamMembers"))) && ("Y".equals(jsonObj.getString("isMailToTeamMembers")))){
				projects.setIsMailToTeamMembers(Status.Y);
			}else{
				projects.setIsMailToTeamMembers(Status.N);
			}
		    
		  if((jsonObj.has("isMailToProjectManager") && !isNullOrEmpty(jsonObj.getString("isMailToProjectManager"))) && ("Y".equals(jsonObj.getString("isMailToProjectManager")))){
			  projects.setIsMailToProjectManager(Status.Y);
			}else{
				projects.setIsMailToProjectManager(Status.N);
			}
	    	
	    	Projects updatedProjects = projectsService.saveProject(projects);
	    	
	    	Long[] ids = new Long[1000];
	    	
	    	ProjectApproverHierarchy pah1 = new ProjectApproverHierarchy();
	    	pah1.setProjectId(updatedProjects);
	    	User approverUserId1 = new User();
	    	
	        if(jsonObj.has("level1Manager")){
			    	if(jsonObj.getString("level1Manager") != null && !jsonObj.getString("level1Manager").isEmpty()){
			    		approverUserId1.setId(Long.parseLong(jsonObj.getString("level1Manager")));
				    	pah1.setApproverUserId(approverUserId1);
				    	pah1.setLevel(1);
				    	ProjectApproverHierarchy saveProjectApproverHierarchy = projectApproverHierarchyService.saveProjectApproverHierarchy(pah1);
				    	ids[0] = saveProjectApproverHierarchy.getApproverUserId().getId();
				    	
			    	}
		    	}
	    	//send mail to assigned Project Manager
	    	String teamMembersList = "";
			
	    	User approverUserId2 = new User();
			 if(jsonObj.has("level2Manager")){ 	
				if(!jsonObj.getString("level2Manager").isEmpty()){
					ProjectApproverHierarchy pah2 = new ProjectApproverHierarchy();
					pah2.setProjectId(updatedProjects);
					approverUserId2.setId(Long.parseLong(jsonObj.getString("level2Manager")));
					pah2.setApproverUserId(approverUserId2);
					pah2.setLevel(2);
					ProjectApproverHierarchy projectApproverHierarchy = projectApproverHierarchyService.saveProjectApproverHierarchy(pah2);
					ids[1] = projectApproverHierarchy.getApproverUserId().getId();
				}
			 }
		 
		//admin as approver hierarchy ..
		Collection<User> uLsit = userService.getUsersByUserRoles(loginOrgLocation.getId());
		CopyOnWriteArrayList<User> userLsit = new CopyOnWriteArrayList<User>(uLsit);
		if(userLsit != null && !userLsit.isEmpty()){
		for(User userRole : userLsit){
			  if(ids[0] != null &&  ids[0].equals(userRole.getId())){
				  userLsit.remove(userRole);
			  }
			  else if (ids[1] != null && ids[1].equals(userRole.getId())) {
				  userLsit.remove(userRole);
			  }else{
				    ProjectApproverHierarchy pah3 = new ProjectApproverHierarchy();
				    pah3.setProjectId(updatedProjects);
					pah3.setApproverUserId(userRole);
					pah3.setLevel(2);
				    ProjectApproverHierarchy projectApproverHierarchy = projectApproverHierarchyService.saveProjectApproverHierarchy(pah3);
			  } 
			  
		}
		}
	    	
		
	    	String maxAdminTimeTypeRowNum = jsonObj.getString("maxTsRowNum");
			int maxCount = Integer.parseInt(maxAdminTimeTypeRowNum);

			for (int c = 1; c < maxCount; c++) {
				ProjectTasks  tasks  = new ProjectTasks();
				if(jsonObj.has(c+"taskName")){
					if(jsonObj.has(c+"taskDescription")){
						tasks.setDescription(jsonObj.getString(c+"taskDescription"));
					}
						tasks.setTaskName(jsonObj.getString(c+"taskName"));
						tasks.setCreatedDate(new Date());
						tasks.setUpdatedDate(new Date());
						tasks.setProjectId(projects);
				ProjectTasks savedtasks = tasksService.addTasks(tasks);
			  }
			}
			
			if (jsonObj.has("employeeMaxCount")){
				String employeeMaxCount = jsonObj.getString("employeeMaxCount");
				if(!jsonObj.getString("employeeMaxCount").isEmpty()){
						int empMaxCount = Integer.parseInt(employeeMaxCount);
						for (int c = 1; c < empMaxCount; c++) {
							
							ProjectMembers pm = new ProjectMembers();
							if(StringUtils.isNotEmpty(jsonObj.getString("employeeId"+c))){
								Employees emp = employeesService.getEmployee(jsonObj.getString("employeeId"+c));
							    if(emp != null){
							    	pm.setEmployee(emp);
						    	    
							    	
						            if(StringUtils.isNotEmpty(jsonObj.getString("employeestartDate"+c))){
						            	Date currentStartDate = format.parse(jsonObj.getString("employeestartDate"+c));
						            	  pm.setProjectAssignedDate(currentStartDate);
						            }else{
						            	 pm.setProjectAssignedDate(projects.getStartDate());
						            }
						            
						            if(StringUtils.isNotEmpty(jsonObj.getString("employeeendDate"+c))){
						            	Date currentEndDate = format.parse(jsonObj.getString("employeeendDate"+c));
						            	pm.setProjectEndDate(currentEndDate);
						            }
						            else{
							    		pm.setProjectEndDate(projects.getEndDate());
							    	}
									pm.setProjects(projects);
									pm.setStatus(Constants.MEMBER_ACTIVE);
									pm.setCreatedDate(new Date());
									pm.setUpdatedDate(new Date());
									projectMembersService.saveProjectMember(pm);
									
									//send mail to assigned team members
									if((jsonObj.has("isMailToTeamMembers") && !isNullOrEmpty(jsonObj.getString("isMailToTeamMembers"))) && ("Y".equals(jsonObj.getString("isMailToTeamMembers")))){
										sendEmailToTeamMembers(projects, emp, user, approverUserId1, loginOrgLocation, loginOrgCode);
										
									}
									
									commonUtil.storeTimesheetSubmissionRecords(emp.getUser());
									
									/**
								    * This teamMembersList string to store all team members for mail to project manager.
								    */
									String teamMember = "";
									teamMember = emp.getUser().getFirstName()+" "+emp.getUser().getLastName();
									teamMembersList = teamMembersList+teamMember+"<br/>";
							    }
							}
					    }
				 }
		    }
			
			//send mail to assigned Project Manager
			if((jsonObj.has("isMailToProjectManager") && !isNullOrEmpty(jsonObj.getString("isMailToProjectManager"))) && ("Y".equals(jsonObj.getString("isMailToProjectManager")))){
				if(approverUserId1 != null && user.getId() != approverUserId1.getId()){
					sendEmailToProjectManager(projects, user, approverUserId1, loginOrgLocation, teamMembersList, loginOrgCode);
				}
				if(!jsonObj.getString("level2Manager").isEmpty() && approverUserId2 != null && user.getId() != approverUserId2.getId()){
					sendEmailToProjectManager(projects, user, approverUserId2, loginOrgLocation, teamMembersList, loginOrgCode);
				}
			}
			
			Collection<ProjectTasks> tasksList = tasksService.getAllTasks();
			
			 resultJson = toJSON(tasksList);
			 SourceLeadSessionManager.setSessionAttribute(request, "projectCreateMsg", "Successfully you have created a project");
			 return "projectDashboard";
			 
	   }
	 
	 
    @RequestMapping(value = "/validateProjectId",  method = RequestMethod.POST)
    public @ResponseBody String validateJobSequenceNumber(@RequestParam(value="ProjectId", required=false) Long projectId) throws Exception {
	    	String result = "";
			String resultJson = "";
			Integer projectIdCount = projectsService.projectIdValidation(projectId);
			if(projectIdCount>0){
				result = "duplicate";
			}
			else{
				result = "unique";
			}
			resultJson = toJSON(result);
			return resultJson;
	    }
		


	@RequestMapping(value = "/sourceLeadTasks")
			public String sourceLeadTasks() throws Exception {
	
					ModelAndView model = new ModelAndView();
					String result = "";
					String resultJson = "";
					
					Collection<ProjectTasks>  sourceleadProjectTasks = tasksService.getAllSourceleadTasks();
					model.addObject("Tasks_List", sourceleadProjectTasks);
					
					resultJson = toJSON(sourceleadProjectTasks);
					return resultJson;
		}
	 
	 @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET,headers="Accept=application/json")
	 public Projects getProjectById(@PathVariable Long id) throws Exception { 
		    Projects projects = projectsService.getProjectById(id);
		    return projects;
	  }
	 
	 
	 /**
	  * This method for delete project by id
	  * @param id
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET,headers="Accept=application/json")
	 public Boolean delete(@PathVariable Long id) throws Exception { 
		Boolean isDeleted = projectsService.deleteProjectById(id);
		return isDeleted;
	  }
	  
	@RequestMapping(value = "/addNewEmployee",  method = RequestMethod.POST)
	public @ResponseBody  String addNewEmployee(@RequestParam(value="json", required=false) String json,HttpServletRequest request) throws Exception {
		    
		    json = "{"+json+"}";	
		    JSONObject jsonObj = new JSONObject(json);
			
		 	OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 	User users = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			Employees employee  = new Employees();
			
			// User saving
			User user= new User();
			user.setFirstName(jsonObj.getString("employeeFirstName"));
			user.setLastName(jsonObj.getString("employeeLastName"));
			user.setEmail(jsonObj.getString("EmailId"));
			user.setUserType("Employee");
			user.setCreatedDate(new Date());
			user.setCreatedBy(users.getUserCode());
			user.setUserState(Status.NOT_LOGGEDIN);
			user.setUserName(jsonObj.getString("EmailId"));
			String password = SourceLeadRandomCodeHelper.generateRandomPassword();
			user.setPassword(sourceleadPasswordEncoder.encode(password));
			
			if((jsonObj.has("isMailSent") && !isNullOrEmpty(jsonObj.getString("isMailSent"))) && ("Y".equals(jsonObj.getString("isMailSent")))){
				user.setIsMailSent(Status.Y);
			}else{
				user.setIsMailSent(Status.N);
			}
			user.setUserCode(SourceLeadRandomCodeHelper.generateRandomUserCode());
			User  usr=	userService.addUser(user);
			
			//Employee Saving
			employee.setUser(user);
			if(!jsonObj.getString("invoiceFrequency").isEmpty()){
				employee.setInvoiceFrequency(jsonObj.getString("invoiceFrequency"));
			}
			employee.setOrgLocations(loginOrgLocation);
			employee.setCreatedBy(users.getUserCode());
			employee.setCreatedDate(new Date());
			Employees  emp=	employeesService.addEmployee(employee);
			
			
			// UserRoles saving
			String roleName = "ROLE_USER";
			Roles roles = rolesService.getRolesByRoleName(roleName);
			UserRole userRole = new UserRole();
			userRole.setRoles(roles);
			userRole.setUser(usr);
			userRole.setOrgLocations(loginOrgLocation);
			userRole.setIsPrimary(1);
			userRole.setCreatedBy(users.getUserCode());
			userRole.setCreatedDate(new Date());
			UserRole userRolesSaving = userRoleService.addUserRole(userRole);
			
			
			//send mail to user
			if((jsonObj.has("isMailSent") && !isNullOrEmpty(jsonObj.getString("isMailSent"))) && ("Y".equals(jsonObj.getString("isMailSent")))){
			sendEmail(user,users,password);
			}
			
			return null;
		}
	  
	  /**
		  * This method for getEmployeesList by by excluding  Level1 and Level2 Managers 
		  * @param id
		  * @return
		  * @throws Exception
		  */
	  @RequestMapping(value = "/getEmployeesListByExcludingManagers",  method = RequestMethod.POST)
		public @ResponseBody  String getEmployeesListByExcludingManagers(HttpServletRequest request,@RequestParam(value="level1Manager", required=false) Long level1Manager,@RequestParam(value="level2Manager", required=false) Long level2Manager) throws Exception {
		 	String resultJson = null;
		 	
		 	
		 	String loginOrgLocation = (String) SourceLeadSessionManager.getSessionAttribute(request, "locationCode");
		    OrgLocations loginOrgLocations = orgLocationService.getOrgLocationsByLocationCode(loginOrgLocation);
		    
		    Collection<Employees> employeList=employeesService.getAllEmployeesByOrgLocationId(loginOrgLocations.getId(),level1Manager,level2Manager);
			resultJson = toJSON(employeList);
			return resultJson;
	  }
	 
 
	 @RequestMapping(value = "/notification", method = RequestMethod.GET,headers="Accept=application/json")
	 public void getNotification() throws Exception{
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.TIME_SHEET_SUBMISSION,Constants.ENTITY_TYPE_NOTIFICATION);
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		    globalVarsMap.put("firstName", "Vinesh");
		    globalVarsMap.put("lastName","Kammari");
			String body = emailScenerioUtil.buildBody(emailScenario.getBody(),globalVarsMap);
			emailScenario.setBody(body);
			String url = null;
			String actionLink = "";
			User user = new User();
			OrgLocations loginOrgLocation = new OrgLocations();
			emailScenerioUtil.sendNotification(actionLink, user, user, loginOrgLocation, emailScenario);
	}
	 
	 //to  validate user email
	 @RequestMapping(value = "/getEmailVerification" , method = RequestMethod.POST)
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

	//to validate Project SequeneNumber
		 @RequestMapping(value = "/validateProjectSequeneNumberById" , method = RequestMethod.POST)
		 public @ResponseBody  Boolean validateProjectSequeneNumber(@RequestParam(value="ProjectSequeneNumber", required=false) String ProjectSequeneNumber, HttpServletRequest request) throws Exception {
			    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			 	Boolean flag = projectsService.validateProjectSequeneNumber(ProjectSequeneNumber,orgLocations.getId());
			 	
				return flag;
		   }

	//Validate for Cilent Website
	 @RequestMapping(value = "/ws/validateClientWebsite", method = RequestMethod.GET)
	   public @ResponseBody String validateOrgWebsite(
	   		@RequestParam(value="website", required=false) String website,
	   		@RequestParam(value="sourceleadBLockedwebsites", required=false) String sourceleadBLockedwebsites) {
		
			String status = "success";
            String orgInfo = orgService.websiteValidation(website,sourceleadBLockedwebsites);
            if(orgInfo !=null && !"".equalsIgnoreCase(orgInfo)){
            	 if (orgInfo.equalsIgnoreCase("Website Duplicated")) {
                     status = "Website Duplicated";
                 }else if(orgInfo.equalsIgnoreCase("Invalid Website")){
                     status = "Invalid Website";
                 }
            }
	    return status;
	   }
	 
	 
	  private void sendEmail(User user, User loginUser ,String password) throws Exception {
		  
		     EmailMessage emailMessage = new EmailMessage();
			 emailMessage.setFirstName(user.getFirstName());
			 emailMessage.setLastName(user.getLastName());
			 emailMessage.setToEmail(user.getEmail());
			 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
			 emailMessage.setFromName(loginUser.getFirstName()+" "+loginUser.getLastName());

				//setting the class filed values
			 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 globalVarsMap.put("firstName", emailMessage.getFirstName());
			 globalVarsMap.put("lastName",emailMessage.getLastName());
			 globalVarsMap.put("administratorFirstName", loginUser.getFirstName());
			 globalVarsMap.put("administratorLastName",loginUser.getLastName());
			 globalVarsMap.put("role",user.getUserType());
			 globalVarsMap.put("loginURL","<a href='"+sourceleadUrl+"/"+"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>");
			 globalVarsMap.put("userEmailId",user.getEmail());
			 globalVarsMap.put("password",password);
			 
			//setting the Recipient information
			 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
			 EmailRecipient emailRecipient = new EmailRecipient();
			 emailRecipient.setEmail(emailMessage.getToEmail());
			 emailRecipient.setName(emailMessage.getFirstName()+" "+emailMessage.getLastName());
			 emailRecipientList.add(emailRecipient);
			 
			//getting the current EmailScenario by EmailScenario unique name and entity type
			 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.INVITE_USER,Constants.ENTITY_TYPE_EMAIL);
			
			 //covert subject,body and signature
			 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
			 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
			 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
			 String url = null;
			 
			 //construct HTML template using required data
			 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
			 emailMessage.setSubject(subject);
			 emailMessage.setMessageBody(emailTemplate);
			 
			 //calling the sendEmail method of emailScenerioUtil
			 emailScenerioUtil.sendEmail(emailMessage,emailRecipientList,new ArrayList<EmailAttachment>());
			 
		}
	  
	  /**
	  * This method for send Email to team member
	  * @return
	  * @throws Exception
	  * @author  venkatKesav
	  * @since   2016-07-22
	  */
	  private void sendEmailToTeamMembers(Projects projects, Employees emp, User loginUser, User approverUserId1, OrgLocations loginOrgLocation, String loginOrgCode) throws Exception {
		  	 User projectManager = userService.getUserById(approverUserId1.getId());
		  	
		     EmailMessage emailMessage = new EmailMessage();
			 emailMessage.setFirstName(emp.getUser().getFirstName());
			 emailMessage.setLastName(emp.getUser().getLastName());
			 emailMessage.setToEmail(emp.getUser().getEmail());
			 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
			 emailMessage.setFromName(loginUser.getFirstName()+" "+loginUser.getLastName());

			 //setting the class filed values
			 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 globalVarsMap.put("loginUserFirstName",loginUser.getFirstName());
			 globalVarsMap.put("loginUserLastName",loginUser.getLastName());
			 globalVarsMap.put("managerFirstName",projectManager.getFirstName());
			 globalVarsMap.put("managerLastName",projectManager.getLastName());
			 globalVarsMap.put("employeeFirstName",emailMessage.getFirstName());
			 globalVarsMap.put("employeeLastName",emailMessage.getLastName());
			 globalVarsMap.put("projectName", projects.getProjectName());
			 globalVarsMap.put("orgName",loginOrgLocation.getOrg().getOrgName());
			 globalVarsMap.put("loginURL","<a href='"+sourceleadUrl+"/"+"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>");
			 
			 //setting the Recipient information
			 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
			 EmailRecipient emailRecipient = new EmailRecipient();
			 emailRecipient.setEmail(emailMessage.getToEmail());
			 emailRecipient.setName(emailMessage.getFirstName()+" "+emailMessage.getLastName());
			 emailRecipientList.add(emailRecipient);
			 
			 //getting the current EmailScenario by EmailScenario unique name and entity type
			 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_TEAM_MEMBERS,Constants.ENTITY_TYPE_EMAIL);
			
			 //covert subject,body and signature
			 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
			 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
			 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
			 String url = sourceleadUrl;;
			 
			 //construct HTML template using required data
			 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
			 emailMessage.setSubject(subject);
			 emailMessage.setMessageBody(emailTemplate);
			 
			 //calling the sendEmail method of emailScenerioUtil
			 emailScenerioUtil.sendEmail(emailMessage,emailRecipientList,new ArrayList<EmailAttachment>());
			 
			 
			//calling the sendNotification method of emailScenerioUtil
			EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_TEAM_MEMBERS,Constants.ENTITY_TYPE_NOTIFICATION);
			String notificationBody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
			notificationScenario.setBody(notificationBody);
			notificationScenario.setOrgCode(loginOrgCode);
			//String url = null;
			String actionLink = "";
			emailScenerioUtil.sendNotification(actionLink, loginUser, emp.getUser(), loginOrgLocation, notificationScenario);
		}
	  
	  /**
	  * This method for send Email to project manager
	  * @return
	  * @throws Exception
	  * @author  venkatKesav
	  * @since   2016-07-25
	  */
	  private void sendEmailToProjectManager(Projects projects, User loginUser, User approverUserId1, OrgLocations loginOrgLocation, String teamMembersList, String loginOrgCode) throws Exception {
		  	 User projectManager = userService.getUserById(approverUserId1.getId());
		  	 
		  	 if(isNullOrEmpty(teamMembersList) && ("undefined".equals(teamMembersList))){
		  		teamMembersList = "-";
		  	 }
		  	
		     EmailMessage emailMessage = new EmailMessage();
			 emailMessage.setFirstName(projectManager.getFirstName());
			 emailMessage.setLastName(projectManager.getLastName());
			 emailMessage.setToEmail(projectManager.getEmail());
			 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
			 emailMessage.setFromName(loginUser.getFirstName()+" "+loginUser.getLastName());

			 //setting the class filed values
			 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 globalVarsMap.put("loginUserFirstName",loginUser.getFirstName());
			 globalVarsMap.put("loginUserLastName",loginUser.getLastName());
			 globalVarsMap.put("managerFirstName",projectManager.getFirstName());
			 globalVarsMap.put("managerLastName",projectManager.getLastName());
			 globalVarsMap.put("teamMembers",teamMembersList);
			 globalVarsMap.put("projectName", projects.getProjectName());
			 globalVarsMap.put("orgName",loginOrgLocation.getOrg().getOrgName());
			 globalVarsMap.put("loginURL","<a href='"+sourceleadUrl+"/"+"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>");
			 
			 //setting the Recipient information
			 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
			 EmailRecipient emailRecipient = new EmailRecipient();
			 emailRecipient.setEmail(emailMessage.getToEmail());
			 emailRecipient.setName(emailMessage.getFirstName()+" "+emailMessage.getLastName());
			 emailRecipientList.add(emailRecipient);
			 
			 //getting the current EmailScenario by EmailScenario unique name and entity type
			 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_PROJECT_MANAGER,Constants.ENTITY_TYPE_EMAIL);
			
			 //covert subject,body and signature
			 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
			 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
			 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
			 String url = sourceleadUrl;;
			 
			 //construct HTML template using required data
			 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
			 emailMessage.setSubject(subject);
			 emailMessage.setMessageBody(emailTemplate);
			 
			 //calling the sendEmail method of emailScenerioUtil
			 emailScenerioUtil.sendEmail(emailMessage,emailRecipientList,new ArrayList<EmailAttachment>());
			 
			 
			//calling the sendNotification method of emailScenerioUtil
			EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_PROJECT_MANAGER,Constants.ENTITY_TYPE_NOTIFICATION);
			String notificationBody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
			notificationScenario.setBody(notificationBody);
			notificationScenario.setOrgCode(loginOrgCode);
			//String url = null;
			String actionLink = "";
			emailScenerioUtil.sendNotification(actionLink, loginUser, projectManager, loginOrgLocation, notificationScenario);
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
