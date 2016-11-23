package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.BoPaymentTypesDao;
import com.bis.sourcelead.entity.BoPaymentTypes;
import com.bis.sourcelead.service.BoPaymentTypesService;

@Service
public class BoPaymentTypesServiceImpl implements BoPaymentTypesService {
	
	@Autowired
	BoPaymentTypesDao boPaymentTypesDao;

	@Override
	public List<BoPaymentTypes> getAllBoPaymentTypes() throws Exception {
		return boPaymentTypesDao.getAllBoPaymentTypes();
	}

}
