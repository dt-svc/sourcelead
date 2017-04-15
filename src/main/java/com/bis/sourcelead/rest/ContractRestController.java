package com.bis.sourcelead.rest;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.URLConnection;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ContractsTemplateService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.itextpdf.text.Document;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Handles contract related functionalities
 * @author root
 *
 */

@RestController
@PropertySource("${propertyLocation:app.properties}")
public class ContractRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(ContractRestController.class);
	
	@Autowired
	ContractsTemplateService contractsTemplateService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private OrgSpocService orgSpocService;
	
	
	@RequestMapping(value = "/saveTemplate", method = RequestMethod.POST)
	public @ResponseBody String saveClient(@RequestParam(value = "json", required = false) String json,
			HttpServletRequest request) throws Exception {

		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		
		ContractsTemplate contractsTemplate = contractsTemplateService.saveContractTemplate(jsonObj, user, orgLocation);
		
			return contractsTemplate.getId().toString();
	} 
	@RequestMapping(value = "/generatePDF", method = RequestMethod.POST)
	public @ResponseBody String generatePDF(@RequestParam(value = "json", required = false) String json,
			HttpServletRequest request) throws Exception {

		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		
		return contractsTemplateService.generatePDFContractTemplate(jsonObj, user, orgLocation);
		
	} 
	
	@RequestMapping(value = "/testController/{id}", method = RequestMethod.GET)
	public @ResponseBody  void testController(@PathVariable("id") String id,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ContractsTemplate contractsTemplate =	contractsTemplateService.getContractTemplateById(Long.parseLong(id));		
    	ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
  	    Document document = new Document();
  	    PdfWriter.getInstance(document, byteArray);
  	    document.open();
  	    HTMLWorker htmlWorker = new HTMLWorker(document);
  	    htmlWorker.parse(new StringReader(contractsTemplate.getTemplateContent()));
  	    document.close();
  	    byte[] pdfBytes = byteArray.toByteArray();
  	    response.setHeader("Content-disposition","inline; filename=contractPdf.pdf");
  	    response.setContentType("application/pdf");
  	    response.setContentLength(pdfBytes.length);
          ServletOutputStream sos = response.getOutputStream();
          sos.write(pdfBytes);
          sos.flush();
          sos.close();
	}
	

	
	@RequestMapping(value = "/getAllTemplatesByOrgLocationId", method = RequestMethod.GET)
	public @ResponseBody String getAllTemplates(HttpServletRequest request,HttpServletResponse response) throws Exception {
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<ContractsTemplate> contractsTemplate = contractsTemplateService.getAllContractsTemplatesByOrgLocationid(orgLocation);
		return commonUtil.toJSON(contractsTemplate);
	} 
	
	
//	/getVendorContactsByOrgRelationId
	
	
	@RequestMapping(value = "/getVendorContactsByOrgRelationId", method = RequestMethod.GET)
	public @ResponseBody String getVendorContactsByOrgRelationId(@RequestParam(value = "orgRelationId", required = false) String orgRelationId,HttpServletRequest request,HttpServletResponse response) throws Exception {
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<OrgSpoc> orgSpocList = orgSpocService.getOrgSpocByOrgRelationId(Long.parseLong(orgRelationId));
		for(OrgSpoc orgSpoc : orgSpocList){
			StringBuilder sb = new StringBuilder();
			if(null != orgSpoc.getVendorUser().getFirstName() && null != orgSpoc.getVendorUser().getLastName()){
				sb.append(orgSpoc.getVendorUser().getFirstName()).append(" ").append(orgSpoc.getVendorUser().getLastName());
			}else{
				sb.append(orgSpoc.getVendorUser().getFirstName());
			}
			orgSpoc.setSpocName(sb.toString());
		}
		return commonUtil.toJSON(orgSpocList);
	} 
	
	
	@RequestMapping(value = "/getContractsTemplatesByContractType", method = RequestMethod.GET)
	public @ResponseBody String getContractsTemplatesByContractType(@RequestParam(value = "contractType", required = false) String contractType,HttpServletRequest request,HttpServletResponse response) throws Exception {
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		List<ContractsTemplate> contractsTemplateList = contractsTemplateService.getAllContractsTemplatesByContractTypeLandLocationId(orgLocation.getId(),contractType);
		return commonUtil.toJSON(contractsTemplateList);
	} 
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/downloadContractsTemplate", method = RequestMethod.GET)
	public void downloadContractsTemplate(@RequestParam(value="templateId", required=false) String templateId,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ContractsTemplate contractsTemplate =	contractsTemplateService.getContractTemplateById(Long.parseLong(templateId));
   	    File file = new File(contractsTemplate.getTemplateName()+".pdf");
		OutputStream outputStream = new FileOutputStream(file);
   	    Document document = new Document();
   	    PdfWriter.getInstance(document, outputStream);
   	    document.open();
   	    HTMLWorker htmlWorker = new HTMLWorker(document);
   	    htmlWorker.parse(new StringReader(contractsTemplate.getTemplateContent()));
   	    document.close();
   	    outputStream.close();
    	 String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		response.setContentType(mimeType);
		StringBuilder fileName = new StringBuilder();
		response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"" ).append(file.getName()).append( "\"").toString());
		response.setContentLength((int) file.length());
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
		inputStream.close();
		response.getOutputStream().close();
		response.getOutputStream().flush();	
		if(file != null){
			file.delete();
		}
	}
	
	

}
