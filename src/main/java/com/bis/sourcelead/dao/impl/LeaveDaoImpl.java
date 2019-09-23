package com.bis.sourcelead.dao.impl;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.LeaveDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dto.DayWiseLeaveReportDTO;
import com.bis.sourcelead.dto.HrLeaveReportDTO;
import com.bis.sourcelead.dto.LeaveDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.AppliedLeaveDateView;
import com.bis.sourcelead.entity.AssociateBalanceLeaveView;
import com.bis.sourcelead.entity.HolidayAndRestrictday;
import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.entity.LeaveApproveHierarchy;
import com.bis.sourcelead.entity.LeaveReportView;
import com.bis.sourcelead.entity.LeaveRole;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.MonthlyLeaves;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RoleHierarchy;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.indPayroll.entity.IndEmployeePayroll;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.PayrollLopHis;
import com.bis.sourcelead.reportsDTO.LeaveBalanceReportDTO;
import com.bis.sourcelead.service.HolidayService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.RoleHierarchyService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.LeaveManagementUtil;


@Repository
public class LeaveDaoImpl implements LeaveDao{

	private static final Logger logger = LoggerFactory.getLogger(LeaveDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	ProjectTasksService projectTasksService;

	@Autowired
	RoleHierarchyService roleHierarchyService;
	
	@Autowired
	RolesService roleService;
	
	@Autowired
	ConversionUtil conversionUtil;
	
	@Autowired
	HolidayService holidayService;
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	LeaveManagementUtil leaveManagementUtil;
	
	@Autowired
	UserDao userDao;
	
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
		
		Long usrId = Long.parseLong(leaveDTO.getUserId());
		Session session = this.sessionFactory.getCurrentSession();
		List<LeaveApproveHierarchy> leaveApproveHierarchyList = null;
		ResponseList<LeaveApproveHierarchy> response = new ResponseList<>();
		if(StringUtils.isNotBlank(leaveDTO.getLeaveStatus())) {
			StringBuilder searchQuery = new StringBuilder("");
			StringBuilder leavesearchQuery = new StringBuilder("");
			String query ="";
			String countQuery ="";
			if("ROLE_ADMIN".equalsIgnoreCase(leaveDTO.getLoginUserRole())) {
				query = "from LeaveApproveHierarchy laph left join fetch laph.leaveid leave "
						      + " left join fetch leave.leaveType  leaveType"
						      + " left join fetch leave.user user"
						      + " where (leave.orgLocations.id = :orgLocationId or leave.orgLocations.org.id =:orgId) and leave.status =  '"+leaveDTO.getLeaveStatus()+"' ";
				countQuery ="from Leave leave where (leave.orgLocations.id = :orgLocationId or leave.orgLocations.org.id =:orgId) and leave.status = '"+leaveDTO.getLeaveStatus()+"'";
			}else {
				query = "from LeaveApproveHierarchy laph left join fetch laph.leaveid leave "
					      + " left join fetch leave.leaveType  leaveType"
					      + " left join fetch leave.user user"
					      + " where (leave.orgLocations.id = :orgLocationId or leave.orgLocations.org.id =:orgId) and (laph.approverUserId.id= :userId or user.id in (select urole.user.id from UserRole urole where urole.reportingManager.id = :userId)) and leave.status =  '"+leaveDTO.getLeaveStatus()+"'";
				countQuery ="from LeaveApproveHierarchy laph where (laph.leaveid.orgLocations.id = :orgLocationId or laph.leaveid.orgLocations.org.id =:orgId) and (laph.approverUserId.id= :userId or laph.leaveid.user.id in (select urole.user.id from UserRole urole where urole.reportingManager.id = :userId)) and laph.leaveid.status = '"+leaveDTO.getLeaveStatus()+"'";
			}
			
			String qry = "";
			if(StringUtils.isNotBlank(leaveDTO.getSortColumn())) {
				if("firstName".equalsIgnoreCase(leaveDTO.getSortColumn())) {
					qry = " order by laph.leaveid.user.firstName "+leaveDTO.getSortOrder();
				}else if("name".equalsIgnoreCase(leaveDTO.getSortColumn())) {
					qry = " order by laph.leaveid.leaveType.name "+leaveDTO.getSortOrder();
				}else if("approvedBy".equalsIgnoreCase(leaveDTO.getSortColumn())) {
					qry = " order by laph.approverUserId.firstName "+leaveDTO.getSortOrder();
				}else if("updatedDate".equalsIgnoreCase(leaveDTO.getSortColumn())) {
					qry = " order by laph.leaveid.updatedDate "+leaveDTO.getSortOrder();
				}else if("rejectedBy".equalsIgnoreCase(leaveDTO.getSortColumn())) {
					qry = " order by laph.leaveid.rejectedBy.firstName "+leaveDTO.getSortOrder();
				}else if("cancelledBy".equalsIgnoreCase(leaveDTO.getSortColumn())) {
					qry = " order by laph.leaveid.cancelledBy.firstName "+leaveDTO.getSortOrder();
				}
			}
			
			if(StringUtils.isNotBlank(leaveDTO.getSearchValue())){
				if ((leaveDTO.getSearchValue().contains("/") || leaveDTO.getSearchValue().contains("-")) &&  leaveDTO.getSearchValue().contains("/p")){
					String convertedDate = leaveDTO.getSearchValue();
					if (leaveDTO.getSearchValue().contains("/")) {
						convertedDate = leaveDTO.getSearchValue().replaceAll("/", "-");
					}
					DateTimeFormatter orgFormat = new DateTimeFormatterBuilder().appendPattern(orgLocations.getDateFormat())
							.toFormatter();
					LocalDate parsedDate = LocalDate.parse(convertedDate, orgFormat);
					DateTimeFormatter f2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					String newDate = parsedDate.format(f2);
					Date searchDate = new SimpleDateFormat("yyyy-MM-dd").parse(newDate);
					java.sql.Date sqlDate = new java.sql.Date(searchDate.getTime());
					searchQuery.append(" and laph.leaveid.fromDate like '%" + sqlDate + "%' ");
					searchQuery.append(" and  laph.leaveid.toDate like '%" + sqlDate + "%' ");
				}else {
					searchQuery.append(" and concat (laph.leaveid.user.firstName ,' ',")
					.append("laph.leaveid.user.lastName) like '%"+ leaveDTO.getSearchValue() +"%'");
					if(Constants.LEAVE_APPROVED.equalsIgnoreCase(leaveDTO.getLeaveStatus())) {
						searchQuery.append("OR  (concat(laph.leaveid.approvedBy.firstName,laph.leaveid.approvedBy.lastName) like '%" + leaveDTO.getSearchValue() + "%' ")
						.append("OR laph.leaveid.approvedBy.lastName like '%" + leaveDTO.getSearchValue() + "%' ")
						.append("OR laph.leaveid.fromDate like '%" + leaveDTO.getSearchValue() + "%') ");
					}else if(Constants.LEAVE_REJECTED.equalsIgnoreCase(leaveDTO.getLeaveStatus())) {
						searchQuery.append("OR (concat(laph.leaveid.rejectedBy.firstName,laph.leaveid.rejectedBy.lastName) like '%" + leaveDTO.getSearchValue() + "%' ) ")
						.append("OR laph.leaveid.rejectedBy.lastName like '%" + leaveDTO.getSearchValue() + "%' ");
					} else if(Constants.LEAVE_CANCELLED.equalsIgnoreCase(leaveDTO.getLeaveStatus())) {
						searchQuery.append("OR (concat(laph.leaveid.cancelledBy.firstName,laph.leaveid.cancelledBy.lastName) like '%" + leaveDTO.getSearchValue() + "%' ) ")
						.append("OR laph.leaveid.cancelledBy.lastName like '%" + leaveDTO.getSearchValue() + "%' ");
					}
					searchQuery.append("OR laph.leaveid.leaveType.name like '%" + leaveDTO.getSearchValue() + "%' )");
					
					leavesearchQuery.append(" and ((concat(leave.user.firstName,leave.user.lastName) like '%" + leaveDTO.getSearchValue() + "%' ) ")
					.append("OR leave.user.lastName like '%" + leaveDTO.getSearchValue() + "%' ")
					.append("OR leave.numberOfDays like '%" + leaveDTO.getSearchValue() + "%' ");
					if(Constants.LEAVE_APPROVED.equalsIgnoreCase(leaveDTO.getLeaveStatus())) {
						leavesearchQuery.append("OR (concat(leave.approvedBy.firstName,leave.approvedBy.lastName) like '%" + leaveDTO.getSearchValue() + "%' ) ")
						.append("OR leave.approvedBy.lastName like '%" + leaveDTO.getSearchValue() + "%' ");
					}else if(Constants.LEAVE_REJECTED.equalsIgnoreCase(leaveDTO.getLeaveStatus())) {
						leavesearchQuery.append("OR (concat(leave.rejectedBy.firstName,leave.rejectedBy.lastName) like '%" + leaveDTO.getSearchValue() + "%' ) ")
						.append("OR leave.rejectedBy.lastName like '%" + leaveDTO.getSearchValue() + "%' ");
					}else {
						leavesearchQuery.append("OR (concat(leave.cancelledBy.firstName,leave.cancelledBy.lastName) like '%" + leaveDTO.getSearchValue() + "%' ) ")
						.append("OR leave.cancelledBy.lastName like '%" + leaveDTO.getSearchValue() + "%' ");
					}
					leavesearchQuery.append("OR leave.leaveType.name like '%" + leaveDTO.getSearchValue() + "%' )");
				}
				
			}
			
			Query queryObj = session.createQuery(query+searchQuery+" group by leave.id "+qry);
			if (leaveDTO != null && leaveDTO.getLength() > 0) {
				queryObj.setFirstResult(leaveDTO.getStart());
				queryObj.setMaxResults(leaveDTO.getLength());
			}
			if("ROLE_ADMIN".equalsIgnoreCase(leaveDTO.getLoginUserRole())) {
			    queryObj.setLong("orgLocationId", orgLocations.getId()).setLong("orgId", orgLocations.getOrg().getId()).list();
			}else {
				queryObj.setLong("orgLocationId", orgLocations.getId()).setLong("orgId", orgLocations.getOrg().getId()).setLong("userId", usrId).list();
			}
			leaveApproveHierarchyList = queryObj.list();
			int count = 0;
			int filteredCount = 0;
			/**
			 * @author Mahaveer Manoj
			 * @commented for hotfix of SLC721 [Exception in Production Environment for leave management module]
			 */
			/*if("ROLE_ADMIN".equalsIgnoreCase(leaveDTO.getLoginUserRole())) {
				List<Long> countlist = session.createQuery("select count(*) " + countQuery+leavesearchQuery).setLong("orgLocationId", orgLocations.getId()).setLong("orgId", orgLocations.getOrg().getId()).list();
				count = countlist.get(0).intValue();
			}else {
				List<Long> countlist = session.createQuery("select count(*) " + countQuery+leavesearchQuery).setLong("orgLocationId", orgLocations.getId()).setLong("orgId", orgLocations.getOrg().getId()).setLong("userId", usrId).list();
				count = countlist.get(0).intValue();
			}*/
			 
//			if(leaveApproveHierarchyList != null && !leaveApproveHierarchyList.isEmpty()) {
//				count = leaveApproveHierarchyList.stream().filter(c->c.getLeaveid().getStatus().equalsIgnoreCase(leaveDTO.getLeaveStatus())).collect(Collectors.toList()).size();
//			}
			count = leaveApproveHierarchyList.size();
			response.setRecordsTotal(count);
//			if (leaveDTO != null) {
//				List<Long> filteredCountlist  = session.createQuery("select count(*) " + countQuery+leavesearchQuery).setLong("orgLocationId", orgLocations.getId()).setLong("orgId", orgLocations.getOrg().getId()).list();
//				if(leaveApproveHierarchyList != null && !leaveApproveHierarchyList.isEmpty()) {
//					filteredCount = leaveApproveHierarchyList.stream().filter(c->c.getLeaveid().getStatus().equalsIgnoreCase(leaveDTO.getLeaveStatus())).collect(Collectors.toList()).size();
//				}
				response.setRecordsFiltered(count);
//			}
			response.setData(leaveApproveHierarchyList);
			}
		else {
			StringBuilder searchQuery = new StringBuilder("");
			StringBuilder leavesearchQuery = new StringBuilder("");
			String query = "";
			String countQuery ="";
			if("ROLE_ADMIN".equalsIgnoreCase(leaveDTO.getLoginUserRole())) {
			query = "from LeaveApproveHierarchy laph left join fetch laph.leaveid leave "
					+ "left join fetch leave.leaveType leaveType "
					+ "left join fetch leave.user user "
					+ " where (leave.orgLocations.id = :orgLocationId or leave.orgLocations.org.id =:orgId)";
			countQuery ="from Leave leave where (leave.orgLocations.id = :orgLocationId or leave.orgLocations.org.id =:orgId) and leave.status = '"+leaveDTO.getLeaveStatus()+"'";
			}else {
				query = "from LeaveApproveHierarchy laph left join fetch laph.leaveid leave "
						+ "left join fetch leave.leaveType leaveType "
						+ "left join fetch leave.user user "
						+ " where (leave.orgLocations.id = :orgLocationId or leave.orgLocations.org.id =:orgId) and laph.approverUserId.id= :userId";
				countQuery ="from Leave leave where (leave.orgLocations.id = :orgLocationId or leave.orgLocations.org.id =:orgId) and leave.status = '"+leaveDTO.getLeaveStatus()+"'";
			}
			
			if(StringUtils.isNotBlank(leaveDTO.getSearchValue())){
				if (leaveDTO.getSearchValue().contains("/") || leaveDTO.getSearchValue().contains("-")) {
					String convertedDate = leaveDTO.getSearchValue();
					if (leaveDTO.getSearchValue().contains("/")) {
						convertedDate = leaveDTO.getSearchValue().replaceAll("/", "-");
					}
					DateTimeFormatter orgFormat = new DateTimeFormatterBuilder().appendPattern(orgLocations.getDateFormat())
							.toFormatter();
					LocalDate parsedDate = LocalDate.parse(convertedDate, orgFormat);
					DateTimeFormatter f2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					String newDate = parsedDate.format(f2);
					Date searchDate = new SimpleDateFormat("yyyy-MM-dd").parse(newDate);
					java.sql.Date sqlDate = new java.sql.Date(searchDate.getTime());
					searchQuery.append(" and laph.leaveid.fromDate like '%" + sqlDate + "%' ");
					searchQuery.append(" and  laph.leaveid.toDate like '%" + sqlDate + "%' ");
				}else {
				searchQuery.append(" and (laph.leaveid.user.firstName like '%" + leaveDTO.getSearchValue() + "%' ")
				.append("OR laph.leaveid.user.lastName like '%" + leaveDTO.getSearchValue() + "%' ")
				.append("OR laph.leaveid.numberOfDays like '%" + leaveDTO.getSearchValue() + "%' ");
				if(Constants.LEAVE_APPROVED.equalsIgnoreCase(leaveDTO.getLeaveStatus())) {
					searchQuery.append("OR laph.leaveid.approvedBy.firstName like '%" + leaveDTO.getSearchValue() + "%' ")
					.append("OR laph.leaveid.approvedBy.lastName like '%" + leaveDTO.getSearchValue() + "%' ");
				}else if(Constants.LEAVE_REJECTED.equalsIgnoreCase(leaveDTO.getLeaveStatus())) {
					searchQuery.append("OR laph.leaveid.rejectedBy.firstName like '%" + leaveDTO.getSearchValue() + "%' ")
					.append("OR laph.leaveid.rejectedBy.firstName like '%" + leaveDTO.getSearchValue() + "%' ");
				}else {
					searchQuery.append("OR laph.leaveid.cancelledBy.firstName like '%" + leaveDTO.getSearchValue() + "%' ")
					.append("OR laph.leaveid.cancelledBy.firstName like '%" + leaveDTO.getSearchValue() + "%' ");
				}
				searchQuery.append("OR laph.leaveid.leaveType.name like '%" + leaveDTO.getSearchValue() + "%' )");
				
				leavesearchQuery.append(" and (leave.user.firstName like '%" + leaveDTO.getSearchValue() + "%' ")
				.append("OR leave.user.lastName like '%" + leaveDTO.getSearchValue() + "%' ")
				.append("OR leave.numberOfDays like '%" + leaveDTO.getSearchValue() + "%' ");
				if(Constants.LEAVE_APPROVED.equalsIgnoreCase(leaveDTO.getLeaveStatus())) {
					leavesearchQuery.append("OR leave.approvedBy.firstName like '%" + leaveDTO.getSearchValue() + "%' ")
					.append("OR leave.approvedBy.lastName like '%" + leaveDTO.getSearchValue() + "%' ");
				}else if(Constants.LEAVE_REJECTED.equalsIgnoreCase(leaveDTO.getLeaveStatus())) {
					leavesearchQuery.append("OR leave.rejectedBy.firstName like '%" + leaveDTO.getSearchValue() + "%' ")
					.append("OR leave.rejectedBy.firstName like '%" + leaveDTO.getSearchValue() + "%' ");
				}else {
					leavesearchQuery.append("OR leave.cancelledBy.firstName like '%" + leaveDTO.getSearchValue() + "%' ")
					.append("OR leave.cancelledBy.firstName like '%" + leaveDTO.getSearchValue() + "%' ");
				}
				leavesearchQuery.append("OR leave.leaveType.name like '%" + leaveDTO.getSearchValue() + "%' )");
				}
				
			}
			
			Query queryObj = session.createQuery(query+searchQuery);
			if (leaveDTO != null && leaveDTO.getLength() > 0) {
				queryObj.setFirstResult(leaveDTO.getStart());
				queryObj.setMaxResults(leaveDTO.getLength());
				
			}
			if("ROLE_ADMIN".equalsIgnoreCase(leaveDTO.getLoginUserRole())) {
				queryObj.setLong("orgLocationId", orgLocations.getId()).setLong("orgId", orgLocations.getOrg().getId()).setCacheable(false).list();
			}else {
				queryObj.setLong("orgLocationId", orgLocations.getId()).setLong("orgId", orgLocations.getOrg().getId()).setLong("userId", usrId).setCacheable(false).list();
			}
			leaveApproveHierarchyList = queryObj.list();
			int count = 0;
			int filteredCount = 0;
			/**
			 * @author Mahaveer Manoj
			 * @commented for hotfix of SLC721 [Exception in Production Environment for leave management module]
			 */
			/*if("ROLE_ADMIN".equalsIgnoreCase(leaveDTO.getLoginUserRole())) {
				List<Long> countlist = session.createQuery("select count(*) " + countQuery+leavesearchQuery).setLong("orgLocationId", orgLocations.getId()).setLong("orgId", orgLocations.getOrg().getId()).setLong("userId", usrId).list();
				count = countlist.get(0).intValue();
			}else {
				List<Long> countlist = session.createQuery("select count(*) " + countQuery+leavesearchQuery).setLong("orgLocationId", orgLocations.getId()).setLong("orgId", orgLocations.getOrg().getId()).setLong("userId", usrId).list();
				count = countlist.get(0).intValue();
			}*/
			count = leaveApproveHierarchyList.size();
			
			/*if(leaveApproveHierarchyList != null && !leaveApproveHierarchyList.isEmpty()) {
				count = leaveApproveHierarchyList.stream().filter(c->c.getLeaveid().getStatus().equalsIgnoreCase(leaveDTO.getLeaveStatus())).collect(Collectors.toList()).size();
			}*/
			response.setRecordsTotal(count);
//			if (leaveDTO != null) {
//				List<Long> filteredCountlist  = session.createQuery("select count(*) " + countQuery+leavesearchQuery).setLong("orgLocationId", orgLocations.getId()).setLong("orgId", orgLocations.getOrg().getId()).setLong("userId", usrId).list();
//				if(leaveApproveHierarchyList != null && !leaveApproveHierarchyList.isEmpty()) {
//					filteredCount = leaveApproveHierarchyList.stream().filter(c->c.getLeaveid().getStatus().equalsIgnoreCase(leaveDTO.getLeaveStatus())).collect(Collectors.toList()).size();
//				}
				response.setRecordsFiltered(count);
//			} 
				response.setData(leaveApproveHierarchyList);
		}
		return response;
	}


