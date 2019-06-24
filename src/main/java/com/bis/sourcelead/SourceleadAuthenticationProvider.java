package com.bis.sourcelead;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.CandidateOffBoarding;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.CandidateOffBoardingService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.service.WhiteListIpAddressService;
import com.bis.sourcelead.util.UserLoginHelper;

/**
 * Custom AuthenticationProvider
 * 
 * @author: Naresh Bolisetty
 * @date: 26-May-2016
 */
@Component
public class SourceleadAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	private UserService userService;
	@Autowired
	private SourceleadPasswordEncoder sourceleadPasswordEncoder;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	EmployeesService employeesService;
	@Autowired
	OrgLocationService orgLocationService;
	@Autowired
	UserLoginHelper userLoginHelper;
	@Autowired
	WhiteListIpAddressService whiteListIpAddressService;
	@Autowired
	CandidateOffBoardingService candidateOffBoardingService;
	
	/*
	 * @param
	 * 
	 * Overriding AuthenticationProvider authenticate(-) method to handle
	 * NullPointer Exception for unregistered users
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException
	{
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();
		User user = userService.findByUserName(username);
		UserRole userRole = null;
		List<UserRole> userRolesList = null;
		CandidateOffBoarding candidateOffBoarding = null;
		try {
			if (user != null) {
				userRole = userRoleService.getLoginUserPrimaryRole(user.getId());
				candidateOffBoarding = candidateOffBoardingService.getCandidateOffBoardingByUserIdAndLocationId(userRole.getUser().getId(), userRole.getOrgLocations().getId());
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (user == null) {
			throw new BadCredentialsException("login_unRegistered_error");
		}
		else if ((userRole == null) || (userRole != null && userRole.getUserState() != null
				&& userRole.getUserState().equals(Status.INACTIVE))) {
			throw new BadCredentialsException("login_inActive_error");
		}
		else if (Constants.NO.equalsIgnoreCase(user.getIsVerified())) {
			throw new BadCredentialsException("email_notVerified_error");
		}
		else {
			if (sourceleadPasswordEncoder.matches(password, user.getPassword())) {
				try {
					userRolesList = userRoleService.getUserSpocsListByUserId(userRole.getUser().getId());
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (authentication.getDetails() != null) {
					if (userRolesList.size() > 1)
						throw new BadCredentialsException("login_2_Step_Process_Enabled");
					else if ((userRolesList.size() == 1
							&& (Constants.YES.equalsIgnoreCase(userRolesList.get(0).getOrgLocations().getLoginSecurity()))
							|| Constants.YES.equalsIgnoreCase(userRolesList.get(0).getUser().getLoginSecurity())))
						throw new BadCredentialsException("redirect_to_login_2_Step_Process");
					else if ((userRolesList.size() == 1 && candidateOffBoarding != null
							&& candidateOffBoarding.getOffboardSubmitted().equalsIgnoreCase("Y")))
						throw new BadCredentialsException("login_offboardUser_error");
					else {
						throw new BadCredentialsException("redirect_to_dashboard");
					}
				}
				else {
					throw new BadCredentialsException("redirect_to_dashboard");
				}
			}
			else {
				throw new BadCredentialsException("login_password_error");
			}
		}
	}
	
	public boolean supports(Class<?> arg0)
	{
		return true;
	}
}