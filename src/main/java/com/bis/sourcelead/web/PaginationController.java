package com.bis.sourcelead.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.Constants;
import com.bis.sourcelead.util.SessionMaintainanceData;
/**
 * Handles requests for the Client service.
 * 
 * @author PrasadK
 * @date 13th June 2016
 *
 */

@Controller
@PropertySource("${propertyLocation:app.properties}")
public class PaginationController {

	private static final Logger logger = LoggerFactory.getLogger(ClientController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	
	@Autowired
	private UserService userService;
	
	/**
	 * Handles to display client jsp
	 * @return ModelAndView
	 * @throws Exception
	 */
	 @RequestMapping(value = "/pagination")
		public ModelAndView client(HttpServletRequest request) throws Exception {
			ModelAndView model = new ModelAndView();
			SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			if(sessionData == null)
			{
				sessionData = new SessionMaintainanceData();
			}
			sessionData.setNav(Constants.Clients);
			request.getSession().setAttribute("sessionData",sessionData);
			model.setViewName("client/pagination");
			model.addObject("sourceleadUrl", sourceleadUrl);
			return model;
	    }
}