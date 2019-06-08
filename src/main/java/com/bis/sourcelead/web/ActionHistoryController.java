package com.bis.sourcelead.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.couchbase.connection.CouchbaseConnectionConfig;
import com.bis.sourcelead.couchbase.connection.CouchbasePropertyPathConfiguration;
import com.bis.sourcelead.service.ActionHistoryService;
import com.bis.sourcelead.service.CouchbaseClusterService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.couchbase.client.java.Bucket;

@Controller
@RequestMapping("/app")
public class ActionHistoryController {
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	ActionHistoryService actionHistoryService;
	
	@Autowired
	CouchbaseClusterService couchbaseClusterService;
	
	
	@RequestMapping(value = "/getActionHistoryList/{actionUUID}" , method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView getActionHistoryList(@PathVariable String actionUUID,HttpServletRequest request) throws Exception
	{
		ModelAndView model = new ModelAndView();
		
		String sessionId = (String) SourceLeadSessionManager.getSessionAttribute(request, "sessionId");
		
	    model.addObject("sessionId", sessionId);
		model.addObject("actionUUID", actionUUID);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("/actionHistory");
		return model;
	}
	
	
	@RequestMapping(value = "/logoutFormSourcelead")
	@ResponseBody
	public String logoutFormSourcelead(@RequestParam(value="sessionId", required=false) String sessionId,HttpServletRequest request) throws Exception 
	{
		
		 ApplicationContext ctx = new AnnotationConfigApplicationContext(CouchbasePropertyPathConfiguration.class);
	     CouchbaseConnectionConfig couchConfig = ctx.getBean(CouchbaseConnectionConfig.class);
	     Bucket bucket = couchbaseClusterService.openBucket(couchConfig.getUserLoginSessionStoreBucket(), couchConfig.getPassword());
	     bucket.remove(sessionId+"_SESSION");
		 return "Success";
	}

}
