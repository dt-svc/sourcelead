package com.bis.sourcelead.service.impl;

import java.util.List;

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

	@Override
	public List<Asset> getAssetListByLocationId(Long orgLocationId) throws Exception {
		return assetDao.getAssetListByLocationId(orgLocationId);
	}

}
