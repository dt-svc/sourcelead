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
import com.bis.sourcelead.dto.ImmigrationDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateIdendities;
import com.bis.sourcelead.entity.CandidateLcaI94Summary;
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
	
	@Override
	public List<ImmigrationTemplate> getImmigrationTemplateList(OrgLocations orgLocations) {
		
		return immigrationCommonDao.getImmigrationTemplateList(orgLocations);
	}
	
	@Override
	public void savePetitionDetails(ImmigrationDTO immigrationDTO, User user){
		
		try {
		/*get the candidate details*/
		Candidate candidate = candidateDao.getCandidateByCandidateId(Long.parseLong(immigrationDTO.getCandidateId()));
		//candidate.setId(Long.parseLong(immigrationDTO.getCandidateId()));
		Immigration immigration = new Immigration();
		immigration.setPetitionType(Long.parseLong(immigrationDTO.getPetitionType()));
		DateFormat dateFormat = new SimpleDateFormat ("dd-MM-yyyy");
		immigration.setOpenDate(dateFormat.parse(immigrationDTO.getOpenDate()));
		if(immigrationDTO.getCurrUscisStatus()!=null && !StringUtils.isEmpty(immigrationDTO.getCurrUscisStatus())) {
			immigration.setCurrUscisStauts(Long.parseLong(immigrationDTO.getCurrUscisStatus()));
		}
		if(immigrationDTO.getArrivalDate()!=null && !StringUtils.isEmpty(immigrationDTO.getArrivalDate())) {
			immigration.setDateOfArrival(dateFormat.parse(immigrationDTO.getArrivalDate()));
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
		candidate.setDateOfBirth(dateFormat.parse(immigrationDTO.getBirthDate()));
		candidate.setPlaceOfBirth(immigrationDTO.getBirthPlace());
		candidate.setNationality(immigrationDTO.getNationality());
		candidate.setMaritalStatus(immigrationDTO.getMaritalStatus());
		candidate.setEadNo(immigrationDTO.getEadNumber());
		candidate.setI20ServiceNo(immigrationDTO.getI_20_number());
		candidate.setI94No(immigrationDTO.getI_94_number());
		candidate.setUpdatedBy(user.getId().toString());
		candidate.setUpdatedDate(new Date());
		candidate.setLocation(immigrationDTO.getLocation());
		candidate.setAddress(immigrationDTO.getAddress());
		candidate.setCity(immigrationDTO.getCity());
		candidate.setState(immigrationDTO.getState());
		candidate.setCountry(immigrationDTO.getCountry());
		candidate.setZipCode(immigrationDTO.getZipcode());
		candidate.setResidenceStartDate(dateFormat.parse(immigrationDTO.getResidenceStartDate()));
		candidate.setResidenceEndDate(dateFormat.parse(immigrationDTO.getResidenceEndDate()));
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
		candidateResume.setCandidateId(candidate);
		candidateResume.setFileName(immigrationDTO.getResumeInfo().getFileName());
		candidateResume.setFileURL(immigrationDTO.getResumeInfo().getFileURL());
		candidateResume.setFileContentType(immigrationDTO.getResumeInfo().getContentType());
		}
		/*candidateResume ends here */
		List<CandidateLcaI94Summary> lcaSummaryLst  = new ArrayList<CandidateLcaI94Summary>();
		CandidateLcaI94Summary lcaSummaryCurr  = new CandidateLcaI94Summary();
		lcaSummaryCurr.setIdNumber(immigrationDTO.getLcaNumber());
		if(immigrationDTO.getLcaStartDate()!=null && !StringUtils.isEmpty(immigrationDTO.getLcaStartDate())) {
			lcaSummaryCurr.setStartDate(dateFormat.parse(immigrationDTO.getLcaStartDate()));
		}
		if(immigrationDTO.getLcaEndDate()!=null && !StringUtils.isEmpty(immigrationDTO.getLcaEndDate())) {
			lcaSummaryCurr.setEndDate(dateFormat.parse(immigrationDTO.getLcaEndDate()));
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
		lcaSummaryCurr.setCreatedDate(new Date());
		lcaSummaryCurr.setUpdatedDate(new Date());
		
		lcaSummaryLst.add(lcaSummaryCurr); // add current lca
		
		CandidateLcaI94Summary lcaSummaryPrev  = new CandidateLcaI94Summary();
		lcaSummaryPrev.setIdNumber(immigrationDTO.getPrevLcaNumber());
		if(immigrationDTO.getPrevLcaStartDate()!=null && !StringUtils.isEmpty(immigrationDTO.getPrevLcaStartDate())) {
			lcaSummaryPrev.setStartDate(dateFormat.parse(immigrationDTO.getPrevLcaStartDate()));
			
		}
		if(immigrationDTO.getPrevLcaEndDate()!=null && !StringUtils.isEmpty(immigrationDTO.getPrevLcaEndDate())) {
			lcaSummaryPrev.setEndDate(dateFormat.parse(immigrationDTO.getPrevLcaEndDate()));
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
		
		immigrationCommonDao.savePetitionDetails(immigration,candidate,candidateIdendities,candidateResume,lcaSummaryLst);
		}catch(Exception e) {
			logger.error("Exception caugth in savePetitionDetails service"+ e.getMessage());
			e.printStackTrace();
		}
	}	

}
