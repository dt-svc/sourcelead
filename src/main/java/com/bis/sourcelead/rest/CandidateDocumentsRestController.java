package com.bis.sourcelead.rest;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
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

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.CandidateDocuments;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequestedDocuments;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateDocumentsService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.RequestedDocumentsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

/**
 * Handles requests for the CandidateDocuments Service
 * 
 * @author Prasad K
 * @date 06th April 2017
 *
 */

@RestController
public class CandidateDocumentsRestController {

	private static final Logger logger = LoggerFactory.getLogger(CandidateDocumentsRestController.class); 
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	
	@Autowired
	private CandidateDocumentsService candidateDocumentsService;
	
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	LookUpDataService lookUpDataService;
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	RequestedDocumentsService requestedDocumentsService;
	
	
	/**
	* @author Prasad K
	* 
	* Handles Document saving for Candidate
	* 
	* @param json, candidateId
	* @param request
	* 
	* @return ResponseBody
	* 
	* @throws Exception
	*/
	
	
	@RequestMapping(value = "/saveCandidateDocument", method = RequestMethod.POST)
	public @ResponseBody String saveCandidateDocument(@RequestParam(value = "json", required = false) String json,@RequestParam(value = "candidateId", required = false) Long candidateId,
			@RequestParam(value = "documentUUID", required = false) String documentUUID,MultipartHttpServletRequest request) throws Exception {

		
		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);
		
