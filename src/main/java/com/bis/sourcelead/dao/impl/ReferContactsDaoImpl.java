package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.bis.sourcelead.dao.ReferContactsDao;
import com.bis.sourcelead.entity.ReferContacts;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class ReferContactsDaoImpl implements ReferContactsDao {
	
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
	
	private static final Logger logger = LoggerFactory.getLogger(ReferContactsDaoImpl.class);

	@Override
	public ReferContacts addReferContacts(ReferContacts referContacts) throws Exception {
		logger.debug("Adding addReferContacts");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (referContacts != null) {
				session.saveOrUpdate(referContacts);
			}
			logger.debug("addReferContacts Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"REFER_CONTACTS"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return referContacts;
	}

}
