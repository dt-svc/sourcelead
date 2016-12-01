package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.Asset;

public interface AssetService {

	Asset saveAsset(Asset asset) throws Exception;
	
	List<Asset> getAssetListByLocationId(Long orgLocationId) throws Exception;
}
