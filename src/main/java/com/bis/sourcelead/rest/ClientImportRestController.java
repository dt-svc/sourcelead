package com.bis.sourcelead.rest;

import java.util.ArrayList;
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
import com.bis.sourcelead.dto.ClientVendorUploadDTO;
import com.bis.sourcelead.dto.ResponseList;
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
	
	@PreAuthorize("hasAnyRole('ROLE_VENDOR_BULK_IMPORTS','ROLE_CLIENT_BULK_IMPORTS')")
	@RequestMapping(value = "/clientExcelFileUpload", method = RequestMethod.POST)
	@ResponseBody
	public ResponseList<BulkClientDetails> fileUploaded(ClientVendorUploadDTO clientVendorUploadDTO,MultipartHttpServletRequest request,@RequestParam(value = "responderType", required = false) String responderType,@RequestParam(value = "isMailCheck", required = false) String isMailCheck)throws Exception{

		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		MultipartFile multipartFile = null;
		Iterator<String> itrator = request.getFileNames();
		if (itrator.hasNext()) {
			multipartFile = request.getFile(itrator.next());
		}
		
		List<BulkClientDetails> fileList = null;
		if(multipartFile != null){
			fileList = clientImportsService.clientExcelDataSave(multipartFile,user,orgLocation,org,isMailCheck,responderType);
		}
		
		if(fileList != null && fileList.size() > 0) {
			ResponseList<BulkClientDetails> response = clientImportsService.getbulkClientDetailsByOrgLocationId(fileList,clientVendorUploadDTO,orgLocation,user,org,isMailCheck,responderType);
			
			
			if(response !=null) {
				if(response.getData().isEmpty() && response.getData().size() == 0) {
					response.setStatusCode("success");
				}else if(!response.getData().isEmpty() && response.getData().size() >0) {
					List<BulkClientDetails> filteredBulkClientDetailsList = response.getData();
					response.setData(filteredBulkClientDetailsList);
					if(response.getData() !=null ) {
						response.setStatusCode("success");
					}else if(filteredBulkClientDetailsList !=null ) {
						response.setStatusCode("invalid");
					}else {
						response.setStatusCode("invalid");
					}
					
				}
			}
			return response;
		}
		return null;
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
	public boolean addRecordFromBulk(@RequestParam(value = "recordId", required = false) String recordId,@RequestParam(value = "responderType", required = false) String responderType,@RequestParam(value = "templateId", required = false) List<String> templateId,HttpServletRequest request)throws Exception{

		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		boolean isDeleted = false;
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		BulkClientDetails bulkClientDetails  = clientImportsService.saveClientRecord(recordId,orgLocation,org,user,responderType,templateId);
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
