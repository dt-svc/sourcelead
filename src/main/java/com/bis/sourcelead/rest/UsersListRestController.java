package com.bis.sourcelead.rest;

import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.couchbase.util.UserStatsCacheHelper;
import com.bis.sourcelead.dao.PayrollPayheadDao;
import com.bis.sourcelead.dto.AddUserDTO;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.ConnectionsDTO;
import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.dto.OrglocationFormFieldsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.SourceleadSectionDTO;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.dto.UserRoleDTO;
import com.bis.sourcelead.elk.index.ClientVendorContactsIndex;
import com.bis.sourcelead.elk.index.ProjectsIndex;
import com.bis.sourcelead.elk.index.UserIndex;
import com.bis.sourcelead.elk.index.helper.ClientVendorContactsIndexHelper;
import com.bis.sourcelead.elk.index.helper.ProjectsIndexHelper;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.AssociateGrades;
import com.bis.sourcelead.entity.AssociateSalaryInd;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgDocuments;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.OrglocationFormFields;
import com.bis.sourcelead.entity.ProbationHistory;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectClients;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectMembersHistory;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementApproverHierarchy;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.Shift;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.TeamMembers;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserCustomFieldData;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Primary;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.enums.TagEntites;
import com.bis.sourcelead.service.AssociateBalanceLeaveService;
import com.bis.sourcelead.service.AssociateGradesService;
import com.bis.sourcelead.service.AssociateSalaryService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.OrgDocumentsService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.OrglocationFormFieldsService;
import com.bis.sourcelead.service.ProbationHistoryService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectClientService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RequirementApproverHierarchyService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.ShiftService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.SourceleadFormSectionsService;
import com.bis.sourcelead.service.SourceleadTagsService;
import com.bis.sourcelead.service.StatesService;
import com.bis.sourcelead.service.TabsService;
import com.bis.sourcelead.service.TeamMembersService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.UserCustomFieldDataService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.timesheet.service.TimesheetSubmissionsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

@RestController
public class UsersListRestController {

	private static final Logger logger = LoggerFactory.getLogger(UsersListRestController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;
	
	@Autowired
	UserRoleService userRoleService;

	@Autowired
	EmployeesService employeesService;

	@Autowired
	UserService userService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private EmailScenarioService emailScenarioService;
	

	@Autowired
	ProjectsService projectsService;

	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	private EmailScenerioUtil emailScenerioUtil;

	@Autowired
	TimesheetSubmissionsService timesheetSubmissionsService;

	@Autowired
	private SourceleadPasswordEncoder sourceleadPasswordEncoder;

	@Autowired
	OrgSpocService orgSpocService;

	@Autowired
	RequirementService requirementService;

	@Autowired
	TeamService teamService;

	@Autowired
	RequirementApproverHierarchyService requirementApproverHierarchyService;

	@Autowired
	TeamMembersService teamMembersService;

	@Autowired
	ProjectApproverHierarchyService projectApproverHierarchyService;

	@Autowired
	ProjectMembersService projectMembersService;

	@Autowired
	ProjectMembersHistoryService projectMembersHistoryService;

	@Autowired
	RolesService rolesService;

	@Autowired
	TabsService tabsService;    

	@Autowired
	private OrgRelationsService orgRelationsService;
	
	@Autowired
	SourceleadELKBaseIndexer sourceleadELKBaseIndexer;
	
	@Autowired
	private CountriesService countriesService;
	
	@Autowired
	ClientVendorContactsIndexHelper clientVendorContactsIndexHelper;
	
	@Autowired
	OrgDocumentsService orgDocumentsService;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	private OrglocationFormFieldsService orglocationFormFieldsService;
	
	@Autowired
	private UserCustomFieldDataService userCustomFieldDataService;
	
	@Autowired
	private SourceleadFormSectionsService sourceleadFormSectionsService;
	
	@Autowired
	private SourceleadActionsService sourceleadActionsService;
	
	@Autowired
	ProjectClientService projectClientService;
	
	@Autowired
    ProjectsIndexHelper projectsIndexHelper;
	
	@Autowired
	AssociateGradesService associateGradesService;
	
	@Autowired
	private ShiftService shiftService;
	
	@Autowired
	AssociateBalanceLeaveService associateBalanceLeaveService;
	
	@Autowired
	private SourceleadTagsService sourceleadTagsService;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	StatesService statesService;
	
	@Autowired
	private ProbationHistoryService probationHistoryService;
	
	@Autowired
	AssociateSalaryService associateSalaryService;
	
	@Autowired
	PayrollPayheadDao payrollPayheadDao;
	
	@Autowired
	UserStatsCacheHelper userStatsCacheHelper;
	
	@Value("${isCouchbaseEnabled}")
	private String isCouchbaseEnabled;
	
	
	
	/**
	 * To get All the Users by filtering
	 * 
	 * @param request
	 * @param userRole
	 * @param searchByName
	 * @param userState
	 * @param loginUserRole
	 * @return
	 */


	/* this method is used to display the users list onload */
	@RequestMapping(value = "/getDynamicUserList", method = RequestMethod.POST)
	public @ResponseBody   ResponseList<UserRoleDTO> getDynamicUserList(UserDTO userDTO,UserRoleDTO userRoleDto,HttpServletRequest request) throws Exception {

		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		List<Long> orgRelationsList = new ArrayList<>();
		List<Long> projectAssignedUserIds = new ArrayList<>();
		List<Long> projectClientsList = new ArrayList<>();
		List<Long> managerUserIds = new ArrayList<>();
		List<Long> teamsList = new ArrayList<>();
		List<Long> departmentsList = new ArrayList<>();
		
		String[] tableColumns = {"firstName", "email", "mobilePhone", "displayName","","userEmploymentType"};
	    int sortColumnIndex = 5; //by default sorting using updated date
		String sortColumnOrder = "asc"; //by default descending order
		if(userRoleDto.getOrder() != null && !userRoleDto.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(userRoleDto.getOrder().get(0).get("column"));
			sortColumnOrder = userRoleDto.getOrder().get(0).get("dir");
		}
		userRoleDto.setSortColumn(tableColumns[sortColumnIndex]);
		userRoleDto.setSortOrder(sortColumnOrder);
		
		ResponseList<UserRoleDTO> response = new ResponseList<UserRoleDTO>();
		List<UserRoleDTO> userRolesList = new ArrayList<>();
		
		
		if(userRoleDto.getOrgClients() != null && !userRoleDto.getOrgClients().isEmpty()) {
			String[] orgClients = userRoleDto.getOrgClients().split(",");
			for(int i=0; i < orgClients.length; i++) {
				orgRelationsList.add(Long.parseLong(orgClients[i]));
			}
		}
		
		if(userRoleDto.getOrgManagers() != null && !userRoleDto.getOrgManagers().isEmpty()) {
			String[] orgManagers = userRoleDto.getOrgManagers().split(",");
			for(int i=0; i < orgManagers.length; i++) {
				managerUserIds.add(Long.parseLong(orgManagers[i]));
			}
		}
		
		if(userRoleDto.getOrgTeams() != null && !userRoleDto.getOrgTeams().isEmpty()) {
			String[] orgTeams = userRoleDto.getOrgTeams().split(",");
			for(int i=0; i < orgTeams.length; i++) {
				teamsList.add(Long.parseLong(orgTeams[i]));
			}
		}
		
		if(userRoleDto.getOrgDepartments() != null && !userRoleDto.getOrgDepartments().isEmpty()) {
			String[] orgDepts = userRoleDto.getOrgDepartments().split(",");
			for(int i=0; i < orgDepts.length; i++) {
				departmentsList.add(Long.parseLong(orgDepts[i]));
			}
		}
			
			
			    ResponseList<UserRole>  rolesList = userRoleService.getDynamicUsersListByRoleAndNameAndLocationId(userDTO, userRoleDto, orgLocations.getId(), user.getId(), userDTO.getLoginUserRole(), user.getUserCode(),orgRelationsList,managerUserIds,teamsList,departmentsList);
				for(UserRole role : rolesList.getData()){
					UserRoleDTO userRoleDTO = new UserRoleDTO();
					userRoleDTO.setUserRoleName(role.getRoles().getDisplayName());
					userRoleDTO.setIsMailSent(role.getIsMailSent().toString());
					userRoleDTO.setUserName(role.getUser().getFullName());
					userRoleDTO.setUserEmail(role.getUser().getEmail());
					userRoleDTO.setUserId(role.getUser().getId());
					userRoleDTO.setUserCode(role.getUser().getUserCode());
					userRoleDTO.setUserState(role.getUserState().toString());
					if(role.getUser() != null) {
						if(role.getUser().getFileUrl() != null) {
							userRoleDTO.setFileUrl(role.getUser().getFileUrl());
						}
					}
					userRoleDTO.setMobilePhone(role.getUser().getMobilePhone());
					if(role.getReportingManager() !=null){
						StringBuilder reportingManagerName = new StringBuilder();
						if(role.getReportingManager().getLastName() !=null && !"".equalsIgnoreCase(role.getReportingManager().getLastName())){
							reportingManagerName.append(role.getReportingManager().getFirstName()).append(" ").append(role.getReportingManager().getLastName());
						}else{
							reportingManagerName.append(role.getReportingManager().getFirstName());
						}
						userRoleDTO.setReportingManagerName(reportingManagerName.toString());
					}
					Employees emp = employeesService.getEmployeeByUserIdAndOrgLocationsId(role.getUser().getId(),orgLocations.getId());
					if(emp !=null){
						if(emp.getUserEmploymentType()!=null && !"".equalsIgnoreCase(emp.getUserEmploymentType())){
							userRoleDTO.setEmployeMentType(emp.getUserEmploymentType());
						}
					}
					userRolesList.add(userRoleDTO);
				}
				response.setRecordsFiltered(rolesList.getRecordsFiltered());
				response.setRecordsTotal(rolesList.getRecordsTotal());
				response.setData(userRolesList);
		 return response;

	}
	
	/* this method is used to display the users list onload */
	@RequestMapping(value = "/getUsersList")
	@JsonInclude(value = JsonInclude.Include.NON_NULL)
	@ResponseBody
	public String getUsersList(HttpServletRequest request,
			@RequestParam(value = "userRole", required = false) String userRole,
			@RequestParam(value = "searchByName", required = false) String searchByName,
			@RequestParam(value = "userState", required = false) String userState,
			@RequestParam(value = "loginUserRole", required = false) String loginUserRole,
			@RequestParam(value = "offset", required = false) Integer offset,
			@RequestParam(value = "viewType", required = false) String viewType) throws Exception {

		String resultJson = null;
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		ArrayList<UserRole> userRolesList = new ArrayList<UserRole>();
//		List<UserRole> rolesList = new ArrayList<UserRole>();
		
			Long userRoleType ;
			
			if(!"".equalsIgnoreCase(userRole)){
				 userRoleType = Long.parseLong(userRole);
			}else{
				 userRoleType = null;
			}
			
			
			List<UserRole> rolesList  = userRoleService.getUsersListByRoleAndNameAndLocationId(userRoleType, searchByName, orgLocations.getId(),userState,user.getId(),loginUserRole,user.getUserCode(),offset,viewType);
	
			if(null != rolesList && rolesList.size() >0){
				for(UserRole role : rolesList){
					StringBuilder userName = new StringBuilder();
					role.setUserRoleName(role.getRoles().getDisplayName());
					role.setIsMailSent(role.getIsMailSent());
					if(!isNullOrEmpty(role.getUser().getLastName())){
						userName = userName.append(role.getUser().getFirstName()).append(" ").append(role.getUser().getLastName());
						role.setUserName(userName.toString());
						
					}else{
						role.setUserName(role.getUser().getFirstName());
					}
					role.setUserEmail(role.getUser().getEmail());
					role.setUserId(role.getUser().getId());
					role.setUserCode(role.getUser().getUserCode());
					//role.setIsMailSent(role.getUser().getIsMailSent().toString());	
					if(role.getUser() != null) {
						if(role.getUser().getFileUrl() != null) {
							role.setFileUrl(role.getUser().getFileUrl());
						}
					}
					role.setMobilePhone(role.getUser().getMobilePhone());
					if(role.getReportingManager() !=null){
						StringBuilder reportingManagerName = new StringBuilder();
						if(role.getReportingManager().getLastName() !=null && !"".equalsIgnoreCase(role.getReportingManager().getLastName())){
							reportingManagerName.append(role.getReportingManager().getFirstName()).append(" ").append(role.getReportingManager().getLastName());
						}else{
							reportingManagerName.append(role.getReportingManager().getFirstName());
						}
						role.setReportingManagerName(reportingManagerName.toString());
					}
					Employees emp = employeesService.getEmployeeByUserIdAndOrgLocationsId(role.getUser().getId(),orgLocations.getId());
					if(emp !=null){
						if(emp.getUserEmploymentType()!=null && !"".equalsIgnoreCase(emp.getUserEmploymentType())){
							role.setEmployeMentType(emp.getUserEmploymentType());
						}
					}
					userRolesList.add(role);
				}
				 resultJson = toJSON(userRolesList);
			}	
		
		 return resultJson;

	}

	/*
	 * This method is used for landing page and advanced search for user tab.
	 * So, who has user tab access they can see users list. No preAuthorize for
	 * this method
	 */
	@RequestMapping(value = "/searchUser", method = RequestMethod.POST)
	public String searchUser(@RequestParam(value = "json", required = false) String json,
			@RequestParam(value = "offset", required = false) Integer offset,
			@RequestParam(value = "userRole", required = false) String userRole,
			@RequestParam(value = "searchByName", required = false) String searchByName,
			@RequestParam(value = "userState", required = false) String userState,
			@RequestParam(value = "loginUserRole", required = false) String loginUserRole,
			MultipartHttpServletRequest request) throws Exception {

		MultipartFile multiFile = null;
		Iterator<String> itrator = request.getFileNames();
		if (itrator.hasNext()) {
			multiFile = request.getFile(itrator.next());
		}
		String jsonc = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(jsonc);

		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");

		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

		boolQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", loginOrgLocation.getId()));
		boolQueryBuilder.mustNot(QueryBuilders.matchQuery("userId", user.getId()));

		Integer maxResults = Constants.MAX_RESULTS_TEN;

		if (null != userState && !"".equalsIgnoreCase(userState)) {
			boolQueryBuilder.must(QueryBuilders.matchPhrasePrefixQuery("userStatus", userState));
		}
		if (null != userRole && !"".equalsIgnoreCase(userRole)) {
			boolQueryBuilder.must(QueryBuilders.matchPhrasePrefixQuery("userRoleId", userRole));
		}
		if (null != searchByName && !"".equalsIgnoreCase(searchByName)) {
			boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("email", searchByName));
			/*
			 * boolQueryBuilder.should(QueryBuilders.matchPhraseQuery(
			 * "lastName", searchByName));
			 * boolQueryBuilder.should(QueryBuilders.matchPhraseQuery(
			 * "firstName", searchByName));
			 */
		}

		// Exclude Keyword
		ArrayList<String> excludeKeyWordArray = getSearchKeywordsFromChosenSelectField(jsonObj, "excludeKeyWord");
		if (null != excludeKeyWordArray && !excludeKeyWordArray.isEmpty()) {

			BoolQueryBuilder excludeBoolQueryBuilder = QueryBuilders.boolQuery();
			for (String keywd : excludeKeyWordArray) {
				BoolQueryBuilder excludeBoolSubQueryBuilder = QueryBuilders.boolQuery();
				ArrayList<QueryBuilder> QueryBuilderList = getQueryBuilderList(keywd);

				if (null != QueryBuilderList && !QueryBuilderList.isEmpty()) {

					for (QueryBuilder QueryBuilder : QueryBuilderList) {
						excludeBoolSubQueryBuilder.should(QueryBuilder);
					}
					excludeBoolQueryBuilder.should(excludeBoolSubQueryBuilder);
				}
			}
			boolQueryBuilder.mustNot(excludeBoolQueryBuilder);
		}

		// Any Keyword
		ArrayList<String> anyKeyWordArray = getSearchKeywordsFromChosenSelectField(jsonObj, "anyOfKeyword");

		if (null != anyKeyWordArray && !anyKeyWordArray.isEmpty()) {

			BoolQueryBuilder anyBoolQueryBuilder = QueryBuilders.boolQuery();
			for (String keywd : anyKeyWordArray) {
				BoolQueryBuilder anyBoolSubQueryBuilder = QueryBuilders.boolQuery();
				ArrayList<QueryBuilder> QueryBuilderList = getQueryBuilderList(keywd);

				if (null != QueryBuilderList && !QueryBuilderList.isEmpty()) {
					for (QueryBuilder QueryBuilder : QueryBuilderList) {
						anyBoolSubQueryBuilder.should(QueryBuilder);
					}
					anyBoolQueryBuilder.should(anyBoolSubQueryBuilder);
				}
			}
			boolQueryBuilder.must(anyBoolQueryBuilder);
		}
		// All Keywords
		ArrayList<String> allKeyWordArray = getSearchKeywordsFromChosenSelectField(jsonObj, "allKeywords");

		if (null != allKeyWordArray && !allKeyWordArray.isEmpty()) {

			BoolQueryBuilder allBoolQueryBuilder = QueryBuilders.boolQuery();
			for (String keywd : allKeyWordArray) {
				BoolQueryBuilder allBoolSubQueryBuilder = QueryBuilders.boolQuery();
				ArrayList<QueryBuilder> QueryBuilderList = getQueryBuilderList(keywd);

				if (null != QueryBuilderList && !QueryBuilderList.isEmpty()) {
					for (QueryBuilder QueryBuilder : QueryBuilderList) {
						allBoolSubQueryBuilder.should(QueryBuilder);
					}
					allBoolQueryBuilder.must(allBoolSubQueryBuilder);
				}
			}
			boolQueryBuilder.must(allBoolQueryBuilder);
		}

		// Email
		ArrayList<String> emailArray = getSearchKeywordsFromChosenSelectField(jsonObj, "email");

		if (null != emailArray && !emailArray.isEmpty()) {
			BoolQueryBuilder emailBoolQueryBuilder = QueryBuilders.boolQuery();
			for (String keywd : emailArray) {
				emailBoolQueryBuilder.should(QueryBuilders.matchPhrasePrefixQuery("email", "*" + keywd + "*"));
			}
			boolQueryBuilder.must(emailBoolQueryBuilder);
		}

		// Mobile Phone
		ArrayList<String> mobilePhoneArray = getSearchKeywordsFromChosenSelectField(jsonObj, "mobilePhone");

		if (null != mobilePhoneArray && !mobilePhoneArray.isEmpty()) {
			BoolQueryBuilder mobilePhoneBoolQueryBuilder = QueryBuilders.boolQuery();
			for (String keywd : mobilePhoneArray) {
				mobilePhoneBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("mobilePhone", keywd));
			}
			boolQueryBuilder.must(mobilePhoneBoolQueryBuilder);
		}

