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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.http.HttpHost;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.main.MainResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.ClusterName;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
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
import com.bis.sourcelead.dto.JobFairDTO;
import com.bis.sourcelead.dto.RequirementAssignToDTO;
import com.bis.sourcelead.dto.RequirementDTO;
import com.bis.sourcelead.dto.RequirementLocationsDTO;
import com.bis.sourcelead.dto.RequirementSearchDTO;
import com.bis.sourcelead.dto.RequirementSubmissionsCountDTO;
import com.bis.sourcelead.dto.RequirementSubmissionsDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.elk.index.RequirementIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.JobFair;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementApproverHierarchy;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.RequirementInterviewHistory;
import com.bis.sourcelead.entity.RequirementLocations;
import com.bis.sourcelead.entity.RequirementOfferHistory;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementRateSubmissionNegotiation;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionCounts;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.RequirementSubmissionTemplateFields;
import com.bis.sourcelead.entity.SubmissionRequirementListView;
import com.bis.sourcelead.entity.TimeSheetSubmissionCount;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.ApplicantSubmissionDisplayStatus;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.enums.REQUIREMENT_SUBMISSION_STATUS;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.enums.SubmissionRateStatus;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.RecommendationsService;
import com.bis.sourcelead.service.RequirementApproverHierarchyService;
import com.bis.sourcelead.service.RequirementAssignToService;
import com.bis.sourcelead.service.RequirementInterviewHistoryService;
import com.bis.sourcelead.service.RequirementInterviewService;
import com.bis.sourcelead.service.RequirementLocationsService;
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
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.PdfUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.workflow.dto.RequirementWorkFlowStagesDTO;
import com.bis.sourcelead.workflow.entity.RequirementQuestionnaireFeedbackTemplate;
import com.bis.sourcelead.workflow.entity.RequirementWorkFlowStages;
import com.bis.sourcelead.workflow.service.RequirementWorkFlowService;

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
	
	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;
	
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
	@Autowired
	ConversionUtil conversionUtil;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	@Autowired
	OrgSpocService orgSpocService;
	
	@Autowired
	LookUpDataService lookUpDataService;
	
	@Autowired
	RequirementLocationsService requirementLocationsService;
	
	@Autowired
	RequirementWorkFlowService requirementWorkFlowService;
	
	protected boolean isNullOrEmpty(String str)
	{
		return (str == null || "".equals(str.trim()));
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
	public @ResponseBody Requirement getRequirementById(@RequestParam(value = "requirementId", required = false) String requirementId, HttpServletRequest request) throws Exception
	{
		Requirement requirement = null;
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		if (requirementId != null && !requirementId.isEmpty()) {
			 requirement = requirementService.getRequirementByRequirementId(Long.valueOf(requirementId));
			 if(requirement.getDescription()!= null && StringUtils.isNotBlank(requirement.getDescription())) {
				 requirement.getDescription();
			 }else {
				 requirement.setDescription("");
			 }
			 
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
		}
		return requirement;
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
	public @ResponseBody ResponseList<RequirementAssignTo> getRequirementAssignedListByRequirementId(RequirementAssignToDTO requirementAssignToDTO, HttpServletRequest request) throws Exception
	{
		ResponseList<RequirementAssignTo> requirementAssignToDtoResponse = new ResponseList<>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(user != null) {
			try {
				ResponseList<RequirementAssignTo> list = requirementAssignToService.loadRequirementAssignToListByRequirementId(requirementAssignToDTO);
				List<RequirementAssignTo> requirementAssignToList = list.getData();
					if (list != null && list.getRecordsTotal() > 0) {
						for (RequirementAssignTo assing : requirementAssignToList) {
							if (assing.getAssignUserId() != null) {
								assing.setAssignedUserName(assing.getAssignUserId().getFullName());
							}
							if (assing.getRoleId() != null) {
								assing.setAssignedUserRole(assing.getRoleId().getDisplayName());
							}
							if (assing.getTeamId() != null) {
								assing.setTeamName(assing.getTeamId().getName());
							}
							if (assing.getAssignUserId() != null) {
								Integer applicantCount = requirementSubmissionService.getApplicantCountByrequirementIdandAssignedUsercode(requirementAssignToDTO.getRequirementId(), assing.getAssignUserId().getUserCode());
								assing.setApplicantCount(applicantCount.toString());
							}
						}
					}
				requirementAssignToDtoResponse.setData(requirementAssignToList);
				requirementAssignToDtoResponse.setRecordsFiltered(list.getRecordsFiltered());
				requirementAssignToDtoResponse.setRecordsTotal(list.getRecordsTotal());
				requirementAssignToDtoResponse.setError(list.getError());
				requirementAssignToDtoResponse.setResponse(Result.SUCCESS.name());
				requirementAssignToDtoResponse.setResponseMessage(Result.SUCCESS.getValue());
		}catch(Exception e) {
			requirementAssignToDtoResponse.setResponse(Result.AWKWARD.name());
			requirementAssignToDtoResponse.setResponseMessage(Result.AWKWARD.getValue());
		}
	}else {
		requirementAssignToDtoResponse.setResponse(Result.SESSION_TIMEDOUT.name());
		requirementAssignToDtoResponse.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
	return requirementAssignToDtoResponse;
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
						
						if(requirementSubmission.getCandidateResumes() != null) {
							requirementSubmission.setResumeId(requirementSubmission.getCandidateResumes().getId());
							requirementSubmission.setResumeUUID(requirementSubmission.getCandidateResumes().getResumeUUID());
						}
					
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
					requirementSubmission.setRequirementCurrentStatus(requirementSubmission.getRequirement().getRequirementSubmissionStatus());
				}
			}
			resultJson = commonUtil.toJSON(submissionList);
			
			long endTime = System.currentTimeMillis();
			long actualmills = (endTime - startTime);
			System.out.println("totalTimeMillis===========>"+actualmills);
		}
		return resultJson;
	}
	
	
	
	/**
	 * load loadSubmissionsListByRequirementId using RequirementId
	 * @param request
	 * @param response
	 * @return json
	 * @throws Exception
	 */
	@RequestMapping(value = {"/loadSubmissionsListByRequirementId", "/rest/loadSubmissionsListByRequirementId"}, method = RequestMethod.POST)
	public @ResponseBody  ResponseList<RequirementSubmission> loadSubmissionsListByRequirementId(RequirementSubmissionsDTO requirementSubmissionsDTO, HttpServletRequest request) throws Exception
	{
    System.out.println("requirementSubmissionsDTO.getWorkFlowId( )"+requirementSubmissionsDTO.getWorkFlowId());
		OrgLocations orgLocation = orgLocationService.getOrgLocations(requirementSubmissionsDTO.getOrgLocationId());
		Timezone timezone = (Timezone) SourceLeadSessionManager.getSessionAttribute(request, "timezone");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		ResponseList<RequirementSubmission> list = new ResponseList<>();
		if(user != null) {
			List<RequirementSubmission> submissionList = new ArrayList<RequirementSubmission>();
				try {
					if(requirementSubmissionsDTO.getSubmissionType() != null && requirementSubmissionsDTO.getSearchValue() != null && !requirementSubmissionsDTO.getSearchValue().equalsIgnoreCase("'")) {
						String[] tableColumns = null;
						if(requirementSubmissionsDTO.getSubmissionType().equalsIgnoreCase("SHORTLIST")){
							String[] columns = {"name", "email", "phNo", "resume", "appliedOn", "rate", "source", "shortlistedBy", "score", "exp", "status"};
							tableColumns = columns;
						}else if(requirementSubmissionsDTO.getSubmissionType().equalsIgnoreCase("INTERVIEW")){
							String[] columns = {"name", "email", "phNo", "resume", "noOfInterviews", "status"};
							tableColumns = columns;
						}else if(requirementSubmissionsDTO.getSubmissionType().equalsIgnoreCase("OFFERMADE")){
							String[] columns = {"name", "email", "phNo", "resume", "offerJoinDate", "positionType", "offerRate", "attachment", "status"};
							tableColumns = columns;
						}else if(requirementSubmissionsDTO.getSubmissionType().equalsIgnoreCase("JOINED")){
							String[] columns = {"name", "email", "phNo", "resume", "appliedOn", "source", "joinDate", "status"};
							tableColumns = columns;
						}else if(requirementSubmissionsDTO.getSubmissionType().equalsIgnoreCase("SELECTED")){
							String[] columns = {"name", "email", "phNo", "resume", "appliedOn", "source", "selectedDate", "status"};
							tableColumns = columns;
						}else if(requirementSubmissionsDTO.getSubmissionType().equalsIgnoreCase("REJECTED")){
							String[] columns = {"name", "email", "resume", "appliedOn", "rate", "subRate", "source", "rejectedOn", "rejectedBy", "status"};
							tableColumns = columns;
						}else if(requirementSubmissionsDTO.getSubmissionType().equalsIgnoreCase("BACKOUT")){
							String[] columns = {"name", "email", "resume", "appliedOn", "rate", "subRate", "source", "backOutOn", "backOutBy", "status"};
							tableColumns = columns;
						}else if(requirementSubmissionsDTO.getSubmissionType().equalsIgnoreCase("SUBMIT_TO_CLIENT")){
							String[] columns = {"name", "email", "phNo", "resume", "appliedOn", "subRate", "source", "score", "exp", "status"};
							tableColumns = columns;
						}else{
							String[] columns = {"name", "email", "phNo", "resume", "appliedOn", "rate", "source", "score", "exp", "status"};
							tableColumns = columns;
						}
						
					    int sortColumnIndex = 3; //by default sorting using updated date
						String sortColumnOrder = "desc"; //by default descending order
						if(requirementSubmissionsDTO.getOrder() != null && !requirementSubmissionsDTO.getOrder().isEmpty()) {
							sortColumnIndex = Integer.parseInt(requirementSubmissionsDTO.getOrder().get(0).get("column"));
							sortColumnOrder = requirementSubmissionsDTO.getOrder().get(0).get("dir");
						}
						
						requirementSubmissionsDTO.setSortColumn(tableColumns[sortColumnIndex]);
						requirementSubmissionsDTO.setSortOrder(sortColumnOrder);
						
						list = requirementSubmissionService.loadSubmissionsListByRequirementId(requirementSubmissionsDTO, orgLocation);
						
						if (list.getData() != null && list.getData().size() > 0) {
							for (RequirementSubmission requirementSubmission : list.getData()) {
									RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = null;
									Set<RequirementSubmissionTemplateFields> requirementSubmissionTemplateFieldsList = requirementSubmission.getRequirementSubmissionTemplateFieldsList();
									for(RequirementSubmissionTemplateFields templateFields : requirementSubmissionTemplateFieldsList){
										if(templateFields.getRequirementSubmission().getId().equals(requirementSubmission.getId())){
											requirementSubmissionTemplateFields = templateFields;
										}
									}
									
									
									if (requirementSubmissionsDTO.getSubmissionType() != null
											&& requirementSubmissionsDTO.getSubmissionType().equalsIgnoreCase("INTERVIEW")) {
										
										Set<RequirementInterviewHistory> requirementInterviewHistoryList = requirementSubmission.getRequirementInterviewHistory();
										requirementSubmission.setTotalNoOfInterviews(requirementInterviewHistoryList.size());
									}
									
									if (requirementSubmissionsDTO.getSubmissionType() != null
											&& requirementSubmissionsDTO.getSubmissionType().equalsIgnoreCase("OFFERMADE")) {
										
										Set<RequirementOffers> requirementOffersList = requirementSubmission.getRequirementOffersList();
										
										if (requirementOffersList.size() == 0
												&& (ApplicantSubmissionStatus.CLIENT_OFFERMADE.toString().equalsIgnoreCase(requirementSubmission.getSubmissionStatus().toString())
														|| ApplicantSubmissionStatus.CLIENT_OFFERACCEPTED.toString().equalsIgnoreCase(requirementSubmission.getSubmissionStatus().toString())
														|| ApplicantSubmissionStatus.CLIENT_OFFERREJECTED.toString().equalsIgnoreCase(requirementSubmission.getSubmissionStatus().toString())
														|| ApplicantSubmissionStatus.CLIENT_OFFERHIRED.toString().equalsIgnoreCase(requirementSubmission.getSubmissionStatus().toString()))) {
											
											Set<RequirementOfferHistory> offersList = requirementSubmission.getRequirementOfferHistoryList();
											
											if(offersList != null){
												for (RequirementOfferHistory requirementOfferHistory : offersList) {
													RequirementOffers requirementOffershis = requirementOfferHistory.getOfferId();
													requirementSubmission.setOfferId(requirementOffershis.getId());
													if (requirementOffershis.getDateOfJoining() != null) {
														requirementSubmission.setOfferJoinDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),requirementOffershis.getDateOfJoining()));
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
												requirementSubmission.setOfferJoinDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),requirementOffers.getDateOfJoining()));
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
									HashMap<String , String> stagesMap = null;
									if (requirementSubmissionTemplateFields != null) {
										if (!isNullOrEmpty(requirementSubmissionTemplateFields.getLastName())) {
											StringBuilder candidateNameSB = new StringBuilder();
											candidateName = candidateNameSB.append(requirementSubmissionTemplateFields.getFirstName()).append(" ").append(requirementSubmissionTemplateFields.getLastName()).toString();
										}
										else {
											candidateName = requirementSubmissionTemplateFields.getFirstName();
										}
										requirementSubmission.setCandidateName(candidateName);
										requirementSubmission.setCandidateEmail(requirementSubmission.getCandidate().getEmail());
										requirementSubmission.setExpectedCTC(requirementSubmissionTemplateFields.getExpectedCTC());
										requirementSubmission.setWorkExperience(requirementSubmissionTemplateFields.getWorkExperience());
										requirementSubmission.setPhoneNumber(requirementSubmissionTemplateFields.getPhoneNumber());
									}
									if(requirementSubmissionsDTO.getWorkFlowId() != null && StringUtils.isNotBlank(requirementSubmissionsDTO.getWorkFlowId().toString())){
									 stagesMap = requirementWorkFlowService.getRequirementWorkflowStagesBySeqOrderWorkflowIdAndStageName(requirementSubmissionsDTO.getWorkFlowId(), orgLocation, requirementSubmission.getSubmissionStatus());
									   if(stagesMap!= null && stagesMap.size() >0) {
									 	requirementSubmission.setWorkflowStagesMap(stagesMap);
									   }
									}
									
									requirementSubmission.setCandidateId(requirementSubmission.getCandidate().getId().toString());
									
									/**
									*@author Mahaveer Manoj
									*@purpose Get the resume which got uploaded while adding an applicant from jobpipeline,
									*@senario-1 : If Resume got uploaded while adding applicant, then get the uploaded resume from RequirementSubmission
									*@senario-2 : If Resume wasn't uploaded from jobpipeline and if it was uploaded later from Candidate Summary page, 
									*			  then get the latest uploaded Resume with status == 1 from CandidateResumes 
									**/
									if(requirementSubmission.getCandidateResumes() != null) {
										requirementSubmission.setResumeId(requirementSubmission.getCandidateResumes().getId());
										requirementSubmission.setResumeUUID(requirementSubmission.getCandidateResumes().getResumeUUID());
										requirementSubmission.setFileContentType(requirementSubmission.getCandidateResumes().getFileContentType());
										requirementSubmission.setFileName(requirementSubmission.getCandidateResumes().getFileName());
										requirementSubmission.setFileURL(requirementSubmission.getCandidateResumes().getFileURL());
									} else if(requirementSubmission.getCandidate().getCandidateResumes() != null && !requirementSubmission.getCandidate().getCandidateResumes().isEmpty()){
										List<CandidateResumes> activeResumes = requirementSubmission.getCandidate()
												.getCandidateResumes()
												.stream()
												.filter(x -> x.getStatus() == 1)
												.collect(Collectors.toList());
										if (activeResumes != null && !activeResumes.isEmpty()) {
											CandidateResumes latestResume = activeResumes
													.stream()
													.max(Comparator.comparingLong(CandidateResumes::getId))
													.get();
											if(latestResume != null) {
												requirementSubmission.setResumeId(latestResume.getId());
												requirementSubmission.setResumeUUID(latestResume.getResumeUUID());
												requirementSubmission.setFileContentType(latestResume.getFileContentType());
												requirementSubmission.setFileName(latestResume.getFileName());
												requirementSubmission.setFileURL(latestResume.getFileURL());
											}
										}
									}
									
									if(requirementSubmission.getSubmissionStatus() != null) {
										//ApplicantSubmissionDisplayStatus status =	ApplicantSubmissionDisplayStatus.valueOf(requirementSubmission.getSubmissionStatus().toString());
										String status =	requirementSubmission.getSubmissionStatus();
										if(status != null && stagesMap != null) {
						  				requirementSubmission.setSubmissionDisplayStatus(stagesMap.get(status));
										}else {
											requirementSubmission.setSubmissionDisplayStatus(status);
										}
									}
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
									if (null != requirementSubmissionsDTO.getSubmissionType()
											&& requirementSubmissionsDTO.getSubmissionType().equalsIgnoreCase("SUBMIT_TO_CLIENT")) {
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
										
										requirementSubmission.setCreatedDateStr(commonUtil.dateStrFromOrgDateFormatter(requirementSubmission.getOrgLocations().getDateFormat(), requirementSubmission.getCreatedDate()));
										//commented by Laxman
										//add applicant created date is wrong
										/*String currentTimeStr = Constants.TZ_DF.format(requirementSubmission.getCreatedDate());
										if (null != timezone && null != timezone.getTimeZoneId()) {
											String currentDateInTimezone = CommonUtil.convertTimeZones("UTC", timezone.getTimeZoneId(), currentTimeStr);
											DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
											Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
											currentDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),converteDate);
											requirementSubmission.setCreatedDateStr(currentDateInTimezone);
											SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
											String time = localDateFormat.format(converteDate);
											requirementSubmission.setCreatedTimeStr(time);
										}*/
									}
									if (requirementSubmission.getUpdatedDate() != null) {
										requirementSubmission.setUpDatedStr(commonUtil.dateStrFromOrgDateFormatter(requirementSubmission.getOrgLocations().getDateFormat(), requirementSubmission.getUpdatedDate()));
									   //commented by Laxman
										//add applicant updated date is wrong
										/*String currentTimeStr = Constants.TZ_DF.format(requirementSubmission.getUpdatedDate());
										if (null != timezone && null != timezone.getTimeZoneId()) {
											String currentDateInTimezone = CommonUtil.convertTimeZones("UTC", timezone.getTimeZoneId(), currentTimeStr);
											DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
											Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
											currentDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),converteDate);
											requirementSubmission.setUpDatedStr(currentDateInTimezone);
										}*/
									}
									if (requirementSubmission.getJoinedDate() != null) {
										requirementSubmission.setJoinedDateStr(commonUtil.dateStrFromOrgDateFormatter(requirementSubmission.getOrgLocations().getDateFormat(), requirementSubmission.getJoinedDate()));
									}
									//submissionList.add(requirementSubmission);
								//requirementSubmission.setRequirementCurrentStatus(requirementSubmission.getRequirement().getRequirementSubmissionStatus());
							}
						}
					} else {
						List<RequirementSubmission> rsList = new LinkedList<>();
						list.setData(rsList);
					}
				list.setResponse(Result.SUCCESS.name());
				list.setResponseMessage(Result.SUCCESS.getValue());
			}catch(Exception e) {
				e.printStackTrace();
			list.setResponse(Result.AWKWARD.name());
			list.setResponseMessage(Result.AWKWARD.getValue());
		}
		}else {
		list.setResponse(Result.SESSION_TIMEDOUT.name());
		list.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		return list;
		
	}
	
	/**
	 * load loadWorkflowSubmissionsListByRequirementId using RequirementId
	 * @param request
	 * @param response
	 * @return json
	 * @throws Exception
	 */
	@RequestMapping(value = {"/loadWorkflowSubmissionsListByRequirementId", "/rest/loadWorkflowSubmissionsListByRequirementId"}, method = RequestMethod.POST)
	public @ResponseBody  ResponseList<RequirementSubmission> loadWorkflowSubmissionsListByRequirementId(RequirementSubmissionsDTO requirementSubmissionsDTO, HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocation = orgLocationService.getOrgLocations(requirementSubmissionsDTO.getOrgLocationId());
		Timezone timezone = (Timezone) SourceLeadSessionManager.getSessionAttribute(request, "timezone");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		ResponseList<RequirementSubmission> list = new ResponseList<>();
		System.out.println("inside loadWorkflowSubmissionsListByRequirementId >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		if(user != null && StringUtils.isNotBlank(requirementSubmissionsDTO.getWorkflowSatgeColumns() )) {
				try {
					
					System.out.println("search Vlaue is  >>>>>>>>>>>>>>>>>>"+ requirementSubmissionsDTO.getSearchValue());
					if(requirementSubmissionsDTO.getSubmissionType() != null && requirementSubmissionsDTO.getSearchValue() != null && !requirementSubmissionsDTO.getSearchValue().equalsIgnoreCase("'")) {
						//if(requirementSubmissionsDTO.getSubmissionType().equalsIgnoreCase("all")){
							String[] tableColumns = requirementSubmissionsDTO.getWorkflowSatgeColumns().split(",");
						//}
						System.out.println("tableColumns >>>>>>>>>>>>>>>>>>"+ requirementSubmissionsDTO.getWorkflowSatgeColumns());
					    int sortColumnIndex = 1; //by default sorting using updated date
						String sortColumnOrder = "desc"; //by default descending order
						if(requirementSubmissionsDTO.getOrder() != null && !requirementSubmissionsDTO.getOrder().isEmpty()) {
							sortColumnIndex = Integer.parseInt(requirementSubmissionsDTO.getOrder().get(0).get("column"));
							sortColumnOrder = requirementSubmissionsDTO.getOrder().get(0).get("dir");
						}
						
						requirementSubmissionsDTO.setSortColumn(tableColumns[sortColumnIndex]);
						requirementSubmissionsDTO.setSortOrder(sortColumnOrder);
						
						list = requirementSubmissionService.loadWorkflowSubmissionsListByRequirementId(requirementSubmissionsDTO, orgLocation);
						
						if (list.getData() != null && list.getData().size() > 0) {
							for (RequirementSubmission requirementSubmission : list.getData()) {
									RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = null;
									Set<RequirementSubmissionTemplateFields> requirementSubmissionTemplateFieldsList = requirementSubmission.getRequirementSubmissionTemplateFieldsList();
									for(RequirementSubmissionTemplateFields templateFields : requirementSubmissionTemplateFieldsList){
										if(templateFields.getRequirementSubmission().getId().equals(requirementSubmission.getId())){
											requirementSubmissionTemplateFields = templateFields;
										}
									}
									
									String candidateName = "";
									HashMap<String , String> stagesMap = null;
									if (requirementSubmissionTemplateFields != null) {
										if (!isNullOrEmpty(requirementSubmissionTemplateFields.getLastName())) {
											StringBuilder candidateNameSB = new StringBuilder();
											candidateName = candidateNameSB.append(requirementSubmissionTemplateFields.getFirstName()).append(" ").append(requirementSubmissionTemplateFields.getLastName()).toString();
										}
										else {
											candidateName = requirementSubmissionTemplateFields.getFirstName();
										}
										requirementSubmission.setCandidateName(candidateName);
										requirementSubmission.setCandidateEmail(requirementSubmission.getCandidate().getEmail());
										requirementSubmission.setExpectedCTC(requirementSubmissionTemplateFields.getExpectedCTC());
										requirementSubmission.setWorkExperience(requirementSubmissionTemplateFields.getWorkExperience());
										requirementSubmission.setPhoneNumber(requirementSubmissionTemplateFields.getPhoneNumber());
										// added by SRINIVAS SABBU
										requirementSubmission.setAlternateNumber(requirementSubmissionTemplateFields.getAlternateNumber());
										requirementSubmission.setLocation(requirementSubmissionTemplateFields.getLocation());
										requirementSubmission.setDateOfBirthStr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),requirementSubmissionTemplateFields.getDateOfBirth()));
										requirementSubmission.setCurrentCtc(requirementSubmissionTemplateFields.getCurrentCTC());
										requirementSubmission.setEducation(requirementSubmissionTemplateFields.getEducation());
										requirementSubmission.setCreatedDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), requirementSubmission.getCreatedDate()));
										requirementSubmission.setNoticePeriod(requirementSubmissionTemplateFields.getNoticePeriod());
										// here loading all the subsequent stage for the the requirement
										if(requirementSubmissionsDTO.getWorkFlowId() != null && StringUtils.isNotBlank(requirementSubmissionsDTO.getWorkFlowId().toString())){
										stagesMap = requirementWorkFlowService.getRequirementWorkflowStagesBySeqOrderWorkflowIdAndStageName(requirementSubmissionsDTO.getWorkFlowId(), orgLocation, requirementSubmissionsDTO.getSubmissionType());
										if(stagesMap!= null && stagesMap.size() >0) {
											requirementSubmission.setWorkflowStagesMap(stagesMap);
										}
										// here setting questionnaire to stage if candidate submitted questionnaire for particular stage otherwise setting empty value.
										// below service call used to get requirement work flow stage Id.
										RequirementWorkFlowStages workflowSatge = requirementWorkFlowService.getRequirementWorkFlowStagesDetails(requirementSubmissionsDTO.getSubmissionType(),requirementSubmissionsDTO.getWorkFlowId(), orgLocation.getId());
										// by using below service getting questionnaire submission template UUID from the RequirementQuestionnaireFeedbackTemplate.
										RequirementQuestionnaireFeedbackTemplate feedbacTemplate= requirementWorkFlowService.getSubmittedQuestionnaireUuidByWorkflowStage(workflowSatge.getUuid(), requirementSubmission.getId(), orgLocation.getId());
										if(feedbacTemplate != null ) {
											 StringBuilder viewLink = new StringBuilder();
											 viewLink.append(sourceleadUrl).append("/viewQuestionnaireFeedBackTemplate?templateUuid=").append(feedbacTemplate.getUuid())
											                                /*.append("&stageId=").append(feedbacTemplate.getRequirementWorkFlowStagesId().getId())
											                                .append("&submissionId=").append(requirementSubmission.getId())*/;
											   
											requirementSubmission.setSubmittedQuestionrUuid(viewLink.toString());
										}else {
											requirementSubmission.setSubmittedQuestionrUuid(null);
										}
									}
									}
									requirementSubmission.setCandidateId(requirementSubmission.getCandidate().getId().toString());
									
									
									if(requirementSubmission.getSubmissionStatus() != null) {
										/*ApplicantSubmissionDisplayStatus status =	ApplicantSubmissionDisplayStatus.valueOf(requirementSubmission.getSubmissionStatus().toString());
										if(status != null)*/
										if(stagesMap != null) {
											System.out.println("stagesMap stage name is "+stagesMap.get(requirementSubmission.getSubmissionStatus()));
						  				requirementSubmission.setSubmissionDisplayStatus(stagesMap.get(requirementSubmission.getSubmissionStatus()));
										}else {
											System.out.println("stagesMap else stage name is "+requirementSubmission.getSubmissionStatus());
											requirementSubmission.setSubmissionDisplayStatus(requirementSubmission.getSubmissionStatus());
										}
										
									}
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
							}
						}
					} else {
						List<RequirementSubmission> rsList = new LinkedList<>();
						list.setData(rsList);
					}
				list.setResponse(Result.SUCCESS.name());
				list.setResponseMessage(Result.SUCCESS.getValue());
			}catch(Exception e) {
				e.printStackTrace();
			list.setResponse(Result.AWKWARD.name());
			list.setResponseMessage(Result.AWKWARD.getValue());
		}
		}else {
		list.setResponse(Result.SESSION_TIMEDOUT.name());
		list.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		return list;
		
	}
	@RequestMapping(value = "/saveChangedStatus", method = RequestMethod.POST)
	@ResponseBody
	Response<String> saveChangedStatus(@RequestParam(value = "requirementId", required = false) Long requirementId, @RequestParam(value = "status", required = false) String status, HttpServletRequest request) throws Exception
	{
		Response<String> response =  new Response<>();
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(loginUser != null) {
			try {
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
				// authorizing if the login user as Team Member Or Not
				Boolean isLoginUserAsTeamMember = requirementAssignToService.checkLoginUserIsRequirementAssignedToByRequirementIdAndUserId(requirementId, loginUser.getId());
				if (isLoginUserAsTeamMember && !"ROLE_ADMIN".equalsIgnoreCase(userRoleName) && !"ROLE_SOURCELEAD_ADMIN".equalsIgnoreCase(userRoleName)) {
					response.setObject("false");
				}
				else {
					Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);
					REQUIREMENT_SUBMISSION_STATUS previousStatus = requirement.getRequirementSubmissionStatus();
					REQUIREMENT_SUBMISSION_STATUS changedStatus = Enum.valueOf(REQUIREMENT_SUBMISSION_STATUS.class, status);
					requirement.setRequirementSubmissionStatus(changedStatus);
					requirement.setRequirementSubmissionPreviousStatus(previousStatus);
					requirement.setUpdatedBy(loginUser.getUserCode());
					requirement.setUpdatedDate(CommonUtil.currentDate());
					if (changedStatus == REQUIREMENT_SUBMISSION_STATUS.OPEN
							&& (previousStatus == REQUIREMENT_SUBMISSION_STATUS.UNFILLEDCLOSED
									|| previousStatus == REQUIREMENT_SUBMISSION_STATUS.FILLEDCLOSED
									|| previousStatus == REQUIREMENT_SUBMISSION_STATUS.CLOSED)) {
						requirement.setRepoenedDate(CommonUtil.currentDate());
					}
					requirementService.addRequirement(requirement);
					/*changeStatusEmail();*/
					StringBuilder reqStatusChange = new StringBuilder();
					SourceLeadSessionManager.setSessionAttribute(request, "reqStatusChange", reqStatusChange.append(" ").append(requirement.getJobTitle()).append(" Status changed ").append(requirement.getRequirementSubmissionPreviousStatus()).append(" to ").append(requirement.getRequirementSubmissionStatus()).append(" ").toString());
					modifiedReqStatusMailtoVendorContact(requirement, loginUser, loginOrgLocation);
//					List<Requirement> requirementList = requirementService.getRequirementListByOrgLocationID(loginOrgLocation.getId(), status, clients, user, userRoleName);
					
					
					
					
// ----------- method for e-mail scenario when job status changed mail will be send to manager, vendor,assigned members and primaryRecruiter -----------------------------
					List<RequirementAssignTo> assignedToList= requirementAssignToService.getRequirementAssignToListByRequirementId(requirement.getId());
					
					List<Requirement> parentReqeqList = requirementService.getAllReleasedRequirementsByParentId(requirement.getId());
					
					List<User> assignUser = new ArrayList<>();
					
					List<User> assignTeamUser = new ArrayList<>();
					
					OrgSpoc spoc = null;
					if(parentReqeqList != null) {
						for (Requirement req : parentReqeqList) {
							spoc = orgSpocService.getOrgSpocById(req.getSpocId().getId());
							assignUser.add(spoc.getVendorUser());
						}
					}
					
					if(assignedToList != null) {
						for (RequirementAssignTo requirementAssignTo : assignedToList) {
							assignTeamUser.add(requirementAssignTo.getAssignUserId());
							for (User toUser : assignTeamUser) {
								if(requirement.getRequirementSubmissionStatus() != null &&  (!requirement.getRequirementSubmissionStatus().toString().equalsIgnoreCase("ONHOLD") && (requirement.getRequirementSubmissionPreviousStatus().toString().equalsIgnoreCase("SUBMITTED")))){
									requirementService.sendMailToPrimaryRecruiter(requirement,loginUser,toUser);
								}
							}
						}
					}
					
					if(requirement.getPrimaryRecruiterUserId() != null) {
						assignUser.add(requirement.getPrimaryRecruiterUserId());
					}
					
						assignUser.add(requirement.getManagerUserId());
						
						for (User toUser : assignUser) {
							requirementService.sendMailToPrimaryRecruiter(requirement,loginUser,toUser);
						}
					
				}
				response.setObject("true");
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
	// ------------------------------------------------------------------------End of this method------------------------------------------------
	
	
	
	
	@RequestMapping(value = "/saveChangedStatusFromDraft", method = RequestMethod.POST)
	@ResponseBody
	Response<String> saveChangedStatusFromDraft(@RequestParam(value = "requirementId", required = false) Long requirementId, @RequestParam(value = "status", required = false) String status, @RequestParam(value = "userId", required = true) Long userId, HttpServletRequest request) throws Exception
	{
		Response<String> response =  new Response<>();
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(loginUser != null) {
			try {
				String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
				// authorizing if the login user as Team Member Or Not
				Boolean isLoginUserAsTeamMember = requirementAssignToService.checkLoginUserIsRequirementAssignedToByRequirementIdAndUserId(requirementId, loginUser.getId());
				if (isLoginUserAsTeamMember && !"ROLE_ADMIN".equalsIgnoreCase(userRoleName) && !"ROLE_SOURCELEAD_ADMIN".equalsIgnoreCase(userRoleName)) {
					response.setObject("false");
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
					
					/*for (String approver : approvers) {
						User approverUser = userService.getUserById(Long.parseLong(approver));
						if (!approverUser.getId().equals(user.getId())) {
							approverEmailScenario(user, approverUser, savedRequirement, location, orgLocations);
						}
					}*/
					
				}
				response.setObject("true");
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
	public void getRequirementExportListByOrgLocationID(RequirementSearchDTO search, HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		// String resultJson;
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		
		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
		boolQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", orgLocation.getId()));
		
		BoolQueryBuilder boolQueryBuilderForCount = new BoolQueryBuilder();
		boolQueryBuilderForCount.must(QueryBuilders.matchQuery("orgLocationId", orgLocation.getId()));
	 
		
		if(StringUtils.isNotEmpty(search.getSearchValue())){
			    ArrayList<String> anyKeyWordArray = getSearchKeywordsFromChosenSelectField(search.getSearchValue());
				if(anyKeyWordArray != null && !anyKeyWordArray.isEmpty()){
					 BoolQueryBuilder anyBoolQueryBuilder = QueryBuilders.boolQuery();
					 for(String keywd : anyKeyWordArray){
						 BoolQueryBuilder anyBoolSubQueryBuilder = QueryBuilders.boolQuery();
						ArrayList<QueryBuilder> QueryBuilderList =getQueryBuilderListForUniversalSearch(keywd);
						if(QueryBuilderList != null && !QueryBuilderList.isEmpty()){
							for(QueryBuilder QueryBuilder :QueryBuilderList){
								anyBoolSubQueryBuilder.should(QueryBuilder);
							}
							DateFormat df = orgLocation.getLocFullFormDateFormatter();
							try{
								if(keywd.contains("/")){
									keywd = keywd.replaceAll("/", "-");
								}
								Date d = df.parse(keywd);
								anyBoolSubQueryBuilder = QueryBuilders.boolQuery();
								String dateStr = commonUtil.elkDateFormatter(d);
								anyBoolSubQueryBuilder.should(QueryBuilders.termQuery("createdDate", dateStr));
							 }
							 catch(Exception e){}
							
							anyBoolQueryBuilder.must(anyBoolSubQueryBuilder);
						}
					}
					 boolQueryBuilder.must(anyBoolQueryBuilder);
				} 
		 }
		 
		
		//Generated Id
		if(StringUtils.isNotEmpty(search.getGeneratedId())){
			boolQueryBuilder.must(QueryBuilders.matchQuery("generatedId", search.getGeneratedId()));
	    }
		
		//Requirement Title
		if(StringUtils.isNotEmpty(search.getRequirementTitle())){
			boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("jobTitle", search.getRequirementTitle()));
	    }
		// jobId
		if(StringUtils.isNotEmpty(search.getJobId())){
			boolQueryBuilder.must(QueryBuilders.matchQuery("jobId", search.getJobId()));
	    }
		
		List<String> jobsTagsArray = search.getJobsTags();
		if (jobsTagsArray != null && !jobsTagsArray.isEmpty()) {
			BoolQueryBuilder tagBoolQueryBuilder = QueryBuilders.boolQuery();
			for (String keywd : jobsTagsArray) {
				tagBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("tags", keywd));
			}
			boolQueryBuilder.must(tagBoolQueryBuilder);
		}
		
		//multiple City
		if(StringUtils.isNotEmpty(search.getCity())){
			ArrayList<String> cityArray = getSearchKeywordsFromChosenSelectField(search.getCity());
			if(cityArray != null && !cityArray.isEmpty()){
				BoolQueryBuilder multiCityBoolQueryBuilder = new BoolQueryBuilder();
				for(String keywd : cityArray){
					multiCityBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("city", keywd));
				}
				boolQueryBuilder.must(multiCityBoolQueryBuilder);
			} 
		}
		
		
		if(StringUtils.isNotEmpty(search.getNoticePeriod())){
			ArrayList<String> noticePeriodArray = getSearchKeywordsFromChosenSelectField(search.getNoticePeriod());
			if(noticePeriodArray != null && !noticePeriodArray.isEmpty()){
				BoolQueryBuilder noticePeriodBoolQueryBuilder = new BoolQueryBuilder();
				for(String keywd : noticePeriodArray){
					noticePeriodBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("noticePeriod", keywd));
				}
				boolQueryBuilder.must(noticePeriodBoolQueryBuilder);
			} 
		}
			
		//visa type
				if(StringUtils.isNotEmpty(search.getVisaType())){
					ArrayList<String> immigrationTypeArray = getSearchKeywordsFromChosenSelectField(search.getVisaType());
					if(immigrationTypeArray != null && !immigrationTypeArray.isEmpty()){
						BoolQueryBuilder immigrationTypeBoolQueryBuilder = new BoolQueryBuilder();
						for(String keywd : immigrationTypeArray){
							immigrationTypeBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("visaTypes", keywd));
						}
						boolQueryBuilder.must(immigrationTypeBoolQueryBuilder);
					} 
				}
				
				//Skills
				if(StringUtils.isNotEmpty(search.getRequirementSkills())){
					ArrayList<String> skillsArray = getSearchKeywordsFromChosenSelectField(search.getRequirementSkills());
					if(skillsArray != null && !skillsArray.isEmpty()){
						BoolQueryBuilder skillsBoolQueryBuilder = new BoolQueryBuilder();
						for(String keywd : skillsArray){
							skillsBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("skills", keywd));
						}
						boolQueryBuilder.must(skillsBoolQueryBuilder);
					} 
				}
				
	    //requirementStatus
		if(StringUtils.isNotEmpty(search.getRequirementStatus()) || StringUtils.isNotEmpty(search.getFilterStatus())){
			String status = "";
			if(StringUtils.isNotEmpty(search.getRequirementStatus())){
				status = search.getRequirementStatus();
			}
			else{
				status = search.getFilterStatus();
			}
			ArrayList<String> requirementStatusArray = getSearchKeywordsFromChosenSelectField(status);
			if(requirementStatusArray != null && !requirementStatusArray.isEmpty()){
				BoolQueryBuilder reqStatusBoolQueryBuilder = new BoolQueryBuilder();
				for(String keywd : requirementStatusArray){
					reqStatusBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("requirementStatus", keywd));
				}
				
				boolQueryBuilder.must(reqStatusBoolQueryBuilder);
				
			} 

		}
			
		
		//requirementAssignTo
		if(StringUtils.isNotEmpty(search.getFilterAssignTo()) && "Myjobs".equalsIgnoreCase(search.getFilterAssignTo())){
			String assignTo = user.getId().toString();
			BoolQueryBuilder reqAssignToBoolQueryBuilder = new BoolQueryBuilder();
			reqAssignToBoolQueryBuilder.should(QueryBuilders.termQuery("reqLevel1ApproverUserId", assignTo));
			reqAssignToBoolQueryBuilder.should(QueryBuilders.termQuery("reqLevel2ApproverUserId", assignTo));
			reqAssignToBoolQueryBuilder.should(QueryBuilders.termQuery("managerUserId", assignTo));
			reqAssignToBoolQueryBuilder.should(QueryBuilders.termQuery("primaryRecruiterUserId", assignTo));
			reqAssignToBoolQueryBuilder.should(QueryBuilders.termQuery("teamOwnerUserId", assignTo));
			reqAssignToBoolQueryBuilder.should(QueryBuilders.termQuery("teamAssignUserId", assignTo));
			
			
			
			
			boolQueryBuilder.must(reqAssignToBoolQueryBuilder);
			
		}

		
		  //requirementPriority

				if(StringUtils.isNotEmpty(search.getFilterPriority()) || StringUtils.isNotEmpty(search.getFilterPriority())){
					String priority = "";
					if(StringUtils.isNotEmpty(search.getFilterPriority())){
						priority = search.getFilterPriority();
					}
					else{
						priority = search.getFilterPriority();
					}
					ArrayList<String> requirementPriorityArray = getSearchKeywordsFromChosenSelectField(priority);
					if(requirementPriorityArray != null && !requirementPriorityArray.isEmpty()){
						BoolQueryBuilder reqPriorityBoolQueryBuilder = new BoolQueryBuilder();
						for(String keywd : requirementPriorityArray){
							reqPriorityBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("priority", keywd));
						}
						
						boolQueryBuilder.must(reqPriorityBoolQueryBuilder);
						if(StringUtils.isNotEmpty(search.getSearchType()) && search.getSearchType().equalsIgnoreCase("list")){
							boolQueryBuilderForCount.must(reqPriorityBoolQueryBuilder);
						}
					} 
				}
				
				
				//filter for is Client job
				if(StringUtils.isNotEmpty(search.getFilterSpecJobs()) && "ClientJobs".equalsIgnoreCase(search.getFilterSpecJobs())){
					BoolQueryBuilder clientBoolQueryBuilder = new BoolQueryBuilder();
					clientBoolQueryBuilder.must(QueryBuilders.matchPhraseQuery("isClientJob", "Y"));
					boolQueryBuilder.must(clientBoolQueryBuilder);
					
				}
				
				//filter for Normal jobs
				if(StringUtils.isNotEmpty(search.getFilterSpecJobs()) && "NormalJobs".equalsIgnoreCase(search.getFilterSpecJobs())){
					BoolQueryBuilder nonClientBoolQueryBuilder = new BoolQueryBuilder();
					nonClientBoolQueryBuilder.must(QueryBuilders.matchQuery("isClientJob", "N"));
					boolQueryBuilder.must(nonClientBoolQueryBuilder);
					
				}
				
			
		if(StringUtils.isNotEmpty(search.getUpdatedOn())){
			String updatedOn = search.getUpdatedOn();
			String upOn = UpdateOnToDate(updatedOn);
			if(!Constants.ALL_RECORDS.equalsIgnoreCase(upOn)){
			 RangeQueryBuilder qb = QueryBuilders.rangeQuery("createdDate").gte(upOn);
			boolQueryBuilder.must(qb);
			}
		}
		//client
		if(StringUtils.isNotEmpty(search.getClient()) || StringUtils.isNotEmpty(search.getFilterClients())){
			String clients = "";
			if(StringUtils.isNotEmpty(search.getClient())){
				clients = search.getClient();
			}
			else{
				clients = search.getFilterClients();
			}
			ArrayList<String> employerArray = getSearchKeywordsFromChosenSelectField(clients);
			if(employerArray != null && !employerArray.isEmpty()){
				BoolQueryBuilder employerBoolQueryBuilder = new BoolQueryBuilder();
				for(String keywd : employerArray){
					employerBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("client", keywd));
				}
				boolQueryBuilder.must(employerBoolQueryBuilder);
				

			}

		}
		
		//projects
				if(StringUtils.isNotEmpty(search.getFilterProjectId())){
					
					String	projects = search.getFilterProjectId();
					
					ArrayList<String> projectArray = getSearchKeywordsFromChosenSelectField(projects);
					if(projectArray != null && !projectArray.isEmpty()){
						BoolQueryBuilder projectBoolQueryBuilder = new BoolQueryBuilder();
						for(String keywd : projectArray){
							projectBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("projectClientID", keywd));
							
						}
						boolQueryBuilder.must(projectBoolQueryBuilder);
						
					}
				}
				
				
			
		
		if(userRole != null&& !("ROLE_ADMIN".equalsIgnoreCase(userRole) || "ROLE_SOURCELEAD_ADMIN".equalsIgnoreCase(userRole))){
			BoolQueryBuilder assignedBoolQueryBuilder = QueryBuilders.boolQuery();
			//releaseJob To AllUsers
			BoolQueryBuilder releaseJobToAllUsersQueryBuilder = QueryBuilders.boolQuery();
			releaseJobToAllUsersQueryBuilder.must(QueryBuilders.matchQuery("releaseJobToAllUsers", "Y"));
			releaseJobToAllUsersQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementStatus", "DRAFT"));
			releaseJobToAllUsersQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementStatus", "SUBMITTED"));
			releaseJobToAllUsersQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementStatus", "REJECTED"));
			assignedBoolQueryBuilder.should(releaseJobToAllUsersQueryBuilder);
			
			//Created By User
			BoolQueryBuilder createdByQueryBuilder = QueryBuilders.boolQuery();
			createdByQueryBuilder.must(QueryBuilders.termQuery("reqCreatedBy",user.getId().toString()));
			assignedBoolQueryBuilder.should(createdByQueryBuilder);
			
			//For Level1 Approver
			BoolQueryBuilder reqLevel1ApproverUserIdQueryBuilder = QueryBuilders.boolQuery();
			reqLevel1ApproverUserIdQueryBuilder.must(QueryBuilders.termQuery("reqLevel1ApproverUserId", user.getId().toString()));
			reqLevel1ApproverUserIdQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementStatus", "DRAFT"));
			assignedBoolQueryBuilder.should(reqLevel1ApproverUserIdQueryBuilder);
			
			
			//For Level2 Approver
			BoolQueryBuilder reqLevel2ApproverUserIdQueryBuilder = QueryBuilders.boolQuery();
			reqLevel2ApproverUserIdQueryBuilder.must(QueryBuilders.termQuery("reqLevel2ApproverUserId", user.getId().toString()));
			reqLevel2ApproverUserIdQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementStatus", "DRAFT"));
			reqLevel2ApproverUserIdQueryBuilder.must(QueryBuilders.matchQuery("reqLevel1ApproverStatus", "APPROVED"));
			assignedBoolQueryBuilder.should(reqLevel2ApproverUserIdQueryBuilder);
			
			//For Requirement Manager
			BoolQueryBuilder reqManagerUserIdQueryBuilder = QueryBuilders.boolQuery();
			reqManagerUserIdQueryBuilder.must(QueryBuilders.termQuery("managerUserId",user.getId().toString()));
			reqManagerUserIdQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementStatus", "DRAFT"));
			assignedBoolQueryBuilder.should(reqManagerUserIdQueryBuilder);
			
			//For Requirement Primary Recruiter
			BoolQueryBuilder reqPrimaryManagerUserIdQueryBuilder = QueryBuilders.boolQuery();
			reqPrimaryManagerUserIdQueryBuilder.must(QueryBuilders.termQuery("primaryRecruiterUserId",user.getId().toString()));
			reqPrimaryManagerUserIdQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementStatus", "DRAFT"));
			assignedBoolQueryBuilder.should(reqPrimaryManagerUserIdQueryBuilder);
			
			//For Team Owner
			BoolQueryBuilder teamOwnerQueryBuilder = QueryBuilders.boolQuery();
			teamOwnerQueryBuilder.must(QueryBuilders.termQuery("teamOwnerUserId", user.getId().toString()));
			teamOwnerQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementStatus", "DRAFT"));
			teamOwnerQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementStatus", "SUBMITTED"));
			teamOwnerQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementStatus", "REJECTED"));
			assignedBoolQueryBuilder.should(teamOwnerQueryBuilder);
			
			//For Team AssignedUser
			BoolQueryBuilder teamAssignedUserQueryBuilder = QueryBuilders.boolQuery();
			teamAssignedUserQueryBuilder.must(QueryBuilders.termQuery("teamAssignUserId", user.getId().toString()));
			teamAssignedUserQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementStatus", "DRAFT"));
			teamAssignedUserQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementStatus", "SUBMITTED"));
			teamAssignedUserQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementStatus", "REJECTED"));
			assignedBoolQueryBuilder.should(teamAssignedUserQueryBuilder);
			
			
			boolQueryBuilder.must(assignedBoolQueryBuilder);
			boolQueryBuilderForCount.must(assignedBoolQueryBuilder);
		}
		
		SearchResponse searchResponse = sourceleadELKBaseIndexer
				.searchDocumentsResults(Constants.ELK_REQUIREMENT_INDEX, Constants.ELK_REQUIREMENT_TYPE,0,10000,
						 boolQueryBuilder);
		
		SearchHit searchHits[] = searchResponse.getHits().getHits();
		//boolQueryBuilderForCount
		List<Requirement>   reqList = new LinkedList<>();
		if(searchHits != null && searchHits.length >0){
			for (SearchHit hit : searchHits) {
				String sourceAsString = hit.getSourceAsString();
				ObjectMapper mapper = new ObjectMapper();
				if (sourceAsString != null) {
					RequirementIndex requirementIndex = mapper.readValue(sourceAsString, RequirementIndex.class);
					Requirement	requirement = getData(requirementIndex,user,orgLocation);
	            	if(requirement !=null){
	            		RequirementSubmissionsCountDTO applicantsCount = requirementSubmissionService.getAllPipeLineCountsByRequirementIdAndSubmissionStage(requirement.getId(), user, orgLocation, "ALL");
	            		requirement.setTotalApplicants(applicantsCount.getApplicantsCount());
	            		if(applicantsCount.getShortlistCount() != null){
		            		requirement.setShortlistCount(applicantsCount.getShortlistCount().toString());
	            		}
	            		if(applicantsCount.getSubmitToClientCount() != null){
		            		requirement.setSubmitToClientCount(applicantsCount.getSubmitToClientCount().toString());
	            		}
	            		if(applicantsCount.getInterviewCount() != null){
		            		requirement.setInterviewCount(applicantsCount.getInterviewCount().toString());
	            		}
	            		if(applicantsCount.getOffersCount() != null){
		            		requirement.setOffersCount(applicantsCount.getOffersCount().toString());
	            		}
	            		if(applicantsCount.getOnHoldCount() != null){
		            		requirement.setOnHoldCount(applicantsCount.getOnHoldCount().toString());
	            		}
	            		if(applicantsCount.getNoResponseCount() != null){
		            		requirement.setNoResponseCount(applicantsCount.getNoResponseCount().toString());
	            		}
	            		if(applicantsCount.getJoinedCount() != null){
		            		requirement.setJoinedCount(applicantsCount.getJoinedCount().toString());
	            		}
	            		
	            		if(applicantsCount.getBackoutCount() != null){
		            		requirement.setBackoutCount(applicantsCount.getBackoutCount().toString());
	            		}
	            		
	            		if(applicantsCount.getRejectedCount() != null){
		            		requirement.setRejectedCount(applicantsCount.getRejectedCount().toString());
	            		}
	            		
	            		if(requirementIndex.getIndustryName() != null){
		            		requirement.setIndustryName(requirementIndex.getIndustryName());
	            		}
	            		
	            		
	            		if(requirement.getOrgRelationId() != null && !requirement.getOrgRelationId().isEmpty()){
	            			OrgRelations orgRelations = orgRelationsService.getOrgRelationById(Long.parseLong(requirement.getOrgRelationId()));
	            			if(orgRelations != null) {
	            				requirement.setOrgRelationUUID(orgRelations.getUUID());
	            			}
	            		}
	            		
	            		if(requirement.getSpocId() != null){
	            			OrgSpoc orgSpoc = orgSpocService.getOrgSpocById(requirement.getSpocId().getId());
	            			requirement.setSpocUUID(orgSpoc.getUUID());
	            		}
	            		if(StringUtils.isEmpty(requirement.getReqCoolingOffPeriod())){
            				requirement.setReqCoolingOffPeriod(orgLocation.getCoolingOffPeriod());
            			}
	            		
	            		Integer requirementAssignToListCount = requirementAssignToService.getRequirementAssignToListCountByRequirementId(requirement.getId());
	            		requirement.setAssignedMembersCount(requirementAssignToListCount.toString());
	            		reqList.add(requirement);
	            	}
	            }
			}
		}
		
		TimeSheetSubmissionCount requirementSubCount = getRequirementCount(reqList);
		File file = requirementService.getExportRequirementFile(reqList, orgLocation.getOrgLocationName(), search.getExportType(), requirementSubCount);
		
		
	
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
	/*@RequestMapping(value = "/downloadRequirementAttachment", method = RequestMethod.GET)
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
	*/
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
	Response<RequirementSubmissionsCountDTO> getSubmissionsCountBySubmissionStatus(@RequestParam(value = "requirementId", required = false) String requirementId, @RequestParam(value = "submissionType", required = false) String submissionType, HttpServletRequest request) throws Exception
	{
		Response<RequirementSubmissionsCountDTO> response = new Response<>();
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String resultJson = null;
		if (requirementId != null && !requirementId.isEmpty() && !requirementId.equalsIgnoreCase("undefined")) {
			RequirementSubmissionsCountDTO rsCount = requirementSubmissionService.getAllPipeLineCountsByRequirementIdAndSubmissionStage(Long.valueOf(requirementId), user, loginOrgLocation, submissionType);
			//resultJson = commonUtil.toJSON(applicantsCount);
			response.setObject(rsCount);
		}
		return response;
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
	@RequestMapping(value = "/getWorkflowSubmissionsCountBySubmissionStatus", method = RequestMethod.POST)
	@ResponseBody
	Map<String, Integer> getWorkflowSubmissionsCountBySubmissionStatus(@RequestParam(value = "requirementId", required = false) String requirementId, @RequestParam(value = "submissionType", required = false) String submissionType, 
			 @RequestParam(value = "workflowStages", required = false) String[] workflowStages,
			HttpServletRequest request) throws Exception
	{
		Map<String, Integer> stagesCountMap = new HashMap<String, Integer>();
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String resultJson = null;
		if (requirementId != null && !requirementId.isEmpty() && !requirementId.equalsIgnoreCase("undefined")) {
			stagesCountMap = requirementSubmissionService.getAllPipeLineWorlflowCountsByRequirementIdAndSubmissionStage(Long.valueOf(requirementId), user, loginOrgLocation, submissionType, workflowStages);
			//resultJson = commonUtil.toJSON(applicantsCount);
		}
		return stagesCountMap;
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
	@RequestMapping(value = "/ws/downloadOfferAttachment")
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
						if(requirementSubmission.getCandidateResumes() != null) {
							CandidateResumes candidateResumes = candidateResumesService.getCandidateResumeByResumeId(requirementSubmission.getCandidateResumes().getId());
							requirementSubmission.setResumeName(candidateResumes.getFileName());
						}
						
						RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(requirementSubmission.getId());
						List<RequirementInterview> numberOfInterviews = requirementInterviewService.getRequirementInterviewsBySubmissionIdAndOrgLocationsId(requirementSubmission.getId(), loginOrgLocation);
						List<RequirementOffers> requirementOffersList = requirementOffersService.getRequirementOfferSubmissionByIdAndOrgLocationId(requirementSubmission.getId(), loginOrgLocation.getId());
						if (Constants.All_APPLICANTS.equalsIgnoreCase(submissionType)) {
							submissionType = "SUBMISSION";
						}
						List<RequirementSubmissionHistory> requirementSubmissionHistoryServiceList = requirementSubmissionHistoryService.getRequirementHistoryByLocationIdAndStage(loginOrgLocation.getId(), submissionType, requirementSubmission.getId());
						if (requirementSubmissionHistoryServiceList != null
								&& requirementSubmissionHistoryServiceList.size() > 0) {
							RequirementSubmissionHistory requirementSubmissionHistory = requirementSubmissionHistoryServiceList.get(0);
							User usr = userService.getUserByUserCode(requirementSubmissionHistory.getCreatedBy());
							if (usr != null && usr.getFirstName() != null && usr.getLastName() != null) {
								StringBuilder name = new StringBuilder();
								requirementSubmission.setSubmittedBy(name.append(usr.getFirstName()).append(" ").append(usr.getLastName()).toString());
							}else if(usr != null && usr.getFirstName() != null) {
								requirementSubmission.setSubmittedBy(usr.getFirstName());
							}
							else {
								requirementSubmission.setSubmittedBy("---");
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
						if(requirementSubmission.getCandidateResumes() != null) {
							requirementSubmission.setResumeId(requirementSubmission.getCandidateResumes().getId());
						}
						
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
						if(userVal != null) {
							requirementSubmission.setUserName(userVal.getFullName());
						}
						requirementSubmission.setPreviousSubmissionStatus(requirementSubmission.getPreviousSubmissionStatus());
						Candidate candidate = candidateService.getCandidateByCandidateId(requirementSubmission.getCandidate().getId());
						List<Candidate> candidateList = new ArrayList<>();
						candidateList.add(candidate);
						requirementSubmission.setScore(requirementSubmission.getCandidateScore());
				}
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
	@PreAuthorize("hasAnyRole('ROLE_EXPORT_PIPELINE')")
	@RequestMapping(value = "/getWorkflowSubmissionsExportByRequirementId")
	@ResponseBody
	void getWorkflowsubmissionsExportByRequirementId(@RequestParam(value = "requirementId", required = false) String requirementId, 
			@RequestParam(value = "submissionType", required = true) String submissionType,
			@RequestParam(value = "workflowId", required = true) Long workflowId, 
			@RequestParam(value = "exportType", required = true) String exportType, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		File file = null;
		String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		//SimpleDateFormat simpleDateFormat = loginOrgLocation.getLocFullFormDateFormatter();
		//RequirementSubmissionCounts requirementSubmissionCounts = getRequirementSubmissionsCount(Long.valueOf(requirementId), user, loginOrgLocation,loginUserRole);
		
		RequirementSubmissionCounts requirementSubmissionCounts =new RequirementSubmissionCounts();
		if (StringUtils.isNotBlank(requirementId) && StringUtils.isNotBlank(submissionType)) {
			List<RequirementSubmission> requirementStatusList = null;
			//  loading the requirement submission list by dynamic submission(stage) type.
				requirementStatusList = requirementSubmissionService.getSubmissionsByRequirementIdandStatus(Long.valueOf(requirementId), user, loginOrgLocation, submissionType,loginUserRole);
				
			if (requirementStatusList != null) {
			    // setting the work flow stage count by taking the list size.
				requirementSubmissionCounts.setWorkflowStageCount(requirementStatusList.size());
				for (RequirementSubmission requirementSubmission : requirementStatusList) {
						RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(requirementSubmission.getId());
						List<RequirementSubmissionHistory> requirementSubmissionHistoryServiceList = requirementSubmissionHistoryService.getRequirementHistoryByLocationIdAndStage(loginOrgLocation.getId(), submissionType, requirementSubmission.getId());
						if (requirementSubmissionHistoryServiceList != null
								&& requirementSubmissionHistoryServiceList.size() > 0) {
							RequirementSubmissionHistory requirementSubmissionHistory = requirementSubmissionHistoryServiceList.get(0);
							User usr = userService.getUserByUserCode(requirementSubmissionHistory.getCreatedBy());
							if (usr != null && usr.getFirstName() != null && usr.getLastName() != null) {
								StringBuilder name = new StringBuilder();
								requirementSubmission.setSubmittedBy(name.append(usr.getFirstName()).append(" ").append(usr.getLastName()).toString());
							}else if(usr != null && usr.getFirstName() != null) {
								requirementSubmission.setSubmittedBy(usr.getFirstName());
							}
							else {
								requirementSubmission.setSubmittedBy("---");
							}
							requirementSubmission.setSubmittedOn(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),requirementSubmissionHistory.getCreatedDate()));
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
						// added by SRINIVAS SABBU
						requirementSubmission.setAlternateNumber(requirementSubmissionTemplateFields.getAlternateNumber());
						requirementSubmission.setLocation(requirementSubmissionTemplateFields.getLocation());
						requirementSubmission.setDateOfBirthStr(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),requirementSubmissionTemplateFields.getDateOfBirth()));
						requirementSubmission.setCurrentCtc(requirementSubmissionTemplateFields.getCurrentCTC());
						//requirementSubmission.setEducation(requirementSubmissionTemplateFields.getEducation());
						requirementSubmission.setCreatedDateStr(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(), requirementSubmission.getCreatedDate()));
						requirementSubmission.setNoticePeriod(requirementSubmissionTemplateFields.getNoticePeriod());
						if(requirementSubmission.getCandidateResumes() != null) {
							requirementSubmission.setResumeId(requirementSubmission.getCandidateResumes().getId());
						}
						
						//requirementSubmission.setTotalNoOfInterviews(numberOfInterviews.size());
					//	requirementSubmission.setRequirementInterviewList(numberOfInterviews);
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
						requirementSubmission.setPreviousSubmissionStatus(requirementSubmission.getPreviousSubmissionStatus());
						Candidate candidate = candidateService.getCandidateByCandidateId(requirementSubmission.getCandidate().getId());
						List<Candidate> candidateList = new ArrayList<>();
						candidateList.add(candidate);
						requirementSubmission.setScore(requirementSubmission.getCandidateScore());
				}
			}
			file = requirementPipelineExportService.getWorkflowExportRequirementFile(requirementStatusList, loginOrgLocation, exportType, requirementId, workflowId, submissionType, requirementSubmissionCounts);
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
			if (duplicatecount != null && !duplicatecount.isEmpty()) {
				requirementSubmissionCounts.setDuplicateCount(duplicatecount.size());
			}
		}
		return requirementSubmissionCounts;
	}
	
	//@PreAuthorize("hasAnyRole('ROLE_CREATE_REQUIREMENT','ROLE_VIEW_REQUIREMENT','ROLE_VIEW_EDIT_REQUIREMENT')")
	@RequestMapping(value = "/searchJobFairs")
	@ResponseBody
	public ResponseList<JobFairDTO> searchJobFairs(HttpServletRequest request,JobFairDTO jobFairDTO,
			@RequestParam(defaultValue = "false") boolean includeInactive) throws Exception {
		ResponseList<JobFairDTO> response = new ResponseList<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			String roleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "displayName");
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (user != null) {
				String[] tableColumns = {"generatedId" ,"name", "startDate", "endDate", "noOfJobs", "", "", "", "status", "id"};
				/*jobFairDTO.setSortColumn("id");
				jobFairDTO.setSortOrder("desc");*/
				
				int sortColumnIndex = 9; //by default sorting using id
				boolean sortColumnOrder = false; //by default descending order
				if(jobFairDTO.getOrder() != null && !jobFairDTO.getOrder().isEmpty()) {
					sortColumnIndex = Integer.parseInt(jobFairDTO.getOrder().get(0).get("column"));
					sortColumnOrder = !jobFairDTO.getOrder().get(0).get("dir").equalsIgnoreCase("desc");					
				}
				jobFairDTO.setSortColumn(tableColumns[sortColumnIndex]);
				jobFairDTO.setSortOrder(sortColumnOrder ? "asc" : "desc");
				
				jobFairDTO.setUserId(user.getId());
				jobFairDTO.setOrgLocationId(orgLocation.getId());
				
				if("Admin".equalsIgnoreCase(roleName)) {
					jobFairDTO.setAdminUser(true);
				}
				
				response = requirementService.getJobFairsList(jobFairDTO, includeInactive);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
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
	
	@RequestMapping(value = "/jobFairsByRequirementId")
	@ResponseBody
	public ResponseList<JobFairDTO> getJobFairListByRequirementId(HttpServletRequest request,
			@RequestParam(defaultValue = "0") long requirementId) {
		ResponseList<JobFairDTO> response = new ResponseList<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				response.setData(requirementService.getJobFairsDTOListByRequirementId(requirementId));
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
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
	
	/**
	 * Search Requirement
	 * R
	 * @param json
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@PreAuthorize("hasAnyRole('ROLE_CREATE_REQUIREMENT','ROLE_VIEW_REQUIREMENT','ROLE_VIEW_EDIT_REQUIREMENT')")
	@RequestMapping(value = "/searchRequirement")
	public ResponseList<Requirement> searchRequirement(RequirementSearchDTO search, HttpServletRequest request) throws Exception
	{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		ResponseList<Requirement> resp= new ResponseList<>();
		if(user != null) {
			try {
				OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		
				/*
				 * This array is used to enable the sorting feature in data tables. And, these names should match the ELK Candidate index field names.
				 */
//				String[] tableColumns = {"requirementId","", "generatedId.keyword","jobTitle.keyword","process", "client.keyword","rate","billRate", "jobType.keyword", "country.keyword", "totalApplicants", "manager.keyword", "createdDate", ""};
				
				String[] tableColumns = {"requirementId","", "generatedId.keyword","jobTitle.keyword","process", "client.keyword","rate","billRate", "jobType.keyword", "country.keyword", "totalApplicants", "shortListApplicantsCount" ,"submitToClientsCount","interviewCount","offerCount","onHoldCount","onResponseCount","joinedCount","backOutCount","rejectedCount","manager.keyword", "createdDate","experience","positions","industryName.keyword",""};

				
				int sortColumnIndex = 10; //by default sorting using updated date
				boolean sortColumnOrder = false; //by default descending order
				if(search.getOrder() != null && !search.getOrder().isEmpty()) {
					sortColumnIndex = Integer.parseInt(search.getOrder().get(0).get("column"));
					sortColumnOrder = !search.getOrder().get(0).get("dir").equalsIgnoreCase("desc");
				}
				
				
				BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
				boolQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", orgLocation.getId()));
				
				BoolQueryBuilder boolQueryBuilderForCount = new BoolQueryBuilder();
				boolQueryBuilderForCount.must(QueryBuilders.matchQuery("orgLocationId", orgLocation.getId()));
			 
				
				if(StringUtils.isNotEmpty(search.getSearchType())  
						&& StringUtils.isNotEmpty(search.getSearchValue())){
					    ArrayList<String> anyKeyWordArray = getSearchKeywordsFromChosenSelectField(search.getSearchValue());
						if(anyKeyWordArray != null && !anyKeyWordArray.isEmpty()){
							 BoolQueryBuilder anyBoolQueryBuilder = QueryBuilders.boolQuery();
							 for(String keywd : anyKeyWordArray){
								 BoolQueryBuilder anyBoolSubQueryBuilder = QueryBuilders.boolQuery();
								ArrayList<QueryBuilder> QueryBuilderList =getQueryBuilderListForUniversalSearch(keywd);
								if(QueryBuilderList != null && !QueryBuilderList.isEmpty()){
									for(QueryBuilder QueryBuilder :QueryBuilderList){
										anyBoolSubQueryBuilder.should(QueryBuilder);
									}
									DateFormat df = orgLocation.getLocFullFormDateFormatter();
									try{
										if(keywd.contains("/")){
											keywd = keywd.replaceAll("/", "-");
										}
										Date d = df.parse(keywd);
										anyBoolSubQueryBuilder = QueryBuilders.boolQuery();
										String dateStr = commonUtil.elkDateFormatter(d);
										anyBoolSubQueryBuilder.should(QueryBuilders.termQuery("createdDate", dateStr));
									 }
									 catch(Exception e){}
									
									anyBoolQueryBuilder.must(anyBoolSubQueryBuilder);
								}
							}
							 boolQueryBuilder.must(anyBoolQueryBuilder);
						} 
				 }
				 
				
				//Generated Id
				if(StringUtils.isNotEmpty(search.getGeneratedId())){
					boolQueryBuilder.must(QueryBuilders.matchQuery("generatedId", search.getGeneratedId()));
			    }
				
				//Requirement Title
				if(StringUtils.isNotEmpty(search.getRequirementTitle())){
					boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("jobTitle", search.getRequirementTitle()));
			    }
				// jobId
				if(StringUtils.isNotEmpty(search.getJobId())){
					boolQueryBuilder.must(QueryBuilders.matchQuery("jobId", search.getJobId()));
			    }
				
				List<String> jobsTagsArray = search.getJobsTags();
				if (jobsTagsArray != null && !jobsTagsArray.isEmpty()) {
					BoolQueryBuilder tagBoolQueryBuilder = QueryBuilders.boolQuery();
					for (String keywd : jobsTagsArray) {
						tagBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("tags", keywd));
					}
					boolQueryBuilder.must(tagBoolQueryBuilder);
				}
				
			   //Country
				/*if(StringUtils.isNotEmpty(search.getCountry())){
					boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("country", search.getCountry().split(",")[0]));
			    }
				 //State
				if(StringUtils.isNotEmpty(search.getState())){
					boolQueryBuilder.must(QueryBuilders.matchQuery("state", search.getState()));
			    }*/
			 
				//multiple City
				if(StringUtils.isNotEmpty(search.getCity())){
					ArrayList<String> cityArray = getSearchKeywordsFromChosenSelectField(search.getCity());
					if(cityArray != null && !cityArray.isEmpty()){
						BoolQueryBuilder multiCityBoolQueryBuilder = new BoolQueryBuilder();
						for(String keywd : cityArray){
							multiCityBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("city", keywd));
						}
						boolQueryBuilder.must(multiCityBoolQueryBuilder);
					} 
				}
				
				
				if(StringUtils.isNotEmpty(search.getNoticePeriod())){
					ArrayList<String> noticePeriodArray = getSearchKeywordsFromChosenSelectField(search.getNoticePeriod());
					if(noticePeriodArray != null && !noticePeriodArray.isEmpty()){
						BoolQueryBuilder noticePeriodBoolQueryBuilder = new BoolQueryBuilder();
						for(String keywd : noticePeriodArray){
							noticePeriodBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("noticePeriod", keywd));
						}
						boolQueryBuilder.must(noticePeriodBoolQueryBuilder);
					} 
				}
					
				//visa type
						if(StringUtils.isNotEmpty(search.getVisaType())){
							ArrayList<String> immigrationTypeArray = getSearchKeywordsFromChosenSelectField(search.getVisaType());
							if(immigrationTypeArray != null && !immigrationTypeArray.isEmpty()){
								BoolQueryBuilder immigrationTypeBoolQueryBuilder = new BoolQueryBuilder();
								for(String keywd : immigrationTypeArray){
									immigrationTypeBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("visaTypes", keywd));
								}
								boolQueryBuilder.must(immigrationTypeBoolQueryBuilder);
							} 
						}
						
						//Skills
						if(StringUtils.isNotEmpty(search.getRequirementSkills())){
							ArrayList<String> skillsArray = getSearchKeywordsFromChosenSelectField(search.getRequirementSkills());
							if(skillsArray != null && !skillsArray.isEmpty()){
								BoolQueryBuilder skillsBoolQueryBuilder = new BoolQueryBuilder();
								for(String keywd : skillsArray){
									skillsBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("skills", keywd));
								}
								boolQueryBuilder.must(skillsBoolQueryBuilder);
							} 
						}
						
			    //requirementStatus
				if(StringUtils.isNotEmpty(search.getRequirementStatus()) || StringUtils.isNotEmpty(search.getFilterStatus())){
					String status = "";
					if(StringUtils.isNotEmpty(search.getRequirementStatus())){
						status = search.getRequirementStatus();
					}
					else{
						status = search.getFilterStatus();
					}
					ArrayList<String> requirementStatusArray = getSearchKeywordsFromChosenSelectField(status);
					if(requirementStatusArray != null && !requirementStatusArray.isEmpty()){
						BoolQueryBuilder reqStatusBoolQueryBuilder = new BoolQueryBuilder();
						for(String keywd : requirementStatusArray){
							reqStatusBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("requirementStatus", keywd));
						}
						
						boolQueryBuilder.must(reqStatusBoolQueryBuilder);
						if(StringUtils.isNotEmpty(search.getSearchType()) && search.getSearchType().equalsIgnoreCase("list")){
							boolQueryBuilderForCount.must(reqStatusBoolQueryBuilder);
						}
					} 

				}
					
				
				//requirementAssignTo
				if(StringUtils.isNotEmpty(search.getFilterAssignTo()) && "Myjobs".equalsIgnoreCase(search.getFilterAssignTo())){
					String assignTo = user.getId().toString();
					BoolQueryBuilder reqAssignToBoolQueryBuilder = new BoolQueryBuilder();
					reqAssignToBoolQueryBuilder.should(QueryBuilders.termQuery("reqLevel1ApproverUserId", assignTo));
					reqAssignToBoolQueryBuilder.should(QueryBuilders.termQuery("reqLevel2ApproverUserId", assignTo));
					reqAssignToBoolQueryBuilder.should(QueryBuilders.termQuery("managerUserId", assignTo));
					reqAssignToBoolQueryBuilder.should(QueryBuilders.termQuery("primaryRecruiterUserId", assignTo));
					reqAssignToBoolQueryBuilder.should(QueryBuilders.termQuery("teamOwnerUserId", assignTo));
					reqAssignToBoolQueryBuilder.should(QueryBuilders.termQuery("teamAssignUserId", assignTo));
					
					
					
					
					boolQueryBuilder.must(reqAssignToBoolQueryBuilder);
					if(StringUtils.isNotEmpty(search.getSearchType()) && search.getSearchType().equalsIgnoreCase("list")){
						boolQueryBuilderForCount.must(reqAssignToBoolQueryBuilder);
					}
				}

				
				  //requirementPriority
		
						if(StringUtils.isNotEmpty(search.getFilterPriority()) || StringUtils.isNotEmpty(search.getFilterPriority())){
							String priority = "";
							if(StringUtils.isNotEmpty(search.getFilterPriority())){
								priority = search.getFilterPriority();
							}
							else{
								priority = search.getFilterPriority();
							}
							ArrayList<String> requirementPriorityArray = getSearchKeywordsFromChosenSelectField(priority);
							if(requirementPriorityArray != null && !requirementPriorityArray.isEmpty()){
								BoolQueryBuilder reqPriorityBoolQueryBuilder = new BoolQueryBuilder();
								for(String keywd : requirementPriorityArray){
									reqPriorityBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("priority", keywd));
								}
								
								boolQueryBuilder.must(reqPriorityBoolQueryBuilder);
								if(StringUtils.isNotEmpty(search.getSearchType()) && search.getSearchType().equalsIgnoreCase("list")){
									boolQueryBuilderForCount.must(reqPriorityBoolQueryBuilder);
								}
							} 
						}
						
						//jobType
						/*if(StringUtils.isNotEmpty(search.getRequirementStatus()) || StringUtils.isNotEmpty(search.getFilterJobType())){
							String jobType = "";
							if(StringUtils.isNotEmpty(search.getRequirementStatus())){
								jobType = search.getRequirementStatus();
							}
							else{
								jobType = search.getFilterJobType();
							}
							ArrayList<String> requirementJobTypeyArray = getSearchKeywordsFromChosenSelectField(jobType);
							if(requirementJobTypeyArray != null && !requirementJobTypeyArray.isEmpty()){
								BoolQueryBuilder reqJobTypeBoolQueryBuilder = new BoolQueryBuilder();
								for(String keywd : requirementJobTypeyArray){
									reqJobTypeBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("jobType", keywd));
								}
								
								boolQueryBuilder.must(reqJobTypeBoolQueryBuilder);
								if(StringUtils.isNotEmpty(search.getSearchType()) && search.getSearchType().equalsIgnoreCase("list")){
									boolQueryBuilderForCount.must(reqJobTypeBoolQueryBuilder);
								}
							} 
						}*/
						
						
						//filter for is Client job
						if(StringUtils.isNotEmpty(search.getFilterSpecJobs()) && "ClientJobs".equalsIgnoreCase(search.getFilterSpecJobs())){
							BoolQueryBuilder clientBoolQueryBuilder = new BoolQueryBuilder();
							clientBoolQueryBuilder.must(QueryBuilders.matchPhraseQuery("isClientJob", "Y"));
							boolQueryBuilder.must(clientBoolQueryBuilder);
							if(StringUtils.isNotEmpty(search.getSearchType()) && search.getSearchType().equalsIgnoreCase("list")){
								boolQueryBuilderForCount.must(clientBoolQueryBuilder);
							}
						}
						
						//filter for Normal jobs
						if(StringUtils.isNotEmpty(search.getFilterSpecJobs()) && "NormalJobs".equalsIgnoreCase(search.getFilterSpecJobs())){
							BoolQueryBuilder nonClientBoolQueryBuilder = new BoolQueryBuilder();
							nonClientBoolQueryBuilder.must(QueryBuilders.matchQuery("isClientJob", "N"));
							boolQueryBuilder.must(nonClientBoolQueryBuilder);
							if(StringUtils.isNotEmpty(search.getSearchType()) && search.getSearchType().equalsIgnoreCase("list")){
								boolQueryBuilderForCount.must(nonClientBoolQueryBuilder);
							}
						}
						
					
				if(StringUtils.isNotEmpty(search.getUpdatedOn())){
					String updatedOn = search.getUpdatedOn();
					String upOn = UpdateOnToDate(updatedOn);
					if(!Constants.ALL_RECORDS.equalsIgnoreCase(upOn)){
					 RangeQueryBuilder qb = QueryBuilders.rangeQuery("createdDate").gte(upOn);
					boolQueryBuilder.must(qb);
					}
				}
				//client
				if(StringUtils.isNotEmpty(search.getClient()) || StringUtils.isNotEmpty(search.getFilterClients())){
					String clients = "";
					if(StringUtils.isNotEmpty(search.getClient())){
						clients = search.getClient();
					}
					else{
						clients = search.getFilterClients();
					}
					ArrayList<String> employerArray = getSearchKeywordsFromChosenSelectField(clients);
					if(employerArray != null && !employerArray.isEmpty()){
						BoolQueryBuilder employerBoolQueryBuilder = new BoolQueryBuilder();
						for(String keywd : employerArray){
							employerBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("client", keywd));
						}
						boolQueryBuilder.must(employerBoolQueryBuilder);
						if(StringUtils.isNotEmpty(search.getSearchType()) && search.getSearchType().equalsIgnoreCase("list")){
							boolQueryBuilderForCount.must(employerBoolQueryBuilder);
						}

					}

				}
				
				//projects
						if(StringUtils.isNotEmpty(search.getFilterProjectId())){
							
							String	projects = search.getFilterProjectId();
							
							ArrayList<String> projectArray = getSearchKeywordsFromChosenSelectField(projects);
							if(projectArray != null && !projectArray.isEmpty()){
								BoolQueryBuilder projectBoolQueryBuilder = new BoolQueryBuilder();
								for(String keywd : projectArray){
									projectBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("projectClientID", keywd));
									
								}
								boolQueryBuilder.must(projectBoolQueryBuilder);
								if(StringUtils.isNotEmpty(search.getSearchType()) && search.getSearchType().equalsIgnoreCase("list")){
									boolQueryBuilderForCount.must(projectBoolQueryBuilder);
								}
							}
						}
						
						
					
				
				if(userRole != null&& !("ROLE_ADMIN".equalsIgnoreCase(userRole) || "ROLE_SOURCELEAD_ADMIN".equalsIgnoreCase(userRole))){
					BoolQueryBuilder assignedBoolQueryBuilder = QueryBuilders.boolQuery();
					//releaseJob To AllUsers
					BoolQueryBuilder releaseJobToAllUsersQueryBuilder = QueryBuilders.boolQuery();
					releaseJobToAllUsersQueryBuilder.must(QueryBuilders.matchQuery("releaseJobToAllUsers", "Y"));
					releaseJobToAllUsersQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementStatus", "DRAFT"));
					releaseJobToAllUsersQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementStatus", "SUBMITTED"));
					releaseJobToAllUsersQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementStatus", "REJECTED"));
					assignedBoolQueryBuilder.should(releaseJobToAllUsersQueryBuilder);
					
					//Created By User
					BoolQueryBuilder createdByQueryBuilder = QueryBuilders.boolQuery();
					createdByQueryBuilder.must(QueryBuilders.termQuery("reqCreatedBy",user.getId().toString()));
					assignedBoolQueryBuilder.should(createdByQueryBuilder);
					
					//For Level1 Approver
					BoolQueryBuilder reqLevel1ApproverUserIdQueryBuilder = QueryBuilders.boolQuery();
					reqLevel1ApproverUserIdQueryBuilder.must(QueryBuilders.termQuery("reqLevel1ApproverUserId", user.getId().toString()));
					reqLevel1ApproverUserIdQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementStatus", "DRAFT"));
					assignedBoolQueryBuilder.should(reqLevel1ApproverUserIdQueryBuilder);
					
					
					//For Level2 Approver
					BoolQueryBuilder reqLevel2ApproverUserIdQueryBuilder = QueryBuilders.boolQuery();
					reqLevel2ApproverUserIdQueryBuilder.must(QueryBuilders.termQuery("reqLevel2ApproverUserId", user.getId().toString()));
					reqLevel2ApproverUserIdQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementStatus", "DRAFT"));
					reqLevel2ApproverUserIdQueryBuilder.must(QueryBuilders.matchQuery("reqLevel1ApproverStatus", "APPROVED"));
					assignedBoolQueryBuilder.should(reqLevel2ApproverUserIdQueryBuilder);
					
					//For Requirement Manager
					BoolQueryBuilder reqManagerUserIdQueryBuilder = QueryBuilders.boolQuery();
					reqManagerUserIdQueryBuilder.must(QueryBuilders.termQuery("managerUserId",user.getId().toString()));
					reqManagerUserIdQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementStatus", "DRAFT"));
					assignedBoolQueryBuilder.should(reqManagerUserIdQueryBuilder);
					
					//For Requirement Primary Recruiter
					BoolQueryBuilder reqPrimaryManagerUserIdQueryBuilder = QueryBuilders.boolQuery();
					reqPrimaryManagerUserIdQueryBuilder.must(QueryBuilders.termQuery("primaryRecruiterUserId",user.getId().toString()));
					reqPrimaryManagerUserIdQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementStatus", "DRAFT"));
					assignedBoolQueryBuilder.should(reqPrimaryManagerUserIdQueryBuilder);
					
					//For Team Owner
					BoolQueryBuilder teamOwnerQueryBuilder = QueryBuilders.boolQuery();
					teamOwnerQueryBuilder.must(QueryBuilders.termQuery("teamOwnerUserId", user.getId().toString()));
					teamOwnerQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementStatus", "DRAFT"));
					teamOwnerQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementStatus", "SUBMITTED"));
					teamOwnerQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementStatus", "REJECTED"));
					assignedBoolQueryBuilder.should(teamOwnerQueryBuilder);
					
					//For Team AssignedUser
					BoolQueryBuilder teamAssignedUserQueryBuilder = QueryBuilders.boolQuery();
					teamAssignedUserQueryBuilder.must(QueryBuilders.termQuery("teamAssignUserId", user.getId().toString()));
					teamAssignedUserQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementStatus", "DRAFT"));
					teamAssignedUserQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementStatus", "SUBMITTED"));
					teamAssignedUserQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementStatus", "REJECTED"));
					assignedBoolQueryBuilder.should(teamAssignedUserQueryBuilder);
					
					
					boolQueryBuilder.must(assignedBoolQueryBuilder);
					boolQueryBuilderForCount.must(assignedBoolQueryBuilder);
				}
				
				SearchResponse searchResponse = sourceleadELKBaseIndexer
						.searchDocumentWithMaxResults(Constants.ELK_REQUIREMENT_INDEX, Constants.ELK_REQUIREMENT_TYPE,
								search.getStart(), search.getLength(), tableColumns[sortColumnIndex], sortColumnOrder ? SortOrder.ASC : SortOrder.DESC, boolQueryBuilder);
				SearchHit searchHits[] = searchResponse.getHits().getHits();
				//boolQueryBuilderForCount
				List<Requirement>   reqList = new LinkedList<>();
				if(searchHits != null && searchHits.length >0){
					for (SearchHit hit : searchHits) {
						String sourceAsString = hit.getSourceAsString();
						ObjectMapper mapper = new ObjectMapper();
						if (sourceAsString != null) {
							RequirementIndex requirementIndex = mapper.readValue(sourceAsString, RequirementIndex.class);
							Requirement	requirement = getData(requirementIndex,user,orgLocation);
			            	if(requirement !=null){
			            		RequirementSubmissionsCountDTO applicantsCount = requirementSubmissionService.getAllPipeLineCountsByRequirementIdAndSubmissionStage(requirement.getId(), user, orgLocation, "ALL");
			            		requirement.setTotalApplicants(applicantsCount.getApplicantsCount());
			            		if(applicantsCount.getShortlistCount() != null){
				            		requirement.setShortlistCount(applicantsCount.getShortlistCount().toString());
			            		}
			            		if(applicantsCount.getSubmitToClientCount() != null){
				            		requirement.setSubmitToClientCount(applicantsCount.getSubmitToClientCount().toString());
			            		}
			            		if(applicantsCount.getInterviewCount() != null){
				            		requirement.setInterviewCount(applicantsCount.getInterviewCount().toString());
			            		}
			            		if(applicantsCount.getOffersCount() != null){
				            		requirement.setOffersCount(applicantsCount.getOffersCount().toString());
			            		}
			            		if(applicantsCount.getOnHoldCount() != null){
				            		requirement.setOnHoldCount(applicantsCount.getOnHoldCount().toString());
			            		}
			            		if(applicantsCount.getNoResponseCount() != null){
				            		requirement.setNoResponseCount(applicantsCount.getNoResponseCount().toString());
			            		}
			            		if(applicantsCount.getJoinedCount() != null){
				            		requirement.setJoinedCount(applicantsCount.getJoinedCount().toString());
			            		}
			            		
			            		if(applicantsCount.getBackoutCount() != null){
				            		requirement.setBackoutCount(applicantsCount.getBackoutCount().toString());
			            		}
			            		
			            		if(applicantsCount.getRejectedCount() != null){
				            		requirement.setRejectedCount(applicantsCount.getRejectedCount().toString());
			            		}
			            		
			            		if(requirementIndex.getIndustryName() != null){
				            		requirement.setIndustryName(requirementIndex.getIndustryName());
			            		}
			            		
			            		
			            		if(requirement.getOrgRelationId() != null && !requirement.getOrgRelationId().isEmpty()){
			            			OrgRelations orgRelations = orgRelationsService.getOrgRelationById(Long.parseLong(requirement.getOrgRelationId()));
			            			if(orgRelations != null) {
			            				requirement.setOrgRelationUUID(orgRelations.getUUID());
			            			}
			            		}
			            		
			            		if(requirement.getSpocId() != null){
			            			OrgSpoc orgSpoc = orgSpocService.getOrgSpocById(requirement.getSpocId().getId());
			            			requirement.setSpocUUID(orgSpoc.getUUID());
			            		}
			            		if(StringUtils.isEmpty(requirement.getReqCoolingOffPeriod())){
		            				requirement.setReqCoolingOffPeriod(orgLocation.getCoolingOffPeriod());
		            			}
			            		
			            		Integer requirementAssignToListCount = requirementAssignToService.getRequirementAssignToListCountByRequirementId(requirement.getId());
			            		requirement.setAssignedMembersCount(requirementAssignToListCount.toString());
			            		reqList.add(requirement);
			            	}
			            }
					}
			
				 resp.setRecordsFiltered(Long.valueOf(searchResponse.getHits().getTotalHits()).intValue());
				 SearchResponse searchResponseForCount = sourceleadELKBaseIndexer
						.searchDocumentWithMaxResults(Constants.ELK_REQUIREMENT_INDEX, Constants.ELK_REQUIREMENT_TYPE,
								search.getStart(), search.getLength(), tableColumns[sortColumnIndex], sortColumnOrder ? SortOrder.ASC : SortOrder.DESC, boolQueryBuilderForCount);
				
				 resp.setRecordsTotal(searchResponseForCount == null ? resp.getRecordsFiltered() : Long.valueOf(searchResponseForCount.getHits().getTotalHits()).intValue());
				}
				 resp.setData(reqList);
			     
			}catch(Exception e) {
				e.printStackTrace();
				resp.setError(e.getMessage());
			}
		}else {
			resp.setResponse(Result.SESSION_TIMEDOUT.name());
			resp.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
        return resp;
		
	}
	
	private ArrayList<String> getSearchKeywordsFromChosenSelectField(String paramValue) throws Exception {
		 ArrayList<String> keyWordList = null;
		 if(StringUtils.isNotEmpty(paramValue)){
			 keyWordList = new ArrayList<String>(Arrays.asList(paramValue.split(",")));
		 }
		 else{
			 keyWordList = new ArrayList<String>(); 
		 }
		
       return keyWordList;
   }
	 private ArrayList<QueryBuilder> getQueryBuilderListForUniversalSearch(String keywd){
		    ArrayList<QueryBuilder> QueryBuilderList = new ArrayList<QueryBuilder>();
		    QueryBuilderList.add(QueryBuilders.matchPhraseQuery("jobTitle", keywd));
		    QueryBuilderList.add(QueryBuilders.matchPhraseQuery("jobId", keywd));
		    QueryBuilderList.add(QueryBuilders.matchPhraseQuery("skills", keywd));
			QueryBuilderList.add(QueryBuilders.matchPhraseQuery("requirementStatus", keywd));
			QueryBuilderList.add(QueryBuilders.matchPhraseQuery("country", keywd));
			QueryBuilderList.add(QueryBuilders.matchPhraseQuery("city", keywd));
			QueryBuilderList.add(QueryBuilders.matchPhraseQuery("state", keywd));
			QueryBuilderList.add(QueryBuilders.matchPhraseQuery("client", keywd));
			QueryBuilderList.add(QueryBuilders.matchPhraseQuery("jobType", keywd));
			QueryBuilderList.add(QueryBuilders.matchPhraseQuery("priority", keywd));
			QueryBuilderList.add(QueryBuilders.matchPhraseQuery("generatedId", keywd));
			QueryBuilderList.add(QueryBuilders.matchPhraseQuery("manager", keywd));
			QueryBuilderList.add(QueryBuilders.matchPhraseQuery("requirementStatus", keywd));
			QueryBuilderList.add(QueryBuilders.matchPhraseQuery("industryName", keywd));
			//Matcher matcher = Pattern.compile("\\d+").matcher(keywd);
			if(StringUtils.isNumeric(keywd)){
//				Float expYears = Float.valueOf(matcher.group());
				QueryBuilderList.add(QueryBuilders.matchPhraseQuery("minExperience", keywd));
				QueryBuilderList.add(QueryBuilders.matchPhraseQuery("maxExperience", keywd));
			}
			return QueryBuilderList; 
	 }
      public String UpdateOnToDate(String updatedOn){
		
		 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	     Calendar cal = Calendar.getInstance();
	     if((Constants.ONE_DAY_AGO).equalsIgnoreCase(updatedOn)){
	        cal.add(Calendar.DATE, -1);    
	        return  dateFormat.format(cal.getTime());
		 }else if((Constants.THREE_DAY_AGO).equalsIgnoreCase(updatedOn)){
			 cal.add(Calendar.DATE, -3);    
		        return  dateFormat.format(cal.getTime());
		 }else if((Constants.ONE_WEEK_AGO).equalsIgnoreCase(updatedOn)){
			 cal.add(Calendar.DATE, -7);    
		        return  dateFormat.format(cal.getTime());
		 }else if((Constants.TWO_WEEK_AGO).equalsIgnoreCase(updatedOn)){
			 cal.add(Calendar.DATE, -14);    
		        return  dateFormat.format(cal.getTime());
		 }else if((Constants.ONE_MONTH_AGO).equalsIgnoreCase(updatedOn)){
			 cal.add(Calendar.DATE, -30);    
		        return  dateFormat.format(cal.getTime());
		 }else if((Constants.THREE_MONTH_AGO).equalsIgnoreCase(updatedOn)){
			 cal.add(Calendar.DATE, -90);    
		        return  dateFormat.format(cal.getTime());
		 }else{
			 return updatedOn;
		 }
	 }
      private Requirement getData(RequirementIndex requirementIndex, User user,OrgLocations orgLocations) throws Exception {
  		 Requirement requirement = new Requirement();
  		requirement.setId(requirementIndex.getRequirementId());
       	requirement.setJobId(requirementIndex.getJobId());
       	requirement.setJobType(requirementIndex.getJobType());
       	requirement.setOtherJobType(requirementIndex.getOtherJobType());
       	requirement.setJobTitle(requirementIndex.getJobTitle());
       	requirement.setGeneratedId(requirementIndex.getGeneratedId());
       	String convertedStirng = requirementIndex.getCreatedDate();
       	requirement.setOrgRelationId(requirementIndex.getOrgRelationId());
       	requirement.setPriority(requirementIndex.getPriority());
       	if(null != requirementIndex.getCurrentRateCurrency() && !"".equalsIgnoreCase(requirementIndex.getCurrentRateCurrency())){
       		requirement.setCurrencyCode(requirementIndex.getCurrentRateCurrency());
 		}
       	if (null != requirementIndex.getMaxRate()) {
       		requirement.setMaxRate(requirementIndex.getMaxRate().toString());
 		}
 		if (null != requirementIndex.getRate()) {
 			requirement.setRate(requirementIndex.getRate().toString());
 		}
 		if (null != requirementIndex.getRateType() && !"".equals(requirementIndex.getRateType())) {
 			requirement.setRateType(requirementIndex.getRateType());
 		}
 		
 		if(null != requirementIndex.getClientCurrencyCode() && !"".equalsIgnoreCase(requirementIndex.getClientCurrencyCode())){
 			requirement.setClientCurrencyCode(requirementIndex.getClientCurrencyCode());
 		}
 		if(null != requirementIndex.getClientMaxRate()){
 			requirement.setClientMaxRate(requirementIndex.getClientMaxRate().toString());
 		}
 		if(null != requirementIndex.getClientRate()){
 			requirement.setClientRate(requirementIndex.getClientRate().toString());
 		}
 		if(null != requirementIndex.getClientRateType() && !"".equalsIgnoreCase(requirementIndex.getClientRateType())){
 			requirement.setClientRateType(requirementIndex.getClientRateType());
 		}
       	
       	
       	DateFormat elkFormat = new SimpleDateFormat("yyyy-MM-dd"); 
       	Date elkDate = elkFormat.parse(convertedStirng);
       	requirement.setPostedDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),elkDate));
       	if(requirementIndex.getMinExperience() !=null){
       		requirement.setMinExp(requirementIndex.getMinExperience().toString());	
       	}
       	if(requirementIndex.getSkills() !=null){
       		String skills = getReqSkills(requirementIndex.getSkills());
       		requirement.setSkills(skills);
       	}
       	if(requirementIndex.getMaxExperience() !=null){
       		requirement.setMaxExp(requirementIndex.getMaxExperience().toString());
       	}
       	requirement.setPostedOnStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),elkDate));
       	requirement.setClientName(requirementIndex.getClient());
       	requirement.setClientContactName(requirementIndex.getClientContactName());
       	requirement.setClientContactEmail(requirementIndex.getClientContactEmail());
       	requirement.setCountry(requirementIndex.getCountry());
       	requirement.setState(requirementIndex.getState());
       	requirement.setCity(requirementIndex.getCity());
       	requirement.setLocation(requirementIndex.getLocation());
       	requirement.setSearchType("Requirement");
       	requirement.setPositions(requirementIndex.getNumberOfPositions());
       	if(null != requirementIndex.getNumberOfApplicants()){
       		requirement.setTotalApplicants(Integer.parseInt(requirementIndex.getNumberOfApplicants()));
       	}
       	requirement.setManager(requirementIndex.getManager());
       	requirement.setPrimaryRecruiter(requirementIndex.getPrimaryRecruiterUserId());
       	if(StringUtils.isNotEmpty(requirementIndex.getRequirementStatus())){
           	requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.valueOf(requirementIndex.getRequirementStatus()));
       	}
       	if(requirementIndex.getRequirementSubmissionPreviousStatus() != null && !requirementIndex.getRequirementSubmissionPreviousStatus().isEmpty()) {
       		requirement.setRequirementSubmissionPreviousStatus(REQUIREMENT_SUBMISSION_STATUS.valueOf(requirementIndex.getRequirementSubmissionPreviousStatus()));
       	}
       /*	Boolean isLoginUserAsTeamMember =  requirementAssignToService.checkLoginUserIsRequirementAssignedToByRequirementIdAndUserId(requirementIndex.getRequirementId(), user.getId());
  		requirement.setIsLoginUserAsTeamMember(isLoginUserAsTeamMember.toString());*/
       	requirement.setIsLoginUserAsManager(requirementIndex.getManagerUserId());
       	requirement.setLevel1ApproverUserId(requirementIndex.getReqLevel1ApproverUserId());
  		Boolean isLoginUserAsApprover = false;
  		requirement.setLevel2ApproverUserId(requirementIndex.getReqLevel2ApproverUserId());
 		RequirementApproverHierarchy requirementApproverHierarchy = requirementApproverHierarchyService.getRequirementApproverHierarchyByRequirementId(requirementIndex.getRequirementId(), user.getId());
 		if (requirementApproverHierarchy != null) {
 			isLoginUserAsApprover = true;
 		}
 		requirement.setIsLoginUserAsApprover(isLoginUserAsApprover.toString());
         if(StringUtils.isNotEmpty(requirementIndex.getReleasedBy())){
         	requirement.setIsRequirementReleased("Y");
  			List<Long> userId = new ArrayList<Long>();
  			for (String s : requirementIndex.getReleasedBy().split(",")){
  				userId.add(Long.parseLong(s));
  			 }
       		requirement.setRequirementReleasedUserIds(userId);
  		}
         if(null != requirementIndex.getRequirementUUID()){
     		requirement.setRequirementUUID(requirementIndex.getRequirementUUID());	
     	   }
         
     	List<String> requirementLocationsList = new ArrayList<>();
 		List<RequirementLocations> locationsList = requirementLocationsService.getRequirementLocationsListByRequirementId(requirementIndex.getRequirementId());
 		if(locationsList != null && !locationsList.isEmpty()) {
 			for(RequirementLocations location : locationsList) {
 				requirementLocationsList.add(location.getLocation());
 			}
 		}
         
 		requirement.setLocationsList(requirementLocationsList);
         requirement.setProcess(requirementIndex.getProcess());
         REQUIREMENT_SUBMISSION_STATUS status = Enum.valueOf(REQUIREMENT_SUBMISSION_STATUS.class, requirementIndex.getRequirementStatus());
         requirement.setRequirementSubmissionStatus(status);
         requirement.setRequirementCreatedDateToString(requirementIndex.getCreatedDate());
       	return requirement;
  	}
    private String getReqSkills(String[] skills) {
  		StringBuilder sb = new StringBuilder();
  		for(int i=0;i<skills.length;i++){
  			sb.append(skills[i]).append(",");
  		}
  		String str = sb.toString();
  		return str.substring(0, str.length()-1);
  	}
	
	@RequestMapping(value = "/getTimezonesList")
	public @ResponseBody String getTimezonesList(HttpServletRequest request) throws Exception
	{
		String resultJson = null;
		List<Timezone> timezoneList = timeZoneService.getAllTimeZones();
		resultJson = commonUtil.toJSON(timezoneList);
		return resultJson;
	}
	
	@RequestMapping(value = "/getRequirementLocations")
	public @ResponseBody List<RequirementLocationsDTO> getRequirementLocations(@RequestParam(value = "requirementUUID", required = true) String requirementUUID) throws Exception
	{
		
		List<RequirementLocationsDTO> locationsDTOList = new ArrayList<>();
		Requirement requirement = requirementService.getRequirementByRequirementUUID(requirementUUID);
		List<RequirementLocations> locationsList = requirementLocationsService.getRequirementLocationsListByRequirementId(requirement.getId());
		if(locationsList != null && !locationsList.isEmpty()) {
			for(RequirementLocations locations : locationsList) {
				RequirementLocationsDTO locationsDTO = new RequirementLocationsDTO();
				locationsDTO.setLocationName(locations.getLocation());
				
				if(!isNullOrEmpty(locations.getCountry())) {
					locationsDTO.setCountry(locations.getCountry());
				}else {
					locationsDTO.setCountry("");
				}
				
				if(!isNullOrEmpty(locations.getState())) {
					locationsDTO.setState(locations.getState());
				}else {
					locationsDTO.setState("");
				}
				if(!isNullOrEmpty(locations.getCity())) {
					locationsDTO.setCity(locations.getCity());
				}else {
					locationsDTO.setCity("");
				}
				if(!isNullOrEmpty(locations.getZipCode())) {
					locationsDTO.setZipcode(locations.getZipCode());
				}else {
					locationsDTO.setZipcode("");
				}
				if(!isNullOrEmpty(locations.getLatitude())) {
					locationsDTO.setLatitude(locations.getLatitude());
				}else {
					locationsDTO.setLatitude("");
				}
				if(!isNullOrEmpty(locations.getLongitude())) {
					locationsDTO.setLongitude(locations.getLongitude());
				}else {
					locationsDTO.setLongitude("");
				}
				
				locationsDTO.setLocationUUID(locations.getLocationUUID());
				locationsDTO.setId(locations.getId());
				locationsDTOList.add(locationsDTO);
			}
		}
		return locationsDTOList;
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
				globalVarsMap.put("LoginURL", linkBuild.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
				// getting the current EmailScenario by EmailScenario unique
				// name and entity type
				EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MODIFIED_REQUIREMENT_STATUS_NOTIFICATION, Constants.ENTITY_TYPE_NOTIFICATION);
				// covert subject,body and signature
				String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap,notificationScenario);
				notificationScenario.setBody(notificationbody);
				// calling the sendEmail method of emailScenerioUtil
				commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,loginOrgLocation.getOrg(),loginOrgLocation,Constants.MODIFIED_REQUIREMENT_STATUS_EMAIL,new ArrayList<EmailAttachment>());
				String actionLink = "";
				emailScenerioUtil.sendNotification(actionLink, loginUser, vendorContact, releasedRequirement.getSpocId().getVendorLocationId(), notificationScenario,new HashMap<String,String>());
			}
		}
	}
	
	
	
	
	
	
	@RequestMapping(value = "/getRequirementListForCandidateBulkSubmission")
	public @ResponseBody ResponseList<SubmissionRequirementListView> getRequirementListForCandidateBulkSubmission(RequirementDTO requirementDTO,HttpServletRequest request) throws Exception {

	      OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	      User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	    
			
			String[] tableColumns = { "action","generatedId", "jobTitle", "profilePercentage", "clientName", "createdDate", "location"};
			int sortColumnIndex = 1; // by default sorting using updated date
			String sortColumnOrder = "desc"; // by default descending order
			if (requirementDTO.getOrder() != null && !requirementDTO.getOrder().isEmpty()) {
				sortColumnIndex = Integer.parseInt(requirementDTO.getOrder().get(0).get("column"));
				sortColumnOrder = requirementDTO.getOrder().get(0).get("dir");
			}
			requirementDTO.setSortColumn(tableColumns[sortColumnIndex]);
			requirementDTO.setSortOrder(sortColumnOrder);
			ResponseList<SubmissionRequirementListView> requirementDTOList = requirementService.getAllRequirementsForCandidateSubmission(requirementDTO, user.getId(), orgLocations);
			return requirementDTOList;
			
		}
	
	@PreAuthorize("hasRole('ROLE_VIEW_REQUIREMENT')")
	@RequestMapping(value = "/getRequirementListByPostedDateAndSelectedClients")
	public String getRequirementListByPostedDateAndSelectedClients(
			@RequestParam(value = "postedDateRange", required = false) String postedDateRange,
			@RequestParam(defaultValue = "") String selectedClients, HttpServletRequest request) throws Exception {
		String resultJson = null;
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if(user!=null) {			
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
						"orgLocations");
				String clients = "";
				if (selectedClients != null && !selectedClients.isEmpty()) {
					for (String client : selectedClients.split(",")) {
						clients += (clients.isEmpty() ? "" : ",") + "'" + client + "'";
					}
				}
				List<Requirement> requirementList = requirementService.getRequirementListByPostedDateAndSelectedClients(
						loginOrgLocation.getId(), user.getId(), postedDateRange, clients);
				resultJson = commonUtil.toJSON(requirementList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultJson;
	}
	
	
	@RequestMapping(value = "/forceDeleteForSelectedJobs", method = RequestMethod.POST)
	public Response<String> forceDeleteForSelectedJobs(RequirementDTO requirementDTO, HttpServletRequest request) throws Exception
	{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Response<String> response = new Response<>();
		try {
			if (user != null) {
				if(requirementDTO.getReqUUIDArr().length > 0) {
					 Boolean flag =  requirementService.forceDeleteForSelectedJobs(requirementDTO.getReqUUIDArr());

					 response.setResponse(Result.SUCCESS.name());
					 response.setResponseMessage(Result.SUCCESS.getValue());
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
	
}