		MultipartFile multiFile = null;
		Iterator<String> itrator = request.getFileNames();
		if(itrator.hasNext()){
	        multiFile = request.getFile(itrator.next());
		}

		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		if(null !=candidateId){
			candidateDocumentsService.storeCandidateDocuments(jsonObj, candidateId,documentUUID, orgLocation.getId(), user, multiFile);
			return "success";
		}else{
			return "exception";
		}
	}
	
	/**
	 * This Method is for getting candidate documents
	 *  
	 * @author PrasadK
	 * 
	 * @param candidateId
	 * @param request
	 * 
	 * @return CandidateEmployers list
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCandidateDocumentsListByCandidateId")
	public String getCandidateDocumentsListByCandidateId(@RequestParam(value = "candidateId", required = false) String candidateId,HttpServletRequest request) throws Exception {
		
		
		 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 String resultJson;
		 List<CandidateDocuments>   CandidateDocumentsList = candidateDocumentsService.getCandidateDocumentsListByCandidateIdAndOrgLocationID(Long.parseLong(candidateId), loginOrgLocation.getId());
		 for(CandidateDocuments canDoc : CandidateDocumentsList){
				canDoc.setUserIdStr(user.getId());
			}
		 resultJson = commonUtil.toJSON(CandidateDocumentsList);
		 return resultJson;
	}	
	
	
	/**
	* @author Prasad K 
	* 
	* This method is used to get Document
	* 
	* @param documentId
	* @param request
	* 
	* @return resultJson
	* 
	* @throws Exception
	*/
	
	@RequestMapping(value = "/getDocument", method = RequestMethod.POST)
	public @ResponseBody String getDocument(@RequestParam(value = "candidateId", required = false) Long candidateId,
			@RequestParam(value = "documentId", required = false) String documentId, HttpServletRequest request) throws Exception {
		String resultJson = null;
		CandidateDocuments candidateDocuments =  candidateDocumentsService.getCandidateDocumentByDocumentId(Long.parseLong(documentId));
		if(candidateDocuments != null){
			resultJson = commonUtil.toJSON(candidateDocuments);
		}
		return resultJson;
	}
	

	/**
	* @author Prasad K 
	* 
	* This method is used to download Document
	* 
	* @param documentUUID
	* @param request
	* 
	* @return resultJson
	* 
	* @throws Exception
	*/
	@RequestMapping(value = "/downloadDoc", method = RequestMethod.GET)
    public void downloadDoc(@RequestParam(value="documentUUID", required=false) String documentUUID ,HttpServletResponse response) throws Exception {
		
		CandidateDocuments candidateDocuments =  candidateDocumentsService.getCandidateDocumentByDocumentUUID(documentUUID);
		    
			if(null != candidateDocuments){
				response.setContentType("application/octet-stream");
			    StringBuilder fileName = new StringBuilder();
		        response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(candidateDocuments.getFileName()).append("\"").toString());
			    
				InputStream inputStream = new ByteArrayInputStream(candidateDocuments.getDocFile());
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
	
	
	/**
	* @author Prasad K 
	* 
	* This method is used to update Document
	* 
	* @param documentId
	* @param request
	* 
	* @return resultJson
	* 
	* @throws Exception
	*/
	
	@RequestMapping(value = "/terminateDoc", method = RequestMethod.POST)
	public @ResponseBody String terminateDoc(@RequestParam(value = "candidateId", required = false) Long candidateId,
			@RequestParam(value = "documentId", required = false) String documentId, 
			HttpServletRequest request) throws Exception {
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		
		CandidateDocuments candidateDocuments =  candidateDocumentsService.getCandidateDocumentByDocumentId(Long.parseLong(documentId));
		if(candidateDocuments != null){
			candidateDocuments.setStatus(Constants.CANDIDATE_IN_ACTIVE);
			candidateDocuments.setUpdatedBy(user.getUserCode());
			candidateDocuments.setUpdatedDate(commonUtil.currentDate());
			candidateDocumentsService.updateCandidateDocuments(candidateDocuments);
		}
		return "success";
	}
	
	
	
	@RequestMapping(value = "/requestCandidateForDocuments", method = RequestMethod.POST)
	public @ResponseBody String requestCandidateForDocuments(@RequestParam(value = "json", required = false) String json,
			HttpServletRequest request) throws Exception {

		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		
		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);
		
		requestedDocumentsService.storeRequestedDocumentsDetails(jsonObj, user, loginOrgLocation);
		
		return "success";
	}
	
	
	 @RequestMapping(value = "/uploadRequestedDocuments" , method = RequestMethod.POST)
	 public @ResponseBody  String uploadRequestedDocuments(@RequestParam(value="json", required=false) String json, MultipartHttpServletRequest request) throws Exception {
		
		 String json1 = "{" + json + "}";
		 JSONObject jsonObj = new JSONObject(json1);
			
		 RequestedDocuments requestedDocuments = requestedDocumentsService.getRequestedDocumentsByRequestDocUUID(jsonObj.getString("requestDocUUID"));
			if(null != requestedDocuments){
				List<CandidateDocuments> candidateDocumentsList = candidateDocumentsService.getCandidateDocumentsListByRequestedDocumentsId(requestedDocuments.getId());
				if(null != candidateDocumentsList && candidateDocumentsList.size() > 0){
					for(CandidateDocuments candidateDocuments : candidateDocumentsList){
						
						final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request; 
		       		    final Map files = multiRequest.getFileMap();
		       		     
		       		    MultipartFile  multiFile = (MultipartFile)files.get("docFile"+candidateDocuments.getId());
		       		    if(multiFile != null && !multiFile.isEmpty()){
		       		    	candidateDocuments.setDocFile(multiFile.getBytes());
		       		    	candidateDocuments.setFileName(multiFile.getOriginalFilename());
					    }
		       		    
			       		 if(null != multiFile.getContentType()){
			       			candidateDocuments.setFileContentType(multiFile.getContentType());
	                     }
			       		 candidateDocuments.setRequestedDocStatus(Constants.REQUESTED_DOCUMENT_SUBMITTED);
			       		 candidateDocumentsService.updateCandidateDocuments(candidateDocuments);
				}
			}
				requestedDocuments.setIsDocsUploaded(Constants.YES);
				requestedDocumentsService.saveRequestedDocuments(requestedDocuments);
		}
	   return "success";
	 }
	 
	 
	 

		@RequestMapping(value = "/rejectRequestedDocuments", method = RequestMethod.POST)
		public @ResponseBody String rejectRequestedDocuments(@RequestParam(value = "json", required = false) String json,
				@RequestParam(value = "documentsIds", required = false) String documentsIds,HttpServletRequest request) throws Exception {

			
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			
			String json1 = "{" + json + "}";
			JSONObject jsonObj = new JSONObject(json1);
			
			requestedDocumentsService.rejectRequestedDocuments(jsonObj, user, loginOrgLocation,documentsIds);
			
			return "success";
		}
		
		@RequestMapping(value = "/acceptRequestedDocuments", method = RequestMethod.POST)
		public @ResponseBody String acceptRequestedDocuments(@RequestParam(value = "documentsIds", required = false) String documentsIds,HttpServletRequest request) throws Exception {

			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			requestedDocumentsService.acceptRequestedDocuments(user,documentsIds);
			return "success";
		}
		
	 
}


