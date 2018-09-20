package com.bis.sourcelead.elk.index.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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

			userIndex.setUserRoleTableId(userRole.getId());
			userIndex.setUserId(userRole.getUser().getId());
			userIndex.setFirstName(user.getFirstName());
			userIndex.setLastName(user.getLastName());
			userIndex.setMiddleName(user.getMiddelName());
			userIndex.setGender(user.getGender());
			userIndex.setMaritalStatus(user.getMaritalStatus());
			userIndex.setOrgLocationId(userRole.getOrgLocations().getId());
			userIndex.setEmail(user.getEmail());
			userIndex.setMobilePhone(user.getMobilePhone());
			userIndex.setExtensionNumber(user.getExtensionNumber());
			userIndex.setMiddleName(user.getMiddelName());
			userIndex.setGender(user.getGender());
			userIndex.setMaritalStatus(user.getMaritalStatus());
			if(null != user.getIsMailSent()){
				userIndex.setIsMailSent(user.getIsMailSent().toString());	
			}
			userIndex.setUserStatus(userRole.getUserState().toString());
			userIndex.setUserType(userRole.getUserType());
			
			if(null != userRole.getReportingManager()){
			userIndex.setReportingManagerId(userRole.getReportingManager().getId());
			}
			userIndex.setStatus(userRole.getUser().getStatus());
			userIndex.setTerminateReason(userRole.getTerminateReason());
			 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				if(null != employee){
					userIndex.setEmployeeId(employee.getEmployeeId());
					userIndex.setRate(employee.getRate());
					userIndex.setRatePer(employee.getRatePer());
					 
					if(null != employee.getHireDate()){
						String hireDate  = dateFormat.format(employee.getHireDate());
						userIndex.setHireDate(hireDate);
					}
			}
				if(null != user.getDateOfBirth()){
					
					String dateofBirth = dateFormat.format(user.getDateOfBirth());
					userIndex.setDateofBirth(dateofBirth);
				}
				
				if(null != user.getDateOfBirth()){
					String dateofBirth  = dateFormat.format(user.getDateOfBirth());
					userIndex.setDateofBirth(dateofBirth);
				}
				
			userIndex.setUserCode(user.getUserCode());
			userIndex.setUserRoleId(userRole.getRoles().getId().toString());
			if(null != userRole.getCreatedDate()){
				userIndex.setCreatedDate(dateFormat.format(userRole.getCreatedDate()));
			}
			if(null != userRole.getUpdatedDate()){
				userIndex.setUpdatedDate(dateFormat.format(userRole.getUpdatedDate()));
			}
			
			
			/*if(null == userRole.getCreatedBy() || "".equalsIgnoreCase(userRole.getCreatedBy())){
				userIndex.setCreatedBy(loginUserCode);
			}*/
			userIndex.setCreatedBy(userRole.getCreatedBy());
			/*if(null != userRole.getUpdatedDate()){
				String updatedDate  = StringDate.format(new Date(userRole.getUpdatedDate().getTime()));
				userIndex.setUpdatedDate(updatedDate);
			}*/
			
			userIndex.setUpdatedBy(userRole.getUpdatedBy());
		}
		
		
		return userIndex;
		
	}
	
	
}
