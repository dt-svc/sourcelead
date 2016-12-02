package com.bis.sourcelead.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.AssetDao;
import com.bis.sourcelead.entity.Asset;
import com.bis.sourcelead.service.AssetService;

@Service
public class AssetServiceImpl implements AssetService{
	
	@Autowired
	AssetDao assetDao;

	@Override
	public Asset saveAsset(Asset asset) throws Exception {
		return assetDao.saveAsset(asset);
	}
	
	/**
	 * @author Prasad salina
	 * @param Id
	 * @return
	 * @throws Exception
	 * To get the asset by Id.
	 * This method used in BackOfficeRestController
	 */
	@Override
	public Asset getAssetById(Long Id) throws Exception {
		 Asset asset = assetDao.getAssetById(Id);
		 asset = getEmpNames(asset);
		 return asset;
	}

	/**
	 * 
	 * @param orgLocationId
	 * @param categoryIds
	 * @return
	 * @throws Exception
	 * To get the asset list with filters.
	 * This method used in BackOfficeRestController
	 */
	@Override
	public List<Asset> getAssetFilterListByLocationId(Long orgLocationId, String categoryIds) throws Exception {
		List<Asset> assetsList =  assetDao.getAssetFilterListByLocationId(orgLocationId, categoryIds);
		if(assetsList != null && !assetsList.isEmpty()){
			for (Asset asset : assetsList) {
				asset = getEmpNames(asset);
			}
		}
		return assetsList;
	}
	
	/**
	 * @author Prasad Salina
	 * @param asset
	 * @return
	 * To set the Shift A and Shift B emp names
	 */
	private Asset getEmpNames(Asset asset) {
		if(asset.getShiftAEmployee() != null && asset.getShiftAEmployee().getUser() != null){
			   String shiftAEmpName = "";
	  		   if(StringUtils.isNotEmpty(asset.getShiftAEmployee().getUser().getFirstName())){
	  			 shiftAEmpName = asset.getShiftAEmployee().getUser().getFirstName();
	  		   }
	  		   if(StringUtils.isNotEmpty(asset.getShiftAEmployee().getUser().getLastName()) && StringUtils.isNotBlank(asset.getShiftAEmployee().getUser().getLastName())){
	  			 shiftAEmpName = shiftAEmpName+" "+asset.getShiftAEmployee().getUser().getLastName();
	  		   }
	  		   asset.setShiftAEmpName(shiftAEmpName);
		 }
		 
		 if(asset.getShiftBEmployee() != null && asset.getShiftBEmployee().getUser() != null){
			   String shiftBEmpName = "";
	  		   if(StringUtils.isNotEmpty(asset.getShiftBEmployee().getUser().getFirstName())){
	  			 shiftBEmpName = asset.getShiftBEmployee().getUser().getFirstName();
	  		   }
	  		   if(StringUtils.isNotEmpty(asset.getShiftBEmployee().getUser().getLastName()) && StringUtils.isNotBlank(asset.getShiftBEmployee().getUser().getLastName())){
	  			 shiftBEmpName = shiftBEmpName+" "+asset.getShiftBEmployee().getUser().getLastName();
	  		   }
	  		   asset.setShiftBEmpName(shiftBEmpName);
		 }
		return asset;
	}

	@Override
	public List<Asset> getAssetListByLocationId(Long orgLocationId) throws Exception {
		return assetDao.getAssetListByLocationId(orgLocationId);
	}

}
