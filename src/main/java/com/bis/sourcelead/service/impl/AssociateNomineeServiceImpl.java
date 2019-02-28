package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.AssociateNomineeDao;
import com.bis.sourcelead.dto.AssociateNomineeDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.AssociateNominee;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.AssociateNomineeService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class AssociateNomineeServiceImpl implements AssociateNomineeService {
	
	private static final Logger logger = LoggerFactory.getLogger(AssociateNomineeServiceImpl.class);
	
	@Autowired
	AssociateNomineeDao associateNomineeDao;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Override
	public Response<AssociateNomineeDTO> saveAssociateNomineeDetails(AssociateNomineeDTO associateNomineeDTO,
			OrgLocations orgLocation, Candidate can,User user) {
		
		AssociateNominee associateNominee = null;
		if(associateNomineeDTO.getId() != null) {
			associateNominee = getNomineeById(associateNomineeDTO.getId());
			associateNominee.setUpdatedBy(user.getId());
			associateNominee.setUpdatedDate(new Date());
		}else {
			associateNominee = new AssociateNominee();
			associateNominee.setCreatedBy(user.getId());
			associateNominee.setCreatedDate(new Date());
		}
		Response<AssociateNomineeDTO> assNomineeDTO = new Response<>();
		
		try {
			if (Validator.validate(associateNomineeDTO)) {
				associateNominee.setOrgLocationId(orgLocation.getId());
				associateNominee.setCandidateId(can);
				associateNominee.setName(associateNomineeDTO.getName());
				associateNominee.setDateOfBirth(commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), associateNomineeDTO.getDateOfBirth()));
				associateNominee.setGender(associateNomineeDTO.getGender());
				associateNominee.setBloodGroup(associateNomineeDTO.getBloodGroup());
				associateNominee.setRelation(associateNomineeDTO.getRelation());
				associateNominee.setNominationFor(associateNomineeDTO.getNominationFor());
				associateNominee.setNominationPercentage(associateNomineeDTO.getNominationPercentage());
				associateNominee.setAddress(associateNomineeDTO.getAddress());
				associateNominee.setIllnessGuardianName(associateNomineeDTO.getIllnessGuardianName());
				associateNominee.setIllnessGuardianRelationName(associateNomineeDTO.getIllnessGuardianRelationName());
				associateNominee.setIllnessGuardianAddress(associateNomineeDTO.getIllnessGuardianAddress());
				associateNominee.setIllnessGuardianPhoneNumber(associateNomineeDTO.getIllnessGuardianPhoneNumber());
				associateNominee.setIllnessGuardianEmail(associateNomineeDTO.getIllnessGuardianEmail());
				associateNominee.setMinorGuardianName(associateNomineeDTO.getMinorGuardianName());
				associateNominee.setMinorGuardianRelationName(associateNomineeDTO.getMinorGuardianRelationName());
				associateNominee.setMinorGuardianAddress(associateNomineeDTO.getMinorGuardianAddress());
				associateNominee.setMinorGuardianPhoneNumber(associateNomineeDTO.getMinorGuardianPhoneNumber());
				associateNominee.setMinorGuardianEmail(associateNomineeDTO.getMinorGuardianEmail());
				associateNominee.setStatus(Constants.RECORD_ACTIVE);
				
				AssociateNominee assNominee = associateNomineeDao.saveAssociateNomineeDetails(associateNominee);
				associateNomineeDTO.setId(assNominee.getId());
				assNomineeDTO.setObject(associateNomineeDTO);
				
				if(assNominee != null) {
					assNomineeDTO.setResponse(Result.SUCCESS.name());
					assNomineeDTO.setResponseMessage(Result.SUCCESS.getValue());
				}
			} 
			else {
				assNomineeDTO.setResponse("DataMissing");
				assNomineeDTO.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} catch (Exception e) {
			assNomineeDTO.setResponse(Result.AWKWARD.name());
			assNomineeDTO.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		
		return assNomineeDTO;
	}

	@Override
	public ResponseList<AssociateNomineeDTO> getAssociateNomineeDetails(AssociateNomineeDTO associateNomineeDTO,
			OrgLocations orgLocation, Candidate candidate, User user) {
		
		
		ResponseList<AssociateNomineeDTO> nomineeList = new ResponseList<>();
		
		List<AssociateNomineeDTO> nomineeDTO = new ArrayList<>();
		
		List<AssociateNominee> assNominee = associateNomineeDao.getAssociateNomineeDetails(candidate,orgLocation);
		try {
			if(assNominee != null) {
				for(AssociateNominee associateNominee:assNominee) {
					
					AssociateNomineeDTO dto = new AssociateNomineeDTO(); 
					dto.setName(associateNominee.getName());
					dto.setDateOfBirth(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), associateNominee.getDateOfBirth()));
					dto.setGender(associateNominee.getGender());
					dto.setBloodGroup(associateNominee.getBloodGroup());
					dto.setRelation(associateNominee.getRelation());
					dto.setNominationFor(associateNominee.getNominationFor());
					dto.setNominationPercentage(associateNominee.getNominationPercentage());
					dto.setAddress(associateNominee.getAddress());
					dto.setIllnessGuardianName(associateNominee.getIllnessGuardianName());
					dto.setIllnessGuardianRelationName(associateNominee.getIllnessGuardianRelationName());
					dto.setIllnessGuardianAddress(associateNominee.getIllnessGuardianAddress());
					dto.setIllnessGuardianPhoneNumber(associateNominee.getIllnessGuardianPhoneNumber());
					dto.setIllnessGuardianEmail(associateNominee.getIllnessGuardianEmail());
					dto.setMinorGuardianName(associateNominee.getMinorGuardianName());
					dto.setMinorGuardianRelationName(associateNominee.getMinorGuardianRelationName());
					dto.setMinorGuardianAddress(associateNominee.getMinorGuardianAddress());
					dto.setMinorGuardianPhoneNumber(associateNominee.getMinorGuardianPhoneNumber());
					dto.setMinorGuardianEmail(associateNominee.getMinorGuardianEmail());
					dto.setStatus(associateNominee.getStatus());
					dto.setCandidateId(candidate.getId());
					dto.setOrgLocationId(orgLocation.getId());
					dto.setId(associateNominee.getId());
					
					nomineeDTO.add(dto);
				}
			}
			
			nomineeList.setData(nomineeDTO);
				
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		
		return nomineeList;
	}

	@Override
	public AssociateNominee getNomineeById(Long id) {
		
		return associateNomineeDao.getNomineeById(id);
		
	}
	

}
