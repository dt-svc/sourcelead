package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CalendarUtil;

@Repository
public class UserRoleDaoImpl implements UserRoleDao {

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

	private static final Logger logger = LoggerFactory.getLogger(UserRoleDaoImpl.class);

	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public HashSet<ProjectMembers> getAllUserPrevoiusWeekTimeSheetSubmissionStatus(String invoiceFrequency) throws Exception {
		 Session session = this.sessionFactory.getCurrentSession();
		 List<ProjectMembers> projectMemberList = null;
		 if(StringUtils.isEmpty(invoiceFrequency)){
			invoiceFrequency = "Weekly";
		 }
		 String query = "from ProjectMembers pm left join fetch pm.projects proj left join fetch pm.employee emp where proj.invoiceFrequency = :invoiceFrequency";
		 projectMemberList = session.createQuery(query).setString("invoiceFrequency", invoiceFrequency).setCacheable(true).list(); 
		 HashSet<ProjectMembers> projectMemberSet  = null;
		 if(projectMemberList != null && !projectMemberList.isEmpty()){
			 logger.debug("The Total Employees which are not submitted the Time sheets within a week are : ***********************"+projectMemberList.size());
			 projectMemberSet = new HashSet<>(projectMemberList);
		 }
		 return projectMemberSet;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public HashSet<ProjectMembers> getAllUserPrevoiusWeekTimeSheetSubmissionStatusByApproverUserId(String invoiceFrequency,Long approverUserId) throws Exception {
		 Session session = this.sessionFactory.getCurrentSession();
		 List<ProjectMembers> projectMemberList = null;
		 if(StringUtils.isEmpty(invoiceFrequency)){
			invoiceFrequency = "Weekly";
		 }
		 String query = "from ProjectMembers pm left join fetch pm.projects proj left join fetch pm.employee emp where proj.invoiceFrequency = :invoiceFrequency and proj.id IN (select hierarchy.projectId.id from ProjectApproverHierarchy hierarchy where hierarchy.approverUserId.id = :approverUserId) ";
		 projectMemberList = session.createQuery(query).setString("invoiceFrequency", invoiceFrequency).setLong("approverUserId", approverUserId).setCacheable(true).list(); 
		 HashSet<ProjectMembers> projectMemberSet  = null;
		 if(projectMemberList != null && !projectMemberList.isEmpty()){
			 logger.debug("The Total Employees which are not submitted the Time sheets within a week are : ***********************"+projectMemberList.size());
			 projectMemberSet = new HashSet<>(projectMemberList);
		 }
		 return projectMemberSet;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public HashSet<UserRole> getAllUserTimeSheetSubmissionStatusCurrentWeek() {
	      Session session = this.sessionFactory.getCurrentSession(); 
	      Calendar cal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL); 
	      cal.setLenient(false); 
	      cal.setTime(new Date()); 
	      Date previousDate = CalendarUtil.getCurrentWeekDay("MON"); 
	      java.sql.Date previousDayDate = new java.sql.Date(previousDate.getTime()); 
	      String query = "from User user left join fetch userRole.user user left join fetch userRole.orgLocations orglocation where user.id IN (select emp.user from Employees emp where emp.InvoiceFrequency = 'Weekly' and emp.id IN(select pm.employee from ProjectMembers pm where pm.projects.orgLocationsId.id = orglocation.id and pm.id IN(select ts.projectMembers from TimesheetSubmissions ts where ts.startDate = :startDate and ts.status ='1' and ts.Id IN(select sah.submissionId from SubmissionApproverHierarchy sah where sah.submissionStatus = 'NEW'))))"; 
	      List<UserRole> userList = session.createQuery(query.toString()).setDate("startDate", previousDayDate).setCacheable(true).list(); 
	      logger.debug("The Total Employees which are not submitted the Time sheets within a week are : "+userList.size());
	      HashSet<UserRole> userSet  = null;
			if(userList != null && !userList.isEmpty()){
				userSet = new HashSet<>(userList);
			}
			return userSet;

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public UserRole addUserRole(UserRole userRole) throws Exception {
		logger.debug("Adding UserRole");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (userRole != null) {
				session.saveOrUpdate(userRole);
			}
			logger.debug("UserRole Added Successfully");
		} catch (Exception exception) {
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"UserLoginAttempts"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return userRole;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public UserRole getLoginUserPrimaryRole(Long userId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		List<UserRole> userRole = new ArrayList<UserRole>();
		logger.debug("getPrimaryRole By UserId start");
		userRole = session
				.createQuery("from UserRole userRole left join fetch userRole.orgLocations where userRole.user.id=:userId and userRole.isPrimary = '1' ")
				.setLong("userId", userId).setCacheable(true).list();
		logger.debug("getPrimaryRole By UserId start");
		if (userRole.size() > 0) {
			return userRole.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public UserRole getNotPrimaryUser(Long userId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		List<UserRole> userRole = new ArrayList<UserRole>();
		logger.debug("getNotPrimaryUser By UserId start");
		userRole = session
				.createQuery("from UserRole userRole where userRole.user.id=:userId and userRole.isPrimary = '0' and userRole.userState != 'INACTIVE' ")
				.setLong("userId", userId).setCacheable(true).list();
		logger.debug("getNotPrimaryUser By UserId start");
		if (userRole.size() > 0) {
			return userRole.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })

	public List<UserRole> getUsersListByLocationId(Long locationId, String userState, Long userId,String loginUserRole,String userCode)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();

		String subQuery = "";
		if (!isNullOrEmpty(userState)) {
			subQuery = "and userRole.userState = '" + userState + "'";
		}
		
		/*String subQuery1 = "";
		if(!loginUserRole.equalsIgnoreCase("ROLE_ADMIN") && !loginUserRole.equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")){
			subQuery1 = "and userRole.createdBy = '" + userCode + "' ";
		}*/

		return session.createQuery(
				"Select userRole from UserRole userRole where userRole.orgLocations.id = :locationId and userRole.user.id <> :userId and userRole.user.id <> '1' "
						+ subQuery + " ")
				.setLong("locationId", locationId).setLong("userId", userId).setCacheable(true).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<UserRole> getUsersListByRoleAndLocationId(Long roleId, Long locationId, String userState,
			Long userId,String loginUserRole,String userCode) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();

		String subQuery = "";
		if (!isNullOrEmpty(userState)) {
			subQuery = "and userRole.userState = '" + userState + "'";
		}
		
		/*String subQuery1 = "";
		if(!loginUserRole.equalsIgnoreCase("ROLE_ADMIN") && !loginUserRole.equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")){
			subQuery1 = "and userRole.createdBy = '" + userCode + "' ";
		}*/

		return session
				.createQuery("Select userRole from UserRole userRole where userRole.roles.id = :roleId "
						+ "and userRole.orgLocations.id = :locationId and userRole.user.id <> :userId and userRole.user.id <> '1' " + subQuery + "  ")
				.setLong("roleId", roleId).setLong("locationId", locationId).setLong("userId", userId).setCacheable(true).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<UserRole> getUsersListByUserNameAndLocationId(String userName, Long locationId, String userState,
			Long userId,String loginUserRole,String userCode) throws Exception {

		String subQuery = "";
		if (!isNullOrEmpty(userState)) {
			subQuery = "and userRole.userState = '" + userState + "'";
		}
		
		/*String subQuery1 = "";
		if(!loginUserRole.equalsIgnoreCase("ROLE_ADMIN") && !loginUserRole.equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")){
			subQuery1 = "and userRole.createdBy = '" + userCode + "' ";
		}*/

		Session session = this.sessionFactory.getCurrentSession();
		return session.createQuery("Select userRole from UserRole userRole where " + "((userRole.user.firstName LIKE '"
				+ userName + "%') OR (userRole.user.lastName LIKE '" + userName + "%') OR (userRole.user.email LIKE '" + userName + "')) "
				+ "and userRole.orgLocations.id = :locationId and userRole.user.id <> :userId and userRole.user.id <> '1' " + subQuery + "  ")
				.setLong("locationId", locationId).setLong("userId", userId).setCacheable(true).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<UserRole> getUsersListByRoleAndNameAndLocationId(Long roleId, String userName, Long locationId,
			String userState, Long userId,String loginUserRole,String userCode,Integer offset) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
        Map<String, Object> params = new HashMap<String,Object>();
		
        ArrayList<String> subquerys = new ArrayList<String>();
       
		String from = "from UserRole userRole where "; 
		
		String subQuery = "userRole.orgLocations.id = :locationId and userRole.user.id <> :userId and userRole.user.id <> '1' "
				+ " and userRole.roles.roleName <> 'ROLE_CROWDSOURCING_PROFESSIONAL' and userRole.roles.roleName <> 'ROLE_CROWDSOURCING_RECRUITER' " ;
		params.put("locationId",locationId);
		params.put("userId",userId);
		subquerys.add(subQuery);
		

		String subQuery3 = "";
		String subQuery4 = "";
		String subQuery5 = ""; 
		String firstWord="";
		String lastWord="";
		String subQuery8="";
		
		String s1 =userName;
		String withoutWhitespace = s1.trim();
		if(!"".equalsIgnoreCase(withoutWhitespace) && withoutWhitespace.contains(" "))
		{
			 
			 String[] words =withoutWhitespace.split("\\s");
				 firstWord = words[0];
				 lastWord = words[1];
		
		
		}
		
		if(!firstWord.equals("") && !lastWord.equals("")){
			 subQuery8 = " and ((userRole.user.firstName LIKE '" + firstWord + "%') OR (userRole.user.firstName LIKE '" + lastWord + "%') OR  (userRole.user.lastName LIKE '"+ firstWord + "%') OR  (userRole.user.lastName LIKE '"+ lastWord + "%'))";
			 subquerys.add(subQuery8);
		 }
		
		else{
			 subQuery8 =""; 
		 }
		
		 /*
		  *  to check the if the search  parameter is given or not
		 */
		
		 if(!"".equalsIgnoreCase(withoutWhitespace) && !withoutWhitespace.contains(" ")){
			 subQuery3 = " and ((userRole.user.firstName LIKE '" + withoutWhitespace + "%') OR  (userRole.user.lastName LIKE '"+ withoutWhitespace + "%') OR (userRole.user.email LIKE '" + withoutWhitespace + "'))";
			 subquerys.add(subQuery3);
		
		
		 }else{
			 subQuery3 =""; 
		 }
		
		 
		 /*
		  *  to check the if the UserState i,e Active,In Active or Not LoggedInn   parameter is given or not
		 */
		if(!"".equalsIgnoreCase(userState)){
			subQuery4 = " and userRole.userState = '" + userState + "'";
			 subquerys.add(subQuery4);
		}else{
			 subQuery4 ="";
		}
		
		
		 /*
		  *  to check the if the Role Search id Given Or Not
		 */
		if(roleId != null){
			subQuery5 = " and userRole.roles.id = :roleId";
			params.put("roleId",roleId);
			subquerys.add(subQuery5);
		}else{
			 subQuery5 ="";
		}
		
		
		/*
		 * appending   each and every subquery  to where Condition  
		 */
		StringBuilder whereCondition = new StringBuilder();
		for (String s : subquerys){
			whereCondition.append(s);
	       }
		
		Query query = session.createQuery(from+" "+whereCondition+" ORDER BY userRole.id DESC");
		for (String str : query.getNamedParameters())
        {
			if(str.equalsIgnoreCase("locationId") || str.equalsIgnoreCase("userId")){
				query.setLong(str, (long) params.get( str ));
			}else{
				query.setParameter( str, params.get( str ) );
			}
        	
        }
		List<UserRole>  list =null;
		list = query.setCacheable(true).list();
		if(query.list().size() >= offset){
			if(query.list().size() > offset + 10){
				return   list.subList(offset, (offset+10));
			}else{
				return   list.subList(offset, (  offset + (query.list().size() - offset)));
			}
		}else{
			return  null;
		}
		
	 	
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<UserRole> getUsersListByUserStateAndLocationId(String userState, Long locationId, Long userId,String loginUserRole,String userCode)
			throws Exception {
		
		/*String subQuery = "";
		if(!loginUserRole.equalsIgnoreCase("ROLE_ADMIN") && !loginUserRole.equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")){
			subQuery = "and userRole.createdBy = '" + userCode + "' ";
		}*/
		
		Session session = this.sessionFactory.getCurrentSession();
		return session
				.createQuery(
						"Select userRole from UserRole userRole where userRole.orgLocations.id = :locationId and userRole.user.id <> :userId and "
								+ " userRole.user.id <> '1' and userRole.userState = :userState  ")
				.setLong("locationId", locationId).setString("userState", userState).setLong("userId", userId).setCacheable(true).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<UserRole> getUsersBasedOnUserRoles() throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserRole userRole left join fetch userRole.roles role left join fetch userRole.user where role.roleName = 'ROLE_ADMIN'";
		List<UserRole> userRoleList = session.createQuery(query.toString()).setCacheable(true).list();
		return userRoleList;

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<UserRole> getApproverAssignedUsers(List<Long> projectIds, String userState, String searchByName) throws Exception {
		Long roleId = 2L;
		String userStateSubQuery = "";
		if (!isNullOrEmpty(userState)) {
			userStateSubQuery = "and userRole.user.userState = '" + userState + "'";
		}
		String userNameSubQuery = "";
		if (!isNullOrEmpty(searchByName)) {

			userNameSubQuery = "and (userRole.user.firstName LIKE '" + searchByName
					+ "%') OR (userRole.user.lastName LIKE '" + searchByName + "%')";

		}
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserRole userRole where userRole.roles.id = :roleId and "

				+ "userRole.user.id IN (select distinct pm.employee.user.id from ProjectMembers pm where pm.projects.id IN (:projectIds) ) "
				+ "" + userStateSubQuery + " " + userNameSubQuery + "";

		List<UserRole> userRoleList = session.createQuery(query.toString())
				.setLong("roleId", roleId)
				.setParameterList("projectIds", projectIds)
				.setCacheable(true).list();
		return userRoleList;

	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Long getUsersCountByOrgLocationID(Long orgLocationId) throws Exception {
		logger.debug("..getUsersCountByOrgLocationID() Method Started..");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select count(*) from UserRole userRole where userRole.orgLocations.id = :orgLocationId ";
		List<Long> results = session.createQuery(query).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		logger.debug("..getUsersCountByOrgLocationID() Method Ended..");
		return results.get(0);
	}
	

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean isUserAdmin(Long userId) {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<UserRole> userRole = new ArrayList<UserRole>();
		logger.debug("isUserAdmin By UserId start");
		userRole = session.createQuery("from UserRole userRole left join fetch userRole.roles role left join fetch userRole.user  where (role.roleName = 'ROLE_ADMIN' OR  role.roleName = 'ROLE_SOURCELEAD_ADMIN') and userRole.user =:userId")
						   .setLong("userId", userId)
						   .setCacheable(true).list();
		
		logger.debug("isUserAdmin By UserId End");
		
		Boolean flag = false;
		if (userRole.size() > 0) {
			flag = true;
		} 
		return flag;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean isUserAdminByOrgLocationId(Long userId,Long orgLocationId) {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<UserRole> userRole = new ArrayList<UserRole>();
		logger.debug("isUserAdmin By UserId start");
		userRole = session.createQuery("from UserRole userRole left join fetch userRole.roles role left join fetch userRole.user  where (role.roleName = 'ROLE_ADMIN' OR  role.roleName = 'ROLE_SOURCELEAD_ADMIN') and userRole.user =:userId and userRole.orgLocations.id = :orgLocationId")
						   .setLong("userId", userId)
						   .setLong("orgLocationId", orgLocationId) .setCacheable(true).list();
		
		logger.debug("isUserAdmin By UserId End");
		
		Boolean flag = false;
		if (userRole.size() > 0) {
			flag = true;
		} 
		return flag;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public UserRole getUserDetailsByEmailAndLocationId(String email,Long locationId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		List<UserRole> userRole = new ArrayList<UserRole>();
		logger.debug("getUserDetailsByEmailAndLocationId method  start");
		userRole = session
				.createQuery("from UserRole userRole left join fetch userRole.orgLocations left join fetch userRole.roles  "
						+ "left join fetch userRole.user where userRole.user.email = :email and userRole.orgLocations.id = :locationId")
				.setString("email", email).setLong("locationId", locationId).setCacheable(true).list();
		logger.debug("getUserDetailsByEmailAndLocationId method ends");
		if (userRole.size() > 0) {
			return userRole.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public UserRole getUserRoleByUserIdAndLocationId(Long userId,Long locationId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		List<UserRole> userRole = new ArrayList<UserRole>();
		logger.debug("getUserRoleByUserIdAndLocationId method  start");
		userRole = session.createQuery("from UserRole userRole left join fetch userRole.orgLocations left join fetch userRole.roles  "
						+ "left join fetch userRole.user where userRole.user.id = :userId and userRole.orgLocations.id = :locationId")
				.setLong("userId", userId).setLong("locationId", locationId).setCacheable(true).list();
		logger.debug("getUserRoleByUserIdAndLocationId method ends");
		if (userRole.size() > 0) {
			return userRole.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public boolean isUserActive(Long userId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<UserRole> userRole = new ArrayList<UserRole>();
		logger.debug("isUserActive By UserId start");
		userRole = session.createQuery("from UserRole userRole where userRole.user.id = :userId and userRole.userState = 'ACTIVE' ").setLong("userId", userId).setCacheable(true).list();
		logger.debug("isUserActive By UserId End");
		Boolean flag = false;
		if (userRole.size() > 0) {
			flag = true;
		} 
		return flag;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Long> getAllUserIdsByReportingManagerId(Long reportingManagerId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select userRole.user from UserRole userRole where userRole.reportingManager.id = :reportingManagerId and userRole.userState != 'INACTIVE' and userRole.userState != 'NOT_LOGGEDIN' " ;
	    List<Long> userIds = session.createQuery(query).setLong( "reportingManagerId", reportingManagerId).setCacheable(true).list();
        return userIds;
        
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<UserRole> getUserSpocsListByUserId(Long userId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserRole userRole left join fetch userRole.roles role left join fetch userRole.user where userRole.user.id = :userId and userRole.userState != 'INACTIVE' ";
		List<UserRole> userRoleList = session.createQuery(query.toString()).setLong("userId", userId).setCacheable(true).list();
		return userRoleList;

	}
	/**
	 * this method is used for elastic search
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<UserRole> getUsersListByUserId(Long userId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserRole userRole left join fetch userRole.roles role left join fetch userRole.user where userRole.user.id = :userId ";
		List<UserRole> userRoleList = session.createQuery(query.toString()).setLong("userId", userId).setCacheable(true).list();
		return userRoleList;

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public UserRole getUserByUserId(Long userId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		List<UserRole> userRole = new ArrayList<UserRole>();
		logger.debug("getUserByUserId By UserId start");
		userRole = session
				.createQuery("from UserRole userRole where userRole.user.id = :userId  ")
				.setLong("userId", userId).setCacheable(true).list();
		logger.debug("getUserByUserId By UserId start");
		if (userRole.size() > 0) {
			return userRole.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public UserRole getUserRoleByUserId(Long userId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserRole userRole where userRole.user.id=:userId";
		List<UserRole> userRoleList = session.createQuery(query.toString()).setLong("userId", userId).setCacheable(true).list();
		return userRoleList.get(0);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public UserRole getUserDetailsByUserIdAndLocationId(Long userId, Long locationId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		List<UserRole> userRole = new ArrayList<UserRole>();
		logger.debug("getUserDetailsByEmailAndLocationId method  start");
		userRole = session
				.createQuery("from UserRole userRole left join fetch userRole.orgLocations left join fetch userRole.roles  "
						+ "left join fetch userRole.user where userRole.user.id = :userId and userRole.orgLocations.id = :locationId")
				.setLong("userId", userId).setLong("locationId", locationId).setCacheable(true).list();
		logger.debug("getUserDetailsByEmailAndLocationId method ends");
		if (userRole.size() > 0) {
			return userRole.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public HashSet<ProjectMembers> getAllUserPrevoiusFridayTimeSheetSubmissionStatus(String invoiceFrequency) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		Calendar cal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
		cal.setLenient(false);
		cal.setTime(new Date());
		
		Calendar startMonday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON");
		
		Calendar previousFriday = CalendarUtil.getDateIncrementByincrement(startMonday,-3);
		Date previousDateFri = previousFriday.getTime();
		java.sql.Date  previousDayDateFri = new java.sql.Date(previousDateFri.getTime());
		
		Calendar previousMonday = CalendarUtil.getDateIncrementByincrement(startMonday,-42);
		Date previousDate = previousMonday.getTime();
		java.sql.Date  previousDayDate = new java.sql.Date(previousDate.getTime());
		
//		Date currentWeekMondayDate = CalendarUtil.getCurrentWeekDay("MON");
//		java.sql.Date  currentMondayDate = new java.sql.Date(currentWeekMondayDate.getTime());
		List<ProjectMembers> projectMemberList = null;
		if(invoiceFrequency !=null && !"".equalsIgnoreCase(invoiceFrequency)){
			//String query = "from UserRole userRole left join fetch userRole.user user left join fetch userRole.orgLocations orglocation where user.id IN (select emp.user from Employees emp where emp.id IN(select pm.employee from ProjectMembers pm where pm.projects.invoiceFrequency = :invoiceFrequency and pm.projects.orgLocationsId.id = orglocation.id and pm.id IN(select ts.projectMembers from TimesheetSubmissions ts where  (ts.startDate > :startDate and ts.startDate < :endDate ) and ts.status ='1' and ts.Id IN(select sah.submissionId from SubmissionApproverHierarchy sah where sah.submissionStatus = 'NEW'))))";
			String query = "from ProjectMembers pm left join fetch pm.projects proj left join fetch pm.employee emp where proj.invoiceFrequency = :invoiceFrequency and pm.id IN(select ts.projectMembers from TimesheetSubmissions ts where  (ts.startDate > :startDate and ts.startDate < :endDate ) and ts.status ='1' and ts.Id IN(select sah.submissionId from SubmissionApproverHierarchy sah where sah.submissionStatus = 'NEW'))";
			projectMemberList = session.createQuery(query).setDate("startDate", previousDayDate).setDate("endDate", previousDateFri).setString("invoiceFrequency", invoiceFrequency).setCacheable(true).list(); 
		}else{
			String query = "from ProjectMembers pm left join fetch pm.projects proj left join fetch pm.employee emp where pm.id IN(select ts.projectMembers from TimesheetSubmissions ts where  (ts.startDate > :startDate and ts.startDate < :endDate ) and ts.status ='1' and ts.Id IN(select sah.submissionId from SubmissionApproverHierarchy sah where sah.submissionStatus = 'NEW'))";
			//String query = "from UserRole userRole left join fetch userRole.user user left join fetch userRole.orgLocations orglocation where user.id IN (select emp.user from Employees emp where emp.id IN(select pm.employee from ProjectMembers pm where pm.projects.orgLocationsId.id = orglocation.id and pm.id IN(select ts.projectMembers from TimesheetSubmissions ts where (ts.startDate > :startDate and ts.startDate < :endDate ) and ts.status ='1' and ts.Id IN(select sah.submissionId from SubmissionApproverHierarchy sah where sah.submissionStatus = 'NEW'))))";
			projectMemberList = session.createQuery(query).setDate("startDate", previousDayDate).setDate("endDate", previousDateFri).setCacheable(true).list(); 
		}
		
		logger.debug("The Total Employees which are not submitted the Time sheets within a week are : ***********************"+projectMemberList.size());
		 HashSet<ProjectMembers> projectMemberSet  = null;
		 if(projectMemberList != null && !projectMemberList.isEmpty()){
			 projectMemberSet = new HashSet<>(projectMemberList);
			}
			return projectMemberSet;
	}
    
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Long> getAllAssignedUsersIdsByReportingManagerId(Long reportingManagerId,Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select userRole.user.id from UserRole userRole where userRole.reportingManager.id = :reportingManagerId and userRole.orgLocations.id = :locationId  " ;
	    List<Long> userIds = session.createQuery(query).setLong( "reportingManagerId", reportingManagerId).setLong("locationId", orgLocationId).setCacheable(true).list();
        return userIds;
        
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<UserRole> getAllAssignedUsersByReportingManagerId(Long reportingManagerId, Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserRole userRole where userRole.reportingManager.id = :reportingManagerId and userRole.orgLocations.id = :locationId ";
		List<UserRole> userRoleList = session.createQuery(query.toString()).setLong( "reportingManagerId", reportingManagerId).setLong("locationId", orgLocationId).setCacheable(true).list();
		return userRoleList;
	}

	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<UserRole> getAllUsersListByOrgLocationId(Long orgLocationId,Long loginUserId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserRole userRole left join fetch userRole.user user  where"
				+ " (userRole.user.id IN (select os.vendorUser.id from OrgSpoc os where os.clientUser.id =:loginUserId and  os.clientLocationId.id = :orgLocationId) "
				+ "OR userRole.user.id IN (select os.clientUser.id from OrgSpoc os where os.vendorUser.id =:loginUserId and  os.vendorLocationId.id = :orgLocationId) )"
				+ "OR (userRole.orgLocations.id =:orgLocationId) GROUP BY user.id ";
		List<UserRole> usersList = session.createQuery(query.toString()).setLong( "orgLocationId", orgLocationId).setLong( "loginUserId", loginUserId).setCacheable(true).list();
		return usersList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<UserRole> getAllActiveUsersListByOrgLocationId(Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserRole userRole where userRole.orgLocations.id = :orgLocationId and userRole.userState = 'ACTIVE' ";
		List<UserRole> userRoleList = session.createQuery(query.toString()).setLong( "orgLocationId", orgLocationId).setCacheable(true).list();
		return userRoleList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean isUserisPrimaryForOrg(Long userId, Long orgLocationId) {
		

		Session session = this.sessionFactory.getCurrentSession();
		List<UserRole> userRole = new ArrayList<UserRole>();
		logger.debug("isUserAdmin By UserId start");
		userRole = session.createQuery("from UserRole userRole left join fetch userRole.roles role left join fetch userRole.user  where userRole.user =:userId "
				+ "and userRole.orgLocations.id = :orgLocationId and userRole.isPrimary = '1' ")
						   .setLong("userId", userId)
						   .setLong("orgLocationId", orgLocationId) .setCacheable(true).list();
		
		logger.debug("isUserAdmin By UserId End");
		
		Boolean flag = false;
		if (userRole.size() > 0) {
			flag = true;
		} 
		return flag;
	}

	@Override
	@Transactional
	public UserRole getUserDetailsByUserIdAndLocationIdAndUserType(Long userId, Long locationId, String userType) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<UserRole> userRole = new ArrayList<UserRole>();
		logger.debug("getUserDetailsByEmailAndLocationId method  start");
		userRole = session
				.createQuery("from UserRole userRole left join fetch userRole.orgLocations left join fetch userRole.roles  "
						+ "left join fetch userRole.user where userRole.user.id = :userId and userRole.orgLocations.id = :locationId and userRole.userType = :userType ")
				.setLong("userId", userId).setLong("locationId", locationId).setString("userType", userType).setCacheable(true).list();
		logger.debug("getUserDetailsByEmailAndLocationId method ends");
		if (userRole.size() > 0) {
			return userRole.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public UserRole getIsProfessionalOrRecruiterExistsWithEmailAndUserType(String email, String userType) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<UserRole> userRole = new ArrayList<UserRole>();
		logger.debug("getIsProfessionalOrRecruiterExistsWithEmailAndUserType method  start");
		userRole = session
				.createQuery("from UserRole userRole where userRole.user.email = :email and userRole.userType = :userType ")
				.setString("email", email).setString("userType", userType).setCacheable(true).list();
		logger.debug("getIsProfessionalOrRecruiterExistsWithEmailAndUserType method ends");
		if (userRole.size() > 0) {
			return userRole.get(0);
		} else {
			return null;
		}
	}

	
}
