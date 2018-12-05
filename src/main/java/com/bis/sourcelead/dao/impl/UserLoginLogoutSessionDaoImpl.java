package com.bis.sourcelead.dao.impl;

import java.math.BigInteger;
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

import com.bis.sourcelead.dao.UserLoginLogoutSessionDao;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.dto.UsersUsageReportDTO;
import com.bis.sourcelead.entity.ActionHistory;
import com.bis.sourcelead.entity.UserLoginLogoutSession;

@Repository
@Transactional(rollbackFor = { Exception.class })
public class UserLoginLogoutSessionDaoImpl implements UserLoginLogoutSessionDao {

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
    

    private static final Logger logger = LoggerFactory.getLogger(UserLoginLogoutSessionDaoImpl.class);
    @Override
    public UserLoginLogoutSession storeUserLoginLogoutSession(UserLoginLogoutSession userLoginLogoutSession){
        logger.debug("Storing UserLoginLogoutSession Record");
        Session session = this.sessionFactory.getCurrentSession();
        if (userLoginLogoutSession != null) {
            session.saveOrUpdate(userLoginLogoutSession);
        }
        logger.debug("UserLoginLogoutSession Added Successfully");
        return userLoginLogoutSession;
    }

    @Override
    public UserLoginLogoutSession getLastLoginRecord(Long userId) {
        Session session = this.sessionFactory.getCurrentSession();
        List<UserLoginLogoutSession> userLoginLogoutSessionList = new ArrayList<UserLoginLogoutSession>();
        logger.debug("getLastLoginRecord start");
        String query = "from UserLoginLogoutSession uls left join fetch uls.userId where uls.id IN (select max(tempuls.id) from UserLoginLogoutSession tempuls where tempuls.userId.id =:userId) and uls.userId.id =:userId ";
        userLoginLogoutSessionList = session.createQuery(query).setLong( "userId", userId ).list();
        logger.debug("getLastLoginRecord end");
        if (userLoginLogoutSessionList.size() > 0) {
            return userLoginLogoutSessionList.get(0);
        } else {
            return null;
        }
    }
    
    @Override
    public UserLoginLogoutSession getUserLoginLogoutSessionById(Long userSessionId) {
        Session session = this.sessionFactory.getCurrentSession();
        List<UserLoginLogoutSession> userLoginLogoutSessionList = new ArrayList<UserLoginLogoutSession>();
        logger.debug("getLastLoginRecord start");
        String query = "from UserLoginLogoutSession uls where uls.id =:userSessionId";
        userLoginLogoutSessionList = session.createQuery(query).setLong( "userSessionId", userSessionId ).list();
        logger.debug("getLastLoginRecord end");
        if (userLoginLogoutSessionList.size() > 0) {
            return userLoginLogoutSessionList.get(0);
        } else {
            return null;
        }
    }
    
