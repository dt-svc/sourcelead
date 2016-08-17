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
	public static final String ROLE = "Role";
	public static final String INVOICE_FREQUENCY = "Invoice Frequency";
	public static final String DAY_HOURS = "Day Hours";
	public static final String FAILED_REASON = "Reason";
	   
	public static transient DateFormat DMY = new SimpleDateFormat("dd-MM-yyyy");
	public static transient DateFormat DF = new SimpleDateFormat("yyyy/MM/dd");
	public static DateFormat DF_STD = new SimpleDateFormat("yyyy/MM/dd");
	public static DateFormat DF_YMD = new SimpleDateFormat("yyyy-MM-dd");
	public static DateFormat DF_STD_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
	
	public static DateFormat DF_DMY = new SimpleDateFormat("dd-MM-yyyy");
	
	public static String MAIL_TEAM_MEMBERS = "Mail To Team Members";
	public static String INVITE_USER = "Invite User";
	public static String MAIL_PROJECT_MANAGER_TIMESHEET = "Mail To Manager At Timesheet Submission";
	public static String MAIL_TEAM_MEMBERS_APPROVED_TIMESHEET = "Timesheet Approved By Approver";
	public static String MAIL_TEAM_MEMBERS_REJECTED_TIMESHEET = "Timesheet Rejected By Approver";
	
	public static String ENTITY_TYPE_EMAIL = "Email";
	public static String ENTITY_TYPE_NOTIFICATION = "Notification";
	
	public static TimeZone TIMEZONE_CENTRAL = TimeZone.getDefault();
	
	/** projectMember  constants**/
	public static String MEMBER_ACTIVE = "ACTIVE";
	public static String  MEMBER_IN_ACTIVE= "INACTIVE";
	

	/** projectMemberHistory constants**/
	public static String PROJECT_MEMBER_HISTORY_OPEN = "OPEN";
	public static String  PROJECT_MEMBER_HISTORY_CLOSED= "CLOSED";

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
	
	public static  String Dashboard = "Dashboard";
	public static  String Users = "Users";
	public static  String Projects = "Projects";
	public static  String Clients = "Clients";
	public static  String TimeSheet = "TimeSheet";
	public static  String TimeSheetApproval = "TimeSheetApproval";
	public static  String OrgSettings = "OrgSettings";
	
	public static  String Jobs = "Jobs";
	public static  String Vendors = "Vendors";
	public static  String Candidates = "Candidates";
	
	public static String SOURCELEAD_ORGCODE = "sourcelead.com";
	public static String SOURCELEAD_LOCATIONCODE_IN = "in.sourcelead.com";
	
	/**Constants values for unique emails and notification **/
	
	public static String ORG_REGISTRATION = "Org Registration";
	public static String FORGOT_PASSWORD = "Forgot Password";
	public static String UPDATE_PASSWORD = "Update Password";
	public static String MAIL_PROJECT_MANAGER = "Mail To Project Manager";
	
	
	/**Constants values for notifications **/
	
	public static String NOTIFICATION_UNREAD = "unread";
	public static String NOTIFICATION_READ = "read";
	public static String  NOTIFICATION_DELETED= "0";
	public static String  NOTIFICATION_NOT_DELETED= "1";
	
	
	public static String  TIME_SHEET_SUBMISSION= "Time Sheet Submission";
	

	public static String[] RATEPER_LIST = {"Daily","Hourly","Weekly","Bi-Weekly","Semi-Monthly","Monthly","Yearly"};
	
	public static String[] ROLE_LIST = {"Admin","Employee","Approver"};
	
	public static String[] INVOICE_FREQUENCY_LIST = {"Weekly","Bi-Weekly","Semi-Monthly","Monthly"};
	

	/** for user roles**/
	public static String ROLE_ADMIN = "ROLE_ADMIN";
	public static String ROLE_APPROVER = "ROLE_APPROVER";
	public static String ROLE_USER = "ROLE_USER";
	public static String SOURCELEAD_ADMIN = "SOURCELEAD_ADMIN";

	
}
