package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.SourceleadFormFieldsDao;
import com.bis.sourcelead.entity.SourceleadFormFields;
import com.bis.sourcelead.service.SourceleadFormFieldsService;

@Service
public class SourceleadFormFieldsServiceImpl implements SourceleadFormFieldsService{

 @Autowired
 SourceleadFormFieldsDao sourceleadFormFieldsDao;

@Override
public List<SourceleadFormFields> getAllFormFieldsBySectionId(Long sectionId) throws Exception {
	return sourceleadFormFieldsDao.getAllFormFieldsBySectionId(sectionId);
}


}