	@Override
	@Transactional
	public Leave getLeaveById(Long leaveId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Leave leave left join fetch leave.leaveType left join fetch leave.user where leave.id = :leaveId";
		Leave leave = null;
		List<Leave> leaveList = session.createQuery(query).setLong("leaveId", leaveId).setCacheable(false).list();
		if(leaveList !=null && leaveList.size() >0){
			leave =  leaveList.get(0);
		}
		return leave;
	}


	@Override
	@Transactional
	public List<Leave> getLeavesListByLocationIdAndStatus(Long orgLocationId, String status,Long userId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<Leave> leaveList = null;
		String query = "from Leave leave left join fetch leave.leaveType where leave.orgLocations.id = :orgLocationId and "
				+ "leave.status = :status and leave.user.id IN (select user.id from User user where user.id=:userId)";
		leaveList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("userId", userId).setString("status", status).setCacheable(false).list();
		return leaveList;
	}


	@Override
	@Transactional
	public List<LeaveApproveHierarchy> getEmployeeRequestedLeavesBylocationIdAndDuration(Long locationId, String userId, String status, Date currentWeekStarDate, Date currentWeekEndDate,String department,Date leaveDate) throws Exception
	{
		UserRole role = userRoleService.getUserRoleByUserIdAndLocationId(Long.parseLong(userId), locationId);
		Long usrId = Long.parseLong(userId);
		Session session = this.sessionFactory.getCurrentSession();
		String subQuery = "";
		String subQuery1 = "";
		String subQuery2 = "";
		String subQuery3 = "";
		
		String[] departments = null;
		if(null != department && !"".equals(department)){	
			departments = department.split(",");
		}
		
		List<Long> deptList = new ArrayList<>();
		if (departments != null) {
			for (int i = 0; i < departments.length; i++) {
				deptList.add(Long.parseLong(departments[i]));
			}
		}
		
		if(currentWeekStarDate != null && currentWeekEndDate != null){
			subQuery1 = " and (leave.fromDate >= :weekStartDate and leave.fromDate <= :weekEnddate)";
		}
		
		if(leaveDate != null){
			subQuery3 = " and (leave.fromDate <= :leaveDate and leave.toDate >= :leaveDate)";
		}
		
		if(deptList.size()>0){
			subQuery2 = "and (leave.user.id IN (select emp.user.id from Employees emp where emp.departmentId.id IN (:deptList)))"; 
		}
		
//		if(StringUtils.isNotBlank(status)) {
//			subQuery = "and leave.status = :leaveStatus";
		if(!"Admin".equalsIgnoreCase(role.getRoles().getDisplayName())){
			if(currentWeekStarDate != null && currentWeekEndDate != null){
				String query = "from LeaveApproveHierarchy laph left join fetch laph.leaveid leave left join fetch leave.leaveType left join fetch leave.user ltype where leave.orgLocations.id = :orgLocationId and laph.approverUserId.id= :userId and (leave.status = :leaveStatus or leave.status = 'PENDING') "+subQuery1+" "+subQuery2+" "+subQuery3+"";
				if(deptList.size() >0)
					return session.createQuery(query).setParameter("leaveStatus",status).setLong("orgLocationId", locationId).setLong("userId", usrId).setParameter("weekStartDate", currentWeekStarDate).setParameter("weekEnddate", currentWeekEndDate).setParameterList("deptList", deptList).list();
				else 
					return session.createQuery(query).setParameter("leaveStatus",status).setLong("orgLocationId", locationId).setLong("userId", usrId).setParameter("weekStartDate", currentWeekStarDate).setParameter("weekEnddate", currentWeekEndDate).list();
			}else{
				String query = "from LeaveApproveHierarchy laph left join fetch laph.leaveid leave left join fetch leave.leaveType left join fetch leave.user ltype where leave.orgLocations.id = :orgLocationId and laph.approverUserId.id= :userId and (leave.status = :leaveStatus or leave.status = 'PENDING') "+subQuery2+" "+subQuery3+"";
				if(deptList.size() >0)
					return session.createQuery(query).setParameter("leaveStatus",status).setLong("orgLocationId", locationId).setLong("userId", usrId).setParameter("leaveDate", leaveDate).setParameterList("deptList", deptList).list();
				else 
					return session.createQuery(query).setParameter("leaveStatus",status).setLong("orgLocationId", locationId).setLong("userId", usrId).setParameter("leaveDate", leaveDate).list();
			}
		}else{
			if(currentWeekStarDate != null && currentWeekEndDate != null){
				String query = "from LeaveApproveHierarchy laph left join fetch laph.leaveid leave left join fetch leave.leaveType left join fetch leave.user ltype where leave.orgLocations.id = :orgLocationId and (leave.status = :leaveStatus or leave.status = 'PENDING') "+subQuery1+" "+subQuery2+" "+subQuery3+"";
				if(deptList.size() >0)
					return session.createQuery(query).setParameter("leaveStatus",status).setLong("orgLocationId", locationId).setParameter("weekStartDate", currentWeekStarDate).setParameter("weekEnddate", currentWeekEndDate).setParameterList("deptList", deptList).list();
				else 
					return session.createQuery(query).setParameter("leaveStatus",status).setLong("orgLocationId", locationId).setParameter("weekStartDate", currentWeekStarDate).setParameter("weekEnddate", currentWeekEndDate).list();
			}else{
				String query = "from LeaveApproveHierarchy laph left join fetch laph.leaveid leave left join fetch leave.leaveType left join fetch leave.user ltype where leave.orgLocations.id = :orgLocationId and (leave.status = :leaveStatus or leave.status = 'PENDING') "+subQuery2+" "+subQuery3+"";
				if(deptList.size() >0)
					return session.createQuery(query).setParameter("leaveStatus",status).setLong("orgLocationId", locationId).setParameter("leaveDate", leaveDate).setParameterList("deptList", deptList).list();
				else 
					return session.createQuery(query).setParameter("leaveStatus",status).setLong("orgLocationId", locationId).setParameter("leaveDate", leaveDate).list();
			}
		}
		
		
			
//		}else {
//			String query = "from LeaveApproveHierarchy laph left join fetch laph.leaveid leave left join fetch leave.leaveType left join fetch leave.user ltype where leave.orgLocations.id = :orgLocationId and laph.approverUserId.id= :userId "+subQuery1+" "+subQuery2+" "+subQuery3+"";
//			if(deptList.size() >0)
//				return session.createQuery(query).setLong("orgLocationId", locationId).setLong("userId", usrId).setParameter("weekStartDate", currentWeekStarDate).setParameter("weekEnddate", currentWeekEndDate).setParameterList("deptList", deptList).list();
//			else
//				return session.createQuery(query).setLong("orgLocationId", locationId).setLong("userId", usrId).setParameter("weekStartDate", currentWeekStarDate).setParameter("weekEnddate", currentWeekEndDate).list();
//		}
	}

