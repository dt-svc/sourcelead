package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

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
import com.bis.sourcelead.dto.CandidateEducationDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.index.helper.CandidateIndexHelper;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateGlobal;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.CandidateEducationService;
import com.bis.sourcelead.service.CandidateGlobalService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CandidateGlobalUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

/**
 * Handles requests for the CandidateEducation Service
 * 
 * @author Ajith Matta
 * @date 30th Aug 2016
 *
 */

@RestController
public class CandidateEducationRestController {

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Autowired
	private CandidateEducationService candidateEducationService;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	LookUpDataService lookUpDataService;

	@Autowired
	private CandidateService candidateService;

	@Autowired
	UserService userService;

	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	CandidateIndexHelper candidateIndexHelper;

	@Autowired
	private SourceleadELKBaseIndexer<CandidateIndex> sourceleadELKBaseIndexer;
	
	@Autowired
	CandidateGlobalService candidateGlobalService;
	
	@Autowired
	CandidateGlobalUtil candidateGlobalUtil;

	private static final Logger logger = LoggerFactory.getLogger(CandidateEducationRestController.class);

	/**
	 * @author Ajith Matta
	 * 
	 * @since aug 26 2016
	 * 
	 * @version 0.0.1
	 * 
	 *          This method is used to save the candidate Employers. Candidate
	 *          employers details saved by json
	 * 
	 * @param This
	 *            is the first parameter of candidate employers
	 * 
	 * @return string
	 * 
	 * @throws NullPointer
	 */

