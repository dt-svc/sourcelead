package com.bis.sourcelead.rest;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ProjectsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.elk.index.ClientVendorIndex;
import com.bis.sourcelead.elk.index.ProjectsIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectBudget;
import com.bis.sourcelead.entity.ProjectClients;
import com.bis.sourcelead.entity.ProjectDocuments;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.ClientVendorExport;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectAssignTasksService;
import com.bis.sourcelead.service.ProjectDocumentsService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectTaskActivityService;
import com.bis.sourcelead.service.ProjectTaskCommentsService;
import com.bis.sourcelead.service.ProjectTaskDocumentsService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.PdfConversionUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author David Sammeta
 *
 */

@RestController
public class ProjectsDashboardRestController {
	private static final Logger logger = LoggerFactory.getLogger(ProjectsDashboardRestController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	 @Autowired
	 private ProjectsService projectsService; 
	 
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
	 public SubmissionApproverHierarchyService submissionApproverHierarchyService;
	 
	 @Autowired
	 private OrgLocationService orgLocationService;
	 
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
	 ClientVendorExport clientVendorExport;
	 
	 @Autowired
	 OrgRelationsService orgRelationsService;
	 
	 @Autowired
	 SourceleadELKBaseIndexer<ClientVendorIndex> sourceleadELKBaseIndexer;
	 
	 @Autowired
	 ProjectDocumentsService projectDocumentsService;
	 
	@Autowired
	private PdfConversionUtil pdfConversionUtil;
	
	@Autowired
	LookUpDataService lookUpDataService;
	
	@Autowired
	private ProjectTaskDocumentsService projectTaskDocumentsService;
	
	@Autowired
	ProjectAssignTasksService projectAssignTasksService;
	
	@Autowired
	ProjectTaskActivityService projectTaskActivityService;
	
	@Autowired
	ProjectTaskCommentsService projectTaskCommentsService;
	
	@Autowired
	ProjectTasksService projectTasksService;
	
	@Autowired
	TimeZoneService timeZoneService;
		/**
		 * to filter the projects based on client and project status
		 * @author David Sammeta
		 * @param values
		 * @param request
		 * @param clients
		 * @return String
		 * @throws Exception
        */
			@RequestMapping(value = "/getProjectsByStatusAndClient")
			public ResponseList<ProjectsDTO> getProjectsByStatusAndClient(ProjectsDTO projectsDTO,@RequestParam(value="values", required=false) String values,HttpServletRequest request,@RequestParam(value="clients", required=false) String clients) throws Exception {

			    String loginOrgLocation = (String) SourceLeadSessionManager.getSessionAttribute(request, "locationCode");
			    OrgLocations loginOrgLocations = orgLocationService.getOrgLocationsByLocationCode(loginOrgLocation);
			    String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
				User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");

				String[] tableColumns = {"projectSequeneNumber", "projectName", "projectManagers", "invoiceFrequency", "budget", "projectMode", "projectDate", "client","status"};
			    int sortColumnIndex = 1; //by default sorting using updated date
				String sortColumnOrder = "desc"; //by default descending order
				if(projectsDTO.getOrder() != null && !projectsDTO.getOrder().isEmpty()) {
					sortColumnIndex = Integer.parseInt(projectsDTO.getOrder().get(0).get("column"));
					sortColumnOrder = projectsDTO.getOrder().get(0).get("dir");
				}
				projectsDTO.setSortColumn(tableColumns[sortColumnIndex]);
				projectsDTO.setSortOrder(sortColumnOrder);
				ResponseList<ProjectsDTO> projectsList = projectsService.getProjectsListByClientsAndStatus(projectsDTO,user.getId(), clients, values,loginOrgLocations.getId(),userRole,candidateId);
			    
				return projectsList;
			}
	
	/**
	 * get project details by project Id
	 * @author Laxman
	 * @param id
	 * @return Json
	 * @throws Exception
	 */
	 @PreAuthorize("hasAnyRole('ROLE_VIEW_EDIT_PROJECT','ROLE_VIEW_PROJECT')") 
	 @RequestMapping(value = "/getProjectById", method = RequestMethod.POST)
	 public @ResponseBody ProjectsDTO getProjectById(@RequestParam(value="ProjectId", required=false) Long projectId,HttpServletRequest request) throws Exception{ 
		   String loginOrgLocation = (String) SourceLeadSessionManager.getSessionAttribute(request, "locationCode");
		   User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		   
		     ProjectsDTO ProjectDetails = projectsService.getProjectDetailsByProjectIdAndLoginOrgId(projectId, user, loginOrgLocation);
		   
			 return ProjectDetails;
	  }
	 
	 @PreAuthorize("hasRole('ROLE_CREATE_PROJECT')")
	 @RequestMapping(value = "/getProjectDetailsByProjectId", method = RequestMethod.POST)
	 public @ResponseBody Projects getProjectDetailsByProjectId(@RequestParam(value="projectId", required=false) Long projectId,HttpServletRequest request) throws Exception{ 
		   
		     OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		     Projects projects = projectsService.getProjectById(projectId);
		     Set<ProjectClients> projectClientsList = projects.getProjectClients();
		     if(projectClientsList != null){
		    	 for(ProjectClients projectClients : projectClientsList){
		    		 if(projectClients.getOrgLocationsId().getId().equals(loginOrgLocation.getId())){
		    			 Set<ProjectApproverHierarchy> approverHierarchiesList =  projectClients.getProjectApproverHierarchyList();
			    		 if(approverHierarchiesList != null){
			    			 Set<ProjectApproverHierarchy> hirearchySet = new LinkedHashSet<ProjectApproverHierarchy>();
			    			 for(ProjectApproverHierarchy hierarcy : approverHierarchiesList){
			    				 hirearchySet.add(hierarcy);
				    		 }
			    			 projects.setApproverHierarchyList(hirearchySet);
			    		 }
		    		 }
		    	 }
		     }
		     
		     Set<ProjectBudget> projectBudgetList = projects.getProjectBudget();
		     if(projectBudgetList != null && projectBudgetList.size() > 0) {
		    	 Set<ProjectBudget> ProjectBudgetList =  new LinkedHashSet<ProjectBudget>();
		    	 for(ProjectBudget pl:projectBudgetList ) {
			    	 pl.setEstimatedBudgetCurrency(pl.getEstimatedBudgetCurrency());
			    	 pl.setEstimatedHours(pl.getEstimatedHours());
			    	 pl.setEstimatedBudgetValue(pl.getEstimatedBudgetValue());
			    	 if(pl.getApproverUserId() != null) {
			    		 pl.setUserId(pl.getApproverUserId().getId());
			    	 }

			    	 ProjectBudgetList.add(pl);
			     }
		    	 projects.setProjectBudgetList(ProjectBudgetList);
		     }
		     
		    
		     
		     Set<ProjectDocuments> projectDocumentsList = projects.getProjectDocuments();
		     if(projectDocumentsList != null && projectDocumentsList.size() > 0){
		    	 Set<ProjectDocuments> documentsList =  new LinkedHashSet<ProjectDocuments>();
		    	 for(ProjectDocuments projectDocuments : projectDocumentsList){
		    		 if(projectDocuments.getStatus().equals(1)){
		    			 documentsList.add(projectDocuments);
		    		 }
		    	 }
		    	 projects.setProjectDocumentsList(documentsList);
		     }
		     
			 return projects;
	  }
	 
	 
	 
	 @RequestMapping(value = "/getProjectDetailsForResouceTransfer", method = RequestMethod.POST)
	 public @ResponseBody ProjectsDTO getProjectDetailsForResouceTransfer(@RequestParam(value="projectId", required=false) Long projectId,
			 @RequestParam(value="orgLocationId", required=false) Long orgLocationId,HttpServletRequest request) throws Exception{ 
		     User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		     OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		     ProjectsDTO projectDetails = projectsService.getProjectDetailsByProjectIdAndLoginOrgId(projectId, user, orgLocations.getLocationCode());
			 return projectDetails;
	  }
	 
	 /**
	  * update the project by project ID
	  * @author Laxman
	  * @param projectId
	  * @param json
	  * @param request
	  * @return String
	  * @throws Exception
	  */
	 @PreAuthorize("hasAnyRole('ROLE_VIEW_EDIT_PROJECT')")
	 @RequestMapping(value = "/updateProjectByProjectId", method = RequestMethod.POST)
	 public String updateProjectByProjectId(ProjectsDTO projectsDTO,@RequestParam(value="ProjectId", required=false) Long projectId,HttpServletRequest request) throws Exception{ 
		 String resultJson;
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user"); 
		 Projects projects = projectsService.getProjectDetailsUpdateByProjectId(projectId, user, projectsDTO);
	
		 resultJson = commonUtil.toJSON(projects);
		 return resultJson;
	 }
	 
	 /** 
		 * get projects tasks based on project id
		 * @param projectId
		 * @return JSON
		 * @throws Exception
		 */
		 @PreAuthorize("hasAnyRole('ROLE_VIEW_PROJECT_TASKS', 'ROLE_VIEW_EDIT_PROJECT_TASKS')")
		 @RequestMapping(value = "/getProjectTasks" , method = RequestMethod.POST)
		 public @ResponseBody   List<ProjectTasks> getProjectTasks(@RequestParam(value="projectId", required=false) Long projectId,@RequestParam(value="taskType", required=false) String taskType,@RequestParam(value="taskStatus", required=false) String taskStatus) throws Exception {
			 List<ProjectTasks> taskList=tasksService.getAllTasksByProjectIdAndTaskTypeAndTaskStatus(projectId,taskType,taskStatus);
			 return taskList;
		 }
				 
	/**
	 * Get Employees list based on project id
	 * @param projectId
	 * @return JSON
	 * @throws Exception
	 */
	 @PreAuthorize("hasAnyRole('ROLE_VIEW_PROJECT_EMPLOYEES', 'ROLE_VIEW_EDIT_PROJECT_EMPLOYEES', 'ROLE_VIEW_DELETE_PROJECT_EMPLOYEES')")
	 @RequestMapping(value = "/getProjectEmployeeListById", method = RequestMethod.POST)
	 public @ResponseBody List<ProjectMembers>  getProjectEmployeeListById(@RequestParam(value="status", required=false) String status,@RequestParam(value="projectId", required=false) Long projectId,
			 HttpServletRequest request) throws Exception{ 
		 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 List<ProjectMembers> employeeList = projectsService.getProjectEmployeeListByProjectIdAndStatus(projectId,status,loginOrgLocation);
		 return employeeList;
	  }
	 
	 /**
	  * Save Employee from project summary
	  * @param json
	  * @param request
	  * @return JSON
	  * @throws Exception
	  */
	 @PreAuthorize("hasRole('ROLE_CREATE_PROJECT_EMPLOYEES')")
	 @RequestMapping(value = "/addEmployeeForProjectDashboard",  method = RequestMethod.POST)
		public @ResponseBody  String addEmployeeForProjectDashboard(@RequestParam(value="json", required=false) String json,HttpServletRequest request) throws Exception {
		   String  json1 = "{"+json+"}";
		    JSONObject jsonObj = new JSONObject(json1);
		 	OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 	User users = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			
		 	//userService.storeUser(jsonObj,users,loginOrgLocation,"PROJECTS","",new Org());
			return "SUCCESS";
		}
		 
	/**
	 * Get Teammembers list by project id	
	 * @param projectId
	 * @param request
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/getTeamMembersList",  method = RequestMethod.POST)
		public @ResponseBody  String getTeamMembersList(@RequestParam(value="ProjectId", required=false) Long projectId,@RequestParam(value="searchByName", required=false) String searchByName, HttpServletRequest request) throws Exception {
		 	String resultJson;
		 	String loginOrgLocation = (String) SourceLeadSessionManager.getSessionAttribute(request, "locationCode");
			OrgLocations loginOrgLocations = orgLocationService.getOrgLocationsByLocationCode(loginOrgLocation);
			
			 List<Employees>  employeList = employeesService.getEmployeesExcludeTeamMembersBySearchByName(projectId, loginOrgLocations.getId(), searchByName);
			 for (Employees employees : employeList) {
			    	UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(employees.getUser().getId(), loginOrgLocations.getId());
			    	if(userRole != null){
			    		employees.setUserType(userRole.getUserType());
			    	}
				}
			 resultJson = commonUtil.toJSON(employeList);
			 return resultJson;
		}
	/**
	 * Assign Employee to project by  project id	 
	 * @param projectId
	 * @param employeeId
	 * @param startDate
	 * @param endDate
	 * @param mailtoMembers
	 * @param request
	 * @return Json
	 * @throws Exception
	 */
	
	 @PreAuthorize("hasRole('ROLE_ASSIGN_EMPLOYEE_TO_PROJECT')")
	 @RequestMapping(value = "/storeAssignEmployee",  method = RequestMethod.POST)
		 public @ResponseBody  String storeAssignEmployee(
				@RequestParam(value="ProjectId", required=false) Long projectId,
				@RequestParam(value="employeeId", required=false) Long employeeId,
				@RequestParam(value="startDate", required=false) String startDate,
				@RequestParam(value="endDate", required=false) String endDate,
				@RequestParam(value="mailtoMembers", required=false) String mailtoMembers, HttpServletRequest request) throws Exception {
		 	
			 	OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			 	String loginOrgCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "orgCode");
			 	User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			 	String storeAssignEmployee = projectMembersService.storeAssignEmployeeByProjectIdEmployeeIdStartDateEndaDate(projectId, employeeId, startDate, endDate, mailtoMembers, loginOrgCode, user, loginOrgLocation);
			 
				return storeAssignEmployee;
		}
	
	
	 