	@Override
	@Transactional
	public List<Leave> getLeavesListByTypeIdAndLocationIdAndUserId(String leaveTypeUUID,Long orgLocationId, Long userId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Leave> leaveList = null;
		String query = "from Leave leave left join fetch leave.leaveType lType left join fetch leave.user where leave.orgLocations.id = :orgLocationId and leave.user.id = :userId and lType.leaveTypeUUID = :leaveTypeUUID";
		leaveList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("userId", userId).setString("leaveTypeUUID", leaveTypeUUID).list();
		return leaveList;
	}
	
	@Override
	@Transactional
	public Map<String, Float> getNumberOfLeavesTakenByUser(Long userId,Long leaveTypeId) throws Exception {
		Map<String, Float> leavesMap = new HashMap<>();
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Leave leave where leave.user.id = :userId  and leave.leaveType.id = :leaveTypeId and (leave.status = 'APPROVED' OR leave.status = 'PENDING')";
		List<Leave> leavesList = session.createQuery(query).setLong("userId", userId).setLong("leaveTypeId", leaveTypeId).list();
		float numberOfLeavsTaken = leavesList.stream().mapToInt(l->l.getNumberOfDays()).sum();
		leavesMap.put("numberOfLeavsTaken", numberOfLeavsTaken);
//		int halfDayCount = leavesList.stream().mapToInt(ll->ll.getFromHalfDay().equalsIgnoreCase("YES")).collect(supplier, accumulator, combiner);
		int fromhalfDayCount = leavesList.stream().filter(ll->ll.getFromHalfDay() != null && ll.getFromHalfDay().equalsIgnoreCase("YES")).collect(Collectors.toList()).size();
		int tohalfDayCount = leavesList.stream().filter(ll->ll.getToHalfDay() != null && ll.getToHalfDay().equalsIgnoreCase("YES")).collect(Collectors.toList()).size();
		leavesMap.put("halfdayLeavesCount", (float)fromhalfDayCount + tohalfDayCount);
		
		float numberOfExtraLeaves = (float) leavesList.stream().mapToDouble(l->l.getExtraLeavesTaken()).sum();
		leavesMap.put("numberOfExtraLeaves", numberOfExtraLeaves);
		return leavesMap;
	}


	@Override
	@Transactional
	public float getThisMonthLeavesTakenByUser(Long userId,LeaveType leaveType,OrgLocations orgLocations) {
		
		Date[] dates = conversionUtil.getMonthStartDateEndDate();
		java.sql.Date startDate = new java.sql.Date(dates[0].getTime());
		java.sql.Date endDate = new java.sql.Date(dates[1].getTime());
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from AppliedLeaveDateView aleave where aleave.userId = :userId and aleave.leaveTypeId = :leaveTypeId and (( aleave.applyLeaveDate >= :startDate AND aleave.applyLeaveDate <= :endDate)) and (aleave.leaveStatus = 'APPROVED' or aleave.leaveStatus = 'PENDING')";
		List<AppliedLeaveDateView> thisMonthLeaves = session.createQuery(query).setLong("leaveTypeId", leaveType.getId()).setParameter("startDate", startDate).setParameter("endDate", endDate).setLong("userId", userId).list();
		float leaves = (float) leaveManagementUtil.getExcludeHolidayLeaveCount(thisMonthLeaves, orgLocations,new AssociateBalanceLeaveView(),dates[0],dates[1],leaveType);
		return leaves;
	}


