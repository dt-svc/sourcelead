package com.bis.sourcelead.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.BulkClientDetails;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Payroll;
import com.bis.sourcelead.service.PayrollService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

/**
 * 
 * Handles requests for the Requirement service.
 * 
 * @author sarath kumar punnana
 * @date 14th Dec 2016
 *
 */

@RestController
@RequestMapping("/path")
public class PayrollRestController {

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	PayrollService payrollService;
	
	@Autowired
	CommonUtil commonUtil;
	
	
	
	/**
	 * @author sarath kumar punnana and Ajith Matta
	 * 
	 * @description this method is for get payment details by id
	 * 
	 * @return JSON  : this method returns json objects
	 *  
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/getPayrollById", method = RequestMethod.POST)
	public @ResponseBody String getPayrollById(Long payrollId) throws Exception {
		String resultJson;

			Payroll payroll = payrollService.getPayrollById(payrollId);
			resultJson = commonUtil.toJSON(payroll);
		    return resultJson;
		    
	}
	
	/**
	 * @author sarath kumar punnana and Ajith Matta
	 * 
	 * @description this method returns payments list
	 * 
	 * @return JSON  : this method returns json objects.
	 *  
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAllPayrolls", method = RequestMethod.POST)
	public @ResponseBody String getAllPayrolls() throws Exception {
			String resultJson;
			List<Payroll> payrolls  = payrollService.getAllPayroll();
			resultJson = commonUtil.toJSON(payrolls);
			 return resultJson;
	    
	}
		


	 
}
