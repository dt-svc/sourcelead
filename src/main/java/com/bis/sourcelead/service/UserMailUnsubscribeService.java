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
	
	public UserMailUnsubscribe getUserMailUnsubscriptionByUserId(Long userId) throws Exception;
	
	public UserMailUnsubscribe getUserMailUnsubscriptionByUserEmail(String userEmail) throws Exception;
	
	public UserMailUnsubscribe getUserMailUnsubscriptionByUserEmailAndOrgLocationCode(String userEmail, String locationCode) throws Exception;
	
}
