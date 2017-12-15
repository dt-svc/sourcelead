package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.OrglocationFormSubFieldsDao;
import com.bis.sourcelead.entity.OrglocationFormSubFields;
import com.bis.sourcelead.service.OrglocationFormSubFieldsService;

@Service
public class OrglocationFormSubFieldsServiceImpl implements OrglocationFormSubFieldsService{

	@Autowired
	private OrglocationFormSubFieldsDao orglocationFormSubFieldsDao;
	@Override
	public List<OrglocationFormSubFields> getOrgCustomSufieldsBySectionId(Long customFieldId) {
		return orglocationFormSubFieldsDao.getOrgCustomSufieldsBySectionId(customFieldId);
	}

}
