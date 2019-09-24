package com.bis.sourcelead.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.dto.CandidateCommentsDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateComments;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.CandidateCommentsService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@PropertySource("${propertyLocation:app.properties}")
@RequestMapping("/app")
/**
 * 
 * @author root Handles client requests
 */
public class CandidateCommentsRestController {

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Autowired
	CandidateService candidateService;

	@Autowired
	CandidateCommentsService candidateCommentsService;

	@Autowired
	CommonUtil commonUtil;

	@Autowired
	UserService userService;

	private static final Logger logger = LoggerFactory.getLogger(CandidateCommentsRestController.class);

	/**
	 * @author Venkat Kesav
	 * 
	 *         Handles Comment saving for Candidate
	 * 
	 * @param json,
	 *            candidateId
	 * @param request
	 * 
	 * @return ResponseBody
	 * 
	 * @throws Exception
	 */

	@PreAuthorize("hasAnyRole('ROLE_POST_COMMENTS','ROLE_CROWDSOURCING_CANDIDATES')")
	@RequestMapping(value = "/saveCandidateComment", method = RequestMethod.POST)
	public @ResponseBody Response<String> saveCandidateComment(CandidateCommentsDTO candidateCommentsDTO,
			String candidateId, String submissionId, HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				
				if (StringUtils.isNotEmpty(candidateId)) {
					Candidate candidate = null;
					if (StringUtils.isNumeric(candidateId)) {
						candidate = new Candidate();
						candidate.setId(Long.parseLong(candidateId));
					}
					RequirementSubmission submission = null;
					if (StringUtils.isNotEmpty(submissionId) && StringUtils.isNumeric(submissionId)) {
						submission = new RequirementSubmission();
						submission.setId(Long.parseLong(submissionId));
					}
					if (Validator.validate(candidateCommentsDTO)) {
					candidateCommentsService.storeCandidateComments(candidateCommentsDTO, user, candidate.getId(), orgLocation, submission.getId());
					SourceLeadSessionManager.setSessionAttribute(request, "resumeAddMessage", "Successfully you have added");
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				    }else {
						response.setResponse(Result.INVALID_DATA.name());
						response.setResponseMessage(Result.INVALID_DATA.getValue());
					}
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
	 * @author Venkat Kesav
	 * 
	 *         Handles Comment list for Candidate
	 * 
	 * @param candidateId
	 * 
	 * @param request
	 * 
	 * @return ResponseBody
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAllCommentsOnCandidateByCandidateIdAndOrgLocation", method = RequestMethod.POST)
	public @ResponseBody ResponseList<CandidateComments> getAllCommentsOnCandidateByCandidateIdAndOrgLocation(String candidateId,
			HttpServletRequest request) throws Exception {
		ResponseList<CandidateComments> response = new ResponseList<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				if (StringUtils.isNotEmpty(candidateId)) {
					List<CandidateComments> candidateCommentsList = candidateCommentsService
							.getAllCommentsOnCandidateByCandidateIdAndOrgLocation(Long.parseLong(candidateId), orgLocation);
					response.setData(candidateCommentsList);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					response.setResponse(Result.INVALID_USER.name());
					response.setResponseMessage(Result.INVALID_USER.getValue());
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
	 * @author Venkat Kesav
	 * 
	 *         Handles Comment list for Candidate
	 * 
	 * @param candidateId
	 * 
	 * @param request
	 * 
	 * @return ResponseBody
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAllCommentsOnCandidateByCandidateIdAndOrgLocationAndSubmissionId", method = RequestMethod.POST)
	public @ResponseBody ResponseList<CandidateComments> getAllCommentsOnCandidateByCandidateIdAndOrgLocationAndSubmissionId(String candidateId,
			String submissionId, HttpServletRequest request) throws Exception {
		ResponseList<CandidateComments> response = new ResponseList<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

				if (StringUtils.isNumeric(candidateId) && StringUtils.isNotEmpty(candidateId) && StringUtils.isNumeric(submissionId)
						&& StringUtils.isNotEmpty(submissionId)) {

					List<CandidateComments> candidateCommentsList = candidateCommentsService
							.getAllCommentsOnCandidateByCandidateIdAndOrgLocationAndSubmissionId(Long.parseLong(candidateId),
									Long.parseLong(submissionId), orgLocation);
					response.setData(candidateCommentsList);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else if (!StringUtils.isNumeric(submissionId) || !StringUtils.isNotEmpty(submissionId)) {
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				} else {
					response.setResponse(Result.INVALID_USER.name());
					response.setResponseMessage(Result.INVALID_USER.getValue());
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
}