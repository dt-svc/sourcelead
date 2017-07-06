package com.bis.sourcelead.rest;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.BulkClientDetails;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateEducationService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.ClientImportsService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

/**
 * Handles requests for the CandidateEducation Service
 * 
 * @author Ajith Matta
 * @date 30th Aug 2016
 *
 */

@RestController
public class ClientImportRestController {


	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	ClientImportsService clientImportsService;
	
	/*@PreAuthorize("hasAnyRole('ROLE_CLIENT_BULK_IMPORTS','ROLE_CLIENT_CONTACT_BULK_IMPORTS')")*/
	@RequestMapping(value = "/clientExcelFileUpload", method = RequestMethod.POST)
	@ResponseBody
	public String fileUploaded(MultipartHttpServletRequest request,@RequestParam(value = "responderType", required = false) String responderType)throws Exception{

		String isSuccess =  "failure";
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		MultipartFile multipartFile = null;
		Iterator<String> itrator = request.getFileNames();
		if (itrator.hasNext()) {
			multipartFile = request.getFile(itrator.next());
		}
		
		String isFileCreated = "";
		if(multipartFile != null){
		isFileCreated = clientImportsService.clientExcelDataSave(multipartFile,user,orgLocation,org,"Y",responderType);
		}
		if("empty".equalsIgnoreCase(isFileCreated)){
			isSuccess = "empty";
		}else if("invalid".equalsIgnoreCase(isFileCreated)){
			isSuccess = "invalid";
		}else{
			List<BulkClientDetails> bulkClientDetailsList = clientImportsService.getbulkClientDetailsByOrgLocationId(orgLocation,user,org,"Y",responderType);
			List<BulkClientDetails> filteredBulkClientDetailsList = bulkClientDetailsList.stream()
					.filter(bulkClientDetails -> bulkClientDetails.getEmail() != null
							&& !"".equalsIgnoreCase(bulkClientDetails.getEmail()))
					.collect(Collectors.toList());
			return commonUtil.toJSON(filteredBulkClientDetailsList);
		}
		return isSuccess;
	}
	
//	/deleteRecordFromBulk
	
	/*@PreAuthorize("hasAnyRole('ROLE_CLIENT_BULK_IMPORTS','ROLE_CLIENT_CONTACT_BULK_IMPORTS')")*/
	@RequestMapping(value = "/deleteRecordFromBulk", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteRecordFromBulk(@RequestParam(value = "recordId", required = false) String recordId,@RequestParam(value = "responderType", required = false) String responderType,HttpServletRequest request)throws Exception{

		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		boolean isDeleted = false;
		BulkClientDetails bulkClientDetails  = clientImportsService.deleteDuplicateRecord(recordId,orgLocation,responderType);
		if(bulkClientDetails != null){
			isDeleted = true;
		}
		return isDeleted;
}
	
	@RequestMapping(value = "/addRecordFromBulk", method = RequestMethod.POST)
	@ResponseBody
	public boolean addRecordFromBulk(@RequestParam(value = "recordId", required = false) String recordId,@RequestParam(value = "responderType", required = false) String responderType,HttpServletRequest request)throws Exception{

		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		boolean isDeleted = false;
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		BulkClientDetails bulkClientDetails  = clientImportsService.saveClientRecord(recordId,orgLocation,org,user,responderType);
		if(bulkClientDetails != null){
			isDeleted = true;
		}
		return isDeleted;
}

	@RequestMapping(value = "/getMatchedClientList", method = RequestMethod.POST)
	@ResponseBody
	public String getMatchedClientList(@RequestParam(value = "recordId", required = false) String recordId,@RequestParam(value = "responderType", required = false) String responderType,HttpServletRequest request)throws Exception{

		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgRelations orgRelations  = clientImportsService.getMatchedClientList(recordId,orgLocation,org,user,responderType);
		return commonUtil.toJSON(orgRelations);
}
	
	
	
	@RequestMapping(value = "/mergeRecordFromBulk", method = RequestMethod.POST)
	@ResponseBody
	public String mergeRecordFromBulk(@RequestParam(value = "recordId", required = false) String recordId,@RequestParam(value = "responderType", required = false) String responderType,HttpServletRequest request)throws Exception{

		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		String[] ids = recordId.split(",");
		
		String orgRelationId= ids[0];
		String bulkRecordId = ids[1];
		
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		return  clientImportsService.mergeSpocWithClient(bulkRecordId,orgLocation,org,user,orgRelationId,responderType);
		
}
	
}

