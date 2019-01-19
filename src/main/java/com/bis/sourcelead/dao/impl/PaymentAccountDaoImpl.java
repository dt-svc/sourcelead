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

import com.bis.sourcelead.dao.PaymentAccountDao;
import com.bis.sourcelead.entity.PaymentAccount;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class PaymentAccountDaoImpl implements PaymentAccountDao{
	
	
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
	

	private static final Logger logger = LoggerFactory.getLogger(PaymentAccountDaoImpl.class);
	

	@Override
	@Transactional
	public PaymentAccount savePaymentAccount(PaymentAccount paymentAccount) throws Exception {
		logger.debug("Adding PaymentAccount");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (paymentAccount != null) {
				session.saveOrUpdate(paymentAccount);
			}
			logger.debug("PaymentAccount Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PAYMENT_ACCOUNT"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return paymentAccount;
	}

	@Override
	@Transactional
	public PaymentAccount getPaymentAccountById(Long id) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<PaymentAccount> paymentAccount = new ArrayList<PaymentAccount>();
		logger.debug("getPaymentAccountById start");
		paymentAccount = session.createQuery("from PaymentAccount where id=:id" ).setLong( "id", id ).list();
		logger.debug("getPaymentAccountById end");
		if (paymentAccount.size() > 0) {
			return paymentAccount.get(0);
		} else {
			return null;
		}
   	}

	@Override
	@Transactional
	public PaymentAccount getPaymentAccountDetailsByCandidateId(Long candidateId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<PaymentAccount> paymentAccount = new ArrayList<PaymentAccount>();
		logger.debug("getPaymentAccountDetailsByCandidateId start");
		paymentAccount = session.createQuery("from PaymentAccount pa left join fetch pa.candidateId where pa.candidateId.id = :candidateId" ).setLong( "candidateId", candidateId ).list();
		logger.debug("getPaymentAccountDetailsByCandidateId end");
		if (paymentAccount.size() > 0) {
			return paymentAccount.get(0);
		} else {
			return null;
		}
   	}

	@Override
	@Transactional
	public boolean getisActivePaymentAccountExistedByCandidateId(Long candidateId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<PaymentAccount> paymentAccount = new ArrayList<PaymentAccount>();
		boolean  flag = false;
		logger.debug("getisActivePaymentAccountExistedByCandidateId start");
		paymentAccount = session.createQuery("from PaymentAccount pa left join fetch pa.candidateId where pa.candidateId.id = :candidateId and pa.defaultAccount = :flag " ).setLong( "candidateId", candidateId ).setBoolean("flag", true).list();
		logger.debug("getisActivePaymentAccountExistedByCandidateId end");
		if (paymentAccount.size() > 0) {
			flag = true;
		} 
		return flag;
   	}

}
