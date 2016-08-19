package com.bis.sourcelead.service;


import java.util.Collection;
import java.util.List;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
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
	
	public User saveUserProfile(UserProfile userProfile,MultipartFile multiFile,User user) throws Exception;
	
	public Collection<User> getUsersByUserRoles(Long orgLocationsId) throws Exception;
	
	public List<User> getUsersByUserRolesAndOrgLocationIdAndNoCurrentUser(Long userId, Long orgLocationsId) throws Exception;
	
	public List<User> getUsersByUserRolesAndOrgLocationId(Long orgLocationsId) throws Exception;
	
	List<User> getUsersRoleCountByRoleIdAndLocationIdAndNotLoggedInUser(Long roleId,Long orgLocationsId,Long userId) throws Exception;
	
	public List<Employees> getAllUserOrgCodeAndLocationCodeTimeSheetNotification(Long userId) throws Exception;
	
	void storeUser(JSONObject jsonObj,User users,OrgLocations orgLocations)  throws Exception;

	
}	
		
	