    @Override
    public UserLoginLogoutSession getUserLoginLogoutSessionByActionUUID(String actionUUID) {
        Session session = this.sessionFactory.getCurrentSession();
        List<UserLoginLogoutSession> userLoginLogoutSessionList = new ArrayList<UserLoginLogoutSession>();
        logger.debug("getLastLoginRecord start");
        String query = "from UserLoginLogoutSession uls where uls.actionUUID =:actionUUID";
        userLoginLogoutSessionList = session.createQuery(query).setString( "actionUUID", actionUUID ).list();
        logger.debug("getLastLoginRecord end");
        if (userLoginLogoutSessionList.size() > 0) {
            return userLoginLogoutSessionList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public boolean validatePreviouslyAuthenticatedByUserIdAndIpAddress(String ipAddress, Long userId)
    {
        Session session = this.sessionFactory.getCurrentSession();
        List<UserLoginLogoutSession> userLoginLogoutSessionList = new ArrayList<UserLoginLogoutSession>();
        logger.debug("getLastLoginRecord start");
        String query = "from UserLoginLogoutSession uls where uls.ipaddress =:ipAddress and uls.authenticated = 'Y' and uls.userId.id =:userId";
        userLoginLogoutSessionList = session.createQuery(query).setString( "ipAddress", ipAddress).setLong("userId", userId).list();
        logger.debug("getLastLoginRecord end");
        if (userLoginLogoutSessionList.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<String> getMultipleIps(UsersUsageReportDTO usersUsageReportDTO)
    {
        Session session = this.sessionFactory.getCurrentSession();
        List<String> ipList = new ArrayList<>();
        String query = "select uLoginSession.ipaddress from UserLoginLogoutSession uLoginSession where uLoginSession.userId.id IN (select userRole.user.id from UserRole userRole where  userRole.orgLocations.org.id IN (:orgList)) group by uLoginSession.ipaddress";
        ipList = session.createQuery(query).setParameterList("orgList", usersUsageReportDTO.getOrgId()).list();
        return ipList;
    }

    @Override
    public UserDTO getUsersListByIpAddress(String ipAddress,List<Long> list,Long orgLocationId)
    {
        Session session = this.sessionFactory.getCurrentSession();
        UserDTO udto = new UserDTO();
        String query1 = "SELECT count(distinct uls.USERID) As count FROM user_login_logout_session uls where uls.IPADDRESS = :ipAddress and uls.USERID IN(select USER_ID from user_role ur join ORG_LOCATIONS ol on ur.ORG_LOCATIONS_ID = ol.ID and ol.ORG_ID in (:orgList)) group by uls.IPADDRESS";
        //String query = "select userLogin.userId.id from UserLoginLogoutSession userLogin where userLogin.ipaddress =:ipaddress and userLogin.userId.id IN (select userRole.user.id from UserRole userRole INNER JOIN  userRole.orgLocations orgl where  orgl.org.id IN (:orgList)) group by userLogin.userId.id";
         BigInteger ipCount = (BigInteger) session.createSQLQuery(query1).setString("ipAddress", ipAddress).setParameterList("orgList", list).uniqueResult();
         udto.setTotalNoOfUsers(ipCount);

         List<ActionHistory> actionHistory = new ArrayList<>();
//         String query1 = "from ActionHistory ah left join fetch ah.userLoginLogoutSessionId uls where uls.ipaddress = :ipAddress and uls.userId.id IN(select ur.userId.id from UserRole ur left join fetch ur.orgLocations ol where ol.org.id in (:orgList))";
         
         
         String query = "SELECT  IPADDRESS, COUNT(ACTION_NAME) NO_OF_ACTIONS, ORG_LOCATION_ID  "
         		+ "FROM user_login_logout_session, action_history WHERE "
         		+ "action_history.USER_LOGIN_LOGOUT_SESSION_ID = user_login_logout_session.ID AND IPADDRESS = :ipAddress  AND ORG_LOCATION_ID =:orgLocationId "
         		+ "GROUP BY USER_LOGIN_LOGOUT_SESSION_ID";
         
         actionHistory = session.createSQLQuery(query).setString("ipAddress", ipAddress).setLong("orgLocationId", orgLocationId).list();
         udto.setNoOfActivities(String.valueOf(actionHistory.size()));
         
        return udto;
    }
    

    @Override
    public List<UserLoginLogoutSession> getUsersByipAddress(UsersUsageReportDTO usersUsageReportDTO)
    {
        Session session = this.sessionFactory.getCurrentSession();
        List<UserLoginLogoutSession> ipUsersList = new ArrayList<>();
        String query = "from UserLoginLogoutSession userLogin where userLogin.ipaddress =:ipaddress and userLogin.userId.id IN (select userRole.user.id from UserRole userRole where  userRole.orgLocations.org.id IN (:orgList)) group by userLogin.userId.id";
        ipUsersList = session.createQuery(query).setString("ipaddress", usersUsageReportDTO.getIpAddress()).setParameterList("orgList", usersUsageReportDTO.getOrgId()).list();
        return ipUsersList;
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<UserLoginLogoutSession> getAll()
	{
		Session session = this.sessionFactory.getCurrentSession();
		return session.createQuery("from UserLoginLogoutSession").list();
				
	}

	@Override
	public List<String> getUsersByUserId(UsersUsageReportDTO usersUsageReportDTO)
	{
		
		 Session session = this.sessionFactory.getCurrentSession();
	        List<String> ipList = new ArrayList<>();
	        String query = "select uLoginSession.ipaddress from UserLoginLogoutSession uLoginSession where uLoginSession.userId.id IN (select userRole.user.id from UserRole userRole where userRole.user.id = :userId) group by uLoginSession.ipaddress";
	        ipList = session.createQuery(query).setLong("userId", usersUsageReportDTO.getId()).list();
	        return ipList;
	}
    
    

}