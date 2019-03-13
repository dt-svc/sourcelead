package com.bis.sourcelead.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.FinanceConfigureEmailDao;
import com.bis.sourcelead.dto.EmailReminderDTO;
import com.bis.sourcelead.entity.EmailReminder;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.service.FinanceConfigureEmailService;

@Service
public class FinanceConfigureEmailServiceimpl implements FinanceConfigureEmailService{
	
	@Autowired
	FinanceConfigureEmailDao financeConfigureEmailDao;
	
	private static final Logger logger = LoggerFactory.getLogger(FinanceConfigureEmailServiceimpl.class);

	@Override
	public EmailReminder storeFinanceConfigureEmailService(EmailReminderDTO emailreminderConfigurationDTO,
			Org org, OrgLocations orgLocations) {
		logger.debug("In SaveFinanceConfigureEmailServiceimpl class storeFinanceConfigureEmailService method...");
		
		try {
			
			EmailReminder email = new EmailReminder();
			email.setCreatedBy(emailreminderConfigurationDTO.getCreatedBy());
//			email.setScenarioId(scenarioId);
//			email.setScenarioStatus(scenarioStatus);
//			email.setTime(emailreminderConfigurationDTO.getRemindertime());
//			email.setCreatedDate(createdDate);
			//email.setId(1);	///emailreminderConfigurationDTO.getId()
			email.setScenarionName(emailreminderConfigurationDTO.getScenarioName());
//			email.setStatus(status);
			email.setUpdatedBy(emailreminderConfigurationDTO.getUpdatedby());
			email.setUpdatedDate(new Date());
			
			financeConfigureEmailDao.saveFinanceConfigureEmail(email);
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
}
