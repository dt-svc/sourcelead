package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.PasswordResets;

public interface PasswordResetsDao {

	public PasswordResets savePasswordResets(PasswordResets passwordResets) ;
	
	public PasswordResets getPasswordResetsByUserId(Long userId);
	
	public PasswordResets getPasswordResetsByVerificationCode(String verificationCode);
	
	public PasswordResets deletePasswordResets(PasswordResets passwordResets);
}
