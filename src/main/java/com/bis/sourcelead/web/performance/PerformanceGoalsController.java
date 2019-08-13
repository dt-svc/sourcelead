package com.bis.sourcelead.web.performance;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.performance.PerformanceGoals;
import com.bis.sourcelead.service.performance.PerformanceGoalsService;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class PerformanceGoalsController {
	
	@Value("${static.path}")
	private String staticPath;
	
	@Autowired
	PerformanceGoalsService performanceGoalsService;
	
	
	 @RequestMapping(value = "/performanceGoalsList")
	 public ModelAndView performanceGoalsList(HttpServletRequest request) throws Exception {
		    
				ModelAndView model = new ModelAndView();
				
				 SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
					if(sessionData == null)
					{
						sessionData = new SessionMaintainanceData();
					}
					sessionData.setNav(Constants.PERFORMANCE_GOALS);
					
					request.getSession().setAttribute("sessionData",sessionData);
					
					model.setViewName("performance/performanceGoalsList");
					return model;
	}
	 
	 
	 @RequestMapping(value = "/addNewGoal")
	 public ModelAndView addNewGoal(HttpServletRequest request,
			 @RequestParam(value="goalUUID", required=false) String goalUUID) throws Exception {
		    
				ModelAndView model = new ModelAndView();
				Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
				
				 SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
					if(sessionData == null)
					{
						sessionData = new SessionMaintainanceData();
					}
					sessionData.setNav(Constants.PERFORMANCE_GOALS);
					
					request.getSession().setAttribute("sessionData",sessionData);
					
					/*if(StringUtils.isNotBlank(goalUUID)) {
						PerformanceGoals performanceGoals = performanceGoalsService.getPerformanceGoalsByUUID(goalUUID);
						if(performanceGoals.getCreatedBy().getId().equals(userId)) {
							model.setViewName("performance/addNewGoal");	
						}else {
							model.setViewName("performance/editPerformanceGoal");
						}
						model.addObject("goalUUID", goalUUID);
					}else {
						model.setViewName("performance/addNewGoal");
					}*/
					
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
			sessionData.setNav(Constants.PERFORMANCE_GOALS);
			
			request.getSession().setAttribute("sessionData",sessionData);
			
			model.addObject("goalUUID", goalUUID);
			model.setViewName("performance/addSubGoal");
			return model;
	 }
	 
	 
	 
	 
	 @RequestMapping(value = "/editPerformanceGoal")
	 public ModelAndView editPerformanceGoal(HttpServletRequest request,
			 @RequestParam(value="goalUUID", required=false) String goalUUID) throws Exception {
		    
				ModelAndView model = new ModelAndView();
				
				 SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
					if(sessionData == null)
					{
						sessionData = new SessionMaintainanceData();
					}
					sessionData.setNav(Constants.PERFORMANCE_GOALS);
					
					request.getSession().setAttribute("sessionData",sessionData);
					
					model.addObject("goalUUID", goalUUID);
					model.setViewName("performance/editPerformanceGoal");
					return model;
	}
	 
	 @RequestMapping(value = "/goalSummary")
	 public ModelAndView goalSummary(HttpServletRequest request,
	 @RequestParam(value="goalUUID", required=true) String goalUUID) throws Exception {
    
		ModelAndView model = new ModelAndView();
		PerformanceGoals goals = performanceGoalsService.getPerformanceGoalsByUUID(goalUUID);
		
		 SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			if(sessionData == null)
			{
				sessionData = new SessionMaintainanceData();
			}
			sessionData.setNav(Constants.PERFORMANCE_GOALS);
			
			request.getSession().setAttribute("sessionData",sessionData);
			
			model.addObject("goalUUID", goalUUID);
			model.addObject("goalId", goals.getId());
			model.setViewName("performance/goalSummary");
			return model;
	 }
	 
	 
	 @RequestMapping(value = "/performanceTeamGoalsList")
	 public ModelAndView performanceTeamGoalsList(HttpServletRequest request) throws Exception {
		    
				ModelAndView model = new ModelAndView();
				
				 SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
					if(sessionData == null)
					{
						sessionData = new SessionMaintainanceData();
					}
					sessionData.setNav(Constants.PERFORMANCE_GOALS);
					
					request.getSession().setAttribute("sessionData",sessionData);
					
					model.setViewName("performance/performanceTeamGoalsList");
					return model;
	}
	 
	 @RequestMapping(value = "/addNewTeamGoal")
	 public ModelAndView addNewTeamGoal(HttpServletRequest request,
			 @RequestParam(value="goalUUID", required=false) String goalUUID) throws Exception {
		    
				ModelAndView model = new ModelAndView();
				
				 SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
					if(sessionData == null)
					{
						sessionData = new SessionMaintainanceData();
					}
					sessionData.setNav(Constants.PERFORMANCE_GOALS);
					
					request.getSession().setAttribute("sessionData",sessionData);
					
					model.addObject("goalUUID", goalUUID);
					model.setViewName("performance/addNewTeamGoal");
					return model;
	}
	 
	 @RequestMapping(value = "/addNewTeamSubGoal")
	 public ModelAndView addNewTeamSubGoal(HttpServletRequest request,
			 @RequestParam(value="goalUUID", required=false) String goalUUID,@RequestParam(value="goalType", required=false) String goalType) throws Exception {
		    
				ModelAndView model = new ModelAndView();
				
				 SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
					if(sessionData == null)
					{
						sessionData = new SessionMaintainanceData();
					}
					sessionData.setNav(Constants.PERFORMANCE_GOALS);
					
					request.getSession().setAttribute("sessionData",sessionData);
					
					model.addObject("goalUUID", goalUUID);
					model.addObject("goalType", goalType);
					model.setViewName("performance/addNewTeamSubGoal");
					return model;
	}
	 

}
