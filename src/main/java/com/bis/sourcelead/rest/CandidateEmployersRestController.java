package com.bis.sourcelead.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.CandidateEmployerDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.CandidateEmployersService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CandidateActivityCommonUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

/**
 * Handles requests for the CandidateCandidateEmployers Service.
 * 
 * @author Ajith Matta
 * @date 30th June 2016
 *
 */

@RestController
@RequestMapping("/app")
public class CandidateEmployersRestController {

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Autowired
	private CandidateEmployersService candidateEmployersService;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private CandidateService candidateService;

	@Autowired
	private UserService userService;

	@Autowired
	private OrgLocationService orgLocationService;

	@Autowired
	private CandidateActivityCommonUtil candidateActivityCommonUtil;

	@Autowired
	private SourceleadELKBaseIndexer<CandidateIndex> sourceleadELKBaseIndexer;

	private static final Logger logger = LoggerFactory.getLogger(CandidateEmployersRestController.class);

	/*------------Create a Candidate Employers details--------------------------------*/

	/**
	 * @author Ajith Matta
	 * 
	 * @since aug 26 2016
	 * 
	 * @version 0.0.1
	 * 
	 *          This method is used to save the candidate Employers.
	 * 
	 * @param json
	 *            This is the first parameter of storeCandidateEmployers
	 * @param candidateId
	 *            This is the second parameter of storeCandidateEmployers
	 * @param request
	 *            This is the third parameter of storeCandidateEmployers
	 * @return string
	 * 
	 * @throws NullPointer
	 */

