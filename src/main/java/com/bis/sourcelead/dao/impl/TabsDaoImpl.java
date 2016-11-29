package com.bis.sourcelead.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.TabsDao;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.Tabs;

@Repository
public class TabsDaoImpl implements TabsDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(UserRoleDaoImpl.class);

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Tabs> getTabsListByLoginUserRoleIdAndOrgLocationId(Long roleId, Long orgLocationId) throws Exception {
		
	     Session session = this.sessionFactory.getCurrentSession();
	     
		 return session.createQuery("from Tabs tabs left join fetch tabs.tabs where tabs.id IN (select distinct tabActions.tabs.id from TabActions tabActions "
		 		+ "where tabActions.id IN (select actionRoles.tabActions.id from ActionRoles actionRoles where "
		 		+ "actionRoles.roles.id = :roleId and actionRoles.orgLocations.id = :orgLocationId and actionRoles.isNotify = 'Y' )) order by tabs.tabOrder asc")
					  .setLong("roleId", roleId)
					  .setLong("orgLocationId", orgLocationId)
					  .setCacheable(true).list();
		 
		 
		 
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Tabs> getSourceLeadTabsListByOrgId(Long roleId,Long orgId) throws Exception {
		
	     Session session = this.sessionFactory.getCurrentSession();
	     
		 return session.createQuery("from Tabs tabs left join fetch tabs.tabs where tabs.id IN (select distinct tabActions.tabs.id from TabActions tabActions "
		 		+ "where tabActions.id IN (select actionRoles.tabActions.id from ActionRoles actionRoles where "
		 		+ "actionRoles.roles.id = :roleId and actionRoles.org.id = :orgId and actionRoles.isNotify = 'Y' )) order by tabs.tabOrder asc")
					  .setLong("roleId", roleId)
					  .setLong("orgId", orgId)
					  .setCacheable(true).list();
		 
		 
		 
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Tabs> getTabsList(List<Long> modulesId, Long roleId, Long orgId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Tabs tabs left join fetch tabs.tabs where tabs.id IN (select distinct sa.tabs.id from SourceleadActions sa where sa.id IN (select moduleActions.sourceleadActions.id from ModuleActions moduleActions where moduleActions.modules.id IN(:modulesId) "
	    		+ "and moduleActions.sourceleadActions.id IN (select ram.sourceleadActions.id from RoleActionMapping ram where ram.roles.id = :roleId and ram.org.id = :orgId and ram.status = '1' ))) ";
	    List<Tabs> tabsList = session.createQuery(query.toString()).setLong( "roleId", roleId).setLong( "orgId", orgId).setParameterList("modulesId", modulesId).setCacheable(true).list();
	    return tabsList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Tabs> getTabsListByOrgLocationId(List<Long> modulesId, Long roleId, Long orgLocationId)
			throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		
         List<Roles> rolesList = new ArrayList<Roles>();
		 rolesList = session.createQuery("from Roles roles where roles.id = :roleId and roles.orgLocations.id = :orgLocationId" ).setLong( "roleId", roleId ).setLong( "orgLocationId", orgLocationId).setCacheable(true).list();
		 List<Long> roleActonList = new ArrayList<Long>();
		 for(Roles roles : rolesList){
			 for (String s : roles.getActionId().split(",")){
				 roleActonList.add(Long.parseLong(s.trim()));
			 }
		 }
		
		String query = "from Tabs tabs left join fetch tabs.tabs where tabs.id IN (select distinct sa.tabs.id from SourceleadActions sa where sa.id IN (select moduleActions.sourceleadActions.id from ModuleActions moduleActions where moduleActions.modules.id IN (:modulesId) and moduleActions.sourceleadActions.id IN (:roleActonList))) ";
	    List<Tabs> tabsList = session.createQuery(query.toString()).setParameterList("modulesId", modulesId).setParameterList("roleActonList", roleActonList).setCacheable(true).list();
	    return tabsList;
	}

}
