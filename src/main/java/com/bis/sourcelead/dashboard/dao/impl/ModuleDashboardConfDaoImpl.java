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

import com.bis.sourcelead.dashboard.dao.ModuleDashboardConfDao;
import com.bis.sourcelead.dashboard.entity.CustomModuleConfiguration;

@Repository
public class ModuleDashboardConfDaoImpl implements ModuleDashboardConfDao{

private static final Logger logger = LoggerFactory.getLogger(CustomDashBoardConfDaoImpl.class);
	
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	@Transactional
	public CustomModuleConfiguration saveModuleConfiguration(CustomModuleConfiguration customModConf)
	{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (customModConf != null) {
				session.saveOrUpdate(customModConf);
				logger.info("Module Configuration saved for dashboard..!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customModConf;
	}


	@Override
	@Transactional
	public CustomModuleConfiguration getSubModuleByIdAndConfDashBoardId(Long subModuleId,Long confDashBoardId)
	{
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from CustomModuleConfiguration customModConf where customModConf.subTabId = :subModuleId and customModConf.dashBoardConfId.id=:dashBoardConfId";
			List<CustomModuleConfiguration> list = session.createQuery(query).setLong("subModuleId", subModuleId).setLong("dashBoardConfId", confDashBoardId).list();
			if(list != null && list.size() >0) {
				return list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	@Transactional
	public boolean deleteModuleConfByDashBoardId(Long dashBoardConfId)
	{
		boolean isDeleted = false;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "delete from CustomModuleConfiguration custMod where custMod.dashBoardConfId.id =:dashBoardConfId ";
			Query qry = session.createQuery(query).setLong("dashBoardConfId", dashBoardConfId);
			int i = qry.executeUpdate();
			if( i >0) {
				isDeleted = true;
			}
			return isDeleted;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isDeleted;
	}
}
