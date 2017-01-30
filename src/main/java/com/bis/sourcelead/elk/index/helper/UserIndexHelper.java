package com.bis.sourcelead.elk.index.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.elk.index.UserIndex;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;

@Component
public class UserIndexHelper {
	
	@Autowired
	private UserDao userDao;
	
	
	
	public UserIndex getUserIndexFromUser(UserRole userRole, Employees employee, String loginUserCode) throws Exception{
		UserIndex userIndex = null;
		User user = userDao.getUserByIdAndOrgLocationsId(userRole.getUser().getId(), userRole.getOrgLocations().getId());
		
		if(null != userRole && null != userRole.getId()){
			userIndex = new UserIndex();
			userIndex.setUserId(userRole.getUser().getId());
			userIndex.setFirstName(user.getFirstName());
			userIndex.setLastName(user.getLastName());
			userIndex.setOrgLocationId(userRole.getOrgLocations().getId());
			userIndex.setEmail(user.getEmail());
			userIndex.setMobilePhone(user.getMobilePhone());
			userIndex.setIsMailSent(user.getIsMailSent().toString());
			userIndex.setUserStatus(userRole.getUserState().toString());
			userIndex.setUserType(userRole.getUserType());
			userIndex.setStatus(userRole.getUser().getStatus());
			userIndex.setTerminateReason(userRole.getTerminateReason());
			userIndex.setEmployeeId(employee.getEmployeeId());
			userIndex.setRate(employee.getRate());
			userIndex.setRatePer(employee.getRatePer());
			DateFormat StringDate = new SimpleDateFormat("dd-MM-yyyy"); 
			if(null != employee.getHireDate() && !"".equalsIgnoreCase(employee.getHireDate().toString())){
			String hireDate  = StringDate.format(employee.getHireDate());
			userIndex.setHireDate(hireDate);
			}
			userIndex.setUserCode(user.getUserCode());
			userIndex.setUserRoleId(userRole.getRoles().getId().toString());
			if(null != userRole.getCreatedDate()){
				String createdDate  = StringDate.format(new Date(userRole.getCreatedDate().getTime()));
				userIndex.setCreatedDate(createdDate);
			}
			/*if(null == userRole.getCreatedBy() || "".equalsIgnoreCase(userRole.getCreatedBy())){
				userIndex.setCreatedBy(loginUserCode);
			}*/
			userIndex.setCreatedBy(userRole.getCreatedBy());
			if(null != userRole.getUpdatedDate()){
				String updatedDate  = StringDate.format(new Date(userRole.getUpdatedDate().getTime()));

				userIndex.setUpdatedDate(updatedDate);
			}
			
			userIndex.setUpdatedBy(loginUserCode);
		}
		
		
		return userIndex;
		
	}
	
	
}
