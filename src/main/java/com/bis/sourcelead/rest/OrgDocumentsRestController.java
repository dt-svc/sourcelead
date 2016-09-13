package com.bis.sourcelead.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.service.OrgDocumentsService;

/**
 * 
 * @author Prasanna vadla
 *
 */

@RestController
public class OrgDocumentsRestController {
	
	
	@Autowired
	OrgDocumentsService orgDocumentsService;
	
	@RequestMapping(value = "/saveDocuments", method = RequestMethod.POST)
	@ResponseBody String saveDocuments(@RequestParam(value = "json", required = false) String json,
			@RequestParam(value = "clientLocationId", required = false) String responderLocationId,
			HttpServletRequest request) throws Exception {

		/*String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);*/

		
		orgDocumentsService.saveDocument();
		return null;
	
		
		
	} 

}
