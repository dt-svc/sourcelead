package com.bis.sourcelead.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.CountriesDao;
import com.bis.sourcelead.dao.OrgLicenseSubscriptionDao;
import com.bis.sourcelead.dao.SourceleadActionsDao;
import com.bis.sourcelead.dao.TabsDao;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLicenseSubscription;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.Tabs;

@Repository
public class SourceleadActionsDaoImpl implements SourceleadActionsDao {
	
	

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	OrgLicenseSubscriptionDao orgLicenseSubscriptionDao;
	
	@Autowired
	TabsDao tabsDao;
	
	@Autowired
	CountriesDao countriesDao;
	
	@Value("${SourceLead.100}")
	private String notFound;
	@Value("${SourceLead.101}")
	private String noRecords;
	@Value("${SourceLead.102}")
	private String statusUpdate;
	@Value("${SourceLead.103}")
	private String statusDelete;
	@Value("${SourceLead.106}")
	private String duplicateValue;
	

	private static final Logger logger = LoggerFactory.getLogger(SourceleadActionsDaoImpl.class);

	@Override
	@Transactional
	public List<SourceleadActions> getLoginUserRoleActions(List<Long> modulesId, Long roleId,Long orgId) throws Exception {

		 Session session = this.sessionFactory.getCurrentSession();
         List<Roles> rolesList = new ArrayList<Roles>();
		 rolesList = session.createQuery("from Roles roles where roles.id = :roleId and roles.org.id = :orgId" ).setLong( "roleId", roleId ).setLong( "orgId", orgId).list();
		 List<Long> roleActonList = new ArrayList<Long>();
		 for(Roles roles : rolesList){
			 /*for (String s : roles.getActionId().split(",")){
				 roleActonList.add(Long.parseLong(s));
			 }*/
			 for (String s : Arrays.stream(roles.getActionId().split(",")).filter(value -> value != null && value.length() > 0).toArray(size -> new String[size])){
				 roleActonList.add(Long.parseLong(s));
        	 }
		 }
		
	    String query = "from SourceleadActions sa where sa.id IN (select moduleActions.sourceleadActions.id from ModuleActions moduleActions where moduleActions.modules.id IN(:modulesId) "
	    		+ "and moduleActions.sourceleadActions.id IN (:roleActonList)) ";
	    List<SourceleadActions> actionsList = session.createQuery(query.toString()).setParameterList("modulesId", modulesId).setParameterList("roleActonList", roleActonList).list();
      
	    return actionsList;
	}

	@Override
	@Transactional
	public List<SourceleadActions> getLoginUserRoleActionsByOrgLocationId(List<Long> modulesId, Long roleId,
			Long orgLocationId) throws Exception {

		 Session session = this.sessionFactory.getCurrentSession();
         List<Roles> rolesList = new ArrayList<Roles>();
		 rolesList = session.createQuery("from Roles roles where roles.id = :roleId and roles.orgLocations.id = :orgLocationId" ).setLong( "roleId", roleId ).setLong( "orgLocationId", orgLocationId).list();
		 List<Long> roleActonList = new ArrayList<Long>();
		 List<SourceleadActions> actionsList = null;
		 if(rolesList != null && !rolesList.isEmpty()) {
			 for(Roles roles : rolesList){
				 for (String s : Arrays.stream(roles.getActionId().split(",")).filter(value -> value != null && value.length() > 0).toArray(size -> new String[size])){
					 roleActonList.add(Long.parseLong(s));
	        	 }
			 }
			
		    String query = "from SourceleadActions sa where sa.id IN (:roleActonList) ";
		    actionsList = session.createQuery(query.toString()).setParameterList("roleActonList", roleActonList).list();
		}
      
	    return actionsList;
	}


	@Override
	@Transactional
	public List<Long> getAllSourceleadActionsIds(OrgLocations orgLocations) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		
		 List<Long> modulesList = new ArrayList<Long>();
			for (String s : orgLocations.getModulesId().split(",")){
				modulesList.add(Long.parseLong(s));
			 }
        		
	    String query = "select sa.id from SourceleadActions sa where sa.actionRole != 'ROLE_ENROLL_ORG' and sa.id IN(select moduleActions.sourceleadActions.id from ModuleActions moduleActions where moduleActions.modules.id IN(:modulesId))";
	    List<Long> actionIds = session.createQuery(query.toString()).setParameterList("modulesId", modulesList).list();
     
