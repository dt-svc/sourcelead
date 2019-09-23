package com.bis.sourcelead.payroll.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.bis.sourcelead.payroll.dao.PayrollContributionDao;
import com.bis.sourcelead.payroll.dto.ContributionDTO;
import com.bis.sourcelead.payroll.dto.PayrollContributionDTO;
import com.bis.sourcelead.payroll.entity.PayrollContribution;
import com.bis.sourcelead.payroll.entity.PayrollDeduction;
import com.bis.sourcelead.payroll.service.PayrollContributionService;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class PayrollContributionServiceImpl implements PayrollContributionService{
	
	private static final Logger logger = LoggerFactory.getLogger(PayrollContributionServiceImpl.class);
	@Autowired
	PayrollContributionDao payrollContributionDao;
	
	@Override
	public ResponseList<PayrollContributionDTO> savePayrollContribution(PayrollContributionDTO payrollContributionDTO, OrgLocations orgLocation, User user)
	{
		PayrollContribution payrollContribution = null;
		if(StringUtils.isNoneBlank(payrollContributionDTO.getContributionUUID())) {
			payrollContribution = payrollContributionDao.findPayrollContributionByUUID(payrollContributionDTO.getContributionUUID());
		}else {
			payrollContribution = new PayrollContribution();
		}
		
		ResponseList<PayrollContributionDTO> response = new ResponseList<>();
		List<PayrollContributionDTO> payrollContributionList = new ArrayList<>();
		PayrollDeduction payrollDeduction = payrollContributionDao.findById(payrollContributionDTO.getDeductionTypeId());
		if (payrollDeduction != null) {
			payrollContribution.setDeductionTypeId(payrollDeduction);
			payrollContribution.setPayrollContributionMethod(payrollContributionDTO.getPayrollContributionMethod());
			payrollContribution.setContributionAmount(payrollContributionDTO.getContributionAmount());
			payrollContribution.setDescription(payrollContributionDTO.getDescription());
			payrollContribution.setCreatedBy(user.getUserCode());
			payrollContribution.setCreatedDate(new Date());
			payrollContribution.setUpdatedBy(user.getUserCode());
			payrollContribution.setUpdatedDate(new Date());
			payrollContribution.setStatus(1);
			payrollContribution.setContributionUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			payrollContribution.setOrgLocationId(orgLocation);
			PayrollContribution savedPayrollContribution = payrollContributionDao.savePayrollContribution(payrollContribution);
			if (savedPayrollContribution != null) {
				payrollContributionList.add(payrollContributionDTO);
				response.setData(payrollContributionList);
				response.setStatusCode("200");
			}
			else {
				response.setData(payrollContributionList);
				response.setStatusCode("400");
			}
		}
		return response;
	}
	
	@Override
	public boolean deleteContributionById(long contributionID,String changeStatus)
	{
		boolean isDeleted = false;
		try {
			PayrollContribution payrollContribution = payrollContributionDao.findPayrollContributionByID(contributionID);
			
			if(changeStatus.equalsIgnoreCase(Constants.PAYROLL_DELETE)) {
				payrollContribution.setStatus(0);
			}else if(changeStatus.equalsIgnoreCase(Constants.REACTIVE)){
				payrollContribution.setStatus(1);
			}
			
			PayrollContribution updatedPayrollContribution = payrollContributionDao.savePayrollContribution(payrollContribution);
			if (updatedPayrollContribution != null) {
				isDeleted = true;
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return isDeleted;
	}
	
	@Override
	public ResponseList<PayrollContributionDTO> findAllPayrollContribution(PayrollContributionDTO payrollContributionDTO, OrgLocations orgLocation)
	{
		ResponseList<PayrollContribution> payrollContriList = payrollContributionDao.findAllPayrollContribution(payrollContributionDTO, orgLocation);
		ResponseList<PayrollContributionDTO> response = new ResponseList<>();
		List<PayrollContributionDTO> list = new ArrayList<>();
		try {
			if (payrollContriList.getData() != null) {
				payrollContriList.getData().forEach(contribution -> {
					PayrollContributionDTO dto = new PayrollContributionDTO();
					dto.setContributionId(contribution.getId());
					PayrollDeduction payrollDeduction = payrollContributionDao.findById(contribution.getDeductionTypeId().getId());
					dto.setDeductionType(payrollDeduction.getPayrollDeductionType());
					dto.setDescription(contribution.getDescription());
					dto.setPayrollContributionMethod(contribution.getPayrollContributionMethod());
					dto.setContributionAmount(contribution.getContributionAmount());
					dto.setContributionUUID(contribution.getContributionUUID());
					dto.setStatus(contribution.getStatus());
					dto.setTaxType(contribution.getDeductionTypeId().getTaxType());
					dto.setScheduleType(contribution.getDeductionTypeId().getPayrollScheduled());
					list.add(dto);
				});
				response.setData(list);
				response.setRecordsFiltered(payrollContriList.getRecordsFiltered());
				response.setRecordsTotal(payrollContriList.getRecordsTotal());
				response.setStatusCode("200");
			}
			else {
				response.setStatusCode("400");
				response.setResponseMessage("something went wrong.!");
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}


	@Override
	public PayrollContribution findPayrollContributionByUUID(String contributionUUID)
	{
		return payrollContributionDao.findPayrollContributionByUUID(contributionUUID);
	}

	@Override
	public boolean isValidContributionType(long deductionTypeId,OrgLocations orgLocation)
	{
		boolean isValid = false; 
		PayrollContribution payrollContribution = payrollContributionDao.findPayrollContributionByDeductionTypeId(deductionTypeId,orgLocation);
		if(payrollContribution != null) {
			isValid = true;
		}
		return isValid;
	}

	@Override
	public List<PayrollContributionDTO> getAllContributionTypeByLocationId(OrgLocations orgLocation)
	{
		List<PayrollContribution> list = payrollContributionDao.getAllContributionTypeByLocationId(orgLocation);
		List<PayrollContributionDTO> resObj = new ArrayList<>();
		list.forEach(contr -> {
			PayrollContributionDTO payrollContributionDTO = new PayrollContributionDTO();
			payrollContributionDTO.setContributionUUID(contr.getContributionUUID());
			payrollContributionDTO.setContributionId(contr.getId());
			payrollContributionDTO.setContributionType(contr.getDeductionTypeId().getPayrollDeductionType());
			resObj.add(payrollContributionDTO);
		});
		return resObj;
	}

	@Override
	public PayrollContributionDTO getContributionAmount(long contributionId)
	{
		PayrollContribution payrollContribution = payrollContributionDao.findPayrollContributionByID(contributionId);
		PayrollContributionDTO PayrollContributionDTO = new PayrollContributionDTO();
		PayrollContributionDTO.setContributionAmount(payrollContribution.getContributionAmount());
		PayrollContributionDTO.setPayrollContributionMethod(payrollContribution.getPayrollContributionMethod());
		return PayrollContributionDTO;
	}

}
