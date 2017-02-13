package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.PasswordResetsDao;
import com.bis.sourcelead.entity.PasswordResets;
import com.bis.sourcelead.service.PasswordResetsSevice;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class PasswordResetsSeviceImpl implements PasswordResetsSevice{

	@Autowired
	PasswordResetsDao passwordResetsDao;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Override
	public PasswordResets savePasswordResets(PasswordResets passwordResets) throws Exception {
		passwordResets.setCreatedDate(commonUtil.currentDate());
		passwordResets.setUpdatedDate(commonUtil.currentDate());
		return passwordResetsDao.savePasswordResets(passwordResets);
	}

	@Override
	public PasswordResets getPasswordResetsByUserId(Long userId) throws Exception {
		return passwordResetsDao.getPasswordResetsByUserId(userId);
	}

	@Override
	public PasswordResets getPasswordResetsByVerificationCode(String verificationCode) throws Exception {
		return passwordResetsDao.getPasswordResetsByVerificationCode(verificationCode);
	}

	@Override
	public PasswordResets deletePasswordResets(PasswordResets passwordResets) throws Exception {
		return passwordResetsDao.deletePasswordResets(passwordResets);
	}

}
