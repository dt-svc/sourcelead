/**
 * 
 */
package com.bis.sourcelead.immigration.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.ImmigrationTemplateDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateDocDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateFormDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateListDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.ImmigrationTemplate;
import com.bis.sourcelead.entity.ImmigrationTemplateDocs;
import com.bis.sourcelead.entity.ImmigrationTemplateForms;
import com.bis.sourcelead.entity.ImmigrationTemplateTasks;
import com.bis.sourcelead.entity.ImmigrationTemplateView;
import com.bis.sourcelead.entity.OrgLocations;
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
public class ImmigrationTemplateServiceImpl implements ImmigrationTemplateService{
	
	@Autowired
	ImmigrationTemplateDao immigrationTemplateDao;

	@Override
	public Long savePetitionTemplateDetails(ImmigrationTemplateDTO templateDTO, User user, OrgLocations orgLocations, String templateId) {
		ImmigrationTemplate immigrationTemplate = new ImmigrationTemplate();
		if(!templateId.equalsIgnoreCase("null") && !templateId.isEmpty()) {
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
	public List<ImmigrationTemplateDocDTO> savePetitionTemplateDocDetails(ImmigrationTemplateDTO templateDTO, User user) {

		List<ImmigrationTemplateDocDTO> docsList = templateDTO.getImmigrationDocList();
		ImmigrationTemplate immiTemplate = new ImmigrationTemplate();
		immiTemplate.setId(templateDTO.getId());
		if(!StringUtils.isEmpty(templateDTO.getImmigrationDocDeletedListIds())) {
			immigrationTemplateDao.deletePetitionTemplateDocDetails(templateDTO.getImmigrationDocDeletedListIds());
		}
		List<ImmigrationTemplateDocDTO> docsListAfterSave = new ArrayList<ImmigrationTemplateDocDTO>();
		for (ImmigrationTemplateDocDTO tempObj : docsList) {
			if(tempObj.getDocumentType() == null) continue;
			ImmigrationTemplateDocs immigrationTemplateDocs = new ImmigrationTemplateDocs();
			immigrationTemplateDocs.setId(tempObj.getId());
			immigrationTemplateDocs.setImmigrationTempId(immiTemplate);
			immigrationTemplateDocs.setCreatedDate(new Date());
			immigrationTemplateDocs.setCreatedBy(user);
			immigrationTemplateDocs.setUpdatedDate(new Date());
			immigrationTemplateDocs.setUpdatedBy(user);
			immigrationTemplateDocs.setDocumentType(tempObj.getDocumentType());
			immigrationTemplateDocs.setDescription(tempObj.getDescription());
			if(tempObj.getIsRequired() != null && "Y".equalsIgnoreCase(tempObj.getIsRequired())){
			immigrationTemplateDocs.setIsRequired(tempObj.getIsRequired());
			}else{
			immigrationTemplateDocs.setIsRequired(Constants.NO);
			}
			if(tempObj.getMultiple() != null && "Y".equalsIgnoreCase(tempObj.getMultiple())){
			immigrationTemplateDocs.setIsMultipleRequired(tempObj.getMultiple());
			}else{
				immigrationTemplateDocs.setIsMultipleRequired(Constants.NO);	
			}
			immigrationTemplateDocs.setStatus(1);// active
			ImmigrationTemplateDocs docs = immigrationTemplateDao.savePetitionTemplateDocDetails(immigrationTemplateDocs);
			tempObj	.setId(docs.getId());
			tempObj.setIsRequired(docs.getIsRequired());
			tempObj.setMultiple(docs.getIsMultipleRequired());
			docsListAfterSave.add(tempObj);
		}
		return docsListAfterSave;

	}

	@Override
	public void savePetitionTemplateTaskDetails(ImmigrationTemplateDTO templateDTO, User user) {
		
		ImmigrationTemplate immiTemplate = new ImmigrationTemplate();
		immiTemplate.setId(templateDTO.getId());
		
		ImmigrationTemplateTasks immigrationTemplateTask = new ImmigrationTemplateTasks();
		immigrationTemplateTask.setImmigrationTempId(immiTemplate);
		
		immigrationTemplateTask.setTaskName(templateDTO.getTaskName());
		 if(StringUtils.isNotBlank(templateDTO.getPetitionAssignedTo())){
			 immigrationTemplateTask.setAssignToUsers(templateDTO.getPetitionAssignedTo());
		 }else{
			 immigrationTemplateTask.setAssignToCandidate(Constants.YES);
		 }
		immigrationTemplateTask.setTaskReminder(templateDTO.getTaskReminder());
		immigrationTemplateTask.setPriority(templateDTO.getPriority());
		immigrationTemplateTask.setDueAfterMonths(templateDTO.getDueAfterMonths());
		immigrationTemplateTask.setDueAfterDays(templateDTO.getDueAfterDays());
		immigrationTemplateTask.setTaskRepeat(templateDTO.getTaskRepeat());
		immigrationTemplateTask.setTaskEmail(templateDTO.getTaskEmail());
		immigrationTemplateTask.setTaskNotification(templateDTO.getTaskNotification());
		immigrationTemplateTask.setDescription(templateDTO.getTaskDescription());
		immigrationTemplateTask.setStatus(1);// 1 for active
		
		immigrationTemplateTask.setCreatedDate(new Date());
		immigrationTemplateTask.setCreatedBy(user);
		immigrationTemplateTask.setUpdatedDate(new Date());
		immigrationTemplateTask.setUpdatedBy(user);

		immigrationTemplateDao.savePetitionTemplateTaskDetails(immigrationTemplateTask);
	}
	@Override
	public ResponseList<ImmigrationTemplateListDTO> getAllTemplateDetails(ImmigrationTemplateListDTO templateListDTO, String searchValue) {
		ResponseList<ImmigrationTemplateListDTO> templateList= immigrationTemplateDao.getAllTemplateDetails(templateListDTO, searchValue);
		return templateList;
	}
	
	@Override
	public List <ImmigrationTemplateFormDTO> savePetitionTemplateFormDetails(ImmigrationTemplateDTO templateDTO, User user) {
		List<ImmigrationTemplateFormDTO> formsList = templateDTO.getImmigrationFormList();
		ImmigrationTemplate immiTemplate = new ImmigrationTemplate();
		immiTemplate.setId(templateDTO.getId());
		
		List <ImmigrationTemplateFormDTO> savedFormList = new ArrayList<ImmigrationTemplateFormDTO>(); 

		if(!StringUtils.isEmpty(templateDTO.getImmigrationFormDeletedListIds())) {
			immigrationTemplateDao.deletePetitionTemplateFormDetails(templateDTO.getImmigrationFormDeletedListIds());
		}
		
		for (ImmigrationTemplateFormDTO tempObj : formsList) {
			ImmigrationTemplateForms immigrationTemplateForms = null;
			if(tempObj.getFormName() == null) continue;
			/*if(tempObj.getId() != null){
				immigrationTemplateForms.setId(tempObj.getId());
			}*/
			if(tempObj.getId() != null){
				immigrationTemplateForms = getImmigrationTemplateFormsById(tempObj.getId());
			}else{
				immigrationTemplateForms = new ImmigrationTemplateForms();
			}
			immigrationTemplateForms.setImmigrationTempId(immiTemplate);
			immigrationTemplateForms.setFormName(tempObj.getFormName());
			immigrationTemplateForms.setDescription(tempObj.getFormDescription());
			if (tempObj.getIsRequired() != null && "Y".equalsIgnoreCase(tempObj.getIsRequired())) {
				immigrationTemplateForms.setIsRequired(tempObj.getIsRequired());
			} else{
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
			ImmigrationTemplateForms immigrationForms = immigrationTemplateDao.savePetitionTemplateFormDetails(immigrationTemplateForms);
			
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
	public ImmigrationTemplateDTO getPetitionTemplateDetails(Long templateId) {
		return immigrationTemplateDao.getPetitionTemplateDetails(templateId);
	}
	
}
