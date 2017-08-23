package com.bis.sourcelead.service;

import com.bis.sourcelead.dto.ProjectAdditionalDetailsDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectQuestionnaire;
import com.bis.sourcelead.entity.User;

public interface ProjectQuestionnaireService {

	void saveProjectQuestionnaireDetails(ProjectAdditionalDetailsDTO projectAdditionalDetailsDTO,Long projectId,User user,OrgLocations orgLocations) throws Exception;
	
	ProjectQuestionnaire saveProjectQuestionnaire(ProjectQuestionnaire projectQuestionnaire) throws Exception;
	
	ProjectQuestionnaire getProjectQuestionnaireByProjectId(Long projectId) throws Exception;
	
}
