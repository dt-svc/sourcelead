package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.SourceleadFormFields;
import com.bis.sourcelead.entity.SourceleadFormSections;

public interface CustomFieldService {
	
public List<SourceleadFormSections> getAllCustomFieldServices();

public List<SourceleadFormFields> getFormFieldsBysectionId(String sectionFieldId);


}
