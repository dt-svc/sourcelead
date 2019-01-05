package com.bis.sourcelead.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.EmailReminderDao;
import com.bis.sourcelead.dto.EmailReminderDTO;
import com.bis.sourcelead.dto.EmailReminderHierarchyDTO;
import com.bis.sourcelead.entity.EmailReminder;
import com.bis.sourcelead.entity.EmailReminderHierarchy;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.EmailReminderService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class EmailReminderServiceImpl implements EmailReminderService {
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	EmailReminderDao emailReminderDao;

	@SuppressWarnings("null")
	@Override
	public EmailReminder saveEmailReminderDetails(EmailReminderDTO emailReminderDTO, User user, OrgLocations orgLocations) {
		EmailReminder emailReminder = new EmailReminder();
		if(emailReminderDTO.getId() != null) {
			emailReminder.setId(emailReminderDTO.getId());
			emailReminder.setUpdatedBy(user);
			emailReminder.setUpdatedDate(new Date());
		}else {
			emailReminder.setCreatedBy(user);
			emailReminder.setCreatedDate(new Date());
		}
		emailReminder.setOrgLocationsId(orgLocations);
		emailReminder.setStatus(Constants.RECORD_ACTIVE);
		emailReminder.setNotifyTo(emailReminderDTO.getNotifyTo());
		emailReminder.setScenarioId(emailReminderDTO.getScenarioId());
		emailReminder.setScenarionName(emailReminderDTO.getScenarioName());
		
		if(emailReminderDTO.getEmailReminderHierarchyList().size() > 0) {
			Set<EmailReminderHierarchy> emailReminderHierarchyList = null; 
			for(EmailReminderHierarchyDTO emailReminderHierarchyDTO : emailReminderDTO.getEmailReminderHierarchyList()) {
				EmailReminderHierarchy emailReminderHierarchy = new EmailReminderHierarchy();
				if(emailReminderHierarchyDTO.getId() != null) {
					emailReminderHierarchy.setId(emailReminderHierarchyDTO.getId());
					emailReminderHierarchy.setUpdatedBy(user);
					emailReminderHierarchy.setUpdatedDate(new Date());
				}else {
					emailReminderHierarchy.setCreatedBy(user);
					emailReminderHierarchy.setCreateddate(new Date());
				}
				emailReminderHierarchy.setEmailReminder(emailReminder);
				emailReminderHierarchy.setReminderdays(emailReminderHierarchyDTO.getReminderdays());
				emailReminderHierarchy.setRemindertime(emailReminderHierarchyDTO.getRemindertime());
				emailReminderHierarchy.setStatus(Constants.RECORD_ACTIVE);
				emailReminderHierarchyList.add(emailReminderHierarchy);
			}
			emailReminder.setEmailReminderHierarchyList(emailReminderHierarchyList);
		}
		
		return  emailReminderDao.saveEmailReminderDetails(emailReminder);
	}

	@Override
	public List<EmailReminder> getEmailReminderList() {
		return emailReminderDao.getEmailReminderList();
	}
	
	

}
