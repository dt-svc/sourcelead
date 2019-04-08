package com.bis.sourcelead.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.UserGroupDao;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserGroupDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.entity.UserIdView;
import com.bis.sourcelead.indPayroll.entity.IndPaysheetFrequency;
import com.bis.sourcelead.payroll.dto.PayPeriodDTO;
import com.bis.sourcelead.payroll.dto.TotalHoursDTO;

@Repository
public class UserGroupDaoImpl implements UserGroupDao{

	private static final Logger logger = LoggerFactory.getLogger(UserGroupDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public UserGroup saveUserGroup(UserGroup userGroup)
	{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (userGroup != null) {
				session.saveOrUpdate(userGroup);
			}
		} catch (Exception exception) {
			logger.error("User Group Saving failed Due to : " + exception.getMessage());
		}
		return userGroup;
	}

	@Override
	@Transactional
	public List<UserIdView> getCommaSeperatedUserId(Long orgLocationId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserIdView userIdView where userIdView.orgLocationId = :orgLocationId ";
		List<UserIdView> userIdViewList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).list();
		return userIdViewList;
	}

	@Override
	@Transactional
	public ResponseList<UserGroup> findAllUserGroup(UserGroupDTO userGroupDTO, OrgLocations orgLocation)
	{
		ResponseList<UserGroup> response = new ResponseList<>();
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String qry = "";
			if (StringUtils.isNotBlank(userGroupDTO.getSortColumn())) {
				qry = " order by ugroup." + userGroupDTO.getSortColumn() + " "
						+ userGroupDTO.getSortOrder();
			}
			String query = "from UserGroup ugroup where ugroup.orgLocationId.id =:orgLocationId and ugroup.status = :status ";
			String countQuery = "from UserGroup ugroup where ugroup.orgLocationId.id =:orgLocationId and ugroup.status = :status ";
			StringBuilder searchQuery = new StringBuilder();
			if (StringUtils.isNoneBlank(userGroupDTO.getSearchValue())) {
				searchQuery.append(" and ( ugroup.groupId like '%"+ userGroupDTO.getSearchValue()+ "%' ")
				           .append(" OR ugroup.payFrequency like '%"+ userGroupDTO.getSearchValue()+ "%' ")
				           .append(" OR ugroup.groupName like '%"+ userGroupDTO.getSearchValue() + "%' )");
			}
			Query queryObj = session.createQuery(query + searchQuery);
			if (userGroupDTO != null && userGroupDTO.getLength() > 0) {
				queryObj.setFirstResult(userGroupDTO.getStart());
				queryObj.setMaxResults(userGroupDTO.getLength());
				queryObj.setInteger("status", 1);
				queryObj.setLong("orgLocationId", orgLocation.getId()).list();
				
			}
			List<UserGroup> userGroupList = queryObj.list();
			int count = 0;
			int filteredCount = 0;
			List<Long> countlist = session.createQuery("select count(*) " + countQuery + searchQuery).setLong("orgLocationId", orgLocation.getId()).setInteger("status", 1).list();
			if (countlist != null && !countlist.isEmpty()) {
				count = countlist.iterator().next().intValue();
			}
			response.setRecordsTotal(count);
			if (userGroupDTO != null) {
				List<Long> filteredCountlist = session.createQuery("select count(*) " + countQuery + searchQuery).setLong("orgLocationId", orgLocation.getId()).setInteger("status", 1).list();
				if (filteredCountlist != null && !filteredCountlist.isEmpty()) {
					filteredCount = filteredCountlist.iterator().next().intValue();
				}
				response.setRecordsFiltered(filteredCount);
			}
			response.setData(userGroupList);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	@Override
	@Transactional
	public boolean validateUserGroupIdOrGroupName(Long locationId, UserGroupDTO userGroupDTO)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<UserGroup> userIdViewList = null;
		Long id = 0l;
		if(userGroupDTO.getId() != null){
			id = userGroupDTO.getId();
		}
		if(StringUtils.isNotBlank(userGroupDTO.getGroupId())){
			String query = "from UserGroup ug where ug.orgLocationId = :orgLocationId and ug.groupId = :groupId and ug.id <> :id and ug.status <> 0";
			userIdViewList = session.createQuery(query.toString()).setLong("orgLocationId", locationId).setString("groupId", userGroupDTO.getGroupId()).setLong("id", id).list();
		}else if(StringUtils.isNotBlank(userGroupDTO.getGroupName())){
			String query = "from UserGroup ug where ug.orgLocationId = :orgLocationId and ug.groupName = :groupName and ug.id <> :id and ug.status <> 0";
			userIdViewList = session.createQuery(query.toString()).setLong("orgLocationId", locationId).setString("groupName", userGroupDTO.getGroupName()).setLong("id", id).list();
		}
		if(userIdViewList != null && userIdViewList.size() > 0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	@Transactional
	public List<UserGroup> getAllUserGroupsByLocationId(OrgLocations orgLocation)
	{
		List<UserGroup> userGroupList = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from UserGroup userGroup where userGroup.orgLocationId.id = :orgLocationId and userGroup.status = 1";
			userGroupList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocation.getId()).list();
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		
		return userGroupList;
	}
	
	@Override
	@Transactional
	public List<UserGroup> getAllUserGroupsByLocationIdAndPayFrequency(OrgLocations orgLocation, String payFrequency)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserGroup userGroup where userGroup.orgLocationId.id = :orgLocationId and userGroup.payFrequency = :payFrequency and userGroup.status = 1";
		List<UserGroup> userGroupList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocation.getId()).setString("payFrequency", payFrequency).list();
		return userGroupList;
	}

	@Override
	@Transactional
	public UserGroup findUserGroupById(String groupUUID)
	{
		UserGroup userGroup = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserGroup userGroup where userGroup.groupUUID = :groupUUID and userGroup.status = 1";
		List<UserGroup> userGroupList = session.createQuery(query.toString()).setString("groupUUID", groupUUID).list();
		if(userGroupList != null && userGroupList.size() >0) {
			userGroup = userGroupList.get(0);
		}
		return userGroup;
	}
	
	@Override
	@Transactional
	public UserGroup getUserGroupByGroupId(long groupId)
	{
		UserGroup userGroup = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserGroup userGroup where userGroup.id = :groupId";
		List<UserGroup> userGroupList = session.createQuery(query.toString()).setLong("groupId", groupId).list();
		if(userGroupList != null && userGroupList.size() >0) {
			userGroup = userGroupList.get(0);
		}
		return userGroup;
	}

	@Override
	@Transactional
	public List<TotalHoursDTO> getTotalHoursByCandidateId(Long candidateId,Date sDate,Date eDate)
	{
		List<TotalHoursDTO> totalHoursDTO = null;
		try {
			java.sql.Date startDate = new java.sql.Date(sDate.getTime());
			java.sql.Date endDate = new java.sql.Date(eDate.getTime());
			Session session = this.sessionFactory.getCurrentSession();
			StringBuilder qry = new StringBuilder(); 
			qry.append("select distinct can.ID as candidateID,")
			.append(" projmem.ID as projectMemId,")
			.append(" projmem.PROJECT_ID as projectId,")
			.append(" projmem.PAY_RATE as payRate,")
			.append(" projmem.PAY_RATE_CURRENCY as payRateCurrency,")
			.append(" projmem.CURRENT_BILL_RATE as currentBillRate,")
			.append(" projmem.BILL_RATE_CURRENCY as billRateCurrency,")
			.append(" projmem.PAY_RATE_TYPE as payRateType,")
			//new field
			.append(" tde.TIMESHEET_DATE as timesheetDate,")
			.append(" ts.START_DATE as startDate,")
			.append(" ts.END_DATE as endDate,")
			.append(" ts.ID as submissinId,")
			.append(" REPLACE ( tde.BILLABLE_HOURS , ':' , '.' )  as billableHours,")
			.append(" ts.TOTAL_HOURS as totalHours,")
			.append(" REPLACE ( tde.NONBILLABLE_HOURS , ':' , '.' ) as nonBillableHours")
			.append(" from ")
			.append(" USER user,")
			.append(" CANDIDATE can,")
			.append(" PROJECT_MEMBERS projmem,")
			.append("SUBMISSION_APPROVER_HIERARCHY sah,")
			.append(" TIMESHEET_DAY_ENTRIES tde LEFT JOIN TIMESHEET_SUBMISSIONS ts ON tde.SUBMISSION_ID = ts.ID")
			.append(" where can.ID  = projmem.CANDIDATE_ID")
			.append(" AND   projmem.MASTER_CANDIDATE_ID = ts.MASTER_CANDIDATE_ID")
			.append (" AND   projmem.PROJECT_ID = ts.PROJECT_ID")
			.append (" AND   sah.SUBMISSION_ID = ts.ID")
			.append(" AND can.ID = :candidateId and tde.TIMESHEET_DATE  >= :startDate AND tde.TIMESHEET_DATE <= :endDate ")
			.append(" AND sah.SUBMISSION_APPROVALSTATUS = 'APPROVED' ");
				 System.out.println(qry.toString());
			        
					Query queryString = session.createSQLQuery(qry.toString());
					queryString.setParameter("candidateId", candidateId).setDate("startDate", startDate).setDate("endDate",endDate);
					queryString.setResultTransformer(Transformers.aliasToBean(TotalHoursDTO.class));
					totalHoursDTO  =  queryString.list();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return totalHoursDTO;
	}

	@Override
	@Transactional
	public List<PayPeriodDTO> getPayPeriodsByGroupId(Long groupId)
	{
		List<PayPeriodDTO> payPeriodDTOList = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			StringBuilder qry = new StringBuilder(); 
			qry.append("SELECT distinct payFreq.GROUP_ID as groupId, payFreq.ID as paySheetFreqId, payFreq.FREQUENCY_START_DATE as freqStartDate, ")
			.append("payFreq.FREQUENCY_END_DATE as freqEndDate, payPeriod.PAY_PERIOD_NUM as payPeriodNumber, ")
			.append("payPeriod.APPROVER_STATUS as approverStatus FROM US_PAY_PERIOD payPeriod, ")
			.append("PAY_SHEET_FREQUENCY payFreq where payPeriod.PAY_SHEET_FREQUENCY_ID = payFreq.ID")
			.append(" AND payPeriod.APPROVER_STATUS = 'APPROVED' and payFreq.GROUP_ID = :groupId");
			Query queryString = session.createSQLQuery(qry.toString());
			queryString.setParameter("groupId", groupId);
			queryString.setResultTransformer(Transformers.aliasToBean(PayPeriodDTO.class));
			payPeriodDTOList  =  queryString.list();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return payPeriodDTOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<UserGroup> getAllUserGroupsByLocationIdAndDepartmentId(OrgLocations orgLocation, String userId)
	{
		List<UserGroup> userGroupList = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from UserGroup userGroup where userGroup.orgLocationId.id = :orgLocationId and userGroup.departmentId IN (select departmentId.id from Employees emp where emp.user.id = :userId and emp.orgLocations.id = :orgLocationId)";
			userGroupList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocation.getId()).setLong("userId", Long.parseLong(userId)).list();
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		
		return userGroupList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<UserGroup> getAllUserGroupsByLocationIdAndPayrollFrequency(OrgLocations orgLocation, String autoPayrollFrequency)
	{
		List<UserGroup> userGroupList = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from UserGroup userGroup where userGroup.orgLocationId.id = :orgLocationId and userGroup.status = 1 and userGroup.payFrequency = :payFrequency";
			userGroupList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocation.getId()).setString("payFrequency", autoPayrollFrequency).list();
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		
		return userGroupList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<IndPaysheetFrequency> getIndPayPeriodsByGroupId(Long groupId, OrgLocations orgLocation) {
		List<IndPaysheetFrequency> payPeriodDTOList = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from IndPaysheetFrequency ipsf where ipsf.orgLocation.id =:orgLocationId and ipsf.status = 1 and ipsf.userGroup.id =:groupId and ipsf.approverStatus ='APPROVED'";
			payPeriodDTOList =session.createQuery(query).setLong("groupId", groupId).setLong("orgLocationId", orgLocation.getId()).list();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return payPeriodDTOList;
	}
	
}
