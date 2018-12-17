package com.bis.sourcelead.web;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.CentralTaxDTO;
import com.bis.sourcelead.dto.PayrollPayheadDTO;
import com.bis.sourcelead.dto.PayrollPaytypeDTO;
import com.bis.sourcelead.dto.PayrollTaxDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.StateTaxDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PayrollBonus;
import com.bis.sourcelead.entity.PayrollLoanAndAdvances;
import com.bis.sourcelead.entity.PayrollPaySlipInd;
import com.bis.sourcelead.entity.PayrollPayhead;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.PayrollBonusService;
import com.bis.sourcelead.service.PayrollLoanAndAdvancesService;
import com.bis.sourcelead.service.PayrollPaySlipIndService;
import com.bis.sourcelead.service.PayrollPayheadService;
import com.bis.sourcelead.service.PayrollPaytypeService;
import com.bis.sourcelead.service.PayrollTaxService;
import com.bis.sourcelead.service.TaxService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@PropertySource("${propertyLocation:app.properties}")
@RequestMapping("/app")
public class PayrollConfigurationController {
	
	private static final Logger logger = LoggerFactory.getLogger(PayrollConfigurationController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	PayrollBonusService payrollBonusService;
	
	@Autowired
	PayrollLoanAndAdvancesService payrollLoanAndAdvancesService;
	
	@Autowired
	PayrollPaytypeService payrollPaytypeService;
	
	@Autowired
	PayrollPayheadService payrollPayheadService;
	
	@Autowired
	private PayrollTaxService payrollTaxService;
	
	@Autowired
	private PayrollPaySlipIndService payrollPaySlipIndService;
	
	
	@RequestMapping(value = "/indPayrollConfiguration")
	public ModelAndView payrollConfiguration(HttpServletRequest request)
	{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ModelAndView model = new ModelAndView();
		commonUtil.highLightMenu(Constants.OrgSettings, request);
		if(orgLocations.getCountry().equalsIgnoreCase("India")) {
			model.setViewName("indPayroll/payrollConfiguration");
		}else if(orgLocations.getCountry().equalsIgnoreCase("United States")) {
			model.setViewName("payroll/payrollConfiguration");
		}
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	
	@RequestMapping(value = "/payrollBonus")
	public ModelAndView payrollBonus(@RequestParam(value="uuid", required=false) String uuid,HttpServletRequest request)
	{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ModelAndView model = new ModelAndView();
		commonUtil.highLightMenu(Constants.OrgSettings, request);
		if(StringUtils.isNotBlank(uuid)) {
			try {
				PayrollBonus payrollBonus = payrollBonusService.getPayrollBonusByUUID(uuid);
				payrollBonus.setPaymentDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),payrollBonus.getPaymentDate()));
				model.addObject("payrollBonus", payrollBonus);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		model.addObject("uuid", uuid);
		model.setViewName("indPayroll/payrollBonus");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	

	@RequestMapping(value = "/payrollBonusList")
	public ModelAndView payrollBonusList(HttpServletRequest request)
	{
		ModelAndView model = new ModelAndView();
		commonUtil.highLightMenu(Constants.OrgSettings, request);
		model.setViewName("indPayroll/payrollBonusList");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	@RequestMapping(value = "/payrollPayhead")
	public ModelAndView payrollPayhead(@RequestParam(value="uuid", required=false) String uuid, HttpServletRequest request)
	{
		ModelAndView model = new ModelAndView();
		commonUtil.highLightMenu(Constants.OrgSettings, request);
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		if(StringUtils.isNotBlank(uuid)) {
			try {
				PayrollPayhead payrollPayhead = payrollPayheadService.getPayrollPayheadByUUID(uuid);
				payrollPayhead.setEffectiveDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),payrollPayhead.getEffectiveDate()));
				model.addObject("payrollPayhead", payrollPayhead);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		ResponseList<PayrollPaytypeDTO> responsePaytypeList;
		try {
			responsePaytypeList = payrollPaytypeService.getPayrollPaytypeList(orgLocations);
			model.addObject("payTypeList",responsePaytypeList.getData());
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addObject("uuid", uuid);
		model.setViewName("indPayroll/addPayhead");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	
	@RequestMapping(value = "/payrollPayheadSummary")
	public ModelAndView payrollPayheadSummary(@RequestParam(value="uuid", required=false) String uuid, HttpServletRequest request)
	{
		ModelAndView model = new ModelAndView();
		commonUtil.highLightMenu(Constants.OrgSettings, request);
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		if(StringUtils.isNotBlank(uuid)) {
			try {
				PayrollPayhead payrollPayhead = payrollPayheadService.getPayrollPayheadByUUID(uuid);
				payrollPayhead.setEffectiveDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),payrollPayhead.getEffectiveDate()));
				model.addObject("payrollPayhead", payrollPayhead);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		model.addObject("uuid", uuid);
		model.setViewName("indPayroll/viewPayhead");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	

	@RequestMapping(value = "/payheadList")
	public ModelAndView payheadList(HttpServletRequest request)
	{
		ModelAndView model = new ModelAndView();
		commonUtil.highLightMenu(Constants.OrgSettings, request);
		model.setViewName("indPayroll/payheadList");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	@RequestMapping(value = "/payrollSalarySetup")
	public ModelAndView salarySetupList(HttpServletRequest request)
	{
		ModelAndView model = new ModelAndView();
		commonUtil.highLightMenu(Constants.OrgSettings, request);
		model.setViewName("indPayroll/salarySetupList");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	
	@RequestMapping(value = "/payrollLoanAndAdvances")
	public ModelAndView payrollLoanAndAdvances(@RequestParam(value="uuid", required=false) String uuid,HttpServletRequest request)
	{
		ModelAndView model = new ModelAndView();
		commonUtil.highLightMenu(Constants.OrgSettings, request);
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		if(StringUtils.isNotBlank(uuid)) {
			try {
				PayrollLoanAndAdvances loanAndAdv = payrollLoanAndAdvancesService.getPayrollLoanAndAdvancesByUUID(uuid);
				model.addObject("loanAndAdv", loanAndAdv);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ResponseList<PayrollPayheadDTO> responsePayheadList;
		try {
			responsePayheadList = payrollPayheadService.getPayrollPayheadList(orgLocations);
			model.addObject("payheadList",responsePayheadList.getData());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		model.setViewName("indPayroll/payrollLoanAndAdvances");
		model.addObject("uuid", uuid);
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	
	@RequestMapping(value = "/payrollLoanAndAdvancesList")
	public ModelAndView payrollLoanAndAdvancesList(HttpServletRequest request)
	{
		ModelAndView model = new ModelAndView();
		commonUtil.highLightMenu(Constants.OrgSettings, request);
		model.setViewName("indPayroll/payrollLoanAndAdvancesList");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	@RequestMapping(value = "/payrollTaxConfiguration")
	public ModelAndView taxConfiguration(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				// commonUtil.highLightMenu(Constants.OrgSettings, request);
				OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

				String financialStartMonth = orgLocations.getStartMonth();
				String financialEndMonth = orgLocations.getEndMonth();

				Calendar c = Calendar.getInstance();
				String fromYear = c.get(Calendar.YEAR) + "";
				c.roll(Calendar.MONTH, 11);
				String toYear = c.get(Calendar.YEAR) + "";
//				String financialYear = fromYear + "-" + toYear;
				
				PayrollTaxDTO payrollTaxDTO = null;
				if(financialStartMonth == null || financialStartMonth.isEmpty() || financialEndMonth == null || financialEndMonth.isEmpty()) {
					payrollTaxDTO = payrollTaxService.getPayrollTaxConfigurationForOrgLocation(orgLocations.getState(), (fromYear + "-" + toYear), "Income Tax");
				} else {
					payrollTaxDTO = payrollTaxService.getPayrollTaxConfigurationByOrgLocationId(orgLocations.getId(), (financialStartMonth + "_" + fromYear) + "-" + (financialEndMonth + "_" + toYear));
				}
				if (payrollTaxDTO == null) {
					payrollTaxDTO = payrollTaxService.getPayrollTaxConfigurationForOrgLocation(orgLocations.getState(), (fromYear + "-" + toYear), "Income Tax");
				}
				modelAndView.addObject("tax", payrollTaxDTO);
				modelAndView.addObject("sourceleadUrl", sourceleadUrl);
				modelAndView.addObject("orgLocations", orgLocations);
				modelAndView.setViewName("indPayroll/taxConfiguration");
			} else {
				modelAndView.setViewName("login");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelAndView;
	}

	
	/**
	 * @author Laxman
	 * @param request
	 * @param paySlipUUID
	 * @return model
	 * @throws Exception
	 */
	@RequestMapping(value = "/payrollPaySlip")
	public ModelAndView paySlip(HttpServletRequest request,@RequestParam(value="paySlipUUID", required=false)String paySlipUUID ) throws Exception
	{
		ModelAndView model = new ModelAndView();
		commonUtil.highLightMenu(Constants.OrgSettings, request);
		if(paySlipUUID != null && paySlipUUID != "") {
			PayrollPaySlipInd payslipInd  = payrollPaySlipIndService.getPayrollPayslipByUUID(paySlipUUID);
			model.addObject("payslipInd", payslipInd);
		}
		model.setViewName("indPayroll/payrollPaySlipInd");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	/**
	 * @author Laxman
	 * @param request
	 * @return model
	 */
	@RequestMapping(value = "/payrollPaySlipView")
	public ModelAndView payrollPaySlipView(HttpServletRequest request)
	{
		ModelAndView model = new ModelAndView();
		commonUtil.highLightMenu(Constants.OrgSettings, request);
		model.setViewName("indPayroll/payrollPaySlipIndView");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	
}
