package com.bis.sourcelead.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.dto.InviteCompanyDTO;
import com.bis.sourcelead.service.InviteCompanyService;
import com.bis.sourcelead.util.CommonUtil;


@RestController
public class InviteCompanyRestController {

	private static final Logger logger = LoggerFactory.getLogger(InviteCompanyRestController.class);
	
	@Autowired
	public InviteCompanyService inviteCompanyService;
	
	
	 @Autowired
	 CommonUtil commonUtil;
	

    @RequestMapping(value = "/storeInviteCompany", method = RequestMethod.POST)
	public @ResponseBody  void storeInviteCompany(InviteCompanyDTO inviteCompanyDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
    	  String inviteCompany =  inviteCompanyService.saveInviteCompany(inviteCompanyDTO);
	}
   
     
    
}
