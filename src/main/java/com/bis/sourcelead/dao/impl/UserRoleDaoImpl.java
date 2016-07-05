package com.bis.sourcelead.dao.impl;

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

}
