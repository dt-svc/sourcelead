package com.bis.sourcelead.payroll.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.AssociateTaxDao;
import com.bis.sourcelead.payroll.dao.USPayrollAssociateTaxHistoryDao;
import com.bis.sourcelead.payroll.entity.AssociateBankData;
import com.bis.sourcelead.payroll.entity.AssociateTax;
import com.bis.sourcelead.payroll.entity.USPayrollAssociateTaxHistory;
import com.bis.sourcelead.payroll.view.AssociateTaxDedContriView;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;

@Repository
public class USPayrollAssociateTaxHistoryDaoImpl implements USPayrollAssociateTaxHistoryDao {
	private static final Logger logger = LoggerFactory.getLogger(USPayrollAssociateTaxHistoryDaoImpl.class);
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public USPayrollAssociateTaxHistory storeUSPayrollAssociateTaxHistory(USPayrollAssociateTaxHistory associateTax)
	{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (associateTax != null) {
				session.saveOrUpdate(associateTax);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return associateTax;
	}

	@Override
	@Transactional
	public List<USPayrollAssociateTaxHistory> getAssociateTaxHistoryByCandidateId(Long candidateId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<USPayrollAssociateTaxHistory> list = null;
		try {
			String query = "from USPayrollAssociateTaxHistory ath where ath.candidate = :candidateId order by ath.id desc";
			list = session.createQuery(query).setLong("candidateId", candidateId).setCacheable(false).list();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return list;
	}
	
	
}
