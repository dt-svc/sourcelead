package com.bis.sourcelead.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.dao.PayrollPayheadDao;
import com.bis.sourcelead.dto.PayrollPayheadDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PayrollPayhead;

/**
 * This is the commonutil for the certain common methods used throughout the application
 * @author: Naresh Bolisetty
 * 
 */
/**
 * @author Venkat Kesav
 *
 */
@Component
public class INDPayrollCommonUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(INDPayrollCommonUtil.class);
	
	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Value("${paymentSuccessReturnEmail}")
	private String paymentSuccessReturnEmail;
	
	@Value("${static.path}")
    private String staticPath;
	
	@Autowired
	PayrollPayheadDao payrollPayheadDao;
	
	@Autowired
	CommonUtil commonUtil;
	
	public List<PayrollPayheadDTO> setPayrollPayheadList(List<PayrollPayhead> payrollPayheadList,OrgLocations orgLocations) throws Exception {
		List<PayrollPayheadDTO> payrollPayheadDTOList = new ArrayList<>();
		if(payrollPayheadList != null && !payrollPayheadList.isEmpty()) {
			for(PayrollPayhead payRollPayhead : payrollPayheadList) {
				PayrollPayheadDTO payheadDTO = new PayrollPayheadDTO();
				payheadDTO.setEffectiveDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),payRollPayhead.getEffectiveDate()));
				payheadDTO.setId(payRollPayhead.getId());
				payheadDTO.setName(payRollPayhead.getName());
				payheadDTO.setShortCode(payRollPayhead.getShortCode());
				payheadDTO.setType(payRollPayhead.getType());
				payheadDTO.setComponent(payRollPayhead.getComponent());
				payheadDTO.setTax(payRollPayhead.getTax());
				payheadDTO.setUuid(payRollPayhead.getUuid());
				payheadDTO.setMethod(payRollPayhead.getMethod());
				payheadDTO.setFormula(payRollPayhead.getFormula());
				payrollPayheadDTOList.add(payheadDTO);
			}
		}
		return payrollPayheadDTOList;
	}
	
}

