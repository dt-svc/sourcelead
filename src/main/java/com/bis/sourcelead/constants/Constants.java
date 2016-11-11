package com.bis.sourcelead.constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * 
 * @author Rajashekar
 * @date 6th July 2016
 *
 */

public class Constants {
	public static final String[] FILE_HEADER_MAPPING = { "Employee ID", "First Name", "Last Name", "Email id",
			"Phone Number", "Rate", "Rate Per", "Date of Join (dd/mm/yyyy)", "Role", "Invoice Frequency", "Day Hours" };

	public static final String EMPLOYEE_ID = "Employee ID";
	public static final String FIRST_NAME = "First Name";
	public static final String LAST_NAME = "Last Name";
	public static final String EMAIL_ID = "Email id";
	public static final String PHONE_NUMBER = "Phone Number";
	public static final String RATE = "Rate";
	public static final String RATEPER = "Rate Per";
	public static final String DATEOFJOIN = "Date of Join (mm/dd/yyyy)";
	public static final String ROLE = "Role*";
	public static final String INVOICE_FREQUENCY = "Invoice Frequency";
	public static final String DAY_HOURS = "Day Hours";
	public static final String FAILED_REASON = "Reason";
	public static final String EMAIL = "Email*";
	public static final String CLIENT_NAME = "Client Name*";
	public static final String PHONE = "Phone";
	public static final String STATUS = "Status";
	public static final String OWNER = "Owner";
	public static final String WEBSITE = "Website";
	public static final String COUNTRY = "Country";
	public static final String SATATE = "State";
	public static final String CITY = "City";
	public static final String LEVEL = "Level";
	public static final String VENDOR_NAME = "Vendor Name*";

	public static final Integer RECORD_ACTIVE = 1;
	public static final Integer RECORD_INACTIVE = 0;
	
	
	public static final Integer LEVEL1 = 1;
	   
	public static transient DateFormat DMY = new SimpleDateFormat("dd-MM-yyyy");
	public static transient DateFormat DF = new SimpleDateFormat("yyyy/MM/dd");
	public static DateFormat DF_STD = new SimpleDateFormat("yyyy/MM/dd");
	public static DateFormat DF_YMD = new SimpleDateFormat("yyyy-MM-dd");
	public static DateFormat DF_STD_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
	
	public static DateFormat DF_DMY = new SimpleDateFormat("dd-MM-yyyy");
	
	public static String MAIL_TEAM_MEMBERS = "Mail To Team Members";
	public static String INVITE_USER = "Invite User";
	public static String INVITE_CLIENT = "Invite Client";
	public static String INVITE_VENDOR = "Invite Vendor";
	
	public static String ACCEPT_CLIENT_INVITATION = "Accept Client Invitation";
	public static String ACCEPT_VENDOR_INVITATION = "Accept Vendor Invitation";
	public static String OFFER_MAIL_TO_VENDORS="Client to Vendor Acceptence";
	public static String APPLICANT_INTERVIEW = "Applicant Interview";
	public static String INTERVIEWER_INVITATION = "Interviewer Invitation";
	
	public static String SUBMIT_TO_CLIENT = "Submit To Client";
	public static String ADD_OFFER_MAIL ="Forward the Mail";

	public static String VENDOR_INTERVIEW = "Vendors Interview";
	public static String VENDOR_INVITATION = "Vendor Invitation";
	
	public static String EDIT_CLIENTCONTACT = "Edit SpocContact";
	
	public static String SHORTLIST_APPLICANTS = "ShortList Applicants";
	
	public static String MAIL_PROJECT_MANAGER_TIMESHEET = "Mail To Manager At Timesheet Submission";
	public static String MAIL_TEAM_MEMBERS_APPROVED_TIMESHEET = "Timesheet Approved By Approver";
	public static String MAIL_TEAM_MEMBERS_REJECTED_TIMESHEET = "Timesheet Rejected By Approver";
	
