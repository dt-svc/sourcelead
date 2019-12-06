package com.bis.sourcelead.dao.impl;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
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
import com.bis.sourcelead.dao.ConnectionsDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.dto.ConnectionsDTO;
import com.bis.sourcelead.dto.OrgRelationDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.dto.UserRoleDTO;
import com.bis.sourcelead.dto.UsersUsageReportDTO;
import com.bis.sourcelead.entity.AssociateDepartmentView;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserIdView;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ReportsUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Repository
public class UserRoleDaoImpl implements UserRoleDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private ConnectionsDao connectionsDao;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private ReportsUtil reportsUtil;

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
	public HashSet<ProjectMembers> getAllUserPrevoiusWeekTimeSheetSubmissionStatus(String invoiceFrequency,
			Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<ProjectMembers> projectMemberList = null;
		if (StringUtils.isEmpty(invoiceFrequency)) {
			invoiceFrequency = "Weekly";
		}
		String query = "from ProjectMembers pm left join fetch pm.projectMemberCandidteId can left join fetch pm.projects proj left join fetch can.consultantUser user  where  user IS NOT NULL and proj.isProjectForTimeSheets = 'Y' and proj.invoiceFrequency = :invoiceFrequency  and can.orgLocationId.id = :orgLocationId";
		projectMemberList = session.createQuery(query).setString("invoiceFrequency", invoiceFrequency)
				.setLong("orgLocationId", orgLocationId).list();
		HashSet<ProjectMembers> projectMemberSet = null;
		if (projectMemberList != null && !projectMemberList.isEmpty()) {
			logger.debug(
					"The Total Employees which are not submitted the Time sheets within a week are : ***********************"
							+ projectMemberList.size());
			projectMemberSet = new HashSet<>(projectMemberList);
		}
		return projectMemberSet;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public HashSet<ProjectMembers> getAllProjectMembersByProjectIdForMissingTSReports(Long projectId, Long locationId,
			Long userId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<ProjectMembers> projectMemberList = null;
		String query = "from ProjectMembers pm left join fetch pm.projects proj left join fetch pm.projectMemberCandidteId can left join fetch can.parentCandidateId left join fetch can.orgLocationId loc left join fetch can.masterCandidateId mc left join fetch pm.employee emp  where  proj.id = :projectId and loc.id = :locationId "
				+ " and ( "
				+ "  (proj.id IN ( select pah.projectId from ProjectApproverHierarchy pah where pah.approverUserId =:userId)) "
				+ "   OR "
				+ ":userId IN (select userRole.user.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.userState = 'ACTIVE' and userRole.orgLocations.id = loc.id)"
				+ ") ";
		projectMemberList = session.createQuery(query).setLong("projectId", projectId).setLong("locationId", locationId)
				.setLong("userId", userId).list();
		HashSet<ProjectMembers> projectMemberSet = null;
		if (projectMemberList != null && !projectMemberList.isEmpty()) {
			projectMemberSet = new HashSet<>(projectMemberList);
		}
		return projectMemberSet;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public HashSet<ProjectMembers> getAllUserPrevoiusWeekTimeSheetSubmissionStatusByApproverUserId(
			String invoiceFrequency, Long approverUserId, Long locationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<ProjectMembers> projectMemberList = null;
		if (StringUtils.isEmpty(invoiceFrequency)) {
			invoiceFrequency = "Weekly";
		}
		String query = "from ProjectMembers pm left join fetch pm.projects proj left join fetch pm.projectClientsId pc left join fetch pc.orgLocationsId loc left join fetch pm.projectMemberCandidteId can left join fetch pm.employee emp where loc.id = :locationId and proj.invoiceFrequency = :invoiceFrequency and proj.id IN (select hierarchy.projectId.id from ProjectApproverHierarchy hierarchy where hierarchy.approverUserId.id = :approverUserId) ";
		projectMemberList = session.createQuery(query).setString("invoiceFrequency", invoiceFrequency)
				.setLong("locationId", locationId).setLong("approverUserId", approverUserId).list();
		HashSet<ProjectMembers> projectMemberSet = null;
		if (projectMemberList != null && !projectMemberList.isEmpty()) {
			logger.debug(
					"The Total Employees which are not submitted the Time sheets within a week are : ***********************"
							+ projectMemberList.size());
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
		List<UserRole> userList = session.createQuery(query.toString()).setDate("startDate", previousDayDate).list();
		logger.debug(
				"The Total Employees which are not submitted the Time sheets within a week are : " + userList.size());
		HashSet<UserRole> userSet = null;
		if (userList != null && !userList.isEmpty()) {
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
				if (StringUtils.isBlank(userRole.getUuid()) || userRole.getUuid() == null) {
					userRole.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
				}
				session.saveOrUpdate(userRole);
			}
			logger.debug("UserRole Added Successfully");
		} catch (Exception exception) {
			StringBuilder message = new StringBuilder(MessageFormat.format(duplicateValue, "UserLoginAttempts"));
			throw new SourceLeadConstraintViolationException(message.toString() + exception.getCause().toString());
		}
		return userRole;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public UserRole getLoginUserPrimaryRole(Long userId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		List<UserRole> userRole = new ArrayList<UserRole>();
		logger.debug("getPrimaryRole By UserId start");
		userRole = session.createQuery(
				"from UserRole userRole left join fetch userRole.orgLocations orgLoc left join fetch orgLoc.org where userRole.user.id=:userId and userRole.isPrimary = '1' ")
				.setLong("userId", userId).list();
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
		userRole = session.createQuery(
				"from UserRole userRole where userRole.user.id=:userId and userRole.isPrimary = '0' and userRole.userState != 'INACTIVE' ")
				.setLong("userId", userId).list();
		logger.debug("getNotPrimaryUser By UserId start");
		if (userRole.size() > 0) {
			return userRole.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })

	public List<UserRole> getUsersListByLocationId(Long locationId, String userState, Long userId, String loginUserRole,
			String userCode) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();

		String subQuery = "";
		if (!isNullOrEmpty(userState)) {
			subQuery = "and userRole.userState = '" + userState + "'";
		}

		/*
		 * String subQuery1 = ""; if(!loginUserRole.equalsIgnoreCase("ROLE_ADMIN") &&
		 * !loginUserRole.equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")){ subQuery1 =
		 * "and userRole.createdBy = '" + userCode + "' "; }
		 */

		return session.createQuery(
				"Select userRole from UserRole userRole where userRole.orgLocations.id = :locationId and userRole.user.id <> :userId and userRole.user.id <> '1' "
						+ subQuery + " ")
				.setLong("locationId", locationId).setLong("userId", userId).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<UserRole> getUsersListByRoleAndLocationId(Long roleId, Long locationId, String userState, Long userId,
			String loginUserRole, String userCode) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();

		String subQuery = "";
		if (!isNullOrEmpty(userState)) {
			subQuery = "and userRole.userState = '" + userState + "'";
		}

		/*
		 * String subQuery1 = ""; if(!loginUserRole.equalsIgnoreCase("ROLE_ADMIN") &&
		 * !loginUserRole.equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")){ subQuery1 =
		 * "and userRole.createdBy = '" + userCode + "' "; }
		 */

		return session.createQuery("Select userRole from UserRole userRole where userRole.roles.id = :roleId "
				+ "and userRole.orgLocations.id = :locationId and userRole.user.id <> :userId and userRole.user.id <> '1' "
				+ subQuery + "  ").setLong("roleId", roleId).setLong("locationId", locationId).setLong("userId", userId)
				.list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<UserRole> getUsersListByUserNameAndLocationId(String userName, Long locationId, String userState,
			Long userId, String loginUserRole, String userCode) throws Exception {

		String subQuery = "";
		if (!isNullOrEmpty(userState)) {
			subQuery = "and userRole.userState = '" + userState + "'";
		}

		/*
		 * String subQuery1 = ""; if(!loginUserRole.equalsIgnoreCase("ROLE_ADMIN") &&
		 * !loginUserRole.equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")){ subQuery1 =
		 * "and userRole.createdBy = '" + userCode + "' "; }
		 */

		Session session = this.sessionFactory.getCurrentSession();
		return session.createQuery("Select userRole from UserRole userRole where " + "((userRole.user.firstName LIKE '"
				+ userName + "%') OR (userRole.user.lastName LIKE '" + userName + "%') OR (userRole.user.email LIKE '"
				+ userName + "')) "
				+ "and userRole.orgLocations.id = :locationId and userRole.user.id <> :userId and userRole.user.id <> '1' "
				+ subQuery + "  ").setLong("locationId", locationId).setLong("userId", userId).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<UserRole> getUsersListByRoleAndNameAndLocationId(Long roleId, String userName, Long locationId,
			String userState, Long userId, String loginUserRole, String userCode, Integer offset, String viewType)
			throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		Map<String, Object> params = new HashMap<String, Object>();

		ArrayList<String> subquerys = new ArrayList<String>();

		String from = "from UserRole userRole where ";

		String subQuery = "userRole.orgLocations.id = :locationId and userRole.user.id <> :userId and userRole.user.id <> '1' "
				+ " and userRole.roles.roleName <> 'ROLE_CROWDSOURCING_PROFESSIONAL' and userRole.roles.roleName <> 'ROLE_CROWDSOURCING_RECRUITER' ";
		params.put("locationId", locationId);
		params.put("userId", userId);
		subquerys.add(subQuery);

		String subQuery3 = "";
		String subQuery4 = "";
		String subQuery5 = "";
		String firstWord = "";
		String lastWord = "";
		String subQuery8 = "";

		String s1 = userName;
		String withoutWhitespace = s1.trim();
		if (!"".equalsIgnoreCase(withoutWhitespace) && withoutWhitespace.contains(" ")) {

			String[] words = withoutWhitespace.split("\\s");
			firstWord = words[0];
			lastWord = words[1];

		}

		if (!firstWord.equals("") && !lastWord.equals("")) {
			subQuery8 = " and ((userRole.user.firstName LIKE '" + firstWord + "%') OR (userRole.user.firstName LIKE '"
					+ lastWord + "%') OR  (userRole.user.lastName LIKE '" + firstWord
					+ "%') OR  (userRole.user.lastName LIKE '" + lastWord + "%'))";
			subquerys.add(subQuery8);
		}

		else {
			subQuery8 = "";
		}

		/*
		 * to check the if the search parameter is given or not
		 */

		if (!"".equalsIgnoreCase(withoutWhitespace) && !withoutWhitespace.contains(" ")) {
			subQuery3 = " and ((userRole.user.firstName LIKE '" + withoutWhitespace
					+ "%') OR  (userRole.user.lastName LIKE '" + withoutWhitespace
					+ "%') OR (userRole.user.email LIKE '" + withoutWhitespace + "'))";
			subquerys.add(subQuery3);

		} else {
			subQuery3 = "";
		}

		/*
		 * to check the if the UserState i,e Active,In Active or Not LoggedInn parameter
		 * is given or not
		 */
		if (!"".equalsIgnoreCase(userState)) {
			subQuery4 = " and userRole.userState = '" + userState + "'";
			subquerys.add(subQuery4);
		} else {
			subQuery4 = "";
		}

		/*
		 * to check the if the Role Search id Given Or Not
		 */
		if (roleId != null) {
			subQuery5 = " and userRole.roles.id = :roleId";
			params.put("roleId", roleId);
			subquerys.add(subQuery5);
		} else {
			subQuery5 = "";
		}

		/*
		 * appending each and every subquery to where Condition
		 */
		StringBuilder whereCondition = new StringBuilder();
		for (String s : subquerys) {
			whereCondition.append(s);
		}

		Query query = session.createQuery(from + " " + whereCondition + " ORDER BY userRole.id DESC");
		for (String str : query.getNamedParameters()) {
			if (str.equalsIgnoreCase("locationId") || str.equalsIgnoreCase("userId")) {
				query.setLong(str, (long) params.get(str));
			} else {
				query.setParameter(str, params.get(str));
			}

		}
		List<UserRole> list = null;
		list = query.list();
		if (viewType != null && viewType.equalsIgnoreCase(Constants.VIEW_TYPE)) {
			return list;
		} else {
			if (query.list().size() >= offset) {
				if (query.list().size() > offset + 10) {
					return list.subList(offset, (offset + 10));
				} else {
					return list.subList(offset, (offset + (query.list().size() - offset)));
				}
			} else {
				return null;
			}
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<UserRole> getUsersListByUserStateAndLocationId(String userState, Long locationId, Long userId,
			String loginUserRole, String userCode) throws Exception {

		/*
		 * String subQuery = ""; if(!loginUserRole.equalsIgnoreCase("ROLE_ADMIN") &&
		 * !loginUserRole.equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")){ subQuery =
		 * "and userRole.createdBy = '" + userCode + "' "; }
		 */

		Session session = this.sessionFactory.getCurrentSession();
		return session.createQuery(
				"Select userRole from UserRole userRole where userRole.orgLocations.id = :locationId and userRole.user.id <> :userId and "
						+ " userRole.user.id <> '1' and userRole.userState = :userState  ")
				.setLong("locationId", locationId).setString("userState", userState).setLong("userId", userId).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<UserRole> getUsersBasedOnUserRoles() throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserRole userRole left join fetch userRole.roles role left join fetch userRole.user where role.roleName = 'ROLE_ADMIN'";
		List<UserRole> userRoleList = session.createQuery(query.toString()).list();
		return userRoleList;

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<UserRole> getApproverAssignedUsers(List<Long> projectIds, String userState, String searchByName)
			throws Exception {
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

		List<UserRole> userRoleList = session.createQuery(query.toString()).setLong("roleId", roleId)
				.setParameterList("projectIds", projectIds).list();
		return userRoleList;

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Long getUsersCountByOrgLocationID(Long orgLocationId) throws Exception {
		logger.debug("..getUsersCountByOrgLocationID() Method Started..");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select count(*) from UserRole userRole where userRole.orgLocations.id = :orgLocationId ";
		List<Long> results = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		logger.debug("..getUsersCountByOrgLocationID() Method Ended..");
		return results.get(0);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean isUserAdmin(Long userId) {

		Session session = this.sessionFactory.getCurrentSession();
		List<UserRole> userRole = new ArrayList<UserRole>();
		logger.debug("isUserAdmin By UserId start");
		userRole = session.createQuery(
				"from UserRole userRole left join fetch userRole.roles role left join fetch userRole.user  where (role.roleName = 'ROLE_ADMIN' OR  role.roleName = 'ROLE_SOURCELEAD_ADMIN') and userRole.user =:userId")
				.setLong("userId", userId).list();

		logger.debug("isUserAdmin By UserId End");

		Boolean flag = false;
		if (userRole.size() > 0) {
			flag = true;
		}
		return flag;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<UserRole> getAllUsersBasedOrgLocationId(Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserRole userRole left join fetch userRole.orgLocations orgLocation  where orgLocation.id =:orgLocationId";
		List<UserRole> userRoleList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId)
				.list();
		return userRoleList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean isUserAdminByOrgLocationId(Long userId, Long orgLocationId) {

		Session session = this.sessionFactory.getCurrentSession();
		List<UserRole> userRole = new ArrayList<UserRole>();
		logger.debug("isUserAdmin By UserId start");
		userRole = session.createQuery(
				"from UserRole userRole left join fetch userRole.roles role left join fetch userRole.user  where (role.roleName = 'ROLE_ADMIN' OR  role.roleName = 'ROLE_SOURCELEAD_ADMIN') and userRole.user =:userId and userRole.orgLocations.id = :orgLocationId")
				.setLong("userId", userId).setLong("orgLocationId", orgLocationId).list();

		logger.debug("isUserAdmin By UserId End");

		Boolean flag = false;
		if (userRole.size() > 0) {
			flag = true;
		}
		return flag;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public UserRole getUserDetailsByEmailAndLocationId(String email, Long locationId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		List<UserRole> userRole = new ArrayList<UserRole>();
		logger.debug("getUserDetailsByEmailAndLocationId method  start");
		userRole = session.createQuery(
				"from UserRole userRole left join fetch userRole.orgLocations left join fetch userRole.roles  "
						+ "left join fetch userRole.user where userRole.user.email = :email and userRole.orgLocations.id = :locationId")
				.setString("email", email).setLong("locationId", locationId).list();
		logger.debug("getUserDetailsByEmailAndLocationId method ends");
		if (userRole.size() > 0) {
			return userRole.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public UserRole getUserRoleByUserIdAndLocationId(Long userId, Long locationId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		List<UserRole> userRole = new ArrayList<UserRole>();
		logger.debug("getUserRoleByUserIdAndLocationId method  start");
		userRole = session.createQuery(
				"from UserRole userRole left join fetch userRole.orgLocations left join fetch userRole.orgLocations.org left join fetch userRole.orgLocations.org left join fetch userRole.roles left join fetch userRole.reportingManager "
						+ " left join fetch userRole.gradeId left join fetch userRole.resourceManager left join fetch userRole.user where userRole.user.id = :userId and userRole.orgLocations.id = :locationId")
				.setLong("userId", userId).setLong("locationId", locationId).list();
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
		userRole = session
				.createQuery(
						"from UserRole userRole where userRole.user.id = :userId and userRole.userState = 'ACTIVE' ")
				.setLong("userId", userId).list();
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
		String query = "select userRole.user from UserRole userRole where userRole.reportingManager.id = :reportingManagerId and userRole.userState != 'INACTIVE' and userRole.userState != 'NOT_LOGGEDIN' ";
		List<Long> userIds = session.createQuery(query).setLong("reportingManagerId", reportingManagerId).list();
		return userIds;

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<UserRole> getUserSpocsListByUserId(Long userId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserRole userRole left join fetch userRole.roles role left join fetch userRole.user left join fetch userRole.orgLocations where userRole.user.id = :userId and userRole.userState != 'INACTIVE' ";
		List<UserRole> userRoleList = session.createQuery(query.toString()).setLong("userId", userId).list();
		return userRoleList;

	}

	/**
	 * this method is used for elastic search
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<UserRole> getUsersListByUserId(Long userId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserRole userRole left join fetch userRole.roles role left join fetch userRole.user "
				+ " left join fetch role.roleHierarchy where userRole.user.id = :userId ";
		List<UserRole> userRoleList = session.createQuery(query.toString()).setLong("userId", userId).list();
		return userRoleList;

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public UserRole getUserByUserId(Long userId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		List<UserRole> userRole = new ArrayList<UserRole>();
		logger.debug("getUserByUserId By UserId start");
		userRole = session.createQuery("from UserRole userRole where userRole.user.id = :userId  ")
				.setLong("userId", userId).list();
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
		userRole = session.createQuery(
				"from UserRole userRole left join fetch userRole.user left join fetch userRole.user.linkUserId left join fetch userRole.orgLocations orgLoc left join fetch orgLoc.org left join fetch userRole.roles where userRole.id = :id  ")
				.setLong("id", id).list();
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
		List<UserRole> userRoleList = session.createQuery(query.toString()).setLong("userId", userId).list();
		return userRoleList.get(0);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public UserRole getUserDetailsByUserIdAndLocationId(Long userId, Long locationId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		List<UserRole> userRole = new ArrayList<UserRole>();
		logger.debug("getUserDetailsByEmailAndLocationId method  start");
		userRole = session.createQuery(
				"from UserRole userRole left join fetch userRole.orgLocations orgLoc left join fetch orgLoc.org left join fetch userRole.roles  "

						+ "left join fetch userRole.resourceManager left join fetch userRole.user left join fetch userRole.reportingManager left join fetch userRole.user.linkUserId  where userRole.user.id = :userId and userRole.orgLocations.id = :locationId")

				.setLong("userId", userId).setLong("locationId", locationId).list();
		logger.debug("getUserDetailsByEmailAndLocationId method ends");
		if (userRole.size() > 0) {
			return userRole.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public UserRole getProjectmemberUserDetailsByUserIdAndLocationId(Long userId, Long locationId,
			String designationType) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<UserRole> userRole = new ArrayList<UserRole>();
		logger.debug("getUserDetailsByEmailAndLocationId method  start");
		String globalUserQuery = "";
		if (StringUtils.isNotEmpty(designationType) && designationType.equalsIgnoreCase(Constants.OFFSHORE_C2C)) {
			globalUserQuery = " and (userRole.orgLocations.org.id IN(select ol.org.id from OrgLocations ol where ol.id = :locationId) and userRole.isPrimary = '1')";
		} else {
			globalUserQuery = " and userRole.orgLocations.id = :locationId ";
		}
		userRole = session.createQuery(
				"from UserRole userRole left join fetch userRole.orgLocations orgLoc left join fetch orgLoc.org left join fetch userRole.roles  "
						+ " left join fetch userRole.resourceManager left join fetch userRole.user left join fetch userRole.reportingManager left join fetch userRole.user.linkUserId  "
						+ " where userRole.user.id = :userId " + globalUserQuery)

				.setLong("userId", userId).setLong("locationId", locationId).list();
		logger.debug("getUserDetailsByEmailAndLocationId method ends");
		if (userRole.size() > 0) {
			return userRole.get(0);
		} else {
			return getLoginUserPrimaryRole(userId);
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public HashSet<ProjectMembers> getAllUserPrevoiusFridayTimeSheetSubmissionStatus(String invoiceFrequency,
			String dateFormat) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		Calendar cal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
		cal.setLenient(false);
		cal.setTime(new Date());

		Calendar startMonday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON", dateFormat);

		Calendar previousFriday = CalendarUtil.getDateIncrementByincrement(startMonday, -3);
		Date previousDateFri = previousFriday.getTime();
		java.sql.Date previousDayDateFri = new java.sql.Date(previousDateFri.getTime());

		Calendar previousMonday = CalendarUtil.getDateIncrementByincrement(startMonday, -42);
		Date previousDate = previousMonday.getTime();
		java.sql.Date previousDayDate = new java.sql.Date(previousDate.getTime());

		// Date currentWeekMondayDate = CalendarUtil.getCurrentWeekDay("MON");
		// java.sql.Date currentMondayDate = new
		// java.sql.Date(currentWeekMondayDate.getTime());
		List<ProjectMembers> projectMemberList = null;
		if (invoiceFrequency != null && !"".equalsIgnoreCase(invoiceFrequency)) {
			// String query = "from UserRole userRole left join fetch userRole.user user
			// left join fetch userRole.orgLocations orglocation where user.id IN (select
			// emp.user from Employees emp where emp.id IN(select pm.employee from
			// ProjectMembers pm where pm.projects.invoiceFrequency = :invoiceFrequency and
			// pm.projects.orgLocationsId.id = orglocation.id and pm.id IN(select
			// ts.projectMembers from TimesheetSubmissions ts where (ts.startDate >
			// :startDate and ts.startDate < :endDate ) and ts.status ='1' and ts.Id
			// IN(select sah.submissionId from SubmissionApproverHierarchy sah where
			// sah.submissionStatus = 'NEW'))))";
			String query = "from ProjectMembers pm left join fetch pm.projects proj left join fetch pm.employee emp where proj.invoiceFrequency = :invoiceFrequency and pm.id IN(select ts.projectMembers from TimesheetSubmissions ts where  (ts.startDate > :startDate and ts.startDate < :endDate ) and ts.status ='1' and ts.Id IN(select sah.submissionId from SubmissionApproverHierarchy sah where sah.submissionStatus = 'NEW'))";
			projectMemberList = session.createQuery(query).setDate("startDate", previousDayDate)
					.setDate("endDate", previousDateFri).setString("invoiceFrequency", invoiceFrequency).list();
		} else {
			String query = "from ProjectMembers pm left join fetch pm.projects proj left join fetch pm.employee emp where pm.id IN(select ts.projectMembers from TimesheetSubmissions ts where  (ts.startDate > :startDate and ts.startDate < :endDate ) and ts.status ='1' and ts.Id IN(select sah.submissionId from SubmissionApproverHierarchy sah where sah.submissionStatus = 'NEW'))";
			// String query = "from UserRole userRole left join fetch userRole.user user
			// left join fetch userRole.orgLocations orglocation where user.id IN (select
			// emp.user from Employees emp where emp.id IN(select pm.employee from
			// ProjectMembers pm where pm.projects.orgLocationsId.id = orglocation.id and
			// pm.id IN(select ts.projectMembers from TimesheetSubmissions ts where
			// (ts.startDate > :startDate and ts.startDate < :endDate ) and ts.status ='1'
			// and ts.Id IN(select sah.submissionId from SubmissionApproverHierarchy sah
			// where sah.submissionStatus = 'NEW'))))";
			projectMemberList = session.createQuery(query).setDate("startDate", previousDayDate)
					.setDate("endDate", previousDateFri).list();
		}

		logger.debug(
				"The Total Employees which are not submitted the Time sheets within a week are : ***********************"
						+ projectMemberList.size());
		HashSet<ProjectMembers> projectMemberSet = null;
		if (projectMemberList != null && !projectMemberList.isEmpty()) {
			projectMemberSet = new HashSet<>(projectMemberList);
		}
		return projectMemberSet;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Long> getAllAssignedUsersIdsByReportingManagerId(Long reportingManagerId, Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select userRole.user.id from UserRole userRole where userRole.reportingManager.id = :reportingManagerId and userRole.orgLocations.id = :locationId  ";
		List<Long> userIds = session.createQuery(query).setLong("reportingManagerId", reportingManagerId)
				.setLong("locationId", orgLocationId).list();
		return userIds;

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<UserRole> getAllAssignedUsersByReportingManagerId(Long reportingManagerId, Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserRole userRole where userRole.reportingManager.id = :reportingManagerId and userRole.orgLocations.id = :locationId and userRole.userState <> 'NOT_LOGGEDIN' ";
		List<UserRole> userRoleList = session.createQuery(query.toString())
				.setLong("reportingManagerId", reportingManagerId).setLong("locationId", orgLocationId).list();
		return userRoleList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<UserRole> getAllUsersListByOrgLocationId(Long orgLocationId, Long loginUserId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserRole userRole left join fetch userRole.user user  where"
				+ " (userRole.user.id IN (select os.vendorUser.id from OrgSpoc os where os.clientUser.id =:loginUserId and  os.clientLocationId.id = :orgLocationId) "
				+ "OR userRole.user.id IN (select os.clientUser.id from OrgSpoc os where os.vendorUser.id =:loginUserId and  os.vendorLocationId.id = :orgLocationId) )"
				+ "OR (userRole.orgLocations.id =:orgLocationId) GROUP BY user.id ";
		List<UserRole> usersList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId)
				.setLong("loginUserId", loginUserId).list();
		return usersList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<UserRole> getAllActiveUsersListByOrgLocationId(Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserRole userRole where userRole.orgLocations.id = :orgLocationId and userRole.userState = 'ACTIVE' ";
		List<UserRole> userRoleList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId)
				.list();
		return userRoleList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean isUserisPrimaryForOrg(Long userId, Long orgLocationId) {

		Session session = this.sessionFactory.getCurrentSession();
		List<UserRole> userRole = new ArrayList<UserRole>();
		logger.debug("isUserAdmin By UserId start");
		userRole = session.createQuery(
				"from UserRole userRole left join fetch userRole.roles role left join fetch userRole.user  where userRole.user =:userId "
						+ "and userRole.orgLocations.id = :orgLocationId and userRole.isPrimary = '1' ")
				.setLong("userId", userId).setLong("orgLocationId", orgLocationId).list();

		logger.debug("isUserAdmin By UserId End");

		Boolean flag = false;
		if (userRole.size() > 0) {
			flag = true;
		}
		return flag;
	}

	@Override
	@Transactional
	public UserRole getUserDetailsByUserIdAndLocationIdAndUserType(Long userId, Long locationId, String userType)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<UserRole> userRole = new ArrayList<UserRole>();
		logger.debug("getUserDetailsByEmailAndLocationId method  start");
		userRole = session.createQuery(
				"from UserRole userRole left join fetch userRole.orgLocations left join fetch userRole.roles  "
						+ "left join fetch userRole.user where userRole.user.id = :userId and userRole.orgLocations.id = :locationId and userRole.userType = :userType ")
				.setLong("userId", userId).setLong("locationId", locationId).setString("userType", userType).list();
		logger.debug("getUserDetailsByEmailAndLocationId method ends");
		if (userRole.size() > 0) {
			return userRole.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public UserRole getIsProfessionalOrRecruiterExistsWithEmailAndUserType(String email, String userType)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<UserRole> userRole = new ArrayList<UserRole>();
		logger.debug("getIsProfessionalOrRecruiterExistsWithEmailAndUserType method  start");
		userRole = session
				.createQuery(
						"from UserRole userRole where userRole.user.email = :email and userRole.userType = :userType ")
				.setString("email", email).setString("userType", userType).list();
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
		userRole = session.createQuery(
				"from UserRole userRole left join fetch userRole.orgLocations left join fetch userRole.roles  "
						+ "left join fetch userRole.user where userRole.user.id = :userId and userRole.orgLocations.id = :locationId and userRole.userType <> 'Admin' GROUP BY  userRole.userType")
				.setLong("userId", userId).setLong("locationId", locationId).list();
		logger.debug("getUserRoleByUserIdAndLocationId method ends");
		if (userRole.size() > 0) {
			return userRole.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public UserRole getUserRoleByVerficationUUID(String uuid) {
		Session session = this.sessionFactory.getCurrentSession();
		List<UserRole> userRole = new ArrayList<UserRole>();
		userRole = session.createQuery("from UserRole userRole where userRole.verificationUUID = :uuid")
				.setString("uuid", uuid).list();
		if (userRole.size() > 0) {
			return userRole.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ConnectionsDTO> getUserListForConnections(String searchByName, Integer offset, Long userId)
			throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		List<ConnectionsDTO> usersList = new ArrayList<ConnectionsDTO>();
		String searchByValue = searchByName.trim();
		StringBuilder subQuery = new StringBuilder("");
		String sqlQuery = "select distinct u.ID as id, u.USER_CODE as userCode, u.FILE_URL as fileUrl, concat(u.FIRST_NAME,' ',IFNULL(u.LAST_NAME, '')) as userName, u.EMAIL as email, ur.USER_TYPE as userType, ol.ORG_NAME as orgName,"
				+ "((select count(c.FROM_UID) from CONNECTIONS c where c.TO_UID = :loginUserId "
				+ "and (c.FROM_UID in (select c.FROM_UID from CONNECTIONS c where c.TO_UID = u.ID and c.CONNECTION_STATUS = 'CONNECTED') or "
				+ "c.FROM_UID in (select c.TO_UID from CONNECTIONS c where c.FROM_UID = u.ID and c.CONNECTION_STATUS = 'CONNECTED')))+ "
				+ "(select count(c.TO_UID) from CONNECTIONS c where c.FROM_UID = :loginUserId "
				+ "and (c.TO_UID in (select c.FROM_UID from CONNECTIONS c where c.TO_UID = u.ID and c.CONNECTION_STATUS = 'CONNECTED') or "
				+ "c.TO_UID in (select c.TO_UID from CONNECTIONS c where c.FROM_UID = u.ID and c.CONNECTION_STATUS = 'CONNECTED')))) as count, "
				+ "(select c.CONNECTION_UID from CONNECTIONS c where (c.FROM_UID = :loginUserId and c.TO_UID = u.ID) or (c.FROM_UID = u.ID and c.TO_UID = :loginUserId)) as connectionUUID, "
				+ "(select count(*) from CONNECTIONS c where c.FROM_UID = :loginUserId and c.TO_UID = u.ID and c.CONNECTION_STATUS = 'REQUEST_SENT') as requestSent, "
				+ "(select count(*) from CONNECTIONS c where c.FROM_UID = u.ID and c.TO_UID = :loginUserId and c.CONNECTION_STATUS = 'REQUEST_SENT') as requestRecieved "
				+ "from USER u, USER_ROLE ur, ORG_LOCATIONS ol "
				+ "where ur.USER_ID = u.ID and ur.ORG_LOCATIONS_ID = ol.ID "
				+ "and u.ID not in (select c.FROM_UID from CONNECTIONS c where c.TO_UID = :loginUserId and c.CONNECTION_STATUS = 'CONNECTED') "
				+ "and u.ID not in (select c.TO_UID from CONNECTIONS c where c.FROM_UID = :loginUserId and c.CONNECTION_STATUS = 'CONNECTED') "
				+ "and u.ID != :loginUserId and ur.IS_PRIMARY = 1 ";
		if (searchByValue != null && !"".equalsIgnoreCase(searchByValue)) {
			subQuery.append(
					" and (ur.USER_TYPE = :searchByUserType or u.FIRST_NAME like :searchByValue or u.LAST_NAME like :searchByValue or ol.ORG_NAME like :searchByValue or u.EMAIL like :searchByValue or concat(u.FIRST_NAME,' ',IFNULL(u.LAST_NAME, '')) like :searchByValue)");
			sqlQuery = sqlQuery.concat(subQuery.toString());
		}
		sqlQuery = sqlQuery.concat(" order by count DESC");

		Query queryString = session.createSQLQuery(sqlQuery);
		queryString.setParameter("loginUserId", userId); // setting same parameters multiple times in query
		if (searchByValue != null && !"".equalsIgnoreCase(searchByValue)) {
			queryString.setParameter("searchByUserType", searchByValue).setParameter("searchByValue",
					"%" + searchByValue + "%");
		}
		// System.out.println("The query is: "+queryString.getQueryString());
		// Pagination
		// Converting custom DTO class list from Sql result
		queryString.setFirstResult(offset).setMaxResults(18)
				.setResultTransformer(Transformers.aliasToBean(ConnectionsDTO.class));
		usersList = queryString.list();
		if (usersList.isEmpty()) {
			ConnectionsDTO connectionsDTO = new ConnectionsDTO();
			connectionsDTO.setCountFlag("true");
			usersList.add(connectionsDTO);
		}
		Map<String, BigInteger> countValues = connectionsDao.getCountValuesForSentReceivedConnected(userId);
		if (!usersList.isEmpty()) {
			usersList.get(0).setSentCount(countValues.get("sentCount"));
			usersList.get(0).setReceivedCount(countValues.get("receivedCount"));
			usersList.get(0).setConnectedCount(countValues.get("connectedCount"));
		}
		return usersList;
	}
	/*
	 * if(userRoleList != null && userRoleList.size() > 0) { int requestSentCount =
	 * 0; int requestReceivedCount = 0; int connectedCount = 0; for(UserRole
	 * userRole : userRoleList) { UserRoleDTO userRoleDTO = new UserRoleDTO();
	 * String queryForStatus =
	 * "from Connections connections where (connections.fromUser.id = :fromId and connections.toUser.id = :toId) or (connections.fromUser.id = :toId and connections.toUser.id = :fromId)"
	 * ; Connections connections = (Connections)
	 * session.createQuery(queryForStatus).setLong("fromId", userId).setLong("toId",
	 * userRole.getUser().getId()).uniqueResult(); if(connections != null) {
	 * if(connections.getToUser().getId().equals(userId) &&
	 * connections.getConnectionStatus().toString().equals(ConnectionStatus.
	 * REQUEST_SENT.toString())) { userRoleDTO.setUserState("Request_received");
	 * requestReceivedCount++; }else
	 * if(connections.getFromUser().getId().equals(userId) &&
	 * connections.getConnectionStatus().toString().equals(ConnectionStatus.
	 * REQUEST_SENT.toString())) { requestSentCount++;
	 * userRoleDTO.setUserState("Request_sent"); }else { connectedCount++;
	 * userRoleDTO.setUserState("connected"); }
	 * userRoleDTO.setCreatedDate(connections.getCreatedDate());
	 * userRoleDTO.setConnectionUUID(connections.getConnectionUID()); }
	 * StringBuilder userName = new StringBuilder();
	 * if(userRole.getUser().getLastName() != null) { userName =
	 * userName.append(userRole.getUser().getFirstName()).append(" ").append(
	 * userRole.getUser().getLastName()); }else { userName =
	 * userName.append(userRole.getUser().getFirstName()); } String queryMutual =
	 * "select count(connections) from Connections connections where (connections.fromUser.id = :userId or connections.toUser.id = :userId) and connections.fromUser.id <> :loginUserId and connections.toUser.id <> :loginUserId and connections.connectionStatus = :connectionStatus"
	 * ; Long count = (Long) session.createQuery(queryMutual).setLong("loginUserId",
	 * userId).setLong("userId",
	 * userRole.getUser().getId()).setString("connectionStatus",
	 * ConnectionStatus.CONNECTED.toString()).uniqueResult();
	 * userRoleDTO.setMutualCount(count);
	 * userRoleDTO.setUserName(userName.toString());
	 * userRoleDTO.setUserRoleName(userRole.getRoles().getDisplayName()); String
	 * userOrgName = null; if(userRole.getRoles().getId() == 10) { userOrgName = "";
	 * }else if(userRole.getRoles().getId() == 11) { userOrgName = ""; }else {
	 * userOrgName = userRole.getOrgLocations().getOrgLocationName(); }
	 * userRoleDTO.setOrgName(userOrgName);
	 * userRoleDTO.setUserId(userRole.getUser().getId());
	 * userRoleDTO.setUserCode(userRole.getUser().getUserCode());
	 * userRoleDTO.setFileUrl(userRole.getUser().getFileUrl());
	 * userRoleDTOList.add(userRoleDTO); }
	 * userRoleDTOList.get(0).setSentCount(requestSentCount);
	 * userRoleDTOList.get(0).setReceivedCount(requestReceivedCount);
	 * userRoleDTOList.get(0).setConnectedCount(connectedCount);
	 * 
	 * Collections.sort(userRoleDTOList,new Comparator<UserRoleDTO>() {
	 * 
	 * @Override public int compare(UserRoleDTO a, UserRoleDTO b) { return (int)
	 * (b.getMutualCount()-a.getMutualCount()); } }); if(userRoleDTOList.size() >=
	 * offset){ if(userRoleDTOList.size() > offset + 18){ userRoleDTOList =
	 * userRoleDTOList.subList(offset, (offset+18)); }else{ userRoleDTOList =
	 * userRoleDTOList.subList(offset, ( offset + (userRoleDTOList.size() -
	 * offset))); } return userRoleDTOList; }else{ return null; } }else{ return
	 * null; }
	 * 
	 * }
	 */

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ResponseList<UserRole> getUserRoleListByLocationIdAndUserType(UsersUsageReportDTO usersUsageReportDTO,
			OrgLocations orglocation) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		Integer filterCount = 0;
		ResponseList<UserRole> response = new ResponseList<>();

		String userType = "";
		if ("ACTIVE".equalsIgnoreCase(usersUsageReportDTO.getUserType())) {
			userType = " userRole.userState = 'ACTIVE'";
		} else if ("INACTIVE".equalsIgnoreCase(usersUsageReportDTO.getUserType())) {
			userType = " userRole.userState = 'INACTIVE'";
		} else if ("NOT_LOGGEDIN".equalsIgnoreCase(usersUsageReportDTO.getUserType())) {
			userType = " userRole.userState = 'NOT_LOGGEDIN'";
		}

		StringBuilder query1 = new StringBuilder("");
		if (usersUsageReportDTO.getSearchValue() != null
				&& !"".equalsIgnoreCase(usersUsageReportDTO.getSearchValue())) {
			query1.append("and( (uRole.user.firstName like '" + usersUsageReportDTO.getSearchValue()
					+ "%' OR uRole.user.lastName like '%" + usersUsageReportDTO.getSearchValue() + "' )")
					.append("OR (concat(uRole.user.firstName,uRole.user.lastName) like '%"
							+ usersUsageReportDTO.getSearchValue().replaceAll("[\\s]+", "").trim() + "%')")
					.append("OR uRole.user.email like '%" + usersUsageReportDTO.getSearchValue() + "%' ")
					.append("OR uRole.orgLocations.org.orgName like '%" + usersUsageReportDTO.getSearchValue()
							+ "%' )");
			// if(sqlDate != null) {
			// query1.append("OR uRole.user.createdDate like '%" + sqlDate + "%' ");
			// }

		}

		List<Long> countlist = null;
		List<UserRole> userList = null;
		String query = "";
		String countQuery = "";

		if ((usersUsageReportDTO.getOrgId() != null && usersUsageReportDTO.getOrgId().size() > 0)
				&& (usersUsageReportDTO.getUserIds() != null && usersUsageReportDTO.getUserIds().size() > 0)) {
			query = " from UserRole uRole where uRole.user.id <> 1 and uRole.user.id <> 2 and uRole.user.id IN (select userRole.user.id from UserRole userRole where "
					+ userType + " and userRole.user.id IN (:userIds)) " + query1
					+ " and uRole.orgLocations.org.id IN (:orgList) group by uRole.user.id";
			userList = session.createQuery(query.toString()).setParameterList("orgList", usersUsageReportDTO.getOrgId())
					.setParameterList("userIds", usersUsageReportDTO.getUserIds()).list();
			countQuery = "select count(*) from UserRole uRole where uRole.user.id IN (select userRole.user.id from UserRole userRole where "
					+ userType + " and  userRole.user.id IN (:userIds)) and uRole.orgLocations.org.id IN (:orgList)";
			Query listCountQuery = session.createQuery(countQuery + query1 + " group by uRole.user.id");
			countlist = listCountQuery.setParameterList("orgList", usersUsageReportDTO.getOrgId())
					.setParameterList("userIds", usersUsageReportDTO.getUserIds()).list();
		} else if ((usersUsageReportDTO.getOrgId() == null || usersUsageReportDTO.getOrgId().isEmpty())
				&& (usersUsageReportDTO.getUserIds() != null && usersUsageReportDTO.getUserIds().size() > 0)) {
			query = " from UserRole uRole where uRole.user.id <> 1 and uRole.user.id <> 2 and uRole.user.id IN (select userRole.user.id from UserRole userRole where "
					+ userType + " and userRole.user.id IN (:userIds)) " + query1 + "  group by uRole.user.id";
			userList = session.createQuery(query.toString())
					.setParameterList("userIds", usersUsageReportDTO.getUserIds()).list();
			countQuery = "select count(*) from UserRole uRole where uRole.user.id IN (select userRole.user.id from UserRole userRole where "
					+ userType + " and userRole.user.id IN (:userIds)) ";
			Query listCountQuery = session.createQuery(countQuery + query1 + " group by uRole.user.id");
			countlist = listCountQuery.setParameterList("userIds", usersUsageReportDTO.getUserIds()).list();
		} else if ((usersUsageReportDTO.getOrgId() != null && usersUsageReportDTO.getOrgId().size() > 0)
				&& (usersUsageReportDTO.getUserIds() == null || usersUsageReportDTO.getUserIds().isEmpty())) {
			query = " from UserRole uRole where uRole.user.id <> 1 and uRole.user.id <> 2 and uRole.user.id IN (select userRole.user.id from UserRole userRole where "
					+ userType + ")  " + query1 + " and uRole.orgLocations.org.id IN (:orgList) group by uRole.user.id";
			userList = session.createQuery(query.toString()).setParameterList("orgList", usersUsageReportDTO.getOrgId())
					.list();
			countQuery = "select count(*) from UserRole uRole where uRole.user.id IN (select userRole.user.id from UserRole userRole where "
					+ userType + ") and uRole.orgLocations.org.id IN (:orgList)";
			Query listCountQuery = session.createQuery(countQuery + query1 + " group by uRole.user.id");
			countlist = listCountQuery.setParameterList("orgList", usersUsageReportDTO.getOrgId()).list();
		} else {
			query = " from UserRole uRole where uRole.user.id <> 1 and uRole.user.id <> 2 and uRole.user.id IN (select userRole.user.id from UserRole userRole where "
					+ userType + ")  " + query1 + " group by uRole.user.id";
			userList = session.createQuery(query.toString()).list();
			countQuery = "select count(*) from UserRole uRole where uRole.user.id IN (select userRole.user.id from UserRole userRole where "
					+ userType + ") ";
			Query listCountQuery = session.createQuery(countQuery + query1 + " group by uRole.user.id");
			countlist = listCountQuery.list();
		}

		int size = countlist.size();
		/*
		 * int totalCount = 0; if (usersUsageReportDTO != null) { if(countlist != null
		 * && !countlist.isEmpty()) { totalCount =
		 * countlist.iterator().next().intValue(); } }
		 */
		response.setRecordsTotal(size);

		if (query1.toString() != null && !query1.toString().isEmpty()) {
			response.setRecordsFiltered(filterCount);
		} else {
			response.setRecordsFiltered(size);
		}
		response.setData(userList);
		return response;

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Map<String, Integer> getUserCountListByOrgLocationId(Long orgLocationId, List<Long> orgId) {
		Map<String, Integer> countMap = new HashMap<>();
		Session session = this.sessionFactory.getCurrentSession();

		if (orgId != null && orgId.size() > 0) {

			String query = " from UserRole uRole where uRole.user.id <> 1 and uRole.user.id <> 2 and uRole.user.id IN (select userRole.user.id from UserRole userRole where  userRole.userState = 'ACTIVE') and uRole.orgLocations.org.id IN (:orgList) group by uRole.user.id";
			List<User> activeUserList = session.createQuery(query.toString()).setParameterList("orgList", orgId).list();
			countMap.put("ACTIVE", activeUserList.size());

			String query1 = " from UserRole uRole where uRole.user.id <> 1 and uRole.user.id <> 2 and uRole.user.id IN (select userRole.user.id from UserRole userRole where  userRole.userState = 'INACTIVE') and uRole.orgLocations.org.id IN (:orgList) group by uRole.user.id";
			List<User> inactiveUserList = session.createQuery(query1.toString()).setParameterList("orgList", orgId)
					.list();
			countMap.put("INACTIVE", inactiveUserList.size());

			String query2 = " from UserRole uRole where uRole.user.id <> 1 and uRole.user.id <> 2 and uRole.user.id IN (select userRole.user.id from UserRole userRole where  userRole.userState = 'NOT_LOGGEDIN') and uRole.orgLocations.org.id IN (:orgList) group by uRole.user.id";
			List<User> notLoggedinUserList = session.createQuery(query2.toString()).setParameterList("orgList", orgId)
					.list();
			countMap.put("NOT_LOGGEDIN", notLoggedinUserList.size());

			List<String> ipList = new ArrayList<>();
			String query3 = "select userLogin.ipaddress from UserLoginLogoutSession userLogin where userLogin.userId.id IN (select userRole.user.id from UserRole userRole where  userRole.orgLocations.org.id IN (:orgList)) group by userLogin.ipaddress";
			ipList = session.createQuery(query3).setParameterList("orgList", orgId).list();
			countMap.put("MULTIPLE_IPS", ipList.size());

		} else {
			String query = " from User user where user.id <> 1 and user.id <> 2 and user.id IN (select userRole.user.id from UserRole userRole where userRole.userState = 'ACTIVE')";
			List<User> activeUserList = session.createQuery(query.toString()).list();
			countMap.put("ACTIVE", activeUserList.size());

			String query1 = " from User user where user.id <> 1 and user.id <> 2 and user.id IN (select userRole.user.id from UserRole userRole where userRole.userState = 'INACTIVE')";
			List<User> inactiveUserList = session.createQuery(query1.toString()).list();
			countMap.put("INACTIVE", inactiveUserList.size());

			String query2 = " from User user where user.id <> 1 and user.id <> 2 and user.id IN (select userRole.user.id from UserRole userRole where userRole.userState = 'NOT_LOGGEDIN')";
			List<User> notLoggedinUserList = session.createQuery(query2.toString()).list();
			countMap.put("NOT_LOGGEDIN", notLoggedinUserList.size());

			List<String> ipList = new ArrayList<>();
			String query3 = "select uLoginSession.ipaddress from UserLoginLogoutSession uLoginSession group by uLoginSession.ipaddress";
			ipList = session.createQuery(query3).list();
			countMap.put("MULTIPLE_IPS", ipList.size());

		}
		return countMap;
	}

	@Override
	@Transactional
	public ResponseList<UserRole> getDynamicUsersListByRoleAndNameAndLocationId(UserDTO userDTO,
			UserRoleDTO userRoleDTO, Long locationId, Long userId, String loginUserRole, String userCode,
			List<Long> orgRelationsList, List<Long> managerUserIds, List<Long> teamsList, List<Long> departmentsList)
			throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		ResponseList<UserRole> response = new ResponseList<>();
		List<String> userStateList = new ArrayList<>();
		List<Long> userRolesList = new ArrayList<>();

		List userRoleList = null;
		List<Long> countlist = null;
		Integer filterCount = 0;
		String subQuery = "";
		String subQuery2 = "";
		String subQuery3 = "";
		String subQuery4 = "";
		String subQuery5 = "";
		String subQuery6 = "";
		String subQuery7 = "";
		String employmentTypeQuery = "";

		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder query1 = new StringBuilder("");
		if (userDTO.getSearchValue() != null && !"".equalsIgnoreCase(userDTO.getSearchValue())) {
			query1.append("and(userRole.user.firstName like '%" + userDTO.getSearchValue().replace("'", "") + "%' ")
					.append("OR (userRole.user.lastName like '%" + userDTO.getSearchValue().replace("'", "") + "%' ) ")
					.append("OR (userRole.user.middelName like '%" + userDTO.getSearchValue().replace("'", "") + "%' ) ")
					.append("OR (case when userRole.user.middelName is not null and userRole.user.lastName is not null"
							+ " then (concat(userRole.user.firstName,' ',userRole.user.middelName,' ',userRole.user.lastName))"
							+ " else ((case when userRole.user.middelName is not null and userRole.user.lastName is null"
							+ "				then concat(userRole.user.firstName,' ',userRole.user.middelName)"
							+ "				else ((case when userRole.user.middelName is null and userRole.user.lastName is not null"
							+ "							then concat(userRole.user.firstName,' ',userRole.user.lastName)"
							+ "							else (userRole.user.firstName) "
							+ "						end)) "
							+ "			end)) "
						    + "end)" 
						    + " like '%"+userDTO.getSearchValue().replace("'", "") + "%' ")
					.append("OR userRole.user.email like '%" + userDTO.getSearchValue().replace("'", "") + "%' ")
					.append("OR userRole.user.mobilePhone like '%" + userDTO.getSearchValue().replace("'", "") + "%' ")
					.append("OR userRole.roles.displayName like '%" + userDTO.getSearchValue().replace("'", "") + "%' ")
					.append("OR userRole.user.id IN(Select emp.user.id from Employees emp where emp.userEmploymentType like '%"
							+ userDTO.getSearchValue().replace("'", "")
							+ "%' and emp.orgLocations.id = :locationId)) ");

		}
		// usertList shorting order
		String sortingQry = "";
		if (StringUtils.isNotBlank(userRoleDTO.getSortColumn())) {
			if (userRoleDTO.getSortColumn().equalsIgnoreCase("displayName")) {
				sortingQry = " order by userRole.roles." + userRoleDTO.getSortColumn() + " "
						+ userRoleDTO.getSortOrder();
			} else {
				sortingQry = " order by userRole.user." + userRoleDTO.getSortColumn() + " "
						+ userRoleDTO.getSortOrder();

			}

		}

		if (userDTO.getUserRole() != null && !userDTO.getUserRole().isEmpty()) {
			String[] userRoles = userDTO.getUserRole().split(",");
			for (int i = 0; i < userRoles.length; i++) {
				userRolesList.add(Long.parseLong(userRoles[i]));
			}
		} else {
			userRolesList.add(0L);
		}

		subQuery = " and ((:varZero  IN (:roleIds)) OR (:varZero NOT IN (:roleIds) and userRole.roles.id IN (:roleIds)))";

		if (userRoleDTO.getUserState() != null && !userRoleDTO.getUserState().isEmpty()) {
			String[] userState = userRoleDTO.getUserState().split(",");
			if (Arrays.asList(userState).contains("ACTIVE")) {
				subQuery2 = subQuery2 + "userRole.userState = 'ACTIVE' or ";
			}

			if (Arrays.asList(userState).contains("INACTIVE")) {
				subQuery2 = subQuery2 + "userRole.userState = 'INACTIVE' or ";
			}

			if (Arrays.asList(userState).contains("NOT_LOGGEDIN")) {
				subQuery2 = subQuery2 + "userRole.userState = 'NOT_LOGGEDIN' or ";
			}

			subQuery2 = "and (" + subQuery2.substring(0, subQuery2.length() - 4) + ")";
		}

		if (orgRelationsList.isEmpty()) {
			orgRelationsList.add(0L);
			subQuery3 = " and ((:varZero  IN (:orgRelationsList)) OR (:varZero NOT IN (:orgRelationsList)))";

		} else {
			subQuery3 = "and userRole.user.id IN (select candidate.consultantUser.id from  Candidate candidate where candidate.isAssociate ='Y' and candidate.status = 1 and candidate.orgRelationId.id IN(:orgRelationsList))";
			
		}

		if (managerUserIds.isEmpty()) {
			managerUserIds.add(0L);
			subQuery4 = " and ((:varZero  IN (:managerUserIds)) OR (:varZero NOT IN (:managerUserIds)))";

		} else {
			subQuery4 = " and userRole.reportingManager.id IN (:managerUserIds)";
		}

		if (teamsList.isEmpty()) {
			teamsList.add(0L);
			subQuery5 = " and ((:varZero  IN (:teamsList)) OR (:varZero NOT IN (:teamsList)))";

		} else {

			subQuery5 = " and userRole.user.id IN (select tm.memberUserId.id from TeamMembers tm where tm.teamId.id IN (:teamsList) and tm.status = '1' )";
		}

		if (departmentsList.isEmpty()) {
			departmentsList.add(0L);
			subQuery6 = " and ((:varZero  IN (:departmentsList)) OR (:varZero NOT IN (:departmentsList)))";

		} else {

			subQuery6 = " and userRole.user.id IN (select emp.user.id from Employees emp where emp.departmentId.id IN (:departmentsList))";
		}

		List<String> tagsList = new ArrayList<>();
		if (userDTO.getTags() != null && !userDTO.getTags().isEmpty()) {
			String[] tags = userDTO.getTags().split(",");
			for (int i = 0; i < tags.length; i++) {
				tagsList.add(tags[i]);
			}
		} else {
			tagsList.add("");
		}

		String tagsQuery = " and ((:varString  IN (:tagsList)) OR (:varString NOT IN (:tagsList) and userRole.user.id IN (select st.entityId from SourceleadTags st where st.tagName IN (:tagsList) and st.status ='1' ))) ";

		if (userRoleDTO.getEmployeMentType() != null && !userRoleDTO.getEmployeMentType().isEmpty()) {
			employmentTypeQuery = "and userRole.user.id IN (select emp.user.id from Employees emp where emp.orgLocations.id = :locationId and emp.userEmploymentType IN('"+userRoleDTO.getEmployeMentType().replace(",", "','")+"') )";
		}

		String listQuery = "from UserRole userRole where userRole.orgLocations.id = :locationId  and userRole.user.id <> '1'  "
				+ subQuery2 + ""
				+ " and userRole.roles.roleName <> 'ROLE_CROWDSOURCING_PROFESSIONAL' and userRole.roles.roleName <> 'ROLE_CROWDSOURCING_RECRUITER' "
				+ subQuery + " " + subQuery3 + " " + subQuery4 + " " + subQuery5 + " " + subQuery6 + " " + tagsQuery
				+ " " + employmentTypeQuery + "  ";
		Query query = session.createQuery(listQuery + query1 + sortingQry);

		userRoleList = query.setLong("locationId", locationId).setParameterList("roleIds", userRolesList)
				.setLong("varZero", 0).setParameterList("orgRelationsList", orgRelationsList).setString("varString", "")
				.setParameterList("tagsList", tagsList).setParameterList("managerUserIds", managerUserIds)
				.setParameterList("teamsList", teamsList).setParameterList("departmentsList", departmentsList)
				.setFirstResult(userDTO.getStart()).setMaxResults(userDTO.getLength()).list();

		String queryCount = "Select count (*) from UserRole userRole where userRole.orgLocations.id = :locationId and userRole.user.id <> '1' "
				+ subQuery2 + ""
				+ " and userRole.roles.roleName <> 'ROLE_CROWDSOURCING_PROFESSIONAL' and userRole.roles.roleName <> 'ROLE_CROWDSOURCING_RECRUITER' "
				+ subQuery + " " + subQuery3 + " " + subQuery4 + " " + subQuery5 + " " + subQuery6 + " " + tagsQuery
				+ " " + employmentTypeQuery + " ";
		Query query2 = session.createQuery(queryCount + query1);

		countlist = query2.setLong("locationId", locationId).setParameterList("roleIds", userRolesList)
				.setString("varString", "").setParameterList("tagsList", tagsList)
				.setParameterList("orgRelationsList", orgRelationsList)
				.setParameterList("managerUserIds", managerUserIds).setParameterList("teamsList", teamsList)
				.setParameterList("departmentsList", departmentsList).setLong("varZero", 0).list();

		if (query1.toString() != null && !query1.toString().isEmpty()) {
			filterCount = countlist.iterator().next().intValue();
		}

		int totalCount = 0;
		if (userDTO != null) {
			if (countlist != null && !countlist.isEmpty()) {
				totalCount = countlist.iterator().next().intValue();
			}
		}
		response.setRecordsTotal(totalCount);

		if (query1.toString() != null && !query1.toString().isEmpty()) {
			response.setRecordsFiltered(filterCount);
		} else {
			response.setRecordsFiltered(totalCount);
		}
		response.setData(userRoleList);
		return response;

	}

	@Override
	@Transactional
	public List<User> getAllReportingManagersAssignedToAssociates(Long orgLocationId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from User user where user.id IN (select distinct userRole.reportingManager.id from UserRole userRole where  userRole.orgLocations.id = :orgLocationId)";
		List<User> userList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).list();
		return userList;
	}

	@Override
	@Transactional
	public Map<String, Integer> getUsageReportUserCountListByOrgLocationId(Long orgLocationId,
			UsersUsageReportDTO usersUsageReportDTO, OrgLocations orgLocations, Date[] freqDates) throws Exception {
		Map<String, Integer> countMaps = new HashMap<>();

		java.sql.Date sqlStartDate = new java.sql.Date(freqDates[0].getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(freqDates[1].getTime());
		Session session = this.sessionFactory.getCurrentSession();

		if (usersUsageReportDTO.getOrgId() != null && usersUsageReportDTO.getOrgId().size() > 0) {

			String query = " from UserRole uRole where uRole.user.id <> 1 and uRole.user.id <> 2 and uRole.user.id IN (select actHis.userId from ActionHistory actHis where actHis.actionTime >= :startDate and actHis.actionTime <= :endDate) and uRole.userState = 'ACTIVE' and uRole.orgLocations.org.id IN (:orgList) group by uRole.user.id";
			List<User> activeUserList = session.createQuery(query.toString()).setParameter("startDate", sqlStartDate)
					.setParameter("endDate", sqlEndDate).setParameterList("orgList", usersUsageReportDTO.getOrgId())
					.list();
			countMaps.put("ACTIVE", activeUserList.size());

			String query1 = " from UserRole uRole where uRole.user.id <> 1 and uRole.user.id <> 2 and uRole.user.id IN (select actHis.userId from ActionHistory actHis where actHis.actionTime >= :startDate and actHis.actionTime <= :endDate) and uRole.userState = 'INACTIVE' and uRole.orgLocations.org.id IN (:orgList) group by uRole.user.id";
			List<User> inactiveUserList = session.createQuery(query1.toString()).setParameter("startDate", sqlStartDate)
					.setParameter("endDate", sqlEndDate).setParameterList("orgList", usersUsageReportDTO.getOrgId())
					.list();
			countMaps.put("INACTIVE", inactiveUserList.size());

			String query2 = " from UserRole uRole where uRole.user.id <> 1 and uRole.user.id <> 2 and uRole.user.id IN (select actHis.userId from ActionHistory actHis where actHis.actionTime >= :startDate and actHis.actionTime <= :endDate) and uRole.userState = 'NOT_LOGGEDIN' and uRole.orgLocations.org.id IN (:orgList) group by uRole.user.id";
			List<User> notLoggedinUserList = session.createQuery(query2.toString())
					.setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate)
					.setParameterList("orgList", usersUsageReportDTO.getOrgId()).list();
			countMaps.put("NOT_LOGGEDIN", notLoggedinUserList.size());

		} else {

			String query = " from UserRole uRole where uRole.user.id <> 1 and uRole.user.id <> 2 and uRole.user.id IN (select actHis.userId from ActionHistory actHis where actHis.actionTime >= :startDate and actHis.actionTime <= :endDate) and uRole.userState = 'ACTIVE' group by uRole.user.id";
			List<User> activeUserList = session.createQuery(query.toString()).setParameter("startDate", sqlStartDate)
					.setParameter("endDate", sqlEndDate).list();
			countMaps.put("ACTIVE", activeUserList.size());

			String query1 = " from UserRole uRole where uRole.user.id <> 1 and uRole.user.id <> 2 and uRole.user.id IN (select actHis.userId from ActionHistory actHis where actHis.actionTime >= :startDate and actHis.actionTime <= :endDate) and uRole.userState = 'INACTIVE') group by uRole.user.id";
			List<User> inactiveUserList = session.createQuery(query1.toString()).setParameter("startDate", sqlStartDate)
					.setParameter("endDate", sqlEndDate).list();
			countMaps.put("INACTIVE", inactiveUserList.size());

			String query2 = " from UserRole uRole where uRole.user.id <> 1 and uRole.user.id <> 2 and uRole.user.id IN (select actHis.userId from ActionHistory actHis where actHis.actionTime >= :startDate and actHis.actionTime <= :endDate) and uRole.userState = 'NOT_LOGGEDIN') group by uRole.user.id";
			List<User> notLoggedinUserList = session.createQuery(query2.toString())
					.setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate).list();
			countMaps.put("NOT_LOGGEDIN", notLoggedinUserList.size());
		}
		return countMaps;
	}

	@Override
	@Transactional
	public List<User> getAllProjectManagersAssignedToProjects(Long orgLocationId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from User user where user.id IN (select distinct approver.approverUserId.id from ProjectApproverHierarchy approver where approver.status = '1' and "
				+ " approver.projectClientsId.id IN (select pc.id from ProjectClients pc where pc.orgLocationsId = :orgLocationId)) ORDER BY user.firstName ASC";
		List<User> userList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).list();
		return userList;

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<UserRole> getAllUsersBasedOrgLocationIdAndActiveAndNotLogin(Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserRole userRole left join fetch userRole.orgLocations orgLocation   where orgLocation.id =:orgLocationId and userRole.userState <> 'INACTIVE' ";
		List<UserRole> userRoleList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId)
				.list();
		return userRoleList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<AssociateDepartmentView> getUserRolesByDepartmentId(String departmentId, Long orgLocationId,
			List<UserIdView> userIdViewList) {
		Session session = this.sessionFactory.getCurrentSession();
		List<AssociateDepartmentView> assocDeptViewList = null;
		if (userIdViewList != null && userIdViewList.size() > 0) {
			String uIds = userIdViewList.get(0).getAssociateId();
			String userIds = uIds.replaceAll("\\s+", "");
			List<Long> userList = Stream.of(userIds.split(",")).map(Long::parseLong).collect(Collectors.toList());
			if (StringUtils.isNoneBlank(departmentId)) {
				List<Long> deptList = Stream.of(departmentId.split(",")).map(Long::parseLong)
						.collect(Collectors.toList());
				String query = "from AssociateDepartmentView assView where assView.userState <> 'INACTIVE' and assView.departmentId IN (:departmentId) and assView.userId NOT IN (:userList) and assView.orgLocationsId = :orgLocationsId and assView.empOrgLocationsId = :orgLocationsId";
				assocDeptViewList = session.createQuery(query.toString()).setParameterList("departmentId", deptList)
						.setParameterList("userList", userList).setLong("orgLocationsId", orgLocationId).list();
			} else {
				String query = "from AssociateDepartmentView assView where assView.userState <> 'INACTIVE' and assView.orgLocationsId = :orgLocationsId and assView.userId NOT IN (:userList) and assView.empOrgLocationsId = :orgLocationsId";
				assocDeptViewList = session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationId)
						.setParameterList("userList", userList).list();
			}
		} else {
			if (StringUtils.isNotBlank(departmentId)) {
				List<Long> deptList = Stream.of(departmentId.split(",")).map(Long::parseLong)
						.collect(Collectors.toList());
				String query = "from AssociateDepartmentView assView where assView.userState <> 'INACTIVE' and assView.departmentId IN (:departmentId) and assView.orgLocationsId = :orgLocationsId and assView.empOrgLocationsId = :orgLocationsId";
				assocDeptViewList = session.createQuery(query.toString()).setParameterList("departmentId", deptList)
						.setLong("orgLocationsId", orgLocationId).list();
			} else {
				String query = "from AssociateDepartmentView assView where assView.userState <> 'INACTIVE' and assView.orgLocationsId = :orgLocationsId and assView.empOrgLocationsId = :orgLocationsId";
				assocDeptViewList = session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationId)
						.list();
			}
		}

		return assocDeptViewList;
	}

	@Override
	@Transactional
	public List<UserRole> getUserListByUserIdList(List<Long> userIdList, Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<UserRole> userList = null;
		String query = "from UserRole uRole where uRole.user.id IN (:userIdList) and uRole.orgLocations.id =:orgLocationId";
		userList = session.createQuery(query).setParameterList("userIdList", userIdList)
				.setLong("orgLocationId", orgLocationId).list();
		return userList;
	}

	@Override
	@Transactional
	public List<UserRole> getUserAccountsListByUserId(List<Long> userId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserRole userRole left join fetch userRole.roles role left join fetch userRole.user left join fetch userRole.orgLocations where userRole.user.id IN (:userId) and userRole.userState != 'INACTIVE' ";
		List<UserRole> userRoleList = session.createQuery(query.toString()).setParameterList("userId", userId).list();
		return userRoleList;

	}

	@Override
	@Transactional
	public List<Long> getAllAssignedUsersIdsByResourceManagerId(Long resourceManagerId, Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select userRole.user.id from UserRole userRole where userRole.resourceManager.id = :resourceManagerId and userRole.orgLocations.id = :locationId  ";
		List<Long> userIds = session.createQuery(query).setLong("resourceManagerId", resourceManagerId)
				.setLong("locationId", orgLocationId).list();
		return userIds;

	}

	@Override
	@Transactional
	public List<UserRole> getAllAssignedUsersByResourceManagerId(Long resourceManagerId, Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserRole userRole where userRole.resourceManager.id = :resourceManagerId and userRole.orgLocations.id = :locationId ";
		List<UserRole> userRoleList = session.createQuery(query.toString())
				.setLong("resourceManagerId", resourceManagerId).setLong("locationId", orgLocationId).list();
		return userRoleList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<BigInteger> getAllUsersRoleList() throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "SELECT  UR.ID AS userRoleId  FROM USER_ROLE UR ORDER BY UR.ID DESC ";
		List<BigInteger> userRoleIds = session.createSQLQuery(query).list();
		return userRoleIds;
	}

	@Override
	@Transactional
	public List<UserDTO> getAllReportingManagersListAssignedToAssociates(Long orgLocationId) throws Exception {

		List<UserDTO> userDTOList = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;

		query = "SELECT DISTINCT" + " UR.ID AS userId,"
				+ " CONCAT (UR.FIRST_NAME,' ',IFNULL(UR.LAST_NAME,'')) AS fullName" + " FROM" + " USER_ROLE RL ,"
				+ " USER UR," + " ORG_LOCATIONS OL" + " WHERE RL.REPORTING_MANAGER_ID = UR.ID"
				+ " AND   RL.ORG_LOCATIONS_ID = :orgLocationId";

		Query queryObj = session.createSQLQuery(query);
		queryObj.setLong("orgLocationId", orgLocationId);
		queryObj.setResultTransformer(Transformers.aliasToBean(UserDTO.class));
		userDTOList = queryObj.list();
		return userDTOList;
	}

	@Override
	@Transactional
	public UserRole getGlobalUserReportingManagerByUserIdAndOrgId(Long userId, Long orgId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserRole userRole where userRole.user.id = :userId and userRole.orgLocations.org.id = :orgId and userRole.isGlobal = 'Y'";
		List<UserRole> userRoleList = session.createQuery(query.toString()).setLong("userId", userId)
				.setLong("orgId", orgId).list();
		if (userRoleList.size() > 0) {
			return userRoleList.get(0);
		} else {
			return null;
		}

	}

	@Override
	@Transactional
	public Boolean getIsGlobalManager(Long orgId, Long userId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		List<UserRole> userRole = new ArrayList<UserRole>();
		logger.debug("isUserAdmin By UserId start");
		userRole = session.createQuery(
				"from UserRole userRole left join fetch userRole.orgLocations ol left join fetch ol.org org where org.id = :orgId and userRole.isGlobal = 'Y' and userRole.user.id = :userId ")
				.setLong("orgId", orgId).setLong("userId", userId).list();

		logger.debug("isUserAdmin By UserId End");

		Boolean flag = false;
		if (userRole.size() > 0) {
			flag = true;
		}
		return flag;
	}

	@Override
	@Transactional
	public Boolean isUserWithMultipleOrgsAccounts(Long orgId, Long userId) {

		Session session = this.sessionFactory.getCurrentSession();
		List<UserRole> userRole = new ArrayList<UserRole>();
		logger.debug("isUserWithMultipleOrgsAccounts start");
		userRole = session.createQuery(
				"from UserRole userRole left join fetch userRole.orgLocations ol left join fetch ol.org org where org.id <> :orgId and userRole.user.id = :userId ")
				.setLong("orgId", orgId).setLong("userId", userId).list();

		logger.debug("isUserWithMultipleOrgsAccounts End");

		Boolean flag = false;
		if (userRole.size() > 0) {
			flag = true;
		}
		return flag;
	}

	@Override
	@Transactional
	public List<UserRole> getLoginUserAccountsList(Long userId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserRole userRole left join fetch userRole.user user where user.id = :userId and userRole.userState = 'ACTIVE' ";
		List<UserRole> userRoleList = session.createQuery(query.toString()).setLong("userId", userId).list();
		return userRoleList;

	}

	@Override
	@Transactional
	public List<UserRole> getAllUserRoles() throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserRole userRole left join fetch userRole.user left join fetch userRole.roles roles "
				+ " left join fetch roles.roleHierarchy  ";
		List<UserRole> userRoleList = session.createQuery(query.toString()).list();
		return userRoleList;

	}

	@Override
	@Transactional
	public String getUserRoleUUIDByUserRoleId(Long userRoleId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "select userRole.uuid from UserRole userRole where userRole.id = :userRoleId";
		List<String> UUID = session.createQuery(query).setLong("userRoleId", userRoleId).list();
		return UUID.get(0);

	}

	@Override
	@Transactional
	public List<UserRole> getAllUserRolesByRoleId(Long roleId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserRole userRole left join fetch userRole.user left join fetch userRole.roles roles "
				+ " left join fetch roles.roleHierarchy where roles.id = :roleId ";
		List<UserRole> userRoleList = session.createQuery(query.toString()).setLong("roleId", roleId).list();
		return userRoleList;

	}

	@Override
	@Transactional
	public List<UserRole> getLoginUserLoginAccountsList(Long userId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserRole userRole left join fetch userRole.user user where user.id = :userId and (userRole.userState = 'ACTIVE' OR userRole.userState = 'NOT_LOGGEDIN') ";
		List<UserRole> userRoleList = session.createQuery(query.toString()).setLong("userId", userId).list();
		return userRoleList;

	}

	@Override
	@Transactional
	public List<UserDTO> getAllActiveUsersList(Long orgLocationId) throws Exception {

		List<UserDTO> userDTOList = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;

		query = "SELECT DISTINCT " + " UR.ID AS userId," + " UR.EMAIL AS email,"
				+ " IFNULL(UR.FILE_URL,'/resources/images/userprofileImg.jpg') AS fileURL,"
				+ " CONCAT (UR.FIRST_NAME,' ',IFNULL(UR.MIDDLE_NAME,''),' ',IFNULL(UR.LAST_NAME,'')) AS fullName"
				+ " FROM" + " USER_ROLE RL ," + " USER UR," + " ORG_LOCATIONS OL"
				+ " WHERE RL.USER_ID = UR.ID AND RL.ORG_LOCATIONS_ID = :orgLocationId "
				+ " AND RL.USER_STATE = 'ACTIVE'";

		Query queryObj = session.createSQLQuery(query);
		queryObj.setLong("orgLocationId", orgLocationId);
		queryObj.setResultTransformer(Transformers.aliasToBean(UserDTO.class));
		userDTOList = queryObj.list();
		return userDTOList;
	}

	@Override
	@Transactional
	public List<UserRole> getAllUserRolesByOrgLocationId(Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserRole userRole left join fetch userRole.orgLocations left join fetch userRole.orgId left join fetch userRole.user "
				+ " left join fetch userRole.roles left join fetch userRole.reportingManager left join fetch userRole.resourceManager "
				+ " where userRole.orgLocations.id = :orgLocationId and (userRole.userState = 'ACTIVE' OR userRole.userState = 'NOT_LOGGEDIN') ";
		List<UserRole> userRoleList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId)
				.list();
		return userRoleList;

	}

	@Override
	@Transactional
	public List<UserRole> getAllUserRolesByOrgLocationIdAndIsSubscriptionStatus(Long orgLocationId,String subscriptionStatus) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "";
		List<UserRole> userRoleList = null;
		if("Y".equalsIgnoreCase(subscriptionStatus)) {
			 query = "from UserRole userRole where userRole.orgLocations.id = :orgLocationId and userRole.userState = 'ACTIVE' and userRole.isSubscription is null ";
			 userRoleList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).list();
		}else {
			if("N".equalsIgnoreCase(subscriptionStatus)) {
				subscriptionStatus = "Y";
			}
			 query = "from UserRole userRole where userRole.orgLocations.id = :orgLocationId and userRole.userState = 'INACTIVE' and userRole.isSubscription =:isSubscription";
			 userRoleList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).setString("isSubscription", subscriptionStatus).list();
		}
		
		return userRoleList;
	}
}
