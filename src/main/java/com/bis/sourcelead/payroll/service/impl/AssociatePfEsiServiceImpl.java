package com.bis.sourcelead.payroll.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.payroll.dao.AssociatePfEsiDao;
import com.bis.sourcelead.payroll.dto.AssociatePfEsiDetailsDTO;
import com.bis.sourcelead.payroll.entity.AssociatePfEsiDetails;
import com.bis.sourcelead.payroll.service.AssociatePfEsiService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class AssociatePfEsiServiceImpl implements AssociatePfEsiService{
	
	private static final Logger logger = LoggerFactory.getLogger(AssociatePfEsiServiceImpl.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	AssociatePfEsiDao associatePfEsiDao;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	CandidateService candidateService;

	@Override
	public Response<AssociatePfEsiDetailsDTO> saveAssociatePfEsi(AssociatePfEsiDetailsDTO associatePfEsiDetailsDTO, OrgLocations orgLocation, User loginUser) {

		AssociatePfEsiDetails associatePfEpf = null;
		Response<AssociatePfEsiDetailsDTO> resObj = new Response<>();
		try {
			if (Validator.validate(associatePfEsiDetailsDTO)) {
				if(associatePfEsiDetailsDTO.getEligibleForPf() != null || associatePfEsiDetailsDTO.getEligibleForEsi() != null) {
				if(associatePfEsiDetailsDTO.getId() != null && associatePfEsiDetailsDTO.getId() != 0){
					associatePfEpf = associatePfEsiDao.findAssociatePfEsiId(associatePfEsiDetailsDTO.getId());
				}else {
					associatePfEpf = new AssociatePfEsiDetails();
					Candidate candidate = null;
					if(associatePfEsiDetailsDTO.getAssociateUserId() != null && !associatePfEsiDetailsDTO.getAssociateUserId().isEmpty()) {
						candidate = candidateService.getCandidateByConsultantUserId(Long.valueOf(associatePfEsiDetailsDTO.getAssociateUserId()), orgLocation.getId());
					}else {
						candidate = candidateService.getCandidateByConsultantUserId(loginUser.getId(), orgLocation.getId());
					}
					
					associatePfEpf.setCandidateId(candidate);
					associatePfEpf.setOrgLocationId(orgLocation);
				}
				
				if(associatePfEsiDetailsDTO.getEligibleForPf() != null && !associatePfEsiDetailsDTO.getEligibleForPf().isEmpty()) {
					associatePfEpf.setEligibleForPf(Constants.YES);
					associatePfEpf.setUanNumber(associatePfEsiDetailsDTO.getUanNumber());
					associatePfEpf.setPfNumber(associatePfEsiDetailsDTO.getPfNumber());
					//associatePfEpf.setAadharNumber(associatePfEsiDetailsDTO.getAadharNumber());
					associatePfEpf.setEnroleDate(commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), associatePfEsiDetailsDTO.getEnroleDate()) );
				}else {
					associatePfEpf.setEligibleForPf(null);
					//associatePfEpf.setAadharNumber(null);
					associatePfEpf.setUanNumber(null);
					associatePfEpf.setPfNumber(null);
					associatePfEpf.setEnroleDate(null);
				}
				
				if(associatePfEsiDetailsDTO.getEligibleForEsi() != null && !associatePfEsiDetailsDTO.getEligibleForEsi().isEmpty()) {
					associatePfEpf.setEligibleForEsi(Constants.YES);
					associatePfEpf.setEsinumber(associatePfEsiDetailsDTO.getEsinumber());
				}else {
					associatePfEpf.setEligibleForEsi(null);
					associatePfEpf.setEsinumber(null);
				}
				
				associatePfEpf.setCreatedBy(loginUser.getUserCode());
				associatePfEpf.setUpdatedBy(loginUser.getUserCode());
				associatePfEpf.setCreatedDate(new Date());
				associatePfEpf.setUpdatedDate(new Date());
				AssociatePfEsiDetails pfEsi = associatePfEsiDao.saveAssociatePfEsi(associatePfEpf);
				associatePfEsiDetailsDTO.setId(pfEsi.getId());
				resObj.setObject(associatePfEsiDetailsDTO);
				if (pfEsi != null) {
					resObj.setResponse(Result.SUCCESS.name());
					resObj.setResponseMessage(Result.SUCCESS.getValue());
				}
				}else {
					if(associatePfEsiDetailsDTO.getId() != null && associatePfEsiDetailsDTO.getId() != 0){
						associatePfEpf = associatePfEsiDao.findAssociatePfEsiId(associatePfEsiDetailsDTO.getId());
						associatePfEpf.setEligibleForPf(null);
						associatePfEpf.setUanNumber(null);
						associatePfEpf.setPfNumber(null);
						//associatePfEpf.setAadharNumber(null);
						associatePfEpf.setEnroleDate(null);
						associatePfEpf.setEligibleForEsi(null);
						associatePfEpf.setEsinumber(null);
						AssociatePfEsiDetails pfEsi = associatePfEsiDao.saveAssociatePfEsi(associatePfEpf);
						associatePfEsiDetailsDTO.setId(pfEsi.getId());
						resObj.setObject(associatePfEsiDetailsDTO);
					}
					resObj.setResponse(Result.SUCCESS.name());
					resObj.setResponseMessage(Result.SUCCESS.getValue());
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
	public AssociatePfEsiDetails findAssociatePfEsiByCandidateId(Long candidateId, OrgLocations orgLocation) {
		AssociatePfEsiDetails aped= associatePfEsiDao.findAssociatePfEsiByCandidateId(candidateId, orgLocation);
		return aped;
	}

	@Override
	public AssociatePfEsiDetailsDTO getAssociatePfEsiDetailsDTOByCandidateId(Long candidateId,
			OrgLocations orgLocation) {
		
		AssociatePfEsiDetails aped= associatePfEsiDao.findAssociatePfEsiByCandidateId(candidateId, orgLocation);
		AssociatePfEsiDetailsDTO efesiDTO = null;
		if(aped != null) {
			efesiDTO = new AssociatePfEsiDetailsDTO();
			efesiDTO.setPfesiId(String.valueOf(aped.getId()));
			efesiDTO.setEligibleForPf(aped.getEligibleForPf());
			efesiDTO.setUanNumber(aped.getUanNumber());
			efesiDTO.setPfNumber(aped.getPfNumber());
			//efesiDTO.setAadharNumber(aped.getAadharNumber());
			if(aped.getEnroleDate() != null) {
				SimpleDateFormat dateFormat = null;
				if(StringUtils.isNotBlank(orgLocation.getDateFormat())) {
					dateFormat = new SimpleDateFormat(orgLocation.getDateFormat());
				}else {
					dateFormat = (SimpleDateFormat) Constants.DF_YMD;
				}
				efesiDTO.setEnroleDate(dateFormat.format(aped.getEnroleDate()));
			}
			efesiDTO.setEligibleForEsi(aped.getEligibleForEsi());
			efesiDTO.setEsinumber(aped.getEsinumber());
			
		}
		return efesiDTO;
	}

	@Override
	public List<AssociatePfEsiDetails> getAllPfEsiDetailsByOrgLocationid(Long id) throws Exception {
		return associatePfEsiDao.getAllPfEsiDetailsByOrgLocationid(id);
	}

}
