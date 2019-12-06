package com.bis.sourcelead.dashboard.dao.impl;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dashboard.dao.CustomDashBoardConfDao;
import com.bis.sourcelead.dashboard.entity.CustomDashBoardConfiguration;
import com.bis.sourcelead.dashboard.entity.EnableDashBoardConfiguration;
import com.bis.sourcelead.entity.SubTabs;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public class CustomDashBoardConfDaoImpl implements CustomDashBoardConfDao{

	private static final Logger logger = LoggerFactory.getLogger(CustomDashBoardConfDaoImpl.class);
	
	
	@Autowired
	private SessionFactory sessionFactory;

	
	@Override
	public CustomDashBoardConfiguration saveCustomDashBoardConf(CustomDashBoardConfiguration customDashBoardConf)
	{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (customDashBoardConf != null) {
				session.saveOrUpdate(customDashBoardConf);
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return customDashBoardConf;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<CustomDashBoardConfiguration> getCustomConfDashBoardByLocationId(Long locationId)
	{
		List<CustomDashBoardConfiguration> customDashBoardConf = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from CustomDashBoardConfiguration customDashBoardConf where customDashBoardConf.orgLocations.id = :orgLocationId  AND customDashBoardConf.status = 1";
			customDashBoardConf = session.createQuery(query).setLong("orgLocationId", locationId).list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return customDashBoardConf;
	}


	@SuppressWarnings("unchecked")
	@Override
	public CustomDashBoardConfiguration getCustomDashboardConfByUUID(String customDashBoardUUID, Long orgLocationId)
	{
		CustomDashBoardConfiguration customDashBoardConf = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from CustomDashBoardConfiguration customDashBoardConf where customDashBoardConf.dashboardConfUUID = :dashboardConfUUID and customDashBoardConf.orgLocations.id = :orgLocationId  AND customDashBoardConf.status = 1";
			List<CustomDashBoardConfiguration> list = session.createQuery(query).setString("dashboardConfUUID", customDashBoardUUID).setLong("orgLocationId", orgLocationId).list();
			if(list != null && list.size() >0) {
				customDashBoardConf = list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customDashBoardConf;
	}


	@SuppressWarnings("unchecked")
	@Override
	public CustomDashBoardConfiguration getCustomDashboardConfById(Long customDashBoardConfID, Long orgLocationId)
	{
		CustomDashBoardConfiguration customDashBoardConf = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from CustomDashBoardConfiguration customDashBoardConf where customDashBoardConf.id = :customDashBoardConfID and customDashBoardConf.orgLocations.id = :orgLocationId";
			List<CustomDashBoardConfiguration> list = session.createQuery(query).setLong("customDashBoardConfID", customDashBoardConfID).setLong("orgLocationId", orgLocationId).list();
			if(list != null && list.size() >0) {
				customDashBoardConf = list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customDashBoardConf;
	}


	@Override
	public int deleteExistedDashBoardAssociates(Long customDashBoardConfId)
	{
		int isDeleted = 0;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "delete from CustomDashBoardAssociatesConf assoConf where assoConf.dashBoardConfId.id =:customDashBoardConfId ";
			Query qry = session.createQuery(query).setLong("customDashBoardConfId", customDashBoardConfId);
			int i = qry.executeUpdate();
			return i;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isDeleted;
	}


	@Override
	public List<SubTabs> getSubModules(Long tabId)
	{
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from SubTabs sTabs where sTabs.tabs.id = :tabId";
			List<SubTabs> list =  session.createQuery(query).setLong("tabId", tabId).list();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}


	@Override
	public void getCustomConfDashBoardListByOrgLocationId(Long orgLOcatinId,String customDashBoardUUID) {
	    try {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CustomDashBoardConfiguration customDashBoardConf where customDashBoardConf.orgLocations.id = :orgLocationId  AND customDashBoardConf.status = 1 ";
		List<CustomDashBoardConfiguration> list = session.createQuery(query).setLong("orgLocationId", orgLOcatinId).list();
			for (CustomDashBoardConfiguration s : list) {
				if(!customDashBoardUUID.equalsIgnoreCase(s.getDashboardConfUUID())) {
					s.setMakeDefault(false);
				}else {
					s.setMakeDefault(true);
				}
	        	session.saveOrUpdate(s);
	        }	
	}     catch (Exception e) {
		e.printStackTrace();
	}   
		
	}


	@Override
	public EnableDashBoardConfiguration enableCustomDashBoard(EnableDashBoardConfiguration enableDashBoardConf) {

		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (enableDashBoardConf != null) {
				session.saveOrUpdate(enableDashBoardConf);
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return enableDashBoardConf;
	}


	@Override
	public EnableDashBoardConfiguration getEnableDashBoardByLocationId(Long orgLocatinId) {
		EnableDashBoardConfiguration enableEntity = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from EnableDashBoardConfiguration enableConf where enableConf.orgLocations.id = :orgLocationId";
			List<EnableDashBoardConfiguration> list = session.createQuery(query).setLong("orgLocationId", orgLocatinId).list();
			if(list != null && list.size() > 0) {
				enableEntity = list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return enableEntity;   
	}


	@Override
	public Long getNumberOfComponentsForCustomDashBoard(Long customConfDashBoardId) {
		
		Long count = 0l;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "select count(*) from CustomModuleConfiguration moduleConf where moduleConf.dashBoardConfId.id=:customConfDashBoardId and moduleConf.status=1";
			Long moduleCount = (Long)session.createQuery(query).setLong("customConfDashBoardId", customConfDashBoardId).uniqueResult();
			String qry = "select count(*) from CustomReportConfiguration reportConf where reportConf.dashBoardConfId.id=:customConfDashBoardId and reportConf.status=1";
			Long reportModuleCount = (Long)session.createQuery(qry).setLong("customConfDashBoardId", customConfDashBoardId).uniqueResult();
			count = moduleCount + reportModuleCount;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
}
