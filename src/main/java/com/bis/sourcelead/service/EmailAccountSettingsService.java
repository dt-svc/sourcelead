package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.EmailAccountSettingsDTO;
import com.bis.sourcelead.entity.EmailAccountSettings;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface EmailAccountSettingsService {
	
	public EmailAccountSettings StoreEmailAccountSettings(EmailAccountSettingsDTO emailAccountSettingsDTO,User user,OrgLocations orgLocations) throws Exception;

	List<EmailAccountSettings> emailAccountSettingsList(Long loginUserId)throws Exception;
	
	public boolean emailAccountRead(Long id,Long user);

}
