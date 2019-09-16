package com.bis.sourcelead.workflow.dto;

public class RequirementQuestionnaireTasksDTO{

	
	private String questionnaireId;
	
	private String stageId;
	
	private String questionnaireName;
	
	private String questionnaireUuid;;

	private String tasksCount;

	private String stageName;
	

	public String getQuestionnaireUuid() {
		return questionnaireUuid;
	}

	public void setQuestionnaireUuid(String questionnaireUuid) {
		this.questionnaireUuid = questionnaireUuid;
	}

	public String getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(String questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public String getStageId() {
		return stageId;
	}

	public void setStageId(String stageId) {
		this.stageId = stageId;
	}

	public String getQuestionnaireName() {
		return questionnaireName;
	}

	public void setQuestionnaireName(String questionnaireName) {
		this.questionnaireName = questionnaireName;
	}

	public String getTasksCount() {
		return tasksCount;
	}

	public void setTasksCount(String tasksCount) {
		this.tasksCount = tasksCount;
	}

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	
	
}