	private List<AppliedLeaveDateView> getExcludeHolidayLeaveCount(List<AppliedLeaveDateView> thisMonthLeaves, OrgLocations orgLocations, Date[] dates, LeaveType l)
	{
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("E"); 
		if (l.getIncludeHolidays() != null
				&& !Constants.YES.equalsIgnoreCase(l.getIncludeHolidays())) {
			List<HolidayAndRestrictday> holidayList = holidayService.getHolidayByFromToDate(dates[0], dates[1], orgLocations);
			for (HolidayAndRestrictday holiday : holidayList) {
				Date holidayDate = commonUtil.orgLocationCommonDate(holiday.getDay(), orgLocations);
				boolean s = thisMonthLeaves.removeIf(x -> commonUtil.orgLocationCommonDate(x.getApplyLeaveDate(), orgLocations).equals(holidayDate));
			}
		}
		if (l.getIncludeWeekoff() != null && !Constants.YES.equalsIgnoreCase(l.getIncludeWeekoff())) {
			if (orgLocations.getWeekOffDays() != null) {
				String[] weekOoffs = orgLocations.getWeekOffDays().split(",");
				for (String weekEndDay : weekOoffs) {
					thisMonthLeaves.removeIf(x -> simpleDateformat.format(x.getApplyLeaveDate()).toUpperCase().equals(weekEndDay.trim().toUpperCase()));
				}
			}
		}
		return thisMonthLeaves;
	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ResponseList<Leave> getTopLeavetakers(OrgLocations orgLocation, LeaveDTO leaveDTO) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String subQuery = "";
		if(StringUtils.isNotBlank(leaveDTO.getTopLeaveTakersreportYear())) {
			Date[] dates = commonUtil.getStartDateEndDateByYear(Integer.parseInt(leaveDTO.getTopLeaveTakersreportYear()));
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
		if(StringUtils.isNotBlank(leaveDTO.getTopLeaveTakersreportYear()) && StringUtils.isNotBlank(leaveDTO.getReportMonth())) {
			Date[] dates = commonUtil.getMonthStartDateEndDateByYearAndMonth(Integer.parseInt(leaveDTO.getTopLeaveTakersreportYear()),Integer.parseInt(leaveDTO.getReportMonth()));
			java.sql.Date satrtDate = new java.sql.Date(dates[0].getTime());
			java.sql.Date endDate = new java.sql.Date(dates[1].getTime());
			subQuery = " and leave.createdDate >= '"+satrtDate+"' AND leave.createdDate <= '"+endDate+"'";
		}
		List<Leave> leaveList = null;
		String query = "";
		String countQuery = "";
		ResponseList<Leave> response = new ResponseList<>();
		if("ROLE_ADMIN".equalsIgnoreCase(leaveDTO.getLoginUserRole())){
			query = "from Leave leave where leave.orgLocations.id =:orgLocationId and (leave.status = 'APPROVED' OR leave.status = 'PENDING' )"+subQuery;
			countQuery = "from Leave leave where leave.orgLocations.id =:orgLocationId and (leave.status = 'APPROVED' OR leave.status = 'PENDING')"+subQuery;
		}else{
			query = "from Leave leave where leave.orgLocations.id =:orgLocationId and (leave.status = 'APPROVED' OR leave.status = 'PENDING' ) and leave.id IN(select lah.leaveid.id from LeaveApproveHierarchy lah where lah.approverUserId.id =:userId) "+subQuery;
			countQuery = "from Leave leave where leave.orgLocations.id =:orgLocationId and (leave.status = 'APPROVED' OR leave.status = 'PENDING') and leave.id IN(select lah.leaveid.id from LeaveApproveHierarchy lah where lah.approverUserId.id =:userId)"+subQuery;
		}


		Query queryObj = session.createQuery(query);
		if (leaveDTO != null && leaveDTO.getLength() > 0) {
			queryObj.setFirstResult(leaveDTO.getStart());
			queryObj.setMaxResults(leaveDTO.getLength());
//			queryObj.setLong("orgLocationId", orgLocation.getId()).list();
		}

		if("ROLE_ADMIN".equalsIgnoreCase(leaveDTO.getLoginUserRole())){
			leaveList = queryObj.setLong("orgLocationId", orgLocation.getId()).list();
		}else{
			leaveList = queryObj.setLong("orgLocationId", orgLocation.getId()).setLong("userId",Long.parseLong(leaveDTO.getUserId())).list();
		}
		
		int count = 0;
		int filteredCount = 0;
		List<Long> countlist = null;

		if("ROLE_ADMIN".equalsIgnoreCase(leaveDTO.getLoginUserRole())){
			countlist = session.createQuery("select count(*) " + countQuery).setLong("orgLocationId", orgLocation.getId()).list();
		}else{
			countlist = session.createQuery("select count(*) " + countQuery).setLong("orgLocationId", orgLocation.getId()).setLong("userId",Long.parseLong(leaveDTO.getUserId())).list();
		}

		if (countlist != null && !countlist.isEmpty()) {
			count = countlist.iterator().next().intValue();
		}
		response.setRecordsTotal(count);
		if (leaveDTO != null) {
			List<Long> filteredCountlist = null;

			if("ROLE_ADMIN".equalsIgnoreCase(leaveDTO.getLoginUserRole())){
				filteredCountlist = session.createQuery("select count(*) " + countQuery)
						.setLong("orgLocationId", orgLocation.getId()).list();
			}else{
				filteredCountlist = session.createQuery("select count(*) " + countQuery)
						.setLong("orgLocationId", orgLocation.getId()).setLong("userId",Long.parseLong(leaveDTO.getUserId())).list();
			}
			
			if (filteredCountlist != null && !filteredCountlist.isEmpty()) {
				filteredCount = filteredCountlist.iterator().next().intValue();
			}
			response.setRecordsFiltered(filteredCount);
		} 

		response.setData(leaveList);
		return response;
	}


	@Override
	@Transactional
	public List<LeaveApproveHierarchy> getAllEmployeesLeavesCounts(Long orgLocationId,Long userId,String loginUserRole,Long orgId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "";
		List<LeaveApproveHierarchy> leavesList = null;
		if("ROLE_ADMIN".equalsIgnoreCase(loginUserRole)) {
			query = "from LeaveApproveHierarchy lah left join fetch lah.leaveid leave left join fetch lah.approverUserId where (leave.orgLocations.id = :orgLocationId or leave.orgLocations.org.id =:orgId)  group by leave.id";
			leavesList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("orgId", orgId).list();
		}else {
			query = "from LeaveApproveHierarchy lah left join fetch lah.leaveid leave left join fetch lah.approverUserId where lah.approverUserId.id =:userId and (leave.orgLocations.id = :orgLocationId or leave.orgLocations.org.id =:orgId)  group by leave.id";
			leavesList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("userId", userId).setLong("orgId", orgId).list();
		}
		return leavesList;
	}


	@Override
	@Transactional
	public List<Leave> getAllSelfLeavesCounts(Long orgLocationId, Long userId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Leave leave1 where leave1.orgLocations.id = :orgLocationId and leave1.user.id = :userId";
		List<Leave> leavesList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("userId", userId).list();
		return leavesList;
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
		String query = "from Leave leave where leave.orgLocations.id = :orgLocationId and (leave.status = 'APPROVED' OR leave.status = 'PENDING' )"+subQuery;
		List<Leave> leavesList = session.createQuery(query).setLong("orgLocationId", orgLocation.getId()).list();
		return leavesList;
	}


	@Override
	@Transactional
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
			return session.createQuery(query).setParameter("leaveStatus",leaveStatus).setLong("orgLocationId", orgLocationId).setLong("userId", usrId).list();
		}else {
			String query = "from LeaveApproveHierarchy laph left join fetch laph.leaveid leave left join fetch leave.leaveType left join fetch leave.user ltype where leave.orgLocations.id = :orgLocationId and laph.approverUserId.id= :userId";
			return session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("userId", usrId).list();
		}
	}


	@Override
	@Transactional
	public boolean validateNameByOrgLocationIdandFromTable(String name, Long orgLocationId, String fromTable,String id)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String subQuery = "";
		/*if("Holiday".equalsIgnoreCase(fromTable))
			subQuery = " and frm.holidayName = :name ";
		else*/ if("Shift".equalsIgnoreCase(fromTable))
			subQuery = " and frm.shiftName = :name and frm.status = 1";
		else if("LeaveType".equalsIgnoreCase(fromTable))
			subQuery = " and frm.name = :name ";
			
		String query = "from "+fromTable+" frm where frm.orgLocations.id = :orgLocationId "+subQuery+" and frm.id <> :id";
		List<Long> found = session.createQuery(query).setLong("orgLocationId", orgLocationId).setString("name", name.replaceAll("\\s+"," ").trim()).setString("id", id).list();
		if(found.size() > 0){
			return true;
		}else{
			return false;
		}
		
	}


	@Override
	@Transactional
	public boolean deleteAllLeaveRolesByParticularTableId(String from ,Long fromId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String subQuery = "";
		if(Constants.LEAVE_TYPE.equalsIgnoreCase(from))
			subQuery = " role.leaveTypeid.id = :fromId ";
		else if(Constants.LEAVE_OPTION.equalsIgnoreCase(from))
			subQuery = " role.leavesOptionId.id = :fromId ";
		else if(Constants.RESTRICTDAY.equalsIgnoreCase(from))
			subQuery = " role.restrictDay.id = :fromId ";
		Query qry = session.createQuery("delete from LeaveRole role where "+subQuery+"");
		qry.setParameter("fromId", fromId);
		int res = qry.executeUpdate();
		if (res > 0) {
			return true;
		}
		else {
			return false;
		}
	}


	@Override
	@Transactional
	public void storeHolidayAndRestrictDays(HolidayAndRestrictday day)
	{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (day != null) {
				session.saveOrUpdate(day);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
	}


	@Override
	@Transactional
	public List<HolidayAndRestrictday> getAllHolidayAndRestrictDaysInDuration(String fromDate, String toDate, OrgLocations orgLocations)
	{
		Date from = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), fromDate);
		Date to =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), toDate);
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from HolidayAndRestrictday day left join fetch day.orgLocations loc left join fetch day.restrictDay left join fetch day.holiday  where loc.id = :orgLocationId and (day.day >= :fromDate and day.day <= :toDate) and day.status = '1'";
		List<HolidayAndRestrictday> list = session.createQuery(query).setParameter("fromDate", from).setParameter("toDate", to).setLong("orgLocationId", orgLocations.getId()).list();
		return list;
	}


	@Override
	@Transactional
	public List<User> getApproversListBasedonHierarchyExceptLoginUser(Long loginUserId, Long roleId, Long locationId) throws Exception
	{
		Roles role = roleService.getRolesById(roleId);
		RoleHierarchy roleHierarchy = roleHierarchyService.getRoleHierarchyById(role.getRoleHierarchy().getId());
		Session session = this.sessionFactory.getCurrentSession();
		String subQuery = "";
		if(roleHierarchy.getHierarchyLevel() == 1)
			subQuery = "userRole.user.id from UserRole userRole where  (userRole.roles.roleHierarchy.hierarchyLevel <= (select r.roleHierarchy.hierarchyLevel from Roles r where r.id = :roleId)) and (userRole.roles.actionId IS NOT NULL) and userRole.orgLocations = :orgLocationsId and userRole.userState = 'ACTIVE' and userRole.user.id <> :userId ";
		else
			subQuery = "userRole.user.id from UserRole userRole where  (userRole.roles.roleHierarchy.hierarchyLevel < (select r.roleHierarchy.hierarchyLevel from Roles r where r.id = :roleId)) and (userRole.roles.actionId IS NOT NULL) and userRole.orgLocations = :orgLocationsId and userRole.userState = 'ACTIVE' and userRole.user.id <> :userId ";
		
		String query = "from User user where user.id IN (select "+subQuery+" )  ";
	    List<User> userRoleList = session.createQuery(query).setLong("roleId", roleId).setLong("userId", loginUserId).setLong( "orgLocationsId", locationId).list();
        return userRoleList;
	}


	@Override
	@Transactional
	public List<HolidayAndRestrictday> getHolidayOrRestrictDaysListById(Long id, String type)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<HolidayAndRestrictday> list = null;
		if("RestrictDay".equalsIgnoreCase(type)){
			String query = "from HolidayAndRestrictday day where day.restrictDay.id =:resId ";
	    	list = session.createQuery(query).setLong("resId", id).list();
		}else{
			String query = "from HolidayAndRestrictday day where day.holiday.id =:holId ";
	    	list = session.createQuery(query).setLong("holId", id).list();
		}
        return list;
	}


	@Override
	@Transactional
	public List<LeaveRole> getLeaveRolesByRestrictDayId(Long restrictDayId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<LeaveRole> list = null;
			String query = "from LeaveRole role where role.restrictDay.id =:resId ";
	    	list = session.createQuery(query).setLong("resId", restrictDayId).list();
        return list;
	}


	@Override
	@Transactional
	public void deleteAllHolidayOrRestrictDayById(Long id, String type)
	{
		Session session = this.sessionFactory.getCurrentSession();
		Query qry = null;
		if("RestrictDay".equalsIgnoreCase(type)){
			String query = "delete from HolidayAndRestrictday day where day.restrictDay.id =:resId ";
	    	qry = session.createQuery(query).setLong("resId", id);
		}else{
			String query = "delete from HolidayAndRestrictday day where day.holiday.id =:holId ";
	    	qry = session.createQuery(query).setLong("holId", id);
    	}
		qry.executeUpdate();
	}


	@Override
	@Transactional
	public List<Leave> getAllLeavesByLocationIdAndStatus(Long locationId, String status)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<Leave> list = null;
		if(status != null && StringUtils.isNotEmpty(status)) {
			String query = "from Leave leave where leave.orgLocations.id =:orgLocationId and leave.status =:status";
			list = session.createQuery(query).setString("status", status).setLong("orgLocationId", locationId).list();
		}else{
			String query = "from Leave leave left join fetch leave.leaveType lvType where leave.orgLocations.id =:orgLocationId";
			list = session.createQuery(query).setLong("orgLocationId", locationId).list();
		}
		return list;
	}


	@Override
	@Transactional
	public List<Leave> getLeavesListByTypeIdAndDuration(Long leaveTypeId, Long orgLocationId, Long userId, Date startDate, Date endDate)
	{
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
		List<Leave> list = null;
		String query = "from Leave leave left join fetch leave.leaveType lType left join fetch leave.user where leave.orgLocations.id = :orgLocationId and leave.user.id = :userId and lType.id = :leaveTypeId and (leave.fromDate >= :startDate and leave.fromDate <= :endDate) and (leave.status = 'APPROVED' or leave.status = 'PENDING') order by leave.fromDate";
		list = session.createQuery(query).setLong("leaveTypeId", leaveTypeId).setLong("orgLocationId", orgLocationId).setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate).setLong("userId", userId).list();
		return list;
	}


	@Override
	@Transactional
	public MonthlyLeaves saveMonthlyLeaves(MonthlyLeaves monthlyLeaves)
	{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (monthlyLeaves != null) {
				session.saveOrUpdate(monthlyLeaves);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return monthlyLeaves;
	}


	@Override
	@Transactional
	public MonthlyLeaves getMonthlyLeavesByLeaveId(Long leaveId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<MonthlyLeaves> list = null;
		String query = "from MonthlyLeaves monthlyLeaves left join fetch monthlyLeaves.leaveId leave where monthlyLeaves.leaveId = :leaveId";
		list = session.createQuery(query).setLong("leaveId", leaveId).list();
		if(list != null && list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}


	@Override
	@Transactional
	public Leave getLeaveByLeaveUUId(String leaveUUID)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Leave leave left join fetch leave.leaveType left join fetch leave.user where leave.leaveUUID = :leaveUUID";
		Leave leave = null;
		List<Leave> leaveList = session.createQuery(query).setString("leaveUUID", leaveUUID).setCacheable(false).list();
		if(leaveList !=null && leaveList.size() >0){
			leave =  leaveList.get(0);
		}
		return leave;
	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Leave> getAllLeave() {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Leave leave  left join fetch leave.orgLocations orgLoc";
		List<Leave> leaveList = session.createQuery(query).list();
		return leaveList;
	}


	@Override
	@Transactional
	public List<Leave> getLeaveByUserIdOrglocationIdandStatus(Long userId, Long orglocationId, String approvedStatus) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Leave leave left join fetch leave.user user left join fetch leave.orgLocations orgLoc where user.id =:userId and orgLoc.id =:orglocationId and leave.status =:approvedStatus";
		List<Leave> leaveList = session.createQuery(query).setLong("userId", userId).setLong("orglocationId", orglocationId).setString("approvedStatus", approvedStatus).setCacheable(false).list();
		return leaveList;
	}


	@Override
	@Transactional
	public List<LeaveApproveHierarchy> getAllLeaveApproversList(Long locationId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from LeaveApproveHierarchy lah left join fetch lah.leaveid leave left join fetch leave.user user left join fetch leave.orgLocations orgLoc where orgLoc.id =:orglocationId";
		List<LeaveApproveHierarchy> leaveList = session.createQuery(query).setLong("orglocationId", locationId).setCacheable(false).list();
		return leaveList;
	}


	@Override
	@Transactional
	public Map<String, Float> getNumberOfCurrentMonthLeavesTakenByUser(Long userId, Long leaveTypeId)
	{
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
		Map<String, Float> leavesMap = new HashMap<>();
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Leave leave where leave.user.id = :userId  and leave.leaveType.id = :leaveTypeId and (leave.status = 'APPROVED' OR leave.status = 'PENDING') and leave.createdDate BETWEEN :currentMonthStartDate AND :currentMonthEndDate";
		List<Leave> leavesList = session.createQuery(query).setLong("userId", userId).setLong("leaveTypeId", leaveTypeId).setParameter("currentMonthStartDate", currentMonthStartDate).setParameter("currentMonthEndDate", currentMonthEndDate).list();
		float numberOfLeavsTaken = leavesList.stream().mapToInt(l->l.getNumberOfDays()).sum();
		leavesMap.put("numberOfLeavsTaken", numberOfLeavsTaken);
//		int halfDayCount = leavesList.stream().mapToInt(ll->ll.getFromHalfDay().equalsIgnoreCase("YES")).collect(supplier, accumulator, combiner);
		int fromhalfDayCount = leavesList.stream().filter(ll->ll.getFromHalfDay() != null && ll.getFromHalfDay().equalsIgnoreCase("YES")).collect(Collectors.toList()).size();
		int tohalfDayCount = leavesList.stream().filter(ll->ll.getToHalfDay() != null && ll.getToHalfDay().equalsIgnoreCase("YES")).collect(Collectors.toList()).size();
		leavesMap.put("halfdayLeavesCount", (float)fromhalfDayCount + tohalfDayCount);
		
		float numberOfExtraLeaves = (float) leavesList.stream().mapToDouble(l->l.getExtraLeavesTaken()).sum();
		leavesMap.put("numberOfExtraLeaves", numberOfExtraLeaves);
		return leavesMap;
	}

	@Override
	@Transactional
	public List<AppliedLeaveDateView> getLeaveByUserIdAndFromToDate(Long userId, OrgLocations orgLocation, String fromDate, String toDate)
	{
		Date from = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), fromDate);
		Date to;
		if(StringUtils.isNotBlank(toDate)){
			to = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), toDate);
		}else{
			to = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), fromDate);
		}
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = new java.sql.Date(from.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(to.getTime());
		String query = "from AppliedLeaveDateView aleave where aleave.orglocationId = :orgLocationId and aleave.userId = :userId and ((aleave.applyLeaveDate >= :startDate and aleave.applyLeaveDate <= :endDate) or ( aleave.applyLeaveDate = :startDate) OR (aleave.applyLeaveDate = :endDate)) and (aleave.leaveStatus = 'APPROVED' or aleave.leaveStatus = 'PENDING')";
		List<AppliedLeaveDateView> list = session.createQuery(query).setLong("orgLocationId", orgLocation.getId()).setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate).setLong("userId", userId).list();
		return list;
	}


	@Override
	@Transactional
	public int deleteApproversByLeaveId(Long leaveId) {
		int isDeleted = 0;
		try {
			if(leaveId != null) {
				Session session = this.sessionFactory.getCurrentSession();
				String query = "delete from LeaveApproveHierarchy lah where lah.leaveid.id =:leaveId";
				Query qry = session.createQuery(query).setLong("leaveId", leaveId);
				int i = qry.executeUpdate();
				return i;	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isDeleted;
	}


	


	@Override
	@Transactional
	public List<String> getLeaveTypeNamesByOrgLocationId(Long orgLocationId,String topleave){
		String query;
		Session session = this.sessionFactory.getCurrentSession();
		if("yes".equalsIgnoreCase(topleave))
		{
		 query = "select lt.NAME from LEAVE_TYPE lt where lt.STATUS =1 and lt.ORGLOCATION_ID =:orgLocationId";
		
	}else { 
		 query = "select lt.NAME from LEAVE_TYPE lt where lt.STATUS =1 and lt.ORGLOCATION_ID =:orgLocationId and lt.VISIBLE_IN_BALANCE_LEAVES='Y'";
	}
		Query queryString = session.createSQLQuery(query);
		queryString.setLong("orgLocationId",orgLocationId);
		List<String> leaveTypeNames = queryString.list(); 

		return leaveTypeNames;	
	
	}

	@Override
	@Transactional
	public Map<String,List<LeaveBalanceReportDTO>> getLeaveNamesByOrgLocationId(Long orgLocationId,List<Long> userleaveTypeList,List<String> leaveTypesList,String topleave){
		HashMap<String,List<LeaveBalanceReportDTO>> resultMap = new HashMap<>();
		String query;
		try {
		Session session = this.sessionFactory.getCurrentSession();
		
		String subQuery1 ="";
		if(userleaveTypeList !=null && userleaveTypeList.size() > 0)
			subQuery1 = " and lt.ID IN (:userleaveTypeList)";
		String subQuery3 ="";
		if(leaveTypesList !=null && leaveTypesList.size() > 0)
			subQuery3 = " and lt.LEAVE_TYPE IN (:leaveTypesList)";
		if("yes".equalsIgnoreCase(topleave)) {
	    query = "select lt.NAME AS name,lt.ID AS id from LEAVE_TYPE lt where  lt.STATUS =1  and lt.ORGLOCATION_ID =:orgLocationId"+subQuery1+subQuery3;
		}else {
			query = "select lt.NAME AS name,lt.ID AS id from LEAVE_TYPE lt where  lt.STATUS =1 and lt.VISIBLE_IN_BALANCE_LEAVES='Y' and lt.ORGLOCATION_ID =:orgLocationId"+subQuery1+subQuery3;
		}
		String query1 ="select d.DEPARTMENT_NAME AS deparmentNames,d.ID AS departmentId from DEPARTMENT d where d.ORG_LOCATION_ID =:orgLocationId";
		Query queryString1 = session.createSQLQuery(query1);
		queryString1.setLong("orgLocationId",orgLocationId);
		Query queryString = session.createSQLQuery(query);
		if(userleaveTypeList !=null && userleaveTypeList.size() > 0)
		queryString.setParameterList("userleaveTypeList",userleaveTypeList);
		if(leaveTypesList !=null && leaveTypesList.size() > 0)
		queryString.setParameterList("leaveTypesList",leaveTypesList);
		queryString.setLong("orgLocationId",orgLocationId);
		queryString.setResultTransformer(Transformers.aliasToBean(LeaveBalanceReportDTO.class));
		queryString1.setResultTransformer(Transformers.aliasToBean(LeaveBalanceReportDTO.class));
		List<LeaveBalanceReportDTO> departments = (List<LeaveBalanceReportDTO>) queryString1.list();
		if(departments !=null && departments.size() > 0)
		resultMap.put("DEPARMENTS", departments);
		resultMap.put("LEAVETYPES", queryString.list());
		return resultMap;
	}catch (Exception e) {
		logger.error("Leave and Department List Loading Failed Due to "
				+ e.getLocalizedMessage());
	}
	return resultMap;

	}


	@Override
	@Transactional
	public String getDepartmentTypeNamesByOrgLocationIdAndUserId(Long userId,Long orgLocationId)  {
		String depart =new String();
		try {
			LeaveBalanceReportDTO depar = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query="select d.DEPARTMENT_NAME As department  from DEPARTMENT d,EMPLOYEE e where e.DEPARTMENT_ID=d.ID and e.USER_ID =:userId and e.ORG_LOCATIONS_ID=:orgLocationId";
		Query department=session.createSQLQuery(query);
		department.setLong("userId", userId);
		department.setLong("orgLocationId", orgLocationId);
	    department.setResultTransformer(Transformers.aliasToBean(LeaveBalanceReportDTO.class));
		List<LeaveBalanceReportDTO> dep=department.list();
		if(dep !=null && dep.size() > 0) {
		 depar =dep.get(0);
		 String departk = depar.getDepartment();
		return departk;
	}}catch (Exception e) {
		logger.error("Leave and Department List Loading Failed Due to "
				+ e.getLocalizedMessage());
}
		return depart;
	}

	@Override
	@Transactional
	public Float getTakenLeavesByOrgLocationIdAndUserId(Long leaveId,Long userId,Long orglocationId)throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		float departk=0;
		LeaveBalanceReportDTO taken = null;
		String query="select ul.NUMBER_OF_DAYS AS leaveTaken from USER_LEAVE ul where ul.LEAVE_TYPE_ID=:leaveId and ul.USER_ID=:userId and ul.ORGLOCATION_ID=:orglocationId ";
		Query leaves =session.createSQLQuery(query);
		leaves.setLong("leaveId", leaveId);
		leaves.setLong("userId", userId);
		leaves.setLong("orglocationId", orglocationId);
		leaves.setResultTransformer(Transformers.aliasToBean(LeaveBalanceReportDTO.class));
		List<LeaveBalanceReportDTO> leavesTaken =leaves.list();
		if(leavesTaken !=null && leavesTaken.size() > 0) {
			taken =leavesTaken.get(0);
			  departk = taken.getLeaveTaken();			
	        }return departk;
		}
		
		
		
		@Override
    @Transactional
    public ResponseList<LeaveReportView> getLeaveReportsOrglocationIdAndFromToDate(DayWiseLeaveReportDTO dayWiseLeaveReportDTO, Long orgId, Long orgLocationId, String loginUserRole) throws Exception
    {
        OrgLocations orgLoc = orgLocationService.getOrgLocations(orgLocationId);
        SimpleDateFormat format = new SimpleDateFormat(orgLoc.getDateFormat());
        Date date = null;
        java.sql.Date sqlDate = null;
        List<LeaveReportView> leaveReportList = null;
        ResponseList<LeaveReportView> response = new ResponseList<>();
        List<Long> countlist = null;
        String sortingQry = "";
        /*
         * if (StringUtils.isEmpty(dayWiseLeaveReportDTO.getFromDate())) {
         * date=format.parse(dayWiseLeaveReportDTO.getFromDate()); date =
         * commonUtil.getOrgLocationCurrentDate(orgLoc.getDateFormat(), date);
         * sqlDate = new java.sql.Date(date.getTime()); }
         */
        Session session = this.sessionFactory.getCurrentSession();
        try {
            if (dayWiseLeaveReportDTO.getFromDate() != null
                    && !dayWiseLeaveReportDTO.getFromDate().isEmpty()) {
                date = format.parse(dayWiseLeaveReportDTO.getFromDate());
                date = commonUtil.getOrgLocationCurrentDate(orgLoc.getDateFormat(), date);
                sqlDate = new java.sql.Date(date.getTime());
            }
            else {
                date = commonUtil.getOrgLocationCurrentDate(orgLoc.getDateFormat(), new Date());
                sqlDate = new java.sql.Date(date.getTime());
            }
            String query = null;
            if (loginUserRole.equalsIgnoreCase("ROLE_ADMIN")) {
                query = "from LeaveReportView leaveReport where (leaveReport.orglocationId = :orgLocationId or leaveReport.orgId = :orgId) and ((:fromDate BETWEEN leaveReport.fromDate AND leaveReport.toDate) or (leaveReport.fromDate = :fromDate and leaveReport.toDate = :fromDate)) and (leaveReport.status = 'APPROVED' or leaveReport.status = 'PENDING')";
            }
            else {
                query = "from LeaveReportView leaveReport where (leaveReport.orglocationId = :orgLocationId or leaveReport.orgId = :orgId) and leaveReport.reportingManagerId = :loginUserId and ((:fromDate BETWEEN leaveReport.fromDate AND leaveReport.toDate) or (leaveReport.fromDate = :fromDate and leaveReport.toDate = :fromDate)) and (leaveReport.status = 'APPROVED' or leaveReport.status = 'PENDING')";
            }
            // String query = "from LeaveReportView leaveReport where
            // (leaveReport.orglocationId = :orgLocationId or leaveReport.orgId
            // = :orgId) and ((:fromDate BETWEEN leaveReport.fromDate AND
            // leaveReport.toDate) or (leaveReport.fromDate = :fromDate and
            // leaveReport.toDate = :fromDate)) and (leaveReport.status =
            // 'APPROVED' or leaveReport.status = 'PENDING')";
            StringBuilder query1 = new StringBuilder();
            if (StringUtils.isNotBlank(dayWiseLeaveReportDTO.getSearchValue())) {
                Pattern pattern = Pattern.compile("^(\\d{0,4}(\\-|\\/{1})\\d{0,4}(\\-|\\/{1})\\d{0,4})$");
                Matcher matcher = pattern.matcher(dayWiseLeaveReportDTO.getSearchValue());
                if (matcher.matches() == true) {
                    if (dayWiseLeaveReportDTO.getSearchValue().contains("/")
                            || (dayWiseLeaveReportDTO.getSearchValue().contains("-"))) {
                        String replacedValue = dayWiseLeaveReportDTO.getSearchValue().replaceAll("/", "-");
                        Date date1 = format.parse(replacedValue);
                        sqlDate = new java.sql.Date(date1.getTime());
                    }
                    if (sqlDate != null) {
                        query1.append("OR (leaveReport.fromDate like '%" + sqlDate
                                + "%' and leaveReport.toDate like '%" + sqlDate + "%'" + ") ");
                    }
                }
                query1.append(" and (leaveReport.name like '%"
                        + dayWiseLeaveReportDTO.getSearchValue()
                        + "%' ").append("OR leaveReport.employmentType like '%"
                                + dayWiseLeaveReportDTO.getSearchValue()
                                + "%' ").append("OR leaveReport.leaveTypeName like '%"
                                        + dayWiseLeaveReportDTO.getSearchValue()
                                        + "%' ").append("OR leaveReport.leaveTypeBillable like '%"
                                                + dayWiseLeaveReportDTO.getSearchValue()
                                                + "%' ").append("OR leaveReport.reason like '%"
                                                        + dayWiseLeaveReportDTO.getSearchValue()
                                                        + "%' ").append("OR leaveReport.approver like '%"
                                                                + dayWiseLeaveReportDTO.getSearchValue()
                                                                + "%' ").append("OR leaveReport.status like '%"
                                                                        + dayWiseLeaveReportDTO.getSearchValue()
                                                                        + "%') ");
            }
            if (StringUtils.isNotBlank(dayWiseLeaveReportDTO.getSortColumn())) {
                sortingQry = " order by leaveReport." + dayWiseLeaveReportDTO.getSortColumn() + " "
                        + dayWiseLeaveReportDTO.getSortOrder();
            }
            Query queryString = null;
            Query countQueryString;
            String userRoleSubQuery = "";
            String deptSubQuery = "";
            String employmentTypeSubQuery = "";
            String leaveTypeSubQuery = "";
            String leaveTypeBillableTypeSubQuery = "";
            if (dayWiseLeaveReportDTO.getUserRole() != null
                    && !dayWiseLeaveReportDTO.getUserRole().isEmpty()) {
                userRoleSubQuery = " and leaveReport.roleId  IN (:userRoleList)";
            }
            if (dayWiseLeaveReportDTO.getOrgDepartments() != null
                    && !dayWiseLeaveReportDTO.getOrgDepartments().isEmpty()) {
                deptSubQuery = " and leaveReport.deptId  IN (:departmentsList)";
            }
            if (dayWiseLeaveReportDTO.getEmploymentType() != null
                    && !dayWiseLeaveReportDTO.getEmploymentType().isEmpty()) {
                employmentTypeSubQuery = " and leaveReport.employmentType  IN (:employmentTypeList)";
            }
            if (dayWiseLeaveReportDTO.getLeaveTypeName() != null && !dayWiseLeaveReportDTO.getLeaveTypeName().isEmpty()) {
                leaveTypeSubQuery = " and leaveReport.leaveTypeId  IN (:leaveTypeNamesList)";
            }
            if (dayWiseLeaveReportDTO.getLeaveTypeBillable() != null
                    && !dayWiseLeaveReportDTO.getLeaveTypeBillable().isEmpty()) {
                leaveTypeBillableTypeSubQuery = " and leaveReport.leaveTypeBillable  IN (:leaveTypeBillableList)";
            }
            queryString = session.createQuery(query + query1.toString() + " " + userRoleSubQuery
                    + " " + deptSubQuery + " " + employmentTypeSubQuery + " " + leaveTypeSubQuery
                    + " " + leaveTypeBillableTypeSubQuery + " " + sortingQry);
            List<Long> userRoleList = new ArrayList<>();
            List<Long> departmentsList = new ArrayList<>();
            List<Long> leaveTypeNamesList = new ArrayList<>();
            List<String> employmentTypeList = new ArrayList<>();
            List<String> leaveTypeBillableList = new ArrayList<>();
            if (dayWiseLeaveReportDTO.getUserRole() != null
                    && !dayWiseLeaveReportDTO.getUserRole().isEmpty()) {
                String[] userRoles = dayWiseLeaveReportDTO.getUserRole().split(",");
                for (int i = 0; i < userRoles.length; i++) {
                    userRoleList.add(Long.parseLong(userRoles[i]));
                }
            }
            if (dayWiseLeaveReportDTO.getOrgDepartments() != null
                    && !dayWiseLeaveReportDTO.getOrgDepartments().isEmpty()) {
                String[] orgDepts = dayWiseLeaveReportDTO.getOrgDepartments().split(",");
                for (int i = 0; i < orgDepts.length; i++) {
                    departmentsList.add(Long.parseLong(orgDepts[i]));
                }
            }
            if (dayWiseLeaveReportDTO.getLeaveTypeName() != null
                    && !dayWiseLeaveReportDTO.getLeaveTypeName().isEmpty()) {
                String[] leaveTypeName = dayWiseLeaveReportDTO.getLeaveTypeName().split(",");
                for (int i = 0; i < leaveTypeName.length; i++) {
                    leaveTypeNamesList.add(Long.parseLong(leaveTypeName[i]));
                }
            }
            if (dayWiseLeaveReportDTO.getEmploymentType() != null
                    && !dayWiseLeaveReportDTO.getEmploymentType().isEmpty()) {
                String[] empType = dayWiseLeaveReportDTO.getEmploymentType().split(",");
                for (int i = 0; i < empType.length; i++) {
                    employmentTypeList.add(empType[i]);
                    employmentTypeList.add(dayWiseLeaveReportDTO.getEmploymentType());
                }
            }
            if (dayWiseLeaveReportDTO.getLeaveTypeBillable() != null
                    && !dayWiseLeaveReportDTO.getLeaveTypeBillable().isEmpty()) {
                String[] leaveType = dayWiseLeaveReportDTO.getLeaveTypeBillable().split(",");
                for (int i = 0; i < leaveType.length; i++) {
                    leaveTypeBillableList.add(leaveType[i]);
                    leaveTypeBillableList.add(dayWiseLeaveReportDTO.getLeaveTypeBillable());
                }
            }
            if (!userRoleList.isEmpty()) {
                queryString.setParameterList("userRoleList", userRoleList);
            }
            if (!departmentsList.isEmpty()) {
                queryString.setParameterList("departmentsList", departmentsList);
            }
            if (!employmentTypeList.isEmpty()) {
                queryString.setParameterList("employmentTypeList", employmentTypeList);
            }
            if (!leaveTypeNamesList.isEmpty()) {
                queryString.setParameterList("leaveTypeNamesList", leaveTypeNamesList);
            }
            if (!leaveTypeBillableList.isEmpty()) {
                queryString.setParameterList("leaveTypeBillableList", leaveTypeBillableList);
            }
            queryString.setLong("orgLocationId", orgLocationId);
            queryString.setLong("orgId", orgId);
            queryString.setDate("fromDate", sqlDate);
            if (!loginUserRole.equalsIgnoreCase("ROLE_ADMIN")) {
                queryString.setLong("loginUserId", dayWiseLeaveReportDTO.getLoginUserId());
            }
            leaveReportList = queryString.list();
            String countQuery1=null;
            countQuery1=query + query1.toString() + " " + userRoleSubQuery
                    + " " + deptSubQuery + " " + employmentTypeSubQuery + " " + leaveTypeSubQuery
                    + " " + leaveTypeBillableTypeSubQuery + " " + sortingQry;
            
            countQueryString = session.createQuery(("select count(*) " + countQuery1));
            countQueryString.setParameter("orgLocationId", orgLocationId);
            countQueryString.setParameter("orgId", orgId);
            countQueryString.setParameter("fromDate", sqlDate);
            if (!loginUserRole.equalsIgnoreCase("ROLE_ADMIN")) {
                countQueryString.setLong("loginUserId", dayWiseLeaveReportDTO.getLoginUserId());
            }
            if (!userRoleList.isEmpty()) {
                countQueryString.setParameterList("userRoleList", userRoleList);
            }
            if (!departmentsList.isEmpty()) {
                countQueryString.setParameterList("departmentsList", departmentsList);
            }
            if (!employmentTypeList.isEmpty()) {
                countQueryString.setParameterList("employmentTypeList", employmentTypeList);
            }
            if (!leaveTypeNamesList.isEmpty()) {
                countQueryString.setParameterList("leaveTypeNamesList", leaveTypeNamesList);
            }
            if (!leaveTypeBillableList.isEmpty()) {
                countQueryString.setParameterList("leaveTypeBillableList", leaveTypeBillableList);
            }
            countlist = countQueryString.list();
            int count = 0;
            int filteredCount = 0;
            if (countlist != null && !countlist.isEmpty()) {
                count = countlist.iterator().next().intValue();
            }
            response.setRecordsTotal(count);
            if (leaveReportList != null) {
                List<Long> filteredCountlist = null;
                    filteredCountlist = countQueryString.list();
                
                if (filteredCountlist != null && !filteredCountlist.isEmpty()) {
                    filteredCount = filteredCountlist.iterator().next().intValue();
                }
                response.setRecordsFiltered(filteredCount);
            }
            response.setData(leaveReportList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
    
    @Override
    @Transactional
    public ResponseList<LeaveReportView> getLeaveReportsOrglocationIdAndFromToDate(HrLeaveReportDTO hrLeaveReportDTO, Long orgId, Long orgLocationId, String loginUserRole) throws Exception
    {
        OrgLocations orgLoc = orgLocationService.getOrgLocations(orgLocationId);
        SimpleDateFormat format = new SimpleDateFormat(orgLoc.getDateFormat());
        Date startDate = null;
        Date endDate = null;
        java.sql.Date startDateSqlDate = null;
        java.sql.Date endDateSqlDate = null;
        String query = null;
        Query queryString = null;
        Query countQueryString;
        List<LeaveReportView> leaveReportList = null;
        ResponseList<LeaveReportView> response = new ResponseList<>();
        List<Long> countlist = null;
        Session session = this.sessionFactory.getCurrentSession();
        try {
            if (hrLeaveReportDTO.getStartDate() != null
                    && StringUtils.isNotEmpty(hrLeaveReportDTO.getStartDate())
                    && hrLeaveReportDTO.getEndDate() != null
                    && StringUtils.isNotEmpty(hrLeaveReportDTO.getEndDate())) {
                startDate = format.parse(hrLeaveReportDTO.getStartDate());
                endDate = format.parse(hrLeaveReportDTO.getEndDate());
                startDate = commonUtil.getOrgLocationCurrentDate(orgLoc.getDateFormat(), startDate);
                endDate = commonUtil.getOrgLocationCurrentDate(orgLoc.getDateFormat(), endDate);
                startDateSqlDate = new java.sql.Date(startDate.getTime());
                endDateSqlDate = new java.sql.Date(endDate.getTime());
            }
            List<Long> userHierarchyIdList = new LinkedList<>();
            String userHierarchyIdString = "";
            if (!loginUserRole.equalsIgnoreCase("ROLE_ADMIN")) {
            	userHierarchyIdList = userDao.getUserHierarchyIdList(hrLeaveReportDTO.getLoginUserId());
            	if(!userHierarchyIdList.contains(hrLeaveReportDTO.getLoginUserId())) {
            		userHierarchyIdList.add(hrLeaveReportDTO.getLoginUserId());
            	}
            	if(userHierarchyIdList != null && !userHierarchyIdList.isEmpty()) {
            		userHierarchyIdString = StringUtils.join(userHierarchyIdList, ',');
            	}
            }
            if(userHierarchyIdString.isEmpty()) {
            	userHierarchyIdString = hrLeaveReportDTO.getLoginUserId() + "";
            }
            if (loginUserRole.equalsIgnoreCase("ROLE_ADMIN") && hrLeaveReportDTO.getStatus() != null
                    && !hrLeaveReportDTO.getStatus().isEmpty()) {
                query = "from LeaveReportView leaveReport where (leaveReport.orglocationId = :orgLocationId or leaveReport.orgId = :orgId) and (leaveReport.fromDate >= :fromDate AND leaveReport.toDate <= :toDate)";
            }
            else if (!loginUserRole.equalsIgnoreCase("ROLE_ADMIN")
                    && hrLeaveReportDTO.getStatus().isEmpty()) {
                query = "from LeaveReportView leaveReport where (leaveReport.orglocationId = :orgLocationId or leaveReport.orgId = :orgId) and leaveReport.reportingManagerId IN (" + userHierarchyIdString + ") and (leaveReport.fromDate >= :fromDate AND leaveReport.toDate <= :toDate)";
            }
            else if (!loginUserRole.equalsIgnoreCase("ROLE_ADMIN")
                    && !hrLeaveReportDTO.getStatus().isEmpty()) {
                query = "from LeaveReportView leaveReport where (leaveReport.orglocationId = :orgLocationId or leaveReport.orgId = :orgId) and leaveReport.reportingManagerId IN (" + userHierarchyIdString + ") and (leaveReport.fromDate >= :fromDate AND leaveReport.toDate <= :toDate)";
            }
            else if (loginUserRole.equalsIgnoreCase("ROLE_ADMIN")
                    && hrLeaveReportDTO.getStatus().isEmpty()) {
                query = "from LeaveReportView leaveReport where (leaveReport.orglocationId = :orgLocationId or leaveReport.orgId = :orgId) and (leaveReport.fromDate >= :fromDate AND leaveReport.toDate <= :toDate)";
            }
            else {
                query = "from LeaveReportView leaveReport where (leaveReport.orglocationId = :orgLocationId or leaveReport.orgId = :orgId) and leaveReport.reportingManagerId IN (" + userHierarchyIdString + ") and (leaveReport.fromDate >= :fromDate AND leaveReport.toDate <= :toDate)";
            }
            StringBuilder query1 = new StringBuilder();
            if (StringUtils.isNotBlank(hrLeaveReportDTO.getSearchValue())) {
                Pattern pattern = Pattern.compile("^(\\d{0,4}(\\-|\\/{1})\\d{0,4}(\\-|\\/{1})\\d{0,4})$");
                Matcher matcher = pattern.matcher(hrLeaveReportDTO.getSearchValue());
                if (matcher.matches() == true) {
                    if (hrLeaveReportDTO.getSearchValue().contains("/")
                            || (hrLeaveReportDTO.getSearchValue().contains("-"))) {
                        String replacedValue = hrLeaveReportDTO.getSearchValue().replaceAll("/", "-");
                        Date date1 = format.parse(replacedValue);
                        startDateSqlDate = new java.sql.Date(date1.getTime());
                    }
                    if (startDateSqlDate != null) {
                        query1.append("OR (leaveReport.fromDate like '%" + startDateSqlDate
                                + "%' and leaveReport.toDate like '%" + startDateSqlDate + "%'"
                                + ") ");
                    }
                }
                query1.append(" and (leaveReport.name like '%" + hrLeaveReportDTO.getSearchValue()
                        + "%' ").append("OR leaveReport.employmentType like '%"
                                + hrLeaveReportDTO.getSearchValue()
                                + "%' ").append("OR leaveReport.leaveTypeName like '%"
                                        + hrLeaveReportDTO.getSearchValue()
                                        + "%' ").append("OR leaveReport.leaveTypeBillable like '%"
                                                + hrLeaveReportDTO.getSearchValue()
                                                + "%' ").append("OR leaveReport.reason like '%"
                                                        + hrLeaveReportDTO.getSearchValue()
                                                        + "%' ").append("OR leaveReport.approver like '%"
                                                                + hrLeaveReportDTO.getSearchValue()
                                                                + "%' ").append("OR leaveReport.status like '%"
                                                                        + hrLeaveReportDTO.getSearchValue()
                                                                        + "%' ").append("OR leaveReport.eid like '%"
                                                                                + hrLeaveReportDTO.getSearchValue()
                                                                                + "%') ");
                /*
                 * if (startDateSqlDate != null) {
                 * query1.append("OR (leaveReport.fromDate <=" +
                 * startDateSqlDate + " and leaveReport.toDate >=" +
                 * startDateSqlDate + ") "); }
                 */
            }
            String deptSubQuery = "";
            String userRoleSubQuery = "";
            String employmentTypeSubQuery = "";
            String leaveTypeSubQuery = "";
            String associateSubQuery = "";
            String leaveStatusSubQuery = "";
            if (hrLeaveReportDTO.getReportRolesId() != null
                    && !hrLeaveReportDTO.getReportRolesId().isEmpty()) {
                userRoleSubQuery = " and leaveReport.roleId  IN (:userRoleList)";
            }
            if (hrLeaveReportDTO.getDepartmentIds() != null
                    && !hrLeaveReportDTO.getDepartmentIds().isEmpty()) {
                deptSubQuery = " and leaveReport.deptId  IN (:departmentsList)";
            }
            if (hrLeaveReportDTO.getEmploymentType() != null
                    && !hrLeaveReportDTO.getEmploymentType().isEmpty()) {
                employmentTypeSubQuery = " and leaveReport.employmentType  IN (:employmentTypeList)";
            }
            if (hrLeaveReportDTO.getLeaveTypeIds() != null
                    && !hrLeaveReportDTO.getLeaveTypeIds().isEmpty()) {
                leaveTypeSubQuery = " and leaveReport.leaveTypeId  IN (:leaveTypeNamesList)";
            }
            if (hrLeaveReportDTO.getAssociateListId() != null
                    && !hrLeaveReportDTO.getAssociateListId().isEmpty()) {
                associateSubQuery = " and leaveReport.userId  IN (:associateNamesList)";
            }
            if (hrLeaveReportDTO.getStatus() != null && !hrLeaveReportDTO.getStatus().isEmpty()) {
                leaveStatusSubQuery = " and leaveReport.status  IN (:leaveStatusList)";
            }
            /*
             * queryString = session.createQuery(query + query1.toString() + " "
             * + userRoleSubQuery + " " + deptSubQuery + " " +
             * employmentTypeSubQuery + " " + leaveTypeSubQuery + " " +
             * sortingQry);
             */
            String sortingQry = "";
            if (StringUtils.isNotBlank(hrLeaveReportDTO.getSortColumn())) {
                sortingQry = " order by leaveReport." + hrLeaveReportDTO.getSortColumn() + " "
                        + hrLeaveReportDTO.getSortOrder();
            }
            queryString = session.createQuery(query + " " + query1.toString() + " " + deptSubQuery
                    + " " + userRoleSubQuery + " " + employmentTypeSubQuery + " "
                    + associateSubQuery + " " + leaveTypeSubQuery + " " + leaveStatusSubQuery + " "
                    + sortingQry);
            List<Long> userRoleList = new ArrayList<>();
            List<Long> departmentsList = new ArrayList<>();
            List<Long> leaveTypeNamesList = new ArrayList<>();
            List<String> employmentTypeList = new ArrayList<>();
            List<Long> associateNamesList = new ArrayList<>();
            List<String> leaveStatusList = new ArrayList<>();
            if (hrLeaveReportDTO.getReportRolesId() != null
                    && !hrLeaveReportDTO.getReportRolesId().isEmpty()) {
                String[] userRoles = hrLeaveReportDTO.getReportRolesId().split(",");
                for (int i = 0; i < userRoles.length; i++) {
                    userRoleList.add(Long.parseLong(userRoles[i]));
                }
            }
            if (hrLeaveReportDTO.getDepartmentIds() != null
                    && !hrLeaveReportDTO.getDepartmentIds().isEmpty()) {
                String[] dept = hrLeaveReportDTO.getDepartmentIds().split(",");
                for (int i = 0; i < dept.length; i++) {
                    departmentsList.add(Long.parseLong(dept[i]));
                }
            }
            if (hrLeaveReportDTO.getLeaveTypeIds() != null
                    && !hrLeaveReportDTO.getLeaveTypeIds().isEmpty()) {
                String[] leaveTypeID = hrLeaveReportDTO.getLeaveTypeIds().split(",");
                for (int i = 0; i < leaveTypeID.length; i++) {
                    leaveTypeNamesList.add(Long.parseLong(leaveTypeID[i]));
                }
            }
            if (hrLeaveReportDTO.getEmploymentType() != null
                    && !hrLeaveReportDTO.getEmploymentType().isEmpty()) {
                String[] empType = hrLeaveReportDTO.getEmploymentType().split(",");
                for (int i = 0; i < empType.length; i++) {
                    employmentTypeList.add(empType[i]);
                }
            }
            if (hrLeaveReportDTO.getAssociateListId() != null
                    && !hrLeaveReportDTO.getAssociateListId().isEmpty()) {
                String[] associateId = hrLeaveReportDTO.getAssociateListId().split(",");
                for (int i = 0; i < associateId.length; i++) {
                    associateNamesList.add(Long.parseLong(associateId[i]));
                }
            }
            if (hrLeaveReportDTO.getStatus() != null && !hrLeaveReportDTO.getStatus().isEmpty()) {
                String[] status = hrLeaveReportDTO.getStatus().split(",");
                for (int i = 0; i < status.length; i++) {
                    leaveStatusList.add(status[i]);
                }
            }
            if (!userRoleList.isEmpty()) {
                queryString.setParameterList("userRoleList", userRoleList);
            }
            if (!departmentsList.isEmpty()) {
                queryString.setParameterList("departmentsList", departmentsList);
            }
            if (!employmentTypeList.isEmpty()) {
                queryString.setParameterList("employmentTypeList", employmentTypeList);
            }
            if (!leaveTypeNamesList.isEmpty()) {
                queryString.setParameterList("leaveTypeNamesList", leaveTypeNamesList);
            }
            if (!associateNamesList.isEmpty()) {
                queryString.setParameterList("associateNamesList", associateNamesList);
            }
            if (!leaveStatusList.isEmpty()) {
                queryString.setParameterList("leaveStatusList", leaveStatusList);
            }
            queryString.setLong("orgLocationId", orgLocationId);
            queryString.setLong("orgId", orgId);
            queryString.setDate("fromDate", startDateSqlDate);
            queryString.setDate("toDate", endDateSqlDate);
            /*if (!loginUserRole.equalsIgnoreCase("ROLE_ADMIN")) {
//                queryString.setLong("loginUserId", hrLeaveReportDTO.getLoginUserId());
            }*/
            leaveReportList = queryString.list();
            
            String countQuery1=null;
            countQuery1=query + " " + query1.toString() + " " + deptSubQuery
                            + " " + userRoleSubQuery + " " + employmentTypeSubQuery + " "
                            + associateSubQuery + " " + leaveTypeSubQuery + " " + leaveStatusSubQuery + " "
                            + sortingQry;
            
            countQueryString = session.createQuery(("select count(*) " + countQuery1));
            countQueryString.setParameter("orgLocationId", orgLocationId);
            countQueryString.setParameter("orgId", orgId);
            countQueryString.setParameter("fromDate", startDateSqlDate);
            countQueryString.setParameter("toDate", endDateSqlDate);
            if (!leaveStatusList.isEmpty()) {
                countQueryString.setParameterList("leaveStatusList", leaveStatusList);
            }
            if (!userRoleList.isEmpty()) {
                countQueryString.setParameterList("userRoleList", userRoleList);
            }
            if (!departmentsList.isEmpty()) {
                countQueryString.setParameterList("departmentsList", departmentsList);
            }
            if (!employmentTypeList.isEmpty()) {
                countQueryString.setParameterList("employmentTypeList", employmentTypeList);
            }
            if (!leaveTypeNamesList.isEmpty()) {
                countQueryString.setParameterList("leaveTypeNamesList", leaveTypeNamesList);
            }
            if (!associateNamesList.isEmpty()) {
                countQueryString.setParameterList("associateNamesList", associateNamesList);
            }
            /*if (!loginUserRole.equalsIgnoreCase("ROLE_ADMIN")) {
                countQueryString.setLong("loginUserId", hrLeaveReportDTO.getLoginUserId());
            }*/
            countlist = countQueryString.list();
            response.setData(leaveReportList);
            int count = 0;
            int filteredCount = 0;
            if (countlist != null && !countlist.isEmpty()) {
                count = countlist.iterator().next().intValue();
            }
            response.setRecordsTotal(count);
            if (leaveReportList != null) {
                List<Long> filteredCountlist = null;
            
                    filteredCountlist = countQueryString.list();
                
                if (filteredCountlist != null && !filteredCountlist.isEmpty()) {
                    filteredCount = filteredCountlist.iterator().next().intValue();
                }
                response.setRecordsFiltered(filteredCount);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }


	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Set<PayrollLopHis> getLeaveByFrequency(Long userId, Date freqStartDate, Date freqEndDate, OrgLocations orgLocation,EmployeePayroll payroll)
	{
		    List<LeaveType>  appliedLeaveList = null;
		    Set<PayrollLopHis> lovLeaves = null;
			try {
	    	   Session session = this.sessionFactory.getCurrentSession();
	           String query = "from LeaveType leaveType where leaveType.orgLocations.id =:orgLocationId";
	    	    Query queryObj = session.createQuery(query);
				queryObj.setLong("orgLocationId", orgLocation.getId()).list();//.setLong("userId", userId).setLong("orgId", orgLocation.getOrg().getId()).setParameter("startDate", startDate).setParameter("endDate", endDate).list();
				appliedLeaveList = queryObj.list();
				if(appliedLeaveList !=null && appliedLeaveList.size() >0) {
					lovLeaves = leaveManagementUtil.getToalAppliedLeavesByCandidaet(appliedLeaveList,orgLocation,freqStartDate,freqEndDate,userId,payroll);
				}
			}catch (Exception e) {
	    	   e.printStackTrace();
		}
		return lovLeaves;
		
		

//        query = "from LeaveReportView leaveReport where (leaveReport.orglocationId = :orgLocationId or leaveReport.orgId = :orgId) and leaveReport.reportingManagerId = :loginUserId and (leaveReport.fromDate >= :fromDate AND leaveReport.toDate <= :toDate) and leaveReport.status = 'APPROVED' ";

	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Set<PayrollLopHis> getLeaveByFrequencyInd(Long userId, Date freqStartDate, Date freqEndDate, OrgLocations orgLocation, IndEmployeePayroll payroll)
	{
		    List<LeaveType>  appliedLeaveList = null;
		    Set<PayrollLopHis> lovLeaves = null;
			try {
	    	   Session session = this.sessionFactory.getCurrentSession();
	           String query = "from LeaveType leaveType where leaveType.orgLocations.id =:orgLocationId";
	    	    Query queryObj = session.createQuery(query);
				queryObj.setLong("orgLocationId", orgLocation.getId()).list();
				appliedLeaveList = queryObj.list();
				if(appliedLeaveList !=null && appliedLeaveList.size() >0) {
					lovLeaves = leaveManagementUtil.getToalAppliedLeavesByCandidaetInd(appliedLeaveList,orgLocation,freqStartDate,freqEndDate,userId,payroll);
				}
			}catch (Exception e) {
	    	   e.printStackTrace();
		}
		return lovLeaves;
	}


	@SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<Leave> getAllLeavesByLocationIdAndStatusAndAppliedDate(Long locationId, String status,
            Date appliedDate) {
        java.sql.Date sqlappliedDate = new java.sql.Date(appliedDate.getTime());
        Session session = this.sessionFactory.getCurrentSession();
        List<Leave> list = null;
        String query = "from Leave leave left join fetch leave.leaveType where leave.orgLocations.id =:orgLocationId and leave.status =:status and leave.createdDate =:sqlappliedDate";
        list = session.createQuery(query).setString("status", status).setLong("orgLocationId", locationId).setDate("sqlappliedDate", sqlappliedDate).list();
        return list;
    }
	
	@SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<Leave> getAllLeavesByLocationIdAndStatusAndLeaveStartDate(Long locationId, String status,
            Date appliedDate) {
        java.sql.Date sqlappliedDate = new java.sql.Date(appliedDate.getTime());
        Session session = this.sessionFactory.getCurrentSession();
        List<Leave> list = null;
        String query = "from Leave leave left join fetch leave.leaveType where leave.orgLocations.id =:orgLocationId and leave.status =:status and leave.fromDate =:sqlappliedDate";
        list = session.createQuery(query).setString("status", status).setLong("orgLocationId", locationId).setDate("sqlappliedDate", sqlappliedDate).list();
        return list;
    }
}

