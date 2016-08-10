package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.PasswordResets;

public interface PasswordResetsSevice {

	public PasswordResets savePasswordResets(PasswordResets passwordResets) throws Exception;
	
	public PasswordResets getPasswordResetsByUserId(Long userId) throws Exception;
	
	public PasswordResets getPasswordResetsByVerificationCode(String verificationCode) throws Exception;
	
	public PasswordResets deletePasswordResets(PasswordResets passwordResets) throws Exception;
}
