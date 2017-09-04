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

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.EmployerPayrollCostDao;
import com.bis.sourcelead.payroll.entity.EmployerPayrollCost;

@Repository
public class EmployerPayrollCostDaoImpl implements EmployerPayrollCostDao{

	private static final Logger logger = LoggerFactory.getLogger(EmployerPayrollCostDaoImpl.class);
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public EmployerPayrollCost saveEmployerPayrollCost(EmployerPayrollCost employerPayrollCost) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(employerPayrollCost);
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return employerPayrollCost;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<EmployerPayrollCost> loadEmployerPayrollCostByOrgLocationId(OrgLocations orgLocation) {
		Session session = this.sessionFactory.getCurrentSession();
		List<EmployerPayrollCost> empCostList = null;
		String query = "from EmployerPayrollCost empPayroll  left join fetch empPayroll.orgLocationId where empPayroll.orgLocationId.id =:orgLocationId";
	    empCostList = session.createQuery(query).setLong("orgLocationId", orgLocation.getId()).setCacheable(true).list();
		if(empCostList != null && empCostList.size() >0)
		{
			return empCostList;
		}
		return empCostList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public EmployerPayrollCost getEmployeePayrollCostById(String EmpId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<EmployerPayrollCost> empCostList = null;
		String query = "from EmployerPayrollCost empPayroll  left join fetch empPayroll.orgLocationId where empPayroll.employeeId =:empId";
	    empCostList = session.createQuery(query).setString("empId", EmpId).setCacheable(true).list();
		if(empCostList != null && empCostList.size() >0)
		{
			return empCostList.get(0);
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public int getEmployerPayrollCostVersionByLocationId(Long locationId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "SELECT MAX(ep.version)  FROM EmployerPayrollCost ep where ep.orgLocationId.id =:orgLocationId";
		List<Integer> version = session.createQuery(query).setLong("orgLocationId", locationId).setCacheable(true).list();
		Integer count = 0;
		if(version != null && !version.isEmpty() && version.get(0) != null){
				count = version.get(0);
		}
		return count;
	}

}
