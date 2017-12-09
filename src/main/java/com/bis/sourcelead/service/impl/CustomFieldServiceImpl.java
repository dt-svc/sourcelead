package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.CustomFieldDao;
import com.bis.sourcelead.entity.SourceleadFormFields;
import com.bis.sourcelead.entity.SourceleadFormSections;
import com.bis.sourcelead.service.CustomFieldService;

@Service
public class CustomFieldServiceImpl implements CustomFieldService {

	@Autowired
	private CustomFieldDao customFieldDao;
	@Override
	public List<SourceleadFormSections> getAllCustomFieldServices() {
		return customFieldDao.getAllCustomFieldServices();
	}
	@Override
	public List<SourceleadFormFields> getFormFieldsBysectionId(String sectionFieldId) {
		return customFieldDao.getFormFieldsBysectionId(sectionFieldId);
	}

	
}
