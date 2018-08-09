package com.bis.sourcelead.dao.impl;

import java.math.BigInteger;
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
import com.bis.sourcelead.entity.RecruiterReportCandidateCountView;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.entity.UserIdView;
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
			String query = "from UserGroup ugroup where ugroup.orgLocationId.id =:orgLocationId and ugroup.status = 1";
			String countQuery = "from UserGroup ugroup where ugroup.orgLocationId.id =:orgLocationId and ugroup.status = 1";
			StringBuilder searchQuery = new StringBuilder();
			if (StringUtils.isNoneBlank(userGroupDTO.getSearchValue())) {
				searchQuery.append(" and ugroup.groupId like '%"+ userGroupDTO.getSearchValue()+ "%' ")
				           .append(" OR ugroup.payFrequency like '%"+ userGroupDTO.getSearchValue()+ "%' ")
				           .append("OR ugroup.groupName like '%"+ userGroupDTO.getSearchValue() + "%' ");
			}
			Query queryObj = session.createQuery(query + searchQuery + qry);
			if (userGroupDTO != null && userGroupDTO.getLength() > 0) {
				queryObj.setFirstResult(userGroupDTO.getStart());
				queryObj.setMaxResults(userGroupDTO.getLength());
				queryObj.setLong("orgLocationId", orgLocation.getId()).list();
			}
			List<UserGroup> userGroupList = queryObj.list();
			int count = 0;
			int filteredCount = 0;
			List<Long> countlist = session.createQuery("select count(*) " + countQuery + searchQuery).setLong("orgLocationId", orgLocation.getId()).list();
			if (countlist != null && !countlist.isEmpty()) {
				count = countlist.iterator().next().intValue();
			}
			response.setRecordsTotal(count);
			if (userGroupDTO != null) {
				List<Long> filteredCountlist = session.createQuery("select count(*) " + countQuery + searchQuery).setLong("orgLocationId", orgLocation.getId()).list();
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
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserGroup userGroup where userGroup.orgLocationId.id = :orgLocationId and userGroup.status = 1";
		List<UserGroup> userGroupList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocation.getId()).list();
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
	public TotalHoursDTO getTotalHoursByUserId(Long userId,Date sDate,Date eDate)
	{
		TotalHoursDTO totalHoursDTO = null;
		try {
			java.sql.Date startDate = new java.sql.Date(sDate.getTime());
			java.sql.Date endDate = new java.sql.Date(eDate.getTime());
			Session session = this.sessionFactory.getCurrentSession();
			StringBuilder qry = new StringBuilder(); 
			qry.append("select distinct user.ID as userId,")
			.append("can.ID as candidateID,")
			.append("projmem.ID as projectMemId,")
			.append("projmem.PAY_RATE as payRate,")
			.append(" projmem.PAY_RATE_CURRENCY as payRateCurrency,")
			.append(" projmem.CURRENT_BILL_RATE as currentBillRate,")
			.append(" projmem.BILL_RATE_CURRENCY as billRateCurrency,")
			.append(" projmem.PAY_RATE_TYPE as payRateType,")
			.append(" timesheet.START_DATE as startDate,")
			.append(" timesheet.END_DATE as endDate,")
			.append(" timesheet.BILLABLE_HOURS as billableHours,")
			.append(" timesheet.TOTAL_HOURS as totalHours,")
			.append(" timesheet.ESTIMATED_HOURS as estimatedHours,")
			.append(" timesheet.NON_BILLABLE_HOURS as nonBillableHours")
			.append(" from ")
			.append(" USER user,")
			.append(" CANDIDATE can,")
			.append(" PROJECT_MEMBERS projmem,")
			.append(" TIMESHEET_SUBMISSIONS timesheet")
			.append(" where user.ID = can.CONSULTANT_USER_ID")
			.append(" AND   can.ID  = projmem.CANDIDATE_ID")
			.append(" AND   projmem.MASTER_CANDIDATE_ID = timesheet.MASTER_CANDIDATE_ID")
			.append (" AND   projmem.PROJECT_ID = timesheet.PROJECT_ID")
			.append(" AND user.ID = :userId and timesheet.START_DATE = :startDate and timesheet.END_DATE = :endDate ");
//				 System.out.println(qry.toString());
			        
					Query queryString = session.createSQLQuery(qry.toString());
					queryString.setParameter("userId", userId).setDate("startDate", startDate).setDate("endDate",endDate);
					queryString.setResultTransformer(Transformers.aliasToBean(TotalHoursDTO.class));
					totalHoursDTO  = (TotalHoursDTO) queryString.uniqueResult();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return totalHoursDTO;
	}
	
	
}
