package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ContractsTemplateDao;
import com.bis.sourcelead.dao.RequirementSubmissionDao;
import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ContractsTemplateService;
import com.itextpdf.text.Document;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class ContractsTemplateServiceImpl implements ContractsTemplateService{

	@Autowired
	private ContractsTemplateDao contractsTemplateDao;
	
	
	@Override
	public ContractsTemplate saveContractTemplate(JSONObject jsonObj, User user, OrgLocations orgLocation) throws Exception {
		
		
		ContractsTemplate contractsTemplate = new ContractsTemplate();
		contractsTemplate.setOrgLocationId(orgLocation);
		
		contractsTemplate.setTemplateName(jsonObj.getString("templateName"));
		
		contractsTemplate.setTemplateType(jsonObj.getString("contractType"));
		
		if(jsonObj.has("letterHead") && !isNullOrEmpty(jsonObj.getString("letterHead"))){
			contractsTemplate.setLetterHead(jsonObj.getString("letterHead"));
		}
		
		if(jsonObj.has("description") && !isNullOrEmpty(jsonObj.getString("description"))){
			contractsTemplate.setDescription(jsonObj.getString("description"));
		}
		
		if(jsonObj.has("templateContent") && !isNullOrEmpty(jsonObj.getString("templateContent"))){
			String content = jsonObj.getString("templateContent");
			String header = "<div style='text-align: center;'>"+jsonObj.getString("templateName")+"<br></div><br>";
			String totalBody = header + content;
			contractsTemplate.setTemplateContent(totalBody);
		}
		contractsTemplate.setCreatedDate(new Date());
		contractsTemplate.setCreatedBy(user.getUserCode());
		contractsTemplate.setUpdatedDate(new Date());
		contractsTemplate.setUpdatedBy(user.getUserCode());
		// for active templates status is 0
		contractsTemplate.setTemplateStatus("0");
		return contractsTemplateDao.saveContractTemplate(contractsTemplate);
	}
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	@Override
	public String generatePDFContractTemplate(JSONObject jsonObj, User user, OrgLocations orgLocation) throws Exception {
		String k = "<h5><b>Business Intelli Service aggrement</b></h5><br>THIS PROFESSIONAL SERVICES AGREEMENT (this 'Agreement') is "
				+ " Contractor which may render services to BIS or BIS's clients in the future. Contractor shall<br><div style='text-align: center;'><span style='font-weight: bold;'>Business Intelli Solutions Inc.</span><br>--------------------------------------------------------------------------------------------------------------------------------------------<br></div><div style='text-align: center;'><span style='color: rgb(61, 133, 198);'>3535 Victory Group Way, Building 4, Frisco, TX 75034.<br>PH: 214 295 5535 FAX: 972 559 3641<br>Email: info@businessintelli.com, www.businessintelli.com</span></div>";
    	    OutputStream file = new FileOutputStream(new File("C:\\Users\\BIS94\\Desktop\\Itext\\TestSample.pdf"));
    	    Document document = new Document();
    	    PdfWriter.getInstance(document, file);
    	    document.open();
    	    HTMLWorker htmlWorker = new HTMLWorker(document);
    	    htmlWorker.parse(new StringReader(k));
    	    document.close();
    	    file.close();
			return "Success";		
	}

	@Override
	public ContractsTemplate getContractTemplateById(Long contractTemplateId) {
		return contractsTemplateDao.saveContractTemplate(contractTemplateId);
	}

	@Override
	public List<ContractsTemplate> getAllContractsTemplatesByOrgLocationid(OrgLocations orgLocation) throws Exception
	{
		return contractsTemplateDao.getAllContractsTemplatesByOrgLocationid(orgLocation);
	}

	@Override
	public List<ContractsTemplate> getAllContractsTemplatesByContractTypeLandLocationId(Long orgLocationId, String contractType) throws Exception
	{
		return contractsTemplateDao.getAllContractsTemplatesByContractTypeLandLocationId(orgLocationId,contractType);
	}

	
}
