package com.bis.sourcelead.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.SourceleadRolesDao;
import com.bis.sourcelead.entity.OrgLicenseSubscription;
import com.bis.sourcelead.entity.SourceleadRoles;

@Repository
public class SourceleadRolesDaoImpl implements SourceleadRolesDao {
	
	
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
	@Value("${jdbc.schema}")
	private String schema;
	private static final Logger logger = LoggerFactory.getLogger(SourceleadRolesDaoImpl.class);

	@Override
	@Transactional
	public List<SourceleadRoles> getSourceleadRoles() {
		Session session = this.sessionFactory.getCurrentSession();
		return session.createQuery("from SourceleadRoles sourceleadRoles where sourceleadRoles.status = '1' ").list();
	}

	@Override
	@Transactional

	public boolean roleHaveActionAccessOrNot(String actionId,String roleName) {
		Session session = this.sessionFactory.getCurrentSession();

		 String actionI= actionId;
		//actionlist =  session.createQuery("from SourceleadRoles sourceleadRoles where    FIND_IN_SET("+actionI+",sourceleadRoles.actionId) and sourceleadRoles.SDisplayName = roleName ").list();
		/* DISPLAY_NAME  ='"+roleName+"' and */
		
		String sql = "SELECT * FROM "+schema+".SOURCELEAD_ROLES where DISPLAY_NAME  ='"+roleName+"' and  FIND_IN_SET("+actionId+",ACTION_ID)";
		SQLQuery query = session.createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<SourceleadRoles> actionlist  = query.list();
		Boolean flag = false;
		if (actionlist.size() > 0) {
			flag = true;
		} 
		return flag;
		
		
		

	}

	@Override
	@Transactional
	public SourceleadRoles getSourceleadRolesByRoleName(String roleName) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<SourceleadRoles> sourceleadRoles = new ArrayList<SourceleadRoles>();
		logger.debug("getSourceleadRolesByRoleName start");
		String query = "from SourceleadRoles roles where roles.RoleName = :roleName ";
		sourceleadRoles = session.createQuery(query).setString( "roleName", roleName ).list();
		logger.debug("getSourceleadRolesByRoleName end");
		if (sourceleadRoles.size() > 0) {
			return sourceleadRoles.get(0);
		} else {
			return null;
		}
   	
	
	}

	@Override
	@Transactional
	public List<SourceleadRoles> getGuestOrgSourceleadRoles() throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		return session.createQuery("from SourceleadRoles sourceleadRoles where sourceleadRoles.status = '2' ").list();
	}

	@Override
	@Transactional
	public SourceleadRoles getSourceleadRolesByUUID(String uuid) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<SourceleadRoles> sourceleadRoles = new ArrayList<SourceleadRoles>();
		logger.debug("getSourceleadRolesByUUID start");
		String query = "from SourceleadRoles roles where roles.uuid = :uuid ";
		sourceleadRoles = session.createQuery(query).setString( "uuid", uuid ).list();
		logger.debug("getSourceleadRolesByUUID end");
		if (sourceleadRoles.size() > 0) {
			return sourceleadRoles.get(0);
		} else {
			return null;
		}
   	
	
	}

	@Override
	@Transactional
	public SourceleadRoles saveSourceleadRoles(SourceleadRoles sourceleadRoles) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
	    session.saveOrUpdate(sourceleadRoles);
	    logger.info("Successfully updated..!");
		return sourceleadRoles;
	}

}
