package com.bis.sourcelead.payroll.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.payroll.dao.DeductionHistoryDao;
import com.bis.sourcelead.payroll.dto.ContributionHistoryDTO;
import com.bis.sourcelead.payroll.dto.DeductionHistoryDTO;
import com.bis.sourcelead.payroll.service.DeductionHistoryService;

@Service
public class DeductionHistoryServiceImpl implements DeductionHistoryService{

	@Autowired
	DeductionHistoryDao deductionHistoryDao;
	
	@Override
	public List<DeductionHistoryDTO> getDeductionsByPayrollId(Long payrollId)
	{
		return deductionHistoryDao.getDeductionsByPayrollId(payrollId);
	}

	@Override
	public List<ContributionHistoryDTO> getContributionsByPayrollId(Long payrollId)
	{
		return deductionHistoryDao.getContributionsByPayrollId(payrollId);
	}
}
