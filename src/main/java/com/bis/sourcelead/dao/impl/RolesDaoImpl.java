package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.RolesDao;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class RolesDaoImpl implements RolesDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
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

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Roles> getSourceleadRoles(Long orgId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		return session.createQuery("from Roles roles where roles.id <> '4' and roles.org.id = :orgId ").setLong("orgId", orgId).setCacheable(true).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Roles storeRoles(Roles roles) {
		logger.debug("Adding Roles");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (roles != null) {
				session.saveOrUpdate(roles);
			}
			logger.debug("Roles Added Successfully");
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return roles;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Roles getRolesByRoleNameAndOrgLocationId(String roleName, Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<Roles> roles = new ArrayList<Roles>();
		logger.debug("Roles start");
		roles = session.createQuery("from Roles roles where roles.roleName = :roleName and roles.orgLocations.id = :orgLocationId " ).setString( "roleName", roleName )
				.setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		logger.debug("Roles end");
		if (roles.size() > 0) {
			return roles.get(0);
		} else {
			return null;
		}
   	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Roles> getUserRolesListByModuleAndLoginUserRoleHierarchy(Org org, Long orgLocationId,Long hierarchyId) {
		
		
        Session session = this.sessionFactory.getCurrentSession();
        
        String subQuery = "";
		
        if("slead".equalsIgnoreCase(org.getOrgCode())){
			subQuery = "and roles.roleName != 'ROLE_SOURCELEAD_ADMIN' ";
		}
        
	    String query = "from Roles roles where roles.roleHierarchy.id between ("+hierarchyId+") and (select max(rh.id) from RoleHierarchy rh where rh.orgLocations.id = :orgLocationId)  "+subQuery+" ";
	    List<Roles> rolesList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		
		return rolesList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Roles> getClientOrVendorRolesList(Long orgLocationId, String moduleType) {
		
		Long moduleId = null;
		Long tabId = null;
		ArrayList<Roles> roleList = new ArrayList<>();
		
		if(moduleType.equalsIgnoreCase("VMS")){
			moduleId = (long) Constants.MODULE_VMS;
			tabId = (long) Constants.TAB_VENDOR;
		}
		else if(moduleType.equalsIgnoreCase("ATS")){
			moduleId = (long) Constants.MODULE_ATS;
			tabId = (long) Constants.TAB_CLIENT;
		}
		
		String subQuery = "";
		if(orgLocationId == 1L){
			subQuery = " and roles.roleName != 'ROLE_SOURCELEAD_ADMIN' ";
		}
		
		
		 List<Long> modulesList = new ArrayList<Long>();
		 modulesList.add(moduleId);
		 
		 
		 Session session = this.sessionFactory.getCurrentSession();
        
        String query = "select moduleActions.sourceleadActions.id from ModuleActions moduleActions where moduleActions.modules.id IN(:modulesId) and moduleActions.sourceleadActions.tabs.id = :tabId  ";
	    List<Long> actionsList = session.createQuery(query.toString()).setParameterList("modulesId", modulesList).setLong("tabId", tabId).setCacheable(true).list();
			
	    
	    String query1 = "from Roles roles where roles.orgLocations.id = :orgLocationId "+subQuery+" ";
	    List<Roles> rolesList = session.createQuery(query1.toString()).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		 for(Roles roles : rolesList){
			 List<Long> roleActonList = new ArrayList<Long>();
			 for (String s : roles.getActionId().split(",")){
				 roleActonList.add(Long.parseLong(s));
			 }
			 roleActonList.retainAll(actionsList);
			 if(roleActonList.size() > 0){
				 roleList.add(roles);
			 }
		 }
		 
		return roleList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Roles> getOrgEnrolledRolesByOrgLocationId(Org org, Long orgLocationId) throws Exception {
		
        Session session = this.sessionFactory.getCurrentSession();
        
        String subQuery = "";
		
        if("slead".equalsIgnoreCase(org.getOrgCode())){
			subQuery = "and roles.roleName != 'ROLE_SOURCELEAD_ADMIN' ";
		}
        
	    String query = "from Roles roles where roles.orgLocations.id = :orgLocationId and roles.status = '1'  "+subQuery+" ";
	    List<Roles> rolesList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		
		return rolesList;
	}

	@Override
	@Transactional
	public Roles getRolesByIdAndOrgLocationId(Long id, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<Roles> roles = new ArrayList<Roles>();
		logger.debug("Roles start");
		roles = session.createQuery("from Roles role where role.id = :id and role.orgLocations.id = :orgLocationId" ).setLong( "id", id ).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		logger.debug("Roles end");
		if (roles.size() > 0) {
			return roles.get(0);
		} else {
			return null;
		}
	}


}
