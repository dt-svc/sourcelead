/**
 * 
 */
package com.bis.sourcelead.immigration.service.impl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.constants.ImmigrationConstants;
import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dao.CandidateGlobalDao;
import com.bis.sourcelead.dao.CandidateResumesDao;
import com.bis.sourcelead.dao.OrgRelationsDao;
import com.bis.sourcelead.dao.OrgSpocDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dto.CandidateFamilyDetailsDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.ImmigrationDTO;
import com.bis.sourcelead.dto.ImmigrationListViewDTO;
import com.bis.sourcelead.dto.ImmigrationPassportDTO;
import com.bis.sourcelead.dto.ImmigrationResidenceDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateDocDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateFormDTO;
import com.bis.sourcelead.dto.ImmigrationVisaDTO;
import com.bis.sourcelead.dto.LcaDTO;
import com.bis.sourcelead.dto.LcaDetailsDTO;
import com.bis.sourcelead.dto.LcaDocumentHistoryDTO;
import com.bis.sourcelead.dto.LcaListViewDTO;
import com.bis.sourcelead.dto.LcaPlaceOfEmploymentDTO;
import com.bis.sourcelead.dto.LcaReportViewDTO;
import com.bis.sourcelead.dto.MyImmigrationViewDTO;
import com.bis.sourcelead.dto.ResidenceDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateFamilyDetails;
import com.bis.sourcelead.entity.CandidateGlobal;
import com.bis.sourcelead.entity.CandidateIdendities;
import com.bis.sourcelead.entity.CandidateLcaJobDetails;
import com.bis.sourcelead.entity.CandidatePassport;
import com.bis.sourcelead.entity.CandidateResidence;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.CandidateVisa;
import com.bis.sourcelead.entity.CandidateVisaHistory;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Immigration;
import com.bis.sourcelead.entity.ImmigrationActivityHistory;
import com.bis.sourcelead.entity.ImmigrationCandidateDocuments;
import com.bis.sourcelead.entity.ImmigrationCandidateDocumentsHistory;
import com.bis.sourcelead.entity.ImmigrationCandidateForms;
import com.bis.sourcelead.entity.ImmigrationDashboardView;
import com.bis.sourcelead.entity.ImmigrationListExportView;
import com.bis.sourcelead.entity.ImmigrationTasks;
import com.bis.sourcelead.entity.ImmigrationTemplate;
import com.bis.sourcelead.entity.ImmigrationTemplateDocs;
import com.bis.sourcelead.entity.ImmigrationTemplateForms;
import com.bis.sourcelead.entity.ImmigrationTemplateTasks;
import com.bis.sourcelead.entity.Lca;
import com.bis.sourcelead.entity.LcaDocumentHistory;
import com.bis.sourcelead.entity.LcaHistory;
import com.bis.sourcelead.entity.LcaListExportView;
import com.bis.sourcelead.entity.LcaPlaceOfEployment;
import com.bis.sourcelead.entity.LcaVisaClarifications;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.immigration.dao.ImmigrationCommonDao;
import com.bis.sourcelead.immigration.dao.ImmigrationTemplateDao;
import com.bis.sourcelead.immigration.service.ImmigrationCommonService;
import com.bis.sourcelead.payroll.dto.ImmigrationReportViewDTO;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.util.CandidateActivityCommonUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.ExcelHelperUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.ImmigrationActivityCommonUtil;
//import com.bis.sourcelead.util.LcaActivityCommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

import cucumber.deps.difflib.StringUtills;

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

	@Autowired
	private ImmigrationActivityCommonUtil immigrationActivityCommonUtil;

	@Autowired
	private OrgRelationsDao orgRelationsDao;

	@Autowired
	private LookUpDataService lookUpDataService;

	@Autowired
	private OrgSpocDao orgSpocDao;

	@Autowired
	private CandidateResumesDao candidateResumesDao;
	
	@Autowired
	CandidateActivityCommonUtil candidateActivityCommonUtil;

	@Autowired
	private UserDao userDao;

	@Value("${static.path}")
	private String staticPath;

	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;

	@Autowired
	EmailScenerioUtil emailScenerioUtil;

	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Value("${vmsUrl}")
	private String vmsUrl;
	
	@Autowired
	CandidateGlobalDao candidateGlobalDao;
	
