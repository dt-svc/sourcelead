package com.bis.sourcelead.bgc.service.Impl;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.bgc.dao.BgcCommonDao;
import com.bis.sourcelead.bgc.dao.Impl.BgcCommonDaoImpl;
import com.bis.sourcelead.bgc.dto.BgcCandidateDetailsDTO;
import com.bis.sourcelead.bgc.entity.BgcCandidateDetails;
import com.bis.sourcelead.bgc.service.BgcCommonService;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;


/**
 * @author Srinivas.Sabbu
 *
 */
@Service
public class BgcCommonServiceImpl implements BgcCommonService{
   
	private static final Logger logger = LoggerFactory.getLogger(BgcCommonServiceImpl.class);
	
	
	@Autowired
    BgcCommonDao bgcCommonDao;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Override
	public BgcCandidateDetails saveBgcCandidateDetails(BgcCandidateDetailsDTO bgcCandidateDetailsDto, User user, OrgLocations orgLocations) {
		
	    try {
		BgcCandidateDetails bgcCandidateDetails = new BgcCandidateDetails();
		
		Candidate candidate = candidateService.getCandidateByCandidateId(bgcCandidateDetailsDto.getCandidateId());
		bgcCandidateDetails.setCandidate(candidate);
		bgcCandidateDetails.setComments(bgcCandidateDetailsDto.getComments());
		bgcCandidateDetails.setCreatedBy(user);  
		
		Date currentDate = commonUtil.orgLocationCommonDate(new Date(), orgLocations);
		
		bgcCandidateDetails.setCreatedDate(currentDate);
		bgcCandidateDetails.setCrimeCheckStatus(bgcCandidateDetailsDto.getCrimeCheckStatus());
		bgcCandidateDetails.setCurrentBgcStatus(bgcCandidateDetailsDto.getCurrentBgcStatus());
		bgcCandidateDetails.setEducationCheckStaus(bgcCandidateDetailsDto.getEducationCheckStaus());
		bgcCandidateDetails.setHealthCheckStatus(bgcCandidateDetailsDto.getHealthCheckStatus());
		OrgLocations sourceOrgLocations = orgLocationService.getOrgLocations(bgcCandidateDetailsDto.getSourceOrglocationId());
		bgcCandidateDetails.setSourceOrglocationId(sourceOrgLocations);
		bgcCandidateDetails.setOrgLocationId(orgLocations);
		bgcCandidateDetails.setOthersInfoStatus(bgcCandidateDetailsDto.getOthersInfoStatus());
		bgcCandidateDetails.setPersonalInfoStatus(bgcCandidateDetailsDto.getPersonalInfoStatus());
		bgcCandidateDetails.setPriorEmployersCheckStatus(bgcCandidateDetailsDto.getPriorEmployersCheckStatus());
		bgcCandidateDetails.setStatus(Constants.RECORD_ACTIVE);
		bgcCandidateDetails.setUpdatedBy(user);
		bgcCandidateDetails.setUpdatedDate(currentDate); 
		bgcCandidateDetails.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID()); // need to add random UUID for the BGC Candidate.
		
		return bgcCommonDao.saveBgcCandidateDetails(bgcCandidateDetails);
		
	    }catch(Exception e) {
	      e.printStackTrace();
	      logger.error("Exception caught  in saveBgcCandidateDetails service "+e.getMessage());
	      return null;
	    }
	}


	@Override
	public ResponseList<BgcCandidateDetailsDTO> getBgcCadidateListByOrgLocationId(BgcCandidateDetailsDTO bgcCandidateDetailsDTO,
			String searchValue, OrgLocations orgLocations, Map<String, Long[]> filterVal, String orgTypeVal) {
		return   bgcCommonDao.getBgcCadidateListByOrgLocationId(bgcCandidateDetailsDTO,searchValue,orgLocations,  filterVal,orgTypeVal);
	}


	@Override
	public String deleteBgcCandidateDetailsbyUuiId(String uuid) {
		return  bgcCommonDao.deleteBgcCandidateDetailsbyUuiId(uuid);
	}
	
	@Override
	public BgcCandidateDetailsDTO getBgcCandidateDetailsByBgcUuid(String bgcUuid) throws Exception {
		BgcCandidateDetailsDTO bgcCandidateDetailsDTO = new BgcCandidateDetailsDTO();
		StringBuilder fullName = new StringBuilder();
		BgcCandidateDetails bgcCandidateDetails = bgcCommonDao.getBgcCandidateDetailsByBgcUuid(bgcUuid);
		if(bgcCandidateDetails != null && bgcCandidateDetails.getCandidate() != null){
			BeanUtils.copyProperties(bgcCandidateDetails, bgcCandidateDetailsDTO);
			if(bgcCandidateDetails.getCandidate().getLastName() != null){
				fullName.append(bgcCandidateDetails.getCandidate().getFirstName()).append(" ").append(bgcCandidateDetails.getCandidate().getLastName());
			}else{
				fullName.append(bgcCandidateDetails.getCandidate().getFirstName());
			}
			bgcCandidateDetailsDTO.setFullName(fullName.toString());
			bgcCandidateDetailsDTO.setAadharNumber(bgcCandidateDetails.getCandidate().getAadharNumber());
			bgcCandidateDetailsDTO.setPanNumber(bgcCandidateDetails.getCandidate().getPanNumber());
			bgcCandidateDetailsDTO.setAddress(bgcCandidateDetails.getCandidate().getAddress());
			bgcCandidateDetailsDTO.setEmail(bgcCandidateDetails.getCandidate().getEmail());
		}
		return bgcCandidateDetailsDTO;
	}

}
