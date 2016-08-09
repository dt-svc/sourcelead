package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.PasswordResets;

public interface PasswordResetsDao {

	public PasswordResets savePasswordResets(PasswordResets passwordResets) throws Exception;
	
	public PasswordResets getPasswordResetsByUserId(Long userId) throws Exception;
	
	public PasswordResets getPasswordResetsByVerificationCode(String verificationCode) throws Exception;
	
	public PasswordResets deletePasswordResets(PasswordResets passwordResets) throws Exception;
}
