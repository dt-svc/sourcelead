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
	public void savePetitionDetails(ImmigrationDTO immigrationDTO, User user,OrgLocations orgLocations){
		
		try {
			
		/*get the candidate details*/
		Candidate candidate = candidateDao.getCandidateByCandidateId(Long.parseLong(immigrationDTO.getCandidateId()));
		//candidate.setId(Long.parseLong(immigrationDTO.getCandidateId()));
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
		
		Requirement requirement = new Requirement();
		requirement.setId(Long.parseLong(immigrationDTO.getJobId()));
		
		immigration.setJob(requirement);
		immigration.setStatus(1);
		immigration.setCreatedBy(user);
		immigration.setCreatedDate(new Date());
		immigration.setUpdatedBy(user);
		immigration.setUpdatedDate(new Date());
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
		CandidateIdendities candidateIdendities = new CandidateIdendities();
		candidateIdendities.setCandidateId(candidate);
		candidateIdendities.setIdNumber(immigrationDTO.getSsnNumber());
		candidateIdendities.setIdType(Constants.ID_TYPE_SSN);
		candidateIdendities.setStatus(1);
		candidateIdendities.setCreatedBy(user);
		candidateIdendities.setCreatedDate(new Date());
		candidateIdendities.setUpdatedBy(user);
		candidateIdendities.setUpdatedDate(new Date());
		/*CandidateIdentities ends here */
		
		CandidateResumes candidateResume = new CandidateResumes();
		if(immigrationDTO.getResumeInfo()!= null) {
		FileInfo fileInfo = immigrationDTO.getResumeInfo().get(0);
		candidateResume.setCandidateId(candidate);
		candidateResume.setFileName(fileInfo.getFileName());
		candidateResume.setFileURL(fileInfo.getFileURL());
		candidateResume.setFileContentType(fileInfo.getContentType());
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
		lcaSummaryCurr.setStatus(1);
		lcaSummaryCurr.setIdType(Constants.ID_TYPE_LCA);
		Date date = new Date();
		lcaSummaryCurr.setCreatedDate(commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), date));
		lcaSummaryCurr.setUpdatedDate(commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), date));
		
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
		lcaSummaryPrev.setRequirementId(requirement);
		lcaSummaryPrev.setCandidateId(candidate);
		lcaSummaryPrev.setCreatedBy(user);
		lcaSummaryPrev.setUpdatedBy(user);
		lcaSummaryPrev.setEmploymentLevel(immigrationDTO.getEmploymentLevel());
		lcaSummaryPrev.setEmployerName(immigrationDTO.getEmployer());
		lcaSummaryPrev.setEmployerLocation(immigrationDTO.getJobLocation());
		lcaSummaryPrev.setRateCurrency(immigrationDTO.getRateCurrency());
		lcaSummaryPrev.setRate(immigrationDTO.getRate());
		lcaSummaryPrev.setRateType(immigrationDTO.getRateType());
		lcaSummaryPrev.setStatus(1);
		lcaSummaryPrev.setIdType(Constants.ID_TYPE_LCA);
		lcaSummaryPrev.setCreatedBy(user);
		lcaSummaryPrev.setUpdatedBy(user);
		lcaSummaryPrev.setCreatedDate(new Date());
		lcaSummaryPrev.setUpdatedDate(new Date());
		
		lcaSummaryLst.add(lcaSummaryPrev); // add previous lca
		List<CandidateResidence> residenceList = new ArrayList<CandidateResidence>(); // add residences to immigration candidate tables
			if (immigrationDTO.getResidenceList() != null) { 
				List<ImmigrationResidenceDTO> residenceDtoLst = immigrationDTO.getResidenceList();
				for (ImmigrationResidenceDTO residenceDto : residenceDtoLst) {
					if(StringUtils.isEmpty(residenceDto.getCountry()) ) continue; // skip the empty records if one of the required field value
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
					residenceEntity.setStatus(1);
					residenceEntity.setCreatedDate(new Date());
					residenceEntity.setUpdatedDate(new Date());
					residenceEntity.setCreatedBy(user);
					residenceEntity.setUpdatedBy(user);
					residenceList.add(residenceEntity);
				}
			}
		
		immigrationCommonDao.savePetitionDetails(immigration,candidate,candidateIdendities,candidateResume,lcaSummaryLst, residenceList);
		}catch(Exception e) {
			logger.error("Exception caugth in savePetitionDetails service"+ e.getMessage());
			e.printStackTrace();
		}
	}	

}
