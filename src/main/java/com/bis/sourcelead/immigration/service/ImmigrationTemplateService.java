/**
 * 
 */
package com.bis.sourcelead.immigration.service;

import java.util.List;

import com.bis.sourcelead.dto.ImmigrationTemplateDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateDocDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateFormDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateListDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.ImmigrationTemplateView;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

/**
 * @author Srinivas.Sabbu
 *
 */
public interface ImmigrationTemplateService {
	Long savePetitionTemplateDetails(ImmigrationTemplateDTO templateDTO, User user, OrgLocations orgLocations, String templateId);
	
	ImmigrationTemplateDTO getPetitionTemplateDetails(Long templateId);
	
	List<ImmigrationTemplateDocDTO> savePetitionTemplateDocDetails(ImmigrationTemplateDTO templateDTO, User user);
	
	void savePetitionTemplateTaskDetails(ImmigrationTemplateDTO templateDTO, User user);
	
	ResponseList<ImmigrationTemplateListDTO> getAllTemplateDetails(ImmigrationTemplateListDTO templateListDTO, String searchValue, Long orgLocationId);
	
	List<ImmigrationTemplateFormDTO> savePetitionTemplateFormDetails(ImmigrationTemplateDTO templateDTO, User user);

	ResponseList<ImmigrationTemplateDTO> getTemplateTaskList(ImmigrationTemplateDTO templateDTO, String searchValue);

	ImmigrationTemplateDTO editImmigrationTaskList(String id) throws Exception;
	
	List<ImmigrationTemplateDocDTO> getImmigrationTemplateDocsListById(Long templateId);
	
	List<ImmigrationTemplateFormDTO> getImmigrationTemplateformsListById(Long templateId);
	
	void deleteAllPetitionTemplateDetailsById(Long templateId);
	
}
