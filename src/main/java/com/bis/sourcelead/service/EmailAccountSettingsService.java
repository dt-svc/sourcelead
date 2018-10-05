package com.bis.sourcelead.service;

import java.util.List;


import com.bis.sourcelead.dto.EmailAccountSettingsDTO;
import com.bis.sourcelead.dto.ReadMailDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.helper.Paginator;
import com.bis.sourcelead.entity.EmailAccountSettings;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface EmailAccountSettingsService {
	
	public Response<Boolean> StoreEmailAccountSettings(EmailAccountSettingsDTO emailAccountSettingsDTO,User user,OrgLocations orgLocations) throws Exception;

	List<EmailAccountSettings> emailAccountSettingsList(Long loginUserId)throws Exception;
	
	public ResponseList<String> emailAccountRead(Long id,Long user)throws Exception;
	
	public ResponseList<ReadMailDTO> emailFolderRead(Long id,String folderName,Long userId, Long orgLocationId, Paginator paginator);
	
	public void deleteEmailAccount(Long id,Long userId)throws Exception;
	
	public EmailAccountSettingsDTO getEmailAccount(Long id,Long userId)throws Exception;

	ReadMailDTO getEmailByUID(long emailAccountId, long userId, long orgId, long orgLocationId, long uid, String folderName, String staticPath);

	public Response<Boolean> deleteEmailsByUID(long emailAccountId, long userId, long[] messageIds, String folderName, boolean deleteForever);

}
