package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.ClassField;
import com.bis.sourcelead.entity.EntityType;

/**
 * 
 * @author Prasad salina
 *
 */
public interface ClassFieldService {
	
	ClassField getClassFieldByDelimiterStr(String delimiterStr) throws Exception;
	
	ClassField getClassFieldByfieldName(String fieldName,Long entityId) throws Exception;
	
	List<ClassField> getClassFieldsByUniqueName(Long entityId) throws Exception;
	
	EntityType getEntityTypeById(String entityName,String entityType) throws Exception;
	
	ClassField getClassFieldByEmailScenerioIdAndfieldName(String fieldName,Long emailScenerioId) throws Exception;
	
	List<String> getClassFieldsByEmailScenerioId(Long emailScenarioId) throws Exception;
	
}