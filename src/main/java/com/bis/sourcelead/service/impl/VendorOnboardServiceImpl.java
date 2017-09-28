package com.bis.sourcelead.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.VendorOnboardDao;
import com.bis.sourcelead.dao.VendorOnboardTasksDao;
import com.bis.sourcelead.dto.OnboardTasksDTO;
import com.bis.sourcelead.dto.VendorOnboardTaskDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.VendorOnboardTasks;
import com.bis.sourcelead.entity.VendorOnboarding;
import com.bis.sourcelead.service.VendorOnboardService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class VendorOnboardServiceImpl implements VendorOnboardService {
	
	@Autowired
	VendorOnboardDao vendorOnboardDao;
	
	@Autowired
	VendorOnboardTasksDao vendorOnboardTasksDao;
	
	@Autowired
	CommonUtil commonUtil;

	@Override
	public VendorOnboarding saveVendorOnboarding(OrgLocations vendorOrgLocations, OrgLocations orgLocations, User user)
			throws Exception {
		
		VendorOnboarding vendorOnboarding = new VendorOnboarding();
		OrgRelations orgRelations = new OrgRelations();
		orgRelations.setId(vendorOrgLocations.getVendorOrgRelationId());
		vendorOnboarding.setOrgRelationId(orgRelations);
		vendorOnboarding.setVendorLocationId(vendorOrgLocations);
		User spocUser = new User();
		spocUser.setId(vendorOrgLocations.getVendorSpocId());
		vendorOnboarding.setSpocUserId(spocUser);
		vendorOnboarding.setVendorOnboardStatus("Draft");
		vendorOnboarding.setOnboardSubmitted(Constants.NO);
		vendorOnboarding.setOnboardUUID(SourceLeadRandomCodeHelper.generateUUID());
		vendorOnboarding.setOrgLocationsId(orgLocations);
		vendorOnboarding.setStatus(1);
		vendorOnboarding.setCreatedBy(user);
		vendorOnboarding.setUpdatedBy(user);
		vendorOnboarding.setCreatedDate(new Date());
		vendorOnboarding.setUpdatedDate(new Date());
		vendorOnboardDao.saveVendorOnboarding(vendorOnboarding);
		
		return vendorOnboarding;
		
	}

	@Override
	public VendorOnboarding getVendorOnboardingByOrgRelationId(Long orgRelationId) throws Exception {
		return vendorOnboardDao.getVendorOnboardingByOrgRelationId(orgRelationId);
	}

	@Override
	public VendorOnboarding getVendorOnboardingById(Long id) throws Exception {
		return vendorOnboardDao.getVendorOnboardingById(id);
	}

	@Override
	public void finishVendorOnboarding(OnboardTasksDTO onboardTasksDTO, User user) throws Exception {

		VendorOnboarding vendorOnboarding = vendorOnboardDao.getVendorOnboardingById(Long.valueOf(onboardTasksDTO.getVendorOnboardId()));
		if(onboardTasksDTO.getVendorOnboardTasksInfo() != null && onboardTasksDTO.getVendorOnboardTasksInfo().size() > 0){
			for(VendorOnboardTaskDTO taskDTO : onboardTasksDTO.getVendorOnboardTasksInfo()) {
				if(StringUtils.isNotBlank(taskDTO.getVendorOnboardTaskId())){
					VendorOnboardTasks task = vendorOnboardTasksDao.getVendorOnboardTasksByTaskId(Long.valueOf(taskDTO.getVendorOnboardTaskId()));
					if(task != null){
						VendorOnboardTasks vendorTask = new VendorOnboardTasks();
						vendorTask.setTaskName(task.getTaskName());
						vendorTask.setVendorOnboardingId(vendorOnboarding);
						vendorTask.setAssignToUsers(task.getAssignToUsers());
						vendorTask.setAssignToVendor(task.getAssignToVendor());
						vendorTask.setPriority(task.getPriority());
						vendorTask.setDueAfterMonths(task.getDueAfterMonths());
						vendorTask.setDueAfterHours(task.getDueAfterHours());
						vendorTask.setDueAfterMinutes(task.getDueAfterMinutes());
						vendorTask.setDueAfterDays(task.getDueAfterDays());
						vendorTask.setTaskRepeat(task.getTaskRepeat());
						vendorTask.setTaskEmail(task.getTaskEmail());
						vendorTask.setTaskNotification(task.getTaskNotification());
						vendorTask.setDescription(task.getDescription());
						vendorTask.setTaskType("Vendor Task");
						vendorTask.setStatus(Constants.CANDIDATE_ACTIVE);
						vendorTask.setCreatedBy(user);
						vendorTask.setUpdatedBy(user);
						vendorTask.setCreatedDate(commonUtil.currentDate());
						vendorTask.setUpdatedDate(commonUtil.currentDate());
						vendorOnboardTasksDao.saveVendorOnboardTasks(vendorTask);
					}
				}
			}
		}
	}

}
