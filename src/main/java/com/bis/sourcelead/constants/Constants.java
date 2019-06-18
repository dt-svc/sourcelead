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
	public static final String[] AVAILABLE_ASSOCIATES_REPORT_EXPORT_HEADDERS = { "Project Name", "Project Duration","Associate Duration", "Availability %","No.of Hours",};
	
	public static final String[] EXPENSE_PENDING_APPROVAL_REPORT_EXPORT_HEADDERS = { "Emp_ID", "Emp_Name","Emp_Type", "Project","Duration","Approval Mechanism","Manager1","Manager2","Submitted Amount","Submitted on","Approved By"};
	
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
	public static final String DATEOFJOIN = "Date of Join";
	public static final String ROLE = "Role*";
	public static final String MANAGER = "Manager*";
	public static final String EMPLOYMENT_TYPE ="Employment Type*";
	public static final String ASSOCIATE_VENDOR="Vendor";
	public static final String ASSOCIATE_VENDOR_CONTACT="Vendor Contact(Email)";
	public static final String ASSOCIATE_GRADE="Grade";
	public static final String ASSOCIATE_DESIGNATION="Designation*";
	public static final String ASSOCIATE_SHIFT="Shift*";
	public static final String INVOICE_FREQUENCY = "Invoice Frequency";
	public static final String DAY_HOURS = "Day Hours";
	public static final String FAILED_REASON = "Reason";
	public static final String EMAIL = "Email*";
	public static final String RESCHEDULED_EMAIL="entity_type_rescheduled_email";
	public static final String CLIENT_NAME = "Client Name*";
	public static final String PHONE = "Phone";
	public static final String EXTENSION_NUMBER = "Extension Number";
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
	public static final String VENDOR_ADDRESS = "VenodrAddress";
	public static final String BILLING_ADDRESS = "BillingAddress";
	public static final String PAYMENT_TERMS = "PaymentTerms";
	public static final String INVOICE_RECEIVABLE = "Receivable";
	public static final String INVOICE_PAYABLES = "Payables";
	public static final String FINANCE_TYPE_INDIVIDUAL = "Individual";
	public static final String FINANCE_TYPE_BULK = "Bulk";
	
	public static final String ASSOCIATE_ID= "Associate Id";
	public static final String ASSOCIATE_EMAIL= "Associate Email*";
	public static final String DEDUCTION_TYPE = "Deduction Type";
	public static final String DEDUCT_AMOUNT= "Deduct Amount";
	public static final String CONTRIBUTE_AMOUNT = "Contribute Amount";

	public static final String country = "Country";
	public static final String TASK_BILLABLE = "Billable";
	public static final String TASK_NON_BILLABLE = "Non-Billable";
	public static final Integer RECORD_ACTIVE = 1;
	public static final Integer RECORD_INACTIVE = 0;
	public static final Integer RECORD_DRAFT = 2;
	public static final Integer LEVEL1 = 1;
	public static final Integer LEVEL2 = 2;
	public static final Integer PROFILE_PERCENTAGE = 20;
	public static final DateFormat DMY = new SimpleDateFormat("dd-MM-yyyy");
	public static final DateFormat DF = new SimpleDateFormat("yyyy/MM/dd");
	public static final DateFormat DF_STD = new SimpleDateFormat("yyyy/MM/dd");
	public static final DateFormat DF_YMD = new SimpleDateFormat("yyyy-MM-dd");
	public static final DateFormat DF_STD_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
	public static final DateFormat MY = new SimpleDateFormat("MMMM-yyyy");
	public static final DateFormat Y = new SimpleDateFormat("yyyy");
	public static final DateFormat M = new SimpleDateFormat("MMMM");
	public static final DateFormat DF_MM = new SimpleDateFormat("MM");
	public static final DateFormat SHOT_M = new SimpleDateFormat("MMM");
	public static final DateFormat Y_M = new SimpleDateFormat("yyyy-MM");
	public static final DateFormat DF_DMY = new SimpleDateFormat("dd-MM-yyyy");
	public static final DateFormat DF_MDY = new SimpleDateFormat("MM-dd-yyyy");
	public static final DateFormat DF_STD_DMY = new SimpleDateFormat("dd/MM/yyyy");
	public static final DateFormat SHORT_DF_STD_DM = new SimpleDateFormat("dd MMM");
	public static final DateFormat SHORT_DF_STD_MD = new SimpleDateFormat("");
	public static final DateFormat SHORT_DF_MM_DD = new SimpleDateFormat("MMM-dd");
	public static final DateFormat TZ_DF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        public static final DateFormat TZ_DF1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        public static final DateFormat FULL_DATE = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
	
	// Below line of code is used to print Week days like Sunday, Monday.... by passing Date.
	public static final SimpleDateFormat WEEK_DAY =  new SimpleDateFormat("EEEE"); 
	
	public static final Long SOURCELEAD_ORG_LOCATION_ID = 1L;
	/*
	 * public static final DateFormat TZ_DF_TS = new SimpleDateFormat (
	 * "yyyy-MM-dd HH:mm:ss");
	 */
	
	public static final String MAILTOEMPLOYEEWHEN_MANAGERSUBMITEXPENSE = "MailToEmployeeWhenManagerSubmitExpense";

	public static final String MAILTOAPPROVER2WHENAPPROVER1APPROVESTHEEXPENSE = "MailToApprover2WhenApprover1ApprovesTheExpense";

	public static final String MAILTOMANAGER1WHENMANAGER2ORADMIN_REJECT_THE_EXPENSE = "MailToManager1WhenManager2orAdminRejectTheExpense";

	public static final String MAILTOMANAGER1WHENMANAGER2ORADMIN_APPROVESTHE_EXPENSE = "MailToManager1WhenManager2orAdminApprovesTheExpense";


	
	public static final String DEFAULT = "Default";
	public static final String ON = "on";
	public static final String ONBOARD_DOCUMENT_BUSNIESS_LICENSE = "Business License";
	public static final String BANK_CHEQUE = "Bank Cheque";
	public static final String REGISTRATION_OR_INCORPORATION_OF_RECORDING = "Registration or Incorporation of recording";
	public static final String ANNUAL_REPORT_BUSINESS_LICENSE = "Annual Report Business License";
	public static final String W9_W8_BEN_E = "W-9,W-8 Ben-E";
	
	public static final String ONBOARDING_FORMS = "Forms";
	public static final String ONBOARDING_POLICIES = "Policies";
	public static final String ONBOARDING_CONTRACTS = "Contracts";
	public static final String ONBOARDING_SUPPORTED = "Supported";
	public static final String MAIL_TEAM_MEMBERS = "Mail To Team Members";
	public static final String INVITE_CONNECTION_USER = "connectionInvitation";
	public static final String INVITE_USER = "Invite User";
	public static final String BLOG_SUBSCRIPTION = "blogSubscription";
	public static final String EXPENSES_EMAIL_TO_MANAGER = "ExpensesEmailToManager";
	public static final String INVITE_CLIENT = "Invite Client";
	public static final String INVITE_VENDOR = "Invite Vendor";
	public static final String MODIFY_CONTRACT_ENDDATE = "ModifyContractEndDate";
	public static final String MODIFY_CONTRACT_ENDDATE_NOTIFICATION = "ModifyContractEndDateNotification";
	public static final String MODIFY_SOW_CONTRACT_ENDDATE = "ModifySowContractEndDate";
	public static final String ACCEPT_CLIENT_INVITATION = "Accept Client Invitation";
	public static final String ACCEPT_VENDOR_INVITATION = "Accept Vendor Invitation";
	public static final String ACCEPT_CLIENT = "Accept Client";
	public static final String ACCEPT_VENDOR = "Accept Vendor";
	public static final String REJECT_CLIENT = "Reject Client";
	public static final String REJECT_VENDOR = "Reject Vendor";
	public static final String SHORTLISTMAILFROMCLIENT_TO_VENDOR = "ShortlistMailFromClient_To_Vendor";
	public static final String OFFER_MAIL_TO_VENDORS = "Client to Vendor Acceptence";
	public static final String APPLICANT_INTERVIEW = "Applicant Interview";
	public static final String INTERVIEWER_INVITATION = "Interviewer Invitation";
	public static final String INTERVIEWER_TEMPLATE = "Interviewer Template";
	public static final String INTERVIEW_RESCHEDULEMAIL_TO_APPLICANT = "interviewRescheduleMailToApplicant";
	public static final String OFFER_ACCEPTED = "Offer Accepted";
	public static final String OFFER_ACCEPTED_NOTIFICATION = "Offer Accepted Notification";
	public static final String OFFER_REJECTED = "Offer Rejected";
	public static final String OFFER_REJECTED_NOTIFICATION = "Offer Rejected Notification";
	public static final String HOT_LIST_CAMPAIGN = "HotListCampaign";
	public static final String JOB_LIST_CAMPAIGN = "JobListCampaign";
	public static final String EMAIL_TO_USERS = "EmailToUsers";
	public static final String EMAIL_CAMPAIGN = "EmailCampaign";
	public static final String CLIENTORVENDORMAIL = "ClientOrVendorMail";
	public static final String CUSTOM_USER_EMAIL = "customUserEmail";
	public static final String INVOICE_CLIENT_RECEIVABLE_MAIL = "InvoiceClientAcceptEmail";
	public static final String INVOICE_CLIENT_RECEIVABLE_REMINDER_MAIL = "InvoiceClientReceivableReminderEmail";
	public static final String INVOICE_CLIENT_PAYABLE_MAIL = "InvoiceClientPayableEmail";
	public static final String INVOICE_CLIENT_PAYABLE_REMINDER_MAIL = "InvoiceClientPayableReminderEmail";
	public static final String SUBMIT_TO_CLIENT = "Submit To Client";
	public static final String ADD_OFFER_MAIL = "Forward the Mail";
	public static final String NOTIFICATION_TO_CLIENT = "Notification To Client";
	public static final String VENDOR_INTERVIEW = "Vendors Interview";
	public static final String VENDOR_INVITATION = "Vendor Invitation";
	public static final String EDIT_CLIENTCONTACT = "Edit SpocContact";
	public static final String CLIENT_OWNER_UPDATEMAIL = "clientOwnerUpdateMail";
	public static final String SHORTLIST_APPLICANTS = "ShortList Applicants";
	public static final String SHORTLIST_NOTIFICATION_MANAGER = "Notification To Manager";
	public static final String VENDOR_INTERVIEW_RESCHEDULE = "Vendors Interview Reschedule";
	public static final String MAIL_PROJECT_MANAGER_TIMESHEET = "Mail To Manager At Timesheet Submission";
	public static final String MAIL_TEAM_MEMBERS_APPROVED_TIMESHEET = "Timesheet Approved By Approver";
	public static final String MAIL_TEAM_MEMBERS_REJECTED_TIMESHEET = "Timesheet Rejected By Approver";
	public static final String MAIL_TEAM_MANAGER_APPROVED_EXPENSE = "ExpensesApprovalManagerEmailToEmployee";
	public static final String MAIL_TEAM_MANAGER_REJECTED_EXPENSE = "ExpensesApprovalRejectedEmailToEmployee";
	public static final String MAIL_APPROVERS_REOPEN_EXPENSE = "ExpensesReopenEmailToSuperAdmin";
	
	public static final String MAIL_REOPEN_APPROVED_MAIL_TO_EMPLOYEE = "MailReopenApprovedMailToEmployee";
	public static final String MAIL_REOPEN_APPROVED_MAIL_TO_EMPLOYEE_NOTIFICATION = "MailReopenApprovedMailToEmployeeNotification";
	
	public static final String IN_SEQUENTIAL_MECHANISM_MAIL_FROM_APPROVERONE_TO_APPROVERTWO = "InSequentialMechanismMailFromApproverOneToApproverTwo";
	public static final String IN_SEQUENTIAL_MECHANISM_MAIL_FROM_APPROVERONE_TO_APPROVERTWO_NOTIFICATION = "InSequentialMechanismMailFromApproverOneToApproverTwo";
	
	public static final String IN_PARALLEL_MECHANISM_MAIL_FROM_APPROVERONE_TO_APPROVERTWO = "InSequentialMechanismMailFromApproverOneToApproverTwo";
	public static final String IN_PARALLEL_MECHANISM_MAIL_FROM_APPROVERONE_TO_APPROVERTWO_NOTIFICATION = "InSequentialMechanismMailFromApproverOneToApproverTwo";
	
	public static final String UPDATED_TASK_EMAIL = "UpdatedTaskEmail";
	public static final String UPDATED_TASK_NOTIFICATION = "UpdatedTaskNotification";
	
	public static final String ASSET_ASSIGN_EMAIL = "AssigneAssetEmail";
	public static final String ASSET_ASSIGN_NOTIFICATION = "AssigneAssetNotification";
	
	public static final String ADD_TASK_COMMENT_EMAIL = "AddTaskCommentEmail";
	public static final String ADD_TASK_COMMENT_NOTIFICATION = "AddTaskCommentNotification";
	
	public static final String ADD_GOAL_COMMENT_EMAIL = "AddGoalsCommentEmail";
	public static final String ADD_GOAL_COMMENT_NOTIFICATION = "AddGoalCommentNotification";
	
	public static final String TIMESHEET_APPROVED_EMAIL_TOVENDOR = "TimesheetApprovedEmailToVendor";
	public static final String TIMESHEET_REJECTED_EMAIL_TOVENDOR = "TimesheetRejectEmailToVendor";
	public static final String LEAVE_CANCEL_EMAIL_TO_USER = "LeaveCancelEmailToUser";
	public static final String LEAVE_CANCEL_NOTOFICATION_TO_USER = "LeaveCancelNotificationToUser";
	public static final String EXPENSES_REJECTED_EMAIL_TOVENDOR = "ExpensesRejectEmailToVendor";
	public static final String EXPENSES_APPROVED_EMAIL_TOVENDOR = "ExpensesApproveEmailToVendor";
	public static final String MAIL_REOPEN_APPROVED_EXPENSES_EMAIL_TO_APPROVERS = "ReopenApprovedExpenseEmailToApprovers";
	public static final String MAIL_REOPEN_REJECT_EXPENSES_EMAIL_TO_APPROVERS = "ReopenRejectExpenseEmailToApprovers";
	
	public static final String MAIL_SEND_PROJECT_DOCUMENTS = "SendProjectDocuments";
	
	public static final String ORGREQUEST_EMAIL = "orgRequestEmail";
	public static final String ORGREQUEST_NOTIFICATION = "orgRequestNotification";
	
	public static final String SOURCLEAD_TICKET_EMAIL = "sourceleadTicketEmail";
	public static final String SOURCLEAD_TICKET_NOTIFICATION = "sourceleadTicketNotification";
	
	public static final String TICKET_MANAGEMENT_EMAIL = "NewTicketEmail";
	public static final String TICKET_MANAGEMENT_NOTIFICATION ="NewTicketNotification";
	
	public static final String TICKET_COMMENT_EMAIL = "NewTicketCommentEmail";
	public static final String TICKET_COMMENT_NOTIFICATION ="NewTicketCommentNotification";
	
	public static final String TICKET_UPDATE_EMAIL = "ChangeTicketStatusEmail";
	public static final String TICKET_UPDATE_NOTIFICATION = "ChangeTicketStatusNotification";
	
	public static final String ORGDEMO_EMAIL = "orgDemoEmail";
	public static final String ORGDEMO_NOTIFICATION = "orgDemoNotification";
	
	public static final String CANDIDATE_BULK_IMPORT_EMAIL = "CandidateBulkImport";
	public static final String CANDIDATE_BULK_IMPORT_SUPPORT_EMAIL = "CandidateBulkImportSupport";
	
	public static final String PROFILES_ADDED_TO_CART_MAIL= "ProfilesAddedToCartMail";
	public static final String PROFILES_ADDED_TO_CART_NOTIFICATION = "ProfilesAddedToCartNotification";
	public static final String ENTITY_TYPE_EMAIL = "Email";
	public static final String ENTITY_TYPE_NOTIFICATION = "Notification";
	public static final String ENTITY_TYPE_TEMPLATE = "Notification";
	public static final String CLIENT_INTERVIEW = "CLIENT_INTERVIEW";
	public static final String BACKOUT_STATUS = "BACKOUT";
	public static final String INTERVIEW = "INTERVIEW";
	public static final String MAIL_REOPEN_TIMESHEET = "ReopenTimeSheet";
	public static final String MAIL_ACCEPT_REOPEN_TIMESHEET = "AcceptReopenTimeSheet";
	public static final String MAIL_REJECT_REOPEN_TIMESHEET = "RejectReopenTimeSheet";
	public static final String MAIL_BOX_SENT = "Sent";
	public static final String MAIL_BOX_INBOX = "Inbox";
	public static final String TASK_STATUS_OPEN = "Open";
	public static final String TASK_STATUS_CLOSE = "Closed";
	public static final String TASK_STATUS_MYTASK = "myTask";
	public static final String TASK_STATUS_ASSIGNTASK = "assignedTasks";
	public static final String TASK_STATUS_CLOSEDTASK = "closedTasks";
	public static final String DEFAULT_INTERVIEW_FEEDBACK_TEMAPLATE = "Sourcelead Inerview Feedback Template";
	public static final String TASK_MECHANISM_EMAIL = "Task Mechanism";
	public static final String TASK_MECHANISM_NOTIFICATION = "Task Mechanism";
	public static final String MODIFIED_REQUIREMENT_STATUS_EMAIL = "ModifiedRequirementStatusMailtoVendorContact";
	public static final String MODIFIED_REQUIREMENT_STATUS_NOTIFICATION = "ModifiedRequirementStatusNotificationtoVendorContact";
	public static final String HOTLIST_ALERT_EMAIL = "HotListAlertMail";
	public static final String HOTLIST_ALERT_NOTIFICATION = "HotListAlertNotification";
	public static final long SOURCELEAD_ADMIN_USERID = 1;
	public static final TimeZone TIMEZONE_CENTRAL = TimeZone.getDefault();
	public static final String loginUserAsAdmin = ":userId IN (select userRole.user.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.orgLocations.id = ts.projectMembers. projectClientsId.orgLocationsId.id and userRole.userState = 'ACTIVE')";
	public static final String loginUserAsAdminWithMaxHeirarchyLevelForTs = "(:userId IN (select userRole.user.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.orgLocations.id = pclient.orgLocationsId.id and userRole.userState = 'ACTIVE') and  sah.hierarchyId.level IN(select MAX(pah.level) from ProjectApproverHierarchy pah where pah.projectClientsId.id = pclient.id and pah.status != '0'))";
	public static final String loginUserAsAdminWithMaxHeirarchyLevelForExp = "(:userId IN (select userRole.user.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.orgLocations.id = pc.orgLocationsId.id and userRole.userState = 'ACTIVE') and  sah.hierarchyId.level IN(select MAX(pah.level) from ExpenseProjectApproverHierarchy pah where pah.projectClientsId.id = pc.id and pah.status != '0' ))";
	public static final String loginUserAsAdminForProjects = ":userId IN (select userRole.user.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.orgLocations.id = pclient.orgLocationsId.id and userRole.userState = 'ACTIVE')";
	public static final String IMMIGRATION_NOTIFICATION_TO_BENEFICIARY = "notificationToImmigrationBeneficiary";
	/** projectMember constants **/
	public static final String MEMBER_ACTIVE = "1";
	public static final String MEMBER_IN_ACTIVE = "0";
	public static final int CANDIDATE_ALL = 2;
	public static final int CANDIDATE_ACTIVE = 1;
	public static final int CANDIDATE_IN_ACTIVE = 0;
	public static final String STATUS_WITHDRAWN = "WITHDRAWN";
	/** Candidate Skill constants **/
	public static final int CANDIDATE_SKILL_ACTIVE = 1;
	public static final int CANDIDATE_SKILL_IN_ACTIVE = 0;
	public static final String ACTIVE = "Active";
	public static final String ADDED_CART = "In_Cart";
	public static final String INACTIVE = "In-Active";
	public static final String PURCHASED = "Purchased";
	public static final String SUCCESS = "Success";
	public static final String FAILED = "Failed";
	/** projectMemberHistory constants **/
	public static final String PROJECT_MEMBER_HISTORY_OPEN = "OPEN";
	public static final String PROJECT_MEMBER_HISTORY_CLOSED = "CLOSED";
	public static final String REQUIREMENT_EMAIL_NOTIFICATION = "Approval Mechanism";
	public static final String SHARE_RECOMENDED_APPLICANT = "Share Recomended Applicant";
	public static final String SHARE_RECOMENDED_APPLICANT_NOTIFICATION = "Share Recommended Applicant Notification";
	public static final String APPROVER_UPDATED_CONTRACT = "approverUpdatedContract";
	public static final String APPROVE_CONTRACT = "Approve Contract";
	public static final String REJECT_CONTRACT = "Reject Contract";
	public static final String CONTRACT_MAIL_TO_OWNER = "Contract Mail To Owner";
	public static final String CONTRACT_EXPIRY_REMAINDER = "contractExpiryRemainder";
	public static final String CONTRACT_DOCUMENTS_RECEIVED = "contractDocumentsReceived";
	public static final String MSA_CONTRACT_FINALIZED = "MSAFinalizeEmail";
	public static final String MSA_FINALIZED_NOTIFICATION = "MsaFinalizedNotification";
	public static final String SOW_CONTRACT_FINALIZED = "SOWFinalizeEmail";
	public static final String SOW_FINALIZED_NOTIFICATION = "SowFinalizedNotification";
	public static final String NDA_NSA_CONTRACT_FINALIZED = "NDAOrNSAFinalizeEmail";
	public static final String REVISE_CONTRACT_EMAIL = "ReviseContractEmail";
	public static final String NDA_NSA_CONTRACT_FINALIZED_NOTIFICATION = "NDAOrNSAFinalizeNotification";
	public static final String APPLICANT_ACCEPTMAILTO_INTERVIEW_CREATOR = "ApplicantAcceptMailToInterviewCreator";
	public static final String APPLICANT_REJECT_MAILTO_INTERVIEW_CREATOR = "ApplicantRejectMailToInterviewCreator";
	public static final String APPLICANT_TENTATIVE_MAILTO_INTERVIEWCREATOR = "ApplicantTentativeMailToInterviewCreator";
	public static final String VENDOR_APPLICANT_REJECTMAIL_TO_INTERVIEWCREATOR = "VendorApplicantRejectMailToInterviewCreator";
	public static final String VENDOR_APPLICANT_ACCEPT_MAILTO_INTERVIEWCREATOR = "VendorApplicantAcceptMailToInterviewCreator";
	public static final String VENDOR_APPLICANT_TENTATIVE_MAILTO_INTERVIEWCREATOR = "VendorApplicantTentativeMailToInterviewCreator";
	public static final long USERID = 1;
	public static final String PASSWORD_RESET_MAIL_TO_USER = "passwordResetMailToUser";
	public static final String REQUIREMENT_SEQUENTIAL_EMAIL_NOTIFICATION = "Approval Mechanism Sequential";
	public static final String APPROVED_EMAIL_NOTIFICATION = "Approved Requirement";
	public static final String REJECTED_EMAIL_NOTIFICATION = "Rejected Mail About The Requirement";
	public static final String RELEASEMECHANISM_EMAIL_NOTIFICATION = "Release Mechanism";
	public static final String MAILREQUIREMENTTOMANAGER = "Requirement Posting";
	public static final String ASSIGNTEAMMEMBERSFORREQUIREMENT = "AssignToTeamMembers";
	public static final String WEEK_TIME_SHEET_SUBMISSION = "Week Time Sheet Submission";
	public static final String TIMESHEET_EMAIL_NOTIFICATION = "Timesheet Scheduler";
	public static final String TIMESHEET_MANAGER_NOTIFICATION = "Timesheet Scheduler Level1 Manager";
	public static final String UPDATEDREQUIREMENTNOTIFICATIONTOVENDOR = "updatedRequirementNotificationToVendor";
	public static final String OFFBOARDINGPROCESSAPPLICANTTERMINATION = "OffBoardingProcessApplicantTermination"; 
	public static final String EMAIL_TO_IMMIGRATION_BENEFICIARY = "emailToImmigrationBeneficiary";
	
	public static final String OFFBOARDINGAPPLICANTDELAYPROCESS = "OffBoardingApplicantDealayProcess";
	public static final String OFFBOARDINGPROCESSAPPLICANTTERMINATIONMAILTOREPORTINGMANAGER = "OffBoardingProcessMailToReportingManager";
	
	public static final String USERTERMINATEDEMAIL="UserTerminatedEmail";
	public static final String USERTERMINATEDNOTIFICATIONEMAIL="UserTerminatedNotificationEmail";
	
	public static final String APPLY_LEAVE_REMAINDER="ApplyLeaveRemainder";
	
	public static final String HOLIDAY_REMAINDER="HolidayRemainder";
	public static final String HOLIDAY_REMAINDER_NOTIFICATION="HolidayRemainderNotification";
	
	public static final String LEAVE_APPROVER_EMAIL = "Approver Leave";
	public static final String LEAVE_APPROVER_NOTIFICATION = "ApproverLeaveNotification";
	
	public static final String LEAVE_REJECTED_EMAIL = "Rejected Leave";
	public static final String LEAVE_REJECTED_NOTIFICATION = "RejectedLeaveNotification";
	
	public static final String LEAVE_WITHDRAW_EMAIL = "Withdraw Leave";
	public static final String LEAVE_WITHDRAW_NOTIFICATION = "WithdrawLeaveNotification";
	
	public static final String RESOURCE_REQUEST = "Resource Request";
	public static final String RESOURCE_REQUEST_NOTIFICATION = "ResourceRequestNotification";
	
	public static final String NEW_RESOURCE_REQUEST = "New Resource Request";
	public static final String NEW_RESOURCE_REQUEST_NOTIFICATION = "NewResourceRequestNotification";
	
	public static final String ACCEPT_SINGLE_NEW_RESOURCE_REQUEST = "Accept Single New Resource Request";
	public static final String ACCEPT_SINGLE_NEW_RESOURCE_REQUEST_NOTIFICATION = "AcceptSingleNewResourceRequestNotification";
	
	public static final String REJECT_SINGLE_NEW_RESOURCE_REQUEST = "Reject Single New Resource Request";
	public static final String REJECT_SINGLE_NEW_RESOURCE_REQUEST_NOTIFICATION = "RejectSingleNewResourceRequestNotification";
	
	public static final String ACCEPT_BULK_NEW_RESOURCE_REQUEST = "Accept Bulk New Resource Request";
	public static final String ACCEPT_BULK_NEW_RESOURCE_REQUEST_NOTIFICATION = "AcceptBulkNewResourceRequestNotification";
	
	public static final String REJECT_BULK_NEW_RESOURCE_REQUEST = "Reject Bulk New Resource Request";
	public static final String REJECT_BULK_NEW_RESOURCE_REQUEST_NOTIFICATION = "RejectBulkNewResourceRequesttNotification";
	
	public static final String ONHOLD_BULK_NEW_RESOURCE_REQUEST = "OnHold Bulk New Resource Request";
	public static final String ONHOLD_BULK_NEW_RESOURCE_REQUEST_NOTIFICATION = "OnHoldBulkNewResourceRequesttNotification";
	
	public static final String ACCEPT_BULK_RESOURCE_REQUEST = "Accept Bulk Resource Request";
	public static final String ACCEPT_BULK_RESOURCE_REQUEST_NOTIFICATION = "AcceptBulkResourceRequestNotification";
	
	public static final String REJECT_BULK_RESOURCE_REQUEST = "Reject Bulk Resource Request";
	public static final String REJECT_BULK_RESOURCE_REQUEST_NOTIFICATION = "RejectBulkResourceRequestNotification";
	
	public static final String ONHOLD_BULK_RESOURCE_REQUEST = "OnHold Bulk Resource Request";
	public static final String ONHOLD_BULK_RESOURCE_REQUEST_NOTIFICATION = "OnHoldBulkResourceRequestNotification";
	
	public static final String REJECT_SINGLE_RESOURCE_REQUEST = "Reject Single Resource Request";
	public static final String REJECT_SINGLE_RESOURCE_REQUEST_NOTIFICATION = "RejectSingleResourceRequestNotification";
	
	public static final String ACCEPT_SINGLE_RESOURCE_REQUEST = "Accept Single Resource Request";
	public static final String ACCEPT_SINGLE_RESOURCE_REQUEST_NOTIFICATION = "AcceptSingleResourceRequestNotification";
	
	public static final String BUDGET_REQUEST = "Budget Request";
	public static final String BUDGET_REQUEST_NOTIFICATION = "BudgetRequestNotification";
	
	public static final String BUDGET_REQUEST_ACCEPTED = "Budget Request Accepted";
	public static final String BUDGET_REQUEST_ACCEPTED_NOTIFICATION = "BudgetRequestAcceptedNotification";
	
	public static final String BUDGET_REQUEST_REJECTED_EMAIL = "Budget Request Rejected";
	public static final String BUDGET_REQUEST_REJECTED_NOTIFICATION = "BudgetRequestRejectedNotification";
	
	public static final String BUDGET_REQUEST_ONHOLD_EMAIL = "Budget Request OnHold";
	public static final String BUDGET_REQUEST_ONHOLD_NOTIFICATION = "BudgetRequestOnHoldNotification";
	
	
	public static final String UPDATED_SPOC_CONTACT = "SpocPointOfContatChangeEmail";
	public static final String INVITE_COMPANY = "inviteCompany";
	public static final String UPDATED_USER_ROLE_EMAIL = "UserRoleChanged";
	public static final String INVOICE_ACCEPT_MAIL_VENDOR_EMAIL = "InvoiceAcceptMailtoVendor";
	public static final String INVOICE_REJECT_MAIL_VENDOR_EMAIL = "InvoiceRejectMailtoVendor";
	public static final String INVOICE_ACCEPT_MAIL_CLIENT_EMAIL = "InvoiceAcceptMailtoClient";
	public static final String INVOICE_REJECT_MAIL_CLIENT_EMAIL = "InvoiceRejectMailtoClient";
	public static final String UPDATED_USER_ROLE_NOTIFICATION = "UserRoleChanged";
	public static final String UPDATED_REQUIREMENT_EMAIL_TO_MANAGER = "updatedRequirementEmailToManager";
	public static final String UPDATED_REQUIREMENT_NOTIFICATION_TO_MANAGER = "updatedRequirementNotificationToManager";
	public static final String UPDATED_FEEDBACK_NOTIFICATIONTO_REQUIREMENTOWNER = "updatedFeedbackNotificationToRequirementOwner";
	public static final String OFFERREJECT_EMAIL = "Vendor Offer Reject";
	public static final String OFFERACCEPT_EMAIL = "VendorAcceptedOffer";
	public static final String APPLICANT_OFFER_ACCEPT_EMAIL = "ApplicantAcceptOffer";
	public static final String APPLICANT_OFFER_REJECT_EMAIL = "ApplicantRejectOffer";
	public static final String APPLICANT_RECEIVE_MAILABOUT_REQUIREMENT_EMAIL = "ApplicantReceiveMailAboutRequirement";
	public static final String NOTIFY_APPLICANT_MAILABOUT_REQUIREMENT = "notifyApplicantMailAboutRequirement";
	public static final String APPLICANT_RECEIVE_MAILABOUT_REQUIREMENT_NOTIFICATION = "ApplicantReceiveMailAboutRequirement";
	public static final String APPLICANT_ACCEPT_MAILABOUT_REQUIREMENT_EMAIL_NOTIFICATION = "ApplicantAcceptRequirement";
	public static final String APPLICANT_REJECT_MAILABOUT_REQUIREMENT_EMAIL_NOTIFICATION = "ApplicantRejectanceRequirement";
	public static final String APPLICANT_NEGOTIATE_REQUIREMENT = "ApplicantNegotiateRequirement";
	public static final String USER_RATE_NEGOTIATION = "UserRateNegotiation";
	public static final String CLIENT_RATE_NEGOTIATION = "ClientRateNegotiation";
	public static final String VENDOR_RATE_NEGOTIATION = "VendorRateNegotiation";
	public static final String USER_ACCEPT_RATE_NEGOTIATION = "UserAcceptRateNegotiation";
	public static final String USER_REJECT_RATE_NEGOTIATION = "UserRejectRateNegotiation";
	public static final String CLIENT_ACCEPT_RATE_NEGOTIATION = "ClientAcceptRateNegotiation";
	public static final String VENDOR_ACCEPT_RATE_NEGOTIATION = "VendorAcceptRateNegotiation";
	public static final String CLIENT_REJECT_RATE_NEGOTIATION = "ClientRejectRateNegotiation";
	public static final String VENDOR_REJECT_RATE_NEGOTIATION = "VendorRejectRateNegotiation";
	public static final String ROLE_NAME_PROFESSIONAL = "ROLE_CROWDSOURCING_PROFESSIONAL";
	public static final String ROLE_NAME_INDEPENDENT_RECRUITER = "ROLE_CROWDSOURCING_RECRUITER";
	public static final String PROFESSIONAL = "Professional";
	public static final String INDIVIDUAL_RECRUITER = "Recruiter";
	public static final String INDEPENDENT_RECRUITER = "Independent Recruiter";
	public static final String INVITE_PROFESSIONAL = "Professional Invitation";
	public static final String ACTIVATE_EMAIL = "ActivateUserEmail";
	public static final String INVITE_INDIVIDUAL_RECRUITER = "Individual Recruiter Invitation";
	public static final String LOGIN_OTP_EMAIL = "LoginOTPEmail";
	public static final String USER_NEW_ACTION_ASSIGNED="UserNewActionAssigned";
	public static final String USER_NEW_ACTION_ASSIGNED_As_A_Approver="UserNewActionAssignedAsaApprover";
	public static final String USER_NEW_ACTION_ASSIGNED_As_A_REPORTING_MANAGER="UserNewActionAssignedAsaReportingManager";
	public static final String USER_NEW_ACTION_ASSIGNED_As_A_TEAM_OWNER="UserNewActionAssignedAsaTeamOwnerUser";
	public static final String USER_NEW_ACTION_ASSIGNED_As_A_PROJECT_APROVER="UserNewActionAssignedAsaProjectApproverUser";
	public static final String USER_NEW_ACTION_ASSIGNED_NOTIFICATION ="UserNewActionAssignedNotification";
	public static final String REQUESTED_DOCUMENTS = "RequestForDocumentsToCandidates";
	public static final String UPLOADED_REQUESTED_DOCUMENTS = "UploadedRequestForDocumentsToCandidates";
	public static final String APPLICANT_ACCEPT_RATE = "ApplicantAcceptRate";
	public static final String APPLIED_TO_REQUIREMENT = "Applied To Requirement";
	public static final String GUEST_APPLIED_TO_REQUIREMENT = "Guest Applied To Requirement";
	public static final String REFER_REQUIREMENT_TO_PROFESSIONAL = "Refer Requirment To Professional";
	public static final String ADD_NEW_USER_CONTACT = "Add New User Contact";
	public static final String CROWD_SOURCING_SOURCE_SELF = "SELF";
	public static final String EMPLOYEE_TERMINATION = "Employee Termination";
	public static final String EMPLOYEE_TERMINATION_MAIL_TO_MANAGER = "Employee Termination Mail To Manager";
	public static final String EMPLOYEE_REASSIGN = "Employee Reassign";
	public static final String SocialInviter = "SocialInviter";
	public static final String CONTRACT_SIGNED = "ContractSigned";
	public static final String REMINDER_CONTRACT_SIGNING_EMAIL_PENDING = "RemindercontractSigningEmailPending";
	public static final String REMINDER_CONTRACT_SIGNING_EMAIL_WAITING_TO_BE_VERIFY = "RemindercontractSigningEmailWTV";
	public static final String SEND_CONTRACT = "SendContract";
	public static final String SEND_SOW_CONTRACT = "SendSowContract";
	public static final DateFormat TMS = new SimpleDateFormat("hh:mm a");
	public static final DateFormat TMS_FORMAT = new SimpleDateFormat("HH:mm aa");
	public static final String INTERVIEWER_INVITATION_REDIFINED = "Interviewer InvitationRedifined";
	public static final String PROPOSE_NEW_TIME = "ProposeNewTime";
	public static final String VENDOR_PROPOSE_NEW_TIME = "VendorProposeNewTime";
	public static final String YES = "Y";
	public static final String NO = "N";
	public static final String WAIT = "W";
	public static final String REJECT = "R";
	public static final String REJECTED = "REJECTED";
	public static final String TEMP = "T";
	public static final DateFormat STDF = new SimpleDateFormat("MM-dd-yyyy");
	public static final DateFormat DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd");
	public static final DateFormat DF_TM = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
	public static final int MAX_RESULTS_FIVE = 5;
	public static final int MAX_RESULTS_TEN = 10;
	public static final int MAX_RESULTS_TWENTY = 20;
	public static final int MAX_RESULTS_THIRTY = 30;
	public static final int MAX_RESULTS_FIFTY = 50;
	public static final int MAX_RESULTS_200 = 200;
	public static final int PAGES_INCREMENT = 10;
	public static final int PAGES_DECREMENT = -10;
	public static final int MODULE_ATS = 2;
	public static final int MODULE_VMS = 3;
	public static final int MODULE_TIMESHEET = 4;
	public static final String MODULE_ATS_STR = "2";
	public static final String MODULE_VMS_STR = "3";
	public static final String MODULE_TIMESHEET_STR = "4";
	public static final int TAB_VENDOR = 9;
	public static final int TAB_BILLING = 20;
	public static final int TAB_CLIENT = 7;
	public static final int TAB_MY_TIMESHEET = 5;
	public static final int TAB_TIMESHEET_APPROVAL = 6;
	public static final int TAB_REQUIREMENT = 8;
	public static final int TAB_CANDIATE = 10;
	public static final int TAB_USER = 2;
	public static final int TAB_PROJECTS = 3;
	public static final int TAB_CONTRACTS = 16;
	public static final int TAB_IMMIGRATION = 33;
	public static final int TAB_RUN_PAYROLL = 37;
	public static final String Dashboard = "Dashboard";
	public static final String Users = "Users";
	public static final String Immigration = "Immigration";
	public static final String Immigrations = "Immigrations";
	public static final String My_Immigration = "MyImmigration";
	public static final String Projects = "Projects";
	public static final String Clients = "Clients";
	public static final String TimeSheet = "TimeSheet";
	public static final String TimeSheetApproval = "TimeSheetApproval";
	public static final String OrgSettings = "OrgSettings";
	public static final String Blogs = "Blogs";
	public static final String ReleaseNote = "Release Note";
	public static final String UserManuals = "UserManuals";
	public static final String Support = "Support";
	public static final String BackOffice = "BackOffice";
	public static final String Finance = "Finance";
	public static final String AssetManagement = "AssetManagement";
	public static final String Contract = "Contract";
	public static final String Profile = "Profile";
	public static final String List_by_status = "List by status";
	public static final String TimeSheet_Approval = "Timesheet Approval";
	public static final String Requirement = "Requirement";
	public static final String JOBS = "Jobs";
	public static final String Vendors = "Vendors";
	public static final String Candidates = "Candidates";
	public static final String Favourite = "Favourite";
	public static final String Candidate = "Candidate";
	public static final String Associates = "Associates";
	public static final String OnAndOffboard = "OnAndOffboard";
	public static final String Receivable = "Receivable";
	public static final String Payables = "Payables";
	public static final String PAYROLL = "Payroll";
	public static final String PAY = "Pay"; 
	public static final String TAXES = "Taxes";
	public static final String FORMS = "Forms";
	public static final String MYPAYROLL = "MyPayroll";
	public static final String RUNPAYROLL = "RunPayroll";
	public static final String PERFORMANCE = "Performance";
	public static final String SOURCELEAD_ORGCODE = "sourcelead.com";
	public static final String SOURCELEAD_LOCATIONCODE_IN = "in.sourcelead.com";
	/** Constants values for unique emails and notification **/
	public static final String ORG_REGISTRATION = "Org Registration";
	public static final String FORGOT_PASSWORD = "Forgot Password";
	public static final String UPDATE_PASSWORD = "Update Password";
	public static final String MAIL_PROJECT_MANAGER = "Mail To Project Manager";
	public static final String EDITPROJECT_MAIL_MANAGER = "Editprojecttomanager";
	public static final String EDITPROJECT_MAIL_TEAMMEMBER = "Editprojecttoteammember";
	public static final String REJECTMAILFROMCLIENT_TO_VENDOR = "RejectMailFromClient_To_Vendor";
	public static final String CONTACTS = "CONTACTS";
	public static final String VENDORSTYPE = "VENDORS";
	/** Constants values for notifications **/
	public static final String NOTIFICATION_UNREAD = "unread";
	public static final String NOTIFICATION_READ = "read";
	public static final String NOTIFICATION_DELETED = "0";
	public static final String NOTIFICATION_NOT_DELETED = "1";
	public static final String TIME_SHEET_SUBMISSION = "Time Sheet Submission";
	public static final String PLACE_UUID = "PLACE_UUID";
	/** for user roles **/
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_APPROVER = "ROLE_APPROVER";
	public static final String ROLE_USER = "ROLE_USER";
	public static final String SOURCELEAD_ADMIN = "ROLE_SOURCELEAD_ADMIN";
	public static final String HR_MANAGER = "ROLE_HR_MANAGER";
	public static final String ACCOUNT_MANAGER = "ROLE_ACCOUNT_MANAGER";
	public static final String SALES_MANAGER = "ROLE_SALES_MANAGER";
	public static final String RECRUITER = "ROLE_RECRUITER";
	public static final String ROLE_MANAGER = "ROLE_MANAGER";
	public static final String Applicant_Acceptance_By_Manager_Of_Requirement = "ApplicantAcceptanceByManagerOfRequirement";
	public static final String Applicant_Will_Receive_Mail_About_The_Acceptance_Of_Requirement_By_Manager = "ApplicantWillReceiveMailAboutTheAcceptanceOfRequirementByManager";
	public static final String Recruiter_Will_Receive_Mail_About_The_Acceptance_Of_Requirement_By_Manager = "RecruiterWillReceiveMailAboutTheAcceptanceOfRequirementByManager";
	public static final String Applicant_Authorization_Manager_Acceptence_Receive_Mail_About_The_Acceptance_Of_Requirement_By_Manager = "ApplicantAuthorizationManagerAcceptance";
	public static final String[] RATEPER_LIST = { "Daily", "Hourly", "Weekly", "Bi-Weekly",
			"Semi-Monthly", "Monthly", "Yearly" };
	public static final String[] ROLE_LIST = { "Admin", "Employee", "Project Manager", "Team Lead" };
	public static final String[] INVOICE_FREQUENCY_LIST = { "Weekly", "Bi-Weekly", "Semi-Monthly",
			"Monthly" };
	public static final String[] YES_NO = { "Y", "N" };
	/** For LookUpData (Eductions,Skills) **/
	public static final String EDUCATIONS = "Educations";
	public static final String SKILLS = "Skills";
	public static final String VISA = "VisaCategory";
	public static final String INDUSTIRES = "Industries";
	
	public static final String TASKTYPE = "TaskType";
	public static final String TASKSEVERITY = "TaskSeverity";
	public static final String REQUEST_ORG = "Request_Org";
	public static final String TASKPRIORITY = "TaskPriority";
	public static final String TASKSTATUS = "TaskStatus";
	public static final String COMPANY_ONBOARD_BUSINESS_TYPE = "Company Onboard Business type";
	
	public static final String EXPENSESCATEGORY = "Expense Category";
	public static final String EXPERIENCELEVEL = "Experience Level";
	
	public static final String NOTES_JOBS = "Jobs";
    public static final String NOTES_CONTRACTS = "Contracts";
    public static final String NOTES_BILLING = "Billing";
    public static final String NOTES_CONTACTS = "Contacts";
    
    public static final String SOURCELEAD_ADMIN_UNIQUE = "Sourcelead";
	
	public static final String DOCUMENT_TYPE = "Document Type";
	public static final String ATTORNEY_CATEGORY = "AttorneyCategory";
	public static final String PETITIONER_CATEGORY = "PetitionerCategory";
	public static final String ADMIN = "Admin";
	public static final String MAIL_SENT = "Sent";
	public static final String MAIL_NEW = "New";
	public static final String MAIL_RESEND = "Resend";
	public static final String MAIL_ACCEPTED = "Accepted";
	public static final String MAIL_REJECTED = "Rejected";
	public static final String OFFERMADE = "OFFERMADE";
	public static final String CLIENT_OFFERMADE = "CLIENT_OFFERMADE";
	public static final String REFER_JOB_APPLIED = "Applied";
	public static final String REQUIREMENT_STATUS = "Draft";
	public static final String TEAM_ACTIVE = "1";
	public static final String TEAM_IN_ACTIVE = "0";
	public static final Integer DEFAULT_CATEGORY = 0;
	public static final String WEEKLY = "Weekly";
	public static final String BI_WEEKLY = "Bi-Weekly";
	public static final String SEMI_MONTHLY = "Semi-Monthly";
	public static final String MONTHLY = "Monthly";
	public static final String YEARLY = "Yearly";
	public static final String DEFAULT_JOB_WORKFLOW = "Default Job WorkFlow";
	public static final int MIN_PROFILE_PERCENETAGE = 30;
	
	// "Workflow" removed from the below list as it planned for next build SRINIVA SABBU 07- JUNE-2019.
	public static final String[] IMPORT_JOB_EXCEL_HEADERS = { "Jobs id", "Title*",
			"Min Experience", "Max Experience", "Min/Max Rate", "Location*", "City",	"State", "Country", "Zip Code",	
			"Education", "Skills", "Immigration Status", "Cooling-off Period", "Travel Allowance", "Closed Date",
			"Employment Type", "No. of Positions", "Notice Period", "Industry", "Priority*","Workflow","Description", "Attachment", "Manager(Email)*", "Approver", "Assign To",
			"Application Form", "Client Name", "Client Contact Name", "Bill Rate / Salary", "Client Submission Form", "Status"};
	
	public static final String[] IMPORT_ASSET_EXCEL_HEADERS = { "Category*", "Work Status*",
			"Name","Laptop Serial Number/CPU Serial Number*", "Make", "Model", "MAC Address", "Memory", "CPU", "Monitor Serial Number", "Hard Disk","Operating System","OS Key","Anti-Virus","Manufactured Date","Purchase Date","Cost","Depreciation Assessment Date","Depreciation Amount","Vendor Details", "Description"};
	
	public static final String[] ROLE_DROPDOWN = { "Admin", "Project Manager", "HR Manager",
			"Recruiter" };
	public static final String[] STATUS_DROPDOWN = { "Active", "In-Active", "New", "Negotiation",
			"Contracted" };
	public static final String[] COUNTRY_DROPDOWN = { "India", "Australia", "France", "Singapore",
			"United Kingdom", "United States" };
	public static final String[] LEVEL_DROPDOWN = { "Tier-1", "Tier-2", "Tier-3", "Tier-4", "Tier-5" };
	public static final String[] USER_EXCEL_HEADERS_INDIA = {  "Employee Id","Date Of Join","Email-id","Probationary Period","status","Confirmation Date",
			"First Name","Middle Name", "Last Name","Work Location","Residence Location","Phone Number","Extension Number","Gender","MaritalStatus","Date Of Birth",
			"Department","EmpType","Role","Designation","Grade","Reporting Manager","Resource Manager","Shift","Rate",
			"Daily Hours","Global Manager","Is  send mail to Associate","Allow Employee To Fill","PAN Number","Aadhar Number" };
	public static final String[] USER_EXCEL_HEADERS = { "Employee Id", "First Name", "Last Name",
			"Email-id", "Phone Number", "Date Of Join", "Rate", "Role", "Manager",
			"status" };
	public static final String[] USER_EXCEL_HEADERS_NOT_INDIA = {	"Employee Id","Date Of Join","Email-id","Probationary Period","status","Confirmation Date",
			"First Name","Middle Name","Last Name","Work Location","Residence Location","Phone Number","Extension Number","Gender","MaritalStatus","Date Of Birth",	"Department",
			"EmpType","Role","Designation","Grade","Reporting Manager","Resource Manager","Shift","Rate",
			"Daily Hours","Global Manager","Is send mail to Associate","Allow Employee To Fill","SSN Number"};
	public static final String[] DAILY_WISE_LEAVE_REPORT_HEADERS = { "Emp Name", "Emp Type", "Departement",
			"Role", "Leave Type", "Type", "Duration", "From Date",
			"To Date", "Applied On", "Reason", "Status", "Approver", "Approved On" };
	public static final String[] LEAVE_REPORT_HEADERS = { "Employee Id", "Emp Name", "Departement", "Leave Type",

			"Reason", "Type", "From Date", "To Date", "Applied On", "No Of Days",

			"Status", "Approver", "Approved On" };
	public static final String[] CANDIDATE_EXCEL_HEADERS = { "Firstname", "Last name", "Email id",
			"Phone Number", "Location", "Designation", "Education", "Experience", "Skills",
			"Rate", "Vendor", "Vendor Contact", "Is Favourite"};
	public static final String[] REQUIREMENT_EXCEL_HEADERS = { "Jobs id", "Jobs Title",
			"Experience", "Rate", "Location", "Skills", "Manager", "Priority",
			"Client Name", "Client Contact Name", "Client Contact Email",
			"Status", "Posted On (YYYY-MM-DD)"};
	public static final String[] REQUIREMENT_PIPELINE_EXCEL_HEADERS = { "Name", "Email", "PhoneNumber",
			"Resume", "Applied On", "Rate", "Source", "Score", "Experience", "Status",
			"Submitted On", "Submitted By" };
	public static final String[] REQUIREMENT_PIPELINE_REQUEST_SENT_HEADERS = { "Name", "Email",
			"PhoneNumber", "Resume", "Applied On", "Rate", "Source", "Score", "Experience",
			"Status" };
	public static final String[] REQUIREMENT_PIPELINE_SUBMIT_TO_CLIENT_EXCEL_HEADERS = { "Name", "Email",
			"PhoneNumber", "Resume", "Applied On", "Applicant Rate", "Submission Rate", "Source",
			"Score", "Experience", "Status", "Submitted On", "Submitted By", "Client Contact" };
	public static final String[] REQUIREMENT_PIPELINE_SUBMIT_TO_CLIENT_EXCEL_HEADERS_FOR_TEMPLATE_CONFIGURED = {
			"Resume", "Applied On", "Applicant Rate", "Submission Rate", "Source", "Score", "Experience", "Status",
			"Submitted On", "Submitted By", "Client Contact" };
	public static final String[] REQUIREMENT_PIPELINE_SHORTLISTED_EXCEL_HEADERS = { "Name", "Email",
			"PhoneNumber", "Resume", "Applied On", "Rate", "Source", "Score", "Experience",
			"Status", "ShortListed By", "ShortListed On" };
	public static final String[] REQUIREMENT_PIPELINE_INTERVIEW_EXCEL_HEADERS = { "Name", "Email",
			"Phone No", "Experience", "DateOfInterview", "TimeOfInterview", "Location" };
	public static final String[] REQUIREMENT_PIPELINE_OFFERMADE_EXCEL_HEADERS = { "Name", "Email",
			"Phone No", "Experience", "OfferDateJoining", "Position", "Offer Rate", "Offerd Date",
			"Attachment" };
	public static final String[] REQUIREMENT_PIPELINE_REJECTED_EXCEL_HEADERS = { "Name", "Email",
			"PhoneNumber", "Resume", "Applied On", "Rate", "Source", "Rejected On", "Rejected By",
			"Rejected Stage", "Status" };
	public static final String[] TIMESHEET_EXCEL_HEADERS = { "Tasks", "Monday", "Tuesday", "Wednesday",
			"Thursday", "Friday", "Saturday", "Sunday", "Total" };
	public static final String[] TIMESHEET_EXCEL_HEADERS2 = { "Tasks", "Mon", "Tue", "Wed", "Thu", "Fri",
			"Sat", "Sun", "Total" };
	public static final String[] TIMESHEET_EXCEL_HEADERS3 = { "Status" };
	public static final String[] TIMESHEET_APPROVAL_MECHANISM_HEADER = { "Employee Name", "Email id",
			"Phone Number", "Client", "projectName", "Total", "Stage", "approved By" };
	public static final String[] CLIENT_EXCEL_HEADERS = { "Client Name", "Website", "Location", "Tiers",
			"Number of Contacts" };
	public static final String[] CLIENT_CONTACTS_EXCEL_HEADERS = { "Client Name", "Name", "Emailid",
			"Phone Number", "Owner", "Status" };
	public static final String[] VENDOR_EXCEL_HEADERS = { "VendorName", "Website", "Location", "Level",
			"Number of Contacts" };
	public static final String[] VENDOR_CONTACTS_EXCEL_HEADERS = { "Vendor Name", "Name", "Emailid",
			"Phone Number", "Owner", "Status" };
	public static final String[] SHORTLIST_CANDIDATE_FROM_CLIENT_TO_VENDOR_HEADER = { "CandidateName",
			"PhoneNumber", "Work Experience", "status" };
	public static final String[] BILLING_HEADERS = { "Invoice Id", "Client Name",
			"Client Contact Name"," Client Contact Email", "Client Phone ", "Invoice Date", "Due Date", "Total Amount", "Due Amount",
			"Paid Amount", "Status", "Payment Status", "Linked Invoice Id"};
	public static final String[] BILLING_PAYABLE_HEADERS = { "Bill Id", "Vendor Name",
			"Vendor Contact Name"," Vendor Contact Email", "Vendor Phone ", "Bill Date", "Due Date", "Total Amount", "Due Amount",
			"Paid Amount", "Status", "Payment Status", "Linked Bill Id"};
	
	public static final String[] EXPENSES_REPORTS_HEADERS = {"Emp id","Employee Name","Email id","Phone Number","Emp Type","Vendor","Vendor Contact","Project","Client"};
	public static final String[] EXPENSES_REPORTS_HEADERS_1 = {"Amount","Claimed"};
	
	
	public static final String[] PROJECT_EXCEL_HEADERS = { "Project Id", "Name", "Manager", "Invoice Frequency", "Budget", "Date", "Client", "Status"};
	
	public static final String[] PROJECT_TASKS_PDF_HEADERS = { "Task Name", "Task Type", "Priority", "Assigned To", "Status"};
	public static final String[] PROJECT_TASKS_EXCEL_HEADERS = { "Task Name", "Task Type", "Priority", "Assigned To","Due Date","Estimated Time","Severity","Owner","Billing Type","Business Value", "Status","Description"};

	public static final String[] DATA_MIGRATION_PROJECT_TASKS_EXCEL_HEADERS = { "Task Type*" , "Task Title*","Assigned To", "Due Date", "Priority", "Estimated Time","Severity","Status*", "Owner","Billing Type*","Currency Type","Business Value", "Description"};
	

	public static final String[] IMMIGRATION_LIST_EXCEL_HEADERS = { "ID", "Country", "Beneficiary First Name", "Beneficiary Last Name", "Beneficiary Email", "Phone Number", "Experience", "SSN", "I-94 Number", "I-20 SEVIS Number If Student", "EAD Number If Student",
			"DOB", "Marital Status", "Petition Type", "Template", "Date Of Arrival", "Open On", "Status", "Visa No", "Visa Start Date", "Visa End Date", "Visa Status", "Attorney/Company", "Contact Name", "Contact Email", "Receipt No", "Location", "LCA Start Date", 
			"LCA End Date", "LCA Number", "LCA Rate", "LCA Location", "LCA Title","Job Name","Job Id", "Previous LCA Start Date", "Previous LCA End Date", "Previous LCA Number", "Previous LCA Rate"};

	public static final String[] DATA_MIGRATION_ASSOCIATE_BANKING_EXCEL_HEADERS = { "Assoicate Id" , "Associate Email*","Payment Mode*","Bank Name*","Account Number*","BSR Number","MICR Code","IFSC Code*",
			"First Name*","Last Name*",};
	
	public static final String[] DATA_MIGRATION_ASSOCIATE_SALARY_EXCEL_HEADERS = { "Assoicate Id" , "Associate Email*","Pay Type*","Pay Frequency*","Pay Group*","Standard Hours","Annual Salary*"};						

	public static final String[] DATA_MIGRATION_ASSOCIATE_EXCEL_HEADERS = { "Employee Id" , "First Name*", "Last Name", "Middle Name", "Email Id*", "Phone Number", "Extension",
			"Rate", "Rate Currency", "Rate Per", "Gender*","Marital Status","Date of Join","Date of Birth","Role*","Day hours(HH:MM)","Manager*(Email)", "Employment type*","Vendor(Email)","Vendor Contact(Email)","Grade","Designation*","Shift*","Department*","Send Mail to User","Is Global Manager"};

	public static final String[] PAYMENT_MODE = {"SAVINGS", "CHEQUE", "CASH"};
	
	public static final String[] ASSOCIATE_SALARY_PAYTYPE = {"Salary"};
	
	public static final String[] ASSOCIATE_SALARY_PAYFREQUENCY = {"Annually", "Monthly", "Bi-Weekly", "Weekly"};

	public static final String[] REQIREMENT_APPLICANT_HEADERS = { "Applicant Name", "Email","Job", "Client", "Phone Number",  "Job Location",   "Source", "Status","Job Rating" ,"Incentivized"};

	public static final String[] REQIREMENT_SUBMISSION_HEADERS = { "Applicant Name","Email", "Job", "Client", "Applied On", "Submitted On","Sub rate", "Submitted by", "Source", "Status","Job Rating" ,"Incentivized"};

	public static final String[] REQIREMENT_SHORTLIST_HEADERS = { "Applicant Name","Email", "Job", "Client",  "Shortlisted On", "Source", "Status","Job Rating" ,"Incentivized"};

	public static final String[] REQIREMENT_INTERVIEW_HEADERS = { "Applicant Name","Email", "Job", "Client",  "Applied On", "No Of Interviews",  "Source", "Status","Job Rating" ,"Incentivized"};

	public static final String[] REQIREMENT_OFFERMADE_HEADERS = { "Applicant Name","Email", "Job", "Client",  "Join Date", "Position Type", "Offered On","Source", "Status","Job Rating" ,"Incentivized"};

	public static final String[] REQIREMENT_REJECTED_HEADERS = { "Applicant Name","Email", "Job", "Client",  "Rejected Type", "Rejected Reason", "Source", "Status","Job Rating" ,"Incentivized"};

	public static final String[] REQIREMENT_JOINED_HEADERS = { "Applicant Name", "Email","Job", "Client", "Join Date", "Source", "Status","Job Rating" ,"Incentivized"};

	public static final String[] REQIREMENT_BACKOUT_HEADERS = { "Applicant Name", "Email", "Job", "Client", "Backout Type", "Backout Reason",  "Source", "Status","Job Rating" ,"Incentivized"};

	public static final String[] APPLICANTS_JOINED_HEADERS = { "Applicant Name", "Email Id","Contact Number","Job Title", "Job Rating","process","Client", "Date Of Interview", "Offer Rate","Source","Submitted On","Date Of Join", "Payment Term","Generate Invoice"};
	
	public static final String[] PURCHASE_CANDIDATE_HEADERS = {"Order No", "Order Date", "Company Name", "Total Resumes", "Total Price", "Service Fee", "Discount", "Grand Total", "Payment Status"};

	public static final String SHORTLIST_CANDIDATE_COUNT = "shortlist_candidate :";
	public static final String VENDOR = "Vendor";
	public static final String CLIENT = "Client";
	public static final String APPLICANT = "Applicant";
	public static final String JOB_CAMPAIGN_VENDOR = "JobCampaignVendor";
	public static final String HOT_LIST_CAMPAIGN_VENDOR = "ClientHotListCampaign";
	public static final String ELK_PROJECT_INDEX = "projects";
	public static final String ELK_PROJECT_TYPE = "project";
	public static final String ELK_CANDIDATE_INDEX = "candidates";
	public static final String ELK_USER_INDEX = "users";
	public static final String ELK_CANDIDATE_TYPE = "candidate";
	public static final String ELK_USER_TYPE = "user";
	public static final String ELK_REQUIREMENT_INDEX = "requirement";
	public static final String ELK_REQUIREMENT_TYPE = "requirement";
	public static final String ELK_EMAIL_INDEX = "email";
	public static final String ELK_CANDIDATE_ID_PREFIX = "CND";
	public static final String ELK_CLIENTVENDOR_INDEX = "clientvendor";
	public static final String ELK_CLIENTVENDOR_TYPE = "clientvendors";
	public static final String ELK_CLIENTVENDOR_CONTACT_INDEX = "clientvendorcontacts";
	public static final String ELK_CLIENTVENDOR_CONTACT_TYPE = "clientvendorcontacts";
	public static final String ELK_CAMPAIGN_INDEX = "campaigns";
	public static final String Campaigns = "Campaigns";
	public static final String CampaignSender = "CampaignSender";
	public static final String CampaignRecipient = "CampaignRecipient";
	public static final String ELK_CAMPAIGN_TYPE = "campaign";
	public static final String ELK_CAMPAIGN_ID_PREFIX = "CAMPAIGN";
	public static final String CANDIDATE = "Candidate";
	public static final String CONSULTANT = "Consultant";
	public static final String ASSOCIATE = "Associate";
	public static final String MAIL_TRASH = "Trash";
	public static final String MAIL_DELETE = "Deleted";
	public static final String MAIL_REQUESTED = "Requested";
	public static final String ELK_WEB_GEO_CITIES_INDEX = "webgeocities";
	public static final String ELK_WEBGEO_CITIES_TYPE = "webgeocities";
	public static final String[] PROJECT_ASSIGNED_EMPLOYEES = { "Emp Name","Start Date", "End Date","Bill Rate", "Pay Rate", "Estimated Hrs", "Estimated Budget", "Actual Hrs","% of Work" };
	// public static String[] VENDOR_CONTACTS_HEADERS = {"Vendor Name*","First
	// Name*","Last Name","Email*","Phone","Status","Owner(Email)*"};
	// public static String[] CLIENT_CONTACTS_HEADERS = {"Client Name*","First
	// Name*","Last Name","Email*","Phone","Status","Owner(Email)*"};
	public static final String[] VENDOR_HEADERS = { "Vendor Name*", "Website", "Country", "State", "City",
			"Tier", "First Name*", "Last Name", "Email*", "Phone", 
			"Extension Number","Status", "Owner(Email)",
			"VenodrAddress", "BillingAddress", "PaymentTerms" };
	public static final String[] CLIENT_HEADERS = { "Client Name*", "Website", "Country", "State", "City",
			"Tier", "First Name*", "Last Name", "Email*", "Phone", 
			"Extension Number","Status", "Owner(Email)",
			"ClientAddress", "BillingAddress", "PaymentTerms" };
	public static final String[] VENDOR_CONTACTS_STATUS_DROPDOWN = { "New", "Active", "In-Active",
			"Negotiation", "Contracted" };
	public static final String[] CLIENT_CONTACTS_STATUS_DROPDOWN = { "Active", "In-Active" };
	public static final String[] CLIENT_PAYMENT_TERMS = { "10 days", "15 days", "30 days", "45 days",
			"60 days", "90 days" };
	public static final int level1 = 1;
	public static final int level2 = 2;
	public static final int PREVIOUS_MONTH = -1;
	public static final int NEXT_MONTH = 1;
	public static final int TWELVE_MONTH = 1;
	public static final int CURRENT_MONTH = 0;
	public static final String SHORTLIST_COUNT = "Shortlisted Applicants:";
	public static final String INTERVIEW_COUNT = "Interview Applicants: ";
	public static final String OFFER_COUNT = "Offer Applicants:";
	public static final String REJECTED_COUNT = "Rejected Applicants :";
	public static final String SUBMITTED_APPLICANTS_COUNT = "Submitted Applicants :";
	public static final String REQUEST_SENT_COUNT = "Request Sent Applicants :";
	public static final String SUBMIT_TO_CLIENT_COUNT = "Submit to Client Applicants :";
	public static final String All_APPLICANTS = "all";
	public static final String DUPLICATE_APPLICANTS = "DUPLICATE_APPLICANTS";
	public static final String REQ_SEARCHTYPE = "Requirement";
	public static final String TODAY = "Today";
	public static final String ONE_DAY_AGO = "1 Day Ago";
	public static final String THREE_DAY_AGO = "3 Days Ago";
	public static final String ONE_WEEK_AGO = "1 Week Ago";
	public static final String TWO_WEEK_AGO = "2 Weeks Ago";
	public static final String ONE_MONTH_AGO = "1 Month Ago";
	public static final String THREE_MONTH_AGO = "3 Months Ago";
	public static final String ALL_RECORDS = "All";
	public static final String SCORE_20 = "20";
	public static final String SCORE_10 = "10";
	public static final String NOTICE_PERIOD_15DAYS = "15 Days";
	public static final String NOTICE_PERIOD_1MONTH = "1 Month";
	public static final String NOTICE_PERIOD_2MONTH = "2 Month";
	public static final String NOTICE_PERIOD_3MONTH = "3 Month";
	public static final String NOTICE_PERIOD_IMMEDIATELY = "Immediately available";
	public static final Integer EXPERIENCE_26YEARS = 26;
	public static final String RATE_TYPE_HOURLY = "Hourly";
	public static final String RATE_TYPE_WEEKLY = "Weekly";
	public static final String RATE_TYPE_BI_WEEKLY = "Bi-weekly";
	public static final String RATE_TYPE_SEMI_MONTHLY = "Semi-Monthly";
	public static final String RATE_TYPE_MONTHLY = "Monthly";
	public static final String RATE_TYPE_BI_MONTHLY = "Bi-Monthly";
	public static final String PROJECT = "Projects";
	public static final String USER = "User";
	public static final String REQUIREMENT = "Requirement";
	public static final String USERS = "Users";
	public static final String CLIENTS_CONTACTS_IMPORT_XLS = "clients_contacts_Import.xls";
	public static final String CLIENTS_CONTACTS_IMPORT_XLSX = "clients_contacts_Import.xlsx";
	public static final String CLIENTS_IMPORT_TEMPLATE_XLS = "clients_import_template.xls";
	public static final String CLIENTS_IMPORT_TEMPLATE_XLSX = "clients_import_template.xlsx";
	public static final String VENDORS_CONTACTS_IMPORT_XLS = "vendors_contacts_Import.xls";
	public static final String VENDORS_CONTACTS_IMPORT_XLSX = "vendors_contacts_Import.xlsx";
	public static final String VENDORS_IMPORT_TEMPLATE_XLS = "vendors_import_template.xls";
	public static final String VENDORS_IMPORT_TEMPLATE_XLSX = "vendors_import_template.xlsx";
	public static final String CANDIDATE_FIRST_NAME = "First Name*";
	public static final String CANDIDATE_LAST_NAME = "Last Name";
	public static final String CANDIDATE_EAMIL = "Email id*";
	public static final String CANDIDATE_PHONE_NUMBER = "Phone Number";
	public static final String CANDIDATE_COUNTRY = "Country";
	public static final String CANDIDATE_STATE = "State";
	public static final String CANDIDATE_CITY = "City";
	public static final String CANDIDATE_ADDRESS = "Address";
	public static final String CANDIDATE_DESIGNATION = "Designation";
	public static final String CANDIDATE_TYPE = "Type";
	
	public static final String CANDIDATE_VENDOR_NAME = "Vendor";
	public static final String CANDIDATE_VENDOR_CONTACT = "Vendor Contact(Email)";
	
	public static final String CANDIDATE_EXPERIENCE = "Experience";
	public static final String CANDIDATE_EXPERIENCE_YEARS = "Experience (Years)";
	public static final String CANDIDATE_EXPERIENCE_MONTHS = "Experience (Months)";
	public static final String CANDIDATE_CURRENT_RATE = "Current Rate";
	public static final String CANDIDATE_CURRENT_RATE_TYPE = "Current Rate Type";
	public static final String CANDIDATE_CURRENT_RATE_CURRENCY = "Current Rate Currency";
	public static final String CANDIDATE_EXPECTED_RATE = "Expected Rate";
	public static final String CANDIDATE_EXPECTED_RATE_TYPE = "Expected Rate Type";
	public static final String CANDIDATE_EXPECTED_RATE_CURRENCY = "Expected Rate Currency";
	public static final String CANDIDATE_SKILLS_NAME = "Skills Name";
	public static final String CANDIDATE_EDUCATION = "Education";
	public static final String CANDIDATE_EMPLOERS = "Employer's";
	public static final String CANDIDATE_PRIMARY_SKILL = "Primary Skill";
	public static final String CANDIDATE_INVITATION = "CandidateInvitation";
	public static final String EMPLOYEE = "Employee";
	public static final String CUSTOM_REPORTS = "custom";
	public static final String UTC_TIMEZONE = "UTC";
	public static final String DATE_DD_MM_YYYY = "dd-MM-yyyy";
	public static final String TO_DAY = "today";
	public static final String YESTER_DAY = "yesterday";
	public static final String ONE_WEEK = "week";
	public static final String TWO_WEEKS = "twoweeks";
	public static final String ONE_MONTH = "month";
	public static final String TWO_MONTHS = "twomonths";
	public static final String THREE_MONTHS = "threemonths";
	public static final String SIX_MONTHS = "sixmonths";
	public static final String ONE_YEAR = "oneyear";
	public static final String INVOICE_ACCEPETED = "Accepted";
	public static final String INVOICE_REJECTED = "Rejected";
	public static final String CREDIT_DEBIT_NOTE_REJECTED = "Rejected";
	public static final String CREDIT_DEBIT_NOTE_ACCEPTED = "Accepted";
	public static final String INVOICE_BILL_VOID = "Void";
	public static final String INVOICE_RECEIVED = "Received";
	public static final String INVOICE_STATUS_UNPAID = "Unpaid";
	public static final String INVOICE_STATUS_PAID = "Paid";
	public static final String INVOICE_TIMELINE_STATUS_PAID = "Payment";
	public static final String INVOICE_TIMELINE_STATUS_CREATED = "Created";
	public static final String INVOICE_STATUS_PARTIAL_PAID = "Partial Paid";
	public static final String INVOICE_SENT = "SENT";
	public static final Long CANDIATE_RESUME_IN_ACTIVE = 0L;
	// invoice
	public static final String[] INVOICE_PDF_HEADERS = { "Associate Name & Id", "Duration(From-To Date)",
			"Rate", "Total Hours", "Total" };
	public static final String submitForApproval = "Submit For Approval";
	public static final String TSAPPROVED = "Approved";
	public static final String TSREJECTED = "Rejected";
	public static final String TSNEW = "New";
	public static final String SENT = "SENT";
	public static final String PAYABLE = "payable";
	public static final String CREDIT_NOTE = "Credit";
	public static final String DEBIT_NOTE = "Debit";
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
	
	public static final String RESOURCE_REQUEST_WAITING_FOR_APPROVAL = "Waiting For Approval";
	public static final String RESOURCE_REQUEST_DRAFT = "Draft";
	public static final String RESOURCE_REQUEST_APPROVED = "Approved";
	public static final String RESOURCE_REQUEST_REJECTED = "Rejected";
	public static final String RESOURCE_REQUEST_ON_HOLD = "On hold";
	
	public static final String RESOURCE_REQUEST_NEW_REQUEST = "New Request";
	public static final String RESOURCE_REQUEST_ASSOCIATE_REQUEST = "Associate Request";
	
	
	public static final String BUDGET_REQUEST_WAITING_FOR_APPROVAL = "Waiting For Approval"; 
	public static final String BUDGET_REQUEST_APPROVED = "Approved"; 
	public static final String BUDGET_REQUEST_REJECTED = "Rejected";
	public static final String BUDGET_REQUEST_ON_HOLD = "On Hold";
	
	public static final int WORKING_HOURS = 8;
	
	public static final String DOCUMENT_READ_ACCESS = "0";
	public static final String DOCUMENT_WRITE_ACCESS = "1";
	
	public static final String PAYABLES = "Payables";
	public static final String RECEIVABLE ="Receivable";
	public static final String DRAFT = "DRAFT";
	public static final String CLIENT_SOW = "clientSow";
	public static final String VENDOR_SOW ="vendorSow";
	public static final String MY_EXPENSES = "My Expenses";
	public static final String EXPENSES_APPROVAL =  "Expenses Approval";
	public static final String THIS_MONTH =  "THIS_MONTH";
	public static final String THIS_QUARTER =  "THIS_QUARTER";
	public static final String THIS_YEAR =  "THIS_YEAR";
	public static final String LAST_30DAYS =  "LAST_30DAYS";
	public static final String LAST_MONTH =  "LAST_MONTH";
	public static final String LAST_QUARTER =  "LAST_QUARTER";
	public static final String LAST_YEAR =  "LAST_YEAR";
	public static final String CUSTOM =  "CUSTOM";
	public static final String LEAVE_MANAGENENT =  "Leave Management";

	public static final String BILLING =  "Billing";
	public static final String ACTIVE_USAGE_LIST =  "Active Usage List";
	public static final String INVOICE_LIST =  "Invoice List";
	public static final String BILLING_LIST =  "Billing List";
	public static final String LINKED_STATUS =  "Linked";

	public static final String APPLICANT_REJECTED = "Applicant Rejected";
	public static final String EXPENSES_STATUS = "1";
	

	public static final String[] MY_EXPENSES_HEADERS ={"Expense Category","Date","Receipt Issued by","Payment Mode","Currency","Amount","Description"};
	public static final String[] MY_EXPENSES_HEADERS_APPROVER ={"Expense Category","Date","Receipt Issued by","Payment Mode","Currency","Amount","Description","Status","Billable"};

	public static final String[] EXPENSES_APPROVAL_lIST_HEADERS = {"Expense ID","Employee Name", "Employee Emailid", "Phone Number",
			"Emp Type", "Vendor", "Project" ,"Client", "Purpose", "Start Date", "End date", "Number of Items",
			"Amount" , "Status"  , "Approved by","Blling Status"};
	
	public static final String[] PROJECT_REPORT_EXPORT_HEADDERS = { "ProjectId", "Project Name", "Project Status",
		"Project Manager", "Project StartDate", "Project EndDate" ,"Team Member", "TeamMember StartDate", "TeamMember Enddate", "TeamMember Status",};
	

	public static final String[] ASSETS_REPORT_EXPORT_HEADDERS = { "Employee Id", "Employee Name","Asset Name", "Category","Serial Number",
			"Assigned On", "Returned On" ,"Shift", "Document", "Assigned By",};
	
	public static final String[] JOBFAIR_EXPORT_HEADERS = {"Generated ID", "Title", "Start Date", "End Date", "No.of Jobs", "Applicants", "Offered", "Venue", "Status"};
	
	public static final String[] ALL_MY_EXPENSES_HEADERS ={"Expense ID","purpose","Project","Client","Start Date","End Date","No of Items","Amount","Status"};

	public static final String[] RECRUITER_REPORTS_HEADERS = { "Associate Name", "Jobs","Candidates","Applicants", "Shortlisted","Submitted","Interviewed", "Offered", "Rejected","Joined","Back-Out","OnHold", "No-Response"};

	public static final String[] RECRUITER_REPORTS_CANDIDATE_HEADERS = { "Candidate Name", "Email","Phone Number","Emp Type", "Vendor","Vendor Contact","Location", "Designation", "Current Rate","Expected Rate","Experience","Primary Skill", "Updated On"};

	public static final String[] RECRUITER_REPORTS_ALL_APPLICANTS_HEADERS = { "Applicant Name", "Email","Phone Number","Job Title", "Client","Job Location","Rate", "Submitted On", "Current Stage"};
	
	public static final String[] RECRUITER_REPORTS_SHORTLIST_HEADERS = { "Applicant Name", "Email","Phone Number","Job Title", "Client","Job Location","Rate", "Shortlisted On", "Current Stage"};
	
	public static final String[] RECRUITER_REPORTS_SUBMIT_TO_CLIENT_HEADERS = { "Applicant Name", "Email","Phone Number","Job Title", "Client","Job Location","Rate","Submitted Rate", "Submitted On", "Current Stage"};

	public static final String[] RECRUITER_REPORTS_INTERVIEW_HEADERS = { "Applicant Name", "Email","Phone Number","Job Title", "Client","Job Location","Rate", "No of Interviews", "Current Stage"};

	public static final String[] RECRUITER_REPORTS_OFFERMADE_HEADERS = { "Applicant Name", "Email","Phone Number","Job Title", "Client","Job Location","Rate","Offered Rate", "Offered On", "Current Stage"};

	public static final String[] RECRUITER_REPORTS_REJECTED_HEADERS = { "Applicant Name", "Email","Phone Number","Job Title", "Client","Job Location","Rate","Rejected On", "Category","Comment", "Current Stage"};

	public static final String[] RECRUITER_REPORTS_JOINED_HEADERS = { "Applicant Name", "Email","Phone Number","Job Title", "Client","Job Location","Rate","Offered On", "Join Date", "Current Stage"};

	public static final String[] RECRUITER_REPORTS_BACKOUT_HEADERS = { "Applicant Name", "Email","Phone Number","Job Title", "Client","Job Location","Rate","Backout On", "Category","Comment", "Current Stage"};

    public static final String BILLABLE_HOURS = "BILLABLE_HOURS";
	public static final String JOB_ALERT_DURATION_DIALY = "Daily";
	public static final String JOB_ALERT_DURATION_WEEKLY = "Weekly";
	public static final String JOB_ALERT_DURATION_MONTHLY = "Monthly";
	public static final String JOB_ALERTS = "JobAlerts";
    public static final String UNDEFINED = "undefined";
    public static final String EMPLOYEE_PAYROLL = "EMPLOYEE_PAYROLL";
    public static final String WAGE_TAX_SUMMARY = "WAGE_TAX";
    public static final String ACCOUNT_DEDUCTIONS = "ACCOUNT_DEDUCTIONS";
    public static final String DOCUMENT_UPLOAD_MANUALLY = "Manually";
    public static final String DOCUMENT_UPLOAD_REQUESTED = "Requested";
    public static final String DOCUMENT_UPLOAD = "Uploaded";
    public static final String REQUESTED_DOCUMENT_ACCEPTED = "Accepted";
    public static final String REQUESTED_DOCUMENT_REJECTED = "Rejected";
    public static final String REQUESTED_DOCUMENT_SUBMITTED = "Submitted";
    public static final String HR_DEDUCTIONS = "HR_DEDUCTIONS";
    public static final String PERCENTAGE = "percentage";
    public static final String[] PERCENTAGES = {"0%","10%","20%","30%","40%","50%"};
    public static final String[] PRIORITIES = {"medium","low","high","urgent"};
    public static final String DEFAULTPRIORITY = "Medium";
    public static final String[] COOLINGOFFSET = {"0 days","1 week","2 weeks","3 weeks","1 month","2 months","3 months","4 months","5 months","6 months","7 months","8 months","9 months","10 months","11 months","1 year"};


    public static final String[] EMPLOYEE_PAYROLL_HEADERS = {"Employee","Emp ID","SSN","Total Hrs","Gross Wages","Fica","Futa","Sui","Er locals","wc cost","admin fee","total er cost"};
    public static final String[] WAGES_TAX_HEADERS = {"Id","Employee Name","Check Date","Check No","Gross","fwt","ssec","med","swt","sdi/sui","local","deds","net"};
    public static final String[] ACCOUNT_DEDUCTION_HEADERS = {"Id","Name","ded","mar 15th","april 1st","april 15th","may 1st","may 15th","june 1st","june 15th","july 1st","july 15th","aug 1st","aug 15th","sep 1st","sep 15th","oct 1st","oct 15th","nov 1st","nov 15th","dec 1st","dec 15th"};
    public static final String[] HR_DEDUCTION_HEADERS = {"Id","Name","ded","mar 15th","april 1st","april 15th","may 1st","may 15th","june 1st","june 15th","july 1st","july 15th","aug 1st","aug 15th"};
    public static final String BIS_USERS = "BIS_USERS";
    public static final String OVER_DUE = "Overdue";
    
    //contracts 
    public static final String EXPIRED = "EXPIRED";
    public static final String FINALIZED = "FINALIZED";
    public static final String SEND_FOR_VERIFICATION = "SEND_FOR_VERIFICATION";
    public static final String WAITING_TO_BE_VERIFY = "WAITING_TO_BE_VERIFY";
    public static final String WAITING_TO_BE_SIGNED = "WAITING_TO_BE_SIGNED";
    public static final String DELETED = "DELETED";   
    public static final String APPROVED = "APPROVED";  
    public static final String SEQUENTIAL = "Sequential";  
    public static final String PARALLEL = "Parallel";  
    public static final String WAITING_TO_SEND = "WAITING_TO_SEND";  
    public static final String WAITING = "WAITING";  
    
    public static final String PENDING = "PENDING";

    public static final String WAITING_FOR_APPROVER = "WAITING_FOR_APPROVER";
    public static final String Client_SOW = "Client SOW";
	public static final String Vendor_SOW = "Vendor SOW";
	public static final String FULL_TIME_OFFER = "Full time offer";
	public static final String CLIENT_MSA = "Client MSA";
	public static final String VENDOR_MSA = "Vendor MSA";
	public static final String NDA = "NDA";
	public static final String NSA = "NSA";
	public static final String CUSTOM_TYPE = "Custom";
	public static final String NEW_TYPE = "New";
	public static final String EXISTING_TYPE =  "Existing";
	public static final String VENDOR_NDA = "Vendor NDA";
	public static final String VENDOR_NSA = "Vendor NSA";
	public static final String CLIENT_NDA = "Client NDA";
	public static final String CLIENT_NSA = "Client NSA";

	public static final String[] DEFAULT_TASKS = {"Holiday","Vacation Leave","Sick Leave","Casual Leave","Compensatory Leave","Work From Home","System Task Billable","System Task NonBillable"};
	public static final String BIS_CHECKS = "BIS_CHECKS";


    public static final String HOLIDAY = "Holiday";
    public static final String COMPENSATORY_LEAVE = "Compensatory Leave";
    public static final String WORK_FROM_HOME = "Work From Home";
	
	public static final String CANDIDATE_ENTITY = "CD";
	public static final String JOB_ENTITY = "JB";
	public static final String JOB_FAIR = "JF";
	public static final String IMMIGRATION_ENTITY = "IM";
	public static final String PROJECT_ENTITY = "PJ";
	public static final String EXPENSES_ENTITY = "EP";
	public static final String TICKET_ENTITY = "ST-";
	
	public static final String OLD_VALUE_TAG_INITIATION = "<span class='textline-throughold'>";
	public static final String OLD_VALUE_TAG_END = "</span>";
	public static final String NEW_VALUE_TAG_INITIATION = "<span class='textline-updated'>";
	public static final String NEW_VALUE_TAG_END = "</span>";
	
	
	public static final String JOBS_ACTION_ICON_CLASS = "fa fa-briefcase bgBlue";
	public static final String CANDIDATE_ACTION_ICON_CLASS = "fa fa-user bg-olive";
	public static final String PROJECTS_ACTION_ICON_CLASS = "fa fa-bar-chart bgAqua";
	public static final String TIMESHEETS_ACTION_ICON_CLASS = "fa fa-clock-o bgYellow";
	public static final String EXPENSES_ACTION_ICON_CLASS = "fa fa-calculator bgLime";
	
	public static final String MANUAL_CANDIDATE = "manual";
	public static final String IMPORT_CANDIDATE = "import";
	public static final String INVOICE_ENTITY_SHORTCODE = "INV";
	public static final String BILL_ENTITY_SHORTCODE = "BIL";
	public static final String CREDIT_ENTITY_SHORTCODE = "CR";
	public static final String DEBIT_ENTITY_SHORTCODE = "DB";
	public static final String CANDIDATE_ADDED_MANUALLY = "added manually";
	public static final String CANDIDATE_ADDED_BY_IMPORTING = "imported";
	public static final String CANDIDATE_EDITTED = "updated profile";
	public static final String CANDIDATE_DELETED = "deleted profile";
	public static final String CANDIDATE_REACTIVATED = "reactivated profile";
	public static final String CANDIDATE_DELETED_RESUME = "deleted resume";
	public static final String CANDIDATE_DELETED_EMPLOYER = "deleted employer";
	public static final String CANDIDATE_DELETED_EDUCATION = "deleted education";
	public static final String CANDIDATE_DELETED_CERTIFICATION = "deleted certification";
	public static final String CANDIDATE_DELETED_DOCUMENTS = "deleted document";
	public static final String CANDIDATE_DELETED_REFERENCES = "deleted reference";
	public static final String CANDIDATE_UPLOAD_NEW_PROFILE_IMAGE = "uploaded new profile image";
	public static final String CANDIDATE_DELETED_PROFILE_IMAGE = "deleted profile image";
	public static final String CANDIDATE_SKILL_UPDATED = "updated skills";
	public static final String CANDIDATE_VISA_UPDATED = "updated immigration status";
	public static final String CANDIDATE_RESUME_UPDATED = "updated resume";
	public static final String CANDIDATE_EMPLOYERS_UPDATED = "updated employers information";
	public static final String CANDIDATE_EDUCATION_UPDATED = "updated education details";
	public static final String CANDIDATE_CERTIFICATION_UPDATED = "updated certification";
	public static final String CANDIDATE_DOCUMENTS_UPDATED = "updated documents";
	public static final String CANDIDATE_REFERENCES_UPDATED = "updated references";
	public static final String CANDIDATE_COMMENTS_NEW = "posted new comment";
	public static final String CANDIDATE_ADDED_FROM_JOB = "added candidate through ";
	public static final String CANDIDATE_REQUEST_DOCUMENTS = "requested for documents";
	public static final String CANDIDATE_ACCEPT_REQUEST_DOCUMENTS = "accepted documents";
	public static final String CANDIDATE_REJECT_REQUEST_DOCUMENTS = "rejected documents";
	public static final String CANDIDATE_TAG_ADDED = "added tag";
	public static final String CANDIDATE_TAG_REMOVED = "removed tag";
	
	public static final String EXPENSES = "expenses";
	public static final String VIEW_TYPE = "LISTVIEW"; 
	public static final String GUEST = "Guest";
	public static final String INVOICE_DATA = "INVOICE";
	public static final String DIR_DEP = "Dir Dep";
	
	public static final String LEGAL_FEES = "LEGAL_FEES";
	public static final String ADVANCE = "ADVANCE";
	public static final String GENERAL_EXPENSES = "GENERAL_EXPENSES";
	public static final String VENDOR_PAYMENTS = "VENDOR_PAYMENTS";
	public static final String ELECTRONIC_WITHDRAWLS = "ELECTRONIC_WITHDRAWLS";
	public static final String PAYROLL_CHKS = "PAYROLL_CHKS";
	
	public static final String LEAVE_TYPE = "LEAVE_TYPE";
	
	public static final String DEFAULT_COOLINGOFF_PERIOD = "3 Months";
	public static final String[] WEEKENDS = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
	
	public static final String[] SHORTWEEKENDS = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
	public static final String[] SHORTWEEKWISEMONTH = {"Week1","Week2","Week3","Week4","Week5","Week6"};
	public static final String[] SHORTDAYS = {"Day1","Day2","Day3","Day4","Day5","Day6","Day7","Day8","Day9","Day10","Day11","Day12","Day13","Day14","Day15","Day16","Day17","Day18","Day19","Day20","Day21","Day22","Day23","Day24","Day25","Day26","Day27","Day28","Day29","Day30","Day31"};
	
	public static final String[] MONTHS = {"January","February","March","April","May","June","July","August","September","October","November","December"};
	
	public static final String LEAVE_OPTION = "LEAVE_OPTION";
	public static final String RESTRICTDAY = "RESTRICT_DAY";

	public static final String[] NOTICE_PERIOD = { "Immediately Available", "15 days", "1 Month", "2 Months", "3 Months" };
	
	public static final String[] COOLING_OFF_PERIOD = { "0 Days", "1 Week", "2 Weeks", "3 Weeks", "1 Month", "2 Months", "3 Months", "4 Months", "5 Months", "6 Months", "7 Months", "8 Months", "9 Months", "10 Months", "11 Months","1 Year" };

	public static final String[] TRAVEL_ALLOWANCE = {"0%","10%","15%","20%","25%","30%","35%","40%","45%","50%"};
	
	public static final String[] EXPERIENCE_LEVEL = {"Level-1","Level-2","Level-3","Level-4"};
	
	public static final String LEAVE_PENDING = "PENDING";
	public static final String LEAVE_APPROVED = "APPROVED";
	public static final String LEAVE_REJECTED = "REJECTED";
	public static final String LEAVE_CANCELLED = "CANCELLED";
	public static final String LEAVE = "LEAVE";
	public static final String LEAVE_NEW = "NEW"; 
	public static final String LEAVE_WITHDRAW = "WITHDRAW";
	
	public static final String CLINT_VENDOR_DOES_NOT_EXIST = "false";
	public static final String CLINT_VENDOR_IF_EXIST = "true";
	public static final String CLINT_VENDOR_IF_EXIST_AS_SAMEORG = "sameorg";
	
	public static final String[] DAY_HOURS_LIST = { "01:00","01:30","02:00","02:30","03:00","03:30","04:00",
			"04:30","05:00","05:30","06:00","06:30","07:00","07:30","08:00","08:30","09:00","09:30","10:00",
			"10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00",
			"16:30","17:00","17:30","18:00","18:30","19:00","19:30","20:00","20:30","21:00","21:30","22:00",
			"22:30","23:00","23:30","24:00" };

	
	public static final String TASK_STATUS_NEW = "New";
	public static final String TASK_STATUS_IN_PROGRESS = "In-Progress";
	public static final String TASK_STATUS_DONE = "Done";
	public static final String TASK_STATUS_ON_HOLD = "On-Hold";
	
	public static final String TASK_PRIORITY_P1 = "P1";
	public static final String TASK_PRIORITY_P2 = "P2";
	public static final String TASK_PRIORITY_P3 = "P3";
	public static final String TASK_PRIORITY_P4 = "P4";
	
	public static final int TASK_PERCENTAGE_ZERO = 0;
	public static final int TASK_PERCENTAGE_TEN = 10;
	public static final int TASK_PERCENTAGE_TWENTY = 20;
	public static final int TASK_PERCENTAGE_THIRTY = 30;
	public static final int TASK_PERCENTAGE_FORTY = 40;
	public static final int TASK_PERCENTAGE_FIFTY = 50;
	public static final int TASK_PERCENTAGE_SIXTY = 60;
	public static final int TASK_PERCENTAGE_SEVENTY = 70;
	public static final int TASK_PERCENTAGE_EIGHTY = 80;
	public static final int TASK_PERCENTAGE_NINTY = 90;
	public static final int TASK_PERCENTAGE_HUNDRED = 100;
	
	public static final String TASK_ACTIVITY_STATUS = "Status";
	public static final String TASK_ACTIVITY_PRIORITY = "Priority";

	public static final String HOLIDAY_RESTRICTED = "Restricted";
	public static final String LEAVE_EMAIL_TO_APPROVERS = "LeaveEmailToApprovers";
	public static final String LEAVE_NOTIFICATION_TO_APPROVERS = "LeaveNotificationToApprovers";
	
	public static final String ON_BEHALF_EMAIL = "onBehalfEmail";
	public static final String ON_BEHALF_NOTIFICATION = "onBehalfNotification";
	
	public static final String LEAVE_EMAIL_ON_BEHALF_EMAIL = "OnBehafLeaveEmail";
	public static final String LEAVE_EMAIL_ON_BEHALF_NOTIFICATION = "OnBehafLeaveNotification";

	public static final String ROLE_CREATE_ASSOCIATE="ROLE_CREATE_ASSOCIATE";
	public static final String ROLE_CREATE_USER="ROLE_CREATE_USER";
	public static final String ROLE_CREATE_REQUIREMENT = "ROLE_CREATE_REQUIREMENT";
	public static final String ROLE_CREATE_CLIENT = "ROLE_CREATE_CLIENT";
	public static final String ROLE_CREATE_VENDOR = "ROLE_CREATE_VENDOR";
	public static final String ROLE_CREATE_CANDIDATE ="ROLE_CREATE_CANDIDATE";
	public static final String ROLE_CREATE_PROJECT ="ROLE_CREATE_PROJECT";
	public static final String ROLE_ADD_NEW_CONTRACT ="ROLE_ADD_NEW_CONTRACT";
	public static final String ROLE_ADD_ASSET ="ROLE_ADD_ASSET";
	public static final String ROLE_CREATE_PROJECT_TASKS ="ROLE_CREATE_PROJECT_TASKS";
	public static final String REPORT_FILTER_LAST_7_DAYS = "Last_7_days";
	public static final String REPORT_FILTER_LAST_15_DAYS = "Last_15_days";
	public static final String REPORT_FILTER_CURRENT_MONTH = "current_month";
	public static final String REPORT_FILTER_1_MONTH = "1_month";
	public static final String REPORT_FILTER_3_MONTHS = "3_months";
	public static final String REPORT_FILTER_6_MONTHS = "6_months";
	public static final String REPORT_FILTER_YTD = "YTD";
	public static final String REPORT_FILTER_LAST_YEAR = "Last_Year";
	public static final String REPORT_FILTER_Last_Week = "Last_Week";
	public static final String REPORT_FILTER_1_Month_Ago = "1_Month_Ago";
	public static final String REPORT_FILTER_CURRENT_YEAR = "Current_Year";
	public static final String CLIENT_REPORT = "clientReport";
	public static final String VENDOR_REPORT = "vendorReport";
	public static final String GENERAL = "General";
	public static final Long TaskStatusID = 1069L;

	public static final String SYSTEM_TASK_NONBILLABE = "System Task NonBillable";
	public static final String SYSTEM_TASK_BILLABE = "System Task Billable";

	public static final String MAIL_ASSIGNEMPLOYEE_PROJECTTASK = "MailtoAssignEmployeeinProjecttask";
	

	public static final String[] TOP_TAKERS_LEAVE_HEADERS = { "Emp id", "Employee Name","Email id"," Leave Type", "Duration ", "No. of Days", "No. of Extra Days","Balance Leaves"}; 
	public static final String LEAVE_DATA =  "Leaves";
	
	public static final String IT_DECLARATION_FORM =  "It_Declaration_Submission_form";
	public static final String IT_DECLARATION_FORM_PARAGRAPHDATA =  "I hereby declare that the following investment will be made by me during the financial year";
	
	
	public static final String[] USERS_USAGE_REPORT_EXPORT = { "Users", "Email", "User Type", "Org", "No.of Activities", "Multiple Ips", "Created On", "Last Activity On"};

	public static final String ORG_REQUEST_FILTER_TODAY = "Today";
	public static final String ORG_REQUEST_FILTER_ONE_WEEK_AGO = "1 Week Ago";
	public static final String ORG_REQUEST_FILTER_TWO_WEEK_AGO = "2 Weeks Ago";
	public static final String ORG_REQUEST_FILTER_ONE_MONTH_AGO = "1 month ago";
	public static final String ORG_REQUEST_FILTER_THREE_MONTHS_AGO = "3 months ago";
	
	

	public static final String[] UNSUBSCRIPTION_TABID_LISTED = {"3","4","8","16","20","23","30" };


	public static final String PAYROLL_CATEGORY = "Payroll_Deduction";
	public static final String ID_TYPE_SSN = "SSN";
	public static final String ID_TYPE_LCA = "LCA";
	public static final String RELATION_TYPE_SPOUSE = "Spouse";
	public static final String RELATION_TYPE_CHILD ="Child";
	public static final String RELATION_TYPE_FATHER="Father";
	public static final String RELATION_TYPE_MOTHER="Mother";
	
	public static final String ID_TYPE_PASSPORT="Passport";
	public static final String ID_TYPE_VISA="Visa";

	
	public static final String SUBSCRIPTION_TYPE = "Subscription Type";
	public static final String USER_SUBSCRIPTION = "User Subscription";
	public static final String DATA_STORAGE = "Data Storage";
	
	public static final String[] REGISTERED_COUNTRIES = { "Australia", "France", "India", "Singapore", "United Kingdom", "United States"};
	
	public static final String ROLE_GUEST_VENDOR = "ROLE_GUEST_VENDOR";
	public static final String ROLE_GUEST_CLIENT = "ROLE_GUEST_CLIENT";
	
	public static final String LICENSE_PAYMENT_ACKNOWLEDGEMENT = "licensePaymentAcknowledgement";
	
	public static final String LICENSE_PAYMENT_ACKNOWLEDGEMENT_DETAILS = "licensePaymentAcknowledgementDetails";
	
	public static final String API_TRUMAIL_URL = "https://api.trumail.io/v1/json/";
	
	public static final String REPORT_FILTER_TODAY = "today";
	public static final String REPORT_FILTER_YESTERDAY = "yesterday";
	public static final String REPORT_FILTER_1_WEEK = "1_week";
	public static final String REPORT_FILTER_2_WEEKS = "2_weeks";
	public static final String REPORT_FILTER_2_MONTHS = "2_months";
	public static final String[] DAYWISE_TIMESHEET_REPORTS = {"Timesheet Date","Day","Employee Id","Employee Name","Employee Email","Department","Ref/Ticket No","Project Name","Task","Billable Hours","Non-Billable Hours","Status","Comments","Submitted On","Approved On","Location"};
	
    public static final String BOOLEAN_TRUE = "true";
    public static final String LOCAL_TAX_YES = "YES";
    public static final String LOCAL_TAX_NO = "NO";

    
    public static final String[] GENDER = { "Male", "Female", "Transgender"};

    public static final String[] EMPLOYMENT_TYPES = { "C2C", "C2C-1099", "W-2", "C2H", "C2H-1099", "Part Time", "Full Time", "Other" };

    public static final String[] MARITAL_STATUS = { "Single", "Married", "Divorsed", "Separated"};
    
    public static final String[] RATE_CURRENCY = { "INR", "AUD", "USD", "GBP", "SGD", "EUR" };
    
    public static final String[] BILLING_TYPE = { "Billable", "Non-Billable" };

    
    public static final String[] ASSET_WORKING_STATUS = { "Working", "Out Of Service"};

	public static final String EMAIL_SUCCESS = "success";
	public static final String UPDATE = "UPD";
	public static final String DELETE = "DEL";
	public static final String NONE = "NONE";
	public static final String ADDITION = "ADD";
	public static final String GENERAL_SHIFT = "General Shift"; 
	public static final String STATUS_NO = "NO";
	public static final String STATUS_YES = "YES";
	public static final String ANNUALLY = "Annually";
	
	public static final String HALFYEARLY = "HalfYearly";
	public static final String QUARTERLY = "Quarterly";

	public static final String OFFSHORE_C2C = "Offshore C2C";

	public static final String PAYROLL_DELETE = "Delete";
	public static final String REACTIVE = "Reactive";
	public static final String REQUEST_FOR_APPROVING_PAYROLL = "RequestForApprovingPayroll";
	public static final String APPROVE_MAIL_TO_REQUEST_USER = "ApproveMailToRequestUser";

	
	public static final String[] IT_DECLARATION_SUBMISSION_FORM = { "Item Name", "Particulars" ,"Eligibility(Rs.)","Amount"};
	public static final String DEDUCTED = "Deducted";
	
	public static final String FULL_TIME = "Full Time";
	public static final String USER_EMAIL_UPDATION = "UserEmailUpdation";
	
	public static final String JOB_PARSING = "job_parsing";
	
	public static final String RESUME_PARSING = "resume_parsing";
	
	public static final String REGULAR = "Regular";
	
	public static final String[] DIRECT_DEPOSIT_HEADER = { "Employee\nNumber", "Employee Name", "Department", "Transit\nNumber", "Account\nNumber", "Deposit\nAmount", "Description" };
	
	//Indian_Payroll
	public static final String IND_PAYROLL = "INDPayroll";
	public static final String US_PAYROLL = "USPayroll";
	
	public static final String IND_PAYROLL_PAYHEAD_DEDUCTION = "Deduction";
	public static final String IND_PAYROLL_PAYHEAD_EARNINGS = "Earnings";
	
	public static final String TAXABLE = "Taxable";
	public static final String NON_TAXABLE = "NonTaxable";
	
	public static final String IND_PAYROLL_PAYFREQUENCY_MONTHLY = "Monthly";
	public static final String IND_PAYROLL_PAYFREQUENCY_WEEKLY = "Bi-Weekly";
	public static final String IND_PAYROLL_PAYFREQUENCY_BI_WEEKLY = "Weekly";
	public static final String IND_PAYROLL_PAYFREQUENCY_ANUALLY = "Annually";
	
	public static final String IND_PAYROLL_PAYHEAD_TDS = "TDS";
	public static final String IND_PAYROLL_PROFESSIONAL_TAX = "PT";
	public static final String IND_PAYROLL_PAYHEAD_PF = "PF";
	public static final String IND_PAYROLL_PAYHEAD_ESI = "ESI";
	
	public static final String[] CONSTANT_PAYHEAD_SHORTCODES = { "PT", "TDS", "PF", "ESI", "L&D", "BO", "ARR", "RMB" };
	
	public static final String[] BASIC_DEPENDENT_CONSTANT_PAYHEAD_SHORTCODES = { "PF", "ESI" };
	
	public static final String[] APPLICANTS_JOINED_LIST_REPORT_HEADERS = { "Recruiter Name", "Date", "No Of Jobs",
			"Total Offer", "Total Joiners"};
	

	public static final String CONTRIBUTION = "contribution";
	public static final String DEDUCTION = "deduction";
	
	public static final String IND_PAYROLL_PAYHEAD_COMPONENT_VARIABLE = "Variable";
	public static final String IND_PAYROLL_PAYHEAD_COMPONENT_FIXED = "Fixed";
	
	public static final String SEND_MAIL_TO_INDPAYROLL_APPROVER = "sendMailToIndPayrollApprover";
	public static final String SEND_NOTIFICATION_TO_INDPAYROLL_APPROVER = "sendNotificationToIndPayrollApprover";
	
	public static final String SEND_APPROVED_MAIL_TO_REQUESTED_USERIND = "sendApprovedMailToRequestedUserInd";
	public static final String SEND_APPROVED_NOTIFICATION_TO_REQUESTE_DUSERIND = "sendApprovedNotificationToRequestedUserInd";

	public static final String LCA_MANAGER_EXPIRED_EMAIL = "LCAManagerExpiredEmail";
	public static final String LCA_MANAGER_EXPIRED_NOTIFICATION = "LCAManagerExpiredNotification";
	public static final String LCA_MANAGER_EXPIRED_EMAIL_IN_DAYS = "LCAManagerExpiredInDaysEmail";

	public static final String CONFIRMED = "Confirmed";
	
	public static final String PROBATIONARY_ASSOCiATES_EMAIL = "ProbationaryAssociatesEmail";

	public static final String CHANGE_JOB_STATUS_EMAIL = "ChangeJobStatusEmail";
	public static final String CHANGE_JOB_STATUS_NOTIFICATION = "ChangeJobStatusEmail";

	
	public static final String CHANGE_REPORTING_MANAGER_EMAIL="ChangeReportingManagerEmail";
	public static final String CHANGE_REPORTING_MANAGER_NOTIFICATOPN="ChangeReportingManagerNotification";
	

	public static final String ASSOCIATE_WITH_HOLDING_REPORT = "Associate With Holding Report";
	public static final String DEDUCTION_SUMMARY_REPORTS = "Deduction Summary Reports";
	public static final String EMPLOYEE_PAYROLL_REPORTS = "Employee PayRoll";
	public static final String CONTRIBUTION_SUMMARY_REPORTS = "Contribution Summary Reports";
	public static final String CONTRACT_EMPLOYEE_PAYROLL = "Contract Employee PayRoll";
	

	public static final String PURCHASED_CANDIDATE_DETAILS_TO_CONTRIBUTOR = "purchasedCandidateDetailsToContributor";

	public static final String CONFIRMATION_OF_EMPLOYMENT_MAIL = "ConfirmationOfEmploymentMail";
	public static final String US_PAYROLL_SCHEDULE_EMAIL = "USPayrollScheduleEmail";
	public static final String PMS_Ticket_Status_Updated_Email = "PMSTicketStatusUpdatedEmail";

	public static final String COMMENTED_RAISED_TICKET_EMAIL = "CommentedRaisedTicketEmail";
	public static final String COMMENTED_RAISED_TICKET_NOTIFICATION = "CommentedRaisedTicketNotification";
	
	
	public static final String EMAIL_REMAINDER_INVOICE =  "Applicants Invoice";
	
	public static final String APPLICANT_JOINING = "ApplicantJoining";
	
	public static final String COUCH_DOCUMENT_TYPE_ORGLOCATION = "OrgLocation";
	
	public static final String COUCH_DOCUMENT_TYPE_USER_LOGIN_ACCOUNT = "User Account";
	
	public static final String COUCH_DOCUMENT_TYPE_USER_LOGIN_SESSION = "Session";
	
	public static final String COUCH_DOCUMENT_UPLOAD_TYPE = "Import";
	
	public static final String COUCH_DOCUMENT_UPLOAD_FAILURE_TYPE = "Failure";
	
	
    public static final String ASSOCIATE_BANK_DETAIL_HEADERS [] = {"Assoicate Id",	"Associate Email*",	"Account Type",	"Bank Name","Account Number","Routing Number"};
	
	public static final String[] BANK_TYPE_DROPDOWN = {"Cheque","Direct Deposit"};
	

	public static final String ASSOCIATE_DEDUCTION_CONTRIBUTION_DETAIL_HEADERS [] = {"Associate Id",	"Associate Email*",	"Deduction Type","Deduct Amount","Contribute Amount"};
	
	public static final String PAYROLLDEDUCTION_TYPE_DROPDOWN = "Payroll_Deduction";

	public static final String PAYMENT_MODE_SAVINGS = "Savings";
	
	public static final String GOAL_TYPE_NEW  = "New_Goal";
	
	public static final String GOAL_TYPE_SUB_GOAL  = "Sub_Goal";

	public static final String[] DATA_MIGRATION_ASSOCIATE_INFO_EXCEL_HEADERS = { "Assoicate Id" , "Associate Email*","UAN Number*","PF Number*","ESI Number"};
	
	public static final String EMPLOYEE_ON_LEAVE = "EmployeeOnLeave";
	public static final String APPROVE_PENDING_LEAVES="approvePendingLeaves";
	
	public static final String CLOCK_IN = "ClockIn";
	public static final String CLOCK_OUT = "ClockOut";
	public static final String PLAY = "play";
	public static final String PAUSE = "pause";
	public static final String LOAN_AND_ADVANCE = "Loan And Advance";

	public static final String NOTIFICATION_LEAVE_TITLE = "Leave Status";
	
	public static final String IT_DECRALARATION_HEADER1 = "INCOME TAX DECLARATION FORM - F.Y. ";
	public static final String IT_DECRALARATION_HEADER2 = "(To be used to declare investment that will be made during the period from ";
	public static final String[] IT_DECRALARATION_FOOTER = {"DECLARATIONS","I hereby declare that the information given above is correct and true in all respects. I am also aware that the company will be considering the above details in utmost good faith based on the details provided by me and that I am personally liable for any consequences arising out of errors, if any, in the above information",
			                                                "I am also aware that any person making a false statement / declaration in the above form shall be liable to be fined and prosecution u/s 277 of the Income Tax Act, 1961", "The proof of payment / Supportings for claim, will be provided latest by _________________",
			                                                "Note-", "Date","Place","SIGNATURE OF THE EMPLOYEE"};
}