package com.bis.sourcelead.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.OrgDocuments;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.service.OrgDocumentsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

/**
 * 
 * @author Prasanna vadla
 *
 */

@RestController
public class OrgDocumentsRestController {
	
	@Autowired
	CommonUtil commonUtil;
	
	
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
	
	
	
	
	
	/**To get documents list
	 * @author Prasad Salina
	 * @param responderLocationId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/getclientDocumentsListByResponderLocationId")
	public String getclientDocumentsListByResponderLocationId(@RequestParam(value = "responderLocationId", required = false) String responderLocationId,HttpServletRequest request) throws Exception {

		 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 String resultJson;
		 List<OrgDocuments> clientDocumentsList = orgDocumentsService.getClientDocumentsListByResponderLocationIdAndOrgLocationID(Long.valueOf(responderLocationId), loginOrgLocation.getId());
		 resultJson = commonUtil.toJSON(clientDocumentsList);
		 return resultJson;
	}	

}