	public static String ENTITY_TYPE_EMAIL = "Email";
	public static String ENTITY_TYPE_NOTIFICATION = "Notification";
	
	public static TimeZone TIMEZONE_CENTRAL = TimeZone.getDefault();
	
	public static String loginUserAsAdmin =":userId IN (select userRole.user.id from UserRole userRole where userRole.roles = '1' and userRole.orgLocations.id = ts.projectMembers.projects.orgLocationsId.id)";
	
	public static String loginUserAsAdminWithMaxHeirarchyLevel ="(:userId IN (select userRole.user.id from UserRole userRole where userRole.roles = '1' and userRole.orgLocations.id = pm.projects.orgLocationsId.id) and  sah.hierarchyId.level IN(select MAX(pah.level) from ProjectApproverHierarchy pah where pah.projectId.id = pm.projects.id))";
	
	/** projectMember  constants**/
	public static String MEMBER_ACTIVE = "1";
	public static String  MEMBER_IN_ACTIVE= "0";
	
	public static int CANDIDATE_ALL = 2;
	public static int CANDIDATE_ACTIVE = 1;
	public static int  CANDIDATE_IN_ACTIVE= 0;
	
	/** Candidate Skill  constants**/
	public static int CANDIDATE_SKILL_ACTIVE = 1;
	public static int CANDIDATE_SKILL_IN_ACTIVE= 0;
	
	public static String ACTIVE = "Active";

	/** projectMemberHistory constants**/
	public static String PROJECT_MEMBER_HISTORY_OPEN = "OPEN";
	public static String  PROJECT_MEMBER_HISTORY_CLOSED= "CLOSED";
	public static String REQUIREMENT_EMAIL_NOTIFICATION = "Approval Mechanism";
	public static String REQUIREMENT_SEQUENTIAL_EMAIL_NOTIFICATION = "Approval Mechanism Sequential";
	public static String APPROVED_EMAIL_NOTIFICATION = "Approved Requirement";
	public static String REJECTED_EMAIL_NOTIFICATION ="Rejected Mail About The Requirement";
	public static String RELEASEMECHANISM_EMAIL_NOTIFICATION="Release Mechanism";
	public static String MAILREQUIREMENTTOMANAGER = "Requirement Posting";
	public static String ASSIGNTEAMMEMBERSFORREQUIREMENT = "AssignToTeamMembers";
	public static String  WEEK_TIME_SHEET_SUBMISSION= "Week Time Sheet Submission";
	public static String TIMESHEET_EMAIL_NOTIFICATION = "Timesheet Scheduler";

	public static String YES = "Y";
	public static String NO  = "N";
	public static String WAIT  = "W";
	public static String REJECT  = "R";
	public static String TEMP    = "T";
	public static transient DateFormat STDF = new SimpleDateFormat("MM-dd-yyyy");
	public static transient DateFormat DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd");
	public static DateFormat DF_TM = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
	
	public static int MAX_RESULTS_FIVE = 5;
	public static int MAX_RESULTS_TEN = 10;
	public static int MAX_RESULTS_TWENTY = 20;
	public static int MAX_RESULTS_THIRTY = 30;
	public static int MAX_RESULTS_FIFTY = 50;
	public static int PAGES_INCREMENT = 10;
	public static int PAGES_DECREMENT = -10;
	
	public static int MODULE_ATS = 2;
	public static int MODULE_VMS = 3;
	
	public static int TAB_VENDOR = 9;
	public static int TAB_CLIENT = 7;
	
	public static  String Dashboard = "Dashboard";
	public static  String Users = "Users";
	public static  String Projects = "Projects";
	public static  String Clients = "Clients";
	public static  String TimeSheet = "TimeSheet";
	public static  String TimeSheetApproval = "TimeSheetApproval";
	public static  String OrgSettings = "OrgSettings";
	
	public static  String Requirement = "Requirement";
	public static  String Vendors = "Vendors";
	public static  String Candidates = "Candidates";
	
