package com.bis.sourcelead.payroll.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dao.USPayrollAssociateTaxHistoryDao;
import com.bis.sourcelead.payroll.dto.USPayrollAssociateTaxHistoryDTO;
import com.bis.sourcelead.payroll.entity.AssociateTax;
import com.bis.sourcelead.payroll.entity.USPayrollAssociateTaxHistory;
import com.bis.sourcelead.payroll.service.USPayrollAssociateTaxHistoryService;
import com.bis.sourcelead.service.UserService;

@Service
public class USPayrollAssociateTaxHistoryServiceImpl implements USPayrollAssociateTaxHistoryService{

	@Autowired
	USPayrollAssociateTaxHistoryDao usPayrollAssociateTaxHistoryDao;
	
	@Autowired
	UserService userService;
	
	@Override
	public USPayrollAssociateTaxHistory storeUSPayrollAssociateTaxHistory(AssociateTax assTax,AssociateTax preAssTax)
	{
		USPayrollAssociateTaxHistory associateTax = new USPayrollAssociateTaxHistory();
		associateTax.setAssTaxId(assTax.getId());
		associateTax.setUpdatedBy(assTax.getUpdatedBy());
		associateTax.setUpdatedDate(assTax.getUpdatedDate());
		associateTax.setCandidate(assTax.getCandidate().getId());
		associateTax.setCreatedBy(assTax.getCreatedBy());
		associateTax.setCreatedDate(assTax.getCreatedDate());
		associateTax.setFederalTaxFilliingStatus(assTax.getFederalTaxFilliingStatus());
		associateTax.setFederalTaxAllowances(assTax.getFederalTaxAllowances());
		associateTax.setFederalTaxAdditionnalWithHolding(assTax.getFederalTaxAdditionnalWithHolding());
		associateTax.setPreviousFederalTaxFilliingStatus(preAssTax.getFederalTaxFilliingStatus());
		associateTax.setPreviousFederalTaxAllowances(preAssTax.getFederalTaxAllowances());
		associateTax.setPreviousFederalTaxAdditionnalWithHolding(preAssTax.getFederalTaxAdditionnalWithHolding());
		associateTax.setStateTaxFilliingStatus(assTax.getStateTaxFilliingStatus());
		associateTax.setStateTaxAllowances(assTax.getStateTaxAllowances());
		associateTax.setStateTaxAdditionnalWithHolding(assTax.getStateTaxAdditionnalWithHolding());
		associateTax.setPreviousStateTaxFilliingStatus(preAssTax.getStateTaxFilliingStatus());
		associateTax.setPreviousStateTaxAdditionnalWithHolding(preAssTax.getStateTaxAdditionnalWithHolding());
		associateTax.setPreviousStateTaxAllowances(preAssTax.getStateTaxAllowances());
		associateTax.setFederalExempt(assTax.getFederalExempt());
		associateTax.setPreviousFederalExempt(preAssTax.getFederalExempt());
		associateTax.setMediacareExempt(assTax.getMediacareExempt());
		associateTax.setPreviousMediacareExempt(preAssTax.getMediacareExempt());
		associateTax.setSocialSecurityExempt(assTax.getSocialSecurityExempt());
		associateTax.setPreviousSocialSecurityExempt(preAssTax.getSocialSecurityExempt());
		associateTax.setFutaExempt(assTax.getFutaExempt());
		associateTax.setPreviousFutaExempt(preAssTax.getFutaExempt());
		associateTax.setSutaExempt(assTax.getSutaExempt());
		associateTax.setPreviousSutaExempt(preAssTax.getSutaExempt());
		associateTax.setAssociateTaxUuid(assTax.getAssociateTaxUuid());
		associateTax.setResidenceTaxFilliingStatus(assTax.getResidenceTaxFilliingStatus());
		associateTax.setPreviousResidenceTaxFilliingStatus(preAssTax.getResidenceTaxFilliingStatus());
		associateTax.setResidenceTaxAllowances(assTax.getResidenceTaxAllowances());
		associateTax.setPreviousResidenceTaxAllowances(preAssTax.getResidenceTaxAllowances());
		associateTax.setResidenceTaxAdditionnalWithHolding(assTax.getResidenceTaxAdditionnalWithHolding());
		associateTax.setPreviousResidenceTaxAdditionnalWithHolding(preAssTax.getResidenceTaxAdditionnalWithHolding());
		associateTax.setLocalTax(assTax.getLocalTax());
		associateTax.setPreviousLocalTax(preAssTax.getLocalTax());
		associateTax.setLocalTaxState(assTax.getLocalTax());
		associateTax.setPreviousLocalTaxState(preAssTax.getLocalTax());
		associateTax.setStatus(assTax.getStatus());
		associateTax.setOrgLocationId(assTax.getOrgLocationId().getId());
		associateTax.setSupplementType(assTax.getSupplementType());
		associateTax.setPreviousSupplementType(preAssTax.getSupplementType());
		associateTax = usPayrollAssociateTaxHistoryDao.storeUSPayrollAssociateTaxHistory(associateTax);
		return associateTax;
	}

