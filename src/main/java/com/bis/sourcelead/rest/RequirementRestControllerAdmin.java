package com.bis.sourcelead.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Writer;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.RequirementDao;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.JobFairDTO;
import com.bis.sourcelead.dto.RequirementDTO;
import com.bis.sourcelead.dto.RequirementSubmissionsCountDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.setup.RequirementElkUtil;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CombinedList;
import com.bis.sourcelead.entity.JobShareCandidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.RecommendedCustomizeScore;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementApproverHierarchy;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.RequirementSubmissionTemplateFields;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.HIERARCHY_TYPE;
import com.bis.sourcelead.enums.REQUIREMENT_APPROVER_HIERARCHY_STATUS;
import com.bis.sourcelead.enums.REQUIREMENT_SUBMISSION_STATUS;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.JobShareService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RecommendationsService;
import com.bis.sourcelead.service.RecommendedCustomizeScoreService;
import com.bis.sourcelead.service.RequirementApproverHierarchyService;
import com.bis.sourcelead.service.RequirementAssignToService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.RequirementSubmissionTemplateFieldsService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.SourceleadRolesService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.PdfUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

/**
 * Handles requests for the Requirement service.
 * 
 * @author Prasad K
 * @date 13th Sept 2016
 *
 */

@RestController
@RequestMapping("/app")
public class RequirementRestControllerAdmin {

