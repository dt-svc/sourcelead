package com.bis.sourcelead.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContractController {
	
	private static final Logger logger = LoggerFactory.getLogger(ContractController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	/**
	 * display contract template list page
	 * @return model
	 */
	@RequestMapping(value = "/orgContractTemplate")
	public ModelAndView orgContractTemplate() {
		logger.debug("display contract template page");
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("contracts/contractsTemplate");
		return model;
	}
	
	/**
	 * 	Display add template page
	 * @return
	 */
	@RequestMapping(value = "/addContractTemplate")
	public ModelAndView addContractTemplate() {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("contracts/addContractTemplate");
		return model;
	}
}
