package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.TeamDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
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
	
	@Autowired
	private UserRoleDao userRoleDao;
	
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
		String query = "from Team team  where team.orgLocationsId =:orgLocationId and  team.status <> 0 ORDER BY team.name Asc";
		return session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		
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

	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Team getTeamByTeamId(Long teamId) throws Exception {
		
		logger.debug("In Team getTeamByTeamId method");
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Team tm left join fetch tm.ownerUserId where tm.id =:teamId";
		List<Team> teamList = session.createQuery(query).setLong("teamId", teamId).list();
		
		Team team = null;
		if(teamList != null && !teamList.isEmpty())
		{
			team = (Team) teamList.iterator().next();
		}
		return team;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Boolean getTeamOwnersListByUserId(User user,Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Team> teamList = new ArrayList<>();
		teamList = session.createQuery("from Team team where team.ownerUserId.id =:userId and team.orgLocationsId =:orgLocationId and team.status <> 0")
						   .setLong("userId", user.getId())
						   .setLong("orgLocationId", orgLocationId)
						   
						   .list();
		
		Boolean flag = false;
		if (teamList.size() > 0) {
			flag = true;
		} 
		return flag;
		
	}

	@Override
	@Transactional
	public Integer getTeamCountByTeamOwner(Long userId, Long orgLocationId) throws Exception {
		logger.debug("getTeamCountByTeamOwner start");
		Session session = this.sessionFactory.getCurrentSession();
			String query = "select count(team.id) from Team team "
					+ "where team.ownerUserId.id = :userId and team.orgLocationsId.id = :orgLocationId ";
			
			List<Long> teamCount = session.createQuery(query.toString()).setLong("userId", userId).setLong("orgLocationId", orgLocationId).list();
			Integer count = 0;
			if(teamCount != null && !teamCount.isEmpty()){
					count = teamCount.get(0).intValue();
			}
			return count;
        
	}

	@Override
	@Transactional
	public List<Team> getTeamListByTeamOwner(Long userId, Long orgLocationId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Team team "
					+ "where team.ownerUserId.id = :userId and team.orgLocationsId.id = :orgLocationId";
		return session.createQuery(query).setLong("userId", userId).setLong("orgLocationId", orgLocationId).list();
		
	}

	@Override
	@Transactional
	public List<Team> getUserAssignedTeamListByOrgLocationId(Long userId, Long orgLocationId) throws Exception {
		UserRole role = userRoleDao.getUserDetailsByUserIdAndLocationId(userId, orgLocationId);
		Session session = this.sessionFactory.getCurrentSession();
		if(role.getRoles().getRoleName().equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN") || role.getRoles().getRoleName().equalsIgnoreCase("ROLE_ADMIN")) {
			/*String query = "from Team team "
					+ "where (team.orgLocationsId.id = :orgLocationId and team.status = '1') OR "
					+ " (team.id IN (select tm.teamId.id from TeamMembers tm where tm.teamId.orgLocationsId.id = :orgLocationId and tm.status = '1'))";*/
			String query = "from Team team "
					+ "where (team.orgLocationsId.id = :orgLocationId and team.status = '1')";
		return session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		}else {
			String query = "from Team team "
					+ "where (team.ownerUserId.id = :userId and team.orgLocationsId.id = :orgLocationId and team.status = '1') OR "
					+ " (team.id IN (select tm.teamId.id from TeamMembers tm where tm.teamId.orgLocationsId.id = :orgLocationId and tm.memberUserId.id = :userId and tm.status = '1'))";
		return session.createQuery(query).setLong("userId", userId).setLong("orgLocationId", orgLocationId).list();
		}
	}

	@Override
	@Transactional
	public Boolean getTeamOwnersListByUserIdAndRequirementId(User user, Long orgLocationId, Long requirement) {
		// TODO Auto-generated method stub

		Session session = this.sessionFactory.getCurrentSession();
		List<Team> teamList = new ArrayList<>();
		teamList = session.createQuery("from Team team where team.ownerUserId.id =:userId and team.orgLocationsId =:orgLocationId and team.status <> 0 and team.id IN (select rato.teamId.id from RequirementAssignTo  rato   where rato.requirementId.id = :requirement )")
						   .setLong("userId", user.getId())
						   .setLong("orgLocationId", orgLocationId)
						   .setLong("requirement", requirement)
						   
						   .list();
		
		Boolean flag = false;
		if (teamList.size() > 0) {
			flag = true;
		} 
		return flag;
		
	}

	@Override
	@Transactional
	public List<Long> getTeamIdsListByOrgLocationID(Long orgLocationId) throws Exception {
		
		
		logger.debug("In TeamDaoImpl class getTeamIdsListByOrgLocationID method");
		Session session = this.sessionFactory.getCurrentSession();
		
			 
		String query = "select team.id from Team team  where team.orgLocationsId =:orgLocationId and  team.status <> 0 ORDER BY team.id DESC ";
		List<Long> teamIdList =  session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId)
									.list();
		
		return teamIdList;
	}

	@Override
	@Transactional
	public List<Long> getUserAssignedTeamIdsListByOrgLocationId(Long userId, Long orgLocationId) throws Exception {
		
		logger.debug("In TeamDaoImpl class getUserAssignedTeamIdsListByOrgLocationId method");
		Session session = this.sessionFactory.getCurrentSession();
		
			 
		String query = "select team.id from Team team  where (team.ownerUserId.id = :userId and team.orgLocationsId.id = :orgLocationId and team.status = '1') OR "
					+ " (team.id IN (select tm.teamId.id from TeamMembers tm where tm.memberUserId.id = :userId and tm.status = '1')) ";
		List<Long> teamIdList =  session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).setLong("userId", userId)
									.list();
		
		return teamIdList;
	}

	@Override
	@Transactional
	public Team findByTeamName(String name, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<Team> team = new ArrayList<Team>();
		logger.debug("findByTeamName start");
		try{
			team = session.createQuery("from Team tm where tm.name=:name and tm.orgLocationsId.id = :orgLocationId and tm.status = '1' " )
					.setString( "name", name ).setLong("orgLocationId", orgLocationId ).list();
			
			logger.debug("findByTeamName end");
			if (team.size() > 0) {
				return team.get(0);
			} else {
				return null;
			}
		}catch(Exception e){
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return null;
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
	
	@Override
	@Transactional
	public List<Team> getTeamListByTeamOwnerAndStatus(Long userId, Long orgLocationId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Team team "
					+ "where team.ownerUserId.id = :userId and team.orgLocationsId.id = :orgLocationId and  team.status <> 0 ORDER BY team.id DESC";
		return session.createQuery(query).setLong("userId", userId).setLong("orgLocationId", orgLocationId).list();
		
	}
	
	@Override
	@Transactional
	public List<Team> getTeamListByMultipleIds(String[] teamids,Long locationId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		// convert string array to long array
				Long[] data = new Long[teamids.length];
				for (int i = 0; i < teamids.length; i++) {
				data[i] = Long.valueOf(teamids[i]);
				}
		String query = "from Team team where team.id IN (:teamIds) and team.orgLocationsId.id = :locationId";
		return session.createQuery(query).setParameterList("teamIds", data).setLong("locationId", locationId).list();
		
	}

}
