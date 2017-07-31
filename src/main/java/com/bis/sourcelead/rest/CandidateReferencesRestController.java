package com.bis.sourcelead.rest;

import java.util.List;

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

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.CandidateReferences;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateReferencesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CandidateActivityCommonUtil;
import com.bis.sourcelead.util.CommonUtil;

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
	@PreAuthorize("hasAnyRole('ROLE_ADD_CANDIDATE_REFERENCE','ROLE_ADD_ASSOCIATE_REFERENCE')")
	@RequestMapping(value = "/saveCandidateReferences", method = RequestMethod.POST)
	public @ResponseBody String saveCandidateReferences(@RequestParam("json") String json, @RequestParam("candidateId") Long candidateId, @RequestParam("userId") Long userId, @RequestParam("locationCode") String locationCode) throws Exception
	{
		/* String json1 = "{" + json + "}"; */
		JSONObject jsonObj = new JSONObject(json);
		User user = userService.getUserById(userId);
		OrgLocations orgLocation = orgLocationService.getOrgLocationsByLocationCode(locationCode);
		if (null != candidateId) {
			candidateReferencesService.storeCandidateReferences(jsonObj, candidateId, orgLocation.getId(), user);
			return "success";
		}
		else {
			return "exception";
		}
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
	@RequestMapping(value = "/getCandidateReferencesListByCandidateId")
	public List<CandidateReferences> getCandidateReferencesListByCandidateId(@RequestParam(value = "candidateId", required = false) String candidateId, @RequestParam("locationCode") String locationCode) throws Exception
	{
		// OrgLocations loginOrgLocation = (OrgLocations)
			// SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		/* String resultJson; */
		OrgLocations orgLocation = orgLocationService.getOrgLocationsByLocationCode(locationCode);
		List<CandidateReferences> candidateReferencesList = candidateReferencesService.getCandidateReferencesListByCandidateIdAndOrgLocationID(Long.parseLong(candidateId), orgLocation.getId());
		/* resultJson = commonUtil.toJSON(candidateReferencesList); */
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
	@PreAuthorize("hasAnyRole('ROLE_VIEW_AND_EDIT_CANDIDATE_REFERENCE','ROLE_VIEW_AND_EDIT_ASSOCIATE_REFERENCE')")
	@RequestMapping(value = "/terminateReference", method = RequestMethod.POST)
	public @ResponseBody String terminateReference(@RequestParam(value = "loginUserId", required = false) Long loginUserId, @RequestParam(value = "orgLocationId", required = false) Long orgLocationId,
													@RequestParam(value = "candidateId", required = false) Long candidateId, @RequestParam(value = "refId", required = false) String refId, @RequestParam(value = "userCode", required = false) String userCode) throws Exception
	{
		// User user = (User)
			// SourceLeadSessionManager.getSessionAttribute(request, "user");
		User user = userService.getUserById(loginUserId);
		OrgLocations loginOrgLocation = orgLocationService.getOrgLocations(orgLocationId);
		CandidateReferences candidateReferences = candidateReferencesService.getCandidateReferenceByReferenceId(Long.parseLong(refId));
		if (candidateReferences != null) {
			candidateReferences.setStatus(Constants.CANDIDATE_IN_ACTIVE);
			candidateReferences.setUpdatedBy(userCode);
			candidateReferences.setUpdatedDate(commonUtil.currentDate());
			candidateReferencesService.updateCandidateReferences(candidateReferences);
			StringBuffer activitySummary = new StringBuffer();
		    activitySummary.append("Name").append(" : ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateReferences.getName()).append(Constants.NEW_VALUE_TAG_END);
			candidateActivityCommonUtil.storeCandidateReferencesHistory(candidateReferences, user);
			candidateActivityCommonUtil.storeCandidateActivity(user, loginOrgLocation, candidateReferences.getCandidateId(), "", Constants.CANDIDATE_DELETED_REFERENCES, activitySummary.toString(), "CANDIDATE");
		}
		return "success";
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
	@RequestMapping(value = "/updateCandidateReferenceBycandidateIdAndReferenceId", method = RequestMethod.POST)
	public @ResponseBody String updateCandidateReferenceBycandidateIdAndReferenceId(@RequestParam("json") String json, @RequestParam("candidateId") Long candidateId, @RequestParam("userId") Long userId, @RequestParam("locationCode") String locationCode) throws Exception
	{
		/*
		 * OrgLocations loginOrgLocation = (OrgLocations)
		 * SourceLeadSessionManager.getSessionAttribute(request,
		 * "orgLocations"); User users = (User)
		 * SourceLeadSessionManager.getSessionAttribute(request, "user");
		 */
		/* String json1 = "{"+json+"}"; */
		User user = userService.getUserById(userId);
		OrgLocations orgLocation = orgLocationService.getOrgLocationsByLocationCode(locationCode);
		JSONObject jsonObject = new JSONObject(json);
		candidateReferencesService.storeCandidateReferences(jsonObject, candidateId, orgLocation.getId(), user);
		return json;
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
	public String getCandidateReferencesByCandidateIdAndReferenceId(@RequestParam(value = "candidateId", required = false) Long candidateId, @RequestParam(value = "refId", required = false) Long refId) throws Exception
	{
		String resultJson;
		CandidateReferences candidateReferencesList = candidateReferencesService.getCandidateReferencesByCandidateIdAndReferenceId(candidateId, refId);
		resultJson = commonUtil.toJSON(candidateReferencesList);
		return resultJson;
	}
}
