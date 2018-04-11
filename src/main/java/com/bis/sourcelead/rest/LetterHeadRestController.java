package com.bis.sourcelead.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.dto.LetterHeadDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.entity.LetterHead;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.LetterHeadService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/app")
public class LetterHeadRestController {
	
@Autowired
LetterHeadService letterHeadService;
	
private static final Logger logger = LoggerFactory.getLogger(LetterHeadRestController.class);
	
@RequestMapping(value= "/saveLetterheadData", method = RequestMethod.POST)
public @ResponseBody String saveLetterheadData(LetterHeadDTO letterHeadDTO,HttpServletRequest request,HttpServletResponse response)
		throws Exception {
	String status = "failure";
	LetterHead letterHead = letterHeadService.storeLetterHeadData(letterHeadDTO);
	if(letterHead != null) {
		status = "success";
	}
	return  status;
}

}
