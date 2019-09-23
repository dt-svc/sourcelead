package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.EmailAccountSettings;
import com.bis.sourcelead.entity.User;

public interface EmailAccountSettingsDao {
	
	public long storeEmailAccountSettings(EmailAccountSettings emailAccountSettings) throws Exception;
	
	List<EmailAccountSettings> emailAccountSettingsList(Long loginUserId)throws Exception;
	
	EmailAccountSettings getEmailAccount(Long id,Long userId);
	
	EmailAccountSettings getEmailAccount(String email, String password, long userId);
	
	public void deleteEmailAccount(Long id, Long userId)throws Exception;

}
