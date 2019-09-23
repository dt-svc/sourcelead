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

import com.bis.sourcelead.dao.AssociateNomineeDao;
import com.bis.sourcelead.entity.AssociateNominee;
import com.bis.sourcelead.entity.AssociateSalary;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.entity.AssociateDeduction;

@Repository
public class AssociateNomineeDaoImpl implements AssociateNomineeDao {
	
	@Autowired
	SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(AssociateNomineeDaoImpl.class);

	@Override
	@Transactional
	public AssociateNominee saveAssociateNomineeDetails(AssociateNominee associateNominee) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (associateNominee != null) {
				session.saveOrUpdate(associateNominee);
			}
		} catch (Exception exception) { 
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return associateNominee;
	}

	@Override
	@Transactional
	public List<AssociateNominee> getAssociateNomineeDetails(Candidate candidate,OrgLocations orgLocation) {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<AssociateNominee> assNominee = null;
		try {
			List<AssociateNominee> associateNomineeList = session.createQuery("from AssociateNominee associateNominee where associateNominee.candidateId.id=:candidateId and associateNominee.orgLocationId=:orgLocationId and associateNominee.status = 1 order by associateNominee.id desc").setLong("candidateId", candidate.getId()).setLong("orgLocationId", orgLocation.getId()).list();
		 if(associateNomineeList !=null && associateNomineeList.size() >0) {
			return associateNomineeList;
		 }
		}catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return assNominee;
		
	}

	@Override
	@Transactional
	public AssociateNominee getNomineeById(Long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		AssociateNominee associateNominee = null;
		List<AssociateNominee>  associateNomineeList = session.createQuery("from AssociateNominee assNominee where assNominee.id=:nomineeId").setLong( "nomineeId", id).list();
		if(associateNomineeList !=null && associateNomineeList.size() >0) {
			associateNominee = associateNomineeList.get(0);
		}
		return associateNominee;
	}
	
}
