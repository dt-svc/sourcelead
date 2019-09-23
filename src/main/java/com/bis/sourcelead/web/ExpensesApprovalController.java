package com.bis.sourcelead.web;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.util.SessionMaintainanceData;

@Controller
@RequestMapping("/app")
public class ExpensesApprovalController {

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@RequestMapping(value={"/expensesApprovalList","/expensesApprovalList/{expensesStatus}"})
	public ModelAndView expensesApprovalList(@PathVariable Optional<String> expensesStatus,HttpServletRequest request) throws Exception
	{
		
		ModelAndView model = new ModelAndView();
		try{
		SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession().getAttribute("sessionData");
		if (null == sessionData) {
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.EXPENSES_APPROVAL);
		request.getSession().setAttribute("sessionData", sessionData);
		model.addObject("sourceleadUrl", sourceleadUrl);
		if(expensesStatus.isPresent()) {
			model.addObject("expensesStatusFormDashBoard",expensesStatus.get());
		}
		model.setViewName("expenses/expensesApprovalList");
		}catch(Exception e){
			 model.addObject("AWKWARD", true);
			  model.setViewName("expenses/expensesApprovalList");
		}
		return model;
	}
}
