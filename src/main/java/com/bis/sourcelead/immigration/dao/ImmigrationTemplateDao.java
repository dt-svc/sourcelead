package com.bis.sourcelead.immigration.dao;

import java.util.List;

import com.bis.sourcelead.entity.ImmigrationTemplate;
import com.bis.sourcelead.entity.ImmigrationTemplateDocs;
import com.bis.sourcelead.entity.ImmigrationTemplateForms;
import com.bis.sourcelead.entity.ImmigrationTemplateTasks;
import com.bis.sourcelead.entity.ImmigrationTemplateView;
/**
 * @author Srinivas.Sabbu
 *
 */
public interface ImmigrationTemplateDao {

	Long savePetitionTemplateDetails(ImmigrationTemplate immigrationTemplate, String templateId);
	
	void savePetitionTemplateDocDetails(ImmigrationTemplateDocs immigrationTemplateDocs);
	
	void savePetitionTemplateTaskDetails(ImmigrationTemplateTasks immigrationTemplateTask);
	
	void deletePetitionTemplateDocDetails(Long id);
	
	List<ImmigrationTemplateView> getAllTemplateDetails();
	
	void savePetitionTemplateFormDetails(ImmigrationTemplateForms immigrationTemplateForms);
	
	
}
