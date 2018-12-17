package com.bis.sourcelead.util;

public class DefaultEmployeeVerificationTemplateUtil {

	public static String getDefaultTemplates(String templateName){
		
		String template = "";
		StringBuilder sb = new StringBuilder();
		sb.append("<HTML>                                                                         ");
		sb.append("<HEAD>                                                                         ");
		sb.append("	<META HTTP-EQUIV='CONTENT-TYPE' CONTENT='text/html; charset=utf-8'>           ");
		sb.append("	<TITLE></TITLE>                                                               ");
		sb.append("	<META NAME='GENERATOR' CONTENT='LibreOffice 4.1.6.2 (Linux)'>                 ");
		sb.append("	<META NAME='AUTHOR' CONTENT='BIS_IND'>                                        ");
		sb.append("	<META NAME='CREATED' CONTENT='20170629;65500000000000'>                       ");
		sb.append("	<META NAME='CHANGEDBY' CONTENT='BIS_IND'>                                     ");
		sb.append("	<META NAME='CHANGED' CONTENT='20170629;65500000000000'>                       ");
		sb.append("	<META NAME='AppVersion' CONTENT='14.0000'>                                    ");
		sb.append("	<META NAME='DocSecurity' CONTENT='0'>                                         ");
		sb.append("	<META NAME='HyperlinksChanged' CONTENT='false'>                               ");
		sb.append("	<META NAME='LinksUpToDate' CONTENT='false'>                                   ");
		sb.append("	<META NAME='ScaleCrop' CONTENT='false'>                                       ");
		sb.append("	<META NAME='ShareDoc' CONTENT='false'>                                        ");
		sb.append("	<STYLE TYPE='text/css'>                                                       ");
		sb.append("	<!--                                                                          ");
		sb.append("		@page { margin: 1in }                                                     ");
		sb.append("		P { margin-bottom: 0.08in; direction: ltr; widows: 2; orphans: 2 }        ");
		sb.append("	-->                                                                           ");
		sb.append("	</STYLE>                                                                      ");
		sb.append("</HEAD>                                                                        ");
		sb.append("<BODY LANG='en-IN' DIR='LTR'>                                                  ");
		sb.append("<P STYLE='margin-bottom: 0.14in'>                							  ");
		sb.append("[Issued_Date] 											  					  ");
		sb.append("<P STYLE='margin-bottom: 0.14in'><BR>                                      	  ");
		sb.append("	To Whom It May Concern: 												      ");
		sb.append("<P STYLE='margin-bottom: 0.14in'><BR>                                      	  ");
		sb.append("	Dear Sir/Madam: 															  ");
		sb.append("<P STYLE='margin-bottom: 0.14in'><BR>                                      	  ");
		sb.append("	This is to certify that [Associate_Name] was a [Employment_Type] employee with"); 
		sb.append(" our organization, [Company_Name],<BR> for the period [Start_Date] to [End_Date]");
		sb.append(" as [Designation_Role].														  ");
		sb.append("<P STYLE='margin-bottom: 0.14in'><BR>                                      	  ");
		sb.append("If you have any questions or concerns regarding this employment, please do not ");
		sb.append("hesitate to call [Company_Contact] or email us at [Company_Email]. 			  ");
		sb.append("<P STYLE='margin-bottom: 0.14in'><BR>                                      	  ");
		sb.append("<P STYLE='margin-bottom: 0.14in'><BR>                                      	  ");
		sb.append("Sincerely,<BR>  															  	  ");
		sb.append("[Signature]<BR>															  	  ");
		sb.append("[Signatory_Name]<BR>  													  	  ");
		sb.append("[Signatory_Designation]<BR>												  	  ");
		sb.append("[Company_Name]<BR>     													  	  ");
		sb.append("</P>                                                                           ");
		sb.append("<P STYLE='margin-bottom: 0.14in'><BR>                                      	  ");
		sb.append("</P>                                                                           ");
		sb.append("<P STYLE='margin-bottom: 0.14in'>                                              ");
		sb.append("</P>                                                                           ");
		sb.append("<P STYLE='margin-bottom: 0.14in'><BR>                                      	  ");
		sb.append("</P>                                                                           ");
		sb.append("<P STYLE='margin-bottom: 0.14in'><BR>                                      	  ");
		sb.append("</P>                                                                           ");
		sb.append("<P STYLE='margin-bottom: 0.14in'>                                              ");
		sb.append("</P>                                                                           ");
		sb.append("<P STYLE='margin-bottom: 0.14in'><BR>                                      	  ");
		sb.append("</P>                                                                           ");
		sb.append("</BODY>                                                                        ");
		sb.append("</HTML>");
		template = sb.toString();
		
		return template;
		
	}
}
