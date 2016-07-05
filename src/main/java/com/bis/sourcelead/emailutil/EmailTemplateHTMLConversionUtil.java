package com.bis.sourcelead.emailutil;

import java.io.Serializable;

import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.Org;


/**
 * This class for build email template
 * @author Prasad salina
 *
 */
public class EmailTemplateHTMLConversionUtil implements Serializable {
	
	
	private static final long serialVersionUID = -3317550653234933840L;

	public static String convertBodyOfEmailTemplateToHTML(String body , String signature ,String callToActionText , String ButtonText , String url, EmailMessage emailMessage, Org org) {	
		 
		StringBuilder tempHTML = new StringBuilder();
		
		  tempHTML.append("<table width='100%' border='s0' cellspacing='0' cellpadding='10'  style='font-size:14px; border-left: 15px solid #f2f2f2; border-right: 15px solid #f2f2f2; border-bottom: 30px solid #f2f2f2'>");
		     tempHTML.append("<tr>");
				 /* if(org.getFileName() != null) {
					 tempHTML.append("<td bgcolor='#378ad0' style='border-top:1px solid #0094D2; border-left:1px solid #0094D2 '><img style='color:#FFFFFF;' src='"+img+"'  height='50' width='80'  border='0' /></a></td>");
				  }else{
					 tempHTML.append("<td bgcolor='#378ad0' style='border-top:1px solid #0094D2; border-left:1px solid #0094D2 '><font style='color:#FFFFFF;' height='50' width='80' border='0' />"+org.getOrgName()+"</font></a></td>");
				  /*}*/
		     tempHTML.append("<td bgcolor='#378ad0' style='border-top:1px solid #0094D2; border-left:1px solid #0094D2 '><font style='color:#FFFFFF;' height='50' width='80' border='0' />SourceLead</font></a></td>");  
		     tempHTML.append("</tr>");
		     
		     tempHTML.append("<tr>");
		     tempHTML.append("<td style='border: 1px solid #fff;'><table width='100%' border='0' cellspacing='0' cellpadding='20'>");
		     tempHTML.append("<tr>");
		     tempHTML.append("<td bgcolor='#FFFFFF'>");
		     tempHTML.append(body);
		          
          	 if(!isNullOrEmpty(callToActionText)){
          		tempHTML.append(callToActionText+"<br/><br/>");
          	 }
			 if(!isNullOrEmpty(url)){
				tempHTML.append("<center><a target='_blank'  href='"+url+"' style='border-radius:3px;color:white;font-size:15px;padding:14px 7px 14px 7px;max-width:150px;font-family:\"Open Sans\",\"Lucida Grande\",\"Segoe UI\",arial,verdana,\"Lucida Sans Unicode\",tahoma,sans-serif;border:1px #1373b5 solid;text-align:center;text-decoration:none;width:280px;margin:6px auto;display:block;background-color:#378ad0'>"+ButtonText+"</a></center><br/><br/><br/>");
			 }
			 if(!isNullOrEmpty(signature)){
				tempHTML.append("<hr />");
			 }
		          
		     tempHTML.append("<table width='100%' border='0' cellspacing='0' cellpadding='0'>");
		     tempHTML.append("<tr>");
		     tempHTML.append("<td style='line-height:22px; padding-top:12px;'>");
				 if(!isNullOrEmpty(signature)){
					tempHTML.append(signature);
				 }
		     tempHTML.append("</td>");
		     tempHTML.append("</tr>");
		     tempHTML.append("</table>");
		     tempHTML.append("</td>");
		     tempHTML.append("</tr>");
		     tempHTML.append("</table>");
		     tempHTML.append("</td>");
		     tempHTML.append("</tr>");
		     
		     tempHTML.append("<tr>");
			     tempHTML.append("<td  style='color:rgb(126, 117, 117); background-color:#f2f2f2; border: 1px solid #F2F2F2;'>");
			     tempHTML.append("<a href='#' target='_blank' style='color:rgb(126, 117, 117)'>About SourceLead</a> |");
			     tempHTML.append("<a href='#' target='_blank' style='color:rgb(126, 117, 117)'> Contact Us</a> |");
			     tempHTML.append("<a href='#' target='_blank' style='color:rgb(126, 117, 117)'> Legal Notices</a> |");
			     tempHTML.append("<a href='#' target='_blank' style='color:rgb(126, 117, 117)'> Terms of Use</a> |");
			     tempHTML.append("<a href='#' target='_blank' style='color:rgb(126, 117, 117)'> Your Privacy Rights</a>");
			     tempHTML.append("<p>For more information about SourceLead, go to <a href='#' target='_blank' style='color:rgb(126, 117, 117)'>sourcelead.com</a></p>");
			     tempHTML.append("</td>");
		     tempHTML.append("</tr>");
		tempHTML.append("</table>");
		 
		 return tempHTML.toString();
	}
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
}
