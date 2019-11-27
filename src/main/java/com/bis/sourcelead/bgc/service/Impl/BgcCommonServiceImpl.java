package com.bis.sourcelead.bgc.service.Impl;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	
	@Override
	public BgcCandidateDetails saveBgcCandidateDetails(BgcCandidateDetailsDTO bgcCandidateDetailsDto, User user, OrgLocations orgLocations) {
		
	    try {
		BgcCandidateDetails bgcCandidateDetails = new BgcCandidateDetails();
		
		Candidate candidate = new Candidate();
		candidate.setId(bgcCandidateDetailsDto.getCandidateId());
		bgcCandidateDetails.setCandidate(candidate);
		bgcCandidateDetails.setComments(bgcCandidateDetailsDto.getComments());
		bgcCandidateDetails.setCreatedBy(user);  
		
		Date currentDate = commonUtil.orgLocationCommonDate(new Date(), orgLocations);
		
		bgcCandidateDetails.setCreatedDate(currentDate);
		bgcCandidateDetails.setCrimeCheckStatus(bgcCandidateDetailsDto.getCrimeCheckStatus());
		bgcCandidateDetails.setCurrentBgcStatus(bgcCandidateDetailsDto.getCurrentBgcStatus());
		bgcCandidateDetails.setEducationCheckStaus(bgcCandidateDetailsDto.getEducationCheckStaus());
		bgcCandidateDetails.setHealthCheckStatus(bgcCandidateDetailsDto.getHealthCheckStatus());
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
	public ResponseList<BgcCandidateDetailsDTO> getBgcCadidateList(BgcCandidateDetailsDTO bgcCandidateDetailsDTO,
			String searchValue, OrgLocations orgLocations, Map<String, Long[]> filterVal, String orgTypeVal) {
		
		return  bgcCommonDao.saveBgcCandidateDetails(bgcCandidateDetailsDTO,searchValue,orgLocations,  filterVal,orgTypeVal);
	}

}
