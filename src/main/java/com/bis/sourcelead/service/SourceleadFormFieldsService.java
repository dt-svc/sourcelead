package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.SourceleadFormFields;

public interface SourceleadFormFieldsService {

	List<SourceleadFormFields> getAllFormFieldsBySectionId(Long sectionId) throws Exception;

}
