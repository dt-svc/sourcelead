package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
public class BillingPayableRestController {
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	
	
	 @RequestMapping(value = "/getVendorInvoiceAssociatesList", method = RequestMethod.GET)
	 	public @ResponseBody  String getVendorInvoiceAssociatesList(@RequestParam(value = "selectedAssociates", required = false) String selectedAssociates,
	 			@RequestParam(value = "vendorOrgRelationId", required = false) Long vendorOrgRelationId,HttpServletRequest request, HttpServletResponse response) throws Exception
	 	{
	     	String resultJson;
	     	
	     	List<Long> associateIds = new ArrayList<Long>();
	     	List<Candidate> associateList = null;
	     	
	     	
	     	if(!isNullOrEmpty(selectedAssociates)){
	     		String[] selectedAssoicatesIds =  selectedAssociates.split(",");
	         	for(int i=0; i < selectedAssoicatesIds.length; i++){
	         		associateIds.add(Long.parseLong(selectedAssoicatesIds[i]));
	         	}
	         	associateList = candidateService.getAllActiveVendorAssociatesByOrgLocationAndAssoicatesList(vendorOrgRelationId,associateIds);
	     	}else{
	     		associateList = candidateService.getAllActiveVendorAssociatesByOrgLocation(vendorOrgRelationId);
	     	}
	     	
	 	    resultJson = commonUtil.toJSON(associateList);
	 	    return resultJson;
	 	}
	    

}
