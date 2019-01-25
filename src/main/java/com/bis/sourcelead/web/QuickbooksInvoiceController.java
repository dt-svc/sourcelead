package com.bis.sourcelead.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/app")
public class QuickbooksInvoiceController {
	
private static final Logger logger = LoggerFactory.getLogger(QuickbooksInvoiceController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	
	
	@RequestMapping(value = "/quickBookInvoice")
	public ModelAndView quickBookInvoice(HttpServletRequest request) throws Exception {
		
		ModelAndView model = new ModelAndView();
		model.setViewName("backOffice/quickBookInvoice");
		model.addObject("sourceleadUrl", sourceleadUrl);
		
		
		/*SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Finance);
		
		request.getSession().setAttribute("sessionData",sessionData);
		*/
		return model;
		
	}
	
	
	
}
