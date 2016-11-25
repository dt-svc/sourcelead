package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.BoPaymentTypesDao;
import com.bis.sourcelead.entity.BoPaymentTypes;

@Repository
public class BoPaymentTypesDaoImpl implements BoPaymentTypesDao {
	
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

	@Override
	@Transactional
	public List<BoPaymentTypes> getAllBoPaymentTypes() throws Exception {
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from BoPaymentTypes"; 
		List<BoPaymentTypes> boPaymentTypesList = session.createQuery(query).setCacheable(true).list();
		return boPaymentTypesList;
	}

}
