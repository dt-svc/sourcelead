package com.bis.sourcelead.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.dto.DataMigrationDTO;
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
}
