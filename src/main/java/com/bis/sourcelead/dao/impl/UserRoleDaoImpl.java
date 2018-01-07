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
import com.bis.sourcelead.dto.ProjectTasksDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.dto.UserRoleDTO;
import com.bis.sourcelead.entity.Connections;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.ConnectionStatus;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CalendarUtil;
import com.mchange.v1.util.StringTokenizerUtils;

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
		 String query = "from ProjectMembers pm left join fetch pm.projects proj left join fetch pm.employee emp  where emp IS NOT NULL and proj.invoiceFrequency = :invoiceFrequency";
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
	public HashSet<ProjectMembers> getAllUserPrevoiusWeekTimeSheetSubmissionStatusByApproverUserId(String invoiceFrequency,Long approverUserId,Long locationId) throws Exception {
		 Session session = this.sessionFactory.getCurrentSession();
		 List<ProjectMembers> projectMemberList = null;
		 if(StringUtils.isEmpty(invoiceFrequency)){
			invoiceFrequency = "Weekly";
		 }
		 String query = "from ProjectMembers pm left join fetch pm.projects proj left join fetch pm.projectClientsId pc left join fetch pc.orgLocationsId loc left join fetch pm.projectMemberCandidteId can left join fetch pm.employee emp where loc.id = :locationId and proj.invoiceFrequency = :invoiceFrequency and proj.id IN (select hierarchy.projectId.id from ProjectApproverHierarchy hierarchy where hierarchy.approverUserId.id = :approverUserId) ";
		 projectMemberList = session.createQuery(query).setString("invoiceFrequency", invoiceFrequency).setLong("locationId", locationId).setLong("approverUserId", approverUserId).setCacheable(true).list(); 
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
			String userState, Long userId,String loginUserRole,String userCode,Integer offset,String viewType) throws Exception {

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
		if(viewType != null && viewType.equalsIgnoreCase(Constants.VIEW_TYPE)){
			return list;
		}else{
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
		String query = "from UserRole userRole left join fetch userRole.roles role left join fetch userRole.user left join fetch userRole.orgLocations where userRole.user.id = :userId and userRole.userState != 'INACTIVE' and userRole.isMailSent = 'Y' ";
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
	public UserRole getUserRoleById(Long id) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		List<UserRole> userRole = new ArrayList<UserRole>();
		logger.debug("getUserByUserId By UserId start");
		userRole = session
				.createQuery("from UserRole userRole left join fetch userRole.orgLocations left join fetch userRole.roles where userRole.id = :id  ")
				.setLong("id", id).setCacheable(true).list();
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
	
	@Transactional(rollbackFor = { Exception.class })
	public HashSet<ProjectMembers> getAllUserPrevoiusFridayTimeSheetSubmissionStatus(String invoiceFrequency,String dateFormat) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		Calendar cal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
		cal.setLenient(false);
		cal.setTime(new Date());
		
		Calendar startMonday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON",dateFormat);
		
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

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public UserRole getUserRoleWithoutAdminsByUserIdAndLocationId(Long userId, Long locationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<UserRole> userRole = new ArrayList<UserRole>();
		userRole = session.createQuery("from UserRole userRole left join fetch userRole.orgLocations left join fetch userRole.roles  "
						+ "left join fetch userRole.user where userRole.user.id = :userId and userRole.orgLocations.id = :locationId and userRole.userType <> 'Admin' GROUP BY  userRole.userType")
				.setLong("userId", userId).setLong("locationId", locationId).setCacheable(true).list();
		logger.debug("getUserRoleByUserIdAndLocationId method ends");
		if (userRole.size() > 0) {
			return userRole.get(0);
		} else {
			return null;
		}
	}
	 
    @Override
	@Transactional
	public UserRole getUserRoleByVerficationUUID(String uuid)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<UserRole> userRole = new ArrayList<UserRole>();
		userRole = session.createQuery("from UserRole userRole where userRole.verificationUUID = :uuid").setString("uuid", uuid).setCacheable(true).list();
		if (userRole.size() > 0) {
			return userRole.get(0);
		}
		else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<UserRole> getDynamicUsersListByRoleAndNameAndLocationId(UserDTO userDTO,Long roleId, Long locationId, String userState, Long userId, String loginUserRole, String userCode) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		ResponseList<UserRole> response = new ResponseList<>();
		List userRoleList = null;
		List<Long> countlist = null;
		Integer filterCount = 0;
		String subQuery = "";
		String subQuery2 ="";
		Map<String, Object> params = new HashMap<String,Object>();
		StringBuilder query1 = new StringBuilder("");
		if(userDTO.getSearchValue() !=null && !"".equalsIgnoreCase(userDTO.getSearchValue())) {
		 query1.append("and( (userRole.user.firstName like '" + userDTO.getSearchValue() + "%' OR userRole.user.lastName like '%" + userDTO.getSearchValue() + "' )")
			   .append("OR (concat(userRole.user.firstName,userRole.user.lastName) like '%" + userDTO.getSearchValue().replaceAll("[\\s]+", "").trim() + "%')" )
		       .append("OR userRole.user.email like '%" + userDTO.getSearchValue() + "%' ")
			   .append("OR userRole.user.mobilePhone like '%" + userDTO.getSearchValue() + "%' ")
			   .append("OR userRole.roles.displayName like '%" + userDTO.getSearchValue() + "%' ")
			   .append("OR userRole.reportingManager.firstName like '" + userDTO.getSearchValue() + "%' OR userRole.reportingManager.lastName like '%" + userDTO.getSearchValue() + "' ")
			   .append("OR (concat(userRole.reportingManager.firstName,userRole.reportingManager.lastName) like '%" + userDTO.getSearchValue().replaceAll("[\\s]+", "").trim() + "%')" )
			   .append("OR userRole.user.id IN(Select emp.user.id from Employees emp where emp.userEmploymentType like '%" + userDTO.getSearchValue() + "%')  ")
			   .append("OR userRole.userState like '%" + userDTO.getSearchValue() + "%' )");
					
		}
//		role.getReportingManager().getFirstName()
		if(roleId != null) {
			 subQuery = " and userRole.roles.id = :roleId ";
			params.put("roleId",roleId);
		}
		if(!"".equalsIgnoreCase(userState)){
			subQuery2 = " and userRole.userState =:userState ";
			params.put("userState",userState);
		} 
			String listQuery = "from UserRole userRole where userRole.orgLocations.id = :locationId and userRole.user.id <> :userId and userRole.user.id <> '1'  "+subQuery2+""
					+ " and userRole.roles.roleName <> 'ROLE_CROWDSOURCING_PROFESSIONAL' and userRole.roles.roleName <> 'ROLE_CROWDSOURCING_RECRUITER' "+subQuery+"" ;
			Query query = session.createQuery(listQuery+query1);
			if(roleId != null && !"".equalsIgnoreCase(userState)) {
				userRoleList = query.setLong("locationId", locationId).setLong("userId", userId).setLong("roleId", roleId).setString("userState", userState).setFirstResult(userDTO.getStart()).setMaxResults(userDTO.getLength()).setCacheable(true).list();	
			}else if(roleId != null && "".equalsIgnoreCase(userState)) {
				userRoleList = query.setLong("locationId", locationId).setLong("userId", userId).setLong("roleId", roleId).setFirstResult(userDTO.getStart()).setMaxResults(userDTO.getLength()).setCacheable(true).list();	
			}  else if(!"".equalsIgnoreCase(userState)){
				userRoleList = query.setLong("locationId", locationId).setLong("userId", userId).setString("userState", userState).setFirstResult(userDTO.getStart()).setMaxResults(userDTO.getLength()).setCacheable(true).list();
			}else {
				userRoleList = query.setLong("locationId", locationId).setLong("userId", userId).setFirstResult(userDTO.getStart()).setMaxResults(userDTO.getLength()).setCacheable(true).list();
			}
			
			
			String queryCount = "Select count (*) from UserRole userRole where userRole.orgLocations.id = :locationId and userRole.user.id <> :userId and userRole.user.id <> '1' "+subQuery2+""
					+ " and userRole.roles.roleName <> 'ROLE_CROWDSOURCING_PROFESSIONAL' and userRole.roles.roleName <> 'ROLE_CROWDSOURCING_RECRUITER' "+subQuery+"" ;
			Query query2 = session.createQuery(queryCount+query1);
			if(roleId != null && !"".equalsIgnoreCase(userState)) {
				countlist =query2.setLong("locationId", locationId).setLong("userId", userId).setString("userState", userState).setLong("roleId", roleId).setFirstResult(userDTO.getStart()).setMaxResults(userDTO.getLength()).setCacheable(true).list();
			}else if(roleId != null && "".equalsIgnoreCase(userState)) {
				countlist =query2.setLong("locationId", locationId).setLong("userId", userId).setLong("roleId", roleId).setFirstResult(userDTO.getStart()).setMaxResults(userDTO.getLength()).setCacheable(true).list();
			} else if(!"".equalsIgnoreCase(userState)){
				countlist =query2.setLong("locationId", locationId).setLong("userId", userId).setString("userState", userState).setFirstResult(userDTO.getStart()).setMaxResults(userDTO.getLength()).setCacheable(true).list();
			}else {
				countlist =query2.setLong("locationId", locationId).setLong("userId", userId).setFirstResult(userDTO.getStart()).setMaxResults(userDTO.getLength()).setCacheable(true).list();
			}
			  
		if(query1.toString() != null && !query1.toString().isEmpty()){
			filterCount = countlist.size();
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
			response.setData(userRoleList);
			return response;
			
	}

	@Override
	@Transactional
	public List<UserRoleDTO> getUserListForConnections(String searchByName, Integer offset,Long userId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<UserRoleDTO> userRoleDTOList = new ArrayList<>();
		String searchByValue = searchByName.trim();
		StringBuilder subQuery = new StringBuilder("");
		if(searchByValue != null && !"".equalsIgnoreCase(searchByValue)) {
			subQuery.append(" and( (userRole.user.firstName like '" + searchByValue + "%' OR userRole.user.lastName like '%" + searchByValue + "' )")
			   .append("OR (concat(userRole.user.firstName,userRole.user.lastName) like '%" + searchByValue.replaceAll("[\\s]+", "").trim() + "%')" )
			   .append("OR (concat(userRole.orgLocations.orgLocationName) like '%" + searchByValue + "%')" )
			   .append("OR userRole.roles.displayName like '%" + searchByValue + "%' )");
		}
		
		
		String query = "from UserRole userRole left join fetch userRole.user left join fetch userRole.roles left join fetch userRole.orgLocations "
				+ " where userRole.isPrimary = '1' and userRole.userState = 'ACTIVE' and userRole.user.id <> :userId "+subQuery.toString()+"  ";
		List<UserRole> userRoleList = session.createQuery(query.toString()).setLong("userId", userId).setCacheable(true).list();
		
		if(userRoleList.size() >= offset){
			if(userRoleList.size() > offset + 5){
				userRoleList = userRoleList.subList(offset, (offset+5));
			}else{
				userRoleList = userRoleList.subList(offset, (  offset + (userRoleList.size() - offset)));
			}
			
			if(userRoleList != null && userRoleList.size() > 0) {
				for(UserRole userRole : userRoleList) {
					UserRoleDTO userRoleDTO = new UserRoleDTO();
					String queryForStatus = "from Connections connections where (connections.fromUser.id = :fromId and connections.toUser.id = :toId) or (connections.fromUser.id = :toId and connections.toUser.id = :fromId)";
					Connections connections = (Connections) session.createQuery(queryForStatus).setLong("fromId", userId).setLong("toId", userRole.getUser().getId()).uniqueResult();
					if(connections != null) {
						if(connections.getToUser().getId().equals(userId) && connections.getConnectionStatus().toString().equals(ConnectionStatus.REQUEST_SENT.toString())) {
							userRoleDTO.setUserState("Request_received");
						}else if(connections.getFromUser().getId().equals(userId) && connections.getConnectionStatus().toString().equals(ConnectionStatus.REQUEST_SENT.toString())) {
							userRoleDTO.setUserState("Request_sent");
						}else {
							userRoleDTO.setUserState("connected");
						}
					}
					StringBuilder userName = new StringBuilder();
					if(userRole.getUser().getLastName() != null) {
						userName = userName.append(userRole.getUser().getFirstName()).append(" ").append(userRole.getUser().getLastName());
					}else {
						userName = userName.append(userRole.getUser().getFirstName());
					}
					String queryMutual = "select count(connections) from Connections connections where (connections.fromUser.id = :userId or connections.toUser.id = :userId) and connections.connectionStatus = :connectionStatus";
					Long count = (Long) session.createQuery(queryMutual).setLong("userId", userRole.getUser().getId()).setString("connectionStatus", ConnectionStatus.CONNECTED.toString()).setCacheable(true).uniqueResult();
					userRoleDTO.setMutualCount(count);
					userRoleDTO.setUserName(userName.toString());
					userRoleDTO.setUserRoleName(userRole.getRoles().getDisplayName());
					userRoleDTO.setOrgName(userRole.getOrgLocations().getOrgLocationName());
					userRoleDTO.setUserId(userRole.getUser().getId());
					userRoleDTO.setUserCode(userRole.getUser().getUserCode());
					userRoleDTO.setFileUrl(userRole.getUser().getFileUrl());
					userRoleDTOList.add(userRoleDTO);
				}
			}
			return  userRoleDTOList;
		}else{
			return  userRoleDTOList;
		}
		
	}
	
	/*
     @SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<UserRole> getUsersListByRoleAndNameAndLocationId1(Long roleId, String userName, Long locationId,
			String userState, Long userId,String loginUserRole,String userCode,Integer offset,String viewType) throws Exception {

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
		
		 if(!"".equalsIgnoreCase(withoutWhitespace) && !withoutWhitespace.contains(" ")){
			 subQuery3 = " and ((userRole.user.firstName LIKE '" + withoutWhitespace + "%') OR  (userRole.user.lastName LIKE '"+ withoutWhitespace + "%') OR (userRole.user.email LIKE '" + withoutWhitespace + "'))";
			 subquerys.add(subQuery3);
		
		 }else{
			 subQuery3 =""; 
		 }
		 
		if(!"".equalsIgnoreCase(userState)){
			subQuery4 = " and userRole.userState = '" + userState + "'";
			 subquerys.add(subQuery4);
		}else{
			 subQuery4 ="";
		}
		
		 
		if(roleId != null){
			subQuery5 = " and userRole.roles.id = :roleId";
			params.put("roleId",roleId);
			subquerys.add(subQuery5);
		}else{
			 subQuery5 ="";
		}
		
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
		if(viewType != null && viewType.equalsIgnoreCase(Constants.VIEW_TYPE)){
			return list;
		}else{
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
	} */
}
