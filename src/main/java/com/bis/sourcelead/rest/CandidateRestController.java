package com.bis.sourcelead.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.RequirementInterviewHistory;
import com.bis.sourcelead.entity.RequirementInterviewer;
import com.bis.sourcelead.entity.RequirementOfferHistory;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.RequirementInterviewHistoryService;
import com.bis.sourcelead.service.RequirementInterviewService;
import com.bis.sourcelead.service.RequirementInterviewerService;
import com.bis.sourcelead.service.RequirementOfferHistoryService;
import com.bis.sourcelead.service.RequirementOffersService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.PrimarySkillUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.util.SourceleadResumeParser;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

/**
 * Handles requests for the Candidate service.
 * 
 * @author Prasad K
 * @date 30th June 2016
 *
 */
@RestController
public class CandidateRestController {
	private static final Logger logger = LoggerFactory.getLogger(CandidateRestController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	@Value("${static.path}")
	private String staticPath;
	@Autowired
	private CandidateService candidateService;
	@Autowired
	private CandidateResumesService candidateResumesService;
	/*
	 * @Autowired private CandidateSkillsService candidateSkillsService;
	 */
	@Autowired
	private UserService userService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private CommonUtil commonUtil;
	@Autowired
	private TeamService teamService;
	@Autowired
	RequirementSubmissionService requirementSubmissionService;
	@Autowired
	RequirementSubmissionHistoryService requirementSubmissionHistoryService;
	@Autowired
	RequirementOffersService requirementOffersService;
	@Autowired
	RequirementInterviewService requirementInterviewService;
	@Autowired
	RequirementInterviewerService requirementInterviewerService;
	@Autowired
	RequirementInterviewHistoryService requirementInterviewHistoryService;
	@Autowired
	RequirementOfferHistoryService requirementOfferHistoryService;
	@Autowired
	private SourceleadResumeParser sourceleadResumeParser;
	@Autowired
	private SourceleadELKBaseIndexer<CandidateIndex> sourceleadELKBaseIndexer;
	@Autowired
	RequirementService requirementService;
	@Autowired
	OrgLocationService orgLocationService;
	
	protected boolean isNullOrEmpty(String str)
	{
		return (str == null || "".equals(str.trim()));
	}
	
	/**
	 * @author prasad k
	 * @since sep 1 2016
	 * @version 0.0.1
	 * 
	 *          This method is used to store/save candidate in candidate table.
	 *          1.internalley store/save in candidateEducation in
	 *          candidateEducation table . 2.internalley store/save in
	 *          candidateSkills in candidateSkills table . 3.internalley
	 *          store/save in candidateResumes in candidateResumes table .
	 *          4.internalley store/save in candidateEmployers in
	 *          candidateEmployers table .
	 * 
	 * 
	 * 
	 * @param jsonObj
	 *            This is the first paramter to storeCandidate method
	 * @param orgLocations
	 *            This is the second paramter to storeCandidate method
	 * @param user
	 *            This is the third paramter to storeCandidate method
	 * @param multiFile
	 *            This is the fourth paramter to storeCandidate method
	 * 
	 * @return Candidate
	 * 
	 * @throws NullPointer
	 */
	@PreAuthorize("hasAnyRole('ROLE_CREATE_CANDIDATE','ROLE_VIEW_CANDIDATE','ROLE_VIEW_EDIT_CANDIDATE_EDUCATION','ROLE_CROWDSOURCING_CANDIDATES')")
	@RequestMapping(value = "/storeCandidate", method = RequestMethod.POST)
	public @ResponseBody String storeCandidate(CandidateDTO candidateDto, HttpServletRequest request) throws Exception
	{
		if(Validator.validate(candidateDto)) {
			candidateDto.setStaticPath(staticPath);
			//FileUploadUtil.fillUploadedFileInfo(candidateDto, candidateDto.getFileInfo());
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			Candidate candidate = candidateService.storeCandidate(candidateDto, orgLocation, user);
		} else{
			return "DataMissing";
		}
		return "success";
	}
	
	@RequestMapping(value = "/parseResume", method = RequestMethod.POST)
	public @ResponseBody String parseResume(@RequestParam(value = "json", required = false) String json, MultipartHttpServletRequest request, @RequestParam(value = "locationMapFile", required = false) CommonsMultipartFile locationMapFileData) throws Exception
	{
		MultipartFile multiFile = null;
		Iterator<String> itrator = request.getFileNames();
		if (itrator.hasNext()) {
			multiFile = request.getFile(itrator.next());
		}
		String resumeDetails = null;
		if (null != multiFile) {
			resumeDetails = sourceleadResumeParser.getCandidateDetailsFromResume(multiFile.getBytes(), request);
		}
		return resumeDetails;
	}
	
	/**
	 * To validate user email
	 * 
	 * @param EmailId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateCandidateEmail", method = RequestMethod.POST)
	public @ResponseBody String validateCandidateEmail(@RequestParam(value = "email", required = false) String email, @RequestParam(value = "candidateType", required = false) String candidateType, HttpServletRequest request) throws Exception
	{
		String result = null;
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Candidate candidate = candidateService.findByCandidateEmail(email, orgLocation.getId());
		if (null != candidate) {
			result = "true";
		}
		else {
			result = "false";
		}
		return result;
	}
	/*
	 * @RequestMapping(value = "/validateSkillName" , method =
	 * RequestMethod.POST) public @ResponseBody Boolean
	 * validateSkillName(@RequestParam(value="skillName", required=false) String
	 * skillName, @RequestParam(value="candidateId", required=false) String
	 * candidateId, HttpServletRequest request) throws Exception { Boolean flag
	 * = false; CandidateSkills candidateSkills =
	 * candidateSkillsService.getSkillsByCandidateIdSkillName(candidateId,
	 * skillName); if(null != candidateSkills){ flag =
	 * candidateSkillsService.validateSkillName(skillName); } return flag; }
	 */
	
	@RequestMapping(value = "/getCandidatesList", method = RequestMethod.POST)
	public @ResponseBody String getCandidatesList(@RequestParam(value = "requirementId", required = false) Long requirementId, @RequestParam(value = "candidateType", required = false) String candidateType, @RequestParam(value = "isAssociate", required = false) String isAssociate, HttpServletRequest request) throws Exception
	{
		String resultJson = null;
		String candidateStatus = null;
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		// List<Candidate> candidatesList =
		// candidateService.getCandidatesByOrgLocationID(orgLocations.getId(),requirementId,candidateType);
		List<Long> candidateIdsList = requirementSubmissionService.getSubmittedCandidateIdsByRequirementId(requirementId);
		Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);
		List<CandidateIndex> candidatesList = new ArrayList<CandidateIndex>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
		boolQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", orgLocations.getId()));
		// boolQueryBuilder.must(QueryBuilders.matchQuery("candidateType",
		// candidateType));
		boolQueryBuilder.must(QueryBuilders.matchQuery("status", 1));
		// Long maxResults =
		// candidateService.getCandidatesCountByOrgLocationID(orgLocations.getId(),candidateStatus);
		if (null != candidateIdsList && candidateIdsList.size() > 0) {
			BoolQueryBuilder candidateBoolQueryBuilder = QueryBuilders.boolQuery();
			for (Long candidateId : candidateIdsList) {
				candidateBoolQueryBuilder.should(QueryBuilders.matchQuery("candidateId", candidateId));
			}
			boolQueryBuilder.mustNot(candidateBoolQueryBuilder);
		}
		if (null != requirement && !isNullOrEmpty(requirement.getSkills())) {
			String[] skills = requirement.getSkills().split(",");
			BoolQueryBuilder skillsBoolQuery = QueryBuilders.boolQuery();
			for (int i = 0; i < skills.length; i++) {
				skillsBoolQuery.should(QueryBuilders.matchQuery("skillsName", skills[i]));
			}
			boolQueryBuilder.must(skillsBoolQuery);
		}
		if (null != isAssociate && isAssociate.equalsIgnoreCase(Constants.YES)) {
			boolQueryBuilder.must(QueryBuilders.matchQuery("isAssociate", isAssociate));
			boolQueryBuilder.must(QueryBuilders.matchQuery("isFavouriteAssociate", "1"));
		}
		else {
			boolQueryBuilder.must(QueryBuilders.matchQuery("isAssociate", isAssociate));
		}
		try {
			SearchHit[] searchHits = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE, 0, Constants.MAX_RESULTS_200, boolQueryBuilder).getHits();
			if (null != searchHits && searchHits.length > 0) {
				ObjectMapper mapper = new ObjectMapper();
				for (SearchHit hit : searchHits) {
					String json = hit.getSourceAsString();
					mapper.setDateFormat(df);
					CandidateIndex candidateIndex = mapper.readValue(json, CandidateIndex.class);
					candidatesList.add(candidateIndex);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		resultJson = toJSON(candidatesList);
		return resultJson;
	}
	
	/**
	 * @author sarath kumar
	 * @since Dec 1 2016
	 * @version 0.0.1
	 * 
	 * 
	 * @throws NullPointer
	 */
	@RequestMapping(value = "/checkCandidateState", method = RequestMethod.POST)
	public @ResponseBody String checkCandidateState(@RequestParam(value = "candidateId", required = false) Long candidateId, HttpServletRequest request) throws Exception
	{
		String resultJson = null;
		Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);
		Boolean isCandidateActive = false;
		if (null != candidate) {
			if (candidate.getStatus() == 1) {
				isCandidateActive = true;
			}
		}
		resultJson = toJSON(isCandidateActive);
		return resultJson;
	}
	
	/**
	 * @author Rajashekar
	 * @param candidateId
	 * @param request
	 * @return string
	 * @throws Exception
	 */
	@RequestMapping(value = "/downloadCandidateResume", method = RequestMethod.GET)
	public @ResponseBody void downloadCandidateResume(@RequestParam(value = "candidateId", required = false) Long candidateId, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CandidateResumes candidateResume = candidateResumesService.getCandidateLatesResumes(candidateId, 1);
		response.setContentType("application/octet-stream");
		StringBuilder fileName = new StringBuilder();
		response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(candidateResume.getFileName()).append("\"").toString());
		if (candidateResume.getFileURL() != null) {
			InputStream inputStream = new FileInputStream(new File(staticPath, candidateResume.getFileURL()));
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
	
	@RequestMapping(value = "/getUserByRoleId", method = RequestMethod.POST)
	public @ResponseBody String getUserByRoleId(@RequestParam(value = "roleId", required = false) Long roleId, HttpServletRequest request) throws Exception
	{
		String resultJson = null;
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<User> userList = userService.getUsersForConsultantsByUserRoleIdAndOrgLocationId(roleId, orgLocations.getId());
		resultJson = toJSON(userList);
		return resultJson;
	}
	
	@RequestMapping(value = "/getAllRoleUsers", method = RequestMethod.POST)
	public @ResponseBody String getAllRoleUsers(HttpServletRequest request) throws Exception
	{
		String resultJson = null;
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		List<User> userList = userService.getUsersForAddAsConsultant(org, orgLocations.getId());
		for (User user : userList) {
			UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocations.getId());
			if (null != userRole) {
				user.setUserType(userRole.getRoles().getDisplayName());
			}
		}
		resultJson = toJSON(userList);
		return resultJson;
	}
	
	@RequestMapping(value = "/getConsultantUserDetailsById", method = RequestMethod.POST)
	public @ResponseBody String getConsultantUserDetailsById(@RequestParam(value = "userId", required = false) Long userId)
	{
		ArrayList<String> userList = new ArrayList<String>();
		User user = null;
		try {
			user = userService.getUserById(userId);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		if (null != user) {
			userList.add(user.getFirstName());
			userList.add(user.getLastName());
			userList.add(user.getEmail());
			userList.add(user.getMobilePhone());
		}
		return commonUtil.toJSON(userList);
	}
	
	@RequestMapping(value = "/getCandidatesListByCandidateType", method = RequestMethod.POST)
	public @ResponseBody String getCandidatesListByCandidateType(@RequestParam(value = "requirementId", required = false) Long requirementId, @RequestParam(value = "candidateType", required = false) String candidateType, @RequestParam(value = "applicantSearch", required = false) String applicantSearch, HttpServletRequest request) throws Exception
	{
		List<Candidate> candidatesList = null;
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		HashMap<String, Long> hm = new HashMap<String, Long>();
		try {
			candidatesList = candidateService.getCandidatesByOrgLocationIDAndSearchText(orgLocations.getId(), requirementId, candidateType, applicantSearch);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		if (candidatesList != null) {
			for (Candidate candidate : candidatesList) {
				StringBuilder candidateName = new StringBuilder();
				if (!isNullOrEmpty(candidate.getLastName())) {
					candidateName = candidateName.append(candidate.getFirstName()).append(" ").append(candidate.getLastName());
				}
				else {
					candidateName = candidateName.append(candidate.getFirstName());
				}
				hm.put(candidateName.toString(), candidate.getId());
			}
		}
		return commonUtil.toJSON(hm);
	}
	
	@PreAuthorize("hasRole('ROLE_ASSIGN_HOTLIST_TO_TEAM')")
	@RequestMapping(value = "/getTeams", method = RequestMethod.POST)
	public @ResponseBody String getTeams(HttpServletRequest request) throws Exception
	{
		String resultJson = null;
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<Team> teamList = teamService.getTeamListByOrgLocationID(orgLocations.getId());
		resultJson = toJSON(teamList);
		return resultJson;
	}
	
	/**
	 * @author Venkat Kesav @ this method is uses for verify Cooling Off Period
	 * @param
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/verifyCoolingOffPeriod")
	public String verifyCoolingOffPeriod(@RequestParam(value = "requirementId", required = false) Long requirementId, @RequestParam(value = "candidateEmail", required = false) String candidateEmail, @RequestParam(value = "reqCoolingOffPeriod", required = false) String reqCoolingOffPeriod, Long orgLocationId) throws Exception
	{
		OrgLocations loginOrgLocation = orgLocationService.getOrgLocations(orgLocationId);
		String resultJson = "false";
		String[] coolingOffArr = null;
		String period = "0";
		if (reqCoolingOffPeriod != null && !reqCoolingOffPeriod.isEmpty() && reqCoolingOffPeriod != "undefined") {
			coolingOffArr = reqCoolingOffPeriod.split(" ");
			period = coolingOffArr[1];
		}
		String reqCoolingOffPeriodDays;
		int Days;
		switch (period) {
			case "Week":
				reqCoolingOffPeriodDays = "7";
				break;
			case "Weeks":
				Days = (Integer.parseInt(coolingOffArr[0]) * 7);
				reqCoolingOffPeriodDays = Integer.toString(Days);
				break;
			case "Month":
				reqCoolingOffPeriodDays = "30";
				break;
			case "Months":
				Days = (Integer.parseInt(coolingOffArr[0]) * 30);
				reqCoolingOffPeriodDays = Integer.toString(Days);
				break;
			case "Year":
				reqCoolingOffPeriodDays = "365";
				break;
			default:
				reqCoolingOffPeriodDays = "0";
		}
		RequirementSubmissionHistory requirementSubmissionHistory = requirementSubmissionHistoryService.isInCoolingOffPeriodByEmailAndLocationsId(candidateEmail, loginOrgLocation, reqCoolingOffPeriodDays, requirementId);
		if (null != requirementSubmissionHistory) {
			if (requirementSubmissionHistory.getIsInCoolingOffPeriod()) {
				resultJson = commonUtil.toJSON(requirementSubmissionHistory.getIsInCoolingOffPeriod());
			}
			else {
				resultJson = commonUtil.toJSON("false");
			}
		}
		return resultJson;
	}
	
	/**
	 * @author Laxman @ this method is uses for get the requirement History by
	 *         candidate
	 * @param candidateId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getRequirementHistoryListByCandidateId")

	public String getRequirementHistoryListByCandidateId(@RequestParam(value = "candidateId", required = false) Long candidateId,HttpServletRequest request) throws Exception {
		 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 String resultJson;
		 //SimpleDateFormat format = loginOrgLocation.getLocFullFormDateFormatter();
		 List<RequirementSubmission> requirementSubmission =requirementSubmissionService.getRequirementHistoryListByCandidateIdAndOrgLocationID(candidateId, loginOrgLocation.getId());
		 for(RequirementSubmission req :requirementSubmission ){
			 req.setPositionType(req.getRequirement().getJobTitle());
			 req.setCountry(req.getRequirement().getCountry());
			 req.setState(req.getRequirement().getState());
			 req.setCity(req.getRequirement().getCity());
			 req.setRate(req.getRequirement().getRate());
			 req.setCreatedDateStr(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),req.getCreatedDate()));
			 if(req.getRequirement().getManagerUserId().getLastName() != null){
				 req.setOwnerName(req.getRequirement().getManagerUserId().getFirstName() +" "+req.getRequirement().getManagerUserId().getLastName());
			 }else{
				 req.setOwnerName(req.getRequirement().getManagerUserId().getFirstName());
			 }
			
			 if(null != req.getRequirement().getSpocId()){
				 if(!"".equalsIgnoreCase(req.getRequirement().getSpocId().getClientUser().getLastName()) && null != req.getRequirement().getSpocId().getClientUser().getLastName() ){
					 req.setClientName(req.getRequirement().getSpocId().getClientUser().getFirstName() +""+req.getRequirement().getSpocId().getClientUser().getLastName());
				 }else{
					 req.setClientName(req.getRequirement().getSpocId().getClientUser().getFirstName());
				 }
			 }
			 
		 }
		 resultJson = commonUtil.toJSON(requirementSubmission);
		 return resultJson;
	}	
	


	
	/**
	 * @author Laxman this method is used for candidate submitte related resumes
	 *         list
	 * @param candidateId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getRequirementHistoryDetailsByCandidateId")
	public String getRequirementHistoryDetailsByCandidateId(@RequestParam(value = "candidateId", required = false) Long candidateId, @RequestParam(value = "requirementId", required = false) Long requirementId, @RequestParam(value = "submissionId", required = false) Long submissionId, HttpServletRequest request) throws Exception
	{
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String resultJson;
		List<RequirementSubmissionHistory> requirementSubmissionhistory = requirementSubmissionHistoryService.getRequirementHistoryListByCandidateIdAndOrgLocationIdAndRequirementIdAndSubmissionId(candidateId, loginOrgLocation.getId(), requirementId, submissionId);
		//SimpleDateFormat simpleDateFormat = loginOrgLocation.getLocFullFormDateFormatter();
		for (RequirementSubmissionHistory rsh : requirementSubmissionhistory) {
			RequirementOffers ros = requirementOffersService.getRequirementOfferSubmissionById(rsh.getSubmissionId().getId());
			if (null != ros) {
				rsh.setOfferResumeid(ros.getId());
			}
			if (null != rsh.getSubmissionId().getCandidateResumes()) {
				rsh.setResumeid(rsh.getSubmissionId().getCandidateResumes().getResumeUUID());
				rsh.setFileName(rsh.getSubmissionId().getCandidateResumes().getFileName());
			}
			if (!"".equalsIgnoreCase(rsh.getSubmissionId().getOrgLocations().getOrg().toString())
					&& null != rsh.getSubmissionId().getOrgLocations().getOrg()) {
				rsh.setOrgLocationId(rsh.getSubmissionId().getOrgLocations().getOrg().getOrgCode());
			}
			rsh.setToStage(rsh.getToStage());
			rsh.setSubmissionStage(rsh.getSubmissionId().getSubmissionStatus().toString());
			rsh.setRequirementReson(rsh.getSubmissionId().getRejectedReason());
			StringBuilder userName = new StringBuilder();
			if (null != rsh.getSubmissionId().getCandidate().getLastName()) {
				rsh.setUserName(userName.append(rsh.getSubmissionId().getCandidate().getFirstName()).append(" ").append(rsh.getSubmissionId().getCandidate().getLastName()).toString());
			}
			else {
				rsh.setUserName(userName.append(rsh.getSubmissionId().getCandidate().getFirstName()).toString());
			}
			// user related data is getting
			User user = userService.getUserDetailsByUserCode(rsh.getCreatedBy());
			StringBuilder loginUserName = new StringBuilder();
			if (null != user.getLastName()) {
				rsh.setLoginUserName(loginUserName.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
			}
			else {
				rsh.setLoginUserName(loginUserName.append(user.getFirstName()).toString());
			}
			// get the inteviewerName
			if (null != rsh.getInterviewerId() && null != rsh.getInterviewerId().getId()) {
				RequirementInterviewer requirementInterviewer = requirementInterviewerService.getInterviwerstByInterviewId(rsh.getInterviewerId().getId());
				StringBuilder interviewerName = new StringBuilder();
				if (!"".equalsIgnoreCase(requirementInterviewer.getInterviewerUser().getLastName())
						&& requirementInterviewer.getInterviewerUser().getLastName() != null) {
					rsh.setInterviewerName(interviewerName.append(requirementInterviewer.getInterviewerUser().getFirstName()).append(requirementInterviewer.getInterviewerUser().getLastName()).toString());
				}
				else {
					rsh.setInterviewerName(interviewerName.append(requirementInterviewer.getInterviewerUser().getFirstName()).toString());
				}
			}
			// get the client side offer history
			List<RequirementOfferHistory> requirementOfferHistory = requirementOfferHistoryService.getRequirementInternalAndIsClientOffersHistoryBySubmissionIdAndOrgLocationsId(submissionId, loginOrgLocation.getId());
			for (RequirementOfferHistory roh : requirementOfferHistory) {
				StringBuilder offerClientName = new StringBuilder();
				user = userService.getUserDetailsByUserCode(roh.getCreatedBy());
				if (null != user.getLastName()) {
					roh.setClientName(offerClientName.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
				}
				else {
					roh.setClientName(offerClientName.append(user.getFirstName()).toString());
				}
				// this logic is date conversion
				long diff = commonUtil.currentDate().getTime() - roh.getCreatedDate().getTime();
				Object object = getCnadidateHistoryTimeConversion(diff, roh);
				roh = (RequirementOfferHistory) object;
				roh.setOfferResumeId(roh.getOfferId().getId());
				roh.setOfferSourceType(roh.getOfferSourceType());
				roh.setNotifyToVendor(roh.getNotifyToVendor());
				rsh.setRequirementOfferHistoryList(requirementOfferHistory);
			}
			// get the client side interview interviewHistory
			List<RequirementInterviewHistory> requirementInterviewHistory = requirementInterviewHistoryService.getClientInterviewsHistoryBySubmissionIdAndOrgLocationsId(rsh.getSubmissionId().getId(), loginOrgLocation.getId());
			for (RequirementInterviewHistory rh : requirementInterviewHistory) {
				StringBuilder clientName = new StringBuilder();
				rh.setInterviewSourceType(rh.getInterviewSourceType());
				user = userService.getUserDetailsByUserCode(rh.getCreatedBy());
				if (null != user.getLastName()) {
					rh.setClientName(clientName.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
				}
				else {
					rh.setClientName(clientName.append(user.getFirstName()).toString());
				}
				long diff = commonUtil.currentDate().getTime() - rh.getCreatedDate().getTime();
				Object object = getCnadidateHistoryTimeConversion(diff, rh);
				rh = (RequirementInterviewHistory) object;
				if (null != rh.getInterviewId()) {
					if (null != rh.getInterviewId().getDateOfInterview()) {
						rh.setDateOfInterviewStr(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),rh.getInterviewId().getDateOfInterview()));
					}
					if (null != rh.getInterviewId().getStartTime()) {
						StringBuilder startTime = new StringBuilder();
						String hours = Integer.toString(rh.getInterviewId().getStartTime().getHours());
						String minutes = Integer.toString(rh.getInterviewId().getStartTime().getMinutes());
						rh.setStartTimeStr(CalendarUtil.get12FormatTimeFrom24FormatTime(startTime.append(hours).append(":").append(minutes).toString()));
					}
					if (null != rh.getInterviewId().getEndTime()) {
						StringBuilder endTime = new StringBuilder();
						String hours = Integer.toString(rh.getInterviewId().getEndTime().getHours());
						String minutes = Integer.toString(rh.getInterviewId().getEndTime().getMinutes());
						rh.setEndTimeStr(CalendarUtil.get12FormatTimeFrom24FormatTime(endTime.append(hours).append(":").append(minutes).toString()));
					}
				}
				rh.setInterviewType(rh.getInterviewId().getInterviewType());
				rsh.setRequirementInterviewHistoryList(requirementInterviewHistory);
			}
			long diff = commonUtil.currentDate().getTime() - rsh.getCreatedDate().getTime();
			Object object = getCnadidateHistoryTimeConversion(diff, rsh);
			rsh = (RequirementSubmissionHistory) object;
			// interview related data is getting
			List<RequirementInterview> RequirementInterview = requirementInterviewService.getRequirementInterviewsHistoryBySubmissionIdAndOrgLocationsId(submissionId, loginOrgLocation.getId());
			for (RequirementInterview requirementInterview : RequirementInterview) {
				if (null != requirementInterview.getDateOfInterview()) {
					rsh.setInterviewDateStr(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),requirementInterview.getDateOfInterview()));
				}
				if (null != requirementInterview.getStartTime()) {
					StringBuilder startTime = new StringBuilder();
					String hours = Integer.toString(requirementInterview.getStartTime().getHours());
					String minutes = Integer.toString(requirementInterview.getStartTime().getMinutes());
					rsh.setInterviewStartTime(CalendarUtil.get12FormatTimeFrom24FormatTime(startTime.append(hours).append(":").append(minutes).toString()));
				}
				if (null != requirementInterview.getEndTime()) {
					StringBuilder endTime = new StringBuilder();
					String hours = Integer.toString(requirementInterview.getEndTime().getHours());
					String minutes = Integer.toString(requirementInterview.getEndTime().getMinutes());
					rsh.setInterviewEndTime(CalendarUtil.get12FormatTimeFrom24FormatTime(endTime.append(hours).append(":").append(minutes).toString()));
				}
				rsh.setInterviewType(requirementInterview.getInterviewType());
			}
		}
		resultJson = commonUtil.toJSON(requirementSubmissionhistory);
		return resultJson;
	}
	
	private Object getCnadidateHistoryTimeConversion(long diff, Object obj)
	{
		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;
		long diffDays = diff / (24 * 60 * 60 * 1000);
		if (obj instanceof RequirementInterviewHistory) {
			StringBuilder time = new StringBuilder();
			RequirementInterviewHistory rih = (RequirementInterviewHistory) obj;
			if (diffDays == 0 && diffHours == 0 && diffMinutes == 0) {
				rih.setInterviewHistoryCreatedDate(time.append(diffSeconds).append(" seconds ago ").toString());
			}
			else if (diffDays == 0 && diffHours == 0) {
				rih.setInterviewHistoryCreatedDate(time.append(diffMinutes).append(" minutes ago").toString());
			}
			else if (diffDays == 0) {
				rih.setInterviewHistoryCreatedDate(time.append(diffHours).append(" hours ").append(diffMinutes).append(" minutes ago ").toString());
			}
			else if (diffDays < 7) {
				rih.setInterviewHistoryCreatedDate(time.append(diffDays).append(" days ").append(diffHours).append(" hours ago ").toString());
			}
			else {
				DateFormat standardFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aa");
				String commonDateFormat = standardFormat.format(rih.getCreatedDate());
				rih.setInterviewHistoryCreatedDate(commonDateFormat.toString());
			}
			return rih;
		}
		else if (obj instanceof RequirementSubmissionHistory) {
			StringBuilder time = new StringBuilder();
			RequirementSubmissionHistory rsh = (RequirementSubmissionHistory) obj;
			if (diffDays == 0 && diffHours == 0 && diffMinutes == 0) {
				rsh.setCreatedDateStr(time.append(diffSeconds).append(" seconds ago ").toString());
			}
			else if (diffDays == 0 && diffHours == 0) {
				rsh.setCreatedDateStr(time.append(diffMinutes).append(" minutes ago").toString());
			}
			else if (diffDays == 0) {
				rsh.setCreatedDateStr(time.append(diffHours).append(" hours ").append(diffMinutes).append(" minutes ago ").toString());
			}
			else if (diffDays < 7) {
				rsh.setCreatedDateStr(time.append(diffDays).append(" days ").append(diffHours).append(" hours ago ").toString());
			}
			else {
				DateFormat standardFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aa");
				String commonDateFormat = standardFormat.format(rsh.getCreatedDate());
				rsh.setCreatedDateStr(commonDateFormat.toString());
			}
			return rsh;
		}
		else if (obj instanceof RequirementOfferHistory) {
			StringBuilder time = new StringBuilder();
			RequirementOfferHistory roh = (RequirementOfferHistory) obj;
			if (diffDays == 0 && diffHours == 0 && diffMinutes == 0) {
				roh.setCreateDateStr(time.append(diffSeconds).append(" seconds ago ").toString());
			}
			else if (diffDays == 0 && diffHours == 0) {
				roh.setCreateDateStr(time.append(diffMinutes).append(" minutes ago").toString());
			}
			else if (diffDays == 0) {
				roh.setCreateDateStr(time.append(diffHours).append(" hours ").append(diffMinutes).append(" minutes ago ").toString());
			}
			else if (diffDays < 7) {
				roh.setCreateDateStr(time.append(diffDays).append(" days ").append(diffHours).append(" hours ago ").toString());
			}
			else {
				DateFormat standardFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aa");
				String commonDateFormat = standardFormat.format(roh.getCreatedDate());
				roh.setCreateDateStr(commonDateFormat.toString());
			}
			return roh;
		}
		return null;
	}
	
	@RequestMapping(value = "/profileBySkillSet", method = RequestMethod.POST)
	public @ResponseBody String profileBySkillSet(@RequestParam(value = "allSkills", required = false) String allSkills, HttpServletRequest request) throws Exception
	{
		String[] mySkills = allSkills.split(",");
		return PrimarySkillUtil.returnMyskill(mySkills);
	}
	
	public String toJSON(Object obj)
	{
		XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
			public HierarchicalStreamWriter createWriter(Writer writer)
			{
				return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
			}
		});
		return xstream.toXML(obj);
	}
}
