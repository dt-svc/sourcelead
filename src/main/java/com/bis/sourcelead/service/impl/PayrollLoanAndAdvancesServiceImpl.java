package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.PayrollLoanAndAdvancesDao;
import com.bis.sourcelead.dto.PayrollLoanAndAdvancesDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PayrollLoanAndAdvances;
import com.bis.sourcelead.service.PayrollLoanAndAdvancesService;

@Service
public class PayrollLoanAndAdvancesServiceImpl implements PayrollLoanAndAdvancesService{

	@Autowired
	PayrollLoanAndAdvancesDao payrollLoanAndAdvancesDao;

	@Override
	public PayrollLoanAndAdvances savePayrollLoanAndAdvances(PayrollLoanAndAdvances payrollLoanAndAdvances)
			throws Exception {
		return payrollLoanAndAdvancesDao.savePayrollLoanAndAdvances(payrollLoanAndAdvances);
	}

	@Override
	public ResponseList<PayrollLoanAndAdvancesDTO> getPayrollLoanAndAdvancesList(
			PayrollLoanAndAdvancesDTO payrollLoanAndAdvancesDTO, OrgLocations orgLocations) throws Exception {
		return payrollLoanAndAdvancesDao.getPayrollLoanAndAdvancesList(payrollLoanAndAdvancesDTO, orgLocations);
	}

	@Override
	public PayrollLoanAndAdvances getPayrollLoanAndAdvancesByUUID(String uuid) throws Exception {
		return payrollLoanAndAdvancesDao.getPayrollLoanAndAdvancesByUUID(uuid);
	}

	@Override
	public List<PayrollLoanAndAdvances> getPayrollLoanAndAdvancesByTypeAndOrgLocationId(String type,Long orgLocationId)
			throws Exception {
		return payrollLoanAndAdvancesDao.getPayrollLoanAndAdvancesByTypeAndOrgLocationId(type, orgLocationId);
	}

	@Override
	public PayrollLoanAndAdvances getPayrollLoanAndAdvancesById(Long id) throws Exception {
		return payrollLoanAndAdvancesDao.getPayrollLoanAndAdvancesById(id);
	}

	@Override
	public List<PayrollLoanAndAdvances> getPayrollLoanAndAdvancesByOrgLocationId(Long orgLocationId) {
		return payrollLoanAndAdvancesDao.getPayrollLoanAndAdvancesByOrgLocationId(orgLocationId);
	}
}
