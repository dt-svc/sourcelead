package com.bis.sourcelead.dao.impl;

import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.TabsDao;
import com.bis.sourcelead.entity.Tabs;

@Repository
public class TabsDaoImpl implements TabsDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(UserRoleDaoImpl.class);

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Collection<Tabs> getTabsListByLoginUserRoleIdAndOrgLocationId(Long roleId, Long orgLocationId) throws Exception {
		
	     Session session = this.sessionFactory.getCurrentSession();
	     
		 return session.createQuery("from Tabs tabs left join fetch tabs.tabs where tabs.id IN (select distinct tabActions.tabs.id from TabActions tabActions "
		 		+ "where tabActions.id IN (select actionRoles.tabActions.id from ActionRoles actionRoles where "
		 		+ "actionRoles.roles.id = :roleId and actionRoles.orgLocations.id = :orgLocationId and actionRoles.isNotify = 'Y' ))")
					  .setLong("roleId", roleId)
					  .setLong("orgLocationId", orgLocationId)
					  .list();
		 
		 
		 
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Collection<Tabs> getSourceLeadTabsListByOrgId(Long roleId,Long orgId) throws Exception {
		
	     Session session = this.sessionFactory.getCurrentSession();
	     
		 return session.createQuery("from Tabs tabs left join fetch tabs.tabs where tabs.id IN (select distinct tabActions.tabs.id from TabActions tabActions "
		 		+ "where tabActions.id IN (select actionRoles.tabActions.id from ActionRoles actionRoles where "
		 		+ "actionRoles.roles.id = :roleId and actionRoles.org.id = :orgId and actionRoles.isNotify = 'Y' ))")
					  .setLong("roleId", roleId)
					  .setLong("orgId", orgId)
					  .list();
		 
		 
		 
	}

}
