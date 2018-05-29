package com.bis.sourcelead.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.UserMailUnsubscribeDao;
import com.bis.sourcelead.dto.UserMailUnsubscribeDTO;
import com.bis.sourcelead.entity.UserMailUnsubscribe;
import com.bis.sourcelead.service.UserMailUnsubscribeService;
import com.bis.sourcelead.util.CommonUtil;

/**
 * @author Venkat Kesav
 *
 */
@Service
public class UserMailUnsubscribeServiceImpl implements UserMailUnsubscribeService {
	private static final Logger logger = LoggerFactory.getLogger(UserMailUnsubscribeServiceImpl.class);
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private UserMailUnsubscribeDao userMailUnsubscribeDao;
		
	/**
	 * @author Venkat Kesav
	 * @since March 07 2018
	 * @version 0.0.1
	 * 
	 *          This method is used to store/save/update the UserMailUnsubscribe in
	 *          User_Mail_Unsubscribe table.
	 * 
	 * @return UserMailUnsubscribe
	 * 
	 * @throws NullPointer
	 */
	@Override
	public UserMailUnsubscribe saveOrUpdateUserMailUnsubscription(UserMailUnsubscribe userMailUnsubscribe) throws Exception
	{
		return userMailUnsubscribeDao.saveOrUpdateUserMailUnsubscription(userMailUnsubscribe);
	}

	@Override
	public UserMailUnsubscribe getUserMailUnsubscriptionByUserId(Long userId) throws Exception
	{
		return userMailUnsubscribeDao.getUserMailUnsubscriptionByUserId(userId);
	}
	
	@Override
	public UserMailUnsubscribe getUserMailUnsubscriptionByUserEmail(String userEmail) throws Exception
	{
		return userMailUnsubscribeDao.getUserMailUnsubscriptionByUserEmail(userEmail);
	}

	@Override
	public UserMailUnsubscribe getUserMailUnsubscriptionByUserEmailAndOrgLocationCode(String userEmail, String locationCode) throws Exception
	{
		return userMailUnsubscribeDao.getUserMailUnsubscriptionByUserEmailAndOrgLocationCode(userEmail, locationCode);
	}
	
}