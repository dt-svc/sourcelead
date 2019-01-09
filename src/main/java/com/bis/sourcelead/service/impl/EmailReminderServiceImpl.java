package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.EmailReminderDao;
import com.bis.sourcelead.dto.EmailReminderDTO;
import com.bis.sourcelead.dto.EmailReminderHierarchyDTO;
import com.bis.sourcelead.entity.EmailReminder;
import com.bis.sourcelead.entity.EmailReminderHierarchy;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Tabs;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.EmailReminderService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class EmailReminderServiceImpl implements EmailReminderService {
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	EmailReminderDao emailReminderDao;
	
	@Autowired
	EmailScenarioService emailScenarioService;

	@SuppressWarnings("null")
	@Override
	public EmailReminder saveEmailReminderDetails(EmailReminderDTO emailReminderDTO, User user, OrgLocations orgLocations) throws Exception {
		EmailReminder emailReminder = new EmailReminder();
		if(emailReminderDTO.getId() != null) {
			emailReminder.setId(emailReminderDTO.getId());
			emailReminder.setUpdatedBy(user);
			emailReminder.setUpdatedDate(new Date());
		}else {
			emailReminder.setCreatedBy(user);
			emailReminder.setCreatedDate(new Date());
		}
		
		EmailScenario emailScenario = emailScenarioService.getEmailScenarioByUniqueName(emailReminderDTO.getScenarioName(), "Email");
		emailReminder.setScenarioId(emailScenario.getId());
		emailReminder.setOrgLocationsId(orgLocations);
		Tabs tab = new Tabs();
		tab.setId(emailReminderDTO.getTabsId());
		emailReminder.setTabsId(tab);
		emailReminder.setStatus(Constants.RECORD_ACTIVE);
		emailReminder.setNotifyTo(emailReminderDTO.getNotifyTo().trim().toLowerCase());
		emailReminder.setScenarioId(emailReminderDTO.getScenarioId());
		emailReminder.setScenarionName(emailReminderDTO.getScenarioName());
		
		if(emailReminderDTO.getEmailReminderHierarchyList().size() > 0) {
			List<EmailReminderHierarchy> emailReminderHierarchyList = new ArrayList<>();
			for(EmailReminderHierarchyDTO emailReminderHierarchyDTO : emailReminderDTO.getEmailReminderHierarchyList()) {
				if(emailReminderHierarchyDTO != null && emailReminderHierarchyDTO.getReminderdays() != null && !StringUtils.isBlank(emailReminderHierarchyDTO.getRemindertime())) {
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
					emailReminderHierarchy.setReminderhierarchy(emailReminderHierarchyDTO.getReminderhierarchy());
					emailReminderHierarchy.setStatus(Constants.RECORD_ACTIVE);
					emailReminderHierarchyList.add(emailReminderHierarchy);
				}
			}
			emailReminder.setEmailReminderHierarchyList(emailReminderHierarchyList);
		}
		
		return  emailReminderDao.saveEmailReminderDetails(emailReminder);
	}

	@Override
	public List<EmailReminder> getEmailReminderList() {
		return emailReminderDao.getEmailReminderList();
	}

	@Override
	public List<EmailReminder> getEmailReminderListByEmailScenarioName(String scenarioName) {
		return emailReminderDao.getEmailReminderList(scenarioName);
	}

	@Override
	public List<EmailReminder> getEmailReminderListByTabsIdAndOrgLocationId(Long tabsId, Long orgLocationsId) {
		return emailReminderDao.getEmailReminderListByTabsIdAndOrgLocationId(tabsId, orgLocationsId);
	}
	
	

}
