package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.EmailReminder;

public interface EmailReminderDao {
	
	EmailReminder saveEmailReminderDetails(EmailReminder emailReminder);
	
	EmailReminder getEmailReminderById(Long id);

	List<EmailReminder> getEmailReminderList();

	List<EmailReminder> getEmailReminderList(String scenarioName);

	List<EmailReminder> getEmailReminderListByTabsIdAndOrgLocationId(Long tabsId, Long orgLocationsId);
}
