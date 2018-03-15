/**
 * 
 */
package com.bis.sourcelead.immigration.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dto.CandidateEmployerDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.ImmigrationDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateDocDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateFormDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateListDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.CandidateIdendities;
import com.bis.sourcelead.entity.CandidateLcaI94Summary;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.Immigration;
import com.bis.sourcelead.entity.ImmigrationTemplate;
import com.bis.sourcelead.entity.ImmigrationTemplateDocs;
import com.bis.sourcelead.entity.ImmigrationTemplateForms;
import com.bis.sourcelead.entity.ImmigrationTemplateTasks;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.immigration.dao.ImmigrationTemplateDao;
import com.bis.sourcelead.immigration.service.ImmigrationTemplateService;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

/**
 * @author Srinivas.Sabbu
 *
 */
@Service
public class ImmigrationTemplateServiceImpl implements ImmigrationTemplateService {

	@Autowired
	ImmigrationTemplateDao immigrationTemplateDao;

	@Autowired
	private UserDao userDao;

	@Override
	public Long savePetitionTemplateDetails(ImmigrationTemplateDTO templateDTO, User user, OrgLocations orgLocations,
			String templateId) {
		ImmigrationTemplate immigrationTemplate = new ImmigrationTemplate();
		if (!templateId.equalsIgnoreCase("null") && !templateId.isEmpty()) {
			immigrationTemplate.setId(Long.parseLong(templateId));
		}
		immigrationTemplate.setOrgLocations(orgLocations);
		immigrationTemplate.setName(templateDTO.getTemplateName());
		immigrationTemplate.setDescription(templateDTO.getTemplateDescription());
		immigrationTemplate.setCreatedBy(user);
		immigrationTemplate.setCreatedDate(new Date());
		immigrationTemplate.setUpdatedBy(user);
		immigrationTemplate.setUpdatedDate(new Date());
		immigrationTemplate.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
		immigrationTemplate.setStatus(0);
		return immigrationTemplateDao.savePetitionTemplateDetails(immigrationTemplate, templateId);

	}

	@Override
	public List<ImmigrationTemplateDocDTO> savePetitionTemplateDocDetails(ImmigrationTemplateDTO templateDTO,
			User user) {

		List<ImmigrationTemplateDocDTO> docsList = templateDTO.getImmigrationDocList();
		ImmigrationTemplate immiTemplate = new ImmigrationTemplate();
		immiTemplate.setId(templateDTO.getId());
		if (!StringUtils.isEmpty(templateDTO.getImmigrationDocDeletedListIds())) {
			immigrationTemplateDao.deletePetitionTemplateDocDetails(templateDTO.getImmigrationDocDeletedListIds());
		}
		List<ImmigrationTemplateDocDTO> docsListAfterSave = new ArrayList<ImmigrationTemplateDocDTO>();
		for (ImmigrationTemplateDocDTO tempObj : docsList) {
			if (tempObj.getDocumentType() == null)
				continue;
			ImmigrationTemplateDocs immigrationTemplateDocs = new ImmigrationTemplateDocs();
			immigrationTemplateDocs.setId(tempObj.getId());
			immigrationTemplateDocs.setImmigrationTempId(immiTemplate);
			immigrationTemplateDocs.setCreatedDate(new Date());
			immigrationTemplateDocs.setCreatedBy(user);
			immigrationTemplateDocs.setUpdatedDate(new Date());
			immigrationTemplateDocs.setUpdatedBy(user);
			immigrationTemplateDocs.setDocumentType(tempObj.getDocumentType());
			immigrationTemplateDocs.setDescription(tempObj.getDescription());
			if (tempObj.getIsRequired() != null && "Y".equalsIgnoreCase(tempObj.getIsRequired())) {
				immigrationTemplateDocs.setIsRequired(tempObj.getIsRequired());
			} else {
				immigrationTemplateDocs.setIsRequired(Constants.NO);
			}
			if (tempObj.getMultiple() != null && "Y".equalsIgnoreCase(tempObj.getMultiple())) {
				immigrationTemplateDocs.setIsMultipleRequired(tempObj.getMultiple());
			} else {
				immigrationTemplateDocs.setIsMultipleRequired(Constants.NO);
			}
			immigrationTemplateDocs.setStatus(1);// active
			ImmigrationTemplateDocs docs = immigrationTemplateDao
					.savePetitionTemplateDocDetails(immigrationTemplateDocs);
			tempObj.setId(docs.getId());
			tempObj.setIsRequired(docs.getIsRequired());
			tempObj.setMultiple(docs.getIsMultipleRequired());
			docsListAfterSave.add(tempObj);
		}
		return docsListAfterSave;

	}

