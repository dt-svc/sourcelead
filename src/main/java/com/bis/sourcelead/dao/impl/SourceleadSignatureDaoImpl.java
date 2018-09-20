package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
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
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

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
	public List<SourceleadSignature> getSourceLeadSignatureByLocationId(Long orgLocationId) throws Exception {
		
		 Session session = this.sessionFactory.getCurrentSession();
		 String query = "from SourceleadSignature signature where signature.orgLocationId.id = :orgLocationId";
		 List<SourceleadSignature> signatureList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).list();
		 return signatureList;
		 
	}

	@Override
	@Transactional
	public SourceleadSignature saveSourceleadSignature(SourceleadSignature signature) throws Exception {
		
		logger.debug("Adding SourceleadSignature");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (signature != null) {
				session.saveOrUpdate(signature);
			}
			logger.debug("SourceleadSignature Added Successfully");
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"SourceleadSignature"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return signature;
	}

	@Override
	@Transactional
	public List<SourceleadSignature> getSourceLeadSignature() throws Exception {
		
		 Session session = this.sessionFactory.getCurrentSession();
		 String query = "from SourceleadSignature signature where signature.orgLocationId IS NULL";
		 List<SourceleadSignature> signatureList = session.createQuery(query.toString()).list();
		 return signatureList;
	}

	@Override
	@Transactional
	public SourceleadSignature getSourceleadSignatureByLabelNameAndOrgLocationId(String labelName, Long orgLocationId)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<SourceleadSignature> sourceleadSignature = new ArrayList<SourceleadSignature>();
		logger.debug("getSourceleadSignatureByLabelNameAndOrgLocationId user code start");
		sourceleadSignature = session.createQuery("from SourceleadSignature signature where signature.labelName = :labelName and signature.orgLocationId.id = :orgLocationId" ).setString("labelName", labelName).setLong("orgLocationId", orgLocationId).list();
		logger.debug("getSourceleadSignatureByLabelNameAndOrgLocationId user code end");
		if (sourceleadSignature.size() > 0) {
			return sourceleadSignature.get(0);
		} else {
			return null;
		}
}

}
