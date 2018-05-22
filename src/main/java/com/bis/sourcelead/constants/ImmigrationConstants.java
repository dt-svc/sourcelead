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
	public static final String IMMIGRATION_UPLOAD ="immigrationUpload";
	
	public static final String[] IMPORT_IMMIGRATION_EXCEL_HEADERS = { "Emp ID", "Beneficiary First Name","Beneficiary Last name",
			 "Beneficiary Email*", "Experience", "SSN", "I-94 Number",	"I-20 SEVIS Number If Student", "EAD Number If Student", "DOB*",	
			 "Marital status*", "Petition Type*", "Template*", "Date of arrival", "Open on*", "Status*","Visa No.", "Visa Start Date", "Visa End Date","Law Firm/Client*",
			 "Name", "Attorney/Contact email*", "Receipt no.", "Location", "LCA Start Date", "LCA End Date","LCA Number", "LCA Rate", "LCA Location","LCA Title",
			 "Previous LCA Start Date", "Previous LCA end Date","Previous LCA Number","Previous LCA Rate","Previous LCA Title","Previous LCA Location"};
	
	@SuppressWarnings("serial")
	public static final Map HEADERS_NAMES_MAP = new HashMap<Integer, String>(){{
		put(1,"empId");put(2,"beneficiaryFirstName");put(3,"beneficiaryLastName");put(4,"beneficiaryEmail");put(5,"experience");put(6,"ssn");put(7,"i94Number");
		put(8,"i20Number");put(9,"eadNumber");put(10,"dateOfBirth");put(11,"maritalStatus");put(12,"petitionType");
		put(13,"template");put(14,"dateOfArrival");put(15,"openOn");put(16,"status");put(17,"visaNo");put(18,"visaStartDate");
		put(19,"visaEndDate");put(20,"company");put(21,"companyName");put(22,"companyContactEmail");put(23,"receiptNo");put(24,"location");put(25,"lcaStartDate");put(26,"lcaEndDate");put(27,"lcaNumber");
		put(28,"lcaRate");put(29,"lcaLocation");put(30,"lcaTitle");put(31,"prevLcaStartDate");put(32,"prevLcaEndDate");put(33,"prevLcaNumber");put(34,"pevLcaRate");put(35,"prevLcaTitle");put(36,"prevLcaLocation");
	}};
	public static final Map HEADERS_FIELD_NAMES_MAP = new HashMap<Integer, String>(){{
		put(4,"Beneficiary Email");
		put(10,"DOB");
		put(11,"Marital status");
		put(12,"Petition Type");
		put(13,"Template");
		put(15,"Open on");
		put(16,"Status");
		put(20,"Company");
		put(22,"Attorney/Contact email Email");
	}};
	
	public static List<String> IMMIGRATION_INPROGRESS_STATUS_LIST= Arrays.asList("Filled", "Reviewed & Send to attorney", "Request for modifications","Reviewed & Filed","RFE","RFE Received","Motion to Re-Open","RFE Approved","RFE Denied","RFE Responded");

	public static String IMMIGRATION_APPROVED_STATUS = "Approved";
	
	public static String IMMIGRATION_INPROGRESS_STATUS = "inProgress";
	
	public static String IMMIGRATION_OTHERS_STATUS = "others";
	
	public static String IMMIGRATION_DRAFT_STATUS = "Draft";
	
	public static String IMMIGRATION_REQUEST_TO_FILL_STATUS = "Request to fill";
	
	public static String IMMIGRATION_REJECTED_STATUS = "Rejected";
	
	public static List<String> IMMIGRATION_OTHER_STATUS_LIST = Arrays.asList("Withdrawn","Cancelled","Revoked","Denied");
}