package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.RoleActionMappingDao;
import com.bis.sourcelead.entity.RoleActionMapping;
import com.bis.sourcelead.entity.SourceleadActions;

@Repository
public class RoleActionMappingDaoImpl implements RoleActionMappingDao {

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
	

	private static final Logger logger = LoggerFactory.getLogger(RoleActionMappingDaoImpl.class);

	@Override
	@Transactional
	public List<RoleActionMapping> getRoleActionMappingForRegistrationByOrgId(Long orgId) {

		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from RoleActionMapping rm where rm.org.id = :orgId and rm.roles.id <> '4' ";
	    List<RoleActionMapping> roleActionMappingList = session.createQuery(query.toString()).setLong( "orgId", orgId).setCacheable(true).list();
      
	    return roleActionMappingList;
	}

	@Override
	@Transactional
	public RoleActionMapping saveRoleActionMapping(RoleActionMapping roleActionMapping) {
		logger.debug("Adding RoleActionMapping");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (roleActionMapping != null) {
				session.saveOrUpdate(roleActionMapping);
			}
			logger.debug("RoleActionMapping Added Successfully");
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return roleActionMapping;
	}

}
