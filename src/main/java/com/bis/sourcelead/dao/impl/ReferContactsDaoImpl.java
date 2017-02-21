package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ReferContactsDao;
import com.bis.sourcelead.entity.ReferContacts;
import com.bis.sourcelead.entity.User;
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
	@Transactional
	public ReferContacts addReferContacts(ReferContacts referContacts) throws Exception {
		logger.debug("Adding addReferContacts");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (referContacts != null) {
				session.saveOrUpdate(referContacts);
			}
			logger.debug("addReferContacts Added Successfully");
		} catch (Exception exception) {
			StringBuilder message = new StringBuilder(MessageFormat.format(duplicateValue, "REFER_CONTACTS"));
			throw new SourceLeadConstraintViolationException(message.toString() + exception.getCause().toString());
		}
		return referContacts;
	}

	@Override
	@Transactional
	public ReferContacts getReferContactsByUUID(String contactUUID) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		List<ReferContacts> referContacts = new ArrayList<ReferContacts>();
		logger.debug("getReferContactsByUUID start");
		referContacts = session
				.createQuery(
						"from ReferContacts contacts left join fetch contacts.referFromUser where contacts.contactUserUUID =:contactUUID")
				.setString("contactUUID", contactUUID).setCacheable(true).list();
		logger.debug("getReferContactsByUUID end");
		if (referContacts.size() > 0) {
			return referContacts.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public ReferContacts getReferContactsByEmails(Long userId, String toEmail) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<ReferContacts> referContacts = new ArrayList<ReferContacts>();
		logger.debug("getReferContactsByEmails start");
		referContacts = session
				.createQuery("from ReferContacts contacts left join fetch contacts.referFromUser user where user.id =:userId and contacts.contactUserEmail = :toEmail")
				.setLong("userId", userId).setString("toEmail", toEmail).setCacheable(true).list();
		logger.debug("getReferContactsByEmails end");
		if (referContacts.size() > 0) {
			return referContacts.get(0);
		} else {
			return null;
		}
	}

}
