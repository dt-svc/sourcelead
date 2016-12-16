package com.bis.sourcelead.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.util.CommonUtil;

@RestController
public class MailBoxRestController {
	
	@Autowired
	OrgSpocService orgSpocService;
	

	@Autowired
	private CommonUtil commonUtil;
	
	
	
	@RequestMapping(value = "/getClientContactsEmailsByOrgRelationId", method = RequestMethod.POST)
	@ResponseBody String getClientContactsEmailsByOrgRelationId(@RequestParam(value = "orgRelationIds", required = false) String orgRelationIds) throws Exception {
		
		String[] relationsId = orgRelationIds.split(",");
		StringBuilder emails  = new StringBuilder();
		for(int i=0 ; i < relationsId.length; i++){
			List<OrgSpoc> orgSpocList = orgSpocService.getOrgSpocByOrgRelationId(Long.valueOf(relationsId[i]));
			for(OrgSpoc orgSpoc :orgSpocList){
				emails.append(orgSpoc.getSpocUser().getEmail()).append(",");
			}
		}
		emails.toString();
		
		 String spocEmails =  emails.substring(0,emails.length()-1);
		 System.out.println(spocEmails);
		
		return commonUtil.toJSON(spocEmails);
	}

}
