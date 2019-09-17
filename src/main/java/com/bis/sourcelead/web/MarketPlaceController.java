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
import com.bis.sourcelead.marketPlaceService.AddProductService;
import com.bis.sourcelead.marktePlace.MarketPlaceProducts;
import com.bis.sourcelead.marktePlace.MarketPlaceProductsDTO;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
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
	
	
	@RequestMapping(value = "/associateMarketplaceList")
	public ModelAndView associateMarket() throws Exception {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("atsUrl", atsUrl);
		model.setViewName("userMarketplace/associateMarketPlaceList");
		return model;
		
	}
	@RequestMapping(value = "/productsList")
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
	
	@RequestMapping(value = "/saveMarketPlaceDetails", method = RequestMethod.POST)
	public @ResponseBody Response<String>saveMarketPlaceDetails(MarketPlaceProductsDTO marketPlaceProductsDTO, HttpServletRequest request)throws Exception {
		Response<String> response = new Response<>();
		Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
        Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
      
		MarketPlaceProducts	marketPlaceProducts = addProductService.savemarketPlaceDetails(marketPlaceProductsDTO,orgLocations,userId);
		return null;
}
}