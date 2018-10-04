package com.bis.sourcelead.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.EmailAccountSettingsDao;
import com.bis.sourcelead.dto.EmailAccountSettingsDTO;
import com.bis.sourcelead.dto.ReadMailDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.helper.Paginator;
import com.bis.sourcelead.entity.EmailAccountSettings;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.EmailAccountSettingsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.MailBox;

@Service
public class EmailAccountSettingsServiceImpl implements EmailAccountSettingsService{
	
	@Autowired
	private EmailAccountSettingsDao emailAccountSettingsDao;
	
	
	@Autowired
	CommonUtil commonUtil;

	@Override
	public Response<Boolean> StoreEmailAccountSettings(EmailAccountSettingsDTO emailAccountSettingsDTO, User user,
			OrgLocations orgLocations) throws Exception {
		Response<Boolean> response = new Response<>();
		EmailAccountSettings savedEmailAccountSettings = emailAccountSettingsDao.getEmailAccount(emailAccountSettingsDTO.getEmail(),
				emailAccountSettingsDTO.getPassword(), user.getId());
		if (savedEmailAccountSettings != null && savedEmailAccountSettings.getId() != emailAccountSettingsDTO.getId()) {
			response.setResponse(Result.DUPLICATE_EMAIL_CONFIGURATION_RECORD.name());
			response.setResponseMessage(Result.DUPLICATE_EMAIL_CONFIGURATION_RECORD.getValue());
		} else {
			EmailAccountSettings emailAccountSettings;
			Date currentDate = commonUtil.orgLocationCommonDate(new Date(), orgLocations);
			if (emailAccountSettingsDTO.getId() == 0) {
				emailAccountSettings = new EmailAccountSettings();
				emailAccountSettings.setCreatedBy(user.getUserCode());
				emailAccountSettings.setCreatedDate(currentDate);
			} else {
				emailAccountSettings = emailAccountSettingsDao.getEmailAccount(emailAccountSettingsDTO.getId(), user.getId());
				emailAccountSettings.setUpdatedBy(user.getUserCode());
				emailAccountSettings.setUpdatedDate(currentDate);				
			}
			
			emailAccountSettings.setEmail(emailAccountSettingsDTO.getEmail());
			emailAccountSettings.setPassword(emailAccountSettingsDTO.getPassword());
			emailAccountSettings.setIncomingServer(emailAccountSettingsDTO.getIncomingServer());
			emailAccountSettings.setIncomingPort(emailAccountSettingsDTO.getIncomingPort());
			emailAccountSettings.setIncomingSSL(emailAccountSettingsDTO.isIncomingSSL());
			emailAccountSettings.setOutgoingServer(emailAccountSettingsDTO.getOutgoingServer());
			emailAccountSettings.setOutgoingPort(emailAccountSettingsDTO.getOutgoingPort());
			emailAccountSettings.setOutgoingSSL(emailAccountSettingsDTO.isOutgoingSSL());
			
			if (emailAccountSettingsDTO.getEmail().contains("@gmail.com")) {
				emailAccountSettings.setEmailType("gmail");
			} else if (emailAccountSettingsDTO.getEmail().contains("@yahoo.com")) {
				emailAccountSettings.setEmailType("yahoo");
			} else {
				emailAccountSettings.setEmailType("other");
			}
			emailAccountSettings.setUser(user);

			long storedEmailAccountId = emailAccountSettingsDao.storeEmailAccountSettings(emailAccountSettings);
			if (storedEmailAccountId > 0) {
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.FAILED.name());
				response.setResponseMessage(Result.FAILED.getValue());
			}
		}
		return response;
	}

	@Override
	public List<EmailAccountSettings> emailAccountSettingsList(Long loginUserId) throws Exception {
		return emailAccountSettingsDao.emailAccountSettingsList(loginUserId);
	}

	@Override
	public ResponseList<String> emailAccountRead(Long id, Long userId) throws Exception {
		ResponseList<String> responseList = new ResponseList<>();
		EmailAccountSettings emailAccountSettings = emailAccountSettingsDao.getEmailAccount(id, userId);
		String email = emailAccountSettings.getEmail();
		String password = emailAccountSettings.getPassword();
		// String host = "imap.gmail.com";
		String storeType = "imaps";
		responseList = MailBox.readMail(emailAccountSettings.getIncomingServer(), emailAccountSettings.getIncomingPort(), storeType, email, password);
		return responseList;

	}

	@Override
	public ResponseList<ReadMailDTO> emailFolderRead(Long id, String folderName, Long userId, Long orgLocationId, Paginator paginator) {
		ResponseList<ReadMailDTO> responseList = new ResponseList<>();
		try {
			EmailAccountSettings emailAccountSettings = emailAccountSettingsDao.getEmailAccount(id, userId);
			String email = emailAccountSettings.getEmail();
			String password = emailAccountSettings.getPassword();
			// String host = "imap.gmail.com";
			String storeType = "imaps";
			List<ReadMailDTO> mailsList = MailBox.readMailFromFolder(emailAccountSettings.getIncomingServer(),
					emailAccountSettings.getIncomingPort(), storeType, email, password, folderName, userId, orgLocationId, paginator);
			responseList.setData(mailsList);
			responseList.setPaginator(paginator);
			responseList.setResponse(Result.SUCCESS.name());
			responseList.setResponseMessage(Result.SUCCESS.getValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseList;
	}
	
	@Override
	public ReadMailDTO getEmailByUID(long emailAccountId, long userId, long orgId, long orgLocationId, long uid, String folderName, String staticPath) {
		EmailAccountSettings emailAccountSettings = emailAccountSettingsDao.getEmailAccount(emailAccountId, userId);
		String email = emailAccountSettings.getEmail();
		String password = emailAccountSettings.getPassword();
		String storeType = "imaps";
		return MailBox.getMailDTOByUID(emailAccountSettings.getIncomingServer(), emailAccountSettings.getIncomingPort(), storeType, email, password, folderName, orgId, orgLocationId, uid, staticPath);
	}
	
	@Override
	public Response<Boolean> deleteEmailsByUID(long emailAccountId, long userId, long[] messageIds, String folderName, boolean deleteForever) {
		Response<Boolean> response = new Response<>();
		try {
			EmailAccountSettings emailAccountSettings = emailAccountSettingsDao.getEmailAccount(emailAccountId, userId);
			String email = emailAccountSettings.getEmail();
			String password = emailAccountSettings.getPassword();
			String storeType = "imaps";
			boolean flag = MailBox.deleteEmailsByUid(emailAccountSettings.getIncomingServer(), emailAccountSettings.getIncomingPort(), storeType, email, password, messageIds, folderName, deleteForever);
			response.setObject(flag);
			response.setResponse(flag ? Result.SUCCESS.name() : Result.FAILED.name());
			response.setResponseMessage(flag ? Result.SUCCESS.getValue() : Result.FAILED.getValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public void deleteEmailAccount(Long id, Long userId)throws Exception {
		emailAccountSettingsDao.deleteEmailAccount(id, userId);
	}

	@Override
	public EmailAccountSettingsDTO getEmailAccount(Long id, Long userId) throws Exception {
		EmailAccountSettings emailAccountSettings = emailAccountSettingsDao.getEmailAccount(id, userId);
		EmailAccountSettingsDTO emailAccountSettingsDTO = new EmailAccountSettingsDTO();
		emailAccountSettingsDTO.setId(emailAccountSettings.getId());
		emailAccountSettingsDTO.setUserId(emailAccountSettings.getUser().getId());
		emailAccountSettingsDTO.setEmail(emailAccountSettings.getEmail());
		emailAccountSettingsDTO.setPassword(emailAccountSettings.getPassword());
		emailAccountSettingsDTO.setIncomingServer(emailAccountSettings.getIncomingServer());
		emailAccountSettingsDTO.setIncomingPort(emailAccountSettings.getIncomingPort());
		emailAccountSettingsDTO.setIncomingSSL(emailAccountSettings.isIncomingSSL());
		emailAccountSettingsDTO.setOutgoingServer(emailAccountSettings.getOutgoingServer());
		emailAccountSettingsDTO.setOutgoingPort(emailAccountSettings.getOutgoingPort());
		emailAccountSettingsDTO.setOutgoingSSL(emailAccountSettings.isOutgoingSSL());
		emailAccountSettingsDTO.setEmailType(emailAccountSettings.getEmailType());
		return emailAccountSettingsDTO;
	}

}
