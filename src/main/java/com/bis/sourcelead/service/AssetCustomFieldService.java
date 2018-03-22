package com.bis.sourcelead.service;

import java.util.List;
import java.util.Map;

import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.entity.AssetCustomFieldData;

public interface AssetCustomFieldService {

	public AssetCustomFieldData getAssetCustomFieldDataById(Long entityId);

	public AssetCustomFieldData  saveAssetCustomFieldData(AssetCustomFieldData assetCustomFields);

	public Map<Long, String[]> getAssetCustomFieldDataByAssetId(Long orgId, List<CustomFormFieldDTO> formFieldList,Long userId);

}
