package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.ClassField;
import com.bis.sourcelead.entity.EntityType;

public interface ClassFieldDao {

	ClassField getClassFieldByDelimiterStr(String delimiterStr) throws Exception;
	
	List<ClassField> getClassFieldsByUniqueName(Long entityId) throws Exception;
	
	ClassField getClassFieldByfieldName(String fieldName,Long entityId) throws Exception;
	
	EntityType getEntityTypeById(String entityName,String entityType) throws Exception;
	
	ClassField getClassFieldByEmailScenerioIdAndfieldName(String fieldName,Long emailScenerioId) throws Exception;
	
	List<String> getClassFieldsByEmailScenerioId(Long emailScenarioId) throws Exception;
}
