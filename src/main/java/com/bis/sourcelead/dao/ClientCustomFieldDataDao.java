package com.bis.sourcelead.dao;

import java.util.List;
import java.util.Map;

import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.entity.ClientCustomFieldData;

public interface ClientCustomFieldDataDao {

	public ClientCustomFieldData saveClientCustomFieldData(ClientCustomFieldData clientCustomFields) throws Exception;

	public List<ClientCustomFieldData> getClientCustomFieldDataByUserId(Long orgLocation,List<CustomFormFieldDTO> formFieldId);

	public Map<Long, String[]> getClientAndVendorCustomFieldDataByClientOrgIdAndClientLocId(Long clientVendorOrgId,List<CustomFormFieldDTO> formFieldId, Long orgId);

	public ClientCustomFieldData getClientCustomFieldDataById(Long entityId);

}