	public static String SOURCELEAD_ORGCODE = "sourcelead.com";
	public static String SOURCELEAD_LOCATIONCODE_IN = "in.sourcelead.com";
	
	/**Constants values for unique emails and notification **/
	
	public static String ORG_REGISTRATION = "Org Registration";
	public static String FORGOT_PASSWORD = "Forgot Password";
	public static String UPDATE_PASSWORD = "Update Password";
	public static String MAIL_PROJECT_MANAGER = "Mail To Project Manager";
	


	public static String CONTACTS = "CONTACTS";
	public static String VENDORSTYPE = "VENDORS";
	/**Constants values for notifications **/
	
	public static String NOTIFICATION_UNREAD = "unread";
	public static String NOTIFICATION_READ = "read";
	public static String  NOTIFICATION_DELETED= "0";
	public static String  NOTIFICATION_NOT_DELETED= "1";
	
	
	public static String  TIME_SHEET_SUBMISSION= "Time Sheet Submission";
	


	/** for user roles**/
	public static String ROLE_ADMIN = "ROLE_ADMIN";
	public static String ROLE_APPROVER = "ROLE_APPROVER";
	public static String ROLE_USER = "ROLE_USER";
	public static String SOURCELEAD_ADMIN = "ROLE_SOURCELEAD_ADMIN";
	
	public static String HR_MANAGER = "ROLE_HR_MANAGER";
	public static String ACCOUNT_MANAGER = "ROLE_ACCOUNT_MANAGER";
	public static String SALES_MANAGER = "ROLE_SALES_MANAGER";
	public static String RECRUITER = "ROLE_RECRUITER";


	
	
	
	public static String[] RATEPER_LIST = {"Daily","Hourly","Weekly","Bi-Weekly","Semi-Monthly","Monthly","Yearly"};
	
	public static String[] ROLE_LIST = {"Admin","Employee","Project Manager","Team Lead"};
	
	public static String[] INVOICE_FREQUENCY_LIST = {"Weekly","Bi-Weekly","Semi-Monthly","Monthly"};
	

	/**  For LookUpData (Eductions,Skills) **/
	
	public static String EDUCATIONS = "Educations";
	public static String SKILLS = "Skills";
	
	public static String ADMIN = "Admin";
	

	public static String MAIL_SENT = "Sent";
	public static String MAIL_NEW = "New";
	public static String MAIL_RESEND = "Resend";
	public static String MAIL_ACCEPTED = "Accepted";
	public static String MAIL_REJECTED = "Rejected";
	
	public static String REQUIREMENT_STATUS = "Draft";
	
	public static String TEAM_ACTIVE = "1";
	public static String TEAM_IN_ACTIVE = "0";

	public static String BI_WEEKLY = "Bi-Weekly";
	public static String SEMI_MONTHLY = "Semi-Monthly";
	public static String MONTHLY = "Monthly";
	
	
	public static String[] ROLE_DROPDOWN = {"Admin","Project Manager","HR Manager","Recruiter"};
	public static String[] STATUS_DROPDOWN = {"Active","In-Active","New","Negotiation","Contracted"};
	public static String[] LEVEL_DROPDOWN = {"Level-1","Level-2","Level-3","Level-4","Level-5"};
	public static String[] USER_EXCEL_HEADERS = { "Employee Id", "First Name", "Last Name", "Email-id", "Phone Number",
			"Date Of Join", "Rate", "Location", "Role", "Manager", "status" };
	public static String[] CANDIDATE_EXCEL_HEADERS = {"Firstname","Last name","Email id","Phone Number","Location","Designation","Education","Experience","Skills","Rate"};
    public static String[] REQUIREMENT_EXCEL_HEADERS = {"Requirement id","Requirement Title","Experience","Rate","Location","Skills","Manager","Priority","Application Form Name","Client Name","Client Contact Name","Client Contact Email"};
}
