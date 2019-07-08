package com.bis.sourcelead.web.performance;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.performance.PerformanceFeedbackDTO;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.performance.PerformanceAppraisalAssignedAssociatesService;
import com.bis.sourcelead.service.performance.PerformanceAppraisalService;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class PerformanceAppraisalController {
	
	@Autowired
	PerformanceAppraisalAssignedAssociatesService performanceAppraisalAssignedAssociatesService;
	
	@Autowired
	PerformanceAppraisalService performanceAppraisalService;
	
	
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
				User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
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
					PerformanceFeedbackDTO performanceFeedbackDTO = performanceAppraisalService.getPerformanceFeedbackDetails(assignedUUID,user.getId());
					model.addObject("submissionStage", performanceFeedbackDTO.getSubmissionStage());
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
				User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				 SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
					if(sessionData == null)
					{
						sessionData = new SessionMaintainanceData();
					}
					sessionData.setNav(Constants.PERFORMANCE_MY_APPRAISAL);
					request.getSession().setAttribute("sessionData",sessionData);
				
					PerformanceFeedbackDTO performanceFeedbackDTO = performanceAppraisalService.getPerformanceFeedbackDetails(assignedUUID,user.getId());
					model.addObject("submissionStage", performanceFeedbackDTO.getSubmissionStage());
					model.addObject("assignedUUID", assignedUUID);
					model.setViewName("performance/peerAppraisalDetails");
					return model;
	}
	 

	 @RequestMapping(value = "/employeePerformanceAppraisalList")
	 public ModelAndView employeePerformanceAppraisalList(HttpServletRequest request) throws Exception {
		    
				ModelAndView model = new ModelAndView();
				
				 SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
					if(sessionData == null)
					{
						sessionData = new SessionMaintainanceData();
					}
					sessionData.setNav(Constants.PERFORMANCE_MY_APPRAISAL);
					
					request.getSession().setAttribute("sessionData",sessionData);
					
					model.setViewName("performance/employeePerformanceAppraisalList");
					return model;
	}
	
	 @RequestMapping(value = "/appraisalEmployeesList")
	 public ModelAndView appraisalEmployeesList(HttpServletRequest request,
			 @RequestParam(value="templateUUID", required=true) String templateUUID) throws Exception {
		    
				ModelAndView model = new ModelAndView();
				
				 SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
					if(sessionData == null)
					{
						sessionData = new SessionMaintainanceData();
					}
					sessionData.setNav(Constants.PERFORMANCE_MY_APPRAISAL);
					
					request.getSession().setAttribute("sessionData",sessionData);
					model.addObject("templateUUID", templateUUID);
					model.setViewName("performance/appraisalEmployeesList");
					return model;
	}
	 
	 
	 
	 @RequestMapping(value = "/employeeAppraisalDetails")
	 public ModelAndView employeeAppraisalDetails(HttpServletRequest request,
			 @RequestParam(value="assignedUUID", required=false) String assignedUUID,
			 @RequestParam(value="templateUUID", required=false) String templateUUID) throws Exception {
		    
				ModelAndView model = new ModelAndView();
				User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
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
				
					PerformanceFeedbackDTO performanceFeedbackDTO = performanceAppraisalService.getPerformanceFeedbackDetails(assignedUUID,user.getId());
					model.addObject("submissionStage", performanceFeedbackDTO.getSubmissionStage());
					model.addObject("assignedUUID", assignedUUID);
					model.addObject("templateUUID", templateUUID);
					model.setViewName("performance/employeeAppraisalDetails");
					return model;
	}
	 
}
