package com.bis.sourcelead.marketPlaceService;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.marktePlace.MarketPlaceProducts;
import com.bis.sourcelead.marktePlace.MarketPlaceProductsDTO;

public interface AddProductService {


	MarketPlaceProducts savemarketPlaceDetails(MarketPlaceProductsDTO marketPlaceProductsDTO, OrgLocations orgLocations,
			Long userId);
	
	

}
