package com.bis.sourcelead.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.bis.sourcelead.dto.CandidateBulkUploadDTO;
import com.bis.sourcelead.dto.DataMigrationDTO;
import com.bis.sourcelead.dto.UploadEntriesDTO;
import com.bis.sourcelead.entity.SourceleadFormFields;

public interface DataMigrationService {

	/**
	 * @author Venkat Kesav
	 * @return
	 * @throws Exception
	 * 
	 */
	Map<String, String> getfileData(String filePath, String headerPosition) throws Exception;
	
	String validateImportedFormData(DataMigrationDTO dataMigrationDTO, HttpServletRequest request) throws Exception;
	
	List<SourceleadFormFields> getfieldsByActionIdAndImportStatus(Long actionId, int importStatus) throws Exception;
	
	List<SourceleadFormFields> getSubmissionTemplateFormFields(Long requirementId) throws Exception;
	
	Map<String, String> getCandidateMappingFileData(String filePath, String headerPosition) throws Exception;
	
	String storeCandidateBulkUploadToCouchbase(CandidateBulkUploadDTO candidateBulkUploadDTO,UploadEntriesDTO uploadEntriesDTO, HttpServletRequest request) throws Exception;
	
	void saveCandidateBulkImports(String couchbaseQueueKey, HttpServletRequest request) throws Exception;
	
	void bulkUploadEmails(String couchbaseQueueKey, String uploadedSize, String type, HttpServletRequest request) throws Exception;
	
}
