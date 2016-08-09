package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ActionRolesDao;
import com.bis.sourcelead.entity.ActionRoles;

@Repository
public class ActionRolesDaoImpl implements ActionRolesDao{
	
	@Autowired
	private SessionFactory sessionFactory;

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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionRoles;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ActionRoles> getSourceLeadActionRolesListByOrgId(Long orgId,List<Long> modulesId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		return session.createQuery("from ActionRoles actionRoles left join fetch actionRoles.org where actionRoles.org.id = :orgId "
				+ "and actionRoles.tabActions.modules.id IN (:modulesId) and actionRoles.roles.id <> '4' ")
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
