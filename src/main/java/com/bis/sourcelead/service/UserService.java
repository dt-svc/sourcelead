package com.bis.sourcelead.service;


import com.bis.sourcelead.entity.User;
public interface UserService  {

	User findByUserName(String username);
	
	public User addUser(User user) throws Exception;

}	
		
	
