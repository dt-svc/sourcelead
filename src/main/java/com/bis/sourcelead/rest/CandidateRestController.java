package com.bis.sourcelead.rest;

import java.util.Iterator;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
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
	
	@Autowired
	private CandidateService candidateService;

	

	/*------------Create a Candidate--------------------------------*/
	
	
	/**
	 * Store Candidate
	 * 
	 * @param json
	 * @param request
	 * @return ResponseBody
	 * @throws Exception
	 */
	@RequestMapping(value = "/storeCandidate", method = RequestMethod.POST)
	public @ResponseBody String storeCandidate(@RequestParam(value = "json", required = false) String json,MultipartHttpServletRequest request) throws Exception {
		
		MultipartFile multiFile = null;
		Iterator<String> itrator = request.getFileNames();
		if(itrator.hasNext()){
	        multiFile = request.getFile(itrator.next());
		}
	    
		String jsonc = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(jsonc);
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Candidate candidate = candidateService.storeCandidate(jsonObj,orgLocation,user,multiFile);
		return "success";
	}
	
	 /**
	  * To  validate user email
	  * @param EmailId
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/validateCandidateEmail" , method = RequestMethod.POST)
	 public @ResponseBody  String validateCandidateEmail(@RequestParam(value="email", required=false) String email) throws Exception {
		 	
		 	String result = null;
		 	Candidate candidate = candidateService.findByCandidateEmail(email);
		 	
		 	if (candidate != null) {
				result = "true";
			}else{
				result = "false";
			}
			return result;
	   }
}
