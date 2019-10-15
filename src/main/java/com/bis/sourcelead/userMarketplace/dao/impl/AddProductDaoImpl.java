package com.bis.sourcelead.userMarketplace.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.BeanUtils;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.CandidateMarketPlaceDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.CandidateMarketPlace;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.userMarketplace.dao.AddProductDao;
import com.bis.sourcelead.userMarketplace.dto.MarketPlaceProductsDTO;
import com.bis.sourcelead.userMarketplace.entity.MarketPlaceProductFilesHistory;

import com.bis.sourcelead.userMarketplace.entity.MarketPlaceProducts;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.workflow.dao.Impl.RequirementWorkFlowDaoImpl;
import com.bis.sourcelead.workflow.entity.RequirementWorkFlow;
import com.bis.sourcelead.workflow.entity.RequirementWorkFlowStages;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public class AddProductDaoImpl implements AddProductDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private CommonUtil commonUtil;


	private static final Logger logger = LoggerFactory.getLogger(RequirementWorkFlowDaoImpl.class);

	@Override
	public MarketPlaceProducts saveMarketPlaceProducts(MarketPlaceProducts marketPlaceProducts) {
		try {
			if (marketPlaceProducts != null) {
				Session session = this.sessionFactory.getCurrentSession();
				session.saveOrUpdate(marketPlaceProducts);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e + " in saveMarketPlaceProducts Dao");
		}
		return marketPlaceProducts;
	}
	
//	  @Override 
//	  public MarketPlaceProductFilesHistory getFilesHistoryByMarketPlaceId(Long id) {
//	  MarketPlaceProductFilesHistory filesHistory = null; 
//	  try {
//		  Session session = this.sessionFactory.getCurrentSession(); 
//		  filesHistory =(MarketPlaceProductFilesHistory)session.get(MarketPlaceProductFilesHistory.class, id); 
//		  } catch
//	  (HibernateException e) 
//	  {
//	  logger.error("SQL Exception occured while doing getting record -->" + e);
//	  e.printStackTrace();
//	  } logger.debug("got the record id -->" + id); 
//	  return filesHistory; 
//	  }
	

	@Override
	public MarketPlaceProductFilesHistory saveMarketPlaceProductFilesHistory(
			MarketPlaceProductFilesHistory marketPlaceProductFilesHistory) throws Exception {
		try {
			if (marketPlaceProductFilesHistory != null) {
				Session session = this.sessionFactory.getCurrentSession();
				session.saveOrUpdate(marketPlaceProductFilesHistory);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e + " in saveMarketPlaceProductFilesHistory Dao");
		}
		return marketPlaceProductFilesHistory;
	}

	@Override
	public Integer getProductCategoryCountByCategory(String category) {
		Session session = this.sessionFactory.getCurrentSession();
		String query ="select count(*) from MarketPlaceProducts mp where mp.category = :category ";
		List<Long> categoryCount = session.createQuery(query.toString()).setString("category", category).list();
			Integer count = 0;
			if(categoryCount != null && !categoryCount.isEmpty()){
					count = categoryCount.get(0).intValue();
			}
			return count;
	}
	
	@Override
	public ResponseList<MarketPlaceProductsDTO> getMarketPlaceDetailsbyId(MarketPlaceProductsDTO marketPlaceProductsDTO,
			OrgLocations orgLocations, User user, String searchValue) {
		Session session = this.sessionFactory.getCurrentSession();
		Date date = new Date();
		List<MarketPlaceProducts> marketplaceProducts = null;
		ResponseList<MarketPlaceProductsDTO> responseList = new ResponseList<>();
		List<Long> countlist = null;
		Integer filterCount = 0;
		String formattedDate =  null;
		
		String listQuery = "from MarketPlaceProducts marketplaceProducts where marketplaceProducts.status = 1 and marketplaceProducts.orgLocationId = "
					+ orgLocations.getId() 
					+ "and marketplaceProducts.status = 1 and marketplaceProducts.createdBy=:userId ORDER BY marketplaceProducts.id DESC ";
		
		String listQueryCount = "select count(*) from MarketPlaceProducts marketplaceProducts  where  marketplaceProducts.status = 1  and marketplaceProducts.orgLocationId = "
				+ orgLocations.getId() ;
		
		Query hqlQuery = session.createQuery(listQuery).setLong("userId", user.getId());
		marketplaceProducts = hqlQuery.setFirstResult(marketPlaceProductsDTO.getStart())
				.setMaxResults(marketPlaceProductsDTO.getLength()).list();
		Query hqlQuery1 = session.createQuery(listQueryCount);
		countlist = hqlQuery1.list();
		int totalCount = 0;
		if (marketPlaceProductsDTO != null) {
			if (countlist != null && !countlist.isEmpty()) {
				totalCount = countlist.iterator().next().intValue();
				filterCount = totalCount;
			}
		}
		responseList.setRecordsTotal(totalCount);
		
		List<MarketPlaceProductsDTO> marketPlaceProductsDto = new ArrayList<MarketPlaceProductsDTO>();
		for (MarketPlaceProducts source : marketplaceProducts) {
			
			MarketPlaceProductsDTO productDto = new MarketPlaceProductsDTO();
			BeanUtils.copyProperties(source, productDto);
			productDto.setProductCode(source.getProductCode());
			productDto.setCategory(source.getCategory());
			productDto.setManufacturerName(source.getManufacturerName());
			//productDto.setManufacturerDate(source.getManufacturerDate());
	        productDto.setPrice(source.getPrice());
	        productDto.setCurrencySpecification(source.getCurrencySpecification());
	        productDto.setPriority(source.getPriority());
	        productDto.setIsNegotiable(source.getIsNegotiable());
	        productDto.setShareContactDetails(source.getShareContactDetails());
	        productDto.setProductShareType(source.getProductShareType());
	        productDto.setProductDescription(source.getProductDescription());
	        formattedDate =  commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), source.getManufacturerDate());
	        productDto.setManufacturerDate(formattedDate);
	        
	        marketPlaceProductsDto.add(productDto);
		   responseList.setData(marketPlaceProductsDto);
	
		
	}
		return responseList;
	
	}

	@Override
	public List<MarketPlaceProducts> getMarketPlaceListByIdAndOrgLocationId(String uuid, Long id) throws Exception {
		List<MarketPlaceProducts> marketPlaceList = new ArrayList<MarketPlaceProducts>();
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from MarketPlaceProducts marketPlaceList where marketPlaceList.id.uuid =:uuid and marketPlaceList.orgLocationId.id=:id";
			marketPlaceList = session.createQuery(query).setString("uuid", uuid).setLong("id", id).list();
			return marketPlaceList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	
	}
	
}


