package com.bis.sourcelead.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.VendorOnboardTasksDao;
import com.bis.sourcelead.dto.OnboardTasksDTO;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.VendorOnboardTasks;
import com.bis.sourcelead.entity.VendorOnboarding;
import com.bis.sourcelead.entity.VendorOnboardingTemplate;
import com.bis.sourcelead.service.VendorOnboardTasksService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class VendorOnboardTasksServiceImpl implements VendorOnboardTasksService{
	
	@Autowired
	VendorOnboardTasksDao vendorOnboardTasksDao;

	@Override
	public List<VendorOnboardTasks> getVendorOnboardTasksByTemplateId(Long templateId) throws Exception {
		return vendorOnboardTasksDao.getVendorOnboardTasksByTemplateId(templateId);
	}

	@Override
	public VendorOnboardTasks saveVendorOnboardTasks(VendorOnboardTasks vendorOnboardTasks) throws Exception {
		return vendorOnboardTasksDao.saveVendorOnboardTasks(vendorOnboardTasks);
	}

	@Override
	public VendorOnboardTasks getVendorOnboardTasksByTaskId(Long Id) throws Exception {
		return vendorOnboardTasksDao.getVendorOnboardTasksByTaskId(Id);
	}

	@Override
	public List<VendorOnboardTasks> getVendorOnboardTasksByTemplateIdsList(List<Long> templateIdsList) throws Exception {
		return vendorOnboardTasksDao.getVendorOnboardTasksByTemplateIdsList(templateIdsList);
	}

	@Override
	public List<VendorOnboardTasks> getVendorOnboardTasksByVendorOnboardId(Long vendorOnboardingId) throws Exception {
		return vendorOnboardTasksDao.getVendorOnboardTasksByVendorOnboardId(vendorOnboardingId);
	}

	@Override
	public VendorOnboardTasks saveVendorOnboardTemplateTask(OnboardTasksDTO onboardTasksDTO, User user)
			throws Exception {
		
		VendorOnboardTasks onboardTasks = new VendorOnboardTasks();
		 if(StringUtils.isNotBlank(onboardTasksDTO.getTaskName())){
			 onboardTasks.setTaskName(onboardTasksDTO.getTaskName());
		 }
		 
		 if(StringUtils.isNotBlank(onboardTasksDTO.getTemplateId())){
			 VendorOnboardingTemplate template = new VendorOnboardingTemplate();
			 template.setId(Long.valueOf(onboardTasksDTO.getTemplateId()));
			 onboardTasks.setVendorOnboardingTemplateId(template);
		 }
		
		 
		 if(StringUtils.isNotBlank(onboardTasksDTO.getOnboardAssignedTo())){
			 onboardTasks.setAssignToUsers(onboardTasksDTO.getOnboardAssignedTo());
		 }else{
			 onboardTasks.setAssignToVendor(Constants.YES);
		 }
		 
		 if(StringUtils.isNotBlank(onboardTasksDTO.getTaskPriority())){
			 onboardTasks.setPriority(onboardTasksDTO.getTaskPriority());
		 }
		 
		 if(StringUtils.isNotBlank(onboardTasksDTO.getTaskRepeat())){
			 onboardTasks.setTaskRepeat(onboardTasksDTO.getTaskRepeat());
		 }
		 
		 if(StringUtils.isNotBlank(onboardTasksDTO.getTaskMonths())){
			 onboardTasks.setDueAfterMonths(Integer.parseInt(onboardTasksDTO.getTaskMonths()));
		 }
		 
		 if(StringUtils.isNotBlank(onboardTasksDTO.getTaskDays())){
			 onboardTasks.setDueAfterDays(Integer.parseInt(onboardTasksDTO.getTaskDays()));
		 }
		 
		 if(StringUtils.isNotBlank(onboardTasksDTO.getTaskHours())){
			 onboardTasks.setDueAfterHours(Integer.parseInt(onboardTasksDTO.getTaskHours()));
		 }
		 
		 if(StringUtils.isNotBlank(onboardTasksDTO.getTaskMinutes())){
			 onboardTasks.setDueAfterMinutes(Integer.parseInt(onboardTasksDTO.getTaskMinutes()));
		 }
		 
		 if(StringUtils.isNotBlank(onboardTasksDTO.getTaskNotification())){
			 onboardTasks.setTaskNotification(onboardTasksDTO.getTaskNotification());
		 }
		 
		 if(StringUtils.isNotBlank(onboardTasksDTO.getTaskEmail())){
			 onboardTasks.setTaskEmail(onboardTasksDTO.getTaskEmail());
		 }
		 
		 if(StringUtils.isNotBlank(onboardTasksDTO.getTaskdescription())){
			 onboardTasks.setDescription(onboardTasksDTO.getTaskdescription());
		 }
		 
		 if(StringUtils.isNotBlank(onboardTasksDTO.getVendorOnboardId())){
			 
			 VendorOnboarding onboarding = new VendorOnboarding();
			 onboarding.setId(Long.valueOf(onboardTasksDTO.getVendorOnboardId()));
			 onboardTasks.setVendorOnboardingId(onboarding);
			 
			 onboardTasks.setTaskType("Vendor Task");
		 }else{
			 onboardTasks.setTaskType("Onboard Task");
		 }
		
		 onboardTasks.setStatus(Constants.CANDIDATE_ACTIVE);
		 onboardTasks.setCreatedDate(CommonUtil.currentDate());
		 onboardTasks.setCreatedBy(user);
		 onboardTasks.setUpdatedDate(CommonUtil.currentDate());
		 onboardTasks.setUpdatedBy(user);
		 
		 VendorOnboardTasks tasks= vendorOnboardTasksDao.saveVendorOnboardTasks(onboardTasks);
		 return tasks;
	}

	@Override
	public Integer getTemplateTasksCountByTemplateId(Long templateId) throws Exception {
		return vendorOnboardTasksDao.getTemplateTasksCountByTemplateId(templateId);
	}

}