	    return actionIds;
	}

	@Override
	@Transactional
	public List<Long> getSourceLeadActionsByModuleIdAndTabId(Long moduleId, Long tabId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		
		 
	    String query = "select sa.id from SourceleadActions sa where sa.id IN(select moduleActions.sourceleadActions.id from ModuleActions moduleActions where moduleActions.modules.id = :moduleId) and sa.tabs.id = :tabId";
	    List<Long> actionIds = session.createQuery(query.toString()).setLong("tabId", tabId).setLong("moduleId", moduleId).list();
     
	    return actionIds;
	}

	@Override
	@Transactional
	public List<Long> getSourceLeadTimeSheetActionsByModuleIdAndTabId(Long moduleId, Long myTimeSheetTabId,
			Long timeSheetApprovalTabId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		
		 
	    String query = "select sa.id from SourceleadActions sa where sa.id IN(select moduleActions.sourceleadActions.id from ModuleActions moduleActions where moduleActions.modules.id = :moduleId) and (sa.tabs.id = :myTimeSheetTabId or sa.tabs.id = :timeSheetApprovalTabId)";
	    List<Long> actionIds = session.createQuery(query.toString()).setLong("myTimeSheetTabId", myTimeSheetTabId).setLong("timeSheetApprovalTabId", timeSheetApprovalTabId).setLong("moduleId", moduleId).list();
     
	    return actionIds;
	}

	@Override
	@Transactional
	public List<Long> getSourceLeadActionsByTabId(Long tabId) throws Exception {Session session = this.sessionFactory.getCurrentSession();
	
    String query = "select sa.id from SourceleadActions sa where sa.tabs.id = :tabId";
    List<Long> actionIds = session.createQuery(query.toString()).setLong("tabId", tabId).list();
 
    return actionIds;}

	@Override
	@Transactional
	public List<Long> getSourceLeadActionsByModuleId(Long moduleId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		
		 
	    String query = "select sa.id from SourceleadActions sa where sa.id IN(select moduleActions.sourceleadActions.id from ModuleActions moduleActions where moduleActions.modules.id = :moduleId) and sa.tabs.id NOT IN (13,14,26,28,29) and sa.id <> '115' ";
	    List<Long> actionIds = session.createQuery(query.toString()).setLong("moduleId", moduleId).list();
     
	    return actionIds;
	}

	@Override
	@Transactional
	public List<SourceleadActions> getLoginCrowdSourcingUserRoleActionsByOrgLocationId(Long roleId) throws Exception {

		 Session session = this.sessionFactory.getCurrentSession();
        List<Roles> rolesList = new ArrayList<Roles>();
		 rolesList = session.createQuery("from Roles roles where roles.id = :roleId" ).setLong( "roleId", roleId ).list();
		 List<Long> roleActonList = new ArrayList<Long>();
		 for(Roles roles : rolesList){
			 /*for (String s : roles.getActionId().split(",")){
				 roleActonList.add(Long.parseLong(s));
			 }*/
			 for (String s : Arrays.stream(roles.getActionId().split(",")).filter(value -> value != null && value.length() > 0).toArray(size -> new String[size])){
				 roleActonList.add(Long.parseLong(s));
        	 }
		 }
		
	    String query = "from SourceleadActions sa where sa.id IN (select moduleActions.sourceleadActions.id from ModuleActions moduleActions where moduleActions.modules.id = '5' "
	    		+ "and moduleActions.sourceleadActions.id IN (:roleActonList)) ";
	    List<SourceleadActions> actionsList = session.createQuery(query.toString()).setParameterList("roleActonList", roleActonList).list();
     
     
	    return actionsList;
	}

	@Override
	@Transactional
	public List<SourceleadActions> getAllSourceleadActionsByOrgLocationId(OrgLocations orgLocations) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		
		 List<Long> modulesList = new ArrayList<Long>();
			for (String s : orgLocations.getModulesId().split(",")){
				modulesList.add(Long.parseLong(s));
			 }
       		
	    String query = "from SourceleadActions sa where sa.actionRole != 'ROLE_ENROLL_ORG' and sa.tabs.id NOT IN (13,26,28,29) "
	    		+ "and sa.id IN(select moduleActions.sourceleadActions.id from ModuleActions moduleActions where moduleActions.modules.id IN(:modulesId)) and sa.status = '1' ";
	    List<SourceleadActions> actionsList = session.createQuery(query.toString()).setParameterList("modulesId", modulesList).list();
    
	    return actionsList;
	}

	@Override
	@Transactional
	public SourceleadActions getSourceleadActionsByActionRole(String actionRole) {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<SourceleadActions> action = new ArrayList<SourceleadActions>();
		logger.debug("getSourceleadActionsByActionRole start");
		action = session.createQuery("from SourceleadActions sa where sa.actionRole = :actionRole" ).setString("actionRole", actionRole ).list();
		logger.debug("getSourceleadActionsByActionRole end");
		if (action.size() > 0) {
			return action.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public List<SourceleadActions> getAllSourceleadActionsForEnrollByOrgLocationIdAndOrgId(OrgLocations orgLocations,
			Org org) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<SourceleadActions> actionsList = null;
		List<SourceleadActions> sourceleadActionsList = new ArrayList<>();
		Countries countries = countriesDao.getCountriesByCountryName(orgLocations.getCountry());
			
			if(org.getOrgCode().equalsIgnoreCase("slead")) {
				
				 String query = "from SourceleadActions sa where sa.actionRole != 'ROLE_ENROLL_ORG' and sa.tabs.id NOT IN (13) "
				    		+ "and  sa.id NOT IN (152,153) and sa.status = '1' ";
				 sourceleadActionsList = session.createQuery(query.toString()).list();
			}else {
				
				     OrgLicenseSubscription orgLicenseSubscription = orgLicenseSubscriptionDao.getOrgLicenseSubscriptionByOrgIdAndCountry(org.getId(), orgLocations.getCountry());
				     List<Long> tabsIds = new ArrayList<>();
						String[] registeredTabs = orgLicenseSubscription.getSubscriptionTabs().split(",");
						
						for(int i=0; i<registeredTabs.length; i++) {
							List<Tabs> childTabsList = tabsDao.getClildTabsListByTabId(Long.valueOf(registeredTabs[i]));
							if(childTabsList.size() > 0) {
								for(Tabs childTabs : childTabsList) {
									tabsIds.add(childTabs.getId());
								}
							}else {
								tabsIds.add(Long.valueOf(registeredTabs[i]));
							}
						}
				     
				     String query = "from SourceleadActions sa where sa.actionRole != 'ROLE_ENROLL_ORG' and sa.tabs.id IN (:tabsIds) and sa.id NOT IN (152,153) and sa.status = '1' ";
				    
				     actionsList = session.createQuery(query.toString()).setParameterList("tabsIds", tabsIds).list();
				     for(SourceleadActions actions : actionsList) {
				    	if(actions.getLocationCode() == null || (actions.getLocationCode() != null && actions.getLocationCode().equalsIgnoreCase(countries.getCode2()))) {
				    		sourceleadActionsList.add(actions);
				    	}
				     }
				     
				     
			}
	   
   
	    return sourceleadActionsList;
	}

	@Override
	@Transactional
	public List<SourceleadActions> getAllSourceleadActionsByTabsList(List<Long> tabsList) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
      		
	    String query = "from SourceleadActions sa where sa.actionRole != 'ROLE_ENROLL_ORG' and sa.tabs.id IN (:tabsList) and sa.status = '1' ";
	    List<SourceleadActions> actionsList = session.createQuery(query.toString()).setParameterList("tabsList", tabsList).list();
   
	    return actionsList;
	}

	@Override
	@Transactional
	public List<SourceleadActions> getAllSourceleadGuestOrgActionsForEnroll() throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<SourceleadActions> actionsList = null;
		
		 String query = "from SourceleadActions sa where sa.actionRole != 'ROLE_ENROLL_ORG' and sa.tabs.id NOT IN (13,26,28,29) and sa.id NOT IN (152,153)  and sa.status = '1' ";
		 actionsList = session.createQuery(query.toString()).list();
		    
		return actionsList;
	}

	@Override
	@Transactional
	public List<SourceleadActions> getAllSourceleadActionsByIdsList(List<Long> actionIdList) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
  		
	    String query = "from SourceleadActions sa left join fetch sa.tabs where sa.id IN (:actionIdList)";
	    List<SourceleadActions> actionsList = session.createQuery(query.toString()).setParameterList("actionIdList", actionIdList).list();
   
	    return actionsList;
	}

	@Override
	@Transactional
	public List<Long> getAllSourceleadActionsIdsByTabsList(List<Long> tabsList) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
      		
	    String query = "select sa.id from SourceleadActions sa where sa.actionRole != 'ROLE_ENROLL_ORG' and sa.tabs.id IN (:tabsList) and sa.status = '1' ";
	    List<Long> actionsList = session.createQuery(query.toString()).setParameterList("tabsList", tabsList).list();
   
	    return actionsList;
	}

}
