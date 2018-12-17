package com.bis.sourcelead.payroll.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.payroll.dao.PayrollAdjustmentDao;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;

@Repository
public class PayrollAdjustmentDaoImpl implements PayrollAdjustmentDao{
	
	private static final Logger logger = LoggerFactory.getLogger(PayrollAdjustmentDaoImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public EmployeePayroll getEmployeePayrollByUUID(String payrollUUID)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from EmployeePayroll epr where epr.usEmpPayrollUUID = :payrollUUID"; 
		EmployeePayroll  payroll = (EmployeePayroll) session.createQuery(query).setString("payrollUUID", payrollUUID).uniqueResult();
		return payroll;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public EmployeePayroll updateEmployeePayroll(EmployeePayroll payroll)
	{
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(payroll);	
		}catch (Exception e) {
			e.printStackTrace();
		}
		return payroll;
	}

}
