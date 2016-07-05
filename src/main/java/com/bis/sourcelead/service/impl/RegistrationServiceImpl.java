package com.bis.sourcelead.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.form.Registration;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.RegistrationService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class RegistrationServiceImpl implements RegistrationService{

	
	@Autowired
	private OrgService orgService;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private CountriesService countriesService;
	
	@Autowired
	private EmployeesService employeesService;
	
	@Autowired
	private SourceleadPasswordEncoder sourceleadPasswordEncoder;
	
	@Autowired
	private RolesService rolesService;
	
	@Override
	public void addRegistration(Registration registration) throws Exception {
		
		Org org = getOrgDetails(registration);
		try {
			orgService.addOrganization(org);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		User user = getUserDetails(registration);
		try {
			userService.addUser(user);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		org.setUser(user);
		org.setCreatedBy(user.getUserCode());
		
		try {
			orgService.updateOrg(org);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		OrgLocations orgLocations = getOrgLocationsDetails(registration, org, user);
		try {
			orgLocationService.addOrgLocations(orgLocations);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		UserRole userRole = getUserRoleDetails(user,orgLocations);
		try {
			userRoleService.addUserRole(userRole);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Employees employee = getEmployeeDetails(user);
		try {
			employeesService.addEmployee(employee);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	private Org getOrgDetails(Registration registration){
		Org org = new Org();
		org.setOrgName(registration.getCompanyName());
		org.setWebSite(registration.getWebsite());
		org.setOrgType("Company");
		org.setOrgCode(SourceLeadRandomCodeHelper.generateRandomOrgCode());
		org.setStatus(Status.ACTIVE);
		org.setCreatedDate(new Date());
		return org;
	}
	
	private User getUserDetails(Registration registration){
		User user = new User();
		user.setFirstName(registration.getFirstName());
		user.setLastName(registration.getLastName());
		user.setEmail(registration.getEmail());
		user.setUserName(registration.getEmail());
		user.setUserState(Status.ACTIVE);
		user.setPassword(sourceleadPasswordEncoder.encode(registration.getPassword()));
		user.setUserCode(SourceLeadRandomCodeHelper.generateRandomUserCode());
		user.setCreatedBy(user.getUserCode());
		user.setCreatedDate(new Date());
		return user;
	}
	
	private OrgLocations getOrgLocationsDetails(Registration registration,Org org,User user ){
		
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setCountry(registration.getCountry());
		orgLocations.setOrg(org);
		orgLocations.setUser(user);
		orgLocations.setStatus(Status.ACTIVE);
		Countries countries = countriesService.getCountriesByCountryName(registration.getCountry());
		orgLocations.setLocationCode(org.getOrgCode()+countries.getCode2());
		orgLocations.setCreatedBy(user.getCreatedBy());
		orgLocations.setCreatedDate(new Date());
		return orgLocations;
	}
	
	private UserRole getUserRoleDetails(User user,OrgLocations orgLocations ){
		
		UserRole userRole = new UserRole();
		userRole.setUser(user);
		userRole.setOrgLocations(orgLocations);
		Roles roles = null;
		try {
			roles = rolesService.getRolesByRoleName("ROLE_ADMIN");
		} catch (Exception e) {
			e.printStackTrace();
		}
		userRole.setRoles(roles);
		userRole.setIsPrimary(1);
		userRole.setCreatedDate(new Date());
		userRole.setCreatedBy(user.getCreatedBy());
		return userRole;
		
	}
	
	private Employees getEmployeeDetails(User user){
		
		Employees employees = new Employees();
		employees.setUser(user);
		employees.setCreatedBy(user.getCreatedBy());
		employees.setCreatedDate(new Date());
		return employees;
	}

}
