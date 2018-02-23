package com.bis.sourcelead.immigration.dao;

import java.util.ArrayList;
import java.util.List;

import com.bis.sourcelead.dto.ImmigrationTemplateDocDTO;
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
	
	Long savePetitionTemplateDocDetails(ImmigrationTemplateDocs immigrationTemplateDocs);
	
	void savePetitionTemplateTaskDetails(ImmigrationTemplateTasks immigrationTemplateTask);
	
	void deletePetitionTemplateDocDetails(String remainingDocsIds);
	
	void deletePetitionTemplateFormDetails(String remainingFormIds);
	
	List<ImmigrationTemplateView> getAllTemplateDetails();
	
	ImmigrationTemplateForms savePetitionTemplateFormDetails(ImmigrationTemplateForms immigrationTemplateForms);

	ImmigrationTemplateForms getImmigrationTemplateFormsById(Long immigrationId);

	
	
}
