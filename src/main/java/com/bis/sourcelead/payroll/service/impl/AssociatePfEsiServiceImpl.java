package com.bis.sourcelead.payroll.service.impl;

import java.util.Date;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.payroll.dao.AssociatePfEsiDao;
import com.bis.sourcelead.payroll.dto.AssociatePfEsiDetailsDTO;
import com.bis.sourcelead.payroll.entity.AssociatePfEsiDetails;
import com.bis.sourcelead.payroll.service.AssociatePfEsiService;
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
					associatePfEpf.setUserId(loginUser);
					associatePfEpf.setOrgLocationId(orgLocation);
				}
				associatePfEpf.setEligibleForPf(associatePfEsiDetailsDTO.getEligibleForPf());
				associatePfEpf.setUanNumber(associatePfEsiDetailsDTO.getUanNumber());
				associatePfEpf.setPfNumber(associatePfEsiDetailsDTO.getPfNumber());
				associatePfEpf.setEnroleDate(commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), associatePfEsiDetailsDTO.getEnroleDate()) );
				associatePfEpf.setEligibleForEsi(associatePfEsiDetailsDTO.getEligibleForEsi());
				associatePfEpf.setEsinumber(associatePfEsiDetailsDTO.getEsinumber());
				associatePfEpf.setCreatedBy(loginUser.getUserCode());
				associatePfEpf.setUpdatedBy(loginUser.getUserCode());
				associatePfEpf.setCreatedDate(new Date());
				associatePfEpf.setUpdatedDate(new Date());
				AssociatePfEsiDetails pfEsi = associatePfEsiDao.saveAssociatePfEsi(associatePfEpf);
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
						associatePfEpf.setEnroleDate(null);
						associatePfEpf.setEligibleForEsi(null);
						associatePfEpf.setEsinumber(null);
						associatePfEsiDao.saveAssociatePfEsi(associatePfEpf);
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
	public AssociatePfEsiDetails findAssociatePfEsiByUserId(Long userId, OrgLocations orgLocation) {
		AssociatePfEsiDetails aped= associatePfEsiDao.findAssociatePfEsiByUserId(userId, orgLocation);
		return aped;
	}
	
	 
}
