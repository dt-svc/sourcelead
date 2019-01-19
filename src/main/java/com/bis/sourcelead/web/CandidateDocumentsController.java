package com.bis.sourcelead.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.CandidateDocuments;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.RequestedDocuments;
import com.bis.sourcelead.service.CandidateDocumentsService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.RequestedDocumentsService;

@Controller
@RequestMapping("/app")
public class CandidateDocumentsController {
	private static final Logger logger = LoggerFactory.getLogger(CandidateEmployersController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	LookUpDataService lookUpDataService;
	
	@Autowired
	RequestedDocumentsService requestedDocumentsService;
	
	@Autowired
	CandidateDocumentsService candidateDocumentsService;
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	/**
	 * Handles to display candidate documents jsp
	 * @return ModelAndView
	 * @throws Exception
	 */
	 @RequestMapping(value = "/candidateDocuments")
		public ModelAndView candidateDocuments(HttpServletRequest request) throws Exception {
		 	logger.debug("Display the candidateDocuments page");
			ModelAndView model = new ModelAndView();
			model.setViewName("candidates/candidate");
			model.addObject("sourceleadUrl", sourceleadUrl);
			
			 List<LookUpData> documentsList = lookUpDataService.getLookUpDataByCategory(Constants.DOCUMENT_TYPE);
			 model.addObject("documentsList", documentsList);
			 
			return model;
	    }
	 
	 @RequestMapping(value = "/ws/requestedDocuments")
		public ModelAndView requestedDocuments(@RequestParam(value = "requestDocUUID", required = false) String requestDocUUID) throws Exception {
			ModelAndView model = new ModelAndView();
			if(!isNullOrEmpty(requestDocUUID)){
				RequestedDocuments requestedDocuments = requestedDocumentsService.getRequestedDocumentsByRequestDocUUID(requestDocUUID);
				if(null != requestedDocuments){
					List<CandidateDocuments> candidateDocumentsList = candidateDocumentsService.getCandidateDocumentsListByRequestedDocumentsId(requestedDocuments.getId());
					model.setViewName("candidates/requestedDocuments");
					model.addObject("sourceleadUrl", sourceleadUrl);
					model.addObject("requestDocUUID", requestDocUUID);
					model.addObject("candidateDocumentsList", candidateDocumentsList);
					model.addObject("candidateDocumentsListSize", candidateDocumentsList.size());
					model.addObject("orgName",requestedDocuments.getOrgLocationId().getOrg().getOrgName());
					model.addObject("orgId",requestedDocuments.getOrgLocationId().getOrg().getId());
					model.addObject("orgLocationId",requestedDocuments.getOrgLocationId().getId());
					model.addObject("isDocsUploaded",requestedDocuments.getIsDocsUploaded());
				}
			}
			return model;
	    }
	 
	 
	
	
}
