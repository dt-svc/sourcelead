package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.SourceleadSignatureDao;
import com.bis.sourcelead.entity.SourceleadSignature;

@Repository
public class SourceleadSignatureDaoImpl implements SourceleadSignatureDao{
	
	
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

	private static final Logger logger = LoggerFactory.getLogger(SourceleadSignatureDaoImpl.class);

	@Override
	@Transactional
	public List<SourceleadSignature> getSourceLeadSignature() throws Exception {
		
		 Session session = this.sessionFactory.getCurrentSession();
		 String query = "from SourceleadSignature signature ";
		 List<SourceleadSignature> signatureList = session.createQuery(query.toString()).setCacheable(true).list();
		 return signatureList;
	}

}
