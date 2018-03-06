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

import com.bis.sourcelead.dao.UserSignatureDao;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserSignature;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class UserSignatureDaoImpl implements UserSignatureDao{
	
	
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

	private static final Logger logger = LoggerFactory.getLogger(UserSignatureDaoImpl.class);

	@Override
	@Transactional
	public UserSignature saveUserSignature(UserSignature userSignature) throws Exception {
		
		logger.debug("Storing saveUserSignature");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (userSignature != null) {
				session.saveOrUpdate(userSignature);
			}
			logger.debug("userSignature Stored Successfully");
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"userSignature"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return userSignature;
		
	}

	@Override
	@Transactional
	public List<UserSignature> getUserSignatureListByUserIdAndLocationId(Long userId, Long orgLocationId)
			throws Exception {
		
		 Session session = this.sessionFactory.getCurrentSession();
		 String query = "from UserSignature signature where signature.userId.id = :userId and signature.orgLocationId.id = :orgLocationId  ";
		 List<UserSignature> signaturesList = session.createQuery(query.toString()).setLong( "userId", userId).setLong( "orgLocationId", orgLocationId).list();
		 return signaturesList;
	}

	@Override
	@Transactional
	public UserSignature getUserSignatureById(Long Id) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<UserSignature> userSignature = new ArrayList<UserSignature>();
		logger.debug("getUserSignatureById start");
		userSignature = session.createQuery("from UserSignature signature where signature.id = :id" ).setLong("id", Id).list();
		logger.debug("findByEmailAndOrgLocationsId end");
		if (userSignature.size() > 0) {
			return userSignature.get(0);
		} else {
			return null;
		}
   	}

	
}