/*	@Autowired
	LcaActivityCommonUtil lcaActivityCommonUtil;*/

	@Override
	public List<ImmigrationTemplate> getImmigrationTemplateList(OrgLocations orgLocations, Long status) {

		return immigrationCommonDao.getImmigrationTemplateList(orgLocations, status);
	}

	@Override
	public Immigration savePetitionDetails(ImmigrationDTO immigrationDTO, User user, OrgLocations orgLocations,
			String[] ids) {
		Immigration immigrationObj = null;
		try {
			CandidateLcaJobDetails candLcaJobDetails = new CandidateLcaJobDetails();
			Candidate candidate = new Candidate();
			candidate = savePetitionCandidate(immigrationDTO, orgLocations, user);
			Long globalCandiateId = candidateDao.getGlobalCandidateIdByCandidateId(candidate.getId());
			CandidateGlobal candidateGlobal = candidateGlobalDao.getCandidateGlobalByCandidateId(globalCandiateId);
			CandidateIdendities candidateIdendities = new CandidateIdendities();
			candidateIdendities = savePetitionCandidateIdentities(immigrationDTO, candidate, user, orgLocations);

			Requirement requirement = new Requirement();
			if (!StringUtils.isEmpty(immigrationDTO.getJobId())) {// job id optional
				requirement.setId(Long.parseLong(immigrationDTO.getJobId()));
			}

			
			Immigration immigration = new Immigration();
			immigration = savePetitionImmigration(immigrationDTO, orgLocations, candidate, requirement, user);

			CandidateResumes candidateResume = new CandidateResumes();
			candidateResume = savePetitionCandidateResume(immigrationDTO, candidateGlobal, user, orgLocations);

		//	old Code
			/*List<CandidateLcaI94Summary> lcaSummaryLst = new ArrayList<CandidateLcaI94Summary>();
			lcaSummaryLst = savePetitionLcaSummaryList(immigrationDTO, candidate, user, requirement, orgLocations);*/
			
			if (ids != null && ids.length != 0) {
				for (String id : ids) {
					if(!id.equals("")) {
						immigrationCommonDao.deleteCandidateResidenceDetails(Long.parseLong(id), Long.parseLong("0"));
					}
				}
			}

			List<CandidateResidence> residenceList = new ArrayList<CandidateResidence>(); // add residences to
																							// immigration candidate
																							// tables
			residenceList = savePetitionResidenceList(immigrationDTO, candidate, user, orgLocations);

			immigrationObj = immigrationCommonDao.savePetitionDetails(immigration, candidate, candidateIdendities,
					candidateResume, candLcaJobDetails, residenceList);
			// Copy IMMIGRATION_TEMPLATE_DOCUMENTS data to IMMIGRATION_CANDIDATE_DOCS table
			// based on templateId
			
			// Save CANDIDATELCAJOBDETAILS New Code
			
			candLcaJobDetails = saveCandidateLcaJobDetails(immigrationObj,immigrationDTO, candidate, user, requirement, orgLocations);
			
			//Save LCAHISTORY with lcaId,candidateId,immigrationId
			
			if(StringUtils.isNotBlank(immigrationDTO.getLcaId())) {
				Lca lca = new Lca();
				lca.setId(Long.parseLong(immigrationDTO.getLcaId()));
				if(StringUtils.isNotBlank(immigrationDTO.getLcaNumber())) {
					saveLcaHistoryDetails(immigrationObj,candidate,lca,user,orgLocations,true);
				}
				 
				// This block of code for no of positions available for LCA Number once LCA Assigned To candidate
				    Date currentDate = commonUtil.orgLocationCommonDate(new Date(), orgLocations);
				    Lca updateLca=	immigrationCommonDao.getLcaByLcaId(Long.parseLong(immigrationDTO.getLcaId()));
				    updateLca.setAvailablePositions((updateLca.getNoOfPositions()-updateLca.getAvailablePositions())-1);
				  /*  updateLca.setAvailablePositions(updateLca.getAvailablePositions()-1);*/
				    updateLca.setUpdatedDate(currentDate);
					immigrationCommonDao.saveLcaDetails(updateLca, null, null);
				
				
			}
			if(StringUtils.isNotBlank(immigrationDTO.getPrevLcaId())) {
				Lca prevLca = new Lca();
				prevLca.setId(Long.parseLong(immigrationDTO.getPrevLcaId()));
				if(StringUtils.isNotBlank(immigrationDTO.getPrevLcaNumber())) {
					saveLcaHistoryDetails(immigrationObj,candidate,prevLca,user,orgLocations, false);
					
				}
				
			}
			
			
			
			StringBuilder actionDesc = new StringBuilder();
			if (StringUtils.isNotBlank(immigrationDTO.getImmigrationId())) {
				actionDesc.append("Petition Details Edited").toString();
			} else {
				actionDesc.append("New Petition Added").toString();
			}
			immigrationActivityCommonUtil.saveImmigrationActivities(user, orgLocations, immigration, null,
					actionDesc.toString());

			// Copy IMMIGRATION_TEMPLATE_FORMS data to IMMIGRATION_CANDIDATE_FORMS table
			// based on templateId

			if (StringUtils.isEmpty(immigrationDTO.getImmigrationId())) {
				copyImmigrationTemplateTaskToImmigrationTaskAndTask(immigrationObj, user, orgLocations);
				copyImmigrationConfiguredFormsToCandidate(immigrationObj, user, orgLocations);
				copyImmigrationConfiguredDocsToCandidate(immigrationObj, user, orgLocations);
			}

		} catch (Exception e) {
			logger.error("Exception caugth in savePetitionDetails service" + e.getMessage());
			e.printStackTrace();
		}

		return immigrationObj;
	}
   
	private LcaHistory saveLcaHistoryDetails(Immigration immigrationObj, Candidate candidate, Lca lca,User user,OrgLocations orgLocations , boolean isCurrent) {
		
		LcaHistory lcaHistory = null;
		try {
			  if(isCurrent) {
				  
				    LcaHistory currLcaHistory = immigrationCommonDao.getLcaHistoryByStatus(immigrationObj.getId(),candidate.getId(),"Y");
				    Date currentDate = commonUtil.orgLocationCommonDate(new Date(), orgLocations);
				    if(currLcaHistory == null) {
				    	currLcaHistory =new LcaHistory();
				    	currLcaHistory.setCreatedDate(currentDate);
				    	currLcaHistory.setCreatedBy(user);
				    }
					currLcaHistory.setImmigrationId(immigrationObj);
					currLcaHistory.setCandidate(candidate);
					currLcaHistory.setLcaId(lca);
					currLcaHistory.setIsCurrent(Constants.YES);
					currLcaHistory.setStatus(Constants.RECORD_ACTIVE);
					
					currLcaHistory.setUpdatedDate(currentDate);
					currLcaHistory.setUpdatedBy(user);
					
					lcaHistory = immigrationCommonDao.saveLcaHistory(currLcaHistory);
					
			  }else if(!isCurrent) {
				 
				  LcaHistory prevLaHistory = immigrationCommonDao.getLcaHistoryByStatus(immigrationObj.getId(),candidate.getId(),"N");
				  Date preCurrentDate = commonUtil.orgLocationCommonDate(new Date(), orgLocations);
				    if(prevLaHistory == null) {
				    	prevLaHistory = new LcaHistory();
				    	prevLaHistory.setCreatedDate(preCurrentDate);
				    	prevLaHistory.setCreatedBy(user);
				    }
				    prevLaHistory.setImmigrationId(immigrationObj);
				    prevLaHistory.setCandidate(candidate);
				    prevLaHistory.setLcaId(lca);
					prevLaHistory.setIsCurrent(Constants.NO);
					prevLaHistory.setStatus(Constants.RECORD_ACTIVE);
					
					prevLaHistory.setUpdatedDate(preCurrentDate);
					prevLaHistory.setUpdatedBy(user);
					
					lcaHistory= immigrationCommonDao.saveLcaHistory(prevLaHistory);
			  }	
			
		} catch (Exception e) {
			logger.error("Exception caugth in LcaHistory service" + e.getMessage());
			e.printStackTrace();
		}
		return lcaHistory;
	}
	
	private void copyImmigrationTemplateTaskToImmigrationTaskAndTask(Immigration immigrationObj, User user,
			OrgLocations orgLocations) throws NumberFormatException, Exception {
		List<ImmigrationTasks> immigrationTasksList = new ArrayList<>();
		List<Tasks> tasksList = new ArrayList<>();
		List<ImmigrationTemplateTasks> templateTasksList = immigrationTemplateDao
				.getImmigratonTemplateTasksByTemplateId(immigrationObj.getImmigrationTemplateId().getId());
		for (ImmigrationTemplateTasks templateTasks : templateTasksList) {
			ImmigrationTasks immigrationTasks = new ImmigrationTasks();
			BeanUtils.copyProperties(templateTasks, immigrationTasks);
			immigrationTasks.setImmigration(immigrationObj);
			immigrationTasksList.add(immigrationTasks);
			String[] assignedUsers = null;
			if (templateTasks.getAssignToCandidate() != null
					&& templateTasks.getAssignToCandidate().equalsIgnoreCase("Y")) {
				Tasks tasks = new Tasks();
				tasks.setOwnerOrglocationId(orgLocations);
				tasks.setImmigrationId(immigrationObj);
				tasks.setAssignedOrglocationId(orgLocations);
				tasks.setTaskOwnerId(user);
				tasks.setAssignedUserId(immigrationObj.getCandidate().getConsultantUser());
				tasks.setPriority(templateTasks.getPriority());
				tasks.setTaskDate(templateTasks.getDueAfterDate());
				SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
				if( templateTasks.getDueAfterDate()!= null) {
				  String time = localDateFormat.format(templateTasks.getDueAfterDate());
				tasks.setTaskTime(time);
				}
				tasks.setSubject(templateTasks.getTaskName());
				tasks.setAlertType(Constants.ENTITY_TYPE_EMAIL + "," + Constants.ENTITY_TYPE_NOTIFICATION);
				tasks.setTaskRepeat(templateTasks.getTaskRepeat());
				tasks.setDescription(templateTasks.getDescription());
				tasks.setTaskStatus(Constants.TASK_STATUS_OPEN);
				tasks.setStatus(Constants.RECORD_ACTIVE);
				Date currentDate = commonUtil.orgLocationCommonDate(new Date(), orgLocations);
				tasks.setCreatedDate(currentDate);
				tasks.setCreatedBy(user.getUserUUID());
				tasks.setUpdatedDate(currentDate);
				tasks.setUpdatedBy(user.getUserUUID());
				tasksList.add(tasks);
			} else if (!StringUtils.isBlank(templateTasks.getAssignToUsers())) {
				assignedUsers = templateTasks.getAssignToUsers().split(",");
				for (String users : assignedUsers) {
					Tasks tasks = new Tasks();
					tasks.setImmigrationId(immigrationObj);
					tasks.setOwnerOrglocationId(orgLocations);
					tasks.setAssignedOrglocationId(orgLocations);
					tasks.setTaskOwnerId(user);
					User AssignedUserObj = userDao.getUserById(Long.parseLong(users));
					tasks.setAssignedUserId(AssignedUserObj);
					tasks.setPriority(templateTasks.getPriority());
					tasks.setTaskDate(templateTasks.getDueAfterDate());
					SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
					String time = localDateFormat.format(templateTasks.getDueAfterDate());
					tasks.setTaskTime(time);
					tasks.setSubject(templateTasks.getTaskName());
					tasks.setAlertType(Constants.ENTITY_TYPE_EMAIL + "," + Constants.ENTITY_TYPE_NOTIFICATION);
					tasks.setTaskRepeat(templateTasks.getTaskRepeat());
					tasks.setDescription(templateTasks.getDescription());
					tasks.setTaskStatus(Constants.TASK_STATUS_OPEN);
					tasks.setStatus(Constants.RECORD_ACTIVE);
					Date currentDate = commonUtil.orgLocationCommonDate(new Date(), orgLocations);
					tasks.setCreatedDate(currentDate);
					tasks.setCreatedBy(user.getUserUUID());
					tasks.setUpdatedDate(currentDate);
					tasks.setUpdatedBy(user.getUserUUID());
					tasksList.add(tasks);
				}
			}

		}
		immigrationCommonDao.saveImmigrationTasksAndTasks(immigrationTasksList, tasksList);
	}

	void copyImmigrationConfiguredDocsToCandidate(Immigration immigrationObj, User user, OrgLocations orgLocations) {
		List<ImmigrationTemplateDocs> immigrationTemplateDocsDocList = immigrationTemplateDao
				.getImmigrationTemplateDocsList(immigrationObj.getImmigrationTemplateId().getId());
		ImmigrationCandidateDocuments immCanObj = null;

		for (ImmigrationTemplateDocs docs : immigrationTemplateDocsDocList) {
			immCanObj = new ImmigrationCandidateDocuments();
			immCanObj.setImmigrationTemplateId(immigrationObj.getImmigrationTemplateId());
			immCanObj.setCandidateId(immigrationObj.getCandidate());
			immCanObj.setImmigrationId(immigrationObj);
			immCanObj.setDocumentType(docs.getDocumentType());
			immCanObj.setDocUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			immCanObj.setDescription(docs.getDescription());
			immCanObj.setDocCategory(docs.getDocumentCategory());
			if (docs.getIsRequired() != null && "Y".equalsIgnoreCase(docs.getIsRequired())) {
				immCanObj.setIsRequired(docs.getIsRequired());
			} else {
				immCanObj.setIsRequired(Constants.NO);
			}
			if (docs.getIsMultipleRequired() != null && "Y".equalsIgnoreCase(docs.getIsMultipleRequired())) {
				immCanObj.setAllowMultiple(docs.getIsMultipleRequired());
			} else {
				immCanObj.setAllowMultiple(Constants.NO);
			}
			immCanObj.setStatus(Constants.RECORD_ACTIVE);
			immCanObj.setDocumentStatus(Constants.DOCUMENT_UPLOAD_REQUESTED);
			immCanObj.setIsTemplateConfigured(Constants.YES);
			immCanObj.setCreatedBy(user);
			immCanObj.setUpdatedBy(user);
			Date currentDate = commonUtil.orgLocationCommonDate(new Date(), orgLocations);
			immCanObj.setUpdatedDate(currentDate);
			immCanObj.setCreatedDate(currentDate);
			try {
				immigrationCommonDao.saveImmigrationCandidateDocs(immCanObj);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	void copyImmigrationConfiguredFormsToCandidate(Immigration immigrationObj, User user, OrgLocations orgLocations) {
		List<ImmigrationTemplateForms> immigrationTemplateFormsList = immigrationTemplateDao
				.getImmigrationTemplateFormsList(immigrationObj.getImmigrationTemplateId().getId());
		ImmigrationCandidateForms immCanObj = null;

		for (ImmigrationTemplateForms forms : immigrationTemplateFormsList) {
			immCanObj = new ImmigrationCandidateForms();
			immCanObj.setImmigrationTempId(immigrationObj.getImmigrationTemplateId());
			immCanObj.setCandidate(immigrationObj.getCandidate());
			immCanObj.setImmigration(immigrationObj);
			immCanObj.setDescription(forms.getDescription());
			if (forms.getIsRequired() != null && "Y".equalsIgnoreCase(forms.getIsRequired())) {
				immCanObj.setIsRequired(forms.getIsRequired());
			} else {
				immCanObj.setIsRequired(Constants.NO);
			}
			immCanObj.setFormName(forms.getFormName());
			immCanObj.setFormType(forms.getFormType());
			immCanObj.setFormUUID(forms.getFormUUID());
			immCanObj.setFileName(forms.getFileName());
			immCanObj.setFormCategory(forms.getFormCategory());
			immCanObj.setFileContentType(forms.getFileContentType());
			immCanObj.setFileUrl(forms.getFileUrl());
			immCanObj.setStatus(Constants.RECORD_ACTIVE);
			immCanObj.setFormStatus(Constants.DOCUMENT_UPLOAD_REQUESTED);
			immCanObj.setIsTemplateConfigured(Constants.YES);
			Date currentDate = commonUtil.orgLocationCommonDate(new Date(), orgLocations);
			immCanObj.setCreatedBy(user);
			immCanObj.setUpdatedBy(user);
			immCanObj.setUpdatedDate(currentDate);
			immCanObj.setCreatedDate(currentDate);
			try {
				immigrationCommonDao.saveImmigrationCandidateForms(immCanObj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private List<CandidateResidence> savePetitionResidenceList(ImmigrationDTO immigrationDTO, Candidate candidate,
			User user, OrgLocations orgLocations) {
		List<CandidateResidence> residenceList = new ArrayList<CandidateResidence>(); // add residences to immigration
																						// candidate tables
		if (immigrationDTO.getResidenceList() != null) {
			List<ImmigrationResidenceDTO> residenceDtoLst = immigrationDTO.getResidenceList();
			for (ImmigrationResidenceDTO residenceDto : residenceDtoLst) {
				if (StringUtils.isEmpty(residenceDto.getLocation()))
					continue; // skip the empty residence rows based on one of the required field value
				CandidateResidence residenceEntity = new CandidateResidence();
				if (residenceDto.getId() != null && StringUtils.isNotEmpty(residenceDto.getId())) {
					residenceEntity = immigrationCommonDao
							.getCandidateResidenceDetailsById(Long.parseLong(residenceDto.getId()));
				}
				residenceEntity.setCandidate(candidate);
				residenceEntity.setCity(residenceDto.getCity());
				residenceEntity.setCountry(residenceDto.getCountry());
				residenceEntity.setLocation(residenceDto.getLocation());
				residenceEntity.setStreetAddress(residenceDto.getAddress());
				residenceEntity.setState(residenceDto.getState());
				residenceEntity.setZipcode(residenceDto.getZipcode());
				if (!StringUtils.isEmpty(residenceDto.getResidenceStartDate())) {
					residenceEntity.setStartDate((commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
							residenceDto.getResidenceStartDate())));
				}
				if (!StringUtils.isEmpty(residenceDto.getResidenceEndDate())) {
					residenceEntity.setEndDate((commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
							residenceDto.getResidenceEndDate())));
				}
				residenceEntity.setIsCurrAddress(Constants.NO);
				// default No for multiple residences treated as secondary residences.
				residenceEntity.setStatus(Constants.RECORD_ACTIVE);
				Date currentDate = commonUtil.orgLocationCommonDate(new Date(), orgLocations);
				residenceEntity.setCreatedDate(currentDate);
				residenceEntity.setUpdatedDate(currentDate);
				residenceEntity.setCreatedBy(user);
				residenceEntity.setUpdatedBy(user);
				residenceList.add(residenceEntity);
			}
		}
		return residenceList;
	}

	/*private List<CandidateLcaI94Summary> savePetitionLcaSummaryList(ImmigrationDTO immigrationDTO, Candidate candidate,
			User user, Requirement requirement, OrgLocations orgLocations) {
		List<CandidateLcaI94Summary> lcaSummaryLst = new ArrayList<CandidateLcaI94Summary>();
		
		CandidateLcaI94Summary lcaSummaryCurr = null;
		Date currentDate = commonUtil.orgLocationCommonDate(new Date(), orgLocations);
		System.out.println("candidate===========>"+immigrationDTO.getCandidateId());
		lcaSummaryCurr = immigrationCommonDao.getCandidateLCADetails(Long.parseLong(immigrationDTO.getCandidateId()));
		if (lcaSummaryCurr == null) {
			lcaSummaryCurr = new CandidateLcaI94Summary();
			lcaSummaryCurr.setCreatedDate(currentDate);
			lcaSummaryCurr.setCreatedBy(user);
		}
		
		lcaSummaryCurr.setIdNumber(immigrationDTO.getLcaNumber());
		
		if (immigrationDTO.getLcaStartDate() != null && !StringUtils.isEmpty(immigrationDTO.getLcaStartDate())) {
			lcaSummaryCurr.setStartDate(
					commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), immigrationDTO.getLcaStartDate()));
		}else {
			lcaSummaryCurr.setStartDate(null);
		}
		if (immigrationDTO.getLcaEndDate() != null && !StringUtils.isEmpty(immigrationDTO.getLcaEndDate())) {
			lcaSummaryCurr.setEndDate(
					commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), immigrationDTO.getLcaEndDate()));
		}else {
			lcaSummaryCurr.setEndDate(null);	
		}
		lcaSummaryCurr.setIsCurrent(Constants.YES);
		lcaSummaryCurr.setRequirementId(requirement);
		lcaSummaryCurr.setCandidateId(candidate);
		lcaSummaryCurr.setUpdatedBy(user);
		lcaSummaryCurr.setEmploymentLevel(immigrationDTO.getEmploymentLevel());
		lcaSummaryCurr.setEmployerName(immigrationDTO.getEmployer());
		lcaSummaryCurr.setEmployerLocation(immigrationDTO.getJobLocation());
		lcaSummaryCurr.setRateCurrency(immigrationDTO.getRateCurrency());
		lcaSummaryCurr.setRate(immigrationDTO.getRate());
		lcaSummaryCurr.setRateType(immigrationDTO.getRateType());
		lcaSummaryCurr.setStatus(Constants.RECORD_ACTIVE);
	//	lcaSummaryCurr.setIdType(Constants.ID_TYPE_LCA);
		lcaSummaryCurr.setUpdatedDate(currentDate);
		if (immigrationDTO.getJobStartDate() != null && StringUtils.isNotEmpty(immigrationDTO.getJobStartDate())) {
			lcaSummaryCurr.setJobStartDate(
					commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), immigrationDTO.getJobStartDate()));
		}else {
			lcaSummaryCurr.setJobStartDate(null);
		}
		if (immigrationDTO.getJobEndDate() != null && StringUtils.isNotEmpty(immigrationDTO.getJobEndDate())) {
			lcaSummaryCurr.setJobEndDate(
					commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), immigrationDTO.getJobEndDate()));
		}else {
			lcaSummaryCurr.setJobEndDate(null);
		}
		lcaSummaryLst.add(lcaSummaryCurr); // add current lca

		
		
		CandidateLcaI94Summary lcaSummaryPrev = null;
		lcaSummaryPrev = immigrationCommonDao.getCandidateLCADetails(Long.parseLong(immigrationDTO.getCandidateId()));
		if (lcaSummaryPrev == null) {
			lcaSummaryPrev = new CandidateLcaI94Summary();
		}
		lcaSummaryPrev.setIdNumber(immigrationDTO.getPrevLcaNumber());
		if (immigrationDTO.getPrevLcaStartDate() != null
				&& !StringUtils.isEmpty(immigrationDTO.getPrevLcaStartDate())) {
			lcaSummaryPrev.setStartDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
					immigrationDTO.getPrevLcaStartDate()));
		}else {
			lcaSummaryPrev.setStartDate(null);
		}
		if (immigrationDTO.getPrevLcaEndDate() != null && !StringUtils.isEmpty(immigrationDTO.getPrevLcaEndDate())) {
			lcaSummaryPrev.setEndDate(
					commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), immigrationDTO.getPrevLcaEndDate()));
		}else {
			lcaSummaryPrev.setEndDate(null);
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
	//	lcaSummaryPrev.setIdType(Constants.ID_TYPE_LCA);
		lcaSummaryPrev.setCreatedBy(user);
		lcaSummaryPrev.setUpdatedBy(user);
		lcaSummaryPrev.setCreatedDate(currentDate);
		lcaSummaryPrev.setUpdatedDate(currentDate);
		lcaSummaryLst.add(lcaSummaryPrev);
		return lcaSummaryLst;
	}*/
	
	
	private CandidateLcaJobDetails saveCandidateLcaJobDetails(Immigration immigrationObj,ImmigrationDTO immigrationDTO, Candidate candidate,
			User user, Requirement requirement, OrgLocations orgLocations) {
		
		
		CandidateLcaJobDetails canLcaJobDetails = null;
		Date currentDate = commonUtil.orgLocationCommonDate(new Date(), orgLocations);
		if(immigrationObj.getId() != null) {
		canLcaJobDetails = immigrationCommonDao.getCandidateLcaJobDetails(immigrationObj.getId());
		}
		
		if(canLcaJobDetails == null){
			canLcaJobDetails = new CandidateLcaJobDetails();
			canLcaJobDetails.setCreatedDate(currentDate);
			canLcaJobDetails.setCreatedBy(user);
		}
		
		canLcaJobDetails.setImmigration(immigrationObj);
		canLcaJobDetails.setRequirementId(requirement);
		canLcaJobDetails.setCandidateId(candidate);
		canLcaJobDetails.setUpdatedBy(user);
		canLcaJobDetails.setEmploymentLevel(immigrationDTO.getEmploymentLevel());
		canLcaJobDetails.setEmployerName(immigrationDTO.getEmployer());
		canLcaJobDetails.setEmployerLocation(immigrationDTO.getJobLocation());
		canLcaJobDetails.setRateCurrency(immigrationDTO.getRateCurrency());
		canLcaJobDetails.setRate(immigrationDTO.getRate());
		canLcaJobDetails.setRateType(immigrationDTO.getRateType());
		
		canLcaJobDetails.setStatus(Constants.RECORD_ACTIVE);
		canLcaJobDetails.setUpdatedDate(currentDate);
		if (immigrationDTO.getJobStartDate() != null && StringUtils.isNotEmpty(immigrationDTO.getJobStartDate())) {
			canLcaJobDetails.setJobStartDate(
					commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), immigrationDTO.getJobStartDate()));
		}else {
			canLcaJobDetails.setJobStartDate(null);
		}
		if (immigrationDTO.getJobEndDate() != null && StringUtils.isNotEmpty(immigrationDTO.getJobEndDate())) {
			canLcaJobDetails.setJobEndDate(
					commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), immigrationDTO.getJobEndDate()));
		}else {
			canLcaJobDetails.setJobEndDate(null);
		}
		
	    canLcaJobDetails = immigrationCommonDao.saveCandidateLcaJobDetails(canLcaJobDetails);
		
		return canLcaJobDetails;
	}

	private CandidateResumes savePetitionCandidateResume(ImmigrationDTO immigrationDTO, CandidateGlobal candidate, User user,
			OrgLocations orgLocations) {
		try {
			CandidateResumes candidateResume = new CandidateResumes();
			if (immigrationDTO.getResumeInfo() != null) {
				FileInfo fileInfo = immigrationDTO.getResumeInfo().get(0);
				candidateResume.setCandidateId(candidate);
				candidateResume.setFileName(fileInfo.getFileName());
				candidateResume.setFileURL(fileInfo.getFileURL());
				candidateResume.setFileContentType(fileInfo.getContentType());
				candidateResume.setOrgLocationId(orgLocations);
				candidateResume.setStatus(Constants.RECORD_ACTIVE);
				Date currentDate = commonUtil.orgLocationCommonDate(new Date(), orgLocations);
				candidateResume.setCreatedDate(currentDate);
				candidateResume.setUpdatedDate(currentDate);
				candidateResume.setCreatedBy(user.getUserCode());
				candidateResume.setResumeUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
				FileUploadUtil.moveFile(immigrationDTO.getStaticPath(), fileInfo, "resumes");
			}
			return candidateResume;
		} catch (Exception e) {
			logger.error("Exception caugth in savePetitionCandidateResume service" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	private CandidateIdendities savePetitionCandidateIdentities(ImmigrationDTO immigrationDTO, Candidate candidate,
			User user, OrgLocations orgLocations) {
		CandidateIdendities candidateIdendities = null;
		Date currentDate = commonUtil.orgLocationCommonDate(new Date(), orgLocations);
		candidateIdendities = candidateDao.getCandidateIdentitiesNumberByCandidateId(candidate.getId(),
				Constants.ID_TYPE_SSN);
		if (candidateIdendities == null) {
			candidateIdendities = new CandidateIdendities();
			candidateIdendities.setCreatedBy(user);
			candidateIdendities.setCreatedDate(currentDate);
		}
		candidateIdendities.setCandidateId(candidate);
		candidateIdendities.setIdNumber(immigrationDTO.getSsnNumber());
		candidateIdendities.setIdType(Constants.ID_TYPE_SSN);
		candidateIdendities.setStatus(Constants.RECORD_ACTIVE);
		candidateIdendities.setUpdatedBy(user);
		candidateIdendities.setUpdatedDate(currentDate);
		return candidateIdendities;
	}

	private Candidate savePetitionCandidate(ImmigrationDTO immigrationDTO, OrgLocations orgLocations, User user) {
		try {
			Candidate candidate = candidateDao
					.getCandidateByCandidateId(Long.parseLong(immigrationDTO.getCandidateId()));
			candidate.setFirstName(immigrationDTO.getFirstName());
			candidate.setMiddelName(immigrationDTO.getMiddleName());
			candidate.setLastName(immigrationDTO.getLastName());
			candidate.setGender(immigrationDTO.getGender());
			candidate.setEmail(immigrationDTO.getEmail());
			candidate.setMobilePhone(immigrationDTO.getMobile());
			candidate.setAlternateMobilePhone(immigrationDTO.getAlternateMobile());
			candidate.setDateOfBirth(
					commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), immigrationDTO.getBirthDate()));
			candidate.setPlaceOfBirth(immigrationDTO.getBirthPlace());
			candidate.setNationality(immigrationDTO.getNationality());
			candidate.setMaritalStatus(immigrationDTO.getMaritalStatus());
			candidate.setEadNo(immigrationDTO.getEadNumber());
			
			candidate.setI20ServiceNo(immigrationDTO.getI_20_number());	
			candidate.setI94No(immigrationDTO.getI_94_number());
			
			candidate.setUpdatedBy(user.getId().toString());
			Date currentDate = commonUtil.orgLocationCommonDate(new Date(), orgLocations);
			candidate.setUpdatedDate(currentDate);
			candidate.setLocation(immigrationDTO.getLocation()); // considered as a primary residence address.
			candidate.setAddress(immigrationDTO.getAddress());
			candidate.setCity(immigrationDTO.getCity());
			candidate.setState(immigrationDTO.getState());
			candidate.setCountry(immigrationDTO.getCountry());
			candidate.setZipCode(immigrationDTO.getZipcode());
			if(StringUtils.isNotBlank(immigrationDTO.getResidenceStartDate())) {
			candidate.setResidenceStartDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
					immigrationDTO.getResidenceStartDate()));
			}
			if(immigrationDTO.getExperienceYears() != null && !immigrationDTO.getExperienceYears().isEmpty()) {
			candidate.setExperienceYears(immigrationDTO.getExperienceYears());
			}
			if(immigrationDTO.getExperienceMonths() != null && !immigrationDTO.getExperienceMonths().isEmpty()) {
				candidate.setExperienceMonths(immigrationDTO.getExperienceMonths());
			}
			return candidate;
		} catch (Exception e) {
			logger.error("Exception caugth in savePetitionCandidate service" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	private Immigration savePetitionImmigration(ImmigrationDTO immigrationDTO, OrgLocations orgLocations,
			Candidate candidate, Requirement requirement, User user) throws Exception, Exception {
		Immigration immigration = null;
		Date currentDate = commonUtil.orgLocationCommonDate(new Date(), orgLocations);
		if (immigrationDTO.getImmigrationId() != null && StringUtils.isNotEmpty(immigrationDTO.getImmigrationId())) {
			immigration = immigrationCommonDao.getImmigrationById(Long.parseLong(immigrationDTO.getImmigrationId()));
		} else {
			immigration = new Immigration();
			immigration.setCreatedBy(user);
			immigration.setCreatedDate(currentDate);
		}
		LookUpData petitionTypeId = lookUpDataService.getLookUpDataByIdAndCategory(Constants.VISA,
				Long.parseLong(immigrationDTO.getPetitionType()));
		immigration.setPetitionType(petitionTypeId);
		immigration.setOpenDate(
				commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), immigrationDTO.getOpenDate()));
		LookUpData uscisStatus = lookUpDataService.getLookUpDataByIdAndCategory(ImmigrationConstants.PETITION_STATUS,
				Long.parseLong(immigrationDTO.getCurrUscisStatus()));
		immigration.setCurrUscisStauts(uscisStatus);
		if (StringUtils.isNotBlank(immigrationDTO.getArrivalDate()) && !immigrationDTO.getArrivalDate().equals("null")) {
			immigration.setDateOfArrival(
					commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), immigrationDTO.getArrivalDate()));
		}
		immigration.setPlaceOfEntry(immigrationDTO.getEntryPlace());
		if (immigrationDTO.getTemplateId() != null && !StringUtils.isEmpty(immigrationDTO.getTemplateId())) {
			ImmigrationTemplate immiTemplate = immigrationTemplateDao
					.getImmigrationTemplateById(Long.parseLong(immigrationDTO.getTemplateId()));
			immigration.setImmigrationTemplateId(immiTemplate);
			immigration.setCountry(immiTemplate.getCountry());
		}
		if(!StringUtils.isNotBlank(immigration.getImmigrationUuid())){ // skip UUID generation in edit mode.
			immigration.setImmigrationUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
		}
		OrgRelations orgRelations = orgRelationsDao.getOrgRelationById(Long.parseLong(immigrationDTO.getAttorneyId()));
		immigration.setOrgRelations(orgRelations);
		immigration.setAttorneyOrgLocationId(orgRelations.getResponderLocationId());
		OrgSpoc orgSpoc = orgSpocDao.getOrgSpocById(Long.parseLong(immigrationDTO.getAttorneyContact()));
		immigration.setOrgSpoc(orgSpoc);
		immigration.setSpocUserId(orgSpoc.getVendorUser());
		immigration.setCandidate(candidate);
		if (candidate.getConsultantUser().getId() != null) {
			User candidateUser = userDao.getUserById(candidate.getConsultantUser().getId());
			immigration.setCandidateUserId(candidateUser);
		}
		if (immigrationDTO.getEditMode().equalsIgnoreCase("false")) {
			if (StringUtils.isNotBlank(orgLocations.getIsLawFirm())
					&& orgLocations.getIsLawFirm().equalsIgnoreCase("Y")) {
				immigration.setOrgLocationId(orgRelations.getLocationId());
			} else {
				immigration.setOrgLocationId(orgLocations);
			}
		}
		immigration.setProcessType(immigrationDTO.getProcessType());
		immigration.setReceiptNo(immigrationDTO.getReceiptNo());
		
		if (StringUtils.isEmpty(immigrationDTO.getImmigrationId())) {
			immigration.setGeneratedId(getImmigrationIdForGeneratedId(orgLocations));
		}
		immigration.setJob(requirement);
		immigration.setStatus(Constants.RECORD_ACTIVE);
		immigration.setUpdatedBy(user);
		immigration.setUpdatedDate(currentDate);

		return immigration;
	}

	@Override
	public ImmigrationDTO getImmigrationDetails(String immigrationId, OrgLocations orgLocations) {
		ImmigrationDTO dto = new ImmigrationDTO();
		try {
			Immigration immigration = immigrationCommonDao.getImmigrationDetails(immigrationId);
			dto.setImmigrationUUID(immigration.getImmigrationUuid());
			dto.setPetitionType(immigration.getPetitionType().getName());
			dto.setProcessType(immigration.getProcessType());
			dto.setGeneratedId(immigration.getGeneratedId());
			dto.setOpenDate(
					commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), immigration.getOpenDate()));
			dto.setCurrUscisStatus(immigrationCommonDao.getPetitionTypeById(immigration.getCurrUscisStauts().getId(),
					ImmigrationConstants.PETITION_STATUS));
			dto.setCurrUscisStatusId(immigration.getCurrUscisStauts().getId());
			if (immigration.getDateOfArrival() != null  && StringUtils.isNotBlank(immigration.getDateOfArrival().toString())) {
				dto.setArrivalDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
						immigration.getDateOfArrival()));
			}
			dto.setEntryPlace(immigration.getPlaceOfEntry());
			if (immigration.getImmigrationTemplateId() != null) {
				dto.setTemplateName(immigration.getImmigrationTemplateId().getName());
			}
			/* petition details end */
			if (orgLocations.getId().equals(immigration.getOrgLocationId().getId())) {
				dto.setAttorneyName(immigration.getOrgRelations().getResponderLocationId().getOrgLocationName());
				dto.setAttorneyEmail(immigration.getOrgSpoc().getVendorUser().getEmail());
				dto.setAttorneyPhone(immigration.getOrgSpoc().getVendorUser().getMobilePhone());
				dto.setAttorneyFax(""); // no data need to update
				// dto.setAttorneyLawFirm(""); //removed column as per request
				dto.setAttorneyContact(immigration.getOrgSpoc().getVendorUser().getFullName());
			} else {
				dto.setAttorneyName(immigration.getOrgRelations().getLocationId().getOrgLocationName());
				dto.setAttorneyEmail(immigration.getOrgSpoc().getClientUser().getEmail());
				dto.setAttorneyPhone(immigration.getOrgSpoc().getClientUser().getMobilePhone());
				dto.setAttorneyFax(""); // no data need to update
				// dto.setAttorneyLawFirm(""); //removed column as per request
				dto.setAttorneyContact(immigration.getOrgSpoc().getClientUser().getFullName());
			}
			/* attorney end */
			if (immigration.getJob() != null) {
				dto.setJobTitle(immigration.getJob().getJobTitle());
			}
			
			// old Code 
			
			/*CandidateLcaI94Summary lcaI94Summary = immigrationCommonDao.getCandidateLCADetails(immigration.getCandidate().getId());
			if (lcaI94Summary.getEmploymentLevel() != null
					&& !StringUtils.isEmpty(lcaI94Summary.getEmploymentLevel())) {
				dto.setEmploymentLevelName(immigrationCommonDao.getPetitionTypeById(
						Long.parseLong(lcaI94Summary.getEmploymentLevel()), ImmigrationConstants.EMPLOYMENT_LEVEL));
			}
			
			if(lcaI94Summary.getEmployerName() != null) {
			dto.setEmployer(lcaI94Summary.getEmployerName());
			}
			dto.setPrevLcaNumber(lcaI94Summary.getIdNumber());
			dto.setPrevLcaStartDate(
					commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), lcaI94Summary.getStartDate()));
			dto.setPrevLcaEndDate(
					commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), lcaI94Summary.getEndDate()));
			dto.setEmployer(lcaI94Summary.getEmployerName());
			dto.setJobLocation(lcaI94Summary.getEmployerLocation());
			dto.setLcaStartDate(
					commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), lcaI94Summary.getStartDate()));
			dto.setLcaEndDate(
					commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), lcaI94Summary.getEndDate()));
			dto.setJobStartDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
					lcaI94Summary.getJobStartDate()));
			dto.setJobEndDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
					lcaI94Summary.getJobEndDate()));*/
			// New Code
			CandidateLcaJobDetails canLcaJobDetails = immigrationCommonDao.getCandidateLcaJobDetails(immigration.getId());
			
			if (canLcaJobDetails != null && !StringUtils.isEmpty(canLcaJobDetails.getEmploymentLevel())) {
				dto.setEmploymentLevelName(immigrationCommonDao.getPetitionTypeById(Long.parseLong(canLcaJobDetails.getEmploymentLevel()), ImmigrationConstants.EMPLOYMENT_LEVEL));
			}
			
			if(canLcaJobDetails != null) {
				if(canLcaJobDetails.getEmployerName() != null) {
				dto.setEmployer(canLcaJobDetails.getEmployerName());
				}
				
				dto.setEmployer(canLcaJobDetails.getEmployerName());
				dto.setJobLocation(canLcaJobDetails.getEmployerLocation());
				dto.setJobStartDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),canLcaJobDetails.getJobStartDate()));
				dto.setJobEndDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),canLcaJobDetails.getJobEndDate()));
			}
			
			/* jobdetails end */
			Candidate candidate = candidateDao.getCandidateById(immigration.getCandidate().getId());
			dto.setAssociateName(candidate.getFirstName() + " " + candidate.getLastName());
			dto.setFirstName(candidate.getFirstName());
			dto.setLastName(candidate.getLastName());
			dto.setMiddleName(candidate.getMiddelName());
			dto.setEmail(candidate.getEmail());
			dto.setMobile(candidate.getMobilePhone());
			dto.setAlternateMobile(candidate.getAlternateMobilePhone());
			
			dto.setBirthDate(
					commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), candidate.getDateOfBirth()));
			dto.setBirthPlace(candidate.getPlaceOfBirth());
			dto.setNationality(candidate.getNationality());
			dto.setMaritalStatus(candidate.getMaritalStatus());
			dto.setSsnNumber(candidateDao
					.getCandidateIdentitiesNumberByCandidateId(candidate.getId(), Constants.ID_TYPE_SSN).getIdNumber());
			dto.setI_94_number(candidate.getI94No());
			dto.setI_20_number(candidate.getI20ServiceNo());
			dto.setEadNumber(candidate.getEadNo());
			dto.setExperienceYears(candidate.getExperienceYears());
			dto.setExperienceMonths(candidate.getExperienceMonths());
		} catch (Exception e) {
			logger.error("Exception caugth in getImmigrationViewList service" + e.getMessage());
			e.printStackTrace();
		}
		return dto;
	}

	@Override
	public String saveCandidateFamilyMemberDetails(CandidateFamilyDetailsDTO familyDetailsDto,
			OrgLocations orgLocations, User user, Long immigrationId) throws Exception {
		String status = null;
		CandidateFamilyDetails familyDetails = new CandidateFamilyDetails();
		Candidate candidate = candidateDao.getCandidateByCandidateId(familyDetailsDto.getCandidateId());
		Immigration immigration = immigrationCommonDao.getImmigrationById(immigrationId);
		candidate.setCandidateId(familyDetailsDto.getCandidateId());
		familyDetails.setCandidate(candidate);
		familyDetails.setOrgLocationId(orgLocations);
		familyDetails.setFirstName(familyDetailsDto.getFirstName());
		familyDetails.setMiddleName(familyDetailsDto.getMiddleName());
		familyDetails.setLastName(familyDetailsDto.getLastName());
		familyDetails.setPlaceOfBirth(familyDetailsDto.getPlaceOfBirth());
		familyDetails.setRelation(familyDetailsDto.getRelation());
		if (!StringUtils.isEmpty(familyDetailsDto.getDateOfBirth())) {
			familyDetails.setDateOfBirth(
					commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), familyDetailsDto.getDateOfBirth()));
		}
		if (!StringUtils.isEmpty(familyDetailsDto.getMarraigeDate())) {
			familyDetails.setMarraigeDate(
					commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), familyDetailsDto.getMarraigeDate()));
		}
		if (!StringUtils.isEmpty(familyDetailsDto.getMarriageEndDate())) {
			familyDetails.setMarriageEndDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
					familyDetailsDto.getMarriageEndDate()));
		}
		familyDetails.setMarriagePlace(familyDetailsDto.getMarriagePlace());
		familyDetails.setNationality(familyDetailsDto.getNationality());
		familyDetails.setCurrentAddress(familyDetailsDto.getCurrentAddress());
		familyDetails.setStatus(Constants.RECORD_ACTIVE);
		Date currentDate = commonUtil.orgLocationCommonDate(new Date(), orgLocations);
		if (familyDetailsDto.getId() != null) {
			logger.debug(" in edit mode --member id >" + familyDetailsDto.getId());
			familyDetails.setId(familyDetailsDto.getId());
		} else {
			familyDetails.setCreatedDate(currentDate); // created date , created by fields skipped for edit mode
			familyDetails.setCreatedBy(user);
		}
		familyDetails.setCreatedBy(user);
		familyDetails.setUpdatedDate(currentDate);
		familyDetails.setUpdatedBy(user);
		status = immigrationCommonDao.saveCandidateFamilyMemberDetails(familyDetails);
		StringBuilder actionDesc = new StringBuilder();
		if (familyDetailsDto.getId() != null) {
			actionDesc.append("Family Details Updated").toString();
			immigrationActivityCommonUtil.saveImmigrationCandidateActivities(user, orgLocations, candidate, immigration,
					actionDesc.toString());
		} else {
			actionDesc.append("New Family Member Added").toString();
			immigrationActivityCommonUtil.saveImmigrationCandidateActivities(user, orgLocations, candidate, immigration,
					actionDesc.toString());
		}
		return status;
	}

	
	//Vias Save
	@Override
	public CandidateVisa saveVisaDetails(ImmigrationVisaDTO immigrationVisaDTO, User user,
			OrgLocations orgLocations, String immigrationId, Long visaHistoryId) throws Exception {
		CandidateVisa candidateVisa = null;
		ImmigrationVisaDTO visaDetailsDTO = null;
		if (immigrationVisaDTO.getVisaNumber() != null && immigrationVisaDTO.getCountry() != null) {
			visaDetailsDTO = immigrationCommonDao.getCandidateVisaDetailsByVisaNumberAndCountry
					                (immigrationVisaDTO.getCandidateId(), immigrationVisaDTO.getVisaNumber() , immigrationVisaDTO.getCountry() );
			if(visaDetailsDTO != null )
			candidateVisa = immigrationCommonDao.getCandidateVisaDetailsById(visaDetailsDTO.getCandidateVisaId());
		}
			
		if (candidateVisa == null) {
			candidateVisa = new CandidateVisa();
			candidateVisa.setCreatedBy(user);
			candidateVisa.setCreatedDate(commonUtil.currentDate());
		}
	    Candidate candidate = candidateDao.getCandidateByCandidateId(immigrationVisaDTO.getCandidateId());
		candidate.setId(immigrationVisaDTO.getCandidateId());
		Immigration immigration = new Immigration();
		if(StringUtils.isNotBlank(immigrationId)) {
		   immigration.setId(Long.parseLong(immigrationId));
		}
		// SimpleDateFormat simpleDateFormat =
		// orgLocationId.getLocFullFormDateFormatter();
		if (immigrationVisaDTO.getCandidateId() != null) {
			if (StringUtils.isNotBlank(immigrationVisaDTO.getVisaType())) {
			candidateVisa.setVisaType(immigrationVisaDTO.getVisaType());
			}
			if (StringUtils.isNotBlank(immigrationVisaDTO.getVisaNumber())) {
				candidateVisa.setVisaNumber(immigrationVisaDTO.getVisaNumber());
			}
			if (StringUtils.isNotBlank(immigrationVisaDTO.getCountry())) {

				candidateVisa.setCountry(immigrationVisaDTO.getCountry());
			}

		}
		
		candidateVisa.setCandidate(candidate);
		candidateVisa.setOrgLocationId(orgLocations);
		candidateVisa.setStatus(Constants.CANDIDATE_ACTIVE);
		candidateVisa.setUpdatedDate(commonUtil.currentDate());
		candidateVisa.setUpdatedBy(user);

		logger.debug("In CandidateCertificationsServiceImpl class save method");
		candidateVisa = immigrationCommonDao.saveVisaDetails(candidateVisa);
		if(candidateVisa.getId()!= null) {
			CandidateVisaHistory visaHistory = null;
			visaHistory = immigrationCommonDao.getCandidateVisaHistoryByStatus(immigrationVisaDTO.getId(), immigrationVisaDTO.getIsCurrent());
			if(visaHistory == null )
				visaHistory = new CandidateVisaHistory();
			
			visaHistory.setVisaId(candidateVisa);
			visaHistory.setIsCurrent(immigrationVisaDTO.getIsCurrent());
			if(immigrationVisaDTO.getId() != null) {
				visaHistory.setId(immigrationVisaDTO.getId() );
			}
			if (StringUtils.isNotBlank(immigrationVisaDTO.getValidFrom())) {
				try {
					visaHistory.setValidFromDate(commonUtil.orgLocationDateFormat(
							orgLocations.getDateFormat(), immigrationVisaDTO.getValidFrom()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				visaHistory.setValidFromDate(null);
			}
			if (StringUtils.isNotBlank(immigrationVisaDTO.getValidTo())) {
				try {
					visaHistory.setValidToDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
							immigrationVisaDTO.getValidTo()));

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				visaHistory.setValidToDate(null);
			}
			visaHistory.setStatus(Constants.CANDIDATE_ACTIVE);
			visaHistory.setCreatedBy(user);
			visaHistory.setUpdatedBy(user);
			Date toDayDate = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String currentDateStr = dateFormat.format(toDayDate);
			Date newDate = dateFormat.parse(currentDateStr);
			Date currentDt = commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), newDate);

			if (currentDt.before(visaHistory.getValidToDate())) {
				visaHistory.setIsCurrent(ImmigrationConstants.IS_CURRENT);
			} else {
				visaHistory.setIsCurrent(ImmigrationConstants.IS_CURRENT_NO);
			}
			visaHistory.setId(immigrationVisaDTO.getId());
			Date currentDate = commonUtil.orgLocationCommonDate(new Date(), orgLocations);
			visaHistory.setUpdatedDate(currentDate);
			visaHistory.setCreatedDate(currentDate);
			
			
			visaHistory = immigrationCommonDao.saveVisaHistoaryDetails(visaHistory);
			// below condition is added for upload functionality for multiple visa details. if current visa comes earlier visas will be in inactive mode.
			// for upload we are passing visa history id as null. 
			if(visaHistory.getIsCurrent().equalsIgnoreCase("Y")){
				int count= immigrationCommonDao.updateCanVisaHistoryDetails(candidateVisa.getId(), visaHistory.getId());
				logger.debug("updated records count in visa history detials for visa id "+visaHistory.getVisaId() +" in visa history table " +count);
				
			}
			
		}
		
		StringBuilder actionDesc = new StringBuilder();
		actionDesc.append("Visa Details Updated ").toString();
		if (immigrationVisaDTO.getId() != null && StringUtils.isNotBlank(immigrationId)) {
			
			immigrationActivityCommonUtil.saveImmigrationCandidateActivities(user, orgLocations, candidate, immigration,
					actionDesc.toString());

		} else {
			candidateActivityCommonUtil.storeCandidateActivity(user, orgLocations, candidate, "", "Visa Details", actionDesc.toString(), "CANDIDATE");
		}
		return candidateVisa;
		
	}	
	
	/**
	 * Passport Save
	 */
	@Override
	public CandidatePassport savePassportDetails(ImmigrationPassportDTO immigrationPassportDTO, User user,
			OrgLocations orgLocations, String immigrationId) throws Exception {

		CandidatePassport candidatePassport = null;
		if (immigrationPassportDTO.getCandidatePassportId() != null) {
			candidatePassport = immigrationCommonDao
					.getCandidatePassportDetailsById(immigrationPassportDTO.getCandidatePassportId());
		} else {
			candidatePassport = new CandidatePassport();
			candidatePassport.setCreatedBy(user);
			candidatePassport.setCreatedDate(commonUtil.currentDate());
		}
		Candidate candidate = candidateDao
				.getCandidateByCandidateId(Long.parseLong(immigrationPassportDTO.getCandidateId()));
		Immigration immigration = null;
		if(StringUtils.isNotBlank(immigrationId))  {
		 immigration = immigrationCommonDao.getImmigrationById(Long.parseLong(immigrationId));
		}
		// SimpleDateFormat simpleDateFormat =
		// orgLocationId.getLocFullFormDateFormatter();
		StringBuffer activitySummary = new StringBuffer();
		if (immigrationPassportDTO.getCandidateId() != null) {

			if (StringUtils.isNotBlank(immigrationPassportDTO.getPassportNumber())) {
			candidatePassport.setPassportNumber(immigrationPassportDTO.getPassportNumber());
			}
			if (StringUtils.isNotBlank(immigrationPassportDTO.getCountry())) {

				candidatePassport.setCountry(immigrationPassportDTO.getCountry());
			}

			if (StringUtils.isNotBlank(immigrationPassportDTO.getValidFrom())) {
				try {

					// Date validFrom =
					// commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),immigrationPassportVisaDTO.getValidFrom());
					candidatePassport.setValidFromDate(commonUtil.orgLocationDateFormat(
							orgLocations.getDateFormat(), immigrationPassportDTO.getValidFrom()));

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				candidatePassport.setValidFromDate(null);
			}
			if (StringUtils.isNotBlank(immigrationPassportDTO.getValidTo())) {
				try {

					candidatePassport.setValidToDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
							immigrationPassportDTO.getValidTo()));

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				candidatePassport.setValidToDate(null);
			}

		}
		

		candidatePassport.setCandidate(candidate);
		candidatePassport.setOrgLocationId(orgLocations);
		candidatePassport.setStatus(Constants.CANDIDATE_ACTIVE);
		candidatePassport.setUpdatedDate(commonUtil.currentDate());
		candidatePassport.setUpdatedBy(user);

		logger.debug("In CandidateCertificationsServiceImpl class save method");
		candidatePassport = immigrationCommonDao.savePassportDetails(candidatePassport);
		StringBuilder actionDesc = new StringBuilder();
		actionDesc.append("Passport Details Updated ").toString();
		if (immigrationPassportDTO.getCandidatePassportId() != null && immigrationId != null) {
			
			immigrationActivityCommonUtil.saveImmigrationCandidateActivities(user, orgLocations, candidate, immigration,
					actionDesc.toString());

		} else {
			candidateActivityCommonUtil.storeCandidateActivity(user, orgLocations, candidate, "", "Passport Details", actionDesc.toString(), "CANDIDATE");
		}
		return candidatePassport;

	}

	@Override
	public CandidateResidence saveResidenceDetails(ResidenceDTO residenceDTO, OrgLocations orgLocations, User user,
			Long candidateId, Long immigrationId) throws Exception {
		CandidateResidence candidateResidence = null;
		Date date = new Date();
		if (residenceDTO.getResidenceId() != null) {
			candidateResidence = immigrationCommonDao.getCandidateResidenceDetailsById(residenceDTO.getResidenceId());
		} else {
			candidateResidence = new CandidateResidence();
			candidateResidence.setCreatedDate(commonUtil.orgLocationCommonDate(date, orgLocations));
			candidateResidence.setCreatedBy(user);

		}
		Candidate candidate = candidateDao.getCandidateByCandidateId(candidateId);
		Immigration immigration = immigrationCommonDao.getImmigrationById(immigrationId);
		candidateResidence.setCandidate(candidate);
		candidateResidence.setLocation(residenceDTO.getLocation());
		candidateResidence.setStreetAddress(residenceDTO.getStreetAddress());
		candidateResidence.setCity(residenceDTO.getCity());
		candidateResidence.setState(residenceDTO.getState());
		candidateResidence.setCountry(residenceDTO.getCountry());
		candidateResidence.setZipcode(residenceDTO.getZipcode());
		if (!StringUtils.isEmpty(residenceDTO.getResidenceStartDate())) {
			candidateResidence.setStartDate((commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
					residenceDTO.getResidenceStartDate())));
		}
		if (!StringUtils.isEmpty(residenceDTO.getResidenceEndDate())) {
			candidateResidence.setEndDate((commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
					residenceDTO.getResidenceEndDate())));
		}
		candidateResidence.setStatus(Constants.RECORD_ACTIVE);
		candidateResidence.setUpdatedDate(commonUtil.orgLocationCommonDate(date, orgLocations));
		candidateResidence.setUpdatedBy(user);

		immigrationCommonDao.saveResidenceDetails(candidateResidence);
		StringBuilder actionDesc = new StringBuilder();
		if (residenceDTO.getResidenceId() != null) {
			actionDesc.append("Residence Details Updated").toString();
			immigrationActivityCommonUtil.saveImmigrationCandidateActivities(user, orgLocations, candidate, immigration,
					actionDesc.toString());
		} else {
			actionDesc.append("New Residence Details Added").toString();
			immigrationActivityCommonUtil.saveImmigrationCandidateActivities(user, orgLocations, candidate, immigration,
					actionDesc.toString());
		}
		return candidateResidence;
	}

	@Override
	public List<CandidateFamilyDetails> getCandidateFamilyDetails(Long candidateId, String searchVal) {
		return immigrationCommonDao.getCandidateFamilyDetails(candidateId, searchVal);
	}

	@Override
	public ImmigrationCandidateDocuments uploadRequstedDocForCandidate(
			ImmigrationTemplateDocDTO immigrationTemplateDocDTO, Long id, Long candidateId, Long immigrationId,
			User user, OrgLocations orgLocations) throws Exception {
		ImmigrationCandidateDocuments docs = immigrationCommonDao.getImmigrationCandidateDocsById(id, candidateId,
				immigrationId);
		Date date = commonUtil.orgLocationCommonDate(new Date(), orgLocations);
		if (immigrationTemplateDocDTO.getFileInfo() != null) {
			FileInfo fileInfo = immigrationTemplateDocDTO.getFileInfo().get(0);
			docs.setUplodedFileUrl(fileInfo.getFileURL());
			docs.setUpldedFileContentType(fileInfo.getContentType());
			docs.setUpldedFileName(fileInfo.getFileName());
			docs.setDocumentStatus(Constants.DOCUMENT_UPLOAD);
			docs.setUpdatedDate(date);
			docs.setUpdatedBy(user);
			immigrationCommonDao.saveImmigrationCandidateDocs(docs);
			List<FileInfo> fileInfo1 = immigrationTemplateDocDTO.getFileInfo();
			for (FileInfo info : fileInfo1) {
				ImmigrationCandidateDocumentsHistory docsHistory = null;
				if (!immigrationTemplateDocDTO.getImmigrationCandidateDocHistoryId().contains(null)) {
					docsHistory = immigrationCommonDao.getImmigrationCandidateDocumentsHistoryById(immigrationTemplateDocDTO.getImmigrationCandidateDocHistoryId().get(0));
				} else {
					docsHistory = new ImmigrationCandidateDocumentsHistory();
				}
				docsHistory.setCandidateId(docs.getCandidateId());
				docsHistory.setImmigrationCandidateDocumentId(docs);
				docsHistory.setImmigrationId(docs.getImmigrationId());
				docsHistory.setUpldedFileContentType(info.getContentType());
				docsHistory.setUpldedFileName(info.getFileName());
				docsHistory.setUplodedFileUrl(info.getFileURL());
				docsHistory.setStatus(Constants.RECORD_ACTIVE);
				docsHistory.setUpdatedDate(date);
				docsHistory.setCreatedDate(date);
				docsHistory.setCreatedBy(user);
				docsHistory.setUpdatedBy(user);
				FileUploadUtil.moveFile(immigrationTemplateDocDTO.getStaticPath(), info,
						"immigrationCandidateDocuments");
				immigrationCommonDao.saveImmigrationCandidateDocumentHistory(docsHistory);
			}
		}

		return docs;
	}

	@Override
	public String getImmigrationIdForGeneratedId(OrgLocations orgLocations) {
		String companyShortCode = null;
		Long number = 1L;
		String immigrationId = null;
		Integer generatedIdcount = 0;
		StringBuilder sbuilder = new StringBuilder();
		sbuilder.append(orgLocations.getGeneratedUserId()).append(Constants.IMMIGRATION_ENTITY);
		String generatedUsreId = sbuilder.toString();
		for (int i = 0; i < generatedUsreId.length(); i++) {
			char ch = generatedUsreId.charAt(i);
			if (Character.isAlphabetic(ch)) {
				generatedIdcount++;
			}
		}
		generatedIdcount = generatedIdcount + 1;
		Long checkNumber = immigrationCommonDao.getImmigrationGeneratedIdByOrgLocationId(orgLocations,
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
		return sb.append(companyShortCode).append(Constants.IMMIGRATION_ENTITY).append(convetredRequirementsId)
				.toString();
	}

	@Override
	public String deletetImmigrationCandidateDocsById(Long id) {
		return immigrationCommonDao.deletetImmigrationCandidateDocsById(id);
	}

	@Override
	public ImmigrationCandidateDocuments getImmigrationCandidateDocumentById(Long id) {
		return immigrationCommonDao.getImmigrationCandidateDocumentById(id);
	}

	@Override
	public ImmigrationCandidateForms uploadRequstedFormForCandidate(
			ImmigrationTemplateFormDTO immigrationTemplateFormDTO, Long id, Long candidateId, Long immigrationId,
			User user, OrgLocations orgLocations) {
		ImmigrationCandidateForms form = null;
		try {
			form = immigrationCommonDao.getImmigrationCandidateFormsById(id, candidateId, immigrationId);

			FileInfo fileInfo = immigrationTemplateFormDTO.getImmigrationFormFileInfo().get(0);
			form.setUploadFileName(fileInfo.getFileName());
			form.setUploadedFormUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			form.setUploadedFileContentType(fileInfo.getContentType());
			form.setUploadedFileUrl(fileInfo.getFileURL());

			FileUploadUtil.moveFile(immigrationTemplateFormDTO.getStaticPath(), fileInfo, "immigrationCandidateForms");

			form.setFormStatus(Constants.DOCUMENT_UPLOAD);
			Date currentDate = commonUtil.orgLocationCommonDate(new Date(), orgLocations);
			form.setUpdatedBy(user);
			form.setUpdatedDate(currentDate);

			immigrationCommonDao.saveImmigrationCandidateForms(form);

		} catch (Exception e) {
			logger.error("Exception caught in service layer --->" + e.getMessage());
			e.printStackTrace();
		}

		return form;
	}

	@Override
	public String deletetCandidateFamilyDetails(Long candidateId) {
		return immigrationCommonDao.deletetCandidateFamilyDetails(candidateId);
	}

	@Override
	public List<CandidateResidence> getCandidatesResidenceListByCandidateIdAndOrgLocationID(Long candidateId,
			String searchVal) {
		return immigrationCommonDao.getCandidatesResidenceListByCandidateIdAndOrgLocationID(candidateId, searchVal);
	}

	public List<ImmigrationVisaDTO> getCandidateVisaDetails(Long candidateId, String searchValue) {
		// TODO Auto-generated method stub
		return immigrationCommonDao.getCandidateVisaDetails(candidateId, searchValue);
	}
	
	//passport
	@Override
	public List<CandidatePassport> getCandidatePassportDetails(Long candidateId,
			String searchValue) {
		// TODO Auto-generated method stub
		return immigrationCommonDao.getCandidatePassportDetails(candidateId,searchValue);
	}
	@Override
		public ResponseList<ImmigrationPassportDTO> getAssociatePassportDetails(Long candidateId, OrgLocations orgLocations)throws Exception {
			return immigrationCommonDao.getAssociatePassportDetails(candidateId, orgLocations);
	}
	@Override
	public CandidateFamilyDetails getCandidateFamilyDetailsById(Long id) {
		// TODO Auto-generated method stub
		return immigrationCommonDao.getCandidateFamilyDetailsById(id);
	}

	
	// visa delete
	@Override
	public void removeCandidateVisaDetails(Long visaHistoryId, Long candidateId, User user, OrgLocations orgLocations) {
		immigrationCommonDao.removeCandidateVisaDetails(visaHistoryId);
		StringBuilder actionDesc = new StringBuilder();
		actionDesc.append("Visa Details Removed ").toString();
		Candidate candidate = new Candidate();
		candidate.setId(candidateId);
		try {
			candidateActivityCommonUtil.storeCandidateActivity(user, orgLocations, candidate, "", "Visa Details", actionDesc.toString(), "CANDIDATE");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	// passport delete
	@Override
	public void removeCandidatePassportDetails(Long candidateId, Long id, User user, OrgLocations orgLocations) {
		immigrationCommonDao.removeCandidatePassportDetails(candidateId, id, user, orgLocations);
		StringBuilder actionDesc = new StringBuilder();
		actionDesc.append("Passport Details Removed ").toString();
		Candidate candidate = new Candidate();
		candidate.setId(candidateId);
		try {
			candidateActivityCommonUtil.storeCandidateActivity(user, orgLocations, candidate, "", "Passport Details", actionDesc.toString(), "CANDIDATE");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public String deletetImmigrationCandidateFormsById(Long id) {
		return immigrationCommonDao.deletetImmigrationCandidateFormsById(id);
	}

	@Override
	public ImmigrationCandidateForms getImmigrationCandidateFormById(Long id) {
		return immigrationCommonDao.getImmigrationCandidateFormById(id);
	}

	@Override
	public String deleteCandidateResidenceDetails(Long id, Long candidateId) {

		return immigrationCommonDao.deleteCandidateResidenceDetails(id, candidateId);
	}

	@Override
	public CandidateResidence getCandidateResidenceDetailsById(Long id) {
		// TODO Auto-generated method stub
		return immigrationCommonDao.getCandidateResidenceDetailsById(id);
	}

	@Override
	public CandidateVisa getCandidateVisaDetailsById(Long id) {
		// TODO Auto-generated method stub
		return immigrationCommonDao.getCandidateVisaDetailsById(id);
	}
	
	//passport 
	@Override
	public CandidatePassport getCandidatePassportDetailsById(Long id) {
		// TODO Auto-generated method stub
		return immigrationCommonDao.getCandidatePassportDetailsById(id);
	}

	@Override
	public ImmigrationCandidateDocuments saveImmigrationCandidateDocs(
			ImmigrationTemplateDocDTO immigrationTemplateDocDTO, User user, OrgLocations orgLocations)
			throws Exception {
		ImmigrationCandidateDocuments immigrationCandidateDocuments = null;
		Date currentDate = commonUtil.orgLocationCommonDate(new Date(), orgLocations);
		if (immigrationTemplateDocDTO.getImmigrationCandidateDocId() != null) {
			immigrationCandidateDocuments = immigrationCommonDao
					.getImmigrationCandidateDocumentById(immigrationTemplateDocDTO.getImmigrationCandidateDocId());
		} else {
			immigrationCandidateDocuments = new ImmigrationCandidateDocuments();
			immigrationCandidateDocuments.setCreatedDate(currentDate);
			immigrationCandidateDocuments.setCreatedBy(user);
		}
		Candidate candidate = candidateDao
				.getCandidateByCandidateId(Long.parseLong(immigrationTemplateDocDTO.getImmigrationcandidateId()));
		ImmigrationTemplate immigrationTemplate = new ImmigrationTemplate();
		immigrationTemplate.setId(Long.parseLong(immigrationTemplateDocDTO.getImmigrationTempplateId()));
		Immigration immigration = immigrationCommonDao
				.getImmigrationById(Long.parseLong(immigrationTemplateDocDTO.getImmigrationId()));
		immigrationCandidateDocuments.setCandidateId(candidate);
		immigrationCandidateDocuments.setImmigrationTemplateId(immigrationTemplate);
		immigrationCandidateDocuments.setImmigrationId(immigration);

		LookUpData documentTypeId = new LookUpData();
		documentTypeId.setId(immigrationTemplateDocDTO.getDocumentType());
		immigrationCandidateDocuments.setDocumentType(documentTypeId);
		immigrationCandidateDocuments.setDocUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		immigrationCandidateDocuments.setDescription(immigrationTemplateDocDTO.getDescription());
		immigrationCandidateDocuments.setDocCategory(Constants.DOCUMENT_TYPE);
		if (immigrationTemplateDocDTO.getOtherType() != null) {
			immigrationCandidateDocuments.setDocumentOtherType(immigrationTemplateDocDTO.getOtherType());
		}
		immigrationCandidateDocuments.setIsTemplateConfigured(Constants.NO);
		if (immigrationTemplateDocDTO.getIsRequired() != null
				&& "Y".equalsIgnoreCase(immigrationTemplateDocDTO.getIsRequired())) {
			immigrationCandidateDocuments.setIsRequired(immigrationTemplateDocDTO.getIsRequired());
		} else {
			immigrationCandidateDocuments.setIsRequired(Constants.NO);
		}
		if (immigrationTemplateDocDTO.getMultiple() != null
				&& "Y".equalsIgnoreCase(immigrationTemplateDocDTO.getMultiple())) {
			immigrationCandidateDocuments.setAllowMultiple(immigrationTemplateDocDTO.getMultiple());
		} else {
			immigrationCandidateDocuments.setAllowMultiple(Constants.NO);
		}
		immigrationCandidateDocuments.setStatus(Constants.RECORD_ACTIVE);
		immigrationCandidateDocuments.setDocumentStatus(Constants.DOCUMENT_UPLOAD_REQUESTED);
		if (immigrationTemplateDocDTO.getFileInfo() != null) {
			FileInfo fileInfo = immigrationTemplateDocDTO.getFileInfo().get(0);
			immigrationCandidateDocuments.setRequestFileUrl(fileInfo.getFileURL());
			immigrationCandidateDocuments.setRequestFileName(fileInfo.getFileName());
			immigrationCandidateDocuments.setRequestFileContentType(fileInfo.getContentType());
			FileUploadUtil.moveFile(immigrationTemplateDocDTO.getStaticPath(), fileInfo,
					"immigrationCandidateDocuments");

		}
		immigrationCandidateDocuments.setUpdatedBy(user);
		immigrationCandidateDocuments.setUpdatedDate(currentDate);

		immigrationCommonDao.saveImmigrationCandidateDocs(immigrationCandidateDocuments);
		StringBuilder actionDesc = new StringBuilder();
		if (immigrationTemplateDocDTO.getImmigrationCandidateDocId() != null) {
			actionDesc.append("Document Details Updated").toString();
			immigrationActivityCommonUtil.saveImmigrationCandidateActivities(user, orgLocations, candidate, immigration,
					actionDesc.toString());
		} else {
			actionDesc.append("New Document Details Added").toString();
			immigrationActivityCommonUtil.saveImmigrationCandidateActivities(user, orgLocations, candidate, immigration,
					actionDesc.toString());
		}
		return immigrationCandidateDocuments;
	}

	@Override
	public ImmigrationCandidateForms saveImmigrationCandidateForms(
			ImmigrationTemplateFormDTO immigrationTemplateFormDTO, User user, OrgLocations orgLocations)
			throws Exception {
		ImmigrationCandidateForms immigrationCandidateForms = null;
		Date currentDate = commonUtil.orgLocationCommonDate(new Date(), orgLocations);
		if (immigrationTemplateFormDTO.getImmigrationCandidateFormId() != null) {
			immigrationCandidateForms = immigrationCommonDao
					.getImmigrationCandidateFormById(immigrationTemplateFormDTO.getImmigrationCandidateFormId());
		} else {
			immigrationCandidateForms = new ImmigrationCandidateForms();
			immigrationCandidateForms.setCreatedBy(user);
			immigrationCandidateForms.setCreatedDate(currentDate);

		}
		Candidate candidate = candidateDao
				.getCandidateByCandidateId(Long.parseLong(immigrationTemplateFormDTO.getImmigrationcandidateId()));
		ImmigrationTemplate immigrationTemplate = new ImmigrationTemplate();
		immigrationTemplate.setId(Long.parseLong(immigrationTemplateFormDTO.getImmigrationTempplateId()));
		Immigration immigration = immigrationCommonDao
				.getImmigrationById(Long.parseLong(immigrationTemplateFormDTO.getImmigrationId()));
		immigrationCandidateForms.setCandidate(candidate);
		immigrationCandidateForms.setImmigrationTempId(immigrationTemplate);
		immigrationCandidateForms.setImmigration(immigration);
		immigrationCandidateForms.setFormName(immigrationTemplateFormDTO.getFormName());
		immigrationCandidateForms.setDescription(immigrationTemplateFormDTO.getFormDescription());
		immigrationCandidateForms.setIsTemplateConfigured(Constants.NO);
		if (immigrationTemplateFormDTO.getIsRequired() != null
				&& "Y".equalsIgnoreCase(immigrationTemplateFormDTO.getIsRequired())) {
			immigrationCandidateForms.setIsRequired(immigrationTemplateFormDTO.getIsRequired());
		} else {
			immigrationCandidateForms.setIsRequired(Constants.NO);
		}
		immigrationCandidateForms.setStatus(Constants.RECORD_ACTIVE);
		immigrationCandidateForms.setFormStatus(Constants.DOCUMENT_UPLOAD_REQUESTED);
		if (immigrationTemplateFormDTO.getImmigrationFormFileInfo() != null) {
			FileInfo fileInfo = immigrationTemplateFormDTO.getImmigrationFormFileInfo().get(0);
			immigrationCandidateForms.setFileUrl(fileInfo.getFileURL());
			immigrationCandidateForms.setFileName(fileInfo.getFileName());
			FileUploadUtil.moveFile(immigrationTemplateFormDTO.getStaticPath(), fileInfo, "immigrationCandidateForms");

		}
		immigrationCandidateForms.setFormCategory(immigrationTemplateFormDTO.getFormCategory());
		immigrationCandidateForms.setUpdatedBy(user);
		immigrationCandidateForms.setUpdatedDate(currentDate);
		immigrationCommonDao.saveImmigrationCandidateForms(immigrationCandidateForms);
		StringBuilder actionDesc = new StringBuilder();
		if (immigrationTemplateFormDTO.getImmigrationCandidateFormId() != null) {
			actionDesc.append("Form Details Updated").toString();
			immigrationActivityCommonUtil.saveImmigrationCandidateActivities(user, orgLocations, candidate, immigration,
					actionDesc.toString());
		} else {
			actionDesc.append("New Form Details Added").toString();
			immigrationActivityCommonUtil.saveImmigrationCandidateActivities(user, orgLocations, candidate, immigration,
					actionDesc.toString());

		}

		return immigrationCandidateForms;
	}

	@Override
	public Immigration getImmigrationByImmigrationId(Long immigrationId) throws Exception {
		// TODO Auto-generated method stub
		return immigrationCommonDao.getImmigrationByImmigrationId(immigrationId);
	}

	@Override
	public List<ImmigrationCandidateDocuments> getImmigrationCandidateDocs(Long immigrationId, Long templateId,
			Long candidateId, User user, OrgLocations orgLocations, String searchVal, String myImmigration) throws Exception {

		List<ImmigrationCandidateDocuments> immigrationCanididateDocList = immigrationCommonDao
				.getImmigrationCandidateDocs(immigrationId, templateId, candidateId, searchVal, myImmigration);

		return immigrationCanididateDocList;
	}

	@Override
	public List<ImmigrationCandidateForms> getCandidateImmigartionFormsDetails(Long candidateId, Long templateId,
			Long immigrationId, String searchVal, String myImmigration) {
		return immigrationCommonDao.getCandidateImmigartionFormsDetails(candidateId, templateId, immigrationId,
				searchVal, myImmigration);
	}

	@Override
	public ResponseList<ImmigrationListViewDTO> getImmigrationsList(ImmigrationListViewDTO immigrationListDTO,
			String searchValue, OrgLocations orgLocations, Map<String, Long[]> filterVal, String orgTypeVal)
			throws Exception {
		ResponseList<ImmigrationListViewDTO> immigartionList = immigrationCommonDao
				.getImmigrationsList(immigrationListDTO, searchValue, orgLocations, filterVal, orgTypeVal);

		return immigartionList;
	}

	@Override
	public String updateUscisTypeDropDown(Long immigrationId, Long uscisId, Long uscisOldId, User user,
			OrgLocations orgLocations) throws Exception {
		Immigration immigration = immigrationCommonDao.getImmigrationById(immigrationId);
		String returnVal;
		StringBuilder actionDesc = new StringBuilder();
		String actionOn = null;

		LookUpData lookUpdata = lookUpDataService.getTasksById(uscisId);
		LookUpData lookUpdataOld = lookUpDataService.getTasksById(uscisOldId);
		immigration.setCurrUscisStauts(lookUpdata);
		actionDesc.append("Changed Status").append(" ").append(lookUpdataOld.getName()).append(" ").append("to")
				.append(" ").append(lookUpdata.getName()).toString();

		returnVal = immigrationCommonDao.updateUscisTypeDropDown(immigration);

		immigrationActivityCommonUtil.saveImmigrationActivities(user, orgLocations, immigration, actionOn,
				actionDesc.toString());

		return returnVal;
	}

	@Override
	public ImmigrationDTO editImmigrationDetails(Long immigrationId, OrgLocations orgLocations) {

		Immigration immigration = immigrationCommonDao.getImmigrationById(immigrationId);
		ImmigrationDTO immigrationDTO = new ImmigrationDTO();
		try {
			/* ============================from immigration Table================= */
			immigrationDTO.setImmigrationId(immigration.getId().toString());
			immigrationDTO.setCandidateId(immigration.getCandidate().getId().toString());
			immigrationDTO.setCandidateNameEmail(immigration.getCandidate().getFullName()+"("+immigration.getCandidate().getEmail()+")");
			// check whether the template id own or not
			Integer count = immigrationCommonDao.getTableRecordCountByTemplateId("ImmigrationTemplate",
					immigration.getImmigrationTemplateId().getId(),
					" where id =:templateId and orgLocations.id=" + orgLocations.getId());
			if (count == null)// no record found for current user.
				immigrationDTO.setIsOwnTemplateId("N");
			else
				immigrationDTO.setIsOwnTemplateId("Y");

			immigrationDTO.setTemplateId(immigration.getImmigrationTemplateId().getId().toString());
			
			immigrationDTO.setTemplateName(immigration.getImmigrationTemplateId().getName());
			
			immigrationDTO.setPetitionType(immigration.getPetitionType().getId().toString());
			immigrationDTO.setOpenDate(
					commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), immigration.getOpenDate()));
			if (immigration.getCurrUscisStauts() != null) {
				immigrationDTO.setCurrUscisStatusId(immigration.getCurrUscisStauts().getId());
				immigrationDTO.setCurrUscisStatus(immigration.getCurrUscisStauts().getName());
			}
			if (immigration.getDateOfArrival() != null) {
				immigrationDTO.setArrivalDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
						immigration.getDateOfArrival()));
			}
			immigrationDTO.setEntryPlace(immigration.getPlaceOfEntry());
			// immigrationDTO.setTemplateId(immigration.getImmigrationTemplateId().getId().toString());
			immigrationDTO.setAttorneyId(immigration.getOrgRelations().getId().toString());
			immigrationDTO.setAttorneyContact(immigration.getOrgSpoc().getId().toString());
			immigrationDTO.setProcessType(immigration.getProcessType());
			immigrationDTO.setReceiptNo(immigration.getReceiptNo());
			immigrationDTO.setOrgloationId(immigration.getOrgLocationId().getId().toString());

			immigrationDTO.setOrgName(immigration.getOrgLocationId().getOrgLocationName());
			if (orgLocations.getId().equals(immigration.getOrgLocationId().getId())) {
				immigrationDTO.setAttorneyContactName(immigration.getOrgSpoc().getVendorUser().getFirstName());
			} else {
				immigrationDTO.setAttorneyContactName(immigration.getOrgSpoc().getClientUser().getFirstName());
			}
			/* ===============from Candidate Table============================= */
			immigrationDTO.setCandidateId(immigration.getCandidate().getId().toString());
			immigrationDTO.setFirstName(immigration.getCandidate().getFirstName());
			immigrationDTO.setMiddleName(immigration.getCandidate().getMiddelName());
			immigrationDTO.setLastName(immigration.getCandidate().getLastName());
			immigrationDTO.setGender(immigration.getCandidate().getGender());
			immigrationDTO.setEmail(immigration.getCandidate().getEmail());
			immigrationDTO.setMobile(immigration.getCandidate().getMobilePhone());
			immigrationDTO.setAlternateMobile(immigration.getCandidate().getAlternateMobilePhone());
			immigrationDTO.setBirthDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
					immigration.getCandidate().getDateOfBirth()));
			immigrationDTO.setBirthPlace(immigration.getCandidate().getPlaceOfBirth());
			immigrationDTO.setNationality(immigration.getCandidate().getNationality());
			immigrationDTO.setMaritalStatus(immigration.getCandidate().getMaritalStatus());
			immigrationDTO.setEadNumber(immigration.getCandidate().getEadNo());
			immigrationDTO.setI_20_number(immigration.getCandidate().getI20ServiceNo());
			immigrationDTO.setI_94_number(immigration.getCandidate().getI94No());
			immigrationDTO.setLocation(immigration.getCandidate().getLocation());
			immigrationDTO.setAddress(immigration.getCandidate().getAddress());
			immigrationDTO.setCity(immigration.getCandidate().getCity());
			immigrationDTO.setState(immigration.getCandidate().getState());
			immigrationDTO.setCountry(immigration.getCandidate().getCountry());
			immigrationDTO.setZipcode(immigration.getCandidate().getZipCode());
			immigrationDTO.setResidenceStartDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
					immigration.getCandidate().getResidenceStartDate()));
			immigrationDTO.setExperienceMonths(immigration.getCandidate().getExperienceMonths());
			immigrationDTO.setExperienceYears(immigration.getCandidate().getExperienceYears());
			/*
			 * ===============================from Candidate Identities
			 * Table==========================
			 */
			CandidateIdendities candidateIdendities = null;
			candidateIdendities = candidateDao.getCandidateIdentitiesNumberByCandidateId(
					immigration.getCandidate().getId(), Constants.ID_TYPE_SSN);
			immigrationDTO.setSsnNumber(candidateIdendities.getIdNumber());
			/*
			 * ===============================from Requirement
			 * Table==========================
			 */
			immigrationDTO.setJobId(immigration.getJob().getId().toString());
			immigrationDTO.setJobTitle(immigration.getJob().getJobTitle());
			/*
			 * ===============================from Lcasummary
			 * Table==========================
			 */
			
			/*CandidateLcaI94Summary lcaSummaryCurr = immigrationCommonDao
					.getCandidateLCADetails(immigration.getCandidate().getId());
			immigrationDTO.setLcaStartDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
					lcaSummaryCurr.getStartDate()));
			immigrationDTO.setLcaEndDate(
					commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), lcaSummaryCurr.getEndDate()));
			immigrationDTO.setLcaNumber(lcaSummaryCurr.getIdNumber());
			immigrationDTO.setIsCurrResidence(lcaSummaryCurr.getIsCurrent());
			immigrationDTO.setEmploymentLevel(lcaSummaryCurr.getEmploymentLevel());
			immigrationDTO.setEmployer(lcaSummaryCurr.getEmployerName());
			immigrationDTO.setJobLocation(lcaSummaryCurr.getEmployerLocation());
			immigrationDTO.setRateCurrency(lcaSummaryCurr.getRateCurrency());
			immigrationDTO.setRate(lcaSummaryCurr.getRate());
			immigrationDTO.setRateType(lcaSummaryCurr.getRateType());
			immigrationDTO.setJobStartDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
					lcaSummaryCurr.getJobStartDate()));
			immigrationDTO.setJobEndDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
					lcaSummaryCurr.getJobEndDate()));
			CandidateLcaI94Summary lcaSummaryPrev = immigrationCommonDao
					.getCandidateLCADetails(immigration.getCandidate().getId(), Constants.NO);
			immigrationDTO.setPrevLcaStartDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
					lcaSummaryPrev.getStartDate()));
			immigrationDTO.setPrevLcaEndDate(
					commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), lcaSummaryPrev.getEndDate()));
			immigrationDTO.setPrevLcaNumber(lcaSummaryPrev.getIdNumber());*/
			
			// Modified Code 
			CandidateLcaJobDetails canLcaJobDetails = immigrationCommonDao.getCandidateLcaJobDetails(immigration.getId());
			
			if(canLcaJobDetails != null) {
			immigrationDTO.setIsCurrResidence(canLcaJobDetails.getIsCurrent());
			
			immigrationDTO.setEmploymentLevel(canLcaJobDetails.getEmploymentLevel());
			immigrationDTO.setEmployer(canLcaJobDetails.getEmployerName());
			immigrationDTO.setJobLocation(canLcaJobDetails.getEmployerLocation());
			immigrationDTO.setRateCurrency(canLcaJobDetails.getRateCurrency());
			immigrationDTO.setRate(canLcaJobDetails.getRate());
			immigrationDTO.setRateType(canLcaJobDetails.getRateType());
			immigrationDTO.setJobStartDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),canLcaJobDetails.getJobStartDate()));
			immigrationDTO.setJobEndDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),canLcaJobDetails.getJobEndDate()));
			}
			
			
			/*
			 * LCA Details from LCA History Table
			 */
			List<LcaHistory> lhcurrList=immigrationCommonDao.getLcaHistoryDetails(immigration.getId(),Constants.YES);
			LcaHistory lhcurr  = null;
			if(lhcurrList != null && lhcurrList.size()>0) {
				lhcurr = lhcurrList.get(0);
			}
			
			if(lhcurr != null) {
				//currentLca info
				immigrationDTO.setLcaStartDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),lhcurr.getLcaId().getStartDate()));
				immigrationDTO.setLcaEndDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),lhcurr.getLcaId().getEndDate()));
				immigrationDTO.setLcaNumber(lhcurr.getLcaId().getLcaNo());
				
				List<LcaPlaceOfEployment>  locList = immigrationCommonDao.getLcaPlaceOfEploymentListById(lhcurr.getLcaId().getId());
				LcaPlaceOfEployment lcaPlaceOfEployment = locList.get(0);
				lhcurr.setLcaLocations(locList);
				immigrationDTO.setLcaCurrLocation(lcaPlaceOfEployment.getLocation());
				
			}
			
			List<LcaHistory> lhPreList=immigrationCommonDao.getLcaHistoryDetails(immigration.getId(),Constants.NO);
			
			LcaHistory lhPre = null;
			if(lhPreList != null && lhPreList.size()>0) {
				lhPre = lhPreList.get(0);
			}
			if(lhPre != null) {
				//preLca info
				immigrationDTO.setPrevLcaStartDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),lhPre.getLcaId().getStartDate()));
				immigrationDTO.setPrevLcaEndDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),lhPre.getLcaId().getEndDate()));
				immigrationDTO.setPrevLcaNumber(lhPre.getLcaId().getLcaNo());
				
			}
			
			/*
			 * ==============================from CandidateResumes
			 * Table==================================
			 */
			CandidateResumes resumes = candidateResumesDao
					.getCandidateResumeByCandidateId(immigration.getCandidate().getId());
			if (resumes != null) {
				immigrationDTO.setFileName(resumes.getFileName());
				immigrationDTO.setFileUrl(resumes.getFileURL());
			}
			/*
			 * =============================from CandidateResidence
			 * Table==============================
			 */
			List<CandidateResidence> candidateResidences = immigrationCommonDao
					.getCandidatesResidenceListByCandidateIdAndOrgLocationID(immigration.getCandidate().getId(), "");
			if (!candidateResidences.isEmpty()) {
				List<ImmigrationResidenceDTO> residenceList = new ArrayList<ImmigrationResidenceDTO>();
				for (CandidateResidence residence : candidateResidences) {
					ImmigrationResidenceDTO residenceDTO = new ImmigrationResidenceDTO();
					residenceDTO.setId(residence.getId().toString());
					residenceDTO.setAddress(residence.getStreetAddress());
					residenceDTO.setCity(residence.getCity());
					residenceDTO.setCountry(residence.getCountry());
					residenceDTO.setLocation(residence.getLocation());
					residenceDTO.setState(residence.getState());
					residenceDTO.setZipcode(residence.getZipcode());
					residenceDTO.setIsCurrResidence(residence.getIsCurrAddress());
					residenceDTO.setResidenceStartDate(commonUtil
							.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), residence.getStartDate()));
					residenceDTO.setResidenceEndDate(commonUtil
							.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), residence.getEndDate()));
					residenceDTO.setIsCurrResidence(residence.getIsCurrAddress());
					residenceList.add(residenceDTO);
				}

				immigrationDTO.setResidenceList(residenceList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return immigrationDTO;
	}

	@Override
	public ImmigrationDashboardView getImmigrationDashBoardDetails(OrgLocations orgLocations) {
		// TODO Auto-generated method stub
		return immigrationCommonDao.getImmigrationDashBoardDetails(orgLocations);

	}

	@Override
	public List<ImmigrationCandidateDocumentsHistory> getImmigrationCandidateDocumentHistoryList(Long immigrationId,
			Long candidateId, Long immigrationCanDocid) throws Exception {

		List<ImmigrationCandidateDocumentsHistory> immigrationCanididateDocHistoryList = immigrationCommonDao
				.getImmigrationCandidateDocumentHistoryList(immigrationId, candidateId, immigrationCanDocid);

		return immigrationCanididateDocHistoryList;
	}

	@Override
	public String deleteDocumentHistoryRecordById(Long id) {
		String DeleteFileUrl = immigrationCommonDao.deleteDocumentHistoryRecordById(id);
		try {
			String rootPath = StringUtils.removeEnd(staticPath, staticPath.substring(staticPath.lastIndexOf("/")));
			File file = new File(rootPath + DeleteFileUrl);

			if (file.delete()) {
				logger.debug(file.getName() + " is deleted!");
			} else {
				logger.error(file.getName() + " is deleted!");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	@Override
	public List<ImmigrationCandidateDocumentsHistory> getImmigrationCandidateDocumentHistoryListByImmigrationId(
			Long immigrationId, Long docId) throws Exception {

		List<ImmigrationCandidateDocumentsHistory> immigrationCanididateDocHistoryList = immigrationCommonDao
				.getImmigrationCandidateDocumentHistoryListByImmigrationId(immigrationId, docId);

		return immigrationCanididateDocHistoryList;
	}

	@Override
	public String deleteImmigrationDetailsById(Long immigrationId) {
		return immigrationCommonDao.deleteImmigrationDetailsById(immigrationId);
	}

	@Override
	public List<ImmigrationCandidateForms> getImmigrationCandidateFormsByImmigrationId(Long immigrationId) {
		return immigrationCommonDao.getImmigrationCandidateFormsByImmigrationId(immigrationId);
	}

	@Override
	public Integer getTableRecordCountByCandidateId(String entityName, Long candidateId, String condition) {
		// TODO Auto-generated method stub
		return immigrationCommonDao.getTableRecordCountByCandidateId(entityName, candidateId, condition);
	}

	@Override
	public Immigration getImmigrationById(Long immigrationId) {
		return immigrationCommonDao.getImmigrationById(immigrationId);
	}

	@Override
	public Immigration updateImigrationDetatils(Immigration immigration) {
		return immigrationCommonDao.updateImigrationDetatils(immigration);
	}

	@Override
	public void sendEmailToImmigrationBeneficiary(Immigration immigration, User user, OrgLocations orgLocations)
			throws Exception {

		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(immigration.getCandidate().getFirstName());
		emailMessage.setLastName(immigration.getCandidate().getLastName());
		emailMessage.setToEmail(immigration.getCandidate().getEmail());
		emailMessage.setFromEmail(user.getEmail());
		emailMessage.setCreatedBy(user.getUserCode());
		// StringBuilder msgBuild = new StringBuilder();
		// emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append("
		// ").append(loginUser.getLastName()).toString());
		// emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		if (immigration.getGeneratedId() != null && !"".equalsIgnoreCase(immigration.getGeneratedId())) {
			globalVarsMap.put("immigrationId", immigration.getGeneratedId());
		}
		if (immigration.getPetitionType().getName() != null
				&& !"".equalsIgnoreCase(immigration.getPetitionType().getName())) {
			globalVarsMap.put("petitionType", immigration.getPetitionType().getName());
		}
		if (user.getFullName() != null && !"".equalsIgnoreCase(user.getFullName())) {
			globalVarsMap.put("immigrationManagerName", user.getFullName());
		}
		if (user.getEmail() != null && !"".equalsIgnoreCase(user.getEmail())) {
			globalVarsMap.put("immigrationManagerEmail", user.getEmail());
		}
		if (immigration.getCandidate().getFullName() != null
				&& !"".equalsIgnoreCase(immigration.getCandidate().getFullName())) {
			globalVarsMap.put("beneficiaryName", immigration.getCandidate().getFullName());
		}
		StringBuilder loginBuilder = new StringBuilder();
		globalVarsMap.put("immigrationLoginUrl", loginBuilder.append("<a href='").append(sourceleadLoginUrl).append(
				"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>")
				.toString());

		// getting the current EmailScenario by EmailScenario unique name and entity
		// type
		// using same migration which has been already used in
		// updatedRequirementMailToManager method
		// EmailScenario notificationScenario =
		// emailScenarioService.getEmailScenarioByUniqueName(Constants.PASSWORD_RESET_MAIL_TO_USER,Constants.ENTITY_TYPE_NOTIFICATION);

		// String notificationbody =
		// emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
		// notificationScenario.setBody(notificationbody);

		// calling the sendEmail method of emailScenerioUtil
		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, orgLocations.getOrg(), orgLocations,
				Constants.EMAIL_TO_IMMIGRATION_BENEFICIARY, new ArrayList<EmailAttachment>());
		
		EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
				Constants.IMMIGRATION_NOTIFICATION_TO_BENEFICIARY, Constants.ENTITY_TYPE_NOTIFICATION);
		String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap, notificationScenario);
		notificationScenario.setBody(notificationBody);
		StringBuilder actionLinkBuilder = new StringBuilder();
		String actionLink = actionLinkBuilder.append("").append(vmsUrl).append("/").append("immigration/viewImmigration?immigrationUUID="+immigration.getImmigrationUuid()+"&candidateUUID="+immigration.getCandidate().getCandidateUUID()+"&immiTemplateUUID="+immigration.getImmigrationTemplateId().getUuid()+"&myImmigrationPage=Y").toString();
		emailScenerioUtil.sendNotification(actionLink, user, immigration.getCandidateUserId(), orgLocations,notificationScenario,new HashMap<String,String>());

		// String actionLink="";
		// emailScenerioUtil.sendNotification(actionLink, user, user, orgLocations,
		// notificationScenario,new HashMap<String,String>());
	}

	@Override
	public ResponseList<MyImmigrationViewDTO> getMyImmigrationsList(MyImmigrationViewDTO immigrationListDTO,
			OrgLocations orgLocations, Long userId) throws Exception {
		ResponseList<MyImmigrationViewDTO> immigartionList = immigrationCommonDao
				.getMyImmigrationsList(immigrationListDTO, orgLocations, userId);
		return immigartionList;
	}

	@Override
	public List<ImmigrationActivityHistory> getImmigrationActivitiesList(Long immigrationId, Long orglocationId)
			throws Exception {
		List<ImmigrationActivityHistory> activityHistoryList = immigrationCommonDao
				.getImmigrationActivitiesList(immigrationId, orglocationId);
		for (ImmigrationActivityHistory activityHistory : activityHistoryList) {
			activityHistory.setActionDesc(activityHistory.getActionDesc());
			activityHistory.setActionBy(activityHistory.getActionBy());
			// date time format conversion.
			long diff = commonUtil.currentDate().getTime() - activityHistory.getCreatedDate().getTime();
			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			StringBuilder timeBuilder = new StringBuilder();
			if (diffDays == 0 && diffHours == 0 && diffMinutes == 0) {
				activityHistory.setCreatedDateString(timeBuilder.append(diffSeconds).append("seconds ago").toString());
			} else if (diffDays == 0 && diffHours == 0) {
				activityHistory.setCreatedDateString(timeBuilder.append(diffMinutes).append(" minutes ago").toString());
			} else if (diffDays == 0) {
				activityHistory.setCreatedDateString(timeBuilder.append(diffHours).append(" hours ").append(diffMinutes)
						.append(" minutes ago").toString());
			} else if (diffDays < 7) {
				activityHistory.setCreatedDateString(timeBuilder.append(diffDays).append(" days ").append(diffHours)
						.append(" hours ago").toString());
			} else {
				SimpleDateFormat standardFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aa");
				String commonDateFormat = standardFormat.format(activityHistory.getCreatedDate());
				activityHistory.setCreatedDateString(commonDateFormat.toString());
			}
		}
		return activityHistoryList;
	}

	@Override
	public Integer getTableRecordCountByImmigrationId(String entityName, Long immigrationId, String condition) {
		return immigrationCommonDao.getTableRecordCountByImmigrationId(entityName, immigrationId, condition);
	}

	//visa
	@Override
	public Boolean validateCandidateVisa(Long candidateId,String idNumber) throws Exception {
		return immigrationCommonDao.validateCandidateVisa(candidateId,idNumber);
	}
	
	//passport
	@Override
	public Boolean validateCandidatePassport(Long candidateId,String idNumber) throws Exception {
		return immigrationCommonDao.validateCandidatePassport(candidateId,idNumber);
	}

	
	@Override
	public CandidateVisa getCandidateVisaDetailsByIdNumberAndId(String idNumber,
			Long candidateId, Long id) throws Exception {
		return immigrationCommonDao.getCandidateVisaDetailsByIdNumberAndId(idNumber, candidateId, id);
	}

	
	@Override
	public CandidatePassport getCandidatePassportDetailsByIdNumberAndId(String idNumber,
			Long candidateId, Long id) throws Exception {
		return immigrationCommonDao.getCandidatePassportDetailsByIdNumberAndId(idNumber, candidateId, id);
	}

	@Override
	public Long getImmigrationIdByUUID(String immigrationUUID) {
		return immigrationCommonDao.getImmigrationIdByUUID(immigrationUUID);
	}

	@Override
	public Long getCandidateIdByUUID(String candidateUUID) {
		return immigrationCommonDao.getCandidateIdByUUID(candidateUUID);
	}

	@Override
	public ResponseList<ImmigrationListViewDTO> getImmigrationsListByCountsLink(
			ImmigrationListViewDTO immigrationListDTO, String searchValue, OrgLocations orgLocations) throws Exception {

		return immigrationCommonDao.getImmigrationsListByCountsLink(immigrationListDTO, searchValue, orgLocations);
	}

	@Override
	public ImmigrationCandidateForms getImmigrationCandidateFormsDetailsByFormNameAndId(String formName,
			Long candidateId, Long immigrationId, Long id) throws Exception {
		return immigrationCommonDao.getImmigrationCandidateFormsDetailsByFormNameAndId(formName, candidateId,
				immigrationId, id);
	}

	@Override
	public Boolean validateImmigrationCandidateForms(String formName, Long candidateId, Long immigrationId)
			throws Exception {
		return immigrationCommonDao.validateImmigrationCandidateForms(formName, candidateId, immigrationId);

	}

	@Override
	public Sheet getExcelFile(Sheet createExcelsheet, Workbook createExcelworkbook, OrgLocations orgLocations) {
		try {
			Row writeRow = null;
			ImmigrationDashboardView immDashboardView = immigrationCommonDao
					.getImmigrationDashBoardDetails(orgLocations);
			createExcelsheet = getExcelSheetWithHeader(createExcelsheet, writeRow, createExcelworkbook,
					immDashboardView);
			List<ImmigrationListExportView> immigrationListExportView = immigrationCommonDao
					.getImmigrationListExportDetails(orgLocations);
			int column = 0;
			Date toDayDate = new Date();
			for (ImmigrationListExportView exportView : immigrationListExportView) {
				
				//Setting visa status based on visaValidTo date.
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String currentDateStr = dateFormat.format(toDayDate);
				Date newDate = dateFormat.parse(currentDateStr);
				Date currentDate = commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), newDate);
				if (exportView.getVisaValidToDate() != null &&
						currentDate.before(exportView.getVisaValidToDate())) {
					exportView.setVisaStatus("Active");
				} else {
					exportView.setVisaStatus("Expired");
				}
				
				writeRow = createExcelsheet.createRow(column + 15);
				createExcelsheet = createImmigrationListXlsSheet(createExcelsheet, writeRow, exportView, orgLocations);
				column++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return createExcelsheet;
	}

	private Sheet createImmigrationListXlsSheet(Sheet createExcelsheet, Row writeRow,
			ImmigrationListExportView exportView, OrgLocations orgLocations) {
		if(exportView.getGeneratedId() !=null){
			 writeRow.createCell(0).setCellValue(exportView.getGeneratedId()); 
		}
		if(exportView.getCountry() != null) {
			writeRow.createCell(1).setCellValue(exportView.getCountry());
		}
		if(exportView.getFirstName() !=null){
			 writeRow.createCell(2).setCellValue(exportView.getFirstName()); 
		}
		if(exportView.getLastName() !=null){
			 writeRow.createCell(3).setCellValue(exportView.getLastName()); 
		}
		if(exportView.getEmail() !=null){
			 writeRow.createCell(4).setCellValue(exportView.getEmail()); 
		}
		if(exportView.getPhone() !=null){
			 writeRow.createCell(5).setCellValue(exportView.getPhone()); 
		}
		
		String totalExperience = null;
		String experienceMonths = null;
		String experienceYears = null;
		if(StringUtils.isNotBlank(exportView.getExperienceYears())){
			experienceYears = exportView.getExperienceYears() +" years ";
		}else {
			experienceYears = " 0 years ";
		}
		if(StringUtils.isNotBlank(exportView.getExperienceMonths())){
			experienceMonths = exportView.getExperienceMonths() +" months";
		}else {
			experienceMonths = " 0 months";
		}
		totalExperience = experienceYears+experienceMonths;
		writeRow.createCell(6).setCellValue(totalExperience);
		if(exportView.getSsnNumber() !=null){
			 writeRow.createCell(7).setCellValue(exportView.getSsnNumber()); 
		}
		if(exportView.getI94No() !=null){
			 writeRow.createCell(8).setCellValue(exportView.getI94No()); 
		}
		if(exportView.getI20SevisNo() !=null){
			 writeRow.createCell(9).setCellValue(exportView.getI20SevisNo()); 
		}
		if(exportView.getEadNo() !=null){
			 writeRow.createCell(10).setCellValue(exportView.getEadNo()); 
		}
		if(exportView.getDateOfBirth() !=null){
			 writeRow.createCell(11).setCellValue(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
					 exportView.getDateOfBirth())); 
		}
		if(exportView.getMaritalStatus() !=null){
			 writeRow.createCell(12).setCellValue(exportView.getMaritalStatus()); 
		}
		if(exportView.getPetitionType() !=null){
			 writeRow.createCell(13).setCellValue(exportView.getPetitionType()); 
		}
		if(exportView.getTemplateName() !=null){
			 writeRow.createCell(14).setCellValue(exportView.getTemplateName()); 
		}
		if(exportView.getArrivalDate() !=null){
			 writeRow.createCell(15).setCellValue(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
					 exportView.getArrivalDate())); 
		}
		if(exportView.getOpenDate() !=null){
			 writeRow.createCell(16).setCellValue(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
					 exportView.getOpenDate())); 
		}
		if(exportView.getCurrUscisStatus() !=null){
			 writeRow.createCell(17).setCellValue(exportView.getCurrUscisStatus()); 
		}
		if(exportView.getVisaIdNumber() !=null){
			 writeRow.createCell(18).setCellValue(exportView.getVisaIdNumber()); 
		}
		if(exportView.getVisaValidFromDate() !=null){
			 writeRow.createCell(19).setCellValue(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
					 exportView.getVisaValidFromDate())); 
		}
		
		if(exportView.getVisaValidToDate() !=null){
			 writeRow.createCell(20).setCellValue(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
					 exportView.getVisaValidToDate())); 
		}
		
		if(exportView.getVisaValidToDate() != null && exportView.getVisaStatus() != null ) {
			 writeRow.createCell(21).setCellValue(exportView.getVisaStatus());
		}
		
		 if(exportView.getOrgLocationId().equals(orgLocations.getId())){
             writeRow.createCell(22).setCellValue(exportView.getVendor()); 
             writeRow.createCell(23).setCellValue(exportView.getSpocNameVendor()); 
             writeRow.createCell(24).setCellValue(exportView.getSpocEmailVendor()); 
        }else {
             writeRow.createCell(22).setCellValue(exportView.getClient());
             writeRow.createCell(23).setCellValue(exportView.getSpocNameClient()); 
             writeRow.createCell(24).setCellValue(exportView.getSpocEmailClient());
        }
		if(exportView.getReceiptNo() !=null){
			 writeRow.createCell(25).setCellValue(exportView.getReceiptNo()); 
		}
		if(exportView.getLocation() !=null){
			 writeRow.createCell(26).setCellValue(exportView.getLocation()); 
		}
		if(exportView.getLcaStartDate() !=null){
			 writeRow.createCell(27).setCellValue(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
					 exportView.getLcaStartDate())); 
		}
		if(exportView.getLcaEndDate() !=null){
			 writeRow.createCell(28).setCellValue(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
					 exportView.getLcaEndDate())); 
		}
		if(exportView.getLcaNumber() !=null){
			 writeRow.createCell(29).setCellValue(exportView.getLcaNumber()); 
		}
		if(exportView.getLcaRate() !=null){
			 writeRow.createCell(30).setCellValue(exportView.getLcaRate()); 
		}
		if(exportView.getEmployerLocation() !=null){
			 writeRow.createCell(31).setCellValue(exportView.getEmployerLocation()); 
		}
		
		if(exportView.getLcaTitle() !=null){
			 writeRow.createCell(32).setCellValue(exportView.getLcaTitle()); 
		}
		
		if(exportView.getJobName() !=null){
			 writeRow.createCell(33).setCellValue(exportView.getJobName()); 
		}
		if(exportView.getJobId() !=null){
			 writeRow.createCell(34).setCellValue(exportView.getJobId()); 
		}
		// dont remove
		if(exportView.getPreviousLcaStartDate() !=null){
			 writeRow.createCell(35).setCellValue(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
					 exportView.getPreviousLcaStartDate())); 
		}
		if(exportView.getPreviousLcaEndDate() !=null){
			 writeRow.createCell(36).setCellValue(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
					 exportView.getPreviousLcaEndDate())); 
		}
		
		if(exportView.getPreviousLcaRate() !=null){
			 writeRow.createCell(37).setCellValue(exportView.getPreviousLcaRate()); 
		}
		
		/*if(exportView.getPreviousLcaNumber() !=null){
			 writeRow.createCell(38).setCellValue(exportView.getPreviousLcaNumber()); 
		}*/
		
		/*if(exportView.getPreviousEmployerLocation() !=null){
			 writeRow.createCell(39).setCellValue(exportView.getPreviousEmployerLocation()); 
		}*/
		return createExcelsheet;
	}

	private Sheet getExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook,
			ImmigrationDashboardView immDashboardView) {
		
		CellStyle style = createExcelworkbook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		Font font = createExcelworkbook.createFont();
		font.setColor(IndexedColors.BLACK.getIndex());
		style.setFont(font);
		
		BigDecimal totlaCount = immDashboardView.getDraftsCount().add(immDashboardView.getReqSentCount()).add(immDashboardView.getInProgressCount()).add(immDashboardView.getApporovedCount()).add(immDashboardView.getRejectedCount()).add(immDashboardView.getRfeCount()).add(immDashboardView.getDeniedCount()).add(immDashboardView.getOthersCount());
				

		/*========= Draft Count in Excel ================*/
		writeRow = createExcelsheet.createRow(0);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);
		
		Cell cell0 = writeRow.createCell(0);
		cell0.setCellValue("Draft :");
		cell0.setCellStyle(style);

		Cell cell01 = writeRow.createCell(1);
		cell01.setCellValue(immDashboardView.getDraftsCount().toString());
		cell01.setCellStyle(style);
		
		/*========= Sent Request Count in Excel ================*/
		writeRow = createExcelsheet.createRow(1);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell1 = writeRow.createCell(0);
		cell1.setCellValue("Sent Request :");
		cell1.setCellStyle(style);

		Cell cell2 = writeRow.createCell(1);
		cell2.setCellValue(immDashboardView.getReqSentCount().toString());
		cell2.setCellStyle(style);

		/*========= In Progress Count in Excel ================*/
		writeRow = createExcelsheet.createRow(2);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell3 = writeRow.createCell(0);
		cell3.setCellValue("In Progress :");
		cell3.setCellStyle(style);

		Cell cell4 = writeRow.createCell(1);
		cell4.setCellValue(immDashboardView.getInProgressCount().toString());
		cell4.setCellStyle(style);

		/*========= Approved Count in Excel ================*/
		writeRow = createExcelsheet.createRow(3);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell5 = writeRow.createCell(0);
		cell5.setCellValue("Approved : ");
		cell5.setCellStyle(style);

		Cell cell6 = writeRow.createCell(1);
		cell6.setCellValue(immDashboardView.getApporovedCount().toString());
		cell6.setCellStyle(style);
		
		/*========= Approved Count in Excel ================*/
		writeRow = createExcelsheet.createRow(4);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell7 = writeRow.createCell(0);
		cell7.setCellValue("Rejected : ");
		cell7.setCellStyle(style);

		Cell cell8 = writeRow.createCell(1);
		cell8.setCellValue(immDashboardView.getRejectedCount().toString());
		cell8.setCellStyle(style);
		
		/*========= RFE Count in Excel ================*/
		writeRow = createExcelsheet.createRow(5);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell9 = writeRow.createCell(0);
		cell9.setCellValue("RFE : ");
		cell9.setCellStyle(style);

		Cell cell10 = writeRow.createCell(1);
		cell10.setCellValue(immDashboardView.getRfeCount().toString());
		cell10.setCellStyle(style);
		
		/*========= Denied Count in Excel ================*/
		writeRow = createExcelsheet.createRow(6);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell11 = writeRow.createCell(0);
		cell11.setCellValue("Denied : ");
		cell11.setCellStyle(style);

		Cell cell12 = writeRow.createCell(1);
		cell12.setCellValue(immDashboardView.getDeniedCount().toString());
		cell12.setCellStyle(style);
		
		/*========= Others Count in Excel ================*/
		writeRow = createExcelsheet.createRow(7);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell13 = writeRow.createCell(0);
		cell13.setCellValue("Others : ");
		cell13.setCellStyle(style);

		Cell cell14 = writeRow.createCell(1);
		cell14.setCellValue(immDashboardView.getOthersCount().toString());
		cell14.setCellStyle(style);
		
		/*========= Total Count in Excel ================*/
		writeRow = createExcelsheet.createRow(9);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell15 = writeRow.createCell(0);
		cell15.setCellValue("Total : ");
		cell15.setCellStyle(style);

		Cell cell16 = writeRow.createCell(1);
		cell16.setCellValue(totlaCount.toString());
		cell16.setCellStyle(style);
		
		String headers [] = Constants.IMMIGRATION_LIST_EXCEL_HEADERS;
		return createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet,style,headers,14);
	}

	@Override
	public Long getTemplateDetailsByNameAndOrgLoacationId(String name, OrgLocations orgLocations) {
		return immigrationCommonDao.getTemplateDetailsByNameAndOrgLoacationId(name, orgLocations);
	}

	@Override
	public List<Immigration> getImmigrationsByOrgId(Long id) {
		return immigrationCommonDao.getImmigrationsByOrgId(id);
	}
	
	/*
	 * LCA Code Starts Here
	 */
	
	@Override
	public Lca saveLcaDetails(LcaDTO lcaDTO, User user, OrgLocations orgLocations) {
		Lca lcaObj = null;
		try {
             
			Lca lca = null;
			if(StringUtils.isNotBlank(lcaDTO.getLcaId())) {
				lca =immigrationCommonDao.getLcaById(Long.parseLong(lcaDTO.getLcaId()));
			}else {
				lca = new Lca();
			}
			lca = saveLcaDetailsInfo(lcaDTO,user,orgLocations);
			
			// Lca Place Of employment
			
			List<LcaPlaceOfEployment> lcaPlaceOfEploymentList = new ArrayList<LcaPlaceOfEployment>(); 
			lcaPlaceOfEploymentList = saveLcaPlaceOfEploymentList(lcaDTO,lca, user, orgLocations);
			
			// Lca Attachments 
			List<LcaDocumentHistory> lcaDocumentList = new ArrayList<LcaDocumentHistory>(); 
			lcaDocumentList = saveLcaDocumentHistoryList(lcaDTO, lca, user, orgLocations);
			lcaObj = immigrationCommonDao.saveLcaDetails(lca,lcaPlaceOfEploymentList,lcaDocumentList);
			
			copyLcaVisaClarificationsToVisaClarifications(lcaObj,lcaDTO, user, orgLocations);
			
			
			StringBuilder actionDesc = new StringBuilder();
			if (StringUtils.isNotBlank(lcaDTO.getLcaId())) {
				actionDesc.append("LCA Details Edited").toString();
			} else {
				actionDesc.append("New LCA Added").toString();
			}
	      // lcaActivityCommonUtil.saveLcaActivities(user, orgLocations, lcaObj, null, actionDesc.toString());

		} catch (Exception e) {
			logger.error("Exception caugth in savePetitionDetails service" + e.getMessage());
			e.printStackTrace();
		}

		return lcaObj;
	}
	
	

	private List<LcaPlaceOfEployment> saveLcaPlaceOfEploymentList(LcaDTO lcaDTO, Lca lca ,User user, OrgLocations orgLocations) {
		
		List<LcaPlaceOfEployment> lcaPlaceOfEploymentList = new ArrayList<LcaPlaceOfEployment>(); // add LcaPlaceOfEployment to LCA
		Date currentDate = commonUtil.orgLocationCommonDate(new Date(), orgLocations);
		
		if (lcaDTO.getLcaPlacaOfempDeletedListIds() !=null && lcaDTO.getLcaPlacaOfempDeletedListIds().length>0) {
			immigrationCommonDao.deleteLcaPlaceOfEmpmntDetails(lcaDTO.getLcaPlacaOfempDeletedListIds());
        }
		
		if (lcaDTO.getLcaPlaceOfEmploymentList() != null) {
			
		List<LcaPlaceOfEmploymentDTO> lcaPlaceOfEmploymentDtoLst = lcaDTO.getLcaPlaceOfEmploymentList();
		
		for (LcaPlaceOfEmploymentDTO lcaPlaceOfEmploymentDTO : lcaPlaceOfEmploymentDtoLst) {
			  if (StringUtils.isEmpty(lcaPlaceOfEmploymentDTO.getLocation()))
				continue; // skip the empty PlaceOfEmployment rows based on one of the required field value
		     LcaPlaceOfEployment lcaPlaceOfEploymentEntity = null;
		
		if (StringUtils.isNotBlank(lcaPlaceOfEmploymentDTO.getPlsempmntId())) {
			lcaPlaceOfEploymentEntity = immigrationCommonDao.getLcaPlaceOfEploymentDetailsById(Long.parseLong(lcaPlaceOfEmploymentDTO.getPlsempmntId()));
			
		}else {
			lcaPlaceOfEploymentEntity = new LcaPlaceOfEployment();
			lcaPlaceOfEploymentEntity.setCreatedDate(currentDate);
			lcaPlaceOfEploymentEntity.setCreatedBy(user);
		}
		
		lcaPlaceOfEploymentEntity.setLcaId(lca);
		
		lcaPlaceOfEploymentEntity.setLocation(lcaPlaceOfEmploymentDTO.getLocation());
		lcaPlaceOfEploymentEntity.setAddressLine1(lcaPlaceOfEmploymentDTO.getAddressLine1());
		
		if(lcaPlaceOfEmploymentDTO.getAddressLine2() != null && !lcaPlaceOfEmploymentDTO.getAddressLine2().isEmpty()) {
		lcaPlaceOfEploymentEntity.setAddressLine2(lcaPlaceOfEmploymentDTO.getAddressLine2());
		}
		lcaPlaceOfEploymentEntity.setCity(lcaPlaceOfEmploymentDTO.getCity());
		lcaPlaceOfEploymentEntity.setState(lcaPlaceOfEmploymentDTO.getState());
		lcaPlaceOfEploymentEntity.setCountry(lcaPlaceOfEmploymentDTO.getCountry());
		
		if(lcaPlaceOfEmploymentDTO.getZipCode()!= null && !lcaPlaceOfEmploymentDTO.getZipCode().isEmpty()) {
		lcaPlaceOfEploymentEntity.setZipCode(lcaPlaceOfEmploymentDTO.getZipCode());
		}
		// default No for multiple residences treated as secondary residences.
		lcaPlaceOfEploymentEntity.setStatus(Constants.RECORD_ACTIVE);
		
		
		lcaPlaceOfEploymentEntity.setUpdatedDate(currentDate);
		
		lcaPlaceOfEploymentEntity.setUpdatedBy(user);
		lcaPlaceOfEploymentList.add(lcaPlaceOfEploymentEntity);
		}
		
		}
      return lcaPlaceOfEploymentList;
	}
	
	
	//Lca Attachments
	
