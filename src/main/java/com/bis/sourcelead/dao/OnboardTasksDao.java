package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.OnboardTasks;

public interface OnboardTasksDao {
	
	OnboardTasks saveOnboardTasks(OnboardTasks onboardTasks) throws Exception;
	
	List<OnboardTasks> getOnboardTasksByTemplateId(Long templateId) throws Exception;

}
