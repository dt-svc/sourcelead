/**
 * 
 */
package com.bis.sourcelead.immigration.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.ImmigrationDTO;
import com.bis.sourcelead.dto.ImmigrationResidenceDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateIdendities;
import com.bis.sourcelead.entity.CandidateLcaI94Summary;
import com.bis.sourcelead.entity.CandidateResidence;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.Immigration;
import com.bis.sourcelead.entity.ImmigrationTemplate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.immigration.dao.ImmigrationCommonDao;
import com.bis.sourcelead.immigration.service.ImmigrationCommonService;
import com.bis.sourcelead.rest.ImmigrationRestController;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

/**
 * @author Srinivas.Sabbu
 *
 */

@Service
public class ImmigrationCommonServiceImpl implements ImmigrationCommonService {
	
	private static final Logger logger = LoggerFactory.getLogger(ImmigrationCommonServiceImpl.class);

	@Autowired
	ImmigrationCommonDao immigrationCommonDao;
	
	@Autowired
	CandidateDao candidateDao;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Override
	public List<ImmigrationTemplate> getImmigrationTemplateList(OrgLocations orgLocations) {
		
		return immigrationCommonDao.getImmigrationTemplateList(orgLocations);
	}
	
	@Override
	public Immigration savePetitionDetails(ImmigrationDTO immigrationDTO, User user,OrgLocations orgLocations){
		Immigration immigrationVal = null;
		try {
			
		/*get the candidate details*/
		Candidate candidate = candidateDao.getCandidateByCandidateId(Long.parseLong(immigrationDTO.getCandidateId()));
		//candidate.setId(Long.parseLong(immigrationDTO.getCandidateId()));
		Date date = new Date();
		Immigration immigration = new Immigration();
		immigration.setPetitionType(Long.parseLong(immigrationDTO.getPetitionType()));
		//DateFormat dateFormat = new SimpleDateFormat ("dd-MM-yyyy");
		immigration.setOpenDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), immigrationDTO.getOpenDate()));
		if(immigrationDTO.getCurrUscisStatus()!=null && !StringUtils.isEmpty(immigrationDTO.getCurrUscisStatus())) {
			immigration.setCurrUscisStauts(Long.parseLong(immigrationDTO.getCurrUscisStatus()));
		}
		if(immigrationDTO.getArrivalDate()!=null && !StringUtils.isEmpty(immigrationDTO.getArrivalDate())) {
			immigration.setDateOfArrival(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),immigrationDTO.getArrivalDate()));
		}
		immigration.setPlaceOfEntry(immigrationDTO.getEntryPlace());
		if(immigrationDTO.getTemplateId()!=null && !StringUtils.isEmpty(immigrationDTO.getTemplateId())) {
			ImmigrationTemplate immiTemplate = new ImmigrationTemplate();
			immiTemplate.setId(Long.parseLong(immigrationDTO.getTemplateId()));
			immigration.setImmigrationTemplateId(immiTemplate);
		}
		Org org = new Org();
		org.setId(Long.parseLong(immigrationDTO.getAttorneyId()));
		immigration.setOrg(org);
		OrgSpoc orgSpoc = new OrgSpoc();
		orgSpoc.setId(Long.parseLong(immigrationDTO.getAttorneyContact()));
		immigration.setOrgSpoc(orgSpoc);
		immigration.setCandidate(candidate);
		immigration.setOrgLocationId(orgLocations);
		
		Requirement requirement = new Requirement();
		if(!StringUtils.isEmpty(immigrationDTO.getJobId())){// job id optional
		requirement.setId(Long.parseLong(immigrationDTO.getJobId()));
		}
		immigration.setReceiptNo(immigrationDTO.getReceiptNo());
		immigration.setJob(requirement);
		immigration.setStatus(Constants.RECORD_ACTIVE);
		immigration.setCreatedBy(user);
		immigration.setCreatedDate(date);
		immigration.setUpdatedBy(user);
		immigration.setUpdatedDate(date);
		/*end Immigration here*/
		candidate.setFirstName(immigrationDTO.getFirstName());
		candidate.setMiddelName(immigrationDTO.getMiddleName());
		candidate.setLastName(immigrationDTO.getLastName());
		candidate.setGender(immigrationDTO.getGender());
		candidate.setEmail(immigrationDTO.getEmail());
		candidate.setMobilePhone(immigrationDTO.getMobile());
		candidate.setAlternateMobilePhone(immigrationDTO.getAlternateMobile());
		candidate.setDateOfBirth(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),immigrationDTO.getBirthDate()));
		candidate.setPlaceOfBirth(immigrationDTO.getBirthPlace());
		candidate.setNationality(immigrationDTO.getNationality());
		candidate.setMaritalStatus(immigrationDTO.getMaritalStatus());
		candidate.setEadNo(immigrationDTO.getEadNumber());
		candidate.setI20ServiceNo(immigrationDTO.getI_20_number());
		candidate.setI94No(immigrationDTO.getI_94_number());
		candidate.setUpdatedBy(user.getId().toString());
		candidate.setUpdatedDate(new Date());
		candidate.setLocation(immigrationDTO.getLocation()); // considered as a primary residence address.
		candidate.setAddress(immigrationDTO.getAddress());
		candidate.setCity(immigrationDTO.getCity());
		candidate.setState(immigrationDTO.getState());
		candidate.setCountry(immigrationDTO.getCountry());
		candidate.setZipCode(immigrationDTO.getZipcode());
		//candidate.setResidenceStartDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),immigrationDTO.getResidenceStartDate()));
		//candidate.setResidenceEndDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),immigrationDTO.getResidenceEndDate()));
		/*End candidate here*/
		CandidateIdendities candidateIdendities = null;
		candidateIdendities = candidateDao.getCandidateIdentitiesNumberByCandidateId(candidate.getId(), Constants.ID_TYPE_SSN);
		if(candidateIdendities == null) {
			candidateIdendities = new CandidateIdendities();
		}
		candidateIdendities.setCandidateId(candidate);
		candidateIdendities.setIdNumber(immigrationDTO.getSsnNumber());
		candidateIdendities.setIdType(Constants.ID_TYPE_SSN);
		candidateIdendities.setStatus(Constants.RECORD_ACTIVE);
		candidateIdendities.setCreatedBy(user);
		candidateIdendities.setCreatedDate(date);
		candidateIdendities.setUpdatedBy(user);
		candidateIdendities.setUpdatedDate(date);
		/*CandidateIdentities ends here */
		
		CandidateResumes candidateResume = new CandidateResumes();
		if(immigrationDTO.getResumeInfo()!= null) {
		FileInfo fileInfo = immigrationDTO.getResumeInfo().get(0);
		candidateResume.setCandidateId(candidate);
		candidateResume.setFileName(fileInfo.getFileName());
		candidateResume.setFileURL(fileInfo.getFileURL());
		candidateResume.setFileContentType(fileInfo.getContentType());
		candidateResume.setOrgLocationId(orgLocations);
		candidateResume.setStatus(Constants.RECORD_ACTIVE);
		candidateResume.setCreatedDate(commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), date));
		candidateResume.setUpdatedDate(commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), date));
		candidateResume.setCreatedBy(user.getUserCode());
		candidateResume.setResumeUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		FileUploadUtil.moveFile(immigrationDTO.getStaticPath(), fileInfo, "resumes");
		}
		/*candidateResume ends here */
		List<CandidateLcaI94Summary> lcaSummaryLst  = new ArrayList<CandidateLcaI94Summary>();
		CandidateLcaI94Summary lcaSummaryCurr  = new CandidateLcaI94Summary();
		lcaSummaryCurr.setIdNumber(immigrationDTO.getLcaNumber());
		if(immigrationDTO.getLcaStartDate()!=null && !StringUtils.isEmpty(immigrationDTO.getLcaStartDate())) {
			lcaSummaryCurr.setStartDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),immigrationDTO.getLcaStartDate()));
		}
		if(immigrationDTO.getLcaEndDate()!=null && !StringUtils.isEmpty(immigrationDTO.getLcaEndDate())) {
			lcaSummaryCurr.setEndDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),immigrationDTO.getLcaEndDate()));
			lcaSummaryCurr.setIsCurrent(Constants.YES);
		}
		
		lcaSummaryCurr.setRequirementId(requirement);
		lcaSummaryCurr.setCandidateId(candidate);
		lcaSummaryCurr.setCreatedBy(user);
		lcaSummaryCurr.setUpdatedBy(user);
		lcaSummaryCurr.setEmploymentLevel(immigrationDTO.getEmploymentLevel());
		lcaSummaryCurr.setEmployerName(immigrationDTO.getEmployer());
		lcaSummaryCurr.setEmployerLocation(immigrationDTO.getJobLocation());
		lcaSummaryCurr.setRateCurrency(immigrationDTO.getRateCurrency());
		lcaSummaryCurr.setRate(immigrationDTO.getRate());
		lcaSummaryCurr.setRateType(immigrationDTO.getRateType());
		lcaSummaryCurr.setStatus(Constants.RECORD_ACTIVE);
		lcaSummaryCurr.setIdType(Constants.ID_TYPE_LCA);
		
		lcaSummaryCurr.setCreatedDate(date);
		lcaSummaryCurr.setUpdatedDate(date);
		
		lcaSummaryLst.add(lcaSummaryCurr); // add current lca
		
		CandidateLcaI94Summary lcaSummaryPrev  = new CandidateLcaI94Summary();
		lcaSummaryPrev.setIdNumber(immigrationDTO.getPrevLcaNumber());
		if(immigrationDTO.getPrevLcaStartDate()!=null && !StringUtils.isEmpty(immigrationDTO.getPrevLcaStartDate())) {
			lcaSummaryPrev.setStartDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),immigrationDTO.getPrevLcaStartDate()));
			
		}
		if(immigrationDTO.getPrevLcaEndDate()!=null && !StringUtils.isEmpty(immigrationDTO.getPrevLcaEndDate())) {
			lcaSummaryPrev.setEndDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),immigrationDTO.getPrevLcaEndDate()));
			lcaSummaryPrev.setIsCurrent(Constants.NO);
		}
		lcaSummaryPrev.setRequirementId(requirement);
		lcaSummaryPrev.setCandidateId(candidate);
		//lcaSummaryPrev.setRequirementId(requirement);
		//lcaSummaryPrev.setCandidateId(candidate);
		lcaSummaryPrev.setCreatedBy(user);
		lcaSummaryPrev.setUpdatedBy(user);
		lcaSummaryPrev.setEmploymentLevel(immigrationDTO.getEmploymentLevel());
		lcaSummaryPrev.setEmployerName(immigrationDTO.getEmployer());
		lcaSummaryPrev.setEmployerLocation(immigrationDTO.getJobLocation());
		lcaSummaryPrev.setRateCurrency(immigrationDTO.getRateCurrency());
		lcaSummaryPrev.setRate(immigrationDTO.getRate());
		lcaSummaryPrev.setRateType(immigrationDTO.getRateType());
		lcaSummaryPrev.setStatus(Constants.RECORD_ACTIVE);
		lcaSummaryPrev.setIdType(Constants.ID_TYPE_LCA);
		lcaSummaryPrev.setCreatedBy(user);
		lcaSummaryPrev.setUpdatedBy(user);
		lcaSummaryPrev.setCreatedDate(date);
		lcaSummaryPrev.setUpdatedDate(date);
		
		lcaSummaryLst.add(lcaSummaryPrev); // add previous lca
		List<CandidateResidence> residenceList = new ArrayList<CandidateResidence>(); // add residences to immigration candidate tables
			if (immigrationDTO.getResidenceList() != null) { 
				List<ImmigrationResidenceDTO> residenceDtoLst = immigrationDTO.getResidenceList();
				for (ImmigrationResidenceDTO residenceDto : residenceDtoLst) {
					if(StringUtils.isEmpty(residenceDto.getLocation()) ) continue; // skip the empty  residence rows based on one of the required field value
					CandidateResidence residenceEntity = new CandidateResidence();
					residenceEntity.setCandidate(candidate);
					residenceEntity.setCity(residenceDto.getCity());
					residenceEntity.setCountry(residenceDto.getCountry());
					residenceEntity.setLocation(residenceDto.getLocation());
					residenceEntity.setStreetAddress(residenceDto.getAddress());
					residenceEntity.setState(residenceDto.getState());
					residenceEntity.setZipcode(residenceDto.getZipcode());
					if(!StringUtils.isEmpty(residenceDto.getResidenceStartDate())){
					residenceEntity.setStartDate((commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
							residenceDto.getResidenceStartDate())));
					}
					if(!StringUtils.isEmpty(residenceDto.getResidenceEndDate())){
					residenceEntity.setEndDate((commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
							residenceDto.getResidenceEndDate())));
					}
					residenceEntity.setIsCurrAddress(Constants.NO); // default No for multiple residences treated as secondary residences.
					residenceEntity.setStatus(Constants.RECORD_ACTIVE);
					residenceEntity.setCreatedDate(date);
					residenceEntity.setUpdatedDate(date);
					residenceEntity.setCreatedBy(user);
					residenceEntity.setUpdatedBy(user);
					residenceList.add(residenceEntity);
				}
			}
		
		 immigrationVal = immigrationCommonDao.savePetitionDetails(immigration,candidate,candidateIdendities,candidateResume,lcaSummaryLst, residenceList);
		}catch(Exception e) {
			logger.error("Exception caugth in savePetitionDetails service"+ e.getMessage());
			e.printStackTrace();
		}
		return immigrationVal;
	}

	@Override
	public ImmigrationDTO getImmigrationViewList(String immigrationId) {
		ImmigrationDTO dto = new ImmigrationDTO();
		try {
		Immigration immigration = immigrationCommonDao.getImmigrationViewList(immigrationId);
		dto.setPetitionType(immigrationCommonDao.getPetitionTypeById(immigration.getPetitionType(), Constants.PETITION_TYPE));
		dto.setOpenDate(immigration.getOpenDate().toString());
		dto.setCurrUscisStatus(immigrationCommonDao.getPetitionTypeById(immigration.getCurrUscisStauts(), Constants.USCIS_STATUS));
		if(immigration.getDateOfArrival() != null) {
			dto.setArrivalDate(immigration.getDateOfArrival().toString()); 
		}
		dto.setEntryPlace(immigration.getPlaceOfEntry());
		if(immigration.getImmigrationTemplateId() != null) {
			dto.setTemplateName(immigration.getImmigrationTemplateId().getName());
		}
		/*petition details end*/
		dto.setAttorneyName(immigration.getOrg().getOrgName());
		/*attorney end*/
		if(immigration.getJob() != null) {
			dto.setJobTitle(immigration.getJob().getJobTitle());
		}
		/*CandidateLcaI94Summary lcaI94Summary = immigrationCommonDao.getCandidateLCADetails(immigration.getJob().getId(), Constants.YES);
		if(lcaI94Summary.getEmploymentLevel() != null) {
			dto.setEmploymentLevelName(immigrationCommonDao.getPetitionTypeById(Long.parseLong(lcaI94Summary.getEmploymentLevel()), Constants.EMPLOYMENT_LEVEL));
		}
		dto.setPrevLcaNumber(lcaI94Summary.getIdNumber());
		dto.setPrevLcaStartDate(lcaI94Summary.getStartDate().toString());
		dto.setPrevLcaEndDate(lcaI94Summary.getEndDate().toString());
		dto.setEmployer(lcaI94Summary.getEmployerName());
		dto.setJobLocation(lcaI94Summary.getEmployerLocation());
		dto.setLcaStartDate(lcaI94Summary.getStartDate().toString());
		dto.setLcaEndDate(lcaI94Summary.getEndDate().toString());*/
		/*jobdetails end*/
		Candidate candidate = candidateDao.getCandidateById(immigration.getCandidate().getId()); 
		dto.setAssociateName(candidate.getFirstName()+" "+candidate.getLastName());
		dto.setFirstName(candidate.getFirstName());
		dto.setLastName(candidate.getLastName());
		dto.setMiddleName(candidate.getMiddelName());
		dto.setEmail(candidate.getEmail());
		dto.setMobile(candidate.getMobilePhone());
		dto.setAlternateMobile(candidate.getAlternateMobilePhone());
		dto.setBirthDate(candidate.getDateOfBirth().toString());
		dto.setBirthPlace(candidate.getPlaceOfBirth());
		dto.setNationality(candidate.getNationality());
		dto.setMaritalStatus(candidate.getMaritalStatus());
		//dto.setSsnNumber(candidateDao.getCandidateIdentitiesNumberByCandidateId(candidate.getId(), Constants.ID_TYPE_SSN).getIdNumber());
		dto.setI_94_number(candidate.getI94No());
		dto.setI_20_number(candidate.getI20ServiceNo());
		dto.setEadNumber(candidate.getEadNo());
		dto.setExperienceYears(candidate.getExperienceYears());
		dto.setExperienceMonths(candidate.getExperienceMonths());
		}catch(Exception e) {
			logger.error("Exception caugth in getImmigrationViewList service"+ e.getMessage());
			e.printStackTrace();
		}
		return dto;
	}	

}
