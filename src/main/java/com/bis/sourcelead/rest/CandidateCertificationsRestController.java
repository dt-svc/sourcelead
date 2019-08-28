package com.bis.sourcelead.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.CandidateCertificationDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.index.helper.CandidateIndexHelper;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateCertifications;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.CandidateCertificationsService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

/**
 * @author Shivayogi
 *
 */
@RestController
public class CandidateCertificationsRestController {

	@Autowired
	private CandidateCertificationsService candidateCertificationsService;

	@Autowired
	CommonUtil commonUtil;

	@Autowired
	private CandidateService candidateService;

	@Autowired
	UserService userService;

	@Autowired
	private OrgLocationService orgLocationService;

	@Autowired
	private SourceleadELKBaseIndexer<CandidateIndex> sourceleadELKBaseIndexer;
	
	@Autowired
	CandidateIndexHelper candidateIndexHelper;

	private static final Logger logger = LoggerFactory.getLogger(CandidateCertificationsRestController.class);

	/**
	 * Handles CandidateCartifications store
	 * 
	 * @author Ajith Matta
	 * 
	 * @param json
	 * 
	 * @param request
	 * 
	 * @return ResponseBody
	 * 
	 * @throws Exception
	 */

	/** @author: Mahaveer Manoj, This method is used for Android application **/
	@RequestMapping(value = "/rest/storeCandidateCertifications", method = RequestMethod.POST)
	public @ResponseBody Response<String> storeCandidateCertifications(@RequestBody CandidateCertificationDTO candidateCertificationDTO,
			HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			response = storeCandidateCertifications(candidateCertificationDTO, Long.parseLong(candidateCertificationDTO.getCandidateId()),
					request);
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	// @PreAuthorize("hasAnyRole('ROLE_ADD_CANDIDATE_CERTIFICATION','ROLE_CROWDSOURCING_PROFILE','ROLE_CROWDSOURCING_CANDIDATES','ROLE_ADD_ASSOCIATE_CERTIFICATION_DETAILS')")
	@RequestMapping(value = "/storeCandidateCertifications", method = RequestMethod.POST)
	public @ResponseBody Response<String> storeCandidateCertifications(CandidateCertificationDTO candidateCertificationDTO,
			@RequestParam(value = "candidateId", required = false) Long candidateId, HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null && Validator.validate(candidateCertificationDTO)) {
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				candidateCertificationDTO.setDateFormat(loginOrgLocation.getDateFormat());
				candidateCertificationsService.storeCandidateCertifications(candidateCertificationDTO, user, loginOrgLocation, candidateId);
				Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);
				if (candidate != null) {
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					response.setResponse(Result.FAILED.name());
					response.setResponseMessage(Result.FAILED.getValue());
				}
			} else if (user == null) {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			} else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	/**
	 * This Method is to getCandidateCertificationListByCandidateIdAndOrgLocationID
	 * 
	 * @author Sammeta David Raju
	 * 
	 * @param candidateId
	 * 
	 * @return String
	 * 
	 * @throws Exception
	 */
	// @PreAuthorize("hasAnyRole('ROLE_VIEW_CANDIDATE_CERTIFICATION','ROLE_CROWDSOURCING_PROFILE','ROLE_CROWDSOURCING_CANDIDATES')")
	@RequestMapping(value = { "/getCandidateCertificationListByCandidateIdAndOrgLocationID",
			"rest/getCandidateCertificationListByCandidateIdAndOrgLocationID" }, method = RequestMethod.POST)
	public ResponseList<CandidateCertificationDTO> getCandidateCertificationListByCandidateIdAndOrgLocationID(
			@RequestParam(defaultValue = "0") long candidateId, HttpServletRequest request) throws Exception {
		ResponseList<CandidateCertificationDTO> reponseList = new ResponseList<>();
		try {
			List<CandidateCertificationDTO> candidateCertificationDTOList = new ArrayList<>();
			if (candidateId > 0) {
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				if (loginOrgLocation != null) {
					List<CandidateCertifications> candidateCertificationsList = candidateCertificationsService
							.getCandidateCertificationListByCandidateIdAndOrgLocationID(candidateId, loginOrgLocation.getId());
					for (CandidateCertifications candidateCertifications : candidateCertificationsList) {
						CandidateCertificationDTO candidateCertificationDTO = new CandidateCertificationDTO();
						candidateCertificationDTO.setCandidateId(candidateId + "");
						candidateCertificationDTO.setId(candidateCertifications.getId());
						candidateCertificationDTO.setCandidatecertificateID(String.valueOf(candidateCertifications.getId()));
						candidateCertificationDTO.setCandidateCertificationId(candidateCertifications.getId());
						candidateCertificationDTO.setName(candidateCertifications.getName());
						candidateCertificationDTO.setProvider(candidateCertifications.getProvider());
						candidateCertificationDTO.setScore(candidateCertifications.getScore());
						candidateCertificationDTO.setScoreType(candidateCertifications.getScoreType());
						candidateCertificationDTO.setDescription(candidateCertifications.getDescription());
						candidateCertificationDTO.setCandidatevalidFromDateToString(commonUtil
								.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(), candidateCertifications.getValidFrom()));
						candidateCertificationDTO.setCandidatevalidToDateToString(commonUtil
								.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(), candidateCertifications.getValidTo()));
						candidateCertificationDTO.setValidFrom(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),
								candidateCertifications.getValidFrom()));
						candidateCertificationDTO.setValidTo(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),
								candidateCertifications.getValidTo()));
						candidateCertificationDTOList.add(candidateCertificationDTO);
					}
					reponseList.setData(candidateCertificationDTOList);
					reponseList.setResponse(Result.SUCCESS.name());
					reponseList.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					reponseList.setResponse(Result.SESSION_TIMEDOUT.name());
					reponseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				}
			} else {
				reponseList.setResponse(Result.INVALID_USER.name());
				reponseList.setResponseMessage(Result.INVALID_USER.getValue());
			}
		} catch (Exception e) {
			reponseList.setResponse(Result.AWKWARD.name());
			reponseList.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return reponseList;
	}

	/**
	 * This Method is to remove CandidateEmployer By candidateId and
	 * CandidateEmployerID
	 * 
	 * @author Sammeta David Raju
	 * 
	 * @param candidateemployersId
	 * @param candidateId
	 * @param request
	 * 
	 * @return String
	 * 
	 * @throws Exception
	 */
	// @PreAuthorize("hasAnyRole('ROLE_VIEW_DELETE_CANDIDATE_CERTIFICATION','ROLE_CROWDSOURCING_PROFILE','ROLE_CROWDSOURCING_CANDIDATES','ROLE_VIEW_DELETE_ASSOCIATE_CERTIFICATION_DETAILS')")
	@RequestMapping(value = { "/removeCandidateCertificateBycandidateIdandcandidateCertificateId",
			"rest/removeCandidateCertificateBycandidateIdandcandidateCertificateId" }, method = RequestMethod.POST)
	public Response<String> removeCandidateCertificateBycandidateIdandcandidateCertificateId(
			CandidateCertificationDTO candidateCertificationDTO) throws Exception {
		Response<String> response = new Response<>();
		try {
			User user = userService.getUserById(candidateCertificationDTO.getLoginUserId());
			if (user != null) {
				OrgLocations orgLocations = orgLocationService.getOrgLocations(candidateCertificationDTO.getOrgLocationId());
				Candidate candidate = candidateService.getCandidateById(Long.parseLong(candidateCertificationDTO.getCandidateId()));
				CandidateCertifications candidatesCertificate = candidateCertificationsService
						.removeCandidateCertificateBycandidateIdandcandidateCertificateId(
								Long.parseLong(candidateCertificationDTO.getCandidateId()),
								candidateCertificationDTO.getCandidateCertificationId(), user, orgLocations);
				if (null != candidate && null != candidate.getId() && candidatesCertificate != null) {
					CandidateIndex candidateIndex = candidateIndexHelper.getCandidateIndexFromCandidate(candidate);
					candidateIndex.setCandidateId(candidate.getId());
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					candidateIndex.setUpdatedDate(dateFormat.format(new Date()));
					sourceleadELKBaseIndexer.saveOrUpdateElkDocument(candidateIndex, Constants.ELK_CANDIDATE_INDEX,
							Constants.ELK_CANDIDATE_TYPE, candidate.getId().toString());
				} else if (candidatesCertificate == null) {
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				} else {
					response.setResponse(Result.INVALID_USER.name());
					response.setResponseMessage(Result.INVALID_USER.getValue());
				}
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
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

	/**
	 * This Method is to remove CandidateEmployer By candidateId and
	 * CandidateEmployerID
	 * 
	 * @author Sammeta David Raju
	 * 
	 * @param candidateemployersId
	 * @param candidateId
	 * @param request
	 * 
	 * @return String
	 * 
	 * @throws Exception
	 */
	// @PreAuthorize("hasAnyRole('ROLE_VIEW_EDIT_CANDIDATE_CERTIFICATION','ROLE_CROWDSOURCING_PROFILE','ROLE_CROWDSOURCING_CANDIDATES','ROLE_VIEW_EDIT_ASSOCIATE_CERTIFICATION_DETAILS')")
	@RequestMapping(value = "/getCandidateCertificateBycandidateIdandCertificateId")
	public Response<CandidateCertifications> getCandidateCertificateBycandidateIdandCertificateId(
			@RequestParam(value = "candidateId", required = false) Long candidateId,
			@RequestParam(value = "candidateCertificationId", required = false) Long candidateCertificationId, HttpServletRequest request)
			throws Exception {
		Response<CandidateCertifications> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				if (candidateId > 0 && candidateCertificationId > 0) {
					Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);
					OrgLocations orgLocations = orgLocationService.getOrgLocations(candidate.getOrgLocationId().getId());
					CandidateCertifications candidateCertificationsList = candidateCertificationsService
							.getCandidateCertificateBycandidateIdandCertificateId(candidateId, candidateCertificationId);
					if (candidateCertificationsList.getValidFrom() != null) {
						candidateCertificationsList.setCandidatevalidFromDateToString(commonUtil
								.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), candidateCertificationsList.getValidFrom()));
					}
					if (candidateCertificationsList.getValidTo() != null) {
						candidateCertificationsList.setCandidatevalidToDateToString(commonUtil
								.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), candidateCertificationsList.getValidTo()));
					}
					response.setObject(candidateCertificationsList);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
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

	/**
	 * Handles CandidateCartifications update
	 * 
	 * @author Ajith Matta
	 * 
	 * @param json
	 * 
	 * @param request
	 * 
	 * @return ResponseBody
	 * 
	 * @throws Exception
	 */

	/** @author: Mahaveer Manoj, This method is used for Android application **/
	@RequestMapping(value = "rest/updateCandidateCertificationsBycandidateIdandcandidateCertificateId", method = RequestMethod.POST)
	public @ResponseBody Response<String> updateCandidateCertificationsBycandidateIdandcandidateCertificateIdForAndroid(
			@RequestBody CandidateCertificationDTO candidateCertificationDTO, HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			response = updateCandidateCertificationsBycandidateIdandcandidateCertificateId(candidateCertificationDTO, request);
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	@RequestMapping(value = "/updateCandidateCertificationsBycandidateIdandcandidateCertificateId", method = RequestMethod.POST)
	public @ResponseBody Response<String> updateCandidateCertificationsBycandidateIdandcandidateCertificateId(
			CandidateCertificationDTO candidateCertificationDTO, HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			User users = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (users != null && Validator.validate(candidateCertificationDTO)) {
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				CandidateCertifications candidateCertifications = candidateCertificationsService.storeCandidateCertifications(
						candidateCertificationDTO, users, loginOrgLocation, Long.parseLong(candidateCertificationDTO.getCandidateId()));
				if (candidateCertifications != null) {
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				}
			} else if (users == null) {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			} else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

}