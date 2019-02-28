package com.bis.sourcelead.payroll.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.payroll.dao.ConfigurePayrollApproverDao;
import com.bis.sourcelead.payroll.dao.impl.ApproverDTO;
import com.bis.sourcelead.payroll.dto.PayrollApproverDTO;
import com.bis.sourcelead.payroll.dto.PayrollConstant;
import com.bis.sourcelead.payroll.entity.ConfigurePayrollApprover;
import com.bis.sourcelead.payroll.entity.PayrollApprover;
import com.bis.sourcelead.payroll.service.ConfigurePayrollApproverService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;

@Service
public class ConfigurePayrollApproverServiceImpl implements ConfigurePayrollApproverService{

	private static final Logger logger = LoggerFactory.getLogger(ConfigurePayrollApproverServiceImpl.class);
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ConfigurePayrollApproverDao configurePayrollApproverDao;
	
	@Override
	public ApproverDTO getPayrollApprover(OrgLocations orgLocation, User loginUser)
	{
		
		List<UserDTO> list = new ArrayList<>();
		ApproverDTO approverDTO = new ApproverDTO();
		HashMap<Long,User> userMap = new HashMap<>();
		List<User> approveruserList = new CopyOnWriteArrayList<User>();
		List<User> tabAccessuserList = new CopyOnWriteArrayList<User>();
		List<User> usersList = new CopyOnWriteArrayList<User>();
		try {
			ConfigurePayrollApprover  configurePayrollApprover = getConfigurePayrollApproverByLoc(orgLocation);
			if(configurePayrollApprover != null) {
				List<Long> approverList = configurePayrollApprover.getPayrollApprover().stream().map(ap->ap.getUser().getId()).collect(Collectors.toList());
				for(Long id : approverList) {
					User user = userService.getUserByIdAndOrgLocationsId(id, orgLocation.getId());
					user.setIsPayRollApprover(Constants.YES);
					approveruserList.add(user);
				}
				usersList.addAll(approveruserList);
				approverDTO.setPayrollType(configurePayrollApprover.getPayrollType());
				approverDTO.setAutomaticPayroll(configurePayrollApprover.getAutomaticPayroll());
				approverDTO.setConfAprrover(String.valueOf(configurePayrollApprover.getId()));
				approverDTO.setAutomaticFrequency(configurePayrollApprover.getAutoPayrollFrequency());
			}
			
			tabAccessuserList = userService.getTabAccessOwnersByOrgLocationIdAndTabId(orgLocation.getId(),Long.valueOf(Constants.TAB_RUN_PAYROLL));
			usersList.addAll(tabAccessuserList);
			
				for(User user : usersList) {
				User tempUser = userMap.get(user.getId());
				if(tempUser == null) {
					userMap.put(user.getId(),user);
					UserDTO userDto = new UserDTO();
					userDto.setFullName(user.getFullName());
					userDto.setId(user.getId());
					if(StringUtils.isNoneBlank(user.getIsPayRollApprover()) && user.getIsPayRollApprover().equalsIgnoreCase(Constants.YES)) {
						userDto.setIsPayRollApprover(Constants.YES);
					}else {
						userDto.setIsPayRollApprover(Constants.NO);
					}
					UserRole userRole = null;
					try {
						userRole = userRoleService.getUserDetailsByUserIdAndLocationId(user.getId(), orgLocation.getId());
					} catch (Exception e) {
						e.printStackTrace();
					}
					userDto.setRole(userRole.getRoles().getDisplayName());
					list.add(userDto);
				}else {
					usersList.remove(user);
					continue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		approverDTO.setUser(list);
		return approverDTO;
	}
	
	@Override
	public Response<PayrollApproverDTO> savePayrollApprover(PayrollApproverDTO payrollApproverDTO, OrgLocations orgLocation, User loginUser)
	{
		Response<PayrollApproverDTO> res = new Response<>();
		try {
			ConfigurePayrollApprover confPayApprover = null;
			if(StringUtils.isNoneBlank(payrollApproverDTO.getConfAprrover())){
				confPayApprover = configurePayrollApproverDao.getConfigurePayrollApproverByLoc(orgLocation);
				int isDeleted = configurePayrollApproverDao.deletePayrollApprover(payrollApproverDTO.getConfAprrover());
			}else {
				confPayApprover = new ConfigurePayrollApprover();
				confPayApprover.setCreatedDate(new Date());
			}
			confPayApprover.setAutomaticPayroll(PayrollConstant.NO);
			if(StringUtils.isNotBlank(payrollApproverDTO.getAutomaticPayroll())) {
				confPayApprover.setAutomaticPayroll(PayrollConstant.YES);
			}
			confPayApprover.setAutoPayrollFrequency(payrollApproverDTO.getPayrollFrequency());
			confPayApprover.setPayrollType(payrollApproverDTO.getPayrollType());
			confPayApprover.setCreatedBy(loginUser.getId());
			confPayApprover.setUpdatedBy(loginUser.getId());
			confPayApprover.setUpdatedDate(new Date());
			confPayApprover.setStatus(1);
			confPayApprover.setOrgLocation(orgLocation);
			Set<PayrollApprover> payrollAppr = getPayrollApprover(confPayApprover,payrollApproverDTO.getApprover(),loginUser);
			confPayApprover.setPayrollApprover(payrollAppr);
			ConfigurePayrollApprover confp = configurePayrollApproverDao.savePayrollApprover(confPayApprover);
			if (confp != null) {
				res.setResponse(Result.SUCCESS.name());
				res.setResponseMessage(Result.SUCCESS.getValue());
				res.setObject(payrollApproverDTO);
			}
		}catch (Exception e) {
			res.setResponse(Result.AWKWARD.name());
			res.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return res;
	}
	/**
	 * populate Payroll Approvers 
	 * @param confPayApprover
	 * @param approver
	 * @param loginUser
	 * @return Set<PayrollApprover>
	 */
	private Set<PayrollApprover> getPayrollApprover(ConfigurePayrollApprover confPayApprover, List<String> approver, User loginUser)
	{
		Set<PayrollApprover> set = new HashSet<>();
		try {
			for (String usr : approver) {
				PayrollApprover appr = new PayrollApprover();
				User user = userService.getUserById(Long.parseLong(usr));
				appr.setConfigurePayrollApprover(confPayApprover);
				appr.setStatus(1);
				appr.setUpdatedBy(loginUser.getId());
				appr.setUser(user);
				appr.setCreatedDate(new Date());
				appr.setUpdatedDate(new Date());
				appr.setCreatedBy(loginUser.getId());
				set.add(appr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return set;
	}

	@Override
	public ConfigurePayrollApprover getConfigurePayrollApproverByLoc(OrgLocations orgLocation)
	{
		ConfigurePayrollApprover configurePayrollApprover = null;
		try {
			 configurePayrollApprover = configurePayrollApproverDao.getConfigurePayrollApproverByLoc(orgLocation);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return configurePayrollApprover;
	}

	@Override
	public ResponseList<PayrollApproverDTO> getConfigurePayrollApprover(OrgLocations orgLocation)
	{
		ResponseList<PayrollApproverDTO> res = new ResponseList<>();
		try {
			ConfigurePayrollApprover configurePayrollApprover = configurePayrollApproverDao.getConfigurePayrollApproverByLoc(orgLocation);
			Set<PayrollApprover> approvers = configurePayrollApprover.getPayrollApprover();
			List<PayrollApproverDTO> list = new ArrayList<>();
			for (PayrollApprover approver : approvers) {
				PayrollApproverDTO dto = new PayrollApproverDTO();
				StringBuilder sb = new StringBuilder();
				dto.setUserId(String.valueOf(approver.getUser().getId()));
				if (approver.getUser().getLastName() != null) {
					sb.append(approver.getUser().getFirstName()).append(" ").append(approver.getUser().getLastName());
				}
				else {
					sb.append(approver.getUser().getFirstName());
				}
				dto.setApproverName(sb.toString());
				UserRole userRole = userRoleService.getUserByUserId(approver.getUser().getId());
				dto.setRoleName(userRole.getRoles().getDisplayName());
				list.add(dto);
			}
			res.setData(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public List<PayrollApprover> getPayrollApproversByCPAID(Long configurePayrollApproverId) {
		return configurePayrollApproverDao.getPayrollApproversByCPAID(configurePayrollApproverId);
	}

	@Override
	public boolean isLoginUserIsPayRollApprover(Long userId, Long orgLocationsId) {
		return configurePayrollApproverDao.isLoginUserIsPayRollApprover(userId, orgLocationsId);
	}

	@Override
	public ConfigurePayrollApprover getConfigurePayrollApproverByLocAndStatus(OrgLocations orgLocation)
	{
		return configurePayrollApproverDao.getConfigurePayrollApproverByLocAndStatus(orgLocation);
	}
}
