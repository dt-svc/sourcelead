package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.OnboardTasks;

public interface OnboardTasksService {

	OnboardTasks saveOnboardTasks(OnboardTasks onboardTasks) throws Exception;
	
	List<OnboardTasks> getOnboardTasksByTemplateId(Long templateId) throws Exception;
}
