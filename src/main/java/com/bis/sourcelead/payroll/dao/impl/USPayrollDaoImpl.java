package com.bis.sourcelead.payroll.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.payroll.dao.USPayrollDao;

@Repository
public class USPayrollDaoImpl implements USPayrollDao {
	
	private static final Logger logger = LoggerFactory.getLogger(USPayrollDaoImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<LookUpData> findAllDeduction()
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from LookUpData lookupData where lookupData.category =:category";
		return session.createQuery(query).setString("category", Constants.PAYROLL_CATEGORY).list();
	}
	
}
