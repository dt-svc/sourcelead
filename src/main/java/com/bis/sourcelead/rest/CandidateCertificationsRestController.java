package com.bis.sourcelead.rest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.service.CandidateCertificationsService;

/**
 * @author Shivayogi
 *
 */
@RestController
public class CandidateCertificationsRestController {
	
	@Autowired
	private CandidateCertificationsService candidateCertificationsService;
/*------------store a CandidateCartifications--------------------------------*/
	
	
	/**
	 * Handles CandidateCartifications  store
	 * 
	 * @param json
	 * @param request
	 * @return ResponseBody
	 * @throws Exception
	 */
	@RequestMapping(value = "/storeCandidateCertifications", method = RequestMethod.POST)
	public @ResponseBody String storeCandidateCertifications(@RequestParam(value = "json", required = false) String json) throws Exception {

		String jsonc = "{" + json + "}";
		JSONObject jsonobject = new JSONObject(jsonc);
		candidateCertificationsService.storeCandidateCertifications(jsonobject);
		return "candidates/candidatesCertificates";
	}

}
