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

import com.bis.sourcelead.dao.TeamMemberDao;
import com.bis.sourcelead.entity.TeamMembers;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CommonUtil;

/**
 * @author sarath kumar
 * @create date 14 sep 2016
 */

@Repository
public class TeamMemberDaoImpl  implements TeamMemberDao{
	
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
	
	
	
	private static final Logger logger = LoggerFactory.getLogger(TeamMemberDaoImpl.class);


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
	public List<TeamMembers> getTeamMembersListByTeamID(Long teamId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from TeamMembers tm  where tm.teamId =:teamId and tm.status = '1'";
		return session.createQuery(query).setLong("teamId", teamId).setCacheable(true).list();
		
	}


	@Override
	public TeamMembers getTeamMemberByIdAndLocationId(Long teamMmeberId,Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from TeamMembers tm  where tm.id = :teamMmeberId and tm.orgLocationId.id =:orgLocationId";  
		List<TeamMembers> teamMembersList = session.createQuery(query).setLong("teamMmeberId", teamMmeberId).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		TeamMembers teamMember = null;
		if(teamMembersList != null && !teamMembersList.isEmpty()){
			teamMember = teamMembersList.iterator().next();
		}
		return teamMember;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public TeamMembers saveOrUpdateTeamMembers(TeamMembers teamMembers) throws Exception {
		logger.debug("Storing Candidate");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (teamMembers != null) {
				session.saveOrUpdate(teamMembers);
			}
			logger.debug("Candidate Stored Successfully");
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"Candidate"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return teamMembers;
		
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public TeamMembers getTeamMemberByTeamMemberId(Long teamMemberId) throws Exception {
		
		logger.debug("In Team getTeamByTeamId method");
		Session session = this.sessionFactory.getCurrentSession();
		return (TeamMembers) session.get(TeamMembers.class, teamMemberId);
		
	}
	
	
	
	 

}
