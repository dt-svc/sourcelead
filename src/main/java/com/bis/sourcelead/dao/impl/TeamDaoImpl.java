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

import com.bis.sourcelead.dao.TeamDao;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.util.CommonUtil;

/**
 * @author sarath kumar
 * @create date 14 sep 2016
 */

@Repository
public class TeamDaoImpl  implements TeamDao{
	
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
	
	@Autowired
	private CommonUtil commonUtil;
	
	
	
	private static final Logger logger = LoggerFactory.getLogger(TeamDaoImpl.class);


	 /**
	   * @author sarath kumar
	   * @since  sep 14 2016
	   * @version 0.0.1
	   * 
	   * This method is used to for getting TeamList By OrgLocationID
	   * 
	   *  
	   * @return string 
	   * 
	   * @throws NullPointer
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Team> getTeamListByOrgLocationID(Long orgLocationId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Team team  where team.orgLocationsId =:orgLocationId";
		return session.createQuery(query).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		
	}
	
	


}
