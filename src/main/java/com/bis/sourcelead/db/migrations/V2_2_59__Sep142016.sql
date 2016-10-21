CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_DOCUMENTS','TERMINATE_REASON','TEXT NULL AFTER `END_DATE`');


INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`ID`, `SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('39', 'Week Time Sheet Submission', 'Notification', 'Week Time Sheet Submission', 'Used for Week Time Sheet Submission', '{{TimeSheetSubmission.FirstName}} {{TimeSheetSubmission.LastName}} have submitted Timesheet', 'org', 'Best Regards,\n{{TimeSheetSubmission.FirstName}} {{TimeSheetSubmission.LastName}}.', 'Dear {{TimeSheetSubmission.ApproverFirstName}} {{TimeSheetSubmission.ApproverLastName}},\n\n{{TimeSheetSubmission.FirstName}} {{TimeSheetSubmission.LastName}} have submitted timesheet for approval from {{TimeSheetSubmission.TimeFrequency}}.\n\nProject Name : {{TimeSheetSubmission.ProjectName}}\n\nClient : {{TimeSheetSubmission.ClientName}}\n\nInvoice Frequency : {{TimeSheetSubmission.InvoiceFrequency}}', 'N', 'SLADMIN', 'sourceLead.com', 'in.sourceLead.com');



DELETE FROM `sourcelead`.`EMAIL_SCENARIO` WHERE `ID`='12';
DELETE FROM `sourcelead`.`EMAIL_SCENARIO` WHERE `ID`='17';
DELETE FROM `sourcelead`.`EMAIL_SCENARIO` WHERE `ID`='18';