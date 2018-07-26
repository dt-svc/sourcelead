package com.bis.sourcelead.payroll.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.payroll.dao.USPayPeriodDao;
import com.bis.sourcelead.payroll.entity.USPayPeriod;
import com.bis.sourcelead.payroll.service.USPayPeriodService;

@Service
public class USPayPeriodServiceImpl implements USPayPeriodService{

	@Autowired
	USPayPeriodDao uSPayPeriodDao;
	
	@Override
	public USPayPeriod getPayPeriodNumber(Long payFrequencyId)
	{
		return uSPayPeriodDao.getPayPeriodNumber(payFrequencyId);
	}
	@Override
	public USPayPeriod savePayPeriodNumber(USPayPeriod uSPayPeriod)
	{
		return uSPayPeriodDao.savePayPeriodNumber(uSPayPeriod);
	}
	
}
