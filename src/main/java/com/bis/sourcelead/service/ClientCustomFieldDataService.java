package com.bis.sourcelead.service;

import java.util.List;
import java.util.Map;

import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.entity.ClientCustomFieldData;

public interface ClientCustomFieldDataService {

	public ClientCustomFieldData saveClientCustomFieldData(ClientCustomFieldData clientCustomFields) throws Exception;

	public List<ClientCustomFieldData> getClientCustomFieldDataByUserId(Long orgLocationId,List<CustomFormFieldDTO> formFieldId);

	public Map<Long, String[]> getClientAndVendorCustomFieldDataByClientOrgIdAndClientLocId(Long clientVendorOrgId,List<CustomFormFieldDTO> formFieldId, Long orgId);

	public ClientCustomFieldData getClientCustomFieldDataById(Long entityId);

}
