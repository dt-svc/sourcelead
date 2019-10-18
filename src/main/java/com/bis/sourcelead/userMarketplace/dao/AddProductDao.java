package com.bis.sourcelead.userMarketplace.dao;

import java.util.List;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.userMarketplace.dto.MarketPlaceProductsDTO;
import com.bis.sourcelead.userMarketplace.entity.MarketPlaceProductFilesHistory;
import com.bis.sourcelead.userMarketplace.entity.MarketPlaceProducts;

public interface AddProductDao {
	
	MarketPlaceProducts saveMarketPlaceProducts(MarketPlaceProducts marketPlaceProducts)throws Exception;
	
	MarketPlaceProductFilesHistory saveMarketPlaceProductFilesHistory(MarketPlaceProductFilesHistory marketPlaceProductFilesHistory)throws Exception;
	
	//MarketPlaceProductNegotiationHistory saveMarketPlaceProductNegotiationHistory(MarketPlaceProductNegotiationHistory marketPlaceProductNegotiationHistory)throws Exception;
    
	Integer getProductCategoryCountByCategory(String category);

	ResponseList<MarketPlaceProductsDTO> getMarketPlaceDetailsbyId(MarketPlaceProductsDTO marketPlaceProductsDTO,
			OrgLocations orgLocations, User user, String searchValue);

	//int getMarketPlaceListByIdAndOrgLocationId(String uuid, Long id) throws Exception;

	//String deleteMarketPlaceListByIdAndOrgLocationId(String uuid, Long id) throws Exception;

	Boolean deleteMarketPlaceProductByUuid(String uuid, Long id);

}
