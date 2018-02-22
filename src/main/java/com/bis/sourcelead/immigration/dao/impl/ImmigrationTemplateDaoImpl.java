package com.bis.sourcelead.immigration.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.dto.ImmigrationTemplateDocDTO;
import com.bis.sourcelead.entity.ImmigrationTemplate;
import com.bis.sourcelead.entity.ImmigrationTemplateDocs;
import com.bis.sourcelead.entity.ImmigrationTemplateForms;
import com.bis.sourcelead.entity.ImmigrationTemplateTasks;
import com.bis.sourcelead.entity.ImmigrationTemplateView;
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
	public Long savePetitionTemplateDetails(ImmigrationTemplate immigrationTemplate, String templateId) {
		logger.debug("Saving Template Details-->");
		try {
			Session session = this.sessionFactory.getCurrentSession();
			if (immigrationTemplate != null) {
					session.saveOrUpdate(immigrationTemplate);
			}
			logger.debug("tempalte Details Added Successfully-->");
		} catch (Exception exception) {
			StringBuilder message = new StringBuilder(MessageFormat.format(duplicateValue, "immigrationTemplate"));
			try {
				throw new SourceLeadConstraintViolationException(message.toString() + exception.getCause().toString());
			} catch (SourceLeadConstraintViolationException e) {
				e.printStackTrace();
			}
		}
		return immigrationTemplate.getId();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Long savePetitionTemplateDocDetails(ImmigrationTemplateDocs immigrationTemplateDocs) {
		logger.debug("Saving Temlate document Details-->");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (immigrationTemplateDocs != null) {
				session.saveOrUpdate(immigrationTemplateDocs);
			}
			
			logger.debug("Template documents added Successfully-->");
		} catch (Exception exception) {
			StringBuilder message = new StringBuilder(MessageFormat.format(duplicateValue, "immigrationTemplateDocs"));
			try {
				throw new SourceLeadConstraintViolationException(message.toString() + exception.getCause().toString());
			} catch (SourceLeadConstraintViolationException e) {
				e.printStackTrace();
			}
		}
		return immigrationTemplateDocs.getId();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void savePetitionTemplateTaskDetails(ImmigrationTemplateTasks immigrationTemplateTask) {
		logger.debug("Saving Temlate Task Details-->");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (immigrationTemplateTask != null) {
				session.saveOrUpdate(immigrationTemplateTask);
			}
			logger.debug("Template task details added Successfully-->");
		} catch (Exception exception) {
			StringBuilder message = new StringBuilder(MessageFormat.format(duplicateValue, "immigrationTemplateTask"));
			try {
				throw new SourceLeadConstraintViolationException(message.toString() + exception.getCause().toString());
			} catch (SourceLeadConstraintViolationException e) {
				e.printStackTrace();
			}
		}
	}

	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ImmigrationTemplateForms savePetitionTemplateFormDetails(ImmigrationTemplateForms immigrationTemplateForms) {
		logger.debug("Saving Temlate Task Details-->");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (immigrationTemplateForms != null) {
				session.saveOrUpdate(immigrationTemplateForms);
			}
			logger.debug("Template form details added Successfully-->");
		} catch (Exception exception) {
			StringBuilder message = new StringBuilder(MessageFormat.format(duplicateValue, "immigrationTemplateForms"));
			try {
				throw new SourceLeadConstraintViolationException(message.toString() + exception.getCause().toString());
			} catch (SourceLeadConstraintViolationException e) {
				e.printStackTrace();
			}
		}
		
		return immigrationTemplateForms;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void deletePetitionTemplateDocDetails(List<Long> remainingDocsIds) {
		logger.debug("Delete Template Docs-->");
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("delete from ImmigrationTemplateDocs docs where docs.immigrationTempId in (:templateIds").setParameterList("templateIds", remainingDocsIds);
		int count = query.executeUpdate();
		logger.debug("Number of records deleted-->"+count);
	}
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ImmigrationTemplateView> getAllTemplateDetails() {
		logger.debug("fetching template details-->");
		Session session = this.sessionFactory.getCurrentSession();
		List<ImmigrationTemplateView>  templateList= null;
		try {
			  Query query= session.createQuery("from ImmigrationTemplateView");
			  templateList = query.list();
			 
		} catch (Exception exception) {
			logger.error("Error Occured  while fetching template details-->");
			   exception.printStackTrace();
			}
		return templateList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Long> getTemplateDocList(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Long>  templateDocList= null;
		try {
			  Query query= session.createQuery("select templateDocs.id from ImmigrationTemplateDocs templateDocs where templateDocs.immigrationTempId.id = :templateId").setLong("templateId", id);
			  templateDocList = query.list();
		} catch (Exception exception) {
			logger.error("Error Occured  while fetching template details-->");
			   exception.printStackTrace();
			}
		return templateDocList;
	}
}