	@Override
	public void savePetitionTemplateTaskDetails(ImmigrationTemplateDTO templateDTO, User user) {

		ImmigrationTemplate immiTemplate = new ImmigrationTemplate();
		immiTemplate.setId(templateDTO.getTemplateId());
		ImmigrationTemplateTasks immigrationTemplateTask = new ImmigrationTemplateTasks();
		immigrationTemplateTask.setImmigrationTempId(immiTemplate);
		if (templateDTO.getId() != null) {
			immigrationTemplateTask.setId(templateDTO.getId());
		}
		immigrationTemplateTask.setTaskName(templateDTO.getTaskName());
		if (StringUtils.isNotBlank(templateDTO.getPetitionAssignedTo())) {
			immigrationTemplateTask.setAssignToUsers(templateDTO.getPetitionAssignedTo());
			immigrationTemplateTask.setAssignToCandidate(null);
		} else {
			immigrationTemplateTask.setAssignToCandidate(Constants.YES);
			immigrationTemplateTask.setAssignToUsers(null);
		}
		immigrationTemplateTask.setTaskReminder(templateDTO.getTaskReminder());
		immigrationTemplateTask.setPriority(templateDTO.getPriority());
		immigrationTemplateTask.setDueAfterMonths(templateDTO.getDueAfterMonths());
		immigrationTemplateTask.setDueAfterDays(templateDTO.getDueAfterDays());
		immigrationTemplateTask.setTaskRepeat(templateDTO.getTaskRepeat());
		immigrationTemplateTask.setTaskEmail(templateDTO.getTaskEmail());
		immigrationTemplateTask.setTaskNotification(templateDTO.getTaskNotification());
		immigrationTemplateTask.setDescription(templateDTO.getTaskDescription());
		immigrationTemplateTask.setStatus(Constants.RECORD_ACTIVE);// 1 for active

		immigrationTemplateTask.setCreatedDate(new Date());
		immigrationTemplateTask.setCreatedBy(user);
		immigrationTemplateTask.setUpdatedDate(new Date());
		immigrationTemplateTask.setUpdatedBy(user);

		immigrationTemplateDao.savePetitionTemplateTaskDetails(immigrationTemplateTask);
	}

	@Override
	public ResponseList<ImmigrationTemplateListDTO> getAllTemplateDetails(ImmigrationTemplateListDTO templateListDTO,
			String searchValue, Long orgLocationId) {
		ResponseList<ImmigrationTemplateListDTO> templateList = immigrationTemplateDao
				.getAllTemplateDetails(templateListDTO, searchValue, orgLocationId);
		return templateList;
	}

