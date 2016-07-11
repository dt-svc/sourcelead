package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.PasswordResetsDao;
import com.bis.sourcelead.entity.PasswordResets;
import com.bis.sourcelead.service.PasswordResetsSevice;

@Service
public class PasswordResetsSeviceImpl implements PasswordResetsSevice{

	@Autowired
	PasswordResetsDao passwordResetsDao;
	
	@Override
	public PasswordResets savePasswordResets(PasswordResets passwordResets) {
		return passwordResetsDao.savePasswordResets(passwordResets);
	}

	@Override
	public PasswordResets getPasswordResetsByUserId(Long userId) {
		return passwordResetsDao.getPasswordResetsByUserId(userId);
	}

	@Override
	public PasswordResets getPasswordResetsByVerificationCode(String verificationCode) {
		return passwordResetsDao.getPasswordResetsByVerificationCode(verificationCode);
	}

	@Override
	public PasswordResets deletePasswordResets(PasswordResets passwordResets) {
		return passwordResetsDao.deletePasswordResets(passwordResets);
	}

}
