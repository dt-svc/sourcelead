package com.bis.sourcelead.bgc.service.Impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateEducationService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgSpocService;
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
	
	@Autowired
	OrgSpocService orgSpocService;
	
	
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
		
		OrgSpoc orgSpoc = orgSpocService.getSpocByClientOrVendorUserId(bgcCandidateDetailsDto.getBgcVendorContact());
		bgcCandidateDetails.setBgcOrgContactId(orgSpoc);
	
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
		bgcCandidateDetails.setGeneratedId(getBgcGeneratedId(orgLocations)); // setting generated id for the BGC candidate for ORG specific.
		
		return bgcCommonDao.saveBgcCandidateDetails(bgcCandidateDetails);
		
	    }catch(Exception e) {
	      e.printStackTrace();
	      logger.error("Exception caught  in saveBgcCandidateDetails service "+e.getMessage());
	      return null;
	    }
	}
	@Override
	public String getBgcGeneratedId(OrgLocations orgLocations) {
		String companyShortCode = null;
		Long number = 1L;
		String immigrationId = null;
		Integer generatedIdcount = 0;
		StringBuilder sbuilder = new StringBuilder();
		sbuilder.append(orgLocations.getGeneratedUserId()).append(Constants.BGC_ENTITY);
		String generatedUsreId = sbuilder.toString();
		for (int i = 0; i < generatedUsreId.length(); i++) {
			char ch = generatedUsreId.charAt(i);
			if (Character.isAlphabetic(ch)) {
				generatedIdcount++;
			}
		}
		generatedIdcount = generatedIdcount + 1;
		Long checkNumber = bgcCommonDao.getBgcIdByOrgLocationId(orgLocations,
				generatedIdcount);
		if (checkNumber == null) {
			immigrationId = number.toString();
		} else {
			Long reqId = checkNumber + 1;
			immigrationId = reqId.toString();
		}
		final DecimalFormat decimalFormat = new DecimalFormat("0");
		String convetredRequirementsId = decimalFormat.format(Long.parseLong(immigrationId));
		StringBuffer sb = new StringBuffer();
		if (orgLocations.getGeneratedUserId() != null && !"".equalsIgnoreCase(orgLocations.getGeneratedUserId())) {
			companyShortCode = orgLocations.getGeneratedUserId();
		}
		return sb.append(companyShortCode).append(Constants.BGC_ENTITY).append(convetredRequirementsId)
				.toString();
	}

	@Override
	public ResponseList<BgcCandidateDetailsDTO> getBgcCadidateListByOrgLocationId(BgcCandidateDetailsDTO bgcCandidateDetailsDTO,
			String searchValue, OrgLocations orgLocations, Map<String, Long[]> filterVal, String orgTypeVal) {
		try {
			orgLocations = orgLocationService.getOrgLocations(orgLocations.getId()); // loading orgLocations for the isBackGroundCheck property.
			return   bgcCommonDao.getBgcCadidateListByOrgLocationId(bgcCandidateDetailsDTO,searchValue,orgLocations,  filterVal,orgTypeVal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public String deleteBgcCandidateDetailsbyUuiId(String uuid) {
		return  bgcCommonDao.deleteBgcCandidateDetailsbyUuiId(uuid);
	}
	
	@Override
	public BgcCandidateDetailsDTO getBgcCandidateDetailsByBgcUuid(String bgcUuid) {
		try{
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
		}
		return bgcCandidateDetailsDTO;
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e+" in getBgcCandidateDetailsByBgcUuid Service");
			return null;
		}
		}


	@Override
	public List<CandidateEducation> getBgcCandidateEducationSummaryByCandidateUuid(String candidateUuid)  {
		try{
		Candidate candidate = candidateService.getCandidateByUUID(candidateUuid);
		List<CandidateEducation> candidateEducationList = new ArrayList<CandidateEducation>();
		if(candidate != null){
			StringBuilder academicYearBuilder = new StringBuilder("");
			SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
			candidateEducationList = bgcCommonDao.getBgcCandidateEducationSummaryListByCandidateId(candidate.getCandidateGlobalId().getId());
			for(int i=0; i<candidateEducationList.size(); i++){
				Date startDate = candidateEducationList.get(i).getStartDate();
				Date endDate = candidateEducationList.get(i).getEndDate();
				academicYearBuilder.append(formatter.format(startDate)).append(" to ").append(formatter.format(endDate));
				candidateEducationList.get(i).setAcademicYear(academicYearBuilder.toString());
				academicYearBuilder.delete(0, academicYearBuilder.length());
			}
		}
		return candidateEducationList;
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e+" in getBgcCandidateEducationSummaryByCandidateUuid Service");
			return null;
		}
	}


	@Override
	public List<CandidateEmployers> getBgcCandidateEmploymentSummary(String dateFormat, String candidateUuid) {
		try{
		Candidate candidate = candidateService.getCandidateByUUID(candidateUuid);
		List<CandidateEmployers> candidateEmploymentList = new ArrayList<CandidateEmployers>();
		if(candidate != null){
			candidateEmploymentList = bgcCommonDao.getBgcCandidateEmploymentSummaryListByCandidateId(candidate.getCandidateGlobalId().getId());
			for(int i=0; i<candidateEmploymentList.size(); i++){
				if(candidateEmploymentList.get(i).getStartDate() == null && candidateEmploymentList.get(i).getEndDate() == null){
					Integer startMonth = Integer.parseInt(candidateEmploymentList.get(i).getStartMonth().split("&")[0]);
					Integer startYear = Integer.parseInt(candidateEmploymentList.get(i).getStartYear());	
					Integer endMonth = Integer.parseInt(candidateEmploymentList.get(i).getEndMonth().split("&")[0]);
					Integer endYear = Integer.parseInt(candidateEmploymentList.get(i).getEndYear());
					StringBuilder startMonthBuilder = new StringBuilder("");
					StringBuilder endMonthBuilder = new StringBuilder("");
					if(startMonth < 10){
						startMonthBuilder.append(Constants.RECORD_INACTIVE).append(startMonth);
					}if(endMonth < 10){
						endMonthBuilder.append(Constants.RECORD_INACTIVE).append(endMonth);
					}
					Date startDate = commonUtil.orgLocationDateFormat("yyyy-MM-dd", startYear+"-"+startMonthBuilder.toString()+"-"+"01");
					Date endDate = commonUtil.orgLocationDateFormat("yyyy-MM-dd", endYear+"-"+endMonthBuilder.toString()+"-"+"01");
					Integer difference = commonUtil.diffBtwTowDates(startDate, endDate);
					Integer period = difference / 365;
					candidateEmploymentList.get(i).setPeriod(period.toString());
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
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e+" in getBgcCandidateEmploymentSummary Service");
			return null;
		}
	}



	@Override
	public BgcCandidateDetails saveEditBgcCandidate(BgcCandidateDetailsDTO bgcCandidateDetailsDTO)  {
		try{
		BgcCandidateDetails bgcCandidateDetails = bgcCommonDao.getBgcCandidateDetailsByBgcUuid(bgcCandidateDetailsDTO.getUuid());
		if(bgcCandidateDetailsDTO != null && bgcCandidateDetails != null){
			bgcCandidateDetails.setEducationCheckStaus(bgcCandidateDetailsDTO.getEducationCheckStaus());
			bgcCandidateDetails.setPriorEmployersCheckStatus(bgcCandidateDetailsDTO.getPriorEmployersCheckStatus());
			bgcCandidateDetails.setPersonalInfoStatus(bgcCandidateDetailsDTO.getPersonalInfoStatus());
			bgcCandidateDetails.setHealthCheckStatus(bgcCandidateDetailsDTO.getHealthCheckStatus());
			bgcCandidateDetails.setCrimeCheckStatus(bgcCandidateDetailsDTO.getCrimeCheckStatus());
			bgcCandidateDetails.setCurrentBgcStatus(bgcCandidateDetailsDTO.getCurrentBgcStatus());
			bgcCandidateDetails.setComments(bgcCandidateDetailsDTO.getComments());
		}
				bgcCommonDao.saveBgcCandidateDetails(bgcCandidateDetails);
		return bgcCandidateDetails;
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e+" in saveEditBgcCandidate Service");
			return null;
		}
	}

}
