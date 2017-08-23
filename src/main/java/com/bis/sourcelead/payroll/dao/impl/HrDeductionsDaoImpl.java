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
import com.bis.sourcelead.payroll.dao.HrDeductionsDao;
import com.bis.sourcelead.payroll.entity.HrDeductions;
import com.bis.sourcelead.payroll.entity.HrDeductionsDetails;
import com.bis.sourcelead.payroll.entity.WageTaxSummary;

@Repository
public class HrDeductionsDaoImpl implements HrDeductionsDao{
	
	private static final Logger logger = LoggerFactory.getLogger(AccountDeductionsDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public HrDeductions saveHRDeductions(HrDeductions hrDeductions) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(hrDeductions);
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return hrDeductions;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<HrDeductions> getHrDeductionsListByOrgLocationid(OrgLocations orgLocation)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<HrDeductions> hrdeductionlist = null;
		String query = "from HrDeductions hrd  left join fetch hrd.orgLocationId where hrd.orgLocationId.id =:orgLocationId";
		hrdeductionlist = session.createQuery(query).setLong("orgLocationId", orgLocation.getId()).setCacheable(true).list();
		if(hrdeductionlist != null && hrdeductionlist.size() >0)
		{
			return hrdeductionlist;
		}
		return hrdeductionlist;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<HrDeductionsDetails> getHrDeductionsDetailsbyId(long hrDeductionId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<HrDeductionsDetails> hrdeductionlist = null;
		String query = "from HrDeductionsDetails hrd  left join fetch hrd.hrDeductions where hrd.hrDeductions.id =:hrDeductionId";
		hrdeductionlist = session.createQuery(query).setLong("hrDeductionId", hrDeductionId).setCacheable(true).list();
		if(hrdeductionlist != null && hrdeductionlist.size() >0)
		{
			return hrdeductionlist;
		}
		return hrdeductionlist;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<HrDeductions> getHrDeductionsByEmployeeId(OrgLocations orgLocations, Long empId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<HrDeductions> hrdeductionlist = null;
		String query = "from HrDeductions hrd  left join fetch hrd.orgLocationId where hrd.orgLocationId.id =:orgLocationId and hrd.employeeId = :empId";
		hrdeductionlist = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).setLong("empId", empId).setCacheable(true).list();
		if(hrdeductionlist != null && hrdeductionlist.size() >0)
		{
			return hrdeductionlist;
		}
		return hrdeductionlist;
	}

}
