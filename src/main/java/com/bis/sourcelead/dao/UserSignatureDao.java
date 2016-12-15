package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.UserSignature;

public interface UserSignatureDao {

	public UserSignature saveUserSignature(UserSignature userSignature) throws Exception;
	
	List<UserSignature> getUserSignatureListByUserIdAndLocationId(Long userId,Long orgLocationId) throws Exception;
	
	public UserSignature getUserSignatureById(Long Id) throws Exception;
}
