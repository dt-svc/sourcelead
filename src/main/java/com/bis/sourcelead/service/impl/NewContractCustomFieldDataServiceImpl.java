package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.NewContractCustomFieldDataDao;
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

}
