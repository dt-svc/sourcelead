package com.bis.sourcelead.payroll.service.impl;

import java.util.Date;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.payroll.dao.AssociateBankDataDao;
import com.bis.sourcelead.payroll.dto.AssociateBankDataDTO;
import com.bis.sourcelead.payroll.entity.AssociateBankData;
import com.bis.sourcelead.payroll.service.AssociateBankDataService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class AssociateBankDataServiceImpl implements AssociateBankDataService{
	
	private static final Logger logger = LoggerFactory.getLogger(AssociateBankDataServiceImpl.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	AssociateBankDataDao associateBankDataDao;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	private CandidateDao candidateDao;
	
	@Override
	public Response<AssociateBankDataDTO> saveAssociateBankData(AssociateBankDataDTO associateBankDataDTO, OrgLocations orgLocation, User loginUser)
	{
		AssociateBankData associateBankData = null;
		Response<AssociateBankDataDTO> resObj = new Response<>();
		try {
			if (Validator.validate(associateBankDataDTO)) {
				if(associateBankDataDTO.getUserId() != null){
					Candidate candidate = candidateDao.getCandidateByConsultantUserId(Long.valueOf(associateBankDataDTO.getUserId()),orgLocation.getId());
					associateBankData = associateBankDataDao.findAssociateBankDataByCandidateId(candidate.getId(),orgLocation);
					if(associateBankData != null) {
						associateBankData.setUpdatedBy(loginUser.getUserCode());
						associateBankData.setUpdatedDate(new Date());
					}else {
						associateBankData = new AssociateBankData();
//						User user = userService.getUserById(Long.parseLong(associateBankDataDTO.getUserId()));
//						candidate = candidateService.getCandidateByConsultantUserId(Long.parseLong(associateBankDataDTO.getUserId()), orgLocation.getId());
						associateBankData.setCandidate(candidate);
						associateBankData.setCreatedDate(new Date());
						associateBankData.setCreatedBy(loginUser.getUserCode());
					}
				
				}
				associateBankData.setAccountType(associateBankDataDTO.getAccountType());
				if(StringUtils.isNotBlank(associateBankDataDTO.getBankName()))
					associateBankData.setBankName(associateBankDataDTO.getBankName());
				if(StringUtils.isNotBlank(associateBankDataDTO.getAccountNumber()))
					associateBankData.setAccountNumber(associateBankDataDTO.getAccountNumber());
				if(StringUtils.isNotBlank(associateBankDataDTO.getRoutingNumber()))
					associateBankData.setRoutingNumber(associateBankDataDTO.getRoutingNumber());
				associateBankData.setOrgLocationId(orgLocation);
				associateBankData.setStatus(1);
				associateBankData.setAssociateBankUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
//				associateBankData.setCreatedBy(loginUser.getUserCode());
//				associateBankData.setUpdatedBy(loginUser.getUserCode());
//				associateBankData.setCreatedDate(new Date());
//				associateBankData.setUpdatedDate(new Date());
				AssociateBankData bankData = associateBankDataDao.saveAssociateBankData(associateBankData);
				if (bankData != null) {
					resObj.setResponse(Result.SUCCESS.name());
					resObj.setResponseMessage(Result.SUCCESS.getValue());
					associateBankDataDTO.setId(bankData.getId());
					resObj.setObject(associateBankDataDTO);
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

	@Override
	public AssociateBankDataDTO findAssociateBankDataByUserId(Long userId, OrgLocations orgLocation)
	{
		AssociateBankDataDTO associateBankDataDTO = new AssociateBankDataDTO();
		try {
			Candidate candidate = candidateService.getCandidateByConsultantUserId(userId, orgLocation.getId());
			AssociateBankData associateBankData =  associateBankDataDao.findAssociateBankDataByCandidateId(candidate.getId(),orgLocation);
			if(associateBankData != null){
//				associateBankDataDTO = new AssociateBankDataDTO();
				associateBankDataDTO.setId(associateBankData.getId());
				associateBankDataDTO.setAccountType(associateBankData.getAccountType());
				associateBankDataDTO.setBankName(associateBankData.getBankName());
				associateBankDataDTO.setRoutingNumber(associateBankData.getRoutingNumber());
				associateBankDataDTO.setAssociateBankDataUUID(associateBankData.getAssociateBankUuid());
				associateBankDataDTO.setAccountNumber(associateBankData.getAccountNumber());
				return associateBankDataDTO;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return associateBankDataDTO;
	}
}
