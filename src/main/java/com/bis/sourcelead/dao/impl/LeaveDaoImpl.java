package com.bis.sourcelead.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
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
	public List<LeaveApproveHierarchy> getEmployeeRequestedLeavesBylocationId(Long orgLocationId, String userId,String leaveStatus) {
		
		Long usrId = Long.parseLong(userId);
		Session session = this.sessionFactory.getCurrentSession();
		String subQuery = "";
		
		if(StringUtils.isNotBlank(leaveStatus)) {
			if(Constants.APPROVED.equalsIgnoreCase(leaveStatus)) {
				subQuery = "and leave.status = :leaveStatus";
			}else if(Constants.PENDING.equalsIgnoreCase(leaveStatus)){
				subQuery = "and leave.status = :leaveStatus";
			}else if(Constants.REJECTED.equalsIgnoreCase(leaveStatus)) {
				subQuery = "and leave.status = :leaveStatus";
			}else{
				subQuery = "and leave.status = :leaveStatus";
			}
			String query = "from LeaveApproveHierarchy laph left join fetch laph.leaveid leave left join fetch leave.leaveType left join fetch leave.user ltype where leave.orgLocations.id = :orgLocationId and laph.approverUserId.id= :userId "+subQuery+"";
			return session.createQuery(query).setParameter("leaveStatus",leaveStatus).setLong("orgLocationId", orgLocationId).setLong("userId", usrId).setCacheable(true).list();
		}else {
			String query = "from LeaveApproveHierarchy laph left join fetch laph.leaveid leave left join fetch leave.leaveType left join fetch leave.user ltype where leave.orgLocations.id = :orgLocationId and laph.approverUserId.id= :userId";
			return session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("userId", usrId).setCacheable(true).list();
		}
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


	@Override
	@Transactional
	public List<LeaveApproveHierarchy> getEmployeeRequestedLeavesBylocationIdAndDuration(Long locationId, String userId, String status, Date currentWeekStarDate, Date currentWeekEndDate,String department)
	{
		Long usrId = Long.parseLong(userId);
		Session session = this.sessionFactory.getCurrentSession();
		String subQuery = "";
		String subQuery1 = "";
		String subQuery2 = "";
		
		String[] departments = null;
		if(null != department && !"".equals(department)){	
			departments = department.split(",");
		}
		
		List<String> deptList = new ArrayList<>();
		if (departments != null) {
			for (int i = 0; i < departments.length; i++) {
				deptList.add(departments[i]);
			}
		}
		
		if(currentWeekStarDate != null && currentWeekEndDate != null){
			subQuery1 = " and (leave.fromDate >= :weekStartDate and leave.fromDate <= :weekEnddate)";
		}
		
		if(deptList.size()>0){
			subQuery2 = "and (leave.user.id IN ()"; 
		}
		
		if(StringUtils.isNotBlank(status)) {
			if(Constants.APPROVED.equalsIgnoreCase(status)) {
				subQuery = "and leave.status = :leaveStatus";
			}else if(Constants.PENDING.equalsIgnoreCase(status)){
				subQuery = "and leave.status = :leaveStatus";
			}else if(Constants.REJECTED.equalsIgnoreCase(status)) {
				subQuery = "and leave.status = :leaveStatus";
			}else{
				subQuery = "and leave.status = :leaveStatus";
			}
			String query = "from LeaveApproveHierarchy laph left join fetch laph.leaveid leave left join fetch leave.leaveType left join fetch leave.user ltype where leave.orgLocations.id = :orgLocationId and laph.approverUserId.id= :userId "+subQuery+" "+subQuery1+"";
			return session.createQuery(query).setParameter("leaveStatus",status).setLong("orgLocationId", locationId).setLong("userId", usrId).setParameter("weekStartDate", currentWeekStarDate).setParameter("weekEnddate", currentWeekEndDate).setCacheable(true).list();
		}else {
			String query = "from LeaveApproveHierarchy laph left join fetch laph.leaveid leave left join fetch leave.leaveType left join fetch leave.user ltype where leave.orgLocations.id = :orgLocationId and laph.approverUserId.id= :userId "+subQuery1+"";
			return session.createQuery(query).setLong("orgLocationId", locationId).setLong("userId", usrId).setParameter("weekStartDate", currentWeekStarDate).setParameter("weekEnddate", currentWeekEndDate).setCacheable(true).list();
		}
	}

}