		// Role
		ArrayList<String> userTypeArray = getSearchKeywordsFromChosenSelectField(jsonObj, "userType");

		if (null != userTypeArray && !userTypeArray.isEmpty()) {
			BoolQueryBuilder userTypeBoolQueryBuilder = QueryBuilders.boolQuery();
			for (String keywd : userTypeArray) {
				userTypeBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("userType", keywd));
			}
			boolQueryBuilder.must(userTypeBoolQueryBuilder);
		}

		// Employee Id
		ArrayList<String> employeeIdArray = getSearchKeywordsFromChosenSelectField(jsonObj, "employeeId");

		if (null != employeeIdArray && !employeeIdArray.isEmpty()) {
			BoolQueryBuilder employeeIdBoolQueryBuilder = QueryBuilders.boolQuery();
			for (String keywd : employeeIdArray) {
				employeeIdBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("employeeId", keywd));
			}
			boolQueryBuilder.must(employeeIdBoolQueryBuilder);
		}

		// User State
		ArrayList<String> userStateArray = getSearchKeywordsFromChosenSelectField(jsonObj, "userStatus");

		if (null != userStateArray && !userStateArray.isEmpty()) {
			BoolQueryBuilder userStateBoolQueryBuilder = QueryBuilders.boolQuery();
			for (String keywd : userStateArray) {
				userStateBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("userStatus", keywd));
			}
			boolQueryBuilder.must(userStateBoolQueryBuilder);
		}

		// Rate
		if (jsonObj.has("rate") && StringUtils.isNotEmpty(jsonObj.getString("rate"))) {

			boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("rate", jsonObj.getString("rate")));
		}

		// Rate Per
		if (jsonObj.has("ratePer") && StringUtils.isNotEmpty(jsonObj.getString("ratePer"))) {
			//boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("ratePer", jsonObj.getString("ratePer")));
			if(jsonObj.getString("ratePer").equalsIgnoreCase("Weekly")){
				boolQueryBuilder.must(QueryBuilders.matchPhrasePrefixQuery("ratePer", "Weekly"));
				boolQueryBuilder.mustNot(QueryBuilders.matchPhrasePrefixQuery("ratePer", "Bi-Weekly"));
			}else if(jsonObj.getString("ratePer").equalsIgnoreCase("Monthly")){
				boolQueryBuilder.must(QueryBuilders.matchPhrasePrefixQuery("ratePer", "Monthly"));
				boolQueryBuilder.mustNot(QueryBuilders.matchPhrasePrefixQuery("ratePer", "Semi-Monthly"));
			}else{
				boolQueryBuilder.must(QueryBuilders.matchPhrasePrefixQuery("ratePer", jsonObj.getString("ratePer")));
			}
			
		}

		List<UserIndex> usersList = new ArrayList<UserIndex>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("GMT"));

		SearchHit[] searchHits = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_USER_INDEX,
				Constants.ELK_USER_TYPE, offset, maxResults.intValue(), boolQueryBuilder).getHits();
		if (null != searchHits && searchHits.length > 0) {
			ObjectMapper mapper = new ObjectMapper();
			for (SearchHit hit : searchHits) {
				StringBuilder json1 = new StringBuilder(hit.getSourceAsString());
				mapper.setDateFormat(df);
				UserIndex userIndex = mapper.readValue(json1.toString(), UserIndex.class);
				usersList.add(userIndex);
			}
		}
		String resultJson;
		resultJson = commonUtil.toJSON(usersList);
		return resultJson;

	}

	private ArrayList<String> getSearchKeywordsFromChosenSelectField(JSONObject jsonObj, String param)
			throws Exception {
		ArrayList<String> keyWordList = null;
		if (jsonObj.has(param)) {
			String searchKeywordString = null;
			try {
				JSONArray jsonArray = jsonObj.getJSONArray(param);
				searchKeywordString = jsonArray.toString();
			} catch (JSONException e) {
				searchKeywordString = jsonObj.getString(param);
			}

			searchKeywordString = searchKeywordString.replaceAll("\\[", "").replaceAll("\\]", "");
			keyWordList = new ArrayList<String>(Arrays.asList(searchKeywordString.split(",")));
		} else {
			keyWordList = new ArrayList<String>();
		}

		return keyWordList;
	}

	private ArrayList<QueryBuilder> getQueryBuilderList(String keywd) {
		ArrayList<QueryBuilder> QueryBuilderList = new ArrayList<QueryBuilder>();
		QueryBuilderList.add(QueryBuilders.matchQuery("firstName", keywd));
		QueryBuilderList.add(QueryBuilders.matchQuery("lastName", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("email", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("userType", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("ratePer", keywd));
		QueryBuilderList.add(QueryBuilders.matchQuery("employeeId", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("userStatus", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("rate", keywd));
		QueryBuilderList.add(QueryBuilders.matchQuery("mobilePhone", keywd));


		Matcher matcher = Pattern.compile("\\d+").matcher(keywd);

		if (matcher.find()) {
			Float expYears = Float.valueOf(matcher.group());
			QueryBuilderList.add(QueryBuilders.matchPhraseQuery("rate", expYears));
			// QueryBuilderList.add(QueryBuilders.matchPhraseQuery("mobilePhone",
			// keywd));
			String mobileKeywd = matcher.group().replaceAll("[+91]", "");
			QueryBuilderList.add(QueryBuilders.matchQuery("mobilePhone", mobileKeywd));
		}

		return QueryBuilderList;
	}

	/**
	 * Forward to user controller by using userId as request param
	 * 
	 * @param userId
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/userSummary/{userCode}", method = RequestMethod.GET)
	public @ResponseBody void userSummary(@PathVariable String userCode, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");

		StringBuilder redirectUrl = new StringBuilder();
	    Set<String> tagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationId(loginOrgLocation.getId());
	    request.getSession().setAttribute("tagsList", tagsList);
	    User user = userService.getUserByUserCode(userCode);
		Set<String> associateTagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationIdAndEntityName(loginOrgLocation.getId(), TagEntites.ASSOCIATES.getValue(), user.getId());
		if(associateTagsList != null && !associateTagsList.isEmpty()) {
			StringBuilder associateTagsStr = new StringBuilder();
			for(String tags : associateTagsList) {
				associateTagsStr = associateTagsStr.append(tags).append(",");
			}
			associateTagsStr.toString();
			request.getSession().setAttribute("associateTags", associateTagsStr.substring(0,associateTagsStr.length()-1));
		}
		response.sendRedirect(redirectUrl.append("/ats/userSummaryDetails?userCode=").append(userCode).toString());

	}

	/**
	 * Get Employee details by User ID
	 * 
	 * @param request
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@PreAuthorize("hasAnyRole('ROLE_VIEW_EDIT_ASSOCIATE','ROLE_VIEW_ASSOCIATE','ROLE_VIEW_DELETE_ASSOCIATE')")
	@RequestMapping(value = "/getUserDetailsById", method = RequestMethod.POST)
	public @ResponseBody UserRole getUserDetailsById(HttpServletRequest request,
			@RequestParam(value = "userId", required = false) Long userId,AddUserDTO userDto) throws Exception {

		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request,"org");

		UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(userId, orgLocations.getId());

		Employees employee = employeesService.getEmployeeByUserIdAndOrgLocationsId(userId, orgLocations.getId());
		//SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
		userRole.setUserType(userRole.getUserType());
		
		if (employee != null) {
			userRole.setEmployeeId(employee.getEmployeeId());
			userRole.setRate(employee.getRate());
			userRole.setRatePer(employee.getRatePer());
			userRole.setCurrentCurrency(employee.getCurrentCurrency());
			
			Boolean flag = userRoleService.getIsGlobalManager(org.getId(),userId);
			if(flag == true) {
				userRole.setIsExistedOrgGlobalUser(Constants.YES);
			}else {
				userRole.setIsExistedOrgGlobalUser(Constants.NO);
			}
			
			if (employee.getHireDate() != null) {
				userRole.setHireDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),employee.getHireDate()));
			} else {
				userRole.setHireDateStr("");
			}
			
			if (employee.getConfirmationDate() != null) {
				userRole.setConfirmationDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),employee.getConfirmationDate()));
			} else {
				userRole.setConfirmationDateStr("");
			}
			
			if (employee.getProbationPeriod() != null) {
				userRole.setProbationPeriod(employee.getProbationPeriod());
			} else {
				userRole.setProbationPeriod("");
			}
			
			if (employee.getProbationStatus() != null) {
				userRole.setProbationStatus(employee.getProbationStatus());
			} else {
				userRole.setProbationStatus("");
			}
			
			if(userRole != null) {
				userRole.setTerminateDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),userRole.getTerminateDate()));
			}
			userRole.setDailyHours(employee.getDailyHours());
			userRole.setUserEmploymentType(employee.getUserEmploymentType());
			userRole.setDesignationOtherType(employee.getDesignationOtherType());
			userRole.setSsn(userRole.getUser().getSsn());
			if(userRole.getShift() != null) {
				userRole.setShiftId(userRole.getShift().getId());
				userRole.setShiftName(userRole.getShift().getShiftName());
			}
			
			if(null != employee.getDepartmentId()){
				userRole.setDepartment(employee.getDepartmentId().getId().toString());
				userRole.setDepartmentName(employee.getDepartmentId().getDepartmentName());
			}
			
			if(null != employee.getOrgRelationId()){
				userRole.setVendorOrgRelationId(employee.getOrgRelationId().getId().toString());
				if(employee.getOrgRelationId().getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
					userRole.setVendorName(employee.getOrgRelationId().getLocationId().getOrgLocationName());	
				}
				if(employee.getOrgRelationId().getLocationId().getId().longValue() == orgLocations.getId().longValue()){
					userRole.setVendorName(employee.getOrgRelationId().getResponderLocationId().getOrgLocationName());	
				}
				
			}
			if(null != employee.getSpocId()){
				StringBuilder contactName = new StringBuilder();
				userRole.setVendorSpocId(employee.getSpocId().getId().toString());
				if(null != employee.getSpocId().getVendorUser().getLastName()){
					userRole.setVendorContactName(contactName.append(employee.getSpocId().getVendorUser().getFirstName()).append(" ").append(employee.getSpocId().getVendorUser().getLastName()).toString());	
				}else{
					userRole.setVendorContactName(contactName.append(employee.getSpocId().getVendorUser().getFirstName()).toString());	

				}
				
			}
			
		}
		
		List<AssociateGrades> gradesList = associateGradesService.getAssociateGradesListByOrgLocationId(orgLocations.getId());
		userRole.setGradesList(gradesList);
		
		List<User> reportingManagersList = new ArrayList<>();
		List<User> userList = userService.getReportingManagersByUserRolesAndOrgLocationId(userRole.getRoles().getId(), orgLocations.getId());
		List<User> globalUserList = userService.getGlobalReportingManagersUsersByOrgId(userRole.getRoles().getId(), org.getId()); 
		if(userList != null && !userList.isEmpty()) {
			reportingManagersList.addAll(userList);
		}
		if(globalUserList != null && !globalUserList.isEmpty()) {
			reportingManagersList.addAll(globalUserList);
		}
		for (User user : reportingManagersList) {
			    UserRole userRole1 = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocations.getId());
				if(null != userRole1){
					user.setUserType(userRole1.getUserType());
				}else {
					UserRole primaryRole = userRoleService.getLoginUserPrimaryRole(user.getId());
					user.setUserType(primaryRole.getUserType());
				}
				
		  }
		 userRole.setAllReportingManagers(reportingManagersList);
		 
		 
		 List<User> resourceManagersList = userService.getResourceManagersByOrgLocationIdAndTabId(orgLocations.getId(),Long.valueOf(Constants.TAB_USER));
		 for (User user : resourceManagersList) {
			    UserRole ur = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocations.getId());
			    user.setUserType(ur.getUserType());
		  }
		 userRole.setAllResourceManagers(resourceManagersList);
		 
		 //	old records currency changed code 
		 Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
		 
		 if(countries !=null) {
			 userRole.setCurrencyConversionWithCountry(countries.getCurrency());
		 }
		 

		 if (userRole.getUser().getDateOfBirth() != null) {
				userRole.setDateofBirthStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),userRole.getUser().getDateOfBirth()));
			} else {
				userRole.setDateofBirthStr("");
			}

		 
		 List<OrgLocations> locationList = orgLocationService.getAllRegisteredWorkLocationsByOrgId(org.getId());
		 userRole.setLocationList(locationList);
		 
		  Candidate candidate = candidateService.getCandidateByConsultantUserId(userId, orgLocations.getId());
		  if(candidate != null) {
			  if(candidate.getWorkLocation() != null) {
				  userRole.setWorkLocationId(candidate.getWorkLocation().getId());
				  userRole.setWorkLocation(candidate.getWorkLocation().getLocation());
				  userRole.setResidenceAddress(candidate.getResidenceAddress());
				  userRole.setResidenceCountry(candidate.getResidenceCountry());
				  userRole.setResidenceState(candidate.getResidenceState());
				  userRole.setResidenceCity(candidate.getResidenceCity());
				  userRole.setResidenceZipCode(candidate.getResidenceZipCode());
				  userRole.setResidenceLatitude(candidate.getResidenceLatitude());
				  userRole.setResidenceLongitude(candidate.getResidenceLongitude());
			  }
			  
			  if(candidate.getPanNumber() != null) {
				  userRole.setPanNumber(candidate.getPanNumber());
			  }else {
				  userRole.setPanNumber("");
			  }
			  if(candidate.getAadharNumber() != null) {
				  userRole.setAadharNumber(candidate.getAadharNumber());
			  }else {
				  userRole.setAadharNumber("");
			  }
		  }
		  
		 
		 SourceleadActions action = sourceleadActionsService.getSourceleadActionsByActionRole(Constants.ROLE_CREATE_ASSOCIATE);

		 List<SourceleadSectionDTO> sections  = sourceleadFormSectionsService.getSourceleadFormSectionsByActionId(action.getId(),orgLocations.getId());
		 List<CustomFormFieldDTO> formFieldId = new ArrayList<>();
		 if(sections != null && !sections.isEmpty()) {
			 for(SourceleadSectionDTO sec:sections) {
				 for(OrglocationFormFieldsDTO customFields:sec.getCustomFieldsList()) {
					 CustomFormFieldDTO custom = new CustomFormFieldDTO();
					 custom.setCustomFormFieldId(customFields.getId());
					 formFieldId.add(custom);
				 }
			 }
			 if(formFieldId != null && !formFieldId.isEmpty() && userId != null) {
			 Map<Long, String[]> userData = userCustomFieldDataService.getUserCustomFieldDataByUserId(org.getId(),formFieldId,userId);
			 Map<Long,String> dataMap = new HashMap<>();
			 if(userData!= null && !userData.isEmpty()) {
				 for(Entry<Long, String[]> userCustomData:userData.entrySet()) {
					String labelName = userCustomData.getValue()[0];
					 String labelData = userCustomData.getValue()[1];
					 Long id = userCustomData.getKey();
					 StringBuilder keyValue =  new StringBuilder();
					 keyValue.append(labelName).append("$").append(labelData);
					 dataMap.put(id, keyValue.toString());
					 userRole.setUserCustomFieldlabelNameAndValue(dataMap);
				 	}
			 	}
			 }
		 }
		//resultJson = toJSON(userRole);
		return userRole;
	}

	@PreAuthorize("hasAnyRole('ROLE_VIEW_EDIT_ASSOCIATE','ROLE_VIEW_ASSOCIATE','ROLE_VIEW_DELETE_ASSOCIATE')")
	@RequestMapping(value = "/getUserDetailsByIdForTermination", method = RequestMethod.POST)
	public @ResponseBody String getUserDetailsByIdForTermination(HttpServletRequest request,
			@RequestParam(value = "userId", required = false) Long userId) throws Exception {
		String resultJson = null;

		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");

		UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(userId, orgLocations.getId());

		List<User> userList = userService.getManagersByUserIdAndUserRolesAndOrgLocationId(userRole.getUser().getId(),
				userRole.getRoles().getId(), orgLocations.getId());
		for (User user : userList) {
			UserRole userRole1 = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocations.getId());
			if (userRole1 != null) {
				user.setUserType(userRole1.getUserType());
			}
		}

		userRole.setAllReportingManagers(userList);

		// To change reporting manager
		List<Long> userIds = userRoleService.getAllAssignedUsersIdsByReportingManagerId(userId, orgLocations.getId());
		userRole.setAssignedUsersCountToReportingManager(userIds.size());
		List<User> reportManagerUsersList = new ArrayList<User>();
		if (userIds.size() > 0) {
			userIds.add(userId);
			List<User> managerUsersList = userService.getTabAccessUsersByOrgLocationIdAndTabIdAndUsersIds(
					orgLocations.getId(), userIds, Long.valueOf(Constants.TAB_USER));
			for (User user : managerUsersList) {
				UserRole role = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocations.getId());
				if (role != null) {
					user.setUserType(role.getRoles().getDisplayName());
					reportManagerUsersList.add(user);
				}
			}
		}

		userRole.setReportingManagers(reportManagerUsersList);
		
		
		
	// To change resource manager
			List<Long> resourceMgrUserIds = userRoleService.getAllAssignedUsersIdsByResourceManagerId(userId, orgLocations.getId());
			userRole.setAssignedUsersCountToResourceManager(resourceMgrUserIds.size());
			List<User> resourceManagerUsersList = new ArrayList<User>();
			if (resourceMgrUserIds.size() > 0) {
				resourceMgrUserIds.add(userId);
				List<User> managerUsersList = userService.getTabAccessUsersByOrgLocationIdAndTabIdAndUsersIds(
						orgLocations.getId(), resourceMgrUserIds, Long.valueOf(Constants.TAB_USER));
				for (User user : managerUsersList) {
					UserRole role = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocations.getId());
					if (role != null) {
						user.setUserType(role.getRoles().getDisplayName());
						resourceManagerUsersList.add(user);
					}
				}
			}

			userRole.setResourceManagers(resourceManagerUsersList);

		// To change client owner and spoc
		List<Long> cleintSpocIds = orgSpocService.getAssignedAsClientOrVendorSpocCountByUserId(userId,
				orgLocations.getId(), "Client");
		userRole.setAssignedAsClientOwnerOrSpocCount(cleintSpocIds.size());
		List<User> clientTabUsersList = new ArrayList<User>();
		if (cleintSpocIds.size() > 0) {
			List<User> clientUsersList = userService.getTabAccessUsersByOrgLocationIdAndTabIdAndNotInUsersId(
					orgLocations.getId(), userId, Long.valueOf(Constants.TAB_CLIENT));
			for (User user : clientUsersList) {
				UserRole role = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocations.getId());
				if (role != null) {
					user.setUserType(role.getRoles().getDisplayName());
					clientTabUsersList.add(user);
				}
			}
		}
		userRole.setClientUsersList(clientTabUsersList);

		// To change vendor owner and spoc
		List<Long> vendorSpocIds = orgSpocService.getAssignedAsClientOrVendorSpocCountByUserId(userId,
				orgLocations.getId(), "Vendor");
		userRole.setAssignedAsVendorOwnerOrSpocCount(vendorSpocIds.size());
		List<User> vendorTabUsersList = new ArrayList<User>();
		;
		if (vendorSpocIds.size() > 0) {
			List<User> vendorUsersList = userService.getTabAccessUsersByOrgLocationIdAndTabIdAndNotInUsersId(
					orgLocations.getId(), userId, Long.valueOf(Constants.TAB_VENDOR));
			for (User user : vendorUsersList) {
				UserRole role = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocations.getId());
				if (role != null) {
					user.setUserType(role.getRoles().getDisplayName());
					vendorTabUsersList.add(user);
				}
			}
		}
		userRole.setVendorUsersList(vendorTabUsersList);

		// To change requirement Manager
		Integer requirementsCountAsManager = requirementService.getManagerAssignedRequirementsCount(userId,
				orgLocations.getId());
		userRole.setRequirementsCountAsManager(requirementsCountAsManager);
		List<User> requirementManagersList = new ArrayList<User>();
		if (requirementsCountAsManager > 0) {
			List<User> requirementManagersUsersList = userService
					.getTabAccessUsersByOrgLocationIdAndTabIdAndNotInUsersId(orgLocations.getId(), userId,
							Long.valueOf(Constants.TAB_REQUIREMENT));
			for (User user : requirementManagersUsersList) {
				UserRole role = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocations.getId());
				if (role != null) {
					user.setUserType(role.getRoles().getDisplayName());
					requirementManagersList.add(user);
				}
			}
		}
		userRole.setRequirementManagersList(requirementManagersList);

		Integer requirementsCountAsApprover = requirementService.getRequirementsCountAsApprover(userId,
				orgLocations.getId());
		userRole.setRequirementsCountAsApprover(requirementsCountAsApprover);
		List<User> requirementApproversList = new ArrayList<User>();
		if (requirementsCountAsApprover > 0) {
			List<User> requirementApproversUsersList = userService
					.getTabAccessUsersByOrgLocationIdAndTabIdAndNotInUsersId(orgLocations.getId(), userId,
							Long.valueOf(Constants.TAB_REQUIREMENT));
			for (User user : requirementApproversUsersList) {
				UserRole role = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocations.getId());
				if (role != null) {
					user.setUserType(role.getRoles().getDisplayName());
					requirementApproversList.add(user);
				}
			}
		}
		userRole.setRequirementApproversList(requirementApproversList);

		Integer teamsCountAsOwner = teamService.getTeamCountByTeamOwner(userId, orgLocations.getId());
		userRole.setTeamsCountAsOwner(teamsCountAsOwner);
		List<User> teamOwnersList = new ArrayList<User>();
		if (teamsCountAsOwner > 0) {
			List<User> teamUsersList = userService.getTabAccessUsersByOrgLocationIdAndTabIdAndNotInUsersId(
					orgLocations.getId(), userId, Long.valueOf(Constants.TAB_REQUIREMENT));
			for (User user : teamUsersList) {
				UserRole role = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocations.getId());
				if (role != null) {
					user.setUserType(role.getRoles().getDisplayName());
					teamOwnersList.add(user);
				}
			}
		}
		userRole.setTeamOwnersList(teamOwnersList);

		Integer projectApproverCount = projectsService.getProjectApproversCountByUserIdAndOrgLocationId(userId,
				orgLocations.getId());
		userRole.setProjectApproverCount(projectApproverCount);
		List<User> approversOwnersList = new ArrayList<User>();
		if (projectApproverCount > 0) {
			List<User> projectApproversList = userService.getTabAccessUsersByOrgLocationIdAndTabIdAndNotInUsersId(
					orgLocations.getId(), userId, Long.valueOf(Constants.TAB_TIMESHEET_APPROVAL));
			for (User user : projectApproversList) {
				UserRole role = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocations.getId());
				if (role != null) {
					user.setUserType(role.getRoles().getDisplayName());
					approversOwnersList.add(user);
				}
			}
		}
		userRole.setProjectApproversList(approversOwnersList);
		
		
		Integer clientDocumentsOwnerCount = orgDocumentsService.getDocumentOwnerCountByUserIdAndOrgLocationIdAndSourceType(userId, orgLocations.getId(), "Client");
		userRole.setClientDocumentsOwnerCount(clientDocumentsOwnerCount);
		List<User> clientDocumentsOwnerList = new ArrayList<User>();
		if (clientDocumentsOwnerCount > 0) {
			List<User> documentsOwnersList = userService.getTabAccessUsersByOrgLocationIdAndTabIdAndNotInUsersId(
					orgLocations.getId(), userId, Long.valueOf(Constants.TAB_CLIENT));
			for (User user : documentsOwnersList) {
				UserRole role = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocations.getId());
				if (role != null) {
					user.setUserType(role.getRoles().getDisplayName());
					clientDocumentsOwnerList.add(user);
				}
			}
		}
		userRole.setClientDocumentsOwnerList(clientDocumentsOwnerList);
		
		
		
		Integer vendorDocumentsOwnerCount = orgDocumentsService.getDocumentOwnerCountByUserIdAndOrgLocationIdAndSourceType(userId, orgLocations.getId(), "Vendor");
		userRole.setVendorDocumentsOwnerCount(vendorDocumentsOwnerCount);
		List<User> vendorDocumentsOwnerList = new ArrayList<User>();
		if (vendorDocumentsOwnerCount > 0) {
			List<User> documentsOwnersList = userService.getTabAccessUsersByOrgLocationIdAndTabIdAndNotInUsersId(
					orgLocations.getId(), userId, Long.valueOf(Constants.TAB_VENDOR));
			for (User user : documentsOwnersList) {
				UserRole role = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocations.getId());
				if (role != null) {
					user.setUserType(role.getRoles().getDisplayName());
					vendorDocumentsOwnerList.add(user);
				}
			}
		}
		userRole.setVendorDocumentsOwnerList(vendorDocumentsOwnerList);

		resultJson = toJSON(userRole);
		return resultJson;
	}
	
	@PreAuthorize("hasRole('ROLE_VIEW_EDIT_ASSOCIATE')")
	@RequestMapping(value = "/getRolesExpectSelectedRole", method = RequestMethod.POST)
	public @ResponseBody String getRolesExpectSelectedRole(
			@RequestParam(value = "roleId", required = false) Long roleId, HttpServletRequest request)
			throws Exception {
		String resultJson = null;
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");

		List<Roles> rolesList = rolesService.getRolesListExpcetCurrentUserRole(roleId, orgLocations.getId());

		resultJson = toJSON(rolesList);
		return resultJson;
	}
	
	@PreAuthorize("hasRole('ROLE_VIEW_EDIT_ASSOCIATE')")
	@RequestMapping(value = "/getUserPermissionByRole", method = RequestMethod.POST)
	public @ResponseBody String getUserPermissionByRole(HttpServletRequest request,
			@RequestParam(value = "userId", required = false) Long userId,
			@RequestParam(value = "userRoleId", required = false) Long userRoleId,
			@RequestParam(value = "changeRoleId", required = false) Long changeRoleId) throws Exception {
		String resultJson = null;

		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");

		List<Long> userRoletabIds = tabsService.getTabsIdsListByUserRoleIdAndOrgLocationId(userRoleId,
				orgLocations.getId());

		List<Long> changeRoletabIds = tabsService.getTabsIdsListByUserRoleIdAndOrgLocationId(changeRoleId,
				orgLocations.getId());

		userRoletabIds.removeAll(changeRoletabIds);

		UserRole userRole = userRoleService.getChangeRolePermissions(userRoletabIds, userId, orgLocations.getId());

		resultJson = toJSON(userRole);
		return resultJson;

	}

	/**
	 * To terminate user by using User ID.
	 * 
	 * @param userId
	 * @param reason
	 * @return
	 * @throws Exception
	 */
	
	@PreAuthorize("hasRole('ROLE_VIEW_DELETE_ASSOCIATE')")
	@RequestMapping(value = "/terminateUser", method = RequestMethod.POST)
	public @ResponseBody String terminateUser(AddUserDTO addUserDTO,@RequestParam(value = "userId", required = false) Long userId,
			 HttpServletRequest request) throws Exception {
		
		Long loginUserId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
		User user = userService.getUserById(loginUserId);
		
		Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		
		
		UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(userId, orgLocations.getId());

		/*json = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json);*/

		if (null != userRole) {

			if (StringUtils.isNotBlank(addUserDTO.getAssignReportingManagers())) {
				List<UserRole> userRolesList = userRoleService.getAllAssignedUsersByReportingManagerId(userId,
						orgLocations.getId());
				User reportingManager = new User();
				reportingManager.setId(Long.valueOf(addUserDTO.getAssignReportingManagers()));
				List<Long> userIds = userRoleService.getAllAssignedUsersIdsByReportingManagerId(userId, orgLocations.getId());
				
				Long count = (long) userIds.size();
					UserRole reportingManagerMail=null;
				if (null != userRolesList) {
					for (UserRole roles : userRolesList) {
						roles.setReportingManager(reportingManager);
						roles.setUpdatedBy(user.getUserCode());
						roles.setUpdatedDate(new Date());
						reportingManagerMail=userRoleService.addUserRole(roles);
					}
				assignedNewUserAsaReportingManagers(user,reportingManagerMail,userId,orgLocations,count);	
				}

			}
			
			if (StringUtils.isNotBlank(addUserDTO.getAssignResourceManagers())) {
				List<UserRole> userRolesList = userRoleService.getAllAssignedUsersByResourceManagerId(userId,
						orgLocations.getId());
				User resourceManager = new User();
				resourceManager.setId(Long.valueOf(addUserDTO.getAssignResourceManagers()));
				//List<Long> userIds = userRoleService.getAllAssignedUsersIdsByResourceManagerId(userId, orgLocations.getId());
				if (null != userRolesList) {
					for (UserRole roles : userRolesList) {
						roles.setResourceManager(resourceManager);
						roles.setUpdatedBy(user.getUserCode());
						roles.setUpdatedDate(new Date());
						userRoleService.addUserRole(roles);
					}
				}

			}
			
			
			if (StringUtils.isNotBlank(addUserDTO.getVendorUsers())) {
				User clientOwnerOrUser = new User();
				clientOwnerOrUser.setId(Long.valueOf(addUserDTO.getVendorUsers()));
				List<OrgSpoc> orgSpocList = orgSpocService.getAssignedAsClientOrVendorSpocByUserId(userId,
						orgLocations.getId(), "Client");
				if (null != orgSpocList) {
					for (OrgSpoc orgSpoc : orgSpocList) {
					
						boolean flag = orgSpocService.getOwnerUserExistedByOrgRelationIdAndOwnerUserId(orgSpoc.getOrgRelations().getId(), clientOwnerOrUser.getId());
						if(flag == false){
							if((orgSpoc.getOrgRelations().getLocationId().getId().longValue() == orgLocations.getId().longValue())&&(orgSpoc.getClientUser().getId()==orgSpoc.getOwnerUser().getId())){
								orgSpoc.setOwnerUser(clientOwnerOrUser);
								orgSpoc.setClientUser(clientOwnerOrUser);
							}else{
								orgSpoc.setClientUser(clientOwnerOrUser);
							}
						}else{
							orgSpoc.setStatus("0");
						}
						
						orgSpoc.setUpdatedBy(user.getUserCode());
						orgSpoc.setUpdatedDate(new Date());
						orgSpocService.addOrgSpoc(orgSpoc);
						ClientVendorContactsIndex clientVendorContactsIndex = clientVendorContactsIndexHelper.getClientVendorContactIndexFromSpoc(orgSpoc);
						sourceleadELKBaseIndexer.saveOrUpdateElkDocument(clientVendorContactsIndex, Constants.ELK_CLIENTVENDOR_CONTACT_INDEX, Constants.ELK_CLIENTVENDOR_CONTACT_TYPE,orgSpoc.getId().toString());
					}
				}

			}

			if (StringUtils.isNotBlank(addUserDTO.getClientUsers())) {
				User vendorOwnerOrUser = new User();
				vendorOwnerOrUser.setId(Long.valueOf(addUserDTO.getClientUsers()));
				List<OrgSpoc> orgSpocList = orgSpocService.getAssignedAsClientOrVendorSpocByUserId(userId,
						orgLocations.getId(), "Vendor");
				if (null != orgSpocList) {
					for (OrgSpoc orgSpoc : orgSpocList) {
						
						boolean flag = orgSpocService.getOwnerUserExistedByOrgRelationIdAndOwnerUserId(orgSpoc.getOrgRelations().getId(), vendorOwnerOrUser.getId());
						
						if(flag == false){
							if((orgSpoc.getOrgRelations().getLocationId().getId().longValue() == orgLocations.getId().longValue()) && (orgSpoc.getOwnerUser() != null && (orgSpoc.getVendorUser().getId() == orgSpoc.getOwnerUser().getId()))){
								orgSpoc.setOwnerUser(vendorOwnerOrUser);
								orgSpoc.setVendorUser(vendorOwnerOrUser);
							}else{
								orgSpoc.setVendorUser(vendorOwnerOrUser);
							}
						}else{
							orgSpoc.setStatus("0");
						}
						
						orgSpoc.setUpdatedBy(user.getUserCode());
						orgSpoc.setUpdatedDate(new Date());
						orgSpocService.addOrgSpoc(orgSpoc);
						ClientVendorContactsIndex clientVendorContactsIndex = clientVendorContactsIndexHelper.getClientVendorContactIndexFromSpoc(orgSpoc);
						sourceleadELKBaseIndexer.saveOrUpdateElkDocument(clientVendorContactsIndex, Constants.ELK_CLIENTVENDOR_CONTACT_INDEX, Constants.ELK_CLIENTVENDOR_CONTACT_TYPE,orgSpoc.getId().toString());
					}
				}

			}

			if (StringUtils.isNotBlank(addUserDTO.getManagerUsers())) {
				User requirementManagerUser = new User();
				requirementManagerUser.setId(Long.valueOf(addUserDTO.getManagerUsers()));
				List<Requirement> requirementsList = requirementService.getManagerAssignedRequirementsList(userId,
						orgLocations.getId());
				Integer requirementsCountAsManager = requirementService.getManagerAssignedRequirementsCount(userId,	orgLocations.getId());
				if (null != requirementsList) {
					Requirement savedRequirement = null;
					for (Requirement requirement : requirementsList) {
						requirement.setManagerUserId(requirementManagerUser);
						requirement.setUpdatedBy(user.getUserCode());
						requirement.setUpdatedDate(new Date());
						savedRequirement=requirementService.addRequirement(requirement);
					}
					
					assignedNewUserAsaManager(user,savedRequirement,userId,orgLocations,requirementsCountAsManager);
				}

			}

			if (StringUtils.isNotBlank(addUserDTO.getApproverUser())) {
				User requirementApproverUser = new User();
				requirementApproverUser.setId(Long.valueOf(addUserDTO.getApproverUser()));
				List<RequirementApproverHierarchy> approversList = requirementApproverHierarchyService
						.getRequirementsAsApprover(userId, orgLocations.getId());
				Integer requirementsCountAsApprover = requirementService.getRequirementsCountAsApprover(userId,	orgLocations.getId());
				RequirementApproverHierarchy updatedHierarchi=null;
				if (null != approversList) {
					for (RequirementApproverHierarchy hierarchy : approversList) {
						hierarchy.setApproverUserId(requirementApproverUser);
						hierarchy.setUpdatedBy(user.getUserCode());
						hierarchy.setUpdatedDate(new Date());
						updatedHierarchi=requirementApproverHierarchyService.addRequirementApproverHierarchy(hierarchy);
					}
					
					assignedNewUserAsaApprover(user,updatedHierarchi,userId,orgLocations,requirementsCountAsApprover);
				}

			}

			if (StringUtils.isNotBlank(addUserDTO.getTeamOwnerUser())) {
				User teamOwnerUser = new User();
				teamOwnerUser.setId(Long.valueOf(addUserDTO.getTeamOwnerUser()));
				List<Team> teamsList = teamService.getTeamListByTeamOwner(userId, orgLocations.getId());
				Integer teamsCountAsOwner = teamService.getTeamCountByTeamOwner(userId, orgLocations.getId());
				Team assignedTeamOwner=null;
				if (null != teamsList) {
					for (Team team : teamsList) {
						team.setOwnerUserId(teamOwnerUser);
						team.setUpdatedBy(user.getUserCode());
						team.setUpdatedDate(new Date());
						assignedTeamOwner=teamService.saveTeam(team);
					}
				}
				
				assignedNewUserAsateamOwnerUser(user,assignedTeamOwner,userId,orgLocations,teamsCountAsOwner);

			}

			List<TeamMembers> teamMembersList = teamMembersService.getTeamMembersListByUserIdAndLocationId(userId,
					orgLocations.getId());
			if (null != teamMembersList && !teamMembersList.isEmpty()) {
				for (TeamMembers teamMembers : teamMembersList) {
					teamMembers.setStatus("0");
					teamMembers.setUpdatedBy(user.getUserCode());
					teamMembers.setUpdatedDate(new Date());
					teamMembersService.saveOrUpdateTeamMembers(teamMembers);
				}
			}

			if (StringUtils.isNotBlank(addUserDTO.getProjectApproverUser())) {
				projectsService.tereminateAsProjectApprover(Long.valueOf(addUserDTO.getProjectApproverUser()), user, userId, orgLocations);
			} else {

				List<ProjectMembers> projectmembersList = projectMembersService
						.getAllProjectsAssigendToUserByIdAndStatus(userId, Constants.MEMBER_ACTIVE);
				if (null != projectmembersList) {
					for (ProjectMembers pm : projectmembersList) {
						   try {
							   List<ProjectMembers> pmList = projectMembersService.getAllProjectMembersByMasterCandidateId(pm.getMasterCandidateId().getId(), pm.getProjects().getId());
						  	 // ProjectMembers projectMembers =	projectMembersService.getProjectMemberById(projectMemberId);
						  	 if(pmList != null && !pmList.isEmpty()) {
							   for(ProjectMembers projectMembers: pmList) {
							  		  String status = Constants.MEMBER_IN_ACTIVE;
								  	  projectMembers.setTerminateReason(addUserDTO.getTerminateReason());
									  projectMembers.setUpdatedDate(new Date());
									  projectMembers.setUpdatedBy(user.getUserCode());
									  projectMembers.setStatus(status);
									  projectMembersService.saveProjectMember(projectMembers);
								  	  
									  List<ProjectMembersHistory> pmhList = projectMembersHistoryService.getProjectMembersHistoryListByProjectMemberId(projectMembers.getId());
									  int sendEmailCount = 1;
									  if(pmhList != null && pmhList.size() > 0){
										  for(ProjectMembersHistory pmh : pmhList){
											  pmh.setProjectTerminationDate(new Date());
											  pmh.setUpdatedDate(new Date());
											  pmh.setUpdatedBy(user.getUserCode());
											  pmh.setStatus(Constants.PROJECT_MEMBER_HISTORY_CLOSED);
											  pmh =  projectMembersHistoryService.updateProjectMembersHistory(pmh);
											  if(sendEmailCount == 1){
												  commonUtil.terminatedMailToEmployee(user,pmh,orgLocations);
											  }
											  
											  if(pmh.getProjectMemberId().getProjectClientsId().getOrgLocationsId().getId().equals(orgLocations.getId())){
												   /*List<ProjectApproverHierarchy>  mangerList = projectApproverHierarchyService.getApproverUsersByProjectId(pmh.getProjectMemberId().getProjects().getId());
												      for(ProjectApproverHierarchy pah : mangerList){
												    	  if(user.getId() != pah.getApproverUserId().getId()){
												    		  employeeTerminationMailToProjectManager(user,pmh,orgLocations);
												    	  }
												      }*/
												  commonUtil.employeeTerminationMailToProjectManager(user,pmh,orgLocations);
											  }else{
												  commonUtil.employeeTerminationMailToProjectManager(pmh.getProjectMemberId().getProjectClientsId().getOrgLocationsId().getUser(),pmh,pmh.getProjectMemberId().getProjectClientsId().getOrgLocationsId());
											  }
											  commonUtil.inactivateTimesheetSubmissionsBasedOnMemberIdAndMemberTerminationDate(pm.getId(),new Date());
											  sendEmailCount = sendEmailCount+1;
										  }
									  }
									  
									  
									  
									 /* ProjectMembersHistory pmh = projectMembersHistoryService.getProjectMembersOpenHistoryByProjectMemberId(projectMembers.getId());
									  if(pmh != null){
										  pmh.setProjectTerminationDate(new Date());
										  pmh.setUpdatedDate(new Date());
										  pmh.setUpdatedBy(user.getUserCode());
										  pmh.setStatus(Constants.PROJECT_MEMBER_HISTORY_CLOSED);
										  pmh =  projectMembersHistoryService.updateProjectMembersHistory(pmh);
									  }
									  
									  commonUtil.terminateProjectMemberByProjectMemberId(projectMemberId,Primary.Yes);
								      updatedCount = 1;*/
								      
								   if(null != pm.getProjects() && pm.getProjects().getId() != null){
										Projects projects = projectsService.getProjectById(pm.getProjects().getId());
										if(null != projects){
											Collection<ProjectMembers> projectMembersList = projectMembersService.getAllProjectMembersByProjectId(pm.getProjects().getId());
											ProjectsIndex projectsIndex = projectsIndexHelper.getProjectsIndexFromProjects(projects, null, projectMembersList);
											//Elastic Search Document Saving
											sourceleadELKBaseIndexer.saveOrUpdateElkDocument(projectsIndex, Constants.ELK_PROJECT_INDEX, Constants.ELK_PROJECT_TYPE,projects.getId().toString());
										}
								   }
						  	  }
						  	 }
						    } catch (Exception e) {
						   }
					    
					}
				}
			}
			
			
			
			if (StringUtils.isNotBlank(addUserDTO.getClientDocsOwnerUser())) {
				User clientDocsOwnerUser = new User();
				clientDocsOwnerUser.setId(Long.valueOf(addUserDTO.getClientDocsOwnerUser()));
				List<OrgDocuments> clientDocsList = orgDocumentsService.getOrgDocumentsListByUserIdAndOrgLocationIdAndSourceType(userId, orgLocations.getId(),"Client");
				Integer clientDocsCountAsOwner = orgDocumentsService.getDocumentOwnerCountByUserIdAndOrgLocationIdAndSourceType(userId, orgLocations.getId(),"Client");
				if (null != clientDocsList) {
					for (OrgDocuments orgDocuments : clientDocsList) {
						orgDocuments.setOwnerUser(clientDocsOwnerUser);
						orgDocuments.setUpdatedBy(user.getUserCode());
						orgDocuments.setUpdatedDate(new Date());
						orgDocumentsService.updateDocumnet(orgDocuments);
					}
				}
			}
			
			
			if (StringUtils.isNotBlank(addUserDTO.getVendorDocsOwnerUser())) {
				User vendorDocsOwnerUser = new User();
				vendorDocsOwnerUser.setId(Long.valueOf(addUserDTO.getVendorDocsOwnerUser()));
				List<OrgDocuments> vendorDocsList = orgDocumentsService.getOrgDocumentsListByUserIdAndOrgLocationIdAndSourceType(userId, orgLocations.getId(),"Vendor");
				Integer vendorDocsCountAsOwner = orgDocumentsService.getDocumentOwnerCountByUserIdAndOrgLocationIdAndSourceType(userId, orgLocations.getId(),"Vendor");
				if (null != vendorDocsList) {
					for (OrgDocuments orgDocuments : vendorDocsList) {
						orgDocuments.setOwnerUser(vendorDocsOwnerUser);
						orgDocuments.setUpdatedBy(user.getUserCode());
						orgDocuments.setUpdatedDate(new Date());
						orgDocumentsService.updateDocumnet(orgDocuments);
					}
				}
			}
			
			
			

			userRole.setIsPrimary(0);
			userRole.setPreviousUserState(userRole.getUserState());
			userRole.setUserState(Status.INACTIVE);
			userRole.setTerminateReason(addUserDTO.getReason());
			userRole.setTerminateDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),  addUserDTO.getTerminateDate()));
			userRole.setUpdatedBy(user.getUserCode());
			userRole.setUpdatedDate(commonUtil.currentDate());
						
			UserRole existinguserRole = userRoleService.getNotPrimaryUser(userId);
			if (existinguserRole != null) {
				existinguserRole.setIsPrimary(1);
				userRoleService.addUserRole(existinguserRole);
			}

			UserRole updateUserRole = userRoleService.addUserRole(userRole);
			
			Candidate candidate = candidateService.getCandidateByConsultantUserId(userId,orgLocations.getId());
			if(candidate != null){
				candidate.setIsAssociate("N");
				candidateService.saveOrUpdateCandidate(candidate);
				candidateService.saveOrUpdateCandidateElkDocument(candidate);
	
			}
			
			if(userRole.getTerminateReason()!=null)
			{
				
				sendTerminateEmailtoUser(user,userRole,orgLocations);	
			}
			// to assign primary role to another user
			
			Employees employee = employeesService.getEmployeeByUserIdAndOrgLocationsId(updateUserRole.getUser().getId(),
					orgLocations.getId());
			userService.saveOrUpdateUserElkDocument(updateUserRole, employee, user.getUserCode());
			
			if(isCouchbaseEnabled.equalsIgnoreCase("true")) {
				userStatsCacheHelper.saveUserProfileDetailsToCouchbase(userRole);
			}
			 
		}
		

		return "success";
	}

	
	
	
	/**
	 * @throws Exception *****/
	private void sendTerminateEmailtoUser( User user,UserRole userRole, OrgLocations orgLocations) throws Exception {
		   // SimpleDateFormat dateFormat=orgLocations.getLocFullFormDateFormatter();
		 
	        EmailMessage emailMessage = new EmailMessage();
	         emailMessage.setFirstName(userRole.getUser().getFirstName());
	         emailMessage.setLastName(userRole.getUser().getLastName());
	         emailMessage.setFromEmail(user.getEmail());
	         emailMessage.setToEmail(userRole.getUser().getEmail());
	         emailMessage.setCreatedBy(userRole.getUser().getUserCode());
	         StringBuilder msgBuild = new StringBuilder();
	         if(userRole.getUser().getLastName() != null) {
		         emailMessage.setFromName(msgBuild.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
	         }else {
		         emailMessage.setFromName(msgBuild.append(user.getFirstName()).toString());

	         }
	         emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
	        //setting the class filed values
	         Map<String, String> globalVarsMap = new HashMap<String, String>();
	        
	         globalVarsMap.put("loginUser", emailMessage.getFromName());

	         StringBuilder name = new StringBuilder();
	         if(userRole.getUser().getLastName() != null && userRole.getUser().getLastName() != "" && userRole.getUser().getLastName() != "undefined"){
	        	  globalVarsMap.put("applicantName", name.append(userRole.getUser().getFirstName()).append(" ").append(userRole.getUser().getLastName()).toString());
	        
	         }
	         else{
	        	 globalVarsMap.put("applicantName", userRole.getUser().getFirstName());
	         }
	         globalVarsMap.put("companyName", orgLocations.getOrg().getOrgName());
	         globalVarsMap.put("RejectReason", userRole.getTerminateReason());
	         globalVarsMap.put("date", CommonUtil.currentDate().toString());
	         StringBuilder name1 = new StringBuilder();
	         if(user.getFirstName() != null && user.getLastName()!= "" && user.getLastName() != "undefined"){
	        	  globalVarsMap.put("admin", name1.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
	         }else{
	        	 globalVarsMap.put("admin", user.getFirstName());
	         }
	        
	         StringBuilder loginBuilder = new StringBuilder();
			 globalVarsMap.put("loginURL",loginBuilder.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		  
	          //getting the current EmailScenario by EmailScenario unique name and entity type
	         EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.USERTERMINATEDNOTIFICATIONEMAIL,Constants.ENTITY_TYPE_NOTIFICATION);
	         String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
	         notificationScenario.setBody(notificationbody);
	         
	         //calling the sendEmail method of emailScenerioUtil
	         commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,userRole.getOrgLocations().getOrg(),userRole.getOrgLocations(),Constants.USERTERMINATEDEMAIL,new ArrayList<EmailAttachment>());

	         String actionLink="";
	       emailScenerioUtil.sendNotification(actionLink, userRole.getUser(), user, orgLocations, notificationScenario,new HashMap<String,String>());

}
	private void assignedNewUserAsaManager(User loginUser, Requirement requirement, Long userId, OrgLocations orgLocations, Integer requirementsCountAsManager2) throws Exception {
		
	Requirement req = 	requirementService.getRequirementByRequirementId(requirement.getId());
	
	 EmailMessage emailMessage = new EmailMessage();
	 emailMessage.setFirstName(loginUser.getFirstName());
	 emailMessage.setLastName(loginUser.getLastName());
	 emailMessage.setToEmail(req.getManagerUserId().getEmail());
	 emailMessage.setFromEmail(loginUser.getEmail());
	 emailMessage.setCreatedBy(loginUser.getUserCode());
	 StringBuilder msgBuild = new StringBuilder();
	emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
	emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
	//setting the class filed values
	 Map<String, String> globalVarsMap = new HashMap<String, String>();
	 	 User termentaedUserName = userService.getUserById(userId);
		 globalVarsMap.put("loginUser", loginUser.getFullName());
		 
		 StringBuilder assignedUserName = new StringBuilder();
		 if(req.getManagerUserId().getLastName() != null){
			 globalVarsMap.put("assignedUser", assignedUserName.append(req.getManagerUserId().getFirstName()).append(" ").append(req.getManagerUserId().getLastName()).toString());
		 }else{
			 globalVarsMap.put("assignedUser", assignedUserName.append(req.getManagerUserId().getFirstName()).toString());
		 }
		 globalVarsMap.put("reqCount", requirementsCountAsManager2.toString());
		 
		 StringBuilder terminatedUserName = new StringBuilder();
		 
		 if(termentaedUserName.getLastName() != null) {
			 globalVarsMap.put("terminatedUser", terminatedUserName.append(termentaedUserName.getFirstName()).append(" ").append(termentaedUserName.getLastName()).toString());
		 }else {
			 globalVarsMap.put("terminatedUser", terminatedUserName.append(termentaedUserName.getFirstName()).toString());
		 }
		 
		// globalVarsMap.put("terminatedUser",termentaedUserName.getFirstName());
		 
		 StringBuilder loginBuilder = new StringBuilder();
		 globalVarsMap.put("loginURL",loginBuilder.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
	  
	
	 //getting the current EmailScenario by EmailScenario unique name and entity type
	 //using same migration which has been already used in updatedRequirementMailToManager method
 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.USER_NEW_ACTION_ASSIGNED_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);

	 String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
	 notificationScenario.setBody(notificationbody);

	 
	 //calling the sendEmail method of emailScenerioUtil
	commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.USER_NEW_ACTION_ASSIGNED,new ArrayList<EmailAttachment>());

	 String actionLink="";
	 emailScenerioUtil.sendNotification(actionLink,loginUser, req.getManagerUserId(), orgLocations, notificationScenario,new HashMap<String,String>());
	}
	
	
	
	
	private void assignedNewUserAsaApprover(User loginUser, RequirementApproverHierarchy requirementApprover, Long userId, OrgLocations orgLocations,Integer requirementsCountAsApprover) throws Exception {
		RequirementApproverHierarchy abc = requirementApproverHierarchyService.getRequirementApproverHierarchyByRequirementId(requirementApprover.getRequirementId().getId(), requirementApprover.getApproverUserId().getId());
		//Integer requirementsCountAsManager = requirementService.getManagerAssignedRequirementsCount(userId,	orgLocations.getId());
		
		//Integer	count = requirementService.getManagerAssignedRequirementsCount(userId,orgLocations.getId());
		
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(loginUser.getFirstName());
		 emailMessage.setLastName(loginUser.getLastName());
		 emailMessage.setToEmail(abc.getApproverUserId().getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 StringBuilder msgBuild = new StringBuilder();
		emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 	 User termentaedUserName = userService.getUserById(userId);
			 globalVarsMap.put("loginUser", loginUser.getFullName());
		 	
			 StringBuilder assignedUserName = new StringBuilder();
			 if(abc.getApproverUserId().getLastName() != null){
				 globalVarsMap.put("assignedUser", assignedUserName.append(abc.getApproverUserId().getFirstName()).append(" ").append(abc.getApproverUserId().getLastName()).toString());
			 }else{
				 globalVarsMap.put("assignedUser", assignedUserName.append(abc.getApproverUserId().getFirstName()).toString());

			 }
			 globalVarsMap.put("reqCount", requirementsCountAsApprover.toString());
			 globalVarsMap.put("terminatedUser",termentaedUserName.getFirstName());
			 StringBuilder loginBuilder = new StringBuilder();
			 globalVarsMap.put("loginURL",loginBuilder.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		  
		  
		
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 //using same migration which has been already used in updatedRequirementMailToManager method
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.USER_NEW_ACTION_ASSIGNED_As_A_Approver,Constants.ENTITY_TYPE_NOTIFICATION);
	
		 
		 
		 String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
		 notificationScenario.setBody(notificationbody);
	
		 
		 //calling the sendEmail method of emailScenerioUtil
		 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.USER_NEW_ACTION_ASSIGNED_As_A_Approver,new ArrayList<EmailAttachment>());
		 
		 String actionLink="";
		 emailScenerioUtil.sendNotification(actionLink, loginUser, abc.getApproverUserId(), orgLocations, notificationScenario,new HashMap<String,String>());
		}
	
	
	
	
	
	
	private void assignedNewUserAsaReportingManagers(User loginUser, UserRole requirementApprover, Long userId, OrgLocations orgLocations,Long count) throws Exception {
		User assUserName = userService.getUserById(requirementApprover.getReportingManager().getId());		
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(loginUser.getFirstName());
		 emailMessage.setLastName(loginUser.getLastName());
		 emailMessage.setToEmail(assUserName.getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 StringBuilder msgBuild = new StringBuilder();
		emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 	 User termentaedUserName = userService.getUserById(userId);
			 globalVarsMap.put("loginUser", loginUser.getFullName());
			 globalVarsMap.put("assignedUser", assUserName.getFullName());
			 globalVarsMap.put("reqCount", count.toString());
			 globalVarsMap.put("terminatedUser",termentaedUserName.getFirstName());
			 StringBuilder loginBuilder = new StringBuilder();
			 globalVarsMap.put("loginURL",loginBuilder.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		  
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 //using same migration which has been already used in updatedRequirementMailToManager method
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.USER_NEW_ACTION_ASSIGNED_As_A_REPORTING_MANAGER,Constants.ENTITY_TYPE_NOTIFICATION);
	
		 String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
		 notificationScenario.setBody(notificationbody);
	
		 //calling the sendEmail method of emailScenerioUtil
			commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.USER_NEW_ACTION_ASSIGNED_As_A_REPORTING_MANAGER,new ArrayList<EmailAttachment>());

		 String actionLink="";
		 emailScenerioUtil.sendNotification(actionLink, loginUser, assUserName, orgLocations, notificationScenario,new HashMap<String,String>());
		}
	
	
	
	private void assignedNewUserAsateamOwnerUser(User loginUser, Team requirementApprover, Long userId, OrgLocations orgLocations,Integer teamsCountAsOwner) throws Exception {
		User assUserName = userService.getUserById(requirementApprover.getOwnerUserId().getId());		
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(loginUser.getFirstName());
		 emailMessage.setLastName(loginUser.getLastName());
		 emailMessage.setToEmail(assUserName.getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 StringBuilder msgBuild = new StringBuilder();
		emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 	 User termentaedUserName = userService.getUserById(userId);
			 globalVarsMap.put("loginUser", loginUser.getFullName());
			 globalVarsMap.put("assignedUser", assUserName.getFullName());
			 globalVarsMap.put("reqCount", teamsCountAsOwner.toString());
			 globalVarsMap.put("terminatedUser",termentaedUserName.getFullName());
			 StringBuilder loginBuilder = new StringBuilder();
			 globalVarsMap.put("loginURL",loginBuilder.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		  
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 //using same migration which has been already used in updatedRequirementMailToManager method
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.USER_NEW_ACTION_ASSIGNED_As_A_TEAM_OWNER,Constants.ENTITY_TYPE_NOTIFICATION);
	
		 String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
		 notificationScenario.setBody(notificationbody);
	
		 //calling the sendEmail method of emailScenerioUtil
		 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.USER_NEW_ACTION_ASSIGNED_As_A_TEAM_OWNER,new ArrayList<EmailAttachment>());

		 String actionLink="";
		 emailScenerioUtil.sendNotification(actionLink, loginUser, assUserName, orgLocations, notificationScenario,new HashMap<String,String>());
		}
	
	
	
	@PreAuthorize("hasRole('ROLE_VIEW_EDIT_ASSOCIATE')")
	@RequestMapping(value = "/editUserRole", method = RequestMethod.POST)
	public @ResponseBody String editUserRole(AddUserDTO addUserDTO, @RequestParam(value = "userId", required = false) Long userId,
			 HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(userId, orgLocations.getId());
		
		// this is used for user role changed mail scenario
		String existingRole = userRole.getUserType();
		
		/*json = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json);
*/
		if (userRole != null) {

			if (StringUtils.isNotBlank(addUserDTO.getAssignReportingManagers())) {
				List<UserRole> userRolesList = userRoleService.getAllAssignedUsersByReportingManagerId(userId,
						orgLocations.getId());
				User reportingManager = new User();
				reportingManager.setId(Long.valueOf(addUserDTO.getAssignReportingManagers()));
				if (null != userRolesList) {
					for (UserRole roles : userRolesList) {
						roles.setReportingManager(reportingManager);
						roles.setUpdatedBy(user.getUserCode());
						roles.setUpdatedDate(new Date());
						userRoleService.addUserRole(roles);
					}
				}

			}

			if (StringUtils.isNotBlank(addUserDTO.getClientUsers())) {
				User clientOwnerOrUser = new User();
				clientOwnerOrUser.setId(Long.valueOf(addUserDTO.getClientUsers()));
				List<OrgSpoc> orgSpocList = orgSpocService.getAssignedAsClientOrVendorSpocByUserId(userId,
						orgLocations.getId(), "Vendor");
				if (null != orgSpocList) {
					for (OrgSpoc orgSpoc : orgSpocList) {
						String spocOwnerName = orgSpoc.getOwnerUser().getFullName();
						orgSpoc.setOwnerUser(clientOwnerOrUser);
						orgSpoc.setVendorUser(clientOwnerOrUser);
						orgSpoc.setUpdatedBy(user.getUserCode());
						orgSpoc.setUpdatedDate(new Date());
						orgSpocService.addOrgSpoc(orgSpoc);
						pointOfContactChangedMial(user, orgLocations, orgSpoc, "Vendor", spocOwnerName,clientOwnerOrUser);
					}
				}

			}

			if (StringUtils.isNotBlank(addUserDTO.getVendorUsers())) {
				User vendorOwnerOrUser = new User();
				vendorOwnerOrUser.setId(Long.valueOf(addUserDTO.getVendorUsers()));
				List<OrgSpoc> orgSpocList = orgSpocService.getAssignedAsClientOrVendorSpocByUserId(userId,
						orgLocations.getId(), "Client");
				if (null != orgSpocList) {
					for (OrgSpoc orgSpoc : orgSpocList) {
						String spocOwnerName = orgSpoc.getOwnerUser().getFullName();
						orgSpoc.setOwnerUser(vendorOwnerOrUser);
						orgSpoc.setClientUser(vendorOwnerOrUser);
						orgSpoc.setUpdatedBy(user.getUserCode());
						orgSpoc.setUpdatedDate(new Date());
						orgSpocService.addOrgSpoc(orgSpoc);
						pointOfContactChangedMial(user, orgLocations, orgSpoc, "Client", spocOwnerName,vendorOwnerOrUser);
					}
				}

			}

			if (StringUtils.isNotBlank(addUserDTO.getManagerUsers())) {
				User requirementManagerUser = new User();
				requirementManagerUser.setId(Long.valueOf(addUserDTO.getManagerUsers()));
				List<Requirement> requirementsList = requirementService.getManagerAssignedRequirementsList(userId,
						orgLocations.getId());
				if (null != requirementsList) {
					for (Requirement requirement : requirementsList) {
						requirement.setManagerUserId(requirementManagerUser);
						requirement.setUpdatedBy(user.getUserCode());
						requirement.setUpdatedDate(new Date());
						requirementService.addRequirement(requirement);
					}
				}

			}

			if (StringUtils.isNotBlank(addUserDTO.getApproverUser())) {
				User requirementApproverUser = new User();
				requirementApproverUser.setId(Long.valueOf(addUserDTO.getApproverUser()));
				List<RequirementApproverHierarchy> approversList = requirementApproverHierarchyService
						.getRequirementsAsApprover(userId, orgLocations.getId());
				if (null != approversList) {
					for (RequirementApproverHierarchy hierarchy : approversList) {
						hierarchy.setApproverUserId(requirementApproverUser);
						hierarchy.setUpdatedBy(user.getUserCode());
						hierarchy.setUpdatedDate(new Date());
						requirementApproverHierarchyService.addRequirementApproverHierarchy(hierarchy);
					}
				}

			}

			if (StringUtils.isNotBlank(addUserDTO.getTeamOwnerUser())) {
				User teamOwnerUser = new User();
				teamOwnerUser.setId(Long.valueOf(addUserDTO.getTeamOwnerUser()));
				List<Team> teamsList = teamService.getTeamListByTeamOwner(userId, orgLocations.getId());
				if (null != teamsList) {
					for (Team team : teamsList) {
						team.setOwnerUserId(teamOwnerUser);
						team.setUpdatedBy(user.getUserCode());
						team.setUpdatedDate(new Date());
						teamService.saveTeam(team);
					}
				}

			}

			if (StringUtils.isNotBlank(addUserDTO.getProjectApproverUser())) {

				projectsService.tereminateAsProjectApprover(Long.valueOf(addUserDTO.getProjectApproverUser()), user, userId, orgLocations);
			}
			
			
			
			if (StringUtils.isNotBlank(addUserDTO.getClientDocsOwnerUser())) {
				User clientDocsOwnerUser = new User();
				clientDocsOwnerUser.setId(Long.valueOf(addUserDTO.getClientDocsOwnerUser()));
				List<OrgDocuments> clientDocsList = orgDocumentsService.getOrgDocumentsListByUserIdAndOrgLocationIdAndSourceType(userId, orgLocations.getId(),"Client");
				if (null != clientDocsList) {
					for (OrgDocuments orgDocuments : clientDocsList) {
						orgDocuments.setOwnerUser(clientDocsOwnerUser);
						orgDocuments.setUpdatedBy(user.getUserCode());
						orgDocuments.setUpdatedDate(new Date());
						orgDocumentsService.updateDocumnet(orgDocuments);
					}
				}
			}
			
			
			if (StringUtils.isNotBlank(addUserDTO.getClientDocsOwnerUser())) {
				User vendorDocsOwnerUser = new User();
				vendorDocsOwnerUser.setId(Long.valueOf(addUserDTO.getClientDocsOwnerUser()));
				List<OrgDocuments> vendorDocsList = orgDocumentsService.getOrgDocumentsListByUserIdAndOrgLocationIdAndSourceType(userId, orgLocations.getId(),"Vendor");
				if (null != vendorDocsList) {
					for (OrgDocuments orgDocuments : vendorDocsList) {
						orgDocuments.setOwnerUser(vendorDocsOwnerUser);
						orgDocuments.setUpdatedBy(user.getUserCode());
						orgDocuments.setUpdatedDate(new Date());
						orgDocumentsService.updateDocumnet(orgDocuments);
					}
				}
			}
			
			

			if (StringUtils.isNotBlank(addUserDTO.getUserChangeRole())) {
				Roles roles = rolesService.getRolesById(Long.valueOf(addUserDTO.getUserChangeRole()));
				userRole.setRoles(roles);
				userRole.setUserType(roles.getDisplayName());
			}
			
			userRole.setRoleChangeReason(addUserDTO.getReason());
			userRole.setUpdatedBy(user.getUserCode());
			userRole.setUpdatedDate(commonUtil.currentDate());
			userRoleService.addUserRole(userRole);
			userStatsCacheHelper.saveUserProfileDetailsToCouchbase(userRole);
			
			// User role changed mail
			userRoleUpdateMail(user,userRole,orgLocations,existingRole);

			/*
			 * Saving USER DB data as ElasticSearch Documents
			 */
			Employees employee = employeesService.getEmployeeByUserIdAndOrgLocationsId(userRole.getUser().getId(),
					orgLocations.getId());
			if (null != employee) {
				userService.saveOrUpdateUserElkDocument(userRole, employee, user.getUserCode());
			}
			int updated = associateBalanceLeaveService.updateAssociateBalanceLeaves(userRole.getUser(), orgLocations, employee, userRole);
		}
		
		return "success";
	}

	
	private void userRoleUpdateMail(User loginUser, UserRole userRole, OrgLocations loginOrgLocation, String existingRole) throws Exception
	{
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(loginUser.getFirstName());
		emailMessage.setLastName(loginUser.getLastName());
		emailMessage.setToEmail(userRole.getUser().getEmail());
		emailMessage.setFromEmail(loginUser.getEmail());
		emailMessage.setCreatedBy(loginUser.getUserCode());
		
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		// StringBuilder msgBuild = new StringBuilder();
		// emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append("
		// ").append(loginUser.getLastName()).toString());
		// emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		StringBuilder stringBuildername = new StringBuilder();
		globalVarsMap.put("FirstName", stringBuildername.append(userRole.getUser().getFirstName()).append(" ").append(userRole.getUser().getLastName()).toString());
		globalVarsMap.put("CurrentRole", userRole.getUserType());
		globalVarsMap.put("ExistingRole", existingRole);
		StringBuilder stringBuilderLoginname = new StringBuilder();
		globalVarsMap.put("LoginUser", stringBuilderLoginname.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		StringBuilder stringBuildforLink = new StringBuilder();
		globalVarsMap.put("Loginlink", stringBuildforLink.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		// getting the current EmailScenario by EmailScenario unique name and
		// entity type
		// using same migration which has been already used in
		// updatedRequirementMailToManager method
		EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.UPDATED_USER_ROLE_NOTIFICATION, Constants.ENTITY_TYPE_NOTIFICATION);
		// covert subject,body and signature
		String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap, notificationScenario);
		notificationScenario.setBody(notificationbody);
		// calling the sendEmail method of emailScenerioUtil
		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, userRole.getOrgLocations().getOrg(), userRole.getOrgLocations(), Constants.UPDATED_USER_ROLE_EMAIL,new ArrayList<EmailAttachment>());
		String actionLink = "";
		emailScenerioUtil.sendNotification(actionLink, loginUser, userRole.getUser(), loginOrgLocation, notificationScenario, new HashMap<String, String>());
	}		
	
	
	private void pointOfContactChangedMial(User loginUser, OrgLocations loginOrgLocation,OrgSpoc orgSpoc,String type,String spocOwnerName,User SpocOwner) throws Exception {
		
		 String spocUserName = null;
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(loginUser.getFirstName());
		 emailMessage.setLastName(loginUser.getLastName());
		 if(type.equalsIgnoreCase("Client")) {
			 emailMessage.setToEmail(orgSpoc.getVendorUser().getEmail());
			 spocUserName = orgSpoc.getVendorUser().getFullName();
		 }else {
			 emailMessage.setToEmail(orgSpoc.getClientUser().getEmail());
			 spocUserName = orgSpoc.getClientUser().getFullName();
		 }
		
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 
			globalVarsMap.put("spocUserName", spocUserName);
			User spocOwner = userService.getUserById(SpocOwner.getId());
			globalVarsMap.put("spocOwnerName", spocOwner.getFullName());
			globalVarsMap.put("spocOldOwnerName", spocOwnerName);
			globalVarsMap.put("loginUser", loginUser.getFullName());
			globalVarsMap.put("loginOrgName", loginOrgLocation.getOrgLocationName());

		 
		 //calling the sendEmail method of emailScenerioUtil
		 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,loginOrgLocation.getOrg(),loginOrgLocation,Constants.UPDATED_SPOC_CONTACT,new ArrayList<EmailAttachment>());
		}
	
	/**
	 * To update user and employee By User ID.
	 * 
	 * @param json
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	
	@PreAuthorize("hasRole('ROLE_VIEW_EDIT_ASSOCIATE')")
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	@ResponseBody
	String updateUser(AddUserDTO addUserDTO,@RequestParam(value = "userId", required = false) Long userId,
			@RequestParam(value = "emailId", required = false) String emailId, HttpServletRequest request)
			throws Exception {
		if(Validator.validate(addUserDTO)){
		
		 Long loginUserId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
	     User loginUser = userService.getUserById(loginUserId);
	     
	     Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		 OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		
		addUserDTO.setDateFormat(orgLocations.getDateFormat());
		User user = userService.getUserById(userId);
		UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocations.getId());

		/*json = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json);*/
		String password = "";
		boolean isUserActive = false;
		//SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
		addUserDTO.setDateFormat(orgLocations.getDateFormat());
		User updatedUser = null;
		if (user != null) {
			user.setFirstName(addUserDTO.getFirstName());
			user.setLastName(addUserDTO.getLastName());
			user.setMiddelName(addUserDTO.getMiddleName());
			user.setGender(addUserDTO.getGender());
			user.setMaritalStatus(addUserDTO.getMaritalStatus());
			user.setMobilePhone(addUserDTO.getPhoneNumber());
			user.setExtensionNumber(addUserDTO.getExtensionNumber());
			user.setSsn(addUserDTO.getSsn());
			
			Long existingReportingManager=null;
			if (StringUtils.isNotBlank(addUserDTO.getReportingManager())) {
				User reportingManager = new User();
				if(userRole.getReportingManager() != null) {
					existingReportingManager = userRole.getReportingManager().getId(); 
				}
				reportingManager.setId(Long.parseLong(addUserDTO.getReportingManager()));
				userRole.setReportingManager(reportingManager);
			} else {
				userRole.setReportingManager(null);
			}

			if(StringUtils.isNotBlank(addUserDTO.getDateofBirth())){
				try {
					user.setDateOfBirth(commonUtil.orgLocationDateFormat(addUserDTO.getDateFormat(), addUserDTO.getDateofBirth()));
					  
					} catch (Exception e) {
					  e.printStackTrace();
					}
			}else{
				user.setDateOfBirth(null);
			}

			
			if(StringUtils.isNotBlank(addUserDTO.getDesignation())){
				userRole.setDesignation(addUserDTO.getDesignation());
			}
			
			if(StringUtils.isNotBlank(addUserDTO.getResourceManager())){
				User resourceManager = new User();
				resourceManager.setId(Long.parseLong(addUserDTO.getResourceManager()));
				userRole.setResourceManager(resourceManager);
			}
			
			if(StringUtils.isNotBlank(addUserDTO.getShiftId())){
				Shift shift = new Shift();
				shift.setId(Long.parseLong(addUserDTO.getShiftId()));
				userRole.setShift(shift);
			}


			isUserActive = userRoleService.isUserActive(user.getId());
			if (isUserActive == false && !emailId.equalsIgnoreCase(addUserDTO.getEmail())) {
				// generate random password
				user.setEmail(addUserDTO.getEmail());
				user.setUserName(addUserDTO.getEmail());
				password = SourceLeadRandomCodeHelper.generateRandomPassword();
				user.setPassword(sourceleadPasswordEncoder.encode(password));
				userRole.setIsMailSent(Status.Y);
			}
			user.setUpdatedBy(loginUser.getUserCode());
			user.setUpdatedDate(commonUtil.currentDate());
			userRole.setUpdatedBy(loginUser.getUserCode());
			userRole.setUpdatedDate(commonUtil.currentDate());
			String existingUserEmail = null;
			
			if(isUserActive == true && !user.getEmail().equalsIgnoreCase(addUserDTO.getEmail())) {
				existingUserEmail = user.getEmail();
				user.setEmail(addUserDTO.getEmail());
				user.setUserName(addUserDTO.getEmail());
			}
			updatedUser = userService.addUser(user);
			
			if(StringUtils.isNotBlank(existingUserEmail)) {
				userService.sendEmailToUserForEmailUpdation(updatedUser, loginUser, orgLocations, existingUserEmail, existingUserEmail);
				userService.sendEmailToUserForEmailUpdation(updatedUser, loginUser, orgLocations, user.getEmail(), existingUserEmail);
			}
			
			if(StringUtils.isNotBlank(addUserDTO.getIsGlobalAccess()) && addUserDTO.getIsGlobalAccess().equalsIgnoreCase(Constants.YES)) {
				userRole.setIsGlobal(Constants.YES);
			}else {
				userRole.setIsGlobal(Constants.NO);
			}
			
			if(StringUtils.isNotBlank(addUserDTO.getGrade())) {
				AssociateGrades grades = new AssociateGrades();
				grades.setId(Long.valueOf(addUserDTO.getGrade()));
				userRole.setGradeId(grades);
			}else {
				userRole.setGradeId(null);
			}
			
			if(addUserDTO.getCustomFormData() !=null) {
				for( CustomFormFieldDTO customFields:addUserDTO.getCustomFormData()) {
					if(customFields!= null && customFields.getCustomFormFieldId() > 0 ) {
						UserCustomFieldData userCustomFields = null;
						if(customFields.getEntityId()!= null && customFields.getEntityId() > 0) {
							userCustomFields = userCustomFieldDataService.getUserCustomFieldDataById(customFields.getEntityId());
						}else {
							userCustomFields = new UserCustomFieldData();
						}
					userCustomFields.setUser(updatedUser);
					OrglocationFormFields orgFormField = orglocationFormFieldsService.getOrglocationFormFieldsById(customFields.getCustomFormFieldId());
					userCustomFields.setOrglocationsFormFields(orgFormField);
					userCustomFields.setCustomFormFieldData(customFields.getCustomFormFieldData());
					userCustomFieldDataService.saveUserCustomFieldData(userCustomFields);
					}
					
				}	
			}
			
			userRoleService.addUserRole(userRole);

			if (isUserActive == false && !emailId.equalsIgnoreCase(addUserDTO.getEmail())) {
				userService.sendEmail(user, loginUser, password, orgLocations.getId());
			}
			
			if(existingReportingManager != null && existingReportingManager != (Long.parseLong(addUserDTO.getReportingManager()))){
				userService.sendEmailForNewReportingManager(user, loginUser, password, orgLocations.getId(), existingReportingManager, Long.parseLong(addUserDTO.getReportingManager()));
			}
			
		}
		Employees employees = employeesService.getEmployeeByUserIdAndOrgLocationsId(userId, orgLocations.getId());

		if (employees != null) {
			employees.setEmployeeId(addUserDTO.getEmployeeId());
			employees.setRate(addUserDTO.getRate());
			employees.setRatePer(addUserDTO.getRatePer());
			employees.setDailyHours(addUserDTO.getDayHours());
			employees.setCurrentCurrency(addUserDTO.getCurrentCurrency());
			
			if(StringUtils.isNotBlank(addUserDTO.getDesignationType())){
				employees.setUserEmploymentType(addUserDTO.getDesignationType());
			}
			if(StringUtils.isNotBlank(addUserDTO.getDesignationOtherType())){
				employees.setDesignationOtherType(addUserDTO.getDesignationOtherType());
			}
			
			if(StringUtils.isNotBlank(addUserDTO.getDepartment())){
				if(employees.getDepartmentId() != null ? !employees.getDepartmentId().getId().equals(Long.parseLong(addUserDTO.getDepartment())) : true) {
					Department department= departmentService.getDepartmentById(Long.parseLong(addUserDTO.getDepartment()));
					employees.setDepartmentId(department);
					Candidate candidate = candidateService.getCandidateByConsultantUserId(userId, orgLocations.getId());
					if(candidate != null) {
						AssociateSalaryInd associateSalaryInd =  payrollPayheadDao.getAssociateSalaryIndByCandidateId(candidate.getId());
						if(associateSalaryInd != null) {
							associateSalaryInd.setGroupId(null);
							associateSalaryInd.setUpdatedDate(new Date());
							associateSalaryInd.setUpdatedBy(loginUser);
							payrollPayheadDao.saveAssociateSalaryInd(associateSalaryInd);
						}
					}
				}
			}else{
				employees.setDepartmentId(null);
				Candidate candidate = candidateService.getCandidateByConsultantUserId(userId, orgLocations.getId());
				if(candidate != null) {
					AssociateSalaryInd associateSalaryInd =  payrollPayheadDao.getAssociateSalaryIndByCandidateId(candidate.getId());
					if(associateSalaryInd != null) {
						associateSalaryInd.setGroupId(null);
						associateSalaryInd.setUpdatedDate(new Date());
						associateSalaryInd.setUpdatedBy(loginUser);
						payrollPayheadDao.saveAssociateSalaryInd(associateSalaryInd);
					}
				}
			}
			
			if(StringUtils.isNotBlank(addUserDTO.getVendors())){
				OrgRelations orgRelation = orgRelationsService.getOrRelationsById(Long.parseLong(addUserDTO.getVendors()));
				employees.setOrgRelationId(orgRelation);
			}else{
				employees.setOrgRelationId(null);
			}
			
			if(StringUtils.isNotBlank(addUserDTO.getVendorcontact())){
				OrgSpoc orgspoc = orgSpocService.getOrgSpocById(Long.parseLong(addUserDTO.getVendorcontact()));
				employees.setSpocId(orgspoc);
			}else{
				employees.setSpocId(null);
			}
			/*
			 * employees.setInvoiceFrequency(jsonObj.getString(
			 * "InvoiceFrequency"));
			 */
			if (StringUtils.isNotBlank(addUserDTO.getDateofJoin())) {
				try {
					//DateFormat sdf = simpleDateFormat;
					employees.setHireDate(commonUtil.orgLocationDateFormat(addUserDTO.getDateFormat(),addUserDTO.getDateofJoin()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				employees.setHireDate(null);
			}
			
			
			//updating probation data in employee
			
			String previousStatus = employees.getProbationStatus();
			
			if(StringUtils.isNotBlank(addUserDTO.getConfirmationDate())){
				try {
					 employees.setConfirmationDate(commonUtil.orgLocationDateFormat(addUserDTO.getDateFormat(), addUserDTO.getConfirmationDate()));
					  
					} catch (Exception e) {
					  e.printStackTrace();
					}
			}else {
					employees.setConfirmationDate(null);
			}
			
			if(StringUtils.isNotBlank(addUserDTO.getProbationPeriod())){
					  employees.setProbationPeriod(addUserDTO.getProbationPeriod());
			}else {
					  employees.setProbationPeriod(null);
			}
			
			
			if(StringUtils.isNotBlank(addUserDTO.getProbationStatus())){
					  employees.setProbationStatus(addUserDTO.getProbationStatus());
			}else {
					  employees.setProbationStatus(null);
			}
			
			
			employees.setUpdatedBy(loginUser.getUserCode());
			employees.setUpdatedDate(commonUtil.currentDate());
			employeesService.updateEmployee(employees);
			
			
			
			//saving probation history
			
			ProbationHistory probationHistory = new ProbationHistory();
			probationHistory.setEmployeeId(employees);
			probationHistory.setDateOfJoin(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), addUserDTO.getDateofJoin()));
			probationHistory.setProbationPeriod(addUserDTO.getProbationPeriod());
			probationHistory.setFromStatus(previousStatus);
			probationHistory.setToStatus(addUserDTO.getProbationStatus());
			if(StringUtils.isNotBlank(addUserDTO.getConfirmationDate()))
				probationHistory.setActualConfirmationDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), addUserDTO.getConfirmationDate()));
			else
				probationHistory.setActualConfirmationDate(null);
						
			if(addUserDTO.getProbationStatus().equalsIgnoreCase(Constants.CONFIRMED)) {
				probationHistory.setConfirmedDate(new Date());
			}else {
				probationHistory.setConfirmedDate(null);
			}
			probationHistory.setStatus(1);
			probationHistory.setUuid(SourceLeadRandomCodeHelper.generateUUID());
			probationHistory.setUpdatedDate(new Date());
			probationHistory.setUpdatedBy(loginUser.getId());
			probationHistoryService.saveProbationHistory(probationHistory);
			
		}
		
		Candidate candidate = candidateService.getCandidateByConsultantUserId(user.getId(), orgLocations.getId());
		if(candidate != null) {
			OrgRelations orgRelation = null;
			Candidate vendorCandidate = null;
			OrgSpoc orgspoc = null;
			OrgRelations currentVendorOrgRelation = null;
			if(candidate.getOrgRelationId() != null) {
				currentVendorOrgRelation = candidate.getOrgRelationId();
			}
			if(StringUtils.isNotBlank(addUserDTO.getDesignationType())){
				candidate.setDesignationType(addUserDTO.getDesignationType());
			}
			if(StringUtils.isNotBlank(addUserDTO.getDesignationOtherType())){
				candidate.setDesignationOtherType(addUserDTO.getDesignationOtherType());
			}
			
			if(StringUtils.isNotBlank(addUserDTO.getVendors())){
				orgRelation = orgRelationsService.getOrRelationsById(Long.parseLong(addUserDTO.getVendors()));
				CandidateDTO candidateDTO = new CandidateDTO();
				candidateDTO.setEmail(candidate.getEmail());
				candidateDTO.setFirstName(candidate.getFirstName());
				candidateDTO.setLastName(candidate.getLastName());
				candidateDTO.setCountry(candidate.getCountry());
				vendorCandidate =  commonUtil.getVendorCandidateForCandidateCreation(candidateDTO, orgRelation, orgLocations, user);
				candidate.setParentCandidateId(vendorCandidate);
				candidate.setOrgRelationId(orgRelation);
			}else{
				candidate.setOrgRelationId(null);
				candidate.setParentCandidateId(null);
			}
			
			if(StringUtils.isNotBlank(addUserDTO.getVendorcontact())){
				orgspoc = orgSpocService.getOrgSpocById(Long.parseLong(addUserDTO.getVendorcontact()));
				candidate.setSpocId(orgspoc);
			}else{
				candidate.setSpocId(null);
			}
			
			candidate.setFirstName(addUserDTO.getFirstName());
			candidate.setLastName(addUserDTO.getLastName());
			candidate.setMiddelName(addUserDTO.getMiddleName());
			candidate.setGender(addUserDTO.getGender());
			candidate.setMobilePhone(addUserDTO.getPhoneNumber());
			candidate.setMaritalStatus(addUserDTO.getMaritalStatus());
			
			if(StringUtils.isNotBlank(addUserDTO.getResidenceAddress())){
			   candidate.setResidenceAddress(addUserDTO.getResidenceAddress());
			   candidate.setResidenceCountry(addUserDTO.getResidenceCountry());
			   candidate.setResidenceState(addUserDTO.getResidenceState());
			   candidate.setResidenceCity(addUserDTO.getResidenceCity());
			   candidate.setResidenceLatitude(addUserDTO.getResidenceLatitude());
			   candidate.setResidenceLongitude(addUserDTO.getResidenceLongitude());
			   candidate.setResidenceZipCode(addUserDTO.getResidenceZipCode());
			   candidate.setResidenceStreetAddress(addUserDTO.getResidenceAddress());
		    	Countries country = countriesService.getCountriesByCountryName(addUserDTO.getResidenceCountry());
		    	if(country != null) {
		    		String stateCode = statesService.getStateCodeByCountryCodeAndState(country.getCode2(),addUserDTO.getResidenceState());
			    	candidate.setResidenceStateCode(stateCode);
		    	}
		    	
		    }
			
			if(StringUtils.isNotBlank(addUserDTO.getWorkLocation())){
				 OrgLocations workLocation = orgLocationService.getOrgLocations(Long.parseLong(addUserDTO.getWorkLocation()));
				   candidate.setWorkLocation(workLocation);
				   if(candidate.getId() != null) {
					   List<Candidate> canlist = candidateService.getOffshoreCandidatesByParentCandidateId(candidate.getId());
					   if(canlist != null && !canlist.isEmpty()) {
						   for(Candidate can :canlist) {
							   can.setWorkLocation(workLocation);
							   can.setFirstName(addUserDTO.getFirstName());
							   can.setLastName(addUserDTO.getLastName());
							   can.setMiddelName(addUserDTO.getMiddleName());
							   can.setMobilePhone(addUserDTO.getPhoneNumber());
							   candidateService.saveOrUpdateCandidate(can);
							   candidateService.saveOrUpdateCandidateElkDocument(can);
						   }
					   }
				   }
			}
			
			if(StringUtils.isNotBlank(addUserDTO.getDateofBirth())){
				candidate.setDateOfBirth(commonUtil.orgLocationDateFormat(addUserDTO.getDateFormat(), addUserDTO.getDateofBirth()));
			}
			
			if(StringUtils.isNotBlank(addUserDTO.getPanNumber())){
				   candidate.setPanNumber(addUserDTO.getPanNumber());
			}
			if(StringUtils.isNotBlank(addUserDTO.getAadharNumber())){
				   candidate.setAadharNumber(addUserDTO.getAadharNumber());
			}
			
			if(StringUtils.isNotBlank(addUserDTO.getSsn())){  
				candidate.setSsnNumber(addUserDTO.getSsn());
				}
			   
			candidateService.saveOrUpdateCandidate(candidate);
			candidateService.saveOrUpdateCandidateElkDocument(candidate);
			
			//For offshoreC2C candidates
			if(candidate != null && candidate.getWorkLocation() != null) {
				List<Candidate> candidatesList = candidateService.getCandidatesForUpdateWorkLocation(candidate.getMasterCandidateId().getId(), orgLocations.getOrg().getId());
				if(candidatesList != null && !candidatesList.isEmpty()) {
					for(Candidate offshoreCandiate : candidatesList) {
						offshoreCandiate.setWorkLocation(candidate.getWorkLocation());
						candidateService.saveOrUpdateCandidate(offshoreCandiate);
					}
				}
			}
			
			if(StringUtils.isNotBlank(addUserDTO.getVendors())){
				if(currentVendorOrgRelation == null || (currentVendorOrgRelation != null && !currentVendorOrgRelation.getId().equals(orgRelation.getId()))) {
					List<ProjectMembers> projectMembersList = projectMembersService.getAllActiveProjectsByUserIdAndOrgLocationId(user.getId(), orgLocations.getId());
					if(projectMembersList != null && !projectMembersList.isEmpty()) {
						
						  OrgLocations vendorOrgLocation = null;
						  if(orgRelation.getLocationId().getId().equals(orgLocations.getId())){
							  vendorOrgLocation = orgRelation.getResponderLocationId();
						  }else if(orgRelation.getResponderLocationId().getId().equals(orgLocations.getId())){
							  vendorOrgLocation = orgRelation.getLocationId();
						  }
						
						for(ProjectMembers projectMembers : projectMembersList) {
							saveVendorProjects(projectMembers,vendorOrgLocation,vendorCandidate,orgRelation,user,orgspoc,orgLocations);
						}
					}
					
					if(currentVendorOrgRelation != null) {
						 removeVendorProject(currentVendorOrgRelation,user,orgLocations,candidate);
					}
				}
			}else{
				if(currentVendorOrgRelation != null) {
					 removeVendorProject(currentVendorOrgRelation,user,orgLocations,candidate);
				}
			}
		}
		
		
		/*
		 * update common user (single user different orgs) details in  ElasticSearch Documents (User)
		 */
		commonUtil.commonUserUpdate(user);
		/*
		 * update spoc details in ElasticSearch Documents
		 */
		commonUtil.updateSpockDetails(user, orgLocations);
		/*
		 * Save or update the user in Elk.
		 */
		userService.saveOrUpdateUserElkDocument(userRole, employees, loginUser.getUserCode());
		

		userStatsCacheHelper.saveUserProfileDetailsToCouchbase(userRole);
		return "success";
		}else{
			return "DataMissing";
		}
	}
	
	
	public void saveVendorProjects(ProjectMembers projectMembers,OrgLocations vendorOrgLocation,Candidate vendorCandidate,OrgRelations orgRelation,User user,OrgSpoc orgspoc,OrgLocations orgLocations) {
		
		try {
		ProjectClients pclients = projectClientService.getProjectClientsByProjectIdAndOrgLocationId(projectMembers.getProjects().getId(), vendorOrgLocation.getId());
		if(pclients == null) {
			
			pclients = new ProjectClients();
			pclients.setProjectId(projectMembers.getProjects());
			pclients.setOrgRelationId(orgRelation);
			pclients.setOrgLocationsId(vendorCandidate.getOrgLocationId());
			pclients.setOrgId(vendorCandidate.getOrgId());
			pclients.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
			pclients.setStatus(1);
			
		
			ProjectClients pc = projectClientService.getProjectClientsByProjectIdAndOrgLocationId(projectMembers.getProjects().getId(), orgLocations.getId());
			if(pc != null && pc.getLevel() != null){
				pclients.setLevel(pc.getLevel()+1);
			}
			
			pclients.setCreatedDate(new Date());
			pclients.setCreatedBy(user);
			pclients.setUpdatedDate(new Date());
			pclients.setUpdatedBy(user);
			
			//prepare ProjectApproverHierarchy
			Set<ProjectApproverHierarchy> projectApproverHierarchyList = new HashSet<ProjectApproverHierarchy>();
			ProjectApproverHierarchy pah = new ProjectApproverHierarchy();
			pah.setProjectId(projectMembers.getProjects());
			pah.setProjectClientsId(pclients);
			
			if(vendorOrgLocation.getUser() != null) {
				pah.setApproverUserId(vendorOrgLocation.getUser());
			}else {
				pah.setApproverUserId(orgspoc.getVendorUser());
			}
			
			
			pah.setLevel(1);
			pah.setStatus("1");
			projectApproverHierarchyList.add(pah);
			pclients.setProjectApproverHierarchyList(projectApproverHierarchyList);
			
			projectClientService.storeProjectClients(pclients);
		}
		
		ProjectMembers pm = null;
		pm = projectMembersService.getProjectMemberByCandidateIdAndProjectIdAndOrgLocationId(vendorCandidate.getId(), projectMembers.getProjects().getId(), vendorOrgLocation.getId());
		if(pm == null) {
			pm = new ProjectMembers();
			pm.setProjects(projectMembers.getProjects());
			pm.setProjectMemberCandidteId(vendorCandidate);
			pm.setMasterCandidateId(vendorCandidate.getMasterCandidateId());
			if(vendorCandidate.getConsultantUser() != null){
				Employees emp = employeesService.getEmployeeByUserIdAndOrgLocationsId(vendorCandidate.getConsultantUser().getId(), vendorOrgLocation.getId());
				projectMembers.setEmployee(emp);
			}
			
			pm.setProjectAssignedDate(new Date());
			pm.setProjects(projectMembers.getProjects());
			pm.setStatus("1");
			pm.setCreatedBy(user.getUserCode());
			pm.setUpdatedBy(user.getUserCode());
			pm.setCreatedDate(new Date());
			pm.setUpdatedDate(new Date());
			pm.setProjectClientsId(pclients);
			projectMembersService.saveProjectMember(pm);
			
			ProjectMembersHistory projectMembersHistory = new ProjectMembersHistory();
			projectMembersHistory.setProjectMemberId(pm);
			projectMembersHistory.setProjectAssignedDate(new Date());
			projectMembersHistory.setStatus("OPEN");
			projectMembersHistory.setCreatedBy(user.getUserCode());
			projectMembersHistory.setUpdatedBy(user.getUserCode());
			projectMembersHistory.setCreatedDate(new Date());
			projectMembersHistory.setUpdatedDate(new Date());
			projectMembersHistoryService.updateProjectMembersHistory(projectMembersHistory);
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void removeVendorProject(OrgRelations currentVendorOrgRelation,User user,OrgLocations orgLocations,Candidate candidate) {
		
		try {
		 List<Long> pclientsList = projectClientService.getProjectClientsByRelationId(currentVendorOrgRelation.getId());
		 if(pclientsList != null && pclientsList.size() > 0) {
			 OrgRelations orel = orgRelationsService.getOrRelationsById(currentVendorOrgRelation.getId());
			 Candidate vendorCand  = null;
			 if(orel.getLocationId().getId().equals(orgLocations.getId())){
				  vendorCand = candidateService.findByCandidateEmail(candidate.getEmail(), orel.getResponderLocationId().getId());
			  }else if(orel.getResponderLocationId().getId().equals(orgLocations.getId())){
				  vendorCand = candidateService.findByCandidateEmail(candidate.getEmail(), orel.getLocationId().getId());
			  }
			 
			 List<ProjectMembers> projectMembersList = projectMembersService.getProjectMembersListByCandidateIdAndProjectClientId(vendorCand.getId(), pclientsList);
			 if(projectMembersList != null && !projectMembersList.isEmpty()) {
				 for(ProjectMembers pm : projectMembersList) {
					 pm.setStatus("0");
					 pm.setIsManuallyTerminated(Primary.Yes);
					 pm.setUpdatedDate(new Date());
					 pm.setUpdatedBy(user.getUserCode());
					 projectMembersService.saveProjectMember(pm);
					 
					 ProjectMembersHistory pmh = projectMembersHistoryService.getProjectMembersAllHistoryByProjectMemberId(pm.getId());
					 if(pmh != null) {
						 pmh.setStatus("CLOSED");
						 pmh.setProjectTerminationDate(new Date());
						 pmh.setUpdatedDate(new Date());
						 pmh.setUpdatedBy(user.getUserCode());
						 projectMembersHistoryService.updateProjectMembersHistory(pmh);
					 }
				 }
			 }
		 }
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * to get the start date and end date of the particular project by projectId
	 * 
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	// to get a start date and end date of the project
	@RequestMapping(value = "/getStartAndEndOfProject", method = RequestMethod.POST)
	public @ResponseBody String getStartAndEndOfProject(
			@RequestParam(value = "projectId", required = false) Long projectId) throws Exception {
		String resultJson = null;

		Projects projects = projectsService.getProjectById(projectId);
		if (projects.getStartDate() != null) {
			projects.setStartDateStr(commonUtil.elkDateFormatter(projects.getStartDate()));
		}
		if (projects.getEndDate() != null) {
			projects.setEndDateStr(commonUtil.elkDateFormatter(projects.getEndDate()));
		}

		resultJson = toJSON(projects);
		return resultJson;
	}

	/**
	 * Redirectin for universal users list
	 * 
	 * @param searchValue
	 * @param request
	 * @return json
	 * @throws Exception
	 */
	@RequestMapping(value = "/getUniversalSearchUsersList", method = RequestMethod.GET)
	public @ResponseBody String getUniversalSearchUsersList(
			@RequestParam(value = "searchValue", required = false) String searchValue, HttpServletRequest request)
			throws Exception {
		String resultJson = null;
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		List<UserIndex> usersList = userService.getAllUniversalSearchUsersList(orgLocations, searchValue, user);
		resultJson = commonUtil.toJSON(usersList);
		return resultJson;

	}
	
	
	@RequestMapping(value = "/getUserListForConnections", method = RequestMethod.POST)
	public @ResponseBody List<ConnectionsDTO> getUserListForConnections(
			@RequestParam(value = "searchValue", required = false) String searchValue, 
			@RequestParam(value = "offset", required = false) Integer offset,HttpServletRequest request)
			throws Exception {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		List<ConnectionsDTO> userRoleDTOList = new ArrayList<>();
		userRoleDTOList = userRoleService.getUserListForConnections(searchValue, offset, user.getId());
		return userRoleDTOList;

	}
	
	@RequestMapping(value = "/getShiftList", method = RequestMethod.POST)
	public @ResponseBody List<Shift> getShiftList(HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<Shift> shifList = shiftService.getShiftList(orgLocations.getId());
		return shifList;

	}
	
	
	private String toJSON(Object obj) {
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