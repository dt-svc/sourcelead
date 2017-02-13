package com.bis.sourcelead.service;

import org.json.JSONObject;

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
	
	public void addUserBySourcelead(User user,JSONObject jsonObj) throws Exception;
	
	public void saveProfessional(JSONObject jsonObj) throws Exception;
	
	public void saveIndividualRecruiter(JSONObject jsonObj) throws Exception;



}
