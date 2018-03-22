package com.bis.sourcelead.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.AssetCustomFieldDao;
import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.entity.AssetCustomFieldData;
import com.bis.sourcelead.service.AssetCustomFieldService;

@Service
public class AssetCustomFieldServiceImpl implements AssetCustomFieldService{

	@Autowired
	private AssetCustomFieldDao assetCustomFieldDao;

	@Override
	public AssetCustomFieldData saveAssetCustomFieldData(AssetCustomFieldData assetCustomFields) {
		return assetCustomFieldDao.saveAssetCustomFieldData(assetCustomFields);
	}

	@Override
	public Map<Long, String[]> getAssetCustomFieldDataByAssetId(Long orgId, List<CustomFormFieldDTO> formFieldId,Long assetId) {
		return assetCustomFieldDao.getAssetCustomFieldDataByAssetId(orgId,formFieldId,assetId);
	}

	@Override
	public  AssetCustomFieldData getAssetCustomFieldDataById(Long entityId) {
		return assetCustomFieldDao.getAssetCustomFieldDataById(entityId);
		
		
		
	}
	
}
