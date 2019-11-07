package com.bis.sourcelead.dashboard.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dashboard.entity.CustomModuleConfiguration;
import com.bis.sourcelead.dashboard.entity.CustomReportConfiguration;

@Repository
public class ReportsDashboardConfDaoImpl implements ReportsDashboardConfDao{
	
	private static final Logger logger = LoggerFactory.getLogger(ReportsDashboardConfDaoImpl.class);


	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public CustomReportConfiguration saveReportsConfiguration(CustomReportConfiguration customReportsConf)
	{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (customReportsConf != null) {
				session.saveOrUpdate(customReportsConf);
				logger.info("Report Configuration saved for dashboard..!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customReportsConf;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<CustomReportConfiguration> getAllReportConfiByCustomDashBoardReportUUID(String customDashBoardConfUUID)
	{
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from CustomReportConfiguration reportConf where reportConf.dashBoardConfId.dashboardConfUUID=:UUID";
			return session.createQuery(query).setString("UUID", customDashBoardConfUUID).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public boolean deleteReportConfByDashBoardId(Long dashBoardId)
	{
		boolean isDeleted = false;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "delete from CustomReportConfiguration custReport where custReport.dashBoardConfId.id =:dashBoardConfId ";
			Query qry = session.createQuery(query).setLong("dashBoardConfId", dashBoardId);
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
