package com.bis.sourcelead.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.PayrollPayheadDao;
import com.bis.sourcelead.dto.PayrollPayheadDTO;
import com.bis.sourcelead.dto.PayrollPayheadSalarySetupDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PayrollPayhead;
import com.bis.sourcelead.entity.PayrollPayheadSalarySetup;
import com.bis.sourcelead.entity.PayrollPaytype;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.PayrollPayheadService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class PayrollPayheadServiceImpl implements PayrollPayheadService{
	
	@Autowired
	PayrollPayheadDao payrollPayheadDao;
	
	@Autowired
	CommonUtil commonUtil;

	@Override
	public PayrollPayhead savePayrollPayhead(PayrollPayheadDTO payrollPayheadDTO, User user, OrgLocations orgLocations) throws Exception {
		PayrollPayhead payrollPayhead = null;
		if(StringUtils.isNotBlank(payrollPayheadDTO.getUuid())) {
			payrollPayhead = getPayrollPayheadByUUID(payrollPayheadDTO.getUuid());
			
			payrollPayhead.setUpdatedDate(new Date());
			payrollPayhead.setUpdatedBy(user);
		}else {
			payrollPayhead = new PayrollPayhead();
			payrollPayhead.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
			payrollPayhead.setCreatedDate(new Date());
			payrollPayhead.setCreatedBy(user);
		}
		
		if(StringUtils.isNotBlank(payrollPayheadDTO.getName())) {
			payrollPayhead.setName(payrollPayheadDTO.getName());
		}else {
			payrollPayhead.setName(null);
		}
		
		if(StringUtils.isNotBlank(payrollPayheadDTO.getShortCode())) {
			payrollPayhead.setShortCode(payrollPayheadDTO.getShortCode().toUpperCase());
		}else {
			payrollPayhead.setShortCode(null);
		}
		
		if(StringUtils.isNotBlank(payrollPayheadDTO.getEffectiveDate())) {
			Date paymentDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), payrollPayheadDTO.getEffectiveDate());
			payrollPayhead.setEffectiveDate(paymentDate);
		}else {
			payrollPayhead.setEffectiveDate(null);
		}
		
		if(StringUtils.isNotBlank(payrollPayheadDTO.getType())) {
			payrollPayhead.setType(payrollPayheadDTO.getType());
		}else {
			payrollPayhead.setType(null);
		}
		
		if(StringUtils.isNotBlank(payrollPayheadDTO.getPayType())) {
			PayrollPaytype payrollPaytype = new PayrollPaytype();
			payrollPaytype.setId(Long.parseLong(payrollPayheadDTO.getPayType()));
			payrollPayhead.setPayType(payrollPaytype);
		}else {
			payrollPayhead.setPayType(null);
		}
		
		if(StringUtils.isNotBlank(payrollPayheadDTO.getForm16())) {
			payrollPayhead.setForm16(payrollPayheadDTO.getForm16());
		}else {
			payrollPayhead.setForm16(null);
		}
		
		if(StringUtils.isNotBlank(payrollPayheadDTO.getTax())) {
			payrollPayhead.setTax(payrollPayheadDTO.getTax());
		}else {
			payrollPayhead.setTax(null);
		}
		
		if(StringUtils.isNotBlank(payrollPayheadDTO.getApplicableFor())) {
			payrollPayhead.setApplicableFor(payrollPayheadDTO.getApplicableFor());
			if(payrollPayheadDTO.getApplicableFor().equalsIgnoreCase("Experienced")) {
				if(StringUtils.isNotBlank(payrollPayheadDTO.getExperienceYears())) {
					payrollPayhead.setExperienceYears(payrollPayheadDTO.getExperienceYears());
				}else {
					payrollPayhead.setExperienceYears(null);
				}
				if(StringUtils.isNotBlank(payrollPayheadDTO.getExperienceMonths())) {
					payrollPayhead.setExperienceMonths(payrollPayheadDTO.getExperienceMonths());
				}else {
					payrollPayhead.setExperienceMonths(null);
				}
			}
		}else {
			payrollPayhead.setApplicableFor(null);
		}
		
		if(StringUtils.isNotBlank(payrollPayheadDTO.getComponent())) {
			payrollPayhead.setComponent(payrollPayheadDTO.getComponent());
		}else {
			payrollPayhead.setComponent(null);
		}
		
		if(StringUtils.isNotBlank(payrollPayheadDTO.getPaidComponent())) {
			payrollPayhead.setPaidComponent(payrollPayheadDTO.getPaidComponent());
		}else {
			payrollPayhead.setPaidComponent("N");
		}
		
		if(StringUtils.isNotBlank(payrollPayheadDTO.getPartOfGratitutiy())) {
			payrollPayhead.setPartOfGratitutiy(payrollPayheadDTO.getPartOfGratitutiy());
		}else {
			payrollPayhead.setPartOfGratitutiy("N");
		}
		
		if(StringUtils.isNotBlank(payrollPayheadDTO.getFbpComponent())) {
			payrollPayhead.setFbpComponent(payrollPayheadDTO.getFbpComponent());
		}else {
			payrollPayhead.setFbpComponent("N");
		}
		
		if(StringUtils.isNotBlank(payrollPayheadDTO.getLeaveDependent())) {
			payrollPayhead.setLeaveDependent(payrollPayheadDTO.getLeaveDependent());
		}else {
			payrollPayhead.setLeaveDependent("N");
		}
		
		if(StringUtils.isNotBlank(payrollPayheadDTO.getShowInPayslip())) {
			payrollPayhead.setShowInPayslip(payrollPayheadDTO.getShowInPayslip());
		}else {
			payrollPayhead.setShowInPayslip("N");
		}
		
		if(StringUtils.isNotBlank(payrollPayheadDTO.getPfApplicable())) {
			payrollPayhead.setPfApplicable(payrollPayheadDTO.getPfApplicable());
		}else {
			payrollPayhead.setPfApplicable("N");
		}
		
		if(StringUtils.isNotBlank(payrollPayheadDTO.getEsiApplicable())) {
			payrollPayhead.setEsiApplicable(payrollPayheadDTO.getEsiApplicable());
		}else {
			payrollPayhead.setEsiApplicable("N");
		}
		
		if(StringUtils.isNotBlank(payrollPayheadDTO.getMethod())) {
			payrollPayhead.setMethod(payrollPayheadDTO.getMethod());
		}else {
			payrollPayhead.setMethod(null);
		}
		
		if(StringUtils.isNotBlank(payrollPayheadDTO.getFormula())) {
			payrollPayhead.setFormula(payrollPayheadDTO.getFormula());
		}else {
			payrollPayhead.setFormula(null);
		}
		
		payrollPayhead.setOrgLocationId(orgLocations);
		payrollPayhead.setStatus(Constants.RECORD_ACTIVE);
		
		return payrollPayheadDao.savePayrollPayhead(payrollPayhead);
	}

	@Override
	public ResponseList<PayrollPayheadDTO> getPayrollPayheadList(OrgLocations orgLocations) throws Exception {
		return payrollPayheadDao.getPayrollPayheadList(orgLocations);
	}
	
	@Override
	public ResponseList<PayrollPayheadDTO> getPayrollPayheadListWithPagination(PayrollPayheadDTO payrollPayheadDTO,
			OrgLocations orgLocations) throws Exception {
		return payrollPayheadDao.getPayrollPayheadListWithPagination(payrollPayheadDTO,orgLocations);
	}
	
	
	@Override
	public PayrollPayhead getPayrollPayheadByUUID(String uuid) throws Exception {
		return payrollPayheadDao.getPayrollPayheadByUUID(uuid);
	}

	@Override
	public Boolean findPayrollPayheadByNameAndLocationId(PayrollPayheadDTO payrollPayheadDTO) throws Exception {
		Boolean flag = payrollPayheadDao.findPayrollPayheadByNameAndLocationId(payrollPayheadDTO);
		return flag;
	}
	
	@Override
	public PayrollPayheadSalarySetup savePayrollPayheadSalarySetup(PayrollPayheadSalarySetup payrollPayheadSalarySetup) throws Exception {
		return payrollPayheadDao.savePayrollPayheadSalarySetup(payrollPayheadSalarySetup);
	}

	@Override
	public PayrollPayheadSalarySetup getPayrollPayheadSalarySetupByUUID(String uuid) throws Exception {
		return payrollPayheadDao.getPayrollPayheadSalarySetupByUUID(uuid);
	}

	@Override
	public ResponseList<PayrollPayheadSalarySetupDTO> getPayrollPayheadSalarySetupListByPayheadUuidAndLocationId(String uuid,
			OrgLocations orgLocations) throws Exception {
		return payrollPayheadDao. getPayrollPayheadSalarySetupListByPayheadUuidAndLocationId(uuid, orgLocations);
	}

}
