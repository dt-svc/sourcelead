package com.bis.sourcelead.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.EmailAccountSettingsDao;
import com.bis.sourcelead.dto.EmailAccountSettingsDTO;
import com.bis.sourcelead.entity.EmailAccountSettings;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.EmailAccountSettingsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ReadMail;

@Service
public class EmailAccountSettingsServiceImpl implements EmailAccountSettingsService{
	
	@Autowired
	private EmailAccountSettingsDao emailAccountSettingsDao;
	
	@Autowired
	CommonUtil commonUtil;

	@Override
	public EmailAccountSettings StoreEmailAccountSettings(EmailAccountSettingsDTO emailAccountSettingsDTO,User user,OrgLocations orgLocations) throws Exception {
		EmailAccountSettings emailAccountSettings=new EmailAccountSettings();
		if(emailAccountSettingsDTO.getEmail() != null){
			emailAccountSettings.setEmail(emailAccountSettingsDTO.getEmail());
		}
		if(emailAccountSettingsDTO.getPassword() != null){
			emailAccountSettings.setPassword(emailAccountSettingsDTO.getPassword());
		}
		if(emailAccountSettingsDTO.getRecieveEmail() != null){
			emailAccountSettings.setRecieveEmail(emailAccountSettingsDTO.getRecieveEmail());
		}
		if(emailAccountSettingsDTO.getRecievePort()!=null){
			emailAccountSettings.setRecievePort(emailAccountSettingsDTO.getRecievePort());
		}
		if(emailAccountSettingsDTO.getRecieveServerName()!=null){
			emailAccountSettings.setRecieveServerName(emailAccountSettingsDTO.getRecieveServerName());
		}
		
		if(emailAccountSettingsDTO.getRecieveSSL()!=null){
			emailAccountSettings.setRecieveSSL(emailAccountSettingsDTO.getRecieveSSL());
		}
		if(emailAccountSettingsDTO.getSendEmail()!=null){
			emailAccountSettings.setSendEmail(emailAccountSettingsDTO.getSendEmail());
		}
		if(emailAccountSettingsDTO.getSendPort()!=null){
			emailAccountSettings.setSendPort(emailAccountSettingsDTO.getSendPort());
		}
		if(emailAccountSettingsDTO.getSendServerName()!=null){
			emailAccountSettings.setSendServerName(emailAccountSettingsDTO.getSendServerName());
		}
		if(emailAccountSettingsDTO.getEmail().contains("@gmail.com")){
			emailAccountSettings.setEmailType("gmail");
		}else if(emailAccountSettingsDTO.getEmail().contains("@yahoo.com")){
			emailAccountSettings.setEmailType("yahoo");
		}else{
			emailAccountSettings.setEmailType("other");
		}
		emailAccountSettings.setUserId(user);
		emailAccountSettings.setCreatedBy(user.getUserCode());
		emailAccountSettings.setUpdatedBy(user.getUserCode());
		Date currentDate = commonUtil.orgLocationCommonDate(new Date(), orgLocations);
		emailAccountSettings.setCreatedDate(currentDate);
		emailAccountSettings.setUpdatedDate(currentDate);
		
		if(emailAccountSettingsDTO.getSendSSL()!=null){
			emailAccountSettings.setSendSSL(emailAccountSettingsDTO.getSendSSL());
		}
		 return emailAccountSettingsDao.storeEmailAccountSettings(emailAccountSettings);
		
	}

	@Override
	public List<EmailAccountSettings> emailAccountSettingsList(Long loginUserId) throws Exception {
		return emailAccountSettingsDao.emailAccountSettingsList(loginUserId);
	}

	@Override
	public boolean emailAccountRead(Long id, Long userId) {
		
		EmailAccountSettings emailAccountSettings=emailAccountSettingsDao.getEmailAccount(id, userId);
		String email=emailAccountSettings.getEmail();
		String password=emailAccountSettings.getPassword();
		String host = "imap.gmail.com";
		String storeType = "imaps";
		@SuppressWarnings("unused")
		boolean successval=ReadMail.readMail(host, storeType, email, password);
		return successval;
		
	}

}
