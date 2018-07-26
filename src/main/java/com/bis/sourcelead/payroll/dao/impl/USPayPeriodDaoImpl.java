package com.bis.sourcelead.payroll.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.payroll.dao.USPayPeriodDao;
import com.bis.sourcelead.payroll.entity.USPayPeriod;

@Repository
public class USPayPeriodDaoImpl implements USPayPeriodDao{

	
	private static final Logger logger = LoggerFactory.getLogger(USPayPeriodDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public USPayPeriod getPayPeriodNumber(Long payFrequencyId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		USPayPeriod uSPayPeriod = null;
		try {
			String query = "from USPayPeriod ppn where ppn.payPeriodNum = (select MAX(paypn.payPeriodNum) from USPayPeriod paypn)";
			uSPayPeriod = (USPayPeriod) session.createQuery(query).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uSPayPeriod;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public USPayPeriod savePayPeriodNumber(USPayPeriod uSPayPeriod)
	{
		Session session = this.sessionFactory.getCurrentSession();
		if (uSPayPeriod != null) {
			session.saveOrUpdate(uSPayPeriod);
		}
		return uSPayPeriod;
	}
}
