package com.bis.sourcelead.web;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.dto.IndAssociatePaymentAccountDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.service.PaymentAccountService;
import com.bis.sourcelead.util.SourceLeadSessionManager;



@Controller
@PropertySource("${propertyLocation:app.properties}")
public class AssociateImportController {


	@Autowired
	PaymentAccountService paymentAccountService;
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	
	/**
	 * @rajat
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/app/uploadbankdetails", method = RequestMethod.POST)
	@ResponseBody
	public IndAssociatePaymentAccountDTO uploadbankdetails(MultipartHttpServletRequest request) throws Exception
	{
		//User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		//Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		MultipartFile multipartFile = null;
		Iterator<String> itrator = request.getFileNames();
		if (itrator.hasNext()) {
			multipartFile = request.getFile(itrator.next());
		}
		IndAssociatePaymentAccountDTO response = null;
		if (multipartFile != null) {
			response = paymentAccountService.saveImportBankDetails(multipartFile, orgLocation);
			return response;
		}
		return response;
	}
}
