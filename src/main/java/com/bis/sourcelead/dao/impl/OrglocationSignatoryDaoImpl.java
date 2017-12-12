package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.OrglocationSignatoryDao;
import com.bis.sourcelead.entity.OrglocationSignatory;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class OrglocationSignatoryDaoImpl implements OrglocationSignatoryDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${SourceLead.100}")
	private String notFound;
	@Value("${SourceLead.101}")
	private String noRecords;
	@Value("${SourceLead.102}")
	private String statusUpdate;
	@Value("${SourceLead.103}")
	private String statusDelete;
	@Value("${SourceLead.106}")
	private String duplicateValue;

	private static final Logger logger = LoggerFactory.getLogger(OrglocationSignatoryDaoImpl.class);

	@Override
	@Transactional
	public OrglocationSignatory saveOrglocationSignatory(OrglocationSignatory orglocationSignatory) throws Exception {
	
		logger.debug("Adding orglocationSignatory");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (orglocationSignatory != null) {
				session.saveOrUpdate(orglocationSignatory);
			}
			logger.debug("orglocationSignatory Added Successfully");
		} catch (ConstraintViolationException exception) {
			StringBuffer message= new StringBuffer(MessageFormat.format(duplicateValue,"ORGLOCATION_SIGNATORY"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return orglocationSignatory;
	}
}
