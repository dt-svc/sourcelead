package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.PayrollPayheadDao;
import com.bis.sourcelead.dto.PayrollPayheadDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PayrollPayhead;
import com.bis.sourcelead.service.PayrollPayheadService;

@Service
public class PayrollPayheadServiceImpl implements PayrollPayheadService{
	
	@Autowired
	PayrollPayheadDao payrollPayheadDao;

	@Override
	public PayrollPayhead savePayrollPayhead(PayrollPayhead payrollPayhead) throws Exception {
		return payrollPayheadDao.savePayrollPayhead(payrollPayhead);
	}

	@Override
	public ResponseList<PayrollPayheadDTO> getPayrollPayheadList(PayrollPayheadDTO payrollPayheadDTO,
			OrgLocations orgLocations) throws Exception {
		return payrollPayheadDao.getPayrollPayheadList(payrollPayheadDTO,orgLocations);
	}
	
	@Override
	public ResponseList<PayrollPayheadDTO> getPayrollPayheadListWithPagination(PayrollPayheadDTO payrollPayheadDTO,
			OrgLocations orgLocations) throws Exception {
		return payrollPayheadDao.getPayrollPayheadListWithPagination(payrollPayheadDTO,orgLocations);
	}


}
