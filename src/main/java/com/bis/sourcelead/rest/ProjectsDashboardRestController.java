package com.bis.sourcelead.rest;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
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
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.GenericDao;
import com.bis.sourcelead.dao.ProjectDocumentPermissionDao;
import com.bis.sourcelead.dto.ClientOrgRelationsDTO;
import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.dto.OrglocationFormFieldsDTO;
import com.bis.sourcelead.dto.ProjectTasksDTO;
import com.bis.sourcelead.dto.ProjectsDTO;
import com.bis.sourcelead.dto.ProjectsDocumentFolderDTO;
import com.bis.sourcelead.dto.ProjectsDocumentsDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.SourceleadSectionDTO;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.elk.index.ClientVendorIndex;
import com.bis.sourcelead.elk.index.ProjectsIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.ExpenseProjectApproverHierarchy;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectAssignTask;
import com.bis.sourcelead.entity.ProjectBudget;
import com.bis.sourcelead.entity.ProjectClients;
import com.bis.sourcelead.entity.ProjectDocumentFolder;
import com.bis.sourcelead.entity.ProjectDocumentPermission;
import com.bis.sourcelead.entity.ProjectDocuments;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.ClientVendorExport;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.ExpenseProjectApproverHierarchyService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectAssignTasksService;
import com.bis.sourcelead.service.ProjectClientService;
import com.bis.sourcelead.service.ProjectCustomFieldDataService;
import com.bis.sourcelead.service.ProjectDocumentFolderService;
import com.bis.sourcelead.service.ProjectDocumentPermissionService;
import com.bis.sourcelead.service.ProjectDocumentsService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectTaskActivityService;
import com.bis.sourcelead.service.ProjectTaskCommentsService;
import com.bis.sourcelead.service.ProjectTaskDocumentsService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.SourceleadFormSectionsService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.timesheet.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.timesheet.service.TimesheetSubmissionsService;
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
@RequestMapping("/app")
public class ProjectsDashboardRestController {
	private static final Logger logger = LoggerFactory.getLogger(ProjectsDashboardRestController.class);
	
	@Value("${static.path}")
	private String staticPath;
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	 @Autowired
	 HttpServletRequest request;
	
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
	 public ExpenseProjectApproverHierarchyService expenseProjectApproverHierarchyService;
	 
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
	
	@Autowired
    ProjectDocumentFolderService projectDocumentsFolderService;
	
	@Autowired
	ProjectDocumentPermissionService projectDocumentPermissionService;
	
	@Autowired
	GenericDao<ProjectDocuments> genericDao;
	
	@Autowired
	ProjectClientService projectClientService;
	
	@Autowired
	private SourceleadFormSectionsService sourceleadFormSectionsService;
	
	@Autowired
	private SourceleadActionsService sourceleadActionsService;
	
	@Autowired
	private ProjectDocumentPermissionDao projectDocumentPermissionDao;
	
