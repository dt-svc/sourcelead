package com.bis.sourcelead.web;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.TimeSheetSubmissionCount;
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

	// Sammeta David: this controller method is for getting TimesheetSubmission
	// Counts based on Status.
	@RequestMapping(value = "/getTimeSheetSubmissionStatus")
	public String getTimeSheetSubmissionStatus(HttpServletRequest request) throws Exception {

		String resultJson = null;
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		TimeSheetSubmissionCount timeSheetSubmissionCount = new TimeSheetSubmissionCount();
		timeSheetSubmissionCount = timesheetSubmissionsService.getTimeSheetSubmissionsCountByUserId(user.getId());
		timeSheetSubmissionCount = timesheetSubmissionsService.getTimeSheetSubmissionsCountByUserId(user.getId());

		resultJson = toJSON(timeSheetSubmissionCount);
		return resultJson;
	}

	private String toJSON(Object obj) {
		XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
			public HierarchicalStreamWriter createWriter(Writer writer) {
				return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
			}
		});
		return xstream.toXML(obj);
	}

	
	/* Pavan Jampana : This method is to get Timesheet Submissions list based on status */
	@RequestMapping(value = "/totalTimesheetList/{status}")
	public @ResponseBody void tiesheetStatusList(@PathVariable("status") String status, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.sendRedirect("/sourcelead/timesheetlistByStatus?status=" + status);
	}

}
