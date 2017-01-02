package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserSignature;

public interface UserSignatureService {

	public UserSignature saveUserSignature(UserSignature userSignature) throws Exception;
	
	List<UserSignature> getUserSignatureListByUserIdAndLocationId(Long userId,Long orgLocationId) throws Exception;
	
	void setUserSignatureDetails(JSONObject jsonObj,User user,OrgLocations orgLocations) throws Exception;
	
	public UserSignature getUserSignatureById(Long Id) throws Exception;
}
