package com.bis.sourcelead.web;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bis.sourcelead.entity.OrgDocuments;
import com.bis.sourcelead.service.OrgDocumentsService;
import com.bis.sourcelead.util.PdfConversionUtil;

/**
 *  to handle the documents of the client and vendors
 * @author Prasanna Vadla
 *
 */

@Controller
@RequestMapping("/app")
public class OrgDocumentsController {
	
	@Autowired
	private OrgDocumentsService orgDocumentsService;
	
	@Autowired
	private PdfConversionUtil pdfConversionUtil;
	
	
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
	 
	
	/* @RequestMapping(value = "/viewClientDocViewerDocument/{title}", method = RequestMethod.GET)
	    public void viewClientDocViewerDocument(@RequestParam(value="docId", required=false) String docId,
	    		@PathVariable(value="title") String title
	    		,HttpServletResponse response) throws Exception {
		 
		 OrgDocuments orgDocument =  orgDocumentsService.getOrgDocumentsById(Long.parseLong(docId));
			    
		 byte[] pdfBytes = null;
			if("application/pdf".equalsIgnoreCase(orgDocument.getFileType())){
				pdfBytes = orgDocument.getDocFile();
			}
			else{
				 pdfBytes = pdfConversionUtil.convertDocToPdf(orgDocument.getDocFile());
			}
			 response.setHeader("Content-disposition","inline; filename=contractPdf.pdf");
		  	    response.setContentType("application/pdf");
		  	    ServletOutputStream sos = response.getOutputStream();
		  	    if(null != pdfBytes){
		  	    	response.setContentLength(pdfBytes.length);
		  	    	sos.write(pdfBytes);
		  	    }
		          sos.flush();
		          sos.close();

		} */
	 
}
