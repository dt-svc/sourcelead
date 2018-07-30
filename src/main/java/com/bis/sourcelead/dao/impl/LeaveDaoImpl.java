package com.bis.sourcelead.dao.impl;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.bouncycastle.asn1.x509.RoleSyntax;
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
import com.bis.sourcelead.elk.install.ApproverHierarchyToRequirement;
import com.bis.sourcelead.entity.AppliedLeaveDateView;
import com.bis.sourcelead.entity.AssociateBalanceLeaveView;
import com.bis.sourcelead.entity.HolidayAndRestrictday;
import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.entity.LeaveApproveHierarchy;
import com.bis.sourcelead.entity.LeaveRole;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.MonthlyLeaves;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RoleHierarchy;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.HolidayService;
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
	LeaveManagementUtil leaveManagementUtil;
	
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
						      + " where (leave.orgLocations.id = :orgLocationId or leave.orgLocations.org.id =:orgId) and leave.status =  '"+leaveDTO.getLeaveStatus()+"'";
				countQuery ="from Leave leave where (leave.orgLocations.id = :orgLocationId or leave.orgLocations.org.id =:orgId) and leave.status = '"+leaveDTO.getLeaveStatus()+"'";
			}else {
				query = "from LeaveApproveHierarchy laph left join fetch laph.leaveid leave "
					      + " left join fetch leave.leaveType  leaveType"
					      + " left join fetch leave.user user"
					      + " where (leave.orgLocations.id = :orgLocationId or leave.orgLocations.org.id =:orgId) and laph.approverUserId.id= :userId and leave.status =  '"+leaveDTO.getLeaveStatus()+"'";
				countQuery ="from Leave leave where (leave.orgLocations.id = :orgLocationId or leave.orgLocations.org.id =:orgId) and laph.approverUserId.id= :userId and leave.status = '"+leaveDTO.getLeaveStatus()+"'";
			}
			
			if(StringUtils.isNotBlank(leaveDTO.getSearchValue())){
				searchQuery.append(" and (laph.leaveid.user.firstName like '%" + leaveDTO.getSearchValue() + "%' ")
				.append("OR laph.leaveid.user.lastName like '%" + leaveDTO.getSearchValue() + "%' ")
				.append("OR laph.leaveid.numberOfDays like '%" + leaveDTO.getSearchValue() + "%' ");
				if(Constants.LEAVE_APPROVED.equalsIgnoreCase(leaveDTO.getLeaveStatus())) {
					searchQuery.append("OR laph.leaveid.approvedBy.firstName like '%" + leaveDTO.getSearchValue() + "%' ")
					.append("OR laph.leaveid.approvedBy.lastName like '%" + leaveDTO.getSearchValue() + "%' ");
				}else if(Constants.LEAVE_REJECTED.equalsIgnoreCase(leaveDTO.getLeaveStatus())) {
					searchQuery.append("OR laph.leaveid.rejectedBy.firstName like '%" + leaveDTO.getSearchValue() + "%' ")
					.append("OR laph.leaveid.rejectedBy.firstName like '%" + leaveDTO.getSearchValue() + "%' ");
				} else if(Constants.LEAVE_CANCELLED.equalsIgnoreCase(leaveDTO.getLeaveStatus())) {
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
			
			Query queryObj = session.createQuery(query+searchQuery);
			if (leaveDTO != null && leaveDTO.getLength() > 0) {
				queryObj.setFirstResult(leaveDTO.getStart());
				queryObj.setMaxResults(leaveDTO.getLength());
				if("ROLE_ADMIN".equalsIgnoreCase(leaveDTO.getLoginUserRole())) {
				    queryObj.setLong("orgLocationId", orgLocations.getId()).setLong("orgId", orgLocations.getOrg().getId()).list();
				}else {
					queryObj.setLong("orgLocationId", orgLocations.getId()).setLong("orgId", orgLocations.getOrg().getId()).setLong("userId", usrId).list();
				}
			}
			leaveApproveHierarchyList = queryObj.list();
			int count = 0;
			int filteredCount = 0;
//			List<Long> countlist = session.createQuery("select count(*) " + countQuery+leavesearchQuery).setLong("orgLocationId", orgLocations.getId()).list();
			
			if(leaveApproveHierarchyList != null && !leaveApproveHierarchyList.isEmpty()) {
				count = leaveApproveHierarchyList.stream().filter(c->c.getLeaveid().getStatus().equalsIgnoreCase(leaveDTO.getLeaveStatus())).collect(Collectors.toList()).size();
			}
			response.setRecordsTotal(count);
			if (leaveDTO != null) {
//				List<Long> filteredCountlist  = session.createQuery("select count(*) " + countQuery+leavesearchQuery).setLong("orgLocationId", orgLocations.getId()).list();
				if(leaveApproveHierarchyList != null && !leaveApproveHierarchyList.isEmpty()) {
					filteredCount = leaveApproveHierarchyList.stream().filter(c->c.getLeaveid().getStatus().equalsIgnoreCase(leaveDTO.getLeaveStatus())).collect(Collectors.toList()).size();
				}
				response.setRecordsFiltered(filteredCount);
			}
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
			
			Query queryObj = session.createQuery(query+searchQuery);
			if (leaveDTO != null && leaveDTO.getLength() > 0) {
				queryObj.setFirstResult(leaveDTO.getStart());
				queryObj.setMaxResults(leaveDTO.getLength());
				if("ROLE_ADMIN".equalsIgnoreCase(leaveDTO.getLoginUserRole())) {
				  queryObj.setLong("orgLocationId", orgLocations.getId()).setLong("orgId", orgLocations.getOrg().getId()).setCacheable(false).list();
				}else {
			      queryObj.setLong("orgLocationId", orgLocations.getId()).setLong("orgId", orgLocations.getOrg().getId()).setLong("userId", usrId).setCacheable(false).list();
				}
			}
			leaveApproveHierarchyList = queryObj.list();
			int count = 0;
			int filteredCount = 0;
//			List<Long> countlist = session.createQuery("select count(*) " + countQuery+leavesearchQuery).setLong("orgLocationId", orgLocations.getId()).list();
			if(leaveApproveHierarchyList != null && !leaveApproveHierarchyList.isEmpty()) {
				count = leaveApproveHierarchyList.stream().filter(c->c.getLeaveid().getStatus().equalsIgnoreCase(leaveDTO.getLeaveStatus())).collect(Collectors.toList()).size();
			}
			response.setRecordsTotal(count);
			if (leaveDTO != null) {
//				List<Long> filteredCountlist  = session.createQuery("select count(*) " + countQuery+leavesearchQuery).setLong("orgLocationId", orgLocations.getId()).list();
				if(leaveApproveHierarchyList != null && !leaveApproveHierarchyList.isEmpty()) {
					filteredCount = leaveApproveHierarchyList.stream().filter(c->c.getLeaveid().getStatus().equalsIgnoreCase(leaveDTO.getLeaveStatus())).collect(Collectors.toList()).size();
				}
				response.setRecordsFiltered(filteredCount);
			} 
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
		if(!"Admin".equalsIgnoreCase(role.getUserType())){
			if(currentWeekStarDate != null && currentWeekEndDate != null){
				String query = "from LeaveApproveHierarchy laph left join fetch laph.leaveid leave left join fetch leave.leaveType left join fetch leave.user ltype where leave.orgLocations.id = :orgLocationId and laph.approverUserId.id= :userId and leave.status = :leaveStatus "+subQuery1+" "+subQuery2+" "+subQuery3+"";
				if(deptList.size() >0)
					return session.createQuery(query).setParameter("leaveStatus",status).setLong("orgLocationId", locationId).setLong("userId", usrId).setParameter("weekStartDate", currentWeekStarDate).setParameter("weekEnddate", currentWeekEndDate).setParameterList("deptList", deptList).list();
				else 
					return session.createQuery(query).setParameter("leaveStatus",status).setLong("orgLocationId", locationId).setLong("userId", usrId).setParameter("weekStartDate", currentWeekStarDate).setParameter("weekEnddate", currentWeekEndDate).list();
			}else{
				String query = "from LeaveApproveHierarchy laph left join fetch laph.leaveid leave left join fetch leave.leaveType left join fetch leave.user ltype where leave.orgLocations.id = :orgLocationId and laph.approverUserId.id= :userId and leave.status = :leaveStatus "+subQuery2+" "+subQuery3+"";
				if(deptList.size() >0)
					return session.createQuery(query).setParameter("leaveStatus",status).setLong("orgLocationId", locationId).setLong("userId", usrId).setParameter("leaveDate", leaveDate).setParameterList("deptList", deptList).list();
				else 
					return session.createQuery(query).setParameter("leaveStatus",status).setLong("orgLocationId", locationId).setLong("userId", usrId).setParameter("leaveDate", leaveDate).list();
			}
		}else{
			if(currentWeekStarDate != null && currentWeekEndDate != null){
				String query = "from LeaveApproveHierarchy laph left join fetch laph.leaveid leave left join fetch leave.leaveType left join fetch leave.user ltype where leave.orgLocations.id = :orgLocationId and leave.status = :leaveStatus "+subQuery1+" "+subQuery2+" "+subQuery3+"";
				if(deptList.size() >0)
					return session.createQuery(query).setParameter("leaveStatus",status).setLong("orgLocationId", locationId).setParameter("weekStartDate", currentWeekStarDate).setParameter("weekEnddate", currentWeekEndDate).setParameterList("deptList", deptList).list();
				else 
					return session.createQuery(query).setParameter("leaveStatus",status).setLong("orgLocationId", locationId).setParameter("weekStartDate", currentWeekStarDate).setParameter("weekEnddate", currentWeekEndDate).list();
			}else{
				String query = "from LeaveApproveHierarchy laph left join fetch laph.leaveid leave left join fetch leave.leaveType left join fetch leave.user ltype where leave.orgLocations.id = :orgLocationId and leave.status = :leaveStatus "+subQuery2+" "+subQuery3+"";
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
			query = "from LeaveApproveHierarchy lah left join fetch lah.leaveid leave left join fetch lah.approverUserId where leave.orgLocations.id = :orgLocationId";
			leavesList = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		}else {
			query = "from LeaveApproveHierarchy lah left join fetch lah.leaveid leave left join fetch lah.approverUserId where lah.approverUserId.id =:userId and (leave.orgLocations.id = :orgLocationId or leave.orgLocations.org.id =:orgId)";
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
		String query = "from Leave leave";
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
		String query = "from AppliedLeaveDateView aleave where aleave.orglocationId = :orgLocationId and aleave.userId = :userId and (( aleave.applyLeaveDate = :startDate) OR (aleave.applyLeaveDate = :endDate)) and (aleave.leaveStatus = 'APPROVED' or aleave.leaveStatus = 'PENDING')";
		List<AppliedLeaveDateView> list = session.createQuery(query).setLong("orgLocationId", orgLocation.getId()).setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate).setLong("userId", userId).list();
		return list;
	}


	@Override
	@Transactional
	public int deleteApproversByLeaveId(Long leaveId) {
		int isDeleted = 0;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "delete from LeaveApproveHierarchy lah where lah.leaveid.id =:leaveId";
			Query qry = session.createQuery(query).setLong("leaveId", leaveId);
			int i = qry.executeUpdate();
			return i;
		} catch (Exception e) {
			logger.error("Delete Approvers By Leave Id Failed Due to "
					+ e.getLocalizedMessage());
		}
		return isDeleted;
	}
}
