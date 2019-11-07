package com.bis.sourcelead.dashboard.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dashboard.dao.CustomDashBoardViewDao;
import com.bis.sourcelead.dashboard.dto.ConfigureDashBoardViewDTO;
import com.bis.sourcelead.dashboard.entity.CustomReportConfiguration;

@Repository
public class CustomDashBoardViewDaoImpl implements CustomDashBoardViewDao{
	
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public List<ConfigureDashBoardViewDTO> getConfiguredModuleByDashBoardId(Long dashBoardId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<ConfigureDashBoardViewDTO> dashBoardViewList = null;
		try {
                StringBuilder qry = new StringBuilder();
                qry.append("select cusMod.ID as customModuleCofigId,")
                .append("cusMod.SUBTAB_ID as subTabId ,")
                .append("cusMod.DASHBOARD_CONFI_ID dashBoardConfiId,")
                .append("subTab.SUBTAB_NAME as subTabName,")
                .append("subTab.SUBTAB_DISPLAY_NAME displayTabName")
                .append("  from CUSTOM_MODULE_CONF cusMod,SUB_TABS subTab")
                .append("  where cusMod.SUBTAB_ID = subTab.ID AND cusMod.DASHBOARD_CONFI_ID = :dashBoardId");
			
				Query queryObj = session.createSQLQuery(qry.toString());
				queryObj.setLong("dashBoardId", dashBoardId);
				queryObj.setResultTransformer(Transformers.aliasToBean(ConfigureDashBoardViewDTO.class));
				dashBoardViewList = queryObj.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dashBoardViewList;

	
	}

		@Override
		@Transactional
		public List<CustomReportConfiguration> getConfiguredReportByDashBoardId(Long dashBoardId)
		{
			try {
				Session session = this.sessionFactory.getCurrentSession();
				String query = "from CustomReportConfiguration reportConf where reportConf.dashBoardConfId.id=:dashBoardConfId";
				return session.createQuery(query).setLong("dashBoardConfId", dashBoardId).list();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;	
		}
}