	@Autowired
	private ProjectCustomFieldDataService projectCustomFieldDataService;
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
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
				Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
				Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
				String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
				projectsDTO.setIsGlobalAccess(isGlobalAccess);
				projectsDTO.setOrgId(org.getId());
				String[] tableColumns = {"id","projectSequeneNumber", "projectName", "projectManagers", "invoiceFrequency", "budget", "startDate", "client","status"};
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
	 @RequestMapping(value = "/getClientProjectApprovers", method = RequestMethod.POST)
	 public @ResponseBody ProjectsDTO getClientProjectApprovers(@RequestParam(value="projectClientUUID", required=false) String projectClientUUID,HttpServletRequest request) throws Exception{
		 
		 OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		 
		 ProjectsDTO projectsDTO = new ProjectsDTO();
		 ProjectClients projectClients = projectClientService.getProjectClientsByUUID(projectClientUUID);
		 if(projectClients != null) {
			 List<ProjectApproverHierarchy> approverList =  projectApproverHierarchyService.getProjectsHierarchyListByProjectClientId(projectClients.getId());
			    if(approverList != null && !approverList.isEmpty()) {
				 	for(ProjectApproverHierarchy approverHierarchy : approverList){
				    	if(approverHierarchy.getLevel() != null && approverHierarchy.getLevel() == 1 && (approverHierarchy.getStatus() == null || approverHierarchy.getStatus().equalsIgnoreCase("1"))){
				    		projectsDTO.setManager1(approverHierarchy.getApproverUserId().getId().intValue());
				    		projectsDTO.setManager1Name(approverHierarchy.getApproverUserId().getFullName());
				    		UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(approverHierarchy.getApproverUserId().getId(),loginOrgLocations.getId());
				    		if(null != userRole){
				    			projectsDTO.setManagerLevel1UserType(userRole.getUserType());
				    		}
				    		StringBuilder projectApprover = new StringBuilder();
				    		projectsDTO.setManagerLevel1(projectApprover.append(approverHierarchy.getApproverUserId().getFirstName()).append(" ").append(approverHierarchy.getApproverUserId().getLastName()).toString());
				    		projectsDTO.setProjectApprovalType(approverHierarchy.getApprovalMechanismType());
				    	}
				    	if(approverHierarchy.getLevel() != null && approverHierarchy.getLevel() == 2 && (approverHierarchy.getStatus() == null || approverHierarchy.getStatus().equalsIgnoreCase("1"))){
				    		projectsDTO.setManager2(approverHierarchy.getApproverUserId().getId().intValue());
				    		projectsDTO.setManager2Name(approverHierarchy.getApproverUserId().getFullName());
				    		StringBuilder projectApprover = new StringBuilder();
				    		projectsDTO.setManagerLevel2(projectApprover.append(approverHierarchy.getApproverUserId().getFirstName()).append(" ").append(approverHierarchy.getApproverUserId().getLastName()).toString());
				    		projectsDTO.setProjectApprovalType(approverHierarchy.getApprovalMechanismType());
				    	}
				    	
				    }
			    }
			    
			    
				 Set<ExpenseProjectApproverHierarchy> expenseApproverList =  projectClients.getExpenseProjectApproverHierarchyList();
				    if(expenseApproverList != null && !expenseApproverList.isEmpty()) {
					 	for(ExpenseProjectApproverHierarchy approverHierarchy : expenseApproverList){
					    	if(approverHierarchy.getLevel() != null && approverHierarchy.getLevel() == 1 && (approverHierarchy.getStatus() == null || approverHierarchy.getStatus().equalsIgnoreCase("1"))){
					    		projectsDTO.setExpenseApprover1(approverHierarchy.getApproverUserId().getId().intValue());
					    		projectsDTO.setExpenseApprover1Name(approverHierarchy.getApproverUserId().getFullName());
					    		UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(approverHierarchy.getApproverUserId().getId(),loginOrgLocations.getId());
					    		if(null != userRole){
					    			projectsDTO.setExpenseApproverLevel1UserType(userRole.getUserType());
					    		}
					    		StringBuilder projectApprover = new StringBuilder();
					    		projectsDTO.setLevel1ExpenseApprover(projectApprover.append(approverHierarchy.getApproverUserId().getFirstName()).append(" ").append(approverHierarchy.getApproverUserId().getLastName()).toString());
					    		projectsDTO.setExpenseApprovalType(approverHierarchy.getApprovalMechanismType());
					    	}
					    	if(approverHierarchy.getLevel() != null && approverHierarchy.getLevel() == 2 && (approverHierarchy.getStatus() == null || approverHierarchy.getStatus().equalsIgnoreCase("1"))){
					    		projectsDTO.setExpenseApprover2(approverHierarchy.getApproverUserId().getId().intValue());
					    		projectsDTO.setExpenseApprover2Name(approverHierarchy.getApproverUserId().getFullName());
					    		StringBuilder projectApprover = new StringBuilder();
					    		projectsDTO.setLevel2ExpenseApprover(projectApprover.append(approverHierarchy.getApproverUserId().getFirstName()).append(" ").append(approverHierarchy.getApproverUserId().getLastName()).toString());
					    		projectsDTO.setExpenseApprovalType(approverHierarchy.getApprovalMechanismType());
					    	}
					    	
					    }
				    }
			    
			    
			    List<User> userList = userService.getTabAccessOwnersByOrgLocationIdAndTabId(loginOrgLocations.getId(),Long.valueOf(Constants.TAB_TIMESHEET_APPROVAL));
			    List<User> globalUserList = userService.getTabAccessGlobalOwnersByOrgIdAndTabId(loginOrgLocations.getId(),org.getId(),Long.valueOf(Constants.TAB_TIMESHEET_APPROVAL));
			 
			    List<User> level1ApproversList = new CopyOnWriteArrayList<>();
			    List<User> level1ExpenseApproversList = new CopyOnWriteArrayList<>();
				if(userList != null && !userList.isEmpty()) {
					level1ApproversList.addAll(userList);
					level1ExpenseApproversList.addAll(userList);
				}
				if(globalUserList != null && !globalUserList.isEmpty()) {
					level1ApproversList.addAll(globalUserList);
					level1ExpenseApproversList.addAll(globalUserList);
				}
				HashMap<Long,User> userMap = new HashMap<>();
			    List<UserDTO> userDTOList = new ArrayList<>();
			    if(level1ApproversList != null && !level1ApproversList.isEmpty()) {
			    	for(User mrguser : level1ApproversList) {
			    		User tempUser = userMap.get(mrguser.getId());
						if(tempUser == null) {
							userMap.put(mrguser.getId(),mrguser);
							UserDTO userDTO = new UserDTO();
				    		userDTO.setId(mrguser.getId());
				    		userDTO.setName(mrguser.getFullName());
				    		userDTOList.add(userDTO);
				    		
				    		UserRole roleType = userRoleService.getUserRoleByUserIdAndLocationId(mrguser.getId(),loginOrgLocations.getId());
				    		if(roleType != null) {
				    			userDTO.setUserType(roleType.getUserType());
				    		}else {
				    			UserRole primaryRole = userRoleService.getLoginUserPrimaryRole(mrguser.getId());
				    			userDTO.setUserType(primaryRole.getUserType());
				    		}
						}else {
							level1ApproversList.remove(mrguser);
							level1ExpenseApproversList.remove(mrguser);
							continue;
						}
			    		
			    		
			    	}
			    }
			    projectsDTO.setManagersList(userDTOList);
			    projectsDTO.setExpenseApproversList(userDTOList);
			    
			    if(projectsDTO.getManager1() != 0){
			    	  List<User> userListwithoutmaneger1 = userService.getTabAccessOwnersByOrgLocationIdAndTabIdAndNoCurrentUser(Long.valueOf(projectsDTO.getManager1()),loginOrgLocations.getId(),Long.valueOf(Constants.TAB_TIMESHEET_APPROVAL));
			    	  List<User> globalUserListwithoutmaneger1 = userService.getTabAccessGlobalOwnersByOrgLocationIdAndTabIdAndNoCurrentUser(Long.valueOf(projectsDTO.getManager1()),org.getId(),Long.valueOf(Constants.TAB_TIMESHEET_APPROVAL));
			    	  List<UserDTO> mrguserDTOList = new ArrayList<>();
			    	  List<User> managersListwithoutmaneger1 = new CopyOnWriteArrayList<>();
			    	  if(userListwithoutmaneger1 != null && !userListwithoutmaneger1.isEmpty()) {
			    		  managersListwithoutmaneger1.addAll(userListwithoutmaneger1);
			    	  }
			    	  
			    	  if(globalUserListwithoutmaneger1 != null && !globalUserListwithoutmaneger1.isEmpty()) {
			    		  managersListwithoutmaneger1.addAll(globalUserListwithoutmaneger1);
			    	  }
			    	  HashMap<Long,User> userMap2 = new HashMap<>();
					    if(managersListwithoutmaneger1 != null && managersListwithoutmaneger1.size() > 0) {
					    	for(User mrguser2 : managersListwithoutmaneger1) {
					    		
					    		User tempUser = userMap2.get(mrguser2.getId());
					    		if(tempUser == null) {
					    			userMap2.put(mrguser2.getId(),mrguser2);
					    			UserDTO userDTO = new UserDTO();
						    		userDTO.setId(mrguser2.getId());
						    		userDTO.setName(mrguser2.getFullName());
						    		UserRole roleType = userRoleService.getUserRoleByUserIdAndLocationId(mrguser2.getId(),loginOrgLocations.getId());
						    		if(roleType != null) {
						    			userDTO.setUserType(roleType.getUserType());
						    		}else {
						    			UserRole primaryRole = userRoleService.getLoginUserPrimaryRole(mrguser2.getId());
						    			userDTO.setUserType(primaryRole.getUserType());
						    		}
						    		
						    		mrguserDTOList.add(userDTO);
					    		}else {
					    			mrguserDTOList.remove(mrguser2);
									continue;
					    		}
					    		
					    	}
					    }
					    projectsDTO.setManagersListwithoutManager1(mrguserDTOList);
			    }
			    
			    
			    
			    if(projectsDTO.getExpenseApprover1() != 0){
			    	  List<User> userListwithoutmaneger1 = userService.getTabAccessOwnersByOrgLocationIdAndTabIdAndNoCurrentUser(Long.valueOf(projectsDTO.getExpenseApprover1()),loginOrgLocations.getId(),Long.valueOf(Constants.TAB_TIMESHEET_APPROVAL));
			    	  List<User> globalUserListwithoutmaneger1 = userService.getTabAccessGlobalOwnersByOrgLocationIdAndTabIdAndNoCurrentUser(Long.valueOf(projectsDTO.getExpenseApprover1()),org.getId(),Long.valueOf(Constants.TAB_TIMESHEET_APPROVAL));
			    	  List<UserDTO> mrguserDTOList = new ArrayList<>();
			    	  List<User> managersListwithoutmaneger1 = new CopyOnWriteArrayList<>();
			    	  if(userListwithoutmaneger1 != null && !userListwithoutmaneger1.isEmpty()) {
			    		  managersListwithoutmaneger1.addAll(userListwithoutmaneger1);
			    	  }
			    	  
			    	  if(globalUserListwithoutmaneger1 != null && !globalUserListwithoutmaneger1.isEmpty()) {
			    		  managersListwithoutmaneger1.addAll(globalUserListwithoutmaneger1);
			    	  }
			    	  HashMap<Long,User> userMap2 = new HashMap<>();
					    if(managersListwithoutmaneger1 != null && managersListwithoutmaneger1.size() > 0) {
					    	for(User mrguser2 : managersListwithoutmaneger1) {
					    		
					    		User tempUser = userMap2.get(mrguser2.getId());
					    		if(tempUser == null) {
					    			userMap2.put(mrguser2.getId(),mrguser2);
					    			UserDTO userDTO = new UserDTO();
						    		userDTO.setId(mrguser2.getId());
						    		userDTO.setName(mrguser2.getFullName());
						    		UserRole roleType = userRoleService.getUserRoleByUserIdAndLocationId(mrguser2.getId(),loginOrgLocations.getId());
						    		if(roleType != null) {
						    			userDTO.setUserType(roleType.getUserType());
						    		}else {
						    			UserRole primaryRole = userRoleService.getLoginUserPrimaryRole(mrguser2.getId());
						    			userDTO.setUserType(primaryRole.getUserType());
						    		}
						    		
						    		mrguserDTOList.add(userDTO);
					    		}else {
					    			mrguserDTOList.remove(mrguser2);
									continue;
					    		}
					    		
					    	}
					    }
					    projectsDTO.setExpensesApproversListwithoutApprover1(mrguserDTOList);
			    }
			    
			    projectsDTO.setIsProjectForExpenses(projectClients.getProjectId().getIsProjectForExpenses());
			    projectsDTO.setIsProjectForTimeSheets(projectClients.getProjectId().getIsProjectForTimeSheets());
			    
		 }
		 
		 return projectsDTO;
	}
			
			
			
			
			
