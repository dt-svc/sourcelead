package com.bis.sourcelead.web;

import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.constants.Constants;
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
import com.bis.sourcelead.entity.TimeSheetSubmissionCount;
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
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;



@RestController
public class ProjectsDashboardController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectsDashboardController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	 @Autowired
	 private ProjectsService projectsService; 
	 
	 @Autowired
	 private OrgService orgService;
	 
	 @Autowired
	 private ProjectTasksService tasksService;
	 
	 @Autowired
	 public UserService userService;
	
	 @Autowired
	 public EmployeesService employeesService;
	
	 @Autowired
	 public ProjectMembersService projectMembersService;
	
	
	 @Autowired
	 public ProjectApproverHierarchyService projectApproverHierarchyService;
	
	 @Autowired
	 private OrgLocationService orgLocationService;
	 
	 @Autowired
	 private OrgRelationsService orgRelationsService;
	 
	 @Autowired
	 public RolesService rolesService;
	 
	 @Autowired
	 public UserRoleService userRoleService;
	 
	 @Autowired
	 CommonUtil commonUtil;
	 
	 @Autowired
	 EmailScenarioService emailScenarioService;
	 
	 @Autowired
	 TimesheetSubmissionsService timesheetSubmissionsService;
	 
	 
	
	 @Autowired
	 EmailScenerioUtil emailScenerioUtil;
	 
	 @Autowired
	 private SourceleadPasswordEncoder sourceleadPasswordEncoder;
	 
	 /**
	  * This method for get project by id
	  * @param id
	  * @return
	  * @throws Exception
	  */
	 
	
	@RequestMapping(value = "/projectDashboard")
	public ModelAndView projectDashboard(HttpServletRequest request) throws Exception {

		User user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
		ModelAndView model = new ModelAndView();
		model.setViewName("project/projectDashboard");
		String loginOrgLocation = (String) SourceLeadSessionManager.getSessionAttribute(request, "locationCode");
	    OrgLocations loginOrgLocations = orgLocationService.getOrgLocationsByLocationCode(loginOrgLocation);
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
        if(sessionData == null)
        {
            sessionData = new SessionMaintainanceData();
        }
        sessionData.setNav(com.bis.sourcelead.util.Constants.Projects);
        request.getSession().setAttribute("sessionData",sessionData);
        
        Collection<OrgRelations> orgClientList =orgRelationsService.getAllClientsByOrgLocationId(loginOrgLocations.getId()); 
        for(OrgRelations orgRelations:orgClientList){
        	orgRelations.setClientName(orgRelations.getClientLocationId().getOrg().getOrgName());
        }
        model.addObject("add_client", orgClientList);
        model.addObject(orgClientList);
        
        List<Long> projectIds = projectApproverHierarchyService.getProjectIdsByLoginUserId(user.getId());
        
        Collection<Projects> projectsList = null;
        try{
        	projectsList = projectsService.getAllLoginUserProjectsList(projectIds, loginOrgLocations.getId());
        }catch(Exception e){
        	e.printStackTrace();
        }
        
        for(Projects projects: projectsList){
            if(projects.getOrgRelationId()!=null) {
                projects.setClient(projects.getOrgRelationId().getClientLocationId().getOrg().getOrgName());
                
            }
        }
		model.addObject(projectsList);
		model.addObject("sourceleadUrl", sourceleadUrl);

		return model;
		
    }
	
	
	// Sammeta David: this controller method is for getting ProjectsCount
			// Counts based on Status.
			@RequestMapping(value = "/getProjectsByStatusAndClient")
			public String getProjectsByStatusAndClient(@RequestParam(value="values", required=false) String values,HttpServletRequest request,@RequestParam(value="clients", required=false) String clients) throws Exception {

				String resultJson = "";
				User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			    Collection<Projects> projectsList = projectsService.getProjectsBasedOnClientsAndStatusSelectionInFilters(user.getId(), clients, values);
				 resultJson = toJSON(projectsList);
				 return resultJson;
			}
		/**


	
	/**
	 * get project details by Id
	 * @param id
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value = "/getProjectById", method = RequestMethod.POST)
	 public @ResponseBody String getProjectById(@RequestParam(value="ProjectId", required=false) Long projectId,HttpServletRequest request) throws Exception{ 
		 Projects projects = projectsService.getProjectById(projectId);
		   String loginOrgLocation = (String) SourceLeadSessionManager.getSessionAttribute(request, "locationCode");
		   OrgLocations loginOrgLocations = orgLocationService.getOrgLocationsByLocationCode(loginOrgLocation);
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			
			if(projects.getCreatedDate() != null){
				projects.setCreatedDateStr(Constants.DF_DMY.format(projects.getCreatedDate()));	
			}
		    projects.setStartDateStr(Constants.DF_DMY.format(projects.getStartDate()));
		    if(projects.getEndDate() != null){
		    	 projects.setEndDateStr(Constants.DF_DMY.format(projects.getEndDate()));
		    }
		    projects.setUpdatedDateStr(Constants.DF_DMY.format(projects.getUpdatedDate()));
		    projects.setCreatedBy(user.getFirstName()+" "+user.getLastName());
		    Collection<ProjectApproverHierarchy> projectApproverHierarchies = projectApproverHierarchyService.getApproverUsersByProjectId(projectId);
		    String[] approvers = new String[projectApproverHierarchies.size()];
		    int i = 0;
		    for(ProjectApproverHierarchy php : projectApproverHierarchies){
		    	approvers[i] = php.getApproverUserId().getFirstName()+"&"+php.getApproverUserId().getId();
		    	i++;
		    }
		    projects.setProjectManagerName(approvers);
		    
		    Collection<OrgRelations> orgClientList =orgRelationsService.getAllClientsByOrgLocationId(loginOrgLocations.getId()); 
		    String[] clientList = new String[orgClientList.size()];
		    int j = 0;
			for(OrgRelations ocl :orgClientList){
				if(projects.getOrgRelationId() !=null){
					if(projects.getOrgRelationId().getId()==ocl.getId()){
						projects.setRelationId(ocl.getId());
					}
				}
				clientList[j] = ocl.getClientLocationId().getOrg().getOrgName()+"&"+ocl.getId();
				j++;
			 }
			projects.setClientName(clientList);
			
			
			Integer projectMembersCount  = projectMembersService.getAllProjectMembersCountByProjectId(projectId);
			projects.setProjectMembersCount(projectMembersCount.toString());
			
		    ObjectMapper mapper = new ObjectMapper();
            String jsonInString = mapper.writeValueAsString(projects);
			return jsonInString;
	  }
	 
	 @RequestMapping(value = "/updateProjectById", method = RequestMethod.POST)
	 public String updateProjectById(@RequestParam(value="ProjectId", required=false) Long projectId,@RequestParam(value="fieldName", required=false) String fieldName,@RequestParam(value="fieldValue", required=false) String fieldValue,HttpServletRequest request) throws Exception{ 
		
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user"); 
		 Projects projects = projectsService.getProjectById(projectId);
		    if(fieldName.equalsIgnoreCase("projectName")){
		    	projects.setProjectName(fieldValue);
		    }
		    else if (fieldName.equalsIgnoreCase("description")) {
		    	projects.setDescription(fieldValue);
			}else if(fieldName.equalsIgnoreCase("endDateStr"))  {
		    	String pattern = "dd-mm-yy";
		        SimpleDateFormat format = new SimpleDateFormat(pattern);
		          if(fieldValue != null && fieldValue != ""){
		        	  Date endtDate = format.parse(fieldValue);
						projects.setEndDate(endtDate);
		          }
			}else if (fieldName.equalsIgnoreCase("projectStatusId")) {
		    	projects.setProjectStatus(Project_Status.getNameByValue(fieldValue));
			}else if (fieldName.equalsIgnoreCase("clientId")) {
				OrgRelations orgRelations = new OrgRelations();
				orgRelations.setId(Long.valueOf(fieldValue));
		    	projects.setOrgRelationId(orgRelations);
			}
			    projects.setUpdatedBy(user.getUserCode());
			    projects.setUpdatedDate(new Date());
			    projectsService.updateProject(projects);
			    return null;
		  }
	 
	 
	 
	 @RequestMapping(value = "/updateProjectTaskById", method = RequestMethod.POST)
	 public String updateProjectTaskById(@RequestParam(value="taskId", required=false) String taskId,@RequestParam(value="fieldName", required=false) String fieldName,@RequestParam(value="fieldValue", required=false) String fieldValue) throws Exception{ 
		    
		    ProjectTasks projectTasks = tasksService.getTasksId(taskId);
		    if(fieldName.equalsIgnoreCase("taskName")){  
		    	projectTasks.setTaskName(fieldValue);
		    }
		    else if (fieldName.equalsIgnoreCase("description")) {
		    	projectTasks.setDescription(fieldValue);
			}     
		    tasksService.updateTasks(projectTasks);
		    return null;
	  }
	 
	 
	 @RequestMapping(value = "/saveProjectTasks" , method = RequestMethod.POST)
	 public @ResponseBody  String saveProjectTasks(@RequestParam(value="json", required=false) String json,
			 @RequestParam(value="projectId", required=false) Long projectId) throws Exception {
		 	
		    json = "{"+json+"}";	
		    JSONObject jsonObj = new JSONObject(json);
		 	
		 	String resultJson = null;
		 	String maxAdminTimeTypeRowNum = jsonObj.getString("maxTsRowNum");
			int maxCount = Integer.parseInt(maxAdminTimeTypeRowNum);
			
			for (int c = 1; c < maxCount; c++) {
				ProjectTasks  tasks  = new ProjectTasks();
				if(jsonObj.has(c+"taskName")){
					tasks.setTaskName(jsonObj.getString(c+"taskName"));
					if(jsonObj.has(c+"taskDescription")){
					tasks.setDescription(jsonObj.getString(c+"taskDescription"));
					}
				tasks.setCreatedDate(new Date());
				tasks.setUpdatedDate(new Date());
				Projects projects = projectsService.getProjectById(projectId);
				tasks.setProjectId(projects);	
				
				ProjectTasks savedtasks = tasksService.addTasks(tasks);
				}
			}
			Collection<ProjectTasks> taskList=tasksService.getAllTasksByProjectId(projectId);
		 	
			 resultJson = toJSON(taskList);
			 return resultJson;
		 	
	 }
	 
	 
	 @RequestMapping(value = "/getProjectTasks" , method = RequestMethod.POST)
	 public @ResponseBody  String getProjectTasks(
			 @RequestParam(value="projectId", required=false) Long projectId) throws Exception {
		 	
		 	 String resultJson = null;
			 Collection<ProjectTasks> taskList=tasksService.getAllTasksByProjectId(projectId);
			 resultJson = toJSON(taskList);
			 return resultJson;
		 	
	 }
	  
	 @RequestMapping(value = "/getProjectEmployeeListById", method = RequestMethod.POST)
	 public @ResponseBody String getProjectEmployeeListById(@RequestParam(value="projectId", required=false) Long projectId) throws Exception{ 
		 
		 Collection<ProjectMembers> projectMembersList = projectMembersService.getAllProjectMembersByProjectId(projectId);
		 ArrayList<ProjectMembers> projectMembersArrList = new ArrayList<ProjectMembers>();
		 for(ProjectMembers pm : projectMembersList){
			 pm.setEmpName(pm.getEmployee().getUser().getFirstName()+" "+pm.getEmployee().getUser().getLastName());
			 
			 if(pm.getProjectAssignedDate()!=null){
				 pm.setProjectAssignedDateStr(Constants.DF_DMY.format(pm.getProjectAssignedDate()));
             }
        	 if(pm.getProjectEndDate()!=null){
        		pm.setProjectEndDateStr(Constants.DF_DMY.format(pm.getProjectEndDate()));
        	 }
        	 if(pm.getEmployee()!=null){
        		 
        	 TimeSheetSubmissionCount timeSheetSubmissionCount = timesheetSubmissionsService.getTimeSheetSubmissionsHoursCountByUserId(pm.getEmployee().getUser().getId());
        	 pm.setDraftHrs(timeSheetSubmissionCount.getDraftTimesheetsSubmissionsHoursCount().toString());
        	 pm.setRejectedHrs(timeSheetSubmissionCount.getRejectedTimesheetsSubmissionsHoursCount());
        	 pm.setApprovedHrs(timeSheetSubmissionCount.getApprovalTimesheetsSubmissionsHoursCount());
        	 pm.setSubmittedHrs(timeSheetSubmissionCount.getSubmittedTimesheetsSubmissionsHoursCount());
        	 
        	 }	
			 projectMembersArrList.add(pm);
		 }
		 ObjectMapper mapper = new ObjectMapper();
		 String jsonInString = mapper.writeValueAsString(projectMembersArrList);
		 
		 return jsonInString;
	  }
	 
	 @RequestMapping(value = "/addEmployeeForProjectDashboard",  method = RequestMethod.POST)
		public @ResponseBody  String addEmployeeForProjectDashboard(@RequestParam(value="json", required=false) String json,HttpServletRequest request) throws Exception {
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
			employee.setStatus("ACTIVE");
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
	 
	 @RequestMapping(value = "/getTeamMembersList",  method = RequestMethod.POST)
		public @ResponseBody  String getTeamMembersList(@RequestParam(value="ProjectId", required=false) Long projectId, HttpServletRequest request) throws Exception {
		 	String resultJson = null;
		 	String loginOrgLocation = (String) SourceLeadSessionManager.getSessionAttribute(request, "locationCode");
			OrgLocations loginOrgLocations = orgLocationService.getOrgLocationsByLocationCode(loginOrgLocation);
			Collection<Employees> employeeList = employeesService.getEmployeesExcludeTeamMembers(projectId, loginOrgLocations.getId());
			resultJson = toJSON(employeeList);
			return resultJson;
		}
	 
	 @RequestMapping(value = "/storeAssignEmployee",  method = RequestMethod.POST)
		 public @ResponseBody  String storeAssignEmployee(
				@RequestParam(value="ProjectId", required=false) Long projectId,
				@RequestParam(value="employeeId", required=false) Long employeeId,
				@RequestParam(value="startDate", required=false) String startDate,
				@RequestParam(value="endDate", required=false) String endDate,
				@RequestParam(value="mailtoMembers", required=false) String mailtoMembers, HttpServletRequest request) throws Exception {
		 	
			 	ProjectMembers pm = new ProjectMembers();
			 	OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			 	User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			 	
			  if(projectId != null){
				  
					Projects projects = projectsService.getProjectById(projectId);
					Employees emp = employeesService.getEmployee(employeeId.toString());
				 	pm.setEmployee(emp);
					pm.setProjects(projects);
					String pattern = "yyyy-MM-dd";
		            SimpleDateFormat format = new SimpleDateFormat(pattern);
		            if(StringUtils.isNotEmpty(startDate)){
		            	Date currentStartDate = format.parse(startDate);
		            	  pm.setProjectAssignedDate(currentStartDate);
		            }
		            else{
		            	 pm.setProjectAssignedDate(projects.getStartDate());
		            }
		            if(StringUtils.isNotEmpty(endDate)){
		            	Date currentEndDate = format.parse(endDate);
		            	pm.setProjectEndDate(currentEndDate);
		            }
		            
		            pm.setStatus(Constants.MEMBER_ACTIVE);
					pm.setCreatedDate(new Date());
					pm.setUpdatedDate(new Date());
					projectMembersService.saveProjectMember(pm);
					commonUtil.storeTimesheetSubmissionRecords(emp.getUser());
					
					/**
				     * This method for send Email to team member while assign employee from project dashboard
				     */
					ProjectApproverHierarchy projectApproverHierarchy = projectApproverHierarchyService.getLevelOneProjectApproverByProjectId(projects.getId());
					
					if(!isNullOrEmpty(mailtoMembers) && ("YES".equals(mailtoMembers))){
						sendEmailToTeamMembers(projects, emp, user, projectApproverHierarchy, loginOrgLocation);
					}
			  }
				return null;
		}
	 
	 /**
	  * This method for send Email to team member
	  * @return
	  * @throws Exception
	  * @author  venkatKesav
	  * @since   2016-07-22
	  */
	  private void sendEmailToTeamMembers(Projects projects, Employees emp, User loginUser, ProjectApproverHierarchy projectApproverHierarchy, OrgLocations loginOrgLocation) throws Exception {
		  	 User projectManager = userService.getUserById(projectApproverHierarchy.getApproverUserId().getId());
		  	
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
		}
	  
	  /**
	  * This method for send Email to user creation
	  * @return
	  * @throws Exception
	  * @author  venkatKesav
	  * @since   2016-07-25
	  */
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
