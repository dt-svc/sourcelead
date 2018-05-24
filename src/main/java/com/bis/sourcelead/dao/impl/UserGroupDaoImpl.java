package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
	public List<UserGroup> getAllUserGroupsByLocationId(OrgLocations orgLocation)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserGroup userGroup where userGroup.orgLocationId.id = :orgLocationId ";
		List<UserGroup> userGroupList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocation.getId()).list();
		return userGroupList;
	}

	@Override
	@Transactional
	public UserGroup findUserGroupById(Long groupId)
	{
		UserGroup userGroup = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserGroup userGroup where userGroup.id = :groupId and userGroup.status = 1";
		List<UserGroup> userGroupList = session.createQuery(query.toString()).setLong("groupId", groupId).list();
		if(userGroupList != null && userGroupList.size() >0) {
			userGroup = userGroupList.get(0);
		}
		return userGroup;
	}
}
