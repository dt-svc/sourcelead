package com.bis.sourcelead.payroll.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dao.PayrollDeductionDao;
import com.bis.sourcelead.payroll.dto.PayrollDeductionDTO;
import com.bis.sourcelead.payroll.entity.PayrollContribution;
import com.bis.sourcelead.payroll.entity.PayrollDeduction;
import com.bis.sourcelead.payroll.service.PayrollDeductionService;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class PayrollDeductionServiceImpl  implements PayrollDeductionService{
	
	private static final Logger logger = LoggerFactory.getLogger(PayrollDeductionServiceImpl.class);
	
	@Autowired
	PayrollDeductionDao payrollDeductionDao;
	
	@Override
	public PayrollDeductionDTO savePayrollDeduction(PayrollDeductionDTO payrollDeductionDTO, OrgLocations orgLocation, User user)
	{
		PayrollDeduction payrollDeduction =null;
		
		if(StringUtils.isNoneBlank(payrollDeductionDTO.getDeductionUUID())) {
			payrollDeduction = payrollDeductionDao.findPayrollDeductionByUUID(payrollDeductionDTO.getDeductionUUID());
//			payrollDeduction = uSPayrollDao.findPayrollDeductionByUUID(payrollDeductionDTO.getDeductionUUID());
		}else {
			payrollDeduction = new PayrollDeduction();
		}
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
		payrollDeduction.setDeductionUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		return payrollDeductionDao.savePayrollDeduction(payrollDeduction, payrollDeductionDTO);
//		return uSPayrollDao.savePayrollDeduction(payrollDeduction, payrollDeductionDTO);
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
		map.put("FSA-Medical", "pretax");
		map.put("FSA-Dependent Care", "pretax");
		return map.get(deductionType);
	}
	
	@Override
	public ResponseList<PayrollDeductionDTO> findAllPayrollDeduction(PayrollDeductionDTO payrollDeductionDTO, OrgLocations orgLocation)
	{
		ResponseList<PayrollDeduction> res = payrollDeductionDao.findAllPayrollDeduction(payrollDeductionDTO, orgLocation);
		ResponseList<PayrollDeductionDTO> payrollResponse = new ResponseList<>();
		if (res.getData() != null) {
			List<PayrollDeductionDTO> dtoList = new ArrayList<>();
			res.getData().forEach(pDed -> {
				PayrollDeductionDTO payrollDTO = new PayrollDeductionDTO();
				payrollDTO.setId(pDed.getId());
				payrollDTO.setPayrollDeductionType(pDed.getPayrollDeductionType());
				payrollDTO.setPayrollDeductionMethod(pDed.getPayrollDeductionMethod());
				payrollDTO.setDeductionAmount(pDed.getDeductionAmount());
				payrollDTO.setPayrollScheduled(pDed.getPayrollScheduled());
				payrollDTO.setDeductionUUID(pDed.getDeductionUUID());
				payrollDTO.setDescription(pDed.getDescription());
				payrollDTO.setStatus(pDed.getStatus());
				payrollDTO.setTaxType(pDed.getTaxType());
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
	
	@Override
	public ResponseList<PayrollDeductionDTO> getAllDeductionTypeByLocationId(OrgLocations orgLocation)
	{
		List<PayrollDeduction> res = payrollDeductionDao.getAllDeductionTypeByLocationId(orgLocation);
		ResponseList<PayrollDeductionDTO> respones = new ResponseList<>();
		if (res != null) {
			List<PayrollDeductionDTO> payrollDedList = new ArrayList<>();
			res.forEach(pded -> {
				PayrollDeductionDTO payrollDeductionDTO = new PayrollDeductionDTO();
				payrollDeductionDTO.setId(pded.getId());
				payrollDeductionDTO.setPayrollDeductionType(pded.getPayrollDeductionType());
				payrollDedList.add(payrollDeductionDTO);
			});
			respones.setData(payrollDedList);
			respones.setStatusCode("200");
		}
		else {
			respones.setStatusCode("400");
			respones.setResponseMessage("Something went wrong.!");
		}
		return respones;
	}
	
	@Override
	public PayrollDeduction findPayrollDeductionByUUID(String deductionUUID)
	{
		return payrollDeductionDao.findPayrollDeductionByUUID(deductionUUID);
	}
	
	@Override
	public boolean deleteDeductionById(Long deductionId,String changeStatus)
	{
		boolean isDeleted = false;
		
		PayrollDeductionDTO payrollDeductionDTO = new PayrollDeductionDTO();
		PayrollDeduction payrollDeduction = payrollDeductionDao.findPayrollDeductionByID(deductionId);
		
        if(changeStatus.equalsIgnoreCase(Constants.PAYROLL_DELETE)) {
    			payrollDeduction.setStatus(0);
    			payrollDeductionDTO.setStatus(0);
		}else if(changeStatus.equalsIgnoreCase(Constants.REACTIVE)){
				payrollDeduction.setStatus(1);
				payrollDeductionDTO.setStatus(1);
		}
        
        PayrollDeductionDTO updatedPayrollDeductionDTO = payrollDeductionDao.savePayrollDeduction(payrollDeduction, payrollDeductionDTO);
        if (updatedPayrollDeductionDTO != null) {
			isDeleted = true;
		}
		
	
		return isDeleted;
	}

	@Override
	public boolean isValidDeductionType(String payrollDeductionType,OrgLocations orgLocation)
	{
		boolean isValid = false;
		PayrollDeduction payrollDeduction = payrollDeductionDao.isValidDeductionType(payrollDeductionType,orgLocation);
		if (payrollDeduction != null) {
			isValid = true;
		}
		return isValid;
	}

	@Override
	public PayrollDeduction getPayrollDeductionTypeById(long deductionId)
	{
		PayrollDeduction payrollDeduction = payrollDeductionDao.getPayrollDeductionTypeById(deductionId);
		return payrollDeduction;
	}
	
	@Override
	public PayrollDeduction getDeductionByDeductionTypeAndLocationId(String deductionType, Long orgLocationId) {
		return payrollDeductionDao.getDeductionByDeductionTypeAndLocationId(deductionType,orgLocationId);
	}

}
