package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.PasswordResets;

public interface PasswordResetsSevice {

	public PasswordResets savePasswordResets(PasswordResets passwordResets);
	
	public PasswordResets getPasswordResetsByUserId(Long userId);
	
	public PasswordResets getPasswordResetsByVerificationCode(String verificationCode);
	
	public PasswordResets deletePasswordResets(PasswordResets passwordResets);
}
