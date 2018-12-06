package com.bis.sourcelead.payroll.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
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
//		if(assTax.getUpdatedDate() != null)
//			associateTax.setUpdatedDate(assTax.getUpdatedDate());
//		else 
			associateTax.setUpdatedDate(new Date());
		associateTax.setCandidate(assTax.getCandidate().getId());
		associateTax.setCreatedBy(assTax.getCreatedBy());
		associateTax.setCreatedDate(assTax.getCreatedDate());
		associateTax.setFederalTaxFilliingStatus(assTax.getFederalTaxFilliingStatus());
		associateTax.setFederalTaxAllowances(assTax.getFederalTaxAllowances());
		associateTax.setFederalTaxAdditionnalWithHolding(assTax.getFederalTaxAdditionnalWithHolding());
		associateTax.setStateTaxFilliingStatus(assTax.getStateTaxFilliingStatus());
		associateTax.setStateTaxAllowances(assTax.getStateTaxAllowances());
		associateTax.setStateTaxAdditionnalWithHolding(assTax.getStateTaxAdditionnalWithHolding());
		associateTax.setFederalExempt(assTax.getFederalExempt());
		associateTax.setMediacareExempt(assTax.getMediacareExempt());
		associateTax.setSocialSecurityExempt(assTax.getSocialSecurityExempt());
		associateTax.setFutaExempt(assTax.getFutaExempt());
		associateTax.setSutaExempt(assTax.getSutaExempt());
		associateTax.setAssociateTaxUuid(assTax.getAssociateTaxUuid());
		associateTax.setResidenceTaxFilliingStatus(assTax.getResidenceTaxFilliingStatus());
		associateTax.setResidenceTaxAllowances(assTax.getResidenceTaxAllowances());
		associateTax.setResidenceTaxAdditionnalWithHolding(assTax.getResidenceTaxAdditionnalWithHolding());
		associateTax.setLocalTax(assTax.getLocalTax());
		associateTax.setLocalTaxState(assTax.getLocalTaxState());
		associateTax.setStatus(assTax.getStatus());
		associateTax.setOrgLocationId(assTax.getOrgLocationId().getId());
		associateTax.setSupplementType(assTax.getSupplementType());
		if(StringUtils.isNotBlank(preAssTax.getFederalTaxFilliingStatus())) {
			associateTax.setPreviousFederalTaxFilliingStatus(preAssTax.getFederalTaxFilliingStatus());
			associateTax.setPreviousFederalTaxAllowances(preAssTax.getFederalTaxAllowances());
			associateTax.setPreviousFederalTaxAdditionnalWithHolding(preAssTax.getFederalTaxAdditionnalWithHolding());
			associateTax.setPreviousStateTaxFilliingStatus(preAssTax.getStateTaxFilliingStatus());
			associateTax.setPreviousStateTaxAdditionnalWithHolding(preAssTax.getStateTaxAdditionnalWithHolding());
			associateTax.setPreviousStateTaxAllowances(preAssTax.getStateTaxAllowances());
			associateTax.setPreviousFederalExempt(preAssTax.getFederalExempt());
			associateTax.setPreviousSupplementType(preAssTax.getSupplementType());
			associateTax.setPreviousMediacareExempt(preAssTax.getMediacareExempt());
			associateTax.setPreviousSocialSecurityExempt(preAssTax.getSocialSecurityExempt());
			associateTax.setPreviousFutaExempt(preAssTax.getFutaExempt());
			associateTax.setPreviousSutaExempt(preAssTax.getSutaExempt());
			associateTax.setPreviousResidenceTaxFilliingStatus(preAssTax.getResidenceTaxFilliingStatus());
			associateTax.setPreviousResidenceTaxAllowances(preAssTax.getResidenceTaxAllowances());
			associateTax.setPreviousResidenceTaxAdditionnalWithHolding(preAssTax.getResidenceTaxAdditionnalWithHolding());
			associateTax.setPreviousLocalTax(preAssTax.getLocalTax());
			associateTax.setPreviousLocalTaxState(preAssTax.getLocalTaxState());
		}else {
			associateTax.setPreviousFederalTaxFilliingStatus("NA");
			associateTax.setPreviousFederalTaxAllowances("NA");
			associateTax.setPreviousFederalTaxAdditionnalWithHolding("NA");
			associateTax.setPreviousStateTaxFilliingStatus("NA");
			associateTax.setPreviousStateTaxAdditionnalWithHolding("NA");
			associateTax.setPreviousStateTaxAllowances("NA");
			associateTax.setPreviousFederalExempt(false);
			associateTax.setPreviousSupplementType("NA");
			associateTax.setPreviousMediacareExempt(false);
			associateTax.setPreviousSocialSecurityExempt(false);
			associateTax.setPreviousFutaExempt(false);
			associateTax.setPreviousSutaExempt(false);
			associateTax.setPreviousResidenceTaxFilliingStatus("NA");
			associateTax.setPreviousResidenceTaxAllowances("NA");
			associateTax.setPreviousResidenceTaxAdditionnalWithHolding("NA");
			associateTax.setPreviousLocalTax("NA");
			associateTax.setPreviousLocalTaxState("NA");
		}
		associateTax = usPayrollAssociateTaxHistoryDao.storeUSPayrollAssociateTaxHistory(associateTax);
		return associateTax;
	}

	@Override
	public List<USPayrollAssociateTaxHistoryDTO> getAssociateTaxHistoryByCandidateId(Long candidateId,String dateFormat)
	{
		List<USPayrollAssociateTaxHistory> list = usPayrollAssociateTaxHistoryDao.getAssociateTaxHistoryByCandidateId(candidateId);
		List<USPayrollAssociateTaxHistoryDTO> dtoList = new ArrayList<>();
		try {
			for(USPayrollAssociateTaxHistory assTax : list) {
				USPayrollAssociateTaxHistoryDTO dto = new USPayrollAssociateTaxHistoryDTO();
				if(StringUtils.isNotBlank(assTax.getUpdatedBy())) {
					User user = userService.getUserByUserCode(assTax.getUpdatedBy());
					dto.setUserName(user.getFullName());
					dto.setProfileURL(user.getFileUrl());
				}else {
					User user = userService.getUserByUserCode(assTax.getCreatedBy());
					dto.setUserName(user.getFullName());
					dto.setProfileURL(user.getFileUrl());
				}
				
				dto.setUpdatedBy(assTax.getUpdatedBy());
				String updatedDateStr = getUpdatedDateTimeAsString(assTax.getUpdatedDate(),dateFormat);
				dto.setUpdatedDate(updatedDateStr);
				dto.setCandidate(assTax.getCandidate());
				dto.setCreatedBy(assTax.getCreatedBy());
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
				dto.setPreviousFederalExempt(assTax.getPreviousFederalExempt().toString());
				dto.setMediacareExempt(assTax.getMediacareExempt().toString());
				dto.setPreviousMediacareExempt(assTax.getPreviousMediacareExempt().toString());
				dto.setSocialSecurityExempt(assTax.getSocialSecurityExempt().toString());
				dto.setPreviousSocialSecurityExempt(assTax.getPreviousSocialSecurityExempt().toString());
				dto.setFutaExempt(assTax.getFutaExempt().toString());
				dto.setPreviousFutaExempt(assTax.getPreviousFutaExempt().toString());
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
				dto.setLocalTaxState(assTax.getLocalTaxState());
				dto.setPreviousLocalTaxState(assTax.getPreviousLocalTaxState());
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

	private String getUpdatedDateTimeAsString(Date date, String dateFormat)
	{
		SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat);
    	SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
    	String dateAsString = dateFormatter.format(date);
    	String timeAsString = timeFormatter.format(date);
    	StringBuilder sb = new StringBuilder();
    	sb.append(dateAsString).append("#").append(timeAsString);
		return sb.toString();
	}
	
	
}
