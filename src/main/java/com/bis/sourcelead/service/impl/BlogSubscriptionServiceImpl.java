package com.bis.sourcelead.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.BlogSubscriptionDao;
import com.bis.sourcelead.dto.BlogSubscriptionDTO;
import com.bis.sourcelead.entity.BlogSubscription;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.SourceleadBlogs;
import com.bis.sourcelead.service.BlogSubscriptionService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.SourceleadBlogService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class BlogSubscriptionServiceImpl implements BlogSubscriptionService{
	
	@Autowired
	BlogSubscriptionDao  blogSubscriptionDao;
	
	@Autowired
	SourceleadBlogService sourceleadBlogService;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;


	@Override
	public void addBlogSubscription(BlogSubscriptionDTO blogSubscriptionDTO) throws Exception {
		    BlogSubscription blogSubscription = null;
		    blogSubscription = new BlogSubscription();
		    blogSubscription.setEmail(blogSubscriptionDTO.getEmail());
		    blogSubscription.setStatus("Y");
		    blogSubscription.setSubscriptionUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		    blogSubscription.setCreatedDate(commonUtil.currentDate());
		    blogSubscriptionDao.addBlogSubscription(blogSubscription);
		    sendEmail(blogSubscriptionDTO);
	}
	
	@Override
	public void sendEmail(BlogSubscriptionDTO blogSubscriptionDTO) throws Exception {
		
		 
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setToEmail(blogSubscriptionDTO.getEmail());
		 emailMessage.setFromEmail("info@sourcelead.com");
		 emailMessage.setFirstName("Admin");
		 globalVarsMap = getBlogSubscriptionTable(globalVarsMap); 
		 
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		 
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.BLOG_SUBSCRIPTION,Constants.ENTITY_TYPE_EMAIL);
		
		//covert subject,body and signature
		 String subject = emailScenerioUtil.bulidTemplateSubject(emailScenario.getSubject(),globalVarsMap,emailScenario);
		 String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(),globalVarsMap,emailScenario);
		 String signature = emailScenerioUtil.buildTemplateSignature(emailScenario.getSignature(),globalVarsMap,emailScenario);
		 String url = sourceleadUrl;
		 
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url,null,null);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,1l,iAdressArray,new ArrayList<EmailAttachment>());
		 
		 
		
	}

	
	private Map<String, String>  getBlogSubscriptionTable(Map<String, String> globalVarsMap) throws Exception
	{
		String tempHTML = "";
		String sleadUrl = sourceleadUrl.replace("/app", "");
		
		      //Table Design
				 tempHTML = tempHTML + "<table style='display:table-cell;vertical-align:middle; margin:0px;'>";
				 tempHTML = tempHTML+"<tr align='center'>";
				 tempHTML = tempHTML+"</tr>";
     
				 
				 List<SourceleadBlogs> sourceleadBlogsList = sourceleadBlogService.getAllSourceleadBlogs();
				 if(sourceleadBlogsList.size() > 0) {
					String mailSubject = ""; 
				
				 
				 StringBuilder blogTable = new StringBuilder();
				 int count = 0;
				 for (SourceleadBlogs allblogs : sourceleadBlogsList) {
					 count++;
					 if(count == 1) {
						 mailSubject = allblogs.getBlogTitle();
					 }
					 if(count < 4) {
						 if(!isNullOrEmpty(allblogs.getBlogTitle())){
							 blogTable = blogTable.append("<div class=\"blgWrp\"><div class=\"blgText\"><a href='"+sleadUrl+"/blog/"+allblogs.getHref()+".html'>").append(allblogs.getBlogTitle()).append("</a>").append("\n").append("<p>").append(allblogs.getShortDescription()).append("</p></div>");
						 }
					 }
				 }
				 tempHTML = tempHTML+"<tr align='center'>";
				 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+blogTable+"</td>";
				 tempHTML = tempHTML+"</tr>";
				 
        
	         tempHTML = tempHTML+"</tbody>";
			 tempHTML = tempHTML+"</table>";
			 tempHTML = tempHTML+"</td>";
			 tempHTML = tempHTML+"</tr>";;
			 tempHTML = tempHTML+"<tr style='background-color:#ffffff;min-height:32px;'>";
			 tempHTML = tempHTML+"<td style='padding-top:15px;padding-bottom:15px;padding-right:10px;padding-left:10px;'>";
			 tempHTML = tempHTML+"<table width='100%' style='border-spacing:0;font-family:sans-serif;color:#ffffff; margin:0px;'>";
			 tempHTML = tempHTML+"<tbody>";
			 tempHTML = tempHTML+"<tr>";
			 tempHTML = tempHTML+"<td style='cursor:pointer;padding-bottom:10px;padding-left:0;vertical-align:top;'>";
			 
			 if(sourceleadBlogsList.size() > 3){
				 tempHTML = tempHTML+"<br/><a href='"+sleadUrl+"/blog/all.html'  target='_blank'>View all Blogs</a> </td>";
			 }
			 
			 tempHTML = tempHTML+"</tr>";
			 tempHTML = tempHTML+"</tbody>";
			 tempHTML = tempHTML+"</table>";
			 tempHTML = tempHTML+"</td>";
			 tempHTML = tempHTML+"</tr>";
			 tempHTML = tempHTML+"</tbody>";
			 tempHTML = tempHTML+"</table>";
		 // Actual code End
			 globalVarsMap.put("blogTable", tempHTML);
		     globalVarsMap.put("blogTitle", mailSubject);
			 
			}
		 return globalVarsMap;
		
	}
	
	private boolean isNullOrEmpty(String blogTitle) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BlogSubscription getBlogSubscriptionByBlogId(Long blogSubscriptionId) throws Exception {
		return blogSubscriptionDao.getBlogSubscriptionByBlogId(blogSubscriptionId);
	}

	@Override
	public BlogSubscription validateBlogSubscriptionByEmailAndSubscriptionUUID(String email, String subscriptionUUID)
			throws Exception {
		return blogSubscriptionDao.validateBlogSubscriptionByEmailAndSubscriptionUUID(email, subscriptionUUID);
	}

	@Override
	public Boolean validateBlogSubscriptionByEmail(String email) throws Exception {
		return blogSubscriptionDao.validateBlogSubscriptionByEmail(email);
	}

	@Override
	public BlogSubscription getBlogSubscriptionBySubscriptionUUID(String subscriptionUUID) throws Exception {
		return blogSubscriptionDao.getBlogSubscriptionBySubscriptionUUID(subscriptionUUID);
	}

}
