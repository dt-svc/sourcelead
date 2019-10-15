package com.bis.sourcelead.background.web;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.jadira.usertype.spi.utils.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@RequestMapping("/bgc")
public class BackgroundController {

	private static final Logger logger = LoggerFactory.getLogger(BackgroundController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;

	@Value("${static.path}")
	private String staticPath;

	
	
	
	@RequestMapping(value = { "/inProgressBgcDetails" })
	public ModelAndView viewInprogressBgcDetails(HttpServletRequest request	) throws Exception {
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		SessionMaintainanceData sessionData = new SessionMaintainanceData();
		sessionData.setNav(Constants.InProgress_Bgc_Details);
			  model.addObject("sourceleadUrl", sourceleadUrl);
			  model.setViewName("/background/inProgressBgcDetails");
		return model;
	}

	@RequestMapping(value = { "/completedBgcDetails" })
	public ModelAndView viewCompletedBgcDetails(HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		SessionMaintainanceData sessionData = new SessionMaintainanceData();
		sessionData.setNav(Constants.Completed_Bgc_Details);
		
			  model.addObject("sourceleadUrl", sourceleadUrl);
			  model.setViewName("/background/completedBgcDetails");
		return model;
	}

}
