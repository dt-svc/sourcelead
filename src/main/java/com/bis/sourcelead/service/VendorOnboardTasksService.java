package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.OnboardTasksDTO;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.VendorOnboardTasks;

public interface VendorOnboardTasksService {
	
	List<VendorOnboardTasks> getVendorOnboardTasksByTemplateId(Long templateId) throws Exception;
	
	VendorOnboardTasks saveVendorOnboardTasks(VendorOnboardTasks vendorOnboardTasks) throws Exception;
	
	VendorOnboardTasks getVendorOnboardTasksByTaskId(Long Id) throws Exception;
	
	List<VendorOnboardTasks> getVendorOnboardTasksByTemplateIdsList(List<Long> templateIdsList) throws Exception;
	
	List<VendorOnboardTasks> getVendorOnboardTasksByVendorOnboardId(Long vendorOnboardingId) throws Exception;
	
	VendorOnboardTasks saveVendorOnboardTemplateTask(OnboardTasksDTO onboardTasksDTO,User user) throws Exception;
	
	Integer getTemplateTasksCountByTemplateId(Long templateId) throws Exception;
}
