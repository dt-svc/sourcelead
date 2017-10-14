package com.bis.sourcelead.dao.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private UserRoleDao userRoleDao;
	
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
	
	/*@param
	 * (non-Javadoc)
	 * @see com.bis.sourcelead.dao.UserDao#findByUserName(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public User findByUserName(String username) {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<User> users = new ArrayList<User>();
		logger.debug("findByUserName start");
		users = session.createQuery("from User where userName=:username" ).setString( "username", username ).setCacheable(true).list();
		logger.debug("findByUserName end");
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
   	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public User findByEmail(String email) {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<User> users = new ArrayList<User>();
		logger.debug("findByEmail start");
		users = session.createQuery("from User where email=:email" ).setString( "email", email ).setCacheable(true).list();
		logger.debug("findByEmail end");
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
   	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public User findByEmailAndOrgLocationsId(String email, Long orgLocationsId) {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<User> users = new ArrayList<User>();
		logger.debug("findByEmailAndOrgLocationsId start");
		users = session.createQuery("from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations.id =:orgLocationsId) and user.email=:email" ).setString("email", email ).setLong("orgLocationsId", orgLocationsId).setCacheable(true).list();
		logger.debug("findByEmailAndOrgLocationsId end");
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
   	}
	
	
	

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public User addUser(User user) throws Exception {
		logger.debug("Adding User");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (user != null) {
				session.saveOrUpdate(user);
			}
			logger.debug("User Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"USER"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return user;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public String validateEmailandCandidate(String website, String email, String sourceleadBLockedwebsites) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		
		String[] SourceLead_BLOCKED_SOCIAL_DOMAINS = sourceleadBLockedwebsites.split(",");
		if(!website.startsWith("http") && !website.startsWith("https")){
			 website = "http://" + website;
	    }        
	    URL netUrl = null;
		try {
			netUrl = new URL(website);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String status = " ";
	    String host = netUrl.getHost();
	    if(host.startsWith("www")){
	        host = host.substring("www".length()+1);
	    }
	    
	    String[] emailHostArray = email.split("@");
	    if(emailHostArray != null && emailHostArray.length >1 ){
	    	String emailHost = emailHostArray[1];
	    	/*if(emailHost.equalsIgnoreCase(host)){*/
	 	    	if(Arrays.asList(SourceLead_BLOCKED_SOCIAL_DOMAINS).contains(host)){
	 	    		status="Invalid Email";
	 	    	}/*else{
	 	    		    User user = findByEmail(email);
			 	      	if(user != null &&  user.getEmail().equalsIgnoreCase(email)){
			 	    		status="Existed Email";
			 	    	}else{
			 	    		status="Valid Email";
			 	    	}
		 	    }*/
	 	    /*}
	    	else{
	 	    	status="Invalid Email";
	 	    }*/
	    }
 	    else{
 	    	status="Invalid Email";
 	    }
	    
	return status;
		
}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Collection<User> getUsers() throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(User.class);
		Collection<User> orgList = crit.setCacheable(true).list();
		return orgList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public User getUserById(Long Id) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<User> users = new ArrayList<User>();
		logger.debug("getUserById start");
		users = session.createQuery("from User where id=:id" ).setCacheable(true).setLong( "id", Id ).setCacheable(true).list();
		logger.debug("getUserById end");
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
   	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public User getUserByIdAndOrgLocationsId(Long Id, Long orgLocationsId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<User> users = new ArrayList<User>();
		logger.debug("getUserById start");
		String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations.id =:orgLocationsId) and user.id =:id ";
		users = session.createQuery(query).setCacheable(true).setLong( "id", Id ).setLong("orgLocationsId", orgLocationsId).setCacheable(true).list();
		logger.debug("getUserById end");
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
   	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Collection<User> getUsersByUserRoles(Long orgLocations) throws Exception {
		// TODO Auto-generated method stub
		
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations =:orgLocations) ";
	    List<User> userRoleList = session.createQuery(query.toString()).setLong("orgLocations", orgLocations).setCacheable(true).list();
        return userRoleList;
        
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<User> getUsersByUserRolesAndOrgLocationIdAndNoCurrentUser(Long userId, Long orgLocationsId)
			throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		Boolean isAdmin = userRoleDao.isUserAdmin(userId);
				
		if(isAdmin){
			query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations=:orgLocationsId and userRole.userState != 'INACTIVE' and userRole.roles.id <>'2') and user.id <> :userId";

		}else{
			 query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations=:orgLocationsId and userRole.userState != 'INACTIVE' and userRole.roles.id <>'2') and user.id <> :userId  ";

		}
		
	    List<User> userRoleList = session.createQuery(query.toString()).setLong( "orgLocationsId", orgLocationsId).setLong( "userId", userId).setCacheable(true).list();
      
	    return userRoleList;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<User> getUsersByUserRolesAndOrgLocationId(Long orgLocationsId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();

	    String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations=:orgLocationsId and userRole.userState != 'INACTIVE' )  ";

	    List<User> userRoleList = session.createQuery(query.toString()).setLong( "orgLocationsId", orgLocationsId).setCacheable(true).list();
        return userRoleList;
        
	}

	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<User> getUsersRoleCountByRoleIdAndLocationIdAndNotLoggedInUser(Long roleId, Long orgLocationsId,Long userId) throws Exception {
		
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "select user.id from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.roles.id = :roleId and userRole.orgLocations.id = :orgLocationsId and userRole.user.id <> :userId)";
		List<User> usersList = session.createQuery(query.toString()).setLong("roleId", roleId).setLong( "orgLocationsId", orgLocationsId).setLong( "userId", userId).setCacheable(true).list();
        return usersList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Employees> getAllUserOrgCodeAndLocationCodeTimeSheetNotification(Long userId) throws Exception{
		
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from Employees emp left join fetch emp.orgLocations orgLocation left join fetch orgLocation.org org where emp.user.id IN(select id from User user where user.id =:userId)"; 
		List<Employees> empList = session.createQuery(query.toString()).setLong("userId", userId).setCacheable(true).list();
		return empList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public User getUserByUserCode(String userCode) throws Exception {
			Session session = this.sessionFactory.getCurrentSession();
			List<User> users = new ArrayList<User>();
			logger.debug("getUserBy user code start");
			users = session.createQuery("from User user where user.userCode =:userCode" ).setCacheable(true).setString( "userCode", userCode ).setCacheable(true).list();
			logger.debug("getUserBy user code end");
			if (users.size() > 0) {
				return users.get(0);
			} else {
				return null;
			}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<User> getOwnersByOrgLocationId(Long orgLocationsId) throws Exception {
		
	 Session session = this.sessionFactory.getCurrentSession();
     String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations=:orgLocationsId and userRole.userState != 'INACTIVE')  ";
     List<User> userRoleList = session.createQuery(query.toString()).setLong( "orgLocationsId", orgLocationsId).setCacheable(true).list();
     return userRoleList;
        
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<User> getManagersByUserRolesAndOrgLocationId(Long roleId , Long orgLocationsId,Long tabId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String subQuery = "";
		
		
		 String actionsQuery = "select sa.id from SourceleadActions sa where sa.tabs.id = :tabId  ";
	     List<Long> tabActionsIds = session.createQuery(actionsQuery.toString()).setLong( "tabId", tabId).setCacheable(true).list();
	     
	     String rolesQuery = "from Roles roles where roles.orgLocations.id = :orgLocationsId ";
	     List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setLong( "orgLocationsId", orgLocationsId).setCacheable(true).list();
	     List<Long> ownerRolesList = new ArrayList<Long>();
	     
	     for(Roles roles : rolesList){
	    	 if(null != roles.getActionId()){
	    		 List<Long> roleActionsList = new ArrayList<Long>();
		    	 for (String s : roles.getActionId().split(",")){
		    		 roleActionsList.add(Long.parseLong(s));
		    	 }
		    	 
		    	 roleActionsList.retainAll(tabActionsIds);
		    	 if(roleActionsList.size() > 0){
		    		 ownerRolesList.add(roles.getId());
		    	 }
	    	 }
	     }
	     
	     
		
		subQuery = "userRole.user.id from UserRole userRole where  (userRole.roles.roleHierarchy.hierarchyLevel <= (select r.roleHierarchy.hierarchyLevel from Roles r where r.id = :roleId)) and (userRole.roles.actionId IS NOT NULL) and userRole.orgLocations = :orgLocationsId and userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList)";
		
		String query = "from User user where user.id IN (select "+subQuery+" )  ";
	    List<User> userRoleList = session.createQuery(query).setLong("roleId", roleId).setParameterList("ownerRolesList", ownerRolesList).setLong( "orgLocationsId", orgLocationsId).setCacheable(true).list();
        return userRoleList;
        
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<User> getManagersByUserIdAndUserRolesAndOrgLocationIdAndUserTabId(Long userId, Long roleId, Long orgLocationsId, Long tabId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String subQuery = "";
		
		
		 String actionsQuery = "select sa.id from SourceleadActions sa where sa.tabs.id = :tabId  ";
	     List<Long> tabActionsIds = session.createQuery(actionsQuery.toString()).setLong( "tabId", tabId).setCacheable(true).list();
	     
	     String rolesQuery = "from Roles roles where roles.orgLocations.id = :orgLocationsId ";
	     List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setLong( "orgLocationsId", orgLocationsId).setCacheable(true).list();
	     List<Long> ownerRolesList = new ArrayList<Long>();
	     
	     for(Roles roles : rolesList){
	    	 if(null != roles.getActionId()){
	    		 List<Long> roleActionsList = new ArrayList<Long>();
		    	 for (String s : roles.getActionId().split(",")){
		    		 roleActionsList.add(Long.parseLong(s));
		    	 }
		    	 
		    	 roleActionsList.retainAll(tabActionsIds);
		    	 if(roleActionsList.size() > 0){
		    		 ownerRolesList.add(roles.getId());
		    	 }
	    	 }
	     }
	     
	     
		
		subQuery = "userRole.user.id from UserRole userRole where  (userRole.roles.roleHierarchy.hierarchyLevel <= (select r.roleHierarchy.hierarchyLevel from Roles r where r.id = :roleId)) and (userRole.roles.actionId IS NOT NULL) and userRole.orgLocations = :orgLocationsId and userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList) and userRole.user.id <> :userId";
		
		String query = "from User user where user.id IN (select "+subQuery+" )  ";
	    List<User> userRoleList = session.createQuery(query).setLong("roleId", roleId).setLong( "userId", userId).setParameterList("ownerRolesList", ownerRolesList).setLong( "orgLocationsId", orgLocationsId).setCacheable(true).list();
        return userRoleList;
        
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<User> getManagersByUserIdAndUserRolesAndOrgLocationId(Long userId,Long roleId , Long orgLocationsId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String subQuery = "";
		/*String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations=:orgLocationsId and userRole.userState != 'INACTIVE' and userRole.user.id <> :userId)  ";
	    List<User> userRoleList = session.createQuery(query).setLong( "orgLocationsId", orgLocationsId).setLong( "userId", userId).setCacheable(true).list();
        return userRoleList;*/
        
        subQuery = "userRole.user.id from UserRole userRole where  (userRole.roles.roleHierarchy.hierarchyLevel <= (select r.roleHierarchy.hierarchyLevel from Roles r where r.id = :roleId)) and (userRole.roles.actionId IS NOT NULL) and userRole.orgLocations = :orgLocationsId and userRole.userState != 'INACTIVE' and userRole.user.id <> :userId ";
		
		String query = "from User user where user.id IN (select "+subQuery+" )  ";
	    List<User> userRoleList = session.createQuery(query).setLong("roleId", roleId).setLong( "userId", userId).setLong( "orgLocationsId", orgLocationsId).setCacheable(true).list();
        return userRoleList;
        
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public User getUserDetailsByUserCode(String userCode) {
		Session session = this.sessionFactory.getCurrentSession();
		List<User> users = session.createQuery("from User where userCode=:userCode" ).setString( "userCode", userCode ).setCacheable(true).list();
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<User> getOwnersByOrgLocationIdAndNotExistedOwnerId(Long orgLocationsId, Long ownerId,List<Long> ownerListOfSpoc) {
		 Session session = this.sessionFactory.getCurrentSession();
		 List<User> userRoleList;	     
	     if(ownerListOfSpoc!=null && !ownerListOfSpoc.isEmpty()){
	    	//removed by prasad salina --->user.id !=:ownerId and 
			 String query = "from User user where user.id NOT IN (:ids) and user.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations=:orgLocationsId and userRole.userState != 'INACTIVE')  ";
		      userRoleList = session.createQuery(query).setLong( "orgLocationsId", orgLocationsId).setParameterList("ids", ownerListOfSpoc).setCacheable(true).list();
		 }
		 else{
			 //removed by prasad salina --->user.id !=:ownerId and 
			 String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations=:orgLocationsId and userRole.userState != 'INACTIVE')  ";
		      userRoleList = session.createQuery(query).setLong( "orgLocationsId", orgLocationsId).setCacheable(true).list();
		 }
	     return userRoleList;
	     
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<User> getAllUsersByOrgLocationCode(Long orgLocationsId) {

		 Session session = this.sessionFactory.getCurrentSession();
	     String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations=:orgLocationsId and userRole.userState != 'INACTIVE')  ";
	     List<User> userRoleList = session.createQuery(query.toString()).setLong( "orgLocationsId", orgLocationsId).setCacheable(true).list();
	     return userRoleList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<User> getAllUsersByOrgLocationCodeExceptTeamOwner(Long teamOwnerUserId, Long orgLocationsId, Long tabId) {

		 Session session = this.sessionFactory.getCurrentSession();
		 /*if(teamOwnerUserId != null){
			 String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations=:orgLocationsId and userRole.userState != 'INACTIVE' ) and user.id <> :teamOwnerUserId  ";
		     List<User> userRoleList = session.createQuery(query.toString()).setLong( "teamOwnerUserId", teamOwnerUserId).setLong( "orgLocationsId", orgLocationsId).setCacheable(true).list();
		     return userRoleList;
		 }else{
			 String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations=:orgLocationsId and userRole.userState != 'INACTIVE' ) ";
		     List<User> userRoleList = session.createQuery(query.toString()).setLong( "orgLocationsId", orgLocationsId).setCacheable(true).list();
		     return userRoleList;
		 }*/
		 
		 String actionsQuery = "select sa.id from SourceleadActions sa where sa.tabs.id = :tabId  ";
	     List<Long> tabActionsIds = session.createQuery(actionsQuery.toString()).setLong( "tabId", tabId).setCacheable(true).list();
	     
	     String rolesQuery = "from Roles roles where roles.orgLocations.id = :orgLocationsId ";
	     List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setLong( "orgLocationsId", orgLocationsId).setCacheable(true).list();
	     List<Long> ownerRolesList = new ArrayList<Long>();
	     
	     for(Roles roles : rolesList){
	    	 if(null != roles.getActionId()){
	    		 List<Long> roleActionsList = new ArrayList<Long>();
	    		 for (String s : roles.getActionId().split(",")){
	        		 roleActionsList.add(Long.parseLong(s));
	        	 }
	        	 
	        	 roleActionsList.retainAll(tabActionsIds);
	        	 if(roleActionsList.size() > 0){
	        		 ownerRolesList.add(roles.getId());
	        	 }
	    	 }
	    	 
	     }
	     if(teamOwnerUserId != null){
	    	 String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList)) and user.id <> :teamOwnerUserId ";
	    	 List<User> userRoleList = session.createQuery(query.toString()).setParameterList("ownerRolesList", ownerRolesList).setLong( "teamOwnerUserId", teamOwnerUserId).setCacheable(true).list();
	    	 return userRoleList;
	     }else{
	    	 String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList))  ";
		     List<User> userRoleList = session.createQuery(query.toString()).setParameterList("ownerRolesList", ownerRolesList).setCacheable(true).list();
		     return userRoleList;
	     }
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<User> getUsersByTeamType(Long orgLocationsId, String teamType) throws Exception {
		 Session session = this.sessionFactory.getCurrentSession();
		 String subQuery = "";
		if("undefined".equalsIgnoreCase(teamType) || "All".equalsIgnoreCase(teamType)){
			 subQuery = "userRole.user.id from UserRole userRole where userRole.orgLocations=:orgLocationsId and userRole.userState != 'INACTIVE' ";
		 }
		 else if(teamType.contains("role")){
			 String[] roleIdStr = teamType.split("role");
			 Long roleId = Long.parseLong(roleIdStr[0]);
			 subQuery = "userRole.user.id from UserRole userRole where userRole.roles.id IN(select roles.id from Roles roles where roles.id = "+roleId+") and userRole.orgLocations=:orgLocationsId and userRole.userState != 'INACTIVE' ";
		 }
		 /*else if("HR Manager".equalsIgnoreCase(teamType)){
			 subQuery = "userRole.user.id from UserRole userRole where userRole.roles.id IN(select roles.id from Roles roles where roles.displayName = 'HR Manager') and userRole.orgLocations=:orgLocationsId and userRole.userState != 'INACTIVE' ";
		 }
		 else if("Recruiter".equalsIgnoreCase(teamType)){
			 subQuery = "userRole.user.id from UserRole userRole where userRole.roles.id IN(select roles.id from Roles roles where roles.displayName = 'Recruiter') and userRole.orgLocations=:orgLocationsId and userRole.userState != 'INACTIVE' ";
		 }
		 else if("Admin".equalsIgnoreCase(teamType)){
			 subQuery = "userRole.user.id from UserRole userRole where userRole.roles.id IN(select roles.id from Roles roles where roles.displayName = 'Admin') and userRole.orgLocations=:orgLocationsId and userRole.userState != 'INACTIVE' ";
		 }else if("Project Manager".equalsIgnoreCase(teamType)){
			 subQuery = "userRole.user.id from UserRole userRole where userRole.roles.id IN(select roles.id from Roles roles where roles.displayName = 'Project Manager') and userRole.orgLocations=:orgLocationsId and userRole.userState != 'INACTIVE' ";
		 }*/
		 
		 else{
			 Long teamId = Long.parseLong(teamType);
			 subQuery = "teamMembers.memberUserId.id from TeamMembers teamMembers where teamMembers.teamId.id ="+teamId+" and teamMembers.teamId.orgLocationsId.id=:orgLocationsId and teamMembers.memberUserId.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE')  and  teamMembers.status != 0 "; 
		 }
		String query = "from User user where user.id IN (select "+subQuery+" )  ";
	     List<User> userRoleList = session.createQuery(query).setLong( "orgLocationsId", orgLocationsId).setCacheable(true).list();
	     return userRoleList;
	}
	
	/**
	 * @author Venkat Kesav
	 * This method to fetch all users in the org by TeamType And TabId
	 * @param orgLocationsId
	 * @param teamType
	 * @param tabId
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<User> getUsersByTeamTypeAndTabId(Long orgLocationsId, String teamType, Long tabId) throws Exception {
		 Session session = this.sessionFactory.getCurrentSession();
		 String actionsQuery = "select sa.id from SourceleadActions sa where sa.tabs.id = :tabId  ";
	     List<Long> tabActionsIds = session.createQuery(actionsQuery.toString()).setLong( "tabId", tabId).setCacheable(true).list();
	     
	     String rolesQuery = "from Roles roles where roles.orgLocations.id = :orgLocationsId ";
	     List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setLong( "orgLocationsId", orgLocationsId).setCacheable(true).list();
	     List<Long> ownerRolesList = new ArrayList<Long>();
	     
	     for(Roles roles : rolesList){
	    	 if(null != roles.getActionId()){
	    		 List<Long> roleActionsList = new ArrayList<Long>();
	    		 for (String s : roles.getActionId().split(",")){
	        		 roleActionsList.add(Long.parseLong(s));
	        	 }
	        	 
	        	 roleActionsList.retainAll(tabActionsIds);
	        	 if(roleActionsList.size() > 0){
	        		 ownerRolesList.add(roles.getId());
	        	 }
	    	 }
	     }
	     
	     List<User> userRoleList = new ArrayList<User>();
	     List<String> teamTypeList = Arrays.asList(teamType.split(","));
	     for(String type : teamTypeList){
			String subQuery = "";
			if("".equalsIgnoreCase(type) || "null".equalsIgnoreCase(type) || "undefined".equalsIgnoreCase(type) || "All".equalsIgnoreCase(type)){
				 subQuery = "userRole.user.id from UserRole userRole where userRole.orgLocations=:orgLocationsId and userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList) ";
				 
				 String query = "from User user where user.id IN (select "+subQuery+" ) ";
			     List<User> userRoleListAll = session.createQuery(query).setParameterList("ownerRolesList", ownerRolesList).setLong( "orgLocationsId", orgLocationsId).setCacheable(true).list();
			     userRoleList.addAll(userRoleListAll);
			 }
			 else if(type.contains("role")){
				 String[] roleIdStr = type.split("role");
				 Long roleId = Long.parseLong(roleIdStr[0]);
				 subQuery = "userRole.user.id from UserRole userRole where userRole.roles.id IN(select roles.id from Roles roles where roles.id = "+roleId+") and userRole.orgLocations=:orgLocationsId and userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList) ";
				 
				 String query = "from User user where user.id IN (select "+subQuery+" ) ";
			     List<User> userRoleListRole = session.createQuery(query).setParameterList("ownerRolesList", ownerRolesList).setLong( "orgLocationsId", orgLocationsId).setCacheable(true).list();
			     userRoleList.addAll(userRoleListRole);
			 }
			 else{
				 Long teamId = Long.parseLong(type);
				 String teamName = (String) session.createQuery("select name from Team team where team.id =:teamId").setLong( "teamId", teamId).setCacheable(true).uniqueResult();
				 
				 subQuery = "teamMembers.memberUserId.id from TeamMembers teamMembers where teamMembers.teamId.id ="+teamId+" and teamMembers.teamId.orgLocationsId.id=:orgLocationsId and teamMembers.memberUserId.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList))  and  teamMembers.status != 0 ";
				 
				 String query = "from User user where user.id IN (select "+subQuery+" ) ";
			     List<User> userRoleListTeam = session.createQuery(query).setParameterList("ownerRolesList", ownerRolesList).setLong( "orgLocationsId", orgLocationsId).setCacheable(true).list();
			     for(User user : userRoleListTeam){
			    	 
			    	 User usr = prepareUserData(user);
			    	 usr.setAssignedTeamId(type);
			    	 usr.setAssignedTeam(" ("+teamName+")");
			    	 usr.setIsAssignedOwner("");
			    	 userRoleList.add(usr);
			    	 logger.info("### UserDaoImpl -- retriving user data of a team ###"+usr.getId());
			     }
			     //userRoleList.addAll(userRoleListTeam);
			     
			     String subQuery1 = "team.ownerUserId.id from Team team where team.id ="+teamId+" and team.orgLocationsId.id=:orgLocationsId and team.ownerUserId.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList))  and  team.status != 0 ";
				 String query1 = "from User user where user.id IN (select "+subQuery1+" ) ";
			     List<User> userRoleListTeamOwner = session.createQuery(query1).setParameterList("ownerRolesList", ownerRolesList).setLong( "orgLocationsId", orgLocationsId).setCacheable(true).list();
			     for(User user : userRoleListTeamOwner){
			    	 User usr = prepareUserData(user);
			    	 usr.setAssignedTeamId(type);
			    	 usr.setAssignedTeam(" ("+teamName+")");
			    	 usr.setIsAssignedOwner(" (Owner)");
			    	 userRoleList.add(usr);
			    	 logger.info("### UserDaoImpl -- retriving owner data of a team ###"+usr.getId());
			     }
			     //userRoleList.addAll(userRoleListTeamOwner);
			 }
	     }
		/*String query = "from User user where user.id IN (select "+subQuery+" ) ";
	     List<User> userRoleList = session.createQuery(query).setParameterList("ownerRolesList", ownerRolesList).setLong( "orgLocationsId", orgLocationsId).setCacheable(true).list();*/
		return userRoleList;
	}
	
	
	private User prepareUserData(User user) {
		 User usr = new User();
	   	 usr.setId(user.getId());
	   	 usr.setFirstName(user.getFirstName());
	   	 usr.setEmail(user.getEmail());
	   	 usr.setRole(user.getRole());
	   	 usr.setAssignJobCount(user.getAssignJobCount());
	   	 usr.setLastName(user.getLastName());
		return usr;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<User> getUserListExceptExistedUsersList(List<Long> userIds, Long orgLocationId, Long tabId)
			throws Exception {
		 Session session = this.sessionFactory.getCurrentSession();
		 
		 String actionsQuery = "select sa.id from SourceleadActions sa where sa.tabs.id = :tabId  ";
	     List<Long> tabActionsIds = session.createQuery(actionsQuery.toString()).setLong( "tabId", tabId).setCacheable(true).list();
	     
	     String rolesQuery = "from Roles roles where roles.orgLocations.id = :orgLocationId ";
	     List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setLong( "orgLocationId", orgLocationId).setCacheable(true).list();
	     List<Long> ownerRolesList = new ArrayList<Long>();
	     
	     for(Roles roles : rolesList){
	    	 if(null != roles.getActionId()){
	    		 List<Long> roleActionsList = new ArrayList<Long>();
	    		 for (String s : roles.getActionId().split(",")){
	        		 roleActionsList.add(Long.parseLong(s));
	        	 }
	        	 
	        	 roleActionsList.retainAll(tabActionsIds);
	        	 if(roleActionsList.size() > 0){
	        		 ownerRolesList.add(roles.getId());
	        	 }
	    	 }
	     }
		 
		 
		 
		 List<User> userRoleList;
		 if(userIds != null && !userIds.isEmpty()){
			 String query = "from User user where user.id NOT IN (:userIds) and user.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations = :orgLocationId and userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList)) )  "; 
			 userRoleList = session.createQuery(query).setParameterList("userIds", userIds).setParameterList("ownerRolesList", ownerRolesList).setLong( "orgLocationId", orgLocationId).setCacheable(true).list();
		 }else{
			 String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations = :orgLocationId and userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList)) )  ";
			 userRoleList = session.createQuery(query).setParameterList("ownerRolesList", ownerRolesList).setLong( "orgLocationId", orgLocationId).setCacheable(true).list();
		 }
		 return userRoleList;
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<User> getUsersByTeamTypeAndNoCurrentUser( Long currentApproverId,Long orgLocationsId) {
		
		  	 Session session = this.sessionFactory.getCurrentSession();
			 String actionsQuery = "select sa.id from SourceleadActions sa where sa.tabs.id = :tabId  ";
		     List<Long> tabActionsIds = session.createQuery(actionsQuery.toString()).setLong( "tabId", Long.valueOf(Constants.TAB_REQUIREMENT)).setCacheable(true).list();
		     
		     String rolesQuery = "from Roles roles where roles.orgLocations.id = :orgLocationsId ";
		     List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setLong( "orgLocationsId", orgLocationsId).setCacheable(true).list();
		     List<Long> ownerRolesList = new ArrayList<Long>();
		     
		     for(Roles roles : rolesList){
		    	 if(null != roles.getActionId()){
		    		 List<Long> roleActionsList = new ArrayList<Long>();
		    		 for (String s : roles.getActionId().split(",")){
		        		 roleActionsList.add(Long.parseLong(s));
		        	 }
		        	 
		        	 roleActionsList.retainAll(tabActionsIds);
		        	 if(roleActionsList.size() > 0){
		        		 ownerRolesList.add(roles.getId());
		        	 }
		    	 }
		    	 
		     }
			 
			 
		     String query = "from User user where user.id <> :currentApproverId  and user.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList))  ";
		     List<User> userRoleList = session.createQuery(query.toString()).setLong( "currentApproverId", currentApproverId).setParameterList("ownerRolesList", ownerRolesList).setCacheable(true).list();
		     return userRoleList;
		     
		
			/* Session session = this.sessionFactory.getCurrentSession();
			 String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where  userRole.orgLocations=:orgLocationsId and  userRole.userState != 'INACTIVE'  )  and   user.id <> :currentApproverId ";
	
			 List<User> userRoleList = session.createQuery(query.toString()).setLong( "currentApproverId", currentApproverId).setLong( "orgLocationsId", orgLocationsId).setCacheable(true).list();
			 return userRoleList;*/
    }

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<User> getUserListExceptExistedTeamMembersList(List<Long> teamMembersId, Long orgLocationId)
			throws Exception {

		 Session session = this.sessionFactory.getCurrentSession();

		 /* Commented by prasad salina
		 
		 String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE') and user.id <> :currentApproverId  ";
		 List<User> userRoleList = session.createQuery(query.toString()).setLong( "currentApproverId", currentApproverId).setCacheable(true).list();*/
		
		 String query = "from User user where user.id NOT IN (:teamMembersId) and   user.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and userRole.orgLocations.id=:orgLocationId )  ";
		 
		 /*and user.id IN (select userRole.user.id from UserRole userRole where  userRole.orgLocations=:orgLocationsId) */

		 List<User> userRoleList = session.createQuery(query.toString()).setParameterList("teamMembersId", teamMembersId).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		 return userRoleList;
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<User> getAllUsersByOrgLocationCodeExcludingEmployees(Long orgLocationsId) throws Exception {

		 Session session = this.sessionFactory.getCurrentSession();
	     String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations=:orgLocationsId and  userRole.userState != 'INACTIVE'  )  ";
	     List<User> userRoleList = session.createQuery(query.toString()).setLong( "orgLocationsId", orgLocationsId).setCacheable(true).list();
	     return userRoleList;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<User> getReoprtingManagersByOrgLocationId(Long orgLocationsId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String subQuery = "";
		
		subQuery = "userRole.user.id from UserRole userRole where  userRole.orgLocations = :orgLocationsId and userRole.userState != 'INACTIVE'";
		String query = "from User user where user.id IN (select "+subQuery+" )  ";
	    List<User> userRoleList = session.createQuery(query).setLong( "orgLocationsId", orgLocationsId).setCacheable(true).list();
        return userRoleList;
        
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<User> getTabAccessOwnersByOrgLocationIdAndTabId(Long orgLocationsId, Long tabId) throws Exception {
		
	  Session session = this.sessionFactory.getCurrentSession();
		
	 String actionsQuery = "select sa.id from SourceleadActions sa where sa.tabs.id = :tabId  ";
     List<Long> tabActionsIds = session.createQuery(actionsQuery.toString()).setLong( "tabId", tabId).setCacheable(true).list();
     
     String rolesQuery = "from Roles roles where roles.orgLocations.id = :orgLocationsId ";
     List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setLong( "orgLocationsId", orgLocationsId).setCacheable(true).list();
     List<Long> ownerRolesList = new ArrayList<Long>();
     
     for(Roles roles : rolesList){
    	 if(null != roles.getActionId()){
    		 List<Long> roleActionsList = new ArrayList<Long>();
    		 for (String s : roles.getActionId().split(",")){
        		 roleActionsList.add(Long.parseLong(s));
        	 }
        	 
        	 roleActionsList.retainAll(tabActionsIds);
        	 if(roleActionsList.size() > 0){
        		 ownerRolesList.add(roles.getId());
        	 }
    	 }
    	 
     }
	 
     String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList))  ";
     List<User> userRoleList = session.createQuery(query.toString()).setParameterList("ownerRolesList", ownerRolesList).setCacheable(true).list();
     return userRoleList;
        
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<User> getTabAccessOwnersByOrgLocationIdAndTabIdAndNoCurrentUser(Long currentApproverId, Long orgLocationsId,Long tabId) throws Exception {
		
	  Session session = this.sessionFactory.getCurrentSession();
	  String query = "";
	  Boolean isAdmin = userRoleDao.isUserAdmin(currentApproverId);
	  if(isAdmin){
		  query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList) and userRole.roles.roleName = 'ROLE_ADMIN' and userRole.user.id != :currentApproverId)";
	  }else{
		  query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList) and userRole.user.id != :currentApproverId)";
	  }
		
	  String actionsQuery = "select sa.id from SourceleadActions sa where sa.tabs.id = :tabId  ";
	     List<Long> tabActionsIds = session.createQuery(actionsQuery.toString()).setLong( "tabId", tabId).setCacheable(true).list();
	     
	     String rolesQuery = "from Roles roles where roles.orgLocations.id = :orgLocationsId ";
	     List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setLong( "orgLocationsId", orgLocationsId).setCacheable(true).list();
	     List<Long> ownerRolesList = new ArrayList<Long>();
	     
	     for(Roles roles : rolesList){
	    	 if(null != roles.getActionId()){
	    		 List<Long> roleActionsList = new ArrayList<Long>();
	    		 for (String s : roles.getActionId().split(",")){
	        		 roleActionsList.add(Long.parseLong(s));
	        	 }
	        	 
	        	 roleActionsList.retainAll(tabActionsIds);
	        	 if(roleActionsList.size() > 0){
	        		 ownerRolesList.add(roles.getId());
	        	 }
	    	 }
	    	 
	     }
     List<User> userRoleList = session.createQuery(query).setLong("currentApproverId", currentApproverId).setParameterList("ownerRolesList", ownerRolesList).setCacheable(true).list();
     return userRoleList;
        
	}

	@Override
	@Transactional
	public List<User> getUsersForAddAsConsultant(Org org,Long orgLocationsId) throws Exception {
		
		 String subQuery = "";
			
	        if("slead".equalsIgnoreCase(org.getOrgCode())){
				subQuery = "and userRole.roles.roleName != 'ROLE_SOURCELEAD_ADMIN' ";
			}

		 Session session = this.sessionFactory.getCurrentSession();
	     String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations=:orgLocationsId and  userRole.userState != 'INACTIVE'  "+subQuery+" "
	     		+ "and userRole.user.id NOT IN (select candidate.consultantUser.id from Candidate candidate where candidate.orgLocationId.id = :orgLocationsId2 and candidate.consultantUser.id IS NOT NULL))  ";
	     List<User> usersList = session.createQuery(query.toString()).setLong( "orgLocationsId", orgLocationsId).setLong( "orgLocationsId2", orgLocationsId).setCacheable(true).list();
	     return usersList;
	}

	@Override
	@Transactional
	public List<User> getUsersForConsultantsByUserRoleIdAndOrgLocationId(Long roleId, Long orgLocationsId) throws Exception {

		 Session session = this.sessionFactory.getCurrentSession();
	     String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations=:orgLocationsId and userRole.userState != 'INACTIVE' "
	     		+ "and userRole.roles.id = :roleId and userRole.user.id NOT IN (select candidate.consultantUser.id from Candidate candidate where candidate.orgLocationId.id = :orgLocationsId2 and candidate.consultantUser.id IS NOT NULL))  ";
	     List<User> usersList = session.createQuery(query.toString()).setLong( "orgLocationsId", orgLocationsId).setLong( "orgLocationsId2", orgLocationsId).setLong("roleId", roleId).setCacheable(true).list();
	     return usersList;
	}

	@Override
	@Transactional
	public List<User> getTabAccessUsersByOrgLocationIdAndTabIdAndUsersIds(Long orgLocationsId, List<Long> userIds,Long tabId) throws Exception {
		
		  Session session = this.sessionFactory.getCurrentSession();
			
		 String actionsQuery = "select sa.id from SourceleadActions sa where sa.tabs.id = :tabId  ";
	     List<Long> tabActionsIds = session.createQuery(actionsQuery.toString()).setLong( "tabId", tabId).setCacheable(true).list();
	     
	     String rolesQuery = "from Roles roles where roles.orgLocations.id = :orgLocationsId ";
	     List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setLong( "orgLocationsId", orgLocationsId).setCacheable(true).list();
	     List<Long> ownerRolesList = new ArrayList<Long>();
	     
	     for(Roles roles : rolesList){
	    	 if(null != roles.getActionId()){
	    		 List<Long> roleActionsList = new ArrayList<Long>();
	    		 for (String s : roles.getActionId().split(",")){
	        		 roleActionsList.add(Long.parseLong(s));
	        	 }
	        	 
	        	 roleActionsList.retainAll(tabActionsIds);
	        	 if(roleActionsList.size() > 0){
	        		 ownerRolesList.add(roles.getId());
	        	 }
	    	 }
	    	 
	     }
		 
		 
	     String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList) and userRole.user.id NOT IN (:userIds))  ";
	     List<User> userRoleList = session.createQuery(query.toString()).setParameterList("ownerRolesList", ownerRolesList).setParameterList("userIds", userIds).setCacheable(true).list();
	     return userRoleList;
	        
		}

	@Override
	@Transactional
	public List<User> getTabAccessUsersByOrgLocationIdAndTabIdAndNotInUsersId(Long orgLocationsId, Long userId,Long tabId) throws Exception {
		
		  Session session = this.sessionFactory.getCurrentSession();
			
		 String actionsQuery = "select sa.id from SourceleadActions sa where sa.tabs.id = :tabId  ";
	     List<Long> tabActionsIds = session.createQuery(actionsQuery.toString()).setLong( "tabId", tabId).setCacheable(true).list();
	     
	     String rolesQuery = "from Roles roles where roles.orgLocations.id = :orgLocationsId ";
	     List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setLong( "orgLocationsId", orgLocationsId).setCacheable(true).list();
	     List<Long> ownerRolesList = new ArrayList<Long>();
	     
	     for(Roles roles : rolesList){
	    	 if(null != roles.getActionId()){
	    		 List<Long> roleActionsList = new ArrayList<Long>();
	    		 for (String s : roles.getActionId().split(",")){
	        		 roleActionsList.add(Long.parseLong(s));
	        	 }
	        	 
	        	 roleActionsList.retainAll(tabActionsIds);
	        	 if(roleActionsList.size() > 0){
	        		 ownerRolesList.add(roles.getId());
	        	 }
	    	 }
	    	 
	     }
		 
		 
	     String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList) and userRole.user.id <> :userId)  ";
	     List<User> userRoleList = session.createQuery(query.toString()).setParameterList("ownerRolesList", ownerRolesList).setLong("userId", userId).setCacheable(true).list();
	     return userRoleList;
	        
		}

	@Override
	@Transactional
	public List<User> getUsersListByOrglocationId(Long orgLocationId) throws Exception {

		 Session session = this.sessionFactory.getCurrentSession();
	     String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations=:orgLocationId) ORDER BY user.id DESC";
	     List<User> usersList = session.createQuery(query.toString()).setLong( "orgLocationId", orgLocationId).setCacheable(true).list();
	     return usersList;
	}

	@Override
	@Transactional
	public User getUserByEmailAndUserType(String email, String userType) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<User> users = new ArrayList<User>();
		logger.debug("getUserByEmailAndUserType  start");
		users = session.createQuery("from User user where user.email = :email and user.userType = :userType" ).setCacheable(true).setString( "email", email).setString( "userType", userType).setCacheable(true).list();
		logger.debug("getUserByEmailAndUserType  end");
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
}

	@Override
	@Transactional
	public User getUserByUserName(String userName) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<User> users = new ArrayList<User>();
		logger.debug("getUserByUserName  start");
		users = session.createQuery("from User user where user.userName = :userName" ).setCacheable(true).setString( "userName", userName).setCacheable(true).list();
		logger.debug("getUserByUserName  end");
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
}

	@Override
	@Transactional
	public List<User> getTabAccessOwnersByOrgLocationIdAndTabIdAndNotOwners(Long orgLocationsId, Long tabId,
			List<Long> ownersList) {
		Session session = this.sessionFactory.getCurrentSession();
		
		List<User> userRoleList = null;
		
		 String actionsQuery = "select sa.id from SourceleadActions sa where sa.tabs.id = :tabId  ";
	     List<Long> tabActionsIds = session.createQuery(actionsQuery.toString()).setLong( "tabId", tabId).setCacheable(true).list();
	     
	     String rolesQuery = "from Roles roles where roles.orgLocations.id = :orgLocationsId ";
	     List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setLong( "orgLocationsId", orgLocationsId).setCacheable(true).list();
	     List<Long> ownerRolesList = new ArrayList<Long>();
	     
	     for(Roles roles : rolesList){
	    	 if(null != roles.getActionId()){
	    		 List<Long> roleActionsList = new ArrayList<Long>();
	    		 for (String s : roles.getActionId().split(",")){
	        		 roleActionsList.add(Long.parseLong(s));
	        	 }
	        	 
	        	 roleActionsList.retainAll(tabActionsIds);
	        	 if(roleActionsList.size() > 0){
	        		 ownerRolesList.add(roles.getId());
	        	 }
	    	 }
	    	 
	     }
		 
	     if(null != ownersList && !ownersList.isEmpty() && null != ownerRolesList && !ownerRolesList.isEmpty()){
	    	 String query = "from User user where user.id NOT IN (:ownersList) and user.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList))  ";
		     userRoleList = session.createQuery(query.toString()).setParameterList("ownersList", ownersList).setParameterList("ownerRolesList", ownerRolesList).setCacheable(true).list();
	     }else if(null != ownerRolesList && !ownerRolesList.isEmpty()){
	    	 String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList))  ";
		     userRoleList = session.createQuery(query.toString()).setParameterList("ownerRolesList", ownerRolesList).setCacheable(true).list();
	     }
	     return userRoleList;
	}
	
	
    @Override
	public List<User> getAllUsersWithRequirementTabAccessAndMemeberInRequirementMemberOrgLocationIdAndTabId(
			Long orgLocationsId, Long tabId) throws Exception {
	
		  Session session = this.sessionFactory.getCurrentSession();

    	 String actionsQuery = "select sa.id from SourceleadActions sa where sa.tabs.id = :tabId  ";
         List<Long> tabActionsIds = session.createQuery(actionsQuery.toString()).setLong( "tabId", tabId).setCacheable(true).list();
         
         String rolesQuery = "from Roles roles where roles.orgLocations.id = :orgLocationsId ";
         List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setLong( "orgLocationsId", orgLocationsId).setCacheable(true).list();
         List<Long> ownerRolesList = new ArrayList<Long>();
         
         for(Roles roles : rolesList){
        	 if(null != roles.getActionId()){
        		 List<Long> roleActionsList = new ArrayList<Long>();
        		 for (String s : roles.getActionId().split(",")){
            		 roleActionsList.add(Long.parseLong(s));
            	 }
            	 
            	 roleActionsList.retainAll(tabActionsIds);
            	 if(roleActionsList.size() > 0){
            		 ownerRolesList.add(roles.getId());
            	 }
        	 }
        	 
         }
    	
         String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList))  ";
         List<User> userRoleList = session.createQuery(query.toString()).setParameterList("ownerRolesList", ownerRolesList).setCacheable(true).list();
         return userRoleList;
         
	}

	@Override
	public List<User> getUsersListAssignedToReoprtingManagersByOrgLocationId(Long orgLocationsId, User user, Long tabId)
			throws Exception {
		

		  Session session = this.sessionFactory.getCurrentSession();

      	 String actionsQuery = "select sa.id from SourceleadActions sa where sa.tabs.id = :tabId  ";
         List<Long> tabActionsIds = session.createQuery(actionsQuery.toString()).setLong( "tabId", tabId).setCacheable(true).list();
       
         String rolesQuery = "from Roles roles where roles.orgLocations.id = :orgLocationsId ";
         List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setLong( "orgLocationsId", orgLocationsId).setCacheable(true).list();
         List<Long> ownerRolesList = new ArrayList<Long>();
       
	       for(Roles roles : rolesList){
	      	 if(null != roles.getActionId()){
	      		 List<Long> roleActionsList = new ArrayList<Long>();
	      		 for (String s : roles.getActionId().split(",")){
	          		 roleActionsList.add(Long.parseLong(s));
	          	 }
	          	 roleActionsList.retainAll(tabActionsIds);
	          	 if(roleActionsList.size() > 0){
	          		 ownerRolesList.add(roles.getId());
	          	 }
	      	 }
	       }
	       String query = "from User user where user.reportingManager.id =:userId  and  user.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList))   ";
	       List<User> userRoleList = session.createQuery(query.toString()).setParameterList("ownerRolesList", ownerRolesList).setLong( "userId", user.getId()).setLong( "orgLocationsId", orgLocationsId).setCacheable(true).list();
	       return userRoleList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public LinkedHashSet<User> getUsersListAsReoprtingManagersByOrgLocationIdHavingTabAccess(Long orgLocationsId,User user, Long tabId)
			throws Exception {
		
		
		Session session = this.sessionFactory.getCurrentSession();

     	 String actionsQuery = "select sa.id from SourceleadActions sa where sa.tabs.id = :tabId  ";
        List<Long> tabActionsIds = session.createQuery(actionsQuery.toString()).setLong( "tabId", tabId).setCacheable(true).list();
      
        String rolesQuery = "from Roles roles where roles.orgLocations.id = :orgLocationsId ";
        List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setLong( "orgLocationsId", orgLocationsId).setCacheable(true).list();
        List<Long> ownerRolesList = new ArrayList<Long>();
        LinkedHashSet<User> userRoleList = new LinkedHashSet<User>();
	       for(Roles roles : rolesList){
	      	 if(null != roles.getActionId()){
	      		 List<Long> roleActionsList = new ArrayList<Long>();
	      		 for (String s : roles.getActionId().split(",")){
	          		 roleActionsList.add(Long.parseLong(s));
	          	 }
	          	 roleActionsList.retainAll(tabActionsIds);
	          	 if(roleActionsList.size() > 0){
	          		 ownerRolesList.add(roles.getId());
	          	 }
	      	 }
	       }
	       
	       String query = "from User user where  user.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList) and userRole.reportingManager.id =:managerUserId  and userRole.orgLocations.id =:orgLocationsId)   ";
	       List<User> userList = session.createQuery(query.toString()).setParameterList("ownerRolesList", ownerRolesList).setLong( "orgLocationsId", orgLocationsId).setLong( "managerUserId", user.getId()).setCacheable(true).list();
	       userRoleList.addAll(userList);
	       
	       return userRoleList;
	}

	@Override
	@Transactional
	public List<User> getUsersByOrgLocationIdExceptLoggedInUser(Long orgLocationsId, Long userId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();

	    String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations=:orgLocationsId and userRole.userState = 'ACTIVE') and user.id <> :userId and"
	    		+ " user.id NOT IN (select offboarding.userId from CandidateOffBoarding offboarding where offboarding.orgLocationId = :orgLocationsId) ";

	    List<User> userRoleList = session.createQuery(query.toString()).setLong( "orgLocationsId", orgLocationsId).setLong("userId", userId).setCacheable(true).list();
        return userRoleList;
	}
}