	private static final Logger logger = LoggerFactory.getLogger(RequirementRestControllerAdmin.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Value("${static.path}")
	private String staticPath;

	@Autowired
	private RequirementService requirementService;

	@Autowired
	private RequirementApproverHierarchyService requirementApproverHierarchyService;

	@Autowired
	UserService userService;

	@Autowired
	TeamService teamService;

	@Autowired
	OrgSpocService orgSpocService;

	 @Autowired
	 CommonUtil commonUtil;
	 
	 @Autowired
	 private CandidateService candidateService;
	 
	 @Autowired
	 private UserRoleService userRoleService;
	 
	 @Autowired
	 private RecommendationsService recommendationsService;
	 
	 @Autowired
	 private RolesService roleService;
	 
	 @Autowired
	 private SourceleadRolesService sourceleadRolesService;
	 
	 @Autowired
	 private RequirementAssignToService requirementAssignToService;
	 
	 @Autowired
	 private LookUpDataService lookUpDataService;

	 @Autowired
	 RecommendedCustomizeScoreService recommendedCustomizeScoreService;
	 
	 @Autowired
	 RequirementSubmissionService requirementSubmissionService;
	 
	 @Autowired
	 private SourceleadELKBaseIndexer<CandidateIndex> sourceleadELKBaseIndexer;
	 
	 @Autowired
	 private RequirementElkUtil requirementElkUtil;
	
	 @Autowired
	 private ProjectsService projectsService;
	 
	 @Autowired
	 private RequirementDao requirementDao; 
	 
	 @Autowired
	 private OrgRelationsService orgRelationsService;
	 
	 @Autowired
	 JobShareService jobShareService;
	 
	 @Autowired
	 RequirementSubmissionHistoryService requirementSubmissionHistoryService;
	 
	 @Autowired
	 TimeZoneService timeZoneService;
	 
	 @Autowired
	 RequirementSubmissionTemplateFieldsService requirementSubmissionTemplateFieldsService;
	 
	 @Autowired
	 private OrgLocationService orgLocationService;
	 
	@Autowired
	private PdfUtil pdfUtil;
	 
	@RequestMapping(value = "/storeFairJob", method = RequestMethod.POST)
	public @ResponseBody Response<String> storeFairJob(JobFairDTO jobFairDTO, HttpServletRequest request)
			throws Exception {
		Response<String> response = new Response<>();
		try {
			Long orgLocId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
			OrgLocations orgLocation = orgLocationService.getOrgLocationById(orgLocId);
			
			Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
			User user = userService.getUserById(userId);
			if (user != null) {
				if(Validator.validate(jobFairDTO)) {
					response = requirementService.saveJobFair(jobFairDTO, user, orgLocation);
				} else {
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}
	
	@RequestMapping(value = "/changeJobFairStatus", method = RequestMethod.POST)
	@ResponseBody
	public Response<Boolean> changeJobFairStatus(HttpServletRequest request, @RequestParam(defaultValue = "0") long jobFairId,
			@RequestParam(defaultValue = "0") int status) {
		Response<Boolean> response = new Response<>();
		try {
			Long orgLocId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
			OrgLocations orgLocation = orgLocationService.getOrgLocationById(orgLocId);
					
			Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
			User user = userService.getUserById(userId);
			if (user != null) {
				boolean result = requirementService.changeJobFairStatus(jobFairId, status, user, orgLocation);
				response.setObject(result);
				response.setResponse(result ? Result.SUCCESS.name() : Result.FAILED.name());
				response.setResponseMessage(result ? Result.SUCCESS.getValue() : Result.FAILED.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}
	
	
	@RequestMapping(value="/getJobFairDatesForCalendar")
	public ResponseList<String> getJobFairDatesForCalendar(HttpServletRequest request){
		ResponseList<String> responseList = new ResponseList<>();
		try {
			Long orgLocId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
			OrgLocations loginOrgLocations = orgLocationService.getOrgLocationById(orgLocId);
					
			Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
			User user = userService.getUserById(userId);
			String roleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "displayName");
			if(user !=null) {
				boolean adminFlag = false;
				if("Admin".equalsIgnoreCase(roleName)) {
					adminFlag = true;	
				}
				/*Set<UserRole> userRoleSet = user.getUserRole();
				if (userRoleSet != null && !userRoleSet.isEmpty()) {
					for (UserRole userRole : userRoleSet) {
						if ((loginOrgLocations.getId().longValue() == userRole.getOrgLocations().getId().longValue()) && (user.getId().longValue() == userRole.getUser().getId().longValue()) 
								&& !userRole.getUser().getUserRole().isEmpty()) {
							for (UserRole uRole : userRole.getUser().getUserRole()) {
								if (uRole.getRoles().getRoleName().equalsIgnoreCase(Constants.ROLE_ADMIN)) {
									adminFlag = true;
								}
							}
						}
					}
				}*/
				responseList.setData(requirementService.getJobFairDatesForCalendar(user.getId(), loginOrgLocations.getId(), loginOrgLocations.getDateFormat(), adminFlag));
				responseList.setResponse(Result.SUCCESS.name());
				responseList.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				responseList.setResponse(Result.SESSION_TIMEDOUT.name());
				responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
		}
		return responseList;
	}
	
	@RequestMapping(value = "/getJobFairRequirements")
	public ResponseList<Requirement> getJobFairRequirements(HttpServletRequest request, @RequestParam(defaultValue="") String date) {
		ResponseList<Requirement> responseList = new ResponseList<>();
		try {
			Long orgLocId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
			OrgLocations loginOrgLocations = orgLocationService.getOrgLocationById(orgLocId);
					
			Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
			User user = userService.getUserById(userId);
			String roleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "displayName");
			if (user != null) {
				Date startTime =  commonUtil.orgLocationDateFormat(loginOrgLocations.getDateFormat(), date);
				DateFormat week_Day = new SimpleDateFormat ("EEEE");
				String dayOfweek = week_Day.format(startTime);
				SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
				String selectedDate  = formatter.format(startTime);
				
				boolean adminFlag = false;
				if("Admin".equalsIgnoreCase(roleName)) {
					adminFlag = true;
				}
				/*Set<UserRole> userRoleSet = user.getUserRole();
				if (userRoleSet != null && !userRoleSet.isEmpty()) {
					for (UserRole userRole : userRoleSet) {
						if (loginOrgLocations.getId().longValue() == userRole.getOrgLocations().getId().longValue() &&
								user.getId().longValue() == userRole.getUser().getId().longValue()
								&& !userRole.getUser().getUserRole().isEmpty()) {
							for (UserRole uRole : userRole.getUser().getUserRole()) {
								if (uRole.getRoles().getRoleName().equalsIgnoreCase(Constants.ROLE_ADMIN)) {
									adminFlag = true;
								}
							}
						}
					}
				}*/
				
				responseList.setData(requirementService.getJobFairRequirementsByDate(user.getId(), loginOrgLocations.getId(), loginOrgLocations.getDateFormat(), date, adminFlag));
				responseList.setResponse(Result.SUCCESS.name());
				responseList.setResponseMessage(dayOfweek + "<>" + selectedDate);
			} else {
				responseList.setResponse(Result.SESSION_TIMEDOUT.name());
				responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
		}
		return responseList;
	}
	
	@RequestMapping(value = "/getRequirementsByJobFair")
	public ResponseList<Requirement> getRequirementsByJobFairId(HttpServletRequest request,
			@RequestParam(defaultValue = "0") long jobFairId) {
		ResponseList<Requirement> responseList = new ResponseList<>();
		try {
			Long orgLocId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
			OrgLocations loginOrgLocations = orgLocationService.getOrgLocationById(orgLocId);
					
			Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
			User user = userService.getUserById(userId);
			
			if (user != null) {
				String roleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "displayName");
				boolean adminFlag = false;
				if("Admin".equalsIgnoreCase(roleName)) {
					adminFlag = true;
				}
				List<Requirement> requirementList = requirementService.getRequirementsListByJobFairId(user.getId(), loginOrgLocations.getId(), jobFairId, adminFlag);
				for(Requirement requirement : requirementList) {
					//setting jobfair date(s)
					requirement.setJobFairDates(requirementService.getJobFairDatesByRequirementIdAndJobFairId(requirement.getId(), jobFairId));
					//setting counts
					RequirementSubmissionsCountDTO rsCount = requirementSubmissionService.getAllPipeLineCountsByRequirementIdAndSubmissionStage(requirement.getId(), user, loginOrgLocations, "ALL");
					requirement.setNoOfApplicants(String.valueOf(rsCount.getApplicantsCount()));
					requirement.setNoOfShortlistedApplicants(String.valueOf(rsCount.getShortlistCount()));
					requirement.setNoOfSubmittedApplicants(String.valueOf(rsCount.getSubmitToClientCount()));
					requirement.setNoOfInterviewedApplicants(String.valueOf(rsCount.getInterviewCount()));
					requirement.setNoOfOfferedApplicants(String.valueOf(rsCount.getOffersCount()));
					requirement.setNoOfJoinedApplicants(String.valueOf(rsCount.getJoinedCount()));
				}
				responseList.setData(requirementList);
				responseList.setResponse(Result.SUCCESS.name());
				responseList.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				responseList.setResponse(Result.SESSION_TIMEDOUT.name());
				responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
		}
		return responseList;
	}
	 
	/**
	 * @author prasad k
	 * @since sept 13 2016
	 * @version 0.0.1
	 * 
	 *          This method is used to store/save Requirement in Requirement
	 *          table.
	 * 
	 * @param jsonObj
	 *            This is the first paramter to storeRequirement method
	 * @param orgLocations
	 *            This is the second paramter to storeRequirement method
	 * @param user
	 *            This is the third paramter to storeRequirement method
	 * @param multiFile
	 *            This is the fourth paramter to storeRequirement method
	 * 
	 * @return Candidate
	 * 
	 * @throws NullPointer
	 */
	@RequestMapping(value = "/storeRequirement", method = RequestMethod.POST)
	public @ResponseBody Response<String> storeRequirement(RequirementDTO requirementDTO, HttpServletRequest request, @RequestParam(value = "saveType", required = false) String saveType) throws Exception
	{
		Response<String> response =  new Response<>();
		if (Validator.validate(requirementDTO)) {
			requirementDTO.setStaticPath(staticPath);
		//	FileUploadUtil.fillUploadedFileInfo(requirementDTO, requirementDTO.getFileInfo());
			Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
			User user = userService.getUserById(userId); 
			if(user != null) {
				try {
					Long orgLocId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
					OrgLocations orgLocation = orgLocationService.getOrgLocationById(orgLocId);
					requirementDTO.setDateFormat(orgLocation.getDateFormat());
					Requirement requirement = requirementService.storeRequirement(requirementDTO, orgLocation, user);
					if (requirementDTO.getSaveType().equalsIgnoreCase("saveType")
							&& (null == (requirementDTO.getRequirementId())
									|| "".equalsIgnoreCase(requirementDTO.getRequirementId().toString()))) {
						StringBuilder reqUpdateMsg = new StringBuilder();
						SourceLeadSessionManager.setSessionAttribute(request, "reqCreateMsg", reqUpdateMsg.append("Success!! New ").append(requirement.getJobTitle()).append(" job  saved to Draft").toString());
					}
					else if (requirementDTO.getSaveType().equalsIgnoreCase("save")
							&& (null == (requirementDTO.getRequirementId())
									|| "".equalsIgnoreCase(requirementDTO.getRequirementId().toString()))) {
						StringBuilder reqUpdateMsg1 = new StringBuilder();
						SourceLeadSessionManager.setSessionAttribute(request, "reqCreateMsg", reqUpdateMsg1.append("Success!! New ").append(requirement.getJobTitle()).append(" job  saved").toString());
					}
					else if (requirementDTO.getSaveType().equalsIgnoreCase("submitForApproval")
							&& (null == (requirementDTO.getRequirementId())
									|| "".equalsIgnoreCase(requirementDTO.getRequirementId().toString()))) {
						StringBuilder reqUpdateMsg2 = new StringBuilder();
						SourceLeadSessionManager.setSessionAttribute(request, "reqCreateMsg", reqUpdateMsg2.append("Success!! New ").append(requirement.getJobTitle()).append(" job  submitted for approval").toString());
					}
					else if ((requirementDTO.getRequirementId() != null)
							&& !(requirementDTO.getRequirementId().isEmpty())
							&& (requirementDTO.getSaveType().equalsIgnoreCase("save"))) {
						StringBuilder reqUpdateMs3 = new StringBuilder();
						SourceLeadSessionManager.setSessionAttribute(request, "reqCreateMsg", reqUpdateMs3.append("Success!! ").append(requirement.getJobTitle()).append(" details updated").toString());
					}
					else if ((requirementDTO.getRequirementId() != null)
							&& !(requirementDTO.getRequirementId().isEmpty())
							&& (requirementDTO.getSaveType().equalsIgnoreCase("submitForApproval"))) {
						StringBuilder reqUpdateMsg4 = new StringBuilder();
						SourceLeadSessionManager.setSessionAttribute(request, "reqCreateMsg", reqUpdateMsg4.append("Success!! ").append(requirement.getJobTitle()).append(" Updated and Submitted ").toString());
					}
					else if ((requirementDTO.getRequirementId() != null)
							&& !(requirementDTO.getRequirementId().isEmpty())
							&& (requirementDTO.getSaveType().equalsIgnoreCase("draft"))) {
						StringBuilder reqUpdateMs5 = new StringBuilder();
						SourceLeadSessionManager.setSessionAttribute(request, "reqCreateMsg", reqUpdateMs5.append("Success!!  ").append(requirement.getJobTitle()).append(" Updated in Draft Stage").toString());
					}
					response.setObject(requirement.getRequirementUUID());
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				}catch(Exception e) {
					response.setResponse(Result.AWKWARD.name());
					response.setResponseMessage(Result.AWKWARD.getValue());
					e.printStackTrace();
				}
			}else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
			return response;
		}
		else
			response.setObject("MissingData");
			return response;
	}
	
	/**
	 * This method is used for to create template and stored in Requirement
	 * table
	 * 
	 * @param json
	 * @param request
	 * @return success msg
	 * @throws NullPointer
	 */
	@PreAuthorize("hasAnyRole('ROLE_CREATE_REQUIREMENT_TEMPLATE','ROLE_VIEW_EDIT_REQUIREMENT_TEMPLATE')")
	@RequestMapping(value = "/storeRequirementTemplate", method = RequestMethod.POST)
	public @ResponseBody String storeRequirementTemplate(RequirementDTO requirementDTO, HttpServletRequest request) throws Exception
	{
		Long orgLocId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		OrgLocations orgLocation = orgLocationService.getOrgLocationById(orgLocId);
		
		Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
		User user = userService.getUserById(userId);
		Requirement requirement = requirementService.storeRequirementTemplate(requirementDTO, orgLocation, user);
		StringBuilder reqTemplateSuccessMessage = new StringBuilder();
		SourceLeadSessionManager.setSessionAttribute(request, "userCreateMsgTemplate", reqTemplateSuccessMessage.append("Success!! ").append(requirementDTO.getJobTitle()).append(" template  saved").toString());
		return "success";
	}
	@RequestMapping(value = "/deleteJobDoc", method = RequestMethod.POST)
	public @ResponseBody String deleteJobDoc(@RequestParam(value = "requirementId", required = false) Long requirementId,HttpServletRequest request) throws Exception{
		
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);
		if(requirement != null){
		requirement.setFileURL(null);
		requirement.setFileName(null);
		requirement.setFileContentType(null);
		requirementDao.storeRequirement(requirement);
		}
		return "success";
	}
	/**
	 * @author sarath kumaar
	 * @since oct 18 2016
	 * @version 0.0.1
	 * 
	 *          This method is used to accept/Reject Requirement in Requirement
	 *          table.
	 * 
	 * @param jsonObj
	 *            This is the first paramter to storeRequirement method
	 * @param orgLocations
	 *            This is the second paramter to storeRequirement method
	 * @param user
	 *            This is the third paramter to storeRequirement method
	 * @param multiFile
	 *            This is the fourth paramter to storeRequirement method
	 * 
	 * @return success msg
	 * 
	 * @throws NullPointer
	 */

	@RequestMapping(value = "/requirementAcceptOrReject", method = RequestMethod.POST)
	public @ResponseBody String requirementAcceptOrReject(
			@RequestParam(value = "requirementId", required = false) Long requirementId,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "isLoginUserAsAdmin", required = false) String isLoginUserAsAdmin,
			@RequestParam(value = "reason", required = false) String reason, HttpServletRequest request)
			throws Exception {

		Long orgLocId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		OrgLocations orgLocation = orgLocationService.getOrgLocationById(orgLocId);
		
		Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
		User loginUser = userService.getUserById(userId);
		Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);
		User managerUser=userService.getUserById(requirement.getManagerUserId().getId());

		RequirementApproverHierarchy requirementApproverHierarchy = null;
		//rejected reason save in requirementApproverHierarchy table only who can reject the requirement
		if(isLoginUserAsAdmin.equalsIgnoreCase("true")){
			requirementApproverHierarchy = requirementApproverHierarchyService.getApproverHierarchyByRequirementId(requirementId);
			/*requirementApproverHierarchy.setRejectedReason(reason);
	    	requirementApproverHierarchyService.addRequirementApproverHierarchy(requirementApproverHierarchy);*/
		}else{
		requirementApproverHierarchy = requirementApproverHierarchyService.getRequirementApproverHierarchyByRequirementId(requirementId, loginUser.getId());
		
		 /*if(status.equalsIgnoreCase("REJECTED")  &&  !reason.equalsIgnoreCase("") && !reason.isEmpty()){
	    	requirementApproverHierarchy.setRejectedReason(reason);
	    	requirementApproverHierarchyService.addRequirementApproverHierarchy(requirementApproverHierarchy);
	    }*/
		}
		//  This for the Requirement Location 
		StringBuilder location = new StringBuilder();
		if(null != requirement.getCity() && !requirement.getCity().equalsIgnoreCase("")){
			location.append(requirement.getCity()).append(" ,");
		 }
		if(null != requirement.getState() && !requirement.getState().equalsIgnoreCase("")){
			location.append(requirement.getState()).append(" ,");
		 }
		
		 location.append(requirement.getCountry()).append(" ");
		
		
		// requirement created user

					// to get RequirementApproverHierarchy with maximum Level
					Integer maxLevel = requirementApproverHierarchyService.getRequirementApproverHierarachyWithHighestLevel(requirementId);

					if (null != maxLevel) {
						
						// approve the requirement from admin login. 
						if(isLoginUserAsAdmin.equalsIgnoreCase("true")){
							if (status.equalsIgnoreCase("APPROVED")) {
								requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.OPEN);
								requirement.setReqlevel1ApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.APPROVED.toString());
							} else if (status.equalsIgnoreCase("REJECTED")) {
								requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.REJECTED);
							}
							requirement.setUpdatedBy(loginUser.getUserCode());
							requirement.setUpdatedDate(commonUtil.currentDate());
							requirementService.addRequirement(requirement);
							List<RequirementApproverHierarchy> list1 = requirementApproverHierarchyService.getRequirementApproverHierarchyListByRequirementId(requirementId);
							for (RequirementApproverHierarchy rah : list1) {
								if (status.equalsIgnoreCase("APPROVED")) {
									rah.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.APPROVED);
								} else if (status.equalsIgnoreCase("REJECTED")) {
									rah.setRejectedReason(reason);
									rah.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.REJECTED);
									requirementService.rejectedEmailScenario(loginUser, requirement.getCreatedBy(), requirement, location, reason,orgLocation);
								}
								rah.setUpdatedBy(loginUser.getUserCode());
								rah.setUpdatedDate(commonUtil.currentDate());
								requirementApproverHierarchyService.addRequirementApproverHierarchy(rah);
							}
							// for URL redirections from mail
							String redirectURL = "/requirementSummary-params-requirementUUID="+requirement.getRequirementUUID();
							Long redirectOrgLocationId = orgLocation.getId();
							/*This logic is to send mail to assigned team members after approver accepted the requirement request(parallel approver)*/
							RequirementApproverHierarchy hierarchy = requirementApproverHierarchyService.getApproverHierarchyByRequirementId(requirementId);
					    	 List<RequirementAssignTo> reqAssignTo = requirementAssignToService.getAssignedToMembersByRequirementIdAndOrgLocationId(requirementId,orgLocation.getId());
				    			if(null != hierarchy && "APPROVED".equals(hierarchy.getRequirementApproverStatus().toString())){
				    				for(RequirementAssignTo reqAssign:reqAssignTo ){
											if(null != reqAssign.getAssignUserId() && !loginUser.getId().equals(reqAssign.getAssignUserId().getId())){
												requirementService.AssignToTeamMembersEmail(loginUser, reqAssign.getAssignUserId(), requirement,new StringBuilder(),orgLocation,redirectURL,redirectOrgLocationId);
											}
				    				}
				    		}
						}
						else if (maxLevel.equals(requirementApproverHierarchy.getLevel())) {
								if (status.equalsIgnoreCase("APPROVED")) {
									requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.OPEN);
								} else if (status.equalsIgnoreCase("REJECTED")) {
									requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.REJECTED);
								}
									requirement.setUpdatedBy(loginUser.getUserCode());
									requirement.setUpdatedDate(commonUtil.currentDate());
									requirementService.addRequirement(requirement);
									List<RequirementApproverHierarchy> list1 = requirementApproverHierarchyService.getRequirementApproverHierarchyListByRequirementId(requirementId);
									for (RequirementApproverHierarchy rah : list1) {
										if (status.equalsIgnoreCase("APPROVED")) {
											rah.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.APPROVED);
										} else if (status.equalsIgnoreCase("REJECTED")) {
											rah.setRejectedReason(reason);
											rah.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.REJECTED);
										}
										rah.setUpdatedBy(loginUser.getUserCode());
										rah.setUpdatedDate(commonUtil.currentDate());
										requirementApproverHierarchyService.addRequirementApproverHierarchy(rah);
									}
								// this is for sending email to requirement initiator
								if (status.equalsIgnoreCase("APPROVED")) {
									
									if(loginUser.getId().longValue() != requirement.getManagerUserId().getId().longValue()){
										if(!requirement.getCreatedBy().getId().equals(managerUser.getId())){
									requirementService.acceptEmailScenario(loginUser, requirement.getCreatedBy(), requirement, location ,orgLocation);
									requirementService.acceptEmailScenario(loginUser, managerUser, requirement, location ,orgLocation);
										}else{
									requirementService.acceptEmailScenario(loginUser, requirement.getCreatedBy(), requirement, location ,orgLocation);
										}
									}
								} else if (status.equalsIgnoreCase("REJECTED")) {
									
									if(loginUser.getId().longValue() != requirement.getManagerUserId().getId().longValue()){
										if(!requirement.getCreatedBy().getId().equals(managerUser.getId())){
									requirementService.rejectedInitiatorUserEmailScenario(loginUser, requirement.getCreatedBy(), requirement, location, reason,orgLocation);
									requirementService.rejectedEmailScenario(loginUser, managerUser, requirement, location, reason,orgLocation);
										}
										else{
											
											requirementService.rejectedEmailScenario(loginUser, requirement.getCreatedBy(), requirement, location, reason,orgLocation);
										}
									}
									
									}
								// for URL redirections from mail
								String redirectURL = "/requirementSummary-params-requirementUUID="+requirement.getRequirementUUID();
								Long redirectOrgLocationId = orgLocation.getId();
								 /*This logic is to send mail to assigned team members after approver accepted the requirement request(parallel approver)*/
								RequirementApproverHierarchy hierarchy = requirementApproverHierarchyService.getApproverHierarchyByRequirementId(requirementId);
						    	 List<RequirementAssignTo> reqAssignTo = requirementAssignToService.getAssignedToMembersByRequirementIdAndOrgLocationId(requirementId,orgLocation.getId());
					    			if(null != hierarchy && "APPROVED".equals(hierarchy.getRequirementApproverStatus().toString())){
					    				for(RequirementAssignTo reqAssign:reqAssignTo ){
												if(null != reqAssign.getAssignUserId() && !loginUser.getId().equals(reqAssign.getAssignUserId().getId())){
													requirementService.AssignToTeamMembersEmail(loginUser, reqAssign.getAssignUserId(), requirement,new StringBuilder(),orgLocation,redirectURL,redirectOrgLocationId);
												}
					    				}
					    		}
						}else{

							    RequirementApproverHierarchy level1approver = requirementApproverHierarchyService.getRequirementApproverHierarchyByRequirementIdAndMaximumLevel(requirementId);
								User approverUser = userService.getUserById(level1approver.getApproverUserId().getId());
								if (status.equalsIgnoreCase("APPROVED")) {
									if (null != level1approver) {
										// this is for sending email to second level approver
										if(loginUser.getId().longValue() != requirement.getManagerUserId().getId().longValue()){
										requirementService.approverTwoEmailScenario(loginUser, approverUser, requirement, location,	orgLocation);
									}
									}
									requirementApproverHierarchy.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.APPROVED);
									requirement.setReqlevel1ApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.APPROVED.toString());
								} else if (status.equalsIgnoreCase("REJECTED")) {
									// this is for sending email to requirement initiator
									requirementApproverHierarchy.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.REJECTED);
									requirement.setReqlevel1ApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.REJECTED.toString());
									requirementApproverHierarchy.setRejectedReason(reason);
									if(loginUser.getId().longValue() != requirement.getManagerUserId().getId().longValue()){
									requirementService.rejectedEmailScenario(loginUser, requirement.getCreatedBy(), requirement, location, reason,orgLocation);
									}
									requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.REJECTED);
									
									// This is  to update  the level2 Approver Record For RejectedS
								    RequirementApproverHierarchy level2approver = requirementApproverHierarchyService.getRequirementApproverHierarchyByRequirementIdExcludingApproverHierarchyId(requirementApproverHierarchy.getId(), requirementId);
								    level2approver.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.REJECTED);
								    requirement.setReqlevel1ApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.REJECTED.toString());
								    level2approver.setUpdatedBy(loginUser.getUserCode());
								    level2approver.setUpdatedDate(commonUtil.currentDate());
								    level2approver.setRejectedReason(reason);
									requirementApproverHierarchyService.addRequirementApproverHierarchy(level2approver);
								}
								requirementApproverHierarchy.setUpdatedBy(loginUser.getUserCode());
								requirementApproverHierarchy.setUpdatedDate(commonUtil.currentDate());
								requirementApproverHierarchyService.addRequirementApproverHierarchy(requirementApproverHierarchy);
								requirementService.addRequirement(requirement);
						}
						
					}
					RequirementDTO requirementDTO = commonUtil.getRequirementDTO(requirement);
					requirementElkUtil.saveElkRequirement(requirement,requirementDTO);
			
					return "success";
	}
					
		// Old Code			
		/*// to get  RequirementApproverHierarchy with maximum Level

		RequirementApproverHierarchy requirementApproverHierarchy = requirementApproverHierarchyService
				.getRequirementApproverHierarchyByRequirementId(requirementId, loginUser.getId());

		// requirement created user
		User intiatorUser = userService.getUserByUserCode(requirement.getCreatedBy());

		// to get RequirementApproverHierarchy with maximum Level

		Integer maxLevel = requirementApproverHierarchyService.getRequirementApproverHierarachyWithHighestLevel(requirementId);

		if (maxLevel != null) {
			if (maxLevel.equals(requirementApproverHierarchy.getLevel())) {
				if (status.equalsIgnoreCase("APPROVED")) {
					requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.OPEN);
				} else if (status.equalsIgnoreCase("REJECTED")) {
					requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.REJECTED);
				}
				requirement.setUpdatedBy(loginUser.getUserCode());
				requirement.setUpdatedDate(commonUtil.currentDate());
				requirementService.addRequirement(requirement);
				List<RequirementApproverHierarchy> list1 = requirementApproverHierarchyService.getRequirementApproverHierarchyListByRequirementId(requirementId);
				for (RequirementApproverHierarchy rah : list1) {
					if (status.equalsIgnoreCase("APPROVED")) {
						rah.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.OPEN);
					} else if (status.equalsIgnoreCase("REJECTED")) {
						rah.setRejectedReason(reason);
						rah.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.REJECTED);
					}
					rah.setUpdatedBy(loginUser.getUserCode());
					rah.setUpdatedDate(commonUtil.currentDate());
					requirementApproverHierarchyService.addRequirementApproverHierarchy(rah);
				}
				// this is for sending email to requirement initiator
				if (status.equalsIgnoreCase("APPROVED")) {
					requirementService.acceptEmailScenario(loginUser, intiatorUser, requirement, orgLocation);
				} else if (status.equalsIgnoreCase("REJECTED")) {
					requirementService.rejectedEmailScenario(loginUser, intiatorUser, requirement, null, reason,orgLocation);
				}
			} else {

				RequirementApproverHierarchy level2approver = requirementApproverHierarchyService.getRequirementApproverHierarchyByRequirementIdAndMaximumLevel(requirementId);
				User approverUser = userService.getUserById(level2approver.getApproverUserId().getId());
				if (status.equalsIgnoreCase("APPROVED")) {
					if (level2approver != null) {
						// this is for sending email to second level approver
						requirementService.approverTwoEmailScenario(loginUser, approverUser, requirement, null,	orgLocation);
					}
					requirementApproverHierarchy
							.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.OPEN);
				} else if (status.equalsIgnoreCase("REJECTED")) {
					// this is for sending email to requirement initiator
					requirementApproverHierarchy.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.REJECTED);
					requirementApproverHierarchy.setRejectedReason(reason);
					requirementService.rejectedEmailScenario(loginUser, intiatorUser, requirement, null, reason,orgLocation);

				}
				requirementApproverHierarchy.setUpdatedBy(loginUser.getUserCode());
				requirementApproverHierarchy.setUpdatedDate(commonUtil.currentDate());
				requirementApproverHierarchyService.addRequirementApproverHierarchy(requirementApproverHierarchy);
			}
	
		}

		*/
	

		/*
		 * if(status.equalsIgnoreCase("REJECTED")){ if(maxLevel!=null){
		 * if(maxLevel.equals(requirementApproverHierarchy.getLevel())){
		 * requirement.setRequirementSubmissionStatus(
		 * REQUIREMENT_SUBMISSION_STATUS.REJECTED);
		 * requirement.setUpdatedBy(loginUser.getUserCode());
		 * requirement.setUpdatedDate(commonUtil.currentDate());
		 * requirementService.addRequirement(requirement);
		 * List<RequirementApproverHierarchy> list1 =
		 * requirementApproverHierarchyService.
		 * getRequirementApproverHierarchyListByRequirementId(requirementId);
		 * for(RequirementApproverHierarchy rah :list1){
		 * rah.setRequirementApproverStatus(
		 * REQUIREMENT_APPROVER_HIERARCHY_STATUS.REJECTED);
		 * rah.setUpdatedBy(loginUser.getUserCode());
		 * if(rah.getApproverUserId().getId() == loginUser.getId()){
		 * requirementApproverHierarchy.setRejectedReason(reason); }
		 * rah.setUpdatedDate(commonUtil.currentDate());
		 * requirementApproverHierarchyService.addRequirementApproverHierarchy(
		 * rah); }
		 * 
		 * 
		 * // Email for Reject requirement from approver level Two to Initiator.
		 * 
		 * requirementService.rejectedEmailScenario(loginUser, intiatorUser,
		 * requirement, null, reason,orgLocation); }else{
		 * requirementApproverHierarchy.setRequirementApproverStatus(
		 * REQUIREMENT_APPROVER_HIERARCHY_STATUS.REJECTED);
		 * requirementApproverHierarchy.setUpdatedBy(loginUser.getUserCode());
		 * requirementApproverHierarchy.setUpdatedDate(commonUtil.currentDate())
		 * ; requirementApproverHierarchy.setRejectedReason(reason);
		 * requirementApproverHierarchyService.addRequirementApproverHierarchy(
		 * requirementApproverHierarchy);
		 * 
		 * // Email for Reject requirement from approver level One to Initiator.
		 * requirementService.rejectedEmailScenario(loginUser, intiatorUser,
		 * requirement, null, reason,orgLocation); //User approverUser =
		 * userService.getUserById(requirementApproverHierarchy.
		 * getApproverUserId().getId());
		 * //requirementService.approverTwoEmailScenario(loginUser,
		 * approverUser,requirement,null,orgLocation); } } }
		 */

	

	/**
	 * @author punnana sarath kumar
	 * @since sept 13 2016
	 * @version 0.0.1
	 * 
	 *          This method is used to store/save Requirement in Requirement
	 *          table.
	 * 
	 * @param jsonObj
	 *            This is the first paramter to storeRequirement method
	 * @param orgLocations
	 *            This is the second paramter to storeRequirement method
	 * @param user
	 *            This is the third paramter to storeRequirement method
	 * @param multiFile
	 *            This is the fourth paramter to storeRequirement method
	 * 
	 * @return Candidate
	 * 
	 * @throws NullPointer
	 */

	@RequestMapping(value = "/getUserList", method = RequestMethod.POST)
	public @ResponseBody ResponseList<User> getUserList(@RequestParam(value = "teamType", required = false) String teamType,
			HttpServletRequest request) throws Exception {
		ResponseList<User> response =  new ResponseList<>();
		Long orgLocId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		OrgLocations orgLocation = orgLocationService.getOrgLocationById(orgLocId);
				
		Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
		User loginUser = userService.getUserById(userId);
		
	 	if(loginUser != null) {
			try {

				List<User> usersList = userService.getUsersByTeamTypeAndTabId(orgLocation.getId(),teamType, Long.valueOf(Constants.TAB_REQUIREMENT));
				List<User> assignedUserList = new CopyOnWriteArrayList<>();
		    	  HashMap<Long,User> userMap = new HashMap<>();
				    if(usersList != null && usersList.size() > 0) {
				    	for(User assignedUser : usersList) {
				    		
				    		User tempUser = userMap.get(assignedUser.getId());
				    		if(tempUser == null) {
				    			userMap.put(assignedUser.getId(),assignedUser);
					    		assignedUserList.add(assignedUser);
				    		}else {
				    			assignedUserList.remove(assignedUser);
								continue;
				    		}
				    		
				    	}
				    }
				
				response.setData(assignedUserList);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			}catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
			}
	 	}else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		return response;
	}
	
	
	@RequestMapping(value = "/getUserAssingedJobsListCountForRequirementAssignedTo" , method = RequestMethod.GET)
	 public @ResponseBody UserDTO getUserAssingedJobsListCountForRequirementAssignedTo(@RequestParam(value = "userId", required = true) Long userId, HttpServletRequest request) throws Exception {
			
		     OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		     UserDTO userDTO = new UserDTO();
		     List<RequirementAssignTo> assign = requirementService.getUserAssignJobCount(userId,orgLocation.getId());
		     userDTO.setAssignedJobsCount(assign.size());
				UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(userId, orgLocation.getId());
				if(null != userRole){
					userDTO.setUserType(userRole.getUserType());
				}
				return userDTO;
	}

	@RequestMapping(value = "/getApproverTwoByUserId", method = RequestMethod.POST)
	public @ResponseBody String getApproverTwoByUserId(
			@RequestParam(value = "currentApproverId", required = false) Long currentApproverId,
			HttpServletRequest request) throws Exception {
		String resultJson = null;

		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

		List<User> userList = userService.getUsersByTeamTypeAndNoCurrentUser(currentApproverId, orgLocation.getId());
		resultJson = toJSON(userList);
		return resultJson;
	}

	/**
	 * Handles Vendor Contacts
	 * 
	 * @param json
	 * @param request
	 * @return Json
	 * @throws Exception
	 */
	//TODO Dont delete
	/*@PreAuthorize("hasRole('ROLE_CREATE_CLIENT_CONTACT')")
	@RequestMapping(value = "/saveClientContact", method = RequestMethod.POST)
	@ResponseBody
	public String saveClientContact(@RequestParam(value = "json", required = false) String json,
			@RequestParam(value = "orgRelationId", required = false) String orgRelationId,
			@RequestParam(value = "clientLocationId", required = false) String clientLocationId,
			HttpServletRequest request) throws Exception {

		String responderType = "Client";
		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		orgSpocService.addVendorContacts(jsonObj, user, orgLocations, orgRelationId, clientLocationId, responderType,
				org);

		List<OrgSpoc> orgSpocsList = orgSpocService.getAllSpocsByOrgRelationId(Long.parseLong(orgRelationId),
				user.getId(), orgLocations.getId(),"Client");
		for (OrgSpoc orgSpoc : orgSpocsList) {
			String ownerLastName = "";
			if (null != orgSpoc.getVendorUser() && null != orgSpoc.getVendorUser().getLastName()) {
				ownerLastName = orgSpoc.getVendorUser().getLastName();
			}
			StringBuilder spocNameSB = new StringBuilder();
			StringBuilder ownerNameSB = new StringBuilder();
			orgSpoc.setSpocName(spocNameSB.append(orgSpoc.getClientUser().getFirstName()).append(" ").append(orgSpoc.getClientUser().getLastName()).toString());
			orgSpoc.setSpocEmail(orgSpoc.getClientUser().getEmail());
			orgSpoc.setSpocMobilePhone(orgSpoc.getClientUser().getMobilePhone());
			orgSpoc.setOwnerName(ownerNameSB.append(orgSpoc.getOwnerUser().getFirstName()).append(" ").append(ownerLastName).toString());
			orgSpoc.setOwnerUserId(orgSpoc.getVendorUser().getId().toString());
			orgSpoc.setSpocUserId(orgSpoc.getClientUser().getId());
		}
		return commonUtil.toJSON(orgSpocsList);
	}*/

	/**
	 * @author Venkat Kesav
	 * @since sept 21 2016
	 * @version 0.0.1
	 * 
	 *          This method is used to getTeamsList.
	 * 
	 * @param request
	 * @return resultJson
	 * 
	 * @throws NullPointer
	 */
	@RequestMapping(value = "/getTeamsList", method = RequestMethod.POST)
	public @ResponseBody Response<CombinedList> getTeamsList(@RequestParam(value = "reqId", required = false) Long reqId, HttpServletRequest request) throws Exception {
		Response<CombinedList> response =  new Response<>();
		
		logger.info("Team And Roles Start Time>>>"+Calendar.getInstance().getTime());
		
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(loginUser != null) {
			try {
					OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
					CombinedList combinedList = new CombinedList();
					
					List<RequirementAssignTo> requirementAssignTo = null;
					if(!Objects.isNull(reqId)){
						requirementAssignTo = requirementAssignToService.getAssignedToMembersByRequirementIdAndOrgLocationId(reqId, orgLocation.getId());
					}
			
					Boolean isTeamViewAccess = commonUtil.hasRole("ROLE_VIEW_TEAM");
					if (isTeamViewAccess) {
						List<Team> teamsList = teamService.getTeamListByOrgLocationID(orgLocation.getId());
						if (null != teamsList) {
							for(Team team : teamsList){
								
								if(null != requirementAssignTo){
									if (requirementAssignTo.stream().anyMatch(t -> t.getTeamId() != null && t.getTeamId().getId().equals(team.getId()))) {
										team.setIsTeamAssigned("Y");
									}
								}
							}
							combinedList.setTeams(teamsList);
						}
					}
			
					List<Roles> rolesList = roleService.getTabAccessRolesListByOrgLocationIdAndTabId(orgLocation.getId(),
							Long.valueOf(Constants.TAB_REQUIREMENT));
					if (null != rolesList) {
						for(Roles role : rolesList){
							if(null != requirementAssignTo){
								if (requirementAssignTo.stream().anyMatch(t -> t.getTeamId() == null && t.getRoleId() != null && t.getRoleId().getId().equals(role.getId()))) {
									role.setIsRoleAssigned("Y");
								}
							}
						}
						combinedList.setRoles(rolesList);
					}
					response.setObject(combinedList);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				}catch (Exception e) {
					response.setResponse(Result.AWKWARD.name());
					response.setResponseMessage(Result.AWKWARD.getValue());
				}
		 	}else {
					response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		
		logger.info("Team And Roles End Time>>>"+Calendar.getInstance().getTime());
		return response;
	}

	/**
	 * @author Rajashekar Recommendations
	 * @param requirementId
	 * @param request
	 * @return Json
	 * @throws Exception
	 */
	@PreAuthorize("hasAnyRole('ROLE_RECOMMENDATIONS')")
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getRequirementRecommendationsByRequirementId", method = RequestMethod.POST)
	public @ResponseBody ResponseList<CandidateIndex> getRequirementRecommendationsByRequirementId(
			@RequestParam(value = "requirementId", required = false) Long requirementId, @RequestParam(value = "orgLocationId", required = false) Long orgLocationId, @RequestParam(value = "count", required = false) Long offset,CandidateDTO candidateDTO)
			throws Exception {
		
		ResponseList<CandidateIndex> candidateListCopy = null;
		try {
			RecommendedCustomizeScore  recommendedCustomizeScore = recommendedCustomizeScoreService.getRecommendedCustomizeScoreByRequirementId(requirementId);
		    if(null != recommendedCustomizeScore){ 
		    	candidateListCopy = recommendationsService.getRecommendationsUpdateWithScoreByUsingElk(orgLocationId, recommendedCustomizeScore, requirementId,null,candidateDTO);
		    }
		    candidateListCopy.setResponse(Result.SUCCESS.name());
		    candidateListCopy.setResponseMessage(Result.SUCCESS.getValue());
		}catch(Exception e) {
			candidateListCopy.setResponse(Result.AWKWARD.name());
			candidateListCopy.setResponseMessage(Result.AWKWARD.getValue());
		}
		return candidateListCopy;
	}

	
	@PreAuthorize("hasAnyRole('ROLE_RECOMMENDATIONS')")
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getRecommendationsCountBasedOnRequirementId", method = RequestMethod.POST)
	public @ResponseBody String getRecommendationsCountBasedOnRequirementId(
			@RequestParam(value = "requirementId", required = false) Long requirementId, HttpServletRequest request)
			throws Exception {
		 String resultJson;
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 Long maxResults = candidateService.getCandidatesCountByOrgLocationID(orgLocation.getId(),null);
		 BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		 boolQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", orgLocation.getId()));
		 boolQueryBuilder.must(QueryBuilders.matchQuery("status",1));
		 Long counts = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE,0,maxResults.intValue(),boolQueryBuilder).getTotalHits();
		resultJson = commonUtil.toJSON(counts);
		return resultJson;

	}
	
	
	/**
	 * @David Raju Sammeta
	 * 
	 *        This Method getRequirementWhichIsReleasedOrNot is Used to check
	 *        whether the requirement is released Or Not
	 * 
	 * @param requirementId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@PreAuthorize("hasRole('ROLE_CREAE_OFFER')")
	@RequestMapping(value = "/getRequirementWhichIsReleasedOrNot", method = RequestMethod.POST)
	public @ResponseBody String getRequirementWhichIsReleasedOrNot(
			@RequestParam(value = "requirementId", required = false) Long requirementId, HttpServletRequest request)
			throws Exception {
		String resultJson = null;

		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

		Boolean flag = requirementService.checkRequirementIsReleasedOrNot(requirementId, orgLocation.getId());
		resultJson = toJSON(flag);
		return resultJson;
	}

	/**
	 * prasad K This method is check job id duplicate or not
	 * 
	 * @param jobId
	 * @return
	 * @throws Exception
	 */


	 @RequestMapping(value = "/validateJobId",  method = RequestMethod.POST)
	    public @ResponseBody Boolean validateJobId(@RequestParam(value="jobId", required=false) String jobId,
	    		@RequestParam(value="requirementid", required=false) Long requirementid,HttpServletRequest request) throws Exception {
		       OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		       Requirement requirement = null;
		       if(null != requirementid){
		    	   requirement  = requirementService.getRequirementByRequirementIdOrgLocationsId(jobId, requirementid, orgLocation.getId());
		       }
		        Boolean flag = false;
			    if(requirement == null){
			    	flag = requirementService.validateJobid(jobId, orgLocation.getId());
			    }
				return flag;
		    }
	 
	 
	 @RequestMapping(value = "/validateExistingJobId",  method = RequestMethod.POST)
	    public @ResponseBody Boolean validateExistingJobId(@RequestParam(value="jobId", required=false) String jobId,
	    		@RequestParam(value="requirementid", required=false) Long requirementid,HttpServletRequest request) throws Exception {
		       OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		        Boolean flag = requirementService.validateJobid(jobId, orgLocation.getId());
				return flag;
		    }

	
	private String toJSON(Object obj){
        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
            public HierarchicalStreamWriter createWriter(Writer writer) {
                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
            }
        });
        return xstream.toXML(obj);
    }
	 @RequestMapping(value = "/validatetemplateName",  method = RequestMethod.POST)
	    public @ResponseBody String validatejobName(@RequestParam(value="templateName", required=false) String templateName,HttpServletRequest request) throws Exception {
				OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				Boolean validatejobName = requirementService.validateRequirementTitle(templateName,orgLocation.getId());
				return validatejobName.toString();
		    }
	 
	 
	    @RequestMapping(value = "/getRequirementAndLookUpSkills",  method = RequestMethod.POST)
	    public @ResponseBody String getRequirementAndLookUpSkills(@RequestParam(value="requirementid", required=false) Long requirementid) throws Exception {
	    	
	    	List<String> allReqSkills = requirementService.getAllRequirementSkillsByRequirementI(requirementid);		
		  	List<String> skillsList = lookUpDataService.getAllSkillsBySkillCategory();
			if(null != allReqSkills  && !allReqSkills.isEmpty() && !allReqSkills.contains(null) && !allReqSkills.contains("")){
						String[] skills = allReqSkills.get(0).split(","); 
						for(String skill : skills){ 
							if(!skillsList.contains(skill)){
									skillsList.add(skill);
							}
						}  
					}
 	
	    	String resultJson; 
		  	resultJson = commonUtil.toJSON(skillsList);
			return resultJson;
		    }

	    
	    @RequestMapping(value = "/getCandidateHistoryList", method = RequestMethod.POST)
		public @ResponseBody String getCandidateHistoryList(
				@RequestParam(value = "candidateId", required = false) Long candidateId, HttpServletRequest request)
				throws Exception {
			String resultJson = null;
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			List<RequirementSubmission> reqList = requirementSubmissionService.getRequirementHistoryListByCandidateIdAndOrgLocationID(candidateId,orgLocation.getId());
			for(RequirementSubmission reqSubmission : reqList){
				if(null != reqSubmission.getRequirement().getJobTitle() && !"".equalsIgnoreCase(reqSubmission.getRequirement().getJobTitle())){
					reqSubmission.setRequirementName(reqSubmission.getRequirement().getJobTitle());	
				}
				reqSubmission.setRequirementJobId(reqSubmission.getRequirement().getJobId());
				reqSubmission.setRequirementStage(reqSubmission.getSubmissionStatus().toString());
				if(null != reqSubmission.getRequirement().getSpocId()){
					StringBuilder clientName = new StringBuilder();
					 if(!"".equalsIgnoreCase(reqSubmission.getRequirement().getSpocId().getClientUser().getLastName()) && null != reqSubmission.getRequirement().getSpocId().getClientUser().getLastName() ){
						 reqSubmission.setClientName(clientName.append(reqSubmission.getRequirement().getSpocId().getClientUser().getFirstName()).append(" ").append(reqSubmission.getRequirement().getSpocId().getClientUser().getLastName()).toString());
					 }else{
						 reqSubmission.setClientName(clientName.append(reqSubmission.getRequirement().getSpocId().getClientUser().getFirstName()).toString());
					 }
				 }
			}
			resultJson = toJSON(reqList);
			return resultJson;
		}
	    
	    @RequestMapping(value = "/getEditRequirementDetails", method = RequestMethod.GET)
		public @ResponseBody String getEditRequirementDetails(@RequestParam(value="requirementId", required=false) String requirementId,HttpServletRequest request,HttpServletResponse response) throws Exception {
	    	
	    	Requirement requirement = null;
	    	if(null !=requirementId && "" !=requirementId && "undefined" !=requirementId){
	    	requirement =	requirementService.getRequirementDetailsById(Long.parseLong(requirementId));
	    	}
			return commonUtil.toJSON(requirement);

		} 
	    

	@RequestMapping(value = "/refreshRequirementClientList", method = RequestMethod.POST)
		public @ResponseBody ResponseList<OrgRelations> refreshClientList(HttpServletRequest request) throws Exception {
			ResponseList<OrgRelations> response = new ResponseList<>();
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if(user != null) {
				try {
					OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
					List<OrgRelations> orgClientList =orgRelationsService.getAllClientsByOrgLocationId(orgLocations.getId()); 
					for(OrgRelations ocl :orgClientList){
						/*ocl.setOrgName(ocl.getResponderLocationId().getOrgLocationName());
						ocl.setOrgid(ocl.getResponderLocationId().getOrg().getId());*/
						if(orgLocations.getId().equals(ocl.getLocationId().getId())){
							ocl.setOrgName(ocl.getResponderLocationId().getOrgLocationName());
							ocl.setLocationID(ocl.getResponderLocationId().getId().toString());
		        		}if(orgLocations.getId().equals(ocl.getResponderLocationId().getId())){
		        			ocl.setOrgName(ocl.getLocationId().getOrgLocationName());
		        			ocl.setLocationID(ocl.getLocationId().getId().toString());
		        		}
					 }
					response.setData(orgClientList);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				}catch (Exception e) {
					response.setResponse(Result.AWKWARD.name());
					response.setResponseMessage(Result.AWKWARD.getValue());
				}
			}else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		   return response;
		}
	    

	    
		/*@RequestMapping(value = "/getAllProjectsByOrgRelationIdAndInProgress", method = RequestMethod.GET)
	 	public @ResponseBody  List<Projects>  getAllProjectsByOrgRelationId(HttpServletRequest request, HttpServletResponse response) throws Exception
	 	{
	     	//String resultJson;
	     	OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	     	List<Projects> projectList = projectsService.getAllProjectsByOrgLocationIdAndStatusInProgressAndNotStarted(orgLocations.getId());
	 	   // resultJson = commonUtil.toJSON(projectList);
	 	    return projectList;
	 	}*/

	    
	@RequestMapping(value = "/saveOrUpdateAssignToTeam", method = RequestMethod.POST)
	public @ResponseBody Response<String> saveOrUpdateAssignToTeam(@RequestParam(value = "assignedIdVal", required = false) String assignedIdVal, @RequestParam(value = "requirementId", required = false) String requirementId, HttpServletRequest request) throws Exception
	{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Response<String> response =  new Response<>();
	 	if(user != null) {
			try {
				requirementAssignToService.updateRequirementAssignToListByRequirementId(assignedIdVal, requirementId, orgLocations, user);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			}catch (Exception e) {
				e.printStackTrace();
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
			}
		}else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		return response;
	}
	
	
	
	@RequestMapping(value = "/updateApplicantSourceMember", method = RequestMethod.POST)
	public String updateApplicantSourceMember(@RequestParam(value = "reqId", required = false) String reqId, HttpServletRequest request) throws Exception {
		String resultJson = null;
		List<RequirementAssignTo> requirementAssignToList = requirementAssignToService.getRequirementAssignToListByRequirementId(Long.parseLong(reqId));
		for (RequirementAssignTo requirementAssignTo : requirementAssignToList) {
			requirementAssignTo.setAssignedUserName(requirementAssignTo.getAssignUserId().getFullName());
			requirementAssignTo.setAssignedUserIdTem(requirementAssignTo.getAssignUserId().getId());
		}
		
		resultJson = toJSON(requirementAssignToList);
		return resultJson;

	}
	
	@RequestMapping(value = "/getAssignedMembersForEdit", method = RequestMethod.POST)
	public @ResponseBody Response<String> getAssignedMembersForEdit(@RequestParam(value = "reqId", required = false) String reqId, HttpServletRequest request) throws Exception
	{
		Response<String> response =  new Response<>();
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	 	if(loginUser != null) {
			try {
				List<RequirementAssignTo> requirementAssignToList = requirementAssignToService.getRequirementAssignToListByRequirementId(Long.parseLong(reqId));
				String existedUser = "";
				String comma = "";
				for (RequirementAssignTo requirementAssignTo : requirementAssignToList) {
					comma = (requirementAssignToList.indexOf(requirementAssignTo) > 0 )? "," : "";
					
					if (requirementAssignTo.getTeamId() != null) {
						existedUser = existedUser + comma+requirementAssignTo.getAssignUserId().getId().toString() + ","
								+ requirementAssignTo.getTeamId().getId().toString();
					}
					else {
						existedUser = existedUser + comma+requirementAssignTo.getAssignUserId().getId().toString()
								+ ",null";
					}
				}
				response.setObject(existedUser);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			}catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
			}
	 	}else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		return response;
	}
	

	
	@RequestMapping(value = "/shareRecomendedApplicant", method = RequestMethod.POST)
	public @ResponseBody Response<String>  shareRecomendedApplicant(@RequestParam(value = "requirementId", required = false) Long requirementId, @RequestParam(value = "candidateId", required = false) Long candidateId,HttpServletRequest request)throws Exception {
		Response<String> response = new Response<>();
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		if(loginUser != null) {
			try {
				Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);
				Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);
				requirementService.shareRecomendedApplicant(loginUser, orgLocation,requirement,candidate);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
				}catch(Exception e) {
					response.setResponse(Result.AWKWARD.name());
					response.setResponseMessage(Result.AWKWARD.getValue());
			}
		}else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		return response;
	}
	
	@RequestMapping(value = "/shareAllRecommendations", method = RequestMethod.POST)
	public @ResponseBody Response<String> shareAllRecommendations(@RequestParam(value = "requirementId", required = false) Long requirementId, @RequestParam(value = "candidateIds", required = false) String candidateIds,HttpServletRequest request)throws Exception {
		Response<String> response = new Response<>();
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		if(loginUser != null) {
			try {
				Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);
				
				String[] candidateId = candidateIds.split(",");
				if(candidateId != null &&  candidateId.length>0 && candidateId[0] != null && candidateId[0] != ""){
					for(String candidateid   : candidateId){
						Candidate candidates = candidateService.getCandidateByCandidateId(Long.valueOf(candidateid));
								JobShareCandidate jobShareCandidate = new JobShareCandidate();
								jobShareCandidate.setCandidateId(candidates);
								jobShareCandidate.setJobId(requirement);
								jobShareCandidate.setCreatedDate(new Date());
								jobShareService.saveJobShareCandidate(jobShareCandidate);
								requirementService.shareRecomendedApplicant(loginUser, orgLocation,requirement,candidates);
						}
				}
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
				}catch(Exception e) {
					response.setResponse(Result.AWKWARD.name());
					response.setResponseMessage(Result.AWKWARD.getValue());
			}
		}else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		return response;
	}
	
	
	
	@RequestMapping(value = "/submitRecommendationCandidates", method = RequestMethod.POST)
	public @ResponseBody Response<String> submitRecommendationCandidates(@RequestParam(value = "requirementId", required = false) Long requirementId, 
			@RequestParam(value = "candidateIds", required = false) String candidateIds,
			@RequestParam(value = "candidateResume", required = false) String candidateResume,
			@RequestParam(value = "N", required = false) String needApplicantPermit,
			@RequestParam(value = "N", required = false) String notifyToApplicant,
			HttpServletRequest request)throws Exception {
		Response<String> response = new Response<>();
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		List<CandidateDTO> faliureCandidateDTO = new ArrayList<>();
		
		if(loginUser != null) {
			try {
				Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);
				
				String[] candidateId = candidateIds.split(",");
				if(candidateId != null &&  candidateId.length > 0){
					for(String candidateid   : candidateId){
						
						
						boolean flag = requirementSubmissionService.getRequirementSubmissionByCandidateIdAndJobId(Long.valueOf(candidateid),requirement.getId());
						if(flag == false) {
						
							CandidateDTO candidate = candidateService.getCandidateDTOByCandidateId(Long.valueOf(candidateid));
							if(candidate.getProfilePercentage() >= requirement.getProfilePercentage()) {
								
								JSONObject labelItems = new JSONObject();
								labelItems.put("requirementId", requirementId);
								labelItems.put("reqCoolingOffPeriod", requirement.getReqCoolingOffPeriod());
								labelItems.put("candidateId", candidate.getCandId());
								labelItems.put("candidateResumeRequired", candidateResume);
								
								JSONArray array = new JSONArray();
								JSONObject firstNameItem = new JSONObject();
								firstNameItem.put("label", "First Name");
								firstNameItem.put("id", "text-"+SourceLeadRandomCodeHelper.generateRandomBulkSubmissionId());
								firstNameItem.put("name", "FirstName");
								firstNameItem.put("value", candidate.getFirstName());
								labelItems.put("FirstName", candidate.getFirstName());
								array.put(firstNameItem);
								
								JSONObject lastNameItem = new JSONObject();
								lastNameItem.put("label", "Last Name");
								lastNameItem.put("id", "text-"+SourceLeadRandomCodeHelper.generateRandomBulkSubmissionId());
								lastNameItem.put("name", "LastName");
								lastNameItem.put("value", candidate.getLastName());
								labelItems.put("LastName", candidate.getLastName());
								array.put(lastNameItem);
								
								if(StringUtils.isNotBlank(candidate.getPhoneNumber())) {
									JSONObject mobilePhoneItem = new JSONObject();
									mobilePhoneItem.put("label", "Phone Number");
									mobilePhoneItem.put("type", "text");
									mobilePhoneItem.put("id", "text-"+SourceLeadRandomCodeHelper.generateRandomBulkSubmissionId());
									mobilePhoneItem.put("name", "PhoneNumber");
									mobilePhoneItem.put("value", candidate.getPhoneNumber());
									array.put(mobilePhoneItem);
									labelItems.put("PhoneNumber", candidate.getPhoneNumber());
								}
								
								JSONObject emailItem = new JSONObject();
								emailItem.put("label", "Email Id");
								emailItem.put("type", "text");
								emailItem.put("id", "text-"+SourceLeadRandomCodeHelper.generateRandomBulkSubmissionId());
								emailItem.put("name", "EmailId");
								emailItem.put("value", candidate.getEmail());
								labelItems.put("EmailId", candidate.getEmail());
								array.put(emailItem);
								
								JSONObject locationItem = new JSONObject();
								locationItem.put("label", "Location");
								locationItem.put("type", "text");
								locationItem.put("id", "submissionLocation");
								locationItem.put("name", "location");
								locationItem.put("value", candidate.getLocation());
								labelItems.put("location", candidate.getLocation());
								array.put(locationItem);
								
								JSONObject countryItem = new JSONObject();
								countryItem.put("label", "Country");
								countryItem.put("type", "text");
								countryItem.put("id", "submissionCountry");
								countryItem.put("name", "country");
								countryItem.put("value", candidate.getCountry());
								labelItems.put("country", candidate.getCountry());
								array.put(countryItem);
								
								if(StringUtils.isNotBlank(candidate.getState())) {
									JSONObject stateItem = new JSONObject();
									stateItem.put("label", "State");
									stateItem.put("type", "text");
									stateItem.put("id", "submissionState");
									stateItem.put("name", "state");
									stateItem.put("value", candidate.getState());
									labelItems.put("state", candidate.getState());
									array.put(stateItem);
								}
								
								if(StringUtils.isNotBlank(candidate.getCity())) {
									JSONObject cityItem = new JSONObject();
									cityItem.put("label", "City");
									cityItem.put("type", "text");
									cityItem.put("id", "submissionCity");
									cityItem.put("name", "city");
									cityItem.put("value", candidate.getCity());
									labelItems.put("city", candidate.getCity());
									array.put(cityItem);
								}
								
								if(StringUtils.isNotBlank(candidate.getNoticePeriod())) {
									JSONObject npItem = new JSONObject();
									npItem.put("label", "Notice Period");
									npItem.put("type", "text");
									npItem.put("id", "select-"+SourceLeadRandomCodeHelper.generateRandomBulkSubmissionId());
									npItem.put("name", "NoticePeriod");
									npItem.put("value", candidate.getNoticePeriod());
									labelItems.put("NoticePeriod", candidate.getNoticePeriod());
									array.put(npItem);
								}
								
								if(StringUtils.isNotBlank(candidate.getCurrentRate())) {
									JSONObject ctcItem = new JSONObject();
									ctcItem.put("label", "Current CTC");
									ctcItem.put("type", "text");
									ctcItem.put("id", "select-"+SourceLeadRandomCodeHelper.generateRandomBulkSubmissionId());
									ctcItem.put("name", "CurrentCTC");
									ctcItem.put("value", candidate.getCurrentRateCurrency()+" "+candidate.getCurrentRate()+" / "+candidate.getCurrentRateType());
									labelItems.put("currentCTCRateType", candidate.getCurrentRateCurrency());
									labelItems.put("CurrentCTC", candidate.getCurrentRate());
									labelItems.put("currentCTCRatePer", candidate.getCurrentRateType());
									array.put(ctcItem);
								}
								
								if(StringUtils.isNotBlank(candidate.getExpectedRate())) {
									JSONObject ectcItem = new JSONObject();
									ectcItem.put("label", "Expected CTC");
									ectcItem.put("type", "text");
									ectcItem.put("id", "select-"+SourceLeadRandomCodeHelper.generateRandomBulkSubmissionId());
									ectcItem.put("name", "ExpectedCTC");
									ectcItem.put("value", candidate.getExpectedRateCurrencey()+" "+candidate.getExpectedRate()+" / "+candidate.getRateType());
									labelItems.put("expectedCTCRateType", candidate.getExpectedRateCurrencey());
									labelItems.put("ExpectedCTC", candidate.getExpectedRate());
									labelItems.put("expectedCTCRatePer", candidate.getRateType());
									array.put(ectcItem);
								}
								
								if(needApplicantPermit != null && !needApplicantPermit.isEmpty() && needApplicantPermit.equalsIgnoreCase("Y")) {
									labelItems.put("needApplicantPermit", "Y");
								}
								
								if(notifyToApplicant != null && !notifyToApplicant.isEmpty() && notifyToApplicant.equalsIgnoreCase("Y")) {
									labelItems.put("notifyToApplicant", "Y");
								}
								
								String con = array.toString();
								RequirementSubmission requirementSubmission = requirementSubmissionService.saveRequirementSubmission(labelItems,loginUser, orgLocation, con, loginUserRole);
						
								RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
								requirementSubmissionHistory.setToStage(requirementSubmission.getSubmissionStatus().toString());
								requirementSubmissionHistory.setFromStage("NEW");
								requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, loginUser,requirementSubmissionHistory, requirementSubmission.getCreatedBy());
								
								if(null != requirement){
									Integer totalApplicatsCountForRequirement = 0;
								   //updating total number of applicants for requrement
									totalApplicatsCountForRequirement = requirementSubmissionService.getNumberOfApplicantsForRequirement(requirement.getOrgLocationId().getId(), requirement.getId());
									if(totalApplicatsCountForRequirement > 0){
										requirement.setTotalApplicants(totalApplicatsCountForRequirement); 
										requirementService.updateRequirement(requirement);
									}else{
										requirement.setTotalApplicants(0); 
									}
									
									// pass dto as parameter
									RequirementDTO requirementDTO = commonUtil.getRequirementDTO(requirement);
									List<String> parallelApprover = new LinkedList<>();
									List<RequirementApproverHierarchy> list1 = requirementApproverHierarchyService.getRequirementApproverHierarchyListByRequirementId(requirement.getId());
									if(list1 != null && !list1.isEmpty()) {
										for (RequirementApproverHierarchy rah : list1) {
											if (rah.getHierarchyType() == HIERARCHY_TYPE.PARALLEL) {
												parallelApprover.add(rah.getApproverUserId().getId() + "");
												requirementDTO.setParallelApprover(parallelApprover);
											}
											if (rah.getHierarchyType() == HIERARCHY_TYPE.SEQUENTIAL) {
												if(rah.getLevel() == 1) {
													String sequentialApprover1 = rah.getApproverUserId().getId() + "";
													String[] levelUserIds = new String[1];
													levelUserIds[0] = sequentialApprover1;
													requirement.setLevel1ApproverUserId(levelUserIds);
													
													requirementDTO.setSequentialApprover1(sequentialApprover1);
													requirementDTO.setReqLevel1ApproverStatus(rah.getRequirementApproverStatus().name());
												}
												if(rah.getLevel() == 2) {
													String sequentialApprover2 = rah.getApproverUserId().getId() + "";
													String[] levelUserIds = new String[1];
													levelUserIds[0] = sequentialApprover2;
													requirement.setLevel2ApproverUserId(levelUserIds);
													
													requirementDTO.setSequentialApprover2(sequentialApprover2);
													requirementDTO.setReqLevel2ApproverStatus(rah.getRequirementApproverStatus().name());
												}
											}
										}
									}
									requirementElkUtil.saveElkRequirement(requirement,requirementDTO);	
								}
							}else {
								faliureCandidateDTO.add(candidate);
							}
					  }
					}
					response.setObject("success");
					response.setResponse(Result.SUCCESS.name());
					response.setResponseObject(faliureCandidateDTO);
					response.setResponseMessage(Result.SUCCESS.getValue());
				}
			}catch(Exception e) {
					response.setResponse(Result.AWKWARD.name());
					response.setResponseMessage(Result.AWKWARD.getValue());
					e.printStackTrace();
			}
		}else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		return response;
	}
	
	
	@RequestMapping(value = "/ws/assignGeneratedIdToJobFairs", method = RequestMethod.GET)
	public String assignGeneratedIdToJobFairs(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("---- Calling assignGenerateIdToJobFairs() -----");
		requirementService.assignGeneratedIdToJobFairs();
		return "Successfully added Generated Id's to JobFairs";
	}
	
	
	@RequestMapping(value = "/ws/dumpSubmissionTemplateFileds", method = RequestMethod.GET)
    public void dumpSubmissionTemplateFileds(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
		   List<RequirementSubmission> requirementSubmissionList =  requirementSubmissionService.getAllSubmissionsNotInTemplateFileds();
	       
		   if(requirementSubmissionList != null && !requirementSubmissionList.isEmpty()) {
			   for(RequirementSubmission rs : requirementSubmissionList) {
		    	   JSONArray jsonarray = new JSONArray(rs.getSubmissionTemplateJsonStringValue());
		    	   RequirementSubmissionTemplateFields rsf = new RequirementSubmissionTemplateFields();
			  		 for(int i=0; i<jsonarray.length(); i++){
			  		        JSONObject obj = jsonarray.getJSONObject(i);
			  		         rsf.setRequirementSubmission(rs);
			  		         if(obj.has("name") && obj.getString("name").equalsIgnoreCase("FirstName")) {
			  		        	rsf.setFirstName(obj.getString("value"));
			  		         }
			  		         
			  		         if(obj.has("name") && obj.getString("name").equalsIgnoreCase("LastName") && StringUtils.isNotBlank(obj.getString("value")) && obj.getString("value") != null) {
			  		        	rsf.setLastName(obj.getString("value"));
			  		         }
			  		         
			  		         if(obj.has("name") && obj.getString("name").equalsIgnoreCase("PhoneNumber") && StringUtils.isNotBlank(obj.getString("value")) && obj.getString("value") != null) {
			  		        	rsf.setPhoneNumber(obj.getString("value"));
			  		         }
			  		         
			  		         
			  		         if(obj.has("name") && obj.getString("name").equalsIgnoreCase("EmailId") && StringUtils.isNotBlank(obj.getString("value")) && obj.getString("value") != null) {
			  		        	rsf.setEmailId(obj.getString("value"));
			  		         }
			  		         
			  		         
			  		         if(obj.has("name") && obj.getString("name").equalsIgnoreCase("location") && StringUtils.isNotBlank(obj.getString("value")) && obj.getString("value") != null) {
			  		        	rsf.setLocation(obj.getString("value"));
			  		         }
			  		         
			  		         if(obj.has("name") && obj.getString("name").equalsIgnoreCase("country") && StringUtils.isNotBlank(obj.getString("value")) && obj.getString("value") != null) {
			  		        	rsf.setCountry(obj.getString("value"));
			  		         }
			  		         
			  		         if(obj.has("name") && obj.getString("name").equalsIgnoreCase("state") && StringUtils.isNotBlank(obj.getString("value")) && obj.getString("value") != null) {
			  		        	rsf.setState(obj.getString("value"));
			  		         }
			  		       
			  		         if(obj.has("name") && obj.getString("name").equalsIgnoreCase("city") && StringUtils.isNotBlank(obj.getString("value")) && obj.getString("value") != null) {
			  		        	rsf.setCity(obj.getString("value"));
			  		         }
			  		         
			  		         if(obj.has("name") && obj.getString("name").equalsIgnoreCase("Current/LastCompany") && StringUtils.isNotBlank(obj.getString("value")) && obj.getString("value") != null) {
			  		        	rsf.setCurrentORLastCompany(obj.getString("value"));
			  		         }
			  		         
			  		         if(obj.has("name") && obj.getString("name").equalsIgnoreCase("Designation") && StringUtils.isNotBlank(obj.getString("value")) && obj.getString("value") != null) {
			  		        	rsf.setDesignation(obj.getString("value"));
			  		         }
			  		         
			  		         if(obj.has("name") && obj.getString("name").equalsIgnoreCase("Education") && StringUtils.isNotBlank(obj.getString("value")) && obj.getString("value") != null) {
			  		        	rsf.setEducation(obj.getString("value"));
			  		         }
			  		         
			  		         if(obj.has("name") && obj.getString("name").equalsIgnoreCase("CurrentCTC") && StringUtils.isNotBlank(obj.getString("value")) && obj.getString("value") != null) {
			  		        	 String[] currentCTC = obj.getString("value").split(" ");
				  		        	if(currentCTC.length > 1) {
				  		        		 rsf.setCurrentRateType(currentCTC[0]);
					  		        	 rsf.setCurrentCTC(currentCTC[1]);
					  		        	 rsf.setCurrentRatePer(currentCTC[3]);
				  		        	}else {
				  		        		rsf.setCurrentCTC(obj.getString("value"));
				  		        	}
				  		        	
			  		         }
			  		       
			  		         if(obj.has("name") && obj.getString("name").equalsIgnoreCase("ExpectedCTC") && StringUtils.isNotBlank(obj.getString("value")) && obj.getString("value") != null) {
			  		        	 String[] expectedCTC = obj.getString("value").split(" ");
			  		        	 if(expectedCTC.length > 1) {
			  		        		 rsf.setExpectedRateType(expectedCTC[0]);
				  		        	 rsf.setExpectedCTC(expectedCTC[1]);
				  		        	 rsf.setExpectedRatePer(expectedCTC[3]);
			  		        	 }else {
			  		        		rsf.setExpectedCTC(obj.getString("value"));
			  		        	 }
			  		        	 
			  		         } 
			  		         
			  		        if(obj.has("name") && obj.getString("name").equalsIgnoreCase("NoticePeriod") && StringUtils.isNotBlank(obj.getString("value")) && obj.getString("value") != null) {
			  		        	rsf.setNoticePeriod(obj.getString("value"));
			  		         } 
			  		        
			  		         if(obj.has("name") && obj.getString("name").equalsIgnoreCase("WorkExperience") && StringUtils.isNotBlank(obj.getString("value")) && obj.getString("value") != null) {
			  		        	String[] experience = obj.getString("value").split(" ");
			  		        	if(experience.length > 1) {
			  		        		rsf.setWorkExperience(experience[0]+"."+experience[2]);
			  		        	}else {
			  		        		rsf.setWorkExperience(obj.getString("value"));
			  		        	}
			  		        	
			  		         } 
			  		        
			  		         if(obj.has("name") && obj.getString("name").equalsIgnoreCase("zipcode") && StringUtils.isNotBlank(obj.getString("value")) && obj.getString("value") != null) {
			  		        	rsf.setZipCode(obj.getString("value"));
			  		         } 
			  		       
			  		         if(obj.has("name") && obj.getString("name").equalsIgnoreCase("DOB") && StringUtils.isNotBlank(obj.getString("value")) && obj.getString("value") != null) {
			  		        	rsf.setDateOfBirth(commonUtil.orgLocationDateFormat("MM-dd-yyyy",obj.getString("value")));
			  		         } 
			  		       
			  		         
			  		       rsf.setStatus(1);
			  		       rsf.setCreatedBy(rs.getCreatedBy());
			  		       rsf.setUpdatedBy(rs.getUpdatedBy());
			  		       rsf.setCreatedDate(rs.getCreatedDate());
			  		       rsf.setUpdatedDate(rs.getUpdatedDate());
			  		       
			  		       requirementSubmissionTemplateFieldsService.storeRequirementSubmissionTemplateFields(rsf);
			  		 }
		  		 
		       }
		   }
		  

    }
	
	@RequestMapping(value = {"/pdfExportForJobfair", "/excelExportForJobfair"})
	public File jobFairExcelExport(HttpServletRequest request, HttpServletResponse response, JobFairDTO jobFairDTO,
			@RequestParam(defaultValue = "false") boolean includeInactive) throws Exception {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if (user != null) {
			String roleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "displayName");
			if("Admin".equalsIgnoreCase(roleName)) {
				jobFairDTO.setAdminUser(true);
			}
			
			File file = null;
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			String[] tableColumns = { "generatedId", "name", "startDate", "endDate", "noOfJobs", "", "", "", "status", "id" };

			int sortColumnIndex = 9; // by default sorting using id
			boolean sortColumnOrder = false; // by default descending order
			jobFairDTO.setSortColumn(tableColumns[sortColumnIndex]);
			jobFairDTO.setSortOrder(sortColumnOrder ? "asc" : "desc");
			jobFairDTO.setUserId(user.getId());
			jobFairDTO.setOrgLocationId(orgLocations.getId());
			jobFairDTO.setToExport(true);
			
			ResponseList<JobFairDTO> responseList = requirementService.getJobFairsList(jobFairDTO, includeInactive);
			if (responseList != null && responseList.getData() != null && !responseList.getData().isEmpty()) {
				if (request.getRequestURI().contains("excelExportForJobfair"))
					file = requirementService.getJobFairListOfExcel(responseList.getData(), user, orgLocations);
				else
					file = pdfUtil.getJobFairListOfPdf(responseList.getData(), user, orgLocations);
			}
			if(file != null) {
				String mimeType = URLConnection.guessContentTypeFromName(file.getName());
				if (mimeType == null) {
					mimeType = "application/octet-stream";
				}
				response.setContentType(mimeType);
				StringBuilder fileName = new StringBuilder();
				response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
				response.setContentLength((int) file.length());
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				FileCopyUtils.copy(inputStream, response.getOutputStream());
				inputStream.close();
				response.getOutputStream().close();
				response.getOutputStream().flush();
				if (file != null) {
					file.delete();
				}
			}
			return file;
		}
		return null;
	}
	
}