package com.bis.sourcelead.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.CandidateMarketPlaceDTO;
import com.bis.sourcelead.dto.CandidateSourceDTO;
import com.bis.sourcelead.dto.CandidateVerificationConfigDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.MarketplacePurchaseSalesDTO;
import com.bis.sourcelead.dto.MarketplaceWalletDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateMarketPlace;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.CandidateVerificationConfig;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.RequirementInterviewHistory;
import com.bis.sourcelead.entity.RequirementInterviewer;
import com.bis.sourcelead.entity.RequirementLocations;
import com.bis.sourcelead.entity.RequirementOfferHistory;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.CandidateDocumentsService;
import com.bis.sourcelead.service.CandidateEducationService;
import com.bis.sourcelead.service.CandidateEmployersService;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.RequirementInterviewHistoryService;
import com.bis.sourcelead.service.RequirementInterviewService;
import com.bis.sourcelead.service.RequirementInterviewerService;
import com.bis.sourcelead.service.RequirementLocationsService;
import com.bis.sourcelead.service.RequirementOfferHistoryService;
import com.bis.sourcelead.service.RequirementOffersService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CandidateMarketplaceExportUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.PrimarySkillUtil;
import com.bis.sourcelead.util.ReportsUtil;
import com.bis.sourcelead.util.ResumeParserJsonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.util.SourceleadResumeParserNew;
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
	private SourceleadResumeParserNew sourceleadResumeParser;
	@Autowired
	private SourceleadELKBaseIndexer<CandidateIndex> sourceleadELKBaseIndexer;
	@Autowired
	RequirementService requirementService;
	@Autowired
	OrgLocationService orgLocationService;

	@Autowired
	CandidateEducationService candidateEducationService;

	@Autowired
	CandidateEmployersService candidateEmployersService;

	@Autowired
	CandidateDocumentsService candidateDocumentsService;

	@Autowired
	RequirementLocationsService requirementLocationsService;
	
	@Autowired
	CandidateMarketplaceExportUtil candidateMarketplaceExportUtil;
	
	@Autowired
	ReportsUtil reportsUtil;
	
	@Value("${isResumeparserEnabled}")
	private String isResumeparserEnabled;

	protected boolean isNullOrEmpty(String str) {
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
	 * @param candidateDto
	 * @param request
	 * @return Candidate
	 */
	@PreAuthorize("hasAnyRole('ROLE_CREATE_CANDIDATE','ROLE_VIEW_CANDIDATE','ROLE_VIEW_EDIT_CANDIDATE_EDUCATION','ROLE_CROWDSOURCING_CANDIDATES')")
	@RequestMapping(value = "/storeCandidate", method = RequestMethod.POST)
	@ResponseBody
	public Response<String> storeCandidate(CandidateDTO candidateDto, HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
		User user = userService.getUserById(userId);
		
		if (user != null) {
			try {

				if (Validator.validate(candidateDto)) {
					candidateDto.setStaticPath(staticPath);
					// FileUploadUtil.fillUploadedFileInfo(candidateDto,
					// candidateDto.getFileInfo());
					Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
					OrgLocations orgLocation = orgLocationService.getOrgLocations(orgLocationId);
					Candidate candidate = candidateService.storeCandidate(candidateDto, orgLocation, user);
					SourceLeadSessionManager.setSessionAttribute(request, "candidateCreateMsg",
							"Success new candidate is created");
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					response.setObject("DataMissing");
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			} catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
		} else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		return response;
	}

	@RequestMapping(value = { "/parseResume", "/parseResume" }, method = RequestMethod.POST)
	public @ResponseBody Response<ResumeParserJsonUtil> parseResume(
			@RequestParam(value = "json", required = false) String json, MultipartHttpServletRequest request,
			@RequestParam(value = "locationMapFile", required = false) CommonsMultipartFile locationMapFileData) {
		Response<ResumeParserJsonUtil> response = new Response<>();
		try {
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			String country = (String) SourceLeadSessionManager.getSessionAttribute(request, "country");
			MultipartFile multiFile = null;
			Iterator<String> itrator = request.getFileNames();
			if (itrator.hasNext()) {
				multiFile = request.getFile(itrator.next());
			}
			ResumeParserJsonUtil resumeDetails = null;
			FileInfo fileInfo = new FileInfo();
			File convertedFile = new File("");
				File file = new File(multiFile.getOriginalFilename());
				multiFile.transferTo(file);
				convertedFile = file;
				 fileInfo.setFileName(multiFile.getOriginalFilename());
					fileInfo.setFileURL(convertedFile.getAbsolutePath());
					fileInfo.setContentType(multiFile.getContentType());
			if (null != multiFile && orgLocation !=null && country != null ) {
				
					if("true".equalsIgnoreCase(isResumeparserEnabled)) {
						logger.info("*******Resume Parser Service calling..");
						resumeDetails = sourceleadResumeParser.getCandidateDetailsFromResumeParser(file,fileInfo,country);
					}else {
						logger.info("*******Resume Parser without Service calling..");
						resumeDetails = sourceleadResumeParser.getCandidateDetailsFromResume1(file, country, orgLocation.getDateFormat(), file.getName());
					}
			}else {
				//getting candidate detils from public pages
				if("true".equalsIgnoreCase(isResumeparserEnabled)) {
					logger.info("*******Resume Parser Service calling..");
					resumeDetails = sourceleadResumeParser.getCandidateDetailsFromResumeParser(file,fileInfo,country);
				}else {
					logger.info("*******Resume Parser without Service calling..");
					resumeDetails = sourceleadResumeParser.getCandidateDetailsFromResume1(file, country, orgLocation.getDateFormat(), file.getName());
				}
			}
			response.setObject(resumeDetails);
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}
	
	

	/**
	 * To validate user email
	 *
	 * @param EmailId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateCandidateEmail", method = RequestMethod.POST)
	public @ResponseBody Response<String> validateCandidateEmail(
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "candidateType", required = false) String candidateType, HttpServletRequest request)
			throws Exception {
		Response<String> response = new Response<>();
		try {
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
					"orgLocations");
			Candidate candidate = candidateService.findByCandidateEmail(email, orgLocation.getId());
			if (null != candidate) {
				response.setObject("true");
			} else {
				response.setObject("false");
			}
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	/*
	 * @RequestMapping(value = "/validateSkillName" , method =
	 * RequestMethod.POST) public @ResponseBody Boolean
	 * 
	 * validateSkillName(@RequestParam(value="skillName", required=false) String
	 * skillName, @RequestParam(value="candidateId", required=false) String
	 * candidateId, HttpServletRequest request) throws Exception { Boolean flag
	 * = false; CandidateSkills candidateSkills =
	 * candidateSkillsService.getSkillsByCandidateIdSkillName(candidateId,
	 * skillName); if(null != candidateSkills){ flag =
	 * candidateSkillsService.validateSkillName(skillName); } return flag; }
	 */
	@RequestMapping(value = "/getCandidatesList", method = RequestMethod.POST)
	public @ResponseBody ResponseList<CandidateDTO> getCandidatesList(
			@RequestParam(value = "requirementId", required = false) Long requirementId, HttpServletRequest request)
			throws Exception {
		ResponseList<CandidateDTO> responseList = new ResponseList<>();
		try {
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
					"orgLocations");
			Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);
			List<CandidateDTO> candidateDTOList = candidateService.getCandidateListForAddApplicant(orgLocations,
					requirementId, requirement.getSkills(), null, requirement.getProfilePercentage());
			responseList.setData(candidateDTOList);
			responseList.setResponse(Result.SUCCESS.name());
			responseList.setResponseMessage(Result.SUCCESS.getValue());
		} catch (Exception e) {
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return responseList;
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
	public @ResponseBody Response<Boolean> checkCandidateState(
			@RequestParam(value = "candidateId", required = false) Long candidateId, HttpServletRequest request)
			throws Exception {
		Response<Boolean> response = new Response<>();
		try {
			Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);
			Boolean isCandidateActive = false;
			if (null != candidate) {
				if (candidate.getStatus() == 1) {
					isCandidateActive = true;
				}
			}
			response.setObject(isCandidateActive);
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	/**
	 * @author Rajashekar
	 * @param candidateId
	 * @param request
	 * @return string
	 * @throws Exception
	 */
	@RequestMapping(value = "/downloadCandidateResume", method = RequestMethod.GET)
	public @ResponseBody void downloadCandidateResume(
			@RequestParam(value = "candidateId", required = false) Long candidateId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CandidateResumes candidateResume = candidateResumesService.getCandidateLatesResumes(candidateId, 1);
		response.setContentType("application/octet-stream");
		StringBuilder fileName = new StringBuilder();
		response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"")
				.append(candidateResume.getFileName()).append("\"").toString());
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
	public @ResponseBody ResponseList<User> getUserByRoleId(
			@RequestParam(value = "roleId", required = false) Long roleId, HttpServletRequest request)
			throws Exception {
		ResponseList<User> response = new ResponseList<>();
		try {
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
					"orgLocations");
			List<User> userList = userService.getUsersForConsultantsByUserRoleIdAndOrgLocationId(roleId,
					orgLocations.getId());
			response.setData(userList);
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	@RequestMapping(value = "/getAllRoleUsers", method = RequestMethod.POST)
	public @ResponseBody ResponseList<User> getAllRoleUsers(HttpServletRequest request) throws Exception {
		ResponseList<User> response = new ResponseList<>();
		try {
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
					"orgLocations");
			Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
			List<User> userList = userService.getUsersForAddAsConsultant(org, orgLocations.getId());
			for (User user : userList) {
				UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(),
						orgLocations.getId());
				if (null != userRole) {
					user.setUserType(userRole.getRoles().getDisplayName());
				}
			}
			response.setData(userList);
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	@RequestMapping(value = "/getConsultantUserDetailsById", method = RequestMethod.POST)
	public @ResponseBody ResponseList<String> getConsultantUserDetailsById(
			@RequestParam(value = "userId", required = false) Long userId) {
		ResponseList<String> response = new ResponseList<>();
		try {
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
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
				response.setData(userList);
			} else {
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

	@RequestMapping(value = "/getCandidatesListByCandidateType", method = RequestMethod.POST)
	public @ResponseBody Response<String> getCandidatesListByCandidateType(
			@RequestParam(value = "requirementId", required = false) Long requirementId,
			@RequestParam(value = "candidateType", required = false) String candidateType,
			@RequestParam(value = "applicantSearch", required = false) String applicantSearch,
			HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			List<Candidate> candidatesList = null;
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
					"orgLocations");
			HashMap<String, Long> hm = new HashMap<String, Long>();
			candidatesList = candidateService.getCandidatesByOrgLocationIDAndSearchText(orgLocations.getId(),
					requirementId, candidateType, applicantSearch);
			if (candidatesList != null) {
				for (Candidate candidate : candidatesList) {
					StringBuilder candidateName = new StringBuilder();
					if (!isNullOrEmpty(candidate.getLastName())) {
						candidateName = candidateName.append(candidate.getFirstName()).append(" ")
								.append(candidate.getLastName());
					} else {
						candidateName = candidateName.append(candidate.getFirstName());
					}
					hm.put(candidateName.toString(), candidate.getId());
				}
			}
			response.setObject(commonUtil.toJSON(hm));
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	@PreAuthorize("hasRole('ROLE_ASSIGN_HOTLIST_TO_TEAM')")
	@RequestMapping(value = "/getTeams", method = RequestMethod.POST)
	public @ResponseBody ResponseList<Team> getTeams(HttpServletRequest request) throws Exception {
		ResponseList<Team> responseList = new ResponseList<>();
		try {
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
					"orgLocations");
			List<Team> teamList = teamService.getTeamListByOrgLocationID(orgLocations.getId());
			responseList.setData(teamList);
			responseList.setResponse(Result.SUCCESS.name());
			responseList.setResponseMessage(Result.SUCCESS.getValue());
		} catch (Exception e) {
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return responseList;
	}

	/**
	 * @author Venkat Kesav @ this method is uses for verify Cooling Off Period
	 * @param
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/verifyCoolingOffPeriod")
	public Response<Boolean> verifyCoolingOffPeriod(
			@RequestParam(value = "requirementId", required = false) Long requirementId,
			@RequestParam(value = "candidateEmail", required = false) String candidateEmail,
			@RequestParam(value = "reqCoolingOffPeriod", required = false) String reqCoolingOffPeriod,
			Long orgLocationId) throws Exception {
		Response<Boolean> response = new Response<>();
		try {
			OrgLocations loginOrgLocation = orgLocationService.getOrgLocations(orgLocationId);
			String[] coolingOffArr = null;
			String period = "0";
			if (reqCoolingOffPeriod != null && !reqCoolingOffPeriod.isEmpty() && reqCoolingOffPeriod != "undefined") {
				coolingOffArr = reqCoolingOffPeriod.split(" ");
				period = coolingOffArr[1];
			} else {
				Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);
				if (requirement != null && StringUtils.isNotBlank(requirement.getReqCoolingOffPeriod())) {
					coolingOffArr = requirement.getReqCoolingOffPeriod().split(" ");
					period = coolingOffArr[1];
				}
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
			RequirementSubmissionHistory requirementSubmissionHistory = requirementSubmissionHistoryService
					.isInCoolingOffPeriodByEmailAndLocationsId(candidateEmail, loginOrgLocation,
							reqCoolingOffPeriodDays, requirementId);
			if (null != requirementSubmissionHistory) {
				if (requirementSubmissionHistory.getIsInCoolingOffPeriod()) {
					response.setObject(requirementSubmissionHistory.getIsInCoolingOffPeriod());
				} else {
					response.setObject(false);
				}
			}
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
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

	public ResponseList<RequirementSubmission> getRequirementHistoryListByCandidateId(
			@RequestParam(value = "candidateId", required = false) Long candidateId, HttpServletRequest request)
			throws Exception {
		ResponseList<RequirementSubmission> responseList = new ResponseList<>();
		try {
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
					"orgLocations");
			// SimpleDateFormat format =
			// loginOrgLocation.getLocFullFormDateFormatter();
			List<RequirementSubmission> requirementSubmission = requirementSubmissionService
					.getRequirementHistoryListByCandidateIdAndOrgLocationID(candidateId, loginOrgLocation.getId());
			for (RequirementSubmission req : requirementSubmission) {
				if (req.getRequirement() != null) {
					req.setPositionType(req.getRequirement().getJobTitle());
					req.setCountry(req.getRequirement().getCountry());
					req.setState(req.getRequirement().getState());
					req.setCity(req.getRequirement().getCity());
					req.setRate(req.getRequirement().getRate());
					req.setCreatedDateStr(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),
							req.getCreatedDate()));
					if (req.getRequirement().getManagerUserId().getLastName() != null) {
						req.setOwnerName(req.getRequirement().getManagerUserId().getFirstName() + " "
								+ req.getRequirement().getManagerUserId().getLastName());
					} else {
						req.setOwnerName(req.getRequirement().getManagerUserId().getFirstName());
					}
					List<String> requirementLocationsList = new ArrayList<>();
					List<RequirementLocations> locationsList = requirementLocationsService
							.getRequirementLocationsListByRequirementId(req.getRequirement().getId());
					if (locationsList != null && !locationsList.isEmpty()) {
						for (RequirementLocations location : locationsList) {
							requirementLocationsList.add(location.getLocation());
						}
					}
					req.setLocationsList(requirementLocationsList);

					if (null != req.getRequirement().getSpocId()) {
						if (!"".equalsIgnoreCase(req.getRequirement().getSpocId().getClientUser().getLastName())
								&& null != req.getRequirement().getSpocId().getClientUser().getLastName()) {
							req.setClientName(req.getRequirement().getSpocId().getClientUser().getFirstName() + ""
									+ req.getRequirement().getSpocId().getClientUser().getLastName());
						} else {
							req.setClientName(req.getRequirement().getSpocId().getClientUser().getFirstName());
						}
					}
				}
			}
			responseList.setData(requirementSubmission);
			responseList.setResponse(Result.SUCCESS.name());
			responseList.setResponseMessage(Result.SUCCESS.getValue());
		} catch (Exception e) {
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return responseList;
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
	public ResponseList<RequirementSubmissionHistory> getRequirementHistoryDetailsByCandidateId(
			@RequestParam(value = "candidateId", required = false) Long candidateId,
			@RequestParam(value = "requirementId", required = false) Long requirementId,
			@RequestParam(value = "submissionId", required = false) Long submissionId, HttpServletRequest request)
			throws Exception {
		ResponseList<RequirementSubmissionHistory> response = new ResponseList<>();
		try {
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
					"orgLocations");
			List<RequirementSubmissionHistory> requirementSubmissionhistory = requirementSubmissionHistoryService
					.getRequirementHistoryListByCandidateIdAndOrgLocationIdAndRequirementIdAndSubmissionId(candidateId,
							loginOrgLocation.getId(), requirementId, submissionId);
			// SimpleDateFormat simpleDateFormat =
			// loginOrgLocation.getLocFullFormDateFormatter();
			for (RequirementSubmissionHistory rsh : requirementSubmissionhistory) {
				RequirementOffers ros = requirementOffersService
						.getRequirementOfferSubmissionById(rsh.getSubmissionId().getId());
				if (null != ros) {
					rsh.setOfferResumeid(ros.getId());
				}
				if (null != rsh.getSubmissionId().getCandidateResumes()) {
					rsh.setResumeid(rsh.getSubmissionId().getCandidateResumes().getFileURL());
					rsh.setFileName(rsh.getSubmissionId().getCandidateResumes().getFileName());
					rsh.setFileUrl(rsh.getSubmissionId().getCandidateResumes().getFileURL());
					rsh.setContentType(rsh.getSubmissionId().getCandidateResumes().getFileContentType());
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
					rsh.setUserName(userName.append(rsh.getSubmissionId().getCandidate().getFirstName()).append(" ")
							.append(rsh.getSubmissionId().getCandidate().getLastName()).toString());
				} else {
					rsh.setUserName(userName.append(rsh.getSubmissionId().getCandidate().getFirstName()).toString());
				}
				// user related data is getting
				User user = userService.getUserDetailsByUserCode(rsh.getCreatedBy());
				StringBuilder loginUserName = new StringBuilder();
				if (null != user.getLastName()) {
					rsh.setLoginUserName(loginUserName.append(user.getFirstName()).append(" ")
							.append(user.getLastName()).toString());
				} else {
					rsh.setLoginUserName(loginUserName.append(user.getFirstName()).toString());
				}
				// get the inteviewerName
				if (null != rsh.getInterviewerId() && null != rsh.getInterviewerId().getId()) {
					RequirementInterviewer requirementInterviewer = requirementInterviewerService
							.getInterviwerstByInterviewId(rsh.getInterviewerId().getId());
					StringBuilder interviewerName = new StringBuilder();
					if (!"".equalsIgnoreCase(requirementInterviewer.getInterviewerUser().getLastName())
							&& requirementInterviewer.getInterviewerUser().getLastName() != null) {
						rsh.setInterviewerName(
								interviewerName.append(requirementInterviewer.getInterviewerUser().getFirstName())
										.append(requirementInterviewer.getInterviewerUser().getLastName()).toString());
					} else {
						rsh.setInterviewerName(interviewerName
								.append(requirementInterviewer.getInterviewerUser().getFirstName()).toString());
					}
				}
				// get the client side offer history
				List<RequirementOfferHistory> requirementOfferHistory = requirementOfferHistoryService
						.getRequirementInternalAndIsClientOffersHistoryBySubmissionIdAndOrgLocationsId(submissionId,
								loginOrgLocation.getId());
				for (RequirementOfferHistory roh : requirementOfferHistory) {
					StringBuilder offerClientName = new StringBuilder();
					user = userService.getUserDetailsByUserCode(roh.getCreatedBy());
					if (null != user.getLastName()) {
						roh.setClientName(offerClientName.append(user.getFirstName()).append(" ")
								.append(user.getLastName()).toString());
					} else {
						roh.setClientName(offerClientName.append(user.getFirstName()).toString());
					}
					// this logic is date conversion
					long diff = commonUtil.currentDate().getTime() - roh.getCreatedDate().getTime();
					Object object = getCnadidateHistoryTimeConversion(diff, roh);
					roh = (RequirementOfferHistory) object;
					roh.setOfferResumeId(roh.getOfferId().getId());
					if (roh.getOfferId().getDocFile() != null) {
						roh.setDocFile(roh.getOfferId().getDocFile());
						roh.setFileContentType(roh.getOfferId().getFileContentType());
					}
					roh.setOfferSourceType(roh.getOfferSourceType());
					roh.setNotifyToVendor(roh.getNotifyToVendor());
					rsh.setRequirementOfferHistoryList(requirementOfferHistory);
				}
				// get the client side interview interviewHistory
				List<RequirementInterviewHistory> requirementInterviewHistory = requirementInterviewHistoryService
						.getClientInterviewsHistoryBySubmissionIdAndOrgLocationsId(rsh.getSubmissionId().getId(),
								loginOrgLocation.getId());
				for (RequirementInterviewHistory rh : requirementInterviewHistory) {
					StringBuilder clientName = new StringBuilder();
					rh.setInterviewSourceType(rh.getInterviewSourceType());
					user = userService.getUserDetailsByUserCode(rh.getCreatedBy());
					if (null != user.getLastName()) {
						rh.setClientName(clientName.append(user.getFirstName()).append(" ").append(user.getLastName())
								.toString());
					} else {
						rh.setClientName(clientName.append(user.getFirstName()).toString());
					}
					long diff = commonUtil.currentDate().getTime() - rh.getCreatedDate().getTime();
					Object object = getCnadidateHistoryTimeConversion(diff, rh);
					rh = (RequirementInterviewHistory) object;
					if (null != rh.getInterviewId()) {
						if (null != rh.getInterviewId().getDateOfInterview()) {
							rh.setDateOfInterviewStr(commonUtil.dateStrFromOrgDateFormatter(
									loginOrgLocation.getDateFormat(), rh.getInterviewId().getDateOfInterview()));
						}
						if (null != rh.getInterviewId().getStartTime()) {
							StringBuilder startTime = new StringBuilder();
							String hours = Integer.toString(rh.getInterviewId().getStartTime().getHours());
							String minutes = Integer.toString(rh.getInterviewId().getStartTime().getMinutes());
							rh.setStartTimeStr(CalendarUtil.get12FormatTimeFrom24FormatTime(
									startTime.append(hours).append(":").append(minutes).toString()));
						}
						if (null != rh.getInterviewId().getEndTime()) {
							StringBuilder endTime = new StringBuilder();
							String hours = Integer.toString(rh.getInterviewId().getEndTime().getHours());
							String minutes = Integer.toString(rh.getInterviewId().getEndTime().getMinutes());
							rh.setEndTimeStr(CalendarUtil.get12FormatTimeFrom24FormatTime(
									endTime.append(hours).append(":").append(minutes).toString()));
						}
					}
					rh.setInterviewType(rh.getInterviewId().getInterviewType());
					rsh.setRequirementInterviewHistoryList(requirementInterviewHistory);
				}
				long diff = commonUtil.currentDate().getTime() - rsh.getCreatedDate().getTime();
				Object object = getCnadidateHistoryTimeConversion(diff, rsh);
				rsh = (RequirementSubmissionHistory) object;
				// interview related data is getting
				List<RequirementInterview> RequirementInterview = requirementInterviewService
						.getRequirementInterviewsHistoryBySubmissionIdAndOrgLocationsId(submissionId,
								loginOrgLocation.getId());
				for (RequirementInterview requirementInterview : RequirementInterview) {
					if (null != requirementInterview.getDateOfInterview()) {
						rsh.setInterviewDateStr(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),
								requirementInterview.getDateOfInterview()));
					}
					if (null != requirementInterview.getStartTime()) {
						StringBuilder startTime = new StringBuilder();
						String hours = Integer.toString(requirementInterview.getStartTime().getHours());
						String minutes = Integer.toString(requirementInterview.getStartTime().getMinutes());
						rsh.setInterviewStartTime(CalendarUtil.get12FormatTimeFrom24FormatTime(
								startTime.append(hours).append(":").append(minutes).toString()));
					}
					if (null != requirementInterview.getEndTime()) {
						StringBuilder endTime = new StringBuilder();
						String hours = Integer.toString(requirementInterview.getEndTime().getHours());
						String minutes = Integer.toString(requirementInterview.getEndTime().getMinutes());
						rsh.setInterviewEndTime(CalendarUtil.get12FormatTimeFrom24FormatTime(
								endTime.append(hours).append(":").append(minutes).toString()));
					}
					rsh.setInterviewType(requirementInterview.getInterviewType());
				}
			}
			response.setData(requirementSubmissionhistory);
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	private Object getCnadidateHistoryTimeConversion(long diff, Object obj) {
		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;
		long diffDays = diff / (24 * 60 * 60 * 1000);
		if (obj instanceof RequirementInterviewHistory) {
			StringBuilder time = new StringBuilder();
			RequirementInterviewHistory rih = (RequirementInterviewHistory) obj;
			if (diffDays == 0 && diffHours == 0 && diffMinutes == 0) {
				rih.setInterviewHistoryCreatedDate(time.append(diffSeconds).append(" seconds ago ").toString());
			} else if (diffDays == 0 && diffHours == 0) {
				rih.setInterviewHistoryCreatedDate(time.append(diffMinutes).append(" minutes ago").toString());
			} else if (diffDays == 0) {
				rih.setInterviewHistoryCreatedDate(time.append(diffHours).append(" hours ").append(diffMinutes)
						.append(" minutes ago ").toString());
			} else if (diffDays < 7) {
				rih.setInterviewHistoryCreatedDate(
						time.append(diffDays).append(" days ").append(diffHours).append(" hours ago ").toString());
			} else {
				DateFormat standardFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aa");
				String commonDateFormat = standardFormat.format(rih.getCreatedDate());
				rih.setInterviewHistoryCreatedDate(commonDateFormat.toString());
			}
			return rih;
		} else if (obj instanceof RequirementSubmissionHistory) {
			StringBuilder time = new StringBuilder();
			RequirementSubmissionHistory rsh = (RequirementSubmissionHistory) obj;
			if (diffDays == 0 && diffHours == 0 && diffMinutes == 0) {
				rsh.setCreatedDateStr(time.append(diffSeconds).append(" seconds ago ").toString());
			} else if (diffDays == 0 && diffHours == 0) {
				rsh.setCreatedDateStr(time.append(diffMinutes).append(" minutes ago").toString());
			} else if (diffDays == 0) {
				rsh.setCreatedDateStr(time.append(diffHours).append(" hours ").append(diffMinutes)
						.append(" minutes ago ").toString());
			} else if (diffDays < 7) {
				rsh.setCreatedDateStr(
						time.append(diffDays).append(" days ").append(diffHours).append(" hours ago ").toString());
			} else {
				DateFormat standardFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aa");
				String commonDateFormat = standardFormat.format(rsh.getCreatedDate());
				rsh.setCreatedDateStr(commonDateFormat.toString());
			}
			return rsh;
		} else if (obj instanceof RequirementOfferHistory) {
			StringBuilder time = new StringBuilder();
			RequirementOfferHistory roh = (RequirementOfferHistory) obj;
			if (diffDays == 0 && diffHours == 0 && diffMinutes == 0) {
				roh.setCreateDateStr(time.append(diffSeconds).append(" seconds ago ").toString());
			} else if (diffDays == 0 && diffHours == 0) {
				roh.setCreateDateStr(time.append(diffMinutes).append(" minutes ago").toString());
			} else if (diffDays == 0) {
				roh.setCreateDateStr(time.append(diffHours).append(" hours ").append(diffMinutes)
						.append(" minutes ago ").toString());
			} else if (diffDays < 7) {
				roh.setCreateDateStr(
						time.append(diffDays).append(" days ").append(diffHours).append(" hours ago ").toString());
			} else {
				DateFormat standardFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aa");
				String commonDateFormat = standardFormat.format(roh.getCreatedDate());
				roh.setCreateDateStr(commonDateFormat.toString());
			}
			return roh;
		}
		return null;
	}

	@RequestMapping(value = { "/ws/profileBySkillSet", "/profileBySkillSet" }, method = RequestMethod.POST)
	public @ResponseBody Response<String> profileBySkillSet(
			@RequestParam(value = "allSkills", required = false) String allSkills, HttpServletRequest request)
			throws Exception {
		Response<String> response = new Response<>();
		try {
			String[] mySkills = allSkills.split(",");
			response.setObject(PrimarySkillUtil.returnMyskill(mySkills));
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	@RequestMapping(value = "/ws/dumpCandidateProfilePercentage", method = RequestMethod.GET)
	public void dumpCandidateProfilePercentage(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<Candidate> candidatesList = candidateService.getAllCandidatesList();
		for (Candidate can : candidatesList) {

			int percentage = 0;
			if (can != null) {
				// Basic Details - 30 %
				if (can.getFirstName() != null && can.getEmail() != null) {
					percentage = 30;
				}
				// Resume Details - 10 %
				Integer resumesCount = candidateResumesService.getCandidateActiveResumeCountByCandidateId(can.getId());
				if (resumesCount > 0) {
					percentage = percentage + 10;
				}
				// Education Details - 10 %
				Integer educationsCount = candidateEducationService
						.getCandidateActiveEducationsCountByCandidateId(can.getId());
				if (educationsCount > 0) {
					percentage = percentage + 10;
				}
				// Employment Details - 10 %
				Integer employersCount = candidateEmployersService
						.getCandidateActiveEmployersCountByCandidateId(can.getId());
				if (employersCount > 0) {
					percentage = percentage + 10;
				}

				// Skills Details - 10 %
				if (can.getSkillsName() != null && !"".equalsIgnoreCase(can.getSkillsName())) {
					percentage = percentage + 10;
				}

				// PrimarySkill Details - 5 %
				if (can.getPrimarySkill() != null && !"".equalsIgnoreCase(can.getPrimarySkill())) {
					percentage = percentage + 5;
				}

				int countOtherDetails = 0;
				if (can.getSummary() != null && !"".equalsIgnoreCase(can.getSummary())) {
					countOtherDetails++;
				}
				if (can.getVisaType() != null && !"".equalsIgnoreCase(can.getVisaType())) {
					countOtherDetails++;
				}
				if (can.getExpectedRate() != null && !"".equalsIgnoreCase(can.getExpectedRate())) {
					countOtherDetails++;
				}
				if ((can.getExperienceYears() != null && !"".equalsIgnoreCase(can.getExperienceYears())
						&& Long.parseLong(can.getExperienceYears()) > 0)
						|| (can.getExperienceMonths() != null && !"".equalsIgnoreCase(can.getExperienceMonths())
								&& Long.parseLong(can.getExperienceMonths()) > 0)) {
					countOtherDetails++;
				}
				// any two OtherDetails - 10 %
				if (countOtherDetails >= 2) {
					percentage = percentage + 10;
				}
				// if only one OtherDetails - 5 %
				else if (countOtherDetails == 1) {
					percentage = percentage + 5;
				}

				// photo Details - 5 %
				if (can.getFileUrl() != null) {
					percentage = percentage + 5;
				} else if (can.getConsultantUser() != null && can.getConsultantUser().getFileUrl() != null) {
					percentage = percentage + 5;
				}

				// document Details - 10 %
				Integer documentsCount = candidateDocumentsService
						.getCandidateActiveDocumentsByCandidateId(can.getId());
				if (documentsCount > 0) {
					percentage = percentage + 10;
				}

				can.setCandidateProfilePercentage(percentage);
				candidateService.addCandidate(can);
			}
		}

	}

	public String toJSON(Object obj) {
		XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
			public HierarchicalStreamWriter createWriter(Writer writer) {
				return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
			}
		});
		return xstream.toXML(obj);
	}

	@RequestMapping(value = "/navigateMarketPlaceAjax", method = RequestMethod.POST)
	public String goToMarketplace(
			@RequestParam(value = "candidateIdsVal[]", required = false) List<Long> candidateIdsVal,
			HttpServletRequest request) throws Exception {
		request.getSession().setAttribute("candidateIds", candidateIdsVal);
		return "success";
	}

	// @PreAuthorize("hasAnyRole('ROLE_CREATE_CANDIDATE','ROLE_CROWDSOURCING_CANDIDATES')")
	@RequestMapping(value = "/navigateMarketPlaceList")
	public ModelAndView goToMarketplace(HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		model.setViewName("candidates/candidateMarketPlaceList");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}

	@RequestMapping(value = "/getMarketPlaceCandidateMovedList", method = RequestMethod.POST)
	public @ResponseBody ResponseList<CandidateDTO> getMarketPlaceDraftList(HttpServletRequest request) {
		List<String> candidateIds = (List<String>) request.getSession().getAttribute("candidateIds");
		ResponseList<CandidateDTO> marketCandidates = candidateService.getCandidatesByCandidateIds(candidateIds);
		return marketCandidates;
	}

	@PreAuthorize("hasAnyRole('ROLE_SHARE_PROFILE','ROLE_ADD_PROFILE')")
	@RequestMapping(value = "/saveMarketPlaceData", method = RequestMethod.POST)
	public Response<String> saveMarketPlaceData(@RequestBody List<CandidateMarketPlaceDTO> marketPlaceList,
			HttpServletRequest request) throws NumberFormatException, Exception {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Response<String> response = new Response<>();
		if (user != null) {
			try {
				if (marketPlaceList != null && marketPlaceList.size() > 0) {
					candidateService.saveCandidateMarketPlaceList(marketPlaceList, user);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					response.setObject("DataMissing");
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			} catch (Exception e) {
				logger.error("Exception caugth in saveMarketPlaceData controller" + e.getMessage());
				e.printStackTrace();
			}
		} else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		return response;
		
	}

	// Note this method is Global marketplace Candidate method
	 @PreAuthorize("hasAnyRole('ROLE_SHARE_PROFILE','ROLE_ADD_PROFILE')")
	@RequestMapping(value = "/globalMarketplacePage")
	public ModelAndView goToGlobalMarketplace(HttpServletRequest request) throws Exception {
		logger.debug("inisde goToGlobalMarketplace  conroller =========>");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		//candidateService.updateMarketplaceDataFromCandidateGlobal(orgLocations.getId(),user);
		ModelAndView model = new ModelAndView();
		model.setViewName("candidates/candidateGlobalMarketPlaceList");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}

	 
	// Note this method is Global marketplace Candidate method
	 @PreAuthorize("hasAnyRole('ROLE_SHARE_PROFILE','ROLE_ADD_PROFILE')")
	@RequestMapping(value = "/OrgMarketplace")
	public ModelAndView goToOrgMarketplace(HttpServletRequest request) throws Exception {
		logger.debug("inisde OrgMarketplace  conroller =========>");
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		//candidateService.updateMarketplaceDataFromCandidate(orgLocations.getId(),user);
		model.setViewName("candidates/candidateMarketPlaceView");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	 
	 
		 @PreAuthorize("hasAnyRole('ROLE_SHARE_PROFILE','ROLE_ADD_PROFILE')")
		 @RequestMapping(value = "/PurchaseOrderList")
		 public ModelAndView goToPurchaseOrderList(HttpServletRequest request) throws Exception {
			logger.debug("inside PurchaseOrderList  controller =========>");
			ModelAndView model = new ModelAndView();		
			model.setViewName("candidates/purchaseOrderList");
			model.addObject("sourceleadUrl", sourceleadUrl);			
			return model;
		}
		 
		 //response list for purchaseList table
		 @RequestMapping(value = "/getPurchasedOrderList")
		 public ResponseList<MarketplacePurchaseSalesDTO> getPurchasedOrderList(
				 MarketplacePurchaseSalesDTO marketplacePurchaseSalesDTO,
				 @RequestParam(value = "searchValue", required = false)String searchValue,
				 @RequestParam(value = "purchaseOrderFromDate", required = false)String purchaseOrderFromDate,
				 @RequestParam(value = "purchaseOrderToDate", required = false)String purchaseOrderToDate,
				 @RequestParam(value = "purchaseOrderFilterDate", required = false)String purchaseOrderFilterDate,
				 HttpServletRequest request) throws Exception {
			
			 ResponseList<MarketplacePurchaseSalesDTO> res = new ResponseList<>();
			logger.debug("inside PurchaseOrderList  controller =========>");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			marketplacePurchaseSalesDTO.setOrglocationId(orgLocations.getId());
			try{
			  	String[] tableColumns = { "orderNo", "orderDate", "companyName", "totalResume", "totalPrice", "serviceFee","discount","grandTotal","paymentStatus"    };
			  	int sortColumnIndex = -1; 
		  		String sortColumnOrder = "desc"; 
		  		if (marketplacePurchaseSalesDTO.getOrder() != null && !marketplacePurchaseSalesDTO.getOrder().isEmpty()) {
		  		sortColumnIndex = Integer.parseInt(marketplacePurchaseSalesDTO.getOrder().get(0).get("column"));
		  		sortColumnOrder = marketplacePurchaseSalesDTO.getOrder().get(0).get("dir"); 
		  		marketplacePurchaseSalesDTO.setSortOrder(sortColumnOrder);
		  		}
		  		if (sortColumnIndex != -1){
		  			marketplacePurchaseSalesDTO.setSortColumn(tableColumns[sortColumnIndex]);
		  		}
		  		}catch(Exception e){
		  			e.printStackTrace();
		  			return null;
		  		}
			ResponseList<MarketplacePurchaseSalesDTO>  marketplacePurchaseOrder = candidateService.getMarketplacePurchaseOrder(orgLocations, marketplacePurchaseSalesDTO,searchValue, purchaseOrderFromDate, purchaseOrderToDate,purchaseOrderFilterDate);
			return marketplacePurchaseOrder;
			/*res.setData(marketplacePurchaseOrder);
			return res;*/
		}
		 
	
		 
		// fetching record from marketplace purchase table
		 @PreAuthorize("hasAnyRole('ROLE_SHARE_PROFILE','ROLE_ADD_PROFILE')")
		 @RequestMapping(value = "/PurchaseOrder")
		 public ModelAndView goToPurchaseOrder(@RequestParam(value = "purchaseUuid", required = true) String purchaseUuid,HttpServletRequest request) throws Exception {
			logger.debug("inside PurchaseOrder  controller =========>");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			MarketplacePurchaseSalesDTO marketplacePurchaseOrder = candidateService.getPurchaseDetailByPurchaseUuid(purchaseUuid, orgLocations);
			ModelAndView model = new ModelAndView();
			model.setViewName("candidates/purchaseOrder");
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.addObject("marketplacePurchaseOrder", marketplacePurchaseOrder);
				return model;
		}
				 

		 
			// Note this method is sales orderlist  method
		 @PreAuthorize("hasAnyRole('ROLE_SHARE_PROFILE','ROLE_ADD_PROFILE')")
		 @RequestMapping(value = "/SalesOrderList")
		 public ModelAndView goToSalesOrderList(HttpServletRequest request) throws Exception {
			logger.debug("inisde SalesOrderList  conroller =========>");
			ModelAndView model = new ModelAndView();
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			model.setViewName("candidates/salesOrderList");
			model.addObject("sourceleadUrl", sourceleadUrl);
			return model;
		}
		 
		 
	@PreAuthorize("hasRole('ROLE_VIEW_CART')")
	@RequestMapping(value = "/getAddedMarketplaceCandidates", method = RequestMethod.POST)
	public Map<String, Object[]> getAddedMarketplaceCandidates(
			@RequestParam(value = "candidateIdsVal[]", required = false) List<Long> candidateIdsVal,
			HttpServletRequest request) throws Exception {
		Map<String, Object[]> candidateIds = new HashMap<>();
		if (candidateIdsVal != null) {
			candidateIds = candidateService.getAddedMarketplaceCandidates(candidateIdsVal);
		}
		
		return candidateIds;
	}

	@PreAuthorize("hasRole('ROLE_SHARE_PROFILE')")
	@RequestMapping(value = "/updateMarketPlacePrice", method = RequestMethod.POST)
	public Response<String> updateMarketPlacePrice(@RequestParam(value = "marketPlaceId", required = false) Long id,
			@RequestParam(value = "priceValType", required = false) String priceValType,
			@RequestParam(value = "priceVal", required = false) String priceVal, HttpServletRequest request)
			throws Exception {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Response<String> response = new Response<>();
		if (user != null) {
			try {
				if(id !=null && priceValType != null && priceVal != null){
				candidateService.updateMarketPlacePrice(id, priceVal, priceValType);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
				}else {
					response.setObject("DataMissing");
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			} catch (Exception e) {
				logger.error("Exception caugth in updateMarketPlacePrice controller" + e.getMessage());
				e.printStackTrace();
			}
		}else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		return response;
	}

	@PreAuthorize("hasRole('ROLE_WITHDRAW_PROFILE')")
	@RequestMapping(value = "/withdrawCandidateFromMarketPlace")
	public Response<String> withdrawCandidateFromMarketPlace(@RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "withdrawReason", required = true) String withdrawReason, HttpServletRequest request)
			throws Exception {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		CandidateMarketPlace marketplace = candidateService.getCandidateMarketPlaceDetailsByIdAndOrgLocationId(id, orgLocation.getId());
		Response<String> response = new Response<>();
		if (user != null) {
			try {
				if (null != marketplace) {
					candidateService.withdrawCandidateFromMarketplace(marketplace, withdrawReason);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				}else {
					response.setObject("DataMissing");
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			} catch (Exception e) {
				logger.error("Exception caugth in withdrawCandidateFromMarketPlace controller" + e.getMessage());
				e.printStackTrace();
			}
		}else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		return response;
	}	
	
	@PreAuthorize("hasRole('ROLE_WITHDRAW_PROFILE')")
	@RequestMapping(value = "/withdrawCandidatesFromMarketPlace")
	public Response<String> withdrawCandidatesFromMarketPlace(@RequestParam(value = "id", required = true) List<Long> candidateIds,
			@RequestParam(value = "withdrawReason", required = true) String withdrawReason, HttpServletRequest request)
			throws Exception {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<CandidateMarketPlace> marketplace = candidateService.getCandidateListMarketPlaceDetailsByIdAndOrgLocationId(candidateIds, orgLocation.getId());
		Response<String> response = new Response<>();
		if (user != null) {
			try {
				if (null != marketplace) {
					for(CandidateMarketPlace cmp:marketplace){
						candidateService.withdrawCandidateFromMarketplace(cmp, withdrawReason);
					}
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				}else {
					response.setObject("DataMissing");
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			} catch (Exception e) {
				logger.error("Exception caugth in withdrawCandidateFromMarketPlace controller" + e.getMessage());
				e.printStackTrace();
			}
		}else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		return response;
	}	
	
	

	 @PreAuthorize("hasRole('ROLE_ADD_PROFILE')")
	@RequestMapping(value = "/addCandidateToMarketCart")
	public Response<String> addCandidateToMarketCart(@RequestParam(value = "id", required = true) String uuid, HttpServletRequest request)throws Exception{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		CandidateMarketPlace marketplace = candidateService.getCandidateMarketPlaceDetailsByUUId(uuid);
		Response<String> response = new Response<>();
		if (user != null) {
			try {
				if (null != marketplace) {
					candidateService.addCandidateToMarketCart(user, marketplace, orgLocation);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				}else {
					response.setObject("DataMissing");
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
					}
				} catch (Exception e) {
				logger.error("Exception caugth in addCandidateToMarketCart controller" + e.getMessage());
				e.printStackTrace();
				}
			}else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		return response;
	 }
	
	 @PreAuthorize("hasRole('ROLE_ADD_PROFILE')")
	@RequestMapping(value = "/deleteCandidateMarketCart")
	public Response<String> deleteCandidateMarketCart(@RequestParam(value = "marketUUID", required = true) String candidatgeMarketUUID, HttpServletRequest request)throws Exception{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Response<String> response = new Response<>();
		if (user != null) {
			try {
				if(candidatgeMarketUUID != null){	
					candidateService.deleteCandidateMarketCartByMarketUUID(candidatgeMarketUUID);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				}else {
					response.setObject("DataMissing");
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
					}
				} catch (Exception e) {
				logger.error("Exception caugth in deleteCandidateMarketCart controller" + e.getMessage());
				e.printStackTrace();
				}
			}else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		return response;
	 	}
	 
	@PreAuthorize("hasRole('ROLE_VIEW_CART')") 
	@RequestMapping(value = "/updateMarketplaceCartCountAndPrice")
	public Object[] updateMarketplaceCartCountAndPrice( HttpServletRequest request)throws Exception{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		Object[] countAndSum = null;
		if(user!= null) {
			if(StringUtils.isNotBlank(userRoleName)) {
				OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
					countAndSum = candidateService.updateMarketplaceCartCountAndPrice(orgLocation.getId(), userRoleName, user);
			}
		}
		return countAndSum;
	}
	@PreAuthorize("hasRole('WITHDRAW_PROFILE')")
	@RequestMapping(value = "/deleteCandidateFromMarketcartByCandidateUuid")
	public Response<String> deleteCandidateFromMarketcartByCandidateUuid(@RequestParam(value = "candidateUuid", required = true) String candidateUuid, HttpServletRequest request)throws Exception{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Response<String> response = new Response<>();
		if (user != null) {
			try {
				if(candidateUuid != null){	
					candidateService.deleteCandidateFromMarketcartByCandidateUuid(candidateUuid);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				}else {
					response.setObject("DataMissing");
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
					}
				} catch (Exception e) {
				logger.error("Exception caugth in deleteCandidateFromMarketcartBycandidateUuid controller" + e.getMessage());
				e.printStackTrace();
				}
			}else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		return response;
	 	}
	
	@PreAuthorize("hasRole('WITHDRAW_PROFILE')")
	@RequestMapping(value = "/deleteAllCandidatesFromMarketcart")
	public Response<String> deleteAllCandidatesFromMarketcart(HttpServletRequest request)throws Exception{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Response<String> response = new Response<>();
		if (user != null) {
			try {
				if(orgLocation.getId() != null){	
					candidateService.deleteAllCandidatesFromMarketcart(orgLocation.getId());
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				}else {
					response.setObject("DataMissing");
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
					}
				} catch (Exception e) {
				logger.error("Exception caugth in deleteAllCandidateFromMarketCart controller" + e.getMessage());
				e.printStackTrace();
				}
			}else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		return response;
	 	}
	
	@RequestMapping(value = {"/getDynamicSalesOrderList", "/exportSalesOrderToPdfOrExcel"} )
	 public ResponseList<MarketplacePurchaseSalesDTO> getDynamicSalesOrderList
	 		(MarketplacePurchaseSalesDTO marketplacePurchaseSalesDTO,
			 @RequestParam(value = "searchValue", required = false)String searchValue,
			 @RequestParam(value = "salesOrderFromDate", required = false)String salesOrderFromDate,
			 @RequestParam(value = "salesOrderToDate", required = false)String salesOrderToDate,
			 @RequestParam(value = "salesOrderFilterDate", required = false)String salesOrderFilterDate,
			 HttpServletRequest request, HttpServletResponse response)  {

		try{
			ResponseList<MarketplacePurchaseSalesDTO> responseList = new ResponseList<>();
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		  	String[] tableColumns = { "orderNo", "orderDate", "companyName", "totalResume", "totalPrice", "paymentStatus" };
		  	int sortColumnIndex = -1; 
	  		String sortColumnOrder = "desc"; 
	  		if (marketplacePurchaseSalesDTO.getOrder() != null && !marketplacePurchaseSalesDTO.getOrder().isEmpty()) {
	  		sortColumnIndex = Integer.parseInt(marketplacePurchaseSalesDTO.getOrder().get(0).get("column"));
	  		sortColumnOrder = marketplacePurchaseSalesDTO.getOrder().get(0).get("dir"); 
	  		marketplacePurchaseSalesDTO.setSortOrder(sortColumnOrder);
	  		}
	  		if (sortColumnIndex != -1){
	  			marketplacePurchaseSalesDTO.setSortColumn(tableColumns[sortColumnIndex]);
	  		}
	  		responseList = candidateService.getMarketplaceSalesOrderList(marketplacePurchaseSalesDTO, orgLocations, searchValue, salesOrderFromDate, salesOrderToDate, salesOrderFilterDate);
	  		File file = null;
	  		if(marketplacePurchaseSalesDTO.getExportType() != null){
		    if(marketplacePurchaseSalesDTO.getExportType().equalsIgnoreCase("pdf")) {
		    	 file = candidateMarketplaceExportUtil.exportSalesOrderToPdf(responseList,orgLocations);
		    }else {
		    	 file = candidateMarketplaceExportUtil.exportSalesOrderToExcel(responseList,orgLocations);
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
	  		return responseList;
		}catch(Exception e){
  			e.printStackTrace();
  			return null;
  		}
	}
	
	/*
     *  walletList
     */
	 @RequestMapping(value = "/walletList")
	 public ModelAndView goToWalletList(HttpServletRequest request) throws Exception {
		logger.debug("inisde walletList  conroller =========>");
		ModelAndView model = new ModelAndView();
		model.setViewName("candidates/walletList");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	 
	    @RequestMapping(value = "/getWalletList", method = RequestMethod.POST)
		public @ResponseBody ResponseList<MarketplaceWalletDTO> getWalletList(
				MarketplaceWalletDTO marketplaceWalletDTO,
				@RequestParam(value = "searchValue", required = false) String searchValue,
				@RequestParam(value = "selectedTime", required = false) String selectedTime,
				@RequestParam(value = "transactionType", required = false) String transactionType,
				@RequestParam(value = "fromDate", required = false) String fromDate,
				@RequestParam(value = "toDate", required = false) String toDate,HttpServletRequest request) {
			ResponseList<MarketplaceWalletDTO> marketWalletDTOList = null;
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			marketplaceWalletDTO.setOrgLocationId(orgLocations.getId());
			marketplaceWalletDTO.setOrgLocations(orgLocations);
			logger.debug("inisde getWalletList  conroller =========> --> ");
			try {
				String[] tableColumns = { "transactionId", "transactionDate", "description" ,"amount","paymentStatus"};
																															
				// entity names
				int sortColumnIndex = -1; // by default sorting using updated date
				String sortColumnOrder = "desc"; // by default ascending order
				if (marketplaceWalletDTO.getOrder() != null && !marketplaceWalletDTO.getOrder().isEmpty()) {
					sortColumnIndex = Integer.parseInt(marketplaceWalletDTO.getOrder().get(0).get("column"));
					sortColumnOrder = marketplaceWalletDTO.getOrder().get(0).get("dir"); // sorting order 'dir' means
					marketplaceWalletDTO.setSortOrder(sortColumnOrder); // direction
				}

				if (sortColumnIndex != -1){
					marketplaceWalletDTO.setSortColumn(tableColumns[sortColumnIndex]);
				}
				marketWalletDTOList = candidateService.getWalletList(marketplaceWalletDTO,searchValue,selectedTime,transactionType,fromDate,toDate);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.debug("after controller  getWalletList  --> " + marketWalletDTOList.getRecordsTotal());
			
			return marketWalletDTOList;
		}
	    
	    @RequestMapping(value = {"/validatePanNumber"})
		public Boolean validatePanNumber(
				@RequestParam(value = "panNumber", required = false) String panNumber,@RequestParam(value = "candidateId", required = false) Long candidateId,
				HttpServletRequest request)
				throws Exception {
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			Candidate can = null;
			if (null != candidateId) {
				can = candidateService.getCandidateDetailsByPanNumberAndId(panNumber, orgLocations.getId());
			}
			Boolean flag = false;
			if (can == null) {
				flag = candidateService.validatePanNumber(panNumber, orgLocations.getId());
			}
			return flag;
		}  
	    
	    
	    @RequestMapping(value = { "/validateAadharNumber" })
		public Boolean validateAadharNumber(
				@RequestParam(value = "aadharNumber", required = false) String aadharNumber,@RequestParam(value = "candidateId", required = false) Long candidateId,
				HttpServletRequest request)
				throws Exception {
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			Candidate can = null;
			if (null != candidateId) {
				can = candidateService.getCandidateDetailsByAadharNoAndId(aadharNumber, orgLocations.getId());
			}
			Boolean flag = false;
			if (can == null) {
				flag = candidateService.validateAadharNumber(aadharNumber, orgLocations.getId());
			}
			return flag;
		}  
	    
	    @RequestMapping(value = { "/validateSsnNumber" })
		public Boolean validateSsnNumber(
				@RequestParam(value = "ssnNumber", required = false) String ssnNumber,@RequestParam(value = "candidateId", required = false) Long candidateId,
				HttpServletRequest request)
				throws Exception {
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			Candidate can = null;
			if (null != candidateId) {
				can = candidateService.getCandidateDetailsBySsnNoAndId(ssnNumber, orgLocations.getId());
			}
			Boolean flag = false;
			if (can == null) {
				flag = candidateService.validateSsnNumber(ssnNumber, orgLocations.getId());
			}
			return flag;
		} 

	    @RequestMapping(value = {"/saveDuplicateCheckConfig"},method = RequestMethod.POST)
		public CandidateVerificationConfig saveDuplicateCheckConfig(CandidateVerificationConfigDTO candidateVerificationConfigDTO,HttpServletRequest request) {
			
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");

			return candidateService.saveDuplicateCheckConfig(candidateVerificationConfigDTO,loginOrgLocation,user);
			
		}
		
	    @RequestMapping(value = {"/getDuplicateConfigData"},method = RequestMethod.GET)
		public CandidateVerificationConfig getDuplicateConfigData(CandidateVerificationConfigDTO candidateVerificationConfigDTO,HttpServletRequest request) {
			
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			return candidateService.getCandidateVerificationConfig(loginOrgLocation.getId());
				
		}

	    @RequestMapping(value = {"/getCandidateSourceList", "/rest/getCandidateSourceList"}, method = RequestMethod.POST)
		@ResponseBody
		public ResponseList<CandidateSourceDTO> getCandidateSourceList(HttpServletRequest request, @RequestParam(defaultValue = "") String searchValue,CandidateSourceDTO candidateSourceDTO) {
			ResponseList<CandidateSourceDTO> responseList = new ResponseList<>();
			try {
				User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				if (null != user) {
					boolean forMobile = request.getRequestURI().contains("rest");
					OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
					String[] tableColumns = { "id", "name ", "description"};// should be

					// entity names
					int sortColumnIndex = 1; // by default sorting using invoice date
					String sortColumnOrder = "asc"; // by default descending order
					if (candidateSourceDTO.getOrder() != null && !candidateSourceDTO.getOrder().isEmpty()) {
						sortColumnIndex = Integer.parseInt(candidateSourceDTO.getOrder().get(0).get("column"));
						sortColumnOrder = candidateSourceDTO.getOrder().get(0).get("dir");
					}
					candidateSourceDTO.setSortColumn(tableColumns[sortColumnIndex]);
					candidateSourceDTO.setSortOrder(sortColumnOrder);
					List<CandidateSourceDTO> candidateSourceDTOList = candidateService.getCandidateSourceListByOrgLocationId(loginOrgLocation.getId(), forMobile ? 1 : -1, searchValue,candidateSourceDTO);
					responseList.setData(candidateSourceDTOList);
					responseList.setRecordsTotal(candidateSourceDTOList.size());
					responseList.setRecordsFiltered(candidateSourceDTOList.size());
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
	    @RequestMapping(value = "/saveCandidateSource", method = RequestMethod.POST)
		@ResponseBody
		public Response<Long> saveCandidateSource(HttpServletRequest request, CandidateSourceDTO candidateSourceDTO) {
			Response<Long> response = new Response<>();
			try {
				User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				if (null != user) {
					long result = candidateService.saveCandidateSource(candidateSourceDTO,user, loginOrgLocation);
					if(result == -1) {
						response.setResponse(Result.DUPLICATE_NAME.name());
						response.setResponseMessage(Result.DUPLICATE_NAME.getValue());
					} else if(result > 0) {
						response.setObject(result);
						response.setResponse(Result.SUCCESS.name());
						response.setResponseMessage(Result.SUCCESS.getValue());
					} else {
						response.setResponse(Result.FAILED.name());
						response.setResponseMessage(Result.FAILED.getValue());
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
	    @RequestMapping(value = "/getCandidateSourceById", method = RequestMethod.POST)
		@ResponseBody
		public Response<CandidateSourceDTO> getCandidateSourceDataById(HttpServletRequest request, long candidateSourceId) throws Exception {
			Response<CandidateSourceDTO> response = new Response<>();
			try {
				User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				if (null != user) {
					response.setObject(candidateService.getCandidateSourceById(candidateSourceId, user.getId(), loginOrgLocation));
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
	    
	@RequestMapping(value = "/deleteCandidateSourceById", method = RequestMethod.POST)
	@ResponseBody
	public Response<Boolean> deleteCandidateSourceById(HttpServletRequest request, long candidateSourceId) throws Exception {

		Response<Boolean> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (null != user) {
				response.setObject(candidateService.deleteCandidateSourceById(candidateSourceId));
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

}

