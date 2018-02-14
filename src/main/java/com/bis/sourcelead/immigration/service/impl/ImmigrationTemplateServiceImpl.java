/**
 * 
 */
package com.bis.sourcelead.immigration.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dto.ImmigrationTemplateDTO;
import com.bis.sourcelead.entity.ImmigrationTemplate;
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
	public void savePetitionTemplateDetails(ImmigrationTemplateDTO templateDTO, User user) {
		ImmigrationTemplate immigrationTemplate = new ImmigrationTemplate();
		immigrationTemplate.setName(templateDTO.getTemplateName());
		immigrationTemplate.setDescription(templateDTO.getTemplateDescription());
		immigrationTemplate.setCreatedBy(user);
		immigrationTemplate.setCreatedDate(new Date());
		immigrationTemplate.setUpdatedBy(user);
		immigrationTemplate.setUpdatedDate(new Date());
		immigrationTemplate.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
		immigrationTemplate.setStatus(0);
		immigrationTemplateDao.savePetitionTemplateDetails(immigrationTemplate);
		
		
	}

}
