package com.bis.sourcelead.dao.impl;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.PaymentAccountDao;
import com.bis.sourcelead.dto.AssociateBalanceLeaveDTO;
import com.bis.sourcelead.dto.IndAssociatePaymentAccountDTO;
import com.bis.sourcelead.dto.TotalLeaveDTO;
import com.bis.sourcelead.entity.PaymentAccount;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.payroll.dto.AssociatePfEsiDetailsDTO;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

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
		paymentAccount = session.createQuery("from PaymentAccount pa left join fetch pa.candidateId where pa.candidateId.id = :candidateId and pa.defaultAccount = 1 " ).setLong( "candidateId", candidateId ).list();
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


     
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<PaymentAccount> getPaymentAccountDetailsByOrglocationId(Long id)  throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from  PaymentAccount payment where payment.orgLocation.id =:id";
		List<PaymentAccount> paymentList = session.createQuery(query).setLong("id", id).list();
		return paymentList;
	}

	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public int saveBankingDetailInPaymentAccount(Long orgLocationId, List<IndAssociatePaymentAccountDTO> list,	Map<String, Long> canMap) throws Exception {
		List<Integer> listCount = new ArrayList<>();
		int totalCount = 0;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.doWork(new Work() {
				@Override
				public void execute(java.sql.Connection connection) throws SQLException {
					boolean initialAutocommitSetting = connection.getAutoCommit();
					connection.setAutoCommit(false);
					CallableStatement stmt = connection.prepareCall("{call insertAssociateBankingDetails(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
					for (IndAssociatePaymentAccountDTO paymentlist : list) {	
						Long candidateId = canMap.get(paymentlist.getEmail().trim());
							stmt.setLong(1, candidateId);
							stmt.setString(2,   paymentlist.getEmail());
							stmt.setString(3,   paymentlist.getAccountType());
							stmt.setString(4,   paymentlist.getBankName());
							stmt.setString(5,   paymentlist.getAccountNumber());
							stmt.setString(6,   paymentlist.getBsrNumber());
							stmt.setString(7,   paymentlist.getMicrCode());
							stmt.setString(8,   paymentlist.getRoutingNumber());
							stmt.setString(9,   paymentlist.getFirstName());
							stmt.setString(10,  paymentlist.getLastName());	
							stmt.setBoolean(11, false);	
							stmt.setBoolean(12, false);
							stmt.setBoolean(13, false);
							stmt.setString(14,  orgLocationId.toString());	
							stmt.addBatch();
					}
					int[] updateCount = stmt.executeBatch();
					listCount.add(updateCount.length);
					connection.commit();
					connection.setAutoCommit(initialAutocommitSetting);
				}
			});
			if(listCount.size() >0) {
				totalCount = 0;
			}
			return totalCount;
		} catch (Exception e) {
			e.printStackTrace();
			return totalCount;
		}
	}

}