private List<LcaDocumentHistory> saveLcaDocumentHistoryList(LcaDTO lcaDTO, Lca lca ,User user, OrgLocations orgLocations) throws IOException {
		
		List<LcaDocumentHistory> lcaDocumentList = new ArrayList<LcaDocumentHistory>(); // add residences to immigration
		Date currentDate = commonUtil.orgLocationCommonDate(new Date(), orgLocations);
		
		if (lcaDTO.getLcaDocumentListIds() !=null && lcaDTO.getLcaDocumentListIds().length>0) {
			immigrationCommonDao.deleteLcaDocumentsDetails(lcaDTO.getLcaDocumentListIds());
        }
		
		if (lcaDTO.getLcaDocumentList() != null) {
			
		List<LcaDocumentHistoryDTO> lcaDocumentDtoList = lcaDTO.getLcaDocumentList();
		
		for (LcaDocumentHistoryDTO lcaDocumentDTO : lcaDocumentDtoList) {
			 if (StringUtils.isEmpty(lcaDocumentDTO.getDocumentTitle()))
				continue; // skip the empty LcaDocumentHistory rows based on one of the required field value
		  LcaDocumentHistory lcaDocumentHistoryEntity = null;
		if (StringUtils.isNotBlank(lcaDocumentDTO.getDocId())) {
			lcaDocumentHistoryEntity = immigrationCommonDao.getLcaDocumentHistoryDetailsById(Long.parseLong(lcaDocumentDTO.getDocId()),lca.getId());
			
		}else {
			lcaDocumentHistoryEntity = new LcaDocumentHistory();
			lcaDocumentHistoryEntity.setCreatedDate(currentDate);
			lcaDocumentHistoryEntity.setCreatedBy(user);
		}
		
		lcaDocumentHistoryEntity.setLcaId(lca);
		
		if(lcaDocumentDTO.getDocumentTitle() != null && !lcaDocumentDTO.getDocumentTitle().isEmpty()) {
		lcaDocumentHistoryEntity.setDocumentTitle(lcaDocumentDTO.getDocumentTitle());
		}
		lcaDocumentHistoryEntity.setDocumentUUID(lcaDocumentDTO.getDocumentUUID());
		
		
		lcaDocumentHistoryEntity.setDescription(lcaDocumentDTO.getDescription());
	
		lcaDocumentDTO.setStaticPath(staticPath);
		if (lcaDocumentDTO.getFileInfo() != null) {
		FileInfo fileInfo = lcaDocumentDTO.getFileInfo().get(0);
		if(fileInfo.getFileName() != null) {
			if (fileInfo != null) {
				lcaDocumentHistoryEntity.setFileName(fileInfo.getFileName());
				lcaDocumentHistoryEntity.setFileUrl(fileInfo.getFileURL());
				lcaDocumentHistoryEntity.setFileContentType(fileInfo.getContentType());
             try {
                 FileUploadUtil.moveFile(lcaDocumentDTO.getStaticPath(), fileInfo, "LCADocuments");
             } catch (java.io.IOException e) {
                 // TODO Auto-generated catch block
                 e.printStackTrace();
             }
         }
		}
			 
		}
		lcaDocumentHistoryEntity.setStatus(Constants.RECORD_ACTIVE);
		lcaDocumentHistoryEntity.setUpdatedDate(currentDate);
		
		lcaDocumentHistoryEntity.setUpdatedBy(user);
		lcaDocumentList.add(lcaDocumentHistoryEntity);
		}
		
		}
      return lcaDocumentList;
	}

	

	private void copyLcaVisaClarificationsToVisaClarifications(Lca lcaObj,LcaDTO lcaDTO, User user, OrgLocations orgLocations) {
		
		
		
		   LcaVisaClarifications lcaVisaClfsObj = null;
		   Date currentDate = commonUtil.orgLocationCommonDate(new Date(), orgLocations);
		  
		   if(StringUtils.isNotBlank(lcaDTO.getVisaClsfId())) {
		    lcaVisaClfsObj= immigrationCommonDao.getLcaVisaClarificationsDetailsById(Long.parseLong(lcaDTO.getVisaClsfId()),lcaObj.getId());
		   }else {
		    lcaVisaClfsObj = new LcaVisaClarifications();
		    lcaVisaClfsObj.setCreatedBy(user);
		    lcaVisaClfsObj.setCreatedDate(currentDate);
		   }
		    lcaVisaClfsObj.setLcaId(lcaObj);
		    
		    if(lcaDTO.getNewEmployment() != null && !lcaDTO.getNewEmployment().isEmpty()) {
		    lcaVisaClfsObj.setNewEmployment(lcaDTO.getNewEmployment());
		    lcaVisaClfsObj.setNewEmploymentCount(Integer.parseInt(lcaDTO.getNewEmploymentCount()));
		    }else {
		    lcaVisaClfsObj.setNewEmployment(null);
			lcaVisaClfsObj.setNewEmploymentCount(null);	
		    }
		    
		    if(lcaDTO.getChangeInEmployer() != null && !lcaDTO.getChangeInEmployer().isEmpty()) {
		    lcaVisaClfsObj.setChangeInEmployer(lcaDTO.getChangeInEmployer());
		    lcaVisaClfsObj.setChangeInEmployerCount(Integer.parseInt(lcaDTO.getChangeInEmployerCount()));
		    }else {
		    lcaVisaClfsObj.setChangeInEmployer(null);
			lcaVisaClfsObj.setChangeInEmployerCount(null);	
		    }
		    
		    if(lcaDTO.getConcurrentEmployment() != null && !lcaDTO.getConcurrentEmployment().isEmpty()) {
		    lcaVisaClfsObj.setConcurrentEmployment(lcaDTO.getConcurrentEmployment());
		    lcaVisaClfsObj.setConcurrentEmploymentCount(Integer.parseInt(lcaDTO.getConcurrentEmploymentCount()));
		    }else {
		    lcaVisaClfsObj.setConcurrentEmployment(null);
		    lcaVisaClfsObj.setConcurrentEmploymentCount(null);
		    }
		    
		    if(lcaDTO.getChangeInPreApproEmployment() != null && !lcaDTO.getChangeInPreApproEmployment().isEmpty()) {
		    lcaVisaClfsObj.setChangeInPreApproEmployment(lcaDTO.getChangeInPreApproEmployment());
		    lcaVisaClfsObj.setChangeInPreApproEmplmtCount(Integer.parseInt(lcaDTO.getChangeInPreApproEmplmtCount()));
		    }else {
		    lcaVisaClfsObj.setChangeInPreApproEmployment(null);
			lcaVisaClfsObj.setChangeInPreApproEmplmtCount(null);
		    }
		    
		    if(lcaDTO.getAmendment() != null && !lcaDTO.getAmendment().isEmpty()) {
		    lcaVisaClfsObj.setAmendment(lcaDTO.getAmendment());
		    lcaVisaClfsObj.setAmendmentCount(Integer.parseInt(lcaDTO.getAmendmentCount()));
		    }else {
		    lcaVisaClfsObj.setAmendment(null);
			lcaVisaClfsObj.setAmendmentCount(null);
		    }
		    
		    if(lcaDTO.getExtention() != null && !lcaDTO.getExtention().isEmpty()) {
		    lcaVisaClfsObj.setExtention(lcaDTO.getExtention());
		    lcaVisaClfsObj.setExtentionCount(Integer.parseInt(lcaDTO.getExtentionCount()));
		    }else {
		    	lcaVisaClfsObj.setExtention(null);
			    lcaVisaClfsObj.setExtentionCount(null);
		    }
		   
		    
			lcaVisaClfsObj.setStatus(Constants.RECORD_ACTIVE);
		//	lcaVisaClfsObj.setCreatedBy(user);
			lcaVisaClfsObj.setUpdatedBy(user);
			
			lcaVisaClfsObj.setUpdatedDate(currentDate);
			//lcaVisaClfsObj.setCreatedDate(currentDate);
			try {
				immigrationCommonDao.saveLcaVisaClarifications(lcaVisaClfsObj);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	private Lca saveLcaDetailsInfo(LcaDTO lcaDTO, User user, OrgLocations orgLocations) throws Exception {
		
		Lca lca = null;
		Date currentDate = commonUtil.orgLocationCommonDate(new Date(), orgLocations);
		if (lcaDTO.getLcaId() != null && StringUtils.isNotEmpty(lcaDTO.getLcaId())) {
			lca = immigrationCommonDao.getLcaById(Long.parseLong(lcaDTO.getLcaId()));
			
			//update noOfPositions and Available positions
			
			if(lca.getAvailablePositions() == lca.getNoOfPositions()) {
				lca.setNoOfPositions(Integer.parseInt(lcaDTO.getNoOfPositions()));
				lca.setAvailablePositions(Integer.parseInt(lcaDTO.getNoOfPositions()));
			}else if(lca.getNoOfPositions() != null && lcaDTO.getNoOfPositions() != null) {
				int posCount = Integer.parseInt(lcaDTO.getNoOfPositions());
				if(posCount > lca.getNoOfPositions())
				lca.setAvailablePositions(lca.getAvailablePositions() + (posCount-lca.getNoOfPositions()));
				else {
					lca.setAvailablePositions(lca.getAvailablePositions() -(lca.getNoOfPositions()-posCount));
				}
				lca.setNoOfPositions(Integer.parseInt(lcaDTO.getNoOfPositions()));
			}
			
		} else {
			lca = new Lca();
			lca.setCreatedBy(user);
			lca.setCreatedDate(currentDate);
			
			if(lcaDTO.getNoOfPositions() != null && !lcaDTO.getNoOfPositions().isEmpty()) {
				lca.setNoOfPositions(Integer.parseInt(lcaDTO.getNoOfPositions()));
				lca.setAvailablePositions(Integer.parseInt(lcaDTO.getNoOfPositions()));
		     }
		}
		if(lcaDTO.getStartDate() != null && !lcaDTO.getStartDate().isEmpty())
		{
		lca.setStartDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), lcaDTO.getStartDate()));
		}
		if(lcaDTO.getEndDate() != null && !lcaDTO.getEndDate().isEmpty())
		{
		lca.setEndDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), lcaDTO.getEndDate()));
		}
		
		/*if(lcaDTO.getNoOfPositions() != null && !lcaDTO.getNoOfPositions().isEmpty()) {
		lca.setNoOfPositions(Integer.parseInt(lcaDTO.getNoOfPositions()));
		if(lcaDTO.getNoOfPositions() != null && StringUtils.isNumeric(lcaDTO.getNoOfPositions()) && lcaDTO.getAvailablePositions() != null && StringUtils.isNumeric(lcaDTO.getAvailablePositions()))
		lca.setAvailablePositions(Integer.parseInt(lcaDTO.getNoOfPositions())-Integer.parseInt(lcaDTO.getAvailablePositions())-1);
		}else {
			lca.setAvailablePositions(Integer.parseInt(lcaDTO.getNoOfPositions()));
		}*/
		// updateLca.setAvailablePositions((updateLca.getNoOfPositions()-updateLca.getAvailablePositions())-1);
		lca.setLcaUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
		lca.setLcaNo(lcaDTO.getLcaNo());
		lca.setLcaTitle(lcaDTO.getLcaTitle());
		
		lca.setLcaStatus(lcaDTO.getLcaStatus());
		
		lca.setOfferedWage(lcaDTO.getOfferedWage());
		lca.setOfferedWageCurrency(lcaDTO.getOfferedWageCurrency());
		lca.setOfferedWageRateType(lcaDTO.getOfferedWageRateType());
		
		if(orgLocations != null){
			lca.setOrgLocationId(orgLocations);
			}
		
		lca.setPrevailingWage(lcaDTO.getPrevailingWage());
		lca.setPrivilingWageCurrency(lcaDTO.getPrivilingWageCurrency());
		lca.setPrivilingWageRateType(lcaDTO.getPrivilingWageRateType());
		
		lca.setEmployee(lcaDTO.getEmployee());
		lca.setWagesLevel(lcaDTO.getWagesLevel());
		lca.setJobTitle(lcaDTO.getJobTitle());
		lca.setSocCode(lcaDTO.getSocCode());
		lca.setSocOccupationTitle(lcaDTO.getSocOccupationTitle());
		if(lcaDTO.getStatus().equalsIgnoreCase("Active")) {
		lca.setStatus(Constants.RECORD_ACTIVE);
		}else {
			lca.setStatus(Constants.RECORD_INACTIVE);
		}
		lca.setUpdatedBy(user);
		lca.setUpdatedDate(currentDate);

		return lca;
		
	}

   @Override
	public ResponseList<LcaListViewDTO> getLcasList(LcaListViewDTO lcaListViewDTO,String status,String lcaStatus, String searchValue,
			OrgLocations orgLocations, Map<String, Long[]> filterVal,String filterByDays) throws Exception {
		return immigrationCommonDao.getLcaList(lcaListViewDTO,status, lcaStatus,searchValue, orgLocations, filterVal,filterByDays);
	}

	@Override
	public Long getLcaIdByUUID(String lcaUuid) {
		return immigrationCommonDao.getLcaIdByUUID(lcaUuid);
	}

	@Override
	public String deleteLcaDetailsById(Long lcaId) {
		return immigrationCommonDao.deleteLcaDetailsById(lcaId);
	}

	@Override
	public LcaDTO editLcaDetails(Long lcaId, OrgLocations orgLocations) {
		
		Lca lca = immigrationCommonDao.getLcaById(lcaId);
		LcaDTO lcaDTO = new LcaDTO();
		try {
			/* ============================from lca Table================= */
		    lcaDTO.setLcaId(lca.getId().toString());
		    lcaDTO.setLcaNo(lca.getLcaNo());
		    lcaDTO.setLcaTitle(lca.getLcaTitle());
		    lcaDTO.setStartDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),lca.getStartDate()));
		    lcaDTO.setEndDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),lca.getEndDate()));
		    
		    if(lca.getNoOfPositions() != null) {
		    lcaDTO.setNoOfPositions(lca.getNoOfPositions().toString());
		    }
		    
		    if(lca.getWagesLevel() != null) {
		    lcaDTO.setWagesLevel(lca.getWagesLevel());
		    }
		    lcaDTO.setPrevailingWage(lca.getPrevailingWage());
		    lcaDTO.setPrivilingWageRateType(lca.getPrivilingWageRateType());
		    lcaDTO.setPrivilingWageCurrency(lca.getPrivilingWageCurrency());
		    
		    lcaDTO.setOfferedWage(lca.getOfferedWage());
		    lcaDTO.setOfferedWageRateType(lca.getOfferedWageRateType());
		    lcaDTO.setOfferedWageCurrency(lca.getOfferedWageCurrency());
		    
		    if(lca.getJobTitle() != null) {
		    lcaDTO.setJobTitle(lca.getJobTitle());
		    }
		    lcaDTO.setStatus(lca.getStatus().toString());
		    
		    if(lca.getLcaStatus() != null) {
		    lcaDTO.setLcaStatus(lca.getLcaStatus());
		    }
		    
		    if(lca.getEmployee() != null) {
		    lcaDTO.setEmployee(lca.getEmployee());
		    }
		    if(lca.getSocCode() != null) {
		    lcaDTO.setSocCode(lca.getSocCode());
		    }
		    
		    if(lca.getSocOccupationTitle() != null) {
		    lcaDTO.setSocOccupationTitle(lca.getSocOccupationTitle());
		    }
		    
		    /* ============================from lca Table End================= */
		    
		    /* ============================from LcaVisaClarifications Table Start================= */
		    LcaVisaClarifications lcaVisaClfs = null;
		    lcaVisaClfs = immigrationCommonDao.getLcaVisaClarificationsByIDAndLcaId(lca.getId());
			if(lcaVisaClfs != null) {
				
				lcaDTO.setVisaClsfId(lcaVisaClfs.getId().toString());
				
				if(lcaVisaClfs.getNewEmployment() != null){
				lcaDTO.setNewEmployment(lcaVisaClfs.getNewEmployment());
				}
				if(lcaVisaClfs.getNewEmploymentCount() != null){
				lcaDTO.setNewEmploymentCount(lcaVisaClfs.getNewEmploymentCount().toString());
				}
				if(lcaVisaClfs.getConcurrentEmployment()!= null){
				lcaDTO.setConcurrentEmployment(lcaVisaClfs.getConcurrentEmployment());
				}
				if(lcaVisaClfs.getConcurrentEmploymentCount() != null){
				lcaDTO.setConcurrentEmploymentCount(lcaVisaClfs.getConcurrentEmploymentCount().toString());
				}
				
				if(lcaVisaClfs.getExtention() != null){
				lcaDTO.setExtention(lcaVisaClfs.getExtention());
				}
				
				if(lcaVisaClfs.getExtentionCount() != null){
				lcaDTO.setExtentionCount(lcaVisaClfs.getExtentionCount().toString());
				}
				if(lcaVisaClfs.getChangeInEmployer() != null){
				lcaDTO.setChangeInEmployer(lcaVisaClfs.getChangeInEmployer());
				}
				if(lcaVisaClfs.getChangeInEmployerCount() != null){
				lcaDTO.setChangeInEmployerCount(lcaVisaClfs.getChangeInEmployerCount().toString());
				}
				if(lcaVisaClfs.getChangeInPreApproEmployment() != null){
				lcaDTO.setChangeInPreApproEmployment(lcaVisaClfs.getChangeInPreApproEmployment());
				}
				if(lcaVisaClfs.getChangeInPreApproEmplmtCount() != null){
				lcaDTO.setChangeInPreApproEmplmtCount(lcaVisaClfs.getChangeInPreApproEmplmtCount().toString());
				}
				if(lcaVisaClfs.getAmendment() != null){
				lcaDTO.setAmendment(lcaVisaClfs.getAmendment());
				}
				if(lcaVisaClfs.getAmendmentCount() != null){
				lcaDTO.setAmendmentCount(lcaVisaClfs.getAmendmentCount().toString());
				}
				
				 /* ============================from LcaVisaClarifications Table End================= */
				
				
				/* ============================from LcaPlaceOfEployment Table Start================= */
				
				List<LcaPlaceOfEployment> lcaPlaceOfEployment = immigrationCommonDao.getLcaPlaceOfEploymentListByIdAndLcaID(lca.getId());
				if (!lcaPlaceOfEployment.isEmpty()) {
					List<LcaPlaceOfEmploymentDTO> residenceList = new ArrayList<LcaPlaceOfEmploymentDTO>();
					for (LcaPlaceOfEployment residence : lcaPlaceOfEployment) {
						LcaPlaceOfEmploymentDTO residenceDTO = new LcaPlaceOfEmploymentDTO();
						residenceDTO.setPlsempmntId(residence.getId().toString());
						residenceDTO.setAddressLine1(residence.getAddressLine1());
						
						if(StringUtils.isNotBlank(residence.getAddressLine2())) {
						residenceDTO.setAddressLine2(residence.getAddressLine2());
						}
						residenceDTO.setCity(residence.getCity());
						residenceDTO.setCountry(residence.getCountry());
						residenceDTO.setLocation(residence.getLocation());
						residenceDTO.setState(residence.getState());
						residenceDTO.setZipCode(residence.getZipCode());
						
						residenceList.add(residenceDTO);
					}

					lcaDTO.setLcaPlaceOfEmploymentList(residenceList);;
				}
				
				/* ============================from LcaPlaceOfEployment Table End================= */
				
				/* ============================from LcaDocumentHistory Table Start================= */
				
				List<LcaDocumentHistory> lcaDocs = immigrationCommonDao.getLcaDocumentHistoryListById(lcaId)  ;
				if(!lcaDocs.isEmpty()) {
					
		        List<LcaDocumentHistoryDTO> lcaDocsDTOList =new ArrayList<LcaDocumentHistoryDTO>();
		        for(LcaDocumentHistory lcaAttachments:lcaDocs) {
		        	LcaDocumentHistoryDTO lcaDocDTO = new LcaDocumentHistoryDTO();
		        	lcaDocDTO.setDocId(lcaAttachments.getId().toString());
		        	lcaDocDTO.setDocumentTitle(lcaAttachments.getDocumentTitle());
		        	lcaDocDTO.setFileName(lcaAttachments.getFileName());
		        	lcaDocDTO.setFileContentType(lcaAttachments.getFileContentType());
		        	lcaDocDTO.setFileUrl(lcaAttachments.getFileUrl());
		        	if(lcaAttachments.getDescription() != null && !lcaAttachments.getDescription().isEmpty()) {
		        	lcaDocDTO.setDescription(lcaAttachments.getDescription());
		        	}
		        	lcaDocsDTOList.add(lcaDocDTO);
		        }
		        lcaDTO.setLcaDocumentList(lcaDocsDTOList);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return lcaDTO;
	}

	@Override
	public List<Map<String, String>> getSocCodeWithSOCOccupationTitleList(String name,String category,String socType) {
		return immigrationCommonDao.getSocCodeWithSOCOccupationTitleList(name,socType);
	}

	@Override
	public String reActiveLcaById(Long lcaId) {
		return immigrationCommonDao.reActiveLcaById(lcaId);
	}

	@Override
	public String getOesSocCodeById(Long id, String socType) {
		return immigrationCommonDao.getOesSocCodeById(id, socType);
	}
	
	
	@Override
	public Sheet getLCAExcelFile(Sheet createExcelsheet, Workbook createExcelworkbook, OrgLocations orgLocations) {
		Row writeRow = null;
		
		createExcelsheet = getLCAExcelSheetWithHeader(createExcelsheet, writeRow, createExcelworkbook);
       List<LcaListExportView> immigrationListExportView = immigrationCommonDao.getLcaListExportDetails(orgLocations);
		int column=0;
        for(LcaListExportView exportView : immigrationListExportView){
       	 writeRow = createExcelsheet.createRow(column+1);
       	 try {
			createExcelsheet = createLcaListXlsSheet(createExcelsheet,writeRow,exportView,orgLocations);
		} catch (IllegalArgumentException e) {
			logger.error("IllegalArgumentException occured while genearting LCA Excel sheet.");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			logger.error("IllegalAccessException occured while genearting LCA Excel sheet.");
			e.printStackTrace();
		}
       	 column++;  
        }
		return createExcelsheet;
	}
	
	private Sheet createLcaListXlsSheet(Sheet createExcelsheet, Row writeRow,
			LcaListExportView lcaExportView, OrgLocations orgLocations) throws IllegalArgumentException, IllegalAccessException {
		Class mainClass= LcaListExportView.class;
		int cellIndex=0;
		for(String fieldName : ImmigrationConstants.LCA_EXCEL_REFLECTION_FILEDS_LIST) {
			if(fieldName.equalsIgnoreCase("empName")|| fieldName.equalsIgnoreCase("empEmail")) {
				cellIndex++;
				continue;
			}
			// above if condition should be removed once columns are update in view in the databse.
			try {
				Field field = mainClass.getDeclaredField(fieldName);
				
				     field.setAccessible(true);
				     if(fieldName.equalsIgnoreCase("location")) {
				    
				    	 String addrArr[]= ((String)field.get(lcaExportView)).split("\n");
				    	 for(int i=0; i<addrArr.length; i++) {
				    		 
				    		 if(i==3) break;
				    		 ExcelHelperUtil.setDataValueInCell(writeRow.createCell(cellIndex), addrArr[i], null); 
				    		 
				    		 cellIndex++;
				    	 }
				    	 cellIndex = 5;// setting default value for next column index. It does not depend on address or location list. 
				     }else if(fieldName.contains("Date")) {
				    	 
				    	 
				    	String dateString = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),lcaExportView.getStartDate());
				    	 ExcelHelperUtil.setDataValueInCell(writeRow.createCell(cellIndex), dateString, null);
				    	
				     }/*else if(fieldName.equalsIgnoreCase("status")) {
				    	 Integer status = lcaExportView.getStatus();
				    	 if(status == 1) {
				    	     ExcelHelperUtil.setDataValueInCell(writeRow.createCell(cellIndex), ImmigrationConstants.LCA_STATUS_ACTIVE, null);
				    	 }else {
				    		 ExcelHelperUtil.setDataValueInCell(writeRow.createCell(cellIndex), ImmigrationConstants.LCA_STATUS_IN_ACTIVE, null);
				    	 }
				     }*/
				     else {
				             ExcelHelperUtil.setDataValueInCell(writeRow.createCell(cellIndex), field.get(lcaExportView), null);
				     }
				
			} catch (NoSuchFieldException | SecurityException e) {
				logger.error(" NoSuchFieldException | SecurityException  while genearting LCA Excel sheet.");
				e.printStackTrace();
			}
			cellIndex++;
		}
		
		return createExcelsheet;
	}
	private Sheet getLCAExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook) {
		
		CellStyle style = createExcelworkbook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		Font font = createExcelworkbook.createFont();
		font.setColor(IndexedColors.BLACK.getIndex());
		style.setFont(font);
		String headers [] = ImmigrationConstants.LCA_LIST_EXCEL_HEADERS;
		return createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet,style,headers,0);
	}

	@Override
	public List<Lca> getAllLcaList(Date currentDate) {
		return immigrationCommonDao.getAllLcaList(currentDate);
	}

	@Override
	public String updateLcaStatusWithExpired(Long lcaId) {
		return immigrationCommonDao.updateLcaStatusWithExpired(lcaId);
	}
		public Lca getLcaDetailsByLcaNoAndId(String lcaNo, Long lcaId) throws Exception {
		return immigrationCommonDao.getLcaDetailsByLcaNoAndId(lcaNo, lcaId);
	}

	@Override
	public Boolean validateLcaNumber(String lcaNo, Long orgLocationId) throws Exception {
		return immigrationCommonDao.validateLcaNumber(lcaNo, orgLocationId);
	}

	@Override
	public Lca getLcaDetailsByLcaTitleAndId(String lcaTitle, Long lcaId) throws Exception {
		return immigrationCommonDao.getLcaDetailsByLcaTitleAndId(lcaTitle, lcaId);
	}

	@Override
	public Boolean validateLcaTitle(String lcaTitle, Long orgLocationId) throws Exception {
		return immigrationCommonDao.validateLcaTitle(lcaTitle, orgLocationId);
	}

	@Override
	public List<Map<String, String>> getLcaNumbersList(String lcaNum,String orgLocationId,String statusLca,String candidateId) {
		return immigrationCommonDao.getLcaNumbersList(lcaNum,orgLocationId,statusLca,candidateId);
	}

	@Override
	public Lca getLcaByLcaId(Long lcaId) throws Exception {
		
        Lca lca=immigrationCommonDao.getLcaByLcaId(lcaId);
        // Set Location From LcaPlaceOfEployment table
		List<LcaPlaceOfEployment> locList = immigrationCommonDao.getLcaPlaceOfEploymentListById(lcaId);
		LcaPlaceOfEployment lcaPlaceOfEployment = locList.get(0);
		lca.setLcaPlaceOfEployment(locList);
		lca.setLcaLocation(lcaPlaceOfEployment.getLocation());
		return lca;
	}

	@Override
	public List<LcaDTO> getLcaHistoryDetailsInfo(Long immigrationId) {
		return immigrationCommonDao.getLcaHistoryDetailsInfo(immigrationId);
	}

	@Override
	public List<LcaDetailsDTO> getEmployeeByLcaId(Long lcaId,OrgLocations orgLocations) throws Exception {
		return immigrationCommonDao.getEmployeeByLcaId(lcaId,orgLocations);
	}

	@Override
	public LcaHistory getLcaHistoryByStatus(Long immigrationId,Long candidateId ,String isCurrent) {
		return immigrationCommonDao.getLcaHistoryByStatus(immigrationId,candidateId,isCurrent);
	}

	@Override
	public LcaHistory saveLcaHistory(LcaHistory lcaHistory) {
		return immigrationCommonDao.saveLcaHistory(lcaHistory);
	}

	@Override
	public int updateLcaHistoryStatusByLcaNumber(Long lhId, Long immigrationId, Long candidateId) {
		return immigrationCommonDao.updateLcaHistoryStatusByLcaNumber(lhId, immigrationId, candidateId);
	}

	@Override
	public int deleteLcaHistoryDetailsByImmigrationId(Long immigrationId) {
		return immigrationCommonDao.deleteLcaHistoryDetailsByImmigrationId(immigrationId);
	}

	@Override
	public List<String> getAllExpiredCandidatesFromLcaHistoryByLcaId(Long lcaId) {
		return immigrationCommonDao.getAllExpiredCandidatesFromLcaHistoryByLcaId(lcaId);
	}

	/*
	 * immigration reports
	 */
	@Override
	public ResponseList<ImmigrationReportViewDTO> getImmigrationReports(ImmigrationReportViewDTO immigrationReportDTO,
			OrgLocations orgLocations) {
		return immigrationCommonDao.getImmigrationReports(immigrationReportDTO, orgLocations);
	}
	
	/*
	 * immigration reports
	 */
	@Override
	public ResponseList<ImmigrationReportViewDTO> getImmigrationAttorneyReports(ImmigrationReportViewDTO immigrationReportDTO,
			OrgLocations orgLocations) {
		return immigrationCommonDao.getImmigrationAttorneyReports(immigrationReportDTO, orgLocations);
	}

	@Override
	public ResponseList<LcaReportViewDTO> getLcaReportsList(LcaReportViewDTO lcaReportViewDTO, String status, String lcaStatus,
			String searchValue, OrgLocations orgLocations, Map<String, Long[]> filterVal, String filterByDays,String positionsAvaialble)
			throws Exception {
		return immigrationCommonDao.getLcaReportList(lcaReportViewDTO, status, lcaStatus, searchValue, orgLocations, filterVal, filterByDays,positionsAvaialble);
	}

	@Override
	public ImmigrationVisaDTO getCandidateVisaDetailsByVisaHistoryId(Long visaHistoryId) {
		return immigrationCommonDao.getCandidateVisaDetailsByVisaHistoryId(visaHistoryId);
	}

	@Override
	public CandidateVisaHistory saveVisaHistoryDetails(ImmigrationVisaDTO immigrationVisaDTO, User user,
			OrgLocations orgLocations, String immigrationId) {
		CandidateVisaHistory candVisaHistory = null;
		try {

			CandidateVisa candidateVisa = new CandidateVisa();
			candidateVisa.setId(immigrationVisaDTO.getCandidateVisaId());
			candidateVisa.setOrgLocationId(orgLocations);
			Immigration immigration = new Immigration();
			candVisaHistory = immigrationCommonDao.getCandidateVisaHistoryByStatus(
												immigrationVisaDTO.getCandidateVisaId(), ImmigrationConstants.IS_CURRENT_NO);
			if(StringUtils.isNotBlank(immigrationId)) {
			 immigration.setId(Long.parseLong(immigrationId));
		    }
			if (candVisaHistory != null) {
				candVisaHistory.setVisaId(candidateVisa);
				candVisaHistory.setIsCurrent(immigrationVisaDTO.getIsCurrent()); // "Y"
				Date currentDate = commonUtil.orgLocationCommonDate(new Date(), orgLocations);
				candVisaHistory.setUpdatedBy(user);
				candVisaHistory.setUpdatedDate(currentDate);
				candVisaHistory.setId(null); // setting null for inserting new record.
				candVisaHistory.setValidToDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
														immigrationVisaDTO.getValidTo()));
				StringBuilder actionDesc = new StringBuilder();
				actionDesc.append("Visa Details Updated ").toString();
				candVisaHistory = immigrationCommonDao.saveVisaHistoaryDetails(candVisaHistory);
				int count = immigrationCommonDao.updateCanVisaHistoryDetails(immigrationVisaDTO.getCandidateVisaId(),
																						candVisaHistory.getId());
				if (immigrationVisaDTO.getId() != null && StringUtils.isNotBlank(immigrationId)) {
					Candidate candidate = new Candidate();
					candidate.setId(immigrationVisaDTO.getCandidateId());
					
					try {
						immigrationActivityCommonUtil.saveImmigrationCandidateActivities(user, orgLocations, candidate,
								immigration, actionDesc.toString());
					} catch (Exception e) {
						e.printStackTrace();
					}

				}else {
					Candidate candidate = new Candidate();
					candidate.setId(immigrationVisaDTO.getCandidateId());
					candidateActivityCommonUtil.storeCandidateActivity(user, orgLocations, candidate, "", "Visa Details", actionDesc.toString(), "CANDIDATE");
				}
				logger.debug(count + "Records are upated in Visa History Table >>>>>>>>>>");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception caught while extending visa details " + e.getMessage());
		}
		return candVisaHistory;
	}

	@Override
	public ImmigrationVisaDTO getCandidateVisaDetailsByCandidateIdAndVisaNumber(Long candidateId,
			String visaNumber) {
		return immigrationCommonDao.getCandidateVisaDetailsByCandidateIdAndVisaNumber(candidateId, visaNumber);
	}

	@Override
	public ImmigrationVisaDTO getCandidateActiveVisaDetailsByVisaNumberAndCountry(Long candidateId, String visaNumber, String country) {
		return immigrationCommonDao.getCandidateActiveVisaDetailsByVisaNumberAndCountry(candidateId, visaNumber, country);
	}

	@Override
	public Lca updateLcaAvailablePositionsById(Lca lca) {
		
		return immigrationCommonDao.updateLcaAvailablePositionsById(lca);
	}
	
	@Override
	public Lca getLcaNumber(String lcaNo) throws Exception{
		
		return immigrationCommonDao.getLcaNumber(lcaNo);
	}

	@Override
	public List<LcaDTO> getAssociateLcaDetails(Long candidateId) {
		return immigrationCommonDao.getAssociateLcaDetails(candidateId);
	}
	public List<CandidatePassport> getAllPassportList(Date expectedExpiryDate) throws Exception {
		return immigrationCommonDao.getAllPassportList(expectedExpiryDate);
	}

	@Override
	public List<CandidateVisaHistory> getAllVisaHistoryList(Date expectedExpiryDate) throws Exception {
		return immigrationCommonDao.getAllVisaHistoryList(expectedExpiryDate);
	}

}
