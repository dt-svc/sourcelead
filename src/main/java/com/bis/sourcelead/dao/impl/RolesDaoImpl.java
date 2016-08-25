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

import com.bis.sourcelead.dao.RolesDao;
import com.bis.sourcelead.entity.Roles;

@Repository
public class RolesDaoImpl implements RolesDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(RolesDaoImpl.class);

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Roles getRolesByRoleName(String roleName) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<Roles> roles = new ArrayList<Roles>();
		logger.debug("Roles start");
		roles = session.createQuery("from Roles where roleName = :roleName" ).setString( "roleName", roleName ).setCacheable(true).list();
		logger.debug("Roles end");
		if (roles.size() > 0) {
			return roles.get(0);
		} else {
			return null;
		}
   	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Roles> getRoles(Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		return session.createQuery("from Roles roles where roles.id IN (select distinct actionRoles.roles.id from ActionRoles actionRoles "
				+ "where actionRoles.orgLocations.id = :orgLocationId) ")
				.setLong("orgLocationId", orgLocationId).setCacheable(true).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Roles getRolesByDisplayName(String displayName) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<Roles> roles = new ArrayList<Roles>();
		logger.debug("Roles start");
		roles = session.createQuery("from Roles where displayName = :displayName" ).setString( "displayName", displayName ).setCacheable(true).list();
		logger.debug("Roles end");
		if (roles.size() > 0) {
			return roles.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Roles> getMoudleRoles(Long orgId) throws Exception {
		
		String subQuery = "";
		if(orgId == '1'){
			subQuery = "and actionRoles.roles.id <> '4' ";
		}
		Session session = this.sessionFactory.getCurrentSession();
		return session.createQuery("from Roles roles where roles.id IN (select distinct actionRoles.roles.id from ActionRoles actionRoles "
				+ "where actionRoles.orgId.id = :orgId "+subQuery+") ")
				.setLong("orgId", orgId).setCacheable(true).list();
	}


}
