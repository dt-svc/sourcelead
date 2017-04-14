package com.bis.sourcelead.rest;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ContractsTemplateService;
import com.bis.sourcelead.service.ProjectMembersService;
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
	
	@Autowired
	ContractsTemplateService contractsTemplateService;
	
	
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
    	/*String k = "<h5><b>Business Intelli Service aggrement</b></h5><br>THIS PROFESSIONAL SERVICES AGREEMENT (this 'Agreement') is "
				+ " Contractor which may render services to BIS or BIS's clients in the future. Contractor shall<br><div style='text-align: center;'><span style='font-weight: bold;'>Business Intelli Solutions Inc.</span><br>--------------------------------------------------------------------------------------------------------------------------------------------<br></div><div style='text-align: center;'><span style='color: rgb(61, 133, 198);'>3535 Victory Group Way, Building 4, Frisco, TX 75034.<br>PH: 214 295 5535 FAX: 972 559 3641<br>Email: info@businessintelli.com, www.businessintelli.com</span></div>"*/; 
    	
				
		/*String json1 = "{" + id + "}";
		JSONObject jsonObj = new JSONObject(json1);
		
		String content = jsonObj.getString("templateContent");
		String header = "<h5><b>"+jsonObj.getString("templateName")+"</b></h5><br>";
		String totalBody = header + content;*/
				
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
	
}
