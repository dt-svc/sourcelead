package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ActionRolesDao;
import com.bis.sourcelead.entity.ActionRoles;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class ActionRolesDaoImpl implements ActionRolesDao{
	
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

	private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
	

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ActionRoles addActionRoles(ActionRoles actionRoles) throws Exception {
		logger.debug("Adding ActionRoles");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (actionRoles != null) {
				session.saveOrUpdate(actionRoles);
			}
			logger.debug("ActionRoles Added Successfully");
		} catch (Exception exception) {
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"ActionRoles"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return actionRoles;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ActionRoles> getSourceLeadActionRolesListByOrgId(Long orgId,List<Long> modulesId,Org org) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		
		String actionRoles = "";
		
		if((Status.Y.equals(org.getVms()) && Status.Y.equals(org.getAts())) || (Status.Y.equals(org.getVms()) && Status.Y.equals(org.getAts()) && Status.Y.equals(org.getTimeSheet()))){
			actionRoles = "and actionRoles.roles.id <> '4' ";
		}
		
		else if((Status.Y.equals(org.getVms())) || ((Status.Y.equals(org.getVms())) && (Status.Y.equals(org.getTimeSheet())))){
			actionRoles = "and actionRoles.roles.id <> '4' and actionRoles.roles.id <> '7' and actionRoles.roles.id <> '6' ";
		}
		
		else if(Status.Y.equals(org.getAts()) && Status.Y.equals(org.getTimeSheet())){
			actionRoles = "and actionRoles.roles.id <> '4'";
		}
		
		else if((Status.Y.equals(org.getAts()))){
			actionRoles = "and actionRoles.roles.id <> '4' and actionRoles.roles.id <> '3' ";
		}
		
		else if(Status.Y.equals(org.getTimeSheet())){
			actionRoles = "and actionRoles.roles.id <> '4' and actionRoles.roles.id <> '5' and actionRoles.roles.id <> '6' and actionRoles.roles.id <> '7' and actionRoles.roles.id <> '8'";
		}
		
		
		return session.createQuery("from ActionRoles actionRoles left join fetch actionRoles.org where actionRoles.org.id = :orgId "
				+ "and actionRoles.tabActions.modules.id IN (:modulesId) "+actionRoles+" ")
					  .setLong("orgId", orgId).setParameterList("modulesId", modulesId)
					  .setCacheable(true).list();
		
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ActionRoles> getOrgActionRolesByRoleAndOrgLocationId(Long roleId, Long orgLocationId)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		return session.createQuery("from ActionRoles actionRoles left join fetch actionRoles.orgLocations left join fetch actionRoles.roles "
				+ "left join fetch actionRoles.tabActions tabactions left join fetch tabactions.tabs tabs left join fetch tabs.tabs  "
				+ "where actionRoles.roles.id = :roleId and actionRoles.orgLocations.id = :orgLocationId ")
					  .setLong("roleId", roleId)
					  .setLong("orgLocationId", orgLocationId)
					  .setCacheable(true).list();
	}

}
