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

import com.bis.sourcelead.dao.TeamDao;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.exception.SourceLeadObjectNotFoundException;
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
		String query = "from Team team  where team.orgLocationsId =:orgLocationId and  team.status <> 0";
		return session.createQuery(query).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		
	}

	/**
	   * @author prasad K
	   * @since  sep 19 2016
	   * @version 0.0.1
	   * 
	   * This method is used to Store Team details into DB
	   * 
	   *  
	   * @return team 
	   * 
	   * @throws NullPointer
	 */
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Team storeTeam(Team team) throws Exception {
		logger.debug("Storing Team");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (team != null) {
				session.saveOrUpdate(team);
			}
			logger.debug("Team Stored Successfully");
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"Team"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return team;
	}

	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Team getTeamByTeamId(Long teamId) throws Exception {
		logger.debug("In Team getTeamByTeamId method");
		Session session = this.sessionFactory.getCurrentSession();
		return (Team) session.get(Team.class, teamId);
	}

	@Override
	public Boolean getTeamOwnersListByUserId(User user,Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Team> teamList = new ArrayList<>();
		teamList = session.createQuery("from Team team where team.ownerUserId.id =:userId and team.orgLocationsId =:orgLocationId andteam.status <> 0")
						   .setLong("userId", user.getId())
						   .setLong("orgLocationId", orgLocationId)
						   .setCacheable(true).list();
		
		Boolean flag = false;
		if (teamList.size() > 0) {
			flag = true;
		} 
		return flag;
		
	}


	/*@Override
	@Transactional(rollbackFor = { Exception.class })
	public Team deleteTeamById(Long teamId,Long id) throws Exception {
		 logger.debug("In Team deleteTeamByTeamId method");
		 Session session = this.sessionFactory.getCurrentSession();
		 boolean isDeleted = false;
		 try{
			 if(id != null){
				 Team team= (Team)session.load(Team.class,id);
				 team.setStatus("0");
				 session.saveOrUpdate(team);
				 logger.info("Successfully deleted !!!");
				 isDeleted = true;
			 }	 
		 }catch(Exception e){
			    StringBuilder message= new StringBuilder(MessageFormat.format(statusDelete,"TEAM"));
				throw new SourceLeadObjectNotFoundException(message.toString());	
		 }
		
		return isDeleted;
	}*/

}
