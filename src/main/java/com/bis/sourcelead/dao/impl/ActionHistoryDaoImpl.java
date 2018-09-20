package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ActionHistoryDao;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.entity.ActionHistory;
import com.bis.sourcelead.entity.ActionNames;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.UserLoginLogoutSession;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class ActionHistoryDaoImpl implements ActionHistoryDao {
	private static final Logger logger = LoggerFactory.getLogger(ActionHistoryDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private OrgLocationService orgLocationService;
	
	@Autowired
	private CommonUtil commonUtil;

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


	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}


	@Override
	@Transactional
	public List<ActionHistory> getAllActionHistoryList(Long userSessionId, Long orgLocation) {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ActionHistory ac left join fetch ac.userLoginLogoutSessionId where ac.userLoginLogoutSessionId.id = :userSessionId and ac.orgLocations.id =:orgLocation";
		List<ActionHistory> actionHistoryList = session.createQuery(query.toString()).setLong("userSessionId", userSessionId).setLong("orgLocation", orgLocation).list();
		return actionHistoryList;
	}
	
	@Override
	@Transactional
	public List<ActionHistory> getAllActionHistoryListByActionUUID(String actionUUID, Long orgLocation) {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ActionHistory ac left join fetch ac.userLoginLogoutSessionId where ac.userLoginLogoutSessionId.actionUUID = :actionUUID and ac.orgLocations.id =:orgLocation";
		List<ActionHistory> actionHistoryList = session.createQuery(query.toString()).setString("actionUUID", actionUUID).setLong("orgLocation", orgLocation).list();
		return actionHistoryList;
	}
	
	@Override
	@Transactional
	public List<ActionHistory> getAllOrglocationsActionHistoryList(Long userSessionId) {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ActionHistory ac left join fetch ac.userLoginLogoutSessionId left join fetch ac.orgLocations where ac.userLoginLogoutSessionId.id = :userSessionId GROUP BY ac.orgLocations ";
		List<ActionHistory> actionHistoryList = session.createQuery(query.toString()).setLong("userSessionId", userSessionId).list();
		return actionHistoryList;
	}
	
	@Override
	@Transactional
	public List<ActionHistory> getAllOrglocationsActionHistoryListActionUUID(String actionUUID) {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ActionHistory ac left join fetch ac.userLoginLogoutSessionId left join fetch ac.orgLocations where ac.userLoginLogoutSessionId.actionUUID = :actionUUID GROUP BY ac.orgLocations ";
		List<ActionHistory> actionHistoryList = session.createQuery(query.toString()).setString("actionUUID", actionUUID).list();
		return actionHistoryList;
	}

	@Override
	@Async
	@Transactional(rollbackFor = { Exception.class })
	public void saveActionHistory(String methodName,UserLoginLogoutSession userLoginLogoutSession,OrgLocations loginOrgLocation,String type) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<ActionNames> actionNamesList = getActionNameUsingMethodName(methodName);
		ActionHistory actionHistory = new ActionHistory();
		if(actionNamesList != null && !actionNamesList.isEmpty()){
			if(actionNamesList.size() >1){
				for(ActionNames actionName : actionNamesList){
					if(actionName.getActionName().contains(type)){
						actionHistory.setActionName(actionName.getActionName());
						actionHistory.setActionPerformed("You have performed "+ actionName.getActionName()+" Action ");
						actionHistory.setOrgLocations(loginOrgLocation);
						actionHistory.setUserId(userLoginLogoutSession.getUserId().getId());
						actionHistory.setUserLoginLogoutSessionId(userLoginLogoutSession);
						Date date = new Date();
						actionHistory.setActionTime(commonUtil.orgLocationCommonDate(date, loginOrgLocation));

					}
				}
			}else
			{
				if(methodName.equalsIgnoreCase("loginSession"))
				{
					List<ActionHistory> list = getAllOrglocationsActionHistoryList(userLoginLogoutSession.getId());
					if(!(list.size()>0)){
					ActionNames actionNames = actionNamesList.get(0);
					actionHistory.setActionName(actionNames.getActionName());
					actionHistory.setActionPerformed("You have performed "+ actionNames.getActionName()+" Action ");
					//test
					actionHistory.setOrgLocations(loginOrgLocation);
					actionHistory.setUserId(userLoginLogoutSession.getUserId().getId());
					actionHistory.setUserLoginLogoutSessionId(userLoginLogoutSession);
					// saving the Activity Time based on the time zone 
					Date date = new Date();
					/*DateTime dt = new DateTime(date);
					DateTimeZone dtZone = DateTimeZone.forID(loginOrgLocation.getTimezone().getTimeZoneId());
					DateTime dtus = dt.withZone(dtZone); 
					Date dateWithTimezone = dtus.toLocalDateTime().toDate();*/
					actionHistory.setActionTime(commonUtil.orgLocationCommonDate(date, loginOrgLocation));
					}
				}
				else
				{
				ActionNames actionNames = actionNamesList.get(0);
				actionHistory.setActionName(actionNames.getActionName());
				actionHistory.setActionPerformed("You have performed "+ actionNames.getActionName()+" Action ");
				//test
				actionHistory.setOrgLocations(loginOrgLocation);
				actionHistory.setUserId(userLoginLogoutSession.getUserId().getId());
				actionHistory.setUserLoginLogoutSessionId(userLoginLogoutSession);
				// saving the Activity Time based on the time zone 
				Date date = new Date();
				/*DateTime dt = new DateTime(date);
				DateTimeZone dtZone = DateTimeZone.forID(loginOrgLocation.getTimezone().getTimeZoneId());
				DateTime dtus = dt.withZone(dtZone); 
				Date dateWithTimezone = dtus.toLocalDateTime().toDate();*/
				actionHistory.setActionTime(commonUtil.orgLocationCommonDate(date, loginOrgLocation));
				}
			}
			
			try {
				if (actionHistory != null && actionHistory.getUserId() != null) {
					session.saveOrUpdate(actionHistory);
				}
			} catch (Exception exception) {
				StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"ActionHistory"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
			}
		}
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ActionNames> getActionNameUsingMethodName(String methodName) throws Exception {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		logger.debug("getActionNameUsingMethodName start");
		String query = "from ActionNames  actNames  where actNames.methodName =:methodName ";
		List<ActionNames> actionNames = session.createQuery(query).setString("methodName", methodName).list();
		logger.debug("getActionNameUsingMethodName end");
			return actionNames;
	}
	
	

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public UserDTO getAllActionHistoryByUserId(Long userId)throws Exception
	{
		UserDTO countMap = new UserDTO();
		Session session = this.sessionFactory.getCurrentSession();
		
		String query = "select count(*) from ActionHistory  actHistory  where actHistory.userId =:userId ";
		Query actionHistory = session.createQuery(query).setLong("userId", userId);
		Long count = (Long) actionHistory.uniqueResult();
		countMap.setNoOfActivities(count.toString());
		/*List<Long> countlist = actionHistory.list();
		
		String query1 = "from ActionHistory act where act.userId = :userId order by act.id desc";
		List<ActionHistory> actionHistorylist = session.createQuery(query1).setLong("userId", userId).list();
		Date lastActivity = null ;
		if(actionHistorylist != null && actionHistorylist.size() > 0 && actionHistorylist.get(0) != null){
			lastActivity = actionHistorylist.get(0).getActionTime();
			SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			countMap.setLastActivity(df.format(lastActivity));
		}*/
		
		return countMap;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ActionHistory> getAllMultipleIpsByUserId(Long userId)throws Exception
	{
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserLoginLogoutSession  userLoginLogoutSession  where userLoginLogoutSession.userId.id =:userId group by userLoginLogoutSession.ipaddress";
		List<ActionHistory> action = session.createQuery(query).setLong("userId", userId).list();
		return action;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Long> getAllUserIdsByFrequencyBasedOnActivity(Date[] freqDates)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ActionHistory actHis where actHis.actionTime >= :startDate and actHis.actionTime <= :endDate group by actHis.userId";
		List<ActionHistory> actHisList = session.createQuery(query).setParameter("startDate", freqDates[0]).setParameter("endDate", freqDates[1]).list();
		List<Long> userIds = new ArrayList<>();
		for(ActionHistory actHis : actHisList){
			userIds.add(actHis.getUserId());
		}
		return userIds;
	}



}

