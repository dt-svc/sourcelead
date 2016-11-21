package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.PasswordResets;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.form.Registration;

public interface RegistrationService {
	
	public void addRegistration(Registration registration,User user,String modules,String subModules, Long orgLocationsId) throws Exception;
	
	public void sendEmail(Registration registration,Long locationsId) throws Exception;
	
	public void sendResetPasswordEmail(User user,Long locationsId, String verificationCode) throws Exception;
	
	public void sendUpdatePasswordEmail(User user,Long locationsId) throws Exception;
	
	public String addPasswordResets(User user) throws Exception;
	
	public void resetPassword(PasswordResets passwordResets,String password) throws Exception;
	
	public void deletePasswordResets(PasswordResets passwordResets) throws Exception;



}
