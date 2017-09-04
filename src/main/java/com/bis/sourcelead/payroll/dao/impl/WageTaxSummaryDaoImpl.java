package com.bis.sourcelead.payroll.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
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

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public int updateWageTaxSummary(Long empId, OrgLocations orgLocation, User slUser)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("update WageTaxSummary set userId = :userId where employeeId = :empId");
		query.setParameter("userId", empId);
		int result = query.executeUpdate();
		return result;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<WageTaxSummary> getAllImportedUsers(OrgLocations orgLocations) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<WageTaxSummary> wagesAndTaxlist = null;
		String query = "from WageTaxSummary wts  left join fetch wts.orgLocationId where wts.orgLocationId.id =:orgLocationId and wts.userId.id IS NULL GROUP BY wts.employeeId";
		wagesAndTaxlist = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).setCacheable(true).list();
		if (wagesAndTaxlist != null && wagesAndTaxlist.size() > 0) {
			return wagesAndTaxlist;
		}
		return wagesAndTaxlist;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<WageTaxSummary> getMappedUsersListByOrgLocationId(Long locationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<WageTaxSummary> wagesAndTaxlist = null;
		String query = "from WageTaxSummary wts  left join fetch wts.orgLocationId where wts.orgLocationId.id =:orgLocationId and wts.userId.id <> NULL GROUP BY wts.employeeId";
		wagesAndTaxlist = session.createQuery(query).setLong("orgLocationId", locationId).setCacheable(true).list();
		if (wagesAndTaxlist != null && wagesAndTaxlist.size() > 0) {
			return wagesAndTaxlist;
		}
		return wagesAndTaxlist;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<WageTaxSummary> getUniqueWagesAndTaxListByOrgLocationId(OrgLocations orgLocation) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<WageTaxSummary> wagesAndTaxlist = null;
		String query = "from WageTaxSummary wts  left join fetch wts.orgLocationId where wts.orgLocationId.id =:orgLocationId  GROUP BY wts.employeeId";
	    wagesAndTaxlist = session.createQuery(query).setLong("orgLocationId", orgLocation.getId()).setCacheable(true).list();
		if(wagesAndTaxlist != null && wagesAndTaxlist.size() >0)
		{
			return wagesAndTaxlist;
		}
		return wagesAndTaxlist;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public WageTaxSummary getWageTaxSummaryByEmpIdandDuration(String empId,Long locationId,Date startDate, Date endDate)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<WageTaxSummary> wagesAndTaxlist = null;
		Date date = new Date();
		String query = "from WageTaxSummary wt where wt.orgLocationId.id =:orgLocationId and wt.employeeId =:empId and ( wt.createdDate >= :startDateUTC)";
		wagesAndTaxlist = session.createQuery(query).setLong("orgLocationId", locationId).setString("empId", empId).setParameter("startDateUTC", date).setCacheable(true).list();
		if(wagesAndTaxlist != null && wagesAndTaxlist.size() >0)
		{
			return wagesAndTaxlist.get(0);
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public int getWageTaxSummaryVersionByLocationId(Long locationId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "SELECT MAX(wts.version) FROM WageTaxSummary wts where wts.orgLocationId.id =:orgLocationId";
		List<Integer> version = session.createQuery(query).setLong("orgLocationId", locationId).setCacheable(true).list();
		Integer count = 0;
		if(version != null && !version.isEmpty() && version.get(0) != null){
				count = version.get(0).intValue();
		}
		return count;
		
	}
	

}
