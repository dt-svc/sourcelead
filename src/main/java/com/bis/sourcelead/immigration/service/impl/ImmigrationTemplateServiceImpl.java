/**
 *
 */
package com.bis.sourcelead.immigration.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dto.CandidateEmployerDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.ImmigrationTemplateDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateDocDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateFormDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateListDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateTaskDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.ImmigrationTemplate;
import com.bis.sourcelead.entity.ImmigrationTemplateDocs;
import com.bis.sourcelead.entity.ImmigrationTemplateForms;
import com.bis.sourcelead.entity.ImmigrationTemplateTasks;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.immigration.dao.ImmigrationTemplateDao;
import com.bis.sourcelead.immigration.service.ImmigrationTemplateService;
import com.bis.sourcelead.service.TaskService;
import com.bis.sourcelead.util.CommonUtil;
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
    CandidateDao candidateDao;
    
    @Autowired
    TaskService taskService;
    
    @Autowired
    private CommonUtil commonUtil;

    @Override
    public Long savePetitionTemplateDetails(ImmigrationTemplateDTO templateDTO, User user, OrgLocations orgLocations,
            String templateId) {
        ImmigrationTemplate immigrationTemplate = null;
        Date date = new Date();
        Date latestDate = commonUtil.orgLocationCommonDate(date, orgLocations);
        if (!templateId.equalsIgnoreCase("null") && !templateId.isEmpty()) {
        	immigrationTemplate =  new ImmigrationTemplate();
        	immigrationTemplate.setId(Long.parseLong(templateId));
        	immigrationTemplate = immigrationTemplateDao.getImmigrationTemplateById(Long.parseLong(templateId));
            //immigrationTemplate.setStatus(1);
        }else {
        	immigrationTemplate = new ImmigrationTemplate();
        	immigrationTemplate.setCreatedDate(latestDate);
            immigrationTemplate.setCreatedBy(user);
        	immigrationTemplate.setStatus(Constants.RECORD_DRAFT); // Changed to in active state to Draft State.
        }
        
        immigrationTemplate.setOrgLocations(orgLocations);
        immigrationTemplate.setName(templateDTO.getTemplateName());
        immigrationTemplate.setDescription(templateDTO.getTemplateDescription());
        immigrationTemplate.setUpdatedBy(user);
        immigrationTemplate.setUpdatedDate(latestDate);
        immigrationTemplate.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
        LookUpData lookUpData = new LookUpData();
        lookUpData.setId(Long.parseLong(templateDTO.getPetitionType()));
        immigrationTemplate.setPetitionType(lookUpData);
        immigrationTemplate.setCountry(templateDTO.getCountry());
        immigrationTemplate.setActiveStatus(Constants.CANDIDATE_SKILL_IN_ACTIVE);
        return immigrationTemplateDao.savePetitionTemplateDetails(immigrationTemplate, templateId);

    }

    @Override
    public List<ImmigrationTemplateDocDTO> savePetitionTemplateDocDetails(ImmigrationTemplateDTO templateDTO,
            User user,OrgLocations orgLocations) {

        List<ImmigrationTemplateDocDTO> docsList = templateDTO.getImmigrationDocList();
        ImmigrationTemplate immiTemplate = new ImmigrationTemplate();
        Date date = new Date();
        Date latestDate = commonUtil.orgLocationCommonDate(date, orgLocations);
        immiTemplate.setId(templateDTO.getId());
        if (templateDTO.getImmigrationDocDeletedListIds().length>0) {
            immigrationTemplateDao.deletePetitionTemplateDocDetails(templateDTO.getImmigrationDocDeletedListIds());
        }
        List<ImmigrationTemplateDocDTO> docsListAfterSave = new ArrayList<ImmigrationTemplateDocDTO>();
        for (ImmigrationTemplateDocDTO tempObj : docsList) {
            if (tempObj.getDocumentType() == null) {
                continue;
            }
            ImmigrationTemplateDocs immigrationTemplateDocs = null;
            if(tempObj.getId() != null){
            	immigrationTemplateDocs = new ImmigrationTemplateDocs();
            	immigrationTemplateDocs.setId(tempObj.getId());
            //	immigrationTemplateDocs.setDocumentCategory(tempObj.getDocumentCategory());
            }else{
            	immigrationTemplateDocs = new ImmigrationTemplateDocs();
            	 immigrationTemplateDocs.setCreatedDate(latestDate);
                 immigrationTemplateDocs.setCreatedBy(user);
            }
            immigrationTemplateDocs.setImmigrationTempId(immiTemplate);
            immigrationTemplateDocs.setUpdatedDate(latestDate);
            immigrationTemplateDocs.setUpdatedBy(user);
            immigrationTemplateDocs.setDocumentCategory(tempObj.getDocumentCategory());
            
            LookUpData documentTypeId = new LookUpData();
           
            documentTypeId.setId(tempObj.getDocumentType());
            immigrationTemplateDocs.setDocumentType(documentTypeId);
            
            immigrationTemplateDocs.setDescription(tempObj.getDescription());
            immigrationTemplateDocs.setDocumentOtherType(tempObj.getOtherType());
            if(!StringUtils.isEmpty(tempObj.getOtherType())){ // if other the value is not empty overriding the description value.
           	 immigrationTemplateDocs.setDescription(tempObj.getDescriptionOther());	
            }
            if (StringUtils.isNotBlank(tempObj.getIsRequired()) && tempObj.getIsRequired().equalsIgnoreCase("Y")) {
                immigrationTemplateDocs.setIsRequired(Constants.YES);
            } else {
                immigrationTemplateDocs.setIsRequired(Constants.NO);
            }
            if (StringUtils.isNotBlank(tempObj.getMultiple()) && tempObj.getMultiple().equalsIgnoreCase("Y")) {
                immigrationTemplateDocs.setIsMultipleRequired(Constants.YES);
            } else {
                immigrationTemplateDocs.setIsMultipleRequired(Constants.NO);
            }
            immigrationTemplateDocs.setStatus(Constants.RECORD_ACTIVE);// active
            immiTemplate.setActiveStatus(Constants.CANDIDATE_SKILL_IN_ACTIVE);
            ImmigrationTemplateDocs docs = immigrationTemplateDao
                    .savePetitionTemplateDocDetails(immigrationTemplateDocs);
            tempObj.setId(docs.getId());
            tempObj.setIsRequired(docs.getIsRequired());
            tempObj.setMultiple(docs.getIsMultipleRequired());
            if(!StringUtils.isEmpty(docs.getDocumentOtherType())){ // if other the value is not empty overriding the description value.
            	tempObj.setDescriptionOther(docs.getDescription()); // mapping to descriptionOther value in jsp.
               }
            docsListAfterSave.add(tempObj);
        }
        return docsListAfterSave;

    }

    @Override
    public void savePetitionTemplateTaskDetails(ImmigrationTemplateTaskDTO templateDTO, User user,OrgLocations orgLocations) {

        ImmigrationTemplate immiTemplate = new ImmigrationTemplate();
        immiTemplate.setId(templateDTO.getTemplateId());
        ImmigrationTemplateTasks immigrationTemplateTask = new ImmigrationTemplateTasks();
        immigrationTemplateTask.setImmigrationTempId(immiTemplate);
        Date date = new Date();
        Date latestDate = commonUtil.orgLocationCommonDate(date, orgLocations);
        if (templateDTO.getId() != null) {
        	immigrationTemplateTask = immigrationTemplateDao.getImmigratonTemplateTasksById(templateDTO.getId());
        	immigrationTemplateTask.setUpdatedDate(latestDate);
            immigrationTemplateTask.setUpdatedBy(user);
        }
        if(immigrationTemplateTask.getUpdatedDate() == null) {
        	immigrationTemplateTask.setUpdatedDate(latestDate);
            immigrationTemplateTask.setUpdatedBy(user);
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
        Calendar cal = Calendar.getInstance();
        cal.setTime(immigrationTemplateTask.getUpdatedDate());
        if(templateDTO.getDueAfterMonths() != null) {
        	cal.add(Calendar.MONTH, templateDTO.getDueAfterMonths());
        }
        if(templateDTO.getDueAfterDays() != null) {
        	cal.add(Calendar.DATE, templateDTO.getDueAfterDays());
        }
        immigrationTemplateTask.setDueAfterDate(cal.getTime());
        immigrationTemplateTask.setTaskRepeat(templateDTO.getTaskRepeat());
        immigrationTemplateTask.setTaskEmail(Constants.YES);
        immigrationTemplateTask.setTaskNotification(Constants.YES);
        immigrationTemplateTask.setDescription(templateDTO.getTaskDescription());
        immigrationTemplateTask.setStatus(Constants.RECORD_ACTIVE);// 1 for active
        immiTemplate.setActiveStatus(Constants.CANDIDATE_SKILL_IN_ACTIVE);
        
        immigrationTemplateTask.setCreatedDate(latestDate);
        immigrationTemplateTask.setCreatedBy(user);
        immigrationTemplateDao.savePetitionTemplateTaskDetails(immigrationTemplateTask);
    }

    @Override
    public ResponseList<ImmigrationTemplateListDTO> getAllTemplateDetails(ImmigrationTemplateListDTO templateListDTO,
            String searchValue, Long orgLocationId, String[] countriesList) {
        ResponseList<ImmigrationTemplateListDTO> templateList = immigrationTemplateDao
                .getAllTemplateDetails(templateListDTO, searchValue, orgLocationId, countriesList);
        return templateList;
    }

    @Override
    public List<ImmigrationTemplateFormDTO> savePetitionTemplateFormDetails(ImmigrationTemplateDTO templateDTO,
            User user,OrgLocations orgLocations) {
        List<ImmigrationTemplateFormDTO> formsList = templateDTO.getImmigrationFormList();
        ImmigrationTemplate immiTemplate = new ImmigrationTemplate();
        immiTemplate.setId(templateDTO.getId());
        Date date = new Date();
        Date latestDate = commonUtil.orgLocationCommonDate(date, orgLocations);
        List<ImmigrationTemplateFormDTO> savedFormList = new ArrayList<ImmigrationTemplateFormDTO>();

        if (templateDTO.getImmigrationFormDeletedListIds().length>0) {
            immigrationTemplateDao.deletePetitionTemplateFormDetails(templateDTO.getImmigrationFormDeletedListIds());
        }

        for (ImmigrationTemplateFormDTO tempObj : formsList) {
            ImmigrationTemplateForms immigrationTemplateForms = null;
            if (tempObj.getFormName() == null) {
                continue;
            }
            /*
             * if(tempObj.getId() != null){ immigrationTemplateForms.setId(tempObj.getId());
             * }
             */
            if (tempObj.getId() != null) {
                immigrationTemplateForms = getImmigrationTemplateFormsById(tempObj.getId());
            } else {
                immigrationTemplateForms = new ImmigrationTemplateForms();
                immigrationTemplateForms.setCreatedDate(latestDate);
                immigrationTemplateForms.setCreatedBy(user);
            }
            immigrationTemplateForms.setImmigrationTempId(immiTemplate);
            immigrationTemplateForms.setFormName(tempObj.getFormName());
            immigrationTemplateForms.setDescription(tempObj.getFormDescription());
            immigrationTemplateForms.setFormCategory(tempObj.getFormCategory());
            if (tempObj.getIsRequired() != null && "Y".equalsIgnoreCase(tempObj.getIsRequired())) {
                immigrationTemplateForms.setIsRequired(tempObj.getIsRequired());
            } else {
                immigrationTemplateForms.setIsRequired(Constants.NO);
            }
            immigrationTemplateForms.setUpdatedDate(latestDate);
            immigrationTemplateForms.setUpdatedBy(user);
            immigrationTemplateForms.setStatus(Constants.RECORD_ACTIVE);
            immiTemplate.setActiveStatus(Constants.CANDIDATE_SKILL_IN_ACTIVE);
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
    public ResponseList<ImmigrationTemplateTaskDTO> getTemplateTaskList(ImmigrationTemplateTaskDTO templateDTO,
            String searchValue) throws Exception {
        ResponseList<ImmigrationTemplateTaskDTO> responseList = immigrationTemplateDao.getTemplateTaskList(templateDTO,searchValue);
        return responseList;
    }

    @Override
    public ImmigrationTemplateTaskDTO editImmigrationTaskList(String id) throws Exception {
        ImmigrationTemplateTasks templateTasks = immigrationTemplateDao.editImmigrationTaskList(id);
        ImmigrationTemplateTaskDTO templateDTO = new ImmigrationTemplateTaskDTO();
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
    public void deleteAllPetitionTemplateDetailsById(Long templateId,String reason) {
        immigrationTemplateDao.deleteAllPetitionTemplateDetailsById(templateId,reason);
    }
    
    @Override
    public void reactiveImmigrationTemplateById(Long templateId) {
        immigrationTemplateDao.reactiveImmigrationTemplateById(templateId);
    }

	@Override
	public void deleteImmigrationTaskList(String id, String taskName) {
		try {
			Tasks task = taskService.getTaskByTaskName(taskName);
			if (task != null) {
				taskService.updateTaskStatus(task);
			}
			immigrationTemplateDao.deleteImmigrationTaskList(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    @Override
    public int savePetitionTemplateDetailsValues(String templateId) {
        return immigrationTemplateDao.savePetitionTemplateDetailsValues(templateId);
    }

    @Override
    public List<CandidateEmployerDTO> getPetitionTemplateEmployers(Long id) {
        List<CandidateEmployers> candidateEmployers = immigrationTemplateDao.getPetitionTemplateEmployers(id);
        List<CandidateEmployerDTO> employerDTOs = new ArrayList<>();
        for (CandidateEmployers source : candidateEmployers) {
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
    public boolean validateTemplateNameExistance(String templateName, Long orgLocationId) {
        return immigrationTemplateDao.validateTemplateNameExistance(templateName, orgLocationId);
    }

	@Override
	public List<LookUpData> getLookupDataByTypeAndLocation(String petitionType, String locationVal) {
		 List<LookUpData> lookUpDatas = immigrationTemplateDao.getLookupDataByTypeAndLocation(petitionType, locationVal);
	     return lookUpDatas;
	}

	@Override
	public Long getTemplateIdByUUID(String immiTemplateUUID) {
		return immigrationTemplateDao.getTemplateIdByUUID(immiTemplateUUID);
	}

	@Override
	public List<ImmigrationTemplateTasks> getImmigrationTemplateTasksByTempId(Long templateId) {
		return immigrationTemplateDao.getImmigratonTemplateTasksByTemplateId(templateId);
	}

	@Override
	public boolean validateTemplateTaskNameExistance(String templateTaskName, Long templateId, Long immiTaskId) {
		return immigrationTemplateDao.validateTemplateTaskNameExistance(templateTaskName, templateId, immiTaskId);
	}

	@Override
	public ImmigrationTemplate getImmigrationTemplateByIdAndLocationId(String templateName,Long templateId, Long orgLocationId) {
		// TODO Auto-generated method stub
		return immigrationTemplateDao.getImmigrationTemplateByIdAndLocationId(templateName,templateId, orgLocationId);
	}

}
