CALL AlterTableAddColumn ('ORG_DOCUMENTS','TERMINATE_REASON','TEXT NULL AFTER `END_DATE`');


INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('Week Time Sheet Submission', 'Notification', 'Week Time Sheet Submission', 'Used for Week Time Sheet Submission', '{{TimeSheetSubmission.FirstName}} {{TimeSheetSubmission.LastName}} have submitted Timesheet', 'org', 'Best Regards,\n{{TimeSheetSubmission.FirstName}} {{TimeSheetSubmission.LastName}}.', 'Dear {{TimeSheetSubmission.ApproverFirstName}} {{TimeSheetSubmission.ApproverLastName}},\n\n{{TimeSheetSubmission.FirstName}} {{TimeSheetSubmission.LastName}} have submitted timesheet for approval from {{TimeSheetSubmission.TimeFrequency}}.\n\nProject Name : {{TimeSheetSubmission.ProjectName}}\n\nClient : {{TimeSheetSubmission.ClientName}}\n\nInvoice Frequency : {{TimeSheetSubmission.InvoiceFrequency}}', 'N', 'SLADMIN', 'com', 'in.com');



DELETE FROM `EMAIL_SCENARIO` WHERE `ID`='12';
DELETE FROM `EMAIL_SCENARIO` WHERE `ID`='17';
DELETE FROM `EMAIL_SCENARIO` WHERE `ID`='18';