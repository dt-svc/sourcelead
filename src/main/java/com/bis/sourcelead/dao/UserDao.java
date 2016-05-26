package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.User;

public interface UserDao {

	User findByUserName(String username) throws Exception;

}