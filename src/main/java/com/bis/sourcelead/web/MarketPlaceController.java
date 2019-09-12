package com.bis.sourcelead.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MarketPlaceController {
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Value("${atsUrl}")
	private String atsUrl;
	
	@RequestMapping(value = "/associateMarketplaceList")
	public ModelAndView associateMarket() throws Exception {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("atsUrl", atsUrl);
		model.setViewName("candidates/associateMarketPlaceList");
		return model;
		
	}
	@RequestMapping(value = "/productsList")
	public ModelAndView productList() throws Exception {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("atsUrl", atsUrl);
		model.setViewName("candidates/productList");
		return model;
	}
	
	@RequestMapping(value = "/addProduct")
	public ModelAndView addProduct() throws Exception {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("atsUrl", atsUrl);
		model.setViewName("candidates/addProduct");
		return model;
	}
	
}
