package com.bis.sourcelead.web;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.AssociateBankDataDTO;
import com.bis.sourcelead.payroll.service.AssociateBankDataService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@RequestMapping("/app")

public class AssociateImportController {
	private static final Logger logger = LoggerFactory.getLogger(AssociateController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	AssociateBankDataService associateBankDataService;
	
	
	
	/**
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadbankdetails", method = RequestMethod.POST)
	@ResponseBody
	public AssociateBankDataDTO uploadbankdetails(MultipartHttpServletRequest request) throws Exception
	{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		MultipartFile multipartFile = null;
		Iterator<String> itrator = request.getFileNames();
		if (itrator.hasNext()) {
			multipartFile = request.getFile(itrator.next());
		}
		AssociateBankDataDTO response = new AssociateBankDataDTO();
		if (multipartFile != null) {
			response = associateBankDataService.saveImportBankDetails(multipartFile, orgLocation, user, org);
			return response;
		}
		return response;
	}

}
