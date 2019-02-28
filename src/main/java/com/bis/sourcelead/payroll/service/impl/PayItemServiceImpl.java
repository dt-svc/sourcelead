package com.bis.sourcelead.payroll.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.AssociatePayItemDTO;
import com.bis.sourcelead.dto.AssociateSalaryDTO;
import com.bis.sourcelead.dto.OptionsDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserGroupDTO;
import com.bis.sourcelead.entity.AssociatePayItem;
import com.bis.sourcelead.entity.AssociateSalary;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.payroll.dao.PayItemDao;
import com.bis.sourcelead.payroll.dto.PayItemDTO;
import com.bis.sourcelead.payroll.entity.PayItem;
import com.bis.sourcelead.payroll.service.PayItemService;
import com.bis.sourcelead.service.AssociateSalaryService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Service
public class PayItemServiceImpl implements PayItemService{
	
	private static final Logger logger = LoggerFactory.getLogger(PayItemServiceImpl.class);
	
	@Autowired
	PayItemDao payItemDao;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private AssociateSalaryService associateSalaryService;

	@Override
	public Response<PayItemDTO> addPayItem(OrgLocations orgLocation, PayItemDTO payItemDTO, User loginUser)
	{
		Response<PayItemDTO> resObj = new Response<>();
		PayItem payItem = null;
		try {
			if (Validator.validate(payItemDTO)) {
				if(StringUtils.isNotBlank(payItemDTO.getPayItemUUID())){
					payItem = payItemDao.getPayItemByUUID(payItemDTO.getPayItemUUID());
				}else{
					payItem = new PayItem();
					payItem.setCreatedBy(loginUser.getUserCode());
					payItem.setCreatedDate(new Date());
					payItem.setPayItemUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
				}
				
				payItem.setPayItemName(payItemDTO.getPayItemName());
				if (!"".equalsIgnoreCase(payItemDTO.getPaySchedule())) {
					payItem.setPaySchedule(payItemDTO.getPaySchedule());
				}
				else {
					payItem.setPaySchedule("EVERY_PAY");
				}
				payItem.setAllowOverTime(payItemDTO.getAllowOverTime());
				if(payItemDTO.getAllowEmployeementType() != null && payItemDTO.getAllowEmployeementType().size() >0) {
					String allowEmpType = String.join(", ", payItemDTO.getAllowEmployeementType());
					payItem.setAllowEmployeementType(allowEmpType);
				}
				payItem.setDescription(payItemDTO.getDescription());
				payItem.setOrgLocationId(orgLocation);
				payItem.setStatus(1);
				payItem.setAllowOverTime(payItemDTO.getAllowOverTime());
				PayItem savedPayItem = payItemDao.addPayItem(payItem);
				if (savedPayItem != null) {
					resObj.setResponse(Result.SUCCESS.name());
					resObj.setResponseMessage(Result.SUCCESS.getValue());
					resObj.setObject(payItemDTO);
				}
			}
			else {
				resObj.setResponse("DataMissing");
				resObj.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} catch (Exception e) {
			resObj.setResponse(Result.AWKWARD.name());
			resObj.setResponseMessage(Result.AWKWARD.getValue());
			logger.error("create user group failed due to : " + e.getMessage());
		}
		return resObj;
	}

	@Override
	public ResponseList<PayItemDTO> getPayItemByLocationId(OrgLocations orgLocation,String associateEmpType)
	{
		ResponseList<PayItem> payItemList = payItemDao.getPayItemByLocationId(orgLocation);
		ResponseList<PayItemDTO> responseList = new ResponseList<>();
		try {
			if (payItemList.getData() != null) {
				List<PayItemDTO> list = new ArrayList<>();
				payItemList.getData().forEach(item -> {
					PayItemDTO payItemDTO = new PayItemDTO();
					payItemDTO.setId(item.getId());
					payItemDTO.setPayItemName(item.getPayItemName());
					payItemDTO.setPaySchedule(item.getPaySchedule());
					payItemDTO.setEmpType(item.getAllowEmployeementType());
					payItemDTO.setAllowOverTime(item.getAllowOverTime());
					payItemDTO.setDescription(item.getDescription());
					payItemDTO.setPayItemUUID(item.getPayItemUuid());
					if(StringUtils.isNotBlank(associateEmpType)){
						if(item.getAllowEmployeementType().contains(associateEmpType)) {
							list.add(payItemDTO);
						}
					}else {
						list.add(payItemDTO);
					}
					
				});
				responseList.setData(list);
				responseList.setResponse(Result.SUCCESS.name());
			}
			else {
				responseList.setResponse(Result.SESSION_TIMEDOUT.name());
				responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return responseList;
	}

	@Override
	public PayItem getPayItemByUUID(String payItemUUID)
	{
		return payItemDao.getPayItemByUUID(payItemUUID);
	}

	@Override
	public Response<PayItem> deletePayItemByUUID(PayItemDTO payItemDTO, User user)
	{
		Response<PayItem> res = new Response<>();
		try {
			PayItem payitem = payItemDao.getPayItemByUUID(payItemDTO.getPayItemUUID());
			if(payitem != null){
				payitem.setStatus(Constants.RECORD_INACTIVE);
				payitem.setUpdatedBy(user.getUserCode());
				payitem.setUpdatedDate(new Date());
				payItemDao.addPayItem(payitem);
				res.setResponse(Result.SUCCESS.name());
				res.setResponseMessage(Result.SUCCESS.getValue());
			}else{
				res.setResponse(Result.FAILED.name());
				res.setResponseMessage(Result.FAILED.getValue());
			}
		}catch (Exception e){
			res.setResponse(Result.AWKWARD.name());
			res.setResponseMessage(Result.AWKWARD.getValue());
		}
		
		return res;
	}

	@Override
	public Response<PayItem> validatePayItemName(PayItemDTO payItemDTO,OrgLocations orgLocations)
	{
		Response<PayItem> resObj = new Response<>();
		if(orgLocations != null){
			try {
				boolean flag = payItemDao.validatePayItemName(payItemDTO, orgLocations.getId());
				if (flag) {
					resObj.setResponse("Record Exists");
					resObj.setResponseMessage("Record Exists");
				}else {
					resObj.setResponse(Result.SUCCESS.name());
					resObj.setResponseMessage(Result.SUCCESS.getValue());
				}
			}catch (Exception e) {
				resObj.setResponse(Result.AWKWARD.name());
				resObj.setResponseMessage(Result.AWKWARD.getValue());
			}
		}
		return resObj;
	}

	@Override
	public ResponseList<PayItemDTO> getPayItemByLocationId(OrgLocations orgLocation)
	{
		ResponseList<PayItemDTO> response = new ResponseList<>();
		List<PayItemDTO> list = new ArrayList<>();
		ResponseList<PayItem>  res = payItemDao.getPayItemByLocationId(orgLocation);
		if(res.getData() != null) {
			PayItemDTO payItemDTO = new PayItemDTO();
			res.getData().forEach(pi->{
				payItemDTO.setId(pi.getId());
				payItemDTO.setAllowOverTime(pi.getAllowOverTime());
				payItemDTO.setDescription(pi.getDescription());
				payItemDTO.setPayItemName(pi.getPayItemName());;
				payItemDTO.setPayItemUUID(pi.getPayItemUuid());
				payItemDTO.setPaySchedule(pi.getPaySchedule());
				list.add(payItemDTO);
			});
		}
		response.setData(list);
		return response;
	}

	@Override
	public AssociatePayItem savePayItemsFromSummary(AssociateSalaryDTO associateSalaryDTO, OrgLocations orgLocation,User user) {
		
		AssociatePayItem ap = null;
		
		try {
			
			for (AssociatePayItemDTO api : associateSalaryDTO.getAssociatePayItemDto()) {
				
				if(api.getPayItemCheck() != null && api.getPayItemCheck().equalsIgnoreCase(Constants.BOOLEAN_TRUE)) {
					
					BigDecimal amount = BigDecimal.ZERO;
					AssociatePayItem associatePayItem = new AssociatePayItem();
					
					associatePayItem.setPayItemName(api.getPayItemName());
					associatePayItem.setPayItemSchedule(api.getPayItemSchedule());
					if (api.getOverRide() != null) {
						associatePayItem.setOverRide(api.getOverRide());
					}
					if (api.getEffectiveDate() != null && !"".equalsIgnoreCase(api.getEffectiveDate())) {
						associatePayItem.setEffectiveDate(commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), api.getEffectiveDate()));
					}
					if (api.getEndDate() != null && !"".equalsIgnoreCase(api.getEndDate())) {
						associatePayItem.setEndDate(commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), api.getEndDate()));
					}
					if (api.getPayItemAmount() != null && !"".equalsIgnoreCase(api.getPayItemAmount())) {
						amount = new BigDecimal(api.getPayItemAmount());
					}
					associatePayItem.setOrgPayItemId(api.getPayItemId());
					
					AssociateSalary associateSalary = associateSalaryService.getAssociateSalaryBySalaryId(Long.parseLong(associateSalaryDTO.getId()));
					associatePayItem.setAssociateSalary(associateSalary);
					associatePayItem.setPayItemAmount(amount);
					associatePayItem.setCreatedDate(new Date());
					associatePayItem.setUpdatedDate(new Date());
					associatePayItem.setCreatedBy(user.getUserCode());
					associatePayItem.setUpdatedBy(user.getUserCode());
					associatePayItem.setStatus(1);
					ap = associateSalaryService.saveUpdatedPayItem(associatePayItem);
					
				}
				
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return ap;
		
	}
}
