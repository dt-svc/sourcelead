package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.DataMigrationDao;
import com.bis.sourcelead.entity.SourceleadFormFields;

@Repository
public class DataMigrationDaoImpl implements DataMigrationDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(DataMigrationDaoImpl.class);

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SourceleadFormFields> getfieldsByActionIdAndImportStatus(Long actionId, int importStatus) throws Exception
	{
		logger.debug("In DataMigrationDaoImpl class getfieldsBySectionIdAndImportStatus method");
		Session session = this.sessionFactory.getCurrentSession();
		List<SourceleadFormFields> formfieldsList = null;
		try {
			String QUERY = "FROM SourceleadFormFields SFF WHERE SFF.sourceleadFormSections.id IN(SELECT SFS.id FROM SourceleadFormSections SFS WHERE SFS.sourceleadActions = :actionId and SFS.status = '1' ) AND SFF.isImportField = '1' ";
			 formfieldsList = session.createQuery(QUERY).setLong("actionId", actionId).list();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return formfieldsList;
	}

}