	 @PreAuthorize("hasAnyRole('ROLE_VIEW_EDIT_PROJECT','ROLE_VIEW_PROJECT')") 
	 @RequestMapping(value = "/getProjectById", method = RequestMethod.POST)
	 public @ResponseBody ProjectsDTO getProjectById(@RequestParam(value="ProjectId", required=false) Long projectId,
			 @RequestParam(value="projectClientUUID", required=false) String projectClientUUID,HttpServletRequest request) throws Exception{ 
		   String loginOrgLocation = (String) SourceLeadSessionManager.getSessionAttribute(request, "locationCode");
		   User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		   OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		   String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		   ProjectsDTO ProjectDetails = projectsService.getProjectDetailsByProjectIdAndLoginOrgId(projectId, user, loginOrgLocation,isGlobalAccess);
		   
		   if(StringUtils.isNotBlank(projectClientUUID)) {
			   ProjectClients projectClients = projectClientService.getProjectClientsByUUID(projectClientUUID);
				 if(projectClients.getOrgRelationId() != null){
		    		 
					 if(StringUtils.isNotBlank(isGlobalAccess) && isGlobalAccess.equalsIgnoreCase(Constants.YES)) {
						 if(null != projectClients.getOrgRelationId().getLocationId() && projectClients.getOrgRelationId().getLocationId().getOrg().getId().longValue() == loginOrgLocations.getOrg().getId().longValue()){
							 ProjectDetails.setClient(projectClients.getOrgRelationId().getResponderLocationId().getOrgLocationName());
							}if(null != projectClients.getOrgRelationId().getResponderLocationId() && projectClients.getOrgRelationId().getResponderLocationId().getOrg().getId().longValue() == loginOrgLocations.getOrg().getId().longValue()){
								ProjectDetails.setClient(projectClients.getOrgRelationId().getLocationId().getOrgLocationName());
							}
					 }else {
						 if(null != projectClients.getOrgRelationId().getLocationId() && projectClients.getOrgRelationId().getLocationId().getId().longValue() == loginOrgLocations.getId().longValue()){
							 ProjectDetails.setClient(projectClients.getOrgRelationId().getResponderLocationId().getOrgLocationName());
							}if(null != projectClients.getOrgRelationId().getResponderLocationId() && projectClients.getOrgRelationId().getResponderLocationId().getId().longValue() == loginOrgLocations.getId().longValue()){
								ProjectDetails.setClient(projectClients.getOrgRelationId().getLocationId().getOrgLocationName());
							}
					 }
					
					 ProjectDetails.setOrgRelationUUID(projectClients.getOrgRelationId().getUUID());
		 	  }
				 Integer projectMembersCount  = projectMembersService.getProjectMembersCountByProjectClientId(projectClients.getId());
				 ProjectDetails.setProjectMembersCount(projectMembersCount.toString());
				 
				 
				/* String projectApprovalMechanismType = projectApproverHierarchyService.getApprovalMechanismTypeByProjectClientId(projectClients.getId());
				 ProjectDetails.setProjectApprovalType(projectApprovalMechanismType);
				 String expenseApprovalMechanismType = expenseProjectApproverHierarchyService.getExpenseApprovalMechanismTypeByProjectClientId(projectClients.getId());
				 ProjectDetails.setExpenseApprovalType(expenseApprovalMechanismType);*/
		   }
		 
		   List<ClientOrgRelationsDTO> clientsDTOList = new ArrayList<>();
		   SourceleadActions action = sourceleadActionsService.getSourceleadActionsByActionRole(Constants.ROLE_CREATE_PROJECT);
			 List<SourceleadSectionDTO> sections  = sourceleadFormSectionsService.getSourceleadFormSectionsByActionId(action.getId(),loginOrgLocations.getId());
			 List<CustomFormFieldDTO> formFieldId = new ArrayList<>();
			 if(sections != null && !sections.isEmpty()) {
				 for(SourceleadSectionDTO sec:sections) {
					 for(OrglocationFormFieldsDTO customFields:sec.getCustomFieldsList()) {
						 CustomFormFieldDTO custom = new CustomFormFieldDTO();
						 custom.setCustomFormFieldId(customFields.getId());
						 formFieldId.add(custom);
					 }
				 }
				 if(formFieldId != null && !formFieldId.isEmpty() && projectId != null) {
				 Map<Long, String[]> projectData = projectCustomFieldDataService.getProjectCustomFieldDataByProjectId(loginOrgLocations.getOrg().getId(),formFieldId, projectId);
				 Map<Long,String> dataMap = new HashMap<>();
				 
				 if(projectData!= null && !projectData.isEmpty()) {
					 for(Entry<Long, String[]> projectCustomData:projectData.entrySet()) {
						String labelName = projectCustomData.getValue()[0];
						 String labelData = projectCustomData.getValue()[1];
						 Long id = projectCustomData.getKey();
						 StringBuilder keyValue =  new StringBuilder();
						 keyValue.append(labelName).append("$").append(labelData);
						 dataMap.put(id, keyValue.toString());
						 ProjectDetails.setProjectCustomFieldlabelNameAndValue(dataMap);
					 	}
				 	}
				 }
			 }
			 
			 List<OrgRelations> clientsList = orgRelationsService.getAllClientsByOrgLocationId(loginOrgLocations.getId());
				if(clientsList != null & clientsList.size() > 0) {
					for(OrgRelations orgRelations : clientsList) {
						OrgLocations clientOrgLoc = null;
						if(orgRelations.getLocationId().getId().equals(loginOrgLocations.getId())) {
							clientOrgLoc = orgRelations.getResponderLocationId();
						}else {
							clientOrgLoc = orgRelations.getLocationId();
						}
						
						if(StringUtils.isNotBlank(clientOrgLoc.getOrg().getOrgType()) && clientOrgLoc.getOrg().getOrgType().equalsIgnoreCase("Guest")) {
							ClientOrgRelationsDTO clientDTO = new ClientOrgRelationsDTO();
							clientDTO.setOrgRelationId(orgRelations.getId());
							if(orgRelations.getLocationId().getId().equals(loginOrgLocations.getId())) {
								clientDTO.setOrgLocationId(orgRelations.getResponderLocationId().getId());
								clientDTO.setOrgName(orgRelations.getResponderLocationId().getOrgLocationName());
							}else {
								clientDTO.setOrgLocationId(orgRelations.getLocationId().getId());
								clientDTO.setOrgName(orgRelations.getLocationId().getOrgLocationName());
							}
							clientsDTOList.add(clientDTO);
						}
						
					}
				}
				
				List<ProjectApproverHierarchy> hierarchyList = projectApproverHierarchyService.getProjectsHierarchyListByProjectIdAndOrgLocationId(projectId,loginOrgLocations.getId());
				if(hierarchyList != null && !hierarchyList.isEmpty()){
					for(ProjectApproverHierarchy hierarchy : hierarchyList){
						if(hierarchy.getApproverUserId().getId().equals(user.getId())){
							ProjectDetails.setProjectApprover(Constants.YES);
							break;
						}else{
							ProjectDetails.setProjectApprover(Constants.NO);
						}
					}
				}
				
				ProjectDetails.setClientsList(clientsDTOList);
		   
		   return ProjectDetails;
	  }
	 