	 /**
	   * @author Venkat Kesav
	   * @since  Jan 25 2017
	   * @version 0.0.1
	   * 
	   *  
	   * @return string 
	   * 
	   * @throws NullPointer
	   */
	 @RequestMapping(value = "/searchProject", method = RequestMethod.GET)
	 public String searchProject(@RequestParam(value = "json", required = false) String json, HttpServletRequest request) throws Exception {

			String jsonc = "{" + json + "}";
			JSONObject jsonObj = new JSONObject(jsonc);
			
			 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			
			 BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
			 
			 boolQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", loginOrgLocation.getId()));
			 
			 //Integer maxResults = projectsService.getAllProjects().size();
			 
			 Integer maxResults = projectsService.getProjectCountByOrgLocationId(loginOrgLocation.getId());
		   	 
			 //Exclude Keyword
			 /*ArrayList<String> excludeKeyWordArray = getSearchKeywordsFromChosenSelectField(jsonObj, "excludeKeyWord");
			 if(excludeKeyWordArray != null && !excludeKeyWordArray.isEmpty()){
			 	 BoolQueryBuilder excludeBoolQueryBuilder = QueryBuilders.boolQuery();
				 for(String keywd : excludeKeyWordArray){
					 BoolQueryBuilder excludeBoolSubQueryBuilder = QueryBuilders.boolQuery();
					ArrayList<MatchQueryBuilder> matchQueryBuilderList =getMatchQueryBuilderList(keywd);
					if(matchQueryBuilderList != null && !matchQueryBuilderList.isEmpty()){
						for(MatchQueryBuilder matchQueryBuilder :matchQueryBuilderList){
							excludeBoolSubQueryBuilder.should(matchQueryBuilder);
						}
						excludeBoolQueryBuilder.should(excludeBoolSubQueryBuilder);
					}
				}
				 boolQueryBuilder.mustNot(excludeBoolQueryBuilder);
			  }*/
				
				//Any Keyword
				/*ArrayList<String> anyKeyWordArray = getSearchKeywordsFromChosenSelectField(jsonObj, "anyOfKeyword");
				if(anyKeyWordArray != null && !anyKeyWordArray.isEmpty()){
					 BoolQueryBuilder anyBoolQueryBuilder = QueryBuilders.boolQuery();
					 for(String keywd : anyKeyWordArray){
						 BoolQueryBuilder anyBoolSubQueryBuilder = QueryBuilders.boolQuery();
						ArrayList<MatchQueryBuilder> matchQueryBuilderList =getMatchQueryBuilderList(keywd);
						if(matchQueryBuilderList != null && !matchQueryBuilderList.isEmpty()){
							for(MatchQueryBuilder matchQueryBuilder :matchQueryBuilderList){
								anyBoolSubQueryBuilder.should(matchQueryBuilder);
							}
							anyBoolQueryBuilder.should(anyBoolSubQueryBuilder);
						}
					}
					 boolQueryBuilder.must(anyBoolQueryBuilder);
				}*/
				//All Keywords
				/*ArrayList<String> allKeyWordArray = getSearchKeywordsFromChosenSelectField(jsonObj, "allKeywords");
				if(allKeyWordArray != null && !allKeyWordArray.isEmpty()){
					 BoolQueryBuilder allBoolQueryBuilder = QueryBuilders.boolQuery();
					 for(String keywd : allKeyWordArray){
						 BoolQueryBuilder allBoolSubQueryBuilder = QueryBuilders.boolQuery();
						ArrayList<MatchQueryBuilder> matchQueryBuilderList =getMatchQueryBuilderList(keywd);
						if(matchQueryBuilderList != null && !matchQueryBuilderList.isEmpty()){
							for(MatchQueryBuilder matchQueryBuilder :matchQueryBuilderList){
								allBoolSubQueryBuilder.should(matchQueryBuilder);
							}
							allBoolQueryBuilder.must(allBoolSubQueryBuilder);
						}
					}
					 boolQueryBuilder.must(allBoolQueryBuilder);
				}*/
				
				//project Id
				ArrayList<String> projectIdArray = getSearchKeywordsFromChosenSelectField(jsonObj, "projectIdAdvanced");
				if(projectIdArray != null && !projectIdArray.isEmpty()){
					BoolQueryBuilder projectIdBoolQueryBuilder = QueryBuilders.boolQuery();
					for(String keywd : projectIdArray){
						projectIdBoolQueryBuilder.should(QueryBuilders.matchQuery("projectSequeneNumber", keywd));
					}
					boolQueryBuilder.must(projectIdBoolQueryBuilder);
				}
				
				//project Name
				ArrayList<String> projectNameArray = getSearchKeywordsFromChosenSelectField(jsonObj, "projectNameAdvanced");
				if(projectNameArray != null && !projectNameArray.isEmpty()){
					BoolQueryBuilder projectNameBoolQueryBuilder = QueryBuilders.boolQuery();
					for(String keywd : projectNameArray){
						projectNameBoolQueryBuilder.should(QueryBuilders.matchQuery("projectName", keywd));
					}
					boolQueryBuilder.must(projectNameBoolQueryBuilder);
				}
				
				//project Manager
				ArrayList<String> projectManagerArray = getSearchKeywordsFromChosenSelectField(jsonObj, "projectManagerAdvanced");
				if(projectManagerArray != null && !projectManagerArray.isEmpty()){
					BoolQueryBuilder projectManagerBoolQueryBuilder = QueryBuilders.boolQuery();
					for(String keywd : projectManagerArray){
						projectManagerBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("level1ManagerName", keywd));
						projectManagerBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("level2ManagerName", keywd));
					}
					boolQueryBuilder.must(projectManagerBoolQueryBuilder);
				}
				
				
				//project Manager2
				/*ArrayList<String> projectManager2Array = getSearchKeywordsFromChosenSelectField(jsonObj, "projectManagerAdvanced");
				if(projectManager2Array != null && !projectManager2Array.isEmpty()){
					BoolQueryBuilder projectManager2BoolQueryBuilder = QueryBuilders.boolQuery();
					for(String keywd : projectManager2Array){
						projectManager2BoolQueryBuilder.should(QueryBuilders.matchQuery("level2ManagerName", keywd));
					}
					boolQueryBuilder.must(projectManager2BoolQueryBuilder);
				}*/
				
				//invoice Frequency
				ArrayList<String> invoiceFrequencyArray = getSearchKeywordsFromChosenSelectField(jsonObj, "invoiceFrequencyAdvanced");
				if(invoiceFrequencyArray != null && !invoiceFrequencyArray.isEmpty()){
					BoolQueryBuilder invoiceFrequencyBoolQueryBuilder = QueryBuilders.boolQuery();
					for(String keywd : invoiceFrequencyArray){
						if(keywd.equalsIgnoreCase("Weekly")){
							invoiceFrequencyBoolQueryBuilder.should(QueryBuilders.matchPhrasePrefixQuery("invoiceFrequency", "Weekly"));
							invoiceFrequencyBoolQueryBuilder.mustNot(QueryBuilders.matchPhrasePrefixQuery("invoiceFrequency", "Bi-Weekly"));
						}else if(keywd.equalsIgnoreCase("Monthly")){
							invoiceFrequencyBoolQueryBuilder.should(QueryBuilders.matchPhrasePrefixQuery("invoiceFrequency", "Monthly"));
							invoiceFrequencyBoolQueryBuilder.mustNot(QueryBuilders.matchPhrasePrefixQuery("invoiceFrequency", "Semi-Monthly"));
						}
						else{
							invoiceFrequencyBoolQueryBuilder.should(QueryBuilders.matchPhrasePrefixQuery("invoiceFrequency", keywd));
						}
					}
					boolQueryBuilder.must(invoiceFrequencyBoolQueryBuilder);
				}
				
				//client Name
				ArrayList<String> clientsArray = getSearchKeywordsFromChosenSelectField(jsonObj, "clientsAdvanced");
				if(clientsArray != null && !clientsArray.isEmpty()){
					BoolQueryBuilder clientsBoolQueryBuilder = QueryBuilders.boolQuery();
					for(String keywd : clientsArray){
						clientsBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("clientName", keywd));
					}
					boolQueryBuilder.must(clientsBoolQueryBuilder);
				}
				
				//project Status
				ArrayList<String> projectStatusArray = getSearchKeywordsFromChosenSelectField(jsonObj, "statusAdvanced");
				if(projectStatusArray != null && !projectStatusArray.isEmpty()){
					BoolQueryBuilder projectStatusBoolQueryBuilder = QueryBuilders.boolQuery();
					for(String keywd : projectStatusArray){
						projectStatusBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("projectStatus", keywd));
					}
					boolQueryBuilder.must(projectStatusBoolQueryBuilder);
				}
				
				//project start Date
				/*ArrayList<String> startDateArray = getSearchKeywordsFromChosenSelectField(jsonObj, "projectStartDateAdvanced");
				if(startDateArray != null && !startDateArray.isEmpty()){
					BoolQueryBuilder startDateBoolQueryBuilder = QueryBuilders.boolQuery();
					for(String keywd : startDateArray){
						startDateBoolQueryBuilder.should(QueryBuilders.matchQuery("startDate", keywd));
					}
					boolQueryBuilder.must(startDateBoolQueryBuilder);
				}*/
				String startDateKey = getSearchKeywordsFromTextField(jsonObj, "projectStartDateAdvanced");
				if(startDateKey != null && !startDateKey.isEmpty()){
					BoolQueryBuilder startDateBoolQueryBuilder = QueryBuilders.boolQuery();
					startDateBoolQueryBuilder.must(QueryBuilders.matchPhraseQuery("startDate", startDateKey));
					boolQueryBuilder.must(startDateBoolQueryBuilder);
				}
				
				//project Members
				ArrayList<String> projectMembersArray = getSearchKeywordsFromChosenSelectField(jsonObj, "employeeNameAdvanced");
				if(projectMembersArray != null && !projectMembersArray.isEmpty()){
					BoolQueryBuilder projectMembersBoolQueryBuilder = QueryBuilders.boolQuery();
					for(String keywd : projectMembersArray){
						projectMembersBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("projectMembers", keywd));
					}
					boolQueryBuilder.must(projectMembersBoolQueryBuilder);
				}
				
				//project Member Email
				ArrayList<String> projectMemberEmailArray = getSearchKeywordsFromChosenSelectField(jsonObj, "employeeEmailAdvanced");
				if(projectMemberEmailArray != null && !projectMemberEmailArray.isEmpty()){
					BoolQueryBuilder projectMemberEmailBoolQueryBuilder = QueryBuilders.boolQuery();
					for(String keywd : projectMemberEmailArray){
						projectMemberEmailBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("projectMemberEmail", keywd));
					}
					boolQueryBuilder.must(projectMemberEmailBoolQueryBuilder);
				}
				
				List<ProjectsIndex> projectsList = new ArrayList<ProjectsIndex>();
				try {
					SearchHit[] searchHits = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_PROJECT_INDEX, Constants.ELK_PROJECT_TYPE,0,maxResults.intValue(),boolQueryBuilder).getHits();
					if(searchHits != null && searchHits.length >0){
						ObjectMapper mapper = new ObjectMapper();
						for (SearchHit hit : searchHits) {
							json = hit.getSourceAsString();
							ProjectsIndex projectsIndex = mapper.readValue(json, ProjectsIndex.class);
							projectsList.add(projectsIndex);
						}
					}
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			 String resultJson;
			 resultJson = commonUtil.toJSON(projectsList);
			 return resultJson;
	 }
	 
	 private String getSearchKeywordsFromTextField(JSONObject jsonObj,String param) throws Exception{
		 		String startDate = "";
		 		if(jsonObj.has(param)){
		 			startDate = jsonObj.getString("projectStartDateAdvanced");
		 		}
		 		return startDate;
	 }
	 
	 private ArrayList<String> getSearchKeywordsFromChosenSelectField(JSONObject jsonObj,String param) throws Exception {
		 ArrayList<String> keyWordList = null;
		 if(jsonObj.has(param)){
			 String searchKeywordString = null;
			 try{
	   		   JSONArray jsonArray = jsonObj.getJSONArray(param);
	   		   searchKeywordString = jsonArray.toString();
		   	  }
		   	  catch(JSONException e){
		   		  searchKeywordString= jsonObj.getString(param);
		   	  }
			 
		     searchKeywordString = searchKeywordString.replaceAll("\\[", "").replaceAll("\\]","");
		     keyWordList = new ArrayList<String>(Arrays.asList(searchKeywordString.split(",")));
		 }
		 else{
			 keyWordList = new ArrayList<String>();
		 }
         return keyWordList;
    }

	 private ArrayList<MatchQueryBuilder> getMatchQueryBuilderList(String keywd){
		    ArrayList<MatchQueryBuilder> matchQueryBuilderList = new ArrayList<MatchQueryBuilder>();
		   
		    //Project Search
		    //matchQueryBuilderList.add(QueryBuilders.matchQuery("id", keywd));
		    matchQueryBuilderList.add(QueryBuilders.matchQuery("projectSequeneNumber", keywd));
		    matchQueryBuilderList.add(QueryBuilders.matchQuery("projectName", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("level1ManagerName", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("level2ManagerName", keywd));
			//matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("startDate", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("invoiceFrequency", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("clientName", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("projectStatus", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("projectMembers", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("projectMemberEmail", keywd));
			return matchQueryBuilderList; 
	 }
	 
	 @RequestMapping(value = "/getUniversalProjectsListByOrgLocationID")
	 public String getUniversalProjectsListByOrgLocationID(@RequestParam(value="searchValue", required=false) String searchValue,HttpServletRequest request) throws Exception{
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 String resultJson;
			List<Object> uniProjectsList =	projectsService.getAllUniversalSearchProjectsList(orgLocations, searchValue);
			resultJson = commonUtil.toJSON(uniProjectsList);
			return resultJson;
	 }
	
	 /**
	  * @author Laxman
	  * @param projectId
	  * @return projectDes
	  * @throws Exception
	  */
	 @RequestMapping(value = "/getProjectDescriptionById", method = RequestMethod.GET)
	 public @ResponseBody String getProjectDescriptionById(@RequestParam(value="projectId", required=false) Long projectId) throws Exception{ 
		 Projects projectDes = null;
		 if(projectId != null){
			 projectDes = projectsService.getProjectById(projectId);
		 }
				return commonUtil.toJSON(projectDes);
	  }
	 
	 /**
	  * @author Laxman
	  * @param projectId
	  * @return resultJson
	  * @throws Exception
	  */
	 @RequestMapping(value = "/getProjectDocumentsList")
		public String getProjectDocumentsList(@RequestParam(value = "projectId", required = false) Long projectId) throws Exception
		{
			String resultJson;
			List<ProjectDocuments> projectDocumentList = projectDocumentsService.getProjectDocumentsListByProjectIdId(projectId);
			resultJson = commonUtil.toJSON(projectDocumentList);
			return resultJson;
		}
	 /**
	  * @author Laxman
	  * @param porjectDocumentId
	  * @return success
	  * @throws Exception
	  */
	 @RequestMapping(value = "/terminateProjectDoc", method = RequestMethod.POST)
		public @ResponseBody String terminateProjectDoc(@RequestParam(value = "porjectDocumentId", required = false) String porjectDocumentId) throws Exception
		{
			ProjectDocuments document = projectDocumentsService.getDocumentById(Long.valueOf(porjectDocumentId));
			if(document != null){
				document.setStatus(Constants.CANDIDATE_IN_ACTIVE);
				projectDocumentsService.updateProjectDocuments(document);
			}
			return "success";
		}
	 
	 /**
	  * @author Laxman
	  * @param porjectDocumentId
	  * @param response
	  * @throws Exception
	  */
		@RequestMapping(value = "/downloadProjectDoc", method = RequestMethod.GET)
		public void downloadProjectDoc(@RequestParam(value = "porjectDocumentId", required = false) String porjectDocumentId, HttpServletResponse response) throws Exception
		{
			ProjectDocuments document = projectDocumentsService.getDocumentById(Long.valueOf(porjectDocumentId));
			if (null != document) {
				response.setContentType("application/octet-stream");
				StringBuilder fileName = new StringBuilder();
				response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(document.getFileName()).append("\"").toString());
				InputStream inputStream = new ByteArrayInputStream(document.getFileContents());
				try {
					OutputStream outStream = response.getOutputStream();
					byte[] buffer = new byte[4096];
					int bytesRead = -1;
					while ((bytesRead = inputStream.read(buffer)) != -1) {
						outStream.write(buffer, 0, bytesRead);
					}
					inputStream.close();
					outStream.close();
				} catch (FileNotFoundException e) {
					logger.error(ExceptionUtils.getFullStackTrace(e));
				}
			}
		}
		

		/**
		 * @author Laxman
		 * @param documentUUID
		 * @param title
		 * @param response
		 * @throws Exception
		 */
		 @RequestMapping(value = "/viewProjectDocument/{title}", method = RequestMethod.GET)
		    public void viewProjectDocument(@RequestParam(value="documentUUID", required=false) String documentUUID,
		    		@PathVariable(value="title") String title,
		    		HttpServletResponse response) throws Exception {
			 
			 ProjectDocuments document = projectDocumentsService.getDocumentById(Long.valueOf(documentUUID));	
				    
			 byte[] pdfBytes = null;
				if("application/pdf".equalsIgnoreCase(document.getFileType())){
					pdfBytes = document.getFileContents();
				}
				else{
					 pdfBytes = pdfConversionUtil.convertDocToPdf(document.getFileContents());
				}
				 response.setHeader("Content-disposition","inline; filename=contractPdf.pdf");
			  	    response.setContentType("application/pdf");
			  	    ServletOutputStream sos = response.getOutputStream();
			  	    if(null != pdfBytes){
			  	    	response.setContentLength(pdfBytes.length);
			  	    	sos.write(pdfBytes);
			  	    }
			          sos.flush();
			          sos.close();

			} 
		 
		 /**
		  * @author Laxman
		  * @param json
		  * @param projectId
		  * @param request
		  * @return 
		  * @throws Exception
		  */
		 @RequestMapping(value = "/saveProjectDocument", method = RequestMethod.POST)
			public @ResponseBody String saveProjectDocument(@RequestParam(value = "json", required = false) String json, @RequestParam(value = "projectId", required = false) Long projectId,@RequestParam(value = "documentId", required = false) Long documentId,  MultipartHttpServletRequest request) throws Exception
			{
				String json1 = "{" + json + "}";
				JSONObject jsonObj = new JSONObject(json1);
				MultipartFile multiFile = null;
				Iterator<String> itrator = request.getFileNames();
				if (itrator.hasNext()) {
					multiFile = request.getFile(itrator.next());
				}
				if (null != projectId) {
					projectDocumentsService.storeProjectDocuments(jsonObj, projectId,multiFile,documentId);
					return "success";
				}
				else {
					return "exception";
				}
			}
		 
		 /**
		  * @author Laxman
		  * @param documentId
		  * @return json
		  * @throws Exception
		  */
		 @RequestMapping(value = "/getProjectDocument", method = RequestMethod.POST)
		 public @ResponseBody String getProjectDocument(@RequestParam(value = "documentId", required = false) String documentId) throws Exception
		 {
			 ProjectDocuments document = projectDocumentsService.getDocumentById(Long.valueOf(documentId));	
			 return commonUtil.toJSON(document);
		 }
		 
		 
}
