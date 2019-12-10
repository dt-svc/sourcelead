package com.bis.sourcelead.bgc.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
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
import com.bis.sourcelead.dto.CandidateEducationDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateEducationService;
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
			bgcCandidateDetailsDTO.setCandidateUUID(bgcCandidateDetails.getCandidate().getCandidateUUID());
			//bgcCandidateDetailsDTO.setCrimeCheckStatus(bgcCandidateDetails.getCrimeCheckStatus());
		}
		return bgcCandidateDetailsDTO;
	}


	@Override
	public List<CandidateEducation> getBgcCandidateEducationSummaryByCandidateUuid(String candidateUuid) throws Exception {
		Candidate candidate = candidateService.getCandidateByUUID(candidateUuid);
		List<CandidateEducation> candidateEducationList = new ArrayList<CandidateEducation>();
		if(candidate != null){
			candidateEducationList = bgcCommonDao.getBgcCandidateEducationSummaryListByCandidateId(candidate.getCandidateGlobalId().getId());
			for(int i=0; i<candidateEducationList.size(); i++){
				Date startDate = candidateEducationList.get(i).getStartDate();
				Date endDate = candidateEducationList.get(i).getEndDate();
				Integer difference = commonUtil.diffBtwTowDates(startDate, endDate);
				Integer academicYear = difference / 365;
				candidateEducationList.get(i).setAcademicYear(academicYear.toString());
			}
		}
		return candidateEducationList;
	}


	@Override
	public List<CandidateEmployers> getBgcCandidateEmploymentSummary(String candidateUuid) throws Exception {
		Candidate candidate = candidateService.getCandidateByUUID(candidateUuid);
		List<CandidateEmployers> candidateEmploymentList = new ArrayList<CandidateEmployers>();
		if(candidate != null){
			candidateEmploymentList = bgcCommonDao.getBgcCandidateEmploymentSummaryListByCandidateId(candidate.getCandidateGlobalId().getId());
			for(int i=0; i<candidateEmploymentList.size(); i++){
				if(candidateEmploymentList.get(i).getStartDate() == null && candidateEmploymentList.get(i).getEndDate() == null){
					String startMonth = candidateEmploymentList.get(i).getStartMonth();
					String startYear = candidateEmploymentList.get(i).getStartYear();	
					String endMonth = candidateEmploymentList.get(i).getEndMonth();
					String endYear = candidateEmploymentList.get(i).getEndYear();
					
				}else{
					Date startDate = candidateEmploymentList.get(i).getStartDate();
					Date endDate = candidateEmploymentList.get(i).getEndDate();
					Integer difference = commonUtil.diffBtwTowDates(startDate, endDate);
					Integer period = difference / 365;
					candidateEmploymentList.get(i).setPeriod(period.toString());	
				}
			}
		}
		return candidateEmploymentList;
	}


	@Override
	public BgcCandidateDetails saveEditBgcCandidate(BgcCandidateDetailsDTO bgcCandidateDetailsDTO) throws Exception {
		BgcCandidateDetails bgcCandidateDetails = bgcCommonDao.getBgcCandidateDetailsByBgcUuid(bgcCandidateDetailsDTO.getUuid());
		if(bgcCandidateDetailsDTO != null && bgcCandidateDetails != null){
			bgcCandidateDetails.setEducationCheckStaus(bgcCandidateDetailsDTO.getEducationCheckStaus());
			bgcCandidateDetails.setPriorEmployersCheckStatus(bgcCandidateDetailsDTO.getPriorEmployersCheckStatus());
			bgcCandidateDetails.setPersonalInfoStatus(bgcCandidateDetailsDTO.getPersonalInfoStatus());
			bgcCandidateDetails.setHealthCheckStatus(bgcCandidateDetailsDTO.getHealthCheckStatus());
			bgcCandidateDetails.setCrimeCheckStatus(bgcCandidateDetailsDTO.getCrimeCheckStatus());
			bgcCandidateDetails.setComments(bgcCandidateDetailsDTO.getComments());
		}
				bgcCommonDao.saveBgcCandidateDetails(bgcCandidateDetails);
		return bgcCandidateDetails;
	}

}
