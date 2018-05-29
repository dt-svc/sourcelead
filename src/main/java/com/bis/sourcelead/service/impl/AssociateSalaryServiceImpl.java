package com.bis.sourcelead.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.dao.AssociateSalaryDao;
import com.bis.sourcelead.dto.AssociatePayItemDTO;
import com.bis.sourcelead.dto.AssociateSalaryDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.entity.AssociatePayItem;
import com.bis.sourcelead.entity.AssociateSalary;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.AssociateSalaryService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class AssociateSalaryServiceImpl implements AssociateSalaryService{
	
	private static final Logger logger = LoggerFactory.getLogger(AssociateSalaryServiceImpl.class);
		
	@Autowired
	AssociateSalaryDao associateSalaryDao;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	UserService userService;

	@Override
	public Response<AssociateSalaryDTO> addAssociateSal(OrgLocations orgLocation, AssociateSalaryDTO associateSalaryDTO, User loginUser)
	{
		
		Response<AssociateSalaryDTO> resObj = new Response<>();
		try {
			if (Validator.validate(associateSalaryDTO)) {
				AssociateSalary associateSalary = new AssociateSalary();
				associateSalary.setPayType(associateSalaryDTO.getPayType());
				associateSalary.setPayFrequency(associateSalaryDTO.getPayFrequency());
				associateSalary.setStandardHours(associateSalaryDTO.getStandardHours());
				associateSalary.setAnnualSalary(new BigDecimal(associateSalaryDTO.getAnnualSal()));
				associateSalary.setGrossSalary(new BigDecimal(associateSalaryDTO.getPerPaySal()));
				associateSalary.setHourlyRate(new BigDecimal(associateSalaryDTO.getHourlyRate()));
				associateSalary.setAllowOverTimePay(associateSalaryDTO.getAllowOverTimePay());
				User user = userService.getUserById(Long.parseLong(associateSalaryDTO.getUserId()));
				associateSalary.setUserId(user);
				associateSalary.setOrgLocations(orgLocation);
				Set<AssociatePayItem> assoPayItemSet = getAssociatePayItemSet(associateSalary,associateSalaryDTO,orgLocation,loginUser);
				associateSalary.setAssociatePayItem(assoPayItemSet);
				associateSalary.setCreatedDate(new Date());
				associateSalary.setUpdatedDate(new Date());
				associateSalary.setCreatedBy(loginUser.getUserCode());
				associateSalary.setUpdatedBy(loginUser.getUserCode());
				associateSalary.setStatus(1);
				AssociateSalary saveAssociateSalary = associateSalaryDao.addAssociateSal(associateSalary);
				if (saveAssociateSalary != null) {
					resObj.setResponse(Result.SUCCESS.name());
					resObj.setResponseMessage(Result.SUCCESS.getValue());
					associateSalaryDTO.setId(String.valueOf(saveAssociateSalary.getId()));
					resObj.setObject(associateSalaryDTO);
				}
			}
			else {
				resObj.setResponse("DataMissing");
				resObj.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} catch (Exception e) {
			resObj.setResponse(Result.AWKWARD.name());
			resObj.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return resObj;
	}

	/**
	 * prepare child table (ASSOCIATE_PAY_ITEM) data from Associate Salary
	 * 
	 * @param associateSalary
	 * @param associateSalaryDTO
	 * @param orgLocation
	 * @param loginUser 
	 * @return Set<AssociatePayItem>
	 */
	private Set<AssociatePayItem> getAssociatePayItemSet(AssociateSalary associateSalary, AssociateSalaryDTO associateSalaryDTO, OrgLocations orgLocation, User loginUser)
	{
		List<AssociatePayItemDTO> list = associateSalaryDTO.getAssociatePayItemDto();
		Set<AssociatePayItem> set = new HashSet<>();
		list.forEach(assoPayItem -> {
			if (assoPayItem.getPayItemCheck() != null && !"".equalsIgnoreCase(assoPayItem.getPayItemCheck())) {
				String amount = "0";
				AssociatePayItem associatePayItem = new AssociatePayItem();
				associatePayItem.setPayItemName(assoPayItem.getPayItemName());
				associatePayItem.setPayItemSchedule(assoPayItem.getPayItemSchedule());
				if (assoPayItem.getOverRide() != null) {
					associatePayItem.setOverRide(assoPayItem.getOverRide());
				}
				if (assoPayItem.getEffectiveDate() != null && !"".equalsIgnoreCase(assoPayItem.getEffectiveDate())) {
					associatePayItem.setEffectiveDate(commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), assoPayItem.getEffectiveDate()));
				}
				if (assoPayItem.getEndDate() != null && !"".equalsIgnoreCase(assoPayItem.getEndDate())) {
					associatePayItem.setEndDate(commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), assoPayItem.getEndDate()));
				}
				if (assoPayItem.getPayItemAmount() != null && !"".equalsIgnoreCase(assoPayItem.getPayItemAmount())) {
					amount = assoPayItem.getPayItemAmount();
				}
				associatePayItem.setPayItemAmount(new BigDecimal(amount));
				associatePayItem.setCreatedDate(new Date());
				associatePayItem.setUpdatedDate(new Date());
				associatePayItem.setCreatedBy(loginUser.getUserCode());
				associatePayItem.setUpdatedBy(loginUser.getUserCode());
				associatePayItem.setStatus(1);
				set.add(associatePayItem);
			}
		});
		return set;
	}
}
