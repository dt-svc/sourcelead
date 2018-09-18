package com.bis.sourcelead.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.dto.ConnectionsDTO;
import com.bis.sourcelead.dto.DashboardItemsDTO;
import com.bis.sourcelead.dto.RecruiterRrportDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.entity.DashboardItems;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RecruiterReportAssociateCountView;
import com.bis.sourcelead.entity.RecruiterReportCandidateCountView;
import com.bis.sourcelead.entity.RecruiterReportView;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.reportsDTO.LeaveBalanceReportDTO;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private UserRoleDao userRoleDao;
	
	@Autowired
    OrgLocationService orgLocationService;
	
	@Autowired
	RequirementService requirementService;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	RequirementSubmissionHistoryService requirementSubmissionHistoryService;
	
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
		users = session.createQuery("from User user left join fetch user.linkUserId where user.userName = :username" ).setString( "username", username ).list();
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
		users = session.createQuery("from User where email=:email" ).setString( "email", email ).list();
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
		users = session.createQuery("from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations.id =:orgLocationsId) and user.email=:email" ).setString("email", email ).setLong("orgLocationsId", orgLocationsId).list();
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
				if(user.getLastName() == null){
					user.setLastName("");
					session.saveOrUpdate(user);
				}else{
					session.saveOrUpdate(user);
				}
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
		Collection<User> orgList = crit.list();
		return orgList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public User getUserById(Long Id) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<User> users = new ArrayList<User>();
		logger.debug("getUserById start");
		users = session.createQuery("from User user left join fetch user.linkUserId where user.id=:id" ).setLong( "id", Id ).list();
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
		users = session.createQuery(query).setLong( "id", Id ).setLong("orgLocationsId", orgLocationsId).list();
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
	    List<User> userRoleList = session.createQuery(query.toString()).setLong("orgLocations", orgLocations).list();
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
		
	    List<User> userRoleList = session.createQuery(query.toString()).setLong( "orgLocationsId", orgLocationsId).setLong( "userId", userId).list();
      
	    return userRoleList;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<User> getUsersByUserRolesAndOrgLocationId(Long orgLocationsId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();

	    String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations=:orgLocationsId and userRole.userState != 'INACTIVE' )  ";

	    List<User> userRoleList = session.createQuery(query.toString()).setLong( "orgLocationsId", orgLocationsId).list();
        return userRoleList;
        
	}

	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<User> getUsersRoleCountByRoleIdAndLocationIdAndNotLoggedInUser(Long roleId, Long orgLocationsId,Long userId) throws Exception {
		
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "select user.id from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.roles.id = :roleId and userRole.orgLocations.id = :orgLocationsId and userRole.user.id <> :userId)";
		List<User> usersList = session.createQuery(query.toString()).setLong("roleId", roleId).setLong( "orgLocationsId", orgLocationsId).setLong( "userId", userId).list();
        return usersList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Employees> getAllUserOrgCodeAndLocationCodeTimeSheetNotification(Long userId) throws Exception{
		
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from Employees emp left join fetch emp.orgLocations orgLocation left join fetch orgLocation.org org where emp.user.id IN(select id from User user where user.id =:userId)"; 
		List<Employees> empList = session.createQuery(query.toString()).setLong("userId", userId).list();
		return empList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public User getUserByUserCode(String userCode) throws Exception {
			Session session = this.sessionFactory.getCurrentSession();
			List<User> users = new ArrayList<User>();
			logger.debug("getUserBy user code start");
			users = session.createQuery("from User user where user.userCode =:userCode" ).setString( "userCode", userCode ).list();
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
     List<User> userRoleList = session.createQuery(query.toString()).setLong( "orgLocationsId", orgLocationsId).list();
     return userRoleList;
        
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<User> getManagersByUserRolesAndOrgLocationId(Long roleId , Long orgLocationsId,Long tabId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String subQuery = "";
		
		
		 String actionsQuery = "select sa.id from SourceleadActions sa where sa.tabs.id = :tabId  ";
	     List<Long> tabActionsIds = session.createQuery(actionsQuery.toString()).setLong( "tabId", tabId).list();
	     
	     String rolesQuery = "from Roles roles where roles.orgLocations.id = :orgLocationsId ";
	     List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setLong( "orgLocationsId", orgLocationsId).list();
	     List<Long> ownerRolesList = new ArrayList<Long>();
	     
	     for(Roles roles : rolesList){
	    	 if(null != roles.getActionId()){
	    		 List<Long> roleActionsList = new ArrayList<Long>();
		    	 /*for (String s : roles.getActionId().split(",")){
		    		 roleActionsList.add(Long.parseLong(s));
		    	 }*/
		    	 for (String s : Arrays.stream(roles.getActionId().split(",")).filter(value -> value != null && value.length() > 0).toArray(size -> new String[size])){
	    			 roleActionsList.add(Long.parseLong(s));
	        	 }
		    	 
		    	 roleActionsList.retainAll(tabActionsIds);
		    	 if(roleActionsList.size() > 0){
		    		 ownerRolesList.add(roles.getId());
		    	 }
	    	 }
	     }
	     
	     
		
		subQuery = "userRole.user.id from UserRole userRole where  (userRole.roles.roleHierarchy.hierarchyLevel <= (select r.roleHierarchy.hierarchyLevel from Roles r where r.id = :roleId)) and (userRole.roles.actionId IS NOT NULL) and userRole.orgLocations = :orgLocationsId and userRole.userState != 'INACTIVE' and (userRole.isGlobal = 'N' OR userRole.isGlobal = NULL) and userRole.roles.id IN (:ownerRolesList)";
		
		String query = "from User user where user.id IN (select "+subQuery+" )  ";
	    List<User> userRoleList = session.createQuery(query).setLong("roleId", roleId).setParameterList("ownerRolesList", ownerRolesList).setLong( "orgLocationsId", orgLocationsId).list();
        return userRoleList;
        
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<User> getManagersByUserIdAndUserRolesAndOrgLocationIdAndUserTabId(Long userId, Long roleId, Long orgLocationsId, Long tabId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String subQuery = "";
		
		
		 String actionsQuery = "select sa.id from SourceleadActions sa where sa.tabs.id = :tabId  ";
	     List<Long> tabActionsIds = session.createQuery(actionsQuery.toString()).setLong( "tabId", tabId).list();
	     
	     String rolesQuery = "from Roles roles where roles.orgLocations.id = :orgLocationsId ";
	     List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setLong( "orgLocationsId", orgLocationsId).list();
	     List<Long> ownerRolesList = new ArrayList<Long>();
	     
	     for(Roles roles : rolesList){
	    	 if(null != roles.getActionId()){
	    		 List<Long> roleActionsList = new ArrayList<Long>();
		    	 /*for (String s : roles.getActionId().split(",")){
		    		 roleActionsList.add(Long.parseLong(s));
		    	 }*/
		    	 for (String s : Arrays.stream(roles.getActionId().split(",")).filter(value -> value != null && value.length() > 0).toArray(size -> new String[size])){
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
	    List<User> userRoleList = session.createQuery(query).setLong("roleId", roleId).setLong( "userId", userId).setParameterList("ownerRolesList", ownerRolesList).setLong( "orgLocationsId", orgLocationsId).list();
        return userRoleList;
        
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<User> getManagersByUserIdAndUserRolesAndOrgLocationId(Long userId,Long roleId , Long orgLocationsId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String subQuery = "";
		/*String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations=:orgLocationsId and userRole.userState != 'INACTIVE' and userRole.user.id <> :userId)  ";
	    List<User> userRoleList = session.createQuery(query).setLong( "orgLocationsId", orgLocationsId).setLong( "userId", userId).list();
        return userRoleList;*/
        
        subQuery = "userRole.user.id from UserRole userRole where  (userRole.roles.roleHierarchy.hierarchyLevel <= (select r.roleHierarchy.hierarchyLevel from Roles r where r.id = :roleId)) and (userRole.roles.actionId IS NOT NULL) and userRole.orgLocations = :orgLocationsId and userRole.userState != 'INACTIVE' and userRole.user.id <> :userId ";
		
		String query = "from User user where user.id IN (select "+subQuery+" )  ";
	    List<User> userRoleList = session.createQuery(query).setLong("roleId", roleId).setLong( "userId", userId).setLong( "orgLocationsId", orgLocationsId).list();
        return userRoleList;
        
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public User getUserDetailsByUserCode(String userCode) {
		Session session = this.sessionFactory.getCurrentSession();
		List<User> users = session.createQuery("from User where userCode=:userCode" ).setString( "userCode", userCode ).list();
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
		      userRoleList = session.createQuery(query).setLong( "orgLocationsId", orgLocationsId).setParameterList("ids", ownerListOfSpoc).list();
		 }
		 else{
			 //removed by prasad salina --->user.id !=:ownerId and 
			 String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations=:orgLocationsId and userRole.userState != 'INACTIVE')  ";
		      userRoleList = session.createQuery(query).setLong( "orgLocationsId", orgLocationsId).list();
		 }
	     return userRoleList;
	     
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<User> getAllUsersByOrgLocationCode(Long orgLocationsId) {

		 Session session = this.sessionFactory.getCurrentSession();
	     String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations=:orgLocationsId and userRole.userState != 'INACTIVE')  ";
	     List<User> userRoleList = session.createQuery(query.toString()).setLong( "orgLocationsId", orgLocationsId).list();
	     return userRoleList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<User> getAllUsersByOrgLocationCodeExceptTeamOwner(Long teamOwnerUserId, Long orgLocationsId, Long tabId) {

		 Session session = this.sessionFactory.getCurrentSession();
		 /*if(teamOwnerUserId != null){
			 String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations=:orgLocationsId and userRole.userState != 'INACTIVE' ) and user.id <> :teamOwnerUserId  ";
		     List<User> userRoleList = session.createQuery(query.toString()).setLong( "teamOwnerUserId", teamOwnerUserId).setLong( "orgLocationsId", orgLocationsId).list();
		     return userRoleList;
		 }else{
			 String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations=:orgLocationsId and userRole.userState != 'INACTIVE' ) ";
		     List<User> userRoleList = session.createQuery(query.toString()).setLong( "orgLocationsId", orgLocationsId).list();
		     return userRoleList;
		 }*/
		 
		 String actionsQuery = "select sa.id from SourceleadActions sa where sa.tabs.id = :tabId  ";
	     List<Long> tabActionsIds = session.createQuery(actionsQuery.toString()).setLong( "tabId", tabId).list();
	     
	     String rolesQuery = "from Roles roles where roles.orgLocations.id = :orgLocationsId ";
	     List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setLong( "orgLocationsId", orgLocationsId).list();
	     List<Long> ownerRolesList = new ArrayList<Long>();
	     
	     for(Roles roles : rolesList){
	    	 if(null != roles.getActionId()){
	    		 List<Long> roleActionsList = new ArrayList<Long>();
	    		 /*for (String s : roles.getActionId().split(",")){
	        		 roleActionsList.add(Long.parseLong(s));
	        	 }*/
	    		 for (String s : Arrays.stream(roles.getActionId().split(",")).filter(value -> value != null && value.length() > 0).toArray(size -> new String[size])){
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
	    	 List<User> userRoleList = session.createQuery(query.toString()).setParameterList("ownerRolesList", ownerRolesList).setLong( "teamOwnerUserId", teamOwnerUserId).list();
	    	 return userRoleList;
	     }else{
	    	 String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList))  ";
		     List<User> userRoleList = session.createQuery(query.toString()).setParameterList("ownerRolesList", ownerRolesList).list();
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
	     List<User> userRoleList = session.createQuery(query).setLong( "orgLocationsId", orgLocationsId).list();
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
	     List<Long> tabActionsIds = session.createQuery(actionsQuery.toString()).setLong( "tabId", tabId).list();
	     
	     String rolesQuery = "from Roles roles where roles.orgLocations.id = :orgLocationsId ";
	     List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setLong( "orgLocationsId", orgLocationsId).list();
	     List<Long> ownerRolesList = new ArrayList<Long>();
	     
	     for(Roles roles : rolesList){
	    	 if(null != roles.getActionId()){
	    		 List<Long> roleActionsList = new ArrayList<Long>();
	    		/* for (String s : roles.getActionId().split(",")){
	        		 roleActionsList.add(Long.parseLong(s));
	        	 }*/
	    		 for (String s : Arrays.stream(roles.getActionId().split(",")).filter(value -> value != null && value.length() > 0).toArray(size -> new String[size])){
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
			     List<User> userRoleListAll = session.createQuery(query).setParameterList("ownerRolesList", ownerRolesList).setLong( "orgLocationsId", orgLocationsId).list();
			     userRoleList.addAll(userRoleListAll);
			 }
			 else if(type.contains("role")){
				 String[] roleIdStr = type.split("role");
				 Long roleId = Long.parseLong(roleIdStr[0]);
				 subQuery = "userRole.user.id from UserRole userRole where userRole.roles.id IN(select roles.id from Roles roles where roles.id = "+roleId+") and userRole.orgLocations=:orgLocationsId and userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList) ";
				 
				 String query = "from User user where user.id IN (select "+subQuery+" ) ";
			     List<User> userRoleListRole = session.createQuery(query).setParameterList("ownerRolesList", ownerRolesList).setLong( "orgLocationsId", orgLocationsId).list();
			     userRoleList.addAll(userRoleListRole);
			 }
			 else{
				 Long teamId = Long.parseLong(type);
				 String teamName = (String) session.createQuery("select name from Team team where team.id =:teamId").setLong( "teamId", teamId).uniqueResult();
				 
				 subQuery = "teamMembers.memberUserId.id from TeamMembers teamMembers where teamMembers.teamId.id ="+teamId+" and teamMembers.teamId.orgLocationsId.id=:orgLocationsId and teamMembers.memberUserId.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList))  and  teamMembers.status != 0 ";
				 
				 String query = "from User user where user.id IN (select "+subQuery+" ) ";
			     List<User> userRoleListTeam = session.createQuery(query).setParameterList("ownerRolesList", ownerRolesList).setLong( "orgLocationsId", orgLocationsId).list();
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
			     List<User> userRoleListTeamOwner = session.createQuery(query1).setParameterList("ownerRolesList", ownerRolesList).setLong( "orgLocationsId", orgLocationsId).list();
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
	     List<User> userRoleList = session.createQuery(query).setParameterList("ownerRolesList", ownerRolesList).setLong( "orgLocationsId", orgLocationsId).list();*/
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
	     List<Long> tabActionsIds = session.createQuery(actionsQuery.toString()).setLong( "tabId", tabId).list();
	     
	     String rolesQuery = "from Roles roles where roles.orgLocations.id = :orgLocationId ";
	     List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setLong( "orgLocationId", orgLocationId).list();
	     List<Long> ownerRolesList = new ArrayList<Long>();
	     
	     for(Roles roles : rolesList){
	    	 if(null != roles.getActionId()){
	    		 List<Long> roleActionsList = new ArrayList<Long>();
	    		 /*for (String s : roles.getActionId().split(",")){
	        		 roleActionsList.add(Long.parseLong(s));
	        	 }*/
	    		 for (String s : Arrays.stream(roles.getActionId().split(",")).filter(value -> value != null && value.length() > 0).toArray(size -> new String[size])){
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
			 userRoleList = session.createQuery(query).setParameterList("userIds", userIds).setParameterList("ownerRolesList", ownerRolesList).setLong( "orgLocationId", orgLocationId).list();
		 }else{
			 String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations = :orgLocationId and userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList)) )  ";
			 userRoleList = session.createQuery(query).setParameterList("ownerRolesList", ownerRolesList).setLong( "orgLocationId", orgLocationId).list();
		 }
		 return userRoleList;
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<User> getUsersByTeamTypeAndNoCurrentUser( Long currentApproverId,Long orgLocationsId) {
		
		  	 Session session = this.sessionFactory.getCurrentSession();
			 String actionsQuery = "select sa.id from SourceleadActions sa where sa.tabs.id = :tabId  ";
		     List<Long> tabActionsIds = session.createQuery(actionsQuery.toString()).setLong( "tabId", Long.valueOf(Constants.TAB_REQUIREMENT)).list();
		     
		     String rolesQuery = "from Roles roles where roles.orgLocations.id = :orgLocationsId ";
		     List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setLong( "orgLocationsId", orgLocationsId).list();
		     List<Long> ownerRolesList = new ArrayList<Long>();
		     
		     for(Roles roles : rolesList){
		    	 if(null != roles.getActionId()){
		    		 List<Long> roleActionsList = new ArrayList<Long>();
		    		 /*for (String s : roles.getActionId().split(",")){
		        		 roleActionsList.add(Long.parseLong(s));
		        	 }*/
		    		 for (String s : Arrays.stream(roles.getActionId().split(",")).filter(value -> value != null && value.length() > 0).toArray(size -> new String[size])){
		    			 roleActionsList.add(Long.parseLong(s));
		        	 }
		        	 
		        	 roleActionsList.retainAll(tabActionsIds);
		        	 if(roleActionsList.size() > 0){
		        		 ownerRolesList.add(roles.getId());
		        	 }
		    	 }
		    	 
		     }
			 
			 
		     String query = "from User user where user.id <> :currentApproverId  and user.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList))  ";
		     List<User> userRoleList = session.createQuery(query.toString()).setLong( "currentApproverId", currentApproverId).setParameterList("ownerRolesList", ownerRolesList).list();
		     return userRoleList;
		     
		
			/* Session session = this.sessionFactory.getCurrentSession();
			 String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where  userRole.orgLocations=:orgLocationsId and  userRole.userState != 'INACTIVE'  )  and   user.id <> :currentApproverId ";
	
			 List<User> userRoleList = session.createQuery(query.toString()).setLong( "currentApproverId", currentApproverId).setLong( "orgLocationsId", orgLocationsId).list();
			 return userRoleList;*/
    }

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<User> getUserListExceptExistedTeamMembersList(List<Long> teamMembersId, Long orgLocationId)
			throws Exception {

		 Session session = this.sessionFactory.getCurrentSession();

		 /* Commented by prasad salina
		 
		 String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE') and user.id <> :currentApproverId  ";
		 List<User> userRoleList = session.createQuery(query.toString()).setLong( "currentApproverId", currentApproverId).list();*/
		
		 String query = "from User user where user.id NOT IN (:teamMembersId) and   user.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and userRole.orgLocations.id=:orgLocationId )  ";
		 
		 /*and user.id IN (select userRole.user.id from UserRole userRole where  userRole.orgLocations=:orgLocationsId) */

		 List<User> userRoleList = session.createQuery(query.toString()).setParameterList("teamMembersId", teamMembersId).setLong("orgLocationId", orgLocationId).list();
		 return userRoleList;
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<User> getAllUsersByOrgLocationCodeExcludingEmployees(Long orgLocationsId) throws Exception {

		 Session session = this.sessionFactory.getCurrentSession();
	     String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations=:orgLocationsId and  userRole.userState != 'INACTIVE'  )  ";
	     List<User> userRoleList = session.createQuery(query.toString()).setLong( "orgLocationsId", orgLocationsId).list();
	     return userRoleList;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<User> getReoprtingManagersByOrgLocationId(Long orgLocationsId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String subQuery = "";
		
		subQuery = "userRole.user.id from UserRole userRole where  userRole.orgLocations = :orgLocationsId and userRole.userState != 'INACTIVE'";
		String query = "from User user where user.id IN (select "+subQuery+" )  ";
	    List<User> userRoleList = session.createQuery(query).setLong( "orgLocationsId", orgLocationsId).list();
        return userRoleList;
        
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<User> getTabAccessOwnersByOrgLocationIdAndTabId(Long orgLocationsId, Long tabId) throws Exception {
		
	  Session session = this.sessionFactory.getCurrentSession();
		
	 String actionsQuery = "select sa.id from SourceleadActions sa where sa.tabs.id = :tabId  ";
     List<Long> tabActionsIds = session.createQuery(actionsQuery.toString()).setLong( "tabId", tabId).list();
     
     String rolesQuery = "from Roles roles where roles.orgLocations.id = :orgLocationsId ";
     List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setLong( "orgLocationsId", orgLocationsId).list();
     List<Long> ownerRolesList = new ArrayList<Long>();
     
     for(Roles roles : rolesList){
    	 if(null != roles.getActionId()){
    		 List<Long> roleActionsList = new ArrayList<Long>();
    		 /*for (String s : roles.getActionId().split(",")){
        		 roleActionsList.add(Long.parseLong(s));
        	 }*/
    		 for (String s : Arrays.stream(roles.getActionId().split(",")).filter(value -> value != null && value.length() > 0).toArray(size -> new String[size])){
    			 roleActionsList.add(Long.parseLong(s));
        	 }
        	 
        	 roleActionsList.retainAll(tabActionsIds);
        	 if(roleActionsList.size() > 0){
        		 ownerRolesList.add(roles.getId());
        	 }
    	 }
    	 
     }
	 
     String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and (userRole.isGlobal = 'N' OR userRole.isGlobal = NULL) and userRole.roles.id IN (:ownerRolesList))  ";
     List<User> userRoleList = session.createQuery(query.toString()).setParameterList("ownerRolesList", ownerRolesList).list();
     return userRoleList;
        
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public User getTabAccessOwnersByOrgLocationIdEmailAndTabId(Long orgLocationsId, String email, Long tabId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		String actionsQuery = "select sa.id from SourceleadActions sa where sa.tabs.id = :tabId  ";
		List<Long> tabActionsIds = session.createQuery(actionsQuery.toString()).setLong("tabId", tabId).list();
		String rolesQuery = "from Roles roles where roles.orgLocations.id = :orgLocationsId ";
		List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setLong("orgLocationsId", orgLocationsId).list();
		List<Long> ownerRolesList = new ArrayList<Long>();
		for (Roles roles : rolesList) {
			if (null != roles.getActionId()) {
				List<Long> roleActionsList = new ArrayList<Long>();
				/*for (String s : roles.getActionId().split(",")) {
					roleActionsList.add(Long.parseLong(s));
				}*/
				for (String s : Arrays.stream(roles.getActionId().split(",")).filter(value -> value != null && value.length() > 0).toArray(size -> new String[size])){
	    			 roleActionsList.add(Long.parseLong(s));
	        	 }
				roleActionsList.retainAll(tabActionsIds);
				if (roleActionsList.size() > 0) {
					ownerRolesList.add(roles.getId());
				}
			}
		}
		String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList)) and user.email = :email  ";
		List<User> userRoleList = session.createQuery(query.toString()).setParameterList("ownerRolesList", ownerRolesList).setString("email", email).list();
		if (userRoleList.size() > 0) {
			return userRoleList.get(0);
		}
		else {
			return null;
		}
	}
		
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<User> getTabAccessOwnersByOrgLocationIdAndTabIdAndNoCurrentUser(Long currentApproverId, Long orgLocationsId,Long tabId) throws Exception {
		
	  Session session = this.sessionFactory.getCurrentSession();
	  String query = "";
	  Boolean isAdmin = userRoleDao.isUserAdminByOrgLocationId(currentApproverId,orgLocationsId);
	  if(isAdmin){
		  query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and userRole.orgLocations.id = :orgLocationsId and (userRole.isGlobal = 'N' OR userRole.isGlobal = NULL) and userRole.roles.id IN (:ownerRolesList) and userRole.roles.roleName = 'ROLE_ADMIN' and userRole.userState = 'ACTIVE' and userRole.user.id != :currentApproverId)";
	  }else{
		  query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and userRole.orgLocations.id = :orgLocationsId and (userRole.isGlobal = 'N' OR userRole.isGlobal = NULL) and userRole.roles.id IN (:ownerRolesList) and userRole.user.id != :currentApproverId)";
	  }
		
	  String actionsQuery = "select sa.id from SourceleadActions sa where sa.tabs.id = :tabId  ";
	     List<Long> tabActionsIds = session.createQuery(actionsQuery.toString()).setLong( "tabId", tabId).list();
	     
	     String rolesQuery = "from Roles roles where roles.orgLocations.id = :orgLocationsId ";
	     List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setLong( "orgLocationsId", orgLocationsId).list();
	     List<Long> ownerRolesList = new ArrayList<Long>();
	     
	     for(Roles roles : rolesList){
	    	 if(null != roles.getActionId()){
	    		 List<Long> roleActionsList = new ArrayList<Long>();
	    		 /*for (String s : roles.getActionId().split(",")){
	        		 roleActionsList.add(Long.parseLong(s));
	        	 }*/
	    		 for (String s : Arrays.stream(roles.getActionId().split(",")).filter(value -> value != null && value.length() > 0).toArray(size -> new String[size])){
	    			 roleActionsList.add(Long.parseLong(s));
	        	 }
	        	 
	        	 roleActionsList.retainAll(tabActionsIds);
	        	 if(roleActionsList.size() > 0){
	        		 ownerRolesList.add(roles.getId());
	        	 }
	    	 }
	    	 
	     }
     List<User> userRoleList = session.createQuery(query).setLong("orgLocationsId", orgLocationsId).setLong("currentApproverId", currentApproverId).setParameterList("ownerRolesList", ownerRolesList).list();
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
	     List<User> usersList = session.createQuery(query.toString()).setLong( "orgLocationsId", orgLocationsId).setLong( "orgLocationsId2", orgLocationsId).list();
	     return usersList;
	}

	@Override
	@Transactional
	public List<User> getUsersForConsultantsByUserRoleIdAndOrgLocationId(Long roleId, Long orgLocationsId) throws Exception {

		 Session session = this.sessionFactory.getCurrentSession();
	     String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations=:orgLocationsId and userRole.userState != 'INACTIVE' "
	     		+ "and userRole.roles.id = :roleId and userRole.user.id NOT IN (select candidate.consultantUser.id from Candidate candidate where candidate.orgLocationId.id = :orgLocationsId2 and candidate.consultantUser.id IS NOT NULL))  ";
	     List<User> usersList = session.createQuery(query.toString()).setLong( "orgLocationsId", orgLocationsId).setLong( "orgLocationsId2", orgLocationsId).setLong("roleId", roleId).list();
	     return usersList;
	}

	@Override
	@Transactional
	public List<User> getTabAccessUsersByOrgLocationIdAndTabIdAndUsersIds(Long orgLocationsId, List<Long> userIds,Long tabId) throws Exception {
		
		  Session session = this.sessionFactory.getCurrentSession();
			
		 String actionsQuery = "select sa.id from SourceleadActions sa where sa.tabs.id = :tabId  ";
	     List<Long> tabActionsIds = session.createQuery(actionsQuery.toString()).setLong( "tabId", tabId).list();
	     
	     String rolesQuery = "from Roles roles where roles.orgLocations.id = :orgLocationsId ";
	     List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setLong( "orgLocationsId", orgLocationsId).list();
	     List<Long> ownerRolesList = new ArrayList<Long>();
	     
	     for(Roles roles : rolesList){
	    	 if(null != roles.getActionId()){
	    		 List<Long> roleActionsList = new ArrayList<Long>();
	    		 /*for (String s : roles.getActionId().split(",")){
	        		 roleActionsList.add(Long.parseLong(s));
	        	 }*/
	    		 for (String s : Arrays.stream(roles.getActionId().split(",")).filter(value -> value != null && value.length() > 0).toArray(size -> new String[size])){
	    			 roleActionsList.add(Long.parseLong(s));
	        	 }
	        	 
	        	 roleActionsList.retainAll(tabActionsIds);
	        	 if(roleActionsList.size() > 0){
	        		 ownerRolesList.add(roles.getId());
	        	 }
	    	 }
	    	 
	     }
		 
		 
	     String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList) and userRole.user.id NOT IN (:userIds))  ";
	     List<User> userRoleList = session.createQuery(query.toString()).setParameterList("ownerRolesList", ownerRolesList).setParameterList("userIds", userIds).list();
	     return userRoleList;
	        
		}

	@Override
	@Transactional
	public List<User> getTabAccessUsersByOrgLocationIdAndTabIdAndNotInUsersId(Long orgLocationsId, Long userId,Long tabId) throws Exception {
		
		  Session session = this.sessionFactory.getCurrentSession();
			
		 String actionsQuery = "select sa.id from SourceleadActions sa where sa.tabs.id = :tabId  ";
	     List<Long> tabActionsIds = session.createQuery(actionsQuery.toString()).setLong( "tabId", tabId).list();
	     
	     String rolesQuery = "from Roles roles where roles.orgLocations.id = :orgLocationsId ";
	     List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setLong( "orgLocationsId", orgLocationsId).list();
	     List<Long> ownerRolesList = new ArrayList<Long>();
	     
	     for(Roles roles : rolesList){
	    	 if(null != roles.getActionId()){
	    		 List<Long> roleActionsList = new ArrayList<Long>();
	    		 /*for (String s : roles.getActionId().split(",")){
	        		 roleActionsList.add(Long.parseLong(s));
	        	 }*/
	    		 for (String s : Arrays.stream(roles.getActionId().split(",")).filter(value -> value != null && value.length() > 0).toArray(size -> new String[size])){
	    			 roleActionsList.add(Long.parseLong(s));
	        	 }
	        	 
	        	 roleActionsList.retainAll(tabActionsIds);
	        	 if(roleActionsList.size() > 0){
	        		 ownerRolesList.add(roles.getId());
	        	 }
	    	 }
	    	 
	     }
		 
		 
	     String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList) and userRole.user.id <> :userId)  ";
	     List<User> userRoleList = session.createQuery(query.toString()).setParameterList("ownerRolesList", ownerRolesList).setLong("userId", userId).list();
	     return userRoleList;
	        
		}

	@Override
	@Transactional
	public List<User> getUsersListByOrglocationId(Long orgLocationId) throws Exception {

		 Session session = this.sessionFactory.getCurrentSession();
	     String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations=:orgLocationId) ORDER BY user.id DESC";
	     List<User> usersList = session.createQuery(query.toString()).setLong( "orgLocationId", orgLocationId).list();
	     return usersList;
	}

	@Override
	@Transactional
	public User getUserByEmailAndUserType(String email, String userType) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<User> users = new ArrayList<User>();
		logger.debug("getUserByEmailAndUserType  start");
		users = session.createQuery("from User user where user.email = :email and user.userType = :userType" ).setString( "email", email).setString( "userType", userType).list();
		logger.debug("getUserByEmailAndUserType  end");
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	@Transactional
	public User getUserByEmail(String email) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<User> users = new ArrayList<User>();
		logger.debug("getUserByEmail  start");
		users = session.createQuery("from User user where user.email = :email " ).setString( "email", email).list();
		logger.debug("getUserByEmail  end");
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
		users = session.createQuery("from User user where user.userName = :userName" ).setString( "userName", userName).list();
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
	     List<Long> tabActionsIds = session.createQuery(actionsQuery.toString()).setLong( "tabId", tabId).list();
	     
	     String rolesQuery = "from Roles roles where roles.orgLocations.id = :orgLocationsId ";
	     List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setLong( "orgLocationsId", orgLocationsId).list();
	     List<Long> ownerRolesList = new ArrayList<Long>();
	     
	     for(Roles roles : rolesList){
	    	 if(null != roles.getActionId()){
	    		 List<Long> roleActionsList = new ArrayList<Long>();
	    		 /*for (String s : roles.getActionId().split(",")){
	        		 roleActionsList.add(Long.parseLong(s));
	        	 }*/
	    		 for (String s : Arrays.stream(roles.getActionId().split(",")).filter(value -> value != null && value.length() > 0).toArray(size -> new String[size])){
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
		     userRoleList = session.createQuery(query.toString()).setParameterList("ownersList", ownersList).setParameterList("ownerRolesList", ownerRolesList).list();
	     }else if(null != ownerRolesList && !ownerRolesList.isEmpty()){
	    	 String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList))  ";
		     userRoleList = session.createQuery(query.toString()).setParameterList("ownerRolesList", ownerRolesList).list();
	     }
	     return userRoleList;
	}
	
	
    @Override
	public List<User> getAllUsersWithRequirementTabAccessAndMemeberInRequirementMemberOrgLocationIdAndTabId(
			Long orgLocationsId, Long tabId) throws Exception {
	
		  Session session = this.sessionFactory.getCurrentSession();

    	 String actionsQuery = "select sa.id from SourceleadActions sa where sa.tabs.id = :tabId  ";
         List<Long> tabActionsIds = session.createQuery(actionsQuery.toString()).setLong( "tabId", tabId).list();
         
         String rolesQuery = "from Roles roles where roles.orgLocations.id = :orgLocationsId ";
         List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setLong( "orgLocationsId", orgLocationsId).list();
         List<Long> ownerRolesList = new ArrayList<Long>();
         
         for(Roles roles : rolesList){
        	 if(null != roles.getActionId()){
        		 List<Long> roleActionsList = new ArrayList<Long>();
        		 /*for (String s : roles.getActionId().split(",")){
            		 roleActionsList.add(Long.parseLong(s));
            	 }*/
        		 for (String s : Arrays.stream(roles.getActionId().split(",")).filter(value -> value != null && value.length() > 0).toArray(size -> new String[size])){
	    			 roleActionsList.add(Long.parseLong(s));
	        	 }
            	 
            	 roleActionsList.retainAll(tabActionsIds);
            	 if(roleActionsList.size() > 0){
            		 ownerRolesList.add(roles.getId());
            	 }
        	 }
        	 
         }
    	
         String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList))  ";
         List<User> userRoleList = session.createQuery(query.toString()).setParameterList("ownerRolesList", ownerRolesList).list();
         return userRoleList;
         
	}

	@Override
	public List<User> getUsersListAssignedToReoprtingManagersByOrgLocationId(Long orgLocationsId, User user, Long tabId)
			throws Exception {
		

		  Session session = this.sessionFactory.getCurrentSession();

      	 String actionsQuery = "select sa.id from SourceleadActions sa where sa.tabs.id = :tabId  ";
         List<Long> tabActionsIds = session.createQuery(actionsQuery.toString()).setLong( "tabId", tabId).list();
       
         String rolesQuery = "from Roles roles where roles.orgLocations.id = :orgLocationsId ";
         List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setLong( "orgLocationsId", orgLocationsId).list();
         List<Long> ownerRolesList = new ArrayList<Long>();
       
	       for(Roles roles : rolesList){
	      	 if(null != roles.getActionId()){
	      		 List<Long> roleActionsList = new ArrayList<Long>();
	      		 /*for (String s : roles.getActionId().split(",")){
	          		 roleActionsList.add(Long.parseLong(s));
	          	 }*/
	      		for (String s : Arrays.stream(roles.getActionId().split(",")).filter(value -> value != null && value.length() > 0).toArray(size -> new String[size])){
	    			 roleActionsList.add(Long.parseLong(s));
	        	 }
	          	 roleActionsList.retainAll(tabActionsIds);
	          	 if(roleActionsList.size() > 0){
	          		 ownerRolesList.add(roles.getId());
	          	 }
	      	 }
	       }
	       String query = "from User user where user.reportingManager.id =:userId  and  user.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList))   ";
	       List<User> userRoleList = session.createQuery(query.toString()).setParameterList("ownerRolesList", ownerRolesList).setLong( "userId", user.getId()).setLong( "orgLocationsId", orgLocationsId).list();
	       return userRoleList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public LinkedHashSet<User> getUsersListAsReoprtingManagersByOrgLocationIdHavingTabAccess(Long orgLocationsId,User user, Long tabId)
			throws Exception {
		
		
		Session session = this.sessionFactory.getCurrentSession();

     	 String actionsQuery = "select sa.id from SourceleadActions sa where sa.tabs.id = :tabId  ";
        List<Long> tabActionsIds = session.createQuery(actionsQuery.toString()).setLong( "tabId", tabId).list();
      
        String rolesQuery = "from Roles roles where roles.orgLocations.id = :orgLocationsId ";
        List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setLong( "orgLocationsId", orgLocationsId).list();
        List<Long> ownerRolesList = new ArrayList<Long>();
        LinkedHashSet<User> userRoleList = new LinkedHashSet<User>();
	       for(Roles roles : rolesList){
	      	 if(null != roles.getActionId()){
	      		 List<Long> roleActionsList = new ArrayList<Long>();
	      		 /*for (String s : roles.getActionId().split(",")){
	          		 roleActionsList.add(Long.parseLong(s));
	          	 }*/
	      		for (String s : Arrays.stream(roles.getActionId().split(",")).filter(value -> value != null && value.length() > 0).toArray(size -> new String[size])){
	    			 roleActionsList.add(Long.parseLong(s));
	        	 }
	          	 roleActionsList.retainAll(tabActionsIds);
	          	 if(roleActionsList.size() > 0){
	          		 ownerRolesList.add(roles.getId());
	          	 }
	      	 }
	       }
	       
	       String query = "from User user where  user.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList) and userRole.reportingManager.id =:managerUserId  and userRole.orgLocations.id =:orgLocationsId)   ";
	       List<User> userList = session.createQuery(query.toString()).setParameterList("ownerRolesList", ownerRolesList).setLong( "orgLocationsId", orgLocationsId).setLong( "managerUserId", user.getId()).list();
	       userRoleList.addAll(userList);
	       
	       return userRoleList;
	}

	@Override
	@Transactional
	public List<User> getUsersByOrgLocationIdExceptLoggedInUser(Long orgLocationsId, Long userId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();

	    String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations=:orgLocationsId and userRole.userState = 'ACTIVE') and user.id != :userId and"
	    		+ " user.id NOT IN (select offboarding.userId from CandidateOffBoarding offboarding where offboarding.orgLocationId = :orgLocationsId) ";

	    List<User> userRoleList = session.createQuery(query.toString()).setLong( "orgLocationsId", orgLocationsId).setLong("userId", userId).list();
        return userRoleList;
	}

	@Override
	@Transactional
	public List<User> getUsersHierarchyListByOrgLocationIdAndRoleIdAndNoCurrentUser(Long userId, Long roleId,
			Long orgLocationsId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String subQuery = "";
		
		subQuery = "userRole.user.id from UserRole userRole where  (userRole.roles.roleHierarchy.hierarchyLevel >= (select r.roleHierarchy.hierarchyLevel from Roles r where r.id = :roleId)) and (userRole.roles.actionId IS NOT NULL) and userRole.orgLocations = :orgLocationsId and userRole.userState = 'ACTIVE' and userRole.user.id <> :userId ";
		
		String query = "from User user where user.id IN (select "+subQuery+" )  ";
	    List<User> userRoleList = session.createQuery(query).setLong("roleId", roleId).setLong("userId", userId).setLong( "orgLocationsId", orgLocationsId).list();
        return userRoleList;
        
	
	}

	@Override
	@Transactional
	public List<User> getUserListByRoleActionIdAndOrgLocationId(Long roleActionId, Long orgLocationId)
			throws Exception {
		
		 Session session = this.sessionFactory.getCurrentSession();
		 String rolesQuery = "from Roles roles where roles.orgLocations.id = :orgLocationsId ";
         List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setLong( "orgLocationsId", orgLocationId).list();
         List<Long> ownerRolesList = new ArrayList<Long>();
         List<User> roleAccessUserList = new ArrayList<User>();
         
         for(Roles roles : rolesList){
	      	 if(null != roles.getActionId()){
	      		 List<Long> roleActionsList = new ArrayList<Long>();
	      		 /*for (String s : roles.getActionId().split(",")){
	          		 roleActionsList.add(Long.parseLong(s));
	          	 }*/
	      		for (String s : Arrays.stream(roles.getActionId().split(",")).filter(value -> value != null && value.length() > 0).toArray(size -> new String[size])){
	    			 roleActionsList.add(Long.parseLong(s));
	        	 }
	      		 
	      		 if(roleActionsList.contains(roleActionId)) {
	      			ownerRolesList.add(roles.getId());
	      		 }
	      	 }
	       }
         
           String query = "from User user where  user.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList) and  userRole.orgLocations.id =:orgLocationsId)   ";
	       List<User> userList = session.createQuery(query.toString()).setParameterList("ownerRolesList", ownerRolesList).setLong( "orgLocationsId", orgLocationId).list();
	       roleAccessUserList.addAll(userList);
	       
	       return roleAccessUserList;
	}

	@Override
	@Transactional
	public User getUserByVerficationUUID(String token)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<User> user = new ArrayList<User>();
		user = session.createQuery("from User user where user.verificationUUID = :uuid").setString("uuid", token).list();
		if (user.size() > 0) {
			return user.get(0);
		}
		else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public User getUserByUserCodeAndOrgLocationsId(String userCode, Long orgLocationsId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<User> users = new ArrayList<User>();
		String query = "from User user where user.userCode = :userCode and user.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations.id =:orgLocationsId) ";
		users = session.createQuery(query).setString( "userCode", userCode ).setLong("orgLocationsId", orgLocationsId).list();
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public ResponseList<UserDTO> getUsersDTOListByOrglocationId(Long orgLocationId,UserDTO userDTO) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		List<Long> countlist = null;
		Integer filterCount = 0;
		ResponseList<UserDTO> response = new ResponseList<>();
		Query queryObj = null;
		List<User> usersList = null;
		String subQuery1 =""; 
		StringBuilder query1 = new StringBuilder("");
		
		
		if(userDTO.getSearchValue() !=null && !"".equalsIgnoreCase(userDTO.getSearchValue())) {
			 query1.append(" user.firstName like '%" + userDTO.getSearchValue() + "%' ")
					.append("OR user.lastName like '%" + userDTO.getSearchValue() + "%' ")
					.append("OR concat(user.firstName,user.lastName) like '%" + userDTO.getSearchValue() + "%' ")
					.append("OR user.email like '%" + userDTO.getSearchValue() + "%' ")
					.append("OR user.mobilePhone like '%" + userDTO.getSearchValue() + "%' ")
					.append("OR user.id IN (select userRole.user.id from UserRole userRole where userRole.roles.displayName like '%" + userDTO.getSearchValue() + "%' and userRole.orgLocations.id = :orgLocationId) ");
					
			 
				 if(userDTO.getSearchValue().toLowerCase().equalsIgnoreCase("Resend")) {
					 query1.append("OR user.id IN (select userRole.user.id from UserRole userRole where userRole.userState = 'NOT_LOGGEDIN' and userRole.orgLocations.id = :orgLocationId)");
				 }else if(userDTO.getSearchValue().toLowerCase().equalsIgnoreCase("In-active") || userDTO.getSearchValue().toLowerCase().equalsIgnoreCase("In active")) {
					 query1.append("OR user.id IN (select userRole.user.id from UserRole userRole where userRole.userState = 'INACTIVE' and userRole.orgLocations.id = :orgLocationId)");
				 }else if(userDTO.getSearchValue().toLowerCase().equalsIgnoreCase("Active")) {
					 query1.append("OR user.id IN (select userRole.user.id from UserRole userRole where userRole.userState = 'ACTIVE' and userRole.orgLocations.id = :orgLocationId)");
				 }
						
			}
		
		
		
		String sortingQry = "";
		if(StringUtils.isNotBlank(userDTO.getSortColumn())) {
			sortingQry = " order by user."+ userDTO.getSortColumn() +" "+userDTO.getSortOrder();
		}
		
		
		if(userDTO.getUserloggedInStatus() != null && !userDTO.getUserloggedInStatus().isEmpty()){
			String[] statusFilter = userDTO.getUserloggedInStatus().split(",");
			if (Arrays.asList(statusFilter).contains("Not-Logged-In")) {
				subQuery1 = subQuery1 + "userRole.userState = 'NOT_LOGGEDIN' or ";
			}
			
			if (Arrays.asList(statusFilter).contains("Active")) {
				subQuery1 = subQuery1 + "userRole.userState = 'ACTIVE' or ";
			}
			
			if (Arrays.asList(statusFilter).contains("In-Active")) {
				subQuery1 = subQuery1 + "userRole.userState = 'INACTIVE' or ";
			}
			
			
			
			
			subQuery1 = "and (" + subQuery1.substring(0, subQuery1.length() - 4) + ")";
		}
		
		List<Long> rolesIdsList = new ArrayList<>();
		if(userDTO.getSubscribedOrgLocationRoles() != null && !userDTO.getSubscribedOrgLocationRoles().isEmpty()){
			String[] roles = userDTO.getSubscribedOrgLocationRoles().split(",");
			for(int i=0; i<roles.length; i++){
				rolesIdsList.add(Long.valueOf(roles[i]));
			}
		}else {
			rolesIdsList.add(0L);
		}
		
		String subQuery2 = " and ((:varZero  IN (:rolesIdsList)) OR (:varZero NOT IN (:rolesIdsList) and userRole.roles.id IN (:rolesIdsList)))";
		
	     query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations.id = :orgLocationId "+subQuery1+" "+subQuery2+")";
	
		if(query1.toString() != null && !query1.toString().isEmpty()){
			 queryObj = session.createQuery(query + " and "+"("+query1.toString()+")"+sortingQry);
		}else{
			 queryObj = session.createQuery(query + query1.toString()+sortingQry);
		}
		
		queryObj.setFirstResult(userDTO.getStart());
		queryObj.setMaxResults(userDTO.getLength());
		queryObj.setLong("orgLocationId", orgLocationId).setLong("varZero", 0).setParameterList("rolesIdsList", rolesIdsList).list();
		usersList = queryObj.list();
		
		if(query1.toString() != null && !query1.toString().isEmpty()){
			countlist = session.createQuery("select count(*) " + query + "and "+"("+query1.toString()+")").setLong("varZero", 0).setParameterList("rolesIdsList", rolesIdsList).setLong("orgLocationId", orgLocationId).list();
			filterCount = countlist.iterator().next().intValue();
		}else {
			countlist = session.createQuery("select count(*) " + query).setLong("orgLocationId", orgLocationId).setLong("varZero", 0).setParameterList("rolesIdsList", rolesIdsList).list();
			filterCount = countlist.iterator().next().intValue();
		}
		
        int totalCount = 0;

		
		if (userDTO != null) {
			if(countlist != null && !countlist.isEmpty()) {
				totalCount = countlist.iterator().next().intValue();
			}
		} 
		response.setRecordsTotal(totalCount);
		
		if(query1.toString() != null && !query1.toString().isEmpty()){
			response.setRecordsFiltered(filterCount);
		}else {
			response.setRecordsFiltered(totalCount);
		}
		
		response.setData(getUsersListDetails(usersList,orgLocationId));
		return response;
		
	}
	
	
	private List<UserDTO> getUsersListDetails(List<User> usersList,Long orgLocationId) throws Exception {
		
		List<UserDTO> userDTOList = new ArrayList<>();
		if(null != usersList){
			for(User user : usersList){
				UserDTO userDTO = new UserDTO();
				UserRole userRole = userRoleDao.getUserRoleByUserIdAndLocationId(user.getId(), orgLocationId);
				userDTO.setUserState(userRole.getUserState().toString());
				userDTO.setRole(userRole.getRoles().getDisplayName());
				userDTO.setFirstName(user.getFirstName());
				userDTO.setLastName(user.getLastName());
				userDTO.setEmail(user.getEmail());
				userDTO.setId(user.getId());
				if(null != userRole.getOrgLocations().getUser() && user.getId().equals(userRole.getOrgLocations().getUser().getId())){
					userDTO.setIsPrimaryAdmin("Y");
				}
				if(null != userRole.getOrgLocations().getOrg().getUser() && user.getId().equals(userRole.getOrgLocations().getOrg().getUser().getId())){
					userDTO.setIsSuperAdmin("Y");
				}
				
				userDTOList.add(userDTO);
			}
		}
		
		return userDTOList;
	
	}

	@SuppressWarnings({ "unchecked", "unchecked" })
	@Override
	@Transactional
	public ResponseList<UserRole> getUsersResponseListByOrglocationId(UserDTO userDTO,OrgLocations orgLocation,Date[] searchDates) throws Exception {
		 Session session = this.sessionFactory.getCurrentSession();
		 String query = "";
		 java.sql.Date startDate = null;
		 java.sql.Date endDate = null;
		 java.sql.Date sqlDate = null;
		 if(searchDates != null) {
			  startDate = new java.sql.Date(searchDates[0].getTime()); 
			  endDate = new java.sql.Date(searchDates[1].getTime()); 
			 query = "from UserRole userRole  where  userRole.orgLocations=:orgLocationId  and date(userRole.user.createdDate) BETWEEN :startDate and :endDate ";
		 }else {
			 query = "from UserRole userRole  where  userRole.orgLocations=:orgLocationId";
		 }
	      

	    List<UserRole> usersRoleList = null;
		ResponseList<UserRole> response = new ResponseList<>();
		List<Long> countlist = null;
	    String qry = "";
		if(StringUtils.isNotBlank(userDTO.getSortColumn())) {
				qry = " order by user."+ userDTO.getSortColumn() +" "+userDTO.getSortOrder();
		}
	     
	     StringBuilder query1 = new StringBuilder("");
	     
	     if(StringUtils.isNotEmpty(userDTO.getSearchValue()) && userDTO.getSearchValue() != null) {
	    	 Pattern pattern = Pattern.compile("^(\\d{0,4}(\\-|\\/{1})\\d{0,4}(\\-|\\/{1})\\d{0,4})$");
			   Matcher matcher = pattern.matcher(userDTO.getSearchValue());
			    if(matcher.matches() == true) {
							if(userDTO.getSearchValue().contains("/")||(userDTO.getSearchValue().contains("-"))) {
								String replacedValue = userDTO.getSearchValue().replaceAll("/", "-");
								    OrgLocations orgLoc = orgLocationService.getOrgLocations(orgLocation.getId());
									SimpleDateFormat format = new SimpleDateFormat(orgLoc.getDateFormat());
							        java.util.Date utilDate = format.parse(replacedValue);
							        sqlDate = new java.sql.Date(utilDate.getTime());
							}
			    }
			    
			    query1.append(" and (userRole.user.firstName like '%" + userDTO.getSearchValue() + "%' ")
				.append("OR userRole.user.lastName like '%" + userDTO.getSearchValue() + "%' ")
				.append("OR userRole.user.email like '%" + userDTO.getSearchValue() + "%' ")
				.append("OR userRole.user.mobilePhone like '%" + userDTO  .getSearchValue() + "%' ")
				.append("OR userRole.designation like '%" + userDTO  .getSearchValue() + "%' ")
				.append("OR userRole.user.id like '%" + userDTO.getSearchValue() + "%' )");
			    if(sqlDate != null) {
			    	query1.append("OR userRole.updatedDate like '%" + sqlDate + "%' ");
			    	query1.append("OR userRole.user.id IN(select employee.user.id from Employees employee where employee.hireDate like '%" + sqlDate + "%')");
			    }
	    	 
	     }
	     
	     Query queryObj = session.createQuery(query + query1.toString()+qry);
			
			if(searchDates != null) {
				usersRoleList = queryObj.setLong("orgLocationId", orgLocation.getId()).setParameter("startDate", startDate).setParameter("endDate", endDate).setFirstResult(userDTO.getStart()).setMaxResults(userDTO.getLength()).list();
				 countlist = session.createQuery("select count(*) " + query).setLong("orgLocationId", orgLocation.getId()).setParameter("startDate", startDate).setParameter("endDate", endDate).list();
			}else {
				usersRoleList = queryObj.setLong("orgLocationId", orgLocation.getId()).list();
				 countlist = session.createQuery("select count(*) " + query).setLong("orgLocationId", orgLocation.getId()).list();
			}
			
			int count = 0;
			int filteredCount = 0;

			if(countlist != null && !countlist.isEmpty()) {
				count = countlist.iterator().next().intValue();
			}
			response.setRecordsTotal(count);
			if (userDTO != null) {
				List<Long> filteredCountlist = null;
				if(searchDates != null) {
					filteredCountlist  = session.createQuery("select count(*) " + query + query1.toString()).setLong("orgLocationId", orgLocation.getId()).setParameter("startDate", startDate).setParameter("endDate", endDate).list();
				}else {
					filteredCountlist  = session.createQuery("select count(*) " + query + query1.toString()).setLong("orgLocationId", orgLocation.getId()).list();
				}
				if(filteredCountlist != null && !filteredCountlist.isEmpty()) {
					filteredCount = filteredCountlist.iterator().next().intValue();
				}
				response.setRecordsFiltered(filteredCount);
			} 
				response.setData(usersRoleList);
				return response;
	     
	}

	@Override
	@Transactional
	public User getUserbyConsultantUserId(Long consultantUserId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		User user = null;
		String query = "from User user where user.id = :userId";
		List<User> userList = session.createQuery(query).setLong( "userId", consultantUserId ).list();
		if (userList.size() > 0) {
			user = userList.get(0);
		}
		return user;
	}

	@Override
	@Transactional
	public List<UserDTO> getLinkedUserListByUserId(Long id) throws Exception {
		// TODO Auto-generated method stub
		
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from User user where user.linkUserId.id = :id  ";
	    List<User> usersList = session.createQuery(query.toString()).setLong("id", id).list();
	    List<UserDTO> userDTOList = new ArrayList<>();
	    if(usersList != null && !usersList.isEmpty()) {
	    	for(User user : usersList) {
	    		UserDTO userDTO = new UserDTO();
	    		userDTO.setEmail(user.getEmail());
	    		userDTO.setUserCode(user.getUserCode());
	    		userDTOList.add(userDTO);
	    	}
	    }
        return userDTOList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<User> getGlobalUsersByOrgId(Long roleId, Long orgId, Long tabId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String subQuery = "";
		
		
		 String actionsQuery = "select sa.id from SourceleadActions sa where sa.tabs.id = :tabId  ";
	     List<Long> tabActionsIds = session.createQuery(actionsQuery.toString()).setLong( "tabId", tabId).list();
	     
	     String rolesQuery = "from Roles roles where roles.org.id = :orgId ";
	     List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setLong( "orgId", orgId).list();
	     List<Long> ownerRolesList = new ArrayList<Long>();
	     
	     for(Roles roles : rolesList){
	    	 if(null != roles.getActionId()){
	    		 List<Long> roleActionsList = new ArrayList<Long>();
		    	 /*for (String s : roles.getActionId().split(",")){
		    		 roleActionsList.add(Long.parseLong(s));
		    	 }*/
		    	 for (String s : Arrays.stream(roles.getActionId().split(",")).filter(value -> value != null && value.length() > 0).toArray(size -> new String[size])){
	    			 roleActionsList.add(Long.parseLong(s));
	        	 }
		    	 
		    	 roleActionsList.retainAll(tabActionsIds);
		    	 if(roleActionsList.size() > 0){
		    		 ownerRolesList.add(roles.getId());
		    	 }
	    	 }
	     }
	     
	     
		
		subQuery = "userRole.user.id from UserRole userRole where (userRole.roles.actionId IS NOT NULL) and userRole.orgLocations.org.id = :orgId and userRole.userState != 'INACTIVE' and userRole.isGlobal = 'Y' and userRole.roles.id IN (:ownerRolesList)";
		
		String query = "from User user where user.id IN (select "+subQuery+" )  ";
	    List<User> userRoleList = session.createQuery(query).setParameterList("ownerRolesList", ownerRolesList).setLong( "orgId", orgId).list();
        return userRoleList;

        
	}

	@Override
	@Transactional
	public List<Long> getUserIdsListByLinkedUserId(Long userId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select user.id from User user where user.linkUserId.id = :userId " ;
	    List<Long> userIds = session.createQuery(query).setLong( "userId", userId).list();
        return userIds;
	}
	
	@Override
	@Transactional
	public List<User> getUsersListForSignatoryByOrglocationId(Long locationId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<User> userList = null;
		String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations=:orgLocationId) and user.id NOT IN (select ols.userId.id from OrglocationSignatory ols where ols.orgLocationId.id = :orgLocationId) ORDER BY user.id DESC";
		userList = session.createQuery(query).setLong( "orgLocationId", locationId).list();
		return userList;
	}

	@Override
	@Transactional
	public List<User> getTabAccessGlobalOwnersByOrgIdAndTabId(Long orgId, Long tabId) throws Exception {
		
		  Session session = this.sessionFactory.getCurrentSession();
			
		 String actionsQuery = "select sa.id from SourceleadActions sa where sa.tabs.id = :tabId  ";
	     List<Long> tabActionsIds = session.createQuery(actionsQuery.toString()).setLong( "tabId", tabId).list();
	     
	     String rolesQuery = "from Roles roles where roles.org.id = :orgId ";
	     List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setLong( "orgId", orgId).list();
	     List<Long> ownerRolesList = new ArrayList<Long>();
	     
	     for(Roles roles : rolesList){
	    	 if(null != roles.getActionId()){
	    		 List<Long> roleActionsList = new ArrayList<Long>();
	    		 /*for (String s : roles.getActionId().split(",")){
	        		 roleActionsList.add(Long.parseLong(s));
	        	 }*/
	    		 for (String s : Arrays.stream(roles.getActionId().split(",")).filter(value -> value != null && value.length() > 0).toArray(size -> new String[size])){
	    			 roleActionsList.add(Long.parseLong(s));
	        	 }
	        	 
	        	 roleActionsList.retainAll(tabActionsIds);
	        	 if(roleActionsList.size() > 0){
	        		 ownerRolesList.add(roles.getId());
	        	 }
	    	 }
	    	 
	     }
		 
	     String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and userRole.isGlobal = 'Y' and userRole.roles.id IN (:ownerRolesList))  ";
	     List<User> userRoleList = session.createQuery(query.toString()).setParameterList("ownerRolesList", ownerRolesList).list();
	     return userRoleList;
	        
		}

	@Override
	@Transactional
	public List<User> getTabAccessGlobalOwnersByOrgLocationIdAndTabIdAndNoCurrentUser(Long currentApproverId,
			Long orgId, Long tabId) throws Exception {
		
		  Session session = this.sessionFactory.getCurrentSession();
		  String query = query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and userRole.orgLocations.org.id = :orgId and userRole.isGlobal = 'Y'  and userRole.roles.id IN (:ownerRolesList) and userRole.user.id != :currentApproverId)";
		 
			
		  String actionsQuery = "select sa.id from SourceleadActions sa where sa.tabs.id = :tabId  ";
		     List<Long> tabActionsIds = session.createQuery(actionsQuery.toString()).setLong( "tabId", tabId).list();
		     
		     String rolesQuery = "from Roles roles where roles.org.id = :orgId ";
		     List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setLong( "orgId", orgId).list();
		     List<Long> ownerRolesList = new ArrayList<Long>();
		     
		     for(Roles roles : rolesList){
		    	 if(null != roles.getActionId()){
		    		 List<Long> roleActionsList = new ArrayList<Long>();
		    		 /*for (String s : roles.getActionId().split(",")){
		        		 roleActionsList.add(Long.parseLong(s));
		        	 }*/
		    		 for (String s : Arrays.stream(roles.getActionId().split(",")).filter(value -> value != null && value.length() > 0).toArray(size -> new String[size])){
		    			 roleActionsList.add(Long.parseLong(s));
		        	 }
		        	 
		        	 roleActionsList.retainAll(tabActionsIds);
		        	 if(roleActionsList.size() > 0){
		        		 ownerRolesList.add(roles.getId());
		        	 }
		    	 }
		    	 
		     }
	     List<User> userRoleList = session.createQuery(query).setLong("orgId", orgId).setLong("currentApproverId", currentApproverId).setParameterList("ownerRolesList", ownerRolesList).list();
	     return userRoleList;
	        
		}

	@Override
	@Transactional
	public List<User> getResourceManagersByOrgLocationIdAndTabId(Long orgLocationsId, Long tabId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String subQuery = "";
		
		
/*		 String actionsQuery = "select sa.id from SourceleadActions sa where sa.tabs.id = :tabId  ";
	     List<Long> tabActionsIds = session.createQuery(actionsQuery.toString()).setLong( "tabId", tabId).list();
*/	     
	     String rolesQuery = "from Roles roles where roles.orgLocations.id = :orgLocationsId ";
	     List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setLong( "orgLocationsId", orgLocationsId).list();
	     List<Long> ownerRolesList = new ArrayList<Long>();
	     
	     for(Roles roles : rolesList){
	    	 if(null != roles.getActionId()){
	    		 ownerRolesList.add(roles.getId());
	    		 /*List<Long> roleActionsList = new ArrayList<Long>();
		    	 for (String s : roles.getActionId().split(",")){
		    		 roleActionsList.add(Long.parseLong(s));
		    	 }
		    	 
		    	 roleActionsList.retainAll(tabActionsIds);
		    	 if(roleActionsList.size() > 0){
		    		 ownerRolesList.add(roles.getId());
		    	 }*/
	    	 }
	     }
	     
		
		subQuery = "userRole.user.id from UserRole userRole where userRole.orgLocations = :orgLocationsId and userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList)";
		
		String query = "from User user where user.id IN (select "+subQuery+" )  ";
	    List<User> userRoleList = session.createQuery(query).setParameterList("ownerRolesList", ownerRolesList).setLong( "orgLocationsId", orgLocationsId).list();
        return userRoleList;
        

	}

	@Override
	@Transactional
	public List<User> getReportingManagersByUserRolesAndOrgLocationId(Long roleId, Long orgLocationsId)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String subQuery = "";
		
		
	     String rolesQuery = "from Roles roles where roles.orgLocations.id = :orgLocationsId ";
	     List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setLong( "orgLocationsId", orgLocationsId).list();
	     List<Long> ownerRolesList = new ArrayList<Long>();
	     
	     for(Roles roles : rolesList){
	    	 if(null != roles.getActionId()){
	    		 ownerRolesList.add(roles.getId());
	    	 }
	     }
	     
		
		subQuery = "userRole.user.id from UserRole userRole where  (userRole.roles.roleHierarchy.hierarchyLevel <= (select r.roleHierarchy.hierarchyLevel from Roles r where r.id = :roleId)) and (userRole.roles.actionId IS NOT NULL) and userRole.orgLocations = :orgLocationsId and userRole.userState != 'INACTIVE' and (userRole.isGlobal = 'N' OR userRole.isGlobal = NULL) and userRole.roles.id IN (:ownerRolesList)";
		
		String query = "from User user where user.id IN (select "+subQuery+" )  ";
	    List<User> userRoleList = session.createQuery(query).setLong("roleId", roleId).setParameterList("ownerRolesList", ownerRolesList).setLong( "orgLocationsId", orgLocationsId).list();
        return userRoleList;
        
	}

	@Override
	@Transactional
	public List<User> getGlobalReportingManagersUsersByOrgId(Long roleId, Long orgId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String subQuery = "";
		
		
	     
	     String rolesQuery = "from Roles roles where roles.org.id = :orgId ";
	     List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setLong( "orgId", orgId).list();
	     List<Long> ownerRolesList = new ArrayList<Long>();
	     
	     for(Roles roles : rolesList){
	    	 if(null != roles.getActionId()){
	    		 ownerRolesList.add(roles.getId());
	    	 }
	     }
		
		subQuery = "userRole.user.id from UserRole userRole where (userRole.roles.actionId IS NOT NULL) and userRole.orgLocations.org.id = :orgId and userRole.userState != 'INACTIVE' and userRole.isGlobal = 'Y' and userRole.roles.id IN (:ownerRolesList)";
		
		String query = "from User user where user.id IN (select "+subQuery+" )  ";
	    List<User> userRoleList = session.createQuery(query).setParameterList("ownerRolesList", ownerRolesList).setLong( "orgId", orgId).list();
        return userRoleList;

        
	}

	@Override
	@Transactional
	public List<User> getReportingManagersByUserIdAndUserRolesAndOrgLocationIdAndUserTabId(Long userId, Long roleId,
			Long orgLocationsId) throws Exception {
		

		Session session = this.sessionFactory.getCurrentSession();
		String subQuery = "";
	     
	     String rolesQuery = "from Roles roles where roles.orgLocations.id = :orgLocationsId ";
	     List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setLong( "orgLocationsId", orgLocationsId).list();
	     List<Long> ownerRolesList = new ArrayList<Long>();
	     
	     for(Roles roles : rolesList){
	    	 if(null != roles.getActionId()){
	    		 ownerRolesList.add(roles.getId());
	    	 }
	     }
	     
		
		subQuery = "userRole.user.id from UserRole userRole where  (userRole.roles.roleHierarchy.hierarchyLevel <= (select r.roleHierarchy.hierarchyLevel from Roles r where r.id = :roleId)) and (userRole.roles.actionId IS NOT NULL) and userRole.orgLocations = :orgLocationsId and userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList) and userRole.user.id <> :userId";
		
		String query = "from User user where user.id IN (select "+subQuery+" )  ";
	    List<User> userRoleList = session.createQuery(query).setLong("roleId", roleId).setLong( "userId", userId).setParameterList("ownerRolesList", ownerRolesList).setLong( "orgLocationsId", orgLocationsId).list();
        return userRoleList;
        
	
	}

	@Override
	@Transactional
	public ResponseList<UserDTO> getRecruitersReports(Long userId, Long orgLocationId, Long orgId, String isGlobalUser,UserDTO userDTO,String loginUserRole,Date[] searchDates,List<Long> teamsList,String type)
			throws Exception {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		List<Long> countlist = null;
		Integer filterCount = 0;
		ResponseList<UserDTO> response = new ResponseList<>();
		Query queryObj = null;
		List<User> usersList = null;
		StringBuilder query1 = new StringBuilder("");
		StringBuilder query2 = new StringBuilder("");
		String globalAccessQuery = "";
		String searchQuery = "";
		int totalCount = 0;
		String subQueryTeam = "";
		String sortingQry = "";
		String myPerformanceReportQuery="";
		
		if(loginUserRole.equalsIgnoreCase("ROLE_ADMIN")) {
			
			if(userDTO.getSearchValue() !=null && !"".equalsIgnoreCase(userDTO.getSearchValue())) {
				searchQuery = " AND U.FIRST_NAME LIKE '%" + userDTO.getSearchValue() + "%' OR U.LAST_NAME LIKE '%" + userDTO.getSearchValue() + "%'";
			}
			if(teamsList.size()>0 && !teamsList.isEmpty()){
			subQueryTeam = " AND UR.USER_ID IN (SELECT TM.MEMBER_USER_ID FROM TEAM_MEMBERS TM WHERE TM.TEAM_ID IN (:teamsList) and TM.STATUS = '1' )";
			}
			if(StringUtils.isNotBlank(userDTO.getSortColumn())) {
				sortingQry = " ORDER BY U.FIRST_NAME"+" "+userDTO.getSortOrder();
			}
			
			if(StringUtils.isNotBlank(isGlobalUser) && isGlobalUser.equalsIgnoreCase(Constants.YES)) {
				
				try {
				query ="SELECT "
						+" CONCAT (U.FIRST_NAME,' ',IFNULL(U.LAST_NAME,'')) AS fullName, "
						+" U.ID AS userId,"
						+" U.USER_CODE AS userCode"
						+ " FROM "
					    + " USER U,"
					    + " USER_ROLE UR,"
					    + " ORG_LOCATIONS OL"
						+ " WHERE OL.ID = UR.ORG_LOCATIONS_ID AND "
						+ " UR.USER_ID = U.ID AND "
					    + " UR.USER_STATE = 'ACTIVE' "
						+searchQuery
						+subQueryTeam
						+ " AND  OL.ORG_ID = :orgId"
					    + " GROUP BY UR.USER_ID"
					    +sortingQry;
				
				
				queryObj = session.createSQLQuery(query);
				queryObj.setParameter("orgId", orgId);
				queryObj.setResultTransformer(Transformers.aliasToBean(UserDTO.class));
				queryObj.setFirstResult(userDTO.getStart());
				queryObj.setMaxResults(userDTO.getLength());
			List<UserDTO> userDTOList = queryObj.list();
			if(userDTOList != null && !userDTOList.isEmpty()) {
				usersList = new ArrayList<>();
				for(UserDTO uDTO :userDTOList) {
					User user = new User();
					user.setId(uDTO.getUserId().longValue());
					user.setFullName(uDTO.getFullName());
					user.setUserCode(uDTO.getUserCode());
					usersList.add(user);
				}
			}
			
			
			String countQuery ="SELECT"
					+ " COUNT(DISTINCT UR.USER_ID) AS USER_COUNT"
					+ " FROM"
					    + " USER U,"
					    + " USER_ROLE UR,"
					    + " ORG_LOCATIONS OL"
						+ " WHERE UR.USER_ID = U.ID AND "
					    + " OL.ID = UR.ORG_LOCATIONS_ID AND"
					    + " UR.USER_STATE = 'ACTIVE' "
						+searchQuery
						+subQueryTeam
						+ " AND  OL.ORG_ID = :orgId"
					    +sortingQry;
			
			Query countqueryString = session.createSQLQuery(countQuery);
			countqueryString.setParameter("orgId", orgId);
			BigInteger filterCount1 =  (BigInteger) countqueryString.uniqueResult();
			filterCount = filterCount1.intValue();
			totalCount = filterCount1.intValue();
			
				
				}catch (Exception e) {
					e.printStackTrace();
				}
				
			}else {
				if(StringUtils.isNotBlank(type) && type.equalsIgnoreCase("myPerformanceReports")) {
					myPerformanceReportQuery = " AND  UR.USER_ID = :ID";
				}
				try {
				
					query ="SELECT "
							+" CONCAT (U.FIRST_NAME,' ',IFNULL(U.LAST_NAME,'')) AS fullName, "
							+" U.ID AS userId,"
							+" U.USER_CODE AS userCode"
							+ " FROM"
						    + " USER U,"
						    + " USER_ROLE UR"
							+ " WHERE"
							+ " UR.USER_ID = U.ID AND "
						    + " UR.USER_STATE = 'ACTIVE' "
							+searchQuery
							+subQueryTeam
							+ " AND  UR.ORG_LOCATIONS_ID = :orgLocationId"
							+myPerformanceReportQuery
						    + " GROUP BY UR.USER_ID"
						    +sortingQry;
				
					queryObj = session.createSQLQuery(query);
					queryObj.setParameter("orgLocationId", orgLocationId);
					if(StringUtils.isNotBlank(type) && type.equalsIgnoreCase("myPerformanceReports")) {
						queryObj.setParameter("ID", userId);
					}
					if(teamsList.size()>0 && !teamsList.isEmpty()){
					queryObj.setParameterList("teamsList", teamsList);
					}
					queryObj.setResultTransformer(Transformers.aliasToBean(UserDTO.class));
					queryObj.setFirstResult(userDTO.getStart());
					queryObj.setMaxResults(userDTO.getLength());
				List<UserDTO> userDTOList = queryObj.list();
				if(userDTOList != null && !userDTOList.isEmpty()) {
					usersList = new ArrayList<>();
					for(UserDTO uDTO :userDTOList) {
						User user = new User();
						user.setId(uDTO.getUserId().longValue());
						user.setFullName(uDTO.getFullName());
						user.setUserCode(uDTO.getUserCode());
						usersList.add(user);
					}
				}
				
				String countQuery ="SELECT"
						+ " COUNT(DISTINCT UR.USER_ID) AS USER_COUNT"
						+ " FROM"
						    + " USER U,"
						    + " USER_ROLE UR"
							+ " WHERE UR.USER_ID = U.ID AND "
						    + " UR.USER_STATE = 'ACTIVE' "
							+searchQuery
							+subQueryTeam
						    + " AND  UR.ORG_LOCATIONS_ID = :orgLocationId"
						    +myPerformanceReportQuery
						    +sortingQry;
				
				Query countqueryString = session.createSQLQuery(countQuery);
				countqueryString.setParameter("orgLocationId", orgLocationId);
				if(StringUtils.isNotBlank(type) && type.equalsIgnoreCase("myPerformanceReports")) {
					countqueryString.setParameter("ID", userId);
				}
				if(teamsList.size()>0 && !teamsList.isEmpty()){
				countqueryString.setParameterList("teamsList", teamsList);
				}
				BigInteger filterCount1 =  (BigInteger) countqueryString.uniqueResult();
				filterCount = filterCount1.intValue();
				totalCount = filterCount1.intValue();
				}catch (Exception e) {
					e.printStackTrace();
				}
				
			}

			
		}else {
			
			if(teamsList != null && teamsList.isEmpty()){
				teamsList.add(0l);
			}
			
			String subQuery = " and ((:varZero  IN (:teamsList)) OR (:varZero NOT IN (:teamsList) and tm.teamId.id IN (:teamsList))) ";
			
			if(StringUtils.isNotBlank(isGlobalUser) && isGlobalUser.equalsIgnoreCase(Constants.YES)) {
				globalAccessQuery = " and userRole.orgLocations.org.id IN (select ol.org.id from OrgLocations ol where ol.id = :orgLocationId)";
			}else {
				globalAccessQuery = " and userRole.orgLocations.id = :orgLocationId";
			}
			
			
			if(userDTO.getSearchValue() !=null && !"".equalsIgnoreCase(userDTO.getSearchValue())) {
				query1.append(" user.firstName like '%" + userDTO.getSearchValue() + "%' ")
				.append("OR user.lastName like '%" + userDTO.getSearchValue() + "%' ");
							
				}
			if(StringUtils.isNotBlank(userDTO.getSortColumn())) {
					sortingQry = " order by user."+ userDTO.getSortColumn() +" "+userDTO.getSortOrder();
			}
			if(StringUtils.isNotBlank(type) && type.equalsIgnoreCase("myPerformanceReports")) {
				query = "from User user where (user.id IN (select userRole.user.id from UserRole userRole where userRole.user.id = :userId and userRole.userState = 'ACTIVE' "+globalAccessQuery+" )) ";
			}
			else{
				query = "from User user where (user.id IN (select userRole.user.id from UserRole userRole where (userRole.reportingManager.id = :userId OR userRole.resourceManager.id = :userId) and userRole.userState = 'ACTIVE' "+globalAccessQuery+" ) "
						+ " OR user.id IN (select tm.memberUserId.id from TeamMembers tm where tm.teamId.ownerUserId = :userId and tm.status = '1'  and tm.teamId.orgLocationsId = :orgLocationId  "+subQuery+"))";
			}
			
			if(query1.toString() != null && !query1.toString().isEmpty()){
				 queryObj = session.createQuery(query + "and "+"("+query1.toString()+")"+" "+sortingQry+" ");
			}else{
				 queryObj = session.createQuery(query +query1.toString()+" "+sortingQry);
			}
			
			if(StringUtils.isNotBlank(type) && type.equalsIgnoreCase("myPerformanceReports")) {
				queryObj.setLong("orgLocationId", orgLocationId).setLong("userId", userId).list();
			}
			else{
				queryObj.setLong("orgLocationId", orgLocationId).setLong("userId", userId).setParameterList("teamsList", teamsList).setLong("varZero", 0).list();
			}
			
			if(query1.toString() != null && !query1.toString().isEmpty()){
				countlist = session.createQuery("select count(*) " + query + "and "+"("+query1.toString()+") "+sortingQry+" ").setLong("orgLocationId", orgLocationId).setLong("userId", userId).list();
				filterCount = countlist.iterator().next().intValue();
			}else {
				if(StringUtils.isNotBlank(type) && type.equalsIgnoreCase("myPerformanceReports")) {
					countlist = session.createQuery("select count(*) " + query +sortingQry).setLong("orgLocationId", orgLocationId).setLong("userId", userId).list();
					filterCount = countlist.iterator().next().intValue();	
				}
				else{
					countlist = session.createQuery("select count(*) " + query +sortingQry).setLong("orgLocationId", orgLocationId).setLong("userId", userId).setLong("varZero", 0).setParameterList("teamsList", teamsList).list();
					filterCount = countlist.iterator().next().intValue();	
				}
				
			}
			queryObj.setFirstResult(userDTO.getStart());
			queryObj.setMaxResults(userDTO.getLength());
			usersList = queryObj.list();
		}
		
		if (userDTO != null) {
			if(countlist != null && !countlist.isEmpty()) {
				totalCount = countlist.iterator().next().intValue();
			}
		} 
		response.setRecordsTotal(totalCount);
		
		if(query1.toString() != null && !query1.toString().isEmpty()){
			response.setRecordsFiltered(filterCount);
		}else {
			response.setRecordsFiltered(totalCount);
		}
		
		response.setData(getUserListForRecruiterReport(usersList,orgLocationId,searchDates,isGlobalUser,orgId));
		return response;
	}
	
	private List<UserDTO> getUserListForRecruiterReport(List<User> usersList,Long orgLocationId,Date[] searchDates,String isGlobalUser,Long orgId){
		List<UserDTO> usersDTOList = new ArrayList<>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fromDate = sdf.format(searchDates[0]);
		String toDate = sdf.format(searchDates[1]);
		Map<String, UserDTO> userListMap = new HashMap<String, UserDTO>();
		Map<BigInteger, UserDTO> associatesListMap = new HashMap<BigInteger, UserDTO>();
		List<String> userCodeList = new ArrayList<>();
		List<Long> userIdsList = new ArrayList<>();
		if(usersList != null && !usersList.isEmpty()) {
			for(User user : usersList) {
				
				try {
					UserDTO userDTO = new UserDTO();
					userDTO.setFullName(user.getFullName());
					userDTO.setUserCode(user.getUserCode());
					userListMap.put(user.getUserCode(), userDTO);
					associatesListMap.put(BigInteger.valueOf(user.getId()), userDTO);
					userCodeList.add(user.getUserCode());
					userIdsList.add(user.getId());
					usersDTOList.add(userDTO);
					
				}catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
			try {
				
				
				List<RecruiterReportCandidateCountView> RecruiterReportCandidatesList = candidateService.getCandidateCountsCreatedUserList(orgLocationId, userCodeList, fromDate, toDate,isGlobalUser, orgId);
				if(RecruiterReportCandidatesList != null && !RecruiterReportCandidatesList.isEmpty()) {
					for(RecruiterReportCandidateCountView candidateReportView : RecruiterReportCandidatesList) {
						UserDTO userDTO = userListMap.get(candidateReportView.getAssociateUserCode());
						if(candidateReportView.getCandCount() != null) {
							userDTO.setUserCandidatesCount(candidateReportView.getCandCount());
						}
						
					}
				}
				
				//List<RecruiterReportAssociateCountView> RecruiterReportAssociateList = requirementService.getAssociateCountsCreatedUserList(orgLocationId, userIdsList, fromDate, toDate,isGlobalUser, orgId);
				List<RecruiterRrportDTO> recruiterReportAssociateList1 = requirementService.getAssociateCountsCreatedUserListByLocation(orgLocationId, userIdsList, fromDate, toDate,isGlobalUser, orgId);
				//Map<Long,List<Long>> requirementMap = new HashMap<Long,List<Long>>();
				Set<BigInteger> jobIds = new HashSet<>();
				Set<BigInteger> primaryUserjobIds = new HashSet<>();
				for(RecruiterRrportDTO reportDTO: recruiterReportAssociateList1){
					UserDTO userDTO = associatesListMap.get(reportDTO.getManagerUserId());
					if(userDTO != null){
						jobIds.add(reportDTO.getRequirementId());
						BigDecimal bd = new BigDecimal(jobIds.size());
						userDTO.setUserRequirementsCount(bd);
					}
					
					userDTO = associatesListMap.get(reportDTO.getPrimaryUserId());
					if(userDTO != null){
						primaryUserjobIds.add(reportDTO.getRequirementId());
						BigDecimal bd = new BigDecimal(primaryUserjobIds.size());
						userDTO.setUserRequirementsCount(bd);
					}
					
					if(reportDTO.getAssignedUserId() != null && reportDTO.getAssignedUserStatus().equalsIgnoreCase("1")) {
						userDTO = associatesListMap.get(reportDTO.getAssignedUserId());
						if(userDTO != null){
							jobIds.add(reportDTO.getRequirementId());
							userDTO.setUserRequirementsCount(userDTO.getUserRequirementsCount()==null? BigDecimal.ONE :userDTO.getUserRequirementsCount().add(BigDecimal.ONE) );
						}
					}
					
				}
				
				
				
				
			/*if(RecruiterReportAssociateList != null && !RecruiterReportAssociateList.isEmpty()) {
					for(RecruiterReportAssociateCountView associateReportView : RecruiterReportAssociateList) {
						UserDTO userDTO = associatesListMap.get(associateReportView.getAssociateUserId());
						if(associateReportView.getJobCount() != null) {
							userDTO.setUserRequirementsCount(associateReportView.getJobCount());
						}
						
					}
				}*/
				
				List<RecruiterReportView> recruiterReportViewList = requirementSubmissionHistoryService.getSubmissionCountsByStageAndCreatedUserListAndOrglocationId(orgLocationId, userCodeList, fromDate, toDate,isGlobalUser, orgId);
				if(recruiterReportViewList != null && !recruiterReportViewList.isEmpty()) {
					for(RecruiterReportView recuiterReportView : recruiterReportViewList) {
						UserDTO userDTO = userListMap.get(recuiterReportView.getUserCode());
						userDTO.setUserApplicantsCount(recuiterReportView.getApplicantSubmissionCount());
						userDTO.setUserShortlistedApplicantsCount(recuiterReportView.getApplicantShortlistedCount());
						userDTO.setUserSubmittedApplicantsCount(recuiterReportView.getApplicantSubmittedClientCount());
						userDTO.setUserOfferedApplicantsCount(recuiterReportView.getApplicantOfferMadeCount());
						userDTO.setUserRejectedApplicantsCount(recuiterReportView.getApplicantRejectedCount());
						userDTO.setUserInterviewedApplicantsCount(recuiterReportView.getApplicantInterviewCount());
						userDTO.setUserApplicantsBackOutCount(recuiterReportView.getApplicantBackOutCount());
						userDTO.setUserApplicantsJoinedCount(recuiterReportView.getApplicantJoinedCount());
					}
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return usersDTOList;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public DashboardItems getDashboardItemsByUserId(long loginUserId, long orgLocationId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from DashboardItems items where items.user.id= :userId and items.orgLocations.id=:orgLocationId";
			Query queryObj = session.createQuery(query);
			queryObj.setLong("userId", loginUserId);
			queryObj.setLong("orgLocationId", orgLocationId);
			List<DashboardItems> dashboardItems = queryObj.list();
			if (dashboardItems != null && !dashboardItems.isEmpty())
				return dashboardItems.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public DashboardItems getDashboardItemsByDashboardId(long dashboardItemId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			return session.get(DashboardItems.class, dashboardItemId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public long saveDashboardItems(DashboardItems dashboardItems) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(dashboardItems);
			return dashboardItems.getId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	@Transactional
	public void deleteUser(User user) throws Exception {
		logger.debug("Adding User");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (user != null) {
				session.delete(user);
			}
			logger.debug("User Deleted Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"USER"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
	}

	@Override
	@Transactional
	public List<LeaveBalanceReportDTO> getUserNameAndTypeByOrgLocationId(String searchValue,Long orgLocationId,List<Long> userRole,List<Long> orgDepartmentsList,String topleave,Date MonthStart,Date MonthEnd) {
		try {
		java.sql.Date startDateSqlDate = null;
		java.sql.Date endDateSqlDate = null;
		
		if((MonthStart!=null)||(MonthEnd!=null))
		{
		startDateSqlDate = new java.sql.Date(MonthStart.getTime());
		endDateSqlDate = new java.sql.Date(MonthEnd.getTime());
		}
		Session session = this.sessionFactory.getCurrentSession();
		List<LeaveBalanceReportDTO> userNameAndType = null;
		String subQuery4="";
		if(userRole !=null && userRole.size() > 0)
			subQuery4 = " and ur.ROLE_ID IN (:userRole)";
		String subQuery3=" ";
		if(orgDepartmentsList !=null && orgDepartmentsList.size() > 0)
			subQuery3 = " and e.DEPARTMENT_ID IN (:orgDepartmentsList)";
		String subQuery5 =" ";
		String subQuery6 =" ";
		String subQuery7 =" ";
        StringBuilder subQuery8 = new StringBuilder();
		if("yes".equalsIgnoreCase(topleave)) {

			if(startDateSqlDate !=null) {
				subQuery5="and ul.USER_ID=u.ID";
				subQuery6=" and ul.FROM_DATE >=:startDateSqlDate";
			}
			if(endDateSqlDate !=null) {
				subQuery7=" and ul.TO_DATE <=:endDateSqlDate";
			}
		}
		
		
		if(searchValue !=null && !"".equalsIgnoreCase(searchValue)) {
			subQuery8.append("and (u.FIRST_NAME like '%" + searchValue + "%' ")
					.append("OR u.LAST_NAME like '%" + searchValue + "%' ")
					.append("OR concat(u.FIRST_NAME,u.LAST_NAME) like '%" + searchValue + "%' ")
					.append("OR concat(u.FIRST_NAME,u.LAST_NAME) like '%" + searchValue + "%' ")
			.append("OR e.EMPLOYMENT_TYPE like '%" + searchValue + "%' ) ");

		}
			
					
		
	
		String query ="select distinct CONCAT (u.FIRST_NAME,' ',IFNULL(u.LAST_NAME,'')) AS empName ,e.EMPLOYMENT_TYPE AS empType,u.ID AS userId,ur.USER_TYPE AS role from USER u,USER_ROLE ur,EMPLOYEE e,USER_LEAVE ul where u.ID=ur.USER_ID and u.ID=e.USER_ID and  u.ID = ul.USER_ID and ur.ORG_LOCATIONS_ID=e.ORG_LOCATIONS_ID and ur.ORG_LOCATIONS_ID =:orgLocationId "+subQuery8.toString()+subQuery4+subQuery3+subQuery5+subQuery6+subQuery7;
		//Query queryString = (Query) session.createSQLQuery(query).setLong("orgLocationId",orgLocationId).setResultTransformer(Transformers.aliasToBean(LeaveBalanceReportDTO.class)).list();
		Query queryString = session.createSQLQuery(query);
		queryString.setParameter("orgLocationId", orgLocationId);
		if(userRole !=null && userRole.size() > 0) {
		queryString.setParameterList("userRole", userRole);
		}
		if(orgDepartmentsList !=null && orgDepartmentsList.size() > 0) {
			queryString.setParameterList("orgDepartmentsList", orgDepartmentsList);
			}
		if("yes".equalsIgnoreCase(topleave)) {
			if(startDateSqlDate !=null) {
				queryString.setParameter("startDateSqlDate", startDateSqlDate);
			}
			if(endDateSqlDate !=null) {
				queryString.setParameter("endDateSqlDate", endDateSqlDate);
			}
		}
		queryString.setResultTransformer(Transformers.aliasToBean(LeaveBalanceReportDTO.class));
		userNameAndType = queryString.list();
		
		return userNameAndType;
	  }catch(Exception e) {
			e.printStackTrace();
		  
	  }List<LeaveBalanceReportDTO>userName =null;
		
		return userName;
	}
	

	@Override
	@Transactional(rollbackFor = { Exception.class })
	@SuppressWarnings("unchecked")
	public List<User> getUsersListUsingMultipleStringIdsWithLocationId(String ids, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String[] idsValues = ids.split(",");
		// convert string array to long array
		Long[] data = new Long[idsValues.length];
		for (int i = 0; i < idsValues.length; i++) {
		  data[i] = Long.valueOf(idsValues[i]);
		}
		String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.orgLocations.id =:orgLocationId) and user.id IN ( :ids )";
		List<User> list = session.createQuery(query).setParameterList("ids", data).setParameter("orgLocationId", orgLocationId).list();
		return list;

	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	@SuppressWarnings("unchecked")
	public List<User> getUsersListUsingMultipleStringIds(String ids) throws Exception {
	Session session = this.sessionFactory.getCurrentSession();
	String[] idsValues = ids.split(",");
	// convert string array to long array
	Long[] data = new Long[idsValues.length];
	for (int i = 0; i < idsValues.length; i++) {
	data[i] = Long.valueOf(idsValues[i]);
	}
	String query = "from User user where user.id IN ( :ids )";
	List<User> list = session.createQuery(query).setParameterList("ids", data).list();
	return list;

	}


}