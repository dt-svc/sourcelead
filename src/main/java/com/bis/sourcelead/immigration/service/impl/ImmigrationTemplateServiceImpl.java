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
	public Long savePetitionTemplateDetails(ImmigrationTemplateDTO templateDTO, User user) {
		ImmigrationTemplate immigrationTemplate = new ImmigrationTemplate();
		immigrationTemplate.setName(templateDTO.getTemplateName());
		immigrationTemplate.setDescription(templateDTO.getTemplateDescription());
		immigrationTemplate.setCreatedBy(user);
		immigrationTemplate.setCreatedDate(new Date());
		immigrationTemplate.setUpdatedBy(user);
		immigrationTemplate.setUpdatedDate(new Date());
		immigrationTemplate.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
		immigrationTemplate.setStatus(0);
		return immigrationTemplateDao.savePetitionTemplateDetails(immigrationTemplate);
		
		
	}

	@Override
	public void savePetitionTemplateDocDetails(ImmigrationTemplateDTO templateDTO, User user) {

		List<ImmigrationTemplateDocDTO> docsList = templateDTO.getImmigrationDocList();
		ImmigrationTemplate immiTemplate = new ImmigrationTemplate();
		immiTemplate.setId(templateDTO.getId());
		
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
			immigrationTemplateDao.savePetitionTemplateDocDetails(immigrationTemplateDocs);
		}

	}

}
