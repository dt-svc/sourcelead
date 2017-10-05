package com.bis.sourcelead.dao.impl;

import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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
import com.bis.sourcelead.dao.LeaveDao;
import com.bis.sourcelead.dto.LeaveDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.entity.LeaveApproveHierarchy;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.entity.EmployerPayrollCost;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class LeaveDaoImpl implements LeaveDao{

	private static final Logger logger = LoggerFactory.getLogger(LeaveDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	CommonUtil commonUtil;

	
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

	@Override
	@Transactional
	public List<Leave> getLeavesListByTypeIdAndLocationIdAndUserId(String leaveTypeId,Long orgLocationId, Long userId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Leave> leaveList = null;
		String query = "from Leave leave left join fetch leave.leaveType lType left join fetch leave.user where leave.orgLocations.id = :orgLocationId and leave.user.id = :userId and lType.id = :leaveTypeId";
		leaveList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("userId", userId).setLong("userId", userId).setParameter("leaveTypeId", leaveTypeId).setCacheable(true).list();
		return leaveList;
	}
	
	@Override
	@Transactional
	public int getNumberOfLeavesTakenByUser(Long userId,String leaveTypeId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Leave leave where leave.user.id = :userId  and leave.leaveType.id = :leaveTypeId and leave.status = 'APPROVED'";
		List<Leave> leavesList = session.createQuery(query).setLong("userId", userId).setString("leaveTypeId", leaveTypeId).setCacheable(true).list();
		int numberOfLeavsTaken = leavesList.stream().filter(l->l.getNumberOfDays() != null).mapToInt(l->Integer.parseInt(l.getNumberOfDays())).sum();
		return numberOfLeavsTaken;
	}


	@Override
	@Transactional
	public int getThisMonthLeavesTakenByUser(Long userId,String leaveTypeId) {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = 1;
		c.set(year, month, day);
		int numOfDaysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		Date startDate = c.getTime();
		java.sql.Date currentMonthStartDate = new java.sql.Date(startDate.getTime());
		c.add(Calendar.DAY_OF_MONTH, numOfDaysInMonth - 1);
		Date endDate = c.getTime();
		java.sql.Date currentMonthEndDate = new java.sql.Date(endDate.getTime());
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Leave leave where leave.user.id = :userId and leave.leaveType.id = :leaveTypeId and leave.status = 'APPROVED' and leave.createdDate BETWEEN :currentMonthStartDate AND :currentMonthEndDate";
		List<Leave> leavesList = session.createQuery(query).setLong("userId", userId).setString("leaveTypeId", leaveTypeId).setParameter("currentMonthStartDate", currentMonthStartDate).setParameter("currentMonthEndDate", currentMonthEndDate).setCacheable(true)
				.list();
		int numberOfLeavsTaken = leavesList.stream().filter(l->l.getNumberOfDays() != null).mapToInt(l->Integer.parseInt(l.getNumberOfDays())).sum();
		return numberOfLeavsTaken;
	}


	@Override
	@Transactional
	public ResponseList<Leave> getTopLeavetakers(OrgLocations orgLocation, LeaveDTO leaveDTO) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String subQuery = "";
		if(StringUtils.isNotBlank(leaveDTO.getReportYear())) {
			Date[] dates = commonUtil.getStartDateEndDateByYear(Integer.parseInt(leaveDTO.getReportYear()));
			java.sql.Date satrtDate = new java.sql.Date(dates[0].getTime());
			java.sql.Date endDate = new java.sql.Date(dates[1].getTime());
			subQuery = " and leave.createdDate >= '"+satrtDate+"' AND leave.createdDate <= '"+endDate+"'";
		}
		if(StringUtils.isNotBlank(leaveDTO.getReportMonth())) {
			Date[] dates = commonUtil.getMonthStartDateEndDateByMonth(Integer.parseInt(leaveDTO.getReportMonth()));
			java.sql.Date satrtDate = new java.sql.Date(dates[0].getTime());
			java.sql.Date endDate = new java.sql.Date(dates[1].getTime());
			subQuery = " and leave.createdDate >= '"+satrtDate+"' AND leave.createdDate <= '"+endDate+"'";
		}
		if(StringUtils.isNotBlank(leaveDTO.getReportFromDate()) && StringUtils.isNotBlank(leaveDTO.getReportEndDate())) {
			Date startDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), leaveDTO.getReportFromDate());
			Date eDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), leaveDTO.getReportEndDate());
			java.sql.Date satrtDate = new java.sql.Date(startDate.getTime());
			java.sql.Date endDate = new java.sql.Date(eDate.getTime());
			subQuery = " and leave.createdDate >= '"+satrtDate+"' AND leave.createdDate <= '"+endDate+"'";
		}
		if(StringUtils.isNotBlank(leaveDTO.getReportYear()) && StringUtils.isNotBlank(leaveDTO.getReportMonth())) {
			Date[] dates = commonUtil.getMonthStartDateEndDateByYearAndMonth(Integer.parseInt(leaveDTO.getReportYear()),Integer.parseInt(leaveDTO.getReportMonth()));
			java.sql.Date satrtDate = new java.sql.Date(dates[0].getTime());
			java.sql.Date endDate = new java.sql.Date(dates[1].getTime());
			subQuery = " and leave.createdDate >= '"+satrtDate+"' AND leave.createdDate <= '"+endDate+"'";
		}
		List<Leave> leaveList = null;
		ResponseList<Leave> response = new ResponseList<>();
		String query = "from Leave leave where leave.orgLocations.id =:orgLocationId and leave.status = 'APPROVED'"+subQuery;
		String countQuery = "from Leave leave  where leave.orgLocations.id =:orgLocationId and leave.status = 'APPROVED'"+subQuery;

		Query queryObj = session.createQuery(query);
		if (leaveDTO != null && leaveDTO.getLength() > 0) {
			queryObj.setFirstResult(leaveDTO.getStart());
			queryObj.setMaxResults(leaveDTO.getLength());
			queryObj.setLong("orgLocationId", orgLocation.getId()).setCacheable(true).list();
		}
		leaveList = queryObj.list();
		int count = 0;
		int filteredCount = 0;
		List<Long> countlist = session.createQuery("select count(*) " + countQuery).setLong("orgLocationId", orgLocation.getId()).setCacheable(true).list();
		if (countlist != null && !countlist.isEmpty()) {
			count = countlist.iterator().next().intValue();
		}
		response.setRecordsTotal(count);
		if (leaveDTO != null) {
			List<Long> filteredCountlist = session.createQuery("select count(*) " + countQuery)
					.setLong("orgLocationId", orgLocation.getId()).list();
			if (filteredCountlist != null && !filteredCountlist.isEmpty()) {
				filteredCount = filteredCountlist.iterator().next().intValue();
			}
			response.setRecordsFiltered(filteredCount);
		} else {
			response.setRecordsFiltered(filteredCount);
		}

		response.setData(leaveList);
		return response;
	}

}
