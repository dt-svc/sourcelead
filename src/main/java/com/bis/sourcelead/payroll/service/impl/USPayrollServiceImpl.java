package com.bis.sourcelead.payroll.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.payroll.dao.USPayrollDao;
import com.bis.sourcelead.payroll.dto.PayrollDeductionDTO;
import com.bis.sourcelead.payroll.service.USPayrollService;

@Service
public class USPayrollServiceImpl  implements USPayrollService{
	
	private static final Logger logger = LoggerFactory.getLogger(USPayrollServiceImpl.class);
	
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
	
}
