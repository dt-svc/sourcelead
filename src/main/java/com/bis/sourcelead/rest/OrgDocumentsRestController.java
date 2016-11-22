package com.bis.sourcelead.rest;


import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgDocuments;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.OrgDocumentsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

/**
 * 
 * @author Prasanna vadla
 *
 */

@RestController
public class OrgDocumentsRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(OrgDocumentsRestController.class);
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	OrgDocumentsService orgDocumentsService;
	
	@Autowired
	private OrgSpocService orgSpocService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/saveDocuments", method = RequestMethod.POST)
	@ResponseBody String saveDocuments(@RequestParam(value = "json", required = false) String json,
			@RequestParam(value = "responderLocationId", required = false) String responderLocationId,
			@RequestParam(value = "docId", required = false) String docId,
			@RequestParam(value = "sourceType", required = false) String sourceType,
			MultipartHttpServletRequest request) throws Exception {

		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);

		MultipartFile multiFile = null;
		Iterator<String> itrator = request.getFileNames();
		if(itrator.hasNext()){
	        multiFile = request.getFile(itrator.next());
		}
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		orgDocumentsService.saveDocument(jsonObj,multiFile,responderLocationId,docId, orgLocation,user, sourceType);
		return "success";

	} 	

	/**To get documents list
	 * @author Prasad Salina
	 * @param responderLocationId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@PreAuthorize("hasRole('ROLE_VIEW_VENDOR_DOCUMENT')")
	@RequestMapping(value = "/getclientDocumentsListByResponderLocationId")
	public String getclientDocumentsListByResponderLocationId(@RequestParam(value = "responderLocationId", required = false) String responderLocationId,
			                                                 @RequestParam(value = "sourceType", required = false) String sourceType,
			                                                 HttpServletRequest request) throws Exception {

		 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 String resultJson;
		 List<OrgDocuments> clientDocumentsList = orgDocumentsService.getOrgDocumentsListByResponderLocationIdAndOrgLocationIdAndSourceType(Long.valueOf(responderLocationId), user.getId(), loginOrgLocation.getId(), sourceType);
		 resultJson = commonUtil.toJSON(clientDocumentsList);
		 return resultJson;
	}	
	
	
	/** to get orgDocument by Id
	 * @author Prasad Salina
	 * @param docId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getOrgDocument", method = RequestMethod.POST)
	public @ResponseBody String getOrgDocument(String docId, Long orgRelationId, HttpServletRequest request) throws Exception {
		String resultJson = null;
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		OrgDocuments orgDocument = orgDocumentsService.getOrgDocumentsById(Long.parseLong(docId));
		
		//to fetch client contact list
		List<OrgSpoc> orgSpocsList  = orgSpocService.getAllSpocsByOrgRelationId(orgRelationId, user.getId(),orgLocations.getId());
		for (OrgSpoc orgSpoc : orgSpocsList) {
			orgSpoc.setSpocName(orgSpoc.getSpocUser().getFirstName()+" "+orgSpoc.getSpocUser().getLastName());
			orgSpoc.setSpocUserId(orgSpoc.getSpocUser().getId());
		}
		
		//to fetch Owners list
		List<User> ownersList = userService.getOwnersByOrgLocationId(orgLocations.getId());
		
		if(orgDocument != null){
			orgDocument.setOrgSpocsList(orgSpocsList);
			orgDocument.setOwnersList(ownersList);
			resultJson = commonUtil.toJSON(orgDocument);
		}
		return resultJson;
	}
	
	
	@RequestMapping(value = "/getOrgDocumentbySpocId", method = RequestMethod.POST)
	@ResponseBody String getOrgDocumentbySpocId(String docId, Long orgRelationId, String userId,HttpServletRequest request) throws Exception {
		String resultJson = null;
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		OrgDocuments orgDocument = orgDocumentsService.getOrgDocumentsByIdAndSpocId(Long.parseLong(docId),Long.parseLong(userId));
		
		//to fetch client contact list
		List<OrgSpoc> orgSpocsList  = orgSpocService.getAllSpocsByOrgRelationId(orgRelationId, user.getId(),orgLocations.getId());
		for (OrgSpoc orgSpoc : orgSpocsList) {
			orgSpoc.setSpocName(orgSpoc.getSpocUser().getFirstName()+" "+orgSpoc.getSpocUser().getLastName());
			orgSpoc.setSpocUserId(orgSpoc.getSpocUser().getId());
		}
		
		//to fetch Owners list
		List<User> ownersList = userService.getOwnersByOrgLocationId(orgLocations.getId());
		
		if(orgDocument != null){
			orgDocument.setOrgSpocsList(orgSpocsList);
			orgDocument.setOwnersList(ownersList);
			resultJson = commonUtil.toJSON(orgDocument);
		}
		return resultJson;
	}
	
	/** to download document
	 * @author Prasad Salina
	 * @param resumeId
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/downloadOrgDocument", method = RequestMethod.GET)
    public void downloadResume(@RequestParam(value="docId", required=false) String docId ,HttpServletResponse response) throws Exception {
		
		    OrgDocuments orgDocument =  orgDocumentsService.getOrgDocumentsById(Long.parseLong(docId));
		    
		    response.setContentType("application/octet-stream");
	        response.setHeader("Content-Disposition", "attachment;filename="+orgDocument.getFileName());
		    
			InputStream inputStream = new ByteArrayInputStream(orgDocument.getDocFile());
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
		
	/**To get Org spoc Documnet List by spoc userId
	 * @author Prasanna Vadla
	 * @param spocUserId
	 * @param responderLocationId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@PreAuthorize("hasRole('ROLE_VIEW_VENDOR_CONTACT_DOCUMENT')")
	@RequestMapping(value = "/getclientDocumentsListByResponderLocationIdAndSpocUserId")
	public String getclientDocumentsListByResponderLocationIdAndSpocUserId(@RequestParam(value = "responderLocationId", required = false) String responderLocationId,
			@RequestParam(value = "userId", required = false) String spocUserId,HttpServletRequest request) throws Exception {

		 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 String resultJson;
		 List<OrgDocuments> clientDocumentsList = orgDocumentsService.getclientDocumentsListByResponderLocationIdAndSpocUserIdOrgLocationID(Long.valueOf(responderLocationId), loginOrgLocation.getId(),Long.valueOf(spocUserId));
		 resultJson = commonUtil.toJSON(clientDocumentsList);
		 return resultJson;
	}	

	/**To terminate OrgDocumnet by using Id
	 * @author Prasad Salina
	 * @param docId
	 * @param terminateReason
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/terminateOrgDocumnet", method = RequestMethod.POST)
	public @ResponseBody String terminateOrgDocumnet(@RequestParam(value="docId", required=false) String docId ,
			                                         @RequestParam(value = "terminateReason", required = false) String terminateReason,
			                                         HttpServletRequest request) throws Exception {
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		
		OrgDocuments orgDocument =  orgDocumentsService.getOrgDocumentsById(Long.parseLong(docId));
		if(orgDocument != null){
			orgDocument.setTerminateReason(terminateReason);
			orgDocument.setStatus(Constants.MEMBER_IN_ACTIVE);
			orgDocument.setUpdatedBy(user.getUserCode());
			orgDocument.setUpdatedDate(commonUtil.currentDate());
			orgDocumentsService.updateDocumnet(orgDocument);
		}
		return "success";
	}


}
