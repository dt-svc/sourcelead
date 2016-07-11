package com.bis.sourcelead.service.impl;

import java.util.Collection;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.form.UserProfile;
import com.bis.sourcelead.service.UserService;

/**
 * This Service Class is used to get User Object
 * 
 * @author: Naresh Bolisetty
 * @date: 27-May-2016
 */

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;

	@Override
	public User findByUserName(String username) {
		logger.debug("In UserService class...");
		return userDao.findByUserName(username);
	}

	@Override
	public User addUser(User user) throws Exception {
		logger.debug("In UserService class...");
		return userDao.addUser(user);
	}

	@Override
	public String validateEmailandCandidate(String website, String email, String sourceleadBLockedwebsites)
			throws Exception {
		logger.debug("In UserService class...");
		return userDao.validateEmailandCandidate(website, email, sourceleadBLockedwebsites);
	}

	@Override
	public Collection<User> getUsers() throws Exception {
		logger.debug("In UserService class...");
		return userDao.getUsers();
	}

	@Override
	public User findByEmail(String email) throws Exception {
		logger.debug("In UserService class...");
		return userDao.findByEmail(email);
	}

	@Override
	public User getUserById(Long Id) throws Exception {
		logger.debug("In UserService class...");
		return userDao.getUserById(Id);
	}

	@Override
	public User saveUserProfile(UserProfile userProfile, MultipartHttpServletRequest request,User user) throws Exception {
		
		user = getUserProfileDetails(userProfile,request,user);
		userDao.addUser(user);
		return user;
	}
	
	
	private User getUserProfileDetails(UserProfile userProfile, MultipartHttpServletRequest request,User user){
		user.setFirstName(userProfile.getFirstName());
		user.setLastName(userProfile.getLastName());
		user.setMobilePhone(userProfile.getMobilePhone());
		user.setJobTitle(userProfile.getJobTitle());
		user.setLocation(userProfile.getLocation());
		Iterator<String> itrator = request.getFileNames();
        MultipartFile multiFile = request.getFile(itrator.next());
        if(null != multiFile.getContentType()){
    	  try {
    	   		user.setFileContentType(multiFile.getContentType());
    	   		user.setDocFile(multiFile.getBytes());
    	   
	       }catch (Exception e) {
    	      	 e.printStackTrace();
			    }
        }
		
		return user;
	}

}
