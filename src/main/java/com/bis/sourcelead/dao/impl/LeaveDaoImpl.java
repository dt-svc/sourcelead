package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.LeaveDao;
import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.entity.LeaveApproveHierarchy;

@Repository
public class LeaveDaoImpl implements LeaveDao{

	private static final Logger logger = LoggerFactory.getLogger(LeaveDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	
	@Override
	@Transactional
	public Leave saveLeave(Leave leave) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (leave != null) {
				session.saveOrUpdate(leave);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return leave;
	}


	@Override
	@Transactional
	public List<LeaveApproveHierarchy> getEmployeeRequestedLeavesBylocationId(Long orgLocationId, String userId) {
		Long usrId = Long.parseLong(userId);
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from LeaveApproveHierarchy laph left join fetch laph.leaveid leave left join fetch leave.leaveType left join fetch leave.user ltype where leave.orgLocations.id = :orgLocationId and laph.approverUserId.id= :userId";
		return session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("userId", usrId).setCacheable(true).list();
	}


	@Override
	@Transactional
	public Leave getLeaveById(String leaveId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		  Leave leave =  (Leave) session.get(Leave.class, leaveId);
		return leave;
	}


	@Override
	@Transactional
	public List<Leave> getLeavesListByLocationIdAndStatus(Long orgLocationId, String status,Long userId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<Leave> leaveList = null;
		String query = "from Leave leave left join fetch leave.leaveType where leave.orgLocations.id = :orgLocationId and leave.status = :status and leave.createdBy IN (select user.userCode from User user where user.id=:userId)";
		leaveList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("userId", userId).setString("status", status).setCacheable(true).list();
		return leaveList;
	}

}
