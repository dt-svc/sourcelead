package com.bis.sourcelead.immigration.dao.impl;

import java.text.MessageFormat;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.dao.impl.UserDaoImpl;
import com.bis.sourcelead.entity.ImmigrationTemplate;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.immigration.dao.ImmigrationTemplateDao;

/**
 * @author Srinivas.Sabbu
 *
 */
@Repository
public class ImmigrationTemplateDaoImpl implements ImmigrationTemplateDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private UserRoleDao userRoleDao;

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

	private static final Logger logger = LoggerFactory.getLogger(ImmigrationTemplateDaoImpl.class);

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Long savePetitionTemplateDetails(ImmigrationTemplate immigrationTemplate) {
		logger.debug("Adding User");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (immigrationTemplate != null) {
				session.saveOrUpdate(immigrationTemplate);
			}
			logger.debug("User Added Successfully");
		} catch (Exception exception) {
			StringBuilder message = new StringBuilder(MessageFormat.format(duplicateValue, "USER"));
			try {
				throw new SourceLeadConstraintViolationException(message.toString() + exception.getCause().toString());
			} catch (SourceLeadConstraintViolationException e) {
				e.printStackTrace();
			}
		}
		return immigrationTemplate.getId();
	}

}