	 @PreAuthorize("hasRole('ROLE_CREATE_PROJECT')")
	 @RequestMapping(value = "/getProjectDetailsByProjectId", method = RequestMethod.POST)
	 public @ResponseBody Projects getProjectDetailsByProjectId(@RequestParam(value="projectId", required=false) Long projectId,HttpServletRequest request) throws Exception{ 
		   
		     OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		     Projects projects = projectsService.getProjectById(projectId);
		     if(StringUtils.isNotBlank(projects.getIsProjectShared()) && projects.getIsProjectShared().equalsIgnoreCase(Constants.YES)) {
		    	 projects.setSharedClientOrgLocationId(projects.getOrgLocationsId().getId());
		     }
		     
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
			    		 
			    		 Set<ExpenseProjectApproverHierarchy> expenseApproverHierarchiesList =  projectClients.getExpenseProjectApproverHierarchyList();
			    		 if(expenseApproverHierarchiesList != null){
			    			 Set<ExpenseProjectApproverHierarchy> expenseHirearchySet = new LinkedHashSet<ExpenseProjectApproverHierarchy>();
			    			 for(ExpenseProjectApproverHierarchy hierarcy : expenseApproverHierarchiesList){
			    				 expenseHirearchySet.add(hierarcy);
				    		 }
			    			 projects.setExpenseApproverHierarchyList(expenseHirearchySet);
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
		     String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		     ProjectsDTO projectDetails = projectsService.getProjectDetailsByProjectIdAndLoginOrgId(projectId, user, orgLocations.getLocationCode(),isGlobalAccess);
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
	 public Response<Projects> updateProjectByProjectId(ProjectsDTO projectsDTO,@RequestParam(value="ProjectId", required=false) Long projectId,HttpServletRequest request) throws Exception{ 
		 Response<Projects> response = new Response<>();
		 try {
			 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user"); 
			 if(user != null) {
				 if(Validator.validate(projectsDTO)) {
					 Projects projects = projectsService.getProjectDetailsUpdateByProjectId(projectId, user, projectsDTO);
					 response.setObject(projects);
					 response.setResponse(Result.SUCCESS.name());
					 response.setResponseMessage(Result.SUCCESS.getValue());
				 }else {
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				 }
			 }else {
					response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		  }catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
				e.printStackTrace();
			}
		 return response;
	 }
	 
	 
	 
	 @PreAuthorize("hasAnyRole('ROLE_VIEW_EDIT_PROJECT')")
	 @RequestMapping(value = "/updateClientProjectApprovers", method = RequestMethod.POST)
	 public String updateClientProjectApprovers(ProjectsDTO projectsDTO,@RequestParam(value="projectClientUUID", required=false) String projectClientUUID,HttpServletRequest request) throws Exception{ 
		 String resultJson;
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user"); 
		 Projects projects = projectsService.getClientProjectApproversUpdate(projectClientUUID, user, projectsDTO);
	
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
		 public @ResponseBody   List<ProjectTasks> getProjectTasks(@RequestParam(value="projectId", required=false) Long projectId,@RequestParam(value="taskType", required=false) String taskType,
				 @RequestParam(value="taskStatus", required=false) String taskStatus,HttpServletRequest request) throws Exception {
				List<ProjectTasks> taskList=tasksService.getAllTasksByProjectIdAndTaskTypeAndTaskStatus(projectId,taskType,taskStatus);
			 return taskList;
		 }
				 
		 
		 @RequestMapping(value = "/getAllProjectTasks", method = RequestMethod.POST)
		 public @ResponseBody   ResponseList<ProjectTasks> getAllProjectTasks(ProjectTasksDTO projectTasksDTO,HttpServletRequest request) throws Exception {
			 ResponseList<ProjectTasks> response =  new  ResponseList<ProjectTasks>();
			 try {
				 String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
				 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				 projectTasksDTO.setOrgLocationId(orgLocations.getId());
				 Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
				 if(userRole != null) {
					 String[] tableColumns = {"id","taskName", "taskType", "priority", "assignedTo","createdDate","status"};
					    int sortColumnIndex = 1; //by default sorting using updated date
						String sortColumnOrder = "asc"; //by default descending order
						if(projectTasksDTO.getOrder() != null && !projectTasksDTO.getOrder().isEmpty()) {
							sortColumnIndex = Integer.parseInt(projectTasksDTO.getOrder().get(0).get("column"));
							sortColumnOrder = projectTasksDTO.getOrder().get(0).get("dir");
						}
						projectTasksDTO.setCandidateId(candidateId);
						projectTasksDTO.setUserCode(user.getUserCode());
						projectTasksDTO.setSortColumn(tableColumns[sortColumnIndex]);
						projectTasksDTO.setSortOrder(sortColumnOrder);
						response = tasksService.getAllProjectTasksByProjectIdAndTaskTypeAndTaskStatus(projectTasksDTO,Long.parseLong(projectTasksDTO.getProjectId()),projectTasksDTO.getProjectTaskType(),projectTasksDTO.getProjectTaskStatus(),userRole,projectTasksDTO.getAssignedMembers());
						response.setResponse(Result.SUCCESS.name());
						response.setResponseMessage(Result.SUCCESS.getValue());
				 }else {
					 response.setResponse(Result.INVALID_DATA.name());
					 response.setResponseMessage(Result.INVALID_DATA.getValue());
				 }
			  }catch (Exception e) {
					response.setResponse(Result.AWKWARD.name());
					response.setResponseMessage(Result.AWKWARD.getValue());
					e.printStackTrace();
				}
				
			 return response;
		 }
	/**
	 * Get Employees list based on project id
	 * @param projectId
	 * @return JSON
	 * @throws Exception
	 */
	 @PreAuthorize("hasAnyRole('ROLE_VIEW_PROJECT_EMPLOYEES', 'ROLE_VIEW_EDIT_PROJECT_EMPLOYEES', 'ROLE_VIEW_DELETE_PROJECT_EMPLOYEES','ROLE_CREATE_PROJECT_TASKS')")
	 @RequestMapping(value = "/getProjectEmployeeListById", method = RequestMethod.GET)
	 public @ResponseBody List<ProjectMembers>  getProjectEmployeeListById(@RequestParam(value="status", required=false) String status,@RequestParam(value="projectId", required=false) Long projectId,
			 @RequestParam(value="roleIds", required=false) String roleIds,@RequestParam(value="teamIds", required=false) String teamIds, HttpServletRequest request) throws Exception{ 
		 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		 List<ProjectMembers> employeeList = projectsService.getProjectEmployeeListByProjectIdAndStatus(projectId,status,loginOrgLocation,roleIds,teamIds,isGlobalAccess);
		 return employeeList;
	  }
	 
	 
	 @RequestMapping(value = "/getProjectDocumentPermissions", method = RequestMethod.POST)
	 public ModelAndView  getProjectDocumentPermissions(@RequestParam(value="status", required=false) String status,@RequestParam(value="projectId", required=false) Long projectId,
			 @RequestParam(value="folderId", required=false) Long folderId,@RequestParam(value="documentId", required=false) Long documentId, HttpServletRequest request) throws Exception{ 
		 ModelAndView model = new ModelAndView();
		 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 ProjectClients projectClients = projectClientService.getProjectClientsByProjectIdAndOrgLocationId(projectId, loginOrgLocation.getId());
		 if(projectClients != null) {
			 LinkedHashMap<Long,String> memberRolesList = new LinkedHashMap<>();
			 List<ProjectMembers> projectMembersList = projectMembersService.getAllProjectMembersByProjectClientIdAndActiveStatusRecords(projectClients.getId());
			 if(projectMembersList != null && projectMembersList.size() > 0){ 
					for(ProjectMembers pm : projectMembersList){
						 StringBuilder membersListStringBuilder=new StringBuilder();
						 if(pm.getProjectMemberCandidteId().getLastName() != null) {
							 pm.setEmpName(membersListStringBuilder.append(pm.getProjectMemberCandidteId().getFirstName()).append(" ").append(pm.getProjectMemberCandidteId().getLastName()).toString());	 
						 }else {
							 pm.setEmpName(membersListStringBuilder.append(pm.getProjectMemberCandidteId().getFirstName()).toString());	 
						 }
						 
						 if(pm.getProjectMemberCandidteId().getConsultantUser() != null){
							 UserRole userRole = userRoleService.getUserRoleWithoutAdminsByUserIdAndLocationId(pm.getProjectMemberCandidteId().getConsultantUser().getId(), pm.getProjects().getOrgLocationsId().getId());
							 if(userRole != null){
								 memberRolesList.put(userRole.getRoles().getId(), userRole.getUserType());
							 } 
						 }else{
							 pm.setUserRoleType("Candidate");
						 }
					}
			 }
			 
			 
			 model.addObject("memberRolesList",memberRolesList);
			 model.addObject("projectMembersList",projectMembersList);
		 }
		 
		 model.addObject("folderId",folderId);
		 if(documentId != null) {
			 model.addObject("documentId",documentId);
		 }else {
			 documentId = null;
		 }
		 List<ProjectDocumentPermission> readList = projectDocumentPermissionService.projectDocumentPermissionListByFolderIdAndAccessStatus(folderId, documentId, "0");
		 model.addObject("readList",readList);
		 
		 List<ProjectDocumentPermission> writeList = projectDocumentPermissionService.projectDocumentPermissionListByFolderIdAndAccessStatus(folderId,documentId, "1");
		 model.addObject("writeList",writeList);
		 
		 model.setViewName("project/projectPermissions");
		 return model;
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
					ArrayList<QueryBuilder> QueryBuilderList =getQueryBuilderList(keywd);
					if(QueryBuilderList != null && !QueryBuilderList.isEmpty()){
						for(QueryBuilder QueryBuilder :QueryBuilderList){
							excludeBoolSubQueryBuilder.should(QueryBuilder);
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
						ArrayList<QueryBuilder> QueryBuilderList =getQueryBuilderList(keywd);
						if(QueryBuilderList != null && !QueryBuilderList.isEmpty()){
							for(QueryBuilder QueryBuilder :QueryBuilderList){
								anyBoolSubQueryBuilder.should(QueryBuilder);
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
						ArrayList<QueryBuilder> QueryBuilderList =getQueryBuilderList(keywd);
						if(QueryBuilderList != null && !QueryBuilderList.isEmpty()){
							for(QueryBuilder QueryBuilder :QueryBuilderList){
								allBoolSubQueryBuilder.should(QueryBuilder);
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

	 private ArrayList<QueryBuilder> getQueryBuilderList(String keywd){
		    ArrayList<QueryBuilder> QueryBuilderList = new ArrayList<QueryBuilder>();
		   
		    //Project Search
		    //QueryBuilderList.add(QueryBuilders.matchQuery("id", keywd));
		    QueryBuilderList.add(QueryBuilders.matchQuery("projectSequeneNumber", keywd));
		    QueryBuilderList.add(QueryBuilders.matchQuery("projectName", keywd));
			QueryBuilderList.add(QueryBuilders.matchQuery("level1ManagerName", keywd));
			QueryBuilderList.add(QueryBuilders.matchQuery("level2ManagerName", keywd));
			//QueryBuilderList.add(QueryBuilders.matchPhraseQuery("startDate", keywd));
			QueryBuilderList.add(QueryBuilders.matchPhraseQuery("invoiceFrequency", keywd));
			QueryBuilderList.add(QueryBuilders.matchPhraseQuery("clientName", keywd));
			QueryBuilderList.add(QueryBuilders.matchPhraseQuery("projectStatus", keywd));
			QueryBuilderList.add(QueryBuilders.matchQuery("projectMembers", keywd));
			QueryBuilderList.add(QueryBuilders.matchPhraseQuery("projectMemberEmail", keywd));
			return QueryBuilderList; 
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
		public List<ProjectDocuments> getProjectDocumentsList(@RequestParam(value = "projectId", required = false) Long projectId) throws Exception
		{
			List<ProjectDocuments> projectDocumentList = projectDocumentsService.getProjectDocumentsListByProjectIdId(projectId);
			return projectDocumentList;
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
	public @ResponseBody String saveProjectDocument(ProjectsDocumentsDTO projectsDocumentsDTO) throws Exception
	{
	 projectsDocumentsDTO.setStaticPath(staticPath);
		if (null != projectsDocumentsDTO.getProjectId()) {
			projectDocumentsService.storeProjectDocuments(projectsDocumentsDTO);
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
 
 
 /**
  * @author Laxman
  * @param projectsDocumentFolderDTO
  * @return success
  * @throws Exception
  */
 
	 @RequestMapping(value = "/saveProjectDocumentFolder", method = RequestMethod.POST)
	public @ResponseBody String saveProjectDocumentFolder(ProjectsDocumentFolderDTO projectsDocumentFolderDTO) throws Exception
	{
	       projectDocumentsFolderService.saveProjectDocumentFolder(projectsDocumentFolderDTO);
			return "success";
	}
 
 
 /**
  * @author Laxman
  * @param projectId
  * @return projectDocumentFolderList
  * @throws Exception
  */
   @RequestMapping(value = "/getProjectDocumentsFolderList",method = RequestMethod.POST)
	public List<ProjectDocumentFolder> getProjectDocumentsFolderList(@RequestParam(value = "projectId", required = false) Long projectId,@RequestParam(value = "userId", required = false) Long userId,HttpServletRequest request) throws Exception
	{
	   OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(userId,loginOrgLocation.getId());
	    Long candidateId =  (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
		List<ProjectDocumentFolder> projectDocumentFolderList = projectDocumentsFolderService.getProjectDocumentFolderByProjectId(projectId,userRole.getRoles().getId(),userRole.getRoles().getRoleName(),candidateId);
		return projectDocumentFolderList;
	}
   
   /**
    * @author Laxman
    * @param projectId
    * @param folderId
    * @return projectDocumentsList
    * @throws Exception
    */
   @RequestMapping(value = "/getEachProjectDocumentsFolderList",method = RequestMethod.POST)
	public List<ProjectDocuments> getEachProjectDocumentsFolderList(@RequestParam(value = "projectId", required = false) Long projectId,@RequestParam(value = "folderId", required = false) Long folderId,@RequestParam(value = "userId", required = false) Long userId) throws Exception
	{
	   
	   OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	   UserRole userRoel = userRoleService.getUserRoleByUserIdAndLocationId(userId,loginOrgLocation.getId());
	   Long candidateId =  (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
		List<ProjectDocuments> projectDocumentsList = projectDocumentsService.getEachProjectDocumentsListByProjectIdIdAndFolderId(projectId, userRoel.getRoles().getId(),userRoel.getRoles().getRoleName(),folderId,candidateId);
		return projectDocumentsList;
	}
   
   /**
    * @author Laxman
    * @param documentIds
    * @return projectDocumentsList
    * @throws Exception
    */
   
   @RequestMapping(value = "/deleteDocuments",method = RequestMethod.POST)
	public List<ProjectDocuments> deleteDocuments(@RequestParam(value = "documentIds", required = false) String documentIds) throws Exception
	{
		List<ProjectDocuments> projectDocumentsList = projectDocumentsService.getDeleteProjectDocumentsListByDocumentId(documentIds);
		return projectDocumentsList;
	}
   
   /**
    * @author Laxman
    * @param documentId
    * @return
    * @throws Exception
    */
   @RequestMapping(value = "/eachDeleteDocuments",method = RequestMethod.POST)
	public void eachDeleteDocuments(@RequestParam(value = "documentId", required = false) Long documentId) throws Exception
	{
	   projectDocumentPermissionDao.deleteAllProjectDocumentByDocumentId(documentId);
	   genericDao.delteEachDocumentById(documentId);
	 
	}
   
   
   /**
    * @author Laxman
    * @param documentId
    * @param fileName
    * @return
    * @throws Exception
    */
   @RequestMapping(value = "/saveReNameProjectDocument", method = RequestMethod.POST)
	public @ResponseBody String saveReNameProjectDocument(@RequestParam(value = "documentId", required = false) Long documentId,@RequestParam(value = "fileName", required = false) String fileName) throws Exception
	{
	   User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	   ProjectDocuments pd = projectDocumentsService.getDocumentById(documentId);
	   if(pd != null){
			   pd.setFileName(fileName);
			   pd.setUpdatedBy(user);
			   pd.setUpdatedDate(commonUtil.currentDate());
			   projectDocumentsService.updateProjectDocuments(pd);
			   return "success";
	   }else {
			return "exception";
		}
    }
 
   @RequestMapping(value = "/projectDocumentFolderRename", method = RequestMethod.POST)
	public @ResponseBody String projectDocumentFolderRename(@RequestParam(value = "folderId", required = false) Long folderId,@RequestParam(value = "folderName", required = false) String folderName) throws Exception
	{
	   User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	   ProjectDocumentFolder pdf = projectDocumentsFolderService.getProjectDocumentFolderByDocumentId(folderId);
	     if(pdf != null){
		       pdf.setFoderName(folderName);
		       pdf.setUpdatedBy(user);
		       pdf.setUpdatedDate(commonUtil.currentDate());
		       projectDocumentsFolderService.saveProjectDocumentFolder(pdf);
			   return "success";
	   }else {
			return "exception";
		}
   }
   
   
   
   @RequestMapping(value = "/deleteProjectDcoumentFolder",method = RequestMethod.POST)
	public @ResponseBody String deleteProjectDcoumentFolder(@RequestParam(value = "folderId", required = false) Long folderId,@RequestParam(value = "projectId", required = false) Long projectId,@RequestParam(value = "userId", required = false) Long userId) throws Exception
	{
	   OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	   UserRole userRoel = userRoleService.getUserRoleByUserIdAndLocationId(userId,loginOrgLocation.getId());
	   Long candidateId =  (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
	   projectDocumentPermissionDao.deleteAllProjectDocumentByFoderId(folderId);
	   List<ProjectDocuments> projectDocumentsList = projectDocumentsService.getEachProjectDocumentsListByProjectIdIdAndFolderId(projectId,userRoel.getId(),userRoel.getRoles().getRoleName(), folderId,candidateId);
	   for(ProjectDocuments pd:projectDocumentsList) {
		     genericDao.delteEachDocumentById(pd.getId());
	   }
		projectDocumentsFolderService.deleteProjectFolderByFolderId(folderId);
		
		
		   
		return "success";
	}
   
   
   @RequestMapping(value = "/projectDocumentMailSending", method = RequestMethod.POST)
	public @ResponseBody String projectDocumentMailSending(ProjectsDocumentsDTO projectsDocumentsDTO) throws Exception
	{
	   User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	   OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	   if(StringUtils.isNotEmpty(projectsDocumentsDTO.getSendTo()) && user != null){
		   projectDocumentsService.sendProjectDocumentEmail(projectsDocumentsDTO, user, loginOrgLocation);
	   }
	   return "success";
   }
   
  
@RequestMapping(value = "/getAllDocumentDownload",method = RequestMethod.POST)
public @ResponseBody String getAllDocumentDownload(@RequestParam(value = "documentIds", required = false) String documentid,HttpServletResponse response) throws Exception
{
	 File file = null;
     if(!isNullOrEmpty(documentid)){
		String[] documentIds = documentid.split(",");
	
	String path =StringUtils.removeEnd(staticPath, "/slead-static");
	   file = File.createTempFile("ProjectDocuments",".zip");
	   FileOutputStream   fos = new FileOutputStream(file);
		ZipOutputStream zos = new ZipOutputStream(fos);
	for(int j=0; j< documentIds.length; j++) {
		ProjectDocuments projectDocuments =  projectDocumentsService.getDocumentById(Long.valueOf(documentIds[j]));
		try {
		      byte[] buffer = new byte[128];
		        File currentFile = new File(path+projectDocuments.getFileUrl());
		        if (!currentFile.isDirectory()) {
		          ZipEntry entry = new ZipEntry(currentFile.getName());
		          FileInputStream fis = new FileInputStream(currentFile);
		          zos.putNextEntry(entry);
		          int read = 0;
		          while ((read = fis.read(buffer)) != -1) {
		            zos.write(buffer, 0, read);
		          }
		          zos.closeEntry();
		          fis.close();
		        }
		    } catch (FileNotFoundException e) {
		      System.out.println("File not found : " + e);
			    }
			  }

		  zos.close();
	      fos.close();
	}
   return file.getAbsolutePath();
	 
}

@RequestMapping(value = "/saveProjectNamePermissions", method = RequestMethod.POST)
public @ResponseBody String saveProjectNamePermissions(ProjectsDocumentFolderDTO projectsDocumentFolderDTO) throws Exception
{
	projectDocumentPermissionService.saveProjectDocumentPermissionWithDTO(projectsDocumentFolderDTO);
   return "success";
}

@RequestMapping(value = "/validateFolderName" , method = RequestMethod.GET)
 public @ResponseBody  Boolean validateFolderName(@RequestParam(value="folderName", required=false) String folderName,@RequestParam(value="folderId", required=false) Long folderId,
	 @RequestParam(value="projectId", required=false) Long projectId) throws Exception {
	
	   ProjectDocumentFolder pdf = null;
	   if(null != folderId){
		    pdf = projectDocumentsFolderService.validateProjectDocumentFolderByFolderIdAndFolderName(folderId,folderName);
	   }
	  Boolean flag = false;
	   if(null == pdf){
		   flag = projectDocumentsService.validateProjectDocumentFolderNameByProjectIdIdAndFolderName(projectId, folderName);
		   
	   }
	return flag;
   } 


 @RequestMapping(value = "/validateDocumentName" , method = RequestMethod.GET)
 public @ResponseBody  Boolean validateDocumentName(@RequestParam(value="documentName", required=false) String documentName, @RequestParam(value="projectId", required=false) Long projectId) throws Exception {
	  Boolean flag = projectDocumentsService.validateProjectDocumentNameByProjectIdIdAndDocumentName(projectId, documentName);
	return flag;
 } 
		    
	    
		    
		    
@RequestMapping(value = "/getExportTasks")
public void getExportUsersData(ProjectTasksDTO projectTasksDTO,HttpServletRequest request, HttpServletResponse response) throws Exception {
			 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 	 String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		 	 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 	 Workbook createExcelworkbook =  new XSSFWorkbook();
			 StringBuilder excelSB = new StringBuilder();
	 		 Sheet createExcelsheet = createExcelworkbook.createSheet(excelSB.append("project_Tasks ").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString());
	 		 projectTasksDTO.setUserCode(user.getUserCode());
	 		projectTasksDTO.setOrgLocationId(orgLocations.getId());
	 		 ResponseList<ProjectTasks>  taskList = tasksService.getAllProjectTasksByProjectIdAndTaskTypeAndTaskStatus(projectTasksDTO,Long.parseLong(projectTasksDTO.getProjectId()),projectTasksDTO.getProjectTaskType(),projectTasksDTO.getProjectTaskStatus(),userRole,projectTasksDTO.getAssignedMembers());
	 		 File file = null;
	 		 if(!"pdf".equalsIgnoreCase(projectTasksDTO.getExportType())){
	  			createExcelsheet = tasksService.getTaskExcelFile(createExcelsheet,createExcelworkbook,taskList,orgLocations);
	  			 StringBuilder templateSB = new StringBuilder();
		 		  file = File.createTempFile(templateSB.append("project_Tasks_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".xls");
		 		  file.setReadable(true);
		 		  
				 FileOutputStream fos = new FileOutputStream(file);
				 try {
					createExcelworkbook.write(fos);
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}	 	
	 		 }else {
	 			file = tasksService.getTaskPdfFile(taskList,orgLocations);
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
