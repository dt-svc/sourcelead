package com.bis.sourcelead.service.impl;

import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.UserSignatureDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserSignature;
import com.bis.sourcelead.service.UserSignatureService;

@Service
public class UserSignatureServiceImpl implements UserSignatureService{
	
	@Autowired
	UserSignatureDao userSignatureDao;

	@Override
	public UserSignature saveUserSignature(UserSignature userSignature) throws Exception {
		return userSignatureDao.saveUserSignature(userSignature);
	}

	@Override
	public List<UserSignature> getUserSignatureListByUserIdAndLocationId(Long userId, Long orgLocationId)
			throws Exception {
		return userSignatureDao.getUserSignatureListByUserIdAndLocationId(userId, orgLocationId);
	}

	@Override
	public void setUserSignatureDetails(JSONObject jsonObj, User user, OrgLocations orgLocations) throws Exception{
		
		UserSignature signature = new UserSignature();
		signature.setUserId(user);
		signature.setOrgLocationId(orgLocations);
		signature.setSignatureName(jsonObj.getString("signatureName"));
		signature.setSignature(jsonObj.getString("signatureText"));
		signature.setStatus(1);
		signature.setCreatedBy(user.getUserCode());
		signature.setCreatedDate(new Date());
		signature.setUpdatedBy(user.getUserCode());
		signature.setUpdatedDate(new Date());
		userSignatureDao.saveUserSignature(signature);
		
	}

	@Override
	public UserSignature getUserSignatureById(Long Id) throws Exception {
		return userSignatureDao.getUserSignatureById(Id);
	}

	

}
