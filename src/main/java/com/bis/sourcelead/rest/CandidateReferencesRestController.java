package com.bis.sourcelead.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.CandidateReferencesDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.index.helper.CandidateIndexHelper;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateReferences;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.CandidateReferencesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CandidateActivityCommonUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

/**
 * Handles requests for the CandidateReferences Service
 * 
 * @author Prasad K
 *
 */
@RestController
public class CandidateReferencesRestController {
	private static final Logger logger = LoggerFactory.getLogger(CandidateReferencesRestController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	@Autowired
	private CandidateReferencesService candidateReferencesService;
	@Autowired
	private CommonUtil commonUtil;
	@Autowired
	private UserService userService;
	@Autowired
	private OrgLocationService orgLocationService;
	@Autowired
	CandidateActivityCommonUtil candidateActivityCommonUtil;

	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	CandidateIndexHelper candidateIndexHelper;

	@Autowired
	private SourceleadELKBaseIndexer<CandidateIndex> sourceleadELKBaseIndexer;

	/**
	 * @author Prasad K
	 * 
	 *         Handles References saving for Candidate
	 * 
	 * @param json,
	 *            candidateId
	 * @param request
	 * 
	 * @return ResponseBody
	 * 
	 * @throws Exception
	 */

	/** @author: Mahaveer Manoj, This method is used for Android application **/
	@RequestMapping(value = "/rest/saveCandidateReferences", method = RequestMethod.POST)
	public @ResponseBody Response<String> saveCandidateReferences(@RequestBody CandidateReferencesDTO candidateReferencesDTO)
			throws Exception {
		Response<String> response = new Response<>();
		try {
			response = saveCandidateReferences(candidateReferencesDTO, candidateReferencesDTO.getCandidateId(),
					candidateReferencesDTO.getUserId(), candidateReferencesDTO.getLocationCode());
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	// @PreAuthorize("hasAnyRole('ROLE_ADD_CANDIDATE_REFERENCE','ROLE_CROWDSOURCING_PROFILE','ROLE_CROWDSOURCING_CANDIDATES','ROLE_ADD_ASSOCIATE_REFERENCE')")
	@RequestMapping(value = "/saveCandidateReferences", method = RequestMethod.POST)
	public @ResponseBody Response<String> saveCandidateReferences(CandidateReferencesDTO candidateReferencesDTO,
			@RequestParam("candidateId") Long candidateId, @RequestParam("userId") Long userId,
			@RequestParam("locationCode") String locationCode) throws Exception {
		Response<String> response = new Response<>();
		try {
			User user = userService.getUserById(userId);
			if (user != null && Validator.validate(candidateReferencesDTO)) {
				OrgLocations orgLocation = orgLocationService.getOrgLocationsByLocationCode(locationCode);
				if (null != candidateId) {
					candidateReferencesService.storeCandidateReferences(candidateReferencesDTO, candidateId, orgLocation.getId(), user);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					response.setResponse(Result.INVALID_USER.name());
					response.setResponseMessage(Result.INVALID_USER.getValue());
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

	@RequestMapping(value = {"/validateReferEmail","/rest/validateReferEmail"}, method = RequestMethod.POST)
	public @ResponseBody Response<Boolean> validateReferEmail(@RequestParam("candidateId") Long candidateId,
			@RequestParam("referId") Long referId, @RequestParam("email") String email, @RequestParam("candidateType") String candidateType) throws Exception {
		Response<Boolean> response = new Response<>();
		try {
			CandidateReferences cr = null;
			if (referId != null) {
				cr = candidateReferencesService.validateReferenceEmailByReferEmailAndReferId(email, referId);
			}
			Boolean flag = false;
			if (null == cr) {
				if(StringUtils.isNotBlank(candidateType) && candidateType.equalsIgnoreCase(Constants.CANDIDATE_GLOBAL)) {
					flag = candidateReferencesService.validateReferenceEmailByReferEmailAndCandidateGlobalId(email, candidateId);
				}else {
					flag = candidateReferencesService.validateReferenceEmailByReferEmailAndCandidateId(email, candidateId);
				}
				
			}
			response.setObject(flag);
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
	 * This Method is for getting candidate references
	 * 
	 * @author PrasadK
	 * 
	 * @param candidateId
	 * @param request
	 * 
	 * @return CandidateReferences list
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getCandidateReferencesListByCandidateId",
			"rest/getCandidateReferencesListByCandidateId" }, method = RequestMethod.POST)
	public ResponseList<CandidateReferencesDTO> getCandidateReferencesListByCandidateId(
			@RequestParam(value = "candidateId", required = false) String candidateId, @RequestParam("locationCode") String locationCode, 
			@RequestParam(value = "candidateType", required = false) String candidateType, HttpServletRequest request)
			throws Exception {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = orgLocationService.getOrgLocationsByLocationCode(locationCode);
		ResponseList<CandidateReferencesDTO> candidateReferencesList =  null;
		
		if(StringUtils.isNotBlank(candidateType) && candidateType.equalsIgnoreCase(Constants.CANDIDATE_GLOBAL)) {
			candidateReferencesList = candidateReferencesService
					.getCandidateReferencesListByCandidateGlobalIdAndOrgLocationID(Long.parseLong(candidateId), orgLocation.getId(),user.getUserCode());
		}else {
			candidateReferencesList = candidateReferencesService
					.getCandidateReferencesListByCandidateIdAndOrgLocationID(Long.parseLong(candidateId), orgLocation.getId(),user.getUserCode());
		}
		 
		return candidateReferencesList;
	}

	/**
	 * @author Prasad K
	 * 
	 *         This method is used to remove Reference
	 * 
	 * @param documentId
	 * @param request
	 * 
	 * @return resultJson
	 * 
	 * @throws Exception
	 */
	// @PreAuthorize("hasAnyRole('ROLE_VIEW_AND_EDIT_CANDIDATE_REFERENCE','ROLE_CROWDSOURCING_PROFILE','ROLE_CROWDSOURCING_CANDIDATES','ROLE_VIEW_AND_EDIT_ASSOCIATE_REFERENCE')")
	@RequestMapping(value = { "/terminateReference", "rest/terminateReference" }, method = RequestMethod.POST)
	public @ResponseBody Response<String> terminateReference(CandidateReferencesDTO candidateReferencesDTO) throws Exception {
		Response<String> response = new Response<>();
		try {
			User user = userService.getUserById(candidateReferencesDTO.getLoginUserId());
			if (user != null) {
				OrgLocations loginOrgLocation = orgLocationService.getOrgLocations(candidateReferencesDTO.getOrgLocationId());
				CandidateReferences candidateReferences = candidateReferencesService
						.getCandidateReferenceByReferenceId(Long.parseLong(candidateReferencesDTO.getRefId()));
				if (candidateReferences != null) {
					candidateReferences.setStatus(Constants.CANDIDATE_IN_ACTIVE);
					candidateReferences.setUpdatedBy(candidateReferencesDTO.getUserCode());
					candidateReferences.setUpdatedDate(commonUtil.currentDate());
					candidateReferencesService.updateCandidateReferences(candidateReferences);
					StringBuffer activitySummary = new StringBuffer();
					activitySummary.append("Name").append(" : ").append(Constants.NEW_VALUE_TAG_INITIATION)
							.append(candidateReferences.getName()).append(Constants.NEW_VALUE_TAG_END);
					candidateActivityCommonUtil.storeCandidateReferencesHistory(candidateReferences, user);
					candidateActivityCommonUtil.storeCandidateActivity(user, loginOrgLocation, candidateReferences.getCandidateId(), "",
							Constants.CANDIDATE_DELETED_REFERENCES, activitySummary.toString(), "CANDIDATE");
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
	 * Handles CandidateReference update
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
	@RequestMapping(value = "rest/updateCandidateReferenceBycandidateIdAndReferenceId", method = RequestMethod.POST)
	public @ResponseBody Response<String> updateCandidateReferenceBycandidateIdAndReferenceIdforAndroid(
			@RequestBody CandidateReferencesDTO candidateReferencesDTO) throws Exception {
		Response<String> response = new Response<>();
		try {
			response = updateCandidateReferenceBycandidateIdAndReferenceId(candidateReferencesDTO);
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	@RequestMapping(value = "/updateCandidateReferenceBycandidateIdAndReferenceId", method = RequestMethod.POST)
	public @ResponseBody Response<String> updateCandidateReferenceBycandidateIdAndReferenceId(CandidateReferencesDTO candidateReferencesDTO)
			throws Exception {
		Response<String> response = new Response<>();
		try {
			User user = userService.getUserById(candidateReferencesDTO.getUserId());
			if (user != null && Validator.validate(candidateReferencesDTO)) {
				OrgLocations orgLocation = orgLocationService.getOrgLocationsByLocationCode(candidateReferencesDTO.getLocationCode());
				CandidateReferences candidateReferences = candidateReferencesService.storeCandidateReferences(candidateReferencesDTO,
						candidateReferencesDTO.getCandidateId(), orgLocation.getId(), user);
				if (candidateReferences != null) {
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					response.setResponse(Result.INVALID_USER.name());
					response.setResponseMessage(Result.INVALID_USER.getValue());
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
	@RequestMapping(value = "/getCandidateReferencesByCandidateIdAndReferenceId")
	public Response<CandidateReferences> getCandidateReferencesByCandidateIdAndReferenceId(
			@RequestParam(value = "refId", required = false) Long refId) throws Exception {
		Response<CandidateReferences> response = new Response<>();
		try {
			CandidateReferences candidateReferencesList = candidateReferencesService
					.getCandidateReferenceByReferenceId(refId);
			response.setObject(candidateReferencesList);
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	// to validate Project SequeneNumber
	@RequestMapping(value = "/validateReferenceEmailByCadidateId", method = RequestMethod.POST)
	public @ResponseBody Boolean validateProjectSequeneNumber(@RequestParam(value = "email", required = false) String email,
			@RequestParam("candidateId") Long candidateId,
			@RequestParam(value = "candidateReferenceId", required = false) Long candidateReferenceId) throws Exception {
		return candidateReferencesService.validateReferenceEmail(email, candidateId, candidateReferenceId);
	}

}
