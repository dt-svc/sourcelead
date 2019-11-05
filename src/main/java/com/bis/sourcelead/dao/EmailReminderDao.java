package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.EmailReminder;

public interface EmailReminderDao {
	
	EmailReminder saveEmailReminderDetails(EmailReminder emailReminder);

	List<EmailReminder> getEmailReminderList();

	List<EmailReminder> getEmailReminderList(String scenarioName);

	List<EmailReminder> getEmailReminderListByTabsIdAndOrgLocationId(Long tabsId, Long orgLocationsId);

	List<EmailReminder> getReminderConfiguredOrgLocations(String scenarioName);
}
