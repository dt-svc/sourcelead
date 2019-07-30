package com.bis.sourcelead.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.SourceleadActionsDao;
import com.bis.sourcelead.dao.TabsDao;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.Tabs;
import com.bis.sourcelead.entity.User;

@Repository
public class TabsDaoImpl implements TabsDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	SourceleadActionsDao sourceleadActionsDao;
	
	private static final Logger logger = LoggerFactory.getLogger(UserRoleDaoImpl.class);

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Tabs> getTabsListByLoginUserRoleIdAndOrgLocationId(Long roleId, Long orgLocationId) throws Exception {
		
	     
	     
	     Session session = this.sessionFactory.getCurrentSession();
         List<Roles> rolesList = new ArrayList<Roles>();
		 rolesList = session.createQuery("from Roles roles where roles.id = :roleId and roles.orgLocations.id = :orgLocationId" ).setLong( "roleId", roleId ).setLong( "orgLocationId", orgLocationId).list();
		 List<Long> roleActionsList = new ArrayList<Long>();
		 for(Roles roles : rolesList){
			 /*for (String s : roles.getActionId().split(",")){
				 roleActionsList.add(Long.parseLong(s));
			 }*/
			 for (String s : Arrays.stream(roles.getActionId().split(",")).filter(value -> value != null && value.length() > 0).toArray(size -> new String[size])){
    			 roleActionsList.add(Long.parseLong(s));
        	 }
		 }
		
	    String query = "from Tabs tabs where tabs.id IN (select sa.tabs.id from SourceleadActions sa where sa.id IN (:roleActionsList)) ";
	    List<Tabs> tabsList = session.createQuery(query.toString()).setParameterList("roleActionsList", roleActionsList).list();
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
					  .list();
		 
		 
		 
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Tabs> getTabsList(List<Long> modulesId, Long roleId, Long orgId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Tabs tabs left join fetch tabs.tabs where tabs.id IN (select distinct sa.tabs.id from SourceleadActions sa where sa.id IN (select moduleActions.sourceleadActions.id from ModuleActions moduleActions where moduleActions.modules.id IN(:modulesId) "
	    		+ "and moduleActions.sourceleadActions.id IN (select ram.sourceleadActions.id from RoleActionMapping ram where ram.roles.id = :roleId and ram.org.id = :orgId and ram.status = '1' ))) ORDER BY tabs.tabOrder ASC ";
	    List<Tabs> tabsList = session.createQuery(query.toString()).setLong( "roleId", roleId).setLong( "orgId", orgId).setParameterList("modulesId", modulesId).list();
	    return tabsList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Collection<Tabs> getTabsListByOrgLocationId(Long roleId, Long orgLocationId)
			throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		
         List<Roles> rolesList = new ArrayList<Roles>();
		 rolesList = session.createQuery("from Roles roles where roles.id = :roleId and roles.orgLocations.id = :orgLocationId" ).setLong( "roleId", roleId ).setLong( "orgLocationId", orgLocationId).list();
		 List<Long> roleActonList = new ArrayList<Long>();
		 for(Roles roles : rolesList){
			 /*for (String s : roles.getActionId().split(",")){
				 roleActonList.add(Long.parseLong(s.trim()));
			 }*/
			 for (String s : Arrays.stream(roles.getActionId().split(",")).filter(value -> value != null && value.length() > 0).toArray(size -> new String[size])){
    			 roleActonList.add(Long.parseLong(s));
        	 }
		 }
		
		String query = "from Tabs tabs left join fetch tabs.tabs where tabs.id IN (select distinct sa.tabs.id from SourceleadActions sa where sa.id IN (:roleActonList)) and tabs.status = '1' ORDER BY tabs.tabOrder ASC ";
	    List<Tabs> tabsList = session.createQuery(query.toString()).setParameterList("roleActonList", roleActonList).list();
	    return tabsList;
	}

	@Override
	@Transactional
	public List<Long> getTabsIdsListByUserRoleIdAndOrgLocationId(Long roleId, Long orgLocationId)
			throws Exception {
		
		 Session session = this.sessionFactory.getCurrentSession();
         List<Roles> rolesList = new ArrayList<Roles>();
		 rolesList = session.createQuery("from Roles roles where roles.id = :roleId and roles.orgLocations.id = :orgLocationId" ).setLong( "roleId", roleId ).setLong( "orgLocationId", orgLocationId).list();
		 List<Long> roleActionsList = new ArrayList<Long>();
		 for(Roles roles : rolesList){
			 /*for (String s : roles.getActionId().split(",")){
				 roleActionsList.add(Long.parseLong(s));
			 }*/
			 for (String s : Arrays.stream(roles.getActionId().split(",")).filter(value -> value != null && value.length() > 0).toArray(size -> new String[size])){
    			 roleActionsList.add(Long.parseLong(s));
        	 }
		 }
		
	    String query = "select tabs.id from Tabs tabs where tabs.id IN (select sa.tabs.id from SourceleadActions sa where sa.id IN (:roleActionsList)) ";
	    List<Long> tabIds = session.createQuery(query.toString()).setParameterList("roleActionsList", roleActionsList).list();
    
	    return tabIds;
	}

	@Override
	@Transactional
	public List<Tabs> getTabsListByIds(List<Long> tabIds) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Tabs tabs where tabs.id IN (:tabIds) ";
	    List<Tabs> tabsList = session.createQuery(query.toString()).setParameterList("tabIds", tabIds).list();
	    return tabsList;
	}

	@Override
	@Transactional
	public boolean getTabAccessByRoleIdAndTabId(Long roleId, Long orgLocationId, Long tabId) throws Exception {
		
		 Session session = this.sessionFactory.getCurrentSession();
		 boolean flag = false;
        List<Roles> rolesList = new ArrayList<Roles>();
		 rolesList = session.createQuery("from Roles roles where roles.id = :roleId and roles.orgLocations.id = :orgLocationId" ).setLong( "roleId", roleId ).setLong( "orgLocationId", orgLocationId).list();
		 List<Long> roleActionsList = new ArrayList<Long>();
		 for(Roles roles : rolesList){
			 /*for (String s : roles.getActionId().split(",")){
				 roleActionsList.add(Long.parseLong(s));
			 }*/
			 for (String s : Arrays.stream(roles.getActionId().split(",")).filter(value -> value != null && value.length() > 0).toArray(size -> new String[size])){
    			 roleActionsList.add(Long.parseLong(s));
        	 }
		 }
		
	    String query = "select tabs.id from Tabs tabs where tabs.id IN (select sa.tabs.id from SourceleadActions sa where sa.id IN (:roleActionsList)) ";
	    List<Long> tabIds = session.createQuery(query.toString()).setParameterList("roleActionsList", roleActionsList).list();
   
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
		 rolesList = session.createQuery("from Roles roles where roles.id = :roleId" ).setLong( "roleId", roleId ).list();
		 List<Long> roleActonList = new ArrayList<Long>();
		 for(Roles roles : rolesList){
			 /*for (String s : roles.getActionId().split(",")){
				 roleActonList.add(Long.parseLong(s.trim()));
			 }*/
			 for (String s : Arrays.stream(roles.getActionId().split(",")).filter(value -> value != null && value.length() > 0).toArray(size -> new String[size])){
    			 roleActonList.add(Long.parseLong(s));
        	 }
		 }
		
		String query = "from Tabs tabs left join fetch tabs.tabs where tabs.id IN (select distinct sa.tabs.id from SourceleadActions sa where sa.id IN (select moduleActions.sourceleadActions.id from ModuleActions moduleActions where moduleActions.modules.id = '5' and moduleActions.sourceleadActions.id IN (:roleActonList))) ORDER BY tabs.tabOrder ASC ";
	    List<Tabs> tabsList = session.createQuery(query.toString()).setParameterList("roleActonList", roleActonList).list();
	    return tabsList;
	}

	@Override
	@Transactional
	public List<String> getTabsNamesListByActionsIds(List<Long> actionIdsList) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		
		String query = "select tabs.tabName from Tabs tabs where tabs.id IN (select distinct sa.tabs.id from SourceleadActions sa where sa.id IN (:actionIdsList)) ORDER BY tabs.tabOrder ASC ";
	    List<String> tabNamesList = session.createQuery(query.toString()).setParameterList("actionIdsList", actionIdsList).list();
	    return tabNamesList;
	}

	@Override
	@Transactional
	public Tabs getTabListByTabId(Long tabId) {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<Tabs> tabs = new ArrayList<Tabs>();
		logger.debug("getTabListByTabId start");
		tabs = session.createQuery("from Tabs tabs left join fetch tabs.tabs where tabs.id = :tabId" ).setLong( "tabId", tabId ).list();
		logger.debug("getTabListByTabId end");
		if (tabs.size() > 0) {
			return tabs.get(0);
		} else {
			return null;
		}
   	
	}

	@Override
	@Transactional
	public Collection<Tabs> getAllTabsList() throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		
		String query = "from Tabs tabs left join fetch tabs.tabs where  tabs.tabs IS NULL and tabs.status = '1' and tabs.id NOT IN(1,11,12,15,26,27) ORDER BY tabs.tabOrder ASC ";
	    List<Tabs> tabsList = session.createQuery(query.toString()).list();
	    return tabsList;
	
	}

	@Override
	@Transactional
	public List<Tabs> getClildTabsListByTabId(Long tabId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Tabs tabs left join fetch tabs.tabs parentTab where parentTab.id = :tabId and parentTab.status = '1' ";
	    List<Tabs> tabsList = session.createQuery(query.toString()).setLong( "tabId", tabId).list();
	    return tabsList;
	}

	@Override
	@Transactional
	public List<Tabs> getTabListByActionsIds(List<Long> actionIdsList) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Tabs tabs left join fetch tabs.tabs where tabs.id IN (select distinct sa.tabs.id from SourceleadActions sa where sa.id IN (:actionIdsList)) ORDER BY tabs.tabOrder ASC ";
	    List<Tabs> tabsList = session.createQuery(query.toString()).setParameterList("actionIdsList", actionIdsList).list();
	    return tabsList;
	
	}

	@Override
	@Transactional
	public List<Tabs> getTabsByRoleIdAndOrgLocationId(long roleId, Long locationId)
	{
		  Session session = this.sessionFactory.getCurrentSession();
		  List<Tabs> tabsList = new CopyOnWriteArrayList<>();
		  
	         List<Roles> rolesList = new ArrayList<Roles>();
			 rolesList = session.createQuery("from Roles roles where roles.id = :roleId and roles.orgLocations.id = :orgLocationId" ).setLong( "roleId", roleId ).setLong( "orgLocationId", locationId).list();
			 List<Long> roleActionsList = new ArrayList<Long>();
			 for(Roles roles : rolesList){
				 
				 for (String s : Arrays.stream(roles.getActionId().split(",")).filter(value -> value != null && value.length() > 0).toArray(size -> new String[size])){
	    			 roleActionsList.add(Long.parseLong(s));
	        	 }
			 }
			 
		    String query = "from SourceleadActions sa left join fetch sa.tabs where sa.id IN (:actionIdList)";
		    List<SourceleadActions> actionsList = session.createQuery(query.toString()).setParameterList("actionIdList", roleActionsList).list();
		    HashMap<Long,Tabs> tabsMap = new HashMap<>();
			 if(actionsList != null && !actionsList.isEmpty()) {
				 for(SourceleadActions action : actionsList) {
					 Tabs tabs = getTabListByTabId(action.getTabs().getId());
					 Tabs tempTabs = null;
					 if(tabs.getTabs() != null){
						 tempTabs = tabsMap.get(tabs.getTabs().getId());
					 }else {
						 tempTabs = tabsMap.get(tabs.getId()); 
					 }
			    		if(tempTabs == null) {
			    			if(tabs.getTabs() != null){
								 tabsMap.put(tabs.getTabs().getId(),tabs.getTabs());
				    			 tabsList.add(tabs.getTabs());
							 }else {
								 tabsMap.put(tabs.getId(),tabs);
				    			 tabsList.add(tabs);
							 }
			    			
			    		}
					 
				 }
			 }
			 
	        return tabsList;
	}
	
	 
	
}
