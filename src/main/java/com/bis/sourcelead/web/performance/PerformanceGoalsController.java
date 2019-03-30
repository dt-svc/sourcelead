package com.bis.sourcelead.web.performance;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.util.SessionMaintainanceData;

@Controller
public class PerformanceGoalsController {
	
	@Value("${static.path}")
	private String staticPath;
	
	
	 @RequestMapping(value = "/performanceGoalsList")
	 public ModelAndView performanceGoalsList(HttpServletRequest request) throws Exception {
		    
				ModelAndView model = new ModelAndView();
				
				 SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
					if(sessionData == null)
					{
						sessionData = new SessionMaintainanceData();
					}
					sessionData.setNav(Constants.PERFORMANCE);
					
					request.getSession().setAttribute("sessionData",sessionData);
					
					model.setViewName("performance/performanceGoalsList");
					return model;
	}
	 
	 
	 @RequestMapping(value = "/addNewGoal")
	 public ModelAndView addNewGoal(HttpServletRequest request,
			 @RequestParam(value="goalUUID", required=false) String goalUUID) throws Exception {
		    
				ModelAndView model = new ModelAndView();
				
				 SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
					if(sessionData == null)
					{
						sessionData = new SessionMaintainanceData();
					}
					sessionData.setNav(Constants.PERFORMANCE);
					
					request.getSession().setAttribute("sessionData",sessionData);
					
					model.addObject("goalUUID", goalUUID);
					model.setViewName("performance/addNewGoal");
					return model;
	}
	 
	 
	 		 @RequestMapping(value = "/addNewSubGoal")
	 		 public ModelAndView addNewSubGoal(HttpServletRequest request,
			 @RequestParam(value="goalUUID", required=false) String goalUUID) throws Exception {
		    
				ModelAndView model = new ModelAndView();
				
				 SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
					if(sessionData == null)
					{
						sessionData = new SessionMaintainanceData();
					}
					sessionData.setNav(Constants.PERFORMANCE);
					
					request.getSession().setAttribute("sessionData",sessionData);
					
					model.addObject("goalUUID", goalUUID);
					model.addObject("goalType", "Sub_Goal");
					model.setViewName("performance/addNewGoal");
					return model;
	}

}
