package com.bis.sourcelead.service;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.dto.ClientVendorUploadDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.BulkClientDetails;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.User;

public interface ClientImportsService {

//	String  clientBulkSaving(MultipartFile multipartFile,User user,OrgLocations orgLocation,Org  org,String isMailCheck, String responderType)throws Exception;

	File getVendorContactsTemplate(Long orgLocationID) throws Exception;
	
	File getClientContactsTemplate(Long orgLocationID) throws Exception;

	File getClientTemplate(Long orgLocationId)throws Exception;

	String clientBulkSavingImp(MultipartFile multipartFile, User user, OrgLocations orgLocation, Org org, String isMailCheck, String responderType)throws Exception;

	List<BulkClientDetails> clientExcelDataSave(MultipartFile multipartFile, User user, OrgLocations orgLocation, Org org, String email, String responderType)throws Exception;

	ResponseList<BulkClientDetails> getbulkClientDetailsByOrgLocationId(List<BulkClientDetails> fileList,ClientVendorUploadDTO clientVendorUploadDTO,OrgLocations orgLocation, User user, Org org, String isMailSent, String responderType) throws Exception;

	BulkClientDetails deleteDuplicateRecord(String recordId, OrgLocations orgLocation,String responderType)throws Exception;

	BulkClientDetails saveClientRecord(String recordId, OrgLocations orgLocation, Org org, User user,String responderType, List<String> templateId) throws Exception;

	OrgRelations getMatchedClientList(String recordId, OrgLocations orgLocation, Org org, User user, String responderType) throws Exception;

	String mergeSpocWithClient(String bulkRecordId, OrgLocations orgLocation, Org org, User user, String orgRelationId,String responderType) throws Exception;
	
}
