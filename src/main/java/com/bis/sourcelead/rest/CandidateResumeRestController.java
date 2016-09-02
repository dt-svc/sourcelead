package com.bis.sourcelead.rest;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
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

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.UserService;
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
	
	@Autowired
	UserService userService;
	
	/**
	* @author Venkat Kesav 
	* 
	* Handles Resume saving for Candidate
	* 
	* @param json, candidateId
	* @param request
	* 
	* @return ResponseBody
	* 
	* @throws Exception
	*/
	@RequestMapping(value = "/saveCandidateResume", method = RequestMethod.POST)
	public @ResponseBody String saveCandidateResume(@RequestParam(value = "json", required = false) String json, String candidateId, MultipartHttpServletRequest request) throws Exception {

		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);
		
		MultipartFile multiFile = null;
		Iterator<String> itrator = request.getFileNames();
		if(itrator.hasNext()){
	        multiFile = request.getFile(itrator.next());
		}

		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		if(StringUtils.isNotEmpty(candidateId)){
			Candidate candidate = candidateService.getCandidateByCandidateId(Long.parseLong(candidateId));
			
			candidateResumesService.storeCandidateResume(jsonObj, user, candidate, orgLocation, multiFile);

			SourceLeadSessionManager.setSessionAttribute(request, "resumeAddMessage",
					"Successfully you have added");

			return "success";
		}else{
			return "exception";
		}
	}
	
	
	/**
	* @author Venkat Kesav 
	* 
	* This method is used to get candidate resumes list
	* 
	* @param candidateId
	* 
	* @return resultJson
	* 
	* @throws Exception
	*/
	@RequestMapping(value = "/candidateResumesListByCandidateId", method = RequestMethod.POST)
	public @ResponseBody String candidateResumesListByCandidateId(String candidateId) throws Exception {

		String resultJson = null;
		String status = Constants.CANDIDATE_ACTIVE;
		if(candidateId != null){
			List<CandidateResumes> candidateResumesList = candidateResumesService.getCandidateResumesListByCandidateIdAndStatus(Long.parseLong(candidateId), status);
			for(CandidateResumes candidateResume : candidateResumesList){
				User createdUser = userService.getUserByUserCode(candidateResume.getCreatedBy());
				if(createdUser != null){
					candidateResume.setCreatedBy(createdUser.getFirstName()+" "+createdUser.getLastName());
				}
			}
			resultJson = commonUtil.toJSON(candidateResumesList);
		}
		return resultJson;
	}
	
	
	/**
	* @author Venkat Kesav 
	* 
	* This method is used to update Resume
	* 
	* @param resumeId
	* @param request
	* 
	* @return resultJson
	* 
	* @throws Exception
	*/
	@RequestMapping(value = "/terminateResume", method = RequestMethod.POST)
	public @ResponseBody String terminateResume(String resumeId, HttpServletRequest request) throws Exception {
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		
		CandidateResumes candidateResume =  candidateResumesService.getCandidateResumeByResumeId(Long.parseLong(resumeId));
		if(candidateResume != null){
			candidateResume.setStatus(Constants.MEMBER_IN_ACTIVE);
			candidateResume.setUpdatedBy(user.getUserCode());
			candidateResume.setUpdatedDate(commonUtil.currentDate());
			candidateResumesService.updateCandidateResume(candidateResume);
		}
		return "success";
	}
	
	
	/**
	* @author Venkat Kesav 
	* 
	* This method is used to download Resume
	* 
	* @param resumeId
	* @param request
	* 
	* @return resultJson
	* 
	* @throws Exception
	*/
	@RequestMapping(value = "/downloadResume", method = RequestMethod.GET)
    public void downloadResume(@RequestParam(value="resumeId", required=false) String resumeId ,HttpServletResponse response) throws Exception {
		
			CandidateResumes candidateResume =  candidateResumesService.getCandidateResumeByResumeId(Long.parseLong(resumeId));
		    
		    response.setContentType("application/octet-stream");
	        response.setHeader("Content-Disposition",
	                "attachment;filename="+candidateResume.getFileName());
		    
			InputStream inputStream = new ByteArrayInputStream(candidateResume.getDocFile());
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