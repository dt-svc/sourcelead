package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

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
import com.bis.sourcelead.entity.User;
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
	public HashSet<UserRole> getAllUserPrevoiusWeekTimeSheetSubmissionStatus(String invoiceFrequency) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		Calendar cal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
		cal.setLenient(false);
		cal.setTime(new Date());
		
		Calendar startMonday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON");
		
		Calendar previousMonday = CalendarUtil.getDateIncrementByincrement(startMonday,-42);
		Date previousDate = previousMonday.getTime();
		java.sql.Date  previousDayDate = new java.sql.Date(previousDate.getTime());
		
		Date currentWeekMondayDate = CalendarUtil.getCurrentWeekDay("MON");
		java.sql.Date  currentMondayDate = new java.sql.Date(currentWeekMondayDate.getTime());
		List<UserRole> userList = null;
		if(invoiceFrequency !=null && !"".equalsIgnoreCase(invoiceFrequency)){
			String query = "from UserRole userRole left join fetch userRole.user user left join fetch userRole.orgLocations orglocation where user.id IN (select emp.user from Employees emp where emp.InvoiceFrequency = :invoiceFrequency and emp.id IN(select pm.employee from ProjectMembers pm where pm.projects.orgLocationsId.id = orglocation.id and pm.id IN(select ts.projectMembers from TimesheetSubmissions ts where  (ts.startDate > :startDate and ts.startDate < :endDate ) and ts.status ='1' and ts.Id IN(select sah.submissionId from SubmissionApproverHierarchy sah where sah.submissionStatus = 'NEW'))))";
			userList = session.createQuery(query).setDate("startDate", previousDayDate).setDate("endDate", currentMondayDate).setString("invoiceFrequency", invoiceFrequency).setCacheable(true).list(); 
		}else{
			String query = "from UserRole userRole left join fetch userRole.user user left join fetch userRole.orgLocations orglocation where user.id IN (select emp.user from Employees emp where emp.id IN(select pm.employee from ProjectMembers pm where pm.projects.orgLocationsId.id = orglocation.id and pm.id IN(select ts.projectMembers from TimesheetSubmissions ts where (ts.startDate > :startDate and ts.startDate < :endDate ) and ts.status ='1' and ts.Id IN(select sah.submissionId from SubmissionApproverHierarchy sah where sah.submissionStatus = 'NEW'))))";
			userList = session.createQuery(query).setDate("startDate", previousDayDate).setDate("endDate", currentMondayDate).setCacheable(true).list(); 
		}
		
		logger.debug("The Total Employees which are not submitted the Time sheets within a week are : ***********************"+userList.size());
		 HashSet<UserRole> userSet  = null;
		 if(userList != null && !userList.isEmpty()){
				userSet = new HashSet<>(userList);
			}
			return userSet;
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
		
		String subQuery1 = "";
		if(!loginUserRole.equalsIgnoreCase("ROLE_ADMIN") && !loginUserRole.equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")){
			subQuery1 = "and userRole.createdBy = '" + userCode + "' ";
		}

		return session.createQuery(
				"Select userRole from UserRole userRole where userRole.orgLocations.id = :locationId and userRole.user.id <> :userId "
						+ subQuery + " "+subQuery1+" ")
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
		
		String subQuery1 = "";
		if(!loginUserRole.equalsIgnoreCase("ROLE_ADMIN") && !loginUserRole.equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")){
			subQuery1 = "and userRole.createdBy = '" + userCode + "' ";
		}

		return session
				.createQuery("Select userRole from UserRole userRole where userRole.roles.id = :roleId "
						+ "and userRole.orgLocations.id = :locationId and userRole.user.id <> :userId " + subQuery + " "+subQuery1+" ")
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
		
		String subQuery1 = "";
		if(!loginUserRole.equalsIgnoreCase("ROLE_ADMIN") && !loginUserRole.equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")){
			subQuery1 = "and userRole.createdBy = '" + userCode + "' ";
		}

		Session session = this.sessionFactory.getCurrentSession();
		return session.createQuery("Select userRole from UserRole userRole where " + "((userRole.user.firstName LIKE '"
				+ userName + "%') OR (userRole.user.lastName LIKE '" + userName + "%') OR (userRole.user.email LIKE '" + userName + "')) "
				+ "and userRole.orgLocations.id = :locationId and userRole.user.id <> :userId " + subQuery + " " + subQuery1 + " ")
				.setLong("locationId", locationId).setLong("userId", userId).setCacheable(true).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<UserRole> getUsersListByRoleAndNameAndLocationId(Long roleId, String userName, Long locationId,
			String userState, Long userId,String loginUserRole,String userCode) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();

		String subQuery = "";
		if (!isNullOrEmpty(userState)) {
			subQuery = "and userRole.userState = '" + userState + "'";
		}
		
		String subQuery1 = "";
		if(!loginUserRole.equalsIgnoreCase("ROLE_ADMIN") && !loginUserRole.equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")){
			subQuery1 = "and userRole.createdBy = '" + userCode + "' ";
		}

		return session
				.createQuery("Select userRole from UserRole userRole where userRole.roles.id = :roleId and "
						+ "((userRole.user.firstName LIKE '" + userName + "%') OR (userRole.user.lastName LIKE '"
						+ userName + "%') OR (userRole.user.email LIKE '" + userName + "')) "
						+ "and userRole.orgLocations.id = :locationId and userRole.user.id <> :userId " + subQuery + " " + subQuery1 + "")
				.setLong("roleId", roleId).setLong("locationId", locationId).setLong("userId", userId).setCacheable(true).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<UserRole> getUsersListByUserStateAndLocationId(String userState, Long locationId, Long userId,String loginUserRole,String userCode)
			throws Exception {
		
		String subQuery = "";
		if(!loginUserRole.equalsIgnoreCase("ROLE_ADMIN") && !loginUserRole.equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")){
			subQuery = "and userRole.createdBy = '" + userCode + "' ";
		}
		
		Session session = this.sessionFactory.getCurrentSession();
		return session
				.createQuery(
						"Select userRole from UserRole userRole where userRole.orgLocations.id = :locationId and userRole.user.id <> :userId and "
								+ "userRole.userState = :userState "+subQuery+" ")
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
	
	/*@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<UserRole> getManagersByUserRolesAndOrgLocationId(String roleType , Long orgLocationsId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String subQuery = "";
		
		if(roleType != null && !"".equals(roleType) && "1".equals(roleType)){
			subQuery = "and userRole.userType = 'Admin'";
		}
		else if(roleType != null && !"".equals(roleType) && "3".equals(roleType)){
			subQuery = "and (userRole.userType = 'Admin' OR userRole.userType = 'Project Manager' OR userRole.userType = 'HR Manager')";
		}
		else if(roleType != null && !"".equals(roleType) && "2".equals(roleType)){
			subQuery = "and (userRole.userType = 'Admin' OR userRole.userType = 'Project Manager' OR userRole.userType = 'HR Manager' OR userRole.userType = 'Account Manager' OR userRole.userType = 'Sales Manager' OR userRole.userType = 'Recruiter' OR userRole.userType = 'Team Lead')";
		}
		else if(roleType != null && !"".equals(roleType) && "6".equals(roleType)){
			subQuery = "and (userRole.userType = 'Admin' OR userRole.userType = 'Project Manager' OR userRole.userType = 'HR Manager' OR userRole.userType = 'Account Manager')";
		}
		else if(roleType != null && !"".equals(roleType) && "5".equals(roleType)){
			subQuery = "and (userRole.userType = 'Admin' OR userRole.userType = 'HR Manager')";
		}
		else if(roleType != null && !"".equals(roleType) && "7".equals(roleType)){
			subQuery = "and (userRole.userType = 'Admin' OR userRole.userType = 'Project Manager' OR userRole.userType = 'HR Manager' OR userRole.userType = 'Account Manager' OR userRole.userType = 'Sales Manager')";
		}
		else if(roleType != null && !"".equals(roleType) && "8".equals(roleType)){
			subQuery = "and (userRole.userType = 'Admin' OR userRole.userType = 'Project Manager' OR userRole.userType = 'HR Manager' OR userRole.userType = 'Account Manager' OR userRole.userType = 'Sales Manager' OR userRole.userType = 'Recruiter' OR userRole.userType = 'Team Lead')";
		}
		else if(roleType != null && !"".equals(roleType) && "9".equals(roleType)){
			subQuery = "and (userRole.userType = 'Admin' OR userRole.userType = 'Project Manager' OR userRole.userType = 'HR Manager' OR userRole.userType = 'Project Manager' OR userRole.userType = 'Team Lead')";
		}
		
	    String query = "from UserRole userRole where userRole.orgLocations = :orgLocationsId and userRole.userState != 'INACTIVE' "+subQuery+" ";
	    List<UserRole> userRoleList = session.createQuery(query).setLong( "orgLocationsId", orgLocationsId).setCacheable(true).list();
        return userRoleList;
        
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<UserRole> getManagersByUserIdAndUserRolesAndOrgLocationId(Long userId,String roleType , Long orgLocationsId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String subQuery = "";
		
		if(roleType != null && !"".equals(roleType) && "Admin".equals(roleType)){
			subQuery = "and userRole.userType = 'Admin'";
		}
		else if(roleType != null && !"".equals(roleType) && "Project Manager".equals(roleType)){
			subQuery = "and (userRole.userType = 'Admin' OR userRole.userType = 'Project Manager' OR userRole.userType = 'HR Manager')";
		}
		else if(roleType != null && !"".equals(roleType) && "Employee".equals(roleType)){
			subQuery = "and (userRole.userType = 'Admin' OR userRole.userType = 'Project Manager' OR userRole.userType = 'HR Manager' OR userRole.userType = 'Account Manager' OR userRole.userType = 'Sales Manager' OR userRole.userType = 'Recruiter')";
		}
		else if(roleType != null && !"".equals(roleType) && "Account Manager".equals(roleType)){
			subQuery = "and (userRole.userType = 'Admin' OR userRole.userType = 'Project Manager' OR userRole.userType = 'HR Manager' OR userRole.userType = 'Account Manager')";
		}
		else if(roleType != null && !"".equals(roleType) && "HR Manager".equals(roleType)){
			subQuery = "and (userRole.userType = 'Admin' OR userRole.userType = 'HR Manager')";
		}
		else if(roleType != null && !"".equals(roleType) && "Sales Manager".equals(roleType)){
			subQuery = "and (userRole.userType = 'Admin' OR userRole.userType = 'Project Manager' OR userRole.userType = 'HR Manager' OR userRole.userType = 'Account Manager' OR userRole.userType = 'Sales Manager')";
		}
		else if(roleType != null && !"".equals(roleType) && "Recruiter".equals(roleType)){
			subQuery = "and (userRole.userType = 'Admin' OR userRole.userType = 'Project Manager' OR userRole.userType = 'HR Manager' OR userRole.userType = 'Account Manager' OR userRole.userType = 'Sales Manager' OR userRole.userType = 'Recruiter')";
		}
		
	    String query = "from UserRole userRole where userRole.orgLocations = :orgLocationsId and userRole.userState != 'INACTIVE' and userRole.user.id <> :userId "+subQuery+" ";
	    List<UserRole> userRoleList = session.createQuery(query).setLong( "orgLocationsId", orgLocationsId).setLong( "userId", userId).setCacheable(true).list();
        return userRoleList;
        
	}*/
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Long> getAllUserIdsByReportingManagerId(Long reportingManagerId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select userRole.user from UserRole userRole where userRole.reportingManager.id = :reportingManagerId and userRole.userState != 'INACTIVE' " ;
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
		
	public UserRole getUserRoleByUserId(Long userId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserRole userRole where userRole.user.id=:userId";
		List<UserRole> userRoleList = session.createQuery(query.toString()).setLong("userId", userId).setCacheable(true).list();
		return userRoleList.get(0);
	}

	
}
