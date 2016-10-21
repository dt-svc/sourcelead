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
	public List<Roles> getMoudleRoles(Long orgId,Long userRoleId) throws Exception {
		
		String subQuery = "";
		
		if(orgId == 1l){
			subQuery = "and actionRoles.roles.id <> '4' ";
		}
		
		String subQuery1 = "";
		if(userRoleId != 1l || userRoleId != 4l){
			
			if(userRoleId == 5l){
				subQuery1 = "and actionRoles.roles.id <> '1' ";
			}
			
			else if(userRoleId == 3l){
				subQuery1 = "and actionRoles.roles.id <> '1'  and actionRoles.roles.id <> '5' ";
			}
			
			else if(userRoleId == 6l){
				subQuery1 = "and actionRoles.roles.id <> '1'  and actionRoles.roles.id <> '5' and actionRoles.roles.id <> '3' ";
			}
			
			else if(userRoleId == 7l){
				subQuery1 = "and actionRoles.roles.id <> '1'  and actionRoles.roles.id <> '5' and actionRoles.roles.id <> '3' and actionRoles.roles.id <> '6' ";
			}
			
			else if(userRoleId == 8l){
				subQuery1 = "and actionRoles.roles.id <> '1'  and actionRoles.roles.id <> '5' "
						+ "and actionRoles.roles.id <> '3' and actionRoles.roles.id <> '6' and actionRoles.roles.id <> '7' ";
			}
			
		}
		
		
		Session session = this.sessionFactory.getCurrentSession();
		return session.createQuery("from Roles roles where roles.id IN (select distinct actionRoles.roles.id from ActionRoles actionRoles "
				+ "where actionRoles.org.id = :orgId "+subQuery+" "+subQuery1+") ")
				.setLong("orgId", orgId).setCacheable(true).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Roles getRolesById(Long id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<Roles> roles = new ArrayList<Roles>();
		logger.debug("Roles start");
		roles = session.createQuery("from Roles role where role.id = :id" ).setLong( "id", id ).setCacheable(true).list();
		logger.debug("Roles end");
		if (roles.size() > 0) {
			return roles.get(0);
		} else {
			return null;
		}
	}


}
