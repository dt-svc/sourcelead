package com.bis.sourcelead.rest;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.elk.index.RequirementIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementApproverHierarchy;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.RequirementInterviewHistory;
import com.bis.sourcelead.entity.RequirementOfferHistory;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementRateSubmissionNegotiation;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionCounts;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.RequirementSubmissionTemplateFields;
import com.bis.sourcelead.entity.TimeSheetSubmissionCount;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.enums.REQUIREMENT_SUBMISSION_STATUS;
import com.bis.sourcelead.enums.SubmissionRateStatus;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.RecommendationsService;
import com.bis.sourcelead.service.RequirementApproverHierarchyService;
import com.bis.sourcelead.service.RequirementAssignToService;
import com.bis.sourcelead.service.RequirementInterviewHistoryService;
import com.bis.sourcelead.service.RequirementInterviewService;
import com.bis.sourcelead.service.RequirementOfferHistoryService;
import com.bis.sourcelead.service.RequirementOffersService;
import com.bis.sourcelead.service.RequirementPipelineExportService;
import com.bis.sourcelead.service.RequirementRateSubmissionNegotiationService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.RequirementSubmissionTemplateFieldsService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

/**
 * Handles requests for the Candidate List Service.
 * 
 * @author Sammeta David
 * @date 26th August 2016
 *
 */
