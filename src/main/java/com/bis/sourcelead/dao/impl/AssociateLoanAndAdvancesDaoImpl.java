package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.bis.sourcelead.dao.AssociateLoanAndAdvancesDao;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.AssociateLoanAndAdvances;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PayrollLoanAndAdvances;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CommonUtil;
import com.ibm.icu.math.BigDecimal;

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
			Long orgLocationId,String type) throws Exception {
		  Session session = this.sessionFactory.getCurrentSession();
		  List<AssociateLoanAndAdvances> loanAndAdvList = null;
		  try {
			  String subQuery = "";
		         if(type.equalsIgnoreCase("outstanding")) {
		        	 subQuery = " and lad.loanAndAdvanceStatus = 'Outstanding'";
		         }else if(type.equalsIgnoreCase("Completed")) {
		        	 subQuery = " and lad.loanAndAdvanceStatus = 'Completed'";
		         }else if(type.equalsIgnoreCase("Requested")) {
		        	 subQuery = " and lad.loanAndAdvanceStatus = 'Requested'";
		         }
				 String query = "from AssociateLoanAndAdvances lad left join fetch lad.payrollLoanAndAdvanceId left join fetch lad.approverUser left join fetch lad.candidateId where "
				 		+ " lad.candidateId.id = :candidateId and lad.orgLocationId.id = :orgLocationId and lad.status = '1'  ";  
			     loanAndAdvList = session.createQuery(query.toString()+subQuery).setLong("candidateId", candidateId).setLong("orgLocationId", orgLocationId).list();
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
	     return loanAndAdvList;
	}
	
	@Override
	@Transactional
	public List<AssociateLoanAndAdvances> getAssociateLoanAndAdvancesListByCandidateIdByOrgLocationId(
			Long candidateId, Long orgLocation) {
		Session session = this.sessionFactory.getCurrentSession();
		List<AssociateLoanAndAdvances> loanAndAdvanceList = null;
		try {
			String query = "from AssociateLoanAndAdvances lads left join fetch lads.payrollLoanAndAdvanceId left join fetch lads.approverUser left join fetch lads.candidateId where "
			 		+ " lads.candidateId.id = :candidateId and lads.orgLocationId.id = :orgLocationId and lads.status = '1'  ";  
		     loanAndAdvanceList = session.createQuery(query.toString()).setLong("candidateId", candidateId).setLong("orgLocationId", orgLocation).list();
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		 
	     return loanAndAdvanceList;
	}

	@Override
	@Transactional
	public Map<String, Double> getEmpLoansAndAdvancesCountListByOrgLocationId(Long candidateId) {
		
		Map<String, Double> countMap = new HashMap<>();
		Session session = this.sessionFactory.getCurrentSession();
		String query = "";
			query = "from AssociateLoanAndAdvances loansAndAd where loansAndAd.candidateId =:candidateId and loansAndAd.loanAndAdvanceStatus = 'Outstanding' and loansAndAd.status = '1'";
			List<AssociateLoanAndAdvances> outstandingList = session.createQuery(query.toString()).setLong("candidateId", candidateId).list();
			Double outscount = outstandingList.stream().mapToDouble(l->l.getAmount()).sum();
			countMap.put("Outstanding", outscount);
			
			query = "from AssociateLoanAndAdvances loansAndAd where loansAndAd.candidateId =:candidateId and loansAndAd.loanAndAdvanceStatus = 'Completed' and loansAndAd.status = '1'";
			List<AssociateLoanAndAdvances> completedList = session.createQuery(query.toString()).setLong("candidateId", candidateId).list();
			Double Complcount = completedList.stream().mapToDouble(l->l.getAmount()).sum();
			countMap.put("Completed", Complcount);
			
			query = "from AssociateLoanAndAdvances loansAndAd where loansAndAd.candidateId =:candidateId and loansAndAd.loanAndAdvanceStatus = 'Requested' and loansAndAd.status = '1'";
			List<AssociateLoanAndAdvances> requestedList = session.createQuery(query.toString()).setLong("candidateId", candidateId).list();
			Double reqcount = requestedList.stream().mapToDouble(l->l.getAmount()).sum();
			countMap.put("Requested", reqcount);
	     
		return countMap;
	}

	@Override
	@Transactional
	public List<AssociateLoanAndAdvances> getAssociateLoanAndAdvancesListByUserIdAndLocationId(User user,
			OrgLocations orgLocation, String loanStatus) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		  List<AssociateLoanAndAdvances> loanAndAdvList = null;
		  try {
			  String subQuery = "";
		         if(loanStatus.equalsIgnoreCase("outstanding")) {
		        	 subQuery = " and lad.loanAndAdvanceStatus = 'Outstanding'";
		         }else if(loanStatus.equalsIgnoreCase("Completed")) {
		        	 subQuery = " and lad.loanAndAdvanceStatus = 'Completed'";
		         }else if(loanStatus.equalsIgnoreCase("Requested")) {
		        	 subQuery = " and lad.loanAndAdvanceStatus = 'Requested'";
		         }
				 String query = "from AssociateLoanAndAdvances lad left join fetch lad.payrollLoanAndAdvanceId left join fetch lad.approverUser left join fetch lad.candidateId left join fetch lad.candidateId.consultantUser where "
				 		+ " lad.candidateId.consultantUser.id = :userId and lad.orgLocationId.id = :orgLocationId and lad.status = '1'  ";  
			     loanAndAdvList = session.createQuery(query.toString()+subQuery).setLong("userId", user.getId()).setLong("orgLocationId", orgLocation.getId()).list();
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
	     return loanAndAdvList;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<AssociateLoanAndAdvances> getAssociateLoanAndAdvancesListByCandidateIdOrgLocationIdAndLoanStatus(
			Long candidateId, Long orgLocationId, List<String> loanStatus) {
		Session session = this.sessionFactory.getCurrentSession();
		  List<AssociateLoanAndAdvances> loanAndAdvList = null;
		  try {
				 String query = "from AssociateLoanAndAdvances lad where "
				 		+ " lad.candidateId.id = :candidateId and lad.orgLocationId.id = :orgLocationId and lad.status = '1' and lad.loanAndAdvanceStatus IN (:loanStatus) ";  
			     loanAndAdvList = session.createQuery(query.toString()).setLong("candidateId", candidateId).setLong("orgLocationId", orgLocationId).setParameterList("loanStatus", loanStatus).list();
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return loanAndAdvList;
	}

}
