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
	public static final String[] FILE_HEADER_MAPPING = { "Employee ID", "First Name", "Last Name",
			"Email id", "Phone Number", "Rate", "Rate Per", "Date of Join (dd/mm/yyyy)", "Role",
			"Invoice Frequency", "Day Hours" };
	public static final String[] AMEX_FILE_HEADER_MAPPING = { "Date", "Description", "Amount" };
	public static final String[] COMERICA_FILE_HEADER_MAPPING = { "Date", "No.", "Description",
			"Debit", "Credit" };
	public static final String[] CHASE_FILE_HEADER_MAPPING = { "Details", "Posting Date",
			"Description", "Amount", "Type", "Balance", "Check Or Slip#" };
	public static final String[] FONT_FAMILY = { "Arial", "Comisc", "Comic Sans MS", "Courier New",
			"Georgia", "Monotype", "Tahoma", "Times New Roman", "Trebuchet MS", "Verdana",
			"Impace" };
	public static final String[] FONT_SIZE = { "8px", "9px", "10px", "11px", "12px", "13px", "14px",
			"15px", "16px", "17px", "18px", "19px", "20px", "21px", "221px", "23px", "24px" };
	public static final String EMPLOYEE_ID = "Employee ID";
	public static final String FIRST_NAME = "First Name*";
	public static final String LAST_NAME = "Last Name";
	public static final String EMAIL_ID = "Email id*";
	public static final String EMAIL_ID_BULK_IMPORT = "Email*";
	public static final String PHONE_NUMBER_BULK_IMPORT = "Phone";
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
	public static final String OWNER = "Owner(Email)";
	public static final String WEBSITE = "Website";
	public static final String COUNTRY = "Country*";
	public static final String SATATE = "State";
	public static final String CITY = "City";
	public static final String LEVEL = "Tier";
	public static final String VENDOR_NAME = "Vendor Name*";
	public static final String JOB_OTHER = "Other";
	public static final String CLIENT_ADDRESS = "ClientAddress";
	public static final String BILLING_ADDRESS = "BillingAddress";
	public static final String PAYMENT_TERMS = "PaymentTerms";
	public static final String INVOICE_RECEIVABLE = "Receivable";
	public static final String INVOICE_PAYABLES = "Payables";
	public static final String Client_SOW = "Client SOW";
	public static final String Vendor_SOW = "Vendor SOW";
	public static final String country = "Country";
	public static final String TASK_BILLABLE = "Billable";
	public static final String TASK_NON_BILLABLE = "Non-Billable";
	public static final Integer RECORD_ACTIVE = 1;
	public static final Integer RECORD_INACTIVE = 0;
	public static final Integer LEVEL1 = 1;
	public static final Integer LEVEL2 = 2;
	public static transient DateFormat DMY = new SimpleDateFormat("dd-MM-yyyy");
	public static transient DateFormat DF = new SimpleDateFormat("yyyy/MM/dd");
	public static DateFormat DF_STD = new SimpleDateFormat("yyyy/MM/dd");
	public static DateFormat DF_YMD = new SimpleDateFormat("yyyy-MM-dd");
	public static DateFormat DF_STD_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
	public static transient DateFormat MY = new SimpleDateFormat("MMMM-yyyy");
	public static transient DateFormat Y = new SimpleDateFormat("yyyy");
	public static transient DateFormat M = new SimpleDateFormat("MMMM");
	public static transient DateFormat DF_MM = new SimpleDateFormat("MM");
	public static DateFormat DF_DMY = new SimpleDateFormat("dd-MM-yyyy");
	public static DateFormat DF_STD_DMY = new SimpleDateFormat("dd/MM/yyyy");
	public static DateFormat SHORT_DF_STD_DM = new SimpleDateFormat("ddMMM");
	public static DateFormat SHORT_DF_MM_DD = new SimpleDateFormat("MMM-dd");
	public static final DateFormat TZ_DF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	/*
	 * public static final DateFormat TZ_DF_TS = new SimpleDateFormat (
	 * "yyyy-MM-dd HH:mm:ss");
	 */
	public static String ONBOARDING_FORMS = "Forms";
	public static String ONBOARDING_POLICIES = "Policies";
	public static String ONBOARDING_CONTRACTS = "Contracts";
	public static String ONBOARDING_SUPPORTED = "Supported";
	public static String MAIL_TEAM_MEMBERS = "Mail To Team Members";
	public static String INVITE_USER = "Invite User";
	public static String INVITE_CLIENT = "Invite Client";
	public static String INVITE_VENDOR = "Invite Vendor";
	public static String ACCEPT_CLIENT_INVITATION = "Accept Client Invitation";
	public static String ACCEPT_VENDOR_INVITATION = "Accept Vendor Invitation";
	public static String ACCEPT_CLIENT = "Accept Client";
	public static String ACCEPT_VENDOR = "Accept Vendor";
	public static String REJECT_CLIENT = "Reject Client";
	public static String REJECT_VENDOR = "Reject Vendor";
	public static String SHORTLISTMAILFROMCLIENT_TO_VENDOR = "ShortlistMailFromClient_To_Vendor";
	public static String OFFER_MAIL_TO_VENDORS = "Client to Vendor Acceptence";
	public static String APPLICANT_INTERVIEW = "Applicant Interview";
	public static String INTERVIEWER_INVITATION = "Interviewer Invitation";
	public static String INTERVIEWER_TEMPLATE = "Interviewer Template";
	public static String INTERVIEW_RESCHEDULEMAIL_TO_APPLICANT = "interviewRescheduleMailToApplicant";
	public static String OFFER_ACCEPTED = "Offer Accepted";
	public static String OFFER_ACCEPTED_NOTIFICATION = "Offer Accepted Notification";
	public static String OFFER_REJECTED = "Offer Rejected";
	public static String OFFER_REJECTED_NOTIFICATION = "Offer Rejected Notification";
	public static String HOT_LIST_CAMPAIGN = "HotListCampaign";
	public static String EMAIL_TO_USERS = "EmailToUsers";
	public static String EMAIL_CAMPAIGN = "EmailCampaign";
	public static String CLIENTORVENDORMAIL = "ClientOrVendorMail";
	public static String CUSTOM_USER_EMAIL = "customUserEmail";
	public static String INVOICE_CLIENT_RECEIVABLE_MAIL = "InvoiceClientAcceptEmail";
	public static String INVOICE_CLIENT_PAYABLE_MAIL = "InvoiceClientPayableEmail";
	public static String SUBMIT_TO_CLIENT = "Submit To Client";
	public static String ADD_OFFER_MAIL = "Forward the Mail";
	public static String NOTIFICATION_TO_CLIENT = "Notification To Client";
	public static String VENDOR_INTERVIEW = "Vendors Interview";
	public static String VENDOR_INVITATION = "Vendor Invitation";
	public static String EDIT_CLIENTCONTACT = "Edit SpocContact";
	public static String CLIENT_OWNER_UPDATEMAIL = "clientOwnerUpdateMail";
	public static String SHORTLIST_APPLICANTS = "ShortList Applicants";
	public static String SHORTLIST_NOTIFICATION_MANAGER = "Notification To Manager";
	public static String MAIL_PROJECT_MANAGER_TIMESHEET = "Mail To Manager At Timesheet Submission";
	public static String MAIL_TEAM_MEMBERS_APPROVED_TIMESHEET = "Timesheet Approved By Approver";
	public static String MAIL_TEAM_MEMBERS_REJECTED_TIMESHEET = "Timesheet Rejected By Approver";
	public static String ENTITY_TYPE_EMAIL = "Email";
	public static String ENTITY_TYPE_NOTIFICATION = "Notification";
	public static String ENTITY_TYPE_TEMPLATE = "Notification";
	public static String CLIENT_INTERVIEW = "CLIENT_INTERVIEW";
	public static String INTERVIEW = "INTERVIEW";
	public static String MAIL_REOPEN_TIMESHEET = "ReopenTimeSheet";
	public static String MAIL_ACCEPT_REOPEN_TIMESHEET = "AcceptReopenTimeSheet";
	public static String MAIL_REJECT_REOPEN_TIMESHEET = "RejectReopenTimeSheet";
	public static String MAIL_BOX_SENT = "Sent";
	public static String MAIL_BOX_INBOX = "Inbox";
	public static String TASK_STATUS_OPEN = "Open";
	public static String TASK_STATUS_CLOSE = "Closed";
	public static String TASK_STATUS_MYTASK = "myTask";
	public static String TASK_STATUS_ASSIGNTASK = "assignedTasks";
	public static String TASK_STATUS_CLOSEDTASK = "closedTasks";
	public static String DEFAULT_INTERVIEW_FEEDBACK_TEMAPLATE = "Sourcelead Inerview Feedback Template";
	public static String TASK_MECHANISM_EMAIL = "Task Mechanism";
	public static String TASK_MECHANISM_NOTIFICATION = "Task Mechanism";
	public static String MODIFIED_REQUIREMENT_STATUS_EMAIL = "ModifiedRequirementStatusMailtoVendorContact";
	public static String MODIFIED_REQUIREMENT_STATUS_NOTIFICATION = "ModifiedRequirementStatusNotificationtoVendorContact";
	public static String HOTLIST_ALERT_EMAIL = "HotListAlertMail";
	public static String HOTLIST_ALERT_NOTIFICATION = "HotListAlertNotification";
	public static long SOURCELEAD_ADMIN_USERID = 1;
	public static TimeZone TIMEZONE_CENTRAL = TimeZone.getDefault();
	public static String loginUserAsAdmin = ":userId IN (select userRole.user.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.orgLocations.id = ts.projectMembers.projects.orgLocationsId.id)";
	public static String loginUserAsAdminWithMaxHeirarchyLevel = "(:userId IN (select userRole.user.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.orgLocations.id = pm.projects.orgLocationsId.id) and  sah.hierarchyId.level IN(select MAX(pah.level) from ProjectApproverHierarchy pah where pah.projectId.id = pm.projects.id))";
	public static String loginUserAsAdminForProjects = ":userId IN (select userRole.user.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.orgLocations.id = projects.orgLocationsId.id)";
	/** projectMember constants **/
	public static String MEMBER_ACTIVE = "1";
	public static String MEMBER_IN_ACTIVE = "0";
	public static int CANDIDATE_ALL = 2;
	public static int CANDIDATE_ACTIVE = 1;
	public static int CANDIDATE_IN_ACTIVE = 0;
	/** Candidate Skill constants **/
	public static int CANDIDATE_SKILL_ACTIVE = 1;
	public static int CANDIDATE_SKILL_IN_ACTIVE = 0;
	public static String ACTIVE = "Active";
	public static String INACTIVE = "In-Active";
	/** projectMemberHistory constants **/
	public static String PROJECT_MEMBER_HISTORY_OPEN = "OPEN";
	public static String PROJECT_MEMBER_HISTORY_CLOSED = "CLOSED";
	public static String REQUIREMENT_EMAIL_NOTIFICATION = "Approval Mechanism";
	public static String APPROVER_UPDATED_CONTRACT = "approverUpdatedContract";
	public static String APPROVE_CONTRACT = "Approve Contract";
	public static String REJECT_CONTRACT = "Reject Contract";
	public static String CONTRACT_MAIL_TO_OWNER = "Contract Mail To Owner";
	public static String APPLICANT_ACCEPTMAILTO_INTERVIEW_CREATOR = "ApplicantAcceptMailToInterviewCreator";
	public static String APPLICANT_REJECT_MAILTO_INTERVIEW_CREATOR = "ApplicantRejectMailToInterviewCreator";
	public static String APPLICANT_TENTATIVE_MAILTO_INTERVIEWCREATOR = "ApplicantTentativeMailToInterviewCreator";
	public static String VENDOR_APPLICANT_REJECTMAIL_TO_INTERVIEWCREATOR = "VendorApplicantRejectMailToInterviewCreator";
	public static String VENDOR_APPLICANT_ACCEPT_MAILTO_INTERVIEWCREATOR = "VendorApplicantAcceptMailToInterviewCreator";
	public static String VENDOR_APPLICANT_TENTATIVE_MAILTO_INTERVIEWCREATOR = "VendorApplicantTentativeMailToInterviewCreator";
	public static final long USERID = 1;
	public static String PASSWORD_RESET_MAIL_TO_USER = "passwordResetMailToUser";
	public static String REQUIREMENT_SEQUENTIAL_EMAIL_NOTIFICATION = "Approval Mechanism Sequential";
	public static String APPROVED_EMAIL_NOTIFICATION = "Approved Requirement";
	public static String REJECTED_EMAIL_NOTIFICATION = "Rejected Mail About The Requirement";
	public static String RELEASEMECHANISM_EMAIL_NOTIFICATION = "Release Mechanism";
	public static String MAILREQUIREMENTTOMANAGER = "Requirement Posting";
	public static String ASSIGNTEAMMEMBERSFORREQUIREMENT = "AssignToTeamMembers";
	public static String WEEK_TIME_SHEET_SUBMISSION = "Week Time Sheet Submission";
	public static String TIMESHEET_EMAIL_NOTIFICATION = "Timesheet Scheduler";
	public static String TIMESHEET_MANAGER_NOTIFICATION = "Timesheet Scheduler Level1 Manager";
	public static String UPDATEDREQUIREMENTNOTIFICATIONTOVENDOR = "updatedRequirementNotificationToVendor";
	public static String UPDATED_USER_ROLE_EMAIL = "UserRoleChanged";
	public static String INVOICE_ACCEPT_MAIL_VENDOR_EMAIL = "InvoiceAcceptMailtoVendor";
	public static String INVOICE_REJECT_MAIL_VENDOR_EMAIL = "InvoiceRejectMailtoVendor";
	public static String INVOICE_ACCEPT_MAIL_CLIENT_EMAIL = "InvoiceAcceptMailtoClient";
	public static String UPDATED_USER_ROLE_NOTIFICATION = "UserRoleChanged";
	public static String UPDATED_REQUIREMENT_EMAIL_TO_MANAGER = "updatedRequirementEmailToManager";
	public static String UPDATED_REQUIREMENT_NOTIFICATION_TO_MANAGER = "updatedRequirementNotificationToManager";
	public static String UPDATED_FEEDBACK_NOTIFICATIONTO_REQUIREMENTOWNER = "updatedFeedbackNotificationToRequirementOwner";
	public static String OFFERREJECT_EMAIL = "Vendor Offer Reject";
	public static String OFFERACCEPT_EMAIL = "VendorAcceptedOffer";
	public static String APPLICANT_RECEIVE_MAILABOUT_REQUIREMENT_EMAIL = "ApplicantReceiveMailAboutRequirement";
	public static String NOTIFY_APPLICANT_MAILABOUT_REQUIREMENT = "notifyApplicantMailAboutRequirement";
	public static String APPLICANT_RECEIVE_MAILABOUT_REQUIREMENT_NOTIFICATION = "ApplicantReceiveMailAboutRequirement";
	public static String APPLICANT_ACCEPT_MAILABOUT_REQUIREMENT_EMAIL_NOTIFICATION = "ApplicantAcceptRequirement";
	public static String APPLICANT_REJECT_MAILABOUT_REQUIREMENT_EMAIL_NOTIFICATION = "ApplicantRejectanceRequirement";
	public static String APPLICANT_NEGOTIATE_REQUIREMENT = "ApplicantNegotiateRequirement";
	public static String USER_RATE_NEGOTIATION = "UserRateNegotiation";
	public static String CLIENT_RATE_NEGOTIATION = "ClientRateNegotiation";
	public static String VENDOR_RATE_NEGOTIATION = "VendorRateNegotiation";
	public static String USER_ACCEPT_RATE_NEGOTIATION = "UserAcceptRateNegotiation";
	public static String USER_REJECT_RATE_NEGOTIATION = "UserRejectRateNegotiation";
	public static String CLIENT_ACCEPT_RATE_NEGOTIATION = "ClientAcceptRateNegotiation";
	public static String VENDOR_ACCEPT_RATE_NEGOTIATION = "VendorAcceptRateNegotiation";
	public static String CLIENT_REJECT_RATE_NEGOTIATION = "ClientRejectRateNegotiation";
	public static String VENDOR_REJECT_RATE_NEGOTIATION = "VendorRejectRateNegotiation";
	public static String ROLE_NAME_PROFESSIONAL = "ROLE_CROWDSOURCING_PROFESSIONAL";
	public static String ROLE_NAME_INDEPENDENT_RECRUITER = "ROLE_CROWDSOURCING_RECRUITER";
	public static String PROFESSIONAL = "Professional";
	public static String INDIVIDUAL_RECRUITER = "Recruiter";
	public static String INVITE_PROFESSIONAL = "Professional Invitation";
	public static String INVITE_INDIVIDUAL_RECRUITER = "Individual Recruiter Invitation";
	
	
	
	public static String USER_NEW_ACTION_ASSIGNED="UserNewActionAssigned";

	public static String USER_NEW_ACTION_ASSIGNED_As_A_Approver="UserNewActionAssignedAsaApprover";

	public static String USER_NEW_ACTION_ASSIGNED_As_A_REPORTING_MANAGER="UserNewActionAssignedAsaReportingManager";

	public static String USER_NEW_ACTION_ASSIGNED_As_A_TEAM_OWNER="UserNewActionAssignedAsaTeamOwnerUser";

	public static String USER_NEW_ACTION_ASSIGNED_As_A_PROJECT_APROVER="UserNewActionAssignedAsaProjectApproverUser";
	
	public static String USER_NEW_ACTION_ASSIGNED_NOTIFICATION ="UserNewActionAssignedNotification";
	public static String APPLICANT_ACCEPT_RATE = "ApplicantAcceptRate";
	public static String APPLIED_TO_REQUIREMENT = "Applied To Requirement";
	public static String REFER_REQUIREMENT_TO_PROFESSIONAL = "Refer Requirment To Professional";
	public static String ADD_NEW_USER_CONTACT = "Add New User Contact";
	public static String CROWD_SOURCING_SOURCE_SELF = "SELF";
	public static String EMPLOYEE_TERMINATION = "Employee Termination";
	public static String EMPLOYEE_TERMINATION_MAIL_TO_MANAGER = "Employee Termination Mail To Manager";
	public static String EMPLOYEE_REASSIGN = "Employee Reassign";
	public static String SocialInviter = "SocialInviter";
	public static final DateFormat TMS = new SimpleDateFormat("hh:mm a");
	public static final DateFormat TMS_FORMAT = new SimpleDateFormat("HH:mm aa");
	public static String INTERVIEWER_INVITATION_REDIFINED = "Interviewer InvitationRedifined";
	public static String YES = "Y";
	public static String NO = "N";
	public static String WAIT = "W";
	public static String REJECT = "R";
	public static String REJECTED = "REJECTED";
	public static String TEMP = "T";
	public static transient DateFormat STDF = new SimpleDateFormat("MM-dd-yyyy");
	public static transient DateFormat DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd");
	public static DateFormat DF_TM = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
	public static int MAX_RESULTS_FIVE = 5;
	public static int MAX_RESULTS_TEN = 10;
	public static int MAX_RESULTS_TWENTY = 20;
	public static int MAX_RESULTS_THIRTY = 30;
	public static int MAX_RESULTS_FIFTY = 50;
	public static int MAX_RESULTS_200 = 200;
	public static int PAGES_INCREMENT = 10;
	public static int PAGES_DECREMENT = -10;
	public static int MODULE_ATS = 2;
	public static int MODULE_VMS = 3;
	public static int MODULE_TIMESHEET = 4;
	public static String MODULE_ATS_STR = "2";
	public static String MODULE_VMS_STR = "3";
	public static String MODULE_TIMESHEET_STR = "4";
	public static int TAB_VENDOR = 9;
	public static int TAB_CLIENT = 7;
	public static int TAB_MY_TIMESHEET = 5;
	public static int TAB_TIMESHEET_APPROVAL = 6;
	public static int TAB_REQUIREMENT = 8;
	public static int TAB_USER = 2;
	public static int TAB_PROJECTS = 3;
	public static String Dashboard = "Dashboard";
	public static String Users = "Users";
	public static String Projects = "Projects";
	public static String Clients = "Clients";
	public static String TimeSheet = "TimeSheet";
	public static String TimeSheetApproval = "TimeSheetApproval";
	public static String OrgSettings = "OrgSettings";
	public static String BackOffice = "BackOffice";
	public static String Finance = "Finance";
	public static String AssetManagement = "AssetManagement";
	public static String Contract = "Contract";
	public static String Profile = "Profile";
	public static String List_by_status = "List by status";
	public static String TimeSheet_Approval = "Timesheet Approval";
	public static String Requirement = "Requirement";
	public static String Vendors = "Vendors";
	public static String Candidates = "Candidates";
	public static String Associates = "Associates";
	public static String OnAndOffboard = "OnAndOffboard";
	public static String Receivable = "Receivable";
	public static String Payables = "Payables";
	public static String SOURCELEAD_ORGCODE = "sourcelead.com";
	public static String SOURCELEAD_LOCATIONCODE_IN = "in.sourcelead.com";
	/** Constants values for unique emails and notification **/
	public static String ORG_REGISTRATION = "Org Registration";
	public static String FORGOT_PASSWORD = "Forgot Password";
	public static String UPDATE_PASSWORD = "Update Password";
	public static String MAIL_PROJECT_MANAGER = "Mail To Project Manager";
	public static String EDITPROJECT_MAIL_MANAGER = "Editprojecttomanager";
	public static String EDITPROJECT_MAIL_TEAMMEMBER = "Editprojecttoteammember";
	public static String REJECTMAILFROMCLIENT_TO_VENDOR = "RejectMailFromClient_To_Vendor";
	public static String CONTACTS = "CONTACTS";
	public static String VENDORSTYPE = "VENDORS";
	/** Constants values for notifications **/
	public static String NOTIFICATION_UNREAD = "unread";
	public static String NOTIFICATION_READ = "read";
	public static String NOTIFICATION_DELETED = "0";
	public static String NOTIFICATION_NOT_DELETED = "1";
	public static String TIME_SHEET_SUBMISSION = "Time Sheet Submission";
	/** for user roles **/
	public static String ROLE_ADMIN = "ROLE_ADMIN";
	public static String ROLE_APPROVER = "ROLE_APPROVER";
	public static String ROLE_USER = "ROLE_USER";
	public static String SOURCELEAD_ADMIN = "ROLE_SOURCELEAD_ADMIN";
	public static String HR_MANAGER = "ROLE_HR_MANAGER";
	public static String ACCOUNT_MANAGER = "ROLE_ACCOUNT_MANAGER";
	public static String SALES_MANAGER = "ROLE_SALES_MANAGER";
	public static String RECRUITER = "ROLE_RECRUITER";
	public static String ROLE_MANAGER = "ROLE_MANAGER";
	public static String Applicant_Acceptance_By_Manager_Of_Requirement = "ApplicantAcceptanceByManagerOfRequirement";
	public static String Applicant_Will_Receive_Mail_About_The_Acceptance_Of_Requirement_By_Manager = "ApplicantWillReceiveMailAboutTheAcceptanceOfRequirementByManager";
	public static String Recruiter_Will_Receive_Mail_About_The_Acceptance_Of_Requirement_By_Manager = "RecruiterWillReceiveMailAboutTheAcceptanceOfRequirementByManager";
	public static String[] RATEPER_LIST = { "Daily", "Hourly", "Weekly", "Bi-Weekly",
			"Semi-Monthly", "Monthly", "Yearly" };
	public static String[] ROLE_LIST = { "Admin", "Employee", "Project Manager", "Team Lead" };
	public static String[] INVOICE_FREQUENCY_LIST = { "Weekly", "Bi-Weekly", "Semi-Monthly",
			"Monthly" };
	/** For LookUpData (Eductions,Skills) **/
	public static String EDUCATIONS = "Educations";
	public static String SKILLS = "Skills";
	public static String DOCUMENT_TYPE = "Document Type";
	public static String ADMIN = "Admin";
	public static String MAIL_SENT = "Sent";
	public static String MAIL_NEW = "New";
	public static String MAIL_RESEND = "Resend";
	public static String MAIL_ACCEPTED = "Accepted";
	public static String MAIL_REJECTED = "Rejected";
	public static String OFFERMADE = "OFFERMADE";
	public static String CLIENT_OFFERMADE = "CLIENT_OFFERMADE";
	public static String REFER_JOB_APPLIED = "Applied";
	public static String REQUIREMENT_STATUS = "Draft";
	public static String TEAM_ACTIVE = "1";
	public static String TEAM_IN_ACTIVE = "0";
	public static Integer DEFAULT_CATEGORY = 0;
	public static String WEEKLY = "Weekly";
	public static String BI_WEEKLY = "Bi-Weekly";
	public static String SEMI_MONTHLY = "Semi-Monthly";
	public static String MONTHLY = "Monthly";
	public static String[] ROLE_DROPDOWN = { "Admin", "Project Manager", "HR Manager",
			"Recruiter" };
	public static String[] STATUS_DROPDOWN = { "Active", "In-Active", "New", "Negotiation",
			"Contracted" };
	public static String[] COUNTRY_DROPDOWN = { "India", "Australia", "France", "Singapore",
			"United Kingdom", "United States" };
	public static String[] LEVEL_DROPDOWN = { "Tier-1", "Tier-2", "Tier-3", "Tier-4", "Tier-5" };
	public static String[] USER_EXCEL_HEADERS = { "Employee Id", "First Name", "Last Name",
			"Email-id", "Phone Number", "Date Of Join", "Rate", "Location", "Role", "Manager",
			"status" };
	public static String[] CANDIDATE_EXCEL_HEADERS = { "Firstname", "Last name", "Email id",
			"Phone Number", "Location", "Designation", "Education", "Experience", "Skills",
			"Rate" };
	public static String[] REQUIREMENT_EXCEL_HEADERS = { "Requirement id", "Requirement Title",
			"Experience", "Rate", "Location", "Skills", "Manager", "Priority",
			"Application Form Name", "Client Name", "Client Contact Name", "Client Contact Email",
			"Status" };
	public static String[] REQUIREMENT_PIPELINE_EXCEL_HEADERS = { "Name", "Email", "PhoneNumber",
			"Resume", "Applied On", "Rate", "Source", "Score", "Experience", "Status",
			"Submitted On", "Submitted By" };
	public static String[] REQUIREMENT_PIPELINE_REQUEST_SENT_HEADERS = { "Name", "Email",
			"PhoneNumber", "Resume", "Applied On", "Rate", "Source", "Score", "Experience",
			"Status" };
	public static String[] REQUIREMENT_PIPELINE_SUBMIT_TO_CLIENT_EXCEL_HEADERS = { "Name", "Email",
			"PhoneNumber", "Resume", "Applied On", "Applicant Rate", "Submission Rate", "Source",
			"Score", "Experience", "Status", "Submitted On", "Submitted By", "Client Contact" };
	public static String[] REQUIREMENT_PIPELINE_SHORTLISTED_EXCEL_HEADERS = { "Name", "Email",
			"PhoneNumber", "Resume", "Applied On", "Rate", "Source", "Score", "Experience",
			"Status", "ShortListed By", "ShortListed On" };
	public static String[] REQUIREMENT_PIPELINE_INTERVIEW_EXCEL_HEADERS = { "Name", "Email",
			"Phone No", "Experience", "DateOfInterview", "TimeOfInterview", "Location" };
	public static String[] REQUIREMENT_PIPELINE_OFFERMADE_EXCEL_HEADERS = { "Name", "Email",
			"Phone No", "Experience", "OfferDateJoining", "Position", "Offer Rate", "Offerd Date",
			"Attachment" };
	public static String[] REQUIREMENT_PIPELINE_REJECTED_EXCEL_HEADERS = { "Name", "Email",
			"PhoneNumber", "Resume", "Applied On", "Rate", "Source", "Rejected On", "Rejected By",
			"Rejected Stage", "Status" };
	public static String[] TIMESHEET_EXCEL_HEADERS = { "Tasks", "Monday", "Tuesday", "Wednesday",
			"Thursday", "Friday", "Saturday", "Sunday", "Total" };
	public static String[] TIMESHEET_EXCEL_HEADERS2 = { "Tasks", "Mon", "Tue", "Wed", "Thu", "Fri",
			"Sat", "Sun", "Total" };
	public static String[] TIMESHEET_EXCEL_HEADERS3 = { "Status" };
	public static String[] TIMESHEET_APPROVAL_MECHANISM_HEADER = { "Employee Name", "Email id",
			"Phone Number", "Client", "projectName", "Total", "Stage", "approved By" };
	public static String[] CLIENT_EXCEL_HEADERS = { "Client Name", "Website", "Location", "Tiers",
			"Number of Contacts" };
	public static String[] CLIENT_CONTACTS_EXCEL_HEADERS = { "Client Name", "Name", "Emailid",
			"Phone Number", "Owner", "Role", "Status" };
	public static String[] VENDOR_EXCEL_HEADERS = { "VendorName", "Website", "Location", "Level",
			"Number of Contacts" };
	public static String[] VENDOR_CONTACTS_EXCEL_HEADERS = { "Vendor Name", "Name", "Emailid",
			"Phone Number", "Owner", "Role", "Status" };
	public static String[] SHORTLIST_CANDIDATE_FROM_CLIENT_TO_VENDOR_HEADER = { "CandidateName",
			"PhoneNumber", "Work Experience", "status" };
	public static String SHORTLIST_CANDIDATE_COUNT = "shortlist_candidate :";
	public static String VENDOR = "Vendor";
	public static String CLIENT = "Client";
	public static String ELK_PROJECT_INDEX = "projects";
	public static String ELK_PROJECT_TYPE = "project";
	public static String ELK_CANDIDATE_INDEX = "candidates";
	public static String ELK_USER_INDEX = "users";
	public static String ELK_CANDIDATE_TYPE = "candidate";
	public static String ELK_USER_TYPE = "user";
	public static String ELK_REQUIREMENT_INDEX = "requirement";
	public static String ELK_REQUIREMENT_TYPE = "requirement";
	public static String ELK_CANDIDATE_ID_PREFIX = "CND";
	public static String ELK_CLIENTVENDOR_INDEX = "clientvendor";
	public static String ELK_CLIENTVENDOR_TYPE = "clientvendors";
	public static String ELK_CLIENTVENDOR_CONTACT_INDEX = "clientvendorcontacts";
	public static String ELK_CLIENTVENDOR_CONTACT_TYPE = "clientvendorcontacts";
	public static String ELK_CAMPAIGN_INDEX = "campaigns";
	public static String ELK_CAMPAIGN_TYPE = "campaign";
	public static String ELK_CAMPAIGN_ID_PREFIX = "CAMPAIGN";
	public static String CANDIDATE = "Candidate";
	public static String CONSULTANT = "Consultant";
	public static String ASSOCIATE = "Associate";
	public static String MAIL_TRASH = "Trash";
	public static String MAIL_DELETE = "Deleted";
	public static String MAIL_REQUESTED = "Requested";
	public static String[] PROJECT_ASSIGNED_EMPLOYEES = { "Emp Name", "Emp Email", "Start Date",
			"End Date", "Approved Hrs", "Rejected Hrs", "Wiating for Approval", "Saved Hrs" };
	// public static String[] VENDOR_CONTACTS_HEADERS = {"Vendor Name*","First
	// Name*","Last Name","Email*","Phone","Status","Owner(Email)*"};
	// public static String[] CLIENT_CONTACTS_HEADERS = {"Client Name*","First
	// Name*","Last Name","Email*","Phone","Status","Owner(Email)*"};
	public static String[] VENDOR_HEADERS = { "Vendor Name*", "Website", "Country", "State", "City",
			"Tier", "First Name*", "Last Name", "Email*", "Phone", "Status", "Owner(Email)",
			"ClientAddress", "BillingAddress", "PaymentTerms" };
	public static String[] CLIENT_HEADERS = { "Client Name*", "Website", "Country", "State", "City",
			"Tier", "First Name*", "Last Name", "Email*", "Phone", "Status", "Owner(Email)",
			"ClientAddress", "BillingAddress", "PaymentTerms" };
	public static String[] VENDOR_CONTACTS_STATUS_DROPDOWN = { "New", "Active", "In-Active",
			"Negotiation", "Contracted" };
	public static String[] CLIENT_CONTACTS_STATUS_DROPDOWN = { "New", "Active", "In-Active",
			"Negotiation", "Contracted" };
	public static String[] CLIENT_PAYMENT_TERMS = { "10 days", "15 days", "30 days", "45 days",
			"60 days", "90 days" };
	public static int level1 = 1;
	public static int level2 = 2;
	public static int PREVIOUS_MONTH = -1;
	public static int NEXT_MONTH = 1;
	public static int TWELVE_MONTH = 1;
	public static int CURRENT_MONTH = 0;
	public static String SHORTLIST_COUNT = "Shortlisted Applicants:";
	public static String INTERVIEW_COUNT = "Interview Applicants: ";
	public static String OFFER_COUNT = "Offer Applicants:";
	public static String REJECTED_COUNT = "Rejected Applicants :";
	public static String SUBMITTED_APPLICANTS_COUNT = "Submitted Applicants :";
	public static String REQUEST_SENT_COUNT = "Request Sent Applicants :";
	public static String SUBMIT_TO_CLIENT_COUNT = "Submit to Client Applicants :";
	public static String All_APPLICANTS = "all";
	public static String DUPLICATE_APPLICANTS = "DUPLICATE_APPLICANTS";
	public static String REQ_SEARCHTYPE = "Requirement";
	public static String TODAY = "Today";
	public static String ONE_DAY_AGO = "1 Day Ago";
	public static String THREE_DAY_AGO = "3 Days Ago";
	public static String ONE_WEEK_AGO = "1 Week Ago";
	public static String TWO_WEEK_AGO = "2 Weeks Ago";
	public static String ONE_MONTH_AGO = "1 Month Ago";
	public static String THREE_MONTH_AGO = "3 Months Ago";
	public static String ALL_RECORDS = "All";
	public static String SCORE_20 = "20";
	public static String SCORE_10 = "10";
	public static String NOTICE_PERIOD_15DAYS = "15 Days";
	public static String NOTICE_PERIOD_1MONTH = "1 Month";
	public static String NOTICE_PERIOD_2MONTH = "2 Month";
	public static String NOTICE_PERIOD_3MONTH = "3 Month";
	public static String NOTICE_PERIOD_IMMEDIATELY = "Immediately available";
	public static Integer EXPERIENCE_26YEARS = 26;
	public static final String RATE_TYPE_HOURLY = "Hourly";
	public static final String RATE_TYPE_WEEKLY = "Weekly";
	public static final String RATE_TYPE_BI_WEEKLY = "Bi-weekly";
	public static final String RATE_TYPE_SEMI_MONTHLY = "Semi-Monthly";
	public static final String RATE_TYPE_MONTHLY = "Monthly";
	public static final String RATE_TYPE_BI_MONTHLY = "Bi-Monthly";
	public static String PROJECT = "Projects";
	public static String USER = "User";
	public static String REQUIREMENT = "Requirement";
	public static String USERS = "Users";
	public static String CLIENTS_CONTACTS_IMPORT_XLS = "clients_contacts_Import.xls";
	public static String CLIENTS_CONTACTS_IMPORT_XLSX = "clients_contacts_Import.xlsx";
	public static String CLIENTS_IMPORT_TEMPLATE_XLS = "clients_import_template.xls";
	public static String CLIENTS_IMPORT_TEMPLATE_XLSX = "clients_import_template.xlsx";
	public static String VENDORS_CONTACTS_IMPORT_XLS = "vendors_contacts_Import.xls";
	public static String VENDORS_CONTACTS_IMPORT_XLSX = "vendors_contacts_Import.xlsx";
	public static String VENDORS_IMPORT_TEMPLATE_XLS = "vendors_import_template.xls";
	public static String VENDORS_IMPORT_TEMPLATE_XLSX = "vendors_import_template.xlsx";
	public static String CANDIDATE_FIRST_NAME = "First Name*";
	public static String CANDIDATE_LAST_NAME = "Last Name";
	public static String CANDIDATE_EAMIL = "Email id*";
	public static String CANDIDATE_PHONE_NUMBER = "Phone Number";
	public static String CANDIDATE_COUNTRY = "Country";
	public static String CANDIDATE_STATE = "State";
	public static String CANDIDATE_CITY = "City";
	public static String CANDIDATE_ADDRESS = "Address";
	public static String CANDIDATE_DESIGNATION = "Designation";
	public static String CANDIDATE_TYPE = "Type";
	public static String CANDIDATE_EXPERIENCE_YEARS = "Experience (Years)";
	public static String CANDIDATE_EXPERIENCE_MONTHS = "Experience (Months)";
	public static String CANDIDATE_CURRENT_RATE = "Current Rate";
	public static String CANDIDATE_CURRENT_RATE_TYPE = "Current Rate Type";
	public static String CANDIDATE_CURRENT_RATE_CURRENCY = "Current Rate Currency";
	public static String CANDIDATE_EXPECTED_RATE = "Expected Rate";
	public static String CANDIDATE_EXPECTED_RATE_TYPE = "Expected Rate Type";
	public static String CANDIDATE_EXPECTED_RATE_CURRENCY = "Expected Rate Currency";
	public static String CANDIDATE_SKILLS_NAME = "Skills Name";
	public static String CANDIDATE_EDUCATION = "Education";
	public static String CANDIDATE_EMPLOERS = "Employer's";
	public static String CANDIDATE_PRIMARY_SKILL = "Primary Skill";
	public static String EMPLOYEE = "Employee";
	public static String CUSTOM_REPORTS = "custom";
	public static String UTC_TIMEZONE = "UTC";
	public static String DATE_DD_MM_YYYY = "dd-MM-yyyy";
	public static String TO_DAY = "today";
	public static String YESTER_DAY = "yesterday";
	public static String ONE_WEEK = "week";
	public static String TWO_WEEKS = "twoweeks";
	public static String ONE_MONTH = "month";
	public static String TWO_MONTHS = "twomonths";
	public static String THREE_MONTHS = "threemonths";
	public static String SIX_MONTHS = "sixmonths";
	public static String ONE_YEAR = "oneyear";
	public static String INVOICE_ACCEPETED = "Accepted";
	public static String INVOICE_REJECTED = "Rejected";
	public static String INVOICE_RECEIVED = "Received";
	public static String INVOICE_STATUS_UNPAID = "Unpaid";
	public static String INVOICE_STATUS_PAID = "Paid";
	public static String INVOICE_STATUS_PARTIAL_PAID = "Partial Paid";
	public static String INVOICE_SENT = "SENT";
	
	public static Long CANDIATE_RESUME_IN_ACTIVE = 0L;
	// invoice
	public static String[] INVOICE_PDF_HEADERS = { "Associate Name & Id", "Duration(From-To Date)",
			"Rate", "Total Hours", "Total" };
	public static String submitForApproval = "Submit For Approval";
	public static String TSAPPROVED = "Approved";
	public static String TSREJECTED = "Rejected";
	public static String TSNEW = "New";
	public static String SENT = "SENT";
	public static String PAYABLE = "payable";
	
	
//	public static  String  COUNTRY_INDIA = "India";
	public static final String COUNTRY_INDIA = "India";
	public static final String COUNTRY_AUSTRALIA = "Australia";
	public static final String COUNTRY_UNITEDSTATES = "United States";
	public static final String COUNTRY_SINGAPORE = "Singapore";
	public static final String COUNTRY_UNITEDKINGDOM = "United Kingdom";
	public static final String COUNTRY_FRANCE = "France";
	
	public static final String CURRENCYCODE_INR = "INR";
	public static final String CURRENCYCODE_AUD = "AUD";
	public static final String CURRENCYCODE_USD = "USD";
	public static final String CURRENCYCODE_SGD = "SGD";
	public static final String CURRENCYCODE_GBP = "GBP";
	public static final String CURRENCYCODE_EUR = "EUR";
	
	
	
	
	
	
}