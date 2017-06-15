package com.bis.sourcelead.payroll.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.bis.sourcelead.payroll.dao.WageTaxSummaryDao;
import com.bis.sourcelead.payroll.entity.WageTaxSummary;
import com.bis.sourcelead.payroll.service.WageTaxSummaryService;

public class WageTaxSummaryServiceImpl implements WageTaxSummaryService{

	@Autowired
	WageTaxSummaryDao wageTaxSummaryDao;
	
	@Override
	public WageTaxSummary saveWageTaxSummary(WageTaxSummary wageTaxSummary) throws Exception {
		return wageTaxSummaryDao.saveWageTaxSummary(wageTaxSummary);
	}

}
