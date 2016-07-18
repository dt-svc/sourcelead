package com.bis.sourcelead.web;


import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;



/**
 * This class is used for...
 * 
 * @author: Venkat Kesav
 * @date: 15-July-2016
 */

@RestController
public class DashboardController {

	private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

	
	
	@Autowired
	public TimesheetSubmissionsService timesheetSubmissionsService;
	
	
	
	@RequestMapping(value = "/dashboard")
	public ModelAndView projects() throws Exception {

			ModelAndView model = new ModelAndView();
			model.setViewName("/dashboard");
			return model;
	}
	
	
	
	 @RequestMapping(value = "/getTimeSheetSubmissionStatus")
		public String getTimeSheetSubmissionStatus(HttpServletRequest request) throws Exception {
		 
		 String resultJson = null;
	     User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	     ModelAndView model = new ModelAndView();
	     
	     ArrayList<Integer> counts = new ArrayList<>();
	     
	     Integer draftStatusCount = timesheetSubmissionsService.getTimeSheetSubmissionsCountBasedOnStatus(user.getId(), "DRAFT");
	     Integer newStatusCount = timesheetSubmissionsService.getTimeSheetSubmissionsCountBasedOnStatus(user.getId(), "NEW");
	  	 Integer savedTimesheetsSubmissionsCount =  newStatusCount +draftStatusCount;
	  	 counts.add(savedTimesheetsSubmissionsCount);
	  	 
	  	 Integer waitingTimesheetsSubmissionsCount = timesheetSubmissionsService.getTimeSheetSubmissionsCountBasedOnStatus(user.getId(), "SUBMITTED");
	   	 counts.add(waitingTimesheetsSubmissionsCount);
	   	 
	  	 Integer approvalTimesheetsSubmissionsCount = timesheetSubmissionsService.getTimeSheetSubmissionsCountBasedOnStatus(user.getId(), "APPROVED");
	  	 counts.add(approvalTimesheetsSubmissionsCount);
	  	 
	  	 Integer rejectedTimesheetsSubmissionsCount = timesheetSubmissionsService.getTimeSheetSubmissionsCountBasedOnStatus(user.getId(), "REJECTED");
	  	 counts.add(rejectedTimesheetsSubmissionsCount);
          
	  	 
	  	 resultJson = toJSON(counts);
		return resultJson;
	}
	
	 private String toJSON(Object obj){
	        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
	            public HierarchicalStreamWriter createWriter(Writer writer) {
	                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
	            }
	        });
	        return xstream.toXML(obj);
	    }
  
}
