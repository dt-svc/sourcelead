package com.bis.sourcelead.web;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.CandidateDocuments;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgDocuments;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.States;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateDocumentsService;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.StatesService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.PdfConversionUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@RequestMapping("/app")
public class CandidateTimelineActivityController {
	private static final Logger logger = LoggerFactory.getLogger(CandidateTimelineActivityController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	 CandidateService candidateService;
	
	@Autowired
	 TimeZoneService timeZoneService;
	
	@Autowired
	CommonUtil commonUtil;
	
		/* @PreAuthorize("hasAnyRole('ROLE_CREATE_CANDIDATE','ROLE_CROWDSOURCING_CANDIDATES')")
		 @RequestMapping(value = "/addCandidate")
			public ModelAndView addCandidate(HttpServletRequest request) throws Exception {
			 	logger.debug("Display the candiade page");
			 	OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				ModelAndView model = new ModelAndView();
				model.setViewName("candidates/addCandidate");
				model.addObject("sourceleadUrl", sourceleadUrl);
				
				
				return model;
		    }*/
}
