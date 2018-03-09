package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.UserMailUnsubscribe;

/**
 * @author Venkat Kesav
 *
 */
public interface UserMailUnsubscribeDao {
	
	public UserMailUnsubscribe saveOrUpdateUserMailUnsubscription(UserMailUnsubscribe userMailUnsubscribe) throws Exception;
	
	public UserMailUnsubscribe getUserMailUnsubscriptionByUserIdAndOrgLocationId(Long userId, Long orgLocationId) throws Exception;
	
	public UserMailUnsubscribe getUserMailUnsubscriptionByUserEmailAndOrgLocationId(String userEmail, Long orgLocationId) throws Exception;
	
}
