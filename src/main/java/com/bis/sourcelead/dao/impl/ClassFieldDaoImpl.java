package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ClassFieldDao;
import com.bis.sourcelead.entity.ClassField;
import com.bis.sourcelead.entity.EntityType;

/**
 * 
 * @author Prasad salina
 *
 */
@Repository
public class ClassFieldDaoImpl implements ClassFieldDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(ClassFieldDaoImpl.class);

	/**
	 * this method for get the class filed by delimiterStr
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ClassField getClassFieldByDelimiterStr(String delimiterStr) throws Exception{
		logger.debug("In ClassFieldDaoImpl class getClassFieldByDelimiterStr method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = " select cf  from ClassField cf left join fetch cf.entityTypeId where cf.delimStr = :delimiterStr";
		List cfList = session.createQuery(query)
							 .setString("delimiterStr", delimiterStr)
							 .list();
		
		ClassField classField = null;
		if(cfList != null && !cfList.isEmpty())
		{
			classField = (ClassField) cfList.iterator().next();
		}
			
		return classField;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ClassField> getClassFieldsByUniqueName(Long id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from ClassField cf  where cf.entityTypeId = :id";
		List cfList = session.createQuery(query)
							 .setLong("id", id)
							 .list();
		return cfList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public EntityType getEntityTypeById(String entityName,String entityType) throws Exception {
		logger.debug("In ClassFieldDaoImpl class getClassFieldByDelimiterStr method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from EntityType ef where ef.entityName = :entityName and ef.entityType=:entityType";
		List cfList = session.createQuery(query)
							 .setString("entityName", entityName).setString("entityType", entityType)
							 .list();
		
		EntityType entityTypes = null;
		if(cfList != null && !cfList.isEmpty())
		{
			entityTypes = (EntityType) cfList.iterator().next();
		}
			
		return entityTypes;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ClassField getClassFieldByfieldName(String fieldName, Long entityId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ClassField cf  where cf.entityTypeId=:entityId and cf.delimiter = :fieldName";
		List cfList = session.createQuery(query)
							 .setString("fieldName", fieldName).setLong("entityId", entityId)
							 .list();
		
		ClassField classField = null;
		if(cfList != null && !cfList.isEmpty())
		{
			classField = (ClassField) cfList.iterator().next();
		}
			
		return classField;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ClassField getClassFieldByEmailScenerioIdAndfieldName(String fieldName, Long emailScenerioId)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ClassField cf  where cf.emailScenarioId=:emailScenarioId and cf.delimiter = :fieldName";
		List cfList = session.createQuery(query)
							 .setString("fieldName", fieldName).setLong("emailScenarioId", emailScenerioId)
							 .list();
		
		ClassField classField = null;
		if(cfList != null && !cfList.isEmpty())
		{
			classField = (ClassField) cfList.iterator().next();
		}
			
		return classField;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<String> getClassFieldsByEmailScenerioId(Long emailScenarioId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select cf.delimiter from ClassField cf  where cf.emailScenarioId.id = :emailScenarioId";
		List<String> cfList = session.createQuery(query)
							 .setLong("emailScenarioId", emailScenarioId)
							 .list();
		return cfList;
	}

}
