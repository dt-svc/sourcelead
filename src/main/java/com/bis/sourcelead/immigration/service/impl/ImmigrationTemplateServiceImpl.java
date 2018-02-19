/**
 * 
 */
package com.bis.sourcelead.immigration.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dto.ImmigrationTemplateDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateDocDTO;
import com.bis.sourcelead.entity.ImmigrationTemplate;
import com.bis.sourcelead.entity.ImmigrationTemplateDocs;
import com.bis.sourcelead.entity.ImmigrationTemplateForms;
import com.bis.sourcelead.entity.ImmigrationTemplateTasks;
import com.bis.sourcelead.entity.ImmigrationTemplateView;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.immigration.dao.ImmigrationTemplateDao;
import com.bis.sourcelead.immigration.service.ImmigrationTemplateService;
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
	public Long savePetitionTemplateDetails(ImmigrationTemplateDTO templateDTO, User user, String templateId) {
		ImmigrationTemplate immigrationTemplate = new ImmigrationTemplate();
		if(!templateId.equalsIgnoreCase("null") && !templateId.isEmpty()) {
			immigrationTemplate.setId(Long.parseLong(templateId));
		}
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
	public void savePetitionTemplateDocDetails(ImmigrationTemplateDTO templateDTO, User user) {

		List<ImmigrationTemplateDocDTO> docsList = templateDTO.getImmigrationDocList();
		ImmigrationTemplate immiTemplate = new ImmigrationTemplate();
		immiTemplate.setId(templateDTO.getId());
		// delete the records if exist in database and reinsert. 
		immigrationTemplateDao.deletePetitionTemplateDocDetails(templateDTO.getId());
		
		for (ImmigrationTemplateDocDTO tempObj : docsList) {
			ImmigrationTemplateDocs immigrationTemplateDocs = new ImmigrationTemplateDocs();
			immigrationTemplateDocs.setImmigrationTempId(immiTemplate);
			immigrationTemplateDocs.setCreatedDate(new Date());
			immigrationTemplateDocs.setCreatedBy(user);
			immigrationTemplateDocs.setUpdatedDate(new Date());
			immigrationTemplateDocs.setUpdatedBy(user);
			immigrationTemplateDocs.setDocumentType(tempObj.getDocumentType());
			immigrationTemplateDocs.setDescription(tempObj.getDescription());
			immigrationTemplateDocs.setIsRequired(tempObj.getIsRequired());
			immigrationTemplateDocs.setIsMultipleRequired(tempObj.getMultiple());
			immigrationTemplateDocs.setStatus(1);// active
			immigrationTemplateDao.savePetitionTemplateDocDetails(immigrationTemplateDocs);
		}

	}

	@Override
	public void savePetitionTemplateTaskDetails(ImmigrationTemplateDTO templateDTO, User user) {
		
		ImmigrationTemplate immiTemplate = new ImmigrationTemplate();
		immiTemplate.setId(templateDTO.getId());
		
		ImmigrationTemplateTasks immigrationTemplateTask = new ImmigrationTemplateTasks();
		immigrationTemplateTask.setImmigrationTempId(immiTemplate);
		
		immigrationTemplateTask.setTaskName(templateDTO.getTaskName());
		immigrationTemplateTask.setAssignToUsers(templateDTO.getAssignToUsers());
		immigrationTemplateTask.setAssignToCandidate(templateDTO.getAssignToCandidate());
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
	public List<ImmigrationTemplateView> getAllTemplateDetails() {
		List<ImmigrationTemplateView> templateList= immigrationTemplateDao.getAllTemplateDetails();
		return templateList;
	}
}
