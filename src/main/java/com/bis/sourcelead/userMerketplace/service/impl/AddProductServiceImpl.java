package com.bis.sourcelead.userMerketplace.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
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
		
		if(marketPlaceProductsDTO.getManufacturerDate() != "" && marketPlaceProductsDTO.getManufacturerDate() != null) {
			market.setManufacturerDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),marketPlaceProductsDTO.getManufacturerDate()));
		}
		
		market.setCurrentCity(marketPlaceProductsDTO.getCurrentCity());
		market.setPriority(marketPlaceProductsDTO.getPriority());
		market.setCurrencySpecification(marketPlaceProductsDTO.getCurrencySpecification());
		
		if(marketPlaceProductsDTO.getIsInWarranty()!=null && marketPlaceProductsDTO.getIsInWarranty()!="") {
			market.setIsInWarranty(Constants.YES);
		}else {
			market.setIsInWarranty(Constants.NO);
		}
		
		if(marketPlaceProductsDTO.getIsNegotiable()!=null && marketPlaceProductsDTO.getIsNegotiable()!="") {
			market.setIsNegotiable(Constants.YES);
		}else {
			market.setIsNegotiable(Constants.NO);
		}
		
		if(marketPlaceProductsDTO.getShareContactDetails()!=null && marketPlaceProductsDTO.getShareContactDetails()!="" ) {
			market.setShareContactDetails(Constants.YES);
		}else {
			market.setShareContactDetails(Constants.NO);
		}
		
		if(marketPlaceProductsDTO.getProductShareType()!=null && marketPlaceProductsDTO.getProductShareType()!="") {
			market.setProductShareType(Constants.YES);
		}else {
			market.setProductShareType(Constants.NO);
		}
		
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
		FileInfo fileInfo  = null;
		if(marketPlaceProductsDTO.getFileInfo() != null) {
			fileInfo = marketPlaceProductsDTO.getFileInfo().get(0);
			market.setFileName(fileInfo.getFileName());
			market.setFileContentType(fileInfo.getContentType());
			market.setFileUrl(fileInfo.getFileURL());
			try {
				FileUploadUtil.moveFile(staticPath, fileInfo, "resumes");
			} catch (Exception e) {
			}
		}
			
		
		addProductDao.saveMarketPlaceProducts(market);
		if(marketPlaceProductsDTO.getFileInfo() != null) {
			MarketPlaceProductFilesHistory filesHistory = new MarketPlaceProductFilesHistory();

			filesHistory.setMarketPlaceProductId(market);
			filesHistory.setUpdatedDate(commonUtil.orgLocationCommonDate(date, orgLocations));
			filesHistory.setUpdatedBy(user);
			filesHistory.setCreatedDate(commonUtil.orgLocationCommonDate(date, orgLocations));
			filesHistory.setCreatedBy(user);
			filesHistory.setStatus(Constants.RECORD_ACTIVE.toString());
			filesHistory.setUploadedfileName(fileInfo.getFileName());
			filesHistory.setUpleadedFileContentType(fileInfo.getContentType());
			filesHistory.setUploadedFileUrl(fileInfo.getFileURL());
				try {
					FileUploadUtil.moveFile(staticPath, fileInfo, "resumes");
				} catch (Exception e) {
				}
			
			
			addProductDao.saveMarketPlaceProductFilesHistory(filesHistory);
		}
			  return response;
	
}
	
			
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
	

	@Override
	public Boolean deleteMarketPlaceProductByUuid(String uuid, Long id) {
			return addProductDao.deleteMarketPlaceProductByUuid(uuid,id);
	}


	@Override
	public MarketPlaceProductsDTO getMarketPlaceDetailsByUuid(String uuid,OrgLocations orgLocations) throws Exception {
		String formattedDate =  null;
		MarketPlaceProductsDTO productsDTO = new MarketPlaceProductsDTO(); 
		MarketPlaceProducts productsEntity = addProductDao.getmarketPlaceProductEditList(uuid,orgLocations);
		productsDTO.setCategory(productsEntity.getCategory());
		productsDTO.setManufacturerName(productsEntity.getManufacturerName());
		formattedDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), productsEntity.getManufacturerDate());
		//BeanUtils.copyProperties(productsEntity, productsDTO);
		productsDTO.setManufacturerDate(formattedDate);
		productsDTO.setPrice(productsEntity.getPrice());
		productsDTO.setCurrencySpecification(productsEntity.getCurrencySpecification());
		productsDTO.setPriority(productsEntity.getPriority());
		productsDTO.setProductDescription(productsEntity.getProductDescription());
		productsDTO.setDocFile(productsEntity.getFileUrl());
		productsDTO.setUuid(productsEntity.getUuid());
		productsDTO.setIsNegotiable(productsEntity.getIsNegotiable());
		productsDTO.setProductShareType(productsEntity.getProductShareType());
		productsDTO.setShareContactDetails(productsEntity.getShareContactDetails());
	
		return productsDTO;
	}


	@Override
	public Response<String> saveEditMarketPlaceDetails(MarketPlaceProductsDTO marketPlaceProductsDTO,
			OrgLocations orgLocations, User user, Org org, String uuid) throws Exception {
		Response<String> response = new Response<>();
		MarketPlaceProducts mp = addProductDao.getmarketPlaceProductEditList(uuid, orgLocations);
		if(marketPlaceProductsDTO.getManufacturerName()!=null && marketPlaceProductsDTO.getManufacturerName()!="") {
			mp.setManufacturerName(marketPlaceProductsDTO.getManufacturerName());
		}
		if(marketPlaceProductsDTO.getCategory()!=null && marketPlaceProductsDTO.getCategory()!="") {
			mp.setCategory(marketPlaceProductsDTO.getCategory());
		}
		if(marketPlaceProductsDTO.getPrice()!=null && marketPlaceProductsDTO.getPrice()!="") {
			mp.setPrice(marketPlaceProductsDTO.getPrice());
		}
		if(marketPlaceProductsDTO.getPriority()!=null && marketPlaceProductsDTO.getPriority()!="") {
			mp.setPriority(marketPlaceProductsDTO.getPriority());
		}
		if(marketPlaceProductsDTO.getProductDescription()!=null && marketPlaceProductsDTO.getProductDescription()!="") {
			mp.setProductDescription(marketPlaceProductsDTO.getProductDescription());
		}
		if(marketPlaceProductsDTO.getCurrencySpecification()!=null && marketPlaceProductsDTO.getCurrencySpecification()!="") {
			mp.setCurrencySpecification(marketPlaceProductsDTO.getCurrencySpecification());
		}
		if(marketPlaceProductsDTO.getManufacturerDate() != "" && marketPlaceProductsDTO.getManufacturerDate() != null) {
			mp.setManufacturerDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),marketPlaceProductsDTO.getManufacturerDate()));
		}
		if(marketPlaceProductsDTO.getIsNegotiable()!=null && marketPlaceProductsDTO.getIsNegotiable()!="") {
			mp.setIsNegotiable(Constants.YES);
		}else {
			mp.setIsNegotiable(Constants.NO);
		}
		
		if(marketPlaceProductsDTO.getShareContactDetails()!=null && marketPlaceProductsDTO.getShareContactDetails()!="" ) {
			mp.setShareContactDetails(Constants.YES);
		}else {
			mp.setShareContactDetails(Constants.NO);
		}
		
		if(marketPlaceProductsDTO.getProductShareType()!=null && marketPlaceProductsDTO.getProductShareType()!="") {
			mp.setProductShareType(Constants.YES);
		}else {
			mp.setProductShareType(Constants.NO);
		}
		
		
		addProductDao.saveMarketPlaceProducts(mp);
		return response;
	}
	
}
