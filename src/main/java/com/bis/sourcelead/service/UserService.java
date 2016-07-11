package com.bis.sourcelead.service;


import java.util.Collection;

import com.bis.sourcelead.entity.User;
public interface UserService  {

	User findByUserName(String username);
	
	public User addUser(User user) throws Exception;
	
	String validateEmailandCandidate(String website, String email,String sourceleadBLockedwebsites) throws Exception;
	User getUserById(Long Id) throws Exception;

	public Collection<User> getUsers() throws Exception;
	
	public User findByEmail(String email)  throws Exception;
	
}	
		
	
