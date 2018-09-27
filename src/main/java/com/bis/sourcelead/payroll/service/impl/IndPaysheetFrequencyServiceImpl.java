package com.bis.sourcelead.payroll.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.IndPaysheetFrequencyDao;
import com.bis.sourcelead.payroll.dto.IndPaysheetFrequencyDTO;
import com.bis.sourcelead.payroll.entity.IndPaysheetFrequency;
import com.bis.sourcelead.payroll.service.IndPaysheetFrequencyService;

@Service
public class IndPaysheetFrequencyServiceImpl implements IndPaysheetFrequencyService{
	
	private static final Logger logger = LoggerFactory.getLogger(IndPaysheetFrequencyServiceImpl.class);

	@Autowired
	IndPaysheetFrequencyDao indPaysheetFrequencyDao;
	
	@Override
	public ResponseList<IndPaysheetFrequency> getIndPaysheetFrequencyListByOrgLocationId(OrgLocations orgLocation,
			IndPaysheetFrequencyDTO indPaysheetFrequencyDTO) throws Exception {
		return indPaysheetFrequencyDao.getIndPaysheetFrequencyListByOrgLocationId(orgLocation, indPaysheetFrequencyDTO);
	}

	@Override
	public IndPaysheetFrequency addIndPaysheetFrequency(IndPaysheetFrequency paySheetFreq) {
		return indPaysheetFrequencyDao.addIndPaysheetFrequency(paySheetFreq);
	}

}
