package com.bis.sourcelead.rest;

import java.io.Writer;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.CandidateTimelineActivity;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CandidateTimelineActivityService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

@RestController
public class CandidateTimelineActivityRestController {

	private static final Logger logger = LoggerFactory.getLogger(CandidateTimelineActivityRestController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private CandidateTimelineActivityService candidateTimelineActivityService;
	
	
	@RequestMapping(value = "/getCandidateActivitiesByCandidateIdAndLocationCode", method = RequestMethod.POST)
	public @ResponseBody String getCandidateActivitiesByCandidateIdAndLocationCode(@RequestParam(value = "candidateId", required = false) Long candidateId, @RequestParam(value = "locationId", required = false) Long locationId) throws Exception {
		logger.debug("Calling getCandidateTimelineActivities mrthod to fetch Candidate Activities");
		List<CandidateTimelineActivity> activitiesList = candidateTimelineActivityService.getCandidateTimelineActivities(candidateId, locationId);
		for(CandidateTimelineActivity activity : activitiesList){
			
			switch (activity.getActionIcon()){
			    case "JOBS": 
			    	activity.setActionIconClass(Constants.JOBS_ACTION_ICON_CLASS);
			        break;
			    case "CANDIDATE": 
			    	activity.setActionIconClass(Constants.CANDIDATE_ACTION_ICON_CLASS);
			        break;
			    case "PROJECTS": 
			    	activity.setActionIconClass(Constants.PROJECTS_ACTION_ICON_CLASS);
			        break;
			    case "TIMESHEETS": 
			    	activity.setActionIconClass(Constants.TIMESHEETS_ACTION_ICON_CLASS);
			        break;
			    case "EXPENSES": 
			    	activity.setActionIconClass(Constants.EXPENSES_ACTION_ICON_CLASS);
			        break;
			}
			
			
		}
		return toJSON(activitiesList);
	}
	
	
	public String toJSON(Object obj){
	        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
	            public HierarchicalStreamWriter createWriter(Writer writer) {
	                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
	            }
	        });
	        return xstream.toXML(obj);
	    }
}
