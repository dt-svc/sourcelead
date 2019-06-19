package com.bis.sourcelead.web.performance;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.util.SessionMaintainanceData;

@Controller
public class PerformanceAppraisalController {
	
	
	 @RequestMapping(value = "/performanceAppraisalList")
	 public ModelAndView performanceMyAppraisalList(HttpServletRequest request) throws Exception {
		    
				ModelAndView model = new ModelAndView();
				
				 SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
					if(sessionData == null)
					{
						sessionData = new SessionMaintainanceData();
					}
					sessionData.setNav(Constants.PERFORMANCE_MY_APPRAISAL);
					
					request.getSession().setAttribute("sessionData",sessionData);
					
					model.setViewName("performance/performanceAppraisalList");
					return model;
	}

}
