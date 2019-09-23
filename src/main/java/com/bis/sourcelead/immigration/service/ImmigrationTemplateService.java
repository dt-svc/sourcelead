/**
 *
 */
package com.bis.sourcelead.immigration.service;

import java.util.List;

import com.bis.sourcelead.dto.CandidateEmployerDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateDocDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateFormDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateListDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateTaskDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.ImmigrationTemplate;
import com.bis.sourcelead.entity.ImmigrationTemplateTasks;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

/**
 * @author Srinivas.Sabbu
 *
 */
public interface ImmigrationTemplateService {

    Long savePetitionTemplateDetails(ImmigrationTemplateDTO templateDTO, User user, OrgLocations orgLocations, String templateId);

    ImmigrationTemplateDTO getPetitionTemplateDetails(Long templateId);
    
    ImmigrationTemplate getImmigrationTemplateByIdAndLocationId(String templateName,Long templateId,Long orgLocationId);

    List<ImmigrationTemplateDocDTO> savePetitionTemplateDocDetails(ImmigrationTemplateDTO templateDTO, User user,OrgLocations orgLocations);

    void savePetitionTemplateTaskDetails(ImmigrationTemplateTaskDTO templateDTO, User user,OrgLocations orgLocations);

    ResponseList<ImmigrationTemplateListDTO> getAllTemplateDetails(ImmigrationTemplateListDTO templateListDTO, String searchValue, Long orgLocationId, String[] countriesList);

    List<ImmigrationTemplateFormDTO> savePetitionTemplateFormDetails(ImmigrationTemplateDTO templateDTO, User user,OrgLocations orgLocations);

    ResponseList<ImmigrationTemplateTaskDTO> getTemplateTaskList(ImmigrationTemplateTaskDTO templateDTO, String searchValue) throws Exception;

    ImmigrationTemplateTaskDTO editImmigrationTaskList(String id) throws Exception;

    List<ImmigrationTemplateDocDTO> getImmigrationTemplateDocsListById(Long templateId);

    List<ImmigrationTemplateFormDTO> getImmigrationTemplateformsListById(Long templateId);

    void deleteAllPetitionTemplateDetailsById(Long templateId,String reason);
    
    void reactiveImmigrationTemplateById(Long templateId);

    void deleteImmigrationTaskList(String id, String taskName);

    int savePetitionTemplateDetailsValues(String templateId);

    List<CandidateEmployerDTO> getPetitionTemplateEmployers(Long id);

    List<LookUpData> getLookupDataByType(String type);

    public boolean validateTemplateNameExistance(String templateName, Long orgLocationId);

	List<LookUpData> getLookupDataByTypeAndLocation(String petitionType, String locationVal);

	Long getTemplateIdByUUID(String immiTemplateUUID);

	List<ImmigrationTemplateTasks> getImmigrationTemplateTasksByTempId(Long templateId);

	boolean validateTemplateTaskNameExistance(String templateTaskName, Long templateId, Long immiTaskId);

}
