package com.bis.sourcelead.service;

import org.json.JSONException;

import com.bis.sourcelead.entity.UserMailUnsubscribe;

/**
 * @author Venkat Kesav
 *
 */
public interface UserMailUnsubscribeService {
 
	/**
	 * @param user
	 * @param tabId
	 * @param orgLocation
	 * @param contentType
	 * @return
	 * @throws JSONException
	 * @throws Exception
	 */
	public UserMailUnsubscribe saveOrUpdateUserMailUnsubscription(UserMailUnsubscribe userMailUnsubscribe) throws Exception;
	
	public UserMailUnsubscribe getUserMailUnsubscriptionByUserIdAndOrgLocationId(Long userId, Long orgLocationId) throws Exception;
	
	public UserMailUnsubscribe getUserMailUnsubscriptionByUserEmailAndOrgLocationId(String userEmail, Long orgLocationId) throws Exception;
	
}
