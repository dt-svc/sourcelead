package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.PayrollPaytypeDao;
import com.bis.sourcelead.dto.PayrollPaytypeDTO;
import com.bis.sourcelead.dto.PayrollPaytypeDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PayrollPaytype;
import com.bis.sourcelead.service.PayrollPaytypeService;

@Service
public class PayrollPaytypeServiceImpl implements PayrollPaytypeService{
	
	@Autowired
	PayrollPaytypeDao payrollPaytypeDao;

	@Override
	public PayrollPaytype savePayrollPaytype(PayrollPaytype payrollPaytype) throws Exception {
		return payrollPaytypeDao.savePayrollPaytype(payrollPaytype);
	}
	
	@Override
	public ResponseList<PayrollPaytypeDTO> getPayrollPaytypeList(OrgLocations orgLocations) throws Exception {
		return payrollPaytypeDao.getPayrollPaytypeList(orgLocations);
	}
	
	@Override
	public Boolean findPayrollPaytypeByNameAndLocationId(PayrollPaytypeDTO payrollPaytypeDTO) throws Exception {
		Boolean flag = payrollPaytypeDao.findPayrollPaytypeByNameAndLocationId(payrollPaytypeDTO);
		return flag;
	}

}
