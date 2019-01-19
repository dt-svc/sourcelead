package com.bis.sourcelead.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ClientCustomFieldDataDao;
import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.entity.ClientCustomFieldData;
import com.bis.sourcelead.service.ClientCustomFieldDataService;

@Service
public class ClientCustomFieldDataServiceImpl implements ClientCustomFieldDataService{

	@Autowired
	private ClientCustomFieldDataDao clientCustomFieldDataDao;
	
	@Override
	public ClientCustomFieldData saveClientCustomFieldData(ClientCustomFieldData clientCustomFields) throws Exception {
		return clientCustomFieldDataDao.saveClientCustomFieldData(clientCustomFields);
		
	}

	@Override
	public List<ClientCustomFieldData> getClientCustomFieldDataByUserId(Long orgLocation,List<CustomFormFieldDTO> formFieldId) {
		return clientCustomFieldDataDao.getClientCustomFieldDataByUserId(orgLocation,formFieldId);
	}

	@Override
	public Map<Long, String[]> getClientAndVendorCustomFieldDataByClientOrgIdAndClientLocId(Long clientVendorOrgId,List<CustomFormFieldDTO> formFieldId, Long orgId) {
		return clientCustomFieldDataDao.getClientAndVendorCustomFieldDataByClientOrgIdAndClientLocId(clientVendorOrgId,formFieldId,orgId);
	}

	@Override
	public ClientCustomFieldData getClientCustomFieldDataById(Long entityId) {
		return clientCustomFieldDataDao.getClientCustomFieldDataById(entityId);
	}

}
