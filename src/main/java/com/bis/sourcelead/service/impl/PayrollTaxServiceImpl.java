package com.bis.sourcelead.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.PayrollTaxDao;
import com.bis.sourcelead.dao.TaxDao;
import com.bis.sourcelead.dto.CentralTaxDTO;
import com.bis.sourcelead.dto.PayrollTaxDTO;
import com.bis.sourcelead.dto.ProfessionalTaxDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.StateTaxDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PayrollTax;
import com.bis.sourcelead.entity.ProfessionalTax;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.PayrollTaxService;
import com.bis.sourcelead.service.TaxService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class PayrollTaxServiceImpl implements PayrollTaxService {

	@Autowired
	private PayrollTaxDao payrollTaxDao;

	@Autowired
	private TaxDao taxDao;

	@Autowired
	private TaxService taxService;

	@Override
	public Response<Long> savePayrollTaxConfiguration(PayrollTaxDTO payrollTaxDTO, User user,
			OrgLocations orgLocations) {
		Response<Long> response = new Response<>();
		try {
			PayrollTax payrollTax = null;
			if (payrollTaxDTO.getId() > 0) {
				payrollTax = payrollTaxDao.getPayrollTaxById(payrollTaxDTO.getId());
				payrollTax.setUpdatedBy(user);
				payrollTax.setUpdatedDate(new Date());
			} else {
				payrollTax = new PayrollTax();
				payrollTax.setFinancialYear(payrollTaxDTO.getFinancialYear());
				payrollTax.setCreatedBy(user);
				payrollTax.setCreatedDate(new Date());
			}
			payrollTax.setOrgLocations(orgLocations);
			payrollTax.setStateTax(taxDao.getStateTaxById(payrollTaxDTO.getStateTaxId()));
			payrollTax.setEmployeeESIContribution(new BigDecimal(payrollTaxDTO.getEmployeeESIContribution()));
			payrollTax.setEmployerESIContribution(new BigDecimal(payrollTaxDTO.getEmployerESIContribution()));
			payrollTax.setEmployeePFContribution(new BigDecimal(payrollTaxDTO.getEmployeePFContribution()));
			payrollTax.setEmployerPFContribution(new BigDecimal(payrollTaxDTO.getEmployerPFContribution()));
//			payrollTax.setEdli(new BigDecimal(payrollTaxDTO.getEdli()));
//			payrollTax.setPension(new BigDecimal(payrollTaxDTO.getPension()));
//			payrollTax.setEdliAdminCharges(new BigDecimal(payrollTaxDTO.getEdliAdminCharges()));
//			payrollTax.setPfAdminCharges(new BigDecimal(payrollTaxDTO.getPfAdminCharges()));
			payrollTax.setPfCeilValue(new BigDecimal(payrollTaxDTO.getPfCeilValue()));
			long result = payrollTaxDao.savePayrollTaxConfiguration(payrollTax);
			if (result > 0) {
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
				response.setObject(result);
			} else if (result == -2) {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			} else {
				response.setResponse(Result.FAILED.name());
				response.setResponseMessage(Result.FAILED.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public PayrollTaxDTO getPayrollTaxConfigurationByOrgLocationId(Long orgLocationId, String financialYear) {
		try {
			PayrollTax payrollTax = payrollTaxDao.getPayrollTaxConfigurationByOrgLocationId(orgLocationId, financialYear);
			if (payrollTax != null) {
				PayrollTaxDTO payrollTaxDTO = new PayrollTaxDTO();
				return setPayrollTaxToPayrollTaxDTO(payrollTax, payrollTaxDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public PayrollTaxDTO getPayrollTaxConfigurationForOrgLocation(String stateName, String financialYear,
			String taxType) {
		PayrollTaxDTO payrollTaxDTO = new PayrollTaxDTO();
		try {
			StateTaxDTO stateTaxDTO = taxService.getStateTaxByStateNameAndFinancialYear(stateName, financialYear);
			CentralTaxDTO centralTaxDTO = taxService.getCentralTaxByTaxType(taxType);
			payrollTaxDTO.setId(centralTaxDTO.getId());
			payrollTaxDTO.setEmployeeESIContribution(centralTaxDTO.getEmployeeEsi() + "");
			payrollTaxDTO.setEmployerESIContribution(centralTaxDTO.getEmployerEsi() + "");
			payrollTaxDTO.setEmployeePFContribution(centralTaxDTO.getEmployeepf() + "");
			payrollTaxDTO.setEmployerPFContribution(centralTaxDTO.getEmployerpf() + "");
			payrollTaxDTO.setStateTaxId(stateTaxDTO.getId());
			payrollTaxDTO.setPtList(stateTaxDTO.getProfessionalTaxList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return payrollTaxDTO;
	}
	
	
	@Override
	public PayrollTaxDTO getPayrollTaxConfigurationByOrgLocationId(Long orgLocationId) {
		try {
			PayrollTax payrollTax = payrollTaxDao.getPayrollTaxConfigurationByOrgLocationId(orgLocationId);
			if (payrollTax != null) {
				PayrollTaxDTO payrollTaxDTO = new PayrollTaxDTO();
				return setPayrollTaxToPayrollTaxDTO(payrollTax, payrollTaxDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public PayrollTaxDTO setPayrollTaxToPayrollTaxDTO(PayrollTax payrollTax, PayrollTaxDTO payrollTaxDTO) {
			payrollTaxDTO.setId(payrollTax.getId());
			payrollTaxDTO.setOrgLocationId(payrollTax.getOrgLocations().getId());
			payrollTaxDTO.setEmployeeESIContribution(payrollTax.getEmployeeESIContribution() + "");
			payrollTaxDTO.setEmployerESIContribution(payrollTax.getEmployerESIContribution() + "");
			payrollTaxDTO.setEmployeePFContribution(payrollTax.getEmployeePFContribution() + "");
			payrollTaxDTO.setEmployerPFContribution(payrollTax.getEmployerPFContribution() + "");
			payrollTaxDTO.setEdli(payrollTax.getEdli() + "");
			payrollTaxDTO.setPension(payrollTax.getPension() + "");
			payrollTaxDTO.setPfAdminCharges(payrollTax.getPfAdminCharges() + "");
			payrollTaxDTO.setEdliAdminCharges(payrollTax.getEdliAdminCharges() + "");
			payrollTaxDTO.setPfCeilValue(payrollTax.getPfCeilValue() + "");
			if (payrollTax.getStateTax() != null) {
				payrollTaxDTO.setStateTaxId(payrollTax.getStateTax().getId());
				List<ProfessionalTaxDTO> ptList = new LinkedList<>();
				for (ProfessionalTax pt : payrollTax.getStateTax().getProfessionalTaxList()) {
					ProfessionalTaxDTO professionalTaxDTO = new ProfessionalTaxDTO();
					professionalTaxDTO.setId(pt.getId());
					professionalTaxDTO.setStateTaxId(pt.getStateTax().getId());
					professionalTaxDTO.setTaxType(payrollTax.getStateTax().getSourceleadTaxType().getDescription());
					professionalTaxDTO.setAmount(CommonUtil.toFixed2(pt.getAmount()));
					professionalTaxDTO.setAmountFrom(CommonUtil.toFixed2(pt.getAmountFrom()));
					professionalTaxDTO.setAmountTo(CommonUtil.toFixed2(pt.getAmountTo()));
					professionalTaxDTO.setMonthApplicable(pt.getMonthApplicable());
					ptList.add(professionalTaxDTO);
				}
				payrollTaxDTO.setPtList(ptList);
			}
		return payrollTaxDTO;
	}

}
