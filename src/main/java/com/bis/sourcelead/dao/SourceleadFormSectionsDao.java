package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.SourceleadFormSections;

public interface SourceleadFormSectionsDao {

	List<SourceleadActions> getAllSourceleadActions() throws Exception;
	
	List<SourceleadFormSections> getSourceleadFormSectionsByActionId(Long actionId) throws Exception;
}
