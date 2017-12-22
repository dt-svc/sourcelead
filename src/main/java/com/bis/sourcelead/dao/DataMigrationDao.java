package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.SourceleadFormFields;

public interface DataMigrationDao {

	List<SourceleadFormFields> getfieldsBySectionIdAndImportStatus(Long sectionId, int importStatus) throws Exception;
}
