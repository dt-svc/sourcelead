package com.bis.sourcelead.util;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Tabs;


/**
 * This class for build email template
 * @author Prasad salina
 *
 */
public class EmailTemplateHTMLConversionUtil implements Serializable {
	
	private static final long serialVersionUID = -3317550653234933840L;
	
	public static String convertBodyOfEmailTemplateToHTML(String body , String signature ,String callToActionText , String ButtonText , String url, EmailMessage emailMessage, Org org,OrgLocations orgLocations, Tabs tabs,String templateType) {	
		 
		StringBuilder tempHTML = new StringBuilder();
		/*
		 * need to replace image location
		 * src='"+url+"/resources/images/mail-logo.png'
		 * 
		 * src='http://vannamnew.xp3.biz/html/images/mail-logo.png'
		 */
		
		String newUrl = null;
		if(StringUtils.isNotEmpty(url)) {
			newUrl = StringUtils.removeEnd(url, url.substring(url.lastIndexOf("/")));
		}
		tempHTML.append("<table width='100%' cellpadding='0' cellspacing='0' style='background-color:#EEEEEE; font-family:verdana, arial;'>");
				tempHTML.append("<tr>");
						tempHTML.append("<td>");
								tempHTML.append("<table width='650' align='center' cellpadding='0' cellspacing='0' style='padding:5px 0px; background-color:#fff; border:1px solid #E0E0E0;'>");
										tempHTML.append("<tr>");
												tempHTML.append("<td>");
												  tempHTML.append("<table width='600' align='center'>");
												      tempHTML.append("<tr>");
												      
												      //This condition for org image     
												      if(org != null && org.getId() != null){
												    	  	if(StringUtils.isNotEmpty(org.getFileContentType())){
												    	  	  tempHTML.append("<td style='text-align: center; height:50px;'><img src='"+newUrl+org.getFileUrl()+"' width='auto' style='padding: 5px 20px; max-height:100%; background-color:#fff; margin: 0px auto; display:block;'></td>");
												    	  	}else{
												    	  		 tempHTML.append("<td class='text-logo'><span style='font-size: 20px; color: #757575; width: 400px; display: block; text-overflow: ellipsis; overflow: hidden; white-space: nowrap; padding: 10px 0px; text-align:center; margin:0 auto; font-weight: 600;'>"+orgLocations.getOrgLocationName()+"</span></td>");
												    	  	}
												      }else{
												    	  url = url.replace("/app", "");
												    	  tempHTML.append("<td style='text-align: center; height:50px;'><img src='"+url+"/resources/images/SourceLead-logo.png' style='max-height:100%;'></td>");
												      }
												           
										             tempHTML.append("</tr>");
								                 tempHTML.append("</table>");
						                      tempHTML.append("</td>");
				                         tempHTML.append("</tr>");
		                          tempHTML.append("</table> ");
		
		tempHTML.append("<table width='650'  bgcolor='#f5f5f5' align='center' style='background-color:#f5f5f5; margin:0px auto; border:solid #E0E0E0; border-width:0 1px 1px 1px;'  cellpadding='0' cellspacing='0'>");
				tempHTML.append("<tr>");
						tempHTML.append("<td>");
					  	      tempHTML.append("<table width='650' style='padding:0 15px 15px; margin:20px auto 0; font-family:verdana, arial;'; align='center' cellpadding='0' cellspacing='0' >");
						           tempHTML.append("<tr>");
						                 tempHTML.append("<td>");
						                         tempHTML.append("<div style='font-size:13px; color:#333333; line-height: 20px; padding:0 15px;'>");
		                                         tempHTML.append(body);
		                                         tempHTML.append("</div>");
		                                         
		                                         if(!isNullOrEmpty(signature)){
									                    tempHTML.append("<div style='font-size:13px; font-weight:600; color:#333333; line-height:20px; padding:15px 15px 15px 15px; clear:both;'>");
														tempHTML.append(signature);
													    tempHTML.append(" </div>");		
												 }
					                            								
					                     tempHTML.append("</td>");
					          tempHTML.append("</tr>");
					      tempHTML.append("</table>");
					  tempHTML.append(" </td>");
			    tempHTML.append("</tr>");
		tempHTML.append("</table>");
		
			
			tempHTML.append("<table width='650' bgcolor='#ededed' align='center' cellpadding='0' cellspacing='0' style='background-color:#ededed; padding:0px;'>");
			  tempHTML.append("<tr>");
					  tempHTML.append("<td>");
					       tempHTML.append("<table bgcolor='#ededed'  width='650' align='center' style='background-color:#ededed; padding:10px 15px; border:solid #E0E0E0; border-width:0 1px 0px 1px;'>");
					       if(templateType.equalsIgnoreCase("sourcelead")) {
					    	   tempHTML.append("<tr>");
				                 tempHTML.append("<td>");
				                   tempHTML.append("<p style='color:#214b90; text-transform: uppercase; padding:5px 0px; margin:0px 0px; font-size:13px; font-weight:600; text-align:center'>get started!</p>");
				                   tempHTML.append("<p style='padding:0px 0px 2px 0px; margin:0px 0px; font-weight:400; font-size:11px; text-align:center'>For more information about SourceLead, go to <a target='blank' href='http://www.sourcelead.com/' style='text-decoration:none; color:#0D47A1; display:inline-block; padding:0; border-radius:0; margin:0; font-size:14px'>sourcelead.com</a></p>");
				                   tempHTML.append("<p style='padding:0px 0px 2px 0px; margin:0px 0px; font-weight:400; font-size:11px; text-align:center'>3535 Victory Group Way, Building 4 Frisco, TX 75034, USA, Phone : (214) 295-5535, FAX : (972) 559-3641</p>");
				                 tempHTML.append("</td>");
				            tempHTML.append("</tr>");
				         
				            tempHTML.append("<tr>");    
				                  tempHTML.append("<td>");
				                       tempHTML.append("<p style='text-align:center; padding:2px 0px; margin:0px 0px; font-size:11px; font-weight:400; border-top:1px solid #BDBDBD;'>");
				                       tempHTML.append("<a target='blank' href='http://www.sourcelead.com/about-us.html' style='text-decoration:none; color:#333;' title='About Us'>About Us</a> | <a target='blank' href='http://www.sourcelead.com/contact.html' style='text-decoration:none; color:#333;' title='Contact Us'>Contact Us</a> | <a target='blank' href='http://www.sourcelead.com/ats.html' style='text-decoration:none; color:#333;' title='ATS'>ATS</a> | <a target='blank' href='http://www.sourcelead.com/vms.html' style='text-decoration:none; color:#333;' title='VMS'>VMS</a> | <a target='blank' href='http://www.sourcelead.com/crms.html' style='text-decoration:none; color:#333;' title='CRMS'>CRMS</a> | <a target='blank' href='http://www.sourcelead.com/pms.html' style='text-decoration:none; color:#333;' title='PMS'>PMS</a> | <a target='blank' href='http://www.sourcelead.com/hrms.html' style='text-decoration:none; color:#333;' title='HRMS'>HRMS</a> | <a target='blank' href='http://www.sourcelead.com/ims.html' style='text-decoration:none; color:#333;' title='IMS'>IMS</a> | <a target='blank' href='http://www.sourcelead.com/search-jobs.html' style='text-decoration:none; color:#333;' title='Job Search'>Job Search</a> | <a target='blank' href='http://www.sourcelead.com/market-place.html' style='text-decoration:none; color:#333;' title='Marketplace'>Marketplace</a> <br> <a target='blank' href='http://www.sourcelead.com/terms-of-use.html' style='text-decoration:none; color:#333;' title='Terms of Use'>Terms of Use</a> | <a target='blank' href='http://www.sourcelead.com/privacy-policy.html' style='text-decoration:none; color:#333;' title='Privacy policy'>Privacy policy</a></p>");
				                  tempHTML.append("</td>");
				            tempHTML.append("</tr>");
					       }
					            tempHTML.append("<tr><td><p style='padding:5px 0px 5px 0px; margin:0px 0px; font-weight:400; font-size:11px; text-align:center;'>If you do not wish to receive any further communication, please  <a target='_blank' href='"+url+"/ws/unsubscribe' style='color:#a21e23;'>unsubscribe</a></p></td></tr>");
					       tempHTML.append("</table>");
					 tempHTML.append("</td>");
			    tempHTML.append("</tr>");
      tempHTML.append("</table>"); 
      
        
	tempHTML.append("</td>");
 tempHTML.append("</tr>");
tempHTML.append("</table>");

if(tabs != null) {
	tempHTML.append("<div style='display: none;'><tabIdDiv>").append(tabs.getId().toString()).append("</tabIdDiv>").append("</div>");
}else {
	tempHTML.append("<div style='display: none;'><tabIdDiv>").append("</tabIdDiv>").append("</div>");
}

		 return tempHTML.toString();
	}
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
}