	// @PreAuthorize("hasAnyRole('ROLE_ADD_EMPLOYEER','ROLE_CROWDSOURCING_PROFILE','ROLE_CROWDSOURCING_CANDIDATES','ROLE_ADD_ASSOCIATE_EMPLOYER')")
	@RequestMapping(value = { "/storeCandidateEmployers", "rest/storeCandidateEmployers" }, method = RequestMethod.POST)
	@ResponseBody
	public Response<Integer> storeCandidateEmployers(CandidateEmployerDTO candidateEmployerDTO,
			@RequestParam(value = "candidateId", required = false) Long candidateId, HttpServletRequest request) throws Exception {
		Response<Integer> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null && Validator.validate(candidateEmployerDTO)) {
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				candidateEmployersService.storeCandidateEmployers(candidateEmployerDTO, user, loginOrgLocation, candidateId);
				Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);
				Integer percentage = candidateService.getCandidatePercentage(candidate, loginOrgLocation);
				response.setObject(percentage);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
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
	 * This Method is for getting candidate employers
	 * 
	 * @author Ajith Matta
	 * 
	 * @param candidateemployersId
	 *            This is the first parameter of
	 *            getCandidatesEmployersListByCandidateIdAndOrgLocationID
	 * @param request
	 *            This is the second parameter of
	 *            getCandidatesEmployersListByCandidateIdAndOrgLocationID
	 * 
	 * @return CandidateEmployers list
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getCandidatesEmployersListByCandidateIdAndOrgLocationID",
			"rest/getCandidatesEmployersListByCandidateIdAndOrgLocationID" }, method = RequestMethod.POST)
	public ResponseList<CandidateEmployerDTO> getCandidatesEmployersListByCandidateIdAndOrgLocationID(HttpServletRequest request,
			CandidateEmployerDTO candidateEmployer) throws Exception {
		ResponseList<CandidateEmployerDTO> reponseList = new ResponseList<>();
		try {

			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

			Candidate employers = candidateService.getCandidateById(candidateEmployer.getCandidateId());
			if (employers != null) {
				Long loc = employers.getOrgLocationId().getId();

				List<CandidateEmployerDTO> candidateEmployeeDTOList = new ArrayList<>();
				List<CandidateEmployers> candidatesEmployersList = candidateEmployersService
						.getCandidatesEmployersListByCandidateIdAndOrgLocationID(candidateEmployer.getCandidateId(), loc);

				// candidatesEmployersList.forEach(candidaet->{
				for (CandidateEmployers candidateEmployers : candidatesEmployersList) {
					CandidateEmployerDTO candidateEmployerDTO = new CandidateEmployerDTO();

					if (StringUtils.isNotBlank(candidateEmployers.getStartMonth())) {
						String smonth = getMonthAsString(candidateEmployers.getStartMonth());
						candidateEmployers.setStartMonth(smonth);
					}

					if (StringUtils.isNotBlank(candidateEmployers.getEndMonth())) {
						String emonth = getMonthAsString(candidateEmployers.getEndMonth());
						candidateEmployers.setEndMonth(emonth);
					}
					candidateEmployerDTO.setId(candidateEmployers.getId());
					candidateEmployerDTO.setName(candidateEmployers.getName());
					candidateEmployerDTO.setDesignation(candidateEmployers.getDesignation());
					candidateEmployerDTO.setAddress(candidateEmployers.getAddress());
					candidateEmployerDTO.setStartMonth(candidateEmployers.getStartMonth());
					candidateEmployerDTO.setEndMonth(candidateEmployers.getEndMonth());
					candidateEmployerDTO.setStartYear(candidateEmployers.getStartYear());
					candidateEmployerDTO.setEndYear(candidateEmployers.getEndYear());
					candidateEmployerDTO.setCandidateId(candidateEmployerDTO.getCandidateId());
					candidateEmployerDTO.setCandidateEmployeeId(String.valueOf(candidateEmployers.getId()));
					candidateEmployerDTO.setCandidateExperianceId(String.valueOf(candidateEmployers.getId()));
					candidateEmployerDTO.setIsCurrentPosition(candidateEmployers.getIsCurrentPosition());
					candidateEmployerDTO.setOrgLocationId(String.valueOf(orgLocations.getId()));
					candidateEmployerDTO.setCandidateemployerId(String.valueOf(candidateEmployers.getId()));
					candidateEmployeeDTOList.add(candidateEmployerDTO);
				}
				// });
				reponseList.setData(candidateEmployeeDTOList);
				reponseList.setResponse(Result.SUCCESS.name());
				reponseList.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				reponseList.setResponse(Result.INVALID_DATA.name());
				reponseList.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} catch (Exception e) {
			reponseList.setResponse(Result.AWKWARD.name());
			reponseList.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return reponseList;
	}

	private String getMonthAsString(String month) {
		Pattern pt = Pattern.compile("[^a-zA-Z0-9]");
		Matcher match = pt.matcher(month);
		while (match.find()) {
			String s = match.group();
			month = month.replaceAll("[^a-zA-Z_]", "");
		}
		return month;

	}

	/**
	 * This Method is to remove CandidateEmployer By candidateId and
	 * CandidateEmployerID
	 * 
	 * @author Sammeta David Raju
	 * 
	 * @param candidateemployersId
	 * @param candidateId
	 *            This is the first parameter of
	 *            removeCandidateEmployerBycandidateIdandCandidateEmployerID
	 * @param candidateemployerId
	 *            This is the second parameter of
	 *            removeCandidateEmployerBycandidateIdandCandidateEmployerID
	 * 
	 * @return String
	 * 
	 * @throws Exception
	 */

	// @PreAuthorize("hasAnyRole('ROLE_VIEW_DELETE_EMPLOYEER','ROLE_CROWDSOURCING_PROFILE','ROLE_CROWDSOURCING_CANDIDATES','ROLE_VIEW_DELETE_ASSOCIATE_EMPLOYER')")
	@RequestMapping(value = { "/removeCandidateEmployerBycandidateIdandCandidateEmployerID",
			"rest/removeCandidateEmployerBycandidateIdandCandidateEmployerID" })
	public Response<String> removeCandidateEmployerBycandidateIdandCandidateEmployerID(HttpServletRequest request,
			CandidateEmployerDTO candidateEmployerDTO) throws Exception {
		Response<String> response = new Response<>();
		try {
			// OrgLocations loginOrgLocation = (OrgLocations)
			// SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			User user = userService.getUserById(Long.parseLong(candidateEmployerDTO.getLoginUserId()));
			if (user != null) {
				OrgLocations orgLocations = orgLocationService.getOrgLocations(Long.parseLong(candidateEmployerDTO.getOrgLocationId()));
				StringBuffer activitySummary = new StringBuffer();
				CandidateEmployers candidatesEmployersList = candidateEmployersService
						.removeCandidateEmployerBycandidateIdandCandidateEmployerID(candidateEmployerDTO.getCandidateId(),
								Long.parseLong(candidateEmployerDTO.getCandidateemployerId()));
				Candidate candidate = candidateService.getCandidateById(candidateEmployerDTO.getCandidateId());

				activitySummary.append("Employer").append(" : ").append(Constants.NEW_VALUE_TAG_INITIATION)
						.append(candidatesEmployersList.getName()).append(Constants.NEW_VALUE_TAG_END);
				candidateActivityCommonUtil.storeCandidateEmployersHistory(candidatesEmployersList, user);
				candidateActivityCommonUtil.storeCandidateActivity(user, orgLocations, candidatesEmployersList.getCandidateId(), "",
						Constants.CANDIDATE_DELETED_EMPLOYER, activitySummary.toString(), "CANDIDATE");

				if (null != candidate && null != candidate.getId()) {
					CandidateIndex candidateIndex = new CandidateIndex();
					candidateIndex.setCandidateId(candidate.getId());
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					candidateIndex.setUpdatedDate(dateFormat.format(new Date()));
					sourceleadELKBaseIndexer.saveOrUpdateElkDocument(candidateIndex, Constants.ELK_CANDIDATE_INDEX,
							Constants.ELK_CANDIDATE_TYPE, candidate.getId().toString());
				}

				Integer percentage = candidateService.getCandidatePercentage(candidate, orgLocations);
				response.setObject(percentage.toString());
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
	 * This Method is to get CandidateEmployer By CandidateId And CandidateEmpoyerId
	 * 
	 * @author Sammeta David Raju
	 * 
	 * @param candidateemployersId
	 * @param candidateId
	 *            This is the first parameter of
	 *            getCandidateEmployerByCandidateIdAndCandidateEmpoyerId
	 * @param candidateemployerId
	 *            This is the second parameter of
	 *            getCandidateEmployerByCandidateIdAndCandidateEmpoyerId
	 * 
	 * @return String
	 * 
	 * @throws Exception
	 */
	// @PreAuthorize("hasAnyRole('ROLE_VIEW_EDIT_EMPLOYEER','ROLE_CROWDSOURCING_PROFILE','ROLE_CROWDSOURCING_CANDIDATES','ROLE_VIEW_EDIT_ASSOCIATE_EMPLOYER')")
	@RequestMapping(value = "/getCandidateEmployerByCandidateIdAndCandidateEmpoyerId")
	public Response<CandidateEmployers> getCandidateEmployerByCandidateIdAndCandidateEmpoyerId(
			@RequestParam(value = "candidateId", required = false) Long candidateId,
			@RequestParam(value = "candidateEmpId", required = false) Long candidateEmpId, HttpServletRequest request) throws Exception {
		Response<CandidateEmployers> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				CandidateEmployers candidatesEmployersList = candidateEmployersService
						.getCandidateEmployerBycandidateIdandCandidateEmployerID(candidateId, candidateEmpId);
				response.setObject(candidatesEmployersList);
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
	 * This Method is to get CandidateEmployer By CandidateId And CandidateEmpoyerId
	 * 
	 * @author Sammeta David Raju
	 * 
	 * @param candidateemployersId
	 * @param candidateId
	 *            This is the first parameter of
	 *            getCandidateEmployerByCandidateIdAndCandidateEmpoyerId
	 * @param candidateemployerId
	 *            This is the second parameter of
	 *            getCandidateEmployerByCandidateIdAndCandidateEmpoyerId
	 * 
	 * @return String
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = { "/upDateCandidateEmployerByCandidateIdAndCandidateExperianceId",
			"rest/upDateCandidateEmployerByCandidateIdAndCandidateExperianceId" }, method = RequestMethod.POST)
	public Response<Boolean> upDateCandidateEmployerByCandidateIdAndCandidateExperianceId(CandidateEmployerDTO candidateEmployerDTO,
			HttpServletRequest request) throws Exception {
		Response<Boolean> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null && Validator.validate(candidateEmployerDTO)) {
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				CandidateEmployers candidateEmp = candidateEmployersService.storeCandidateEmployers(candidateEmployerDTO, user,
						loginOrgLocation, candidateEmployerDTO.getCandidateId());
				if (candidateEmp != null) {
					response.setObject(true);
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

}
