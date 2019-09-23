package com.bis.sourcelead.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.dto.BlogSubscriptionDTO;
import com.bis.sourcelead.entity.BlogSubscription;
import com.bis.sourcelead.entity.ReleaseNote;
import com.bis.sourcelead.service.BlogSubscriptionService;
import com.bis.sourcelead.service.ReleaseNoteService;
import com.bis.sourcelead.timesheet.rest.TimesheetRestController;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.dynamicReport.DynamicReport;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

@RestController
public class BlogRestController {
	
private static final Logger logger = LoggerFactory.getLogger(TimesheetRestController.class);
	
	@Value("${static.path}")
	private String staticPath;

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	
    @Autowired
	CommonUtil commonUtil;
    
    @Autowired
    BlogSubscriptionService blogSubscriptionService;
    
    @Autowired
	DynamicReport dynamicReport;
    
    @Autowired
	private ReleaseNoteService releaseNoteService;
    
    
    @RequestMapping(value = "/addBlogSubscription", method = RequestMethod.POST)
	public void addBlogSubscription(BlogSubscriptionDTO blogSubscriptionDTO, HttpServletRequest request) throws Exception {
		blogSubscriptionService.addBlogSubscription(blogSubscriptionDTO);
	}
		 
    /**
     * validate Blog Subscription Email 
     * @param email
     * @param subscriptionUUID
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/validateBlogSubscriptionEmail", method = RequestMethod.POST)
	public @ResponseBody Boolean validateBlogSubscriptionEmail(@RequestParam("email") String email) throws Exception
	{
		 BlogSubscription  blogSubscription = null;
		 Boolean flag = false;
		 if(null == blogSubscription) {
			 flag = blogSubscriptionService.validateBlogSubscriptionByEmail(email);
		 }
	    return flag;
	}	 
		
	@RequestMapping(value = "/downloadReleaseNote")
	@ResponseBody
	public void downloadReleaseNote(@RequestParam(value="releaseUUID", required=false) String releaseUUID,HttpServletRequest request,HttpServletResponse response) throws Exception {
		ReleaseNote releaseNote = releaseNoteService.getReleaseNoteByUUId(releaseUUID);
		    /*File file = null;
		    if(releaseNote != null) {
		    	DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
		        String releaseDate = dateFormat.format(releaseNote.getReleaseDate());
			    StringBuilder fileSB = new StringBuilder();
				file = File.createTempFile(fileSB.append("releaseNote_").append(releaseDate).toString(),".pdf");
				file.setReadable(true);*/
				
				Document document = new Document();
			    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("F:/dynamicReport/html.pdf"));
			    document.open();
			    XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(releaseNote.getReleaseDescrption()));
			    document.close();
		/*	    
//		    	 file=  dynamicReport.releaseNotePdfExport(releaseNote);
		    } 
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			
			response.setContentType(mimeType);
			StringBuilder fileNameSB = new StringBuilder();
			response.setHeader("Content-Disposition", fileNameSB.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
			inputStream.close();
			response.getOutputStream().close();
			response.getOutputStream().flush();	
			if(file!= null){
				file.delete();
			}*/
		}

	 

}
