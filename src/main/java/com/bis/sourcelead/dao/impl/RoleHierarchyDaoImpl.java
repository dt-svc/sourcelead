package com.bis.sourcelead.dao.impl;

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

import com.bis.sourcelead.dao.RoleHierarchyDao;
import com.bis.sourcelead.entity.RoleHierarchy;

@Repository
public class RoleHierarchyDaoImpl implements RoleHierarchyDao {
	
	
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

	private static final Logger logger = LoggerFactory.getLogger(RoleHierarchyDaoImpl.class);

	@Override
	@Transactional
	public RoleHierarchy saveRoleHierarchy(RoleHierarchy roleHierarchy) throws Exception {
		logger.debug("Adding RoleHierarchy");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (roleHierarchy != null) {
				session.saveOrUpdate(roleHierarchy);
			}
			logger.debug("RoleHierarchy Added Successfully");
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return roleHierarchy;
	}

	@Override
	@Transactional
	public List<RoleHierarchy> getRoleHierarchyByOrgLocationId(Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		return session.createQuery("from RoleHierarchy roleHierarchy where roleHierarchy.orgLocations.id = :orgLocationId and roleHierarchy.status = '1' ").setLong("orgLocationId", orgLocationId).setCacheable(true).list();
	}

	@Override
	@Transactional
	public RoleHierarchy getRoleHierarchyByHierarchyLevelAndOrgLocationId(Integer hierarchyLevel, Long orgLocationId)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<RoleHierarchy> roleHierarchy = new ArrayList<RoleHierarchy>();
		logger.debug("RoleHierarchy start");
		roleHierarchy = session.createQuery("from RoleHierarchy roleHierarchy where roleHierarchy.hierarchyLevel = :hierarchyLevel and roleHierarchy.orgLocations.id = :orgLocationId " ).setInteger( "hierarchyLevel", hierarchyLevel ).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		logger.debug("RoleHierarchy end");
		if (roleHierarchy.size() > 0) {
			return roleHierarchy.get(0);
		} else {
			return null;
		}
	}

}
