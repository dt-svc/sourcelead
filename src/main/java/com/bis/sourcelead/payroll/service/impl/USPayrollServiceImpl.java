package com.bis.sourcelead.payroll.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dao.USPayrollDao;
import com.bis.sourcelead.payroll.dto.PayrollDeductionDTO;
import com.bis.sourcelead.payroll.entity.PayrollDeduction;
import com.bis.sourcelead.payroll.service.USPayrollService;

@Service
public class USPayrollServiceImpl  implements USPayrollService{
	
	@Autowired
	USPayrollDao uSPayrollDao;
	
	@Override
	public ResponseList<PayrollDeductionDTO> findAllDeduction()
	{
		ResponseList<PayrollDeductionDTO> response = new ResponseList<>();
		List<LookUpData> lookupDataList = uSPayrollDao.findAllDeduction();
		List<PayrollDeductionDTO> list = new ArrayList<>();
		lookupDataList.forEach(l -> {
			PayrollDeductionDTO payrollDeductionDTO = new PayrollDeductionDTO();
			payrollDeductionDTO.setPayrollDeductionTypeName(l.getName());
			payrollDeductionDTO.setLookupDataId(l.getId());
			list.add(payrollDeductionDTO);
		});
		if (list.size() > 0) {
			response.setData(list);
			response.setStatusCode("200");
		}
		else {
			response.setStatusCode("400");
		}
		return response;
	}
	
	@Override
	public PayrollDeductionDTO savePayrollDeduction(PayrollDeductionDTO payrollDeductionDTO, OrgLocations orgLocation, User user)
	{
		PayrollDeduction payrollDeduction = new PayrollDeduction();
		payrollDeduction.setPayrollDeductionType(payrollDeductionDTO.getPayrollDeductionType());
		payrollDeduction.setPayrollDeductionMethod(payrollDeductionDTO.getPayrollDeductionMethod());
		payrollDeduction.setDeductionAmount(payrollDeductionDTO.getDeductionAmount());
		payrollDeduction.setDescription(payrollDeductionDTO.getDescription());
		payrollDeduction.setPayrollScheduled(payrollDeductionDTO.getPayrollScheduled());
		payrollDeduction.setOrgLocationId(orgLocation);
		payrollDeduction.setCreatedBy(user.getUserCode());
		payrollDeduction.setCreatedDate(new Date());
		payrollDeduction.setUpdatedBy(user.getUserCode());
		payrollDeduction.setUpdatedDate(new Date());
		payrollDeduction.setTaxType(getTaxType(payrollDeductionDTO.getPayrollDeductionType()));
		payrollDeduction.setStatus(1);
		return uSPayrollDao.savePayrollDeduction(payrollDeduction, payrollDeductionDTO);
	}

	/**
	 * To Get Tax Type based on deduction Type
	 * 
	 * @param payrollDeductionType
	 * @return String
	 */
	private String getTaxType(String deductionType)
	{
		Map<String, String> map = new HashMap<>();
		map.put("Selection-125", "pretax");
		map.put("401(k)", "pretax");
		map.put("403(b)", "pretax");
		map.put("457(b)", "pretax");
		map.put("Roth 401(k)", "posttax");
		map.put("Roth 403(b)", "posttax");
		map.put("Roth 457(b)", "posttax");
		map.put("Simple IRA", "pretax");
		map.put("HSA-Single Limit", "pretax");
		map.put("HSA-Family Limit", "pretax");
		map.put("FSA-medical", "pretax");
		map.put("FSA-Dependent Care", "pretax");
		return map.get(deductionType);
	}
	
	@Override
	public ResponseList<PayrollDeductionDTO> findAllPayrollDeduction(PayrollDeductionDTO payrollDeductionDTO, OrgLocations orgLocation)
	{
		ResponseList<PayrollDeduction> res = uSPayrollDao.findAllPayrollDeduction(payrollDeductionDTO, orgLocation);
		ResponseList<PayrollDeductionDTO> payrollResponse = new ResponseList<>();
		if (res.getData() != null) {
			List<PayrollDeductionDTO> dtoList = new ArrayList<>();
			res.getData().forEach(payroll -> {
				PayrollDeductionDTO payrollDTO = new PayrollDeductionDTO();
				payrollDTO.setId(payroll.getId());
				payrollDTO.setPayrollDeductionType(payroll.getPayrollDeductionType());
				payrollDTO.setPayrollDeductionMethod(payroll.getPayrollDeductionMethod());
				payrollDTO.setDeductionAmount(payroll.getDeductionAmount());
				payrollDTO.setPayrollScheduled(payroll.getPayrollScheduled());
				payrollDTO.setDescription(payroll.getDescription());
				dtoList.add(payrollDTO);
			});
			payrollResponse.setData(dtoList);
			payrollResponse.setRecordsFiltered(res.getRecordsFiltered());
			payrollResponse.setRecordsTotal(res.getRecordsTotal());
			payrollResponse.setStatusCode("200");
		}
		else {
			payrollResponse.setStatusCode("400");
			payrollResponse.setResponseMessage("Something went wrong .!");
		}
		return payrollResponse;
	}

	
}
