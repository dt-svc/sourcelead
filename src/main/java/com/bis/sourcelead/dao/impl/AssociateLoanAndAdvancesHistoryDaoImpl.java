package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.AssociateLoanAndAdvancesHistoryDao;
import com.bis.sourcelead.entity.AssociateLoanAndAdvanceHistory;
import com.bis.sourcelead.entity.AssociateLoanAndAdvances;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class AssociateLoanAndAdvancesHistoryDaoImpl implements AssociateLoanAndAdvancesHistoryDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	CommonUtil commonUtil;
	
	
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

	private static final Logger logger = LoggerFactory.getLogger(PayrollLoanAndAdvancesDaoImpl.class);

	@Override
	@Transactional
	public AssociateLoanAndAdvanceHistory getLoanAndAdvanceHistoryByCandidateIdAndLocationIdAndAdvanceId(Long loanAndAdvanceId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from AssociateLoanAndAdvanceHistory ala where ala.associateLoanAndAdvances.id = :loanAndAdvanceId order by ala.schedule desc";  
		List<AssociateLoanAndAdvanceHistory> associateLoanAndAdvancesList =  session.createQuery(query).setLong("loanAndAdvanceId", loanAndAdvanceId).list();
		if (associateLoanAndAdvancesList != null && associateLoanAndAdvancesList.size() > 0) {
			return associateLoanAndAdvancesList.get(0);
		}else {
			return null;
		}
	}

	@Override
	@Transactional
	public AssociateLoanAndAdvanceHistory saveAssociateLoanAndAdvancesHistory(AssociateLoanAndAdvanceHistory assLoanHistory)
	{
		logger.debug("Adding AssociateLoanAndAdvanceHistory");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (assLoanHistory != null) {
				session.saveOrUpdate(assLoanHistory);
			}
			logger.debug("AssociateLoanAndAdvanceHistory Added Successfully");
		} catch (ConstraintViolationException exception) {
			StringBuffer message = new StringBuffer(MessageFormat.format(duplicateValue, "ASSOCIATE_LOAN_ADVANCES_HISTORY"));
			try {
				throw new SourceLeadConstraintViolationException(message.toString()
						+ exception.getCause().toString());
			} catch (SourceLeadConstraintViolationException e) {
				e.printStackTrace();
			}
		}
		return assLoanHistory;
	}

	@Override
	@Transactional
	public List<AssociateLoanAndAdvanceHistory> getLoanAndAdvanceHistoryByCandidateIdAndLocationIdAndAdvanceLoanId(
			Long loanAndAdvanceId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<AssociateLoanAndAdvanceHistory> associateLoanAndAdvancesList = null;
		try {
			String query = "from AssociateLoanAndAdvanceHistory ala where ala.associateLoanAndAdvances.id = :loanAndAdvanceId order by ala.schedule desc";  
			associateLoanAndAdvancesList =  session.createQuery(query).setLong("loanAndAdvanceId", loanAndAdvanceId).list();
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return associateLoanAndAdvancesList;
	}


}
