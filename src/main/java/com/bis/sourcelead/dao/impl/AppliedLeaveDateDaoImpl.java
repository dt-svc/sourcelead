package com.bis.sourcelead.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.AppliedLeaveDateDao;
import com.bis.sourcelead.entity.AppliedLeaveDate;
import com.bis.sourcelead.entity.AppliedLeaveDateView;
import com.bis.sourcelead.entity.Leave;

import opennlp.tools.parser.Cons;

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
    		java.sql.Date startDate = new java.sql.Date(sDate.getTime());
    		java.sql.Date endDate = new java.sql.Date(eDate.getTime());
    	   Session session = this.sessionFactory.getCurrentSession();
           String query = "from AppliedLeaveDateView alView where alView.leaveTypeId =:leaveTypeId and alView.orglocationId = :orgLocationid and alView.userId = :userId and (alView.applyLeaveDate >= :startDate and alView.applyLeaveDate <= :endDate) and (alView.leaveStatus = 'APPROVED' or alView.leaveStatus = 'PENDING')";
           List<AppliedLeaveDateView> appliedLeaveList = session.createQuery(query).setLong("leaveTypeId", leaveTypeId).setLong("orgLocationid", orgLocationId).setLong("userId", userId).setParameter("startDate", startDate).setParameter("endDate", endDate).list();
           return appliedLeaveList;
       }catch (Exception e) {
    	   e.printStackTrace();
	}
    	return null;
		
    	
    	
    	
	}

	@Override
	@Transactional
	public AppliedLeaveDate saveAppliedLeaveDates(AppliedLeaveDate appliedLeaveDate) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (appliedLeaveDate != null) {
				session.saveOrUpdate(appliedLeaveDate);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return appliedLeaveDate;
	}

	@Override
	@Transactional
	public boolean checkForExistedLeave(Leave leave) {
		boolean existed = false;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from AppliedLeaveDate ald where ald.leave.id = :leaveId";
			List<Leave> leaveList = session.createQuery(query).setLong("leaveId", leave.getId()).list();
			if(leaveList != null && leaveList.size() > 0) {
				existed = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return existed;
	}

	@Override
	@Transactional
	public List<AppliedLeaveDateView> getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserIdAndStatus(Long orgLocationId, Long leaveTypeId, Date sDate, Date eDate, Long userId, String leaveStatus)
	{
		try {
	    	   String subQuery = "";
	    	   if(Constants.LEAVE_APPROVED.equalsIgnoreCase(leaveStatus) ) {
	    		   subQuery =  " and alView.leaveStatus = 'APPROVED' ";
	    	   }
	    	   if(Constants.LEAVE_REJECTED.equalsIgnoreCase(leaveStatus)) {
	    		   subQuery =  " and alView.leaveStatus = 'REJECTED'";
	    	   }
	    	   if(Constants.LEAVE_CANCELLED.equalsIgnoreCase(leaveStatus)) {
	    		   subQuery =  " and alView.leaveStatus = 'CANCELLED ";  
	    	   }
	    	   if(Constants.LEAVE_PENDING.equalsIgnoreCase(leaveStatus)){
	    		   subQuery =  " and alView.leaveStatus = 'PENDING' ";  
	    	   }
	    		java.sql.Date startDate = new java.sql.Date(sDate.getTime());
	    		java.sql.Date endDate = new java.sql.Date(eDate.getTime());
	    	   Session session = this.sessionFactory.getCurrentSession();
	           String query = "from AppliedLeaveDateView alView where alView.leaveTypeId =:leaveTypeId and alView.orglocationId = :orgLocationid and alView.userId = :userId and (alView.applyLeaveDate >= :startDate and alView.applyLeaveDate <= :endDate) "+subQuery;
	           List<AppliedLeaveDateView> appliedLeaveList = session.createQuery(query).setLong("leaveTypeId", leaveTypeId).setLong("orgLocationid", orgLocationId).setLong("userId", userId).setParameter("startDate", startDate).setParameter("endDate", endDate).list();
	           return appliedLeaveList;
	       }catch (Exception e) {
	    	   e.printStackTrace();
		}
	    	return null;
	}
}
