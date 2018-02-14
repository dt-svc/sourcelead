package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.VendorOnboardTasks;

public interface VendorOnboardTasksDao {

	List<VendorOnboardTasks> getVendorOnboardTasksByTemplateId(Long templateId) throws Exception;
	
	VendorOnboardTasks saveVendorOnboardTasks(VendorOnboardTasks vendorOnboardTasks) throws Exception;
	
	VendorOnboardTasks getVendorOnboardTasksByTaskId(Long Id) throws Exception;
	
	List<VendorOnboardTasks> getVendorOnboardTasksByTemplateIdsList(List<Long> templateIdsList) throws Exception;
	
	List<VendorOnboardTasks> getVendorOnboardTasksByVendorOnboardId(Long vendorOnboardingId) throws Exception;
	
	Integer getTemplateTasksCountByTemplateId(Long templateId) throws Exception;
}
