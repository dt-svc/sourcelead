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
         List<Roles> rolesList = new ArrayList<Roles>();
		 rolesList = session.createQuery("from Roles roles where roles.id = :roleId and roles.orgLocations.id = :orgLocationId" ).setLong( "roleId", roleId ).setLong( "orgLocationId", orgLocationId).setCacheable(true).list();
		 List<Long> roleActionsList = new ArrayList<Long>();
		 for(Roles roles : rolesList){
			 for (String s : roles.getActionId().split(",")){
				 roleActionsList.add(Long.parseLong(s));
			 }
		 }
		
	    String query = "from Tabs tabs where tabs.id IN (select sa.tabs.id from SourceleadActions sa where sa.id IN (:roleActionsList)) ";
	    List<Tabs> tabsList = session.createQuery(query.toString()).setParameterList("roleActionsList", roleActionsList).setCacheable(true).list();
        return tabsList;	
		 
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
	    		+ "and moduleActions.sourceleadActions.id IN (select ram.sourceleadActions.id from RoleActionMapping ram where ram.roles.id = :roleId and ram.org.id = :orgId and ram.status = '1' ))) ORDER BY tabs.tabOrder ASC ";
	    List<Tabs> tabsList = session.createQuery(query.toString()).setLong( "roleId", roleId).setLong( "orgId", orgId).setParameterList("modulesId", modulesId).setCacheable(true).list();
	    return tabsList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Collection<Tabs> getTabsListByOrgLocationId(List<Long> modulesId, Long roleId, Long orgLocationId)
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
		
		String query = "from Tabs tabs left join fetch tabs.tabs where tabs.id IN (select distinct sa.tabs.id from SourceleadActions sa where sa.id IN (select moduleActions.sourceleadActions.id from ModuleActions moduleActions where moduleActions.modules.id IN (:modulesId) and moduleActions.sourceleadActions.id IN (:roleActonList))) ORDER BY tabs.tabOrder ASC ";
	    List<Tabs> tabsList = session.createQuery(query.toString()).setParameterList("modulesId", modulesId).setParameterList("roleActonList", roleActonList).setCacheable(true).list();
	    return tabsList;
	}

	@Override
	@Transactional
	public List<Long> getTabsIdsListByUserRoleIdAndOrgLocationId(Long roleId, Long orgLocationId)
			throws Exception {
		
		 Session session = this.sessionFactory.getCurrentSession();
         List<Roles> rolesList = new ArrayList<Roles>();
		 rolesList = session.createQuery("from Roles roles where roles.id = :roleId and roles.orgLocations.id = :orgLocationId" ).setLong( "roleId", roleId ).setLong( "orgLocationId", orgLocationId).setCacheable(true).list();
		 List<Long> roleActionsList = new ArrayList<Long>();
		 for(Roles roles : rolesList){
			 for (String s : roles.getActionId().split(",")){
				 roleActionsList.add(Long.parseLong(s));
			 }
		 }
		
	    String query = "select tabs.id from Tabs tabs where tabs.id IN (select sa.tabs.id from SourceleadActions sa where sa.id IN (:roleActionsList)) ";
	    List<Long> tabIds = session.createQuery(query.toString()).setParameterList("roleActionsList", roleActionsList).setCacheable(true).list();
    
	    return tabIds;
	}

	@Override
	@Transactional
	public List<Tabs> getTabsListByIds(List<Long> tabIds) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Tabs tabs where tabs.id IN (:tabIds) ";
	    List<Tabs> tabsList = session.createQuery(query.toString()).setParameterList("tabIds", tabIds).setCacheable(true).list();
	    return tabsList;
	}

	@Override
	@Transactional
	public boolean getTabAccessByRoleIdAndTabId(Long roleId, Long orgLocationId, Long tabId) throws Exception {
		
		 Session session = this.sessionFactory.getCurrentSession();
		 boolean flag = false;
        List<Roles> rolesList = new ArrayList<Roles>();
		 rolesList = session.createQuery("from Roles roles where roles.id = :roleId and roles.orgLocations.id = :orgLocationId" ).setLong( "roleId", roleId ).setLong( "orgLocationId", orgLocationId).setCacheable(true).list();
		 List<Long> roleActionsList = new ArrayList<Long>();
		 for(Roles roles : rolesList){
			 for (String s : roles.getActionId().split(",")){
				 roleActionsList.add(Long.parseLong(s));
			 }
		 }
		
	    String query = "select tabs.id from Tabs tabs where tabs.id IN (select sa.tabs.id from SourceleadActions sa where sa.id IN (:roleActionsList)) ";
	    List<Long> tabIds = session.createQuery(query.toString()).setParameterList("roleActionsList", roleActionsList).setCacheable(true).list();
   
	    for(Long id : tabIds){
	    	if(id.equals(tabId)){
	    		flag = true;
	    	}
	    }
	    return flag;
	}

	@Override
	@Transactional
	public Collection<Tabs> getCrowdSourcingTabsListByRoleId(Long roleId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		
         List<Roles> rolesList = new ArrayList<Roles>();
		 rolesList = session.createQuery("from Roles roles where roles.id = :roleId" ).setLong( "roleId", roleId ).setCacheable(true).list();
		 List<Long> roleActonList = new ArrayList<Long>();
		 for(Roles roles : rolesList){
			 for (String s : roles.getActionId().split(",")){
				 roleActonList.add(Long.parseLong(s.trim()));
			 }
		 }
		
		String query = "from Tabs tabs left join fetch tabs.tabs where tabs.id IN (select distinct sa.tabs.id from SourceleadActions sa where sa.id IN (select moduleActions.sourceleadActions.id from ModuleActions moduleActions where moduleActions.modules.id = '5' and moduleActions.sourceleadActions.id IN (:roleActonList))) ORDER BY tabs.tabOrder ASC ";
	    List<Tabs> tabsList = session.createQuery(query.toString()).setParameterList("roleActonList", roleActonList).setCacheable(true).list();
	    return tabsList;
	}

	@Override
	@Transactional
	public List<String> getTabsNamesListByActionsIds(List<Long> actionIdsList) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		
		String query = "select tabs.tabName from Tabs tabs where tabs.id IN (select distinct sa.tabs.id from SourceleadActions sa where sa.id IN (:actionIdsList)) ORDER BY tabs.tabOrder ASC ";
	    List<String> tabNamesList = session.createQuery(query.toString()).setParameterList("actionIdsList", actionIdsList).setCacheable(true).list();
	    return tabNamesList;
	}

}
