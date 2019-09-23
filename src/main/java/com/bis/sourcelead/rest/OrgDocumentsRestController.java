package com.bis.sourcelead.rest;


import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
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
import com.bis.sourcelead.dto.ClientDocumentsDTO;
import com.bis.sourcelead.dto.OrgDocumentsDTO;
import com.bis.sourcelead.entity.OrgDocuments;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.OrgDocumentsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

/**
 * 
 * @author Prasanna vadla
 *
 */

@RestController
@RequestMapping("/app")
public class OrgDocumentsRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(OrgDocumentsRestController.class);
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	OrgDocumentsService orgDocumentsService;
	
	@Value("${static.path}")
	private String staticPath;
	
	@Autowired
	private OrgSpocService orgSpocService;
	
	@Autowired
	private UserService userService;
	
	@PreAuthorize("hasAnyRole('ROLE_EDIT_CLIENT_DOCUMENT','ROLE_CREATE_CLIENT_DOCUMENT','ROLE_EDIT_VENDOR_DOCUMENT','ROLE_CREATE_VENDOR_DOCUMENT')")
	@RequestMapping(value = "/saveDocuments", method = RequestMethod.POST)
	@ResponseBody String saveDocuments(ClientDocumentsDTO clientDocumentsDTO,
			@RequestParam(value = "responderLocationId", required = false) String responderLocationId,
			@RequestParam(value = "docId", required = false) String docId,
			@RequestParam(value = "sourceType", required = false) String sourceType,
			HttpServletRequest request) throws Exception {

		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		clientDocumentsDTO.setStaticPath(staticPath);
		clientDocumentsDTO.setDateFormat(orgLocation.getDateFormat());
		//FileUploadUtil.fillUploadedFileInfo(clientDocumentsDTO, clientDocumentsDTO.getFileInfo());
		orgDocumentsService.saveDocument(clientDocumentsDTO,responderLocationId,docId, orgLocation,user, sourceType);
		if(docId.equalsIgnoreCase("null")){
			SourceLeadSessionManager.setSessionAttribute(request, "addDocument", "Success !! New document added.");
		}else{
			SourceLeadSessionManager.setSessionAttribute(request, "editDocument", "Success!! Document details updated.");
		}
		
		return "success";

	} 	

	/**To get documents list
	 * @author Prasad Salina
	 * @param responderLocationId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@PreAuthorize("hasAnyRole('ROLE_VIEW_VENDOR_DOCUMENT','ROLE_VIEW_CLIENT','ROLE_VIEW_VENDOR')")
	@RequestMapping(value = {"/getclientDocumentsListByResponderLocationId","/rest/getclientDocumentsListByResponderLocationId"},method = RequestMethod.POST)
	public List<OrgDocumentsDTO> getclientDocumentsListByResponderLocationId(@RequestParam(value = "responderLocationId", required = false) String responderLocationId,
			                                                 @RequestParam(value = "sourceType", required = false) String sourceType,
			                                                 HttpServletRequest request) throws Exception {

		 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 String  msg="" ;
		 List<OrgDocumentsDTO> clientDocumentsListDTO=new ArrayList<OrgDocumentsDTO>();
		 List<OrgDocuments> clientDocumentsList = orgDocumentsService.getOrgDocumentsListByResponderLocationIdAndOrgLocationIdAndSourceType(Long.valueOf(responderLocationId), user.getId(), loginOrgLocation.getId(), sourceType);
		 for(OrgDocuments orgDocuments :clientDocumentsList){
			 OrgDocumentsDTO orgDocumentsDTO=new OrgDocumentsDTO();
			 msg = (String) SourceLeadSessionManager.getSessionAttribute(request,"addDocument");
			 if(msg == null){
				 msg = (String) SourceLeadSessionManager.getSessionAttribute(request,"editDocument");
			 }
			 if(msg == null){
				 msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "deleteDocument");
			 }
			 if(msg != null){
				 orgDocuments.setMsg(msg);
			 }
			 orgDocumentsDTO.setId((orgDocuments.getId()).toString());
			 orgDocumentsDTO.setDocName(orgDocuments.getDocName());
			 orgDocumentsDTO.setContractType(orgDocuments.getContractType());
			 orgDocumentsDTO.setEndDateStr(orgDocuments.getEndDateStr());
			 orgDocumentsDTO.setFileName(orgDocuments.getFileName());
			 orgDocumentsDTO.setFileType(orgDocuments.getFileType());
			 orgDocumentsDTO.setFileUrl(orgDocuments.getFileUrl());
			 orgDocumentsDTO.setOwnerName(orgDocuments.getOwnerName());
			 orgDocumentsDTO.setSpocName(orgDocuments.getSpocName());
			 orgDocumentsDTO.setDescription(orgDocuments.getDescription());
			 orgDocumentsDTO.setStartDateStr(orgDocuments.getStartDateStr());
			 clientDocumentsListDTO.add(orgDocumentsDTO);
				 SourceLeadSessionManager.removeSessionAttribute(request, "addDocument");
				 SourceLeadSessionManager.removeSessionAttribute(request, "editDocument");
				 SourceLeadSessionManager.removeSessionAttribute(request, "deleteDocument");
		 }
		 return clientDocumentsListDTO;
	}		
	
	
	/** to get orgDocument by Id
	 * @author Prasad Salina
	 * @param docId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = {"/getOrgDocument","/rest/getOrgDocument"}, method = RequestMethod.POST)
	public @ResponseBody OrgDocumentsDTO getOrgDocument(String docId, Long orgRelationId, HttpServletRequest request,String type) throws Exception {
		OrgDocumentsDTO orgDocumentDTO=new OrgDocumentsDTO();

		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		OrgDocuments orgDocument = orgDocumentsService.getOrgDocumentsByDocIdAndLocationId(Long.parseLong(docId), orgLocations.getId());
		List<User> ownersList = null;
		//to fetch client contact list
		List<OrgSpoc> orgSpocsList  = orgSpocService.getAllSpocsByOrgRelationId(orgRelationId, user.getId(),orgLocations.getId(),type);
		for (OrgSpoc orgSpoc : orgSpocsList) {
			if(null != type && "Client".equalsIgnoreCase(type)){
				orgSpoc.setSpocUserId(orgSpoc.getClientUser().getId());
			}else{
				orgSpoc.setSpocUserId(orgSpoc.getVendorUser().getId());
			}
			
		}
		
		if(null != type){
			if("Client".equalsIgnoreCase(type)){
				ownersList = userService.getTabAccessOwnersByOrgLocationIdAndTabId(orgLocations.getId(),Long.valueOf(Constants.TAB_CLIENT));
			}else{
				ownersList = userService.getTabAccessOwnersByOrgLocationIdAndTabId(orgLocations.getId(),Long.valueOf(Constants.TAB_VENDOR));
			}
		}
		
		
		
		if(orgDocument != null){
			
			orgDocumentDTO.setOwnersList(ownersList);
			orgDocumentDTO.setId(orgDocument.getId().toString());
			orgDocumentDTO.setFileName(orgDocument.getFileName());
			orgDocumentDTO.setDocName(orgDocument.getDocName());
			orgDocumentDTO.setContractType(orgDocument.getContractType());
			orgDocumentDTO.setOrgSpocsList(orgSpocsList);
			orgDocumentDTO.setSpocUser(orgDocument.getSpocUser());
			orgDocumentDTO.setStartDateStr(orgDocument.getStartDateStr());
			orgDocumentDTO.setEndDateStr(orgDocument.getEndDateStr());
			orgDocumentDTO.setDescription(orgDocument.getDescription());
			orgDocumentDTO.setOwnerUser(orgDocument.getOwnerUser());
			orgDocumentDTO.setOwnerId(orgDocument.getOwnerId());
		}
		return orgDocumentDTO;
	}
	
	
	@RequestMapping(value = "/getOrgDocumentbySpocId", method = RequestMethod.POST)
	@ResponseBody String getOrgDocumentbySpocId(String docId, Long orgRelationId, String userId,HttpServletRequest request) throws Exception {
		String resultJson = null;
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		OrgDocuments orgDocument = orgDocumentsService.getOrgDocumentsByIdAndSpocId(Long.parseLong(docId),Long.parseLong(userId),orgLocations);
		
		//to fetch client contact list
		List<OrgSpoc> orgSpocsList  = orgSpocService.getAllSpocsByOrgRelationId(orgRelationId, user.getId(),orgLocations.getId(),"Client");
		
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
	/*@PreAuthorize("hasAnyRole('ROLE_DOWNLOAD_VENODR_DOCS','ROLE_DOWNLOAD_CLIENT_DOCS')")
	@RequestMapping(value = "/downloadOrgDocument", method = RequestMethod.GET)
    public void downloadResume(@RequestParam(value="docId", required=false) String docId ,HttpServletResponse response) throws Exception {
		
		    OrgDocuments orgDocument =  orgDocumentsService.getOrgDocumentsById(Long.parseLong(docId));
		    
		    response.setContentType("application/octet-stream");
		    StringBuilder fileName = new StringBuilder();
	        response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(orgDocument.getFileName()).append("\"").toString());
		    
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
		}*/
		
	/**To get Org spoc Documnet List by spoc userId
	 * @author Prasanna Vadla
	 * @param spocUserId
	 * @param responderLocationId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@PreAuthorize("hasAnyRole('ROLE_VIEW_VENDOR_DOCUMENT','ROLE_VIEW_CLIENT_DOCUMENT')")
	@RequestMapping(value = "/getclientDocumentsListByResponderLocationIdAndSpocUserId")
	public String getclientDocumentsListByResponderLocationIdAndSpocUserId(@RequestParam(value = "responderLocationId", required = false) String responderLocationId,
			@RequestParam(value = "userId", required = false) String spocUserId,HttpServletRequest request) throws Exception {

		String msg = "";
		 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 String resultJson;
		 List<OrgDocuments> clientDocumentsList = orgDocumentsService.getclientDocumentsListByResponderLocationIdAndSpocUserIdOrgLocationID(Long.valueOf(responderLocationId), loginOrgLocation.getId(),Long.valueOf(spocUserId));
		 for(OrgDocuments orgDocuments : clientDocumentsList){
			 msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "addDocument");
			 if(msg == null){
				 msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "editDocument");
			 }
			 if(msg == null){
				 msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "deleteDocument");
			 }
			 if(msg != null){
				 orgDocuments.setMsg(msg);
			 }
			 SourceLeadSessionManager.removeSessionAttribute(request, "addDocument");
			 SourceLeadSessionManager.removeSessionAttribute(request, "editDocument");
			 SourceLeadSessionManager.removeSessionAttribute(request, "deleteDocument");
		 }
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
	@PreAuthorize("hasAnyRole('ROLE_DELETE_CLIENT_DOCUMENT','ROLE_DELETE_VENDOR_DOCUMENT')")
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
			StringBuilder deleteDocumentMsg = new StringBuilder();
			SourceLeadSessionManager.setSessionAttribute(request, "deleteDocument", deleteDocumentMsg.append("").append(orgDocument.getDocName()).append(" removed.").toString());
		}
		return "success";
	}


}
