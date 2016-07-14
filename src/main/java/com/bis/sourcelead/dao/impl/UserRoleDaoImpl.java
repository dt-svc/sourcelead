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
public class UserRoleDaoImpl implements UserRoleDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(UserRoleDaoImpl.class);

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
		userRole = session.createQuery("from UserRole userRole where userRole.user.id=:userId and userRole.isPrimary = '1' " ).setLong( "userId", userId ).list();
		logger.debug("getPrimaryRole By UserId start");
		if (userRole.size() > 0) {
			return userRole.get(0);
		} else {
			return null;
		}
   	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Collection<UserRole> getUsersListByLocationId(Long locationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		return session.createQuery("Select userRole from UserRole userRole where userRole.orgLocations.id = :locationId")
					  .setLong("locationId", locationId)
					  .list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Collection<UserRole> getUsersListByRoleAndLocationId(String roleName, Long locationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		Long roleId = null;
		if(roleName.equalsIgnoreCase("Administrator")){
			roleId = 1L;
		}else if(roleName.equalsIgnoreCase("User")){
			roleId = 2L;
		}
		
		return session.createQuery("Select userRole from UserRole userRole where userRole.roles.id = :roleId and userRole.orgLocations.id = :locationId")
					.setLong("roleId", roleId)	  
					.setLong("locationId", locationId)
					.list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Collection<UserRole> getUsersListByUserNameAndLocationId(String userName, Long locationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		return session.createQuery("Select userRole from UserRole userRole where ((userRole.user.firstName LIKE '" + userName + "%') OR (userRole.user.lastName LIKE '" + userName + "%')) and userRole.orgLocations.id = :locationId")
					.setLong("locationId", locationId)
					.list();
	}

}
