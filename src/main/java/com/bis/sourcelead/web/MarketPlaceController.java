package com.bis.sourcelead.web;

import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@RequestMapping(value = "/saveMarketPlaceDetails", method = RequestMethod.POST)
	public @ResponseBody Response<String> saveMarketPlaceDetails(MarketPlaceProductsDTO marketPlaceProductsDTO, HttpServletRequest request)throws Exception {
		Response<String> response = new Response<>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
        Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
      
        response =  addProductService.savemarketPlaceDetails(marketPlaceProductsDTO,orgLocations,user,org);
		 return response;
}
	
	@RequestMapping(value = "/getMarketPlaceListDetails", method = RequestMethod.GET)
	public @ResponseBody ResponseList<MarketPlaceProductsDTO> getWorkflowList(
			MarketPlaceProductsDTO marketPlaceProductsDTO,
			HttpServletRequest request){
		ResponseList<MarketPlaceProductsDTO> responseList = new ResponseList<>();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
        String searchValue ="";
	try {
			String[] tableColumns = { "productCode","category", "manufacturerName", "manufacturerDate","price","currencySpecification","priority",
					"docFile","isNegotiate","shareContactDetails","productShareType","productDescription","uploadPhotos"};
		  	int sortColumnIndex = -1; 
	  		String sortColumnOrder = "desc"; 
 	  		if (marketPlaceProductsDTO.getOrder() != null && !marketPlaceProductsDTO.getOrder().isEmpty()) {
	  		sortColumnIndex = Integer.parseInt(marketPlaceProductsDTO.getOrder().get(0).get("column"));
	  		sortColumnOrder = marketPlaceProductsDTO.getOrder().get(0).get("dir"); 
	  		}
 	  		    if (sortColumnIndex != -1) {
 	  		    	marketPlaceProductsDTO.setSortOrder(sortColumnOrder);
 	  		    	marketPlaceProductsDTO.setSortColumn(tableColumns[sortColumnIndex]);
 	  		    }
			responseList = addProductService.getMareketPlaceListDetails(marketPlaceProductsDTO, orgLocations, user, org, searchValue);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return responseList;
		
	}
	
	@RequestMapping(value = { "/deleteMarketPlaceProductByUuid" }, method = RequestMethod.POST)
	public @ResponseBody Boolean deleteMarketPlaceProductByUuid(@RequestParam(value = "uuid", required = false) String uuid, HttpServletRequest request) throws Exception {
	
	
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Boolean status = addProductService.deleteMarketPlaceProductByUuid(uuid, orgLocations.getId());
		
		return status;
	}
	
	@RequestMapping(value = "/editMarketPlace")
	public ModelAndView editMarketPlace() throws Exception {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("atsUrl", atsUrl);
		model.setViewName("userMarketplace/editMarketPlace");
		return model;
	}
	
	
	}