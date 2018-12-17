package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.OrgLocationInformationDetailsDao;
import com.bis.sourcelead.entity.OrgLocationInformationDetails;
import com.bis.sourcelead.entity.OrglocationBankingDetails;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class OrgLocationInformationDetailsDaoImpl implements OrgLocationInformationDetailsDao{

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

	private static final Logger logger = LoggerFactory.getLogger(OrgLocationInformationDetailsDaoImpl.class);

	@Override
	@Transactional
	public OrgLocationInformationDetails saveOrgLocationInformationDetails(
			OrgLocationInformationDetails orgLocationInformationDetails) throws Exception {
		
		
		logger.debug("Adding orgLocationInformationDetails");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (orgLocationInformationDetails != null) {
				session.saveOrUpdate(orgLocationInformationDetails);
			}
			logger.debug("orgLocationInformationDetails Added Successfully");
		} catch (ConstraintViolationException exception) {
			StringBuffer message= new StringBuffer(MessageFormat.format(duplicateValue,"ORGLOCATION_INFORMATION_DETAILS"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return orgLocationInformationDetails;
	}

	@Override
	@Transactional
	public OrgLocationInformationDetails getOrgLocationInformationDetailsByOrgLocationId(Long orgLocationId)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from OrgLocationInformationDetails informationDetails where informationDetails.orgLocationId = :orgLocationId ";  
		List<OrgLocationInformationDetails> orgLocationInformationDetailsList =  session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		OrgLocationInformationDetails orgLocationInformationDetails = null;
		if (orgLocationInformationDetailsList != null && orgLocationInformationDetailsList.size() > 0) {
			orgLocationInformationDetails = (OrgLocationInformationDetails) orgLocationInformationDetailsList.iterator().next();
		}
		return orgLocationInformationDetails;
	}
}
