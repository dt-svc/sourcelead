package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.PayrollTaxDao;
import com.bis.sourcelead.entity.PayrollTax;

@Repository
@Transactional
public class PayrollTaxDaoImpl implements PayrollTaxDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public long savePayrollTaxConfiguration(PayrollTax payrollTax) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(payrollTax);
			return payrollTax.getId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public PayrollTax getPayrollTaxById(long id) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			PayrollTax payrollTax = session.get(PayrollTax.class, id);
			return payrollTax;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PayrollTax getPayrollTaxConfigurationByOrgLocationId(Long orgLocationId, String financialYear) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from PayrollTax payrollTax where payrollTax.orgLocations.id =:orgLocationId and payrollTax.financialYear = :financialYear";
			Query queryObj = session.createQuery(query);
			queryObj.setLong("orgLocationId", orgLocationId);
			queryObj.setString("financialYear", financialYear);
			List<PayrollTax> payrollTaxList = queryObj.list();
			if (payrollTaxList != null && !payrollTaxList.isEmpty()) {
				return payrollTaxList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public PayrollTax getPayrollTaxConfigurationByOrgLocationId(Long orgLocationId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from PayrollTax payrollTax where payrollTax.orgLocations.id =:orgLocationId ORDER by payrollTax.createdDate DESC ";
			Query queryObj = session.createQuery(query);
			queryObj.setLong("orgLocationId", orgLocationId);
			List<PayrollTax> payrollTaxList = queryObj.list();
			if (payrollTaxList != null && !payrollTaxList.isEmpty()) {
				return payrollTaxList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
