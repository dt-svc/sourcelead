/**
 * 
 */
package com.bis.sourcelead.immigration.service.impl;

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
import com.bis.sourcelead.dto.CandidateFamilyDetailsDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.ImmigrationDTO;
import com.bis.sourcelead.dto.ImmigrationPassportVisaDTO;
import com.bis.sourcelead.dto.ImmigrationResidenceDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateDocDTO;
import com.bis.sourcelead.dto.ResidenceDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateFamilyDetails;
import com.bis.sourcelead.entity.CandidateIdendities;
import com.bis.sourcelead.entity.CandidateLcaI94Summary;
import com.bis.sourcelead.entity.CandidatePassportVisa;
import com.bis.sourcelead.entity.CandidateResidence;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.Immigration;
import com.bis.sourcelead.entity.ImmigrationCandidateDocuments;
import com.bis.sourcelead.entity.ImmigrationTemplate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.immigration.dao.ImmigrationCommonDao;
import com.bis.sourcelead.immigration.dao.ImmigrationTemplateDao;
import com.bis.sourcelead.immigration.service.ImmigrationCommonService;
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
	private ImmigrationTemplateDao immigrationTemplateDao;
	
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
			
		Candidate candidate = new Candidate();
		candidate = savePetitionCandidate(immigrationDTO,orgLocations,user);
		
		CandidateIdendities candidateIdendities = new CandidateIdendities();
		candidateIdendities = savePetitionCandidateIdentities(immigrationDTO,candidate,user);
		
		Requirement requirement = new Requirement();
		if(!StringUtils.isEmpty(immigrationDTO.getJobId())){// job id optional
			requirement.setId(Long.parseLong(immigrationDTO.getJobId()));
		}
		
		Immigration immigration = new Immigration();
		immigration = savePetitionImmigration(immigrationDTO,orgLocations,candidate,requirement,user);
		
		CandidateResumes candidateResume = new CandidateResumes();
		candidateResume = savePetitionCandidateResume(immigrationDTO,candidate, user, orgLocations);
		
		List<CandidateLcaI94Summary> lcaSummaryLst  = new ArrayList<CandidateLcaI94Summary>();
		lcaSummaryLst = savePetitionLcaSummaryList(immigrationDTO, candidate, user, requirement, orgLocations);
		
		List<CandidateResidence> residenceList = new ArrayList<CandidateResidence>(); // add residences to immigration candidate tables
		residenceList = savePetitionResidenceList(immigrationDTO,candidate, user, orgLocations);
		
		 immigrationVal = immigrationCommonDao.savePetitionDetails(immigration,candidate,candidateIdendities,candidateResume,lcaSummaryLst, residenceList);
		}catch(Exception e) {
			logger.error("Exception caugth in savePetitionDetails service"+ e.getMessage());
			e.printStackTrace();
		}
		return immigrationVal;
	}

	private List<CandidateResidence> savePetitionResidenceList(ImmigrationDTO immigrationDTO, Candidate candidate,
			User user, OrgLocations orgLocations) {
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
					residenceEntity.setCreatedDate(new Date());
					residenceEntity.setUpdatedDate(new Date());
					residenceEntity.setCreatedBy(user);
					residenceEntity.setUpdatedBy(user);
					residenceList.add(residenceEntity);
				}
			}
		return residenceList;
	}

	private List<CandidateLcaI94Summary> savePetitionLcaSummaryList(ImmigrationDTO immigrationDTO, Candidate candidate,
			User user, Requirement requirement, OrgLocations orgLocations) {
		List<CandidateLcaI94Summary> lcaSummaryLst  = new ArrayList<CandidateLcaI94Summary>();
		CandidateLcaI94Summary lcaSummaryCurr  = null;
		lcaSummaryCurr = immigrationCommonDao.getCandidateLCADetails(Long.parseLong(immigrationDTO.getCandidateId()), Constants.YES);
		if(lcaSummaryCurr == null) {
			lcaSummaryCurr = new CandidateLcaI94Summary();
		}
		lcaSummaryCurr.setIdNumber(immigrationDTO.getLcaNumber());
		if(immigrationDTO.getLcaStartDate()!=null && !StringUtils.isEmpty(immigrationDTO.getLcaStartDate())) {
			lcaSummaryCurr.setStartDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),immigrationDTO.getLcaStartDate()));
		}
		if(immigrationDTO.getLcaEndDate()!=null && !StringUtils.isEmpty(immigrationDTO.getLcaEndDate())) {
			lcaSummaryCurr.setEndDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),immigrationDTO.getLcaEndDate()));
		}
		lcaSummaryCurr.setIsCurrent(Constants.YES);
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
		lcaSummaryCurr.setCreatedDate(new Date());
		lcaSummaryCurr.setUpdatedDate(new Date());
		lcaSummaryLst.add(lcaSummaryCurr); // add current lca
		
		CandidateLcaI94Summary lcaSummaryPrev  = null;
		lcaSummaryPrev = immigrationCommonDao.getCandidateLCADetails(Long.parseLong(immigrationDTO.getCandidateId()), Constants.NO);
		if(lcaSummaryPrev == null) {
			lcaSummaryPrev = new CandidateLcaI94Summary();
		}
		lcaSummaryPrev.setIdNumber(immigrationDTO.getPrevLcaNumber());
		if(immigrationDTO.getPrevLcaStartDate()!=null && !StringUtils.isEmpty(immigrationDTO.getPrevLcaStartDate())) {
			lcaSummaryPrev.setStartDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),immigrationDTO.getPrevLcaStartDate()));
		}
		if(immigrationDTO.getPrevLcaEndDate()!=null && !StringUtils.isEmpty(immigrationDTO.getPrevLcaEndDate())) {
			lcaSummaryPrev.setEndDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),immigrationDTO.getPrevLcaEndDate()));
		}
		lcaSummaryPrev.setIsCurrent(Constants.NO);
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
		lcaSummaryPrev.setStatus(Constants.RECORD_ACTIVE);
		lcaSummaryPrev.setIdType(Constants.ID_TYPE_LCA);
		lcaSummaryPrev.setCreatedBy(user);
		lcaSummaryPrev.setUpdatedBy(user);
		lcaSummaryPrev.setCreatedDate(new Date());
		lcaSummaryPrev.setUpdatedDate(new Date());
		lcaSummaryLst.add(lcaSummaryPrev); 
		return lcaSummaryLst;
	}

	private CandidateResumes savePetitionCandidateResume(ImmigrationDTO immigrationDTO, Candidate candidate,
			User user, OrgLocations orgLocations) {
		try {
		CandidateResumes candidateResume = new CandidateResumes();
		if(immigrationDTO.getResumeInfo()!= null) {
			FileInfo fileInfo = immigrationDTO.getResumeInfo().get(0);
			candidateResume.setCandidateId(candidate);
			candidateResume.setFileName(fileInfo.getFileName());
			candidateResume.setFileURL(fileInfo.getFileURL());
			candidateResume.setFileContentType(fileInfo.getContentType());
			candidateResume.setOrgLocationId(orgLocations);
			candidateResume.setStatus(Constants.RECORD_ACTIVE);
			candidateResume.setCreatedDate(new Date());
			candidateResume.setUpdatedDate(new Date());
			candidateResume.setCreatedBy(user.getUserCode());
			candidateResume.setResumeUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			FileUploadUtil.moveFile(immigrationDTO.getStaticPath(), fileInfo, "resumes");
			}
		return candidateResume;
		}catch(Exception e) {
			logger.error("Exception caugth in savePetitionCandidateResume service"+ e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	private CandidateIdendities savePetitionCandidateIdentities(ImmigrationDTO immigrationDTO, Candidate candidate,
			User user) {
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
		candidateIdendities.setCreatedDate(new Date());
		candidateIdendities.setUpdatedBy(user);
		candidateIdendities.setUpdatedDate(new Date());
		return candidateIdendities;
	}

	private Candidate savePetitionCandidate(ImmigrationDTO immigrationDTO, OrgLocations orgLocations, User user) {
		try {
			Candidate candidate = candidateDao.getCandidateByCandidateId(Long.parseLong(immigrationDTO.getCandidateId()));
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
			candidate.setExperienceMonths(immigrationDTO.getExperienceMonths());
			candidate.setExperienceYears(immigrationDTO.getExperienceYears());
			return candidate;
		}catch(Exception e) {
			logger.error("Exception caugth in savePetitionCandidate service"+ e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	private Immigration savePetitionImmigration(ImmigrationDTO immigrationDTO, OrgLocations orgLocations, Candidate candidate, Requirement requirement, User user) {
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
		OrgRelations orgRelations = new OrgRelations();
		orgRelations.setId(Long.parseLong(immigrationDTO.getAttorneyId()));
		immigration.setOrgRelations(orgRelations);
		OrgSpoc orgSpoc = new OrgSpoc();
		orgSpoc.setId(Long.parseLong(immigrationDTO.getAttorneyContact()));
		immigration.setOrgSpoc(orgSpoc);
		immigration.setCandidate(candidate);
		immigration.setOrgLocationId(orgLocations);
		immigration.setReceiptNo(immigrationDTO.getReceiptNo());
		immigration.setJob(requirement);
		immigration.setStatus(Constants.RECORD_ACTIVE);
		immigration.setCreatedBy(user);
		immigration.setCreatedDate(new Date());
		immigration.setUpdatedBy(user);
		immigration.setUpdatedDate(new Date());
		return immigration;
	}

	@Override
	public ImmigrationDTO getImmigrationViewList(String immigrationId, OrgLocations orgLocations) {
		ImmigrationDTO dto = new ImmigrationDTO();
		try {
		Immigration immigration = immigrationCommonDao.getImmigrationViewList(immigrationId);
		dto.setPetitionType(immigrationCommonDao.getPetitionTypeById(immigration.getPetitionType(), Constants.PETITION_TYPE));
		dto.setOpenDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),immigration.getOpenDate()));
		dto.setCurrUscisStatus(immigrationCommonDao.getPetitionTypeById(immigration.getCurrUscisStauts(), Constants.USCIS_STATUS));
		if(immigration.getDateOfArrival() != null) {
			dto.setArrivalDate(immigration.getDateOfArrival().toString()); 
		}
		dto.setEntryPlace(immigration.getPlaceOfEntry());
		if(immigration.getImmigrationTemplateId() != null) {
			dto.setTemplateName(immigration.getImmigrationTemplateId().getName());
		}
		/*petition details end*/
		dto.setAttorneyName(immigration.getOrgRelations().getResponderLocationId().getOrgLocationName());
		dto.setAttorneyEmail(immigration.getOrgSpoc().getVendorUser().getEmail());
		dto.setAttorneyPhone(immigration.getOrgSpoc().getVendorUser().getMobilePhone());
		dto.setAttorneyFax(""); //no data need to update
		dto.setAttorneyLawFirm(""); //no data need to update
		
		/*attorney end*/
		if(immigration.getJob() != null) {
			dto.setJobTitle(immigration.getJob().getJobTitle());
		}
		CandidateLcaI94Summary lcaI94Summary = immigrationCommonDao.getCandidateLCADetails(immigration.getCandidate().getId(), Constants.YES);
		if(lcaI94Summary.getEmploymentLevel() != null && !StringUtils.isEmpty(lcaI94Summary.getEmploymentLevel())) {
			dto.setEmploymentLevelName(immigrationCommonDao.getPetitionTypeById(Long.parseLong(lcaI94Summary.getEmploymentLevel()), Constants.EMPLOYMENT_LEVEL));
		}
		dto.setPrevLcaNumber(lcaI94Summary.getIdNumber());
		dto.setPrevLcaStartDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),lcaI94Summary.getStartDate()));
		dto.setPrevLcaEndDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),lcaI94Summary.getEndDate()));
		dto.setEmployer(lcaI94Summary.getEmployerName());
		dto.setJobLocation(lcaI94Summary.getEmployerLocation());
		dto.setLcaStartDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),lcaI94Summary.getStartDate()));
		dto.setLcaEndDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),lcaI94Summary.getEndDate()));
		/*jobdetails end*/
		Candidate candidate = candidateDao.getCandidateById(immigration.getCandidate().getId()); 
		dto.setAssociateName(candidate.getFirstName()+" "+candidate.getLastName());
		dto.setFirstName(candidate.getFirstName());
		dto.setLastName(candidate.getLastName());
		dto.setMiddleName(candidate.getMiddelName());
		dto.setEmail(candidate.getEmail());
		dto.setMobile(candidate.getMobilePhone());
		dto.setAlternateMobile(candidate.getAlternateMobilePhone());
		dto.setBirthDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),candidate.getDateOfBirth()));
		dto.setBirthPlace(candidate.getPlaceOfBirth());
		dto.setNationality(candidate.getNationality());
		dto.setMaritalStatus(candidate.getMaritalStatus());
		dto.setSsnNumber(candidateDao.getCandidateIdentitiesNumberByCandidateId(candidate.getId(), Constants.ID_TYPE_SSN).getIdNumber());
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

	@Override
	public String saveCandidateFamilyMemberDetails(CandidateFamilyDetailsDTO familyDetailsDto, OrgLocations orgLocations, User user) throws Exception {
		CandidateFamilyDetails familyDetails = new CandidateFamilyDetails();
		Candidate candidate = candidateDao.getCandidateByCandidateId(familyDetailsDto.getCandidateId());
		candidate.setCandidateId(familyDetailsDto.getCandidateId());
		familyDetails.setCandidate(candidate);
		familyDetails.setFirstName(familyDetailsDto.getFirstName());
		familyDetails.setMiddleName(familyDetailsDto.getMiddleName());
		familyDetails.setLastName(familyDetailsDto.getLastName());
		familyDetails.setPlaceOfBirth(familyDetailsDto.getPlaceOfBirth());
		familyDetails.setRelation(familyDetailsDto.getRelation());
		if(!StringUtils.isEmpty(familyDetailsDto.getDateOfBirth())) {
		familyDetails.setDateOfBirth(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), familyDetailsDto.getDateOfBirth()));
		}
		if(!StringUtils.isEmpty(familyDetailsDto.getMarraigeDate())) {
		familyDetails.setMarraigeDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), familyDetailsDto.getMarraigeDate()));
		}
		if(!StringUtils.isEmpty(familyDetailsDto.getMarriageEndDate())) {
		familyDetails.setMarriageEndDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), familyDetailsDto.getMarriageEndDate()));
		}
		familyDetails.setMarriagePlace(familyDetailsDto.getMarriagePlace());
		familyDetails.setNationality(familyDetailsDto.getNationality());
		familyDetails.setCurrentAddress(familyDetailsDto.getCurrentAddress());
		familyDetails.setStatus(Constants.RECORD_ACTIVE);
		Date currentDate = commonUtil.orgLocationCommonDate(new Date(), orgLocations);
	    if(familyDetailsDto.getId()!= null) {
	    System.out.println(" in edit mode --member id >"+familyDetailsDto.getId());
	    familyDetails.setId(familyDetailsDto.getId());
	    }else {
	    familyDetails.setCreatedDate(currentDate); // created date , created by fields skipped for edit mode 
	    familyDetails.setCreatedBy(user);
	     }
		familyDetails.setCreatedBy(user);
		familyDetails.setUpdatedDate(currentDate);
		familyDetails.setUpdatedBy(user);
		
		return immigrationCommonDao.saveCandidateFamilyMemberDetails(familyDetails);
	}	
	
	@Override
	public CandidatePassportVisa savePassportDetails(ImmigrationPassportVisaDTO immigrationPassportVisaDTO, User user,
			OrgLocations orgLocations) throws Exception {
		
		CandidatePassportVisa candidatePassportVisa = null;
		if(immigrationPassportVisaDTO.getCandidatePassportId() != null){
			candidatePassportVisa = immigrationCommonDao.getCandidatePassportVisaDetailsById(immigrationPassportVisaDTO.getCandidatePassportId());
		}else{
			candidatePassportVisa = new CandidatePassportVisa();
			candidatePassportVisa.setCreatedBy(user);
			candidatePassportVisa.setCreatedDate(commonUtil.currentDate());
		}
		Candidate candidate = candidateDao.getCandidateByCandidateId(Long.parseLong(immigrationPassportVisaDTO.getCandidateId()));
		// SimpleDateFormat simpleDateFormat =
		// orgLocationId.getLocFullFormDateFormatter();
		StringBuffer activitySummary = new StringBuffer();
		if (immigrationPassportVisaDTO.getCandidateId() != null) {

			if (StringUtils.isNotBlank(immigrationPassportVisaDTO.getIdNumber())) {

				candidatePassportVisa.setIdNumber(immigrationPassportVisaDTO.getIdNumber());
			}

			if (StringUtils.isNotBlank(immigrationPassportVisaDTO.getCountry())) {

				candidatePassportVisa.setCountry(immigrationPassportVisaDTO.getCountry());
			}

			if (StringUtils.isNotBlank(immigrationPassportVisaDTO.getValidFrom())) {
				try {

					//Date validFrom = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),immigrationPassportVisaDTO.getValidFrom());
					candidatePassportVisa.setValidFromDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),immigrationPassportVisaDTO.getValidFrom()));

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				candidatePassportVisa.setValidFromDate(null);
			}
			if (StringUtils.isNotBlank(immigrationPassportVisaDTO.getValidTo())) {
				try {

					candidatePassportVisa.setValidToDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),immigrationPassportVisaDTO.getValidTo()));

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				candidatePassportVisa.setValidToDate(null);
			}

		}
		if(immigrationPassportVisaDTO.getIdType()=="Visa")
		{
			candidatePassportVisa.setVisaType(immigrationPassportVisaDTO.getVisaType());
                        candidatePassportVisa.setIdType(Constants.ID_TYPE_VISA);
		}
                else
                {candidatePassportVisa.setIdType(Constants.ID_TYPE_PASSPORT);

                 }
		
		candidatePassportVisa.setCandidate(candidate);
		candidatePassportVisa.setStatus(Constants.CANDIDATE_ACTIVE);
		candidatePassportVisa.setUpdatedDate(commonUtil.currentDate());
		candidatePassportVisa.setUpdatedBy(user);
		
		
		logger.debug("In CandidateCertificationsServiceImpl class save method");
		candidatePassportVisa = immigrationCommonDao.savePassportDetails(candidatePassportVisa);
		return candidatePassportVisa;

	}


	
	@Override
		public CandidateResidence saveResidenceDetails(ResidenceDTO residenceDTO, OrgLocations orgLocations, User user, Long candidateId)	throws Exception {
			CandidateResidence candidateResidence = null;
			Date date = new Date();
			if(residenceDTO.getResidenceId() != null){
				candidateResidence = immigrationCommonDao.getCandidateResidenceDetailsById(residenceDTO.getResidenceId());
			}else{
				candidateResidence = new CandidateResidence();
				candidateResidence.setCreatedDate(commonUtil.orgLocationCommonDate(date,orgLocations));
				candidateResidence.setCreatedBy(user);

			}
		Candidate candidate = candidateDao.getCandidateByCandidateId(candidateId);
		candidateResidence.setCandidate(candidate);
		candidateResidence.setLocation(residenceDTO.getLocation());
		candidateResidence.setStreetAddress(residenceDTO.getStreetAddress());
		candidateResidence.setCity(residenceDTO.getCity());
		candidateResidence.setState(residenceDTO.getState());
		candidateResidence.setCountry(residenceDTO.getCountry());
		candidateResidence.setZipcode(residenceDTO.getZipcode());
		if(!StringUtils.isEmpty(residenceDTO.getResidenceStartDate())){
			candidateResidence.setStartDate((commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
					residenceDTO.getResidenceStartDate())));
			}
			if(!StringUtils.isEmpty(residenceDTO.getResidenceEndDate())){
				candidateResidence.setEndDate((commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
					residenceDTO.getResidenceEndDate())));
			}
			candidateResidence.setStatus(Constants.RECORD_ACTIVE);
			candidateResidence.setUpdatedDate(commonUtil.orgLocationCommonDate(date,orgLocations));
			candidateResidence.setUpdatedBy(user);
		
		immigrationCommonDao.saveResidenceDetails(candidateResidence);
		return candidateResidence;
	}

	@Override
	public List<CandidateFamilyDetails> getCandidateFamilyDetails(Long candidateId) {
		return immigrationCommonDao.getCandidateFamilyDetails(candidateId);
	}

	@Override
	public String deletetCandidateFamilyDetails(Long candidateId) {
		return immigrationCommonDao.deletetCandidateFamilyDetails(candidateId);
	}	
	
	@Override
 public List<CandidateResidence> getCandidatesResidenceListByCandidateIdAndOrgLocationID(Long candidateId) {
 return immigrationCommonDao.getCandidatesResidenceListByCandidateIdAndOrgLocationID(candidateId);
 }	
	@Override
	public List<CandidatePassportVisa> getCandidatePassportVisaDetails(Long candidateId,String idType) {
		// TODO Auto-generated method stub
		return immigrationCommonDao.getCandidatePassportVisaDetails(candidateId,idType);
	}	
	@Override
	public CandidateFamilyDetails getCandidateFamilyDetailsById(Long id) {
		// TODO Auto-generated method stub
		return immigrationCommonDao.getCandidateFamilyDetailsById(id);
	}

	@Override
	public void removeCandidatePassportVisaDetails(Long candidateId, Long id, User user, OrgLocations orgLocations, String idType)
	{
		 immigrationCommonDao.removeCandidatePassportVisaDetails(candidateId, id, user, orgLocations,idType);

	}

	@Override
	public String deleteCandidateResidenceDetails(Long id,Long candidateId) {
		
		return immigrationCommonDao.deleteCandidateResidenceDetails(id,candidateId);
	}

	@Override
	public CandidateResidence getCandidateResidenceDetailsById(Long id) {
		// TODO Auto-generated method stub
		return  immigrationCommonDao.getCandidateResidenceDetailsById(id);
	}
	
	@Override
	public CandidatePassportVisa getCandidatePassportVisaDetailsById(Long id) {
		// TODO Auto-generated method stub
		return immigrationCommonDao.getCandidatePassportVisaDetailsById(id);
	}

	@Override
	public ImmigrationCandidateDocuments saveImmigrationCandidateDocs(ImmigrationTemplateDocDTO immigrationTemplateDocDTO, User user, OrgLocations orgLocations)
			throws Exception {
		ImmigrationCandidateDocuments immigrationCandidateDocuments = new ImmigrationCandidateDocuments();
		Date date = new Date();
		Candidate candidate = candidateDao.getCandidateByCandidateId(Long.parseLong(immigrationTemplateDocDTO.getImmigrationcandidateId()));
		ImmigrationTemplate immigrationTemplate = immigrationTemplateDao.getImmigrationTemplateById(Long.parseLong(immigrationTemplateDocDTO.getImmigrationTempplateId()));
		Immigration immigration = immigrationCommonDao.getImmigrationById(Long.parseLong(immigrationTemplateDocDTO.getImmigrationId()));
		immigrationCandidateDocuments.setCandidateId(candidate);
		immigrationCandidateDocuments.setImmigrationTemplateId(immigrationTemplate);
		immigrationCandidateDocuments.setImmigrationId(immigration);
		immigrationCandidateDocuments.setDocumentType(immigrationTemplateDocDTO.getDocumentType());
		immigrationCandidateDocuments.setDocUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		immigrationCandidateDocuments.setDescription(immigrationTemplateDocDTO.getDescription());
		immigrationCandidateDocuments.setIsRequired(immigrationTemplateDocDTO.getIsRequired());
		immigrationCandidateDocuments.setCreatedBy(user);
		immigrationCandidateDocuments.setUpdatedBy(user);
		immigrationCandidateDocuments.setUpdatedDate(date);
		immigrationCandidateDocuments.setCreatedDate(date);
		
		return immigrationCommonDao.saveImmigrationCandidateDocs(immigrationCandidateDocuments);
	}
	
}