	@Override
	public List<USPayrollAssociateTaxHistoryDTO> getAssociateTaxHistoryByCandidateId(Long candidateId)
	{
		List<USPayrollAssociateTaxHistory> list = usPayrollAssociateTaxHistoryDao.getAssociateTaxHistoryByCandidateId(candidateId);
		List<USPayrollAssociateTaxHistoryDTO> dtoList = new ArrayList<>();
		try {
			for(USPayrollAssociateTaxHistory assTax : list) {
				USPayrollAssociateTaxHistoryDTO dto = new USPayrollAssociateTaxHistoryDTO();
				User user = userService.getUserByUserCode(assTax.getUpdatedBy());
				dto.setUserName(user.getFullName());
				dto.setProfileURL(user.getFileUrl());
				dto.setUpdatedBy(assTax.getUpdatedBy());
//				dto.setUpdatedDate(assTax.getUpdatedDate());
				dto.setCandidate(assTax.getCandidate());
				dto.setCreatedBy(assTax.getCreatedBy());
//				dto.setCreatedDate(assTax.getCreatedDate());
				dto.setFederalTaxFilliingStatus(assTax.getFederalTaxFilliingStatus());
				dto.setFederalTaxAllowances(assTax.getFederalTaxAllowances());
				dto.setFederalTaxAdditionnalWithHolding(assTax.getFederalTaxAdditionnalWithHolding());
				dto.setPreviousFederalTaxFilliingStatus(assTax.getPreviousFederalTaxFilliingStatus());
				dto.setPreviousFederalTaxAllowances(assTax.getPreviousFederalTaxAllowances());
				dto.setPreviousFederalTaxAdditionnalWithHolding(assTax.getPreviousFederalTaxAdditionnalWithHolding());
				dto.setStateTaxFilliingStatus(assTax.getStateTaxFilliingStatus());
				dto.setStateTaxAllowances(assTax.getStateTaxAllowances());
				dto.setStateTaxAdditionnalWithHolding(assTax.getStateTaxAdditionnalWithHolding());
				dto.setPreviousStateTaxFilliingStatus(assTax.getPreviousStateTaxFilliingStatus());
				dto.setPreviousStateTaxAdditionnalWithHolding(assTax.getPreviousStateTaxAdditionnalWithHolding());
				dto.setPreviousStateTaxAllowances(assTax.getPreviousStateTaxAllowances());
				dto.setFederalExempt(assTax.getFederalExempt().toString());
				dto.setPreviousFederalExempt(assTax.getFederalExempt().toString());
				dto.setMediacareExempt(assTax.getMediacareExempt().toString());
				dto.setPreviousMediacareExempt(assTax.getPreviousMediacareExempt().toString());
				dto.setSocialSecurityExempt(assTax.getSocialSecurityExempt().toString());
				dto.setPreviousSocialSecurityExempt(assTax.getPreviousSocialSecurityExempt().toString());
				dto.setFutaExempt(assTax.getFutaExempt().toString());
				dto.setPreviousFutaExempt(assTax.getFutaExempt().toString());
				dto.setSutaExempt(assTax.getSutaExempt().toString());
				dto.setPreviousSutaExempt(assTax.getPreviousSutaExempt().toString());
				dto.setAssociateTaxUuid(assTax.getAssociateTaxUuid());
				dto.setResidenceTaxFilliingStatus(assTax.getResidenceTaxFilliingStatus());
				dto.setPreviousResidenceTaxFilliingStatus(assTax.getPreviousResidenceTaxFilliingStatus());
				dto.setResidenceTaxAllowances(assTax.getResidenceTaxAllowances());
				dto.setPreviousResidenceTaxAllowances(assTax.getPreviousResidenceTaxAllowances());
				dto.setResidenceTaxAdditionnalWithHolding(assTax.getResidenceTaxAdditionnalWithHolding());
				dto.setPreviousResidenceTaxAdditionnalWithHolding(assTax.getPreviousResidenceTaxAdditionnalWithHolding());
				dto.setLocalTax(assTax.getLocalTax());
				dto.setPreviousLocalTax(assTax.getPreviousLocalTax());
				dto.setLocalTaxState(assTax.getLocalTax());
				dto.setPreviousLocalTaxState(assTax.getPreviousLocalTax());
				dto.setStatus(assTax.getStatus());
				dto.setOrgLocationId(assTax.getOrgLocationId());
				dto.setSupplementType(assTax.getSupplementType());
				dto.setPreviousSupplementType(assTax.getPreviousSupplementType());
				dtoList.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dtoList;
	}
	
	
}
