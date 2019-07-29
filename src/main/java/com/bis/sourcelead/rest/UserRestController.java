package com.bis.sourcelead.rest;

import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
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
import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.AddAssociateDTO;
import com.bis.sourcelead.dto.AddUserDTO;
import com.bis.sourcelead.dto.AssociateBalanceLeaveDTO;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.OrgRelationDTO;
import com.bis.sourcelead.dto.ProjectsDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.SingnatoryDTO;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.dto.UserGroupDTO;
import com.bis.sourcelead.elk.index.ProjectsIndex;
import com.bis.sourcelead.elk.index.helper.ProjectsIndexHelper;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.AssetAssign;
import com.bis.sourcelead.entity.AssociateDepartmentView;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateOffBoarding;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProbationHistory;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectMembersHistory;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Primary;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.AssetService;
import com.bis.sourcelead.service.AssociateBalanceLeaveService;
import com.bis.sourcelead.service.CandidateOffBoardingService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.ModulesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.ProbationHistoryService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RegistrationService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.TabsService;
import com.bis.sourcelead.service.UserGroupService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.timesheet.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.timesheet.service.TimesheetSubmissionsService;
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
	
	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;
	
	@Value("${pmsUrl}")
    private String pmsUrl;
	
	@Autowired
	RolesService roleService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	private ProjectsService projectsService;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
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
	private DepartmentService departmentService;
	
	@Autowired
	EmployeesService employeesService;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
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
	
	@Autowired
	ModulesService modulesService;
	
	@Autowired
	SourceleadActionsService sourceleadActionsService;
	
	@Autowired
	TabsService tabsService;
	
	@Autowired
	ProjectMembersService projectMembersService;
	
	@Autowired
	CandidateOffBoardingService candidateOffBoardingService;

	@Autowired
	AssetService assetService;
	
	/*@Autowired
	private Org org;*/
	
	@Autowired
	UserGroupService userGroupService;
	
	@Autowired
	AssociateBalanceLeaveService associateBalanceLeaveService;
	
	@Autowired
	RegistrationService registrationService;
	
	@Autowired
	private ProbationHistoryService probationHistoryService;
	
	
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
	public @ResponseBody List<CandidateDTO> getCandidatesList(
		   @RequestParam(value = "employeementType", required = false) String employeementType, HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		List<CandidateDTO> candidateList = candidateService.getAllActiveCandidatesListByOrglocationId(orgLocations.getId(),employeementType);
		return candidateList;
	}
	
	@RequestMapping(value = "/getDepartmentsList", method = RequestMethod.POST)
	public @ResponseBody String getDepartmentsList(
		   @RequestParam(value = "employeementType", required = false) String employeementType, HttpServletRequest request) throws Exception {
		String resultJson = null;
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		List<Department> departmentList = departmentService.getDepartmentByOrgLocationId(orgLocations.getId());
		resultJson = toJSON(departmentList);
		return resultJson;
	}
	
	@RequestMapping(value = "/getActiveAssociateDetails",  method = RequestMethod.POST)
    public @ResponseBody String getAssociateDetailsById(@RequestParam(value="candidateId", required=false) Long candidateId,HttpServletRequest request) throws Exception {
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 if(candidateId !=null){
		Candidate candidate   = candidateService.getCandidateById(candidateId);
		if(null != candidate.getOrgRelationId()){
			candidate.setVendorOrgRelationId(candidate.getOrgRelationId().getId().toString());
			if(candidate.getOrgRelationId().getResponderLocationId().getId().equals(orgLocations.getId())){
				candidate.setVendorName(candidate.getOrgRelationId().getLocationId().getOrg().getOrgName());	
			}
			if(candidate.getOrgRelationId().getLocationId().getId().equals(orgLocations.getId())){
				candidate.setVendorName(candidate.getOrgRelationId().getResponderLocationId().getOrg().getOrgName());	
			}
			
		}
		if(null != candidate.getSpocId()){
			StringBuilder contactName = new StringBuilder();
			candidate.setVendorSpocId(candidate.getSpocId().getId().toString());
			if(null != candidate.getSpocId().getClientUser().getLastName()){
				candidate.setVendorContactName(contactName.append(candidate.getSpocId().getVendorUser().getFirstName()).append(" ").append(candidate.getSpocId().getClientUser().getLastName()).toString());	
			}else{
				candidate.setVendorContactName(contactName.append(candidate.getSpocId().getVendorUser().getFirstName()).toString());
			}
			
		}
		if(candidate.getDateOfBirth() != null) {
			candidate.setDateofBirthString(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),candidate.getDateOfBirth()));
		}
		
		return commonUtil.toJSON(candidate);
		}
		return "success";
		
	}

	/**
	 * To Create user
	 * @param json
	 * @param request
	 * @return
	 * @throws Exception
	 */
	 @PreAuthorize("hasRole('ROLE_CREATE_ASSOCIATE')")
	 @RequestMapping(value = "/createUser" , method = RequestMethod.POST)
	public Response<AddUserDTO> createUser(AddUserDTO addUserDTO, CandidateDTO candidateDTO, HttpServletRequest request)
			throws Exception {
		 Response<AddUserDTO> resObj = new Response<>();
		 if(Validator.validate(addUserDTO) || Validator.validate(candidateDTO)){
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		addUserDTO.setDateFormat(orgLocations.getDateFormat());
		candidateDTO.setDateFormat(orgLocations.getDateFormat());
		Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);
		commonUtil.onboardCreationCandidateGeneratedIdUpdated(candidate,orgLocations);
		User savedUser = userService.storeUser(addUserDTO, candidateDTO, user, orgLocations, "CREATEUSER", "", new Org());
		SourceLeadSessionManager.setSessionAttribute(request, "userCreateMsg", "Success !!New associate created");
		addUserDTO.setUserId(String.valueOf(savedUser.getId()));
		addUserDTO.setCandidateId(savedUser.getCandidateId());
		resObj.setObject(addUserDTO);
		return resObj;
	}
	 else{
		 resObj.setResponse("DataMissing");
		 return resObj; //"DataMissing";
	 }
}
	 
	 
	 @PreAuthorize("hasRole('ROLE_CREATE_ASSOCIATE')")
	 @RequestMapping(value = "/createSingnatoryUser" , method = RequestMethod.POST)
	public Response<String> createUser(SingnatoryDTO singnatoryDTO,CandidateDTO candidateDTO, HttpServletRequest request)
			throws Exception {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Candidate candidate = (Candidate) SourceLeadSessionManager.getSessionAttribute(request, "candidate");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Response<String> response = new Response<>();
		try {
			if (user != null) {
				if (Validator.validate(singnatoryDTO)) {
					AddUserDTO addUserDTO = new AddUserDTO();
					addUserDTO.setDateFormat(orgLocations.getDateFormat());
					addUserDTO.setFirstName(singnatoryDTO.getFirstName());
					addUserDTO.setLastName(singnatoryDTO.getLastName());
					if (StringUtils.isNoneBlank(singnatoryDTO.getDesignation())) {
						addUserDTO.setDesignation(singnatoryDTO.getDesignation());
					}
					addUserDTO.setEmail(singnatoryDTO.getEmail());
					addUserDTO.setRoleType(singnatoryDTO.getRoleType());
					addUserDTO.setSsn(singnatoryDTO.getSsn());
					if (StringUtils.isNoneBlank(singnatoryDTO.getPhoneNumber())) {
						addUserDTO.setPhoneNumber(singnatoryDTO.getPhoneNumber());
					}
					candidateDTO.setDateFormat(orgLocations.getDateFormat());
					commonUtil.onboardCreationCandidateGeneratedIdUpdated(candidate, orgLocations);
					userService.storeUser(addUserDTO, candidateDTO, user, orgLocations, "CREATEUSER", "", new Org());
					SourceLeadSessionManager.setSessionAttribute(request, "userCreateMsg", "Success !!New associate created");
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				}
				else {
					response.setObject("DataMissing");
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			}
			else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
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
	 public @ResponseBody  UserDTO getUserByUserEmail(@RequestParam(value="EmailId", required=false) String EmailId, HttpServletRequest request) throws Exception {
		 	Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request,"org");
		 	User user = userService.findByEmail(EmailId);
		 	UserDTO userDTO = new UserDTO();
		 	if(user != null) {
		 		userDTO.setFirstName(user.getFirstName());
			 	userDTO.setLastName(user.getLastName());
			 	userDTO.setMobilePhone(user.getMobilePhone());
			 	Boolean flag = userRoleService.getIsGlobalManager(org.getId(),user.getId());
			 	if(flag == true) {
			 		userDTO.setIsGlobalUser(Constants.YES);
			 	}else {
			 		userDTO.setIsGlobalUser(Constants.NO);
			 	}
		 	}else {
		 		Candidate candidate = candidateService.getCandidateByEmail(EmailId);
		 		if(candidate != null) {
		 			userDTO.setFirstName(candidate.getFirstName());
				 	userDTO.setLastName(candidate.getLastName());
				 	userDTO.setMobilePhone(candidate.getMobilePhone());
		 		}
		 	}
			return userDTO;
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
		 
		 @RequestMapping(value = "/validateExistingEmailIdForUser" , method = RequestMethod.POST)
		 public @ResponseBody  Boolean validateExistingEmailIdForUser(@RequestParam(value="emailId", required=true) String emailId,@RequestParam(value="userId", required=false) Long userId, HttpServletRequest request) throws Exception {
			   
			    Boolean flag = false;
			    User user = userService.getUserById(userId);
			 	if(!user.getEmail().equalsIgnoreCase(emailId)) {
			 		User existingUser = userService.getUserByEmail(emailId);
			 		if(existingUser != null) {
			 			flag = true;
			 		}
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
		public @ResponseBody List<User> getApproversByUserId(
				@RequestParam(value = "currentApproverId", required = false) Long currentApproverId,
				HttpServletRequest request) throws Exception {
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
					"orgLocations");
			Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request,
					"org");
			List<User> usersList = new CopyOnWriteArrayList<>();
			List<User> userList = userService.getTabAccessOwnersByOrgLocationIdAndTabIdAndNoCurrentUser(currentApproverId,orgLocations.getId(),Long.valueOf(Constants.TAB_TIMESHEET_APPROVAL));
			List<User> globalUserList = userService.getTabAccessGlobalOwnersByOrgLocationIdAndTabIdAndNoCurrentUser(currentApproverId,org.getId(),Long.valueOf(Constants.TAB_TIMESHEET_APPROVAL));
			if(userList != null && !userList.isEmpty()) {
				usersList.addAll(userList);
			}
			if(globalUserList != null && !globalUserList.isEmpty()) {
				usersList.addAll(globalUserList);
			}
			
			
			HashMap<Long,User> userMap = new HashMap<>();
			for (User user : usersList) {
				User tempUser = userMap.get(user.getId());
				if(tempUser == null) {
					userMap.put(user.getId(),user);
				}else {
					usersList.remove(user);
					continue;
				}
				UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocations.getId());
				if(userRole != null){
					user.setUserType(userRole.getUserType());
				}else {
					UserRole primaryRole = userRoleService.getLoginUserPrimaryRole(user.getId());
					user.setUserType(primaryRole.getUserType());
				}
			}
			
			return usersList;
		}

		/**
		 * To get All Approvers
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/getAllApprovers")
		public @ResponseBody ResponseList<UserRole> getAllApprovers(HttpServletRequest request) throws Exception {
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			ResponseList<UserRole> responseList = new ResponseList<>();
			List<UserRole> userList = userRoleService.getAllActiveUsersListByOrgLocationId(orgLocations.getId());
			userList.forEach( ul -> {
			    if(StringUtils.isNotBlank(ul.getUser().getLastName())) {
			    	StringBuilder sb = new StringBuilder();
			    	sb.append(ul.getUser().getFirstName()).append(" ").append(ul.getUser().getLastName());
			    	ul.setUserName(sb.toString());
			    }else {
			    	ul.setUserName(ul.getUser().getFirstName());
			    }
			    ul.setUserId(ul.getUser().getId());
			});
			
			responseList.setData(userList);
			return responseList;

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
			  @RequestParam(value="orgId", required=false) Long orgId, @RequestParam(value="orgLocationId", required=false) Long orgLocationId,HttpServletRequest request) throws Exception {
		    
		   
		    User user = userService.getUserById(Long.parseLong(userId));
		   
		    Long loginUserId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
		    User loginUser = userService.getUserById(loginUserId);
		    
		    Long loginOrgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		    OrgLocations orgLocations = orgLocationService.getOrgLocations(loginOrgLocationId);
		    
		    //generate random password
		    String password = SourceLeadRandomCodeHelper.generateRandomPassword();
		    boolean isUserActive = false;
		    
		    
	 		if(user != null){
	 			isUserActive = userRoleService.isUserActive(user.getId());
	 			if(isUserActive == false){
	 				user.setPassword(sourceleadPasswordEncoder.encode(password));
	 			}
	 			userService.addUser(user);
	 			
	 			// mail should forward user when action done in sourcelead admin 
	 			if(orgId != null && orgLocationId != null){
	 			userService.sendEmailFromSourcelead(user,loginUser,password,orgLocationId,orgId);
	 			}else{
	 				userService.sendEmail(user,loginUser,password,orgLocations.getId());
	 			}
	 			if(Constants.NO.equalsIgnoreCase(user.getIsVerified())){
	 				registrationService.sendActivationEmail(user,orgLocations);	
	 			}
	 			/*
				 * Update USER DB data as ElasticSearch Documents
				 */
	 			  UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocations.getId());
	 			  if(null != userRole){
	 				 Employees employee = employeesService.getEmployeeByUserIdAndOrgLocationsId(userRole.getUser().getId(), orgLocations.getId());
	    			  if(null != employee){
	    				  userService.saveOrUpdateUserElkDocument(userRole,employee,user.getUserCode());
	    			  }
	    			  
	    			  userRole.setIsMailSent(Status.Y);
	    			  userRoleService.addUserRole(userRole);
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
					@RequestParam(value = "filterValue", required = false) String filterValue,@RequestParam(value = "orgLocationId", required = false) Long orgLocationId) throws Exception {

				List<ProjectMembers> allProjectsList = projectMembersService.getAllProjectsByUserIdAndStatus(userId, filterValue,orgLocationId);
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
					projectMembers.setCurrentBillRate(Double.parseDouble(employee.getRate()));
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
					@RequestParam(value = "projectMemberId", required = true) Long projectMemberId,
					@RequestParam(value = "projectId", required = true) Long projectId,
					@RequestParam(value = "reason", required = true) String reason,HttpServletRequest request) {
				   User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				   OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
							"orgLocations");
				   Integer updatedCount =  0;
				   try {
					   ProjectMembers pm = projectMembersService.getProjectMemberById(projectMemberId);
					   List<ProjectMembers> pmList = projectMembersService.getAllProjectMembersByMasterCandidateId(pm.getMasterCandidateId().getId(), projectId);
					   Projects projects = projectsService.getProjectById(projectId);
				  	 if(pmList != null && !pmList.isEmpty()) {
					   for(ProjectMembers projectMembers: pmList) {
					  		  String status = Constants.MEMBER_IN_ACTIVE;
						  	  projectMembers.setTerminateReason(reason);
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
										  commonUtil.terminatedMailToEmployee(user,pmh,projects.getOrgLocationsId());
									  }
									  
									  if(pmh.getProjectMemberId().getProjectClientsId().getOrgLocationsId().getId().equals(projects.getOrgLocationsId().getId())){
										  commonUtil.employeeTerminationMailToProjectManager(user,pmh,projects.getOrgLocationsId());
									  }else{
										  commonUtil.employeeTerminationMailToProjectManager(pmh.getProjectMemberId().getProjectClientsId().getOrgLocationsId().getUser(),pmh,pmh.getProjectMemberId().getProjectClientsId().getOrgLocationsId());
									  }
									  commonUtil.inactivateTimesheetSubmissionsBasedOnMemberIdAndMemberTerminationDate(projectMemberId,new Date());
									  sendEmailCount = sendEmailCount+1;
								  }
							  }
							
						      
								if(null != projects){
									Collection<ProjectMembers> projectMembersList = projectMembersService.getAllProjectMembersByProjectId(projectId);
									ProjectsIndex projectsIndex = projectsIndexHelper.getProjectsIndexFromProjects(projects, null, projectMembersList);
									//Elastic Search Document Saving
									sourceleadELKBaseIndexer.saveOrUpdateElkDocument(projectsIndex, Constants.ELK_PROJECT_INDEX, Constants.ELK_PROJECT_TYPE,projects.getId().toString());
								}
				  	  }
				  	 }
				    } catch (Exception e) {
				   }
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

				@RequestParam(value = "projectMemberId", required = false) Long projectMemberId,HttpServletRequest request) throws Exception {
					
			
					User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
					OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				
					ProjectMembers projectMembers =	projectMembersService.getProjectMemberById(projectMemberId);
					//SimpleDateFormat sdf = orgLocations.getLocFullFormDateFormatter();
					
					if (endDate != null && !"".equals(endDate) && !endDate.equalsIgnoreCase("--")) {
						ProjectMembersHistory pmh = projectMembersHistoryService.getProjectMembersOpenHistoryByProjectMemberId(projectMembers.getId());
						
						 //List<ProjectMembers> pmList = projectMembersService.getAllProjectMembersByMasterCandidateId(projectMembers.getMasterCandidateId().getId(), projectMembers.getProjects().getId());
						if(pmh == null){
							pmh = projectMembersHistoryService.getProjectMembersRecentClosedByProjectMemberId(projectMembers.getId());
						}
						Date endDate1=commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),endDate);
						if(pmh.getProjectTerminationDate()== null){
							/*projectMembersService.updateEndDateOfProjectMemberId(endDate1, projectMemberId);*/
							projectMembersHistoryService.updateEndDateOfProjectMemberId(endDate1, projectMemberId);
						}

						if (pmh.getProjectTerminationDate() != null && pmh.getProjectTerminationDate().compareTo(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),endDate)) != 0) {
							projectMembersHistoryService.updateEndDateOfProjectMemberId(endDate1, projectMemberId);
							
						}
						// mail forward to members inline end date edit
						projectEnddatechangetoEmployee(projectMembers,user, orgLocations, endDate );
						
						Date terminationDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),endDate);
						Date currentDate = new  Date();
						
						if(currentDate.after(terminationDate) && !currentDate.equals(terminationDate)){
							commonUtil.terminateProjectMemberByProjectMemberId(projectMemberId,Primary.Yes);
						}
						else{
							commonUtil.inactivateTimesheetSubmissionsBasedOnMemberIdAndMemberTerminationDate(projectMemberId, terminationDate);
						}
					}else{
						ProjectMembersHistory pmh = projectMembersHistoryService.getProjectMembersOpenHistoryByProjectMemberId(projectMembers.getId());
						if(pmh == null){
							pmh = projectMembersHistoryService.getProjectMembersRecentClosedByProjectMemberId(projectMembers.getId());
						}
						Date endDate1=null;
						if(pmh.getProjectTerminationDate()== null){
							/*projectMembersService.updateEndDateOfProjectMemberId(endDate1, projectMemberId);*/
							projectMembersHistoryService.updateEndDateOfProjectMemberId(endDate1, projectMemberId);
						}

						if (pmh.getProjectTerminationDate() != null) {
							projectMembersHistoryService.updateEndDateOfProjectMemberId(endDate1, projectMemberId);
							
						}
						// mail forward to members inline end date edit
						projectEnddatechangetoEmployee(projectMembers,user, orgLocations, endDate );
						
						Date terminationDate = null;
							commonUtil.inactivateTimesheetSubmissionsBasedOnMemberIdAndMemberTerminationDate(projectMemberId, terminationDate);
					}
					return "updated";
		       }
		
		
		 private void projectEnddatechangetoEmployee(ProjectMembers emp, User loginUser,  OrgLocations loginOrgLocation, String changedEndDate) throws Exception {
			 Projects projects = projectsService.getProjectById(emp.getProjects().getId());
			 //SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			 StringBuilder fromNameBuilder=new StringBuilder();
		     EmailMessage emailMessage = new EmailMessage();
			 emailMessage.setFirstName(emp.getEmployee().getUser().getFirstName());
			 emailMessage.setLastName(emp.getEmployee().getUser().getLastName());
			 emailMessage.setToEmail(emp.getEmployee().getUser().getEmail());
			 emailMessage.setFromEmail(loginUser.getEmail());
			 emailMessage.setCreatedBy(loginUser.getUserCode());
			 emailMessage.setFromName(fromNameBuilder.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
			 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
			 //setting the class filed values
			 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 
			 globalVarsMap.put("projectStartdate",commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),projects.getStartDate()));
			 globalVarsMap.put("projectEnddate",changedEndDate);
			 
			 if(loginUser.getFirstName() != null && loginUser.getLastName()!=null && !"".equalsIgnoreCase(loginUser.getFirstName()) && !"".equalsIgnoreCase(loginUser.getLastName())){
				 StringBuilder userBuild = new StringBuilder();
				 globalVarsMap.put("loginUser",userBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
				 }else{
					 globalVarsMap.put("loginUser",loginUser.getFirstName());
				}
			 if(emailMessage.getFirstName() != null && emailMessage.getLastName()!=null && !"".equalsIgnoreCase(emailMessage.getFirstName()) && !"".equalsIgnoreCase(emailMessage.getLastName())){
				 StringBuilder userBuild = new StringBuilder();
				 globalVarsMap.put("employeeName",userBuild.append(emailMessage.getFirstName()).append(" ").append(emailMessage.getLastName()).toString());
				 }else{
					 globalVarsMap.put("employeeName",emailMessage.getFirstName());
				}
			 globalVarsMap.put("projectName", projects.getProjectName());
			 globalVarsMap.put("loginOrgName",loginOrgLocation.getOrg().getOrgName());
			 
			 
			 //calling the sendEmail method of emailScenerioUtil
			 if(null != projects.getOrgLocationsId())
			 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,projects.getOrgLocationsId().getOrg(),projects.getOrgLocationsId(),Constants.EDITPROJECT_MAIL_TEAMMEMBER,new ArrayList<EmailAttachment>());
			 
			//calling the sendNotification method of emailScenerioUtil
			EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.EDITPROJECT_MAIL_TEAMMEMBER,Constants.ENTITY_TYPE_NOTIFICATION);
			String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
			notificationScenario.setBody(notificationBody);
			//String url = null;
			String actionLink = "";
			emailScenerioUtil.sendNotification(actionLink, loginUser, emp.getEmployee().getUser(), loginOrgLocation, notificationScenario,new HashMap<String,String>());
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
		@ResponseBody String saveReAssignProject(ProjectsDTO projectsDTO,
				@RequestParam(value = "projectMemberId", required = false) Long projectMemberId, HttpServletRequest request)
						throws Exception {
			
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			 try {
			  	  ProjectMembers pm =	projectMembersService.getProjectMemberById(projectMemberId);
			  	  List<ProjectMembers> pmList = projectMembersService.getAllProjectMembersListByMasterCandidateId(pm.getMasterCandidateId().getId(), pm.getProjects().getId());
			  	  
			  	 int sendEmailCount = 1;
			  	 if(pmList != null && !pmList.isEmpty()) {
					   for(ProjectMembers projectMembers: pmList) {
						    String status = Constants.MEMBER_ACTIVE;
							  projectMembers.setUpdatedDate(new Date());
							  projectMembers.setUpdatedBy(user.getUserCode());
							  projectMembers.setStatus(status);
							  projectMembers =  projectMembersService.saveProjectMember(projectMembers);
							  projectMembers = projectMembersService.getProjectMemberById(projectMembers.getId());
							  ProjectMembersHistory pmh = new  ProjectMembersHistory();  
							  pmh.setProjectMemberId(projectMembers);
					  		  pmh.setStatus(Constants.PROJECT_MEMBER_HISTORY_OPEN);
				    	      if(!isNullOrEmpty(projectsDTO.getReStartDate())){
				    	    	  Date startDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),projectsDTO.getReStartDate());
								  pmh.setProjectAssignedDate(startDate);
				    	      }
				    	      if(!isNullOrEmpty(projectsDTO.getReEndDate())){
				    	    	  Date endDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),projectsDTO.getReEndDate());
								  pmh.setProjectTerminationDate(endDate);
				    	      }
				    	      pmh.setCreatedDate(commonUtil.currentDate());
				    	    	if(user.getUserCode() != null){
				    	    		pmh.setCreatedBy(user.getUserCode());	
				    	    	}
				    	    	
							  pmh.setUpdatedDate(commonUtil.currentDate());
							  pmh.setProjectTerminationDate(null);
							  pmh.setUpdatedBy(user.getUserCode());
							  pmh =  projectMembersHistoryService.updateProjectMembersHistory(pmh);
							  if(sendEmailCount == 1) {
								  reAssignMailToEmployee(user,pmh,orgLocations);
							  }
							  sendEmailCount = sendEmailCount+1;
						  }
							
				  	 }
			  	  
			 }catch (Exception e) {
				e.printStackTrace();
			}
			
			return "success";
		}
		
		
		
		private void reAssignMailToEmployee(User loginUser, ProjectMembersHistory pmh, OrgLocations orgLocations) throws Exception {

			 User user=null;
			 if(null != pmh.getProjectMemberId()){
			 user = userService.getUserByUserCode(pmh.getProjectMemberId().getCreatedBy());
			 }
			 
			 EmailMessage emailMessage = new EmailMessage();
			 emailMessage.setFirstName(pmh.getProjectMemberId().getProjectMemberCandidteId().getFirstName());
			 emailMessage.setLastName(pmh.getProjectMemberId().getProjectMemberCandidteId().getLastName());
			 emailMessage.setToEmail(pmh.getProjectMemberId().getProjectMemberCandidteId().getEmail());
			 emailMessage.setFromEmail(loginUser.getEmail());
			 emailMessage.setCreatedBy(loginUser.getUserCode());
			 
			// StringBuilder msgBuild = new StringBuilder();
			// emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
			// emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
			//setting the class filed values
			//SimpleDateFormat dateFormat=orgLocations.getLocFullFormDateFormatter();
			 Map<String, String> globalVarsMap = new HashMap<String, String>();    
			 String startDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),pmh.getProjectAssignedDate());
			 String endDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),pmh.getProjectTerminationDate());
			
			 
			 StringBuilder employeeName = new StringBuilder();
		 	 if(StringUtils.isNotBlank(pmh.getProjectMemberId().getProjectMemberCandidteId().getLastName())) {
		 		 employeeName = employeeName.append(pmh.getProjectMemberId().getProjectMemberCandidteId().getFirstName()).append(" ").append(pmh.getProjectMemberId().getProjectMemberCandidteId().getLastName());
		 	 }else {
		 		 employeeName = employeeName.append(pmh.getProjectMemberId().getProjectMemberCandidteId().getFirstName());
		  		 }
		  		 
		 	 globalVarsMap.put("EmployeeName", employeeName.toString());
			 
			 
			 if(null != pmh.getProjectMemberId().getProjects()){
				 if(null != pmh.getProjectMemberId().getProjects().getProjectName() && !"".equalsIgnoreCase(pmh.getProjectMemberId().getProjects().getProjectName())){
				 globalVarsMap.put("Project Name", pmh.getProjectMemberId().getProjects().getProjectName());
				}else{
					 globalVarsMap.put("Project Name", "--");
				 }
			 }
			 
			 if(null != pmh.getProjectAssignedDate()){
				 globalVarsMap.put("ProjectStartDate",startDate);
			 }else{
				 globalVarsMap.put("ProjectStartDate", "--");
			 }
			 
			 if(null != pmh.getProjectTerminationDate()){
				 globalVarsMap.put("ProjectEndDate",endDate);
			 }else{
				 globalVarsMap.put("ProjectEndDate", "--");
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
			 StringBuilder Locations = new StringBuilder();
			 if(orgLocations.getState() !=null && orgLocations.getState() !="undefined" && orgLocations.getCity() !=null && orgLocations.getCity() !="undefined"){
			 globalVarsMap.put("location",Locations.append(orgLocations.getCity()).append(",").append(orgLocations.getState()).append(",").append(orgLocations.getCountry()).toString());
			 }
			 else{
				 
				 globalVarsMap.put("location", orgLocations.getCountry()); 
			 }
			 /*if (null != orgLocations.getOrg()) {
			  * 
				 globalVarsMap.put("LoginOrg", orgLocations.getOrg().getOrgName());
				 } else {
					 globalVarsMap.put("LoginOrg", "--");
				}*/
			 
			 //getting the current EmailScenario by EmailScenario unique name and entity type
			 //using same migration which has been already used in updatedRequirementMailToManager method
			 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.EMPLOYEE_REASSIGN,Constants.ENTITY_TYPE_NOTIFICATION);
			 
			 String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
			 notificationScenario.setBody(notificationbody);
			 
			 if(pmh.getProjectMemberId().getProjectMemberCandidteId().getConsultantUser() != null) {
	 			 User toUser = pmh.getProjectMemberId().getProjectMemberCandidteId().getConsultantUser();
	 			 StringBuilder notificationLink = new StringBuilder();
				 String actionLink="";
			     actionLink = notificationLink.append(pmsUrl).append("/").append("projectSummary?projectUUID="+pmh.getProjectMemberId().getProjects().getProjectUUID()+"&projectClientUUID="+pmh.getProjectMemberId().getProjectClientUUID()).toString();
			     emailScenerioUtil.sendNotification(actionLink, loginUser,toUser, pmh.getProjectMemberId().getProjectMemberCandidteId().getOrgLocationId(), notificationScenario,new HashMap<String,String>());
			  
 		       }
			 
			 //calling the sendEmail method of emailScenerioUtil
			 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.EMPLOYEE_REASSIGN,new ArrayList<EmailAttachment>());

		}

		/**
		 * 
		 * @param userID
		 * @return
		 * @throws Exception
		 */
		@PreAuthorize("hasAnyRole('ROLE_VIEW_DELETE_ASSOCIATE','ROLE_CREATE_ASSOCIATE')")
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

					Candidate candidate = candidateService.getCandidateByConsultantUserId(Long.parseLong(userID),orgLocations.getId());
					if(candidate != null){
						candidate.setIsAssociate("Y");
						candidateService.saveOrUpdateCandidate(candidate);
						candidateService.saveOrUpdateCandidateElkDocument(candidate);
							
					}
					
					/*
    				 * Saving USER DB data as ElasticSearch Documents
    				 */
        			  Employees employee = employeesService.getEmployeeByUserIdAndOrgLocationsId(userRole.getUser().getId(), orgLocations.getId());
        			  if(null != employee){
        				  userService.saveOrUpdateUserElkDocument(userRole,employee,user.getUserCode());
        			  }
        			  
        			  
        			  CandidateOffBoarding candidateOffBoarding =  candidateOffBoardingService.getCandidateOffBoardingByUserIdAndLocationId(Long.parseLong(userID), orgLocations.getId());
        			  if(candidateOffBoarding != null){
        				  candidateOffBoarding.setStatus(0);
        				  candidateOffBoardingService.saveCandidateOffBoarding(candidateOffBoarding);
        			  }
				}
                return "updated";
         }
		
		@RequestMapping(value = "/getVendorsByOrgLocationId")
		public List<OrgRelationDTO> getVendorsByOrgLocationId(
				HttpServletRequest request) throws Exception {
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			List<OrgRelationDTO> vendorList = orgRelationsService.getAllVendorsListOrgLocationId(orgLocations.getId());
			return vendorList;

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
		
		
		
		@RequestMapping(value = "/getAssociateReportingManager", method = RequestMethod.POST)
		public @ResponseBody List<User> getAssociateReportingManager(
				@RequestParam(value = "role", required = false) Long roleId, HttpServletRequest request) throws Exception {
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request,"org");
			List<User> reportingManagersList = new CopyOnWriteArrayList<User>();
			
			List<User> userList = userService.getReportingManagersByUserRolesAndOrgLocationId(roleId, orgLocations.getId());
			List<User> globalUserList = userService.getGlobalReportingManagersUsersByOrgId(roleId, org.getId());
			if(userList != null && !userList.isEmpty()) {
				reportingManagersList.addAll(userList);
			}
			
			if(globalUserList != null && !globalUserList.isEmpty()) {
				reportingManagersList.addAll(globalUserList);
			}
			HashMap<Long,User> userMap = new HashMap<>();
			for (User user : reportingManagersList) {
				User tempUser = userMap.get(user.getId());
				if(tempUser == null) {
					userMap.put(user.getId(),user);
				}else {
					reportingManagersList.remove(user);
					continue;
				}
				UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocations.getId());
				if(userRole != null){
					user.setUserType(userRole.getUserType());
				}else {
					UserRole primaryRole = userRoleService.getLoginUserPrimaryRole(user.getId());
					user.setUserType(primaryRole.getUserType());
				}
			}
			return reportingManagersList;
		}
		
		
		@RequestMapping(value = "/getAssociateResourceManager", method = RequestMethod.POST)
		public @ResponseBody List<UserDTO> getAssociateResourceManager(
				 HttpServletRequest request) throws Exception {
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request,"org");
			List<UserDTO> resourceManagersDTOList = new ArrayList<>();
			
			List<User> userList = userService.getResourceManagersByOrgLocationIdAndTabId(orgLocations.getId(),Long.valueOf(Constants.TAB_USER));
			
			for (User user : userList) {
				UserDTO userDTO = new UserDTO();
				userDTO.setFullName(user.getFullName());
				userDTO.setId(user.getId());
				UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocations.getId());
				userDTO.setUserType(userRole.getUserType());
				resourceManagersDTOList.add(userDTO);
				
			}
			return resourceManagersDTOList;
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
	 
	 
	/**
	 * To get the assigned projects list for user based on userId and
	 * OrgLocationId
	 * 
	 * @param userId
	 * @param orgLocationId
	 * @return json object
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getAssignedProjectsListForUser",
			"/rest/getAssignedProjectsListForUser" }, method = RequestMethod.GET)
	public @ResponseBody List<ProjectMembers> getAssignedProjectsListForUser(@RequestParam(value = "userId", required = false) Long userId, @RequestParam(value = "orgLocationId", required = false) Long orgLocationId,@RequestParam(value = "fromType", required = false) String fromType) throws Exception
	{
		List<ProjectMembers> projectMembersList = projectMembersService.getAllAssignedProjectsForUser(userId, orgLocationId,fromType);
		return projectMembersList;
	}
	
	@RequestMapping(value =  "/getUserBasedAssetManagementList", method = RequestMethod.GET)
	public @ResponseBody List<AssetAssign> getUserBasedAssetManagementList(@RequestParam(value = "userId", required = false) Long userId, @RequestParam(value = "orgLocationId", required = false) Long orgLocationId) throws Exception
	{
	List<AssetAssign> assementList = assetService.getUserBasedAssetManagementListandOrgLocation(orgLocationId, userId);
	return assementList;
	}
	
	 @RequestMapping(value = "/refreshRoleList", method = RequestMethod.POST)
		public @ResponseBody List<Roles> refreshRoleList(HttpServletRequest request, Long roleHierarchyId) throws Exception {
		  roleHierarchyId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "roleHierarchyId");
		  	Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
	    	OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    	List<Roles> rolesList = rolesService.getUserRolesListByModuleAndLoginUserRoleHierarchy(org,orgLocations.getId(),roleHierarchyId);
            return 	rolesList;	   
		    
		   
		} 
	 
	 
	 
	 @RequestMapping(value = "/getOrgLocationAssociatesList", method = RequestMethod.GET)
	 public @ResponseBody ResponseList<CandidateDTO>  getOrgLocationAssociatesList(CandidateDTO candidateDTO,HttpServletRequest request) throws Exception{ 
		 
		
		List<Long> roleIds = null; 
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");		
		
		String[] tableColumns = {"ischeck","name", "email","role", "rate", "manager", "resume"};
	    int sortColumnIndex = 1; //by default sorting using updated date
		String sortColumnOrder = "desc"; //by default referContactsDTO order
		if(candidateDTO.getOrder() != null && !candidateDTO.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(candidateDTO.getOrder().get(0).get("column"));
			sortColumnOrder = candidateDTO.getOrder().get(0).get("dir");
		}
		candidateDTO.setSortColumn(tableColumns[sortColumnIndex]);
		candidateDTO.setSortOrder(sortColumnOrder);
		
		if(!isNullOrEmpty(candidateDTO.getRoles())){
			roleIds = new ArrayList<Long>();
			 String[] rolesList = candidateDTO.getRoles().split(",");
			 for(int i=0; i < rolesList.length; i++){
				 roleIds.add(Long.valueOf(rolesList[i]));
			 }
			 
		}
		
		List<Long> associateIds = null; 
		if(!isNullOrEmpty(candidateDTO.getSelectedAssociates())){
			associateIds = new ArrayList<Long>();
			 String[] associateIdsList = candidateDTO.getSelectedAssociates().split(",");
			 for(int i=0; i < associateIdsList.length; i++){
				 associateIds.add(Long.valueOf(associateIdsList[i]));
			 }
		}
		
		 
		ResponseList<CandidateDTO> candidateDTOList = candidateService.getLocationCandidatesList(candidateDTO, candidateDTO.getOrgLocationId(), roleIds, associateIds, orgLocations.getId());
		
		return candidateDTOList;
	  }
	 
	 
	 
	 @PreAuthorize("hasRole('ROLE_CREATE_ASSOCIATE')")
	 @RequestMapping(value = "/saveLocationAssociate" , method = RequestMethod.POST)
	 public String saveLocationAssociate(AddAssociateDTO addAssociateDTO, HttpServletRequest request)
			throws Exception {
		   Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		   Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
           User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
           OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
	       candidateService.saveOrglocationAsssociate(addAssociateDTO, loginUser, orgLocations, org); 
         
           return "success";
	 
	 }
	 
	 /**
	  * Get all department users by Dept Id
	  * @param departmentId
	  * @param request
	  * @return List<AssociateDepartmentView> 
	  * @throws Exception
	  */
	 @RequestMapping(value = "/getUsersByDepartment" , method = RequestMethod.GET)
	 public List<AssociateDepartmentView> getUsersByDepartment(@RequestParam(value = "departmentId", required = false) String departmentId,HttpServletRequest request)
			throws Exception {
		 long lStartTime = System.currentTimeMillis();  
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 List<AssociateDepartmentView>  viewList = userRoleService.getUserRolesByDepartmentId(departmentId,orgLocations.getId());
		 long lEndTime = System.currentTimeMillis();
		 long totalTime = lEndTime - lStartTime;
		 logger.debug("Response time is "+totalTime);
		 return viewList;
	 }
 
	 
	 @RequestMapping(value = "/getUserGroupByGroupUUID" , method = RequestMethod.GET)
	 public Response<UserGroupDTO> getUserGroupByGroupUUID(UserGroupDTO userGroupDTO,HttpServletRequest request)throws Exception {
		  Response<UserGroupDTO> userGroupDto =  userGroupService.findUserGroupById(userGroupDTO.getGroupUUID());
		  return userGroupDto;
	 }
	 

	 @RequestMapping(value = "/deleteUserGroupByUUID" , method = RequestMethod.GET)
	 public Response<UserGroupDTO> deleteUserGroupByUUID(UserGroupDTO userGroupDTO,HttpServletRequest request)throws Exception {
		  User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		  Response<UserGroupDTO> userGroupDto =  userGroupService.deleteUserGroupByUUID(userGroupDTO.getGroupUUID(),loginUser);
		  return userGroupDto;
	 }

	 @RequestMapping(value = "/getLinkedUserDetailsByUserName" , method = RequestMethod.POST)
	 public String getLinkedUserDetailsByUserName(UserDTO userDTO, HttpServletRequest request)
			throws Exception {
		 Long loginUserId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
	     User loginUser = userService.getUserById(loginUserId);
	       String result = "";
           if(userDTO.getLinkedUserName().equalsIgnoreCase(loginUser.getUserName())) {
        	   result = "invalid";
           }else {
        	   User user = userService.findByUserName(userDTO.getLinkedUserName());
        	   if(user != null) {
        		   if(user.getLinkUserId() == null) {
        			   result = "success";  
        		   }else {
        			   result = "linked";  
        		   }
        		  
        	   }else {
        		   result = "Not Registered";  
        	   }
           }
           return result;
	 
	 }
	 
	 @RequestMapping(value = "/validateLinkedUserPasswordByUserName" , method = RequestMethod.POST)
	 public String validateLinkedUserPasswordByUserName(UserDTO userDTO, HttpServletRequest request)
			throws Exception {
	       String result = "";
         
	       if(StringUtils.isNotBlank(userDTO.getLinkedUserName()) && StringUtils.isNotBlank(userDTO.getLinkedUserPwd())) {
	    	   User user = userService.findByUserName(userDTO.getLinkedUserName());
	    	   
	    	   if (sourceleadPasswordEncoder.matches(userDTO.getLinkedUserPwd(), user.getPassword())) {
	    		   result =  "valid";
				} else {
					result =  "invalid";
				}
	       }
	       
           return result;
	 
	 }
	 
	
	 
	 @RequestMapping(value = "/linkUser" , method = RequestMethod.POST)
	 public Response<String> linkUser(UserDTO userDTO, HttpServletRequest request) throws Exception{
	
	 Long loginUserId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
     User user = userService.getUserById(loginUserId);
	    
	 Response<String> response = new Response<>();
		try {
			if(user != null) {
				
				 if(StringUtils.isNotBlank(userDTO.getLinkedUserName()) && StringUtils.isNotBlank(userDTO.getLinkedUserPwd())) {
					User linkedUser =  userService.findByUserName(userDTO.getLinkedUserName());
					linkedUser.setLinkUserId(user);
					userService.addUser(linkedUser);
					
				    List<Long> accountUserList = new ArrayList<>();
					List<Long> userIdsList = userService.getUserIdsListByLinkedUserId(user.getId());
					if(userIdsList != null && !userIdsList.isEmpty()) {
						for(Long userId : userIdsList) {
							List<Long> linkedUserIdsList = userService.getUserIdsListByLinkedUserId(userId);
							accountUserList.addAll(linkedUserIdsList);
						}
					}
					userIdsList.addAll(accountUserList);
					userIdsList.add(user.getId());
					if(user.getLinkUserId() != null) {
						userIdsList.add(user.getLinkUserId().getId());
					}
					
					List<UserRole> userSpocRolesList = userRoleService.getUserAccountsListByUserId(userIdsList);
					SourceLeadSessionManager.setSessionAttribute(request, "userSpocRolesList", userSpocRolesList);
					SourceLeadSessionManager.setSessionAttribute(request, "userSpocRolesListCount", userSpocRolesList.size());
					
					response.setResponse(Result.SUCCESS.name());
				    response.setResponseMessage(Result.SUCCESS.getValue());
					
				 }else {
					    response.setObject("DataMissing");
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
		}
		
		return response;
	 
	 }
	 
	 
	 
	 
	 
	 @RequestMapping(value = "/removeLinkedUser" , method = RequestMethod.POST)
	 public Response<String> removeLinkedUser(@RequestParam(value="userCode", required=true) String userCode, HttpServletRequest request) throws Exception{
		 Long loginUserId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
	     User user = userService.getUserById(loginUserId);
	  Response<String> response = new Response<>();
		try {
			if(user != null) {
				
				 if(StringUtils.isNotBlank(userCode)) {
					User linkedUser =  userService.getUserByUserCode(userCode);
					linkedUser.setLinkUserId(null);
					userService.addUser(linkedUser);
				
				    List<Long> accountUserList = new ArrayList<>();
					List<Long> userIdsList = userService.getUserIdsListByLinkedUserId(user.getId());
					if(userIdsList != null && !userIdsList.isEmpty()) {
						for(Long userId : userIdsList) {
							List<Long> linkedUserIdsList = userService.getUserIdsListByLinkedUserId(userId);
							accountUserList.addAll(linkedUserIdsList);
						}
					}
					userIdsList.addAll(accountUserList);
					userIdsList.add(user.getId());
					if(user.getLinkUserId() != null) {
						userIdsList.add(user.getLinkUserId().getId());
					}
					
					List<UserRole> userSpocRolesList = userRoleService.getUserAccountsListByUserId(userIdsList);
					SourceLeadSessionManager.setSessionAttribute(request, "userSpocRolesList", userSpocRolesList);
					SourceLeadSessionManager.setSessionAttribute(request, "userSpocRolesListCount", userSpocRolesList.size());
					
					response.setResponse(Result.SUCCESS.name());
				    response.setResponseMessage(Result.SUCCESS.getValue());
				    
				 }else {
					    response.setObject("DataMissing");
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
		}
		
		return response;
	 
	 }
	 
	 
	 
	 /**
	  * Save Associate Balance Leaves 
	  * @param associateBalanceLeaveDTO
	  * @param request
	  * @return Response<AssociateBalanceLeaveDTO>
	  * @throws Exception
	  */
	 
	 @RequestMapping(value = "/addAssociateLeave" , method = RequestMethod.POST)
	 public Response<AssociateBalanceLeaveDTO> addAssociateLeave(AssociateBalanceLeaveDTO associateBalanceLeaveDTO, HttpServletRequest request)throws Exception {
		   OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
           User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
           Response<AssociateBalanceLeaveDTO> resObj = associateBalanceLeaveService.addAssociateLeave(associateBalanceLeaveDTO, loginUser, orgLocations); 
           return resObj;
	 
	 }
	 
	/**
	 * Get Assciate Balance Leaves By user Id
	 * 
	 * @param userId
	 * @param request
	 * @return ResponseList<AssociateBalanceLeaveDTO>
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAssociateBalanceLeavesByUserId", method = RequestMethod.GET)
	public ResponseList<AssociateBalanceLeaveDTO> getAssociateBalanceLeavesByUserId(@RequestParam(value = "userId", required = false) Long userId, HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		orgLocations.setOrgId(org.getId());
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		ResponseList<AssociateBalanceLeaveDTO> response = new ResponseList();
		try {
			List<AssociateBalanceLeaveDTO> list = associateBalanceLeaveService.getAssociateBalanceLeavesByUserId(userId,orgLocations,loginUser);
			response.setData(list);
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			e.printStackTrace();
		}
		return response;
	}

	
	
	/**
	 * To Update Balance Leaves By user Id
	 * 
	 * @param userId
	 * @param request
	 * @return ResponseList<AssociateBalanceLeaveDTO>
	 * @throws Exception
	 */
	@RequestMapping(value = "/editAssBalLeave", method = RequestMethod.PUT)
	public boolean editAssBalLeave(@RequestParam(value = "assoBalLeaveUUID", required = false) String assoBalLeaveUUID, 
								   @RequestParam(value = "totalLeaves", required = false) String totalLeaves,HttpServletRequest request) throws Exception
	{
		boolean isUpdated = associateBalanceLeaveService.getAssociateBalanceByUUID(assoBalLeaveUUID,totalLeaves);
		return isUpdated;
	}
	
	@RequestMapping(value = "/getConfirmationDateBasedOnJoiningDate", method = RequestMethod.GET)
	public @ResponseBody String getConfirmationDateBasedOnJoiningDate(@RequestParam(value = "joiningDate", required = false) String joiningDate, @RequestParam(value = "probationperiod", required = false) String probationperiod, HttpServletRequest request) throws Exception
	{
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		SimpleDateFormat formatter = orgLocations.getLocFullFormDateFormatter();
		String[] frequency = probationperiod.split(" ");
		String confirmationDate = null;
		try {
			
			Date date = formatter.parse(joiningDate);
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DATE, Integer.parseInt(frequency[0]));
			confirmationDate = formatter.format(c.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return confirmationDate;
	}
	
	
	
	@RequestMapping(value = "/confirmProbation", method = RequestMethod.POST)
	public void confirmProbation(@RequestParam(value = "userId", required = false) Long userId,HttpServletRequest request) throws Exception
	{
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Employees emp = employeesService.getEmployeeByUserAndOrgLocationId(userId, orgLocations.getId());
		
		if(emp != null) {
			String previousStatus = emp.getProbationStatus();
			emp.setProbationStatus(Constants.CONFIRMED);
			emp.setUpdatedDate(new Date());
			emp.setUpdatedBy(loginUser.getUserCode());
			employeesService.updateEmployee(emp);
			
			ProbationHistory probationHistory = new ProbationHistory();
			probationHistory.setEmployeeId(emp);
			probationHistory.setDateOfJoin(emp.getHireDate());
			probationHistory.setProbationPeriod(emp.getProbationPeriod());
			probationHistory.setFromStatus(previousStatus);
			probationHistory.setToStatus(Constants.CONFIRMED);
			probationHistory.setActualConfirmationDate(emp.getConfirmationDate());
			probationHistory.setConfirmedDate(new Date());
			probationHistory.setStatus(1);
			probationHistory.setUuid(SourceLeadRandomCodeHelper.generateUUID());
			probationHistory.setCreatedDate(new Date());
			probationHistory.setCreatedBy(loginUser.getId());
			probationHistory.setUpdatedDate(new Date());
			probationHistory.setUpdatedBy(loginUser.getId());
			probationHistoryService.saveProbationHistory(probationHistory);
			
		}
	}
	
	@RequestMapping(value = "/sendMailConfirmationToAssociate", method = RequestMethod.POST)
	public void sendMailConfirmationToAssociate(@RequestParam(value = "userId", required = false) Long userId,
			@RequestParam(value = "confirmationDate", required = false) String confirmationDate,
			@RequestParam(value = "designation", required = false) String designation,HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User fromUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		User toUser = userService.getUserByIdAndOrgLocationsId(userId, orgLocations.getId());
		userService.sendEmployemntConfirmationMailToAssocaite(fromUser,toUser,orgLocations,confirmationDate,designation);
	}
	

	@RequestMapping(value = "/validateSSNDuplicate", method = RequestMethod.POST)
	public boolean validateSSNDuplicate(@RequestParam(value = "ssnNumber", required = false) String ssnNumber,@RequestParam(value = "userId",required = false) String userId, HttpServletRequest request) throws Exception
	{
		boolean isFound = false;
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = userService.validateSSNDuplicate(ssnNumber,orgLocations.getId(),userId);
		if(user != null) {
			isFound = true;
		}
		return isFound;
		
	}
	
	 @RequestMapping(value = "/getOrgLocationRolesList", method = RequestMethod.GET)
	 public @ResponseBody List<Roles>  getOrgLocationRolesList(@RequestParam(value="orgLocations", required=false) String orgLocations) throws Exception{ 
		 
		 List<Roles> rolesList = roleService.getRoles(Long.valueOf(orgLocations));
		 return rolesList;
	  }
}
