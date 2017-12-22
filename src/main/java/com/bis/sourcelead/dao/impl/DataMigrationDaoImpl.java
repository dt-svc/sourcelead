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
	public List<SourceleadFormFields> getfieldsBySectionIdAndImportStatus(Long sectionId, int importStatus) throws Exception
	{
		logger.debug("In DataMigrationDaoImpl class getfieldsBySectionIdAndImportStatus method");
		Session session = this.sessionFactory.getCurrentSession();
		String QUERY = "from SourceleadFormFields SFF where SFF.sourceleadFormSections.id =:sectionId and SFF.isImportField = '1' ";
		List<SourceleadFormFields> formfieldsList = session.createQuery(QUERY).setLong("sectionId", sectionId).list();
		return formfieldsList;
	}

}
