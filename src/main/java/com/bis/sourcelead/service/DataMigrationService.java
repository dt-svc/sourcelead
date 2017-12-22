package com.bis.sourcelead.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

import com.bis.sourcelead.dto.DataMigrationDTO;
import com.bis.sourcelead.entity.SourceleadFormFields;

public interface DataMigrationService {

	/**
	 * @author Venkat Kesav
	 * @return
	 * @throws Exception
	 * 
	 */
	Map<String, String> getfileData(String filePath) throws Exception;
	
	Map<String, String> validateImportedFormData(DataMigrationDTO dataMigrationDTO) throws Exception;
	
	List<SourceleadFormFields> getfieldsBySectionIdAndImportStatus(Long sectionId, int importStatus) throws Exception;
}
