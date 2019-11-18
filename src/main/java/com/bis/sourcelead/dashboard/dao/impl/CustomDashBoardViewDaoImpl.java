package com.bis.sourcelead.dashboard.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dashboard.dao.CustomDashBoardViewDao;
import com.bis.sourcelead.dashboard.dto.CommonDataDto;
import com.bis.sourcelead.dashboard.dto.ConfigureDashBoardViewDTO;
import com.bis.sourcelead.dashboard.entity.CustomReportConfiguration;
import com.bis.sourcelead.dashboard.util.DashBoardUtil;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.MarketplacePurchaseOrder;
import com.bis.sourcelead.entity.MarketplaceSalesOrder;
import com.bis.sourcelead.util.CommonUtil;

import bsh.StringUtil;

@Repository
public class CustomDashBoardViewDaoImpl implements CustomDashBoardViewDao{
	
	
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private DashBoardUtil dashBoardUtil;
	
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
                .append("  where cusMod.SUBTAB_ID = subTab.ID AND cusMod.DASHBOARD_CONFI_ID = :dashBoardId AND cusMod.status = 1");
			
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
				String query = "from CustomReportConfiguration reportConf where reportConf.dashBoardConfId.id=:dashBoardConfId and reportConf.status = 1";
				return session.createQuery(query).setLong("dashBoardConfId", dashBoardId).list();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;	
		}

		@SuppressWarnings("unchecked")
		@Override
		@Transactional
		public String getMarketplacePurchaseOrderDashBoard(CommonDataDto commonDataDto) {
		     
			String jsonData = "";
			try {
				Session session = this.sessionFactory.getCurrentSession();
				String query="from MarketplacePurchaseOrder cmpth where cmpth.orgLocationId.id = :orgLocationId order by cmpth.id DESC";
				Query qeury = session.createQuery(query).setLong("orgLocationId",  commonDataDto.getOrgLocation().getId());
				List<MarketplacePurchaseOrder>  marketplacePurchaseOrderList = qeury.setMaxResults(5).list();
				if(marketplacePurchaseOrderList != null & marketplacePurchaseOrderList.size()>0) {
					List<Map<String,String>> list = new ArrayList<Map<String,String>>();
					for(MarketplacePurchaseOrder markPurOrd : marketplacePurchaseOrderList) {
						Map<String,String> map = new HashMap<String, String>();
						map.put("Order No", markPurOrd.getOrderNo());
						map.put("Company Name", markPurOrd.getCompanyName());
						map.put("Total Price", String.valueOf(markPurOrd.getTotalPrice()));
						map.put("Order Date", commonUtil.dateStrFromOrgDateFormatter(commonDataDto.getOrgLocation().getDateFormat(), markPurOrd.getOrderDate()));
						map.put("Payment Status", markPurOrd.getPaymentStatus());
						list.add(map);
					}
					 jsonData = dashBoardUtil.getMapListToJsonString(list);
				}
				
			
			}
			catch(Exception e) {
	       	 e.printStackTrace();
	        }
			return jsonData;
		}

		@Override
		@Transactional
		public String getMarketplaceSalesOrderDashBoard(CommonDataDto commonDataDto) {
			
			String jsonData = "";
			try {
				Session session = this.sessionFactory.getCurrentSession();
				String query="from MarketplaceSalesOrder salesOrder where salesOrder.orgLocationId.id = :orgLocationId order by salesOrder.id DESC";
				Query qeury = session.createQuery(query).setLong("orgLocationId",  commonDataDto.getOrgLocation().getId());
				List<MarketplaceSalesOrder>  marketplaceSalesOrderList = qeury.setMaxResults(5).list();
				if(marketplaceSalesOrderList != null & marketplaceSalesOrderList.size()>0) {
					List<Map<String,String>> list = new ArrayList<Map<String,String>>();
					for(MarketplaceSalesOrder markSalesOrd : marketplaceSalesOrderList) {
						Map<String,String> map = new HashMap<String, String>();
						map.put("Order No", markSalesOrd.getOrderNo());
						map.put("Company Name", markSalesOrd.getCompanyName());
						map.put("Total Resumes Price", String.valueOf(markSalesOrd.getTotalResumePrice()));
						map.put("Order Date", commonUtil.dateStrFromOrgDateFormatter(commonDataDto.getOrgLocation().getDateFormat(), markSalesOrd.getOrderDate()));
						map.put("Payment Status", markSalesOrd.getPaymentStatus());
						list.add(map);
					}
					 jsonData = dashBoardUtil.getMapListToJsonString(list);
				}
				
			
			}
			catch(Exception e) {
	       	 e.printStackTrace();
	        }
			return jsonData;
		}

		@Override
		@Transactional
		public String getRecentlyAddedCandidates(CommonDataDto commonDataDto) {

			String jsonData = "";
			try {
				Session session = this.sessionFactory.getCurrentSession();
				String query="from Candidate can where can.orgLocationId.id = :orgLocationId order by can.id DESC";
				Query qeury = session.createQuery(query).setLong("orgLocationId",  commonDataDto.getOrgLocation().getId());
				List<Candidate>  marketplaceSalesOrderList = qeury.setMaxResults(5).list();
				if(marketplaceSalesOrderList != null & marketplaceSalesOrderList.size()>0) {
					List<Map<String,String>> list = new ArrayList<Map<String,String>>();
					for(Candidate can : marketplaceSalesOrderList) {
						Map<String,String> map = new HashMap<String, String>();
						if(can.getLastName() != null && !"".equalsIgnoreCase(can.getLastName())) {
							map.put("Candidate Name", can.getFirstName().concat(" ").concat(can.getLastName()));
						}else {
							map.put("Candidate Name",can.getFirstName());
						}
						map.put("Email", can.getEmail());
						if(StringUtils.isNotBlank(can.getCountry())) {
							map.put("country", can.getCountry());
						}else{
							map.put("country", "--");
						}
						if(StringUtils.isNotBlank(can.getMobilePhone())) {
							map.put("mobilePhone", can.getMobilePhone());
						}else{
							map.put("mobilePhone", "--");
						}
						if(StringUtils.isNotBlank(can.getDesignation())) {
							map.put("designation", can.getDesignation());
						}else{
							map.put("designation", "--");
						}
						list.add(map);
					}
					 jsonData = dashBoardUtil.getMapListToJsonString(list);
				}
				
			
			}
			catch(Exception e) {
	       	 e.printStackTrace();
	        }
			return jsonData;
		}
		
		
		
}