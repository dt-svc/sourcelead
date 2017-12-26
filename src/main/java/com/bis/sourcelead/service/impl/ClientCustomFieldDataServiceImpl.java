package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ClientCustomFieldDataDao;
import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.entity.ClientCustomFieldData;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.service.ClientCustomFieldDataService;

@Service
public class ClientCustomFieldDataServiceImpl implements ClientCustomFieldDataService{

	@Autowired
	private ClientCustomFieldDataDao clientCustomFieldDataDao;
	
	@Override
	public ClientCustomFieldData saveUserCustomFieldData(ClientCustomFieldData clientCustomFields) throws Exception {
		return clientCustomFieldDataDao.saveUserCustomFieldData(clientCustomFields);
		
	}

	@Override
	public List<ClientCustomFieldData> getClientCustomFieldDataByUserId(Long orgLocation,List<CustomFormFieldDTO> formFieldId) {
		return clientCustomFieldDataDao.getClientCustomFieldDataByUserId(orgLocation,formFieldId);
	}

}
