package com.bis.sourcelead.payroll.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.payroll.dao.PayAdjustmentHistoryDao;
import com.bis.sourcelead.payroll.entity.PayAdjustmentHistory;
import com.bis.sourcelead.payroll.service.PayAdjustmentHistoryService;

@Service
public class PayAdjustmentHistoryServiceImpl implements PayAdjustmentHistoryService {

	@Autowired
	PayAdjustmentHistoryDao payAdjustmentHistoryDao;
	
	@Override
	public PayAdjustmentHistory savePayAdjustmentHistory(PayAdjustmentHistory payAdjHistory)
	{
		return payAdjustmentHistoryDao.savePayAdjustmentHistory(payAdjHistory);
	}
}
