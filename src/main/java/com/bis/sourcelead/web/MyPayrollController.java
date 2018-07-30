package com.bis.sourcelead.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/app/myPayroll")
public class MyPayrollController {
	private static final Logger logger = LoggerFactory.getLogger(TaxAdminController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@RequestMapping(value = "/pays")
	public ModelAndView paySlips(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			modelAndView.addObject("activeTaxTab", "pays");
			modelAndView.setViewName("payroll_india/my_payroll/pays");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return modelAndView;
	}

	@RequestMapping(value = "/forms")
	public ModelAndView forms(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			modelAndView.addObject("activeTaxTab", "forms");
			modelAndView.setViewName("payroll_india/my_payroll/forms");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return modelAndView;
	}

	@RequestMapping(value = "/loansAndAdvances")
	public ModelAndView loansAndAdvances(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			modelAndView.addObject("activeTaxTab", "loansAndAdvances");
			modelAndView.setViewName("payroll_india/my_payroll/loansAndAdvances");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return modelAndView;
	}
}
