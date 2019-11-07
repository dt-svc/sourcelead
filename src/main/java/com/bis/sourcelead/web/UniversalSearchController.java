package com.bis.sourcelead.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.util.SessionMaintainanceData;

@Controller
@PropertySource("${propertyLocation:app.properties}")
@RequestMapping("/app")
public class UniversalSearchController {
//	/universalSearch
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	 @RequestMapping(value = "/universalSearchForm")
		public ModelAndView universalResultForm(@RequestParam(value = "searchType", required = false) String searchType,@RequestParam(value = "searchValue", required = false) String searchValue,HttpServletRequest request) {
		 SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			if(sessionData == null)
			{
				sessionData = new SessionMaintainanceData();
			}
			sessionData.setNav(Constants.Dashboard);
			
			request.getSession().setAttribute("sessionData",sessionData);
			ModelAndView model = new ModelAndView();
			model.addObject("searchType", searchType);
			model.addObject("searchValue", searchValue);
			model.setViewName("universalsearch/searchresult");
			model.addObject("sourceleadUrl", sourceleadUrl);
			return model;

		}
}
