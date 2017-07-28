package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ActionHistoryDao;
import com.bis.sourcelead.dao.ActionRolesDao;
import com.bis.sourcelead.entity.ActionHistory;
import com.bis.sourcelead.entity.ActionNames;
import com.bis.sourcelead.entity.UserLoginLogoutSession;
import com.sun.star.util.DateTime;

@Repository
public class ActionHistoryDaoImpl implements ActionHistoryDao {
	private static final Logger logger = LoggerFactory.getLogger(ExpensesCategoryDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private ActionRolesDao actionRolesDao;

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
	public List<ActionHistory> getAllActionHistoryList(Long userSessionId) {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ActionHistory ac left join fetch ac.userLoginLogoutSessionId where ac.userLoginLogoutSessionId.id = :userSessionId ";
		List<ActionHistory> actionHistoryList = session.createQuery(query.toString()).setLong("userSessionId", userSessionId).setCacheable(true).list();
		return actionHistoryList;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void saveActionHistory(String methodName,UserLoginLogoutSession userLoginLogoutSession) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		
		ActionNames actionNames = getActionNameUsingMethodName(methodName);

		if(actionNames!=null)
		{
			ActionHistory actionHistory = new ActionHistory();
			actionHistory.setUserId(userLoginLogoutSession.getUserId().getId());
			actionHistory.setUserLoginLogoutSessionId(userLoginLogoutSession);
			actionHistory.setActionName(actionNames.getActionName());
			actionHistory.setActionPerformed("You have performed "+ actionNames.getActionName()+" Action ");
			actionHistory.setActionTime(new DateTime());

			try {
				if (actionHistory != null) {
					session.saveOrUpdate(actionHistory);
				}
			} catch (Exception exception) {
				//					    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"expenses"));
				//						throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
			}


		}
		
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ActionNames getActionNameUsingMethodName(String methodName) throws Exception {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		logger.debug("getActionNameUsingMethodName start");
		String query = "from ActionNames  actNames  where actNames.methodName =:methodName ";
		List<ActionNames> actionNames = session.createQuery(query).setString("methodName", methodName).setCacheable(true).list();
		logger.debug("getActionNameUsingMethodName end");
		if (!actionNames.isEmpty() && actionNames!=null) {
			return actionNames.iterator().next();
		} else {
			return null;
		}
	}



}

