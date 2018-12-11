package com.bis.sourcelead.payroll.dao.impl;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;
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

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public UsPayrollSchedule getPayScheduleByUUID(String payrollScheduleUUID,Long orgLocationId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UsPayrollSchedule usPay where usPay.orgLocation.id = :orgLocationId and usPay.payrollScheduleUUID =:payrollScheduleUUID and usPay.status = 1";
		return (UsPayrollSchedule) session.createQuery(query).setString("payrollScheduleUUID", payrollScheduleUUID).setLong("orgLocationId", orgLocationId).uniqueResult();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public int deleteExistedNotifyMembers(Long payScheduleId)
	{
		int isDeleted = 0;
		try {
			if(payScheduleId != null) {
				Session session = this.sessionFactory.getCurrentSession();
				String query = "delete from USPayScheduleNotifyMember notifyMem where notifyMem.payrollScheduleId.id =:payrollScheduleId ";
				Query qry = session.createQuery(query).setLong("payrollScheduleId", payScheduleId);
				isDeleted = qry.executeUpdate();	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isDeleted;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public UsPayrollSchedule findScheduleByFrequency(String payFrequency, Long orgLocationId,String scheduleUUID)
	{
		UsPayrollSchedule usPayrollSchedule = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from UsPayrollSchedule usPay where usPay.orgLocation.id = :orgLocationId and usPay.payFrequency =:payFrequency and usPay.status = 1 and usPay.payrollScheduleUUID != :scheduleUUID";
			usPayrollSchedule =  (UsPayrollSchedule) session.createQuery(query).setString("payFrequency", payFrequency).setLong("orgLocationId", orgLocationId).setString("scheduleUUID", scheduleUUID).uniqueResult();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return usPayrollSchedule;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<UsPayrollSchedule> getAllPayrollScheduleByOrgLocationId(Long orgLocationId) {
		List<UsPayrollSchedule> usPayrollSchedule = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from UsPayrollSchedule usPay where usPay.orgLocation.id = :orgLocationId";// and usPay.payFrequency =:payFrequency and usPay.status = 1 and usPay.payrollScheduleUUID != :scheduleUUID";
			usPayrollSchedule =  session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return usPayrollSchedule;
	}

}
