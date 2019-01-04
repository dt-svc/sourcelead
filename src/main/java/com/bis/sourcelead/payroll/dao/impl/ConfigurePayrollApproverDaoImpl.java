package com.bis.sourcelead.payroll.dao.impl;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.ConfigurePayrollApproverDao;
import com.bis.sourcelead.payroll.entity.ConfigurePayrollApprover;
import com.bis.sourcelead.payroll.entity.PayrollApprover;

@Repository
public class ConfigurePayrollApproverDaoImpl implements ConfigurePayrollApproverDao{

	
	private static final Logger logger = LoggerFactory.getLogger(ConfigurePayrollApproverDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ConfigurePayrollApprover savePayrollApprover(ConfigurePayrollApprover confPayApprover)
	{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(confPayApprover);
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return confPayApprover;
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ConfigurePayrollApprover getConfigurePayrollApproverByLoc(OrgLocations orgLocation)
	{
		Session session = this.sessionFactory.getCurrentSession();
		ConfigurePayrollApprover configurePayrollApprover = null;
		try {
			String query = "from ConfigurePayrollApprover cpa where cpa.orgLocation.id =:orgLocationId";
			configurePayrollApprover = (ConfigurePayrollApprover) session.createQuery(query).setLong("orgLocationId", orgLocation.getId()).uniqueResult();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return configurePayrollApprover;
	}

	@Override
	@Transactional
	public int deletePayrollApprover(String confAprroverId)
	{
			int isDeleted = 0;
			try {
				Session session = this.sessionFactory.getCurrentSession();
				String query = "delete from PayrollApprover payrollAppr where payrollAppr.configurePayrollApprover.id =:confAprroverId ";
				Query qry = session.createQuery(query).setLong("confAprroverId", Long.parseLong(confAprroverId));
				int i = qry.executeUpdate();
				return i;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return isDeleted;

		
	}

	@Override
	@Transactional
	public List<PayrollApprover> getPayrollApproversByCPAID(Long configurePayrollApproverId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<PayrollApprover> payrollApprover = null;
		try {
			String query = "from PayrollApprover pa where pa.configurePayrollApprover.id =:configurePayrollApproverId";
			payrollApprover = session.createQuery(query).setLong("configurePayrollApproverId", configurePayrollApproverId).list();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return payrollApprover;
	}

	@Override
	@Transactional
	public boolean isLoginUserIsPayRollApprover(Long userId, Long orgLocationsId) {
		Session session = this.sessionFactory.getCurrentSession();
		boolean flag = false;
		List<PayrollApprover> payrollApproverList = null;
		try {
			String query = "from PayrollApprover pa where pa.user = :userId and pa.status = '1' and "
					+ " pa.configurePayrollApprover.id IN (select cpa.id from ConfigurePayrollApprover cpa where cpa.orgLocation.id = :orgLocationsId and cpa.status = '1' )";
			payrollApproverList = session.createQuery(query).setLong("userId", userId).setLong("orgLocationsId", orgLocationsId).list();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		if(payrollApproverList != null && !payrollApproverList.isEmpty()) {
			flag = true;
		}
		return flag;
	}

	@Override
	@Transactional
	public ConfigurePayrollApprover getConfigurePayrollApproverByLocAndStatus(OrgLocations orgLocation)
	{
		Session session = this.sessionFactory.getCurrentSession();
		ConfigurePayrollApprover configurePayrollApprover = null;
		try {
			String query = "from ConfigurePayrollApprover cpa where cpa.orgLocation.id =:orgLocationId and cpa.automaticPayroll = 'YES'";
			configurePayrollApprover = (ConfigurePayrollApprover) session.createQuery(query).setLong("orgLocationId", orgLocation.getId()).uniqueResult();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return configurePayrollApprover;
	}
	
}
