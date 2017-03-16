package com.bis.sourcelead.util;

import java.util.HashMap;
import java.util.Map;

public class PrimarySkillUtil {

	
	public static String returnMyskill(String[] sk){

		
		String[] javaSkills = {"Java","J2SE","J2EE","Spring","Struts","Hibernate","JBoss","Apache Tomcat","Core Java","JavaScript","JSP","Servlets","Apache Tomcat","SDLC","WebDriver","Eclipse","Angular JS"};
		
		String[] sapSkills ={"SAP","SAP ABAP/IDOCS","SAP Basis","SAP BW/BI","SAP CRM","SAP EP","SAP FICO/COPA"};
		
		String[] dotNetSkills = {".Net",".NET(C#&ASP.NET)",".NETFramework","ASP.NetAJAX","ASP.NetandMVCFramework","ASP.NETFramework","C#.NET","SQL Server","Visual Studio","Entitiy Framework","Azure"};
		
		String[] testingSkills = {"Software Testing","IntegrationTesting","PerformanceTesting","UnitTesting","QTP","Selenium","Jmeter","Automation Testing","Manual Testing"};
		
		String[] mainFrameSkills = {"Mainframe","MainframeDB2"};
		
		String[] webDeveloperSkills = {"HTML","CSS","JSON","Javascript","Bootstrap","Ajax","SVG","Canvas","JQuery"};
		
		HashMap<String, String[]> hm = new HashMap<String, String[]>();
		hm.put("Java Developer", javaSkills);
		hm.put("SAP Developer", sapSkills);
		hm.put("DotNet Developer", dotNetSkills);
		hm.put("Test Engineer", testingSkills);
		hm.put("MainFrame Developer", mainFrameSkills);
		hm.put("UI Developer", webDeveloperSkills);


		for (Map.Entry<String, String[]> entry : hm.entrySet()) {
		String key = entry.getKey();
		String[] value = entry.getValue();
		int i =0;
		for(String val : value){
			for(String checkSkill : sk){
				if(val.equalsIgnoreCase(checkSkill)){
					i++;
				}
			}
		
		}
		if(i>=3)
			return key;
		}
		return null;
		}
}
