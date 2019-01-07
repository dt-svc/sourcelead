package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.EmailReminderDTO;
import com.bis.sourcelead.entity.EmailReminder;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface EmailReminderService {
	
	EmailReminder saveEmailReminderDetails(EmailReminderDTO emailReminder, User user, OrgLocations orgLocations);
	
	List<EmailReminder> getEmailReminderList();

}
