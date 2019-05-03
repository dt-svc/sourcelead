package com.bis.sourcelead.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.RoleHierarchyDao;
import com.bis.sourcelead.dao.RolesDao;
import com.bis.sourcelead.dto.RoleDTO;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.RoleHierarchy;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Repository
public class RolesDaoImpl implements RolesDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	RoleHierarchyDao roleHierarchyDao;
	
	
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
		roles = session.createQuery("from Roles where roleName = :roleName" ).setString( "roleName", roleName ).list();
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
		return session.createQuery("from Roles roles where roles.orgLocations.id = :orgLocationId and roles.actionId IS NOT NULL)")
				.setLong("orgLocationId", orgLocationId).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Roles getRolesByDisplayName(String displayName) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<Roles> roles = new ArrayList<Roles>();
		logger.debug("Roles start");
		roles = session.createQuery("from Roles where displayName = :displayName" ).setString( "displayName", displayName ).list();
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
		
		if(orgId.longValue() == 1l){
			subQuery = "and actionRoles.roles.id <> '4' ";
		}
		
		String subQuery1 = "";
		if(userRoleId.longValue() != 1l || userRoleId.longValue() != 4l){
			
			if(userRoleId.longValue() == 5l){
				subQuery1 = "and actionRoles.roles.id <> '1' ";
			}
			
			else if(userRoleId.longValue() == 3l){
				subQuery1 = "and actionRoles.roles.id <> '1'  and actionRoles.roles.id <> '5' ";
			}
			
			else if(userRoleId.longValue() == 6l){
				subQuery1 = "and actionRoles.roles.id <> '1'  and actionRoles.roles.id <> '5' and actionRoles.roles.id <> '3' ";
			}
			
			else if(userRoleId.longValue() == 7l){
				subQuery1 = "and actionRoles.roles.id <> '1'  and actionRoles.roles.id <> '5' and actionRoles.roles.id <> '3' and actionRoles.roles.id <> '6' ";
			}
			
			else if(userRoleId.longValue() == 8l){
				subQuery1 = "and actionRoles.roles.id <> '1'  and actionRoles.roles.id <> '5' "
						+ "and actionRoles.roles.id <> '3' and actionRoles.roles.id <> '6' and actionRoles.roles.id <> '7' ";
			}
			
		}
		
		
		Session session = this.sessionFactory.getCurrentSession();
		return session.createQuery("from Roles roles where roles.id IN (select distinct actionRoles.roles.id from ActionRoles actionRoles "
				+ "where actionRoles.org.id = :orgId "+subQuery+" "+subQuery1+") ")
				.setLong("orgId", orgId).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Roles getRolesById(Long id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<Roles> roles = new ArrayList<Roles>();
		logger.debug("Roles start");
		roles = session.createQuery("from Roles role left join fetch role.roleHierarchy where role.id = :id" ).setLong( "id", id ).list();
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
		return session.createQuery("from Roles roles where roles.id <> '4' and roles.org.id = :orgId ").setLong("orgId", orgId).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Roles storeRoles(Roles roles) {
		logger.debug("Adding Roles");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (roles != null) {
				if(roles.getId() == null) {
					roles.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
				}
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
				.setLong("orgLocationId", orgLocationId).list();
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
        RoleHierarchy hierarchy  = null;
		
        if("slead".equalsIgnoreCase(org.getOrgCode())){
			subQuery = "and roles.roleName != 'ROLE_SOURCELEAD_ADMIN' ";
		}
        
        try {
			hierarchy = roleHierarchyDao.getRoleHierarchyById(hierarchyId);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
	    String query = "from Roles roles where roles.roleHierarchy.hierarchyLevel between ("+hierarchy.getHierarchyLevel()+") and (select max(rh.hierarchyLevel) from RoleHierarchy rh where rh.orgLocations.id = :orgLocationId) and (roles.actionId IS NOT NULL) and roles.orgLocations.id = :orgLocationId  "+subQuery+" ";
	    List<Roles> rolesList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).list();
		
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
		if(orgLocationId.longValue() == 1L){
			subQuery = " and roles.roleName != 'ROLE_SOURCELEAD_ADMIN' ";
		}
		
		
		 List<Long> modulesList = new ArrayList<Long>();
		 modulesList.add(moduleId);
		 
		 
		 Session session = this.sessionFactory.getCurrentSession();
        
        String query = "select moduleActions.sourceleadActions.id from ModuleActions moduleActions where moduleActions.modules.id IN(:modulesId) and moduleActions.sourceleadActions.tabs.id = :tabId  ";
	    List<Long> actionsList = session.createQuery(query.toString()).setParameterList("modulesId", modulesList).setLong("tabId", tabId).list();
			
	    
	    String query1 = "from Roles roles where roles.orgLocations.id = :orgLocationId "+subQuery+" ";
	    List<Roles> rolesList = session.createQuery(query1.toString()).setLong("orgLocationId", orgLocationId).list();
		 for(Roles roles : rolesList){
			 List<Long> roleActonList = new ArrayList<Long>();
			 if(null != roles.getActionId()){
				 /*for (String s : roles.getActionId().split(",")){
					 roleActonList.add(Long.parseLong(s));
				 }*/
				 for (String s : Arrays.stream(roles.getActionId().split(",")).filter(value -> value != null && value.length() > 0).toArray(size -> new String[size])){
	    			 roleActonList.add(Long.parseLong(s));
	        	 }
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
			subQuery = "and roles.roleName != 'ROLE_SOURCELEAD_ADMIN' and roles.roleName != 'ROLE_CROWDSOURCING_PROFESSIONAL' and roles.roleName != 'ROLE_CROWDSOURCING_RECRUITER' ";
		}
        
	    String query = "from Roles roles left join fetch roles.roleHierarchy where roles.orgLocations.id = :orgLocationId and roles.status = '1'  "+subQuery+" ";
	    List<Roles> rolesList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).list();
		
		return rolesList;
	}

	@Override
	@Transactional
	public Roles getRolesByIdAndOrgLocationId(Long id, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<Roles> roles = new ArrayList<Roles>();
		logger.debug("Roles start");
		roles = session.createQuery("from Roles role where role.id = :id and role.orgLocations.id = :orgLocationId" ).setLong( "id", id ).setLong("orgLocationId", orgLocationId).list();
		logger.debug("Roles end");
		if (roles.size() > 0) {
			return roles.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public Roles getRolesByRoleDisplayNameAndOrgLocationId(String roleName, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<Roles> roles = new ArrayList<Roles>();
		logger.debug("Roles start");
		roles = session.createQuery("from Roles roles where roles.displayName = :displayName and roles.orgLocations.id = :orgLocationId " ).setString( "displayName", roleName ).setLong("orgLocationId", orgLocationId).list();
		logger.debug("Roles end");
		if (roles.size() > 0) {
			return roles.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Roles> getTabAccessRolesListByOrgLocationIdAndTabId(Long orgLocationsId, Long tabId) throws Exception {
		
	  Session session = this.sessionFactory.getCurrentSession();
		
	 String actionsQuery = "select sa.id from SourceleadActions sa where sa.tabs.id = :tabId  ";
     List<Long> tabActionsIds = session.createQuery(actionsQuery.toString()).setLong( "tabId", tabId).list();
     
     String rolesQuery = "from Roles roles where roles.orgLocations.id = :orgLocationsId ";
     List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setLong( "orgLocationsId", orgLocationsId).list();
     List<Roles> ownerRolesList = new ArrayList<Roles>();
     
     for(Roles roles : rolesList){
    	 if(null != roles.getActionId()){
    		 List<Long> roleActionsList = new ArrayList<Long>();
    		 /*for (String s : roles.getActionId().split(",")){
        		 roleActionsList.add(Long.parseLong(s));
        	 }*/
    		 for (String s : Arrays.stream(roles.getActionId().split(",")).filter(value -> value != null && value.length() > 0).toArray(size -> new String[size])){
    			 roleActionsList.add(Long.parseLong(s));
        	 }
        	 
        	 roleActionsList.retainAll(tabActionsIds);
        	 if(roleActionsList.size() > 0){
        		 ownerRolesList.add(roles);
        	 }
    	 }
    	 
     }
     return ownerRolesList;
        
	}

	@Override
	@Transactional
	public List<Roles> getRolesListExpcetCurrentUserRole(Long roleId, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		return session.createQuery("from Roles roles where roles.id <> :roleId and roles.orgLocations.id = :orgLocationId and roles.actionId IS NOT NULL ")
				.setLong("roleId", roleId).setLong("orgLocationId", orgLocationId).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Roles> getRolesListUsingMultipleIds(String[] roleIds) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		// convert string array to long array
		Long[] data = new Long[roleIds.length];
		for (int i = 0; i < roleIds.length; i++) {
		data[i] = Long.valueOf(roleIds[i]);
		}
		return session.createQuery("from Roles roles where roles.id IN (:roleIds) ").setParameterList("roleIds", data).list();
	}

	@Override
	@Transactional
	public List<Roles> getUserRolesListByRoleHierarchyIdAndOrgLocationId(Long orgLocationId, Long hierarchyId,String orgCode)
			throws Exception {
		
		    Session session = this.sessionFactory.getCurrentSession();
	        
	        String subQuery = "";
			
	        if("slead".equalsIgnoreCase(orgCode)){
				subQuery = "and roles.roleName != 'ROLE_SOURCELEAD_ADMIN' ";
			}
	        
		    String query = "from Roles roles  where "
				+ "roles.roleHierarchy.id = :hierarchyId and roles.orgLocations.id = :orgLocationId and roles.status = '1' "+subQuery+" ";
		    List<Roles> rolesList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("hierarchyId", hierarchyId).list();
			
			return rolesList;
	}

	@Override
	@Transactional
	public Roles getRolesByRoleNameAndOrgId(String roleName, Long orgId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<Roles> roles = new ArrayList<Roles>();
		logger.debug("Roles start");
		roles = session.createQuery("from Roles roles left join fetch roles.org where roles.roleName = :roleName and roles.org.id = :orgId" ).setString( "roleName", roleName ).setLong("orgId", orgId).list();
		logger.debug("Roles end");
		if (roles.size() > 0) {
			return roles.get(0);
		} else {
			return null;
		}
   	}

	@Override
	@Transactional
	public List<Roles> getAllRolesListByOrgLocationIdList(List<Long> orgLocationIdsList) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Roles roles where roles.orgLocations.id IN (:orgLocationIdsList) ";
		List<Roles> rolesList = session.createQuery(query.toString()).setParameterList("orgLocationIdsList", orgLocationIdsList).list();
		return rolesList;
	}

	@Override
	@Transactional
	public Roles getRolesByUUIDAndOrgLocationId(String uuid, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<Roles> roles = new ArrayList<Roles>();
		logger.debug("Roles start");
		roles = session.createQuery("from Roles role where role.uuid = :uuid and role.orgLocations.id = :orgLocationId" ).setString( "uuid", uuid ).setLong("orgLocationId", orgLocationId).list();
		logger.debug("Roles end");
		if (roles.size() > 0) {
			return roles.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public Roles getRolesByUUID(String uuid) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<Roles> roles = new ArrayList<Roles>();
		logger.debug("Roles start");
		roles = session.createQuery("from Roles role where role.uuid = :uuid " ).setString( "uuid", uuid ).list();
		logger.debug("Roles end");
		if (roles.size() > 0) {
			return roles.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public List<String> getRolesListByOrgLocationId(Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		query ="SELECT "
				+" R.DISPLAY_NAME "
				+" FROM ROLES R "
				+" WHERE "
				+" R.ORG_LOCATION_ID = :orgLocationId";
		Query queryObj = session.createSQLQuery(query);
		queryObj.setLong("orgLocationId", orgLocationId);	
		List<String> rolesList = (List<String>)queryObj.list();
		
		return rolesList;
	}

	@Override
	@Transactional
	public List<RoleDTO> getRolesDTOListByOrgLocationId(Long orgLocationId) throws Exception {

		List<RoleDTO> rolesDTOList = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		query = "SELECT R.ID AS rolesId, "
				+ " R.DISPLAY_NAME AS displayName "
				+ " FROM ROLES R WHERE R.ORG_LOCATION_ID = :orgLocationId AND R.ACTION_ID IS NOT NULL ";
		Query queryObj = session.createSQLQuery(query);
		queryObj.setLong("orgLocationId", orgLocationId);
		queryObj.setResultTransformer(Transformers.aliasToBean(RoleDTO.class));
		rolesDTOList = queryObj.list();
		return rolesDTOList;
	
	}
	
	@Override
	@Transactional
	public List<String> getRolesNamesListByRolesId(List<String> roleIds) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		query ="SELECT "
				+" R.DISPLAY_NAME "
				+" FROM ROLES R "
				+" WHERE "
				+" R.ID IN (:roleIds)";
		Query queryObj = session.createSQLQuery(query);
		queryObj.setParameterList("roleIds", roleIds);
		List<String> rolesNamesList = (List<String>)queryObj.list();
		return rolesNamesList;
	
	}



}
