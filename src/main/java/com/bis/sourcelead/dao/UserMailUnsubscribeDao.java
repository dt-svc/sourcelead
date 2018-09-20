package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.UserMailUnsubscribe;

/**
 * @author Venkat Kesav
 *
 */
public interface UserMailUnsubscribeDao {
	
	public UserMailUnsubscribe saveOrUpdateUserMailUnsubscription(UserMailUnsubscribe userMailUnsubscribe) throws Exception;
	
	public UserMailUnsubscribe getUserMailUnsubscriptionByUserId(Long userId) throws Exception;
	
	public UserMailUnsubscribe getUserMailUnsubscriptionByUserEmail(String userEmail) throws Exception;
	
	public UserMailUnsubscribe getUserMailUnsubscriptionByUserEmailAndOrgLocationCode(String userEmail, String orgLocationCode) throws Exception;
	
}
