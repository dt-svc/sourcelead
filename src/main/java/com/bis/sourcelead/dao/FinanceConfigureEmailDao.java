package com.bis.sourcelead.dao;

import com.bis.sourcelead.dto.EmailReminderDTO;
import com.bis.sourcelead.entity.EmailReminder;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;

public interface FinanceConfigureEmailDao {
	
	public EmailReminder saveFinanceConfigureEmail(EmailReminder email) throws Exception;

}
