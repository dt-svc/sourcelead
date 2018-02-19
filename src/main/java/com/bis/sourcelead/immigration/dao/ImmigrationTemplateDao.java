package com.bis.sourcelead.immigration.dao;

import com.bis.sourcelead.dto.ImmigrationTemplateDTO;
import com.bis.sourcelead.entity.ImmigrationTemplate;
import com.bis.sourcelead.entity.ImmigrationTemplateDocs;
import com.bis.sourcelead.entity.ImmigrationTemplateForms;
import com.bis.sourcelead.entity.ImmigrationTemplateTasks;
import com.bis.sourcelead.entity.User;

/**
 * @author Srinivas.Sabbu
 *
 */
public interface ImmigrationTemplateDao {

	Long savePetitionTemplateDetails(ImmigrationTemplate immigrationTemplate, String templateId);
	void savePetitionTemplateDocDetails(ImmigrationTemplateDocs immigrationTemplateDocs);
	void savePetitionTemplateTaskDetails(ImmigrationTemplateTasks immigrationTemplateTask);
	void deletePetitionTemplateDocDetails(Long id);
}
