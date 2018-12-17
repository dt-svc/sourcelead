package com.bis.sourcelead.immigration.dao;

import java.util.List;

import com.bis.sourcelead.dto.ImmigrationTemplateDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateDocDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateFormDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateListDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateTaskDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.CandidateIdendities;
import com.bis.sourcelead.entity.CandidateLcaJobDetails;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.Immigration;
import com.bis.sourcelead.entity.ImmigrationTemplate;
import com.bis.sourcelead.entity.ImmigrationTemplateDocs;
import com.bis.sourcelead.entity.ImmigrationTemplateForms;
import com.bis.sourcelead.entity.ImmigrationTemplateTasks;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.User;

/**
 * @author Srinivas.Sabbu
 *
 */
public interface ImmigrationTemplateDao {

    Long savePetitionTemplateDetails(ImmigrationTemplate immigrationTemplate, String templateId);

    ImmigrationTemplateDTO getPetitionTemplateDetails(Long templateId);
    
    ImmigrationTemplate getImmigrationTemplateById(Long templateId);
    
    ImmigrationTemplate getImmigrationTemplateByIdAndLocationId(String templateName,Long templateId,Long orgLocationId);

    ImmigrationTemplateDocs savePetitionTemplateDocDetails(ImmigrationTemplateDocs immigrationTemplateDocs);

    void savePetitionTemplateTaskDetails(ImmigrationTemplateTasks immigrationTemplateTask);

    void deletePetitionTemplateDocDetails(Long remainingDocsIds[]);

    void deletePetitionTemplateFormDetails(Long remainingFormIds[]);

    ResponseList<ImmigrationTemplateListDTO> getAllTemplateDetails(ImmigrationTemplateListDTO templateListDTO, String searchValue, Long orgLocationId, String[] countriesList);

    ImmigrationTemplateForms savePetitionTemplateFormDetails(ImmigrationTemplateForms immigrationTemplateForms);

    ImmigrationTemplateForms getImmigrationTemplateFormsById(Long immigrationId);

    ResponseList<ImmigrationTemplateTaskDTO> getTemplateTaskList(ImmigrationTemplateTaskDTO templateDTO, String searchValue) throws Exception;

    ImmigrationTemplateTasks editImmigrationTaskList(String id);

    List<ImmigrationTemplateDocDTO> getImmigrationTemplateDocsListById(Long templateId);
    
    List<ImmigrationTemplateDocs> getImmigrationTemplateDocsList(Long templateId);
    
    List<ImmigrationTemplateForms> getImmigrationTemplateFormsList(Long templateId);

    List<ImmigrationTemplateFormDTO> getImmigrationTemplateformsListById(Long templateId);

    void deleteAllPetitionTemplateDetailsById(Long templateId,String reason);
    
    void reactiveImmigrationTemplateById(Long templateId);

    User getUserById(Long Id) throws Exception;

    void deleteImmigrationTaskList(String id);

    int savePetitionTemplateDetailsValues(String templateId);

    List<CandidateEmployers> getPetitionTemplateEmployers(Long id);

    List<LookUpData> getLookupDataByType(String type);

    //old code
   /* void savePetitionDetails(Immigration immigration, Candidate candidate, CandidateIdendities candidateIdendities,
            CandidateResumes candidateResume, List<CandidateLcaI94Summary> lcaSummaryLst) throws Exception;*/
    //new code
    void savePetitionDetails(Immigration immigration, Candidate candidate, CandidateIdendities candidateIdendities,
            CandidateResumes candidateResume, CandidateLcaJobDetails canLcaJobDetails) throws Exception;

    public boolean validateTemplateNameExistance(String templateName, Long orgLocationId);

	List<LookUpData> getLookupDataByTypeAndLocation(String petitionType, String locationVal);

	ImmigrationTemplateTasks getImmigratonTemplateTasksById(Long id);

	List<ImmigrationTemplateTasks> getImmigratonTemplateTasksByTemplateId(Long templateId);

	Long getTemplateIdByUUID(String immiTemplateUUID);

	boolean validateTemplateTaskNameExistance(String templateTaskName, Long templateId, Long immiTaskId);

}
