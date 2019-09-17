package com.bis.sourcelead.userMarketplace.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.userMarketplace.dao.AddProductDao;
import com.bis.sourcelead.userMarketplace.entity.MarketPlaceProductFilesHistory;
import com.bis.sourcelead.userMarketplace.entity.MarketPlaceProductNegotiaitionHistory;
import com.bis.sourcelead.userMarketplace.entity.MarketPlaceProducts;
import com.bis.sourcelead.workflow.dao.Impl.RequirementWorkFlowDaoImpl;


@Repository
@Transactional
public class AddProductDaoImpl implements AddProductDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementWorkFlowDaoImpl.class);
	
	@Override
	public MarketPlaceProducts saveMarketPlaceProducts(MarketPlaceProducts marketPlaceProducts) {
		try{
			if(marketPlaceProducts != null){
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(marketPlaceProducts);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e+" in saveMarketPlaceProducts Dao");
		}
		return marketPlaceProducts;
	}

	@Override
	public MarketPlaceProductFilesHistory saveMarketPlaceProductFilesHistory(MarketPlaceProductFilesHistory marketPlaceProductFilesHistory) throws Exception {
		try{
			if(marketPlaceProductFilesHistory != null){
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(marketPlaceProductFilesHistory);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e+" in saveMarketPlaceProductFilesHistory Dao");
		}
		return marketPlaceProductFilesHistory;
	}

	@Override
	public MarketPlaceProductNegotiaitionHistory saveMarketPlaceProductNegotiaitionHistory(MarketPlaceProductNegotiaitionHistory marketPlaceProductNegotiaitionHistory) throws Exception {
		try{
			if(marketPlaceProductNegotiaitionHistory != null){
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(marketPlaceProductNegotiaitionHistory);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e+" in saveMarketPlaceProductNegotiaitionHistory Dao");
		}
		return marketPlaceProductNegotiaitionHistory;
	}

	@Override
	public MarketPlaceProducts deleteMarketPlaceProducts() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}
