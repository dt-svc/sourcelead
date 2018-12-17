CALL AlterTableAddColumn ('TIMESHEET_SUBMISSIONS','DOC_FILE','MEDIUMBLOB NULL  AFTER `LEVEL_OF_APPROVER`');
CALL AlterTableAddColumn ('TIMESHEET_SUBMISSIONS','FILE_CONTENT_TYPE','VARCHAR(50) NULL  AFTER `DOC_FILE`');
CALL AlterTableAddColumn ('TIMESHEET_SUBMISSIONS','FILE_NAME','VARCHAR(50) NULL  AFTER `FILE_CONTENT_TYPE`');
CALL AlterTableAddColumn ('TIMESHEET_SUBMISSIONS','WEEK_COMMENTS','TEXT NULL  AFTER `FILE_NAME`');

UPDATE `EMAIL_SCENARIO` SET `BODY`='Welcome !!!\n\nHi {{Registration.FirstName}} {{Registration.LastName}},\n\nIt\'s great to see that you\'ve started accessing sourcelead\n\n{{Registration.LoginURL}}\n\nI hope you have a wonderful time with  If you have any questions or comments, feel free to reach out to us at support@com' WHERE `ID`='1';
UPDATE `EMAIL_SCENARIO` SET `BODY`='Hi {{ForgotPassword.FirstName}} {{ForgotPassword.LastName}},\n\nWe received a request to reset your password for your SourceLead account.\n\nVerification Code : {{ForgotPassword.verificationCode}}\n\nTo reset your password process simply click the link below\n\n{{ForgotPassword.ResetPasswordURL}}\n\nIf you didn\'t ask to change your password ,don\'t worry! Your password is still safe and access the sourcelead with existing credentials.' WHERE `ID`='2';
UPDATE `EMAIL_SCENARIO` SET `BODY`='Hi {{UpdatePassword.FirstName}} {{UpdatePassword.LastName}},\n\nThe Password of your Sourcelead  has been reset successfully.\n\nYou can now log in to your Sourcelead application with your new password. If you do not request for new password please contact support@com' WHERE `ID`='3';
UPDATE `EMAIL_SCENARIO` SET `BODY`='Hi {{ManagerAssignedToProject.ManagerFirstName}} {{ManagerAssignedToProject.ManagerLastName}},\n\n{{ManagerAssignedToProject.UserEmailId}} assigned as a project manager for the {{ManagerAssignedToProject.ProjectName}} to the\n\n{{ManagerAssignedToProject.ClientName}}.\n\nTeam Members\n\n{{ManagerAssignedToProject.TeamMembers}}.' WHERE `ID`='5';
UPDATE `EMAIL_SCENARIO` SET `BODY`='Hi {{TeamAssignedToProject.EmployeeFirstName}} {{TeamAssignedToProject.EmployeeLastName}},\n\n{{TeamAssignedToProject.UserEmailId}} assigned you as a team member for the {{TeamAssignedToProject.ProjectName}} and your project manager is\n\n{{TeamAssignedToProject.ManagerFirstName}} {{TeamAssignedToProject.ManagerLastName}}.' WHERE `ID`='6';
UPDATE `EMAIL_SCENARIO` SET `BODY`='Hi {{TimeSheetSubmission.ApproverFirstName}} {{TimeSheetSubmission.ApproverLastName}},\n\n{{TimeSheetSubmission.FirstName}} {{TimeSheetSubmission.LastName}} have submitted timesheet for approval from {{TimeSheetSubmission.TimeFrequency}}.\n\nProject Name : {{TimeSheetSubmission.ProjectName}}\n\nClient : {{TimeSheetSubmission.ClientName}}\n\nInvoice Frequency : {{TimeSheetSubmission.InvoiceFrequency}}' WHERE `ID`='7';

UPDATE `EMAIL_SCENARIO` SET `BODY`='Hi {{TimeSheetSubmission.ApproverFirstName}} {{TimeSheetSubmission.ApproverLastName}},\n\n{{TimeSheetSubmission.FirstName}} {{TimeSheetSubmission.LastName}} have submitted timesheet for approval from {{TimeSheetSubmission.TimeFrequency}}.\n\nProject Name : {{TimeSheetSubmission.ProjectName}}\n\nClient : {{TimeSheetSubmission.ClientName}}\n\nInvoice Frequency : {{TimeSheetSubmission.InvoiceFrequency}}' WHERE `ID`='9';

UPDATE `EMAIL_SCENARIO` SET `BODY`='Hi{{TimesheetScheduler.FirstName}}{{TimesheetScheduler.LastName}},\n\nYou need to update the timesheet of {{TimesheetScheduler.WeekFrequency}}.So update as soon as possible' WHERE `ID`='11';

UPDATE `EMAIL_SCENARIO` SET `BODY`='Hi {{TimesheetScheduler.FirstName}}{{TimesheetScheduler.LastName}},\n\nYour timesheet have been approved by {{TimesheetApproved.ApproverFirstName}}{{TimesheetApproved.ApproverLastName}}  for the {{TimesheetApproved.WeekFrequency}}' WHERE `ID`='13';


UPDATE `EMAIL_SCENARIO` SET `BODY`='Hi {{TimesheetRejected.FirstName}} {{TimesheetRejected.LastName}},\n\nYour timesheet has been rejected by {{TimesheetRejected.rejecteduserFirstName}} {{TimesheetRejected.rejecteduserLastName}} of {{TimesheetRejected.WeekFrequency}}\n\nfor the {{TimesheetRejected.ProjectName}} {{TimesheetRejected.Reason}}.' WHERE `ID`='15';

UPDATE `EMAIL_SCENARIO` SET `BODY`='Hi{{TimesheetScheduler.FirstName}}{{TimesheetScheduler.LastName}},\n\nYou need to update the timesheet of {{TimesheetScheduler.WeekFrequency}}.So update as soon as possible' WHERE `ID`='17';

UPDATE `EMAIL_SCENARIO` SET `BODY`='Hi {{TimesheetScheduler.FirstName}}{{TimesheetScheduler.LastName}},\n\nYour timesheet have been approved by {{TimesheetApproved.ApproverFirstName}}{{TimesheetApproved.ApproverLastName}}  for the {{TimesheetApproved.WeekFrequency}}' WHERE `ID`='19';

UPDATE `EMAIL_SCENARIO` SET `BODY`='Hi {{TimesheetRejected.FirstName}} {{TimesheetRejected.FirstName}},\n\nYour timesheet has been rejected by {{TimesheetRejected.rejecteduserFirstName}} {{TimesheetRejected.rejecteduserLastName}} of {{TimesheetRejected.WeekFrequency}}\n\nfor the {{TimesheetRejected.ProjectName}} {{TimesheetRejected.Reason}}.' WHERE `ID`='21';

ALTER TABLE `NOTIFICATIONS` 
ADD COLUMN `NOTIFICATION_STATUS` VARCHAR(5) NULL AFTER `CREATED_BY`;
