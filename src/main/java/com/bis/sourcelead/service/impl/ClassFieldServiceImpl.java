package com.bis.sourcelead.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ClassFieldDao;
import com.bis.sourcelead.entity.ClassField;
import com.bis.sourcelead.entity.EntityType;
import com.bis.sourcelead.service.ClassFieldService;

/**
 * 
 * @author Prasad salina
 *
 */
@Service
public class ClassFieldServiceImpl implements ClassFieldService{
	
	private static final Logger logger = LoggerFactory.getLogger(ClassFieldServiceImpl.class);
	
	@Autowired
	private ClassFieldDao classFieldDao;
	
	
	
	@Override
	public ClassField getClassFieldByDelimiterStr(String delimiterStr) throws Exception{
		logger.debug("In ClassFieldServiceImpl class getClassFieldByDelimiterStr method");
		ClassField classField = classFieldDao.getClassFieldByDelimiterStr(delimiterStr);
		return classField;
	}



	@Override
	public List<ClassField> getClassFieldsByUniqueName(Long  entityId) throws Exception {
		// TODO Auto-generated method stub
		return classFieldDao.getClassFieldsByUniqueName(entityId);
	}



	@Override
	public EntityType getEntityTypeById(String entityName,String entityType) throws Exception {
		return classFieldDao.getEntityTypeById(entityName,entityType);
	}



	@Override
	public ClassField getClassFieldByfieldName(String fieldName, Long entityId) throws Exception {
		return classFieldDao.getClassFieldByfieldName(fieldName, entityId);
	}



	@Override
	public ClassField getClassFieldByEmailScenerioIdAndfieldName(String fieldName, Long emailScenerioId)
			throws Exception {
		return classFieldDao.getClassFieldByEmailScenerioIdAndfieldName(fieldName, emailScenerioId);
	}



	@Override
	public List<String> getClassFieldsByEmailScenerioId(Long emailScenarioId) throws Exception {
		return classFieldDao.getClassFieldsByEmailScenerioId(emailScenarioId);
	}

}
