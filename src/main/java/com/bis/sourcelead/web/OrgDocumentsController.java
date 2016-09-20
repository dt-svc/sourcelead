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
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.TimeSheetSubmissionCount;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

/**
 *  to handle the documents of the client and vendors
 * @author Prasanna Vadla
 *
 */

@Controller
public class OrgDocumentsController {
	
	private static final Logger logger = LoggerFactory.getLogger(OrgDocumentsController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	/**
	 * checks string is empty or null
	 * @param str
	 * @return
	 */
	 
	 protected static boolean isNullOrEmpty(String str) {
			return str == null || "".equals(str.trim());
		}
}