@RestController
public class RequirementListRestController {
	private static final Logger logger = LoggerFactory.getLogger(RequirementListRestController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	@Autowired
	CommonUtil commonUtil;
	@Autowired
	UserService userService;
	@Autowired
	RequirementService requirementService;
	@Autowired
	RequirementSubmissionService requirementSubmissionService;
	@Autowired
	CandidateResumesService candidateResumesService;
	@Autowired
	RequirementInterviewHistoryService requirementInterviewHistoryService;
	@Autowired
	RequirementAssignToService requirementAssignToService;
	@Autowired
	RequirementApproverHierarchyService requirementApproverHierarchyService;
	@Autowired
	RequirementSubmissionTemplateFieldsService requirementSubmissionTemplateFieldsService;
	@Autowired
	private RecommendationsService recommendationsService;
	@Autowired
	private CandidateService candidateService;
	@Autowired
	private RequirementInterviewService requirementInterviewService;
	@Autowired
	private RequirementOffersService requirementOffersService;
	@Autowired
	private RequirementSubmissionHistoryService requirementSubmissionHistoryService;
	@Autowired
	private RequirementRateSubmissionNegotiationService requirementRateSubmissionNegotiationService;
	@Autowired
	RequirementPipelineExportService requirementPipelineExportService;
	@Autowired
	private SourceleadELKBaseIndexer<RequirementIndex> sourceleadELKBaseIndexer;
	@Autowired
	private TimeZoneService timeZoneService;
	@Autowired
	EmailScenarioService emailScenarioService;
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	@Autowired
	RequirementOfferHistoryService requirementOfferHistoryService;
	@Autowired
	OrgLocationService orgLocationService;
	
	protected boolean isNullOrEmpty(String str)
	{
		return (str == null || "".equals(str.trim()));
	}
	
	/**
	 * @author sarath kumar
	 * @since aug 30 2016
	 * @version 0.0.1
	 * 
	 *          This method is used to for getting CandidatesList By
	 *          OrgLocationID this method is called at onload when click on
	 *          timesheet left menu
	 * 
	 * @param user
	 *            This is the first parameter to
	 *            getCandidatesListByOrgLocationID method
	 * @param navigation
	 *            This is the second parameter to
	 *            getCandidatesListByOrgLocationID method
	 * @param startDate
	 *            This is the third parameter to
	 *            getCandidatesListByOrgLocationID method
	 * 
	 * @return string
	 * 
	 * @throws NullPointer
	 */
		@PreAuthorize("hasAnyRole('ROLE_CREATE_REQUIREMENT','ROLE_VIEW_REQUIREMENT')")
	@RequestMapping(value = "/getRequirementListByOrgLocationID")
	public List<Requirement> getRequirementListByOrgLocationID(@RequestParam(value = "status", required = false) String status, @RequestParam(value = "clients", required = false) String clients, HttpServletRequest request) throws Exception
	{
		
		User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		String  userRoleName = (String)SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		
		List<Requirement>   requirementList = requirementService.getRequirementListByOrgLocationID(loginOrgLocation.getId(),status,clients,user,userRoleName);
		return requirementList;
	}
	
	/**
	 * @author sarath kumar
	 * @since sep 13 2016
	 * @version 0.0.1
	 * 
	 *          This method is used to get Requirement by requirementId .
	 * 
	 * @param requirementId
	 *            This is the first paramter to getRequirementByRequirementId
	 *            method
	 * 
	 * @return Requirement
	 * 
	 * @throws NullPointer
	 */
	@RequestMapping(value = "/getRequirementById", method = RequestMethod.POST)
	public @ResponseBody String getRequirementById(@RequestParam(value = "requirementId", required = false) String requirementId, HttpServletRequest request) throws Exception
	{
		String resultJson = null;
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		if (requirementId != null && !requirementId.isEmpty()) {
			Requirement requirement = requirementService.getRequirementByRequirementId(Long.valueOf(requirementId));
			User intiatorUser = userService.getUserById(requirement.getCreatedBy().getId());
			 //SimpleDateFormat formatter = loginOrgLocation.getLocFullFormDateFormatter();
			 if(requirement.getCreatedDate()!=null){
					requirement.setRequirementCreatedDateToString(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),requirement.getCreatedDate()));
				}

			if (null != requirement.getClosedDate()) {
				requirement.setClosedDateStringFormat(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),requirement.getClosedDate()));
			}
			// to get the RequirementApproverHierarchy to set the Status
		
			if (requirement.getSpocId() != null) {
				if (requirement.getClientId() != null) {
					if (requirement.getClientId().getInitiatorType().equalsIgnoreCase("Client")) {
						requirement.setClientName(requirement.getClientId().getLocationId().getOrgLocationName());
					}
					if (requirement.getClientId().getInitiatorType().equalsIgnoreCase("Vendor")) {
						requirement.setClientName(requirement.getClientId().getResponderLocationId().getOrgLocationName());
					}
				}
				if (requirement.getSpocId().getClientUser().getFirstName() != null
						&& requirement.getSpocId().getClientUser().getFirstName() != ""
						&& requirement.getSpocId().getClientUser().getLastName() != null
						&& requirement.getSpocId().getClientUser().getLastName() != "") {
					StringBuilder spocSB = new StringBuilder();
					requirement.setSpocName(spocSB.append(requirement.getSpocId().getClientUser().getFirstName()).append(" ").append(requirement.getSpocId().getClientUser().getLastName()).toString());
				}
				else {
					requirement.setSpocName(requirement.getSpocId().getClientUser().getFirstName());
				}
				requirement.setSpocEmail(requirement.getSpocId().getClientUser().getEmail());
				requirement.setSpocMobilePhone(requirement.getSpocId().getClientUser().getMobilePhone());
			}
			if (requirement.getManagerUserId() != null
					&& requirement.getManagerUserId().getFirstName() != null
					&& requirement.getManagerUserId().getLastName() != null) {
				StringBuilder managerSB = new StringBuilder();
				requirement.setManager(managerSB.append(requirement.getManagerUserId().getFirstName()).append(" ").append(requirement.getManagerUserId().getLastName()).toString());
			}
			// created by name display
			StringBuilder createdby = new StringBuilder();
			if (null != requirement.getCreatedBy().getLastName() && requirement.getCreatedBy().getLastName() != "undefined") {
				requirement.setCreatedByName(createdby.append(requirement.getCreatedBy().getFirstName()).append(" ").append(requirement.getCreatedBy().getLastName()).toString());
			}
			else {
				requirement.setCreatedByName(createdby.append(requirement.getCreatedBy().getFirstName()).toString());
			}
			if (requirement.getCreatedBy().getId().equals(user.getId())) {
				requirement.setIsLoginUserAsInitiator("true");
			}
			else {
				requirement.setIsLoginUserAsInitiator("false");
			}
			if (requirement.getSubmissionTemplateId() != null) {
				requirement.setClientSubmissionTemplate(requirement.getSubmissionTemplateId().getTemplateName());
			}
			
			ArrayList<String> ar = new ArrayList<String>();
			Boolean isLoginUserAsApprover = false;
			
			
			if(requirement.getApproverHierarchy() != null  && requirement.getApproverHierarchy().equalsIgnoreCase(Constants.YES)){
				
				List<RequirementApproverHierarchy> list = requirementApproverHierarchyService.getRequirementApproverHierarchyListByRequirementId(Long.parseLong(requirementId));
				if (list != null && list.size() > 0) {
					StringBuilder sb = new StringBuilder();
					for (RequirementApproverHierarchy approverN : list) {
						
						if(approverN.getApproverUserId().getId().equals(user.getId()) && isLoginUserAsApprover == false){
							requirement.setRequirementApproverHierarchyStatus(approverN.getRequirementApproverStatus().toString());
							isLoginUserAsApprover = true;
						}
						
						StringBuilder approverSB = new StringBuilder();
						if (null != approverN.getApproverUserId().getLastName()) {
							ar.add(approverSB.append(approverN.getApproverUserId().getFirstName()).append(" ").append(approverN.getApproverUserId().getLastName()).toString());
						}
						else {
							ar.add(approverSB.append(approverN.getApproverUserId().getFirstName()).toString());
						}
						requirement.setApprovalMechanismType(approverN.getHierarchyType().toString());
						if (null != approverN.getRejectedReason()
								&& !approverN.getRejectedReason().equalsIgnoreCase("")
								&& Constants.REJECTED.equalsIgnoreCase(approverN.getRequirementApproverStatus().toString())) {
							requirement.setRejectReasonForRequirement(sb.append(approverN.getRejectedReason()).toString());
						}
					}
					requirement.setApproverNames(ar);
				}
			}
			else {
				requirement.setApproverNames(ar);
			}
			
			requirement.setIsLoginUserAsApprover(isLoginUserAsApprover.toString());
			
			// UnWanted Code
			/*
			 * Boolean isLoginUserAsTeamMember = requirementAssignToService.
			 * checkLoginUserIsRequirementAssignedToByRequirementIdAndUserId(
			 * Long.parseLong(requirementId), user.getId());
			 * requirement.setIsLoginUserAsTeamMember(isLoginUserAsTeamMember.
			 * toString());
			 * 
			 * 
			 * 
			 * Boolean isLoginUserAsManager = requirementService.
			 * checkLoginUserIsRequirementManagerByRequirementIdAndUserId(Long.
			 * parseLong(requirementId), user.getId());
			 * requirement.setIsLoginUserAsManager(isLoginUserAsManager.toString
			 * ());
			 */
			resultJson = commonUtil.toJSON(requirement);
		}
		return resultJson;
	}
	
	/**
	 * @author sarath kumar
	 * @since sep 13 2016
	 * @version 0.0.1
	 * 
	 *          This method is used to get Requirement by requirementId .
	 * 
	 * @param requirementId
	 *            This is the first paramter to getRequirementByRequirementId
	 *            method
	 * 
	 * @return Requirement
	 * 
	 * @throws NullPointer
	 */
	@RequestMapping(value = "/getRequirementAssignedListByRequirementId", method = RequestMethod.POST)
	public @ResponseBody String getRequirementAssignedListByRequirementId(@RequestParam(value = "requirementId", required = false) String requirementId, HttpServletRequest request) throws Exception
	{
		String resultJson = null;
		List<RequirementAssignTo> requirementAssignToList = new ArrayList<RequirementAssignTo>();
		if (requirementId != null && !requirementId.isEmpty()) {
			Collection<RequirementAssignTo> requirementAssignToListdb = requirementAssignToService.getRequirementAssignToListByRequirementId(Long.parseLong(requirementId));
			if (requirementAssignToListdb != null && !requirementAssignToListdb.isEmpty()) {
				for (RequirementAssignTo assing : requirementAssignToListdb) {
					RequirementAssignTo ra = new RequirementAssignTo();
					StringBuilder assignUserSB = new StringBuilder();
					if (assing.getAssignUserId().getLastName() != null) {
						ra.setAssignedUserName(assignUserSB.append(assing.getAssignUserId().getFirstName()).append(" ").append(assing.getAssignUserId().getLastName()).toString());
					}
					else {
						ra.setAssignedUserName(assignUserSB.append(assing.getAssignUserId().getFirstName()).toString());
					}
					if (assing.getRoleId() != null) {
						ra.setAssignedUserRole(assing.getRoleId().getDisplayName());
					}
					if (assing.getTeamId() != null) {
						ra.setTeamName(assing.getTeamId().getName());
					}
					if (assing.getAssignUserId() != null) {
						Integer applicantCount = requirementSubmissionService.getApplicantCountByrequirementIdandAssignedUsercode(Long.parseLong(requirementId), assing.getAssignUserId().getUserCode());
						ra.setApplicantCount(applicantCount.toString());
					}
					requirementAssignToList.add(ra);
				}
			}
			resultJson = commonUtil.toJSON(requirementAssignToList);
		}
		else {
			resultJson = "failure";
		}
		return resultJson;
	}
	
	/**
	 * @author Prasanna vadla
	 * @param requirementId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSubmissionsByRequirementId", method = RequestMethod.POST)
	@ResponseBody
	String getSubmissionsByRequirementId(@RequestParam(value = "requirementId", required = false) String requirementId, @RequestParam(value = "submissionType", required = false) String submissionType, HttpServletRequest request) throws Exception
	{
		
		long startTime = System.currentTimeMillis();
		
		String resultJson = null;
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		//SimpleDateFormat dateFormat = loginOrgLocation.getLocFullFormDateFormatter();
		
		/*Timezone timezone = null;
		SimpleDateFormat dateFormat = loginOrgLocation.getLocFullFormDateFormatter();
		if (null != loginOrgLocation.getTimezone()) {
			timezone = timeZoneService.getTimezone(loginOrgLocation.getTimezone().getId());
		}*/

		Timezone timezone = (Timezone) SourceLeadSessionManager.getSessionAttribute(request, "timezone");;

		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		if (requirementId != null && !requirementId.isEmpty()) {
			List<RequirementSubmission> requirementStatusList = null;
			List<RequirementSubmission> submissionList = new ArrayList<RequirementSubmission>();
			if (Constants.All_APPLICANTS.equalsIgnoreCase(submissionType)) {
				requirementStatusList = requirementSubmissionService.getSubmissionsByRequirementId(Long.valueOf(requirementId), user, loginOrgLocation,loginUserRole);
			}
			else {
				requirementStatusList = requirementSubmissionService.getSubmissionsByRequirementIdandStatus(Long.valueOf(requirementId), user, loginOrgLocation, submissionType,loginUserRole);
			}
			if (requirementStatusList != null) {
				for (RequirementSubmission requirementSubmission : requirementStatusList) {
					if (requirementSubmission.getCandidateResumes() != null) {
						RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = null;
						Set<RequirementSubmissionTemplateFields> requirementSubmissionTemplateFieldsList = requirementSubmission.getRequirementSubmissionTemplateFieldsList();
						for(RequirementSubmissionTemplateFields templateFields : requirementSubmissionTemplateFieldsList){
							if(templateFields.getRequirementSubmission().getId().equals(requirementSubmission.getId())){
								requirementSubmissionTemplateFields = templateFields;
							}
						}
						
						
						if (submissionType != null
								&& submissionType.equalsIgnoreCase("INTERVIEW")) {
							
							/*List<RequirementSubmissionHistory> requirementSubmissionHistoryList = requirementSubmissionHistoryService.getRequirementHistoryByLocationIdAndStage(loginOrgLocation.getId(), submissionType, requirementSubmission.getId());
							if (requirementSubmissionHistoryList != null
									&& requirementSubmissionHistoryList.size() > 0) {
								RequirementSubmissionHistory requirementSubmissionHistory = requirementSubmissionHistoryList.get(0);
								requirementSubmission.setSubmittedBy(requirementSubmissionHistory.getCreatedUser());
								System.out.println(requirementSubmissionHistory.getCreatedUser());
							}*/
							
							Set<RequirementInterviewHistory> requirementInterviewHistoryList = requirementSubmission.getRequirementInterviewHistory();
							requirementSubmission.setTotalNoOfInterviews(requirementInterviewHistoryList.size());
						}
						
						if (submissionType != null
								&& submissionType.equalsIgnoreCase("OFFERMADE")) {
							
							Set<RequirementOffers> requirementOffersList = requirementSubmission.getRequirementOffersList();
							
							if (requirementOffersList.size() == 0
									&& (ApplicantSubmissionStatus.CLIENT_OFFERMADE.toString().equalsIgnoreCase(requirementSubmission.getSubmissionStatus().toString())
											|| ApplicantSubmissionStatus.CLIENT_OFFERACCEPTED.toString().equalsIgnoreCase(requirementSubmission.getSubmissionStatus().toString())
											|| ApplicantSubmissionStatus.CLIENT_OFFERREJECTED.toString().equalsIgnoreCase(requirementSubmission.getSubmissionStatus().toString())
											|| ApplicantSubmissionStatus.CLIENT_OFFERHIRED.toString().equalsIgnoreCase(requirementSubmission.getSubmissionStatus().toString()))) {

								
								//List<RequirementOfferHistory> offersList = requirementOfferHistoryService.getRequirementInternalAndIsClientOfferssBySubmissionIdAndOrgLocationsId(requirementSubmission.getId(), loginOrgLocation.getId());
								Set<RequirementOfferHistory> offersList = requirementSubmission.getRequirementOfferHistoryList();
								
								if(offersList != null){
									for (RequirementOfferHistory requirementOfferHistory : offersList) {
										RequirementOffers requirementOffershis = requirementOfferHistory.getOfferId();
										requirementSubmission.setOfferId(requirementOffershis.getId());
										if (requirementOffershis.getDateOfJoining() != null) {
											requirementSubmission.setOfferJoinDate(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),requirementOffershis.getDateOfJoining()));
										}
										if (requirementOffershis.getPositionType() != null) {
											requirementSubmission.setPositionType(requirementOffershis.getPositionType());
										}
										if (requirementOffershis.getAmount() != null) {
											requirementSubmission.setOfferRate(requirementOffershis.getAmount());
										}
										if (requirementOffershis.getFileName() != null) {
											requirementSubmission.setAttachment(requirementOffershis.getFileName());
										}

									}
								}
							}
							
							if (requirementOffersList != null && requirementOffersList.size() > 0) {
								RequirementOffers requirementOffers = null;
								for(RequirementOffers offers : requirementOffersList){
									requirementOffers = offers;
								}
								requirementSubmission.setOfferId(requirementOffers.getId());
								if (requirementOffers.getDateOfJoining() != null) {
									requirementSubmission.setOfferJoinDate(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),requirementOffers.getDateOfJoining()));
								}
								if (requirementOffers.getPositionType() != null) {
									requirementSubmission.setPositionType(requirementOffers.getPositionType());
								}
								if (requirementOffers.getAmount() != null) {
									requirementSubmission.setOfferRate(requirementOffers.getAmount());
								}
								if (requirementOffers.getFileName() != null) {
									requirementSubmission.setAttachment(requirementOffers.getFileName());
								}
							}
						}
						String candidateName = "";
						if (requirementSubmissionTemplateFields != null) {
							if (!isNullOrEmpty(requirementSubmissionTemplateFields.getLastName())) {
								StringBuilder candidateNameSB = new StringBuilder();
								candidateName = candidateNameSB.append(requirementSubmissionTemplateFields.getFirstName()).append(" ").append(requirementSubmissionTemplateFields.getLastName()).toString();
							}
							else {
								candidateName = requirementSubmissionTemplateFields.getFirstName();
							}
							requirementSubmission.setCandidateName(candidateName);
							requirementSubmission.setCandidateEmail(requirementSubmissionTemplateFields.getEmailId());
							/*
							 * if(requirementSubmission.getFinalizedRate()
							 * !=null &&
							 * !"".equalsIgnoreCase(requirementSubmission.
							 * getFinalizedRate())){
							 * requirementSubmission.setExpectedCTC(
							 * requirementSubmission.getFinalizedRate()); }else{
							 * requirementSubmission.setExpectedCTC(
							 * requirementSubmissionTemplateFields.
							 * getExpectedCTC()); }
							 */
							requirementSubmission.setExpectedCTC(requirementSubmissionTemplateFields.getExpectedCTC());
							requirementSubmission.setWorkExperience(requirementSubmissionTemplateFields.getWorkExperience());
							requirementSubmission.setPhoneNumber(requirementSubmissionTemplateFields.getPhoneNumber());
						}
						requirementSubmission.setCandidateId(requirementSubmission.getCandidate().getId().toString());
						/*
						 * if(Constants.RECORD_ACTIVE ==
						 * requirementSubmission.getCandidateResumes().getStatus
						 * ()){ requirementSubmission.setResumeId(
						 * requirementSubmission.getCandidateResumes().getId());
						 * }
						 */
						requirementSubmission.setResumeId(requirementSubmission.getCandidateResumes().getId());
						requirementSubmission.setResumeUUID(requirementSubmission.getCandidateResumes().getResumeUUID());
						if (requirementSubmission.getVendorLocationId() != null) {
							requirementSubmission.setOrgName(requirementSubmission.getVendorLocationId().getOrgLocationName());
						}
						else {
							if (requirementSubmission.getRecruiterUserId() == null
									&& requirementSubmission.getCandidate().getCandidateType().equalsIgnoreCase("Professional")) {
								requirementSubmission.setCandidateSource(requirementSubmission.getCandidate().getCandidateType());
							}
							if (requirementSubmission.getRecruiterUserId() != null) {
								requirementSubmission.setCandidateSource("Individual Recruiter");
							}
						}
						if (requirementSubmission.getCandidateResumes() != null
								&& requirementSubmission.getCandidateResumes().getFileName() != null) {
							requirementSubmission.setFilename(requirementSubmission.getCandidateResumes().getFileName());
						}
						
						RequirementRateSubmissionNegotiation pastRequirementRateSubmissionNegotiation = null;
						if (null != submissionType
								&& submissionType.equalsIgnoreCase("SUBMIT_TO_CLIENT")) {
							/*
							 * if(requirementSubmission.getVendorLocationId() !=
							 * null){ requirementSubmission.setOrgName(
							 * requirementSubmission.getVendorLocationId().
							 * getOrgLocationName()); }
							 */
							RequirementSubmission clientSubmission = requirementSubmissionService.getClientSubmissionsBySubmissionIdAndOrgLocationId(requirementSubmission.getId(), requirementSubmission.getOrgLocations().getId());
							if (clientSubmission != null) {
								requirementSubmission.setClientSubmissionId(clientSubmission.getId().toString());
								pastRequirementRateSubmissionNegotiation = requirementRateSubmissionNegotiationService.getLatestRequirementRateSubmissionNegotiationBySubmissionId(clientSubmission.getId());
								if (pastRequirementRateSubmissionNegotiation != null) {
									requirementSubmission.setProposedSubmissionRate(pastRequirementRateSubmissionNegotiation.getExpectedRate());
									requirementSubmission.setSubmitToClientRate(pastRequirementRateSubmissionNegotiation.getRequirementsubmissionId().getFinalizedRate());
								}
							}
						}else{
							pastRequirementRateSubmissionNegotiation = requirementRateSubmissionNegotiationService.getLatestRequirementRateSubmissionNegotiationBySubmissionId(requirementSubmission.getId());
						}
						
					/*	RequirementSubmission clientSubmission = requirementSubmissionService.getClientRequierementSubmission(requirementSubmission.getId());
						RequirementRateSubmissionNegotiation pastRequirementRateSubmissionNegotiation = null;
						if (clientSubmission != null
								&& submissionType.equalsIgnoreCase("SUBMIT_TO_CLIENT")) {
							pastRequirementRateSubmissionNegotiation = requirementRateSubmissionNegotiationService.getLatestRequirementRateSubmissionNegotiationBySubmissionId(clientSubmission.getId());
						}
						else {
							pastRequirementRateSubmissionNegotiation = requirementRateSubmissionNegotiationService.getLatestRequirementRateSubmissionNegotiationBySubmissionId(requirementSubmission.getId());
						}
						*/
						if (pastRequirementRateSubmissionNegotiation != null) {
							if (pastRequirementRateSubmissionNegotiation.getRateInitiatorType().equals(SubmissionRateStatus.VENDOR)
									|| pastRequirementRateSubmissionNegotiation.getRateInitiatorType().equals(SubmissionRateStatus.CLIENT)) {
								requirementSubmission.setProposedSubmissionRate(pastRequirementRateSubmissionNegotiation.getExpectedRate());
							}
							else {
								requirementSubmission.setExpectedRate(pastRequirementRateSubmissionNegotiation.getExpectedRate());
							}
							requirementSubmission.setLastRateIniLocId(pastRequirementRateSubmissionNegotiation.getInitiatorOrgLocationId().getId().toString());
							requirementSubmission.setLastRateIniType(pastRequirementRateSubmissionNegotiation.getRateInitiatorType().getValue());
							requirementSubmission.setLastRateIniUserCode(pastRequirementRateSubmissionNegotiation.getCreatedBy());
							requirementSubmission.setLastRateStage(pastRequirementRateSubmissionNegotiation.getRateStage().getValue());
						}
						if (requirementSubmission.getCreatedDate() != null) {
							String currentTimeStr = Constants.TZ_DF.format(requirementSubmission.getCreatedDate());
							if (null != timezone && null != timezone.getTimeZoneId()) {
								String currentDateInTimezone = CommonUtil.convertTimeZones("UTC", timezone.getTimeZoneId(), currentTimeStr);
								DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
								currentDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),converteDate);
								requirementSubmission.setCreatedDateStr(currentDateInTimezone);
								SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
								String time = localDateFormat.format(converteDate);
								requirementSubmission.setCreatedTimeStr(time);
							}
						}
						if (requirementSubmission.getUpdatedDate() != null) {
							String currentTimeStr = Constants.TZ_DF.format(requirementSubmission.getUpdatedDate());
							if (null != timezone && null != timezone.getTimeZoneId()) {
								String currentDateInTimezone = CommonUtil.convertTimeZones("UTC", timezone.getTimeZoneId(), currentTimeStr);
								DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
								currentDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),converteDate);
								requirementSubmission.setUpDatedStr(currentDateInTimezone);
							}
						}
						/*
						 * if(requirementSubmission.getCreatedDate() != null){
						 * requirementSubmission.setCreatedDateStr(Constants.
						 * DF_YMD.format(requirementSubmission.getCreatedDate())
						 * ); }
						 */
						/*
						 * if(requirementSubmission.getUpdatedDate() != null){
						 * requirementSubmission.setUpDatedStr(Constants.DF_YMD.
						 * format(requirementSubmission.getUpdatedDate())); }
						 */
						submissionList.add(requirementSubmission);
					}
				}
			}
			resultJson = commonUtil.toJSON(submissionList);
			
			long endTime = System.currentTimeMillis();
			long actualmills = (endTime - startTime);
			System.out.println("totalTimeMillis===========>"+actualmills);
		}
		return resultJson;
	}
	
	@RequestMapping(value = "/saveChangedStatus", method = RequestMethod.POST)
	@ResponseBody
	String saveChangedStatus(@RequestParam(value = "requirementId", required = false) Long requirementId, @RequestParam(value = "status", required = false) String status, HttpServletRequest request) throws Exception
	{
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		// authorizing if the login user as Team Member Or Not
		Boolean isLoginUserAsTeamMember = requirementAssignToService.checkLoginUserIsRequirementAssignedToByRequirementIdAndUserId(requirementId, loginUser.getId());
		if (isLoginUserAsTeamMember) {
			return "false";
		}
		else {
			Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);
			REQUIREMENT_SUBMISSION_STATUS previousStatus = requirement.getRequirementSubmissionStatus();
			REQUIREMENT_SUBMISSION_STATUS changedStatus = Enum.valueOf(REQUIREMENT_SUBMISSION_STATUS.class, status);
			requirement.setRequirementSubmissionStatus(changedStatus);
			requirement.setRequirementSubmissionPreviousStatus(previousStatus);
			requirement.setUpdatedBy(loginUser.getUserCode());
			requirement.setUpdatedDate(commonUtil.currentDate());
			requirementService.addRequirement(requirement);
			StringBuilder reqStatusChange = new StringBuilder();
			SourceLeadSessionManager.setSessionAttribute(request, "reqStatusChange", reqStatusChange.append(" ").append(requirement.getJobTitle()).append(" Status changed ").append(requirement.getRequirementSubmissionPreviousStatus()).append(" to ").append(requirement.getRequirementSubmissionStatus()).append(" ").toString());
			modifiedReqStatusMailtoVendorContact(requirement, loginUser, loginOrgLocation);
		}
		return "true";
	}
	
	@RequestMapping(value = "/saveChangedStatusFromDraft", method = RequestMethod.POST)
	@ResponseBody
	String saveChangedStatusFromDraft(@RequestParam(value = "requirementId", required = false) Long requirementId, @RequestParam(value = "status", required = false) String status, @RequestParam(value = "userId", required = true) Long userId) throws Exception
	{
		User loginUser = userService.getUserById(userId);
		// authorizing if the login user as Team Member Or Not
		Boolean isLoginUserAsTeamMember = requirementAssignToService.checkLoginUserIsRequirementAssignedToByRequirementIdAndUserId(requirementId, loginUser.getId());
		if (isLoginUserAsTeamMember) {
			return "false";
		}
		else {
			Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);
			REQUIREMENT_SUBMISSION_STATUS previousStatus = requirement.getRequirementSubmissionStatus();
			REQUIREMENT_SUBMISSION_STATUS changedStatus = Enum.valueOf(REQUIREMENT_SUBMISSION_STATUS.class, status);
			requirement.setRequirementSubmissionStatus(changedStatus);
			requirement.setRequirementSubmissionPreviousStatus(previousStatus);
			requirement.setManagerUserId(loginUser);
			requirement.setUpdatedBy(loginUser.getUserCode());
			requirement.setUpdatedDate(commonUtil.currentDate());
			requirementService.addRequirement(requirement);
		}
		return "true";
	}
	
	@RequestMapping(value = "/changeStatusAuthorisation", method = RequestMethod.POST)
	@ResponseBody
	String changeStatusAuthorisation(@RequestParam(value = "requirementId", required = false) Long requirementId, @RequestParam(value = "status", required = false) String status, @RequestParam(value = "userId", required = false) Long userId) throws Exception
	{
		// authorizing if the login user as Team Member Or Not
		Boolean isAllowedAccessAsTeamMember = true;
		Boolean isLoginUserAsTeamMember = requirementAssignToService.checkLoginUserIsRequirementAssignedToByRequirementIdAndUserId(requirementId, userId);
		if (isLoginUserAsTeamMember) {
			isAllowedAccessAsTeamMember = false;
		}
		Boolean isLoginUserAsApprover = false;
		RequirementApproverHierarchy requirementApproverHierarchy = requirementApproverHierarchyService.getRequirementApproverHierarchyByRequirementId(requirementId, userId);
		if (requirementApproverHierarchy != null) {
			isLoginUserAsApprover = true;
		}
		Boolean isLoginUserAsManager = requirementService.checkLoginUserIsRequirementManagerByRequirementIdAndUserId(requirementId, userId);
		if (isAllowedAccessAsTeamMember || isLoginUserAsApprover || isLoginUserAsManager) {
			return "true";
		}
		else {
			return "false";
		}
	}
	
	/**
	 * @author Rajashekar used fro export xls and pdf
	 * @param status
	 * @param clients
	 * @param exportType
	 * @param request
	 * @return void
	 * @throws Exception
	 */
	@PreAuthorize("hasAnyRole('ROLE_EXPORT_REQUIREMENTS')")
	@RequestMapping(value = "/getRequirementExportListByOrgLocationID")
	public void getRequirementExportListByOrgLocationID(@RequestParam(value = "status", required = false) String status, @RequestParam(value = "clients", required = false) String clients, @RequestParam(value = "exportType", required = false) String exportType, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// String resultJson;
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		List<Requirement> requirementList = requirementService.getRequirementListByOrgLocationID(loginOrgLocation.getId(), status, clients, user, userRoleName);
		String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		File file = null;
		TimeSheetSubmissionCount requirementSubCount = getRequirementCount(requirementList);
		for (Requirement requirement : requirementList) {
			if (requirement.getClientId() != null) {
				if (requirement.getClientId().getResponderType().equalsIgnoreCase("Client")) {
					if (requirement.getClientId().getResponderLocationId() != null) {
						StringBuilder sb = new StringBuilder();
						requirement.setClientName(requirement.getClientId().getResponderLocationId().getOrgLocationName());
						if (requirement.getSpocId().getClientUser().getFirstName() != null
								&& requirement.getSpocId().getClientUser().getLastName() != null) {
							sb.append(requirement.getSpocId().getClientUser().getFirstName()).append(" ").append(requirement.getSpocId().getClientUser().getLastName());
						}
						else {
							sb.append(requirement.getSpocId().getClientUser().getFirstName());
						}
						requirement.setClientContactName(sb.toString());
						requirement.setClientContactEmail(requirement.getSpocId().getClientUser().getEmail());
					}
				}
				/*
				 * if(requirement.getClientId().getResponderType().
				 * equalsIgnoreCase("Vendor")){
				 * requirement.setClientName(requirement.getClientId().
				 * getLocationId().getOrgLocationName()); }
				 */
				if (requirement.getClientId().getResponderType().equalsIgnoreCase("Vendor")) {
					StringBuilder sb1 = new StringBuilder();
					requirement.setClientName(requirement.getClientId().getLocationId().getOrgLocationName());
					if (requirement.getSpocId().getVendorUser().getFirstName() != null
							&& requirement.getSpocId().getVendorUser().getLastName() != null) {
						sb1.append(requirement.getSpocId().getVendorUser().getFirstName()).append(" ").append(requirement.getSpocId().getVendorUser().getLastName());
					}
					else {
						sb1.append(requirement.getSpocId().getVendorUser().getFirstName());
					}
					requirement.setClientContactName(sb1.toString());
					requirement.setClientContactEmail(requirement.getSpocId().getVendorUser().getEmail());
				}
			}
			if (requirement.getManagerUserId() != null) {
				String managerFirstName = "";
				if (StringUtils.isNotEmpty(requirement.getManagerUserId().getFirstName())) {
					managerFirstName = requirement.getManagerUserId().getFirstName();
				}
				String managerLastName = "";
				if (StringUtils.isNotEmpty(requirement.getManagerUserId().getLastName())) {
					managerLastName = requirement.getManagerUserId().getLastName();
				}
				StringBuilder managerSB = new StringBuilder();
				requirement.setManager(managerSB.append(managerFirstName).append(" ").append(managerLastName).toString());
			}
			String isAllowStageAccess = "false";
			if(requirement.getManagerUserId()!= null && user.getId().equals(requirement.getManagerUserId().getId())){
				isAllowStageAccess = "true";
			}
			else{
				RequirementApproverHierarchy requirementApproverHierarchy = requirementApproverHierarchyService.getRequirementApproverHierarchyByRequirementId(requirement.getId(), user.getId());
				if (requirementApproverHierarchy != null) {
					isAllowStageAccess = "true";
				}
			}
			
			String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
			if(loginUserRole.equalsIgnoreCase(Constants.ROLE_ADMIN)){
				isAllowStageAccess = "true";
			}
			
			requirement.setIsAllowStageAccess(isAllowStageAccess);
		}
		List<Requirement> requirementList1 = new ArrayList<Requirement>();
		String[] status1 = status.split(",");
		if (status1[0] != null && status1[0].length() > 0) {
			for (String stat : status1) {
				for (Requirement requirement : requirementList) {
					if (stat.equalsIgnoreCase(requirement.getRequirementSubmissionStatus().toString())) {
						if (requirement.getClientId() != null) {
							if (requirement.getClientId().getResponderType().equalsIgnoreCase("Client")) {
								StringBuilder sb3 = new StringBuilder();
								requirement.setClientName(requirement.getClientId().getResponderLocationId().getOrgLocationName());
								if (requirement.getSpocId().getClientUser().getFirstName() != null
										&& requirement.getSpocId().getClientUser().getLastName() != null) {
									sb3.append(requirement.getSpocId().getClientUser().getFirstName()).append(" ").append(requirement.getSpocId().getClientUser().getLastName());
								}
								else {
									sb3.append(requirement.getSpocId().getClientUser().getFirstName());
								}
								requirement.setClientContactName(sb3.toString());
								requirement.setClientContactEmail(requirement.getSpocId().getClientUser().getEmail());
							}
							if (requirement.getClientId().getResponderType().equalsIgnoreCase("Vendor")) {
								StringBuilder sb4 = new StringBuilder();
								requirement.setClientName(requirement.getClientId().getLocationId().getOrgLocationName());
								if (requirement.getSpocId().getVendorUser().getFirstName() != null
										&& requirement.getSpocId().getVendorUser().getLastName() != null) {
									sb4.append(requirement.getSpocId().getVendorUser().getFirstName()).append(" ").append(requirement.getSpocId().getVendorUser().getLastName());
								}
								else {
									sb4.append(requirement.getSpocId().getVendorUser().getFirstName());
								}
								requirement.setClientContactName(sb4.toString());
								requirement.setClientContactEmail(requirement.getSpocId().getVendorUser().getEmail());
							}
						}
						if (requirement.getManagerUserId() != null) {
							String managerFirstName = "";
							if (StringUtils.isNotEmpty(requirement.getManagerUserId().getFirstName())) {
								managerFirstName = requirement.getManagerUserId().getFirstName();
							}
							String managerLastName = "";
							if (StringUtils.isNotEmpty(requirement.getManagerUserId().getLastName())) {
								managerLastName = requirement.getManagerUserId().getLastName();
							}
							StringBuilder managerSB1 = new StringBuilder();
							requirement.setManager(managerSB1.append(managerFirstName).append(" ").append(managerLastName).toString());
						}
						requirementList1.add(requirement);
					}
				}
			}
			file = requirementService.getExportRequirementFile(requirementList1, loginOrgLocation.getOrgLocationName(), exportType, requirementSubCount);
		}
		else {
			file = requirementService.getExportRequirementFile(requirementList, loginOrgLocation.getOrgLocationName(), exportType, requirementSubCount);
		}
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
	
	/**
	 * @author Rajashekar Returns requirements count
	 * @param loginOrgLocations
	 * @param userRole
	 * @param user
	 * @return TimeSheetSubmissionCount pojo
	 * @throws Exception
	 */
	private TimeSheetSubmissionCount getRequirementCount(List<Requirement> requirementList) throws Exception
	{
		TimeSheetSubmissionCount timeSheetSubmissionCount = new TimeSheetSubmissionCount();
		Integer newCount = 0;
		Integer draftCount = 0;
		Integer submittedCount = 0;
		Integer openCount = 0;
		Integer onholdCount = 0;
		Integer cancelCount = 0;
		for (Requirement req : requirementList) {
			if ("NEW".equalsIgnoreCase(req.getRequirementSubmissionStatus().toString())) {
				newCount++;
			}
			if ("Draft".equalsIgnoreCase(req.getRequirementSubmissionStatus().toString())) {
				draftCount++;
			}
			if ("submitted".equalsIgnoreCase(req.getRequirementSubmissionStatus().toString())) {
				submittedCount++;
			}
			if ("open".equalsIgnoreCase(req.getRequirementSubmissionStatus().toString())) {
				openCount++;
			}
			if ("onhold".equalsIgnoreCase(req.getRequirementSubmissionStatus().toString())) {
				onholdCount++;
			}
			if ("CANCEL".equalsIgnoreCase(req.getRequirementSubmissionStatus().toString())) {
				cancelCount++;
			}
		}
		timeSheetSubmissionCount.setNewRequirementCount(newCount);
		timeSheetSubmissionCount.setDraftRequirementCount(draftCount);
		timeSheetSubmissionCount.setSubmittedRequirementCount(submittedCount);
		timeSheetSubmissionCount.setOpenRequirementCount(openCount);
		timeSheetSubmissionCount.setOnHoldRequirementCount(onholdCount);
		timeSheetSubmissionCount.setCancelRequirementCount(cancelCount);
		return timeSheetSubmissionCount;
	}
	
	/**
	 * @author sarath kumar
	 * 
	 *         This method is used to download attachment of requirement
	 * 
	 * @param requirementId
	 * @param request
	 * 
	 * @return resultJson
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/downloadRequirementAttachment", method = RequestMethod.GET)
	public void downloadRequirementAttachment(@RequestParam(value = "requirementId", required = false) String requirementId, HttpServletResponse response) throws Exception
	{
		Requirement requirement = requirementService.getRequirementByRequirementId(Long.parseLong(requirementId));
		response.setContentType("application/octet-stream");
		StringBuilder fileName = new StringBuilder();
		response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(requirement.getFileName()).append("\"").toString());
		InputStream inputStream = new ByteArrayInputStream(requirement.getDocFile());
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
	
	/**
	 * @author Sammeta David Raju
	 * 
	 *         This method is used get RequirementListCount Based On Status
	 * 
	 * @param requirementId
	 * @param request
	 * 
	 * @return resultJson
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getRequirementListCountBasedOnStatus", method = RequestMethod.GET)
	@ResponseBody
	public String getRequirementByListCountBasedOnStatus(HttpServletRequest request) throws Exception
	{
		String resultJson;
		OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		TimeSheetSubmissionCount timeSheetSubmissionCount = new TimeSheetSubmissionCount();
		Integer newCount = 0;
		Integer draftCount = 0;
		Integer submittedCount = 0;
		Integer openCount = 0;
		Integer onholdCount = 0;
		Integer cancelCount = 0;
		List<Requirement> requirementList = requirementService.getRequirementListByOrgLocationID(loginOrgLocations.getId(), null, null, user, userRoleName);
		for (Requirement req : requirementList) {
			if ("NEW".equalsIgnoreCase(req.getRequirementSubmissionStatus().toString())) {
				newCount++;
			}
			if ("Draft".equalsIgnoreCase(req.getRequirementSubmissionStatus().toString())) {
				draftCount++;
			}
			if ("submitted".equalsIgnoreCase(req.getRequirementSubmissionStatus().toString())) {
				submittedCount++;
			}
			if ("open".equalsIgnoreCase(req.getRequirementSubmissionStatus().toString())) {
				openCount++;
			}
			if ("onhold".equalsIgnoreCase(req.getRequirementSubmissionStatus().toString())) {
				onholdCount++;
			}
			if ("CANCEL".equalsIgnoreCase(req.getRequirementSubmissionStatus().toString())) {
				cancelCount++;
			}
		}
		timeSheetSubmissionCount.setNewRequirementCount(newCount);
		timeSheetSubmissionCount.setDraftRequirementCount(draftCount);
		timeSheetSubmissionCount.setSubmittedRequirementCount(submittedCount);
		timeSheetSubmissionCount.setOpenRequirementCount(openCount);
		timeSheetSubmissionCount.setOnHoldRequirementCount(onholdCount);
		timeSheetSubmissionCount.setCancelRequirementCount(cancelCount);
		resultJson = commonUtil.toJSON(timeSheetSubmissionCount);
		return resultJson;
	}
	
	/**
	 * @author update:shivayogi
	 * 
	 * @param requirementId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Async
	@RequestMapping(value = "/getSubmissionsCountBySubmissionStatus", method = RequestMethod.POST)
	@ResponseBody
	String getSubmissionsCountBySubmissionStatus(@RequestParam(value = "requirementId", required = false) String requirementId, @RequestParam(value = "submissionType", required = false) String submissionType, HttpServletRequest request) throws Exception
	{
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String resultJson = null;
		/*
		 * int numOfShortList = 0; int numOfInterview = 0; int
		 * numOfSubmitToClient = 0; int numOfOffers = 0; int numOfRejected = 0;
		 * int numofrequestsent = 0;
		 */
		if (requirementId != null && !requirementId.isEmpty()) {
			Integer applicantsCount = requirementSubmissionService.getAllPipeLineCountsByRequirementIdAndSubmissionStage(Long.valueOf(requirementId), user, loginOrgLocation, submissionType);
			/*
			 * List<RequirementSubmission> requirementSubmissions =
			 * requirementSubmissionService.getSubmissionsByRequirementId(Long.
			 * valueOf(requirementId),user,loginOrgLocation);
			 * List<RequirementSubmission> duplicateCount =
			 * requirementSubmissionService.duplicateCandidateCountList(Long.
			 * parseLong(requirementId), user, loginOrgLocation);
			 * 
			 * if(requirementSubmissions !=null && requirementSubmissions.size()
			 * >0){ for(RequirementSubmission rmentSubmission
			 * :requirementSubmissions){
			 * 
			 * if("SHORTLIST".equalsIgnoreCase(rmentSubmission.
			 * getSubmissionStatus().toString())){ numOfShortList++; } else
			 * if("INTERVIEW".equalsIgnoreCase(rmentSubmission.
			 * getSubmissionStatus().toString()) ||
			 * "CLIENT_INTERVIEW".equalsIgnoreCase(rmentSubmission.
			 * getSubmissionStatus().toString())){ numOfInterview++; } else
			 * if("SUBMIT_TO_CLIENT".equalsIgnoreCase(rmentSubmission.
			 * getSubmissionStatus().toString())){ numOfSubmitToClient++; } else
			 * if("OFFERMADE".equalsIgnoreCase(rmentSubmission.
			 * getSubmissionStatus().toString()) ||
			 * "OFFERACCEPTED".equalsIgnoreCase(rmentSubmission.
			 * getSubmissionStatus().toString()) ||
			 * "OFFERREJECTED".equalsIgnoreCase(rmentSubmission.
			 * getSubmissionStatus().toString()) ||
			 * "CLIENT_OFFERMADE".equalsIgnoreCase(rmentSubmission.
			 * getSubmissionStatus().toString()) ||
			 * "CLIENT_OFFERACCEPTED".equalsIgnoreCase(rmentSubmission.
			 * getSubmissionStatus().toString()) ||
			 * "CLIENT_OFFERREJECTED".equalsIgnoreCase(rmentSubmission.
			 * getSubmissionStatus().toString()) ){ numOfOffers++; }else
			 * if("REJECTED".equalsIgnoreCase(rmentSubmission.
			 * getSubmissionStatus().toString())){ numOfRejected++; }else
			 * if("REQUEST_SENT".equalsIgnoreCase(rmentSubmission.
			 * getSubmissionStatus().toString())){ numofrequestsent++; } }
			 * for(RequirementSubmission rmentSubmission
			 * :requirementSubmissions){
			 * rmentSubmission.setNumOfSubmissions(requirementSubmissions.size()
			 * ); rmentSubmission.setNumOfShortList(numOfShortList);
			 * rmentSubmission.setNumOfInterview(numOfInterview);
			 * rmentSubmission.setNumOfSubmitToClient(numOfSubmitToClient);
			 * rmentSubmission.setNumOfOffers(numOfOffers);
			 * rmentSubmission.setNumOfRejected(numOfRejected);
			 * rmentSubmission.setNumofrequestsent(numofrequestsent);
			 * if(duplicateCount != null){
			 * rmentSubmission.setDuplicateCount(duplicateCount.size()); } } }
			 * resultJson = commonUtil.toJSON(requirementSubmissions);
			 */
			resultJson = commonUtil.toJSON(applicantsCount);
		}
		return resultJson;
	}
	
	/**
	 * @author Prasad salina
	 * @param requirementId
	 * @param submissionType
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Async
	@RequestMapping(value = "/getDuplicateSubmissionsCountByRequirementId", method = RequestMethod.POST)
	@ResponseBody
	String getDuplicateSubmissionsCountByRequirementId(@RequestParam(value = "requirementId", required = false) String requirementId, HttpServletRequest request) throws Exception
	{
		String resultJson = null;
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if (requirementId != null && !requirementId.isEmpty()) {
			Integer duplicateApplicantscount = requirementSubmissionService.duplicateCandidateCountsByRequirementId(Long.valueOf(requirementId), user, loginOrgLocation);
			resultJson = commonUtil.toJSON(duplicateApplicantscount);
		}
		return resultJson;
	}
	
	/**
	 * @author Rajashekar It downloads the attachment of offer based on offer id
	 * @param offerId
	 * @param response
	 * @throws Exception
	 * @returns void
	 */
	@RequestMapping(value = "/downloadOfferAttachment")
	public void downloadOfferAttachment(@RequestParam(value = "offerId", required = false) Long offerId, HttpServletResponse response) throws Exception
	{
		RequirementOffers offer = requirementOffersService.getRequirementOfferId(offerId);
		response.setContentType("application/octet-stream");
		StringBuilder fileName = new StringBuilder();
		response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(offer.getFileName()).append("\"").toString());
		InputStream inputStream = new ByteArrayInputStream(offer.getDocFile());
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
	
	@PreAuthorize("hasAnyRole('ROLE_CREATE_REQUIREMENT_TEMPLATE','ROLE_VIEW_EDIT_REQUIREMENT_TEMPLATE','ROLE_VIEW_DELETE_REQUIREMENT_TEMPLATE')")
	@RequestMapping(value = "/getRequirementTemplateListByOrgLocationsId", method = RequestMethod.POST)
	public @ResponseBody String getRequirementTemplateListByOrgLocationsId(HttpServletRequest request) throws Exception
	{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<Requirement> reqTemplatesList = requirementService.getAllRequirementTeplateJobsByOrgLocationId(loginOrgLocation.getId(), user);
		String resultJson;
		resultJson = commonUtil.toJSON(reqTemplatesList);
		return resultJson;
	}
	
	@PreAuthorize("hasAnyRole('ROLE_VIEW_EDIT_REQUIREMENT_TEMPLATE','ROLE_VIEW_DELETE_REQUIREMENT_TEMPLATE')")
	@RequestMapping(value = "/getRequirementTemplateByRequirementId", method = RequestMethod.POST)
	public @ResponseBody String getRequirementTemplateByRequirementId(@RequestParam(value = "requirementId", required = false) Long requirementId, HttpServletRequest request) throws Exception
	{
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Requirement requirement = requirementService.getRequirementTemplateByRequirementId(requirementId, loginOrgLocation.getId());
		String resultJson;
		resultJson = commonUtil.toJSON(requirement);
		return resultJson;
	}
	
	@PreAuthorize("hasAnyRole('ROLE_EXPORT_PIPELINE')")
	@RequestMapping(value = "/getSubmissionsExportByRequirementId")
	@ResponseBody
	void getSubmissionsExportByRequirementId(@RequestParam(value = "requirementId", required = false) String requirementId, @RequestParam(value = "submissionType", required = false) String submissionType, @RequestParam(value = "exportType", required = false) String exportType, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		File file = null;
		String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		//SimpleDateFormat simpleDateFormat = loginOrgLocation.getLocFullFormDateFormatter();
		RequirementSubmissionCounts requirementSubmissionCounts = getRequirementSubmissionsCount(Long.valueOf(requirementId), user, loginOrgLocation,loginUserRole);
		if (requirementId != null && !requirementId.isEmpty()) {
			List<RequirementSubmission> requirementStatusList = null;
			if (Constants.All_APPLICANTS.equalsIgnoreCase(submissionType)) {
				requirementStatusList = requirementSubmissionService.getSubmissionsByRequirementId(Long.valueOf(requirementId), user, loginOrgLocation,loginUserRole);
			} /*
				 * else if(Constants.DUPLICATE_APPLICANTS.equalsIgnoreCase(
				 * submissionType)){ requirementStatusList =
				 * requirementSubmissionService.
				 * getSubmissionsByRequirementIdandStatus(Long.valueOf(
				 * requirementId),user,loginOrgLocation,submissionType); //
				 * requirementStatusList =
				 * requirementSubmissionService.getSubmissionsByRequirementId(
				 * Long.valueOf(requirementId),user,loginOrgLocation); }
				 */
			else {
				requirementStatusList = requirementSubmissionService.getSubmissionsByRequirementIdandStatus(Long.valueOf(requirementId), user, loginOrgLocation, submissionType,loginUserRole);
				// requirementStatusList =
				// requirementSubmissionService.getSubmissionsByRequirementId(Long.valueOf(requirementId),user,loginOrgLocation);
			}
			if (requirementStatusList != null) {
				for (RequirementSubmission requirementSubmission : requirementStatusList) {
					if (requirementSubmission.getCandidateResumes() != null) {
						CandidateResumes candidateResumes = candidateResumesService.getCandidateResumeByResumeId(requirementSubmission.getCandidateResumes().getId());
						RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(requirementSubmission.getId());
						List<RequirementInterview> numberOfInterviews = requirementInterviewService.getRequirementInterviewsBySubmissionIdAndOrgLocationsId(requirementSubmission.getId(), loginOrgLocation.getId());
						List<RequirementOffers> requirementOffersList = requirementOffersService.getRequirementOfferSubmissionByIdAndOrgLocationId(requirementSubmission.getId(), loginOrgLocation.getId());
						if (Constants.All_APPLICANTS.equalsIgnoreCase(submissionType)) {
							submissionType = "SUBMISSION";
						}
						List<RequirementSubmissionHistory> requirementSubmissionHistoryServiceList = requirementSubmissionHistoryService.getRequirementHistoryByLocationIdAndStage(loginOrgLocation.getId(), submissionType, requirementSubmission.getId());
						if (requirementSubmissionHistoryServiceList != null
								&& requirementSubmissionHistoryServiceList.size() > 0) {
							RequirementSubmissionHistory requirementSubmissionHistory = requirementSubmissionHistoryServiceList.get(0);
							User usr = userService.getUserByUserCode(requirementSubmissionHistory.getCreatedBy());
							if (usr.getFirstName() != null && usr.getLastName() != null) {
								StringBuilder name = new StringBuilder();
								requirementSubmission.setSubmittedBy(name.append(usr.getFirstName()).append(" ").append(usr.getLastName()).toString());
							}
							else {
								requirementSubmission.setSubmittedBy(usr.getFirstName());
							}
							requirementSubmission.setSubmittedOn(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),requirementSubmissionHistory.getCreatedDate()));
						}
						if (requirementOffersList.size() > 0) {
							RequirementOffers requirementOffers = requirementOffersList.get(0);
							requirementSubmission.setOfferId(requirementOffers.getId());
							if (requirementOffers.getDateOfJoining() != null) {
								requirementSubmission.setOfferJoinDate(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),requirementOffers.getDateOfJoining()));
							}
							if (requirementOffers.getPaymentType() != null) {
								requirementSubmission.setPositionType(requirementOffers.getPaymentType());
							}
							if (requirementOffers.getAmount() != null) {
								requirementSubmission.setOfferRate(requirementOffers.getAmount());
							}
							if (requirementOffers.getFileName() != null) {
								requirementSubmission.setAttachment(requirementOffers.getFileName());
							}
						}
						String candidateName = requirementSubmissionTemplateFields.getFirstName();
						if (StringUtils.isNotBlank(requirementSubmissionTemplateFields.getLastName())) {
							StringBuilder candidateNameSB = new StringBuilder();
							candidateName = candidateNameSB.append(candidateName).append(" ").append(requirementSubmissionTemplateFields.getLastName()).toString();
						}
						requirementSubmission.setCandidateName(candidateName);
						requirementSubmission.setCandidateEmail(requirementSubmissionTemplateFields.getEmailId());
						requirementSubmission.setExpectedCTC(requirementSubmissionTemplateFields.getExpectedCTC());
						requirementSubmission.setWorkExperience(requirementSubmissionTemplateFields.getWorkExperience());
						requirementSubmission.setCandidateId(requirementSubmission.getCandidate().getId().toString());
						requirementSubmission.setPhoneNumber(requirementSubmissionTemplateFields.getPhoneNumber());
						requirementSubmission.setResumeId(requirementSubmission.getCandidateResumes().getId());
						requirementSubmission.setTotalNoOfInterviews(numberOfInterviews.size());
						requirementSubmission.setRequirementInterviewList(numberOfInterviews);
						RequirementSubmission clientSubmission = requirementSubmissionService.getClientSubmissionsBySubmissionIdAndOrgLocationId(requirementSubmission.getId(), requirementSubmission.getOrgLocations().getId());
						if (clientSubmission != null) {
							requirementSubmission.setClientSubmissionId(clientSubmission.getId().toString());
							RequirementRateSubmissionNegotiation pastRequirementRateSubmissionNegotiation = requirementRateSubmissionNegotiationService.getLatestRequirementRateSubmissionNegotiationBySubmissionId(clientSubmission.getId());
							if (pastRequirementRateSubmissionNegotiation != null) {
								requirementSubmission.setProposedSubmissionRate(pastRequirementRateSubmissionNegotiation.getExpectedRate());
							}
						}
						else {
							RequirementRateSubmissionNegotiation pastRequirementRateSubmissionNegotiation = requirementRateSubmissionNegotiationService.getLatestRequirementRateSubmissionNegotiationBySubmissionId(requirementSubmission.getId());
							if (pastRequirementRateSubmissionNegotiation != null) {
								if (pastRequirementRateSubmissionNegotiation.getRateInitiatorType().equals(SubmissionRateStatus.VENDOR)
										|| pastRequirementRateSubmissionNegotiation.getRateInitiatorType().equals(SubmissionRateStatus.CLIENT)) {
									requirementSubmission.setProposedSubmissionRate(pastRequirementRateSubmissionNegotiation.getExpectedRate());
								}
								else {
									requirementSubmission.setExpectedCTC(pastRequirementRateSubmissionNegotiation.getExpectedRate());
								}
							}
						}
						if (requirementSubmission.getCreatedDate() != null) {
							requirementSubmission.setCreatedDateStr(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),requirementSubmission.getCreatedDate()));
						}
						if (requirementSubmission.getUpdatedDate() != null) {
							requirementSubmission.setUpDatedStr(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),requirementSubmission.getUpdatedDate()));
						}
						User userVal = userService.getUserByUserCode(requirementSubmission.getCreatedBy());
						StringBuilder canNameSB = new StringBuilder();
						requirementSubmission.setUserName(canNameSB.append(userVal.getFirstName()).append(" ").append(userVal.getLastName()).toString());
						requirementSubmission.setResumeName(candidateResumes.getFileName());
						requirementSubmission.setPreviousSubmissionStatus(requirementSubmission.getPreviousSubmissionStatus());
						Candidate candidate = candidateService.getCandidateByCandidateId(requirementSubmission.getCandidate().getId());
						List<Candidate> candidateList = new ArrayList<>();
						candidateList.add(candidate);
						requirementSubmission.setScore(requirementSubmission.getCandidateScore());
					}
				}
				// file =
				// requirementPipelineExportService.getExportRequirementFile(requirementStatusList,loginOrgLocation.getOrgLocationName(),exportType,requirementId,submissionType,
				// requirementSubmissionCounts);
			}
			file = requirementPipelineExportService.getExportRequirementFile(requirementStatusList, loginOrgLocation, exportType, requirementId, submissionType, requirementSubmissionCounts);
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
		}
	}
	
	private RequirementSubmissionCounts getRequirementSubmissionsCount(Long requirementId, User user, OrgLocations loginOrgLocation,String loginUserRole) throws Exception
	{
		int numOfShortList = 0;
		int numOfInterview = 0;
		int numOfSubmitToClient = 0;
		int numOfOffers = 0;
		int numOfRejected = 0;
		int numOfRequestSent = 0;
		List<RequirementSubmission> duplicatecount = requirementSubmissionService.duplicateCandidateCountList(requirementId, user, loginOrgLocation);
		RequirementSubmissionCounts requirementSubmissionCounts = new RequirementSubmissionCounts();
		if (requirementId != null) {
			List<RequirementSubmission> requirementSubmissions = requirementSubmissionService.getSubmissionsByRequirementId(Long.valueOf(requirementId), user, loginOrgLocation,loginUserRole);
			for (RequirementSubmission rmentSubmission : requirementSubmissions) {
				if ("SHORTLIST".equalsIgnoreCase(rmentSubmission.getSubmissionStatus().toString())) {
					numOfShortList++;
				}
				else if ("INTERVIEW".equalsIgnoreCase(rmentSubmission.getSubmissionStatus().toString())) {
					numOfInterview++;
				}
				else if ("SUBMIT_TO_CLIENT".equalsIgnoreCase(rmentSubmission.getSubmissionStatus().toString())) {
					numOfSubmitToClient++;
				}
				else if ("OFFERMADE".equalsIgnoreCase(rmentSubmission.getSubmissionStatus().toString())) {
					numOfOffers++;
				}
				else if ("REJECTED".equalsIgnoreCase(rmentSubmission.getSubmissionStatus().toString())) {
					numOfRejected++;
				}
				else if ("REQUEST_SENT".equalsIgnoreCase(rmentSubmission.getSubmissionStatus().toString())) {
					numOfRequestSent++;
				}
			}
			requirementSubmissionCounts.setNumOfShortList(numOfShortList);
			requirementSubmissionCounts.setNumOfInterview(numOfInterview);
			requirementSubmissionCounts.setNumOfSubmitToClient(numOfSubmitToClient);
			requirementSubmissionCounts.setNumOfOffers(numOfOffers);
			requirementSubmissionCounts.setNumOfRejected(numOfRejected);
			requirementSubmissionCounts.setSubmittedCount(requirementSubmissions.size());
			requirementSubmissionCounts.setNumOfRequestSent(numOfRequestSent);
			if (duplicatecount.size() != 0 && duplicatecount != null) {
				requirementSubmissionCounts.setDuplicateCount(duplicatecount.size());
			}
		}
		return requirementSubmissionCounts;
	}
	
	/**
	 * Search Requirement
	 * 
	 * @param json
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchRequiremet", method = RequestMethod.GET)
	public @ResponseBody String searchRequirement(@RequestParam(value = "json", required = false) String json, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultJson = "";
		String jsonc = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(jsonc);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		List<Requirement> searchRequirementList = requirementService.searchRequirement(orgLocation, jsonObj, org, userRoleName, user);
		resultJson = commonUtil.toJSON(searchRequirementList);
		return resultJson;
	}
	
	@RequestMapping(value = "/getUniversalRequirementsListByOrgLocationID")
	public String getUniversalRequirementsListByOrgLocationID(@RequestParam(value = "serachValue", required = false) String serachValue, HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		String resultJson;
		List<Object> uniRequirementList = requirementService.getAllUniversalSearchRequirementList(orgLocations, serachValue, userRole, user);
		resultJson = commonUtil.toJSON(uniRequirementList);
		return resultJson;
	}
	
	@RequestMapping(value = "/getTimezonesList")
	public @ResponseBody String getTimezonesList(HttpServletRequest request) throws Exception
	{
		String resultJson = null;
		List<Timezone> timezoneList = timeZoneService.getAllTimeZones();
		resultJson = commonUtil.toJSON(timezoneList);
		return resultJson;
	}
	
	@SuppressWarnings("unused")
	public void modifiedReqStatusMailtoVendorContact(Requirement requirement, User loginUser, OrgLocations loginOrgLocation) throws Exception
	{
		List<Requirement> releasedRequirementsList = requirementService.getAllReleasedRequirementsByParentId(requirement.getId());
		if (null != releasedRequirementsList && releasedRequirementsList.size() > 0) {
			for (Requirement releasedRequirement : releasedRequirementsList) {
				User vendorContact = releasedRequirement.getSpocId().getVendorUser();
				EmailMessage emailMessage = new EmailMessage();
				emailMessage.setFirstName(loginUser.getFirstName());
				emailMessage.setLastName(loginUser.getLastName());
				emailMessage.setToEmail(vendorContact.getEmail());
				emailMessage.setFromEmail(loginUser.getEmail());
				emailMessage.setCreatedBy(loginUser.getUserCode());
				StringBuilder msgBuild = new StringBuilder();
				emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
				emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
				// setting the class filed values
				Map<String, String> globalVarsMap = new HashMap<String, String>();
				StringBuilder managerUserBuild = new StringBuilder();
				StringBuilder vendorContactUserBuild = new StringBuilder();
				globalVarsMap.put("requirementManagerName", managerUserBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
				globalVarsMap.put("requirementManagerEmail", loginUser.getEmail());
				globalVarsMap.put("vendorContactName", vendorContactUserBuild.append(vendorContact.getFirstName()).append(" ").append(vendorContact.getLastName()).toString());
				globalVarsMap.put("vendorContactEmail", vendorContact.getEmail());
				globalVarsMap.put("requirementTitle", releasedRequirement.getJobTitle());
				globalVarsMap.put("requirementStatus", requirement.getRequirementSubmissionStatus().toString());
				globalVarsMap.put("logInOrgName", loginOrgLocation.getOrgLocationName());
				globalVarsMap.put("requirementClientName", loginOrgLocation.getOrgLocationName());
				String reqLocation = "";
				if (null != requirement.getCountry() && "" != requirement.getCountry()
						&& "undefined" != requirement.getCountry()) {
					reqLocation = requirement.getCountry();
					if (null != requirement.getState() && "" != requirement.getState()
							&& "undefined" != requirement.getState()) {
						reqLocation = reqLocation + ", " + requirement.getState();
						if (null != requirement.getCity() && "" != requirement.getCity()
								&& "undefined" != requirement.getCity()) {
							reqLocation = reqLocation + ", " + requirement.getCity();
						}
					}
				}
				else {
					reqLocation = "---";
				}
				globalVarsMap.put("requirementLocation", reqLocation);
				String reqRate = "";
				if (null != requirement.getCurrencyCode() && "" != requirement.getCurrencyCode()
						&& null != requirement.getRate() && "" != requirement.getRate()
						&& null != requirement.getRateType() && "" != requirement.getRateType()) {
					reqRate = requirement.getCurrencyCode() + " " + requirement.getRate() + " / "
							+ requirement.getRateType();
				}
				else {
					reqRate = "---";
				}
				globalVarsMap.put("requirementRate", reqRate);
				String reqExp = "";
				if (null != requirement.getMinExp() && "" != requirement.getMinExp()) {
					reqExp = requirement.getMinExp();
					if (null != requirement.getMaxExp() && "" != requirement.getMaxExp()) {
						reqExp = reqExp + " - " + requirement.getMaxExp();
					}
				}
				else if (null != requirement.getMaxExp() && "" != requirement.getMaxExp()) {
					reqExp = requirement.getMaxExp();
				}
				else {
					reqExp = "---";
				}
				globalVarsMap.put("reqExp", reqExp);
				StringBuilder linkBuild = new StringBuilder();
				globalVarsMap.put("loginURL", linkBuild.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
				InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
				// getting the current EmailScenario by EmailScenario unique
				// name and entity type
				EmailScenario emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MODIFIED_REQUIREMENT_STATUS_EMAIL, Constants.ENTITY_TYPE_EMAIL);
				EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MODIFIED_REQUIREMENT_STATUS_NOTIFICATION, Constants.ENTITY_TYPE_NOTIFICATION);
				// covert subject,body and signature
				String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(), globalVarsMap);
				String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(), globalVarsMap);
				String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(), globalVarsMap);
				String url = "";
				String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getBody(), globalVarsMap);
				notificationScenario.setBody(notificationbody);
				// construct HTML template using required data
				String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body, signature, url, new Org(), loginOrgLocation);
				emailMessage.setSubject(subject);
				emailMessage.setMessageBody(emailTemplate);
				// calling the sendEmail method of emailScenerioUtil
				emailScenerioUtil.sendEmail(emailMessage, loginOrgLocation.getId(), iAdressArray, new ArrayList<EmailAttachment>());
				String actionLink = "";
				emailScenerioUtil.sendNotification(actionLink, loginUser, vendorContact, releasedRequirement.getSpocId().getVendorLocationId(), notificationScenario);
			}
		}
	}
}