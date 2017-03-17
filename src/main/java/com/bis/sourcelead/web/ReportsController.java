package com.bis.sourcelead.web;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class ReportsController {

private static final Logger logger = LoggerFactory.getLogger(ReportsController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	 @RequestMapping(value = "/applicantsListForReports")
		public ModelAndView applicantsListForReports() throws Exception {
		 ModelAndView model = new ModelAndView();
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.setViewName("reports/applicantsReport");
		 return model;
			
	 }	
	 @RequestMapping(value = "/applicantsReceivedListForReports")
		public ModelAndView applicantsReceivedListForReports() throws Exception {
		 ModelAndView model = new ModelAndView();
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.setViewName("reports/applicantsReceivedReport");
		 return model;
			
	 }
	 
	 @RequestMapping(value = "/applicantsSubmittedListForReports")
		public ModelAndView applicantsSubmittedListForReports(HttpServletRequest request) throws Exception {
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 ModelAndView model = new ModelAndView();
		 Collection<OrgRelations> orgClientList = orgRelationsService.getAllClientsByOrgLocationId(orgLocations.getId()); 
			if(null != orgClientList && orgClientList.size() > 0){
				for(OrgRelations ocl :orgClientList){
					ocl.setOrgName(ocl.getResponderLocationId().getOrg().getOrgName());
					ocl.setOrgid(ocl.getResponderLocationId().getOrg().getId());
				 }
			    model.addObject("add_clientList", orgClientList);
			}
		 model.addObject("loginOrgLocId", orgLocations.getId());
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.setViewName("reports/applicantsSubmittedReport");
		 return model;
			
	 }	
}
