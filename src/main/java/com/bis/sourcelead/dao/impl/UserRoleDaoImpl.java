package com.bis.sourcelead.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.entity.UserRole;

@Repository
public class UserRoleDaoImpl implements UserRoleDao {

	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(UserRoleDaoImpl.class);

	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public UserRole addUserRole(UserRole userRole) throws Exception {
		logger.debug("Adding UserRole");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (userRole != null) {
				session.save(userRole);
			}
			logger.debug("UserRole Added Successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userRole;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public UserRole getLoginUserPrimaryRole(Long userId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		List<UserRole> userRole = new ArrayList<UserRole>();
		logger.debug("getPrimaryRole By UserId start");
		userRole = session
				.createQuery("from UserRole userRole where userRole.user.id=:userId and userRole.isPrimary = '1' ")
				.setLong("userId", userId).list();
		logger.debug("getPrimaryRole By UserId start");
		if (userRole.size() > 0) {
			return userRole.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })

	public Collection<UserRole> getUsersListByLocationId(Long locationId, String userState, Long userId)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();

		String subQuery = "";
		if (!isNullOrEmpty(userState)) {
			subQuery = "and userRole.user.userState = '" + userState + "'";
		}

		return session.createQuery(
				"Select userRole from UserRole userRole where userRole.orgLocations.id = :locationId and userRole.user.id <> :userId "
						+ subQuery + " ")
				.setLong("locationId", locationId).setLong("userId", userId).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Collection<UserRole> getUsersListByRoleAndLocationId(Long roleId, Long locationId, String userState,
			Long userId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();

		String subQuery = "";
		if (!isNullOrEmpty(userState)) {
			subQuery = "and userRole.user.userState = '" + userState + "'";
		}

		return session
				.createQuery("Select userRole from UserRole userRole where userRole.roles.id = :roleId "
						+ "and userRole.orgLocations.id = :locationId and userRole.user.id <> :userId " + subQuery + "")
				.setLong("roleId", roleId).setLong("locationId", locationId).setLong("userId", userId).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Collection<UserRole> getUsersListByUserNameAndLocationId(String userName, Long locationId, String userState,
			Long userId) throws Exception {

		String subQuery = "";
		if (!isNullOrEmpty(userState)) {
			subQuery = "and userRole.user.userState = '" + userState + "'";
		}

		Session session = this.sessionFactory.getCurrentSession();
		return session.createQuery("Select userRole from UserRole userRole where " + "((userRole.user.firstName LIKE '"
				+ userName + "%') OR (userRole.user.lastName LIKE '" + userName + "%')) "
				+ "and userRole.orgLocations.id = :locationId and userRole.user.id <> :userId " + subQuery + " ")
				.setLong("locationId", locationId).setLong("userId", userId).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Collection<UserRole> getUsersListByRoleAndNameAndLocationId(Long roleId, String userName, Long locationId,
			String userState, Long userId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();

		String subQuery = "";
		if (!isNullOrEmpty(userState)) {
			subQuery = "and userRole.user.userState = '" + userState + "'";
		}

		return session
				.createQuery("Select userRole from UserRole userRole where userRole.roles.id = :roleId and "
						+ "((userRole.user.firstName LIKE '" + userName + "%') OR (userRole.user.lastName LIKE '"
						+ userName + "%')) "
						+ "and userRole.orgLocations.id = :locationId and userRole.user.id <> :userId " + subQuery + "")
				.setLong("roleId", roleId).setLong("locationId", locationId).setLong("userId", userId).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Collection<UserRole> getUsersListByUserStateAndLocationId(String userState, Long locationId, Long userId)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		return session
				.createQuery(
						"Select userRole from UserRole userRole where userRole.orgLocations.id = :locationId and userRole.user.id <> :userId and "
								+ "userRole.user.userState = :userState")
				.setLong("locationId", locationId).setString("userState", userState).setLong("userId", userId).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Collection<UserRole> getUsersBasedOnUserRoles() throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserRole userRole left join fetch userRole.roles role left join fetch userRole.user where role.roleName = 'ROLE_ADMIN'";
		List<UserRole> userRoleList = session.createQuery(query.toString()).list();
		return userRoleList;

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Collection<UserRole> getApproverAssignedUsers(List<Long> projectIds, String userState, String searchByName) throws Exception {
		Long roleId = 2L;
		String userStateSubQuery = "";
		if (!isNullOrEmpty(userState)) {
			userStateSubQuery = "and userRole.user.userState = '" + userState + "'";
		}
		String userNameSubQuery = "";
		if (!isNullOrEmpty(searchByName)) {

			userNameSubQuery = "and (userRole.user.firstName LIKE '" + searchByName
					+ "%') OR (userRole.user.lastName LIKE '" + searchByName + "%')";

		}
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserRole userRole where userRole.roles.id = :roleId and "

				+ "userRole.user.id IN (select distinct pm.employee.user.id from ProjectMembers pm where pm.projects.id IN (:projectIds) ) "
				+ "" + userStateSubQuery + " " + userNameSubQuery + "";

		List<UserRole> userRoleList = session.createQuery(query.toString())
				.setLong("roleId", roleId)
				.setParameterList("projectIds", projectIds)
				.list();
		return userRoleList;

	}

}
