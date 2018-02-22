package com.bis.sourcelead.service;

import java.util.List;
import java.util.Map;

import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.entity.ClientCustomFieldData;

public interface ClientCustomFieldDataService {

	ClientCustomFieldData saveUserCustomFieldData(ClientCustomFieldData clientCustomFields) throws Exception;

	List<ClientCustomFieldData> getClientCustomFieldDataByUserId(Long orgLocationId,List<CustomFormFieldDTO> formFieldId);

	Map<Long, String[]> getClientAndVendorCustomFieldDataByClientOrgIdAndClientLocId(Long clientVendorOrgId,List<CustomFormFieldDTO> formFieldId, Long orgId);

}
