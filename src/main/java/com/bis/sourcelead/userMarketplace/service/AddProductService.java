package com.bis.sourcelead.userMarketplace.service;

import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.userMarketplace.dto.MarketPlaceProductsDTO;
import com.bis.sourcelead.userMarketplace.entity.MarketPlaceProducts;

public interface AddProductService{

	Response<String> savemarketPlaceDetails(MarketPlaceProductsDTO marketPlaceProductsDTO, OrgLocations orgLocations,
			User user, Org org) throws Exception;


	ResponseList<MarketPlaceProductsDTO> getMareketPlaceListDetails(MarketPlaceProductsDTO marketPlaceProductsDTO,
			OrgLocations orgLocations, User user, Org org, String searchValue);



	
}