package com.bis.sourcelead.immigration.dao;

import java.util.List;

import com.bis.sourcelead.dto.ImmigrationTemplateDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateDocDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateFormDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateListDTO;
import com.bis.sourcelead.dto.ResponseList;
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
	
	ImmigrationTemplateDTO getPetitionTemplateDetails(Long templateId);
	
	ImmigrationTemplateDocs savePetitionTemplateDocDetails(ImmigrationTemplateDocs immigrationTemplateDocs);
	
	void savePetitionTemplateTaskDetails(ImmigrationTemplateTasks immigrationTemplateTask);
	
	void deletePetitionTemplateDocDetails(String remainingDocsIds);
	
	void deletePetitionTemplateFormDetails(String remainingFormIds);
	
	ResponseList<ImmigrationTemplateListDTO> getAllTemplateDetails(ImmigrationTemplateListDTO templateListDTO, String searchValue,Long orgLocationId);
	
	ImmigrationTemplateForms savePetitionTemplateFormDetails(ImmigrationTemplateForms immigrationTemplateForms);

	ImmigrationTemplateForms getImmigrationTemplateFormsById(Long immigrationId);

	ResponseList<ImmigrationTemplateDTO> getTemplateTaskList(ImmigrationTemplateDTO templateDTO, String searchValue) throws Exception;

	ImmigrationTemplateTasks editImmigrationTaskList(String id);
	
	List<ImmigrationTemplateDocDTO> getImmigrationTemplateDocsListById(Long templateId);
	
	List<ImmigrationTemplateFormDTO> getImmigrationTemplateformsListById(Long templateId);
	
	void deleteAllPetitionTemplateDetailsById(Long templateId);

	User getUserById(Long Id) throws Exception;

	void deleteImmigrationTaskList(String id);

	int savePetitionTemplateDetailsValues(String templateId);
	
	
}