	@Override
	public List<ImmigrationTemplateFormDTO> savePetitionTemplateFormDetails(ImmigrationTemplateDTO templateDTO,
			User user) {
		List<ImmigrationTemplateFormDTO> formsList = templateDTO.getImmigrationFormList();
		ImmigrationTemplate immiTemplate = new ImmigrationTemplate();
		immiTemplate.setId(templateDTO.getId());

		List<ImmigrationTemplateFormDTO> savedFormList = new ArrayList<ImmigrationTemplateFormDTO>();

		if (!StringUtils.isEmpty(templateDTO.getImmigrationFormDeletedListIds())) {
			immigrationTemplateDao.deletePetitionTemplateFormDetails(templateDTO.getImmigrationFormDeletedListIds());
		}

		for (ImmigrationTemplateFormDTO tempObj : formsList) {
			ImmigrationTemplateForms immigrationTemplateForms = null;
			if (tempObj.getFormName() == null)
				continue;
			/*
			 * if(tempObj.getId() != null){ immigrationTemplateForms.setId(tempObj.getId());
			 * }
			 */
			if (tempObj.getId() != null) {
				immigrationTemplateForms = getImmigrationTemplateFormsById(tempObj.getId());
			} else {
				immigrationTemplateForms = new ImmigrationTemplateForms();
			}
			immigrationTemplateForms.setImmigrationTempId(immiTemplate);
			immigrationTemplateForms.setFormName(tempObj.getFormName());
			immigrationTemplateForms.setDescription(tempObj.getFormDescription());
			if (tempObj.getIsRequired() != null && "Y".equalsIgnoreCase(tempObj.getIsRequired())) {
				immigrationTemplateForms.setIsRequired(tempObj.getIsRequired());
			} else {
				immigrationTemplateForms.setIsRequired(Constants.NO);
			}
			immigrationTemplateForms.setCreatedDate(new Date());
			immigrationTemplateForms.setCreatedBy(user);
			immigrationTemplateForms.setUpdatedDate(new Date());
			immigrationTemplateForms.setUpdatedBy(user);
			immigrationTemplateForms.setStatus(Constants.RECORD_ACTIVE);
			immigrationTemplateForms.setFormUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			if (tempObj.getImmigrationFormFileInfo() != null && !tempObj.getImmigrationFormFileInfo().isEmpty()) {
				FileInfo info = tempObj.getImmigrationFormFileInfo().get(0);

				if (info != null && info.getFileURL() != null) {
					immigrationTemplateForms.setFileName(info.getFileName());
					immigrationTemplateForms.setFileUrl(info.getFileURL());
					immigrationTemplateForms.setFileContentType(info.getContentType());

					try {

						FileUploadUtil.moveFile(templateDTO.getStaticPath(), info, "ImmigrationFormDocs");
					} catch (java.io.IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
			ImmigrationTemplateForms immigrationForms = immigrationTemplateDao
					.savePetitionTemplateFormDetails(immigrationTemplateForms);

			tempObj.setId(immigrationForms.getId());
			tempObj.setFileName(immigrationForms.getFileName());
			tempObj.setFileUrl(immigrationForms.getFileUrl());
			tempObj.setIsRequired(immigrationForms.getIsRequired());
			savedFormList.add(tempObj);
		}

		return savedFormList;
	}

	private ImmigrationTemplateForms getImmigrationTemplateFormsById(Long immigrationId) {
		return immigrationTemplateDao.getImmigrationTemplateFormsById(immigrationId);
	}

	@Override
	public ResponseList<ImmigrationTemplateDTO> getTemplateTaskList(ImmigrationTemplateDTO templateDTO,
			String searchValue) throws Exception {
		ResponseList<ImmigrationTemplateDTO> responseList = immigrationTemplateDao.getTemplateTaskList(templateDTO,
				searchValue);
		return responseList;
	}

	@Override
	public ImmigrationTemplateDTO editImmigrationTaskList(String id) throws Exception {
		ImmigrationTemplateTasks templateTasks = immigrationTemplateDao.editImmigrationTaskList(id);
		ImmigrationTemplateDTO templateDTO = new ImmigrationTemplateDTO();
		if (templateTasks != null) {
			BeanUtils.copyProperties(templateTasks, templateDTO);
			templateDTO.setTemplateId(templateTasks.getImmigrationTempId().getId());
			/*
			 * templateDTO.setId(templateTasks.getId());
			 * templateDTO.setTaskName(templateTasks.getTaskName());
			 * templateDTO.setPriority(templateTasks.getPriority().trim());
			 * templateDTO.setTaskRepeat(templateTasks.getTaskRepeat());
			 * templateDTO.setTaskReminder(templateTasks.getTaskReminder());
			 * templateDTO.setTaskEmail(templateTasks.getTaskEmail());
			 * templateDTO.setDueAfterMonths(templateTasks.getDueAfterMonths());
			 * templateDTO.setDueAfterDays(templateTasks.getDueAfterDays());
			 */
			if (templateTasks.getAssignToUsers() != null) {
				// User user =
				// userDao.getUserById(Long.parseLong(templateTasks.getAssignToUsers()));
				// templateDTO.setPetitionAssignedTo(user.getFullName());
				templateDTO.setPetitionAssignedTo(templateTasks.getAssignToUsers());
			} else if (templateTasks.getAssignToCandidate() != null) {
				templateDTO.setAssignedTo(Constants.CANDIDATE);
			}
			templateDTO.setTaskDescription(templateTasks.getDescription());
		}
		return templateDTO;
	}

	@Override
	public ImmigrationTemplateDTO getPetitionTemplateDetails(Long templateId) {
		return immigrationTemplateDao.getPetitionTemplateDetails(templateId);
	}

	@Override
	public List<ImmigrationTemplateDocDTO> getImmigrationTemplateDocsListById(Long templateId) {
		return immigrationTemplateDao.getImmigrationTemplateDocsListById(templateId);
	}

	@Override
	public List<ImmigrationTemplateFormDTO> getImmigrationTemplateformsListById(Long templateId) {
		return immigrationTemplateDao.getImmigrationTemplateformsListById(templateId);
	}

	@Override
	public void deleteAllPetitionTemplateDetailsById(Long templateId) {
		immigrationTemplateDao.deleteAllPetitionTemplateDetailsById(templateId);
	}

	@Override
	public void deleteImmigrationTaskList(String id) {
		immigrationTemplateDao.deleteImmigrationTaskList(id);

	}

	@Override
	public int savePetitionTemplateDetailsValues(String templateId) {
		return immigrationTemplateDao.savePetitionTemplateDetailsValues(templateId);
	}

	@Override
	public List<CandidateEmployerDTO> getPetitionTemplateEmployers(Long id) {
		List<CandidateEmployers> candidateEmployers = immigrationTemplateDao.getPetitionTemplateEmployers(id);
		List<CandidateEmployerDTO> employerDTOs = new ArrayList<>();
		for(CandidateEmployers source: candidateEmployers) {
			CandidateEmployerDTO target = new CandidateEmployerDTO();
			BeanUtils.copyProperties(source, target);
			employerDTOs.add(target);
		}
		return employerDTOs;
	}

	@Override
	public List<LookUpData> getLookupDataByType(String type) {
		List<LookUpData> lookUpDatas = immigrationTemplateDao.getLookupDataByType(type);
		return lookUpDatas;
	}

	@Override
	public void savePetitionDetails(ImmigrationDTO immigrationDTO, User user) throws Exception {
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
		immigration.setCreatedBy(user);
		immigration.setCreatedDate(new Date());
		immigration.setUpdatedBy(user);
		immigration.setUpdatedDate(new Date());
		
		/*end Immigration here*/
		/*Candidate Starts Here*/
		Candidate candidate = new Candidate();
		candidate.setId(Long.parseLong(immigrationDTO.getCandidateId()));
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
		candidateIdendities.setCreatedBy(user);
		candidateIdendities.setCreatedDate(new Date());
		candidateIdendities.setUpdatedBy(user);
		candidateIdendities.setUpdatedDate(new Date());
		/*CandidateIdentities ends here */
		
		CandidateResumes candidateResume = new CandidateResumes();
		candidateResume.setCandidateId(candidate);
		candidateResume.setFileName(immigrationDTO.getResumeInfo().getFileName());
		candidateResume.setFileURL(immigrationDTO.getResumeInfo().getFileURL());
		candidateResume.setFileContentType(immigrationDTO.getResumeInfo().getContentType());
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
		Requirement requirement = new Requirement();
		requirement.setId(Long.parseLong(immigrationDTO.getJobId()));
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
		lcaSummaryCurr.setCreatedDate(new Date());
		lcaSummaryCurr.setUpdatedDate(new Date());
		
		//lcaSummaryCurr.set
		///lcaSummaryLst.add(lcaSummaryCurr);
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
		lcaSummaryPrev.setCreatedBy(user);
		lcaSummaryPrev.setUpdatedBy(user);
		lcaSummaryCurr.setCreatedDate(new Date());
		lcaSummaryCurr.setUpdatedDate(new Date());
		lcaSummaryLst.add(lcaSummaryPrev);
		immigrationTemplateDao.savePetitionDetails(immigration,candidate,candidateIdendities,candidateResume,lcaSummaryLst);
		
	}	

}
