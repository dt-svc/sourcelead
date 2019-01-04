package com.bis.sourcelead.service;

import com.bis.sourcelead.dto.EmailReminderDTO;
import com.bis.sourcelead.entity.EmailReminder;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;

public interface FinanceConfigureEmailService {
	
	public	EmailReminder storeFinanceConfigureEmailService(EmailReminderDTO emailreminderConfigurationDTO, Org org, OrgLocations orgLocations);

}
