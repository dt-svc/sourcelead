package com.bis.sourcelead.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.userMarketplace.dto.MarketPlaceProductsDTO;
import com.bis.sourcelead.userMarketplace.entity.MarketPlaceProducts;
import com.bis.sourcelead.userMarketplace.service.AddProductService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.workflow.dto.RequirementWorkFlowDTO;

@Controller
public class MarketPlaceController {
	
	
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Value("${atsUrl}")
	private String atsUrl;

	@Autowired
	private UserService userService;
	
	@Autowired
	private AddProductService addProductService;
	
	
	@RequestMapping(value = "/productList")
	public ModelAndView productList() throws Exception {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("atsUrl", atsUrl);
		model.setViewName("userMarketplace/productList");
		return model;
	}
	
	@RequestMapping(value = "/addProduct")
	public ModelAndView addProduct() throws Exception {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("atsUrl", atsUrl);
		model.setViewName("userMarketplace/addProduct");
		return model;
	}
	
	
}