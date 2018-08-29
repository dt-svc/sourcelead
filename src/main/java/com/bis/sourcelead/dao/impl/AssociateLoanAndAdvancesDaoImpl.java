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

import com.bis.sourcelead.dao.AssociateLoanAndAdvancesDao;
import com.bis.sourcelead.entity.AssociateLoanAndAdvances;
import com.bis.sourcelead.entity.PayrollLoanAndAdvances;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class AssociateLoanAndAdvancesDaoImpl implements AssociateLoanAndAdvancesDao{
	
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
	public AssociateLoanAndAdvances saveAssociateLoanAndAdvances(AssociateLoanAndAdvances associateLoanAndAdvances)
			throws Exception {
		
		logger.debug("Adding AssociateLoanAndAdvances");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (associateLoanAndAdvances != null) {
				session.saveOrUpdate(associateLoanAndAdvances);
			}
			logger.debug("associateLoanAndAdvances Added Successfully");
		} catch (ConstraintViolationException exception) {
			StringBuffer message= new StringBuffer(MessageFormat.format(duplicateValue,"ASSOCIATE_LOAN_ADVANCES"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return associateLoanAndAdvances;
	}

	@Override
	@Transactional
	public AssociateLoanAndAdvances getAssociateLoanAndAdvancesById(Long id) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from AssociateLoanAndAdvances lad where lad.id = :id ";  
		List<AssociateLoanAndAdvances> associateLoanAndAdvancesList =  session.createQuery(query).setLong("id", id).list();
		AssociateLoanAndAdvances associateLoanAndAdvances = null;
		if (associateLoanAndAdvancesList != null && associateLoanAndAdvancesList.size() > 0) {
			associateLoanAndAdvances = (AssociateLoanAndAdvances) associateLoanAndAdvancesList.iterator().next();
		}
		return associateLoanAndAdvances;
	}

	@Override
	@Transactional
	public AssociateLoanAndAdvances getAssociateLoanAndAdvancesDetailsByCandidateId(Long candidateId,
			Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from AssociateLoanAndAdvances lad left join fetch lad.payrollLoanAndAdvanceId left join fetch lad.approverUser "
				+ " where lad.candidateId.id = :candidateId and lad.orgLocationId.id = :orgLocationId and lad.status = '1' ";  
		List<AssociateLoanAndAdvances> associateLoanAndAdvancesList =  session.createQuery(query).setLong("candidateId", candidateId).setLong("orgLocationId", orgLocationId).list();
		AssociateLoanAndAdvances associateLoanAndAdvances = null;
		if (associateLoanAndAdvancesList != null && associateLoanAndAdvancesList.size() > 0) {
			associateLoanAndAdvances = (AssociateLoanAndAdvances) associateLoanAndAdvancesList.iterator().next();
		}
		return associateLoanAndAdvances;
	}

	@Override
	@Transactional
	public List<AssociateLoanAndAdvances> getAssociateLoanAndAdvancesListByCandidateId(Long candidateId,
			Long orgLocationId) throws Exception {

		 Session session = this.sessionFactory.getCurrentSession();
		 String query = "from AssociateLoanAndAdvances lad left join fetch lad.payrollLoanAndAdvanceId left join fetch lad.approverUser left join fetch lad.candidateId where "
		 		+ " lad.candidateId.id = :candidateId and lad.orgLocationId.id = :orgLocationId and lad.status = '1'  ";  
	     List<AssociateLoanAndAdvances> loanAndAdvList = session.createQuery(query.toString()).setLong("candidateId", candidateId).setLong("orgLocationId", orgLocationId).list();
	     return loanAndAdvList;
	}

}
