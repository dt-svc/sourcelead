package com.bis.sourcelead.userMarketplace.service;


import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.userMarketplace.dto.MarketPlaceProductsDTO;

public interface AddProductService{

	Response<String> savemarketPlaceDetails(MarketPlaceProductsDTO marketPlaceProductsDTO, OrgLocations orgLocations,
			User user, Org org) throws Exception;


	ResponseList<MarketPlaceProductsDTO> getMareketPlaceListDetails(MarketPlaceProductsDTO marketPlaceProductsDTO,
			OrgLocations orgLocations, User user, Org org, String searchValue);


	Boolean deleteMarketPlaceProductByUuid(String uuid, Long id) throws Exception;

	MarketPlaceProductsDTO getMarketPlaceDetailsByUuid(String uuid, OrgLocations orgLocations) throws Exception;
	
	Response<String> saveEditMarketPlaceDetails(MarketPlaceProductsDTO marketPlaceProductsDTO, OrgLocations orgLocations,
			User user, Org org, String uuid)throws Exception;
	
}