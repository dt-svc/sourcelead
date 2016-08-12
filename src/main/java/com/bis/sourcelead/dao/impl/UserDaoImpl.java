package com.bis.sourcelead.dao.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class UserDaoImpl implements UserDao {

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
	    	if(emailHost.equalsIgnoreCase(host)){
	 	    	if(Arrays.asList(SourceLead_BLOCKED_SOCIAL_DOMAINS).contains(host)){
	 	    		status="Invalid Email";
	 	    	}else{
	 	    		    User user = findByEmail(email);
			 	      	if(user != null &&  user.getEmail().equalsIgnoreCase(email)){
			 	    		status="Existed Email";
			 	    	}else{
			 	    		status="Valid Email";
			 	    	}
		 	    }
	 	    }
	    	else{
	 	    	status="Invalid Email";
	 	    }
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
	public Collection<User> getUsersByUserRoles() throws Exception {
		// TODO Auto-generated method stub
		
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where userRole.roles = '1') ";
	    List<User> userRoleList = session.createQuery(query.toString()).setCacheable(true).list();
        return userRoleList;
        
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<User> getUsersByUserRolesAndOrgLocationIdAndNoCurrentUser(Long userId, Long orgLocationsId)
			throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where (userRole.roles = '3') and userRole.orgLocations=:orgLocationsId) and user.userState != 'INACTIVE' and user.id <> :userId ";
	    List<User> userRoleList = session.createQuery(query.toString()).setLong( "orgLocationsId", orgLocationsId).setLong( "userId", userId).setCacheable(true).list();
      
	    return userRoleList;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<User> getUsersByUserRolesAndOrgLocationId(Long orgLocationsId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from User user where user.id IN (select userRole.user.id from UserRole userRole where (userRole.roles = '3') and userRole.orgLocations=:orgLocationsId) and user.userState != 'INACTIVE' ";
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
	

}