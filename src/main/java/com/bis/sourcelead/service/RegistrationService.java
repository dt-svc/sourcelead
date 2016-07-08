package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.PasswordResets;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.form.Registration;

public interface RegistrationService {
	
	public void addRegistration(Registration registration) throws Exception;
	
	public void addPasswordResets(User user) throws Exception;
	
	public void resetPassword(PasswordResets passwordResets,String password) throws Exception;
	
	public void deletePasswordResets(PasswordResets passwordResets) throws Exception;

}
