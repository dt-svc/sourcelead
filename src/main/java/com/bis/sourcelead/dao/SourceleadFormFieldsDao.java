package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.SourceleadFormFields;

public interface SourceleadFormFieldsDao {

	List<SourceleadFormFields> getAllFormFieldsBySectionId(Long sectionId) throws Exception;

}
