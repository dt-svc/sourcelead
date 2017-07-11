package com.bis.sourcelead.util;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;


/**
 * This class for build email template
 * @author Prasad salina
 *
 */
public class EmailTemplateHTMLConversionUtil implements Serializable {
	
	private static final long serialVersionUID = -3317550653234933840L;
	
	public static String convertBodyOfEmailTemplateToHTML(String body , String signature ,String callToActionText , String ButtonText , String url, EmailMessage emailMessage, Org org,OrgLocations orgLocations) {	
		 
		StringBuilder tempHTML = new StringBuilder();
		/*
		 * need to replace image location
		 * src='"+url+"/resources/images/mail-logo.png'
		 * 
		 * src='http://vannamnew.xp3.biz/html/images/mail-logo.png'
		 */
		
		
		tempHTML.append("<table width='100%' bgcolor='#F9F9F9' cellpadding='0' cellspacing='0' style='background-color:#F9F9F9; font-family: arial;'>");
				tempHTML.append("<tr>");
						tempHTML.append("<td>");
								tempHTML.append("<table width='100%' align='center' cellpadding='0' cellspacing='0' style='padding:10px 0px 10px 0px; background-color:#10a0e2;'>");
										tempHTML.append("<tr>");
												tempHTML.append("<td>");
												  tempHTML.append("<table width='600' align='center'>");
												      tempHTML.append("<tr>");
												      
												      //This condition for org image     
												      if(org != null && org.getId() != null){
												    	  	if(StringUtils.isNotEmpty(org.getFileContentType())){
												    	  	  tempHTML.append("<td style='text-align: center;'><img src='"+url+"/displayOrgImage/"+org.getOrgCode()+"' width='auto' style='padding: 5px 20px; max-height:55px; background-color:#fff; margin: 5px auto; display:block;'></td>");
												    	  	}else{
												    	  		 tempHTML.append("<td class='text-logo'><span class='tbldata-dotdot'  style='font-size: 28px; color: #fff; width: 600px; display: block; text-overflow: ellipsis; overflow: hidden; white-space: nowrap; padding: 10px 0px; text-align:center; margin:0 auto; font-weight: 600;'>"+orgLocations.getOrgLocationName()+"</span></td>");
												    	  	}
												      }else{
												    	  tempHTML.append("<td style='text-align: center;'><img src='"+url+"/resources/images/SourceLead-logo-white.png' width='300px' height='66px'></td>");
												      }
												           
										             tempHTML.append("</tr>");
								                 tempHTML.append("</table>");
						                      tempHTML.append("</td>");
				                         tempHTML.append("</tr>");
		                          tempHTML.append("</table> ");
		
		tempHTML.append("<table width='900'  bgcolor='#F9F9F9' align='center' style='background-color:#F9F9F9; margin:0px auto 12px auto; padding:25px 0px 10px 20px'  cellpadding='0' cellspacing='0'>");
				tempHTML.append("<tr>");
						tempHTML.append("<td>");
					  	      tempHTML.append("<table width='900' style='margin:30px auto; font-family:'arial'; align='center' cellpadding='0' cellspacing='0' >");
						           tempHTML.append("<tr>");
						                 tempHTML.append("<td>");
						                         tempHTML.append("<div style='font-size:16px; color:#333333; line-height: 20px;'>");
		                                         tempHTML.append(body);
		                                         tempHTML.append("</div>");
		                                         
		                                         if(!isNullOrEmpty(signature)){
									                    tempHTML.append("<div style='font-size:16px; font-weight:600; color:#333333; line-height:20px;'><br/><br/>");
														tempHTML.append(signature);
													    tempHTML.append(" </div>");		
												 }
					                            								
					                     tempHTML.append("</td>");
					          tempHTML.append("</tr>");
					      tempHTML.append("</table>");
					  tempHTML.append(" </td>");
			    tempHTML.append("</tr>");
		tempHTML.append("</table>");
		
		tempHTML.append("<table width='100%' bgcolor='#ddd' align='center' cellpadding='0' cellspacing='0' style='padding:10px 0px 10px 0px; background-color:#ddd; font-family:'Segoe UI';'>");
			  tempHTML.append("<tr>");
					  tempHTML.append("<td>");
					       tempHTML.append("<table bgcolor='#ddd'  width='900' align='center' style='background-color:#ddd; padding:15px 0px;'>");
					           tempHTML.append("<tr>");
					                 tempHTML.append("<td>");
					                   tempHTML.append("<p style='color:#214b90; text-transform: uppercase; padding:5px 0px; margin:0px 0px; font-size:25px; font-weight:700;'>get started!</p>");
					                   tempHTML.append("<p style='padding:0px 0px 15px 0px; margin:0px 0px; font-weight:600;'>For more information about SourceLead, go to <a target='blank' href='http://www.sourcelead.com/' style='text-decoration:none; color:#fff; display:inline-block;background-color:#e74c51; padding:6px 25px 8px; border-radius:15px; margin:0px 0px 0px 20px;'>sourcelead.com</a></p>");
					                   tempHTML.append("<p style='padding:0px 0px 15px 0px; margin:0px 0px; font-weight:600;'>3535 Victory Group Way,Building 4 Frisco,TX 75034, USA,Phone : (214) 295-5535,FAX : (972) 559-3641 </p>");
					                 tempHTML.append("</td>");
					            tempHTML.append("</tr>");
					         
					            tempHTML.append("<tr>");    
					                  tempHTML.append("<td>");
					                       tempHTML.append("<p style='text-align:center; padding:10px 0px; margin:0px 0px; font-size:14px; font-weight:600; border-top:1px solid #333;'>");
					                       tempHTML.append("<a target='blank' href='http://www.sourcelead.com/about-us.html' style='text-decoration:none; color:#333;' title='About SourceLead'>About SourceLead</a> | <a target='blank' href='http://www.sourcelead.com/contact.html' style='text-decoration:none; color:#333;' title='Contact Us'>Contact Us</a> | <a target='blank' href='http://www.sourcelead.com/ats.html' style='text-decoration:none; color:#333;' title='ATS'>ATS</a> | <a target='blank' href='http://www.sourcelead.com/vms.html' style='text-decoration:none; color:#333;' title='VMS'>VMS</a> | <a target='blank' href='http://www.sourcelead.com/timesheet.html' style='text-decoration:none; color:#333;' title='Time Management'>Time Management</a> | <a target='blank' href='http://www.sourcelead.com' style='text-decoration:none; color:#333;' title='Terms of Use'>Terms of Use</a></p>");
					                  tempHTML.append("</td>");
					            tempHTML.append("</tr>");
					       tempHTML.append("</table>");
					 tempHTML.append("</td>");
			    tempHTML.append("</tr>");
        tempHTML.append("</table>"); 
        
	tempHTML.append("</td>");
 tempHTML.append("</tr>");
tempHTML.append("</table>");

		 return tempHTML.toString();
	}
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
}