	/** @author: Mahaveer Manoj, This method is used for Android application **/
	@RequestMapping(value = "/rest/storeCandidateEducation", method = RequestMethod.POST)
	@ResponseBody
	public Response<Integer> storeCandidateEducation(@RequestBody CandidateEducationDTO candidateEducationDTO,
			HttpServletRequest request) throws Exception {
		Response<Integer> response = new Response<>();
		try {
			response = storeCandidateEducation(candidateEducationDTO, candidateEducationDTO.getCandidateId() + "",
					request);
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	@RequestMapping(value = "/storeCandidateEducation", method = RequestMethod.POST)
	@ResponseBody
	public Response<Integer> storeCandidateEducation(CandidateEducationDTO candidateEducationDTO,
			@RequestParam(value = "candidateId", required = false) String candidateId, HttpServletRequest request)
			throws Exception {
		Response<Integer> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null && Validator.validate(candidateEducationDTO)) {
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
						"orgLocations");
				candidateEducationDTO.setDateFormat(loginOrgLocation.getDateFormat());
				candidateEducationService.storeCandidateEducation(candidateEducationDTO, Long.parseLong(candidateId),
						loginOrgLocation.getId(), user);
				CandidateGlobal candidateGlobal = candidateGlobalService.getCandidateGlobalByCandidateId(Long.parseLong(candidateId));
				Integer percentage = candidateGlobalUtil.getCandidatePercentage(candidateGlobal, loginOrgLocation);
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
	 * @param request
	 * 
	 * @return CandidateEmployers list
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getCandidateEducationListByCandidateId",
			"/rest/getCandidateEducationListByCandidateId" }, method = RequestMethod.GET)
	public ResponseList<CandidateEducationDTO> getCandidateEducationListByCandidateId(
			@RequestParam(defaultValue = "0") long candidateId, @RequestParam(value = "candidateType", required = false) String candidateType,HttpServletRequest request) throws Exception {
		ResponseList<CandidateEducationDTO> reponseList = new ResponseList<>();
		try {
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
					"orgLocations");
			if (loginOrgLocation != null) {
				if (candidateId > 0) {
					List<CandidateEducation> candidatesEducationList = null;
					
					candidatesEducationList = candidateEducationService
							.getCandidateEducationListByCandidateIdAndOrgLocationID(candidateId,
									loginOrgLocation.getId());
					
					List<CandidateEducationDTO> eduList = new ArrayList<>();
					for (CandidateEducation candidateEducation : candidatesEducationList) {
						CandidateEducationDTO edu = new CandidateEducationDTO();
						edu.setName(candidateEducation.getName());
						edu.setSpecialization(candidateEducation.getSpecialization());
						edu.setStartDateStr(candidateEducation.getStartDateStr());
						edu.setEndDateStr(candidateEducation.getEndDateStr());
						edu.setGrade(candidateEducation.getGrade());
						edu.setGradeType(candidateEducation.getGradeType());
						edu.setInstitute(candidateEducation.getInstitute());
						edu.setAddress(candidateEducation.getAddress());
						edu.setId(candidateEducation.getId());
						edu.setCandidateId(candidateEducation.getCandidateId().getId());
						
						edu.setCandidateEduId(candidateEducation.getId().toString());
						edu.setStartDate(candidateEducation.getStartDateStr());
						edu.setEndDate(candidateEducation.getEndDateStr());
						edu.setCandidateEducationId(candidateEducation.getId());
						eduList.add(edu);
					}
					reponseList.setData(eduList);
					reponseList.setResponse(Result.SUCCESS.name());
					reponseList.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					reponseList.setResponse(Result.INVALID_USER.name());
					reponseList.setResponseMessage(Result.INVALID_USER.getValue());
				}
			} else {
				reponseList.setResponse(Result.SESSION_TIMEDOUT.name());
				reponseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
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

	// @PreAuthorize("hasAnyRole('ROLE_DELETE_CANDIDATE_EDUCATION','ROLE_CROWDSOURCING_PROFILE','ROLE_CROWDSOURCING_CANDIDATES','ROLE_VIEW_DELETE_ASSOCIATE_EDUCATION_DETAILS')")
	@RequestMapping(value = { "/removeCandidateEducationBycandidateIdandcandidateEducationId",
			"rest/removeCandidateEducationBycandidateIdandcandidateEducationId" })
	public Response<String> removeCandidateEducationBycandidateIdandcandidateEducationId(
			CandidateEducationDTO candidateEducationDTO) throws Exception {
		Response<String> response = new Response<>();
		try {
			User user = userService.getUserById(candidateEducationDTO.getLoginUserId());
			if (user != null) {
				OrgLocations orgLocations = orgLocationService
						.getOrgLocations(candidateEducationDTO.getOrgLocationId());
				CandidateEducation candidateEducation = candidateEducationService
						.removeCandidateGlobalEducationBycandidateIdandcandidateEducationId(
								candidateEducationDTO.getCandidateId(), candidateEducationDTO.getCandidateEducationId(),
								user, orgLocations);
				
				CandidateGlobal candidate = candidateGlobalService
						.getCandidateGlobalByCandidateId(candidateEducationDTO.getCandidateId());
				if (null != candidate && null != candidate.getId()) {
					Integer percentage = candidateGlobalUtil.getCandidatePercentage(candidate, orgLocations);
					response.setObject(percentage.toString());
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

	// @PreAuthorize("hasAnyRole('ROLE_VIEW_EDIT_CANDIDATE_EDUCATION','ROLE_CROWDSOURCING_PROFILE','ROLE_CROWDSOURCING_CANDIDATES','ROLE_VIEW_EDIT_ASSOCIATE_EDUCATION_DETAILS')")
	@RequestMapping(value = { "/getCandidateEducationBycandidateIdandEducationId",
			"/rest/getCandidateEducationBycandidateIdandEducationId" }, method = RequestMethod.POST)
	public Response<CandidateEducationDTO> getCandidateEducationBycandidateIdandEducationId(
			@RequestParam(value = "candidateId", required = false, defaultValue = "0") Long candidateId,
			@RequestParam(value = "candidateEducationId", required = false, defaultValue = "0") Long candidateEducationId,
			@RequestParam(value = "candidateType", required = false) String candidateType,
			HttpServletRequest request) throws Exception {
		Response<CandidateEducationDTO> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (user != null && candidateId > 0 && candidateEducationId > 0) {
				CandidateEducationDTO candEduDTO = new CandidateEducationDTO();
				CandidateEducation candidatesEducationList = null;
						
				if(StringUtils.isNotBlank(candidateType) && candidateType.equalsIgnoreCase(Constants.CANDIDATE_GLOBAL)) {
					 candidatesEducationList = candidateEducationService
								.getCandidateGlobalEducationBycandidateIdandEducationId(candidateId, candidateEducationId);
				}else {
					 candidatesEducationList = candidateEducationService
							.getCandidateEducationBycandidateIdandEducationId(candidateId, candidateEducationId);
				}
				

				if (candidatesEducationList.getName() != null) {
					candEduDTO.setName(candidatesEducationList.getName());
				}

				if (candidatesEducationList.getInstitute() != null) {
					candEduDTO.setInstitute(candidatesEducationList.getInstitute());
				}

				if (candidatesEducationList.getSpecialization() != null) {
					candEduDTO.setSpecialization(candidatesEducationList.getSpecialization());
				}

				if (candidatesEducationList.getAddress() != null) {
					candEduDTO.setAddress(candidatesEducationList.getAddress());
				}

				if (candidatesEducationList.getGrade() != null) {
					candEduDTO.setGrade(candidatesEducationList.getGrade());
				}

				if (candidatesEducationList.getGradeType() != null) {
					candEduDTO.setGradeType(candidatesEducationList.getGradeType());
				}

				if (candidatesEducationList.getAllAndCandidateEducations() != null) {
					candEduDTO.setAllAndCandidateEducations(candidatesEducationList.getAllAndCandidateEducations());
				}

				if (candidatesEducationList.getStartDate() != null) {
					candEduDTO.setStartDateStr(commonUtil.dateStrFromOrgDateFormatter(
							orgLocations.getDateFormat(), candidatesEducationList.getStartDate()));
				}
				if (candidatesEducationList.getEndDate() != null) {
					candEduDTO.setEndDateStr(commonUtil.dateStrFromOrgDateFormatter(
							orgLocations.getDateFormat(), candidatesEducationList.getEndDate()));
				}

				LinkedHashSet<String> lhs = new LinkedHashSet<String>();
				List<LookUpData> educationsList = lookUpDataService.getLookUpDataByCategory(Constants.EDUCATIONS);
				for (LookUpData lookUpData : educationsList) {
					lhs.add(lookUpData.getName());
				}
				if (StringUtils.isNotEmpty(candidatesEducationList.getName())) {
					String[] cel = candidatesEducationList.getName().split(",");
					for (String el : cel) {
						lhs.add(el);
					}
				}
				candEduDTO.setAllAndCandidateEducations(lhs);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
				response.setObject(candEduDTO);
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

	/** @author: Mahaveer Manoj, This method is used for Android application **/
	@RequestMapping(value = "rest/upDateCandidateEducationByCandidateIdAndCandidateEducationId", method = RequestMethod.POST)
	@ResponseBody
	public Response<Boolean> upDateCandidateEducationByCandidateIdAndCandidateEducationIdForAndroid(
			@RequestBody CandidateEducationDTO candidateEducationDTO, HttpServletRequest request) throws Exception {
		Response<Boolean> response = new Response<>();
		try {
			response = upDateCandidateEducationByCandidateIdAndCandidateEducationId(candidateEducationDTO, request);
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	@RequestMapping(value = "/upDateCandidateEducationByCandidateIdAndCandidateEducationId", method = RequestMethod.POST)
	@ResponseBody
	public Response<Boolean> upDateCandidateEducationByCandidateIdAndCandidateEducationId(
			CandidateEducationDTO candidateEducationDTO, HttpServletRequest request) throws Exception {
		Response<Boolean> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null && Validator.validate(candidateEducationDTO)) {
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
						"orgLocations");
				candidateEducationDTO.setDateFormat(loginOrgLocation.getDateFormat());
				CandidateEducation candidateEdu = candidateEducationService.storeCandidateEducation(
						candidateEducationDTO, candidateEducationDTO.getCandidateId(), loginOrgLocation.getId(), user);
				if (candidateEdu != null) {
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

	@RequestMapping(value = {"/validateEduname","/rest/validateEduname"})
	public Response<Boolean> validateEduname(@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "candidateId", required = false) Long candidateId, 
			@RequestParam(value = "candidateType", required = false) String candidateType,HttpServletRequest request)
			throws Exception {
		Response<Boolean> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null && candidateId > 0) {
				String candidateEducation = name;
				Boolean canEdu = false;
				if(StringUtils.isNotBlank(candidateType) && candidateType.equalsIgnoreCase(Constants.CANDIDATE_GLOBAL)) {
					canEdu = candidateEducationService.validateCandidateGlobalEducationName(candidateId, candidateEducation);
				}else {
					canEdu = candidateEducationService.validateEducationName(candidateId, candidateEducation);
				}
				
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
				response.setObject(canEdu);
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

	@RequestMapping(value = { "/rest/getAllEducations" }, method = RequestMethod.POST)
	@ResponseBody
	public ResponseList<LookUpData> getAllEducationList(HttpServletRequest request) throws Exception {
		ResponseList<LookUpData> reponseList = new ResponseList<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				List<LookUpData> lookupDataList = lookUpDataService.getLookUpDataByCategory(Constants.EDUCATIONS);
				reponseList.setData(lookupDataList);
				reponseList.setResponse(Result.SUCCESS.name());
				reponseList.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				reponseList.setResponse(Result.SESSION_TIMEDOUT.name());
				reponseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			reponseList.setResponse(Result.AWKWARD.name());
			reponseList.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return reponseList;
	}
	
	@RequestMapping(value = "/rest/candidateEducation", method = RequestMethod.GET)
	@ResponseBody
	public ResponseList<LookUpData> candidateEducation(HttpServletRequest request) throws Exception {
	ResponseList<LookUpData> response = new ResponseList<>();
	List<LookUpData> educationsList = lookUpDataService.getLookUpDataByCategory(Constants.EDUCATIONS);
	response.setData(educationsList);
	return response;
	}

}
