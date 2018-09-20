package com.bis.sourcelead.constants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * 
 * @author Srinivas Sabbu.
 * @date 6th July 2016
 *
 */
public class ImmigrationConstants {

	public static final String EMPLOYMENT_TYPE = "EmploymentType";
	public static final String PETITION_TYPE = "PetitionType";
	public static final String PETITION_STATUS = "USCISStatus";
	public static final String PETITION_STATUS_DRAFT = "Draft";
	public static final String PETITION_STATUS_FILLED = "Filled";
	public static final String EMPLOYMENT_LEVEL = "EmploymentLevel";
	public static final String ORG_NAME_SELECTED_ORG1 = "Org1";
	public static final String ORG_NAME_SELECTED_ORG2 = "Org2";
	public static final String FILE_UPLOADED = "Uploaded";
	public static final String PETITION_STATUS_REQUEST_TO_FILL = "Request to fill";
	public static final String SEND_REQUEST = "sendRequest";
	public static final Integer STATUS_ACTIVE = 1;
	public static final Integer STATUS_IN_ACTIVE = 0;
	
	public static final String LCA_STATUS_EXPIRED= "Expired";
	
	public static final String LCA_STATUS_EXPIRED_IN_DAYS= "Expired In Days";
	
	public static final String IMMIGRATION_UPLOAD ="immigrationUpload";
	public static final String LCA_UPLOAD ="lcaUpload";
	
	public static final String SOC_TYPE = "SocDetails";
	
	public static final String EMAIL_TO_LCA_MANAGER_EXPIRED = "EmailToLcaManagerExpired";
	
	
	
	public static final String[] IMPORT_IMMIGRATION_EXCEL_HEADERS = {"Beneficiary First Name","Beneficiary Last Name",
            "Beneficiary Email", "Experience", "SSN", "I-94 Number",    "I-20 SEVIS Number If Student", "EAD Number If Student", "DOB",    
            "Marital status", "Petition Type", "Template", "Date of arrival", "Open on", "Status","Passport No","Passport Country","Passport Start Date","Passport End Date","Visa No.","Visa Type", "Visa Country","Visa Start Date", "Visa End Date","Company",
            "Attorney/Contact email", "Receipt No.", "Location", "LCA Start Date", "LCA End Date","LCA Number", "LCA Rate Currency","LCA Rate","LCA Rate Per", "LCA Location","LCA Title","Job ID",
            "Previous LCA Start Date", "Previous LCA end Date","Previous LCA Number","Previous LCA Title","Previous LCA Location"};
   
   public static final String[] IMPORT_DOWNLOAD_IMMIGRATION_EXCEL_HEADERS = {"Beneficiary First Name*","Beneficiary Last Name*",
            "Beneficiary Email*", "Experience", "SSN", "I-94 Number",    "I-20 SEVIS Number If Student", "EAD Number If Student", "DOB*",    
            "Marital status", "Petition Type*", "Template*", "Date of arrival", "Open on*", "Status*","Passport No","Passport Country","Passport Start Date","Passport End Date","Visa No.","Visa Type", "Visa Country","Visa Start Date", "Visa End Date","Company*",
            "Attorney/Contact email*", "Receipt No.", "Location", "LCA Start Date", "LCA End Date","LCA Number", "LCA Rate Currency","LCA Rate","LCA Rate Per", "LCA Location","LCA Title","Job ID*",
            "Previous LCA Start Date", "Previous LCA end Date","Previous LCA Number","Previous LCA Title","Previous LCA Location"};
   
   public static final String[] IMPORT_LCA_EXCEL_HEADERS = {"LCA Number","LCA Title",
           "No. of positions", "Current Client Location (Address 1, City, State, Country, PIN)", "Current Prim Vendor Location (Address 1, City, State, Country, PIN)",
           	"Current Vendor Location (Address 1, City, State, Country, PIN)", "SOC Code", "SOC Occupation Title","Job Title","Wage Level","Prevailing Wage Currency",
           "Prevailing Wage Rate", "Prevailing Wage rate per", "LCA Start Date", "LCA End Date", "LCA Status","Status"};
   
   public static final String[] IMPORT_DOWNLOAD_LCA_EXCEL_HEADERS = {"LCA Number*(I-000-00000-000000)","LCA Title*", "No. of positions*",
            "Current Client Location* (Address 1, City, State, Country, PIN)", "Current Prim Vendor Location (Address 1, City, State, Country, PIN)",
           	"Current Vendor Location (Address 1, City, State, Country, PIN)", "SOC Code*", "SOC Occupation Title*" ,"Job Title*","Wage Level*","Prevailing Wage Currency*",
           "Prevailing Wage Rate*", "Prevailing Wage rate per*", "LCA Start Date*", "LCA End Date*", "LCA Status*","Status*"};
   
