package com.bis.sourcelead.rest;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@PropertySource("${propertyLocation:app.properties}")
/**
 * 
 * @author root
 * Handles client requests
 */
public class CandidateResumeRestController {

	private static final Logger logger = LoggerFactory.getLogger(CandidateResumeRestController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Autowired
	CandidateService candidateService;
	
	@Autowired
	CandidateResumesService candidateResumesService;
	
	@Autowired
	CommonUtil commonUtil;
	
	/**
	* @author Venkat Kesav 
	* Handles Resume saving for Candidate
	* @param json
	* @param request
	* @return ResponseBody
	* @throws Exception
	*/
	@RequestMapping(value = "/saveCandidateResume", method = RequestMethod.POST)
	public @ResponseBody String saveCandidateResume(@RequestParam(value = "json", required = false) String json, MultipartHttpServletRequest request) throws Exception {

		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);
		
		MultipartFile multiFile = null;
		Iterator<String> itrator = request.getFileNames();
		if(itrator.hasNext()){
	        multiFile = request.getFile(itrator.next());
		}

		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		//Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		Candidate candidate = null;
		
		candidateResumesService.storeCandidateResume(jsonObj, user, candidate, orgLocation, multiFile);

		SourceLeadSessionManager.setSessionAttribute(request, "resumeAddMessage",
				"Successfully you have added");

		return "success";
	}
	
	
	/**
	* @author Venkat Kesav 
	* This method is used to get candidate resumes list
	* @param candidateId
	* @param request
	* @return resultJson
	* @throws Exception
	*/
	@RequestMapping(value = "/candidateResumesListByCandidateId", method = RequestMethod.POST)
	public @ResponseBody String candidateResumesListByCandidateId(String candidateId) throws Exception {

		String resultJson;
		List<CandidateResumes> candidateResumesList = candidateResumesService.getCandidateResumesListByCandidateId(Long.parseLong(candidateId));
		resultJson = commonUtil.toJSON(candidateResumesList);
		return resultJson;
	}


	/**
	 * check given string empty or null
	 * @param str
	 * @return boolean
	 */
	protected static boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str.trim());
	}

}