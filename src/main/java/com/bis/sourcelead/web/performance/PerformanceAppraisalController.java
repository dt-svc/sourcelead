package com.bis.sourcelead.web.performance;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.service.performance.PerformanceAppraisalAssignedAssociatesService;
import com.bis.sourcelead.util.SessionMaintainanceData;

@Controller
public class PerformanceAppraisalController {
	
	@Autowired
	PerformanceAppraisalAssignedAssociatesService performanceAppraisalAssignedAssociatesService;
	
	
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
	 
	 @RequestMapping(value = "/selfAppraisalDetails")
	 public ModelAndView selfAppraisalDetails(HttpServletRequest request,
			 @RequestParam(value="assignedUUID", required=false) String assignedUUID) throws Exception {
		    
				ModelAndView model = new ModelAndView();
				
				 SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
					if(sessionData == null)
					{
						sessionData = new SessionMaintainanceData();
					}
					sessionData.setNav(Constants.PERFORMANCE_MY_APPRAISAL);
					request.getSession().setAttribute("sessionData",sessionData);

					boolean isGolasConfigured = performanceAppraisalAssignedAssociatesService.isGoalsConfiguredForAssignedPerformanceApprisal(assignedUUID);
					if(isGolasConfigured == true) {
						model.addObject("isGolasConfigured", Constants.YES);
					}else {
						model.addObject("isGolasConfigured", Constants.NO);
					}
					model.addObject("assignedUUID", assignedUUID);
					model.setViewName("performance/selfAppraisalDetails");
					return model;
	}
	 
	 
	 @RequestMapping(value = "/getTemplateOverView")
		public ModelAndView getTemplateOverView(HttpServletRequest request) throws Exception{
			ModelAndView model = new ModelAndView();
			model.setViewName("performance/templateOverView");
			return model;
		}
	 
	 @RequestMapping(value = "/getAppraisalFeedbackDetails")
		public ModelAndView getAppraisalFeedbackDetails(HttpServletRequest request) throws Exception{
			ModelAndView model = new ModelAndView();
			model.setViewName("performance/appraisalFeedback");
			return model;
		}
	 
	 
	 @RequestMapping(value = "/peerAppraisalDetails")
	 public ModelAndView peerAppraisalDetails(HttpServletRequest request,
			 @RequestParam(value="assignedUUID", required=false) String assignedUUID) throws Exception {
		    
				ModelAndView model = new ModelAndView();
				
				 SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
					if(sessionData == null)
					{
						sessionData = new SessionMaintainanceData();
					}
					sessionData.setNav(Constants.PERFORMANCE_MY_APPRAISAL);
					request.getSession().setAttribute("sessionData",sessionData);
				
					model.addObject("assignedUUID", assignedUUID);
					model.setViewName("performance/peerAppraisalDetails");
					return model;
	}
	 

}
