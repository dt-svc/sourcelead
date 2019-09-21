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
	
	public static final String IS_CURRENT = "Y";
	
	public static final String IS_CURRENT_NO = "N";
	
	public static final String PASSPORT_EXPIRES_IN_DAYS_MAIL = "PassportExpiresInDaysMail";
	
	public static final String PASSPORT_EXPIRED_MAIL = "PassportExpiredMail";
	
	public static final String VISA_EXPIRES_IN_DAYS_MAIL = "VisaExpiresInDaysMail";
	
	public static final String VISA_EXPIRED_MAIL = "VisaExpiredMail";
	
	public static final String PASSPORT_EXPIRES_IN_DAYS_MAIL_TO_REPORTINGMANAGER = "PassportExpiresInDaysMailToReportingManager";
	
	public static final String PASSPORT_EXPIRED_MAIL_TO_REPORTINGMANAGER = "PassportExpiredMailToReportingManager";
	
	public static final String VISA_EXPIRES_IN_DAYS_MAIL_TO_REPORTINGMANAGER = "VisaExpiresInDaysMailToReportingManager";
	
	public static final String VISA_EXPIRED_MAIL_TO_REPORTINGMANAGER = "VisaExpiredMailToReportingManager";
	
	public static final String[] IMPORT_IMMIGRATION_EXCEL_HEADERS = {"Beneficiary First Name","Beneficiary Last Name",
            "Beneficiary Email", "Phone Number", "Experience", "SSN", "I-94 Number",    "I-20 SEVIS Number If Student", "EAD Number If Student", "DOB",    
            "Marital status", "Petition Type", "Template", "Date of arrival", "Open on", "Status","Passport No","Passport Country","Passport Start Date","Passport End Date","Visa No.","Visa Type", "Visa Country","Visa Start Date", "Visa End Date","Company",
            "Attorney/Contact email", "Receipt No.", "Location", "LCA Start Date", "LCA End Date","LCA Number", "LCA Rate Currency","LCA Rate","LCA Rate Per", "LCA Location","LCA Title","Job ID",
            "Previous LCA Start Date", "Previous LCA end Date","Previous LCA Number","Previous LCA Title","Previous LCA Location"};
   
   public static final String[] IMPORT_DOWNLOAD_IMMIGRATION_EXCEL_HEADERS = {"Beneficiary First Name*","Beneficiary Last Name*",
            "Beneficiary Email*", "Phone Number", "Experience", "SSN", "I-94 Number",    "I-20 SEVIS Number If Student", "EAD Number If Student", "DOB*",    
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
       put(1,"beneficiaryFirstName");put(2,"beneficiaryLastName");put(3,"beneficiaryEmail");put(4,"beneficiaryPhone");put(5,"experience");put(6,"ssn");put(7,"i94Number");put(8,"i20Number");
       put(9,"eadNumber");put(10,"dateOfBirth");put(11,"maritalStatus");put(12,"petitionType");put(13,"template");
       put(14,"dateOfArrival");put(15,"openOn");put(16,"status");put(17,"passportNo");put(18,"passportCountry");put(19,"passportStartDate");
       put(20,"passportEndDate");put(21,"visaNo");put(22,"visaType"); put(23,"visaCountry");put(24,"visaStartDate");put(25,"visaEndDate");put(26,"company");put(27,"companyContactEmail");put(28,"receiptNo");
       put(29,"benficiarylocation");put(30,"lcaStartDate");put(31,"lcaEndDate");put(32,"lcaNumber");put(33,"lcaRateCurrency");put(34,"lcaRate");put(35,"lcaRatePer");put(36,"lcaLocation");put(37,"lcaTitle");put(38,"immigrationJobId");put(39,"prevLcaStartDate");put(40,"prevLcaEndDate");
       put(41,"prevLcaNumber");put(42,"prevLcaTitle");put(43,"prevLcaLocation");
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
	public static final String LCA_DELETED = "Lca deleted";
	
	public static final String LCA_RE_ACTIVE = "Lca ReActive";
	
	public static final String LCA_IMPORTED = "Lca import";
}