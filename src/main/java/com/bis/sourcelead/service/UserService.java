package com.bis.sourcelead.service;


import java.util.Collection;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.form.Registration;
import com.bis.sourcelead.form.UserProfile;
public interface UserService  {

	User findByUserName(String username);
	
	public User addUser(User user) throws Exception;
	
	public void createUser(JSONObject jsonObj, User user, Long orgLocationsId) throws Exception;
	
	public void sendEmail(User user, User loginUser, String password) throws Exception;
	
	String validateEmailandCandidate(String website, String email,String sourceleadBLockedwebsites) throws Exception;
	
	User getUserById(Long Id) throws Exception;

	public Collection<User> getUsers() throws Exception;
	
	public User findByEmail(String email)  throws Exception;
	
	public User saveUserProfile(UserProfile userProfile,MultipartHttpServletRequest request,User user) throws Exception;
	
	public Collection<User> getUsersByUserRoles() throws Exception;
	
}	
		
	
