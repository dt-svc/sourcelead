package com.bis.sourcelead.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.SourceleadActionsDao;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.SourceleadActions;

@Repository
public class SourceleadActionsDaoImpl implements SourceleadActionsDao {
	
	

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
	

	private static final Logger logger = LoggerFactory.getLogger(SourceleadActionsDaoImpl.class);

	@Override
	@Transactional
	public List<SourceleadActions> getLoginUserRoleActions(List<Long> modulesId, Long roleId,Long orgId) throws Exception {

		 Session session = this.sessionFactory.getCurrentSession();
         List<Roles> rolesList = new ArrayList<Roles>();
		 rolesList = session.createQuery("from Roles roles where roles.id = :roleId and roles.org.id = :orgId" ).setLong( "roleId", roleId ).setLong( "orgId", orgId).setCacheable(true).list();
		 List<Long> roleActonList = new ArrayList<Long>();
		 for(Roles roles : rolesList){
			 for (String s : roles.getActionId().split(",")){
				 roleActonList.add(Long.parseLong(s));
			 }
		 }
		
	    String query = "from SourceleadActions sa where sa.id IN (select moduleActions.sourceleadActions.id from ModuleActions moduleActions where moduleActions.modules.id IN(:modulesId) "
	    		+ "and moduleActions.sourceleadActions.id IN (:roleActonList)) ";
	    List<SourceleadActions> actionsList = session.createQuery(query.toString()).setParameterList("modulesId", modulesId).setParameterList("roleActonList", roleActonList).setCacheable(true).list();
      
	    return actionsList;
	}

	@Override
	@Transactional
	public List<SourceleadActions> getLoginUserRoleActionsByOrgLocationId(List<Long> modulesId, Long roleId,
			Long orgLocationId) throws Exception {

		 Session session = this.sessionFactory.getCurrentSession();
         List<Roles> rolesList = new ArrayList<Roles>();
		 rolesList = session.createQuery("from Roles roles where roles.id = :roleId and roles.orgLocations.id = :orgLocationId" ).setLong( "roleId", roleId ).setLong( "orgLocationId", orgLocationId).setCacheable(true).list();
		 List<Long> roleActonList = new ArrayList<Long>();
		 for(Roles roles : rolesList){
			 for (String s : roles.getActionId().split(",")){
				 roleActonList.add(Long.parseLong(s));
			 }
		 }
		
	    String query = "from SourceleadActions sa where sa.id IN (select moduleActions.sourceleadActions.id from ModuleActions moduleActions where moduleActions.modules.id IN(:modulesId) "
	    		+ "and moduleActions.sourceleadActions.id IN (:roleActonList)) ";
	    List<SourceleadActions> actionsList = session.createQuery(query.toString()).setParameterList("modulesId", modulesId).setParameterList("roleActonList", roleActonList).setCacheable(true).list();
      
      
	    return actionsList;
	}

}
