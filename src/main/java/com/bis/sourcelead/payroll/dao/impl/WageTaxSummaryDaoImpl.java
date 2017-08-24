package com.bis.sourcelead.payroll.dao.impl;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.WageTaxSummaryDao;
import com.bis.sourcelead.payroll.entity.EmployerPayrollCost;
import com.bis.sourcelead.payroll.entity.WageTaxSummary;


@Repository
public class WageTaxSummaryDaoImpl implements WageTaxSummaryDao {

	private static final Logger logger = LoggerFactory.getLogger(WageTaxSummaryDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public WageTaxSummary saveWageTaxSummary(WageTaxSummary wageTaxSummary) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(wageTaxSummary);
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return wageTaxSummary;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<WageTaxSummary> getWagesAndTaxListByOrgLocationId(OrgLocations orgLocation)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<WageTaxSummary> wagesAndTaxlist = null;
		String query = "from WageTaxSummary wts  left join fetch wts.orgLocationId where wts.orgLocationId.id =:orgLocationId";
	    wagesAndTaxlist = session.createQuery(query).setLong("orgLocationId", orgLocation.getId()).setCacheable(true).list();
		if(wagesAndTaxlist != null && wagesAndTaxlist.size() >0)
		{
			return wagesAndTaxlist;
		}
		return wagesAndTaxlist;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<WageTaxSummary> getWagesAndTaxListByEmployeeId(OrgLocations orgLocations, Long empId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<WageTaxSummary> wagesAndTaxlist = null;
		String query = "from WageTaxSummary wts  left join fetch wts.orgLocationId where wts.orgLocationId.id =:orgLocationId and wts.employeeId = :empId";
		wagesAndTaxlist = session.createQuery(query).setLong("orgLocationId", orgLocations.getId())
				.setLong("empId", empId).setCacheable(true).list();
		if (wagesAndTaxlist != null && wagesAndTaxlist.size() > 0) {
			return wagesAndTaxlist;
		}
		return wagesAndTaxlist;
	}
	

}
