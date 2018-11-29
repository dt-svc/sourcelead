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

import com.bis.sourcelead.dao.OrglocationDepositTaxesDao;
import com.bis.sourcelead.entity.OrglocationDepositTaxes;
import com.bis.sourcelead.entity.OrglocationFormSubFields;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class OrglocationDepositTaxesDaoImpl implements OrglocationDepositTaxesDao{
	
	@Autowired
	SessionFactory sessionFactory;
	
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
	
	private static final Logger logger = LoggerFactory.getLogger(OrglocationStateTaxDaoImpl.class);

	@Override
	@Transactional
	public OrglocationDepositTaxes saveOrglocationDepositTaxes(OrglocationDepositTaxes orglocationDepositTaxes) throws Exception {
		
		logger.debug("Adding orglocationDepositTaxes");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (orglocationDepositTaxes != null) {
				session.saveOrUpdate(orglocationDepositTaxes);
			}
			logger.debug("orglocationDepositTaxes Added Successfully");
		} catch (ConstraintViolationException exception) {
			StringBuffer message= new StringBuffer(MessageFormat.format(duplicateValue,"ORGLOCATION_DEPOSIT_TAXES"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return orglocationDepositTaxes;
	}

	@Override
	@Transactional
	public OrglocationDepositTaxes getOrglocationDepositTaxesByOrgLocationId(Long locationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from OrglocationDepositTaxes orglocDepTaxes where orglocDepTaxes.orgLocationId =:orgLocationId";
		List<OrglocationDepositTaxes> orglocationDepositTaxes = session.createQuery(query).setLong("orgLocationId", locationId).list();
		if(orglocationDepositTaxes != null && orglocationDepositTaxes.size() > 0) {
			return orglocationDepositTaxes.get(0);
		}else {
			return null;
		}
		
	}

}
