package com.bis.sourcelead.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.AppliedLeaveDateDao;
import com.bis.sourcelead.entity.AppliedLeaveDate;
import com.bis.sourcelead.entity.AppliedLeaveDateView;

@Repository
public class AppliedLeaveDateDaoImpl implements AppliedLeaveDateDao {

	private static final Logger logger = LoggerFactory.getLogger(AppliedLeaveDateDaoImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public int deleteAppliedLeaveDatesByLeaveId(Long leaveId)
	{
		int isDeleted = 0;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "delete from AppliedLeaveDate aleave where aleave.leave.id =:leaveId ";
			Query qry = session.createQuery(query).setLong("leaveId", leaveId);
			int i = qry.executeUpdate();
			return i;
		} catch (Exception e) {
			logger.error("Delete Applied Leave Date By Leave Id Failed Due to "
					+ e.getLocalizedMessage());
		}
		return isDeleted;
	}

	@Override
	@Transactional
	public List<AppliedLeaveDateView> getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationId(Long orgLocationid, Long leaveTypeId,Date startDate,Date endDate)
	{
		List<AppliedLeaveDateView> appliedLeaveList = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from AppliedLeaveDateView alView where alView.leaveTypeId =:leaveTypeId and alView.orglocationId = :orgLocationid and (alView.applyLeaveDate >= :startDate and alView.applyLeaveDate <= :endDate)";
		appliedLeaveList = session.createQuery(query).setLong("leaveTypeId", leaveTypeId).setLong("orgLocationid", orgLocationid).setParameter("startDate", startDate).setParameter("endDate", endDate).list();
		return appliedLeaveList;
	}

	@Override
	@Transactional
	public List<AppliedLeaveDateView> getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(Long orgLocationId, Long leaveTypeId, Date sDate, Date eDate,Long userId)
	{
       try {
    	   Session session = this.sessionFactory.getCurrentSession();
           String query = "from AppliedLeaveDateView alView where alView.leaveTypeId =:leaveTypeId and alView.orglocationId = :orgLocationid and alView.userId = :userId and (alView.applyLeaveDate >= :startDate and alView.applyLeaveDate <= :endDate)";
           List<AppliedLeaveDateView> appliedLeaveList = session.createQuery(query).setLong("leaveTypeId", leaveTypeId).setLong("orgLocationid", orgLocationId).setLong("userId", userId).setParameter("startDate", sDate).setParameter("endDate", eDate).list();
           return appliedLeaveList;
       }catch (Exception e) {
    	   e.printStackTrace();
	}
    	return null;
		
	}
}
