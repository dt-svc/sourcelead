package com.bis.sourcelead.payroll.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.PayrollScheduleDao;
import com.bis.sourcelead.payroll.entity.UsPayrollSchedule;

@Repository
public class PayrollScheduleDaoImpl implements PayrollScheduleDao {
	private static final Logger logger = LoggerFactory.getLogger(UserPayrollDaoImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public UsPayrollSchedule savePayrollSchedule(UsPayrollSchedule usPayrollSchedule, OrgLocations orgLocations) {
		
		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(usPayrollSchedule);
		return  usPayrollSchedule;
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public UsPayrollSchedule getWeeklyFrequenciesBySelectedDate(String dateFrequency, OrgLocations orgLocations) {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UsPayrollSchedule usPay where usPay.orgLocation.id =:orgLocationId and usPay.payScheduleType =:dateFrequency";
		List<UsPayrollSchedule> usPayList = session.createQuery(query).setString("dateFrequency", dateFrequency).setLong("orgLocationId", orgLocations.getId()).list();
		
	    if(usPayList != null && usPayList.size() > 0) {
	    	return usPayList.get(0);
	    }else {
	    	return null;
	    }
		
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<UsPayrollSchedule> findAllPayrollScheduleList(Long orgLocationId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UsPayrollSchedule usPay where usPay.orgLocation.id =:orgLocationId and usPay.status = 1";
		return session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		
	}

}
