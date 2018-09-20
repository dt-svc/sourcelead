package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.OrgBankDetailsDao;
import com.bis.sourcelead.entity.OrgBankDetails;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class OrgBankDetailsDaoImpl implements OrgBankDetailsDao{

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
	

	private static final Logger logger = LoggerFactory.getLogger(OrgBankDetailsDaoImpl.class);


	@Override
	@Transactional
	public OrgBankDetails saveOrgBankDetails(OrgBankDetails orgBankDetails) throws Exception {
	
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (orgBankDetails != null) {
				session.saveOrUpdate(orgBankDetails);
			}
			logger.debug("OrgBankDetails Saved Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"ORG_BANK_DETAILS"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return orgBankDetails;
	}


	@Override
	@Transactional
	public OrgBankDetails getOrgBankDetailsByOrgLocationId(Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<OrgBankDetails> bankDetails = session.createQuery("from OrgBankDetails bankDetails left join fetch bankDetails.orgLocationsId  where bankDetails.orgLocationsId.id = :orgLocationId" ).setLong( "orgLocationId", orgLocationId ).list();
		if (bankDetails.size() > 0) {
			return bankDetails.get(0);
		} else {
			return null;
		}
	}


	@Override
	@Transactional
	public OrgBankDetails getOrgBankDetailsById(Long id) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<OrgBankDetails> bankDetails = session.createQuery("from OrgBankDetails bankDetails left join fetch bankDetails.orgLocationsId  where bankDetails.id = :id" ).setLong( "id", id ).list();
		if (bankDetails.size() > 0) {
			return bankDetails.get(0);
		} else {
			return null;
		}
	}
	
	
}
