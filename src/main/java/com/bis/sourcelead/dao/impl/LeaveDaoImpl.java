package com.bis.sourcelead.dao.impl;


import java.text.ParseException;

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
	public ResponseList<LeaveApproveHierarchy> getEmployeeRequestedLeavesBylocationId(LeaveDTO leaveDTO,OrgLocations orgLocations) throws ParseException {
		
		
		// Rajashekar Start
		/*Session session = this.sessionFactory.getCurrentSession();
		List<LeaveApproveHierarchy> employerPayrollCostList = null;
		ResponseList<EmployerPayrollCost> response = new ResponseList<>();
		String query = "from EmployerPayrollCost epc  left join fetch epc.orgLocationId where epc.orgLocationId.id =:orgLocationId";
		String countQuery = "from EmployerPayrollCost epc  where epc.orgLocationId.id =:orgLocationId";
		
		if(employerPayrollCostDTO.getSearchValue().contains("/") || employerPayrollCostDTO.getSearchValue().contains("-")){
			String convertedDate=employerPayrollCostDTO.getSearchValue();
			if(employerPayrollCostDTO.getSearchValue().contains("/")){
				convertedDate = employerPayrollCostDTO.getSearchValue().replaceAll("/", "-");
			}
			DateTimeFormatter orgFormat = new DateTimeFormatterBuilder().appendPattern(orgLocation.getDateFormat()).toFormatter();
            LocalDate parsedDate = LocalDate.parse(convertedDate, orgFormat);
            DateTimeFormatter f2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String newDate = parsedDate.format(f2);
            Date searchDate = new SimpleDateFormat("yyyy-MM-dd").parse(newDate);
            java.sql.Date sqlDate = new java.sql.Date(searchDate.getTime());
            String queryDate = "from EmployerPayrollCost epc where epc.orgLocationId.id =:orgLocationId and ( epc.fromDate =:sqlDate or epc.toDate =:sqlDate )";
            Query queryObjDate = session.createQuery(queryDate.toString()).setDate("sqlDate", sqlDate).setLong("orgLocationId", orgLocation.getId());
            response.setData(queryObjDate.list());

		}else{
		StringBuilder query1 = new StringBuilder("");
			if(employerPayrollCostDTO.getSearchValue() !=null && !"".equalsIgnoreCase(employerPayrollCostDTO.getSearchValue())) {
			query1.append(" and epc.fromDate like '%" + employerPayrollCostDTO.getSearchValue() + "%' ")
					.append("OR epc.toDate like '%" + employerPayrollCostDTO.getSearchValue() + "%' ")
					.append("OR epc.employeeId like '%" + employerPayrollCostDTO.getSearchValue() + "%' ")
					.append("OR epc.employeeName like '%" + employerPayrollCostDTO.getSearchValue() + "%' ")
					.append("OR epc.ssn like '%" + employerPayrollCostDTO.getSearchValue() + "%' ")
					.append("OR epc.totalHrs like '%" + employerPayrollCostDTO.getSearchValue() + "%' ")
					.append("OR epc.grossWages like '%" + employerPayrollCostDTO.getSearchValue() + "%' ")
					.append("OR epc.fica like '%" + employerPayrollCostDTO.getSearchValue() + "%' ")
					.append("OR epc.futa like '%" + employerPayrollCostDTO.getSearchValue() + "%' ")
					.append("OR epc.sui like '%" + employerPayrollCostDTO.getSearchValue() + "%' ")
					.append("OR epc.erLocals like '%" + employerPayrollCostDTO.getSearchValue() + "%' ")
					.append("OR epc.wcCost like '%" + employerPayrollCostDTO.getSearchValue() + "%' ")
					.append("OR epc.adminFee like '%" + employerPayrollCostDTO.getSearchValue() + "%' ")
					.append("OR epc.totalErCost like '%" + employerPayrollCostDTO.getSearchValue() + "%' ");
			}
		Query queryObj = session.createQuery(query + query1.toString());
		if (employerPayrollCostDTO != null && employerPayrollCostDTO.getLength() > 0) {
			queryObj.setFirstResult(employerPayrollCostDTO.getStart());
			queryObj.setMaxResults(employerPayrollCostDTO.getLength());
			queryObj.setLong("orgLocationId", orgLocation.getId()).setCacheable(true).list();
		}
		employerPayrollCostList = queryObj.list();
		int count = 0;
		int filteredCount = 0;
		List<Long> countlist = session.createQuery("select count(*) " + countQuery).setLong("orgLocationId", orgLocation.getId()).setCacheable(true).list();
		if(countlist != null && !countlist.isEmpty()) {
			count = countlist.iterator().next().intValue();
		}
		response.setRecordsTotal(count);
		if (employerPayrollCostDTO != null) {
			List<Long> filteredCountlist  = session.createQuery("select count(*) " + countQuery + query1.toString()).setLong("orgLocationId", orgLocation.getId()).list();
			if(filteredCountlist != null && !filteredCountlist.isEmpty()) {
				filteredCount = filteredCountlist.iterator().next().intValue();
			}
			response.setRecordsFiltered(filteredCount);
		} else {
			response.setRecordsFiltered(filteredCount);
		}

			response.setData(employerPayrollCostList);
		}
			return response;*/
		// Rajashekar End
		
		
		
		
		Long usrId = Long.parseLong(leaveDTO.getUserId());
		Session session = this.sessionFactory.getCurrentSession();
		String subQuery = "";
		
		List<LeaveApproveHierarchy> leaveApproveHierarchyList = null;
		ResponseList<LeaveApproveHierarchy> response = new ResponseList<>();
		
		if(StringUtils.isNotBlank(leaveDTO.getLeaveStatus())) {
			StringBuilder searchQuery = new StringBuilder("");
			StringBuilder leavesearchQuery = new StringBuilder("");
			if(Constants.APPROVED.equalsIgnoreCase(leaveDTO.getLeaveStatus())) {
				subQuery = "and leave.status = :leaveStatus";
			}else if(Constants.PENDING.equalsIgnoreCase(leaveDTO.getLeaveStatus())){
				subQuery = "and leave.status = :leaveStatus";
			}else if(Constants.REJECTED.equalsIgnoreCase(leaveDTO.getLeaveStatus())) {
				subQuery = "and leave.status = :leaveStatus";
			}else{
				subQuery = "and leave.status = :leaveStatus";
			}
			String query = "from LeaveApproveHierarchy laph left join fetch laph.leaveid leave left join fetch leave.leaveType left join fetch leave.user ltype where leave.orgLocations.id = :orgLocationId and laph.approverUserId.id= :userId "+subQuery+"";
			String countQuery ="from Leave leave where leave.orgLocations.id = :orgLocationId "+subQuery;
			if(StringUtils.isNotBlank(leaveDTO.getSearchValue())){
				searchQuery.append(" and laph.leaveid.user.firstName like '%" + leaveDTO.getSearchValue() + "%' ")
				.append("OR laph.leaveid.user.lastName like '%" + leaveDTO.getSearchValue() + "%' ");
				
				leavesearchQuery.append(" and leave.user.firstName like '%" + leaveDTO.getSearchValue() + "%' ")
				.append("OR leave.user.lastName like '%" + leaveDTO.getSearchValue() + "%' ");
			}
			
			Query queryObj = session.createQuery(query+searchQuery);
			if (leaveDTO != null && leaveDTO.getLength() > 0) {
				queryObj.setFirstResult(leaveDTO.getStart());
				queryObj.setMaxResults(leaveDTO.getLength());
				queryObj.setParameter("leaveStatus",leaveDTO.getLeaveStatus()).setLong("orgLocationId", orgLocations.getId()).setLong("userId", usrId).setCacheable(true).list();
			}
			leaveApproveHierarchyList = queryObj.list();
			int count = 0;
			int filteredCount = 0;
			List<Long> countlist = session.createQuery("select count(*) " + countQuery+leavesearchQuery).setLong("orgLocationId", orgLocations.getId()).setParameter("leaveStatus",leaveDTO.getLeaveStatus()).setCacheable(true).list();
			if(countlist != null && !countlist.isEmpty()) {
				count = countlist.iterator().next().intValue();
			}
			response.setRecordsTotal(count);
			if (leaveDTO != null) {
				List<Long> filteredCountlist  = session.createQuery("select count(*) " + countQuery+leavesearchQuery).setLong("orgLocationId", orgLocations.getId()).setParameter("leaveStatus",leaveDTO.getLeaveStatus()).list();
				if(filteredCountlist != null && !filteredCountlist.isEmpty()) {
					filteredCount = filteredCountlist.iterator().next().intValue();
				}
				response.setRecordsFiltered(filteredCount);
			} else {
				response.setRecordsFiltered(filteredCount);
			}

				response.setData(leaveApproveHierarchyList);
			}
		else {
			StringBuilder searchQuery = new StringBuilder("");
			StringBuilder leavesearchQuery = new StringBuilder("");
			String query = "from LeaveApproveHierarchy laph left join fetch laph.leaveid leave left join fetch leave.leaveType left join fetch leave.user ltype where leave.orgLocations.id = :orgLocationId and laph.approverUserId.id= :userId";
			String countQuery ="from Leave leave where leave.orgLocations.id = :orgLocationId";
			if(StringUtils.isNotBlank(leaveDTO.getSearchValue())){
				searchQuery.append(" and laph.leaveid.user.firstName like '%" + leaveDTO.getSearchValue() + "%' ")
				.append("OR laph.leaveid.user.lastName like '%" + leaveDTO.getSearchValue() + "%' ");
				
				
				leavesearchQuery.append(" and leave.user.firstName like '%" + leaveDTO.getSearchValue() + "%' ")
				.append("OR leave.user.lastName like '%" + leaveDTO.getSearchValue() + "%' ");
			}
			
			Query queryObj = session.createQuery(query+searchQuery);
			if (leaveDTO != null && leaveDTO.getLength() > 0) {
				queryObj.setFirstResult(leaveDTO.getStart());
				queryObj.setMaxResults(leaveDTO.getLength());
				queryObj.setLong("orgLocationId", orgLocations.getId()).setLong("userId", usrId).setCacheable(true).list();
			}
			leaveApproveHierarchyList = queryObj.list();
			int count = 0;
			int filteredCount = 0;
			List<Long> countlist = session.createQuery("select count(*) " + countQuery+leavesearchQuery).setLong("orgLocationId", orgLocations.getId()).setCacheable(true).list();
			if(countlist != null && !countlist.isEmpty()) {
				count = countlist.iterator().next().intValue();
			}
			response.setRecordsTotal(count);
			if (leaveDTO != null) {
				List<Long> filteredCountlist  = session.createQuery("select count(*) " + countQuery+leavesearchQuery).setLong("orgLocationId", orgLocations.getId()).list();
				if(filteredCountlist != null && !filteredCountlist.isEmpty()) {
					filteredCount = filteredCountlist.iterator().next().intValue();
				}
				response.setRecordsFiltered(filteredCount);
			} else {
				response.setRecordsFiltered(filteredCount);
			}

				response.setData(leaveApproveHierarchyList);
		}
		return response;
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


	@Override
	@Transactional
	public List<Leave> getLeaveTakersBarChat(OrgLocations orgLocation, LeaveDTO leaveDTO) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String subQuery = "";
		if(StringUtils.isNotBlank(leaveDTO.getBarChartYear())) {
			Date[] dates = commonUtil.getStartDateEndDateByYear(Integer.parseInt(leaveDTO.getBarChartYear()));
			java.sql.Date satrtDate = new java.sql.Date(dates[0].getTime());
			java.sql.Date endDate = new java.sql.Date(dates[1].getTime());
			subQuery = " and leave.createdDate >= '"+satrtDate+"' AND leave.createdDate <= '"+endDate+"'";
		}
		if(StringUtils.isNotBlank(leaveDTO.getBarCharMonth())) {
			Date[] dates = commonUtil.getMonthStartDateEndDateByMonth(Integer.parseInt(leaveDTO.getBarCharMonth()));
			java.sql.Date satrtDate = new java.sql.Date(dates[0].getTime());
			java.sql.Date endDate = new java.sql.Date(dates[1].getTime());
			subQuery = " and leave.createdDate >= '"+satrtDate+"' AND leave.createdDate <= '"+endDate+"'";
		}
		if(StringUtils.isNotBlank(leaveDTO.getBarChartFromDate()) && StringUtils.isNotBlank(leaveDTO.getBarChartToDate())) {
			Date startDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), leaveDTO.getBarChartFromDate());
			Date eDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), leaveDTO.getBarChartToDate());
			java.sql.Date satrtDate = new java.sql.Date(startDate.getTime());
			java.sql.Date endDate = new java.sql.Date(eDate.getTime());
			subQuery = " and leave.createdDate >= '"+satrtDate+"' AND leave.createdDate <= '"+endDate+"'";
		}
		if(StringUtils.isNotBlank(leaveDTO.getBarChartYear()) && StringUtils.isNotBlank(leaveDTO.getBarCharMonth())) {
			Date[] dates = commonUtil.getMonthStartDateEndDateByYearAndMonth(Integer.parseInt(leaveDTO.getBarChartYear()),Integer.parseInt(leaveDTO.getBarCharMonth()));
			java.sql.Date satrtDate = new java.sql.Date(dates[0].getTime());
			java.sql.Date endDate = new java.sql.Date(dates[1].getTime());
			subQuery = " and leave.createdDate >= '"+satrtDate+"' AND leave.createdDate <= '"+endDate+"'";
		}
		String query = "from Leave leave where leave.orgLocations.id = :orgLocationId and leave.status = 'APPROVED'"+subQuery;
		List<Leave> leavesList = session.createQuery(query).setLong("orgLocationId", orgLocation.getId())
				.setCacheable(true).list();
		return leavesList;
	}


	@Override
	public List<LeaveApproveHierarchy> getEmployeeRequestedLeavesBylocationIdAndUserIdAndStatus(Long orgLocationId, String userId, String leaveStatus)
	{
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

}
