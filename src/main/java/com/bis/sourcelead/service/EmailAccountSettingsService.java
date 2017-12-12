package com.bis.sourcelead.service;

import java.util.List;


import com.bis.sourcelead.dto.EmailAccountSettingsDTO;
import com.bis.sourcelead.dto.ReadMailDTO;
import com.bis.sourcelead.entity.EmailAccountSettings;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface EmailAccountSettingsService {
	
	public EmailAccountSettings StoreEmailAccountSettings(EmailAccountSettingsDTO emailAccountSettingsDTO,User user,OrgLocations orgLocations) throws Exception;

	List<EmailAccountSettings> emailAccountSettingsList(Long loginUserId)throws Exception;
	
	public List<String> emailAccountRead(Long id,Long user)throws Exception;
	
	public List<ReadMailDTO> emailFolderRead(Long id,String folderName,Long userId);

}
