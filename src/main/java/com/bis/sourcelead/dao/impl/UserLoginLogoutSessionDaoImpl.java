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

import com.bis.sourcelead.dao.UserLoginLogoutSessionDao;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UsersUsageReportDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.UserLoginLogoutSession;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
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
    @Transactional(rollbackFor = { Exception.class })
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
    @Transactional(rollbackFor = { Exception.class })
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
    @Transactional(rollbackFor = { Exception.class })
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
    @Transactional(rollbackFor = { Exception.class })
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
    @Transactional(rollbackFor = { Exception.class })
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
    @Transactional(rollbackFor = { Exception.class })
    public List<String> getMultipleIps(UsersUsageReportDTO usersUsageReportDTO)
    {
        Session session = this.sessionFactory.getCurrentSession();
        List<String> ipList = new ArrayList<>();
        String query = "select uLoginSession.ipaddress from UserLoginLogoutSession uLoginSession where uLoginSession.userId.id IN (select userRole.user.id from UserRole userRole where  userRole.orgLocations.org.id IN (:orgList)) group by uLoginSession.ipaddress";
        ipList = session.createQuery(query).setParameterList("orgList", usersUsageReportDTO.getOrgId()).list();
        return ipList;
    }

    @Override
    @Transactional(rollbackFor = { Exception.class })
    public List<Long> getUsersListByIpAddress(String ipAddress,List<Long> list)
    {
        Session session = this.sessionFactory.getCurrentSession();
        List<Long> ipList = new ArrayList<>();
        String query = "select userLogin.userId.id from UserLoginLogoutSession userLogin where userLogin.ipaddress =:ipaddress and userLogin.userId.id IN (select userRole.user.id from UserRole userRole where  userRole.orgLocations.org.id IN (:orgList)) group by userLogin.userId.id";
        ipList = session.createQuery(query).setString("ipaddress", ipAddress).setParameterList("orgList", list).list();
        return ipList;
    }

    @Override
    @Transactional(rollbackFor = { Exception.class })
    public List<UserLoginLogoutSession> getUsersByipAddress(UsersUsageReportDTO usersUsageReportDTO)
    {
        Session session = this.sessionFactory.getCurrentSession();
        List<UserLoginLogoutSession> ipUsersList = new ArrayList<>();
        String query = "from UserLoginLogoutSession userLogin where userLogin.ipaddress =:ipaddress and userLogin.userId.id IN (select userRole.user.id from UserRole userRole where  userRole.orgLocations.org.id IN (:orgList)) group by userLogin.userId.id";
        ipUsersList = session.createQuery(query).setString("ipaddress", usersUsageReportDTO.getIpAddress()).setParameterList("orgList", usersUsageReportDTO.getOrgId()).list();
        return ipUsersList;
    }
    
    

}