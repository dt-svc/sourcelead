package com.bis.sourcelead.dao.impl;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ActionHistoryDao;
import com.bis.sourcelead.entity.ActionHistory;
import com.bis.sourcelead.entity.ActionNames;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.UserLoginLogoutSession;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.entity.UserLoginLogoutSession;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.service.OrgLocationService;

@Repository
public class ActionHistoryDaoImpl implements ActionHistoryDao {
	private static final Logger logger = LoggerFactory.getLogger(ExpensesCategoryDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private OrgLocationService orgLocationService;

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
		List<ActionHistory> actionHistoryList = session.createQuery(query.toString()).setLong("userSessionId", userSessionId).setLong("orgLocation", orgLocation).setCacheable(true).list();
		return actionHistoryList;
	}
	
	@Override
	@Transactional
	public List<ActionHistory> getAllOrglocationsActionHistoryList(Long userSessionId) {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ActionHistory ac left join fetch ac.userLoginLogoutSessionId left join fetch ac.orgLocations where ac.userLoginLogoutSessionId.id = :userSessionId GROUP BY ac.orgLocations ";
		List<ActionHistory> actionHistoryList = session.createQuery(query.toString()).setLong("userSessionId", userSessionId).setCacheable(true).list();
		return actionHistoryList;
	}

	@Override
	@Async
	@Transactional(rollbackFor = { Exception.class })
	public void saveActionHistory(String methodName,UserLoginLogoutSession userLoginLogoutSession,OrgLocations loginOrgLocation,String type) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<ActionNames> actionNamesList = getActionNameUsingMethodName(methodName);
		ActionHistory actionHistory = new ActionHistory();
		if(actionNamesList.size() >1){
			for(ActionNames actionName : actionNamesList){
				if(actionName.getActionName().contains(type)){
					actionHistory.setActionName(actionName.getActionName());
					actionHistory.setActionPerformed("You have performed "+ actionName.getActionName()+" Action ");
				}
			}
		}else
		{
			ActionNames actionNames = actionNamesList.get(0);
			actionHistory.setActionName(actionNames.getActionName());
			actionHistory.setActionPerformed("You have performed "+ actionNames.getActionName()+" Action ");
		}

		actionHistory.setOrgLocations(loginOrgLocation);
		actionHistory.setUserId(userLoginLogoutSession.getUserId().getId());
		actionHistory.setUserLoginLogoutSessionId(userLoginLogoutSession);
		// saving the Activity Time based on the time zone 
		Date date = new Date();
		DateTime dt = new DateTime(date);
		DateTimeZone dtZone = DateTimeZone.forID(loginOrgLocation.getTimezone().getTimeZoneId());
		DateTime dtus = dt.withZone(dtZone); 
		Date dateWithTimezone = dtus.toLocalDateTime().toDate();
		actionHistory.setActionTime(dateWithTimezone);
		try {
			if (actionHistory != null) {
				session.saveOrUpdate(actionHistory);
			}
		} catch (Exception exception) {
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"ActionHistory"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ActionNames> getActionNameUsingMethodName(String methodName) throws Exception {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		logger.debug("getActionNameUsingMethodName start");
		String query = "from ActionNames  actNames  where actNames.methodName =:methodName ";
		List<ActionNames> actionNames = session.createQuery(query).setString("methodName", methodName).setCacheable(true).list();
		logger.debug("getActionNameUsingMethodName end");
			return actionNames;
	}



}

