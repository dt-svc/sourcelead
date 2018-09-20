package com.bis.sourcelead.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.NewContractCustomFieldDataDao;
import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.entity.NewContractCustomFieldData;
import com.bis.sourcelead.service.NewContractCustomFieldDataService;

@Service
public class NewContractCustomFieldDataServiceImpl implements NewContractCustomFieldDataService {

	@Autowired
	private NewContractCustomFieldDataDao newContractCustomFieldDataDao;
	@Override
	public NewContractCustomFieldData saveNewContractCustomFieldData(NewContractCustomFieldData contractCustomFields) {
		return newContractCustomFieldDataDao.saveNewContractCustomFieldData(contractCustomFields);
	}
	@Override
	public Map<Long, String[]> getNewContractCustomFieldDataByContractId(Long orgId, List<CustomFormFieldDTO> formFieldId,Long ContractId) {
		return newContractCustomFieldDataDao.getNewContractCustomFieldDataByContractId(orgId,formFieldId,ContractId);
	}
	@Override
	public NewContractCustomFieldData getNewContractCustomFieldDataById(Long contractId) {
		return newContractCustomFieldDataDao.getNewContractCustomFieldDataById(contractId);
	}

}
