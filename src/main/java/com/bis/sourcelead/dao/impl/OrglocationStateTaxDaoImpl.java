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

import com.bis.sourcelead.dao.OrglocationStateTaxDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrglocationStateTax;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class OrglocationStateTaxDaoImpl implements OrglocationStateTaxDao{
	
	
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

	private static final Logger logger = LoggerFactory.getLogger(OrglocationStateTaxDaoImpl.class);

	@Override
	@Transactional
	public OrglocationStateTax saveOrglocationStateTax(OrglocationStateTax orglocationStateTax) throws Exception {
		
		
		logger.debug("Adding orglocationStateTax");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (orglocationStateTax != null) {
				session.saveOrUpdate(orglocationStateTax);
			}
			logger.debug("orglocationStateTax Added Successfully");
		} catch (ConstraintViolationException exception) {
			StringBuffer message= new StringBuffer(MessageFormat.format(duplicateValue,"ORGLOCATION_STATE_TAX"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return orglocationStateTax;
	}

	@Override
	@Transactional
	public List<OrglocationStateTax> getOrglocationStateTaxListByOrgLocationId(Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();

		String query = "from OrglocationStateTax orglocationStateTax where orglocationStateTax.orgLocationId.id = :orgLocationId and orglocationStateTax.status = '1' ";
		
		List<OrglocationStateTax> orglocationStateTaxList = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		return orglocationStateTaxList;
	}

	@Override
	@Transactional
	public OrglocationStateTax getOrglocationStateTaxByUUID(String UUID) throws Exception {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from OrglocationStateTax orglocationStateTax  where orglocationStateTax.uuid = :UUID and orglocationStateTax.status = '1' ";  
		List<OrglocationStateTax> orglocationStateTaxList =  session.createQuery(query).setString("UUID", UUID).list();
		OrglocationStateTax orglocationStateTax = null;
		if (orglocationStateTaxList != null && orglocationStateTaxList.size() > 0) {
			orglocationStateTax = (OrglocationStateTax) orglocationStateTaxList.iterator().next();
		}
		return orglocationStateTax;
	}

	@Override
	@Transactional
	public void deleteOrglocationStateTax(OrglocationStateTax orglocationStateTax) throws Exception {
		
		logger.debug("Deleting orglocationStateTax");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (orglocationStateTax != null) {
				session.delete(orglocationStateTax);
			}
			logger.debug("orglocationStateTax deleted Successfully");
		} catch (ConstraintViolationException exception) {
			StringBuffer message= new StringBuffer(MessageFormat.format(duplicateValue,"ORGLOCATION_STATE_TAX"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		
	}

}
