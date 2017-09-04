package com.bis.sourcelead.payroll.dao.impl;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.AccountDeductionsDao;
import com.bis.sourcelead.payroll.entity.AccountDeductions;
import com.bis.sourcelead.payroll.entity.AccountDeductionsDetails;
import com.bis.sourcelead.payroll.entity.WageTaxSummary;

@Repository
public class AccountDeductionsDaoImpl implements AccountDeductionsDao{
	
	private static final Logger logger = LoggerFactory.getLogger(AccountDeductionsDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public AccountDeductions saveAccountDeductions(AccountDeductions payCycle)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(payCycle);
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return payCycle;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<AccountDeductions> getAccountDeductionsListByOrgLocationId(OrgLocations orgLocation) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<AccountDeductions> accountDeductionslist = null;
		String query = "from AccountDeductions ad left join fetch ad.orgLocationId where ad.orgLocationId.id =:orgLocationId";
		accountDeductionslist = session.createQuery(query).setLong("orgLocationId", orgLocation.getId()).setCacheable(true).list();
		if(accountDeductionslist != null && accountDeductionslist.size() >0)
		{
			return accountDeductionslist;
		}
		return accountDeductionslist;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<AccountDeductionsDetails> getDeductionsDetailsbyId(Long deductionId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<AccountDeductionsDetails> accountDeductionslist = null;
		String query = "from AccountDeductionsDetails ad left join fetch ad.accountDeductions where ad.accountDeductions.id =:deductionId";
		accountDeductionslist = session.createQuery(query).setLong("deductionId", deductionId).setCacheable(true).list();
		if(accountDeductionslist != null && accountDeductionslist.size() >0)
		{
			return accountDeductionslist;
		}
		return accountDeductionslist;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public AccountDeductions getDeductionsbyId(Long deductionId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		AccountDeductions accountDeductions = null;
		String query = "from AccountDeductions ad where ad.id =:deductionId";
		List<AccountDeductions> accountDeductionslist = session.createQuery(query).setLong("deductionId", deductionId).setCacheable(true).list();
		if(accountDeductionslist != null && accountDeductionslist.size() >0)
		{
			accountDeductions = accountDeductionslist.get(0);
		}
		return accountDeductions;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<AccountDeductions> getAccountDeductionsByEmployeeId(OrgLocations orgLocations, Long empId)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<AccountDeductions> accountDeductionslist = null;
		String query = "from AccountDeductions ad left join fetch ad.orgLocationId where ad.orgLocationId.id =:orgLocationId and ad.employeeId = :empId";
		accountDeductionslist = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).setLong("empId", empId).setCacheable(true).list();
		if(accountDeductionslist != null && accountDeductionslist.size() >0)
		{
			return accountDeductionslist;
		}
		return accountDeductionslist;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public int getAccountDeductionsVersionByLocationId(Long locationId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "SELECT MAX(acc.version)  FROM AccountDeductions acc where acc.orgLocationId.id =:orgLocationId";
		List<Integer> version = session.createQuery(query).setLong("orgLocationId", locationId).setCacheable(true).list();
		Integer count = 0;
		if(version != null && !version.isEmpty() && version.get(0) != null){
				count = version.get(0).intValue();
		}
		return count;
	}

}
