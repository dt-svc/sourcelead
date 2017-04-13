package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ContractsTemplateDao;
import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class ContractsTemplateDaoImpl implements ContractsTemplateDao{
	@Autowired
	private SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(ContractsTemplateDaoImpl.class);
	
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
	
	@Override
	@Transactional
	public ContractsTemplate saveContractTemplate(ContractsTemplate contractsTemplate) throws Exception {
		logger.debug("Adding OrgSpoc");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (contractsTemplate != null) {
				session.saveOrUpdate(contractsTemplate);
			}
			logger.debug("contractsTemplate Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"ORG_SPOC"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return contractsTemplate;
	}
	
}
