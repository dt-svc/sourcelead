package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.InvoiceDao;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.Invoice;

@Repository
public class InvoiceDaoImpl implements InvoiceDao {
	
	private static final Logger logger = LoggerFactory.getLogger(InvoiceDaoImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public Invoice saveInvoce(Invoice invoice) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (invoice != null) {
				session.saveOrUpdate(invoice);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return invoice;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Contracts> getSowcontractListByAssociateId(Long candidateId, Long orgLocationId, Long orgRelationId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Contracts contract left join fetch contract.requirementSubmission submission where contract.orgLocationId.id = :orgLocationId and contract.orgRelations.id = :orgRelationId and submission.candidate.id = :candidateId";
		return session.createQuery(query.toString()).setLong("candidateId", candidateId).setLong("orgLocationId", orgLocationId).setLong("orgRelationId", orgRelationId).setCacheable(true).list();
		 
	}
}
