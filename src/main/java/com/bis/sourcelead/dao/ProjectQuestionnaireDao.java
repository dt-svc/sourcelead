package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.ProjectQuestionnaire;

public interface ProjectQuestionnaireDao {

	ProjectQuestionnaire saveProjectQuestionnaire(ProjectQuestionnaire projectQuestionnaire) throws Exception;
	
	ProjectQuestionnaire getProjectQuestionnaireByProjectId(Long projectId) throws Exception;
}
