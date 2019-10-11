package com.bis.sourcelead.userMerketplace.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.CandidateMarketPlaceDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.userMarketplace.dao.AddProductDao;
import com.bis.sourcelead.userMarketplace.dto.MarketPlaceProductsDTO;
import com.bis.sourcelead.userMarketplace.entity.MarketPlaceProductFilesHistory;
import com.bis.sourcelead.userMarketplace.entity.MarketPlaceProducts;
import com.bis.sourcelead.userMarketplace.service.AddProductService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.ibm.icu.text.DecimalFormat;

@Service
public class AddProductServiceImpl implements AddProductService {

	@Autowired
	private CommonUtil commonUtil;

	@Value("${static.path}")
	private String staticPath;

	@Autowired
	AddProductDao addProductDao;

	@Override
	public Response<String> savemarketPlaceDetails(MarketPlaceProductsDTO marketPlaceProductsDTO,
			OrgLocations orgLocations, User user, Org org) throws Exception {
		Date date = new Date();
		Response<String> response = new Response<>();
		MarketPlaceProducts market = new MarketPlaceProducts();
		
		market.setRegionType(marketPlaceProductsDTO.getRegionType());
		market.setPrice(marketPlaceProductsDTO.getPrice());
		market.setProductName(marketPlaceProductsDTO.getProductName());
		market.setProductDescription(marketPlaceProductsDTO.getProductDescription());
		market.setManufacturerName(marketPlaceProductsDTO.getManufacturerName());
		market.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
		market.setComments(marketPlaceProductsDTO.getComments());
		market.setCategory(marketPlaceProductsDTO.getCategory());
		market.setStatus(Constants.RECORD_ACTIVE.toString());
		if(marketPlaceProductsDTO.getCategory() != "" && marketPlaceProductsDTO.getCategory() != null) {
			market.setProductCode(getGenerateProductShortCode(marketPlaceProductsDTO));
		}

		// market.setProductCode(marketPlaceProductsDTO.getProductCode());
		market.setProductShareType(marketPlaceProductsDTO.getProductShareType());

		if(marketPlaceProductsDTO.getManufacturerDate() != "" && marketPlaceProductsDTO.getManufacturerDate() != null) {
			market.setManufacturerDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),marketPlaceProductsDTO.getManufacturerDate()));
		}
		market.setIsNegotiable(marketPlaceProductsDTO.getIsNegotiable());
		market.setCurrentCity(marketPlaceProductsDTO.getCurrentCity());
		market.setPriority(marketPlaceProductsDTO.getPriority());
		market.setCurrencySpecification(marketPlaceProductsDTO.getCurrencySpecification());

		// market.setFileUrl(marketPlaceProductsDTO.getFileUrl());
		market.setShareContactDetails(marketPlaceProductsDTO.getShareContactDetails());
		if(marketPlaceProductsDTO.getFromDate() != "" && marketPlaceProductsDTO.getFromDate() != null) {
			market.setFromDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), marketPlaceProductsDTO.getFromDate()));
		}
		if(marketPlaceProductsDTO.getToDate() != "" && marketPlaceProductsDTO.getToDate() != null) {
			market.setToDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), marketPlaceProductsDTO.getToDate()));
		}
		if(marketPlaceProductsDTO.getWarrantyEndDate() != "" && marketPlaceProductsDTO.getWarrantyEndDate() != null) {
			market.setWarrantyEndDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),marketPlaceProductsDTO.getWarrantyEndDate()));
		}
		
		// setting objects (foreign keys)
		market.setUserId(user);
		market.setUpdatedDate(commonUtil.orgLocationCommonDate(date, orgLocations));
		market.setUpdatedBy(user);
		market.setCreatedDate(commonUtil.orgLocationCommonDate(date, orgLocations));
		market.setCreatedBy(user);
		market.setOrgId(org);
		market.setOrgLocationId(orgLocations);
		// Attach document for product
		
		FileInfo fileInfo = marketPlaceProductsDTO.getFileInfo().get(0);
		market.setFileName(fileInfo.getFileName());
		market.setFileContentType(fileInfo.getContentType());
		market.setFileUrl(fileInfo.getFileURL());
		try {
			FileUploadUtil.moveFile(staticPath, fileInfo, "resumes");
		} catch (Exception e) {
		}
			
			MarketPlaceProductFilesHistory filesHistory = new MarketPlaceProductFilesHistory();
			
			filesHistory.setMarketPlaceProductId(market);
			filesHistory.setUpdatedDate(commonUtil.orgLocationCommonDate(date, orgLocations));
			filesHistory.setUpdatedBy(user);
			filesHistory.setCreatedDate(commonUtil.orgLocationCommonDate(date, orgLocations));
			filesHistory.setCreatedBy(user);
			
			if(marketPlaceProductsDTO.getUploadPhotos() != null) {
				FileInfo fileInfo1 = marketPlaceProductsDTO.getUploadInfo().get(0);
				filesHistory.setUploadedfileName(fileInfo1.getFileName());
				filesHistory.setUpleadedFileContentType(fileInfo1.getContentType());
				filesHistory.setUploadedFileUrl(fileInfo1.getFileURL());
				try {
					FileUploadUtil.moveFile(staticPath, fileInfo1, "resumes");
				} catch (Exception e) {
				}
			}
			
			
			addProductDao.saveMarketPlaceProducts(market);
			  
			  return response;
	}
			
			//addProductDao.saveMarketPlaceProductFilesHistory(filesHistory);
		
			
	@Override
	public ResponseList<MarketPlaceProductsDTO> getMareketPlaceListDetails(MarketPlaceProductsDTO marketPlaceProductsDTO,
					OrgLocations orgLocations, User user, Org org, String searchValue){
				return addProductDao.getMarketPlaceDetailsbyId(marketPlaceProductsDTO, orgLocations, user, searchValue);
		
	}
	
	public String getGenerateProductShortCode(MarketPlaceProductsDTO marketPlaceProductsDTO) throws Exception {
		String productShortCode = "";
		if (marketPlaceProductsDTO.getCategory().equalsIgnoreCase("Home")) {
			productShortCode = Constants.PRODUCT_SHORT_CODE_HOME;
		} else if (marketPlaceProductsDTO.getCategory().equalsIgnoreCase("Vehicles")) {
			productShortCode =  Constants.PRODUCT_SHORT_CODE_VEHICLES;
		} else if (marketPlaceProductsDTO.getCategory().equalsIgnoreCase("Home appliance")) {
			productShortCode = Constants.PRODUCT_SHORT_CODE_HOME_APPLIANCE;
		} else if (marketPlaceProductsDTO.getCategory().equalsIgnoreCase("Utensils")) {
			productShortCode =  Constants.PRODUCT_SHORT_CODE_UTENSILS;
		} else if (marketPlaceProductsDTO.getCategory().equalsIgnoreCase("Electronics/gadgets")) {
			productShortCode =  Constants.PRODUCT_SHORT_CODE_ELECTRONICS;
		} else if (marketPlaceProductsDTO.getCategory().equalsIgnoreCase("Furnitures")) {
			productShortCode =  Constants.PRODUCT_SHORT_CODE_FURNITURES;
		} else if (marketPlaceProductsDTO.getCategory().equalsIgnoreCase("Other")) {
			productShortCode =  Constants.PRODUCT_SHORT_CODE_OTHER;
		}   
		
		Integer generatedIdcount = 0;
		Long number = 1L;
		String requirementId = null;
		 
		StringBuilder sbuilder = new StringBuilder();
		sbuilder.append(productShortCode);
		String generatedUsreId = sbuilder.toString();
		for (int i = 0; i < generatedUsreId.length(); i++) {
			char ch = generatedUsreId.charAt(i);
			if (Character.isAlphabetic(ch)) {
				generatedIdcount++;
			}
		}
		generatedIdcount = generatedIdcount + 1;
		
		  Integer checkNumber =  addProductDao.getProductCategoryCountByCategory(marketPlaceProductsDTO.getCategory()); 
		  if (checkNumber  == null) {
		       requirementId = number.toString(); 
		  } else { 
			  Integer reqId = checkNumber + 1; 
			  System.out.println("reqId >>>"+reqId);
		      requirementId = reqId.toString(); 
		  }
		 
		final DecimalFormat decimalFormat = new DecimalFormat("0");
		String convetredRequirementsId = decimalFormat.format(Long.valueOf(requirementId));
		
		return sbuilder.append(convetredRequirementsId).toString();
	}
	
}