   @SuppressWarnings("serial")
   public static final Map HEADERS_NAMES_MAP = new HashMap<Integer, String>(){{
       put(1,"beneficiaryFirstName");put(2,"beneficiaryLastName");put(3,"beneficiaryEmail");put(4,"experience");put(5,"ssn");put(6,"i94Number");put(7,"i20Number");
       put(8,"eadNumber");put(9,"dateOfBirth");put(10,"maritalStatus");put(11,"petitionType");put(12,"template");
       put(13,"dateOfArrival");put(14,"openOn");put(15,"status");put(16,"passportNo");put(17,"passportCountry");put(18,"passportStartDate");
       put(19,"passportEndDate");put(20,"visaNo");put(21,"visaType"); put(22,"visaCountry");put(23,"visaStartDate");put(24,"visaEndDate");put(25,"company");put(26,"companyContactEmail");put(27,"receiptNo");
       put(28,"benficiarylocation");put(29,"lcaStartDate");put(30,"lcaEndDate");put(31,"lcaNumber");put(32,"lcaRateCurrency");put(33,"lcaRate");put(34,"lcaRatePer");put(35,"lcaLocation");put(36,"lcaTitle");put(37,"immigrationJobId");put(38,"prevLcaStartDate");put(39,"prevLcaEndDate");
       put(40,"prevLcaNumber");put(41,"prevLcaTitle");put(42,"prevLcaLocation");
   }};
   
   @SuppressWarnings("serial")
   public static final Map LCA_HEADERS_NAMES_MAP = new HashMap<Integer, String>(){{
       put(1,"newLcaNumber");put(2,"newLcaTitle");put(3,"noOfPos");put(4,"currClientLocation");put(5,"curPrimrVenodorLocation");
       put(6,"curVenodorLocation");put(7,"socCode");put(8,"socTitle");put(9,"socJobTitle");put(10,"wageLevel");put(11,"prevailWageCurr");
       put(12,"prevailWageRate");put(13,"prevailWageRatePer");put(14,"newLcaStartDate");put(15,"newLcaEndDate");put(16,"lcaStatus");put(17,"newStatus");
   }};
   
   public static final String[] LCA_LIST_EXCEL_HEADERS = { "LCA Number", "LCA Title", "Number Of Positions","Current Client Location", "Current Primary Venodor Location", "Current Venodor Location", "Soc Code",
			"SOC Title","Job Title", "Wage Level", "Prevailing  Wage Currency", "Prevailing  Wage Rate", "Prevailing  Wage Rate Per", "LCA Start Date", "LCA End Date", "LCA Status", "Status"};
   
   public static final Map HEADERS_FIELD_NAMES_MAP = new HashMap<Integer, String>(){{
       put(1,"Beneficiary First Name");
       put(2,"Beneficiary Last Name");
       put(3,"Beneficiary Email");
       put(9,"DOB");
       put(10,"Marital status");
       put(11,"Petition Type");
       put(12,"Template");
       put(14,"Open on");
       put(15,"Status");
       put(25,"Company");
       put(26,"Attorney/Contact email Email");
       put(37,"Job ID");
   }};
   public static final Map LCA_HEADERS_FIELD_NAMES_MAP = new HashMap<Integer, String>(){{
   	   put(1,"LCA Number");
       put(2,"LCA Title");
       put(3,"Number Of Positions");
       put(4,"Current Client Location");
       put(7,"SOC Code");
       put(8,"SOC Occupation Title");
       put(9,"JobTitle");
       put(10,"Wage Level");
       put(11,"Prevailing Wage Currency");
       put(12,"Prevailing Wage Rate");
       put(13,"Prevailing Wage rate per");
       put(14,"LCA Start Date");
       put(15,"LCA End Date");
       put(16,"LCA Status");
       put(17,"Status");
   }};
	public static List<String> IMMIGRATION_INPROGRESS_STATUS_LIST= Arrays.asList("Filled", "Reviewed & Send to attorney", "Request for modifications","Reviewed & Filed","Motion to Re-Open");

	public static String IMMIGRATION_APPROVED_STATUS = "Approved";
	
	public static String IMMIGRATION_INPROGRESS_STATUS = "inProgress";
	
	public static String IMMIGRATION_OTHERS_STATUS = "others";
	
	public static String IMMIGRATION_DRAFT_STATUS = "Draft";
	
	public static String IMMIGRATION_REQUEST_TO_FILL_STATUS = "Request to fill";
	
	public static String IMMIGRATION_REJECTED_STATUS = "Rejected";
	
	public static List<String> IMMIGRATION_OTHER_STATUS_LIST = Arrays.asList("Withdrawn","Cancelled","Revoked");
	
	public static String IMMIGRATION_DENIED_STATUS = "Denied";
	
	public static String IMMIGRATION_RFE_STATUS = "rfe";
	
	public static List<String> IMMIGRATION_RFE_STATUS_LIST = Arrays.asList("RFE","RFE Received","RFE Approved","RFE Denied","RFE Responded");
	
	public static List<String> MARRAIAGE_STATUS_LIST = Arrays.asList("Single","Married","","Divorced","Separated");
	
	public static List<String> LCA_EXCEL_REFLECTION_FILEDS_LIST = Arrays.asList("lcaNo","lcaTitle", "noOfPositions","location","socCode","socOccupationTitle","jobTitle","wagesLevel","privilingWageCurrency","prevailingWage",
			                                                                    "privilingWageRateType","startDate","endDate","lcaStatus","status");
}