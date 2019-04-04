package com.bis.sourcelead.payroll.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.payroll.dao.AssociateTaxDao;
import com.bis.sourcelead.payroll.dto.AssociateBankDataDTO;
import com.bis.sourcelead.payroll.dto.AssociateDeductionDTO;
import com.bis.sourcelead.payroll.dto.AssociateTaxDTO;
import com.bis.sourcelead.payroll.entity.AssociateTax;
import com.bis.sourcelead.payroll.entity.USPayrollAssociateTaxHistory;
import com.bis.sourcelead.payroll.service.AssociateTaxService;
import com.bis.sourcelead.payroll.service.USPayrollAssociateTaxHistoryService;
import com.bis.sourcelead.payroll.view.AssociateTaxDedContriView;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class AssociateTaxServiceImpl implements AssociateTaxService{

	private static final Logger logger = LoggerFactory.getLogger(AssociateBankDataServiceImpl.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	AssociateTaxDao associateTaxDao;
	
	@Autowired
	CandidateDao candidateDao;
	
	@Autowired
	USPayrollAssociateTaxHistoryService uSPayrollAssociateTaxHistoryService; 
	
	
	@Override
	public Response<AssociateTaxDTO> saveAssociateTax(AssociateTaxDTO associateTaxDTO, OrgLocations orgLocation, User loginUser)
	{
		Response<AssociateTaxDTO> resObj = new Response<>();
		try {
			AssociateTax associateTax = null;
			AssociateTax previousAssociateTax = new AssociateTax();
			if (Validator.validate(associateTaxDTO)) {
				if(associateTaxDTO.getUserId() != null) {
					Candidate candidate = candidateDao.getCandidateByConsultantUserId(Long.valueOf(associateTaxDTO.getUserId()),orgLocation.getId());
					associateTax = associateTaxDao.findAssociateTaxSummaryByCandidateId(candidate.getId(),orgLocation);
					if(associateTax != null) {
						previousAssociateTax = setPreviousAssociateTaxData(associateTax);
					}else {
						associateTax = new AssociateTax();
//						Candidate candidate = candidateDao.getCandidateByConsultantUserId(associateTaxDTO.getUserId(),orgLocation.getId());
						associateTax.setCandidate(candidate);
						associateTax.setCreatedBy(loginUser.getUserCode());
						associateTax.setCreatedDate(new Date());
						associateTax.setUpdatedBy(loginUser.getUserCode());
						associateTax.setUpdatedDate(new Date());
					}
				}
				associateTax.setFederalTaxFilliingStatus(associateTaxDTO.getFederaltaxFillingStatus());
				associateTax.setFederalTaxAllowances(associateTaxDTO.getFederaltaxAllowances());
				associateTax.setFederalTaxAdditionnalWithHolding(associateTaxDTO.getFederaltaxAdditionalWithHolding());
				associateTax.setStateTaxFilliingStatus(associateTaxDTO.getStateTaxFillingStatus());
				associateTax.setStateTaxAllowances(associateTaxDTO.getStateTaxAllowances());
				associateTax.setStateTaxAdditionnalWithHolding(associateTaxDTO.getStateTaxAdditionalWithHolding());
				if (StringUtils.isNoneBlank(associateTaxDTO.getFederalExempt())&& Constants.BOOLEAN_TRUE.equalsIgnoreCase(associateTaxDTO.getFederalExempt())) {
					associateTax.setFederalExempt(true);
				}
				else {
					associateTax.setFederalExempt(false);
				}
				if (StringUtils.isNoneBlank(associateTaxDTO.getMedicareExempt())&& Constants.BOOLEAN_TRUE.equalsIgnoreCase(associateTaxDTO.getMedicareExempt())) {
					associateTax.setMediacareExempt(true);
				}
				else {
					associateTax.setMediacareExempt(false);
				}
				if (StringUtils.isNoneBlank(associateTaxDTO.getSocailSecurityExempt()) && Constants.BOOLEAN_TRUE.equalsIgnoreCase(associateTaxDTO.getSocailSecurityExempt())) {
					associateTax.setSocialSecurityExempt(true);
				}
				else {
					associateTax.setSocialSecurityExempt(false);
				}
				if (StringUtils.isNoneBlank(associateTaxDTO.getFutaExempt()) && Constants.BOOLEAN_TRUE.equalsIgnoreCase(associateTaxDTO.getFutaExempt())) {
					associateTax.setFutaExempt(true);
				}
				else {
					associateTax.setFutaExempt(false);
				}
				if (StringUtils.isNoneBlank(associateTaxDTO.getSutaExempt()) && Constants.BOOLEAN_TRUE.equalsIgnoreCase(associateTaxDTO.getSutaExempt())) {
					associateTax.setSutaExempt(true);
				}
				else {
					associateTax.setSutaExempt(false);
				}
				associateTax.setAssociateTaxUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
				associateTax.setResidenceTaxFilliingStatus(associateTaxDTO.getResidenceLocationFillingStatus());
				associateTax.setResidenceTaxAllowances(associateTaxDTO.getResidenceLocationAllowances());
				associateTax.setResidenceTaxAdditionnalWithHolding(associateTaxDTO.getResidenceLocationAdditionalWithHolding());
				if (!"NO_TAX".equalsIgnoreCase(associateTaxDTO.getLocalTax())) {
					associateTax.setLocalTax(Constants.LOCAL_TAX_YES);
					associateTax.setLocalTaxState(associateTaxDTO.getLocalTax());
				}
				else {
					associateTax.setLocalTax(Constants.LOCAL_TAX_NO);
					associateTax.setLocalTaxState(associateTaxDTO.getLocalTax());
				}
				associateTax.setStatus(1);
				associateTax.setOrgLocationId(orgLocation);
//				associateTax.setCreatedBy(loginUser.getUserCode());
//				associateTax.setCreatedDate(new Date());
//				associateTax.setUpdatedBy(loginUser.getUserCode());
//				associateTax.setUpdatedDate(new Date());
				associateTax.setSupplementType(associateTaxDTO.getResidenceLocationSupplementType());
				AssociateTax savedAssociateTax = associateTaxDao.saveAssociateTax(associateTax);
				if (savedAssociateTax != null) {
					resObj.setResponse(Result.SUCCESS.name());
					resObj.setResponseMessage(Result.SUCCESS.getValue());
					associateTaxDTO.setId(savedAssociateTax.getId());
					resObj.setObject(associateTaxDTO);
					// below method is used to store history of Associate Tax
					USPayrollAssociateTaxHistory his = uSPayrollAssociateTaxHistoryService.storeUSPayrollAssociateTaxHistory(savedAssociateTax,previousAssociateTax);
				}
			}
			else {
				resObj.setResponse("DataMissing");
				resObj.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} catch (Exception e) {
			resObj.setResponse(Result.AWKWARD.name());
			resObj.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return resObj;
	}
	
	private AssociateTax setPreviousAssociateTaxData(AssociateTax assTax)
	{
		AssociateTax associateTax = new AssociateTax();
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
		associateTax.setSupplementType(assTax.getSupplementType());
		return associateTax;
	}

	@Override
	public Response<AssociateTaxDTO> isLocalTaxYes(AssociateTaxDTO associateTaxDTO, OrgLocations orgLocation, User loginUser)
	{
		Response<AssociateTaxDTO>  resObj = new Response<>();
		String location[] = associateTaxDTO.getLocalTax().split(",");
		String state = location[0];
		String city = location[1];
		String isLocalTaxYes = getIsLocalTaxYes(state);
		associateTaxDTO.setState(state);
		associateTaxDTO.setCity(city);
		associateTaxDTO.setIsLocalTaxYes(isLocalTaxYes);
		resObj.setObject(associateTaxDTO);
		return resObj;
	}

	/**
	 * Get Local Tax YES or NO
	 * @param state
	 * @return String
	 */
	private String getIsLocalTaxYes(String state)
	{
		Map<String,String> map = new HashMap<String, String>();
		map.put("Albama", "YES");
		map.put("Alaska", "YES");
		map.put("Arizona", "YES");
		map.put("Arkansas", "YES");
		map.put("California", "YES");
		map.put("Colorado", "YES");
		map.put("Connectuit", "NO");
		map.put("Delaware", "NO");
		map.put("District of Columbia", "NO");
		map.put("Florida", "NO");
		map.put("Georgia", "NO");
		map.put("Hawaii", "NO");
		map.put("Idaho", "NO");
		
		map.put("IliNOis", "YES");
		map.put("Indiana", "YES");
		map.put("IOWA", "NO");
		map.put("Kansas", "NO");
		map.put("Kentucky", "YES");
		map.put("Lousiana", "NO");
		map.put("Maine", "NO");
		map.put("Maryland", "YES");
		map.put("Massachusetts", "NO");
			
		map.put("Michigan", "NO");
		map.put("Minnesota", "NO");
		map.put("Mississippi", "NO");
		
		map.put("Missouri", "YES");
		map.put("Montana", "NO");
		
		map.put("Nebraska", "NO");
		map.put("Nevada", "NO");
		map.put("New-hampshire", "NO");
		map.put("New Jersey", "YES");
		map.put("New Mexico", "NO");
		map.put("New York", "YES");
		map.put("NOrth Carolina", "NO");
		map.put("NOrth Dakota", "NO");
		map.put("Ohio", "YES");
		map.put("Oklahoma", "NO");
		map.put("Oregon", "YES");
		map.put("Pennsylvania", "YES");
		map.put("Puerto Rica", "NO");
		map.put("Rhode Island", "NO");
		map.put("South Carolina", "NO");
		map.put("South Dakota", "NO");
		map.put("Tennessee", "NO");
		map.put("Texas", "NO");
		map.put("Utah", "NO");
		map.put("Vermont", "NO");
		
		map.put("Virgin Islands", "NO");
		map.put("Virginia", "NO");
		map.put("Washington", "NO");
		map.put("West Virginia", "YES");
		map.put("Wisconsin", "NO");
		map.put("Wyoming", "NO");
		return map.get(state);
		
	}

	@Override
	public AssociateTaxDTO findAssociateTaxSummaryByCandidateId(Long candidateId, OrgLocations orgLocation)
	{
		AssociateTaxDTO associateTaxDTO = null;
		try {
			AssociateTax associateTax = associateTaxDao.findAssociateTaxSummaryByCandidateId(candidateId,orgLocation);
			if(associateTax != null) {
				associateTaxDTO = new AssociateTaxDTO();
				associateTaxDTO.setId(associateTax.getId());
				associateTaxDTO.setFederaltaxFillingStatus(associateTax.getFederalTaxFilliingStatus());
				associateTaxDTO.setFederaltaxAllowances(associateTax.getFederalTaxAllowances());
				associateTaxDTO.setFederaltaxAdditionalWithHolding(associateTax.getFederalTaxAdditionnalWithHolding());
				associateTaxDTO.setStateTaxFillingStatus(associateTax.getStateTaxFilliingStatus());
				associateTaxDTO.setStateTaxAllowances(associateTax.getStateTaxAllowances());
				associateTaxDTO.setStateTaxAdditionalWithHolding(associateTax.getStateTaxAdditionnalWithHolding());
				associateTaxDTO.setResidenceLocationFillingStatus(associateTax.getResidenceTaxFilliingStatus());
				associateTaxDTO.setResidenceLocationAllowances(associateTax.getResidenceTaxAllowances());
				associateTaxDTO.setResidenceLocationAdditionalWithHolding(associateTax.getResidenceTaxAdditionnalWithHolding());
				associateTaxDTO.setSupplementType(associateTax.getSupplementType());
				associateTaxDTO.setResidenceLocationSupplementType(associateTax.getSupplementType());
				associateTaxDTO.setLocalTax(associateTax.getLocalTax());
				associateTaxDTO.setLocalTaxState(associateTax.getLocalTaxState());
				
				
				if(associateTax.getFederalExempt()) {
					associateTaxDTO.setFederalExempt("YES");
				}else {
					associateTaxDTO.setFederalExempt("NO");
				}
				if(associateTax.getMediacareExempt()) {
					associateTaxDTO.setMedicareExempt("YES");
				}else {
					associateTaxDTO.setMedicareExempt("NO");
				}
				if(associateTax.getFutaExempt()) {
					associateTaxDTO.setFutaExempt("YES");
				}else {
					associateTaxDTO.setFutaExempt("NO");
				}
				if(associateTax.getSocialSecurityExempt()) {
					associateTaxDTO.setSocailSecurityExempt("YES");
				}else{
					associateTaxDTO.setSocailSecurityExempt("NO");
				}
				if(associateTax.getSutaExempt()) {
					associateTaxDTO.setSutaExempt("YES");
				}else {
					associateTaxDTO.setSutaExempt("NO");
				}
				
				StringBuilder sb = new StringBuilder();
				if(orgLocation.getCity() != null && orgLocation.getState() != null) {
					sb.append(orgLocation.getCountry()).append(",").append(orgLocation.getState()).append(",").append(orgLocation.getCity());
				}else {
					sb.append(orgLocation.getCountry());
				}
				
				associateTaxDTO.setLocation(sb.toString());
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return associateTaxDTO;
	}

	@Override
	public AssociateTaxDedContriView findUserTaxDeatails(Long candidateId, OrgLocations orgLocation)
	{
		return associateTaxDao.findUserTaxDeatails(candidateId,orgLocation);
	}


}
